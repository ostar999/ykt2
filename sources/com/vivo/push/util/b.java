package com.vivo.push.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    protected Context f24435a;

    /* renamed from: b, reason: collision with root package name */
    private String f24436b;

    /* renamed from: c, reason: collision with root package name */
    private volatile SharedPreferences f24437c;

    /* renamed from: d, reason: collision with root package name */
    private HashMap<String, String> f24438d = new HashMap<>();

    /* renamed from: e, reason: collision with root package name */
    private HashMap<String, Long> f24439e = new HashMap<>();

    /* renamed from: f, reason: collision with root package name */
    private HashMap<String, Integer> f24440f = new HashMap<>();

    /* renamed from: g, reason: collision with root package name */
    private HashMap<String, Boolean> f24441g = new HashMap<>();

    private List<String> c(String str) {
        Object objA;
        String[] strArrSplit;
        if (this.f24435a == null) {
            p.c("BaseSharePreference", " parsLocalIv error mContext is null ");
            return null;
        }
        ArrayList arrayList = new ArrayList();
        try {
            Context context = this.f24435a;
            objA = z.a(context, context.getPackageName(), str);
        } catch (Exception e2) {
            p.c("BaseSharePreference", " parsLocalIv error e =" + e2.getMessage());
            e2.printStackTrace();
        }
        if (objA == null) {
            return null;
        }
        String str2 = new String(Base64.decode(objA.toString(), 2));
        if (!TextUtils.isEmpty(str2) && (strArrSplit = str2.split(",#@")) != null && strArrSplit.length >= 4) {
            for (String str3 : strArrSplit) {
                arrayList.add(str3.replace(",#@", ""));
            }
            if (arrayList.size() >= 4) {
                return arrayList;
            }
        }
        return null;
    }

    public final void a(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            throw new RuntimeException("sharedFileName can't be null");
        }
        this.f24436b = str;
        this.f24437c = context.getSharedPreferences(str, 0);
        this.f24435a = context;
        List<String> listC = c("local_iv");
        if (listC == null || listC.size() < 4) {
            p.a("BaseSharePreference", " initSecureCode error list is null ");
            return;
        }
        HashMap map = new HashMap();
        map.put("com.vivo.push.secure_sub_iv", listC.get(1));
        map.put("com.vivo.push.secure_sub_key", listC.get(2));
        map.put("com.vivo.push.secure_cache_iv", listC.get(3));
        map.put("com.vivo.push.secure_cache_key", listC.get(0));
        a(map);
    }

    public final String b(String str, String str2) {
        String string = this.f24438d.get(str);
        if (string != null) {
            return string;
        }
        b();
        if (this.f24437c != null) {
            string = this.f24437c.getString(str, str2);
            if (!TextUtils.isEmpty(string) && !string.equals(str2)) {
                this.f24438d.put(str, string);
            }
        }
        return string;
    }

    public final long b(String str, long j2) {
        Long lValueOf = this.f24439e.get(str);
        if (lValueOf != null) {
            return lValueOf.longValue();
        }
        b();
        if (this.f24437c != null) {
            lValueOf = Long.valueOf(this.f24437c.getLong(str, j2));
            if (!lValueOf.equals(Long.valueOf(j2))) {
                this.f24439e.put(str, lValueOf);
            }
        }
        return lValueOf.longValue();
    }

    public final void a(String str, String str2) {
        this.f24438d.put(str, str2);
        b();
        if (this.f24437c != null) {
            SharedPreferences.Editor editorEdit = this.f24437c.edit();
            editorEdit.putString(str, str2);
            a(editorEdit);
        }
    }

    public final void b(String str) {
        this.f24439e.remove(str);
        this.f24440f.remove(str);
        this.f24441g.remove(str);
        this.f24438d.remove(str);
        b();
        if (this.f24437c != null) {
            SharedPreferences.Editor editorEdit = this.f24437c.edit();
            if (this.f24437c.contains(str)) {
                editorEdit.remove(str);
                a(editorEdit);
            }
        }
    }

    private void a(Map<String, String> map) {
        if (map.size() > 0) {
            b();
            if (this.f24437c != null) {
                SharedPreferences.Editor editorEdit = this.f24437c.edit();
                for (String str : map.keySet()) {
                    String str2 = map.get(str);
                    this.f24438d.put(str, str2);
                    editorEdit.putString(str, str2);
                }
                a(editorEdit);
            }
        }
    }

    private void b() {
        if (this.f24437c == null) {
            Context context = this.f24435a;
            if (context != null) {
                this.f24437c = context.getSharedPreferences(this.f24436b, 0);
                return;
            }
            throw new RuntimeException("SharedPreferences is not init", new Throwable());
        }
    }

    public final void a(String str, int i2) {
        this.f24440f.put(str, Integer.valueOf(i2));
        b();
        if (this.f24437c != null) {
            SharedPreferences.Editor editorEdit = this.f24437c.edit();
            editorEdit.putInt(str, i2);
            a(editorEdit);
        }
    }

    public final void a(String str, long j2) {
        this.f24439e.put(str, Long.valueOf(j2));
        b();
        if (this.f24437c != null) {
            SharedPreferences.Editor editorEdit = this.f24437c.edit();
            editorEdit.putLong(str, j2);
            a(editorEdit);
        }
    }

    public final int a(String str) {
        Integer numValueOf = this.f24440f.get(str);
        if (numValueOf != null) {
            return numValueOf.intValue();
        }
        b();
        if (this.f24437c != null) {
            numValueOf = Integer.valueOf(this.f24437c.getInt(str, 0));
            if (!numValueOf.equals(0)) {
                this.f24440f.put(str, numValueOf);
            }
        }
        return numValueOf.intValue();
    }

    public static void a(SharedPreferences.Editor editor) {
        if (editor == null) {
            return;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    public final void a() {
        this.f24439e.clear();
        this.f24440f.clear();
        this.f24441g.clear();
        this.f24438d.clear();
        b();
        if (this.f24437c != null) {
            SharedPreferences.Editor editorEdit = this.f24437c.edit();
            editorEdit.clear();
            a(editorEdit);
        }
    }
}
