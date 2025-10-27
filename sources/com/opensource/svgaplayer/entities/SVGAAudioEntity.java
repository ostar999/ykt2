package com.opensource.svgaplayer.entities;

import com.opensource.svgaplayer.BuildConfig;
import com.opensource.svgaplayer.proto.AudioEntity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0012\b\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001e\u0010\r\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0013\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\b\u0014\u0010\u000f\"\u0004\b\u0015\u0010\u0011R\u0011\u0010\u0016\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\fR\u0011\u0010\u0018\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\fR\u0011\u0010\u001a\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\f¨\u0006\u001c"}, d2 = {"Lcom/opensource/svgaplayer/entities/SVGAAudioEntity;", "", "audioItem", "Lcom/opensource/svgaplayer/proto/AudioEntity;", "(Lcom/opensource/svgaplayer/proto/AudioEntity;)V", "audioKey", "", "getAudioKey", "()Ljava/lang/String;", "endFrame", "", "getEndFrame", "()I", "playID", "getPlayID", "()Ljava/lang/Integer;", "setPlayID", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "soundID", "getSoundID", "setSoundID", "startFrame", "getStartFrame", "startTime", "getStartTime", "totalTime", "getTotalTime", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGAAudioEntity {

    @Nullable
    private final String audioKey;
    private final int endFrame;

    @Nullable
    private Integer playID;

    @Nullable
    private Integer soundID;
    private final int startFrame;
    private final int startTime;
    private final int totalTime;

    public SVGAAudioEntity(@NotNull AudioEntity audioItem) {
        Intrinsics.checkParameterIsNotNull(audioItem, "audioItem");
        this.audioKey = audioItem.audioKey;
        Integer num = audioItem.startFrame;
        this.startFrame = num != null ? num.intValue() : 0;
        Integer num2 = audioItem.endFrame;
        this.endFrame = num2 != null ? num2.intValue() : 0;
        Integer num3 = audioItem.startTime;
        this.startTime = num3 != null ? num3.intValue() : 0;
        Integer num4 = audioItem.totalTime;
        this.totalTime = num4 != null ? num4.intValue() : 0;
    }

    @Nullable
    public final String getAudioKey() {
        return this.audioKey;
    }

    public final int getEndFrame() {
        return this.endFrame;
    }

    @Nullable
    public final Integer getPlayID() {
        return this.playID;
    }

    @Nullable
    public final Integer getSoundID() {
        return this.soundID;
    }

    public final int getStartFrame() {
        return this.startFrame;
    }

    public final int getStartTime() {
        return this.startTime;
    }

    public final int getTotalTime() {
        return this.totalTime;
    }

    public final void setPlayID(@Nullable Integer num) {
        this.playID = num;
    }

    public final void setSoundID(@Nullable Integer num) {
        this.soundID = num;
    }
}
