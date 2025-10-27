package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes6.dex */
public class t extends com.tencent.liteav.basic.opengl.j {

    /* renamed from: r, reason: collision with root package name */
    private ByteBuffer f18977r;

    /* renamed from: u, reason: collision with root package name */
    public int f18978u;

    /* renamed from: v, reason: collision with root package name */
    public int f18979v;

    /* renamed from: w, reason: collision with root package name */
    public int f18980w;

    public t(String str) {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\n \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n    textureCoordinate2 = inputTextureCoordinate2.xy;\n}", str);
    }

    public void a(com.tencent.liteav.basic.opengl.l lVar, boolean z2, boolean z3) {
        float[] fArrA = com.tencent.liteav.basic.opengl.m.a(lVar, z2, z3);
        ByteBuffer byteBufferOrder = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        FloatBuffer floatBufferAsFloatBuffer = byteBufferOrder.asFloatBuffer();
        floatBufferAsFloatBuffer.put(fArrA);
        floatBufferAsFloatBuffer.flip();
        this.f18977r = byteBufferOrder;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean b() {
        boolean zB = super.b();
        if (zB) {
            this.f18978u = GLES20.glGetAttribLocation(q(), "inputTextureCoordinate2");
            this.f18979v = GLES20.glGetUniformLocation(q(), "inputImageTexture2");
        }
        return zB;
    }

    public int c(int i2, int i3) {
        this.f18980w = i3;
        return a(i2, this.f18604m, this.f18605n);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void e() {
        super.e();
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void i() {
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, this.f18980w);
        GLES20.glUniform1i(this.f18979v, 3);
        int i2 = this.f18978u;
        if (i2 != -1) {
            GLES20.glEnableVertexAttribArray(i2);
            this.f18977r.position(0);
            GLES20.glVertexAttribPointer(this.f18978u, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 0, (Buffer) this.f18977r);
        }
    }

    public t(String str, String str2) {
        super(str, str2);
        this.f18978u = -1;
        this.f18980w = -1;
        a(com.tencent.liteav.basic.opengl.l.NORMAL, false, true);
    }

    public int a(int i2, int i3, int i4, int i5) {
        this.f18980w = i3;
        return a(i2, i4, i5);
    }
}
