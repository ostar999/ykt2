package com.easefun.polyv.mediasdk.player.misc;

import android.text.TextUtils;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.android.exoplayer2.C;

/* loaded from: classes3.dex */
public class IjkTrackInfo implements ITrackInfo {
    private IjkMediaMeta.IjkStreamMeta mStreamMeta;
    private int mTrackType = 0;

    public IjkTrackInfo(IjkMediaMeta.IjkStreamMeta ijkStreamMeta) {
        this.mStreamMeta = ijkStreamMeta;
    }

    @Override // com.easefun.polyv.mediasdk.player.misc.ITrackInfo
    public IMediaFormat getFormat() {
        return new IjkMediaFormat(this.mStreamMeta);
    }

    @Override // com.easefun.polyv.mediasdk.player.misc.ITrackInfo
    public String getInfoInline() {
        StringBuilder sb = new StringBuilder(128);
        int i2 = this.mTrackType;
        if (i2 == 1) {
            sb.append("VIDEO");
            sb.append(", ");
            sb.append(this.mStreamMeta.getCodecShortNameInline());
            sb.append(", ");
            sb.append(this.mStreamMeta.getBitrateInline());
            sb.append(", ");
            sb.append(this.mStreamMeta.getResolutionInline());
        } else if (i2 == 2) {
            sb.append("AUDIO");
            sb.append(", ");
            sb.append(this.mStreamMeta.getCodecShortNameInline());
            sb.append(", ");
            sb.append(this.mStreamMeta.getBitrateInline());
            sb.append(", ");
            sb.append(this.mStreamMeta.getSampleRateInline());
        } else if (i2 == 3) {
            sb.append("TIMEDTEXT");
            sb.append(", ");
            sb.append(this.mStreamMeta.mLanguage);
        } else if (i2 != 4) {
            sb.append("UNKNOWN");
        } else {
            sb.append("SUBTITLE");
        }
        return sb.toString();
    }

    @Override // com.easefun.polyv.mediasdk.player.misc.ITrackInfo
    public String getLanguage() {
        IjkMediaMeta.IjkStreamMeta ijkStreamMeta = this.mStreamMeta;
        return (ijkStreamMeta == null || TextUtils.isEmpty(ijkStreamMeta.mLanguage)) ? C.LANGUAGE_UNDETERMINED : this.mStreamMeta.mLanguage;
    }

    @Override // com.easefun.polyv.mediasdk.player.misc.ITrackInfo
    public int getTrackType() {
        return this.mTrackType;
    }

    public void setMediaMeta(IjkMediaMeta.IjkStreamMeta ijkStreamMeta) {
        this.mStreamMeta = ijkStreamMeta;
    }

    public void setTrackType(int i2) {
        this.mTrackType = i2;
    }

    public String toString() {
        return IjkTrackInfo.class.getSimpleName() + '{' + getInfoInline() + "}";
    }
}
