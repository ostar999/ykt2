package com.psychiatrygarden.bean;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lcom/psychiatrygarden/bean/ChooseSchoolCalendarListBean;", "", "tag", "", "list", "", "Lcom/psychiatrygarden/bean/ChooseSchoolCalendarListItemBean;", "(Ljava/lang/String;Ljava/util/List;)V", "getList", "()Ljava/util/List;", "getTag", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class ChooseSchoolCalendarListBean {

    @Nullable
    private final List<ChooseSchoolCalendarListItemBean> list;

    @Nullable
    private final String tag;

    public ChooseSchoolCalendarListBean(@Nullable String str, @Nullable List<ChooseSchoolCalendarListItemBean> list) {
        this.tag = str;
        this.list = list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ChooseSchoolCalendarListBean copy$default(ChooseSchoolCalendarListBean chooseSchoolCalendarListBean, String str, List list, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = chooseSchoolCalendarListBean.tag;
        }
        if ((i2 & 2) != 0) {
            list = chooseSchoolCalendarListBean.list;
        }
        return chooseSchoolCalendarListBean.copy(str, list);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getTag() {
        return this.tag;
    }

    @Nullable
    public final List<ChooseSchoolCalendarListItemBean> component2() {
        return this.list;
    }

    @NotNull
    public final ChooseSchoolCalendarListBean copy(@Nullable String tag, @Nullable List<ChooseSchoolCalendarListItemBean> list) {
        return new ChooseSchoolCalendarListBean(tag, list);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChooseSchoolCalendarListBean)) {
            return false;
        }
        ChooseSchoolCalendarListBean chooseSchoolCalendarListBean = (ChooseSchoolCalendarListBean) other;
        return Intrinsics.areEqual(this.tag, chooseSchoolCalendarListBean.tag) && Intrinsics.areEqual(this.list, chooseSchoolCalendarListBean.list);
    }

    @Nullable
    public final List<ChooseSchoolCalendarListItemBean> getList() {
        return this.list;
    }

    @Nullable
    public final String getTag() {
        return this.tag;
    }

    public int hashCode() {
        String str = this.tag;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        List<ChooseSchoolCalendarListItemBean> list = this.list;
        return iHashCode + (list != null ? list.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "ChooseSchoolCalendarListBean(tag=" + this.tag + ", list=" + this.list + ')';
    }
}
