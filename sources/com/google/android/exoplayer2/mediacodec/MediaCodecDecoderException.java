package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.decoder.DecoderException;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes3.dex */
public class MediaCodecDecoderException extends DecoderException {

    @Nullable
    public final MediaCodecInfo codecInfo;

    @Nullable
    public final String diagnosticInfo;

    /* JADX WARN: Illegal instructions before constructor call */
    public MediaCodecDecoderException(Throwable th, @Nullable MediaCodecInfo mediaCodecInfo) {
        String strValueOf = String.valueOf(mediaCodecInfo == null ? null : mediaCodecInfo.name);
        super(strValueOf.length() != 0 ? "Decoder failed: ".concat(strValueOf) : new String("Decoder failed: "), th);
        this.codecInfo = mediaCodecInfo;
        this.diagnosticInfo = Util.SDK_INT >= 21 ? getDiagnosticInfoV21(th) : null;
    }

    @Nullable
    @RequiresApi(21)
    private static String getDiagnosticInfoV21(Throwable th) {
        if (th instanceof MediaCodec.CodecException) {
            return ((MediaCodec.CodecException) th).getDiagnosticInfo();
        }
        return null;
    }
}
