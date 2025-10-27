package io.agora.rtc.gl;

import android.opengl.GLES20;
import com.yikaobang.yixue.R2;
import io.agora.rtc.gl.VideoFrame;
import io.agora.rtc.utils.ThreadUtils;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/* loaded from: classes8.dex */
public class YuvConverter {
    private static final String OES_FRAGMENT_SHADER = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 interp_tc;\n\nuniform samplerExternalOES tex;\nuniform vec2 xUnit;\nuniform vec4 coeffs;\n\nvoid main() {\n  gl_FragColor.r = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc - 1.5 * xUnit).rgb);\n  gl_FragColor.g = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc - 0.5 * xUnit).rgb);\n  gl_FragColor.b = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc + 0.5 * xUnit).rgb);\n  gl_FragColor.a = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc + 1.5 * xUnit).rgb);\n}\n";
    private static final String RGB_FRAGMENT_SHADER = "precision mediump float;\nvarying vec2 interp_tc;\n\nuniform sampler2D tex;\nuniform vec2 xUnit;\nuniform vec4 coeffs;\n\nvoid main() {\n  gl_FragColor.r = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc - 1.5 * xUnit).rgb);\n  gl_FragColor.g = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc - 0.5 * xUnit).rgb);\n  gl_FragColor.b = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc + 0.5 * xUnit).rgb);\n  gl_FragColor.a = coeffs.a + dot(coeffs.rgb,\n      texture2D(tex, interp_tc + 1.5 * xUnit).rgb);\n}\n";
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

    /* renamed from: io.agora.rtc.gl.YuvConverter$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$agora$rtc$gl$VideoFrame$TextureBuffer$Type;

        static {
            int[] iArr = new int[VideoFrame.TextureBuffer.Type.values().length];
            $SwitchMap$io$agora$rtc$gl$VideoFrame$TextureBuffer$Type = iArr;
            try {
                iArr[VideoFrame.TextureBuffer.Type.OES.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$agora$rtc$gl$VideoFrame$TextureBuffer$Type[VideoFrame.TextureBuffer.Type.RGB.ordinal()] = 2;
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

    private void initShader(VideoFrame.TextureBuffer.Type textureType) {
        String str;
        GlShader glShader = this.shader;
        if (glShader != null) {
            glShader.release();
        }
        int i2 = AnonymousClass1.$SwitchMap$io$agora$rtc$gl$VideoFrame$TextureBuffer$Type[textureType.ordinal()];
        if (i2 == 1) {
            str = OES_FRAGMENT_SHADER;
        } else {
            if (i2 != 2) {
                throw new IllegalArgumentException("Unsupported texture type.");
            }
            str = RGB_FRAGMENT_SHADER;
        }
        this.shaderTextureType = textureType;
        GlShader glShader2 = new GlShader(VERTEX_SHADER, str);
        this.shader = glShader2;
        glShader2.useProgram();
        this.texMatrixLoc = this.shader.getUniformLocation("texMatrix");
        this.xUnitLoc = this.shader.getUniformLocation("xUnit");
        this.coeffsLoc = this.shader.getUniformLocation("coeffs");
        GLES20.glUniform1i(this.shader.getUniformLocation("tex"), 0);
        GlUtil.checkNoGLES2Error("Initialize fragment shader uniform values.");
        this.shader.setVertexAttribArray("in_pos", 2, DEVICE_RECTANGLE);
        this.shader.setVertexAttribArray("in_tc", 2, TEXTURE_RECTANGLE);
    }

    public VideoFrame.I420Buffer convert(VideoFrame.TextureBuffer textureBuffer) {
        int width = textureBuffer.getWidth();
        int height = textureBuffer.getHeight();
        int i2 = ((width + 7) / 8) * 8;
        int i3 = (height + 1) / 2;
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect((height + i3 + 1) * i2);
        convert(byteBufferAllocateDirect, width, height, i2, textureBuffer.getTextureId(), RendererCommon.convertMatrixFromAndroidGraphicsMatrix(textureBuffer.getTransformMatrix()), textureBuffer.getType());
        int i4 = (i2 * height) + 0;
        int i5 = (i2 / 2) + i4;
        byteBufferAllocateDirect.position(0);
        byteBufferAllocateDirect.limit(i4);
        ByteBuffer byteBufferSlice = byteBufferAllocateDirect.slice();
        byteBufferAllocateDirect.position(i4);
        int i6 = i3 * i2;
        byteBufferAllocateDirect.limit(i4 + i6);
        ByteBuffer byteBufferSlice2 = byteBufferAllocateDirect.slice();
        byteBufferAllocateDirect.position(i5);
        byteBufferAllocateDirect.limit(i5 + i6);
        return JavaI420Buffer.wrap(width, height, byteBufferSlice, i2, byteBufferSlice2, i2, byteBufferAllocateDirect.slice(), i2, null);
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
    public void convert(ByteBuffer buf, int width, int height, int stride, int srcTextureId, float[] transformMatrix) {
        convert(buf, width, height, stride, srcTextureId, transformMatrix, VideoFrame.TextureBuffer.Type.OES);
    }

    private void convert(ByteBuffer buf, int width, int height, int stride, int srcTextureId, float[] transformMatrix, VideoFrame.TextureBuffer.Type textureType) {
        this.threadChecker.checkIsOnValidThread();
        if (!this.released) {
            if (textureType != this.shaderTextureType) {
                initShader(textureType);
            }
            this.shader.useProgram();
            if (stride % 8 != 0) {
                throw new IllegalArgumentException("Invalid stride, must be a multiple of 8");
            }
            if (stride >= width) {
                int i2 = (width + 3) / 4;
                int i3 = (width + 7) / 8;
                int i4 = (height + 1) / 2;
                int i5 = height + i4;
                if (buf.capacity() >= stride * i5) {
                    float[] fArrMultiplyMatrices = RendererCommon.multiplyMatrices(transformMatrix, RendererCommon.verticalFlipMatrix());
                    int i6 = stride / 4;
                    this.textureFrameBuffer.setSize(i6, i5);
                    GLES20.glBindFramebuffer(36160, this.textureFrameBuffer.getFrameBufferId());
                    GlUtil.checkNoGLES2Error("glBindFramebuffer");
                    GLES20.glActiveTexture(33984);
                    GLES20.glBindTexture(textureType.getGlTarget(), srcTextureId);
                    GLES20.glUniformMatrix4fv(this.texMatrixLoc, 1, false, fArrMultiplyMatrices, 0);
                    GLES20.glViewport(0, 0, i2, height);
                    float f2 = width;
                    GLES20.glUniform2f(this.xUnitLoc, fArrMultiplyMatrices[0] / f2, fArrMultiplyMatrices[1] / f2);
                    GLES20.glUniform4f(this.coeffsLoc, 0.299f, 0.587f, 0.114f, 0.0f);
                    GLES20.glDrawArrays(5, 0, 4);
                    GLES20.glViewport(0, height, i3, i4);
                    GLES20.glUniform2f(this.xUnitLoc, (fArrMultiplyMatrices[0] * 2.0f) / f2, (fArrMultiplyMatrices[1] * 2.0f) / f2);
                    GLES20.glUniform4f(this.coeffsLoc, -0.169f, -0.331f, 0.499f, 0.5f);
                    GLES20.glDrawArrays(5, 0, 4);
                    GLES20.glViewport(stride / 8, height, i3, i4);
                    GLES20.glUniform4f(this.coeffsLoc, 0.499f, -0.418f, -0.0813f, 0.5f);
                    GLES20.glDrawArrays(5, 0, 4);
                    GLES20.glReadPixels(0, 0, i6, i5, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, buf);
                    GlUtil.checkNoGLES2Error("YuvConverter.convert");
                    GLES20.glBindFramebuffer(36160, 0);
                    GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
                    GLES20.glBindTexture(textureType.getGlTarget(), 0);
                    return;
                }
                throw new IllegalArgumentException("YuvConverter.convert called with too small buffer");
            }
            throw new IllegalArgumentException("Invalid stride, must >= width");
        }
        throw new IllegalStateException("YuvConverter.convert called on released object");
    }
}
