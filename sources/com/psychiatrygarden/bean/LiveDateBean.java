package com.psychiatrygarden.bean;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005HÆ\u0003J'\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lcom/psychiatrygarden/bean/LiveDateBean;", "", "year_month", "", "day", "", "(Ljava/lang/String;Ljava/util/List;)V", "getDay", "()Ljava/util/List;", "getYear_month", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class LiveDateBean {

    @Nullable
    private final List<String> day;

    @Nullable
    private final String year_month;

    public LiveDateBean(@Nullable String str, @Nullable List<String> list) {
        this.year_month = str;
        this.day = list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ LiveDateBean copy$default(LiveDateBean liveDateBean, String str, List list, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = liveDateBean.year_month;
        }
        if ((i2 & 2) != 0) {
            list = liveDateBean.day;
        }
        return liveDateBean.copy(str, list);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getYear_month() {
        return this.year_month;
    }

    @Nullable
    public final List<String> component2() {
        return this.day;
    }

    @NotNull
    public final LiveDateBean copy(@Nullable String year_month, @Nullable List<String> day) {
        return new LiveDateBean(year_month, day);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LiveDateBean)) {
            return false;
        }
        LiveDateBean liveDateBean = (LiveDateBean) other;
        return Intrinsics.areEqual(this.year_month, liveDateBean.year_month) && Intrinsics.areEqual(this.day, liveDateBean.day);
    }

    @Nullable
    public final List<String> getDay() {
        return this.day;
    }

    @Nullable
    public final String getYear_month() {
        return this.year_month;
    }

    public int hashCode() {
        String str = this.year_month;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        List<String> list = this.day;
        return iHashCode + (list != null ? list.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "LiveDateBean(year_month=" + this.year_month + ", day=" + this.day + ')';
    }
}
