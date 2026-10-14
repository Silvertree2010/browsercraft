package main

import (
	"fmt"
	"io"
	"mime"
	"net"
	"net/http"
	"os"
	"os/exec"
	"path/filepath"
	"regexp"
	"runtime"
	"strings"
	"sync"
)

var hashRe = regexp.MustCompile(`^[0-9a-f]{2}/[0-9a-f]{40}$`)
var locks sync.Map

func root() string {
	if d := os.Getenv("BC_ROOT"); d != "" {
		return d
	}
	exe, err := os.Executable()
	if err != nil {
		return "."
	}
	exe, _ = filepath.EvalSymlinks(exe)
	return filepath.Dir(exe)
}

func fetchObject(dir, rel string) error {
	m, _ := locks.LoadOrStore(rel, &sync.Mutex{})
	mu := m.(*sync.Mutex)
	mu.Lock()
	defer mu.Unlock()
	p := filepath.Join(dir, filepath.FromSlash(rel))
	if _, err := os.Stat(p); err == nil {
		return nil
	}
	r, err := http.Get("https://resources.download.minecraft.net/" + rel)
	if err != nil {
		return err
	}
	defer r.Body.Close()
	if r.StatusCode != 200 {
		return fmt.Errorf("status %d", r.StatusCode)
	}
	if err := os.MkdirAll(filepath.Dir(p), 0o755); err != nil {
		return err
	}
	f, err := os.CreateTemp(filepath.Dir(p), ".dl-*")
	if err != nil {
		return err
	}
	if _, err := io.Copy(f, r.Body); err != nil {
		f.Close()
		os.Remove(f.Name())
		return err
	}
	f.Close()
	return os.Rename(f.Name(), p)
}

func main() {
	base := root()
	web := filepath.Join(base, "web")
	data := filepath.Join(base, "data")
	objs := filepath.Join(data, "assets", "objects")
	mime.AddExtensionType(".wasm", "application/wasm")
	mime.AddExtensionType(".js", "text/javascript")
	mime.AddExtensionType(".mjs", "text/javascript")
	files := http.FileServer(http.Dir(web))
	datas := http.FileServer(http.Dir(data))

	h := http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Cross-Origin-Opener-Policy", "same-origin")
		w.Header().Set("Cross-Origin-Embedder-Policy", "require-corp")
		w.Header().Set("Cross-Origin-Resource-Policy", "same-origin")
		w.Header().Set("Cache-Control", "no-cache")
		p := r.URL.Path
		switch {
		case p == "/":
			http.Redirect(w, r, "/game/", http.StatusFound)
		case strings.HasPrefix(p, "/data/assets/objects/"):
			rel := strings.TrimPrefix(p, "/data/assets/objects/")
			if !hashRe.MatchString(rel) {
				http.NotFound(w, r)
				return
			}
			if err := fetchObject(objs, rel); err != nil {
				http.Error(w, "asset download failed: "+err.Error(), http.StatusBadGateway)
				return
			}
			http.ServeFile(w, r, filepath.Join(objs, filepath.FromSlash(rel)))
		case strings.HasPrefix(p, "/data/"):
			http.StripPrefix("/data", datas).ServeHTTP(w, r)
		default:
			files.ServeHTTP(w, r)
		}
	})

	ln, err := net.Listen("tcp", "127.0.0.1:8090")
	if err != nil {
		ln, err = net.Listen("tcp", "127.0.0.1:0")
	}
	if err != nil {
		fmt.Println("could not open a port:", err)
		wait()
		return
	}
	url := fmt.Sprintf("http://localhost:%d/game/", ln.Addr().(*net.TCPAddr).Port)
	fmt.Println("Browsercraft is running at " + url)
	fmt.Println("Use Chrome or Edge (WebGPU). Keep this window open while you play; close it to stop.")
	if os.Getenv("BC_NO_BROWSER") == "" {
		open(url + "?ask")
	}
	if err := http.Serve(ln, h); err != nil {
		fmt.Println(err)
		wait()
	}
}

func open(url string) {
	var c *exec.Cmd
	switch runtime.GOOS {
	case "darwin":
		c = exec.Command("open", url)
	case "windows":
		c = exec.Command("rundll32", "url.dll,FileProtocolHandler", url)
	default:
		c = exec.Command("xdg-open", url)
	}
	_ = c.Start()
}

func wait() {
	fmt.Println("Press Enter to exit.")
	fmt.Scanln()
}
