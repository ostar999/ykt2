package com.plv.foundationsdk.model.log;

import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes4.dex */
public class PLVElogRequestEntity implements PLVFoundationVO {
    private String log;
    private int ltype;
    private String ptime;
    private String[] sign;

    public PLVElogRequestEntity(String[] strArr, String str, int i2) {
        this.sign = strArr;
        this.log = str;
        this.ltype = i2;
    }

    public String getLog() {
        return this.log;
    }

    public int getLtype() {
        return this.ltype;
    }

    public String getPtime() {
        return this.ptime;
    }

    public String[] getSign() {
        return this.sign;
    }

    public void setLog(String str) {
        this.log = str;
    }

    public void setLtype(int i2) {
        this.ltype = i2;
    }

    public void setPtime(String str) {
        this.ptime = str;
    }

    public void setSign(String[] strArr) {
        this.sign = strArr;
    }

    public PLVElogRequestEntity(String str, String[] strArr, String str2, int i2) {
        this.ptime = str;
        this.sign = strArr;
        this.log = str2;
        this.ltype = i2;
    }
}
