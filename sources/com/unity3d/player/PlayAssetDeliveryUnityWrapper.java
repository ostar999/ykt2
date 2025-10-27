package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import com.google.android.exoplayer2.C;

/* loaded from: classes6.dex */
class PlayAssetDeliveryUnityWrapper {

    /* renamed from: a, reason: collision with root package name */
    private static PlayAssetDeliveryUnityWrapper f23905a;

    /* renamed from: b, reason: collision with root package name */
    private d f23906b;

    private PlayAssetDeliveryUnityWrapper(Context context) throws ClassNotFoundException {
        if (f23905a != null) {
            throw new RuntimeException("PlayAssetDeliveryUnityWrapper should be created only once. Use getInstance() instead.");
        }
        try {
            Class.forName("com.google.android.play.core.assetpacks.AssetPackManager");
            this.f23906b = a(context);
        } catch (ClassNotFoundException unused) {
            this.f23906b = null;
        }
    }

    private static d a(Context context) {
        return a.a(context);
    }

    private void a() {
        if (playCoreApiMissing()) {
            throw new RuntimeException("AssetPackManager API is not available! Make sure your gradle project includes \"com.google.android.play:core\" dependency.");
        }
    }

    public static synchronized PlayAssetDeliveryUnityWrapper getInstance() {
        PlayAssetDeliveryUnityWrapper playAssetDeliveryUnityWrapper;
        while (true) {
            playAssetDeliveryUnityWrapper = f23905a;
            if (playAssetDeliveryUnityWrapper != null) {
                break;
            }
            try {
                PlayAssetDeliveryUnityWrapper.class.wait(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
            } catch (InterruptedException e2) {
                f.Log(6, e2.getMessage());
            }
        }
        if (playAssetDeliveryUnityWrapper == null) {
            throw new RuntimeException("PlayAssetDeliveryUnityWrapper is not yet initialised.");
        }
        return playAssetDeliveryUnityWrapper;
    }

    public static synchronized PlayAssetDeliveryUnityWrapper init(Context context) {
        if (f23905a != null) {
            throw new RuntimeException("PlayAssetDeliveryUnityWrapper.init() should be called only once. Use getInstance() instead.");
        }
        f23905a = new PlayAssetDeliveryUnityWrapper(context);
        PlayAssetDeliveryUnityWrapper.class.notifyAll();
        return f23905a;
    }

    public void cancelAssetPackDownload(String str) {
        cancelAssetPackDownloads(new String[]{str});
    }

    public void cancelAssetPackDownloads(String[] strArr) {
        a();
        this.f23906b.a(strArr);
    }

    public void downloadAssetPack(String str, IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback) {
        downloadAssetPacks(new String[]{str}, iAssetPackManagerDownloadStatusCallback);
    }

    public void downloadAssetPacks(String[] strArr, IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback) {
        a();
        this.f23906b.a(strArr, iAssetPackManagerDownloadStatusCallback);
    }

    public String getAssetPackPath(String str) {
        a();
        return this.f23906b.a(str);
    }

    public void getAssetPackState(String str, IAssetPackManagerStatusQueryCallback iAssetPackManagerStatusQueryCallback) {
        getAssetPackStates(new String[]{str}, iAssetPackManagerStatusQueryCallback);
    }

    public void getAssetPackStates(String[] strArr, IAssetPackManagerStatusQueryCallback iAssetPackManagerStatusQueryCallback) {
        a();
        this.f23906b.a(strArr, iAssetPackManagerStatusQueryCallback);
    }

    public boolean playCoreApiMissing() {
        return this.f23906b == null;
    }

    public Object registerDownloadStatusListener(IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback) {
        a();
        return this.f23906b.a(iAssetPackManagerDownloadStatusCallback);
    }

    public void removeAssetPack(String str) {
        a();
        this.f23906b.b(str);
    }

    public void requestToUseMobileData(Activity activity, IAssetPackManagerMobileDataConfirmationCallback iAssetPackManagerMobileDataConfirmationCallback) {
        a();
        this.f23906b.a(activity, iAssetPackManagerMobileDataConfirmationCallback);
    }

    public void unregisterDownloadStatusListener(Object obj) {
        a();
        this.f23906b.a(obj);
    }
}
