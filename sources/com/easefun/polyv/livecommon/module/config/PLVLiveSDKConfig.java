package com.easefun.polyv.livecommon.module.config;

import android.app.Application;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes3.dex */
public class PLVLiveSDKConfig {

    public static class Parameter {
        private Application application;
        private boolean isOpenDebugLog = true;
        private boolean isEnableHttpDns = false;
        private boolean isEnableIPV6 = false;
        private boolean isEnableBugly = false;

        public Parameter(Application application) {
            this.application = application;
        }

        public Parameter isEnableHttpDns(boolean isEnableHttpDns) {
            this.isEnableHttpDns = isEnableHttpDns;
            return this;
        }

        public Parameter isOpenDebugLog(boolean isOpenDebugLog) {
            this.isOpenDebugLog = isOpenDebugLog;
            return this;
        }

        public Parameter setEnableBugly(boolean enableBugly) {
            this.isEnableBugly = enableBugly;
            return this;
        }

        public Parameter setEnableIPV6(boolean enableIPV6) {
            this.isEnableIPV6 = enableIPV6;
            return this;
        }
    }

    public static void init(Parameter parameter) {
        PLVCommonLog.setDebug(parameter.isOpenDebugLog);
        PolyvLiveSDKClient polyvLiveSDKClient = PolyvLiveSDKClient.getInstance();
        polyvLiveSDKClient.initContext(parameter.application);
        polyvLiveSDKClient.enableHttpDns(parameter.isEnableHttpDns);
        polyvLiveSDKClient.enableIPV6(parameter.isEnableIPV6);
        if (parameter.isEnableBugly) {
            polyvLiveSDKClient.initCrashReport(parameter.application);
        }
    }
}
