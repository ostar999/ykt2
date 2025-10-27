package org.wrtca.api;

import android.content.Context;
import android.os.SystemClock;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import org.wrtca.api.VideoCapturer;
import org.wrtca.log.Logging;

/* loaded from: classes9.dex */
public class FileVideoCapturer implements VideoCapturer {
    private static final String TAG = "FileVideoCapturer";
    private VideoCapturer.CapturerObserver capturerObserver;
    private final VideoReader videoReader;
    private final Timer timer = new Timer();
    private final TimerTask tickTask = new TimerTask() { // from class: org.wrtca.api.FileVideoCapturer.1
        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            FileVideoCapturer.this.tick();
        }
    };

    public interface VideoReader {
        void close();

        VideoFrame getNextFrame();
    }

    public static class VideoReaderY4M implements VideoReader {
        private static final int FRAME_DELIMETER_LENGTH = 6;
        private static final String TAG = "VideoReaderY4M";
        private static final String Y4M_FRAME_DELIMETER = "FRAME";
        private final int frameHeight;
        private final int frameWidth;
        private final RandomAccessFile mediaFile;
        private final FileChannel mediaFileChannel;
        private final long videoStart;

        public VideoReaderY4M(String str) throws IOException, NumberFormatException {
            RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
            this.mediaFile = randomAccessFile;
            this.mediaFileChannel = randomAccessFile.getChannel();
            StringBuilder sb = new StringBuilder();
            while (true) {
                int i2 = this.mediaFile.read();
                if (i2 == -1) {
                    throw new RuntimeException("Found end of file before end of header for file: " + str);
                }
                if (i2 == 10) {
                    this.videoStart = this.mediaFileChannel.position();
                    String strSubstring = "";
                    int i3 = 0;
                    int i4 = 0;
                    for (String str2 : sb.toString().split("[ ]")) {
                        char cCharAt = str2.charAt(0);
                        if (cCharAt == 'C') {
                            strSubstring = str2.substring(1);
                        } else if (cCharAt == 'H') {
                            i4 = Integer.parseInt(str2.substring(1));
                        } else if (cCharAt == 'W') {
                            i3 = Integer.parseInt(str2.substring(1));
                        }
                    }
                    Logging.d(TAG, "Color space: " + strSubstring);
                    if (!strSubstring.equals("420") && !strSubstring.equals("420mpeg2")) {
                        throw new IllegalArgumentException("Does not support any other color space than I420 or I420mpeg2");
                    }
                    if (i3 % 2 == 1 || i4 % 2 == 1) {
                        throw new IllegalArgumentException("Does not support odd width or height");
                    }
                    this.frameWidth = i3;
                    this.frameHeight = i4;
                    Logging.d(TAG, "frame dim: (" + i3 + ", " + i4 + ")");
                    return;
                }
                sb.append((char) i2);
            }
        }

        @Override // org.wrtca.api.FileVideoCapturer.VideoReader
        public void close() throws IOException {
            try {
                this.mediaFile.close();
            } catch (IOException e2) {
                Logging.e(TAG, "Problem closing file", e2);
            }
        }

        @Override // org.wrtca.api.FileVideoCapturer.VideoReader
        public VideoFrame getNextFrame() throws IOException {
            long nanos = TimeUnit.MILLISECONDS.toNanos(SystemClock.elapsedRealtime());
            JavaI420Buffer javaI420BufferAllocate = JavaI420Buffer.allocate(this.frameWidth, this.frameHeight);
            ByteBuffer dataY = javaI420BufferAllocate.getDataY();
            ByteBuffer dataU = javaI420BufferAllocate.getDataU();
            ByteBuffer dataV = javaI420BufferAllocate.getDataV();
            int i2 = (this.frameHeight + 1) / 2;
            javaI420BufferAllocate.getStrideY();
            javaI420BufferAllocate.getStrideU();
            javaI420BufferAllocate.getStrideV();
            try {
                int i3 = FRAME_DELIMETER_LENGTH;
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i3);
                if (this.mediaFileChannel.read(byteBufferAllocate) < i3) {
                    this.mediaFileChannel.position(this.videoStart);
                    if (this.mediaFileChannel.read(byteBufferAllocate) < i3) {
                        throw new RuntimeException("Error looping video");
                    }
                }
                String str = new String(byteBufferAllocate.array(), Charset.forName("US-ASCII"));
                if (str.equals("FRAME\n")) {
                    this.mediaFileChannel.read(dataY);
                    this.mediaFileChannel.read(dataU);
                    this.mediaFileChannel.read(dataV);
                    return new VideoFrame(javaI420BufferAllocate, 0, nanos);
                }
                throw new RuntimeException("Frames should be delimited by FRAME plus newline, found delimter was: '" + str + "'");
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public FileVideoCapturer(String str) throws IOException {
        try {
            this.videoReader = new VideoReaderY4M(str);
        } catch (IOException e2) {
            Logging.d(TAG, "Could not open video file: " + str);
            throw e2;
        }
    }

    @Override // org.wrtca.api.VideoCapturer
    public void changeCaptureFormat(int i2, int i3, int i4) {
    }

    @Override // org.wrtca.api.VideoCapturer
    public void dispose() {
        this.videoReader.close();
    }

    @Override // org.wrtca.api.VideoCapturer
    public void initialize(SurfaceTextureHelper surfaceTextureHelper, Context context, VideoCapturer.CapturerObserver capturerObserver) {
        this.capturerObserver = capturerObserver;
    }

    @Override // org.wrtca.api.VideoCapturer
    public boolean isScreencast() {
        return false;
    }

    @Override // org.wrtca.api.VideoCapturer
    public void startCapture(int i2, int i3, int i4) {
        this.timer.schedule(this.tickTask, 0L, 1000 / i4);
    }

    @Override // org.wrtca.api.VideoCapturer
    public void stopCapture() throws InterruptedException {
        this.timer.cancel();
    }

    public void tick() {
        this.capturerObserver.onFrameCaptured(this.videoReader.getNextFrame());
    }
}
