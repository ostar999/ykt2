package com.tencent.liteav.beauty.b.a;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.opengl.j;
import com.tencent.liteav.beauty.NativeLoad;

/* loaded from: classes6.dex */
public class d extends j {

    /* renamed from: r, reason: collision with root package name */
    private int f18837r;

    /* renamed from: s, reason: collision with root package name */
    private int f18838s;

    /* renamed from: t, reason: collision with root package name */
    private float f18839t;

    /* renamed from: u, reason: collision with root package name */
    private String f18840u;

    public d() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.f18837r = -1;
        this.f18838s = -1;
        this.f18839t = 4.0f;
        this.f18840u = "SmoothHorizontal";
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean a() {
        int iNativeLoadGLProgram = NativeLoad.nativeLoadGLProgram(13);
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
    public boolean b() {
        super.b();
        r();
        return true;
    }

    public void r() {
        this.f18837r = GLES20.glGetUniformLocation(q(), "texelWidthOffset");
        this.f18838s = GLES20.glGetUniformLocation(q(), "texelHeightOffset");
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        super.a(i2, i3);
        if (i2 > i3) {
            if (i3 < 540) {
                this.f18839t = 2.0f;
            } else {
                this.f18839t = 4.0f;
            }
        } else if (i2 < 540) {
            this.f18839t = 2.0f;
        } else {
            this.f18839t = 4.0f;
        }
        TXCLog.i(this.f18840u, "m_textureRation " + this.f18839t);
        a(this.f18837r, this.f18839t / ((float) i2));
        a(this.f18838s, this.f18839t / ((float) i3));
    }
}
