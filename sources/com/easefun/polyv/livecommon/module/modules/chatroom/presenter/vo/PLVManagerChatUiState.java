package com.easefun.polyv.livecommon.module.modules.chatroom.presenter.vo;

/* loaded from: classes3.dex */
public class PLVManagerChatUiState {
    private boolean canLoadMoreHistoryMessage;
    private boolean isHistoryMessageLoading;
    private int unreadMessageCount;

    public PLVManagerChatUiState() {
        this.unreadMessageCount = 0;
        this.isHistoryMessageLoading = false;
        this.canLoadMoreHistoryMessage = true;
    }

    public PLVManagerChatUiState copy() {
        return new PLVManagerChatUiState(this);
    }

    public int getUnreadMessageCount() {
        return this.unreadMessageCount;
    }

    public boolean isCanLoadMoreHistoryMessage() {
        return this.canLoadMoreHistoryMessage;
    }

    public boolean isHistoryMessageLoading() {
        return this.isHistoryMessageLoading;
    }

    public PLVManagerChatUiState setCanLoadMoreHistoryMessage(boolean canLoadMoreHistoryMessage) {
        this.canLoadMoreHistoryMessage = canLoadMoreHistoryMessage;
        return this;
    }

    public PLVManagerChatUiState setHistoryMessageLoading(boolean historyMessageLoading) {
        this.isHistoryMessageLoading = historyMessageLoading;
        return this;
    }

    public PLVManagerChatUiState setUnreadMessageCount(int unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
        return this;
    }

    public String toString() {
        return "PLVManagerChatUiState{unreadMessageCount=" + this.unreadMessageCount + ", isHistoryMessageLoading=" + this.isHistoryMessageLoading + ", canLoadMoreHistoryMessage=" + this.canLoadMoreHistoryMessage + '}';
    }

    public PLVManagerChatUiState(PLVManagerChatUiState oldState) {
        this.unreadMessageCount = 0;
        this.isHistoryMessageLoading = false;
        this.canLoadMoreHistoryMessage = true;
        this.unreadMessageCount = oldState.unreadMessageCount;
        this.isHistoryMessageLoading = oldState.isHistoryMessageLoading;
        this.canLoadMoreHistoryMessage = oldState.canLoadMoreHistoryMessage;
    }
}
