package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.unity3d.splash.services.ads.adunit.AdUnitActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J3\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001R\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\t\"\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\t¨\u0006\u001a"}, d2 = {"Lcom/psychiatrygarden/bean/ChapterOptions;", "", "chapterId", "", "title", AdUnitActivity.EXTRA_ACTIVITY_ID, "pass", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getActivityId", "()Ljava/lang/String;", "getChapterId", "getPass", "setPass", "(Ljava/lang/String;)V", "getTitle", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class ChapterOptions {

    @SerializedName(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID)
    @Nullable
    private final String activityId;

    @SerializedName("chapter_id")
    @NotNull
    private final String chapterId;

    @NotNull
    private String pass;

    @NotNull
    private final String title;

    public ChapterOptions(@NotNull String chapterId, @NotNull String title, @Nullable String str, @NotNull String pass) {
        Intrinsics.checkNotNullParameter(chapterId, "chapterId");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(pass, "pass");
        this.chapterId = chapterId;
        this.title = title;
        this.activityId = str;
        this.pass = pass;
    }

    public static /* synthetic */ ChapterOptions copy$default(ChapterOptions chapterOptions, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = chapterOptions.chapterId;
        }
        if ((i2 & 2) != 0) {
            str2 = chapterOptions.title;
        }
        if ((i2 & 4) != 0) {
            str3 = chapterOptions.activityId;
        }
        if ((i2 & 8) != 0) {
            str4 = chapterOptions.pass;
        }
        return chapterOptions.copy(str, str2, str3, str4);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getChapterId() {
        return this.chapterId;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getActivityId() {
        return this.activityId;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getPass() {
        return this.pass;
    }

    @NotNull
    public final ChapterOptions copy(@NotNull String chapterId, @NotNull String title, @Nullable String activityId, @NotNull String pass) {
        Intrinsics.checkNotNullParameter(chapterId, "chapterId");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(pass, "pass");
        return new ChapterOptions(chapterId, title, activityId, pass);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChapterOptions)) {
            return false;
        }
        ChapterOptions chapterOptions = (ChapterOptions) other;
        return Intrinsics.areEqual(this.chapterId, chapterOptions.chapterId) && Intrinsics.areEqual(this.title, chapterOptions.title) && Intrinsics.areEqual(this.activityId, chapterOptions.activityId) && Intrinsics.areEqual(this.pass, chapterOptions.pass);
    }

    @Nullable
    public final String getActivityId() {
        return this.activityId;
    }

    @NotNull
    public final String getChapterId() {
        return this.chapterId;
    }

    @NotNull
    public final String getPass() {
        return this.pass;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        int iHashCode = ((this.chapterId.hashCode() * 31) + this.title.hashCode()) * 31;
        String str = this.activityId;
        return ((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.pass.hashCode();
    }

    public final void setPass(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.pass = str;
    }

    @NotNull
    public String toString() {
        return "ChapterOptions(chapterId=" + this.chapterId + ", title=" + this.title + ", activityId=" + this.activityId + ", pass=" + this.pass + ')';
    }
}
