package com.plv.business.api.common.player;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.MediaController;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alipay.sdk.cons.b;
import com.easefun.polyv.businesssdk.api.common.player.IPolyvBaseVideoView;
import com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView;
import com.easefun.polyv.mediasdk.example.widget.media.IRenderView;
import com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView;
import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.easefun.polyv.mediasdk.player.IjkLibLoader;
import com.easefun.polyv.mediasdk.player.IjkMediaPlayer;
import com.google.android.exoplayer2.C;
import com.plv.business.api.auxiliary.PLVAuxiliaryVideoview;
import com.plv.business.api.common.mediacontrol.IPLVMediaController;
import com.plv.business.api.common.player.PLVVideoViewListener;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent;
import com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer;
import com.plv.business.api.common.service.PLVAudioFocusManager;
import com.plv.foundationsdk.config.PLVPlayOption;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.foundationsdk.utils.PLVControlUtils;
import com.plv.foundationsdk.utils.PLVNetworkUtils;
import com.plv.foundationsdk.utils.PLVUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class PLVBaseVideoView<T extends PLVVideoViewListener> extends FrameLayout implements MediaController.MediaPlayerControl, IPolyvBaseVideoView<IPLVMediaController>, IPLVVideoViewListenerBinder {
    public static final int IJK_VIDEO_ID = 100001;
    public static final int PLAY_STAGE_HEADAD = 1;
    public static final int PLAY_STAGE_NONE = 0;
    public static final int PLAY_STAGE_TAILAD = 3;
    public static final int PLAY_STAGE_TEASER = 2;
    protected static final double RADIUS_SLOP = 0.7853981633974483d;
    protected static final String TAG = "PLVBaseVideoView";
    protected static final int WHAT_BUFFER_TIMEOUT = 13;
    protected static final int WHAT_TIMEOUT = 12;

    /* renamed from: a, reason: collision with root package name */
    private static final int f10748a = 3000;
    protected PLVAudioFocusManager audioFocusManager;

    /* renamed from: b, reason: collision with root package name */
    private Disposable f10749b;
    protected int bitratePos;
    protected int bufferSecond;

    /* renamed from: c, reason: collision with root package name */
    private int f10750c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f10751d;
    protected boolean destroyFlag;

    /* renamed from: e, reason: collision with root package name */
    private int f10752e;
    protected int eventType;

    /* renamed from: f, reason: collision with root package name */
    private Handler f10753f;

    /* renamed from: g, reason: collision with root package name */
    private IPLVVideoViewNotifyer f10754g;
    protected GestureDetector gestureDetector;

    /* renamed from: h, reason: collision with root package name */
    private boolean f10755h;
    protected Handler handler;
    protected Map<String, String> headers;

    /* renamed from: i, reason: collision with root package name */
    private BroadcastReceiver f10756i;
    protected IIjkVideoView ijkVideoView;
    protected int inLastHeadAdPlayTime;
    protected boolean isBuffering;
    protected boolean isEnableAccurateSeek;
    protected boolean isFirstStart;
    protected boolean isOpenScreenKeepOn;
    protected float lastX;
    protected float lastY;
    protected int linesPos;
    protected Context mApplicationContext;
    protected int mCurrentBufferPercentage;
    protected IRenderView.IRenderCallback mIRenderCallback;
    protected boolean needGesture;
    protected View noStreamView;
    protected HashMap<String, Object> options;
    protected int playMode;
    protected PLVPlayOption playOption;
    protected Disposable playPollingTimer;
    protected String playUri;
    protected View playerBufferingView;
    protected String playerId;
    protected T plvListener;
    protected IPLVMediaController plvMediaController;
    protected PLVMediaPlayerListenerHolder plvMediaPlayerListenerHolder;
    protected IPLVVideoViewNotifyer plvVideoViewNotifyer;
    protected int reconnectCount;
    protected int reconnectCountdown;
    protected long startLoaderTime;
    protected int stayTimeDuration;
    protected View stopStreamView;
    protected PLVAuxiliaryVideoview subVideoView;
    protected int surfaceHeight;
    protected int surfaceWidth;
    protected int timeoutSecond;
    protected IPLVVideoViewListenerEvent.OnCompletionListener urlPlayCompletionListener;
    protected IPLVVideoViewListenerEvent.OnErrorListener urlPlayErrorListener;
    protected IPLVVideoViewListenerEvent.OnInfoListener urlPlayInfoListener;
    protected IPLVVideoViewListenerEvent.OnPreparedListener urlPlayPreparedListener;
    protected int watchTimeDuration;

    public PLVBaseVideoView(@NonNull Context context) {
        this(context, null);
    }

    private void d() {
        this.mApplicationContext.registerReceiver(this.f10756i, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    private void e() {
        BroadcastReceiver broadcastReceiver = this.f10756i;
        if (broadcastReceiver != null) {
            try {
                this.mApplicationContext.unregisterReceiver(broadcastReceiver);
            } catch (Exception e2) {
                PLVCommonLog.exception(e2);
            }
            this.f10756i = null;
        }
    }

    public void addLogsListener() {
    }

    public void attacherListener() {
        PLVCommonLog.d(TAG, "attacherListener");
        this.ijkVideoView.setOnCompletionListener(this.plvMediaPlayerListenerHolder);
        this.ijkVideoView.setOnErrorListener(this.plvMediaPlayerListenerHolder);
        this.ijkVideoView.setOnInfoListener(this.plvMediaPlayerListenerHolder);
        this.ijkVideoView.setOnPreparedListener(this.plvMediaPlayerListenerHolder);
        this.ijkVideoView.setOnBufferingListener(this.plvMediaPlayerListenerHolder);
        this.ijkVideoView.setOnSeekCompleteListener(this.plvMediaPlayerListenerHolder);
    }

    @SuppressLint({"WrongConstant"})
    public void callOnDefineError(int i2, String str) {
        callOnDefineError(i2, str, this.playMode);
    }

    public void callOnError(PLVPlayError pLVPlayError) {
        stopTimeoutCountdown();
        setPlayerBufferingViewVisibility(8);
        this.plvListener.notifyOnError(pLVPlayError);
    }

    public abstract boolean canMove();

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canPause() {
        return this.ijkVideoView.canPause();
    }

    public boolean canPreload() {
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview;
        return isVodPlayMode() || ((pLVAuxiliaryVideoview = this.subVideoView) != null && pLVAuxiliaryVideoview.isOpenTeaser());
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekBackward() {
        return this.ijkVideoView.canSeekBackward();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekForward() {
        return this.ijkVideoView.canSeekForward();
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean canStart() {
        return false;
    }

    public void clear() {
        stopPlay();
        this.ijkVideoView.resetLoadCost();
        this.ijkVideoView.removeRenderView();
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void closeSound() {
        PLVControlUtils.closeSound(this.mApplicationContext);
    }

    public abstract Handler createHandler();

    public Object[][] createIjkOptionsParams(ArrayList<PLVPlayerOptionParamVO> arrayList) {
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        Object[][] objArr = (Object[][]) Array.newInstance((Class<?>) Object.class, arrayList.size(), 3);
        Iterator<PLVPlayerOptionParamVO> it = arrayList.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            PLVPlayerOptionParamVO next = it.next();
            objArr[i2][0] = Integer.valueOf(next.getType());
            objArr[i2][1] = next.getName();
            objArr[i2][2] = next.getValue();
            i2++;
        }
        return objArr;
    }

    public abstract T createListener();

    public abstract IPLVVideoViewNotifyer createNotifyer(IPLVVideoViewNotifyer iPLVVideoViewNotifyer);

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void destroy() {
        this.destroyFlag = true;
        PLVCommonLog.i(TAG, "destroy");
        PLVAudioFocusManager pLVAudioFocusManager = this.audioFocusManager;
        if (pLVAudioFocusManager != null) {
            pLVAudioFocusManager.abandonAudioFocus();
            this.audioFocusManager = null;
        }
        if (this.subVideoView != null) {
            PLVCommonLog.d(TAG, "destory sub video");
            this.subVideoView.destroy();
            this.subVideoView = null;
        }
        this.ijkVideoView.stopBackgroundPlay();
        release(true);
        T t2 = this.plvListener;
        if (t2 != null) {
            t2.clearAllListener();
            this.plvListener = null;
        }
        IjkMediaPlayer.native_profileEnd();
        endPlayPolling();
        Disposable disposable = this.f10749b;
        if (disposable != null) {
            disposable.dispose();
        }
        e();
        this.mApplicationContext = null;
    }

    public void enableAccurateSeek(boolean z2) {
        this.isEnableAccurateSeek = z2;
    }

    public void endPlayPolling() {
        Disposable disposable = this.playPollingTimer;
        if (disposable != null && !disposable.isDisposed()) {
            this.playPollingTimer.dispose();
            this.playPollingTimer = null;
        }
        this.watchTimeDuration = 0;
        this.stayTimeDuration = 0;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void enterBackground() {
        this.ijkVideoView.enterBackground();
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public int getAspectRatio() {
        return this.ijkVideoView.getCurrentAspectRatio();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getAudioSessionId() {
        return 0;
    }

    public int getBitratePos() {
        return this.bitratePos;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public int getBrightness(Activity activity) {
        return PLVControlUtils.getBrightness(activity);
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getBufferPercentage() {
        return getMediaPlayer() != null ? this.mCurrentBufferPercentage : this.ijkVideoView.getBufferPercentage();
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public String getCurrentPlayPath() {
        return this.playUri;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getCurrentPosition() {
        return this.ijkVideoView.getCurrentPosition();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getDuration() {
        return this.ijkVideoView.getDuration();
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public GestureDetector getGestureDetector() {
        return this.gestureDetector;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public IjkMediaPlayer getIjkMediaPlayer() {
        IIjkVideoView iIjkVideoView = this.ijkVideoView;
        if (iIjkVideoView != null) {
            return iIjkVideoView.getIjkMediaPlayer();
        }
        return null;
    }

    public IIjkVideoView getIjkVideoView() {
        return this.ijkVideoView;
    }

    public int getLinesPos() {
        return this.linesPos;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public IPLVMediaController getMediaController() {
        return this.plvMediaController;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public IMediaPlayer getMediaPlayer() {
        IIjkVideoView iIjkVideoView = this.ijkVideoView;
        if (iIjkVideoView != null) {
            return iIjkVideoView.getMediaPlayer();
        }
        return null;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean getNeedGestureDetector() {
        return this.needGesture;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public int getPlayerVolume() {
        return this.f10750c;
    }

    public String getSDKVersion() {
        return PLVUtils.getVerName(PLVAppUtils.getApp());
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public float getSpeed() {
        return this.ijkVideoView.getSpeed();
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public long getTcpSpeed() {
        IjkMediaPlayer ijkMediaPlayer = this.ijkVideoView.getIjkMediaPlayer();
        if (ijkMediaPlayer != null) {
            return ijkMediaPlayer.getTcpSpeed();
        }
        return -1L;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public int getVolume() {
        return PLVControlUtils.getVolume(this.mApplicationContext);
    }

    public void hideController() {
        IPLVMediaController iPLVMediaController = this.plvMediaController;
        if (iPLVMediaController != null) {
            iPLVMediaController.hide();
        }
    }

    public ArrayList<PLVPlayerOptionParamVO> initOptionParameters() {
        int iMin = Math.min(Math.max(0, ((Integer) this.options.get(PLVPlayOption.KEY_FRAMEDROP)).intValue()), 10);
        int i2 = this.f10752e == 1 ? 1 : 0;
        ArrayList<PLVPlayerOptionParamVO> arrayList = new ArrayList<>();
        arrayList.add(new PLVPlayerOptionParamVO(4, "framedrop", Integer.valueOf(iMin)));
        arrayList.add(new PLVPlayerOptionParamVO(4, "mediacodec", Integer.valueOf(i2)));
        arrayList.add(new PLVPlayerOptionParamVO(1, "allowed_extensions", "ALL"));
        if (i2 == 1) {
            arrayList.add(new PLVPlayerOptionParamVO(4, "mediacodec-handle-resolution-change", 1));
            arrayList.add(new PLVPlayerOptionParamVO(4, "mediacodec-auto-rotate", 1));
        } else {
            arrayList.add(new PLVPlayerOptionParamVO(2, "skip_loop_filter", "0"));
        }
        arrayList.add(new PLVPlayerOptionParamVO(1, b.f3216b, getSDKVersion()));
        if (this.isEnableAccurateSeek) {
            arrayList.add(new PLVPlayerOptionParamVO(4, "enable-accurate-seek", 1));
        }
        arrayList.add(new PLVPlayerOptionParamVO(1, "max_open_fail", "5"));
        PLVCommonLog.d(TAG, "base video initOptionParameters size ：" + arrayList.size());
        return arrayList;
    }

    public void initial() {
        this.mApplicationContext = getContext().getApplicationContext();
        this.playerId = PLVUtils.getPid();
        d();
        Handler handlerCreateHandler = createHandler();
        this.handler = handlerCreateHandler;
        if (handlerCreateHandler == null) {
            this.handler = this.f10753f;
        }
        if (a()) {
            attacherListener();
            addLogsListener();
        }
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isBufferState() {
        return isInPlaybackState() && this.isBuffering;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isCompletedState() {
        return isInPlaybackState() && this.ijkVideoView.getCurrentState() == this.ijkVideoView.getStatePlaybackCompletedCode();
    }

    public boolean isInPlaybackState() {
        return this.ijkVideoView.isInPlaybackStateForwarding();
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isLivePlayMode() {
        return this.playMode == 1002;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isOpenSound() {
        return PLVControlUtils.isOpenSound(this.mApplicationContext);
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isPlayState() {
        return isPlayState(false);
    }

    public boolean isPlaying() {
        return this.ijkVideoView.isPlaying() || this.ijkVideoView.getTargetState() == this.ijkVideoView.getStatePlayingCode();
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isPreparedState() {
        return getMediaPlayer() != null && this.ijkVideoView.getCurrentState() == this.ijkVideoView.getStatePreparedCode();
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isPreparingState() {
        return getMediaPlayer() != null && this.ijkVideoView.getCurrentState() == this.ijkVideoView.getStatePreparingCode();
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isRealPlaying() {
        return this.ijkVideoView.isPlaying();
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isTargetCompletedState() {
        return this.ijkVideoView.getTargetState() == this.ijkVideoView.getStatePlaybackCompletedCode();
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isVodPlayMode() {
        return this.playMode == 1001;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void keepPlayerVolume(boolean z2) {
        this.f10751d = z2;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        boolean z2 = (i2 == 4 || i2 == 24 || i2 == 25 || i2 == 164 || i2 == 82 || i2 == 5 || i2 == 6) ? false : true;
        if (isInPlaybackStateEx() && z2 && this.plvMediaController != null) {
            if (i2 == 79 || i2 == 85) {
                if (isPlaying()) {
                    pause();
                    this.plvMediaController.show();
                } else {
                    start();
                    this.plvMediaController.hide();
                }
                return true;
            }
            if (i2 == 126) {
                if (!isPlaying()) {
                    start();
                    this.plvMediaController.hide();
                }
                return true;
            }
            if (i2 == 86 || i2 == 127) {
                if (isPlaying()) {
                    pause();
                    this.plvMediaController.show();
                }
                return true;
            }
            toggleMediaControlsVisiblity();
        }
        return super.onKeyDown(i2, keyEvent);
    }

    public void onNetWorkError() {
    }

    public void onNetWorkRecover() {
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        T t2;
        if (!this.needGesture) {
            toggleMediaControlsVisiblity();
            return false;
        }
        GestureDetector gestureDetector = this.gestureDetector;
        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(motionEvent);
        }
        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            int i2 = this.eventType;
            if (i2 == 0 || i2 == 1 || i2 == 2 || i2 == 3) {
                T t3 = this.plvListener;
                if (t3 != null) {
                    t3.notifyGestureAction(i2, false, true);
                }
            } else if ((i2 == 4 || i2 == 5) && (t2 = this.plvListener) != null) {
                t2.notifyGestureAction(i2, false, true, 1);
            }
            this.lastX = 0.0f;
            this.lastY = 0.0f;
            this.eventType = -1;
        }
        return true;
    }

    public void onVideoLoadSlow(int i2, boolean z2) {
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void openKeepScreenOn(boolean z2) {
        this.isOpenScreenKeepOn = z2;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void openSound() {
        PLVControlUtils.openSound(this.mApplicationContext);
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void pause() {
        a(false, "pause");
        pause(true);
    }

    public boolean prepare(boolean z2) {
        if (this.destroyFlag) {
            return false;
        }
        release(false);
        this.plvListener.notifyOnPreparing();
        setPlayerBufferingViewVisibility(0);
        return true;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void release(boolean z2) {
        PLVCommonLog.d(TAG, "release player");
        this.ijkVideoView.release(z2);
        this.ijkVideoView.resetLoadCost();
        IIjkVideoView iIjkVideoView = this.ijkVideoView;
        iIjkVideoView.setTargetState(iIjkVideoView.getStateIdleCode());
        setPlayerBufferingViewVisibility(8);
        stopTimeoutCountdown();
        hideController();
        c();
        a(false, "release(boolean cleartargetstate)");
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void removeRenderView() {
        IIjkVideoView iIjkVideoView = this.ijkVideoView;
        if (iIjkVideoView != null) {
            iIjkVideoView.removeRenderView();
        }
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public Bitmap screenshot() {
        return this.ijkVideoView.screenshot();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void seekTo(int i2) {
        if (i2 >= getDuration()) {
            i2 = getDuration() - 100;
        } else if (i2 < 0) {
            i2 = 0;
        }
        this.ijkVideoView.seekTo(i2);
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean setAspectRatio(int i2) {
        if (this.ijkVideoView.getRenderView() == null) {
            return false;
        }
        this.ijkVideoView.setCurrentAspectRatio(i2);
        return true;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void setBrightness(Activity activity, int i2) {
        PLVControlUtils.setBrightness(activity, i2);
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void setDecodeMode(int i2) {
        this.f10752e = i2;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void setMediaController(IPLVMediaController iPLVMediaController) {
        this.plvMediaController = iPLVMediaController;
        this.ijkVideoView.setMediaController(iPLVMediaController);
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void setMirror(boolean z2) {
        this.ijkVideoView.setMirror(z2);
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void setNeedGestureDetector(boolean z2) {
        this.needGesture = z2;
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void setNoStreamIndicator(View view) {
        this.noStreamView = view;
    }

    public void setNoStreamViewVisibility(int i2) {
        View view = this.noStreamView;
        if (view != null) {
            view.setVisibility(i2);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnBufferingUpdateListener(IPLVVideoViewListenerEvent.OnBufferingUpdateListener onBufferingUpdateListener) {
        this.plvListener.setOnBufferingUpdateListener(onBufferingUpdateListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnCompletionListener(IPLVVideoViewListenerEvent.OnCompletionListener onCompletionListener) {
        this.plvListener.setOnCompletionListener(onCompletionListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnCoverImageOutListener(IPLVVideoViewListenerEvent.OnCoverImageOutListener onCoverImageOutListener) {
        this.plvListener.setOnCoverImageOutListener(onCoverImageOutListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnErrorListener(IPLVVideoViewListenerEvent.OnErrorListener onErrorListener) {
        this.plvListener.setOnErrorListener(onErrorListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureClickListener(IPLVVideoViewListenerEvent.OnGestureClickListener onGestureClickListener) {
        this.plvListener.setOnGestureClickListener(onGestureClickListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureDoubleClickListener(IPLVVideoViewListenerEvent.OnGestureDoubleClickListener onGestureDoubleClickListener) {
        this.plvListener.setOnGestureDoubleClickListener(onGestureDoubleClickListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureLeftDownListener(IPLVVideoViewListenerEvent.OnGestureLeftDownListener onGestureLeftDownListener) {
        this.plvListener.setOnGestureLeftDownListener(onGestureLeftDownListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureLeftUpListener(IPLVVideoViewListenerEvent.OnGestureLeftUpListener onGestureLeftUpListener) {
        this.plvListener.setOnGestureLeftUpListener(onGestureLeftUpListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureRightDownListener(IPLVVideoViewListenerEvent.OnGestureRightDownListener onGestureRightDownListener) {
        this.plvListener.setOnGestureRightDownListener(onGestureRightDownListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureRightUpListener(IPLVVideoViewListenerEvent.OnGestureRightUpListener onGestureRightUpListener) {
        this.plvListener.setOnGestureRightUpListener(onGestureRightUpListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureSwipeLeftListener(IPLVVideoViewListenerEvent.OnGestureSwipeLeftListener onGestureSwipeLeftListener) {
        this.plvListener.setOnGestureSwipeLeftListener(onGestureSwipeLeftListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureSwipeRightListener(IPLVVideoViewListenerEvent.OnGestureSwipeRightListener onGestureSwipeRightListener) {
        this.plvListener.setOnGestureSwipeRightListener(onGestureSwipeRightListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnInfoListener(IPLVVideoViewListenerEvent.OnInfoListener onInfoListener) {
        this.plvListener.setOnInfoListener(onInfoListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnNetworkStateListener(IPLVVideoViewListenerEvent.OnNetworkStateListener onNetworkStateListener) {
        this.plvListener.setOnNetworkStateListener(onNetworkStateListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnPreparedListener(IPLVVideoViewListenerEvent.OnPreparedListener onPreparedListener) {
        this.plvListener.setOnPreparedListener(onPreparedListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnSEIRefreshListener(IPLVVideoViewListenerEvent.OnSEIRefreshListener onSEIRefreshListener) {
        this.plvListener.setOnSEIRefreshListener(onSEIRefreshListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnSeekCompleteListener(IPLVVideoViewListenerEvent.OnSeekCompleteListener onSeekCompleteListener) {
        this.plvListener.setOnSeekCompleteListener(onSeekCompleteListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnVideoPauseListener(IPLVVideoViewListenerEvent.OnVideoPauseListener onVideoPauseListener) {
        this.plvListener.setOnVideoPauseListener(onVideoPauseListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnVideoPlayListener(IPLVVideoViewListenerEvent.OnVideoPlayListener onVideoPlayListener) {
        this.plvListener.setOnVideoPlayListener(onVideoPlayListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnVideoSizeChangedListener(IPLVVideoViewListenerEvent.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.plvListener.setOnVideoSizeChangedListener(onVideoSizeChangedListener);
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void setPlayerBufferingIndicator(@NonNull View view) {
        this.playerBufferingView = view;
    }

    public void setPlayerBufferingViewVisibility(int i2) {
        final View view = this.playerBufferingView;
        if (view == null) {
            return;
        }
        if (i2 == 0) {
            Disposable disposable = this.f10749b;
            if (disposable != null) {
                disposable.dispose();
            }
            this.f10749b = PLVRxTimer.delay(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS, new Consumer<Object>() { // from class: com.plv.business.api.common.player.PLVBaseVideoView.4
                @Override // io.reactivex.functions.Consumer
                public void accept(Object obj) throws Exception {
                    view.setVisibility(0);
                }
            });
            return;
        }
        Disposable disposable2 = this.f10749b;
        if (disposable2 != null) {
            disposable2.dispose();
        }
        view.setVisibility(i2);
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void setPlayerVolume(int i2) {
        this.f10750c = i2;
        if (getIjkMediaPlayer() != null) {
            float f2 = i2 / 100.0f;
            getIjkMediaPlayer().setVolume(f2, f2);
        }
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void setSpeed(float f2) {
        this.ijkVideoView.setSpeed(f2);
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void setStopStreamIndicator(@NonNull View view) {
        this.stopStreamView = view;
    }

    public void setStopStreamViewVisibility(int i2) {
        View view = this.stopStreamView;
        if (view != null) {
            view.setVisibility(i2);
        }
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public void setVolume(int i2) {
        PLVControlUtils.setVolume(this.mApplicationContext, i2);
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void start() {
        PLVCommonLog.d(TAG, "start isFirstStart:" + this.isFirstStart);
        a(true, "start");
        if (this.isFirstStart) {
            start(false);
        } else if (start(true)) {
            this.isFirstStart = true;
        }
    }

    public void startBufferCountdown() {
        PLVCommonLog.d(TAG, "开始缓冲计时");
        this.handler.removeMessages(13);
        this.handler.sendEmptyMessageDelayed(13, 1000L);
    }

    public void startTimeoutCountdown() {
        PLVCommonLog.d(TAG, "开始超时计时");
        this.handler.removeMessages(12);
        this.handler.sendEmptyMessageDelayed(12, this.timeoutSecond * 1000);
    }

    public void stopBufferCountdown() {
        this.handler.removeMessages(13);
        this.bufferSecond = 0;
    }

    public void stopPlay() {
        release(false);
        PLVAudioFocusManager pLVAudioFocusManager = this.audioFocusManager;
        if (pLVAudioFocusManager != null) {
            pLVAudioFocusManager.abandonAudioFocus();
        }
        a(false, "stopPlay");
    }

    public void stopTimeoutCountdown() {
        this.handler.removeMessages(12);
        stopBufferCountdown();
    }

    public void toggleMediaControlsVisiblity() {
        IPLVMediaController iPLVMediaController = this.plvMediaController;
        if (iPLVMediaController != null) {
            if (iPLVMediaController.isShowing()) {
                this.plvMediaController.hide();
            } else {
                this.plvMediaController.show();
            }
        }
    }

    public PLVBaseVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private void b() {
        PLVCommonLog.d(TAG, "initGestureDetector");
        this.gestureDetector = new GestureDetector(this.mApplicationContext, new GestureDetector.SimpleOnGestureListener() { // from class: com.plv.business.api.common.player.PLVBaseVideoView.5
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent motionEvent) {
                PLVCommonLog.d(PLVBaseVideoView.TAG, "onDoubleTap");
                T t2 = PLVBaseVideoView.this.plvListener;
                if (t2 != null) {
                    t2.notifyGestureAction(7, false, false);
                }
                PLVBaseVideoView.this.eventType = -1;
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public void onLongPress(MotionEvent motionEvent) {
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
                int i2;
                int i3;
                int i4;
                int i5;
                PLVBaseVideoView pLVBaseVideoView = PLVBaseVideoView.this;
                if (pLVBaseVideoView.eventType == -1) {
                    if (pLVBaseVideoView.mApplicationContext != null && motionEvent.getRawY() <= PLVControlUtils.getStatusBarHeight(PLVBaseVideoView.this.mApplicationContext)) {
                        return false;
                    }
                    Context context = PLVBaseVideoView.this.mApplicationContext;
                    if (context != null && PLVControlUtils.hasVirtualNavigationBar(context) && PLVBaseVideoView.this.mApplicationContext.getResources().getConfiguration().orientation == 2 && (PLVBaseVideoView.this.mApplicationContext instanceof Activity) && motionEvent.getX() + PLVControlUtils.getNavigationBarHeight(PLVBaseVideoView.this.mApplicationContext) >= PLVControlUtils.getDisplayWH((Activity) PLVBaseVideoView.this.mApplicationContext)[0]) {
                        return false;
                    }
                }
                if (!PLVBaseVideoView.this.canMove()) {
                    return false;
                }
                PLVBaseVideoView pLVBaseVideoView2 = PLVBaseVideoView.this;
                if (pLVBaseVideoView2.lastX == 0.0f || pLVBaseVideoView2.lastY == 0.0f) {
                    pLVBaseVideoView2.lastX = motionEvent.getX();
                    PLVBaseVideoView.this.lastY = motionEvent.getY();
                }
                int measuredWidth = ((ViewGroup) PLVBaseVideoView.this.getParent()).getMeasuredWidth();
                int measuredHeight = ((ViewGroup) PLVBaseVideoView.this.getParent()).getMeasuredHeight();
                if (measuredWidth > 0 && measuredHeight > 0) {
                    int i6 = measuredWidth / 2;
                    float fAbs = Math.abs(PLVBaseVideoView.this.lastX - motionEvent2.getX());
                    double d3 = measuredWidth * 0.01d;
                    boolean z2 = ((double) Math.abs(PLVBaseVideoView.this.lastY - motionEvent2.getY())) > ((double) measuredHeight) * 0.05d;
                    double d4 = fAbs;
                    boolean z3 = d4 > d3;
                    int iMax = Math.max(1, (int) (d4 / d3));
                    double d5 = f3;
                    double dSqrt = d5 / Math.sqrt(Math.pow(f2, 2.0d) + Math.pow(d5, 2.0d));
                    if (Math.abs(dSqrt) > PLVBaseVideoView.RADIUS_SLOP && z2) {
                        PLVBaseVideoView pLVBaseVideoView3 = PLVBaseVideoView.this;
                        float f4 = pLVBaseVideoView3.lastX;
                        float f5 = i6;
                        if ((f4 <= f5 || !((i5 = pLVBaseVideoView3.eventType) == -1 || i5 == 2 || i5 == 3)) && (f4 > f5 || !((i4 = pLVBaseVideoView3.eventType) == 2 || i4 == 3))) {
                            if ((f4 <= f5 && ((i3 = pLVBaseVideoView3.eventType) == -1 || i3 == 0 || i3 == 1)) || (f4 > f5 && ((i2 = pLVBaseVideoView3.eventType) == 0 || i2 == 1))) {
                                if (pLVBaseVideoView3.lastY > motionEvent2.getY()) {
                                    PLVBaseVideoView pLVBaseVideoView4 = PLVBaseVideoView.this;
                                    pLVBaseVideoView4.eventType = 0;
                                    T t2 = pLVBaseVideoView4.plvListener;
                                    if (t2 != null) {
                                        t2.notifyGestureAction(0, true, false);
                                    }
                                } else {
                                    PLVBaseVideoView pLVBaseVideoView5 = PLVBaseVideoView.this;
                                    pLVBaseVideoView5.eventType = 1;
                                    T t3 = pLVBaseVideoView5.plvListener;
                                    if (t3 != null) {
                                        t3.notifyGestureAction(1, true, false);
                                    }
                                }
                            }
                        } else if (pLVBaseVideoView3.lastY > motionEvent2.getY()) {
                            PLVBaseVideoView pLVBaseVideoView6 = PLVBaseVideoView.this;
                            pLVBaseVideoView6.eventType = 2;
                            T t4 = pLVBaseVideoView6.plvListener;
                            if (t4 != null) {
                                t4.notifyGestureAction(2, true, false);
                            }
                        } else {
                            PLVBaseVideoView pLVBaseVideoView7 = PLVBaseVideoView.this;
                            pLVBaseVideoView7.eventType = 3;
                            T t5 = pLVBaseVideoView7.plvListener;
                            if (t5 != null) {
                                t5.notifyGestureAction(3, true, false);
                            }
                        }
                        PLVBaseVideoView.this.lastX = motionEvent2.getX();
                        PLVBaseVideoView.this.lastY = motionEvent2.getY();
                    } else if (Math.abs(dSqrt) <= PLVBaseVideoView.RADIUS_SLOP && z3) {
                        PLVBaseVideoView pLVBaseVideoView8 = PLVBaseVideoView.this;
                        int i7 = pLVBaseVideoView8.eventType;
                        if (i7 == -1 || i7 == 4 || i7 == 5) {
                            if (pLVBaseVideoView8.lastX > motionEvent2.getX()) {
                                PLVBaseVideoView pLVBaseVideoView9 = PLVBaseVideoView.this;
                                pLVBaseVideoView9.eventType = 4;
                                T t6 = pLVBaseVideoView9.plvListener;
                                if (t6 != null) {
                                    t6.notifyGestureAction(4, true, false, iMax);
                                }
                            } else {
                                PLVBaseVideoView pLVBaseVideoView10 = PLVBaseVideoView.this;
                                pLVBaseVideoView10.eventType = 5;
                                T t7 = pLVBaseVideoView10.plvListener;
                                if (t7 != null) {
                                    t7.notifyGestureAction(5, true, false, iMax);
                                }
                            }
                        }
                        PLVBaseVideoView.this.lastX = motionEvent2.getX();
                        PLVBaseVideoView.this.lastY = motionEvent2.getY();
                    }
                }
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public void onShowPress(MotionEvent motionEvent) {
                PLVCommonLog.d(PLVBaseVideoView.TAG, "onShowPress");
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                T t2 = PLVBaseVideoView.this.plvListener;
                if (t2 != null) {
                    t2.notifyGestureAction(6, false, false);
                }
                PLVAuxiliaryVideoview pLVAuxiliaryVideoview = PLVBaseVideoView.this.subVideoView;
                if (pLVAuxiliaryVideoview == null || !pLVAuxiliaryVideoview.isShow()) {
                    PLVBaseVideoView.this.toggleMediaControlsVisiblity();
                }
                PLVBaseVideoView.this.eventType = -1;
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                PLVAuxiliaryVideoview pLVAuxiliaryVideoview = PLVBaseVideoView.this.subVideoView;
                if (pLVAuxiliaryVideoview != null && pLVAuxiliaryVideoview.isShow()) {
                    PLVBaseVideoView.this.subVideoView.notifyClick(6, false, false);
                }
                return false;
            }
        });
    }

    private void c() {
        this.isBuffering = false;
        this.mCurrentBufferPercentage = 0;
        this.headers = null;
        this.reconnectCountdown = 0;
        this.isFirstStart = false;
    }

    @SuppressLint({"WrongConstant"})
    public void callOnDefineError(int i2, String str, int i3) {
        callOnError(PLVPlayError.toErrorObj(getCurrentPlayPath(), i2, str, i3));
        this.ijkVideoView.onErrorState();
    }

    @Override // com.plv.business.api.common.player.IPLVBaseVideoView
    public boolean isPlayState(boolean z2) {
        return z2 ? isInPlaybackState() && isPlaying() : isInPlaybackState() && isPlaying() && !this.isBuffering;
    }

    public PLVBaseVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.playMode = 1001;
        this.noStreamView = null;
        this.stopStreamView = null;
        this.isOpenScreenKeepOn = true;
        this.needGesture = true;
        this.eventType = -1;
        this.surfaceWidth = 0;
        this.surfaceHeight = 0;
        this.playPollingTimer = null;
        this.startLoaderTime = 0L;
        this.watchTimeDuration = 0;
        this.stayTimeDuration = 0;
        this.f10750c = 100;
        this.f10751d = true;
        this.f10752e = 0;
        this.f10753f = new Handler(Looper.getMainLooper()) { // from class: com.plv.business.api.common.player.PLVBaseVideoView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i3 = message.what;
                if (i3 == 12) {
                    PLVCommonLog.d(PLVBaseVideoView.TAG, "加载超时：" + PLVBaseVideoView.this.timeoutSecond);
                    PLVBaseVideoView pLVBaseVideoView = PLVBaseVideoView.this;
                    pLVBaseVideoView.onVideoLoadSlow(pLVBaseVideoView.timeoutSecond, false);
                    return;
                }
                if (i3 == 13) {
                    PLVBaseVideoView.this.bufferSecond++;
                    PLVCommonLog.d(PLVBaseVideoView.TAG, "缓冲计时：" + PLVBaseVideoView.this.bufferSecond + "*" + PLVBaseVideoView.this.timeoutSecond);
                    PLVBaseVideoView pLVBaseVideoView2 = PLVBaseVideoView.this;
                    int i4 = pLVBaseVideoView2.bufferSecond;
                    int i5 = pLVBaseVideoView2.timeoutSecond;
                    if (i4 == i5) {
                        pLVBaseVideoView2.onVideoLoadSlow(i5, true);
                    } else {
                        if (i4 > i5) {
                            return;
                        }
                        sendEmptyMessageDelayed(13, 1000L);
                    }
                }
            }
        };
        this.f10754g = new IPLVVideoViewNotifyer() { // from class: com.plv.business.api.common.player.PLVBaseVideoView.2
            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnBufferingUpdate(int i3) {
                PLVCommonLog.d(PLVBaseVideoView.TAG, "notifyOnBufferingUpdate");
                PLVBaseVideoView pLVBaseVideoView = PLVBaseVideoView.this;
                pLVBaseVideoView.mCurrentBufferPercentage = i3;
                pLVBaseVideoView.plvListener.notifyOnBufferingUpdate(i3);
            }

            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnCompletion() {
                PLVCommonLog.d(PLVBaseVideoView.TAG, "notifyOnCompletion");
                PLVBaseVideoView.this.a(false, "notifyOnCompletion");
                PLVBaseVideoView.this.stopTimeoutCountdown();
                PLVBaseVideoView.this.plvListener.notifyOnCompletion();
            }

            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public boolean notifyOnError(int i3, int i4) {
                PLVCommonLog.d(PLVBaseVideoView.TAG, "notifyOnError");
                PLVBaseVideoView.this.a(false, "notifyOnError");
                PLVBaseVideoView.this.plvListener.notifyOnError(i3, i4);
                PLVBaseVideoView.this.stopTimeoutCountdown();
                return false;
            }

            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public boolean notifyOnInfo(int i3, int i4) {
                PLVCommonLog.d(PLVBaseVideoView.TAG, "notifyOnInfo");
                PLVBaseVideoView.this.plvListener.notifyOnInfo(i3, i4);
                return false;
            }

            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnPrepared() {
                PLVCommonLog.d(PLVBaseVideoView.TAG, "notifyOnPrepared");
                PLVBaseVideoView.this.a(true, "notifyOnPrepared");
                if (PLVBaseVideoView.this.f10751d) {
                    PLVBaseVideoView pLVBaseVideoView = PLVBaseVideoView.this;
                    pLVBaseVideoView.setPlayerVolume(pLVBaseVideoView.f10750c);
                }
                PLVBaseVideoView.this.plvListener.notifyOnPrepared();
            }

            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnPreparing() {
                PLVCommonLog.d(PLVBaseVideoView.TAG, "notifyOnPreparing");
                PLVBaseVideoView.this.a(true, "notifyOnPreparing");
                PLVBaseVideoView.this.plvListener.notifyOnPreparing();
            }

            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnSEIRefresh(int i3, byte[] bArr) {
                PLVCommonLog.d(PLVBaseVideoView.TAG, "notifyOnSEIRefresh");
                PLVBaseVideoView.this.plvListener.notifyOnSEIRefresh(i3, bArr);
            }

            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnSeekComplete() {
                PLVCommonLog.d(PLVBaseVideoView.TAG, "notifyOnSeekComplete");
                PLVBaseVideoView.this.plvListener.notifyOnSeekComplete();
            }

            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public void notifyOnVideoSizeChanged(int i3, int i4, int i5, int i6) {
                PLVCommonLog.d(PLVBaseVideoView.TAG, "notifyOnVideoSizeChanged");
                PLVBaseVideoView.this.plvListener.notifyOnVideoSizeChanged(i3, i4, i5, i6);
            }

            @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
            public boolean notifyOnError(PLVPlayError pLVPlayError) {
                PLVCommonLog.d(PLVBaseVideoView.TAG, "notifyOnError");
                PLVBaseVideoView.this.a(false, "notifyOnError");
                PLVBaseVideoView.this.stopTimeoutCountdown();
                return PLVBaseVideoView.this.plvListener.notifyOnError(pLVPlayError);
            }
        };
        this.f10755h = true;
        this.f10756i = new BroadcastReceiver() { // from class: com.plv.business.api.common.player.PLVBaseVideoView.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (!TextUtils.isEmpty(action) && action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    if (PLVBaseVideoView.this.f10755h) {
                        PLVBaseVideoView.this.f10755h = false;
                        return;
                    }
                    if (PLVNetworkUtils.isConnected(context2)) {
                        T t2 = PLVBaseVideoView.this.plvListener;
                        if (t2 == null || !t2.notifyNetworkRecover()) {
                            PLVBaseVideoView.this.onNetWorkRecover();
                            return;
                        }
                        return;
                    }
                    T t3 = PLVBaseVideoView.this.plvListener;
                    if (t3 == null || !t3.notifyNetworkError()) {
                        PLVBaseVideoView.this.onNetWorkError();
                    }
                }
            }
        };
        initial();
        b();
    }

    public void pause(boolean z2) {
        PLVAudioFocusManager pLVAudioFocusManager;
        a(false, "pause(boolean isAbandonAudioFocus)");
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
        if (pLVAuxiliaryVideoview != null && pLVAuxiliaryVideoview.isShow()) {
            this.subVideoView.pause(z2);
            return;
        }
        IIjkVideoView iIjkVideoView = this.ijkVideoView;
        iIjkVideoView.setTargetState(iIjkVideoView.getStatePauseCode());
        if (isInPlaybackState()) {
            if (z2 && (pLVAudioFocusManager = this.audioFocusManager) != null) {
                pLVAudioFocusManager.abandonAudioFocus();
            }
            this.ijkVideoView.pause();
            this.plvListener.notifyOnVideoPause();
        }
    }

    private boolean a() {
        PLVCommonLog.d(TAG, "createIjkPlayer");
        IjkVideoView ijkVideoView = new IjkVideoView(getContext());
        ijkVideoView.setId(IJK_VIDEO_ID);
        this.ijkVideoView = new PLVijkVideoViewSubject(ijkVideoView);
        this.plvListener = (T) createListener();
        IPLVVideoViewNotifyer iPLVVideoViewNotifyerCreateNotifyer = createNotifyer(this.f10754g);
        this.plvVideoViewNotifyer = iPLVVideoViewNotifyerCreateNotifyer;
        if (this.plvListener == null) {
            return false;
        }
        if (iPLVVideoViewNotifyerCreateNotifyer == null) {
            this.plvVideoViewNotifyer = this.f10754g;
        }
        this.plvMediaPlayerListenerHolder = new PLVMediaPlayerListenerHolder(this.plvVideoViewNotifyer);
        addView(ijkVideoView);
        this.ijkVideoView.setRender(2);
        IjkMediaPlayer.loadLibrariesOnce(new IjkLibLoader() { // from class: com.plv.business.api.common.player.PLVBaseVideoView.3
            @Override // com.easefun.polyv.mediasdk.player.IjkLibLoader
            public void loadLibrary(String str) throws SecurityException, UnsatisfiedLinkError {
                try {
                    System.loadLibrary(str);
                } catch (UnsatisfiedLinkError e2) {
                    Log.e(PLVBaseVideoView.TAG, e2.getMessage(), e2);
                }
            }
        });
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        this.ijkVideoView.setIjkLogLevel(4);
        this.ijkVideoView.setLogTag(TAG);
        return true;
    }

    public boolean start(boolean z2) {
        a(true, "start(boolean isFirst)");
        PLVAuxiliaryVideoview pLVAuxiliaryVideoview = this.subVideoView;
        if (pLVAuxiliaryVideoview != null && pLVAuxiliaryVideoview.isShow()) {
            this.subVideoView.start();
            return false;
        }
        IIjkVideoView iIjkVideoView = this.ijkVideoView;
        iIjkVideoView.setTargetState(iIjkVideoView.getStatePlayingCode());
        if (!isInPlaybackState()) {
            return false;
        }
        PLVAudioFocusManager pLVAudioFocusManager = this.audioFocusManager;
        if (pLVAudioFocusManager != null) {
            pLVAudioFocusManager.requestAudioFocus();
        }
        this.ijkVideoView.start();
        this.plvListener.notifyOnVideoPlay(z2);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z2, String str) {
        if (this.isOpenScreenKeepOn && getKeepScreenOn() != z2) {
            setKeepScreenOn(z2);
        }
    }
}
