package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.opengl.TXCOpenGlUtils;
import com.tencent.liteav.beauty.NativeLoad;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/* loaded from: classes6.dex */
public class k extends com.tencent.liteav.basic.opengl.j {

    /* renamed from: y, reason: collision with root package name */
    private static String f18926y = "YUV420pToRGBFilter";

    /* renamed from: r, reason: collision with root package name */
    private ByteBuffer f18927r;

    /* renamed from: s, reason: collision with root package name */
    private final int f18928s;

    /* renamed from: t, reason: collision with root package name */
    private int[] f18929t;

    /* renamed from: u, reason: collision with root package name */
    private int[] f18930u;

    /* renamed from: v, reason: collision with root package name */
    private int f18931v;

    /* renamed from: w, reason: collision with root package name */
    private int f18932w;

    /* renamed from: x, reason: collision with root package name */
    private int[] f18933x;

    public k(int i2) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.f18927r = null;
        this.f18929t = null;
        this.f18930u = null;
        this.f18931v = 0;
        this.f18932w = 0;
        this.f18933x = null;
        this.f18928s = i2;
        TXCLog.i(f18926y, "yuv Type " + i2);
    }

    private int s() {
        GLES20.glBindTexture(R2.attr.tab_indicator_height, this.f18933x[0]);
        ByteBuffer byteBuffer = this.f18927r;
        if (byteBuffer != null) {
            GLES20.glTexImage2D(R2.attr.tab_indicator_height, 0, R2.dimen.dm_200, this.f18596e, this.f18597f, 0, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, byteBuffer);
        }
        return this.f18933x[0];
    }

    private void t() {
        int[] iArr = this.f18929t;
        if (iArr != null && iArr[0] > 0) {
            GLES20.glDeleteTextures(1, iArr, 0);
            this.f18929t = null;
        }
        int[] iArr2 = this.f18930u;
        if (iArr2 != null && iArr2[0] > 0) {
            GLES20.glDeleteTextures(1, iArr2, 0);
            this.f18930u = null;
        }
        int[] iArr3 = this.f18933x;
        if (iArr3 == null || iArr3[0] <= 0) {
            return;
        }
        GLES20.glDeleteTextures(1, iArr3, 0);
        this.f18933x = null;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean a() {
        int i2 = this.f18928s;
        int i3 = 7;
        if (i2 != 1) {
            if (i2 == 3) {
                i3 = 9;
            } else {
                if (i2 == 2) {
                    return super.a();
                }
                TXCLog.e(f18926y, "don't support yuv format " + this.f18928s);
            }
        }
        int iNativeLoadGLProgram = NativeLoad.nativeLoadGLProgram(i3);
        this.f18592a = iNativeLoadGLProgram;
        if (iNativeLoadGLProgram == 0 || !b()) {
            this.f18598g = false;
        } else {
            this.f18598g = true;
        }
        c();
        return this.f18598g;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void e() {
        super.e();
        t();
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void i() {
        int[] iArr;
        int i2;
        super.i();
        if (this.f18596e % 4 != 0) {
            iArr = new int[1];
            GLES20.glGetIntegerv(R2.attr.srlHeaderMaxDragRate, iArr, 0);
            GLES20.glPixelStorei(R2.attr.srlHeaderMaxDragRate, 1);
        } else {
            iArr = null;
        }
        int i3 = this.f18928s;
        if (2 == i3) {
            s();
        } else {
            TXCOpenGlUtils.a(this.f18927r, i3 == 1 ? R2.dimen.dm_224 : R2.dimen.dm_3, this.f18596e, this.f18597f, new int[]{this.f18929t[0], this.f18930u[0]});
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, this.f18929t[0]);
            GLES20.glUniform1i(this.f18931v, 0);
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, this.f18930u[0]);
            GLES20.glUniform1i(this.f18932w, 1);
        }
        if (this.f18596e % 4 != 0) {
            if (iArr == null || (i2 = iArr[0]) <= 0) {
                GLES20.glPixelStorei(R2.attr.srlHeaderMaxDragRate, 4);
            } else {
                GLES20.glPixelStorei(R2.attr.srlHeaderMaxDragRate, i2);
            }
        }
    }

    public int r() {
        if (2 != this.f18928s) {
            return super.b(-1);
        }
        int iS = s();
        GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
        return iS;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        if (this.f18597f == i3 && this.f18596e == i2) {
            return;
        }
        t();
        if (this.f18929t == null) {
            int[] iArr = new int[1];
            this.f18929t = iArr;
            iArr[0] = TXCOpenGlUtils.a(i2, i3, R2.dimen.dm_224, R2.dimen.dm_224, iArr);
        }
        this.f18931v = GLES20.glGetUniformLocation(q(), "yTexture");
        this.f18932w = GLES20.glGetUniformLocation(q(), "uvTexture");
        int i4 = this.f18928s;
        if (1 == i4) {
            int[] iArr2 = new int[1];
            this.f18930u = iArr2;
            iArr2[0] = TXCOpenGlUtils.a(i2, i3 / 2, R2.dimen.dm_224, R2.dimen.dm_224, iArr2);
        } else if (3 == i4) {
            this.f18931v = GLES20.glGetUniformLocation(q(), "yTexture");
            this.f18932w = GLES20.glGetUniformLocation(q(), "uvTexture");
            int[] iArr3 = new int[1];
            this.f18930u = iArr3;
            iArr3[0] = TXCOpenGlUtils.a(i2 / 2, i3 / 2, R2.dimen.dm_3, R2.dimen.dm_3, iArr3);
        } else if (2 == i4 && this.f18933x == null) {
            int[] iArr4 = new int[1];
            this.f18933x = iArr4;
            iArr4[0] = TXCOpenGlUtils.a(i2, i3, R2.dimen.dm_200, R2.dimen.dm_200, iArr4);
        }
        super.a(i2, i3);
    }

    public void a(byte[] bArr) {
        if (bArr != null) {
            this.f18927r = ByteBuffer.wrap(bArr);
        }
    }

    public void a(ByteBuffer byteBuffer) {
        this.f18927r = byteBuffer;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        super.a(-1, floatBuffer, floatBuffer2);
    }
}
