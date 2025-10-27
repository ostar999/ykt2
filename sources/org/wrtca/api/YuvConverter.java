package org.wrtca.api;

import android.opengl.GLES20;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.wrtca.api.VideoFrame;
import org.wrtca.jni.JniCommon;
import org.wrtca.util.ThreadUtils;

/* loaded from: classes9.dex */
public class YuvConverter {
    private static final String OES_FRAGMENT_SHADER = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 interp_tc;\n\nuniform samplerExternalOES tex;\nuniform vec2 xUnit;\nuniform vec4 coeffs;\n\nvoid main() {\n  gl_FragColor.r = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc - 1.5 * xUnit).rgb);\n  gl_FragColor.g = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc - 0.5 * xUnit).rgb);\n  gl_FragColor.b = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc + 0.5 * xUnit).rgb);\n  gl_FragColor.a = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc + 1.5 * xUnit).rgb);\n}\n";
    private static final String RGB_FRAGMENT_SHADER = "precision mediump float;\nvarying vec2 interp_tc;\n\nuniform sampler2D tex;\nuniform vec2 xUnit;\nuniform vec4 coeffs;\n\nvoid main() {\n  gl_FragColor.r = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc - 1.5 * xUnit).rgb);\n  gl_FragColor.g = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc - 0.5 * xUnit).rgb);\n  gl_FragColor.b = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc + 0.5 * xUnit).rgb);\n  gl_FragColor.a = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc + 1.5 * xUnit).rgb);\n}\n";
    private static final String TAG = "YuvConverter";
    private static final String VERTEX_SHADER = "varying vec2 interp_tc;\nattribute vec4 in_pos;\nattribute vec4 in_tc;\n\nuniform mat4 texMatrix;\n\nvoid main() {\n    gl_Position = in_pos;\n    interp_tc = (texMatrix * in_tc).xy;\n}\n";
    private int coeffsLoc;
    private boolean released;
    private GlShader shader;
    private VideoFrame.TextureBuffer.Type shaderTextureType;
    private int texMatrixLoc;
    private final GlTextureFrameBuffer textureFrameBuffer;
    private final ThreadUtils.ThreadChecker threadChecker;
    private int xUnitLoc;
    private static final FloatBuffer DEVICE_RECTANGLE = GlUtil.createFloatBuffer(new float[]{-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f});
    private static final FloatBuffer TEXTURE_RECTANGLE = GlUtil.createFloatBuffer(new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f});

    /* renamed from: org.wrtca.api.YuvConverter$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$org$wrtca$api$VideoFrame$TextureBuffer$Type;

        static {
            int[] iArr = new int[VideoFrame.TextureBuffer.Type.values().length];
            $SwitchMap$org$wrtca$api$VideoFrame$TextureBuffer$Type = iArr;
            try {
                iArr[VideoFrame.TextureBuffer.Type.OES.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$wrtca$api$VideoFrame$TextureBuffer$Type[VideoFrame.TextureBuffer.Type.RGB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public YuvConverter() {
        ThreadUtils.ThreadChecker threadChecker = new ThreadUtils.ThreadChecker();
        this.threadChecker = threadChecker;
        this.released = false;
        threadChecker.checkIsOnValidThread();
        this.textureFrameBuffer = new GlTextureFrameBuffer(R2.dimen.dm_200);
    }

    private void initShader(VideoFrame.TextureBuffer.Type type) {
        String str;
        GlShader glShader = this.shader;
        if (glShader != null) {
            glShader.release();
        }
        int i2 = AnonymousClass1.$SwitchMap$org$wrtca$api$VideoFrame$TextureBuffer$Type[type.ordinal()];
        if (i2 == 1) {
            str = OES_FRAGMENT_SHADER;
        } else {
            if (i2 != 2) {
                throw new IllegalArgumentException("Unsupported texture type.");
            }
            str = RGB_FRAGMENT_SHADER;
        }
        this.shaderTextureType = type;
        GlShader glShader2 = new GlShader(VERTEX_SHADER, str);
        this.shader = glShader2;
        glShader2.useProgram();
        this.texMatrixLoc = this.shader.getUniformLocation("texMatrix");
        this.xUnitLoc = this.shader.getUniformLocation("xUnit");
        this.coeffsLoc = this.shader.getUniformLocation("coeffs");
        GLES20.glUniform1i(this.shader.getUniformLocation("tex"), 0);
        GlUtil.checkNoGLES2Error("Initialize fragment shader uniform values.");
    }

    public VideoFrame.I420Buffer convert(VideoFrame.TextureBuffer textureBuffer) {
        int width = textureBuffer.getWidth();
        int height = textureBuffer.getHeight();
        int i2 = ((width + 7) / 8) * 8;
        int i3 = (height + 1) / 2;
        final ByteBuffer byteBufferNativeAllocateByteBuffer = JniCommon.nativeAllocateByteBuffer((height + i3 + 1) * i2);
        convert(byteBufferNativeAllocateByteBuffer, width, height, i2, textureBuffer.getTextureId(), RendererCommon.convertMatrixFromAndroidGraphicsMatrix(textureBuffer.getTransformMatrix()), textureBuffer.getType());
        int i4 = (i2 * height) + 0;
        int i5 = (i2 / 2) + i4;
        byteBufferNativeAllocateByteBuffer.position(0);
        byteBufferNativeAllocateByteBuffer.limit(i4);
        ByteBuffer byteBufferSlice = byteBufferNativeAllocateByteBuffer.slice();
        byteBufferNativeAllocateByteBuffer.position(i4);
        int i6 = i3 * i2;
        byteBufferNativeAllocateByteBuffer.limit(i4 + i6);
        ByteBuffer byteBufferSlice2 = byteBufferNativeAllocateByteBuffer.slice();
        byteBufferNativeAllocateByteBuffer.position(i5);
        byteBufferNativeAllocateByteBuffer.limit(i5 + i6);
        return JavaI420Buffer.wrap(width, height, byteBufferSlice, i2, byteBufferSlice2, i2, byteBufferNativeAllocateByteBuffer.slice(), i2, new Runnable() { // from class: org.wrtca.api.w
            @Override // java.lang.Runnable
            public final void run() {
                JniCommon.nativeFreeByteBuffer(byteBufferNativeAllocateByteBuffer);
            }
        });
    }

    public void release() {
        this.threadChecker.checkIsOnValidThread();
        this.released = true;
        GlShader glShader = this.shader;
        if (glShader != null) {
            glShader.release();
        }
        this.textureFrameBuffer.release();
    }

    @Deprecated
    public void convert(ByteBuffer byteBuffer, int i2, int i3, int i4, int i5, float[] fArr) {
        convert(byteBuffer, i2, i3, i4, i5, fArr, VideoFrame.TextureBuffer.Type.OES);
    }

    private void convert(ByteBuffer byteBuffer, int i2, int i3, int i4, int i5, float[] fArr, VideoFrame.TextureBuffer.Type type) {
        this.threadChecker.checkIsOnValidThread();
        if (!this.released) {
            if (type != this.shaderTextureType) {
                initShader(type);
            }
            this.shader.useProgram();
            this.shader.setVertexAttribArray("in_pos", 2, DEVICE_RECTANGLE);
            this.shader.setVertexAttribArray("in_tc", 2, TEXTURE_RECTANGLE);
            if (i4 % 8 != 0) {
                throw new IllegalArgumentException("Invalid stride, must be a multiple of 8");
            }
            if (i4 >= i2) {
                int i6 = (i2 + 3) / 4;
                int i7 = (i2 + 7) / 8;
                int i8 = (i3 + 1) / 2;
                int i9 = i3 + i8;
                if (byteBuffer.capacity() >= i4 * i9) {
                    float[] fArrMultiplyMatrices = RendererCommon.multiplyMatrices(fArr, RendererCommon.verticalFlipMatrix());
                    int i10 = i4 / 4;
                    this.textureFrameBuffer.setSize(i10, i9);
                    GLES20.glBindFramebuffer(36160, this.textureFrameBuffer.getFrameBufferId());
                    GlUtil.checkNoGLES2Error("glBindFramebuffer");
                    GLES20.glActiveTexture(33984);
                    GLES20.glBindTexture(type.getGlTarget(), i5);
                    GlUtil.checkNoGLES2Error("glBindTexture");
                    GLES20.glUniformMatrix4fv(this.texMatrixLoc, 1, false, fArrMultiplyMatrices, 0);
                    GLES20.glViewport(0, 0, i6, i3);
                    float f2 = i2;
                    GLES20.glUniform2f(this.xUnitLoc, fArrMultiplyMatrices[0] / f2, fArrMultiplyMatrices[1] / f2);
                    GLES20.glUniform4f(this.coeffsLoc, 0.299f, 0.587f, 0.114f, 0.0f);
                    GLES20.glDrawArrays(5, 0, 4);
                    GLES20.glViewport(0, i3, i7, i8);
                    GLES20.glUniform2f(this.xUnitLoc, (fArrMultiplyMatrices[0] * 2.0f) / f2, (fArrMultiplyMatrices[1] * 2.0f) / f2);
                    GLES20.glUniform4f(this.coeffsLoc, -0.169f, -0.331f, 0.499f, 0.5f);
                    GLES20.glDrawArrays(5, 0, 4);
                    GLES20.glViewport(i4 / 8, i3, i7, i8);
                    GLES20.glUniform4f(this.coeffsLoc, 0.499f, -0.418f, -0.0813f, 0.5f);
                    GLES20.glDrawArrays(5, 0, 4);
                    GLES20.glReadPixels(0, 0, i10, i9, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, byteBuffer);
                    GlUtil.checkNoGLES2Error("YuvConverter.convert");
                    GLES20.glBindFramebuffer(36160, 0);
                    GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
                    GLES20.glBindTexture(type.getGlTarget(), 0);
                    return;
                }
                throw new IllegalArgumentException("YuvConverter.convert called with too small buffer");
            }
            throw new IllegalArgumentException("Invalid stride, must >= width");
        }
        throw new IllegalStateException("YuvConverter.convert called on released object");
    }
}
