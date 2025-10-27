package com.tencent.liteav.beauty.b.a;

import android.opengl.GLES20;
import android.util.Log;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.beauty.NativeLoad;
import com.tencent.liteav.beauty.b.t;

/* loaded from: classes6.dex */
public class e extends t {
    private String A;

    /* renamed from: r, reason: collision with root package name */
    private int f18841r;

    /* renamed from: s, reason: collision with root package name */
    private int f18842s;

    /* renamed from: t, reason: collision with root package name */
    private int f18843t;

    /* renamed from: x, reason: collision with root package name */
    private int f18844x;

    /* renamed from: y, reason: collision with root package name */
    private float f18845y;

    /* renamed from: z, reason: collision with root package name */
    private float f18846z;

    public e() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.f18841r = -1;
        this.f18842s = -1;
        this.f18843t = -1;
        this.f18844x = -1;
        this.f18845y = 2.0f;
        this.f18846z = 0.5f;
        this.A = "SmoothVertical";
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean a() {
        if (TXCBuild.Brand().equals("samsung") && TXCBuild.Model().equals("GT-I9500") && TXCBuild.Version().equals("4.3")) {
            Log.d(this.A, "SAMSUNG_S4 GT-I9500 + Android 4.3; use diffrent shader!");
            this.f18592a = NativeLoad.nativeLoadGLProgram(15);
        } else {
            this.f18592a = NativeLoad.nativeLoadGLProgram(5);
        }
        if (this.f18592a == 0 || !b()) {
            this.f18598g = false;
        } else {
            this.f18598g = true;
        }
        c();
        return this.f18598g;
    }

    @Override // com.tencent.liteav.beauty.b.t, com.tencent.liteav.basic.opengl.j
    public boolean b() {
        super.b();
        r();
        return true;
    }

    public void r() {
        this.f18841r = GLES20.glGetUniformLocation(q(), "texelWidthOffset");
        this.f18842s = GLES20.glGetUniformLocation(q(), "texelHeightOffset");
        this.f18843t = GLES20.glGetUniformLocation(q(), "smoothDegree");
    }

    public void a(float f2) {
        this.f18846z = f2;
        TXCLog.i(this.A, "setBeautyLevel " + f2);
        a(this.f18843t, f2);
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        super.a(i2, i3);
        if (i2 > i3) {
            if (i3 < 540) {
                this.f18845y = 2.0f;
            } else {
                this.f18845y = 4.0f;
            }
        } else if (i2 < 540) {
            this.f18845y = 2.0f;
        } else {
            this.f18845y = 4.0f;
        }
        TXCLog.i(this.A, "m_textureRation " + this.f18845y);
        a(this.f18841r, this.f18845y / ((float) i2));
        a(this.f18842s, this.f18845y / ((float) i3));
    }
}
