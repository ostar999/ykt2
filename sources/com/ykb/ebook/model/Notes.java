package com.ykb.ebook.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import com.ykb.ebook.common.PreferKeyKt;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.parcelize.Parcelize;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Parcelize
@Keep
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b*\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001Bm\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000e\u001a\u00020\u0003¢\u0006\u0002\u0010\u000fJ\t\u0010)\u001a\u00020\u0003HÆ\u0003J\t\u0010*\u001a\u00020\fHÆ\u0003J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\t\u0010,\u001a\u00020\u0003HÆ\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010.\u001a\u00020\u0003HÆ\u0003J\t\u0010/\u001a\u00020\u0003HÆ\u0003J\t\u00100\u001a\u00020\u0003HÆ\u0003J\t\u00101\u001a\u00020\u0003HÆ\u0003J\t\u00102\u001a\u00020\u0003HÆ\u0003J\t\u00103\u001a\u00020\fHÆ\u0003Jy\u00104\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\u0003HÆ\u0001J\t\u00105\u001a\u00020\fHÖ\u0001J\u0013\u00106\u001a\u0002072\b\u00108\u001a\u0004\u0018\u000109HÖ\u0003J\t\u0010:\u001a\u00020\fHÖ\u0001J\t\u0010;\u001a\u00020\u0003HÖ\u0001J\u0019\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020\fHÖ\u0001R\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0006\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0011\"\u0004\b\u0013\u0010\u0014R\u001e\u0010\u0007\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0014R\u001e\u0010\b\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0011\"\u0004\b\u0018\u0010\u0014R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001e\u0010\t\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0011\"\u0004\b\u001e\u0010\u0014R\u001e\u0010\n\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0011\"\u0004\b \u0010\u0014R\u001a\u0010\r\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u001a\"\u0004\b\"\u0010\u001cR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0011\"\u0004\b$\u0010\u0014R\u001a\u0010\u000e\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0011\"\u0004\b&\u0010\u0014R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u0011\"\u0004\b(\u0010\u0014¨\u0006A"}, d2 = {"Lcom/ykb/ebook/model/Notes;", "Landroid/os/Parcelable;", "title", "", "sort", "chapterId", "ctime", "displayStatus", "drawContent", "id", "notesContent", "editPos", "", "parentPos", PreferKeyKt.START_POSITION, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;)V", "getChapterId", "()Ljava/lang/String;", "getCtime", "setCtime", "(Ljava/lang/String;)V", "getDisplayStatus", "setDisplayStatus", "getDrawContent", "setDrawContent", "getEditPos", "()I", "setEditPos", "(I)V", "getId", "setId", "getNotesContent", "setNotesContent", "getParentPos", "setParentPos", "getSort", "setSort", "getStart_position", "setStart_position", "getTitle", "setTitle", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "describeContents", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class Notes implements Parcelable {

    @NotNull
    public static final Parcelable.Creator<Notes> CREATOR = new Creator();

    @SerializedName("chapter_id")
    @Nullable
    private final String chapterId;

    @SerializedName("ctime")
    @NotNull
    private String ctime;

    @SerializedName("display_status")
    @NotNull
    private String displayStatus;

    @SerializedName("draw_content")
    @NotNull
    private String drawContent;
    private int editPos;

    @SerializedName("id")
    @NotNull
    private String id;

    @SerializedName("notes_content")
    @NotNull
    private String notesContent;
    private int parentPos;

    @NotNull
    private String sort;

    @NotNull
    private String start_position;

    @NotNull
    private String title;

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<Notes> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public final Notes createFromParcel(@NotNull Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new Notes(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public final Notes[] newArray(int i2) {
            return new Notes[i2];
        }
    }

    public Notes(@NotNull String title, @NotNull String sort, @Nullable String str, @NotNull String ctime, @NotNull String displayStatus, @NotNull String drawContent, @NotNull String id, @NotNull String notesContent, int i2, int i3, @NotNull String start_position) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(sort, "sort");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(displayStatus, "displayStatus");
        Intrinsics.checkNotNullParameter(drawContent, "drawContent");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(notesContent, "notesContent");
        Intrinsics.checkNotNullParameter(start_position, "start_position");
        this.title = title;
        this.sort = sort;
        this.chapterId = str;
        this.ctime = ctime;
        this.displayStatus = displayStatus;
        this.drawContent = drawContent;
        this.id = id;
        this.notesContent = notesContent;
        this.editPos = i2;
        this.parentPos = i3;
        this.start_position = start_position;
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component10, reason: from getter */
    public final int getParentPos() {
        return this.parentPos;
    }

    @NotNull
    /* renamed from: component11, reason: from getter */
    public final String getStart_position() {
        return this.start_position;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getSort() {
        return this.sort;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getChapterId() {
        return this.chapterId;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getCtime() {
        return this.ctime;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getDisplayStatus() {
        return this.displayStatus;
    }

    @NotNull
    /* renamed from: component6, reason: from getter */
    public final String getDrawContent() {
        return this.drawContent;
    }

    @NotNull
    /* renamed from: component7, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    /* renamed from: component8, reason: from getter */
    public final String getNotesContent() {
        return this.notesContent;
    }

    /* renamed from: component9, reason: from getter */
    public final int getEditPos() {
        return this.editPos;
    }

    @NotNull
    public final Notes copy(@NotNull String title, @NotNull String sort, @Nullable String chapterId, @NotNull String ctime, @NotNull String displayStatus, @NotNull String drawContent, @NotNull String id, @NotNull String notesContent, int editPos, int parentPos, @NotNull String start_position) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(sort, "sort");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(displayStatus, "displayStatus");
        Intrinsics.checkNotNullParameter(drawContent, "drawContent");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(notesContent, "notesContent");
        Intrinsics.checkNotNullParameter(start_position, "start_position");
        return new Notes(title, sort, chapterId, ctime, displayStatus, drawContent, id, notesContent, editPos, parentPos, start_position);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Notes)) {
            return false;
        }
        Notes notes = (Notes) other;
        return Intrinsics.areEqual(this.title, notes.title) && Intrinsics.areEqual(this.sort, notes.sort) && Intrinsics.areEqual(this.chapterId, notes.chapterId) && Intrinsics.areEqual(this.ctime, notes.ctime) && Intrinsics.areEqual(this.displayStatus, notes.displayStatus) && Intrinsics.areEqual(this.drawContent, notes.drawContent) && Intrinsics.areEqual(this.id, notes.id) && Intrinsics.areEqual(this.notesContent, notes.notesContent) && this.editPos == notes.editPos && this.parentPos == notes.parentPos && Intrinsics.areEqual(this.start_position, notes.start_position);
    }

    @Nullable
    public final String getChapterId() {
        return this.chapterId;
    }

    @NotNull
    public final String getCtime() {
        return this.ctime;
    }

    @NotNull
    public final String getDisplayStatus() {
        return this.displayStatus;
    }

    @NotNull
    public final String getDrawContent() {
        return this.drawContent;
    }

    public final int getEditPos() {
        return this.editPos;
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final String getNotesContent() {
        return this.notesContent;
    }

    public final int getParentPos() {
        return this.parentPos;
    }

    @NotNull
    public final String getSort() {
        return this.sort;
    }

    @NotNull
    public final String getStart_position() {
        return this.start_position;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        int iHashCode = ((this.title.hashCode() * 31) + this.sort.hashCode()) * 31;
        String str = this.chapterId;
        return ((((((((((((((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.ctime.hashCode()) * 31) + this.displayStatus.hashCode()) * 31) + this.drawContent.hashCode()) * 31) + this.id.hashCode()) * 31) + this.notesContent.hashCode()) * 31) + this.editPos) * 31) + this.parentPos) * 31) + this.start_position.hashCode();
    }

    public final void setCtime(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.ctime = str;
    }

    public final void setDisplayStatus(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.displayStatus = str;
    }

    public final void setDrawContent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.drawContent = str;
    }

    public final void setEditPos(int i2) {
        this.editPos = i2;
    }

    public final void setId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final void setNotesContent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.notesContent = str;
    }

    public final void setParentPos(int i2) {
        this.parentPos = i2;
    }

    public final void setSort(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.sort = str;
    }

    public final void setStart_position(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.start_position = str;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    @NotNull
    public String toString() {
        return "Notes(title=" + this.title + ", sort=" + this.sort + ", chapterId=" + this.chapterId + ", ctime=" + this.ctime + ", displayStatus=" + this.displayStatus + ", drawContent=" + this.drawContent + ", id=" + this.id + ", notesContent=" + this.notesContent + ", editPos=" + this.editPos + ", parentPos=" + this.parentPos + ", start_position=" + this.start_position + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NotNull Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeString(this.title);
        parcel.writeString(this.sort);
        parcel.writeString(this.chapterId);
        parcel.writeString(this.ctime);
        parcel.writeString(this.displayStatus);
        parcel.writeString(this.drawContent);
        parcel.writeString(this.id);
        parcel.writeString(this.notesContent);
        parcel.writeInt(this.editPos);
        parcel.writeInt(this.parentPos);
        parcel.writeString(this.start_position);
    }

    public /* synthetic */ Notes(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i2, int i3, String str9, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, (i4 & 8) != 0 ? "" : str4, (i4 & 16) != 0 ? "1" : str5, (i4 & 32) != 0 ? "" : str6, (i4 & 64) != 0 ? "" : str7, (i4 & 128) != 0 ? "" : str8, (i4 & 256) != 0 ? 0 : i2, (i4 & 512) != 0 ? 0 : i3, str9);
    }
}
