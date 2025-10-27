package com.vivo.push.d;

import com.vivo.push.model.UnvarnishedMessage;

/* loaded from: classes6.dex */
final class q implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ UnvarnishedMessage f24334a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ p f24335b;

    public q(p pVar, UnvarnishedMessage unvarnishedMessage) {
        this.f24335b = pVar;
        this.f24334a = unvarnishedMessage;
    }

    @Override // java.lang.Runnable
    public final void run() {
        p pVar = this.f24335b;
        ((z) pVar).f24347b.onTransmissionMessage(((com.vivo.push.l) pVar).f24388a, this.f24334a);
    }
}
