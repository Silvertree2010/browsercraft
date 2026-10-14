import http.server, os, sys, urllib.request, threading

ROOT = sys.argv[1]
DATA = sys.argv[2]
PORT = int(sys.argv[3]) if len(sys.argv) > 3 else 8090
lock = threading.Lock()

class H(http.server.SimpleHTTPRequestHandler):
    def end_headers(self):
        self.send_header('Cross-Origin-Opener-Policy', 'same-origin')
        self.send_header('Cross-Origin-Embedder-Policy', 'require-corp')
        self.send_header('Cross-Origin-Resource-Policy', 'same-origin')
        self.send_header('Cache-Control', 'no-cache')
        super().end_headers()

    def translate_path(self, path):
        path = path.split('?')[0]
        if path.startswith('/data/'):
            p = os.path.join(DATA, path[6:])
            if path.startswith('/data/assets/objects/') and not os.path.exists(p):
                h = os.path.basename(p)
                with lock:
                    if not os.path.exists(p):
                        os.makedirs(os.path.dirname(p), exist_ok=True)
                        urllib.request.urlretrieve('https://resources.download.minecraft.net/' + h[:2] + '/' + h, p + '.tmp')
                        os.replace(p + '.tmp', p)
            return p
        return os.path.join(ROOT, path.lstrip('/'))

    def log_message(self, *a):
        pass

H.extensions_map.update({'.wasm': 'application/wasm', '.js': 'text/javascript', '.mjs': 'text/javascript'})
http.server.ThreadingHTTPServer(('0.0.0.0', PORT), H).serve_forever()
