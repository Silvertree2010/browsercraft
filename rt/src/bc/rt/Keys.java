package bc.rt;

public final class Keys {
    static final int M = 0x40000000;
    static final String PUNCT = "-=[]\\#;'`,./";

    public static int key(int sc) {
        if (sc >= 4 && sc <= 29) return 'a' + sc - 4;
        if (sc >= 30 && sc <= 38) return '1' + sc - 30;
        if (sc == 39) return '0';
        return switch (sc) {
            case 40 -> 13;
            case 41 -> 27;
            case 42 -> 8;
            case 43 -> 9;
            case 44 -> 32;
            case 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56 -> PUNCT.charAt(sc - 45);
            case 76 -> 127;
            default -> sc | M;
        };
    }

    public static int scan(int k) {
        if (k >= 'a' && k <= 'z') return 4 + k - 'a';
        if (k >= '1' && k <= '9') return 30 + k - '1';
        if (k == '0') return 39;
        if ((k & M) != 0) return k & ~M;
        return switch (k) {
            case 13 -> 40;
            case 27 -> 41;
            case 8 -> 42;
            case 9 -> 43;
            case 32 -> 44;
            case 127 -> 76;
            default -> { int i = PUNCT.indexOf(k); yield i >= 0 ? 45 + i : 0; }
        };
    }

    public static String name(int k) {
        if (k >= 'a' && k <= 'z') return String.valueOf((char) (k - 32));
        if (k > 32 && k < 127) return String.valueOf((char) k);
        if ((k & M) == 0) {
            return switch (k) {
                case 13 -> "Return";
                case 27 -> "Escape";
                case 8 -> "Backspace";
                case 9 -> "Tab";
                case 32 -> "Space";
                case 127 -> "Delete";
                default -> k > 127 ? new String(Character.toChars(k)).toUpperCase() : "";
            };
        }
        int sc = k & ~M;
        if (sc >= 58 && sc <= 69) return "F" + (sc - 57);
        if (sc >= 104 && sc <= 115) return "F" + (sc - 91);
        if (sc >= 89 && sc <= 97) return "Keypad " + (sc - 88);
        return switch (sc) {
            case 57 -> "CapsLock";
            case 70 -> "PrintScreen";
            case 71 -> "ScrollLock";
            case 72 -> "Pause";
            case 73 -> "Insert";
            case 74 -> "Home";
            case 75 -> "PageUp";
            case 77 -> "End";
            case 78 -> "PageDown";
            case 79 -> "Right";
            case 80 -> "Left";
            case 81 -> "Down";
            case 82 -> "Up";
            case 83 -> "Numlock";
            case 84 -> "Keypad /";
            case 85 -> "Keypad *";
            case 86 -> "Keypad -";
            case 87 -> "Keypad +";
            case 88 -> "Keypad Enter";
            case 98 -> "Keypad 0";
            case 99 -> "Keypad .";
            case 224 -> "Left Ctrl";
            case 225 -> "Left Shift";
            case 226 -> "Left Alt";
            case 227 -> "Left GUI";
            case 228 -> "Right Ctrl";
            case 229 -> "Right Shift";
            case 230 -> "Right Alt";
            case 231 -> "Right GUI";
            default -> "";
        };
    }
}
