package com.hyphenate.easeui.modules.chat.presenter;

import android.text.TextUtils;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMMessage;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseChatMessagePresenterImpl extends EaseChatMessagePresenter {

    /* renamed from: com.hyphenate.easeui.modules.chat.presenter.EaseChatMessagePresenterImpl$1, reason: invalid class name */
    public class AnonymousClass1 implements EMValueCallBack<EMChatRoom> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(int i2, String str) {
            if (EaseChatMessagePresenterImpl.this.isActive()) {
                EaseChatMessagePresenterImpl.this.mView.joinChatRoomFail(i2, str);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(EMChatRoom eMChatRoom) {
            if (EaseChatMessagePresenterImpl.this.isActive()) {
                EaseChatMessagePresenterImpl.this.mView.joinChatRoomSuccess(eMChatRoom);
            }
        }

        @Override // com.hyphenate.EMValueCallBack
        public void onError(final int i2, final String str) {
            EaseChatMessagePresenterImpl.this.runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.i
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8678c.lambda$onError$1(i2, str);
                }
            });
        }

        @Override // com.hyphenate.EMValueCallBack
        public void onSuccess(final EMChatRoom eMChatRoom) {
            EaseChatMessagePresenterImpl.this.runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.h
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8676c.lambda$onSuccess$0(eMChatRoom);
                }
            });
        }
    }

    /* renamed from: com.hyphenate.easeui.modules.chat.presenter.EaseChatMessagePresenterImpl$2, reason: invalid class name */
    public class AnonymousClass2 implements EMValueCallBack<EMCursorResult<EMMessage>> {
        final /* synthetic */ int val$pageSize;

        public AnonymousClass2(int i2) {
            this.val$pageSize = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(int i2, String str, int i3) {
            if (EaseChatMessagePresenterImpl.this.isActive()) {
                EaseChatMessagePresenterImpl.this.mView.loadMsgFail(i2, str);
                EaseChatMessagePresenterImpl.this.loadLocalMessages(i3);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(EMCursorResult eMCursorResult) {
            if (EaseChatMessagePresenterImpl.this.isActive()) {
                EaseChatMessagePresenterImpl.this.mView.loadServerMsgSuccess((List) eMCursorResult.getData());
            }
        }

        @Override // com.hyphenate.EMValueCallBack
        public void onError(final int i2, final String str) {
            EaseChatMessagePresenterImpl easeChatMessagePresenterImpl = EaseChatMessagePresenterImpl.this;
            final int i3 = this.val$pageSize;
            easeChatMessagePresenterImpl.runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.k
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8683c.lambda$onError$1(i2, str, i3);
                }
            });
        }

        @Override // com.hyphenate.EMValueCallBack
        public void onSuccess(final EMCursorResult<EMMessage> eMCursorResult) {
            EaseChatMessagePresenterImpl.this.conversation.loadMoreMsgFromDB("", this.val$pageSize);
            EaseChatMessagePresenterImpl.this.runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.j
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8681c.lambda$onSuccess$0(eMCursorResult);
                }
            });
        }
    }

    /* renamed from: com.hyphenate.easeui.modules.chat.presenter.EaseChatMessagePresenterImpl$3, reason: invalid class name */
    public class AnonymousClass3 implements EMValueCallBack<EMCursorResult<EMMessage>> {
        final /* synthetic */ String val$msgId;
        final /* synthetic */ int val$pageSize;

        public AnonymousClass3(String str, int i2) {
            this.val$msgId = str;
            this.val$pageSize = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(int i2, String str, String str2, int i3) {
            if (EaseChatMessagePresenterImpl.this.isActive()) {
                EaseChatMessagePresenterImpl.this.mView.loadMsgFail(i2, str);
                EaseChatMessagePresenterImpl.this.loadMoreLocalMessages(str2, i3);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(EMCursorResult eMCursorResult) {
            if (EaseChatMessagePresenterImpl.this.isActive()) {
                EaseChatMessagePresenterImpl.this.mView.loadMoreServerMsgSuccess((List) eMCursorResult.getData());
            }
        }

        @Override // com.hyphenate.EMValueCallBack
        public void onError(final int i2, final String str) {
            EaseChatMessagePresenterImpl easeChatMessagePresenterImpl = EaseChatMessagePresenterImpl.this;
            final String str2 = this.val$msgId;
            final int i3 = this.val$pageSize;
            easeChatMessagePresenterImpl.runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.m
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8689c.lambda$onError$1(i2, str, str2, i3);
                }
            });
        }

        @Override // com.hyphenate.EMValueCallBack
        public void onSuccess(final EMCursorResult<EMMessage> eMCursorResult) {
            EaseChatMessagePresenterImpl.this.conversation.loadMoreMsgFromDB(this.val$msgId, this.val$pageSize);
            EaseChatMessagePresenterImpl.this.runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.l
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8687c.lambda$onSuccess$0(eMCursorResult);
                }
            });
        }
    }

    private void checkMessageStatus(List<EMMessage> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (EMMessage eMMessage : list) {
            if (eMMessage.status() != EMMessage.Status.SUCCESS) {
                EMMessage.Status status = eMMessage.status();
                EMMessage.Status status2 = EMMessage.Status.FAIL;
                if (status != status2) {
                    eMMessage.setStatus(status2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadLocalMessages$0() {
        this.mView.loadNoLocalMsg();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadLocalMessages$1(List list) {
        this.mView.loadLocalMsgSuccess(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadMoreLocalHistoryMessages$4(List list, EMConversation.EMSearchDirection eMSearchDirection) {
        if (list == null || list.isEmpty()) {
            this.mView.loadNoMoreLocalHistoryMsg();
        } else {
            this.mView.loadMoreLocalHistoryMsgSuccess(list, eMSearchDirection);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadMoreLocalMessages$2() {
        this.mView.loadNoMoreLocalMsg();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadMoreLocalMessages$3(List list) {
        this.mView.loadMoreLocalMsgSuccess(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshCurrentConversation$5(List list) {
        this.mView.refreshCurrentConSuccess(list, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshToLatest$6(List list) {
        this.mView.refreshCurrentConSuccess(list, true);
    }

    public boolean isMessageId(String str) {
        return TextUtils.isEmpty(str) || this.conversation.getMessage(str, true) != null;
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseChatMessagePresenter
    public void joinChatRoom(String str) {
        EMClient.getInstance().chatroomManager().joinChatRoom(str, new AnonymousClass1());
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseChatMessagePresenter
    public void loadLocalMessages(int i2) {
        EMConversation eMConversation = this.conversation;
        if (eMConversation == null) {
            throw new NullPointerException("should first set up with conversation");
        }
        final List<EMMessage> listLoadMoreMsgFromDB = null;
        try {
            listLoadMoreMsgFromDB = eMConversation.loadMoreMsgFromDB(null, i2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (listLoadMoreMsgFromDB == null || listLoadMoreMsgFromDB.isEmpty()) {
            if (isActive()) {
                runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.f
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f8673c.lambda$loadLocalMessages$0();
                    }
                });
            }
        } else if (isActive()) {
            checkMessageStatus(listLoadMoreMsgFromDB);
            runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.g
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8674c.lambda$loadLocalMessages$1(listLoadMoreMsgFromDB);
                }
            });
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseChatMessagePresenter
    public void loadMoreLocalHistoryMessages(String str, int i2, final EMConversation.EMSearchDirection eMSearchDirection) {
        if (this.conversation == null) {
            throw new NullPointerException("should first set up with conversation");
        }
        if (!isMessageId(str)) {
            throw new IllegalArgumentException("please check if set correct msg id");
        }
        final List<EMMessage> listSearchMsgFromDB = this.conversation.searchMsgFromDB(this.conversation.getMessage(str, true).getMsgTime() - 1, i2, eMSearchDirection);
        if (isActive()) {
            runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.b
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8665c.lambda$loadMoreLocalHistoryMessages$4(listSearchMsgFromDB, eMSearchDirection);
                }
            });
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseChatMessagePresenter
    public void loadMoreLocalMessages(String str, int i2) {
        final List<EMMessage> listLoadMoreMsgFromDB;
        if (this.conversation == null) {
            throw new NullPointerException("should first set up with conversation");
        }
        if (!isMessageId(str)) {
            throw new IllegalArgumentException("please check if set correct msg id");
        }
        try {
            listLoadMoreMsgFromDB = this.conversation.loadMoreMsgFromDB(str, i2);
        } catch (Exception e2) {
            e2.printStackTrace();
            listLoadMoreMsgFromDB = null;
        }
        if (listLoadMoreMsgFromDB == null || listLoadMoreMsgFromDB.isEmpty()) {
            if (isActive()) {
                runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.d
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f8670c.lambda$loadMoreLocalMessages$2();
                    }
                });
            }
        } else if (isActive()) {
            checkMessageStatus(listLoadMoreMsgFromDB);
            runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.e
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8671c.lambda$loadMoreLocalMessages$3(listLoadMoreMsgFromDB);
                }
            });
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseChatMessagePresenter
    public void loadMoreServerMessages(String str, int i2) {
        if (this.conversation == null) {
            throw new NullPointerException("should first set up with conversation");
        }
        if (!isMessageId(str)) {
            throw new IllegalArgumentException("please check if set correct msg id");
        }
        EMClient.getInstance().chatManager().asyncFetchHistoryMessage(this.conversation.conversationId(), this.conversation.getType(), i2, str, new AnonymousClass3(str, i2));
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseChatMessagePresenter
    public void loadServerMessages(int i2) {
        if (this.conversation == null) {
            throw new NullPointerException("should first set up with conversation");
        }
        EMClient.getInstance().chatManager().asyncFetchHistoryMessage(this.conversation.conversationId(), this.conversation.getType(), i2, "", new AnonymousClass2(i2));
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseChatMessagePresenter
    public void refreshCurrentConversation() {
        EMConversation eMConversation = this.conversation;
        if (eMConversation == null) {
            throw new NullPointerException("should first set up with conversation");
        }
        eMConversation.markAllMessagesAsRead();
        final List<EMMessage> allMessages = this.conversation.getAllMessages();
        if (isActive()) {
            runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.a
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8661c.lambda$refreshCurrentConversation$5(allMessages);
                }
            });
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.EaseChatMessagePresenter
    public void refreshToLatest() {
        EMConversation eMConversation = this.conversation;
        if (eMConversation == null) {
            throw new NullPointerException("should first set up with conversation");
        }
        eMConversation.markAllMessagesAsRead();
        final List<EMMessage> allMessages = this.conversation.getAllMessages();
        if (isActive()) {
            runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.presenter.c
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8668c.lambda$refreshToLatest$6(allMessages);
                }
            });
        }
    }
}
