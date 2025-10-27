package io.agora.rtc.gl;

import android.graphics.Matrix;
import android.graphics.Point;
import android.opengl.GLES20;
import com.yikaobang.yixue.R2;
import io.agora.rtc.gl.RendererCommon;
import io.agora.rtc.gl.VideoFrame;
import java.nio.ByteBuffer;

/* loaded from: classes8.dex */
public class VideoFrameDrawer {
    static final float[] srcPoints = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f};
    private VideoFrame lastI420Frame;
    private VideoFrame lastRgbaFrame;
    private int renderHeight;
    private int renderWidth;
    private final RGBAUploader rgbaUploader;
    private final YuvUploader yuvUploader;
    private final float[] dstPoints = new float[6];
    private final Point renderSize = new Point();
    private final Matrix renderMatrix = new Matrix();

    /* renamed from: io.agora.rtc.gl.VideoFrameDrawer$1, reason: invalid class name */
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

    public static class YuvUploader {
        private ByteBuffer copyBuffer;
        private int[] yuvTextures;

        private YuvUploader() {
        }

        public int[] getYuvTextures() {
            return this.yuvTextures;
        }

        public void release() {
            this.copyBuffer = null;
            int[] iArr = this.yuvTextures;
            if (iArr != null) {
                GLES20.glDeleteTextures(3, iArr, 0);
                this.yuvTextures = null;
            }
        }

        public int[] uploadFromBuffer(VideoFrame.I420Buffer buffer) {
            return uploadYuvData(buffer.getWidth(), buffer.getHeight(), new int[]{buffer.getStrideY(), buffer.getStrideU(), buffer.getStrideV()}, new ByteBuffer[]{buffer.getDataY(), buffer.getDataU(), buffer.getDataV()});
        }

        public int[] uploadYuvData(int width, int height, int[] strides, ByteBuffer[] planes) {
            ByteBuffer byteBuffer;
            int i2 = width / 2;
            int[] iArr = {width, i2, i2};
            int i3 = height / 2;
            int[] iArr2 = {height, i3, i3};
            int iMax = 0;
            for (int i4 = 0; i4 < 3; i4++) {
                int i5 = strides[i4];
                int i6 = iArr[i4];
                if (i5 > i6) {
                    iMax = Math.max(iMax, i6 * iArr2[i4]);
                }
            }
            if (iMax > 0 && ((byteBuffer = this.copyBuffer) == null || byteBuffer.capacity() < iMax)) {
                this.copyBuffer = ByteBuffer.allocateDirect(iMax);
            }
            if (this.yuvTextures == null) {
                this.yuvTextures = new int[3];
                for (int i7 = 0; i7 < 3; i7++) {
                    this.yuvTextures[i7] = GlUtil.generateTexture(R2.attr.tab_indicator_height);
                }
            }
            for (int i8 = 0; i8 < 3; i8++) {
                GLES20.glActiveTexture(33984 + i8);
                GLES20.glBindTexture(R2.attr.tab_indicator_height, this.yuvTextures[i8]);
                int i9 = strides[i8];
                int i10 = iArr[i8];
                GLES20.glTexImage2D(R2.attr.tab_indicator_height, 0, R2.dimen.dm_224, i10, iArr2[i8], 0, R2.dimen.dm_224, R2.color.m3_ref_palette_dynamic_tertiary100, i9 == i10 ? planes[i8] : this.copyBuffer);
            }
            return this.yuvTextures;
        }

        public /* synthetic */ YuvUploader(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public VideoFrameDrawer() {
        AnonymousClass1 anonymousClass1 = null;
        this.yuvUploader = new YuvUploader(anonymousClass1);
        this.rgbaUploader = new RGBAUploader(anonymousClass1);
    }

    private void calculateTransformedRenderSize(int frameWidth, int frameHeight, Matrix renderMatrix) {
        if (renderMatrix == null) {
            this.renderWidth = frameWidth;
            this.renderHeight = frameHeight;
            return;
        }
        renderMatrix.mapPoints(this.dstPoints, srcPoints);
        for (int i2 = 0; i2 < 3; i2++) {
            float[] fArr = this.dstPoints;
            int i3 = i2 * 2;
            int i4 = i3 + 0;
            fArr[i4] = fArr[i4] * frameWidth;
            int i5 = i3 + 1;
            fArr[i5] = fArr[i5] * frameHeight;
        }
        float[] fArr2 = this.dstPoints;
        this.renderWidth = distance(fArr2[0], fArr2[1], fArr2[2], fArr2[3]);
        float[] fArr3 = this.dstPoints;
        this.renderHeight = distance(fArr3[0], fArr3[1], fArr3[4], fArr3[5]);
    }

    private static int distance(float x02, float y02, float x12, float y12) {
        return (int) Math.round(Math.hypot(x12 - x02, y12 - y02));
    }

    public static void drawTexture(RendererCommon.GlDrawer drawer, VideoFrame.TextureBuffer buffer, Matrix renderMatrix, int frameWidth, int frameHeight, int viewportX, int viewportY, int viewportWidth, int viewportHeight) {
        Matrix matrix = new Matrix(buffer.getTransformMatrix());
        matrix.preConcat(renderMatrix);
        float[] fArrConvertMatrixFromAndroidGraphicsMatrix = RendererCommon.convertMatrixFromAndroidGraphicsMatrix(matrix);
        int i2 = AnonymousClass1.$SwitchMap$io$agora$rtc$gl$VideoFrame$TextureBuffer$Type[buffer.getType().ordinal()];
        if (i2 == 1) {
            drawer.drawOes(buffer.getTextureId(), fArrConvertMatrixFromAndroidGraphicsMatrix, frameWidth, frameHeight, viewportX, viewportY, viewportWidth, viewportHeight);
        } else {
            if (i2 != 2) {
                throw new RuntimeException("Unknown texture type.");
            }
            drawer.drawRgb(buffer.getTextureId(), fArrConvertMatrixFromAndroidGraphicsMatrix, frameWidth, frameHeight, viewportX, viewportY, viewportWidth, viewportHeight);
        }
    }

    public void drawFrame(VideoFrame frame, RendererCommon.GlDrawer drawer) {
        drawFrame(frame, drawer, null);
    }

    public void release() {
        this.yuvUploader.release();
        this.lastI420Frame = null;
        this.rgbaUploader.release();
        this.lastRgbaFrame = null;
    }

    public static class RGBAUploader {
        private ByteBuffer mData;
        private int mTextureId;

        private RGBAUploader() {
            this.mTextureId = 0;
        }

        public int getTextureId() {
            return this.mTextureId;
        }

        public void release() {
            this.mData = null;
            int i2 = this.mTextureId;
            if (i2 != 0) {
                GLES20.glDeleteTextures(1, new int[]{i2}, 0);
            }
        }

        public int uploadData(ByteBuffer data, int width, int height) {
            this.mData = data;
            if (this.mTextureId == 0) {
                this.mTextureId = GlUtil.generateTexture(R2.attr.tab_indicator_height);
            }
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, this.mTextureId);
            GLES20.glTexImage2D(R2.attr.tab_indicator_height, 0, R2.dimen.dm_200, width, height, 0, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, this.mData);
            GlUtil.checkNoGLES2Error("glTexImage2D");
            return this.mTextureId;
        }

        public /* synthetic */ RGBAUploader(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public void drawFrame(VideoFrame frame, RendererCommon.GlDrawer drawer, Matrix additionalRenderMatrix) {
        drawFrame(frame, drawer, additionalRenderMatrix, 0, 0, frame.getRotatedWidth(), frame.getRotatedHeight());
    }

    public void drawFrame(VideoFrame frame, RendererCommon.GlDrawer drawer, Matrix additionalRenderMatrix, int viewportX, int viewportY, int viewportWidth, int viewportHeight) {
        calculateTransformedRenderSize(frame.getRotatedWidth(), frame.getRotatedHeight(), additionalRenderMatrix);
        boolean z2 = frame.getBuffer() instanceof VideoFrame.TextureBuffer;
        boolean z3 = frame.getBuffer() instanceof RgbaBuffer;
        this.renderMatrix.reset();
        this.renderMatrix.preTranslate(0.5f, 0.5f);
        if (!z2) {
            this.renderMatrix.preScale(1.0f, -1.0f);
        }
        this.renderMatrix.preRotate(frame.getRotation());
        this.renderMatrix.preTranslate(-0.5f, -0.5f);
        if (additionalRenderMatrix != null) {
            this.renderMatrix.preConcat(additionalRenderMatrix);
        }
        if (z2) {
            this.lastI420Frame = null;
            this.lastRgbaFrame = null;
            drawTexture(drawer, (VideoFrame.TextureBuffer) frame.getBuffer(), this.renderMatrix, this.renderWidth, this.renderHeight, viewportX, viewportY, viewportWidth, viewportHeight);
        } else {
            if (z3) {
                if (frame != this.lastRgbaFrame) {
                    this.lastRgbaFrame = frame;
                    RgbaBuffer rgbaBuffer = (RgbaBuffer) frame.getBuffer();
                    this.rgbaUploader.uploadData(rgbaBuffer.getBuffer(), rgbaBuffer.getWidth(), rgbaBuffer.getHeight());
                    rgbaBuffer.release();
                }
                drawer.drawRgb(this.rgbaUploader.getTextureId(), RendererCommon.convertMatrixFromAndroidGraphicsMatrix(this.renderMatrix), this.renderWidth, this.renderHeight, viewportX, viewportY, viewportWidth, viewportHeight);
                return;
            }
            if (frame != this.lastI420Frame) {
                this.lastI420Frame = frame;
                VideoFrame.I420Buffer i420 = frame.getBuffer().toI420();
                this.yuvUploader.uploadFromBuffer(i420);
                i420.release();
            }
            drawer.drawYuv(this.yuvUploader.getYuvTextures(), RendererCommon.convertMatrixFromAndroidGraphicsMatrix(this.renderMatrix), this.renderWidth, this.renderHeight, viewportX, viewportY, viewportWidth, viewportHeight);
        }
    }
}
