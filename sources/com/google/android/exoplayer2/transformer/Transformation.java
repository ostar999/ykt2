package com.google.android.exoplayer2.transformer;

import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
final class Transformation {

    @Nullable
    public final String audioMimeType;
    public final boolean flattenForSlowMotion;
    public final String outputMimeType;
    public final boolean removeAudio;
    public final boolean removeVideo;

    @Nullable
    public final String videoMimeType;

    public Transformation(boolean z2, boolean z3, boolean z4, String str, @Nullable String str2, @Nullable String str3) {
        this.removeAudio = z2;
        this.removeVideo = z3;
        this.flattenForSlowMotion = z4;
        this.outputMimeType = str;
        this.audioMimeType = str2;
        this.videoMimeType = str3;
    }
}
