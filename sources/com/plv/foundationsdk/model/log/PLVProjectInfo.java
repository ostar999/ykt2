package com.plv.foundationsdk.model.log;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes4.dex */
public class PLVProjectInfo implements PLVFoundationVO {
    private String SDKName;
    private String SDKUserId;
    private String SDKVersion;
    private String SDKViewerId;

    public PLVProjectInfo(String str, String str2, String str3, String str4) {
        this.SDKVersion = str;
        this.SDKName = str2;
        this.SDKViewerId = str3;
        this.SDKUserId = str4;
    }

    public String getSDKName() {
        return this.SDKName;
    }

    public String getSDKUserId() {
        return this.SDKUserId;
    }

    public String getSDKVersion() {
        return this.SDKVersion;
    }

    public String getSDKViewerId() {
        return this.SDKViewerId;
    }

    public String toString() {
        return "PLVProjectInfo{SDKVersion='" + this.SDKVersion + CharPool.SINGLE_QUOTE + ", SDKName='" + this.SDKName + CharPool.SINGLE_QUOTE + ", SDKViewerId='" + this.SDKViewerId + CharPool.SINGLE_QUOTE + ", SDKUserId='" + this.SDKUserId + CharPool.SINGLE_QUOTE + '}';
    }
}
