package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes6.dex */
public class s extends com.tencent.liteav.basic.opengl.j {

    /* renamed from: r, reason: collision with root package name */
    public int f18969r;

    /* renamed from: s, reason: collision with root package name */
    public int f18970s;

    /* renamed from: t, reason: collision with root package name */
    public int f18971t;

    /* renamed from: u, reason: collision with root package name */
    public int f18972u;

    /* renamed from: v, reason: collision with root package name */
    public int f18973v;

    /* renamed from: w, reason: collision with root package name */
    public int f18974w;

    /* renamed from: x, reason: collision with root package name */
    private ByteBuffer f18975x;

    /* renamed from: y, reason: collision with root package name */
    private ByteBuffer f18976y;

    public s(String str) {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\nattribute vec4 inputTextureCoordinate3;\n \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\nvarying vec2 textureCoordinate3;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n    textureCoordinate2 = inputTextureCoordinate2.xy;\n    textureCoordinate3 = inputTextureCoordinate3.xy;\n}", str);
        a(com.tencent.liteav.basic.opengl.l.NORMAL, false, true);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public int a(int i2, int i3, int i4) {
        return a(i2, i3, i4, this.f18604m, this.f18605n);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean b() {
        boolean zB = super.b();
        GLES20.glUseProgram(q());
        this.f18969r = GLES20.glGetAttribLocation(q(), "inputTextureCoordinate2");
        this.f18972u = GLES20.glGetAttribLocation(q(), "inputTextureCoordinate3");
        this.f18970s = GLES20.glGetUniformLocation(q(), "inputImageTexture2");
        this.f18973v = GLES20.glGetUniformLocation(q(), "inputImageTexture3");
        GLES20.glEnableVertexAttribArray(this.f18969r);
        GLES20.glEnableVertexAttribArray(this.f18972u);
        return zB;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void i() {
        GLES20.glEnableVertexAttribArray(this.f18969r);
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, this.f18971t);
        GLES20.glUniform1i(this.f18970s, 3);
        this.f18975x.position(0);
        GLES20.glVertexAttribPointer(this.f18969r, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 0, (Buffer) this.f18975x);
        GLES20.glEnableVertexAttribArray(this.f18972u);
        GLES20.glActiveTexture(33988);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, this.f18974w);
        GLES20.glUniform1i(this.f18973v, 4);
        this.f18976y.position(0);
        GLES20.glVertexAttribPointer(this.f18972u, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 0, (Buffer) this.f18976y);
    }

    public int a(int i2, int i3, int i4, int i5, int i6) {
        this.f18971t = i3;
        this.f18974w = i4;
        return super.a(i2, i5, i6);
    }

    public s(String str, String str2) {
        super(str, str2);
        this.f18971t = -1;
        this.f18974w = -1;
        a(com.tencent.liteav.basic.opengl.l.NORMAL, false, true);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        super.a(i2, i3);
    }

    public void a(com.tencent.liteav.basic.opengl.l lVar, boolean z2, boolean z3) {
        float[] fArrA = com.tencent.liteav.basic.opengl.m.a(lVar, z2, z3);
        ByteBuffer byteBufferOrder = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        FloatBuffer floatBufferAsFloatBuffer = byteBufferOrder.asFloatBuffer();
        floatBufferAsFloatBuffer.put(fArrA);
        floatBufferAsFloatBuffer.flip();
        this.f18975x = byteBufferOrder;
        ByteBuffer byteBufferOrder2 = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        FloatBuffer floatBufferAsFloatBuffer2 = byteBufferOrder2.asFloatBuffer();
        floatBufferAsFloatBuffer2.put(fArrA);
        floatBufferAsFloatBuffer2.flip();
        this.f18976y = byteBufferOrder2;
    }
}
