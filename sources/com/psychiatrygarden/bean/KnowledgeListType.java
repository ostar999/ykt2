package com.psychiatrygarden.bean;

import com.aliyun.vod.log.core.AliyunLogCommon;
import com.plv.foundationsdk.web.PLVWebview;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.apache.http.cookie.ClientCookie;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\u0001\u0018\u0000 \u000e2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000eB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000f"}, d2 = {"Lcom/psychiatrygarden/bean/KnowledgeListType;", "", "type", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getType", "()Ljava/lang/String;", "ALL", PLVWebview.MESSAGE_ERROR, "COLLECTION", "NOTE", "COMMENT", "PRAISE", "CUT", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public enum KnowledgeListType {
    ALL("all "),
    ERROR("error"),
    COLLECTION("collection"),
    NOTE("note"),
    COMMENT(ClientCookie.COMMENT_ATTR),
    PRAISE("praise"),
    CUT(AliyunLogCommon.SubModule.CUT);


    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String type;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/bean/KnowledgeListType$Companion;", "", "()V", "getKnowledgeListType", "Lcom/psychiatrygarden/bean/KnowledgeListType;", "type", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nKnowledgeMapData.kt\nKotlin\n*S Kotlin\n*F\n+ 1 KnowledgeMapData.kt\ncom/psychiatrygarden/bean/KnowledgeListType$Companion\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,66:1\n13579#2,2:67\n*S KotlinDebug\n*F\n+ 1 KnowledgeMapData.kt\ncom/psychiatrygarden/bean/KnowledgeListType$Companion\n*L\n55#1:67,2\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KnowledgeListType getKnowledgeListType(@NotNull String type) {
            Intrinsics.checkNotNullParameter(type, "type");
            for (KnowledgeListType knowledgeListType : KnowledgeListType.values()) {
                if (Intrinsics.areEqual(knowledgeListType.getType(), type)) {
                    return knowledgeListType;
                }
            }
            return KnowledgeListType.ALL;
        }
    }

    KnowledgeListType(String str) {
        this.type = str;
    }

    @NotNull
    public final String getType() {
        return this.type;
    }
}
