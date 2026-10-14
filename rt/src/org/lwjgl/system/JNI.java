package org.lwjgl.system;

import bc.rt.Nat;

public final class JNI {
    private JNI() {}

    public static byte invokePB(long a0, long a1) { return (byte) Nat.c(a1, new long[]{a0}); }
    public static short invokeC(long a0) { return (short) Nat.c(a0, new long[]{}); }
    public static short invokeC(int a0, long a1) { return (short) Nat.c(a1, new long[]{a0}); }
    public static short invokePC(long a0, long a1) { return (short) Nat.c(a1, new long[]{a0}); }
    public static short invokeCC(int a0, short a1, long a2) { return (short) Nat.c(a2, new long[]{a0, a1}); }
    public static short invokeCC(short a0, boolean a1, long a2) { return (short) Nat.c(a2, new long[]{a0, (a1 ? 1L : 0L)}); }
    public static short invokePC(long a0, int a1, long a2) { return (short) Nat.c(a2, new long[]{a0, a1}); }
    public static short invokeJC(int a0, int a1, long a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static short invokeCUC(short a0, byte a1, long a2) { return (short) Nat.c(a2, new long[]{a0, a1}); }
    public static short invokePCC(long a0, short a1, long a2) { return (short) Nat.c(a2, new long[]{a0, a1}); }
    public static short invokeCCC(short a0, short a1, boolean a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}); }
    public static short invokePCC(int a0, long a1, short a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static short invokePCC(long a0, int a1, short a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static short invokeUPC(byte a0, long a1, boolean a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}); }
    public static short invokePCC(long a0, int a1, int a2, short a3, long a4) { return (short) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static short invokeCJC(int a0, boolean a1, short a2, int a3, long a4, long a5) { return (short) Nat.c(a5, new long[]{a0, (a1 ? 1L : 0L), a2, a3, a4}); }
    public static short invokeCPCC(short a0, long a1, short a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static short invokeCPPC(short a0, long a1, long a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static short invokePPCC(long a0, long a1, short a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static short invokeCCJC(short a0, short a1, int a2, long a3, long a4) { return (short) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static short invokePCCC(long a0, short a1, short a2, int a3, int a4, long a5) { return (short) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static short invokeCCCCC(short a0, short a1, short a2, short a3, long a4) { return (short) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static short invokePJUPC(long a0, long a1, byte a2, long a3, long a4) { return (short) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static short invokePCCCCC(long a0, short a1, short a2, short a3, short a4, long a5) { return (short) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static short invokeCCJPJC(short a0, boolean a1, short a2, int a3, long a4, long a5, long a6, long a7) { return (short) Nat.c(a7, new long[]{a0, (a1 ? 1L : 0L), a2, a3, a4, a5, a6}); }
    public static short invokeCCCJPJC(short a0, short a1, short a2, boolean a3, int a4, long a5, long a6, long a7, long a8) { return (short) Nat.c(a8, new long[]{a0, a1, a2, (a3 ? 1L : 0L), a4, a5, a6, a7}); }
    public static short invokeCCCJPJC(short a0, short a1, boolean a2, short a3, int a4, long a5, long a6, long a7, long a8) { return (short) Nat.c(a8, new long[]{a0, a1, (a2 ? 1L : 0L), a3, a4, a5, a6, a7}); }
    public static double invokeD(long a0) { return Double.longBitsToDouble(Nat.c(a0, new long[]{})); }
    public static double invokeD(int a0, long a1) { return Double.longBitsToDouble(Nat.c(a1, new long[]{a0})); }
    public static double invokePD(long a0, long a1) { return Double.longBitsToDouble(Nat.c(a1, new long[]{a0})); }
    public static double invokePD(long a0, int a1, long a2) { return Double.longBitsToDouble(Nat.c(a2, new long[]{a0, a1})); }
    public static double invokePD(long a0, int a1, int a2, long a3) { return Double.longBitsToDouble(Nat.c(a3, new long[]{a0, a1, a2})); }
    public static double invokePPD(long a0, long a1, long a2) { return Double.longBitsToDouble(Nat.c(a2, new long[]{a0, a1})); }
    public static float invokeF(long a0) { return Float.intBitsToFloat((int) Nat.c(a0, new long[]{})); }
    public static float invokeF(int a0, long a1) { return Float.intBitsToFloat((int) Nat.c(a1, new long[]{a0})); }
    public static float invokePF(long a0, long a1) { return Float.intBitsToFloat((int) Nat.c(a1, new long[]{a0})); }
    public static float invokePF(float a0, long a1, long a2) { return Float.intBitsToFloat((int) Nat.c(a2, new long[]{Float.floatToRawIntBits(a0), a1})); }
    public static float invokePF(long a0, int a1, long a2) { return Float.intBitsToFloat((int) Nat.c(a2, new long[]{a0, a1})); }
    public static float invokePF(float a0, float a1, long a2, long a3) { return Float.intBitsToFloat((int) Nat.c(a3, new long[]{Float.floatToRawIntBits(a0), Float.floatToRawIntBits(a1), a2})); }
    public static float invokePF(int a0, long a1, float a2, long a3) { return Float.intBitsToFloat((int) Nat.c(a3, new long[]{a0, a1, Float.floatToRawIntBits(a2)})); }
    public static float invokePF(long a0, float a1, float a2, long a3) { return Float.intBitsToFloat((int) Nat.c(a3, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2)})); }
    public static float invokePF(long a0, int a1, int a2, long a3) { return Float.intBitsToFloat((int) Nat.c(a3, new long[]{a0, a1, a2})); }
    public static float invokePPF(long a0, long a1, long a2) { return Float.intBitsToFloat((int) Nat.c(a2, new long[]{a0, a1})); }
    public static float invokePPF(long a0, int a1, long a2, long a3) { return Float.intBitsToFloat((int) Nat.c(a3, new long[]{a0, a1, a2})); }
    public static float invokePPF(long a0, float a1, long a2, int a3, long a4) { return Float.intBitsToFloat((int) Nat.c(a4, new long[]{a0, Float.floatToRawIntBits(a1), a2, a3})); }
    public static int invokeI(long a0) { return (int) Nat.c(a0, new long[]{}); }
    public static int invokeI(int a0, long a1) { return (int) Nat.c(a1, new long[]{a0}); }
    public static int invokeI(int a0, float a1, long a2) { return (int) Nat.c(a2, new long[]{a0, Float.floatToRawIntBits(a1)}); }
    public static int invokeI(int a0, int a1, long a2) { return (int) Nat.c(a2, new long[]{a0, a1}); }
    public static int invokeI(int a0, boolean a1, long a2) { return (int) Nat.c(a2, new long[]{a0, (a1 ? 1L : 0L)}); }
    public static int invokeI(int a0, int a1, int a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokeI(int a0, int a1, int a2, int a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokeI(int a0, int a1, int a2, int a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokeI(int a0, int a1, int a2, int a3, int a4, int a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokeJI(long a0, long a1) { return (int) Nat.c(a1, new long[]{a0}); }
    public static int invokePI(long a0, long a1) { return (int) Nat.c(a1, new long[]{a0}); }
    public static int invokeUI(byte a0, long a1) { return (int) Nat.c(a1, new long[]{a0}); }
    public static int invokeCI(int a0, short a1, long a2) { return (int) Nat.c(a2, new long[]{a0, a1}); }
    public static int invokePI(int a0, long a1, long a2) { return (int) Nat.c(a2, new long[]{a0, a1}); }
    public static int invokePI(long a0, int a1, long a2) { return (int) Nat.c(a2, new long[]{a0, a1}); }
    public static int invokePI(long a0, boolean a1, long a2) { return (int) Nat.c(a2, new long[]{a0, (a1 ? 1L : 0L)}); }
    public static int invokeCI(int a0, short a1, boolean a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}); }
    public static int invokePI(long a0, int a1, int a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokePI(long a0, int a1, boolean a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}); }
    public static int invokePI(long a0, boolean a1, boolean a2, long a3) { return (int) Nat.c(a3, new long[]{a0, (a1 ? 1L : 0L), (a2 ? 1L : 0L)}); }
    public static int invokePI(long a0, int a1, int a2, int a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePI(long a0, int a1, int a2, int a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePI(long a0, int a1, int a2, int a3, int a4, int a5, int a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int invokeCPI(short a0, long a1, long a2) { return (int) Nat.c(a2, new long[]{a0, a1}); }
    public static int invokePCI(long a0, short a1, long a2) { return (int) Nat.c(a2, new long[]{a0, a1}); }
    public static int invokePJI(long a0, long a1, long a2) { return (int) Nat.c(a2, new long[]{a0, a1}); }
    public static int invokePNI(long a0, long a1, long a2) { return (int) Nat.c(a2, new long[]{a0, a1}); }
    public static int invokePPI(long a0, long a1, long a2) { return (int) Nat.c(a2, new long[]{a0, a1}); }
    public static int invokePJI(long a0, long a1, int a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokePNI(long a0, int a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokePNI(long a0, long a1, int a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokePPI(int a0, long a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokePPI(long a0, int a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokePPI(long a0, long a1, float a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, Float.floatToRawIntBits(a2)}); }
    public static int invokePPI(long a0, long a1, int a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokePPI(long a0, long a1, boolean a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}); }
    public static int invokePPI(long a0, boolean a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, (a1 ? 1L : 0L), a2}); }
    public static int invokePPI(long a0, int a1, int a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePPI(long a0, int a1, long a2, int a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePPI(long a0, int a1, long a2, boolean a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, (a3 ? 1L : 0L)}); }
    public static int invokePPI(long a0, int a1, boolean a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, (a2 ? 1L : 0L), a3}); }
    public static int invokePPI(long a0, long a1, int a2, int a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePPI(long a0, long a1, boolean a2, boolean a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, (a2 ? 1L : 0L), (a3 ? 1L : 0L)}); }
    public static int invokePPI(long a0, int a1, int a2, int a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPI(long a0, int a1, int a2, long a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPI(long a0, int a1, long a2, int a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPI(long a0, boolean a1, boolean a2, boolean a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, (a1 ? 1L : 0L), (a2 ? 1L : 0L), (a3 ? 1L : 0L), a4}); }
    public static int invokePPI(int a0, long a1, int a2, long a3, int a4, boolean a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, (a5 ? 1L : 0L)}); }
    public static int invokePPI(long a0, int a1, int a2, int a3, int a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPI(long a0, long a1, int a2, int a3, int a4, int a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPI(long a0, int a1, int a2, int a3, int a4, long a5, int a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int invokePPI(long a0, long a1, int a2, float a3, float a4, int a5, int a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), a5, a6}); }
    public static int invokeJPPI(long a0, long a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokePCPI(long a0, short a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokePNNI(long a0, long a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokePNPI(long a0, long a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokePPCI(long a0, long a1, short a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokePPJI(long a0, long a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokePPNI(long a0, long a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokePPPI(long a0, long a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int invokePNPI(long a0, long a1, int a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePNPI(long a0, long a1, long a2, int a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePPNI(long a0, int a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePPPI(int a0, long a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePPPI(long a0, int a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePPPI(long a0, long a1, int a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePPPI(long a0, long a1, long a2, int a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePNNI(long a0, long a1, long a2, int a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPI(long a0, int a1, int a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPI(long a0, int a1, long a2, int a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPI(long a0, int a1, long a2, long a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPI(long a0, int a1, long a2, boolean a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, (a3 ? 1L : 0L), a4}); }
    public static int invokePPPI(long a0, long a1, int a2, int a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPI(long a0, long a1, int a2, long a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPI(long a0, long a1, long a2, int a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPI(long a0, int a1, int a2, int a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPI(long a0, int a1, long a2, int a3, long a4, int a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPI(long a0, int a1, long a2, long a3, int a4, int a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPI(long a0, long a1, int a2, long a3, int a4, int a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPI(long a0, int a1, int a2, int a3, int a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int invokePPPI(long a0, int a1, int a2, int a3, long a4, long a5, int a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int invokePPPI(long a0, int a1, int a2, long a3, int a4, long a5, int a6, int a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int invokeCPCUI(short a0, long a1, short a2, byte a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePNPPI(long a0, long a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePPNNI(long a0, long a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePPNPI(long a0, long a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePPPNI(long a0, long a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePPPPI(long a0, long a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePUUUI(long a0, byte a1, byte a2, byte a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int invokePNNPI(long a0, long a1, int a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPNI(long a0, long a1, int a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPPI(long a0, int a1, long a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPPI(long a0, long a1, int a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPPI(long a0, long a1, long a2, int a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPPI(long a0, long a1, long a2, long a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPPI(long a0, int a1, int a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPPI(long a0, long a1, int a2, int a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPPI(long a0, long a1, int a2, long a3, int a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPPI(long a0, long a1, int a2, long a3, long a4, int a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPPI(long a0, long a1, long a2, int a3, int a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPPI(long a0, long a1, long a2, long a3, int a4, int a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPPI(long a0, int a1, int a2, int a3, long a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int invokePPPPI(long a0, int a1, int a2, long a3, long a4, long a5, int a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int invokePPPPI(long a0, int a1, long a2, int a3, long a4, int a5, long a6, int a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int invokePPPPI(long a0, long a1, long a2, int a3, int a4, int a5, long a6, int a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int invokePPPPI(long a0, long a1, long a2, long a3, int a4, int a5, int a6, int a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int invokePPPPI(long a0, int a1, int a2, long a3, int a4, long a5, long a6, int a7, int a8, long a9) { return (int) Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static int invokePNNPPI(long a0, long a1, long a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPNNPI(long a0, long a1, long a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPNNI(long a0, long a1, long a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPNPI(long a0, long a1, long a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPPNI(long a0, long a1, long a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPPPPI(long a0, long a1, long a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePPUUUI(long a0, long a1, byte a2, byte a3, byte a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePUUUUI(long a0, byte a1, byte a2, byte a3, byte a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int invokePJPPNI(long a0, int a1, long a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPNPPI(long a0, int a1, long a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPNPPI(long a0, long a1, long a2, int a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPNJI(long a0, int a1, long a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPNNI(long a0, int a1, long a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPNPI(long a0, int a1, long a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPPNI(long a0, int a1, long a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPPNI(long a0, long a1, int a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPPPI(int a0, long a1, long a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPPPI(long a0, int a1, long a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPPPI(long a0, long a1, int a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPPPI(long a0, long a1, long a2, int a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPPPI(long a0, long a1, long a2, long a3, long a4, int a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePNPPPI(long a0, int a1, int a2, long a3, long a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int invokePPPPPI(long a0, long a1, int a2, int a3, long a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int invokePPPPPI(long a0, long a1, int a2, long a3, int a4, long a5, int a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int invokePPPPPI(long a0, long a1, int a2, long a3, long a4, int a5, int a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int invokePPPPPI(long a0, long a1, long a2, int a3, long a4, int a5, int a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int invokePPPPPI(long a0, long a1, long a2, int a3, int a4, int a5, float a6, long a7, long a8, long a9) { return (int) Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, Float.floatToRawIntBits(a6), a7, a8}); }
    public static int invokePPPPNPI(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPPPPPI(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePPUUUUI(long a0, long a1, byte a2, byte a3, byte a4, byte a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int invokePJJJJPI(long a0, long a1, long a2, long a3, long a4, int a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int invokePPNPPPI(long a0, int a1, long a2, long a3, long a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int invokePPPPPPI(long a0, int a1, long a2, long a3, long a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int invokePPPPPPI(long a0, long a1, long a2, long a3, long a4, long a5, int a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int invokePNNPPPI(long a0, long a1, long a2, int a3, int a4, long a5, long a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int invokePPPPPPI(int a0, int a1, long a2, long a3, long a4, long a5, long a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int invokePPPPPPI(long a0, int a1, int a2, long a3, long a4, long a5, long a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int invokePPPPPPI(long a0, long a1, int a2, long a3, int a4, long a5, int a6, long a7, int a8, long a9, long a10) { return (int) Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static int invokePPPPPPPI(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int invokePPPPPPPI(long a0, int a1, int a2, long a3, long a4, long a5, long a6, long a7, long a8, long a9) { return (int) Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static int invokePPPPPPPI(long a0, long a1, long a2, int a3, long a4, float a5, float a6, long a7, long a8, long a9, long a10) { return (int) Nat.c(a10, new long[]{a0, a1, a2, a3, a4, Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), a7, a8, a9}); }
    public static int invokePPPPPPPI(long a0, long a1, long a2, int a3, int a4, long a5, long a6, int a7, long a8, int a9, long a10, int a11, long a12) { return (int) Nat.c(a12, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11}); }
    public static int invokePPPPPPPPI(long a0, int a1, int a2, long a3, long a4, long a5, long a6, long a7, long a8, long a9, long a10) { return (int) Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static long invokeJ(long a0) { return Nat.c(a0, new long[]{}); }
    public static long invokeJ(int a0, int a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokePJ(long a0, long a1) { return Nat.c(a1, new long[]{a0}); }
    public static long invokePJ(long a0, int a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokePJ(long a0, int a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePJJ(long a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokePPJ(long a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokePJJ(int a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePJJ(long a0, int a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePJJ(long a0, long a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePPJ(long a0, long a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokeN(int a0, long a1) { return Nat.c(a1, new long[]{a0}); }
    public static long invokeNN(long a0, long a1) { return Nat.c(a1, new long[]{a0}); }
    public static long invokePN(long a0, long a1) { return Nat.c(a1, new long[]{a0}); }
    public static long invokePN(long a0, int a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokeNNN(long a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokePPN(long a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokeNNN(int a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokeNNNN(long a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePNPN(long a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePNPN(long a0, long a1, long a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePPNN(long a0, int a1, int a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePNPNN(long a0, long a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePNPNPN(long a0, long a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, long a9, long a10, long a11, long a12) { return Nat.c(a12, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11}); }
    public static long invokeP(long a0) { return Nat.c(a0, new long[]{}); }
    public static long invokeP(int a0, long a1) { return Nat.c(a1, new long[]{a0}); }
    public static long invokeP(boolean a0, long a1) { return Nat.c(a1, new long[]{(a0 ? 1L : 0L)}); }
    public static long invokeP(int a0, int a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokeP(int a0, int a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokeCP(short a0, long a1) { return Nat.c(a1, new long[]{a0}); }
    public static long invokeJP(long a0, long a1) { return Nat.c(a1, new long[]{a0}); }
    public static long invokePP(long a0, long a1) { return Nat.c(a1, new long[]{a0}); }
    public static long invokePP(int a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokePP(long a0, double a1, long a2) { return Nat.c(a2, new long[]{a0, Double.doubleToRawLongBits(a1)}); }
    public static long invokePP(long a0, float a1, long a2) { return Nat.c(a2, new long[]{a0, Float.floatToRawIntBits(a1)}); }
    public static long invokePP(long a0, int a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokePP(long a0, boolean a1, long a2) { return Nat.c(a2, new long[]{a0, (a1 ? 1L : 0L)}); }
    public static long invokePP(int a0, int a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePP(int a0, boolean a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, (a1 ? 1L : 0L), a2}); }
    public static long invokePP(long a0, int a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePP(int a0, int a1, int a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePP(long a0, int a1, int a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePP(int a0, int a1, int a2, long a3, int a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePP(long a0, float a1, int a2, float a3, int a4, long a5) { return Nat.c(a5, new long[]{a0, Float.floatToRawIntBits(a1), a2, Float.floatToRawIntBits(a3), a4}); }
    public static long invokePP(long a0, int a1, int a2, int a3, int a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePP(long a0, int a1, int a2, int a3, int a4, int a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokeCCP(short a0, short a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokeJPP(long a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokePJP(long a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokePNP(long a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokePPP(long a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokePUP(long a0, byte a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long invokeCPP(int a0, short a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePCP(long a0, short a1, boolean a2, long a3) { return Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}); }
    public static long invokePJP(long a0, int a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePJP(long a0, long a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePPP(int a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePPP(long a0, int a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePPP(long a0, long a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePPP(long a0, long a1, boolean a2, long a3) { return Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}); }
    public static long invokePJP(long a0, int a1, int a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePPP(long a0, int a1, int a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePPP(long a0, int a1, long a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePPP(long a0, long a1, int a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePPP(long a0, long a1, boolean a2, boolean a3, long a4) { return Nat.c(a4, new long[]{a0, a1, (a2 ? 1L : 0L), (a3 ? 1L : 0L)}); }
    public static long invokePPP(long a0, boolean a1, boolean a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, (a1 ? 1L : 0L), (a2 ? 1L : 0L), a3}); }
    public static long invokePPP(long a0, int a1, int a2, int a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePPP(long a0, int a1, long a2, int a3, int a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePJP(long a0, int a1, int a2, int a3, int a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPP(int a0, int a1, int a2, int a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPP(long a0, long a1, int a2, int a3, int a4, int a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePUP(long a0, int a1, byte a2, int a3, boolean a4, boolean a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, (a4 ? 1L : 0L), (a5 ? 1L : 0L)}); }
    public static long invokePPP(int a0, int a1, int a2, int a3, long a4, int a5, long a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long invokeCCPP(short a0, short a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokeCPCP(short a0, long a1, short a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePJJP(long a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePPJP(long a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePPPP(long a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePPUP(long a0, long a1, byte a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long invokePPPP(int a0, long a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePPPP(long a0, int a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePPPP(long a0, long a1, int a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePPPP(long a0, long a1, long a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePPPP(long a0, long a1, boolean a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, (a2 ? 1L : 0L), a3}); }
    public static long invokePPUP(long a0, long a1, int a2, byte a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePPPP(int a0, int a1, long a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePPPP(long a0, int a1, int a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePPPP(long a0, long a1, int a2, int a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePPPP(long a0, long a1, int a2, long a3, int a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePPPP(long a0, long a1, long a2, int a3, int a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokeJPPP(int a0, int a1, int a2, long a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPPP(long a0, int a1, long a2, long a3, int a4, int a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPPP(long a0, long a1, int a2, int a3, int a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPPP(long a0, long a1, int a2, int a3, long a4, int a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPPP(int a0, int a1, int a2, long a3, long a4, int a5, long a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long invokePPPP(long a0, long a1, int a2, int a3, long a4, int a5, int a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long invokePBPPP(long a0, byte a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePNNPP(long a0, long a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePPJPP(long a0, long a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePPNNP(long a0, long a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePPPPP(long a0, long a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long invokePPPJP(int a0, long a1, long a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePPPJP(long a0, long a1, long a2, long a3, int a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePPPPP(long a0, int a1, long a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePPPPP(long a0, long a1, int a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePPPPP(long a0, long a1, long a2, int a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePPPPP(long a0, long a1, long a2, long a3, int a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePJPPP(long a0, int a1, int a2, long a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePJPPP(long a0, long a1, int a2, long a3, long a4, int a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPPJP(long a0, long a1, long a2, long a3, int a4, int a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPPPP(long a0, long a1, int a2, long a3, int a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPPPP(long a0, long a1, long a2, int a3, long a4, int a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPPPP(long a0, long a1, long a2, int a3, int a4, int a5, long a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long invokePPPPP(long a0, long a1, long a2, int a3, long a4, int a5, int a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long invokePPPPP(long a0, long a1, long a2, long a3, int a4, int a5, int a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long invokePPPPP(int a0, int a1, int a2, long a3, long a4, long a5, int a6, long a7, long a8) { return Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static long invokePJPJPP(long a0, long a1, long a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePNNNPP(long a0, long a1, long a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePPBPPP(long a0, long a1, byte a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokePPPPPP(long a0, long a1, long a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long invokeCCCUJP(short a0, short a1, short a2, byte a3, int a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPPJPP(long a0, long a1, long a2, long a3, long a4, int a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPPPNP(long a0, long a1, int a2, long a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPPPPP(long a0, long a1, long a2, int a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPPPPP(long a0, long a1, long a2, long a3, int a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPPPPP(long a0, long a1, long a2, long a3, long a4, int a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPJPPP(long a0, long a1, long a2, int a3, int a4, long a5, long a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long invokePPPPPP(long a0, long a1, long a2, int a3, long a4, int a5, long a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long invokePPPPPP(long a0, long a1, long a2, long a3, int a4, long a5, int a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long invokePPPPPP(long a0, long a1, long a2, long a3, long a4, int a5, int a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long invokePPPPPP(long a0, long a1, long a2, long a3, long a4, int a5, int a6, int a7, int a8, long a9) { return Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static long invokePPJJPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePSSCCPP(long a0, short a1, short a2, short a3, short a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long invokePPPPPPP(long a0, long a1, long a2, int a3, long a4, long a5, long a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long invokePPPPPPP(long a0, long a1, long a2, long a3, int a4, long a5, long a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long invokePPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, int a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long invokePPPPPPP(long a0, long a1, int a2, int a3, long a4, long a5, long a6, long a7, long a8) { return Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static long invokePPPPPPP(long a0, long a1, long a2, int a3, long a4, int a5, long a6, long a7, long a8) { return Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static long invokePPPPPPP(long a0, long a1, long a2, long a3, int a4, long a5, int a6, long a7, long a8) { return Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static long invokePPPPPPP(long a0, long a1, long a2, long a3, long a4, int a5, long a6, int a7, long a8) { return Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static long invokePPPPPPP(long a0, long a1, long a2, long a3, int a4, long a5, int a6, long a7, int a8, int a9, long a10) { return Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static long invokePPPPPPP(long a0, long a1, long a2, long a3, long a4, int a5, long a6, int a7, int a8, int a9, long a10) { return Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static long invokePPPPPPPP(long a0, long a1, long a2, long a3, int a4, long a5, long a6, long a7, long a8) { return Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static long invokePPPPPJPP(long a0, long a1, long a2, long a3, long a4, int a5, long a6, int a7, long a8, long a9) { return Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static long invokePPPPPPPP(long a0, long a1, long a2, long a3, int a4, long a5, long a6, long a7, int a8, long a9) { return Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static long invokePPPPPPPP(long a0, long a1, long a2, long a3, long a4, int a5, long a6, int a7, long a8, int a9, long a10) { return Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static long invokePPPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7, long a8) { return Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static long invokePPPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6, int a7, long a8, long a9) { return Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static long invokePPPPPPPPP(long a0, long a1, long a2, long a3, int a4, long a5, long a6, long a7, int a8, long a9, long a10) { return Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static long invokePPPPJJPPP(long a0, long a1, long a2, long a3, int a4, long a5, int a6, long a7, int a8, long a9, long a10, long a11) { return Nat.c(a11, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10}); }
    public static long invokePPPPPJJPP(long a0, long a1, long a2, long a3, long a4, int a5, long a6, int a7, long a8, int a9, long a10, long a11) { return Nat.c(a11, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10}); }
    public static long invokePPPPPJPPP(long a0, long a1, long a2, long a3, long a4, int a5, long a6, int a7, long a8, int a9, long a10, long a11) { return Nat.c(a11, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10}); }
    public static long invokePPPPPPPPP(long a0, long a1, long a2, long a3, long a4, int a5, long a6, int a7, long a8, int a9, long a10, long a11) { return Nat.c(a11, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10}); }
    public static long invokePPPPPJPPP(long a0, int a1, long a2, long a3, long a4, long a5, int a6, int a7, long a8, int a9, long a10, long a11, long a12) { return Nat.c(a12, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11}); }
    public static long invokePPPPPJPPP(long a0, int a1, long a2, long a3, long a4, long a5, int a6, int a7, long a8, int a9, int a10, long a11, long a12, long a13) { return Nat.c(a13, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12}); }
    public static long invokePPPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6, int a7, long a8, int a9, int a10, int a11, int a12, int a13, long a14) { return Nat.c(a14, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13}); }
    public static long invokePPPPPPPPPP(long a0, long a1, long a2, long a3, long a4, int a5, long a6, long a7, long a8, long a9, long a10) { return Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static long invokePPPPPJJJPP(long a0, long a1, long a2, long a3, long a4, int a5, long a6, long a7, long a8, int a9, long a10, long a11) { return Nat.c(a11, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10}); }
    public static long invokePPPPPPPPPP(long a0, long a1, long a2, long a3, int a4, long a5, long a6, long a7, long a8, int a9, long a10, long a11) { return Nat.c(a11, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10}); }
    public static long invokePPPPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6, int a7, long a8, int a9, long a10, int a11, long a12) { return Nat.c(a12, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11}); }
    public static long invokePPPPPJPPPP(long a0, long a1, long a2, long a3, long a4, int a5, long a6, int a7, int a8, long a9, int a10, int a11, long a12, long a13, long a14) { return Nat.c(a14, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13}); }
    public static long invokePPPPPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7, long a8, long a9, long a10) { return Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static long invokePPPPPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6, int a7, long a8, int a9, long a10, long a11, int a12, long a13) { return Nat.c(a13, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12}); }
    public static long invokePPPPPJPPPPPP(long a0, long a1, long a2, long a3, int a4, long a5, long a6, int a7, int a8, long a9, long a10, long a11, long a12, long a13, long a14) { return Nat.c(a14, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13}); }
    public static long invokePPPPPJPPPPPP(long a0, long a1, long a2, long a3, long a4, int a5, long a6, int a7, int a8, long a9, long a10, int a11, int a12, long a13, long a14, long a15, long a16) { return Nat.c(a16, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15}); }
    public static long invokePPPPPPPPPPPPP(long a0, int a1, long a2, long a3, long a4, int a5, long a6, long a7, int a8, long a9, long a10, int a11, int a12, int a13, int a14, long a15, long a16, long a17, long a18, long a19) { return Nat.c(a19, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18}); }
    public static long invokePPPPPJPPPPPPPP(long a0, long a1, long a2, long a3, int a4, long a5, long a6, int a7, long a8, long a9, int a10, long a11, long a12, long a13, long a14, long a15, long a16) { return Nat.c(a16, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15}); }
    public static long invokePPPPPJJPPPPPPP(long a0, long a1, long a2, long a3, long a4, int a5, long a6, int a7, long a8, int a9, long a10, long a11, int a12, long a13, long a14, long a15, long a16, long a17) { return Nat.c(a17, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16}); }
    public static short invokePS(long a0, int a1, long a2) { return (short) Nat.c(a2, new long[]{a0, a1}); }
    public static byte invokeU(int a0, long a1) { return (byte) Nat.c(a1, new long[]{a0}); }
    public static byte invokePU(long a0, int a1, long a2) { return (byte) Nat.c(a2, new long[]{a0, a1}); }
    public static byte invokeUPU(byte a0, long a1, long a2) { return (byte) Nat.c(a2, new long[]{a0, a1}); }
    public static void invokeV(long a0) { Nat.c(a0, new long[]{}); }
    public static void invokeV(double a0, long a1) { Nat.c(a1, new long[]{Double.doubleToRawLongBits(a0)}); }
    public static void invokeV(float a0, long a1) { Nat.c(a1, new long[]{Float.floatToRawIntBits(a0)}); }
    public static void invokeV(int a0, long a1) { Nat.c(a1, new long[]{a0}); }
    public static void invokeV(boolean a0, long a1) { Nat.c(a1, new long[]{(a0 ? 1L : 0L)}); }
    public static void invokeV(int a0, float a1, long a2) { Nat.c(a2, new long[]{a0, Float.floatToRawIntBits(a1)}); }
    public static void invokeV(int a0, int a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokeV(int a0, boolean a1, long a2) { Nat.c(a2, new long[]{a0, (a1 ? 1L : 0L)}); }
    public static void invokeV(int a0, int a1, double a2, long a3) { Nat.c(a3, new long[]{a0, a1, Double.doubleToRawLongBits(a2)}); }
    public static void invokeV(int a0, int a1, float a2, long a3) { Nat.c(a3, new long[]{a0, a1, Float.floatToRawIntBits(a2)}); }
    public static void invokeV(int a0, int a1, int a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokeV(int a0, float a1, float a2, float a3, long a4) { Nat.c(a4, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3)}); }
    public static void invokeV(int a0, int a1, int a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokeV(int a0, int a1, double a2, double a3, double a4, long a5) { Nat.c(a5, new long[]{a0, a1, Double.doubleToRawLongBits(a2), Double.doubleToRawLongBits(a3), Double.doubleToRawLongBits(a4)}); }
    public static void invokeV(int a0, int a1, float a2, float a3, float a4, long a5) { Nat.c(a5, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4)}); }
    public static void invokeV(int a0, int a1, int a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokeCV(short a0, long a1) { Nat.c(a1, new long[]{a0}); }
    public static void invokeJV(long a0, long a1) { Nat.c(a1, new long[]{a0}); }
    public static void invokePV(long a0, long a1) { Nat.c(a1, new long[]{a0}); }
    public static void invokeUV(byte a0, long a1) { Nat.c(a1, new long[]{a0}); }
    public static void invokeCV(int a0, short a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokeCV(short a0, int a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokeCV(short a0, boolean a1, long a2) { Nat.c(a2, new long[]{a0, (a1 ? 1L : 0L)}); }
    public static void invokeJV(int a0, long a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokeJV(long a0, int a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokeNV(int a0, long a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokePV(int a0, long a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokePV(long a0, float a1, long a2) { Nat.c(a2, new long[]{a0, Float.floatToRawIntBits(a1)}); }
    public static void invokePV(long a0, int a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokePV(long a0, boolean a1, long a2) { Nat.c(a2, new long[]{a0, (a1 ? 1L : 0L)}); }
    public static void invokeUV(byte a0, int a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokeUV(byte a0, boolean a1, long a2) { Nat.c(a2, new long[]{a0, (a1 ? 1L : 0L)}); }
    public static void invokeCV(short a0, int a1, int a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokeJV(int a0, int a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePV(int a0, int a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePV(int a0, long a1, boolean a2, long a3) { Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}); }
    public static void invokePV(long a0, double a1, double a2, long a3) { Nat.c(a3, new long[]{a0, Double.doubleToRawLongBits(a1), Double.doubleToRawLongBits(a2)}); }
    public static void invokePV(long a0, float a1, float a2, long a3) { Nat.c(a3, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2)}); }
    public static void invokePV(long a0, int a1, double a2, long a3) { Nat.c(a3, new long[]{a0, a1, Double.doubleToRawLongBits(a2)}); }
    public static void invokePV(long a0, int a1, float a2, long a3) { Nat.c(a3, new long[]{a0, a1, Float.floatToRawIntBits(a2)}); }
    public static void invokePV(long a0, int a1, int a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePV(long a0, int a1, boolean a2, long a3) { Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}); }
    public static void invokePV(int a0, int a1, int a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePV(long a0, float a1, float a2, float a3, long a4) { Nat.c(a4, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3)}); }
    public static void invokePV(long a0, float a1, float a2, int a3, long a4) { Nat.c(a4, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), a3}); }
    public static void invokePV(long a0, int a1, int a2, double a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, Double.doubleToRawLongBits(a3)}); }
    public static void invokePV(long a0, int a1, int a2, float a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3)}); }
    public static void invokePV(long a0, int a1, int a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePV(int a0, int a1, long a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePV(long a0, int a1, float a2, float a3, float a4, long a5) { Nat.c(a5, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4)}); }
    public static void invokePV(long a0, int a1, int a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokeUV(byte a0, float a1, float a2, float a3, float a4, long a5) { Nat.c(a5, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4)}); }
    public static void invokePV(int a0, int a1, int a2, int a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePV(int a0, int a1, int a2, int a3, long a4, boolean a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, (a5 ? 1L : 0L)}); }
    public static void invokePV(long a0, int a1, int a2, double a3, double a4, double a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, Double.doubleToRawLongBits(a3), Double.doubleToRawLongBits(a4), Double.doubleToRawLongBits(a5)}); }
    public static void invokePV(long a0, int a1, int a2, float a3, float a4, float a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5)}); }
    public static void invokePV(long a0, int a1, int a2, int a3, int a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePV(long a0, int a1, int a2, int a3, int a4, boolean a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, (a5 ? 1L : 0L)}); }
    public static void invokePV(int a0, int a1, int a2, int a3, int a4, int a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void invokePV(long a0, float a1, float a2, float a3, float a4, float a5, float a6, long a7) { Nat.c(a7, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6)}); }
    public static void invokeCCV(short a0, short a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokeCPV(short a0, long a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokePCV(long a0, short a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokePJV(long a0, long a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokePNV(long a0, long a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokePPV(long a0, long a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokePUV(long a0, byte a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokeUPV(byte a0, long a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void invokeCPV(short a0, int a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokeCPV(short a0, long a1, int a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokeJPV(boolean a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{(a0 ? 1L : 0L), a1, a2}); }
    public static void invokePCV(long a0, int a1, short a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePCV(long a0, short a1, boolean a2, long a3) { Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}); }
    public static void invokePJV(int a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePJV(long a0, int a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePJV(long a0, long a1, int a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePPV(int a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePPV(long a0, int a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePPV(long a0, long a1, float a2, long a3) { Nat.c(a3, new long[]{a0, a1, Float.floatToRawIntBits(a2)}); }
    public static void invokePPV(long a0, long a1, int a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePPV(long a0, long a1, boolean a2, long a3) { Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}); }
    public static void invokeUCV(byte a0, short a1, int a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePBV(long a0, int a1, int a2, byte a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePCV(long a0, int a1, int a2, short a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePCV(long a0, short a1, int a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePJV(long a0, int a1, int a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePPV(int a0, int a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePPV(int a0, long a1, long a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePPV(long a0, int a1, int a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePPV(long a0, int a1, long a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePPV(long a0, int a1, long a2, boolean a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, (a3 ? 1L : 0L)}); }
    public static void invokePPV(long a0, long a1, int a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePSV(long a0, int a1, int a2, short a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePUV(long a0, int a1, int a2, byte a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokeUCV(byte a0, short a1, int a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokeUPV(byte a0, long a1, int a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePCV(long a0, short a1, int a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPV(int a0, int a1, int a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPV(long a0, float a1, long a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, Float.floatToRawIntBits(a1), a2, a3, a4}); }
    public static void invokePPV(long a0, int a1, int a2, int a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPV(long a0, long a1, int a2, float a3, float a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4)}); }
    public static void invokePPV(long a0, long a1, int a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPV(int a0, int a1, int a2, int a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPV(long a0, int a1, int a2, int a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPV(long a0, int a1, int a2, long a3, int a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPV(long a0, long a1, float a2, float a3, float a4, float a5, long a6) { Nat.c(a6, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5)}); }
    public static void invokePPV(int a0, int a1, int a2, int a3, int a4, long a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void invokePPV(long a0, int a1, int a2, int a3, int a4, int a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void invokePPV(long a0, int a1, int a2, int a3, int a4, long a5, boolean a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, (a6 ? 1L : 0L)}); }
    public static void invokePPV(long a0, long a1, int a2, int a3, int a4, int a5, int a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void invokePPV(long a0, long a1, float a2, float a3, float a4, float a5, float a6, float a7, long a8) { Nat.c(a8, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), Float.floatToRawIntBits(a7)}); }
    public static void invokeCCPV(short a0, short a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokeCPCV(short a0, long a1, short a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokeCPPV(short a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokeJPPV(long a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePJPV(long a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePNNV(long a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePNPV(long a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePPNV(long a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokePPPV(long a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void invokeCCUV(short a0, short a1, int a2, byte a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokeCSSV(short a0, short a1, short a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePJPV(long a0, long a1, long a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePPJV(long a0, int a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePPPV(int a0, long a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePPPV(long a0, int a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePPPV(long a0, long a1, int a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePPPV(long a0, long a1, long a2, float a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3)}); }
    public static void invokePPPV(long a0, long a1, long a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePPPV(long a0, long a1, long a2, boolean a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, (a3 ? 1L : 0L)}); }
    public static void invokePUCV(long a0, byte a1, short a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokeUCCV(byte a0, short a1, short a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokeCCUV(short a0, short a1, int a2, float a3, byte a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), a4}); }
    public static void invokeJJJV(int a0, int a1, long a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePNNV(long a0, long a1, int a2, int a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPPV(float a0, long a1, float a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{Float.floatToRawIntBits(a0), a1, Float.floatToRawIntBits(a2), a3, a4}); }
    public static void invokePPPV(int a0, int a1, long a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPPV(long a0, int a1, int a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPPV(long a0, int a1, long a2, long a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPPV(long a0, long a1, int a2, int a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPPV(long a0, long a1, int a2, long a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPPV(long a0, long a1, long a2, float a3, float a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4)}); }
    public static void invokePPPV(long a0, long a1, long a2, int a3, boolean a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, (a4 ? 1L : 0L)}); }
    public static void invokePPPV(long a0, long a1, long a2, boolean a3, boolean a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, (a3 ? 1L : 0L), (a4 ? 1L : 0L)}); }
    public static void invokePUCV(long a0, byte a1, short a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePUPV(long a0, byte a1, long a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokeUCCV(byte a0, short a1, int a2, int a3, short a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokeUCUV(byte a0, short a1, byte a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokeUPCV(byte a0, long a1, int a2, int a3, short a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokeCCUV(short a0, short a1, int a2, int a3, int a4, byte a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPPV(int a0, int a1, long a2, int a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPPV(long a0, int a1, int a2, int a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPPV(long a0, int a1, long a2, int a3, long a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPPV(long a0, int a1, long a2, long a3, int a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPPV(long a0, long a1, int a2, int a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPPV(long a0, long a1, int a2, long a3, int a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPPV(long a0, long a1, long a2, int a3, boolean a4, boolean a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, (a4 ? 1L : 0L), (a5 ? 1L : 0L)}); }
    public static void invokePPPV(long a0, boolean a1, int a2, long a3, long a4, int a5, long a6) { Nat.c(a6, new long[]{a0, (a1 ? 1L : 0L), a2, a3, a4, a5}); }
    public static void invokePPPV(long a0, int a1, int a2, float a3, float a4, long a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), a5, a6}); }
    public static void invokePPPV(long a0, int a1, int a2, long a3, int a4, long a5, int a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void invokePPPV(long a0, int a1, long a2, int a3, long a4, int a5, int a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void invokePPPV(long a0, long a1, float a2, float a3, float a4, float a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), a6}); }
    public static void invokePPPV(long a0, long a1, long a2, float a3, float a4, float a5, float a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6)}); }
    public static void invokePPPV(long a0, long a1, long a2, int a3, int a4, int a5, boolean a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, (a6 ? 1L : 0L)}); }
    public static void invokePPPV(long a0, long a1, long a2, float a3, float a4, float a5, float a6, float a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), Float.floatToRawIntBits(a7)}); }
    public static void invokePPPV(long a0, long a1, float a2, float a3, float a4, float a5, float a6, float a7, long a8, long a9) { Nat.c(a9, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), Float.floatToRawIntBits(a7), a8}); }
    public static void invokePPPV(long a0, long a1, int a2, int a3, int a4, int a5, long a6, int a7, boolean a8, long a9) { Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, (a8 ? 1L : 0L)}); }
    public static void invokePPPV(long a0, long a1, long a2, float a3, float a4, float a5, float a6, float a7, float a8, long a9) { Nat.c(a9, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), Float.floatToRawIntBits(a7), Float.floatToRawIntBits(a8)}); }
    public static void invokePPPV(long a0, long a1, long a2, float a3, float a4, float a5, float a6, float a7, float a8, int a9, long a10) { Nat.c(a10, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), Float.floatToRawIntBits(a7), Float.floatToRawIntBits(a8), a9}); }
    public static void invokeCCPCV(short a0, short a1, long a2, short a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokeCCUPV(short a0, short a1, byte a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePCPCV(long a0, short a1, long a2, short a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePNPPV(long a0, long a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokePPPPV(long a0, long a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void invokeCCCUV(short a0, short a1, short a2, int a3, byte a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePCCUV(long a0, short a1, short a2, int a3, byte a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePJJPV(long a0, int a1, long a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPCPV(long a0, long a1, short a2, int a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPPCV(long a0, long a1, int a2, long a3, short a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPPPV(long a0, int a1, long a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPPPV(long a0, long a1, int a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPPPV(long a0, long a1, long a2, int a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPPPV(long a0, long a1, long a2, long a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPPPV(long a0, long a1, long a2, long a3, boolean a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, (a4 ? 1L : 0L)}); }
    public static void invokePUCCV(long a0, byte a1, short a2, short a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokeCCCUV(short a0, short a1, short a2, int a3, int a4, byte a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePJJJV(long a0, int a1, int a2, long a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPPPV(long a0, int a1, int a2, long a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPPPV(long a0, long a1, long a2, float a3, float a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), a5}); }
    public static void invokePUCCV(long a0, byte a1, short a2, int a3, int a4, short a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePUCUV(long a0, byte a1, short a2, byte a3, int a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePUPCV(long a0, byte a1, long a2, int a3, int a4, short a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokeCCCUV(short a0, short a1, short a2, int a3, int a4, int a5, byte a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void invokePCCUV(long a0, short a1, short a2, int a3, int a4, int a5, byte a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void invokePPPPV(long a0, long a1, int a2, long a3, int a4, int a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void invokePPPPV(long a0, long a1, long a2, float a3, float a4, float a5, float a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), a7}); }
    public static void invokePPPPV(long a0, long a1, long a2, int a3, int a4, int a5, float a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, Float.floatToRawIntBits(a6), a7}); }
    public static void invokePPPPV(float a0, float a1, float a2, float a3, float a4, float a5, long a6, long a7, long a8, long a9, long a10) { Nat.c(a10, new long[]{Float.floatToRawIntBits(a0), Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), a6, a7, a8, a9}); }
    public static void invokePPPPV(long a0, long a1, long a2, float a3, float a4, float a5, float a6, float a7, float a8, long a9, long a10) { Nat.c(a10, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), Float.floatToRawIntBits(a7), Float.floatToRawIntBits(a8), a9}); }
    public static void invokeCCCCCV(short a0, short a1, short a2, short a3, short a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokeCCUPPV(short a0, short a1, byte a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokeCSSCCV(short a0, short a1, short a2, short a3, short a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokeCUUCCV(short a0, byte a1, byte a2, short a3, short a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPCPPV(long a0, long a1, short a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePPPPPV(long a0, long a1, long a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void invokePCCCUV(long a0, short a1, short a2, short a3, int a4, byte a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePJPPPV(long a0, long a1, long a2, int a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPPPPV(int a0, long a1, long a2, long a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPPPPV(long a0, int a1, long a2, long a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPPPPV(long a0, long a1, int a2, long a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPPPPV(long a0, long a1, long a2, long a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePCCCUV(long a0, short a1, short a2, short a3, int a4, int a5, byte a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void invokePPPPPV(long a0, int a1, int a2, long a3, long a4, long a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void invokePPPPPV(long a0, long a1, long a2, long a3, int a4, long a5, boolean a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, (a6 ? 1L : 0L)}); }
    public static void invokeUCCCUV(byte a0, short a1, short a2, short a3, byte a4, int a5, int a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void invokePCCCCV(long a0, short a1, short a2, short a3, boolean a4, boolean a5, short a6, int a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, (a4 ? 1L : 0L), (a5 ? 1L : 0L), a6, a7}); }
    public static void invokePCCCUV(long a0, short a1, short a2, short a3, int a4, int a5, int a6, byte a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void invokePPPPPV(long a0, long a1, int a2, long a3, int a4, long a5, int a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void invokePPPPPV(long a0, long a1, int a2, long a3, long a4, int a5, int a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void invokeCCCCUV(short a0, short a1, short a2, int a3, short a4, int a5, int a6, int a7, byte a8, long a9) { Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static void invokePPPPPV(int a0, long a1, int a2, long a3, long a4, long a5, int a6, long a7, int a8, boolean a9, long a10) { Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, (a9 ? 1L : 0L)}); }
    public static void invokeCCCCPCV(short a0, short a1, short a2, short a3, long a4, short a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePPPPPPV(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void invokePCCCCUV(long a0, short a1, int a2, short a3, short a4, short a5, byte a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void invokePPPPPPV(int a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void invokePUCCCUV(long a0, byte a1, short a2, short a3, short a4, byte a5, int a6, int a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void invokePCCCCUV(long a0, short a1, short a2, short a3, int a4, short a5, int a6, int a7, int a8, byte a9, long a10) { Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static void invokePPPPPPPV(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void invokePPPPPPPV(long a0, int a1, long a2, long a3, long a4, long a5, long a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void invokeUCCCCUUV(byte a0, short a1, short a2, short a3, short a4, byte a5, byte a6, int a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void invokePPPPPPPPV(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void invokePUCCCCUUV(long a0, byte a1, short a2, short a3, short a4, short a5, byte a6, byte a7, int a8, long a9) { Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static void invokeCCUCCCCPCV(short a0, short a1, byte a2, short a3, short a4, short a5, short a6, long a7, short a8, long a9) { Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static void invokeCUCCCCCCPV(short a0, byte a1, short a2, short a3, short a4, short a5, short a6, short a7, long a8, long a9) { Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static void invokeCCUUCCCCPCV(short a0, short a1, byte a2, byte a3, short a4, short a5, short a6, short a7, long a8, short a9, long a10) { Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static void invokeCCUUUUUUUUUV(short a0, short a1, float a2, byte a3, byte a4, byte a5, byte a6, byte a7, byte a8, byte a9, byte a10, byte a11, long a12) { Nat.c(a12, new long[]{a0, a1, Float.floatToRawIntBits(a2), a3, a4, a5, a6, a7, a8, a9, a10, a11}); }
    public static void invokeCCUCCCCUCCCCCCV(short a0, short a1, byte a2, short a3, short a4, short a5, short a6, byte a7, short a8, short a9, short a10, short a11, short a12, short a13, long a14) { Nat.c(a14, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13}); }
    public static void invokePCCUCCCCUCCCCCCV(long a0, short a1, short a2, byte a3, short a4, short a5, short a6, short a7, byte a8, short a9, short a10, short a11, short a12, short a13, short a14, long a15) { Nat.c(a15, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14}); }
    public static boolean invokeZ(long a0) { return Nat.c(a0, new long[]{}) != 0; }
    public static boolean invokeZ(int a0, long a1) { return Nat.c(a1, new long[]{a0}) != 0; }
    public static boolean invokeZ(boolean a0, long a1) { return Nat.c(a1, new long[]{(a0 ? 1L : 0L)}) != 0; }
    public static boolean invokeZ(float a0, float a1, long a2) { return Nat.c(a2, new long[]{Float.floatToRawIntBits(a0), Float.floatToRawIntBits(a1)}) != 0; }
    public static boolean invokeZ(int a0, float a1, long a2) { return Nat.c(a2, new long[]{a0, Float.floatToRawIntBits(a1)}) != 0; }
    public static boolean invokeZ(int a0, int a1, long a2) { return Nat.c(a2, new long[]{a0, a1}) != 0; }
    public static boolean invokePZ(long a0, long a1) { return Nat.c(a1, new long[]{a0}) != 0; }
    public static boolean invokeJZ(long a0, int a1, long a2) { return Nat.c(a2, new long[]{a0, a1}) != 0; }
    public static boolean invokePZ(int a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}) != 0; }
    public static boolean invokePZ(long a0, float a1, long a2) { return Nat.c(a2, new long[]{a0, Float.floatToRawIntBits(a1)}) != 0; }
    public static boolean invokePZ(long a0, int a1, long a2) { return Nat.c(a2, new long[]{a0, a1}) != 0; }
    public static boolean invokePZ(long a0, boolean a1, long a2) { return Nat.c(a2, new long[]{a0, (a1 ? 1L : 0L)}) != 0; }
    public static boolean invokeJZ(long a0, int a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}) != 0; }
    public static boolean invokePZ(int a0, int a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}) != 0; }
    public static boolean invokePZ(int a0, long a1, float a2, long a3) { return Nat.c(a3, new long[]{a0, a1, Float.floatToRawIntBits(a2)}) != 0; }
    public static boolean invokePZ(int a0, long a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}) != 0; }
    public static boolean invokePZ(int a0, long a1, boolean a2, long a3) { return Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}) != 0; }
    public static boolean invokePZ(long a0, float a1, float a2, long a3) { return Nat.c(a3, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2)}) != 0; }
    public static boolean invokePZ(long a0, float a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, Float.floatToRawIntBits(a1), a2}) != 0; }
    public static boolean invokePZ(long a0, int a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}) != 0; }
    public static boolean invokePZ(long a0, int a1, boolean a2, long a3) { return Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}) != 0; }
    public static boolean invokePZ(long a0, boolean a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, (a1 ? 1L : 0L), a2}) != 0; }
    public static boolean invokePZ(long a0, float a1, float a2, float a3, long a4) { return Nat.c(a4, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3)}) != 0; }
    public static boolean invokePZ(long a0, int a1, int a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}) != 0; }
    public static boolean invokePZ(long a0, float a1, float a2, float a3, float a4, long a5) { return Nat.c(a5, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4)}) != 0; }
    public static boolean invokePZ(int a0, int a1, int a2, float a3, boolean a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), (a4 ? 1L : 0L), a5}) != 0; }
    public static boolean invokePZ(long a0, int a1, int a2, float a3, float a4, float a5, float a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6)}) != 0; }
    public static boolean invokePZ(long a0, int a1, int a2, boolean a3, float a4, float a5, float a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, (a3 ? 1L : 0L), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6)}) != 0; }
    public static boolean invokePBZ(long a0, byte a1, long a2) { return Nat.c(a2, new long[]{a0, a1}) != 0; }
    public static boolean invokePCZ(long a0, short a1, long a2) { return Nat.c(a2, new long[]{a0, a1}) != 0; }
    public static boolean invokePJZ(long a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}) != 0; }
    public static boolean invokePPZ(long a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}) != 0; }
    public static boolean invokePSZ(long a0, short a1, long a2) { return Nat.c(a2, new long[]{a0, a1}) != 0; }
    public static boolean invokePUZ(long a0, byte a1, long a2) { return Nat.c(a2, new long[]{a0, a1}) != 0; }
    public static boolean invokeUPZ(byte a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}) != 0; }
    public static boolean invokeJPZ(long a0, long a1, boolean a2, long a3) { return Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}) != 0; }
    public static boolean invokePJZ(int a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}) != 0; }
    public static boolean invokePPZ(int a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}) != 0; }
    public static boolean invokePPZ(long a0, int a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}) != 0; }
    public static boolean invokePPZ(long a0, long a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}) != 0; }
    public static boolean invokePPZ(long a0, long a1, boolean a2, long a3) { return Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}) != 0; }
    public static boolean invokePSZ(long a0, int a1, short a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}) != 0; }
    public static boolean invokePUZ(long a0, int a1, byte a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}) != 0; }
    public static boolean invokePPZ(long a0, float a1, float a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), a3}) != 0; }
    public static boolean invokePPZ(long a0, int a1, int a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}) != 0; }
    public static boolean invokePPZ(long a0, int a1, long a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}) != 0; }
    public static boolean invokePPZ(long a0, long a1, int a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}) != 0; }
    public static boolean invokePPZ(long a0, boolean a1, long a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, (a1 ? 1L : 0L), a2, a3}) != 0; }
    public static boolean invokePPZ(long a0, long a1, int a2, int a3, float a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, Float.floatToRawIntBits(a4)}) != 0; }
    public static boolean invokePPZ(long a0, long a1, boolean a2, boolean a3, boolean a4, int a5, long a6) { return Nat.c(a6, new long[]{a0, a1, (a2 ? 1L : 0L), (a3 ? 1L : 0L), (a4 ? 1L : 0L), a5}) != 0; }
    public static boolean invokePPZ(int a0, int a1, int a2, long a3, int a4, int a5, long a6, int a7, long a8) { return Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}) != 0; }
    public static boolean invokePPZ(int a0, int a1, int a2, long a3, int a4, int a5, long a6, int a7, boolean a8, long a9) { return Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, (a8 ? 1L : 0L)}) != 0; }
    public static boolean invokePPZ(int a0, int a1, int a2, int a3, int a4, long a5, int a6, int a7, int a8, int a9, long a10, int a11, long a12) { return Nat.c(a12, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11}) != 0; }
    public static boolean invokePCCZ(long a0, short a1, short a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}) != 0; }
    public static boolean invokePPPZ(long a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}) != 0; }
    public static boolean invokePCCZ(long a0, short a1, short a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}) != 0; }
    public static boolean invokePJJZ(long a0, int a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}) != 0; }
    public static boolean invokePJPZ(long a0, long a1, long a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}) != 0; }
    public static boolean invokePPPZ(int a0, long a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}) != 0; }
    public static boolean invokePPPZ(long a0, int a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}) != 0; }
    public static boolean invokePPPZ(long a0, long a1, long a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}) != 0; }
    public static boolean invokePPPZ(long a0, long a1, long a2, boolean a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, (a3 ? 1L : 0L)}) != 0; }
    public static boolean invokePPPZ(long a0, boolean a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, (a1 ? 1L : 0L), a2, a3}) != 0; }
    public static boolean invokePSSZ(long a0, int a1, short a2, short a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}) != 0; }
    public static boolean invokeCCJZ(short a0, boolean a1, short a2, int a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, (a1 ? 1L : 0L), a2, a3, a4}) != 0; }
    public static boolean invokePJPZ(long a0, int a1, long a2, long a3, int a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}) != 0; }
    public static boolean invokePPPZ(long a0, float a1, float a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), a3, a4}) != 0; }
    public static boolean invokePPPZ(long a0, long a1, long a2, boolean a3, int a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, (a3 ? 1L : 0L), a4}) != 0; }
    public static boolean invokePPPZ(long a0, long a1, int a2, long a3, int a4, boolean a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, (a5 ? 1L : 0L)}) != 0; }
    public static boolean invokePPPZ(long a0, long a1, boolean a2, boolean a3, boolean a4, int a5, boolean a6, long a7, long a8) { return Nat.c(a8, new long[]{a0, a1, (a2 ? 1L : 0L), (a3 ? 1L : 0L), (a4 ? 1L : 0L), a5, (a6 ? 1L : 0L), a7}) != 0; }
    public static boolean invokePPPJZ(long a0, long a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}) != 0; }
    public static boolean invokePPPPZ(long a0, long a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}) != 0; }
    public static boolean invokePUUUZ(long a0, byte a1, byte a2, byte a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}) != 0; }
    public static boolean invokePPPPZ(int a0, long a1, long a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}) != 0; }
    public static boolean invokePPPPZ(long a0, int a1, long a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}) != 0; }
    public static boolean invokePPPPZ(long a0, long a1, int a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}) != 0; }
    public static boolean invokePPPPZ(long a0, long a1, long a2, float a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), a4}) != 0; }
    public static boolean invokePPPPZ(long a0, long a1, long a2, long a3, int a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}) != 0; }
    public static boolean invokePPPPZ(long a0, boolean a1, long a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, (a1 ? 1L : 0L), a2, a3, a4}) != 0; }
    public static boolean invokePPPPZ(boolean a0, long a1, long a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{(a0 ? 1L : 0L), a1, a2, a3, a4}) != 0; }
    public static boolean invokePJPPZ(long a0, int a1, int a2, long a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}) != 0; }
    public static boolean invokePPPPZ(long a0, long a1, float a2, int a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, Float.floatToRawIntBits(a2), a3, a4, a5}) != 0; }
    public static boolean invokePPPPZ(long a0, long a1, long a2, int a3, long a4, int a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}) != 0; }
    public static boolean invokePPPPZ(long a0, long a1, long a2, float a3, float a4, float a5, float a6, float a7, long a8, long a9) { return Nat.c(a9, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), Float.floatToRawIntBits(a7), a8}) != 0; }
    public static boolean invokePPPPZ(long a0, long a1, int a2, int a3, int a4, int a5, float a6, int a7, long a8, long a9, long a10) { return Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, Float.floatToRawIntBits(a6), a7, a8, a9}) != 0; }
    public static boolean invokePPPPZ(long a0, long a1, long a2, float a3, float a4, float a5, float a6, float a7, long a8, float a9, long a10) { return Nat.c(a10, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), Float.floatToRawIntBits(a7), a8, Float.floatToRawIntBits(a9)}) != 0; }
    public static boolean invokePPPPPZ(long a0, long a1, long a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}) != 0; }
    public static boolean invokePPPUPZ(long a0, long a1, long a2, byte a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}) != 0; }
    public static boolean invokePUUUUZ(long a0, byte a1, byte a2, byte a3, byte a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}) != 0; }
    public static boolean invokePPPPPZ(int a0, long a1, long a2, long a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}) != 0; }
    public static boolean invokePPPPPZ(long a0, long a1, int a2, long a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}) != 0; }
    public static boolean invokePPPPPZ(long a0, long a1, long a2, long a3, long a4, int a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}) != 0; }
    public static boolean invokePPPPPZ(long a0, int a1, int a2, long a3, long a4, long a5, long a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}) != 0; }
    public static boolean invokePPPPPZ(long a0, long a1, long a2, long a3, double a4, long a5, int a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, Double.doubleToRawLongBits(a4), a5, a6}) != 0; }
    public static boolean invokePPPPPZ(long a0, long a1, long a2, long a3, long a4, boolean a5, int a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, (a5 ? 1L : 0L), a6}) != 0; }
    public static boolean invokePUUUUZ(long a0, int a1, int a2, byte a3, byte a4, byte a5, byte a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}) != 0; }
    public static boolean invokePPPPPZ(long a0, long a1, long a2, int a3, long a4, int a5, long a6, int a7, long a8) { return Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}) != 0; }
    public static boolean invokePPPPPZ(long a0, long a1, boolean a2, boolean a3, boolean a4, int a5, boolean a6, long a7, long a8, long a9, long a10) { return Nat.c(a10, new long[]{a0, a1, (a2 ? 1L : 0L), (a3 ? 1L : 0L), (a4 ? 1L : 0L), a5, (a6 ? 1L : 0L), a7, a8, a9}) != 0; }
    public static boolean invokePPJJPPZ(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}) != 0; }
    public static boolean invokePPPPPPZ(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}) != 0; }
    public static boolean invokeUUCCUUZ(int a0, byte a1, byte a2, short a3, short a4, byte a5, byte a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}) != 0; }
    public static boolean invokePPPPPPZ(long a0, long a1, long a2, int a3, long a4, int a5, long a6, int a7, int a8, long a9, int a10, int a11, long a12) { return Nat.c(a12, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11}) != 0; }
    public static short callC(int a0, long a1) { return (short) Nat.c(a1, new long[]{a0}); }
    public static float callF(int a0, int a1, int a2, long a3) { return Float.intBitsToFloat((int) Nat.c(a3, new long[]{a0, a1, a2})); }
    public static float callPF(long a0, float a1, long a2) { return Float.intBitsToFloat((int) Nat.c(a2, new long[]{a0, Float.floatToRawIntBits(a1)})); }
    public static int callI(long a0) { return (int) Nat.c(a0, new long[]{}); }
    public static int callI(int a0, long a1) { return (int) Nat.c(a1, new long[]{a0}); }
    public static int callI(int a0, int a1, long a2) { return (int) Nat.c(a2, new long[]{a0, a1}); }
    public static int callI(int a0, int a1, int a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int callPI(long a0, long a1) { return (int) Nat.c(a1, new long[]{a0}); }
    public static int callPI(int a0, long a1, long a2) { return (int) Nat.c(a2, new long[]{a0, a1}); }
    public static int callPI(long a0, float a1, long a2) { return (int) Nat.c(a2, new long[]{a0, Float.floatToRawIntBits(a1)}); }
    public static int callPI(long a0, int a1, long a2) { return (int) Nat.c(a2, new long[]{a0, a1}); }
    public static int callPI(int a0, int a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int callPI(int a0, long a1, int a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int callPI(long a0, float a1, float a2, long a3) { return (int) Nat.c(a3, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2)}); }
    public static int callPI(long a0, float a1, int a2, long a3) { return (int) Nat.c(a3, new long[]{a0, Float.floatToRawIntBits(a1), a2}); }
    public static int callPI(long a0, int a1, float a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, Float.floatToRawIntBits(a2)}); }
    public static int callPI(long a0, int a1, int a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int callJI(int a0, long a1, int a2, int a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPI(int a0, int a1, int a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPI(long a0, float a1, float a2, float a3, long a4) { return (int) Nat.c(a4, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3)}); }
    public static int callPI(long a0, int a1, float a2, float a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3)}); }
    public static int callPI(long a0, int a1, int a2, int a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPI(long a0, int a1, int a2, boolean a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, (a3 ? 1L : 0L)}); }
    public static int callPI(int a0, int a1, int a2, int a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPI(long a0, int a1, float a2, float a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), a4}); }
    public static int callPI(long a0, int a1, int a2, int a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPI(int a0, int a1, int a2, int a3, int a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPI(int a0, long a1, int a2, int a3, float a4, int a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, Float.floatToRawIntBits(a4), a5}); }
    public static int callPI(long a0, int a1, int a2, int a3, int a4, int a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPI(long a0, int a1, int a2, int a3, int a4, int a5, int a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPI(int a0, int a1, long a2, int a3, int a4, int a5, int a6, float a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, Float.floatToRawIntBits(a7)}); }
    public static int callPI(long a0, float a1, float a2, float a3, float a4, float a5, float a6, float a7, float a8, long a9) { return (int) Nat.c(a9, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), Float.floatToRawIntBits(a7), Float.floatToRawIntBits(a8)}); }
    public static int callPJI(long a0, long a1, long a2) { return (int) Nat.c(a2, new long[]{a0, a1}); }
    public static int callPPI(long a0, long a1, long a2) { return (int) Nat.c(a2, new long[]{a0, a1}); }
    public static int callPJI(long a0, int a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int callPJI(long a0, long a1, float a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, Float.floatToRawIntBits(a2)}); }
    public static int callPJI(long a0, long a1, int a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int callPPI(int a0, long a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int callPPI(long a0, float a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, Float.floatToRawIntBits(a1), a2}); }
    public static int callPPI(long a0, int a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int callPPI(long a0, long a1, float a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, Float.floatToRawIntBits(a2)}); }
    public static int callPPI(long a0, long a1, int a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int callPPI(int a0, int a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPPI(long a0, double a1, double a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, Double.doubleToRawLongBits(a1), Double.doubleToRawLongBits(a2), a3}); }
    public static int callPPI(long a0, int a1, float a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, Float.floatToRawIntBits(a2), a3}); }
    public static int callPPI(long a0, int a1, int a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPPI(long a0, int a1, long a2, int a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPPI(long a0, long a1, float a2, float a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3)}); }
    public static int callPPI(long a0, long a1, float a2, int a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, Float.floatToRawIntBits(a2), a3}); }
    public static int callPPI(long a0, long a1, int a2, int a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPPI(long a0, int a1, float a2, float a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), a4}); }
    public static int callPPI(long a0, int a1, int a2, int a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPI(long a0, int a1, int a2, long a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPI(long a0, long a1, int a2, int a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPI(long a0, long a1, int a2, int a3, boolean a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, (a4 ? 1L : 0L)}); }
    public static int callPPI(int a0, long a1, int a2, int a3, float a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, Float.floatToRawIntBits(a4), a5}); }
    public static int callPPI(long a0, int a1, float a2, float a3, float a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), a5}); }
    public static int callPPI(long a0, long a1, int a2, int a3, int a4, int a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPI(long a0, int a1, float a2, float a3, float a4, int a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), a5, a6}); }
    public static int callPPI(long a0, int a1, int a2, int a3, int a4, int a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPPI(long a0, int a1, float a2, float a3, float a4, float a5, int a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), a6, a7}); }
    public static int callPPI(long a0, int a1, int a2, int a3, int a4, int a5, int a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int callPPI(int a0, int a1, long a2, long a3, int a4, int a5, int a6, int a7, float a8, long a9) { return (int) Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, Float.floatToRawIntBits(a8)}); }
    public static int callPPI(long a0, int a1, float a2, float a3, float a4, float a5, float a6, int a7, long a8, long a9) { return (int) Nat.c(a9, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), a7, a8}); }
    public static int callPPI(long a0, int a1, int a2, float a3, float a4, float a5, float a6, float a7, int a8, long a9, int a10, long a11) { return (int) Nat.c(a11, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), Float.floatToRawIntBits(a7), a8, a9, a10}); }
    public static int callPPI(long a0, int a1, int a2, int a3, int a4, int a5, int a6, long a7, int a8, int a9, int a10, int a11, int a12, int a13, int a14, int a15, int a16, long a17) { return (int) Nat.c(a17, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16}); }
    public static int callPJJI(long a0, long a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int callPJPI(long a0, long a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int callPPJI(long a0, long a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int callPPPI(long a0, long a1, long a2, long a3) { return (int) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static int callPJJI(long a0, long a1, long a2, float a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3)}); }
    public static int callPJJI(long a0, long a1, long a2, int a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPJPI(long a0, int a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPJPI(long a0, long a1, int a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPPJI(long a0, int a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPPJI(long a0, long a1, int a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPPNI(long a0, int a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPPPI(int a0, long a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPPPI(long a0, int a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPPPI(long a0, long a1, int a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPPPI(long a0, long a1, long a2, int a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPJJI(long a0, long a1, int a2, long a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPJPI(long a0, int a1, long a2, long a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPJI(long a0, int a1, long a2, int a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPPI(int a0, long a1, int a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPPI(long a0, int a1, int a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPPI(long a0, int a1, long a2, long a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPPI(long a0, long a1, int a2, long a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPPI(long a0, long a1, long a2, int a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPPI(long a0, int a1, int a2, int a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPI(long a0, int a1, int a2, long a3, int a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPI(long a0, int a1, long a2, long a3, int a4, int a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPI(long a0, long a1, int a2, int a3, int a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPI(long a0, long a1, long a2, int a3, int a4, int a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPI(long a0, float a1, float a2, int a3, int a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), a3, a4, a5, a6}); }
    public static int callPPPI(long a0, int a1, int a2, int a3, int a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPPPI(long a0, int a1, long a2, long a3, int a4, int a5, int a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPPPI(long a0, long a1, int a2, int a3, int a4, int a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callJPPI(int a0, int a1, int a2, int a3, int a4, long a5, long a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int callJJPPI(long a0, long a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPJJJI(long a0, long a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPJJPI(long a0, long a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPJPPI(long a0, long a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPPJPI(long a0, long a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPPNPI(long a0, long a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPPPJI(long a0, long a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPPPPI(long a0, long a1, long a2, long a3, long a4) { return (int) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static int callPJJJI(long a0, int a1, long a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPJPPI(long a0, long a1, int a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPJPPI(long a0, long a1, long a2, long a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPPPI(long a0, int a1, long a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPPPI(long a0, long a1, int a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPPPI(long a0, long a1, long a2, int a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPPPI(long a0, long a1, long a2, long a3, int a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPJPPI(long a0, long a1, int a2, int a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPPI(int a0, long a1, long a2, int a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPPI(long a0, int a1, int a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPPI(long a0, int a1, long a2, int a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPPI(long a0, int a1, long a2, long a3, int a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPPI(long a0, int a1, long a2, long a3, long a4, int a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPPI(long a0, long a1, int a2, int a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPPI(long a0, long a1, int a2, long a3, int a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPPI(long a0, long a1, long a2, int a3, int a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPPI(long a0, long a1, long a2, int a3, long a4, int a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPJPPI(long a0, long a1, int a2, int a3, int a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPPPPI(long a0, int a1, int a2, int a3, long a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPPPPI(long a0, int a1, long a2, int a3, int a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPPPPI(long a0, long a1, long a2, int a3, int a4, int a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPPPPI(int a0, long a1, long a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10, int a11, int a12, int a13, int a14, int a15, int a16, int a17, int a18, int a19, int a20, long a21, long a22, long a23) { return (int) Nat.c(a23, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22}); }
    public static int callPJJPPI(long a0, long a1, long a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPJPPPI(long a0, long a1, long a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPJPPI(long a0, long a1, long a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPPPPPI(long a0, long a1, long a2, long a3, long a4, long a5) { return (int) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static int callPJJJPI(long a0, long a1, long a2, long a3, int a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPJPPPI(long a0, long a1, int a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPPPI(long a0, int a1, long a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPPPI(long a0, long a1, int a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPPPI(long a0, long a1, long a2, int a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPPPI(long a0, long a1, long a2, long a3, int a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPJPPI(long a0, int a1, long a2, long a3, int a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPPJPPI(long a0, long a1, int a2, long a3, int a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPPPPPI(int a0, int a1, long a2, long a3, long a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPPPPPI(long a0, int a1, int a2, long a3, long a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPPPPPI(long a0, int a1, long a2, int a3, long a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPPPPPI(long a0, int a1, long a2, long a3, int a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callJPPPPI(int a0, int a1, long a2, long a3, int a4, long a5, long a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int callPJPPJI(long a0, long a1, int a2, int a3, long a4, long a5, long a6, int a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int callPPPPPI(long a0, int a1, long a2, int a3, long a4, int a5, long a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int callPJJJJPI(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPJJPPPI(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPPJPI(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPPPPPPI(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { return (int) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static int callPJJPPPI(long a0, long a1, long a2, int a3, long a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPJPPPPI(long a0, int a1, long a2, long a3, long a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPPPJPPI(long a0, long a1, long a2, long a3, int a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPPPPPPI(long a0, long a1, long a2, int a3, long a4, long a5, long a6, long a7) { return (int) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static int callPJPPPPI(long a0, int a1, long a2, long a3, long a4, int a5, long a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int callPPPJPPI(long a0, int a1, long a2, long a3, long a4, int a5, long a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int callPPPPPPI(int a0, int a1, long a2, long a3, long a4, long a5, long a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int callPPPPPPI(long a0, int a1, long a2, int a3, long a4, long a5, long a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int callPPPPPPI(long a0, int a1, long a2, long a3, long a4, int a5, long a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int callPPPPPPPI(long a0, long a1, int a2, long a3, long a4, long a5, long a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int callPPPPPPPI(long a0, long a1, long a2, long a3, int a4, long a5, long a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int callPPPPPPPI(long a0, long a1, long a2, long a3, long a4, int a5, long a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int callPPPPPPPI(long a0, long a1, long a2, long a3, long a4, long a5, long a6, int a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int callPPPPPPPI(long a0, int a1, long a2, int a3, long a4, long a5, long a6, long a7, long a8, long a9) { return (int) Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static int callPPPPPPPI(long a0, int a1, long a2, long a3, int a4, long a5, long a6, long a7, long a8, long a9) { return (int) Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static int callPPPPPPPI(long a0, long a1, int a2, long a3, long a4, long a5, int a6, long a7, long a8, long a9) { return (int) Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static int callPPPPPPPI(long a0, long a1, long a2, int a3, long a4, long a5, int a6, long a7, long a8, long a9) { return (int) Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static int callPPPPPJPPI(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7, long a8) { return (int) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static int callPPJPPPPPI(long a0, long a1, long a2, long a3, long a4, int a5, long a6, long a7, long a8, long a9) { return (int) Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static int callPPPPPPPPI(long a0, long a1, long a2, long a3, long a4, long a5, int a6, long a7, long a8, long a9) { return (int) Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static int callPPPPPPPPI(long a0, long a1, long a2, long a3, int a4, long a5, long a6, int a7, long a8, long a9, long a10) { return (int) Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static int callPPPPPPPPI(long a0, int a1, int a2, long a3, long a4, long a5, long a6, int a7, long a8, long a9, long a10, long a11) { return (int) Nat.c(a11, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10}); }
    public static int callPPPPPPPPPI(long a0, long a1, long a2, long a3, long a4, long a5, int a6, long a7, long a8, long a9, long a10) { return (int) Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static int callPPPPPPPPPI(long a0, long a1, int a2, long a3, long a4, long a5, long a6, long a7, int a8, long a9, long a10, long a11) { return (int) Nat.c(a11, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10}); }
    public static int callPPPPPPPPPPI(long a0, long a1, long a2, long a3, long a4, long a5, long a6, int a7, long a8, long a9, long a10, long a11) { return (int) Nat.c(a11, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10}); }
    public static int callPPPPPPPPPPI(long a0, long a1, long a2, long a3, int a4, long a5, long a6, long a7, int a8, long a9, long a10, long a11, long a12) { return (int) Nat.c(a12, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11}); }
    public static int callPPPPPPPPPPPI(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7, int a8, long a9, long a10, long a11, long a12) { return (int) Nat.c(a12, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11}); }
    public static int callPPPPPPPPPPPPI(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7, long a8, long a9, int a10, long a11, long a12, long a13) { return (int) Nat.c(a13, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12}); }
    public static int callPPPPPPPPPPPPI(long a0, long a1, int a2, long a3, long a4, long a5, long a6, long a7, long a8, long a9, long a10, int a11, long a12, long a13, long a14) { return (int) Nat.c(a14, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13}); }
    public static int callPPPPPPPPPPPPPPPI(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7, long a8, long a9, long a10, long a11, int a12, long a13, long a14, long a15, long a16) { return (int) Nat.c(a16, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15}); }
    public static long callJ(long a0) { return Nat.c(a0, new long[]{}); }
    public static long callJ(int a0, long a1) { return Nat.c(a1, new long[]{a0}); }
    public static long callJ(int a0, int a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long callJ(int a0, int a1, boolean a2, int a3, int a4, long a5) { return Nat.c(a5, new long[]{a0, a1, (a2 ? 1L : 0L), a3, a4}); }
    public static long callPJ(long a0, int a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long callPPJ(long a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long callPJJ(long a0, long a1, int a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long callPJJJ(long a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long callPN(long a0, long a1) { return Nat.c(a1, new long[]{a0}); }
    public static long callP(long a0) { return Nat.c(a0, new long[]{}); }
    public static long callP(int a0, long a1) { return Nat.c(a1, new long[]{a0}); }
    public static long callP(int a0, int a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long callP(int a0, float a1, float a2, float a3, long a4) { return Nat.c(a4, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3)}); }
    public static long callJP(long a0, long a1) { return Nat.c(a1, new long[]{a0}); }
    public static long callPP(long a0, long a1) { return Nat.c(a1, new long[]{a0}); }
    public static long callPP(int a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long callPP(long a0, int a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long callPP(int a0, int a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long callPP(int a0, long a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long callPP(long a0, int a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long callPP(int a0, int a1, long a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long callJJP(long a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long callPNP(long a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long callPPP(long a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}); }
    public static long callPPP(int a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long callPPP(long a0, int a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long callPPP(long a0, long a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long callPPP(int a0, long a1, long a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long callPPP(long a0, int a1, int a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long callPPP(int a0, int a1, int a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long callPPP(long a0, int a1, int a2, int a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long callPPP(long a0, long a1, int a2, int a3, int a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long callPPNP(long a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long callPPPP(long a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}); }
    public static long callPJPP(long a0, long a1, int a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long callPJPP(long a0, long a1, long a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long callPPPP(int a0, long a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long callPPPP(long a0, int a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long callPPPP(long a0, long a1, int a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long callPPPP(long a0, long a1, long a2, int a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long callPPPP(long a0, long a1, int a2, int a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long callPPPP(long a0, long a1, int a2, long a3, int a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long callPJPP(long a0, long a1, int a2, int a3, int a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long callJJPPP(long a0, long a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long callPPJPP(long a0, long a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long callPPNPP(long a0, long a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long callPPPPP(long a0, long a1, long a2, long a3, long a4) { return Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static long callPJPPP(long a0, long a1, int a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long callPJPPP(long a0, long a1, long a2, int a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long callPPPPP(long a0, int a1, long a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long callPPPPP(long a0, long a1, int a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long callPPPPP(long a0, long a1, long a2, int a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long callPPPPP(long a0, long a1, long a2, long a3, int a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long callPJPPP(long a0, long a1, int a2, int a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long callPJPPPP(long a0, long a1, long a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long callPPPJPP(long a0, long a1, long a2, long a3, long a4, long a5) { return Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static long callPPPPPP(long a0, int a1, long a2, long a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long callPPPPPP(long a0, long a1, long a2, long a3, int a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long callPPPPPP(long a0, int a1, int a2, long a3, int a4, long a5, long a6, long a7, long a8) { return Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static long callPJJPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long callPJPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long callPPJPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long callPPPJPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { return Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static long callPPPPPPP(long a0, int a1, long a2, long a3, long a4, long a5, long a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long callPPJPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long callPPPPJPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7) { return Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static long callPPPPPPPP(long a0, int a1, long a2, long a3, int a4, long a5, long a6, long a7, long a8, long a9) { return Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static long callPPPPPPPP(int a0, long a1, long a2, int a3, int a4, int a5, int a6, long a7, long a8, long a9, int a10, long a11, long a12, long a13) { return Nat.c(a13, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12}); }
    public static long callPJPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7, long a8) { return Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static long callPPJPPPPPP(long a0, long a1, int a2, long a3, long a4, long a5, int a6, long a7, long a8, long a9, long a10) { return Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static long callPJPPPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7, long a8, long a9, long a10) { return Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static long callPPJPPPPPPPP(long a0, long a1, int a2, long a3, long a4, long a5, long a6, long a7, int a8, long a9, long a10, long a11, long a12) { return Nat.c(a12, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11}); }
    public static short callS(int a0, long a1) { return (short) Nat.c(a1, new long[]{a0}); }
    public static short callPS(long a0, long a1) { return (short) Nat.c(a1, new long[]{a0}); }
    public static short callPCS(long a0, short a1, long a2) { return (short) Nat.c(a2, new long[]{a0, a1}); }
    public static short callPPS(long a0, long a1, long a2) { return (short) Nat.c(a2, new long[]{a0, a1}); }
    public static short callPSS(long a0, short a1, long a2) { return (short) Nat.c(a2, new long[]{a0, a1}); }
    public static short callSPS(short a0, long a1, long a2) { return (short) Nat.c(a2, new long[]{a0, a1}); }
    public static short callPPS(long a0, long a1, int a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static short callPPS(long a0, int a1, long a2, int a3, long a4) { return (short) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static short callPCPS(long a0, short a1, long a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static short callPPCS(long a0, long a1, short a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static short callPPPS(long a0, long a1, long a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static short callPPSS(long a0, long a1, short a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static short callPSPS(long a0, short a1, long a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static short callSPPS(short a0, long a1, long a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static short callSPSS(short a0, long a1, short a2, long a3) { return (short) Nat.c(a3, new long[]{a0, a1, a2}); }
    public static short callPPPS(long a0, int a1, long a2, int a3, long a4, long a5) { return (short) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static short callPJCCS(long a0, long a1, short a2, short a3, long a4) { return (short) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static short callPPSPS(long a0, long a1, short a2, long a3, long a4) { return (short) Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static short callPSSPS(long a0, short a1, short a2, long a3, int a4, long a5) { return (short) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static short callPPPPS(long a0, long a1, int a2, long a3, int a4, long a5, long a6) { return (short) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static short callPCPPPS(long a0, short a1, long a2, long a3, long a4, long a5) { return (short) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static short callPCPSPS(long a0, short a1, long a2, short a3, long a4, long a5) { return (short) Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static short callPSSPPS(long a0, short a1, short a2, long a3, int a4, long a5, long a6) { return (short) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static short callPCPPPPS(long a0, short a1, long a2, long a3, long a4, long a5, long a6) { return (short) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static short callPCSPPPS(long a0, short a1, short a2, long a3, long a4, long a5, long a6) { return (short) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static short callPPSPSPS(long a0, long a1, short a2, long a3, short a4, long a5, long a6) { return (short) Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static short callPCCPSPPS(long a0, short a1, short a2, long a3, short a4, long a5, long a6, long a7) { return (short) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static short callPPSPSPSS(long a0, long a1, short a2, long a3, short a4, long a5, short a6, long a7) { return (short) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static short callSPSSPSPS(short a0, long a1, short a2, short a3, long a4, short a5, long a6, long a7) { return (short) Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static short callPCPSPPSPS(long a0, short a1, long a2, short a3, long a4, long a5, short a6, long a7, long a8) { return (short) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static short callPPPSPSPCS(long a0, long a1, long a2, short a3, long a4, short a5, long a6, short a7, long a8) { return (short) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static short callSPSPPPSPS(short a0, long a1, short a2, long a3, long a4, long a5, short a6, long a7, long a8) { return (short) Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static short callPCPSPPPPPS(long a0, short a1, long a2, short a3, long a4, long a5, long a6, long a7, long a8, long a9) { return (short) Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static short callPPSPSPSCCS(long a0, long a1, short a2, long a3, short a4, long a5, short a6, short a7, short a8, long a9) { return (short) Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static short callPPSPSPSPSS(long a0, long a1, short a2, long a3, short a4, long a5, short a6, long a7, short a8, long a9) { return (short) Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static short callPCPSPSPSCCS(long a0, short a1, long a2, short a3, long a4, short a5, long a6, short a7, short a8, short a9, long a10) { return (short) Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static short callPCSSSPSPPPS(long a0, short a1, short a2, short a3, short a4, long a5, short a6, long a7, long a8, long a9, long a10) { return (short) Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static short callPSSSPSSPPPS(long a0, short a1, short a2, short a3, long a4, short a5, short a6, long a7, long a8, long a9, long a10) { return (short) Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static short callPSPSPPPPPPPS(long a0, short a1, long a2, short a3, long a4, long a5, long a6, long a7, long a8, long a9, long a10, long a11) { return (short) Nat.c(a11, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10}); }
    public static short callPPSPSPSPSPSPSS(long a0, long a1, short a2, long a3, short a4, long a5, short a6, long a7, short a8, long a9, short a10, long a11, short a12, long a13) { return (short) Nat.c(a13, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12}); }
    public static void callV(long a0) { Nat.c(a0, new long[]{}); }
    public static void callV(double a0, long a1) { Nat.c(a1, new long[]{Double.doubleToRawLongBits(a0)}); }
    public static void callV(float a0, long a1) { Nat.c(a1, new long[]{Float.floatToRawIntBits(a0)}); }
    public static void callV(int a0, long a1) { Nat.c(a1, new long[]{a0}); }
    public static void callV(boolean a0, long a1) { Nat.c(a1, new long[]{(a0 ? 1L : 0L)}); }
    public static void callV(double a0, double a1, long a2) { Nat.c(a2, new long[]{Double.doubleToRawLongBits(a0), Double.doubleToRawLongBits(a1)}); }
    public static void callV(float a0, float a1, long a2) { Nat.c(a2, new long[]{Float.floatToRawIntBits(a0), Float.floatToRawIntBits(a1)}); }
    public static void callV(float a0, boolean a1, long a2) { Nat.c(a2, new long[]{Float.floatToRawIntBits(a0), (a1 ? 1L : 0L)}); }
    public static void callV(int a0, double a1, long a2) { Nat.c(a2, new long[]{a0, Double.doubleToRawLongBits(a1)}); }
    public static void callV(int a0, float a1, long a2) { Nat.c(a2, new long[]{a0, Float.floatToRawIntBits(a1)}); }
    public static void callV(int a0, int a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void callV(int a0, boolean a1, long a2) { Nat.c(a2, new long[]{a0, (a1 ? 1L : 0L)}); }
    public static void callV(double a0, double a1, double a2, long a3) { Nat.c(a3, new long[]{Double.doubleToRawLongBits(a0), Double.doubleToRawLongBits(a1), Double.doubleToRawLongBits(a2)}); }
    public static void callV(float a0, float a1, float a2, long a3) { Nat.c(a3, new long[]{Float.floatToRawIntBits(a0), Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2)}); }
    public static void callV(int a0, double a1, double a2, long a3) { Nat.c(a3, new long[]{a0, Double.doubleToRawLongBits(a1), Double.doubleToRawLongBits(a2)}); }
    public static void callV(int a0, float a1, float a2, long a3) { Nat.c(a3, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2)}); }
    public static void callV(int a0, int a1, double a2, long a3) { Nat.c(a3, new long[]{a0, a1, Double.doubleToRawLongBits(a2)}); }
    public static void callV(int a0, int a1, float a2, long a3) { Nat.c(a3, new long[]{a0, a1, Float.floatToRawIntBits(a2)}); }
    public static void callV(int a0, int a1, int a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callV(int a0, int a1, boolean a2, long a3) { Nat.c(a3, new long[]{a0, a1, (a2 ? 1L : 0L)}); }
    public static void callV(double a0, double a1, double a2, double a3, long a4) { Nat.c(a4, new long[]{Double.doubleToRawLongBits(a0), Double.doubleToRawLongBits(a1), Double.doubleToRawLongBits(a2), Double.doubleToRawLongBits(a3)}); }
    public static void callV(float a0, float a1, float a2, float a3, long a4) { Nat.c(a4, new long[]{Float.floatToRawIntBits(a0), Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3)}); }
    public static void callV(int a0, double a1, double a2, double a3, long a4) { Nat.c(a4, new long[]{a0, Double.doubleToRawLongBits(a1), Double.doubleToRawLongBits(a2), Double.doubleToRawLongBits(a3)}); }
    public static void callV(int a0, float a1, float a2, float a3, long a4) { Nat.c(a4, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3)}); }
    public static void callV(int a0, int a1, double a2, double a3, long a4) { Nat.c(a4, new long[]{a0, a1, Double.doubleToRawLongBits(a2), Double.doubleToRawLongBits(a3)}); }
    public static void callV(int a0, int a1, float a2, float a3, long a4) { Nat.c(a4, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3)}); }
    public static void callV(int a0, int a1, float a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, Float.floatToRawIntBits(a2), a3}); }
    public static void callV(int a0, int a1, int a2, double a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, Double.doubleToRawLongBits(a3)}); }
    public static void callV(int a0, int a1, int a2, float a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3)}); }
    public static void callV(int a0, int a1, int a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callV(int a0, int a1, int a2, boolean a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, (a3 ? 1L : 0L)}); }
    public static void callV(int a0, int a1, boolean a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, (a2 ? 1L : 0L), a3}); }
    public static void callV(boolean a0, boolean a1, boolean a2, boolean a3, long a4) { Nat.c(a4, new long[]{(a0 ? 1L : 0L), (a1 ? 1L : 0L), (a2 ? 1L : 0L), (a3 ? 1L : 0L)}); }
    public static void callV(int a0, double a1, double a2, double a3, double a4, long a5) { Nat.c(a5, new long[]{a0, Double.doubleToRawLongBits(a1), Double.doubleToRawLongBits(a2), Double.doubleToRawLongBits(a3), Double.doubleToRawLongBits(a4)}); }
    public static void callV(int a0, float a1, float a2, float a3, float a4, long a5) { Nat.c(a5, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4)}); }
    public static void callV(int a0, int a1, double a2, double a3, double a4, long a5) { Nat.c(a5, new long[]{a0, a1, Double.doubleToRawLongBits(a2), Double.doubleToRawLongBits(a3), Double.doubleToRawLongBits(a4)}); }
    public static void callV(int a0, int a1, float a2, float a3, float a4, long a5) { Nat.c(a5, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4)}); }
    public static void callV(int a0, int a1, int a2, float a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), a4}); }
    public static void callV(int a0, int a1, int a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callV(int a0, int a1, int a2, boolean a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, (a3 ? 1L : 0L), a4}); }
    public static void callV(int a0, boolean a1, boolean a2, boolean a3, boolean a4, long a5) { Nat.c(a5, new long[]{a0, (a1 ? 1L : 0L), (a2 ? 1L : 0L), (a3 ? 1L : 0L), (a4 ? 1L : 0L)}); }
    public static void callV(double a0, double a1, double a2, double a3, double a4, double a5, long a6) { Nat.c(a6, new long[]{Double.doubleToRawLongBits(a0), Double.doubleToRawLongBits(a1), Double.doubleToRawLongBits(a2), Double.doubleToRawLongBits(a3), Double.doubleToRawLongBits(a4), Double.doubleToRawLongBits(a5)}); }
    public static void callV(int a0, double a1, double a2, int a3, double a4, double a5, long a6) { Nat.c(a6, new long[]{a0, Double.doubleToRawLongBits(a1), Double.doubleToRawLongBits(a2), a3, Double.doubleToRawLongBits(a4), Double.doubleToRawLongBits(a5)}); }
    public static void callV(int a0, float a1, float a2, int a3, float a4, float a5, long a6) { Nat.c(a6, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), a3, Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5)}); }
    public static void callV(int a0, int a1, double a2, double a3, double a4, double a5, long a6) { Nat.c(a6, new long[]{a0, a1, Double.doubleToRawLongBits(a2), Double.doubleToRawLongBits(a3), Double.doubleToRawLongBits(a4), Double.doubleToRawLongBits(a5)}); }
    public static void callV(int a0, int a1, float a2, float a3, float a4, float a5, long a6) { Nat.c(a6, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5)}); }
    public static void callV(int a0, int a1, int a2, int a3, int a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callV(int a0, int a1, int a2, int a3, int a4, boolean a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, (a5 ? 1L : 0L)}); }
    public static void callV(int a0, int a1, int a2, int a3, boolean a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, (a4 ? 1L : 0L), a5}); }
    public static void callV(int a0, double a1, double a2, double a3, double a4, double a5, double a6, long a7) { Nat.c(a7, new long[]{a0, Double.doubleToRawLongBits(a1), Double.doubleToRawLongBits(a2), Double.doubleToRawLongBits(a3), Double.doubleToRawLongBits(a4), Double.doubleToRawLongBits(a5), Double.doubleToRawLongBits(a6)}); }
    public static void callV(int a0, int a1, int a2, double a3, double a4, double a5, double a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, Double.doubleToRawLongBits(a3), Double.doubleToRawLongBits(a4), Double.doubleToRawLongBits(a5), Double.doubleToRawLongBits(a6)}); }
    public static void callV(int a0, int a1, int a2, float a3, float a4, float a5, float a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6)}); }
    public static void callV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callV(int a0, int a1, int a2, int a3, int a4, int a5, boolean a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, (a6 ? 1L : 0L)}); }
    public static void callV(int a0, int a1, int a2, boolean a3, int a4, int a5, int a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, (a3 ? 1L : 0L), a4, a5, a6}); }
    public static void callV(float a0, float a1, float a2, float a3, float a4, float a5, float a6, float a7, long a8) { Nat.c(a8, new long[]{Float.floatToRawIntBits(a0), Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), Float.floatToRawIntBits(a7)}); }
    public static void callV(int a0, int a1, int a2, float a3, float a4, float a5, float a6, float a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), Float.floatToRawIntBits(a7)}); }
    public static void callV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void callV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, boolean a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, (a7 ? 1L : 0L)}); }
    public static void callV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, long a9) { Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static void callV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, boolean a8, long a9) { Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, (a8 ? 1L : 0L)}); }
    public static void callV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, long a10) { Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static void callV(int a0, int a1, float a2, float a3, float a4, float a5, float a6, float a7, float a8, float a9, float a10, long a11) { Nat.c(a11, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), Float.floatToRawIntBits(a7), Float.floatToRawIntBits(a8), Float.floatToRawIntBits(a9), Float.floatToRawIntBits(a10)}); }
    public static void callV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10, int a11, long a12) { Nat.c(a12, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11}); }
    public static void callV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10, int a11, int a12, int a13, int a14, long a15) { Nat.c(a15, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14}); }
    public static void callV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10, int a11, int a12, int a13, int a14, int a15, int a16, long a17) { Nat.c(a17, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16}); }
    public static void callJV(long a0, long a1) { Nat.c(a1, new long[]{a0}); }
    public static void callPV(long a0, long a1) { Nat.c(a1, new long[]{a0}); }
    public static void callSV(short a0, long a1) { Nat.c(a1, new long[]{a0}); }
    public static void callUV(byte a0, long a1) { Nat.c(a1, new long[]{a0}); }
    public static void callCV(int a0, short a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void callJV(int a0, long a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void callJV(long a0, int a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void callPV(int a0, long a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void callPV(long a0, float a1, long a2) { Nat.c(a2, new long[]{a0, Float.floatToRawIntBits(a1)}); }
    public static void callPV(long a0, int a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void callSV(int a0, short a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void callJV(int a0, int a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callPV(int a0, int a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callPV(int a0, long a1, int a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callPV(long a0, float a1, float a2, long a3) { Nat.c(a3, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2)}); }
    public static void callPV(long a0, int a1, int a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callJV(int a0, long a1, int a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callNV(long a0, int a1, int a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPV(int a0, int a1, int a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPV(int a0, int a1, long a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPV(int a0, int a1, boolean a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, (a2 ? 1L : 0L), a3}); }
    public static void callPV(int a0, long a1, int a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPV(long a0, float a1, float a2, float a3, long a4) { Nat.c(a4, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3)}); }
    public static void callPV(long a0, int a1, int a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPV(int a0, int a1, int a2, int a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPV(int a0, int a1, int a2, long a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPV(int a0, int a1, int a2, long a3, boolean a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, (a4 ? 1L : 0L)}); }
    public static void callPV(int a0, int a1, int a2, boolean a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, (a3 ? 1L : 0L), a4}); }
    public static void callPV(int a0, int a1, long a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPV(int a0, long a1, int a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPV(int a0, boolean a1, int a2, int a3, long a4, long a5) { Nat.c(a5, new long[]{a0, (a1 ? 1L : 0L), a2, a3, a4}); }
    public static void callPV(long a0, int a1, int a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callJV(int a0, int a1, int a2, int a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPV(int a0, double a1, double a2, int a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, Double.doubleToRawLongBits(a1), Double.doubleToRawLongBits(a2), a3, a4, a5}); }
    public static void callPV(int a0, float a1, float a2, int a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), a3, a4, a5}); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPV(int a0, int a1, int a2, int a3, long a4, boolean a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, (a5 ? 1L : 0L)}); }
    public static void callPV(int a0, int a1, int a2, long a3, int a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPV(int a0, int a1, int a2, boolean a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, (a3 ? 1L : 0L), a4, a5}); }
    public static void callPV(int a0, int a1, long a2, int a3, int a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPV(int a0, boolean a1, int a2, int a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, (a1 ? 1L : 0L), a2, a3, a4, a5}); }
    public static void callPV(long a0, int a1, int a2, int a3, int a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callJV(int a0, int a1, int a2, int a3, int a4, int a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPV(int a0, int a1, double a2, double a3, int a4, int a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, Double.doubleToRawLongBits(a2), Double.doubleToRawLongBits(a3), a4, a5, a6}); }
    public static void callPV(int a0, int a1, float a2, float a3, float a4, float a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), a6}); }
    public static void callPV(int a0, int a1, float a2, float a3, int a4, int a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), a4, a5, a6}); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, long a5, int a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPV(int a0, int a1, int a2, long a3, int a4, int a5, int a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPV(int a0, int a1, long a2, int a3, int a4, int a5, int a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPV(long a0, int a1, int a2, int a3, int a4, int a5, int a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callJV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void callJV(int a0, int a1, int a2, int a3, int a4, boolean a5, int a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, (a5 ? 1L : 0L), a6, a7}); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, boolean a5, int a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, (a5 ? 1L : 0L), a6, a7}); }
    public static void callJV(int a0, int a1, int a2, int a3, int a4, int a5, boolean a6, int a7, long a8, long a9) { Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, (a6 ? 1L : 0L), a7, a8}); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, long a8, long a9) { Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static void callPV(int a0, int a1, long a2, int a3, int a4, int a5, int a6, int a7, float a8, long a9) { Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, Float.floatToRawIntBits(a8)}); }
    public static void callPV(int a0, double a1, double a2, int a3, int a4, double a5, double a6, int a7, int a8, long a9, long a10) { Nat.c(a10, new long[]{a0, Double.doubleToRawLongBits(a1), Double.doubleToRawLongBits(a2), a3, a4, Double.doubleToRawLongBits(a5), Double.doubleToRawLongBits(a6), a7, a8, a9}); }
    public static void callPV(int a0, float a1, float a2, int a3, int a4, float a5, float a6, int a7, int a8, long a9, long a10) { Nat.c(a10, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2), a3, a4, Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), a7, a8, a9}); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, long a9, long a10) { Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static void callJV(long a0, int a1, float a2, float a3, float a4, float a5, float a6, float a7, float a8, float a9, float a10, long a11) { Nat.c(a11, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), Float.floatToRawIntBits(a4), Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), Float.floatToRawIntBits(a7), Float.floatToRawIntBits(a8), Float.floatToRawIntBits(a9), Float.floatToRawIntBits(a10)}); }
    public static void callPV(int a0, int a1, double a2, double a3, int a4, int a5, double a6, double a7, int a8, int a9, long a10, long a11) { Nat.c(a11, new long[]{a0, a1, Double.doubleToRawLongBits(a2), Double.doubleToRawLongBits(a3), a4, a5, Double.doubleToRawLongBits(a6), Double.doubleToRawLongBits(a7), a8, a9, a10}); }
    public static void callPV(int a0, int a1, float a2, float a3, int a4, int a5, float a6, float a7, int a8, int a9, long a10, long a11) { Nat.c(a11, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3), a4, a5, Float.floatToRawIntBits(a6), Float.floatToRawIntBits(a7), a8, a9, a10}); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, long a10, long a11) { Nat.c(a11, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10}); }
    public static void callPV(long a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10, long a11) { Nat.c(a11, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10}); }
    public static void callJV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, long a10, boolean a11, long a12) { Nat.c(a12, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, (a11 ? 1L : 0L)}); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10, long a11, long a12) { Nat.c(a12, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11}); }
    public static void callPJV(long a0, long a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void callPPV(long a0, long a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void callSSV(short a0, short a1, long a2) { Nat.c(a2, new long[]{a0, a1}); }
    public static void callJJV(int a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callPCV(long a0, int a1, short a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callPJV(long a0, int a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callPJV(long a0, long a1, float a2, long a3) { Nat.c(a3, new long[]{a0, a1, Float.floatToRawIntBits(a2)}); }
    public static void callPJV(long a0, long a1, int a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callPPV(int a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callPPV(long a0, int a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callPPV(long a0, long a1, int a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callSSV(int a0, short a1, short a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callJJV(int a0, int a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callJPV(int a0, int a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callJPV(int a0, long a1, int a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPJV(int a0, long a1, int a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPJV(long a0, int a1, long a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPJV(long a0, long a1, int a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPPV(int a0, int a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPPV(int a0, long a1, int a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPPV(int a0, long a1, long a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPPV(int a0, long a1, long a2, boolean a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, (a3 ? 1L : 0L)}); }
    public static void callPPV(long a0, int a1, int a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPPV(long a0, long a1, int a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPJV(long a0, long a1, int a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPV(int a0, int a1, int a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPV(int a0, int a1, long a2, int a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPV(int a0, int a1, long a2, long a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPV(int a0, long a1, int a2, long a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPV(int a0, long a1, long a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPV(long a0, int a1, int a2, int a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPV(int a0, int a1, int a2, int a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPPV(int a0, int a1, int a2, long a3, long a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPPV(int a0, int a1, long a2, int a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPPV(int a0, int a1, long a2, long a3, int a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPPV(int a0, long a1, long a2, int a3, int a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPPV(long a0, int a1, int a2, long a3, int a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPPV(long a0, int a1, long a2, int a3, int a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPPV(int a0, int a1, int a2, int a3, int a4, long a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPPV(int a0, int a1, int a2, long a3, int a4, int a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPPV(int a0, int a1, long a2, int a3, int a4, int a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPPV(int a0, int a1, long a2, long a3, int a4, int a5, int a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPPV(int a0, int a1, int a2, int a3, int a4, int a5, long a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void callPPV(int a0, int a1, int a2, int a3, long a4, int a5, int a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void callPPV(int a0, int a1, long a2, int a3, int a4, int a5, int a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void callPPV(int a0, int a1, int a2, long a3, int a4, float a5, float a6, int a7, long a8, long a9) { Nat.c(a9, new long[]{a0, a1, a2, a3, a4, Float.floatToRawIntBits(a5), Float.floatToRawIntBits(a6), a7, a8}); }
    public static void callPPV(int a0, int a1, long a2, int a3, int a4, int a5, int a6, int a7, long a8, long a9) { Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static void callPPV(int a0, int a1, long a2, int a3, int a4, int a5, long a6, int a7, int a8, float a9, long a10) { Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, Float.floatToRawIntBits(a9)}); }
    public static void callBBBV(byte a0, byte a1, byte a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callCCCV(short a0, short a1, short a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callPJJV(long a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callPJPV(long a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callPPNV(long a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callPPPV(long a0, long a1, long a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callSSSV(short a0, short a1, short a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callUUUV(byte a0, byte a1, byte a2, long a3) { Nat.c(a3, new long[]{a0, a1, a2}); }
    public static void callJJJV(int a0, long a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPJJV(long a0, long a1, long a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPJPV(long a0, long a1, int a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPPPV(int a0, long a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPPPV(long a0, int a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPPPV(long a0, long a1, int a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPPPV(long a0, long a1, long a2, int a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callSSSV(int a0, short a1, short a2, short a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callJJJV(int a0, int a1, long a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPJJV(long a0, int a1, long a2, long a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPJJV(long a0, long a1, long a2, int a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPPV(int a0, int a1, long a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPPV(int a0, long a1, int a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPPV(int a0, long a1, long a2, int a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPPV(int a0, long a1, long a2, long a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPPV(long a0, int a1, int a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPPV(long a0, int a1, long a2, int a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPJPV(long a0, int a1, long a2, int a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPJPV(long a0, long a1, int a2, int a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPPJV(int a0, long a1, long a2, int a3, long a4, boolean a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, (a5 ? 1L : 0L)}); }
    public static void callPPJV(long a0, int a1, long a2, int a3, long a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPPPV(int a0, int a1, int a2, long a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPPPV(int a0, int a1, long a2, int a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPPPV(int a0, long a1, int a2, long a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPJJV(long a0, int a1, int a2, long a3, long a4, int a5, int a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPJPV(long a0, long a1, int a2, int a3, long a4, int a5, int a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPPPV(int a0, int a1, int a2, int a3, long a4, long a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPPPV(int a0, int a1, long a2, long a3, int a4, int a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPPPV(long a0, int a1, long a2, int a3, int a4, int a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPPPV(int a0, int a1, int a2, int a3, long a4, int a5, long a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void callPPPV(long a0, int a1, int a2, int a3, int a4, int a5, long a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void callPPPV(long a0, long a1, int a2, int a3, int a4, int a5, int a6, int a7, long a8, int a9, int a10, int a11, int a12, int a13, int a14, int a15, int a16, int a17, long a18) { Nat.c(a18, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17}); }
    public static void callBBBBV(byte a0, byte a1, byte a2, byte a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callCCCCV(short a0, short a1, short a2, short a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPJJJV(long a0, long a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPJJPV(long a0, long a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPJPPV(long a0, long a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPPPNV(long a0, long a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callPPPPV(long a0, long a1, long a2, long a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callSSSSV(short a0, short a1, short a2, short a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callUUUUV(byte a0, byte a1, byte a2, byte a3, long a4) { Nat.c(a4, new long[]{a0, a1, a2, a3}); }
    public static void callJJJJV(int a0, long a1, long a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPJJJV(long a0, long a1, long a2, long a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPJJPV(long a0, int a1, long a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPJJPV(long a0, long a1, long a2, int a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPJPV(long a0, long a1, int a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPPPV(long a0, int a1, long a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPPPV(long a0, long a1, long a2, long a3, int a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callSSSSV(int a0, short a1, short a2, short a3, short a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callUUUUV(int a0, byte a1, byte a2, byte a3, byte a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callJJJJV(int a0, int a1, long a2, long a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPJJJV(long a0, long a1, long a2, long a3, int a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPJJPV(long a0, long a1, int a2, long a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPJJPV(long a0, long a1, long a2, int a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPJPPV(long a0, long a1, int a2, long a3, int a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPPPPV(int a0, long a1, int a2, long a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPPPPV(int a0, long a1, long a2, long a3, long a4, int a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPPPPV(long a0, int a1, int a2, long a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPJJPV(long a0, long a1, int a2, int a3, long a4, int a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPJJPV(long a0, long a1, int a2, long a3, int a4, int a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPJPPV(long a0, int a1, long a2, int a3, int a4, long a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPPPPV(int a0, int a1, int a2, long a3, long a4, long a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPPPPV(int a0, int a1, long a2, long a3, long a4, long a5, int a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPJJPV(long a0, long a1, int a2, long a3, int a4, int a5, long a6, int a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void callPJPPV(long a0, int a1, long a2, int a3, int a4, long a5, int a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void callPPPPV(long a0, int a1, int a2, int a3, int a4, long a5, int a6, long a7, int a8, long a9, long a10) { Nat.c(a10, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9}); }
    public static void callPJJJPV(long a0, long a1, long a2, long a3, long a4, long a5) { Nat.c(a5, new long[]{a0, a1, a2, a3, a4}); }
    public static void callPPPPPV(long a0, int a1, long a2, long a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPJJJJV(long a0, long a1, long a2, long a3, long a4, int a5, int a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPPPPPV(int a0, int a1, long a2, long a3, long a4, long a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPPPPPV(long a0, int a1, int a2, long a3, long a4, long a5, long a6, long a7) { Nat.c(a7, new long[]{a0, a1, a2, a3, a4, a5, a6}); }
    public static void callPJJJJV(long a0, long a1, int a2, int a3, long a4, long a5, long a6, int a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void callPJPPPV(long a0, int a1, int a2, long a3, long a4, int a5, long a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void callPPPPPV(long a0, long a1, long a2, long a3, long a4, int a5, int a6, int a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void callPPPPPV(long a0, int a1, long a2, int a3, int a4, int a5, long a6, int a7, long a8, int a9, long a10, long a11) { Nat.c(a11, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10}); }
    public static void callPPPPPJV(long a0, long a1, long a2, long a3, long a4, long a5, long a6) { Nat.c(a6, new long[]{a0, a1, a2, a3, a4, a5}); }
    public static void callPPPPPPV(long a0, long a1, long a2, int a3, int a4, long a5, long a6, long a7, long a8) { Nat.c(a8, new long[]{a0, a1, a2, a3, a4, a5, a6, a7}); }
    public static void callPPPPPPPV(int a0, int a1, int a2, long a3, int a4, long a5, long a6, long a7, long a8, long a9, long a10, long a11) { Nat.c(a11, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10}); }
    public static void callPPJJJJJJV(long a0, long a1, long a2, long a3, int a4, long a5, long a6, long a7, long a8, long a9) { Nat.c(a9, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8}); }
    public static void callPJJJJJJJJJJJV(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7, long a8, long a9, long a10, long a11, int a12, int a13, int a14, long a15) { Nat.c(a15, new long[]{a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14}); }
    public static boolean callZ(int a0, long a1) { return Nat.c(a1, new long[]{a0}) != 0; }
    public static boolean callZ(int a0, int a1, long a2) { return Nat.c(a2, new long[]{a0, a1}) != 0; }
    public static boolean callZ(int a0, float a1, float a2, long a3) { return Nat.c(a3, new long[]{a0, Float.floatToRawIntBits(a1), Float.floatToRawIntBits(a2)}) != 0; }
    public static boolean callZ(int a0, int a1, float a2, float a3, long a4) { return Nat.c(a4, new long[]{a0, a1, Float.floatToRawIntBits(a2), Float.floatToRawIntBits(a3)}) != 0; }
    public static boolean callJZ(long a0, long a1) { return Nat.c(a1, new long[]{a0}) != 0; }
    public static boolean callPZ(long a0, long a1) { return Nat.c(a1, new long[]{a0}) != 0; }
    public static boolean callJZ(int a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}) != 0; }
    public static boolean callPZ(int a0, long a1, long a2) { return Nat.c(a2, new long[]{a0, a1}) != 0; }
    public static boolean callJZ(int a0, long a1, int a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}) != 0; }
    public static boolean callPPZ(int a0, long a1, long a2, long a3) { return Nat.c(a3, new long[]{a0, a1, a2}) != 0; }
    public static boolean callPPPPZ(int a0, int a1, int a2, float a3, long a4, long a5, long a6, long a7, long a8) { return Nat.c(a8, new long[]{a0, a1, a2, Float.floatToRawIntBits(a3), a4, a5, a6, a7}) != 0; }
    public static short invokeUPC(byte a0, short[] a1, boolean a2, long a3) { throw new UnsupportedOperationException(); }
    public static short invokeCPCC(short a0, short[] a1, short a2, long a3) { throw new UnsupportedOperationException(); }
    public static int invokeCPI(short a0, int[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static int invokePCI(float[] a0, short a1, long a2) { throw new UnsupportedOperationException(); }
    public static int invokePPI(int a0, long a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int invokePPI(int a0, int[] a1, int a2, int[] a3, int a4, boolean a5, long a6) { throw new UnsupportedOperationException(); }
    public static int invokePPI(int a0, short[] a1, int a2, short[] a3, int a4, boolean a5, long a6) { throw new UnsupportedOperationException(); }
    public static int invokePPCI(long a0, float[] a1, short a2, long a3) { throw new UnsupportedOperationException(); }
    public static int invokeCPCUI(short a0, float[] a1, short a2, byte a3, long a4) { throw new UnsupportedOperationException(); }
    public static int invokeCPCUI(short a0, int[] a1, short a2, byte a3, long a4) { throw new UnsupportedOperationException(); }
    public static int invokeCPCUI(short a0, short[] a1, short a2, byte a3, long a4) { throw new UnsupportedOperationException(); }
    public static int invokePPPPI(long a0, long a1, long a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int invokePPPPI(long a0, long a1, int a2, int a3, float[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static int invokePPPPI(long a0, long a1, int a2, int a3, int[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static int invokePPPPPI(long a0, int[] a1, int[] a2, int[] a3, long a4, long a5) { throw new UnsupportedOperationException(); }
    public static int invokePNNPPPI(long a0, long a1, long a2, int a3, int a4, int[] a5, int[] a6, long a7, long a8) { throw new UnsupportedOperationException(); }
    public static int invokePPPPPPI(int a0, int a1, int[] a2, int[] a3, int[] a4, int[] a5, int[] a6, long a7, long a8) { throw new UnsupportedOperationException(); }
    public static int invokePPPPPPPI(long a0, int a1, int a2, int[] a3, int[] a4, int[] a5, int[] a6, int[] a7, long a8, long a9) { throw new UnsupportedOperationException(); }
    public static int invokePPPPPPPPI(long a0, int a1, int a2, long a3, int[] a4, int[] a5, float[] a6, int[] a7, int[] a8, int[] a9, long a10) { throw new UnsupportedOperationException(); }
    public static long invokePP(double[] a0, int a1, long a2) { throw new UnsupportedOperationException(); }
    public static long invokePP(float[] a0, int a1, long a2) { throw new UnsupportedOperationException(); }
    public static long invokePP(int[] a0, int a1, long a2) { throw new UnsupportedOperationException(); }
    public static long invokePP(long[] a0, int a1, long a2) { throw new UnsupportedOperationException(); }
    public static long invokePP(short[] a0, int a1, long a2) { throw new UnsupportedOperationException(); }
    public static long invokePPP(long a0, int[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static byte invokeUPU(byte a0, int[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, double[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, float[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, double[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, float[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, long[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int[] a1, boolean a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, float[] a2, int a3, int a4, long a5) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, int[] a2, int a3, int a4, long a5) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, short[] a2, int a3, int a4, long a5) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, int a2, int a3, int a4, double[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, int a2, int a3, int a4, float[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, int a2, int a3, int a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, int a2, int a3, int a4, short[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, int a2, int a3, int[] a4, boolean a5, long a6) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, int a2, int a3, int a4, int a5, double[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, int a2, int a3, int a4, int a5, float[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, int a2, int a3, int a4, int a5, int[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void invokePV(int a0, int a1, int a2, int a3, int a4, int a5, short[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void invokeUPV(byte a0, float[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void invokePJV(int a0, int[] a1, long a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePPV(long a0, int a1, double[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePPV(long a0, int a1, float[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePPV(long a0, int a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePPV(long a0, float[] a1, int a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePPV(long a0, int[] a1, int a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePPV(long a0, short[] a1, int a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePPV(long a0, int a1, int a2, double[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokePPV(long a0, int a1, int a2, float[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokePPV(long a0, int a1, int a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokePPV(long a0, int a1, int a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokePPV(long a0, int a1, int[] a2, boolean a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokePPV(int a0, int a1, int a2, int[] a3, long a4, long a5) { throw new UnsupportedOperationException(); }
    public static void invokePPV(long a0, int a1, int a2, float[] a3, int a4, int a5, long a6) { throw new UnsupportedOperationException(); }
    public static void invokePPV(long a0, int a1, int a2, int[] a3, int a4, int a5, long a6) { throw new UnsupportedOperationException(); }
    public static void invokePPV(long a0, int a1, int a2, short[] a3, int a4, int a5, long a6) { throw new UnsupportedOperationException(); }
    public static void invokePPV(long a0, int a1, int a2, int a3, int a4, int[] a5, boolean a6, long a7) { throw new UnsupportedOperationException(); }
    public static void invokeCCPV(short a0, short a1, short[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokeCPCV(short a0, double[] a1, short a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokeCPCV(short a0, float[] a1, short a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokeCPCV(short a0, int[] a1, short a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokeCPCV(short a0, long[] a1, short a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokeCPCV(short a0, short[] a1, short a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokeCPPV(short a0, float[] a1, float[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePNPV(long a0, long a1, short[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePPPV(long a0, double[] a1, double[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePPPV(long a0, float[] a1, float[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePPPV(long a0, int[] a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePPPV(int[] a0, int[] a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void invokePPJV(long a0, int a1, int[] a2, long a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokePPPV(int a0, float[] a1, float[] a2, float[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokePPPV(int a0, int[] a1, int[] a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokePPPV(int a0, int a1, double[] a2, double[] a3, double[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void invokePPPV(int a0, int a1, float[] a2, float[] a3, float[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void invokePPPV(int a0, int a1, int[] a2, int[] a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void invokePPPV(int a0, int a1, long[] a2, long[] a3, long[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void invokePPPV(float[] a0, int a1, long a2, long a3, int a4, long a5) { throw new UnsupportedOperationException(); }
    public static void invokePPPV(long a0, int a1, int a2, int a3, int[] a4, long a5, long a6) { throw new UnsupportedOperationException(); }
    public static void invokePPPV(float[] a0, boolean a1, int a2, long a3, long a4, int a5, long a6) { throw new UnsupportedOperationException(); }
    public static void invokeCCPCV(short a0, short a1, double[] a2, short a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokeCCPCV(short a0, short a1, float[] a2, short a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokeCCPCV(short a0, short a1, int[] a2, short a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokeCCPCV(short a0, short a1, long[] a2, short a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokeCCPCV(short a0, short a1, short[] a2, short a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokePCPCV(long a0, short a1, double[] a2, short a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokePCPCV(long a0, short a1, float[] a2, short a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokePCPCV(long a0, short a1, int[] a2, short a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokePCPCV(long a0, short a1, long[] a2, short a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokePCPCV(long a0, short a1, short[] a2, short a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokePNPPV(long a0, long a1, long a2, short[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void invokePPPPV(long a0, int a1, float[] a2, float[] a3, float[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void invokePPPPV(long a0, int a1, int[] a2, int[] a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void invokePPPPV(long a0, int a1, int a2, double[] a3, double[] a4, double[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void invokePPPPV(long a0, int a1, int a2, float[] a3, float[] a4, float[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void invokePPPPV(long a0, int a1, int a2, int[] a3, int[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void invokePPPPV(long a0, int a1, int a2, long[] a3, long[] a4, long[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void invokePPPPPV(long a0, long a1, long a2, float[] a3, long a4, long a5) { throw new UnsupportedOperationException(); }
    public static void invokePPPPPV(long a0, int[] a1, int[] a2, int[] a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void invokePPPPPV(long a0, int a1, long a2, int[] a3, long a4, long a5, long a6) { throw new UnsupportedOperationException(); }
    public static void invokePPPPPV(int a0, long a1, int a2, float[] a3, float[] a4, long a5, int a6, long a7, int a8, boolean a9, long a10) { throw new UnsupportedOperationException(); }
    public static void invokePPPPPV(int a0, int[] a1, int a2, float[] a3, float[] a4, long a5, int a6, int[] a7, int a8, boolean a9, long a10) { throw new UnsupportedOperationException(); }
    public static void invokePPPPPV(int a0, short[] a1, int a2, float[] a3, float[] a4, long a5, int a6, short[] a7, int a8, boolean a9, long a10) { throw new UnsupportedOperationException(); }
    public static boolean invokePZ(int a0, int[] a1, boolean a2, long a3) { throw new UnsupportedOperationException(); }
    public static boolean invokePPZ(long a0, int[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static boolean invokePPPZ(long a0, long a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPI(int[] a0, long a1) { throw new UnsupportedOperationException(); }
    public static int callPI(int a0, int[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static int callPI(int[] a0, int a1, long a2) { throw new UnsupportedOperationException(); }
    public static int callPI(int a0, int a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPI(int a0, int[] a1, int a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPI(int a0, int a1, int a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPI(int a0, int a1, int a2, int a3, float[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPI(int a0, int a1, int a2, int a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPI(long a0, int[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static int callPPI(long a0, long[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static int callPPI(int[] a0, long a1, long a2) { throw new UnsupportedOperationException(); }
    public static int callPPI(int a0, long a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPPI(long a0, int a1, double[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPPI(long a0, int a1, float[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPPI(long a0, int a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPPI(long a0, int a1, long[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPPI(long a0, int a1, short[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPPI(long a0, long[] a1, int a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPPI(long a0, int a1, int a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPI(long a0, int a1, int a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPI(int a0, long a1, int a2, int a3, float a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPJPI(long a0, long a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPJPI(long a0, long a1, long[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, long a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, long a1, long[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, int[] a1, long a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, int[] a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, long[] a1, long[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPPPI(int[] a0, long a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static int callPJPI(long a0, int a1, long a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPJPI(long a0, long a1, int a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPI(int a0, long a1, int[] a2, long a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, int a1, long a2, double[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, int a1, long a2, float[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, int a1, long a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, int a1, long a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, int a1, long a2, short[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, int a1, int[] a2, long a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, int a1, int[] a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, int a1, int[] a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, int a1, long[] a2, long a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, long a1, int a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, long a1, int a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, long a1, int[] a2, int a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPJI(long a0, int a1, long[] a2, int a3, long a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, int a1, int a2, long a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, int a1, int a2, int a3, int[] a4, float[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPPPI(long a0, int a1, int a2, int a3, int[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPJPPI(long a0, long a1, long a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPJPPI(long a0, long a1, long a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPJPPI(long a0, long a1, int[] a2, long a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPJPPI(long a0, long a1, int[] a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPJPPI(long a0, long a1, int[] a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPJPI(long a0, long[] a1, long a2, long a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPNPI(long a0, long a1, long a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, long a1, long a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, long a1, int[] a2, long a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, long a1, int[] a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static int callPJPPI(long a0, long a1, int a2, long a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPJPPI(long a0, long a1, int[] a2, long a3, int a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, int a1, long a2, long a3, long[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, int a1, long a2, int[] a3, long a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, int a1, long a2, long[] a3, long a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, int a1, long a2, long[] a3, long[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, int a1, int[] a2, long a3, long a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, long a1, int a2, long a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, long a1, int a2, long[] a3, long a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, long a1, long a2, int a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, int[] a1, long a2, int a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, long[] a1, int a2, long a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPJPPI(long a0, long a1, int a2, int a3, long a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPJPPI(long a0, long a1, int a2, int a3, int[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, int a1, int a2, long a3, int[] a4, long a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, int a1, int a2, long a3, long[] a4, long a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, int a1, int a2, long[] a3, int[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPJPPI(long a0, long a1, int a2, int a3, int a4, long a5, int[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(long a0, int a1, long a2, int a3, int a4, long a5, int[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static int callPPPPI(int a0, int[] a1, long[] a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10, int a11, int a12, int a13, int a14, int a15, int a16, int a17, int a18, int a19, int a20, int[] a21, long[] a22, long a23) { throw new UnsupportedOperationException(); }
    public static int callPJPPPI(long a0, long a1, long a2, long a3, long[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPJPPI(long a0, long a1, long a2, int[] a3, long a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPPPPI(long a0, long a1, int[] a2, int[] a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPPPPI(long a0, long a1, int[] a2, int[] a3, long[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPPPPPI(long a0, int[] a1, int[] a2, int[] a3, long a4, long a5) { throw new UnsupportedOperationException(); }
    public static int callPJPPPI(long a0, long a1, int a2, long a3, long a4, long[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPPPPPI(long a0, int a1, long a2, long a3, int[] a4, long a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPPPPPI(long a0, long a1, int a2, long a3, int[] a4, long a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPPPPPI(long a0, long a1, int a2, long a3, long[] a4, long a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPPPPPI(long a0, long a1, long a2, int[] a3, int a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPPPPPI(long a0, int[] a1, float[] a2, int a3, int[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPPPPPI(int a0, int a1, int[] a2, int[] a3, int[] a4, int[] a5, long a6, long a7) { throw new UnsupportedOperationException(); }
    public static int callPPPPPI(long a0, int a1, long a2, long[] a3, int a4, long a5, long a6, long a7) { throw new UnsupportedOperationException(); }
    public static int callPPPPPI(long a0, int a1, long[] a2, int a3, long a4, long a5, long a6, long a7) { throw new UnsupportedOperationException(); }
    public static int callPJPPJI(long a0, long a1, int a2, int a3, long a4, int[] a5, long a6, int a7, long a8) { throw new UnsupportedOperationException(); }
    public static int callPJPPJI(long a0, long a1, int a2, int a3, long a4, long[] a5, long a6, int a7, long a8) { throw new UnsupportedOperationException(); }
    public static int callPJJJJPI(long a0, long a1, long a2, long a3, long a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPJJPPPI(long a0, long a1, long a2, long[] a3, long a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPI(long a0, int[] a1, int[] a2, int[] a3, int[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static int callPJJPPPI(long a0, long a1, long a2, int a3, long a4, long a5, long[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPI(long a0, long a1, long a2, int a3, long a4, int[] a5, long a6, long a7) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPI(long a0, long a1, long a2, int a3, long a4, long[] a5, long a6, long a7) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPI(long a0, long a1, long[] a2, int a3, int[] a4, int[] a5, long a6, long a7) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPI(int a0, int a1, int[] a2, int[] a3, int[] a4, int[] a5, int[] a6, long a7, long a8) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPI(long a0, long a1, long a2, long a3, int a4, long a5, int[] a6, long a7, long a8) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPI(long a0, long a1, float[] a2, long a3, long a4, int a5, long a6, long a7, long a8) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPI(long a0, long a1, int[] a2, long a3, long a4, int a5, long a6, long a7, long a8) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPI(long a0, long a1, int a2, long a3, long a4, double[] a5, int a6, long a7, long a8, long a9) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPI(long a0, long a1, int a2, long a3, long a4, float[] a5, int a6, long a7, long a8, long a9) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPI(long a0, long a1, int a2, long a3, long a4, int[] a5, int a6, long a7, long a8, long a9) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPI(long a0, long a1, int a2, long a3, long a4, short[] a5, int a6, long a7, long a8, long a9) { throw new UnsupportedOperationException(); }
    public static int callPPJPPPPPI(long a0, long[] a1, long a2, long a3, long a4, int a5, long a6, long a7, long a8, long a9) { throw new UnsupportedOperationException(); }
    public static int callPPJPPPPPI(long a0, long[] a1, long a2, long a3, long a4, int a5, long a6, int[] a7, long a8, long a9) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPPPI(long a0, long a1, long[] a2, long a3, long a4, long a5, int a6, int[] a7, int[] a8, long a9, long a10) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPPPI(long a0, long a1, int a2, long a3, long a4, long a5, long a6, double[] a7, int a8, long a9, long a10, long a11) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPPPI(long a0, long a1, int a2, long a3, long a4, long a5, long a6, float[] a7, int a8, long a9, long a10, long a11) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPPPI(long a0, long a1, int a2, long a3, long a4, long a5, long a6, int[] a7, int a8, long a9, long a10, long a11) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPPPI(long a0, long a1, int a2, long a3, long a4, long a5, long a6, short[] a7, int a8, long a9, long a10, long a11) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPPPPI(long a0, long a1, long[] a2, long a3, long a4, long a5, long a6, int a7, int[] a8, int[] a9, long a10, long a11) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPPPPI(long a0, long a1, long[] a2, long a3, float[] a4, long a5, long a6, int a7, int[] a8, int[] a9, long a10, long a11) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPPPPI(long a0, long a1, long[] a2, long a3, int[] a4, long a5, long a6, int a7, int[] a8, int[] a9, long a10, long a11) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPPPPI(long a0, long a1, long[] a2, long a3, int a4, long a5, long a6, long a7, int a8, int[] a9, int[] a10, long a11, long a12) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPPPPPI(long a0, long a1, long[] a2, long a3, long a4, long a5, long a6, long a7, int a8, int[] a9, int[] a10, long a11, long a12) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPPPPPPI(long a0, long a1, int a2, long a3, long a4, long a5, long a6, long a7, long a8, long a9, double[] a10, int a11, long a12, long a13, long a14) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPPPPPPI(long a0, long a1, int a2, long a3, long a4, long a5, long a6, long a7, long a8, long a9, float[] a10, int a11, long a12, long a13, long a14) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPPPPPPI(long a0, long a1, int a2, long a3, long a4, long a5, long a6, long a7, long a8, long a9, int[] a10, int a11, long a12, long a13, long a14) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPPPPPPI(long a0, long a1, int a2, long a3, long a4, long a5, long a6, long a7, long a8, long a9, short[] a10, int a11, long a12, long a13, long a14) { throw new UnsupportedOperationException(); }
    public static int callPPPPPPPPPPPPPPPI(long a0, long a1, long[] a2, long a3, long a4, long a5, long a6, long a7, long a8, long a9, long a10, long a11, int a12, int[] a13, int[] a14, long a15, long a16) { throw new UnsupportedOperationException(); }
    public static long callPP(int[] a0, long a1) { throw new UnsupportedOperationException(); }
    public static long callPPP(long a0, int[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static long callPPP(int a0, long a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static long callPPP(long a0, int a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static long callPPP(int a0, int a1, int a2, int[] a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static long callPPP(long a0, int a1, int a2, int a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static long callPPPP(long a0, long a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static long callPPPP(long a0, long[] a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static long callPJPP(long a0, long a1, int a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static long callPPPP(int a0, long a1, long[] a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static long callPPPP(long a0, int a1, int[] a2, long a3, long a4) { throw new UnsupportedOperationException(); }
    public static long callPPPP(long a0, long a1, int a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static long callPPPP(long a0, long a1, int a2, int a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static long callPJPP(long a0, long a1, int a2, int a3, int a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPPJPP(long a0, long a1, long a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static long callPPNPP(long a0, long a1, long a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static long callPPPPP(long a0, long a1, long a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static long callPPPPP(long a0, long a1, long[] a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static long callPJPPP(long a0, long a1, int a2, long a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static long callPJPPP(long a0, long a1, int[] a2, int a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static long callPPPPP(long a0, int a1, long a2, long a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static long callPPPPP(long a0, long a1, int a2, long a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static long callPPPPP(long a0, long a1, long a2, int a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static long callPPPPP(long a0, long[] a1, int a2, long a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static long callPPPPP(long a0, long[] a1, long a2, int a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static long callPJPPP(long a0, long a1, int a2, int a3, long a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPJPPPP(long a0, long a1, long a2, long a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static long callPJPPPP(long a0, long a1, long a2, double[] a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static long callPJPPPP(long a0, long a1, long a2, float[] a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static long callPJPPPP(long a0, long a1, long a2, int[] a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static long callPJPPPP(long a0, long a1, long a2, short[] a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static long callPPPPPP(long a0, int a1, long a2, long a3, long a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPPPPPP(long a0, long a1, long[] a2, long a3, int a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPPPPPP(long a0, int a1, int a2, long a3, int a4, long a5, long a6, int[] a7, long a8) { throw new UnsupportedOperationException(); }
    public static long callPJJPPPP(long a0, long a1, long a2, long a3, long a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPJJPPPP(long a0, long a1, long a2, long a3, double[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPJJPPPP(long a0, long a1, long a2, long a3, float[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPJJPPPP(long a0, long a1, long a2, long a3, int[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPJJPPPP(long a0, long a1, long a2, long a3, short[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPJPPPPP(long a0, long a1, long a2, long a3, long a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPJPPPPP(long a0, long a1, long a2, long a3, float[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPJPPPPP(long a0, long a1, long a2, long a3, int[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPJPPPPP(long a0, long a1, long a2, long a3, short[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPPJPPPP(long a0, long[] a1, long a2, long a3, long a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPPJPPPP(long a0, long[] a1, long a2, long a3, double[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPPJPPPP(long a0, long[] a1, long a2, long a3, float[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPPJPPPP(long a0, long[] a1, long a2, long a3, int[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPPJPPPP(long a0, long[] a1, long a2, long a3, short[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPPPJPPP(long a0, long a1, long a2, long a3, long a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static long callPPPPPPP(long a0, int a1, long a2, long a3, long a4, int[] a5, int[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static long callPPJPPPPP(long a0, long[] a1, long a2, long a3, long a4, long a5, int[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static long callPPJPPPPP(long a0, long[] a1, long a2, long a3, long a4, float[] a5, int[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static long callPPJPPPPP(long a0, long[] a1, long a2, long a3, long a4, int[] a5, int[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static long callPPJPPPPP(long a0, long[] a1, long a2, long a3, long a4, short[] a5, int[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static long callPPPPPPPP(long a0, int a1, long a2, long a3, int a4, long a5, long a6, long a7, int[] a8, long a9) { throw new UnsupportedOperationException(); }
    public static long callPPPPPPPP(int a0, int[] a1, long[] a2, int a3, int a4, int a5, int a6, long a7, long a8, long a9, int a10, int[] a11, long[] a12, long a13) { throw new UnsupportedOperationException(); }
    public static long callPJPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6, int[] a7, long a8) { throw new UnsupportedOperationException(); }
    public static long callPJPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, float[] a6, int[] a7, long a8) { throw new UnsupportedOperationException(); }
    public static long callPJPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, int[] a6, int[] a7, long a8) { throw new UnsupportedOperationException(); }
    public static long callPJPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, short[] a6, int[] a7, long a8) { throw new UnsupportedOperationException(); }
    public static long callPPJPPPPPP(long a0, long a1, int a2, long a3, long a4, long a5, int a6, long a7, long a8, int[] a9, long a10) { throw new UnsupportedOperationException(); }
    public static long callPJPPPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7, long a8, int[] a9, long a10) { throw new UnsupportedOperationException(); }
    public static long callPJPPPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7, float[] a8, int[] a9, long a10) { throw new UnsupportedOperationException(); }
    public static long callPJPPPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7, int[] a8, int[] a9, long a10) { throw new UnsupportedOperationException(); }
    public static long callPJPPPPPPPPP(long a0, long a1, long a2, long a3, long a4, long a5, long a6, long a7, short[] a8, int[] a9, long a10) { throw new UnsupportedOperationException(); }
    public static long callPPJPPPPPPPP(long a0, long a1, int a2, long a3, long a4, long a5, long a6, long a7, int a8, long a9, long a10, int[] a11, long a12) { throw new UnsupportedOperationException(); }
    public static void callPV(double[] a0, long a1) { throw new UnsupportedOperationException(); }
    public static void callPV(float[] a0, long a1) { throw new UnsupportedOperationException(); }
    public static void callPV(int[] a0, long a1) { throw new UnsupportedOperationException(); }
    public static void callPV(short[] a0, long a1) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, double[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, float[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, long[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, short[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, double[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, float[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, long[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, short[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int[] a1, int a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, double[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, float[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, short[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, boolean a2, double[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, boolean a2, float[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, boolean a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int[] a2, int a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int[] a1, int a2, int a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, double[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, float[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, long[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, short[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, boolean a3, double[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, boolean a3, float[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int[] a3, boolean a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int[] a2, int a3, int a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, boolean a1, int a2, int a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, double a1, double a2, int a3, int a4, double[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, float a1, float a2, int a3, int a4, float[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, double[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, float[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, short[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int[] a4, boolean a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, boolean a3, int a4, float[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, boolean a3, int a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, boolean a3, int a4, short[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, double a2, double a3, int a4, int a5, double[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, float a2, float a3, int a4, int a5, float[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, double[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, float[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, short[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int[] a2, int a3, int a4, int a5, int a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, double[] a7, long a8) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, float[] a7, long a8) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int[] a7, long a8) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, short[] a7, long a8) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, double[] a8, long a9) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, float[] a8, long a9) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int[] a8, long a9) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, short[] a8, long a9) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, double a1, double a2, int a3, int a4, double a5, double a6, int a7, int a8, double[] a9, long a10) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, float a1, float a2, int a3, int a4, float a5, float a6, int a7, int a8, float[] a9, long a10) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, double[] a9, long a10) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, float[] a9, long a10) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int[] a9, long a10) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, short[] a9, long a10) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, double a2, double a3, int a4, int a5, double a6, double a7, int a8, int a9, double[] a10, long a11) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, float a2, float a3, int a4, int a5, float a6, float a7, int a8, int a9, float[] a10, long a11) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, double[] a10, long a11) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, float[] a10, long a11) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int[] a10, long a11) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, short[] a10, long a11) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10, double[] a11, long a12) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10, float[] a11, long a12) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10, int[] a11, long a12) { throw new UnsupportedOperationException(); }
    public static void callPV(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10, short[] a11, long a12) { throw new UnsupportedOperationException(); }
    public static void callPPV(long a0, float[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void callPPV(long a0, int[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void callPPV(double[] a0, double[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void callPPV(float[] a0, float[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void callPPV(int[] a0, int[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void callPPV(short[] a0, short[] a1, long a2) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, long a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int[] a1, float[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int[] a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int[] a1, long[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPPV(long a0, int a1, float[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPPV(long a0, int a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPPV(int[] a0, int a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, long a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, int[] a2, long a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, int[] a2, float[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, int[] a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, int[] a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, long a1, int a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, long a1, double[] a2, int a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, long a1, float[] a2, int a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, long a1, int[] a2, int a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, long a1, long[] a2, int a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, long a1, short[] a2, int a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int[] a1, int[] a2, int a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, long[] a1, int[] a2, int a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPV(long a0, int a1, int a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, int a2, long a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, int a2, int[] a3, long a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, int a2, int[] a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, long a2, int[] a3, int a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, int[] a2, int a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int[] a1, int a2, long a3, int a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int[] a1, int a2, int[] a3, int a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int[] a1, long a2, int a3, int a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPV(long a0, int a1, int a2, int a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, int a2, int a3, int[] a4, long a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, int a2, int[] a3, int[] a4, int a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, long a2, int a3, int a4, float[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, long a2, int a3, int a4, short[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, int[] a2, long a3, int a4, int a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, int a2, long a3, int a4, int a5, float[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, long a2, int a3, int a4, int a5, float[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, int a2, int a3, long a4, int a5, int a6, float[] a7, long a8) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, int a2, int a3, long a4, int a5, int a6, short[] a7, long a8) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, long a2, int a3, int a4, int a5, int a6, float[] a7, long a8) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, int a2, long a3, int a4, float a5, float a6, int a7, float[] a8, long a9) { throw new UnsupportedOperationException(); }
    public static void callPPV(int a0, int a1, long a2, int a3, int a4, int a5, int a6, int a7, float[] a8, long a9) { throw new UnsupportedOperationException(); }
    public static void callPJPV(long a0, long a1, long[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPPPV(long a0, long a1, int[] a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPPPV(long a0, int[] a1, long a2, long a3) { throw new UnsupportedOperationException(); }
    public static void callPJPV(long a0, long a1, int a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, long a1, long a2, double[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, long a1, long a2, float[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, long a1, long a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, long a1, long a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, long a1, long a2, short[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPPV(long a0, int a1, int[] a2, long a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPPV(long a0, int a1, int[] a2, long[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPPV(long a0, int a1, long[] a2, long a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPPV(long a0, long a1, int a2, int[] a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int a1, long a2, long a3, double[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int a1, long a2, long a3, float[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int a1, long a2, long a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int a1, long a2, long a3, short[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int a1, int[] a2, long a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int a1, int[] a2, int[] a3, long a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, long a1, int a2, int[] a3, long a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, long a1, int a2, int[] a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int[] a1, int[] a2, int a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPPV(long a0, int a1, int a2, int[] a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPPV(long a0, int a1, int a2, long[] a3, long[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPJPV(long a0, long a1, int a2, int a3, int a4, double[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPJPV(long a0, long a1, int a2, int a3, int a4, float[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPJPV(long a0, long a1, int a2, int a3, int a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPJPV(long a0, long a1, int a2, int a3, int a4, long[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPJPV(long a0, long a1, int a2, int a3, int a4, short[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPPJV(long a0, int a1, long[] a2, int a3, long a4, int a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int a1, int a2, int[] a3, long a4, long a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int a1, int a2, int[] a3, long a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int a1, int[] a2, int a3, int[] a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int[] a1, int a2, long a3, int a4, int[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int a1, long a2, long a3, int a4, int a5, float[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int a1, long a2, long a3, int a4, int a5, int[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int a1, long a2, long a3, int a4, int a5, short[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPPPV(long a0, int a1, long a2, int a3, int a4, int a5, int[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int a1, int a2, int a3, int[] a4, int a5, int[] a6, float[] a7, long a8) { throw new UnsupportedOperationException(); }
    public static void callPPPV(int a0, int a1, int a2, int a3, int[] a4, int a5, int[] a6, int[] a7, long a8) { throw new UnsupportedOperationException(); }
    public static void callPPPV(long a0, int a1, int a2, int a3, int a4, int a5, int[] a6, long a7, long a8) { throw new UnsupportedOperationException(); }
    public static void callPJPPV(long a0, long a1, int[] a2, long a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPPPPV(long a0, long a1, int[] a2, long a3, long a4) { throw new UnsupportedOperationException(); }
    public static void callPJJPV(long a0, int a1, long a2, long a3, long[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPPPV(long a0, int a1, long a2, int[] a3, long a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPPPV(long[] a0, int[] a1, int[] a2, int[] a3, int a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPPPV(int a0, long a1, int[] a2, int[] a3, int[] a4, int a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPPPPV(long a0, int a1, int a2, long[] a3, long[] a4, long[] a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPJPPV(long a0, int a1, long a2, int a3, int a4, int[] a5, long[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPPPPV(int a0, int a1, int a2, int[] a3, int[] a4, int[] a5, long a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPPPPV(int a0, int a1, long a2, int[] a3, int[] a4, int[] a5, int a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPJPPV(long a0, int a1, long a2, int a3, int a4, long[] a5, int a6, int[] a7, long a8) { throw new UnsupportedOperationException(); }
    public static void callPJJJPV(long a0, long a1, long a2, long a3, double[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPJJJPV(long a0, long a1, long a2, long a3, float[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPJJJPV(long a0, long a1, long a2, long a3, int[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPJJJPV(long a0, long a1, long a2, long a3, long[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPJJJPV(long a0, long a1, long a2, long a3, short[] a4, long a5) { throw new UnsupportedOperationException(); }
    public static void callPPPPPV(long a0, int a1, long a2, long[] a3, int[] a4, long a5, long a6) { throw new UnsupportedOperationException(); }
    public static void callPPPPPV(int a0, int a1, long a2, int[] a3, int[] a4, int[] a5, int[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPPPPPV(long a0, int a1, int a2, long[] a3, long[] a4, long[] a5, long[] a6, long a7) { throw new UnsupportedOperationException(); }
    public static void callPPPPPV(long a0, int a1, long[] a2, int a3, int a4, int a5, long a6, int a7, long a8, int a9, long a10, long a11) { throw new UnsupportedOperationException(); }
    public static void callPPPPPPPV(int a0, int a1, int a2, long a3, int a4, long a5, int[] a6, int[] a7, int[] a8, int[] a9, long[] a10, long a11) { throw new UnsupportedOperationException(); }
    public static boolean callPPZ(int a0, int[] a1, long a2, long a3) { throw new UnsupportedOperationException(); }
    public static boolean callPPPPZ(int a0, int a1, int a2, float a3, float[] a4, float[] a5, float[] a6, float[] a7, long a8) { throw new UnsupportedOperationException(); }
}
