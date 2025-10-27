package com.hyphenate.chat;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMChatThreadChangeListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatThreadEvent;
import com.hyphenate.chat.adapter.EMAError;
import com.hyphenate.chat.adapter.EMAThreadInfo;
import com.hyphenate.chat.adapter.EMAThreadManager;
import com.hyphenate.chat.adapter.EMAThreadManagerListener;
import com.hyphenate.chat.adapter.message.EMAMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EMChatThreadManager {
    private static String TAG = "EMChatThreadManager";
    EMAThreadManager emaObject;
    EMClient mClient;
    private EMAThreadManagerListener listenerImpl = new EMAThreadManagerListener() { // from class: com.hyphenate.chat.EMChatThreadManager.12
        @Override // com.hyphenate.chat.adapter.EMAThreadManagerListener, com.hyphenate.chat.adapter.EMAThreadManagerListenerInterface
        public void onLeaveThread(EMAThreadInfo eMAThreadInfo, int i2) {
            synchronized (EMChatThreadManager.this.threadChangeListeners) {
                for (EMChatThreadChangeListener eMChatThreadChangeListener : EMChatThreadManager.this.threadChangeListeners) {
                    try {
                        if (i2 == EMAThreadInfo.LeaveReason.BE_KICKED.ordinal()) {
                            eMChatThreadChangeListener.onChatThreadUserRemoved(new EMChatThreadEvent(eMAThreadInfo));
                        } else {
                            eMChatThreadChangeListener.onChatThreadDestroyed(new EMChatThreadEvent(eMAThreadInfo));
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAThreadManagerListener, com.hyphenate.chat.adapter.EMAThreadManagerListenerInterface
        public void onMemberExited(EMAThreadInfo eMAThreadInfo) {
        }

        @Override // com.hyphenate.chat.adapter.EMAThreadManagerListener, com.hyphenate.chat.adapter.EMAThreadManagerListenerInterface
        public void onMemberJoined(EMAThreadInfo eMAThreadInfo) {
        }

        @Override // com.hyphenate.chat.adapter.EMAThreadManagerListener, com.hyphenate.chat.adapter.EMAThreadManagerListenerInterface
        public void onThreadNameUpdated(EMAThreadInfo eMAThreadInfo) {
        }

        @Override // com.hyphenate.chat.adapter.EMAThreadManagerListener, com.hyphenate.chat.adapter.EMAThreadManagerListenerInterface
        public void onThreadNotifyChange(EMAThreadInfo eMAThreadInfo) {
            synchronized (EMChatThreadManager.this.threadChangeListeners) {
                for (EMChatThreadChangeListener eMChatThreadChangeListener : EMChatThreadManager.this.threadChangeListeners) {
                    try {
                        int i2 = AnonymousClass13.$SwitchMap$com$hyphenate$chat$EMChatThreadEvent$TYPE[eMAThreadInfo.getType().ordinal()];
                        if (i2 == 1) {
                            eMChatThreadChangeListener.onChatThreadCreated(new EMChatThreadEvent(eMAThreadInfo));
                        } else if (i2 == 2 || i2 == 3) {
                            eMChatThreadChangeListener.onChatThreadUpdated(new EMChatThreadEvent(eMAThreadInfo));
                        } else if (i2 == 4) {
                            eMChatThreadChangeListener.onChatThreadDestroyed(new EMChatThreadEvent(eMAThreadInfo));
                        }
                    } catch (Exception e2) {
                        EMLog.e(EMChatThreadManager.TAG, e2.getMessage());
                    }
                }
            }
        }
    };
    List<EMChatThreadChangeListener> threadChangeListeners = Collections.synchronizedList(new ArrayList());

    /* renamed from: com.hyphenate.chat.EMChatThreadManager$13, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass13 {
        static final /* synthetic */ int[] $SwitchMap$com$hyphenate$chat$EMChatThreadEvent$TYPE;

        static {
            int[] iArr = new int[EMChatThreadEvent.TYPE.values().length];
            $SwitchMap$com$hyphenate$chat$EMChatThreadEvent$TYPE = iArr;
            try {
                iArr[EMChatThreadEvent.TYPE.CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMChatThreadEvent$TYPE[EMChatThreadEvent.TYPE.UPDATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMChatThreadEvent$TYPE[EMChatThreadEvent.TYPE.UPDATE_MSG.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMChatThreadEvent$TYPE[EMChatThreadEvent.TYPE.DELETE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public EMChatThreadManager(EMClient eMClient, EMAThreadManager eMAThreadManager) {
        this.emaObject = eMAThreadManager;
        this.mClient = eMClient;
        this.emaObject.addListener(this.listenerImpl);
        EMClient.getInstance().chatManager();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EMChatThread createChatThread(String str, String str2, String str3) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAThreadInfo eMAThreadInfoCreateThread = this.emaObject.createThread(str, str2, str3, eMAError);
        handleError(eMAError);
        return new EMChatThread(eMAThreadInfoCreateThread);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroyChatThread(String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.destroyThread(str, eMAError);
        handleError(eMAError);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EMChatThread getChatThreadFromServer(String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAThreadInfo threadFromServer = this.emaObject.getThreadFromServer(str, eMAError);
        handleError(eMAError);
        return new EMChatThread(threadFromServer);
    }

    private Map<String, EMMessage> getChatThreadLatestMessage(List<String> list) throws HyphenateException {
        EMAError eMAError = new EMAError();
        Map<String, EMAMessage> threadsLatestMessage = this.emaObject.getThreadsLatestMessage(list, eMAError);
        handleError(eMAError);
        HashMap map = new HashMap();
        for (Map.Entry<String, EMAMessage> entry : threadsLatestMessage.entrySet()) {
            map.put(entry.getKey(), new EMMessage(entry.getValue()));
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EMCursorResult<String> getChatThreadMembers(String str, int i2, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMCursorResult<String> eMCursorResultFetchThreadMembers = this.emaObject.fetchThreadMembers(str, i2, str2, eMAError);
        handleError(eMAError);
        return eMCursorResultFetchThreadMembers;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EMCursorResult<EMChatThread> getChatThreadsFromServer(String str, int i2, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMCursorResult<EMAThreadInfo> threadsFromServer = this.emaObject.getThreadsFromServer(str, i2, str2, eMAError);
        handleError(eMAError);
        EMCursorResult<EMChatThread> eMCursorResult = new EMCursorResult<>();
        ArrayList arrayList = new ArrayList();
        List list = (List) threadsFromServer.getData();
        for (int i3 = 0; i3 < list.size(); i3++) {
            arrayList.add(new EMChatThread((EMAThreadInfo) list.get(i3)));
        }
        eMCursorResult.setData(arrayList);
        eMCursorResult.setCursor(threadsFromServer.getCursor());
        return eMCursorResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EMCursorResult<EMChatThread> getJoinedChatThreadsFromServer(int i2, String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMCursorResult<EMAThreadInfo> joinedThreadsFromServer = this.emaObject.getJoinedThreadsFromServer(i2, str, eMAError);
        handleError(eMAError);
        EMCursorResult<EMChatThread> eMCursorResult = new EMCursorResult<>();
        ArrayList arrayList = new ArrayList();
        List list = (List) joinedThreadsFromServer.getData();
        for (int i3 = 0; i3 < list.size(); i3++) {
            arrayList.add(new EMChatThread((EMAThreadInfo) list.get(i3)));
        }
        eMCursorResult.setData(arrayList);
        eMCursorResult.setCursor(joinedThreadsFromServer.getCursor());
        return eMCursorResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EMCursorResult<EMChatThread> getJoinedChatThreadsFromServer(String str, int i2, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMCursorResult<EMAThreadInfo> joinedThreadsFromServer = this.emaObject.getJoinedThreadsFromServer(str, i2, str2, eMAError);
        handleError(eMAError);
        EMCursorResult<EMChatThread> eMCursorResult = new EMCursorResult<>();
        ArrayList arrayList = new ArrayList();
        List list = (List) joinedThreadsFromServer.getData();
        for (int i3 = 0; i3 < list.size(); i3++) {
            arrayList.add(new EMChatThread((EMAThreadInfo) list.get(i3)));
        }
        eMCursorResult.setData(arrayList);
        eMCursorResult.setCursor(joinedThreadsFromServer.getCursor());
        return eMCursorResult;
    }

    private void handleError(EMAError eMAError) throws HyphenateException {
        if (eMAError.errCode() != 0) {
            throw new HyphenateException(eMAError);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EMChatThread joinChatThread(String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAThreadInfo eMAThreadInfoJoinThread = this.emaObject.joinThread(str, eMAError);
        handleError(eMAError);
        return new EMChatThread(eMAThreadInfoJoinThread);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getChatThreadLatestMessage$0(List list, EMValueCallBack eMValueCallBack) {
        try {
            Map<String, EMMessage> chatThreadLatestMessage = getChatThreadLatestMessage(list);
            if (eMValueCallBack != null) {
                eMValueCallBack.onSuccess(chatThreadLatestMessage);
            }
        } catch (HyphenateException e2) {
            if (eMValueCallBack != null) {
                eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void leaveChatThread(String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.leaveThread(str, eMAError);
        handleError(eMAError);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeMemberFromChatThread(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.removeMemberFromThread(str, str2, eMAError);
        handleError(eMAError);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateChatThreadName(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.updateChatThreadName(str, str2, eMAError);
        handleError(eMAError);
    }

    public void addChatThreadChangeListener(EMChatThreadChangeListener eMChatThreadChangeListener) {
        if (eMChatThreadChangeListener == null || this.threadChangeListeners.contains(eMChatThreadChangeListener)) {
            return;
        }
        this.threadChangeListeners.add(eMChatThreadChangeListener);
    }

    public void createChatThread(final String str, final String str2, final String str3, final EMValueCallBack<EMChatThread> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMChatThreadManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMChatThread eMChatThreadCreateChatThread = EMChatThreadManager.this.createChatThread(str, str2, str3);
                    EMValueCallBack eMValueCallBack2 = eMValueCallBack;
                    if (eMValueCallBack2 != null) {
                        eMValueCallBack2.onSuccess(eMChatThreadCreateChatThread);
                    }
                } catch (HyphenateException e2) {
                    EMValueCallBack eMValueCallBack3 = eMValueCallBack;
                    if (eMValueCallBack3 != null) {
                        eMValueCallBack3.onError(e2.getErrorCode(), e2.getDescription());
                        return;
                    }
                    EMLog.e(EMChatThreadManager.TAG, "createChatThread error: " + e2.getErrorCode() + " " + e2.getDescription());
                }
            }
        });
    }

    public void destroyChatThread(final String str, final EMCallBack eMCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMChatThreadManager.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMChatThreadManager.this.destroyChatThread(str);
                    EMCallBack eMCallBack2 = eMCallBack;
                    if (eMCallBack2 != null) {
                        eMCallBack2.onSuccess();
                    }
                } catch (HyphenateException e2) {
                    EMCallBack eMCallBack3 = eMCallBack;
                    if (eMCallBack3 != null) {
                        eMCallBack3.onError(e2.getErrorCode(), e2.getDescription());
                        return;
                    }
                    EMLog.e(EMChatThreadManager.TAG, "destroyChatThread error: " + e2.getErrorCode() + " " + e2.getDescription());
                }
            }
        });
    }

    public void getChatThreadFromServer(final String str, final EMValueCallBack<EMChatThread> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMChatThreadManager.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMChatThread chatThreadFromServer = EMChatThreadManager.this.getChatThreadFromServer(str);
                    EMValueCallBack eMValueCallBack2 = eMValueCallBack;
                    if (eMValueCallBack2 != null) {
                        eMValueCallBack2.onSuccess(chatThreadFromServer);
                    }
                } catch (HyphenateException e2) {
                    EMValueCallBack eMValueCallBack3 = eMValueCallBack;
                    if (eMValueCallBack3 != null) {
                        eMValueCallBack3.onError(e2.getErrorCode(), e2.getDescription());
                        return;
                    }
                    EMLog.e(EMChatThreadManager.TAG, "getChatThreadFromServer error: " + e2.getErrorCode() + " " + e2.getDescription());
                }
            }
        });
    }

    public void getChatThreadLatestMessage(final List<String> list, final EMValueCallBack<Map<String, EMMessage>> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.e
            @Override // java.lang.Runnable
            public final void run() {
                this.f8581c.lambda$getChatThreadLatestMessage$0(list, eMValueCallBack);
            }
        });
    }

    public void getChatThreadMembers(final String str, final int i2, final String str2, final EMValueCallBack<EMCursorResult<String>> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMChatThreadManager.8
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMCursorResult chatThreadMembers = EMChatThreadManager.this.getChatThreadMembers(str, i2, str2);
                    EMValueCallBack eMValueCallBack2 = eMValueCallBack;
                    if (eMValueCallBack2 != null) {
                        eMValueCallBack2.onSuccess(chatThreadMembers);
                    }
                } catch (HyphenateException e2) {
                    EMValueCallBack eMValueCallBack3 = eMValueCallBack;
                    if (eMValueCallBack3 != null) {
                        eMValueCallBack3.onError(e2.getErrorCode(), e2.getDescription());
                        return;
                    }
                    EMLog.e(EMChatThreadManager.TAG, "getThreadMembers error: " + e2.getErrorCode() + " " + e2.getDescription());
                }
            }
        });
    }

    public void getChatThreadsFromServer(final String str, final int i2, final String str2, final EMValueCallBack<EMCursorResult<EMChatThread>> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMChatThreadManager.11
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMCursorResult chatThreadsFromServer = EMChatThreadManager.this.getChatThreadsFromServer(str, i2, str2);
                    EMValueCallBack eMValueCallBack2 = eMValueCallBack;
                    if (eMValueCallBack2 != null) {
                        eMValueCallBack2.onSuccess(chatThreadsFromServer);
                    }
                } catch (HyphenateException e2) {
                    EMValueCallBack eMValueCallBack3 = eMValueCallBack;
                    if (eMValueCallBack3 != null) {
                        eMValueCallBack3.onError(e2.getErrorCode(), e2.getDescription());
                        return;
                    }
                    EMLog.e(EMChatThreadManager.TAG, "getThreadsFromServer error: " + e2.getErrorCode() + " " + e2.getDescription());
                }
            }
        });
    }

    public void getJoinedChatThreadsFromServer(final int i2, final String str, final EMValueCallBack<EMCursorResult<EMChatThread>> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMChatThreadManager.9
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMCursorResult joinedChatThreadsFromServer = EMChatThreadManager.this.getJoinedChatThreadsFromServer(i2, str);
                    EMValueCallBack eMValueCallBack2 = eMValueCallBack;
                    if (eMValueCallBack2 != null) {
                        eMValueCallBack2.onSuccess(joinedChatThreadsFromServer);
                    }
                } catch (HyphenateException e2) {
                    EMValueCallBack eMValueCallBack3 = eMValueCallBack;
                    if (eMValueCallBack3 != null) {
                        eMValueCallBack3.onError(e2.getErrorCode(), e2.getDescription());
                        return;
                    }
                    EMLog.e(EMChatThreadManager.TAG, "getJoinedThreadsFromServer error: " + e2.getErrorCode() + " " + e2.getDescription());
                }
            }
        });
    }

    public void getJoinedChatThreadsFromServer(final String str, final int i2, final String str2, final EMValueCallBack<EMCursorResult<EMChatThread>> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMChatThreadManager.10
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMCursorResult joinedChatThreadsFromServer = EMChatThreadManager.this.getJoinedChatThreadsFromServer(str, i2, str2);
                    EMValueCallBack eMValueCallBack2 = eMValueCallBack;
                    if (eMValueCallBack2 != null) {
                        eMValueCallBack2.onSuccess(joinedChatThreadsFromServer);
                    }
                } catch (HyphenateException e2) {
                    EMValueCallBack eMValueCallBack3 = eMValueCallBack;
                    if (eMValueCallBack3 != null) {
                        eMValueCallBack3.onError(e2.getErrorCode(), e2.getDescription());
                        return;
                    }
                    EMLog.e(EMChatThreadManager.TAG, "getJoinedThreadsFromServer error: " + e2.getErrorCode() + " " + e2.getDescription());
                }
            }
        });
    }

    public void joinChatThread(final String str, final EMValueCallBack<EMChatThread> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMChatThreadManager.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMChatThread eMChatThreadJoinChatThread = EMChatThreadManager.this.joinChatThread(str);
                    EMValueCallBack eMValueCallBack2 = eMValueCallBack;
                    if (eMValueCallBack2 != null) {
                        eMValueCallBack2.onSuccess(eMChatThreadJoinChatThread);
                    }
                } catch (HyphenateException e2) {
                    EMValueCallBack eMValueCallBack3 = eMValueCallBack;
                    if (eMValueCallBack3 != null) {
                        eMValueCallBack3.onError(e2.getErrorCode(), e2.getDescription());
                        return;
                    }
                    EMLog.e(EMChatThreadManager.TAG, "joinChatThread error: " + e2.getErrorCode() + " " + e2.getDescription());
                }
            }
        });
    }

    public void leaveChatThread(final String str, final EMCallBack eMCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMChatThreadManager.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMChatThreadManager.this.leaveChatThread(str);
                    EMCallBack eMCallBack2 = eMCallBack;
                    if (eMCallBack2 != null) {
                        eMCallBack2.onSuccess();
                    }
                } catch (HyphenateException e2) {
                    EMCallBack eMCallBack3 = eMCallBack;
                    if (eMCallBack3 != null) {
                        eMCallBack3.onError(e2.getErrorCode(), e2.getDescription());
                        return;
                    }
                    EMLog.e(EMChatThreadManager.TAG, "leaveChatThread error: " + e2.getErrorCode() + " " + e2.getDescription());
                }
            }
        });
    }

    public void removeChatThreadChangeListener(EMChatThreadChangeListener eMChatThreadChangeListener) {
        if (eMChatThreadChangeListener == null || !this.threadChangeListeners.contains(eMChatThreadChangeListener)) {
            return;
        }
        this.threadChangeListeners.remove(eMChatThreadChangeListener);
    }

    public void removeMemberFromChatThread(final String str, final String str2, final EMCallBack eMCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMChatThreadManager.7
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMChatThreadManager.this.removeMemberFromChatThread(str, str2);
                    EMCallBack eMCallBack2 = eMCallBack;
                    if (eMCallBack2 != null) {
                        eMCallBack2.onSuccess();
                    }
                } catch (HyphenateException e2) {
                    EMCallBack eMCallBack3 = eMCallBack;
                    if (eMCallBack3 != null) {
                        eMCallBack3.onError(e2.getErrorCode(), e2.getDescription());
                        return;
                    }
                    EMLog.e(EMChatThreadManager.TAG, "removeMemberFromThread error: " + e2.getErrorCode() + " " + e2.getDescription());
                }
            }
        });
    }

    public void updateChatThreadName(final String str, final String str2, final EMCallBack eMCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMChatThreadManager.6
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMChatThreadManager.this.updateChatThreadName(str, str2);
                    EMCallBack eMCallBack2 = eMCallBack;
                    if (eMCallBack2 != null) {
                        eMCallBack2.onSuccess();
                    }
                } catch (HyphenateException e2) {
                    EMCallBack eMCallBack3 = eMCallBack;
                    if (eMCallBack3 != null) {
                        eMCallBack3.onError(e2.getErrorCode(), e2.getDescription());
                        return;
                    }
                    EMLog.e(EMChatThreadManager.TAG, "changeChatThreadName error: " + e2.getErrorCode() + " " + e2.getDescription());
                }
            }
        });
    }
}
