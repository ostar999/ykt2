package com.beizi.ad.internal;

import android.content.Context;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.beizi.ad.R;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.StringUtil;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    static HashSet<String> f4121a;

    /* renamed from: c, reason: collision with root package name */
    private String f4123c;

    /* renamed from: j, reason: collision with root package name */
    private String f4130j;

    /* renamed from: m, reason: collision with root package name */
    private WeakReference<Context> f4133m;

    /* renamed from: n, reason: collision with root package name */
    private String f4134n;

    /* renamed from: b, reason: collision with root package name */
    private l f4122b = l.PREFETCH;

    /* renamed from: d, reason: collision with root package name */
    private int f4124d = 3;

    /* renamed from: e, reason: collision with root package name */
    private boolean f4125e = false;

    /* renamed from: f, reason: collision with root package name */
    private int f4126f = -1;

    /* renamed from: g, reason: collision with root package name */
    private int f4127g = -1;

    /* renamed from: h, reason: collision with root package name */
    private int f4128h = -1;

    /* renamed from: i, reason: collision with root package name */
    private int f4129i = -1;

    /* renamed from: k, reason: collision with root package name */
    private boolean f4131k = false;

    /* renamed from: l, reason: collision with root package name */
    private boolean f4132l = false;

    public d(Context context, String str) {
        this.f4134n = "";
        this.f4133m = new WeakReference<>(context);
        this.f4134n = str;
    }

    public void a(boolean z2) {
        this.f4131k = z2;
    }

    public Context b() {
        if (this.f4133m.get() != null) {
            return this.f4133m.get();
        }
        return null;
    }

    public String c() {
        return this.f4123c;
    }

    public int d() {
        if (this.f4122b == l.BANNER) {
            return this.f4126f;
        }
        return -1;
    }

    public int e() {
        if (this.f4122b == l.BANNER) {
            return this.f4127g;
        }
        return -1;
    }

    public int f() {
        return this.f4128h;
    }

    public int g() {
        return this.f4129i;
    }

    public boolean h() {
        return this.f4125e;
    }

    public l i() {
        return this.f4122b;
    }

    public boolean j() {
        if (!StringUtil.isEmpty(g.a().d()) && !StringUtil.isEmpty(this.f4123c)) {
            return true;
        }
        HaoboLog.e(HaoboLog.baseLogTag, HaoboLog.getString(R.string.no_identification));
        return false;
    }

    public String k() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            String str = b().getResources().getConfiguration().orientation == 2 ? "h" : "v";
            this.f4130j = str;
            if (!StringUtil.isEmpty(str)) {
                jSONObject.put("mOrientation", this.f4130j);
            }
            if (this.f4126f > 0 && this.f4127g > 0) {
                jSONObject.put(DatabaseManager.SIZE, this.f4126f + "x" + this.f4127g);
            }
            int iG = g();
            int iF = f();
            if (iG > 0 && iF > 0) {
                l lVar = this.f4122b;
                l lVar2 = l.INTERSTITIAL;
                if (!lVar.equals(lVar2) && (this.f4126f < 0 || this.f4127g < 0)) {
                    jSONObject.put("max_size", iF + "x" + iG);
                } else if (this.f4122b.equals(lVar2)) {
                    jSONObject.put(DatabaseManager.SIZE, iF + "x" + iG);
                }
            }
            return jSONObject.toString();
        } catch (JSONException e2) {
            HaoboLog.e(HaoboLog.jsonLogTag, "Failed to encode adUnitParams, err = " + e2.getMessage());
            return "";
        }
    }

    public String a() {
        return this.f4134n;
    }

    public void c(int i2) {
        this.f4127g = i2;
    }

    public void a(String str) {
        this.f4123c = str;
    }

    public void b(int i2) {
        this.f4126f = i2;
    }

    public void d(int i2) {
        this.f4128h = i2;
    }

    public void e(int i2) {
        this.f4129i = i2;
    }

    public void a(int i2) {
        this.f4124d = i2;
    }

    public void b(boolean z2) {
        this.f4125e = z2;
    }

    public void a(l lVar) {
        this.f4122b = lVar;
    }
}
