package com.psychiatrygarden.bean;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lcom/psychiatrygarden/bean/SpecialData;", "", "title", "", "list", "", "Lcom/psychiatrygarden/bean/SpecialBean;", "(Ljava/lang/String;Ljava/util/List;)V", "getList", "()Ljava/util/List;", "getTitle", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class SpecialData {

    @Nullable
    private final List<SpecialBean> list;

    @Nullable
    private final String title;

    public SpecialData(@Nullable String str, @Nullable List<SpecialBean> list) {
        this.title = str;
        this.list = list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SpecialData copy$default(SpecialData specialData, String str, List list, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = specialData.title;
        }
        if ((i2 & 2) != 0) {
            list = specialData.list;
        }
        return specialData.copy(str, list);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final List<SpecialBean> component2() {
        return this.list;
    }

    @NotNull
    public final SpecialData copy(@Nullable String title, @Nullable List<SpecialBean> list) {
        return new SpecialData(title, list);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SpecialData)) {
            return false;
        }
        SpecialData specialData = (SpecialData) other;
        return Intrinsics.areEqual(this.title, specialData.title) && Intrinsics.areEqual(this.list, specialData.list);
    }

    @Nullable
    public final List<SpecialBean> getList() {
        return this.list;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        String str = this.title;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        List<SpecialBean> list = this.list;
        return iHashCode + (list != null ? list.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "SpecialData(title=" + this.title + ", list=" + this.list + ')';
    }
}
