package com.aliyun.player;

import com.aliyun.player.IPlayer;
import com.aliyun.utils.f;
import com.cicada.player.utils.NativeUsed;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class AliPlayerGlobalSettings {
    private static OnGetUrlHashCallback sOnGetUrlHashCallback;
    private static OnNetworkCallback sOnNetworkCallback;

    public interface OnGetUrlHashCallback {
        String getUrlHashCallback(String str);
    }

    public interface OnNetworkCallback {
        boolean onNetworkDataProcess(String str, ByteBuffer byteBuffer, long j2, ByteBuffer byteBuffer2);
    }

    public enum StreamType {
        STREAM_VOICE_CALL,
        STREAM_SYSTEM,
        STREAM_RING,
        STREAM_MUSIC,
        STREAM_ALARM,
        STREAM_NOTIFICATION
    }

    static {
        f.b();
        sOnGetUrlHashCallback = null;
        sOnNetworkCallback = null;
    }

    public static void clearCaches() {
        nClearCaches();
    }

    public static void disableCrashUpload(boolean z2) {
        nDisableCrashUpload(z2);
    }

    public static void enableBufferToLocalCache(boolean z2) {
        nEnableBufferToLocalCache(z2);
    }

    public static void enableHttpDns(boolean z2) {
        nEnableHttpDns(z2);
    }

    public static void enableLocalCache(boolean z2, int i2, String str) {
        nEnableLocalCache(z2, i2, str);
    }

    public static void enableNetworkBalance(boolean z2) {
        nEnableNetworkBalance(z2);
    }

    public static void forceAudioRendingFormat(boolean z2, String str, int i2, int i3) {
        nForceAudioRendingFormat(z2, str, i2, i3);
    }

    public static void loadClass() {
    }

    private static native void nClearCaches();

    private static native void nDisableCrashUpload(boolean z2);

    private static native void nEnableBufferToLocalCache(boolean z2);

    private static native void nEnableHttpDns(boolean z2);

    private static native void nEnableLocalCache(boolean z2, int i2, String str);

    private static native void nEnableNetworkBalance(boolean z2);

    private static native void nForceAudioRendingFormat(boolean z2, String str, int i2, int i3);

    @NativeUsed
    private static synchronized String nOnGetUrlHashCallback(String str) {
        OnGetUrlHashCallback onGetUrlHashCallback = sOnGetUrlHashCallback;
        if (onGetUrlHashCallback == null) {
            return null;
        }
        return onGetUrlHashCallback.getUrlHashCallback(str);
    }

    @NativeUsed
    private static synchronized boolean nOnNetworkDataProcessCallback(String str, ByteBuffer byteBuffer, long j2, ByteBuffer byteBuffer2) {
        OnNetworkCallback onNetworkCallback = sOnNetworkCallback;
        if (onNetworkCallback == null) {
            return false;
        }
        return onNetworkCallback.onNetworkDataProcess(str, byteBuffer, j2, byteBuffer2);
    }

    private static native void nSetAudioStreamType(int i2);

    private static native void nSetCacheFileClearConfig(long j2, long j3, long j4);

    private static native void nSetCacheUrlHashCallback(boolean z2);

    private static native void nSetDNSResolve(String str, String str2);

    private static native void nSetIPResolveType(int i2);

    private static native void nSetNetworkCallback(boolean z2);

    private static native void nSetUseHttp2(boolean z2);

    public static void setAudioStreamType(StreamType streamType) {
        nSetAudioStreamType(streamType.ordinal());
    }

    public static void setCacheFileClearConfig(long j2, long j3, long j4) {
        nSetCacheFileClearConfig(j2, j3, j4);
    }

    public static synchronized void setCacheUrlHashCallback(OnGetUrlHashCallback onGetUrlHashCallback) {
        sOnGetUrlHashCallback = onGetUrlHashCallback;
        nSetCacheUrlHashCallback(onGetUrlHashCallback != null);
    }

    public static void setDNSResolve(String str, String str2) {
        nSetDNSResolve(str, str2);
    }

    public static void setIPResolveType(IPlayer.IPResolveType iPResolveType) {
        nSetIPResolveType(iPResolveType.ordinal());
    }

    public static synchronized void setNetworkCallback(OnNetworkCallback onNetworkCallback) {
        sOnNetworkCallback = onNetworkCallback;
        nSetNetworkCallback(onNetworkCallback != null);
    }

    public static void setUseHttp2(boolean z2) {
        nSetUseHttp2(z2);
    }
}
