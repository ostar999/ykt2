package org.wrtca.api;

import android.os.Handler;
import android.os.HandlerThread;
import cn.hutool.core.text.StrPool;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import org.wrtca.api.EglBase;
import org.wrtca.api.VideoFrame;
import org.wrtca.api.VideoRenderer;
import org.wrtca.jni.JNINamespace;
import org.wrtca.jni.JniCommon;
import org.wrtca.log.Logging;
import org.wrtca.util.ThreadUtils;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class VideoFileRenderer implements VideoRenderer.Callbacks, VideoSink {
    private static final String TAG = "VideoFileRenderer";
    private EglBase eglBase;
    private final int outputFileHeight;
    private final String outputFileName;
    private final int outputFileWidth;
    private final ByteBuffer outputFrameBuffer;
    private final int outputFrameSize;
    private ArrayList<ByteBuffer> rawFrames = new ArrayList<>();
    private final HandlerThread renderThread;
    private final Handler renderThreadHandler;
    private final FileOutputStream videoOutFile;
    private YuvConverter yuvConverter;

    public VideoFileRenderer(String str, int i2, int i3, final EglBase.Context context) throws IOException {
        if (i2 % 2 == 1 || i3 % 2 == 1) {
            throw new IllegalArgumentException("Does not support uneven width or height");
        }
        this.outputFileName = str;
        this.outputFileWidth = i2;
        this.outputFileHeight = i3;
        int i4 = ((i2 * i3) * 3) / 2;
        this.outputFrameSize = i4;
        this.outputFrameBuffer = ByteBuffer.allocateDirect(i4);
        FileOutputStream fileOutputStream = new FileOutputStream(str);
        this.videoOutFile = fileOutputStream;
        fileOutputStream.write(("YUV4MPEG2 C420 W" + i2 + " H" + i3 + " Ip F30:1 A1:1\n").getBytes(Charset.forName("US-ASCII")));
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.renderThread = handlerThread;
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        this.renderThreadHandler = handler;
        ThreadUtils.invokeAtFrontUninterruptibly(handler, new Runnable() { // from class: org.wrtca.api.VideoFileRenderer.1
            @Override // java.lang.Runnable
            public void run() {
                VideoFileRenderer.this.eglBase = a.c(context, EglBase.CONFIG_PIXEL_BUFFER);
                VideoFileRenderer.this.eglBase.createDummyPbufferSurface();
                VideoFileRenderer.this.eglBase.makeCurrent();
                VideoFileRenderer.this.yuvConverter = new YuvConverter();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$release$1(CountDownLatch countDownLatch) {
        this.yuvConverter.release();
        this.eglBase.release();
        this.renderThread.quit();
        countDownLatch.countDown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: renderFrameOnRenderThread, reason: merged with bridge method [inline-methods] */
    public void lambda$onFrame$0(VideoFrame videoFrame) {
        VideoFrame.Buffer buffer = videoFrame.getBuffer();
        int i2 = videoFrame.getRotation() % 180 == 0 ? this.outputFileWidth : this.outputFileHeight;
        int i3 = videoFrame.getRotation() % 180 == 0 ? this.outputFileHeight : this.outputFileWidth;
        float width = buffer.getWidth() / buffer.getHeight();
        float f2 = i2 / i3;
        int width2 = buffer.getWidth();
        int height = buffer.getHeight();
        if (f2 > width) {
            height = (int) (height * (width / f2));
        } else {
            width2 = (int) (width2 * (f2 / width));
        }
        VideoFrame.Buffer bufferCropAndScale = buffer.cropAndScale((buffer.getWidth() - width2) / 2, (buffer.getHeight() - height) / 2, width2, height, i2, i3);
        videoFrame.release();
        VideoFrame.I420Buffer i420 = bufferCropAndScale.toI420();
        bufferCropAndScale.release();
        ByteBuffer byteBufferNativeAllocateByteBuffer = JniCommon.nativeAllocateByteBuffer(this.outputFrameSize);
        YuvHelper.I420Rotate(i420.getDataY(), i420.getStrideY(), i420.getDataU(), i420.getStrideU(), i420.getDataV(), i420.getStrideV(), byteBufferNativeAllocateByteBuffer, i420.getWidth(), i420.getHeight(), videoFrame.getRotation());
        i420.release();
        byteBufferNativeAllocateByteBuffer.rewind();
        this.rawFrames.add(byteBufferNativeAllocateByteBuffer);
    }

    @Override // org.wrtca.api.VideoSink
    public void onFrame(final VideoFrame videoFrame) {
        videoFrame.retain();
        this.renderThreadHandler.post(new Runnable() { // from class: org.wrtca.api.s
            @Override // java.lang.Runnable
            public final void run() {
                this.f28131c.lambda$onFrame$0(videoFrame);
            }
        });
    }

    public void release() throws IOException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.renderThreadHandler.post(new Runnable() { // from class: org.wrtca.api.r
            @Override // java.lang.Runnable
            public final void run() {
                this.f28129c.lambda$release$1(countDownLatch);
            }
        });
        ThreadUtils.awaitUninterruptibly(countDownLatch);
        try {
            Iterator<ByteBuffer> it = this.rawFrames.iterator();
            while (it.hasNext()) {
                ByteBuffer next = it.next();
                this.videoOutFile.write("FRAME\n".getBytes(Charset.forName("US-ASCII")));
                byte[] bArr = new byte[this.outputFrameSize];
                next.get(bArr);
                this.videoOutFile.write(bArr);
                JniCommon.nativeFreeByteBuffer(next);
            }
            this.videoOutFile.close();
            Logging.d(TAG, "Video written to disk as " + this.outputFileName + ". Number frames are " + this.rawFrames.size() + " and the dimension of the frames are " + this.outputFileWidth + "x" + this.outputFileHeight + StrPool.DOT);
        } catch (IOException e2) {
            Logging.e(TAG, "Error writing video to disk", e2);
        }
    }

    @Override // org.wrtca.api.VideoRenderer.Callbacks
    public void renderFrame(VideoRenderer.I420Frame i420Frame) {
        VideoFrame videoFrame = i420Frame.toVideoFrame();
        onFrame(videoFrame);
        videoFrame.release();
    }
}
