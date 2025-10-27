package com.tencent.rtmp.sharp.jni;

import android.annotation.SuppressLint;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.view.Surface;
import com.google.android.exoplayer2.util.MimeTypes;
import com.heytap.mcssdk.constant.a;
import com.tencent.liteav.basic.util.TXCBuild;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class MediaCodecDecoder {
    private static final String TAG = "MediaCodecDecoder";
    private MediaCodec mAudioAACDecoder = null;
    private int mChannels = 2;
    private int mSampleRate = 44100;
    ByteBuffer mInputBuffer = null;
    ByteBuffer mOutputBuffer = null;
    private MediaCodec.BufferInfo mAACDecBufferInfo = null;
    private ByteBuffer mDecInBuffer = ByteBuffer.allocateDirect(16384);
    private ByteBuffer mDecOutBuffer = ByteBuffer.allocateDirect(16384);
    private byte[] mTempBufDec = new byte[16384];

    @SuppressLint({"NewApi"})
    public int createAACDecoder(int i2, int i3) {
        try {
            this.mAudioAACDecoder = MediaCodec.createDecoderByType(MimeTypes.AUDIO_AAC);
            MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat(MimeTypes.AUDIO_AAC, i2, i3);
            mediaFormatCreateAudioFormat.setInteger("sample-rate", i2);
            mediaFormatCreateAudioFormat.setInteger("channel-count", i3);
            mediaFormatCreateAudioFormat.setInteger("aac-profile", 2);
            mediaFormatCreateAudioFormat.setByteBuffer("csd-0", ByteBuffer.wrap(new byte[]{17, -112}));
            this.mAudioAACDecoder.configure(mediaFormatCreateAudioFormat, (Surface) null, (MediaCrypto) null, 0);
            MediaCodec mediaCodec = this.mAudioAACDecoder;
            if (mediaCodec != null) {
                mediaCodec.start();
                this.mAACDecBufferInfo = new MediaCodec.BufferInfo();
                if (QLog.isColorLevel()) {
                    QLog.w(TAG, 2, "createAACDecoder succeed!!! : (" + i2 + ", " + i3 + ")");
                }
            }
            return 0;
        } catch (Exception unused) {
            if (!QLog.isColorLevel()) {
                return -1;
            }
            QLog.e("TRAE", 2, "Error when creating aac decode stream");
            return -1;
        }
    }

    public int decodeAACFrame(int i2) throws MediaCodec.CryptoException {
        this.mDecInBuffer.get(this.mTempBufDec, 0, i2);
        int iDecodeInternalAACFrame = decodeInternalAACFrame(i2);
        this.mDecOutBuffer.rewind();
        if (iDecodeInternalAACFrame <= 0) {
            return 0;
        }
        this.mDecOutBuffer.put(this.mTempBufDec, 0, iDecodeInternalAACFrame);
        return iDecodeInternalAACFrame;
    }

    @SuppressLint({"NewApi"})
    public int decodeInternalAACFrame(int i2) throws MediaCodec.CryptoException {
        while (true) {
            try {
                int iDequeueInputBuffer = this.mAudioAACDecoder.dequeueInputBuffer(200L);
                if (iDequeueInputBuffer >= 0) {
                    if (TXCBuild.VersionInt() >= 21) {
                        this.mInputBuffer = this.mAudioAACDecoder.getInputBuffer(iDequeueInputBuffer);
                    } else {
                        this.mInputBuffer = this.mAudioAACDecoder.getInputBuffers()[iDequeueInputBuffer];
                    }
                    this.mInputBuffer.clear();
                    this.mInputBuffer.put(this.mTempBufDec, 0, i2);
                    this.mDecInBuffer.rewind();
                    this.mAudioAACDecoder.queueInputBuffer(iDequeueInputBuffer, 0, i2, 0L, 0);
                }
                int iDequeueOutputBuffer = this.mAudioAACDecoder.dequeueOutputBuffer(this.mAACDecBufferInfo, a.f7153q);
                if (iDequeueOutputBuffer < 0) {
                    return 0;
                }
                if (TXCBuild.VersionInt() >= 21) {
                    this.mOutputBuffer = this.mAudioAACDecoder.getOutputBuffer(iDequeueOutputBuffer);
                } else {
                    this.mOutputBuffer = this.mAudioAACDecoder.getOutputBuffers()[iDequeueOutputBuffer];
                }
                int i3 = this.mAACDecBufferInfo.size;
                try {
                    this.mOutputBuffer.limit(i3);
                    this.mOutputBuffer.get(this.mTempBufDec, 0, i3);
                    this.mOutputBuffer.position(0);
                    this.mAudioAACDecoder.releaseOutputBuffer(iDequeueOutputBuffer, false);
                    return i3;
                } catch (Exception unused) {
                    if (QLog.isColorLevel()) {
                        QLog.e("TRAE", 2, "Error when decoding aac stream");
                    }
                }
            } catch (Exception unused2) {
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "decode failed.");
                }
                return 0;
            }
        }
    }

    @SuppressLint({"NewApi"})
    public int releaseAACDecoder() {
        try {
            MediaCodec mediaCodec = this.mAudioAACDecoder;
            if (mediaCodec != null) {
                mediaCodec.stop();
                this.mAudioAACDecoder.release();
                this.mAudioAACDecoder = null;
                if (!QLog.isColorLevel()) {
                    return 0;
                }
                QLog.w(TAG, 2, "releaseAACDecoder, release aac decode stream succeed!!");
                return 0;
            }
        } catch (Exception unused) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "release aac decoder failed.");
            }
        }
        if (!QLog.isColorLevel()) {
            return -1;
        }
        QLog.e("TRAE", 2, "releaseAACDecoder, Error when releasing aac decode stream");
        return -1;
    }
}
