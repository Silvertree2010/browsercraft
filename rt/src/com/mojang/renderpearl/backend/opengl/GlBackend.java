package com.mojang.renderpearl.backend.opengl;

import com.mojang.renderpearl.api.device.BackendCreationException;
import com.mojang.renderpearl.api.device.GpuBackend;
import com.mojang.renderpearl.api.device.GpuDebugOptions;
import com.mojang.renderpearl.api.device.GpuDevice;

public class GlBackend implements GpuBackend {
    public String getName() { return "OpenGL"; }
    public void loadLibrary() throws BackendCreationException { throw new BackendCreationException("no opengl in browser", BackendCreationException.Reason.OPENGL_MISSING); }
    public void unloadLibrary() {}
    public long createWindow(String t, int w, int h, long f) { return 0; }
    public GpuDevice createDevice(GpuDebugOptions o) throws BackendCreationException { throw new BackendCreationException("no opengl in browser", BackendCreationException.Reason.OPENGL_MISSING); }
}
