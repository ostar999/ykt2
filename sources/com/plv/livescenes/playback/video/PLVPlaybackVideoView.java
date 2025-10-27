package com.plv.livescenes.playback.video;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.easefun.polyv.businesssdk.api.common.player.PolyvPlayType;
import com.easefun.polyv.businesssdk.api.common.player.microplayer.PolyvCommonVideoView;
import com.easefun.polyv.businesssdk.vodplayer.PolyvVodSDKClient;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.easefun.polyv.livescenes.playback.video.PolyvPlaybackVideoViewListener;
import com.easefun.polyv.livescenes.playback.video.api.IPolyvPlaybackVideoView;
import com.easefun.polyv.livescenes.playback.video.api.IPolyvPlaybackVideoViewPlayBinder;
import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.easefun.polyv.mediasdk.player.IjkMediaPlayer;
import com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent;
import com.plv.business.api.common.player.PLVBaseVideoView;
import com.plv.business.api.common.player.PLVErrorMessageUtils;
import com.plv.business.api.common.player.PLVPlayError;
import com.plv.business.api.common.player.PLVPlayerOptionParamVO;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent;
import com.plv.business.api.common.player.microplayer.PLVCommonVideoView;
import com.plv.business.api.common.ppt.IPLVPPTView;
import com.plv.business.api.common.ppt.vo.PLVPPTLocalCacheVO;
import com.plv.business.model.video.PLVBaseVideoParams;
import com.plv.business.model.video.PLVLiveChannelVO;
import com.plv.business.model.video.PLVLiveMarqueeVO;
import com.plv.business.model.video.PLVLogVideoLableVO;
import com.plv.business.model.video.PLVPlaybackVideoParams;
import com.plv.business.model.video.PLVWatermarkVO;
import com.plv.business.net.PLVCommonApiManager;
import com.plv.foundationsdk.config.PLVPlayOption;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.logcode.play.PLVErrorCodePlayVideoInfo;
import com.plv.foundationsdk.log.elog.logcode.play.PLVErrorCodePlayVideoPlay;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.PLVrResponseCallback;
import com.plv.foundationsdk.rx.PLVRxBaseTransformer;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.foundationsdk.utils.PLVUtils;
import com.plv.livescenes.access.PLVChannelFeature;
import com.plv.livescenes.access.PLVChannelFeatureManager;
import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.livescenes.log.PLVELogSender;
import com.plv.livescenes.log.PLVLiveViewLog;
import com.plv.livescenes.model.PLVPlaybackVO;
import com.plv.livescenes.model.PLVPlaybackVO2;
import com.plv.livescenes.net.PLVApiManager;
import com.plv.livescenes.playback.log.PLVPlaybackVodQOSAnalytics;
import com.plv.livescenes.playback.log.PLVPlaybackVodViewLog;
import com.plv.livescenes.playback.ppt.PLVPlaybackPPTPlayWrapper;
import com.plv.livescenes.playback.video.api.IPLVPlaybackListenerEvent;
import com.plv.livescenes.playback.vo.PLVPlaybackDataVO;
import com.plv.livescenes.playback.vo.PLVPlaybackLocalCacheVO;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/* loaded from: classes5.dex */
public class PLVPlaybackVideoView extends PolyvCommonVideoView<PLVPlaybackVO.DataBean, PolyvPlaybackVideoViewListener> implements IPolyvPlaybackVideoView, IPolyvPlaybackVideoViewPlayBinder {
    public static final String APP_ID = "appId";
    public static final String CHANNEL_ID = "channelId";
    private static final int DEFAULT_RETRY_COUNT = 3;
    public static final String LIST_TYPE = "listType";
    public static final String TIMESTAMP = "timestamp";
    public static final String VID = "vid";

    /* renamed from: a, reason: collision with root package name */
    private Disposable f10822a;

    /* renamed from: b, reason: collision with root package name */
    private Disposable f10823b;

    /* renamed from: c, reason: collision with root package name */
    private Disposable f10824c;
    private String channelId;

    /* renamed from: d, reason: collision with root package name */
    private Disposable f10825d;

    /* renamed from: e, reason: collision with root package name */
    private Disposable f10826e;

    /* renamed from: f, reason: collision with root package name */
    private PLVPlaybackPPTPlayWrapper f10827f;

    /* renamed from: g, reason: collision with root package name */
    private PLVLiveChannelVO f10828g;

    /* renamed from: h, reason: collision with root package name */
    private PLVPlaybackVO.DataBean f10829h;

    /* renamed from: i, reason: collision with root package name */
    private PLVPlaybackDataVO f10830i;
    private boolean isOnlyAudio;

    /* renamed from: j, reason: collision with root package name */
    private Boolean f10831j;

    /* renamed from: k, reason: collision with root package name */
    private boolean f10832k;

    /* renamed from: l, reason: collision with root package name */
    private int f10833l;

    /* renamed from: m, reason: collision with root package name */
    private boolean f10834m;

    /* renamed from: n, reason: collision with root package name */
    private boolean f10835n;

    /* renamed from: o, reason: collision with root package name */
    private PolyvPlayType f10836o;

    /* renamed from: p, reason: collision with root package name */
    private long f10837p;

    /* renamed from: q, reason: collision with root package name */
    private boolean f10838q;

    /* renamed from: r, reason: collision with root package name */
    private boolean f10839r;

    /* renamed from: s, reason: collision with root package name */
    private int f10840s;

    /* renamed from: t, reason: collision with root package name */
    private PLVPlaybackListType f10841t;

    /* renamed from: u, reason: collision with root package name */
    private final List<PLVPlaybackLocalCacheVO> f10842u;
    private String userId;

    /* renamed from: v, reason: collision with root package name */
    private boolean f10843v;
    private String videoId;

    /* renamed from: w, reason: collision with root package name */
    private int f10844w;

    public PLVPlaybackVideoView(@NonNull Context context) {
        super(context);
        this.f10832k = true;
        this.f10833l = 3;
        this.f10834m = false;
        this.f10835n = false;
        this.f10836o = PolyvPlayType.IDLE;
        this.f10837p = 0L;
        this.f10838q = false;
        this.f10839r = false;
        this.f10840s = 0;
        this.f10841t = PLVPlaybackListType.PLAYBACK;
        this.f10842u = new ArrayList();
        this.f10843v = true;
        this.isOnlyAudio = false;
    }

    @Nullable
    private PLVPlaybackLocalCacheVO getLocalCacheVideo() {
        String videoPoolId;
        if (!this.f10842u.isEmpty() && (videoPoolId = getVideoPoolId()) != null && !TextUtils.isEmpty(videoPoolId)) {
            for (PLVPlaybackLocalCacheVO pLVPlaybackLocalCacheVO : this.f10842u) {
                if (videoPoolId.equals(pLVPlaybackLocalCacheVO.getVideoPoolId())) {
                    return pLVPlaybackLocalCacheVO;
                }
            }
        }
        return null;
    }

    private String getVideoPoolId() {
        String strSubstring;
        if (this.videoId.contains(StrPool.UNDERLINE)) {
            String str = this.videoId;
            strSubstring = str.substring(0, str.indexOf(StrPool.UNDERLINE));
        } else {
            strSubstring = this.videoId;
        }
        if (!TextUtils.isEmpty(strSubstring)) {
            return strSubstring;
        }
        PLVPlaybackDataVO pLVPlaybackDataVO = this.f10830i;
        if (pLVPlaybackDataVO == null) {
            return null;
        }
        String videoPoolId = pLVPlaybackDataVO.getVideoPoolId();
        if (!TextUtils.isEmpty(videoPoolId)) {
            return videoPoolId;
        }
        String fileId = this.f10830i.getFileId();
        if (TextUtils.isEmpty(fileId)) {
            return null;
        }
        return fileId;
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public void bindPPTView(IPLVPPTView iPLVPPTView) {
        PLVPlaybackPPTPlayWrapper pLVPlaybackPPTPlayWrapper = this.f10827f;
        if (pLVPlaybackPPTPlayWrapper != null) {
            pLVPlaybackPPTPlayWrapper.bindPPTView(iPLVPPTView);
        }
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void callOnError(PLVPlayError pLVPlayError) {
        stopTimeoutCountdown();
        setPlayerBufferingViewVisibility(8);
        if (pLVPlayError.errorCode == -10000) {
            pLVPlayError.errorDescribe = PLVErrorMessageUtils.getPlayErrorMessage(20003);
        }
        ((PolyvPlaybackVideoViewListener) this.plvListener).notifyOnError(pLVPlayError);
        n();
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public boolean canMove() {
        return true;
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public boolean changeBitRate(int i2) {
        return false;
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public boolean changeLines(int i2) {
        return false;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public Handler createHandler() {
        return null;
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public Uri createPlayUri() {
        PLVPlaybackLocalCacheVO localCacheVideo = getLocalCacheVideo();
        if (localCacheVideo != null) {
            this.f10836o = PolyvPlayType.LOCAL_PLAY;
            return Uri.parse(localCacheVideo.getVideoPath());
        }
        String fileUrl = this.f10830i.getFileUrl();
        if (this.f10832k && a(fileUrl) && !fileUrl.contains("ijkhttphook:")) {
            fileUrl = "ijkhttphook:" + fileUrl;
        }
        return Uri.parse(addPlayUriParams(fileUrl));
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView, com.plv.business.api.common.player.PLVBaseVideoView, com.plv.business.api.common.player.IPLVBaseVideoView
    public void destroy() {
        super.destroy();
        d();
        a();
        PLVPlaybackPPTPlayWrapper pLVPlaybackPPTPlayWrapper = this.f10827f;
        if (pLVPlaybackPPTPlayWrapper != null) {
            pLVPlaybackPPTPlayWrapper.destory();
            this.f10827f = null;
        }
        this.f10832k = false;
    }

    @Override // com.plv.livescenes.playback.video.api.IPLVPlaybackVideoView
    public void enableRetry(boolean z2) {
        this.f10832k = z2;
    }

    @Override // com.plv.livescenes.playback.video.api.IPLVPlaybackVideoView
    public PolyvPlayType getPlayType() {
        return this.f10836o;
    }

    @Override // com.plv.livescenes.playback.video.api.IPLVPlaybackVideoView
    public PLVPlaybackDataVO getPlaybackData() {
        return this.f10830i;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public String getSDKVersion() {
        return PolyvLiveSDKClient.getInstance().getPolyvLiveAndroidSdk();
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public ArrayList<PLVPlayerOptionParamVO> initOptionParameters() {
        ArrayList<PLVPlayerOptionParamVO> arrayListInitOptionParameters = super.initOptionParameters();
        arrayListInitOptionParameters.add(new PLVPlayerOptionParamVO(1, "reconnect", 1));
        return arrayListInitOptionParameters;
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView, com.plv.business.api.common.player.PLVBaseVideoView
    public void initial() {
        super.initial();
        this.f10827f = new PLVPlaybackPPTPlayWrapper(this);
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public boolean isOnlyAudio() {
        return this.isOnlyAudio;
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public boolean isValidatePlayId() {
        PLVPlaybackDataVO pLVPlaybackDataVO = this.f10830i;
        if (pLVPlaybackDataVO != null && pLVPlaybackDataVO.isValid()) {
            return true;
        }
        ((PolyvPlaybackVideoViewListener) this.plvListener).notifyOnError(PLVPlayError.toErrorObj(getCurrentPlayPath(), PLVELogSender.sendPlaybackLog(PLVErrorCodePlayVideoPlay.class, 10, new Exception("视频id无效, vid is " + this.videoId)), PLVErrorCodePlayVideoPlay.getMessage(10), 1001));
        setNoStreamViewVisibility(0);
        return false;
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView, com.plv.business.api.common.player.PLVBaseVideoView
    public void onNetWorkError() {
        super.onNetWorkError();
        PLVCommonLog.d("PLVBaseVideoView", "onNetWorkError");
        this.f10834m = true;
        if (isInPlaybackState()) {
            this.f10840s = getCurrentPosition();
        }
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView, com.plv.business.api.common.player.PLVBaseVideoView
    public void onNetWorkRecover() {
        PLVCommonLog.d("PLVBaseVideoView", "onNetWorkRecover");
        this.f10834m = false;
        ((PolyvPlaybackVideoViewListener) this.plvListener).notifyVideoViewRestart(true);
        d();
        h();
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public boolean onPlayCompelete() {
        IMediaPlayer mediaPlayer = getMediaPlayer();
        if (mediaPlayer == null) {
            return true;
        }
        PLVCommonLog.e("PLVBaseVideoView", "pos:" + mediaPlayer.getCurrentPosition() + "   dur :" + mediaPlayer.getDuration());
        return true;
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public boolean onPlayError(int i2, int i3) {
        if (this.destroyFlag) {
            return false;
        }
        a(i2);
        PLVPlaybackVodQOSAnalytics pLVPlaybackVodQOSAnalytics = PLVPlaybackVodQOSAnalytics.getInstance();
        String str = this.playId;
        String str2 = this.videoId;
        PLVPlaybackDataVO pLVPlaybackDataVO = this.f10830i;
        pLVPlaybackVodQOSAnalytics.error(str, str2, "video_type_on_error_listener", "", pLVPlaybackDataVO == null ? "" : pLVPlaybackDataVO.getChannelSessionId(), "", "", PLVPlaybackVodQOSAnalytics.getInstance().getQOSAnalyticsParam(), String.format(Locale.getDefault(), "%s implErr[%d] frameworkErr[%d]", getCurrentPlayPath(), Integer.valueOf(i3), Integer.valueOf(i2)), getCurrentPlayPath());
        c();
        setPlayerBufferingViewVisibility(8);
        setNoStreamViewVisibility(0);
        return true;
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public boolean onPlayInfo(int i2, int i3) {
        if (i2 == 701) {
            this.f10837p = System.currentTimeMillis();
        } else if (i2 == 702 && this.f10836o == PolyvPlayType.ONLINE_PLAY && !this.f10838q) {
            this.f10838q = true;
            int iCurrentTimeMillis = (int) (System.currentTimeMillis() - this.f10837p);
            PLVPlaybackVodQOSAnalytics pLVPlaybackVodQOSAnalytics = PLVPlaybackVodQOSAnalytics.getInstance();
            String str = this.playId;
            String str2 = this.videoId;
            PLVPlaybackDataVO pLVPlaybackDataVO = this.f10830i;
            pLVPlaybackVodQOSAnalytics.buffer(str, str2, iCurrentTimeMillis, "", pLVPlaybackDataVO == null ? "" : pLVPlaybackDataVO.getChannelSessionId(), PLVPlaybackVodQOSAnalytics.getInstance().getQOSAnalyticsParam());
        }
        return true;
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public boolean onPlayPrepared() {
        IjkMediaPlayer ijkMediaPlayer;
        if (this.f10836o == PolyvPlayType.ONLINE_PLAY && !this.f10839r) {
            this.f10839r = true;
            int iCurrentTimeMillis = (int) (System.currentTimeMillis() - this.startLoaderTime);
            PLVPlaybackVodQOSAnalytics pLVPlaybackVodQOSAnalytics = PLVPlaybackVodQOSAnalytics.getInstance();
            String str = this.playId;
            String str2 = this.videoId;
            PLVPlaybackDataVO pLVPlaybackDataVO = this.f10830i;
            pLVPlaybackVodQOSAnalytics.loading(str, str2, iCurrentTimeMillis, "", pLVPlaybackDataVO == null ? "" : pLVPlaybackDataVO.getChannelSessionId(), PLVPlaybackVodQOSAnalytics.getInstance().getQOSAnalyticsParam());
        }
        setPlayerBufferingViewVisibility(8);
        setNoStreamViewVisibility(4);
        int i2 = this.f10840s;
        if (i2 > 0) {
            if (i2 < 100) {
                seekTo((getDuration() * this.f10840s) / 100);
            } else {
                seekTo(i2);
            }
            this.f10840s = 0;
        }
        if (this.ijkVideoView.getTargetState() != this.ijkVideoView.getStatePauseCode()) {
            start();
        }
        this.f10835n = false;
        if (this.f10832k && a(this.playUri) && (ijkMediaPlayer = this.ijkVideoView.getIjkMediaPlayer()) != null) {
            ijkMediaPlayer.setOnNativeInvokeListener(new IjkMediaPlayer.OnNativeInvokeListener() { // from class: com.plv.livescenes.playback.video.PLVPlaybackVideoView.1
                @Override // com.easefun.polyv.mediasdk.player.IjkMediaPlayer.OnNativeInvokeListener
                public boolean onNativeInvoke(int i3, Bundle bundle) {
                    if (i3 == 131075) {
                        int i4 = bundle.getInt(IjkMediaPlayer.OnNativeInvokeListener.ARG_RETRY_COUNTER);
                        PLVCommonLog.d("IJKMEDIA", "重连次数=" + i4);
                        if (PLVPlaybackVideoView.this.f10835n) {
                            return false;
                        }
                        if (PLVPlaybackVideoView.this.getCurrentPosition() > 0) {
                            PLVPlaybackVideoView pLVPlaybackVideoView = PLVPlaybackVideoView.this;
                            pLVPlaybackVideoView.f10840s = pLVPlaybackVideoView.getCurrentPosition();
                            PLVCommonLog.d("PLVBaseVideoView", "onNativeInvoke record seek position " + PLVPlaybackVideoView.this.f10840s);
                        }
                        if (i4 < PLVPlaybackVideoView.this.f10833l) {
                            return true;
                        }
                        PLVPlaybackVideoView.this.post(new Runnable() { // from class: com.plv.livescenes.playback.video.PLVPlaybackVideoView.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                PLVCommonLog.d("IJKMEDIA", "重连达到次数上限，回调重试失败");
                                PLVPlaybackVideoView.this.stopPlay();
                                if (((PolyvPlaybackVideoViewListener) ((PLVBaseVideoView) PLVPlaybackVideoView.this).plvListener).notifyRetryFailed()) {
                                    return;
                                }
                                if (PLVPlaybackVideoView.this.f10834m) {
                                    ((PolyvPlaybackVideoViewListener) ((PLVBaseVideoView) PLVPlaybackVideoView.this).plvListener).notifyOnError(new PLVPlayError(PLVPlaybackVideoView.this.getCurrentPlayPath(), 20003, PLVErrorMessageUtils.getPlayErrorMessage(20003), ((PLVBaseVideoView) PLVPlaybackVideoView.this).playMode));
                                } else {
                                    PLVCommonLog.d("IJKMEDIA", "重连达到次数上限，处理播放器内部逻辑，重新加载视频");
                                    PLVPlaybackVideoView.this.g();
                                }
                            }
                        });
                        PLVPlaybackVideoView.this.f10835n = true;
                    }
                    return false;
                }
            });
        }
        b();
        return true;
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public boolean onPlaySeek() {
        IMediaPlayer mediaPlayer = getMediaPlayer();
        if (mediaPlayer == null) {
            return false;
        }
        Log.i("PLVBaseVideoView", mediaPlayer.getCurrentPosition() + "/" + mediaPlayer.getDuration());
        return false;
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public void onVideoLoadSlow(int i2, boolean z2) {
        super.onVideoLoadSlow(i2, z2);
        T t2 = this.plvListener;
        if (t2 != 0) {
            ((PolyvPlaybackVideoViewListener) t2).notifyLoadSlow(i2, z2);
        }
        PLVELogSender.sendPlaybackLog(PLVErrorCodePlayVideoPlay.class, 2, new Exception((z2 ? "视频缓冲超时" : "视频加载超时") + ", loadedTime is " + i2));
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView, android.widget.MediaController.MediaPlayerControl
    public void pause() {
        super.pause();
        PLVPlaybackPPTPlayWrapper pLVPlaybackPPTPlayWrapper = this.f10827f;
        if (pLVPlaybackPPTPlayWrapper != null) {
            pLVPlaybackPPTPlayWrapper.pause();
        }
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView, com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public void playByMode(@NonNull PLVBaseVideoParams pLVBaseVideoParams, int i2) {
        this.f10840s = 0;
        super.playByMode(pLVBaseVideoParams, i2);
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public void reconnect() {
        n();
        super.reconnect();
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public void requestModleVO(PLVBaseVideoParams pLVBaseVideoParams, int i2) {
        d();
        release(true);
        a();
        a(pLVBaseVideoParams);
        h();
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView, android.widget.MediaController.MediaPlayerControl
    public void seekTo(int i2) {
        super.seekTo(i2);
        PLVPlaybackPPTPlayWrapper pLVPlaybackPPTPlayWrapper = this.f10827f;
        if (pLVPlaybackPPTPlayWrapper != null) {
            pLVPlaybackPPTPlayWrapper.seekTo(i2);
        }
    }

    @Override // com.plv.livescenes.playback.video.api.IPLVPlaybackVideoView
    public void setMaxRetryCount(int i2) {
        this.f10833l = i2;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnDanmuServerOpenListener(IPLVVideoViewListenerEvent.OnDanmuServerOpenListener onDanmuServerOpenListener) {
        ((PolyvPlaybackVideoViewListener) this.plvListener).setOnDanmuServerOpenListener(onDanmuServerOpenListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGetMarqueeVoListener(IPLVVideoViewListenerEvent.OnGetMarqueeVoListener onGetMarqueeVoListener) {
        ((PolyvPlaybackVideoViewListener) this.plvListener).setOnGetMarqueeVoListener(onGetMarqueeVoListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGetWatermarkVOListener(IPLVVideoViewListenerEvent.OnGetWatermarkVoListener onGetWatermarkVoListener) {
        ((PolyvPlaybackVideoViewListener) this.plvListener).setOnGetWatermarkVOListener(onGetWatermarkVoListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnPPTShowListener(IPLVVideoViewListenerEvent.OnPPTShowListener onPPTShowListener) {
        ((PolyvPlaybackVideoViewListener) this.plvListener).setOnPPTShowListener(onPPTShowListener);
    }

    @Override // com.plv.livescenes.playback.video.api.IPLVPlaybackVideoViewPlayBinder
    public void setOnPlaybackDataReadyListener(IPLVPlaybackListenerEvent.OnPlaybackDataReadyListener onPlaybackDataReadyListener) {
        ((PolyvPlaybackVideoViewListener) this.plvListener).setOnPlaybackDataReadyListener(onPlaybackDataReadyListener);
    }

    @Override // com.plv.livescenes.playback.video.api.IPLVPlaybackVideoViewPlayBinder
    public void setOnRetryListener(IPLVPlaybackListenerEvent.OnRetryListener onRetryListener) {
        ((PolyvPlaybackVideoViewListener) this.plvListener).setOnRetryListener(onRetryListener);
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnVideoViewRestartListener(IPLVVideoViewListenerEvent.OnVideoViewRestart onVideoViewRestart) {
        ((PolyvPlaybackVideoViewListener) this.plvListener).setOnVideoViewRestartListener(onVideoViewRestart);
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView, com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public void setViewerId(String str) {
        super.setViewerId(str);
        PolyvLiveSDKClient.getInstance().setViewerId(str);
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView, android.widget.MediaController.MediaPlayerControl
    public void start() {
        super.start();
        PLVPlaybackPPTPlayWrapper pLVPlaybackPPTPlayWrapper = this.f10827f;
        if (pLVPlaybackPPTPlayWrapper != null) {
            pLVPlaybackPPTPlayWrapper.restart();
        }
    }

    @Override // com.plv.business.api.common.player.microplayer.PLVCommonVideoView
    public void staticsVideoViewPlay() {
        if (this.f10836o == PolyvPlayType.ONLINE_PLAY) {
            if (this.f10841t == PLVPlaybackListType.VOD) {
                PLVPlaybackVodViewLog.getInstance().statVodPlay(this.playId, this.videoId, getIjkMediaPlayer() != null ? getIjkMediaPlayer().getTrafficStatisticByteCount() : 0L, this.watchTimeDuration, this.stayTimeDuration, getCurrentPosition() / 1000, getDuration() / 1000, 1L, getViewerId(), "", this.viewLogParam2, "", this.viewLogParam4, this.viewLogParam5, getCurrentPlayPath());
                return;
            }
            PLVLiveViewLog pLVLiveViewLog = PLVLiveViewLog.getInstance();
            String str = this.playId;
            String str2 = this.userId;
            String str3 = this.channelId;
            int i2 = this.watchTimeDuration;
            int i3 = this.stayTimeDuration;
            PLVPlaybackDataVO pLVPlaybackDataVO = this.f10830i;
            pLVLiveViewLog.statLive(str, str2, str3, 0L, i2, i3, pLVPlaybackDataVO == null ? "" : pLVPlaybackDataVO.getChannelSessionId(), "0", getViewerId(), this.viewLogParam2, "vod", this.viewLogParam4, this.viewLogParam5, this.f10841t.getStatisticValuePb());
        }
    }

    private boolean f() {
        boolean z2 = !TextUtils.isEmpty(this.f10828g.getAdvertFlvUrl());
        boolean z3 = !TextUtils.isEmpty(this.f10828g.getAdvertImage());
        if (!this.isAllowOpenAdHead || this.hasOpenAdHead || this.subVideoView == null || !(z2 || z3)) {
            return false;
        }
        String advertFlvUrl = z2 ? this.f10828g.getAdvertFlvUrl() : null;
        int advertDuration = this.f10828g.getAdvertDuration();
        if (z3) {
            this.subVideoView.showWaittingImage(this.f10828g.getAdvertImage(), true, this.f10828g.getAdvertHref());
        } else {
            this.subVideoView.showWaittingImage("", false, this.f10828g.getAdvertHref());
        }
        this.subVideoView.addAudioFocusManager(this.audioFocusManager);
        this.options.put(PLVPlayOption.KEY_HEADAD, new PLVPlayOption.HeadAdOption(advertFlvUrl, advertDuration));
        this.subVideoView.initOption(this.options);
        this.subVideoView.startHeadAd();
        this.subVideoView.setOnAuxiliaryPlayEndListener(new IPLVAuxiliaryVideoViewListenerEvent.IPLVOnAuxiliaryPlayEndListener() { // from class: com.plv.livescenes.playback.video.PLVPlaybackVideoView.3
            @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent.IPLVOnAuxiliaryPlayEndListener
            public void onAfterEnd() {
                PLVCommonLog.d("PLVBaseVideoView", "onAfterEnd");
            }

            @Override // com.plv.business.api.auxiliary.IPLVAuxiliaryVideoViewListenerEvent.IPLVOnAuxiliaryPlayEndListener
            public void onBeforeEnd(boolean z4) {
                if (((PLVCommonVideoView) PLVPlaybackVideoView.this).isNetWorkError) {
                    return;
                }
                ((PLVCommonVideoView) PLVPlaybackVideoView.this).hasOpenAdHead = true;
                PLVPlaybackVideoView.this.clear();
                PLVPlaybackVideoView.this.g();
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        boolean zIsEmpty = true;
        if (TextUtils.isEmpty(this.f10830i.getLiveType())) {
            zIsEmpty = true ^ TextUtils.isEmpty(this.f10830i.getChannelSessionId());
        } else if (!"ppt".equals(this.f10830i.getLiveType())) {
            zIsEmpty = false;
        }
        ((PolyvPlaybackVideoViewListener) this.plvListener).notifyPPTShow(zIsEmpty ? 0 : 4);
        this.startLoaderTime = System.currentTimeMillis();
        setPlayerBufferingViewVisibility(0);
        String string = createPlayUri().toString();
        this.playUri = string;
        setVideoURIFromSelf(string);
    }

    private void h() {
        PLVPlaybackLocalCacheVO localCacheVideo = getLocalCacheVideo();
        if (localCacheVideo != null) {
            a(localCacheVideo);
        } else if (!TextUtils.isEmpty(this.videoId)) {
            i();
        } else if (this.f10843v) {
            j();
        }
    }

    private void i() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        String str = this.f10841t == PLVPlaybackListType.VOD ? "vod" : "playback";
        HashMap map = new HashMap();
        map.put("channelId", this.channelId);
        map.put("timestamp", jCurrentTimeMillis + "");
        map.put("appId", appId);
        map.put(LIST_TYPE, str);
        map.put("vid", this.videoId);
        map.put("ignoreScene", "Y");
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, map);
        this.f10822a = PLVApiManager.getPlvLiveStatusApi().getPlaybackVO(appId, this.channelId, this.videoId, jCurrentTimeMillis, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), str, strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2], "Y").map(new PLVRxEncryptDataFunction<PLVPlaybackVO>(PLVPlaybackVO.DataBean.class) { // from class: com.plv.livescenes.playback.video.PLVPlaybackVideoView.6
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVPlaybackVO pLVPlaybackVO) {
                return new Pair<>(pLVPlaybackVO.getDataObj(), Boolean.valueOf(pLVPlaybackVO.isEncryption()));
            }
        }).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<PLVPlaybackVO>() { // from class: com.plv.livescenes.playback.video.PLVPlaybackVideoView.4
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVPlaybackVO pLVPlaybackVO) throws Exception {
                if (pLVPlaybackVO.getCode() != 200) {
                    PLVPlaybackVideoView.this.j();
                    return;
                }
                PLVPlaybackVideoView.this.f10829h = pLVPlaybackVO.getData();
                if (PLVPlaybackVideoView.this.f10829h == null) {
                    PLVPlaybackVideoView.this.j();
                    return;
                }
                if (TextUtils.isEmpty(PLVPlaybackVideoView.this.f10829h.getFileUrl())) {
                    PLVPlaybackVideoView.this.j();
                    return;
                }
                PLVPlaybackVideoView pLVPlaybackVideoView = PLVPlaybackVideoView.this;
                pLVPlaybackVideoView.f10830i = new PLVPlaybackDataVO(pLVPlaybackVideoView.f10841t, PLVPlaybackVideoView.this.f10829h);
                ((PolyvPlaybackVideoViewListener) ((PLVBaseVideoView) PLVPlaybackVideoView.this).plvListener).notifyOnPlaybackDataReady(PLVPlaybackVideoView.this.f10830i);
                PLVPlaybackVideoView.this.k();
                PLVPlaybackVideoView.this.l();
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.playback.video.PLVPlaybackVideoView.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                if (th != null) {
                    PLVCommonLog.e("PLVBaseVideoView", "getPlaybackVO: " + th.getMessage());
                }
                ((PolyvPlaybackVideoViewListener) ((PLVBaseVideoView) PLVPlaybackVideoView.this).plvListener).notifyOnError(PLVPlayError.toErrorObj(PLVPlaybackVideoView.this.getCurrentPlayPath(), PLVELogSender.sendPlaybackLog(PLVErrorCodePlayVideoInfo.class, 11, th), PLVPlaybackVideoView.this.getErrorMessage(th), 1001));
                PLVPlaybackVideoView.this.setNoStreamViewVisibility(0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        String str = this.f10841t == PLVPlaybackListType.VOD ? "vod" : "playback";
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, PLVSugarUtil.mapOf(PLVSugarUtil.pair("appId", appId), PLVSugarUtil.pair("channelId", this.channelId), PLVSugarUtil.pair("timestamp", String.valueOf(jCurrentTimeMillis)), PLVSugarUtil.pair("vid", this.videoId), PLVSugarUtil.pair("playbackType", str)));
        String signatureMethod = PLVSignCreator.getSignatureMethod();
        Disposable disposable = this.f10826e;
        if (disposable != null) {
            disposable.dispose();
        }
        this.f10826e = PLVApiManager.getPlvLiveStatusApi().getPlaybackData(appId, this.channelId, jCurrentTimeMillis, this.videoId, str, strArrCreateSignWithSignatureNonceEncrypt[0], signatureMethod, strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVPlaybackVO2>(PLVPlaybackVO2.Data.class) { // from class: com.plv.livescenes.playback.video.PLVPlaybackVideoView.10
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVPlaybackVO2 pLVPlaybackVO2) {
                return new Pair<>(pLVPlaybackVO2.getDataObj(), Boolean.valueOf(pLVPlaybackVO2.isEncryption()));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).filter(new Predicate<PLVPlaybackVO2>() { // from class: com.plv.livescenes.playback.video.PLVPlaybackVideoView.9
            @Override // io.reactivex.functions.Predicate
            public boolean test(@NonNull PLVPlaybackVO2 pLVPlaybackVO2) throws Exception {
                if (pLVPlaybackVO2.getCode().intValue() != 200) {
                    ((PolyvPlaybackVideoViewListener) ((PLVBaseVideoView) PLVPlaybackVideoView.this).plvListener).notifyOnError(PLVPlayError.toErrorObj(PLVPlaybackVideoView.this.getCurrentPlayPath(), PLVELogSender.sendPlaybackLog(PLVErrorCodePlayVideoInfo.class, 8, new Exception("回放信息加载失败, code is " + pLVPlaybackVO2.getCode())), pLVPlaybackVO2.getMessage(), 1001));
                    PLVPlaybackVideoView.this.setNoStreamViewVisibility(0);
                    return false;
                }
                if (pLVPlaybackVO2.getData() != null) {
                    return "Y".equals(pLVPlaybackVO2.getData().getHasPlaybackVideo()) || "Y".equals(pLVPlaybackVO2.getData().getHasRecordFile());
                }
                PLVPlaybackVodQOSAnalytics.getInstance().error(((PLVCommonVideoView) PLVPlaybackVideoView.this).playId, PLVPlaybackVideoView.this.videoId, "video_type_playbackVO_is_null", "", "", "", "", PLVPlaybackVodQOSAnalytics.getInstance().getQOSAnalyticsParam(), "");
                ((PolyvPlaybackVideoViewListener) ((PLVBaseVideoView) PLVPlaybackVideoView.this).plvListener).notifyOnError(PLVPlayError.toErrorObj(PLVPlaybackVideoView.this.getCurrentPlayPath(), PLVELogSender.sendPlaybackLog(PLVErrorCodePlayVideoInfo.class, 9, new Exception("回放信息加载失败, data is null")), PLVErrorCodePlayVideoInfo.getMessage(9), 1001));
                PLVPlaybackVideoView.this.setNoStreamViewVisibility(0);
                return false;
            }
        }).subscribe(new Consumer<PLVPlaybackVO2>() { // from class: com.plv.livescenes.playback.video.PLVPlaybackVideoView.7
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVPlaybackVO2 pLVPlaybackVO2) throws Exception {
                boolean zEquals = "Y".equals(pLVPlaybackVO2.getData().getHasRecordFile());
                PLVPlaybackVideoView pLVPlaybackVideoView = PLVPlaybackVideoView.this;
                pLVPlaybackVideoView.f10841t = zEquals ? PLVPlaybackListType.TEMP_STORE : pLVPlaybackVideoView.f10841t;
                PLVPlaybackVideoView pLVPlaybackVideoView2 = PLVPlaybackVideoView.this;
                pLVPlaybackVideoView2.f10830i = new PLVPlaybackDataVO(pLVPlaybackVideoView2.f10841t, pLVPlaybackVO2).setLiveType(((PLVLiveChannelType) PLVChannelFeatureManager.onChannel(PLVPlaybackVideoView.this.channelId).get(PLVChannelFeature.LIVE_CHANNEL_TYPE)).getValue());
                ((PolyvPlaybackVideoViewListener) ((PLVBaseVideoView) PLVPlaybackVideoView.this).plvListener).notifyOnPlaybackDataReady(PLVPlaybackVideoView.this.f10830i);
                PLVPlaybackVideoView.this.k();
                PLVPlaybackVideoView.this.l();
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.playback.video.PLVPlaybackVideoView.8
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                if (th != null) {
                    PLVCommonLog.e("PLVBaseVideoView", "requestPlaybackData: " + th.getMessage());
                }
                ((PolyvPlaybackVideoViewListener) ((PLVBaseVideoView) PLVPlaybackVideoView.this).plvListener).notifyOnError(PLVPlayError.toErrorObj(PLVPlaybackVideoView.this.getCurrentPlayPath(), PLVELogSender.sendPlaybackLog(PLVErrorCodePlayVideoInfo.class, 11, th), PLVPlaybackVideoView.this.getErrorMessage(th), 1001));
                PLVPlaybackVideoView.this.setNoStreamViewVisibility(0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        if (this.f10830i == null) {
            return;
        }
        PLVPlaybackLocalCacheVO localCacheVideo = getLocalCacheVideo();
        if (localCacheVideo == null) {
            this.f10827f.startPlay(this.channelId, this.f10830i);
        } else {
            this.f10827f.startLocalPlay(new PLVPPTLocalCacheVO().setVideoId(localCacheVideo.getVideoId()).setVideoPoolId(localCacheVideo.getVideoPoolId()).setPptHtmlFilePath(localCacheVideo.getJsPath()).setPptDataPath(localCacheVideo.getPptPath()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void l() {
        this.f10823b = PLVResponseExcutor.excute(PLVCommonApiManager.getPlvPlayerApi().getChannelJsonEncrypt(this.userId, this.channelId), String.class, new PLVrResponseCallback<String>() { // from class: com.plv.livescenes.playback.video.PLVPlaybackVideoView.11
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d("PLVBaseVideoView", "getChannelJsonEncrypt onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(String str) {
                if (PLVPlaybackVideoView.this.b(str)) {
                    PLVPlaybackVideoView.this.e();
                    PLVLiveMarqueeVO pLVLiveMarqueeVOGenerateMarqueeVo = PLVPlaybackVideoView.this.f10828g.generateMarqueeVo();
                    PLVWatermarkVO channelWatermarkModel = PLVPlaybackVideoView.this.f10828g.getChannelWatermarkModel();
                    if (channelWatermarkModel == null) {
                        return;
                    }
                    ((PolyvPlaybackVideoViewListener) ((PLVBaseVideoView) PLVPlaybackVideoView.this).plvListener).notifyGetWatermarkVO(channelWatermarkModel);
                    if (pLVLiveMarqueeVOGenerateMarqueeVo == null) {
                        return;
                    }
                    ((PolyvPlaybackVideoViewListener) ((PLVBaseVideoView) PLVPlaybackVideoView.this).plvListener).notifyGetMarqueeVo(pLVLiveMarqueeVOGenerateMarqueeVo);
                    ((PolyvPlaybackVideoViewListener) ((PLVBaseVideoView) PLVPlaybackVideoView.this).plvListener).notifyOnDanmuServerOpen(PLVPlaybackVideoView.this.f10828g.getCloseDanmuEnable().equals("N"));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void m() {
        if (this.f10830i == null || this.f10828g == null) {
            return;
        }
        String statisticValuePb = this.f10841t.getStatisticValuePb();
        this.f10824c = PLVResponseExcutor.excuteResponseBodyData(PLVApiManager.getPlvLiveStatusApi().getLiveStatusByStreamV3(this.f10828g.getStream(), this.channelId, PLVLiveViewLog.getInstance().createStaticNormalInfo(this.playId, this.userId, this.channelId, this.f10830i.getOriginSessionId(), this.f10830i.getChannelSessionId(), getViewerId(), this.viewLogParam2, "vod", this.viewLogParam4, this.viewLogParam5, "", statisticValuePb, this.f10830i.getFileId(), this.playerId, this.watchTimeDuration, (getCurrentPosition() / 1000) + "")), new PLVrResponseCallback<String>() { // from class: com.plv.livescenes.playback.video.PLVPlaybackVideoView.12
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(String str) {
                PLVCommonLog.d("PLVBaseVideoView", "request Live status ");
            }
        });
    }

    private void n() {
        IjkMediaPlayer ijkMediaPlayer = getIjkMediaPlayer();
        if (ijkMediaPlayer != null) {
            long currentPosition = ijkMediaPlayer.getCurrentPosition();
            PLVCommonLog.d("PLVBaseVideoView", "errorPos=" + currentPosition);
            if (currentPosition > 0) {
                this.f10840s = (int) currentPosition;
            }
        }
    }

    @Override // com.plv.business.api.common.player.PLVBaseVideoView
    public PolyvPlaybackVideoViewListener createListener() {
        return new PolyvPlaybackVideoViewListener();
    }

    @Override // com.plv.business.api.common.player.microplayer.IPLVCommonVideoView
    public PLVPlaybackVO.DataBean getModleVO() {
        return this.f10829h;
    }

    private void c() {
        Disposable disposable = this.f10825d;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void d() {
        c();
        Disposable disposable = this.f10822a;
        if (disposable != null) {
            disposable.dispose();
            this.f10822a = null;
        }
        Disposable disposable2 = this.f10823b;
        if (disposable2 != null) {
            disposable2.dispose();
            this.f10823b = null;
        }
        Disposable disposable3 = this.f10824c;
        if (disposable3 != null) {
            disposable3.dispose();
        }
        Disposable disposable4 = this.f10826e;
        if (disposable4 != null) {
            disposable4.dispose();
            this.f10826e = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        PLVPlayOption pLVPlayOption = PLVPlayOption.getDefault();
        pLVPlayOption.put(PLVPlayOption.KEY_RECONNECTION_COUNT, Integer.valueOf(this.f10833l));
        pLVPlayOption.put(PLVPlayOption.KEY_TIMEOUT, Integer.valueOf(this.f10844w));
        setOption(pLVPlayOption);
        if (prepare(pLVPlayOption.get(PLVPlayOption.KEY_PRELOADTIME) != null) && !f()) {
            g();
        }
    }

    private void b() {
        PLVCommonLog.d("PLVBaseVideoView", "startRefreshLiveStatusTimer");
        c();
        this.f10825d = PLVRxTimer.timer(this.playStatInterval, new Consumer<Long>() { // from class: com.plv.livescenes.playback.video.PLVPlaybackVideoView.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                PLVPlaybackVideoView.this.m();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(String str) {
        String channelData2String = PolyvLiveSDKClient.getInstance().getChannelData2String(str);
        PLVLiveChannelVO pLVLiveChannelVO = (PLVLiveChannelVO) PLVGsonUtil.fromJson(PLVLiveChannelVO.class, channelData2String);
        this.f10828g = pLVLiveChannelVO;
        if (pLVLiveChannelVO == null) {
            return false;
        }
        if (pLVLiveChannelVO.getReportFreq() > 0) {
            this.playStatInterval = this.f10828g.getReportFreq();
        }
        this.isOnlyAudio = "Y".equals(this.f10828g.getIsOnlyAudio());
        notifyOnGetLogoListener(this.f10828g.getLogoImage(), this.f10828g.getLogoOpacity(), this.f10828g.getLogoPosition(), this.f10828g.getLogoHref());
        PLVCommonLog.d("PLVBaseVideoView", "channle data :" + channelData2String + "   interval ：" + this.f10828g.getReportFreq());
        return true;
    }

    private void a() {
        this.playId = "";
        this.videoId = "";
        this.channelId = "";
        this.userId = "";
        this.f10836o = PolyvPlayType.IDLE;
        this.f10829h = null;
        this.f10828g = null;
        this.f10831j = Boolean.FALSE;
        this.mCurrentBufferPercentage = 0;
        this.watchTimeDuration = 0;
        this.stayTimeDuration = 0;
        this.f10837p = 0L;
        this.f10839r = false;
        this.f10838q = false;
    }

    public PLVPlaybackVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f10832k = true;
        this.f10833l = 3;
        this.f10834m = false;
        this.f10835n = false;
        this.f10836o = PolyvPlayType.IDLE;
        this.f10837p = 0L;
        this.f10838q = false;
        this.f10839r = false;
        this.f10840s = 0;
        this.f10841t = PLVPlaybackListType.PLAYBACK;
        this.f10842u = new ArrayList();
        this.f10843v = true;
        this.isOnlyAudio = false;
    }

    private void a(PLVBaseVideoParams pLVBaseVideoParams) {
        this.userId = pLVBaseVideoParams.getUserId();
        this.videoId = (String) PLVSugarUtil.getOrDefault(pLVBaseVideoParams.getVideoId(), "");
        this.channelId = pLVBaseVideoParams.getChannelId();
        this.playId = PLVUtils.getPid();
        this.f10836o = PolyvPlayType.ONLINE_PLAY;
        PolyvVodSDKClient.getInstance().setPolyvLogVideoLable(new PLVLogVideoLableVO(this.videoId, this.playId));
        try {
            Boolean bool = Boolean.FALSE;
            this.isOpenMarquee = ((Boolean) pLVBaseVideoParams.getOptionValue(Boolean.class, PLVBaseVideoParams.MARQUEE, bool)).booleanValue();
            this.isAllowOpenAdHead = ((Boolean) pLVBaseVideoParams.getOptionValue(Boolean.class, PLVBaseVideoParams.HEAD_AD, bool)).booleanValue();
            this.f10831j = (Boolean) pLVBaseVideoParams.getOptionValue(Boolean.class, PLVBaseVideoParams.IS_PPT_PLAY, bool);
            this.viewLogParam2 = (String) pLVBaseVideoParams.getOptionValue(String.class, PLVBaseVideoParams.PARAMS2, "");
            this.viewLogParam4 = (String) pLVBaseVideoParams.getOptionValue(String.class, PLVBaseVideoParams.PARAMS4, "");
            this.viewLogParam5 = (String) pLVBaseVideoParams.getOptionValue(String.class, PLVBaseVideoParams.PARAMS5, "");
            enableAccurateSeek(((Boolean) pLVBaseVideoParams.getOptionValue(Boolean.class, PLVPlaybackVideoParams.ENABLE_ACCURATE_SEEK, bool)).booleanValue());
            this.f10841t = (PLVPlaybackListType) pLVBaseVideoParams.getOptionValue(PLVPlaybackListType.class, PLVPlaybackVideoParams.VIDEO_LISTTYPE, PLVPlaybackListType.PLAYBACK);
            this.f10843v = ((Boolean) pLVBaseVideoParams.getOptionValue(Boolean.class, PLVPlaybackVideoParams.ENABLE_AUTO_PLAY_TEMP_STORE_VIDEO, Boolean.TRUE)).booleanValue();
            this.f10844w = ((Integer) pLVBaseVideoParams.getOptionValue(Integer.class, PLVBaseVideoParams.LOAD_SLOW_TIME, 15)).intValue();
            this.f10842u.clear();
            this.f10842u.addAll((Collection) pLVBaseVideoParams.getOptionValue(List.class, PLVPlaybackVideoParams.LOCAL_VIDEO_CACHE_LIST, Collections.emptyList()));
        } catch (Exception unused) {
            PLVCommonLog.e("PLVBaseVideoView", "param is wrong");
        }
    }

    public PLVPlaybackVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f10832k = true;
        this.f10833l = 3;
        this.f10834m = false;
        this.f10835n = false;
        this.f10836o = PolyvPlayType.IDLE;
        this.f10837p = 0L;
        this.f10838q = false;
        this.f10839r = false;
        this.f10840s = 0;
        this.f10841t = PLVPlaybackListType.PLAYBACK;
        this.f10842u = new ArrayList();
        this.f10843v = true;
        this.isOnlyAudio = false;
    }

    private void a(int i2) {
        PLVELogSender.sendPlaybackLog(PLVErrorCodePlayVideoPlay.class, 1, new Exception("视频播放异常, playPath is" + getCurrentPlayPath() + ", what is " + i2 + ", position is " + getCurrentPosition()));
    }

    private boolean a(String str) {
        return str.endsWith("mp4") || str.endsWith("MP4");
    }

    private void a(@NonNull PLVPlaybackLocalCacheVO pLVPlaybackLocalCacheVO) {
        PLVPlaybackDataVO pLVPlaybackDataVO = new PLVPlaybackDataVO(pLVPlaybackLocalCacheVO);
        this.f10830i = pLVPlaybackDataVO;
        ((PolyvPlaybackVideoViewListener) this.plvListener).notifyOnPlaybackDataReady(pLVPlaybackDataVO);
        k();
        l();
    }
}
