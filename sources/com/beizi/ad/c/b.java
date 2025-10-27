package com.beizi.ad.c;

import com.aliyun.vod.log.core.AliyunLogCommon;
import com.beizi.ad.c.e;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class b {

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private e.f f3765a;

        /* renamed from: b, reason: collision with root package name */
        private String f3766b;

        /* renamed from: c, reason: collision with root package name */
        private List<e> f3767c;

        public e.f a() {
            return this.f3765a;
        }

        public String b() {
            return this.f3766b;
        }

        public List<e> c() {
            return this.f3767c;
        }

        public int d() {
            List<e> list = this.f3767c;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        public void a(e.f fVar) {
            this.f3765a = fVar;
        }

        public void a(String str) {
            this.f3766b = str;
        }

        public void a(List<e> list) {
            this.f3767c = list;
        }
    }

    /* renamed from: com.beizi.ad.c.b$b, reason: collision with other inner class name */
    public static class C0046b {

        /* renamed from: a, reason: collision with root package name */
        private String f3768a;

        /* renamed from: b, reason: collision with root package name */
        private String f3769b;

        /* renamed from: c, reason: collision with root package name */
        private int f3770c;

        /* renamed from: d, reason: collision with root package name */
        private String f3771d;

        /* renamed from: e, reason: collision with root package name */
        private String f3772e;

        /* renamed from: f, reason: collision with root package name */
        private String f3773f;

        /* renamed from: g, reason: collision with root package name */
        private String f3774g;

        /* renamed from: h, reason: collision with root package name */
        private String f3775h;

        /* renamed from: i, reason: collision with root package name */
        private String f3776i;

        /* renamed from: j, reason: collision with root package name */
        private boolean f3777j;

        /* renamed from: k, reason: collision with root package name */
        private int f3778k;

        /* renamed from: l, reason: collision with root package name */
        private h f3779l;

        /* renamed from: m, reason: collision with root package name */
        private a f3780m;

        /* renamed from: n, reason: collision with root package name */
        private C0047b f3781n;

        /* renamed from: o, reason: collision with root package name */
        private List<h> f3782o;

        /* renamed from: p, reason: collision with root package name */
        private String f3783p;

        /* renamed from: q, reason: collision with root package name */
        private String f3784q;

        /* renamed from: r, reason: collision with root package name */
        private String f3785r;

        /* renamed from: s, reason: collision with root package name */
        private String f3786s;

        /* renamed from: t, reason: collision with root package name */
        private String f3787t;

        /* renamed from: u, reason: collision with root package name */
        private String f3788u;

        /* renamed from: com.beizi.ad.c.b$b$a */
        public static class a {

            /* renamed from: a, reason: collision with root package name */
            private List<String> f3789a;

            /* renamed from: b, reason: collision with root package name */
            private List<String> f3790b;

            /* renamed from: c, reason: collision with root package name */
            private List<String> f3791c;

            /* renamed from: d, reason: collision with root package name */
            private List<String> f3792d;

            /* renamed from: e, reason: collision with root package name */
            private List<String> f3793e;

            /* renamed from: f, reason: collision with root package name */
            private List<String> f3794f;

            /* renamed from: g, reason: collision with root package name */
            private List<String> f3795g;

            /* renamed from: h, reason: collision with root package name */
            private List<String> f3796h;

            /* renamed from: i, reason: collision with root package name */
            private List<String> f3797i;

            /* renamed from: j, reason: collision with root package name */
            private List<String> f3798j;

            /* renamed from: k, reason: collision with root package name */
            private List<String> f3799k;

            /* renamed from: l, reason: collision with root package name */
            private List<String> f3800l;

            /* renamed from: m, reason: collision with root package name */
            private List<String> f3801m;

            /* renamed from: n, reason: collision with root package name */
            private List<String> f3802n;

            /* renamed from: o, reason: collision with root package name */
            private List<String> f3803o;

            public List<String> a() {
                return this.f3789a;
            }

            public List<String> b() {
                return this.f3790b;
            }

            public List<String> c() {
                return this.f3791c;
            }

            public List<String> d() {
                return this.f3792d;
            }

            public List<String> e() {
                return this.f3793e;
            }

            public void f(List<String> list) {
                this.f3794f = list;
            }

            public void g(List<String> list) {
                this.f3795g = list;
            }

            public void h(List<String> list) {
                this.f3796h = list;
            }

            public void i(List<String> list) {
                this.f3797i = list;
            }

            public void j(List<String> list) {
                this.f3798j = list;
            }

            public void k(List<String> list) {
                this.f3799k = list;
            }

            public void l(List<String> list) {
                this.f3800l = list;
            }

            public void m(List<String> list) {
                this.f3801m = list;
            }

            public void n(List<String> list) {
                this.f3802n = list;
            }

            public void o(List<String> list) {
                this.f3803o = list;
            }

            public void a(List<String> list) {
                this.f3789a = list;
            }

            public void b(List<String> list) {
                this.f3790b = list;
            }

            public void c(List<String> list) {
                this.f3791c = list;
            }

            public void d(List<String> list) {
                this.f3792d = list;
            }

            public void e(List<String> list) {
                this.f3793e = list;
            }

            public List<String> f() {
                return this.f3800l;
            }

            public List<String> g() {
                return this.f3801m;
            }

            public List<String> h() {
                return this.f3802n;
            }

            public List<String> i() {
                return this.f3803o;
            }
        }

        /* renamed from: com.beizi.ad.c.b$b$b, reason: collision with other inner class name */
        public static class C0047b {

            /* renamed from: a, reason: collision with root package name */
            private List<String> f3804a;

            /* renamed from: b, reason: collision with root package name */
            private List<String> f3805b;

            /* renamed from: c, reason: collision with root package name */
            private List<String> f3806c;

            /* renamed from: d, reason: collision with root package name */
            private List<String> f3807d;

            /* renamed from: e, reason: collision with root package name */
            private List<String> f3808e;

            /* renamed from: f, reason: collision with root package name */
            private List<a> f3809f;

            /* renamed from: com.beizi.ad.c.b$b$b$a */
            public static class a {

                /* renamed from: a, reason: collision with root package name */
                private int f3810a;

                /* renamed from: b, reason: collision with root package name */
                private List<String> f3811b;

                public void a(int i2) {
                    this.f3810a = i2;
                }

                public void a(List<String> list) {
                    this.f3811b = list;
                }
            }

            public void a(List<String> list) {
                this.f3804a = list;
            }

            public void b(List<String> list) {
                this.f3805b = list;
            }

            public void c(List<String> list) {
                this.f3806c = list;
            }

            public void d(List<String> list) {
                this.f3807d = list;
            }

            public void e(List<String> list) {
                this.f3808e = list;
            }

            public void f(List<a> list) {
                this.f3809f = list;
            }
        }

        public String a() {
            return this.f3768a;
        }

        public String b() {
            return this.f3769b;
        }

        public int c() {
            return this.f3770c;
        }

        public String d() {
            return this.f3771d;
        }

        public String e() {
            return this.f3772e;
        }

        public String f() {
            return this.f3774g;
        }

        public String g() {
            return this.f3775h;
        }

        public String h() {
            return this.f3776i;
        }

        public h i() {
            return this.f3779l;
        }

        public a j() {
            return this.f3780m;
        }

        public C0047b k() {
            return this.f3781n;
        }

        public List<h> l() {
            return this.f3782o;
        }

        public String m() {
            return this.f3783p;
        }

        public String n() {
            return this.f3784q;
        }

        public String o() {
            return this.f3785r;
        }

        public String p() {
            return this.f3786s;
        }

        public String q() {
            return this.f3787t;
        }

        public String r() {
            return this.f3788u;
        }

        public void a(String str) {
            this.f3768a = str;
        }

        public void b(String str) {
            this.f3769b = str;
        }

        public void c(String str) {
            this.f3771d = str;
        }

        public void d(String str) {
            this.f3772e = str;
        }

        public void e(String str) {
            this.f3773f = str;
        }

        public void f(String str) {
            this.f3774g = str;
        }

        public void g(String str) {
            this.f3775h = str;
        }

        public void h(String str) {
            this.f3783p = str;
        }

        public void i(String str) {
            this.f3784q = str;
        }

        public void j(String str) {
            this.f3785r = str;
        }

        public void k(String str) {
            this.f3786s = str;
        }

        public void l(String str) {
            this.f3787t = str;
        }

        public void m(String str) {
            this.f3788u = str;
        }

        public void a(int i2) {
            this.f3770c = i2;
        }

        public void b(int i2) {
            this.f3778k = i2;
        }

        public void a(boolean z2) {
            this.f3777j = z2;
        }

        public void a(a aVar) {
            this.f3780m = aVar;
        }

        public void a(C0047b c0047b) {
            this.f3781n = c0047b;
        }

        public void a(List<h> list) {
            this.f3782o = list;
        }
    }

    public static class c {

        /* renamed from: a, reason: collision with root package name */
        private String f3812a;

        /* renamed from: b, reason: collision with root package name */
        private String f3813b;

        /* renamed from: c, reason: collision with root package name */
        private String f3814c;

        /* renamed from: d, reason: collision with root package name */
        private String f3815d;

        public String a() {
            return this.f3812a;
        }

        public String b() {
            return this.f3813b;
        }

        public String c() {
            return this.f3814c;
        }

        public String d() {
            return this.f3815d;
        }

        public void a(String str) {
            this.f3812a = str;
        }

        public void b(String str) {
            this.f3813b = str;
        }

        public void c(String str) {
            this.f3814c = str;
        }

        public void d(String str) {
            this.f3815d = str;
        }
    }

    public static class d {

        /* renamed from: a, reason: collision with root package name */
        private String f3816a;

        /* renamed from: b, reason: collision with root package name */
        private C0046b f3817b;

        /* renamed from: c, reason: collision with root package name */
        private c f3818c;

        /* renamed from: d, reason: collision with root package name */
        private List<a> f3819d;

        /* renamed from: e, reason: collision with root package name */
        private List<f> f3820e;

        /* renamed from: f, reason: collision with root package name */
        private String f3821f;

        /* renamed from: g, reason: collision with root package name */
        private String f3822g;

        public String a() {
            return this.f3816a;
        }

        public String b() {
            return this.f3822g;
        }

        public C0046b c() {
            return this.f3817b;
        }

        public int d() {
            List<a> list = this.f3819d;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        public c e() {
            return this.f3818c;
        }

        public List<a> f() {
            return this.f3819d;
        }

        public List<f> g() {
            return this.f3820e;
        }

        public int h() {
            List<f> list = this.f3820e;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        public String i() {
            return this.f3821f;
        }

        public void a(String str) {
            this.f3816a = str;
        }

        public void b(String str) {
            this.f3822g = str;
        }

        public void c(String str) {
            this.f3821f = str;
        }

        public void a(C0046b c0046b) {
            this.f3817b = c0046b;
        }

        public void a(c cVar) {
            this.f3818c = cVar;
        }

        public void a(List<a> list) {
            this.f3819d = list;
        }
    }

    public static class e {

        /* renamed from: a, reason: collision with root package name */
        private String f3823a;

        /* renamed from: b, reason: collision with root package name */
        private String f3824b;

        public String a() {
            return this.f3823a;
        }

        public String b() {
            return this.f3824b;
        }

        public void a(String str) {
            this.f3823a = str;
        }

        public void b(String str) {
            this.f3824b = str;
        }
    }

    public static class f {

        /* renamed from: a, reason: collision with root package name */
        private String f3825a;

        /* renamed from: b, reason: collision with root package name */
        private String f3826b;

        /* renamed from: c, reason: collision with root package name */
        private String f3827c;

        public String a() {
            return this.f3825a;
        }

        public String b() {
            return this.f3826b;
        }

        public String c() {
            return this.f3827c;
        }
    }

    public static class g {

        /* renamed from: a, reason: collision with root package name */
        private String f3828a;

        /* renamed from: b, reason: collision with root package name */
        private String f3829b;

        public String a() {
            return this.f3828a;
        }

        public String b() {
            return this.f3829b;
        }

        public void a(String str) {
            this.f3828a = str;
        }

        public void b(String str) {
            this.f3829b = str;
        }
    }

    public static class h {

        /* renamed from: a, reason: collision with root package name */
        private String f3830a;

        /* renamed from: b, reason: collision with root package name */
        private String f3831b;

        /* renamed from: c, reason: collision with root package name */
        private String f3832c;

        /* renamed from: d, reason: collision with root package name */
        private String f3833d;

        /* renamed from: e, reason: collision with root package name */
        private String f3834e;

        /* renamed from: f, reason: collision with root package name */
        private String f3835f;

        /* renamed from: g, reason: collision with root package name */
        private String f3836g;

        public String a() {
            return this.f3830a;
        }

        public String b() {
            return this.f3831b;
        }

        public String c() {
            return this.f3832c;
        }

        public String d() {
            return this.f3833d;
        }

        public String e() {
            return this.f3834e;
        }

        public void f(String str) {
            this.f3835f = str;
        }

        public void g(String str) {
            this.f3836g = str;
        }

        public void a(String str) {
            this.f3830a = str;
        }

        public void b(String str) {
            this.f3831b = str;
        }

        public void c(String str) {
            this.f3832c = str;
        }

        public void d(String str) {
            this.f3833d = str;
        }

        public void e(String str) {
            this.f3834e = str;
        }

        public String f() {
            return this.f3836g;
        }
    }

    public static class i {

        /* renamed from: a, reason: collision with root package name */
        private int f3837a;

        /* renamed from: b, reason: collision with root package name */
        private String f3838b;

        /* renamed from: c, reason: collision with root package name */
        private String f3839c;

        /* renamed from: d, reason: collision with root package name */
        private long f3840d;

        /* renamed from: e, reason: collision with root package name */
        private List<j> f3841e;

        public int a() {
            List<j> list = this.f3841e;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        public int b() {
            return this.f3837a;
        }

        public String c() {
            return this.f3838b;
        }

        public String d() {
            return this.f3839c;
        }

        public List<j> e() {
            return this.f3841e;
        }

        private static i c(String str) throws JSONException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
            String str2;
            JSONArray jSONArray;
            String str3;
            JSONArray jSONArray2;
            String str4;
            JSONArray jSONArray3;
            String str5;
            JSONArray jSONArray4;
            JSONArray jSONArray5;
            JSONArray jSONArray6;
            String strB = com.beizi.ad.a.a.a.b(com.beizi.ad.a.a.h.a(), str);
            String str6 = "ServerResponse";
            com.beizi.ad.a.a.i.d("ServerResponse", "decryptStr = " + strB);
            JSONObject jSONObject = new JSONObject(strB);
            i iVar = new i();
            try {
                iVar.a(jSONObject.optString("errcode"));
                iVar.b(jSONObject.optString("errmsg"));
                iVar.a(jSONObject.optInt("status"));
                iVar.a(jSONObject.optLong("ts"));
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("spaceInfo");
                ArrayList arrayList = new ArrayList();
                if (b(jSONArrayOptJSONArray)) {
                    int i2 = 0;
                    while (i2 < jSONArrayOptJSONArray.length()) {
                        j jVar = new j();
                        JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i2);
                        if (jSONObjectOptJSONObject != null) {
                            jVar.a(jSONObjectOptJSONObject.optString("spaceID"));
                            jVar.b(jSONObjectOptJSONObject.optString("spaceParam"));
                            jVar.a(e.a.a(jSONObjectOptJSONObject.optInt("adpType")));
                            jVar.a(jSONObjectOptJSONObject.optInt("refreshInterval"));
                            jVar.a(e.h.a(jSONObjectOptJSONObject.optInt("screenDirection")));
                            jVar.c(jSONObjectOptJSONObject.optString("width"));
                            jVar.d(jSONObjectOptJSONObject.optString("height"));
                            JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject("adpPosition");
                            g gVar = new g();
                            gVar.a(jSONObjectOptJSONObject2.optString("x"));
                            gVar.b(jSONObjectOptJSONObject2.optString("y"));
                            jVar.a(gVar);
                            jVar.a(jSONObjectOptJSONObject.optBoolean("autoClose"));
                            jVar.b(jSONObjectOptJSONObject.optInt("maxTime"));
                            jVar.b(jSONObjectOptJSONObject.optBoolean("manualClosable"));
                            jVar.c(jSONObjectOptJSONObject.optInt("minTime"));
                            jVar.c(jSONObjectOptJSONObject.optBoolean("wifiPreload"));
                            jVar.d(jSONObjectOptJSONObject.optBoolean(c.i.f2279j));
                            jVar.e(jSONObjectOptJSONObject.optBoolean("fullScreen"));
                            jVar.f(jSONObjectOptJSONObject.optBoolean("autoPlay"));
                            jVar.d(jSONObjectOptJSONObject.optInt("orgID"));
                            jVar.e(jSONObjectOptJSONObject.optInt("contentType"));
                            jVar.e(jSONObjectOptJSONObject.optString(com.heytap.mcssdk.constant.b.f7196u));
                            JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray("adResponse");
                            ArrayList arrayList2 = new ArrayList();
                            if (b(jSONArrayOptJSONArray2)) {
                                int i3 = 0;
                                while (i3 < jSONArrayOptJSONArray2.length()) {
                                    JSONObject jSONObjectOptJSONObject3 = jSONArrayOptJSONArray2.optJSONObject(i3);
                                    if (jSONObjectOptJSONObject3 != null) {
                                        d dVar = new d();
                                        dVar.a(jSONObjectOptJSONObject3.optString("extInfo"));
                                        dVar.b(jSONObjectOptJSONObject3.optString("adid"));
                                        JSONArray jSONArrayOptJSONArray3 = jSONObjectOptJSONObject3.optJSONArray("contentInfo");
                                        ArrayList arrayList3 = new ArrayList();
                                        if (b(jSONArrayOptJSONArray3)) {
                                            int i4 = 0;
                                            while (i4 < jSONArrayOptJSONArray3.length()) {
                                                JSONObject jSONObjectOptJSONObject4 = jSONArrayOptJSONArray3.optJSONObject(i4);
                                                a aVar = new a();
                                                JSONArray jSONArray7 = jSONArrayOptJSONArray;
                                                aVar.a(jSONObjectOptJSONObject4.optString("template"));
                                                aVar.a(e.f.a(jSONObjectOptJSONObject4.optInt("renderType")));
                                                JSONArray jSONArrayOptJSONArray4 = jSONObjectOptJSONObject4.optJSONArray("adcontentSlot");
                                                if (b(jSONArrayOptJSONArray4)) {
                                                    ArrayList arrayList4 = new ArrayList();
                                                    jSONArray4 = jSONArrayOptJSONArray2;
                                                    jSONArray5 = jSONArrayOptJSONArray3;
                                                    int i5 = 0;
                                                    while (i5 < jSONArrayOptJSONArray4.length()) {
                                                        JSONObject jSONObjectOptJSONObject5 = jSONArrayOptJSONArray4.optJSONObject(i5);
                                                        if (jSONObjectOptJSONObject5 != null) {
                                                            jSONArray6 = jSONArrayOptJSONArray4;
                                                            e eVar = new e();
                                                            str2 = str6;
                                                            try {
                                                                eVar.a(jSONObjectOptJSONObject5.optString("md5"));
                                                                eVar.b(jSONObjectOptJSONObject5.optString("content"));
                                                                arrayList4.add(eVar);
                                                            } catch (JSONException e2) {
                                                                e = e2;
                                                                com.beizi.ad.a.a.i.c(str2, "JSONException e = " + e.getMessage());
                                                                return iVar;
                                                            }
                                                        } else {
                                                            jSONArray6 = jSONArrayOptJSONArray4;
                                                            str2 = str6;
                                                        }
                                                        i5++;
                                                        jSONArrayOptJSONArray4 = jSONArray6;
                                                        str6 = str2;
                                                    }
                                                    str5 = str6;
                                                    aVar.a(arrayList4);
                                                } else {
                                                    str5 = str6;
                                                    jSONArray4 = jSONArrayOptJSONArray2;
                                                    jSONArray5 = jSONArrayOptJSONArray3;
                                                }
                                                arrayList3.add(aVar);
                                                i4++;
                                                jSONArrayOptJSONArray = jSONArray7;
                                                jSONArrayOptJSONArray2 = jSONArray4;
                                                jSONArrayOptJSONArray3 = jSONArray5;
                                                str6 = str5;
                                            }
                                            jSONArray2 = jSONArrayOptJSONArray;
                                            str4 = str6;
                                            jSONArray3 = jSONArrayOptJSONArray2;
                                            dVar.a(arrayList3);
                                        } else {
                                            jSONArray2 = jSONArrayOptJSONArray;
                                            str4 = str6;
                                            jSONArray3 = jSONArrayOptJSONArray2;
                                        }
                                        JSONObject jSONObjectOptJSONObject6 = jSONObjectOptJSONObject3.optJSONObject("adLogo");
                                        if (jSONObjectOptJSONObject6 != null) {
                                            c cVar = new c();
                                            cVar.b(jSONObjectOptJSONObject6.optString("adLabel"));
                                            cVar.a(jSONObjectOptJSONObject6.optString("adLabelUrl"));
                                            cVar.d(jSONObjectOptJSONObject6.optString("sourceLabel"));
                                            cVar.c(jSONObjectOptJSONObject6.optString("sourceUrl"));
                                            dVar.a(cVar);
                                        }
                                        dVar.c(jSONObjectOptJSONObject3.optString("price"));
                                        C0046b c0046b = new C0046b();
                                        JSONObject jSONObjectOptJSONObject7 = jSONObjectOptJSONObject3.optJSONObject("interactInfo");
                                        if (jSONObjectOptJSONObject7 != null) {
                                            JSONArray jSONArrayOptJSONArray5 = jSONObjectOptJSONObject7.optJSONArray("thirdpartInfo");
                                            if (b(jSONArrayOptJSONArray5)) {
                                                ArrayList arrayList5 = new ArrayList();
                                                for (int i6 = 0; i6 < jSONArrayOptJSONArray5.length(); i6++) {
                                                    JSONObject jSONObjectOptJSONObject8 = jSONArrayOptJSONArray5.optJSONObject(i6);
                                                    if (jSONObjectOptJSONObject8 != null) {
                                                        h hVar = new h();
                                                        hVar.b(jSONObjectOptJSONObject8.optString("clickUrl"));
                                                        hVar.a(jSONObjectOptJSONObject8.optString("viewUrl"));
                                                        hVar.c(jSONObjectOptJSONObject8.optString("convertUrl"));
                                                        hVar.g(jSONObjectOptJSONObject8.optString("onFinish"));
                                                        hVar.e(jSONObjectOptJSONObject8.optString("onPause"));
                                                        hVar.f(jSONObjectOptJSONObject8.optString("onRecover"));
                                                        hVar.d(jSONObjectOptJSONObject8.optString("onStart"));
                                                        arrayList5.add(hVar);
                                                    }
                                                }
                                                c0046b.a(arrayList5);
                                            }
                                            c0046b.c(jSONObjectOptJSONObject7.optString("apkName"));
                                            c0046b.f(jSONObjectOptJSONObject7.optString("appDesc"));
                                            c0046b.h(jSONObjectOptJSONObject7.optString("appVersion"));
                                            c0046b.i(jSONObjectOptJSONObject7.optString("appDeveloper"));
                                            c0046b.j(jSONObjectOptJSONObject7.optString("appPermissionsDesc"));
                                            c0046b.k(jSONObjectOptJSONObject7.optString("appPermissionsUrl"));
                                            c0046b.l(jSONObjectOptJSONObject7.optString("appPrivacyUrl"));
                                            c0046b.m(jSONObjectOptJSONObject7.optString("appIconURL"));
                                            c0046b.g(jSONObjectOptJSONObject7.optString("appDownloadURL"));
                                            c0046b.e(jSONObjectOptJSONObject7.optString("appStoreID"));
                                            c0046b.a(jSONObjectOptJSONObject7.optString("landingPageUrl"));
                                            c0046b.b(jSONObjectOptJSONObject7.optString("deeplinkUrl"));
                                            c0046b.a(jSONObjectOptJSONObject7.optInt("interactType"));
                                            c0046b.d(jSONObjectOptJSONObject7.optString("packageName"));
                                            c0046b.a(jSONObjectOptJSONObject7.optBoolean("useBuiltInBrow"));
                                            c0046b.b(jSONObjectOptJSONObject7.optInt("openExternal"));
                                            JSONObject jSONObjectOptJSONObject9 = jSONObjectOptJSONObject7.optJSONObject("followTrackExt");
                                            C0046b.a aVar2 = new C0046b.a();
                                            if (jSONObjectOptJSONObject9 != null) {
                                                aVar2.a(a(jSONObjectOptJSONObject9.optJSONArray("open")));
                                                aVar2.b(a(jSONObjectOptJSONObject9.optJSONArray("beginDownload")));
                                                aVar2.c(a(jSONObjectOptJSONObject9.optJSONArray(AliyunLogCommon.SubModule.download)));
                                                aVar2.d(a(jSONObjectOptJSONObject9.optJSONArray("beginInstall")));
                                                aVar2.e(a(jSONObjectOptJSONObject9.optJSONArray("install")));
                                                aVar2.f(a(jSONObjectOptJSONObject9.optJSONArray("active")));
                                                aVar2.g(a(jSONObjectOptJSONObject9.optJSONArray("close")));
                                                aVar2.h(a(jSONObjectOptJSONObject9.optJSONArray("showSlide")));
                                                aVar2.j(a(jSONObjectOptJSONObject9.optJSONArray("pageClose")));
                                                aVar2.i(a(jSONObjectOptJSONObject9.optJSONArray("pageLoad")));
                                                aVar2.k(a(jSONObjectOptJSONObject9.optJSONArray("pageAction")));
                                                aVar2.l(a(jSONObjectOptJSONObject9.optJSONArray("deepLinkSuccess")));
                                                aVar2.m(a(jSONObjectOptJSONObject9.optJSONArray("deepLinkFail")));
                                                aVar2.n(a(jSONObjectOptJSONObject9.optJSONArray("dpAppInstalled")));
                                                aVar2.o(a(jSONObjectOptJSONObject9.optJSONArray("dpAppNotInstalled")));
                                                c0046b.a(aVar2);
                                            }
                                            JSONObject jSONObjectOptJSONObject10 = jSONObjectOptJSONObject7.optJSONObject("videoTrackExt");
                                            C0046b.C0047b c0047b = new C0046b.C0047b();
                                            if (jSONObjectOptJSONObject10 != null) {
                                                c0047b.a(a(jSONObjectOptJSONObject10.optJSONArray("start")));
                                                c0047b.b(a(jSONObjectOptJSONObject10.optJSONArray("pause")));
                                                c0047b.c(a(jSONObjectOptJSONObject10.optJSONArray("continue")));
                                                c0047b.d(a(jSONObjectOptJSONObject10.optJSONArray("exit")));
                                                c0047b.e(a(jSONObjectOptJSONObject10.optJSONArray("complete")));
                                                JSONArray jSONArrayOptJSONArray6 = jSONObjectOptJSONObject10.optJSONArray("showTrack");
                                                ArrayList arrayList6 = new ArrayList();
                                                if (b(jSONArrayOptJSONArray6)) {
                                                    for (int i7 = 0; i7 < jSONArrayOptJSONArray6.length(); i7++) {
                                                        JSONObject jSONObjectOptJSONObject11 = jSONArrayOptJSONArray6.optJSONObject(i7);
                                                        if (jSONObjectOptJSONObject11 != null) {
                                                            C0046b.C0047b.a aVar3 = new C0046b.C0047b.a();
                                                            aVar3.a(jSONObjectOptJSONObject11.optInt("t"));
                                                            aVar3.a(a(jSONObjectOptJSONObject11.optJSONArray("url")));
                                                            arrayList6.add(aVar3);
                                                        }
                                                    }
                                                    c0047b.f(arrayList6);
                                                }
                                                c0046b.a(c0047b);
                                            }
                                            dVar.a(c0046b);
                                        }
                                        arrayList2.add(dVar);
                                    } else {
                                        jSONArray2 = jSONArrayOptJSONArray;
                                        str4 = str6;
                                        jSONArray3 = jSONArrayOptJSONArray2;
                                    }
                                    i3++;
                                    jSONArrayOptJSONArray = jSONArray2;
                                    jSONArrayOptJSONArray2 = jSONArray3;
                                    str6 = str4;
                                }
                                jSONArray = jSONArrayOptJSONArray;
                                str3 = str6;
                                jVar.a(arrayList2);
                            } else {
                                jSONArray = jSONArrayOptJSONArray;
                                str3 = str6;
                            }
                            arrayList.add(jVar);
                        } else {
                            jSONArray = jSONArrayOptJSONArray;
                            str3 = str6;
                        }
                        i2++;
                        jSONArrayOptJSONArray = jSONArray;
                        str6 = str3;
                    }
                    str2 = str6;
                    iVar.a(arrayList);
                }
            } catch (JSONException e3) {
                e = e3;
                str2 = str6;
            }
            return iVar;
        }

        public void a(int i2) {
            this.f3837a = i2;
        }

        public void b(String str) {
            this.f3839c = str;
        }

        private static String b(InputStream inputStream) throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = inputStream.read(bArr);
                if (i2 != -1) {
                    byteArrayOutputStream.write(bArr, 0, i2);
                } else {
                    return byteArrayOutputStream.toString("UTF-8");
                }
            }
        }

        public void a(String str) {
            this.f3838b = str;
        }

        public void a(long j2) {
            this.f3840d = j2;
        }

        public void a(List<j> list) {
            this.f3841e = list;
        }

        public static i a(InputStream inputStream) {
            if (inputStream == null) {
                return null;
            }
            try {
                return c(b(inputStream));
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            } catch (JSONException e3) {
                e3.printStackTrace();
                return null;
            }
        }

        private static boolean b(JSONArray jSONArray) {
            return jSONArray != null && jSONArray.length() > 0;
        }

        public static i a(byte[] bArr) throws JSONException {
            if (bArr == null) {
                return null;
            }
            try {
                return c(new String(bArr, "UTF-8"));
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
                return null;
            }
        }

        private static ArrayList<String> a(JSONArray jSONArray) throws JSONException {
            ArrayList<String> arrayList = new ArrayList<>();
            if (b(jSONArray)) {
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    arrayList.add(jSONArray.getString(i2));
                }
            }
            return arrayList;
        }
    }

    public static class j {

        /* renamed from: a, reason: collision with root package name */
        private String f3842a;

        /* renamed from: b, reason: collision with root package name */
        private String f3843b;

        /* renamed from: c, reason: collision with root package name */
        private e.a f3844c;

        /* renamed from: d, reason: collision with root package name */
        private int f3845d;

        /* renamed from: e, reason: collision with root package name */
        private e.h f3846e;

        /* renamed from: f, reason: collision with root package name */
        private String f3847f;

        /* renamed from: g, reason: collision with root package name */
        private String f3848g;

        /* renamed from: h, reason: collision with root package name */
        private g f3849h;

        /* renamed from: i, reason: collision with root package name */
        private boolean f3850i;

        /* renamed from: j, reason: collision with root package name */
        private int f3851j;

        /* renamed from: k, reason: collision with root package name */
        private boolean f3852k;

        /* renamed from: l, reason: collision with root package name */
        private int f3853l;

        /* renamed from: m, reason: collision with root package name */
        private boolean f3854m;

        /* renamed from: n, reason: collision with root package name */
        private boolean f3855n;

        /* renamed from: o, reason: collision with root package name */
        private boolean f3856o;

        /* renamed from: p, reason: collision with root package name */
        private boolean f3857p;

        /* renamed from: q, reason: collision with root package name */
        private boolean f3858q;

        /* renamed from: r, reason: collision with root package name */
        private int f3859r;

        /* renamed from: s, reason: collision with root package name */
        private int f3860s;

        /* renamed from: t, reason: collision with root package name */
        private String f3861t;

        /* renamed from: u, reason: collision with root package name */
        private List<d> f3862u;

        public String a() {
            return this.f3842a;
        }

        public String b() {
            return this.f3843b;
        }

        public e.a c() {
            return this.f3844c;
        }

        public int d() {
            return this.f3845d;
        }

        public e.h e() {
            return this.f3846e;
        }

        public String f() {
            return this.f3847f;
        }

        public String g() {
            return this.f3848g;
        }

        public g h() {
            return this.f3849h;
        }

        public boolean i() {
            return this.f3850i;
        }

        public int j() {
            return this.f3851j;
        }

        public boolean k() {
            return this.f3852k;
        }

        public int l() {
            return this.f3853l;
        }

        public boolean m() {
            return this.f3854m;
        }

        public boolean n() {
            return this.f3855n;
        }

        public boolean o() {
            return this.f3856o;
        }

        public boolean p() {
            return this.f3857p;
        }

        public boolean q() {
            return this.f3858q;
        }

        public List<d> r() {
            return this.f3862u;
        }

        public int s() {
            List<d> list = this.f3862u;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        public void a(String str) {
            this.f3842a = str;
        }

        public void b(String str) {
            this.f3843b = str;
        }

        public void c(String str) {
            this.f3847f = str;
        }

        public void d(String str) {
            this.f3848g = str;
        }

        public void e(boolean z2) {
            this.f3856o = z2;
        }

        public void f(boolean z2) {
            this.f3857p = z2;
        }

        public void a(e.a aVar) {
            this.f3844c = aVar;
        }

        public void b(int i2) {
            this.f3851j = i2;
        }

        public void c(int i2) {
            this.f3853l = i2;
        }

        public void d(boolean z2) {
            this.f3855n = z2;
        }

        public void e(int i2) {
            this.f3860s = i2;
        }

        public void a(int i2) {
            this.f3845d = i2;
        }

        public void b(boolean z2) {
            this.f3852k = z2;
        }

        public void c(boolean z2) {
            this.f3854m = z2;
        }

        public void d(int i2) {
            this.f3859r = i2;
        }

        public void e(String str) {
            this.f3861t = str;
        }

        public void a(e.h hVar) {
            this.f3846e = hVar;
        }

        public void a(g gVar) {
            this.f3849h = gVar;
        }

        public void a(boolean z2) {
            this.f3850i = z2;
        }

        public void a(List<d> list) {
            this.f3862u = list;
        }
    }
}
