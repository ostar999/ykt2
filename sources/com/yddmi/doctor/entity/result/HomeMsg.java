package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.aliyun.vod.common.utils.UriUtil;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Serializable;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b]\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 \u008a\u00012\u00020\u0001:\u0004\u0089\u0001\u008a\u0001B\u00ad\u0002\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u0013\u001a\u00020\u0003\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u001b\u001a\u00020\u0003\u0012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u001d\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u001e\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u001f\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010 \u001a\u00020!\u0012\u0006\u0010\"\u001a\u00020!\u0012\u0006\u0010#\u001a\u00020!\u0012\b\u0010$\u001a\u0004\u0018\u00010%¢\u0006\u0002\u0010&BÁ\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u0007\u0012\b\b\u0002\u0010 \u001a\u00020!\u0012\b\b\u0002\u0010\"\u001a\u00020!\u0012\b\b\u0002\u0010#\u001a\u00020!¢\u0006\u0002\u0010'J\t\u0010_\u001a\u00020\u0003HÆ\u0003J\t\u0010`\u001a\u00020\u0003HÆ\u0003J\u000b\u0010a\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010b\u001a\u00020\u0007HÆ\u0003J\t\u0010c\u001a\u00020\u0007HÆ\u0003J\t\u0010d\u001a\u00020\u0007HÆ\u0003J\t\u0010e\u001a\u00020\u0003HÆ\u0003J\u000b\u0010f\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010g\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010h\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010i\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010j\u001a\u00020\u0003HÆ\u0003J\u000b\u0010k\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010l\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010m\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010n\u001a\u00020\u0003HÆ\u0003J\t\u0010o\u001a\u00020\u0007HÆ\u0003J\t\u0010p\u001a\u00020\u0007HÆ\u0003J\t\u0010q\u001a\u00020\u0007HÆ\u0003J\t\u0010r\u001a\u00020\u0007HÆ\u0003J\t\u0010s\u001a\u00020!HÆ\u0003J\t\u0010t\u001a\u00020!HÆ\u0003J\t\u0010u\u001a\u00020\u0007HÆ\u0003J\t\u0010v\u001a\u00020!HÆ\u0003J\t\u0010w\u001a\u00020\u0007HÆ\u0003J\t\u0010x\u001a\u00020\u0003HÆ\u0003J\t\u0010y\u001a\u00020\u0003HÆ\u0003J\t\u0010z\u001a\u00020\u0003HÆ\u0003J\t\u0010{\u001a\u00020\u0003HÆ\u0003J\t\u0010|\u001a\u00020\u0007HÆ\u0003JÅ\u0002\u0010}\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u00072\b\b\u0002\u0010\u0011\u001a\u00020\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u00072\b\b\u0002\u0010\u0013\u001a\u00020\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u001b\u001a\u00020\u00032\b\b\u0002\u0010\u001c\u001a\u00020\u00072\b\b\u0002\u0010\u001d\u001a\u00020\u00072\b\b\u0002\u0010\u001e\u001a\u00020\u00072\b\b\u0002\u0010\u001f\u001a\u00020\u00072\b\b\u0002\u0010 \u001a\u00020!2\b\b\u0002\u0010\"\u001a\u00020!2\b\b\u0002\u0010#\u001a\u00020!HÆ\u0001J\u0013\u0010~\u001a\u00020!2\b\u0010\u007f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\n\u0010\u0080\u0001\u001a\u00020\u0003HÖ\u0001J\n\u0010\u0081\u0001\u001a\u00020\u0007HÖ\u0001J(\u0010\u0082\u0001\u001a\u00030\u0083\u00012\u0007\u0010\u0084\u0001\u001a\u00020\u00002\b\u0010\u0085\u0001\u001a\u00030\u0086\u00012\b\u0010\u0087\u0001\u001a\u00030\u0088\u0001HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b*\u0010)R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010,\"\u0004\b0\u0010.R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b1\u0010,R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b2\u0010)R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010,\"\u0004\b4\u0010.R\u001a\u0010\n\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010)\"\u0004\b6\u00107R\u0011\u0010\u001d\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b8\u0010,R\u0011\u0010\u001e\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b9\u0010,R\u0011\u0010\u001f\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b:\u0010,R\u001a\u0010\u000b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010)\"\u0004\b;\u00107R\u001a\u0010 \u001a\u00020!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u001a\u0010#\u001a\u00020!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010=\"\u0004\bA\u0010?R\u001a\u0010\"\u001a\u00020!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010=\"\u0004\bC\u0010?R\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bD\u0010)R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010,\"\u0004\bF\u0010.R\u001a\u0010\u001c\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010,\"\u0004\bH\u0010.R\u001a\u0010\u0012\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010,\"\u0004\bJ\u0010.R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010,\"\u0004\bL\u0010.R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010,\"\u0004\bN\u0010.R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010,\"\u0004\bP\u0010.R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010,\"\u0004\bR\u0010.R\u001a\u0010\u001b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bS\u0010)\"\u0004\bT\u00107R\u001a\u0010\r\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bU\u0010,\"\u0004\bV\u0010.R\u001a\u0010\u000e\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bW\u0010)\"\u0004\bX\u00107R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010,\"\u0004\bZ\u0010.R\u0011\u0010\u0010\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b[\u0010,R\u0011\u0010\u0011\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\\\u0010,R\u001a\u0010\u0013\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b]\u0010)\"\u0004\b^\u00107¨\u0006\u008b\u0001"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeMsg;", "", "seen1", "", UriUtil.QUERY_CATEGORY, "categoryChildren", "content", "", "createTime", "examId", "id", "isRead", "messageType", "title", "type", "typeName", "updateTime", "userId", "releaseTime", "warnStatus", "name", "reply", "coverUrl", "fileUrl", "skipWay", "skipUrl", "showPic", "status", "popupId", "inviterCode", "inviterId", "inviterUrl", "mFullImg", "", "mOneDayShow", "mHalfClick", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IIILjava/lang/String;Ljava/lang/String;IIIILjava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZZLkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(IILjava/lang/String;Ljava/lang/String;IIIILjava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZZ)V", "getCategory", "()I", "getCategoryChildren", "getContent", "()Ljava/lang/String;", "setContent", "(Ljava/lang/String;)V", "getCoverUrl", "setCoverUrl", "getCreateTime", "getExamId", "getFileUrl", "setFileUrl", "getId", "setId", "(I)V", "getInviterCode", "getInviterId", "getInviterUrl", "setRead", "getMFullImg", "()Z", "setMFullImg", "(Z)V", "getMHalfClick", "setMHalfClick", "getMOneDayShow", "setMOneDayShow", "getMessageType", "getName", "setName", "getPopupId", "setPopupId", "getReleaseTime", "setReleaseTime", "getReply", "setReply", "getShowPic", "setShowPic", "getSkipUrl", "setSkipUrl", "getSkipWay", "setSkipWay", "getStatus", "setStatus", "getTitle", "setTitle", "getType", "setType", "getTypeName", "setTypeName", "getUpdateTime", "getUserId", "getWarnStatus", "setWarnStatus", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeMsg {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final int category;
    private final int categoryChildren;

    @NotNull
    private String content;

    @Nullable
    private String coverUrl;

    @NotNull
    private final String createTime;
    private final int examId;

    @Nullable
    private String fileUrl;
    private int id;

    @NotNull
    private final String inviterCode;

    @NotNull
    private final String inviterId;

    @NotNull
    private final String inviterUrl;
    private int isRead;
    private boolean mFullImg;
    private boolean mHalfClick;
    private boolean mOneDayShow;
    private final int messageType;

    @Nullable
    private String name;

    @NotNull
    private String popupId;

    @NotNull
    private String releaseTime;

    @Nullable
    private String reply;

    @Nullable
    private String showPic;

    @Nullable
    private String skipUrl;

    @Nullable
    private String skipWay;
    private int status;

    @NotNull
    private String title;
    private int type;

    @Nullable
    private String typeName;

    @NotNull
    private final String updateTime;

    @NotNull
    private final String userId;
    private int warnStatus;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeMsg$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeMsg> serializer() {
            return HomeMsg$$serializer.INSTANCE;
        }
    }

    public HomeMsg() {
        this(0, 0, (String) null, (String) null, 0, 0, 0, 0, (String) null, 0, (String) null, (String) null, (String) null, (String) null, 0, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, 0, (String) null, (String) null, (String) null, (String) null, false, false, false, LockFreeTaskQueueCore.MAX_CAPACITY_MASK, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeMsg(int i2, int i3, int i4, String str, String str2, int i5, int i6, int i7, int i8, String str3, int i9, String str4, String str5, String str6, String str7, int i10, String str8, String str9, String str10, String str11, String str12, String str13, String str14, int i11, String str15, String str16, String str17, String str18, boolean z2, boolean z3, boolean z4, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HomeMsg$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.category = -1;
        } else {
            this.category = i3;
        }
        if ((i2 & 2) == 0) {
            this.categoryChildren = -1;
        } else {
            this.categoryChildren = i4;
        }
        if ((i2 & 4) == 0) {
            this.content = "";
        } else {
            this.content = str;
        }
        if ((i2 & 8) == 0) {
            this.createTime = "";
        } else {
            this.createTime = str2;
        }
        if ((i2 & 16) == 0) {
            this.examId = -1;
        } else {
            this.examId = i5;
        }
        if ((i2 & 32) == 0) {
            this.id = -1;
        } else {
            this.id = i6;
        }
        if ((i2 & 64) == 0) {
            this.isRead = 0;
        } else {
            this.isRead = i7;
        }
        if ((i2 & 128) == 0) {
            this.messageType = -1;
        } else {
            this.messageType = i8;
        }
        if ((i2 & 256) == 0) {
            this.title = "";
        } else {
            this.title = str3;
        }
        if ((i2 & 512) == 0) {
            this.type = -1;
        } else {
            this.type = i9;
        }
        if ((i2 & 1024) == 0) {
            this.typeName = "";
        } else {
            this.typeName = str4;
        }
        if ((i2 & 2048) == 0) {
            this.updateTime = "";
        } else {
            this.updateTime = str5;
        }
        if ((i2 & 4096) == 0) {
            this.userId = "";
        } else {
            this.userId = str6;
        }
        if ((i2 & 8192) == 0) {
            this.releaseTime = "";
        } else {
            this.releaseTime = str7;
        }
        if ((i2 & 16384) == 0) {
            this.warnStatus = -1;
        } else {
            this.warnStatus = i10;
        }
        if ((32768 & i2) == 0) {
            this.name = "";
        } else {
            this.name = str8;
        }
        if ((65536 & i2) == 0) {
            this.reply = "";
        } else {
            this.reply = str9;
        }
        if ((131072 & i2) == 0) {
            this.coverUrl = "";
        } else {
            this.coverUrl = str10;
        }
        if ((262144 & i2) == 0) {
            this.fileUrl = "";
        } else {
            this.fileUrl = str11;
        }
        if ((524288 & i2) == 0) {
            this.skipWay = "";
        } else {
            this.skipWay = str12;
        }
        if ((1048576 & i2) == 0) {
            this.skipUrl = "";
        } else {
            this.skipUrl = str13;
        }
        if ((2097152 & i2) == 0) {
            this.showPic = "";
        } else {
            this.showPic = str14;
        }
        if ((4194304 & i2) == 0) {
            this.status = -1;
        } else {
            this.status = i11;
        }
        if ((8388608 & i2) == 0) {
            this.popupId = "";
        } else {
            this.popupId = str15;
        }
        if ((16777216 & i2) == 0) {
            this.inviterCode = "";
        } else {
            this.inviterCode = str16;
        }
        if ((33554432 & i2) == 0) {
            this.inviterId = "";
        } else {
            this.inviterId = str17;
        }
        if ((67108864 & i2) == 0) {
            this.inviterUrl = "";
        } else {
            this.inviterUrl = str18;
        }
        if ((134217728 & i2) == 0) {
            this.mFullImg = false;
        } else {
            this.mFullImg = z2;
        }
        if ((268435456 & i2) == 0) {
            this.mOneDayShow = false;
        } else {
            this.mOneDayShow = z3;
        }
        if ((i2 & 536870912) == 0) {
            this.mHalfClick = false;
        } else {
            this.mHalfClick = z4;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeMsg self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.category != -1) {
            output.encodeIntElement(serialDesc, 0, self.category);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.categoryChildren != -1) {
            output.encodeIntElement(serialDesc, 1, self.categoryChildren);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.content, "")) {
            output.encodeStringElement(serialDesc, 2, self.content);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.createTime, "")) {
            output.encodeStringElement(serialDesc, 3, self.createTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.examId != -1) {
            output.encodeIntElement(serialDesc, 4, self.examId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || self.id != -1) {
            output.encodeIntElement(serialDesc, 5, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || self.isRead != 0) {
            output.encodeIntElement(serialDesc, 6, self.isRead);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || self.messageType != -1) {
            output.encodeIntElement(serialDesc, 7, self.messageType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.title, "")) {
            output.encodeStringElement(serialDesc, 8, self.title);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || self.type != -1) {
            output.encodeIntElement(serialDesc, 9, self.type);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || !Intrinsics.areEqual(self.typeName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 10, StringSerializer.INSTANCE, self.typeName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || !Intrinsics.areEqual(self.updateTime, "")) {
            output.encodeStringElement(serialDesc, 11, self.updateTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || !Intrinsics.areEqual(self.userId, "")) {
            output.encodeStringElement(serialDesc, 12, self.userId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 13) || !Intrinsics.areEqual(self.releaseTime, "")) {
            output.encodeStringElement(serialDesc, 13, self.releaseTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 14) || self.warnStatus != -1) {
            output.encodeIntElement(serialDesc, 14, self.warnStatus);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 15) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 15, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 16) || !Intrinsics.areEqual(self.reply, "")) {
            output.encodeNullableSerializableElement(serialDesc, 16, StringSerializer.INSTANCE, self.reply);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 17) || !Intrinsics.areEqual(self.coverUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 17, StringSerializer.INSTANCE, self.coverUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 18) || !Intrinsics.areEqual(self.fileUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 18, StringSerializer.INSTANCE, self.fileUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 19) || !Intrinsics.areEqual(self.skipWay, "")) {
            output.encodeNullableSerializableElement(serialDesc, 19, StringSerializer.INSTANCE, self.skipWay);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 20) || !Intrinsics.areEqual(self.skipUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 20, StringSerializer.INSTANCE, self.skipUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 21) || !Intrinsics.areEqual(self.showPic, "")) {
            output.encodeNullableSerializableElement(serialDesc, 21, StringSerializer.INSTANCE, self.showPic);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 22) || self.status != -1) {
            output.encodeIntElement(serialDesc, 22, self.status);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 23) || !Intrinsics.areEqual(self.popupId, "")) {
            output.encodeStringElement(serialDesc, 23, self.popupId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 24) || !Intrinsics.areEqual(self.inviterCode, "")) {
            output.encodeStringElement(serialDesc, 24, self.inviterCode);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 25) || !Intrinsics.areEqual(self.inviterId, "")) {
            output.encodeStringElement(serialDesc, 25, self.inviterId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 26) || !Intrinsics.areEqual(self.inviterUrl, "")) {
            output.encodeStringElement(serialDesc, 26, self.inviterUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 27) || self.mFullImg) {
            output.encodeBooleanElement(serialDesc, 27, self.mFullImg);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 28) || self.mOneDayShow) {
            output.encodeBooleanElement(serialDesc, 28, self.mOneDayShow);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 29) || self.mHalfClick) {
            output.encodeBooleanElement(serialDesc, 29, self.mHalfClick);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getCategory() {
        return this.category;
    }

    /* renamed from: component10, reason: from getter */
    public final int getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getTypeName() {
        return this.typeName;
    }

    @NotNull
    /* renamed from: component12, reason: from getter */
    public final String getUpdateTime() {
        return this.updateTime;
    }

    @NotNull
    /* renamed from: component13, reason: from getter */
    public final String getUserId() {
        return this.userId;
    }

    @NotNull
    /* renamed from: component14, reason: from getter */
    public final String getReleaseTime() {
        return this.releaseTime;
    }

    /* renamed from: component15, reason: from getter */
    public final int getWarnStatus() {
        return this.warnStatus;
    }

    @Nullable
    /* renamed from: component16, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component17, reason: from getter */
    public final String getReply() {
        return this.reply;
    }

    @Nullable
    /* renamed from: component18, reason: from getter */
    public final String getCoverUrl() {
        return this.coverUrl;
    }

    @Nullable
    /* renamed from: component19, reason: from getter */
    public final String getFileUrl() {
        return this.fileUrl;
    }

    /* renamed from: component2, reason: from getter */
    public final int getCategoryChildren() {
        return this.categoryChildren;
    }

    @Nullable
    /* renamed from: component20, reason: from getter */
    public final String getSkipWay() {
        return this.skipWay;
    }

    @Nullable
    /* renamed from: component21, reason: from getter */
    public final String getSkipUrl() {
        return this.skipUrl;
    }

    @Nullable
    /* renamed from: component22, reason: from getter */
    public final String getShowPic() {
        return this.showPic;
    }

    /* renamed from: component23, reason: from getter */
    public final int getStatus() {
        return this.status;
    }

    @NotNull
    /* renamed from: component24, reason: from getter */
    public final String getPopupId() {
        return this.popupId;
    }

    @NotNull
    /* renamed from: component25, reason: from getter */
    public final String getInviterCode() {
        return this.inviterCode;
    }

    @NotNull
    /* renamed from: component26, reason: from getter */
    public final String getInviterId() {
        return this.inviterId;
    }

    @NotNull
    /* renamed from: component27, reason: from getter */
    public final String getInviterUrl() {
        return this.inviterUrl;
    }

    /* renamed from: component28, reason: from getter */
    public final boolean getMFullImg() {
        return this.mFullImg;
    }

    /* renamed from: component29, reason: from getter */
    public final boolean getMOneDayShow() {
        return this.mOneDayShow;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getContent() {
        return this.content;
    }

    /* renamed from: component30, reason: from getter */
    public final boolean getMHalfClick() {
        return this.mHalfClick;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getCreateTime() {
        return this.createTime;
    }

    /* renamed from: component5, reason: from getter */
    public final int getExamId() {
        return this.examId;
    }

    /* renamed from: component6, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component7, reason: from getter */
    public final int getIsRead() {
        return this.isRead;
    }

    /* renamed from: component8, reason: from getter */
    public final int getMessageType() {
        return this.messageType;
    }

    @NotNull
    /* renamed from: component9, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final HomeMsg copy(int category, int categoryChildren, @NotNull String content, @NotNull String createTime, int examId, int id, int isRead, int messageType, @NotNull String title, int type, @Nullable String typeName, @NotNull String updateTime, @NotNull String userId, @NotNull String releaseTime, int warnStatus, @Nullable String name, @Nullable String reply, @Nullable String coverUrl, @Nullable String fileUrl, @Nullable String skipWay, @Nullable String skipUrl, @Nullable String showPic, int status, @NotNull String popupId, @NotNull String inviterCode, @NotNull String inviterId, @NotNull String inviterUrl, boolean mFullImg, boolean mOneDayShow, boolean mHalfClick) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(createTime, "createTime");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(updateTime, "updateTime");
        Intrinsics.checkNotNullParameter(userId, "userId");
        Intrinsics.checkNotNullParameter(releaseTime, "releaseTime");
        Intrinsics.checkNotNullParameter(popupId, "popupId");
        Intrinsics.checkNotNullParameter(inviterCode, "inviterCode");
        Intrinsics.checkNotNullParameter(inviterId, "inviterId");
        Intrinsics.checkNotNullParameter(inviterUrl, "inviterUrl");
        return new HomeMsg(category, categoryChildren, content, createTime, examId, id, isRead, messageType, title, type, typeName, updateTime, userId, releaseTime, warnStatus, name, reply, coverUrl, fileUrl, skipWay, skipUrl, showPic, status, popupId, inviterCode, inviterId, inviterUrl, mFullImg, mOneDayShow, mHalfClick);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeMsg)) {
            return false;
        }
        HomeMsg homeMsg = (HomeMsg) other;
        return this.category == homeMsg.category && this.categoryChildren == homeMsg.categoryChildren && Intrinsics.areEqual(this.content, homeMsg.content) && Intrinsics.areEqual(this.createTime, homeMsg.createTime) && this.examId == homeMsg.examId && this.id == homeMsg.id && this.isRead == homeMsg.isRead && this.messageType == homeMsg.messageType && Intrinsics.areEqual(this.title, homeMsg.title) && this.type == homeMsg.type && Intrinsics.areEqual(this.typeName, homeMsg.typeName) && Intrinsics.areEqual(this.updateTime, homeMsg.updateTime) && Intrinsics.areEqual(this.userId, homeMsg.userId) && Intrinsics.areEqual(this.releaseTime, homeMsg.releaseTime) && this.warnStatus == homeMsg.warnStatus && Intrinsics.areEqual(this.name, homeMsg.name) && Intrinsics.areEqual(this.reply, homeMsg.reply) && Intrinsics.areEqual(this.coverUrl, homeMsg.coverUrl) && Intrinsics.areEqual(this.fileUrl, homeMsg.fileUrl) && Intrinsics.areEqual(this.skipWay, homeMsg.skipWay) && Intrinsics.areEqual(this.skipUrl, homeMsg.skipUrl) && Intrinsics.areEqual(this.showPic, homeMsg.showPic) && this.status == homeMsg.status && Intrinsics.areEqual(this.popupId, homeMsg.popupId) && Intrinsics.areEqual(this.inviterCode, homeMsg.inviterCode) && Intrinsics.areEqual(this.inviterId, homeMsg.inviterId) && Intrinsics.areEqual(this.inviterUrl, homeMsg.inviterUrl) && this.mFullImg == homeMsg.mFullImg && this.mOneDayShow == homeMsg.mOneDayShow && this.mHalfClick == homeMsg.mHalfClick;
    }

    public final int getCategory() {
        return this.category;
    }

    public final int getCategoryChildren() {
        return this.categoryChildren;
    }

    @NotNull
    public final String getContent() {
        return this.content;
    }

    @Nullable
    public final String getCoverUrl() {
        return this.coverUrl;
    }

    @NotNull
    public final String getCreateTime() {
        return this.createTime;
    }

    public final int getExamId() {
        return this.examId;
    }

    @Nullable
    public final String getFileUrl() {
        return this.fileUrl;
    }

    public final int getId() {
        return this.id;
    }

    @NotNull
    public final String getInviterCode() {
        return this.inviterCode;
    }

    @NotNull
    public final String getInviterId() {
        return this.inviterId;
    }

    @NotNull
    public final String getInviterUrl() {
        return this.inviterUrl;
    }

    public final boolean getMFullImg() {
        return this.mFullImg;
    }

    public final boolean getMHalfClick() {
        return this.mHalfClick;
    }

    public final boolean getMOneDayShow() {
        return this.mOneDayShow;
    }

    public final int getMessageType() {
        return this.messageType;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final String getPopupId() {
        return this.popupId;
    }

    @NotNull
    public final String getReleaseTime() {
        return this.releaseTime;
    }

    @Nullable
    public final String getReply() {
        return this.reply;
    }

    @Nullable
    public final String getShowPic() {
        return this.showPic;
    }

    @Nullable
    public final String getSkipUrl() {
        return this.skipUrl;
    }

    @Nullable
    public final String getSkipWay() {
        return this.skipWay;
    }

    public final int getStatus() {
        return this.status;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public final int getType() {
        return this.type;
    }

    @Nullable
    public final String getTypeName() {
        return this.typeName;
    }

    @NotNull
    public final String getUpdateTime() {
        return this.updateTime;
    }

    @NotNull
    public final String getUserId() {
        return this.userId;
    }

    public final int getWarnStatus() {
        return this.warnStatus;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((((((((((((((((((this.category * 31) + this.categoryChildren) * 31) + this.content.hashCode()) * 31) + this.createTime.hashCode()) * 31) + this.examId) * 31) + this.id) * 31) + this.isRead) * 31) + this.messageType) * 31) + this.title.hashCode()) * 31) + this.type) * 31;
        String str = this.typeName;
        int iHashCode2 = (((((((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.updateTime.hashCode()) * 31) + this.userId.hashCode()) * 31) + this.releaseTime.hashCode()) * 31) + this.warnStatus) * 31;
        String str2 = this.name;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.reply;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.coverUrl;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.fileUrl;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.skipWay;
        int iHashCode7 = (iHashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.skipUrl;
        int iHashCode8 = (iHashCode7 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.showPic;
        int iHashCode9 = (((((((((((iHashCode8 + (str8 != null ? str8.hashCode() : 0)) * 31) + this.status) * 31) + this.popupId.hashCode()) * 31) + this.inviterCode.hashCode()) * 31) + this.inviterId.hashCode()) * 31) + this.inviterUrl.hashCode()) * 31;
        boolean z2 = this.mFullImg;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        int i3 = (iHashCode9 + i2) * 31;
        boolean z3 = this.mOneDayShow;
        int i4 = z3;
        if (z3 != 0) {
            i4 = 1;
        }
        int i5 = (i3 + i4) * 31;
        boolean z4 = this.mHalfClick;
        return i5 + (z4 ? 1 : z4 ? 1 : 0);
    }

    public final int isRead() {
        return this.isRead;
    }

    public final void setContent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.content = str;
    }

    public final void setCoverUrl(@Nullable String str) {
        this.coverUrl = str;
    }

    public final void setFileUrl(@Nullable String str) {
        this.fileUrl = str;
    }

    public final void setId(int i2) {
        this.id = i2;
    }

    public final void setMFullImg(boolean z2) {
        this.mFullImg = z2;
    }

    public final void setMHalfClick(boolean z2) {
        this.mHalfClick = z2;
    }

    public final void setMOneDayShow(boolean z2) {
        this.mOneDayShow = z2;
    }

    public final void setName(@Nullable String str) {
        this.name = str;
    }

    public final void setPopupId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.popupId = str;
    }

    public final void setRead(int i2) {
        this.isRead = i2;
    }

    public final void setReleaseTime(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.releaseTime = str;
    }

    public final void setReply(@Nullable String str) {
        this.reply = str;
    }

    public final void setShowPic(@Nullable String str) {
        this.showPic = str;
    }

    public final void setSkipUrl(@Nullable String str) {
        this.skipUrl = str;
    }

    public final void setSkipWay(@Nullable String str) {
        this.skipWay = str;
    }

    public final void setStatus(int i2) {
        this.status = i2;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final void setType(int i2) {
        this.type = i2;
    }

    public final void setTypeName(@Nullable String str) {
        this.typeName = str;
    }

    public final void setWarnStatus(int i2) {
        this.warnStatus = i2;
    }

    @NotNull
    public String toString() {
        return "HomeMsg(category=" + this.category + ", categoryChildren=" + this.categoryChildren + ", content=" + this.content + ", createTime=" + this.createTime + ", examId=" + this.examId + ", id=" + this.id + ", isRead=" + this.isRead + ", messageType=" + this.messageType + ", title=" + this.title + ", type=" + this.type + ", typeName=" + this.typeName + ", updateTime=" + this.updateTime + ", userId=" + this.userId + ", releaseTime=" + this.releaseTime + ", warnStatus=" + this.warnStatus + ", name=" + this.name + ", reply=" + this.reply + ", coverUrl=" + this.coverUrl + ", fileUrl=" + this.fileUrl + ", skipWay=" + this.skipWay + ", skipUrl=" + this.skipUrl + ", showPic=" + this.showPic + ", status=" + this.status + ", popupId=" + this.popupId + ", inviterCode=" + this.inviterCode + ", inviterId=" + this.inviterId + ", inviterUrl=" + this.inviterUrl + ", mFullImg=" + this.mFullImg + ", mOneDayShow=" + this.mOneDayShow + ", mHalfClick=" + this.mHalfClick + ")";
    }

    public HomeMsg(int i2, int i3, @NotNull String content, @NotNull String createTime, int i4, int i5, int i6, int i7, @NotNull String title, int i8, @Nullable String str, @NotNull String updateTime, @NotNull String userId, @NotNull String releaseTime, int i9, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, int i10, @NotNull String popupId, @NotNull String inviterCode, @NotNull String inviterId, @NotNull String inviterUrl, boolean z2, boolean z3, boolean z4) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(createTime, "createTime");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(updateTime, "updateTime");
        Intrinsics.checkNotNullParameter(userId, "userId");
        Intrinsics.checkNotNullParameter(releaseTime, "releaseTime");
        Intrinsics.checkNotNullParameter(popupId, "popupId");
        Intrinsics.checkNotNullParameter(inviterCode, "inviterCode");
        Intrinsics.checkNotNullParameter(inviterId, "inviterId");
        Intrinsics.checkNotNullParameter(inviterUrl, "inviterUrl");
        this.category = i2;
        this.categoryChildren = i3;
        this.content = content;
        this.createTime = createTime;
        this.examId = i4;
        this.id = i5;
        this.isRead = i6;
        this.messageType = i7;
        this.title = title;
        this.type = i8;
        this.typeName = str;
        this.updateTime = updateTime;
        this.userId = userId;
        this.releaseTime = releaseTime;
        this.warnStatus = i9;
        this.name = str2;
        this.reply = str3;
        this.coverUrl = str4;
        this.fileUrl = str5;
        this.skipWay = str6;
        this.skipUrl = str7;
        this.showPic = str8;
        this.status = i10;
        this.popupId = popupId;
        this.inviterCode = inviterCode;
        this.inviterId = inviterId;
        this.inviterUrl = inviterUrl;
        this.mFullImg = z2;
        this.mOneDayShow = z3;
        this.mHalfClick = z4;
    }

    public /* synthetic */ HomeMsg(int i2, int i3, String str, String str2, int i4, int i5, int i6, int i7, String str3, int i8, String str4, String str5, String str6, String str7, int i9, String str8, String str9, String str10, String str11, String str12, String str13, String str14, int i10, String str15, String str16, String str17, String str18, boolean z2, boolean z3, boolean z4, int i11, DefaultConstructorMarker defaultConstructorMarker) {
        this((i11 & 1) != 0 ? -1 : i2, (i11 & 2) != 0 ? -1 : i3, (i11 & 4) != 0 ? "" : str, (i11 & 8) != 0 ? "" : str2, (i11 & 16) != 0 ? -1 : i4, (i11 & 32) != 0 ? -1 : i5, (i11 & 64) != 0 ? 0 : i6, (i11 & 128) != 0 ? -1 : i7, (i11 & 256) != 0 ? "" : str3, (i11 & 512) != 0 ? -1 : i8, (i11 & 1024) != 0 ? "" : str4, (i11 & 2048) != 0 ? "" : str5, (i11 & 4096) != 0 ? "" : str6, (i11 & 8192) != 0 ? "" : str7, (i11 & 16384) != 0 ? -1 : i9, (i11 & 32768) != 0 ? "" : str8, (i11 & 65536) != 0 ? "" : str9, (i11 & 131072) != 0 ? "" : str10, (i11 & 262144) != 0 ? "" : str11, (i11 & 524288) != 0 ? "" : str12, (i11 & 1048576) != 0 ? "" : str13, (i11 & 2097152) != 0 ? "" : str14, (i11 & 4194304) != 0 ? -1 : i10, (i11 & 8388608) != 0 ? "" : str15, (i11 & 16777216) != 0 ? "" : str16, (i11 & 33554432) != 0 ? "" : str17, (i11 & 67108864) != 0 ? "" : str18, (i11 & 134217728) != 0 ? false : z2, (i11 & 268435456) != 0 ? false : z3, (i11 & 536870912) != 0 ? false : z4);
    }
}
