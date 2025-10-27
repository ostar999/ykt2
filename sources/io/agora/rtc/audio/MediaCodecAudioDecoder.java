package io.agora.rtc.audio;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.view.Surface;
import com.easefun.polyv.mediasdk.player.misc.IMediaFormat;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.common.base.Ascii;
import io.agora.rtc.internal.Logging;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.ByteBuffer;

/* loaded from: classes8.dex */
public class MediaCodecAudioDecoder {
    private static int HTTP_REQUEST_TIMEOUT = 3000;
    private static int MAX_DECODER_RETRY_COUNT = 300;
    private ByteBuffer mDecodedRAWBuffer;
    private long mFileLength;
    private ByteBuffer[] mInputBuffers;
    private ByteBuffer[] mOutputBuffers;
    private Context mContext = null;
    private MediaCodec mMediaCodec = null;
    private MediaExtractor mExtractor = null;
    private MediaFormat mTrackFormat = null;
    private boolean mDecodedDataReady = false;
    private boolean eoInputStream = false;
    private boolean eoOutputStream = false;
    private int mSampleRate = 44100;
    private int mChannels = 2;
    private int mRetryCount = 0;
    private MediaCodec mAACDecoder = null;
    private ByteBuffer mAACOutputBuffer = ByteBuffer.allocateDirect(4096);
    private String TAG = "MediaCodec Audio Decoder";

    private boolean checkInfoChange() {
        try {
            MediaFormat outputFormat = this.mMediaCodec.getOutputFormat();
            int integer = outputFormat.getInteger("sample-rate");
            int integer2 = outputFormat.getInteger("channel-count");
            boolean z2 = (this.mSampleRate == integer && this.mChannels == integer2) ? false : true;
            this.mSampleRate = integer;
            this.mChannels = integer2;
            return z2;
        } catch (Exception e2) {
            Logging.e(this.TAG, "Error when checking file's new format");
            e2.printStackTrace();
            return false;
        }
    }

    private void cloneByteBuffer(final ByteBuffer original) {
        try {
            ByteBuffer byteBuffer = this.mDecodedRAWBuffer;
            if (byteBuffer == null || byteBuffer.limit() != original.limit()) {
                ByteBuffer byteBuffer2 = this.mDecodedRAWBuffer;
                if (byteBuffer2 != null) {
                    byteBuffer2.clear();
                    this.mDecodedRAWBuffer = null;
                }
                this.mDecodedRAWBuffer = ByteBuffer.allocateDirect(original.limit());
            }
            this.mDecodedRAWBuffer.position(0);
            this.mDecodedRAWBuffer.put(original);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void cloneByteBufferByLength(final ByteBuffer original, int length) {
        try {
            ByteBuffer byteBuffer = this.mDecodedRAWBuffer;
            if (byteBuffer == null || byteBuffer.capacity() < length) {
                ByteBuffer byteBuffer2 = this.mDecodedRAWBuffer;
                if (byteBuffer2 != null) {
                    byteBuffer2.clear();
                    this.mDecodedRAWBuffer = null;
                }
                this.mDecodedRAWBuffer = ByteBuffer.allocateDirect(length);
            }
            this.mDecodedRAWBuffer.position(0);
            original.limit(length);
            this.mDecodedRAWBuffer.put(original);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public boolean checkAACSupported() {
        try {
            for (MediaCodecInfo mediaCodecInfo : new MediaCodecList(1).getCodecInfos()) {
                if (!mediaCodecInfo.isEncoder() && mediaCodecInfo.getName().toLowerCase().contains("nvidia")) {
                    return false;
                }
            }
            return true;
        } catch (Exception e2) {
            Logging.e(this.TAG, "Error when checking aac codec availability");
            e2.printStackTrace();
            return false;
        }
    }

    public boolean createAACStreaming(int sample_rate) {
        try {
            this.mAACDecoder = MediaCodec.createDecoderByType(MimeTypes.AUDIO_AAC);
            MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat(MimeTypes.AUDIO_AAC, sample_rate, 1);
            mediaFormatCreateAudioFormat.setInteger("sample-rate", sample_rate);
            mediaFormatCreateAudioFormat.setInteger("channel-count", 1);
            mediaFormatCreateAudioFormat.setByteBuffer("csd-0", ByteBuffer.wrap(new byte[]{Ascii.DC2, -120}));
            this.mAACDecoder.configure(mediaFormatCreateAudioFormat, (Surface) null, (MediaCrypto) null, 0);
            MediaCodec mediaCodec = this.mAACDecoder;
            if (mediaCodec != null) {
                mediaCodec.start();
            }
            return true;
        } catch (Exception e2) {
            Logging.e(this.TAG, "Error when creating aac decode stream");
            e2.printStackTrace();
            return false;
        }
    }

    public boolean createStreaming(String filename) throws IOException {
        try {
            Logging.i(this.TAG, "Try to decode audio file : " + filename);
            this.mRetryCount = 0;
            boolean zStartsWith = filename.startsWith("/assets/");
            boolean zStartsWith2 = filename.toLowerCase().startsWith("http");
            MediaExtractor mediaExtractor = new MediaExtractor();
            this.mExtractor = mediaExtractor;
            if (zStartsWith) {
                Context context = this.mContext;
                if (context == null) {
                    return false;
                }
                AssetFileDescriptor assetFileDescriptorOpenFd = context.getAssets().openFd(filename.substring(8));
                this.mExtractor.setDataSource(assetFileDescriptorOpenFd.getFileDescriptor(), assetFileDescriptorOpenFd.getStartOffset(), assetFileDescriptorOpenFd.getLength());
            } else if (zStartsWith2) {
                try {
                    HttpURLConnection.setFollowRedirects(false);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(filename).openConnection();
                    httpURLConnection.setConnectTimeout(HTTP_REQUEST_TIMEOUT);
                    httpURLConnection.setReadTimeout(HTTP_REQUEST_TIMEOUT);
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() != 200) {
                        return false;
                    }
                    this.mExtractor.setDataSource(filename);
                } catch (SocketTimeoutException unused) {
                    Logging.e(this.TAG, "Connect timeout on URL : " + filename);
                    return false;
                } catch (IOException unused2) {
                    Logging.e(this.TAG, "Connect IOException on URL : " + filename);
                    return false;
                }
            } else {
                mediaExtractor.setDataSource(filename);
            }
            int trackCount = this.mExtractor.getTrackCount();
            for (int i2 = 0; i2 < trackCount; i2++) {
                this.mExtractor.unselectTrack(i2);
            }
            int i3 = 0;
            while (true) {
                if (i3 >= trackCount) {
                    break;
                }
                MediaFormat trackFormat = this.mExtractor.getTrackFormat(i3);
                this.mTrackFormat = trackFormat;
                String string = trackFormat.getString(IMediaFormat.KEY_MIME);
                if (string.contains("audio/")) {
                    this.mExtractor.selectTrack(i3);
                    MediaCodec mediaCodecCreateDecoderByType = MediaCodec.createDecoderByType(string);
                    this.mMediaCodec = mediaCodecCreateDecoderByType;
                    mediaCodecCreateDecoderByType.configure(this.mTrackFormat, (Surface) null, (MediaCrypto) null, 0);
                    break;
                }
                i3++;
            }
            MediaCodec mediaCodec = this.mMediaCodec;
            if (mediaCodec != null) {
                mediaCodec.start();
            }
            this.mChannels = this.mTrackFormat.getInteger("channel-count");
            this.mSampleRate = this.mTrackFormat.getInteger("sample-rate");
            this.mFileLength = this.mTrackFormat.getLong("durationUs");
            return true;
        } catch (Exception e2) {
            Logging.e(this.TAG, "Error when creating aac audio file decoder");
            e2.printStackTrace();
            return false;
        }
    }

    public int decodeAACFrame(byte[] encoded_data) throws MediaCodec.CryptoException {
        int i2 = 0;
        try {
            int iDequeueInputBuffer = this.mAACDecoder.dequeueInputBuffer(200L);
            if (iDequeueInputBuffer >= 0) {
                ByteBuffer inputBuffer = this.mAACDecoder.getInputBuffer(iDequeueInputBuffer);
                inputBuffer.clear();
                inputBuffer.put(encoded_data);
                this.mAACDecoder.queueInputBuffer(iDequeueInputBuffer, 0, encoded_data.length, 0L, 0);
            }
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int iDequeueOutputBuffer = this.mAACDecoder.dequeueOutputBuffer(bufferInfo, 0L);
            if (iDequeueOutputBuffer == -3 || iDequeueOutputBuffer == -2 || iDequeueOutputBuffer == -1 || iDequeueOutputBuffer < 0) {
                return 0;
            }
            ByteBuffer outputBuffer = this.mAACDecoder.getOutputBuffer(iDequeueOutputBuffer);
            int i3 = bufferInfo.size;
            try {
                this.mAACOutputBuffer.position(0);
                outputBuffer.limit(i3);
                this.mAACOutputBuffer.put(outputBuffer);
                this.mAACDecoder.releaseOutputBuffer(iDequeueOutputBuffer, false);
                return i3;
            } catch (Exception e2) {
                i2 = i3;
                e = e2;
                Logging.e(this.TAG, "Error when decoding aac stream");
                e.printStackTrace();
                return i2;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00b1 A[Catch: Exception -> 0x00e5, TRY_LEAVE, TryCatch #0 {Exception -> 0x00e5, blocks: (B:3:0x0004, B:5:0x000c, B:7:0x0014, B:9:0x0022, B:11:0x0027, B:13:0x0037, B:14:0x0039, B:15:0x0045, B:17:0x0049, B:23:0x005f, B:25:0x0063, B:27:0x0069, B:28:0x006b, B:29:0x007c, B:31:0x0085, B:33:0x0091, B:35:0x009d, B:37:0x00a7, B:39:0x00b1), top: B:45:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean decodeFrame() throws android.media.MediaCodec.CryptoException {
        /*
            Method dump skipped, instructions count: 243
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.agora.rtc.audio.MediaCodecAudioDecoder.decodeFrame():boolean");
    }

    public int getChannelCount() {
        return this.mChannels;
    }

    public long getCurrentFilePosition() {
        return this.mExtractor.getSampleTime();
    }

    public boolean getDecodeDataReadyFlag() {
        return this.mDecodedDataReady;
    }

    public long getFileLength() {
        return this.mFileLength;
    }

    public int getSampleRate() {
        return this.mSampleRate;
    }

    public void releaseAACStreaming() {
        try {
            MediaCodec mediaCodec = this.mAACDecoder;
            if (mediaCodec != null) {
                mediaCodec.stop();
                this.mAACDecoder.release();
                this.mAACDecoder = null;
            }
        } catch (Exception e2) {
            Logging.e(this.TAG, "Error when releasing aac decode stream");
            e2.printStackTrace();
        }
    }

    public void releaseStreaming() {
        try {
            MediaCodec mediaCodec = this.mMediaCodec;
            if (mediaCodec != null) {
                mediaCodec.stop();
                this.mMediaCodec.release();
                this.mMediaCodec = null;
            }
            MediaExtractor mediaExtractor = this.mExtractor;
            if (mediaExtractor != null) {
                mediaExtractor.release();
                this.mExtractor = null;
            }
        } catch (Exception e2) {
            Logging.e(this.TAG, "Error when releasing audio file stream");
            e2.printStackTrace();
        }
        this.eoOutputStream = false;
        this.eoInputStream = false;
    }

    public void rewindStreaming() {
        try {
            this.mExtractor.seekTo(0L, 1);
            this.mMediaCodec.flush();
        } catch (Exception e2) {
            Logging.e(this.TAG, "Error when rewinding audio file stream");
            e2.printStackTrace();
        }
        this.eoInputStream = false;
        this.eoOutputStream = false;
        this.mDecodedDataReady = false;
    }

    public void setCurrentFilePosition(long position) {
        this.mExtractor.seekTo(position, 2);
    }
}
