package com.tencent.rtmp.sharp.jni;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;
import com.easefun.polyv.mediasdk.player.misc.IMediaFormat;
import com.heytap.mcssdk.constant.a;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCBuild;
import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class AudioDecoder {
    private static final String TAG = "AudioDecoder";
    private String srcPath;
    private MediaCodec mediaDecode = null;
    private MediaExtractor mediaExtractor = null;
    private ByteBuffer[] decodeInputBuffers = null;
    private ByteBuffer[] decodeOutputBuffers = null;
    private MediaCodec.BufferInfo decodeBufferInfo = null;
    private OnCompleteListener onCompleteListener = null;
    private OnProgressListener onProgressListener = null;
    private long fileTotalMs = 0;
    private RingBuffer decRingBuffer = null;
    int sampleRate = 0;
    int channels = 0;
    int nFrameSize = R2.attr.triangleHeight;
    boolean IsTenFramesReady = false;
    int nFirstThreeFrameInfo = 3;
    int m_nIndex = 0;
    private boolean codeOver = true;

    public interface OnCompleteListener {
        void completed();
    }

    public interface OnProgressListener {
        void progress();
    }

    private int initMediaDecode(int i2) throws IOException {
        try {
            MediaExtractor mediaExtractor = new MediaExtractor();
            this.mediaExtractor = mediaExtractor;
            mediaExtractor.setDataSource(this.srcPath);
            if (this.mediaExtractor.getTrackCount() > 1) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " initMediaDecode mediaExtractor container video, getTrackCount: " + this.mediaExtractor.getTrackCount());
                }
                this.codeOver = true;
                return -2;
            }
            int i3 = 0;
            while (true) {
                if (i3 >= this.mediaExtractor.getTrackCount()) {
                    break;
                }
                MediaFormat trackFormat = this.mediaExtractor.getTrackFormat(i3);
                String string = trackFormat.getString(IMediaFormat.KEY_MIME);
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " initMediaDecode mediaExtractor audio type:" + string);
                }
                if (string.startsWith("audio/mpeg")) {
                    this.mediaExtractor.selectTrack(i3);
                    MediaCodec mediaCodecCreateDecoderByType = MediaCodec.createDecoderByType(string);
                    this.mediaDecode = mediaCodecCreateDecoderByType;
                    mediaCodecCreateDecoderByType.configure(trackFormat, (Surface) null, (MediaCrypto) null, 0);
                    this.sampleRate = trackFormat.getInteger("sample-rate");
                    this.channels = trackFormat.getInteger("channel-count");
                    this.fileTotalMs = trackFormat.getLong("durationUs") / 1000;
                    int i4 = (((this.sampleRate * this.channels) * 2) * 20) / 1000;
                    this.nFrameSize = i4;
                    this.decRingBuffer = new RingBuffer(i4 * i2);
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " initMediaDecode open succeed, mp3 format:(" + this.sampleRate + "," + this.channels + "), fileTotalMs:" + this.fileTotalMs + "ms RingBufferFrame:" + i2);
                    }
                } else {
                    i3++;
                }
            }
            MediaCodec mediaCodec = this.mediaDecode;
            if (mediaCodec == null) {
                Log.e(TAG, "m_nIndex: " + this.m_nIndex + " initMediaDecode create mediaDecode failed");
                this.codeOver = true;
                return -1;
            }
            if (this.decRingBuffer == null) {
                Log.e(TAG, "m_nIndex: " + this.m_nIndex + " initMediaDecode create decRingBuffer failed");
                this.codeOver = true;
                return -1;
            }
            mediaCodec.start();
            this.decodeInputBuffers = this.mediaDecode.getInputBuffers();
            this.decodeOutputBuffers = this.mediaDecode.getOutputBuffers();
            this.decodeBufferInfo = new MediaCodec.BufferInfo();
            this.codeOver = false;
            this.IsTenFramesReady = false;
            this.nFirstThreeFrameInfo = 3;
            return 0;
        } catch (IOException e2) {
            TXCLog.e(TAG, "init media decode failed.", e2);
            this.codeOver = true;
            return -1;
        }
    }

    private void showLog(String str) {
        Log.e("AudioCodec", str);
    }

    private void srcAudioFormatToPCM() throws MediaCodec.CryptoException {
        int i2;
        if (this.decodeInputBuffers.length <= 1) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " srcAudioFormatToPCM decodeInputBuffers.length to small," + this.decodeInputBuffers.length);
            }
            this.codeOver = true;
            return;
        }
        int iDequeueInputBuffer = this.mediaDecode.dequeueInputBuffer(-1L);
        if (iDequeueInputBuffer < 0) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " srcAudioFormatToPCM decodeInputBuffers.inputIndex <0");
            }
            this.codeOver = true;
            return;
        }
        int iVersionInt = TXCBuild.VersionInt();
        ByteBuffer inputBuffer = iVersionInt >= 21 ? this.mediaDecode.getInputBuffer(iDequeueInputBuffer) : this.decodeInputBuffers[iDequeueInputBuffer];
        inputBuffer.clear();
        int sampleData = this.mediaExtractor.readSampleData(inputBuffer, 0);
        if (sampleData < 0) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " srcAudioFormatToPCM readSampleData over,end");
            }
            this.codeOver = true;
        } else {
            this.mediaDecode.queueInputBuffer(iDequeueInputBuffer, 0, sampleData, 0L, 0);
            this.mediaExtractor.advance();
        }
        int iDequeueOutputBuffer = this.mediaDecode.dequeueOutputBuffer(this.decodeBufferInfo, a.f7153q);
        while (iDequeueOutputBuffer >= 0) {
            ByteBuffer outputBuffer = iVersionInt >= 21 ? this.mediaDecode.getOutputBuffer(iDequeueOutputBuffer) : this.decodeOutputBuffers[iDequeueOutputBuffer];
            byte[] bArr = new byte[this.decodeBufferInfo.size];
            try {
                outputBuffer.get(bArr);
                outputBuffer.clear();
                RingBuffer ringBuffer = this.decRingBuffer;
                if (ringBuffer != null && (i2 = this.decodeBufferInfo.size) > 0) {
                    ringBuffer.Push(bArr, i2);
                    int i3 = this.nFirstThreeFrameInfo;
                    this.nFirstThreeFrameInfo = i3 - 1;
                    if (i3 > 0 && QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " DecodeOneFrame size: " + this.decodeBufferInfo.size + " Remain: " + (this.decRingBuffer.RemainRead() / this.nFrameSize));
                    }
                }
                this.mediaDecode.releaseOutputBuffer(iDequeueOutputBuffer, false);
                MediaCodec.BufferInfo bufferInfo = this.decodeBufferInfo;
                if (bufferInfo.size > 0) {
                    return;
                } else {
                    iDequeueOutputBuffer = this.mediaDecode.dequeueOutputBuffer(bufferInfo, a.f7153q);
                }
            } catch (Exception unused) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " srcAudioFormatToPCM wrong outputIndex: " + iDequeueOutputBuffer);
                }
                this.codeOver = true;
                return;
            }
        }
    }

    public int ReadOneFrame(byte[] bArr, int i2) throws MediaCodec.CryptoException {
        int i3 = 20;
        if (!this.IsTenFramesReady) {
            int i4 = 20;
            while (this.decRingBuffer.RemainRead() / this.nFrameSize < 10) {
                int i5 = i4 - 1;
                if (i4 <= 0 || this.codeOver) {
                    break;
                }
                srcAudioFormatToPCM();
                i4 = i5;
            }
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " 10 FramesReady Remain frame: " + (this.decRingBuffer.RemainRead() / this.nFrameSize));
            }
            this.IsTenFramesReady = true;
        }
        while (!this.codeOver && this.decRingBuffer.RemainRead() / this.nFrameSize < 10) {
            int i6 = i3 - 1;
            if (i3 <= 0) {
                break;
            }
            srcAudioFormatToPCM();
            i3 = i6;
        }
        if (this.decRingBuffer.RemainRead() < i2) {
            return -1;
        }
        this.decRingBuffer.Pop(bArr, i2);
        return i2;
    }

    public int SeekTo(int i2) {
        MediaExtractor mediaExtractor = this.mediaExtractor;
        if (mediaExtractor == null) {
            return 0;
        }
        long sampleTime = mediaExtractor.getSampleTime();
        int iRemainRead = i2 + ((this.decRingBuffer.RemainRead() * 20) / this.nFrameSize);
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " current PlayMs: " + (sampleTime / 1000) + " SeekTo: " + iRemainRead);
        }
        this.mediaExtractor.seekTo(iRemainRead * 1000, 2);
        long sampleTime2 = this.mediaExtractor.getSampleTime();
        int i3 = (int) ((sampleTime2 - sampleTime) / 1000);
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " total SeekTo time: " + i3 + " t2:" + (sampleTime2 / 1000));
        }
        return i3;
    }

    public int getChannels() {
        return this.channels;
    }

    public long getFileTotalMs() {
        return this.fileTotalMs;
    }

    public int getFrameSize() {
        return this.nFrameSize;
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public int prepare(int i2) {
        if (this.srcPath == null) {
            return -1;
        }
        return initMediaDecode(i2);
    }

    public void release() {
        MediaCodec mediaCodec = this.mediaDecode;
        if (mediaCodec != null) {
            mediaCodec.stop();
            this.mediaDecode.release();
            this.mediaDecode = null;
        }
        MediaExtractor mediaExtractor = this.mediaExtractor;
        if (mediaExtractor != null) {
            mediaExtractor.release();
            this.mediaExtractor = null;
        }
        if (this.onCompleteListener != null) {
            this.onCompleteListener = null;
        }
        if (this.onProgressListener != null) {
            this.onProgressListener = null;
        }
        showLog("release");
    }

    public void setIOPath(String str) {
        this.srcPath = str;
    }

    public void setIndex(int i2) {
        this.m_nIndex = i2;
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }
}
