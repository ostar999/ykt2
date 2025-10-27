package com.huawei.hms.update.ui;

import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class UpdateBean implements Serializable {

    /* renamed from: a, reason: collision with root package name */
    public boolean f8113a;

    /* renamed from: b, reason: collision with root package name */
    public String f8114b;

    /* renamed from: c, reason: collision with root package name */
    public ArrayList f8115c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f8116d = true;

    public static <T> T a(T t2) {
        return t2;
    }

    public String getClientAppName() {
        return (String) a(this.f8114b);
    }

    public boolean getResolutionInstallHMS() {
        return this.f8113a;
    }

    public ArrayList getTypeList() {
        return (ArrayList) a(this.f8115c);
    }

    public boolean isNeedConfirm() {
        return ((Boolean) a(Boolean.valueOf(this.f8116d))).booleanValue();
    }

    public void setClientAppId(String str) {
    }

    public void setClientAppName(String str) {
        this.f8114b = str;
    }

    public void setClientPackageName(String str) {
    }

    public void setClientVersionCode(int i2) {
    }

    public void setHmsOrApkUpgrade(boolean z2) {
    }

    public void setNeedConfirm(boolean z2) {
        this.f8116d = z2;
    }

    public void setResolutionInstallHMS(boolean z2) {
        this.f8113a = z2;
    }

    public void setTypeList(ArrayList arrayList) {
        this.f8115c = arrayList;
    }
}
