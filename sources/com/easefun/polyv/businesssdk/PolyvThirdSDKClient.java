package com.easefun.polyv.businesssdk;

import android.app.Application;
import com.plv.foundationsdk.net.IPLVHttpDns;
import com.plv.foundationsdk.net.PLVOkHttpDns;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.foundationsdk.utils.PLVStethoDecoupler;

/* loaded from: classes3.dex */
public class PolyvThirdSDKClient {
    @Deprecated
    public static void enableHttpDns() {
    }

    public static IPLVHttpDns getHttpDns() {
        return PLVOkHttpDns.getInstance();
    }

    public static void initAppUtils(Application application) {
        PLVAppUtils.init(application);
    }

    public static void initStetho() {
        PLVStethoDecoupler.initStetho(PLVAppUtils.getApp());
    }
}
