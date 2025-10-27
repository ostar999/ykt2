package org.wrtca.customize;

import android.content.Context;
import android.os.SystemClock;
import core.interfaces.DataProvider;
import d.b;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.wrtca.api.JavaI420Buffer;
import org.wrtca.api.SurfaceTextureHelper;
import org.wrtca.api.VideoCapturer;
import org.wrtca.api.VideoFrame;
import org.wrtca.api.YuvHelper;
import org.wrtca.jni.JniCommon;

/* loaded from: classes9.dex */
public class RtcExDevice2YUVCapturer implements VideoCapturer {
    private static final String TAG = "YUVCapture";
    public static final ReentrantLock reentrantLock = new ReentrantLock(true);
    private VideoCapturer.CapturerObserver mCapturerObserver;
    public DataProvider mDataPusher;
    private int mFrameRate;
    private boolean mStop;
    private ByteBuffer yuvCache;
    private Timer mTimer = null;
    private TimerTask mTickTask = null;
    private RtcVideoReader mVideoReader = new RGBAReader();

    public class RGBAReader implements RtcVideoReader {
        private RGBAReader() {
        }

        @Override // org.wrtca.customize.RtcVideoReader
        public void close() {
        }

        @Override // org.wrtca.customize.RtcVideoReader
        public VideoFrame getNextFrame(RtcDataSource rtcDataSource, int i2) {
            if (!(rtcDataSource instanceof Convert2YUVData)) {
                return null;
            }
            Convert2YUVData convert2YUVData = (Convert2YUVData) rtcDataSource;
            return new VideoFrame(JavaI420Buffer.allocate(RtcExDevice2YUVCapturer.convert(RtcExDevice2YUVCapturer.this.yuvCache, convert2YUVData.mSrcWidth, convert2YUVData.mSrcHeight), convert2YUVData.mSrcWidth, convert2YUVData.mSrcHeight), i2, TimeUnit.MILLISECONDS.toNanos(SystemClock.elapsedRealtime()));
        }
    }

    public RtcExDevice2YUVCapturer(DataProvider dataProvider) {
        this.mDataPusher = dataProvider;
    }

    public static void Mirror(byte[] bArr, int i2, int i3) {
        int i4;
        int i5 = 0;
        int i6 = 0;
        while (i6 < i3) {
            int i7 = i6 * i2;
            i6++;
            for (int i8 = (i6 * i2) - 1; i7 < i8; i8--) {
                byte b3 = bArr[i7];
                bArr[i7] = bArr[i8];
                bArr[i8] = b3;
                i7++;
            }
        }
        int i9 = i2 * i3;
        int i10 = 0;
        while (true) {
            i4 = i3 / 2;
            if (i10 >= i4) {
                break;
            }
            int i11 = (i10 * i2) / 2;
            i10++;
            for (int i12 = ((i10 * i2) / 2) - 1; i11 < i12; i12--) {
                int i13 = i11 + i9;
                byte b4 = bArr[i13];
                int i14 = i12 + i9;
                bArr[i13] = bArr[i14];
                bArr[i14] = b4;
                i11++;
            }
        }
        int i15 = (i9 / 4) * 5;
        while (i5 < i4) {
            int i16 = (i5 * i2) / 2;
            i5++;
            for (int i17 = ((i5 * i2) / 2) - 1; i16 < i17; i17--) {
                int i18 = i16 + i15;
                byte b5 = bArr[i18];
                int i19 = i17 + i15;
                bArr[i18] = bArr[i19];
                bArr[i19] = b5;
                i16++;
            }
        }
    }

    public static byte[] convert(ByteBuffer byteBuffer) {
        byteBuffer.position(0);
        byte[] bArr = new byte[byteBuffer.limit() - byteBuffer.position()];
        if (byteBuffer.isReadOnly()) {
            return null;
        }
        byteBuffer.get(bArr);
        return bArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x001e A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0021 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getFrameOrientation() {
        /*
            r4 = this;
            boolean r0 = d.b.s()
            int r1 = d.b.i()
            r2 = 1
            r3 = 0
            if (r1 == r2) goto L23
            r2 = 2
            if (r1 == r2) goto L1c
            r2 = 3
            if (r1 == r2) goto L19
            r2 = 4
            if (r1 == r2) goto L16
            goto L23
        L16:
            if (r0 == 0) goto L1e
            goto L21
        L19:
            r3 = 180(0xb4, float:2.52E-43)
            goto L23
        L1c:
            if (r0 == 0) goto L21
        L1e:
            r3 = 90
            goto L23
        L21:
            r3 = 270(0x10e, float:3.78E-43)
        L23:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.wrtca.customize.RtcExDevice2YUVCapturer.getFrameOrientation():int");
    }

    private void initTimerTask() {
        this.mTickTask = new TimerTask() { // from class: org.wrtca.customize.RtcExDevice2YUVCapturer.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (RtcExDevice2YUVCapturer.this.mStop) {
                    return;
                }
                RtcExDevice2YUVCapturer.this.tickConvertData();
            }
        };
        this.mTimer = new Timer("capturer");
    }

    @Override // org.wrtca.api.VideoCapturer
    public void changeCaptureFormat(int i2, int i3, int i4) {
    }

    @Override // org.wrtca.api.VideoCapturer
    public void dispose() {
        RtcVideoReader rtcVideoReader = this.mVideoReader;
        if (rtcVideoReader != null) {
            rtcVideoReader.close();
        }
    }

    public int getFrameRate() {
        return this.mFrameRate;
    }

    @Override // org.wrtca.api.VideoCapturer
    public void initialize(SurfaceTextureHelper surfaceTextureHelper, Context context, VideoCapturer.CapturerObserver capturerObserver) {
        this.mCapturerObserver = capturerObserver;
    }

    @Override // org.wrtca.api.VideoCapturer
    public boolean isScreencast() {
        return false;
    }

    @Override // org.wrtca.api.VideoCapturer
    public void startCapture(int i2, int i3, int i4) {
        this.mFrameRate = i4;
        this.mStop = false;
        if (this.mDataPusher != null) {
            this.yuvCache = JniCommon.nativeAllocateByteBuffer(35389440);
            initTimerTask();
            this.mTimer.scheduleAtFixedRate(this.mTickTask, 0L, 1000 / i4);
        }
    }

    @Override // org.wrtca.api.VideoCapturer
    public void stopCapture() throws InterruptedException {
        ReentrantLock reentrantLock2 = reentrantLock;
        reentrantLock2.lock();
        try {
            this.mStop = true;
            DataProvider dataProvider = this.mDataPusher;
            if (dataProvider != null) {
                dataProvider.releaseBuffer();
                this.mDataPusher = null;
            }
            this.mTimer.cancel();
            this.mTimer.purge();
            this.mTimer = null;
            JniCommon.nativeFreeByteBuffer(this.yuvCache);
            reentrantLock2.unlock();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    public void tickConvertData() {
        int iRgb565ToI420;
        ReentrantLock reentrantLock2 = reentrantLock;
        reentrantLock2.lock();
        try {
            Convert2YUVData convert2YUVData = new Convert2YUVData();
            convert2YUVData.pushData(this.mDataPusher);
            ByteBuffer byteBuffer = convert2YUVData.mSrcData;
            if (byteBuffer != null && byteBuffer.limit() > 0) {
                int i2 = 0;
                convert2YUVData.mSrcData.position(0);
                this.yuvCache.clear();
                int i3 = convert2YUVData.mType;
                switch (i3) {
                    case DataProvider.RGB565_TO_I420 /* 16781349 */:
                        iRgb565ToI420 = YuvHelper.Rgb565ToI420(i3, convert2YUVData.mSrcData, this.yuvCache, convert2YUVData.mSrcWidth, convert2YUVData.mSrcHeight);
                        i2 = iRgb565ToI420;
                        break;
                    case DataProvider.RGB24_TO_I420 /* 16781364 */:
                        iRgb565ToI420 = YuvHelper.Rgb24ToI420(i3, convert2YUVData.mSrcData, this.yuvCache, convert2YUVData.mSrcWidth, convert2YUVData.mSrcHeight);
                        i2 = iRgb565ToI420;
                        break;
                    case 16781376:
                    case 16781377:
                    case DataProvider.BGRA_TO_I420 /* 16781378 */:
                        iRgb565ToI420 = YuvHelper.RgbaToI420(i3, convert2YUVData.mSrcData, this.yuvCache, convert2YUVData.mSrcWidth, convert2YUVData.mSrcHeight);
                        i2 = iRgb565ToI420;
                        break;
                    case DataProvider.ARGB_TO_I420 /* 16781379 */:
                        iRgb565ToI420 = YuvHelper.ArgbToI420(i3, convert2YUVData.mSrcData, this.yuvCache, convert2YUVData.mSrcWidth, convert2YUVData.mSrcHeight);
                        i2 = iRgb565ToI420;
                        break;
                    case DataProvider.NV21 /* 16781456 */:
                        iRgb565ToI420 = YuvHelper.NV21ToI420(i3, convert2YUVData.mSrcData, this.yuvCache, convert2YUVData.mSrcWidth, convert2YUVData.mSrcHeight);
                        i2 = iRgb565ToI420;
                        break;
                    case DataProvider.NV12 /* 16781457 */:
                        iRgb565ToI420 = YuvHelper.NV12ToI420(i3, convert2YUVData.mSrcData, this.yuvCache, convert2YUVData.mSrcWidth, convert2YUVData.mSrcHeight);
                        i2 = iRgb565ToI420;
                        break;
                    case DataProvider.I420 /* 16781465 */:
                        this.yuvCache.put(convert2YUVData.mSrcData);
                        break;
                    default:
                        i2 = -1;
                        break;
                }
                if (i2 == 0) {
                    VideoFrame nextFrame = this.mVideoReader.getNextFrame(convert2YUVData, getFrameOrientation());
                    VideoCapturer.CapturerObserver capturerObserver = this.mCapturerObserver;
                    if (capturerObserver != null && nextFrame != null) {
                        capturerObserver.onFrameCaptured(nextFrame);
                        this.yuvCache.clear();
                        nextFrame.release();
                    }
                }
            }
            reentrantLock2.unlock();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    public static byte[] convert(ByteBuffer byteBuffer, int i2, int i3) {
        byteBuffer.position(0);
        byte[] bArr = new byte[(i2 * i3) + (((i2 + 1) / 2) * 2 * ((i3 + 1) / 2))];
        boolean zS = b.s();
        if (byteBuffer.isReadOnly()) {
            return null;
        }
        byteBuffer.get(bArr);
        if (zS) {
            Mirror(bArr, i2, i3);
        }
        return bArr;
    }
}
