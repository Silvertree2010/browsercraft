package net.minecraft.util;

import java.util.List;
import java.util.Optional;
import net.minecraft.CrashReportCategory;

public class NativeModuleLister {
    public static List<NativeModuleInfo> listModules() { return List.of(); }
    public static Optional<NativeModuleVersion> tryGetModuleVersion(String p) { return Optional.empty(); }
    public static void addCrashSection(CrashReportCategory c) {}

    public static class NativeModuleInfo { public String name() { return ""; } public String toString() { return ""; } }
    public static class NativeModuleVersion {}
}
