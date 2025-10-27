package com.plv.linkmic.processor.c;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import androidx.annotation.Nullable;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.linkmic.PLVLinkMicDataConfig;
import com.plv.linkmic.PLVLinkMicEventHandler;
import com.plv.linkmic.model.PLVRTCConfig;
import com.plv.linkmic.processor.PLVVideoDimensionBitrate;
import com.plv.linkmic.processor.e;
import com.plv.linkmic.repository.PLVLinkMicEngineToken;
import com.plv.livescenes.log.linkmic.PLVLinkMicELog;
import com.plv.rtc.urtc.PLVURTCEngine;
import com.plv.rtc.urtc.PLVURTCEngineFactory;
import com.plv.rtc.urtc.URTCSdkEnv;
import com.plv.rtc.urtc.constant.URTCErrorCode;
import com.plv.rtc.urtc.enummeration.URTCSdkCaptureMode;
import com.plv.rtc.urtc.enummeration.URTCSdkLogLevel;
import com.plv.rtc.urtc.enummeration.URTCSdkMediaType;
import com.plv.rtc.urtc.enummeration.URTCSdkMode;
import com.plv.rtc.urtc.enummeration.URTCSdkPushEncode;
import com.plv.rtc.urtc.enummeration.URTCSdkPushOrientation;
import com.plv.rtc.urtc.enummeration.URTCSdkRoomType;
import com.plv.rtc.urtc.enummeration.URTCSdkScaleType;
import com.plv.rtc.urtc.enummeration.URTCSdkStreamRole;
import com.plv.rtc.urtc.enummeration.URTCSdkTrackType;
import com.plv.rtc.urtc.enummeration.URTCSdkVideoProfile;
import com.plv.rtc.urtc.listener.URTCFirstFrameRendered;
import com.plv.rtc.urtc.listener.URTCOnFrameListener;
import com.plv.rtc.urtc.listener.URtcSdkEventListener;
import com.plv.rtc.urtc.model.URTCSdkAuthInfo;
import com.plv.rtc.urtc.model.URTCSdkMixProfile;
import com.plv.rtc.urtc.model.URTCSdkStreamInfo;
import com.plv.rtc.urtc.view.URTCRendererViewWrapper;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;

/* loaded from: classes4.dex */
public class b extends e {
    private static final String TAG = "b";
    private static final int aq = 5004;
    private static final int ar = 5006;

    @Nullable
    private URTCRendererViewWrapper aA;
    private c aB;
    private PLVURTCEngine aC;

    @Nullable
    private URtcSdkEventListener aD;
    private String as;
    private URTCSdkMixProfile.MixParamsBuilder av;
    private URTCSdkVideoProfile ay;
    private com.plv.linkmic.processor.a linkMicEventDispatcher;

    /* renamed from: p, reason: collision with root package name */
    private String f10814p;

    /* renamed from: q, reason: collision with root package name */
    private String f10815q;

    /* renamed from: r, reason: collision with root package name */
    private PLVRTCConfig f10816r;
    private String uid;

    /* renamed from: t, reason: collision with root package name */
    private int f10817t = 1;
    private boolean at = false;
    private boolean au = false;
    private final ConcurrentHashMap<String, d> M = new ConcurrentHashMap<>();
    private final Set<String> aw = new HashSet();
    private final Set<String> ax = new HashSet();
    private boolean K = true;
    private boolean az = false;
    private final Handler handler = new Handler(Looper.getMainLooper());

    /* renamed from: com.plv.linkmic.processor.c.b$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] R;
        static final /* synthetic */ int[] aG;

        static {
            int[] iArr = new int[URTCSdkMediaType.values().length];
            aG = iArr;
            try {
                iArr[URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_VIDEO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                aG[URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_SCREEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[PLVVideoDimensionBitrate.values().length];
            R = iArr2;
            try {
                iArr2[PLVVideoDimensionBitrate.BITRATE_STANDARD_16_9_15FPS.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_STANDARD_16_9_30FPS.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_STANDARD_4_3_15FPS.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_STANDARD_4_3_30FPS.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_HIGH_16_9_15FPS.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_HIGH_16_9_30FPS.ordinal()] = 6;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_HIGH_4_3_15FPS.ordinal()] = 7;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_HIGH_4_3_30FPS.ordinal()] = 8;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_SUPER_16_9_15FPS.ordinal()] = 9;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_SUPER_16_9_30FPS.ordinal()] = 10;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_SUPER_4_3_15FPS.ordinal()] = 11;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_SUPER_4_3_30FPS.ordinal()] = 12;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    public class a extends com.plv.linkmic.processor.c.c {
        public a(URtcSdkEventListener uRtcSdkEventListener) {
            super(uRtcSdkEventListener);
        }

        @Override // com.plv.linkmic.processor.c.c, com.plv.rtc.urtc.listener.URtcSdkEventListener
        public void onRemotePublish(URTCSdkStreamInfo uRTCSdkStreamInfo) {
            PLVCommonLog.d(b.TAG, "PLVUCloudDoubleStreamDecorator.onRemotePublish");
            super.onRemotePublish(uRTCSdkStreamInfo);
            if (b.this.aC == null || b.this.uid.equals(uRTCSdkStreamInfo.getUId())) {
                return;
            }
            final String uId = uRTCSdkStreamInfo.getUId();
            d dVar = (d) b.this.M.get(uId);
            if (dVar == null) {
                dVar = new d(uId);
                b.this.M.put(uId, dVar);
            }
            dVar.c(uRTCSdkStreamInfo);
            b.this.aC.subscribe(uRTCSdkStreamInfo);
            URTCSdkMediaType mediaType = uRTCSdkStreamInfo.getMediaType();
            if (mediaType != null) {
                int i2 = AnonymousClass4.aG[mediaType.ordinal()];
                if (i2 == 1) {
                    Runnable runnable = new Runnable() { // from class: com.plv.linkmic.processor.c.b.a.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Iterator<PLVLinkMicEventHandler> it = b.this.linkMicEventDispatcher.a().iterator();
                            while (it.hasNext()) {
                                it.next().onRemoteStreamOpen(uId, 1);
                            }
                        }
                    };
                    if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                        runnable.run();
                        return;
                    } else {
                        b.this.handler.post(runnable);
                        return;
                    }
                }
                if (i2 != 2) {
                    return;
                }
                Runnable runnable2 = new Runnable() { // from class: com.plv.linkmic.processor.c.b.a.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator<PLVLinkMicEventHandler> it = b.this.linkMicEventDispatcher.a().iterator();
                        while (it.hasNext()) {
                            it.next().onRemoteStreamOpen(uId, 2);
                        }
                    }
                };
                if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                    runnable2.run();
                } else {
                    b.this.handler.post(runnable2);
                }
            }
        }

        @Override // com.plv.linkmic.processor.c.c, com.plv.rtc.urtc.listener.URtcSdkEventListener
        public void onRemoteUnPublish(URTCSdkStreamInfo uRTCSdkStreamInfo) {
            PLVCommonLog.d(b.TAG, "PLVUCloudDoubleStreamDecorator.onRemoteUnPublish");
            super.onRemoteUnPublish(uRTCSdkStreamInfo);
            if (b.this.aC == null) {
                return;
            }
            b.this.aC.unSubscribe(uRTCSdkStreamInfo);
            b.this.aC.stopRemoteView(uRTCSdkStreamInfo);
            final String uId = uRTCSdkStreamInfo.getUId();
            d dVar = (d) b.this.M.get(uId);
            if (dVar == null) {
                return;
            }
            URTCRendererViewWrapper uRTCRendererViewWrapperP = dVar.p();
            URTCRendererViewWrapper uRTCRendererViewWrapperQ = dVar.q();
            URTCRendererViewWrapper uRTCRendererViewWrapperO = dVar.o();
            URTCSdkStreamInfo uRTCSdkStreamInfoM = dVar.m();
            URTCSdkStreamInfo uRTCSdkStreamInfoN = dVar.n();
            URTCSdkMediaType mediaType = uRTCSdkStreamInfo.getMediaType();
            if (mediaType == null) {
                mediaType = URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_NULL;
            }
            int i2 = AnonymousClass4.aG[mediaType.ordinal()];
            if (i2 == 1) {
                dVar.b((URTCSdkStreamInfo) null);
                if (uRTCRendererViewWrapperP != null) {
                    uRTCRendererViewWrapperP.release();
                }
                if (uRTCRendererViewWrapperO != null && uRTCSdkStreamInfoM != null) {
                    b.this.aC.startRemoteView(uRTCSdkStreamInfoM, uRTCRendererViewWrapperO.getRenderer(), b.this.d(dVar.getRenderMode()), null);
                }
                Runnable runnable = new Runnable() { // from class: com.plv.linkmic.processor.c.b.a.4
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator<PLVLinkMicEventHandler> it = b.this.linkMicEventDispatcher.a().iterator();
                        while (it.hasNext()) {
                            it.next().onRemoteStreamClose(uId, 1);
                        }
                    }
                };
                if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                    runnable.run();
                } else {
                    b.this.handler.post(runnable);
                }
            } else if (i2 == 2) {
                dVar.a((URTCSdkStreamInfo) null);
                if (uRTCRendererViewWrapperQ != null) {
                    uRTCRendererViewWrapperQ.release();
                }
                if (uRTCRendererViewWrapperO != null && uRTCSdkStreamInfoN != null) {
                    b.this.aC.startRemoteView(uRTCSdkStreamInfoN, uRTCRendererViewWrapperO.getRenderer(), b.this.d(dVar.getRenderMode()), null);
                }
                Runnable runnable2 = new Runnable() { // from class: com.plv.linkmic.processor.c.b.a.5
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator<PLVLinkMicEventHandler> it = b.this.linkMicEventDispatcher.a().iterator();
                        while (it.hasNext()) {
                            it.next().onRemoteStreamClose(uId, 2);
                        }
                    }
                };
                if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                    runnable2.run();
                } else {
                    b.this.handler.post(runnable2);
                }
            }
            if (dVar.n() == null && dVar.m() == null) {
                if (uRTCRendererViewWrapperO != null) {
                    uRTCRendererViewWrapperO.release();
                }
                b.this.M.remove(uId);
                PLVCommonLog.d(b.TAG, "PLVUCloudDoubleStreamDecorator.onRemoteUnPublish, remove user " + uId + " from remoteUsersMap");
                super.onUnSubscribeResult(0, "", uRTCSdkStreamInfo);
            }
        }

        @Override // com.plv.linkmic.processor.c.c, com.plv.rtc.urtc.listener.URtcSdkEventListener
        public void onSubscribeResult(int i2, String str, URTCSdkStreamInfo uRTCSdkStreamInfo) {
            PLVCommonLog.d(b.TAG, "PLVUCloudDoubleStreamDecorator.onSubscribeResult");
            if (b.this.aC == null) {
                return;
            }
            d dVar = (d) b.this.M.get(uRTCSdkStreamInfo.getUId());
            if (dVar == null) {
                return;
            }
            if (!dVar.r()) {
                dVar.c(true);
                super.onSubscribeResult(i2, str, uRTCSdkStreamInfo);
            }
            dVar.c(uRTCSdkStreamInfo);
            URTCSdkStreamInfo uRTCSdkStreamInfoN = dVar.n();
            URTCSdkStreamInfo uRTCSdkStreamInfoM = dVar.m();
            URTCRendererViewWrapper uRTCRendererViewWrapperO = dVar.o();
            URTCRendererViewWrapper uRTCRendererViewWrapperP = dVar.p();
            URTCRendererViewWrapper uRTCRendererViewWrapperQ = dVar.q();
            URTCFirstFrameRendered uRTCFirstFrameRendered = new URTCFirstFrameRendered() { // from class: com.plv.linkmic.processor.c.b.a.3
                @Override // com.plv.rtc.urtc.listener.URTCFirstFrameRendered
                public void onFirstFrameRender(URTCSdkStreamInfo uRTCSdkStreamInfo2, View view) {
                    if (uRTCSdkStreamInfo2.getUId() == null) {
                        return;
                    }
                    if (b.this.aw.contains(uRTCSdkStreamInfo2.getUId())) {
                        b.this.muteRemoteAudio(uRTCSdkStreamInfo2.getUId(), true);
                    }
                    if (b.this.ax.contains(uRTCSdkStreamInfo2.getUId())) {
                        b.this.muteRemoteVideo(uRTCSdkStreamInfo2.getUId(), true);
                    }
                }
            };
            if (uRTCRendererViewWrapperP != null && uRTCSdkStreamInfoN != null) {
                b.this.aC.startRemoteView(uRTCSdkStreamInfoN, uRTCRendererViewWrapperP.getRenderer(), b.this.d(dVar.getRenderMode()), uRTCFirstFrameRendered);
            }
            if (uRTCRendererViewWrapperQ != null && uRTCSdkStreamInfoM != null) {
                b.this.aC.startRemoteView(uRTCSdkStreamInfoM, uRTCRendererViewWrapperQ.getRenderer(), b.this.d(dVar.getRenderMode()), uRTCFirstFrameRendered);
            }
            if (uRTCRendererViewWrapperO != null) {
                if (uRTCSdkStreamInfoM != null) {
                    b.this.aC.stopRemoteView(uRTCSdkStreamInfoN);
                    b.this.aC.startRemoteView(uRTCSdkStreamInfoM, uRTCRendererViewWrapperO.getRenderer(), b.this.d(dVar.getRenderMode()), uRTCFirstFrameRendered);
                } else if (uRTCSdkStreamInfoN != null) {
                    b.this.aC.stopRemoteView(uRTCSdkStreamInfoN);
                    b.this.aC.startRemoteView(uRTCSdkStreamInfoN, uRTCRendererViewWrapperO.getRenderer(), b.this.d(dVar.getRenderMode()), uRTCFirstFrameRendered);
                }
            }
        }

        @Override // com.plv.linkmic.processor.c.c, com.plv.rtc.urtc.listener.URtcSdkEventListener
        public void onUnSubscribeResult(int i2, String str, URTCSdkStreamInfo uRTCSdkStreamInfo) {
            PLVCommonLog.d(b.TAG, "PLVUCloudDoubleStreamDecorator.onUnSubscribeResult");
        }
    }

    /* renamed from: com.plv.linkmic.processor.c.b$b, reason: collision with other inner class name */
    public class C0211b extends com.plv.linkmic.processor.c.c {
        public C0211b(URtcSdkEventListener uRtcSdkEventListener) {
            super(uRtcSdkEventListener);
        }

        @Override // com.plv.linkmic.processor.c.c, com.plv.rtc.urtc.listener.URtcSdkEventListener
        public void onLocalPublish(int i2, String str, URTCSdkStreamInfo uRTCSdkStreamInfo) {
            super.onLocalPublish(i2, str, uRTCSdkStreamInfo);
            b bVar = b.this;
            bVar.muteLocalVideo(bVar.au);
            b bVar2 = b.this;
            bVar2.muteLocalAudio(bVar2.at);
        }

        @Override // com.plv.linkmic.processor.c.c, com.plv.rtc.urtc.listener.URtcSdkEventListener
        public void onRejoinRoomResult(String str) {
            super.onRejoinRoomResult(str);
            b bVar = b.this;
            bVar.muteLocalVideo(bVar.au);
            b bVar2 = b.this;
            bVar2.muteLocalAudio(bVar2.at);
        }

        @Override // com.plv.linkmic.processor.c.c, com.plv.rtc.urtc.listener.URtcSdkEventListener
        public void onSubscribeResult(int i2, String str, URTCSdkStreamInfo uRTCSdkStreamInfo) {
            super.onSubscribeResult(i2, str, uRTCSdkStreamInfo);
            String uId = uRTCSdkStreamInfo.getUId();
            super.onRemoteTrackNotify(uId, uRTCSdkStreamInfo.getMediaType(), URTCSdkTrackType.UCLOUD_RTC_SDK_TRACK_TYPE_VIDEO, uRTCSdkStreamInfo.isMuteVideo());
            super.onRemoteTrackNotify(uId, uRTCSdkStreamInfo.getMediaType(), URTCSdkTrackType.UCLOUD_RTC_SDK_TRACK_TYPE_AUDIO, uRTCSdkStreamInfo.isMuteAudio());
        }
    }

    public static class c extends PhoneStateListener {
        private final WeakReference<PLVURTCEngine> aJ;
        private final Context context;

        public c(Context context, PLVURTCEngine pLVURTCEngine) {
            this.aJ = new WeakReference<>(pLVURTCEngine);
            this.context = context;
        }

        public void l() {
            TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            if (telephonyManager != null) {
                telephonyManager.listen(this, 32);
            }
        }

        @Override // android.telephony.PhoneStateListener
        public void onCallStateChanged(int i2, String str) {
            PLVURTCEngine pLVURTCEngine = this.aJ.get();
            if (pLVURTCEngine == null) {
                return;
            }
            if (i2 == 0) {
                pLVURTCEngine.controlAudio(true);
            } else if (i2 == 1 || i2 == 2) {
                pLVURTCEngine.controlAudio(false);
            }
        }

        public void unregister() {
            TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            if (telephonyManager != null) {
                telephonyManager.listen(this, 0);
            }
        }
    }

    private void j() {
        switch (AnonymousClass4.R[PLVVideoDimensionBitrate.match(this.f10817t, this.f10816r.getResolutionRatio(), this.f10816r.getFrameRate()).ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                this.ay = URTCSdkVideoProfile.UCLOUD_RTC_SDK_VIDEO_PROFILE_320_180;
                break;
            case 5:
            case 6:
            case 7:
            case 8:
                this.ay = URTCSdkVideoProfile.UCLOUD_RTC_SDK_VIDEO_PROFILE_640_360;
                break;
            case 9:
            case 10:
            case 11:
            case 12:
                this.ay = URTCSdkVideoProfile.UCLOUD_RTC_SDK_VIDEO_PROFILE_1280_720;
                break;
        }
        this.aC.setVideoProfile(this.ay);
        this.aC.setScreenProfile(this.ay);
        this.aC.changePushResolution(this.ay);
    }

    private void k() {
        PLVVideoDimensionBitrate pLVVideoDimensionBitrateMatch = PLVVideoDimensionBitrate.match(this.f10817t, this.f10816r.getResolutionRatio(), this.f10816r.getFrameRate());
        int i2 = pLVVideoDimensionBitrateMatch.width;
        int i3 = pLVVideoDimensionBitrateMatch.height;
        this.aC.updateMixConfig(this.av.resolution(i2, i3).type(4).bitRate(pLVVideoDimensionBitrateMatch.realBitrate).build());
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int addPublishStreamUrl(String str, boolean z2) {
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(str);
        URTCSdkMixProfile uRTCSdkMixProfileBuild = this.av.type(1).pushUrl(jSONArray).build();
        PLVCommonLog.d(TAG, "addPublishStreamUrl:mixProfile=" + uRTCSdkMixProfileBuild.toString());
        this.aC.startRelay(uRTCSdkMixProfileBuild);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int adjustRecordingSignalVolume(int i2) {
        this.aC.adjustRecordVolume(i2);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public SurfaceView createRendererView(Context context) {
        URTCRendererViewWrapper uRTCRendererViewWrapper = new URTCRendererViewWrapper(context);
        try {
            uRTCRendererViewWrapper.init();
            return uRTCRendererViewWrapper.getSurfaceView();
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public TextureView createTextureRenderView(Context context) {
        URTCRendererViewWrapper uRTCRendererViewWrapper = new URTCRendererViewWrapper(context, true);
        try {
            uRTCRendererViewWrapper.init();
            return uRTCRendererViewWrapper.getTextureView();
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void destroy() {
        this.f10814p = "";
        this.f10815q = "";
        this.as = "";
        this.uid = "";
        a(false);
        c cVar = this.aB;
        if (cVar != null) {
            cVar.unregister();
        }
        this.aD = null;
        this.aC.destroy();
        this.aC = null;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int enableLocalVideo(boolean z2) {
        return muteLocalVideo(!z2);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int enableTorch(boolean z2) {
        PLVURTCEngine pLVURTCEngine = this.aC;
        if (pLVURTCEngine == null) {
            return -1;
        }
        pLVURTCEngine.setFlashOn(z2);
        return super.enableTorch(z2);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public String getLinkMicUid() {
        return this.uid;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int joinChannel(String str) {
        String str2 = TAG;
        PLVCommonLog.d(str2, PLVLinkMicELog.LinkMicTraceLogEvent.JOIN_CHANNEL);
        if (this.aC == null) {
            return -1;
        }
        URTCSdkAuthInfo uRTCSdkAuthInfo = new URTCSdkAuthInfo();
        uRTCSdkAuthInfo.setAppId(this.f10814p);
        uRTCSdkAuthInfo.setToken(this.f10815q);
        uRTCSdkAuthInfo.setRoomId(str);
        uRTCSdkAuthInfo.setUId(this.uid + "");
        int iJoinChannel = this.aC.joinChannel(uRTCSdkAuthInfo);
        PLVCommonLog.d(str2, "joinChannel result=" + iJoinChannel);
        if (iJoinChannel == 5004 || iJoinChannel == 5006) {
            URtcSdkEventListener uRtcSdkEventListener = this.aD;
            if (uRtcSdkEventListener != null) {
                uRtcSdkEventListener.onJoinRoomResult(0, "", str);
            }
            iJoinChannel = URTCErrorCode.NET_ERR_CODE_OK;
        }
        return c(iJoinChannel);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void leaveChannel(boolean z2) {
        String str = TAG;
        PLVCommonLog.d(str, PLVLinkMicELog.LinkMicTraceLogEvent.LEAVE_CHANNEL);
        a(z2);
        try {
            PLVURTCEngine pLVURTCEngine = this.aC;
            if (pLVURTCEngine != null) {
                pLVURTCEngine.unPublish(URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_VIDEO);
                this.aC.unPublish(URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_SCREEN);
                PLVCommonLog.d(str, "leaveChannel result=" + (z2 ? this.aC.leaveChannelNonStopLocalPreview() : this.aC.leaveChannel()));
            }
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
        }
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int muteAllRemoteAudio(final boolean z2) {
        HashSet hashSet = new HashSet();
        if (z2) {
            hashSet.addAll(this.M.keySet());
        } else {
            hashSet.addAll(this.aw);
        }
        this.aC.controlAudioPlayOut(!z2);
        PLVSugarUtil.foreach(hashSet, new PLVSugarUtil.Consumer<String>() { // from class: com.plv.linkmic.processor.c.b.2
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
            /* renamed from: b, reason: merged with bridge method [inline-methods] */
            public void accept(String str) {
                b.this.muteRemoteAudio(str, z2);
            }
        });
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int muteAllRemoteVideo(final boolean z2) {
        HashSet hashSet = new HashSet();
        if (z2) {
            hashSet.addAll(this.M.keySet());
        } else {
            hashSet.addAll(this.ax);
        }
        PLVSugarUtil.foreach(hashSet, new PLVSugarUtil.Consumer<String>() { // from class: com.plv.linkmic.processor.c.b.1
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
            /* renamed from: b, reason: merged with bridge method [inline-methods] */
            public void accept(String str) {
                b.this.muteRemoteVideo(str, z2);
            }
        });
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int muteLocalAudio(boolean z2) {
        if (this.aC == null) {
            return -1;
        }
        PLVCommonLog.d(TAG, "muteLocalAudio:" + z2);
        this.at = z2;
        return c(this.aC.muteLocalMic(z2));
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int muteLocalVideo(boolean z2) {
        PLVURTCEngine pLVURTCEngine = this.aC;
        if (pLVURTCEngine == null) {
            return -1;
        }
        int iMuteLocalVideo = pLVURTCEngine.muteLocalVideo(z2, URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_VIDEO);
        this.aC.controlLocalVideo(!z2);
        this.au = z2;
        PLVCommonLog.d(TAG, "muteLocalVideo:" + z2 + " result=" + iMuteLocalVideo);
        return c(iMuteLocalVideo);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int muteRemoteAudio(String str, boolean z2) {
        if (z2) {
            this.aw.add(str);
        } else {
            this.aw.remove(str);
        }
        PLVURTCEngine pLVURTCEngine = this.aC;
        if (pLVURTCEngine != null) {
            return c(pLVURTCEngine.muteRemoteAudio(str, z2));
        }
        return -1;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int muteRemoteVideo(String str, boolean z2) {
        if (z2) {
            this.ax.add(str);
        } else {
            this.ax.remove(str);
        }
        PLVURTCEngine pLVURTCEngine = this.aC;
        if (pLVURTCEngine != null) {
            return c(pLVURTCEngine.muteRemoteVideo(str, z2));
        }
        return -1;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void releaseRenderView(View view) {
        URTCRendererViewWrapper uRTCRendererViewWrapperB;
        if (a(view) && (uRTCRendererViewWrapperB = b(view)) != null) {
            if (uRTCRendererViewWrapperB != this.aA) {
                Iterator<Map.Entry<String, d>> it = this.M.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    d value = it.next().getValue();
                    URTCRendererViewWrapper uRTCRendererViewWrapperO = value.o();
                    URTCRendererViewWrapper uRTCRendererViewWrapperP = value.p();
                    URTCRendererViewWrapper uRTCRendererViewWrapperQ = value.q();
                    if (uRTCRendererViewWrapperB == uRTCRendererViewWrapperO) {
                        value.a((URTCRendererViewWrapper) null);
                        URTCSdkStreamInfo uRTCSdkStreamInfoN = value.n();
                        if (uRTCSdkStreamInfoN != null) {
                            this.aC.stopRemoteView(uRTCSdkStreamInfoN);
                        }
                        URTCSdkStreamInfo uRTCSdkStreamInfoM = value.m();
                        if (uRTCSdkStreamInfoM != null) {
                            this.aC.stopRemoteView(uRTCSdkStreamInfoM);
                        }
                    } else if (uRTCRendererViewWrapperB == uRTCRendererViewWrapperP) {
                        value.b((URTCRendererViewWrapper) null);
                        URTCSdkStreamInfo uRTCSdkStreamInfoN2 = value.n();
                        if (uRTCSdkStreamInfoN2 != null) {
                            this.aC.stopRemoteView(uRTCSdkStreamInfoN2);
                        }
                    } else if (uRTCRendererViewWrapperB == uRTCRendererViewWrapperQ) {
                        value.c((URTCRendererViewWrapper) null);
                        URTCSdkStreamInfo uRTCSdkStreamInfoM2 = value.m();
                        if (uRTCSdkStreamInfoM2 != null) {
                            this.aC.stopRemoteView(uRTCSdkStreamInfoM2);
                        }
                    }
                }
            } else {
                this.aA = null;
                this.aC.stopPreview(URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_VIDEO, uRTCRendererViewWrapperB.getRenderer());
                PLVCommonLog.d(TAG, "release local render view");
            }
            uRTCRendererViewWrapperB.release();
        }
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int removePublishStreamUrl(String str) {
        this.aC.stopRelay(null);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int renewToken(String str) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void setBitrate(int i2) {
        PLVCommonLog.d(TAG, "setBitrate:" + i2);
        this.f10817t = i2;
        j();
        k();
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setLocalPreviewMirror(boolean z2) {
        URTCRendererViewWrapper uRTCRendererViewWrapper;
        if (this.aC == null || (uRTCRendererViewWrapper = this.aA) == null) {
            return -1;
        }
        uRTCRendererViewWrapper.setMirror(z2);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setLocalPushMirror(boolean z2) {
        URTCRendererViewWrapper uRTCRendererViewWrapper;
        if (this.aC == null || (uRTCRendererViewWrapper = this.aA) == null) {
            return -1;
        }
        uRTCRendererViewWrapper.setMirrorOnlyRemote(z2);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setOnlyAudio(boolean z2) {
        PLVURTCEngine pLVURTCEngine = this.aC;
        if (pLVURTCEngine == null) {
            return -1;
        }
        this.az = z2;
        pLVURTCEngine.configLocalCameraPublish(!z2);
        this.aC.configLocalAudioPublish(true);
        this.aC.setAudioOnlyMode(this.az);
        return super.setOnlyAudio(z2);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setPushPictureResolutionType(int i2) {
        if (i2 == 1) {
            URTCSdkEnv.setPushOrientation(URTCSdkPushOrientation.UCLOUD_RTC_PUSH_PORTRAIT_MODE);
            return 0;
        }
        if (i2 != 2) {
            URTCSdkEnv.setPushOrientation(URTCSdkPushOrientation.UCLOUD_RTC_PUSH_AUTO_MODE);
            return 0;
        }
        URTCSdkEnv.setPushOrientation(URTCSdkPushOrientation.UCLOUD_RTC_PUSH_LANDSCAPE_MODE);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setPushResolutionRatio(PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio) {
        this.f10816r.resolutionRatio(pushResolutionRatio);
        j();
        k();
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void setScreenCaptureSource(Intent intent) {
        this.aC.onScreenCaptureResult(intent);
        super.setScreenCaptureSource(intent);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setupLocalVideo(View view, int i2, String str) {
        Object renderer;
        if (this.aC == null || !a(view)) {
            return -1;
        }
        PLVCommonLog.d(TAG, "setupLocalVideo,uid=" + str);
        URTCSdkStreamInfo uRTCSdkStreamInfo = new URTCSdkStreamInfo();
        uRTCSdkStreamInfo.setUid(str + "");
        uRTCSdkStreamInfo.setHasVideo(true);
        uRTCSdkStreamInfo.setHasAudio(true);
        uRTCSdkStreamInfo.setMediaType(URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_VIDEO);
        URTCRendererViewWrapper uRTCRendererViewWrapperB = b(view);
        if (uRTCRendererViewWrapperB != null) {
            uRTCRendererViewWrapperB.setZOrderMediaOverlay(false);
            renderer = uRTCRendererViewWrapperB.getRenderer();
        } else {
            renderer = view;
        }
        view.setTag(str);
        this.aC.renderLocalView(uRTCSdkStreamInfo, renderer, d(i2), null);
        this.aA = uRTCRendererViewWrapperB;
        return -1;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void setupRemoteVideo(View view, int i2, String str) {
        setupRemoteVideo(view, i2, str, 0);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void startPreview() {
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int startShareScreen() {
        this.aC.setScreenProfile(this.ay);
        this.aC.publish(URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_SCREEN, true, true);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int stopShareScreen() {
        return this.aC.unPublish(URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_SCREEN);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void switchCamera() {
        this.K = !this.K;
        PLVCommonLog.d(TAG, "switchCamera:" + this.K);
        URTCSdkEnv.setCameraType(this.K ? 1 : 2);
        PLVURTCEngine pLVURTCEngine = this.aC;
        if (pLVURTCEngine != null) {
            pLVURTCEngine.switchCamera();
        }
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int switchRoleToAudience() {
        return c(this.aC.unPublishOnly(URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_VIDEO));
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int switchRoleToBroadcaster() {
        return c(this.aC.publish(URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_VIDEO, !this.az, true));
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void takeSnapshot(String str, final PLVSugarUtil.Consumer<Bitmap> consumer) {
        if (getLinkMicUid() == null || this.M.get(str) == null || consumer == null) {
            return;
        }
        this.M.get(str).s().addFrameListener(new URTCOnFrameListener() { // from class: com.plv.linkmic.processor.c.b.3
            @Override // com.plv.rtc.urtc.listener.URTCOnFrameListener
            public void onFrame(ByteBuffer byteBuffer, int i2, int i3) {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
                bitmapCreateBitmap.copyPixelsFromBuffer(byteBuffer);
                consumer.accept(bitmapCreateBitmap);
            }
        }, 1.0f);
    }

    private static URTCRendererViewWrapper b(View view) {
        if (view instanceof SurfaceView) {
            return URTCRendererViewWrapper.getRendererView((SurfaceView) view);
        }
        if (view instanceof TextureView) {
            return URTCRendererViewWrapper.getRendererView((TextureView) view);
        }
        return null;
    }

    private int c(int i2) {
        if (i2 == URTCErrorCode.NET_ERR_CODE_OK) {
            return 0;
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public URTCSdkScaleType d(int i2) {
        URTCSdkScaleType uRTCSdkScaleType = URTCSdkScaleType.UCLOUD_RTC_SDK_SCALE_ASPECT_FILL;
        return (i2 == 1 || i2 != 2) ? uRTCSdkScaleType : URTCSdkScaleType.UCLOUD_RTC_SDK_SCALE_ASPECT_FIT;
    }

    private void i() {
        PLVVideoDimensionBitrate pLVVideoDimensionBitrateMatch = PLVVideoDimensionBitrate.match(this.f10817t, this.f10816r.getResolutionRatio(), this.f10816r.getFrameRate());
        int i2 = pLVVideoDimensionBitrateMatch.width;
        int i3 = pLVVideoDimensionBitrateMatch.height;
        URTCSdkMixProfile.MixParamsBuilder mixParamsBuilderMainViewUserId = URTCSdkMixProfile.getInstance().assembleMixParamsBuilder().type(1).layout(1).resolution(i2, i3).bgColor(0, 0, 0).frameRate(this.f10816r.getFrameRate()).bitRate(pLVVideoDimensionBitrateMatch.realBitrate).videoCodec("h264").qualityLevel("CB").audioCodec("aac").mainViewUserId(this.uid);
        URTCSdkMediaType uRTCSdkMediaType = URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_VIDEO;
        this.av = mixParamsBuilderMainViewUserId.mainViewMediaType(uRTCSdkMediaType.ordinal()).addStreamMode(2).addStream(this.uid, uRTCSdkMediaType.ordinal());
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setupRemoteVideo(View view, int i2, String str, int i3) {
        if (this.aC == null || !a(view)) {
            return -1;
        }
        URTCRendererViewWrapper uRTCRendererViewWrapperB = b(view);
        if (uRTCRendererViewWrapperB == null) {
            PLVCommonLog.exception(new Exception("URTCRenderView.getRenderView return null"));
            return -1;
        }
        d dVar = this.M.get(str);
        if (dVar == null) {
            d dVar2 = new d(str);
            dVar2.a(i3, uRTCRendererViewWrapperB);
            dVar2.setRenderMode(i2);
            this.M.put(str, dVar2);
        } else {
            URTCSdkStreamInfo uRTCSdkStreamInfoM = dVar.m();
            URTCSdkStreamInfo uRTCSdkStreamInfoN = dVar.n();
            URTCRendererViewWrapper uRTCRendererViewWrapperF = dVar.f(i3);
            if (uRTCRendererViewWrapperF != null) {
                if (uRTCRendererViewWrapperF.getRendererView() == view) {
                    PLVCommonLog.d(TAG, "repeat call setupRemoteVideo!");
                    return 0;
                }
                PLVCommonLog.d(TAG, "Client re-renders without release old render view ahead, so we stop and release render view inside.");
                if (i3 != 1) {
                    if (uRTCSdkStreamInfoM != null) {
                        this.aC.stopRemoteView(uRTCSdkStreamInfoM);
                    }
                } else if (uRTCSdkStreamInfoN != null) {
                    this.aC.stopRemoteView(uRTCSdkStreamInfoN);
                }
                uRTCRendererViewWrapperF.release();
                dVar.e(i3);
            }
            dVar.a(i3, uRTCRendererViewWrapperB);
            dVar.setRenderMode(i2);
            if (uRTCSdkStreamInfoM == null && uRTCSdkStreamInfoN == null) {
                PLVCommonLog.d(TAG, "PLVLinkMicUCloudProcessor.setupRemoteVideo, camera and screen stream info are null due to no published stream,so we wait for onPublish");
                return 0;
            }
            if (!dVar.r()) {
                PLVCommonLog.d(TAG, "PLVLinkMicUCloudProcessor.setupRemoteVideo, user is not subscribed yet so we wait for onSubscribe");
                return 0;
            }
            if (i3 != 1) {
                if (i3 != 2) {
                    if (uRTCSdkStreamInfoM != null) {
                        int iStartRemoteView = this.aC.startRemoteView(uRTCSdkStreamInfoM, uRTCRendererViewWrapperB.getRenderer(), d(i2), null);
                        if (iStartRemoteView == URTCErrorCode.NET_ERR_CODE_OK) {
                            PLVCommonLog.d(TAG, "PLVLinkMicUCloudProcessor.setupRemoteVideo, render success->cameraInfo");
                        } else {
                            PLVCommonLog.exception(new Exception("render failed " + iStartRemoteView));
                        }
                    } else if (uRTCSdkStreamInfoN != null) {
                        int iStartRemoteView2 = this.aC.startRemoteView(uRTCSdkStreamInfoN, uRTCRendererViewWrapperB.getRenderer(), d(i2), null);
                        if (iStartRemoteView2 == URTCErrorCode.NET_ERR_CODE_OK) {
                            PLVCommonLog.d(TAG, "PLVLinkMicUCloudProcessor.setupRemoteVideo, render success->cameraInfo");
                        } else {
                            PLVCommonLog.exception(new Exception("render failed " + iStartRemoteView2));
                        }
                    }
                } else if (uRTCSdkStreamInfoM != null) {
                    int iStartRemoteView3 = this.aC.startRemoteView(uRTCSdkStreamInfoM, uRTCRendererViewWrapperB.getRenderer(), d(i2), null);
                    if (iStartRemoteView3 == URTCErrorCode.NET_ERR_CODE_OK) {
                        PLVCommonLog.d(TAG, "PLVLinkMicUCloudProcessor.setupRemoteVideo, render success->cameraInfo");
                    } else {
                        PLVCommonLog.exception(new Exception("render failed " + iStartRemoteView3));
                    }
                } else {
                    PLVCommonLog.exception(new Exception("screenInfo is null, failed to render camera stream"));
                }
            } else if (uRTCSdkStreamInfoN != null) {
                int iStartRemoteView4 = this.aC.startRemoteView(uRTCSdkStreamInfoN, uRTCRendererViewWrapperB.getRenderer(), d(i2), null);
                if (iStartRemoteView4 == URTCErrorCode.NET_ERR_CODE_OK) {
                    PLVCommonLog.d(TAG, "PLVLinkMicUCloudProcessor.setupRemoteVideo, render success->cameraInfo");
                } else {
                    PLVCommonLog.exception(new Exception("render failed " + iStartRemoteView4));
                }
            } else {
                PLVCommonLog.exception(new Exception("cameraInfo is null, failed to render camera stream"));
            }
        }
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.c
    public boolean a(PLVLinkMicEngineToken pLVLinkMicEngineToken, PLVRTCConfig pLVRTCConfig, Context context, com.plv.linkmic.processor.a aVar) {
        this.uid = pLVRTCConfig.getUid();
        this.f10814p = pLVLinkMicEngineToken.getAppId();
        this.f10815q = pLVLinkMicEngineToken.getToken();
        this.as = pLVLinkMicEngineToken.getUAppToken();
        this.f10816r = pLVRTCConfig;
        this.linkMicEventDispatcher = aVar;
        return a(context, (URtcSdkEventListener) aVar.b());
    }

    private boolean a(Context context, URtcSdkEventListener uRtcSdkEventListener) {
        if (this.aC != null) {
            return true;
        }
        URTCSdkEnv.initEnv(context.getApplicationContext());
        URTCSdkEnv.setWriteToLogCat(false);
        URTCSdkEnv.setLogReport(true);
        URTCSdkEnv.setLogLevel(URTCSdkLogLevel.UCLOUD_RTC_SDK_LogLevelInfo);
        URTCSdkEnv.setEncodeMode(URTCSdkPushEncode.UCLOUD_RTC_PUSH_ENCODE_MODE_H264);
        URTCSdkEnv.setSdkMode(URTCSdkMode.UCLOUD_RTC_SDK_MODE_NORMAL);
        URTCSdkEnv.setCaptureMode(URTCSdkCaptureMode.UCLOUD_RTC_CAPTURE_MODE_LOCAL);
        URTCSdkEnv.setTokenSecKey(this.as);
        URTCSdkEnv.setReConnectTimes(-1);
        URTCSdkEnv.setCameraType(1);
        URTCSdkEnv.setHttpsVerify(true);
        try {
            C0211b c0211b = new C0211b(new a(uRtcSdkEventListener));
            this.aD = c0211b;
            PLVURTCEngine pLVURTCEngineCreateEngine = PLVURTCEngineFactory.createEngine(c0211b);
            this.aC = pLVURTCEngineCreateEngine;
            pLVURTCEngineCreateEngine.setAudioOnlyMode(false);
            this.aC.configLocalCameraPublish(true);
            this.aC.configLocalAudioPublish(true);
            this.aC.configLocalScreenPublish(true);
            if (PLVLinkMicDataConfig.pureRtcWatchEnabled) {
                this.aC.setStreamRole(URTCSdkStreamRole.UCLOUD_RTC_SDK_STREAM_ROLE_PUB);
            } else {
                this.aC.setStreamRole(URTCSdkStreamRole.UCLOUD_RTC_SDK_STREAM_ROLE_BOTH);
            }
            this.aC.setClassType(URTCSdkRoomType.UCLOUD_RTC_SDK_ROOM_LARGE);
            this.aC.setAutoPublish(false);
            this.aC.setAutoSubscribe(false);
            URTCSdkVideoProfile uRTCSdkVideoProfile = URTCSdkVideoProfile.UCLOUD_RTC_SDK_VIDEO_PROFILE_320_180;
            this.ay = uRTCSdkVideoProfile;
            this.aC.setVideoProfile(uRTCSdkVideoProfile);
            i();
            c cVar = new c(context, this.aC);
            this.aB = cVar;
            cVar.l();
            return true;
        } catch (Exception e2) {
            Log.e(TAG, e2.getLocalizedMessage());
            return false;
        }
    }

    private void a(boolean z2) {
        if (!z2) {
            if (this.aA != null) {
                PLVCommonLog.d(TAG, "releaseSessionData->release local render view");
                this.aA.release();
                this.aA = null;
            }
            this.au = false;
            this.at = false;
        }
        for (Map.Entry<String, d> entry : this.M.entrySet()) {
            URTCRendererViewWrapper uRTCRendererViewWrapperO = entry.getValue().o();
            if (uRTCRendererViewWrapperO != null) {
                uRTCRendererViewWrapperO.release();
            }
            URTCRendererViewWrapper uRTCRendererViewWrapperP = entry.getValue().p();
            if (uRTCRendererViewWrapperP != null) {
                uRTCRendererViewWrapperP.release();
            }
            URTCRendererViewWrapper uRTCRendererViewWrapperQ = entry.getValue().q();
            if (uRTCRendererViewWrapperQ != null) {
                uRTCRendererViewWrapperQ.release();
            }
        }
        this.M.clear();
    }

    private static boolean a(View view) {
        return (view instanceof SurfaceView) || (view instanceof TextureView);
    }
}
