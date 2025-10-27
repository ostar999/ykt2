package com.tencent.liteav.videobase.d;

import com.tencent.liteav.videobase.frame.c;
import com.tencent.liteav.videobase.utils.OpenGlUtils;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;
import java.nio.FloatBuffer;

/* loaded from: classes6.dex */
public class b extends com.tencent.liteav.videobase.b.a {

    /* renamed from: f, reason: collision with root package name */
    private int f19983f = -1;

    /* renamed from: g, reason: collision with root package name */
    private int f19984g = -1;

    /* renamed from: h, reason: collision with root package name */
    private int f19985h = -1;

    private void b(int i2, int i3) {
        if (this.f19983f == i2 && this.f19984g == i3) {
            return;
        }
        this.f19983f = i2;
        this.f19984g = i3;
        OpenGlUtils.deleteTexture(this.f19985h);
        this.f19985h = -1;
    }

    public void a(Buffer buffer, int i2, int i3) {
        b(i2, i3);
        this.f19985h = OpenGlUtils.loadTexture(R2.dimen.dm_200, buffer, i2, i3, this.f19985h);
    }

    @Override // com.tencent.liteav.videobase.b.a
    public void g() {
        super.g();
        OpenGlUtils.deleteTexture(this.f19985h);
        this.f19985h = -1;
    }

    @Override // com.tencent.liteav.videobase.b.a
    public void a(int i2, c.a aVar, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        super.a(this.f19985h, aVar, floatBuffer, floatBuffer2);
    }
}
