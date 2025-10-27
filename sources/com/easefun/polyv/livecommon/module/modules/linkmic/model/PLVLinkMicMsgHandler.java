package com.easefun.polyv.livecommon.module.modules.linkmic.model;

import android.text.TextUtils;
import com.easefun.polyv.businesssdk.model.ppt.PolyvPPTAuthentic;
import com.easefun.polyv.livescenes.socket.PolyvSocketWrapper;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.linkmic.model.PLVJoinRequestSEvent;
import com.plv.linkmic.model.PLVLinkMicJoinSuccess;
import com.plv.linkmic.model.PLVLinkMicMedia;
import com.plv.linkmic.model.PLVLinkMicSwitchViewEvent;
import com.plv.linkmic.model.PLVMicphoneStatus;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.chat.PLVSendCupEvent;
import com.plv.socket.event.ppt.PLVOnSliceIDEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLinkMicMsgHandler {
    private static final String LINK_MIC_STATE_OPEN = "open";
    private static final String LINK_MIC_TYPE_AUDIO = "audio";
    private static final String TAG = "PLVLinkMicMsgHandler";
    private String linkMicId;
    private PLVSocketMessageObserver.OnMessageListener onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.1
        @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
        public void onMessage(String listenEvent, String event, String message) {
            PLVLinkMicMsgHandler.this.processLinkMicSocketMessage(message, event);
        }
    };
    private List<OnLinkMicDataListener> onLinkMicDataListeners = new ArrayList();

    public interface OnLinkMicDataListener {
        void onFinishClass();

        void onLinkMicConnectMode(String avConnectMode);

        void onSwitchFirstScreen(String linkMicId);

        void onSwitchPPTViewLocation(boolean toMainScreen);

        void onTeacherAllowMeToJoin();

        void onTeacherCloseLinkMic();

        void onTeacherHangupMe();

        void onTeacherMuteMedia(boolean isMute, boolean isAudio);

        void onTeacherOpenLinkMic();

        void onTeacherReceiveJoinRequest();

        void onTeacherSendCup(String linkMicId, int cupNum);

        void onUpdateLinkMicType(boolean isAudio);

        void onUserJoinSuccess(PLVLinkMicItemDataBean dataBean);
    }

    public static abstract class SimpleOnLinkMicDataListener implements OnLinkMicDataListener {
        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onFinishClass() {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onLinkMicConnectMode(String avConnectMode) {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onSwitchFirstScreen(String linkMicId) {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onSwitchPPTViewLocation(boolean toMainScreen) {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onTeacherAllowMeToJoin() {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onTeacherCloseLinkMic() {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onTeacherHangupMe() {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onTeacherMuteMedia(boolean isMute, boolean isAudio) {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onTeacherOpenLinkMic() {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onTeacherReceiveJoinRequest() {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onTeacherSendCup(String linkMicId, int cupNum) {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onUpdateLinkMicType(boolean isAudio) {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onUserJoinSuccess(PLVLinkMicItemDataBean dataBean) {
        }
    }

    public PLVLinkMicMsgHandler(String linkMicId) {
        this.linkMicId = linkMicId;
        PolyvSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.onMessageListener, PLVEventConstant.LinkMic.JOIN_REQUEST_EVENT, PLVEventConstant.LinkMic.JOIN_RESPONSE_EVENT, "joinSuccess", PLVEventConstant.LinkMic.JOIN_LEAVE_EVENT, PLVEventConstant.Class.SE_SWITCH_MESSAGE, "message");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processLinkMicSocketMessage(String message, String event) {
        if (TextUtils.isEmpty(event)) {
        }
        event.hashCode();
        switch (event) {
            case "joinResponse":
                PLVCommonLog.d(TAG, message);
                Iterator<OnLinkMicDataListener> it = this.onLinkMicDataListeners.iterator();
                while (it.hasNext()) {
                    it.next().onTeacherAllowMeToJoin();
                }
                break;
            case "changeVideoAndPPTPosition":
                PLVCommonLog.d(TAG, message);
                PolyvPPTAuthentic polyvPPTAuthentic = (PolyvPPTAuthentic) PLVGsonUtil.fromJson(PolyvPPTAuthentic.class, message);
                if (polyvPPTAuthentic != null) {
                    if ("1".equals(polyvPPTAuthentic.getStatus())) {
                        Iterator<OnLinkMicDataListener> it2 = this.onLinkMicDataListeners.iterator();
                        while (it2.hasNext()) {
                            it2.next().onSwitchPPTViewLocation(false);
                        }
                        break;
                    } else {
                        Iterator<OnLinkMicDataListener> it3 = this.onLinkMicDataListeners.iterator();
                        while (it3.hasNext()) {
                            it3.next().onSwitchPPTViewLocation(true);
                        }
                        break;
                    }
                }
                break;
            case "OPEN_MICROPHONE":
                PLVCommonLog.d(TAG, message);
                PLVMicphoneStatus pLVMicphoneStatus = (PLVMicphoneStatus) PLVGsonUtil.fromJson(PLVMicphoneStatus.class, message);
                if (pLVMicphoneStatus != null) {
                    String type = pLVMicphoneStatus.getType();
                    String status = pLVMicphoneStatus.getStatus();
                    String userId = pLVMicphoneStatus.getUserId();
                    boolean zIsEmpty = TextUtils.isEmpty(userId);
                    boolean zEquals = "audio".equals(type);
                    Iterator<OnLinkMicDataListener> it4 = this.onLinkMicDataListeners.iterator();
                    while (it4.hasNext()) {
                        it4.next().onUpdateLinkMicType(zEquals);
                    }
                    if (!"open".equals(status) || !zIsEmpty) {
                        if (zIsEmpty) {
                            Iterator<OnLinkMicDataListener> it5 = this.onLinkMicDataListeners.iterator();
                            while (it5.hasNext()) {
                                it5.next().onTeacherCloseLinkMic();
                            }
                            break;
                        } else if (this.linkMicId.equals(userId)) {
                            Iterator<OnLinkMicDataListener> it6 = this.onLinkMicDataListeners.iterator();
                            while (it6.hasNext()) {
                                it6.next().onTeacherHangupMe();
                            }
                            break;
                        }
                    } else {
                        Iterator<OnLinkMicDataListener> it7 = this.onLinkMicDataListeners.iterator();
                        while (it7.hasNext()) {
                            it7.next().onTeacherOpenLinkMic();
                        }
                        break;
                    }
                }
                break;
            case "joinRequest":
                PLVCommonLog.d(TAG, message);
                PLVJoinRequestSEvent pLVJoinRequestSEvent = (PLVJoinRequestSEvent) PLVGsonUtil.fromJson(PLVJoinRequestSEvent.class, message);
                if (pLVJoinRequestSEvent != null && pLVJoinRequestSEvent.getUser() != null && this.linkMicId.equals(pLVJoinRequestSEvent.getUser().getUserId())) {
                    Iterator<OnLinkMicDataListener> it8 = this.onLinkMicDataListeners.iterator();
                    while (it8.hasNext()) {
                        it8.next().onTeacherReceiveJoinRequest();
                    }
                    break;
                }
                break;
            case "onSliceID":
                PLVOnSliceIDEvent pLVOnSliceIDEvent = (PLVOnSliceIDEvent) PLVGsonUtil.fromJson(PLVOnSliceIDEvent.class, message);
                if (pLVOnSliceIDEvent != null && pLVOnSliceIDEvent.getData() != null) {
                    Iterator<OnLinkMicDataListener> it9 = this.onLinkMicDataListeners.iterator();
                    while (it9.hasNext()) {
                        it9.next().onLinkMicConnectMode(pLVOnSliceIDEvent.getData().getAvConnectMode());
                    }
                    break;
                }
                break;
            case "joinLeave":
                PLVCommonLog.d(TAG, message);
                break;
            case "switchView":
                PLVCommonLog.d(TAG, message);
                PLVLinkMicSwitchViewEvent pLVLinkMicSwitchViewEvent = (PLVLinkMicSwitchViewEvent) PLVGsonUtil.fromJson(PLVLinkMicSwitchViewEvent.class, message);
                if (pLVLinkMicSwitchViewEvent != null) {
                    Iterator<OnLinkMicDataListener> it10 = this.onLinkMicDataListeners.iterator();
                    while (it10.hasNext()) {
                        it10.next().onSwitchFirstScreen(pLVLinkMicSwitchViewEvent.getUserId());
                    }
                    break;
                }
                break;
            case "joinSuccess":
                PLVCommonLog.d(TAG, message);
                PLVLinkMicJoinSuccess pLVLinkMicJoinSuccess = (PLVLinkMicJoinSuccess) PLVGsonUtil.fromJson(PLVLinkMicJoinSuccess.class, message);
                if (pLVLinkMicJoinSuccess != null) {
                    Iterator<OnLinkMicDataListener> it11 = this.onLinkMicDataListeners.iterator();
                    while (it11.hasNext()) {
                        it11.next().onUserJoinSuccess(PLVLinkMicDataMapper.map2LinkMicItemData(pLVLinkMicJoinSuccess));
                    }
                    break;
                }
                break;
            case "TEACHER_SET_PERMISSION":
                PLVCommonLog.d(TAG, message);
                break;
            case "finishClass":
                Iterator<OnLinkMicDataListener> it12 = this.onLinkMicDataListeners.iterator();
                while (it12.hasNext()) {
                    it12.next().onFinishClass();
                }
                break;
            case "MUTE_USER_MICRO":
                PLVCommonLog.d(TAG, message);
                PLVLinkMicMedia pLVLinkMicMedia = (PLVLinkMicMedia) PLVGsonUtil.fromJson(PLVLinkMicMedia.class, message);
                if (pLVLinkMicMedia != null) {
                    boolean zIsMute = pLVLinkMicMedia.isMute();
                    boolean zEquals2 = "audio".equals(pLVLinkMicMedia.getType());
                    for (OnLinkMicDataListener onLinkMicDataListener : this.onLinkMicDataListeners) {
                        onLinkMicDataListener.onTeacherMuteMedia(zIsMute, zEquals2);
                        if (TextUtils.isEmpty(pLVLinkMicMedia.getSocketId())) {
                            onLinkMicDataListener.onLinkMicConnectMode(zIsMute ? pLVLinkMicMedia.getType() : "");
                        }
                    }
                    break;
                }
                break;
            case "SEND_CUP":
                PLVCommonLog.d(TAG, message);
                PLVSendCupEvent pLVSendCupEvent = (PLVSendCupEvent) PLVGsonUtil.fromJson(PLVSendCupEvent.class, message);
                if (pLVSendCupEvent != null && pLVSendCupEvent.getOwner() != null && pLVSendCupEvent.getOwner().getUserId() != null) {
                    Iterator<OnLinkMicDataListener> it13 = this.onLinkMicDataListeners.iterator();
                    while (it13.hasNext()) {
                        it13.next().onTeacherSendCup(pLVSendCupEvent.getOwner().getUserId(), pLVSendCupEvent.getOwner().getNum());
                    }
                    break;
                }
                break;
        }
    }

    public void addLinkMicMsgListener(OnLinkMicDataListener onLinkMicDataListener) {
        this.onLinkMicDataListeners.add(onLinkMicDataListener);
    }

    public void destroy() {
        this.onLinkMicDataListeners.clear();
        PolyvSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
    }
}
