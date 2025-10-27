package com.beizi.ad.c;

import com.beizi.ad.c.d;
import com.beizi.ad.c.e;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: com.beizi.ad.c.a$a, reason: collision with other inner class name */
    public static class C0043a {

        /* renamed from: a, reason: collision with root package name */
        private String f3731a;

        /* renamed from: b, reason: collision with root package name */
        private String f3732b;

        /* renamed from: c, reason: collision with root package name */
        private String f3733c;

        /* renamed from: d, reason: collision with root package name */
        private long f3734d;

        /* renamed from: e, reason: collision with root package name */
        private String f3735e;

        /* renamed from: com.beizi.ad.c.a$a$a, reason: collision with other inner class name */
        public static final class C0044a {

            /* renamed from: a, reason: collision with root package name */
            private String f3736a;

            /* renamed from: b, reason: collision with root package name */
            private String f3737b;

            /* renamed from: c, reason: collision with root package name */
            private String f3738c;

            /* renamed from: d, reason: collision with root package name */
            private long f3739d;

            /* renamed from: e, reason: collision with root package name */
            private String f3740e;

            public C0044a a(String str) {
                this.f3736a = str;
                return this;
            }

            public C0044a b(String str) {
                this.f3737b = str;
                return this;
            }

            public C0044a c(String str) {
                this.f3738c = str;
                return this;
            }

            public C0043a a() {
                C0043a c0043a = new C0043a();
                c0043a.f3734d = this.f3739d;
                c0043a.f3733c = this.f3738c;
                c0043a.f3735e = this.f3740e;
                c0043a.f3732b = this.f3737b;
                c0043a.f3731a = this.f3736a;
                return c0043a;
            }
        }

        private C0043a() {
        }

        public JSONObject a() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("spaceID", this.f3731a);
                jSONObject.put("spaceParam", this.f3732b);
                jSONObject.put("requestUUID", this.f3733c);
                jSONObject.put("channelReserveTs", this.f3734d);
                jSONObject.put("sdkExtInfo", this.f3735e);
                return jSONObject;
            } catch (JSONException e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        private String f3741a;

        /* renamed from: b, reason: collision with root package name */
        private e.i f3742b;

        /* renamed from: c, reason: collision with root package name */
        private e.g f3743c;

        /* renamed from: d, reason: collision with root package name */
        private long f3744d;

        /* renamed from: e, reason: collision with root package name */
        private String f3745e;

        /* renamed from: f, reason: collision with root package name */
        private String f3746f;

        /* renamed from: g, reason: collision with root package name */
        private String f3747g;

        /* renamed from: h, reason: collision with root package name */
        private long f3748h;

        /* renamed from: i, reason: collision with root package name */
        private long f3749i;

        /* renamed from: j, reason: collision with root package name */
        private d.a f3750j;

        /* renamed from: k, reason: collision with root package name */
        private d.c f3751k;

        /* renamed from: l, reason: collision with root package name */
        private ArrayList<C0043a> f3752l;

        /* renamed from: com.beizi.ad.c.a$b$a, reason: collision with other inner class name */
        public static final class C0045a {

            /* renamed from: a, reason: collision with root package name */
            private String f3753a;

            /* renamed from: b, reason: collision with root package name */
            private e.i f3754b;

            /* renamed from: c, reason: collision with root package name */
            private e.g f3755c;

            /* renamed from: d, reason: collision with root package name */
            private long f3756d;

            /* renamed from: e, reason: collision with root package name */
            private String f3757e;

            /* renamed from: f, reason: collision with root package name */
            private String f3758f;

            /* renamed from: g, reason: collision with root package name */
            private String f3759g;

            /* renamed from: h, reason: collision with root package name */
            private long f3760h;

            /* renamed from: i, reason: collision with root package name */
            private long f3761i;

            /* renamed from: j, reason: collision with root package name */
            private d.a f3762j;

            /* renamed from: k, reason: collision with root package name */
            private d.c f3763k;

            /* renamed from: l, reason: collision with root package name */
            private ArrayList<C0043a> f3764l = new ArrayList<>();

            public C0045a a(String str) {
                this.f3753a = str;
                return this;
            }

            public C0045a b(String str) {
                this.f3757e = str;
                return this;
            }

            public C0045a c(String str) {
                this.f3758f = str;
                return this;
            }

            public C0045a d(String str) {
                this.f3759g = str;
                return this;
            }

            public C0045a a(e.i iVar) {
                this.f3754b = iVar;
                return this;
            }

            public C0045a b(long j2) {
                this.f3760h = j2;
                return this;
            }

            public C0045a c(long j2) {
                this.f3761i = j2;
                return this;
            }

            public C0045a a(e.g gVar) {
                this.f3755c = gVar;
                return this;
            }

            public C0045a a(long j2) {
                this.f3756d = j2;
                return this;
            }

            public C0045a a(d.a aVar) {
                this.f3762j = aVar;
                return this;
            }

            public C0045a a(d.c cVar) {
                this.f3763k = cVar;
                return this;
            }

            public b a() {
                b bVar = new b();
                bVar.f3745e = this.f3757e;
                bVar.f3750j = this.f3762j;
                bVar.f3743c = this.f3755c;
                bVar.f3748h = this.f3760h;
                bVar.f3742b = this.f3754b;
                bVar.f3744d = this.f3756d;
                bVar.f3747g = this.f3759g;
                bVar.f3749i = this.f3761i;
                bVar.f3751k = this.f3763k;
                bVar.f3752l = this.f3764l;
                bVar.f3746f = this.f3758f;
                bVar.f3741a = this.f3753a;
                return bVar;
            }

            public void a(C0043a c0043a) {
                this.f3764l.add(c0043a);
            }
        }

        public String toString() {
            return a();
        }

        private b() {
        }

        private String a() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("version", this.f3741a);
                jSONObject.put("srcType", this.f3742b);
                jSONObject.put("reqType", this.f3743c);
                jSONObject.put("timeStamp", this.f3744d);
                jSONObject.put("appid", this.f3745e);
                jSONObject.put("appVersion", this.f3746f);
                jSONObject.put("apkName", this.f3747g);
                jSONObject.put("appInstallTime", this.f3748h);
                jSONObject.put("appUpdateTime", this.f3749i);
                d.a aVar = this.f3750j;
                if (aVar != null) {
                    jSONObject.put("devInfo", aVar.a());
                }
                d.c cVar = this.f3751k;
                if (cVar != null) {
                    jSONObject.put("envInfo", cVar.a());
                }
                ArrayList<C0043a> arrayList = this.f3752l;
                if (arrayList != null && arrayList.size() > 0) {
                    JSONArray jSONArray = new JSONArray();
                    for (int i2 = 0; i2 < this.f3752l.size(); i2++) {
                        jSONArray.put(this.f3752l.get(i2).a());
                    }
                    jSONObject.put("adReqInfo", jSONArray);
                }
                return jSONObject.toString();
            } catch (JSONException e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }
}
