package bc.gpu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public final class Glsl {
    public static final class Res {
        public String src;
        public final List<String> ubos = new ArrayList<>();
        public final List<String> samplers = new ArrayList<>();
        public final Map<String, String> samplerTypes = new HashMap<>();
        public final List<String> buffers = new ArrayList<>();
        public final Map<String, String> bufferTypes = new HashMap<>();
        public final Map<String, int[]> inputs = new HashMap<>();
        public final Map<String, String> inputTypes = new HashMap<>();
        public boolean pointSize;
    }

    static final Set<String> TEXFN = Set.of("texture", "textureLod", "textureGrad", "texelFetch", "textureSize", "textureGather", "textureOffset",
            "textureLodOffset", "textureGradOffset", "texelFetchOffset", "textureProj", "textureProjLod", "textureQueryLevels", "textureQueryLod",
            "textureGatherOffset", "textureProjOffset", "textureSamples");

    static final Map<String, String[]> STYPES = Map.ofEntries(
            Map.entry("sampler2D", new String[]{"texture2D", "sampler", "sampler2D"}),
            Map.entry("isampler2D", new String[]{"itexture2D", "sampler", "isampler2D"}),
            Map.entry("usampler2D", new String[]{"utexture2D", "sampler", "usampler2D"}),
            Map.entry("sampler2DArray", new String[]{"texture2DArray", "sampler", "sampler2DArray"}),
            Map.entry("sampler3D", new String[]{"texture3D", "sampler", "sampler3D"}),
            Map.entry("samplerCube", new String[]{"textureCube", "sampler", "samplerCube"}),
            Map.entry("sampler2DShadow", new String[]{"texture2D", "samplerShadow", "sampler2DShadow"}),
            Map.entry("sampler2DMS", new String[]{"texture2DMS", "sampler", "sampler2DMS"}));

    static final Map<String, String> BTYPES = Map.of("isamplerBuffer", "int", "usamplerBuffer", "uint", "samplerBuffer", "float");

    static final java.util.regex.Pattern CI = java.util.regex.Pattern.compile("(?m)^[ \\t]*const\\s+int\\s+(\\w+)\\s*=\\s*([^;]+);[ \\t]*$");

    public static String constArr(String s) {
        var m = CI.matcher(s);
        var sb = new StringBuilder();
        int p = 0;
        while (m.find()) {
            var n = m.group(1);
            if (!java.util.regex.Pattern.compile("\\[\\s*" + n + "\\s*\\]").matcher(s).find()) continue;
            sb.append(s, p, m.start()).append("#define ").append(n).append(" (").append(m.group(2).trim()).append(')');
            p = m.end();
        }
        return sb.append(s, p, s.length()).toString();
    }

    public static String preprocess(String src, Map<String, String> vals, Set<String> flags, Function<String, String> inc) {
        var sb = new StringBuilder();
        var seen = new HashSet<String>();
        include(src, inc, sb, seen, 0);
        var lines = sb.toString().split("\n", -1);
        var out = new StringBuilder();
        boolean ver = false;
        for (var l : lines) {
            var t = l.strip();
            if (t.startsWith("#version")) {
                out.append("#version 450\n#define RENDERPEARL_DEPTH_IS_ZERO_TO_ONE\n#define RENDERPEARL_INSTANCE_INDEX_INCLUDES_BASE_INSTANCE\n");
                for (var f : flags) out.append("#define ").append(f).append('\n');
                for (var e : vals.entrySet()) out.append("#define ").append(e.getKey()).append(' ').append(e.getValue()).append('\n');
                ver = true;
                continue;
            }
            if (t.startsWith("#extension")) { out.append('\n'); continue; }
            out.append(l).append('\n');
        }
        var r = out.toString();
        if (!ver) {
            var h = new StringBuilder("#version 450\n#define RENDERPEARL_DEPTH_IS_ZERO_TO_ONE\n#define RENDERPEARL_INSTANCE_INDEX_INCLUDES_BASE_INSTANCE\n");
            for (var f : flags) h.append("#define ").append(f).append('\n');
            for (var e : vals.entrySet()) h.append("#define ").append(e.getKey()).append(' ').append(e.getValue()).append('\n');
            r = h + r;
        }
        return r;
    }

    static void include(String src, Function<String, String> inc, StringBuilder sb, Set<String> seen, int depth) {
        for (var l : src.split("\n", -1)) {
            var t = l.strip();
            if (t.startsWith("#include") || t.startsWith("#moj_import")) {
                int a = t.indexOf('<'), b = t.lastIndexOf('>');
                if (a < 0) { a = t.indexOf('"'); b = t.lastIndexOf('"'); }
                var id = t.substring(a + 1, b);
                if (depth < 16 && seen.add(id)) {
                    var s = inc.apply(id);
                    if (s == null) throw new IllegalArgumentException("missing include " + id);
                    include(s, inc, sb, seen, depth + 1);
                }
                sb.append('\n');
                continue;
            }
            sb.append(l).append('\n');
        }
    }

    record Tok(int k, String s) {}

    static List<Tok> lex(String s) {
        var r = new ArrayList<Tok>();
        int i = 0, n = s.length();
        while (i < n) {
            char c = s.charAt(i);
            if (c == '/' && i + 1 < n && s.charAt(i + 1) == '/') {
                int j = s.indexOf('\n', i);
                if (j < 0) j = n;
                r.add(new Tok(0, s.substring(i, j)));
                i = j;
            } else if (c == '/' && i + 1 < n && s.charAt(i + 1) == '*') {
                int j = s.indexOf("*/", i + 2);
                j = j < 0 ? n : j + 2;
                r.add(new Tok(0, s.substring(i, j)));
                i = j;
            } else if (c == '#') {
                int j = i;
                while (j < n && s.charAt(j) != '\n') {
                    if (s.charAt(j) == '\\' && j + 1 < n && s.charAt(j + 1) == '\n') j++;
                    j++;
                }
                r.add(new Tok(3, s.substring(i, j)));
                i = j;
            } else if (Character.isWhitespace(c)) {
                int j = i;
                while (j < n && Character.isWhitespace(s.charAt(j))) j++;
                r.add(new Tok(0, s.substring(i, j)));
                i = j;
            } else if (Character.isLetter(c) || c == '_') {
                int j = i;
                while (j < n && (Character.isLetterOrDigit(s.charAt(j)) || s.charAt(j) == '_')) j++;
                r.add(new Tok(1, s.substring(i, j)));
                i = j;
            } else if (Character.isDigit(c) || (c == '.' && i + 1 < n && Character.isDigit(s.charAt(i + 1)))) {
                int j = i;
                while (j < n && (Character.isLetterOrDigit(s.charAt(j)) || s.charAt(j) == '.' || ((s.charAt(j) == '+' || s.charAt(j) == '-') && (s.charAt(j - 1) == 'e' || s.charAt(j - 1) == 'E')))) j++;
                r.add(new Tok(2, s.substring(i, j)));
                i = j;
            } else {
                r.add(new Tok(4, String.valueOf(c)));
                i++;
            }
        }
        return r;
    }

    static int next(List<Tok> t, int i) {
        i++;
        while (i < t.size() && t.get(i).k == 0) i++;
        return i;
    }

    static int prev(List<Tok> t, int i) {
        i--;
        while (i >= 0 && t.get(i).k == 0) i--;
        return i;
    }

    static String s(List<Tok> t, int i) { return i >= 0 && i < t.size() ? t.get(i).s : ""; }

    public static Res rewrite(String src, Map<String, Integer> bind, boolean vert) {
        var res = new Res();
        var t = lex(src);
        var out = new StringBuilder();
        var samp = new HashMap<String, String>();
        var bufs = new HashMap<String, String>();
        for (int i = 0; i < t.size(); i++) {
            if (t.get(i).k != 1 || !t.get(i).s.equals("uniform")) continue;
            int j = next(t, i);
            var ty = s(t, j);
            int k = next(t, j);
            var nm = s(t, k);
            if (STYPES.containsKey(ty)) samp.put(nm, ty);
            else if (BTYPES.containsKey(ty)) bufs.put(nm, ty);
        }
        var fnSamp = new HashMap<String, String>();
        var calls = new ArrayList<String>();
        var parens = new ArrayList<Integer>();
        var fix = new ArrayList<Boolean>();
        int brace = 0;
        int paramDepth = -1;
        for (int i = 0; i < t.size(); i++) {
            var tk = t.get(i);
            if (tk.k == 3) {
                out.append(dir(tk.s));
                continue;
            }
            if (tk.k == 4) {
                switch (tk.s) {
                    case "{" -> brace++;
                    case "}" -> { brace--; if (brace == 0) fnSamp.clear(); }
                    case "(" -> {
                        int p = prev(t, i);
                        calls.add(t.get(p).k == 1 ? t.get(p).s : "");
                        parens.add(i);
                        fix.add(vert && s(t, p).equals("texture"));
                        if (brace == 0 && paramDepth < 0 && t.get(p).k == 1) {
                            int pp = prev(t, p);
                            if (pp >= 0 && t.get(pp).k == 1) paramDepth = calls.size();
                        }
                    }
                    case ")" -> {
                        if (paramDepth == calls.size()) paramDepth = -1;
                        if (!calls.isEmpty()) { calls.remove(calls.size() - 1); parens.remove(parens.size() - 1); if (fix.remove(fix.size() - 1)) out.append(", 0.0"); }
                    }
                    case "[" -> {
                        int q = next(t, i);
                        if (s(t, q).equals("]")) {
                            int n = arrLen(t, q);
                            if (n > 0) { out.append('[').append(n).append(']'); i = q; continue; }
                        }
                    }
                    default -> {}
                }
                out.append(tk.s);
                continue;
            }
            if (tk.k != 1) {
                out.append(tk.s);
                continue;
            }
            var w = tk.s;
            if (w.equals("gl_PointSize")) { out.append("bc_ps"); res.pointSize = true; continue; }
            if (w.equals("gl_VertexID") || w.equals("gl_VertexIndex")) { out.append("int(gl_VertexIndex)"); continue; }
            if (w.equals("texture") && vert && s(t, next(t, i)).equals("(")) { out.append("textureLod"); continue; }
            if (w.equals("gl_InstanceID") || w.equals("gl_InstanceIndex")) { out.append("int(gl_InstanceIndex)"); continue; }
            if (w.equals("layout") && brace == 0) {
                int j = next(t, i);
                if (s(t, j).equals("(")) {
                    int e = j;
                    while (e < t.size() && !t.get(e).s.equals(")")) e++;
                    var inner = new StringBuilder();
                    for (int q = j + 1; q < e; q++) inner.append(t.get(q).s);
                    int u = next(t, e);
                    if (s(t, u).equals("uniform")) {
                        int nmI = next(t, u);
                        var nm = s(t, nmI);
                        if (!STYPES.containsKey(nm) && !BTYPES.containsKey(nm)) {
                            Integer b = bind.get(nm);
                            if (b == null) b = 90 + res.ubos.size();
                            res.ubos.add(nm);
                            out.append("layout(").append(inner.toString().replace("std140", "").replaceAll("^\\s*,|,\\s*$", "").isBlank() ? "std140" : inner.toString()).append(", set = 0, binding = ").append(b).append(")");
                            i = e;
                            continue;
                        }
                    }
                    if (s(t, u).equals("in") && vert) {
                        int ty = next(t, u), nm = next(t, ty);
                        var m = java.util.regex.Pattern.compile("location\\s*=\\s*(\\d+)").matcher(inner);
                        if (m.find()) {
                            res.inputs.put(s(t, nm), new int[]{Integer.parseInt(m.group(1))});
                            res.inputTypes.put(s(t, nm), s(t, ty));
                        }
                    }
                }
            }
            if (w.equals("uniform") && brace == 0) {
                int j = next(t, i);
                var ty = s(t, j);
                int k = next(t, j);
                var nm = s(t, k);
                int semi = next(t, k);
                if (STYPES.containsKey(ty) && s(t, semi).equals(";")) {
                    var st = STYPES.get(ty);
                    Integer b = bind.get(nm);
                    if (b == null) b = 60 + 2 * res.samplers.size();
                    res.samplers.add(nm);
                    res.samplerTypes.put(nm, ty);
                    out.append("layout(set = 0, binding = ").append(b).append(") uniform ").append(st[0]).append(' ').append(nm).append("_t; ");
                    out.append("layout(set = 0, binding = ").append(b + 1).append(") uniform ").append(st[1]).append(' ').append(nm).append("_s;");
                    i = semi;
                    continue;
                }
                if (BTYPES.containsKey(ty) && s(t, semi).equals(";")) {
                    Integer b = bind.get(nm);
                    if (b == null) b = 80 + res.buffers.size();
                    res.buffers.add(nm);
                    res.bufferTypes.put(nm, ty);
                    var bt = BTYPES.get(ty);
                    var vt = bt.equals("int") ? "ivec4" : bt.equals("uint") ? "uvec4" : "vec4";
                    out.append("layout(std430, set = 0, binding = ").append(b).append(") readonly buffer ").append(nm).append("_b { ").append(bt).append(' ').append(nm).append("_d[]; }; ");
                    out.append(vt).append(' ').append(nm).append("_fetch(int i) { return ").append(vt).append('(').append(nm).append("_d[i], 0, 0, 0); }");
                    i = semi;
                    continue;
                }
            }
            if (paramDepth >= 0 && paramDepth == calls.size() && STYPES.containsKey(w)) {
                int k = next(t, i);
                var pn = s(t, k);
                var st = STYPES.get(w);
                fnSamp.put(pn, w);
                out.append(st[0]).append(' ').append(pn).append("_t, ").append(st[1]).append(' ').append(pn).append("_s");
                i = k;
                continue;
            }
            var sty = fnSamp.containsKey(w) ? fnSamp.get(w) : samp.get(w);
            if (sty != null && !calls.isEmpty()) {
                var fn = calls.get(calls.size() - 1);
                if (TEXFN.contains(fn)) out.append(STYPES.get(sty)[2]).append('(').append(w).append("_t, ").append(w).append("_s)");
                else out.append(w).append("_t, ").append(w).append("_s");
                continue;
            }
            if (w.equals("texelFetch")) {
                int pa = next(t, i);
                int ar = next(t, pa);
                int cm = next(t, ar);
                if (s(t, pa).equals("(") && bufs.containsKey(s(t, ar)) && s(t, cm).equals(",")) {
                    out.append(s(t, ar)).append("_fetch(");
                    calls.add(s(t, ar) + "_fetch");
                    parens.add(pa);
                    fix.add(false);
                    i = cm;
                    continue;
                }
            }
            out.append(w);
        }
        res.src = out.toString();
        if (vert) {
            res.src = res.src.replaceFirst("void\\s+main\\s*\\(\\s*\\)", "void bc_main()") + "\nvoid main() { bc_main(); gl_Position.y = -gl_Position.y; }\n";
        }
        if (res.pointSize) { int v = res.src.indexOf('\n'); res.src = res.src.substring(0, v + 1) + "float bc_ps;\n" + res.src.substring(v + 1); }
        return res;
    }

    static int arrLen(List<Tok> t, int close) {
        int q = next(t, close);
        if (!s(t, q).equals("(")) {
            int e = q;
            while (e < t.size() && !t.get(e).s.equals("=") && !t.get(e).s.equals(";")) e++;
            if (e >= t.size() || !t.get(e).s.equals("=")) return -1;
            int a = next(t, e);
            int b = next(t, a);
            int c = next(t, b);
            int d = next(t, c);
            if (!s(t, b).equals("[") || !s(t, c).equals("]") || !s(t, d).equals("(")) return -1;
            q = d;
        }
        int depth = 0, n = 0;
        boolean any = false;
        for (int i = q + 1; i < t.size(); i++) {
            var x = t.get(i).s;
            if (x.equals("(") || x.equals("[") || x.equals("{")) depth++;
            else if (x.equals(")") || x.equals("]") || x.equals("}")) { if (depth == 0) return any ? n + 1 : 0; depth--; }
            else if (x.equals(",") && depth == 0) n++;
            else if (t.get(i).k != 0) any = true;
        }
        return -1;
    }

    static String dir(String d) {
        return d;
    }
}
