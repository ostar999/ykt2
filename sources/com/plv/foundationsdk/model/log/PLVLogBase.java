package com.plv.foundationsdk.model.log;

import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes4.dex */
public class PLVLogBase implements PLVFoundationVO {
    protected String event = "default_event";
    protected String module = "default_module";
    protected String time;

    public String getEvent() {
        return this.event;
    }

    public String getModule() {
        return this.module;
    }

    public String getTime() {
        return this.time;
    }

    public void setEvent(String str) {
        this.event = str;
    }

    public void setModule(String str) {
        this.module = str;
    }

    public void setTime(String str) {
        this.time = str;
    }
}
