package org.wrtca.api;

import java.nio.ByteBuffer;
import org.wrtca.api.VideoFrame;
import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.JNINamespace;
import org.wrtca.video.TextureBufferImpl;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class VideoRenderer {
    public long nativeVideoRenderer;

    public interface Callbacks {
        @CalledByNative("Callbacks")
        void renderFrame(I420Frame i420Frame);
    }

    public VideoRenderer(Callbacks callbacks) {
        this.nativeVideoRenderer = nativeCreateVideoRenderer(callbacks);
    }

    public static native void nativeCopyPlane(ByteBuffer byteBuffer, int i2, int i3, int i4, ByteBuffer byteBuffer2, int i5);

    private static native long nativeCreateVideoRenderer(Callbacks callbacks);

    private static native void nativeFreeWrappedVideoRenderer(long j2);

    private static native void nativeReleaseFrame(long j2);

    public static void renderFrameDone(I420Frame i420Frame) {
        i420Frame.yuvPlanes = null;
        i420Frame.textureId = 0;
        if (i420Frame.nativeFramePointer != 0) {
            nativeReleaseFrame(i420Frame.nativeFramePointer);
            i420Frame.nativeFramePointer = 0L;
        }
    }

    public void dispose() {
        long j2 = this.nativeVideoRenderer;
        if (j2 == 0) {
            return;
        }
        nativeFreeWrappedVideoRenderer(j2);
        this.nativeVideoRenderer = 0L;
    }

    public static class I420Frame {
        private final VideoFrame.Buffer backingBuffer;
        public final int height;
        private long nativeFramePointer;
        public int rotationDegree;
        public final float[] samplingMatrix;
        public int textureId;
        public final int width;
        public final boolean yuvFrame;
        public ByteBuffer[] yuvPlanes;
        public final int[] yuvStrides;

        public I420Frame(int i2, int i3, int i4, int[] iArr, ByteBuffer[] byteBufferArr, long j2) {
            this.width = i2;
            this.height = i3;
            this.yuvStrides = iArr;
            this.yuvPlanes = byteBufferArr;
            this.yuvFrame = true;
            this.rotationDegree = i4;
            this.nativeFramePointer = j2;
            this.backingBuffer = null;
            if (i4 % 90 == 0) {
                this.samplingMatrix = RendererCommon.verticalFlipMatrix();
                return;
            }
            throw new IllegalArgumentException("Rotation degree not multiple of 90: " + i4);
        }

        @CalledByNative("I420Frame")
        public static I420Frame createI420Frame(int i2, int i3, int i4, int i5, ByteBuffer byteBuffer, int i6, ByteBuffer byteBuffer2, int i7, ByteBuffer byteBuffer3, long j2) {
            return new I420Frame(i2, i3, i4, new int[]{i5, i6, i7}, new ByteBuffer[]{byteBuffer, byteBuffer2, byteBuffer3}, j2);
        }

        @CalledByNative("I420Frame")
        public static I420Frame createTextureFrame(int i2, int i3, int i4, int i5, float[] fArr, long j2) {
            return new I420Frame(i2, i3, i4, i5, fArr, j2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$toVideoFrame$0() {
            VideoRenderer.renderFrameDone(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$toVideoFrame$1() {
            VideoRenderer.renderFrameDone(this);
        }

        public int rotatedHeight() {
            return this.rotationDegree % 180 == 0 ? this.height : this.width;
        }

        public int rotatedWidth() {
            return this.rotationDegree % 180 == 0 ? this.width : this.height;
        }

        public String toString() {
            String str;
            if (this.yuvFrame) {
                str = "Y: " + this.yuvStrides[0] + ", U: " + this.yuvStrides[1] + ", V: " + this.yuvStrides[2];
            } else {
                str = "Texture: " + this.textureId;
            }
            return this.width + "x" + this.height + ", " + str;
        }

        public VideoFrame toVideoFrame() {
            VideoFrame.Buffer textureBufferImpl;
            VideoFrame.Buffer buffer = this.backingBuffer;
            if (buffer != null) {
                buffer.retain();
                VideoRenderer.renderFrameDone(this);
                textureBufferImpl = this.backingBuffer;
            } else if (this.yuvFrame) {
                int i2 = this.width;
                int i3 = this.height;
                ByteBuffer[] byteBufferArr = this.yuvPlanes;
                ByteBuffer byteBuffer = byteBufferArr[0];
                int[] iArr = this.yuvStrides;
                textureBufferImpl = JavaI420Buffer.wrap(i2, i3, byteBuffer, iArr[0], byteBufferArr[1], iArr[1], byteBufferArr[2], iArr[2], new Runnable() { // from class: org.wrtca.api.u
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f28134c.lambda$toVideoFrame$0();
                    }
                });
            } else {
                textureBufferImpl = new TextureBufferImpl(this.width, this.height, VideoFrame.TextureBuffer.Type.OES, this.textureId, RendererCommon.convertMatrixToAndroidGraphicsMatrix(this.samplingMatrix), null, new Runnable() { // from class: org.wrtca.api.v
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f28135c.lambda$toVideoFrame$1();
                    }
                });
            }
            return new VideoFrame(textureBufferImpl, this.rotationDegree, 0L);
        }

        public I420Frame(int i2, int i3, int i4, int i5, float[] fArr, long j2) {
            this.width = i2;
            this.height = i3;
            this.yuvStrides = null;
            this.yuvPlanes = null;
            this.samplingMatrix = fArr;
            this.textureId = i5;
            this.yuvFrame = false;
            this.rotationDegree = i4;
            this.nativeFramePointer = j2;
            this.backingBuffer = null;
            if (i4 % 90 == 0) {
                return;
            }
            throw new IllegalArgumentException("Rotation degree not multiple of 90: " + i4);
        }

        /* JADX WARN: Removed duplicated region for block: B:9:0x003d  */
        @org.wrtca.jni.CalledByNative("I420Frame")
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public I420Frame(int r7, org.wrtca.api.VideoFrame.Buffer r8, long r9) {
            /*
                r6 = this;
                r6.<init>()
                int r0 = r8.getWidth()
                r6.width = r0
                int r0 = r8.getHeight()
                r6.height = r0
                r6.rotationDegree = r7
                int r0 = r7 % 90
                if (r0 != 0) goto L8d
                boolean r7 = r8 instanceof org.wrtca.api.VideoFrame.TextureBuffer
                r0 = 0
                r1 = 0
                if (r7 == 0) goto L3d
                r7 = r8
                org.wrtca.api.VideoFrame$TextureBuffer r7 = (org.wrtca.api.VideoFrame.TextureBuffer) r7
                org.wrtca.api.VideoFrame$TextureBuffer$Type r2 = r7.getType()
                org.wrtca.api.VideoFrame$TextureBuffer$Type r3 = org.wrtca.api.VideoFrame.TextureBuffer.Type.OES
                if (r2 != r3) goto L3d
                r6.yuvFrame = r1
                int r1 = r7.getTextureId()
                r6.textureId = r1
                android.graphics.Matrix r7 = r7.getTransformMatrix()
                float[] r7 = org.wrtca.api.RendererCommon.convertMatrixFromAndroidGraphicsMatrix(r7)
                r6.samplingMatrix = r7
                r6.yuvStrides = r0
                r6.yuvPlanes = r0
                goto L88
            L3d:
                boolean r7 = r8 instanceof org.wrtca.api.VideoFrame.I420Buffer
                if (r7 == 0) goto L7e
                r7 = r8
                org.wrtca.api.VideoFrame$I420Buffer r7 = (org.wrtca.api.VideoFrame.I420Buffer) r7
                r0 = 1
                r6.yuvFrame = r0
                r2 = 3
                int[] r3 = new int[r2]
                int r4 = r7.getStrideY()
                r3[r1] = r4
                int r4 = r7.getStrideU()
                r3[r0] = r4
                int r4 = r7.getStrideV()
                r5 = 2
                r3[r5] = r4
                r6.yuvStrides = r3
                java.nio.ByteBuffer[] r2 = new java.nio.ByteBuffer[r2]
                java.nio.ByteBuffer r3 = r7.getDataY()
                r2[r1] = r3
                java.nio.ByteBuffer r3 = r7.getDataU()
                r2[r0] = r3
                java.nio.ByteBuffer r7 = r7.getDataV()
                r2[r5] = r7
                r6.yuvPlanes = r2
                float[] r7 = org.wrtca.api.RendererCommon.verticalFlipMatrix()
                r6.samplingMatrix = r7
                r6.textureId = r1
                goto L88
            L7e:
                r6.yuvFrame = r1
                r6.textureId = r1
                r6.samplingMatrix = r0
                r6.yuvStrides = r0
                r6.yuvPlanes = r0
            L88:
                r6.nativeFramePointer = r9
                r6.backingBuffer = r8
                return
            L8d:
                java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                java.lang.String r10 = "Rotation degree not multiple of 90: "
                r9.append(r10)
                r9.append(r7)
                java.lang.String r7 = r9.toString()
                r8.<init>(r7)
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: org.wrtca.api.VideoRenderer.I420Frame.<init>(int, org.wrtca.api.VideoFrame$Buffer, long):void");
        }
    }
}
