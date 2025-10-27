package com.easefun.polyv.businesssdk;

import android.app.Application;
import android.util.Log;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.net.PLVOkHttpDns;

/* loaded from: classes3.dex */
public abstract class PolyvCommonSDKClient {
    private String viewerId = "";

    static {
        try {
            System.loadLibrary("polyvSDKModule");
        } catch (UnsatisfiedLinkError e2) {
            Log.e("PolyvCommonSDKClient", e2.getMessage(), e2);
        }
    }

    public void enableHttpDns(boolean z2) {
        PLVOkHttpDns.enableHttpDns(z2);
    }

    public void enableIPV6(boolean z2) {
        PLVOkHttpDns.enableHttpDnsIPV6(z2);
    }

    public String getChannelData2String(String str) {
        return new String(getChannelInfo(str));
    }

    public native byte[] getChannelInfo(String str);

    public String getViewerId() {
        return this.viewerId;
    }

    public void initContext(Application application) {
        PolyvThirdSDKClient.initAppUtils(application);
        PolyvThirdSDKClient.initStetho();
        PLVCommonLog.init();
        initUA();
    }

    public abstract void initUA();

    public void setViewerId(String str) {
        this.viewerId = str;
    }
}
