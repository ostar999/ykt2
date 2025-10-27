package com.psychiatrygarden.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\bHÆ\u0003J\t\u0010\u001c\u001a\u00020\nHÆ\u0003JO\u0010\u001d\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\nHÆ\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!HÖ\u0003J\t\u0010\"\u001a\u00020\nHÖ\u0001J\t\u0010#\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013¨\u0006$"}, d2 = {"Lcom/psychiatrygarden/bean/ChooseSchoolCalendarListItemBean;", "Lcom/chad/library/adapter/base/entity/MultiItemEntity;", "timeline_status", "", "timeline", "timeline_desc", "timeline_event_desc", PushConstants.EXTRA, "Lcom/psychiatrygarden/bean/TarGetParamBean;", "itemType", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/psychiatrygarden/bean/TarGetParamBean;I)V", "getExtra", "()Lcom/psychiatrygarden/bean/TarGetParamBean;", "getItemType", "()I", "setItemType", "(I)V", "getTimeline", "()Ljava/lang/String;", "getTimeline_desc", "getTimeline_event_desc", "getTimeline_status", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "", "hashCode", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class ChooseSchoolCalendarListItemBean implements MultiItemEntity {

    @Nullable
    private final TarGetParamBean extra;
    private int itemType;

    @Nullable
    private final String timeline;

    @Nullable
    private final String timeline_desc;

    @Nullable
    private final String timeline_event_desc;

    @Nullable
    private final String timeline_status;

    public ChooseSchoolCalendarListItemBean(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable TarGetParamBean tarGetParamBean, int i2) {
        this.timeline_status = str;
        this.timeline = str2;
        this.timeline_desc = str3;
        this.timeline_event_desc = str4;
        this.extra = tarGetParamBean;
        this.itemType = i2;
    }

    public static /* synthetic */ ChooseSchoolCalendarListItemBean copy$default(ChooseSchoolCalendarListItemBean chooseSchoolCalendarListItemBean, String str, String str2, String str3, String str4, TarGetParamBean tarGetParamBean, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = chooseSchoolCalendarListItemBean.timeline_status;
        }
        if ((i3 & 2) != 0) {
            str2 = chooseSchoolCalendarListItemBean.timeline;
        }
        String str5 = str2;
        if ((i3 & 4) != 0) {
            str3 = chooseSchoolCalendarListItemBean.timeline_desc;
        }
        String str6 = str3;
        if ((i3 & 8) != 0) {
            str4 = chooseSchoolCalendarListItemBean.timeline_event_desc;
        }
        String str7 = str4;
        if ((i3 & 16) != 0) {
            tarGetParamBean = chooseSchoolCalendarListItemBean.extra;
        }
        TarGetParamBean tarGetParamBean2 = tarGetParamBean;
        if ((i3 & 32) != 0) {
            i2 = chooseSchoolCalendarListItemBean.getItemType();
        }
        return chooseSchoolCalendarListItemBean.copy(str, str5, str6, str7, tarGetParamBean2, i2);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getTimeline_status() {
        return this.timeline_status;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getTimeline() {
        return this.timeline;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getTimeline_desc() {
        return this.timeline_desc;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getTimeline_event_desc() {
        return this.timeline_event_desc;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final TarGetParamBean getExtra() {
        return this.extra;
    }

    public final int component6() {
        return getItemType();
    }

    @NotNull
    public final ChooseSchoolCalendarListItemBean copy(@Nullable String timeline_status, @Nullable String timeline, @Nullable String timeline_desc, @Nullable String timeline_event_desc, @Nullable TarGetParamBean extra, int itemType) {
        return new ChooseSchoolCalendarListItemBean(timeline_status, timeline, timeline_desc, timeline_event_desc, extra, itemType);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChooseSchoolCalendarListItemBean)) {
            return false;
        }
        ChooseSchoolCalendarListItemBean chooseSchoolCalendarListItemBean = (ChooseSchoolCalendarListItemBean) other;
        return Intrinsics.areEqual(this.timeline_status, chooseSchoolCalendarListItemBean.timeline_status) && Intrinsics.areEqual(this.timeline, chooseSchoolCalendarListItemBean.timeline) && Intrinsics.areEqual(this.timeline_desc, chooseSchoolCalendarListItemBean.timeline_desc) && Intrinsics.areEqual(this.timeline_event_desc, chooseSchoolCalendarListItemBean.timeline_event_desc) && Intrinsics.areEqual(this.extra, chooseSchoolCalendarListItemBean.extra) && getItemType() == chooseSchoolCalendarListItemBean.getItemType();
    }

    @Nullable
    public final TarGetParamBean getExtra() {
        return this.extra;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.itemType;
    }

    @Nullable
    public final String getTimeline() {
        return this.timeline;
    }

    @Nullable
    public final String getTimeline_desc() {
        return this.timeline_desc;
    }

    @Nullable
    public final String getTimeline_event_desc() {
        return this.timeline_event_desc;
    }

    @Nullable
    public final String getTimeline_status() {
        return this.timeline_status;
    }

    public int hashCode() {
        String str = this.timeline_status;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.timeline;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.timeline_desc;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.timeline_event_desc;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        TarGetParamBean tarGetParamBean = this.extra;
        return ((iHashCode4 + (tarGetParamBean != null ? tarGetParamBean.hashCode() : 0)) * 31) + getItemType();
    }

    public void setItemType(int i2) {
        this.itemType = i2;
    }

    @NotNull
    public String toString() {
        return "ChooseSchoolCalendarListItemBean(timeline_status=" + this.timeline_status + ", timeline=" + this.timeline + ", timeline_desc=" + this.timeline_desc + ", timeline_event_desc=" + this.timeline_event_desc + ", extra=" + this.extra + ", itemType=" + getItemType() + ')';
    }

    public /* synthetic */ ChooseSchoolCalendarListItemBean(String str, String str2, String str3, String str4, TarGetParamBean tarGetParamBean, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, str4, tarGetParamBean, (i3 & 32) != 0 ? 1 : i2);
    }
}
