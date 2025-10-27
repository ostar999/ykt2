package io.agora.rtc.audio;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.view.Surface;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.util.MimeTypes;
import com.yikaobang.yixue.R2;
import io.agora.rtc.internal.Logging;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes8.dex */
public class MediaCodecAudioEncoder {
    private ByteBuffer[] mAACInputBuffers;
    private ByteBuffer[] mAACOutputBuffers;
    private ByteBuffer[] mInputBuffers;
    private ByteBuffer[] mOutputBuffers;
    private MediaCodec mMediaCodec = null;
    private MediaFormat mTrackFormat = null;
    private String mCodecString = null;
    private File outputFile = null;
    private BufferedOutputStream outputStream = null;
    private MediaCodec mAACEncoder = null;
    private MediaFormat mAACFormat = null;
    private ByteBuffer mAACEncodedBuffer = ByteBuffer.allocateDirect(1024);
    private String TAG = "MediaCodec Audio Encoder";

    private void addADTStoPacket(byte[] packet, int packetLen) {
        packet[0] = -1;
        packet[1] = -7;
        packet[2] = (byte) 84;
        packet[3] = (byte) (64 + (packetLen >> 11));
        packet[4] = (byte) ((packetLen & R2.attr.indicatorSize) >> 3);
        packet[5] = (byte) (((packetLen & 7) << 5) + 31);
        packet[6] = -4;
    }

    private void touch(File f2) throws IOException {
        try {
            if (f2.exists()) {
                return;
            }
            f2.createNewFile();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public boolean createAACStreaming(int sampleRate, int channels, int encodeRate) {
        try {
            Logging.i(this.TAG, "Encoding aac with fs = " + sampleRate + ", bitrate = " + encodeRate);
            this.mAACEncoder = MediaCodec.createEncoderByType(MimeTypes.AUDIO_AAC);
            MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat(MimeTypes.AUDIO_AAC, sampleRate, channels);
            this.mAACFormat = mediaFormatCreateAudioFormat;
            mediaFormatCreateAudioFormat.setInteger("aac-profile", 2);
            this.mAACFormat.setInteger("sample-rate", sampleRate);
            this.mAACFormat.setInteger("channel-count", channels);
            this.mAACFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, encodeRate);
            this.mAACEncoder.configure(this.mAACFormat, (Surface) null, (MediaCrypto) null, 1);
            MediaCodec mediaCodec = this.mAACEncoder;
            if (mediaCodec != null) {
                mediaCodec.start();
            }
            return true;
        } catch (Exception e2) {
            Logging.e(this.TAG, "Error when creating aac encode stream");
            e2.printStackTrace();
            return false;
        }
    }

    public boolean createStreaming(String filename, int sampleRate, int channels, int quality) throws IOException {
        try {
            Logging.i(this.TAG, "Recording aac with fs = " + sampleRate + ", ch = " + channels + ", quality = " + quality);
            String strSubstring = filename.substring(filename.length() - 3);
            if (strSubstring.equalsIgnoreCase("3gp") || strSubstring.equalsIgnoreCase("amr")) {
                if (sampleRate == 8000) {
                    this.mMediaCodec = MediaCodec.createEncoderByType(MimeTypes.AUDIO_AMR_NB);
                    MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat(MimeTypes.AUDIO_AMR_NB, sampleRate, channels);
                    this.mTrackFormat = mediaFormatCreateAudioFormat;
                    mediaFormatCreateAudioFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, R2.drawable.selector_icon_my_edit_clear);
                    this.mCodecString = MimeTypes.AUDIO_AMR_NB;
                } else if (sampleRate == 16000) {
                    this.mMediaCodec = MediaCodec.createEncoderByType(MimeTypes.AUDIO_AMR_WB);
                    MediaFormat mediaFormatCreateAudioFormat2 = MediaFormat.createAudioFormat(MimeTypes.AUDIO_AMR_WB, sampleRate, channels);
                    this.mTrackFormat = mediaFormatCreateAudioFormat2;
                    mediaFormatCreateAudioFormat2.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, R2.string.heartlung_101);
                    this.mCodecString = MimeTypes.AUDIO_AMR_WB;
                }
            } else {
                if (!strSubstring.equalsIgnoreCase("aac")) {
                    return false;
                }
                int i2 = quality != 0 ? quality != 1 ? 50000 : 25000 : 16000;
                this.mMediaCodec = MediaCodec.createEncoderByType(MimeTypes.AUDIO_AAC);
                MediaFormat mediaFormatCreateAudioFormat3 = MediaFormat.createAudioFormat(MimeTypes.AUDIO_AAC, sampleRate, channels);
                this.mTrackFormat = mediaFormatCreateAudioFormat3;
                mediaFormatCreateAudioFormat3.setInteger("aac-profile", 2);
                this.mTrackFormat.setInteger("sample-rate", sampleRate);
                this.mTrackFormat.setInteger("channel-count", channels);
                this.mTrackFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, i2);
                this.mCodecString = MimeTypes.AUDIO_AAC;
            }
            this.mMediaCodec.configure(this.mTrackFormat, (Surface) null, (MediaCrypto) null, 1);
            MediaCodec mediaCodec = this.mMediaCodec;
            if (mediaCodec != null) {
                mediaCodec.start();
            }
            File file = new File(filename);
            this.outputFile = file;
            touch(file);
            try {
                this.outputStream = new BufferedOutputStream(new FileOutputStream(this.outputFile));
                Logging.i(this.TAG, "outputStream initialized");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            String str = this.mCodecString;
            if (str == MimeTypes.AUDIO_AMR_NB) {
                this.outputStream.write(new byte[]{35, 33, 65, 77, 82, 10});
            } else if (str == MimeTypes.AUDIO_AMR_WB) {
                this.outputStream.write(new byte[]{35, 33, 65, 77, 82, 45, 87, 66, 10});
            }
            return true;
        } catch (Exception e3) {
            Logging.e(this.TAG, "Error when creating aac file encoder");
            e3.printStackTrace();
            return false;
        }
    }

    public int encodeAACFrame(byte[] data) throws MediaCodec.CryptoException {
        int i2 = 0;
        try {
            int iDequeueInputBuffer = this.mAACEncoder.dequeueInputBuffer(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            if (iDequeueInputBuffer != -1) {
                ByteBuffer inputBuffer = this.mAACEncoder.getInputBuffer(iDequeueInputBuffer);
                inputBuffer.clear();
                inputBuffer.put(data);
                this.mAACEncoder.queueInputBuffer(iDequeueInputBuffer, 0, data.length, 0L, 0);
            }
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int iDequeueOutputBuffer = this.mAACEncoder.dequeueOutputBuffer(bufferInfo, 0L);
            if (iDequeueOutputBuffer < 0) {
                return 0;
            }
            int i3 = bufferInfo.size;
            ByteBuffer outputBuffer = this.mAACEncoder.getOutputBuffer(iDequeueOutputBuffer);
            int i4 = (bufferInfo.flags & 2) == 2 ? 0 : bufferInfo.size;
            try {
                outputBuffer.position(bufferInfo.offset);
                outputBuffer.limit(bufferInfo.offset + i3);
                this.mAACEncodedBuffer.position(0);
                this.mAACEncodedBuffer.put(outputBuffer);
                this.mAACEncoder.releaseOutputBuffer(iDequeueOutputBuffer, false);
                return i4;
            } catch (Exception e2) {
                e = e2;
                i2 = i4;
                Logging.e(this.TAG, "Error when encoding aac stream");
                e.printStackTrace();
                return i2;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public void encodeFrame(byte[] data) throws MediaCodec.CryptoException, IOException {
        try {
            int iDequeueInputBuffer = this.mMediaCodec.dequeueInputBuffer(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            if (iDequeueInputBuffer != -1) {
                ByteBuffer inputBuffer = this.mMediaCodec.getInputBuffer(iDequeueInputBuffer);
                inputBuffer.clear();
                inputBuffer.put(data);
                this.mMediaCodec.queueInputBuffer(iDequeueInputBuffer, 0, data.length, 0L, 0);
            }
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int iDequeueOutputBuffer = this.mMediaCodec.dequeueOutputBuffer(bufferInfo, 0L);
            while (iDequeueOutputBuffer >= 0) {
                int i2 = bufferInfo.size;
                ByteBuffer outputBuffer = this.mMediaCodec.getOutputBuffer(iDequeueOutputBuffer);
                outputBuffer.position(bufferInfo.offset);
                outputBuffer.limit(bufferInfo.offset + i2);
                String str = this.mCodecString;
                if (str == MimeTypes.AUDIO_AAC) {
                    int i3 = i2 + 7;
                    byte[] bArr = new byte[i3];
                    addADTStoPacket(bArr, i3);
                    outputBuffer.get(bArr, 7, i2);
                    outputBuffer.position(bufferInfo.offset);
                    this.outputStream.write(bArr, 0, i3);
                } else if (str == MimeTypes.AUDIO_AMR_NB || str == MimeTypes.AUDIO_AMR_WB) {
                    byte[] bArr2 = new byte[i2];
                    outputBuffer.get(bArr2, 0, i2);
                    outputBuffer.position(bufferInfo.offset);
                    this.outputStream.write(bArr2, 0, i2);
                }
                this.mMediaCodec.releaseOutputBuffer(iDequeueOutputBuffer, false);
                iDequeueOutputBuffer = this.mMediaCodec.dequeueOutputBuffer(bufferInfo, 0L);
            }
        } catch (Exception e2) {
            Logging.e(this.TAG, "Error when encoding aac file");
            e2.printStackTrace();
        }
    }

    public void releaseAACStreaming() {
        try {
            MediaCodec mediaCodec = this.mAACEncoder;
            if (mediaCodec != null) {
                mediaCodec.stop();
                this.mAACEncoder.release();
                this.mAACEncoder = null;
            }
        } catch (Exception e2) {
            Logging.e(this.TAG, "Error when releasing aac encode stream");
            e2.printStackTrace();
        }
    }

    public void releaseStreaming() throws IOException {
        try {
            MediaCodec mediaCodec = this.mMediaCodec;
            if (mediaCodec != null) {
                mediaCodec.stop();
                this.mMediaCodec.release();
                this.mMediaCodec = null;
            }
            BufferedOutputStream bufferedOutputStream = this.outputStream;
            if (bufferedOutputStream != null) {
                bufferedOutputStream.flush();
                this.outputStream.close();
                this.outputStream = null;
            }
        } catch (Exception e2) {
            Logging.e(this.TAG, "Error when releasing aac file encoder");
            e2.printStackTrace();
        }
    }

    public boolean setAACEncodeBitrate(int bitrate) {
        Logging.w(this.TAG, "Set hw aac bitrate = " + bitrate);
        try {
            MediaCodec mediaCodec = this.mAACEncoder;
            if (mediaCodec != null) {
                mediaCodec.stop();
                this.mAACFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, bitrate);
                this.mAACEncoder.configure(this.mAACFormat, (Surface) null, (MediaCrypto) null, 1);
                this.mAACEncoder.start();
            }
            return true;
        } catch (Exception e2) {
            Logging.e(this.TAG, "Error when setting aac encode bitrate");
            e2.printStackTrace();
            return false;
        }
    }

    public void setChannelCount(int channels) {
        try {
            this.mTrackFormat.setInteger("channel-count", channels);
        } catch (Exception e2) {
            Logging.e(this.TAG, "Error when setting aac file encoder channel count");
            e2.printStackTrace();
        }
    }

    public void setSampleRate(int sample_rate) {
        try {
            this.mTrackFormat.setInteger("sample-rate", sample_rate);
        } catch (Exception e2) {
            Logging.e(this.TAG, "Error when setting aac file encoder sample rate");
            e2.printStackTrace();
        }
    }
}
