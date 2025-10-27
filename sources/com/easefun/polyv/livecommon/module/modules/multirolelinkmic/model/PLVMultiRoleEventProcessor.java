package com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.linkmic.model.PLVMicphoneStatus;
import com.plv.linkmic.model.PLVNetworkStatusVO;
import com.plv.livescenes.document.event.PLVSwitchRoomEvent;
import com.plv.livescenes.linkmic.IPLVLinkMicManager;
import com.plv.livescenes.linkmic.listener.PLVLinkMicEventListener;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.chat.PLVOTeacherInfoEvent;
import com.plv.socket.event.linkmic.PLVJoinLeaveSEvent;
import com.plv.socket.event.linkmic.PLVJoinResponseSEvent;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import com.plv.socket.event.linkmic.PLVTeacherSetPermissionEvent;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import com.plv.socket.event.login.PLVLoginEvent;
import com.plv.socket.event.ppt.PLVFinishClassEvent;
import com.plv.socket.event.ppt.PLVOnSliceIDEvent;
import com.plv.socket.event.ppt.PLVOnSliceStartEvent;
import com.plv.socket.event.seminar.PLVDiscussAckResult;
import com.plv.socket.event.seminar.PLVHostSendToAllGroupEvent;
import com.plv.socket.event.seminar.PLVJoinDiscussEvent;
import com.plv.socket.event.seminar.PLVJoinSuccessEvent;
import com.plv.socket.event.seminar.PLVLeaveDiscussEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.user.PLVClassStatusBean;
import java.util.Map;

/* loaded from: classes3.dex */
public class PLVMultiRoleEventProcessor {
    private static final String TAG = "PLVMultiRoleEventProcessor";
    private String groupId;
    private String groupLeaderId;
    private boolean isInClassStatusInDiscuss;
    private boolean isTeacherType;
    private PLVLinkMicEventListener linkMicEventListener;

    @Nullable
    private IPLVLinkMicManager linkMicManager;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private String myLinkMicId;
    private OnEventProcessorListener onEventProcessorListener;
    private PLVSocketMessageObserver.OnMessageListener onMessageListener;
    private boolean sendJoinDiscussMsgFlag;

    public interface OnEventProcessorListener {
        void onAcceptMyJoinLeave(boolean isByTeacherControl);

        void onChangeLinkMicZoom(@Nullable Map<String, PLVUpdateMicSiteEvent> updateMicSiteEventMap);

        void onJoinChannelSuccess();

        void onJoinDiscuss(String groupId, boolean isInClass, @Nullable String leaderId, PLVSwitchRoomEvent switchRoomEvent);

        void onLeaderCancelHelp();

        void onLeaderRequestHelp();

        void onLeaveChannel();

        void onLeaveDiscuss(PLVSwitchRoomEvent switchRoomEvent);

        void onNetworkQuality(int quality);

        void onRemoteNetworkStatus(PLVNetworkStatusVO networkStatusVO);

        void onRemoveLinkMicZoom(PLVRemoveMicSiteEvent removeMicSiteEvent);

        void onResponseJoin(boolean isNeedAnswer);

        void onResponseJoinForDiscuss();

        void onSliceStart(PLVOnSliceStartEvent onSliceStartEvent);

        void onTeacherInfo(String nick);

        void onTeacherJoinDiscuss(boolean isJoin);

        void onTeacherMuteMyMedia(boolean isVideoType, boolean isMute);

        void onTeacherSendBroadcast(String content);

        void onUpdateLinkMicZoom(PLVUpdateMicSiteEvent updateMicSiteEvent);

        void onUpstreamNetworkStatus(PLVNetworkStatusVO networkStatusVO);

        void onUserLogin(PLVLoginEvent loginEvent);
    }

    public PLVMultiRoleEventProcessor(IPLVLiveRoomDataManager liveRoomDataManager) {
        this.liveRoomDataManager = liveRoomDataManager;
        this.isTeacherType = "teacher".equals(liveRoomDataManager.getConfig().getUser().getViewerType());
        observeSocketEvent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptCancelHelp() {
        OnEventProcessorListener onEventProcessorListener = this.onEventProcessorListener;
        if (onEventProcessorListener != null) {
            onEventProcessorListener.onLeaderCancelHelp();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptFinishClassEvent(PLVFinishClassEvent finishClassEvent) {
        OnEventProcessorListener onEventProcessorListener;
        if (this.isTeacherType || (onEventProcessorListener = this.onEventProcessorListener) == null) {
            return;
        }
        onEventProcessorListener.onAcceptMyJoinLeave(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptHostJoinEvent() {
        OnEventProcessorListener onEventProcessorListener = this.onEventProcessorListener;
        if (onEventProcessorListener != null) {
            onEventProcessorListener.onTeacherJoinDiscuss(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptHostLeaveEvent() {
        OnEventProcessorListener onEventProcessorListener = this.onEventProcessorListener;
        if (onEventProcessorListener != null) {
            onEventProcessorListener.onTeacherJoinDiscuss(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptHostSendToAllGroupEvent(PLVHostSendToAllGroupEvent hostSendToAllGroupEvent) {
        OnEventProcessorListener onEventProcessorListener;
        if (hostSendToAllGroupEvent == null || (onEventProcessorListener = this.onEventProcessorListener) == null) {
            return;
        }
        onEventProcessorListener.onTeacherSendBroadcast(hostSendToAllGroupEvent.getContent());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptJoinDiscussEvent(final PLVJoinDiscussEvent joinDiscussEvent) {
        if (joinDiscussEvent == null) {
            return;
        }
        this.groupId = joinDiscussEvent.getGroupId();
        this.sendJoinDiscussMsgFlag = true;
        this.isInClassStatusInDiscuss = false;
        this.groupLeaderId = null;
        PLVSocketWrapper.getInstance().emit(PLVEventConstant.Seminar.SEMINAR_EVENT, PLVGsonUtil.toJson(new PLVJoinDiscussEvent()), new IPLVLinkMicEventSender.PLVSMainCallAck() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.2
            @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.PLVSMainCallAck
            public void onCall(Object... args) {
                Object obj;
                PLVDiscussAckResult pLVDiscussAckResult;
                PLVMultiRoleEventProcessor.this.sendJoinDiscussMsgFlag = false;
                if (args == null || args.length == 0 || (obj = args[0]) == null || (pLVDiscussAckResult = (PLVDiscussAckResult) PLVGsonUtil.fromJson(PLVDiscussAckResult.class, obj.toString())) == null || !pLVDiscussAckResult.isSuccess()) {
                    return;
                }
                PLVSwitchRoomEvent pLVSwitchRoomEventFromDataBean = PLVSwitchRoomEvent.fromDataBean(pLVDiscussAckResult.getData());
                if (PLVMultiRoleEventProcessor.this.onEventProcessorListener != null) {
                    PLVMultiRoleEventProcessor.this.onEventProcessorListener.onJoinDiscuss(joinDiscussEvent.getGroupId(), PLVMultiRoleEventProcessor.this.isInClassStatusInDiscuss, PLVMultiRoleEventProcessor.this.groupLeaderId, pLVSwitchRoomEventFromDataBean);
                }
                PLVSocketWrapper.getInstance().emit("joinSuccess", PLVGsonUtil.toJson(new PLVJoinSuccessEvent()), null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptJoinLeaveSEvent(PLVJoinLeaveSEvent joinLeaveSEvent) {
        OnEventProcessorListener onEventProcessorListener;
        if (joinLeaveSEvent == null || joinLeaveSEvent.getUser() == null || !isMyLinkMicId(joinLeaveSEvent.getUser().getUserId()) || (onEventProcessorListener = this.onEventProcessorListener) == null) {
            return;
        }
        onEventProcessorListener.onAcceptMyJoinLeave(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptJoinResponseSEvent(PLVJoinResponseSEvent joinResponseSEvent) {
        OnEventProcessorListener onEventProcessorListener;
        if (joinResponseSEvent != null) {
            if ((TextUtils.isEmpty(this.groupId) || this.groupId.equals(joinResponseSEvent.getRoomId())) && (onEventProcessorListener = this.onEventProcessorListener) != null) {
                onEventProcessorListener.onResponseJoin(joinResponseSEvent.isNeedAnswer());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptLeaveDiscussEvent(PLVLeaveDiscussEvent leaveDiscussEvent) {
        this.groupId = null;
        PLVSocketWrapper.getInstance().emit(PLVEventConstant.Seminar.SEMINAR_EVENT, PLVGsonUtil.toJson(new PLVLeaveDiscussEvent()), new IPLVLinkMicEventSender.PLVSMainCallAck() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.3
            @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.PLVSMainCallAck
            public void onCall(Object... args) {
                Object obj;
                if (args == null || args.length == 0 || (obj = args[0]) == null) {
                    return;
                }
                final PLVDiscussAckResult pLVDiscussAckResult = (PLVDiscussAckResult) PLVGsonUtil.fromJson(PLVDiscussAckResult.class, obj.toString());
                if (pLVDiscussAckResult != null && pLVDiscussAckResult.isSuccess()) {
                    PLVSwitchRoomEvent pLVSwitchRoomEventFromDataBean = PLVSwitchRoomEvent.fromDataBean(pLVDiscussAckResult.getData());
                    if (PLVMultiRoleEventProcessor.this.onEventProcessorListener != null) {
                        PLVMultiRoleEventProcessor.this.onEventProcessorListener.onLeaveDiscuss(pLVSwitchRoomEventFromDataBean);
                    }
                }
                Map<String, PLVUpdateMicSiteEvent> map = (Map) PLVSugarUtil.nullable(new PLVSugarUtil.Supplier<Map<String, PLVUpdateMicSiteEvent>>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.3.1
                    @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Supplier
                    public Map<String, PLVUpdateMicSiteEvent> get() {
                        return pLVDiscussAckResult.getData().getRoomsStatus().getParsedMicSite();
                    }
                });
                if (PLVMultiRoleEventProcessor.this.onEventProcessorListener != null) {
                    PLVMultiRoleEventProcessor.this.onEventProcessorListener.onChangeLinkMicZoom(map);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptLoginEvent(PLVLoginEvent loginEvent) {
        OnEventProcessorListener onEventProcessorListener = this.onEventProcessorListener;
        if (onEventProcessorListener != null) {
            onEventProcessorListener.onUserLogin(loginEvent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptMicphoneStatusEvent(PLVMicphoneStatus micPhoneStatus) {
        OnEventProcessorListener onEventProcessorListener;
        if (micPhoneStatus != null) {
            String status = micPhoneStatus.getStatus();
            String userId = micPhoneStatus.getUserId();
            if (TextUtils.isEmpty(userId) || !isMyLinkMicId(userId) || !"close".equals(status) || (onEventProcessorListener = this.onEventProcessorListener) == null) {
                return;
            }
            onEventProcessorListener.onAcceptMyJoinLeave(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptOTeacherInfoEvent(PLVOTeacherInfoEvent oTeacherInfoEvent) {
        OnEventProcessorListener onEventProcessorListener;
        if (oTeacherInfoEvent == null || oTeacherInfoEvent.getData() == null || (onEventProcessorListener = this.onEventProcessorListener) == null) {
            return;
        }
        onEventProcessorListener.onTeacherInfo(oTeacherInfoEvent.getData().getNick());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptOnSliceIDEvent(PLVOnSliceIDEvent onSliceIDEvent) {
        OnEventProcessorListener onEventProcessorListener;
        String str;
        if (onSliceIDEvent == null || onSliceIDEvent.getData() == null) {
            return;
        }
        PLVClassStatusBean classStatus = onSliceIDEvent.getClassStatus();
        if ((classStatus == null || !classStatus.isVoice()) && !this.isTeacherType && (onEventProcessorListener = this.onEventProcessorListener) != null) {
            onEventProcessorListener.onAcceptMyJoinLeave(false);
        }
        if (this.sendJoinDiscussMsgFlag && (str = this.groupId) != null && str.equals(onSliceIDEvent.getGroupId())) {
            this.groupLeaderId = onSliceIDEvent.getLeader();
        }
        Map<String, PLVUpdateMicSiteEvent> parsedMicSite = onSliceIDEvent.getData().getParsedMicSite();
        OnEventProcessorListener onEventProcessorListener2 = this.onEventProcessorListener;
        if (onEventProcessorListener2 != null) {
            onEventProcessorListener2.onChangeLinkMicZoom(parsedMicSite);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptOnSliceStartEvent(PLVOnSliceStartEvent onSliceStartEvent) {
        OnEventProcessorListener onEventProcessorListener;
        if (onSliceStartEvent == null || (onEventProcessorListener = this.onEventProcessorListener) == null) {
            return;
        }
        onEventProcessorListener.onSliceStart(onSliceStartEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptRemoveLinkMicZoom(String listenEvent, String event, String message) {
        if ("changeMicSite".equals(listenEvent) && PLVRemoveMicSiteEvent.EVENT_NAME.equals(event) && this.onEventProcessorListener != null) {
            this.onEventProcessorListener.onRemoveLinkMicZoom(PLVRemoveMicSiteEvent.fromJson(message));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptRequestHelp() {
        OnEventProcessorListener onEventProcessorListener = this.onEventProcessorListener;
        if (onEventProcessorListener != null) {
            onEventProcessorListener.onLeaderRequestHelp();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptTeacherSetPermissionEvent(PLVTeacherSetPermissionEvent teacherSetPermissionEvent) {
        String str;
        if (teacherSetPermissionEvent != null) {
            String type = teacherSetPermissionEvent.getType();
            String status = teacherSetPermissionEvent.getStatus();
            String userId = teacherSetPermissionEvent.getUserId();
            if (userId == null || !userId.equals(this.liveRoomDataManager.getConfig().getUser().getViewerId())) {
                return;
            }
            boolean zEquals = "0".equals(status);
            if ("video".equals(type)) {
                OnEventProcessorListener onEventProcessorListener = this.onEventProcessorListener;
                if (onEventProcessorListener != null) {
                    onEventProcessorListener.onTeacherMuteMyMedia(true, zEquals);
                    return;
                }
                return;
            }
            if ("audio".equals(type)) {
                OnEventProcessorListener onEventProcessorListener2 = this.onEventProcessorListener;
                if (onEventProcessorListener2 != null) {
                    onEventProcessorListener2.onTeacherMuteMyMedia(false, zEquals);
                    return;
                }
                return;
            }
            if (!"voice".equals(type) || zEquals || (str = this.groupId) == null || !str.equals(teacherSetPermissionEvent.getRoomId())) {
                return;
            }
            if (this.sendJoinDiscussMsgFlag) {
                this.isInClassStatusInDiscuss = true;
            }
            OnEventProcessorListener onEventProcessorListener3 = this.onEventProcessorListener;
            if (onEventProcessorListener3 != null) {
                onEventProcessorListener3.onResponseJoinForDiscuss();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptUpdateLinkMicZoom(String listenEvent, String event, String message) {
        if ("changeMicSite".equals(listenEvent) && PLVUpdateMicSiteEvent.EVENT_NAME.equals(event) && this.onEventProcessorListener != null) {
            this.onEventProcessorListener.onUpdateLinkMicZoom(PLVUpdateMicSiteEvent.fromJson(message));
        }
    }

    private boolean isMyLinkMicId(String linkMicId) {
        return linkMicId != null && linkMicId.equals(this.myLinkMicId);
    }

    private void observeRTCEventInner() {
        PLVLinkMicEventListener pLVLinkMicEventListener = new PLVLinkMicEventListener() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.4
            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onJoinChannelSuccess(String uid) {
                PLVCommonLog.d(PLVMultiRoleEventProcessor.TAG, "onJoinChannelSuccess, uid=" + uid);
                if (PLVMultiRoleEventProcessor.this.onEventProcessorListener != null) {
                    PLVMultiRoleEventProcessor.this.onEventProcessorListener.onJoinChannelSuccess();
                }
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onLeaveChannel() {
                super.onLeaveChannel();
                PLVCommonLog.d(PLVMultiRoleEventProcessor.TAG, "onLeaveChannel");
                if (PLVMultiRoleEventProcessor.this.onEventProcessorListener != null) {
                    PLVMultiRoleEventProcessor.this.onEventProcessorListener.onLeaveChannel();
                }
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onNetworkQuality(final int quality) {
                super.onNetworkQuality(quality);
                if (PLVMultiRoleEventProcessor.this.onEventProcessorListener != null) {
                    PLVMultiRoleEventProcessor.this.onEventProcessorListener.onNetworkQuality(quality);
                }
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onRemoteNetworkStatus(PLVNetworkStatusVO networkStatusVO) {
                super.onRemoteNetworkStatus(networkStatusVO);
                if (PLVMultiRoleEventProcessor.this.onEventProcessorListener != null) {
                    PLVMultiRoleEventProcessor.this.onEventProcessorListener.onRemoteNetworkStatus(networkStatusVO);
                }
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onUpstreamNetworkStatus(PLVNetworkStatusVO networkStatusVO) {
                super.onUpstreamNetworkStatus(networkStatusVO);
                if (PLVMultiRoleEventProcessor.this.onEventProcessorListener != null) {
                    PLVMultiRoleEventProcessor.this.onEventProcessorListener.onUpstreamNetworkStatus(networkStatusVO);
                }
            }
        };
        this.linkMicEventListener = pLVLinkMicEventListener;
        IPLVLinkMicManager iPLVLinkMicManager = this.linkMicManager;
        if (iPLVLinkMicManager != null) {
            iPLVLinkMicManager.addEventHandler(pLVLinkMicEventListener);
        }
    }

    private void observeSocketEvent() {
        this.onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.1
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String listenEvent, String event, String message) {
                event.hashCode();
                switch (event) {
                    case "hostSendToAllGroup":
                        PLVMultiRoleEventProcessor.this.acceptHostSendToAllGroupEvent((PLVHostSendToAllGroupEvent) PLVGsonUtil.fromJson(PLVHostSendToAllGroupEvent.class, message));
                        break;
                    case "joinResponse":
                        PLVMultiRoleEventProcessor.this.acceptJoinResponseSEvent((PLVJoinResponseSEvent) PLVGsonUtil.fromJson(PLVJoinResponseSEvent.class, message));
                        break;
                    case "OPEN_MICROPHONE":
                        PLVMultiRoleEventProcessor.this.acceptMicphoneStatusEvent((PLVMicphoneStatus) PLVGsonUtil.fromJson(PLVMicphoneStatus.class, message));
                        break;
                    case "leaveDiscuss":
                        PLVMultiRoleEventProcessor.this.acceptLeaveDiscussEvent((PLVLeaveDiscussEvent) PLVGsonUtil.fromJson(PLVLeaveDiscussEvent.class, message));
                        break;
                    case "groupRequestHelp":
                        PLVMultiRoleEventProcessor.this.acceptRequestHelp();
                        break;
                    case "O_TEACHER_INFO":
                        PLVMultiRoleEventProcessor.this.acceptOTeacherInfoEvent((PLVOTeacherInfoEvent) PLVEventHelper.toMessageEventModel(message, PLVOTeacherInfoEvent.class));
                        break;
                    case "joinDiscuss":
                        PLVMultiRoleEventProcessor.this.acceptJoinDiscussEvent((PLVJoinDiscussEvent) PLVGsonUtil.fromJson(PLVJoinDiscussEvent.class, message));
                        break;
                    case "onSliceID":
                        PLVMultiRoleEventProcessor.this.acceptOnSliceIDEvent((PLVOnSliceIDEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVOnSliceIDEvent.class));
                        break;
                    case "onSliceStart":
                        PLVMultiRoleEventProcessor.this.acceptOnSliceStartEvent((PLVOnSliceStartEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVOnSliceStartEvent.class));
                        break;
                    case "hostLeave":
                        PLVMultiRoleEventProcessor.this.acceptHostLeaveEvent();
                        break;
                    case "joinLeave":
                        PLVMultiRoleEventProcessor.this.acceptJoinLeaveSEvent((PLVJoinLeaveSEvent) PLVGsonUtil.fromJson(PLVJoinLeaveSEvent.class, message));
                        break;
                    case "hostJoin":
                        PLVMultiRoleEventProcessor.this.acceptHostJoinEvent();
                        break;
                    case "LOGIN":
                        PLVMultiRoleEventProcessor.this.acceptLoginEvent((PLVLoginEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVLoginEvent.class));
                        break;
                    case "TEACHER_SET_PERMISSION":
                        PLVMultiRoleEventProcessor.this.acceptTeacherSetPermissionEvent((PLVTeacherSetPermissionEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVTeacherSetPermissionEvent.class));
                        break;
                    case "finishClass":
                        PLVMultiRoleEventProcessor.this.acceptFinishClassEvent((PLVFinishClassEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVFinishClassEvent.class));
                        break;
                    case "cancelHelp":
                        PLVMultiRoleEventProcessor.this.acceptCancelHelp();
                        break;
                }
                PLVMultiRoleEventProcessor.this.acceptUpdateLinkMicZoom(listenEvent, event, message);
                PLVMultiRoleEventProcessor.this.acceptRemoveLinkMicZoom(listenEvent, event, message);
            }
        };
        PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.onMessageListener, PLVEventConstant.LinkMic.JOIN_REQUEST_EVENT, PLVEventConstant.LinkMic.JOIN_RESPONSE_EVENT, "joinSuccess", PLVEventConstant.LinkMic.JOIN_LEAVE_EVENT, PLVEventConstant.LinkMic.JOIN_ANSWER_EVENT, PLVEventConstant.Class.SE_SWITCH_MESSAGE, PLVEventConstant.Seminar.SEMINAR_EVENT, "message", "changeMicSite");
    }

    public void destroy() {
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
        IPLVLinkMicManager iPLVLinkMicManager = this.linkMicManager;
        if (iPLVLinkMicManager != null) {
            iPLVLinkMicManager.removeEventHandler(this.linkMicEventListener);
        }
    }

    public void observeRTCEvent(IPLVLinkMicManager linkMicManager) {
        this.linkMicManager = linkMicManager;
        observeRTCEventInner();
    }

    public void setMyLinkMicId(String myLinkMicId) {
        this.myLinkMicId = myLinkMicId;
    }

    public void setOnEventProcessorListener(OnEventProcessorListener listener) {
        this.onEventProcessorListener = listener;
    }
}
