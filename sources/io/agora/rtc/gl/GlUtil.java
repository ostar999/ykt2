package io.agora.rtc.gl;

import android.opengl.GLES20;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes8.dex */
public class GlUtil {
    private GlUtil() {
    }

    public static void checkNoGLES2Error(String msg) {
        int iGlGetError = GLES20.glGetError();
        if (iGlGetError == 0) {
            return;
        }
        throw new RuntimeException(msg + ": GLES20 error: " + iGlGetError);
    }

    public static FloatBuffer createFloatBuffer(float[] coords) {
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(coords.length * 4);
        byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer floatBufferAsFloatBuffer = byteBufferAllocateDirect.asFloatBuffer();
        floatBufferAsFloatBuffer.put(coords);
        floatBufferAsFloatBuffer.position(0);
        return floatBufferAsFloatBuffer;
    }

    public static int generateTexture(int target) {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        int i2 = iArr[0];
        GLES20.glBindTexture(target, i2);
        GLES20.glTexParameterf(target, R2.drawable.ic_home_index_normal_day, 9729.0f);
        GLES20.glTexParameterf(target, 10240, 9729.0f);
        GLES20.glTexParameterf(target, R2.drawable.ic_home_index_normal_night, 33071.0f);
        GLES20.glTexParameterf(target, R2.drawable.ic_home_index_select_day, 33071.0f);
        checkNoGLES2Error("generateTexture");
        return i2;
    }
}
