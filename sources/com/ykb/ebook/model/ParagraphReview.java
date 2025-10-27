package com.ykb.ebook.model;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.http.cookie.ClientCookie;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0017\n\u0002\u0010 \n\u0002\b_\b\u0087\b\u0018\u00002\u00020\u0001B£\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\f\u001a\u00020\u0007\u0012\b\b\u0002\u0010\r\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00000\u001f\u0012\b\b\u0002\u0010 \u001a\u00020\u0007\u0012\b\b\u0002\u0010!\u001a\u00020\u0007¢\u0006\u0002\u0010\"J\t\u0010]\u001a\u00020\u0003HÆ\u0003J\t\u0010^\u001a\u00020\u0007HÆ\u0003J\t\u0010_\u001a\u00020\u0007HÆ\u0003J\t\u0010`\u001a\u00020\u0007HÆ\u0003J\t\u0010a\u001a\u00020\u0007HÆ\u0003J\t\u0010b\u001a\u00020\u0007HÆ\u0003J\t\u0010c\u001a\u00020\u0007HÆ\u0003J\t\u0010d\u001a\u00020\u0007HÆ\u0003J\t\u0010e\u001a\u00020\u0007HÆ\u0003J\t\u0010f\u001a\u00020\u0007HÆ\u0003J\t\u0010g\u001a\u00020\u0007HÆ\u0003J\t\u0010h\u001a\u00020\u0005HÆ\u0003J\t\u0010i\u001a\u00020\u0007HÆ\u0003J\t\u0010j\u001a\u00020\u0007HÆ\u0003J\t\u0010k\u001a\u00020\u0007HÆ\u0003J\t\u0010l\u001a\u00020\u0007HÆ\u0003J\t\u0010m\u001a\u00020\u0005HÆ\u0003J\t\u0010n\u001a\u00020\u0007HÆ\u0003J\u000f\u0010o\u001a\b\u0012\u0004\u0012\u00020\u00000\u001fHÆ\u0003J\t\u0010p\u001a\u00020\u0007HÆ\u0003J\t\u0010q\u001a\u00020\u0007HÆ\u0003J\t\u0010r\u001a\u00020\u0007HÆ\u0003J\t\u0010s\u001a\u00020\u0007HÆ\u0003J\t\u0010t\u001a\u00020\u0003HÆ\u0003J\t\u0010u\u001a\u00020\u0007HÆ\u0003J\t\u0010v\u001a\u00020\u0007HÆ\u0003J\t\u0010w\u001a\u00020\u0007HÆ\u0003J\t\u0010x\u001a\u00020\u0007HÆ\u0003J§\u0002\u0010y\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u00072\b\b\u0002\u0010\f\u001a\u00020\u00072\b\b\u0002\u0010\r\u001a\u00020\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u00072\b\b\u0002\u0010\u000f\u001a\u00020\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u00072\b\b\u0002\u0010\u0011\u001a\u00020\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u00072\b\b\u0002\u0010\u0013\u001a\u00020\u00072\b\b\u0002\u0010\u0014\u001a\u00020\u00072\b\b\u0002\u0010\u0015\u001a\u00020\u00072\b\b\u0002\u0010\u0016\u001a\u00020\u00072\b\b\u0002\u0010\u0017\u001a\u00020\u00072\b\b\u0002\u0010\u0018\u001a\u00020\u00072\b\b\u0002\u0010\u0019\u001a\u00020\u00072\b\b\u0002\u0010\u001a\u001a\u00020\u00072\b\b\u0002\u0010\u001b\u001a\u00020\u00072\b\b\u0002\u0010\u001c\u001a\u00020\u00052\b\b\u0002\u0010\u001d\u001a\u00020\u00072\u000e\b\u0002\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00000\u001f2\b\b\u0002\u0010 \u001a\u00020\u00072\b\b\u0002\u0010!\u001a\u00020\u0007HÆ\u0001J\u0013\u0010z\u001a\u00020\u00032\b\u0010{\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010|\u001a\u00020\u0005HÖ\u0001J\t\u0010}\u001a\u00020\u0007HÖ\u0001R\u001e\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001e\u0010\n\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010$\"\u0004\b(\u0010&R\u001e\u0010\u000b\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010$\"\u0004\b*\u0010&R\u001e\u0010\f\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010$\"\u0004\b,\u0010&R\u001e\u0010\r\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010$\"\u0004\b.\u0010&R\u001e\u0010\u0018\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010$\"\u0004\b0\u0010&R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u001e\u0010\u000e\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010$\"\u0004\b6\u0010&R\u001e\u0010\u001d\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u0010$\"\u0004\b8\u0010&R\u001e\u0010 \u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010$\"\u0004\b3\u0010&R\u001e\u0010\u000f\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010$\"\u0004\b9\u0010&R\u001e\u0010\u0010\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010$\"\u0004\b:\u0010&R\u001e\u0010\u001c\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010;\"\u0004\b<\u0010=R\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010$\"\u0004\b>\u0010&R\u001e\u0010\u0011\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010$\"\u0004\b@\u0010&R\u001e\u0010\u0012\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010$\"\u0004\bB\u0010&R\u001e\u0010\u001b\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010$\"\u0004\bD\u0010&R\u001e\u0010\u0013\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010$\"\u0004\bF\u0010&R\u001e\u0010\u0014\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010$\"\u0004\bH\u0010&R\u001e\u0010\u0015\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010$\"\u0004\bJ\u0010&R$\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00000\u001f8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010L\"\u0004\bM\u0010NR\u001e\u0010!\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010$\"\u0004\bP\u0010&R\u001e\u0010\u001a\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010$\"\u0004\bR\u0010&R\u001e\u0010\u0016\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bS\u0010$\"\u0004\bT\u0010&R\u001a\u0010\t\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bU\u00102\"\u0004\bV\u00104R\u001e\u0010\u0017\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bW\u0010$\"\u0004\bX\u0010&R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010;\"\u0004\bZ\u0010=R\u001e\u0010\u0019\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b[\u0010$\"\u0004\b\\\u0010&¨\u0006~"}, d2 = {"Lcom/ykb/ebook/model/ParagraphReview;", "", "hot", "", "type", "", "avatar", "", "is_logout", "suspend", "bookId", "chapterId", ClientCookie.COMMENT_ATTR, "ctime", "id", "isOpposition", "isSupport", "nickname", "oppositionCount", "paragraphId", "picture", "replyCount", "supportCount", "title", "floorNum", "userId", "school", "paragraphContent", "isVip", "identity", "replyList", "", "isHot", "replyPrimaryId", "(ZILjava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "getAvatar", "()Ljava/lang/String;", "setAvatar", "(Ljava/lang/String;)V", "getBookId", "setBookId", "getChapterId", "setChapterId", "getComment", "setComment", "getCtime", "setCtime", "getFloorNum", "setFloorNum", "getHot", "()Z", "setHot", "(Z)V", "getId", "setId", "getIdentity", "setIdentity", "setOpposition", "setSupport", "()I", "setVip", "(I)V", "set_logout", "getNickname", "setNickname", "getOppositionCount", "setOppositionCount", "getParagraphContent", "setParagraphContent", "getParagraphId", "setParagraphId", "getPicture", "setPicture", "getReplyCount", "setReplyCount", "getReplyList", "()Ljava/util/List;", "setReplyList", "(Ljava/util/List;)V", "getReplyPrimaryId", "setReplyPrimaryId", "getSchool", "setSchool", "getSupportCount", "setSupportCount", "getSuspend", "setSuspend", "getTitle", "setTitle", "getType", "setType", "getUserId", "setUserId", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class ParagraphReview {

    @SerializedName("avatar")
    @NotNull
    private String avatar;

    @SerializedName("book_id")
    @NotNull
    private String bookId;

    @SerializedName("chapter_id")
    @NotNull
    private String chapterId;

    @SerializedName(ClientCookie.COMMENT_ATTR)
    @NotNull
    private String comment;

    @SerializedName("ctime")
    @NotNull
    private String ctime;

    @SerializedName("floor_num")
    @NotNull
    private String floorNum;
    private boolean hot;

    @SerializedName("id")
    @NotNull
    private String id;

    @SerializedName("identity")
    @NotNull
    private String identity;

    @SerializedName("is_hot")
    @NotNull
    private String isHot;

    @SerializedName("is_opposition")
    @NotNull
    private String isOpposition;

    @SerializedName("is_support")
    @NotNull
    private String isSupport;

    @SerializedName("is_vip")
    private int isVip;

    @NotNull
    private String is_logout;

    @SerializedName("nickname")
    @NotNull
    private String nickname;

    @SerializedName("opposition_count")
    @NotNull
    private String oppositionCount;

    @SerializedName("paragraph_content")
    @NotNull
    private String paragraphContent;

    @SerializedName("paragraph_id")
    @NotNull
    private String paragraphId;

    @SerializedName("picture")
    @NotNull
    private String picture;

    @SerializedName("reply_count")
    @NotNull
    private String replyCount;

    @SerializedName("reply")
    @NotNull
    private List<ParagraphReview> replyList;

    @SerializedName("reply_primary_id")
    @NotNull
    private String replyPrimaryId;

    @SerializedName("school")
    @NotNull
    private String school;

    @SerializedName("support_count")
    @NotNull
    private String supportCount;
    private boolean suspend;

    @SerializedName("title")
    @NotNull
    private String title;
    private int type;

    @SerializedName("user_id")
    @NotNull
    private String userId;

    public ParagraphReview() {
        this(false, 0, null, null, false, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null, null, null, 268435455, null);
    }

    public ParagraphReview(boolean z2, int i2, @NotNull String avatar, @NotNull String is_logout, boolean z3, @NotNull String bookId, @NotNull String chapterId, @NotNull String comment, @NotNull String ctime, @NotNull String id, @NotNull String isOpposition, @NotNull String isSupport, @NotNull String nickname, @NotNull String oppositionCount, @NotNull String paragraphId, @NotNull String picture, @NotNull String replyCount, @NotNull String supportCount, @NotNull String title, @NotNull String floorNum, @NotNull String userId, @NotNull String school, @NotNull String paragraphContent, int i3, @NotNull String identity, @NotNull List<ParagraphReview> replyList, @NotNull String isHot, @NotNull String replyPrimaryId) {
        Intrinsics.checkNotNullParameter(avatar, "avatar");
        Intrinsics.checkNotNullParameter(is_logout, "is_logout");
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        Intrinsics.checkNotNullParameter(chapterId, "chapterId");
        Intrinsics.checkNotNullParameter(comment, "comment");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(isOpposition, "isOpposition");
        Intrinsics.checkNotNullParameter(isSupport, "isSupport");
        Intrinsics.checkNotNullParameter(nickname, "nickname");
        Intrinsics.checkNotNullParameter(oppositionCount, "oppositionCount");
        Intrinsics.checkNotNullParameter(paragraphId, "paragraphId");
        Intrinsics.checkNotNullParameter(picture, "picture");
        Intrinsics.checkNotNullParameter(replyCount, "replyCount");
        Intrinsics.checkNotNullParameter(supportCount, "supportCount");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(floorNum, "floorNum");
        Intrinsics.checkNotNullParameter(userId, "userId");
        Intrinsics.checkNotNullParameter(school, "school");
        Intrinsics.checkNotNullParameter(paragraphContent, "paragraphContent");
        Intrinsics.checkNotNullParameter(identity, "identity");
        Intrinsics.checkNotNullParameter(replyList, "replyList");
        Intrinsics.checkNotNullParameter(isHot, "isHot");
        Intrinsics.checkNotNullParameter(replyPrimaryId, "replyPrimaryId");
        this.hot = z2;
        this.type = i2;
        this.avatar = avatar;
        this.is_logout = is_logout;
        this.suspend = z3;
        this.bookId = bookId;
        this.chapterId = chapterId;
        this.comment = comment;
        this.ctime = ctime;
        this.id = id;
        this.isOpposition = isOpposition;
        this.isSupport = isSupport;
        this.nickname = nickname;
        this.oppositionCount = oppositionCount;
        this.paragraphId = paragraphId;
        this.picture = picture;
        this.replyCount = replyCount;
        this.supportCount = supportCount;
        this.title = title;
        this.floorNum = floorNum;
        this.userId = userId;
        this.school = school;
        this.paragraphContent = paragraphContent;
        this.isVip = i3;
        this.identity = identity;
        this.replyList = replyList;
        this.isHot = isHot;
        this.replyPrimaryId = replyPrimaryId;
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getHot() {
        return this.hot;
    }

    @NotNull
    /* renamed from: component10, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    /* renamed from: component11, reason: from getter */
    public final String getIsOpposition() {
        return this.isOpposition;
    }

    @NotNull
    /* renamed from: component12, reason: from getter */
    public final String getIsSupport() {
        return this.isSupport;
    }

    @NotNull
    /* renamed from: component13, reason: from getter */
    public final String getNickname() {
        return this.nickname;
    }

    @NotNull
    /* renamed from: component14, reason: from getter */
    public final String getOppositionCount() {
        return this.oppositionCount;
    }

    @NotNull
    /* renamed from: component15, reason: from getter */
    public final String getParagraphId() {
        return this.paragraphId;
    }

    @NotNull
    /* renamed from: component16, reason: from getter */
    public final String getPicture() {
        return this.picture;
    }

    @NotNull
    /* renamed from: component17, reason: from getter */
    public final String getReplyCount() {
        return this.replyCount;
    }

    @NotNull
    /* renamed from: component18, reason: from getter */
    public final String getSupportCount() {
        return this.supportCount;
    }

    @NotNull
    /* renamed from: component19, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component2, reason: from getter */
    public final int getType() {
        return this.type;
    }

    @NotNull
    /* renamed from: component20, reason: from getter */
    public final String getFloorNum() {
        return this.floorNum;
    }

    @NotNull
    /* renamed from: component21, reason: from getter */
    public final String getUserId() {
        return this.userId;
    }

    @NotNull
    /* renamed from: component22, reason: from getter */
    public final String getSchool() {
        return this.school;
    }

    @NotNull
    /* renamed from: component23, reason: from getter */
    public final String getParagraphContent() {
        return this.paragraphContent;
    }

    /* renamed from: component24, reason: from getter */
    public final int getIsVip() {
        return this.isVip;
    }

    @NotNull
    /* renamed from: component25, reason: from getter */
    public final String getIdentity() {
        return this.identity;
    }

    @NotNull
    public final List<ParagraphReview> component26() {
        return this.replyList;
    }

    @NotNull
    /* renamed from: component27, reason: from getter */
    public final String getIsHot() {
        return this.isHot;
    }

    @NotNull
    /* renamed from: component28, reason: from getter */
    public final String getReplyPrimaryId() {
        return this.replyPrimaryId;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getAvatar() {
        return this.avatar;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getIs_logout() {
        return this.is_logout;
    }

    /* renamed from: component5, reason: from getter */
    public final boolean getSuspend() {
        return this.suspend;
    }

    @NotNull
    /* renamed from: component6, reason: from getter */
    public final String getBookId() {
        return this.bookId;
    }

    @NotNull
    /* renamed from: component7, reason: from getter */
    public final String getChapterId() {
        return this.chapterId;
    }

    @NotNull
    /* renamed from: component8, reason: from getter */
    public final String getComment() {
        return this.comment;
    }

    @NotNull
    /* renamed from: component9, reason: from getter */
    public final String getCtime() {
        return this.ctime;
    }

    @NotNull
    public final ParagraphReview copy(boolean hot, int type, @NotNull String avatar, @NotNull String is_logout, boolean suspend, @NotNull String bookId, @NotNull String chapterId, @NotNull String comment, @NotNull String ctime, @NotNull String id, @NotNull String isOpposition, @NotNull String isSupport, @NotNull String nickname, @NotNull String oppositionCount, @NotNull String paragraphId, @NotNull String picture, @NotNull String replyCount, @NotNull String supportCount, @NotNull String title, @NotNull String floorNum, @NotNull String userId, @NotNull String school, @NotNull String paragraphContent, int isVip, @NotNull String identity, @NotNull List<ParagraphReview> replyList, @NotNull String isHot, @NotNull String replyPrimaryId) {
        Intrinsics.checkNotNullParameter(avatar, "avatar");
        Intrinsics.checkNotNullParameter(is_logout, "is_logout");
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        Intrinsics.checkNotNullParameter(chapterId, "chapterId");
        Intrinsics.checkNotNullParameter(comment, "comment");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(isOpposition, "isOpposition");
        Intrinsics.checkNotNullParameter(isSupport, "isSupport");
        Intrinsics.checkNotNullParameter(nickname, "nickname");
        Intrinsics.checkNotNullParameter(oppositionCount, "oppositionCount");
        Intrinsics.checkNotNullParameter(paragraphId, "paragraphId");
        Intrinsics.checkNotNullParameter(picture, "picture");
        Intrinsics.checkNotNullParameter(replyCount, "replyCount");
        Intrinsics.checkNotNullParameter(supportCount, "supportCount");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(floorNum, "floorNum");
        Intrinsics.checkNotNullParameter(userId, "userId");
        Intrinsics.checkNotNullParameter(school, "school");
        Intrinsics.checkNotNullParameter(paragraphContent, "paragraphContent");
        Intrinsics.checkNotNullParameter(identity, "identity");
        Intrinsics.checkNotNullParameter(replyList, "replyList");
        Intrinsics.checkNotNullParameter(isHot, "isHot");
        Intrinsics.checkNotNullParameter(replyPrimaryId, "replyPrimaryId");
        return new ParagraphReview(hot, type, avatar, is_logout, suspend, bookId, chapterId, comment, ctime, id, isOpposition, isSupport, nickname, oppositionCount, paragraphId, picture, replyCount, supportCount, title, floorNum, userId, school, paragraphContent, isVip, identity, replyList, isHot, replyPrimaryId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ParagraphReview)) {
            return false;
        }
        ParagraphReview paragraphReview = (ParagraphReview) other;
        return this.hot == paragraphReview.hot && this.type == paragraphReview.type && Intrinsics.areEqual(this.avatar, paragraphReview.avatar) && Intrinsics.areEqual(this.is_logout, paragraphReview.is_logout) && this.suspend == paragraphReview.suspend && Intrinsics.areEqual(this.bookId, paragraphReview.bookId) && Intrinsics.areEqual(this.chapterId, paragraphReview.chapterId) && Intrinsics.areEqual(this.comment, paragraphReview.comment) && Intrinsics.areEqual(this.ctime, paragraphReview.ctime) && Intrinsics.areEqual(this.id, paragraphReview.id) && Intrinsics.areEqual(this.isOpposition, paragraphReview.isOpposition) && Intrinsics.areEqual(this.isSupport, paragraphReview.isSupport) && Intrinsics.areEqual(this.nickname, paragraphReview.nickname) && Intrinsics.areEqual(this.oppositionCount, paragraphReview.oppositionCount) && Intrinsics.areEqual(this.paragraphId, paragraphReview.paragraphId) && Intrinsics.areEqual(this.picture, paragraphReview.picture) && Intrinsics.areEqual(this.replyCount, paragraphReview.replyCount) && Intrinsics.areEqual(this.supportCount, paragraphReview.supportCount) && Intrinsics.areEqual(this.title, paragraphReview.title) && Intrinsics.areEqual(this.floorNum, paragraphReview.floorNum) && Intrinsics.areEqual(this.userId, paragraphReview.userId) && Intrinsics.areEqual(this.school, paragraphReview.school) && Intrinsics.areEqual(this.paragraphContent, paragraphReview.paragraphContent) && this.isVip == paragraphReview.isVip && Intrinsics.areEqual(this.identity, paragraphReview.identity) && Intrinsics.areEqual(this.replyList, paragraphReview.replyList) && Intrinsics.areEqual(this.isHot, paragraphReview.isHot) && Intrinsics.areEqual(this.replyPrimaryId, paragraphReview.replyPrimaryId);
    }

    @NotNull
    public final String getAvatar() {
        return this.avatar;
    }

    @NotNull
    public final String getBookId() {
        return this.bookId;
    }

    @NotNull
    public final String getChapterId() {
        return this.chapterId;
    }

    @NotNull
    public final String getComment() {
        return this.comment;
    }

    @NotNull
    public final String getCtime() {
        return this.ctime;
    }

    @NotNull
    public final String getFloorNum() {
        return this.floorNum;
    }

    public final boolean getHot() {
        return this.hot;
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
    public final String getNickname() {
        return this.nickname;
    }

    @NotNull
    public final String getOppositionCount() {
        return this.oppositionCount;
    }

    @NotNull
    public final String getParagraphContent() {
        return this.paragraphContent;
    }

    @NotNull
    public final String getParagraphId() {
        return this.paragraphId;
    }

    @NotNull
    public final String getPicture() {
        return this.picture;
    }

    @NotNull
    public final String getReplyCount() {
        return this.replyCount;
    }

    @NotNull
    public final List<ParagraphReview> getReplyList() {
        return this.replyList;
    }

    @NotNull
    public final String getReplyPrimaryId() {
        return this.replyPrimaryId;
    }

    @NotNull
    public final String getSchool() {
        return this.school;
    }

    @NotNull
    public final String getSupportCount() {
        return this.supportCount;
    }

    public final boolean getSuspend() {
        return this.suspend;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public final int getType() {
        return this.type;
    }

    @NotNull
    public final String getUserId() {
        return this.userId;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v56 */
    /* JADX WARN: Type inference failed for: r0v57 */
    public int hashCode() {
        boolean z2 = this.hot;
        ?? r02 = z2;
        if (z2) {
            r02 = 1;
        }
        int iHashCode = ((((((r02 * 31) + this.type) * 31) + this.avatar.hashCode()) * 31) + this.is_logout.hashCode()) * 31;
        boolean z3 = this.suspend;
        return ((((((((((((((((((((((((((((((((((((((((((((((iHashCode + (z3 ? 1 : z3 ? 1 : 0)) * 31) + this.bookId.hashCode()) * 31) + this.chapterId.hashCode()) * 31) + this.comment.hashCode()) * 31) + this.ctime.hashCode()) * 31) + this.id.hashCode()) * 31) + this.isOpposition.hashCode()) * 31) + this.isSupport.hashCode()) * 31) + this.nickname.hashCode()) * 31) + this.oppositionCount.hashCode()) * 31) + this.paragraphId.hashCode()) * 31) + this.picture.hashCode()) * 31) + this.replyCount.hashCode()) * 31) + this.supportCount.hashCode()) * 31) + this.title.hashCode()) * 31) + this.floorNum.hashCode()) * 31) + this.userId.hashCode()) * 31) + this.school.hashCode()) * 31) + this.paragraphContent.hashCode()) * 31) + this.isVip) * 31) + this.identity.hashCode()) * 31) + this.replyList.hashCode()) * 31) + this.isHot.hashCode()) * 31) + this.replyPrimaryId.hashCode();
    }

    @NotNull
    public final String isHot() {
        return this.isHot;
    }

    @NotNull
    public final String isOpposition() {
        return this.isOpposition;
    }

    @NotNull
    public final String isSupport() {
        return this.isSupport;
    }

    public final int isVip() {
        return this.isVip;
    }

    @NotNull
    public final String is_logout() {
        return this.is_logout;
    }

    public final void setAvatar(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.avatar = str;
    }

    public final void setBookId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.bookId = str;
    }

    public final void setChapterId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.chapterId = str;
    }

    public final void setComment(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.comment = str;
    }

    public final void setCtime(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.ctime = str;
    }

    public final void setFloorNum(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.floorNum = str;
    }

    public final void setHot(boolean z2) {
        this.hot = z2;
    }

    public final void setId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final void setIdentity(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.identity = str;
    }

    public final void setNickname(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.nickname = str;
    }

    public final void setOpposition(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.isOpposition = str;
    }

    public final void setOppositionCount(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.oppositionCount = str;
    }

    public final void setParagraphContent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.paragraphContent = str;
    }

    public final void setParagraphId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.paragraphId = str;
    }

    public final void setPicture(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.picture = str;
    }

    public final void setReplyCount(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.replyCount = str;
    }

    public final void setReplyList(@NotNull List<ParagraphReview> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.replyList = list;
    }

    public final void setReplyPrimaryId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.replyPrimaryId = str;
    }

    public final void setSchool(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.school = str;
    }

    public final void setSupport(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.isSupport = str;
    }

    public final void setSupportCount(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.supportCount = str;
    }

    public final void setSuspend(boolean z2) {
        this.suspend = z2;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final void setType(int i2) {
        this.type = i2;
    }

    public final void setUserId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.userId = str;
    }

    public final void setVip(int i2) {
        this.isVip = i2;
    }

    public final void set_logout(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.is_logout = str;
    }

    @NotNull
    public String toString() {
        return "ParagraphReview(hot=" + this.hot + ", type=" + this.type + ", avatar=" + this.avatar + ", is_logout=" + this.is_logout + ", suspend=" + this.suspend + ", bookId=" + this.bookId + ", chapterId=" + this.chapterId + ", comment=" + this.comment + ", ctime=" + this.ctime + ", id=" + this.id + ", isOpposition=" + this.isOpposition + ", isSupport=" + this.isSupport + ", nickname=" + this.nickname + ", oppositionCount=" + this.oppositionCount + ", paragraphId=" + this.paragraphId + ", picture=" + this.picture + ", replyCount=" + this.replyCount + ", supportCount=" + this.supportCount + ", title=" + this.title + ", floorNum=" + this.floorNum + ", userId=" + this.userId + ", school=" + this.school + ", paragraphContent=" + this.paragraphContent + ", isVip=" + this.isVip + ", identity=" + this.identity + ", replyList=" + this.replyList + ", isHot=" + this.isHot + ", replyPrimaryId=" + this.replyPrimaryId + ')';
    }

    public final void setHot(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.isHot = str;
    }

    public /* synthetic */ ParagraphReview(boolean z2, int i2, String str, String str2, boolean z3, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18, String str19, String str20, int i3, String str21, List list, String str22, String str23, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? false : z2, (i4 & 2) != 0 ? 0 : i2, (i4 & 4) != 0 ? "" : str, (i4 & 8) != 0 ? "" : str2, (i4 & 16) != 0 ? false : z3, (i4 & 32) != 0 ? "" : str3, (i4 & 64) != 0 ? "" : str4, (i4 & 128) != 0 ? "" : str5, (i4 & 256) != 0 ? "" : str6, (i4 & 512) != 0 ? "" : str7, (i4 & 1024) != 0 ? "" : str8, (i4 & 2048) != 0 ? "" : str9, (i4 & 4096) != 0 ? "" : str10, (i4 & 8192) != 0 ? "" : str11, (i4 & 16384) != 0 ? "" : str12, (i4 & 32768) != 0 ? "" : str13, (i4 & 65536) != 0 ? "0" : str14, (i4 & 131072) != 0 ? "0" : str15, (i4 & 262144) != 0 ? "" : str16, (i4 & 524288) == 0 ? str17 : "0", (i4 & 1048576) != 0 ? "" : str18, (i4 & 2097152) != 0 ? "" : str19, (i4 & 4194304) != 0 ? "" : str20, (i4 & 8388608) != 0 ? 0 : i3, (i4 & 16777216) != 0 ? "" : str21, (i4 & 33554432) != 0 ? CollectionsKt__CollectionsKt.emptyList() : list, (i4 & 67108864) != 0 ? "" : str22, (i4 & 134217728) != 0 ? "" : str23);
    }
}
