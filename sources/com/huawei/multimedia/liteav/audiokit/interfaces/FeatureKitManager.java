package com.huawei.multimedia.liteav.audiokit.interfaces;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import com.tencent.liteav.basic.log.TXCLog;

/* loaded from: classes4.dex */
public class FeatureKitManager {
    private static final String ENGINE_PACKAGE_NAME = "com.huawei.multimedia.audioengine";
    private static final int PACKAGE_INFO_FLAG = 0;
    private static final String TAG = "HwAudioKit.FeatureKitManager";
    private static FeatureKitManager sInstance;
    private IAudioKitCallback mCallBack = null;
    private static final Object SET_CALL_BACK_LOCK = new Object();
    private static final Object NEW_FEATUREMANAGER_LOCK = new Object();
    private static final Object BIND_SERVICE_LOCK = new Object();
    private static final Object UNBIND_SERVICE_LOCK = new Object();

    public static FeatureKitManager getInstance() {
        FeatureKitManager featureKitManager;
        synchronized (NEW_FEATUREMANAGER_LOCK) {
            if (sInstance == null) {
                sInstance = new FeatureKitManager();
            }
            featureKitManager = sInstance;
        }
        return featureKitManager;
    }

    public void bindService(Context context, ServiceConnection serviceConnection, String str) {
        synchronized (BIND_SERVICE_LOCK) {
            if (context == null) {
                return;
            }
            Intent intent = new Intent();
            intent.setClassName(ENGINE_PACKAGE_NAME, str);
            try {
                TXCLog.i(TAG, "bindService");
                context.bindService(intent, serviceConnection, 1);
            } catch (SecurityException e2) {
                TXCLog.e(TAG, "bindService, SecurityException, %s", e2.getMessage());
            }
        }
    }

    public <T extends AudioFeaturesKit> T createFeatureKit(int i2, Context context) {
        TXCLog.i(TAG, "createFeatureKit, type = %d", Integer.valueOf(i2));
        if (context == null) {
            return null;
        }
        if (i2 != 1) {
            TXCLog.i(TAG, "createFeatureKit, type error");
            return null;
        }
        HwAudioKaraokeFeatureKit hwAudioKaraokeFeatureKit = new HwAudioKaraokeFeatureKit(context);
        hwAudioKaraokeFeatureKit.initialize(context);
        return hwAudioKaraokeFeatureKit;
    }

    public IAudioKitCallback getCallBack() {
        return this.mCallBack;
    }

    public boolean isAudioKitSupport(Context context) {
        if (context == null) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return true;
        }
        try {
            if (packageManager.getPackageInfo(ENGINE_PACKAGE_NAME, 0) != null) {
                return true;
            }
            TXCLog.i(TAG, "packageInfo is null");
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            TXCLog.e(TAG, "isAudioKitSupport ,NameNotFoundException");
            return false;
        }
    }

    public void onCallBack(int i2) {
        TXCLog.i(TAG, "onCallBack, result = %d", Integer.valueOf(i2));
        synchronized (SET_CALL_BACK_LOCK) {
            if (getCallBack() != null) {
                getCallBack().onResult(i2);
            }
        }
    }

    public void setCallBack(IAudioKitCallback iAudioKitCallback) {
        this.mCallBack = iAudioKitCallback;
    }

    public void unbindService(Context context, ServiceConnection serviceConnection) {
        TXCLog.i(TAG, "unbindService");
        synchronized (UNBIND_SERVICE_LOCK) {
            if (context != null) {
                context.unbindService(serviceConnection);
            }
        }
    }
}
