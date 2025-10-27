package com.ykb.ebook.model;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import com.huawei.hms.push.HmsMessageService;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.unity3d.splash.services.ads.adunit.AdUnitActivity;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0018\u0002\n\u0002\bx\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001BÁ\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0010\f\u001a\u00020\u0007\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\u0006\u0010\u0010\u001a\u00020\u0007\u0012\u0006\u0010\u0011\u001a\u00020\u0007\u0012\u0006\u0010\u0012\u001a\u00020\u0003\u0012\u0006\u0010\u0013\u001a\u00020\u0003\u0012\u0006\u0010\u0014\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0003\u0012\u0006\u0010\u0016\u001a\u00020\u0003\u0012\u0006\u0010\u0017\u001a\u00020\u0003\u0012\u0006\u0010\u0018\u001a\u00020\u0003\u0012\u0006\u0010\u0019\u001a\u00020\u0003\u0012\u0006\u0010\u001a\u001a\u00020\u0003\u0012\u0006\u0010\u001b\u001a\u00020\u0003\u0012\u0006\u0010\u001c\u001a\u00020\u0003\u0012\u0006\u0010\u001d\u001a\u00020\u0003\u0012\u0006\u0010\u001e\u001a\u00020\u0003\u0012\u0006\u0010\u001f\u001a\u00020\u0003\u0012\u0006\u0010 \u001a\u00020\u0003\u0012\u0006\u0010!\u001a\u00020\u0003\u0012\u0006\u0010\"\u001a\u00020\u0003\u0012\u0006\u0010#\u001a\u00020\u0003\u0012\u0006\u0010$\u001a\u00020\u0003\u0012\u0006\u0010%\u001a\u00020\u0003\u0012\u0006\u0010&\u001a\u00020\u0003\u0012\b\b\u0002\u0010'\u001a\u00020\u0007\u0012\u0006\u0010(\u001a\u00020\u0003\u0012\u0006\u0010)\u001a\u00020\u0003\u0012\b\b\u0002\u0010*\u001a\u00020\u0003\u0012\u0006\u0010+\u001a\u00020,¢\u0006\u0002\u0010-J\t\u0010}\u001a\u00020\u0003HÆ\u0003J\t\u0010~\u001a\u00020\u0003HÆ\u0003J\t\u0010\u007f\u001a\u00020\u0007HÆ\u0003J\n\u0010\u0080\u0001\u001a\u00020\u0007HÆ\u0003J\n\u0010\u0081\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0082\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0083\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0084\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0085\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0086\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0087\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0088\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0089\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u008a\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u008b\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u008c\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u008d\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u008e\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u008f\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0090\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0091\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0092\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0093\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0094\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0095\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0096\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0097\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0098\u0001\u001a\u00020\u0007HÆ\u0003J\n\u0010\u0099\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u009a\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u009b\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u009c\u0001\u001a\u00020,HÆ\u0003J\n\u0010\u009d\u0001\u001a\u00020\u0007HÆ\u0003J\n\u0010\u009e\u0001\u001a\u00020\u0003HÆ\u0003J\u0010\u0010\u009f\u0001\u001a\b\u0012\u0004\u0012\u00020\u000b0\nHÆ\u0003J\n\u0010 \u0001\u001a\u00020\u0007HÆ\u0003J\n\u0010¡\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010¢\u0001\u001a\u00020\u0003HÆ\u0003J\u008c\u0003\u0010£\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\b\b\u0002\u0010\f\u001a\u00020\u00072\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00072\b\b\u0002\u0010\u0011\u001a\u00020\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u00032\b\b\u0002\u0010\u0017\u001a\u00020\u00032\b\b\u0002\u0010\u0018\u001a\u00020\u00032\b\b\u0002\u0010\u0019\u001a\u00020\u00032\b\b\u0002\u0010\u001a\u001a\u00020\u00032\b\b\u0002\u0010\u001b\u001a\u00020\u00032\b\b\u0002\u0010\u001c\u001a\u00020\u00032\b\b\u0002\u0010\u001d\u001a\u00020\u00032\b\b\u0002\u0010\u001e\u001a\u00020\u00032\b\b\u0002\u0010\u001f\u001a\u00020\u00032\b\b\u0002\u0010 \u001a\u00020\u00032\b\b\u0002\u0010!\u001a\u00020\u00032\b\b\u0002\u0010\"\u001a\u00020\u00032\b\b\u0002\u0010#\u001a\u00020\u00032\b\b\u0002\u0010$\u001a\u00020\u00032\b\b\u0002\u0010%\u001a\u00020\u00032\b\b\u0002\u0010&\u001a\u00020\u00032\b\b\u0002\u0010'\u001a\u00020\u00072\b\b\u0002\u0010(\u001a\u00020\u00032\b\b\u0002\u0010)\u001a\u00020\u00032\b\b\u0002\u0010*\u001a\u00020\u00032\b\b\u0002\u0010+\u001a\u00020,HÆ\u0001J\u0016\u0010¤\u0001\u001a\u00030¥\u00012\t\u0010¦\u0001\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\n\u0010§\u0001\u001a\u00020\u0007HÖ\u0001J\n\u0010¨\u0001\u001a\u00020\u0003HÖ\u0001R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010/\"\u0004\b3\u00101R\u001e\u0010\u0005\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u0010/\"\u0004\b5\u00101R\u001e\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001e\u0010\b\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010/\"\u0004\b;\u00101R$\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u001e\u0010\f\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u00107\"\u0004\bA\u00109R\u001e\u0010\r\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010/\"\u0004\bC\u00101R\u001e\u0010\u000e\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bD\u0010/\"\u0004\bE\u00101R\u001e\u0010\u0010\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u00107\"\u0004\bG\u00109R\u001e\u0010\u000f\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010/\"\u0004\bI\u00101R\u001e\u0010\u0011\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u00107\"\u0004\bK\u00109R\u001e\u0010\u0012\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010/\"\u0004\bM\u00101R\u001e\u0010\u0013\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010/\"\u0004\bO\u00101R\u001e\u0010\u0014\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bP\u0010/\"\u0004\bQ\u00101R\u001e\u0010$\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bR\u0010/\"\u0004\bS\u00101R\u001e\u0010\u0015\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bT\u0010/\"\u0004\bU\u00101R\u001e\u0010\u0016\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010/\"\u0004\bV\u00101R\u001e\u0010\u001c\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010/\"\u0004\bW\u00101R\u001e\u0010\u0017\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010/\"\u0004\bX\u00101R\u001e\u0010\u0018\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010/\"\u0004\bY\u00101R\u001e\u0010\u0019\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010/\"\u0004\bZ\u00101R\u001e\u0010&\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b[\u0010/\"\u0004\b\\\u00101R\u001e\u0010%\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b]\u0010/\"\u0004\b^\u00101R\u001a\u0010*\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b_\u0010/\"\u0004\b`\u00101R\u001e\u0010+\u001a\u00020,8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\ba\u0010b\"\u0004\bc\u0010dR\u001e\u0010\u001a\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\be\u0010/\"\u0004\bf\u00101R\u001e\u0010'\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bg\u00107\"\u0004\bh\u00109R\u001e\u0010(\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bi\u0010/\"\u0004\bj\u00101R\u001e\u0010)\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bk\u0010/\"\u0004\bl\u00101R\u001e\u0010\u001b\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bm\u0010/\"\u0004\bn\u00101R\u001e\u0010\u001d\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bo\u0010/\"\u0004\bp\u00101R\u001e\u0010\u001e\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bq\u0010/\"\u0004\br\u00101R\u001e\u0010\u001f\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bs\u0010/\"\u0004\bt\u00101R\u001e\u0010 \u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bu\u0010/\"\u0004\bv\u00101R\u001e\u0010!\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bw\u0010/\"\u0004\bx\u00101R\u001e\u0010\"\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\by\u0010/\"\u0004\bz\u00101R\u001e\u0010#\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b{\u0010/\"\u0004\b|\u00101¨\u0006©\u0001"}, d2 = {"Lcom/ykb/ebook/model/BookInfo;", "", AdUnitActivity.EXTRA_ACTIVITY_ID, "", "appId", SocializeProtocolConstants.AUTHOR, "bookReviewCount", "", "categoryId", "chapterList", "", "Lcom/ykb/ebook/model/Chapter;", "commentCount", "ctime", "describe", "downloadUrl", "downloadCount", "freeChapterCount", "freeEndTime", "freeStartTime", "grade", "id", "isAllowDownload", "isDel", "isIosBuy", "isVipObtain", "price", "readCount", "isBookshelf", "sort", "status", "subjectId", "thumbnail", "title", "utime", "wordCount", "highCommentCount", "middleCommentCount", "lowCommentCount", "rate", "rateComment", "ratePicture", "pass", "perusalDuration", "Lcom/ykb/ebook/model/PerusalDuration;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/util/List;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/ykb/ebook/model/PerusalDuration;)V", "getActivityId", "()Ljava/lang/String;", "setActivityId", "(Ljava/lang/String;)V", "getAppId", "setAppId", "getAuthor", "setAuthor", "getBookReviewCount", "()I", "setBookReviewCount", "(I)V", "getCategoryId", "setCategoryId", "getChapterList", "()Ljava/util/List;", "setChapterList", "(Ljava/util/List;)V", "getCommentCount", "setCommentCount", "getCtime", "setCtime", "getDescribe", "setDescribe", "getDownloadCount", "setDownloadCount", "getDownloadUrl", "setDownloadUrl", "getFreeChapterCount", "setFreeChapterCount", "getFreeEndTime", "setFreeEndTime", "getFreeStartTime", "setFreeStartTime", "getGrade", "setGrade", "getHighCommentCount", "setHighCommentCount", "getId", "setId", "setAllowDownload", "setBookshelf", "setDel", "setIosBuy", "setVipObtain", "getLowCommentCount", "setLowCommentCount", "getMiddleCommentCount", "setMiddleCommentCount", "getPass", "setPass", "getPerusalDuration", "()Lcom/ykb/ebook/model/PerusalDuration;", "setPerusalDuration", "(Lcom/ykb/ebook/model/PerusalDuration;)V", "getPrice", "setPrice", "getRate", "setRate", "getRateComment", "setRateComment", "getRatePicture", "setRatePicture", "getReadCount", "setReadCount", "getSort", "setSort", "getStatus", "setStatus", "getSubjectId", "setSubjectId", "getThumbnail", "setThumbnail", "getTitle", "setTitle", "getUtime", "setUtime", "getWordCount", "setWordCount", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component35", "component36", "component37", "component38", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class BookInfo {

    @SerializedName(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID)
    @NotNull
    private String activityId;

    @SerializedName("app_id")
    @NotNull
    private String appId;

    @SerializedName(SocializeProtocolConstants.AUTHOR)
    @NotNull
    private String author;

    @SerializedName("book_review_count")
    private int bookReviewCount;

    @SerializedName("category_id")
    @NotNull
    private String categoryId;

    @SerializedName("chapter_list")
    @NotNull
    private List<Chapter> chapterList;

    @SerializedName("comment_count")
    private int commentCount;

    @SerializedName("ctime")
    @NotNull
    private String ctime;

    @SerializedName("describe")
    @NotNull
    private String describe;

    @SerializedName("download_count")
    private int downloadCount;

    @SerializedName("download_url")
    @NotNull
    private String downloadUrl;

    @SerializedName("free_chapter_count")
    private int freeChapterCount;

    @SerializedName("free_end_time")
    @NotNull
    private String freeEndTime;

    @SerializedName("free_start_time")
    @NotNull
    private String freeStartTime;

    @SerializedName("grade")
    @NotNull
    private String grade;

    @SerializedName("high_comment_count")
    @NotNull
    private String highCommentCount;

    @SerializedName("id")
    @NotNull
    private String id;

    @SerializedName("is_allow_download")
    @NotNull
    private String isAllowDownload;

    @SerializedName("is_bookshelf")
    @NotNull
    private String isBookshelf;

    @SerializedName("is_del")
    @NotNull
    private String isDel;

    @SerializedName("is_ios_buy")
    @NotNull
    private String isIosBuy;

    @SerializedName("is_vip_obtain")
    @NotNull
    private String isVipObtain;

    @SerializedName("low_comment_count")
    @NotNull
    private String lowCommentCount;

    @SerializedName("middle_comment_count")
    @NotNull
    private String middleCommentCount;

    @NotNull
    private String pass;

    @SerializedName("perusal_duration")
    @NotNull
    private PerusalDuration perusalDuration;

    @SerializedName("price")
    @NotNull
    private String price;

    @SerializedName("rate")
    private int rate;

    @SerializedName("rate_comment")
    @NotNull
    private String rateComment;

    @SerializedName("rate_picture")
    @NotNull
    private String ratePicture;

    @SerializedName("read_count")
    @NotNull
    private String readCount;

    @SerializedName("sort")
    @NotNull
    private String sort;

    @SerializedName("status")
    @NotNull
    private String status;

    @SerializedName(HmsMessageService.SUBJECT_ID)
    @NotNull
    private String subjectId;

    @SerializedName("thumbnail")
    @NotNull
    private String thumbnail;

    @SerializedName("title")
    @NotNull
    private String title;

    @SerializedName("utime")
    @NotNull
    private String utime;

    @SerializedName("word_count")
    @NotNull
    private String wordCount;

    public BookInfo(@NotNull String activityId, @NotNull String appId, @NotNull String author, int i2, @NotNull String categoryId, @NotNull List<Chapter> chapterList, int i3, @NotNull String ctime, @NotNull String describe, @NotNull String downloadUrl, int i4, int i5, @NotNull String freeEndTime, @NotNull String freeStartTime, @NotNull String grade, @NotNull String id, @NotNull String isAllowDownload, @NotNull String isDel, @NotNull String isIosBuy, @NotNull String isVipObtain, @NotNull String price, @NotNull String readCount, @NotNull String isBookshelf, @NotNull String sort, @NotNull String status, @NotNull String subjectId, @NotNull String thumbnail, @NotNull String title, @NotNull String utime, @NotNull String wordCount, @NotNull String highCommentCount, @NotNull String middleCommentCount, @NotNull String lowCommentCount, int i6, @NotNull String rateComment, @NotNull String ratePicture, @NotNull String pass, @NotNull PerusalDuration perusalDuration) {
        Intrinsics.checkNotNullParameter(activityId, "activityId");
        Intrinsics.checkNotNullParameter(appId, "appId");
        Intrinsics.checkNotNullParameter(author, "author");
        Intrinsics.checkNotNullParameter(categoryId, "categoryId");
        Intrinsics.checkNotNullParameter(chapterList, "chapterList");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(describe, "describe");
        Intrinsics.checkNotNullParameter(downloadUrl, "downloadUrl");
        Intrinsics.checkNotNullParameter(freeEndTime, "freeEndTime");
        Intrinsics.checkNotNullParameter(freeStartTime, "freeStartTime");
        Intrinsics.checkNotNullParameter(grade, "grade");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(isAllowDownload, "isAllowDownload");
        Intrinsics.checkNotNullParameter(isDel, "isDel");
        Intrinsics.checkNotNullParameter(isIosBuy, "isIosBuy");
        Intrinsics.checkNotNullParameter(isVipObtain, "isVipObtain");
        Intrinsics.checkNotNullParameter(price, "price");
        Intrinsics.checkNotNullParameter(readCount, "readCount");
        Intrinsics.checkNotNullParameter(isBookshelf, "isBookshelf");
        Intrinsics.checkNotNullParameter(sort, "sort");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(subjectId, "subjectId");
        Intrinsics.checkNotNullParameter(thumbnail, "thumbnail");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(utime, "utime");
        Intrinsics.checkNotNullParameter(wordCount, "wordCount");
        Intrinsics.checkNotNullParameter(highCommentCount, "highCommentCount");
        Intrinsics.checkNotNullParameter(middleCommentCount, "middleCommentCount");
        Intrinsics.checkNotNullParameter(lowCommentCount, "lowCommentCount");
        Intrinsics.checkNotNullParameter(rateComment, "rateComment");
        Intrinsics.checkNotNullParameter(ratePicture, "ratePicture");
        Intrinsics.checkNotNullParameter(pass, "pass");
        Intrinsics.checkNotNullParameter(perusalDuration, "perusalDuration");
        this.activityId = activityId;
        this.appId = appId;
        this.author = author;
        this.bookReviewCount = i2;
        this.categoryId = categoryId;
        this.chapterList = chapterList;
        this.commentCount = i3;
        this.ctime = ctime;
        this.describe = describe;
        this.downloadUrl = downloadUrl;
        this.downloadCount = i4;
        this.freeChapterCount = i5;
        this.freeEndTime = freeEndTime;
        this.freeStartTime = freeStartTime;
        this.grade = grade;
        this.id = id;
        this.isAllowDownload = isAllowDownload;
        this.isDel = isDel;
        this.isIosBuy = isIosBuy;
        this.isVipObtain = isVipObtain;
        this.price = price;
        this.readCount = readCount;
        this.isBookshelf = isBookshelf;
        this.sort = sort;
        this.status = status;
        this.subjectId = subjectId;
        this.thumbnail = thumbnail;
        this.title = title;
        this.utime = utime;
        this.wordCount = wordCount;
        this.highCommentCount = highCommentCount;
        this.middleCommentCount = middleCommentCount;
        this.lowCommentCount = lowCommentCount;
        this.rate = i6;
        this.rateComment = rateComment;
        this.ratePicture = ratePicture;
        this.pass = pass;
        this.perusalDuration = perusalDuration;
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getActivityId() {
        return this.activityId;
    }

    @NotNull
    /* renamed from: component10, reason: from getter */
    public final String getDownloadUrl() {
        return this.downloadUrl;
    }

    /* renamed from: component11, reason: from getter */
    public final int getDownloadCount() {
        return this.downloadCount;
    }

    /* renamed from: component12, reason: from getter */
    public final int getFreeChapterCount() {
        return this.freeChapterCount;
    }

    @NotNull
    /* renamed from: component13, reason: from getter */
    public final String getFreeEndTime() {
        return this.freeEndTime;
    }

    @NotNull
    /* renamed from: component14, reason: from getter */
    public final String getFreeStartTime() {
        return this.freeStartTime;
    }

    @NotNull
    /* renamed from: component15, reason: from getter */
    public final String getGrade() {
        return this.grade;
    }

    @NotNull
    /* renamed from: component16, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    /* renamed from: component17, reason: from getter */
    public final String getIsAllowDownload() {
        return this.isAllowDownload;
    }

    @NotNull
    /* renamed from: component18, reason: from getter */
    public final String getIsDel() {
        return this.isDel;
    }

    @NotNull
    /* renamed from: component19, reason: from getter */
    public final String getIsIosBuy() {
        return this.isIosBuy;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getAppId() {
        return this.appId;
    }

    @NotNull
    /* renamed from: component20, reason: from getter */
    public final String getIsVipObtain() {
        return this.isVipObtain;
    }

    @NotNull
    /* renamed from: component21, reason: from getter */
    public final String getPrice() {
        return this.price;
    }

    @NotNull
    /* renamed from: component22, reason: from getter */
    public final String getReadCount() {
        return this.readCount;
    }

    @NotNull
    /* renamed from: component23, reason: from getter */
    public final String getIsBookshelf() {
        return this.isBookshelf;
    }

    @NotNull
    /* renamed from: component24, reason: from getter */
    public final String getSort() {
        return this.sort;
    }

    @NotNull
    /* renamed from: component25, reason: from getter */
    public final String getStatus() {
        return this.status;
    }

    @NotNull
    /* renamed from: component26, reason: from getter */
    public final String getSubjectId() {
        return this.subjectId;
    }

    @NotNull
    /* renamed from: component27, reason: from getter */
    public final String getThumbnail() {
        return this.thumbnail;
    }

    @NotNull
    /* renamed from: component28, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    /* renamed from: component29, reason: from getter */
    public final String getUtime() {
        return this.utime;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getAuthor() {
        return this.author;
    }

    @NotNull
    /* renamed from: component30, reason: from getter */
    public final String getWordCount() {
        return this.wordCount;
    }

    @NotNull
    /* renamed from: component31, reason: from getter */
    public final String getHighCommentCount() {
        return this.highCommentCount;
    }

    @NotNull
    /* renamed from: component32, reason: from getter */
    public final String getMiddleCommentCount() {
        return this.middleCommentCount;
    }

    @NotNull
    /* renamed from: component33, reason: from getter */
    public final String getLowCommentCount() {
        return this.lowCommentCount;
    }

    /* renamed from: component34, reason: from getter */
    public final int getRate() {
        return this.rate;
    }

    @NotNull
    /* renamed from: component35, reason: from getter */
    public final String getRateComment() {
        return this.rateComment;
    }

    @NotNull
    /* renamed from: component36, reason: from getter */
    public final String getRatePicture() {
        return this.ratePicture;
    }

    @NotNull
    /* renamed from: component37, reason: from getter */
    public final String getPass() {
        return this.pass;
    }

    @NotNull
    /* renamed from: component38, reason: from getter */
    public final PerusalDuration getPerusalDuration() {
        return this.perusalDuration;
    }

    /* renamed from: component4, reason: from getter */
    public final int getBookReviewCount() {
        return this.bookReviewCount;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getCategoryId() {
        return this.categoryId;
    }

    @NotNull
    public final List<Chapter> component6() {
        return this.chapterList;
    }

    /* renamed from: component7, reason: from getter */
    public final int getCommentCount() {
        return this.commentCount;
    }

    @NotNull
    /* renamed from: component8, reason: from getter */
    public final String getCtime() {
        return this.ctime;
    }

    @NotNull
    /* renamed from: component9, reason: from getter */
    public final String getDescribe() {
        return this.describe;
    }

    @NotNull
    public final BookInfo copy(@NotNull String activityId, @NotNull String appId, @NotNull String author, int bookReviewCount, @NotNull String categoryId, @NotNull List<Chapter> chapterList, int commentCount, @NotNull String ctime, @NotNull String describe, @NotNull String downloadUrl, int downloadCount, int freeChapterCount, @NotNull String freeEndTime, @NotNull String freeStartTime, @NotNull String grade, @NotNull String id, @NotNull String isAllowDownload, @NotNull String isDel, @NotNull String isIosBuy, @NotNull String isVipObtain, @NotNull String price, @NotNull String readCount, @NotNull String isBookshelf, @NotNull String sort, @NotNull String status, @NotNull String subjectId, @NotNull String thumbnail, @NotNull String title, @NotNull String utime, @NotNull String wordCount, @NotNull String highCommentCount, @NotNull String middleCommentCount, @NotNull String lowCommentCount, int rate, @NotNull String rateComment, @NotNull String ratePicture, @NotNull String pass, @NotNull PerusalDuration perusalDuration) {
        Intrinsics.checkNotNullParameter(activityId, "activityId");
        Intrinsics.checkNotNullParameter(appId, "appId");
        Intrinsics.checkNotNullParameter(author, "author");
        Intrinsics.checkNotNullParameter(categoryId, "categoryId");
        Intrinsics.checkNotNullParameter(chapterList, "chapterList");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(describe, "describe");
        Intrinsics.checkNotNullParameter(downloadUrl, "downloadUrl");
        Intrinsics.checkNotNullParameter(freeEndTime, "freeEndTime");
        Intrinsics.checkNotNullParameter(freeStartTime, "freeStartTime");
        Intrinsics.checkNotNullParameter(grade, "grade");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(isAllowDownload, "isAllowDownload");
        Intrinsics.checkNotNullParameter(isDel, "isDel");
        Intrinsics.checkNotNullParameter(isIosBuy, "isIosBuy");
        Intrinsics.checkNotNullParameter(isVipObtain, "isVipObtain");
        Intrinsics.checkNotNullParameter(price, "price");
        Intrinsics.checkNotNullParameter(readCount, "readCount");
        Intrinsics.checkNotNullParameter(isBookshelf, "isBookshelf");
        Intrinsics.checkNotNullParameter(sort, "sort");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(subjectId, "subjectId");
        Intrinsics.checkNotNullParameter(thumbnail, "thumbnail");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(utime, "utime");
        Intrinsics.checkNotNullParameter(wordCount, "wordCount");
        Intrinsics.checkNotNullParameter(highCommentCount, "highCommentCount");
        Intrinsics.checkNotNullParameter(middleCommentCount, "middleCommentCount");
        Intrinsics.checkNotNullParameter(lowCommentCount, "lowCommentCount");
        Intrinsics.checkNotNullParameter(rateComment, "rateComment");
        Intrinsics.checkNotNullParameter(ratePicture, "ratePicture");
        Intrinsics.checkNotNullParameter(pass, "pass");
        Intrinsics.checkNotNullParameter(perusalDuration, "perusalDuration");
        return new BookInfo(activityId, appId, author, bookReviewCount, categoryId, chapterList, commentCount, ctime, describe, downloadUrl, downloadCount, freeChapterCount, freeEndTime, freeStartTime, grade, id, isAllowDownload, isDel, isIosBuy, isVipObtain, price, readCount, isBookshelf, sort, status, subjectId, thumbnail, title, utime, wordCount, highCommentCount, middleCommentCount, lowCommentCount, rate, rateComment, ratePicture, pass, perusalDuration);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BookInfo)) {
            return false;
        }
        BookInfo bookInfo = (BookInfo) other;
        return Intrinsics.areEqual(this.activityId, bookInfo.activityId) && Intrinsics.areEqual(this.appId, bookInfo.appId) && Intrinsics.areEqual(this.author, bookInfo.author) && this.bookReviewCount == bookInfo.bookReviewCount && Intrinsics.areEqual(this.categoryId, bookInfo.categoryId) && Intrinsics.areEqual(this.chapterList, bookInfo.chapterList) && this.commentCount == bookInfo.commentCount && Intrinsics.areEqual(this.ctime, bookInfo.ctime) && Intrinsics.areEqual(this.describe, bookInfo.describe) && Intrinsics.areEqual(this.downloadUrl, bookInfo.downloadUrl) && this.downloadCount == bookInfo.downloadCount && this.freeChapterCount == bookInfo.freeChapterCount && Intrinsics.areEqual(this.freeEndTime, bookInfo.freeEndTime) && Intrinsics.areEqual(this.freeStartTime, bookInfo.freeStartTime) && Intrinsics.areEqual(this.grade, bookInfo.grade) && Intrinsics.areEqual(this.id, bookInfo.id) && Intrinsics.areEqual(this.isAllowDownload, bookInfo.isAllowDownload) && Intrinsics.areEqual(this.isDel, bookInfo.isDel) && Intrinsics.areEqual(this.isIosBuy, bookInfo.isIosBuy) && Intrinsics.areEqual(this.isVipObtain, bookInfo.isVipObtain) && Intrinsics.areEqual(this.price, bookInfo.price) && Intrinsics.areEqual(this.readCount, bookInfo.readCount) && Intrinsics.areEqual(this.isBookshelf, bookInfo.isBookshelf) && Intrinsics.areEqual(this.sort, bookInfo.sort) && Intrinsics.areEqual(this.status, bookInfo.status) && Intrinsics.areEqual(this.subjectId, bookInfo.subjectId) && Intrinsics.areEqual(this.thumbnail, bookInfo.thumbnail) && Intrinsics.areEqual(this.title, bookInfo.title) && Intrinsics.areEqual(this.utime, bookInfo.utime) && Intrinsics.areEqual(this.wordCount, bookInfo.wordCount) && Intrinsics.areEqual(this.highCommentCount, bookInfo.highCommentCount) && Intrinsics.areEqual(this.middleCommentCount, bookInfo.middleCommentCount) && Intrinsics.areEqual(this.lowCommentCount, bookInfo.lowCommentCount) && this.rate == bookInfo.rate && Intrinsics.areEqual(this.rateComment, bookInfo.rateComment) && Intrinsics.areEqual(this.ratePicture, bookInfo.ratePicture) && Intrinsics.areEqual(this.pass, bookInfo.pass) && Intrinsics.areEqual(this.perusalDuration, bookInfo.perusalDuration);
    }

    @NotNull
    public final String getActivityId() {
        return this.activityId;
    }

    @NotNull
    public final String getAppId() {
        return this.appId;
    }

    @NotNull
    public final String getAuthor() {
        return this.author;
    }

    public final int getBookReviewCount() {
        return this.bookReviewCount;
    }

    @NotNull
    public final String getCategoryId() {
        return this.categoryId;
    }

    @NotNull
    public final List<Chapter> getChapterList() {
        return this.chapterList;
    }

    public final int getCommentCount() {
        return this.commentCount;
    }

    @NotNull
    public final String getCtime() {
        return this.ctime;
    }

    @NotNull
    public final String getDescribe() {
        return this.describe;
    }

    public final int getDownloadCount() {
        return this.downloadCount;
    }

    @NotNull
    public final String getDownloadUrl() {
        return this.downloadUrl;
    }

    public final int getFreeChapterCount() {
        return this.freeChapterCount;
    }

    @NotNull
    public final String getFreeEndTime() {
        return this.freeEndTime;
    }

    @NotNull
    public final String getFreeStartTime() {
        return this.freeStartTime;
    }

    @NotNull
    public final String getGrade() {
        return this.grade;
    }

    @NotNull
    public final String getHighCommentCount() {
        return this.highCommentCount;
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final String getLowCommentCount() {
        return this.lowCommentCount;
    }

    @NotNull
    public final String getMiddleCommentCount() {
        return this.middleCommentCount;
    }

    @NotNull
    public final String getPass() {
        return this.pass;
    }

    @NotNull
    public final PerusalDuration getPerusalDuration() {
        return this.perusalDuration;
    }

    @NotNull
    public final String getPrice() {
        return this.price;
    }

    public final int getRate() {
        return this.rate;
    }

    @NotNull
    public final String getRateComment() {
        return this.rateComment;
    }

    @NotNull
    public final String getRatePicture() {
        return this.ratePicture;
    }

    @NotNull
    public final String getReadCount() {
        return this.readCount;
    }

    @NotNull
    public final String getSort() {
        return this.sort;
    }

    @NotNull
    public final String getStatus() {
        return this.status;
    }

    @NotNull
    public final String getSubjectId() {
        return this.subjectId;
    }

    @NotNull
    public final String getThumbnail() {
        return this.thumbnail;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final String getUtime() {
        return this.utime;
    }

    @NotNull
    public final String getWordCount() {
        return this.wordCount;
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((this.activityId.hashCode() * 31) + this.appId.hashCode()) * 31) + this.author.hashCode()) * 31) + this.bookReviewCount) * 31) + this.categoryId.hashCode()) * 31) + this.chapterList.hashCode()) * 31) + this.commentCount) * 31) + this.ctime.hashCode()) * 31) + this.describe.hashCode()) * 31) + this.downloadUrl.hashCode()) * 31) + this.downloadCount) * 31) + this.freeChapterCount) * 31) + this.freeEndTime.hashCode()) * 31) + this.freeStartTime.hashCode()) * 31) + this.grade.hashCode()) * 31) + this.id.hashCode()) * 31) + this.isAllowDownload.hashCode()) * 31) + this.isDel.hashCode()) * 31) + this.isIosBuy.hashCode()) * 31) + this.isVipObtain.hashCode()) * 31) + this.price.hashCode()) * 31) + this.readCount.hashCode()) * 31) + this.isBookshelf.hashCode()) * 31) + this.sort.hashCode()) * 31) + this.status.hashCode()) * 31) + this.subjectId.hashCode()) * 31) + this.thumbnail.hashCode()) * 31) + this.title.hashCode()) * 31) + this.utime.hashCode()) * 31) + this.wordCount.hashCode()) * 31) + this.highCommentCount.hashCode()) * 31) + this.middleCommentCount.hashCode()) * 31) + this.lowCommentCount.hashCode()) * 31) + this.rate) * 31) + this.rateComment.hashCode()) * 31) + this.ratePicture.hashCode()) * 31) + this.pass.hashCode()) * 31) + this.perusalDuration.hashCode();
    }

    @NotNull
    public final String isAllowDownload() {
        return this.isAllowDownload;
    }

    @NotNull
    public final String isBookshelf() {
        return this.isBookshelf;
    }

    @NotNull
    public final String isDel() {
        return this.isDel;
    }

    @NotNull
    public final String isIosBuy() {
        return this.isIosBuy;
    }

    @NotNull
    public final String isVipObtain() {
        return this.isVipObtain;
    }

    public final void setActivityId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.activityId = str;
    }

    public final void setAllowDownload(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.isAllowDownload = str;
    }

    public final void setAppId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.appId = str;
    }

    public final void setAuthor(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.author = str;
    }

    public final void setBookReviewCount(int i2) {
        this.bookReviewCount = i2;
    }

    public final void setBookshelf(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.isBookshelf = str;
    }

    public final void setCategoryId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.categoryId = str;
    }

    public final void setChapterList(@NotNull List<Chapter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.chapterList = list;
    }

    public final void setCommentCount(int i2) {
        this.commentCount = i2;
    }

    public final void setCtime(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.ctime = str;
    }

    public final void setDel(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.isDel = str;
    }

    public final void setDescribe(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.describe = str;
    }

    public final void setDownloadCount(int i2) {
        this.downloadCount = i2;
    }

    public final void setDownloadUrl(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.downloadUrl = str;
    }

    public final void setFreeChapterCount(int i2) {
        this.freeChapterCount = i2;
    }

    public final void setFreeEndTime(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.freeEndTime = str;
    }

    public final void setFreeStartTime(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.freeStartTime = str;
    }

    public final void setGrade(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.grade = str;
    }

    public final void setHighCommentCount(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.highCommentCount = str;
    }

    public final void setId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final void setIosBuy(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.isIosBuy = str;
    }

    public final void setLowCommentCount(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.lowCommentCount = str;
    }

    public final void setMiddleCommentCount(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.middleCommentCount = str;
    }

    public final void setPass(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.pass = str;
    }

    public final void setPerusalDuration(@NotNull PerusalDuration perusalDuration) {
        Intrinsics.checkNotNullParameter(perusalDuration, "<set-?>");
        this.perusalDuration = perusalDuration;
    }

    public final void setPrice(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.price = str;
    }

    public final void setRate(int i2) {
        this.rate = i2;
    }

    public final void setRateComment(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.rateComment = str;
    }

    public final void setRatePicture(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.ratePicture = str;
    }

    public final void setReadCount(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.readCount = str;
    }

    public final void setSort(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.sort = str;
    }

    public final void setStatus(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.status = str;
    }

    public final void setSubjectId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.subjectId = str;
    }

    public final void setThumbnail(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.thumbnail = str;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final void setUtime(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.utime = str;
    }

    public final void setVipObtain(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.isVipObtain = str;
    }

    public final void setWordCount(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.wordCount = str;
    }

    @NotNull
    public String toString() {
        return "BookInfo(activityId=" + this.activityId + ", appId=" + this.appId + ", author=" + this.author + ", bookReviewCount=" + this.bookReviewCount + ", categoryId=" + this.categoryId + ", chapterList=" + this.chapterList + ", commentCount=" + this.commentCount + ", ctime=" + this.ctime + ", describe=" + this.describe + ", downloadUrl=" + this.downloadUrl + ", downloadCount=" + this.downloadCount + ", freeChapterCount=" + this.freeChapterCount + ", freeEndTime=" + this.freeEndTime + ", freeStartTime=" + this.freeStartTime + ", grade=" + this.grade + ", id=" + this.id + ", isAllowDownload=" + this.isAllowDownload + ", isDel=" + this.isDel + ", isIosBuy=" + this.isIosBuy + ", isVipObtain=" + this.isVipObtain + ", price=" + this.price + ", readCount=" + this.readCount + ", isBookshelf=" + this.isBookshelf + ", sort=" + this.sort + ", status=" + this.status + ", subjectId=" + this.subjectId + ", thumbnail=" + this.thumbnail + ", title=" + this.title + ", utime=" + this.utime + ", wordCount=" + this.wordCount + ", highCommentCount=" + this.highCommentCount + ", middleCommentCount=" + this.middleCommentCount + ", lowCommentCount=" + this.lowCommentCount + ", rate=" + this.rate + ", rateComment=" + this.rateComment + ", ratePicture=" + this.ratePicture + ", pass=" + this.pass + ", perusalDuration=" + this.perusalDuration + ')';
    }

    public /* synthetic */ BookInfo(String str, String str2, String str3, int i2, String str4, List list, int i3, String str5, String str6, String str7, int i4, int i5, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18, String str19, String str20, String str21, String str22, String str23, String str24, String str25, String str26, String str27, String str28, int i6, String str29, String str30, String str31, PerusalDuration perusalDuration, int i7, int i8, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, i2, str4, list, i3, str5, str6, str7, i4, i5, str8, str9, str10, (i7 & 32768) != 0 ? "0" : str11, str12, str13, str14, str15, str16, str17, str18, str19, str20, str21, str22, str23, str24, str25, str26, str27, str28, (i8 & 2) != 0 ? 0 : i6, str29, str30, (i8 & 16) != 0 ? "" : str31, perusalDuration);
    }
}
