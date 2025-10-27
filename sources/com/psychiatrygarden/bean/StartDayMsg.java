package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0015"}, d2 = {"Lcom/psychiatrygarden/bean/StartDayMsg;", "", "today_duration", "", "continue_days", "all_duration", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAll_duration", "()Ljava/lang/String;", "getContinue_days", "getToday_duration", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class StartDayMsg {

    @NotNull
    private final String all_duration;

    @NotNull
    private final String continue_days;

    @NotNull
    private final String today_duration;

    public StartDayMsg(@NotNull String today_duration, @NotNull String continue_days, @NotNull String all_duration) {
        Intrinsics.checkNotNullParameter(today_duration, "today_duration");
        Intrinsics.checkNotNullParameter(continue_days, "continue_days");
        Intrinsics.checkNotNullParameter(all_duration, "all_duration");
        this.today_duration = today_duration;
        this.continue_days = continue_days;
        this.all_duration = all_duration;
    }

    public static /* synthetic */ StartDayMsg copy$default(StartDayMsg startDayMsg, String str, String str2, String str3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = startDayMsg.today_duration;
        }
        if ((i2 & 2) != 0) {
            str2 = startDayMsg.continue_days;
        }
        if ((i2 & 4) != 0) {
            str3 = startDayMsg.all_duration;
        }
        return startDayMsg.copy(str, str2, str3);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getToday_duration() {
        return this.today_duration;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getContinue_days() {
        return this.continue_days;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getAll_duration() {
        return this.all_duration;
    }

    @NotNull
    public final StartDayMsg copy(@NotNull String today_duration, @NotNull String continue_days, @NotNull String all_duration) {
        Intrinsics.checkNotNullParameter(today_duration, "today_duration");
        Intrinsics.checkNotNullParameter(continue_days, "continue_days");
        Intrinsics.checkNotNullParameter(all_duration, "all_duration");
        return new StartDayMsg(today_duration, continue_days, all_duration);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StartDayMsg)) {
            return false;
        }
        StartDayMsg startDayMsg = (StartDayMsg) other;
        return Intrinsics.areEqual(this.today_duration, startDayMsg.today_duration) && Intrinsics.areEqual(this.continue_days, startDayMsg.continue_days) && Intrinsics.areEqual(this.all_duration, startDayMsg.all_duration);
    }

    @NotNull
    public final String getAll_duration() {
        return this.all_duration;
    }

    @NotNull
    public final String getContinue_days() {
        return this.continue_days;
    }

    @NotNull
    public final String getToday_duration() {
        return this.today_duration;
    }

    public int hashCode() {
        return (((this.today_duration.hashCode() * 31) + this.continue_days.hashCode()) * 31) + this.all_duration.hashCode();
    }

    @NotNull
    public String toString() {
        return "StartDayMsg(today_duration=" + this.today_duration + ", continue_days=" + this.continue_days + ", all_duration=" + this.all_duration + ')';
    }
}
