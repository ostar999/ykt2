package com.ykb.ebook.model;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.http.cookie.ClientCookie;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u00001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010 \n\u0003\b\u0082\u0001\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0087\b\u0018\u00002\u00020\u0001Bó\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00000\u0017\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u0005\u0012\b\b\u0002\u0010 \u001a\u00020\u000b\u0012\b\b\u0002\u0010!\u001a\u00020\u0005\u0012\b\b\u0002\u0010\"\u001a\u00020\u0005\u0012\b\b\u0002\u0010#\u001a\u00020\u0003\u0012\b\b\u0002\u0010$\u001a\u00020\u0003\u0012\b\b\u0002\u0010%\u001a\u00020\u000b\u0012\b\b\u0002\u0010&\u001a\u00020\u0003\u0012\b\b\u0002\u0010'\u001a\u00020\u0003\u0012\b\b\u0002\u0010(\u001a\u00020\u0005\u0012\b\b\u0002\u0010)\u001a\u00020\u0005¢\u0006\u0002\u0010*J\t\u0010s\u001a\u00020\u0003HÆ\u0003J\t\u0010t\u001a\u00020\u0005HÆ\u0003J\t\u0010u\u001a\u00020\u0005HÆ\u0003J\t\u0010v\u001a\u00020\u000bHÆ\u0003J\t\u0010w\u001a\u00020\u0005HÆ\u0003J\t\u0010x\u001a\u00020\u0005HÆ\u0003J\t\u0010y\u001a\u00020\u0005HÆ\u0003J\t\u0010z\u001a\u00020\u000bHÆ\u0003J\t\u0010{\u001a\u00020\u0005HÆ\u0003J\u000f\u0010|\u001a\b\u0012\u0004\u0012\u00020\u00000\u0017HÆ\u0003J\t\u0010}\u001a\u00020\u0005HÆ\u0003J\t\u0010~\u001a\u00020\u0005HÆ\u0003J\t\u0010\u007f\u001a\u00020\u0005HÆ\u0003J\n\u0010\u0080\u0001\u001a\u00020\u0005HÆ\u0003J\n\u0010\u0081\u0001\u001a\u00020\u0005HÆ\u0003J\n\u0010\u0082\u0001\u001a\u00020\u0005HÆ\u0003J\n\u0010\u0083\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0084\u0001\u001a\u00020\u000bHÆ\u0003J\n\u0010\u0085\u0001\u001a\u00020\u0005HÆ\u0003J\n\u0010\u0086\u0001\u001a\u00020\u000bHÆ\u0003J\n\u0010\u0087\u0001\u001a\u00020\u0005HÆ\u0003J\n\u0010\u0088\u0001\u001a\u00020\u0005HÆ\u0003J\n\u0010\u0089\u0001\u001a\u00020\u0005HÆ\u0003J\n\u0010\u008a\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u008b\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u008c\u0001\u001a\u00020\u000bHÆ\u0003J\n\u0010\u008d\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u008e\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u008f\u0001\u001a\u00020\u0005HÆ\u0003J\n\u0010\u0090\u0001\u001a\u00020\u0005HÆ\u0003J\n\u0010\u0091\u0001\u001a\u00020\u0005HÆ\u0003J\n\u0010\u0092\u0001\u001a\u00020\u0005HÆ\u0003J\n\u0010\u0093\u0001\u001a\u00020\u0005HÆ\u0003J\n\u0010\u0094\u0001\u001a\u00020\u000bHÆ\u0003J\n\u0010\u0095\u0001\u001a\u00020\u000bHÆ\u0003J\n\u0010\u0096\u0001\u001a\u00020\u0005HÆ\u0003Jø\u0002\u0010\u0097\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u000b2\b\b\u0002\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\u00052\b\b\u0002\u0010\u0013\u001a\u00020\u00052\b\b\u0002\u0010\u0014\u001a\u00020\u000b2\b\b\u0002\u0010\u0015\u001a\u00020\u00052\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00000\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00052\b\b\u0002\u0010\u0019\u001a\u00020\u00052\b\b\u0002\u0010\u001a\u001a\u00020\u00052\b\b\u0002\u0010\u001b\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u00052\b\b\u0002\u0010\u001d\u001a\u00020\u00032\b\b\u0002\u0010\u001e\u001a\u00020\u000b2\b\b\u0002\u0010\u001f\u001a\u00020\u00052\b\b\u0002\u0010 \u001a\u00020\u000b2\b\b\u0002\u0010!\u001a\u00020\u00052\b\b\u0002\u0010\"\u001a\u00020\u00052\b\b\u0002\u0010#\u001a\u00020\u00032\b\b\u0002\u0010$\u001a\u00020\u00032\b\b\u0002\u0010%\u001a\u00020\u000b2\b\b\u0002\u0010&\u001a\u00020\u00032\b\b\u0002\u0010'\u001a\u00020\u00032\b\b\u0002\u0010(\u001a\u00020\u00052\b\b\u0002\u0010)\u001a\u00020\u0005HÆ\u0001J\u0016\u0010\u0098\u0001\u001a\u00020\u00032\n\u0010\u0099\u0001\u001a\u0005\u0018\u00010\u009a\u0001HÖ\u0003J\n\u0010\u009b\u0001\u001a\u00020\u000bHÖ\u0001J\n\u0010\u009c\u0001\u001a\u00020\u0005HÖ\u0001R\u0014\u0010+\u001a\u00020\u000bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u001e\u0010\u0006\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010/\"\u0004\b3\u00101R\u001e\u0010\u0007\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u0010/\"\u0004\b5\u00101R\u001e\u0010\b\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010/\"\u0004\b7\u00101R\u001e\u0010\u001a\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010/\"\u0004\b9\u00101R\u001e\u0010\t\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010/\"\u0004\b;\u00101R\u001e\u0010\u0015\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010/\"\u0004\b=\u00101R\u001a\u0010$\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010>\"\u0004\b?\u0010@R\u001a\u0010#\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010>\"\u0004\bA\u0010@R\u001a\u0010\u001d\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010>\"\u0004\bB\u0010@R\u001e\u0010)\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010/\"\u0004\bC\u00101R\u001e\u0010\n\u001a\u00020\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010-\"\u0004\bD\u0010ER\u001e\u0010\f\u001a\u00020\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010-\"\u0004\bF\u0010ER\u001e\u0010\u0014\u001a\u00020\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010-\"\u0004\bG\u0010ER\u001a\u0010\"\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010/\"\u0004\bH\u00101R\u001a\u0010'\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010>\"\u0004\bI\u0010@R\u001a\u0010&\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010>\"\u0004\bJ\u0010@R\u001e\u0010\r\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010/\"\u0004\bL\u00101R\u001e\u0010\u000e\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010/\"\u0004\bN\u00101R\u001a\u0010 \u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010-\"\u0004\bP\u0010ER\u001e\u0010(\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010/\"\u0004\bR\u00101R\u001e\u0010\u001c\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bS\u0010/\"\u0004\bT\u00101R\u001e\u0010\u000f\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bU\u0010/\"\u0004\bV\u00101R\u001e\u0010\u0019\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bW\u0010/\"\u0004\bX\u00101R\u001e\u0010\u0010\u001a\u00020\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010-\"\u0004\bZ\u0010ER$\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00000\u00178\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b[\u0010\\\"\u0004\b]\u0010^R\u001e\u0010\u001b\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b_\u0010/\"\u0004\b`\u00101R\u001e\u0010\u0018\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\ba\u0010/\"\u0004\bb\u00101R\u001e\u0010\u0011\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bc\u0010/\"\u0004\bd\u00101R\u001a\u0010!\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\be\u0010/\"\u0004\bf\u00101R\u001e\u0010\u0012\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bg\u0010/\"\u0004\bh\u00101R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bi\u0010>\"\u0004\bj\u0010@R\u001e\u0010\u001f\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bk\u0010/\"\u0004\bl\u00101R\u001a\u0010%\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bm\u0010-\"\u0004\bn\u0010ER\u001a\u0010\u001e\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bo\u0010-\"\u0004\bp\u0010ER\u001e\u0010\u0013\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bq\u0010/\"\u0004\br\u00101¨\u0006\u009d\u0001"}, d2 = {"Lcom/ykb/ebook/model/BookReview;", "Ljava/io/Serializable;", "suspend", "", "avatar", "", "bookId", ClientCookie.COMMENT_ATTR, "ctime", "id", "isOpposition", "", "isSupport", "nickname", "oppositionCount", "picture", "replyCount", "school", "supportCount", "userId", "isVip", "identity", "replyList", "", "reviewType", "rate", "floorNum", "replyPrimaryId", "parentId", "isHot", "type", "title", "otherView", "showName", "is_logout", "isActionSupport", "isActionReply", "tmpPosition", "is_zhankai", "is_showValue", "paragraphContent", "isHotStr", "(ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZILjava/lang/String;ILjava/lang/String;Ljava/lang/String;ZZIZZLjava/lang/String;Ljava/lang/String;)V", "SECTION", "getSECTION", "()I", "getAvatar", "()Ljava/lang/String;", "setAvatar", "(Ljava/lang/String;)V", "getBookId", "setBookId", "getComment", "setComment", "getCtime", "setCtime", "getFloorNum", "setFloorNum", "getId", "setId", "getIdentity", "setIdentity", "()Z", "setActionReply", "(Z)V", "setActionSupport", "setHot", "setHotStr", "setOpposition", "(I)V", "setSupport", "setVip", "set_logout", "set_showValue", "set_zhankai", "getNickname", "setNickname", "getOppositionCount", "setOppositionCount", "getOtherView", "setOtherView", "getParagraphContent", "setParagraphContent", "getParentId", "setParentId", "getPicture", "setPicture", "getRate", "setRate", "getReplyCount", "setReplyCount", "getReplyList", "()Ljava/util/List;", "setReplyList", "(Ljava/util/List;)V", "getReplyPrimaryId", "setReplyPrimaryId", "getReviewType", "setReviewType", "getSchool", "setSchool", "getShowName", "setShowName", "getSupportCount", "setSupportCount", "getSuspend", "setSuspend", "getTitle", "setTitle", "getTmpPosition", "setTmpPosition", "getType", "setType", "getUserId", "setUserId", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component35", "component36", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "", "hashCode", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class BookReview implements Serializable {
    private final int SECTION;

    @SerializedName("avatar")
    @NotNull
    private String avatar;

    @SerializedName("book_id")
    @NotNull
    private String bookId;

    @SerializedName(ClientCookie.COMMENT_ATTR)
    @NotNull
    private String comment;

    @SerializedName("ctime")
    @NotNull
    private String ctime;

    @SerializedName("floor_num")
    @NotNull
    private String floorNum;

    @SerializedName("id")
    @NotNull
    private String id;

    @SerializedName("identity")
    @NotNull
    private String identity;
    private boolean isActionReply;
    private boolean isActionSupport;
    private boolean isHot;

    @SerializedName("is_hot")
    @NotNull
    private String isHotStr;

    @SerializedName("is_opposition")
    private int isOpposition;

    @SerializedName("is_support")
    private int isSupport;

    @SerializedName("is_vip")
    private int isVip;

    @NotNull
    private String is_logout;
    private boolean is_showValue;
    private boolean is_zhankai;

    @SerializedName("nickname")
    @NotNull
    private String nickname;

    @SerializedName("opposition_count")
    @NotNull
    private String oppositionCount;
    private int otherView;

    @SerializedName("paragraph_content")
    @NotNull
    private String paragraphContent;

    @SerializedName("parent_id")
    @NotNull
    private String parentId;

    @SerializedName("picture")
    @NotNull
    private String picture;

    @SerializedName("rate")
    @NotNull
    private String rate;

    @SerializedName("reply_count")
    private int replyCount;

    @SerializedName("reply")
    @NotNull
    private List<BookReview> replyList;

    @SerializedName("reply_primary_id")
    @NotNull
    private String replyPrimaryId;

    @SerializedName("review_type")
    @NotNull
    private String reviewType;

    @SerializedName("school")
    @NotNull
    private String school;

    @NotNull
    private String showName;

    @SerializedName("support_count")
    @NotNull
    private String supportCount;
    private boolean suspend;

    @SerializedName("title")
    @NotNull
    private String title;
    private int tmpPosition;
    private int type;

    @SerializedName("user_id")
    @NotNull
    private String userId;

    public BookReview() {
        this(false, null, null, null, null, null, 0, 0, null, null, null, 0, null, null, null, 0, null, null, null, null, null, null, null, false, 0, null, 0, null, null, false, false, 0, false, false, null, null, -1, 15, null);
    }

    public BookReview(boolean z2, @NotNull String avatar, @NotNull String bookId, @NotNull String comment, @NotNull String ctime, @NotNull String id, int i2, int i3, @NotNull String nickname, @NotNull String oppositionCount, @NotNull String picture, int i4, @NotNull String school, @NotNull String supportCount, @NotNull String userId, int i5, @NotNull String identity, @NotNull List<BookReview> replyList, @NotNull String reviewType, @NotNull String rate, @NotNull String floorNum, @NotNull String replyPrimaryId, @NotNull String parentId, boolean z3, int i6, @NotNull String title, int i7, @NotNull String showName, @NotNull String is_logout, boolean z4, boolean z5, int i8, boolean z6, boolean z7, @NotNull String paragraphContent, @NotNull String isHotStr) {
        Intrinsics.checkNotNullParameter(avatar, "avatar");
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        Intrinsics.checkNotNullParameter(comment, "comment");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(nickname, "nickname");
        Intrinsics.checkNotNullParameter(oppositionCount, "oppositionCount");
        Intrinsics.checkNotNullParameter(picture, "picture");
        Intrinsics.checkNotNullParameter(school, "school");
        Intrinsics.checkNotNullParameter(supportCount, "supportCount");
        Intrinsics.checkNotNullParameter(userId, "userId");
        Intrinsics.checkNotNullParameter(identity, "identity");
        Intrinsics.checkNotNullParameter(replyList, "replyList");
        Intrinsics.checkNotNullParameter(reviewType, "reviewType");
        Intrinsics.checkNotNullParameter(rate, "rate");
        Intrinsics.checkNotNullParameter(floorNum, "floorNum");
        Intrinsics.checkNotNullParameter(replyPrimaryId, "replyPrimaryId");
        Intrinsics.checkNotNullParameter(parentId, "parentId");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(showName, "showName");
        Intrinsics.checkNotNullParameter(is_logout, "is_logout");
        Intrinsics.checkNotNullParameter(paragraphContent, "paragraphContent");
        Intrinsics.checkNotNullParameter(isHotStr, "isHotStr");
        this.suspend = z2;
        this.avatar = avatar;
        this.bookId = bookId;
        this.comment = comment;
        this.ctime = ctime;
        this.id = id;
        this.isOpposition = i2;
        this.isSupport = i3;
        this.nickname = nickname;
        this.oppositionCount = oppositionCount;
        this.picture = picture;
        this.replyCount = i4;
        this.school = school;
        this.supportCount = supportCount;
        this.userId = userId;
        this.isVip = i5;
        this.identity = identity;
        this.replyList = replyList;
        this.reviewType = reviewType;
        this.rate = rate;
        this.floorNum = floorNum;
        this.replyPrimaryId = replyPrimaryId;
        this.parentId = parentId;
        this.isHot = z3;
        this.type = i6;
        this.title = title;
        this.otherView = i7;
        this.showName = showName;
        this.is_logout = is_logout;
        this.isActionSupport = z4;
        this.isActionReply = z5;
        this.tmpPosition = i8;
        this.is_zhankai = z6;
        this.is_showValue = z7;
        this.paragraphContent = paragraphContent;
        this.isHotStr = isHotStr;
        this.SECTION = 1;
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getSuspend() {
        return this.suspend;
    }

    @NotNull
    /* renamed from: component10, reason: from getter */
    public final String getOppositionCount() {
        return this.oppositionCount;
    }

    @NotNull
    /* renamed from: component11, reason: from getter */
    public final String getPicture() {
        return this.picture;
    }

    /* renamed from: component12, reason: from getter */
    public final int getReplyCount() {
        return this.replyCount;
    }

    @NotNull
    /* renamed from: component13, reason: from getter */
    public final String getSchool() {
        return this.school;
    }

    @NotNull
    /* renamed from: component14, reason: from getter */
    public final String getSupportCount() {
        return this.supportCount;
    }

    @NotNull
    /* renamed from: component15, reason: from getter */
    public final String getUserId() {
        return this.userId;
    }

    /* renamed from: component16, reason: from getter */
    public final int getIsVip() {
        return this.isVip;
    }

    @NotNull
    /* renamed from: component17, reason: from getter */
    public final String getIdentity() {
        return this.identity;
    }

    @NotNull
    public final List<BookReview> component18() {
        return this.replyList;
    }

    @NotNull
    /* renamed from: component19, reason: from getter */
    public final String getReviewType() {
        return this.reviewType;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getAvatar() {
        return this.avatar;
    }

    @NotNull
    /* renamed from: component20, reason: from getter */
    public final String getRate() {
        return this.rate;
    }

    @NotNull
    /* renamed from: component21, reason: from getter */
    public final String getFloorNum() {
        return this.floorNum;
    }

    @NotNull
    /* renamed from: component22, reason: from getter */
    public final String getReplyPrimaryId() {
        return this.replyPrimaryId;
    }

    @NotNull
    /* renamed from: component23, reason: from getter */
    public final String getParentId() {
        return this.parentId;
    }

    /* renamed from: component24, reason: from getter */
    public final boolean getIsHot() {
        return this.isHot;
    }

    /* renamed from: component25, reason: from getter */
    public final int getType() {
        return this.type;
    }

    @NotNull
    /* renamed from: component26, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component27, reason: from getter */
    public final int getOtherView() {
        return this.otherView;
    }

    @NotNull
    /* renamed from: component28, reason: from getter */
    public final String getShowName() {
        return this.showName;
    }

    @NotNull
    /* renamed from: component29, reason: from getter */
    public final String getIs_logout() {
        return this.is_logout;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getBookId() {
        return this.bookId;
    }

    /* renamed from: component30, reason: from getter */
    public final boolean getIsActionSupport() {
        return this.isActionSupport;
    }

    /* renamed from: component31, reason: from getter */
    public final boolean getIsActionReply() {
        return this.isActionReply;
    }

    /* renamed from: component32, reason: from getter */
    public final int getTmpPosition() {
        return this.tmpPosition;
    }

    /* renamed from: component33, reason: from getter */
    public final boolean getIs_zhankai() {
        return this.is_zhankai;
    }

    /* renamed from: component34, reason: from getter */
    public final boolean getIs_showValue() {
        return this.is_showValue;
    }

    @NotNull
    /* renamed from: component35, reason: from getter */
    public final String getParagraphContent() {
        return this.paragraphContent;
    }

    @NotNull
    /* renamed from: component36, reason: from getter */
    public final String getIsHotStr() {
        return this.isHotStr;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getComment() {
        return this.comment;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getCtime() {
        return this.ctime;
    }

    @NotNull
    /* renamed from: component6, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* renamed from: component7, reason: from getter */
    public final int getIsOpposition() {
        return this.isOpposition;
    }

    /* renamed from: component8, reason: from getter */
    public final int getIsSupport() {
        return this.isSupport;
    }

    @NotNull
    /* renamed from: component9, reason: from getter */
    public final String getNickname() {
        return this.nickname;
    }

    @NotNull
    public final BookReview copy(boolean suspend, @NotNull String avatar, @NotNull String bookId, @NotNull String comment, @NotNull String ctime, @NotNull String id, int isOpposition, int isSupport, @NotNull String nickname, @NotNull String oppositionCount, @NotNull String picture, int replyCount, @NotNull String school, @NotNull String supportCount, @NotNull String userId, int isVip, @NotNull String identity, @NotNull List<BookReview> replyList, @NotNull String reviewType, @NotNull String rate, @NotNull String floorNum, @NotNull String replyPrimaryId, @NotNull String parentId, boolean isHot, int type, @NotNull String title, int otherView, @NotNull String showName, @NotNull String is_logout, boolean isActionSupport, boolean isActionReply, int tmpPosition, boolean is_zhankai, boolean is_showValue, @NotNull String paragraphContent, @NotNull String isHotStr) {
        Intrinsics.checkNotNullParameter(avatar, "avatar");
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        Intrinsics.checkNotNullParameter(comment, "comment");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(nickname, "nickname");
        Intrinsics.checkNotNullParameter(oppositionCount, "oppositionCount");
        Intrinsics.checkNotNullParameter(picture, "picture");
        Intrinsics.checkNotNullParameter(school, "school");
        Intrinsics.checkNotNullParameter(supportCount, "supportCount");
        Intrinsics.checkNotNullParameter(userId, "userId");
        Intrinsics.checkNotNullParameter(identity, "identity");
        Intrinsics.checkNotNullParameter(replyList, "replyList");
        Intrinsics.checkNotNullParameter(reviewType, "reviewType");
        Intrinsics.checkNotNullParameter(rate, "rate");
        Intrinsics.checkNotNullParameter(floorNum, "floorNum");
        Intrinsics.checkNotNullParameter(replyPrimaryId, "replyPrimaryId");
        Intrinsics.checkNotNullParameter(parentId, "parentId");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(showName, "showName");
        Intrinsics.checkNotNullParameter(is_logout, "is_logout");
        Intrinsics.checkNotNullParameter(paragraphContent, "paragraphContent");
        Intrinsics.checkNotNullParameter(isHotStr, "isHotStr");
        return new BookReview(suspend, avatar, bookId, comment, ctime, id, isOpposition, isSupport, nickname, oppositionCount, picture, replyCount, school, supportCount, userId, isVip, identity, replyList, reviewType, rate, floorNum, replyPrimaryId, parentId, isHot, type, title, otherView, showName, is_logout, isActionSupport, isActionReply, tmpPosition, is_zhankai, is_showValue, paragraphContent, isHotStr);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BookReview)) {
            return false;
        }
        BookReview bookReview = (BookReview) other;
        return this.suspend == bookReview.suspend && Intrinsics.areEqual(this.avatar, bookReview.avatar) && Intrinsics.areEqual(this.bookId, bookReview.bookId) && Intrinsics.areEqual(this.comment, bookReview.comment) && Intrinsics.areEqual(this.ctime, bookReview.ctime) && Intrinsics.areEqual(this.id, bookReview.id) && this.isOpposition == bookReview.isOpposition && this.isSupport == bookReview.isSupport && Intrinsics.areEqual(this.nickname, bookReview.nickname) && Intrinsics.areEqual(this.oppositionCount, bookReview.oppositionCount) && Intrinsics.areEqual(this.picture, bookReview.picture) && this.replyCount == bookReview.replyCount && Intrinsics.areEqual(this.school, bookReview.school) && Intrinsics.areEqual(this.supportCount, bookReview.supportCount) && Intrinsics.areEqual(this.userId, bookReview.userId) && this.isVip == bookReview.isVip && Intrinsics.areEqual(this.identity, bookReview.identity) && Intrinsics.areEqual(this.replyList, bookReview.replyList) && Intrinsics.areEqual(this.reviewType, bookReview.reviewType) && Intrinsics.areEqual(this.rate, bookReview.rate) && Intrinsics.areEqual(this.floorNum, bookReview.floorNum) && Intrinsics.areEqual(this.replyPrimaryId, bookReview.replyPrimaryId) && Intrinsics.areEqual(this.parentId, bookReview.parentId) && this.isHot == bookReview.isHot && this.type == bookReview.type && Intrinsics.areEqual(this.title, bookReview.title) && this.otherView == bookReview.otherView && Intrinsics.areEqual(this.showName, bookReview.showName) && Intrinsics.areEqual(this.is_logout, bookReview.is_logout) && this.isActionSupport == bookReview.isActionSupport && this.isActionReply == bookReview.isActionReply && this.tmpPosition == bookReview.tmpPosition && this.is_zhankai == bookReview.is_zhankai && this.is_showValue == bookReview.is_showValue && Intrinsics.areEqual(this.paragraphContent, bookReview.paragraphContent) && Intrinsics.areEqual(this.isHotStr, bookReview.isHotStr);
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

    public final int getOtherView() {
        return this.otherView;
    }

    @NotNull
    public final String getParagraphContent() {
        return this.paragraphContent;
    }

    @NotNull
    public final String getParentId() {
        return this.parentId;
    }

    @NotNull
    public final String getPicture() {
        return this.picture;
    }

    @NotNull
    public final String getRate() {
        return this.rate;
    }

    public final int getReplyCount() {
        return this.replyCount;
    }

    @NotNull
    public final List<BookReview> getReplyList() {
        return this.replyList;
    }

    @NotNull
    public final String getReplyPrimaryId() {
        return this.replyPrimaryId;
    }

    @NotNull
    public final String getReviewType() {
        return this.reviewType;
    }

    public final int getSECTION() {
        return this.SECTION;
    }

    @NotNull
    public final String getSchool() {
        return this.school;
    }

    @NotNull
    public final String getShowName() {
        return this.showName;
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

    public final int getTmpPosition() {
        return this.tmpPosition;
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
    /* JADX WARN: Type inference failed for: r0v72 */
    /* JADX WARN: Type inference failed for: r0v73 */
    /* JADX WARN: Type inference failed for: r2v40, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v50, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v52, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v55, types: [boolean] */
    public int hashCode() {
        boolean z2 = this.suspend;
        ?? r02 = z2;
        if (z2) {
            r02 = 1;
        }
        int iHashCode = ((((((((((((((((((((((((((((((((((((((((((((r02 * 31) + this.avatar.hashCode()) * 31) + this.bookId.hashCode()) * 31) + this.comment.hashCode()) * 31) + this.ctime.hashCode()) * 31) + this.id.hashCode()) * 31) + this.isOpposition) * 31) + this.isSupport) * 31) + this.nickname.hashCode()) * 31) + this.oppositionCount.hashCode()) * 31) + this.picture.hashCode()) * 31) + this.replyCount) * 31) + this.school.hashCode()) * 31) + this.supportCount.hashCode()) * 31) + this.userId.hashCode()) * 31) + this.isVip) * 31) + this.identity.hashCode()) * 31) + this.replyList.hashCode()) * 31) + this.reviewType.hashCode()) * 31) + this.rate.hashCode()) * 31) + this.floorNum.hashCode()) * 31) + this.replyPrimaryId.hashCode()) * 31) + this.parentId.hashCode()) * 31;
        ?? r2 = this.isHot;
        int i2 = r2;
        if (r2 != 0) {
            i2 = 1;
        }
        int iHashCode2 = (((((((((((iHashCode + i2) * 31) + this.type) * 31) + this.title.hashCode()) * 31) + this.otherView) * 31) + this.showName.hashCode()) * 31) + this.is_logout.hashCode()) * 31;
        ?? r22 = this.isActionSupport;
        int i3 = r22;
        if (r22 != 0) {
            i3 = 1;
        }
        int i4 = (iHashCode2 + i3) * 31;
        ?? r23 = this.isActionReply;
        int i5 = r23;
        if (r23 != 0) {
            i5 = 1;
        }
        int i6 = (((i4 + i5) * 31) + this.tmpPosition) * 31;
        ?? r24 = this.is_zhankai;
        int i7 = r24;
        if (r24 != 0) {
            i7 = 1;
        }
        int i8 = (i6 + i7) * 31;
        boolean z3 = this.is_showValue;
        return ((((i8 + (z3 ? 1 : z3 ? 1 : 0)) * 31) + this.paragraphContent.hashCode()) * 31) + this.isHotStr.hashCode();
    }

    public final boolean isActionReply() {
        return this.isActionReply;
    }

    public final boolean isActionSupport() {
        return this.isActionSupport;
    }

    public final boolean isHot() {
        return this.isHot;
    }

    @NotNull
    public final String isHotStr() {
        return this.isHotStr;
    }

    public final int isOpposition() {
        return this.isOpposition;
    }

    public final int isSupport() {
        return this.isSupport;
    }

    public final int isVip() {
        return this.isVip;
    }

    @NotNull
    public final String is_logout() {
        return this.is_logout;
    }

    public final boolean is_showValue() {
        return this.is_showValue;
    }

    public final boolean is_zhankai() {
        return this.is_zhankai;
    }

    public final void setActionReply(boolean z2) {
        this.isActionReply = z2;
    }

    public final void setActionSupport(boolean z2) {
        this.isActionSupport = z2;
    }

    public final void setAvatar(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.avatar = str;
    }

    public final void setBookId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.bookId = str;
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
        this.isHot = z2;
    }

    public final void setHotStr(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.isHotStr = str;
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

    public final void setOpposition(int i2) {
        this.isOpposition = i2;
    }

    public final void setOppositionCount(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.oppositionCount = str;
    }

    public final void setOtherView(int i2) {
        this.otherView = i2;
    }

    public final void setParagraphContent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.paragraphContent = str;
    }

    public final void setParentId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.parentId = str;
    }

    public final void setPicture(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.picture = str;
    }

    public final void setRate(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.rate = str;
    }

    public final void setReplyCount(int i2) {
        this.replyCount = i2;
    }

    public final void setReplyList(@NotNull List<BookReview> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.replyList = list;
    }

    public final void setReplyPrimaryId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.replyPrimaryId = str;
    }

    public final void setReviewType(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.reviewType = str;
    }

    public final void setSchool(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.school = str;
    }

    public final void setShowName(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.showName = str;
    }

    public final void setSupport(int i2) {
        this.isSupport = i2;
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

    public final void setTmpPosition(int i2) {
        this.tmpPosition = i2;
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

    public final void set_showValue(boolean z2) {
        this.is_showValue = z2;
    }

    public final void set_zhankai(boolean z2) {
        this.is_zhankai = z2;
    }

    @NotNull
    public String toString() {
        return "BookReview(suspend=" + this.suspend + ", avatar=" + this.avatar + ", bookId=" + this.bookId + ", comment=" + this.comment + ", ctime=" + this.ctime + ", id=" + this.id + ", isOpposition=" + this.isOpposition + ", isSupport=" + this.isSupport + ", nickname=" + this.nickname + ", oppositionCount=" + this.oppositionCount + ", picture=" + this.picture + ", replyCount=" + this.replyCount + ", school=" + this.school + ", supportCount=" + this.supportCount + ", userId=" + this.userId + ", isVip=" + this.isVip + ", identity=" + this.identity + ", replyList=" + this.replyList + ", reviewType=" + this.reviewType + ", rate=" + this.rate + ", floorNum=" + this.floorNum + ", replyPrimaryId=" + this.replyPrimaryId + ", parentId=" + this.parentId + ", isHot=" + this.isHot + ", type=" + this.type + ", title=" + this.title + ", otherView=" + this.otherView + ", showName=" + this.showName + ", is_logout=" + this.is_logout + ", isActionSupport=" + this.isActionSupport + ", isActionReply=" + this.isActionReply + ", tmpPosition=" + this.tmpPosition + ", is_zhankai=" + this.is_zhankai + ", is_showValue=" + this.is_showValue + ", paragraphContent=" + this.paragraphContent + ", isHotStr=" + this.isHotStr + ')';
    }

    public /* synthetic */ BookReview(boolean z2, String str, String str2, String str3, String str4, String str5, int i2, int i3, String str6, String str7, String str8, int i4, String str9, String str10, String str11, int i5, String str12, List list, String str13, String str14, String str15, String str16, String str17, boolean z3, int i6, String str18, int i7, String str19, String str20, boolean z4, boolean z5, int i8, boolean z6, boolean z7, String str21, String str22, int i9, int i10, DefaultConstructorMarker defaultConstructorMarker) {
        this((i9 & 1) != 0 ? false : z2, (i9 & 2) != 0 ? "" : str, (i9 & 4) != 0 ? "" : str2, (i9 & 8) != 0 ? "" : str3, (i9 & 16) != 0 ? "" : str4, (i9 & 32) != 0 ? "" : str5, (i9 & 64) != 0 ? 0 : i2, (i9 & 128) != 0 ? 0 : i3, (i9 & 256) != 0 ? "" : str6, (i9 & 512) != 0 ? "0" : str7, (i9 & 1024) != 0 ? "" : str8, (i9 & 2048) != 0 ? 0 : i4, (i9 & 4096) != 0 ? "" : str9, (i9 & 8192) != 0 ? "0" : str10, (i9 & 16384) != 0 ? "" : str11, (i9 & 32768) != 0 ? 0 : i5, (i9 & 65536) != 0 ? "" : str12, (i9 & 131072) != 0 ? CollectionsKt__CollectionsKt.emptyList() : list, (i9 & 262144) != 0 ? "1" : str13, (i9 & 524288) != 0 ? "0" : str14, (i9 & 1048576) != 0 ? "0" : str15, (i9 & 2097152) != 0 ? "" : str16, (i9 & 4194304) != 0 ? "" : str17, (i9 & 8388608) != 0 ? false : z3, (i9 & 16777216) != 0 ? 0 : i6, (i9 & 33554432) != 0 ? "" : str18, (i9 & 67108864) != 0 ? 0 : i7, (i9 & 134217728) != 0 ? "" : str19, (i9 & 268435456) != 0 ? "" : str20, (i9 & 536870912) != 0 ? false : z4, (i9 & 1073741824) != 0 ? false : z5, (i9 & Integer.MIN_VALUE) != 0 ? 0 : i8, (i10 & 1) != 0 ? false : z6, (i10 & 2) != 0 ? true : z7, (i10 & 4) != 0 ? "" : str21, (i10 & 8) != 0 ? "" : str22);
    }
}
