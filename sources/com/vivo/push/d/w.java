package com.vivo.push.d;

import com.vivo.push.model.UPSNotificationMessage;

/* loaded from: classes6.dex */
final class w implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ UPSNotificationMessage f24343a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ u f24344b;

    public w(u uVar, UPSNotificationMessage uPSNotificationMessage) {
        this.f24344b = uVar;
        this.f24343a = uPSNotificationMessage;
    }

    @Override // java.lang.Runnable
    public final void run() {
        u uVar = this.f24344b;
        ((z) uVar).f24347b.onNotificationMessageClicked(((com.vivo.push.l) uVar).f24388a, this.f24343a);
    }
}
