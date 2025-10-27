package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes6.dex */
final class j implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24569a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ f f160a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f161a;

    public j(String str, Context context, f fVar) {
        this.f161a = str;
        this.f24569a = context;
        this.f160a = fVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        String strSubstring;
        if (TextUtils.isEmpty(this.f161a)) {
            return;
        }
        String[] strArrSplit = this.f161a.split(Constants.WAVE_SEPARATOR);
        int length = strArrSplit.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                strSubstring = "";
                break;
            }
            String str = strArrSplit[i2];
            if (!TextUtils.isEmpty(str) && str.startsWith("token:")) {
                strSubstring = str.substring(str.indexOf(":") + 1);
                break;
            }
            i2++;
        }
        if (TextUtils.isEmpty(strSubstring)) {
            com.xiaomi.channel.commonutils.logger.b.m117a("ASSEMBLE_PUSH : receive incorrect token");
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m117a("ASSEMBLE_PUSH : receive correct token");
        i.d(this.f24569a, this.f160a, strSubstring);
        i.m173a(this.f24569a);
    }
}
