package com.huawei.hms.framework.network.grs.f;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.framework.common.AssetsUtil;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.network.grs.GrsApp;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    private static final Map<String, b> f7612b = new ConcurrentHashMap(16);

    /* renamed from: c, reason: collision with root package name */
    private static final Object f7613c = new Object();

    /* renamed from: a, reason: collision with root package name */
    private a f7614a;

    public b(Context context, GrsBaseInfo grsBaseInfo, boolean z2) {
        a(context, z2);
        f7612b.put(context.getPackageName() + grsBaseInfo.uniqueCode(), this);
    }

    public static b a(String str, GrsBaseInfo grsBaseInfo) {
        return f7612b.get(str + grsBaseInfo.uniqueCode());
    }

    public static void a(Context context, GrsBaseInfo grsBaseInfo) {
        b bVarA = a(context.getPackageName(), grsBaseInfo);
        if (bVarA != null) {
            Logger.i("LocalManagerProxy", "appGrs is not null and clear services.");
            synchronized (f7613c) {
                bVarA.f7614a.a();
            }
        }
    }

    public com.huawei.hms.framework.network.grs.local.model.a a() {
        return this.f7614a.b();
    }

    public String a(Context context, com.huawei.hms.framework.network.grs.e.a aVar, GrsBaseInfo grsBaseInfo, String str, String str2, boolean z2) {
        synchronized (f7613c) {
            String strA = this.f7614a.a(context, aVar, grsBaseInfo, str, str2, z2);
            if (!TextUtils.isEmpty(strA) || !this.f7614a.d()) {
                return strA;
            }
            a(context, true);
            a(grsBaseInfo);
            f7612b.put(context.getPackageName() + grsBaseInfo.uniqueCode(), this);
            return this.f7614a.a(context, aVar, grsBaseInfo, str, str2, z2);
        }
    }

    public Map<String, String> a(Context context, com.huawei.hms.framework.network.grs.e.a aVar, GrsBaseInfo grsBaseInfo, String str, boolean z2) {
        synchronized (f7613c) {
            Map<String, String> mapA = this.f7614a.a(context, aVar, grsBaseInfo, str, z2);
            if ((mapA != null && !mapA.isEmpty()) || !this.f7614a.d()) {
                return mapA;
            }
            a(context, true);
            a(grsBaseInfo);
            f7612b.put(context.getPackageName() + grsBaseInfo.uniqueCode(), this);
            return this.f7614a.a(context, aVar, grsBaseInfo, str, z2);
        }
    }

    public void a(Context context, boolean z2) {
        String[] list = AssetsUtil.list(context, GrsApp.getInstance().getBrand(""));
        List<String> arrayList = list == null ? new ArrayList<>() : Arrays.asList(list);
        String appConfigName = GrsApp.getInstance().getAppConfigName();
        Logger.i("LocalManagerProxy", "appConfigName is" + appConfigName);
        this.f7614a = new d(false, z2);
        if (arrayList.contains("grs_app_global_route_config.json") || !TextUtils.isEmpty(appConfigName)) {
            this.f7614a = new d(context, appConfigName, z2);
        }
        if (!this.f7614a.e() && arrayList.contains("grs_sdk_global_route_config.json")) {
            this.f7614a = new c(context, z2);
        }
        this.f7614a.a(context, arrayList);
    }

    public void a(GrsBaseInfo grsBaseInfo) {
        this.f7614a.a(grsBaseInfo);
    }

    public Set<String> b() {
        return this.f7614a.c();
    }
}
