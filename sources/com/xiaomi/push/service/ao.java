package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;
import java.util.List;

/* loaded from: classes6.dex */
public class ao {

    /* renamed from: a, reason: collision with root package name */
    private static volatile ao f25585a;

    /* renamed from: a, reason: collision with other field name */
    protected SharedPreferences f1000a;

    private ao(Context context) {
        this.f1000a = context.getSharedPreferences("mipush_oc", 0);
    }

    public static ao a(Context context) {
        if (f25585a == null) {
            synchronized (ao.class) {
                if (f25585a == null) {
                    f25585a = new ao(context);
                }
            }
        }
        return f25585a;
    }

    private String a(int i2) {
        return "normal_oc_" + i2;
    }

    private void a(SharedPreferences.Editor editor, Pair<Integer, Object> pair, String str) {
        Object obj = pair.second;
        if (obj instanceof Integer) {
            editor.putInt(str, ((Integer) obj).intValue());
            return;
        }
        if (obj instanceof Long) {
            editor.putLong(str, ((Long) obj).longValue());
        } else if (obj instanceof String) {
            editor.putString(str, (String) obj);
        } else if (obj instanceof Boolean) {
            editor.putBoolean(str, ((Boolean) obj).booleanValue());
        }
    }

    private String b(int i2) {
        return "custom_oc_" + i2;
    }

    public int a(int i2, int i3) {
        String strB = b(i2);
        if (this.f1000a.contains(strB)) {
            return this.f1000a.getInt(strB, 0);
        }
        String strA = a(i2);
        return this.f1000a.contains(strA) ? this.f1000a.getInt(strA, 0) : i3;
    }

    public void a(List<Pair<Integer, Object>> list) {
        if (com.xiaomi.push.ad.a(list)) {
            return;
        }
        SharedPreferences.Editor editorEdit = this.f1000a.edit();
        for (Pair<Integer, Object> pair : list) {
            Object obj = pair.first;
            if (obj != null && pair.second != null) {
                a(editorEdit, pair, a(((Integer) obj).intValue()));
            }
        }
        editorEdit.commit();
    }

    public boolean a(int i2, boolean z2) {
        String strB = b(i2);
        if (this.f1000a.contains(strB)) {
            return this.f1000a.getBoolean(strB, false);
        }
        String strA = a(i2);
        return this.f1000a.contains(strA) ? this.f1000a.getBoolean(strA, false) : z2;
    }

    public void b(List<Pair<Integer, Object>> list) {
        if (com.xiaomi.push.ad.a(list)) {
            return;
        }
        SharedPreferences.Editor editorEdit = this.f1000a.edit();
        for (Pair<Integer, Object> pair : list) {
            Object obj = pair.first;
            if (obj != null) {
                String strB = b(((Integer) obj).intValue());
                if (pair.second == null) {
                    editorEdit.remove(strB);
                } else {
                    a(editorEdit, pair, strB);
                }
            }
        }
        editorEdit.commit();
    }
}
