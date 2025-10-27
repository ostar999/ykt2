package androidx.camera.core.impl;

import android.media.CamcorderProfile;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.auto.value.AutoValue;

@AutoValue
@RequiresApi(21)
/* loaded from: classes.dex */
public abstract class CamcorderProfileProxy {
    public static int CODEC_PROFILE_NONE = -1;

    @NonNull
    public static CamcorderProfileProxy create(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13) {
        return new AutoValue_CamcorderProfileProxy(i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13);
    }

    @NonNull
    public static CamcorderProfileProxy fromCamcorderProfile(@NonNull CamcorderProfile camcorderProfile) {
        return new AutoValue_CamcorderProfileProxy(camcorderProfile.duration, camcorderProfile.quality, camcorderProfile.fileFormat, camcorderProfile.videoCodec, camcorderProfile.videoBitRate, camcorderProfile.videoFrameRate, camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight, camcorderProfile.audioCodec, camcorderProfile.audioBitRate, camcorderProfile.audioSampleRate, camcorderProfile.audioChannels);
    }

    public abstract int getAudioBitRate();

    public abstract int getAudioChannels();

    public abstract int getAudioCodec();

    @Nullable
    public String getAudioCodecMimeType() {
        switch (getAudioCodec()) {
            case 1:
                return MimeTypes.AUDIO_AMR_NB;
            case 2:
                return MimeTypes.AUDIO_AMR_WB;
            case 3:
            case 4:
            case 5:
                return MimeTypes.AUDIO_AAC;
            case 6:
                return MimeTypes.AUDIO_VORBIS;
            case 7:
                return MimeTypes.AUDIO_OPUS;
            default:
                return null;
        }
    }

    public abstract int getAudioSampleRate();

    public abstract int getDuration();

    public abstract int getFileFormat();

    public abstract int getQuality();

    public int getRequiredAudioProfile() {
        int audioCodec = getAudioCodec();
        if (audioCodec == 3) {
            return 2;
        }
        if (audioCodec == 4) {
            return 5;
        }
        if (audioCodec != 5) {
            return CODEC_PROFILE_NONE;
        }
        return 39;
    }

    public abstract int getVideoBitRate();

    public abstract int getVideoCodec();

    @Nullable
    public String getVideoCodecMimeType() {
        int videoCodec = getVideoCodec();
        if (videoCodec == 1) {
            return "video/3gpp";
        }
        if (videoCodec == 2) {
            return MimeTypes.VIDEO_H264;
        }
        if (videoCodec == 3) {
            return MimeTypes.VIDEO_MP4V;
        }
        if (videoCodec == 4) {
            return MimeTypes.VIDEO_VP8;
        }
        if (videoCodec != 5) {
            return null;
        }
        return MimeTypes.VIDEO_H265;
    }

    public abstract int getVideoFrameHeight();

    public abstract int getVideoFrameRate();

    public abstract int getVideoFrameWidth();
}
