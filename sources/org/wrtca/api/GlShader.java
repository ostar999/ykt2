package org.wrtca.api;

import android.opengl.GLES20;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import org.wrtca.log.Logging;

/* loaded from: classes9.dex */
public class GlShader {
    private static final String TAG = "GlShader";
    private int program;

    public GlShader(String str, String str2) {
        int iCompileShader = compileShader(35633, str);
        int iCompileShader2 = compileShader(35632, str2);
        int iGlCreateProgram = GLES20.glCreateProgram();
        this.program = iGlCreateProgram;
        if (iGlCreateProgram == 0) {
            throw new RuntimeException("glCreateProgram() failed. GLES20 error: " + GLES20.glGetError());
        }
        GLES20.glAttachShader(iGlCreateProgram, iCompileShader);
        GLES20.glAttachShader(this.program, iCompileShader2);
        GLES20.glLinkProgram(this.program);
        int[] iArr = {0};
        GLES20.glGetProgramiv(this.program, 35714, iArr, 0);
        if (iArr[0] == 1) {
            GLES20.glDeleteShader(iCompileShader);
            GLES20.glDeleteShader(iCompileShader2);
            GlUtil.checkNoGLES2Error("Creating GlShader");
        } else {
            Logging.e(TAG, "Could not link program: " + GLES20.glGetProgramInfoLog(this.program));
            throw new RuntimeException(GLES20.glGetProgramInfoLog(this.program));
        }
    }

    private static int compileShader(int i2, String str) {
        int iGlCreateShader = GLES20.glCreateShader(i2);
        if (iGlCreateShader == 0) {
            throw new RuntimeException("glCreateShader() failed. GLES20 error: " + GLES20.glGetError());
        }
        GLES20.glShaderSource(iGlCreateShader, str);
        GLES20.glCompileShader(iGlCreateShader);
        int[] iArr = {0};
        GLES20.glGetShaderiv(iGlCreateShader, 35713, iArr, 0);
        if (iArr[0] == 1) {
            GlUtil.checkNoGLES2Error("compileShader");
            return iGlCreateShader;
        }
        Logging.e(TAG, "Could not compile shader " + i2 + ":" + GLES20.glGetShaderInfoLog(iGlCreateShader));
        throw new RuntimeException(GLES20.glGetShaderInfoLog(iGlCreateShader));
    }

    public int getAttribLocation(String str) {
        int i2 = this.program;
        if (i2 == -1) {
            throw new RuntimeException("The program has been released");
        }
        int iGlGetAttribLocation = GLES20.glGetAttribLocation(i2, str);
        if (iGlGetAttribLocation >= 0) {
            return iGlGetAttribLocation;
        }
        throw new RuntimeException("Could not locate '" + str + "' in program");
    }

    public int getUniformLocation(String str) {
        int i2 = this.program;
        if (i2 == -1) {
            throw new RuntimeException("The program has been released");
        }
        int iGlGetUniformLocation = GLES20.glGetUniformLocation(i2, str);
        if (iGlGetUniformLocation >= 0) {
            return iGlGetUniformLocation;
        }
        throw new RuntimeException("Could not locate uniform '" + str + "' in program");
    }

    public void release() {
        Logging.d(TAG, "Deleting shader.");
        int i2 = this.program;
        if (i2 != -1) {
            GLES20.glDeleteProgram(i2);
            this.program = -1;
        }
    }

    public void setVertexAttribArray(String str, int i2, FloatBuffer floatBuffer) {
        setVertexAttribArray(str, i2, 0, floatBuffer);
    }

    public void useProgram() {
        int i2 = this.program;
        if (i2 == -1) {
            throw new RuntimeException("The program has been released");
        }
        GLES20.glUseProgram(i2);
        GlUtil.checkNoGLES2Error("glUseProgram");
    }

    public void setVertexAttribArray(String str, int i2, int i3, FloatBuffer floatBuffer) {
        if (this.program == -1) {
            throw new RuntimeException("The program has been released");
        }
        int attribLocation = getAttribLocation(str);
        GLES20.glEnableVertexAttribArray(attribLocation);
        GLES20.glVertexAttribPointer(attribLocation, i2, R2.color.m3_ref_palette_dynamic_tertiary60, false, i3, (Buffer) floatBuffer);
        GlUtil.checkNoGLES2Error("setVertexAttribArray");
    }
}
