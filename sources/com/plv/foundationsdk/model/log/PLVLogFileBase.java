package com.plv.foundationsdk.model.log;

import com.plv.foundationsdk.model.PLVFoundationVO;
import com.plv.foundationsdk.utils.PLVGsonUtil;

/* loaded from: classes4.dex */
public class PLVLogFileBase implements PLVFoundationVO {
    private String exception;
    private String information;
    private String playerParam;
    private String systemLog;

    public PLVLogFileBase(String str) {
        this(str, "");
    }

    public String getException() {
        return this.exception;
    }

    public String getInformation() {
        return this.information;
    }

    public String getPlayerParam() {
        return this.playerParam;
    }

    public String getSystemLog() {
        return this.systemLog;
    }

    public void setException(String str) {
        this.exception = str;
    }

    public void setInformation(String str) {
        this.information = str;
    }

    public void setPlayerParam(String str) {
        this.playerParam = str;
    }

    public void setSystemLog(String str) {
        this.systemLog = str;
    }

    public String toLogFileString() {
        return PLVGsonUtil.toJson(this);
    }

    public PLVLogFileBase(String str, String str2) {
        this(str, str2, "");
    }

    public PLVLogFileBase(String str, String str2, String str3) {
        this.systemLog = str3;
        this.information = str;
        this.exception = str2;
    }
}
