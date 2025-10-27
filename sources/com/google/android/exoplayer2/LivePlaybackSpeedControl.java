package com.google.android.exoplayer2;

import com.google.android.exoplayer2.MediaItem;

/* loaded from: classes3.dex */
public interface LivePlaybackSpeedControl {
    float getAdjustedPlaybackSpeed(long j2, long j3);

    long getTargetLiveOffsetUs();

    void notifyRebuffer();

    void setLiveConfiguration(MediaItem.LiveConfiguration liveConfiguration);

    void setTargetLiveOffsetOverrideUs(long j2);
}
