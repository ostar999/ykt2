package com.tencent.rtmp.sharp.jni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.view.Surface;
import cn.hutool.core.text.StrPool;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.yikaobang.yixue.R2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class MediaCodecEncoder {
    private static final String TAG = "MediaCodecEncoder";
    private static boolean mDumpEnable = false;
    private MediaCodec.BufferInfo mAACEncBufferInfo;
    private MediaCodec mAudioAACEncoder;
    private MediaFormat mAudioFormat;
    private int mBitrate;
    private int mChannels;
    private Context mContext;
    private ByteBuffer mEncInBuffer;
    private ByteBuffer mEncOutBuffer;
    private boolean mFormatChangeFlag;
    private ByteBuffer mInputBuffer;
    private ByteBuffer[] mMediaInputBuffers;
    private ByteBuffer[] mMediaOutputBuffers;
    private ByteBuffer mOutputBuffer;
    private File mRecFileDump;
    private FileOutputStream mRecFileOut;
    private int mSampleRate;
    private byte[] mTempBufEncIn;
    private byte[] mTempBufEncOut;
    private int nMaxBitRate;

    public MediaCodecEncoder() {
        this.mAudioAACEncoder = null;
        this.mAudioFormat = null;
        this.mAACEncBufferInfo = null;
        this.mInputBuffer = null;
        this.mOutputBuffer = null;
        this.mSampleRate = 48000;
        this.mChannels = 1;
        this.mBitrate = 32000;
        this.nMaxBitRate = AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND;
        this.mFormatChangeFlag = false;
        this.mRecFileDump = null;
        this.mRecFileOut = null;
        this.mContext = TXCCommonUtil.getAppContext();
        this.mEncInBuffer = ByteBuffer.allocateDirect(R2.dimen.dp_m_8);
        this.mTempBufEncIn = new byte[R2.dimen.dp_m_8];
        this.mEncOutBuffer = ByteBuffer.allocateDirect((((this.nMaxBitRate * 2) / 8) / 50) + 100);
        this.mTempBufEncOut = new byte[(((this.nMaxBitRate * 2) / 8) / 50) + 100];
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x000b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void addADTStoPacket(byte[] r9, int r10) {
        /*
            r8 = this;
            int r0 = r8.mSampleRate
            r1 = 48000(0xbb80, float:6.7262E-41)
            r2 = 4
            r3 = 5
            r4 = 6
            r5 = 3
            if (r0 != r1) goto Ld
        Lb:
            r0 = r5
            goto L26
        Ld:
            r1 = 44100(0xac44, float:6.1797E-41)
            if (r0 != r1) goto L14
            r0 = r2
            goto L26
        L14:
            r1 = 32000(0x7d00, float:4.4842E-41)
            if (r0 != r1) goto L1a
            r0 = r3
            goto L26
        L1a:
            r1 = 24000(0x5dc0, float:3.3631E-41)
            if (r0 != r1) goto L20
            r0 = r4
            goto L26
        L20:
            r1 = 16000(0x3e80, float:2.2421E-41)
            if (r0 != r1) goto Lb
            r0 = 8
        L26:
            int r1 = r8.mChannels
            r6 = 0
            r7 = -1
            r9[r6] = r7
            r6 = 1
            r7 = -7
            r9[r6] = r7
            r6 = 64
            r7 = 2
            int r0 = r0 << r7
            int r6 = r6 + r0
            int r0 = r1 >> 2
            int r6 = r6 + r0
            byte r0 = (byte) r6
            r9[r7] = r0
            r0 = r1 & 3
            int r0 = r0 << r4
            int r1 = r10 >> 11
            int r0 = r0 + r1
            byte r0 = (byte) r0
            r9[r5] = r0
            r0 = r10 & 2047(0x7ff, float:2.868E-42)
            int r0 = r0 >> r5
            byte r0 = (byte) r0
            r9[r2] = r0
            r10 = r10 & 7
            int r10 = r10 << r3
            int r10 = r10 + 31
            byte r10 = (byte) r10
            r9[r3] = r10
            r10 = -4
            r9[r4] = r10
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.rtmp.sharp.jni.MediaCodecEncoder.addADTStoPacket(byte[], int):void");
    }

    private String getDumpFilePath(String str) {
        File externalFilesDir;
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "manufacture:" + TXCBuild.Manufacturer());
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "MODEL:" + TXCBuild.Model());
        }
        Context context = this.mContext;
        if (context == null || (externalFilesDir = context.getExternalFilesDir(null)) == null) {
            return null;
        }
        String str2 = externalFilesDir.getPath() + "/MF-" + TXCBuild.Manufacturer() + "-M-" + TXCBuild.Model() + "-" + str;
        File file = new File(str2);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "dump:" + str2);
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "dump replace:" + str2.replace(" ", StrPool.UNDERLINE));
        }
        return str2.replace(" ", StrPool.UNDERLINE);
    }

    @SuppressLint({"NewApi"})
    public int createAACEncoder(int i2, int i3, int i4) {
        try {
            this.mAudioAACEncoder = MediaCodec.createEncoderByType(MimeTypes.AUDIO_AAC);
            MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat(MimeTypes.AUDIO_AAC, i2, i3);
            this.mAudioFormat = mediaFormatCreateAudioFormat;
            mediaFormatCreateAudioFormat.setInteger("aac-profile", 2);
            this.mAudioFormat.setInteger("sample-rate", i2);
            this.mAudioFormat.setInteger("channel-count", i3);
            this.mAudioFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, i4);
            this.mAudioAACEncoder.configure(this.mAudioFormat, (Surface) null, (MediaCrypto) null, 1);
            MediaCodec mediaCodec = this.mAudioAACEncoder;
            if (mediaCodec != null) {
                mediaCodec.start();
                this.mAACEncBufferInfo = new MediaCodec.BufferInfo();
                this.mSampleRate = i2;
                this.mChannels = i3;
                this.mBitrate = i4;
            }
            if (mDumpEnable) {
                this.mRecFileDump = new File(getDumpFilePath("jnirecord.aac"));
                try {
                    this.mRecFileOut = new FileOutputStream(this.mRecFileDump);
                } catch (FileNotFoundException unused) {
                    if (QLog.isColorLevel()) {
                        QLog.e(TAG, 2, "open jnirecord.aac file failed.");
                    }
                }
            }
            if (!QLog.isColorLevel()) {
                return 0;
            }
            QLog.w(TAG, 2, "createAACEncoder succeed!!! : (" + i2 + ", " + i3 + ", " + i4 + ")");
            return 0;
        } catch (Exception unused2) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "create AAC Encoder failed.");
            }
            if (!QLog.isColorLevel()) {
                return -1;
            }
            QLog.e(TAG, 2, "[ERROR] creating aac encode stream failed!!! : (" + i2 + ", " + i3 + ", " + i4 + ")");
            return -1;
        }
    }

    @SuppressLint({"NewApi"})
    public int encodeAACFrame(int i2) throws MediaCodec.CryptoException, IOException {
        if (this.mFormatChangeFlag) {
            this.mFormatChangeFlag = false;
            this.mAudioAACEncoder.stop();
            this.mAudioFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, this.mBitrate);
            this.mAudioAACEncoder.configure(this.mAudioFormat, (Surface) null, (MediaCrypto) null, 1);
            this.mAudioAACEncoder.start();
        }
        this.mEncInBuffer.get(this.mTempBufEncIn, 0, i2);
        int iEncodeInternalAACFrame = encodeInternalAACFrame(i2);
        this.mEncOutBuffer.rewind();
        if (iEncodeInternalAACFrame <= 0) {
            return 0;
        }
        this.mEncOutBuffer.put(this.mTempBufEncOut, 0, iEncodeInternalAACFrame);
        if (mDumpEnable && this.mRecFileOut != null) {
            int i3 = iEncodeInternalAACFrame + 7;
            try {
                byte[] bArr = new byte[i3];
                addADTStoPacket(bArr, i3);
                System.arraycopy(this.mTempBufEncOut, 0, bArr, 7, iEncodeInternalAACFrame);
                this.mRecFileOut.write(bArr, 0, i3);
            } catch (IOException unused) {
                if (QLog.isColorLevel()) {
                    QLog.e(TAG, 2, "write file failed.");
                }
            }
        }
        return iEncodeInternalAACFrame;
    }

    @SuppressLint({"NewApi"})
    public int encodeInternalAACFrame(int i2) throws MediaCodec.CryptoException {
        try {
            int iDequeueInputBuffer = this.mAudioAACEncoder.dequeueInputBuffer(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            if (iDequeueInputBuffer != -1) {
                if (TXCBuild.VersionInt() >= 21) {
                    this.mInputBuffer = this.mAudioAACEncoder.getInputBuffer(iDequeueInputBuffer);
                } else {
                    ByteBuffer[] inputBuffers = this.mAudioAACEncoder.getInputBuffers();
                    this.mMediaInputBuffers = inputBuffers;
                    this.mInputBuffer = inputBuffers[iDequeueInputBuffer];
                }
                this.mInputBuffer.clear();
                this.mInputBuffer.put(this.mTempBufEncIn, 0, i2);
                this.mAudioAACEncoder.queueInputBuffer(iDequeueInputBuffer, 0, i2, 0L, 0);
                this.mEncInBuffer.rewind();
            }
            int iDequeueOutputBuffer = this.mAudioAACEncoder.dequeueOutputBuffer(this.mAACEncBufferInfo, 0L);
            if (iDequeueOutputBuffer < 0) {
                return 0;
            }
            int i3 = this.mAACEncBufferInfo.size;
            if (TXCBuild.VersionInt() >= 21) {
                this.mOutputBuffer = this.mAudioAACEncoder.getOutputBuffer(iDequeueOutputBuffer);
            } else {
                ByteBuffer[] outputBuffers = this.mAudioAACEncoder.getOutputBuffers();
                this.mMediaOutputBuffers = outputBuffers;
                this.mOutputBuffer = outputBuffers[iDequeueOutputBuffer];
            }
            MediaCodec.BufferInfo bufferInfo = this.mAACEncBufferInfo;
            int i4 = (bufferInfo.flags & 2) == 2 ? 0 : bufferInfo.size;
            try {
                this.mOutputBuffer.position(bufferInfo.offset);
                this.mOutputBuffer.limit(this.mAACEncBufferInfo.offset + i3);
                this.mOutputBuffer.get(this.mTempBufEncOut, 0, i4);
                this.mOutputBuffer.position(0);
                this.mAudioAACEncoder.releaseOutputBuffer(iDequeueOutputBuffer, false);
                return i4;
            } catch (Exception unused) {
                if (QLog.isColorLevel()) {
                    QLog.e(TAG, 2, "[ERROR] encoding aac stream failed!!!");
                }
                return i4;
            }
        } catch (Exception e2) {
            if (!QLog.isColorLevel()) {
                return 0;
            }
            QLog.e(TAG, 2, "encode failed." + e2.getMessage());
            return 0;
        }
    }

    @SuppressLint({"NewApi"})
    public int releaseAACEncoder() {
        try {
            MediaCodec mediaCodec = this.mAudioAACEncoder;
            if (mediaCodec != null) {
                mediaCodec.stop();
                this.mAudioAACEncoder.release();
                this.mAudioAACEncoder = null;
                if (!QLog.isColorLevel()) {
                    return 0;
                }
                QLog.w(TAG, 2, "releaseAACEncoder, release aac encode stream succeed!!");
                return 0;
            }
        } catch (Exception e2) {
            if (QLog.isColorLevel()) {
                QLog.e(TAG, 2, "release aac encoder failed." + e2.getMessage());
            }
        }
        if (!QLog.isColorLevel()) {
            return -1;
        }
        QLog.e(TAG, 2, "[ERROR] releaseAACEncoder, release aac encode stream failed!!!");
        return -1;
    }

    @SuppressLint({"NewApi"})
    public int setAACEncodeBitrate(int i2) {
        if (this.mAudioAACEncoder == null || this.mBitrate == i2) {
            return 0;
        }
        this.mFormatChangeFlag = true;
        this.mBitrate = i2;
        if (!QLog.isColorLevel()) {
            return 0;
        }
        QLog.w(TAG, 2, "Set AAC bitrate = " + i2);
        return 0;
    }

    public MediaCodecEncoder(Context context) {
        this.mAudioAACEncoder = null;
        this.mAudioFormat = null;
        this.mAACEncBufferInfo = null;
        this.mInputBuffer = null;
        this.mOutputBuffer = null;
        this.mSampleRate = 48000;
        this.mChannels = 1;
        this.mBitrate = 32000;
        this.nMaxBitRate = AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND;
        this.mFormatChangeFlag = false;
        this.mRecFileDump = null;
        this.mRecFileOut = null;
        this.mContext = context;
        this.mEncInBuffer = ByteBuffer.allocateDirect(R2.dimen.dp_m_8);
        this.mTempBufEncIn = new byte[R2.dimen.dp_m_8];
        this.mEncOutBuffer = ByteBuffer.allocateDirect((((this.nMaxBitRate * 2) / 8) / 50) + 100);
        this.mTempBufEncOut = new byte[(((this.nMaxBitRate * 2) / 8) / 50) + 100];
    }
}
