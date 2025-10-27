package com.ykb.ebook.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Keep;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
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
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b3\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B{\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0007¢\u0006\u0002\u0010\u0010J\t\u0010,\u001a\u00020\u0003HÆ\u0003J\t\u0010-\u001a\u00020\u0003HÆ\u0003J\t\u0010.\u001a\u00020\u0007HÆ\u0003J\t\u0010/\u001a\u00020\u0007HÆ\u0003J\t\u00100\u001a\u00020\u0003HÆ\u0003J\t\u00101\u001a\u00020\u0003HÆ\u0003J\t\u00102\u001a\u00020\u0007HÆ\u0003J\t\u00103\u001a\u00020\u0007HÆ\u0003J\t\u00104\u001a\u00020\u0007HÆ\u0003J\t\u00105\u001a\u00020\u0007HÆ\u0003J\t\u00106\u001a\u00020\u0007HÆ\u0003J\t\u00107\u001a\u00020\u0003HÆ\u0003J\u0081\u0001\u00108\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u00072\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00072\b\b\u0002\u0010\u000f\u001a\u00020\u0007HÆ\u0001J\t\u00109\u001a\u00020\u0007HÖ\u0001J\u0013\u0010:\u001a\u00020;2\b\u0010<\u001a\u0004\u0018\u00010=HÖ\u0003J\u0006\u0010>\u001a\u00020;J\t\u0010?\u001a\u00020\u0007HÖ\u0001J\b\u0010@\u001a\u00020\u0003H\u0016J\u0019\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020\u0007HÖ\u0001R\u001e\u0010\n\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001e\u0010\u0005\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u000f\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0012\"\u0004\b\u001a\u0010\u0014R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0016\"\u0004\b\u001c\u0010\u0018R\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0016\"\u0004\b\u001d\u0010\u0018R\u001e\u0010\b\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0012\"\u0004\b\u001f\u0010\u0014R\u001e\u0010\r\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0016\"\u0004\b!\u0010\u0018R\u001a\u0010\u000e\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0012\"\u0004\b#\u0010\u0014R\u001e\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0012\"\u0004\b%\u0010\u0014R\u001e\u0010\t\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0012\"\u0004\b'\u0010\u0014R\u001e\u0010\u000b\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0012\"\u0004\b)\u0010\u0014R\u001e\u0010\f\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0016\"\u0004\b+\u0010\u0018¨\u0006F"}, d2 = {"Lcom/ykb/ebook/model/Note;", "Landroid/os/Parcelable;", "id", "", "isNote", "drawContent", "startPosition", "", SessionDescription.ATTR_LENGTH, TtmlNode.TAG_STYLE, "color", "type", "userId", "noteContent", "startPos", "endPos", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIIIILjava/lang/String;Ljava/lang/String;II)V", "getColor", "()I", "setColor", "(I)V", "getDrawContent", "()Ljava/lang/String;", "setDrawContent", "(Ljava/lang/String;)V", "getEndPos", "setEndPos", "getId", "setId", "setNote", "getLength", "setLength", "getNoteContent", "setNoteContent", "getStartPos", "setStartPos", "getStartPosition", "setStartPosition", "getStyle", "setStyle", "getType", "setType", "getUserId", "setUserId", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "describeContents", "equals", "", "other", "", "hasNote", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class Note implements Parcelable {

    @NotNull
    public static final Parcelable.Creator<Note> CREATOR = new Creator();

    @SerializedName("color")
    private int color;

    @SerializedName("draw_content")
    @NotNull
    private String drawContent;
    private int endPos;

    @SerializedName("id")
    @NotNull
    private String id;

    @SerializedName("is_notes")
    @NotNull
    private String isNote;

    @SerializedName(SessionDescription.ATTR_LENGTH)
    private int length;

    @SerializedName("notes_content")
    @NotNull
    private String noteContent;
    private int startPos;

    @SerializedName(PreferKeyKt.START_POSITION)
    private int startPosition;

    @SerializedName(TtmlNode.TAG_STYLE)
    private int style;

    @SerializedName("type")
    private int type;

    @SerializedName("user_id")
    @NotNull
    private String userId;

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<Note> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public final Note createFromParcel(@NotNull Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new Note(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public final Note[] newArray(int i2) {
            return new Note[i2];
        }
    }

    public Note(@NotNull String id, @NotNull String isNote, @NotNull String drawContent, int i2, int i3, int i4, int i5, int i6, @NotNull String userId, @NotNull String noteContent, int i7, int i8) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(isNote, "isNote");
        Intrinsics.checkNotNullParameter(drawContent, "drawContent");
        Intrinsics.checkNotNullParameter(userId, "userId");
        Intrinsics.checkNotNullParameter(noteContent, "noteContent");
        this.id = id;
        this.isNote = isNote;
        this.drawContent = drawContent;
        this.startPosition = i2;
        this.length = i3;
        this.style = i4;
        this.color = i5;
        this.type = i6;
        this.userId = userId;
        this.noteContent = noteContent;
        this.startPos = i7;
        this.endPos = i8;
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    /* renamed from: component10, reason: from getter */
    public final String getNoteContent() {
        return this.noteContent;
    }

    /* renamed from: component11, reason: from getter */
    public final int getStartPos() {
        return this.startPos;
    }

    /* renamed from: component12, reason: from getter */
    public final int getEndPos() {
        return this.endPos;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getIsNote() {
        return this.isNote;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getDrawContent() {
        return this.drawContent;
    }

    /* renamed from: component4, reason: from getter */
    public final int getStartPosition() {
        return this.startPosition;
    }

    /* renamed from: component5, reason: from getter */
    public final int getLength() {
        return this.length;
    }

    /* renamed from: component6, reason: from getter */
    public final int getStyle() {
        return this.style;
    }

    /* renamed from: component7, reason: from getter */
    public final int getColor() {
        return this.color;
    }

    /* renamed from: component8, reason: from getter */
    public final int getType() {
        return this.type;
    }

    @NotNull
    /* renamed from: component9, reason: from getter */
    public final String getUserId() {
        return this.userId;
    }

    @NotNull
    public final Note copy(@NotNull String id, @NotNull String isNote, @NotNull String drawContent, int startPosition, int length, int style, int color, int type, @NotNull String userId, @NotNull String noteContent, int startPos, int endPos) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(isNote, "isNote");
        Intrinsics.checkNotNullParameter(drawContent, "drawContent");
        Intrinsics.checkNotNullParameter(userId, "userId");
        Intrinsics.checkNotNullParameter(noteContent, "noteContent");
        return new Note(id, isNote, drawContent, startPosition, length, style, color, type, userId, noteContent, startPos, endPos);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Note)) {
            return false;
        }
        Note note = (Note) other;
        return Intrinsics.areEqual(this.id, note.id) && Intrinsics.areEqual(this.isNote, note.isNote) && Intrinsics.areEqual(this.drawContent, note.drawContent) && this.startPosition == note.startPosition && this.length == note.length && this.style == note.style && this.color == note.color && this.type == note.type && Intrinsics.areEqual(this.userId, note.userId) && Intrinsics.areEqual(this.noteContent, note.noteContent) && this.startPos == note.startPos && this.endPos == note.endPos;
    }

    public final int getColor() {
        return this.color;
    }

    @NotNull
    public final String getDrawContent() {
        return this.drawContent;
    }

    public final int getEndPos() {
        return this.endPos;
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    public final int getLength() {
        return this.length;
    }

    @NotNull
    public final String getNoteContent() {
        return this.noteContent;
    }

    public final int getStartPos() {
        return this.startPos;
    }

    public final int getStartPosition() {
        return this.startPosition;
    }

    public final int getStyle() {
        return this.style;
    }

    public final int getType() {
        return this.type;
    }

    @NotNull
    public final String getUserId() {
        return this.userId;
    }

    public final boolean hasNote() {
        return Intrinsics.areEqual("1", this.isNote);
    }

    public int hashCode() {
        return (((((((((((((((((((((this.id.hashCode() * 31) + this.isNote.hashCode()) * 31) + this.drawContent.hashCode()) * 31) + this.startPosition) * 31) + this.length) * 31) + this.style) * 31) + this.color) * 31) + this.type) * 31) + this.userId.hashCode()) * 31) + this.noteContent.hashCode()) * 31) + this.startPos) * 31) + this.endPos;
    }

    @NotNull
    public final String isNote() {
        return this.isNote;
    }

    public final void setColor(int i2) {
        this.color = i2;
    }

    public final void setDrawContent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.drawContent = str;
    }

    public final void setEndPos(int i2) {
        this.endPos = i2;
    }

    public final void setId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final void setLength(int i2) {
        this.length = i2;
    }

    public final void setNote(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.isNote = str;
    }

    public final void setNoteContent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.noteContent = str;
    }

    public final void setStartPos(int i2) {
        this.startPos = i2;
    }

    public final void setStartPosition(int i2) {
        this.startPosition = i2;
    }

    public final void setStyle(int i2) {
        this.style = i2;
    }

    public final void setType(int i2) {
        this.type = i2;
    }

    public final void setUserId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.userId = str;
    }

    @NotNull
    public String toString() {
        return "Note(id='" + this.id + "', drawContent='" + this.drawContent + "', startPosition=" + this.startPosition + ", length=" + this.length + ", style=" + this.style + ", color=" + this.color + ", type=" + this.type + ", userId='" + this.userId + "', noteContent='" + this.noteContent + "', startPos=" + this.startPos + ", endPos=" + this.endPos + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NotNull Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeString(this.id);
        parcel.writeString(this.isNote);
        parcel.writeString(this.drawContent);
        parcel.writeInt(this.startPosition);
        parcel.writeInt(this.length);
        parcel.writeInt(this.style);
        parcel.writeInt(this.color);
        parcel.writeInt(this.type);
        parcel.writeString(this.userId);
        parcel.writeString(this.noteContent);
        parcel.writeInt(this.startPos);
        parcel.writeInt(this.endPos);
    }

    public /* synthetic */ Note(String str, String str2, String str3, int i2, int i3, int i4, int i5, int i6, String str4, String str5, int i7, int i8, int i9, DefaultConstructorMarker defaultConstructorMarker) {
        this((i9 & 1) != 0 ? "" : str, str2, (i9 & 4) != 0 ? "" : str3, (i9 & 8) != 0 ? 0 : i2, (i9 & 16) != 0 ? -1 : i3, (i9 & 32) != 0 ? -1 : i4, (i9 & 64) != 0 ? -1 : i5, (i9 & 128) != 0 ? 1 : i6, (i9 & 256) != 0 ? "" : str4, (i9 & 512) != 0 ? "" : str5, (i9 & 1024) != 0 ? -1 : i7, (i9 & 2048) != 0 ? -1 : i8);
    }
}
