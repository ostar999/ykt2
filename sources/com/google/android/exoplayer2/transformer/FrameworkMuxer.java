package com.google.android.exoplayer2.transformer;

import android.annotation.SuppressLint;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.ParcelFileDescriptor;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.transformer.Muxer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MediaFormatUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

@RequiresApi(18)
/* loaded from: classes3.dex */
final class FrameworkMuxer implements Muxer {
    private final MediaCodec.BufferInfo bufferInfo;
    private boolean isStarted;
    private final MediaMuxer mediaMuxer;

    /* JADX INFO: Access modifiers changed from: private */
    public static int mimeTypeToMuxerOutputFormat(String str) {
        if (str.equals("video/mp4")) {
            return 0;
        }
        if (Util.SDK_INT < 21 || !str.equals("video/webm")) {
            throw new IllegalArgumentException(str.length() != 0 ? "Unsupported output MIME type: ".concat(str) : new String("Unsupported output MIME type: "));
        }
        return 1;
    }

    @Override // com.google.android.exoplayer2.transformer.Muxer
    public int addTrack(Format format) {
        MediaFormat mediaFormatCreateVideoFormat;
        String str = (String) Assertions.checkNotNull(format.sampleMimeType);
        if (MimeTypes.isAudio(str)) {
            mediaFormatCreateVideoFormat = MediaFormat.createAudioFormat((String) Util.castNonNull(str), format.sampleRate, format.channelCount);
        } else {
            mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat((String) Util.castNonNull(str), format.width, format.height);
            this.mediaMuxer.setOrientationHint(format.rotationDegrees);
        }
        MediaFormatUtil.setCsdBuffers(mediaFormatCreateVideoFormat, format.initializationData);
        return this.mediaMuxer.addTrack(mediaFormatCreateVideoFormat);
    }

    @Override // com.google.android.exoplayer2.transformer.Muxer
    public void release(boolean z2) {
        if (this.isStarted) {
            this.isStarted = false;
            try {
                try {
                    this.mediaMuxer.stop();
                } catch (IllegalStateException e2) {
                    if (Util.SDK_INT < 30) {
                        try {
                            Field declaredField = MediaMuxer.class.getDeclaredField("MUXER_STATE_STOPPED");
                            declaredField.setAccessible(true);
                            int iIntValue = ((Integer) Util.castNonNull((Integer) declaredField.get(this.mediaMuxer))).intValue();
                            Field declaredField2 = MediaMuxer.class.getDeclaredField("mState");
                            declaredField2.setAccessible(true);
                            declaredField2.set(this.mediaMuxer, Integer.valueOf(iIntValue));
                        } catch (Exception unused) {
                        }
                    }
                    if (!z2) {
                        throw e2;
                    }
                }
            } finally {
                this.mediaMuxer.release();
            }
        }
    }

    @Override // com.google.android.exoplayer2.transformer.Muxer
    @SuppressLint({"WrongConstant"})
    public void writeSampleData(int i2, ByteBuffer byteBuffer, boolean z2, long j2) {
        if (!this.isStarted) {
            this.isStarted = true;
            this.mediaMuxer.start();
        }
        int iPosition = byteBuffer.position();
        this.bufferInfo.set(iPosition, byteBuffer.limit() - iPosition, j2, z2 ? 1 : 0);
        this.mediaMuxer.writeSampleData(i2, byteBuffer, this.bufferInfo);
    }

    public static final class Factory implements Muxer.Factory {
        @Override // com.google.android.exoplayer2.transformer.Muxer.Factory
        public boolean supportsOutputMimeType(String str) {
            try {
                FrameworkMuxer.mimeTypeToMuxerOutputFormat(str);
                return true;
            } catch (IllegalStateException unused) {
                return false;
            }
        }

        @Override // com.google.android.exoplayer2.transformer.Muxer.Factory
        public boolean supportsSampleMimeType(@Nullable String str, String str2) {
            int i2;
            boolean zIsAudio = MimeTypes.isAudio(str);
            boolean zIsVideo = MimeTypes.isVideo(str);
            if (str2.equals("video/mp4")) {
                if (zIsVideo) {
                    if ("video/3gpp".equals(str) || MimeTypes.VIDEO_H264.equals(str) || MimeTypes.VIDEO_MP4V.equals(str)) {
                        return true;
                    }
                    return Util.SDK_INT >= 24 && MimeTypes.VIDEO_H265.equals(str);
                }
                if (zIsAudio) {
                    return MimeTypes.AUDIO_AAC.equals(str) || MimeTypes.AUDIO_AMR_NB.equals(str) || MimeTypes.AUDIO_AMR_WB.equals(str);
                }
            } else if (str2.equals("video/webm") && (i2 = Util.SDK_INT) >= 21) {
                if (zIsVideo) {
                    if (MimeTypes.VIDEO_VP8.equals(str)) {
                        return true;
                    }
                    return i2 >= 24 && MimeTypes.VIDEO_VP9.equals(str);
                }
                if (zIsAudio) {
                    return MimeTypes.AUDIO_VORBIS.equals(str);
                }
            }
            return false;
        }

        @Override // com.google.android.exoplayer2.transformer.Muxer.Factory
        public FrameworkMuxer create(String str, String str2) throws IOException {
            return new FrameworkMuxer(new MediaMuxer(str, FrameworkMuxer.mimeTypeToMuxerOutputFormat(str2)));
        }

        @Override // com.google.android.exoplayer2.transformer.Muxer.Factory
        @RequiresApi(26)
        public FrameworkMuxer create(ParcelFileDescriptor parcelFileDescriptor, String str) throws IOException {
            return new FrameworkMuxer(new MediaMuxer(parcelFileDescriptor.getFileDescriptor(), FrameworkMuxer.mimeTypeToMuxerOutputFormat(str)));
        }
    }

    private FrameworkMuxer(MediaMuxer mediaMuxer) {
        this.mediaMuxer = mediaMuxer;
        this.bufferInfo = new MediaCodec.BufferInfo();
    }
}
