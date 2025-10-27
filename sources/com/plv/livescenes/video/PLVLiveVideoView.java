package com.plv.livescenes.video;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.easefun.polyv.businesssdk.model.video.PolyvLiveChannelVO;
import com.easefun.polyv.businesssdk.model.video.PolyvLiveLinesVO;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent;
import com.plv.business.api.auxiliary.PLVAuxiliaryVideoview;
import com.plv.business.api.common.player.PLVBaseVideoView;
import com.plv.business.api.common.player.PLVPlayError;
import com.plv.business.api.common.player.PLVPlayerOptionParamVO;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent;
import com.plv.business.api.common.player.microplayer.PLVCommonVideoView;
import com.plv.business.api.common.ppt.IPLVPPTView;
import com.plv.business.model.video.PLVBaseVideoParams;
import com.plv.business.model.video.PLVBitrateVO;
import com.plv.business.model.video.PLVLiveChannelSessionVO;
import com.plv.business.model.video.PLVLiveMarqueeVO;
import com.plv.business.model.video.PLVLiveRTCStatusVO;
import com.plv.business.model.video.PLVLiveVideoParams;
import com.plv.business.model.video.PLVMediaPlayMode;
import com.plv.business.model.video.PLVWatermarkVO;
import com.plv.business.net.PLVCommonApiManager;
import com.plv.business.net.api.PLVApiApi;
import com.plv.foundationsdk.PLVUAClient;
import com.plv.foundationsdk.config.PLVPlayOption;
import com.plv.foundationsdk.ijk.player.media.PLVPlayerNative;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.logcode.play.PLVErrorCodePlayVideoInfo;
import com.plv.foundationsdk.log.elog.logcode.play.PLVErrorCodePlayVideoPlay;
import com.plv.foundationsdk.net.PLVResponseBean;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.PLVrResponseCallback;
import com.plv.foundationsdk.rx.PLVRxBaseTransformer;
import com.plv.foundationsdk.rx.PLVRxBus;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.utils.PLVNetworkUtils;
import com.plv.foundationsdk.utils.PLVUtils;
import com.plv.linkmic.model.PLVMicphoneStatus;
import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.livescenes.config.PLVLiveStatusType;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.livescenes.log.PLVELogSender;
import com.plv.livescenes.log.PLVLiveQOSAnalytics;
import com.plv.livescenes.log.PLVLiveViewLog;
import com.plv.livescenes.model.PLVLiveCountdownVO;
import com.plv.livescenes.model.PLVLiveRestrictVO;
import com.plv.livescenes.model.PLVLiveStatusSessionIdVO;
import com.plv.livescenes.model.PLVLiveStatusVO2;
import com.plv.livescenes.model.PLVSocketMessageVO;
import com.plv.livescenes.model.PLVSocketSliceControlVO;
import com.plv.livescenes.net.PLVApiManager;
import com.plv.livescenes.video.api.IPLVLiveAudioModeView;
import com.plv.livescenes.video.api.IPLVLiveListenerEvent;
import com.plv.livescenes.video.api.IPLVLiveVideoView;
import com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class PLVLiveVideoView extends PLVCommonVideoView<PolyvLiveChannelVO, PLVLiveListener> implements IPLVLiveVideoView, IPLVLiveVideoViewListenerBinder {
    private static final String A = "live";
    private static final String APP_ID = "appId";
    private static final String B = "probesize";
    private static final String C = "skip_frame";
    private static final String CHANNEL_ID = "channelId";
    private static final String D = "analyzeduration";
    private static final String E = "max_cached_duration";
    private static final String F = "infbuf";
    private static final String G = "packet-buffering";
    private static final String H = "playcontrol-optimize";
    private static final String I = "high-level-ms";
    private static final String J = "low-level-ms";
    private static final String K = "webrtc_min_delay";
    private static final String TAG = "PLVLiveVideoView";
    private static final String TIMESTAMP = "timestamp";

    /* renamed from: x, reason: collision with root package name */
    private static final int f10845x = 10000;

    /* renamed from: y, reason: collision with root package name */
    private static final int f10846y = 10000;

    /* renamed from: z, reason: collision with root package name */
    private static final String f10847z = "channelSessionId";
    private PolyvLiveChannelVO L;
    private PLVBitrateVO M;
    private Disposable N;
    private Disposable O;
    private Disposable P;
    private Disposable Q;
    private Disposable R;
    private Disposable S;
    private Disposable T;
    private Disposable U;
    private Disposable V;
    private Disposable W;
    private Disposable a_;
    private boolean aa;
    private boolean ab;
    private boolean ac;
    private boolean ad;
    private boolean ae;
    private boolean af;
    private boolean ag;
    private boolean ah;
    private boolean ai;
    private boolean aj;
    private String ak;
    private String al;
    private int am;
    private IPLVLiveAudioModeView an;

    /* renamed from: b, reason: collision with root package name */
    private Disposable f10848b;
    private Disposable b_;
    private boolean c_;
    private String channelId;
    private PLVLiveChannelType channelType;
    private CompositeDisposable compositeDisposable;

    /* renamed from: d, reason: collision with root package name */
    private Disposable f10849d;
    private boolean isOnlyAudio;
    private PLVLiveStatusType liveStatus;
    private IPLVPPTView pptView;
    private String sessionId;
    private String stream;
    private String userId;

    /* renamed from: w, reason: collision with root package name */
    private int f10850w;

    /* renamed from: com.plv.livescenes.video.PLVLiveVideoView$22, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass22 {
        static final /* synthetic */ int[] $SwitchMap$com$plv$livescenes$config$PLVLiveStatusType;

        static {
            int[] iArr = new int[PLVLiveStatusType.values().length];
            $SwitchMap$com$plv$livescenes$config$PLVLiveStatusType = iArr;
            try {
                iArr[PLVLiveStatusType.LIVE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$plv$livescenes$config$PLVLiveStatusType[PLVLiveStatusType.STOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$plv$livescenes$config$PLVLiveStatusType[PLVLiveStatusType.END.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public PLVLiveVideoView(@NonNull Context context) {
        super(context);
        this.sessionId = "";
        this.userId = "";
        this.channelId = "";
        this.liveStatus = PLVLiveStatusType.END;
        this.channelType = PLVLiveChannelType.PPT;
        this.compositeDisposable = new CompositeDisposable();
        this.c_ = true;
        this.ai = false;
        this.isOnlyAudio = false;
        this.aj = false;
        this.ak = "0";
        this.al = "";
        this.am = 0;
    }

    private void setAudioModeLayoutVisibility(int i2) {
        IPLVLiveAudioModeView iPLVLiveAudioModeView = this.an;
        if (iPLVLiveAudioModeView != null) {
            if (i2 == 0) {
                iPLVLiveAudioModeView.onShow();
            } else {
                iPLVLiveAudioModeView.onHide();
            }
        }
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public void bindPPTView(IPLVPPTView iPLVPPTView) {
        this.pptView = iPLVPPTView;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public boolean canMove() {
        return true;
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public boolean changeBitRate(int i2) {
        this.bitratePos = i2;
        setPlayerBufferingViewVisibility(0);
        d();
        p();
        return true;
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public boolean changeLines(int i2) {
        this.linesPos = i2;
        setPlayerBufferingViewVisibility(0);
        d();
        p();
        return true;
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoView
    public void changeMediaPlayMode(int i2) {
        if (this.am == PLVMediaPlayMode.amendMode(i2)) {
            return;
        }
        this.am = PLVMediaPlayMode.amendMode(i2);
        if (i2 == 0) {
            setAudioModeLayoutVisibility(8);
        }
        setPlayerBufferingViewVisibility(0);
        d();
        p();
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public Handler createHandler() {
        return null;
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public Uri createPlayUri() {
        StringBuilder sb = new StringBuilder();
        List<PolyvLiveLinesVO> lines = this.L.getLines();
        if (lines != null) {
            sb.append(lines.get(0).getFlv());
        }
        String string = sb.toString();
        this.playUri = string;
        return Uri.parse(string);
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView, com.plv.business.api.common.player.PLVBaseVideoView, com.plv.business.api.common.player.IPLVBaseVideoView
    public void destroy() {
        this.ae = true;
        PLVCommonLog.d(TAG, "destory live video");
        super.destroy();
        d();
        Disposable disposable = this.a_;
        if (disposable != null) {
            disposable.dispose();
        }
        Disposable disposable2 = this.b_;
        if (disposable2 != null) {
            disposable2.dispose();
        }
        Disposable disposable3 = this.T;
        if (disposable3 != null) {
            disposable3.dispose();
        }
        Disposable disposable4 = this.W;
        if (disposable4 != null) {
            disposable4.dispose();
        }
        Disposable disposable5 = this.V;
        if (disposable5 != null) {
            disposable5.dispose();
        }
        this.sessionId = "";
        PLVLinkMicConfig.getInstance().setSessionId("");
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoView
    public void enableFrameSkip(boolean z2) {
        this.af = z2;
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoView
    public String getLinkMicType() {
        return this.al;
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoView
    public int getMediaPlayMode() {
        return this.am;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public ArrayList<PLVPlayerOptionParamVO> initOptionParameters() {
        ArrayList<PLVPlayerOptionParamVO> arrayListInitOptionParameters = super.initOptionParameters();
        if (this.af) {
            arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(1, B, 1024));
            arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(1, C, 0));
            arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(1, D, 1));
            arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(4, E, 3000));
            arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(4, F, 1));
            arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(4, G, 0));
            PLVCommonLog.d(TAG, "cloud class initOptionParameters size ：" + arrayListInitOptionParameters.size());
        }
        if (this.aj) {
            arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(4, E, 0));
            arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(4, F, 1));
            arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(4, G, 0));
            arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(4, H, 1));
            arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(4, I, 200));
            arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(4, J, 100));
            arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(1, K, 1000));
            PLVCommonLog.d(TAG, "cloud class low latency initOptionParameters size ：" + arrayListInitOptionParameters.size());
        }
        return arrayListInitOptionParameters;
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView, com.plv.business.api.common.player.PLVBaseVideoView
    public void initial() {
        super.initial();
        x();
        o();
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoView
    public boolean isOnline() {
        return this.liveStatus == PLVLiveStatusType.LIVE;
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public boolean isOnlyAudio() {
        return this.isOnlyAudio;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView, android.widget.MediaController.MediaPlayerControl
    public boolean isPlaying() {
        return super.isPlaying();
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public boolean isValidatePlayId() {
        return true;
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView, com.plv.business.api.common.player.PLVBaseVideoView
    public void onNetWorkRecover() {
        super.onNetWorkRecover();
        ((PLVLiveListener) this.plvListener).notifyVideoViewRestart(true);
        d();
        r();
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public boolean onPlayCompelete() {
        this.ijkVideoView.resume();
        return true;
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public boolean onPlayError(int i2, int i3) {
        if (this.ad) {
            return true;
        }
        PLVLiveQOSAnalytics.getInstance().liveError(this.playId, this.userId, this.channelId, "video_type_on_error_listener", String.format(Locale.getDefault(), "framework_err:%d impl_err:%d", Integer.valueOf(i2), Integer.valueOf(i3)), "", getCurrentPlayPath(), "", PLVLiveQOSAnalytics.getInstance().getQOSAnalyticsParam());
        if (H()) {
            return true;
        }
        this.ad = true;
        a(true);
        setNoStreamViewVisibility(0);
        return true;
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public boolean onPlayInfo(int i2, int i3) {
        return false;
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public boolean onPlayPrepared() {
        if (this.am == 1) {
            setAudioModeLayoutVisibility(0);
        } else {
            setNoStreamViewVisibility(this.c_ ? 8 : 0);
            setAudioModeLayoutVisibility(8);
        }
        PLVLiveQOSAnalytics.getInstance().liveLoading(this.playId, this.userId, this.channelId, (int) (System.currentTimeMillis() - this.startLoaderTime), "", PLVLiveQOSAnalytics.getInstance().getQOSAnalyticsParam());
        b();
        return false;
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public boolean onPlaySeek() {
        return false;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void onVideoLoadSlow(int i2, boolean z2) {
        super.onVideoLoadSlow(i2, z2);
        if (this.plvListener != 0 && !K()) {
            ((PLVLiveListener) this.plvListener).notifyLoadSlow(i2, z2);
        }
        PLVELogSender.sendLiveLog(PLVErrorCodePlayVideoPlay.class, 2, new Exception((z2 ? "视频缓冲超时" : "视频加载超时") + ", loadedTime is " + i2), this.userId);
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public void reconnect() {
        if (this.liveStatus == PLVLiveStatusType.LIVE) {
            super.reconnect();
        }
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView, com.plv.business.api.common.player.IPLVBaseVideoView
    public void release(boolean z2) {
        super.release(z2);
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
        if (pLVAuxiliaryVideoview != null) {
            pLVAuxiliaryVideoview.release(false);
        }
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public void requestModleVO(PLVBaseVideoParams pLVBaseVideoParams, int i2) {
        if (i2 != 1002) {
            Log.e(TAG, "requestModleVO: is not right mode");
            return;
        }
        d();
        release(true);
        a();
        this.userId = pLVBaseVideoParams.getUserId();
        this.channelId = pLVBaseVideoParams.getChannelId();
        if (TextUtils.isEmpty(this.playId)) {
            this.playId = PLVUtils.getPid();
        }
        try {
            Boolean bool = Boolean.FALSE;
            this.isOpenWaitAD = (Boolean) pLVBaseVideoParams.getOptionValue(Boolean.class, PLVBaseVideoParams.WAIT_AD, bool);
            this.isAllowOpenAdHead = ((Boolean) pLVBaseVideoParams.getOptionValue(Boolean.class, PLVBaseVideoParams.HEAD_AD, bool)).booleanValue();
            this.isOpenMarquee = ((Boolean) pLVBaseVideoParams.getOptionValue(Boolean.class, PLVBaseVideoParams.MARQUEE, bool)).booleanValue();
            this.isOpenWatermark = ((Boolean) pLVBaseVideoParams.getOptionValue(Boolean.class, "watermark", bool)).booleanValue();
            this.viewLogParam2 = (String) pLVBaseVideoParams.getOptionValue(String.class, PLVBaseVideoParams.PARAMS2, "");
            this.viewLogParam4 = (String) pLVBaseVideoParams.getOptionValue(String.class, PLVBaseVideoParams.PARAMS4, "");
            this.viewLogParam5 = (String) pLVBaseVideoParams.getOptionValue(String.class, PLVBaseVideoParams.PARAMS5, "");
            this.aj = ((Boolean) pLVBaseVideoParams.getOptionValue(Boolean.class, PLVLiveVideoParams.LOW_LATENCY, bool)).booleanValue();
            this.f10850w = ((Integer) pLVBaseVideoParams.getOptionValue(Integer.class, PLVBaseVideoParams.LOAD_SLOW_TIME, 15)).intValue();
        } catch (Exception unused) {
            PLVCommonLog.e(TAG, "play param type is wrong");
        }
        this.ai = PLVLinkMicConfig.getInstance().isPureRtcWatchEnabled();
        N();
        if (this.am == 0) {
            setAudioModeLayoutVisibility(8);
        }
        setPlayerBufferingViewVisibility(0);
        r();
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoView
    public void rtcPrepared() {
        this.plvVideoViewNotifyer.notifyOnPrepared();
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoView
    public void setAudioModeView(IPLVLiveAudioModeView iPLVLiveAudioModeView) {
        this.an = iPLVLiveAudioModeView;
    }

    public void setLinkType(String str) {
        this.al = str;
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoView
    public void setMediaPlayMode(int i2) {
        this.am = PLVMediaPlayMode.amendMode(i2);
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setMicroPhoneListener(IPLVLiveListenerEvent.MicroPhoneListener microPhoneListener) {
        ((PLVLiveListener) this.plvListener).setMicroPhoneListener(microPhoneListener);
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView, com.plv.business.api.common.player.IPLVBaseVideoView
    public void setNoStreamIndicator(View view) {
        super.setNoStreamIndicator(view);
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
        if (pLVAuxiliaryVideoview != null) {
            pLVAuxiliaryVideoview.setNoStreamIndicator(view);
        }
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void setNoStreamViewVisibility(int i2) {
        if (this.liveStatus != PLVLiveStatusType.STOP || this.stopStreamView == null) {
            super.setNoStreamViewVisibility(i2);
            if (i2 == 0) {
                super.setStopStreamViewVisibility(4);
                return;
            }
            return;
        }
        super.setStopStreamViewVisibility(i2);
        if (i2 == 0) {
            super.setNoStreamViewVisibility(4);
        }
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnCameraShowListener(IPLVLiveListenerEvent.OnCameraShowListener onCameraShowListener) {
        ((PLVLiveListener) this.plvListener).setOnCameraShowListener(onCameraShowListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnDanmuServerOpenListener(IPLVVideoViewListenerEvent.OnDanmuServerOpenListener onDanmuServerOpenListener) {
        ((PLVLiveListener) this.plvListener).setOnDanmuServerOpenListener(onDanmuServerOpenListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGetMarqueeVoListener(IPLVVideoViewListenerEvent.OnGetMarqueeVoListener onGetMarqueeVoListener) {
        ((PLVLiveListener) this.plvListener).setOnGetMarqueeVoListener(onGetMarqueeVoListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGetWatermarkVOListener(IPLVVideoViewListenerEvent.OnGetWatermarkVoListener onGetWatermarkVoListener) {
        ((PLVLiveListener) this.plvListener).setOnGetWatermarkVOListener(onGetWatermarkVoListener);
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnLinesChangedListener(IPLVLiveListenerEvent.OnLinesChangedListener onLinesChangedListener) {
        ((PLVLiveListener) this.plvListener).setOnLinesChangedListener(onLinesChangedListener);
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnLowLatencyNetworkQualityListener(IPLVLiveListenerEvent.OnLowLatencyNetworkQualityListener onLowLatencyNetworkQualityListener) {
        ((PLVLiveListener) this.plvListener).setOnLowLatencyNetworkQualityListener(onLowLatencyNetworkQualityListener);
        Disposable disposable = this.b_;
        if (disposable != null) {
            disposable.dispose();
        }
        this.b_ = PLVRxTimer.timer(1000, new Consumer<Long>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.2
            private long lastNackCount = 0;
            private long lastVideoPacketReceived = 0;

            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                if (!PLVLiveVideoView.this.K() || PLVLiveVideoView.this.getIjkMediaPlayer() == null || PLVLiveVideoView.this.getContext() == null) {
                    return;
                }
                long webrtcNackCount = PLVLiveVideoView.this.getIjkMediaPlayer().getWebrtcNackCount();
                long webrtcVideoPacketReceived = PLVLiveVideoView.this.getIjkMediaPlayer().getWebrtcVideoPacketReceived();
                long j2 = webrtcNackCount - this.lastNackCount;
                long j3 = webrtcVideoPacketReceived - this.lastVideoPacketReceived;
                this.lastNackCount = webrtcNackCount;
                this.lastVideoPacketReceived = webrtcVideoPacketReceived;
                if (!PLVNetworkUtils.isConnected(PLVLiveVideoView.this.getContext())) {
                    ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifyOnLowLatencyNetworkQuality(14);
                    return;
                }
                if (j2 < 0 || j3 <= 0) {
                    return;
                }
                double d3 = j2 / j3;
                PLVCommonLog.i(PLVLiveVideoView.TAG, "low latency watch, ratioNack = " + d3);
                if (d3 < 0.1d) {
                    ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifyOnLowLatencyNetworkQuality(11);
                } else if (d3 < 0.3d) {
                    ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifyOnLowLatencyNetworkQuality(12);
                } else {
                    ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifyOnLowLatencyNetworkQuality(13);
                }
            }
        });
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnNoLiveAtPresentListener(IPLVLiveListenerEvent.OnNoLiveAtPresentListener onNoLiveAtPresentListener) {
        ((PLVLiveListener) this.plvListener).setOnNoLiveAtPresentListener(onNoLiveAtPresentListener);
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnOnlyAudioListener(IPLVLiveListenerEvent.OnOnlyAudioListener onOnlyAudioListener) {
        ((PLVLiveListener) this.plvListener).setOnOnlyAudioListener(onOnlyAudioListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnPPTShowListener(IPLVVideoViewListenerEvent.OnPPTShowListener onPPTShowListener) {
        ((PLVLiveListener) this.plvListener).setOnPPTShowListener(onPPTShowListener);
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnRTCPlayEventListener(IPLVLiveListenerEvent.OnRTCPlayEventListener onRTCPlayEventListener) {
        ((PLVLiveListener) this.plvListener).setOnRTCPlayEventListener(onRTCPlayEventListener);
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView, com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnSEIRefreshListener(final IPLVVideoViewListenerEvent.OnSEIRefreshListener onSEIRefreshListener) {
        Disposable disposable = this.a_;
        if (disposable != null) {
            disposable.dispose();
        }
        this.a_ = PLVRxTimer.timer(1500, new Consumer<Long>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                if (PLVLiveVideoView.this.getIjkMediaPlayer() == null) {
                    return;
                }
                long curFrameAgoraUserTC = PLVPlayerNative.getInstance().getCurFrameAgoraUserTC(PLVLiveVideoView.this.getIjkMediaPlayer()) - PLVLiveVideoView.this.getIjkMediaPlayer().getVideoCachedDuration();
                if (curFrameAgoraUserTC <= 0) {
                    return;
                }
                onSEIRefreshListener.onSEIRefresh(100, Long.toString(curFrameAgoraUserTC).getBytes());
            }
        });
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnSessionIdChangedListener(IPLVLiveListenerEvent.OnSessionIdChangedListener onSessionIdChangedListener) {
        ((PLVLiveListener) this.plvListener).setOnSessionIdChangedListener(onSessionIdChangedListener);
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnSupportRTCListener(IPLVLiveListenerEvent.OnSupportRTCListener onSupportRTCListener) {
        ((PLVLiveListener) this.plvListener).setOnSupportRTCListener(onSupportRTCListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnVideoViewRestartListener(IPLVVideoViewListenerEvent.OnVideoViewRestart onVideoViewRestart) {
        ((PLVLiveListener) this.plvListener).setOnVideoViewRestartListener(onVideoViewRestart);
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoViewListenerBinder
    public void setOnWillPlayWaittingListener(IPLVLiveListenerEvent.OnWillPlayWaittingListener onWillPlayWaittingListener) {
        ((PLVLiveListener) this.plvListener).setOnWillPlayWaittingListener(onWillPlayWaittingListener);
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoView
    public void setPPTLivePlay(String str, String str2, boolean z2) {
        PLVCommonLog.d(TAG, " setPPTLivePlay:" + str + " " + str2 + " " + z2);
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView, com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public void setViewerId(String str) {
        super.setViewerId(str);
        PolyvLiveSDKClient.getInstance().setViewerId(str);
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public void staticsVideoViewPlay() {
        PLVCommonLog.d(TAG, "staticsVideoViewPlay->playId=%s, sessionId=%s, pd=%s,", this.playId, this.sessionId, Integer.valueOf(this.watchTimeDuration));
        if (K()) {
            PLVLiveViewLog.getInstance().statLive(this.playId, this.userId, this.channelId, 0L, this.watchTimeDuration, this.stayTimeDuration, this.sessionId, "2", getViewerId(), this.viewLogParam2, "live", this.viewLogParam4, this.viewLogParam5, null);
        } else {
            PLVLiveViewLog.getInstance().statLive(this.playId, this.userId, this.channelId, 0L, this.watchTimeDuration, this.stayTimeDuration, this.sessionId, "0", getViewerId(), this.viewLogParam2, "live", this.viewLogParam4, this.viewLogParam5, null);
        }
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void toggleMediaControlsVisiblity() {
        if (this.liveStatus != PLVLiveStatusType.LIVE) {
            return;
        }
        super.toggleMediaControlsVisiblity();
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveVideoView
    public void updateMainScreenStatus(boolean z2) {
        this.c_ = z2;
        if (this.am == 0) {
            setNoStreamViewVisibility(z2 ? 4 : 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void A() {
        setNoStreamViewVisibility(0);
        ((PLVLiveListener) this.plvListener).notifyLiveEnd();
        if (this.ai) {
            ((PLVLiveListener) this.plvListener).notifyRTCLiveEnd();
        }
        this.sessionId = "";
        PLVLinkMicConfig.getInstance().setSessionId("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void B() {
        setNoStreamViewVisibility(0);
        if (this.ai) {
            ((PLVLiveListener) this.plvListener).notifyRTCLiveEnd();
        }
        ((PLVLiveListener) this.plvListener).notifyLiveStop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void C() {
        this.watchTimeDuration = 0;
        this.stayTimeDuration = 0;
        this.playId = PLVUtils.getPid();
    }

    private void D() {
        Disposable disposable = this.N;
        if (disposable != null) {
            disposable.dispose();
        }
        Disposable disposable2 = this.f10848b;
        if (disposable2 != null) {
            disposable2.dispose();
        }
        Disposable disposable3 = this.R;
        if (disposable3 != null) {
            disposable3.dispose();
        }
        Disposable disposable4 = this.Q;
        if (disposable4 != null) {
            disposable4.dispose();
        }
        Disposable disposable5 = this.U;
        if (disposable5 != null) {
            disposable5.dispose();
        }
    }

    private void E() {
        Disposable disposable = this.f10849d;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void F() {
        Disposable disposable = this.U;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void G() {
        PLVCommonLog.d(TAG, "cancleLiveRefresh");
        Disposable disposable = this.S;
        if (disposable != null) {
            disposable.dispose();
            this.S = null;
        }
    }

    private boolean H() {
        List<PolyvLiveLinesVO> lines;
        PolyvLiveChannelVO polyvLiveChannelVO = this.L;
        if (polyvLiveChannelVO != null && (lines = polyvLiveChannelVO.getLines()) != null) {
            if (lines.size() != 1) {
                int size = lines.size() - 1;
                int i2 = this.linesPos;
                if (size >= i2 + 1) {
                    int i3 = i2 + 1;
                    this.linesPos = i3;
                    changeLines(i3);
                    ((PLVLiveListener) this.plvListener).notifyLinesChanged(this.linesPos);
                    return true;
                }
            } else if (!this.ag) {
                I();
                return true;
            }
        }
        return false;
    }

    private void I() {
        this.ag = true;
    }

    private Uri J() {
        String audioFlv;
        List<PolyvLiveLinesVO> lines = this.L.getLines();
        if (lines == null || lines.isEmpty()) {
            audioFlv = "";
        } else {
            int size = lines.size();
            int i2 = this.linesPos;
            audioFlv = size > i2 ? lines.get(i2).getAudioFlv() : lines.get(0).getAudioFlv();
        }
        this.playUri = audioFlv;
        return Uri.parse(audioFlv);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean K() {
        return this.aj && PLVLinkMicConfig.getInstance().isLowLatencyWatchEnabled();
    }

    private boolean L() {
        return (!this.L.isMutilrateEnable() || this.L.getMultirateModel() == null || TextUtils.isEmpty(this.L.getMultirateModel().getDefaultDefinitionUrl())) ? false : true;
    }

    @Nullable
    private String M() {
        List<PolyvLiveLinesVO> lines = this.L.getLines();
        if (lines == null) {
            return null;
        }
        for (PolyvLiveLinesVO polyvLiveLinesVO : lines) {
            if (polyvLiveLinesVO.isQuickLiveEnabled()) {
                return polyvLiveLinesVO.getQuickLiveUrl();
            }
        }
        return null;
    }

    private void N() {
        String str = K() ? "2" : "0";
        if (this.ak.equals(str)) {
            return;
        }
        this.ak = str;
        this.playId = PLVUtils.getPid();
        this.watchTimeDuration = 0;
        this.stayTimeDuration = 0;
    }

    private void o() {
        PLVCommonLog.d(TAG, "requestMicPhoneStatus");
        this.W = PLVRxTimer.timer(10000, new Consumer<Long>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                if (TextUtils.isEmpty(PLVLiveVideoView.this.channelId)) {
                    return;
                }
                PLVLiveVideoView.this.V = PLVResponseExcutor.excuteUndefinData(PLVApiManager.getPlvApichatApi().requestMicroPhoneStatus(PLVLiveVideoView.this.channelId), new PLVrResponseCallback<PLVMicphoneStatus>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.5.1
                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onError(Throwable th) {
                        super.onError(th);
                        PLVCommonLog.e(PLVLiveVideoView.TAG, "requestMicPhoneStatus  onError:" + th);
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onFailure(PLVResponseBean<PLVMicphoneStatus> pLVResponseBean) {
                        super.onFailure(pLVResponseBean);
                        PLVCommonLog.e(PLVLiveVideoView.TAG, "requestMicPhoneStatus  onFailure");
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onFinish() {
                        PLVCommonLog.d(PLVLiveVideoView.TAG, "requestMicPhoneStatus  onFinish");
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onSuccess(PLVMicphoneStatus pLVMicphoneStatus) {
                        PLVCommonLog.d(PLVLiveVideoView.TAG, "requestMicPhoneStatus  onSuccess");
                        if (pLVMicphoneStatus == null) {
                            return;
                        }
                        PLVLiveVideoView.this.al = pLVMicphoneStatus.getType();
                        if (((PLVBaseVideoView) PLVLiveVideoView.this).plvListener != null) {
                            ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifyMicroPhoneShow("close".equals(pLVMicphoneStatus.getStatus()) ? 4 : 0);
                        }
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void p() {
        this.f10848b = PLVResponseExcutor.excute(PLVCommonApiManager.getPlvPlayerApi().getChannelJsonEncrypt(this.userId, this.channelId), String.class, new PLVrResponseCallback<String>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.6
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable th) {
                PLVCommonLog.e(PLVLiveVideoView.TAG, "getChannelJsonEncrypt  onError:");
                super.onError(th);
                PLVLiveVideoView.this.a(th);
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFailure(PLVResponseBean<String> pLVResponseBean) {
                PLVCommonLog.e(PLVLiveVideoView.TAG, "channle data  onFailure:");
                super.onFailure(pLVResponseBean);
                PLVLiveVideoView.this.a(pLVResponseBean);
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d(PLVLiveVideoView.TAG, "getChannelJsonEncrypt  onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(String str) {
                if (PLVLiveVideoView.this.b(str)) {
                    PLVLiveVideoView.this.compositeDisposable.add(Observable.zip(PLVLiveVideoView.this.u(), PLVLiveVideoView.this.v(), PLVLiveVideoView.this.w(), new Function3<String, String, PLVLiveRestrictVO, String>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.6.3
                        @Override // io.reactivex.functions.Function3
                        @NotNull
                        public String apply(@NotNull String str2, @NotNull String str3, @NotNull PLVLiveRestrictVO pLVLiveRestrictVO) throws Exception {
                            PLVCommonLog.d(PLVLiveVideoView.TAG, "new sessionId = " + str3);
                            PLVLiveVideoView.this.L.setChannelSessionId(str3);
                            boolean zEquals = str3.equals(PLVLiveVideoView.this.sessionId) ^ true;
                            if (TextUtils.isEmpty(PLVLiveVideoView.this.sessionId) && zEquals && ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener != null) {
                                ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifySessionIdChanged(str3);
                            }
                            PLVLiveVideoView.this.sessionId = str3;
                            PLVLinkMicConfig.getInstance().setSessionId(str3);
                            if (zEquals) {
                                PLVLiveVideoView.this.C();
                            }
                            if (pLVLiveRestrictVO.isCanWatch()) {
                                PLVLiveVideoView.this.e();
                                return "";
                            }
                            PLVCommonLog.d(PLVLiveVideoView.TAG, " can not watch");
                            int iSendLiveLog = PLVELogSender.sendLiveLog(PLVErrorCodePlayVideoInfo.class, 15, new Exception("直播限制观看, errorCode is " + pLVLiveRestrictVO.getErrorCode()), PLVLiveVideoView.this.userId);
                            if (((PLVBaseVideoView) PLVLiveVideoView.this).plvListener == null) {
                                return "";
                            }
                            ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifyOnError(new PLVPlayError(PLVLiveVideoView.this.getCurrentPlayPath(), iSendLiveLog, pLVLiveRestrictVO.getErrorCode(), 1002));
                            return "";
                        }
                    }).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<String>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.6.1
                        @Override // io.reactivex.functions.Consumer
                        public void accept(String str2) throws Exception {
                        }
                    }, new Consumer<Throwable>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.6.2
                        @Override // io.reactivex.functions.Consumer
                        public void accept(Throwable th) throws Exception {
                            th.printStackTrace();
                        }
                    }));
                }
            }
        });
    }

    private void q() {
        this.N = PLVResponseExcutor.excute(PLVApiManager.getPlvLiveApi().getChannelJsonEncrypt(this.userId, this.channelId), String.class, new PLVrResponseCallback<String>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.7
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable th) {
                super.onError(th);
                String errorMessage = PLVLiveVideoView.this.getErrorMessage(th);
                PLVPlayError pLVPlayError = new PLVPlayError(PLVLiveVideoView.this.getCurrentPlayPath(), PLVELogSender.sendLiveLog(PLVErrorCodePlayVideoInfo.class, 12, th, PLVLiveVideoView.this.userId), errorMessage, 1002);
                if (((PLVBaseVideoView) PLVLiveVideoView.this).plvListener != null) {
                    ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifyOnError(pLVPlayError);
                }
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d(PLVLiveVideoView.TAG, "getChannelJsonEncrypt  onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(String str) {
                PLVLiveVideoView.this.ac = false;
                PLVLiveVideoView.this.L = (PolyvLiveChannelVO) PLVGsonUtil.fromJson(PolyvLiveChannelVO.class, str);
                if (PLVLiveVideoView.this.L != null) {
                    PLVLiveVideoView pLVLiveVideoView = PLVLiveVideoView.this;
                    pLVLiveVideoView.stream = pLVLiveVideoView.L.getStream();
                    PLVLiveVideoView.this.s();
                    PLVLiveVideoView pLVLiveVideoView2 = PLVLiveVideoView.this;
                    pLVLiveVideoView2.notifyOnGetLogoListener(pLVLiveVideoView2.L.getLogoImage(), PLVLiveVideoView.this.L.getLogoOpacity(), PLVLiveVideoView.this.L.getLogoPosition(), PLVLiveVideoView.this.L.getLogoHref());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void r() {
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        long jCurrentTimeMillis = System.currentTimeMillis();
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("appId", appId);
        arrayMap.put("timestamp", jCurrentTimeMillis + "");
        arrayMap.put("channelId", this.channelId);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, arrayMap);
        this.R = PLVResponseExcutor.excuteUndefinData(PLVApiManager.getPlvLiveStatusApi().getLiveStatusJson3(this.channelId, jCurrentTimeMillis + "", appId, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVLiveStatusVO2>(String.class) { // from class: com.plv.livescenes.video.PLVLiveVideoView.8
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVLiveStatusVO2 pLVLiveStatusVO2) {
                return new Pair<>(pLVLiveStatusVO2.getDataObj(), Boolean.valueOf(pLVLiveStatusVO2.isEncryption()));
            }
        }), new PLVrResponseCallback<PLVLiveStatusVO2>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.9
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable th) {
                super.onError(th);
                String errorMessage = PLVLiveVideoView.this.getErrorMessage(th);
                PLVPlayError pLVPlayError = new PLVPlayError(PLVLiveVideoView.this.getCurrentPlayPath(), PLVELogSender.sendLiveLog(PLVErrorCodePlayVideoInfo.class, 19, th, PLVLiveVideoView.this.userId), errorMessage, 1002);
                if (((PLVBaseVideoView) PLVLiveVideoView.this).plvListener != null) {
                    ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifyOnError(pLVPlayError);
                }
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d(PLVLiveVideoView.TAG, "getLiveStatusJson2  onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(PLVLiveStatusVO2 pLVLiveStatusVO2) {
                if (PLVLiveVideoView.this.b(pLVLiveStatusVO2)) {
                    ((PLVBaseVideoView) PLVLiveVideoView.this).playUri = null;
                    if (PLVLiveVideoView.this.c(pLVLiveStatusVO2.getLiveStatus()) && PLVLiveVideoView.this.a(pLVLiveStatusVO2)) {
                        if (PLVLiveVideoView.this.liveStatus == PLVLiveStatusType.LIVE || PLVLiveVideoView.this.S == null) {
                            PLVLiveVideoView.this.p();
                            return;
                        }
                        PLVLiveVideoView.this.setNoStreamViewVisibility(0);
                        if (PLVLiveVideoView.this.ah) {
                            if (PLVLiveVideoView.this.liveStatus == PLVLiveStatusType.STOP) {
                                ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifyLiveStop();
                            } else if (PLVLiveVideoView.this.liveStatus == PLVLiveStatusType.END) {
                                ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifyLiveEnd();
                            }
                        }
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void s() {
        this.Q = w().subscribe(new Consumer<PLVLiveRestrictVO>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.11
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVLiveRestrictVO pLVLiveRestrictVO) throws Exception {
                if (pLVLiveRestrictVO.isCanWatch()) {
                    PLVLiveVideoView.this.e();
                    return;
                }
                PLVCommonLog.d(PLVLiveVideoView.TAG, " can not watch");
                int iSendLiveLog = PLVELogSender.sendLiveLog(PLVErrorCodePlayVideoInfo.class, 15, new Exception("直播限制观看, errorCode is " + pLVLiveRestrictVO.getErrorCode()), PLVLiveVideoView.this.userId);
                if (((PLVBaseVideoView) PLVLiveVideoView.this).plvListener != null) {
                    ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifyOnError(new PLVPlayError(PLVLiveVideoView.this.getCurrentPlayPath(), iSendLiveLog, pLVLiveRestrictVO.getErrorCode(), 1002));
                }
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.12
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                th.printStackTrace();
            }
        });
    }

    private void t() {
        F();
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("appId", appId);
        map.put("timestamp", jCurrentTimeMillis + "");
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, map);
        this.U = PLVApiManager.getPlvLiveStatusApi().getLiveCountdown(this.channelId, appId, jCurrentTimeMillis + "", strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVLiveCountdownVO>(PLVLiveCountdownVO.DataBean.class) { // from class: com.plv.livescenes.video.PLVLiveVideoView.15
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVLiveCountdownVO pLVLiveCountdownVO) {
                return new Pair<>(pLVLiveCountdownVO.getDataObj(), Boolean.valueOf(pLVLiveCountdownVO.isEncryption()));
            }
        }).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<PLVLiveCountdownVO>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.13
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVLiveCountdownVO pLVLiveCountdownVO) throws Exception {
                if (pLVLiveCountdownVO.getCode() == 200 && pLVLiveCountdownVO.getData() != null) {
                    ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifyLiveCountdownCallback(pLVLiveCountdownVO);
                    return;
                }
                PLVCommonLog.e(PLVLiveVideoView.TAG, "getLiveCountdown：" + pLVLiveCountdownVO.getMessage());
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.14
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                String errorMessage = PLVLiveVideoView.this.getErrorMessage(th);
                PLVCommonLog.e(PLVLiveVideoView.TAG, "getLiveCountdown：" + errorMessage);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Observable<String> u() {
        final String str = System.currentTimeMillis() + "";
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        final String appId = PolyvLiveSDKClient.getInstance().getAppId();
        HashMap map = new HashMap();
        map.put("channelId", this.channelId);
        map.put("timestamp", str);
        map.put("appId", appId);
        final String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, map);
        return Observable.create(new ObservableOnSubscribe<String>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.16
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(@NotNull final ObservableEmitter<String> observableEmitter) throws Exception {
                PLVApiApi plvApiApi = PLVCommonApiManager.getPlvApiApi();
                String str2 = PLVLiveVideoView.this.channelId;
                String str3 = appId;
                String str4 = str;
                String str5 = strArrCreateSignWithSignatureNonceEncrypt[0];
                String signatureMethod = PLVSignCreator.getSignatureMethod();
                String[] strArr = strArrCreateSignWithSignatureNonceEncrypt;
                PLVResponseExcutor.excuteUndefinData(plvApiApi.getLiveRTCStatus(str2, str3, str4, str5, signatureMethod, strArr[1], strArr[2]).map(new PLVRxEncryptDataFunction<PLVLiveRTCStatusVO>(String.class) { // from class: com.plv.livescenes.video.PLVLiveVideoView.16.1
                    @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
                    public Pair<Object, Boolean> accept(PLVLiveRTCStatusVO pLVLiveRTCStatusVO) {
                        return new Pair<>(pLVLiveRTCStatusVO.getDataObj(), Boolean.valueOf(pLVLiveRTCStatusVO.isEncryption()));
                    }
                }), new PLVrResponseCallback<PLVLiveRTCStatusVO>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.16.2
                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onError(Throwable th) {
                        th.printStackTrace();
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onFailure(PLVResponseBean<PLVLiveRTCStatusVO> pLVResponseBean) {
                        PLVCommonLog.e(PLVLiveVideoView.TAG, "requestRTCStatus onFailure " + pLVResponseBean.getMessage());
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onFinish() {
                        PLVCommonLog.d(PLVLiveVideoView.TAG, "getLiveRTCStatus  onFinish");
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onSuccess(PLVLiveRTCStatusVO pLVLiveRTCStatusVO) {
                        String data = pLVLiveRTCStatusVO.getData();
                        PLVCommonLog.d(PLVLiveVideoView.TAG, "rtc status is :" + data);
                        PLVLiveVideoView.this.L.setSupportRTCLive(data);
                        ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifySupportRTC(PLVLiveVideoView.this.L.isSupportRTCLive());
                        observableEmitter.onNext(data);
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Observable<String> v() {
        Disposable disposable = this.P;
        if (disposable != null) {
            disposable.dispose();
        }
        final String str = System.currentTimeMillis() + "";
        final String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        HashMap map = new HashMap();
        map.put("timestamp", str);
        map.put("appId", appId);
        map.put("channelId", this.channelId);
        final String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, map);
        return Observable.create(new ObservableOnSubscribe<String>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.18
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(@NotNull final ObservableEmitter<String> observableEmitter) throws Exception {
                PLVApiApi plvApiApi = PLVCommonApiManager.getPlvApiApi();
                String str2 = strArrCreateSignWithSignatureNonceEncrypt[0];
                String signatureMethod = PLVSignCreator.getSignatureMethod();
                String str3 = appId;
                String str4 = PLVLiveVideoView.this.channelId;
                String str5 = str;
                String[] strArr = strArrCreateSignWithSignatureNonceEncrypt;
                PLVResponseExcutor.excuteUndefinData(plvApiApi.getChannelSession(str2, signatureMethod, str3, str4, str5, strArr[1], strArr[2]).map(new PLVRxEncryptDataFunction<PLVLiveChannelSessionVO>(String.class) { // from class: com.plv.livescenes.video.PLVLiveVideoView.18.1
                    @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
                    public Pair<Object, Boolean> accept(PLVLiveChannelSessionVO pLVLiveChannelSessionVO) {
                        return new Pair<>(pLVLiveChannelSessionVO.getDataObj(), Boolean.valueOf(pLVLiveChannelSessionVO.isEncryption()));
                    }
                }), new PLVrResponseCallback<PLVLiveChannelSessionVO>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.18.2
                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onError(Throwable th) {
                        super.onError(th);
                        if (observableEmitter.isDisposed()) {
                            return;
                        }
                        observableEmitter.onError(th);
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onFailure(PLVResponseBean<PLVLiveChannelSessionVO> pLVResponseBean) {
                        super.onFailure(pLVResponseBean);
                        if (observableEmitter.isDisposed()) {
                            return;
                        }
                        observableEmitter.onError(new Throwable());
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onFinish() {
                        PLVCommonLog.d(PLVLiveVideoView.TAG, "getChannelSession  onFinish");
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onSuccess(PLVLiveChannelSessionVO pLVLiveChannelSessionVO) {
                        String data = pLVLiveChannelSessionVO.getData();
                        if (TextUtils.isEmpty(data)) {
                            observableEmitter.onNext("");
                        } else {
                            observableEmitter.onNext(data);
                        }
                    }
                });
            }
        }).doOnError(new Consumer<Throwable>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.17
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVLiveVideoView.this.d();
                PLVLiveVideoView.this.stopPlay();
                ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifyOnError(new PLVPlayError(PLVLiveVideoView.this.getCurrentPlayPath(), PLVELogSender.sendLiveLog(PLVErrorCodePlayVideoInfo.class, 23, th, PLVLiveVideoView.this.userId), th.getMessage(), 1002));
                PLVCommonLog.exception(new Throwable(th));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Observable<PLVLiveRestrictVO> w() {
        return Observable.create(new ObservableOnSubscribe<PLVLiveRestrictVO>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.19
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(@NotNull final ObservableEmitter<PLVLiveRestrictVO> observableEmitter) throws Exception {
                PLVResponseExcutor.excuteUndefinData(PLVApiManager.getLiveJosnNetApi().getRestrictJson(PLVLiveVideoView.this.userId, PLVLiveVideoView.this.channelId), new PLVrResponseCallback<PLVLiveRestrictVO>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.19.1
                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onError(Throwable th) {
                        if (!observableEmitter.isDisposed()) {
                            observableEmitter.onError(th);
                        }
                        String errorMessage = PLVLiveVideoView.this.getErrorMessage(th);
                        PLVPlayError pLVPlayError = new PLVPlayError(PLVLiveVideoView.this.getCurrentPlayPath(), PLVELogSender.sendLiveLog(PLVErrorCodePlayVideoInfo.class, 16, th, PLVLiveVideoView.this.userId), errorMessage, 1002);
                        if (((PLVBaseVideoView) PLVLiveVideoView.this).plvListener != null) {
                            ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifyOnError(pLVPlayError);
                        }
                        super.onError(th);
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onFinish() {
                        PLVCommonLog.d(PLVLiveVideoView.TAG, "getRestrictJson  onFinish");
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onSuccess(PLVLiveRestrictVO pLVLiveRestrictVO) {
                        observableEmitter.onNext(pLVLiveRestrictVO);
                    }
                });
            }
        });
    }

    private void x() {
        this.T = PLVRxBus.get().toObservable(PLVSocketMessageVO.class).subscribe(new Consumer<PLVSocketMessageVO>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.20
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVSocketMessageVO pLVSocketMessageVO) throws Exception {
                String event = pLVSocketMessageVO.getEvent();
                if ("onSliceControl".equals(event) || "onSliceID".equals(event)) {
                    PLVCommonLog.d(PLVLiveVideoView.TAG, "receive ONSLICECONTROL message");
                    PLVSocketSliceControlVO pLVSocketSliceControlVO = (PLVSocketSliceControlVO) PLVGsonUtil.fromJson(PLVSocketSliceControlVO.class, pLVSocketMessageVO.getMessage());
                    if (pLVSocketSliceControlVO == null || pLVSocketSliceControlVO.getData() == null) {
                        return;
                    }
                    PLVLiveVideoView.this.c_ = pLVSocketSliceControlVO.getData().getIsCamClosed() == 0;
                    if (PLVLiveVideoView.this.am == 0) {
                        PLVLiveVideoView.this.setNoStreamViewVisibility(pLVSocketSliceControlVO.getData().getIsCamClosed() == 0 ? 4 : 0);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void y() {
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
        if (pLVAuxiliaryVideoview != null) {
            pLVAuxiliaryVideoview.release(false);
        }
        setPlayerBufferingViewVisibility(0);
        setNoStreamViewVisibility(4);
        if (this.am == 0) {
            if (K()) {
                this.playUri = M();
            }
            if (!K() || this.playUri == null) {
                if (L()) {
                    PolyvLiveLinesVO polyvLiveLinesVO = this.L.getLines().get(this.linesPos);
                    if (polyvLiveLinesVO != null && polyvLiveLinesVO.getMultirateModel() != null) {
                        a(polyvLiveLinesVO.getMultirateModel());
                    }
                } else {
                    createPlayUri();
                }
            }
        } else {
            J();
        }
        String strAddPlayUriParams = addPlayUriParams(this.playUri);
        this.playUri = strAddPlayUriParams;
        d(strAddPlayUriParams);
        ((PLVLiveListener) this.plvListener).notifyPPTShow(this.channelType == PLVLiveChannelType.ALONE ? 4 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean z() {
        PLVCommonLog.d(TAG, "startPlayTeaser");
        boolean z2 = !TextUtils.isEmpty(this.L.getWaitImage());
        boolean z3 = !TextUtils.isEmpty(this.L.getCoverImage());
        ((PLVLiveListener) this.plvListener).notifyNoLivePresenter();
        t();
        if (!this.isOpenWaitAD.booleanValue()) {
            PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
            if (pLVAuxiliaryVideoview != null) {
                pLVAuxiliaryVideoview.setNoStreamViewVisibility(0);
            }
            return true;
        }
        ((PLVLiveListener) this.plvListener).notifyPPTShow(4);
        if (z2) {
            PLVAuxiliaryVideoview pLVAuxiliaryVideoview2 = this.subVideoView;
            if (pLVAuxiliaryVideoview2 != null) {
                pLVAuxiliaryVideoview2.clear();
            }
            String waitImage = this.L.getWaitImage();
            this.playUri = waitImage;
            this.options.put(PLVPlayOption.KEY_TEASER, waitImage);
            PLVAuxiliaryVideoview pLVAuxiliaryVideoview3 = this.subVideoView;
            if (pLVAuxiliaryVideoview3 != null) {
                pLVAuxiliaryVideoview3.showWaittingImage(this.L.getCoverImage(), false, this.L.getCoverHref());
                this.subVideoView.addAudioFocusManager(this.audioFocusManager);
                this.options.put(PLVPlayOption.KEY_TEASER, this.playUri);
                this.subVideoView.initOption(this.options);
                this.subVideoView.startTeaser();
            }
            ((PLVLiveListener) this.plvListener).notifyOnWillPlayWaitting(false);
            return true;
        }
        if (!z3) {
            PLVAuxiliaryVideoview pLVAuxiliaryVideoview4 = this.subVideoView;
            if (pLVAuxiliaryVideoview4 != null) {
                pLVAuxiliaryVideoview4.setNoStreamViewVisibility(0);
            }
            setNoStreamViewVisibility(0);
            return false;
        }
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview5 = this.subVideoView;
        if (pLVAuxiliaryVideoview5 != null) {
            pLVAuxiliaryVideoview5.clear();
        }
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview6 = this.subVideoView;
        if (pLVAuxiliaryVideoview6 != null) {
            pLVAuxiliaryVideoview6.showWaittingImage(this.L.getCoverImage(), true, this.L.getCoverHref());
            this.subVideoView.startTeaser();
        }
        ((PLVLiveListener) this.plvListener).notifyOnWillPlayWaitting(true);
        return true;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public PLVLiveListener createListener() {
        return new PLVLiveListener();
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public PolyvLiveChannelVO getModleVO() {
        return this.L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c(String str) {
        try {
            this.liveStatus = PLVLiveStatusType.mapFromServerString(str);
            return true;
        } catch (PLVLiveStatusType.UnknownLiveStatusTypeException e2) {
            PLVCommonLog.e(TAG, "parseLiveStatus:" + e2.getMessage());
            ((PLVLiveListener) this.plvListener).notifyOnError(new PLVPlayError(getCurrentPlayPath(), PLVELogSender.sendLiveLog(PLVErrorCodePlayVideoInfo.class, 21, e2, this.userId), e2.getMessage(), 1002));
            return false;
        }
    }

    private void d(String str) {
        PLVUAClient.generateUserAgent(this.playId, PolyvLiveSDKClient.getInstance().getPolyvLiveAndroidSdk());
        this.startLoaderTime = System.currentTimeMillis();
        if (this.ai && K()) {
            ((PLVLiveListener) this.plvListener).notifyRTCLiveStart();
        } else {
            ((PLVLiveListener) this.plvListener).notifyRTCLiveEnd();
            setVideoURIFromSelf(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        PolyvLiveChannelVO polyvLiveChannelVO;
        PLVLiveMarqueeVO pLVLiveMarqueeVOGenerateMarqueeVo;
        PolyvLiveChannelVO polyvLiveChannelVO2;
        PLVWatermarkVO channelWatermarkModel;
        PolyvLiveChannelVO polyvLiveChannelVO3;
        PLVPlayOption pLVPlayOption = PLVPlayOption.getDefault();
        pLVPlayOption.put(PLVPlayOption.KEY_PLAYMODE, 1002);
        pLVPlayOption.put(PLVPlayOption.KEY_TIMEOUT, Integer.valueOf(this.f10850w));
        setOption(pLVPlayOption);
        prepare(pLVPlayOption.get(PLVPlayOption.KEY_PRELOADTIME) != null);
        PLVCommonLog.d(TAG, "preparePlay:" + this.liveStatus);
        int i2 = AnonymousClass22.$SwitchMap$com$plv$livescenes$config$PLVLiveStatusType[this.liveStatus.ordinal()];
        if (i2 != 1) {
            if (i2 != 2) {
                PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
                if ((pLVAuxiliaryVideoview != null && pLVAuxiliaryVideoview.isShow()) || this.S != null) {
                    return;
                }
                a(false);
                this.hasOpenAdHead = false;
                if (z()) {
                    if (this.isOpenMarquee && (polyvLiveChannelVO3 = this.L) != null) {
                        PLVLiveMarqueeVO pLVLiveMarqueeVOGenerateMarqueeVo2 = polyvLiveChannelVO3.generateMarqueeVo();
                        if (pLVLiveMarqueeVOGenerateMarqueeVo2 == null) {
                            return;
                        } else {
                            ((PLVLiveListener) this.plvListener).notifyGetMarqueeVo(pLVLiveMarqueeVOGenerateMarqueeVo2);
                        }
                    }
                    if (!this.isOpenWatermark || (polyvLiveChannelVO2 = this.L) == null || (channelWatermarkModel = polyvLiveChannelVO2.getChannelWatermarkModel()) == null) {
                        return;
                    }
                    ((PLVLiveListener) this.plvListener).notifyGetWatermarkVO(channelWatermarkModel);
                    return;
                }
            } else {
                B();
                a(false);
                return;
            }
        } else if (!f()) {
            y();
        }
        ((PLVLiveListener) this.plvListener).notifyGetWatermarkVO(this.L.getChannelWatermarkModel());
        ((PLVLiveListener) this.plvListener).notifyShowCamera(this.c_);
        if (!this.isOpenMarquee || (polyvLiveChannelVO = this.L) == null || (pLVLiveMarqueeVOGenerateMarqueeVo = polyvLiveChannelVO.generateMarqueeVo()) == null) {
            return;
        }
        ((PLVLiveListener) this.plvListener).notifyGetMarqueeVo(pLVLiveMarqueeVOGenerateMarqueeVo);
    }

    private boolean f() {
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview;
        boolean z2 = !TextUtils.isEmpty(this.L.getAdvertFlvUrl());
        boolean z3 = !TextUtils.isEmpty(this.L.getAdvertImage());
        if (!this.isAllowOpenAdHead || this.hasOpenAdHead || (pLVAuxiliaryVideoview = this.subVideoView) == null || !(z2 || z3)) {
            return false;
        }
        pLVAuxiliaryVideoview.clear();
        setNoStreamViewVisibility(8);
        G();
        ((PLVLiveListener) this.plvListener).notifyPPTShow(4);
        String advertFlvUrl = z2 ? this.L.getAdvertFlvUrl() : null;
        int advertDuration = this.L.getAdvertDuration();
        if (z3) {
            this.subVideoView.showWaittingImage(this.L.getAdvertImage(), true, this.L.getAdvertHref());
        } else {
            this.subVideoView.showWaittingImage("", false, this.L.getAdvertHref());
        }
        this.subVideoView.addAudioFocusManager(this.audioFocusManager);
        this.options.put(PLVPlayOption.KEY_HEADAD, new PLVPlayOption.HeadAdOption(advertFlvUrl, advertDuration));
        this.subVideoView.initOption(this.options);
        this.subVideoView.startHeadAd();
        this.subVideoView.setOnAuxiliaryPlayEndListener(new IPLVAuxiliaryVideoViewListenerEvent.IPLVOnAuxiliaryPlayEndListener() { // from class: com.plv.livescenes.video.PLVLiveVideoView.21
            @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent.IPLVOnAuxiliaryPlayEndListener
            public void onAfterEnd() {
            }

            @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent.IPLVOnAuxiliaryPlayEndListener
            public void onBeforeEnd(boolean z4) {
                if (((PLVCommonVideoView) PLVLiveVideoView.this).isNetWorkError) {
                    return;
                }
                ((PLVCommonVideoView) PLVLiveVideoView.this).hasOpenAdHead = true;
                PLVLiveVideoView.this.b(true);
            }
        });
        return true;
    }

    private void b() {
        PLVCommonLog.d(TAG, "startRefreshLiveStatusTimer");
        G();
        E();
        this.f10849d = PLVRxTimer.timer(this.playStatInterval, new Consumer<Long>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                PLVLiveVideoView.this.b(false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(final boolean z2) {
        if (TextUtils.isEmpty(this.stream)) {
            return;
        }
        this.compositeDisposable.add(PLVResponseExcutor.excuteResponseBodyData(PLVApiManager.getPlvLiveStatusApi().getLiveStatusByStreamV3(this.stream, this.channelId, PLVLiveViewLog.getInstance().createStaticNormalInfo(this.playId, this.userId, this.channelId, this.sessionId, null, getViewerId(), this.viewLogParam2, "live", this.viewLogParam4, this.viewLogParam5, K() ? "2" : "0", null, null, this.playerId, this.watchTimeDuration, null)), new PLVrResponseCallback<String>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.10
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d(PLVLiveVideoView.TAG, "geLiveStatusByStream  onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(String str) {
                PLVLiveStatusSessionIdVO pLVLiveStatusSessionIdVO = (PLVLiveStatusSessionIdVO) PLVGsonUtil.fromJson(PLVLiveStatusSessionIdVO.class, str);
                if (pLVLiveStatusSessionIdVO == null) {
                    return;
                }
                String sessionId = pLVLiveStatusSessionIdVO.getSessionId();
                if (!TextUtils.isEmpty(sessionId) && !sessionId.equals(PLVLiveVideoView.this.sessionId)) {
                    PLVLiveVideoView.this.sessionId = sessionId;
                    PLVLiveVideoView.this.C();
                    if (((PLVBaseVideoView) PLVLiveVideoView.this).plvListener != null) {
                        ((PLVLiveListener) ((PLVBaseVideoView) PLVLiveVideoView.this).plvListener).notifySessionIdChanged(sessionId);
                    }
                }
                if (PLVLiveVideoView.this.c(pLVLiveStatusSessionIdVO.getStatus())) {
                    PLVCommonLog.d(PLVLiveVideoView.TAG, "live status:" + PLVLiveVideoView.this.liveStatus);
                    int i2 = AnonymousClass22.$SwitchMap$com$plv$livescenes$config$PLVLiveStatusType[PLVLiveVideoView.this.liveStatus.ordinal()];
                    if (i2 == 1) {
                        if (z2) {
                            PLVLiveVideoView.this.clear();
                            PLVLiveVideoView.this.y();
                            return;
                        } else {
                            if (PLVLiveVideoView.this.isCompletedState()) {
                                PLVLiveVideoView.this.y();
                                return;
                            }
                            return;
                        }
                    }
                    if (i2 == 2) {
                        if (z2 && ((PLVBaseVideoView) PLVLiveVideoView.this).subVideoView != null) {
                            ((PLVBaseVideoView) PLVLiveVideoView.this).subVideoView.clear();
                        }
                        PLVLiveVideoView.this.release(false);
                        PLVLiveVideoView.this.B();
                        PLVLiveVideoView.this.cancelBufferingTimer();
                        PLVLiveVideoView.this.a(false);
                        return;
                    }
                    if (z2 && ((PLVBaseVideoView) PLVLiveVideoView.this).subVideoView != null) {
                        ((PLVBaseVideoView) PLVLiveVideoView.this).subVideoView.clear();
                    }
                    PLVLiveVideoView.this.release(false);
                    PLVLiveVideoView.this.z();
                    PLVLiveVideoView.this.A();
                    PLVLiveVideoView.this.cancelBufferingTimer();
                    PLVLiveVideoView.this.a(false);
                    ((PLVCommonVideoView) PLVLiveVideoView.this).hasOpenAdHead = false;
                }
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z2) {
        this.ah = z2;
        G();
        E();
        this.S = PLVRxTimer.timer(10000, 10000, new Consumer<Long>() { // from class: com.plv.livescenes.video.PLVLiveVideoView.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                PLVCommonLog.d(PLVLiveVideoView.TAG, " startRefreshTimer");
                PLVLiveVideoView.this.r();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        PLVCommonLog.d(TAG, "clearRequesting");
        D();
        E();
        G();
        Disposable disposable = this.P;
        if (disposable != null) {
            disposable.dispose();
        }
        Disposable disposable2 = this.O;
        if (disposable2 != null) {
            disposable2.dispose();
        }
        Disposable disposable3 = this.T;
        if (disposable3 != null) {
            disposable3.dispose();
        }
        CompositeDisposable compositeDisposable = this.compositeDisposable;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(PLVResponseBean<String> pLVResponseBean) {
        if (TextUtils.isEmpty(pLVResponseBean.getConvertBody())) {
            if (this.ac) {
                ((PLVLiveListener) this.plvListener).notifyOnError(new PLVPlayError(getCurrentPlayPath(), PLVELogSender.sendLiveLog(PLVErrorCodePlayVideoInfo.class, 13, new Exception("获取频道信息失败， convertBody is empty"), this.userId), PLVErrorCodePlayVideoInfo.getMessage(13), 1002));
                return;
            } else {
                if (this.ae) {
                    return;
                }
                this.ac = true;
                q();
                return;
            }
        }
        if (pLVResponseBean.getCode() != 200) {
            ((PLVLiveListener) this.plvListener).notifyOnError(new PLVPlayError(getCurrentPlayPath(), PLVELogSender.sendLiveLog(PLVErrorCodePlayVideoInfo.class, 14, new Exception("获取频道信息失败, code is " + pLVResponseBean.getCode()), this.userId), pLVResponseBean.getMessage(), 1002));
            return;
        }
        if (b(pLVResponseBean.getConvertBody())) {
            s();
        }
    }

    public PLVLiveVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.sessionId = "";
        this.userId = "";
        this.channelId = "";
        this.liveStatus = PLVLiveStatusType.END;
        this.channelType = PLVLiveChannelType.PPT;
        this.compositeDisposable = new CompositeDisposable();
        this.c_ = true;
        this.ai = false;
        this.isOnlyAudio = false;
        this.aj = false;
        this.ak = "0";
        this.al = "";
        this.am = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(String str) throws JSONException {
        String channelData2String = PolyvLiveSDKClient.getInstance().getChannelData2String(str);
        try {
            JSONObject jSONObject = new JSONObject(channelData2String);
            if (!jSONObject.isNull(f10847z)) {
                jSONObject.put(f10847z, this.sessionId);
                channelData2String = jSONObject.toString();
            }
            PolyvLiveChannelVO polyvLiveChannelVO = (PolyvLiveChannelVO) PLVGsonUtil.fromJson(PolyvLiveChannelVO.class, channelData2String);
            this.L = polyvLiveChannelVO;
            if (polyvLiveChannelVO == null) {
                Exception exc = new Exception("fail to decrypt channel json");
                ((PLVLiveListener) this.plvListener).notifyOnError(new PLVPlayError(getCurrentPlayPath(), PLVELogSender.sendLiveLog(PLVErrorCodePlayVideoInfo.class, 22, exc, this.userId), exc.getMessage(), 1002));
                PLVCommonLog.exception(exc);
                return false;
            }
            if (polyvLiveChannelVO.getReportFreq() > 0) {
                this.playStatInterval = this.L.getReportFreq();
            }
            PLVCommonLog.d(TAG, "channelVO interval = " + this.L.getReportFreq());
            this.stream = this.L.getStream();
            ((PLVLiveListener) this.plvListener).notifyOnDanmuServerOpen("N".equals(this.L.getCloseDanmuEnable()));
            boolean zEquals = "Y".equals(this.L.getIsOnlyAudio());
            this.isOnlyAudio = zEquals;
            if (zEquals) {
                this.am = 1;
            }
            ((PLVLiveListener) this.plvListener).notifyOnlyAudio(zEquals);
            notifyOnGetLogoListener(this.L.getLogoImage(), this.L.getLogoOpacity(), this.L.getLogoPosition(), this.L.getLogoHref());
            return true;
        } catch (JSONException e2) {
            PLVCommonLog.e(TAG, "createChannelJson:" + e2.getMessage());
            return false;
        }
    }

    public PLVLiveVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.sessionId = "";
        this.userId = "";
        this.channelId = "";
        this.liveStatus = PLVLiveStatusType.END;
        this.channelType = PLVLiveChannelType.PPT;
        this.compositeDisposable = new CompositeDisposable();
        this.c_ = true;
        this.ai = false;
        this.isOnlyAudio = false;
        this.aj = false;
        this.ak = "0";
        this.al = "";
        this.am = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Throwable th) {
        if (this.ac) {
            String errorMessage = getErrorMessage(th);
            PLVPlayError pLVPlayError = new PLVPlayError(getCurrentPlayPath(), PLVELogSender.sendLiveLog(PLVErrorCodePlayVideoInfo.class, 12, th, this.userId), errorMessage, 1002);
            T t2 = this.plvListener;
            if (t2 != 0) {
                ((PLVLiveListener) t2).notifyOnError(pLVPlayError);
                return;
            }
            return;
        }
        if (this.ae) {
            return;
        }
        this.ac = true;
        q();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(PLVLiveStatusVO2 pLVLiveStatusVO2) {
        if (pLVLiveStatusVO2 == null) {
            ((PLVLiveListener) this.plvListener).notifyOnError(new PLVPlayError(getCurrentPlayPath(), PLVELogSender.sendLiveLog(PLVErrorCodePlayVideoInfo.class, 17, new Exception("直播状态数据解析出错"), this.userId), PLVErrorCodePlayVideoInfo.getMessage(17), 1002));
            return false;
        }
        if (pLVLiveStatusVO2.getCode() == 200) {
            return true;
        }
        ((PLVLiveListener) this.plvListener).notifyOnError(new PLVPlayError(getCurrentPlayPath(), PLVELogSender.sendLiveLog(PLVErrorCodePlayVideoInfo.class, 18, new Exception("直播状态数据校验出错, code is " + pLVLiveStatusVO2.getCode()), this.userId), pLVLiveStatusVO2.getMessage(), 1002));
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(PLVLiveStatusVO2 pLVLiveStatusVO2) {
        try {
            this.channelType = PLVLiveChannelType.mapFromServerString(pLVLiveStatusVO2.getChannelType());
            return true;
        } catch (PLVLiveChannelType.UnknownChannelTypeException e2) {
            PLVCommonLog.e(TAG, "parseChannelType：" + e2.getMessage());
            ((PLVLiveListener) this.plvListener).notifyOnError(new PLVPlayError(getCurrentPlayPath(), PLVELogSender.sendLiveLog(PLVErrorCodePlayVideoInfo.class, 20, e2, this.userId), e2.getMessage(), 1002));
            return false;
        }
    }

    private void a() {
        this.userId = "";
        this.channelId = "";
        this.L = null;
        this.M = null;
        this.mCurrentBufferPercentage = 0;
        this.ac = false;
        this.ad = false;
        this.ae = false;
        this.stream = null;
        this.aa = false;
        this.ab = false;
        this.ah = false;
    }

    private void a(PLVBitrateVO pLVBitrateVO) {
        if (TextUtils.isEmpty(this.playUri)) {
            this.playUri = pLVBitrateVO.getDefaultDefinitionUrl();
        } else {
            PLVCommonLog.d(TAG, "change bitrate pos :" + this.bitratePos);
            if (pLVBitrateVO.getDefinitions() != null) {
                this.playUri = pLVBitrateVO.getDefinitions().get(this.bitratePos).getUrl();
            }
        }
        this.M = pLVBitrateVO;
    }
}
