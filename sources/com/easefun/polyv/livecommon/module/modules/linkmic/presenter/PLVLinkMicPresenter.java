package com.easefun.polyv.livecommon.module.modules.linkmic.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicDataMapper;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicListShowMode;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicListShowModeGetter;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMuteCacheList;
import com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy;
import com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVRTCWatchEnabledStrategy;
import com.easefun.polyv.livescenes.linkmic.IPolyvLinkMicManager;
import com.easefun.polyv.livescenes.linkmic.listener.PolyvLinkMicEventListener;
import com.easefun.polyv.livescenes.linkmic.listener.PolyvLinkMicListener;
import com.easefun.polyv.livescenes.linkmic.manager.PolyvLinkMicConfig;
import com.easefun.polyv.livescenes.linkmic.manager.PolyvLinkMicManagerFactory;
import com.google.android.exoplayer2.audio.SilenceSkippingAudioProcessor;
import com.hjq.permissions.Permission;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.permission.PLVFastPermission;
import com.plv.foundationsdk.permission.PLVOnPermissionCallback;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.linkmic.PLVLinkMicEventHandler;
import com.plv.linkmic.log.PLVLinkMicTraceLogSender;
import com.plv.linkmic.model.PLVJoinInfoEvent;
import com.plv.linkmic.model.PLVLinkMicJoinStatus;
import com.plv.linkmic.model.PLVLinkMicJoinSuccess;
import com.plv.linkmic.model.PLVLinkMicMedia;
import com.plv.linkmic.repository.PLVLinkMicDataRepository;
import com.plv.linkmic.repository.PLVLinkMicHttpRequestException;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.livescenes.linkmic.vo.PLVLinkMicEngineParam;
import com.plv.livescenes.log.linkmic.PLVLinkMicELog;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLinkMicPresenter implements IPLVLinkMicContract.IPLVLinkMicPresenter {
    private static final int DELAY_TO_GET_LINK_MIC_LIST = 1000;
    private static final int INTERVAL_TO_GET_LINK_MIC_LIST = 20000;
    private static final int LINK_MIC_INITIATED = 3;
    private static final int LINK_MIC_UNINITIATED = 1;
    private static final String TAG = "PLVLinkMicPresenter";
    private static final int TIME_OUT_JOIN_CHANNEL = 20000;

    @Nullable
    private List<Runnable> actionAfterLinkMicEngineCreated;
    private PolyvLinkMicEventListener eventListener;
    private Disposable getLinkMicListDelay;
    private Disposable getLinkMicListTimer;
    private boolean isAudioLinkMic;
    private boolean isMuteAllAudio;
    private boolean isMuteAllVideo;
    private boolean isTeacherOpenLinkMic;
    private boolean isWatchRtc;
    private Disposable linkJoinTimer;
    private IPolyvLinkMicManager linkMicManager;

    @Nullable
    private PLVLinkMicMsgHandler linkMicMsgHandler;

    @Nullable
    private IPLVLinkMicContract.IPLVLinkMicView linkMicView;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private String mainTeacherLinkMicId;
    private PLVSocketMessageObserver.OnMessageListener messageListener;
    private String myLinkMicId;

    @Nullable
    private IPLVRTCInvokeStrategy rtcInvokeStrategy;
    private PolyvLinkMicSocketEventListener socketEventListener;
    private int linkMicInitState = 1;
    private long socketRefreshOpenStatusData = -1;
    private boolean hasInitFirstScreenUser = false;
    private boolean hasInitFirstTeacherLocation = false;
    private List<PLVLinkMicItemDataBean> linkMicList = new LinkedList();
    private PLVLinkMicMuteCacheList muteCacheList = new PLVLinkMicMuteCacheList();
    private String avConnectMode = "";

    public class PolyvLinkMicEventListenerImpl extends PolyvLinkMicEventListener {
        private PolyvLinkMicEventListenerImpl() {
        }

        @Override // com.plv.linkmic.PLVLinkMicEventHandler
        public void onJoinChannelSuccess(String uid) {
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicEventListenerImpl.onJoinChannelSuccess, uid=" + uid);
            PLVLinkMicPresenter.this.stopJoinTimeoutCount();
            PLVLinkMicPresenter pLVLinkMicPresenter = PLVLinkMicPresenter.this;
            pLVLinkMicPresenter.loadLinkMicConnectMode(pLVLinkMicPresenter.avConnectMode);
        }

        @Override // com.plv.linkmic.PLVLinkMicEventHandler
        public void onLeaveChannel() {
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicEventListenerImpl.onLeaveChannel");
            PLVLinkMicPresenter.this.leaveChannel();
        }

        @Override // com.plv.linkmic.PLVLinkMicEventHandler
        public void onLocalAudioVolumeIndication(PLVLinkMicEventHandler.PLVAudioVolumeInfo speaker) {
            Iterator it = PLVLinkMicPresenter.this.linkMicList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                PLVLinkMicItemDataBean pLVLinkMicItemDataBean = (PLVLinkMicItemDataBean) it.next();
                if (pLVLinkMicItemDataBean.getLinkMicId().equals(speaker.getUid())) {
                    pLVLinkMicItemDataBean.setCurVolume(speaker.getVolume());
                    break;
                }
            }
            if (PLVLinkMicPresenter.this.linkMicView != null) {
                PLVLinkMicPresenter.this.linkMicView.onLocalUserMicVolumeChanged();
            }
        }

        @Override // com.plv.linkmic.PLVLinkMicEventHandler
        public void onNetworkQuality(int quality) {
            super.onNetworkQuality(quality);
            if (PLVLinkMicPresenter.this.linkMicView != null) {
                PLVLinkMicPresenter.this.linkMicView.onNetQuality(quality);
            }
        }

        @Override // com.plv.linkmic.PLVLinkMicEventHandler
        public void onRemoteAudioVolumeIndication(PLVLinkMicEventHandler.PLVAudioVolumeInfo[] speakers) {
            boolean z2;
            for (PLVLinkMicItemDataBean pLVLinkMicItemDataBean : PLVLinkMicPresenter.this.linkMicList) {
                if (!pLVLinkMicItemDataBean.getLinkMicId().equals(PLVLinkMicPresenter.this.myLinkMicId)) {
                    int length = speakers.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            z2 = false;
                            break;
                        }
                        PLVLinkMicEventHandler.PLVAudioVolumeInfo pLVAudioVolumeInfo = speakers[i2];
                        if (pLVLinkMicItemDataBean.getLinkMicId().equals(String.valueOf(pLVAudioVolumeInfo.getUid()))) {
                            pLVLinkMicItemDataBean.setCurVolume(pLVAudioVolumeInfo.getVolume());
                            z2 = true;
                            break;
                        }
                        i2++;
                    }
                    if (!z2) {
                        pLVLinkMicItemDataBean.setCurVolume(0);
                    }
                }
            }
            if (PLVLinkMicPresenter.this.linkMicView != null) {
                PLVLinkMicPresenter.this.linkMicView.onRemoteUserVolumeChanged(PLVLinkMicPresenter.this.linkMicList);
            }
        }

        @Override // com.plv.linkmic.PLVLinkMicEventHandler
        public void onUserJoined(String uid) {
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicEventListenerImpl.onUserJoined, uid=" + uid);
            PLVLinkMicPresenter pLVLinkMicPresenter = PLVLinkMicPresenter.this;
            pLVLinkMicPresenter.dispose(pLVLinkMicPresenter.getLinkMicListDelay);
            PLVLinkMicPresenter.this.getLinkMicListDelay = PLVRxTimer.delay(1000L, new Consumer<Object>() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.PolyvLinkMicEventListenerImpl.1
                @Override // io.reactivex.functions.Consumer
                public void accept(Object o2) throws Exception {
                    PLVLinkMicPresenter.this.requestLinkMicListFromServer();
                }
            });
        }

        @Override // com.plv.linkmic.PLVLinkMicEventHandler
        public void onUserMuteAudio(String uid, boolean mute) {
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicEventListenerImpl.onUserMuteAudio,uid=" + uid + " mute=" + mute);
            int i2 = 0;
            while (true) {
                if (i2 >= PLVLinkMicPresenter.this.linkMicList.size()) {
                    break;
                }
                PLVLinkMicItemDataBean pLVLinkMicItemDataBean = (PLVLinkMicItemDataBean) PLVLinkMicPresenter.this.linkMicList.get(i2);
                if (uid.equals(pLVLinkMicItemDataBean.getLinkMicId())) {
                    pLVLinkMicItemDataBean.setMuteAudio(mute);
                    if (PLVLinkMicPresenter.this.linkMicView != null) {
                        PLVLinkMicPresenter.this.linkMicView.onUserMuteAudio(uid, mute, i2);
                    }
                } else {
                    i2++;
                }
            }
            PLVLinkMicPresenter.this.muteCacheList.addOrUpdateAudioMuteCacheList(uid, mute);
        }

        @Override // com.plv.linkmic.PLVLinkMicEventHandler
        public void onUserMuteVideo(String uid, boolean mute) {
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicEventListenerImpl.onUserMuteVideo uid=" + uid);
            int i2 = 0;
            while (true) {
                if (i2 >= PLVLinkMicPresenter.this.linkMicList.size()) {
                    break;
                }
                PLVLinkMicItemDataBean pLVLinkMicItemDataBean = (PLVLinkMicItemDataBean) PLVLinkMicPresenter.this.linkMicList.get(i2);
                if (uid.equals(pLVLinkMicItemDataBean.getLinkMicId())) {
                    pLVLinkMicItemDataBean.setMuteVideo(mute);
                    if (PLVLinkMicPresenter.this.linkMicView != null) {
                        PLVLinkMicPresenter.this.linkMicView.onUserMuteVideo(uid, mute, i2);
                    }
                } else {
                    i2++;
                }
            }
            PLVLinkMicPresenter.this.muteCacheList.addOrUpdateVideoMuteCacheList(uid, mute);
        }

        @Override // com.plv.linkmic.PLVLinkMicEventHandler
        public void onUserOffline(String uid) {
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicEventListenerImpl.onUserOffline, uid=" + uid);
            Iterator it = PLVLinkMicPresenter.this.linkMicList.iterator();
            while (it.hasNext()) {
                if (((PLVLinkMicItemDataBean) it.next()).getLinkMicId().equals(uid)) {
                    if (PLVLinkMicPresenter.this.linkMicView != null) {
                        PLVLinkMicPresenter.this.linkMicView.onUsersLeave(Collections.singletonList(uid));
                    }
                    it.remove();
                    return;
                }
            }
        }
    }

    public class PolyvLinkMicSocketEventListener implements PLVLinkMicMsgHandler.OnLinkMicDataListener {
        private PolyvLinkMicSocketEventListener() {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onFinishClass() {
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicSocketEventListener.onFinishClass");
            PLVLinkMicPresenter.this.handleTeacherCloseLinkMic();
            if (PLVLinkMicPresenter.this.rtcInvokeStrategy != null) {
                PLVLinkMicPresenter.this.rtcInvokeStrategy.setLiveEnd();
            }
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onLinkMicConnectMode(String avConnectMode) {
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicSocketEventListener.onLinkMicConnectMode " + avConnectMode);
            PLVLinkMicPresenter.this.avConnectMode = avConnectMode;
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onSwitchFirstScreen(final String linkMicId) {
            int mediaViewIndexInLinkMicList;
            boolean z2;
            IPLVLinkMicContract.IPLVLinkMicView iPLVLinkMicView = PLVLinkMicPresenter.this.linkMicView;
            if (iPLVLinkMicView == null || TextUtils.isEmpty(linkMicId)) {
                return;
            }
            if (PLVLinkMicPresenter.this.rtcInvokeStrategy != null) {
                PLVLinkMicPresenter.this.rtcInvokeStrategy.setFirstScreenLinkMicId(linkMicId, PLVLinkMicPresenter.this.isMuteAllVideo);
            }
            if (PLVLinkMicPresenter.this.linkMicList.isEmpty()) {
                return;
            }
            PLVLinkMicItemDataBean pLVLinkMicItemDataBean = (PLVLinkMicItemDataBean) PLVLinkMicPresenter.this.linkMicList.get(0);
            for (PLVLinkMicItemDataBean pLVLinkMicItemDataBean2 : PLVLinkMicPresenter.this.linkMicList) {
                if (pLVLinkMicItemDataBean2.getLinkMicId().equals(linkMicId)) {
                    pLVLinkMicItemDataBean = pLVLinkMicItemDataBean2;
                }
            }
            int iIndexOf = PLVLinkMicPresenter.this.linkMicList.indexOf(pLVLinkMicItemDataBean);
            if (PLVLinkMicPresenter.this.liveRoomDataManager.getConfig().isAloneChannelType()) {
                if (iIndexOf == iPLVLinkMicView.getMediaViewIndexInLinkMicList()) {
                    return;
                }
                if (PLVLinkMicPresenter.this.mainTeacherLinkMicId == null || !PLVLinkMicPresenter.this.mainTeacherLinkMicId.equals(linkMicId)) {
                    iPLVLinkMicView.performClickInLinkMicListItem(iIndexOf);
                } else if (iPLVLinkMicView.isMediaShowInLinkMicList()) {
                    iPLVLinkMicView.performClickInLinkMicListItem(iPLVLinkMicView.getMediaViewIndexInLinkMicList());
                }
            } else {
                if (iIndexOf == 0) {
                    return;
                }
                if (iPLVLinkMicView.isMediaShowInLinkMicList()) {
                    mediaViewIndexInLinkMicList = iPLVLinkMicView.getMediaViewIndexInLinkMicList();
                    z2 = true;
                    iPLVLinkMicView.onSwitchPPTViewLocation(true);
                } else {
                    mediaViewIndexInLinkMicList = -1;
                    z2 = false;
                }
                PLVLinkMicItemDataBean pLVLinkMicItemDataBean3 = (PLVLinkMicItemDataBean) PLVLinkMicPresenter.this.linkMicList.get(0);
                PLVLinkMicPresenter.this.linkMicList.remove(pLVLinkMicItemDataBean3);
                PLVLinkMicPresenter.this.linkMicList.remove(pLVLinkMicItemDataBean);
                PLVLinkMicPresenter.this.linkMicList.add(0, pLVLinkMicItemDataBean);
                PLVLinkMicPresenter.this.linkMicList.add(iIndexOf, pLVLinkMicItemDataBean3);
                iPLVLinkMicView.onSwitchFirstScreen(linkMicId);
                if (z2) {
                    iPLVLinkMicView.performClickInLinkMicListItem(mediaViewIndexInLinkMicList);
                }
            }
            iPLVLinkMicView.updateFirstScreenChanged(linkMicId, 0, iIndexOf);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onSwitchPPTViewLocation(boolean toMainScreen) {
            if (PLVLinkMicPresenter.this.rtcInvokeStrategy == null || !PLVLinkMicPresenter.this.rtcInvokeStrategy.isJoinChannel() || PLVLinkMicPresenter.this.linkMicView == null) {
                return;
            }
            PLVLinkMicPresenter.this.linkMicView.onSwitchPPTViewLocation(toMainScreen);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onTeacherAllowMeToJoin() {
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicSocketEventListener.onTeacherAllowMeToJoin");
            PLVLinkMicPresenter pLVLinkMicPresenter = PLVLinkMicPresenter.this;
            pLVLinkMicPresenter.handleTeacherAllowJoin(pLVLinkMicPresenter.isAudioLinkMic);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onTeacherCloseLinkMic() {
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicSocketEventListener.onTeacherCloseLinkMic");
            PLVLinkMicPresenter.this.handleTeacherCloseLinkMic();
            if (PLVLinkMicPresenter.this.rtcInvokeStrategy == null || !PLVLinkMicPresenter.this.isJoinLinkMic()) {
                return;
            }
            PLVLinkMicPresenter.this.rtcInvokeStrategy.setLeaveLinkMic();
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onTeacherHangupMe() {
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicSocketEventListener.onTeacherHangupMe");
            if (PLVLinkMicPresenter.this.rtcInvokeStrategy != null) {
                PLVLinkMicPresenter.this.rtcInvokeStrategy.setLeaveLinkMic();
            }
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onTeacherMuteMedia(boolean isMute, boolean isAudio) {
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicSocketEventListener.onTeacherMuteMedia");
            if (PLVLinkMicPresenter.this.rtcInvokeStrategy == null || !PLVLinkMicPresenter.this.rtcInvokeStrategy.isJoinChannel()) {
                return;
            }
            for (int i2 = 0; i2 < PLVLinkMicPresenter.this.linkMicList.size(); i2++) {
                PLVLinkMicItemDataBean pLVLinkMicItemDataBean = (PLVLinkMicItemDataBean) PLVLinkMicPresenter.this.linkMicList.get(i2);
                if (pLVLinkMicItemDataBean.getLinkMicId().equals(PLVLinkMicPresenter.this.myLinkMicId)) {
                    if (isAudio) {
                        pLVLinkMicItemDataBean.setMuteAudio(isMute);
                        PLVLinkMicPresenter.this.linkMicManager.muteLocalAudio(isMute);
                        if (PLVLinkMicPresenter.this.linkMicView != null) {
                            PLVLinkMicPresenter.this.linkMicView.onUserMuteAudio(PLVLinkMicPresenter.this.myLinkMicId, isMute, i2);
                            return;
                        }
                        return;
                    }
                    pLVLinkMicItemDataBean.setMuteVideo(isMute);
                    PLVLinkMicPresenter.this.linkMicManager.muteLocalVideo(isMute);
                    if (PLVLinkMicPresenter.this.linkMicView != null) {
                        PLVLinkMicPresenter.this.linkMicView.onUserMuteVideo(PLVLinkMicPresenter.this.myLinkMicId, isMute, i2);
                        return;
                    }
                    return;
                }
            }
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onTeacherOpenLinkMic() {
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicSocketEventListener.onTeacherOpenLinkMic");
            PLVLinkMicPresenter.this.isTeacherOpenLinkMic = true;
            if (PLVLinkMicPresenter.this.linkMicView != null) {
                PLVLinkMicPresenter.this.linkMicView.onTeacherOpenLinkMic();
            }
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onTeacherReceiveJoinRequest() {
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicSocketEventListener.onTeacherReceiveJoinRequest");
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onTeacherSendCup(String linkMicId, int cupNum) {
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicSocketEventListener.onTeacherSendCup");
            if (PLVLinkMicPresenter.this.rtcInvokeStrategy == null || !PLVLinkMicPresenter.this.rtcInvokeStrategy.isJoinChannel()) {
                return;
            }
            for (PLVLinkMicItemDataBean pLVLinkMicItemDataBean : PLVLinkMicPresenter.this.linkMicList) {
                if (pLVLinkMicItemDataBean.getLinkMicId().equals(linkMicId)) {
                    pLVLinkMicItemDataBean.setCupNum(cupNum);
                    return;
                }
            }
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onUpdateLinkMicType(boolean isAudio) {
            PLVLinkMicPresenter.this.socketRefreshOpenStatusData = System.currentTimeMillis();
            PLVLinkMicPresenter.this.isAudioLinkMic = isAudio;
        }

        @Override // com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicMsgHandler.OnLinkMicDataListener
        public void onUserJoinSuccess(PLVLinkMicItemDataBean dataBean) {
            boolean z2;
            PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PolyvLinkMicSocketEventListener.onUserJoinSuccess");
            if (PLVLinkMicPresenter.this.rtcInvokeStrategy == null || !PLVLinkMicPresenter.this.rtcInvokeStrategy.isJoinChannel()) {
                return;
            }
            Iterator it = PLVLinkMicPresenter.this.linkMicList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z2 = false;
                    break;
                } else if (((PLVLinkMicItemDataBean) it.next()).getLinkMicId().equals(dataBean.getLinkMicId())) {
                    z2 = true;
                    break;
                }
            }
            if (z2) {
                return;
            }
            PLVLinkMicPresenter.this.muteCacheList.updateUserMuteCacheWhenJoinList(dataBean);
            if (dataBean.isTeacher()) {
                PLVLinkMicPresenter.this.linkMicList.add(0, dataBean);
            } else if (dataBean.getLinkMicId().equals(PLVLinkMicPresenter.this.myLinkMicId)) {
                PLVCommonLog.d(PLVLinkMicPresenter.TAG, "onUserJoinSuccess-> 收到自己的joinSuccess事件");
            } else {
                PLVLinkMicPresenter.this.linkMicList.add(dataBean);
            }
            if (PLVLinkMicPresenter.this.linkMicView != null) {
                PLVLinkMicPresenter.this.linkMicView.onUsersJoin(Collections.singletonList(dataBean.getLinkMicId()));
            }
        }
    }

    public PLVLinkMicPresenter(final IPLVLiveRoomDataManager liveRoomDataManager, @Nullable final IPLVLinkMicContract.IPLVLinkMicView view) {
        this.myLinkMicId = "";
        this.eventListener = new PolyvLinkMicEventListenerImpl();
        this.socketEventListener = new PolyvLinkMicSocketEventListener();
        this.liveRoomDataManager = liveRoomDataManager;
        PolyvLinkMicConfig.getInstance().init(liveRoomDataManager.getConfig().getUser().getViewerId(), false);
        this.linkMicView = view;
        this.actionAfterLinkMicEngineCreated = new ArrayList();
        this.linkMicManager = PolyvLinkMicManagerFactory.createNewLinkMicManager();
        this.linkMicManager.initEngine(new PLVLinkMicEngineParam().setChannelId(liveRoomDataManager.getConfig().getChannelId()).setViewerId(liveRoomDataManager.getConfig().getUser().getViewerId()).setViewerType(liveRoomDataManager.getConfig().getUser().getViewerType()).setNickName(liveRoomDataManager.getConfig().getUser().getViewerName()), new PolyvLinkMicListener() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.1
            @Override // com.plv.livescenes.linkmic.listener.PLVLinkMicListener
            public void onLinkMicEngineCreatedSuccess() {
                PLVCommonLog.d(PLVLinkMicPresenter.TAG, "连麦初始化成功");
                PLVLinkMicPresenter.this.linkMicInitState = 3;
                PLVLinkMicPresenter.this.linkMicManager.addEventHandler(PLVLinkMicPresenter.this.eventListener);
                if (PLVLinkMicPresenter.this.actionAfterLinkMicEngineCreated != null) {
                    Iterator it = PLVLinkMicPresenter.this.actionAfterLinkMicEngineCreated.iterator();
                    while (it.hasNext()) {
                        ((Runnable) it.next()).run();
                    }
                    PLVLinkMicPresenter.this.actionAfterLinkMicEngineCreated = null;
                }
            }

            @Override // com.plv.livescenes.linkmic.listener.PLVLinkMicListener
            public void onLinkMicError(int errorCode, Throwable throwable) {
                PLVLinkMicPresenter.this.linkMicInitState = 1;
                if (PLVLinkMicPresenter.this.linkMicView != null) {
                    PLVLinkMicPresenter.this.linkMicView.onLinkMicError(errorCode, throwable);
                }
            }
        });
        String linkMicUid = this.linkMicManager.getLinkMicUid();
        this.myLinkMicId = linkMicUid;
        if (TextUtils.isEmpty(linkMicUid)) {
            IPLVLinkMicContract.IPLVLinkMicView iPLVLinkMicView = this.linkMicView;
            if (iPLVLinkMicView != null) {
                iPLVLinkMicView.onLinkMicError(-1, new Throwable("获取到空的linkMicId"));
                return;
            }
            return;
        }
        PLVLinkMicMsgHandler pLVLinkMicMsgHandler = new PLVLinkMicMsgHandler(this.myLinkMicId);
        this.linkMicMsgHandler = pLVLinkMicMsgHandler;
        pLVLinkMicMsgHandler.addLinkMicMsgListener(this.socketEventListener);
        this.isWatchRtc = PLVLinkMicConfig.getInstance().isLowLatencyPureRtcWatch();
        initRTCInvokeStrategy();
        this.messageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.2
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String listenEvent, String event, String message) {
                if (event != null && event.equals("RELOGIN") && PLVLinkMicPresenter.this.isJoinLinkMic()) {
                    PLVLinkMicPresenter.this.leaveLinkMic();
                }
            }
        };
        PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.messageListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanLinkMicListData() {
        PLVCommonLog.d(TAG, "cleanLinkMicListData() called \n" + Log.getStackTraceString(new Throwable()));
        this.linkMicList.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTeacherAllowJoin(boolean isAudioLinkMic) {
        this.linkMicManager.enableLocalVideo(!isAudioLinkMic);
        IPLVRTCInvokeStrategy iPLVRTCInvokeStrategy = this.rtcInvokeStrategy;
        if (iPLVRTCInvokeStrategy != null) {
            iPLVRTCInvokeStrategy.setJoinLinkMic();
        }
        IPLVLinkMicContract.IPLVLinkMicView iPLVLinkMicView = this.linkMicView;
        if (iPLVLinkMicView != null) {
            iPLVLinkMicView.onTeacherAllowJoin();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTeacherCloseLinkMic() {
        if (this.isTeacherOpenLinkMic) {
            this.isTeacherOpenLinkMic = false;
            IPLVLinkMicContract.IPLVLinkMicView iPLVLinkMicView = this.linkMicView;
            if (iPLVLinkMicView != null) {
                iPLVLinkMicView.onTeacherCloseLinkMic();
            }
        }
    }

    private void initRTCInvokeStrategy() {
        if (PLVLinkMicConfig.getInstance().isLowLatencyPureRtcWatch() && this.isWatchRtc) {
            PLVRTCWatchEnabledStrategy pLVRTCWatchEnabledStrategy = new PLVRTCWatchEnabledStrategy(this, this.linkMicManager, this.liveRoomDataManager, new PLVRTCWatchEnabledStrategy.OnJoinRTCChannelWatchListener() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.3
                @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVRTCWatchEnabledStrategy.OnJoinRTCChannelWatchListener
                public void onJoinRTCChannelWatch() {
                    if (PLVLinkMicPresenter.this.linkMicView != null) {
                        PLVLinkMicPresenter.this.linkMicView.onJoinRtcChannel();
                    }
                    PLVLinkMicPresenter.this.stopJoinTimeoutCount();
                    PLVLinkMicPresenter pLVLinkMicPresenter = PLVLinkMicPresenter.this;
                    pLVLinkMicPresenter.dispose(pLVLinkMicPresenter.getLinkMicListTimer);
                    PLVLinkMicPresenter.this.getLinkMicListTimer = PLVRxTimer.timer(20000, new Consumer<Long>() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.3.1
                        @Override // io.reactivex.functions.Consumer
                        public void accept(Long aLong) throws Exception {
                            PLVLinkMicPresenter.this.requestLinkMicListFromServer();
                        }
                    });
                }
            }, new IPLVRTCInvokeStrategy.OnJoinLinkMicListener() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.4
                @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy.OnJoinLinkMicListener
                public void onJoinLinkMic(PLVLinkMicJoinSuccess data) {
                    boolean z2;
                    PLVLinkMicItemDataBean pLVLinkMicItemDataBeanMap2LinkMicItemData = PLVLinkMicDataMapper.map2LinkMicItemData(data);
                    Iterator it = PLVLinkMicPresenter.this.linkMicList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z2 = false;
                            break;
                        }
                        if (pLVLinkMicItemDataBeanMap2LinkMicItemData.getLinkMicId().equals(((PLVLinkMicItemDataBean) it.next()).getLinkMicId())) {
                            z2 = true;
                            break;
                        }
                    }
                    if (!z2) {
                        if (PLVLinkMicPresenter.this.linkMicList.isEmpty()) {
                            PLVLinkMicPresenter.this.linkMicList.add(pLVLinkMicItemDataBeanMap2LinkMicItemData);
                        } else {
                            PLVLinkMicPresenter.this.linkMicList.add(1, pLVLinkMicItemDataBeanMap2LinkMicItemData);
                        }
                    }
                    if (PLVLinkMicPresenter.this.linkMicView != null) {
                        PLVLinkMicPresenter.this.linkMicView.onChangeListShowMode(PLVLinkMicListShowModeGetter.getJoinedMicShowMode(PLVLinkMicPresenter.this.isAudioLinkMic));
                        PLVLinkMicPresenter.this.linkMicView.onJoinLinkMic();
                        PLVLinkMicPresenter.this.linkMicView.updateAllLinkMicList();
                    }
                    PLVLinkMicPresenter pLVLinkMicPresenter = PLVLinkMicPresenter.this;
                    pLVLinkMicPresenter.loadLinkMicConnectMode(pLVLinkMicPresenter.avConnectMode);
                }
            });
            this.rtcInvokeStrategy = pLVRTCWatchEnabledStrategy;
            pLVRTCWatchEnabledStrategy.setOnLeaveLinkMicListener(new IPLVRTCInvokeStrategy.OnLeaveLinkMicListener() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.5
                @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy.OnLeaveLinkMicListener
                public void onLeaveLinkMic() {
                    Iterator it = PLVLinkMicPresenter.this.linkMicList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        if (((PLVLinkMicItemDataBean) it.next()).getLinkMicId().equals(PLVLinkMicPresenter.this.myLinkMicId)) {
                            if (PLVLinkMicPresenter.this.linkMicView != null) {
                                PLVLinkMicPresenter.this.linkMicView.onUsersLeave(Collections.singletonList(PLVLinkMicPresenter.this.myLinkMicId));
                            }
                            it.remove();
                        }
                    }
                    if (PLVLinkMicPresenter.this.linkMicView != null) {
                        PLVLinkMicPresenter.this.linkMicView.onChangeListShowMode(PLVLinkMicListShowModeGetter.getLeavedMicShowMode());
                        PLVLinkMicPresenter.this.linkMicView.onLeaveLinkMic();
                    }
                }
            });
        } else {
            PLVRTCWatchDisabledStrategy pLVRTCWatchDisabledStrategy = new PLVRTCWatchDisabledStrategy(this, this.linkMicManager, this.liveRoomDataManager, new IPLVRTCInvokeStrategy.OnJoinLinkMicListener() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.6
                @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy.OnJoinLinkMicListener
                public void onJoinLinkMic(PLVLinkMicJoinSuccess data) {
                    PLVLinkMicPresenter.this.stopJoinTimeoutCount();
                    if (!PLVLinkMicPresenter.this.linkMicList.isEmpty()) {
                        PLVCommonLog.w(PLVLinkMicPresenter.TAG, "非无延迟观看，加入连麦时，连麦列表不为空！手动清空连麦列表，连麦列表为：\n" + PLVLinkMicPresenter.this.linkMicList.toString());
                        PLVLinkMicPresenter.this.cleanLinkMicListData();
                    }
                    PLVLinkMicPresenter.this.linkMicList.add(0, PLVLinkMicDataMapper.map2LinkMicItemData(data));
                    if (PLVLinkMicPresenter.this.linkMicView != null) {
                        PLVLinkMicPresenter.this.linkMicView.onJoinRtcChannel();
                        PLVLinkMicPresenter.this.linkMicView.onJoinLinkMic();
                    }
                    PLVLinkMicPresenter pLVLinkMicPresenter = PLVLinkMicPresenter.this;
                    pLVLinkMicPresenter.loadLinkMicConnectMode(pLVLinkMicPresenter.avConnectMode);
                    PLVLinkMicPresenter pLVLinkMicPresenter2 = PLVLinkMicPresenter.this;
                    pLVLinkMicPresenter2.dispose(pLVLinkMicPresenter2.getLinkMicListTimer);
                    PLVLinkMicPresenter.this.getLinkMicListTimer = PLVRxTimer.timer(20000, new Consumer<Long>() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.6.1
                        @Override // io.reactivex.functions.Consumer
                        public void accept(Long aLong) throws Exception {
                            PLVLinkMicPresenter.this.requestLinkMicListFromServer();
                        }
                    });
                }
            });
            this.rtcInvokeStrategy = pLVRTCWatchDisabledStrategy;
            pLVRTCWatchDisabledStrategy.setOnLeaveLinkMicListener(new IPLVRTCInvokeStrategy.OnLeaveLinkMicListener() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.7
                @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy.OnLeaveLinkMicListener
                public void onLeaveLinkMic() {
                    if (PLVLinkMicPresenter.this.linkMicView != null) {
                        PLVLinkMicPresenter.this.linkMicView.onLeaveLinkMic();
                    }
                }
            });
        }
        this.rtcInvokeStrategy.setOnBeforeJoinChannelListener(new IPLVRTCInvokeStrategy.OnBeforeJoinChannelListener() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.8
            @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy.OnBeforeJoinChannelListener
            public void onBeforeJoinChannel(PLVLinkMicListShowMode linkMicListShowMode) {
                PLVLinkMicPresenter.this.startJoinTimeoutCount(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (PLVLinkMicPresenter.this.linkMicView != null) {
                            PLVLinkMicPresenter.this.linkMicView.onJoinChannelTimeout();
                        }
                    }
                });
                if (PLVLinkMicPresenter.this.linkMicView != null) {
                    PLVLinkMicPresenter.this.linkMicView.onPrepareLinkMicList(PLVLinkMicPresenter.this.myLinkMicId, linkMicListShowMode, PLVLinkMicPresenter.this.linkMicList);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadLinkMicConnectMode(String mode) {
        if (TextUtils.isEmpty(mode)) {
            muteVideo(true);
            muteAudio(false);
        } else if ("audio".equals(mode)) {
            muteAudio(true);
            muteVideo(true);
        } else if ("video".equals(mode)) {
            muteVideo(true);
            muteAudio(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestLinkMicListFromServer() {
        String sessionId = this.liveRoomDataManager.getSessionId();
        if (TextUtils.isEmpty(sessionId)) {
            return;
        }
        this.linkMicManager.getLinkStatus(sessionId, new PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicJoinStatus>() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.10
            @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
            public void onFail(PLVLinkMicHttpRequestException throwable) {
                super.onFail(throwable);
                if (PLVLinkMicPresenter.this.linkMicView != null) {
                    PLVLinkMicPresenter.this.linkMicView.onLinkMicError(throwable.getErrorCode(), throwable);
                }
            }

            @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
            public void onSuccess(PLVLinkMicJoinStatus data) {
                boolean z2;
                PLVLinkMicItemDataBean pLVLinkMicItemDataBean;
                PLVCommonLog.d(PLVLinkMicPresenter.TAG, "PLVLinkMicPresenter.requestLinkMicListFromServer.onSuccess->\n" + PLVGsonUtil.toJson(data));
                if (data.getJoinList().isEmpty()) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                Iterator<PLVJoinInfoEvent> it = data.getJoinList().iterator();
                while (it.hasNext()) {
                    PLVJoinInfoEvent next = it.next();
                    if ("guest".equals(next.getUserType()) && !next.getClassStatus().isVoice()) {
                        it.remove();
                    }
                }
                Iterator<PLVJoinInfoEvent> it2 = data.getJoinList().iterator();
                final String userId = "";
                final String userId2 = "";
                while (true) {
                    boolean z3 = true;
                    if (!it2.hasNext()) {
                        break;
                    }
                    PLVJoinInfoEvent next2 = it2.next();
                    String userId3 = next2.getUserId();
                    Iterator it3 = PLVLinkMicPresenter.this.linkMicList.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            z3 = false;
                            break;
                        } else if (userId3.equals(((PLVLinkMicItemDataBean) it3.next()).getLinkMicId())) {
                            break;
                        }
                    }
                    if (!z3) {
                        PLVLinkMicItemDataBean pLVLinkMicItemDataBeanMap2LinkMicItemData = PLVLinkMicDataMapper.map2LinkMicItemData(next2);
                        PLVLinkMicPresenter.this.linkMicList.add(pLVLinkMicItemDataBeanMap2LinkMicItemData);
                        PLVLinkMicPresenter.this.muteCacheList.updateUserMuteCacheWhenJoinList(pLVLinkMicItemDataBeanMap2LinkMicItemData);
                        arrayList.add(next2.getUserId());
                    }
                    String userType = next2.getUserType();
                    if (userType != null) {
                        if (userType.equals("teacher")) {
                            userId = next2.getUserId();
                        } else if (userType.equals("guest") && TextUtils.isEmpty(userId2)) {
                            userId2 = next2.getUserId();
                        }
                    }
                }
                if (TextUtils.isEmpty(userId)) {
                    PLVCommonLog.d(PLVLinkMicPresenter.TAG, "该频道内不存在讲师");
                }
                if (TextUtils.isEmpty(userId2)) {
                    PLVCommonLog.d(PLVLinkMicPresenter.TAG, "该频道内不存在嘉宾");
                }
                String master2 = data.getMaster();
                if (TextUtils.isEmpty(master2)) {
                    master2 = userId;
                }
                if (!TextUtils.isEmpty(master2)) {
                    userId2 = master2;
                }
                if (TextUtils.isEmpty(userId2)) {
                    userId2 = data.getJoinList().get(0).getUserId();
                }
                PLVCommonLog.d(PLVLinkMicPresenter.TAG, "第一画面:" + userId2);
                if (PLVLinkMicPresenter.this.rtcInvokeStrategy != null && PLVLinkMicPresenter.this.rtcInvokeStrategy.isJoinChannel()) {
                    PLVLinkMicPresenter.this.rtcInvokeStrategy.setFirstScreenLinkMicId(userId2, PLVLinkMicPresenter.this.isMuteAllVideo);
                    if (PLVLinkMicPresenter.this.linkMicView != null) {
                        PLVLinkMicPresenter.this.linkMicView.updateFirstScreenChanged(userId2, -1, -1);
                    }
                    if (PLVLinkMicPresenter.this.linkMicList.size() > 0 && !((PLVLinkMicItemDataBean) PLVLinkMicPresenter.this.linkMicList.get(0)).getLinkMicId().equals(userId2)) {
                        Iterator it4 = PLVLinkMicPresenter.this.linkMicList.iterator();
                        while (true) {
                            if (!it4.hasNext()) {
                                pLVLinkMicItemDataBean = null;
                                break;
                            }
                            pLVLinkMicItemDataBean = (PLVLinkMicItemDataBean) it4.next();
                            if (pLVLinkMicItemDataBean.getLinkMicId().equals(userId2)) {
                                PLVLinkMicPresenter.this.linkMicList.remove(pLVLinkMicItemDataBean);
                                break;
                            }
                        }
                        if (pLVLinkMicItemDataBean != null) {
                            PLVLinkMicPresenter.this.linkMicList.add(0, pLVLinkMicItemDataBean);
                        }
                    }
                }
                if (!arrayList.isEmpty() && PLVLinkMicPresenter.this.linkMicView != null) {
                    PLVLinkMicPresenter.this.linkMicView.onUsersJoin(arrayList);
                }
                if (PLVLinkMicPresenter.this.liveRoomDataManager.getConfig().isAloneChannelType() && userId != null && arrayList.contains(userId)) {
                    PLVLinkMicPresenter.this.mainTeacherLinkMicId = userId;
                    if (PLVLinkMicPresenter.this.linkMicView != null) {
                        int i2 = 0;
                        while (true) {
                            if (i2 >= PLVLinkMicPresenter.this.linkMicList.size()) {
                                break;
                            }
                            if (userId.equals(((PLVLinkMicItemDataBean) PLVLinkMicPresenter.this.linkMicList.get(i2)).getLinkMicId())) {
                                PLVLinkMicPresenter.this.linkMicView.onAdjustTeacherLocation(userId, i2, PLVLinkMicPresenter.this.liveRoomDataManager.isSupportRTC(), new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.10.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        if (userId2.equals(userId)) {
                                            return;
                                        }
                                        PLVLinkMicPresenter.this.socketEventListener.onSwitchFirstScreen(userId2);
                                    }
                                });
                                break;
                            }
                            i2++;
                        }
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                Iterator it5 = PLVLinkMicPresenter.this.linkMicList.iterator();
                while (it5.hasNext()) {
                    PLVLinkMicItemDataBean pLVLinkMicItemDataBean2 = (PLVLinkMicItemDataBean) it5.next();
                    String linkMicId = pLVLinkMicItemDataBean2.getLinkMicId();
                    Iterator<PLVJoinInfoEvent> it6 = data.getJoinList().iterator();
                    while (true) {
                        if (!it6.hasNext()) {
                            z2 = false;
                            break;
                        } else if (linkMicId.equals(it6.next().getUserId())) {
                            z2 = true;
                            break;
                        }
                    }
                    if (!z2) {
                        arrayList2.add(pLVLinkMicItemDataBean2.getLinkMicId());
                        it5.remove();
                    }
                }
                if (arrayList2.isEmpty()) {
                    return;
                }
                if (PLVLinkMicPresenter.this.linkMicView != null) {
                    PLVLinkMicPresenter.this.linkMicView.onUsersLeave(arrayList2);
                }
                if (!arrayList2.contains(PLVLinkMicPresenter.this.myLinkMicId) || PLVLinkMicPresenter.this.linkMicView == null) {
                    return;
                }
                PLVCommonLog.d(PLVLinkMicPresenter.TAG, "onNotInLinkMicList");
                PLVLinkMicPresenter.this.linkMicView.onNotInLinkMicList();
            }
        });
    }

    private void requestPermissionAndJoin() {
        PLVFastPermission.getInstance().start(ActivityUtils.getTopActivity(), new ArrayList(Arrays.asList(Permission.CAMERA, Permission.RECORD_AUDIO)), new PLVOnPermissionCallback() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.12
            @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
            public void onAllGranted() {
                PLVLinkMicPresenter.this.pendingActionInCaseLinkMicEngineInitializing(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.12.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PLVLinkMicPresenter.this.linkMicManager.sendJoinRequestMsg();
                    }
                });
            }

            @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
            public void onPartialGranted(ArrayList<String> grantedPermissions, ArrayList<String> deniedPermissions, ArrayList<String> deniedForeverP) {
                PLVLinkMicTraceLogSender pLVLinkMicTraceLogSender = new PLVLinkMicTraceLogSender();
                pLVLinkMicTraceLogSender.setLogModuleClass(PLVLinkMicELog.class);
                pLVLinkMicTraceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.PERMISSION_DENIED, " deniedPermissions: " + deniedPermissions + " deniedForeverP: " + deniedForeverP);
                if (deniedForeverP == null) {
                    PLVLinkMicPresenter.this.linkMicView.onLeaveLinkMic();
                } else {
                    PLVLinkMicPresenter.this.showRequestPermissionDialog();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showRequestPermissionDialog() {
        new AlertDialog.Builder(ActivityUtils.getTopActivity()).setTitle("提示").setMessage("通话所需的相机权限和麦克风权限被拒绝，请到应用设置的权限管理中恢复").setPositiveButton("确定", new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.14
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                PLVFastPermission.getInstance().jump2Settings(ActivityUtils.getTopActivity());
                PLVLinkMicPresenter.this.linkMicView.onLeaveLinkMic();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.13
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ActivityUtils.getTopActivity(), "权限不足，申请发言失败", 0).show();
                PLVLinkMicPresenter.this.linkMicView.onLeaveLinkMic();
            }
        }).setCancelable(false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startJoinTimeoutCount(final Runnable timeout) {
        Disposable disposable = this.linkJoinTimer;
        if (disposable != null) {
            disposable.dispose();
        }
        this.linkJoinTimer = PLVRxTimer.delay(SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US, new Consumer<Long>() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.11
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                timeout.run();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopJoinTimeoutCount() {
        Disposable disposable = this.linkJoinTimer;
        if (disposable != null) {
            disposable.dispose();
            this.linkJoinTimer = null;
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void cancelRequestJoinLinkMic() {
        this.linkMicManager.sendJoinLeaveMsg(this.liveRoomDataManager.getSessionId());
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public SurfaceView createRenderView(Context context) {
        return this.linkMicManager.createRendererView(context);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void destroy() {
        if (isJoinLinkMic()) {
            this.linkMicManager.sendJoinLeaveMsg(this.liveRoomDataManager.getSessionId());
        }
        leaveChannel();
        dispose(this.getLinkMicListDelay);
        dispose(this.getLinkMicListTimer);
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.messageListener);
        this.linkMicList.clear();
        this.muteCacheList.clear();
        this.myLinkMicId = "";
        this.linkMicView = null;
        this.linkMicInitState = 1;
        this.linkMicManager.destroy();
        PLVLinkMicMsgHandler pLVLinkMicMsgHandler = this.linkMicMsgHandler;
        if (pLVLinkMicMsgHandler != null) {
            pLVLinkMicMsgHandler.destroy();
        }
        PolyvLinkMicConfig.getInstance().clear();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public boolean getIsAudioLinkMic() {
        return this.isAudioLinkMic;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public String getLinkMicId() {
        return this.linkMicManager.getLinkMicUid();
    }

    @Nullable
    public IPLVLinkMicContract.IPLVLinkMicView getLinkMicView() {
        return this.linkMicView;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public String getMainTeacherLinkMicId() {
        return this.mainTeacherLinkMicId;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public int getRTCListSize() {
        return this.linkMicList.size();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public boolean isAloneChannelTypeSupportRTC() {
        return this.liveRoomDataManager.getConfig().isAloneChannelType() && this.liveRoomDataManager.isSupportRTC();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public boolean isJoinChannel() {
        IPLVRTCInvokeStrategy iPLVRTCInvokeStrategy = this.rtcInvokeStrategy;
        if (iPLVRTCInvokeStrategy != null) {
            return iPLVRTCInvokeStrategy.isJoinChannel();
        }
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public boolean isJoinLinkMic() {
        IPLVRTCInvokeStrategy iPLVRTCInvokeStrategy = this.rtcInvokeStrategy;
        if (iPLVRTCInvokeStrategy != null) {
            return iPLVRTCInvokeStrategy.isJoinLinkMic();
        }
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public boolean isTeacherOpenLinkMic() {
        return this.isTeacherOpenLinkMic;
    }

    public void leaveChannel() {
        IPLVRTCInvokeStrategy iPLVRTCInvokeStrategy = this.rtcInvokeStrategy;
        if (iPLVRTCInvokeStrategy == null || !iPLVRTCInvokeStrategy.isJoinChannel()) {
            return;
        }
        dispose(this.getLinkMicListTimer);
        cleanLinkMicListData();
        this.muteCacheList.clear();
        IPLVLinkMicContract.IPLVLinkMicView iPLVLinkMicView = this.linkMicView;
        if (iPLVLinkMicView != null) {
            iPLVLinkMicView.onLeaveRtcChannel();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void leaveLinkMic() {
        IPLVRTCInvokeStrategy iPLVRTCInvokeStrategy = this.rtcInvokeStrategy;
        if (iPLVRTCInvokeStrategy != null) {
            iPLVRTCInvokeStrategy.setLeaveLinkMic();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void muteAllAudio(boolean mute) {
        this.isMuteAllAudio = mute;
        this.linkMicManager.muteAllRemoteAudio(mute);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void muteAllVideo(boolean mute) {
        this.isMuteAllVideo = mute;
        this.linkMicManager.muteAllRemoteVideo(mute);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void muteAudio(boolean mute) {
        PLVLinkMicMedia pLVLinkMicMedia = new PLVLinkMicMedia();
        pLVLinkMicMedia.setType("audio");
        pLVLinkMicMedia.setMute(mute);
        this.linkMicManager.sendMuteEventMsg(pLVLinkMicMedia);
        this.linkMicManager.muteLocalAudio(mute);
        for (int i2 = 0; i2 < this.linkMicList.size(); i2++) {
            PLVLinkMicItemDataBean pLVLinkMicItemDataBean = this.linkMicList.get(i2);
            if (pLVLinkMicItemDataBean.getLinkMicId().equals(this.myLinkMicId)) {
                pLVLinkMicItemDataBean.setMuteAudio(mute);
                IPLVLinkMicContract.IPLVLinkMicView iPLVLinkMicView = this.linkMicView;
                if (iPLVLinkMicView != null) {
                    iPLVLinkMicView.onUserMuteAudio(this.myLinkMicId, mute, i2);
                    return;
                }
                return;
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void muteVideo(boolean mute) {
        PLVLinkMicMedia pLVLinkMicMedia = new PLVLinkMicMedia();
        pLVLinkMicMedia.setType("video");
        pLVLinkMicMedia.setMute(mute);
        this.linkMicManager.sendMuteEventMsg(pLVLinkMicMedia);
        this.linkMicManager.muteLocalVideo(mute);
        for (int i2 = 0; i2 < this.linkMicList.size(); i2++) {
            PLVLinkMicItemDataBean pLVLinkMicItemDataBean = this.linkMicList.get(i2);
            if (pLVLinkMicItemDataBean.getLinkMicId().equals(this.myLinkMicId)) {
                pLVLinkMicItemDataBean.setMuteVideo(mute);
                IPLVLinkMicContract.IPLVLinkMicView iPLVLinkMicView = this.linkMicView;
                if (iPLVLinkMicView != null) {
                    iPLVLinkMicView.onUserMuteVideo(this.myLinkMicId, mute, i2);
                    return;
                }
                return;
            }
        }
    }

    public void pendingActionInCaseLinkMicEngineInitializing(final Runnable action) {
        int i2 = this.linkMicInitState;
        if (i2 != 1) {
            if (i2 != 3) {
                return;
            }
            this.actionAfterLinkMicEngineCreated = null;
            action.run();
            return;
        }
        List<Runnable> list = this.actionAfterLinkMicEngineCreated;
        if (list != null) {
            list.add(action);
        } else {
            action.run();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void releaseRenderView(SurfaceView renderView) {
        this.linkMicManager.releaseRenderView(renderView);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void requestJoinLinkMic() {
        requestPermissionAndJoin();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void resetRequestPermissionList(ArrayList<String> permissions) {
        this.linkMicManager.resetRequestPermissionList(permissions);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void setIsAudioLinkMic(boolean isAudioLinkMic) {
        if ((System.currentTimeMillis() - this.socketRefreshOpenStatusData) / 1000 < 10) {
            return;
        }
        this.isAudioLinkMic = isAudioLinkMic;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void setIsTeacherOpenLinkMic(boolean isTeacherOpenLinkMic) {
        this.isTeacherOpenLinkMic = isTeacherOpenLinkMic;
        if (!isJoinLinkMic() || isTeacherOpenLinkMic) {
            return;
        }
        leaveLinkMic();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void setLiveEnd() {
        IPLVRTCInvokeStrategy iPLVRTCInvokeStrategy = this.rtcInvokeStrategy;
        if (iPLVRTCInvokeStrategy != null) {
            iPLVRTCInvokeStrategy.setLiveEnd();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void setLiveStart() {
        pendingActionInCaseLinkMicEngineInitializing(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVLinkMicPresenter.9
            @Override // java.lang.Runnable
            public void run() {
                if (PLVLinkMicPresenter.this.rtcInvokeStrategy != null) {
                    PLVLinkMicPresenter.this.rtcInvokeStrategy.setLiveStart();
                }
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void setPushPictureResolutionType(int type) {
        this.linkMicManager.setPushPictureResolutionType(type);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void setWatchRtc(boolean watchRtc) {
        if (this.isWatchRtc == watchRtc) {
            return;
        }
        setLiveEnd();
        IPLVRTCInvokeStrategy iPLVRTCInvokeStrategy = this.rtcInvokeStrategy;
        if (iPLVRTCInvokeStrategy != null) {
            iPLVRTCInvokeStrategy.destroy();
        }
        this.isWatchRtc = watchRtc;
        initRTCInvokeStrategy();
        setLiveStart();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void setupRenderView(SurfaceView renderView, String linkMicId) {
        if (this.linkMicManager.getLinkMicUid().equals(linkMicId)) {
            if (this.liveRoomDataManager.isOnlyAudio()) {
                this.linkMicManager.setupLocalVideo(renderView, 10);
            }
            this.linkMicManager.setupLocalVideo(renderView, linkMicId);
        } else {
            this.linkMicManager.setupRemoteVideo(renderView, linkMicId);
            if (this.isMuteAllAudio) {
                this.linkMicManager.muteRemoteAudio(linkMicId, true);
            }
            if (this.isMuteAllVideo) {
                this.linkMicManager.muteRemoteVideo(linkMicId, true);
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void switchCamera() {
        this.linkMicManager.switchCamera();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void muteAudio(String linkMicId, boolean mute) {
        String str = this.myLinkMicId;
        if (str != null && str.equals(linkMicId)) {
            muteAudio(mute);
        } else {
            this.linkMicManager.muteRemoteAudio(linkMicId, mute);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicPresenter
    public void muteVideo(String linkMicId, boolean mute) {
        String str = this.myLinkMicId;
        if (str != null && str.equals(linkMicId)) {
            muteVideo(mute);
        } else {
            this.linkMicManager.muteRemoteVideo(linkMicId, mute);
        }
    }
}
