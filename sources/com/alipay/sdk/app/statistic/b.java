package com.alipay.sdk.app.statistic;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.util.i;
import java.io.IOException;

/* loaded from: classes2.dex */
final class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f3109a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f3110b;

    public b(Context context, String str) {
        this.f3109a = context;
        this.f3110b = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        com.alipay.sdk.packet.impl.c cVar = new com.alipay.sdk.packet.impl.c();
        try {
            String strB = i.b(this.f3109a, a.f3107a, null);
            if (!TextUtils.isEmpty(strB) && cVar.a(this.f3109a, strB) != null) {
                i.a(this.f3109a, a.f3107a);
            }
        } catch (Throwable unused) {
        }
        try {
            if (TextUtils.isEmpty(this.f3110b)) {
                return;
            }
            cVar.a(this.f3109a, this.f3110b);
        } catch (IOException unused2) {
            i.a(this.f3109a, a.f3107a, this.f3110b);
        } catch (Throwable unused3) {
        }
    }
}
