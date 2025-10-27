package com.plv.business.api.auxiliary;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.businesssdk.api.common.player.PolyvBaseVideoView;
import com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView;
import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent;
import com.plv.business.api.common.player.PLVBaseVideoView;
import com.plv.business.api.common.player.PLVListenerNotifyerDecorator;
import com.plv.business.api.common.player.PLVPlayError;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent;
import com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer;
import com.plv.business.api.common.player.universalplayer.IPLVUniversalVideoView;
import com.plv.business.api.common.service.PLVAudioFocusManager;
import com.plv.business.model.video.PLVADMatterVO;
import com.plv.foundationsdk.config.PLVPlayOption;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.logcode.play.PLVErrorCodePlayVideoPlay;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.foundationsdk.utils.PLVUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class PLVAuxiliaryVideoview<T> extends PolyvBaseVideoView<PLVAuxiliaryVideoViewListener> implements IPLVAuxiliaryVideoView<T>, IPLVAuxiliaryVideoViewListenerBinder, IPLVUniversalVideoView {
    public static final int PLAY_STAGE_TAILAD_FINISH = 33;

    /* renamed from: a, reason: collision with root package name */
    private static final String f10729a = "PLVAuxiliaryVideoview";

    /* renamed from: c, reason: collision with root package name */
    private static final int f10730c = 13;

    /* renamed from: b, reason: collision with root package name */
    private int f10731b;

    /* renamed from: d, reason: collision with root package name */
    private int f10732d;

    /* renamed from: e, reason: collision with root package name */
    private List<PLVADMatterVO> f10733e;

    /* renamed from: f, reason: collision with root package name */
    private boolean f10734f;

    /* renamed from: g, reason: collision with root package name */
    private boolean f10735g;

    /* renamed from: h, reason: collision with root package name */
    private boolean f10736h;

    /* renamed from: i, reason: collision with root package name */
    private int f10737i;

    /* renamed from: j, reason: collision with root package name */
    private int f10738j;

    /* renamed from: k, reason: collision with root package name */
    private int f10739k;

    /* renamed from: l, reason: collision with root package name */
    private Uri f10740l;

    /* renamed from: m, reason: collision with root package name */
    private Uri f10741m;

    /* renamed from: n, reason: collision with root package name */
    private Uri f10742n;

    /* renamed from: o, reason: collision with root package name */
    private int f10743o;

    /* renamed from: p, reason: collision with root package name */
    private int f10744p;

    /* renamed from: q, reason: collision with root package name */
    private ImageView f10745q;

    /* renamed from: r, reason: collision with root package name */
    private String f10746r;

    /* renamed from: s, reason: collision with root package name */
    private boolean f10747s;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AdStage {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayStage {
    }

    public PLVAuxiliaryVideoview(@NonNull Context context) {
        super(context);
        this.f10731b = 0;
        this.f10743o = -1;
        this.f10746r = "";
        this.f10747s = false;
    }

    public static /* synthetic */ int e(PLVAuxiliaryVideoview pLVAuxiliaryVideoview) {
        int i2 = pLVAuxiliaryVideoview.f10732d - 1;
        pLVAuxiliaryVideoview.f10732d = i2;
        return i2;
    }

    private void getNextHeadAd() {
        if (hasNextHeadAd()) {
            List<PLVADMatterVO> list = this.f10733e;
            int i2 = this.f10743o + 1;
            this.f10743o = i2;
            PLVADMatterVO pLVADMatterVO = list.get(i2);
            pLVADMatterVO.setHeadAdPath(pLVADMatterVO.getMatterUrl());
            if (TextUtils.isEmpty(pLVADMatterVO.getHeadAdPath())) {
                PLVCommonLog.d(f10729a, "headAdUri = null");
                this.f10740l = null;
            } else {
                this.f10740l = Uri.parse(pLVADMatterVO.getHeadAdPath());
            }
            this.f10737i = pLVADMatterVO.getHeadAdDuration();
        }
    }

    public void addAudioFocusManager(PLVAudioFocusManager pLVAudioFocusManager) {
        this.audioFocusManager = pLVAudioFocusManager;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void attacherListener() {
        super.attacherListener();
        this.urlPlayCompletionListener = new IPLVVideoViewListenerEvent.OnCompletionListener() { // from class: com.plv.business.api.auxiliary.PLVAuxiliaryVideoview.3
            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnCompletionListener
            public void onCompletion() {
                PLVAuxiliaryVideoview.this.setPlayerBufferingViewVisibility(8);
                if (PLVAuxiliaryVideoview.this.f10731b == 2) {
                    if (!PLVAuxiliaryVideoview.this.isCompletedState() || ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).ijkVideoView.getTargetState() == ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).ijkVideoView.getStatePauseCode()) {
                        return;
                    }
                    PLVAuxiliaryVideoview.this.start();
                    return;
                }
                if (PLVAuxiliaryVideoview.this.f10731b != 1 || !PLVAuxiliaryVideoview.this.isCompletedState() || ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).ijkVideoView.getTargetState() == ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).ijkVideoView.getStatePauseCode() || ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).plvListener == null) {
                    return;
                }
                ((PLVAuxiliaryVideoViewListener) ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).plvListener).notifyAuxiliaryPlayBeforeEndListener(false);
            }
        };
        this.urlPlayPreparedListener = new IPLVVideoViewListenerEvent.OnPreparedListener() { // from class: com.plv.business.api.auxiliary.PLVAuxiliaryVideoview.4
            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnPreparedListener
            public void onPrepared() {
                PLVAuxiliaryVideoview.this.b();
                ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).isFirstStart = false;
                if (((PLVBaseVideoView) PLVAuxiliaryVideoview.this).ijkVideoView.getTargetState() != ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).ijkVideoView.getStatePauseCode()) {
                    PLVAuxiliaryVideoview pLVAuxiliaryVideoview = PLVAuxiliaryVideoview.this;
                    pLVAuxiliaryVideoview.start(((PLVBaseVideoView) pLVAuxiliaryVideoview).isFirstStart = true);
                }
            }

            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnPreparedListener
            public void onPreparing() {
                PLVCommonLog.d(PLVAuxiliaryVideoview.f10729a, "onPreparing");
            }
        };
        this.urlPlayErrorListener = new IPLVVideoViewListenerEvent.OnErrorListener() { // from class: com.plv.business.api.auxiliary.PLVAuxiliaryVideoview.5
            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnErrorListener
            public void onError(int i2, int i3) {
                IMediaPlayer mediaPlayer = PLVAuxiliaryVideoview.this.getMediaPlayer();
                if (mediaPlayer != null) {
                    PLVAuxiliaryVideoview.this.callOnError(PLVPlayError.toErrorObj(mediaPlayer.getDataSource(), PLVErrorCodePlayVideoPlay.getCode(1), PLVErrorCodePlayVideoPlay.getMessage(1), PLVAuxiliaryVideoview.this.getPlayStage()));
                }
            }

            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnErrorListener
            public void onError(PLVPlayError pLVPlayError) {
                PLVCommonLog.d(PLVAuxiliaryVideoview.f10729a, "error:" + pLVPlayError.errorCode);
            }
        };
        this.urlPlayInfoListener = new IPLVVideoViewListenerEvent.OnInfoListener() { // from class: com.plv.business.api.auxiliary.PLVAuxiliaryVideoview.6
            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnInfoListener
            public void onInfo(int i2, int i3) {
                if (PLVAuxiliaryVideoview.this.getMediaPlayer() != null) {
                    if (i2 == 701) {
                        ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).isBuffering = true;
                        PLVAuxiliaryVideoview.this.setPlayerBufferingViewVisibility(0);
                    } else if (i2 == 702) {
                        ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).isBuffering = false;
                        PLVAuxiliaryVideoview.this.setPlayerBufferingViewVisibility(8);
                    }
                }
            }
        };
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public void bindData(T t2) {
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void callOnError(PLVPlayError pLVPlayError) {
        c();
        stopTimeoutCountdown();
        setPlayerBufferingViewVisibility(8);
        IIjkVideoView iIjkVideoView = this.ijkVideoView;
        iIjkVideoView.setTargetState(iIjkVideoView.getStatePlaybackCompletedCode());
        int i2 = this.f10731b;
        if (i2 != 1) {
            if (i2 == 2) {
                ((PLVAuxiliaryVideoViewListener) this.plvListener).notifyOnError(pLVPlayError);
                return;
            } else {
                if (i2 != 3) {
                    return;
                }
                ((PLVAuxiliaryVideoViewListener) this.plvListener).notifyOnError(pLVPlayError);
                return;
            }
        }
        ((PLVAuxiliaryVideoViewListener) this.plvListener).notifyOnError(pLVPlayError);
        if (isTargetCompletedState() && hasNextHeadAd()) {
            startHeadAd();
        } else if (isTargetCompletedState() && isOpenTeaser()) {
            startTeaser();
        }
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public boolean canMove() {
        return false;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void clear() {
        PLVCommonLog.d(f10729a, "clear  sub player");
        hide();
        resetPlayStage();
        this.ijkVideoView.resetLoadCost();
        this.ijkVideoView.removeRenderView();
        release(false);
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public Handler createHandler() {
        return new Handler(Looper.myLooper()) { // from class: com.plv.business.api.auxiliary.PLVAuxiliaryVideoview.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i2 = message.what;
                if (i2 != 1 && i2 != 3) {
                    if (i2 == 12) {
                        PLVAuxiliaryVideoview.this.callOnDefineError(PLVErrorCodePlayVideoPlay.getCode(2), PLVErrorCodePlayVideoPlay.getMessage(2), PLVAuxiliaryVideoview.this.getPlayStage());
                        return;
                    } else {
                        if (i2 != 13) {
                            return;
                        }
                        PLVAuxiliaryVideoview.this.setPlayerBufferingViewVisibility(0);
                        return;
                    }
                }
                if (((PLVBaseVideoView) PLVAuxiliaryVideoview.this).plvListener != null) {
                    ((PLVAuxiliaryVideoViewListener) ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).plvListener).notifyAuxiliaryonCountdown(message.arg1, PLVAuxiliaryVideoview.this.f10732d, message.what);
                }
                if (PLVAuxiliaryVideoview.this.f10740l == null || PLVAuxiliaryVideoview.this.isInPlaybackState()) {
                    if (PLVAuxiliaryVideoview.e(PLVAuxiliaryVideoview.this) >= 0) {
                        Message messageObtainMessage = ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).handler.obtainMessage();
                        messageObtainMessage.copyFrom(message);
                        sendMessageDelayed(messageObtainMessage, 1000L);
                        return;
                    }
                    PLVAuxiliaryVideoview.this.release(false);
                    ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).ijkVideoView.setTargetState(((PLVBaseVideoView) PLVAuxiliaryVideoview.this).ijkVideoView.getStatePlaybackCompletedCode());
                    int i3 = message.what;
                    if (i3 != 1) {
                        if (i3 == 3) {
                            ((PLVAuxiliaryVideoViewListener) ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).plvListener).notifyAuxiliaryPlayCompletion(3);
                            PLVAuxiliaryVideoview.this.f10731b = 33;
                            return;
                        }
                        return;
                    }
                    if (((PLVBaseVideoView) PLVAuxiliaryVideoview.this).plvListener != null) {
                        ((PLVAuxiliaryVideoViewListener) ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).plvListener).notifyAuxiliaryPlayBeforeEndListener(false);
                        ((PLVAuxiliaryVideoViewListener) ((PLVBaseVideoView) PLVAuxiliaryVideoview.this).plvListener).notifyAuxiliaryPlayCompletion(1);
                    }
                    if (PLVAuxiliaryVideoview.this.isTargetCompletedState() && PLVAuxiliaryVideoview.this.hasNextHeadAd()) {
                        PLVAuxiliaryVideoview.this.startHeadAd();
                    } else if (PLVAuxiliaryVideoview.this.isTargetCompletedState() && PLVAuxiliaryVideoview.this.isOpenTeaser()) {
                        PLVAuxiliaryVideoview.this.startTeaser();
                    }
                }
            }
        };
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public IPLVVideoViewNotifyer createNotifyer(IPLVVideoViewNotifyer iPLVVideoViewNotifyer) {
        return new PLVListenerNotifyerDecorator(iPLVVideoViewNotifyer) { // from class: com.plv.business.api.auxiliary.PLVAuxiliaryVideoview.1
            @Override // com.plv.business.api.common.player.PLVListenerNotifyerDecorator, com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnBufferingUpdate(int i2) {
                super.notifyOnBufferingUpdate(i2);
            }

            @Override // com.plv.business.api.common.player.PLVListenerNotifyerDecorator, com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnCompletion() {
                super.notifyOnCompletion();
            }

            @Override // com.plv.business.api.common.player.PLVListenerNotifyerDecorator, com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public boolean notifyOnError(int i2, int i3) {
                super.notifyOnError(i2, i3);
                return true;
            }

            @Override // com.plv.business.api.common.player.PLVListenerNotifyerDecorator, com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public boolean notifyOnInfo(int i2, int i3) {
                super.notifyOnInfo(i2, i3);
                return true;
            }

            @Override // com.plv.business.api.common.player.PLVListenerNotifyerDecorator, com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnPrepared() {
                super.notifyOnPrepared();
            }

            @Override // com.plv.business.api.common.player.PLVListenerNotifyerDecorator, com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnSeekComplete() {
                super.notifyOnSeekComplete();
            }

            @Override // com.plv.business.api.common.player.PLVListenerNotifyerDecorator, com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnVideoSizeChanged(int i2, int i3, int i4, int i5) {
                super.notifyOnVideoSizeChanged(i2, i3, i4, i5);
            }
        };
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public ImageView getAdHeadImage() {
        if (this.f10731b == 1 && this.f10740l == null) {
            return this.f10745q;
        }
        return null;
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public String getHeadAdUrl() {
        Uri uri = this.f10740l;
        if (uri == null) {
            return null;
        }
        return uri.toString();
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public int getPlayStage() {
        return this.f10731b;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public String getSDKVersion() {
        return PLVUtils.getVerName(PLVAppUtils.getApp());
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public String getTailAdUrl() {
        Uri uri = this.f10741m;
        if (uri == null) {
            return null;
        }
        return uri.toString();
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public String getTeaserUrl() {
        if (this.f10742n == null) {
            return null;
        }
        return this.f10741m.toString();
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public boolean hasNextHeadAd() {
        List<PLVADMatterVO> list = this.f10733e;
        return (list == null || this.f10743o == list.size() - 1) ? false : true;
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public void hide() {
        if (getVisibility() == 0) {
            setVisibility(8);
            ((PLVAuxiliaryVideoViewListener) this.plvListener).nontifyAuxiliaryonVisibilityChange(false);
        }
        setPlayerBufferingViewVisibility(8);
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public void initOption(HashMap map) {
        this.options = map;
        this.playMode = ((Integer) map.get(PLVPlayOption.KEY_PLAYMODE)).intValue();
        this.timeoutSecond = Math.max(5, ((Integer) map.get(PLVPlayOption.KEY_TIMEOUT)).intValue());
        this.f10744p = Math.max(0, ((Integer) map.get(PLVPlayOption.KEY_LOADINGVIEW_DELAY)).intValue());
        Object obj = map.get(PLVPlayOption.KEY_HEADAD);
        if (obj instanceof PLVPlayOption.HeadAdOption) {
            PLVPlayOption.HeadAdOption headAdOption = (PLVPlayOption.HeadAdOption) obj;
            if (headAdOption.getHeadAdPath() != null) {
                this.f10740l = Uri.parse(headAdOption.getHeadAdPath());
            } else {
                PLVCommonLog.d(f10729a, "headAdUri = null");
                this.f10740l = null;
            }
            this.f10737i = headAdOption.getHeadAdDuration();
            this.f10734f = true;
            this.f10736h = false;
            this.f10735g = false;
        } else if (obj instanceof List) {
            List<PLVADMatterVO> list = (List) obj;
            if (!list.isEmpty()) {
                this.f10733e = list;
                this.f10734f = true;
                this.f10736h = false;
                this.f10735g = false;
            }
        }
        PLVPlayOption.TailAdOption tailAdOption = (PLVPlayOption.TailAdOption) map.get(PLVPlayOption.KEY_TAILAD);
        if (tailAdOption != null && isVodPlayMode()) {
            if (TextUtils.isEmpty(tailAdOption.tailAdPath)) {
                PLVCommonLog.d(f10729a, "tailAdUri = null");
                this.f10741m = null;
            } else {
                this.f10741m = Uri.parse(tailAdOption.tailAdPath);
            }
            this.f10738j = tailAdOption.tailAdDuration;
            this.f10735g = true;
            this.f10736h = false;
            this.f10734f = false;
        }
        if (map.containsKey(PLVPlayOption.KEY_TEASER) && isLivePlayMode()) {
            if (TextUtils.isEmpty((String) map.get(PLVPlayOption.KEY_TEASER))) {
                PLVCommonLog.d(f10729a, "teaserUri = null");
                this.f10742n = null;
            } else {
                this.f10742n = Uri.parse((String) map.get(PLVPlayOption.KEY_TEASER));
            }
            this.f10736h = true;
            this.f10735g = false;
            this.f10734f = false;
        }
        this.ijkVideoView.setOptionParameters(createIjkOptionsParams(initOptionParameters()));
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void initial() {
        super.initial();
        a();
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView, com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isInPlaybackState() {
        return (this.f10731b == 1 && this.f10740l == null && !TextUtils.isEmpty(this.f10746r)) ? this.f10732d >= 0 : this.ijkVideoView.isInPlaybackStateForwarding();
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isInPlaybackStateEx() {
        return isInPlaybackState();
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public boolean isOpenHeadAd() {
        return this.f10734f;
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public boolean isOpenTailAd() {
        return this.f10735g;
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public boolean isOpenTeaser() {
        return this.f10736h;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView, android.widget.MediaController.MediaPlayerControl
    public boolean isPlaying() {
        return (this.f10731b != 1 || this.f10740l != null || TextUtils.isEmpty(this.f10746r) || this.f10732d < 0) ? super.isPlaying() : this.f10747s;
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public boolean isShow() {
        return getVisibility() == 0;
    }

    public void notifyClick(int i2, boolean z2, boolean z3) {
        ((PLVAuxiliaryVideoViewListener) this.plvListener).notifyGestureAction(i2, z2, z3);
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void onNetWorkError() {
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void onNetWorkRecover() {
        super.onNetWorkRecover();
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView, com.plv.business.api.common.player.IPLVBaseVideoView
    public void pause(boolean z2) {
        PLVAudioFocusManager pLVAudioFocusManager;
        IIjkVideoView iIjkVideoView = this.ijkVideoView;
        iIjkVideoView.setTargetState(iIjkVideoView.getStatePauseCode());
        if (isInPlaybackState() && this.f10732d >= 0) {
            c();
            if (z2 && (pLVAudioFocusManager = this.audioFocusManager) != null) {
                pLVAudioFocusManager.abandonAudioFocus();
            }
            this.ijkVideoView.pause();
            ((PLVAuxiliaryVideoViewListener) this.plvListener).notifyOnVideoPause();
        }
        if (this.f10731b == 1 && this.f10740l == null && !TextUtils.isEmpty(this.f10746r)) {
            c();
        }
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public void resetPlayStage() {
        this.mCurrentBufferPercentage = 0;
        this.f10743o = -1;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void setNoStreamViewVisibility(int i2) {
        View view = this.noStreamView;
        if (view != null) {
            view.setVisibility(i2);
        }
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerBinder
    public void setOnAuxiliaryPlayEndListener(IPLVAuxiliaryVideoViewListenerEvent.IPLVOnAuxiliaryPlayEndListener iPLVOnAuxiliaryPlayEndListener) {
        ((PLVAuxiliaryVideoViewListener) this.plvListener).setOnAuxiliaryPlayEndListener(iPLVOnAuxiliaryPlayEndListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnDanmuServerOpenListener(IPLVVideoViewListenerEvent.OnDanmuServerOpenListener onDanmuServerOpenListener) {
        ((PLVAuxiliaryVideoViewListener) this.plvListener).setOnDanmuServerOpenListener(onDanmuServerOpenListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGetLogoListener(IPLVVideoViewListenerEvent.OnGetLogoListener onGetLogoListener) {
        ((PLVAuxiliaryVideoViewListener) this.plvListener).setOnGetLogoListener(onGetLogoListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGetMarqueeVoListener(IPLVVideoViewListenerEvent.OnGetMarqueeVoListener onGetMarqueeVoListener) {
        ((PLVAuxiliaryVideoViewListener) this.plvListener).setOnGetMarqueeVoListener(onGetMarqueeVoListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGetWatermarkVOListener(IPLVVideoViewListenerEvent.OnGetWatermarkVoListener onGetWatermarkVoListener) {
        ((PLVAuxiliaryVideoViewListener) this.plvListener).setOnGetWatermarkVOListener(onGetWatermarkVoListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnPPTShowListener(IPLVVideoViewListenerEvent.OnPPTShowListener onPPTShowListener) {
        ((PLVAuxiliaryVideoViewListener) this.plvListener).setOnPPTShowListener(onPPTShowListener);
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerBinder
    public void setOnSubVideoViewCountdownListener(IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewCountdownListener iPLVOnSubVideoViewCountdownListener) {
        ((PLVAuxiliaryVideoViewListener) this.plvListener).setOnSubVideoViewCountdownListener(iPLVOnSubVideoViewCountdownListener);
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerBinder
    public void setOnSubVideoViewLoadImage(IPLVAuxiliaryVideoViewListenerEvent.IPLVOnSubVideoViewLoadImage iPLVOnSubVideoViewLoadImage) {
        ((PLVAuxiliaryVideoViewListener) this.plvListener).setOnSubVideoViewLoadImage(iPLVOnSubVideoViewLoadImage);
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerBinder
    public void setOnSubVideoViewPlayStatusListener(IPLVAuxiliaryVideoViewListenerEvent.IPLVAuxliaryVideoViewPlayStatusListener iPLVAuxliaryVideoViewPlayStatusListener) {
        ((PLVAuxiliaryVideoViewListener) this.plvListener).setOnSubVideoViewPlayStatusListener(iPLVAuxliaryVideoViewPlayStatusListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnVideoLoadSlowListener(IPLVVideoViewListenerEvent.OnVideoLoadSlowListener onVideoLoadSlowListener) {
        ((PLVAuxiliaryVideoViewListener) this.plvListener).setOnVideoLoadSlowListener(onVideoLoadSlowListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnVideoViewRestartListener(IPLVVideoViewListenerEvent.OnVideoViewRestart onVideoViewRestart) {
        ((PLVAuxiliaryVideoViewListener) this.plvListener).setOnVideoViewRestartListener(onVideoViewRestart);
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public void setOpenRemind(boolean z2, int i2) {
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public void setOpenTeaser(boolean z2) {
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void setPlayerBufferingViewVisibility(int i2) {
        super.setPlayerBufferingViewVisibility(i2);
        if (i2 == 8) {
            this.handler.removeMessages(13);
        }
    }

    @Override // com.plv.business.api.common.player.universalplayer.IPLVUniversalVideoView
    public void setVideoPath(String str) {
        setVideoURI(Uri.parse(str));
    }

    @Override // com.plv.business.api.common.player.universalplayer.IPLVUniversalVideoView
    public void setVideoURI(Uri uri) {
        setVideoURI(uri, null);
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public void show() {
        if (isShow()) {
            return;
        }
        setVisibility(0);
        ((PLVAuxiliaryVideoViewListener) this.plvListener).nontifyAuxiliaryonVisibilityChange(true);
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public void showWaittingImage(String str, boolean z2, String str2) {
        this.f10745q.setVisibility(z2 ? 0 : 4);
        this.f10746r = str;
        ((PLVAuxiliaryVideoViewListener) this.plvListener).notifyAuxiliaryOnLoadImage(str, this.f10745q, str2);
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public boolean start(boolean z2) {
        IIjkVideoView iIjkVideoView = this.ijkVideoView;
        iIjkVideoView.setTargetState(iIjkVideoView.getStatePlayingCode());
        if (!isInPlaybackState()) {
            return false;
        }
        d();
        if (this.f10731b == 1 && this.f10740l == null && !TextUtils.isEmpty(this.f10746r)) {
            return true;
        }
        PLVAudioFocusManager pLVAudioFocusManager = this.audioFocusManager;
        if (pLVAudioFocusManager != null) {
            pLVAudioFocusManager.requestAudioFocus();
        }
        this.ijkVideoView.start();
        ((PLVAuxiliaryVideoViewListener) this.plvListener).notifyOnVideoPlay(z2);
        return true;
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public void startHeadAd() {
        getNextHeadAd();
        this.f10731b = 1;
        Uri uri = this.f10740l;
        if (uri != null) {
            setVideoURI(uri);
        } else {
            if (TextUtils.isEmpty(this.f10746r)) {
                return;
            }
            show();
            b();
            d();
        }
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public void startTailAd() {
        this.f10731b = 3;
        setVideoURI(this.f10741m);
    }

    @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoView
    public void startTeaser() {
        this.f10731b = 2;
        Uri uri = this.f10742n;
        if (uri != null) {
            setVideoURI(uri);
        } else {
            if (TextUtils.isEmpty(this.f10746r)) {
                return;
            }
            show();
        }
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView, com.plv.business.api.common.player.IPLVBaseVideoView
    public void stopPlay() {
        release(false);
        c();
    }

    private boolean e() {
        if (this.destroyFlag) {
            return false;
        }
        show();
        release(false);
        ((PLVAuxiliaryVideoViewListener) this.plvListener).notifyOnPreparing();
        f();
        setOnCompletionListener(this.urlPlayCompletionListener);
        setOnPreparedListener(this.urlPlayPreparedListener);
        setOnErrorListener(this.urlPlayErrorListener);
        setOnInfoListener(this.urlPlayInfoListener);
        if (getCurrentPlayPath() != null) {
            return true;
        }
        callOnDefineError(PLVErrorCodePlayVideoPlay.getCode(7), PLVErrorCodePlayVideoPlay.getMessage(7), getPlayStage());
        return false;
    }

    private void f() {
        this.handler.removeMessages(13);
        this.handler.sendEmptyMessageDelayed(13, this.f10744p);
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public PLVAuxiliaryVideoViewListener createListener() {
        return new PLVAuxiliaryVideoViewListener();
    }

    @Override // com.plv.business.api.common.player.universalplayer.IPLVUniversalVideoView
    public void setVideoURI(Uri uri, Map<String, String> map) {
        this.playUri = uri.toString();
        if (e()) {
            this.ijkVideoView.setVideoURI(uri, map);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        Uri uri;
        stopTimeoutCountdown();
        setPlayerBufferingViewVisibility(8);
        int iMax = Math.max(getDuration() / 1000, 1);
        int i2 = this.f10731b;
        if (i2 == 1) {
            int i3 = this.f10737i;
            if ((i3 <= 0 || i3 > iMax) && (uri = this.f10740l) != null && !TextUtils.isEmpty(uri.getPath())) {
                this.f10737i = iMax;
            }
            int i4 = this.f10737i;
            this.f10739k = i4;
            this.f10732d = i4;
            return;
        }
        if (i2 != 3) {
            this.f10732d = 0;
            return;
        }
        int i5 = this.f10738j;
        if (i5 <= 0 || i5 > iMax) {
            this.f10738j = iMax;
        }
        int i6 = this.f10738j;
        this.f10739k = i6;
        this.f10732d = i6;
    }

    private void c() {
        this.handler.removeMessages(1);
        this.handler.removeMessages(3);
        this.f10747s = false;
    }

    private void d() {
        c();
        if (this.f10732d < 0 || this.f10731b == 2) {
            return;
        }
        Message messageObtainMessage = this.handler.obtainMessage();
        messageObtainMessage.obj = getMediaPlayer();
        messageObtainMessage.what = this.f10731b;
        messageObtainMessage.arg1 = this.f10739k;
        this.handler.sendMessage(messageObtainMessage);
        this.f10747s = true;
    }

    private void a() {
        this.f10745q = new ImageView(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 17;
        this.f10745q.setLayoutParams(layoutParams);
        addView(this.f10745q);
    }

    public PLVAuxiliaryVideoview(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f10731b = 0;
        this.f10743o = -1;
        this.f10746r = "";
        this.f10747s = false;
    }

    public PLVAuxiliaryVideoview(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f10731b = 0;
        this.f10743o = -1;
        this.f10746r = "";
        this.f10747s = false;
    }
}
