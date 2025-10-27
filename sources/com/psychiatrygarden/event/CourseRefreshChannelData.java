package com.psychiatrygarden.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\t¨\u0006\n"}, d2 = {"Lcom/psychiatrygarden/event/CourseRefreshChannelData;", "", "channelId", "", "isRefresh", "", "(Ljava/lang/String;Z)V", "getChannelId", "()Ljava/lang/String;", "()Z", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CourseRefreshChannelData {

    @NotNull
    private final String channelId;
    private final boolean isRefresh;

    public CourseRefreshChannelData(@NotNull String channelId, boolean z2) {
        Intrinsics.checkNotNullParameter(channelId, "channelId");
        this.channelId = channelId;
        this.isRefresh = z2;
    }

    @NotNull
    public final String getChannelId() {
        return this.channelId;
    }

    /* renamed from: isRefresh, reason: from getter */
    public final boolean getIsRefresh() {
        return this.isRefresh;
    }
}
