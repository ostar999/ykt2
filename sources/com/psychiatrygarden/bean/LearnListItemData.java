package com.psychiatrygarden.bean;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u001c\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001Bw\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0010¢\u0006\u0002\u0010\u0012J\u000b\u0010)\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u000eHÆ\u0003J\u0011\u0010+\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0010HÆ\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010/\u001a\u00020\u0003HÆ\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\fHÆ\u0003J\u0091\u0001\u00104\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0010\b\u0002\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0010HÆ\u0001J\u0013\u00105\u001a\u0002062\b\u00107\u001a\u0004\u0018\u000108HÖ\u0003J\t\u00109\u001a\u00020\u0019HÖ\u0001J\t\u0010:\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001aR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0019\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0010¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0014R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0014R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0014R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(¨\u0006;"}, d2 = {"Lcom/psychiatrygarden/bean/LearnListItemData;", "Lcom/chad/library/adapter/base/entity/MultiItemEntity;", "updated_at", "", "course_id", "deadline", "title", "type", AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "percent", "before_watch", "live_info", "Lcom/psychiatrygarden/bean/LiveInfo;", "video_info", "Lcom/psychiatrygarden/bean/VideoInfo;", "package_info", "", "Lcom/psychiatrygarden/bean/PackageInfo;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/psychiatrygarden/bean/LiveInfo;Lcom/psychiatrygarden/bean/VideoInfo;Ljava/util/List;)V", "getBefore_watch", "()Ljava/lang/String;", "getCourse_id", "getCover", "getDeadline", "isOpen", "", "()I", "setOpen", "(I)V", "itemType", "getItemType", "getLive_info", "()Lcom/psychiatrygarden/bean/LiveInfo;", "getPackage_info", "()Ljava/util/List;", "getPercent", "getTitle", "getType", "getUpdated_at", "getVideo_info", "()Lcom/psychiatrygarden/bean/VideoInfo;", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "", "hashCode", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class LearnListItemData implements MultiItemEntity {

    @Nullable
    private final String before_watch;

    @Nullable
    private final String course_id;

    @Nullable
    private final String cover;

    @Nullable
    private final String deadline;
    private int isOpen;

    @Nullable
    private final LiveInfo live_info;

    @Nullable
    private final List<PackageInfo> package_info;

    @Nullable
    private final String percent;

    @Nullable
    private final String title;

    @NotNull
    private final String type;

    @Nullable
    private final String updated_at;

    @Nullable
    private final VideoInfo video_info;

    public LearnListItemData(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @NotNull String type, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable LiveInfo liveInfo, @Nullable VideoInfo videoInfo, @Nullable List<PackageInfo> list) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.updated_at = str;
        this.course_id = str2;
        this.deadline = str3;
        this.title = str4;
        this.type = type;
        this.cover = str5;
        this.percent = str6;
        this.before_watch = str7;
        this.live_info = liveInfo;
        this.video_info = videoInfo;
        this.package_info = list;
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getUpdated_at() {
        return this.updated_at;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final VideoInfo getVideo_info() {
        return this.video_info;
    }

    @Nullable
    public final List<PackageInfo> component11() {
        return this.package_info;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getCourse_id() {
        return this.course_id;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getDeadline() {
        return this.deadline;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getCover() {
        return this.cover;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getPercent() {
        return this.percent;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getBefore_watch() {
        return this.before_watch;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final LiveInfo getLive_info() {
        return this.live_info;
    }

    @NotNull
    public final LearnListItemData copy(@Nullable String updated_at, @Nullable String course_id, @Nullable String deadline, @Nullable String title, @NotNull String type, @Nullable String cover, @Nullable String percent, @Nullable String before_watch, @Nullable LiveInfo live_info, @Nullable VideoInfo video_info, @Nullable List<PackageInfo> package_info) {
        Intrinsics.checkNotNullParameter(type, "type");
        return new LearnListItemData(updated_at, course_id, deadline, title, type, cover, percent, before_watch, live_info, video_info, package_info);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LearnListItemData)) {
            return false;
        }
        LearnListItemData learnListItemData = (LearnListItemData) other;
        return Intrinsics.areEqual(this.updated_at, learnListItemData.updated_at) && Intrinsics.areEqual(this.course_id, learnListItemData.course_id) && Intrinsics.areEqual(this.deadline, learnListItemData.deadline) && Intrinsics.areEqual(this.title, learnListItemData.title) && Intrinsics.areEqual(this.type, learnListItemData.type) && Intrinsics.areEqual(this.cover, learnListItemData.cover) && Intrinsics.areEqual(this.percent, learnListItemData.percent) && Intrinsics.areEqual(this.before_watch, learnListItemData.before_watch) && Intrinsics.areEqual(this.live_info, learnListItemData.live_info) && Intrinsics.areEqual(this.video_info, learnListItemData.video_info) && Intrinsics.areEqual(this.package_info, learnListItemData.package_info);
    }

    @Nullable
    public final String getBefore_watch() {
        return this.before_watch;
    }

    @Nullable
    public final String getCourse_id() {
        return this.course_id;
    }

    @Nullable
    public final String getCover() {
        return this.cover;
    }

    @Nullable
    public final String getDeadline() {
        return this.deadline;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return Integer.parseInt(this.type);
    }

    @Nullable
    public final LiveInfo getLive_info() {
        return this.live_info;
    }

    @Nullable
    public final List<PackageInfo> getPackage_info() {
        return this.package_info;
    }

    @Nullable
    public final String getPercent() {
        return this.percent;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final String getType() {
        return this.type;
    }

    @Nullable
    public final String getUpdated_at() {
        return this.updated_at;
    }

    @Nullable
    public final VideoInfo getVideo_info() {
        return this.video_info;
    }

    public int hashCode() {
        String str = this.updated_at;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.course_id;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.deadline;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.title;
        int iHashCode4 = (((iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31) + this.type.hashCode()) * 31;
        String str5 = this.cover;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.percent;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.before_watch;
        int iHashCode7 = (iHashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        LiveInfo liveInfo = this.live_info;
        int iHashCode8 = (iHashCode7 + (liveInfo == null ? 0 : liveInfo.hashCode())) * 31;
        VideoInfo videoInfo = this.video_info;
        int iHashCode9 = (iHashCode8 + (videoInfo == null ? 0 : videoInfo.hashCode())) * 31;
        List<PackageInfo> list = this.package_info;
        return iHashCode9 + (list != null ? list.hashCode() : 0);
    }

    /* renamed from: isOpen, reason: from getter */
    public final int getIsOpen() {
        return this.isOpen;
    }

    public final void setOpen(int i2) {
        this.isOpen = i2;
    }

    @NotNull
    public String toString() {
        return "LearnListItemData(updated_at=" + this.updated_at + ", course_id=" + this.course_id + ", deadline=" + this.deadline + ", title=" + this.title + ", type=" + this.type + ", cover=" + this.cover + ", percent=" + this.percent + ", before_watch=" + this.before_watch + ", live_info=" + this.live_info + ", video_info=" + this.video_info + ", package_info=" + this.package_info + ')';
    }
}
