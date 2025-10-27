package com.hyphenate.chat;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.huawei.hms.support.api.push.PushException;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConversationListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.adapter.EMAChatManager;
import com.hyphenate.chat.adapter.EMAChatManagerListener;
import com.hyphenate.chat.adapter.EMAConversation;
import com.hyphenate.chat.adapter.EMAError;
import com.hyphenate.chat.adapter.EMAGroupReadAck;
import com.hyphenate.chat.adapter.EMAMessageReaction;
import com.hyphenate.chat.adapter.EMAMessageReactionChange;
import com.hyphenate.chat.adapter.EMAReactionManager;
import com.hyphenate.chat.adapter.EMAReactionManagerListener;
import com.hyphenate.chat.adapter.message.EMAMessage;
import com.hyphenate.chat.core.EMAdvanceDebugManager;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMFileHelper;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.ImageUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes4.dex */
public class EMChatManager {
    private static final String INTERNAL_ACTION_PREFIX = "em_";
    private static final int LIST_SIZE = 512;
    private static final String TAG = "EMChatManager";
    EMAChatManager emaObject;
    EMAReactionManager emaReactionObject;
    EMClient mClient;
    Map<String, EMConversation.MessageCache> caches = new Hashtable();
    private List<EMMessageListener> messageListeners = new CopyOnWriteArrayList();
    private List<EMConversationListener> conversationListeners = Collections.synchronizedList(new ArrayList());
    EMAChatManagerListener chatManagerListenerImpl = new EMAChatManagerListener() { // from class: com.hyphenate.chat.EMChatManager.1
        @Override // com.hyphenate.chat.adapter.EMAChatManagerListener, com.hyphenate.chat.adapter.EMAChatManagerListenerInterface
        public void onMessageAttachmentsStatusChanged(EMAMessage eMAMessage, EMAError eMAError) {
            try {
                EMMessage eMMessage = new EMMessage(eMAMessage);
                Iterator it = EMChatManager.this.messageListeners.iterator();
                while (it.hasNext()) {
                    ((EMMessageListener) it.next()).onMessageChanged(eMMessage, null);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAChatManagerListener, com.hyphenate.chat.adapter.EMAChatManagerListenerInterface
        public void onMessageStatusChanged(final EMAMessage eMAMessage, EMAError eMAError) {
            EMChatManager.this.mClient.executeOnMainQueue(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.1.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        EMMessage eMMessage = new EMMessage(eMAMessage);
                        Iterator it = EMChatManager.this.messageListeners.iterator();
                        while (it.hasNext()) {
                            ((EMMessageListener) it.next()).onMessageChanged(eMMessage, null);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        }

        @Override // com.hyphenate.chat.adapter.EMAChatManagerListener, com.hyphenate.chat.adapter.EMAChatManagerListenerInterface
        public void onReceiveCmdMessages(final List<EMAMessage> list) {
            EMChatManager.this.mClient.executeOnMainQueue(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.1.2
                @Override // java.lang.Runnable
                public void run() {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        EMMessage eMMessage = new EMMessage((EMAMessage) it.next());
                        String strAction = ((EMCmdMessageBody) eMMessage.getBody()).action();
                        if (EMChatManager.this.isAdvanceDebugMessage(strAction)) {
                            EMAdvanceDebugManager.a().a(eMMessage, EMAdvanceDebugManager.Type.valueOf(strAction));
                        } else {
                            arrayList.add(eMMessage);
                        }
                    }
                    try {
                        Iterator it2 = EMChatManager.this.messageListeners.iterator();
                        while (it2.hasNext()) {
                            ((EMMessageListener) it2.next()).onCmdMessageReceived(arrayList);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        }

        @Override // com.hyphenate.chat.adapter.EMAChatManagerListener, com.hyphenate.chat.adapter.EMAChatManagerListenerInterface
        public void onReceiveHasDeliveredAcks(final List<EMAMessage> list) {
            EMChatManager.this.mClient.executeOnMainQueue(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.1.6
                @Override // java.lang.Runnable
                public void run() {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(new EMMessage((EMAMessage) it.next()));
                    }
                    try {
                        Iterator it2 = EMChatManager.this.messageListeners.iterator();
                        while (it2.hasNext()) {
                            ((EMMessageListener) it2.next()).onMessageDelivered(arrayList);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        }

        @Override // com.hyphenate.chat.adapter.EMAChatManagerListener, com.hyphenate.chat.adapter.EMAChatManagerListenerInterface
        public void onReceiveHasReadAcks(final List<EMAMessage> list) {
            EMChatManager.this.mClient.executeOnMainQueue(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.1.5
                @Override // java.lang.Runnable
                public void run() {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(new EMMessage((EMAMessage) it.next()));
                    }
                    try {
                        Iterator it2 = EMChatManager.this.messageListeners.iterator();
                        while (it2.hasNext()) {
                            ((EMMessageListener) it2.next()).onMessageRead(arrayList);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        }

        @Override // com.hyphenate.chat.adapter.EMAChatManagerListener, com.hyphenate.chat.adapter.EMAChatManagerListenerInterface
        public void onReceiveMessages(final List<EMAMessage> list) {
            EMChatManager.this.mClient.executeOnMainQueue(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.1.1
                @Override // java.lang.Runnable
                public void run() {
                    ArrayList<EMMessage> arrayList = new ArrayList();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(new EMMessage((EMAMessage) it.next()));
                    }
                    ArrayList arrayList2 = new ArrayList();
                    for (EMMessage eMMessage : arrayList) {
                        EMConversation conversation = EMChatManager.this.getConversation(eMMessage.conversationId(), EMConversation.msgType2ConversationType(eMMessage.getFrom(), eMMessage.getChatType()), false);
                        if (conversation == null) {
                            EMLog.d(EMChatManager.TAG, "no conversation");
                        } else {
                            if (eMMessage.getType() != EMMessage.Type.CMD) {
                                conversation.getCache().addMessage(eMMessage);
                            }
                            arrayList2.add(eMMessage);
                        }
                    }
                    if (arrayList2.size() <= 0) {
                        EMLog.d(EMChatManager.TAG, "no remainingMsgs");
                        return;
                    }
                    for (EMMessageListener eMMessageListener : EMChatManager.this.messageListeners) {
                        try {
                            EMLog.d(EMChatManager.TAG, "onMessageReceived： " + eMMessageListener);
                            eMMessageListener.onMessageReceived(arrayList2);
                        } catch (Exception e2) {
                            EMLog.d(EMChatManager.TAG, "onMessageReceived has problem: " + e2.getMessage());
                            e2.printStackTrace();
                        }
                    }
                }
            });
        }

        @Override // com.hyphenate.chat.adapter.EMAChatManagerListener, com.hyphenate.chat.adapter.EMAChatManagerListenerInterface
        public void onReceivePrivateMessages(final List<EMAMessage> list) {
            EMChatManager.this.mClient.executeOnMainQueue(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.1.9
                @Override // java.lang.Runnable
                public void run() {
                    EMLog.d(EMChatManager.TAG, "onPrivateMessages");
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        com.hyphenate.notification.core.b.a().a(new EMMessage((EMAMessage) it.next()));
                    }
                }
            });
        }

        @Override // com.hyphenate.chat.adapter.EMAChatManagerListener, com.hyphenate.chat.adapter.EMAChatManagerListenerInterface
        public void onReceiveReadAckForConversation(final String str, final String str2) {
            EMChatManager.this.mClient.executeOnMainQueue(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.1.7
                @Override // java.lang.Runnable
                public void run() {
                    EMConversation conversation;
                    EMLog.d(EMChatManager.TAG, "onReceiveConversationHasReadAcks");
                    synchronized (EMChatManager.this.conversationListeners) {
                        if (!TextUtils.equals(str, EMClient.getInstance().getCurrentUser()) && (conversation = EMClient.getInstance().chatManager().getConversation(str)) != null) {
                            conversation.loadMoreMsgFromDB(null, conversation.getAllMessages().size());
                        }
                        try {
                            Iterator it = EMChatManager.this.conversationListeners.iterator();
                            while (it.hasNext()) {
                                ((EMConversationListener) it.next()).onConversationRead(str, str2);
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
        }

        @Override // com.hyphenate.chat.adapter.EMAChatManagerListener, com.hyphenate.chat.adapter.EMAChatManagerListenerInterface
        public void onReceiveReadAcksForGroupMessage(final List<EMAGroupReadAck> list) {
            EMChatManager.this.mClient.executeOnMainQueue(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.1.10
                @Override // java.lang.Runnable
                public void run() {
                    EMLog.d(EMChatManager.TAG, "onReceiveReadAcksForGroupMessage");
                    ArrayList arrayList = new ArrayList();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(new EMGroupReadAck((EMAGroupReadAck) it.next()));
                    }
                    try {
                        Iterator it2 = EMChatManager.this.messageListeners.iterator();
                        while (it2.hasNext()) {
                            ((EMMessageListener) it2.next()).onGroupMessageRead(arrayList);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        }

        @Override // com.hyphenate.chat.adapter.EMAChatManagerListener, com.hyphenate.chat.adapter.EMAChatManagerListenerInterface
        public void onReceiveRecallMessages(final List<EMAMessage> list) {
            EMChatManager.this.mClient.executeOnMainQueue(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.1.4
                @Override // java.lang.Runnable
                public void run() {
                    ArrayList arrayList = new ArrayList();
                    for (EMAMessage eMAMessage : list) {
                        arrayList.add(new EMMessage(eMAMessage));
                        EMChatManager.this.getConversation(eMAMessage.conversationId()).getCache().removeMessage(eMAMessage.msgId());
                    }
                    try {
                        Iterator it = EMChatManager.this.messageListeners.iterator();
                        while (it.hasNext()) {
                            ((EMMessageListener) it.next()).onMessageRecalled(arrayList);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        }

        @Override // com.hyphenate.chat.adapter.EMAChatManagerListener, com.hyphenate.chat.adapter.EMAChatManagerListenerInterface
        public void onUpdateConversationList(List<EMAConversation> list) {
            EMChatManager.this.mClient.executeOnMainQueue(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.1.8
                @Override // java.lang.Runnable
                public void run() {
                    EMLog.d(EMChatManager.TAG, "onUpdateConversationList");
                    synchronized (EMChatManager.this.conversationListeners) {
                        try {
                            Iterator it = EMChatManager.this.conversationListeners.iterator();
                            while (it.hasNext()) {
                                ((EMConversationListener) it.next()).onCoversationUpdate();
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
        }

        @Override // com.hyphenate.chat.adapter.EMAChatManagerListener, com.hyphenate.chat.adapter.EMAChatManagerListenerInterface
        public void onUpdateGroupAcks() {
            EMChatManager.this.mClient.executeOnMainQueue(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.1.11
                @Override // java.lang.Runnable
                public void run() {
                    EMLog.d(EMChatManager.TAG, "onUpdateGroupAcks");
                    try {
                        Iterator it = EMChatManager.this.messageListeners.iterator();
                        while (it.hasNext()) {
                            ((EMMessageListener) it.next()).onReadAckForGroupMessageUpdated();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        }
    };
    EMAReactionManagerListener mReactionManagerListenerImpl = new EMAReactionManagerListener() { // from class: com.hyphenate.chat.EMChatManager.2
        @Override // com.hyphenate.chat.adapter.EMAReactionManagerListener, com.hyphenate.chat.adapter.EMAReactionManagerListenerInterface
        public void onMessageReactionDidChange(final List<EMAMessageReactionChange> list) {
            EMChatManager.this.mClient.executeOnMainQueue(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.2.1
                @Override // java.lang.Runnable
                public void run() {
                    EMLog.d(EMChatManager.TAG, "onMessageReactionDidChange");
                    ArrayList arrayList = new ArrayList(list.size());
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(new EMMessageReactionChange((EMAMessageReactionChange) it.next()));
                    }
                    Iterator it2 = EMChatManager.this.messageListeners.iterator();
                    while (it2.hasNext()) {
                        try {
                            ((EMMessageListener) it2.next()).onReactionChanged(arrayList);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
        }
    };

    /* renamed from: com.hyphenate.chat.EMChatManager$15, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass15 {
        static final /* synthetic */ int[] $SwitchMap$com$hyphenate$chat$EMConversation$EMConversationType;
        static final /* synthetic */ int[] $SwitchMap$com$hyphenate$chat$EMMessage$ChatType;

        static {
            int[] iArr = new int[EMMessage.ChatType.values().length];
            $SwitchMap$com$hyphenate$chat$EMMessage$ChatType = iArr;
            try {
                iArr[EMMessage.ChatType.Chat.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$ChatType[EMMessage.ChatType.GroupChat.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$ChatType[EMMessage.ChatType.ChatRoom.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[EMConversation.EMConversationType.values().length];
            $SwitchMap$com$hyphenate$chat$EMConversation$EMConversationType = iArr2;
            try {
                iArr2[EMConversation.EMConversationType.Chat.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMConversation$EMConversationType[EMConversation.EMConversationType.GroupChat.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMConversation$EMConversationType[EMConversation.EMConversationType.ChatRoom.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMConversation$EMConversationType[EMConversation.EMConversationType.DiscussionGroup.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMConversation$EMConversationType[EMConversation.EMConversationType.HelpDesk.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public EMChatManager() {
    }

    public EMChatManager(EMClient eMClient, EMAChatManager eMAChatManager, EMAReactionManager eMAReactionManager) {
        this.mClient = eMClient;
        this.emaObject = eMAChatManager;
        eMAChatManager.addListener(this.chatManagerListenerImpl);
        this.emaReactionObject = eMAReactionManager;
        eMAReactionManager.addListener(this.mReactionManagerListenerImpl);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b6 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0014 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void checkContentAttachmentExist(com.hyphenate.chat.EMMessage r11) {
        /*
            r10 = this;
            T r0 = r11.emaObject
            com.hyphenate.chat.adapter.message.EMAMessage r0 = (com.hyphenate.chat.adapter.message.EMAMessage) r0
            java.util.List r0 = r0.bodies()
            if (r0 == 0) goto Ld2
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto Ld2
            java.util.Iterator r0 = r0.iterator()
        L14:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto Ld2
            java.lang.Object r1 = r0.next()
            com.hyphenate.chat.adapter.message.EMAMessageBody r1 = (com.hyphenate.chat.adapter.message.EMAMessageBody) r1
            int r2 = r1.type()
            r3 = 5
            r4 = 4
            r5 = 2
            r6 = 1
            if (r2 == r6) goto L31
            if (r2 == r5) goto L31
            if (r2 == r4) goto L31
            if (r2 == r3) goto L31
            goto L14
        L31:
            r2 = r1
            com.hyphenate.chat.adapter.message.EMAFileMessageBody r2 = (com.hyphenate.chat.adapter.message.EMAFileMessageBody) r2
            java.lang.String r7 = r2.getLocalUrl()
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "download before check path = "
            r8.append(r9)
            r8.append(r7)
            java.lang.String r8 = r8.toString()
            java.lang.String r9 = "EMChatManager"
            com.hyphenate.util.EMLog.d(r9, r8)
            com.hyphenate.util.EMFileHelper r8 = com.hyphenate.util.EMFileHelper.getInstance()
            boolean r7 = r8.isFileExist(r7)
            if (r7 != 0) goto L14
            java.lang.String r7 = r2.displayName()
            int r1 = r1.type()
            if (r1 == r6) goto L94
            if (r1 == r5) goto L86
            if (r1 == r4) goto L78
            if (r1 == r3) goto L6a
            r1 = 0
            goto Lb0
        L6a:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            com.hyphenate.util.PathUtil r3 = com.hyphenate.util.PathUtil.getInstance()
            java.io.File r3 = r3.getFilePath()
            goto La1
        L78:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            com.hyphenate.util.PathUtil r3 = com.hyphenate.util.PathUtil.getInstance()
            java.io.File r3 = r3.getVoicePath()
            goto La1
        L86:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            com.hyphenate.util.PathUtil r3 = com.hyphenate.util.PathUtil.getInstance()
            java.io.File r3 = r3.getVideoPath()
            goto La1
        L94:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            com.hyphenate.util.PathUtil r3 = com.hyphenate.util.PathUtil.getInstance()
            java.io.File r3 = r3.getImagePath()
        La1:
            r1.append(r3)
            java.lang.String r3 = java.io.File.separator
            r1.append(r3)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
        Lb0:
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 != 0) goto L14
            r2.setLocalPath(r1)
            r10.updateMessage(r11)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "download:create new path , path is "
            r2.append(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            com.hyphenate.util.EMLog.d(r9, r1)
            goto L14
        Ld2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.chat.EMChatManager.checkContentAttachmentExist(com.hyphenate.chat.EMMessage):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleError(EMAError eMAError) throws HyphenateException {
        if (eMAError.errCode() != 0) {
            throw new HyphenateException(eMAError);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAdvanceDebugMessage(String str) {
        if (!str.startsWith(INTERNAL_ACTION_PREFIX)) {
            return false;
        }
        try {
            EMAdvanceDebugManager.Type.valueOf(str);
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$asyncAddReaction$0(String str, String str2, EMCallBack eMCallBack) {
        try {
            addReaction(str, str2);
            if (eMCallBack != null) {
                eMCallBack.onSuccess();
            }
        } catch (HyphenateException e2) {
            EMLog.e(TAG, "asyncAddReaction error code:" + e2.getErrorCode() + ",error message:" + e2.getDescription());
            if (eMCallBack != null) {
                eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$asyncGetReactionDetail$3(EMValueCallBack eMValueCallBack, String str, String str2, String str3, int i2) {
        if (eMValueCallBack != null) {
            try {
                eMValueCallBack.onSuccess(getReactionDetail(str, str2, str3, i2));
            } catch (HyphenateException e2) {
                EMLog.e(TAG, "asyncGetReactionDetail error code:" + e2.getErrorCode() + ",error message:" + e2.getDescription());
                eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$asyncGetReactionList$2(EMValueCallBack eMValueCallBack, List list, EMMessage.ChatType chatType, String str) {
        if (eMValueCallBack != null) {
            try {
                eMValueCallBack.onSuccess(getReactionList(list, chatType, str));
            } catch (HyphenateException e2) {
                EMLog.e(TAG, "asyncGetReactionList error code:" + e2.getErrorCode() + ",error message:" + e2.getDescription());
                eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$asyncRemoveReaction$1(String str, String str2, EMCallBack eMCallBack) {
        try {
            removeReaction(str, str2);
            if (eMCallBack != null) {
                eMCallBack.onSuccess();
            }
        } catch (HyphenateException e2) {
            EMLog.e(TAG, "asyncRemoveReaction error code:" + e2.getErrorCode() + ",error message:" + e2.getDescription());
            if (eMCallBack != null) {
                eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMessageSendCallback(final EMMessage eMMessage, final EMConversation eMConversation, final String str, final String str2) {
        if (eMMessage == null) {
            return;
        }
        eMMessage.setInnerCallback(new EMCallBack() { // from class: com.hyphenate.chat.EMChatManager.4
            @Override // com.hyphenate.EMCallBack
            public void onError(int i2, String str3) {
            }

            @Override // com.hyphenate.EMCallBack
            public void onProgress(int i2, String str3) {
            }

            @Override // com.hyphenate.EMCallBack
            public void onSuccess() {
                EMConversation eMConversation2 = eMConversation;
                if (eMConversation2 != null) {
                    eMConversation2.getCache().removeMessage(str);
                    eMConversation.getCache().addMessage(eMMessage);
                }
                if (str2 != null && EMFileHelper.getInstance().isFileExist(str2) && (eMMessage.getBody() instanceof EMImageMessageBody)) {
                    String localUrl = ((EMImageMessageBody) eMMessage.getBody()).getLocalUrl();
                    EMLog.d(EMChatManager.TAG, "origin: + " + str2 + ", scale:" + localUrl);
                    if (localUrl != null && !localUrl.equals(str2)) {
                        EMLog.d(EMChatManager.TAG, "Deleted the scale image file: " + EMFileHelper.getInstance().deletePrivateFile(localUrl) + " the scale image file path: " + localUrl);
                    }
                    ((EMImageMessageBody) eMMessage.getBody()).setLocalUrl(str2);
                    EMChatManager.this.updateMessage(eMMessage);
                }
            }
        });
    }

    public void ackConversationRead(String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.sendReadAckForConversation(str, eMAError);
        handleError(eMAError);
    }

    public void ackGroupMessageRead(String str, String str2, String str3) throws HyphenateException {
        if (!EMClient.getInstance().getChatConfigPrivate().b().getRequireAck()) {
            EMLog.d(TAG, "chat option reqire ack set to false. skip send out ask msg read");
            return;
        }
        EMAMessage message = this.emaObject.getMessage(str2);
        if (message != null) {
            if (message.isNeedGroupAck()) {
                this.emaObject.sendReadAckForGroupMessage(message, str3);
            } else {
                EMLog.d(TAG, "normal group message, do not ack it");
            }
        }
    }

    public void ackMessageRead(String str, String str2) throws HyphenateException {
        if (!EMClient.getInstance().getChatConfigPrivate().b().getRequireAck()) {
            EMLog.d(TAG, "As the chat option SetRequireAck is set to false, the read receipt is not sent.");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            EMLog.e(TAG, "The to parameter cannot be null.");
            return;
        }
        EMAMessage message = this.emaObject.getMessage(str2);
        if (message == null) {
            message = EMAMessage.createReceiveMessage("", EMMessage.self(), null, EMMessage.ChatType.Chat.ordinal());
            message.setMsgId(str2);
            message.setFrom(str);
            message.setConversationId(str);
        }
        this.emaObject.sendReadAckForMessage(message);
    }

    public void addConversationListener(EMConversationListener eMConversationListener) {
        if (this.conversationListeners.contains(eMConversationListener)) {
            return;
        }
        this.conversationListeners.add(eMConversationListener);
    }

    public void addMessageListener(EMMessageListener eMMessageListener) {
        if (eMMessageListener == null) {
            EMLog.d(TAG, "addMessageListener: listener is null");
            return;
        }
        if (this.messageListeners.contains(eMMessageListener)) {
            return;
        }
        EMLog.d(TAG, "add message listener: " + eMMessageListener);
        this.messageListeners.add(eMMessageListener);
    }

    public void addReaction(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaReactionObject.addReaction(str, str2, eMAError);
        handleError(eMAError);
    }

    public void asyncAddReaction(final String str, final String str2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.b
            @Override // java.lang.Runnable
            public final void run() {
                this.f8516c.lambda$asyncAddReaction$0(str, str2, eMCallBack);
            }
        });
    }

    public void asyncFetchConversationsFromServer(final EMValueCallBack<Map<String, EMConversation>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.6
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatManager.this.fetchConversationsFromServer());
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchGroupReadAcks(final String str, final int i2, final String str2, final EMValueCallBack<EMCursorResult<EMGroupReadAck>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.8
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatManager.this.fetchGroupReadAcks(str, i2, str2));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchHistoryMessage(final String str, final EMConversation.EMConversationType eMConversationType, final int i2, final String str2, final EMValueCallBack<EMCursorResult<EMMessage>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.9
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatManager.this.fetchHistoryMessages(str, eMConversationType, i2, str2));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchHistoryMessage(final String str, final EMConversation.EMConversationType eMConversationType, final int i2, final String str2, final EMConversation.EMSearchDirection eMSearchDirection, final EMValueCallBack<EMCursorResult<EMMessage>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.10
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMChatManager.this.fetchHistoryMessages(str, eMConversationType, i2, str2, eMSearchDirection));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncGetReactionDetail(final String str, final String str2, final String str3, final int i2, final EMValueCallBack<EMCursorResult<EMMessageReaction>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f8575c.lambda$asyncGetReactionDetail$3(eMValueCallBack, str, str2, str3, i2);
            }
        });
    }

    public void asyncGetReactionList(final List<String> list, final EMMessage.ChatType chatType, final String str, final EMValueCallBack<Map<String, List<EMMessageReaction>>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.c
            @Override // java.lang.Runnable
            public final void run() {
                this.f8520c.lambda$asyncGetReactionList$2(eMValueCallBack, list, chatType, str);
            }
        });
    }

    public void asyncRemoveReaction(final String str, final String str2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f8512c.lambda$asyncRemoveReaction$1(str, str2, eMCallBack);
            }
        });
    }

    public void asyncReportMessage(final String str, final String str2, final String str3, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.12
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMChatManager.this.reportMessage(str, str2, str3);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void aysncRecallMessage(final EMMessage eMMessage, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMChatManager.this.recallMessage(eMMessage);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public boolean deleteConversation(String str, boolean z2) {
        EMConversation conversation = getConversation(str);
        if (conversation == null) {
            return false;
        }
        if (z2) {
            conversation.clearAllMessages();
        } else {
            conversation.clear();
        }
        this.emaObject.removeConversation(str, z2, conversation.isChatThread());
        return true;
    }

    public void deleteConversationFromServer(final String str, final EMConversation.EMConversationType eMConversationType, final boolean z2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.7
            @Override // java.lang.Runnable
            public void run() {
                EMAConversation.EMAConversationType eMAConversationType = EMAConversation.EMAConversationType.CHAT;
                int i2 = AnonymousClass15.$SwitchMap$com$hyphenate$chat$EMConversation$EMConversationType[eMConversationType.ordinal()];
                if (i2 != 1) {
                    if (i2 == 2) {
                        eMAConversationType = EMAConversation.EMAConversationType.GROUPCHAT;
                    } else if (i2 == 3) {
                        eMAConversationType = EMAConversation.EMAConversationType.CHATROOM;
                    } else if (i2 == 4) {
                        eMAConversationType = EMAConversation.EMAConversationType.DISCUSSIONGROUP;
                    } else if (i2 == 5) {
                        eMAConversationType = EMAConversation.EMAConversationType.HELPDESK;
                    }
                }
                EMAError eMAErrorDeleteConversationFromServer = EMChatManager.this.emaObject.deleteConversationFromServer(str, eMAConversationType, z2);
                if (eMAErrorDeleteConversationFromServer.errCode() == 0) {
                    eMCallBack.onSuccess();
                } else {
                    eMCallBack.onError(eMAErrorDeleteConversationFromServer.errCode(), eMAErrorDeleteConversationFromServer.errMsg());
                }
            }
        });
    }

    public void deleteMessagesBeforeTimestamp(final long j2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.11
            @Override // java.lang.Runnable
            public void run() {
                if (!EMChatManager.this.emaObject.removeMessagesBeforeTimestamp(j2)) {
                    eMCallBack.onError(3, "Database operation failed");
                    return;
                }
                for (EMConversation.MessageCache messageCache : EMChatManager.this.caches.values()) {
                    for (EMMessage eMMessage : messageCache.getAllMessages()) {
                        if (eMMessage.getMsgTime() < j2) {
                            messageCache.removeMessage(eMMessage.getMsgId());
                        }
                    }
                }
                eMCallBack.onSuccess();
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void downloadAttachment(EMMessage eMMessage) {
        if (eMMessage == null) {
            return;
        }
        eMMessage.makeCallbackStrong();
        checkContentAttachmentExist(eMMessage);
        this.emaObject.downloadMessageAttachments((EMAMessage) eMMessage.emaObject);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void downloadThumbnail(EMMessage eMMessage) {
        eMMessage.makeCallbackStrong();
        this.emaObject.downloadMessageThumbnail((EMAMessage) eMMessage.emaObject);
    }

    public Map<String, EMConversation> fetchConversationsFromServer() throws HyphenateException {
        EMAError eMAError = new EMAError();
        List<EMAConversation> listFetchConversationsFromServer = this.emaObject.fetchConversationsFromServer(eMAError);
        handleError(eMAError);
        Hashtable hashtable = new Hashtable();
        for (EMAConversation eMAConversation : listFetchConversationsFromServer) {
            hashtable.put(eMAConversation.conversationId(), new EMConversation(eMAConversation));
        }
        return hashtable;
    }

    public EMCursorResult<EMGroupReadAck> fetchGroupReadAcks(String str, int i2, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMCursorResult<EMGroupReadAck> eMCursorResult = new EMCursorResult<>();
        EMMessage message = getMessage(str);
        if (message.getChatType() != EMMessage.ChatType.GroupChat || !message.isNeedGroupAck()) {
            EMLog.e(TAG, "not group msg or don't need ack");
            return eMCursorResult;
        }
        EMCursorResult<EMAGroupReadAck> eMCursorResultFetchGroupReadAcks = this.emaObject.fetchGroupReadAcks(str, message.conversationId(), eMAError, i2, str2);
        handleError(eMAError);
        eMCursorResult.setCursor(eMCursorResultFetchGroupReadAcks.getCursor());
        ArrayList arrayList = new ArrayList();
        Iterator it = ((List) eMCursorResultFetchGroupReadAcks.getData()).iterator();
        while (it.hasNext()) {
            arrayList.add(new EMGroupReadAck((EMAGroupReadAck) it.next()));
        }
        eMCursorResult.setData(arrayList);
        return eMCursorResult;
    }

    public EMCursorResult<EMMessage> fetchHistoryMessages(String str, EMConversation.EMConversationType eMConversationType, int i2, String str2) throws HyphenateException {
        return fetchHistoryMessages(str, eMConversationType, i2, str2, EMConversation.EMSearchDirection.UP);
    }

    public EMCursorResult<EMMessage> fetchHistoryMessages(String str, EMConversation.EMConversationType eMConversationType, int i2, String str2, EMConversation.EMSearchDirection eMSearchDirection) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMCursorResult<EMAMessage> eMCursorResultFetchHistoryMessages = this.emaObject.fetchHistoryMessages(str, eMConversationType.ordinal(), i2, str2, eMSearchDirection == EMConversation.EMSearchDirection.UP ? EMAConversation.EMASearchDirection.UP : EMAConversation.EMASearchDirection.DOWN, eMAError);
        handleError(eMAError);
        EMCursorResult<EMMessage> eMCursorResult = new EMCursorResult<>();
        eMCursorResult.setCursor(eMCursorResultFetchHistoryMessages.getCursor());
        ArrayList arrayList = new ArrayList();
        Iterator it = ((List) eMCursorResultFetchHistoryMessages.getData()).iterator();
        while (it.hasNext()) {
            arrayList.add(new EMMessage((EMAMessage) it.next()));
        }
        eMCursorResult.setData(arrayList);
        return eMCursorResult;
    }

    public void fetchSupportLanguages(final EMValueCallBack<List<EMLanguage>> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.13
            @Override // java.lang.Runnable
            public void run() {
                try {
                    ArrayList arrayList = new ArrayList();
                    EMAError eMAError = new EMAError();
                    List<List<String>> listFetchSupportLanguages = EMChatManager.this.emaObject.fetchSupportLanguages(eMAError);
                    EMChatManager.this.handleError(eMAError);
                    for (List<String> list : listFetchSupportLanguages) {
                        arrayList.add(new EMLanguage(list.get(0), list.get(1), list.get(2)));
                    }
                    eMValueCallBack.onSuccess(arrayList);
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public Map<String, EMConversation> getAllConversations() {
        List<EMAConversation> conversations = this.emaObject.getConversations();
        Hashtable hashtable = new Hashtable();
        for (EMAConversation eMAConversation : conversations) {
            hashtable.put(eMAConversation.conversationId(), new EMConversation(eMAConversation));
        }
        return hashtable;
    }

    public EMConversation getConversation(String str) {
        EMAConversation eMAConversationConversationWithType = this.emaObject.conversationWithType(str, EMAConversation.EMAConversationType.CHAT, false, false);
        if (eMAConversationConversationWithType == null) {
            eMAConversationConversationWithType = this.emaObject.conversationWithType(str, EMAConversation.EMAConversationType.GROUPCHAT, false, false);
        }
        if (eMAConversationConversationWithType == null) {
            eMAConversationConversationWithType = this.emaObject.conversationWithType(str, EMAConversation.EMAConversationType.GROUPCHAT, false, true);
        }
        if (eMAConversationConversationWithType == null) {
            eMAConversationConversationWithType = this.emaObject.conversationWithType(str, EMAConversation.EMAConversationType.CHATROOM, false, false);
        }
        if (eMAConversationConversationWithType == null) {
            eMAConversationConversationWithType = this.emaObject.conversationWithType(str, EMAConversation.EMAConversationType.CHATROOM, false, true);
        }
        if (eMAConversationConversationWithType == null) {
            eMAConversationConversationWithType = this.emaObject.conversationWithType(str, EMAConversation.EMAConversationType.DISCUSSIONGROUP, false, false);
        }
        if (eMAConversationConversationWithType == null) {
            eMAConversationConversationWithType = this.emaObject.conversationWithType(str, EMAConversation.EMAConversationType.HELPDESK, false, false);
        }
        if (eMAConversationConversationWithType == null) {
            return null;
        }
        return new EMConversation(eMAConversationConversationWithType);
    }

    public EMConversation getConversation(String str, EMConversation.EMConversationType eMConversationType) {
        EMConversation conversation = getConversation(str, eMConversationType, false);
        return conversation == null ? getConversation(str, eMConversationType, false, true) : conversation;
    }

    public EMConversation getConversation(String str, EMConversation.EMConversationType eMConversationType, boolean z2) {
        EMConversation conversation = getConversation(str, eMConversationType, z2, false);
        return conversation == null ? getConversation(str, eMConversationType, z2, true) : conversation;
    }

    public EMConversation getConversation(String str, EMConversation.EMConversationType eMConversationType, boolean z2, boolean z3) {
        EMAConversation.EMAConversationType eMAConversationType = EMAConversation.EMAConversationType.CHAT;
        int i2 = AnonymousClass15.$SwitchMap$com$hyphenate$chat$EMConversation$EMConversationType[eMConversationType.ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                eMAConversationType = EMAConversation.EMAConversationType.GROUPCHAT;
            } else if (i2 == 3) {
                eMAConversationType = EMAConversation.EMAConversationType.CHATROOM;
            } else if (i2 == 4) {
                eMAConversationType = EMAConversation.EMAConversationType.DISCUSSIONGROUP;
            } else if (i2 == 5) {
                eMAConversationType = EMAConversation.EMAConversationType.HELPDESK;
            }
        }
        EMAConversation eMAConversationConversationWithType = this.emaObject.conversationWithType(str, eMAConversationType, z2, z3);
        if (eMAConversationConversationWithType == null) {
            return null;
        }
        Log.d(TAG, "convID:" + eMAConversationConversationWithType.conversationId());
        return new EMConversation(eMAConversationConversationWithType);
    }

    public List<EMConversation> getConversationsByType(EMConversation.EMConversationType eMConversationType) {
        List<EMAConversation> conversations = this.emaObject.getConversations();
        ArrayList arrayList = new ArrayList();
        for (EMAConversation eMAConversation : conversations) {
            if (eMConversationType.ordinal() == eMAConversation._getType().ordinal()) {
                arrayList.add(new EMConversation(eMAConversation));
            }
        }
        return arrayList;
    }

    public EMMessage getMessage(String str) {
        synchronized (this.caches) {
            Iterator<EMConversation.MessageCache> it = this.caches.values().iterator();
            while (it.hasNext()) {
                EMMessage message = it.next().getMessage(str);
                if (message != null) {
                    return message;
                }
            }
            EMAMessage message2 = this.emaObject.getMessage(str);
            if (message2 == null) {
                return null;
            }
            return new EMMessage(message2);
        }
    }

    public EMCursorResult<EMMessageReaction> getReactionDetail(String str, String str2, String str3, int i2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMCursorResult<EMAMessageReaction> reactionDetail = this.emaReactionObject.getReactionDetail(str, str2, str3, i2, eMAError);
        handleError(eMAError);
        EMCursorResult<EMMessageReaction> eMCursorResult = new EMCursorResult<>();
        eMCursorResult.setCursor(reactionDetail.getCursor());
        if (reactionDetail.getData() != null) {
            ArrayList arrayList = new ArrayList(((List) reactionDetail.getData()).size());
            for (EMAMessageReaction eMAMessageReaction : (List) reactionDetail.getData()) {
                if (eMAMessageReaction != null) {
                    arrayList.add(new EMMessageReaction(eMAMessageReaction));
                }
            }
            eMCursorResult.setData(arrayList);
        }
        return eMCursorResult;
    }

    public Map<String, List<EMMessageReaction>> getReactionList(List<String> list, EMMessage.ChatType chatType, String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        String str2 = "chat";
        if (EMMessage.ChatType.Chat != chatType && EMMessage.ChatType.GroupChat == chatType) {
            str2 = "groupchat";
        }
        Map<String, List<EMAMessageReaction>> reactionList = this.emaReactionObject.getReactionList(list, str2, str, eMAError);
        handleError(eMAError);
        HashMap map = new HashMap(reactionList.size());
        if (reactionList.size() > 0) {
            for (Map.Entry<String, List<EMAMessageReaction>> entry : reactionList.entrySet()) {
                ArrayList arrayList = new ArrayList(entry.getValue().size());
                for (EMAMessageReaction eMAMessageReaction : entry.getValue()) {
                    if (eMAMessageReaction != null) {
                        arrayList.add(new EMMessageReaction(eMAMessageReaction));
                    }
                }
                map.put(entry.getKey(), arrayList);
            }
        }
        return map;
    }

    public int getUnreadMessageCount() {
        int iUnreadMessagesCount = 0;
        for (EMAConversation eMAConversation : this.emaObject.getConversations()) {
            if (eMAConversation._getType() != EMAConversation.EMAConversationType.CHATROOM) {
                iUnreadMessagesCount += eMAConversation.unreadMessagesCount();
            }
        }
        return iUnreadMessagesCount;
    }

    public synchronized void importMessages(List<EMMessage> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<EMMessage> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().emaObject);
        }
        EMClient.getInstance().getChatConfigPrivate().c(arrayList);
    }

    public void loadAllConversations() {
        this.emaObject.loadAllConversationsFromDB();
    }

    public synchronized void loadAllConversationsFromDB() {
        this.emaObject.loadAllConversationsFromDB();
    }

    public void markAllConversationsAsRead() {
        Iterator<EMAConversation> it = this.emaObject.loadAllConversationsFromDB().iterator();
        while (it.hasNext()) {
            it.next().markAllMessagesAsRead(true);
        }
    }

    public void onLogout() {
        this.caches.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void recallMessage(EMMessage eMMessage) throws HyphenateException {
        EMAError eMAError = new EMAError();
        if (eMMessage == null) {
            throw new HyphenateException(500, "The message was not found");
        }
        this.emaObject.recallMessage((EMAMessage) eMMessage.emaObject, eMAError);
        handleError(eMAError);
        EMConversation conversation = getConversation(eMMessage.getTo(), EMConversation.msgType2ConversationType(eMMessage.getMsgId(), eMMessage.getChatType()), true, eMMessage.isChatThreadMessage());
        if (conversation != null) {
            conversation.getCache().removeMessage(eMMessage.getMsgId());
        }
    }

    public void removeConversationListener(EMConversationListener eMConversationListener) {
        if (eMConversationListener == null) {
            return;
        }
        this.conversationListeners.remove(eMConversationListener);
    }

    public void removeMessageListener(EMMessageListener eMMessageListener) {
        if (eMMessageListener == null) {
            return;
        }
        this.messageListeners.remove(eMMessageListener);
    }

    public void removeReaction(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaReactionObject.removeReaction(str, str2, eMAError);
        handleError(eMAError);
    }

    public void reportMessage(String str, String str2, String str3) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.reportMessage(str, str2, str3, eMAError);
        handleError(eMAError);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void saveMessage(com.hyphenate.chat.EMMessage r6) {
        /*
            r5 = this;
            com.hyphenate.chat.EMMessage$ChatType r0 = r6.getChatType()
            com.hyphenate.chat.EMConversation$EMConversationType r1 = com.hyphenate.chat.EMConversation.EMConversationType.Chat
            int[] r2 = com.hyphenate.chat.EMChatManager.AnonymousClass15.$SwitchMap$com$hyphenate$chat$EMMessage$ChatType
            int r0 = r0.ordinal()
            r0 = r2[r0]
            r2 = 1
            if (r0 == r2) goto L17
            r3 = 2
            if (r0 == r3) goto L1c
            r3 = 3
            if (r0 == r3) goto L19
        L17:
            r0 = r1
            goto L1e
        L19:
            com.hyphenate.chat.EMConversation$EMConversationType r0 = com.hyphenate.chat.EMConversation.EMConversationType.ChatRoom
            goto L1e
        L1c:
            com.hyphenate.chat.EMConversation$EMConversationType r0 = com.hyphenate.chat.EMConversation.EMConversationType.GroupChat
        L1e:
            java.lang.String r3 = r6.getTo()
            if (r0 != r1) goto L30
            com.hyphenate.chat.EMMessage$Direct r1 = r6.direct()
            com.hyphenate.chat.EMMessage$Direct r4 = com.hyphenate.chat.EMMessage.Direct.RECEIVE
            if (r1 != r4) goto L30
            java.lang.String r3 = r6.getFrom()
        L30:
            com.hyphenate.chat.EMMessage$Type r1 = r6.getType()
            com.hyphenate.chat.EMMessage$Type r4 = com.hyphenate.chat.EMMessage.Type.CMD
            if (r1 != r4) goto L39
            return
        L39:
            boolean r1 = r6.isChatThreadMessage()
            com.hyphenate.chat.EMConversation r0 = r5.getConversation(r3, r0, r2, r1)
            if (r0 != 0) goto L5a
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "Failed to save message because conversation is null, convId: "
            r6.append(r0)
            r6.append(r3)
            java.lang.String r6 = r6.toString()
            java.lang.String r0 = "EMChatManager"
            com.hyphenate.util.EMLog.e(r0, r6)
            return
        L5a:
            r0.insertMessage(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.chat.EMChatManager.saveMessage(com.hyphenate.chat.EMMessage):void");
    }

    public List<EMMessage> searchMsgFromDB(EMMessage.Type type, long j2, int i2, String str, EMConversation.EMSearchDirection eMSearchDirection) {
        List<EMAMessage> listSearchMessages = this.emaObject.searchMessages(type.ordinal(), j2, i2, str, eMSearchDirection == EMConversation.EMSearchDirection.UP ? EMAConversation.EMASearchDirection.UP : EMAConversation.EMASearchDirection.DOWN);
        List<EMMessage> linkedList = listSearchMessages.size() > 512 ? new LinkedList<>() : new ArrayList<>();
        for (EMAMessage eMAMessage : listSearchMessages) {
            if (eMAMessage != null) {
                linkedList.add(new EMMessage(eMAMessage));
            }
        }
        return linkedList;
    }

    public List<EMMessage> searchMsgFromDB(String str, long j2, int i2, String str2, EMConversation.EMSearchDirection eMSearchDirection) {
        List<EMAMessage> listSearchMessages = this.emaObject.searchMessages(str, j2, i2, str2, eMSearchDirection == EMConversation.EMSearchDirection.UP ? EMAConversation.EMASearchDirection.UP : EMAConversation.EMASearchDirection.DOWN);
        List<EMMessage> linkedList = listSearchMessages.size() > 512 ? new LinkedList<>() : new ArrayList<>();
        for (EMAMessage eMAMessage : listSearchMessages) {
            if (eMAMessage != null) {
                linkedList.add(new EMMessage(eMAMessage));
            }
        }
        return linkedList;
    }

    public void sendMessage(final EMMessage eMMessage) {
        eMMessage.makeCallbackStrong();
        final EMConversation conversation = getConversation(eMMessage.conversationId(), EMConversation.msgType2ConversationType(eMMessage.getTo(), eMMessage.getChatType()), eMMessage.getType() != EMMessage.Type.CMD, eMMessage.isChatThreadMessage());
        if (conversation != null) {
            if (!(conversation.getCache().getMessage(eMMessage.getMsgId()) != null)) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                EMMessage lastMessage = conversation.getLastMessage();
                if (lastMessage != null && jCurrentTimeMillis < lastMessage.getMsgTime()) {
                    jCurrentTimeMillis = lastMessage.getMsgTime();
                }
                eMMessage.setMsgTime(jCurrentTimeMillis + 1);
                conversation.getCache().addMessage(eMMessage);
            }
        }
        this.mClient.executeOnSendQueue(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                int i2 = 1;
                try {
                    String str = null;
                    if (EMChatManager.this.mClient.getOptions().getAutoTransferMessageAttachments()) {
                        String str2 = "File not exists or can not be read";
                        String str3 = "Message body can not be null";
                        int i3 = 401;
                        if (eMMessage.getType() == EMMessage.Type.IMAGE) {
                            eMMessage.setStatus(EMMessage.Status.INPROGRESS);
                            EMImageMessageBody eMImageMessageBody = (EMImageMessageBody) eMMessage.getBody();
                            if (eMImageMessageBody == null) {
                                new Object(i2, str3, eMMessage) { // from class: com.hyphenate.chat.EMChatManager.1HandleError
                                    final /* synthetic */ EMMessage val$msg;

                                    {
                                        this.val$msg = eMMessage;
                                        EMMessage.EMCallbackHolder eMCallbackHolder = eMMessage.messageStatusCallBack;
                                        if (eMCallbackHolder != null) {
                                            eMCallbackHolder.onError(i2, str3);
                                        }
                                    }
                                };
                                return;
                            }
                            String localUrl = eMImageMessageBody.getLocalUrl();
                            if (TextUtils.isEmpty(eMImageMessageBody.getRemoteUrl()) && !EMFileHelper.getInstance().isFileExist(localUrl)) {
                                new Object(i3, str2, eMMessage) { // from class: com.hyphenate.chat.EMChatManager.1HandleError
                                    final /* synthetic */ EMMessage val$msg;

                                    {
                                        this.val$msg = eMMessage;
                                        EMMessage.EMCallbackHolder eMCallbackHolder = eMMessage.messageStatusCallBack;
                                        if (eMCallbackHolder != null) {
                                            eMCallbackHolder.onError(i3, str2);
                                        }
                                    }
                                };
                                return;
                            }
                            if (!eMImageMessageBody.isSendOriginalImage() && EMFileHelper.getInstance().isFileExist(localUrl)) {
                                String scaledImageByUri = ImageUtils.getScaledImageByUri(EMChatManager.this.mClient.getContext(), localUrl);
                                if (!TextUtils.equals(scaledImageByUri, localUrl)) {
                                    long fileLength = EMFileHelper.getInstance().getFileLength(localUrl);
                                    long fileLength2 = EMFileHelper.getInstance().getFileLength(scaledImageByUri);
                                    if (fileLength == 0) {
                                        EMLog.d(EMChatManager.TAG, "original image size:" + fileLength);
                                        new Object(i3, "original image size is 0", eMMessage) { // from class: com.hyphenate.chat.EMChatManager.1HandleError
                                            final /* synthetic */ EMMessage val$msg;

                                            {
                                                this.val$msg = eMMessage;
                                                EMMessage.EMCallbackHolder eMCallbackHolder = eMMessage.messageStatusCallBack;
                                                if (eMCallbackHolder != null) {
                                                    eMCallbackHolder.onError(i3, str2);
                                                }
                                            }
                                        };
                                        return;
                                    }
                                    EMLog.d(EMChatManager.TAG, "original image size:" + fileLength + " scaled image size:" + fileLength2 + " ratio:" + ((int) (fileLength2 / fileLength)) + "%");
                                    eMImageMessageBody.setLocalUrl(EMFileHelper.getInstance().formatInUri(scaledImageByUri));
                                    eMImageMessageBody.setFileLength(fileLength2);
                                    str = localUrl;
                                    localUrl = scaledImageByUri;
                                }
                                eMImageMessageBody.setFileName(EMFileHelper.getInstance().getFilename(localUrl));
                            }
                            BitmapFactory.Options bitmapOptions = ImageUtils.getBitmapOptions(EMChatManager.this.mClient.getContext(), localUrl);
                            if (bitmapOptions != null) {
                                eMImageMessageBody.setSize(bitmapOptions.outWidth, bitmapOptions.outHeight);
                            }
                        } else if (eMMessage.getType() == EMMessage.Type.VIDEO) {
                            eMMessage.setStatus(EMMessage.Status.INPROGRESS);
                            EMVideoMessageBody eMVideoMessageBody = (EMVideoMessageBody) eMMessage.getBody();
                            if (eMVideoMessageBody == null) {
                                new Object(i2, str3, eMMessage) { // from class: com.hyphenate.chat.EMChatManager.1HandleError
                                    final /* synthetic */ EMMessage val$msg;

                                    {
                                        this.val$msg = eMMessage;
                                        EMMessage.EMCallbackHolder eMCallbackHolder = eMMessage.messageStatusCallBack;
                                        if (eMCallbackHolder != null) {
                                            eMCallbackHolder.onError(i2, str3);
                                        }
                                    }
                                };
                                return;
                            }
                            Uri localUri = eMVideoMessageBody.getLocalUri();
                            if (TextUtils.isEmpty(eMVideoMessageBody.getRemoteUrl()) && !EMFileHelper.getInstance().isFileExist(localUri)) {
                                new Object(i3, str2, eMMessage) { // from class: com.hyphenate.chat.EMChatManager.1HandleError
                                    final /* synthetic */ EMMessage val$msg;

                                    {
                                        this.val$msg = eMMessage;
                                        EMMessage.EMCallbackHolder eMCallbackHolder = eMMessage.messageStatusCallBack;
                                        if (eMCallbackHolder != null) {
                                            eMCallbackHolder.onError(i3, str2);
                                        }
                                    }
                                };
                                return;
                            }
                            String filePath = EMFileHelper.getInstance().getFilePath(eMVideoMessageBody.getLocalThumbUri());
                            if (!TextUtils.isEmpty(filePath)) {
                                BitmapFactory.Options bitmapOptions2 = ImageUtils.getBitmapOptions(filePath);
                                eMVideoMessageBody.setThumbnailSize(bitmapOptions2.outWidth, bitmapOptions2.outHeight);
                            }
                        }
                    }
                    EMChatManager.this.setMessageSendCallback(eMMessage, conversation, eMMessage.getMsgId(), str);
                    EMChatManager.this.emaObject.sendMessage((EMAMessage) eMMessage.emaObject);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    new Object(i2, PushException.EXCEPTION_SEND_FAILED, eMMessage) { // from class: com.hyphenate.chat.EMChatManager.1HandleError
                        final /* synthetic */ EMMessage val$msg;

                        {
                            this.val$msg = eMMessage;
                            EMMessage.EMCallbackHolder eMCallbackHolder = eMMessage.messageStatusCallBack;
                            if (eMCallbackHolder != null) {
                                eMCallbackHolder.onError(i2, str2);
                            }
                        }
                    };
                }
            }
        });
    }

    public void setVoiceMessageListened(EMMessage eMMessage) {
        eMMessage.setListened(true);
        updateMessage(eMMessage);
    }

    public void translateMessage(final EMMessage eMMessage, final List<String> list, final EMValueCallBack<EMMessage> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMChatManager.14
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMAMessage eMAMessageTranslateMessage = EMChatManager.this.emaObject.translateMessage((EMAMessage) eMMessage.emaObject, list, eMAError);
                    EMChatManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(new EMMessage(eMAMessageTranslateMessage));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public boolean updateMessage(EMMessage eMMessage) {
        String from = eMMessage.direct() == EMMessage.Direct.RECEIVE ? eMMessage.getFrom() : eMMessage.getTo();
        if (eMMessage.getType() == EMMessage.Type.CMD) {
            return false;
        }
        return getConversation(eMMessage.conversationId(), EMConversation.msgType2ConversationType(from, eMMessage.getChatType()), true, eMMessage.isChatThreadMessage()).updateMessage(eMMessage);
    }

    public boolean updateParticipant(String str, String str2) {
        return this.emaObject.updateParticipant(str, str2);
    }
}
