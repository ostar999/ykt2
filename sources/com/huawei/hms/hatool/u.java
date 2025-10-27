package com.huawei.hms.hatool;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class u {

    /* renamed from: a, reason: collision with root package name */
    public String f7868a;

    /* renamed from: b, reason: collision with root package name */
    public String f7869b;

    /* renamed from: c, reason: collision with root package name */
    public String f7870c;

    /* renamed from: d, reason: collision with root package name */
    public List<q> f7871d;

    /* renamed from: e, reason: collision with root package name */
    public String f7872e;

    public u(String str, String str2, String str3, List<q> list, String str4) {
        this.f7868a = str;
        this.f7869b = str2;
        this.f7870c = str3;
        this.f7871d = list;
        this.f7872e = str4;
    }

    public final String a(String str, String str2) {
        String str3;
        String strF = c.f(str, str2);
        if (TextUtils.isEmpty(strF)) {
            y.a("hmsSdk", "No report address,TAG : %s,TYPE: %s ", str, str2);
            return "";
        }
        if ("oper".equals(str2)) {
            str3 = "{url}/common/hmshioperqrt";
        } else if ("maint".equals(str2)) {
            str3 = "{url}/common/hmshimaintqrt";
        } else {
            if (!"diffprivacy".equals(str2)) {
                return "";
            }
            str3 = "{url}/common/common2";
        }
        return str3.replace("{url}", strF);
    }

    public void a() {
        n0 l0Var;
        o0 o0VarC;
        String str;
        String strA = a(this.f7868a, this.f7869b);
        if (!TextUtils.isEmpty(strA) || "preins".equals(this.f7869b)) {
            if (!"_hms_config_tag".equals(this.f7868a) && !"_openness_config_tag".equals(this.f7868a)) {
                b();
            }
            C0549r c0549rD = d();
            if (c0549rD != null) {
                byte[] bArrA = a(c0549rD);
                if (bArrA.length == 0) {
                    str = "request body is empty";
                } else {
                    l0Var = new i0(bArrA, strA, this.f7868a, this.f7869b, this.f7872e, this.f7871d);
                    o0VarC = o0.b();
                }
            } else {
                l0Var = new l0(this.f7871d, this.f7868a, this.f7872e, this.f7869b);
                o0VarC = o0.c();
            }
            o0VarC.a(l0Var);
            return;
        }
        str = "collectUrl is empty";
        y.e("hmsSdk", str);
    }

    public final byte[] a(C0549r c0549r) {
        String str;
        try {
            JSONObject jSONObjectA = c0549r.a();
            if (jSONObjectA != null) {
                return t0.a(jSONObjectA.toString().getBytes("UTF-8"));
            }
            y.e("hmsSdk", "uploadEvents is null");
            return new byte[0];
        } catch (UnsupportedEncodingException unused) {
            str = "sendData(): getBytes - Unsupported coding format!!";
            y.e("hmsSdk", str);
            return new byte[0];
        } catch (JSONException unused2) {
            str = "uploadEvents to json error";
            y.e("hmsSdk", str);
            return new byte[0];
        }
    }

    public final void b() {
        if (q0.a(b.i(), "backup_event", 5242880)) {
            y.d("hmsSdk", "backup file reach max limited size, discard new event ");
            return;
        }
        JSONArray jSONArrayC = c();
        String strA = u0.a(this.f7868a, this.f7869b, this.f7872e);
        y.c("hmsSdk", "Update data cached into backup,spKey: " + strA);
        g0.b(b.i(), "backup_event", strA, jSONArrayC.toString());
    }

    public final JSONArray c() {
        JSONArray jSONArray = new JSONArray();
        Iterator<q> it = this.f7871d.iterator();
        while (it.hasNext()) {
            try {
                jSONArray.put(it.next().d());
            } catch (JSONException unused) {
                y.c("hmsSdk", "handleEvents: json error,Abandon this data");
            }
        }
        return jSONArray;
    }

    public final C0549r d() {
        return d1.a(this.f7871d, this.f7868a, this.f7869b, this.f7872e, this.f7870c);
    }
}
