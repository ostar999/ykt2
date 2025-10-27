package com.tencent.liteav.beauty.b.a;

import android.opengl.GLES20;
import android.util.Log;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.NativeLoad;
import com.tencent.liteav.beauty.b.t;

/* loaded from: classes6.dex */
public class b extends t {

    /* renamed from: r, reason: collision with root package name */
    private int f18833r;

    /* renamed from: s, reason: collision with root package name */
    private int f18834s;

    /* renamed from: t, reason: collision with root package name */
    private int f18835t;

    /* renamed from: x, reason: collision with root package name */
    private final String f18836x;

    public b() {
        super("varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.f18833r = -1;
        this.f18834s = -1;
        this.f18835t = -1;
        this.f18836x = "BeautyBlend";
    }

    private void r() {
        this.f18834s = GLES20.glGetUniformLocation(q(), "whiteDegree");
        this.f18833r = GLES20.glGetUniformLocation(q(), "contrast");
        this.f18835t = GLES20.glGetUniformLocation(q(), "ruddyDegree");
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean a() {
        int iNativeLoadGLProgram = NativeLoad.nativeLoadGLProgram(12);
        this.f18592a = iNativeLoadGLProgram;
        if (iNativeLoadGLProgram == 0 || !b()) {
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

    public void b(float f2) {
        Log.i("BeautyBlend", "setRuddyLevel level " + f2);
        a(this.f18835t, f2 / 2.0f);
    }

    public void a(float f2) {
        TXCLog.i("BeautyBlend", "setBrightLevel " + f2);
        a(this.f18834s, f2);
    }
}
