package com.aliyun.utils;

import com.aliyun.loader.MediaLoader;
import com.aliyun.loader.VodMediaLoader;
import com.aliyun.player.AliPlayerGlobalSettings;
import com.aliyun.player.BuildConfig;
import com.aliyun.player.HlsKeyGenerator;
import com.aliyun.player.nativeclass.JniListPlayerBase;
import com.aliyun.player.nativeclass.JniSaasListPlayer;
import com.aliyun.player.nativeclass.JniSaasPlayer;
import com.aliyun.player.nativeclass.JniUrlListPlayer;
import com.aliyun.player.nativeclass.JniUrlPlayer;
import com.aliyun.player.nativeclass.NativeExternalPlayer;
import com.aliyun.player.nativeclass.NativePlayerBase;
import com.aliyun.private_service.PrivateService;
import com.aliyun.thumbnail.ThumbnailHelper;
import com.cicada.player.utils.Logger;
import com.cicada.player.utils.ass.AssUtils;

/* loaded from: classes2.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f3599a = false;

    /* renamed from: b, reason: collision with root package name */
    private static boolean f3600b = false;

    public static synchronized void a() {
        b();
        if (f3600b) {
            return;
        }
        try {
            System.loadLibrary("saasDownloader");
            f3600b = true;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static synchronized void b() {
        if (f3599a) {
            return;
        }
        f3599a = true;
        JniUrlPlayer.loadClass();
        JniSaasPlayer.loadClass();
        JniListPlayerBase.loadClass();
        JniUrlListPlayer.loadClass();
        JniSaasListPlayer.loadClass();
        PrivateService.loadClass();
        MediaLoader.loadClass();
        VodMediaLoader.loadClass();
        AliPlayerGlobalSettings.loadClass();
        HlsKeyGenerator.loadClass();
        NativeExternalPlayer.loadClass();
        NativePlayerBase.loadClass();
        ThumbnailHelper.loadClass();
        DeviceInfoUtils.loadClass();
        Logger.loadClass();
        AssUtils.loadClass();
        try {
            System.loadLibrary("alivcffmpeg");
            System.loadLibrary(BuildConfig.PLAYER_LIB_NAME);
        } catch (Exception e2) {
            e2.printStackTrace();
            f3599a = false;
        }
    }
}
