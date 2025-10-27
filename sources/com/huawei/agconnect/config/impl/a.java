package com.huawei.agconnect.config.impl;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.agconnect.AGConnectApp;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.CustomAuthProvider;
import com.huawei.agconnect.CustomCredentialsProvider;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class a extends AGConnectApp {

    /* renamed from: a, reason: collision with root package name */
    private static final Map<String, AGConnectApp> f7289a = new HashMap();

    /* renamed from: b, reason: collision with root package name */
    private static final Object f7290b = new Object();

    /* renamed from: c, reason: collision with root package name */
    private static String f7291c;

    /* renamed from: d, reason: collision with root package name */
    private AGConnectServicesConfig f7292d;

    private a(Context context, String str) {
        this.f7292d = AGConnectServicesConfig.fromContext(context, str);
    }

    public static AGConnectApp a() {
        return a(f7291c);
    }

    public static AGConnectApp a(Context context) {
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            context = applicationContext;
        }
        String packageName = context.getPackageName();
        f7291c = packageName;
        return a(context, packageName);
    }

    public static AGConnectApp a(Context context, String str) {
        AGConnectApp aGConnectApp;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("packageName can not be empty");
        }
        synchronized (f7290b) {
            Map<String, AGConnectApp> map = f7289a;
            aGConnectApp = map.get(str);
            if (aGConnectApp == null) {
                map.put(str, new a(context, str));
            }
        }
        return aGConnectApp;
    }

    public static AGConnectApp a(String str) {
        AGConnectApp aGConnectApp;
        synchronized (f7290b) {
            aGConnectApp = f7289a.get(str);
            if (aGConnectApp == null) {
                throw new IllegalStateException("you should call AGConnectApp.initialize first");
            }
        }
        return aGConnectApp;
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setApiKey(String str) {
        this.f7292d.setParam("/client/api_key", str);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setAppId(String str) {
        this.f7292d.setParam("/client/app_id", str);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setClientId(String str) {
        this.f7292d.setParam("/client/client_id", str);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setClientSecret(String str) {
        this.f7292d.setParam("/client/client_secret", str);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setCpId(String str) {
        this.f7292d.setParam("/client/cp_id", str);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setCustomAuthProvider(CustomAuthProvider customAuthProvider) {
        ((com.huawei.agconnect.core.a.b) AGConnectInstance.getInstance()).a(customAuthProvider);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setCustomCredentialsProvider(CustomCredentialsProvider customCredentialsProvider) {
        ((com.huawei.agconnect.core.a.b) AGConnectInstance.getInstance()).a(customCredentialsProvider);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setParam(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("path can not be empty");
        }
        this.f7292d.setParam(str, str2);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setProductId(String str) {
        this.f7292d.setParam("/client/product_id", str);
    }
}
