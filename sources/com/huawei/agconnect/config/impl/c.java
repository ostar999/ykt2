package com.huawei.agconnect.config.impl;

import android.content.Context;
import android.util.Log;
import com.huawei.agconnect.AGCRoutePolicy;
import com.huawei.agconnect.JsonProcessingFactory;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.agconnect.config.LazyInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class c extends AGConnectServicesConfig {

    /* renamed from: a, reason: collision with root package name */
    private final Context f7302a;

    /* renamed from: b, reason: collision with root package name */
    private final String f7303b;

    /* renamed from: c, reason: collision with root package name */
    private LazyInputStream f7304c;

    /* renamed from: d, reason: collision with root package name */
    private volatile d f7305d;

    /* renamed from: e, reason: collision with root package name */
    private final Object f7306e = new Object();

    /* renamed from: f, reason: collision with root package name */
    private AGCRoutePolicy f7307f = AGCRoutePolicy.UNKNOWN;

    /* renamed from: g, reason: collision with root package name */
    private final Map<String, String> f7308g = new HashMap();

    /* renamed from: h, reason: collision with root package name */
    private volatile e f7309h;

    public c(Context context, String str) {
        this.f7302a = context;
        this.f7303b = str;
    }

    private static LazyInputStream a(Context context, final InputStream inputStream) {
        return new LazyInputStream(context) { // from class: com.huawei.agconnect.config.impl.c.1
            @Override // com.huawei.agconnect.config.LazyInputStream
            public InputStream get(Context context2) {
                return inputStream;
            }
        };
    }

    private static String a(String str) {
        int i2 = 0;
        if (str.length() > 0) {
            while (str.charAt(i2) == '/') {
                i2++;
            }
        }
        return '/' + str.substring(i2);
    }

    private void a() {
        if (this.f7305d == null) {
            synchronized (this.f7306e) {
                if (this.f7305d == null) {
                    LazyInputStream lazyInputStream = this.f7304c;
                    if (lazyInputStream != null) {
                        this.f7305d = new h(lazyInputStream.loadInputStream());
                        this.f7304c.close();
                        this.f7304c = null;
                    } else {
                        this.f7305d = new k(this.f7302a, this.f7303b);
                    }
                    this.f7309h = new e(this.f7305d);
                }
                b();
            }
        }
    }

    private String b(String str) {
        JsonProcessingFactory.JsonProcessor jsonProcessor;
        Map<String, JsonProcessingFactory.JsonProcessor> processors = JsonProcessingFactory.getProcessors();
        if (processors.containsKey(str) && (jsonProcessor = processors.get(str)) != null) {
            return jsonProcessor.processOption(this);
        }
        return null;
    }

    private void b() {
        if (this.f7307f == AGCRoutePolicy.UNKNOWN) {
            if (this.f7305d != null) {
                this.f7307f = Utils.getRoutePolicyFromJson(this.f7305d.a("/region", null), this.f7305d.a("/agcgw/url", null));
            } else {
                Log.w("AGConnectServiceConfig", "get route fail , config not ready");
            }
        }
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public boolean getBoolean(String str) {
        return getBoolean(str, false);
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public boolean getBoolean(String str, boolean z2) {
        return Boolean.parseBoolean(getString(str, String.valueOf(z2)));
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public Context getContext() {
        return this.f7302a;
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public String getIdentifier() {
        return Utils.DEFAULT_NAME;
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public int getInt(String str) {
        return getInt(str, 0);
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public int getInt(String str, int i2) {
        try {
            return Integer.parseInt(getString(str, String.valueOf(i2)));
        } catch (NumberFormatException unused) {
            return i2;
        }
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public String getPackageName() {
        return this.f7303b;
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public AGCRoutePolicy getRoutePolicy() {
        if (this.f7307f == null) {
            this.f7307f = AGCRoutePolicy.UNKNOWN;
        }
        AGCRoutePolicy aGCRoutePolicy = this.f7307f;
        AGCRoutePolicy aGCRoutePolicy2 = AGCRoutePolicy.UNKNOWN;
        if (aGCRoutePolicy == aGCRoutePolicy2 && this.f7305d == null) {
            a();
        }
        AGCRoutePolicy aGCRoutePolicy3 = this.f7307f;
        return aGCRoutePolicy3 == null ? aGCRoutePolicy2 : aGCRoutePolicy3;
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public String getString(String str) {
        return getString(str, null);
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public String getString(String str, String str2) {
        if (str == null) {
            throw new NullPointerException("path must not be null.");
        }
        if (this.f7305d == null) {
            a();
        }
        String strA = a(str);
        String str3 = this.f7308g.get(strA);
        if (str3 != null) {
            return str3;
        }
        String strB = b(strA);
        if (strB != null) {
            return strB;
        }
        String strA2 = this.f7305d.a(strA, str2);
        return e.a(strA2) ? this.f7309h.a(strA2, str2) : strA2;
    }

    @Override // com.huawei.agconnect.config.AGConnectServicesConfig
    public void overlayWith(LazyInputStream lazyInputStream) {
        this.f7304c = lazyInputStream;
    }

    @Override // com.huawei.agconnect.config.AGConnectServicesConfig
    public void overlayWith(InputStream inputStream) {
        overlayWith(a(this.f7302a, inputStream));
    }

    @Override // com.huawei.agconnect.config.AGConnectServicesConfig
    public void setParam(String str, String str2) {
        this.f7308g.put(Utils.fixPath(str), str2);
    }

    @Override // com.huawei.agconnect.config.AGConnectServicesConfig
    public void setRoutePolicy(AGCRoutePolicy aGCRoutePolicy) {
        this.f7307f = aGCRoutePolicy;
    }
}
