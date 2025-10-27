package com.easefun.polyv.livecommon.module.modules.streamer.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Pair;
import android.view.SurfaceView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.beauty.model.PLVBeautyRepo;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicDataMapper;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract;
import com.easefun.polyv.livecommon.module.modules.streamer.model.PLVMemberItemDataBean;
import com.easefun.polyv.livecommon.module.modules.streamer.presenter.data.PLVStreamerData;
import com.easefun.polyv.livescenes.streamer.listener.IPLVSStreamerOnLiveStatusChangeListener;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.audio.SilenceSkippingAudioProcessor;
import com.hjq.permissions.Permission;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.rx.PLVRxBaseRetryFunction;
import com.plv.foundationsdk.rx.PLVRxBaseTransformer;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.linkmic.model.PLVJoinInfoEvent;
import com.plv.linkmic.model.PLVLinkMicJoinStatus;
import com.plv.linkmic.repository.PLVLinkMicDataRepository;
import com.plv.linkmic.repository.PLVLinkMicHttpRequestException;
import com.plv.linkmic.screenshare.IPLVScreenShareListener;
import com.plv.livescenes.access.PLVUserAbility;
import com.plv.livescenes.access.PLVUserAbilityManager;
import com.plv.livescenes.access.PLVUserRole;
import com.plv.livescenes.chatroom.PLVChatApiRequestHelper;
import com.plv.livescenes.chatroom.PLVChatroomManager;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.livescenes.linkmic.vo.PLVLinkMicEngineParam;
import com.plv.livescenes.log.chat.PLVChatroomELog;
import com.plv.livescenes.model.PLVListUsersVO;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.livescenes.streamer.IPLVStreamerManager;
import com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender;
import com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender;
import com.plv.livescenes.streamer.listener.IPLVOnGetSessionIdInnerListener;
import com.plv.livescenes.streamer.listener.IPLVStreamerOnLiveStreamingStartListener;
import com.plv.livescenes.streamer.listener.IPLVStreamerOnLiveTimingListener;
import com.plv.livescenes.streamer.listener.IPLVStreamerOnServerTimeoutDueToNetBrokenListener;
import com.plv.livescenes.streamer.listener.PLVStreamerEventListener;
import com.plv.livescenes.streamer.listener.PLVStreamerListener;
import com.plv.livescenes.streamer.manager.PLVStreamerManagerFactory;
import com.plv.livescenes.streamer.mix.PLVRTCMixUser;
import com.plv.livescenes.streamer.transfer.PLVStreamerInnerDataTransfer;
import com.plv.socket.log.PLVELogSender;
import com.plv.socket.user.PLVSocketUserBean;
import com.plv.socket.user.PLVSocketUserConstant;
import com.plv.thirdpart.blankj.utilcode.util.SPUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.socket.client.Ack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class PLVStreamerPresenter implements IPLVStreamerContract.IStreamerPresenter {
    private static final int DEFAULT_MEMBER_LENGTH = 500;
    private static final int DEFAULT_MEMBER_PAGE = 1;
    private static final int ERROR_GUEST_LINK_TIMEOUT = 1;
    private static final int INTERVAL_TO_GET_LINK_MIC_LIST = 10000;
    private static final int INTERVAL_TO_GET_USER_LIST = 20000;
    private static final int INTERVAL_TO_POLL_LIVE_STATUS = 10000;
    private static final int STREAMER_MIC_INITIATED = 3;
    private static final int STREAMER_MIC_INITIATING = 2;
    private static final int STREAMER_MIC_UNINITIATED = 1;
    public static final int STREAMER_STATUS_START = 1;
    public static final int STREAMER_STATUS_START_SUCCESS = 2;
    public static final int STREAMER_STATUS_STOP = 3;
    private static final String TAG = "PLVStreamerPresenter";
    private static final int TIME_OUT_JOIN_CHANNEL = 20000;
    private static final int TIME_OUT_TO_SHOW_NET_BROKEN = 20;
    private Observer<Boolean> beautySwitchStateObserver;

    @Nullable
    private PLVSocketUserBean currentSocketUserBean;
    private PLVSocketUserBean currentSpeakerPermissionUser;
    private List<IPLVStreamerContract.IStreamerView> iStreamerViews;

    @Nullable
    private String lastFirstScreenUserId;
    private Disposable linkMicListTimerDisposable;
    private Disposable listUserTimerDisposable;
    private Disposable listUsersDisposable;
    private final IPLVLiveRoomDataManager liveRoomDataManager;
    private final IPLVStreamerManager streamerManager;
    private final PLVStreamerMsgHandler streamerMsgHandler;
    private final String userType;
    private final PLVBeautyRepo beautyRepo = (PLVBeautyRepo) PLVDependManager.getInstance().get(PLVBeautyRepo.class);
    private int streamerInitState = 1;
    private int streamerStatus = 3;
    private int curBitrate = loadBitrate();
    private boolean curCameraFront = true;
    private boolean curEnableRecordingAudioVolume = true;
    private boolean curEnableLocalVideo = true;
    private boolean isFrontMirror = true;
    private int pushPictureResolution = 2;
    private PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio = PLVLinkMicConstant.PushResolutionRatio.RATIO_16_9;
    private boolean isRecoverStream = false;
    private float localCameraZoomFactor = 1.0f;

    @Nullable
    private Boolean isNetworkConnect = null;
    final List<PLVLinkMicItemDataBean> streamerList = new LinkedList();
    List<PLVMemberItemDataBean> memberList = new LinkedList();
    final Map<String, PLVLinkMicItemDataBean> rtcJoinMap = new HashMap();
    private final TimerToShowNetBroken timerToShowNetBroken = new TimerToShowNetBroken(20);
    private Runnable joinChannelRunnable = null;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final PLVStreamerData streamerData = new PLVStreamerData();

    public static class SortGuestLinkMicListUtils {
        private static final String FIRST_SCREEN_USER_TYPE = "SortGuestLinkMicListUtils-firstScreenUserType";
        private static final String OTHER_TYPE = "SortGuestLinkMicListUtils-other";
        private static final List<String> SORT_INDEX = Arrays.asList(FIRST_SCREEN_USER_TYPE, "teacher", "guest", OTHER_TYPE);

        /* JADX INFO: Access modifiers changed from: private */
        public static String getSortType(PLVLinkMicItemDataBean itemDataBean) {
            if (itemDataBean.isFirstScreen()) {
                return FIRST_SCREEN_USER_TYPE;
            }
            String userType = itemDataBean.getUserType();
            return !SORT_INDEX.contains(userType) ? OTHER_TYPE : userType;
        }

        public static List<PLVLinkMicItemDataBean> sort(List<PLVLinkMicItemDataBean> input) {
            Collections.sort(input, new Comparator<PLVLinkMicItemDataBean>() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.SortGuestLinkMicListUtils.1
                @Override // java.util.Comparator
                public int compare(PLVLinkMicItemDataBean o12, PLVLinkMicItemDataBean o2) {
                    int iIndexOf = SortGuestLinkMicListUtils.SORT_INDEX.indexOf(SortGuestLinkMicListUtils.getSortType(o12));
                    int iIndexOf2 = SortGuestLinkMicListUtils.SORT_INDEX.indexOf(SortGuestLinkMicListUtils.getSortType(o2));
                    if (iIndexOf != iIndexOf2) {
                        return iIndexOf - iIndexOf2;
                    }
                    try {
                        if ("guest".equals(o12.getUserType()) && "guest".equals(o2.getUserType())) {
                            return Integer.parseInt(o12.getUserId()) - Integer.parseInt(o2.getUserId());
                        }
                        return 0;
                    } catch (Exception unused) {
                        return 0;
                    }
                }
            });
            return input;
        }
    }

    public static class SortMemberListUtils {
        private static final String SELF = "自己";
        private static final String REAL_LINK_MIC_WAIT = "非虚拟wait";
        private static final String REAL_LINK_MIC_JOINING = "非虚拟joining";
        private static final String REAL_LINK_MIC_JOIN = "非虚拟join";
        private static final String REAL_LINK_MIC_RTC_JOIN = "非虚拟rtcJoin";
        private static final String REAL = "非虚拟";
        private static final List<String> SORT_INDEX = Arrays.asList(SELF, PLVSocketUserConstant.USERTYPE_MANAGER, "teacher", "guest", "viewer", "assistant", REAL_LINK_MIC_WAIT, REAL_LINK_MIC_JOINING, REAL_LINK_MIC_JOIN, REAL_LINK_MIC_RTC_JOIN, REAL, PLVSocketUserConstant.USERTYPE_DUMMY);

        /* JADX INFO: Access modifiers changed from: private */
        public static String getSortType(PLVMemberItemDataBean item) {
            PLVSocketUserBean socketUserBean = item.getSocketUserBean();
            String userType = socketUserBean.getUserType();
            if (PLVSocketWrapper.getInstance().getLoginVO().getUserId().equals(socketUserBean.getUserId())) {
                return SELF;
            }
            if (PLVSocketUserConstant.USERTYPE_MANAGER.equals(userType) || "teacher".equals(userType) || "guest".equals(userType) || "viewer".equals(userType) || "assistant".equals(userType) || PLVSocketUserConstant.USERTYPE_DUMMY.equals(userType)) {
                return userType;
            }
            PLVLinkMicItemDataBean linkMicItemDataBean = item.getLinkMicItemDataBean();
            if (linkMicItemDataBean != null) {
                if (linkMicItemDataBean.isRtcJoinStatus()) {
                    return REAL_LINK_MIC_RTC_JOIN;
                }
                if (linkMicItemDataBean.isJoinStatus()) {
                    return REAL_LINK_MIC_JOIN;
                }
                if (linkMicItemDataBean.isJoiningStatus()) {
                    return REAL_LINK_MIC_JOINING;
                }
                if (linkMicItemDataBean.isWaitStatus()) {
                    return REAL_LINK_MIC_WAIT;
                }
            }
            return REAL;
        }

        public static List<PLVMemberItemDataBean> sort(List<PLVMemberItemDataBean> memberList) {
            Collections.sort(memberList, new Comparator<PLVMemberItemDataBean>() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.SortMemberListUtils.1
                @Override // java.util.Comparator
                public int compare(PLVMemberItemDataBean o12, PLVMemberItemDataBean o2) {
                    return SortMemberListUtils.SORT_INDEX.indexOf(SortMemberListUtils.getSortType(o12)) - SortMemberListUtils.SORT_INDEX.indexOf(SortMemberListUtils.getSortType(o2));
                }
            });
            return memberList;
        }
    }

    public class TimerToShowNetBroken {
        private boolean hasShownDuringOneNetBroken = false;
        private final int secondsToShow;
        private Disposable timerDisposable;

        public TimerToShowNetBroken(int secondsToShow) {
            this.secondsToShow = secondsToShow;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispose(Disposable disposable) {
            if (disposable != null) {
                disposable.dispose();
            }
        }

        private boolean isOngoing() {
            if (this.timerDisposable != null) {
                return !r0.isDisposed();
            }
            return false;
        }

        public void destroy() {
            this.hasShownDuringOneNetBroken = false;
            dispose(this.timerDisposable);
        }

        public void invokeTimerWhenNoConnection() {
            if (PLVStreamerPresenter.this.streamerStatus == 3 || isOngoing() || this.hasShownDuringOneNetBroken) {
                return;
            }
            dispose(this.timerDisposable);
            this.timerDisposable = PLVRxTimer.timer(1000, new Consumer<Long>() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.TimerToShowNetBroken.1
                @Override // io.reactivex.functions.Consumer
                public void accept(Long aLong) throws Exception {
                    if (aLong.longValue() < TimerToShowNetBroken.this.secondsToShow) {
                        if (PLVStreamerPresenter.this.streamerManager.getCurrentNetQuality() != 14) {
                            TimerToShowNetBroken timerToShowNetBroken = TimerToShowNetBroken.this;
                            timerToShowNetBroken.dispose(timerToShowNetBroken.timerDisposable);
                            return;
                        }
                        return;
                    }
                    TimerToShowNetBroken timerToShowNetBroken2 = TimerToShowNetBroken.this;
                    timerToShowNetBroken2.dispose(timerToShowNetBroken2.timerDisposable);
                    TimerToShowNetBroken.this.hasShownDuringOneNetBroken = true;
                    if (PLVStreamerPresenter.this.streamerManager.isLiveStreaming()) {
                        PLVStreamerPresenter.this.streamerData.postShowNetBroken();
                        PLVStreamerPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.TimerToShowNetBroken.1.2
                            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                                view.onShowNetBroken();
                            }
                        });
                    } else {
                        PLVStreamerPresenter.this.stopLiveStream();
                        PLVStreamerPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.TimerToShowNetBroken.1.1
                            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                                view.onStreamerError(-1, new Throwable("network disconnect"));
                            }
                        });
                    }
                }
            });
        }

        public void resetWhenHasConnection() {
            this.hasShownDuringOneNetBroken = false;
        }
    }

    public interface ViewRunnable {
        void run(@NonNull IPLVStreamerContract.IStreamerView view);
    }

    public PLVStreamerPresenter(IPLVLiveRoomDataManager liveRoomDataManager) {
        this.liveRoomDataManager = liveRoomDataManager;
        String viewerId = liveRoomDataManager.getConfig().getUser().getViewerId();
        this.userType = liveRoomDataManager.getConfig().getUser().getViewerType();
        PLVLinkMicConfig.getInstance().init(viewerId, true);
        IPLVStreamerManager iPLVStreamerManagerCreateNewStreamerManager = PLVStreamerManagerFactory.createNewStreamerManager();
        this.streamerManager = iPLVStreamerManagerCreateNewStreamerManager;
        if (liveRoomDataManager.isOnlyAudio()) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(Permission.RECORD_AUDIO);
            iPLVStreamerManagerCreateNewStreamerManager.resetRequestPermissionList(arrayList);
        }
        PLVStreamerMsgHandler pLVStreamerMsgHandler = new PLVStreamerMsgHandler(this);
        this.streamerMsgHandler = pLVStreamerMsgHandler;
        pLVStreamerMsgHandler.run();
        observeBeautySwitchState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptLinkMicJoinStatus(PLVLinkMicJoinStatus data) {
        List<PLVJoinInfoEvent> joinList = data.getJoinList();
        List<PLVLinkMicJoinStatus.WaitListBean> waitList = data.getWaitList();
        Iterator<PLVJoinInfoEvent> it = joinList.iterator();
        while (it.hasNext()) {
            PLVJoinInfoEvent next = it.next();
            if ("guest".equals(next.getUserType()) && !next.getClassStatus().isVoice()) {
                it.remove();
                waitList.add(PLVLinkMicDataMapper.map2WaitListBean(next));
                PLVCommonLog.d(TAG, String.format(Locale.US, "guest user [%s] lies in joinList but not join at all, so we move him to waitList manually.", next.toString()));
            }
        }
        boolean zUpdateMemberListLinkMicStatus = updateMemberListLinkMicStatus(joinList, waitList);
        for (PLVJoinInfoEvent pLVJoinInfoEvent : joinList) {
            PLVLinkMicItemDataBean pLVLinkMicItemDataBeanMap2LinkMicItemData = PLVLinkMicDataMapper.map2LinkMicItemData(pLVJoinInfoEvent);
            PLVSocketUserBean pLVSocketUserBeanMap2SocketUserBean = PLVLinkMicDataMapper.map2SocketUserBean(pLVJoinInfoEvent);
            updateUserPermissionStatus(pLVLinkMicItemDataBeanMap2LinkMicItemData, pLVJoinInfoEvent);
            if (!isMyLinkMicId(pLVLinkMicItemDataBeanMap2LinkMicItemData.getLinkMicId()) && updateMemberListItemInfo(pLVSocketUserBeanMap2SocketUserBean, pLVLinkMicItemDataBeanMap2LinkMicItemData, true)) {
                zUpdateMemberListLinkMicStatus = true;
            }
        }
        removeLinkMicDataNoExistServer(joinList);
        for (PLVLinkMicJoinStatus.WaitListBean waitListBean : waitList) {
            PLVLinkMicItemDataBean pLVLinkMicItemDataBeanMap2LinkMicItemData2 = PLVLinkMicDataMapper.map2LinkMicItemData(waitListBean);
            PLVSocketUserBean pLVSocketUserBeanMap2SocketUserBean2 = PLVLinkMicDataMapper.map2SocketUserBean(waitListBean);
            if (!isMyLinkMicId(pLVSocketUserBeanMap2SocketUserBean2.getUserId()) && updateMemberListItemInfo(pLVSocketUserBeanMap2SocketUserBean2, pLVLinkMicItemDataBeanMap2LinkMicItemData2, false)) {
                zUpdateMemberListLinkMicStatus = true;
            }
        }
        if (zUpdateMemberListLinkMicStatus) {
            callUpdateSortMemberList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public String findChannelTeacherUserId() {
        for (PLVMemberItemDataBean pLVMemberItemDataBean : this.memberList) {
            if ("teacher".equals(pLVMemberItemDataBean.getSocketUserBean().getUserType())) {
                return pLVMemberItemDataBean.getSocketUserBean().getUserId();
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void generateMemberListWithListUsers(List<PLVSocketUserBean> socketUserBeanList, boolean isResetJoiningStatus) {
        PLVLinkMicItemDataBean linkMicItemDataBean;
        LinkedList linkedList = new LinkedList();
        int i2 = 0;
        while (i2 < socketUserBeanList.size()) {
            PLVSocketUserBean pLVSocketUserBean = socketUserBeanList.get(i2);
            String userId = pLVSocketUserBean.getUserId();
            if (userId == null || !userId.equals(PLVSocketWrapper.getInstance().getLoginVO().getUserId())) {
                PLVMemberItemDataBean pLVMemberItemDataBean = new PLVMemberItemDataBean();
                pLVMemberItemDataBean.setSocketUserBean(pLVSocketUserBean);
                Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId = getMemberItemWithUserId(userId);
                if (memberItemWithUserId != null && (linkMicItemDataBean = ((PLVMemberItemDataBean) memberItemWithUserId.second).getLinkMicItemDataBean()) != null) {
                    if (linkMicItemDataBean.isJoiningStatus() && isResetJoiningStatus) {
                        linkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_IDLE);
                    }
                    pLVMemberItemDataBean.setLinkMicItemDataBean(linkMicItemDataBean);
                }
                linkedList.add(pLVMemberItemDataBean);
            } else {
                socketUserBeanList.remove(pLVSocketUserBean);
                i2--;
            }
            i2++;
        }
        this.memberList = linkedList;
        new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.23
            @Override // java.lang.Runnable
            public void run() {
                PLVMemberItemDataBean pLVMemberItemDataBean2 = new PLVMemberItemDataBean();
                pLVMemberItemDataBean2.setFrontCamera(PLVStreamerPresenter.this.curCameraFront);
                PLVStreamerPresenter pLVStreamerPresenter = PLVStreamerPresenter.this;
                Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = pLVStreamerPresenter.getLinkMicItemWithLinkMicId(pLVStreamerPresenter.streamerManager.getLinkMicUid());
                if (linkMicItemWithLinkMicId != null) {
                    pLVMemberItemDataBean2.setLinkMicItemDataBean((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second);
                }
                PLVStreamerPresenter.this.currentSocketUserBean = PLVSocketWrapper.getInstance().getLoginVO().createSocketUserBean();
                pLVMemberItemDataBean2.setSocketUserBean(PLVStreamerPresenter.this.currentSocketUserBean);
                PLVStreamerPresenter.this.memberList.add(0, pLVMemberItemDataBean2);
            }
        }.run();
        callUpdateSortMemberList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initStreamerListener() {
        this.streamerMsgHandler.observeLinkMicData();
        this.streamerManager.addEventHandler(new PLVStreamerEventListener() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.13
            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onNetworkQuality(final int quality) {
                PLVStreamerPresenter.this.streamerData.postNetworkQuality(quality);
                PLVStreamerPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.13.1
                    @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onNetworkQuality(quality);
                    }
                });
                if (quality == 14) {
                    PLVStreamerPresenter.this.timerToShowNetBroken.invokeTimerWhenNoConnection();
                    PLVStreamerPresenter.this.updateNetworkForGuestAutoLinkMic(false);
                } else {
                    if (PLVStreamerPresenter.this.timerToShowNetBroken.hasShownDuringOneNetBroken) {
                        PLVStreamerPresenter.this.updateMixLayoutUsers();
                    }
                    PLVStreamerPresenter.this.timerToShowNetBroken.resetWhenHasConnection();
                    PLVStreamerPresenter.this.updateNetworkForGuestAutoLinkMic(true);
                }
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onRemoteStreamClose(final String uid, int streamType) {
                PLVLinkMicItemDataBean pLVLinkMicItemDataBean = (PLVLinkMicItemDataBean) PLVSugarUtil.nullable(new PLVSugarUtil.Supplier<PLVLinkMicItemDataBean>() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.13.3
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Supplier
                    public PLVLinkMicItemDataBean get() {
                        return (PLVLinkMicItemDataBean) PLVStreamerPresenter.this.getLinkMicItemWithLinkMicId(uid).second;
                    }
                });
                if (pLVLinkMicItemDataBean != null && streamType == 2) {
                    pLVLinkMicItemDataBean.setScreenShare(false);
                    PLVStreamerPresenter.this.updateMixLayoutWhenScreenShare(false, uid);
                }
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onRemoteStreamOpen(final String uid, int streamType) {
                PLVLinkMicItemDataBean pLVLinkMicItemDataBean = (PLVLinkMicItemDataBean) PLVSugarUtil.nullable(new PLVSugarUtil.Supplier<PLVLinkMicItemDataBean>() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.13.2
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Supplier
                    public PLVLinkMicItemDataBean get() {
                        return (PLVLinkMicItemDataBean) PLVStreamerPresenter.this.getLinkMicItemWithLinkMicId(uid).second;
                    }
                });
                if (pLVLinkMicItemDataBean != null && streamType == 2) {
                    pLVLinkMicItemDataBean.setScreenShare(true);
                    PLVStreamerPresenter.this.updateMixLayoutWhenScreenShare(true, uid);
                }
            }
        });
        this.streamerManager.addOnLiveStreamingStartListener(new IPLVStreamerOnLiveStreamingStartListener() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.14
            @Override // com.plv.livescenes.streamer.listener.IPLVStreamerOnLiveStreamingStartListener
            public void onLiveStreamingStart() {
                PLVStreamerPresenter.this.streamerStatus = 2;
                PLVStreamerPresenter.this.streamerData.postStreamerStatus(true);
                PLVStreamerPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.14.1
                    @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onStatesToStreamStarted();
                    }
                });
            }
        });
        this.streamerManager.setOnLiveTimingListener(new IPLVStreamerOnLiveTimingListener() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.15
            @Override // com.plv.livescenes.streamer.listener.IPLVStreamerOnLiveTimingListener
            public void onTimePastEachSeconds(final int duration) {
                PLVStreamerPresenter.this.streamerData.postStreamerTime(duration);
                PLVStreamerPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.15.1
                    @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onUpdateStreamerTime(duration);
                    }
                });
            }
        });
        this.streamerManager.addStreamerServerTimeoutListener(new IPLVStreamerOnServerTimeoutDueToNetBrokenListener() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.16
            @Override // com.plv.livescenes.streamer.listener.IPLVStreamerOnServerTimeoutDueToNetBrokenListener
            public void onServerTimeoutDueToNetBroken() {
                if (PLVStreamerPresenter.this.streamerStatus != 3) {
                    PLVStreamerPresenter.this.stopLiveStream();
                    PLVStreamerPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.16.1
                        @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                        public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                            view.onStreamerError(-1, new Throwable("timeout"));
                        }
                    });
                }
            }
        });
        this.streamerManager.addGetSessionIdFromServerListener(new IPLVOnGetSessionIdInnerListener() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.17
            @Override // com.plv.livescenes.streamer.listener.IPLVOnGetSessionIdInnerListener
            public void onGetSessionId(String sessionId, String channelId, String streamId, boolean isCamClosed) {
                PLVStreamerPresenter.this.liveRoomDataManager.setSessionId(sessionId);
            }
        });
    }

    private boolean isInitStreamerManager() {
        return this.streamerInitState == 3;
    }

    private boolean isMyLinkMicId(String linkMicId) {
        return linkMicId != null && linkMicId.equals(this.streamerManager.getLinkMicUid());
    }

    private int loadBitrate() {
        int i2 = SPUtils.getInstance().getInt("plv_key_bitrate", 3);
        if (i2 < 1) {
            i2 = 1;
        }
        if (i2 > 3) {
            return 3;
        }
        return i2;
    }

    private void observeBeautySwitchState() {
        if (this.beautySwitchStateObserver != null) {
            this.beautyRepo.getBeautySwitchLiveData().removeObserver(this.beautySwitchStateObserver);
        }
        LiveData<Boolean> beautySwitchLiveData = this.beautyRepo.getBeautySwitchLiveData();
        Observer<Boolean> observer = new Observer<Boolean>() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable Boolean switchOnBoolean) {
                if (switchOnBoolean == null) {
                    return;
                }
                PLVStreamerPresenter.this.streamerManager.switchBeauty(switchOnBoolean.booleanValue());
            }
        };
        this.beautySwitchStateObserver = observer;
        beautySwitchLiveData.observeForever(observer);
    }

    private void pollLiveStatus() {
        this.streamerManager.listenLiveStatusChange(0, 10000, new IPLVSStreamerOnLiveStatusChangeListener() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.30
            @Override // com.plv.livescenes.streamer.listener.IPLVStreamerOnLiveStatusChangeListener
            public void onLiveStatusChange(final boolean isLive) {
                PLVLinkMicItemDataBean pLVLinkMicItemDataBean;
                if (isLive) {
                    PLVStreamerPresenter.this.guestTryJoinLinkMic();
                } else {
                    PLVStreamerPresenter pLVStreamerPresenter = PLVStreamerPresenter.this;
                    pLVStreamerPresenter.dispose(pLVStreamerPresenter.linkMicListTimerDisposable);
                    PLVStreamerPresenter.this.streamerManager.leaveChannel(true);
                    String linkMicUid = PLVStreamerPresenter.this.streamerManager.getLinkMicUid();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= PLVStreamerPresenter.this.streamerList.size()) {
                            pLVLinkMicItemDataBean = null;
                            break;
                        }
                        pLVLinkMicItemDataBean = PLVStreamerPresenter.this.streamerList.get(i2);
                        if (linkMicUid.equals(pLVLinkMicItemDataBean.getLinkMicId())) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                    PLVStreamerPresenter.this.rtcJoinMap.clear();
                    PLVStreamerPresenter.this.streamerList.clear();
                    if (pLVLinkMicItemDataBean != null) {
                        PLVStreamerPresenter.this.streamerList.add(pLVLinkMicItemDataBean);
                    }
                    PLVStreamerPresenter.this.updateLinkMicCount();
                    PLVStreamerPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.30.1
                        @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                        public void run(@NonNull @NotNull IPLVStreamerContract.IStreamerView view) {
                            view.onUsersLeave(PLVStreamerPresenter.this.streamerList);
                        }
                    });
                    PLVStreamerPresenter.this.callUpdateGuestStatus(false);
                }
                PLVStreamerPresenter.this.streamerData.postStreamerStatus(isLive);
                PLVStreamerPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.30.2
                    @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                    public void run(@NonNull @NotNull IPLVStreamerContract.IStreamerView view) {
                        view.onStreamLiveStatusChanged(isLive);
                    }
                });
            }
        });
    }

    private void removeLinkMicDataNoExistServer(List<PLVJoinInfoEvent> joinList) {
        boolean z2;
        final ArrayList arrayList = new ArrayList();
        Iterator<PLVLinkMicItemDataBean> it = this.streamerList.iterator();
        while (it.hasNext()) {
            PLVLinkMicItemDataBean next = it.next();
            String linkMicId = next.getLinkMicId();
            Iterator<PLVJoinInfoEvent> it2 = joinList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z2 = false;
                    break;
                }
                PLVJoinInfoEvent next2 = it2.next();
                if (linkMicId != null && linkMicId.equals(next2.getUserId())) {
                    z2 = true;
                    break;
                }
            }
            if (!z2 && !isMyLinkMicId(linkMicId)) {
                it.remove();
                arrayList.add(next);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        updateMixLayoutUsers();
        updateLinkMicCount();
        callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.27
            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onUsersLeave(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestLinkMicListApiTimer() {
        dispose(this.linkMicListTimerDisposable);
        this.linkMicListTimerDisposable = PLVRxTimer.timer(1000, 10000, new Consumer<Long>() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.24
            @Override // io.reactivex.functions.Consumer
            public void accept(Long aLong) throws Exception {
                if (TextUtils.isEmpty(PLVStreamerPresenter.this.liveRoomDataManager.getSessionId())) {
                    return;
                }
                PLVStreamerPresenter.this.streamerManager.getLinkStatus(PLVStreamerPresenter.this.liveRoomDataManager.getSessionId(), new PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicJoinStatus>() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.24.1
                    @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
                    public void onFail(PLVLinkMicHttpRequestException throwable) {
                        super.onFail(throwable);
                        PLVCommonLog.exception(throwable);
                    }

                    @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
                    public void onSuccess(PLVLinkMicJoinStatus data) {
                        PLVCommonLog.d(PLVStreamerPresenter.TAG, "PLVStreamerPresenter.requestLinkMicListFromServer.onSuccess->\n" + PLVGsonUtil.toJson(data));
                        PLVStreamerPresenter.this.acceptLinkMicJoinStatus(data);
                    }
                });
            }
        });
    }

    private void requestListUsersApi() {
        dispose(this.listUsersDisposable);
        dispose(this.listUserTimerDisposable);
        dispose(this.linkMicListTimerDisposable);
        final String loginRoomId = PLVSocketWrapper.getInstance().getLoginRoomId();
        if (TextUtils.isEmpty(loginRoomId)) {
            loginRoomId = this.liveRoomDataManager.getConfig().getChannelId();
        }
        this.listUsersDisposable = PLVChatApiRequestHelper.getListUsers(loginRoomId, 1, 500).retryWhen(new PLVRxBaseRetryFunction(Integer.MAX_VALUE, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS)).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<PLVListUsersVO>() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.18
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVListUsersVO plvsListUsersVO) throws Exception {
                PLVChatroomManager.getInstance().setOnlineCount(plvsListUsersVO.getCount());
                PLVStreamerPresenter.this.generateMemberListWithListUsers(plvsListUsersVO.getUserlist(), true);
                PLVStreamerPresenter.this.requestLinkMicListApiTimer();
                PLVStreamerPresenter.this.requestListUsersApiTimer(loginRoomId);
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.19
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
                PLVELogSender.send(PLVChatroomELog.class, PLVChatroomELog.Event.GET_LISTUSERS_FAIL, throwable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestListUsersApiTimer(final String roomId) {
        dispose(this.listUserTimerDisposable);
        this.listUserTimerDisposable = Observable.interval(SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US, SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US, TimeUnit.MILLISECONDS, Schedulers.io()).flatMap(new Function<Long, Observable<PLVListUsersVO>>() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.22
            @Override // io.reactivex.functions.Function
            public Observable<PLVListUsersVO> apply(@NonNull Long aLong) throws Exception {
                return PLVChatApiRequestHelper.getListUsers(roomId, 1, 500).retry(1L);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PLVListUsersVO>() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.20
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVListUsersVO plvsListUsersVO) throws Exception {
                PLVChatroomManager.getInstance().setOnlineCount(plvsListUsersVO.getCount());
                PLVStreamerPresenter.this.generateMemberListWithListUsers(plvsListUsersVO.getUserlist(), false);
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.21
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
                PLVELogSender.send(PLVChatroomELog.class, PLVChatroomELog.Event.GET_LISTUSERS_FAIL, throwable);
            }
        });
    }

    private void saveBitrate() {
        int i2 = this.curBitrate;
        if (i2 < 1 || i2 > 3) {
            return;
        }
        SPUtils.getInstance().put("plv_key_bitrate", this.curBitrate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startJoinTimeoutCount(final PLVLinkMicItemDataBean linkMicItemDataBean) {
        final Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.28
            @Override // java.lang.Runnable
            public void run() {
                linkMicItemDataBean.setStatusMethodCallListener(null);
                linkMicItemDataBean.setStatus("wait");
                PLVStreamerPresenter.this.callUpdateSortMemberList();
            }
        };
        this.handler.postDelayed(runnable, SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US);
        linkMicItemDataBean.setStatusMethodCallListener(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.29
            @Override // java.lang.Runnable
            public void run() {
                if (linkMicItemDataBean.isJoiningStatus()) {
                    return;
                }
                PLVStreamerPresenter.this.handler.removeCallbacks(runnable);
            }
        });
    }

    private boolean updateMemberListLinkMicStatus(List<PLVJoinInfoEvent> joinList, List<PLVLinkMicJoinStatus.WaitListBean> waitList) {
        boolean z2;
        Iterator<PLVMemberItemDataBean> it = this.memberList.iterator();
        boolean z3 = false;
        while (it.hasNext()) {
            PLVLinkMicItemDataBean linkMicItemDataBean = it.next().getLinkMicItemDataBean();
            if (linkMicItemDataBean != null && !linkMicItemDataBean.isIdleStatus() && !isMyLinkMicId(linkMicItemDataBean.getLinkMicId())) {
                String linkMicId = linkMicItemDataBean.getLinkMicId();
                Iterator<PLVJoinInfoEvent> it2 = joinList.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z2 = false;
                        break;
                    }
                    PLVJoinInfoEvent next = it2.next();
                    if (linkMicId != null && linkMicId.equals(next.getUserId())) {
                        z2 = true;
                        break;
                    }
                }
                if (!z2) {
                    Iterator<PLVLinkMicJoinStatus.WaitListBean> it3 = waitList.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            break;
                        }
                        PLVLinkMicJoinStatus.WaitListBean next2 = it3.next();
                        if (linkMicId != null && linkMicId.equals(next2.getUserId())) {
                            z2 = true;
                            break;
                        }
                    }
                }
                if (!z2) {
                    linkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_IDLE);
                    this.rtcJoinMap.remove(linkMicItemDataBean.getLinkMicId());
                    z3 = true;
                }
            }
        }
        return z3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNetworkForGuestAutoLinkMic(boolean isNetworkConnect) {
        boolean zBooleanValue = ((Boolean) PLVSugarUtil.getOrDefault(this.isNetworkConnect, Boolean.valueOf(isNetworkConnect))).booleanValue();
        Boolean boolValueOf = Boolean.valueOf(isNetworkConnect);
        this.isNetworkConnect = boolValueOf;
        if (zBooleanValue || !boolValueOf.booleanValue()) {
            return;
        }
        boolean zEquals = "guest".equals(this.userType);
        boolean zIsAutoLinkToGuest = this.liveRoomDataManager.getConfig().isAutoLinkToGuest();
        boolean zBooleanValue2 = ((Boolean) PLVSugarUtil.getOrDefault(this.streamerData.getStreamerStatus().getValue(), Boolean.FALSE)).booleanValue();
        if (zEquals && zIsAutoLinkToGuest && zBooleanValue2) {
            guestTryJoinLinkMic();
        }
    }

    private void updateUserPermissionStatus(PLVLinkMicItemDataBean linkMicItemDataBean, PLVJoinInfoEvent joinInfoEvent) {
        Pair<Integer, PLVMemberItemDataBean> memberItemWithLinkMicId = getMemberItemWithLinkMicId(linkMicItemDataBean.getLinkMicId());
        if (memberItemWithLinkMicId != null) {
            final boolean zIsSpeaker = joinInfoEvent.getClassStatus().isSpeaker();
            if (((PLVMemberItemDataBean) memberItemWithLinkMicId.second).getLinkMicItemDataBean().isHasSpeaker() != zIsSpeaker) {
                ((PLVMemberItemDataBean) memberItemWithLinkMicId.second).getLinkMicItemDataBean().setHasSpeaker(zIsSpeaker);
                final PLVSocketUserBean socketUserBean = ((PLVMemberItemDataBean) memberItemWithLinkMicId.second).getSocketUserBean();
                callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.26
                    @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onSetPermissionChange("speaker", zIsSpeaker, true, socketUserBean);
                    }
                });
            }
            if (zIsSpeaker) {
                onFirstScreenChange(linkMicItemDataBean.getLinkMicId(), true);
            }
        }
    }

    public void callUpdateGuestMediaStatus(boolean isMute, boolean isAudio) {
        this.streamerManager.getLinkMicUid();
        if (isAudio) {
            enableRecordingAudioVolume(!isMute);
        } else {
            enableLocalVideo(!isMute);
            this.streamerManager.enableLocalCamera(!isMute);
        }
    }

    public void callUpdateGuestStatus(boolean joinRTC) {
        String linkMicUid = this.streamerManager.getLinkMicUid();
        final int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= this.streamerList.size()) {
                break;
            }
            PLVLinkMicItemDataBean pLVLinkMicItemDataBean = this.streamerList.get(i3);
            if (linkMicUid.equals(pLVLinkMicItemDataBean.getLinkMicId())) {
                pLVLinkMicItemDataBean.setStatus(joinRTC ? PLVLinkMicItemDataBean.STATUS_RTC_JOIN : PLVLinkMicItemDataBean.STATUS_IDLE);
                i2 = i3;
            } else {
                i3++;
            }
        }
        callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.34
            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
            public void run(@NonNull @NotNull IPLVStreamerContract.IStreamerView view) {
                view.onGuestRTCStatusChanged(i2);
            }
        });
    }

    public void callUpdateSortMemberList() {
        callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.31
            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onUpdateMemberListData(SortMemberListUtils.sort(PLVStreamerPresenter.this.memberList));
            }
        });
    }

    public void callUserMuteAudio(final String linkMicId, final boolean isMute) {
        Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = getLinkMicItemWithLinkMicId(linkMicId);
        if (linkMicItemWithLinkMicId == null) {
            return;
        }
        ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).setMuteAudio(isMute);
        final int iIntValue = ((Integer) linkMicItemWithLinkMicId.first).intValue();
        Pair<Integer, PLVMemberItemDataBean> memberItemWithLinkMicId = getMemberItemWithLinkMicId(linkMicId);
        if (memberItemWithLinkMicId == null) {
            return;
        }
        final int iIntValue2 = ((Integer) memberItemWithLinkMicId.first).intValue();
        callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.32
            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onUserMuteAudio(linkMicId, isMute, iIntValue, iIntValue2);
            }
        });
    }

    public void callUserMuteVideo(final String linkMicId, final boolean isMute) {
        Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = getLinkMicItemWithLinkMicId(linkMicId);
        if (linkMicItemWithLinkMicId == null) {
            return;
        }
        ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).setMuteVideo(isMute);
        final int iIntValue = ((Integer) linkMicItemWithLinkMicId.first).intValue();
        Pair<Integer, PLVMemberItemDataBean> memberItemWithLinkMicId = getMemberItemWithLinkMicId(linkMicId);
        if (memberItemWithLinkMicId == null) {
            return;
        }
        final int iIntValue2 = ((Integer) memberItemWithLinkMicId.first).intValue();
        callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.33
            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onUserMuteVideo(linkMicId, isMute, iIntValue, iIntValue2);
            }
        });
        updateMixLayoutUsers();
    }

    public void callbackToView(ViewRunnable runnable) {
        List<IPLVStreamerContract.IStreamerView> list = this.iStreamerViews;
        if (list != null) {
            for (IPLVStreamerContract.IStreamerView iStreamerView : list) {
                if (iStreamerView != null && runnable != null) {
                    runnable.run(iStreamerView);
                }
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void closeAllUserLinkMic() {
        PLVLinkMicEventSender.getInstance().closeAllUserLinkMic(this.liveRoomDataManager.getSessionId(), null);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void controlUserLinkMic(int position, boolean isAllowJoin) {
        if (position < 0 || position >= this.memberList.size()) {
            return;
        }
        PLVMemberItemDataBean pLVMemberItemDataBean = this.memberList.get(position);
        PLVSocketUserBean socketUserBean = pLVMemberItemDataBean.getSocketUserBean();
        final PLVLinkMicItemDataBean linkMicItemDataBean = pLVMemberItemDataBean.getLinkMicItemDataBean();
        if (!isAllowJoin) {
            if (linkMicItemDataBean != null) {
                PLVLinkMicEventSender.getInstance().closeUserLinkMic(linkMicItemDataBean.getLinkMicId(), null);
            }
        } else if (this.rtcJoinMap.size() >= PLVStreamerInnerDataTransfer.getInstance().getInteractNumLimit()) {
            callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.7
                @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                    view.onReachTheInteractNumLimit();
                }
            });
        } else {
            PLVLinkMicEventSender.getInstance().responseUserLinkMic(socketUserBean, new IPLVLinkMicEventSender.PLVSMainCallAck() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.8
                @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.PLVSMainCallAck
                public void onCall(Object... args) {
                    PLVLinkMicItemDataBean pLVLinkMicItemDataBean = linkMicItemDataBean;
                    if (pLVLinkMicItemDataBean != null) {
                        pLVLinkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_JOINING);
                        PLVStreamerPresenter.this.startJoinTimeoutCount(linkMicItemDataBean);
                        PLVStreamerPresenter.this.callUpdateSortMemberList();
                    }
                }
            });
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void controlUserLinkMicInLinkMicList(int position, boolean isAllowJoin) {
        Pair<Integer, PLVMemberItemDataBean> memberItemWithLinkMicId;
        if (position < 0 || position >= this.streamerList.size() || (memberItemWithLinkMicId = getMemberItemWithLinkMicId(this.streamerList.get(position).getLinkMicId())) == null) {
            return;
        }
        controlUserLinkMic(((Integer) memberItemWithLinkMicId.first).intValue(), isAllowJoin);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public SurfaceView createRenderView(Context context) {
        return this.streamerManager.createRendererView(context);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void destroy() {
        PLVSocketUserBean pLVSocketUserBean = this.currentSocketUserBean;
        if (pLVSocketUserBean != null && pLVSocketUserBean.getUserId() != null && !this.currentSocketUserBean.isTeacher()) {
            setUserPermissionSpeaker(this.currentSocketUserBean.getUserId(), false, null);
        }
        this.streamerInitState = 1;
        this.streamerStatus = 3;
        this.isRecoverStream = false;
        this.handler.removeCallbacksAndMessages(null);
        this.streamerMsgHandler.destroy();
        dispose(this.listUsersDisposable);
        dispose(this.listUserTimerDisposable);
        dispose(this.linkMicListTimerDisposable);
        if (this.beautySwitchStateObserver != null) {
            this.beautyRepo.getBeautySwitchLiveData().removeObserver(this.beautySwitchStateObserver);
            this.beautySwitchStateObserver = null;
        }
        this.streamerList.clear();
        this.timerToShowNetBroken.destroy();
        List<IPLVStreamerContract.IStreamerView> list = this.iStreamerViews;
        if (list != null) {
            list.clear();
        }
        if (!"guest".equals(this.userType)) {
            PLVLinkMicEventSender.getInstance().closeLinkMic();
        }
        this.streamerManager.destroy();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public boolean enableLocalVideo(final boolean enable) {
        this.curEnableLocalVideo = enable;
        if (!isInitStreamerManager()) {
            return false;
        }
        this.streamerManager.enableLocalCamera(enable);
        this.streamerData.postEnableVideo(enable);
        callUserMuteVideo(this.streamerManager.getLinkMicUid(), !enable);
        return true;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public boolean enableRecordingAudioVolume(final boolean enable) {
        this.curEnableRecordingAudioVolume = enable;
        if (!isInitStreamerManager()) {
            return false;
        }
        this.streamerManager.enableLocalMicrophone(enable);
        this.streamerData.postEnableAudio(enable);
        callUserMuteAudio(this.streamerManager.getLinkMicUid(), !enable);
        return true;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public boolean enableTorch(boolean enable) {
        if (isInitStreamerManager()) {
            return this.streamerManager.enableTorch(enable);
        }
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void exitShareScreen() {
        if (this.streamerManager.isScreenSharing()) {
            this.streamerManager.exitScreenCapture();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public int getBitrate() {
        return Math.min(this.curBitrate, getMaxBitrate());
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    @NonNull
    public PLVStreamerData getData() {
        return this.streamerData;
    }

    public Pair<Integer, PLVLinkMicItemDataBean> getLinkMicItemWithLinkMicId(@Nullable String linkMicId) {
        for (int i2 = 0; i2 < this.streamerList.size(); i2++) {
            PLVLinkMicItemDataBean pLVLinkMicItemDataBean = this.streamerList.get(i2);
            if (linkMicId != null && linkMicId.equals(pLVLinkMicItemDataBean.getLinkMicId())) {
                return new Pair<>(Integer.valueOf(i2), pLVLinkMicItemDataBean);
            }
        }
        return null;
    }

    public IPLVLiveRoomDataManager getLiveRoomDataManager() {
        return this.liveRoomDataManager;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public int getMaxBitrate() {
        return PLVStreamerInnerDataTransfer.getInstance().getSupportedMaxBitrate();
    }

    public Pair<Integer, PLVMemberItemDataBean> getMemberItemWithLinkMicId(String linkMicId) {
        for (int i2 = 0; i2 < this.memberList.size(); i2++) {
            PLVMemberItemDataBean pLVMemberItemDataBean = this.memberList.get(i2);
            PLVLinkMicItemDataBean linkMicItemDataBean = pLVMemberItemDataBean.getLinkMicItemDataBean();
            if (linkMicItemDataBean != null) {
                String linkMicId2 = linkMicItemDataBean.getLinkMicId();
                if (linkMicId != null && linkMicId.equals(linkMicId2)) {
                    return new Pair<>(Integer.valueOf(i2), pLVMemberItemDataBean);
                }
            }
        }
        return null;
    }

    public Pair<Integer, PLVMemberItemDataBean> getMemberItemWithUserId(String userId) {
        for (int i2 = 0; i2 < this.memberList.size(); i2++) {
            PLVMemberItemDataBean pLVMemberItemDataBean = this.memberList.get(i2);
            PLVSocketUserBean socketUserBean = pLVMemberItemDataBean.getSocketUserBean();
            if (socketUserBean != null) {
                String userId2 = socketUserBean.getUserId();
                if (userId != null && userId.equals(userId2)) {
                    return new Pair<>(Integer.valueOf(i2), pLVMemberItemDataBean);
                }
            }
        }
        return null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public int getNetworkQuality() {
        return this.streamerManager.getCurrentNetQuality();
    }

    public IPLVStreamerManager getStreamerManager() {
        return this.streamerManager;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public int getStreamerStatus() {
        return this.streamerStatus;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void guestTryJoinLinkMic() {
        if (this.liveRoomDataManager.getConfig().isAutoLinkToGuest()) {
            PLVLinkMicEventSender.getInstance().guestAutoLinkMic(3, new IPLVLinkMicEventSender.IPLVGuestAutoLinkMicListener() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.10
                @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.IPLVGuestAutoLinkMicListener
                public void onAutoLinkMic() {
                    if (PLVStreamerPresenter.this.streamerInitState == 3) {
                        PLVStreamerPresenter.this.streamerManager.joinChannel();
                    } else {
                        PLVStreamerPresenter.this.joinChannelRunnable = new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.10.1
                            @Override // java.lang.Runnable
                            public void run() {
                                PLVStreamerPresenter.this.streamerManager.joinChannel();
                            }
                        };
                    }
                    PLVStreamerPresenter.this.requestLinkMicListApiTimer();
                }

                @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.IPLVGuestAutoLinkMicListener
                public void onHangupByTeacher() {
                    PLVStreamerPresenter.this.streamerManager.switchRoleToAudience();
                    PLVStreamerPresenter.this.callUpdateGuestStatus(false);
                }

                @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.IPLVGuestAutoLinkMicListener
                public void onInviteByTeacher() {
                    PLVStreamerPresenter.this.streamerManager.switchRoleToBroadcaster();
                    PLVStreamerPresenter.this.callUpdateGuestStatus(true);
                }

                @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.IPLVGuestAutoLinkMicListener
                public void onTimeout() {
                    PLVCommonLog.e(PLVStreamerPresenter.TAG, "嘉宾上麦超时！");
                    PLVStreamerPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.10.2
                        @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                        public void run(@NonNull @NotNull IPLVStreamerContract.IStreamerView view) {
                            view.onStreamerError(1, new Exception("嘉宾上麦超时！"));
                        }
                    });
                }
            });
        } else {
            PLVCommonLog.d(TAG, "暂不支持手动上麦的嘉宾");
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void init() {
        this.streamerInitState = 2;
        if ("guest".equals(this.userType)) {
            pollLiveStatus();
            this.streamerManager.disableAutoJoinChannel();
        }
        this.streamerManager.initEngine(new PLVLinkMicEngineParam().setChannelId(this.liveRoomDataManager.getConfig().getChannelId()).setViewerId(this.liveRoomDataManager.getConfig().getUser().getViewerId()).setViewerType(this.liveRoomDataManager.getConfig().getUser().getViewerType()).setNickName(this.liveRoomDataManager.getConfig().getUser().getViewerName()), new PLVStreamerListener() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.2
            @Override // com.plv.livescenes.streamer.listener.PLVStreamerListener
            public void onStreamerEngineCreatedSuccess() {
                PLVCommonLog.d(PLVStreamerPresenter.TAG, "推流和连麦初始化成功");
                PLVStreamerPresenter.this.streamerInitState = 3;
                PLVStreamerPresenter.this.streamerManager.setOnlyAudio(PLVStreamerPresenter.this.liveRoomDataManager.isOnlyAudio());
                PLVLinkMicItemDataBean pLVLinkMicItemDataBean = new PLVLinkMicItemDataBean();
                pLVLinkMicItemDataBean.setMuteAudio(!PLVStreamerPresenter.this.curEnableRecordingAudioVolume);
                pLVLinkMicItemDataBean.setMuteVideo(!PLVStreamerPresenter.this.curEnableLocalVideo);
                if ("guest".equals(PLVStreamerPresenter.this.userType)) {
                    pLVLinkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_IDLE);
                } else {
                    pLVLinkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_RTC_JOIN);
                }
                pLVLinkMicItemDataBean.setLinkMicId(PLVStreamerPresenter.this.streamerManager.getLinkMicUid());
                pLVLinkMicItemDataBean.setActor(PLVStreamerPresenter.this.liveRoomDataManager.getConfig().getUser().getActor());
                pLVLinkMicItemDataBean.setNick(PLVStreamerPresenter.this.liveRoomDataManager.getConfig().getUser().getViewerName());
                pLVLinkMicItemDataBean.setUserId(PLVStreamerPresenter.this.liveRoomDataManager.getConfig().getUser().getViewerId());
                pLVLinkMicItemDataBean.setUserType(PLVStreamerPresenter.this.liveRoomDataManager.getConfig().getUser().getViewerType());
                if ("teacher".equals(PLVStreamerPresenter.this.userType)) {
                    pLVLinkMicItemDataBean.setFirstScreen(true);
                    PLVStreamerPresenter pLVStreamerPresenter = PLVStreamerPresenter.this;
                    pLVStreamerPresenter.lastFirstScreenUserId = pLVStreamerPresenter.streamerManager.getLinkMicUid();
                }
                PLVStreamerPresenter.this.streamerList.add(0, pLVLinkMicItemDataBean);
                Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId = PLVStreamerPresenter.this.getMemberItemWithUserId(pLVLinkMicItemDataBean.getLinkMicId());
                if (memberItemWithUserId != null && ((PLVMemberItemDataBean) memberItemWithUserId.second).getLinkMicItemDataBean() == null) {
                    ((PLVMemberItemDataBean) memberItemWithUserId.second).setLinkMicItemDataBean(pLVLinkMicItemDataBean);
                }
                PLVStreamerPresenter.this.updateLinkMicCount();
                PLVStreamerPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.2.1
                    @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onStreamerEngineCreatedSuccess(PLVStreamerPresenter.this.streamerManager.getLinkMicUid(), PLVStreamerPresenter.this.streamerList);
                    }
                });
                PLVStreamerPresenter pLVStreamerPresenter2 = PLVStreamerPresenter.this;
                pLVStreamerPresenter2.setBitrate(pLVStreamerPresenter2.curBitrate);
                PLVStreamerPresenter pLVStreamerPresenter3 = PLVStreamerPresenter.this;
                pLVStreamerPresenter3.setCameraDirection(pLVStreamerPresenter3.curCameraFront);
                PLVStreamerPresenter pLVStreamerPresenter4 = PLVStreamerPresenter.this;
                pLVStreamerPresenter4.setPushPictureResolutionType(pLVStreamerPresenter4.pushPictureResolution);
                PLVStreamerPresenter pLVStreamerPresenter5 = PLVStreamerPresenter.this;
                pLVStreamerPresenter5.setPushResolutionRatio(pLVStreamerPresenter5.pushResolutionRatio);
                PLVStreamerPresenter pLVStreamerPresenter6 = PLVStreamerPresenter.this;
                pLVStreamerPresenter6.enableLocalVideo(pLVStreamerPresenter6.curEnableLocalVideo);
                PLVStreamerPresenter pLVStreamerPresenter7 = PLVStreamerPresenter.this;
                pLVStreamerPresenter7.enableRecordingAudioVolume(pLVStreamerPresenter7.curEnableRecordingAudioVolume);
                PLVStreamerPresenter pLVStreamerPresenter8 = PLVStreamerPresenter.this;
                pLVStreamerPresenter8.setFrontCameraMirror(pLVStreamerPresenter8.isFrontMirror);
                PLVStreamerPresenter.this.initStreamerListener();
                if (PLVStreamerPresenter.this.streamerStatus == 1) {
                    PLVStreamerPresenter.this.streamerManager.startLiveStream();
                }
                if (PLVStreamerPresenter.this.joinChannelRunnable != null) {
                    PLVStreamerPresenter.this.joinChannelRunnable.run();
                }
            }

            @Override // com.plv.livescenes.streamer.listener.PLVStreamerListener
            public void onStreamerError(final int errorCode, final Throwable throwable) {
                PLVCommonLog.e(PLVStreamerPresenter.TAG, "推流和连麦模块错误：errorCode=" + errorCode);
                PLVCommonLog.exception(throwable);
                if (PLVStreamerPresenter.this.streamerInitState != 3) {
                    PLVStreamerPresenter.this.streamerInitState = 1;
                }
                if (PLVStreamerPresenter.this.streamerStatus != 3) {
                    PLVStreamerPresenter.this.stopLiveStream();
                    PLVStreamerPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.2.2
                        @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                        public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                            view.onStreamerError(errorCode, throwable);
                        }
                    });
                }
            }
        });
        this.streamerManager.setShareScreenListener(new IPLVScreenShareListener() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.3
            @Override // com.plv.linkmic.screenshare.IPLVScreenShareListener
            public void onScreenShare(final boolean isShare) {
                if (PLVStreamerPresenter.this.currentSocketUserBean != null) {
                    PLVLinkMicEventSender.getInstance().sendScreenShareEvent(PLVStreamerPresenter.this.currentSocketUserBean, PLVStreamerPresenter.this.liveRoomDataManager.getSessionId(), isShare, null);
                }
                PLVStreamerPresenter.this.streamerData.postEnableShareScreen(isShare);
                PLVStreamerPresenter pLVStreamerPresenter = PLVStreamerPresenter.this;
                Pair<Integer, PLVMemberItemDataBean> memberItemWithLinkMicId = pLVStreamerPresenter.getMemberItemWithLinkMicId(pLVStreamerPresenter.streamerManager.getLinkMicUid());
                PLVStreamerPresenter pLVStreamerPresenter2 = PLVStreamerPresenter.this;
                Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = pLVStreamerPresenter2.getLinkMicItemWithLinkMicId(pLVStreamerPresenter2.streamerManager.getLinkMicUid());
                if (memberItemWithLinkMicId != null) {
                    ((PLVMemberItemDataBean) memberItemWithLinkMicId.second).getLinkMicItemDataBean().setScreenShare(isShare);
                }
                if (linkMicItemWithLinkMicId != null) {
                    ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).setScreenShare(isShare);
                }
                final int iIntValue = linkMicItemWithLinkMicId == null ? 0 : ((Integer) linkMicItemWithLinkMicId.first).intValue();
                PLVStreamerPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.3.1
                    @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onScreenShareChange(iIntValue, isShare, IPLVScreenShareListener.PLV_SCREEN_SHARE_OK);
                    }
                });
                PLVStreamerPresenter.this.updateMixLayoutWhenScreenShare(isShare, ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).getLinkMicId());
            }

            @Override // com.plv.linkmic.screenshare.IPLVScreenShareListener
            public void onScreenShareError(final int errorCode) {
                PLVStreamerPresenter pLVStreamerPresenter = PLVStreamerPresenter.this;
                Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = pLVStreamerPresenter.getLinkMicItemWithLinkMicId(pLVStreamerPresenter.streamerManager.getLinkMicUid());
                final int iIntValue = ((Integer) linkMicItemWithLinkMicId.first).intValue();
                ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).setScreenShare(false);
                PLVStreamerPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.3.2
                    @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onScreenShareChange(iIntValue, false, errorCode);
                    }
                });
                PLVStreamerPresenter.this.updateMixLayoutWhenScreenShare(false, ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).getLinkMicId());
                PLVStreamerPresenter.this.streamerData.postEnableShareScreen(false);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public boolean isRecoverStream() {
        return this.isRecoverStream;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public boolean isScreenSharing() {
        return this.streamerManager.isScreenSharing();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void muteAllUserAudio(final boolean isMute) {
        for (int i2 = 0; i2 < this.memberList.size(); i2++) {
            PLVLinkMicItemDataBean linkMicItemDataBean = this.memberList.get(i2).getLinkMicItemDataBean();
            if (linkMicItemDataBean != null && linkMicItemDataBean.isRtcJoinStatus() && !isMyLinkMicId(linkMicItemDataBean.getLinkMicId())) {
                muteUserMedia(i2, false, isMute);
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void muteUserMedia(int position, final boolean isVideoType, final boolean isMute) {
        if (position < 0 || position >= this.memberList.size()) {
            return;
        }
        PLVMemberItemDataBean pLVMemberItemDataBean = this.memberList.get(position);
        PLVSocketUserBean socketUserBean = pLVMemberItemDataBean.getSocketUserBean();
        final PLVLinkMicItemDataBean linkMicItemDataBean = pLVMemberItemDataBean.getLinkMicItemDataBean();
        PLVLinkMicEventSender.getInstance().muteUserMedia(socketUserBean, this.liveRoomDataManager.getSessionId(), isVideoType, isMute, new IPLVLinkMicEventSender.PLVSMainCallAck() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.9
            @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.PLVSMainCallAck
            public void onCall(Object... args) {
                PLVLinkMicItemDataBean pLVLinkMicItemDataBean = linkMicItemDataBean;
                if (pLVLinkMicItemDataBean != null) {
                    if (isVideoType) {
                        pLVLinkMicItemDataBean.setMuteVideo(isMute);
                        PLVStreamerPresenter.this.callUserMuteVideo(linkMicItemDataBean.getLinkMicId(), isMute);
                    } else {
                        pLVLinkMicItemDataBean.setMuteAudio(isMute);
                        PLVStreamerPresenter.this.callUserMuteAudio(linkMicItemDataBean.getLinkMicId(), isMute);
                    }
                }
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void muteUserMediaInLinkMicList(int position, boolean isVideoType, boolean isMute) {
        Pair<Integer, PLVMemberItemDataBean> memberItemWithLinkMicId;
        if (position < 0 || position >= this.streamerList.size() || (memberItemWithLinkMicId = getMemberItemWithLinkMicId(this.streamerList.get(position).getLinkMicId())) == null) {
            return;
        }
        muteUserMedia(((Integer) memberItemWithLinkMicId.first).intValue(), isVideoType, isMute);
    }

    public void onCurrentSpeakerChanged(final String type, final boolean isGranted, final boolean isCurrentUser, final PLVSocketUserBean user) {
        callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.38
            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onSetPermissionChange(type, isGranted, isCurrentUser, user);
            }
        });
        if (type != null && type.equals("speaker") && isGranted) {
            PLVSocketUserBean pLVSocketUserBean = this.currentSpeakerPermissionUser;
            if (pLVSocketUserBean == null) {
                PLVSocketUserBean pLVSocketUserBean2 = new PLVSocketUserBean();
                this.currentSpeakerPermissionUser = pLVSocketUserBean2;
                if (user != null) {
                    pLVSocketUserBean2.setUserId(user.getUserId());
                    return;
                }
                return;
            }
            if (pLVSocketUserBean.getUserId() == null || user == null || user.getUserId() == null || this.currentSpeakerPermissionUser.getUserId().equals(user.getUserId())) {
                return;
            }
            final String userId = this.currentSpeakerPermissionUser.getUserId();
            PLVSugarUtil.catchingNull(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.39
                @Override // java.lang.Runnable
                public void run() {
                    ((PLVMemberItemDataBean) PLVStreamerPresenter.this.getMemberItemWithLinkMicId(userId).second).getLinkMicItemDataBean().setHasSpeaker(false);
                }
            });
            PLVSugarUtil.catchingNull(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.40
                @Override // java.lang.Runnable
                public void run() {
                    ((PLVLinkMicItemDataBean) PLVStreamerPresenter.this.getLinkMicItemWithLinkMicId(userId).second).setHasSpeaker(false);
                }
            });
            final boolean zIsMyLinkMicId = isMyLinkMicId(userId);
            if (zIsMyLinkMicId) {
                PLVUserAbilityManager.myAbility().removeRole(PLVUserRole.STREAMER_GRANTED_SPEAKER_USER);
            }
            callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.41
                @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                    view.onSetPermissionChange(type, false, zIsMyLinkMicId, PLVStreamerPresenter.this.currentSpeakerPermissionUser);
                }
            });
            this.currentSpeakerPermissionUser.setUserId(user.getUserId());
        }
    }

    public void onFirstScreenChange(final String firstScreenUserId, final boolean isFirstScreen) {
        String str = this.lastFirstScreenUserId;
        if (str == null || !str.equals(firstScreenUserId)) {
            PLVCommonLog.i(TAG, "onFirstScreenChange: " + firstScreenUserId + ", isFirstScreen: " + isFirstScreen);
            PLVSugarUtil.catchingNull(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.35
                @Override // java.lang.Runnable
                public void run() {
                    PLVStreamerPresenter pLVStreamerPresenter = PLVStreamerPresenter.this;
                    ((PLVLinkMicItemDataBean) pLVStreamerPresenter.getLinkMicItemWithLinkMicId(pLVStreamerPresenter.lastFirstScreenUserId).second).setFirstScreen(false);
                }
            });
            PLVSugarUtil.catchingNull(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.36
                @Override // java.lang.Runnable
                public void run() {
                    ((PLVLinkMicItemDataBean) PLVStreamerPresenter.this.getLinkMicItemWithLinkMicId(firstScreenUserId).second).setFirstScreen(isFirstScreen);
                }
            });
            this.lastFirstScreenUserId = firstScreenUserId;
            SortGuestLinkMicListUtils.sort(this.streamerList);
            updateMixLayoutUsers();
            callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.37
                @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                    view.onFirstScreenChange(firstScreenUserId, isFirstScreen);
                }
            });
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void registerView(@NonNull IPLVStreamerContract.IStreamerView v2) {
        if (this.iStreamerViews == null) {
            this.iStreamerViews = new ArrayList();
        }
        if (!this.iStreamerViews.contains(v2)) {
            this.iStreamerViews.add(v2);
        }
        v2.setPresenter(this);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void releaseRenderView(SurfaceView renderView) {
        this.streamerManager.releaseRenderView(renderView);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void requestMemberList() {
        requestListUsersApi();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void requestShareScreen(Activity activity) {
        if (this.streamerManager.isScreenSharing()) {
            return;
        }
        this.streamerManager.requestScreenCapture(activity);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void setBitrate(int bitrate) {
        this.curBitrate = Math.min(bitrate, getMaxBitrate());
        if (isInitStreamerManager()) {
            this.streamerManager.setBitrate(this.curBitrate);
            this.streamerData.postCurBitrate(this.curBitrate);
            saveBitrate();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public boolean setCameraDirection(final boolean front) {
        this.curCameraFront = front;
        if (!isInitStreamerManager()) {
            return false;
        }
        if (this.curCameraFront) {
            this.streamerManager.setLocalPreviewMirror(this.isFrontMirror);
            this.streamerManager.setLocalPushMirror(this.isFrontMirror);
        } else {
            this.streamerManager.setLocalPreviewMirror(false);
            this.streamerManager.setLocalPushMirror(false);
        }
        this.streamerManager.switchCamera(front);
        this.streamerData.postIsFrontCamera(front);
        callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.4
            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                PLVStreamerPresenter pLVStreamerPresenter = PLVStreamerPresenter.this;
                Pair<Integer, PLVMemberItemDataBean> memberItemWithLinkMicId = pLVStreamerPresenter.getMemberItemWithLinkMicId(pLVStreamerPresenter.streamerManager.getLinkMicUid());
                if (memberItemWithLinkMicId == null) {
                    return;
                }
                ((PLVMemberItemDataBean) memberItemWithLinkMicId.second).setFrontCamera(front);
                view.onCameraDirection(front, ((Integer) memberItemWithLinkMicId.first).intValue());
            }
        });
        return true;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void setDocumentAndStreamerViewPosition(boolean documentInMainScreen) {
        if (!((Boolean) PLVSugarUtil.getOrDefault(this.streamerData.getStreamerStatus().getValue(), Boolean.FALSE)).booleanValue() || PLVUserAbilityManager.myAbility().notHasAbility(PLVUserAbility.STREAMER_DOCUMENT_ALLOW_SWITCH_WITH_FIRST_SCREEN_TO_ALL_USER)) {
            return;
        }
        PLVLinkMicEventSender.getInstance().setDocumentStreamerViewPosition(documentInMainScreen, this.liveRoomDataManager.getSessionId());
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void setFrontCameraMirror(final boolean enable) {
        if (isInitStreamerManager() && this.curCameraFront) {
            this.isFrontMirror = enable;
            this.streamerData.postIsFrontMirrorMode(enable);
            this.handler.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.5
                @Override // java.lang.Runnable
                public void run() {
                    PLVStreamerPresenter.this.streamerManager.setLocalPreviewMirror(enable);
                    PLVStreamerPresenter.this.streamerManager.setLocalPushMirror(enable);
                }
            });
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void setMixLayoutType(int mixLayoutType) {
        if (isInitStreamerManager()) {
            this.streamerManager.setMixLayoutType(mixLayoutType);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void setPushPictureResolutionType(int type) {
        this.pushPictureResolution = type;
        if (isInitStreamerManager()) {
            this.streamerManager.setPushPictureResolutionType(this.pushPictureResolution);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void setPushResolutionRatio(PLVLinkMicConstant.PushResolutionRatio resolutionRatio) {
        this.pushResolutionRatio = resolutionRatio;
        if (isInitStreamerManager()) {
            this.streamerData.postPushResolutionRatio(this.pushResolutionRatio);
            this.streamerManager.setPushResolutionRatio(this.pushResolutionRatio);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void setRecoverStream(boolean recoverStream) {
        this.isRecoverStream = recoverStream;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void setUserPermissionSpeaker(final String userId, final boolean isSetPermission, final Ack ack) {
        if ((PLVUserAbilityManager.myAbility().hasRole(PLVUserRole.STREAMER_TEACHER) || PLVUserAbilityManager.myAbility().hasRole(PLVUserRole.STREAMER_GRANTED_SPEAKER_USER)) && this.currentSocketUserBean != null) {
            final String str = (String) PLVSugarUtil.nullable(new PLVSugarUtil.Supplier<String>() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.11
                @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Supplier
                public String get() {
                    return ((PLVMemberItemDataBean) PLVStreamerPresenter.this.getMemberItemWithUserId(userId).second).getLinkMicItemDataBean().getUserId();
                }
            });
            if (str == null && isSetPermission) {
                return;
            }
            final String sessionId = this.liveRoomDataManager.getSessionId();
            if (this.currentSpeakerPermissionUser == null) {
                PLVSocketUserBean pLVSocketUserBean = new PLVSocketUserBean();
                this.currentSpeakerPermissionUser = pLVSocketUserBean;
                pLVSocketUserBean.setUserId(this.currentSocketUserBean.getUserId());
            }
            if (!isSetPermission) {
                this.currentSpeakerPermissionUser.setUserId(str);
            }
            PLVLinkMicEventSender.getInstance().setSpeakerPermission(this.currentSpeakerPermissionUser, sessionId, false, new Ack() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.12
                @Override // io.socket.client.Ack
                public void call(Object... objects) {
                    if (isSetPermission) {
                        PLVStreamerPresenter.this.currentSpeakerPermissionUser.setUserId(str);
                        PLVLinkMicEventSender.getInstance().setSpeakerPermission(PLVStreamerPresenter.this.currentSpeakerPermissionUser, sessionId, true, ack);
                    } else {
                        PLVStreamerPresenter.this.currentSpeakerPermissionUser.setUserId(PLVStreamerPresenter.this.findChannelTeacherUserId());
                        if (PLVStreamerPresenter.this.currentSpeakerPermissionUser.getUserId() != null) {
                            PLVLinkMicEventSender.getInstance().setSpeakerPermission(PLVStreamerPresenter.this.currentSpeakerPermissionUser, sessionId, true, ack);
                        } else {
                            Ack ack2 = ack;
                            if (ack2 != null) {
                                ack2.call(objects);
                            }
                        }
                    }
                    PLVLinkMicEventSender.getInstance().setSwitchFirstView(PLVStreamerPresenter.this.currentSpeakerPermissionUser, null);
                }
            });
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void setupRenderView(SurfaceView renderView, String linkMicId) {
        if (!isMyLinkMicId(linkMicId)) {
            this.streamerManager.setupRemoteVideo(renderView, linkMicId);
        } else if (this.liveRoomDataManager.isOnlyAudio()) {
            this.streamerManager.setupLocalVideo(renderView, 10);
        } else {
            this.streamerManager.setupLocalVideo(renderView);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void startLiveStream() {
        this.streamerStatus = 1;
        int i2 = this.streamerInitState;
        if (i2 == 1) {
            PLVCommonLog.d(TAG, "推流和连麦开始初始化");
            init();
        } else if (i2 == 2) {
            PLVCommonLog.d(TAG, "推流和连麦初始化中");
        } else {
            if (i2 != 3) {
                return;
            }
            this.streamerManager.startLiveStream(this.isRecoverStream);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void stopLiveStream() {
        this.isRecoverStream = false;
        this.streamerStatus = 3;
        this.streamerManager.stopLiveStream();
        this.streamerData.postStreamerStatus(false);
        callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.6
            @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onStatesToStreamEnded();
            }
        });
        PLVSocketUserBean pLVSocketUserBean = this.currentSpeakerPermissionUser;
        if (pLVSocketUserBean != null && this.currentSocketUserBean != null && !pLVSocketUserBean.getUserId().equals(this.currentSocketUserBean.getUserId())) {
            setUserPermissionSpeaker(this.currentSpeakerPermissionUser.getUserId(), false, null);
        }
        PLVLinkMicEventSender.getInstance().closeLinkMic();
        PLVLinkMicEventSender.getInstance().emitFinishClassEvent(this.streamerManager.getLinkMicUid());
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void unregisterView(IPLVStreamerContract.IStreamerView v2) {
        List<IPLVStreamerContract.IStreamerView> list = this.iStreamerViews;
        if (list != null) {
            list.remove(v2);
        }
    }

    public void updateLinkMicCount() {
        this.streamerData.postLinkMicCount(this.streamerList.size());
    }

    public void updateLinkMicMediaStatus(PLVLinkMicItemDataBean rtcJoinLinkMicItem, PLVLinkMicItemDataBean linkMicItemDataBean) {
        if (rtcJoinLinkMicItem == null || linkMicItemDataBean == null) {
            return;
        }
        if (rtcJoinLinkMicItem.getMuteVideoInRtcJoinList() != null) {
            linkMicItemDataBean.setMuteVideo(rtcJoinLinkMicItem.isMuteVideo());
        } else if (linkMicItemDataBean.isGuest()) {
            linkMicItemDataBean.setMuteVideo(false);
        } else {
            linkMicItemDataBean.setMuteVideo(!PLVLinkMicEventSender.getInstance().isVideoLinkMicType());
        }
        if (rtcJoinLinkMicItem.getMuteAudioInRtcJoinList() != null) {
            linkMicItemDataBean.setMuteAudio(rtcJoinLinkMicItem.isMuteAudio());
        } else {
            linkMicItemDataBean.setMuteAudio(false);
        }
    }

    public boolean updateMemberListItemInfo(PLVSocketUserBean socketUserBean, PLVLinkMicItemDataBean linkMicItemDataBean, boolean isJoinList) {
        return updateMemberListItemInfo(socketUserBean, linkMicItemDataBean, isJoinList, false);
    }

    public boolean updateMemberListLinkMicStatusWithRtcJoinList(PLVMemberItemDataBean item, final String linkMicUid) {
        final PLVLinkMicItemDataBean linkMicItemDataBean = item.getLinkMicItemDataBean();
        boolean z2 = false;
        if (linkMicItemDataBean == null) {
            return false;
        }
        Iterator<Map.Entry<String, PLVLinkMicItemDataBean>> it = this.rtcJoinMap.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Map.Entry<String, PLVLinkMicItemDataBean> next = it.next();
            String key = next.getKey();
            if (linkMicUid != null && linkMicUid.equals(key)) {
                if (!linkMicItemDataBean.isRtcJoinStatus()) {
                    linkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_RTC_JOIN);
                    updateLinkMicMediaStatus(next.getValue(), linkMicItemDataBean);
                    z2 = true;
                }
                if (getLinkMicItemWithLinkMicId(linkMicUid) == null) {
                    this.streamerList.add(linkMicItemDataBean);
                    if ("guest".equals(this.userType)) {
                        SortGuestLinkMicListUtils.sort(this.streamerList);
                    }
                    updateMixLayoutUsers();
                    updateLinkMicCount();
                    callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.25
                        @Override // com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter.ViewRunnable
                        public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                            view.onUsersJoin(Collections.singletonList(linkMicItemDataBean));
                        }
                    });
                }
            }
        }
        return z2;
    }

    public void updateMixLayoutUsers() {
        ArrayList arrayList = new ArrayList();
        for (PLVLinkMicItemDataBean pLVLinkMicItemDataBean : this.streamerList) {
            PLVRTCMixUser pLVRTCMixUser = new PLVRTCMixUser();
            pLVRTCMixUser.setScreenShare(pLVLinkMicItemDataBean.isScreenShare());
            pLVRTCMixUser.setUserId(pLVLinkMicItemDataBean.getLinkMicId());
            pLVRTCMixUser.setMuteVideo(pLVLinkMicItemDataBean.isMuteVideo());
            arrayList.add(pLVRTCMixUser);
        }
        this.streamerManager.updateMixLayoutUsers(arrayList);
    }

    public void updateMixLayoutWhenScreenShare(boolean isShare, String linkmicId) {
        ArrayList arrayList = new ArrayList();
        for (PLVLinkMicItemDataBean pLVLinkMicItemDataBean : this.streamerList) {
            PLVRTCMixUser pLVRTCMixUser = new PLVRTCMixUser();
            if (pLVLinkMicItemDataBean.getLinkMicId().equals(linkmicId)) {
                pLVRTCMixUser.setScreenShare(isShare);
            } else {
                pLVRTCMixUser.setScreenShare(pLVLinkMicItemDataBean.isScreenShare());
            }
            pLVRTCMixUser.setMuteVideo(pLVLinkMicItemDataBean.isMuteVideo());
            pLVRTCMixUser.setUserId(pLVLinkMicItemDataBean.getLinkMicId());
            arrayList.add(pLVRTCMixUser);
        }
        this.streamerManager.updateMixLayoutUsers(arrayList);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerPresenter
    public void zoomLocalCamera(float scaleFactor) {
        if (isInitStreamerManager()) {
            float f2 = this.localCameraZoomFactor + ((scaleFactor - 1.0f) * 3.5f);
            this.localCameraZoomFactor = f2;
            float fMax = Math.max(1.0f, Math.min(10.0f, f2));
            this.localCameraZoomFactor = fMax;
            this.streamerManager.setCameraZoomRatio(fMax);
        }
    }

    public boolean updateMemberListItemInfo(PLVSocketUserBean socketUserBean, PLVLinkMicItemDataBean linkMicItemDataBean, boolean isJoinList, boolean isUpdateJoiningStatus) {
        PLVMemberItemDataBean pLVMemberItemDataBean;
        Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId = getMemberItemWithUserId(socketUserBean.getUserId());
        if (memberItemWithUserId == null || ((PLVMemberItemDataBean) memberItemWithUserId.second).getLinkMicItemDataBean() == null) {
            if (memberItemWithUserId == null) {
                pLVMemberItemDataBean = new PLVMemberItemDataBean();
                pLVMemberItemDataBean.setSocketUserBean(socketUserBean);
                this.memberList.add(pLVMemberItemDataBean);
            } else {
                pLVMemberItemDataBean = (PLVMemberItemDataBean) memberItemWithUserId.second;
            }
            pLVMemberItemDataBean.setLinkMicItemDataBean(linkMicItemDataBean);
            updateMemberListLinkMicStatusWithRtcJoinList(pLVMemberItemDataBean, linkMicItemDataBean.getLinkMicId());
            return true;
        }
        PLVLinkMicItemDataBean linkMicItemDataBean2 = ((PLVMemberItemDataBean) memberItemWithUserId.second).getLinkMicItemDataBean();
        boolean zIsJoiningStatus = linkMicItemDataBean2.isJoiningStatus();
        boolean zIsJoinStatus = linkMicItemDataBean2.isJoinStatus();
        boolean zIsWaitStatus = linkMicItemDataBean2.isWaitStatus();
        if (!isJoinList) {
            if ((zIsJoiningStatus && !isUpdateJoiningStatus) || zIsWaitStatus) {
                return false;
            }
            linkMicItemDataBean2.setStatus("wait");
            return true;
        }
        boolean zUpdateMemberListLinkMicStatusWithRtcJoinList = updateMemberListLinkMicStatusWithRtcJoinList((PLVMemberItemDataBean) memberItemWithUserId.second, linkMicItemDataBean2.getLinkMicId());
        if (zUpdateMemberListLinkMicStatusWithRtcJoinList) {
            return true;
        }
        if (linkMicItemDataBean2.isRtcJoinStatus() || zIsJoinStatus || zIsJoiningStatus) {
            return zUpdateMemberListLinkMicStatusWithRtcJoinList;
        }
        linkMicItemDataBean2.setStatus(PLVLinkMicItemDataBean.STATUS_JOIN);
        return true;
    }
}
