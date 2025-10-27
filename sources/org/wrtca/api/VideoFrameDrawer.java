package org.wrtca.api;

import android.graphics.Matrix;
import android.graphics.Point;
import android.opengl.GLES20;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;
import org.wrtca.api.RendererCommon;
import org.wrtca.api.VideoFrame;

/* loaded from: classes9.dex */
public class VideoFrameDrawer {
    private static final String TAG = "VideoFrameDrawer";
    public static final float[] srcPoints = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f};
    private VideoFrame lastI420Frame;
    private int renderHeight;
    private int renderWidth;
    private final float[] dstPoints = new float[6];
    private final Point renderSize = new Point();
    private final YuvUploader yuvUploader = new YuvUploader(null);
    private final Matrix renderMatrix = new Matrix();

    /* renamed from: org.wrtca.api.VideoFrameDrawer$1, reason: invalid class name */
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

        public int[] uploadFromBuffer(VideoFrame.I420Buffer i420Buffer) {
            return uploadYuvData(i420Buffer.getWidth(), i420Buffer.getHeight(), new int[]{i420Buffer.getStrideY(), i420Buffer.getStrideU(), i420Buffer.getStrideV()}, new ByteBuffer[]{i420Buffer.getDataY(), i420Buffer.getDataU(), i420Buffer.getDataV()});
        }

        public int[] uploadYuvData(int i2, int i3, int[] iArr, ByteBuffer[] byteBufferArr) {
            ByteBuffer byteBuffer;
            ByteBuffer byteBuffer2;
            int i4 = i2 / 2;
            int[] iArr2 = {i2, i4, i4};
            int i5 = i3 / 2;
            int[] iArr3 = {i3, i5, i5};
            int iMax = 0;
            for (int i6 = 0; i6 < 3; i6++) {
                int i7 = iArr[i6];
                int i8 = iArr2[i6];
                if (i7 > i8) {
                    iMax = Math.max(iMax, i8 * iArr3[i6]);
                }
            }
            if (iMax > 0 && ((byteBuffer2 = this.copyBuffer) == null || byteBuffer2.capacity() < iMax)) {
                this.copyBuffer = ByteBuffer.allocateDirect(iMax);
            }
            if (this.yuvTextures == null) {
                this.yuvTextures = new int[3];
                for (int i9 = 0; i9 < 3; i9++) {
                    this.yuvTextures[i9] = GlUtil.generateTexture(R2.attr.tab_indicator_height);
                }
            }
            for (int i10 = 0; i10 < 3; i10++) {
                GLES20.glActiveTexture(33984 + i10);
                GLES20.glBindTexture(R2.attr.tab_indicator_height, this.yuvTextures[i10]);
                int i11 = iArr[i10];
                int i12 = iArr2[i10];
                if (i11 == i12) {
                    byteBuffer = byteBufferArr[i10];
                } else {
                    VideoRenderer.nativeCopyPlane(byteBufferArr[i10], i12, iArr3[i10], i11, this.copyBuffer, i12);
                    byteBuffer = this.copyBuffer;
                }
                GLES20.glTexImage2D(R2.attr.tab_indicator_height, 0, R2.dimen.dm_224, iArr2[i10], iArr3[i10], 0, R2.dimen.dm_224, R2.color.m3_ref_palette_dynamic_tertiary100, byteBuffer);
            }
            return this.yuvTextures;
        }

        public /* synthetic */ YuvUploader(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    private void calculateTransformedRenderSize(int i2, int i3, Matrix matrix) {
        if (matrix == null) {
            this.renderWidth = i2;
            this.renderHeight = i3;
            return;
        }
        matrix.mapPoints(this.dstPoints, srcPoints);
        for (int i4 = 0; i4 < 3; i4++) {
            float[] fArr = this.dstPoints;
            int i5 = i4 * 2;
            int i6 = i5 + 0;
            fArr[i6] = fArr[i6] * i2;
            int i7 = i5 + 1;
            fArr[i7] = fArr[i7] * i3;
        }
        float[] fArr2 = this.dstPoints;
        this.renderWidth = distance(fArr2[0], fArr2[1], fArr2[2], fArr2[3]);
        float[] fArr3 = this.dstPoints;
        this.renderHeight = distance(fArr3[0], fArr3[1], fArr3[4], fArr3[5]);
    }

    private static int distance(float f2, float f3, float f4, float f5) {
        return (int) Math.round(Math.hypot(f4 - f2, f5 - f3));
    }

    public static void drawTexture(RendererCommon.GlDrawer glDrawer, VideoFrame.TextureBuffer textureBuffer, Matrix matrix, int i2, int i3, int i4, int i5, int i6, int i7) {
        Matrix matrix2 = new Matrix(textureBuffer.getTransformMatrix());
        c.h.a(TAG, "drawTexture " + matrix + "frameWidth: " + i2 + " frameHeight: " + i3);
        matrix2.preConcat(matrix);
        float[] fArrConvertMatrixFromAndroidGraphicsMatrix = RendererCommon.convertMatrixFromAndroidGraphicsMatrix(matrix2);
        int i8 = AnonymousClass1.$SwitchMap$org$wrtca$api$VideoFrame$TextureBuffer$Type[textureBuffer.getType().ordinal()];
        if (i8 == 1) {
            glDrawer.drawOes(textureBuffer.getTextureId(), fArrConvertMatrixFromAndroidGraphicsMatrix, i2, i3, i4, i5, i6, i7);
        } else {
            if (i8 != 2) {
                throw new RuntimeException("Unknown texture type.");
            }
            glDrawer.drawRgb(textureBuffer.getTextureId(), fArrConvertMatrixFromAndroidGraphicsMatrix, i2, i3, i4, i5, i6, i7);
        }
    }

    public void drawFrame(VideoFrame videoFrame, RendererCommon.GlDrawer glDrawer) {
        drawFrame(videoFrame, glDrawer, null);
    }

    public void release() {
        this.yuvUploader.release();
        this.lastI420Frame = null;
    }

    public void drawFrame(VideoFrame videoFrame, RendererCommon.GlDrawer glDrawer, Matrix matrix) {
        drawFrame(videoFrame, glDrawer, matrix, 0, 0, videoFrame.getRotatedWidth(), videoFrame.getRotatedHeight());
    }

    public void drawFrame(VideoFrame videoFrame, RendererCommon.GlDrawer glDrawer, Matrix matrix, int i2, int i3, int i4, int i5) {
        calculateTransformedRenderSize(videoFrame.getRotatedWidth(), videoFrame.getRotatedHeight(), matrix);
        boolean z2 = videoFrame.getBuffer() instanceof VideoFrame.TextureBuffer;
        this.renderMatrix.reset();
        this.renderMatrix.preTranslate(0.5f, 0.5f);
        if (!z2) {
            this.renderMatrix.preScale(1.0f, -1.0f);
        }
        this.renderMatrix.preRotate(videoFrame.getRotation());
        this.renderMatrix.preTranslate(-0.5f, -0.5f);
        if (matrix != null) {
            this.renderMatrix.preConcat(matrix);
        }
        if (z2) {
            this.lastI420Frame = null;
            drawTexture(glDrawer, (VideoFrame.TextureBuffer) videoFrame.getBuffer(), this.renderMatrix, this.renderWidth, this.renderHeight, i2, i3, i4, i5);
            return;
        }
        if (videoFrame != this.lastI420Frame) {
            this.lastI420Frame = videoFrame;
            VideoFrame.I420Buffer i420 = videoFrame.getBuffer().toI420();
            this.yuvUploader.uploadFromBuffer(i420);
            i420.release();
        }
        glDrawer.drawYuv(this.yuvUploader.getYuvTextures(), RendererCommon.convertMatrixFromAndroidGraphicsMatrix(this.renderMatrix), this.renderWidth, this.renderHeight, i2, i3, i4, i5);
    }
}
