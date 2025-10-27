package com.huawei.hms.hatool;

import android.content.Context;
import android.text.TextUtils;
import java.util.LinkedHashMap;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class j1 {

    /* renamed from: a, reason: collision with root package name */
    public String f7771a;

    /* renamed from: b, reason: collision with root package name */
    public m f7772b;

    public j1(String str) {
        this.f7771a = str;
        this.f7772b = new m(str);
        i.c().a(this.f7771a, this.f7772b);
    }

    public void a(int i2) {
        y.d("hmsSdk", "onReport. TAG: " + this.f7771a + ", TYPE: " + i2);
        i1.a().a(this.f7771a, i2);
    }

    public void a(int i2, String str, LinkedHashMap<String, String> linkedHashMap) throws JSONException {
        y.d("hmsSdk", "onEvent. TAG: " + this.f7771a + ", TYPE: " + i2 + ", eventId : " + str);
        if (s0.a(str) || !c(i2)) {
            y.e("hmsSdk", "onEvent() parameters check fail. Nothing will be recorded.TAG: " + this.f7771a + ", TYPE: " + i2);
            return;
        }
        if (!s0.a(linkedHashMap)) {
            y.e("hmsSdk", "onEvent() parameter mapValue will be cleared.TAG: " + this.f7771a + ", TYPE: " + i2);
            linkedHashMap = null;
        }
        i1.a().a(this.f7771a, i2, str, linkedHashMap);
    }

    public void a(Context context, String str, String str2) throws JSONException {
        y.d("hmsSdk", "onEvent(context). TAG: " + this.f7771a + ", eventId : " + str);
        if (context == null) {
            y.e("hmsSdk", "context is null in onevent ");
            return;
        }
        if (s0.a(str) || !c(0)) {
            y.e("hmsSdk", "onEvent() parameters check fail. Nothing will be recorded.TAG: " + this.f7771a);
            return;
        }
        if (!s0.a("value", str2, 65536)) {
            y.e("hmsSdk", "onEvent() parameter VALUE is overlong, content will be cleared.TAG: " + this.f7771a);
            str2 = "";
        }
        i1.a().a(this.f7771a, context, str, str2);
    }

    public void a(k kVar) {
        y.c("hmsSdk", "HiAnalyticsInstanceImpl.setMaintConf() is executed.TAG : " + this.f7771a);
        if (kVar != null) {
            this.f7772b.a(kVar);
        } else {
            y.e("hmsSdk", "HiAnalyticsInstanceImpl.setMaintConf(): config for maint is null!");
            this.f7772b.a((k) null);
        }
    }

    public final k b(int i2) {
        if (i2 == 0) {
            return this.f7772b.c();
        }
        if (i2 == 1) {
            return this.f7772b.b();
        }
        if (i2 == 2) {
            return this.f7772b.d();
        }
        if (i2 != 3) {
            return null;
        }
        return this.f7772b.a();
    }

    public void b(k kVar) {
        y.c("hmsSdk", "HiAnalyticsInstanceImpl.setOperConf() is executed.TAG: " + this.f7771a);
        if (kVar != null) {
            this.f7772b.b(kVar);
        } else {
            this.f7772b.b(null);
            y.e("hmsSdk", "HiAnalyticsInstanceImpl.setOperConf(): config for oper is null!");
        }
    }

    public final boolean c(int i2) {
        String str;
        if (i2 != 2) {
            k kVarB = b(i2);
            if (kVarB != null && !TextUtils.isEmpty(kVarB.h())) {
                return true;
            }
            str = "verifyURL(): URL check failed. type: " + i2;
        } else {
            if ("_default_config_tag".equals(this.f7771a)) {
                return true;
            }
            str = "verifyURL(): type: preins. Only default config can report Pre-install data.";
        }
        y.e("hmsSdk", str);
        return false;
    }
}
