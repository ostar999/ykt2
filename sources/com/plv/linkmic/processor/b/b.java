package com.plv.linkmic.processor.b;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.net.api.PLVFoundationApiManager;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.linkmic.model.PLVRTCConfig;
import com.plv.linkmic.processor.PLVVideoDimensionBitrate;
import com.plv.linkmic.processor.e;
import com.plv.linkmic.repository.PLVLinkMicEngineToken;
import com.plv.rtc.trtc.PLVTRTCDef;
import com.plv.rtc.trtc.PLVTRTCEngine;
import com.plv.rtc.trtc.PLVTRTCEngineFactory;
import com.plv.rtc.trtc.PLVTRTCEventListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.ConcurrentHashMap;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes4.dex */
public class b extends e {
    private static final String TAG = "b";

    @Nullable
    private PLVTRTCEngine G;
    private String N;
    private Disposable O;
    private int appId;
    private int bizId;
    private String channelId;

    /* renamed from: r, reason: collision with root package name */
    private PLVRTCConfig f10810r;
    private int sdkAppId;
    private String uid;

    /* renamed from: w, reason: collision with root package name */
    private SurfaceView f10812w;

    /* renamed from: t, reason: collision with root package name */
    private int f10811t = 1;

    @NotNull
    private final PLVTRTCDef.TRTCVideoEncParam H = new PLVTRTCDef.TRTCVideoEncParam();

    @NotNull
    PLVTRTCDef.TRTCRenderParams I = new PLVTRTCDef.TRTCRenderParams();
    private int J = 2;
    private boolean K = true;
    private boolean L = false;
    private final ConcurrentHashMap<String, d> M = new ConcurrentHashMap<>();

    /* renamed from: z, reason: collision with root package name */
    private PLVTRTCEventListener f10813z = null;
    private a P = new a();

    /* renamed from: com.plv.linkmic.processor.b.b$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] R;

        static {
            int[] iArr = new int[PLVVideoDimensionBitrate.values().length];
            R = iArr;
            try {
                iArr[PLVVideoDimensionBitrate.BITRATE_STANDARD_16_9_15FPS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_STANDARD_16_9_30FPS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_STANDARD_4_3_15FPS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_STANDARD_4_3_30FPS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_HIGH_16_9_15FPS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_HIGH_16_9_30FPS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_HIGH_4_3_15FPS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_HIGH_4_3_30FPS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_SUPER_16_9_15FPS.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_SUPER_16_9_30FPS.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_SUPER_4_3_15FPS.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                R[PLVVideoDimensionBitrate.BITRATE_SUPER_4_3_30FPS.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    public class a extends PLVTRTCEventListener {
        private a() {
        }

        @Override // com.plv.rtc.trtc.PLVTRTCEventListener
        public void onExitRoom(int i2) {
            if (b.this.G == null || b.this.f10812w == null) {
                return;
            }
            b.this.d();
            b.this.G.startLocalPreview(true, b.this.f10812w);
        }
    }

    /* renamed from: com.plv.linkmic.processor.b.b$b, reason: collision with other inner class name */
    public class C0209b extends c {
        public C0209b(PLVTRTCEventListener pLVTRTCEventListener) {
            super(pLVTRTCEventListener);
        }

        @Override // com.plv.linkmic.processor.b.c, com.plv.rtc.trtc.PLVTRTCEventListener
        public void onRemoteUserEnterRoom(String str) {
            if (b.this.M.get(str) == null) {
                b.this.M.put(str, new d(str));
            }
            super.onRemoteUserEnterRoom(str);
        }

        @Override // com.plv.linkmic.processor.b.c, com.plv.rtc.trtc.PLVTRTCEventListener
        public void onRemoteUserLeaveRoom(String str, int i2) {
            b.this.M.remove(str);
            super.onRemoteUserLeaveRoom(str, i2);
        }

        @Override // com.plv.linkmic.processor.b.c, com.plv.rtc.trtc.PLVTRTCEventListener
        public void onUserVideoAvailable(String str, boolean z2) {
            d dVar;
            SurfaceView surfaceViewG;
            super.onUserVideoAvailable(str, z2);
            if (b.this.G == null || (dVar = (d) b.this.M.get(str)) == null) {
                return;
            }
            if (!z2) {
                if (dVar.h()) {
                    b.this.G.stopRemoteView(str, dVar.getStreamType());
                    dVar.b(false);
                    return;
                }
                return;
            }
            if (dVar.h() || (surfaceViewG = dVar.g()) == null) {
                return;
            }
            b.this.G.startRemoteView(str, dVar.getStreamType(), surfaceViewG);
            dVar.b(true);
        }
    }

    private void e() {
        if (this.G == null) {
            return;
        }
        PLVVideoDimensionBitrate pLVVideoDimensionBitrateMatch = PLVVideoDimensionBitrate.match(this.f10811t, this.f10810r.getResolutionRatio(), this.f10810r.getFrameRate());
        PLVTRTCDef.TRTCVideoEncParam tRTCVideoEncParam = this.H;
        tRTCVideoEncParam.videoBitrate = pLVVideoDimensionBitrateMatch.realBitrate;
        tRTCVideoEncParam.videoFps = pLVVideoDimensionBitrateMatch.frameRate;
        switch (AnonymousClass4.R[pLVVideoDimensionBitrateMatch.ordinal()]) {
            case 1:
            case 2:
                tRTCVideoEncParam.videoResolution = 104;
                break;
            case 3:
            case 4:
                tRTCVideoEncParam.videoResolution = 52;
                break;
            case 5:
            case 6:
                tRTCVideoEncParam.videoResolution = 108;
                break;
            case 7:
            case 8:
                tRTCVideoEncParam.videoResolution = 60;
                break;
            case 9:
            case 10:
                tRTCVideoEncParam.videoResolution = 112;
                break;
            case 11:
            case 12:
                tRTCVideoEncParam.videoResolution = 64;
                break;
        }
        this.G.setVideoEncoderParam(tRTCVideoEncParam);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int addPublishStreamUrl(String str, boolean z2) {
        if (this.G == null) {
            return -1;
        }
        PLVCommonLog.d(TAG, "addPublishStreamUrl() called with: url = [" + str + "], transcodingEnabled = [" + z2 + StrPool.BRACKET_END);
        PLVTRTCDef.TRTCPublishCDNParam tRTCPublishCDNParam = new PLVTRTCDef.TRTCPublishCDNParam();
        tRTCPublishCDNParam.url = str;
        tRTCPublishCDNParam.appId = this.appId;
        tRTCPublishCDNParam.bizId = this.bizId;
        this.G.startPublishCDNStream(tRTCPublishCDNParam);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int adjustRecordingSignalVolume(int i2) {
        PLVCommonLog.d(TAG, "adjustRecordingSignalVolume() called with: volume = [" + i2 + StrPool.BRACKET_END);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public SurfaceView createRendererView(Context context) {
        return new SurfaceView(context);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void destroy() {
        a(false);
        PLVTRTCEngine pLVTRTCEngine = this.G;
        if (pLVTRTCEngine != null) {
            pLVTRTCEngine.destroy();
            this.G = null;
        }
        Disposable disposable = this.O;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int enableLocalVideo(boolean z2) {
        PLVCommonLog.d(TAG, "enableLocalVideo() called with: enable = [" + z2 + StrPool.BRACKET_END);
        return muteLocalVideo(!z2);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int enableTorch(boolean z2) {
        PLVTRTCEngine pLVTRTCEngine = this.G;
        if (pLVTRTCEngine == null) {
            return -1;
        }
        boolean zEnableTorch = pLVTRTCEngine.enableTorch(z2);
        PLVCommonLog.d(TAG, "enableTorch result=" + zEnableTorch);
        return zEnableTorch ? 0 : -1;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public String getLinkMicUid() {
        return this.uid;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int joinChannel(String str) throws NumberFormatException {
        if (this.G == null) {
            return -1;
        }
        PLVCommonLog.d(TAG, "joinChannel:" + str);
        this.channelId = str;
        this.G.setListener(this.f10813z);
        try {
            Integer.parseInt(str);
            this.G.startLocalAudio(2);
            PLVTRTCDef.TRTCParams tRTCParams = new PLVTRTCDef.TRTCParams();
            tRTCParams.sdkAppId = this.sdkAppId;
            tRTCParams.userId = this.uid;
            tRTCParams.strRoomId = str;
            tRTCParams.userSig = this.N;
            tRTCParams.role = 20;
            this.G.enterRoom(tRTCParams, 1);
            return 0;
        } catch (NumberFormatException e2) {
            PLVCommonLog.exception(e2);
            return -1;
        }
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void leaveChannel(boolean z2) {
        PLVCommonLog.d(TAG, "leaveChannel() called");
        a(z2);
        PLVTRTCEngine pLVTRTCEngine = this.G;
        if (pLVTRTCEngine == null) {
            return;
        }
        pLVTRTCEngine.setListener(this.P);
        this.G.exitRoom();
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int muteLocalAudio(boolean z2) {
        if (this.G == null) {
            return -1;
        }
        PLVCommonLog.d(TAG, "muteLocalAudio" + z2);
        this.G.muteLocalAudio(z2);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int muteLocalVideo(boolean z2) {
        if (this.G == null) {
            return -1;
        }
        this.L = z2;
        PLVCommonLog.d(TAG, "muteLocalVideo:" + z2);
        this.G.muteLocalVideo(z2);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void releaseRenderView(View view) {
        PLVCommonLog.d(TAG, "releaseRenderView() called with: view = [" + view + StrPool.BRACKET_END);
        PLVTRTCEngine pLVTRTCEngine = this.G;
        if (pLVTRTCEngine == null || view == null) {
            return;
        }
        SurfaceView surfaceView = this.f10812w;
        if (surfaceView != null && surfaceView == view) {
            pLVTRTCEngine.stopLocalPreview();
            this.f10812w = null;
            return;
        }
        for (d dVar : this.M.values()) {
            if (dVar.g() == view && dVar.h()) {
                this.G.stopRemoteView(dVar.f(), dVar.getStreamType());
                dVar.b(false);
            }
        }
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int removePublishStreamUrl(String str) {
        if (this.G == null) {
            return -1;
        }
        PLVCommonLog.d(TAG, "removePublishStreamUrl() called with: url = [" + str + StrPool.BRACKET_END);
        this.G.stopPublishCDNStream();
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int renewToken(String str) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void setBitrate(int i2) {
        PLVCommonLog.d(TAG, "setBitrate = " + i2);
        this.f10811t = i2;
        if (this.G == null) {
            return;
        }
        e();
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setCameraZoomRatio(float f2) {
        PLVTRTCEngine pLVTRTCEngine = this.G;
        if (pLVTRTCEngine == null) {
            return -1;
        }
        return pLVTRTCEngine.setCameraZoomRatio(f2);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setLocalPreviewMirror(boolean z2) {
        if (this.G == null) {
            return -1;
        }
        if (z2) {
            this.I.mirrorType = 1;
        } else {
            this.I.mirrorType = 2;
        }
        d();
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setLocalPushMirror(boolean z2) {
        PLVTRTCEngine pLVTRTCEngine = this.G;
        if (pLVTRTCEngine == null) {
            return -1;
        }
        pLVTRTCEngine.setVideoEncoderMirror(z2);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setPushPictureResolutionType(int i2) {
        PLVTRTCEngine pLVTRTCEngine = this.G;
        if (pLVTRTCEngine == null) {
            return -1;
        }
        this.J = i2;
        if (i2 == 1) {
            this.H.videoResolutionMode = 1;
        } else if (i2 == 2) {
            this.H.videoResolutionMode = 0;
        }
        pLVTRTCEngine.setVideoEncoderParam(this.H);
        d();
        return super.setPushPictureResolutionType(i2);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setPushResolutionRatio(PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio) {
        this.f10810r.resolutionRatio(pushResolutionRatio);
        e();
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setVideoMuteImage(String str) {
        if (this.G == null) {
            return -1;
        }
        Disposable disposable = this.O;
        if (disposable != null) {
            disposable.dispose();
        }
        this.O = PLVFoundationApiManager.getPlvUrlApi().requestUrl(str).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).map(new Function<ResponseBody, Bitmap>() { // from class: com.plv.linkmic.processor.b.b.3
            @Override // io.reactivex.functions.Function
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Bitmap apply(@NonNull ResponseBody responseBody) throws Exception {
                return BitmapFactory.decodeStream(responseBody.byteStream());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Bitmap>() { // from class: com.plv.linkmic.processor.b.b.1
            @Override // io.reactivex.functions.Consumer
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void accept(Bitmap bitmap) throws Exception {
                if (b.this.G == null) {
                    return;
                }
                b.this.G.setVideoMuteImage(bitmap, 5);
            }
        }, new Consumer<Throwable>() { // from class: com.plv.linkmic.processor.b.b.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVCommonLog.exception(new Exception(th));
            }
        });
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setupLocalVideo(View view, int i2, String str) {
        if (this.G == null || !a(view)) {
            return -1;
        }
        SurfaceView surfaceView = (SurfaceView) view;
        PLVCommonLog.d(TAG, "setupLocalVideo() called with: childAt = [" + view + "], renderMode = [" + i2 + "], uid = [" + str + StrPool.BRACKET_END);
        this.f10812w = surfaceView;
        if (i2 == 1) {
            this.I.fillMode = 0;
        } else if (i2 == 2) {
            this.I.fillMode = 1;
        }
        d();
        this.G.startLocalPreview(this.K, surfaceView);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void setupRemoteVideo(View view, int i2, String str) {
        if (this.G == null || !a(view)) {
            return;
        }
        SurfaceView surfaceView = (SurfaceView) view;
        PLVCommonLog.d(TAG, "setupRemoteVideo() called with: surfaceV = [" + view + "], renderMode = [" + i2 + "], uid = [" + str + StrPool.BRACKET_END);
        this.G.startRemoteView(str, 0, surfaceView);
        PLVTRTCDef.TRTCRenderParams tRTCRenderParams = new PLVTRTCDef.TRTCRenderParams();
        if (i2 == 1) {
            tRTCRenderParams.fillMode = 0;
        } else if (i2 == 2) {
            tRTCRenderParams.fillMode = 1;
        }
        tRTCRenderParams.mirrorType = 2;
        tRTCRenderParams.rotation = 0;
        this.G.setRemoteRenderParams(str, 0, tRTCRenderParams);
        d dVar = this.M.get(str);
        if (dVar == null) {
            dVar = new d(str);
        }
        dVar.setStreamType(0);
        dVar.a(surfaceView);
        dVar.setRenderMode(i2);
        dVar.b(true);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void startPreview() {
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int startShareScreen() {
        if (this.G == null) {
            return -1;
        }
        PLVCommonLog.d(TAG, "startShareScreen() called");
        this.G.stopLocalPreview();
        if (this.L) {
            this.G.muteLocalVideo(false);
            this.G.stopLocalPreview();
        }
        this.G.startScreenCapture(0, this.H);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int stopShareScreen() {
        if (this.G == null) {
            return -1;
        }
        PLVCommonLog.d(TAG, "stopShareScreen() called");
        this.G.stopScreenCapture();
        d();
        this.G.setVideoEncoderParam(this.H);
        this.G.startLocalPreview(this.K, this.f10812w);
        if (!this.L) {
            return 0;
        }
        this.G.muteLocalVideo(true);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void switchBeauty(boolean z2) {
        PLVTRTCEngine pLVTRTCEngine = this.G;
        if (pLVTRTCEngine != null) {
            pLVTRTCEngine.switchBeauty(z2);
        }
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void switchCamera() {
        PLVTRTCEngine pLVTRTCEngine = this.G;
        if (pLVTRTCEngine != null) {
            this.K = !this.K;
            pLVTRTCEngine.switchCamera();
        }
        PLVCommonLog.d(TAG, "switchCamera() called");
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int switchRoleToAudience() {
        if (this.G == null) {
            return -1;
        }
        PLVCommonLog.d(TAG, "switchRoleToAudience() called");
        this.G.switchRole(21);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int switchRoleToBroadcaster() {
        if (this.G == null) {
            return -1;
        }
        PLVCommonLog.d(TAG, "switchRoleToBroadcaster() called");
        this.G.switchRole(20);
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        if (this.G == null) {
            return;
        }
        PLVTRTCDef.TRTCRenderParams tRTCRenderParams = new PLVTRTCDef.TRTCRenderParams();
        PLVTRTCDef.TRTCRenderParams tRTCRenderParams2 = this.I;
        tRTCRenderParams.fillMode = tRTCRenderParams2.fillMode;
        tRTCRenderParams.mirrorType = tRTCRenderParams2.mirrorType;
        tRTCRenderParams.rotation = tRTCRenderParams2.rotation;
        this.G.setLocalRenderParams(tRTCRenderParams);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.c
    public boolean a(PLVLinkMicEngineToken pLVLinkMicEngineToken, PLVRTCConfig pLVRTCConfig, Context context, com.plv.linkmic.processor.a aVar) {
        this.uid = pLVRTCConfig.getUid();
        this.sdkAppId = PLVFormatUtils.parseInt(pLVLinkMicEngineToken.getTSdkAppId());
        this.appId = PLVFormatUtils.parseInt(pLVLinkMicEngineToken.getAppId());
        this.bizId = PLVFormatUtils.parseInt(pLVLinkMicEngineToken.getTBizId());
        this.N = pLVLinkMicEngineToken.getToken();
        this.f10810r = pLVRTCConfig;
        if (this.G != null) {
            return true;
        }
        try {
            this.G = PLVTRTCEngineFactory.createEngine(context);
            C0209b c0209b = new C0209b((PLVTRTCEventListener) aVar.b());
            this.f10813z = c0209b;
            this.G.setListener(c0209b);
            this.H.videoFps = pLVRTCConfig.getFrameRate();
            PLVTRTCDef.TRTCRenderParams tRTCRenderParams = this.I;
            tRTCRenderParams.mirrorType = 2;
            tRTCRenderParams.rotation = 0;
            setLocalPreviewMirror(false);
            setLocalPushMirror(false);
            setPushPictureResolutionType(this.J);
            setBitrate(this.f10811t);
            this.G.enableAudioVolumeEvaluation(1000);
            return true;
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
            return false;
        }
    }

    private void a(boolean z2) {
        PLVTRTCEngine pLVTRTCEngine;
        if (!z2 && this.f10812w != null) {
            PLVTRTCEngine pLVTRTCEngine2 = this.G;
            if (pLVTRTCEngine2 != null) {
                pLVTRTCEngine2.stopLocalPreview();
            }
            this.f10812w = null;
        }
        for (d dVar : this.M.values()) {
            if (dVar.h() && (pLVTRTCEngine = this.G) != null) {
                pLVTRTCEngine.stopRemoteView(dVar.f(), dVar.getStreamType());
            }
        }
        this.M.clear();
    }

    private static boolean a(View view) {
        return view instanceof SurfaceView;
    }
}
