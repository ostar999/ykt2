package com.tencent.liteav.basic.opengl;

import android.opengl.GLES20;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;
import java.nio.FloatBuffer;

/* loaded from: classes6.dex */
public class k extends j {

    /* renamed from: r, reason: collision with root package name */
    public boolean f18630r;

    /* renamed from: s, reason: collision with root package name */
    private float[] f18631s;

    /* renamed from: t, reason: collision with root package name */
    private int f18632t;

    public k() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nuniform mat4 textureTransform;\nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n}", "#extension GL_OES_EGL_image_external : require\n\nvarying lowp vec2 textureCoordinate;\n \nuniform samplerExternalOES inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.f18631s = new float[16];
        this.f18630r = false;
        this.f18606o = true;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(float[] fArr) {
        this.f18631s = fArr;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean b() {
        boolean zB = super.b();
        this.f18632t = GLES20.glGetUniformLocation(this.f18592a, "textureTransform");
        return zB && GLES20.glGetError() == 0;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        GLES20.glUseProgram(this.f18592a);
        k();
        if (!n() || this.f18631s == null) {
            return;
        }
        floatBuffer.position(0);
        GLES20.glVertexAttribPointer(this.f18593b, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 0, (Buffer) floatBuffer);
        GLES20.glEnableVertexAttribArray(this.f18593b);
        floatBuffer2.position(0);
        GLES20.glVertexAttribPointer(this.f18595d, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 0, (Buffer) floatBuffer2);
        GLES20.glEnableVertexAttribArray(this.f18595d);
        GLES20.glUniformMatrix4fv(this.f18632t, 1, false, this.f18631s, 0);
        if (i2 != -1) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, i2);
            GLES20.glUniform1i(this.f18594c, 0);
        }
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisableVertexAttribArray(this.f18593b);
        GLES20.glDisableVertexAttribArray(this.f18595d);
        GLES20.glBindTexture(36197, 0);
    }
}
