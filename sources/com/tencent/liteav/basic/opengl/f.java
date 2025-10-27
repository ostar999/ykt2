package com.tencent.liteav.basic.opengl;

import com.tencent.liteav.basic.log.TXCLog;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private final int f18526a;

    /* renamed from: b, reason: collision with root package name */
    private final int f18527b;

    /* renamed from: c, reason: collision with root package name */
    private int f18528c = -1;

    /* renamed from: d, reason: collision with root package name */
    private int f18529d = -1;

    public f(int i2, int i3) {
        this.f18526a = i2;
        this.f18527b = i3;
    }

    public void a() {
        this.f18528c = TXCOpenGlUtils.a((ByteBuffer) null, this.f18526a, this.f18527b, -1);
        int iD = TXCOpenGlUtils.d();
        this.f18529d = iD;
        TXCOpenGlUtils.a(this.f18528c, iD);
        TXCLog.i("GLFrameBuffer", "create frameBufferId: %d, textureId: %d", Integer.valueOf(this.f18529d), Integer.valueOf(this.f18528c));
    }

    public int b() {
        return this.f18528c;
    }

    public int c() {
        return this.f18526a;
    }

    public int d() {
        return this.f18527b;
    }

    public void e() {
        TXCLog.i("GLFrameBuffer", "destroy frameBufferId: %d, textureId: %d", Integer.valueOf(this.f18529d), Integer.valueOf(this.f18528c));
        TXCOpenGlUtils.c(this.f18528c);
        this.f18528c = -1;
        TXCOpenGlUtils.b(this.f18529d);
        this.f18529d = -1;
    }
}
