package org.wrtca.api;

import android.opengl.GLES20;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes9.dex */
public class GlUtil {
    private static final String TAG = "GlUtil";

    public static class GlOutOfMemoryException extends RuntimeException {
        public GlOutOfMemoryException(String str) {
            super(str);
        }
    }

    private GlUtil() {
    }

    public static void checkNoGLES2Error(String str) {
        int iGlGetError = GLES20.glGetError();
        if (iGlGetError != 0) {
            c.h.a(TAG, str + " error: " + iGlGetError);
            if (iGlGetError == 1285) {
                throw new GlOutOfMemoryException(str);
            }
            throw new RuntimeException(str + ": GLES20 error: " + iGlGetError);
        }
    }

    public static FloatBuffer createFloatBuffer(float[] fArr) {
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(fArr.length * 4);
        byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer floatBufferAsFloatBuffer = byteBufferAllocateDirect.asFloatBuffer();
        floatBufferAsFloatBuffer.put(fArr);
        floatBufferAsFloatBuffer.position(0);
        return floatBufferAsFloatBuffer;
    }

    public static int generateTexture(int i2) {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        int i3 = iArr[0];
        GLES20.glBindTexture(i2, i3);
        GLES20.glTexParameterf(i2, R2.drawable.ic_home_index_normal_day, 9729.0f);
        GLES20.glTexParameterf(i2, 10240, 9729.0f);
        GLES20.glTexParameterf(i2, R2.drawable.ic_home_index_normal_night, 33071.0f);
        GLES20.glTexParameterf(i2, R2.drawable.ic_home_index_select_day, 33071.0f);
        checkNoGLES2Error("generateTexture");
        return i3;
    }
}
