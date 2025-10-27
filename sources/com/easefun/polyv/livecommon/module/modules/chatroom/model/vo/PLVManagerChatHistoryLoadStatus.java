package com.easefun.polyv.livecommon.module.modules.chatroom.model.vo;

/* loaded from: classes3.dex */
public class PLVManagerChatHistoryLoadStatus {
    private boolean canLoadMore;
    private boolean isLoading;

    public PLVManagerChatHistoryLoadStatus() {
        this.isLoading = false;
        this.canLoadMore = true;
    }

    public PLVManagerChatHistoryLoadStatus copy() {
        return new PLVManagerChatHistoryLoadStatus(this);
    }

    public boolean isCanLoadMore() {
        return this.canLoadMore;
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public PLVManagerChatHistoryLoadStatus setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
        return this;
    }

    public PLVManagerChatHistoryLoadStatus setLoading(boolean loading) {
        this.isLoading = loading;
        return this;
    }

    public PLVManagerChatHistoryLoadStatus(PLVManagerChatHistoryLoadStatus oldStatus) {
        this.isLoading = false;
        this.canLoadMore = true;
        this.isLoading = oldStatus.isLoading;
        this.canLoadMore = oldStatus.canLoadMore;
    }
}
