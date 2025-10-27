package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;

/* loaded from: classes6.dex */
public class p extends com.tencent.liteav.basic.opengl.j {

    /* renamed from: r, reason: collision with root package name */
    private com.tencent.liteav.basic.opengl.a f18958r;

    @Override // com.tencent.liteav.basic.opengl.j
    public int b(int i2) {
        if (this.f18958r == null) {
            com.tencent.liteav.basic.opengl.a aVar = new com.tencent.liteav.basic.opengl.a();
            this.f18958r = aVar;
            aVar.f18492a = 0;
            aVar.f18493b = 0;
            aVar.f18494c = this.f18596e;
            aVar.f18495d = this.f18597f;
        }
        com.tencent.liteav.basic.opengl.a aVar2 = this.f18958r;
        GLES20.glViewport(aVar2.f18492a, aVar2.f18493b, aVar2.f18494c, aVar2.f18495d);
        return super.b(i2);
    }
}
