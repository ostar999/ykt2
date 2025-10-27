package com.plv.linkmic.processor.c;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.linkmic.PLVLinkMicEventHandler;
import com.plv.linkmic.model.PLVNetworkStatusVO;
import com.plv.linkmic.screenshare.IPLVScreenShareListener;
import com.plv.rtc.urtc.enummeration.URTCSdkAudioDevice;
import com.plv.rtc.urtc.enummeration.URTCSdkMediaServerStatus;
import com.plv.rtc.urtc.enummeration.URTCSdkMediaType;
import com.plv.rtc.urtc.enummeration.URTCSdkNetWorkQuality;
import com.plv.rtc.urtc.enummeration.URTCSdkStreamType;
import com.plv.rtc.urtc.enummeration.URTCSdkTrackType;
import com.plv.rtc.urtc.listener.URtcSdkEventListener;
import com.plv.rtc.urtc.model.URTCSdkStats;
import com.plv.rtc.urtc.model.URTCSdkStreamInfo;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class a extends com.plv.linkmic.processor.d {
    private static final String TAG = "PLVLinkMicUCloudEventDispatcher";
    private static final int W = 1500;
    private URTCSdkNetWorkQuality X;
    private URTCSdkNetWorkQuality Y;

    @Nullable
    private ObservableEmitter<PLVLinkMicEventHandler.PLVAudioVolumeInfo> Z;

    @Nullable
    private ObservableEmitter<PLVLinkMicEventHandler.PLVAudioVolumeInfo> aa;
    private final Disposable ab;
    private final Disposable ac;
    private final Disposable ad;
    private final URtcSdkEventListener ae;
    private String roomId;
    private String uid;

    /* renamed from: com.plv.linkmic.processor.c.a$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] ao;
        static final /* synthetic */ int[] ap;

        static {
            int[] iArr = new int[URTCSdkNetWorkQuality.values().length];
            ap = iArr;
            try {
                iArr[URTCSdkNetWorkQuality.U_CLOUD_RTC_SDK_NET_WORK_QUALITY_EXCELLENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                ap[URTCSdkNetWorkQuality.U_CLOUD_RTC_SDK_NET_WORK_QUALITY_GOOD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                ap[URTCSdkNetWorkQuality.U_CLOUD_RTC_SDK_NET_WORK_QUALITY_NORMAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                ap[URTCSdkNetWorkQuality.U_CLOUD_RTC_SDK_NET_WORK_QUALITY_BAD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                ap[URTCSdkNetWorkQuality.U_CLOUD_RTC_SDK_NET_WORK_QUALITY_TERRIBLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                ap[URTCSdkNetWorkQuality.U_CLOUD_RTC_SDK_NET_WORK_QUALITY_DOWN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                ap[URTCSdkNetWorkQuality.U_CLOUD_RTC_SDK_NET_WORK_QUALITY_UNKNOWN.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[URTCSdkMediaServerStatus.values().length];
            ao = iArr2;
            try {
                iArr2[URTCSdkMediaServerStatus.RELAY_STATUS_START.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                ao[URTCSdkMediaServerStatus.RELAY_STATUS_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public a(String str) {
        URTCSdkNetWorkQuality uRTCSdkNetWorkQuality = URTCSdkNetWorkQuality.U_CLOUD_RTC_SDK_NET_WORK_QUALITY_UNKNOWN;
        this.X = uRTCSdkNetWorkQuality;
        this.Y = uRTCSdkNetWorkQuality;
        this.ae = new URtcSdkEventListener() { // from class: com.plv.linkmic.processor.c.a.1
            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onAddStreams(int i2, String str2) {
                PLVCommonLog.d(a.TAG, "onAddStreams：" + i2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onAudioDeviceChanged(URTCSdkAudioDevice uRTCSdkAudioDevice) {
                PLVCommonLog.d(a.TAG, "onServerBroadCastMsg：" + uRTCSdkAudioDevice);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onDelStreams(int i2, String str2) {
                PLVCommonLog.d(a.TAG, "onDelStreams：" + i2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onError(int i2) {
                PLVCommonLog.d(a.TAG, "onError -> error=" + i2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onJoinRoomResult(int i2, String str2, String str3) {
                PLVCommonLog.d(a.TAG, "onJoinRoomResult code=" + i2 + " \tmsg=" + str2 + " \troomid=" + str3);
                if (!"succeed".equals(str2)) {
                    PLVCommonLog.i(a.TAG, "onJoinRoomResult, waiting for success result");
                    return;
                }
                a.this.roomId = str3;
                if (((com.plv.linkmic.processor.d) a.this).f10818a.isEmpty()) {
                    return;
                }
                ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.c.a.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                        while (it.hasNext()) {
                            ((PLVLinkMicEventHandler) it.next()).onJoinChannelSuccess(a.this.uid);
                        }
                    }
                });
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onKickoff(int i2) {
                PLVCommonLog.d(a.TAG, "onKickoff -> code=" + i2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onLeaveRoomResult(int i2, String str2, String str3) {
                PLVCommonLog.d(a.TAG, "onLeaveRoomResult -> code=" + i2 + " msg=" + str2 + " roomId=" + str3);
                if (((com.plv.linkmic.processor.d) a.this).f10818a.isEmpty()) {
                    return;
                }
                ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.c.a.1.7
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                        while (it.hasNext()) {
                            ((PLVLinkMicEventHandler) it.next()).onLeaveChannel();
                        }
                    }
                });
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onLocalAudioLevel(int i2) {
                if (a.this.Z != null) {
                    a.this.Z.onNext(new PLVLinkMicEventHandler.PLVAudioVolumeInfo(a.this.uid, i2));
                }
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onLocalPublish(int i2, String str2, URTCSdkStreamInfo uRTCSdkStreamInfo) {
                PLVCommonLog.d(a.TAG, "onLocalPublish , code=" + i2 + "\nmsg=" + str2);
                if (uRTCSdkStreamInfo == null || uRTCSdkStreamInfo.getMediaType() != URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_SCREEN) {
                    return;
                }
                ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.c.a.1.10
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                        while (it.hasNext()) {
                            ((PLVLinkMicEventHandler) it.next()).onScreenShare(true, IPLVScreenShareListener.PLV_SCREEN_SHARE_OK);
                        }
                    }
                });
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onLocalStreamMuteRsp(int i2, String str2, URTCSdkMediaType uRTCSdkMediaType, URTCSdkTrackType uRTCSdkTrackType, boolean z2) {
                PLVCommonLog.d(a.TAG, "onLocalStreamMuteRsp -> code=" + i2 + " msg=" + str2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onLocalUnPublish(int i2, String str2, URTCSdkStreamInfo uRTCSdkStreamInfo) {
                PLVCommonLog.d(a.TAG, "onLocalUnPublish , code=" + i2 + "\nmsg=" + str2);
                if (((com.plv.linkmic.processor.d) a.this).f10818a.isEmpty()) {
                    return;
                }
                if (uRTCSdkStreamInfo == null || uRTCSdkStreamInfo.getMediaType() != URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_SCREEN) {
                    ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.c.a.1.12
                        @Override // java.lang.Runnable
                        public void run() {
                            Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                            while (it.hasNext()) {
                                ((PLVLinkMicEventHandler) it.next()).onStreamUnpublished(null);
                            }
                        }
                    });
                } else {
                    ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.c.a.1.11
                        @Override // java.lang.Runnable
                        public void run() {
                            Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                            while (it.hasNext()) {
                                ((PLVLinkMicEventHandler) it.next()).onScreenShare(false, IPLVScreenShareListener.PLV_SCREEN_SHARE_OK);
                            }
                        }
                    });
                }
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onLocalUnPublishOnly(int i2, String str2, URTCSdkStreamInfo uRTCSdkStreamInfo) {
                PLVCommonLog.d(a.TAG, "onLocalUnPublishOnly() called with: i = [" + i2 + "], s = [" + str2 + "], uCloudRtcSdkStreamInfo = [" + uRTCSdkStreamInfo + StrPool.BRACKET_END);
                ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.c.a.1.13
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                        while (it.hasNext()) {
                            ((PLVLinkMicEventHandler) it.next()).onStreamUnpublished(null);
                        }
                    }
                });
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onLogOffNotify(int i2, String str2) {
                PLVCommonLog.d(a.TAG, "onLogOffNotify：" + i2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onLogOffUsers(int i2, String str2) {
                PLVCommonLog.d(a.TAG, "onLogOffUsers：" + i2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onMixStart(int i2, String str2, String str3) {
                PLVCommonLog.d(a.TAG, "onMixStart：" + i2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onMixStop(int i2, String str2, String str3) {
                PLVCommonLog.d(a.TAG, "onMixStop：" + i2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onMsgNotify(int i2, String str2) {
                PLVCommonLog.d(a.TAG, "onMsgNotify：" + i2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onNetWorkQuality(String str2, URTCSdkStreamType uRTCSdkStreamType, URTCSdkMediaType uRTCSdkMediaType, URTCSdkNetWorkQuality uRTCSdkNetWorkQuality2) {
                if (TextUtils.isEmpty(a.this.uid)) {
                    return;
                }
                if (a.this.uid.equals(str2)) {
                    a.this.X = uRTCSdkNetWorkQuality2;
                } else {
                    a.this.Y = uRTCSdkNetWorkQuality2;
                }
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onPeerLostConnection(int i2, URTCSdkStreamInfo uRTCSdkStreamInfo) {
                PLVCommonLog.d(a.TAG, "onPeerLostConnection：" + i2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onRecordStart(int i2, String str2) {
                PLVCommonLog.d(a.TAG, "onRecordStart：" + i2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onRecordStop(int i2) {
                PLVCommonLog.d(a.TAG, "onRecordStop：" + i2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onRejoinRoomResult(final String str2) {
                PLVCommonLog.d(a.TAG, "onRejoinRoomResult -> roomId=" + str2);
                ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.c.a.1.9
                    @Override // java.lang.Runnable
                    public void run() {
                        for (PLVLinkMicEventHandler pLVLinkMicEventHandler : ((com.plv.linkmic.processor.d) a.this).f10818a.keySet()) {
                            pLVLinkMicEventHandler.onRejoinChannelSuccess(str2, a.this.uid);
                            pLVLinkMicEventHandler.onNetworkQuality(11);
                        }
                    }
                });
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onRejoiningRoom(String str2) {
                PLVCommonLog.d(a.TAG, "onRejoiningRoom -> roomId=" + str2);
                ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.c.a.1.8
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                        while (it.hasNext()) {
                            ((PLVLinkMicEventHandler) it.next()).onNetworkQuality(14);
                        }
                    }
                });
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onRelayStatusNotify(URTCSdkMediaServerStatus uRTCSdkMediaServerStatus, int i2, String str2, String str3, String str4, String str5, final String[] strArr) {
                PLVCommonLog.d(a.TAG, "onRelayStatusNotify() called with: uCloudRtcSdkMediaServiceStatus = [" + uRTCSdkMediaServerStatus + "], code = [" + i2 + "], msg = [" + str2 + "], userId = [" + str3 + "], roomId = [" + str4 + "], mixId = [" + str5 + "], pushUrls = [" + strArr + StrPool.BRACKET_END);
                if (!((com.plv.linkmic.processor.d) a.this).f10818a.isEmpty() && AnonymousClass2.ao[uRTCSdkMediaServerStatus.ordinal()] == 1) {
                    ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.c.a.1.6
                        @Override // java.lang.Runnable
                        public void run() {
                            for (PLVLinkMicEventHandler pLVLinkMicEventHandler : ((com.plv.linkmic.processor.d) a.this).f10818a.keySet()) {
                                String[] strArr2 = strArr;
                                pLVLinkMicEventHandler.onStreamPublished(strArr2.length > 0 ? strArr2[0] : "", 0);
                            }
                        }
                    });
                }
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onRemoteAudioLevel(String str2, int i2) {
                if (a.this.aa != null) {
                    a.this.aa.onNext(new PLVLinkMicEventHandler.PLVAudioVolumeInfo(str2, i2));
                }
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onRemotePublish(URTCSdkStreamInfo uRTCSdkStreamInfo) {
                PLVCommonLog.d(a.TAG, "onRemotePublish -> info=" + uRTCSdkStreamInfo.toString());
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onRemoteRTCStats(final URTCSdkStats uRTCSdkStats) {
                PLVSugarUtil.foreach(((com.plv.linkmic.processor.d) a.this).f10818a.keySet(), new PLVSugarUtil.Consumer<PLVLinkMicEventHandler>() { // from class: com.plv.linkmic.processor.c.a.1.5
                    @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
                    /* renamed from: a, reason: merged with bridge method [inline-methods] */
                    public void accept(PLVLinkMicEventHandler pLVLinkMicEventHandler) {
                        pLVLinkMicEventHandler.onRemoteNetworkStatus(new PLVNetworkStatusVO(uRTCSdkStats));
                    }
                });
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onRemoteStreamMuteRsp(int i2, String str2, String str3, URTCSdkMediaType uRTCSdkMediaType, URTCSdkTrackType uRTCSdkTrackType, boolean z2) {
                PLVCommonLog.d(a.TAG, "onRemoteStreamMuteRsp -> code=" + i2 + " uid=" + str3 + " msg=" + str2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onRemoteTrackNotify(final String str2, final URTCSdkMediaType uRTCSdkMediaType, final URTCSdkTrackType uRTCSdkTrackType, final boolean z2) {
                PLVCommonLog.d(a.TAG, "onRemoteTrackNotify() called with: uid = [" + str2 + "], mediatype = [" + uRTCSdkMediaType + "], tracktype = [" + uRTCSdkTrackType + "], mute = [" + z2 + StrPool.BRACKET_END);
                if (((com.plv.linkmic.processor.d) a.this).f10818a.isEmpty()) {
                    return;
                }
                ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.c.a.1.3
                    @Override // java.lang.Runnable
                    public void run() {
                        for (PLVLinkMicEventHandler pLVLinkMicEventHandler : ((com.plv.linkmic.processor.d) a.this).f10818a.keySet()) {
                            URTCSdkMediaType uRTCSdkMediaType2 = uRTCSdkMediaType;
                            if (uRTCSdkMediaType2 == URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_VIDEO) {
                                URTCSdkTrackType uRTCSdkTrackType2 = uRTCSdkTrackType;
                                if (uRTCSdkTrackType2 == URTCSdkTrackType.UCLOUD_RTC_SDK_TRACK_TYPE_AUDIO) {
                                    pLVLinkMicEventHandler.onUserMuteAudio(str2, z2, 1);
                                } else if (uRTCSdkTrackType2 == URTCSdkTrackType.UCLOUD_RTC_SDK_TRACK_TYPE_VIDEO) {
                                    pLVLinkMicEventHandler.onUserMuteVideo(str2, z2, 1);
                                }
                            } else if (uRTCSdkMediaType2 == URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_SCREEN) {
                                URTCSdkTrackType uRTCSdkTrackType3 = uRTCSdkTrackType;
                                if (uRTCSdkTrackType3 == URTCSdkTrackType.UCLOUD_RTC_SDK_TRACK_TYPE_AUDIO) {
                                    pLVLinkMicEventHandler.onUserMuteAudio(str2, z2, 2);
                                } else if (uRTCSdkTrackType3 == URTCSdkTrackType.UCLOUD_RTC_SDK_TRACK_TYPE_VIDEO) {
                                    pLVLinkMicEventHandler.onUserMuteVideo(str2, z2, 2);
                                }
                            }
                            URTCSdkTrackType uRTCSdkTrackType4 = uRTCSdkTrackType;
                            if (uRTCSdkTrackType4 == URTCSdkTrackType.UCLOUD_RTC_SDK_TRACK_TYPE_AUDIO) {
                                pLVLinkMicEventHandler.onUserMuteAudio(str2, z2);
                            } else if (uRTCSdkTrackType4 == URTCSdkTrackType.UCLOUD_RTC_SDK_TRACK_TYPE_VIDEO) {
                                pLVLinkMicEventHandler.onUserMuteVideo(str2, z2);
                            }
                        }
                    }
                });
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onRemoteUnPublish(URTCSdkStreamInfo uRTCSdkStreamInfo) {
                PLVCommonLog.d(a.TAG, "onRemoteUnPublish -> info=" + uRTCSdkStreamInfo.toString());
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onRemoteUserJoin(String str2) {
                PLVCommonLog.d(a.TAG, "onRemoteUserJoin -> uid=" + str2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onRemoteUserLeave(String str2, int i2) {
                PLVCommonLog.d(a.TAG, "onRemoteUserLeave -> uid=" + str2 + " reason=" + i2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onSendRTCStats(final URTCSdkStats uRTCSdkStats) {
                PLVSugarUtil.foreach(((com.plv.linkmic.processor.d) a.this).f10818a.keySet(), new PLVSugarUtil.Consumer<PLVLinkMicEventHandler>() { // from class: com.plv.linkmic.processor.c.a.1.4
                    @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
                    /* renamed from: a, reason: merged with bridge method [inline-methods] */
                    public void accept(PLVLinkMicEventHandler pLVLinkMicEventHandler) {
                        pLVLinkMicEventHandler.onUpstreamNetworkStatus(new PLVNetworkStatusVO(uRTCSdkStats));
                    }
                });
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onServerBroadCastMsg(String str2, String str3) {
                PLVCommonLog.d(a.TAG, "onServerBroadCastMsg：" + str2);
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onServerDisconnect() {
                PLVCommonLog.d(a.TAG, "onServerDisconnect");
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onSubscribeResult(int i2, String str2, final URTCSdkStreamInfo uRTCSdkStreamInfo) {
                PLVCommonLog.d(a.TAG, "onSubscribeResult -> code=" + i2 + " msg=" + str2 + " info=" + uRTCSdkStreamInfo);
                if (((com.plv.linkmic.processor.d) a.this).f10818a.isEmpty()) {
                    return;
                }
                ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.c.a.1.14
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                        while (it.hasNext()) {
                            ((PLVLinkMicEventHandler) it.next()).onUserJoined(uRTCSdkStreamInfo.getUId());
                        }
                    }
                });
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onUnSubscribeResult(int i2, String str2, final URTCSdkStreamInfo uRTCSdkStreamInfo) {
                PLVCommonLog.d(a.TAG, "onUnSubscribeResult -> code=" + i2 + " msg=" + str2 + " info=" + uRTCSdkStreamInfo);
                if (((com.plv.linkmic.processor.d) a.this).f10818a.isEmpty()) {
                    return;
                }
                ((com.plv.linkmic.processor.d) a.this).f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.c.a.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                        while (it.hasNext()) {
                            ((PLVLinkMicEventHandler) it.next()).onUserOffline(uRTCSdkStreamInfo.getUId());
                        }
                    }
                });
            }

            @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
            public void onWarning(int i2) {
                PLVCommonLog.d(a.TAG, "onWarning -> warn=" + i2);
            }
        };
        this.uid = str;
        Observable observableCreate = Observable.create(new ObservableOnSubscribe<PLVLinkMicEventHandler.PLVAudioVolumeInfo>() { // from class: com.plv.linkmic.processor.c.a.5
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<PLVLinkMicEventHandler.PLVAudioVolumeInfo> observableEmitter) throws Exception {
                a.this.Z = observableEmitter;
            }
        });
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        this.ab = observableCreate.sample(1500L, timeUnit).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PLVLinkMicEventHandler.PLVAudioVolumeInfo>() { // from class: com.plv.linkmic.processor.c.a.3
            @Override // io.reactivex.functions.Consumer
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void accept(PLVLinkMicEventHandler.PLVAudioVolumeInfo pLVAudioVolumeInfo) throws Exception {
                Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                while (it.hasNext()) {
                    ((PLVLinkMicEventHandler) it.next()).onLocalAudioVolumeIndication(pLVAudioVolumeInfo);
                }
            }
        }, new Consumer<Throwable>() { // from class: com.plv.linkmic.processor.c.a.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVCommonLog.e(a.TAG, "PLVLinkMicUCloudEventDispatcher：" + th.getMessage());
            }
        });
        this.ac = Observable.create(new ObservableOnSubscribe<PLVLinkMicEventHandler.PLVAudioVolumeInfo>() { // from class: com.plv.linkmic.processor.c.a.9
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<PLVLinkMicEventHandler.PLVAudioVolumeInfo> observableEmitter) throws Exception {
                a.this.aa = observableEmitter;
            }
        }).observeOn(Schedulers.io()).buffer(1500L, timeUnit).map(new Function<List<PLVLinkMicEventHandler.PLVAudioVolumeInfo>, List<PLVLinkMicEventHandler.PLVAudioVolumeInfo>>() { // from class: com.plv.linkmic.processor.c.a.8
            @Override // io.reactivex.functions.Function
            public List<PLVLinkMicEventHandler.PLVAudioVolumeInfo> apply(List<PLVLinkMicEventHandler.PLVAudioVolumeInfo> list) throws Exception {
                HashMap map = new HashMap();
                for (PLVLinkMicEventHandler.PLVAudioVolumeInfo pLVAudioVolumeInfo : list) {
                    map.put(pLVAudioVolumeInfo.getUid(), Integer.valueOf(pLVAudioVolumeInfo.getVolume()));
                }
                ArrayList arrayList = new ArrayList();
                for (Map.Entry entry : map.entrySet()) {
                    arrayList.add(new PLVLinkMicEventHandler.PLVAudioVolumeInfo((String) entry.getKey(), ((Integer) entry.getValue()).intValue()));
                }
                return arrayList;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<PLVLinkMicEventHandler.PLVAudioVolumeInfo>>() { // from class: com.plv.linkmic.processor.c.a.6
            @Override // io.reactivex.functions.Consumer
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void accept(List<PLVLinkMicEventHandler.PLVAudioVolumeInfo> list) throws Exception {
                PLVLinkMicEventHandler.PLVAudioVolumeInfo[] pLVAudioVolumeInfoArr = (PLVLinkMicEventHandler.PLVAudioVolumeInfo[]) list.toArray(new PLVLinkMicEventHandler.PLVAudioVolumeInfo[0]);
                Iterator it = ((com.plv.linkmic.processor.d) a.this).f10818a.keySet().iterator();
                while (it.hasNext()) {
                    ((PLVLinkMicEventHandler) it.next()).onRemoteAudioVolumeIndication(pLVAudioVolumeInfoArr);
                }
            }
        }, new Consumer<Throwable>() { // from class: com.plv.linkmic.processor.c.a.7
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVCommonLog.e(a.TAG, "remoteVolumeDisposable:" + th.getMessage());
            }
        });
        this.ad = PLVRxTimer.timer(1500, new Consumer<Long>() { // from class: com.plv.linkmic.processor.c.a.10
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                a aVar = a.this;
                int iA = aVar.a(aVar.X);
                a aVar2 = a.this;
                aVar.a(iA, aVar2.a(aVar2.Y));
            }
        });
    }

    @Override // com.plv.linkmic.processor.d, com.plv.linkmic.processor.a
    public void destroy() {
        super.destroy();
        dispose(this.ab);
        dispose(this.ac);
        dispose(this.ad);
        this.Z = null;
        this.aa = null;
    }

    @Override // com.plv.linkmic.processor.a
    public Object b() {
        return this.ae;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(URTCSdkNetWorkQuality uRTCSdkNetWorkQuality) {
        switch (AnonymousClass2.ap[uRTCSdkNetWorkQuality.ordinal()]) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                return 0;
        }
    }
}
