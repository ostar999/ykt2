package com.plv.business.api.common.player.microplayer;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Pair;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alipay.sdk.cons.c;
import com.easefun.polyv.businesssdk.PolyvThirdSDKClient;
import com.easefun.polyv.businesssdk.api.common.player.PolyvBaseVideoView;
import com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView;
import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.heytap.mcssdk.constant.a;
import com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent;
import com.plv.business.api.auxiliary.PLVAuxiliaryVideoview;
import com.plv.business.api.common.mediacontrol.IPLVMediaController;
import com.plv.business.api.common.player.PLVBaseVideoView;
import com.plv.business.api.common.player.PLVListenerNotifyerDecorator;
import com.plv.business.api.common.player.PLVPlayError;
import com.plv.business.api.common.player.PLVPlayerOptionParamVO;
import com.plv.business.api.common.player.PLVVideoViewListener;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent;
import com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer;
import com.plv.business.api.common.player.microplayer.PLVLifecycleFragment;
import com.plv.business.api.common.service.PLVAudioFocusManager;
import com.plv.business.model.video.PLVBaseVideoParams;
import com.plv.foundationsdk.config.PLVPlayOption;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.logcode.play.PLVErrorCodePlayVideoPlay;
import com.plv.foundationsdk.net.PLVOkHttpDns;
import com.plv.foundationsdk.net.api.PLVFoundationApiManager;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.HttpException;

/* loaded from: classes4.dex */
public abstract class PLVCommonVideoView<R, T extends PLVVideoViewListener> extends PolyvBaseVideoView<T> implements IPLVCommonVideoView<R> {

    /* renamed from: a, reason: collision with root package name */
    private static final int f10757a = 10000;

    /* renamed from: b, reason: collision with root package name */
    private static final int f10758b = 1000;
    protected final String IJK_HTTP_HOOK;

    /* renamed from: c, reason: collision with root package name */
    private final int f10759c;

    /* renamed from: d, reason: collision with root package name */
    private String f10760d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f10761e;

    /* renamed from: f, reason: collision with root package name */
    private Disposable f10762f;

    /* renamed from: g, reason: collision with root package name */
    @Nullable
    private Disposable f10763g;

    /* renamed from: h, reason: collision with root package name */
    private boolean f10764h;
    protected boolean hasOpenAdHead;

    /* renamed from: i, reason: collision with root package name */
    private boolean f10765i;
    protected boolean isAllowOpenAdHead;
    protected boolean isNetWorkError;
    protected boolean isOpenMarquee;
    protected boolean isOpenTeaser;
    protected Boolean isOpenWaitAD;
    protected boolean isOpenWatermark;
    protected String playId;
    protected int playStatInterval;
    protected String viewLogParam2;
    protected String viewLogParam4;
    protected String viewLogParam5;

    public PLVCommonVideoView(@NonNull Context context) {
        super(context);
        this.playStatInterval = 10000;
        this.f10759c = 10000 / 1000;
        this.IJK_HTTP_HOOK = "ijkhttphook:";
        this.isOpenTeaser = false;
        this.isAllowOpenAdHead = false;
        this.isOpenMarquee = false;
        this.isOpenWatermark = true;
        this.hasOpenAdHead = false;
        this.isOpenWaitAD = Boolean.FALSE;
        this.isNetWorkError = false;
        this.f10765i = false;
    }

    public static /* synthetic */ int D(PLVCommonVideoView pLVCommonVideoView) {
        int i2 = pLVCommonVideoView.watchTimeDuration;
        pLVCommonVideoView.watchTimeDuration = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int E(PLVCommonVideoView pLVCommonVideoView) {
        int i2 = pLVCommonVideoView.stayTimeDuration;
        pLVCommonVideoView.stayTimeDuration = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int I(PLVCommonVideoView pLVCommonVideoView) {
        int i2 = pLVCommonVideoView.stayTimeDuration;
        pLVCommonVideoView.stayTimeDuration = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int J(PLVCommonVideoView pLVCommonVideoView) {
        int i2 = pLVCommonVideoView.stayTimeDuration;
        pLVCommonVideoView.stayTimeDuration = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int L(PLVCommonVideoView pLVCommonVideoView) {
        int i2 = pLVCommonVideoView.watchTimeDuration;
        pLVCommonVideoView.watchTimeDuration = i2 + 1;
        return i2;
    }

    public String addPlayUriParams(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(str);
        if (str.contains(".flv?") || str.contains(".m3u8?")) {
            sb.append("&");
            sb.append("param1=");
            sb.append(a(getViewerId()));
            sb.append("&");
            sb.append("pid=");
            sb.append(this.playId);
        } else if (str.contains(".flv") || str.contains(".m3u8")) {
            sb.append("?");
            sb.append("param1=");
            sb.append(a(getViewerId()));
            sb.append("&");
            sb.append("pid=");
            sb.append(this.playId);
        }
        return sb.toString();
    }

    public void afterHeadAdPlay() {
        if (!canPreload()) {
            PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
            if (pLVAuxiliaryVideoview != null) {
                pLVAuxiliaryVideoview.startTeaser();
                return;
            }
            return;
        }
        if (isInPlaybackState()) {
            start();
            return;
        }
        if (isPreparingState()) {
            setOnPreparedListener(this.urlPlayPreparedListener);
        } else {
            if (this.ijkVideoView.getCurrentState() == this.ijkVideoView.getStateErrorCode() || TextUtils.isEmpty(this.playUri)) {
                return;
            }
            this.ijkVideoView.setVideoPath(this.playUri);
        }
    }

    public void beginDemandPlayPolling() {
        endPlayPolling();
        this.playPollingTimer = PLVRxTimer.timer(1000, new Consumer<Long>() { // from class: com.plv.business.api.common.player.microplayer.PLVCommonVideoView.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                try {
                    if (((PLVBaseVideoView) PLVCommonVideoView.this).isBuffering || !PLVCommonVideoView.this.isPlaying()) {
                        if (PLVCommonVideoView.this.f10764h) {
                            PLVCommonVideoView.I(PLVCommonVideoView.this);
                        }
                    } else {
                        if (!PLVCommonVideoView.this.f10765i) {
                            PLVCommonVideoView.D(PLVCommonVideoView.this);
                        }
                        PLVCommonVideoView.E(PLVCommonVideoView.this);
                        if (((PLVBaseVideoView) PLVCommonVideoView.this).watchTimeDuration % PLVCommonVideoView.this.f10759c == 0) {
                            PLVCommonVideoView.this.staticsVideoViewPlay();
                        }
                    }
                } catch (Exception e2) {
                    PLVCommonLog.e("PLVBaseVideoView", "beginDemandPlayPolling:" + e2.getMessage());
                }
            }
        });
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public boolean canPlaySkipHeadAd() {
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
        return pLVAuxiliaryVideoview != null && pLVAuxiliaryVideoview.getPlayStage() == 1 && this.subVideoView.isShow();
    }

    public void cancelBufferingTimer() {
        Disposable disposable = this.f10762f;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.f10762f.dispose();
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void clear() {
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
        if (pLVAuxiliaryVideoview != null) {
            pLVAuxiliaryVideoview.clear();
        }
        super.clear();
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public IPLVVideoViewNotifyer createNotifyer(IPLVVideoViewNotifyer iPLVVideoViewNotifyer) {
        return new PLVListenerNotifyerDecorator(iPLVVideoViewNotifyer) { // from class: com.plv.business.api.common.player.microplayer.PLVCommonVideoView.2
            @Override // com.plv.business.api.common.player.PLVListenerNotifyerDecorator, com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnBufferingUpdate(int i2) {
                PLVCommonVideoView.this.b();
                super.notifyOnBufferingUpdate(i2);
            }

            @Override // com.plv.business.api.common.player.PLVListenerNotifyerDecorator, com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnCompletion() {
                PLVCommonVideoView.this.setPlayerBufferingViewVisibility(8);
                if (PLVCommonVideoView.this.isCompletedState() && ((PLVBaseVideoView) PLVCommonVideoView.this).subVideoView != null && ((PLVBaseVideoView) PLVCommonVideoView.this).subVideoView.isOpenTailAd() && ((PLVBaseVideoView) PLVCommonVideoView.this).subVideoView.getPlayStage() != 33) {
                    ((PLVBaseVideoView) PLVCommonVideoView.this).subVideoView.startTailAd();
                }
                super.notifyOnCompletion();
            }

            @Override // com.plv.business.api.common.player.PLVListenerNotifyerDecorator, com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public boolean notifyOnError(int i2, int i3) {
                PLVCommonVideoView.this.cancelBufferingTimer();
                if (((PLVBaseVideoView) PLVCommonVideoView.this).reconnectCount == ((PLVBaseVideoView) PLVCommonVideoView.this).reconnectCountdown) {
                    IMediaPlayer mediaPlayer = ((PLVBaseVideoView) PLVCommonVideoView.this).ijkVideoView.getMediaPlayer();
                    int code = PLVErrorCodePlayVideoPlay.getCode(1);
                    PLVCommonVideoView pLVCommonVideoView = PLVCommonVideoView.this;
                    pLVCommonVideoView.callOnError(PLVPlayError.toErrorObj(mediaPlayer != null ? mediaPlayer.getDataSource() : pLVCommonVideoView.getCurrentPlayPath(), code, PLVErrorCodePlayVideoPlay.getMessage(1), ((PLVBaseVideoView) PLVCommonVideoView.this).playMode));
                    PLVCommonVideoView.this.onPlayError(i2, i3);
                } else {
                    PLVCommonVideoView.this.reconnect();
                }
                return super.notifyOnError(i2, i3);
            }

            @Override // com.plv.business.api.common.player.PLVListenerNotifyerDecorator, com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public boolean notifyOnInfo(int i2, int i3) {
                PLVCommonVideoView.this.cancelBufferingTimer();
                if (PLVCommonVideoView.this.getMediaPlayer() != null) {
                    if (i2 == 701) {
                        ((PLVBaseVideoView) PLVCommonVideoView.this).isBuffering = true;
                        PLVCommonVideoView.this.setPlayerBufferingViewVisibility(0);
                        PLVCommonVideoView.this.startBufferCountdown();
                    } else if (i2 == 702) {
                        ((PLVBaseVideoView) PLVCommonVideoView.this).isBuffering = false;
                        PLVCommonVideoView.this.setPlayerBufferingViewVisibility(8);
                        PLVCommonVideoView.this.stopBufferCountdown();
                    }
                }
                PLVCommonVideoView.this.onPlayInfo(i2, i3);
                return super.notifyOnInfo(i2, i3);
            }

            @Override // com.plv.business.api.common.player.PLVListenerNotifyerDecorator, com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnPrepared() {
                if (PLVCommonVideoView.this.f10761e) {
                    PLVCommonVideoView.this.c();
                } else if (PLVCommonVideoView.this.isLivePlayMode()) {
                    PLVCommonVideoView.this.c();
                    PLVCommonVideoView.this.stopTimeoutCountdown();
                    PLVCommonVideoView.this.setPlayerBufferingViewVisibility(8);
                    PLVCommonVideoView.this.setNoStreamViewVisibility(8);
                    PLVCommonVideoView.this.setStopStreamViewVisibility(8);
                    if (((PLVBaseVideoView) PLVCommonVideoView.this).reconnectCountdown != 0) {
                        PLVCommonLog.i("PLVBaseVideoView", "直播重连成功");
                    }
                    ((PLVBaseVideoView) PLVCommonVideoView.this).reconnectCountdown = 0;
                    if (((PLVBaseVideoView) PLVCommonVideoView.this).ijkVideoView.getTargetState() != ((PLVBaseVideoView) PLVCommonVideoView.this).ijkVideoView.getStatePauseCode()) {
                        PLVCommonVideoView.this.start(false);
                    }
                } else if (PLVCommonVideoView.this.isVodPlayMode()) {
                    PLVCommonVideoView.this.c();
                    ((PLVBaseVideoView) PLVCommonVideoView.this).isFirstStart = false;
                    if (((PLVBaseVideoView) PLVCommonVideoView.this).ijkVideoView.getTargetState() != ((PLVBaseVideoView) PLVCommonVideoView.this).ijkVideoView.getStatePauseCode()) {
                        PLVCommonVideoView pLVCommonVideoView = PLVCommonVideoView.this;
                        pLVCommonVideoView.start(((PLVBaseVideoView) pLVCommonVideoView).isFirstStart = true);
                    }
                }
                PLVCommonVideoView.this.onPlayPrepared();
                super.notifyOnPrepared();
            }

            @Override // com.plv.business.api.common.player.PLVListenerNotifyerDecorator, com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnSeekComplete() {
                PLVCommonVideoView.this.onPlaySeek();
                super.notifyOnSeekComplete();
            }

            @Override // com.plv.business.api.common.player.PLVListenerNotifyerDecorator, com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public boolean notifyOnError(PLVPlayError pLVPlayError) {
                return super.notifyOnError(pLVPlayError);
            }
        };
    }

    public abstract Uri createPlayUri();

    @Override // com.plv.business.api.common.player.PLVBaseVideoView, com.plv.business.api.common.player.IPLVBaseVideoView
    public void destroy() {
        super.destroy();
        cancelBufferingTimer();
        this.hasOpenAdHead = false;
        Disposable disposable = this.f10763g;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public String getErrorMessage(Throwable th) {
        if (th == null) {
            return "";
        }
        String message = th.getMessage();
        if (!(th instanceof HttpException)) {
            return message;
        }
        try {
            return ((HttpException) th).response().errorBody().string();
        } catch (IOException e2) {
            PLVCommonLog.e("PLVBaseVideoView", "getErrorMessage:" + e2.getMessage());
            return message;
        }
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public int getStayTimeDuration() {
        return this.stayTimeDuration;
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public PLVAuxiliaryVideoview getSubVideoView() {
        return this.subVideoView;
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public String getViewerId() {
        return this.f10760d;
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public int getWatchTimeDuration() {
        return this.watchTimeDuration;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void initial() {
        super.initial();
        PLVCommonLog.d("PLVBaseVideoView", "initial");
        PLVAudioFocusManager pLVAudioFocusManager = new PLVAudioFocusManager(this.mApplicationContext);
        this.audioFocusManager = pLVAudioFocusManager;
        pLVAudioFocusManager.addPlayer(this);
        e();
        beginDemandPlayPolling();
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView, com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isBufferState() {
        return isInPlaybackState() && this.isBuffering;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isInPlaybackStateEx() {
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview;
        return isInPlaybackState() || ((pLVAuxiliaryVideoview = this.subVideoView) != null && pLVAuxiliaryVideoview.isInPlaybackStateEx());
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public boolean isPauseState() {
        return isInPlaybackState() && this.ijkVideoView.getCurrentState() == this.ijkVideoView.getStatePauseCode();
    }

    public abstract boolean isValidatePlayId();

    public void notifyOnGetLogoListener(String str, Double d3, String str2, String str3) {
        Integer num;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        int iDoubleValue = d3 == null ? 100 : (int) (d3.doubleValue() * 100.0d);
        int i2 = 0;
        if (iDoubleValue < 0) {
            iDoubleValue = 0;
        }
        int i3 = iDoubleValue <= 100 ? iDoubleValue : 100;
        HashMap map = new HashMap(4);
        map.put("tl", 1);
        map.put("tr", 2);
        map.put("bl", 3);
        map.put("br", 4);
        int iIntValue = (!map.containsKey(str2) || (num = (Integer) map.get(str2)) == null) ? 0 : num.intValue();
        if (iIntValue >= 0 && iIntValue <= 4) {
            i2 = iIntValue;
        }
        map.clear();
        this.plvListener.notifyGetLogo(str, i3, i2, str3);
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void onNetWorkError() {
        super.onNetWorkError();
        this.isNetWorkError = true;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void onNetWorkRecover() {
        super.onNetWorkRecover();
        this.isNetWorkError = false;
    }

    public abstract boolean onPlayCompelete();

    public abstract boolean onPlayError(int i2, int i3);

    public abstract boolean onPlayInfo(int i2, int i3);

    public abstract boolean onPlayPrepared();

    public abstract boolean onPlaySeek();

    @Override // android.view.View
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        toggleMediaControlsVisiblity();
        return true;
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public void playByMode(@NonNull PLVBaseVideoParams pLVBaseVideoParams, int i2) {
        if (pLVBaseVideoParams == null) {
            PLVCommonLog.e("PLVBaseVideoView", "param is null");
            return;
        }
        pLVBaseVideoParams.buildOptions(PLVPlayOption.KEY_PLAYMODE, Integer.valueOf(i2));
        String viewerId = pLVBaseVideoParams.getViewerId();
        this.f10760d = viewerId;
        setViewerId(viewerId);
        requestModleVO(pLVBaseVideoParams, i2);
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public void playFromHeadAd() {
        clear();
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
        if (pLVAuxiliaryVideoview != null && pLVAuxiliaryVideoview.isOpenHeadAd()) {
            this.subVideoView.startHeadAd();
            return;
        }
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview2 = this.subVideoView;
        if (pLVAuxiliaryVideoview2 == null || !pLVAuxiliaryVideoview2.isOpenTeaser()) {
            setVideoURI(Uri.parse(this.playUri));
        } else {
            this.subVideoView.startTeaser();
        }
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public boolean playSkipHeadAd() {
        return playSkipHeadAd(true);
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public boolean playTailAd() {
        clear();
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
        if (pLVAuxiliaryVideoview == null || !pLVAuxiliaryVideoview.isOpenTailAd()) {
            return false;
        }
        this.subVideoView.startTailAd();
        return true;
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public boolean playTeaser() {
        clear();
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
        if (pLVAuxiliaryVideoview == null || !pLVAuxiliaryVideoview.isOpenTeaser()) {
            return false;
        }
        this.subVideoView.startTeaser();
        return true;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public boolean prepare(boolean z2) {
        if (!super.prepare(z2)) {
            return false;
        }
        this.f10761e = z2;
        if (this.options.containsKey(PLVPlayOption.KEY_HEADERS)) {
            Map<String, String> map = (Map) this.options.get(PLVPlayOption.KEY_HEADERS);
            this.headers = map;
            if (map == null) {
                callOnDefineError(PLVErrorCodePlayVideoPlay.getCode(22), PLVErrorCodePlayVideoPlay.getMessage(22));
                return false;
            }
        }
        if (!this.options.containsKey(PLVPlayOption.KEY_HOST)) {
            return true;
        }
        String str = (String) this.options.get(PLVPlayOption.KEY_HOST);
        if (TextUtils.isEmpty(str)) {
            callOnDefineError(PLVErrorCodePlayVideoPlay.getCode(23), PLVErrorCodePlayVideoPlay.getMessage(23));
            return false;
        }
        Map<String, String> map2 = this.headers;
        if (map2 == null) {
            map2 = new HashMap<>();
        }
        this.headers = map2;
        map2.put(c.f3231f, " " + str);
        return true;
    }

    public void reconnect() {
        this.reconnectCountdown++;
        PLVCommonLog.i("PLVBaseVideoView", "直播重连：" + this.reconnectCountdown);
        d();
        IIjkVideoView iIjkVideoView = this.ijkVideoView;
        iIjkVideoView.setTargetState(iIjkVideoView.getStatePlayingCode());
    }

    public abstract void requestModleVO(PLVBaseVideoParams pLVBaseVideoParams, int i2);

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public void setIsLinkMic(boolean z2) {
        this.f10765i = z2;
    }

    public void setLocalWatchTime() {
        endPlayPolling();
        this.playPollingTimer = PLVRxTimer.timer(1000, new Consumer<Long>() { // from class: com.plv.business.api.common.player.microplayer.PLVCommonVideoView.6
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                PLVCommonVideoView.J(PLVCommonVideoView.this);
                if (((PLVBaseVideoView) PLVCommonVideoView.this).isBuffering || !PLVCommonVideoView.this.isPlaying()) {
                    return;
                }
                PLVCommonVideoView.L(PLVCommonVideoView.this);
            }
        });
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGetLogoListener(IPLVVideoViewListenerEvent.OnGetLogoListener onGetLogoListener) {
        this.plvListener.setOnGetLogoListener(onGetLogoListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnVideoLoadSlowListener(IPLVVideoViewListenerEvent.OnVideoLoadSlowListener onVideoLoadSlowListener) {
        this.plvListener.setOnVideoLoadSlowListener(onVideoLoadSlowListener);
    }

    public void setOption(PLVPlayOption pLVPlayOption) {
        clear();
        a();
        if (isValidatePlayId()) {
            if (pLVPlayOption == null) {
                pLVPlayOption = PLVPlayOption.getDefault();
            }
            this.playOption = pLVPlayOption;
            HashMap<String, Object> map = new HashMap<>(pLVPlayOption.getOptions());
            this.options = map;
            this.playMode = ((Integer) map.get(PLVPlayOption.KEY_PLAYMODE)).intValue();
            this.timeoutSecond = Math.max(5, ((Integer) this.options.get(PLVPlayOption.KEY_TIMEOUT)).intValue());
            this.reconnectCount = Math.max(0, ((Integer) this.options.get(PLVPlayOption.KEY_RECONNECTION_COUNT)).intValue());
            PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
            if (pLVAuxiliaryVideoview != null) {
                pLVAuxiliaryVideoview.initOption(this.options);
            }
        }
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public void setSubVideoView(@NonNull final PLVAuxiliaryVideoview pLVAuxiliaryVideoview) {
        this.subVideoView = pLVAuxiliaryVideoview;
        pLVAuxiliaryVideoview.setOnSubVideoViewPlayStatusListener(new IPLVAuxiliaryVideoViewListenerEvent.IPLVAuxliaryVideoViewPlayStatusListener() { // from class: com.plv.business.api.common.player.microplayer.PLVCommonVideoView.1
            @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent.IPLVAuxliaryVideoViewPlayStatusListener
            public void onCompletion(int i2) {
                if (i2 != 1 || pLVAuxiliaryVideoview.hasNextHeadAd() || !pLVAuxiliaryVideoview.isTargetCompletedState()) {
                    if (i2 == 3) {
                        pLVAuxiliaryVideoview.hide();
                    }
                } else if (PLVCommonVideoView.this.canPreload()) {
                    if (!PLVCommonVideoView.this.isNetWorkError) {
                        pLVAuxiliaryVideoview.hide();
                    }
                    PLVCommonVideoView.this.afterHeadAdPlay();
                }
            }

            @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent.IPLVAuxliaryVideoViewPlayStatusListener
            public void onCountdown(int i2, int i3, int i4) {
                if (!TextUtils.isEmpty(((PLVBaseVideoView) PLVCommonVideoView.this).playUri) && i2 - i3 == ((PLVBaseVideoView) PLVCommonVideoView.this).inLastHeadAdPlayTime && i4 == 1 && pLVAuxiliaryVideoview.isInPlaybackState() && !pLVAuxiliaryVideoview.hasNextHeadAd() && PLVCommonVideoView.this.canPreload()) {
                    PLVCommonVideoView pLVCommonVideoView = PLVCommonVideoView.this;
                    pLVCommonVideoView.a(Uri.parse(((PLVBaseVideoView) pLVCommonVideoView).playUri));
                }
            }

            @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent.IPLVAuxliaryVideoViewPlayStatusListener
            public void onError(PLVPlayError pLVPlayError) {
                if (pLVPlayError.playStage == 1 && !pLVAuxiliaryVideoview.hasNextHeadAd() && pLVAuxiliaryVideoview.isTargetCompletedState()) {
                    if (PLVCommonVideoView.this.canPreload()) {
                        pLVAuxiliaryVideoview.hide();
                        PLVCommonVideoView.this.afterHeadAdPlay();
                        return;
                    }
                    return;
                }
                int i2 = pLVPlayError.playStage;
                if (i2 == 2) {
                    pLVAuxiliaryVideoview.hide();
                } else if (i2 == 3) {
                    pLVAuxiliaryVideoview.hide();
                }
            }
        });
    }

    public void setVideoURI(Uri uri) {
        if (prepare(false)) {
            this.ijkVideoView.setVideoURI(uri, this.headers);
        }
    }

    public void setVideoURIFromSelf(final String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        final ArrayList<PLVPlayerOptionParamVO> arrayListInitOptionParameters = initOptionParameters();
        final HashMap map = new HashMap();
        final Pair<String, String> pairB = b(str);
        if (str.startsWith("webrtc:") || pairB == null) {
            a(str, map, arrayListInitOptionParameters);
            return;
        }
        if (str.contains("m3u8")) {
            Disposable disposable = this.f10763g;
            if (disposable != null) {
                disposable.dispose();
            }
            this.f10763g = PLVFoundationApiManager.getPlvUrlApi().requestUrl(str).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).doOnNext(new Consumer<ResponseBody>() { // from class: com.plv.business.api.common.player.microplayer.PLVCommonVideoView.10
                @Override // io.reactivex.functions.Consumer
                public void accept(ResponseBody responseBody) throws Exception {
                    String str2;
                    String str3;
                    String strString = responseBody.string();
                    if (TextUtils.isEmpty(strString)) {
                        return;
                    }
                    BufferedReader bufferedReader = new BufferedReader(new StringReader(strString));
                    String line = bufferedReader.readLine();
                    while (line != null && (!line.startsWith("http") || !line.contains("ts"))) {
                        line = bufferedReader.readLine();
                    }
                    Pair pair = pairB;
                    String str4 = (String) pair.first;
                    String str5 = (String) pair.second;
                    if (line != null) {
                        Pair pairB2 = PLVCommonVideoView.this.b(line);
                        if (pairB2 != null) {
                            str3 = (String) pairB2.first;
                            str2 = (String) pairB2.second;
                        } else {
                            str3 = null;
                            str2 = null;
                        }
                    } else {
                        str2 = str5;
                        str3 = str4;
                    }
                    if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str4) || TextUtils.isEmpty(str5)) {
                        return;
                    }
                    arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(1, "ip_host", str3));
                    arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(1, "ip_addr", str2));
                    arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(1, "ip_host_ssl", str3));
                    arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(1, "ip_addr_ssl", str2));
                    arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(1, "ip_host_key", str4));
                    arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(1, "ip_addr_key", str5));
                }
            }).observeOn(AndroidSchedulers.mainThread()).doFinally(new Action() { // from class: com.plv.business.api.common.player.microplayer.PLVCommonVideoView.9
                @Override // io.reactivex.functions.Action
                public void run() throws Exception {
                    PLVCommonVideoView.this.a(str, (Map<String, String>) map, (ArrayList<PLVPlayerOptionParamVO>) arrayListInitOptionParameters);
                }
            }).subscribe(new Consumer<ResponseBody>() { // from class: com.plv.business.api.common.player.microplayer.PLVCommonVideoView.7
                @Override // io.reactivex.functions.Consumer
                public void accept(ResponseBody responseBody) throws Exception {
                }
            }, new Consumer<Throwable>() { // from class: com.plv.business.api.common.player.microplayer.PLVCommonVideoView.8
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable th) throws Exception {
                    th.printStackTrace();
                }
            });
            return;
        }
        String str2 = (String) pairB.first;
        this.playUri = str.replaceFirst(str2, (String) pairB.second);
        map.put("Host", " " + str2);
        a(str, map, arrayListInitOptionParameters);
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public void setViewerId(String str) {
        this.f10760d = str;
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public void startFromNew() {
        if (isLivePlayMode()) {
            PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
            if (pLVAuxiliaryVideoview != null && pLVAuxiliaryVideoview.isShow()) {
                this.subVideoView.start();
                return;
            }
            if (isInPlaybackState()) {
                d();
            }
            IIjkVideoView iIjkVideoView = this.ijkVideoView;
            iIjkVideoView.setTargetState(iIjkVideoView.getStatePlayingCode());
        }
    }

    public abstract void staticsVideoViewPlay();

    @Override // com.plv.business.api.common.player.PLVBaseVideoView, com.plv.business.api.common.player.IPLVBaseVideoView
    public void stopPlay() {
        super.stopPlay();
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
        if (pLVAuxiliaryVideoview != null) {
            pLVAuxiliaryVideoview.stopPlay();
        }
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public boolean playSkipHeadAd(boolean z2) {
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview;
        if (!canPlaySkipHeadAd()) {
            clear();
            return false;
        }
        if (z2 || !((pLVAuxiliaryVideoview = this.subVideoView) == null || pLVAuxiliaryVideoview.hasNextHeadAd())) {
            this.subVideoView.release(false);
            this.subVideoView.hide();
            afterHeadAdPlay();
            return true;
        }
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview2 = this.subVideoView;
        if (pLVAuxiliaryVideoview2 == null) {
            return true;
        }
        pLVAuxiliaryVideoview2.startHeadAd();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        cancelBufferingTimer();
        if (this.f10762f == null) {
            this.f10762f = PLVRxTimer.delay(a.f7153q, new Consumer<Long>() { // from class: com.plv.business.api.common.player.microplayer.PLVCommonVideoView.3
                @Override // io.reactivex.functions.Consumer
                public void accept(Long l2) throws Exception {
                    if (((PLVBaseVideoView) PLVCommonVideoView.this).plvMediaController != null) {
                        ((PLVBaseVideoView) PLVCommonVideoView.this).plvMediaController.onLongBuffering("");
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        stopTimeoutCountdown();
        setPlayerBufferingViewVisibility(8);
        IPLVMediaController iPLVMediaController = this.plvMediaController;
        if (iPLVMediaController != null) {
            iPLVMediaController.onPrepared(this);
        }
    }

    private void d() {
        PLVAudioFocusManager pLVAudioFocusManager = this.audioFocusManager;
        if (pLVAudioFocusManager != null) {
            pLVAudioFocusManager.requestAudioFocus();
        }
        setPlayerBufferingViewVisibility(0);
        this.ijkVideoView.resetLoadCost();
        hideController();
        this.ijkVideoView.resetVideoURI();
    }

    private void e() {
        Context context = getContext();
        if (context instanceof Activity) {
            PLVLifecycleFragment.a((Activity) context, new PLVLifecycleFragment.a() { // from class: com.plv.business.api.common.player.microplayer.PLVCommonVideoView.4
                @Override // com.plv.business.api.common.player.microplayer.PLVLifecycleFragment.a
                public void onStart() {
                    super.onStart();
                    PLVCommonVideoView.this.f10764h = true;
                }

                @Override // com.plv.business.api.common.player.microplayer.PLVLifecycleFragment.a
                public void onStop() {
                    super.onStop();
                    PLVCommonVideoView.this.f10764h = false;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public Pair<String, String> b(String str) {
        if (!PLVOkHttpDns.isEnableHttpDns()) {
            return null;
        }
        PLVCommonLog.d("PLVBaseVideoView", "尝试用http dns获取ip: " + str);
        if (this.playUri.startsWith("ijkhttphook:")) {
            this.playUri.substring(12);
        }
        try {
            String host = new URL(str).getHost();
            String ip = PolyvThirdSDKClient.getHttpDns().getIp(host);
            if (ip != null && !TextUtils.isEmpty(ip)) {
                PLVCommonLog.d("PLVBaseVideoView", "http dns成功获取, host=" + host + " ip=" + ip);
                return new Pair<>(host, ip);
            }
            PLVCommonLog.i("PLVBaseVideoView", PLVSugarUtil.format("httpdns未解析出域名[{}]的ip", host));
            return null;
        } catch (MalformedURLException e2) {
            PLVCommonLog.e("PLVBaseVideoView", "getHttpDns：" + e2.getMessage());
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Uri uri) {
        if (prepare(true)) {
            this.ijkVideoView.setVideoURI(uri, this.headers);
        }
    }

    private void a() {
        setPlayerBufferingViewVisibility(0);
    }

    private static String a(String str) {
        return TextUtils.isEmpty(str) ? str : Base64.encodeToString(str.getBytes(), 10);
    }

    public PLVCommonVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.playStatInterval = 10000;
        this.f10759c = 10000 / 1000;
        this.IJK_HTTP_HOOK = "ijkhttphook:";
        this.isOpenTeaser = false;
        this.isAllowOpenAdHead = false;
        this.isOpenMarquee = false;
        this.isOpenWatermark = true;
        this.hasOpenAdHead = false;
        this.isOpenWaitAD = Boolean.FALSE;
        this.isNetWorkError = false;
        this.f10765i = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, Map<String, String> map, ArrayList<PLVPlayerOptionParamVO> arrayList) {
        this.ijkVideoView.setOptionParameters(createIjkOptionsParams(arrayList));
        this.ijkVideoView.setVideoURI(Uri.parse(str));
        startTimeoutCountdown();
    }

    public PLVCommonVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.playStatInterval = 10000;
        this.f10759c = 10000 / 1000;
        this.IJK_HTTP_HOOK = "ijkhttphook:";
        this.isOpenTeaser = false;
        this.isAllowOpenAdHead = false;
        this.isOpenMarquee = false;
        this.isOpenWatermark = true;
        this.hasOpenAdHead = false;
        this.isOpenWaitAD = Boolean.FALSE;
        this.isNetWorkError = false;
        this.f10765i = false;
    }
}
