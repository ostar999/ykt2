package com.xiaomi.push;

import java.util.Date;

/* loaded from: classes6.dex */
class bj implements gf {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ bi f24639a;

    public bj(bi biVar) {
        this.f24639a = biVar;
    }

    @Override // com.xiaomi.push.gf
    public void a(gc gcVar) {
        com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + this.f24639a.f212a.format(new Date()) + " Connection reconnected (" + this.f24639a.f209a.hashCode() + ")");
    }

    @Override // com.xiaomi.push.gf
    public void a(gc gcVar, int i2, Exception exc) {
        com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + this.f24639a.f212a.format(new Date()) + " Connection closed (" + this.f24639a.f209a.hashCode() + ")");
    }

    @Override // com.xiaomi.push.gf
    public void a(gc gcVar, Exception exc) {
        com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + this.f24639a.f212a.format(new Date()) + " Reconnection failed due to an exception (" + this.f24639a.f209a.hashCode() + ")");
        exc.printStackTrace();
    }

    @Override // com.xiaomi.push.gf
    public void b(gc gcVar) {
        com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + this.f24639a.f212a.format(new Date()) + " Connection started (" + this.f24639a.f209a.hashCode() + ")");
    }
}
