package com.ykb.ebook.model;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import com.umeng.analytics.pro.d;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J;\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001J\t\u0010 \u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\fR\u001e\u0010\u0006\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\n\"\u0004\b\u0010\u0010\fR\u001e\u0010\u0007\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\n\"\u0004\b\u0012\u0010\fR\u001e\u0010\u0005\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\n\"\u0004\b\u0014\u0010\f¨\u0006!"}, d2 = {"Lcom/ykb/ebook/model/PerusalDuration;", "", "duration", "", "days", "startTime", "endTime", "seriesDay", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDays", "()Ljava/lang/String;", "setDays", "(Ljava/lang/String;)V", "getDuration", "setDuration", "getEndTime", "setEndTime", "getSeriesDay", "setSeriesDay", "getStartTime", "setStartTime", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class PerusalDuration {

    @NotNull
    private String days;

    @NotNull
    private String duration;

    @SerializedName(d.f22695q)
    @NotNull
    private String endTime;

    @SerializedName("series_day")
    @NotNull
    private String seriesDay;

    @SerializedName(d.f22694p)
    @NotNull
    private String startTime;

    public PerusalDuration(@NotNull String duration, @NotNull String days, @NotNull String startTime, @NotNull String endTime, @NotNull String seriesDay) {
        Intrinsics.checkNotNullParameter(duration, "duration");
        Intrinsics.checkNotNullParameter(days, "days");
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
        Intrinsics.checkNotNullParameter(seriesDay, "seriesDay");
        this.duration = duration;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seriesDay = seriesDay;
    }

    public static /* synthetic */ PerusalDuration copy$default(PerusalDuration perusalDuration, String str, String str2, String str3, String str4, String str5, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = perusalDuration.duration;
        }
        if ((i2 & 2) != 0) {
            str2 = perusalDuration.days;
        }
        String str6 = str2;
        if ((i2 & 4) != 0) {
            str3 = perusalDuration.startTime;
        }
        String str7 = str3;
        if ((i2 & 8) != 0) {
            str4 = perusalDuration.endTime;
        }
        String str8 = str4;
        if ((i2 & 16) != 0) {
            str5 = perusalDuration.seriesDay;
        }
        return perusalDuration.copy(str, str6, str7, str8, str5);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getDuration() {
        return this.duration;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getDays() {
        return this.days;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getStartTime() {
        return this.startTime;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getEndTime() {
        return this.endTime;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getSeriesDay() {
        return this.seriesDay;
    }

    @NotNull
    public final PerusalDuration copy(@NotNull String duration, @NotNull String days, @NotNull String startTime, @NotNull String endTime, @NotNull String seriesDay) {
        Intrinsics.checkNotNullParameter(duration, "duration");
        Intrinsics.checkNotNullParameter(days, "days");
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        Intrinsics.checkNotNullParameter(endTime, "endTime");
        Intrinsics.checkNotNullParameter(seriesDay, "seriesDay");
        return new PerusalDuration(duration, days, startTime, endTime, seriesDay);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PerusalDuration)) {
            return false;
        }
        PerusalDuration perusalDuration = (PerusalDuration) other;
        return Intrinsics.areEqual(this.duration, perusalDuration.duration) && Intrinsics.areEqual(this.days, perusalDuration.days) && Intrinsics.areEqual(this.startTime, perusalDuration.startTime) && Intrinsics.areEqual(this.endTime, perusalDuration.endTime) && Intrinsics.areEqual(this.seriesDay, perusalDuration.seriesDay);
    }

    @NotNull
    public final String getDays() {
        return this.days;
    }

    @NotNull
    public final String getDuration() {
        return this.duration;
    }

    @NotNull
    public final String getEndTime() {
        return this.endTime;
    }

    @NotNull
    public final String getSeriesDay() {
        return this.seriesDay;
    }

    @NotNull
    public final String getStartTime() {
        return this.startTime;
    }

    public int hashCode() {
        return (((((((this.duration.hashCode() * 31) + this.days.hashCode()) * 31) + this.startTime.hashCode()) * 31) + this.endTime.hashCode()) * 31) + this.seriesDay.hashCode();
    }

    public final void setDays(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.days = str;
    }

    public final void setDuration(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.duration = str;
    }

    public final void setEndTime(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.endTime = str;
    }

    public final void setSeriesDay(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.seriesDay = str;
    }

    public final void setStartTime(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.startTime = str;
    }

    @NotNull
    public String toString() {
        return "PerusalDuration(duration=" + this.duration + ", days=" + this.days + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", seriesDay=" + this.seriesDay + ')';
    }
}
