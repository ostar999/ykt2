package com.plv.socket.user;

import android.graphics.Color;
import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes5.dex */
public class PLVAuthorizationBean implements PLVFoundationVO {
    public static final String BGCOLOR_DEFAULT = "#4A90E2";
    public static final String FCOLOR_DEFAULT = "#ffffff";
    private String actor;
    private String bgColor;
    private String fColor;

    public PLVAuthorizationBean(String str) {
        this(str, FCOLOR_DEFAULT, BGCOLOR_DEFAULT);
    }

    public static String fitBgColor(String str) {
        try {
            Color.parseColor(str);
            return str;
        } catch (Exception unused) {
            return BGCOLOR_DEFAULT;
        }
    }

    public static String fitfColor(String str) {
        try {
            Color.parseColor(str);
            return str;
        } catch (Exception unused) {
            return FCOLOR_DEFAULT;
        }
    }

    public String getActor() {
        return this.actor;
    }

    public String getBgColor() {
        return fitBgColor(this.bgColor);
    }

    public String getfColor() {
        return fitfColor(this.fColor);
    }

    public void setActor(String str) {
        this.actor = str;
    }

    public void setBgColor(String str) {
        this.bgColor = str;
    }

    public void setfColor(String str) {
        this.fColor = str;
    }

    public String toString() {
        return "PLVAuthorizationBean{actor='" + this.actor + CharPool.SINGLE_QUOTE + ", fColor='" + this.fColor + CharPool.SINGLE_QUOTE + ", bgColor='" + this.bgColor + CharPool.SINGLE_QUOTE + '}';
    }

    public PLVAuthorizationBean(String str, String str2, String str3) {
        this.actor = str;
        this.fColor = str2;
        this.bgColor = str3;
    }
}
