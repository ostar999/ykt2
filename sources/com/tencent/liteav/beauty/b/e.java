package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;

/* loaded from: classes6.dex */
public class e extends s {
    private int A;
    private float[] B;

    /* renamed from: x, reason: collision with root package name */
    private int f18892x;

    /* renamed from: y, reason: collision with root package name */
    private int f18893y;

    /* renamed from: z, reason: collision with root package name */
    private int f18894z;

    public void b(float[] fArr) {
        b(this.f18893y, new float[]{(float) ((fArr[0] * 0.2989d) + (fArr[1] * 0.5866d) + (fArr[2] * 0.1145d)), (float) ((fArr[0] - r2) * 0.7132d), (float) ((r6 - r2) * 0.5647d)});
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void c() {
        super.c();
        this.f18892x = GLES20.glGetUniformLocation(q(), "screenMode");
        this.f18893y = GLES20.glGetUniformLocation(q(), "screenReplaceColor");
        this.f18894z = GLES20.glGetUniformLocation(q(), "screenMirrorX");
        this.A = GLES20.glGetUniformLocation(q(), "screenMirrorY");
        b(this.B);
    }
}
