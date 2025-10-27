package io.agora.rtc.video;

import android.opengl.GLES20;
import com.yikaobang.yixue.R2;
import io.agora.rtc.gl.GlShader;
import io.agora.rtc.gl.GlUtil;
import io.agora.rtc.video.RendererCommon;
import java.nio.FloatBuffer;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes8.dex */
public class GlRectDrawer implements RendererCommon.GlDrawer {
    private static final FloatBuffer FULL_RECTANGLE_BUF = GlUtil.createFloatBuffer(new float[]{-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f});
    private static final FloatBuffer FULL_RECTANGLE_TEX_BUF = GlUtil.createFloatBuffer(new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f});
    private static final String OES_FRAGMENT_SHADER_STRING = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 interp_tc;\n\nuniform samplerExternalOES oes_tex;\n\nvoid main() {\n  gl_FragColor = texture2D(oes_tex, interp_tc);\n}\n";
    private static final String RGB_FRAGMENT_SHADER_STRING = "precision mediump float;\nvarying vec2 interp_tc;\n\nuniform sampler2D rgb_tex;\n\nvoid main() {\n  gl_FragColor = texture2D(rgb_tex, interp_tc);\n}\n";
    private static final String VERTEX_SHADER_STRING = "varying vec2 interp_tc;\nattribute vec4 in_pos;\nattribute vec4 in_tc;\n\nuniform mat4 texMatrix;\n\nvoid main() {\n    gl_Position = in_pos;\n    interp_tc = (texMatrix * in_tc).xy;\n}\n";
    private static final String YUV_FRAGMENT_SHADER_STRING = "precision mediump float;\nvarying vec2 interp_tc;\n\nuniform sampler2D y_tex;\nuniform sampler2D u_tex;\nuniform sampler2D v_tex;\n\nvoid main() {\n  float y = texture2D(y_tex, interp_tc).r;\n  float u = texture2D(u_tex, interp_tc).r - 0.5;\n  float v = texture2D(v_tex, interp_tc).r - 0.5;\n  gl_FragColor = vec4(y + 1.403 * v,                       y - 0.344 * u - 0.714 * v,                       y + 1.77 * u, 1);\n}\n";
    private FloatBuffer mTexCoordinate = GlUtil.createFloatBuffer(new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f});
    private FloatBuffer mPosCoordinate = GlUtil.createFloatBuffer(new float[]{-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f});
    private final Map<String, Shader> shaders = new IdentityHashMap();

    public static class Shader {
        public final GlShader glShader;
        public final int texMatrixLocation;

        public Shader(String fragmentShader) {
            GlShader glShader = new GlShader(GlRectDrawer.VERTEX_SHADER_STRING, fragmentShader);
            this.glShader = glShader;
            this.texMatrixLocation = glShader.getUniformLocation("texMatrix");
        }
    }

    private float[] ComputePosVertexAttribArray(int bigWidth, int bigHeight, int smallWidth, int smallHeight) {
        float f2 = (((bigHeight - smallHeight) * 2.0f) / bigHeight) - 1.0f;
        float f3 = ((smallWidth * 2.0f) / bigWidth) - 1.0f;
        return new float[]{-1.0f, f2, f3, f2, -1.0f, 1.0f, f3, 1.0f};
    }

    private float[] ComputeVertexAttribArray(int srcWidth, int srcHeight, int targetWidth, int targetHeight) {
        float f2 = targetWidth / targetHeight;
        float f3 = srcWidth;
        float f4 = srcHeight;
        if (f3 / f4 >= f2) {
            float f5 = ((f3 - (f4 * f2)) / 2.0f) / f3;
            float f6 = 1.0f - f5;
            return new float[]{f5, 0.0f, f6, 0.0f, f5, 1.0f, f6, 1.0f};
        }
        float f7 = ((f4 - (f3 / f2)) / 2.0f) / f4;
        float f8 = 1.0f - f7;
        return new float[]{0.0f, f7, 1.0f, f7, 0.0f, f8, 1.0f, f8};
    }

    private void drawRectangle(int x2, int y2, int width, int height) {
        GLES20.glViewport(x2, y2, width, height);
        GLES20.glDrawArrays(5, 0, 4);
    }

    private void prepareShader(String fragmentShader, float[] texMatrix) {
        prepareShader(fragmentShader, texMatrix, FULL_RECTANGLE_TEX_BUF);
    }

    public void drawOes(int oesTextureId, float[] texMatrix, int x2, int y2, int srcWidth, int srcHeight, int dstWidth, int dstHeight) {
        FloatBuffer floatBufferCreateFloatBuffer = GlUtil.createFloatBuffer(ComputeVertexAttribArray(srcWidth, srcHeight, dstWidth, dstHeight));
        this.mTexCoordinate = floatBufferCreateFloatBuffer;
        prepareShader(OES_FRAGMENT_SHADER_STRING, texMatrix, floatBufferCreateFloatBuffer);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(36197, oesTextureId);
        drawRectangle(x2, y2, dstWidth, dstHeight);
        GLES20.glBindTexture(36197, 0);
    }

    public void drawRgb(int textureId, float[] texMatrix, int x2, int y2, int srcWidth, int srcHeight, int dstWidth, int dstHeight) {
        FloatBuffer floatBufferCreateFloatBuffer = GlUtil.createFloatBuffer(ComputeVertexAttribArray(srcWidth, srcHeight, dstWidth, dstHeight));
        this.mTexCoordinate = floatBufferCreateFloatBuffer;
        prepareShader(RGB_FRAGMENT_SHADER_STRING, texMatrix, floatBufferCreateFloatBuffer);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, textureId);
        drawRectangle(x2, y2, dstWidth, dstHeight);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
    }

    @Override // io.agora.rtc.video.RendererCommon.GlDrawer
    public void drawYuv(int[] yuvTextures, float[] texMatrix, int x2, int y2, int width, int height) {
        prepareShader(YUV_FRAGMENT_SHADER_STRING, texMatrix);
        for (int i2 = 0; i2 < 3; i2++) {
            GLES20.glActiveTexture(33984 + i2);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, yuvTextures[i2]);
        }
        drawRectangle(x2, y2, width, height);
        for (int i3 = 0; i3 < 3; i3++) {
            GLES20.glActiveTexture(i3 + 33984);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
        }
    }

    @Override // io.agora.rtc.video.RendererCommon.GlDrawer
    public void release() {
        Iterator<Shader> it = this.shaders.values().iterator();
        while (it.hasNext()) {
            it.next().glShader.release();
        }
        this.shaders.clear();
    }

    private void prepareShader(String fragmentShader, float[] texMatrix, FloatBuffer texCoord) {
        Shader shader;
        if (this.shaders.containsKey(fragmentShader)) {
            shader = this.shaders.get(fragmentShader);
        } else {
            Shader shader2 = new Shader(fragmentShader);
            this.shaders.put(fragmentShader, shader2);
            shader2.glShader.useProgram();
            if (fragmentShader == YUV_FRAGMENT_SHADER_STRING) {
                GLES20.glUniform1i(shader2.glShader.getUniformLocation("y_tex"), 0);
                GLES20.glUniform1i(shader2.glShader.getUniformLocation("u_tex"), 1);
                GLES20.glUniform1i(shader2.glShader.getUniformLocation("v_tex"), 2);
            } else if (fragmentShader == RGB_FRAGMENT_SHADER_STRING) {
                GLES20.glUniform1i(shader2.glShader.getUniformLocation("rgb_tex"), 0);
            } else {
                if (fragmentShader != OES_FRAGMENT_SHADER_STRING) {
                    throw new IllegalStateException("Unknown fragment shader: " + fragmentShader);
                }
                GLES20.glUniform1i(shader2.glShader.getUniformLocation("oes_tex"), 0);
            }
            GlUtil.checkNoGLES2Error("Initialize fragment shader uniform values.");
            shader = shader2;
        }
        shader.glShader.setVertexAttribArray("in_pos", 2, FULL_RECTANGLE_BUF);
        shader.glShader.setVertexAttribArray("in_tc", 2, texCoord);
        shader.glShader.useProgram();
        GLES20.glUniformMatrix4fv(shader.texMatrixLocation, 1, false, texMatrix, 0);
    }

    public void drawOes(int oesTextureId, float[] texMatrix, int x2, int y2, int srcWidth, int srcHeight, int dstWidth, int dstHeight, int dstWidth_ori, int dstHeight_ori) {
        float[] fArrComputeVertexAttribArray = ComputeVertexAttribArray(srcWidth, srcHeight, dstWidth_ori, dstHeight_ori);
        float[] fArrComputePosVertexAttribArray = ComputePosVertexAttribArray(dstWidth, dstHeight, dstWidth_ori, dstHeight_ori);
        this.mTexCoordinate = GlUtil.createFloatBuffer(fArrComputeVertexAttribArray);
        FloatBuffer floatBufferCreateFloatBuffer = GlUtil.createFloatBuffer(fArrComputePosVertexAttribArray);
        this.mPosCoordinate = floatBufferCreateFloatBuffer;
        prepareShader(OES_FRAGMENT_SHADER_STRING, texMatrix, this.mTexCoordinate, floatBufferCreateFloatBuffer);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(36197, oesTextureId);
        drawRectangle(x2, y2, dstWidth, dstHeight);
        GLES20.glBindTexture(36197, 0);
    }

    public void drawRgb(int textureId, float[] texMatrix, int x2, int y2, int srcWidth, int srcHeight, int dstWidth, int dstHeight, int dstWidth_ori, int dstHeight_ori) {
        float[] fArrComputeVertexAttribArray = ComputeVertexAttribArray(srcWidth, srcHeight, dstWidth_ori, dstHeight_ori);
        float[] fArrComputePosVertexAttribArray = ComputePosVertexAttribArray(dstWidth, dstHeight, dstWidth_ori, dstHeight_ori);
        this.mTexCoordinate = GlUtil.createFloatBuffer(fArrComputeVertexAttribArray);
        FloatBuffer floatBufferCreateFloatBuffer = GlUtil.createFloatBuffer(fArrComputePosVertexAttribArray);
        this.mPosCoordinate = floatBufferCreateFloatBuffer;
        prepareShader(RGB_FRAGMENT_SHADER_STRING, texMatrix, this.mTexCoordinate, floatBufferCreateFloatBuffer);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, textureId);
        drawRectangle(x2, y2, dstWidth, dstHeight);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
    }

    @Override // io.agora.rtc.video.RendererCommon.GlDrawer
    public void drawOes(int oesTextureId, float[] texMatrix, int x2, int y2, int width, int height) {
        prepareShader(OES_FRAGMENT_SHADER_STRING, texMatrix);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(36197, oesTextureId);
        drawRectangle(x2, y2, width, height);
        GLES20.glBindTexture(36197, 0);
    }

    @Override // io.agora.rtc.video.RendererCommon.GlDrawer
    public void drawRgb(int textureId, float[] texMatrix, int x2, int y2, int width, int height) {
        prepareShader(RGB_FRAGMENT_SHADER_STRING, texMatrix);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, textureId);
        drawRectangle(x2, y2, width, height);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
    }

    private void prepareShader(String fragmentShader, float[] texMatrix, FloatBuffer texCoord, FloatBuffer posCoord) {
        Shader shader;
        if (this.shaders.containsKey(fragmentShader)) {
            shader = this.shaders.get(fragmentShader);
        } else {
            Shader shader2 = new Shader(fragmentShader);
            this.shaders.put(fragmentShader, shader2);
            shader2.glShader.useProgram();
            if (fragmentShader == YUV_FRAGMENT_SHADER_STRING) {
                GLES20.glUniform1i(shader2.glShader.getUniformLocation("y_tex"), 0);
                GLES20.glUniform1i(shader2.glShader.getUniformLocation("u_tex"), 1);
                GLES20.glUniform1i(shader2.glShader.getUniformLocation("v_tex"), 2);
            } else if (fragmentShader == RGB_FRAGMENT_SHADER_STRING) {
                GLES20.glUniform1i(shader2.glShader.getUniformLocation("rgb_tex"), 0);
            } else if (fragmentShader == OES_FRAGMENT_SHADER_STRING) {
                GLES20.glUniform1i(shader2.glShader.getUniformLocation("oes_tex"), 0);
            } else {
                throw new IllegalStateException("Unknown fragment shader: " + fragmentShader);
            }
            GlUtil.checkNoGLES2Error("Initialize fragment shader uniform values.");
            shader = shader2;
        }
        shader.glShader.setVertexAttribArray("in_pos", 2, posCoord);
        shader.glShader.setVertexAttribArray("in_tc", 2, texCoord);
        shader.glShader.useProgram();
        GLES20.glUniformMatrix4fv(shader.texMatrixLocation, 1, false, texMatrix, 0);
    }
}
