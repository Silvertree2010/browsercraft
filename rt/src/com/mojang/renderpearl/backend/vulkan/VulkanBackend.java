package com.mojang.renderpearl.backend.vulkan;

import com.mojang.renderpearl.api.device.BackendCreationException;
import com.mojang.renderpearl.api.device.GpuBackend;
import com.mojang.renderpearl.api.device.GpuDebugOptions;
import com.mojang.renderpearl.api.device.GpuDevice;

public class VulkanBackend implements GpuBackend {
    public static BackendCreationException checkBackendAvailable() { return null; }
    public String getName() { return "WebGPU"; }
    public void loadLibrary() throws BackendCreationException {}
    public void unloadLibrary() {}
    public long createWindow(String t, int w, int h, long f) { return org.lwjgl.sdl.SDLVideo.SDL_CreateWindow(t, w, h, f); }
    public GpuDevice createDevice(GpuDebugOptions o) throws BackendCreationException { return bc.gpu.Wg.device(o); }
}
