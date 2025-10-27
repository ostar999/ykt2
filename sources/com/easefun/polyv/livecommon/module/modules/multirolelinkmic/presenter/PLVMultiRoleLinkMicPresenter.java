package com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract;
import com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor;
import com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList;
import com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList;
import com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.data.PLVMultiRoleLinkMicData;
import com.easefun.polyv.livecommon.module.modules.streamer.model.PLVMemberItemDataBean;
import com.hjq.permissions.Permission;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.linkmic.model.PLVLinkMicJoinStatus;
import com.plv.linkmic.model.PLVNetworkStatusVO;
import com.plv.linkmic.repository.PLVLinkMicDataRepository;
import com.plv.livescenes.access.PLVUserAbility;
import com.plv.livescenes.access.PLVUserAbilityManager;
import com.plv.livescenes.document.event.PLVSwitchRoomEvent;
import com.plv.livescenes.hiclass.IPLVHiClassManager;
import com.plv.livescenes.hiclass.PLVHiClassManagerFactory;
import com.plv.livescenes.hiclass.api.PLVHCApiManager;
import com.plv.livescenes.hiclass.vo.PLVHCStudentLessonListVO;
import com.plv.livescenes.linkmic.IPLVLinkMicManager;
import com.plv.livescenes.linkmic.listener.PLVLinkMicEventListener;
import com.plv.livescenes.linkmic.listener.PLVLinkMicListener;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManagerFactory;
import com.plv.livescenes.linkmic.vo.PLVLinkMicEngineParam;
import com.plv.livescenes.net.IPLVDataRequestListener;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender;
import com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender;
import com.plv.socket.event.linkmic.PLVJoinResponseAckResult;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import com.plv.socket.event.login.PLVLoginEvent;
import com.plv.socket.event.ppt.PLVOnSliceStartEvent;
import com.plv.socket.user.PLVClassStatusBean;
import com.plv.socket.user.PLVSocketUserBean;
import com.plv.socket.user.PLVSocketUserConstant;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.socket.client.Ack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class PLVMultiRoleLinkMicPresenter implements IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter {
    private static final long CAN_SWITCH_CAMERA_AFTER_INIT = 1200;
    private static final long JOIN_DISCUSS_COUNTDOWN_TIME = 3000;
    private static final String TAG = "PLVMultiRoleLinkMicPresenter";
    private PLVMultiRoleEventProcessor eventProcessor;
    private Disposable getGroupNameDisposable;
    private Disposable getMemberListLessDisposable;
    private String groupId;
    private String groupName;
    private IPLVHiClassManager hiClassManager;
    private List<IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView> iMultiRoleLinkMicViews;
    private long initEngineSuccessTimestamp;
    private boolean isCallJoinDiscuss;
    private boolean isGroupChanged;
    private boolean isLeaderChanged;
    private boolean isTeacherType;
    private PLVMultiRoleLinkMicData linkMicData;
    private PLVMultiRoleLinkMicList linkMicList;
    private IPLVLinkMicManager linkMicManager;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private PLVMultiRoleMemberList memberList;
    private Runnable switchCameraTask;
    private String myLinkMicId = "";
    private int linkMicInitState = 1;
    private List<Runnable> initiatedTasks = new ArrayList();
    private boolean curEnableLocalAudio = true;
    private boolean curEnableLocalVideo = true;
    private boolean curCameraFront = true;
    private int joinChannelStatus = 1;
    private boolean isInClassStatus = false;
    private List<String> autoLinkResponseList = new ArrayList();
    private boolean isFrontPreviewMirror = false;
    private int currentBitrate = 1;
    private Handler handler = new Handler(Looper.getMainLooper());

    public class OnEventProcessorListenerImpl implements PLVMultiRoleEventProcessor.OnEventProcessorListener {
        private OnEventProcessorListenerImpl() {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onAcceptMyJoinLeave(boolean isByTeacherControl) {
            if (isByTeacherControl && PLVMultiRoleLinkMicPresenter.this.isInClassStatus) {
                PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnEventProcessorListenerImpl.1
                    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                    public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                        view.onTeacherControlMyLinkMic(false);
                    }
                });
            }
            PLVMultiRoleLinkMicPresenter.this.isInClassStatus = false;
            PLVMultiRoleLinkMicPresenter.this.switchRoleToAudience();
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onChangeLinkMicZoom(@Nullable final Map<String, PLVUpdateMicSiteEvent> updateMicSiteEventMap) {
            PLVMultiRoleLinkMicPresenter.this.onMyMicSiteChanged((PLVMultiRoleLinkMicPresenter.this.myLinkMicId == null || updateMicSiteEventMap == null || !updateMicSiteEventMap.containsKey(PLVMultiRoleLinkMicPresenter.this.myLinkMicId)) ? false : true);
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnEventProcessorListenerImpl.14
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onChangeLinkMicZoom(updateMicSiteEventMap);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onJoinChannelSuccess() {
            PLVMultiRoleLinkMicPresenter.this.joinChannelStatus = 3;
            if (!PLVMultiRoleLinkMicPresenter.this.isInClassStatus) {
                PLVMultiRoleLinkMicPresenter.this.switchRoleToAudience();
                return;
            }
            if (!PLVMultiRoleLinkMicPresenter.this.isJoinDiscuss()) {
                PLVMultiRoleLinkMicPresenter.this.switchRoleToBroadcaster();
            } else if (PLVMultiRoleLinkMicPresenter.this.isCallJoinDiscuss) {
                PLVMultiRoleLinkMicPresenter.this.switchRoleToBroadcaster();
                PLVMultiRoleLinkMicPresenter.this.linkMicList.addMyItemToLinkMicList(PLVMultiRoleLinkMicPresenter.this.curEnableLocalVideo, PLVMultiRoleLinkMicPresenter.this.curEnableLocalAudio);
            }
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onJoinDiscuss(final String groupId, boolean isInClass, @Nullable String leaderId, PLVSwitchRoomEvent switchRoomEvent) {
            PLVMultiRoleLinkMicPresenter.this.acceptJoinDiscuss(groupId, isInClass, leaderId, switchRoomEvent);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onLeaderCancelHelp() {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnEventProcessorListenerImpl.11
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onLeaderCancelHelp();
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onLeaderRequestHelp() {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnEventProcessorListenerImpl.10
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onLeaderRequestHelp();
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onLeaveChannel() {
            PLVMultiRoleLinkMicPresenter.this.joinChannelStatus = 1;
            PLVMultiRoleLinkMicPresenter.this.isInClassStatus = false;
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onLeaveDiscuss(PLVSwitchRoomEvent switchRoomEvent) {
            PLVMultiRoleLinkMicPresenter.this.acceptLeaveDiscuss(switchRoomEvent);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onNetworkQuality(final int quality) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnEventProcessorListenerImpl.4
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onNetworkQuality(quality);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onRemoteNetworkStatus(final PLVNetworkStatusVO networkStatusVO) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnEventProcessorListenerImpl.6
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onRemoteNetworkStatus(networkStatusVO);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onRemoveLinkMicZoom(final PLVRemoveMicSiteEvent removeMicSiteEvent) {
            if (PLVMultiRoleLinkMicPresenter.this.myLinkMicId != null && PLVMultiRoleLinkMicPresenter.this.myLinkMicId.equals(removeMicSiteEvent.getLinkMicIdFromEventId())) {
                PLVMultiRoleLinkMicPresenter.this.onMyMicSiteChanged(false);
            }
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnEventProcessorListenerImpl.13
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onRemoveLinkMicZoom(removeMicSiteEvent);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onResponseJoin(final boolean isNeedAnswer) {
            PLVMultiRoleLinkMicPresenter.this.acceptInitiatedTask(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnEventProcessorListenerImpl.3
                @Override // java.lang.Runnable
                public void run() {
                    if (PLVMultiRoleLinkMicPresenter.this.isInClassStatus) {
                        return;
                    }
                    if (!isNeedAnswer) {
                        PLVMultiRoleLinkMicPresenter.this.acceptResponseJoin(false);
                        return;
                    }
                    final boolean[] zArr = {false};
                    PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnEventProcessorListenerImpl.3.1
                        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                        public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                            boolean[] zArr2 = zArr;
                            if (zArr2[0]) {
                                return;
                            }
                            zArr2[0] = view.onUserNeedAnswerLinkMic();
                        }
                    });
                    if (zArr[0]) {
                        return;
                    }
                    PLVMultiRoleLinkMicPresenter.this.acceptResponseJoin(true);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onResponseJoinForDiscuss() {
            if (!PLVMultiRoleLinkMicPresenter.this.isJoinDiscuss() || PLVMultiRoleLinkMicPresenter.this.isInClassStatus) {
                return;
            }
            PLVMultiRoleLinkMicPresenter.this.isInClassStatus = true;
            if (PLVMultiRoleLinkMicPresenter.this.isCallJoinDiscuss) {
                PLVMultiRoleLinkMicPresenter.this.switchRoleToBroadcaster();
                PLVMultiRoleLinkMicPresenter.this.linkMicList.addMyItemToLinkMicList(PLVMultiRoleLinkMicPresenter.this.curEnableLocalVideo, PLVMultiRoleLinkMicPresenter.this.curEnableLocalAudio);
            }
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onSliceStart(PLVOnSliceStartEvent onSliceStartEvent) {
            PLVMultiRoleLinkMicPresenter.this.acceptOnSliceStart();
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onTeacherInfo(final String nick) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnEventProcessorListenerImpl.7
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onTeacherInfo(nick);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onTeacherJoinDiscuss(final boolean isJoin) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnEventProcessorListenerImpl.8
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onTeacherJoinDiscuss(isJoin);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onTeacherMuteMyMedia(final boolean isVideoType, final boolean isMute) {
            if (isVideoType) {
                PLVMultiRoleLinkMicPresenter.this.muteVideo(isMute);
            } else {
                PLVMultiRoleLinkMicPresenter.this.muteAudio(isMute);
            }
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnEventProcessorListenerImpl.2
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onTeacherMuteMyMedia(isVideoType, isMute);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onTeacherSendBroadcast(final String content) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnEventProcessorListenerImpl.9
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onTeacherSendBroadcast(content);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onUpdateLinkMicZoom(final PLVUpdateMicSiteEvent updateMicSiteEvent) {
            if (PLVMultiRoleLinkMicPresenter.this.myLinkMicId != null && PLVMultiRoleLinkMicPresenter.this.myLinkMicId.equals(updateMicSiteEvent.getLinkMicIdFromEventId())) {
                PLVMultiRoleLinkMicPresenter.this.onMyMicSiteChanged(true);
            }
            if (PLVUserAbilityManager.myAbility().hasAbility(PLVUserAbility.HI_CLASS_ZOOM_NEED_REACT_UPDATE_EVENT)) {
                PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnEventProcessorListenerImpl.12
                    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                    public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                        view.onUpdateLinkMicZoom(updateMicSiteEvent);
                    }
                });
            }
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onUpstreamNetworkStatus(final PLVNetworkStatusVO networkStatusVO) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnEventProcessorListenerImpl.5
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onUpstreamNetworkStatus(networkStatusVO);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleEventProcessor.OnEventProcessorListener
        public void onUserLogin(PLVLoginEvent loginEvent) {
            PLVMultiRoleLinkMicPresenter.this.acceptUserLogin(loginEvent);
        }
    }

    public class OnHiClassListenerImpl implements IPLVHiClassManager.OnHiClassListener {
        private OnHiClassListenerImpl() {
        }

        @Override // com.plv.livescenes.hiclass.IPLVHiClassManager.OnHiClassListener
        public void onLessonEnd(final long inClassTime, final boolean isFromApi, @Nullable final PLVHCStudentLessonListVO.DataVO dataVO) {
            PLVMultiRoleLinkMicPresenter.this.isInClassStatus = false;
            PLVMultiRoleLinkMicPresenter.this.switchRoleToAudience();
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnHiClassListenerImpl.3
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onLessonEnd(inClassTime, isFromApi, dataVO);
                }
            });
            if (PLVMultiRoleLinkMicPresenter.this.isTeacherType) {
                return;
            }
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnHiClassListenerImpl.4
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onUserHasPaint(true, false, -1, -1);
                }
            });
        }

        @Override // com.plv.livescenes.hiclass.IPLVHiClassManager.OnHiClassListener
        public void onLessonLateTooLong(final long willAutoStopLessonTimeMs) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnHiClassListenerImpl.5
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onLessonLateTooLong(willAutoStopLessonTimeMs);
                }
            });
        }

        @Override // com.plv.livescenes.hiclass.IPLVHiClassManager.OnHiClassListener
        public void onLessonPreparing(final long serverTime, final long lessonStartTime) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnHiClassListenerImpl.1
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onLessonPreparing(serverTime, lessonStartTime);
                }
            });
        }

        @Override // com.plv.livescenes.hiclass.IPLVHiClassManager.OnHiClassListener
        public void onLessonStarted(boolean isFirstStart) {
            PLVMultiRoleLinkMicPresenter.this.autoLinkResponseList.clear();
            if (PLVMultiRoleLinkMicPresenter.this.isTeacherType) {
                PLVMultiRoleLinkMicPresenter.this.isInClassStatus = true;
                PLVMultiRoleLinkMicPresenter.this.switchRoleToBroadcaster();
            }
            if (isFirstStart) {
                PLVMultiRoleLinkMicPresenter.this.joinChannel();
                PLVMultiRoleLinkMicPresenter.this.requestMemberList();
            }
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnHiClassListenerImpl.2
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onLessonStarted();
                }
            });
        }

        @Override // com.plv.livescenes.hiclass.IPLVHiClassManager.OnHiClassListener
        public void onLimitLinkNumber(int limitLinkNumber) {
            PLVMultiRoleLinkMicPresenter.this.linkMicData.postLimitLinkNumber(limitLinkNumber);
        }

        @Override // com.plv.livescenes.hiclass.IPLVHiClassManager.OnHiClassListener
        public void onRepeatLogin(final String desc) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnHiClassListenerImpl.6
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onRepeatLogin(desc);
                }
            });
        }
    }

    public class OnLinkMicListListenerImpl extends PLVMultiRoleLinkMicList.AbsOnLinkMicListListener {
        private OnLinkMicListListenerImpl() {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.AbsOnLinkMicListListener, com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onGetLinkMicListStatus(String sessionId, PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicJoinStatus> callback) {
            PLVMultiRoleLinkMicPresenter.this.linkMicManager.getLinkStatus(sessionId, callback);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.AbsOnLinkMicListListener, com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onLinkMicItemInsert(final PLVLinkMicItemDataBean linkMicItemDataBean, final int position) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnLinkMicListListenerImpl.5
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onUsersJoin(linkMicItemDataBean, position);
                }
            });
            if (!PLVMultiRoleLinkMicPresenter.this.isMyLinkMicId(linkMicItemDataBean.getLinkMicId()) || PLVMultiRoleLinkMicPresenter.this.switchCameraTask == null) {
                return;
            }
            PLVMultiRoleLinkMicPresenter.this.handler.postDelayed(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnLinkMicListListenerImpl.6
                @Override // java.lang.Runnable
                public void run() {
                    if (PLVMultiRoleLinkMicPresenter.this.switchCameraTask != null) {
                        PLVMultiRoleLinkMicPresenter.this.switchCameraTask.run();
                        PLVMultiRoleLinkMicPresenter.this.switchCameraTask = null;
                    }
                }
            }, Math.max(0L, PLVMultiRoleLinkMicPresenter.CAN_SWITCH_CAMERA_AFTER_INIT - (System.currentTimeMillis() - PLVMultiRoleLinkMicPresenter.this.initEngineSuccessTimestamp)));
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.AbsOnLinkMicListListener, com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onLinkMicItemRemove(final PLVLinkMicItemDataBean linkMicItemDataBean, final int position) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnLinkMicListListenerImpl.2
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onUsersLeave(linkMicItemDataBean, position);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.AbsOnLinkMicListListener, com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onLinkMicListChanged(final List<PLVLinkMicItemDataBean> dataBeanList) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnLinkMicListListenerImpl.1
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onLinkMicListChanged(dataBeanList);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.AbsOnLinkMicListListener, com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onLinkMicUserExisted(final PLVLinkMicItemDataBean linkMicItemDataBean, final int position) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnLinkMicListListenerImpl.3
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onUserExisted(linkMicItemDataBean, position);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.AbsOnLinkMicListListener, com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onTeacherScreenStream(final PLVLinkMicItemDataBean linkMicItemDataBean, final boolean isOpen) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnLinkMicListListenerImpl.4
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onTeacherScreenStream(linkMicItemDataBean, isOpen);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.AbsOnLinkMicListListener, com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onUserGetCup(final String userNick, final boolean isByEvent, final int linkMicListPos, final int memberListPos) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnLinkMicListListenerImpl.7
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onUserGetCup(userNick, isByEvent, linkMicListPos, memberListPos);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.AbsOnLinkMicListListener, com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
        public void onUserHasPaint(final boolean isMyself, final boolean isHasPaint, final int linkMicListPos, final int memberListPos) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnLinkMicListListenerImpl.8
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onUserHasPaint(isMyself, isHasPaint, linkMicListPos, memberListPos);
                }
            });
        }
    }

    public class OnMemberListListenerImpl implements PLVMultiRoleMemberList.OnMemberListListener {
        private OnMemberListListenerImpl() {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.OnMemberListListener
        public void onLeaveDiscuss() {
            PLVMultiRoleLinkMicPresenter.this.acceptLeaveDiscuss(null);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.OnMemberListListener
        public void onLocalUserVolumeChanged(final int volume) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnMemberListListenerImpl.5
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onLocalUserVolumeChanged(volume);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.OnMemberListListener
        public void onMemberItemChanged(final int pos) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnMemberListListenerImpl.2
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onMemberItemChanged(pos);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.OnMemberListListener
        public void onMemberItemInsert(final int pos) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnMemberListListenerImpl.4
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onMemberItemInsert(pos);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.OnMemberListListener
        public void onMemberItemRemove(final int pos) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnMemberListListenerImpl.3
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onMemberItemRemove(pos);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.OnMemberListListener
        public void onMemberListChanged(final List<PLVMemberItemDataBean> dataBeans) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnMemberListListenerImpl.1
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onMemberListChanged(dataBeans);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.OnMemberListListener
        public void onRemoteUserVolumeChanged() {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnMemberListListenerImpl.6
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onRemoteUserVolumeChanged();
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.OnMemberListListener
        public void onUserGetCup(final String userNick, final boolean isByEvent, final int linkMicListPos, final int memberListPos) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnMemberListListenerImpl.10
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onUserGetCup(userNick, isByEvent, linkMicListPos, memberListPos);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.OnMemberListListener
        public void onUserHasGroupLeader(final boolean isHasGroupLeader, final String nick, @Nullable final String leaderId) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnMemberListListenerImpl.12
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onUserHasGroupLeader(isHasGroupLeader, nick, PLVMultiRoleLinkMicPresenter.this.isGroupChanged, PLVMultiRoleLinkMicPresenter.this.isLeaderChanged, PLVMultiRoleLinkMicPresenter.this.groupName, leaderId);
                }
            });
            PLVMultiRoleLinkMicPresenter.this.isLeaderChanged = true;
            PLVMultiRoleLinkMicPresenter.this.linkMicList.notifyLeaderChanged(leaderId);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.OnMemberListListener
        public void onUserHasPaint(final boolean isMyself, final boolean isHasPaint, final int linkMicListPos, final int memberListPos) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnMemberListListenerImpl.11
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onUserHasPaint(isMyself, isHasPaint, linkMicListPos, memberListPos);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.OnMemberListListener
        public void onUserMuteAudio(final String uid, final boolean mute, final int linkMicListPos, final int memberListPos) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnMemberListListenerImpl.8
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onUserMuteAudio(uid, mute, linkMicListPos, memberListPos);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.OnMemberListListener
        public void onUserMuteVideo(final String uid, final boolean mute, final int linkMicListPos, final int memberListPos) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnMemberListListenerImpl.7
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onUserMuteVideo(uid, mute, linkMicListPos, memberListPos);
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.OnMemberListListener
        public void onUserRaiseHand(final int raiseHandCount, final boolean isRaiseHand, final int linkMicListPos, final int memberListPos) {
            PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.OnMemberListListenerImpl.9
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onUserRaiseHand(raiseHandCount, isRaiseHand, linkMicListPos, memberListPos);
                }
            });
        }
    }

    public interface ViewRunnable {
        void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view);
    }

    public PLVMultiRoleLinkMicPresenter(IPLVLiveRoomDataManager liveRoomDataManager) {
        this.liveRoomDataManager = liveRoomDataManager;
        String viewerType = liveRoomDataManager.getConfig().getUser().getViewerType();
        this.isTeacherType = "teacher".equals(viewerType);
        PLVLinkMicConfig.getInstance().init(liveRoomDataManager.getConfig().getUser().getViewerId(), false);
        this.linkMicManager = PLVLinkMicManagerFactory.createNewLinkMicManager();
        this.linkMicData = new PLVMultiRoleLinkMicData();
        initLinkMicList();
        initMemberList(this.linkMicList);
        initEventProcessor();
        initHiClassManager(viewerType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptInitiatedTask(Runnable runnable) {
        int i2 = this.linkMicInitState;
        if (i2 == 1) {
            PLVCommonLog.d(TAG, "连麦开始初始化");
            this.initiatedTasks.add(runnable);
            init();
        } else if (i2 == 2) {
            PLVCommonLog.d(TAG, "连麦初始化中");
            this.initiatedTasks.add(runnable);
        } else {
            if (i2 != 3) {
                return;
            }
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptJoinDiscuss(final String groupId, boolean isInClass, final String leaderId, final PLVSwitchRoomEvent switchRoomEvent) {
        String str = this.groupId;
        if (str != null && str.equals(groupId)) {
            if (isMyLinkMicId(leaderId)) {
                callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.11
                    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                    public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                        view.onUserHasGroupLeader(true, PLVMultiRoleLinkMicPresenter.this.liveRoomDataManager.getConfig().getUser().getViewerName(), false, true, PLVMultiRoleLinkMicPresenter.this.groupName, leaderId);
                    }
                });
                return;
            }
            return;
        }
        boolean zIsJoinDiscuss = isJoinDiscuss();
        this.isGroupChanged = zIsJoinDiscuss;
        this.groupId = groupId;
        if (!zIsJoinDiscuss) {
            this.linkMicList.removeMyItemToLinkMicList();
            switchRoleToAudience();
            callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.12
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onWillJoinDiscuss(3000L);
                }
            });
        }
        this.isLeaderChanged = false;
        this.isCallJoinDiscuss = false;
        this.isInClassStatus = isInClass;
        this.handler.postDelayed(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.13
            @Override // java.lang.Runnable
            public void run() {
                PLVMultiRoleLinkMicPresenter pLVMultiRoleLinkMicPresenter = PLVMultiRoleLinkMicPresenter.this;
                pLVMultiRoleLinkMicPresenter.dispose(pLVMultiRoleLinkMicPresenter.getGroupNameDisposable);
                PLVMultiRoleLinkMicPresenter.this.getGroupNameDisposable = PLVHCApiManager.getInstance().getGroupName(groupId).subscribe(new Consumer<String>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.13.1
                    @Override // io.reactivex.functions.Consumer
                    public void accept(final String groupName) throws Exception {
                        AnonymousClass13 anonymousClass13 = AnonymousClass13.this;
                        PLVMultiRoleLinkMicPresenter.this.callJoinDiscuss(groupName, leaderId, switchRoomEvent);
                    }
                }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.13.2
                    @Override // io.reactivex.functions.Consumer
                    public void accept(Throwable throwable) throws Exception {
                        AnonymousClass13 anonymousClass13 = AnonymousClass13.this;
                        PLVMultiRoleLinkMicPresenter.this.callJoinDiscuss("", leaderId, switchRoomEvent);
                        PLVCommonLog.exception(throwable);
                    }
                });
            }
        }, this.isGroupChanged ? 0L : 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptLeaveDiscuss(final PLVSwitchRoomEvent switchRoomEvent) {
        if (this.groupId == null) {
            return;
        }
        this.groupId = null;
        callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.15
            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
            public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                view.onLeaveDiscuss(switchRoomEvent);
            }
        });
        this.isInClassStatus = false;
        handleDiscussChanged(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptOnSliceStart() {
        PLVSocketWrapper.getInstance().sendLoginEvent(new IPLVLinkMicEventSender.PLVSMainCallAck() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.9
            @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.PLVSMainCallAck
            public void onCall(Object... args) {
                if (PLVMultiRoleLinkMicPresenter.this.isTeacherType) {
                    PLVMultiRoleLinkMicPresenter.this.requestMemberListLess();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptResponseJoin(boolean isNeedSendAnswerEvent) {
        if (this.isInClassStatus) {
            return;
        }
        if (isNeedSendAnswerEvent) {
            PLVLinkMicEventSender.getInstance().sendJoinAnswerEvent();
        }
        this.isInClassStatus = true;
        switchRoleToBroadcaster();
        joinChannel();
        this.linkMicList.addMyItemToLinkMicList(this.curEnableLocalVideo, this.curEnableLocalAudio);
        callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.16
            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
            public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                view.onTeacherControlMyLinkMic(true);
            }
        });
    }

    private void acceptSwitchCameraTask(Runnable runnable) {
        if (this.linkMicList.getLinkMicItemWithLinkMicId(this.myLinkMicId) == null) {
            this.switchCameraTask = runnable;
        } else {
            this.switchCameraTask = null;
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptUserLogin(PLVLoginEvent loginEvent) {
        if (!this.isTeacherType || loginEvent == null || loginEvent.getUser() == null) {
            return;
        }
        PLVSocketUserBean user = loginEvent.getUser();
        if (isMySocketUserId(user.getUserId())) {
            return;
        }
        if (this.hiClassManager.isAutoConnectEnabledWithTimeRange()) {
            if (PLVSocketUserConstant.USERTYPE_SCSTUDENT.equals(user.getUserType())) {
                PLVLinkMicEventSender.getInstance().responseUserLinkMic(user, false, null);
                this.autoLinkResponseList.add(user.getUserId());
                return;
            }
            return;
        }
        PLVClassStatusBean classStatus = loginEvent.getClassStatus();
        if (classStatus == null || !classStatus.isVoice()) {
            return;
        }
        PLVLinkMicEventSender.getInstance().responseUserLinkMic(user, false, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callJoinDiscuss(final String groupName, String leaderId, final PLVSwitchRoomEvent switchRoomEvent) {
        callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.14
            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
            public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                view.onJoinDiscuss(PLVMultiRoleLinkMicPresenter.this.groupId, groupName, switchRoomEvent);
            }
        });
        this.groupName = groupName;
        this.isCallJoinDiscuss = true;
        handleDiscussChanged(leaderId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackToView(ViewRunnable runnable) {
        List<IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView> list = this.iMultiRoleLinkMicViews;
        if (list != null) {
            for (IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView iMultiRoleLinkMicView : list) {
                if (iMultiRoleLinkMicView != null && runnable != null) {
                    runnable.run(iMultiRoleLinkMicView);
                }
            }
        }
    }

    private boolean checkSelMediaPermission() {
        return ActivityUtils.getTopActivity() != null && ContextCompat.checkSelfPermission(ActivityUtils.getTopActivity(), Permission.CAMERA) == 0 && ContextCompat.checkSelfPermission(ActivityUtils.getTopActivity(), Permission.RECORD_AUDIO) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private String getRoomIdCombineDiscuss() {
        return isJoinDiscuss() ? this.groupId : this.liveRoomDataManager.getConfig().getChannelId();
    }

    private void handleDiscussChanged(String leaderId) {
        this.linkMicInitState = 1;
        this.joinChannelStatus = 1;
        this.initiatedTasks.clear();
        this.autoLinkResponseList.clear();
        this.handler.removeCallbacksAndMessages(null);
        dispose(this.getMemberListLessDisposable);
        dispose(this.getGroupNameDisposable);
        this.memberList.destroy();
        this.linkMicList.destroy();
        initLinkMicList();
        initMemberList(this.linkMicList);
        this.memberList.setGroupId(this.groupId);
        this.memberList.setLeaderId(leaderId);
        this.linkMicManager.destroy();
        this.linkMicManager = PLVLinkMicManagerFactory.createNewLinkMicManager();
        init();
        joinChannel();
        requestMemberList();
        boolean z2 = this.curCameraFront;
        this.curCameraFront = true;
        switchCamera(z2);
    }

    private void initEventProcessor() {
        PLVMultiRoleEventProcessor pLVMultiRoleEventProcessor = new PLVMultiRoleEventProcessor(this.liveRoomDataManager);
        this.eventProcessor = pLVMultiRoleEventProcessor;
        pLVMultiRoleEventProcessor.setOnEventProcessorListener(new OnEventProcessorListenerImpl());
    }

    private void initHiClassManager(String userType) {
        IPLVHiClassManager iPLVHiClassManagerCreateHiClassManager = PLVHiClassManagerFactory.createHiClassManager(this.liveRoomDataManager.getHiClassDataBean(), userType);
        this.hiClassManager = iPLVHiClassManagerCreateHiClassManager;
        iPLVHiClassManagerCreateHiClassManager.setOnHiClassListener(new OnHiClassListenerImpl());
    }

    private void initLinkMicList() {
        PLVMultiRoleLinkMicList pLVMultiRoleLinkMicList = new PLVMultiRoleLinkMicList(this.liveRoomDataManager);
        this.linkMicList = pLVMultiRoleLinkMicList;
        pLVMultiRoleLinkMicList.addOnLinkMicListListener(new OnLinkMicListListenerImpl());
    }

    private void initMemberList(PLVMultiRoleLinkMicList linkMicList) {
        PLVMultiRoleMemberList pLVMultiRoleMemberList = new PLVMultiRoleMemberList(this.liveRoomDataManager);
        this.memberList = pLVMultiRoleMemberList;
        pLVMultiRoleMemberList.setLinkMicList(linkMicList);
        this.memberList.setOnMemberListListener(new OnMemberListListenerImpl());
    }

    private boolean isMySocketUserId(String userId) {
        return userId != null && userId.equals(this.liveRoomDataManager.getConfig().getUser().getViewerId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void observeRTCEvent(IPLVLinkMicManager linkMicManager) {
        this.memberList.observeRTCEvent(linkMicManager);
        this.linkMicList.observeRTCEvent(linkMicManager);
        this.eventProcessor.observeRTCEvent(linkMicManager);
        registerOnRejoinRoomListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMyMicSiteChanged(boolean zoomIn) {
        Integer num = (Integer) PLVSugarUtil.nullable(new PLVSugarUtil.Supplier<Integer>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.17
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Supplier
            public Integer get() {
                return PLVMultiRoleLinkMicPresenter.this.liveRoomDataManager.getHiClassDataBean().getValue().getRtcMaxResolution();
            }
        });
        if (num == null || num.intValue() == 0) {
            return;
        }
        int iMapFromServerResolution = zoomIn ? PLVLinkMicConstant.Bitrate.mapFromServerResolution(num.intValue()) : 1;
        if (iMapFromServerResolution != this.currentBitrate) {
            this.linkMicManager.setBitrate(iMapFromServerResolution);
            this.currentBitrate = iMapFromServerResolution;
        }
    }

    private void registerOnRejoinRoomListener() {
        IPLVLinkMicManager iPLVLinkMicManager = this.linkMicManager;
        if (iPLVLinkMicManager == null) {
            return;
        }
        iPLVLinkMicManager.addEventHandler(new PLVLinkMicEventListener() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.18
            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onRejoinChannelSuccess(String channel, String uid) {
                PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.18.1
                    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                    public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                        view.onRejoinRoomSuccess();
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestMemberListLess() {
        dispose(this.getMemberListLessDisposable);
        this.getMemberListLessDisposable = this.memberList.requestMemberListLess(new Consumer<List<PLVSocketUserBean>>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.10
            @Override // io.reactivex.functions.Consumer
            public void accept(List<PLVSocketUserBean> userBeans) throws Exception {
                if (PLVMultiRoleLinkMicPresenter.this.hiClassManager.isAutoConnectEnabledWithTimeRange()) {
                    for (PLVSocketUserBean pLVSocketUserBean : userBeans) {
                        if (!PLVMultiRoleLinkMicPresenter.this.autoLinkResponseList.contains(pLVSocketUserBean.getUserId()) && !PLVMultiRoleLinkMicPresenter.this.isMyLinkMicId(pLVSocketUserBean.getUserId()) && PLVSocketUserConstant.USERTYPE_SCSTUDENT.equals(pLVSocketUserBean.getUserType())) {
                            PLVLinkMicEventSender.getInstance().responseUserLinkMic(pLVSocketUserBean, false, null);
                        }
                    }
                }
            }
        });
    }

    private void setMyLinkMicId(String linkMicId) {
        this.memberList.setMyLinkMicId(linkMicId);
        this.linkMicList.setMyLinkMicId(linkMicId);
        this.eventProcessor.setMyLinkMicId(linkMicId);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void answerLinkMicInvitation() {
        acceptResponseJoin(true);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void closeAllUserLinkMic() {
        PLVLinkMicEventSender.getInstance().closeAllUserLinkMic(this.liveRoomDataManager.getSessionId(), null);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void controlUserLinkMic(int memberListPos, boolean isAllowJoin) {
        PLVMemberItemDataBean item = this.memberList.getItem(memberListPos);
        if (item == null) {
            return;
        }
        PLVSocketUserBean socketUserBean = item.getSocketUserBean();
        final PLVLinkMicItemDataBean linkMicItemDataBean = item.getLinkMicItemDataBean();
        if (isAllowJoin) {
            PLVLinkMicEventSender.getInstance().responseUserLinkMic(socketUserBean, true, new IPLVLinkMicEventSender.PLVSMainCallAck() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.8
                @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.PLVSMainCallAck
                public void onCall(Object... args) {
                    Object obj;
                    PLVJoinResponseAckResult pLVJoinResponseAckResult;
                    if (args == null || args.length == 0 || (obj = args[0]) == null || (pLVJoinResponseAckResult = (PLVJoinResponseAckResult) PLVGsonUtil.fromJson(PLVJoinResponseAckResult.class, obj.toString())) == null || pLVJoinResponseAckResult.isStatus()) {
                        PLVMultiRoleLinkMicPresenter.this.memberList.updateUserJoining(linkMicItemDataBean);
                    } else {
                        PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.8.1
                            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                            public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                                view.onReachTheInteractNumLimit();
                            }
                        });
                    }
                }
            });
        } else if (linkMicItemDataBean != null) {
            PLVLinkMicEventSender.getInstance().closeUserLinkMic(linkMicItemDataBean.getLinkMicId(), null);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public View createRenderView(Context context) {
        return this.linkMicManager.createTextureRenderView(context);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void destroy() {
        this.linkMicInitState = 1;
        this.joinChannelStatus = 1;
        this.isInClassStatus = false;
        this.initiatedTasks.clear();
        this.autoLinkResponseList.clear();
        List<IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView> list = this.iMultiRoleLinkMicViews;
        if (list != null) {
            list.clear();
        }
        this.handler.removeCallbacksAndMessages(null);
        dispose(this.getMemberListLessDisposable);
        dispose(this.getGroupNameDisposable);
        this.eventProcessor.destroy();
        this.linkMicList.destroy();
        this.memberList.destroy();
        this.hiClassManager.destroy();
        this.linkMicManager.destroy();
        PLVLinkMicConfig.getInstance().clear();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    @NonNull
    public PLVMultiRoleLinkMicData getData() {
        return this.linkMicData;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public int getLessonStatus() {
        return this.hiClassManager.getLessonStatus();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public int getLimitLinkNumber() {
        return this.hiClassManager.getLimitLinkNumber();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void init() {
        if (checkSelMediaPermission()) {
            this.linkMicInitState = 2;
            this.linkMicManager.initEngine(new PLVLinkMicEngineParam().setChannelId(this.liveRoomDataManager.getConfig().getChannelId()).setGroupId(this.groupId).setViewerId(this.liveRoomDataManager.getConfig().getUser().getViewerId()).setViewerType(this.liveRoomDataManager.getConfig().getUser().getViewerType()).setNickName(this.liveRoomDataManager.getConfig().getUser().getViewerName()), new PLVLinkMicListener() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.1
                @Override // com.plv.livescenes.linkmic.listener.PLVLinkMicListener
                public void onLinkMicEngineCreatedSuccess() {
                    PLVCommonLog.d(PLVMultiRoleLinkMicPresenter.TAG, "连麦初始化成功");
                    PLVMultiRoleLinkMicPresenter.this.initEngineSuccessTimestamp = System.currentTimeMillis();
                    PLVMultiRoleLinkMicPresenter.this.linkMicInitState = 3;
                    PLVMultiRoleLinkMicPresenter pLVMultiRoleLinkMicPresenter = PLVMultiRoleLinkMicPresenter.this;
                    pLVMultiRoleLinkMicPresenter.observeRTCEvent(pLVMultiRoleLinkMicPresenter.linkMicManager);
                    Iterator it = PLVMultiRoleLinkMicPresenter.this.initiatedTasks.iterator();
                    while (it.hasNext()) {
                        ((Runnable) it.next()).run();
                    }
                    PLVMultiRoleLinkMicPresenter.this.initiatedTasks.clear();
                    PLVMultiRoleLinkMicPresenter.this.muteVideo(!r0.curEnableLocalVideo);
                    PLVMultiRoleLinkMicPresenter.this.muteAudio(!r0.curEnableLocalAudio);
                    if (PLVMultiRoleLinkMicPresenter.this.isTeacherType) {
                        PLVMultiRoleLinkMicPresenter.this.linkMicList.addMyItemToLinkMicList(PLVMultiRoleLinkMicPresenter.this.curEnableLocalVideo, PLVMultiRoleLinkMicPresenter.this.curEnableLocalAudio);
                    }
                    PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.1.1
                        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                        public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                            view.onLinkMicEngineCreatedSuccess();
                        }
                    });
                }

                @Override // com.plv.livescenes.linkmic.listener.PLVLinkMicListener
                public void onLinkMicError(final int errorCode, final Throwable throwable) {
                    PLVCommonLog.e(PLVMultiRoleLinkMicPresenter.TAG, "连麦模块错误：errorCode=" + errorCode);
                    PLVCommonLog.exception(throwable);
                    if (PLVMultiRoleLinkMicPresenter.this.linkMicInitState != 3) {
                        PLVMultiRoleLinkMicPresenter.this.linkMicInitState = 1;
                    }
                    PLVMultiRoleLinkMicPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.1.2
                        @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                        public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                            view.onLinkMicError(errorCode, throwable);
                        }
                    });
                }
            });
            String linkMicUid = this.linkMicManager.getLinkMicUid();
            this.myLinkMicId = linkMicUid;
            setMyLinkMicId(linkMicUid);
            callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.2
                @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.ViewRunnable
                public void run(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView view) {
                    view.onInitLinkMicList(PLVMultiRoleLinkMicPresenter.this.myLinkMicId, PLVMultiRoleLinkMicPresenter.this.linkMicList.getData());
                }
            });
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public boolean isInClassStatus() {
        return this.isInClassStatus;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public boolean isJoinDiscuss() {
        return !TextUtils.isEmpty(this.groupId);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public boolean isMyLinkMicId(String linkMicId) {
        return linkMicId != null && linkMicId.equals(this.myLinkMicId);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public boolean isTeacherType() {
        return this.isTeacherType;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void joinChannel() {
        if (this.joinChannelStatus != 1) {
            return;
        }
        this.joinChannelStatus = 2;
        acceptInitiatedTask(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.3
            @Override // java.lang.Runnable
            public void run() {
                Activity topActivity;
                if (PLVLinkMicConfig.getInstance().isPureRtcWatchEnabled() && (topActivity = ActivityUtils.getTopActivity()) != null) {
                    topActivity.setVolumeControlStream(0);
                }
                PLVMultiRoleLinkMicPresenter.this.linkMicManager.joinChannel();
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void leaveChannel() {
        this.linkMicManager.leaveChannel();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void muteAllUserAudio(final boolean isMute) {
        for (int i2 = 0; i2 < this.memberList.getData().size(); i2++) {
            PLVLinkMicItemDataBean linkMicItemDataBean = this.memberList.getData().get(i2).getLinkMicItemDataBean();
            if (linkMicItemDataBean != null && linkMicItemDataBean.isRtcJoinStatus() && !isMyLinkMicId(linkMicItemDataBean.getLinkMicId())) {
                setMediaPermission(i2, false, isMute);
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public boolean muteAudio(boolean mute) {
        this.curEnableLocalAudio = !mute;
        if (3 != this.linkMicInitState) {
            return false;
        }
        this.linkMicManager.muteLocalAudio(mute);
        this.linkMicData.postEnableAudio(!mute);
        this.memberList.updateUserMuteAudio(this.myLinkMicId, mute, 0);
        return true;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public boolean muteVideo(boolean mute) {
        this.curEnableLocalVideo = !mute;
        if (3 != this.linkMicInitState) {
            return false;
        }
        this.linkMicManager.muteLocalVideo(mute);
        this.linkMicData.postEnableVideo(!mute);
        this.memberList.updateUserMuteVideo(this.myLinkMicId, mute, 0);
        return true;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void registerView(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView v2) {
        if (this.iMultiRoleLinkMicViews == null) {
            this.iMultiRoleLinkMicViews = new ArrayList();
        }
        if (!this.iMultiRoleLinkMicViews.contains(v2)) {
            this.iMultiRoleLinkMicViews.add(v2);
        }
        v2.setPresenter(this);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void releaseRenderView(View renderView) {
        this.linkMicManager.releaseRenderView(renderView);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void requestMemberList() {
        this.memberList.requestData();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void sendCupEvent(int linkMicListPos, final Ack ack) {
        PLVMemberItemDataBean itemWithLinkMicListPos = this.memberList.getItemWithLinkMicListPos(linkMicListPos);
        if (itemWithLinkMicListPos == null) {
            return;
        }
        PLVLinkMicEventSender.getInstance().sendCupEvent(itemWithLinkMicListPos.getSocketUserBean(), this.liveRoomDataManager.getSessionId(), new IPLVLinkMicEventSender.PLVSMainCallAck() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.5
            @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.PLVSMainCallAck
            public void onCall(Object... args) {
                Ack ack2 = ack;
                if (ack2 != null) {
                    ack2.call(args);
                }
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void sendRaiseHandEvent(int raiseHandTime) {
        PLVLinkMicEventSender.getInstance().sendRaiseHandEvent(raiseHandTime, this.liveRoomDataManager.getSessionId(), null);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void setMediaPermission(int memberListPos, final boolean isVideoType, final boolean isMute) {
        setMediaPermission(memberListPos, isVideoType, isMute, null);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void setMediaPermissionInLinkMicList(int linkMicListPos, boolean isVideoType, boolean isMute, Ack ack) {
        setMediaPermission(this.memberList.getItemPos(linkMicListPos), isVideoType, isMute, ack);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void setPaintPermission(int memberListPos, boolean isHasPermission, final Ack ack) {
        PLVMemberItemDataBean item = this.memberList.getItem(memberListPos);
        if (item == null) {
            return;
        }
        PLVLinkMicEventSender.getInstance().setPaintPermission(item.getSocketUserBean(), this.liveRoomDataManager.getSessionId(), isHasPermission, new IPLVLinkMicEventSender.PLVSMainCallAck() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.6
            @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.PLVSMainCallAck
            public void onCall(Object... args) {
                Ack ack2 = ack;
                if (ack2 != null) {
                    ack2.call(args);
                }
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void setPaintPermissionInLinkMicList(int linkMicListPos, boolean isHasPermission, Ack ack) {
        setPaintPermission(this.memberList.getItemPos(linkMicListPos), isHasPermission, ack);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void setPushPictureResolutionType(int type) {
        IPLVLinkMicManager iPLVLinkMicManager = this.linkMicManager;
        if (iPLVLinkMicManager == null) {
            return;
        }
        iPLVLinkMicManager.setPushPictureResolutionType(type);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void setupRenderView(View renderView, String linkMicId) {
        if (isMyLinkMicId(linkMicId)) {
            this.linkMicManager.setupLocalVideo(renderView, linkMicId);
        } else {
            this.linkMicManager.setupRemoteVideo(renderView, linkMicId);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void startLesson(final IPLVDataRequestListener<String> listener) {
        this.hiClassManager.changeLessonStatus(listener, 1);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void stopLesson(IPLVDataRequestListener<String> listener) {
        this.hiClassManager.changeLessonStatus(listener, 2);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void switchCamera() {
        switchCamera(!this.curCameraFront);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void switchRoleToAudience() {
        this.linkMicManager.switchRoleToAudience();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void switchRoleToBroadcaster() {
        this.linkMicManager.switchRoleToBroadcaster();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void unregisterView(IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView v2) {
        List<IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView> list = this.iMultiRoleLinkMicViews;
        if (list != null) {
            list.remove(v2);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void setMediaPermission(int memberListPos, final boolean isVideoType, final boolean isMute, final Ack ack) {
        PLVMemberItemDataBean item = this.memberList.getItem(memberListPos);
        if (item == null) {
            return;
        }
        PLVSocketUserBean socketUserBean = item.getSocketUserBean();
        final PLVLinkMicItemDataBean linkMicItemDataBean = item.getLinkMicItemDataBean();
        PLVLinkMicEventSender.getInstance().setMediaPermission(socketUserBean, this.liveRoomDataManager.getSessionId(), isVideoType, isMute, new IPLVLinkMicEventSender.PLVSMainCallAck() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.7
            @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.PLVSMainCallAck
            public void onCall(Object... args) {
                if (linkMicItemDataBean != null) {
                    if (isVideoType) {
                        PLVMultiRoleLinkMicPresenter.this.memberList.updateUserMuteVideo(linkMicItemDataBean.getLinkMicId(), isMute, linkMicItemDataBean.getStreamType());
                    } else {
                        PLVMultiRoleLinkMicPresenter.this.memberList.updateUserMuteAudio(linkMicItemDataBean.getLinkMicId(), isMute, linkMicItemDataBean.getStreamType());
                    }
                    Ack ack2 = ack;
                    if (ack2 != null) {
                        ack2.call(args);
                    }
                }
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void switchCamera(final boolean front) {
        acceptSwitchCameraTask(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.PLVMultiRoleLinkMicPresenter.4
            @Override // java.lang.Runnable
            public void run() {
                if (3 != PLVMultiRoleLinkMicPresenter.this.linkMicInitState) {
                    return;
                }
                if (front) {
                    PLVMultiRoleLinkMicPresenter.this.linkMicManager.setLocalPreviewMirror(PLVMultiRoleLinkMicPresenter.this.isFrontPreviewMirror);
                } else {
                    PLVMultiRoleLinkMicPresenter.this.linkMicManager.setLocalPreviewMirror(false);
                }
                boolean z2 = PLVMultiRoleLinkMicPresenter.this.curCameraFront;
                boolean z3 = front;
                if (z2 == z3) {
                    return;
                }
                PLVMultiRoleLinkMicPresenter.this.curCameraFront = z3;
                PLVMultiRoleLinkMicPresenter.this.linkMicManager.switchCamera();
                PLVMultiRoleLinkMicPresenter.this.linkMicData.postIsFrontCamera(PLVMultiRoleLinkMicPresenter.this.curCameraFront);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter
    public void setupRenderView(View renderView, String linkMicId, int streamType) {
        if (isMyLinkMicId(linkMicId)) {
            this.linkMicManager.setupLocalVideo(renderView, linkMicId);
        } else {
            this.linkMicManager.setupRemoteVideo(renderView, linkMicId, streamType);
        }
    }
}
