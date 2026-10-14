package com.mojang.realmsclient.client;

import java.util.OptionalLong;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.gui.screens.RealmsDownloadLatestWorldScreen;
import net.minecraft.world.level.storage.LevelStorageSource;

public class FileDownload {
    public static OptionalLong contentLength(String u) { return OptionalLong.empty(); }
    public void download(WorldDownload w, String n, RealmsDownloadLatestWorldScreen.DownloadStatus s, LevelStorageSource l) {}
    public void cancel() {}
    public boolean isFinished() { return true; }
    public boolean isError() { return true; }
    public boolean isExtracting() { return false; }
    public static String findAvailableFolderName(String s) { return s; }
}
