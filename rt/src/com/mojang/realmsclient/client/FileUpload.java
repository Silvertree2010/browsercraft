package com.mojang.realmsclient.client;

import java.util.concurrent.CompletableFuture;
import com.mojang.realmsclient.dto.UploadInfo;
import com.mojang.realmsclient.gui.screens.UploadResult;
import net.minecraft.client.User;

public class FileUpload implements AutoCloseable {
    public FileUpload(java.io.File f, long w, int s, UploadInfo i, User u, String v, String c, UploadStatus st) {}
    public void close() {}
    public CompletableFuture<UploadResult> startUpload() { return CompletableFuture.failedFuture(new UnsupportedOperationException("no realms upload in browser")); }
}
