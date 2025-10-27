package com.vivo.push.util;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes6.dex */
public final class x implements d {

    /* renamed from: a, reason: collision with root package name */
    private static String f24474a = "SpCache";

    /* renamed from: b, reason: collision with root package name */
    private static String f24475b = "com.vivo.push.cache";

    /* renamed from: c, reason: collision with root package name */
    private SharedPreferences f24476c;

    @Override // com.vivo.push.util.d
    public final boolean a(Context context) {
        if (this.f24476c != null) {
            return true;
        }
        this.f24476c = context.getSharedPreferences(f24475b, 0);
        return true;
    }

    @Override // com.vivo.push.util.d
    public final void b(String str, String str2) {
        SharedPreferences.Editor editorEdit = this.f24476c.edit();
        if (editorEdit == null) {
            p.b(f24474a, "putString error by ".concat(String.valueOf(str)));
            return;
        }
        editorEdit.putString(str, str2);
        b.a(editorEdit);
        p.d(f24474a, "putString by ".concat(String.valueOf(str)));
    }

    @Override // com.vivo.push.util.d
    public final String a(String str, String str2) {
        String string = this.f24476c.getString(str, str2);
        p.d(f24474a, "getString " + str + " is " + string);
        return string;
    }

    public final void a() {
        SharedPreferences.Editor editorEdit = this.f24476c.edit();
        if (editorEdit != null) {
            editorEdit.clear();
            b.a(editorEdit);
        }
        p.d(f24474a, "system cache is cleared");
    }
}
