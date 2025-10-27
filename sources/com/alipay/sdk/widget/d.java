package com.alipay.sdk.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* loaded from: classes2.dex */
final class d extends Handler {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ a f3412a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public d(a aVar, Looper looper) {
        super(looper);
        this.f3412a = aVar;
    }

    @Override // android.os.Handler
    public final void dispatchMessage(Message message) {
        this.f3412a.b();
    }
}
