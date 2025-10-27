package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private static volatile d f24554a;

    /* renamed from: a, reason: collision with other field name */
    private Context f147a;

    /* renamed from: a, reason: collision with other field name */
    private a f148a;

    /* renamed from: a, reason: collision with other field name */
    String f149a;

    /* renamed from: a, reason: collision with other field name */
    private Map<String, a> f150a;

    public static class a {

        /* renamed from: a, reason: collision with other field name */
        private Context f151a;

        /* renamed from: a, reason: collision with other field name */
        public String f152a;

        /* renamed from: b, reason: collision with root package name */
        public String f24556b;

        /* renamed from: c, reason: collision with root package name */
        public String f24557c;

        /* renamed from: d, reason: collision with root package name */
        public String f24558d;

        /* renamed from: e, reason: collision with root package name */
        public String f24559e;

        /* renamed from: f, reason: collision with root package name */
        public String f24560f;

        /* renamed from: g, reason: collision with root package name */
        public String f24561g;

        /* renamed from: h, reason: collision with root package name */
        public String f24562h;

        /* renamed from: a, reason: collision with other field name */
        public boolean f153a = true;

        /* renamed from: b, reason: collision with other field name */
        public boolean f154b = false;

        /* renamed from: a, reason: collision with root package name */
        public int f24555a = 1;

        public a(Context context) {
            this.f151a = context;
        }

        public static a a(Context context, String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                a aVar = new a(context);
                aVar.f152a = jSONObject.getString("appId");
                aVar.f24556b = jSONObject.getString("appToken");
                aVar.f24557c = jSONObject.getString("regId");
                aVar.f24558d = jSONObject.getString("regSec");
                aVar.f24560f = jSONObject.getString("devId");
                aVar.f24559e = jSONObject.getString("vName");
                aVar.f153a = jSONObject.getBoolean("valid");
                aVar.f154b = jSONObject.getBoolean("paused");
                aVar.f24555a = jSONObject.getInt("envType");
                aVar.f24561g = jSONObject.getString("regResource");
                return aVar;
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.a(th);
                return null;
            }
        }

        private String a() {
            Context context = this.f151a;
            return com.xiaomi.push.g.m439a(context, context.getPackageName());
        }

        public static String a(a aVar) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("appId", aVar.f152a);
                jSONObject.put("appToken", aVar.f24556b);
                jSONObject.put("regId", aVar.f24557c);
                jSONObject.put("regSec", aVar.f24558d);
                jSONObject.put("devId", aVar.f24560f);
                jSONObject.put("vName", aVar.f24559e);
                jSONObject.put("valid", aVar.f153a);
                jSONObject.put("paused", aVar.f154b);
                jSONObject.put("envType", aVar.f24555a);
                jSONObject.put("regResource", aVar.f24561g);
                return jSONObject.toString();
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.a(th);
                return null;
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        public void m168a() {
            d.a(this.f151a).edit().clear().commit();
            this.f152a = null;
            this.f24556b = null;
            this.f24557c = null;
            this.f24558d = null;
            this.f24560f = null;
            this.f24559e = null;
            this.f153a = false;
            this.f154b = false;
            this.f24562h = null;
            this.f24555a = 1;
        }

        public void a(int i2) {
            this.f24555a = i2;
        }

        public void a(String str, String str2) {
            this.f24557c = str;
            this.f24558d = str2;
            this.f24560f = com.xiaomi.push.j.k(this.f151a);
            this.f24559e = a();
            this.f153a = true;
        }

        public void a(String str, String str2, String str3) {
            this.f152a = str;
            this.f24556b = str2;
            this.f24561g = str3;
            SharedPreferences.Editor editorEdit = d.a(this.f151a).edit();
            editorEdit.putString("appId", this.f152a);
            editorEdit.putString("appToken", str2);
            editorEdit.putString("regResource", str3);
            editorEdit.commit();
        }

        public void a(boolean z2) {
            this.f154b = z2;
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m169a() {
            return m170a(this.f152a, this.f24556b);
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m170a(String str, String str2) {
            return TextUtils.equals(this.f152a, str) && TextUtils.equals(this.f24556b, str2) && !TextUtils.isEmpty(this.f24557c) && !TextUtils.isEmpty(this.f24558d) && TextUtils.equals(this.f24560f, com.xiaomi.push.j.k(this.f151a));
        }

        public void b() {
            this.f153a = false;
            d.a(this.f151a).edit().putBoolean("valid", this.f153a).commit();
        }

        public void b(String str, String str2, String str3) {
            this.f24557c = str;
            this.f24558d = str2;
            this.f24560f = com.xiaomi.push.j.k(this.f151a);
            this.f24559e = a();
            this.f153a = true;
            this.f24562h = str3;
            SharedPreferences.Editor editorEdit = d.a(this.f151a).edit();
            editorEdit.putString("regId", str);
            editorEdit.putString("regSec", str2);
            editorEdit.putString("devId", this.f24560f);
            editorEdit.putString("vName", a());
            editorEdit.putBoolean("valid", true);
            editorEdit.putString("appRegion", str3);
            editorEdit.commit();
        }

        public void c(String str, String str2, String str3) {
            this.f152a = str;
            this.f24556b = str2;
            this.f24561g = str3;
        }
    }

    private d(Context context) {
        this.f147a = context;
        c();
    }

    public static SharedPreferences a(Context context) {
        return context.getSharedPreferences("mipush", 0);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static d m156a(Context context) {
        if (f24554a == null) {
            synchronized (d.class) {
                if (f24554a == null) {
                    f24554a = new d(context);
                }
            }
        }
        return f24554a;
    }

    private void c() {
        this.f148a = new a(this.f147a);
        this.f150a = new HashMap();
        SharedPreferences sharedPreferencesA = a(this.f147a);
        this.f148a.f152a = sharedPreferencesA.getString("appId", null);
        this.f148a.f24556b = sharedPreferencesA.getString("appToken", null);
        this.f148a.f24557c = sharedPreferencesA.getString("regId", null);
        this.f148a.f24558d = sharedPreferencesA.getString("regSec", null);
        this.f148a.f24560f = sharedPreferencesA.getString("devId", null);
        if (!TextUtils.isEmpty(this.f148a.f24560f) && this.f148a.f24560f.startsWith("a-")) {
            this.f148a.f24560f = com.xiaomi.push.j.k(this.f147a);
            sharedPreferencesA.edit().putString("devId", this.f148a.f24560f).commit();
        }
        this.f148a.f24559e = sharedPreferencesA.getString("vName", null);
        this.f148a.f153a = sharedPreferencesA.getBoolean("valid", true);
        this.f148a.f154b = sharedPreferencesA.getBoolean("paused", false);
        this.f148a.f24555a = sharedPreferencesA.getInt("envType", 1);
        this.f148a.f24561g = sharedPreferencesA.getString("regResource", null);
        this.f148a.f24562h = sharedPreferencesA.getString("appRegion", null);
    }

    public int a() {
        return this.f148a.f24555a;
    }

    public a a(String str) {
        if (this.f150a.containsKey(str)) {
            return this.f150a.get(str);
        }
        String str2 = "hybrid_app_info_" + str;
        SharedPreferences sharedPreferencesA = a(this.f147a);
        if (!sharedPreferencesA.contains(str2)) {
            return null;
        }
        a aVarA = a.a(this.f147a, sharedPreferencesA.getString(str2, ""));
        this.f150a.put(str2, aVarA);
        return aVarA;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m157a() {
        return this.f148a.f152a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m158a() {
        this.f148a.m168a();
    }

    public void a(int i2) {
        this.f148a.a(i2);
        a(this.f147a).edit().putInt("envType", i2).commit();
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m159a(String str) {
        SharedPreferences.Editor editorEdit = a(this.f147a).edit();
        editorEdit.putString("vName", str);
        editorEdit.commit();
        this.f148a.f24559e = str;
    }

    public void a(String str, a aVar) {
        this.f150a.put(str, aVar);
        a(this.f147a).edit().putString("hybrid_app_info_" + str, a.a(aVar)).commit();
    }

    public void a(String str, String str2, String str3) {
        this.f148a.a(str, str2, str3);
    }

    public void a(boolean z2) {
        this.f148a.a(z2);
        a(this.f147a).edit().putBoolean("paused", z2).commit();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m160a() {
        Context context = this.f147a;
        return !TextUtils.equals(com.xiaomi.push.g.m439a(context, context.getPackageName()), this.f148a.f24559e);
    }

    public boolean a(String str, String str2) {
        return this.f148a.m170a(str, str2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m161a(String str, String str2, String str3) {
        a aVarA = a(str3);
        return aVarA != null && TextUtils.equals(str, aVarA.f152a) && TextUtils.equals(str2, aVarA.f24556b);
    }

    public String b() {
        return this.f148a.f24556b;
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m162b() {
        this.f148a.b();
    }

    public void b(String str) {
        this.f150a.remove(str);
        a(this.f147a).edit().remove("hybrid_app_info_" + str).commit();
    }

    public void b(String str, String str2, String str3) {
        this.f148a.b(str, str2, str3);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m163b() {
        if (this.f148a.m169a()) {
            return true;
        }
        com.xiaomi.channel.commonutils.logger.b.m117a("Don't send message before initialization succeeded!");
        return false;
    }

    /* renamed from: c, reason: collision with other method in class */
    public String m164c() {
        return this.f148a.f24557c;
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m165c() {
        return this.f148a.m169a();
    }

    public String d() {
        return this.f148a.f24558d;
    }

    /* renamed from: d, reason: collision with other method in class */
    public boolean m166d() {
        return this.f148a.f154b;
    }

    public String e() {
        return this.f148a.f24561g;
    }

    /* renamed from: e, reason: collision with other method in class */
    public boolean m167e() {
        return !this.f148a.f153a;
    }

    public String f() {
        return this.f148a.f24562h;
    }
}
