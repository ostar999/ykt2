package com.huawei.hms.framework.network.grs.f;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.StringUtils;
import com.huawei.hms.framework.network.grs.GrsApp;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public abstract class a {

    /* renamed from: a, reason: collision with root package name */
    protected com.huawei.hms.framework.network.grs.local.model.a f7605a;

    /* renamed from: b, reason: collision with root package name */
    protected List<com.huawei.hms.framework.network.grs.local.model.b> f7606b;

    /* renamed from: c, reason: collision with root package name */
    protected Map<String, String> f7607c = new ConcurrentHashMap(16);

    /* renamed from: d, reason: collision with root package name */
    protected boolean f7608d = false;

    /* renamed from: e, reason: collision with root package name */
    protected boolean f7609e = false;

    /* renamed from: f, reason: collision with root package name */
    protected boolean f7610f = false;

    /* renamed from: g, reason: collision with root package name */
    protected Set<String> f7611g = new HashSet(16);

    private Map<String, String> a(List<com.huawei.hms.framework.network.grs.local.model.b> list, GrsBaseInfo grsBaseInfo, String str) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(16);
        concurrentHashMap.put("no_route_country", "no-country");
        for (com.huawei.hms.framework.network.grs.local.model.b bVar : list) {
            if (bVar.a().contains(grsBaseInfo.getIssueCountry())) {
                concurrentHashMap.put(grsBaseInfo.getIssueCountry(), bVar.b());
            }
            if (bVar.a().contains(grsBaseInfo.getRegCountry())) {
                concurrentHashMap.put(grsBaseInfo.getRegCountry(), bVar.b());
            }
            if (bVar.a().contains(grsBaseInfo.getSerCountry())) {
                concurrentHashMap.put(grsBaseInfo.getSerCountry(), bVar.b());
            }
            if (bVar.a().contains(str)) {
                Logger.v("AbstractLocalManager", "get countryGroupID from geoIp");
                concurrentHashMap.put(str, bVar.b());
            }
        }
        return concurrentHashMap;
    }

    private int b(String str, Context context) {
        if (f(com.huawei.hms.framework.network.grs.h.c.a(str, context)) != 0) {
            return -1;
        }
        Logger.i("AbstractLocalManager", "load APP_CONFIG_FILE success{%s}.", str);
        return 0;
    }

    private int f(String str) {
        int iB;
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        if (this.f7609e && (iB = b(str)) != 0) {
            return iB;
        }
        int iA = a(str);
        return iA != 0 ? iA : e(str);
    }

    private int g(String str) {
        List<com.huawei.hms.framework.network.grs.local.model.b> list;
        int iC;
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        return (!this.f7609e || !((list = this.f7606b) == null || list.isEmpty()) || (iC = c(str)) == 0) ? d(str) : iC;
    }

    public abstract int a(String str);

    public int a(String str, Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(GrsApp.getInstance().getBrand("/"));
        sb.append(str);
        return b(sb.toString(), context) != 0 ? -1 : 0;
    }

    public String a(Context context, com.huawei.hms.framework.network.grs.e.a aVar, GrsBaseInfo grsBaseInfo, String str, String str2, boolean z2) {
        Map<String, String> mapA = a(context, aVar, grsBaseInfo, str, z2);
        if (mapA != null) {
            return mapA.get(str2);
        }
        Logger.w("AbstractLocalManager", "addresses not found by routeby in local config{%s}", str);
        return null;
    }

    public List<com.huawei.hms.framework.network.grs.local.model.b> a(JSONArray jSONArray) throws JSONException {
        JSONArray jSONArray2;
        if (jSONArray == null || jSONArray.length() == 0) {
            return new ArrayList();
        }
        try {
            ArrayList arrayList = new ArrayList(16);
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                com.huawei.hms.framework.network.grs.local.model.b bVar = new com.huawei.hms.framework.network.grs.local.model.b();
                bVar.b(jSONObject.getString("id"));
                bVar.c(jSONObject.getString("name"));
                bVar.a(jSONObject.getString("description"));
                if (jSONObject.has("countriesOrAreas")) {
                    jSONArray2 = jSONObject.getJSONArray("countriesOrAreas");
                } else if (jSONObject.has("countries")) {
                    jSONArray2 = jSONObject.getJSONArray("countries");
                } else {
                    Logger.w("AbstractLocalManager", "current country or area group has not config countries or areas.");
                    jSONArray2 = null;
                }
                HashSet hashSet = new HashSet(16);
                if (jSONArray2 != null && jSONArray2.length() != 0) {
                    for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                        hashSet.add((String) jSONArray2.get(i3));
                    }
                    bVar.a(hashSet);
                    arrayList.add(bVar);
                }
                return new ArrayList();
            }
            return arrayList;
        } catch (JSONException e2) {
            Logger.w("AbstractLocalManager", "parse countrygroup failed maybe json style is wrong. %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return new ArrayList();
        }
    }

    public Map<String, String> a(Context context, com.huawei.hms.framework.network.grs.e.a aVar, GrsBaseInfo grsBaseInfo, String str, boolean z2) {
        com.huawei.hms.framework.network.grs.local.model.a aVar2 = this.f7605a;
        if (aVar2 == null) {
            Logger.w("AbstractLocalManager", "application data is null.");
            return null;
        }
        com.huawei.hms.framework.network.grs.local.model.c cVarA = aVar2.a(str);
        if (cVarA == null) {
            Logger.w("AbstractLocalManager", "service not found in local config{%s}", str);
            return null;
        }
        String strB = e.b(context, aVar, cVarA.b(), grsBaseInfo, z2);
        if (strB == null) {
            Logger.w("AbstractLocalManager", "country not found by routeby in local config{%s}", cVarA.b());
            return null;
        }
        List<com.huawei.hms.framework.network.grs.local.model.b> listA = cVarA.a();
        com.huawei.hms.framework.network.grs.local.model.d dVarA = cVarA.a(((listA == null || listA.size() == 0) ? this.f7607c : a(listA, grsBaseInfo, strB)).get(strB));
        if (dVarA == null) {
            return null;
        }
        return dVarA.a();
    }

    public void a() {
        com.huawei.hms.framework.network.grs.local.model.a aVar = this.f7605a;
        if (aVar != null) {
            aVar.a();
            this.f7610f = true;
        }
    }

    public void a(Context context, List<String> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        for (String str : list) {
            if (Pattern.matches("^grs_sdk_global_route_config_[a-zA-Z]+\\.json$", str)) {
                if (g(com.huawei.hms.framework.network.grs.h.c.a(GrsApp.getInstance().getBrand("/") + str, context)) == 0) {
                    Logger.i("AbstractLocalManager", "load SDK_CONFIG_FILE: %s, sucess.", str);
                } else {
                    Logger.i("AbstractLocalManager", "load SDK_CONFIG_FILE: %s, failure.", str);
                }
            }
        }
    }

    public void a(GrsBaseInfo grsBaseInfo) {
        this.f7607c.put("no_route_country", "no-country");
        List<com.huawei.hms.framework.network.grs.local.model.b> list = this.f7606b;
        if (list == null || list.isEmpty()) {
            return;
        }
        for (com.huawei.hms.framework.network.grs.local.model.b bVar : this.f7606b) {
            if (bVar.a().contains(grsBaseInfo.getIssueCountry())) {
                this.f7607c.put(grsBaseInfo.getIssueCountry(), bVar.b());
            }
            if (bVar.a().contains(grsBaseInfo.getRegCountry())) {
                this.f7607c.put(grsBaseInfo.getRegCountry(), bVar.b());
            }
            if (bVar.a().contains(grsBaseInfo.getSerCountry())) {
                this.f7607c.put(grsBaseInfo.getSerCountry(), bVar.b());
            }
        }
        this.f7606b = null;
    }

    public abstract int b(String str);

    public com.huawei.hms.framework.network.grs.local.model.a b() {
        return this.f7605a;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0060 A[PHI: r10
      0x0060: PHI (r10v6 java.lang.String) = (r10v0 java.lang.String), (r10v1 java.lang.String) binds: [B:17:0x005e, B:20:0x006b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c7 A[PHI: r5
      0x00c7: PHI (r5v10 java.lang.String) = (r5v8 java.lang.String), (r5v9 java.lang.String) binds: [B:33:0x00c5, B:36:0x00d6] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void b(org.json.JSONArray r15) throws org.json.JSONException {
        /*
            r14 = this;
            if (r15 == 0) goto Lf6
            int r0 = r15.length()
            if (r0 != 0) goto La
            goto Lf6
        La:
            r0 = 0
            r1 = r0
        Lc:
            int r2 = r15.length()
            if (r1 >= r2) goto Lf6
            org.json.JSONObject r2 = r15.getJSONObject(r1)
            com.huawei.hms.framework.network.grs.local.model.c r3 = new com.huawei.hms.framework.network.grs.local.model.c
            r3.<init>()
            java.lang.String r4 = "name"
            java.lang.String r4 = r2.getString(r4)
            r3.b(r4)
            java.util.Set<java.lang.String> r5 = r14.f7611g
            boolean r5 = r5.contains(r4)
            if (r5 != 0) goto Lf2
            java.util.Set<java.lang.String> r5 = r14.f7611g
            r5.add(r4)
            boolean r5 = r14.f7609e
            if (r5 == 0) goto Lf2
            java.lang.String r5 = "routeBy"
            java.lang.String r5 = r2.getString(r5)
            r3.c(r5)
            java.lang.String r5 = "servings"
            org.json.JSONArray r5 = r2.getJSONArray(r5)
            r6 = r0
        L45:
            int r7 = r5.length()
            java.lang.String r8 = "AbstractLocalManager"
            if (r6 >= r7) goto Lbf
            java.lang.Object r7 = r5.get(r6)
            org.json.JSONObject r7 = (org.json.JSONObject) r7
            com.huawei.hms.framework.network.grs.local.model.d r9 = new com.huawei.hms.framework.network.grs.local.model.d
            r9.<init>()
            java.lang.String r10 = "countryOrAreaGroup"
            boolean r11 = r7.has(r10)
            if (r11 == 0) goto L65
        L60:
            java.lang.String r8 = r7.getString(r10)
            goto L7a
        L65:
            java.lang.String r10 = "countryGroup"
            boolean r11 = r7.has(r10)
            if (r11 == 0) goto L6e
            goto L60
        L6e:
            r10 = 1
            java.lang.Object[] r10 = new java.lang.Object[r10]
            r10[r0] = r4
            java.lang.String r11 = "maybe this service{%s} routeBy is unconditional."
            com.huawei.hms.framework.common.Logger.v(r8, r11, r10)
            java.lang.String r8 = "no-country"
        L7a:
            r9.a(r8)
            java.lang.String r8 = "addresses"
            org.json.JSONObject r7 = r7.getJSONObject(r8)
            java.util.concurrent.ConcurrentHashMap r8 = new java.util.concurrent.ConcurrentHashMap
            r10 = 16
            r8.<init>(r10)
            java.util.Iterator r10 = r7.keys()
        L8e:
            boolean r11 = r10.hasNext()
            if (r11 == 0) goto Lb2
            java.lang.Object r11 = r10.next()
            java.lang.String r11 = (java.lang.String) r11
            java.lang.String r12 = r7.getString(r11)
            boolean r13 = android.text.TextUtils.isEmpty(r11)
            if (r13 != 0) goto L8e
            boolean r12 = android.text.TextUtils.isEmpty(r12)
            if (r12 != 0) goto L8e
            java.lang.String r12 = r7.getString(r11)
            r8.put(r11, r12)
            goto L8e
        Lb2:
            r9.a(r8)
            java.lang.String r7 = r9.b()
            r3.a(r7, r9)
            int r6 = r6 + 1
            goto L45
        Lbf:
            java.lang.String r5 = "countryOrAreaGroups"
            boolean r6 = r2.has(r5)
            if (r6 == 0) goto Ld0
        Lc7:
            org.json.JSONArray r2 = r2.getJSONArray(r5)
            java.util.List r2 = r14.a(r2)
            goto Ldf
        Ld0:
            java.lang.String r5 = "countryGroups"
            boolean r6 = r2.has(r5)
            if (r6 == 0) goto Ld9
            goto Lc7
        Ld9:
            java.lang.String r2 = "service use default countryOrAreaGroup"
            com.huawei.hms.framework.common.Logger.i(r8, r2)
            r2 = 0
        Ldf:
            r3.a(r2)
            com.huawei.hms.framework.network.grs.local.model.a r2 = r14.f7605a
            if (r2 != 0) goto Led
            com.huawei.hms.framework.network.grs.local.model.a r2 = new com.huawei.hms.framework.network.grs.local.model.a
            r2.<init>()
            r14.f7605a = r2
        Led:
            com.huawei.hms.framework.network.grs.local.model.a r2 = r14.f7605a
            r2.a(r4, r3)
        Lf2:
            int r1 = r1 + 1
            goto Lc
        Lf6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.framework.network.grs.f.a.b(org.json.JSONArray):void");
    }

    public int c(String str) throws JSONException {
        JSONArray jSONArray;
        this.f7606b = new ArrayList(16);
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("countryOrAreaGroups")) {
                jSONArray = jSONObject.getJSONArray("countryOrAreaGroups");
            } else if (jSONObject.has("countryGroups")) {
                jSONArray = jSONObject.getJSONArray("countryGroups");
            } else {
                Logger.e("AbstractLocalManager", "maybe local config json is wrong because the default countryOrAreaGroups isn't config.");
                jSONArray = null;
            }
            if (jSONArray == null) {
                return -1;
            }
            this.f7606b.addAll(a(jSONArray));
            return 0;
        } catch (JSONException e2) {
            Logger.w("AbstractLocalManager", "parse countrygroup failed maybe json style is wrong. %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return -1;
        }
    }

    public Set<String> c() {
        return this.f7611g;
    }

    public int d(String str) {
        try {
            b(new JSONObject(str).getJSONArray("services"));
            return 0;
        } catch (JSONException e2) {
            Logger.w("AbstractLocalManager", "parse 2.0 services failed maybe because of json style.please check! %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return -1;
        }
    }

    public boolean d() {
        return this.f7610f;
    }

    public abstract int e(String str);

    public boolean e() {
        return this.f7608d;
    }
}
