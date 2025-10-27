package org.wrtca.video;

import com.google.android.exoplayer2.util.MimeTypes;

/* loaded from: classes9.dex */
public enum VideoCodecType {
    VP8(MimeTypes.VIDEO_VP8),
    VP9(MimeTypes.VIDEO_VP9),
    H264(MimeTypes.VIDEO_H264);

    private final String mimeType;

    VideoCodecType(String str) {
        this.mimeType = str;
    }

    public String mimeType() {
        return this.mimeType;
    }
}
