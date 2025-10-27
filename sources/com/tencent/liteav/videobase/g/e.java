package com.tencent.liteav.videobase.g;

import android.opengl.GLES20;
import com.tencent.liteav.videobase.frame.c;
import com.tencent.liteav.videobase.utils.OpenGlUtils;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Arrays;

/* loaded from: classes6.dex */
public abstract class e extends com.tencent.liteav.videobase.b.a {

    /* renamed from: f, reason: collision with root package name */
    private int f20113f;

    /* renamed from: g, reason: collision with root package name */
    private final int[] f20114g;

    /* renamed from: h, reason: collision with root package name */
    private int f20115h;

    /* renamed from: i, reason: collision with root package name */
    private int f20116i;

    public e(String str, String str2) {
        super(str, str2);
        int[] iArr = new int[2];
        this.f20114g = iArr;
        this.f20115h = 0;
        this.f20116i = 0;
        Arrays.fill(iArr, -1);
    }

    private void i() {
        int i2 = 0;
        while (true) {
            int[] iArr = this.f20114g;
            if (i2 >= iArr.length) {
                return;
            }
            OpenGlUtils.deleteTexture(iArr[i2]);
            this.f20114g[i2] = -1;
            i2++;
        }
    }

    public void a(ByteBuffer byteBuffer, int i2, int i3) {
        if (this.f20115h != i2 || this.f20116i != i3) {
            i();
            this.f20115h = i2;
            this.f20116i = i3;
        }
        OpenGlUtils.loadYuv420DataToTextures(byteBuffer, h(), i2, i3, this.f20114g);
    }

    @Override // com.tencent.liteav.videobase.b.a
    public void b(com.tencent.liteav.videobase.frame.c cVar) {
        super.b(cVar);
        this.f20113f = GLES20.glGetUniformLocation(d(), "uvTexture");
    }

    @Override // com.tencent.liteav.videobase.b.a
    public void g() {
        i();
        super.g();
    }

    public abstract int h();

    @Override // com.tencent.liteav.videobase.b.a
    public void a(int i2, c.a aVar, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        super.a(this.f20114g[0], aVar, floatBuffer, floatBuffer2);
    }

    @Override // com.tencent.liteav.videobase.b.a
    public void a(int i2) {
        super.a(i2);
        GLES20.glActiveTexture(33985);
        OpenGlUtils.bindTexture(a(), this.f20114g[1]);
        GLES20.glUniform1i(this.f20113f, 1);
    }
}
