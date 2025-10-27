package com.huawei.agconnect.config.impl;

import android.content.Context;
import cn.hutool.core.text.CharPool;
import com.huawei.agconnect.AGCRoutePolicy;
import com.huawei.agconnect.AGConnectOptions;
import com.huawei.agconnect.JsonProcessingFactory;
import com.huawei.agconnect.core.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b implements AGConnectOptions {

    /* renamed from: a, reason: collision with root package name */
    private final String f7293a;

    /* renamed from: b, reason: collision with root package name */
    private final Context f7294b;

    /* renamed from: c, reason: collision with root package name */
    private final String f7295c;

    /* renamed from: d, reason: collision with root package name */
    private final AGCRoutePolicy f7296d;

    /* renamed from: e, reason: collision with root package name */
    private final d f7297e;

    /* renamed from: f, reason: collision with root package name */
    private final e f7298f;

    /* renamed from: g, reason: collision with root package name */
    private final Map<String, String> f7299g;

    /* renamed from: h, reason: collision with root package name */
    private final List<Service> f7300h;

    /* renamed from: i, reason: collision with root package name */
    private final Map<String, String> f7301i = new HashMap();

    public b(Context context, String str, AGCRoutePolicy aGCRoutePolicy, InputStream inputStream, Map<String, String> map, List<Service> list, String str2) throws IOException {
        context = context.getApplicationContext() != null ? context.getApplicationContext() : context;
        this.f7294b = context;
        str = str == null ? context.getPackageName() : str;
        this.f7295c = str;
        if (inputStream != null) {
            this.f7297e = new h(inputStream, str);
            Utils.closeQuietly(inputStream);
        } else {
            this.f7297e = new k(context, str);
        }
        this.f7298f = new e(this.f7297e);
        AGCRoutePolicy aGCRoutePolicy2 = AGCRoutePolicy.UNKNOWN;
        if (aGCRoutePolicy != aGCRoutePolicy2 && "1.0".equals(this.f7297e.a("/configuration_version", null))) {
            throw new RuntimeException("The file version does not match,please download the latest agconnect-services.json from the AGC website.");
        }
        this.f7296d = (aGCRoutePolicy == null || aGCRoutePolicy == aGCRoutePolicy2) ? Utils.getRoutePolicyFromJson(this.f7297e.a("/region", null), this.f7297e.a("/agcgw/url", null)) : aGCRoutePolicy;
        this.f7299g = Utils.fixKeyPathMap(map);
        this.f7300h = list;
        this.f7293a = str2 == null ? b() : str2;
    }

    private String a(String str) {
        Map<String, JsonProcessingFactory.JsonProcessor> processors = JsonProcessingFactory.getProcessors();
        if (!processors.containsKey(str)) {
            return null;
        }
        if (this.f7301i.containsKey(str)) {
            return this.f7301i.get(str);
        }
        JsonProcessingFactory.JsonProcessor jsonProcessor = processors.get(str);
        if (jsonProcessor == null) {
            return null;
        }
        String strProcessOption = jsonProcessor.processOption(this);
        this.f7301i.put(str, strProcessOption);
        return strProcessOption;
    }

    private String b() {
        return String.valueOf(("{packageName='" + this.f7295c + CharPool.SINGLE_QUOTE + ", routePolicy=" + this.f7296d + ", reader=" + this.f7297e.toString().hashCode() + ", customConfigMap=" + new JSONObject(this.f7299g).toString().hashCode() + '}').hashCode());
    }

    public List<Service> a() {
        return this.f7300h;
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
        return this.f7294b;
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public String getIdentifier() {
        return this.f7293a;
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
        return this.f7295c;
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public AGCRoutePolicy getRoutePolicy() {
        AGCRoutePolicy aGCRoutePolicy = this.f7296d;
        return aGCRoutePolicy == null ? AGCRoutePolicy.UNKNOWN : aGCRoutePolicy;
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public String getString(String str) {
        return getString(str, null);
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public String getString(String str, String str2) {
        if (str == null) {
            return str2;
        }
        String strFixPath = Utils.fixPath(str);
        String str3 = this.f7299g.get(strFixPath);
        if (str3 != null) {
            return str3;
        }
        String strA = a(strFixPath);
        if (strA != null) {
            return strA;
        }
        String strA2 = this.f7297e.a(strFixPath, str2);
        return e.a(strA2) ? this.f7298f.a(strA2, str2) : strA2;
    }
}
