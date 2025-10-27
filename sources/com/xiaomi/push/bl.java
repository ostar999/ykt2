package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class bl {

    /* renamed from: a, reason: collision with root package name */
    private static int f24642a = 0;

    /* renamed from: a, reason: collision with other field name */
    private static bl f220a = null;

    /* renamed from: a, reason: collision with other field name */
    private static boolean f221a = false;

    /* renamed from: a, reason: collision with other field name */
    private Context f222a;

    /* renamed from: a, reason: collision with other field name */
    private SharedPreferences f223a;

    /* renamed from: a, reason: collision with other field name */
    private List f224a = new ArrayList();

    private bl(Context context) {
        this.f222a = context;
        this.f223a = this.f222a.getSharedPreferences("config", 0);
    }

    public static bl a() {
        return f220a;
    }

    public static synchronized void a(Context context) {
        if (f220a == null) {
            f220a = new bl(context);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m239a() {
        return false;
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m240a() {
        return this.f223a.getLong("d_m_i", Long.MAX_VALUE);
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m241a() {
        return this.f223a.getString("m_s_u", "https://metok.sys.miui.com");
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m242a() {
        synchronized (this.f224a) {
            Iterator it = this.f224a.iterator();
            while (it.hasNext()) {
                ((cr) it.next()).a();
            }
        }
    }

    public void a(cr crVar) {
        if (crVar != null) {
            synchronized (this.f224a) {
                this.f224a.add(crVar);
            }
        }
    }

    public void a(String str) {
        SharedPreferences.Editor editorEdit = this.f223a.edit();
        editorEdit.putString("config_update_time", str);
        editorEdit.commit();
    }

    public long b() {
        return this.f223a.getLong("d_s_t", Long.MAX_VALUE);
    }

    /* renamed from: b, reason: collision with other method in class */
    public String m243b() {
        return this.f223a.getString("config_update_time", "0");
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m244b() throws ParseException {
        String strA;
        int i2;
        int i3;
        String strM243b = m243b();
        String strF = bq.f();
        if (strM243b.equals(strF)) {
            return;
        }
        String strD = bq.d();
        StringBuilder sb = new StringBuilder();
        String str = "t_";
        sb.append("t_");
        sb.append(strD);
        String strA2 = bs.a("collect", sb.toString());
        if (strA2 == null || strA2.isEmpty()) {
            int i4 = 0;
            while (true) {
                strA = bs.a("collect", str + strD);
                if (strA != null && !strA.isEmpty()) {
                    i2 = 5;
                    break;
                }
                i4++;
                String str2 = str;
                i2 = 5;
                if (i4 == 5) {
                    break;
                } else {
                    str = str2;
                }
            }
            if (i4 == i2) {
                return;
            } else {
                strA2 = strA;
            }
        }
        try {
            JSONObject jSONObject = new JSONObject(new JSONObject(strA2).getString("data"));
            String string = this.f223a.getString("s_f", "");
            String strOptString = jSONObject.optString("s_f", "");
            boolean zA = bq.a(jSONObject.optInt("f_d_d", 0));
            SharedPreferences.Editor editorEdit = this.f223a.edit();
            editorEdit.putString("s_f", strOptString);
            editorEdit.putBoolean("f_d_d", zA);
            editorEdit.putString("m_s_u", jSONObject.optString("m_s_u", "https://metok.sys.miui.com"));
            editorEdit.commit();
            new Date();
            new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
            if (string != null && !string.isEmpty() && strOptString != null && !strOptString.isEmpty()) {
                Date date = simpleDateFormat.parse(string);
                Date date2 = simpleDateFormat.parse(strOptString);
                if (date2.before(date) || date2.equals(date)) {
                    a(strF);
                    m242a();
                    return;
                }
            }
            String strA3 = bs.a("collect", "f_" + strD);
            if (strA3 == null || strA3.isEmpty()) {
                int i5 = 0;
                while (true) {
                    strA3 = bs.a("collect", "f_" + strD);
                    if (strA3 != null && !strA3.isEmpty()) {
                        i3 = 5;
                        break;
                    }
                    i5++;
                    i3 = 5;
                    if (i5 == 5) {
                        break;
                    }
                }
                if (i5 == i3) {
                    return;
                }
            }
            JSONObject jSONObject2 = new JSONObject(new JSONObject(strA3).getString("data"));
            SharedPreferences.Editor editorEdit2 = this.f223a.edit();
            editorEdit2.putLong("d_m_i", jSONObject2.optLong("d_m_i", Long.MAX_VALUE));
            editorEdit2.putBoolean("d_n_s", bq.a(jSONObject2.optInt("d_n_s", f24642a)));
            editorEdit2.putLong("d_s_t", jSONObject2.optLong("d_s_t", Long.MAX_VALUE));
            editorEdit2.putLong("d_s_c_t", jSONObject2.optLong("d_s_c_t", Long.MAX_VALUE));
            editorEdit2.commit();
            a(strF);
            m242a();
        } catch (Exception unused) {
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m245b() {
        return this.f223a.getBoolean("f_d_d", true);
    }

    public long c() {
        return this.f223a.getLong("d_s_c_t", Long.MAX_VALUE);
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m246c() {
        return this.f223a.getBoolean("d_n_s", f221a);
    }
}
