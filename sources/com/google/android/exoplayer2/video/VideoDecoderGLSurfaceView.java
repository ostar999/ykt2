package com.google.android.exoplayer2.video;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.VideoDecoderOutputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.GlUtil;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.concurrent.atomic.AtomicReference;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes3.dex */
public final class VideoDecoderGLSurfaceView extends GLSurfaceView implements VideoDecoderOutputBufferRenderer {
    private final Renderer renderer;

    public static final class Renderer implements GLSurfaceView.Renderer {
        private static final String FRAGMENT_SHADER = "precision mediump float;\nvarying vec2 interp_tc_y;\nvarying vec2 interp_tc_u;\nvarying vec2 interp_tc_v;\nuniform sampler2D y_tex;\nuniform sampler2D u_tex;\nuniform sampler2D v_tex;\nuniform mat3 mColorConversion;\nvoid main() {\n  vec3 yuv;\n  yuv.x = texture2D(y_tex, interp_tc_y).r - 0.0625;\n  yuv.y = texture2D(u_tex, interp_tc_u).r - 0.5;\n  yuv.z = texture2D(v_tex, interp_tc_v).r - 0.5;\n  gl_FragColor = vec4(mColorConversion * yuv, 1.0);\n}\n";
        private static final String VERTEX_SHADER = "varying vec2 interp_tc_y;\nvarying vec2 interp_tc_u;\nvarying vec2 interp_tc_v;\nattribute vec4 in_pos;\nattribute vec2 in_tc_y;\nattribute vec2 in_tc_u;\nattribute vec2 in_tc_v;\nvoid main() {\n  gl_Position = in_pos;\n  interp_tc_y = in_tc_y;\n  interp_tc_u = in_tc_u;\n  interp_tc_v = in_tc_v;\n}\n";
        private int colorMatrixLocation;
        private GlUtil.Program program;
        private VideoDecoderOutputBuffer renderedOutputBuffer;
        private final GLSurfaceView surfaceView;
        private static final float[] kColorConversion601 = {1.164f, 1.164f, 1.164f, 0.0f, -0.392f, 2.017f, 1.596f, -0.813f, 0.0f};
        private static final float[] kColorConversion709 = {1.164f, 1.164f, 1.164f, 0.0f, -0.213f, 2.112f, 1.793f, -0.533f, 0.0f};
        private static final float[] kColorConversion2020 = {1.168f, 1.168f, 1.168f, 0.0f, -0.188f, 2.148f, 1.683f, -0.652f, 0.0f};
        private static final String[] TEXTURE_UNIFORMS = {"y_tex", "u_tex", "v_tex"};
        private static final FloatBuffer TEXTURE_VERTICES = GlUtil.createBuffer(new float[]{-1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, -1.0f});
        private final int[] yuvTextures = new int[3];
        private final int[] texLocations = new int[3];
        private final int[] previousWidths = new int[3];
        private final int[] previousStrides = new int[3];
        private final AtomicReference<VideoDecoderOutputBuffer> pendingOutputBufferReference = new AtomicReference<>();
        private final FloatBuffer[] textureCoords = new FloatBuffer[3];

        public Renderer(GLSurfaceView gLSurfaceView) {
            this.surfaceView = gLSurfaceView;
            for (int i2 = 0; i2 < 3; i2++) {
                int[] iArr = this.previousWidths;
                this.previousStrides[i2] = -1;
                iArr[i2] = -1;
            }
        }

        @RequiresNonNull({"program"})
        private void setupTextures() {
            GLES20.glGenTextures(3, this.yuvTextures, 0);
            for (int i2 = 0; i2 < 3; i2++) {
                GLES20.glUniform1i(this.program.getUniformLocation(TEXTURE_UNIFORMS[i2]), i2);
                GLES20.glActiveTexture(33984 + i2);
                GLES20.glBindTexture(R2.attr.tab_indicator_height, this.yuvTextures[i2]);
                GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_day, 9729.0f);
                GLES20.glTexParameterf(R2.attr.tab_indicator_height, 10240, 9729.0f);
                GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_night, 33071.0f);
                GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_select_day, 33071.0f);
            }
            GlUtil.checkGlError();
        }

        @Override // android.opengl.GLSurfaceView.Renderer
        public void onDrawFrame(GL10 gl10) {
            VideoDecoderOutputBuffer andSet = this.pendingOutputBufferReference.getAndSet(null);
            if (andSet == null && this.renderedOutputBuffer == null) {
                return;
            }
            if (andSet != null) {
                VideoDecoderOutputBuffer videoDecoderOutputBuffer = this.renderedOutputBuffer;
                if (videoDecoderOutputBuffer != null) {
                    videoDecoderOutputBuffer.release();
                }
                this.renderedOutputBuffer = andSet;
            }
            VideoDecoderOutputBuffer videoDecoderOutputBuffer2 = (VideoDecoderOutputBuffer) Assertions.checkNotNull(this.renderedOutputBuffer);
            float[] fArr = kColorConversion709;
            int i2 = videoDecoderOutputBuffer2.colorspace;
            if (i2 == 1) {
                fArr = kColorConversion601;
            } else if (i2 == 3) {
                fArr = kColorConversion2020;
            }
            GLES20.glUniformMatrix3fv(this.colorMatrixLocation, 1, false, fArr, 0);
            int[] iArr = (int[]) Assertions.checkNotNull(videoDecoderOutputBuffer2.yuvStrides);
            ByteBuffer[] byteBufferArr = (ByteBuffer[]) Assertions.checkNotNull(videoDecoderOutputBuffer2.yuvPlanes);
            int i3 = 0;
            while (i3 < 3) {
                int i4 = i3 == 0 ? videoDecoderOutputBuffer2.height : (videoDecoderOutputBuffer2.height + 1) / 2;
                GLES20.glActiveTexture(33984 + i3);
                GLES20.glBindTexture(R2.attr.tab_indicator_height, this.yuvTextures[i3]);
                GLES20.glPixelStorei(R2.attr.srlHeaderMaxDragRate, 1);
                GLES20.glTexImage2D(R2.attr.tab_indicator_height, 0, R2.dimen.dm_224, iArr[i3], i4, 0, R2.dimen.dm_224, R2.color.m3_ref_palette_dynamic_tertiary100, byteBufferArr[i3]);
                i3++;
            }
            int i5 = videoDecoderOutputBuffer2.width;
            int i6 = (i5 + 1) / 2;
            int[] iArr2 = {i5, i6, i6};
            for (int i7 = 0; i7 < 3; i7++) {
                if (this.previousWidths[i7] != iArr2[i7] || this.previousStrides[i7] != iArr[i7]) {
                    Assertions.checkState(iArr[i7] != 0);
                    float f2 = iArr2[i7] / iArr[i7];
                    this.textureCoords[i7] = GlUtil.createBuffer(new float[]{0.0f, 0.0f, 0.0f, 1.0f, f2, 0.0f, f2, 1.0f});
                    GLES20.glVertexAttribPointer(this.texLocations[i7], 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 0, (Buffer) this.textureCoords[i7]);
                    this.previousWidths[i7] = iArr2[i7];
                    this.previousStrides[i7] = iArr[i7];
                }
            }
            GLES20.glClear(16384);
            GLES20.glDrawArrays(5, 0, 4);
            GlUtil.checkGlError();
        }

        @Override // android.opengl.GLSurfaceView.Renderer
        public void onSurfaceChanged(GL10 gl10, int i2, int i3) {
            GLES20.glViewport(0, 0, i2, i3);
        }

        @Override // android.opengl.GLSurfaceView.Renderer
        public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
            GlUtil.Program program = new GlUtil.Program(VERTEX_SHADER, FRAGMENT_SHADER);
            this.program = program;
            program.use();
            int attribLocation = this.program.getAttribLocation("in_pos");
            GLES20.glEnableVertexAttribArray(attribLocation);
            GLES20.glVertexAttribPointer(attribLocation, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 0, (Buffer) TEXTURE_VERTICES);
            this.texLocations[0] = this.program.getAttribLocation("in_tc_y");
            GLES20.glEnableVertexAttribArray(this.texLocations[0]);
            this.texLocations[1] = this.program.getAttribLocation("in_tc_u");
            GLES20.glEnableVertexAttribArray(this.texLocations[1]);
            this.texLocations[2] = this.program.getAttribLocation("in_tc_v");
            GLES20.glEnableVertexAttribArray(this.texLocations[2]);
            GlUtil.checkGlError();
            this.colorMatrixLocation = this.program.getUniformLocation("mColorConversion");
            GlUtil.checkGlError();
            setupTextures();
            GlUtil.checkGlError();
        }

        public void setOutputBuffer(VideoDecoderOutputBuffer videoDecoderOutputBuffer) {
            VideoDecoderOutputBuffer andSet = this.pendingOutputBufferReference.getAndSet(videoDecoderOutputBuffer);
            if (andSet != null) {
                andSet.release();
            }
            this.surfaceView.requestRender();
        }
    }

    public VideoDecoderGLSurfaceView(Context context) {
        this(context, null);
    }

    @Deprecated
    public VideoDecoderOutputBufferRenderer getVideoDecoderOutputBufferRenderer() {
        return this;
    }

    @Override // com.google.android.exoplayer2.video.VideoDecoderOutputBufferRenderer
    public void setOutputBuffer(VideoDecoderOutputBuffer videoDecoderOutputBuffer) {
        this.renderer.setOutputBuffer(videoDecoderOutputBuffer);
    }

    public VideoDecoderGLSurfaceView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Renderer renderer = new Renderer(this);
        this.renderer = renderer;
        setPreserveEGLContextOnPause(true);
        setEGLContextClientVersion(2);
        setRenderer(renderer);
        setRenderMode(0);
    }
}
