package com.tencent.liteav.videobase.utils;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private final String f20118a;

    /* renamed from: b, reason: collision with root package name */
    private final String f20119b;

    public c(String str, String str2) {
        this.f20118a = str;
        this.f20119b = str2;
    }

    public int a() {
        int[] iArr = new int[1];
        int iA = a(this.f20118a, 35633);
        if (iA == 0) {
            TXCLog.e("Program", "load vertex shader failed.");
            return -1;
        }
        int iA2 = a(this.f20119b, 35632);
        if (iA2 == 0) {
            TXCLog.e("Program", "load fragment shader failed.");
            return -1;
        }
        int iGlCreateProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(iGlCreateProgram, iA);
        GLES20.glAttachShader(iGlCreateProgram, iA2);
        GLES20.glLinkProgram(iGlCreateProgram);
        GLES20.glGetProgramiv(iGlCreateProgram, 35714, iArr, 0);
        if (iArr[0] != 0) {
            GLES20.glDeleteShader(iA);
            GLES20.glDeleteShader(iA2);
            return iGlCreateProgram;
        }
        TXCLog.e("Program", "link program failed. status: " + iArr[0]);
        return -1;
    }

    private int a(String str, int i2) {
        int[] iArr = new int[1];
        int iGlCreateShader = GLES20.glCreateShader(i2);
        GLES20.glShaderSource(iGlCreateShader, str);
        GLES20.glCompileShader(iGlCreateShader);
        GLES20.glGetShaderiv(iGlCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return iGlCreateShader;
        }
        OpenGlUtils.checkGlError("glCompileShader");
        return 0;
    }
}
