package com.github.gzuliyujiang.oaid.impl;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import com.github.gzuliyujiang.oaid.IGetter;
import com.github.gzuliyujiang.oaid.IOAID;
import com.github.gzuliyujiang.oaid.OAIDException;
import com.github.gzuliyujiang.oaid.OAIDLog;
import com.huawei.hms.ads.identifier.AdvertisingIdClient;
import com.huawei.hms.common.PackageConstants;
import java.io.IOException;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
class HuaweiImpl implements IOAID {
    private final Context context;
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public HuaweiImpl(Context context) {
        this.context = context;
    }

    private void postOnMainThread(final IGetter iGetter, final String str) {
        this.uiHandler.post(new Runnable() { // from class: com.github.gzuliyujiang.oaid.impl.HuaweiImpl.2
            @Override // java.lang.Runnable
            public void run() {
                iGetter.onOAIDGetComplete(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runOnSubThread(IGetter iGetter) {
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.context);
            if (advertisingIdInfo == null) {
                postOnMainThread(iGetter, new OAIDException("Advertising identifier info is null"));
            } else if (advertisingIdInfo.isLimitAdTrackingEnabled()) {
                postOnMainThread(iGetter, new OAIDException("User has disabled advertising identifier"));
            } else {
                postOnMainThread(iGetter, advertisingIdInfo.getId());
            }
        } catch (IOException e2) {
            OAIDLog.print(e2);
            postOnMainThread(iGetter, new OAIDException(e2));
        }
    }

    @Override // com.github.gzuliyujiang.oaid.IOAID
    public void doGet(final IGetter iGetter) {
        if (this.context == null || iGetter == null) {
            return;
        }
        Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.github.gzuliyujiang.oaid.impl.HuaweiImpl.1
            @Override // java.lang.Runnable
            public void run() {
                HuaweiImpl.this.runOnSubThread(iGetter);
            }
        });
    }

    @Override // com.github.gzuliyujiang.oaid.IOAID
    public boolean supported() {
        Context context = this.context;
        if (context == null) {
            return false;
        }
        try {
        } catch (Exception e2) {
            OAIDLog.print(e2);
        }
        if (AdvertisingIdClient.isAdvertisingIdAvailable(context)) {
            return true;
        }
        PackageManager packageManager = this.context.getPackageManager();
        if (packageManager.getPackageInfo("com.huawei.hwid", 0) == null && packageManager.getPackageInfo("com.huawei.hwid.tv", 0) == null) {
            return packageManager.getPackageInfo(PackageConstants.SERVICES_PACKAGE_ALL_SCENE, 0) != null;
        }
        return true;
    }

    private void postOnMainThread(final IGetter iGetter, final OAIDException oAIDException) {
        this.uiHandler.post(new Runnable() { // from class: com.github.gzuliyujiang.oaid.impl.HuaweiImpl.3
            @Override // java.lang.Runnable
            public void run() {
                iGetter.onOAIDGetError(oAIDException);
            }
        });
    }
}
