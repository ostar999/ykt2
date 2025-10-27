package com.plv.livescenes.linkmic.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.easefun.polyv.livescenes.linkmic.IPolyvLinkMicManager;
import com.hjq.permissions.Permission;
import com.plv.business.model.ppt.PLVPPTAuthentic;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.IPLVErrorCodeSender;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;
import com.plv.foundationsdk.log.elog.logcode.linkmic.PLVErrorCodeLinkMicRTC;
import com.plv.foundationsdk.log.elog.logcode.linkmic.PLVErrorCodeLinkMicRequest;
import com.plv.foundationsdk.log.elog.logcode.linkmic.PLVErrorCodeLinkMicSystemError;
import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.foundationsdk.model.log.PLVStatisticsBase;
import com.plv.foundationsdk.net.PLVResponseBean;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.PLVrResponseCallback;
import com.plv.foundationsdk.permission.PLVFastPermission;
import com.plv.foundationsdk.permission.PLVOnPermissionCallback;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.linkmic.PLVLinkMicWrapper;
import com.plv.linkmic.log.IPLVLinkMicErrorCodeSender;
import com.plv.linkmic.log.IPLVLinkMicTraceLogSender;
import com.plv.linkmic.model.PLVLinkMicJoinStatus;
import com.plv.linkmic.model.PLVLinkMicJoinSuccess;
import com.plv.linkmic.model.PLVLinkMicMedia;
import com.plv.linkmic.model.PLVLinkMicTokenStatisticsInfo;
import com.plv.linkmic.model.PLVRTCConfig;
import com.plv.linkmic.repository.PLVLinkMicDataRepository;
import com.plv.linkmic.repository.PLVLinkMicEngineToken;
import com.plv.linkmic.repository.PLVLinkMicHttpRequestException;
import com.plv.livescenes.linkmic.IPLVLinkMicManager;
import com.plv.livescenes.linkmic.listener.PLVLinkMicEventListener;
import com.plv.livescenes.linkmic.listener.PLVLinkMicListener;
import com.plv.livescenes.linkmic.vo.PLVLinkMicEngineParam;
import com.plv.livescenes.log.PLVElogEntityCreator;
import com.plv.livescenes.log.linkmic.PLVLinkMicELog;
import com.plv.livescenes.net.PLVApiManager;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.livescenes.streamer.transfer.PLVStreamerInnerDataTransfer;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.linkmic.PLVJoinLeaveSEvent;
import com.plv.socket.event.linkmic.PLVLinkMicTokenEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.socketio.PLVSocketIOClient;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;
import com.plv.thirdpart.blankj.utilcode.util.EncryptUtils;
import com.plv.thirdpart.blankj.utilcode.util.NetworkUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.Utils;
import com.yikaobang.yixue.R2;
import io.socket.client.Ack;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class PLVLinkMicManager implements IPolyvLinkMicManager, IPLVLinkMicManager {
    public static final String APP_ID = "appId";
    public static final String CHANNEL_ID = "channelId";
    public static final String EMIT_MODE = "emitMode";
    public static final String EMIT_UID = "emitUid";
    public static final String EVENT = "EVENT";
    public static final String NICK = "nick";
    public static final String PIC = "pic";
    public static final String PLV_CHAT_SIGN = "polyvChatSign";
    public static final String RAISE_HAND_TIME = "raiseHandTime";
    public static final String ROOM_ID = "roomId";
    public static final String SESSION_ID = "sessionId";
    public static final String STATUS = "status";
    private static final String TAG = "PLVLinkMicManager";
    public static final String TIMESTAMP = "timestamp";
    public static final String TO_ALL = "toAll";
    public static final String TYPE = "type";
    public static final String UID = "uid";
    public static final String USER = "user";
    public static final String USER_ID = "userId";
    public static final String USER_TYPE = "userType";
    public static final String VIEWER_ID = "viewerId";
    private String channelId;
    private PLVLinkMicWrapper coreLinkMicWrapper;
    private IPLVLinkMicErrorCodeSender errorCodeManager;

    @Nullable
    private String groupId;
    private PLVLinkMicTokenEvent joinSuccessToken;
    private PLVLinkMicDataRepository linkMicDataRepository;
    private PLVLinkMicListener linkMicListener;
    private String linkMicUid;
    private PLVSocketMessageObserver.OnMessageListener socketMsgListener;
    private IPLVLinkMicTraceLogSender traceLogSender;
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean enableAutoRequestPermission = true;
    private ArrayList<String> permissions = new ArrayList<>(Arrays.asList(Permission.CAMERA, Permission.RECORD_AUDIO));

    public PLVLinkMicManager(IPLVLinkMicErrorCodeSender iPLVLinkMicErrorCodeSender, IPLVLinkMicTraceLogSender iPLVLinkMicTraceLogSender, PLVLinkMicDataRepository pLVLinkMicDataRepository) {
        this.errorCodeManager = iPLVLinkMicErrorCodeSender;
        iPLVLinkMicErrorCodeSender.setElogVOCreator(new IPLVErrorCodeSender.ELogVOCreator() { // from class: com.plv.livescenes.linkmic.manager.PLVLinkMicManager.1
            @Override // com.plv.foundationsdk.log.elog.IPLVErrorCodeSender.ELogVOCreator
            public <T extends PLVErrorCodeInfoBase> PLVStatisticsBase createElogVO(Class<T> cls, int i2, String str, PLVLogFileBase pLVLogFileBase) {
                return PLVElogEntityCreator.createLiveEntity(cls, i2, str, pLVLogFileBase, "");
            }
        });
        this.traceLogSender = iPLVLinkMicTraceLogSender;
        this.linkMicDataRepository = pLVLinkMicDataRepository;
        this.coreLinkMicWrapper = new PLVLinkMicWrapper();
    }

    private PLVLinkMicTokenStatisticsInfo createTokenStatisticsInfo(PLVLinkMicEngineParam pLVLinkMicEngineParam) {
        PLVLinkMicTokenStatisticsInfo pLVLinkMicTokenStatisticsInfo = new PLVLinkMicTokenStatisticsInfo();
        pLVLinkMicTokenStatisticsInfo.setUid(this.linkMicUid);
        pLVLinkMicTokenStatisticsInfo.setChannelId(this.channelId + "");
        pLVLinkMicTokenStatisticsInfo.setScene(PLVLinkMicConfig.getInstance().getLiveChannelType().getValue());
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
    public String getRoomIdCombineDiscuss() {
        return !TextUtils.isEmpty(this.groupId) ? this.groupId : this.channelId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyLinkMicErrorCode(int i2, String str, Throwable th) {
        IPLVErrorCodeSender.SubmitResult submitResultSubmitError = this.errorCodeManager.submitError(PLVErrorCodeLinkMicRTC.class, i2, str, th);
        this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.OCCUR_ERROR, "rtcType: " + PLVLinkMicConfig.getInstance().getRtcType() + "  errorCode: " + i2);
        PLVLinkMicListener pLVLinkMicListener = this.linkMicListener;
        if (pLVLinkMicListener != null) {
            pLVLinkMicListener.onLinkMicError(submitResultSubmitError.getIntactErrorCode(), new Throwable(submitResultSubmitError.getCounterPartMsgOfCode(), th));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRequestErrorCode(int i2, String str, Throwable th) {
        IPLVErrorCodeSender.SubmitResult submitResultSubmitError = this.errorCodeManager.submitError(PLVErrorCodeLinkMicRequest.class, i2, str, th);
        PLVLinkMicListener pLVLinkMicListener = this.linkMicListener;
        if (pLVLinkMicListener != null) {
            pLVLinkMicListener.onLinkMicError(submitResultSubmitError.getIntactErrorCode(), new Throwable(submitResultSubmitError.getCounterPartMsgOfCode(), th));
        }
    }

    private void requestPermission(final Runnable runnable) {
        PLVFastPermission.getInstance().start(ActivityUtils.getTopActivity(), this.permissions, new PLVOnPermissionCallback() { // from class: com.plv.livescenes.linkmic.manager.PLVLinkMicManager.8
            @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
            public void onAllGranted() {
                runnable.run();
            }

            @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
            public void onPartialGranted(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3) {
                Throwable th = new Throwable("grantedPermissions=" + arrayList.toString() + "\ndeniedPermissions=" + arrayList2 + "\ndeniedForeverP=" + arrayList3);
                IPLVErrorCodeSender.SubmitResult submitResultSubmitError = PLVLinkMicManager.this.errorCodeManager.submitError(PLVErrorCodeLinkMicSystemError.class, 1, "", th);
                if (PLVLinkMicManager.this.linkMicListener != null) {
                    PLVLinkMicManager.this.linkMicListener.onLinkMicError(submitResultSubmitError.getIntactErrorCode(), new Throwable(submitResultSubmitError.getCounterPartMsgOfCode(), th));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRejoinLinkMicMsg() {
        if (this.joinSuccessToken != null) {
            PLVSocketWrapper.getInstance().emit(PLVEventConstant.LinkMic.EVENT_REJOIN_MIC, this.joinSuccessToken.getToken());
        }
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void addEventHandler(PLVLinkMicEventListener pLVLinkMicEventListener) {
        this.coreLinkMicWrapper.addEventHandler(pLVLinkMicEventListener);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public SurfaceView createRendererView(Context context) {
        return this.coreLinkMicWrapper.createRendererView(context);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public TextureView createTextureRenderView(Context context) {
        return this.coreLinkMicWrapper.createTextureRenderView(context);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void destroy() {
        this.linkMicUid = "";
        this.channelId = "";
        this.groupId = null;
        this.joinSuccessToken = null;
        leaveChannel();
        this.linkMicDataRepository.release();
        if (this.socketMsgListener != null) {
            PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.socketMsgListener);
            this.socketMsgListener = null;
        }
        this.coreLinkMicWrapper.destroy();
        this.linkMicListener = null;
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void enableAutoRequestPermission(boolean z2) {
        this.enableAutoRequestPermission = z2;
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void enableLocalVideo(boolean z2) {
        this.coreLinkMicWrapper.enableLocalVideo(z2);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public String getLinkMicUid() {
        if (TextUtils.isEmpty(this.linkMicUid)) {
            this.linkMicUid = PLVLinkMicConfig.getInstance().getLinkMicUid();
        }
        if (TextUtils.isEmpty(this.linkMicUid)) {
            this.linkMicListener.onLinkMicError(R2.attr.activityChooserViewStyle, new RuntimeException("get empty linkMicID"));
        }
        return this.linkMicUid;
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void getLinkStatus(String str, final PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicJoinStatus> iPLVLinkMicDataRepoListener) {
        this.linkMicDataRepository.getInteractStatus(PolyvLiveSDKClient.getInstance().getAppId(), PolyvLiveSDKClient.getInstance().getAppSecret(), getRoomIdCombineDiscuss(), str, true, new PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicJoinStatus>() { // from class: com.plv.livescenes.linkmic.manager.PLVLinkMicManager.5
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
                iPLVLinkMicDataRepoListener.onSuccess(pLVLinkMicJoinStatus);
            }
        });
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void initEngine(PLVLinkMicEngineParam pLVLinkMicEngineParam, PLVLinkMicListener pLVLinkMicListener) {
        this.channelId = pLVLinkMicEngineParam.getChannelId();
        this.groupId = pLVLinkMicEngineParam.getGroupId();
        this.linkMicListener = pLVLinkMicListener;
        String rtcType = PLVLinkMicConfig.getInstance().getRtcType();
        this.linkMicDataRepository.setRTCType(rtcType);
        this.linkMicUid = PLVLinkMicConfig.getInstance().getLinkMicUid();
        final PLVLinkMicTokenStatisticsInfo pLVLinkMicTokenStatisticsInfoCreateTokenStatisticsInfo = createTokenStatisticsInfo(pLVLinkMicEngineParam);
        this.coreLinkMicWrapper.init(Utils.getApp(), new PLVRTCConfig().uid(this.linkMicUid).rtcType(rtcType), new PLVLinkMicWrapper.IPLVTokenRequester() { // from class: com.plv.livescenes.linkmic.manager.PLVLinkMicManager.2
            @Override // com.plv.linkmic.PLVLinkMicWrapper.IPLVTokenRequester
            public void requestToken(final PLVLinkMicWrapper.IPLVTokenRequester.OnRequestTokenListener onRequestTokenListener) {
                PLVLinkMicManager.this.linkMicDataRepository.requestToken(PLVLinkMicManager.this.channelId + "", PolyvLiveSDKClient.getInstance().getAppId(), PolyvLiveSDKClient.getInstance().getAppSecret(), PLVLinkMicManager.this.linkMicUid, PLVLinkMicManager.this.groupId, pLVLinkMicTokenStatisticsInfoCreateTokenStatisticsInfo, new PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicEngineToken>() { // from class: com.plv.livescenes.linkmic.manager.PLVLinkMicManager.2.1
                    @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
                    public void onFail(PLVLinkMicHttpRequestException pLVLinkMicHttpRequestException) {
                        onRequestTokenListener.onFail(pLVLinkMicHttpRequestException);
                        PLVLinkMicManager.this.notifyRequestErrorCode(pLVLinkMicHttpRequestException.getErrorCode(), pLVLinkMicHttpRequestException.getMessage(), pLVLinkMicHttpRequestException);
                    }

                    @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
                    public void onSuccess(PLVLinkMicEngineToken pLVLinkMicEngineToken) {
                        onRequestTokenListener.onSuccess(pLVLinkMicEngineToken);
                    }
                });
            }
        }, new PLVLinkMicWrapper.IPLVLinkMicWrapperCallback() { // from class: com.plv.livescenes.linkmic.manager.PLVLinkMicManager.3
            @Override // com.plv.linkmic.PLVLinkMicWrapper.IPLVLinkMicWrapperCallback
            public void onJoinChannelError(String str) {
                PLVLinkMicManager.this.notifyLinkMicErrorCode(2, "", new Throwable(str));
            }

            @Override // com.plv.linkmic.PLVLinkMicWrapper.IPLVLinkMicWrapperCallback
            public void onLinkMicEngineCreateFailed(Throwable th) {
                PLVLinkMicManager.this.notifyLinkMicErrorCode(4, "", th);
            }

            @Override // com.plv.linkmic.PLVLinkMicWrapper.IPLVLinkMicWrapperCallback
            public void onLinkMicEngineCreated(String str) {
                if (PLVLinkMicConstant.RtcType.RTC_TYPE_A.equals(PLVLinkMicConfig.getInstance().getRtcType())) {
                    PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(new PLVSocketMessageObserver.OnMessageListener() { // from class: com.plv.livescenes.linkmic.manager.PLVLinkMicManager.3.1
                        @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
                        public void onMessage(String str2, String str3, String str4) {
                            if (PLVEventConstant.LinkMic.JOIN_RESPONSE_EVENT.equals(str2)) {
                                String socketUserId = PLVSocketIOClient.getInstance().getSocketUserId();
                                String str5 = PLVLinkMicManager.this.linkMicUid;
                                String appId = PolyvLiveSDKClient.getInstance().getAppId();
                                long jCurrentTimeMillis = System.currentTimeMillis();
                                ArrayMap arrayMap = new ArrayMap();
                                arrayMap.put("channelId", PLVLinkMicManager.this.channelId + "");
                                arrayMap.put(PLVLinkMicManager.VIEWER_ID, socketUserId);
                                arrayMap.put("uid", str5 + "");
                                arrayMap.put("appId", appId);
                                arrayMap.put("timestamp", jCurrentTimeMillis + "");
                                String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(PolyvLiveSDKClient.getInstance().getAppSecret(), arrayMap);
                                PLVResponseExcutor.excuteDataBean(PLVApiManager.getPlvLiveStatusApi().setLinkMicIdRelation(PLVFormatUtils.parseInt(PLVLinkMicManager.this.channelId), socketUserId, str5 + "", appId, jCurrentTimeMillis, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]), String.class, new PLVrResponseCallback<String>() { // from class: com.plv.livescenes.linkmic.manager.PLVLinkMicManager.3.1.1
                                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                                    public void onError(Throwable th) {
                                        super.onError(th);
                                        PLVCommonLog.exception(th);
                                    }

                                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                                    public void onFailure(PLVResponseBean<String> pLVResponseBean) {
                                        super.onFailure(pLVResponseBean);
                                        PLVCommonLog.exception(new Throwable(pLVResponseBean.toString()));
                                    }

                                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                                    public void onFinish() {
                                    }

                                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                                    public void onSuccess(String str6) {
                                        PLVCommonLog.d(PLVLinkMicManager.TAG, "record viewerId and linkMicId to server succeed");
                                    }
                                });
                            }
                        }
                    });
                }
                if (PLVLinkMicManager.this.linkMicListener != null) {
                    PLVLinkMicManager.this.linkMicListener.onLinkMicEngineCreatedSuccess();
                }
                PLVLinkMicManager.this.coreLinkMicWrapper.setBitrate(1);
                PLVLinkMicManager.this.coreLinkMicWrapper.setPushPictureResolutionType(0);
            }
        });
        this.socketMsgListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.plv.livescenes.linkmic.manager.PLVLinkMicManager.4
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String str, String str2, String str3) {
                if ("LOGIN".equals(str2)) {
                    PLVLinkMicManager.this.sendRejoinLinkMicMsg();
                }
            }
        };
        PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.socketMsgListener);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void joinChannel() {
        this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.JOIN_CHANNEL, "加入频道");
        if (this.enableAutoRequestPermission) {
            requestPermission(new Runnable() { // from class: com.plv.livescenes.linkmic.manager.PLVLinkMicManager.6
                @Override // java.lang.Runnable
                public void run() {
                    PLVLinkMicManager.this.coreLinkMicWrapper.joinChannel(PLVLinkMicManager.this.getRoomIdCombineDiscuss());
                }
            });
        } else {
            this.coreLinkMicWrapper.joinChannel(getRoomIdCombineDiscuss());
        }
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void leaveChannel() {
        this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.LEAVE_CHANNEL, "离开频道");
        this.coreLinkMicWrapper.leaveChannel(false);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void muteAllRemoteAudio(boolean z2) {
        this.coreLinkMicWrapper.muteAllRemoteAudio(z2);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void muteAllRemoteVideo(boolean z2) {
        this.coreLinkMicWrapper.muteAllRemoteVideo(z2);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void muteLocalAudio(boolean z2) {
        this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.ENABLE_LOCAL_AUDIO, "user_muteLocalAudio: " + z2);
        this.coreLinkMicWrapper.muteLocalAudio(z2);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void muteLocalVideo(boolean z2) {
        this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.ENABLE_LOCAL_VIDEO, "user_muteLocalVideo: " + z2);
        this.coreLinkMicWrapper.muteLocalVideo(z2);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void muteRemoteAudio(String str, boolean z2) {
        this.coreLinkMicWrapper.muteRemoteAudio(str, z2);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void muteRemoteVideo(String str, boolean z2) {
        this.coreLinkMicWrapper.muteRemoteVideo(str, z2);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void releaseRenderView(View view) {
        this.coreLinkMicWrapper.releaseRenderView(view);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void removeEventHandler(PLVLinkMicEventListener pLVLinkMicEventListener) {
        this.coreLinkMicWrapper.removeEventHandler(pLVLinkMicEventListener);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void resetRequestPermissionList(ArrayList<String> arrayList) {
        this.permissions.clear();
        this.permissions.addAll(arrayList);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void sendJoinLeaveMsg(String str) {
        if (TextUtils.isEmpty(this.linkMicUid) || PLVSocketWrapper.getInstance().getLoginVO() == null) {
            return;
        }
        PLVJoinLeaveSEvent.UserBean userBean = new PLVJoinLeaveSEvent.UserBean();
        userBean.setNick(PLVSocketWrapper.getInstance().getLoginVO().getNickName());
        userBean.setPic(PLVSocketWrapper.getInstance().getLoginVO().getAvatarUrl());
        userBean.setUserId(this.linkMicUid);
        userBean.setUserType(PLVSocketWrapper.getInstance().getLoginVO().getUserType());
        PLVJoinLeaveSEvent pLVJoinLeaveSEvent = new PLVJoinLeaveSEvent();
        pLVJoinLeaveSEvent.setUser(userBean);
        pLVJoinLeaveSEvent.setRoomId(PLVSocketWrapper.getInstance().getLoginVO().getChannelId());
        PLVLinkMicTokenEvent pLVLinkMicTokenEvent = this.joinSuccessToken;
        if (pLVLinkMicTokenEvent != null) {
            pLVJoinLeaveSEvent.setToken(pLVLinkMicTokenEvent.getToken());
            this.joinSuccessToken = null;
        }
        PLVSocketWrapper.getInstance().emit(PLVEventConstant.LinkMic.JOIN_LEAVE_EVENT, PLVGsonUtil.toJson(pLVJoinLeaveSEvent));
        PLVPPTAuthentic pLVPPTAuthentic = new PLVPPTAuthentic();
        pLVPPTAuthentic.setEVENT(PLVEventConstant.LinkMic.TEACHER_SET_PERMISSION);
        pLVPPTAuthentic.setEmitMode(2);
        pLVPPTAuthentic.setRoomId(PLVSocketWrapper.getInstance().getLoginVO().getChannelId());
        pLVPPTAuthentic.setSessionId(str);
        pLVPPTAuthentic.setStatus("0");
        pLVPPTAuthentic.setType("voice");
        pLVPPTAuthentic.setUserId(this.linkMicUid);
        pLVPPTAuthentic.setSign(EncryptUtils.encryptMD5ToString("polyvChatSignEVENTTEACHER_SET_PERMISSIONemitMode2roomId" + PLVSocketWrapper.getInstance().getLoginVO().getChannelId() + SESSION_ID + str + "status0typevoiceuserId" + this.linkMicUid + PLV_CHAT_SIGN).toUpperCase());
        PLVSocketWrapper.getInstance().emit("message", PLVGsonUtil.toJson(pLVPPTAuthentic));
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void sendJoinRequestMsg() throws JSONException {
        PLVCommonLog.d(TAG, "uid :" + this.linkMicUid);
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject.put(ROOM_ID, PLVSocketWrapper.getInstance().getLoginVO().getChannelId());
            jSONObject2.put(NICK, PLVSocketWrapper.getInstance().getLoginVO().getNickName());
            jSONObject2.put("pic", PLVSocketWrapper.getInstance().getLoginVO().getAvatarUrl());
            jSONObject2.put("userId", this.linkMicUid);
            jSONObject2.put(USER_TYPE, PLVSocketWrapper.getInstance().getLoginVO().getUserType());
            jSONObject.put(USER, jSONObject2);
            PLVSocketWrapper.getInstance().emit(PLVEventConstant.LinkMic.JOIN_REQUEST_EVENT, jSONObject.toString());
        } catch (JSONException e2) {
            PLVCommonLog.exception(e2);
        }
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    @Deprecated
    public PLVLinkMicJoinSuccess sendJoinSuccessMsg(String str) {
        return sendJoinSuccessMsg(str, null);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public boolean sendMuteEventMsg(PLVLinkMicMedia pLVLinkMicMedia) {
        pLVLinkMicMedia.setSocketId(PLVSocketWrapper.getInstance().getSocketId());
        PLVSocketWrapper.getInstance().emit("message", pLVLinkMicMedia);
        return true;
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void setBitrate(int i2) {
        if (i2 > PLVStreamerInnerDataTransfer.getInstance().getSupportedMaxBitrate()) {
            return;
        }
        this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.SET_BITRATE, "setBitrate=" + i2);
        this.coreLinkMicWrapper.setBitrate(i2);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void setLocalPreviewMirror(boolean z2) {
        this.coreLinkMicWrapper.setLocalPreviewMirror(z2);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void setLocalPushMirror(boolean z2) {
        this.coreLinkMicWrapper.setLocalPushMirror(z2);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void setPushPictureResolutionType(int i2) {
        this.coreLinkMicWrapper.setPushPictureResolutionType(i2);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void setupLocalVideo(View view, String str) {
        setupLocalVideo(view, 1);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void setupRemoteVideo(View view, String str) {
        this.coreLinkMicWrapper.setupRemoteVideo(view, str.equals(String.valueOf(this.channelId)) ? 2 : 1, str);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void switchCamera() {
        this.traceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.SWITCH_CAMERA, "切换摄像头");
        this.coreLinkMicWrapper.switchCamera();
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void switchRoleToAudience() {
        this.coreLinkMicWrapper.switchRoleToAudience();
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void switchRoleToBroadcaster() {
        this.coreLinkMicWrapper.switchRoleToBroadcaster();
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void takeSnapshot(String str, PLVSugarUtil.Consumer<Bitmap> consumer) {
        this.coreLinkMicWrapper.takeSnapshot(str, consumer);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public PLVLinkMicJoinSuccess sendJoinSuccessMsg(String str, final IPLVLinkMicManager.OnSendJoinSuccessMsgListener onSendJoinSuccessMsgListener) {
        final PLVLinkMicJoinSuccess pLVLinkMicJoinSuccess = new PLVLinkMicJoinSuccess();
        pLVLinkMicJoinSuccess.setRoomId(PLVSocketWrapper.getInstance().getLoginVO().getChannelId());
        pLVLinkMicJoinSuccess.setSessionId(str);
        PLVLinkMicJoinSuccess.UserBean userBean = new PLVLinkMicJoinSuccess.UserBean();
        userBean.setNick(PLVSocketWrapper.getInstance().getLoginVO().getNickName());
        userBean.setPic(PLVSocketWrapper.getInstance().getLoginVO().getAvatarUrl());
        userBean.setUserId(this.linkMicUid);
        userBean.setUserType(PLVSocketWrapper.getInstance().getLoginVO().getUserType());
        pLVLinkMicJoinSuccess.setUser(userBean);
        PLVSocketWrapper.getInstance().emit("joinSuccess", PLVGsonUtil.toJson(pLVLinkMicJoinSuccess), new Ack() { // from class: com.plv.livescenes.linkmic.manager.PLVLinkMicManager.7
            @Override // io.socket.client.Ack
            public void call(Object... objArr) {
                String string = objArr[0].toString();
                PLVCommonLog.d(PLVLinkMicManager.TAG, "SE_JOIN_SUCCESS:" + string);
                PLVLinkMicManager.this.joinSuccessToken = (PLVLinkMicTokenEvent) PLVGsonUtil.fromJson(PLVLinkMicTokenEvent.class, string);
                if (PLVLinkMicManager.this.joinSuccessToken == null) {
                    PLVCommonLog.exception(new Exception("get invalid join token=" + string));
                }
                if (onSendJoinSuccessMsgListener != null) {
                    PLVLinkMicManager.this.handler.post(new Runnable() { // from class: com.plv.livescenes.linkmic.manager.PLVLinkMicManager.7.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass7 anonymousClass7 = AnonymousClass7.this;
                            onSendJoinSuccessMsgListener.onSendJoinSuccessMsg(pLVLinkMicJoinSuccess);
                        }
                    });
                }
            }
        });
        return pLVLinkMicJoinSuccess;
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void setupLocalVideo(View view, int i2) {
        int i3 = 1;
        if (i2 != 1) {
            if (i2 == 2) {
                i3 = 2;
            } else if (i2 == 10) {
                PLVCommonLog.d(TAG, "setupLocalVideo renderMode = RENDER_MODE_NONE");
                return;
            }
        }
        this.coreLinkMicWrapper.setupLocalVideo(view, i3, this.linkMicUid);
    }

    @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager
    public void setupRemoteVideo(View view, String str, int i2) {
        int i3 = 2;
        if (!str.equals(String.valueOf(this.channelId)) && 2 != i2) {
            i3 = 1;
        }
        this.coreLinkMicWrapper.setupRemoteVideo(view, i3, str, i2);
    }
}
