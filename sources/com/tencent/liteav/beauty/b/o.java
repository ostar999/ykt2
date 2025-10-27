package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.NativeLoad;

/* loaded from: classes6.dex */
public class o extends com.tencent.liteav.basic.opengl.j {
    private static float[] C = {0.1826f, 0.6142f, 0.062f, -0.1006f, -0.3386f, 0.4392f, 0.4392f, -0.3989f, -0.0403f};
    private static float[] D = {0.256816f, 0.504154f, 0.0979137f, -0.148246f, -0.29102f, 0.439266f, 0.439271f, -0.367833f, -0.071438f};
    private static float[] E = {0.0625f, 0.5f, 0.5f};
    private String A;
    private int B;

    /* renamed from: r, reason: collision with root package name */
    private int f18949r;

    /* renamed from: s, reason: collision with root package name */
    private int f18950s;

    /* renamed from: t, reason: collision with root package name */
    private int f18951t;

    /* renamed from: u, reason: collision with root package name */
    private int f18952u;

    /* renamed from: v, reason: collision with root package name */
    private int f18953v;

    /* renamed from: w, reason: collision with root package name */
    private int f18954w;

    /* renamed from: x, reason: collision with root package name */
    private int f18955x;

    /* renamed from: y, reason: collision with root package name */
    private int f18956y;

    /* renamed from: z, reason: collision with root package name */
    private int f18957z;

    public o(int i2) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.f18949r = -1;
        this.f18950s = -1;
        this.f18951t = -1;
        this.f18952u = -1;
        this.f18953v = -1;
        this.f18954w = -1;
        this.f18955x = -1;
        this.f18956y = -1;
        this.f18957z = -1;
        this.A = "RGBA2I420Filter";
        this.B = i2;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean a() {
        int i2 = this.B;
        if (1 == i2) {
            this.f18592a = NativeLoad.nativeLoadGLProgram(8);
            TXCLog.i(this.A, "RGB-->I420 init!");
        } else if (3 == i2) {
            TXCLog.i(this.A, "RGB-->NV21 init!");
            this.f18592a = NativeLoad.nativeLoadGLProgram(11);
        } else {
            if (2 == i2) {
                TXCLog.i(this.A, "RGBA Format init!");
                return super.a();
            }
            TXCLog.i(this.A, "don't support format " + this.B + " use default I420");
            this.f18592a = NativeLoad.nativeLoadGLProgram(8);
        }
        if (this.f18592a == 0 || !b()) {
            this.f18598g = false;
        } else {
            this.f18598g = true;
        }
        c();
        return this.f18598g;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean b() {
        super.b();
        this.f18949r = GLES20.glGetUniformLocation(this.f18592a, "width");
        this.f18950s = GLES20.glGetUniformLocation(this.f18592a, "height");
        return true;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void c() {
        super.c();
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        if (i2 > 0 && i3 > 0) {
            if (this.f18597f == i3 && this.f18596e == i2) {
                return;
            }
            super.a(i2, i3);
            TXCLog.i(this.A, "RGBA2I420Filter width " + i2 + " height " + i3);
            a(this.f18949r, (float) i2);
            a(this.f18950s, (float) i3);
            return;
        }
        TXCLog.e(this.A, "width or height is error!");
    }
}
