package com.huawei.hms.support.api.entity.core;

import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.core.aidl.annotation.Packed;
import com.huawei.hms.support.api.entity.auth.Scope;
import java.util.List;

/* loaded from: classes4.dex */
public class ConnectInfo implements IMessageEntity {

    /* renamed from: a, reason: collision with root package name */
    @Packed
    private List<String> f8075a;

    /* renamed from: b, reason: collision with root package name */
    @Packed
    private List<Scope> f8076b;

    /* renamed from: c, reason: collision with root package name */
    @Packed
    private String f8077c;

    /* renamed from: d, reason: collision with root package name */
    @Packed
    private String f8078d;

    public ConnectInfo() {
    }

    public List<String> getApiNameList() {
        return this.f8075a;
    }

    public String getFingerprint() {
        return this.f8077c;
    }

    public List<Scope> getScopeList() {
        return this.f8076b;
    }

    public String getSubAppID() {
        return this.f8078d;
    }

    public void setApiNameList(List<String> list) {
        this.f8075a = list;
    }

    public void setFingerprint(String str) {
        this.f8077c = str;
    }

    public void setScopeList(List<Scope> list) {
        this.f8076b = list;
    }

    public void setSubAppID(String str) {
        this.f8078d = str;
    }

    public ConnectInfo(List<String> list, List<Scope> list2, String str, String str2) {
        this.f8075a = list;
        this.f8076b = list2;
        this.f8077c = str;
        this.f8078d = str2;
    }
}
