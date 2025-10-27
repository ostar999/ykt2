package com.xiaomi.push;

import android.content.SharedPreferences;

/* loaded from: classes6.dex */
class s implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ r f25536a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f946a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f25537b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ String f25538c;

    public s(r rVar, String str, String str2, String str3) {
        this.f25536a = rVar;
        this.f946a = str;
        this.f25537b = str2;
        this.f25538c = str3;
    }

    @Override // java.lang.Runnable
    public void run() {
        SharedPreferences.Editor editorEdit = this.f25536a.f943a.getSharedPreferences(this.f946a, 4).edit();
        editorEdit.putString(this.f25537b, this.f25538c);
        editorEdit.commit();
    }
}
