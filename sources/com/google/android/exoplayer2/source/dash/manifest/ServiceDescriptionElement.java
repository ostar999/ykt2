package com.google.android.exoplayer2.source.dash.manifest;

/* loaded from: classes3.dex */
public final class ServiceDescriptionElement {
    public final long maxOffsetMs;
    public final float maxPlaybackSpeed;
    public final long minOffsetMs;
    public final float minPlaybackSpeed;
    public final long targetOffsetMs;

    public ServiceDescriptionElement(long j2, long j3, long j4, float f2, float f3) {
        this.targetOffsetMs = j2;
        this.minOffsetMs = j3;
        this.maxOffsetMs = j4;
        this.minPlaybackSpeed = f2;
        this.maxPlaybackSpeed = f3;
    }
}
