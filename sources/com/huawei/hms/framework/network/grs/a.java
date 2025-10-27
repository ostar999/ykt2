package com.huawei.hms.framework.network.grs;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.StringUtils;
import com.huawei.hms.framework.network.grs.g.h;
import com.huawei.hms.framework.network.grs.local.model.CountryCodeBean;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: e, reason: collision with root package name */
    private static final String f7561e = "a";

    /* renamed from: a, reason: collision with root package name */
    private final GrsBaseInfo f7562a;

    /* renamed from: b, reason: collision with root package name */
    private com.huawei.hms.framework.network.grs.e.a f7563b;

    /* renamed from: c, reason: collision with root package name */
    private h f7564c;

    /* renamed from: d, reason: collision with root package name */
    private com.huawei.hms.framework.network.grs.e.c f7565d;

    /* renamed from: com.huawei.hms.framework.network.grs.a$a, reason: collision with other inner class name */
    public static class C0180a implements com.huawei.hms.framework.network.grs.b {

        /* renamed from: a, reason: collision with root package name */
        String f7566a;

        /* renamed from: b, reason: collision with root package name */
        Map<String, String> f7567b;

        /* renamed from: c, reason: collision with root package name */
        IQueryUrlsCallBack f7568c;

        /* renamed from: d, reason: collision with root package name */
        Context f7569d;

        /* renamed from: e, reason: collision with root package name */
        GrsBaseInfo f7570e;

        /* renamed from: f, reason: collision with root package name */
        com.huawei.hms.framework.network.grs.e.a f7571f;

        public C0180a(String str, Map<String, String> map, IQueryUrlsCallBack iQueryUrlsCallBack, Context context, GrsBaseInfo grsBaseInfo, com.huawei.hms.framework.network.grs.e.a aVar) {
            this.f7566a = str;
            this.f7567b = map;
            this.f7568c = iQueryUrlsCallBack;
            this.f7569d = context;
            this.f7570e = grsBaseInfo;
            this.f7571f = aVar;
        }

        @Override // com.huawei.hms.framework.network.grs.b
        public void a() {
            Map<String, String> map = this.f7567b;
            if (map != null && !map.isEmpty()) {
                Logger.i(a.f7561e, "get expired cache localUrls");
                this.f7568c.onCallBackSuccess(this.f7567b);
            } else {
                if (this.f7567b != null) {
                    this.f7568c.onCallBackFail(-3);
                    return;
                }
                Logger.i(a.f7561e, "access local config for return a domain.");
                this.f7568c.onCallBackSuccess(com.huawei.hms.framework.network.grs.f.b.a(this.f7569d.getPackageName(), this.f7570e).a(this.f7569d, this.f7571f, this.f7570e, this.f7566a, true));
            }
        }

        @Override // com.huawei.hms.framework.network.grs.b
        public void a(com.huawei.hms.framework.network.grs.g.d dVar) {
            Map<String, String> mapA = a.a(dVar.j(), this.f7566a);
            if (mapA.isEmpty()) {
                Map<String, String> map = this.f7567b;
                if (map != null && !map.isEmpty()) {
                    Logger.i(a.f7561e, "get expired cache localUrls");
                    this.f7568c.onCallBackSuccess(this.f7567b);
                    return;
                } else if (this.f7567b != null) {
                    this.f7568c.onCallBackFail(-5);
                    return;
                } else {
                    Logger.i(a.f7561e, "access local config for return a domain.");
                    mapA = com.huawei.hms.framework.network.grs.f.b.a(this.f7569d.getPackageName(), this.f7570e).a(this.f7569d, this.f7571f, this.f7570e, this.f7566a, true);
                }
            } else {
                Logger.i(a.f7561e, "get url is from remote server");
            }
            this.f7568c.onCallBackSuccess(mapA);
        }
    }

    public static class b implements com.huawei.hms.framework.network.grs.b {

        /* renamed from: a, reason: collision with root package name */
        String f7572a;

        /* renamed from: b, reason: collision with root package name */
        String f7573b;

        /* renamed from: c, reason: collision with root package name */
        IQueryUrlCallBack f7574c;

        /* renamed from: d, reason: collision with root package name */
        String f7575d;

        /* renamed from: e, reason: collision with root package name */
        Context f7576e;

        /* renamed from: f, reason: collision with root package name */
        GrsBaseInfo f7577f;

        /* renamed from: g, reason: collision with root package name */
        com.huawei.hms.framework.network.grs.e.a f7578g;

        public b(String str, String str2, IQueryUrlCallBack iQueryUrlCallBack, String str3, Context context, GrsBaseInfo grsBaseInfo, com.huawei.hms.framework.network.grs.e.a aVar) {
            this.f7572a = str;
            this.f7573b = str2;
            this.f7574c = iQueryUrlCallBack;
            this.f7575d = str3;
            this.f7576e = context;
            this.f7577f = grsBaseInfo;
            this.f7578g = aVar;
        }

        @Override // com.huawei.hms.framework.network.grs.b
        public void a() {
            if (!TextUtils.isEmpty(this.f7575d)) {
                Logger.i(a.f7561e, "get expired cache localUrl");
                this.f7574c.onCallBackSuccess(this.f7575d);
            } else {
                if (!TextUtils.isEmpty(this.f7575d)) {
                    this.f7574c.onCallBackFail(-3);
                    return;
                }
                Logger.i(a.f7561e, "access local config for return a domain.");
                this.f7574c.onCallBackSuccess(com.huawei.hms.framework.network.grs.f.b.a(this.f7576e.getPackageName(), this.f7577f).a(this.f7576e, this.f7578g, this.f7577f, this.f7572a, this.f7573b, true));
            }
        }

        @Override // com.huawei.hms.framework.network.grs.b
        public void a(com.huawei.hms.framework.network.grs.g.d dVar) {
            String strA;
            IQueryUrlCallBack iQueryUrlCallBack;
            Map<String, String> mapA = a.a(dVar.j(), this.f7572a);
            if (mapA.containsKey(this.f7573b)) {
                Logger.i(a.f7561e, "get url is from remote server");
                iQueryUrlCallBack = this.f7574c;
                strA = mapA.get(this.f7573b);
            } else if (!TextUtils.isEmpty(this.f7575d)) {
                Logger.i(a.f7561e, "get expired cache localUrl");
                this.f7574c.onCallBackSuccess(this.f7575d);
                return;
            } else if (!TextUtils.isEmpty(this.f7575d)) {
                this.f7574c.onCallBackFail(-5);
                return;
            } else {
                Logger.i(a.f7561e, "access local config for return a domain.");
                strA = com.huawei.hms.framework.network.grs.f.b.a(this.f7576e.getPackageName(), this.f7577f).a(this.f7576e, this.f7578g, this.f7577f, this.f7572a, this.f7573b, true);
                iQueryUrlCallBack = this.f7574c;
            }
            iQueryUrlCallBack.onCallBackSuccess(strA);
        }
    }

    public a(GrsBaseInfo grsBaseInfo, com.huawei.hms.framework.network.grs.e.a aVar, h hVar, com.huawei.hms.framework.network.grs.e.c cVar) {
        this.f7562a = grsBaseInfo;
        this.f7563b = aVar;
        this.f7564c = hVar;
        this.f7565d = cVar;
    }

    public static CountryCodeBean a(Context context, boolean z2) {
        return new CountryCodeBean(context, z2);
    }

    public static Map<String, Map<String, String>> a(String str) throws JSONException {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(16);
        if (TextUtils.isEmpty(str)) {
            Logger.v(f7561e, "isSpExpire jsonValue is null.");
            return concurrentHashMap;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                JSONObject jSONObject2 = jSONObject.getJSONObject(next);
                if (!TextUtils.isEmpty(next)) {
                    concurrentHashMap.put(next, a(jSONObject2));
                }
            }
            return concurrentHashMap;
        } catch (JSONException e2) {
            Logger.w(f7561e, "getServicesUrlsMap occur a JSONException: %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return concurrentHashMap;
        }
    }

    private Map<String, String> a(String str, com.huawei.hms.framework.network.grs.e.b bVar, Context context) {
        Map<String, String> mapA = this.f7563b.a(this.f7562a, str, bVar, context);
        if (mapA == null || mapA.isEmpty()) {
            Map<String, String> mapA2 = com.huawei.hms.framework.network.grs.f.b.a(context.getPackageName(), this.f7562a).a(context, this.f7563b, this.f7562a, str, false);
            return mapA2 != null ? mapA2 : new HashMap();
        }
        com.huawei.hms.framework.network.grs.f.b.a(context, this.f7562a);
        return mapA;
    }

    public static Map<String, String> a(String str, String str2) {
        HashMap map = new HashMap();
        if (TextUtils.isEmpty(str)) {
            Logger.w(f7561e, "isSpExpire jsonValue from server is null.");
            return map;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = jSONObject.has(str2) ? jSONObject.getJSONObject(str2) : null;
            if (jSONObject2 == null) {
                Logger.w(f7561e, "getServiceNameUrls: paser null from server json data by {%s}.", str2);
                return map;
            }
            Iterator<String> itKeys = jSONObject2.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, jSONObject2.get(next).toString());
            }
            return map;
        } catch (JSONException e2) {
            Logger.w(f7561e, "Method{getServiceNameUrls} query url from SP occur an JSONException: %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return map;
        }
    }

    public static Map<String, String> a(JSONObject jSONObject) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(16);
        try {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                String string = jSONObject.get(next).toString();
                if (!TextUtils.isEmpty(next) && !TextUtils.isEmpty(string)) {
                    concurrentHashMap.put(next, string);
                }
            }
            return concurrentHashMap;
        } catch (JSONException e2) {
            Logger.w(f7561e, "getServiceUrls occur a JSONException: %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return concurrentHashMap;
        }
    }

    public String a(Context context, String str) {
        com.huawei.hms.framework.network.grs.g.d dVarA = this.f7564c.a(new com.huawei.hms.framework.network.grs.g.k.c(this.f7562a, context), str, this.f7565d);
        return dVarA == null ? "" : dVarA.m() ? this.f7563b.a().a(this.f7562a.getGrsParasKey(true, true, context), "") : dVarA.j();
    }

    public String a(String str, String str2, Context context) {
        com.huawei.hms.framework.network.grs.e.b bVar = new com.huawei.hms.framework.network.grs.e.b();
        String strA = a(str, bVar, context).get(str2);
        if (bVar.a() && !TextUtils.isEmpty(strA)) {
            Logger.i(f7561e, "get unexpired cache localUrl: %s", StringUtils.anonymizeMessage(strA));
            com.huawei.hms.framework.network.grs.f.b.a(context, this.f7562a);
            return strA;
        }
        String str3 = a(a(context, str), str).get(str2);
        if (!TextUtils.isEmpty(str3)) {
            Logger.i(f7561e, "get url is from remote server");
            com.huawei.hms.framework.network.grs.f.b.a(context, this.f7562a);
            return str3;
        }
        if (TextUtils.isEmpty(strA)) {
            Logger.i(f7561e, "access local config for return a domain.");
            strA = com.huawei.hms.framework.network.grs.f.b.a(context.getPackageName(), this.f7562a).a(context, this.f7563b, this.f7562a, str, str2, true);
        } else {
            Logger.i(f7561e, "get expired cache localUrl");
        }
        Logger.i(f7561e, "synGetGrsUrl: %s", StringUtils.anonymizeMessage(strA));
        return strA;
    }

    public Map<String, String> a(String str, Context context) {
        com.huawei.hms.framework.network.grs.e.b bVar = new com.huawei.hms.framework.network.grs.e.b();
        Map<String, String> mapA = a(str, bVar, context);
        if (bVar.a() && !mapA.isEmpty()) {
            Logger.i(f7561e, "get unexpired cache localUrls: %s", StringUtils.anonymizeMessage(new JSONObject(mapA).toString()));
            com.huawei.hms.framework.network.grs.f.b.a(context, this.f7562a);
            return mapA;
        }
        Map<String, String> mapA2 = a(a(context, str), str);
        if (!mapA2.isEmpty()) {
            Logger.i(f7561e, "get url is from remote server");
            com.huawei.hms.framework.network.grs.f.b.a(context, this.f7562a);
            return mapA2;
        }
        if (mapA.isEmpty()) {
            Logger.i(f7561e, "access local config for return a domain.");
            mapA = com.huawei.hms.framework.network.grs.f.b.a(context.getPackageName(), this.f7562a).a(context, this.f7563b, this.f7562a, str, true);
        } else {
            Logger.i(f7561e, "get expired cache localUrls");
        }
        String str2 = f7561e;
        Object[] objArr = new Object[1];
        objArr[0] = StringUtils.anonymizeMessage(mapA != null ? new JSONObject(mapA).toString() : "");
        Logger.i(str2, "synGetGrsUrls: %s", objArr);
        return mapA;
    }

    public void a(String str, IQueryUrlsCallBack iQueryUrlsCallBack, Context context) {
        com.huawei.hms.framework.network.grs.e.b bVar = new com.huawei.hms.framework.network.grs.e.b();
        Map<String, String> mapA = a(str, bVar, context);
        if (!bVar.a()) {
            this.f7564c.a(new com.huawei.hms.framework.network.grs.g.k.c(this.f7562a, context), new C0180a(str, mapA, iQueryUrlsCallBack, context, this.f7562a, this.f7563b), str, this.f7565d);
            return;
        }
        String str2 = f7561e;
        Logger.i(str2, "get unexpired cache localUrls");
        if (mapA.isEmpty()) {
            iQueryUrlsCallBack.onCallBackFail(-5);
            return;
        }
        com.huawei.hms.framework.network.grs.f.b.a(context, this.f7562a);
        Logger.i(str2, "ayncGetGrsUrls: %s", StringUtils.anonymizeMessage(new JSONObject(mapA).toString()));
        iQueryUrlsCallBack.onCallBackSuccess(mapA);
    }

    public void a(String str, String str2, IQueryUrlCallBack iQueryUrlCallBack, Context context) {
        com.huawei.hms.framework.network.grs.e.b bVar = new com.huawei.hms.framework.network.grs.e.b();
        String str3 = a(str, bVar, context).get(str2);
        if (!bVar.a()) {
            this.f7564c.a(new com.huawei.hms.framework.network.grs.g.k.c(this.f7562a, context), new b(str, str2, iQueryUrlCallBack, str3, context, this.f7562a, this.f7563b), str, this.f7565d);
            return;
        }
        String str4 = f7561e;
        Logger.i(str4, "get unexpired cache localUrl");
        if (TextUtils.isEmpty(str3)) {
            iQueryUrlCallBack.onCallBackFail(-5);
            return;
        }
        com.huawei.hms.framework.network.grs.f.b.a(context, this.f7562a);
        Logger.i(str4, "ayncGetGrsUrl: %s", StringUtils.anonymizeMessage(str3));
        iQueryUrlCallBack.onCallBackSuccess(str3);
    }
}
