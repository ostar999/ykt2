package com.easefun.polyv.livecommon.module.modules.streamer.presenter;

import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfigFiller;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicDataMapper;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract;
import com.easefun.polyv.livecommon.module.modules.streamer.model.PLVMemberItemDataBean;
import com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter;
import com.easefun.polyv.livescenes.socket.PolyvSocketWrapper;
import com.easefun.polyv.livescenes.streamer.listener.PLVSStreamerEventListener;
import com.plv.business.model.ppt.PLVPPTAuthentic;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.linkmic.PLVLinkMicEventHandler;
import com.plv.linkmic.model.PLVJoinRequestSEvent;
import com.plv.linkmic.model.PLVLinkMicMedia;
import com.plv.livescenes.document.model.PLVPPTStatus;
import com.plv.livescenes.streamer.transfer.PLVStreamerInnerDataTransfer;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.chat.PLVBanIpEvent;
import com.plv.socket.event.chat.PLVSetNickEvent;
import com.plv.socket.event.chat.PLVUnshieldEvent;
import com.plv.socket.event.linkmic.PLVJoinAnswerSEvent;
import com.plv.socket.event.linkmic.PLVJoinLeaveSEvent;
import com.plv.socket.event.login.PLVKickEvent;
import com.plv.socket.event.login.PLVLoginEvent;
import com.plv.socket.event.login.PLVLogoutEvent;
import com.plv.socket.event.ppt.PLVOnSliceIDEvent;
import com.plv.socket.event.ppt.PLVOnSliceStartEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.socketio.PLVSocketIOObservable;
import com.plv.socket.status.PLVSocketStatus;
import com.plv.socket.user.PLVSocketUserBean;
import com.plv.socket.user.PLVSocketUserConstant;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class PLVStreamerMsgHandler {
    private static final String LINK_MIC_TYPE_AUDIO = "audio";
    private static final String TAG = "PLVStreamerMsgHandler";

    @Nullable
    private String lastFirstScreenUserId;
    private PLVSStreamerEventListener linkMicEventHandler;
    private PLVSocketIOObservable.OnConnectStatusListener onConnectStatusListener;
    private PLVSocketMessageObserver.OnMessageListener onMessageListener;
    private final PLVStreamerPresenter streamerPresenter;

    public PLVStreamerMsgHandler(PLVStreamerPresenter streamerPresenter) {
        this.streamerPresenter = streamerPresenter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptBanIpEvent(PLVBanIpEvent banIpEvent) {
        List<PLVSocketUserBean> userIds;
        if (banIpEvent == null || (userIds = banIpEvent.getUserIds()) == null) {
            return;
        }
        Iterator<PLVSocketUserBean> it = userIds.iterator();
        while (it.hasNext()) {
            final Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId = this.streamerPresenter.getMemberItemWithUserId(it.next().getUserId());
            if (memberItemWithUserId != null) {
                ((PLVMemberItemDataBean) memberItemWithUserId.second).getSocketUserBean().setBanned(true);
                this.streamerPresenter.callbackToView(new PLVStreamerPresenter.ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerMsgHandler.3
                    @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onUpdateSocketUserData(((Integer) memberItemWithUserId.first).intValue());
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptJoinAnswerSEvent(PLVJoinAnswerSEvent joinAnswerSEvent) {
        if (joinAnswerSEvent != null) {
            String userId = joinAnswerSEvent.getUserId();
            if (joinAnswerSEvent.isRefuse()) {
                updateMemberListWithLeave(userId);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptJoinLeaveSEvent(PLVJoinLeaveSEvent joinLeaveSEvent) {
        if (joinLeaveSEvent == null || joinLeaveSEvent.getUser() == null) {
            return;
        }
        if (this.streamerPresenter.getLiveRoomDataManager().getConfig().getUser().getViewerId().equals(joinLeaveSEvent.getUser().getUserId()) && this.streamerPresenter.getLiveRoomDataManager().getConfig().getUser().getViewerType().equals("guest")) {
            PLVCommonLog.d(TAG, "guest receive joinLeave");
        } else {
            updateMemberListWithLeave(joinLeaveSEvent.getUser().getUserId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptJoinRequestSEvent(PLVJoinRequestSEvent joinRequestSEvent) {
        if (joinRequestSEvent == null || joinRequestSEvent.getUser() == null) {
            return;
        }
        PLVSocketUserBean pLVSocketUserBeanMap2SocketUserBean = PLVLinkMicDataMapper.map2SocketUserBean(joinRequestSEvent.getUser());
        final PLVLinkMicItemDataBean pLVLinkMicItemDataBeanMap2LinkMicItemData = PLVLinkMicDataMapper.map2LinkMicItemData(joinRequestSEvent.getUser());
        if (this.streamerPresenter.updateMemberListItemInfo(pLVSocketUserBeanMap2SocketUserBean, pLVLinkMicItemDataBeanMap2LinkMicItemData, false, true)) {
            this.streamerPresenter.callUpdateSortMemberList();
            if (!this.streamerPresenter.getLiveRoomDataManager().getConfig().getUser().getViewerType().equals("guest")) {
                this.streamerPresenter.getData().postUserRequestData(pLVLinkMicItemDataBeanMap2LinkMicItemData.getLinkMicId());
            }
            this.streamerPresenter.callbackToView(new PLVStreamerPresenter.ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerMsgHandler.10
                @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                    view.onUserRequest(pLVLinkMicItemDataBeanMap2LinkMicItemData.getLinkMicId());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptKickEvent(PLVKickEvent kickEvent) {
        final Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId;
        if (kickEvent == null || kickEvent.getUser() == null || (memberItemWithUserId = this.streamerPresenter.getMemberItemWithUserId(kickEvent.getUser().getUserId())) == null) {
            return;
        }
        this.streamerPresenter.memberList.remove(memberItemWithUserId.second);
        this.streamerPresenter.callbackToView(new PLVStreamerPresenter.ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerMsgHandler.6
            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onRemoveMemberListData(((Integer) memberItemWithUserId.first).intValue());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptLoginEvent(PLVLoginEvent loginEvent) {
        if (loginEvent == null || loginEvent.getUser() == null || PLVSocketUserConstant.USERSOURCE_CHATROOM.equals(loginEvent.getUser().getUserSource()) || this.streamerPresenter.getMemberItemWithUserId(loginEvent.getUser().getUserId()) != null) {
            return;
        }
        PLVMemberItemDataBean pLVMemberItemDataBean = new PLVMemberItemDataBean();
        pLVMemberItemDataBean.setSocketUserBean(loginEvent.getUser());
        this.streamerPresenter.memberList.add(pLVMemberItemDataBean);
        PLVStreamerPresenter.SortMemberListUtils.sort(this.streamerPresenter.memberList);
        final Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId = this.streamerPresenter.getMemberItemWithUserId(loginEvent.getUser().getUserId());
        this.streamerPresenter.callbackToView(new PLVStreamerPresenter.ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerMsgHandler.7
            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onAddMemberListData(((Integer) memberItemWithUserId.first).intValue());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptLogoutEvent(PLVLogoutEvent logoutEvent) {
        final Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId;
        if (logoutEvent == null || (memberItemWithUserId = this.streamerPresenter.getMemberItemWithUserId(logoutEvent.getUserId())) == null) {
            return;
        }
        this.streamerPresenter.memberList.remove(memberItemWithUserId.second);
        this.streamerPresenter.callbackToView(new PLVStreamerPresenter.ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerMsgHandler.8
            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onRemoveMemberListData(((Integer) memberItemWithUserId.first).intValue());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptOnSliceIDEvent(PLVOnSliceIDEvent onSliceIDEvent) {
        if (onSliceIDEvent == null || onSliceIDEvent.getData() == null) {
            return;
        }
        this.streamerPresenter.getLiveRoomDataManager().setSessionId(onSliceIDEvent.getData().getSessionId());
        if ("audio".equals(onSliceIDEvent.getData().getAvConnectMode())) {
            this.streamerPresenter.callUpdateGuestMediaStatus(true, true);
        }
        if (PLVLiveChannelConfigFiller.generateNewChannelConfig().isLiveStreamingWhenLogin()) {
            PLVPPTStatus pLVPPTStatus = new PLVPPTStatus();
            PLVOnSliceIDEvent.DataBean data = onSliceIDEvent.getData();
            pLVPPTStatus.setAutoId(data.getAutoId());
            pLVPPTStatus.setStep(PLVFormatUtils.integerValueOf(data.getStep(), 0).intValue());
            pLVPPTStatus.setPageId(data.getPageId());
            PLVStreamerInnerDataTransfer.getInstance().setPPTStatusForOnSliceStartEvent(pLVPPTStatus);
        }
        final boolean z2 = onSliceIDEvent.getPptAndVedioPosition() == 0;
        this.streamerPresenter.callbackToView(new PLVStreamerPresenter.ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerMsgHandler.9
            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onDocumentStreamerViewChange(z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptOnSliceStartEvent(PLVOnSliceStartEvent onSliceStartEvent) {
        if (onSliceStartEvent == null || onSliceStartEvent.getData() == null || !this.streamerPresenter.getLiveRoomDataManager().getConfig().getUser().getViewerType().equals("guest")) {
            return;
        }
        this.streamerPresenter.getLiveRoomDataManager().setSessionId(onSliceStartEvent.getSessionId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptSetNickEvent(PLVSetNickEvent setNickEvent) {
        final Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId;
        if (setNickEvent == null || !"success".equals(setNickEvent.getStatus()) || (memberItemWithUserId = this.streamerPresenter.getMemberItemWithUserId(setNickEvent.getUserId())) == null) {
            return;
        }
        ((PLVMemberItemDataBean) memberItemWithUserId.second).getSocketUserBean().setNick(setNickEvent.getNick());
        this.streamerPresenter.callbackToView(new PLVStreamerPresenter.ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerMsgHandler.5
            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onUpdateSocketUserData(((Integer) memberItemWithUserId.first).intValue());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptTeacherSetPermissionEvent(final PLVPPTAuthentic authentic) {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        Object obj5;
        if (authentic != null) {
            Pair<Integer, PLVMemberItemDataBean> memberItemWithLinkMicId = this.streamerPresenter.getMemberItemWithLinkMicId(authentic.getUserId());
            Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = this.streamerPresenter.getLinkMicItemWithLinkMicId(authentic.getUserId());
            if ("speaker".equals(authentic.getType())) {
                if (memberItemWithLinkMicId != null && (obj5 = memberItemWithLinkMicId.second) != null) {
                    ((PLVMemberItemDataBean) obj5).getLinkMicItemDataBean().setHasSpeaker(!authentic.hasNoAthuentic());
                }
                if (linkMicItemWithLinkMicId != null && (obj4 = linkMicItemWithLinkMicId.second) != null) {
                    ((PLVLinkMicItemDataBean) obj4).setHasSpeaker(!authentic.hasNoAthuentic());
                }
            } else if (PLVPPTAuthentic.PermissionType.SCREEN_SHARE.equals(authentic.getType())) {
                if (memberItemWithLinkMicId != null && (obj2 = memberItemWithLinkMicId.second) != null) {
                    ((PLVMemberItemDataBean) obj2).getLinkMicItemDataBean().setScreenShare(!authentic.hasNoAthuentic());
                }
                if (linkMicItemWithLinkMicId != null && (obj = linkMicItemWithLinkMicId.second) != null) {
                    ((PLVLinkMicItemDataBean) obj).setScreenShare(!authentic.hasNoAthuentic());
                }
            }
            this.streamerPresenter.onCurrentSpeakerChanged(authentic.getType(), !authentic.hasNoAthuentic(), authentic.getUserId().equals(this.streamerPresenter.getStreamerManager().getLinkMicUid()), (memberItemWithLinkMicId == null || (obj3 = memberItemWithLinkMicId.second) == null) ? null : ((PLVMemberItemDataBean) obj3).getSocketUserBean());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptUnshieldEvent(PLVUnshieldEvent unshieldEvent) {
        List<PLVSocketUserBean> userIds;
        if (unshieldEvent == null || (userIds = unshieldEvent.getUserIds()) == null) {
            return;
        }
        Iterator<PLVSocketUserBean> it = userIds.iterator();
        while (it.hasNext()) {
            final Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId = this.streamerPresenter.getMemberItemWithUserId(it.next().getUserId());
            if (memberItemWithUserId != null) {
                ((PLVMemberItemDataBean) memberItemWithUserId.second).getSocketUserBean().setBanned(false);
                this.streamerPresenter.callbackToView(new PLVStreamerPresenter.ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerMsgHandler.4
                    @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onUpdateSocketUserData(((Integer) memberItemWithUserId.first).intValue());
                    }
                });
            }
        }
    }

    private void observeSocketData() {
        this.onConnectStatusListener = new PLVSocketIOObservable.OnConnectStatusListener() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerMsgHandler.1
            @Override // com.plv.socket.socketio.PLVSocketIOObservable.OnConnectStatusListener
            public void onStatus(PLVSocketStatus status) {
                if (4 == status.getStatus()) {
                    PLVStreamerMsgHandler.this.streamerPresenter.requestMemberList();
                }
            }
        };
        this.onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerMsgHandler.2
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String listenEvent, String event, String message) {
                event.hashCode();
                switch (event) {
                    case "SET_NICK":
                        PLVStreamerMsgHandler.this.acceptSetNickEvent((PLVSetNickEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVSetNickEvent.class));
                        break;
                    case "LOGOUT":
                        PLVStreamerMsgHandler.this.acceptLogoutEvent((PLVLogoutEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVLogoutEvent.class));
                        break;
                    case "changeVideoAndPPTPosition":
                        PLVStreamerMsgHandler.this.updateDocumentStreamerViewPosition((PLVPPTAuthentic) PLVGsonUtil.fromJson(PLVPPTAuthentic.class, message));
                        break;
                    case "joinRequest":
                        PLVStreamerMsgHandler.this.acceptJoinRequestSEvent((PLVJoinRequestSEvent) PLVGsonUtil.fromJson(PLVJoinRequestSEvent.class, message));
                        break;
                    case "onSliceID":
                        PLVStreamerMsgHandler.this.acceptOnSliceIDEvent((PLVOnSliceIDEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVOnSliceIDEvent.class));
                        break;
                    case "onSliceStart":
                        PLVStreamerMsgHandler.this.acceptOnSliceStartEvent((PLVOnSliceStartEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVOnSliceStartEvent.class));
                        break;
                    case "joinLeave":
                        PLVStreamerMsgHandler.this.acceptJoinLeaveSEvent((PLVJoinLeaveSEvent) PLVGsonUtil.fromJson(PLVJoinLeaveSEvent.class, message));
                        break;
                    case "switchView":
                        PLVStreamerMsgHandler.this.updateFirstScreen((PLVPPTAuthentic) PLVGsonUtil.fromJson(PLVPPTAuthentic.class, message));
                        break;
                    case "KICK":
                        PLVStreamerMsgHandler.this.acceptKickEvent((PLVKickEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVKickEvent.class));
                        break;
                    case "BANIP":
                        PLVStreamerMsgHandler.this.acceptBanIpEvent((PLVBanIpEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVBanIpEvent.class));
                        break;
                    case "LOGIN":
                        PLVStreamerMsgHandler.this.acceptLoginEvent((PLVLoginEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVLoginEvent.class));
                        break;
                    case "TEACHER_SET_PERMISSION":
                        PLVStreamerMsgHandler.this.acceptTeacherSetPermissionEvent((PLVPPTAuthentic) PLVGsonUtil.fromJson(PLVPPTAuthentic.class, message));
                        break;
                    case "UNSHIELD":
                        PLVStreamerMsgHandler.this.acceptUnshieldEvent((PLVUnshieldEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVUnshieldEvent.class));
                        break;
                    case "joinAnswer":
                        PLVStreamerMsgHandler.this.acceptJoinAnswerSEvent((PLVJoinAnswerSEvent) PLVGsonUtil.fromJson(PLVJoinAnswerSEvent.class, message));
                        break;
                    case "MUTE_USER_MICRO":
                        PLVLinkMicMedia pLVLinkMicMedia = (PLVLinkMicMedia) PLVGsonUtil.fromJson(PLVLinkMicMedia.class, message);
                        if (pLVLinkMicMedia != null) {
                            PLVStreamerMsgHandler.this.streamerPresenter.callUpdateGuestMediaStatus(pLVLinkMicMedia.isMute(), "audio".equals(pLVLinkMicMedia.getType()));
                            break;
                        }
                        break;
                }
            }
        };
        PolyvSocketWrapper.getInstance().getSocketObserver().addOnConnectStatusListener(this.onConnectStatusListener);
        PolyvSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.onMessageListener, PLVEventConstant.LinkMic.JOIN_REQUEST_EVENT, PLVEventConstant.LinkMic.JOIN_RESPONSE_EVENT, "joinSuccess", PLVEventConstant.LinkMic.JOIN_LEAVE_EVENT, PLVEventConstant.LinkMic.JOIN_ANSWER_EVENT, PLVEventConstant.Class.SE_SWITCH_MESSAGE, "message");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDocumentStreamerViewPosition(final PLVPPTAuthentic authentic) {
        if (authentic == null) {
            return;
        }
        final boolean zEquals = "0".equals(authentic.getStatus());
        this.streamerPresenter.callbackToView(new PLVStreamerPresenter.ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerMsgHandler.11
            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onDocumentStreamerViewChange(zEquals);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFirstScreen(final PLVPPTAuthentic authentic) {
        if (authentic == null || authentic.getUserId() == null) {
            return;
        }
        this.streamerPresenter.onFirstScreenChange(authentic.getUserId(), !authentic.hasNoAthuentic());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMemberListWithJoin(final String linkMicUid) {
        if (!this.streamerPresenter.rtcJoinMap.containsKey(linkMicUid)) {
            PLVLinkMicItemDataBean pLVLinkMicItemDataBean = new PLVLinkMicItemDataBean();
            pLVLinkMicItemDataBean.setLinkMicId(linkMicUid);
            this.streamerPresenter.rtcJoinMap.put(linkMicUid, pLVLinkMicItemDataBean);
        }
        Pair<Integer, PLVMemberItemDataBean> memberItemWithLinkMicId = this.streamerPresenter.getMemberItemWithLinkMicId(linkMicUid);
        if (memberItemWithLinkMicId == null || !this.streamerPresenter.updateMemberListLinkMicStatusWithRtcJoinList((PLVMemberItemDataBean) memberItemWithLinkMicId.second, linkMicUid)) {
            return;
        }
        this.streamerPresenter.callUpdateSortMemberList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMemberListWithLeave(final String linkMicUid) {
        this.streamerPresenter.rtcJoinMap.remove(linkMicUid);
        Pair<Integer, PLVMemberItemDataBean> memberItemWithLinkMicId = this.streamerPresenter.getMemberItemWithLinkMicId(linkMicUid);
        if (memberItemWithLinkMicId != null) {
            ((PLVMemberItemDataBean) memberItemWithLinkMicId.second).getLinkMicItemDataBean().setStatus(PLVLinkMicItemDataBean.STATUS_IDLE);
            this.streamerPresenter.callUpdateSortMemberList();
        }
        final Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = this.streamerPresenter.getLinkMicItemWithLinkMicId(linkMicUid);
        if (linkMicItemWithLinkMicId != null) {
            this.streamerPresenter.streamerList.remove(linkMicItemWithLinkMicId.second);
            this.streamerPresenter.callbackToView(new PLVStreamerPresenter.ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerMsgHandler.13
                @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                    view.onUsersLeave(Collections.singletonList((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second));
                }
            });
            this.streamerPresenter.updateMixLayoutUsers();
            this.streamerPresenter.updateLinkMicCount();
        }
    }

    public void destroy() {
        PolyvSocketWrapper.getInstance().getSocketObserver().removeOnConnectStatusListener(this.onConnectStatusListener);
        PolyvSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
        this.streamerPresenter.getStreamerManager().removeEventHandler(this.linkMicEventHandler);
    }

    public void observeLinkMicData() {
        this.linkMicEventHandler = new PLVSStreamerEventListener() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerMsgHandler.12
            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onJoinChannelSuccess(String uid) {
                super.onJoinChannelSuccess(uid);
                PLVCommonLog.d(PLVStreamerMsgHandler.TAG, "onJoinChannelSuccess: " + uid);
                if ("guest".equals(PLVStreamerMsgHandler.this.streamerPresenter.getLiveRoomDataManager().getConfig().getUser().getViewerType())) {
                    PLVStreamerMsgHandler.this.streamerPresenter.getStreamerManager().switchRoleToBroadcaster();
                    PLVStreamerMsgHandler.this.streamerPresenter.callUpdateGuestStatus(true);
                }
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onLeaveChannel() {
                super.onLeaveChannel();
                PLVCommonLog.d(PLVStreamerMsgHandler.TAG, "onLeaveChannel");
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onLocalAudioVolumeIndication(final PLVLinkMicEventHandler.PLVAudioVolumeInfo speaker) {
                super.onLocalAudioVolumeIndication(speaker);
                Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = PLVStreamerMsgHandler.this.streamerPresenter.getLinkMicItemWithLinkMicId(speaker.getUid());
                if (linkMicItemWithLinkMicId != null) {
                    ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).setCurVolume(speaker.getVolume());
                }
                PLVStreamerMsgHandler.this.streamerPresenter.callbackToView(new PLVStreamerPresenter.ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerMsgHandler.12.2
                    @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onLocalUserMicVolumeChanged(speaker.getVolume());
                    }
                });
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onRemoteAudioVolumeIndication(PLVLinkMicEventHandler.PLVAudioVolumeInfo[] speakers) {
                String linkMicId;
                boolean z2;
                super.onRemoteAudioVolumeIndication(speakers);
                Iterator<PLVMemberItemDataBean> it = PLVStreamerMsgHandler.this.streamerPresenter.memberList.iterator();
                while (it.hasNext()) {
                    PLVLinkMicItemDataBean linkMicItemDataBean = it.next().getLinkMicItemDataBean();
                    if (linkMicItemDataBean != null && (linkMicId = linkMicItemDataBean.getLinkMicId()) != null && !linkMicId.equals(PLVStreamerMsgHandler.this.streamerPresenter.getStreamerManager().getLinkMicUid())) {
                        int length = speakers.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length) {
                                z2 = false;
                                break;
                            }
                            PLVLinkMicEventHandler.PLVAudioVolumeInfo pLVAudioVolumeInfo = speakers[i2];
                            if (linkMicId.equals(pLVAudioVolumeInfo.getUid())) {
                                linkMicItemDataBean.setCurVolume(pLVAudioVolumeInfo.getVolume());
                                z2 = true;
                                break;
                            }
                            i2++;
                        }
                        if (!z2) {
                            linkMicItemDataBean.setCurVolume(0);
                        }
                    }
                }
                PLVStreamerMsgHandler.this.streamerPresenter.callbackToView(new PLVStreamerPresenter.ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerMsgHandler.12.1
                    @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onRemoteUserVolumeChanged(PLVStreamerMsgHandler.this.streamerPresenter.memberList);
                    }
                });
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onUserJoined(String uid) {
                super.onUserJoined(uid);
                PLVCommonLog.d(PLVStreamerMsgHandler.TAG, "onUserJoined: " + uid);
                PLVStreamerMsgHandler.this.updateMemberListWithJoin(uid);
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onUserMuteAudio(final String uid, final boolean mute) {
                super.onUserMuteAudio(uid, mute);
                PLVCommonLog.d(PLVStreamerMsgHandler.TAG, "onUserMuteAudio: " + uid + "*" + mute);
                PLVStreamerMsgHandler.this.streamerPresenter.callUserMuteAudio(uid, mute);
                for (Map.Entry<String, PLVLinkMicItemDataBean> entry : PLVStreamerMsgHandler.this.streamerPresenter.rtcJoinMap.entrySet()) {
                    if (uid != null && uid.equals(entry.getKey())) {
                        entry.getValue().setMuteAudioInRtcJoinList(new PLVLinkMicItemDataBean.MuteMedia(mute));
                    }
                }
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onUserMuteVideo(String uid, boolean mute) {
                super.onUserMuteVideo(uid, mute);
                PLVCommonLog.d(PLVStreamerMsgHandler.TAG, "onUserMuteVideo: " + uid + "*" + mute);
                PLVStreamerMsgHandler.this.streamerPresenter.callUserMuteVideo(uid, mute);
                for (Map.Entry<String, PLVLinkMicItemDataBean> entry : PLVStreamerMsgHandler.this.streamerPresenter.rtcJoinMap.entrySet()) {
                    if (uid != null && uid.equals(entry.getKey())) {
                        entry.getValue().setMuteVideoInRtcJoinList(new PLVLinkMicItemDataBean.MuteMedia(mute));
                    }
                }
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onUserOffline(String uid) {
                super.onUserOffline(uid);
                PLVCommonLog.d(PLVStreamerMsgHandler.TAG, "onUserOffline: " + uid);
                PLVStreamerMsgHandler.this.updateMemberListWithLeave(uid);
            }
        };
        this.streamerPresenter.getStreamerManager().addEventHandler(this.linkMicEventHandler);
    }

    public void run() {
        observeSocketData();
    }
}
