package com.ykb.ebook.model;

import androidx.annotation.Keep;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.gson.annotations.SerializedName;
import com.ykb.ebook.common.PreferKeyKt;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b;\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\u0083\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\u0006\u0010\u0010\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\t¢\u0006\u0002\u0010\u0013J\t\u00104\u001a\u00020\u0003HÆ\u0003J\t\u00105\u001a\u00020\u0003HÆ\u0003J\t\u00106\u001a\u00020\u0003HÆ\u0003J\t\u00107\u001a\u00020\u0003HÆ\u0003J\t\u00108\u001a\u00020\u0003HÆ\u0003J\t\u00109\u001a\u00020\u0003HÆ\u0003J\t\u0010:\u001a\u00020\tHÆ\u0003J\t\u0010;\u001a\u00020\u0003HÆ\u0003J\t\u0010<\u001a\u00020\u0003HÆ\u0003J\t\u0010=\u001a\u00020\u0003HÆ\u0003J\t\u0010>\u001a\u00020\u0003HÆ\u0003J\t\u0010?\u001a\u00020\tHÆ\u0003J\t\u0010@\u001a\u00020\u0003HÆ\u0003J\t\u0010A\u001a\u00020\u0003HÆ\u0003J\t\u0010B\u001a\u00020\u0003HÆ\u0003J\u009f\u0001\u0010C\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\tHÆ\u0001J\u0013\u0010D\u001a\u00020E2\b\u0010F\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010G\u001a\u00020\tHÖ\u0001J\t\u0010H\u001a\u00020\u0003HÖ\u0001R\u001e\u0010\u000e\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0015\"\u0004\b\u0019\u0010\u0017R\u001e\u0010\n\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0015\"\u0004\b\u001b\u0010\u0017R\u001e\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0015\"\u0004\b!\u0010\u0017R\u001e\u0010\u0011\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0015\"\u0004\b#\u0010\u0017R\u001a\u0010\f\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0015\"\u0004\b$\u0010\u0017R\u001e\u0010\u0012\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u001d\"\u0004\b%\u0010\u001fR\u001e\u0010\u0007\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0015\"\u0004\b'\u0010\u0017R\u001e\u0010\r\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0015\"\u0004\b)\u0010\u0017R\u001e\u0010\u0005\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0015\"\u0004\b+\u0010\u0017R\u001e\u0010\u000f\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0015\"\u0004\b-\u0010\u0017R\u001e\u0010\u0006\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0015\"\u0004\b/\u0010\u0017R\u001e\u0010\u0010\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u0015\"\u0004\b1\u0010\u0017R\u001e\u0010\u000b\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\u0015\"\u0004\b3\u0010\u0017¨\u0006I"}, d2 = {"Lcom/ykb/ebook/model/NoteListBean;", "", "id", "", "chapter_id", "notes_content", PreferKeyKt.START_POSITION, SessionDescription.ATTR_LENGTH, "display_status", "", "ctime", "user_id", "is_logout", "nickname", "avatar", "school", "title", "identity", "is_vip", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", "getAvatar", "()Ljava/lang/String;", "setAvatar", "(Ljava/lang/String;)V", "getChapter_id", "setChapter_id", "getCtime", "setCtime", "getDisplay_status", "()I", "setDisplay_status", "(I)V", "getId", "setId", "getIdentity", "setIdentity", "set_logout", "set_vip", "getLength", "setLength", "getNickname", "setNickname", "getNotes_content", "setNotes_content", "getSchool", "setSchool", "getStart_position", "setStart_position", "getTitle", "setTitle", "getUser_id", "setUser_id", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class NoteListBean {

    @SerializedName("avatar")
    @NotNull
    private String avatar;

    @SerializedName("chapter_id")
    @NotNull
    private String chapter_id;

    @SerializedName("ctime")
    @NotNull
    private String ctime;

    @SerializedName("display_status")
    private int display_status;

    @SerializedName("id")
    @NotNull
    private String id;

    @SerializedName("identity")
    @NotNull
    private String identity;

    @NotNull
    private String is_logout;

    @SerializedName("is_vip")
    private int is_vip;

    @SerializedName(SessionDescription.ATTR_LENGTH)
    @NotNull
    private String length;

    @SerializedName("nickname")
    @NotNull
    private String nickname;

    @SerializedName("notes_content")
    @NotNull
    private String notes_content;

    @SerializedName("school")
    @NotNull
    private String school;

    @SerializedName(PreferKeyKt.START_POSITION)
    @NotNull
    private String start_position;

    @SerializedName("title")
    @NotNull
    private String title;

    @SerializedName("user_id")
    @NotNull
    private String user_id;

    public NoteListBean(@NotNull String id, @NotNull String chapter_id, @NotNull String notes_content, @NotNull String start_position, @NotNull String length, int i2, @NotNull String ctime, @NotNull String user_id, @NotNull String is_logout, @NotNull String nickname, @NotNull String avatar, @NotNull String school, @NotNull String title, @NotNull String identity, int i3) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(chapter_id, "chapter_id");
        Intrinsics.checkNotNullParameter(notes_content, "notes_content");
        Intrinsics.checkNotNullParameter(start_position, "start_position");
        Intrinsics.checkNotNullParameter(length, "length");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(user_id, "user_id");
        Intrinsics.checkNotNullParameter(is_logout, "is_logout");
        Intrinsics.checkNotNullParameter(nickname, "nickname");
        Intrinsics.checkNotNullParameter(avatar, "avatar");
        Intrinsics.checkNotNullParameter(school, "school");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(identity, "identity");
        this.id = id;
        this.chapter_id = chapter_id;
        this.notes_content = notes_content;
        this.start_position = start_position;
        this.length = length;
        this.display_status = i2;
        this.ctime = ctime;
        this.user_id = user_id;
        this.is_logout = is_logout;
        this.nickname = nickname;
        this.avatar = avatar;
        this.school = school;
        this.title = title;
        this.identity = identity;
        this.is_vip = i3;
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    /* renamed from: component10, reason: from getter */
    public final String getNickname() {
        return this.nickname;
    }

    @NotNull
    /* renamed from: component11, reason: from getter */
    public final String getAvatar() {
        return this.avatar;
    }

    @NotNull
    /* renamed from: component12, reason: from getter */
    public final String getSchool() {
        return this.school;
    }

    @NotNull
    /* renamed from: component13, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    /* renamed from: component14, reason: from getter */
    public final String getIdentity() {
        return this.identity;
    }

    /* renamed from: component15, reason: from getter */
    public final int getIs_vip() {
        return this.is_vip;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getChapter_id() {
        return this.chapter_id;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getNotes_content() {
        return this.notes_content;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getStart_position() {
        return this.start_position;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getLength() {
        return this.length;
    }

    /* renamed from: component6, reason: from getter */
    public final int getDisplay_status() {
        return this.display_status;
    }

    @NotNull
    /* renamed from: component7, reason: from getter */
    public final String getCtime() {
        return this.ctime;
    }

    @NotNull
    /* renamed from: component8, reason: from getter */
    public final String getUser_id() {
        return this.user_id;
    }

    @NotNull
    /* renamed from: component9, reason: from getter */
    public final String getIs_logout() {
        return this.is_logout;
    }

    @NotNull
    public final NoteListBean copy(@NotNull String id, @NotNull String chapter_id, @NotNull String notes_content, @NotNull String start_position, @NotNull String length, int display_status, @NotNull String ctime, @NotNull String user_id, @NotNull String is_logout, @NotNull String nickname, @NotNull String avatar, @NotNull String school, @NotNull String title, @NotNull String identity, int is_vip) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(chapter_id, "chapter_id");
        Intrinsics.checkNotNullParameter(notes_content, "notes_content");
        Intrinsics.checkNotNullParameter(start_position, "start_position");
        Intrinsics.checkNotNullParameter(length, "length");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(user_id, "user_id");
        Intrinsics.checkNotNullParameter(is_logout, "is_logout");
        Intrinsics.checkNotNullParameter(nickname, "nickname");
        Intrinsics.checkNotNullParameter(avatar, "avatar");
        Intrinsics.checkNotNullParameter(school, "school");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(identity, "identity");
        return new NoteListBean(id, chapter_id, notes_content, start_position, length, display_status, ctime, user_id, is_logout, nickname, avatar, school, title, identity, is_vip);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NoteListBean)) {
            return false;
        }
        NoteListBean noteListBean = (NoteListBean) other;
        return Intrinsics.areEqual(this.id, noteListBean.id) && Intrinsics.areEqual(this.chapter_id, noteListBean.chapter_id) && Intrinsics.areEqual(this.notes_content, noteListBean.notes_content) && Intrinsics.areEqual(this.start_position, noteListBean.start_position) && Intrinsics.areEqual(this.length, noteListBean.length) && this.display_status == noteListBean.display_status && Intrinsics.areEqual(this.ctime, noteListBean.ctime) && Intrinsics.areEqual(this.user_id, noteListBean.user_id) && Intrinsics.areEqual(this.is_logout, noteListBean.is_logout) && Intrinsics.areEqual(this.nickname, noteListBean.nickname) && Intrinsics.areEqual(this.avatar, noteListBean.avatar) && Intrinsics.areEqual(this.school, noteListBean.school) && Intrinsics.areEqual(this.title, noteListBean.title) && Intrinsics.areEqual(this.identity, noteListBean.identity) && this.is_vip == noteListBean.is_vip;
    }

    @NotNull
    public final String getAvatar() {
        return this.avatar;
    }

    @NotNull
    public final String getChapter_id() {
        return this.chapter_id;
    }

    @NotNull
    public final String getCtime() {
        return this.ctime;
    }

    public final int getDisplay_status() {
        return this.display_status;
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final String getIdentity() {
        return this.identity;
    }

    @NotNull
    public final String getLength() {
        return this.length;
    }

    @NotNull
    public final String getNickname() {
        return this.nickname;
    }

    @NotNull
    public final String getNotes_content() {
        return this.notes_content;
    }

    @NotNull
    public final String getSchool() {
        return this.school;
    }

    @NotNull
    public final String getStart_position() {
        return this.start_position;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final String getUser_id() {
        return this.user_id;
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((this.id.hashCode() * 31) + this.chapter_id.hashCode()) * 31) + this.notes_content.hashCode()) * 31) + this.start_position.hashCode()) * 31) + this.length.hashCode()) * 31) + this.display_status) * 31) + this.ctime.hashCode()) * 31) + this.user_id.hashCode()) * 31) + this.is_logout.hashCode()) * 31) + this.nickname.hashCode()) * 31) + this.avatar.hashCode()) * 31) + this.school.hashCode()) * 31) + this.title.hashCode()) * 31) + this.identity.hashCode()) * 31) + this.is_vip;
    }

    @NotNull
    public final String is_logout() {
        return this.is_logout;
    }

    public final int is_vip() {
        return this.is_vip;
    }

    public final void setAvatar(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.avatar = str;
    }

    public final void setChapter_id(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.chapter_id = str;
    }

    public final void setCtime(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.ctime = str;
    }

    public final void setDisplay_status(int i2) {
        this.display_status = i2;
    }

    public final void setId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final void setIdentity(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.identity = str;
    }

    public final void setLength(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.length = str;
    }

    public final void setNickname(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.nickname = str;
    }

    public final void setNotes_content(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.notes_content = str;
    }

    public final void setSchool(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.school = str;
    }

    public final void setStart_position(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.start_position = str;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final void setUser_id(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.user_id = str;
    }

    public final void set_logout(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.is_logout = str;
    }

    public final void set_vip(int i2) {
        this.is_vip = i2;
    }

    @NotNull
    public String toString() {
        return "NoteListBean(id=" + this.id + ", chapter_id=" + this.chapter_id + ", notes_content=" + this.notes_content + ", start_position=" + this.start_position + ", length=" + this.length + ", display_status=" + this.display_status + ", ctime=" + this.ctime + ", user_id=" + this.user_id + ", is_logout=" + this.is_logout + ", nickname=" + this.nickname + ", avatar=" + this.avatar + ", school=" + this.school + ", title=" + this.title + ", identity=" + this.identity + ", is_vip=" + this.is_vip + ')';
    }

    public /* synthetic */ NoteListBean(String str, String str2, String str3, String str4, String str5, int i2, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, str4, str5, (i4 & 32) != 0 ? 1 : i2, str6, str7, str8, str9, str10, str11, str12, (i4 & 8192) != 0 ? "" : str13, (i4 & 16384) != 0 ? 0 : i3);
    }
}
