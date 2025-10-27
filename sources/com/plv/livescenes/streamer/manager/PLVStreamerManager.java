package com.plv.livescenes.streamer.manager;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.SurfaceView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.easefun.polyv.livescenes.streamer.listener.IPLVSStreamerOnLiveStatusChangeListener;
import com.hjq.permissions.Permission;
import com.plv.business.model.ppt.PLVPPTAuthentic;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.IPLVErrorCodeSender;
import com.plv.foundationsdk.log.elog.logcode.linkmic.PLVErrorCodeLinkMicRTC;
import com.plv.foundationsdk.log.elog.logcode.linkmic.PLVErrorCodeLinkMicRequest;
import com.plv.foundationsdk.log.elog.logcode.linkmic.PLVErrorCodeLinkMicSystemError;
import com.plv.foundationsdk.permission.PLVFastPermission;
import com.plv.foundationsdk.permission.PLVOnPermissionCallback;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.linkmic.PLVLinkMicWrapper;
import com.plv.linkmic.log.IPLVLinkMicErrorCodeSender;
import com.plv.linkmic.log.IPLVLinkMicTraceLogSender;
import com.plv.linkmic.model.PLVJoinInfoEvent;
import com.plv.linkmic.model.PLVLinkMicJoinStatus;
import com.plv.linkmic.model.PLVLinkMicTokenStatisticsInfo;
import com.plv.linkmic.model.PLVRTCConfig;
import com.plv.linkmic.processor.PLVVideoDimensionBitrate;
import com.plv.linkmic.repository.PLVLinkMicDataRepository;
import com.plv.linkmic.repository.PLVLinkMicEngineToken;
import com.plv.linkmic.repository.PLVLinkMicHttpRequestException;
import com.plv.linkmic.screenshare.IPLVScreenShareListener;
import com.plv.livescenes.access.PLVUserAbilityManager;
import com.plv.livescenes.access.PLVUserRole;
import com.plv.livescenes.config.PLVLiveStatusType;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.livescenes.linkmic.vo.PLVLinkMicEngineParam;
import com.plv.livescenes.log.linkmic.PLVLinkMicELog;
import com.plv.livescenes.model.PLVLiveStatusSessionIdVO;
import com.plv.livescenes.net.PLVApiManager;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.livescenes.streamer.IPLVStreamerManager;
import com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender;
import com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender;
import com.plv.livescenes.streamer.listener.IPLVOnGetSessionIdInnerListener;
import com.plv.livescenes.streamer.listener.IPLVStreamerOnEnableLocalCameraListener;
import com.plv.livescenes.streamer.listener.IPLVStreamerOnLiveStreamingStartListener;
import com.plv.livescenes.streamer.listener.IPLVStreamerOnLiveTimingListener;
import com.plv.livescenes.streamer.listener.IPLVStreamerOnServerTimeoutDueToNetBrokenListener;
import com.plv.livescenes.streamer.listener.PLVStreamerEventListener;
import com.plv.livescenes.streamer.listener.PLVStreamerListener;
import com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager;
import com.plv.livescenes.streamer.mix.PLVRTCMixUser;
import com.plv.livescenes.streamer.mix.PLVStreamerMixOpFactory;
import com.plv.livescenes.streamer.transfer.PLVStreamerInnerDataTransfer;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.ppt.PLVOnSliceIDEvent;
import com.plv.socket.event.ppt.PLVOnSliceStartEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.socketio.PLVSocketIOClient;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;
import com.plv.thirdpart.blankj.utilcode.util.NetworkUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.Utils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class PLVStreamerManager implements IPLVStreamerManager {
    private static final String MARK_BACKSLASH = "/";
    private static final String MARK_QUESTION = "?";
    private static final String TAG = "PLVStreamerManager";
    private boolean autoJoinChannel;

    @NonNull
    private final PLVLinkMicWrapper coreLinkMicWrapper;
    private int curBitrate;
    private boolean curCameraIsFront;
    private int currentNetQuality;
    private Disposable delay2MuteStream;
    private PLVStreamerListener engineCreateListener;
    private final IPLVLinkMicErrorCodeSender errorCodeManager;
    private PLVStreamerEventListener eventHandler;

    @Nullable
    private PLVSocketMessageObserver.OnMessageListener finishClassMsgListener;
    private final List<IPLVOnGetSessionIdInnerListener> getSessionIdInnerListeners;
    private boolean isCDNStreamPublishing;
    private boolean isLive;
    private boolean isLocalCameraEnabled;
    private final PLVLinkMicDataRepository linkMicDataRepository;
    private int liveDuration;
    private long liveStartTimestamp;
    private Disposable liveStatusDisposable;
    private Disposable liveTimerDisposable;
    private IPLVStreamerOnLiveTimingListener liveTimingListener;
    private boolean liveTranscodingEnabled;
    private String mChannelId;
    private String mLinkMicUid;
    private String mPushStreamUrl;
    private String mStreamId;
    private final Handler mainHandler;
    private int maxSupportedBitrate;
    private PLVSocketMessageObserver.OnMessageListener messageListener;
    private final IPLVStreamerMixOpManager mixOpManager;
    private boolean muteAudio;
    private boolean muteVideo;
    private final List<IPLVStreamerOnEnableLocalCameraListener> onEnableLocalCameraListeners;
    private final List<IPLVStreamerOnLiveStreamingStartListener> onLiveStreamingStartListeners;
    private final List<IPLVStreamerOnServerTimeoutDueToNetBrokenListener> onServerTimeoutDueToNetBrokenListeners;
    private ArrayList<String> permissions;
    private PLVLiveStatusType previousLiveStatus;
    private int pushPictureResolutionType;
    private PLVRTCConfig rtcConfig;
    private String rtcType;
    private String sessionId;
    private final IPLVLinkMicTraceLogSender traceLogSender;

    public PLVStreamerManager(IPLVLinkMicErrorCodeSender iPLVLinkMicErrorCodeSender, IPLVLinkMicTraceLogSender iPLVLinkMicTraceLogSender, PLVLinkMicDataRepository pLVLinkMicDataRepository) {
        PLVLinkMicWrapper pLVLinkMicWrapper = new PLVLinkMicWrapper();
        this.coreLinkMicWrapper = pLVLinkMicWrapper;
        this.liveTranscodingEnabled = true;
        this.curCameraIsFront = true;
        this.isLocalCameraEnabled = true;
        this.liveDuration = 0;
        this.liveStartTimestamp = 0L;
        this.isCDNStreamPublishing = false;
        this.currentNetQuality = 11;
        this.autoJoinChannel = true;
        this.onEnableLocalCameraListeners = new LinkedList();
        this.onLiveStreamingStartListeners = new LinkedList();
        this.onServerTimeoutDueToNetBrokenListeners = new LinkedList();
        this.getSessionIdInnerListeners = new LinkedList();
        this.muteVideo = false;
        this.muteAudio = false;
        this.permissions = new ArrayList<>(Arrays.asList(Permission.CAMERA, Permission.RECORD_AUDIO));
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.errorCodeManager = iPLVLinkMicErrorCodeSender;
        this.traceLogSender = iPLVLinkMicTraceLogSender;
        this.linkMicDataRepository = pLVLinkMicDataRepository;
        this.mixOpManager = PLVStreamerMixOpFactory.newInstance(pLVLinkMicDataRepository, pLVLinkMicWrapper);
        this.messageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.1
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String str, String str2, String str3) {
                if (str2 == null) {
                }
                switch (str2) {
                    case "onSliceID":
                        PLVOnSliceIDEvent pLVOnSliceIDEvent = (PLVOnSliceIDEvent) PLVGsonUtil.fromJson(PLVOnSliceIDEvent.class, str3);
                        if (pLVOnSliceIDEvent != null && pLVOnSliceIDEvent.getData() != null) {
                            PLVStreamerManager.this.sessionId = pLVOnSliceIDEvent.getData().getSessionId();
                            break;
                        }
                        break;
                    case "onSliceStart":
                        PLVOnSliceStartEvent pLVOnSliceStartEvent = (PLVOnSliceStartEvent) PLVGsonUtil.fromJson(PLVOnSliceStartEvent.class, str3);
                        if (pLVOnSliceStartEvent != null) {
                            PLVStreamerManager.this.sessionId = pLVOnSliceStartEvent.getSessionId();
                            break;
                        }
                        break;
                    case "TEACHER_SET_PERMISSION":
                        PLVPPTAuthentic pLVPPTAuthentic = (PLVPPTAuthentic) PLVGsonUtil.fromJson(PLVPPTAuthentic.class, str3);
                        if (PLVStreamerManager.this.mLinkMicUid.equals(pLVPPTAuthentic.getUserId())) {
                            PLVStreamerManager.this.updatePermission(pLVPPTAuthentic.getType(), !pLVPPTAuthentic.hasNoAthuentic());
                            break;
                        }
                        break;
                }
            }
        };
        PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.messageListener);
    }

    public static /* synthetic */ int access$508(PLVStreamerManager pLVStreamerManager) {
        int i2 = pLVStreamerManager.liveDuration;
        pLVStreamerManager.liveDuration = i2 + 1;
        return i2;
    }

    private PLVLinkMicTokenStatisticsInfo createTokenStatisticsInfo(PLVLinkMicEngineParam pLVLinkMicEngineParam) {
        PLVLinkMicTokenStatisticsInfo pLVLinkMicTokenStatisticsInfo = new PLVLinkMicTokenStatisticsInfo();
        pLVLinkMicTokenStatisticsInfo.setUid(this.mLinkMicUid + "");
        pLVLinkMicTokenStatisticsInfo.setChannelId(this.mChannelId + "");
        pLVLinkMicTokenStatisticsInfo.setScene(PLVLinkMicConfig.getInstance().getLiveChannelTypeNew().getValue());
        pLVLinkMicTokenStatisticsInfo.setUserType(pLVLinkMicEngineParam.getViewerType());
        pLVLinkMicTokenStatisticsInfo.setViewerId(pLVLinkMicEngineParam.getViewerId());
        pLVLinkMicTokenStatisticsInfo.setNickname(pLVLinkMicEngineParam.getNickName());
        pLVLinkMicTokenStatisticsInfo.setClient("Android SDK");
        pLVLinkMicTokenStatisticsInfo.setClientVersion("1.10.1.1");
        pLVLinkMicTokenStatisticsInfo.setClientTs(System.currentTimeMillis() + "");
        pLVLinkMicTokenStatisticsInfo.setSessionId(PLVLinkMicConfig.getInstance().getSessionId());
        pLVLinkMicTokenStatisticsInfo.setDeviceType(AliyunLogCommon.TERMINAL_TYPE);
        pLVLinkMicTokenStatisticsInfo.setModel(Build.MODEL + "");
        pLVLinkMicTokenStatisticsInfo.setOs("Android");
        pLVLinkMicTokenStatisticsInfo.setOsVersion(Build.VERSION.SDK_INT + "");
        pLVLinkMicTokenStatisticsInfo.setNetworkType(NetworkUtils.isWifiConnected() ? "Wifi" : "4G");
        pLVLinkMicTokenStatisticsInfo.setScreenWidth(ScreenUtils.getScreenWidth() + "");
        pLVLinkMicTokenStatisticsInfo.setScreenHeight(ScreenUtils.getScreenHeight() + "");
        return pLVLinkMicTokenStatisticsInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private int getStreamerFrameRate() {
        return PLVSugarUtil.clamp(PLVLinkMicConfig.getInstance().getFrameRate(), 1, 30);
    }

    private void initCoreLinkMicWrapper(PLVLinkMicEngineParam pLVLinkMicEngineParam) {
        this.rtcType = PLVLinkMicConfig.getInstance().getRtcType();
        this.rtcConfig = new PLVRTCConfig().uid(this.mLinkMicUid).rtcType(this.rtcType).frameRate(getStreamerFrameRate());
        this.linkMicDataRepository.setRTCType(this.rtcType);
        final PLVLinkMicTokenStatisticsInfo pLVLinkMicTokenStatisticsInfoCreateTokenStatisticsInfo = createTokenStatisticsInfo(pLVLinkMicEngineParam);
        this.coreLinkMicWrapper.init(Utils.getApp(), this.rtcConfig, new PLVLinkMicWrapper.IPLVTokenRequester() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.3
            @Override // com.plv.linkmic.PLVLinkMicWrapper.IPLVTokenRequester
            public void requestToken(final PLVLinkMicWrapper.IPLVTokenRequester.OnRequestTokenListener onRequestTokenListener) {
                PLVStreamerManager.this.linkMicDataRepository.requestToken(PLVStreamerManager.this.mChannelId, PolyvLiveSDKClient.getInstance().getAppId(), PolyvLiveSDKClient.getInstance().getAppSecret(), PLVStreamerManager.this.mLinkMicUid, pLVLinkMicTokenStatisticsInfoCreateTokenStatisticsInfo, new PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicEngineToken>() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.3.1
                    @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
                    public void onFail(PLVLinkMicHttpRequestException pLVLinkMicHttpRequestException) {
                        onRequestTokenListener.onFail(pLVLinkMicHttpRequestException);
                        PLVStreamerManager.this.notifyRequestErrorCode(pLVLinkMicHttpRequestException.getErrorCode(), pLVLinkMicHttpRequestException.getMessage(), pLVLinkMicHttpRequestException);
                    }

                    @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
                    public void onSuccess(PLVLinkMicEngineToken pLVLinkMicEngineToken) {
                        onRequestTokenListener.onSuccess(pLVLinkMicEngineToken);
                    }
                });
            }
        }, new PLVLinkMicWrapper.IPLVLinkMicWrapperCallback() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.4
            @Override // com.plv.linkmic.PLVLinkMicWrapper.IPLVLinkMicWrapperCallback
            public void onJoinChannelError(String str) {
                PLVStreamerManager.this.notifyLinkMicErrorCode(2, "", new Throwable(str));
            }

            @Override // com.plv.linkmic.PLVLinkMicWrapper.IPLVLinkMicWrapperCallback
            public void onLinkMicEngineCreateFailed(Throwable th) {
                PLVStreamerManager.this.notifyLinkMicErrorCode(4, "", th);
            }

            @Override // com.plv.linkmic.PLVLinkMicWrapper.IPLVLinkMicWrapperCallback
            public void onLinkMicEngineCreated(String str) throws NumberFormatException {
                PLVStreamerInnerDataTransfer.getInstance().initByLinkMic(PLVStreamerManager.this);
                PLVStreamerManager pLVStreamerManager = PLVStreamerManager.this;
                pLVStreamerManager.initMixOpManager(pLVStreamerManager.mPushStreamUrl);
                PLVStreamerManager.this.initEventHandler();
                PLVStreamerManager.this.initEventTrace();
                PLVStreamerManager.this.coreLinkMicWrapper.switchRoleToAudience();
                PLVStreamerManager.this.requestPermission(new Runnable() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (PLVStreamerManager.this.engineCreateListener != null) {
                            PLVStreamerManager.this.engineCreateListener.onStreamerEngineCreatedSuccess();
                        }
                        if (PLVStreamerManager.this.autoJoinChannel) {
                            PLVStreamerManager.this.coreLinkMicWrapper.joinChannel(PLVStreamerManager.this.mChannelId);
                        }
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initEventHandler() {
        PLVStreamerEventListener pLVStreamerEventListener = new PLVStreamerEventListener() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.5
            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onJoinChannelSuccess(String str) {
                super.onJoinChannelSuccess(str);
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onNetworkQuality(int i2) {
                PLVStreamerManager.this.currentNetQuality = i2;
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onStreamPublished(String str, int i2) {
                if (i2 == 0) {
                    PLVStreamerManager.this.isCDNStreamPublishing = true;
                    if (!PLVStreamerManager.this.isLocalCameraEnabled) {
                        PLVStreamerManager pLVStreamerManager = PLVStreamerManager.this;
                        pLVStreamerManager.dispose(pLVStreamerManager.delay2MuteStream);
                        PLVStreamerManager.this.delay2MuteStream = PLVRxTimer.delay(1000L, new Consumer<Object>() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.5.1
                            @Override // io.reactivex.functions.Consumer
                            public void accept(Object obj) throws Exception {
                                PLVStreamerManager.this.coreLinkMicWrapper.muteLocalVideo(true);
                                PLVStreamerManager.this.muteVideo = true;
                            }
                        });
                    }
                    PLVStreamerManager.this.linkMicDataRepository.getSessionIdFromServer(PolyvLiveSDKClient.getInstance().getAppId(), PolyvLiveSDKClient.getInstance().getAppSecret(), PLVStreamerManager.this.mChannelId, PLVStreamerManager.this.mStreamId, new PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<Pair<String, String>>() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.5.2
                        @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
                        public void onFail(PLVLinkMicHttpRequestException pLVLinkMicHttpRequestException) {
                            PLVStreamerManager.this.notifyRequestErrorCode(pLVLinkMicHttpRequestException.getErrorCode(), pLVLinkMicHttpRequestException.getMessage(), pLVLinkMicHttpRequestException);
                        }

                        @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
                        public void onFinish() {
                        }

                        @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
                        public void onSuccess(Pair<String, String> pair) {
                            String str2 = (String) pair.first;
                            String str3 = (String) pair.second;
                            PLVStreamerManager.this.sessionId = str2;
                            Iterator it = PLVStreamerManager.this.getSessionIdInnerListeners.iterator();
                            while (it.hasNext()) {
                                ((IPLVOnGetSessionIdInnerListener) it.next()).onGetSessionId(str2, str3, PLVStreamerManager.this.mStreamId, !PLVStreamerManager.this.isLocalCameraEnabled);
                            }
                            Iterator it2 = PLVStreamerManager.this.onLiveStreamingStartListeners.iterator();
                            while (it2.hasNext()) {
                                ((IPLVStreamerOnLiveStreamingStartListener) it2.next()).onLiveStreamingStart();
                            }
                            PLVStreamerManager.this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.ON_LIVE_STREAMING_START, "推流成功，直播开始");
                            PLVStreamerManager.this.liveStartTimestamp = System.currentTimeMillis();
                            PLVStreamerManager.this.startLiveTiming();
                        }
                    });
                    return;
                }
                PLVCommonLog.e(PLVStreamerManager.TAG, "推流失败,error=" + i2);
                PLVStreamerManager.this.notifyLinkMicErrorCode(1, "error=" + i2, new Throwable());
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onStreamUnpublished(String str) {
                PLVStreamerManager.this.isCDNStreamPublishing = false;
                if (PLVStreamerManager.this.isRoleGuest()) {
                    return;
                }
                PLVStreamerManager.this.stopLiveTiming();
            }
        };
        this.eventHandler = pLVStreamerEventListener;
        addEventHandler(pLVStreamerEventListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initEventTrace() {
        addEventHandler(new PLVStreamerEventListener() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.6
            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onTokenExpired() {
                PLVStreamerManager.this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.ON_TOKEN_EXPIRED, "token过期");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initMixOpManager(String str) throws NumberFormatException {
        try {
            int i2 = Integer.parseInt(this.mChannelId);
            int streamerFrameRate = getStreamerFrameRate();
            PLVVideoDimensionBitrate pLVVideoDimensionBitrateMatch = PLVVideoDimensionBitrate.match(1, this.rtcConfig.getResolutionRatio(), streamerFrameRate);
            IPLVStreamerMixOpManager.EncodeParam encodeParam = new IPLVStreamerMixOpManager.EncodeParam();
            encodeParam.audioSampleRate = 48000;
            encodeParam.audioBitrate = 64;
            encodeParam.audioChannels = 2;
            encodeParam.videoWidth = pLVVideoDimensionBitrateMatch.width;
            encodeParam.videoHeight = pLVVideoDimensionBitrateMatch.height;
            encodeParam.videoBitrate = pLVVideoDimensionBitrateMatch.realBitrate;
            encodeParam.videoFramerate = streamerFrameRate;
            encodeParam.videoGop = 2;
            this.mixOpManager.init(PolyvLiveSDKClient.getInstance().getAppId(), PolyvLiveSDKClient.getInstance().getAppSecret(), i2, retrieveStreamIdFromPublishUrl(str), false, encodeParam, str);
            this.mixOpManager.setLiveTranscodingEnable(this.liveTranscodingEnabled);
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isRoleGuest() {
        return "guest".equals(PLVSocketIOClient.getInstance().getUserType());
    }

    private boolean isRoleTeacher() {
        return "teacher".equals(PLVSocketIOClient.getInstance().getUserType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void listenLiveStatusChangeInternal(int i2, final int i3, final IPLVSStreamerOnLiveStatusChangeListener iPLVSStreamerOnLiveStatusChangeListener) {
        dispose(this.liveStatusDisposable);
        final String currentStream = PolyvLiveSDKClient.getInstance().getCurrentStream();
        this.liveStatusDisposable = Observable.interval(i2, i3, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).flatMap(new Function<Long, ObservableSource<ResponseBody>>() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.17
            @Override // io.reactivex.functions.Function
            public ObservableSource<ResponseBody> apply(@NotNull Long l2) throws Exception {
                return PLVApiManager.getPlvLiveStatusApi().getLiveStatusByStreamV3(currentStream, PLVStreamerManager.this.mChannelId, new HashMap());
            }
        }).map(new Function<ResponseBody, PLVLiveStatusType>() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.16
            @Override // io.reactivex.functions.Function
            public PLVLiveStatusType apply(@NotNull ResponseBody responseBody) throws Exception {
                PLVLiveStatusSessionIdVO pLVLiveStatusSessionIdVO = (PLVLiveStatusSessionIdVO) PLVGsonUtil.fromJson(PLVLiveStatusSessionIdVO.class, responseBody.string());
                return PLVLiveStatusType.mapFromServerString(pLVLiveStatusSessionIdVO != null ? pLVLiveStatusSessionIdVO.getStatus() : null);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PLVLiveStatusType>() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.14
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVLiveStatusType pLVLiveStatusType) throws Exception {
                PLVLiveStatusType pLVLiveStatusType2 = PLVStreamerManager.this.previousLiveStatus;
                PLVLiveStatusType pLVLiveStatusType3 = PLVLiveStatusType.LIVE;
                boolean z2 = true;
                boolean z3 = pLVLiveStatusType2 == pLVLiveStatusType3;
                boolean z4 = pLVLiveStatusType == pLVLiveStatusType3;
                if (PLVStreamerManager.this.previousLiveStatus != null && z4 == z3) {
                    z2 = false;
                }
                if (z2) {
                    PLVCommonLog.d(PLVStreamerManager.TAG, "onStreamLiveStatusChanged->isLiveNow=" + z4);
                    PLVStreamerManager.this.previousLiveStatus = pLVLiveStatusType;
                    IPLVSStreamerOnLiveStatusChangeListener iPLVSStreamerOnLiveStatusChangeListener2 = iPLVSStreamerOnLiveStatusChangeListener;
                    if (iPLVSStreamerOnLiveStatusChangeListener2 != null) {
                        iPLVSStreamerOnLiveStatusChangeListener2.onLiveStatusChange(z4);
                    }
                    if (!z4) {
                        PLVStreamerManager.this.stopLiveTiming();
                    }
                }
                PLVStreamerManager.this.previousLiveStatus = pLVLiveStatusType;
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.15
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                th.printStackTrace();
                PLVStreamerManager pLVStreamerManager = PLVStreamerManager.this;
                int i4 = i3;
                pLVStreamerManager.listenLiveStatusChange(i4, i4, iPLVSStreamerOnLiveStatusChangeListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyLinkMicErrorCode(int i2, String str, Throwable th) {
        IPLVErrorCodeSender.SubmitResult submitResultSubmitError = this.errorCodeManager.submitError(PLVErrorCodeLinkMicRTC.class, i2, str, th);
        PLVStreamerListener pLVStreamerListener = this.engineCreateListener;
        if (pLVStreamerListener != null) {
            pLVStreamerListener.onStreamerError(submitResultSubmitError.getIntactErrorCode(), new Throwable(submitResultSubmitError.getCounterPartMsgOfCode(), th));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRequestErrorCode(int i2, String str, Throwable th) {
        IPLVErrorCodeSender.SubmitResult submitResultSubmitError = this.errorCodeManager.submitError(PLVErrorCodeLinkMicRequest.class, i2, str, th);
        PLVStreamerListener pLVStreamerListener = this.engineCreateListener;
        if (pLVStreamerListener != null) {
            pLVStreamerListener.onStreamerError(submitResultSubmitError.getIntactErrorCode(), new Throwable(submitResultSubmitError.getCounterPartMsgOfCode(), th));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestPermission(final Runnable runnable) {
        PLVFastPermission.getInstance().start(ActivityUtils.getTopActivity(), this.permissions, new PLVOnPermissionCallback() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.19
            @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
            public void onAllGranted() {
                runnable.run();
            }

            @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
            public void onPartialGranted(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3) {
                Throwable th = new Throwable("grantedPermissions=" + arrayList.toString() + "\ndeniedPermissions=" + arrayList2 + "\ndeniedForeverP=" + arrayList3);
                IPLVErrorCodeSender.SubmitResult submitResultSubmitError = PLVStreamerManager.this.errorCodeManager.submitError(PLVErrorCodeLinkMicSystemError.class, 1, "", th);
                if (PLVStreamerManager.this.engineCreateListener != null) {
                    PLVStreamerManager.this.engineCreateListener.onStreamerError(submitResultSubmitError.getIntactErrorCode(), new Throwable(submitResultSubmitError.getCounterPartMsgOfCode(), th));
                }
            }
        });
    }

    private String retrieveStreamIdFromPublishUrl(String str) {
        if (str.contains(MARK_QUESTION)) {
            PLVCommonLog.d(TAG, "publishStreamUrl contains '?' ");
            str = str.substring(0, str.lastIndexOf(MARK_QUESTION));
        } else {
            if (str.endsWith("/")) {
                str = str.substring(0, str.lastIndexOf("/"));
            }
            PLVCommonLog.d(TAG, "publishStreamUrl doesn't contain '?' ");
        }
        String strSubstring = str.substring(str.lastIndexOf("/") + 1);
        PLVCommonLog.d(TAG, "retrieveStreamIdFromPublishUrl=" + strSubstring);
        return strSubstring;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startLiveTiming() {
        dispose(this.liveTimerDisposable);
        this.liveTimerDisposable = PLVRxTimer.timer(1000, new Consumer<Long>() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.18
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                if (PLVStreamerManager.this.currentNetQuality == 14) {
                    return;
                }
                PLVStreamerManager.access$508(PLVStreamerManager.this);
                PLVStreamerManager.this.mainHandler.post(new Runnable() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.18.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (PLVStreamerManager.this.liveTimingListener != null) {
                            PLVStreamerManager.this.liveTimingListener.onTimePastEachSeconds(PLVStreamerManager.this.liveDuration);
                        }
                    }
                });
                if (PLVStreamerManager.this.liveDuration % 2 == 0) {
                    PLVStreamerManager.this.coreLinkMicWrapper.updateSEIFrameTimeStamp(String.valueOf(PLVStreamerManager.this.liveDuration * 1000));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopLiveTiming() {
        dispose(this.liveTimerDisposable);
        this.liveStartTimestamp = 0L;
        this.liveDuration = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePermission(String str, boolean z2) {
        if ("speaker".equals(str)) {
            if (z2) {
                PLVUserAbilityManager.myAbility().addRole(PLVUserRole.STREAMER_GRANTED_SPEAKER_USER);
                return;
            } else {
                PLVUserAbilityManager.myAbility().removeRole(PLVUserRole.STREAMER_GRANTED_SPEAKER_USER);
                return;
            }
        }
        if ("paint".equals(str)) {
            if (z2) {
                PLVUserAbilityManager.myAbility().addRole(PLVUserRole.STREAMER_GRANTED_PAINT_USER);
            } else {
                PLVUserAbilityManager.myAbility().removeRole(PLVUserRole.STREAMER_GRANTED_PAINT_USER);
            }
        }
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void addEventHandler(PLVStreamerEventListener pLVStreamerEventListener) {
        this.coreLinkMicWrapper.addEventHandler(pLVStreamerEventListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void addGetSessionIdFromServerListener(IPLVOnGetSessionIdInnerListener iPLVOnGetSessionIdInnerListener) {
        this.getSessionIdInnerListeners.add(iPLVOnGetSessionIdInnerListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void addOnEnableLocalCameraListener(IPLVStreamerOnEnableLocalCameraListener iPLVStreamerOnEnableLocalCameraListener) {
        this.onEnableLocalCameraListeners.add(iPLVStreamerOnEnableLocalCameraListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void addOnLiveStreamingStartListener(IPLVStreamerOnLiveStreamingStartListener iPLVStreamerOnLiveStreamingStartListener) {
        this.onLiveStreamingStartListeners.add(iPLVStreamerOnLiveStreamingStartListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void addStreamerServerTimeoutListener(IPLVStreamerOnServerTimeoutDueToNetBrokenListener iPLVStreamerOnServerTimeoutDueToNetBrokenListener) {
        this.onServerTimeoutDueToNetBrokenListeners.add(iPLVStreamerOnServerTimeoutDueToNetBrokenListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void adjustRecordingSignalVolume(int i2) {
        this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.ADJUST_RECODING_VOLUME, "adjustRecordingSignalVolume=" + i2);
        this.coreLinkMicWrapper.adjustRecordingSignalVolume(i2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public SurfaceView createRendererView(Context context) {
        return this.coreLinkMicWrapper.createRendererView(context);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void destroy() {
        PLVLinkMicEventSender.getInstance().release();
        PLVStreamerInnerDataTransfer.getInstance().destroyByLinkMic();
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.messageListener);
        if (this.isLive) {
            stopLiveStream();
        }
        this.coreLinkMicWrapper.destroy();
        this.isCDNStreamPublishing = false;
        this.linkMicDataRepository.release();
        this.previousLiveStatus = null;
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.finishClassMsgListener);
        dispose(this.liveStatusDisposable);
        stopLiveTiming();
        dispose(this.delay2MuteStream);
        this.coreLinkMicWrapper.removeEventHandler(this.eventHandler);
        this.eventHandler = null;
        this.engineCreateListener = null;
        this.liveTimingListener = null;
        this.getSessionIdInnerListeners.clear();
        this.onEnableLocalCameraListeners.clear();
        this.onLiveStreamingStartListeners.clear();
        this.onServerTimeoutDueToNetBrokenListeners.clear();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void disableAutoJoinChannel() {
        this.autoJoinChannel = false;
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void enableLocalCamera(boolean z2) {
        this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.ENABLE_LOCAL_VIDEO, "enable=" + z2);
        this.isLocalCameraEnabled = z2;
        this.muteVideo = z2 ^ true;
        if (z2) {
            int iMuteLocalVideo = this.coreLinkMicWrapper.muteLocalVideo(false);
            Iterator<IPLVStreamerOnEnableLocalCameraListener> it = this.onEnableLocalCameraListeners.iterator();
            while (it.hasNext()) {
                it.next().onCameraOpen(iMuteLocalVideo == 0);
            }
            return;
        }
        int iMuteLocalVideo2 = this.coreLinkMicWrapper.muteLocalVideo(true);
        Iterator<IPLVStreamerOnEnableLocalCameraListener> it2 = this.onEnableLocalCameraListeners.iterator();
        while (it2.hasNext()) {
            it2.next().onCameraClose(iMuteLocalVideo2 == 0);
        }
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void enableLocalMicrophone(boolean z2) {
        this.muteAudio = !z2;
        this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.ENABLE_LOCAL_AUDIO, "enable=" + z2);
        this.coreLinkMicWrapper.muteLocalAudio(z2 ^ true);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public boolean enableTorch(boolean z2) {
        return this.coreLinkMicWrapper.enableTorch(z2) != -1;
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void exitScreenCapture() {
        this.coreLinkMicWrapper.stopShareScreen();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public int getCurrentNetQuality() {
        return this.currentNetQuality;
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public String getLinkMicUid() {
        return this.mLinkMicUid;
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void getLinkStatus(String str, final PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicJoinStatus> iPLVLinkMicDataRepoListener) {
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        this.linkMicDataRepository.getInteractStatus(appId, appSecret, this.mChannelId + "", str, true, new PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicJoinStatus>() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.11
            @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
            public void onFail(PLVLinkMicHttpRequestException pLVLinkMicHttpRequestException) {
                iPLVLinkMicDataRepoListener.onFail(pLVLinkMicHttpRequestException);
            }

            @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
            public void onFinish() {
                iPLVLinkMicDataRepoListener.onFinish();
            }

            @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
            public void onSuccess(PLVLinkMicJoinStatus pLVLinkMicJoinStatus) {
                Iterator<PLVJoinInfoEvent> it = pLVLinkMicJoinStatus.getJoinList().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    PLVJoinInfoEvent next = it.next();
                    if (PLVStreamerManager.this.getLinkMicUid().equals(next.getUserId())) {
                        PLVJoinInfoEvent.ClassStatus classStatus = next.getClassStatus();
                        if (classStatus != null) {
                            PLVStreamerManager.this.updatePermission("speaker", classStatus.isSpeaker());
                        }
                    }
                }
                iPLVLinkMicDataRepoListener.onSuccess(pLVLinkMicJoinStatus);
            }
        });
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public int getLiveDuration() {
        return this.liveDuration;
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public long getLiveStartTimestamp() {
        return this.liveStartTimestamp;
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public int getMaxSupportedBitrate() {
        int i2 = this.maxSupportedBitrate;
        if (i2 >= 720) {
            return 3;
        }
        return i2 >= 360 ? 2 : 1;
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void initEngine(PLVLinkMicEngineParam pLVLinkMicEngineParam, PLVStreamerListener pLVStreamerListener) {
        this.mStreamId = PolyvLiveSDKClient.getInstance().getStreamId();
        this.mChannelId = PolyvLiveSDKClient.getInstance().getChannelId();
        this.mLinkMicUid = PLVLinkMicConfig.getInstance().getLinkMicUid();
        this.engineCreateListener = pLVStreamerListener;
        this.isCDNStreamPublishing = false;
        initCoreLinkMicWrapper(pLVLinkMicEngineParam);
        PLVLinkMicEventSender.getInstance().listenerClassDuration(new IPLVLinkMicEventSender.IPLVGuestClassDuration() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.2
            @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.IPLVGuestClassDuration
            public void onClassDuration(long j2, long j3) {
                if (PLVStreamerManager.this.isRoleGuest()) {
                    PLVStreamerManager.this.liveStartTimestamp = j2;
                    PLVStreamerManager.this.liveDuration = (int) (j3 / 1000);
                    PLVCommonLog.d(PLVStreamerManager.TAG, "startTime=" + j2 + " duration=" + j3);
                    PLVStreamerManager.this.startLiveTiming();
                }
            }
        });
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public boolean isLiveStreaming() {
        return this.isCDNStreamPublishing;
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public boolean isScreenSharing() {
        return this.coreLinkMicWrapper.isScreenSharing();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void joinChannel() {
        requestPermission(new Runnable() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.12
            @Override // java.lang.Runnable
            public void run() {
                PLVStreamerManager.this.coreLinkMicWrapper.joinChannel(PLVStreamerManager.this.mChannelId);
            }
        });
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void leaveChannel() {
        this.coreLinkMicWrapper.leaveChannel(false);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void listenLiveStatusChange(int i2, final int i3, final IPLVSStreamerOnLiveStatusChangeListener iPLVSStreamerOnLiveStatusChangeListener) {
        this.finishClassMsgListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.13
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String str, String str2, String str3) {
                if (PLVEventConstant.Class.FINISH_CLASS.equals(str2)) {
                    PLVStreamerManager.this.previousLiveStatus = PLVLiveStatusType.END;
                    iPLVSStreamerOnLiveStatusChangeListener.onLiveStatusChange(false);
                    PLVStreamerManager.this.stopLiveTiming();
                    PLVStreamerManager pLVStreamerManager = PLVStreamerManager.this;
                    int i4 = i3;
                    pLVStreamerManager.listenLiveStatusChangeInternal(i4, i4, iPLVSStreamerOnLiveStatusChangeListener);
                }
            }
        };
        PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.finishClassMsgListener);
        listenLiveStatusChangeInternal(i2, i3, iPLVSStreamerOnLiveStatusChangeListener);
    }

    public void notifyClassOffWhenReconnectIfServerTimeout() {
        Iterator<IPLVStreamerOnServerTimeoutDueToNetBrokenListener> it = this.onServerTimeoutDueToNetBrokenListeners.iterator();
        while (it.hasNext()) {
            it.next().onServerTimeoutDueToNetBroken();
        }
        this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.ON_SERVER_TIME_OUT_DUE_TO_NET_BROKEN, "长时间断网重连后，发现服务端已经判定本场直播结束了。");
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void releaseRenderView(SurfaceView surfaceView) {
        this.coreLinkMicWrapper.releaseRenderView(surfaceView);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void removeEventHandler(PLVStreamerEventListener pLVStreamerEventListener) {
        this.coreLinkMicWrapper.removeEventHandler(pLVStreamerEventListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void requestScreenCapture(Activity activity) {
        this.coreLinkMicWrapper.requestAndStartShareScreen(activity);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void resetRequestPermissionList(ArrayList<String> arrayList) {
        this.permissions.clear();
        this.permissions.addAll(arrayList);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setBitrate(final int i2) {
        int i3;
        int i4;
        PLVVideoDimensionBitrate pLVVideoDimensionBitrateMatch = PLVVideoDimensionBitrate.match(i2, this.rtcConfig.getResolutionRatio(), getStreamerFrameRate());
        if (this.pushPictureResolutionType == 2) {
            i3 = pLVVideoDimensionBitrateMatch.width;
            i4 = pLVVideoDimensionBitrateMatch.height;
        } else {
            i3 = pLVVideoDimensionBitrateMatch.height;
            i4 = pLVVideoDimensionBitrateMatch.width;
        }
        String str = TAG;
        PLVCommonLog.d(str, String.format(Locale.US, "x=%d, y=%d", Integer.valueOf(i3), Integer.valueOf(i4)));
        this.mixOpManager.updateVideoEncodeParam(i3, i4, pLVVideoDimensionBitrateMatch.realBitrate);
        Runnable runnable = new Runnable() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.10
            @Override // java.lang.Runnable
            public void run() {
                PLVStreamerManager.this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.SET_BITRATE, "setBitrate=" + i2);
                PLVStreamerManager.this.coreLinkMicWrapper.setBitrate(i2);
                PLVStreamerManager.this.curBitrate = i2;
            }
        };
        int i5 = this.maxSupportedBitrate;
        if (i5 >= 720) {
            runnable.run();
            return;
        }
        if (i5 < 360) {
            PLVCommonLog.e(str, "不支持设置码率");
        } else if (i2 == 3) {
            PLVCommonLog.e(str, "该码率不支持");
        } else {
            runnable.run();
        }
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setCameraZoomRatio(float f2) {
        this.coreLinkMicWrapper.setCameraZoomRatio(f2);
    }

    public void setLiveTranscodingEnabled(boolean z2) {
        this.liveTranscodingEnabled = z2;
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setLocalPreviewMirror(boolean z2) {
        this.coreLinkMicWrapper.setLocalPreviewMirror(z2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setLocalPushMirror(boolean z2) {
        this.coreLinkMicWrapper.setLocalPushMirror(z2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setMixLayoutType(int i2) {
        this.mixOpManager.setMixLayoutType(i2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setOnLiveTimingListener(IPLVStreamerOnLiveTimingListener iPLVStreamerOnLiveTimingListener) {
        this.liveTimingListener = iPLVStreamerOnLiveTimingListener;
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public boolean setOnlyAudio(boolean z2) {
        return this.coreLinkMicWrapper.setOnlyAudio(z2) != -1;
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setPushPictureResolutionType(int i2) {
        this.pushPictureResolutionType = i2;
        this.coreLinkMicWrapper.setPushPictureResolutionType(i2);
        setBitrate(this.curBitrate);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setPushResolutionRatio(PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio) {
        PLVRTCConfig pLVRTCConfig = this.rtcConfig;
        if (pLVRTCConfig == null) {
            return;
        }
        pLVRTCConfig.resolutionRatio(pushResolutionRatio);
        this.coreLinkMicWrapper.setPushResolutionRatio(pushResolutionRatio);
        setBitrate(this.curBitrate);
    }

    public void setPushStreamUrl(String str) {
        this.mPushStreamUrl = str;
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setShareScreenListener(IPLVScreenShareListener iPLVScreenShareListener) {
        this.coreLinkMicWrapper.setScreenShareListener(iPLVScreenShareListener);
    }

    public void setSupportedMaxBitrate(int i2) {
        PLVCommonLog.d(TAG, "最大支持的码率=" + i2);
        this.maxSupportedBitrate = i2;
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setupLocalVideo(SurfaceView surfaceView) {
        setupLocalVideo(surfaceView, 1);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setupRemoteVideo(SurfaceView surfaceView, String str) {
        this.coreLinkMicWrapper.setupRemoteVideo(surfaceView, str.equals(String.valueOf(this.mChannelId)) ? 2 : 1, str);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void startLiveStream() {
        startLiveStream(false);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void startPreview() {
        this.coreLinkMicWrapper.startPreview();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void stopLiveStream() {
        if (isRoleTeacher()) {
            this.isLive = false;
            this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.STOP_LIVE_STREAM, "停止推流");
            this.linkMicDataRepository.notifyLiveEnd(PolyvLiveSDKClient.getInstance().getAppId(), PolyvLiveSDKClient.getInstance().getAppSecret(), this.mChannelId, this.mStreamId, new PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<String>() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.8
                @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
                public void onFail(PLVLinkMicHttpRequestException pLVLinkMicHttpRequestException) {
                    PLVCommonLog.exception(pLVLinkMicHttpRequestException);
                }

                @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
                public void onSuccess(String str) {
                }
            });
            if (this.rtcType.equals(PLVLinkMicConstant.RtcType.RTC_TYPE_U)) {
                this.coreLinkMicWrapper.switchRoleToAudience();
            }
            this.mixOpManager.stopMix(new IPLVStreamerMixOpManager.OnMixActionListener() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.9
                @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager.OnMixActionListener
                public void onFail(PLVLinkMicHttpRequestException pLVLinkMicHttpRequestException) {
                    PLVCommonLog.e(PLVStreamerManager.TAG, Log.getStackTraceString(pLVLinkMicHttpRequestException));
                }

                @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager.OnMixActionListener
                public void onSuccess() {
                    if (PLVStreamerManager.this.eventHandler != null) {
                        PLVStreamerManager.this.eventHandler.onStreamUnpublished(PLVStreamerManager.this.mPushStreamUrl);
                    }
                }
            });
            stopLiveTiming();
        }
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void switchBeauty(boolean z2) {
        this.coreLinkMicWrapper.switchBeauty(z2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void switchCamera(boolean z2) {
        this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.SWITCH_CAMERA, "switchCamera->front=" + z2);
        if (this.curCameraIsFront == z2) {
            return;
        }
        this.curCameraIsFront = z2;
        this.coreLinkMicWrapper.switchCamera();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void switchRoleToAudience() {
        this.coreLinkMicWrapper.switchRoleToAudience();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void switchRoleToBroadcaster() {
        if (this.muteVideo) {
            enableLocalCamera(false);
            PLVLinkMicEventSender.getInstance().muteUserMedia(PLVSocketWrapper.getInstance().getLoginVO().createSocketUserBean(), this.sessionId, true, this.muteVideo, null);
        }
        if (this.muteAudio) {
            enableLocalMicrophone(false);
            PLVLinkMicEventSender.getInstance().muteUserMedia(PLVSocketWrapper.getInstance().getLoginVO().createSocketUserBean(), this.sessionId, false, this.muteAudio, null);
        }
        this.coreLinkMicWrapper.switchRoleToBroadcaster();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void updateMixLayoutUsers(List<? extends PLVRTCMixUser> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (PLVRTCMixUser pLVRTCMixUser : list) {
            IPLVStreamerMixOpManager.MixUser mixUser = new IPLVStreamerMixOpManager.MixUser();
            mixUser.userId = pLVRTCMixUser.getUserId();
            if (pLVRTCMixUser.isScreenShare() && PLVLinkMicConstant.RtcType.RTC_TYPE_U.equals(PLVLinkMicConfig.getInstance().getRtcType())) {
                mixUser.streamType = 1;
            } else {
                mixUser.streamType = 0;
            }
            mixUser.mixInputType = pLVRTCMixUser.isMuteVideo() ? 2 : 0;
            mixUser.renderMode = 0;
            mixUser.hidden = pLVRTCMixUser.isHidden();
            arrayList.add(mixUser);
        }
        this.mixOpManager.updateMixUser(arrayList);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void leaveChannel(boolean z2) {
        this.coreLinkMicWrapper.leaveChannel(z2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setupLocalVideo(SurfaceView surfaceView, int i2) {
        int i3 = 2;
        if (i2 != 2) {
            if (i2 == 10) {
                PLVCommonLog.d(TAG, "setupLocalVideo renderMode = RENDER_MODE_NONE");
                return;
            }
            i3 = 1;
        }
        int i4 = this.coreLinkMicWrapper.setupLocalVideo(surfaceView, i3, this.mLinkMicUid);
        PLVCommonLog.d(TAG, "ret=" + i4);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void startLiveStream(boolean z2) {
        String str = z2 ? "Y" : "N";
        this.isLive = true;
        this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.START_LIVE_STREAM, "开始推流：");
        this.linkMicDataRepository.notifyStream(PolyvLiveSDKClient.getInstance().getAppId(), PolyvLiveSDKClient.getInstance().getAppSecret(), this.mChannelId, this.mStreamId, str, new PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<String>() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.7
            @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
            public void onFail(PLVLinkMicHttpRequestException pLVLinkMicHttpRequestException) {
                PLVStreamerManager.this.notifyRequestErrorCode(pLVLinkMicHttpRequestException.getErrorCode(), pLVLinkMicHttpRequestException.getMessage(), pLVLinkMicHttpRequestException);
            }

            @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
            public void onFinish() {
            }

            @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
            public void onSuccess(String str2) {
                PLVStreamerManager.this.coreLinkMicWrapper.switchRoleToBroadcaster();
                IPLVStreamerMixOpManager.MixUser mixUser = new IPLVStreamerMixOpManager.MixUser();
                mixUser.userId = PLVStreamerManager.this.mChannelId;
                mixUser.streamType = 0;
                mixUser.mixInputType = 0;
                mixUser.renderMode = 0;
                PLVStreamerManager.this.mixOpManager.startMix(mixUser, new IPLVStreamerMixOpManager.OnMixActionListener() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManager.7.1
                    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager.OnMixActionListener
                    public void onFail(PLVLinkMicHttpRequestException pLVLinkMicHttpRequestException) {
                        PLVStreamerManager.this.notifyRequestErrorCode(pLVLinkMicHttpRequestException.getErrorCode(), pLVLinkMicHttpRequestException.getMessage(), pLVLinkMicHttpRequestException);
                    }

                    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager.OnMixActionListener
                    public void onSuccess() {
                        if (PLVStreamerManager.this.eventHandler != null) {
                            PLVStreamerManager.this.eventHandler.onStreamPublished(PLVStreamerManager.this.mPushStreamUrl, 0);
                        }
                    }
                });
            }
        });
    }
}
