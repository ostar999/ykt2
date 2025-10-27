package com.hyphenate.chat;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.adapter.EMACallback;
import com.hyphenate.chat.adapter.EMAError;
import com.hyphenate.chat.adapter.EMAGroup;
import com.hyphenate.chat.adapter.EMAGroupManager;
import com.hyphenate.chat.adapter.EMAGroupManagerListener;
import com.hyphenate.chat.adapter.EMAGroupSetting;
import com.hyphenate.chat.adapter.EMAMucShareFile;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import com.plv.livescenes.model.PLVChatFunctionSwitchVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EMGroupManager {
    private static String TAG = "group";
    EMAGroupManager emaObject;
    EMClient mClient;
    EMAGroupManagerListener listenerImpl = new EMAGroupManagerListener() { // from class: com.hyphenate.chat.EMGroupManager.1
        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onAddAdminFromGroup(EMAGroup eMAGroup, String str) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onAdminAdded(eMAGroup.groupId(), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onAddMutesFromGroup(EMAGroup eMAGroup, List<String> list, long j2) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onMuteListAdded(eMAGroup.groupId(), list, j2);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onAllMemberMuteStateChanged(EMAGroup eMAGroup, boolean z2) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onAllMemberMuteStateChanged(eMAGroup.groupId(), z2);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onAnnouncementChanged(EMAGroup eMAGroup, String str) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onAnnouncementChanged(eMAGroup.groupId(), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onAssignOwnerFromGroup(EMAGroup eMAGroup, String str, String str2) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onOwnerChanged(eMAGroup.groupId(), str, str2);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onAutoAcceptInvitationFromGroup(EMAGroup eMAGroup, String str, String str2) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onAutoAcceptInvitationFromGroup(eMAGroup.groupId(), str, str2);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onDeleteShareFileFromGroup(EMAGroup eMAGroup, String str) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onSharedFileDeleted(eMAGroup.groupId(), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onLeaveGroup(EMAGroup eMAGroup, int i2) {
            EMClient.getInstance().chatManager().caches.remove(eMAGroup.groupId());
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    for (EMGroupChangeListener eMGroupChangeListener : EMGroupManager.this.groupChangeListeners) {
                        if (i2 == EMAGroup.EMGroupLeaveReason.BE_KICKED.ordinal()) {
                            eMGroupChangeListener.onUserRemoved(eMAGroup.groupId(), eMAGroup.groupSubject());
                        } else {
                            eMGroupChangeListener.onGroupDestroyed(eMAGroup.groupId(), eMAGroup.groupSubject());
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onMemberExited(EMAGroup eMAGroup, String str) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onMemberExited(eMAGroup.groupId(), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onMemberJoined(EMAGroup eMAGroup, String str) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onMemberJoined(eMAGroup.groupId(), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onReceiveAcceptionFromGroup(EMAGroup eMAGroup) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onRequestToJoinAccepted(eMAGroup.groupId(), eMAGroup.groupSubject(), eMAGroup.getOwner());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onReceiveInviteAcceptionFromGroup(EMAGroup eMAGroup, String str) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onInvitationAccepted(eMAGroup.groupId(), str, "");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onReceiveInviteDeclineFromGroup(EMAGroup eMAGroup, String str, String str2) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onInvitationDeclined(eMAGroup.groupId(), str, "");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onReceiveInviteFromGroup(String str, String str2, String str3, String str4) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onInvitationReceived(str, str2, str3, str4);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onReceiveJoinGroupApplication(EMAGroup eMAGroup, String str, String str2) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onRequestToJoinReceived(eMAGroup.groupId(), eMAGroup.groupSubject(), str, str2);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onReceiveRejectionFromGroup(String str, String str2) {
            EMGroup group = EMGroupManager.this.getGroup(str);
            String strGroupSubject = group == null ? "" : group.groupSubject();
            String owner = group == null ? "" : group.getOwner();
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onRequestToJoinDeclined(str, strGroupSubject, owner, str2);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onRemoveAdminFromGroup(EMAGroup eMAGroup, String str) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onAdminRemoved(eMAGroup.groupId(), str);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onRemoveMutesFromGroup(EMAGroup eMAGroup, List<String> list) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onMuteListRemoved(eMAGroup.groupId(), list);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onStateChangedFromGroup(EMAGroup eMAGroup, boolean z2) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                while (it.hasNext()) {
                    try {
                        it.next().onStateChanged(new EMGroup(eMAGroup), z2);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onUpdateMyGroupList(List<EMAGroup> list) {
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onUpdateSpecificationFromGroup(EMAGroup eMAGroup) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                for (EMGroupChangeListener eMGroupChangeListener : EMGroupManager.this.groupChangeListeners) {
                    if (eMAGroup != null) {
                        try {
                            eMGroupChangeListener.onSpecificationChanged(new EMGroup(eMAGroup));
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onUploadShareFileFromGroup(EMAGroup eMAGroup, EMAMucShareFile eMAMucShareFile) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onSharedFileAdded(eMAGroup.groupId(), new EMMucSharedFile(eMAMucShareFile));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onWhiteListAdded(EMAGroup eMAGroup, List<String> list) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onWhiteListAdded(eMAGroup.groupId(), list);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAGroupManagerListener, com.hyphenate.chat.adapter.EMAGroupManagerListenerInterface
        public void onWhiteListRemoved(EMAGroup eMAGroup, List<String> list) {
            synchronized (EMGroupManager.this.groupChangeListeners) {
                try {
                    Iterator<EMGroupChangeListener> it = EMGroupManager.this.groupChangeListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onWhiteListRemoved(eMAGroup.groupId(), list);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    };
    List<EMGroupChangeListener> groupChangeListeners = Collections.synchronizedList(new ArrayList());

    /* renamed from: com.hyphenate.chat.EMGroupManager$50, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass50 {
        static final /* synthetic */ int[] $SwitchMap$com$hyphenate$chat$EMGroupManager$EMGroupStyle;

        static {
            int[] iArr = new int[EMGroupStyle.values().length];
            $SwitchMap$com$hyphenate$chat$EMGroupManager$EMGroupStyle = iArr;
            try {
                iArr[EMGroupStyle.EMGroupStylePrivateOnlyOwnerInvite.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMGroupManager$EMGroupStyle[EMGroupStyle.EMGroupStylePrivateMemberCanInvite.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMGroupManager$EMGroupStyle[EMGroupStyle.EMGroupStylePublicJoinNeedApproval.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMGroupManager$EMGroupStyle[EMGroupStyle.EMGroupStylePublicOpenJoin.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public enum EMGroupStyle {
        EMGroupStylePrivateOnlyOwnerInvite,
        EMGroupStylePrivateMemberCanInvite,
        EMGroupStylePublicJoinNeedApproval,
        EMGroupStylePublicOpenJoin
    }

    public EMGroupManager(EMClient eMClient, EMAGroupManager eMAGroupManager) {
        this.emaObject = eMAGroupManager;
        this.mClient = eMClient;
        this.emaObject.addListener(this.listenerImpl);
        EMClient.getInstance().chatManager();
    }

    private EMGroup createGroup(int i2, String str, String str2, String[] strArr, int i3, String str3, boolean z2, String str4) throws HyphenateException {
        EMAGroupSetting eMAGroupSetting = new EMAGroupSetting(i2, i3, z2, str4);
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, strArr);
        EMAError eMAError = new EMAError();
        EMAGroup eMAGroupCreateGroup = this.emaObject.createGroup(str, str2, str3, eMAGroupSetting, arrayList, z2, eMAError);
        handleError(eMAError);
        return new EMGroup(eMAGroupCreateGroup);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleError(EMAError eMAError) throws HyphenateException {
        if (eMAError.errCode() != 0) {
            throw new HyphenateException(eMAError);
        }
    }

    public void acceptApplication(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.acceptJoinGroupApplication(str2, str, eMAError);
        handleError(eMAError);
    }

    public EMGroup acceptInvitation(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAGroupManager eMAGroupManager = this.emaObject;
        if (str2 == null) {
            str2 = "";
        }
        EMAGroup eMAGroupAcceptInvitationFromGroup = eMAGroupManager.acceptInvitationFromGroup(str, str2, eMAError);
        handleError(eMAError);
        return new EMGroup(eMAGroupAcceptInvitationFromGroup);
    }

    public EMGroup addGroupAdmin(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAGroup eMAGroupAddGroupAdmin = this.emaObject.addGroupAdmin(str, str2, eMAError);
        handleError(eMAError);
        return new EMGroup(eMAGroupAddGroupAdmin);
    }

    public void addGroupChangeListener(EMGroupChangeListener eMGroupChangeListener) {
        EMLog.d(TAG, "add group change listener:" + eMGroupChangeListener.getClass().getName());
        if (this.groupChangeListeners.contains(eMGroupChangeListener)) {
            return;
        }
        this.groupChangeListeners.add(eMGroupChangeListener);
    }

    public void addToGroupWhiteList(final String str, final List<String> list, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.38
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMGroupManager.this.emaObject.addToWhiteList(str, list, eMAError);
                    EMGroupManager.this.handleError(eMAError);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void addUsersToGroup(String str, String[] strArr) throws HyphenateException {
        addUsersToGroup(str, strArr, PLVChatFunctionSwitchVO.TYPE_WELCOME);
    }

    public void addUsersToGroup(String str, String[] strArr, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, strArr);
        this.emaObject.addGroupMembers(str, arrayList, str2, eMAError);
        handleError(eMAError);
    }

    public void applyJoinToGroup(String str, String str2) throws HyphenateException {
        String currentUser = this.mClient.getCurrentUser();
        EMAError eMAError = new EMAError();
        this.emaObject.applyJoinPublicGroup(str, currentUser, str2, eMAError);
        handleError(eMAError);
    }

    public void asyncAcceptApplication(final String str, final String str2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.19
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.acceptApplication(str, str2);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncAcceptInvitation(final String str, final String str2, final EMValueCallBack<EMGroup> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.17
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.acceptInvitation(str, str2));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncAddGroupAdmin(final String str, final String str2, final EMValueCallBack<EMGroup> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.32
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.addGroupAdmin(str, str2));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncAddUsersToGroup(final String str, final String[] strArr, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.addUsersToGroup(str, strArr);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncApplyJoinToGroup(final String str, final String str2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.22
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.applyJoinToGroup(str, str2);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncBlockGroupMessage(final String str, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.23
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.blockGroupMessage(str);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncBlockUser(final String str, final String str2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.25
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.blockUser(str, str2);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncBlockUsers(final String str, final List<String> list, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.26
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.blockUsers(str, list);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncChangeGroupDescription(final String str, final String str2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.16
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.changeGroupDescription(str, str2);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncChangeGroupName(final String str, final String str2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.15
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.changeGroupName(str, str2);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncChangeOwner(final String str, final String str2, final EMValueCallBack<EMGroup> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.31
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.changeOwner(str, str2));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncCreateGroup(final String str, final String str2, final String[] strArr, final String str3, final EMGroupOptions eMGroupOptions, final EMValueCallBack<EMGroup> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.createGroup(str, str2, strArr, str3, eMGroupOptions));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncDeclineApplication(final String str, final String str2, final String str3, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.20
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.declineApplication(str, str2, str3);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncDeclineInvitation(final String str, final String str2, final String str3, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.18
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.declineInvitation(str, str2, str3);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncDeleteGroupSharedFile(final String str, final String str2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.48
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.deleteGroupSharedFile(str, str2);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncDestroyGroup(final String str, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.destroyGroup(str);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncDownloadGroupSharedFile(final String str, final String str2, final String str3, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.49
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.downloadGroupSharedFile(str, str2, str3, eMCallBack);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchGroupAnnouncement(final String str, final EMValueCallBack<String> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.45
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.fetchGroupAnnouncement(str));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchGroupBlackList(final String str, final int i2, final int i3, final EMValueCallBack<List<String>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.37
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.fetchGroupBlackList(str, i2, i3));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchGroupMembers(final String str, final String str2, final int i2, final EMValueCallBack<EMCursorResult<String>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.30
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.fetchGroupMembers(str, str2, i2));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchGroupMuteList(final String str, final int i2, final int i3, final EMValueCallBack<Map<String, Long>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.36
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.fetchGroupMuteList(str, i2, i3));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncFetchGroupSharedFileList(final String str, final int i2, final int i3, final EMValueCallBack<List<EMMucSharedFile>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.47
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.fetchGroupSharedFileList(str, i2, i3));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncGetBlockedUsers(final String str, final int i2, final int i3, final EMValueCallBack<List<String>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.29
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.getBlockedUsers(str, i2, i3));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncGetBlockedUsers(String str, EMValueCallBack<List<String>> eMValueCallBack) {
        asyncGetBlockedUsers(str, 0, 200, eMValueCallBack);
    }

    public void asyncGetGroupFromServer(final String str, final EMValueCallBack<EMGroup> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.8
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.getGroupFromServer(str));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncGetGroupsFromServer(final EMValueCallBack<List<EMGroup>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.9
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.getGroupsFromServer());
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    @Deprecated
    public void asyncGetJoinedGroupsFromServer(final int i2, final int i3, final EMValueCallBack<List<EMGroup>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.11
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.getJoinedGroupsFromServer(i2, i3));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncGetJoinedGroupsFromServer(final int i2, final int i3, final boolean z2, final boolean z3, final EMValueCallBack<List<EMGroup>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.12
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.getJoinedGroupsFromServer(i2, i3, z2, z3));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncGetJoinedGroupsFromServer(final EMValueCallBack<List<EMGroup>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.10
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.getJoinedGroupsFromServer());
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncGetPublicGroupsFromServer(final int i2, final String str, final EMValueCallBack<EMCursorResult<EMGroupInfo>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.13
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.getPublicGroupsFromServer(i2, str));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncInviteUser(final String str, final String[] strArr, final String str2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.21
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.inviteUser(str, strArr, str2);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncJoinGroup(final String str, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.14
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.joinGroup(str);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncLeaveGroup(final String str, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.7
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.leaveGroup(str);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncRemoveGroupAdmin(final String str, final String str2, final EMValueCallBack<EMGroup> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.33
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.removeGroupAdmin(str, str2));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncRemoveUserFromGroup(final String str, final String str2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.removeUserFromGroup(str, str2);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncRemoveUsersFromGroup(final String str, final List<String> list, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.6
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.removeUsersFromGroup(str, list);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncUnMuteGroupMembers(final String str, final List<String> list, final EMValueCallBack<EMGroup> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.35
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.unMuteGroupMembers(str, list));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncUnblockGroupMessage(final String str, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.24
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.unblockGroupMessage(str);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncUnblockUser(final String str, final String str2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.27
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.unblockUser(str, str2);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncUnblockUsers(final String str, final List<String> list, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.28
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.unblockUsers(str, list);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncUpdateGroupAnnouncement(final String str, final String str2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.44
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.updateGroupAnnouncement(str, str2);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void asyncUploadGroupSharedFile(final String str, final String str2, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.46
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMGroupManager.this.uploadGroupSharedFile(str, str2, eMCallBack);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void aysncMuteGroupMembers(final String str, final List<String> list, final long j2, final EMValueCallBack<EMGroup> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.34
            @Override // java.lang.Runnable
            public void run() {
                try {
                    eMValueCallBack.onSuccess(EMGroupManager.this.muteGroupMembers(str, list, j2));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void blockGroupMessage(String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.blockGroupMessage(str, eMAError);
        handleError(eMAError);
    }

    public void blockUser(String str, String str2) throws HyphenateException {
        EMLog.d(TAG, "block user for groupid:" + str + " username:" + str2);
        EMAError eMAError = new EMAError();
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2);
        this.emaObject.blockGroupMembers(str, arrayList, eMAError, "");
        handleError(eMAError);
    }

    public void blockUsers(String str, List<String> list) throws HyphenateException {
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("block user for groupid:");
        sb.append(str);
        sb.append(" members:");
        sb.append(list);
        EMLog.d(str2, sb.toString() == null ? "" : list.toString());
        EMAError eMAError = new EMAError();
        this.emaObject.blockGroupMembers(str, list, eMAError, "");
        handleError(eMAError);
    }

    public void changeGroupDescription(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.changeGroupDescription(str, str2, eMAError);
        handleError(eMAError);
    }

    public void changeGroupName(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.changeGroupSubject(str, str2, eMAError);
        handleError(eMAError);
    }

    public EMGroup changeOwner(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAGroup eMAGroupTransferGroupOwner = this.emaObject.transferGroupOwner(str, str2, eMAError);
        handleError(eMAError);
        return new EMGroup(eMAGroupTransferGroupOwner);
    }

    public void checkIfInGroupWhiteList(final String str, final EMValueCallBack<Boolean> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.40
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    Boolean boolValueOf = Boolean.valueOf(EMGroupManager.this.emaObject.checkIfInWhiteList(str, eMAError));
                    EMGroupManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(boolValueOf);
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x001d A[PHI: r2
      0x001d: PHI (r2v3 int) = (r2v2 int), (r2v4 int) binds: [B:5:0x0011, B:9:0x0017] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.hyphenate.chat.EMGroup createGroup(java.lang.String r15, java.lang.String r16, java.lang.String[] r17, java.lang.String r18, com.hyphenate.chat.EMGroupOptions r19) throws com.hyphenate.exceptions.HyphenateException {
        /*
            r14 = this;
            r0 = r19
            int[] r1 = com.hyphenate.chat.EMGroupManager.AnonymousClass50.$SwitchMap$com$hyphenate$chat$EMGroupManager$EMGroupStyle
            com.hyphenate.chat.EMGroupManager$EMGroupStyle r2 = r0.style
            int r2 = r2.ordinal()
            r1 = r1[r2]
            r2 = 1
            r3 = 0
            if (r1 == r2) goto L19
            r4 = 2
            if (r1 == r4) goto L1d
            r2 = 3
            if (r1 == r2) goto L1b
            r4 = 4
            if (r1 == r4) goto L1d
        L19:
            r6 = r3
            goto L1e
        L1b:
            r6 = r4
            goto L1e
        L1d:
            r6 = r2
        L1e:
            int r10 = r0.maxUsers
            boolean r12 = r0.inviteNeedConfirm
            java.lang.String r13 = r0.extField
            r5 = r14
            r7 = r15
            r8 = r16
            r9 = r17
            r11 = r18
            com.hyphenate.chat.EMGroup r0 = r5.createGroup(r6, r7, r8, r9, r10, r11, r12, r13)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.chat.EMGroupManager.createGroup(java.lang.String, java.lang.String, java.lang.String[], java.lang.String, com.hyphenate.chat.EMGroupOptions):com.hyphenate.chat.EMGroup");
    }

    public void declineApplication(String str, String str2, String str3) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.declineJoinGroupApplication(str2, str, str3, eMAError);
        handleError(eMAError);
    }

    public void declineInvitation(String str, String str2, String str3) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAGroupManager eMAGroupManager = this.emaObject;
        if (str2 == null) {
            str2 = "";
        }
        if (str3 == null) {
            str3 = "";
        }
        eMAGroupManager.declineInvitationFromGroup(str, str2, str3, eMAError);
        handleError(eMAError);
    }

    public void deleteGroupSharedFile(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.deleteGroupShareFile(str, str2, eMAError);
        handleError(eMAError);
    }

    public void destroyGroup(String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.destroyGroup(str, eMAError);
        EMClient.getInstance().chatManager().caches.remove(str);
        handleError(eMAError);
    }

    public void downloadGroupSharedFile(String str, String str2, String str3, EMCallBack eMCallBack) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.downloadGroupShareFile(str, str2, str3, new EMACallback(eMCallBack), eMAError);
        handleError(eMAError);
    }

    public String fetchGroupAnnouncement(String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        String strFetchGroupAnnouncement = this.emaObject.fetchGroupAnnouncement(str, eMAError);
        handleError(eMAError);
        return strFetchGroupAnnouncement;
    }

    public List<String> fetchGroupBlackList(String str, int i2, int i3) throws HyphenateException {
        EMAError eMAError = new EMAError();
        List<String> listFetchGroupBlackList = this.emaObject.fetchGroupBlackList(str, i2, i3, eMAError);
        handleError(eMAError);
        return listFetchGroupBlackList;
    }

    public EMCursorResult<String> fetchGroupMembers(String str, String str2, int i2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMCursorResult<String> eMCursorResultFetchGroupMembers = this.emaObject.fetchGroupMembers(str, str2, i2, eMAError);
        handleError(eMAError);
        return eMCursorResultFetchGroupMembers;
    }

    public Map<String, Long> fetchGroupMuteList(String str, int i2, int i3) throws HyphenateException {
        EMAError eMAError = new EMAError();
        Map<String, Long> mapFetchGroupMutes = this.emaObject.fetchGroupMutes(str, i2, i3, eMAError);
        handleError(eMAError);
        return mapFetchGroupMutes;
    }

    public List<EMMucSharedFile> fetchGroupSharedFileList(String str, int i2, int i3) throws HyphenateException {
        EMAError eMAError = new EMAError();
        List<EMAMucShareFile> listFetchGroupShareFiles = this.emaObject.fetchGroupShareFiles(str, i2, i3, eMAError);
        handleError(eMAError);
        ArrayList arrayList = new ArrayList();
        Iterator<EMAMucShareFile> it = listFetchGroupShareFiles.iterator();
        while (it.hasNext()) {
            arrayList.add(new EMMucSharedFile(it.next()));
        }
        return arrayList;
    }

    public void fetchGroupWhiteList(final String str, final EMValueCallBack<List<String>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.41
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    List<String> listFetchGroupWhiteList = EMGroupManager.this.emaObject.fetchGroupWhiteList(str, eMAError);
                    EMGroupManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(listFetchGroupWhiteList);
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public List<EMGroup> getAllGroups() {
        List<EMAGroup> listAllMyGroups = this.emaObject.allMyGroups(new EMAError());
        ArrayList arrayList = new ArrayList();
        Iterator<EMAGroup> it = listAllMyGroups.iterator();
        while (it.hasNext()) {
            arrayList.add(new EMGroup(it.next()));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public List<String> getBlockedUsers(String str) throws HyphenateException {
        return getBlockedUsers(str, 0, 200);
    }

    public List<String> getBlockedUsers(String str, int i2, int i3) throws HyphenateException {
        EMLog.d(TAG, "get blocked users of the group: " + str);
        EMAError eMAError = new EMAError();
        List<String> listFetchGroupBlackList = this.emaObject.fetchGroupBlackList(str, i2, i3, eMAError);
        handleError(eMAError);
        return listFetchGroupBlackList;
    }

    public EMGroup getGroup(String str) {
        for (EMAGroup eMAGroup : this.emaObject.allMyGroups(new EMAError())) {
            if (eMAGroup.groupId().equals(str)) {
                return new EMGroup(eMAGroup);
            }
        }
        return null;
    }

    public EMGroup getGroupFromServer(String str) throws HyphenateException {
        if (str == null || str.isEmpty()) {
            throw new HyphenateException(600, "group id is null or empty");
        }
        EMAError eMAError = new EMAError();
        EMAGroup eMAGroupFetchGroupSpecification = this.emaObject.fetchGroupSpecification(str, eMAError, false);
        handleError(eMAError);
        return new EMGroup(eMAGroupFetchGroupSpecification);
    }

    public EMGroup getGroupFromServer(String str, boolean z2) throws HyphenateException {
        if (str == null || str.isEmpty()) {
            throw new HyphenateException(600, "group id is null or empty");
        }
        EMAError eMAError = new EMAError();
        EMAGroup eMAGroupFetchGroupSpecification = this.emaObject.fetchGroupSpecification(str, eMAError, z2);
        handleError(eMAError);
        return new EMGroup(eMAGroupFetchGroupSpecification);
    }

    public synchronized List<EMGroup> getGroupsFromServer() throws HyphenateException {
        ArrayList arrayList;
        EMAError eMAError = new EMAError();
        List<EMAGroup> listFetchAllMyGroups = this.emaObject.fetchAllMyGroups(eMAError);
        handleError(eMAError);
        arrayList = new ArrayList();
        Iterator<EMAGroup> it = listFetchAllMyGroups.iterator();
        while (it.hasNext()) {
            arrayList.add(new EMGroup(it.next()));
        }
        return arrayList;
    }

    public synchronized List<EMGroup> getGroupsFromServer(int i2, int i3) throws HyphenateException {
        ArrayList arrayList;
        EMAError eMAError = new EMAError();
        List<EMAGroup> listFetchAllMyGroupsWithPage = this.emaObject.fetchAllMyGroupsWithPage(i2, i3, eMAError);
        handleError(eMAError);
        arrayList = new ArrayList();
        Iterator<EMAGroup> it = listFetchAllMyGroupsWithPage.iterator();
        while (it.hasNext()) {
            arrayList.add(new EMGroup(it.next()));
        }
        return arrayList;
    }

    public synchronized List<EMGroup> getGroupsFromServer(int i2, int i3, boolean z2, boolean z3) throws HyphenateException {
        ArrayList arrayList;
        EMAError eMAError = new EMAError();
        List<EMAGroup> listFetchAllMyGroupsWithPage = this.emaObject.fetchAllMyGroupsWithPage(i2, i3, z2, z3, eMAError);
        handleError(eMAError);
        arrayList = new ArrayList();
        Iterator<EMAGroup> it = listFetchAllMyGroupsWithPage.iterator();
        while (it.hasNext()) {
            arrayList.add(new EMGroup(it.next()));
        }
        return arrayList;
    }

    public synchronized List<EMGroup> getJoinedGroupsFromServer() throws HyphenateException {
        return getGroupsFromServer();
    }

    @Deprecated
    public synchronized List<EMGroup> getJoinedGroupsFromServer(int i2, int i3) throws HyphenateException {
        return getGroupsFromServer(i2, i3);
    }

    public synchronized List<EMGroup> getJoinedGroupsFromServer(int i2, int i3, boolean z2, boolean z3) throws HyphenateException {
        return getGroupsFromServer(i2, i3, z2, z3);
    }

    public EMCursorResult<EMGroupInfo> getPublicGroupsFromServer(int i2, String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMCursorResult<EMGroupInfo> eMCursorResultFetchPublicGroupsWithCursor = this.emaObject.fetchPublicGroupsWithCursor(str, i2, eMAError);
        handleError(eMAError);
        return eMCursorResultFetchPublicGroupsWithCursor;
    }

    public void inviteUser(String str, String[] strArr, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, strArr);
        this.emaObject.addGroupMembers(str, arrayList, str2, eMAError);
        handleError(eMAError);
    }

    public void joinGroup(String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAGroup eMAGroupFetchGroupSpecification = this.emaObject.fetchGroupSpecification(str, eMAError, false);
        handleError(eMAError);
        if (eMAGroupFetchGroupSpecification.groupSetting() == null) {
            throw new HyphenateException();
        }
        if (eMAGroupFetchGroupSpecification.groupSetting().style() == 3) {
            this.emaObject.joinPublicGroup(str, eMAError);
        } else if (eMAGroupFetchGroupSpecification.groupSetting().style() != 2) {
            return;
        } else {
            this.emaObject.applyJoinPublicGroup(str, this.mClient.getCurrentUser(), "hello", eMAError);
        }
        handleError(eMAError);
    }

    public void leaveGroup(String str) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.leaveGroup(str, eMAError);
        EMClient.getInstance().chatManager().caches.remove(str);
        handleError(eMAError);
    }

    public synchronized void loadAllGroups() {
        this.emaObject.loadAllMyGroupsFromDB();
    }

    public void muteAllMembers(final String str, final EMValueCallBack<EMGroup> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.42
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMAGroup eMAGroupMuteAllMembers = EMGroupManager.this.emaObject.muteAllMembers(str, eMAError);
                    EMGroupManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(new EMGroup(eMAGroupMuteAllMembers));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public EMGroup muteGroupMembers(String str, List<String> list, long j2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAGroup eMAGroupMuteGroupMembers = this.emaObject.muteGroupMembers(str, list, j2, eMAError);
        handleError(eMAError);
        return new EMGroup(eMAGroupMuteGroupMembers);
    }

    public void onLogout() {
    }

    public void removeFromGroupWhiteList(final String str, final List<String> list, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.39
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMGroupManager.this.emaObject.removeFromWhiteList(str, list, eMAError);
                    EMGroupManager.this.handleError(eMAError);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public EMGroup removeGroupAdmin(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAGroup eMAGroupRemoveGroupAdmin = this.emaObject.removeGroupAdmin(str, str2, eMAError);
        handleError(eMAError);
        return new EMGroup(eMAGroupRemoveGroupAdmin);
    }

    public void removeGroupChangeListener(EMGroupChangeListener eMGroupChangeListener) {
        if (eMGroupChangeListener != null) {
            EMLog.d(TAG, "remove group change listener:" + eMGroupChangeListener.getClass().getName());
            this.groupChangeListeners.remove(eMGroupChangeListener);
        }
    }

    public void removeUserFromGroup(String str, String str2) throws HyphenateException {
        ArrayList arrayList = new ArrayList();
        EMAError eMAError = new EMAError();
        arrayList.add(str2);
        this.emaObject.removeGroupMembers(str, arrayList, eMAError);
        handleError(eMAError);
        this.emaObject.fetchGroupSpecification(str, eMAError, true);
        handleError(eMAError);
    }

    public void removeUsersFromGroup(String str, List<String> list) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.removeGroupMembers(str, list, eMAError);
        handleError(eMAError);
        this.emaObject.fetchGroupSpecification(str, eMAError, true);
        handleError(eMAError);
    }

    public EMGroup unMuteGroupMembers(String str, List<String> list) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAGroup eMAGroupUnMuteGroupMembers = this.emaObject.unMuteGroupMembers(str, list, eMAError);
        handleError(eMAError);
        return new EMGroup(eMAGroupUnMuteGroupMembers);
    }

    public void unblockGroupMessage(String str) throws HyphenateException {
        EMLog.d(TAG, "try to unblock group msg:" + str);
        EMAError eMAError = new EMAError();
        this.emaObject.unblockGroupMessage(str, eMAError);
        handleError(eMAError);
    }

    public void unblockUser(String str, String str2) throws HyphenateException {
        EMLog.d(TAG, "unblock user groupid:" + str + " username:" + str2);
        EMAError eMAError = new EMAError();
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2);
        this.emaObject.unblockGroupMembers(str, arrayList, eMAError);
        handleError(eMAError);
    }

    public void unblockUsers(String str, List<String> list) throws HyphenateException {
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("unblock user groupid:");
        sb.append(str);
        sb.append(" members:");
        sb.append(list);
        EMLog.d(str2, sb.toString() == null ? "" : list.toString());
        EMAError eMAError = new EMAError();
        this.emaObject.unblockGroupMembers(str, list, eMAError);
        handleError(eMAError);
    }

    public void unmuteAllMembers(final String str, final EMValueCallBack<EMGroup> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMGroupManager.43
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMAGroup eMAGroupUnmuteAllMembers = EMGroupManager.this.emaObject.unmuteAllMembers(str, eMAError);
                    EMGroupManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(new EMGroup(eMAGroupUnmuteAllMembers));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void updateGroupAnnouncement(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.updateGroupAnnouncement(str, str2, eMAError);
        handleError(eMAError);
    }

    public EMGroup updateGroupExtension(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAGroup eMAGroupUpdateGroupExtension = this.emaObject.updateGroupExtension(str, str2, eMAError);
        handleError(eMAError);
        return new EMGroup(eMAGroupUpdateGroupExtension);
    }

    public EMMucSharedFile uploadGroupSharedFile(String str, String str2, EMCallBack eMCallBack) throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAMucShareFile eMAMucShareFileUploadGroupShareFile = this.emaObject.uploadGroupShareFile(str, str2, new EMACallback(eMCallBack), eMAError);
        handleError(eMAError);
        eMCallBack.onSuccess();
        return new EMMucSharedFile(eMAMucShareFileUploadGroupShareFile);
    }
}
