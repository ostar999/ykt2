package com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model;

import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicDataMapper;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.linkmic.model.PLVJoinInfoEvent;
import com.plv.linkmic.model.PLVLinkMicJoinStatus;
import com.plv.linkmic.model.PLVMicphoneStatus;
import com.plv.linkmic.repository.PLVLinkMicDataRepository;
import com.plv.linkmic.repository.PLVLinkMicHttpRequestException;
import com.plv.livescenes.linkmic.IPLVLinkMicManager;
import com.plv.livescenes.linkmic.listener.PLVLinkMicEventListener;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.linkmic.PLVJoinAnswerSEvent;
import com.plv.socket.event.linkmic.PLVJoinLeaveSEvent;
import com.plv.socket.event.ppt.PLVFinishClassEvent;
import com.plv.socket.event.ppt.PLVOnSliceIDEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.user.PLVClassStatusBean;
import com.plv.socket.user.PLVSocketUserBean;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes3.dex */
public class PLVMultiRoleLinkMicList {
    private static final String TAG = "PLVMultiRoleLinkMicList";
    private String groupLeaderId;
    private boolean isTeacherType;
    private PLVLinkMicEventListener linkMicEventListener;
    private Disposable linkMicListOnceDisposable;
    private Disposable linkMicListTimerDisposable;

    @Nullable
    private IPLVLinkMicManager linkMicManager;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private PLVClassStatusBean myClassStatusBeanOnSliceId;
    private String myLinkMicId;

    @Nullable
    private PLVLinkMicItemDataBean myLinkMicItemBean;
    private PLVSocketMessageObserver.OnMessageListener onMessageListener;
    private List<PLVLinkMicItemDataBean> linkMicList = new LinkedList();
    private Map<String, PLVLinkMicItemDataBean> rtcJoinMap = new HashMap();
    private Map<String, Boolean> teacherScreenStreamMap = new HashMap();
    private List<OnLinkMicListListener> onLinkMicListListeners = new ArrayList();

    public static abstract class AbsOnLinkMicListListener implements OnLinkMicListListener {
        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onGetLinkMicListStatus(String sessionId, PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicJoinStatus> callback) {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public PLVLinkMicItemDataBean onGetSavedLinkMicItem(String linkMicId) {
            return null;
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onLinkMicItemIdleChanged(String linkMicId) {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onLinkMicItemInfoChanged() {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onLinkMicItemInsert(PLVLinkMicItemDataBean linkMicItemDataBean, int position) {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onLinkMicItemRemove(PLVLinkMicItemDataBean linkMicItemDataBean, int position) {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onLinkMicListChanged(List<PLVLinkMicItemDataBean> dataBeanList) {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onLinkMicUserExisted(PLVLinkMicItemDataBean linkMicItemDataBean, int position) {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onTeacherScreenStream(PLVLinkMicItemDataBean linkMicItemDataBean, boolean isOpen) {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public boolean onUpdateLinkMicItemInfo(@NonNull PLVSocketUserBean socketUserBean, @NonNull PLVLinkMicItemDataBean linkMicItemDataBean, boolean isJoinList, boolean isGroupLeader) {
            return false;
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public List<String> onUpdateLinkMicItemStatus(List<PLVJoinInfoEvent> joinList, List<PLVLinkMicJoinStatus.WaitListBean> waitList) {
            return null;
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onUserGetCup(String userNick, boolean isByEvent, int linkMicListPos, int memberListPos) {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onUserHasPaint(boolean isMyself, boolean isHasPaint, int linkMicListPos, int memberListPos) {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void syncLinkMicItem(PLVLinkMicItemDataBean linkMicItemDataBean, String userId) {
        }
    }

    public interface ListenerRunnable {
        void run(@NonNull OnLinkMicListListener linkMicListListener);
    }

    public interface OnLinkMicListListener {
        void onGetLinkMicListStatus(String sessionId, PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicJoinStatus> callback);

        PLVLinkMicItemDataBean onGetSavedLinkMicItem(String linkMicId);

        void onLinkMicItemIdleChanged(String linkMicId);

        void onLinkMicItemInfoChanged();

        void onLinkMicItemInsert(PLVLinkMicItemDataBean linkMicItemDataBean, int position);

        void onLinkMicItemRemove(PLVLinkMicItemDataBean linkMicItemDataBean, int position);

        void onLinkMicListChanged(List<PLVLinkMicItemDataBean> dataBeanList);

        void onLinkMicUserExisted(PLVLinkMicItemDataBean linkMicItemDataBean, int position);

        void onTeacherScreenStream(PLVLinkMicItemDataBean linkMicItemDataBean, boolean isOpen);

        boolean onUpdateLinkMicItemInfo(@NonNull PLVSocketUserBean socketUserBean, @NonNull PLVLinkMicItemDataBean linkMicItemDataBean, boolean isJoinList, boolean isGroupLeader);

        List<String> onUpdateLinkMicItemStatus(List<PLVJoinInfoEvent> joinList, List<PLVLinkMicJoinStatus.WaitListBean> waitList);

        void onUserGetCup(String userNick, boolean isByEvent, int linkMicListPos, int memberListPos);

        void onUserHasPaint(boolean isMyself, boolean isHasPaint, int linkMicListPos, int memberListPos);

        void syncLinkMicItem(PLVLinkMicItemDataBean linkMicItemDataBean, String userId);
    }

    public PLVMultiRoleLinkMicList(IPLVLiveRoomDataManager liveRoomDataManager) {
        this.liveRoomDataManager = liveRoomDataManager;
        this.isTeacherType = "teacher".equals(liveRoomDataManager.getConfig().getUser().getViewerType());
        observeSocketEvent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptFinishClassEvent(PLVFinishClassEvent finishClassEvent) {
        if (this.isTeacherType) {
            return;
        }
        acceptUserJoinLeave(this.myLinkMicId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptGetLinkMicListStatus() {
        callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.3
            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
            public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                linkMicListListener.onGetLinkMicListStatus(PLVMultiRoleLinkMicList.this.liveRoomDataManager.getSessionId(), new PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicJoinStatus>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.3.1
                    @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
                    public void onFail(PLVLinkMicHttpRequestException throwable) {
                        super.onFail(throwable);
                        PLVCommonLog.exception(throwable);
                    }

                    @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
                    public void onSuccess(PLVLinkMicJoinStatus data) {
                        PLVCommonLog.d(PLVMultiRoleLinkMicList.TAG, "requestLinkMicListFromServer.onSuccess->\n" + data.toString());
                        PLVMultiRoleLinkMicList.this.updateLinkMicListWithJoinStatus(data);
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptJoinAnswerSEvent(PLVJoinAnswerSEvent joinAnswerSEvent) {
        if (joinAnswerSEvent != null) {
            final String userId = joinAnswerSEvent.getUserId();
            if (joinAnswerSEvent.isRefuse() || !joinAnswerSEvent.isResult()) {
                callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.21
                    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
                    public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                        linkMicListListener.onLinkMicItemIdleChanged(userId);
                    }
                });
                acceptUserJoinLeave(userId);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptJoinLeaveSEvent(PLVJoinLeaveSEvent joinLeaveSEvent) {
        if (joinLeaveSEvent == null || joinLeaveSEvent.getUser() == null) {
            return;
        }
        acceptUserJoinLeave(joinLeaveSEvent.getUser().getUserId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptMicphoneStatusEvent(PLVMicphoneStatus micPhoneStatus) {
        if (micPhoneStatus != null) {
            String status = micPhoneStatus.getStatus();
            String userId = micPhoneStatus.getUserId();
            if (!TextUtils.isEmpty(userId) && isMyLinkMicId(userId) && "close".equals(status)) {
                acceptUserJoinLeave(userId);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptOnSliceIDEvent(PLVOnSliceIDEvent onSliceIDEvent) {
        if (onSliceIDEvent == null || onSliceIDEvent.getData() == null) {
            return;
        }
        PLVClassStatusBean classStatus = onSliceIDEvent.getClassStatus();
        if (classStatus != null) {
            this.myClassStatusBeanOnSliceId = classStatus;
            final Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = getLinkMicItemWithLinkMicId(this.myLinkMicId);
            if (linkMicItemWithLinkMicId == null) {
                return;
            }
            boolean zIsHasPaint = ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).isHasPaint();
            int cupNum = ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).getCupNum();
            final int iIntValue = ((Integer) linkMicItemWithLinkMicId.first).intValue();
            if (classStatus.hasPaint() != zIsHasPaint) {
                ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).setHasPaint(classStatus.hasPaint());
                callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.19
                    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
                    public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                        linkMicListListener.onUserHasPaint(true, ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).isHasPaint(), iIntValue, -1);
                    }
                });
            }
            if (classStatus.getCup() != cupNum) {
                ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).setCupNum(classStatus.getCup());
                callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.20
                    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
                    public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                        linkMicListListener.onUserGetCup(((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).getNick(), false, iIntValue, -1);
                    }
                });
            }
        }
        if ((classStatus == null || !classStatus.isVoice()) && !this.isTeacherType) {
            acceptUserJoinLeave(this.myLinkMicId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptUserJoinChannel(final String linkMicUid, final int streamType) {
        requestLinkMicListApiOnce();
        PLVLinkMicItemDataBean pLVLinkMicItemDataBean = this.rtcJoinMap.get(linkMicUid);
        if (pLVLinkMicItemDataBean == null) {
            PLVLinkMicItemDataBean pLVLinkMicItemDataBean2 = new PLVLinkMicItemDataBean();
            pLVLinkMicItemDataBean2.setLinkMicId(linkMicUid);
            pLVLinkMicItemDataBean2.setStreamType(streamType);
            this.rtcJoinMap.put(linkMicUid, pLVLinkMicItemDataBean2);
        } else if (!pLVLinkMicItemDataBean.equalStreamType(streamType)) {
            pLVLinkMicItemDataBean.setStreamType(0);
        }
        Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = getLinkMicItemWithLinkMicId(linkMicUid);
        if (linkMicItemWithLinkMicId != null) {
            final PLVLinkMicItemDataBean pLVLinkMicItemDataBean3 = (PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second;
            final int iIntValue = ((Integer) linkMicItemWithLinkMicId.first).intValue();
            if (pLVLinkMicItemDataBean3.equalStreamType(streamType)) {
                callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.24
                    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
                    public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                        linkMicListListener.onLinkMicUserExisted(pLVLinkMicItemDataBean3, iIntValue);
                    }
                });
            } else if (2 == streamType) {
                if (isTeacherLinkMicId(linkMicUid) || isLeaderId(linkMicUid)) {
                    callOnTeacherScreenStream(linkMicUid, true);
                } else {
                    callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.25
                        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
                        public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                            PLVMultiRoleLinkMicList.this.linkMicList.remove(iIntValue);
                            linkMicListListener.onLinkMicItemRemove(pLVLinkMicItemDataBean3, iIntValue);
                            pLVLinkMicItemDataBean3.setStreamType(streamType);
                            PLVMultiRoleLinkMicList.this.linkMicList.add(iIntValue, pLVLinkMicItemDataBean3);
                            linkMicListListener.onLinkMicItemInsert(pLVLinkMicItemDataBean3, iIntValue);
                        }
                    });
                }
            }
        }
        callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.26
            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
            public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                PLVLinkMicItemDataBean pLVLinkMicItemDataBeanOnGetSavedLinkMicItem = linkMicListListener.onGetSavedLinkMicItem(linkMicUid);
                if (pLVLinkMicItemDataBeanOnGetSavedLinkMicItem == null || !PLVMultiRoleLinkMicList.this.updateLinkMicItemInfoWithRtcJoinListInner(pLVLinkMicItemDataBeanOnGetSavedLinkMicItem, linkMicUid)) {
                    return;
                }
                PLVMultiRoleLinkMicList.this.callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.26.1
                    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
                    public void run(@NonNull OnLinkMicListListener linkMicListListener2) {
                        linkMicListListener2.onLinkMicItemInfoChanged();
                    }
                });
            }
        });
    }

    private void acceptUserJoinLeave(String linkMicUid) {
        acceptUserJoinLeave(linkMicUid, 0);
    }

    private void addMyItemToLinkMicListInner(boolean curEnableLocalVideo, boolean curEnableLocalAudio) {
        PLVLinkMicItemDataBean pLVLinkMicItemDataBean;
        if (getLinkMicItemWithLinkMicId(this.myLinkMicId) != null || (pLVLinkMicItemDataBean = this.myLinkMicItemBean) == null) {
            return;
        }
        pLVLinkMicItemDataBean.setMuteVideo(!curEnableLocalVideo);
        this.myLinkMicItemBean.setMuteAudio(!curEnableLocalAudio);
        this.myLinkMicItemBean.setStatus(PLVLinkMicItemDataBean.STATUS_RTC_JOIN);
        final int size = this.isTeacherType ? 0 : this.linkMicList.size();
        if (isLeaderId(this.myLinkMicId)) {
            size = 0;
            for (int i2 = 0; i2 < this.linkMicList.size(); i2++) {
                if (this.linkMicList.get(i2).isTeacher()) {
                    size = i2 + 1;
                }
            }
        }
        this.linkMicList.add(size, this.myLinkMicItemBean);
        PLVClassStatusBean pLVClassStatusBean = this.myClassStatusBeanOnSliceId;
        if (pLVClassStatusBean != null) {
            this.myLinkMicItemBean.setHasPaint(pLVClassStatusBean.hasPaint());
            this.myLinkMicItemBean.setCupNum(this.myClassStatusBeanOnSliceId.getCup());
        }
        callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.4
            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
            public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                linkMicListListener.syncLinkMicItem(PLVMultiRoleLinkMicList.this.myLinkMicItemBean, PLVMultiRoleLinkMicList.this.liveRoomDataManager.getConfig().getUser().getViewerId());
            }
        });
        callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.5
            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
            public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                linkMicListListener.onLinkMicItemInsert(PLVMultiRoleLinkMicList.this.myLinkMicItemBean, size);
            }
        });
        callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.6
            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
            public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                linkMicListListener.onLinkMicItemInfoChanged();
            }
        });
    }

    private void callOnTeacherScreenStream(final String linkMicId, final boolean isOpen) {
        if (this.teacherScreenStreamMap.containsKey(linkMicId) && this.teacherScreenStreamMap.get(linkMicId).booleanValue() == isOpen) {
            return;
        }
        this.teacherScreenStreamMap.put(linkMicId, Boolean.valueOf(isOpen));
        callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.17
            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
            public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                PLVLinkMicItemDataBean pLVLinkMicItemDataBean = new PLVLinkMicItemDataBean();
                pLVLinkMicItemDataBean.setStreamType(2);
                pLVLinkMicItemDataBean.setLinkMicId(linkMicId);
                linkMicListListener.onTeacherScreenStream(pLVLinkMicItemDataBean, isOpen);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackToListener(ListenerRunnable runnable) {
        List<OnLinkMicListListener> list = this.onLinkMicListListeners;
        if (list != null) {
            for (OnLinkMicListListener onLinkMicListListener : list) {
                if (onLinkMicListListener != null && runnable != null) {
                    runnable.run(onLinkMicListListener);
                }
            }
        }
    }

    private void cleanTeacherScreenStream() {
        for (Map.Entry<String, Boolean> entry : this.teacherScreenStreamMap.entrySet()) {
            boolean zBooleanValue = entry.getValue().booleanValue();
            String key = entry.getKey();
            if (zBooleanValue && !isMyLinkMicId(key)) {
                callOnTeacherScreenStream(key, false);
            }
        }
        this.teacherScreenStreamMap.clear();
    }

    private void createMyLinkMicItem(String myLinkMicId) {
        if (this.myLinkMicItemBean != null || myLinkMicId == null) {
            return;
        }
        PLVLinkMicItemDataBean pLVLinkMicItemDataBean = new PLVLinkMicItemDataBean();
        this.myLinkMicItemBean = pLVLinkMicItemDataBean;
        pLVLinkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_IDLE);
        this.myLinkMicItemBean.setLinkMicId(myLinkMicId);
        this.myLinkMicItemBean.setActor(this.liveRoomDataManager.getConfig().getUser().getActor());
        this.myLinkMicItemBean.setNick(this.liveRoomDataManager.getConfig().getUser().getViewerName());
        this.myLinkMicItemBean.setUserType(this.liveRoomDataManager.getConfig().getUser().getViewerType());
        this.myLinkMicItemBean.setPic(this.liveRoomDataManager.getConfig().getUser().getViewerAvatar());
    }

    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private Pair<Integer, PLVLinkMicItemDataBean> getLinkMicItemWithLinkMicIdInner(String linkMicId) {
        for (int i2 = 0; i2 < this.linkMicList.size(); i2++) {
            PLVLinkMicItemDataBean pLVLinkMicItemDataBean = this.linkMicList.get(i2);
            String linkMicId2 = pLVLinkMicItemDataBean.getLinkMicId();
            if (linkMicId != null && linkMicId.equals(linkMicId2)) {
                return new Pair<>(Integer.valueOf(i2), pLVLinkMicItemDataBean);
            }
        }
        return null;
    }

    private boolean isLeaderId(String linkMicId) {
        return linkMicId != null && linkMicId.equals(this.groupLeaderId);
    }

    private boolean isMyLinkMicId(String linkMicId) {
        return linkMicId != null && linkMicId.equals(this.myLinkMicId);
    }

    private boolean isTeacherLinkMicId(final String linkMicUid) {
        Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = getLinkMicItemWithLinkMicId(linkMicUid);
        if (linkMicItemWithLinkMicId != null) {
            return ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).isTeacher();
        }
        final boolean[] zArr = {false};
        callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.16
            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
            public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                PLVLinkMicItemDataBean pLVLinkMicItemDataBeanOnGetSavedLinkMicItem = linkMicListListener.onGetSavedLinkMicItem(linkMicUid);
                if (pLVLinkMicItemDataBeanOnGetSavedLinkMicItem != null) {
                    boolean[] zArr2 = zArr;
                    if (zArr2[0]) {
                        return;
                    }
                    zArr2[0] = pLVLinkMicItemDataBeanOnGetSavedLinkMicItem.isTeacher();
                }
            }
        });
        return zArr[0];
    }

    private void observeRTCEventInner() {
        PLVLinkMicEventListener pLVLinkMicEventListener = new PLVLinkMicEventListener() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.22
            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onRemoteStreamClose(String uid, int streamType) {
                super.onRemoteStreamClose(uid, streamType);
                PLVCommonLog.d(PLVMultiRoleLinkMicList.TAG, "onRemoteStreamClose: " + uid + "*" + streamType);
                PLVMultiRoleLinkMicList.this.acceptUserJoinLeave(uid, streamType);
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onRemoteStreamOpen(String uid, int streamType) {
                super.onRemoteStreamOpen(uid, streamType);
                PLVCommonLog.d(PLVMultiRoleLinkMicList.TAG, "onRemoteStreamOpen: " + uid + "*" + streamType);
                PLVMultiRoleLinkMicList.this.acceptUserJoinChannel(uid, streamType);
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onUserJoined(String uid) {
                super.onUserJoined(uid);
                PLVCommonLog.d(PLVMultiRoleLinkMicList.TAG, "onUserJoined: " + uid);
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onUserMuteAudio(final String uid, final boolean mute, int streamType) {
                super.onUserMuteAudio(uid, mute);
                PLVCommonLog.d(PLVMultiRoleLinkMicList.TAG, "onUserMuteAudio: " + uid + "*" + mute + "*" + streamType);
                for (Map.Entry entry : PLVMultiRoleLinkMicList.this.rtcJoinMap.entrySet()) {
                    if (uid != null && uid.equals(entry.getKey())) {
                        ((PLVLinkMicItemDataBean) entry.getValue()).setMuteAudioInRtcJoinList(new PLVLinkMicItemDataBean.MuteMedia(mute, streamType));
                    }
                }
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onUserMuteVideo(String uid, boolean mute, int streamType) {
                super.onUserMuteVideo(uid, mute);
                PLVCommonLog.d(PLVMultiRoleLinkMicList.TAG, "onUserMuteVideo: " + uid + "*" + mute + "*" + streamType);
                for (Map.Entry entry : PLVMultiRoleLinkMicList.this.rtcJoinMap.entrySet()) {
                    if (uid != null && uid.equals(entry.getKey())) {
                        ((PLVLinkMicItemDataBean) entry.getValue()).setMuteVideoInRtcJoinList(new PLVLinkMicItemDataBean.MuteMedia(mute, streamType));
                    }
                }
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onUserOffline(String uid) {
                super.onUserOffline(uid);
                PLVCommonLog.d(PLVMultiRoleLinkMicList.TAG, "onUserOffline: " + uid);
            }
        };
        this.linkMicEventListener = pLVLinkMicEventListener;
        IPLVLinkMicManager iPLVLinkMicManager = this.linkMicManager;
        if (iPLVLinkMicManager != null) {
            iPLVLinkMicManager.addEventHandler(pLVLinkMicEventListener);
        }
    }

    private void observeSocketEvent() {
        this.onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.18
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String listenEvent, String event, String message) {
                event.hashCode();
                switch (event) {
                    case "OPEN_MICROPHONE":
                        PLVMultiRoleLinkMicList.this.acceptMicphoneStatusEvent((PLVMicphoneStatus) PLVGsonUtil.fromJson(PLVMicphoneStatus.class, message));
                        break;
                    case "onSliceID":
                        PLVMultiRoleLinkMicList.this.acceptOnSliceIDEvent((PLVOnSliceIDEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVOnSliceIDEvent.class));
                        break;
                    case "joinLeave":
                        PLVMultiRoleLinkMicList.this.acceptJoinLeaveSEvent((PLVJoinLeaveSEvent) PLVGsonUtil.fromJson(PLVJoinLeaveSEvent.class, message));
                        break;
                    case "joinAnswer":
                        PLVMultiRoleLinkMicList.this.acceptJoinAnswerSEvent((PLVJoinAnswerSEvent) PLVGsonUtil.fromJson(PLVJoinAnswerSEvent.class, message));
                        break;
                    case "finishClass":
                        PLVMultiRoleLinkMicList.this.acceptFinishClassEvent((PLVFinishClassEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVFinishClassEvent.class));
                        break;
                }
            }
        };
        PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.onMessageListener, PLVEventConstant.LinkMic.JOIN_REQUEST_EVENT, PLVEventConstant.LinkMic.JOIN_RESPONSE_EVENT, "joinSuccess", PLVEventConstant.LinkMic.JOIN_LEAVE_EVENT, PLVEventConstant.LinkMic.JOIN_ANSWER_EVENT, PLVEventConstant.Class.SE_SWITCH_MESSAGE, PLVEventConstant.Seminar.SEMINAR_EVENT, "message");
    }

    private void removeLinkMicItemNoExistServer(List<PLVJoinInfoEvent> joinList) {
        boolean z2;
        boolean z3;
        Iterator<PLVLinkMicItemDataBean> it = this.linkMicList.iterator();
        final int i2 = 0;
        while (it.hasNext()) {
            final PLVLinkMicItemDataBean next = it.next();
            String linkMicId = next.getLinkMicId();
            Iterator<PLVJoinInfoEvent> it2 = joinList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z3 = false;
                    break;
                }
                PLVJoinInfoEvent next2 = it2.next();
                if (linkMicId != null && linkMicId.equals(next2.getUserId())) {
                    z3 = true;
                    break;
                }
            }
            if (!z3 && !isMyLinkMicId(linkMicId)) {
                it.remove();
                callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.12
                    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
                    public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                        linkMicListListener.onLinkMicItemRemove(next, i2);
                    }
                });
                i2--;
            }
            i2++;
        }
        for (Map.Entry<String, Boolean> entry : this.teacherScreenStreamMap.entrySet()) {
            if (entry.getValue().booleanValue()) {
                String key = entry.getKey();
                Iterator<PLVJoinInfoEvent> it3 = joinList.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        z2 = false;
                        break;
                    }
                    PLVJoinInfoEvent next3 = it3.next();
                    if (key != null && key.equals(next3.getUserId())) {
                        z2 = true;
                        break;
                    }
                }
                if (!z2 && !isMyLinkMicId(key)) {
                    callOnTeacherScreenStream(key, false);
                }
            }
        }
    }

    private void removeMyItemToLinkMicListInner() {
        final Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = getLinkMicItemWithLinkMicId(this.myLinkMicId);
        if (linkMicItemWithLinkMicId != null) {
            this.linkMicList.remove(linkMicItemWithLinkMicId.second);
            callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.7
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
                public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                    Pair pair = linkMicItemWithLinkMicId;
                    linkMicListListener.onLinkMicItemRemove((PLVLinkMicItemDataBean) pair.second, ((Integer) pair.first).intValue());
                }
            });
        }
    }

    private void requestLinkMicListApi() {
        dispose(this.linkMicListTimerDisposable);
        this.linkMicListTimerDisposable = PLVRxTimer.timer(1000, 20000, new Consumer<Long>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Long aLong) throws Exception {
                PLVMultiRoleLinkMicList.this.acceptGetLinkMicListStatus();
            }
        });
    }

    private void requestLinkMicListApiOnce() {
        dispose(this.linkMicListOnceDisposable);
        this.linkMicListOnceDisposable = PLVRxTimer.delay(1000L, new Consumer<Long>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Long aLong) throws Exception {
                PLVMultiRoleLinkMicList.this.acceptGetLinkMicListStatus();
            }
        });
    }

    private void sortLinkMicList(String groupLeaderId) {
        if (groupLeaderId == null) {
            return;
        }
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;
        for (PLVLinkMicItemDataBean pLVLinkMicItemDataBean : this.linkMicList) {
            i3++;
            if (pLVLinkMicItemDataBean.isTeacher()) {
                i4 = i3;
            }
            if (pLVLinkMicItemDataBean.getLinkMicId() != null && pLVLinkMicItemDataBean.getLinkMicId().equals(groupLeaderId)) {
                i2 = i3;
            }
        }
        if (i2 == -1 || i2 == i4 || i2 - i4 == 1) {
            return;
        }
        PLVLinkMicItemDataBean pLVLinkMicItemDataBeanRemove = this.linkMicList.remove(i2);
        if (i2 > i4) {
            this.linkMicList.add(i4 + 1, pLVLinkMicItemDataBeanRemove);
        } else {
            this.linkMicList.add(i4, pLVLinkMicItemDataBeanRemove);
        }
        callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.13
            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
            public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                linkMicListListener.onLinkMicListChanged(PLVMultiRoleLinkMicList.this.linkMicList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateLinkMicItemInfoWithRtcJoinListInner(final PLVLinkMicItemDataBean linkMicItemDataBean, final String linkMicUid) {
        boolean z2;
        if (linkMicItemDataBean == null) {
            return false;
        }
        for (Map.Entry<String, PLVLinkMicItemDataBean> entry : this.rtcJoinMap.entrySet()) {
            String key = entry.getKey();
            PLVLinkMicItemDataBean value = entry.getValue();
            if (linkMicUid != null && linkMicUid.equals(key)) {
                if (linkMicItemDataBean.isRtcJoinStatus()) {
                    z2 = false;
                } else {
                    linkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_RTC_JOIN);
                    updateLinkMicItemMediaStatus(value, linkMicItemDataBean);
                    z2 = true;
                }
                if (getLinkMicItemWithLinkMicId(linkMicUid) == null) {
                    final int size = linkMicItemDataBean.isTeacher() ? 0 : this.linkMicList.size();
                    if (isLeaderId(linkMicItemDataBean.getLinkMicId())) {
                        size = 0;
                        for (int i2 = 0; i2 < this.linkMicList.size(); i2++) {
                            if (this.linkMicList.get(i2).isTeacher()) {
                                size = i2 + 1;
                            }
                        }
                    }
                    this.linkMicList.add(size, linkMicItemDataBean);
                    if (value.getStreamType() != 0) {
                        if (2 == value.getStreamType() && (isTeacherLinkMicId(linkMicUid) || isLeaderId(linkMicUid))) {
                            callOnTeacherScreenStream(linkMicUid, true);
                            return false;
                        }
                        linkMicItemDataBean.setStreamType(value.getStreamType());
                    } else if (isTeacherLinkMicId(linkMicUid) || isLeaderId(linkMicUid)) {
                        linkMicItemDataBean.setStreamType(1);
                        callOnTeacherScreenStream(linkMicUid, true);
                    } else {
                        linkMicItemDataBean.setStreamType(2);
                    }
                    updateLinkMicItemMediaStatus(value, linkMicItemDataBean);
                    callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.15
                        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
                        public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                            linkMicListListener.onLinkMicItemInsert(linkMicItemDataBean, size);
                        }
                    });
                }
                return z2;
            }
        }
        return false;
    }

    private void updateLinkMicItemMediaStatus(PLVLinkMicItemDataBean rtcJoinLinkMicItem, PLVLinkMicItemDataBean linkMicItemDataBean) {
        if (rtcJoinLinkMicItem == null || linkMicItemDataBean == null) {
            return;
        }
        PLVLinkMicItemDataBean.MuteMedia muteVideoInRtcJoinList = rtcJoinLinkMicItem.getMuteVideoInRtcJoinList(linkMicItemDataBean.getStreamType());
        if (muteVideoInRtcJoinList != null) {
            linkMicItemDataBean.setMuteVideo(muteVideoInRtcJoinList.isMute());
        } else if (linkMicItemDataBean.isGuest()) {
            linkMicItemDataBean.setMuteVideo(false);
        } else {
            linkMicItemDataBean.setMuteVideo(!PLVLinkMicEventSender.getInstance().isVideoLinkMicType());
        }
        PLVLinkMicItemDataBean.MuteMedia muteAudioInRtcJoinList = rtcJoinLinkMicItem.getMuteAudioInRtcJoinList(linkMicItemDataBean.getStreamType());
        if (muteAudioInRtcJoinList != null) {
            linkMicItemDataBean.setMuteAudio(muteAudioInRtcJoinList.isMute());
        } else {
            linkMicItemDataBean.setMuteAudio(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLinkMicListWithJoinStatus(PLVLinkMicJoinStatus data) {
        final List<PLVJoinInfoEvent> joinList = data.getJoinList();
        final List<PLVLinkMicJoinStatus.WaitListBean> waitList = data.getWaitList();
        Iterator<PLVJoinInfoEvent> it = joinList.iterator();
        while (it.hasNext()) {
            PLVJoinInfoEvent next = it.next();
            if ("guest".equals(next.getUserType()) && !next.getClassStatus().isVoice()) {
                it.remove();
                waitList.add(PLVLinkMicDataMapper.map2WaitListBean(next));
                PLVCommonLog.d(TAG, String.format(Locale.US, "guest user [%s] lies in joinList but not join at all, so we move him to waitList manually.", next.toString()));
            }
        }
        final boolean[] zArr = new boolean[1];
        callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.8
            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
            public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                List<String> listOnUpdateLinkMicItemStatus = linkMicListListener.onUpdateLinkMicItemStatus(joinList, waitList);
                if (listOnUpdateLinkMicItemStatus == null || PLVMultiRoleLinkMicList.this.linkMicList.size() <= 0) {
                    return;
                }
                zArr[0] = true;
                Iterator<String> it2 = listOnUpdateLinkMicItemStatus.iterator();
                while (it2.hasNext()) {
                    PLVMultiRoleLinkMicList.this.rtcJoinMap.remove(it2.next());
                }
            }
        });
        for (PLVJoinInfoEvent pLVJoinInfoEvent : joinList) {
            final PLVLinkMicItemDataBean pLVLinkMicItemDataBeanMap2LinkMicItemData = PLVLinkMicDataMapper.map2LinkMicItemData(pLVJoinInfoEvent);
            final PLVSocketUserBean pLVSocketUserBeanMap2SocketUserBean = PLVLinkMicDataMapper.map2SocketUserBean(pLVJoinInfoEvent);
            final boolean z2 = pLVJoinInfoEvent.getClassStatus() != null && pLVJoinInfoEvent.getClassStatus().isGroupLeader();
            callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.9
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
                public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                    if (linkMicListListener.onUpdateLinkMicItemInfo(pLVSocketUserBeanMap2SocketUserBean, pLVLinkMicItemDataBeanMap2LinkMicItemData, true, z2)) {
                        zArr[0] = true;
                    }
                }
            });
        }
        removeLinkMicItemNoExistServer(joinList);
        sortLinkMicList(joinList);
        for (PLVLinkMicJoinStatus.WaitListBean waitListBean : waitList) {
            final PLVLinkMicItemDataBean pLVLinkMicItemDataBeanMap2LinkMicItemData2 = PLVLinkMicDataMapper.map2LinkMicItemData(waitListBean);
            final PLVSocketUserBean pLVSocketUserBeanMap2SocketUserBean2 = PLVLinkMicDataMapper.map2SocketUserBean(waitListBean);
            callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.10
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
                public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                    if (linkMicListListener.onUpdateLinkMicItemInfo(pLVSocketUserBeanMap2SocketUserBean2, pLVLinkMicItemDataBeanMap2LinkMicItemData2, false, false)) {
                        zArr[0] = true;
                    }
                }
            });
        }
        if (zArr[0]) {
            callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.11
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
                public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                    linkMicListListener.onLinkMicItemInfoChanged();
                }
            });
        }
    }

    public void addMyItemToLinkMicList(boolean curEnableLocalVideo, boolean curEnableLocalAudio) {
        addMyItemToLinkMicListInner(curEnableLocalVideo, curEnableLocalAudio);
    }

    public void addOnLinkMicListListener(OnLinkMicListListener listListener) {
        if (listListener == null || this.onLinkMicListListeners.contains(listListener)) {
            return;
        }
        this.onLinkMicListListeners.add(listListener);
    }

    public void destroy() {
        this.linkMicList.clear();
        this.rtcJoinMap.clear();
        cleanTeacherScreenStream();
        this.onLinkMicListListeners.clear();
        dispose(this.linkMicListTimerDisposable);
        dispose(this.linkMicListOnceDisposable);
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
        IPLVLinkMicManager iPLVLinkMicManager = this.linkMicManager;
        if (iPLVLinkMicManager != null) {
            iPLVLinkMicManager.removeEventHandler(this.linkMicEventListener);
        }
    }

    public void disposeRequestData() {
        dispose(this.linkMicListTimerDisposable);
    }

    public List<PLVLinkMicItemDataBean> getData() {
        return this.linkMicList;
    }

    public PLVLinkMicItemDataBean getItem(int linkMicListPos) {
        if (linkMicListPos < 0 || linkMicListPos >= this.linkMicList.size()) {
            return null;
        }
        return this.linkMicList.get(linkMicListPos);
    }

    public Pair<Integer, PLVLinkMicItemDataBean> getLinkMicItemWithLinkMicId(String linkMicId) {
        return getLinkMicItemWithLinkMicIdInner(linkMicId);
    }

    @Nullable
    public PLVLinkMicItemDataBean getMyLinkMicItemBean() {
        return this.myLinkMicItemBean;
    }

    public Map<String, PLVLinkMicItemDataBean> getRtcJoinMap() {
        return this.rtcJoinMap;
    }

    public void notifyLeaderChanged(String groupLeaderId) {
        this.groupLeaderId = groupLeaderId;
        sortLinkMicList(groupLeaderId);
    }

    public void observeRTCEvent(IPLVLinkMicManager linkMicManager) {
        this.linkMicManager = linkMicManager;
        observeRTCEventInner();
    }

    public void removeMyItemToLinkMicList() {
        removeMyItemToLinkMicListInner();
    }

    public void requestData() {
        requestLinkMicListApi();
    }

    public void setMyLinkMicId(String myLinkMicId) {
        this.myLinkMicId = myLinkMicId;
        createMyLinkMicItem(myLinkMicId);
    }

    public boolean updateLinkMicItemInfoWithRtcJoinList(PLVLinkMicItemDataBean linkMicItemDataBean, final String linkMicUid) {
        return updateLinkMicItemInfoWithRtcJoinListInner(linkMicItemDataBean, linkMicUid);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptUserJoinLeave(final String linkMicUid, final int streamType) {
        Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.23
            @Override // java.lang.Runnable
            public void run() {
                final Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = PLVMultiRoleLinkMicList.this.getLinkMicItemWithLinkMicId(linkMicUid);
                if (linkMicItemWithLinkMicId != null) {
                    if (streamType == 0 || ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).getStreamType() == streamType) {
                        PLVMultiRoleLinkMicList.this.linkMicList.remove(linkMicItemWithLinkMicId.second);
                        PLVMultiRoleLinkMicList.this.callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.23.1
                            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
                            public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                                Pair pair = linkMicItemWithLinkMicId;
                                linkMicListListener.onLinkMicItemRemove((PLVLinkMicItemDataBean) pair.second, ((Integer) pair.first).intValue());
                            }
                        });
                    }
                }
            }
        };
        if (streamType == 0) {
            this.rtcJoinMap.remove(linkMicUid);
            runnable.run();
            if (this.teacherScreenStreamMap.containsKey(linkMicUid)) {
                callOnTeacherScreenStream(linkMicUid, false);
                return;
            }
            return;
        }
        PLVLinkMicItemDataBean pLVLinkMicItemDataBean = this.rtcJoinMap.get(linkMicUid);
        if (pLVLinkMicItemDataBean == null || !pLVLinkMicItemDataBean.includeStreamType(streamType)) {
            return;
        }
        if (pLVLinkMicItemDataBean.equalStreamType(streamType)) {
            this.rtcJoinMap.remove(linkMicUid);
        } else if (2 == streamType) {
            pLVLinkMicItemDataBean.setStreamType(1);
        } else {
            pLVLinkMicItemDataBean.setStreamType(2);
        }
        if (2 == streamType && this.teacherScreenStreamMap.containsKey(linkMicUid)) {
            callOnTeacherScreenStream(linkMicUid, false);
        } else {
            runnable.run();
        }
    }

    private void sortLinkMicList(List<PLVJoinInfoEvent> joinList) {
        int size = this.linkMicList.size();
        PLVLinkMicItemDataBean[] pLVLinkMicItemDataBeanArr = new PLVLinkMicItemDataBean[size];
        ArrayList arrayList = new ArrayList(joinList);
        Iterator it = arrayList.iterator();
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;
        while (true) {
            boolean z2 = true;
            if (!it.hasNext()) {
                break;
            }
            i4++;
            PLVJoinInfoEvent pLVJoinInfoEvent = (PLVJoinInfoEvent) it.next();
            String userId = pLVJoinInfoEvent.getUserId();
            if (userId != null && userId.equals(this.groupLeaderId)) {
                i3 = i4;
            }
            Iterator<PLVLinkMicItemDataBean> it2 = this.linkMicList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z2 = false;
                    break;
                }
                PLVLinkMicItemDataBean next = it2.next();
                if (userId != null && userId.equals(next.getLinkMicId())) {
                    break;
                }
            }
            if (!z2 || "teacher".equals(pLVJoinInfoEvent.getUserType())) {
                it.remove();
                i4--;
            }
        }
        if (i3 != -1) {
            arrayList.add(0, (PLVJoinInfoEvent) arrayList.remove(i3));
        }
        ArrayList arrayList2 = new ArrayList();
        int i5 = -1;
        boolean z3 = false;
        for (PLVLinkMicItemDataBean pLVLinkMicItemDataBean : this.linkMicList) {
            if (!pLVLinkMicItemDataBean.isTeacher() && pLVLinkMicItemDataBean.getLinkMicId() != null) {
                i5++;
                String linkMicId = pLVLinkMicItemDataBean.getLinkMicId();
                Iterator it3 = arrayList.iterator();
                int i6 = i2;
                int i7 = i5;
                while (it3.hasNext()) {
                    i6++;
                    if (linkMicId.equals(((PLVJoinInfoEvent) it3.next()).getUserId()) && i5 != i6) {
                        z3 = true;
                        i7 = i6;
                    }
                }
                while (true) {
                    if (i7 >= size) {
                        break;
                    }
                    if (pLVLinkMicItemDataBeanArr[i7] == null) {
                        pLVLinkMicItemDataBeanArr[i7] = pLVLinkMicItemDataBean;
                        break;
                    }
                    i7++;
                }
            } else {
                arrayList2.add(0, pLVLinkMicItemDataBean);
            }
            i2 = -1;
        }
        if (z3) {
            arrayList2.addAll(Arrays.asList(pLVLinkMicItemDataBeanArr).subList(0, size - arrayList2.size()));
            this.linkMicList.clear();
            this.linkMicList.addAll(arrayList2);
            callbackToListener(new ListenerRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.14
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.ListenerRunnable
                public void run(@NonNull OnLinkMicListListener linkMicListListener) {
                    linkMicListListener.onLinkMicListChanged(PLVMultiRoleLinkMicList.this.linkMicList);
                }
            });
        }
    }
}
