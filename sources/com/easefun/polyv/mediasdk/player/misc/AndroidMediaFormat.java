package com.easefun.polyv.mediasdk.player.misc;

import android.annotation.TargetApi;
import android.media.MediaFormat;

/* loaded from: classes3.dex */
public class AndroidMediaFormat implements IMediaFormat {
    private final MediaFormat mMediaFormat;

    public AndroidMediaFormat(MediaFormat mediaFormat) {
        this.mMediaFormat = mediaFormat;
    }

    @Override // com.easefun.polyv.mediasdk.player.misc.IMediaFormat
    @TargetApi(16)
    public int getInteger(String str) {
        MediaFormat mediaFormat = this.mMediaFormat;
        if (mediaFormat == null) {
            return 0;
        }
        return mediaFormat.getInteger(str);
    }

    @Override // com.easefun.polyv.mediasdk.player.misc.IMediaFormat
    @TargetApi(16)
    public String getString(String str) {
        MediaFormat mediaFormat = this.mMediaFormat;
        if (mediaFormat == null) {
            return null;
        }
        return mediaFormat.getString(str);
    }

    @TargetApi(16)
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(AndroidMediaFormat.class.getName());
        sb.append('{');
        MediaFormat mediaFormat = this.mMediaFormat;
        if (mediaFormat != null) {
            sb.append(mediaFormat.toString());
        } else {
            sb.append("null");
        }
        sb.append('}');
        return sb.toString();
    }
}
