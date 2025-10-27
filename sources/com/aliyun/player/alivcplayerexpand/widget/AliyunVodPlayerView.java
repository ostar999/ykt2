package com.aliyun.player.alivcplayerexpand.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.VidPlayerConfigGen;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.alivcplayerexpand.bean.DotBean;
import com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig;
import com.aliyun.player.alivcplayerexpand.listener.LockPortraitListener;
import com.aliyun.player.alivcplayerexpand.listener.OnAutoPlayListener;
import com.aliyun.player.alivcplayerexpand.listener.OnScreenCostingSingleTagListener;
import com.aliyun.player.alivcplayerexpand.listener.OnStoppedListener;
import com.aliyun.player.alivcplayerexpand.listener.QualityValue;
import com.aliyun.player.alivcplayerexpand.theme.ITheme;
import com.aliyun.player.alivcplayerexpand.theme.Theme;
import com.aliyun.player.alivcplayerexpand.util.BrowserCheckUtil;
import com.aliyun.player.alivcplayerexpand.util.DensityUtil;
import com.aliyun.player.alivcplayerexpand.util.TimeFormater;
import com.aliyun.player.alivcplayerexpand.view.control.ControlView;
import com.aliyun.player.alivcplayerexpand.view.dot.DotView;
import com.aliyun.player.alivcplayerexpand.view.function.AdvPictureView;
import com.aliyun.player.alivcplayerexpand.view.function.AdvVideoView;
import com.aliyun.player.alivcplayerexpand.view.function.MarqueeView;
import com.aliyun.player.alivcplayerexpand.view.function.MutiSeekBarView;
import com.aliyun.player.alivcplayerexpand.view.function.PlayerDanmakuView;
import com.aliyun.player.alivcplayerexpand.view.function.WaterMarkRegion;
import com.aliyun.player.alivcplayerexpand.view.gesture.GestureDialogManager;
import com.aliyun.player.alivcplayerexpand.view.gesture.GestureView;
import com.aliyun.player.alivcplayerexpand.view.guide.GuideView;
import com.aliyun.player.alivcplayerexpand.view.interfaces.ViewAction;
import com.aliyun.player.alivcplayerexpand.view.more.SpeedValue;
import com.aliyun.player.alivcplayerexpand.view.quality.QualityView;
import com.aliyun.player.alivcplayerexpand.view.speed.SpeedView;
import com.aliyun.player.alivcplayerexpand.view.thumbnail.ThumbnailView;
import com.aliyun.player.alivcplayerexpand.view.trailers.TrailersView;
import com.aliyun.player.alivcplayerexpand.widget.AliyunRenderView;
import com.aliyun.player.aliyunplayerbase.util.AliyunScreenMode;
import com.aliyun.player.aliyunplayerbase.util.NetWatchdog;
import com.aliyun.player.aliyunplayerbase.util.OrientationWatchDog;
import com.aliyun.player.aliyunplayerbase.util.ScreenUtils;
import com.aliyun.player.aliyunplayerbase.view.tipsview.OnTipsViewBackClickListener;
import com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.bean.InfoCode;
import com.aliyun.player.nativeclass.CacheConfig;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.PlayerConfig;
import com.aliyun.player.nativeclass.Thumbnail;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.LiveSts;
import com.aliyun.player.source.StsInfo;
import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidMps;
import com.aliyun.player.source.VidSts;
import com.aliyun.subtitle.SubtitleView;
import com.aliyun.svideo.common.utils.DensityUtils;
import com.aliyun.svideo.common.utils.FileUtils;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.aliyun.svideo.common.utils.ToastUtils;
import com.aliyun.svideo.common.utils.image.ImageLoaderImpl;
import com.aliyun.thumbnail.ThumbnailBitmapInfo;
import com.aliyun.thumbnail.ThumbnailHelper;
import com.cicada.player.utils.Logger;
import com.cicada.player.utils.ass.AssHeader;
import com.cicada.player.utils.ass.AssSubtitleView;
import com.yikaobang.yixue.R2;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class AliyunVodPlayerView extends RelativeLayout implements ITheme {
    private static final String ADV_PICTURE_URL = "";
    private static final String ADV_URL = "https://www.aliyun.com/product/vod?spm=5176.10695662.782639.1.4ac218e2p7BEEf";
    private static final int ADV_VIDEO_PREPARED = 0;
    private static final String ADV_VIDEO_URL = "https://alivc-demo-cms.alicdn.com/video/videoAD.mp4";
    private static final int SOURCE_VIDEO_PREPARED = 1;
    private static final String VIDEO_ADV_VID = "9fb028c29acb421cb634c77cf4ebe078";
    private static final String WATER_MARK_URL = "";
    private AudioManager audioManager;
    private float currentSpeed;
    private float currentVolume;
    private View fastForwardView;
    private View guestureSeekView;
    private Map<MediaInfo, Boolean> hasLoadEnd;
    private boolean inSeek;
    private boolean initNetWatch;
    private boolean isCompleted;
    public boolean isLongPress;
    private boolean isShowBackAndFullBtn;
    private boolean isVideo;
    private long mAdvCurrentPosition;
    private long mAdvDuration;
    private AdvPictureView mAdvPictureView;
    private MutiSeekBarView.AdvPosition mAdvPosition;
    private long mAdvTotalPosition;
    private int mAdvVideoCount;
    private MediaInfo mAdvVideoMediaInfo;
    private MediaInfo mAdvVideoMeidaInfo;
    private int mAdvVideoPlayerState;
    private AdvVideoView mAdvVideoView;
    private LiveSts mAliyunLiveSts;
    private UrlSource mAliyunLocalSource;
    private MediaInfo mAliyunMediaInfo;
    private VidAuth mAliyunPlayAuth;
    public AliyunRenderView mAliyunRenderView;
    private VidMps mAliyunVidMps;
    private VidSts mAliyunVidSts;
    private AssSubtitleView mAssSubtitleView;
    private ControlView mControlView;
    private ImageView mCoverView;
    private int mCurrentBufferPercentage;
    private AdvVideoView.IntentPlayVideo mCurrentIntentPlayVideo;
    private long mCurrentPosition;
    private AliyunScreenMode mCurrentScreenMode;
    private String mCurrentSpeed;
    private PlayerDanmakuView mDanmakuView;
    public FullOrigrinClickIml mFullOrigrinClickIml;
    private GestureDialogManager mGestureDialogManager;
    private GestureView mGestureView;
    private GuideView mGuideView;
    private boolean mInBackground;
    private boolean mIsCanPlayVideoBy4G;
    private boolean mIsFullScreenLocked;
    private boolean mIsInMultiWindow;
    private boolean mIsNeedOnlyFullScreen;
    private boolean mIsOperatorPlay;
    private boolean mIsScreenCosting;
    private boolean mIsVipRetry;
    private ControlView.OnLandScapeActionListener mLandScapeActionListener;
    private LockPortraitListener mLockPortraitListener;
    private MarqueeView mMarqueeView;
    private NetConnectedListener mNetConnectedListener;
    private NetWatchdog mNetWatchdog;
    private ControlView.OnControlViewHideListener mOnControlViewHideListener;
    private ControlView.OnDotViewClickListener mOnDotViewClickListener;
    private OnFinishListener mOnFinishListener;
    private OnScreenBrightnessListener mOnScreenBrightnessListener;
    private OnScreenCostingSingleTagListener mOnScreenCostingSingleTagListener;
    private OnScreenCostingVideoCompletionListener mOnScreenCostingVideoCompletionListener;
    private OnSoftKeyHideListener mOnSoftKeyHideListener;
    private OnStoppedListener mOnStoppedListener;
    private TrailersView.OnTrailerViewClickListener mOnTrailerViewClickListener;
    private OnAutoPlayListener mOutAutoPlayListener;
    private IPlayer.OnCompletionListener mOutCompletionListener;
    private IPlayer.OnErrorListener mOutErrorListener;
    private IPlayer.OnRenderingStartListener mOutFirstFrameStartListener;
    private IPlayer.OnInfoListener mOutInfoListener;
    private IPlayer.OnSeiDataListener mOutOnSeiDataListener;
    private ControlView.OnShowMoreClickListener mOutOnShowMoreClickListener;
    private TipsView.OnTipClickListener mOutOnTipClickListener;
    private OnTipsViewBackClickListener mOutOnTipsViewBackClickListener;
    private IPlayer.OnTrackChangedListener mOutOnTrackChangedListener;
    private ControlView.OnTrackInfoClickListener mOutOnTrackInfoClickListener;
    private IPlayer.OnTrackReadyListener mOutOnTrackReadyListener;
    private AliPlayer.OnVerifyTimeExpireCallback mOutOnVerifyTimeExpireCallback;
    private IPlayer.OnPreparedListener mOutPreparedListener;
    private OnTimeExpiredErrorListener mOutTimeExpiredErrorListener;
    private IPlayer.OnSeekCompleteListener mOuterSeekCompleteListener;
    private int mPlayerState;
    private QualityView mQualityView;
    private int mScreenBrightness;
    private int mScreenCostingVolume;
    private OnScreenShotListener mScreenShotListener;
    private int mSeekToCurrentPlayerPosition;
    private int mSeekToPosition;
    private long mSourceDuration;
    private int mSourceSeekToPosition;
    private MediaInfo mSourceVideoMediaInfo;
    private SpeedView mSpeedView;
    private int mStartScreenCostingPosition;
    private Map<Integer, AssHeader.SubtitleType> mSubtitleTypeMap;
    private SubtitleView mSubtitleView;
    private SurfaceView mSurfaceView;
    private ThumbnailHelper mThumbnailHelper;
    private boolean mThumbnailPrepareSuccess;
    private ThumbnailView mThumbnailView;
    private TipsView mTipsView;
    private TrailersView mTrailersView;
    private long mVideoBufferedPosition;
    private VodPlayerHandler mVodPlayerHandler;
    private ImageView mWaterMark;
    private boolean needToSeek;
    private OnPlayStateBtnClickListener onPlayStateBtnClickListener;
    private OnSeekStartListener onSeekStartListener;
    private OnOrientationChangeListener orientationChangeListener;
    public PauseClickIml pauseClickIml;
    private boolean preparePlay;
    private boolean tryWatchComplete;
    private TextView tvCurrent;
    private TextView tvTotal;
    private VodPlayerLoadEndHandler vodPlayerLoadEndHandler;
    private static final WaterMarkRegion WATER_MARK_REGION = WaterMarkRegion.RIGHT_TOP;
    private static final MarqueeView.MarqueeRegion MARQUEE_REGION = MarqueeView.MarqueeRegion.TOP;
    private static final String TAG = AliyunVodPlayerView.class.getSimpleName();
    public static int TRAILER = 300;
    public static String PLAY_DOMAIN = "alivc-demo-vod-player.aliyuncs.com";

    /* renamed from: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView$27, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass27 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$AdvVideoView$IntentPlayVideo;
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MarqueeView$MarqueeRegion;
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$WaterMarkRegion;

        static {
            int[] iArr = new int[AdvVideoView.IntentPlayVideo.values().length];
            $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$AdvVideoView$IntentPlayVideo = iArr;
            try {
                iArr[AdvVideoView.IntentPlayVideo.START_ADV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$AdvVideoView$IntentPlayVideo[AdvVideoView.IntentPlayVideo.MIDDLE_ADV.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$AdvVideoView$IntentPlayVideo[AdvVideoView.IntentPlayVideo.END_ADV.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$AdvVideoView$IntentPlayVideo[AdvVideoView.IntentPlayVideo.MIDDLE_ADV_SEEK.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$AdvVideoView$IntentPlayVideo[AdvVideoView.IntentPlayVideo.MIDDLE_END_ADV_SEEK.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$AdvVideoView$IntentPlayVideo[AdvVideoView.IntentPlayVideo.REVERSE_SOURCE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$AdvVideoView$IntentPlayVideo[AdvVideoView.IntentPlayVideo.NORMAL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[WaterMarkRegion.values().length];
            $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$WaterMarkRegion = iArr2;
            try {
                iArr2[WaterMarkRegion.LEFT_TOP.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$WaterMarkRegion[WaterMarkRegion.LEFT_BOTTOM.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$WaterMarkRegion[WaterMarkRegion.RIGHT_TOP.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$WaterMarkRegion[WaterMarkRegion.RIGHT_BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused11) {
            }
            int[] iArr3 = new int[MarqueeView.MarqueeRegion.values().length];
            $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MarqueeView$MarqueeRegion = iArr3;
            try {
                iArr3[MarqueeView.MarqueeRegion.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MarqueeView$MarqueeRegion[MarqueeView.MarqueeRegion.MIDDLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MarqueeView$MarqueeRegion[MarqueeView.MarqueeRegion.BOTTOM.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    public interface FullOrigrinClickIml {
        void mFullOrigrinClick(int i2);
    }

    public static class InnerOrientationListener implements OrientationWatchDog.OnOrientationListener {
        private WeakReference<AliyunVodPlayerView> playerViewWeakReference;

        public InnerOrientationListener(AliyunVodPlayerView aliyunVodPlayerView) {
            this.playerViewWeakReference = new WeakReference<>(aliyunVodPlayerView);
        }

        @Override // com.aliyun.player.aliyunplayerbase.util.OrientationWatchDog.OnOrientationListener
        public void changedToLandForwardScape(boolean z2) {
            this.playerViewWeakReference.get();
        }

        @Override // com.aliyun.player.aliyunplayerbase.util.OrientationWatchDog.OnOrientationListener
        public void changedToLandReverseScape(boolean z2) {
            this.playerViewWeakReference.get();
        }

        @Override // com.aliyun.player.aliyunplayerbase.util.OrientationWatchDog.OnOrientationListener
        public void changedToPortrait(boolean z2) {
            this.playerViewWeakReference.get();
        }
    }

    public static class MyNetChangeListener implements NetWatchdog.NetChangeListener {
        private WeakReference<AliyunVodPlayerView> viewWeakReference;

        public MyNetChangeListener(AliyunVodPlayerView aliyunVodPlayerView) {
            this.viewWeakReference = new WeakReference<>(aliyunVodPlayerView);
        }

        @Override // com.aliyun.player.aliyunplayerbase.util.NetWatchdog.NetChangeListener
        public void on4GToWifi() {
            AliyunVodPlayerView aliyunVodPlayerView = this.viewWeakReference.get();
            if (aliyunVodPlayerView == null || !aliyunVodPlayerView.isLocalSource()) {
                return;
            }
            aliyunVodPlayerView.on4GToWifi();
        }

        @Override // com.aliyun.player.aliyunplayerbase.util.NetWatchdog.NetChangeListener
        public void onNetDisconnected() {
            AliyunVodPlayerView aliyunVodPlayerView = this.viewWeakReference.get();
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.onNetDisconnected();
            }
        }

        @Override // com.aliyun.player.aliyunplayerbase.util.NetWatchdog.NetChangeListener
        public void onWifiTo4G() throws IllegalAccessException, IllegalArgumentException {
            AliyunVodPlayerView aliyunVodPlayerView = this.viewWeakReference.get();
            if (aliyunVodPlayerView != null) {
                if (aliyunVodPlayerView.isLocalSource()) {
                    aliyunVodPlayerView.onWifiTo4G();
                    return;
                }
                if (aliyunVodPlayerView.mIsOperatorPlay || aliyunVodPlayerView.isLocalVideo()) {
                    return;
                }
                aliyunVodPlayerView.pause();
                aliyunVodPlayerView.preparePlay = false;
                if (aliyunVodPlayerView.mTipsView != null) {
                    aliyunVodPlayerView.mTipsView.showNetChangeTipView();
                }
                if (aliyunVodPlayerView.mControlView != null) {
                    aliyunVodPlayerView.mControlView.hide(ViewAction.HideType.Normal);
                }
            }
        }
    }

    public class MyNetConnectedListener implements NetWatchdog.NetConnectedListener {
        public MyNetConnectedListener(AliyunVodPlayerView aliyunVodPlayerView) {
        }

        @Override // com.aliyun.player.aliyunplayerbase.util.NetWatchdog.NetConnectedListener
        public void onNetUnConnected() {
            if (AliyunVodPlayerView.this.mNetConnectedListener != null) {
                AliyunVodPlayerView.this.mNetConnectedListener.onNetUnConnected();
            }
        }

        @Override // com.aliyun.player.aliyunplayerbase.util.NetWatchdog.NetConnectedListener
        public void onReNetConnected(boolean z2) {
            if (AliyunVodPlayerView.this.mNetConnectedListener != null) {
                AliyunVodPlayerView.this.mNetConnectedListener.onReNetConnected(z2);
            }
        }
    }

    public interface NetConnectedListener {
        void onNetUnConnected();

        void onReNetConnected(boolean z2);
    }

    public interface OnFinishListener {
        void onFinishClick();
    }

    public interface OnOrientationChangeListener {
        void orientationChange(boolean z2, AliyunScreenMode aliyunScreenMode);
    }

    public interface OnPlayStateBtnClickListener {
        void onPlayBtnClick(int i2);
    }

    public interface OnScreenBrightnessListener {
        void onScreenBrightness(int i2);
    }

    public interface OnScreenCostingVideoCompletionListener {
        void onScreenCostingVideoCompletion();
    }

    public interface OnScreenShotListener {
        void snapshotSuccess(String str);
    }

    public interface OnSeekStartListener {
        void onSeekStart(int i2);
    }

    public interface OnSoftKeyHideListener {
        void onClickPaint();

        void softKeyHide();
    }

    public interface OnTimeExpiredErrorListener {
        void onTimeExpiredError();
    }

    public interface PauseClickIml {
        void mPauseClick();
    }

    public static class VideoPlayerAdvBackImageViewListener implements AdvVideoView.OnBackImageViewClickListener {
        private WeakReference<AliyunVodPlayerView> weakReference;

        public VideoPlayerAdvBackImageViewListener(AliyunVodPlayerView aliyunVodPlayerView) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
        }

        @Override // com.aliyun.player.alivcplayerexpand.view.function.AdvVideoView.OnBackImageViewClickListener
        public void onBackImageViewClick() {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.onAdvBackImageViewClickListener();
            }
        }
    }

    public static class VideoPlayerCompletionListener implements IPlayer.OnCompletionListener {
        private boolean isAdvPlayer;
        private WeakReference<AliyunVodPlayerView> weakReference;

        public VideoPlayerCompletionListener(AliyunVodPlayerView aliyunVodPlayerView, boolean z2) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
            this.isAdvPlayer = z2;
        }

        @Override // com.aliyun.player.IPlayer.OnCompletionListener
        public void onCompletion() {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                if (this.isAdvPlayer) {
                    aliyunVodPlayerView.advVideoPlayerCompletion();
                } else {
                    aliyunVodPlayerView.sourceVideoPlayerCompletion();
                }
            }
        }
    }

    public static class VideoPlayerErrorListener implements IPlayer.OnErrorListener {
        private boolean isAdvPlayer;
        private WeakReference<AliyunVodPlayerView> weakReference;

        public VideoPlayerErrorListener(AliyunVodPlayerView aliyunVodPlayerView, boolean z2) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
            this.isAdvPlayer = z2;
        }

        @Override // com.aliyun.player.IPlayer.OnErrorListener
        public void onError(ErrorInfo errorInfo) {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                if (this.isAdvPlayer) {
                    aliyunVodPlayerView.advVideoPlayerError(errorInfo);
                } else {
                    aliyunVodPlayerView.sourceVideoPlayerError(errorInfo);
                }
            }
        }
    }

    public static class VideoPlayerInfoListener implements IPlayer.OnInfoListener {
        private boolean isAdvPlayer;
        private WeakReference<AliyunVodPlayerView> weakReference;

        public VideoPlayerInfoListener(AliyunVodPlayerView aliyunVodPlayerView, boolean z2) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
            this.isAdvPlayer = z2;
        }

        @Override // com.aliyun.player.IPlayer.OnInfoListener
        public void onInfo(InfoBean infoBean) {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                if (this.isAdvPlayer) {
                    aliyunVodPlayerView.advVideoPlayerInfo(infoBean);
                } else {
                    aliyunVodPlayerView.sourceVideoPlayerInfo(infoBean);
                }
            }
        }
    }

    public static class VideoPlayerLoadingStatusListener implements IPlayer.OnLoadingStatusListener {
        private boolean isAdvPlayer;
        private WeakReference<AliyunVodPlayerView> weakReference;

        public VideoPlayerLoadingStatusListener(AliyunVodPlayerView aliyunVodPlayerView, boolean z2) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
            this.isAdvPlayer = z2;
        }

        @Override // com.aliyun.player.IPlayer.OnLoadingStatusListener
        public void onLoadingBegin() {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                if (this.isAdvPlayer) {
                    aliyunVodPlayerView.advVideoPlayerLoadingBegin();
                } else {
                    aliyunVodPlayerView.sourceVideoPlayerLoadingBegin();
                }
            }
        }

        @Override // com.aliyun.player.IPlayer.OnLoadingStatusListener
        public void onLoadingEnd() {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                if (this.isAdvPlayer) {
                    aliyunVodPlayerView.advVideoPlayerLoadingEnd();
                } else {
                    aliyunVodPlayerView.sourceVideoPlayerLoadingEnd();
                }
            }
        }

        @Override // com.aliyun.player.IPlayer.OnLoadingStatusListener
        public void onLoadingProgress(int i2, float f2) {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                if (this.isAdvPlayer) {
                    aliyunVodPlayerView.advVideoPlayerLoadingProgress(i2);
                } else {
                    aliyunVodPlayerView.sourceVideoPlayerLoadingProgress(i2);
                }
            }
        }
    }

    public static class VideoPlayerOnSeekCompleteListener implements IPlayer.OnSeekCompleteListener {
        private WeakReference<AliyunVodPlayerView> weakReference;

        public VideoPlayerOnSeekCompleteListener(AliyunVodPlayerView aliyunVodPlayerView) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
        }

        @Override // com.aliyun.player.IPlayer.OnSeekCompleteListener
        public void onSeekComplete() {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.sourceVideoPlayerSeekComplete();
            }
        }
    }

    public static class VideoPlayerOnSeiDataListener implements IPlayer.OnSeiDataListener {
        private WeakReference<AliyunVodPlayerView> weakReference;

        public VideoPlayerOnSeiDataListener(AliyunVodPlayerView aliyunVodPlayerView) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
        }

        @Override // com.aliyun.player.IPlayer.OnSeiDataListener
        public void onSeiData(int i2, byte[] bArr) {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.onSeiData(i2, bArr);
            }
        }
    }

    public static class VideoPlayerOnSnapShotListener implements IPlayer.OnSnapShotListener {
        private WeakReference<AliyunVodPlayerView> weakReference;

        public VideoPlayerOnSnapShotListener(AliyunVodPlayerView aliyunVodPlayerView) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
        }

        @Override // com.aliyun.player.IPlayer.OnSnapShotListener
        public void onSnapShot(Bitmap bitmap, int i2, int i3) {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.sourceVideoSnapShot(bitmap, i2, i3);
            }
        }
    }

    public static class VideoPlayerOnTrackReadyListenner implements IPlayer.OnTrackReadyListener {
        public WeakReference<AliyunVodPlayerView> weakReference;

        public VideoPlayerOnTrackReadyListenner(AliyunVodPlayerView aliyunVodPlayerView) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
        }

        @Override // com.aliyun.player.IPlayer.OnTrackReadyListener
        public void onTrackReady(MediaInfo mediaInfo) {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.onTrackReady(mediaInfo);
            }
        }
    }

    public static class VideoPlayerOnVerifyStsCallback implements AliPlayer.OnVerifyTimeExpireCallback {
        private WeakReference<AliyunVodPlayerView> weakReference;

        public VideoPlayerOnVerifyStsCallback(AliyunVodPlayerView aliyunVodPlayerView) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
        }

        @Override // com.aliyun.player.AliPlayer.OnVerifyTimeExpireCallback
        public AliPlayer.Status onVerifyAuth(VidAuth vidAuth) {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            return aliyunVodPlayerView != null ? aliyunVodPlayerView.onVerifyAuth(vidAuth) : AliPlayer.Status.Valid;
        }

        @Override // com.aliyun.player.AliPlayer.OnVerifyTimeExpireCallback
        public AliPlayer.Status onVerifySts(StsInfo stsInfo) {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            return aliyunVodPlayerView != null ? aliyunVodPlayerView.onVerifySts(stsInfo) : AliPlayer.Status.Valid;
        }
    }

    public static class VideoPlayerPreparedListener implements IPlayer.OnPreparedListener {
        private boolean isAdvPlayer;
        private WeakReference<AliyunVodPlayerView> weakReference;

        public VideoPlayerPreparedListener(AliyunVodPlayerView aliyunVodPlayerView, boolean z2) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
            this.isAdvPlayer = z2;
        }

        @Override // com.aliyun.player.IPlayer.OnPreparedListener
        public void onPrepared() {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                if (this.isAdvPlayer) {
                    aliyunVodPlayerView.advVideoPlayerPrepared();
                } else {
                    aliyunVodPlayerView.sourceVideoPlayerPrepared();
                }
            }
        }
    }

    public static class VideoPlayerRenderingStartListener implements IPlayer.OnRenderingStartListener {
        private boolean isAdvPlayer;
        private WeakReference<AliyunVodPlayerView> weakReference;

        public VideoPlayerRenderingStartListener(AliyunVodPlayerView aliyunVodPlayerView, boolean z2) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
            this.isAdvPlayer = z2;
        }

        @Override // com.aliyun.player.IPlayer.OnRenderingStartListener
        public void onRenderingStart() {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                if (this.isAdvPlayer) {
                    aliyunVodPlayerView.advVideoPlayerOnVideoRenderingStart();
                } else {
                    aliyunVodPlayerView.sourceVideoPlayerOnVideoRenderingStart();
                }
            }
        }
    }

    public static class VideoPlayerStateChangedListener implements IPlayer.OnStateChangedListener {
        private boolean isAdvPlayer;
        private WeakReference<AliyunVodPlayerView> weakReference;

        public VideoPlayerStateChangedListener(AliyunVodPlayerView aliyunVodPlayerView, boolean z2) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
            this.isAdvPlayer = z2;
        }

        @Override // com.aliyun.player.IPlayer.OnStateChangedListener
        public void onStateChanged(int i2) {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                if (this.isAdvPlayer) {
                    aliyunVodPlayerView.advVideoPlayerStateChanged(i2);
                } else {
                    aliyunVodPlayerView.sourceVideoPlayerStateChanged(i2);
                }
            }
        }
    }

    public static class VideoPlayerSubtitleDeisplayListener implements IPlayer.OnSubtitleDisplayListener {
        private final WeakReference<AliyunVodPlayerView> weakReference;

        public VideoPlayerSubtitleDeisplayListener(AliyunVodPlayerView aliyunVodPlayerView) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
        }

        @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
        public void onSubtitleExtAdded(int i2, String str) {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.mAliyunRenderView.selectExtSubtitle(i2, true);
            }
        }

        @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
        public void onSubtitleHeader(int i2, String str) {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.onSubtitleHeader(i2, str);
            }
        }

        @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
        public void onSubtitleHide(int i2, long j2) {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.onSubtitleHide(i2, j2);
            }
        }

        @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
        public void onSubtitleShow(int i2, long j2, String str) {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.onSubtitleShow(i2, j2, str);
            }
        }
    }

    public static class VideoPlayerTrackChangedListener implements IPlayer.OnTrackChangedListener {
        private WeakReference<AliyunVodPlayerView> weakReference;

        public VideoPlayerTrackChangedListener(AliyunVodPlayerView aliyunVodPlayerView) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
        }

        @Override // com.aliyun.player.IPlayer.OnTrackChangedListener
        public void onChangedFail(TrackInfo trackInfo, ErrorInfo errorInfo) {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.sourceVideoPlayerTrackInfoChangedFail(trackInfo, errorInfo);
            }
        }

        @Override // com.aliyun.player.IPlayer.OnTrackChangedListener
        public void onChangedSuccess(TrackInfo trackInfo) {
            AliyunVodPlayerView aliyunVodPlayerView = this.weakReference.get();
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.sourceVideoPlayerTrackInfoChangedSuccess(trackInfo);
            }
        }
    }

    public static class VodPlayerHandler extends Handler {
        private WeakReference<AliyunVodPlayerView> weakReference;

        public VodPlayerHandler(AliyunVodPlayerView aliyunVodPlayerView) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            AliyunVodPlayerView aliyunVodPlayerView;
            TrackInfo trackInfoCurrentTrack;
            super.handleMessage(message);
            int i2 = message.what;
            if ((i2 == 0 || i2 == 1) && (aliyunVodPlayerView = this.weakReference.get()) != null) {
                if (message.what == 0) {
                    aliyunVodPlayerView.mAdvVideoMeidaInfo = (MediaInfo) message.obj;
                }
                if (message.what == 1) {
                    aliyunVodPlayerView.mSourceVideoMediaInfo = (MediaInfo) message.obj;
                }
                if (aliyunVodPlayerView.mSourceVideoMediaInfo == null || aliyunVodPlayerView.mAdvVideoMeidaInfo == null) {
                    return;
                }
                new MediaInfo().setDuration(aliyunVodPlayerView.mAdvVideoMeidaInfo.getDuration() + aliyunVodPlayerView.mSourceVideoMediaInfo.getDuration());
                AliyunRenderView aliyunRenderView = aliyunVodPlayerView.mAliyunRenderView;
                if (aliyunRenderView != null && (trackInfoCurrentTrack = aliyunRenderView.currentTrack(TrackInfo.Type.TYPE_VOD.ordinal())) != null) {
                    aliyunVodPlayerView.mControlView.setMediaInfo(aliyunVodPlayerView.mSourceVideoMediaInfo, trackInfoCurrentTrack.getVodDefinition());
                }
                ControlView controlView = aliyunVodPlayerView.mControlView;
                ViewAction.HideType hideType = ViewAction.HideType.Normal;
                controlView.setHideType(hideType);
                aliyunVodPlayerView.mGestureView.setHideType(hideType);
                aliyunVodPlayerView.mControlView.setPlayState(ControlView.PlayState.Playing);
                aliyunVodPlayerView.mControlView.setMutiSeekBarInfo(aliyunVodPlayerView.mAdvVideoMeidaInfo.getDuration(), aliyunVodPlayerView.mSourceVideoMediaInfo.getDuration(), aliyunVodPlayerView.mAdvPosition);
                aliyunVodPlayerView.mControlView.hideNativeSeekBar();
                aliyunVodPlayerView.mGestureView.show();
                if (aliyunVodPlayerView.mTipsView != null) {
                    aliyunVodPlayerView.mTipsView.hideNetLoadingTipView();
                }
            }
        }
    }

    public static class VodPlayerLoadEndHandler extends Handler {
        private boolean intentPause;
        private WeakReference<AliyunVodPlayerView> weakReference;

        public VodPlayerLoadEndHandler(AliyunVodPlayerView aliyunVodPlayerView) {
            this.weakReference = new WeakReference<>(aliyunVodPlayerView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            AliyunVodPlayerView aliyunVodPlayerView;
            super.handleMessage(message);
            int i2 = message.what;
            if (i2 == 0) {
                this.intentPause = true;
            }
            if (i2 == 1 && (aliyunVodPlayerView = this.weakReference.get()) != null && this.intentPause) {
                aliyunVodPlayerView.onStop();
                this.intentPause = false;
            }
        }
    }

    public AliyunVodPlayerView(Context context) {
        this(context, null);
    }

    private void addFastForwardView() {
        this.fastForwardView = View.inflate(getContext(), R.layout.fast_forward_tips_view, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13, -1);
        layoutParams.addRule(10, -1);
        addView(this.fastForwardView, layoutParams);
        this.fastForwardView.setVisibility(8);
    }

    private void addSubViewBelow(final View view, final View view2) {
        view2.post(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.23
            @Override // java.lang.Runnable
            public void run() {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
                layoutParams.topMargin = view2.getMeasuredHeight();
                AliyunVodPlayerView.this.addView(view, layoutParams);
            }
        });
    }

    private void addSubViewByBottom(View view) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(12);
        addView(view, layoutParams);
    }

    private void addSubViewByCenter(View view) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        addView(view, layoutParams);
    }

    private void addSubViewByWrap(View view) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        int i2 = AnonymousClass27.$SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$WaterMarkRegion[WATER_MARK_REGION.ordinal()];
        if (i2 == 1) {
            layoutParams.leftMargin = DensityUtil.dip2px(getContext(), 20.0f);
            layoutParams.topMargin = DensityUtil.dip2px(getContext(), 10.0f);
            layoutParams.addRule(9);
            layoutParams.addRule(10);
        } else if (i2 == 2) {
            layoutParams.leftMargin = DensityUtil.dip2px(getContext(), 20.0f);
            layoutParams.bottomMargin = DensityUtil.dip2px(getContext(), 10.0f);
            layoutParams.addRule(9);
            layoutParams.addRule(12);
        } else if (i2 == 3 || i2 != 4) {
            layoutParams.rightMargin = DensityUtil.dip2px(getContext(), 20.0f);
            layoutParams.topMargin = DensityUtil.dip2px(getContext(), 10.0f);
            layoutParams.addRule(11);
            layoutParams.addRule(10);
        } else {
            layoutParams.rightMargin = DensityUtil.dip2px(getContext(), 20.0f);
            layoutParams.bottomMargin = DensityUtil.dip2px(getContext(), 10.0f);
            layoutParams.addRule(11);
            layoutParams.addRule(12);
        }
        addView(view, layoutParams);
    }

    private void addSubViewHeightWrap(View view) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        int i2 = AnonymousClass27.$SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MarqueeView$MarqueeRegion[MARQUEE_REGION.ordinal()];
        if (i2 == 1) {
            layoutParams.addRule(10);
        } else if (i2 == 2) {
            layoutParams.addRule(15);
        } else if (i2 != 3) {
            layoutParams.addRule(10);
        } else {
            layoutParams.addRule(12);
        }
        addView(view, layoutParams);
    }

    private boolean advStyleIsIncludeEnd() {
        MutiSeekBarView.AdvPosition advPosition = this.mAdvPosition;
        return advPosition == MutiSeekBarView.AdvPosition.ALL || advPosition == MutiSeekBarView.AdvPosition.ONLY_END || advPosition == MutiSeekBarView.AdvPosition.START_END || advPosition == MutiSeekBarView.AdvPosition.MIDDLE_END;
    }

    private void advVideoPlayer4gTips() {
        if (show4gTips()) {
            return;
        }
        this.mAliyunRenderView.start();
        this.mAdvVideoView.setAutoPlay(false);
        this.mAdvVideoView.optionPrepare();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void advVideoPlayerCompletion() {
        TrailersView trailersView;
        if (GlobalPlayerConfig.IS_TRAILER && (trailersView = this.mTrailersView) != null) {
            trailersView.trailerPlayTipsIsShow(true);
        }
        showDanmakuAndMarquee();
        this.mAdvVideoCount++;
        this.inSeek = false;
        afterAdvVideoPlayerComplete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void advVideoPlayerError(ErrorInfo errorInfo) {
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.hideAll();
        }
        lockScreen(false);
        showErrorTipView(errorInfo.getCode().getValue(), Integer.toHexString(errorInfo.getCode().getValue()), errorInfo.getMsg());
        IPlayer.OnErrorListener onErrorListener = this.mOutErrorListener;
        if (onErrorListener != null) {
            onErrorListener.onError(errorInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void advVideoPlayerInfo(InfoBean infoBean) {
        int value = infoBean.getCode().getValue();
        TrackInfo.Type type = TrackInfo.Type.TYPE_VOD;
        if (value == type.ordinal()) {
            this.mControlView.setCurrentQuality(type.name());
            TipsView tipsView = this.mTipsView;
            if (tipsView != null) {
                tipsView.hideNetLoadingTipView();
                return;
            }
            return;
        }
        if (infoBean.getCode() == InfoCode.BufferedPosition) {
            return;
        }
        if (infoBean.getCode() != InfoCode.CurrentPosition) {
            IPlayer.OnInfoListener onInfoListener = this.mOutInfoListener;
            if (onInfoListener != null) {
                onInfoListener.onInfo(infoBean);
                return;
            }
            return;
        }
        hideDanmakuAndMarquee();
        long extraValue = infoBean.getExtraValue();
        this.mAdvCurrentPosition = extraValue;
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            long j2 = this.mCurrentPosition;
            controlView.setAdvVideoPosition((int) (extraValue + j2 + this.mAdvTotalPosition), (int) j2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void advVideoPlayerLoadingBegin() {
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.showBufferLoadingTipView();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void advVideoPlayerLoadingEnd() {
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.hideBufferLoadingTipView();
            this.mTipsView.hideErrorTipView();
        }
        if (isPlaying()) {
            this.mTipsView.hideErrorTipView();
        }
        this.hasLoadEnd.put(this.mAdvVideoMediaInfo, Boolean.TRUE);
        this.vodPlayerLoadEndHandler.sendEmptyMessage(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void advVideoPlayerLoadingProgress(int i2) {
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.updateLoadingPercent(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void advVideoPlayerOnVideoRenderingStart() {
        ImageView imageView = this.mCoverView;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
        IPlayer.OnRenderingStartListener onRenderingStartListener = this.mOutFirstFrameStartListener;
        if (onRenderingStartListener != null) {
            onRenderingStartListener.onRenderingStart();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void advVideoPlayerPrepared() {
        MediaInfo mediaInfo;
        if (this.mAdvVideoView == null) {
            return;
        }
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.hideNetLoadingTipView();
        }
        AliPlayer advVideoAliyunVodPlayer = this.mAdvVideoView.getAdvVideoAliyunVodPlayer();
        if (advVideoAliyunVodPlayer == null || (mediaInfo = advVideoAliyunVodPlayer.getMediaInfo()) == null) {
            return;
        }
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null && this.mPlayerState == 2) {
            surfaceView.setVisibility(8);
        }
        AdvVideoView advVideoView = this.mAdvVideoView;
        if (advVideoView != null && this.mPlayerState == 2) {
            advVideoView.setSurfaceViewVisibility(0);
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.hide(ViewAction.HideType.Normal);
        }
        this.mAdvVideoMediaInfo = mediaInfo;
        this.mAdvDuration = mediaInfo.getDuration();
        if (isLocalVideo()) {
            this.preparePlay = true;
        }
        if (this.mAdvVideoCount == 0) {
            Message messageObtain = Message.obtain();
            messageObtain.what = 0;
            messageObtain.obj = this.mAdvVideoMediaInfo;
            this.mVodPlayerHandler.sendMessage(messageObtain);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void advVideoPlayerStateChanged(int i2) {
        this.mAdvVideoPlayerState = i2;
        if (i2 == 3) {
            ControlView controlView = this.mControlView;
            if (controlView != null) {
                controlView.setVisibility(8);
            }
            MarqueeView marqueeView = this.mMarqueeView;
            if (marqueeView != null) {
                marqueeView.stopFlip();
            }
            PlayerDanmakuView playerDanmakuView = this.mDanmakuView;
            if (playerDanmakuView != null) {
                playerDanmakuView.hide();
            }
            SurfaceView surfaceView = this.mSurfaceView;
            if (surfaceView != null) {
                surfaceView.setVisibility(8);
            }
            AdvVideoView advVideoView = this.mAdvVideoView;
            if (advVideoView != null) {
                advVideoView.setSurfaceViewVisibility(0);
            }
            AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
            if (aliyunRenderView != null) {
                aliyunRenderView.pause();
            }
        }
    }

    private void afterAdvVideoPlayerComplete() {
        IPlayer.OnCompletionListener onCompletionListener;
        SurfaceView surfaceView;
        this.mAdvTotalPosition += this.mAdvCurrentPosition;
        if (this.mAliyunRenderView != null && (surfaceView = this.mSurfaceView) != null) {
            surfaceView.setVisibility(0);
            AdvVideoView advVideoView = this.mAdvVideoView;
            if (advVideoView != null) {
                advVideoView.setSurfaceViewVisibility(8);
            }
            if (!this.needToSeek) {
                if (this.mCurrentIntentPlayVideo == AdvVideoView.IntentPlayVideo.MIDDLE_END_ADV_SEEK && this.mAdvVideoCount < 3) {
                    if (this.mAliyunRenderView != null) {
                        isAutoAccurate(this.mSourceDuration);
                        this.mAliyunRenderView.pause();
                    }
                    ControlView controlView = this.mControlView;
                    if (controlView != null) {
                        controlView.setAdvVideoPosition((int) (this.mSourceDuration + (this.mAdvDuration * 2)), (int) this.mCurrentPosition);
                    }
                    AdvVideoView advVideoView2 = this.mAdvVideoView;
                    if (advVideoView2 != null) {
                        advVideoView2.setAutoPlay(!this.mIsScreenCosting);
                        this.mAdvVideoView.optionPrepare();
                    }
                }
                if (this.mAdvVideoCount < 3) {
                    advVideoPlayer4gTips();
                }
            } else if (this.mAdvVideoCount < 3) {
                isAutoAccurate(this.mSeekToPosition - (this.mAdvDuration * 2));
                advVideoPlayer4gTips();
            }
        }
        ControlView controlView2 = this.mControlView;
        if (controlView2 != null) {
            controlView2.setTotalPosition(this.mAdvTotalPosition);
        }
        if (advStyleIsIncludeEnd() && this.mAdvVideoCount == 3 && (onCompletionListener = this.mOutCompletionListener) != null) {
            onCompletionListener.onCompletion();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changedToLandForwardScape(boolean z2) {
        if (z2) {
            changeScreenMode(AliyunScreenMode.Full, false);
            OnOrientationChangeListener onOrientationChangeListener = this.orientationChangeListener;
            if (onOrientationChangeListener != null) {
                onOrientationChangeListener.orientationChange(z2, this.mCurrentScreenMode);
            }
        }
    }

    private void changedToLandReverseScape(boolean z2) {
        if (z2) {
            changeScreenMode(AliyunScreenMode.Full, true);
            OnOrientationChangeListener onOrientationChangeListener = this.orientationChangeListener;
            if (onOrientationChangeListener != null) {
                onOrientationChangeListener.orientationChange(z2, this.mCurrentScreenMode);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changedToPortrait(boolean z2) {
        if (this.mIsFullScreenLocked) {
            return;
        }
        if (this.mCurrentScreenMode != AliyunScreenMode.Full) {
            AliyunScreenMode aliyunScreenMode = AliyunScreenMode.Small;
        } else if (getLockPortraitMode() == null && z2) {
            changeScreenMode(AliyunScreenMode.Small, false);
        }
        OnOrientationChangeListener onOrientationChangeListener = this.orientationChangeListener;
        if (onOrientationChangeListener != null) {
            onOrientationChangeListener.orientationChange(z2, this.mCurrentScreenMode);
        }
    }

    private void checkAdvVideoSeek(int i2) {
        this.needToSeek = false;
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            AdvVideoView.IntentPlayVideo intentPlayVideo = controlView.getIntentPlayVideo(controlView.getMutiSeekBarCurrentProgress(), i2);
            Log.e(TAG, "checkAdvVideoSeek: intentPlayVideo = " + intentPlayVideo);
            this.mCurrentIntentPlayVideo = intentPlayVideo;
            switch (AnonymousClass27.$SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$AdvVideoView$IntentPlayVideo[intentPlayVideo.ordinal()]) {
                case 1:
                    if (this.mAliyunRenderView != null) {
                        this.mSourceSeekToPosition = 0;
                        isAutoAccurate(0);
                    }
                    ControlView controlView2 = this.mControlView;
                    if (controlView2 != null) {
                        controlView2.setAdvVideoPosition(0, 0);
                    }
                    this.mAdvTotalPosition = 0L;
                    this.mAdvVideoCount = 0;
                    startAdvVideo();
                    break;
                case 2:
                    if (this.mAliyunRenderView != null) {
                        int i3 = (int) (this.mSourceDuration / 2);
                        this.mSourceSeekToPosition = i3;
                        isAutoAccurate(i3);
                    }
                    ControlView controlView3 = this.mControlView;
                    if (controlView3 != null) {
                        controlView3.setAdvVideoPosition((int) (this.mAdvDuration + (this.mSourceDuration / 2)), this.mSourceSeekToPosition);
                    }
                    this.mAdvTotalPosition = this.mAdvDuration;
                    this.mAdvVideoCount = 1;
                    startAdvVideo();
                    break;
                case 3:
                    ControlView controlView4 = this.mControlView;
                    if (controlView4 != null) {
                        long j2 = this.mSourceDuration;
                        long j3 = this.mAdvDuration;
                        int i4 = (int) ((j3 * 2) + j2);
                        this.mSourceSeekToPosition = i4;
                        controlView4.setAdvVideoPosition((int) (j2 + (j3 * 2)), i4);
                    }
                    this.mAdvTotalPosition = this.mAdvDuration * 2;
                    this.mAdvVideoCount = 2;
                    startAdvVideo();
                    break;
                case 4:
                    this.needToSeek = true;
                    if (this.mAliyunRenderView != null) {
                        int i5 = (int) (this.mSourceDuration / 2);
                        this.mSourceSeekToPosition = i5;
                        isAutoAccurate(i5);
                    }
                    ControlView controlView5 = this.mControlView;
                    if (controlView5 != null) {
                        controlView5.setAdvVideoPosition((int) (this.mAdvDuration + (this.mSourceDuration / 2)), this.mSourceSeekToPosition);
                    }
                    this.mAdvTotalPosition = this.mAdvDuration;
                    this.mAdvVideoCount = 1;
                    startAdvVideo();
                    break;
                case 5:
                    this.needToSeek = false;
                    if (this.mAliyunRenderView != null) {
                        int i6 = (int) (this.mSourceDuration / 2);
                        this.mSourceSeekToPosition = i6;
                        isAutoAccurate(i6);
                    }
                    ControlView controlView6 = this.mControlView;
                    if (controlView6 != null) {
                        controlView6.setAdvVideoPosition((int) (this.mAdvDuration + (this.mSourceDuration / 2)), this.mSourceSeekToPosition);
                    }
                    this.mAdvTotalPosition = this.mAdvDuration;
                    this.mAdvVideoCount = 1;
                    startAdvVideo();
                    break;
                case 6:
                    if (this.mAliyunRenderView != null) {
                        long j4 = i2;
                        long j5 = this.mAdvDuration;
                        this.mSourceSeekToPosition = (int) (j4 - j5);
                        isAutoAccurate(j4 - j5);
                    }
                    ControlView controlView7 = this.mControlView;
                    if (controlView7 != null) {
                        controlView7.setAdvVideoPosition(i2, this.mSourceSeekToPosition);
                    }
                    this.mAdvTotalPosition = this.mAdvDuration;
                    this.mAdvVideoCount = 1;
                    break;
                case 7:
                    realySeekToFunction(i2);
                    break;
                default:
                    realySeekToFunction(i2);
                    break;
            }
        }
    }

    private void clearAllSource() {
        this.mAliyunPlayAuth = null;
        this.mAliyunVidSts = null;
        this.mAliyunLocalSource = null;
        this.mAliyunVidMps = null;
        this.mAliyunLiveSts = null;
    }

    private String getPostUrl(String str) {
        UrlSource urlSource = this.mAliyunLocalSource;
        String coverPath = urlSource != null ? urlSource.getCoverPath() : str;
        return TextUtils.isEmpty(coverPath) ? str : coverPath;
    }

    private String getTitle(String str) {
        String title;
        UrlSource urlSource = this.mAliyunLocalSource;
        if (urlSource != null) {
            title = urlSource.getTitle();
        } else {
            VidAuth vidAuth = this.mAliyunPlayAuth;
            if (vidAuth != null) {
                title = vidAuth.getTitle();
            } else {
                VidSts vidSts = this.mAliyunVidSts;
                title = vidSts != null ? vidSts.getTitle() : str;
            }
        }
        return TextUtils.isEmpty(title) ? str : title;
    }

    private void hideDanmakuAndMarquee() {
        PlayerDanmakuView playerDanmakuView = this.mDanmakuView;
        if (playerDanmakuView != null && playerDanmakuView.isShown()) {
            this.mDanmakuView.hide();
        }
        MarqueeView marqueeView = this.mMarqueeView;
        if (marqueeView == null || !marqueeView.isStart()) {
            return;
        }
        this.mMarqueeView.stopFlip();
    }

    private void hideErrorTipView() {
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.hideErrorTipView();
        }
    }

    private void hideGestureAndControlViews() {
        GestureView gestureView = this.mGestureView;
        if (gestureView != null) {
            gestureView.hide(ViewAction.HideType.Normal);
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.hide(ViewAction.HideType.Normal);
        }
    }

    private void hideSystemUI() {
        setSystemUiVisibility(R2.color.umeng_socialize_text_share_content);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideThumbnailView() {
        ThumbnailView thumbnailView = this.mThumbnailView;
        if (thumbnailView != null) {
            thumbnailView.hideThumbnailView();
        }
    }

    private void initAdvVideoView() {
        AdvVideoView advVideoView = new AdvVideoView(getContext());
        this.mAdvVideoView = advVideoView;
        addSubView(advVideoView);
        this.mAdvVideoView.setOutPreparedListener(new VideoPlayerPreparedListener(this, true));
        this.mAdvVideoView.setOutOnLoadingStatusListener(new VideoPlayerLoadingStatusListener(this, true));
        this.mAdvVideoView.setOutOnStateChangedListener(new VideoPlayerStateChangedListener(this, true));
        this.mAdvVideoView.setOutOnCompletionListener(new VideoPlayerCompletionListener(this, true));
        this.mAdvVideoView.setOutOnInfoListener(new VideoPlayerInfoListener(this, true));
        this.mAdvVideoView.setOutOnErrorListener(new VideoPlayerErrorListener(this, true));
        this.mAdvVideoView.setOutOnRenderingStartListener(new VideoPlayerRenderingStartListener(this, true));
        this.mAdvVideoView.setOnBackImageViewClickListener(new VideoPlayerAdvBackImageViewListener(this));
    }

    private void initAliVcPlayer() {
        AliyunRenderView aliyunRenderView = new AliyunRenderView(getContext());
        this.mAliyunRenderView = aliyunRenderView;
        addSubView(aliyunRenderView);
        this.mAliyunRenderView.setSurfaceType(AliyunRenderView.SurfaceType.SURFACE_VIEW);
        this.mAliyunRenderView.setOnPreparedListener(new VideoPlayerPreparedListener(this, false));
        this.mAliyunRenderView.setOnErrorListener(new VideoPlayerErrorListener(this, false));
        this.mAliyunRenderView.setOnLoadingStatusListener(new VideoPlayerLoadingStatusListener(this, false));
        this.mAliyunRenderView.setOnTrackReadyListenenr(new VideoPlayerOnTrackReadyListenner(this));
        this.mAliyunRenderView.setOnStateChangedListener(new VideoPlayerStateChangedListener(this, false));
        this.mAliyunRenderView.setOnCompletionListener(new VideoPlayerCompletionListener(this, false));
        this.mAliyunRenderView.setOnInfoListener(new VideoPlayerInfoListener(this, false));
        this.mAliyunRenderView.setOnRenderingStartListener(new VideoPlayerRenderingStartListener(this, false));
        this.mAliyunRenderView.setOnTrackChangedListener(new VideoPlayerTrackChangedListener(this));
        this.mAliyunRenderView.setOnSubtitleDisplayListener(new VideoPlayerSubtitleDeisplayListener(this));
        this.mAliyunRenderView.setOnSeekCompleteListener(new VideoPlayerOnSeekCompleteListener(this));
        this.mAliyunRenderView.setOnSnapShotListener(new VideoPlayerOnSnapShotListener(this));
        this.mAliyunRenderView.setOnSeiDataListener(new VideoPlayerOnSeiDataListener(this));
        this.mAliyunRenderView.setOnVerifyTimeExpireCallback(new VideoPlayerOnVerifyStsCallback(this));
    }

    private void initControlView() {
        final ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.alivc_playstate_play);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AliyunVodPlayerView.this.switchPlayerState();
                if (AliyunVodPlayerView.this.mPlayerState == 3) {
                    imageView.setImageResource(R.drawable.alivc_playstate_pause);
                } else {
                    imageView.setImageResource(R.drawable.alivc_playstate_play);
                }
            }
        });
        new RelativeLayout.LayoutParams(-2, -2).addRule(13);
        ControlView controlView = new ControlView(getContext());
        this.mControlView = controlView;
        controlView.setLandScapeActionListener(new ControlView.OnLandScapeActionListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.6
            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnLandScapeActionListener
            public void onChapterClick() {
                if (AliyunVodPlayerView.this.mLandScapeActionListener != null) {
                    AliyunVodPlayerView.this.mLandScapeActionListener.onChapterClick();
                }
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnLandScapeActionListener
            public void onCollectClick() {
                if (AliyunVodPlayerView.this.mLandScapeActionListener != null) {
                    AliyunVodPlayerView.this.mLandScapeActionListener.onCollectClick();
                }
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnLandScapeActionListener
            public void onCommentClick() {
                if (AliyunVodPlayerView.this.mLandScapeActionListener != null) {
                    AliyunVodPlayerView.this.mLandScapeActionListener.onCommentClick();
                }
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnLandScapeActionListener
            public void onKnowledgePointClick() {
                if (AliyunVodPlayerView.this.mLandScapeActionListener != null) {
                    AliyunVodPlayerView.this.mLandScapeActionListener.onKnowledgePointClick();
                }
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnLandScapeActionListener
            public void onNoteClick() {
                if (AliyunVodPlayerView.this.mLandScapeActionListener != null) {
                    AliyunVodPlayerView.this.mAliyunRenderView.getAliPlayer().snapshot();
                }
            }
        });
        addSubView(this.mControlView);
        this.mControlView.setOnPlayStateClickListener(new ControlView.OnPlayStateClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.7
            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnPlayStateClickListener
            public void onPlayStateClick() {
                AliyunVodPlayerView.this.switchPlayerState();
            }
        });
        this.mControlView.setOnSeekListener(new ControlView.OnSeekListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.8
            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnSeekListener
            public void onProgressChanged(int i2) {
                AliyunVodPlayerView.this.requestBitmapByPosition(i2);
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnSeekListener
            public void onSeekEnd(int i2) {
                if (AliyunVodPlayerView.this.mControlView != null) {
                    AliyunVodPlayerView.this.mControlView.setVideoPosition(i2);
                }
                if (AliyunVodPlayerView.this.isCompleted) {
                    AliyunVodPlayerView.this.inSeek = false;
                    return;
                }
                if (!AliyunVodPlayerView.this.mIsScreenCosting) {
                    AliyunVodPlayerView.this.seekTo(i2);
                }
                if (AliyunVodPlayerView.this.onSeekStartListener != null) {
                    AliyunVodPlayerView.this.onSeekStartListener.onSeekStart(i2);
                }
                AliyunVodPlayerView.this.hideThumbnailView();
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnSeekListener
            public void onSeekStart(int i2) {
                AliyunVodPlayerView.this.inSeek = true;
                AliyunVodPlayerView.this.mSeekToCurrentPlayerPosition = i2;
                if (AliyunVodPlayerView.this.mThumbnailPrepareSuccess) {
                    AliyunVodPlayerView.this.showThumbnailView();
                }
            }
        });
        this.mControlView.setOnTrackInfoClickListener(new ControlView.OnTrackInfoClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.9
            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnTrackInfoClickListener
            public void onAudioClick(List<TrackInfo> list) {
                if (AliyunVodPlayerView.this.mOutOnTrackInfoClickListener != null) {
                    AliyunVodPlayerView.this.mOutOnTrackInfoClickListener.onAudioClick(list);
                }
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnTrackInfoClickListener
            public void onBitrateClick(List<TrackInfo> list) {
                if (AliyunVodPlayerView.this.mOutOnTrackInfoClickListener != null) {
                    AliyunVodPlayerView.this.mOutOnTrackInfoClickListener.onBitrateClick(list);
                }
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnTrackInfoClickListener
            public void onDefinitionClick(List<TrackInfo> list) {
                if (AliyunVodPlayerView.this.mOutOnTrackInfoClickListener != null) {
                    AliyunVodPlayerView.this.mOutOnTrackInfoClickListener.onDefinitionClick(list);
                }
                List<TrackInfo> definitionTrackInfoList = AliyunVodPlayerView.this.mControlView.getDefinitionTrackInfoList();
                if (definitionTrackInfoList != null) {
                    definitionTrackInfoList.size();
                }
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnTrackInfoClickListener
            public void onSubtitleClick(List<TrackInfo> list) {
                if (AliyunVodPlayerView.this.mOutOnTrackInfoClickListener != null) {
                    AliyunVodPlayerView.this.mOutOnTrackInfoClickListener.onSubtitleClick(list);
                }
            }
        });
        this.mControlView.setOnScreenLockClickListener(new ControlView.OnScreenLockClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.10
            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnScreenLockClickListener
            public void onClick() {
                AliyunVodPlayerView.this.lockScreen(!r0.mIsFullScreenLocked);
            }
        });
        this.mControlView.setOnScreenModeClickListener(new ControlView.OnScreenModeClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.11
            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnScreenModeClickListener
            public void onClick() {
                AliyunVodPlayerView aliyunVodPlayerView = AliyunVodPlayerView.this;
                if (aliyunVodPlayerView.mFullOrigrinClickIml == null) {
                    AliyunScreenMode aliyunScreenMode = aliyunVodPlayerView.mCurrentScreenMode;
                    AliyunScreenMode aliyunScreenMode2 = AliyunScreenMode.Small;
                    if (aliyunScreenMode == aliyunScreenMode2) {
                        AliyunVodPlayerView.this.changedToLandForwardScape(true);
                    } else {
                        AliyunVodPlayerView.this.changedToPortrait(true);
                    }
                    if (AliyunVodPlayerView.this.mCurrentScreenMode == AliyunScreenMode.Full) {
                        AliyunVodPlayerView.this.mControlView.showMoreButton();
                    } else if (AliyunVodPlayerView.this.mCurrentScreenMode == aliyunScreenMode2) {
                        AliyunVodPlayerView.this.mControlView.hideMoreButton();
                    }
                } else if (aliyunVodPlayerView.mCurrentScreenMode == AliyunScreenMode.Small) {
                    AliyunVodPlayerView.this.mFullOrigrinClickIml.mFullOrigrinClick(0);
                } else {
                    AliyunVodPlayerView.this.mFullOrigrinClickIml.mFullOrigrinClick(1);
                }
                if (AliyunVodPlayerView.this.mControlView != null) {
                    AliyunVodPlayerView.this.mControlView.screenModeChange();
                }
                AliyunVodPlayerView.this.getScreenMode();
                AliyunScreenMode aliyunScreenMode3 = AliyunScreenMode.Full;
            }
        });
        this.mControlView.setOnBackClickListener(new ControlView.OnBackClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.12
            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnBackClickListener
            public void onClick() {
                if (AliyunVodPlayerView.this.mCurrentScreenMode == AliyunScreenMode.Full) {
                    if (!AliyunVodPlayerView.this.isLocalSource()) {
                        AliyunVodPlayerView aliyunVodPlayerView = AliyunVodPlayerView.this;
                        if (aliyunVodPlayerView.mFullOrigrinClickIml != null) {
                            Context context = aliyunVodPlayerView.getContext();
                            if (context instanceof Activity) {
                                ((Activity) context).finish();
                                return;
                            }
                            return;
                        }
                        aliyunVodPlayerView.changeScreenMode(AliyunScreenMode.Small, false);
                    } else if (AliyunVodPlayerView.this.orientationChangeListener != null) {
                        AliyunVodPlayerView.this.orientationChangeListener.orientationChange(false, AliyunScreenMode.Small);
                    }
                } else if (AliyunVodPlayerView.this.mCurrentScreenMode == AliyunScreenMode.Small) {
                    Context context2 = AliyunVodPlayerView.this.getContext();
                    if (context2 instanceof Activity) {
                        ((Activity) context2).finish();
                    }
                }
                if (AliyunVodPlayerView.this.mCurrentScreenMode != AliyunScreenMode.Small || AliyunVodPlayerView.this.mControlView == null) {
                    return;
                }
                AliyunVodPlayerView.this.mControlView.hideMoreButton();
            }
        });
        this.mControlView.setOnShowMoreClickListener(new ControlView.OnShowMoreClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.13
            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnShowMoreClickListener
            public void showMore() {
                if (AliyunVodPlayerView.this.mOutOnShowMoreClickListener != null) {
                    AliyunVodPlayerView.this.mOutOnShowMoreClickListener.showMore();
                }
            }
        });
        this.mControlView.setOnScreenShotClickListener(new ControlView.OnScreenShotClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.14
            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnScreenShotClickListener
            public void onScreenShotClick() {
                if (AliyunVodPlayerView.this.mIsFullScreenLocked) {
                    return;
                }
                AliyunVodPlayerView.this.snapShot();
            }
        });
        this.mControlView.setOnScreenRecoderClickListener(new ControlView.OnScreenRecoderClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.15
            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnScreenRecoderClickListener
            public void onScreenRecoderClick() {
                boolean unused = AliyunVodPlayerView.this.mIsFullScreenLocked;
            }
        });
        this.mControlView.setOnInputDanmakuClickListener(new ControlView.OnInputDanmakuClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.16
            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnInputDanmakuClickListener
            public void onInputDanmakuClick() {
                AliyunVodPlayerView.this.showInputDanmakuClick();
                AliyunVodPlayerView.this.pause();
            }
        });
        this.mControlView.setOnDLNAControlListener(new ControlView.OnDLNAControlListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.17
            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnDLNAControlListener
            public void onChangeQuality() {
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnDLNAControlListener
            public void onExit() {
                AliyunVodPlayerView.this.mIsScreenCosting = false;
                if (AliyunVodPlayerView.this.mAdvPictureView != null) {
                    AliyunVodPlayerView.this.mAdvPictureView.hideAll();
                }
                if (AliyunVodPlayerView.this.mControlView != null) {
                    AliyunVodPlayerView.this.mControlView.exitScreenCost();
                    AliyunVodPlayerView.this.mControlView.setInScreenCosting(AliyunVodPlayerView.this.mIsScreenCosting);
                    if (GlobalPlayerConfig.IS_VIDEO) {
                        AliyunVodPlayerView.this.mControlView.hideNativeSeekBar();
                    } else {
                        AliyunVodPlayerView.this.mControlView.showNativeSeekBar();
                    }
                }
                AliyunVodPlayerView aliyunVodPlayerView = AliyunVodPlayerView.this;
                aliyunVodPlayerView.seekTo((int) aliyunVodPlayerView.mCurrentPosition);
            }
        });
        this.mControlView.setOnDotViewClickListener(new ControlView.OnDotViewClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.18
            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnDotViewClickListener
            public void onDotViewClick(int i2, int i3, DotView dotView) {
                if (AliyunVodPlayerView.this.mOnDotViewClickListener != null) {
                    AliyunVodPlayerView.this.mOnDotViewClickListener.onDotViewClick(i2, i3, dotView);
                }
            }
        });
        this.mControlView.setOnControlViewHideListener(new ControlView.OnControlViewHideListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.19
            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnControlViewHideListener
            public void onControlViewHide() {
                if (AliyunVodPlayerView.this.mOnControlViewHideListener != null) {
                    AliyunVodPlayerView.this.mOnControlViewHideListener.onControlViewHide();
                }
            }
        });
    }

    private void initCoverView() {
        ImageView imageView = new ImageView(getContext());
        this.mCoverView = imageView;
        imageView.setId(R.id.custom_id_min);
        addSubView(this.mCoverView);
    }

    private void initDanmaku() {
        PlayerDanmakuView playerDanmakuView = new PlayerDanmakuView(getContext());
        this.mDanmakuView = playerDanmakuView;
        playerDanmakuView.hide();
        addSubViewBelow(this.mDanmakuView, this.mMarqueeView);
    }

    private void initGestureDialogManager() {
        Context context = getContext();
        if (context instanceof Activity) {
            this.mGestureDialogManager = new GestureDialogManager((Activity) context);
        }
    }

    private void initGestureView() {
        GestureView gestureView = new GestureView(getContext());
        this.mGestureView = gestureView;
        addSubView(gestureView);
        this.mGestureView.setMultiWindow(this.mIsInMultiWindow);
        this.mGestureView.setOnGestureListener(new GestureView.GestureListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.22
            @Override // com.aliyun.player.alivcplayerexpand.view.gesture.GestureView.GestureListener
            public void onDoubleTap() {
                if (AliyunVodPlayerView.this.tryWatchComplete || AliyunVodPlayerView.this.mIsScreenCosting) {
                    return;
                }
                if (!GlobalPlayerConfig.IS_TRAILER || AliyunVodPlayerView.this.mCurrentPosition < AliyunVodPlayerView.TRAILER) {
                    if (GlobalPlayerConfig.IS_TRAILER && AliyunVodPlayerView.this.mAdvVideoPlayerState == 3) {
                        return;
                    }
                    if (AliyunVodPlayerView.this.preparePlay || AliyunVodPlayerView.this.isLocalVideo()) {
                        AliyunVodPlayerView.this.switchPlayerState();
                    }
                }
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.gesture.GestureView.GestureListener
            public void onGestureEnd() {
                if (!AliyunVodPlayerView.this.preparePlay || AliyunVodPlayerView.this.tryWatchComplete || AliyunVodPlayerView.this.mGestureDialogManager == null) {
                    return;
                }
                int videoPosition = AliyunVodPlayerView.this.mControlView.getVideoPosition();
                if (videoPosition >= AliyunVodPlayerView.this.mAliyunRenderView.getDuration()) {
                    videoPosition = (int) (AliyunVodPlayerView.this.mAliyunRenderView.getDuration() - 1000);
                }
                if (videoPosition <= 0) {
                    videoPosition = 0;
                }
                if (AliyunVodPlayerView.this.mThumbnailView != null && AliyunVodPlayerView.this.inSeek) {
                    AliyunVodPlayerView.this.seekTo(videoPosition);
                    AliyunVodPlayerView.this.inSeek = false;
                    if (AliyunVodPlayerView.this.mThumbnailView.isShown()) {
                        AliyunVodPlayerView.this.hideThumbnailView();
                    }
                }
                if (AliyunVodPlayerView.this.mControlView != null) {
                    AliyunVodPlayerView.this.mControlView.openAutoHide();
                    if (AliyunVodPlayerView.this.guestureSeekView.getVisibility() != 8) {
                        AliyunVodPlayerView.this.guestureSeekView.setVisibility(8);
                    }
                }
                AliyunVodPlayerView.this.mGestureDialogManager.dismissBrightnessDialog();
                AliyunVodPlayerView.this.mGestureDialogManager.dismissVolumeDialog();
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.gesture.GestureView.GestureListener
            public void onHorizontalDistance(float f2, float f3) {
                int targetPosition;
                if (AliyunVodPlayerView.this.mIsScreenCosting) {
                    return;
                }
                AliyunVodPlayerView aliyunVodPlayerView = AliyunVodPlayerView.this;
                if (aliyunVodPlayerView.mAliyunRenderView == null || aliyunVodPlayerView.tryWatchComplete) {
                    return;
                }
                if (AliyunVodPlayerView.this.mControlView.getVisibility() == 0) {
                    AliyunVodPlayerView.this.mControlView.removeHideCallBack();
                }
                long duration = AliyunVodPlayerView.this.mAliyunRenderView.getDuration();
                long j2 = AliyunVodPlayerView.this.mCurrentPosition;
                if (AliyunVodPlayerView.this.mPlayerState == 2 || AliyunVodPlayerView.this.mPlayerState == 4 || AliyunVodPlayerView.this.mPlayerState == 3) {
                    targetPosition = AliyunVodPlayerView.this.getTargetPosition(duration, j2, (((long) (f3 - f2)) * duration) / AliyunVodPlayerView.this.getWidth());
                } else {
                    targetPosition = 0;
                }
                if (AliyunVodPlayerView.this.mControlView != null) {
                    AliyunVodPlayerView.this.inSeek = true;
                    AliyunVodPlayerView.this.mControlView.setVideoPosition(targetPosition);
                    if (AliyunVodPlayerView.this.guestureSeekView.getVisibility() != 0) {
                        AliyunVodPlayerView.this.guestureSeekView.setVisibility(0);
                    }
                    if (AliyunVodPlayerView.this.getMediaInfo() != null) {
                        AliyunVodPlayerView.this.tvTotal.setText("/" + TimeFormater.formatMs(AliyunVodPlayerView.this.getMediaInfo().getDuration()));
                    }
                    AliyunVodPlayerView.this.tvCurrent.setText(TimeFormater.formatMs(targetPosition));
                    if (AliyunVodPlayerView.this.mIsScreenCosting || !AliyunVodPlayerView.this.mThumbnailPrepareSuccess) {
                        return;
                    }
                    AliyunVodPlayerView.this.requestBitmapByPosition(targetPosition);
                    AliyunVodPlayerView.this.showThumbnailView();
                }
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.gesture.GestureView.GestureListener
            public void onLeftVerticalDistance(float f2, float f3) {
                if (AliyunVodPlayerView.this.tryWatchComplete) {
                    return;
                }
                int height = (int) (((f3 - f2) * 100.0f) / AliyunVodPlayerView.this.getHeight());
                if ((AliyunVodPlayerView.this.preparePlay || AliyunVodPlayerView.this.isLocalVideo()) && AliyunVodPlayerView.this.mGestureDialogManager != null) {
                    GestureDialogManager gestureDialogManager = AliyunVodPlayerView.this.mGestureDialogManager;
                    AliyunVodPlayerView aliyunVodPlayerView = AliyunVodPlayerView.this;
                    gestureDialogManager.showBrightnessDialog(aliyunVodPlayerView, aliyunVodPlayerView.mScreenBrightness);
                    int iUpdateBrightnessDialog = AliyunVodPlayerView.this.mGestureDialogManager.updateBrightnessDialog(height);
                    if (AliyunVodPlayerView.this.mOnScreenBrightnessListener != null) {
                        AliyunVodPlayerView.this.mOnScreenBrightnessListener.onScreenBrightness(iUpdateBrightnessDialog);
                    }
                    AliyunVodPlayerView.this.mScreenBrightness = iUpdateBrightnessDialog;
                }
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.gesture.GestureView.GestureListener
            public void onLongPressTap() {
                if (AliyunVodPlayerView.this.isPlaying()) {
                    AliyunVodPlayerView aliyunVodPlayerView = AliyunVodPlayerView.this;
                    aliyunVodPlayerView.isLongPress = true;
                    aliyunVodPlayerView.mAliyunRenderView.setSpeed(2.0f);
                    if (AliyunVodPlayerView.this.fastForwardView.getVisibility() != 0) {
                        AliyunVodPlayerView.this.fastForwardView.setVisibility(0);
                    }
                }
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.gesture.GestureView.GestureListener
            public void onRightVerticalDistance(float f2, float f3) {
                double d3;
                if (AliyunVodPlayerView.this.tryWatchComplete) {
                    return;
                }
                if (AliyunVodPlayerView.this.preparePlay || AliyunVodPlayerView.this.isLocalVideo()) {
                    double streamVolume = AliyunVodPlayerView.this.audioManager.getStreamVolume(3);
                    double streamMaxVolume = AliyunVodPlayerView.this.audioManager.getStreamMaxVolume(3);
                    double d4 = f3 - f2;
                    double height = d4 / AliyunVodPlayerView.this.getHeight();
                    double d5 = 0.0d;
                    if (d4 > 0.0d) {
                        if (streamVolume > 0.0d) {
                            d3 = streamVolume - (height * streamVolume);
                            d5 = (float) d3;
                        }
                    } else if (streamVolume >= streamMaxVolume) {
                        d5 = streamMaxVolume;
                    } else {
                        d3 = streamVolume - ((streamMaxVolume - streamVolume) * height);
                        d5 = (float) d3;
                    }
                    if (AliyunVodPlayerView.this.mGestureDialogManager != null) {
                        AliyunVodPlayerView.this.mGestureDialogManager.showVolumeDialog(AliyunVodPlayerView.this, (float) ((streamVolume / streamMaxVolume) * 100.0d));
                        AliyunVodPlayerView.this.currentVolume = AliyunVodPlayerView.this.mGestureDialogManager.updateVolumeDialog((int) ((d5 / streamMaxVolume) * 100.0d));
                    }
                    AliyunVodPlayerView.this.audioManager.setStreamVolume(3, (int) d5, 1);
                }
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.gesture.GestureView.GestureListener
            public void onSingleTap() throws IllegalAccessException, IllegalArgumentException {
                if (AliyunVodPlayerView.this.mControlView != null) {
                    if (AliyunVodPlayerView.this.mAdvVideoPlayerState == 3 && GlobalPlayerConfig.IS_VIDEO) {
                        AliyunVodPlayerView.this.openAdvertisement();
                        return;
                    }
                    if (AliyunVodPlayerView.this.mIsScreenCosting) {
                        if (AliyunVodPlayerView.this.mOnScreenCostingSingleTagListener != null) {
                            AliyunVodPlayerView.this.mOnScreenCostingSingleTagListener.onScreenCostingSingleTag();
                        }
                    } else if (AliyunVodPlayerView.this.mControlView.getVisibility() == 0) {
                        AliyunVodPlayerView.this.mControlView.hide(ViewAction.HideType.Normal);
                    } else if (AliyunVodPlayerView.this.preparePlay || AliyunVodPlayerView.this.isLocalVideo()) {
                        AliyunVodPlayerView.this.mControlView.show();
                    }
                }
            }
        });
    }

    private void initGuideView() {
        GuideView guideView = new GuideView(getContext());
        this.mGuideView = guideView;
        addSubView(guideView);
    }

    private void initMarquee() {
        MarqueeView marqueeView = new MarqueeView(getContext());
        this.mMarqueeView = marqueeView;
        addSubViewHeightWrap(marqueeView);
    }

    private void initNetWatchdog() {
        NetWatchdog netWatchdog = new NetWatchdog(getContext());
        this.mNetWatchdog = netWatchdog;
        netWatchdog.setNetChangeListener(new MyNetChangeListener(this));
        this.mNetWatchdog.setNetConnectedListener(new MyNetConnectedListener(this));
    }

    private void initOrientationWatchdog() {
    }

    private void initQualityView() {
        QualityView qualityView = new QualityView(getContext());
        this.mQualityView = qualityView;
        addSubView(qualityView);
        this.mQualityView.setOnQualityClickListener(new QualityView.OnQualityClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.20
            @Override // com.aliyun.player.alivcplayerexpand.view.quality.QualityView.OnQualityClickListener
            public void onQualityClick(TrackInfo trackInfo) {
                AliyunVodPlayerView.this.mAliyunRenderView.selectTrack(trackInfo.getIndex());
            }
        });
    }

    private void initSpeedView() {
        SpeedView speedView = new SpeedView(getContext());
        this.mSpeedView = speedView;
        addSubView(speedView);
        this.mSpeedView.setOnSpeedClickListener(new SpeedView.OnSpeedClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.21
            @Override // com.aliyun.player.alivcplayerexpand.view.speed.SpeedView.OnSpeedClickListener
            public void onHide() {
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.speed.SpeedView.OnSpeedClickListener
            public void onSpeedClick(SpeedView.SpeedValue speedValue) {
                float f2 = 1.0f;
                if (speedValue != SpeedView.SpeedValue.Normal) {
                    if (speedValue == SpeedView.SpeedValue.OneQuartern) {
                        f2 = 1.25f;
                    } else if (speedValue == SpeedView.SpeedValue.OneHalf) {
                        f2 = 1.5f;
                    } else if (speedValue == SpeedView.SpeedValue.Twice) {
                        f2 = 2.0f;
                    }
                }
                AliyunRenderView aliyunRenderView = AliyunVodPlayerView.this.mAliyunRenderView;
                if (aliyunRenderView != null) {
                    aliyunRenderView.setSpeed(f2);
                }
                AliyunVodPlayerView.this.mSpeedView.setSpeed(speedValue);
            }
        });
    }

    private void initSubtitleView() {
        SubtitleView subtitleView = new SubtitleView(getContext());
        this.mSubtitleView = subtitleView;
        subtitleView.setId(R.id.alivc_player_subtitle);
        SubtitleView.DefaultValueBuilder defaultValueBuilder = new SubtitleView.DefaultValueBuilder();
        defaultValueBuilder.setLocation(64);
        defaultValueBuilder.setColor("#e7e7e7");
        this.mSubtitleView.setDefaultValue(defaultValueBuilder);
        addSubView(this.mSubtitleView);
        AssSubtitleView assSubtitleView = new AssSubtitleView(getContext());
        this.mAssSubtitleView = assSubtitleView;
        assSubtitleView.setId(R.id.cicada_player_ass_subtitle);
        addSubViewByCenter(this.mAssSubtitleView);
    }

    private void initThumbnailView() {
        ThumbnailView thumbnailView = new ThumbnailView(getContext());
        this.mThumbnailView = thumbnailView;
        thumbnailView.setVisibility(8);
        addSubViewByCenter(this.mThumbnailView);
        hideThumbnailView();
    }

    private void initTipsView() {
        TipsView tipsView = new TipsView(getContext());
        this.mTipsView = tipsView;
        tipsView.setOnTipClickListener(new TipsView.OnTipClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.3
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.OnTipClickListener
            public void onContinuePlay() {
                AliyunVodPlayerView.this.mIsOperatorPlay = true;
                AliyunVodPlayerView.this.preparePlay = true;
                AliyunVodPlayerView.this.mTipsView.hideAll();
                if (GlobalPlayerConfig.IS_VIDEO) {
                    AliyunRenderView aliyunRenderView = AliyunVodPlayerView.this.mAliyunRenderView;
                    if (aliyunRenderView != null) {
                        aliyunRenderView.start();
                    }
                    if (AliyunVodPlayerView.this.mControlView != null) {
                        AliyunVodPlayerView.this.mControlView.setHideType(ViewAction.HideType.Normal);
                    }
                    if (AliyunVodPlayerView.this.mGestureView != null) {
                        AliyunVodPlayerView.this.mGestureView.setVisibility(0);
                        AliyunVodPlayerView.this.mGestureView.setHideType(ViewAction.HideType.Normal);
                        return;
                    }
                    return;
                }
                if (AliyunVodPlayerView.this.mPlayerState != 0 && AliyunVodPlayerView.this.mPlayerState != 5 && AliyunVodPlayerView.this.mPlayerState != 7 && AliyunVodPlayerView.this.mPlayerState != 6) {
                    AliyunVodPlayerView.this.start();
                    return;
                }
                AliyunVodPlayerView.this.mAliyunRenderView.setAutoPlay(true);
                if (AliyunVodPlayerView.this.mAliyunPlayAuth != null) {
                    AliyunVodPlayerView aliyunVodPlayerView = AliyunVodPlayerView.this;
                    aliyunVodPlayerView.prepareAuth(aliyunVodPlayerView.mAliyunPlayAuth);
                    return;
                }
                if (AliyunVodPlayerView.this.mAliyunVidSts != null) {
                    AliyunVodPlayerView aliyunVodPlayerView2 = AliyunVodPlayerView.this;
                    aliyunVodPlayerView2.prepareVidsts(aliyunVodPlayerView2.mAliyunVidSts);
                    return;
                }
                if (AliyunVodPlayerView.this.mAliyunVidMps != null) {
                    AliyunVodPlayerView aliyunVodPlayerView3 = AliyunVodPlayerView.this;
                    aliyunVodPlayerView3.prepareMps(aliyunVodPlayerView3.mAliyunVidMps);
                } else if (AliyunVodPlayerView.this.mAliyunLocalSource != null) {
                    AliyunVodPlayerView aliyunVodPlayerView4 = AliyunVodPlayerView.this;
                    aliyunVodPlayerView4.prepareLocalSource(aliyunVodPlayerView4.mAliyunLocalSource);
                } else if (AliyunVodPlayerView.this.mAliyunLiveSts != null) {
                    AliyunVodPlayerView aliyunVodPlayerView5 = AliyunVodPlayerView.this;
                    aliyunVodPlayerView5.prepareLiveSts(aliyunVodPlayerView5.mAliyunLiveSts);
                }
            }

            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.OnTipClickListener
            public void onExit() {
                AliyunVodPlayerView.this.mTipsView.hideAll();
                if (AliyunVodPlayerView.this.mOutOnTipClickListener != null) {
                    AliyunVodPlayerView.this.mOutOnTipClickListener.onExit();
                }
            }

            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.OnTipClickListener
            public void onRefreshSts() {
                if (AliyunVodPlayerView.this.mOutTimeExpiredErrorListener != null) {
                    AliyunVodPlayerView.this.mOutTimeExpiredErrorListener.onTimeExpiredError();
                }
            }

            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.OnTipClickListener
            public void onReplay() {
                if (AliyunVodPlayerView.this.mOutOnTipClickListener != null) {
                    AliyunVodPlayerView.this.mOutOnTipClickListener.onReplay();
                }
                AliyunVodPlayerView.this.rePlay();
            }

            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.OnTipClickListener
            public void onRetryPlay(int i2) {
                if (AliyunVodPlayerView.this.mOutOnTipClickListener != null) {
                    AliyunVodPlayerView.this.mOutOnTipClickListener.onRetryPlay(i2);
                }
            }

            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.OnTipClickListener
            public void onStopPlay() {
                AliyunVodPlayerView.this.mTipsView.hideAll();
                AliyunVodPlayerView.this.stop();
                Context context = AliyunVodPlayerView.this.getContext();
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }

            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.OnTipClickListener
            public void onWait() {
                AliyunVodPlayerView.this.mTipsView.hideAll();
                AliyunVodPlayerView.this.mTipsView.showNetLoadingTipView();
            }
        });
        this.mTipsView.setOnTipsViewBackClickListener(new OnTipsViewBackClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.4
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.OnTipsViewBackClickListener
            public void onBackClick() {
                if (AliyunVodPlayerView.this.mOutOnTipsViewBackClickListener != null) {
                    AliyunVodPlayerView.this.mOutOnTipsViewBackClickListener.onBackClick();
                }
            }
        });
        addSubView(this.mTipsView);
    }

    private void initTrailersView() {
        TrailersView trailersView = new TrailersView(getContext());
        this.mTrailersView = trailersView;
        addSubView(trailersView);
        this.mTrailersView.trailerPlayTipsIsShow(true);
        this.mTrailersView.setOnTrailerViewClickListener(this.mOnTrailerViewClickListener);
        this.mTrailersView.setOnTrailerViewClickListener(new TrailersView.OnTrailerViewClickListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.2
            @Override // com.aliyun.player.alivcplayerexpand.view.trailers.TrailersView.OnTrailerViewClickListener
            public void onOpenVipClick() {
                if (AliyunVodPlayerView.this.mOnTrailerViewClickListener != null) {
                    AliyunVodPlayerView.this.mOnTrailerViewClickListener.onOpenVipClick();
                }
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.trailers.TrailersView.OnTrailerViewClickListener
            public void onTrailerPlayAgainClick() {
                if (AliyunVodPlayerView.this.mOnTrailerViewClickListener != null) {
                    AliyunVodPlayerView.this.mOnTrailerViewClickListener.onTrailerPlayAgainClick();
                }
                AliyunVodPlayerView.this.mTrailersView.hideAll();
            }
        });
    }

    private void initVideoView() {
        this.audioManager = (AudioManager) getContext().getSystemService("audio");
        View viewInflate = View.inflate(getContext(), R.layout.layout_gesture_seek, null);
        this.guestureSeekView = viewInflate;
        viewInflate.setVisibility(8);
        this.tvCurrent = (TextView) this.guestureSeekView.findViewById(R.id.tv_current);
        this.tvTotal = (TextView) this.guestureSeekView.findViewById(R.id.tv_total);
        this.mVodPlayerHandler = new VodPlayerHandler(this);
        initAliVcPlayer();
        initCoverView();
        initGestureView();
        initWaterMark();
        initMarquee();
        initTrailersView();
        initControlView();
        initAdvVideoView();
        initQualityView();
        initThumbnailView();
        initSpeedView();
        initGuideView();
        initTipsView();
        initNetWatchdog();
        initGestureDialogManager();
        initAdvPicture();
        hideGestureAndControlViews();
        initDanmaku();
        initSubtitleView();
        addFastForwardView();
        setTheme(Theme.Red);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.addRule(13, 0);
        addView(this.guestureSeekView, layoutParams);
    }

    private void initWaterMark() {
        ImageView imageView = new ImageView(getContext());
        this.mWaterMark = imageView;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.mWaterMark.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.alivc_watermark_icon));
        this.mWaterMark.setVisibility(8);
        addSubViewByWrap(this.mWaterMark);
    }

    private void isAutoAccurate(long j2) {
        if (GlobalPlayerConfig.PlayConfig.mEnableAccurateSeekModule) {
            this.mAliyunRenderView.seekTo(j2, IPlayer.SeekMode.Accurate);
        } else {
            this.mAliyunRenderView.seekTo(j2, IPlayer.SeekMode.Inaccurate);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isLocalSource() {
        UrlSource urlSource;
        if (GlobalPlayerConfig.PLAYTYPE.STS.equals(GlobalPlayerConfig.mCurrentPlayType) || GlobalPlayerConfig.PLAYTYPE.MPS.equals(GlobalPlayerConfig.mCurrentPlayType) || GlobalPlayerConfig.PLAYTYPE.AUTH.equals(GlobalPlayerConfig.mCurrentPlayType) || GlobalPlayerConfig.PLAYTYPE.DEFAULT.equals(GlobalPlayerConfig.mCurrentPlayType) || (urlSource = this.mAliyunLocalSource) == null || TextUtils.isEmpty(urlSource.getUri())) {
            return false;
        }
        return (GlobalPlayerConfig.PLAYTYPE.URL.equals(GlobalPlayerConfig.mCurrentPlayType) ? Uri.parse(this.mAliyunLocalSource.getUri()).getScheme() : null) == null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isLocalVideo() {
        UrlSource urlSource = this.mAliyunLocalSource;
        if (urlSource == null) {
            return false;
        }
        File file = new File(urlSource.getUri());
        return file.exists() && file.length() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void on4GToWifi() {
        if (this.mTipsView.isErrorShow()) {
            return;
        }
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.hideNetErrorTipView();
        }
        if (!this.initNetWatch) {
            reload();
        }
        this.initNetWatch = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAdvBackImageViewClickListener() {
        Context context = getContext();
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNetDisconnected() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSeiData(int i2, byte[] bArr) {
        IPlayer.OnSeiDataListener onSeiDataListener = this.mOutOnSeiDataListener;
        if (onSeiDataListener != null) {
            onSeiDataListener.onSeiData(i2, bArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSubtitleHeader(int i2, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mSubtitleTypeMap.put(Integer.valueOf(i2), AssHeader.SubtitleType.SubtitleTypeAss);
        this.mAssSubtitleView.setAssHeader(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSubtitleHide(int i2, long j2) {
        if (this.mSubtitleTypeMap.size() > 0 && this.mSubtitleTypeMap.get(Integer.valueOf(i2)) == AssHeader.SubtitleType.SubtitleTypeAss) {
            this.mAssSubtitleView.dismiss(j2);
            return;
        }
        this.mSubtitleView.dismiss(j2 + "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSubtitleShow(int i2, long j2, String str) {
        if (this.mSubtitleTypeMap.size() > 0 && this.mSubtitleTypeMap.get(Integer.valueOf(i2)) == AssHeader.SubtitleType.SubtitleTypeAss) {
            this.mAssSubtitleView.show(j2, str);
            return;
        }
        SubtitleView.Subtitle subtitle = new SubtitleView.Subtitle();
        subtitle.id = j2 + "";
        subtitle.content = str;
        this.mSubtitleView.show(subtitle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTrackReady(MediaInfo mediaInfo) {
        IPlayer.OnTrackReadyListener onTrackReadyListener = this.mOutOnTrackReadyListener;
        if (onTrackReadyListener != null) {
            onTrackReadyListener.onTrackReady(mediaInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AliPlayer.Status onVerifyAuth(VidAuth vidAuth) {
        AliPlayer.OnVerifyTimeExpireCallback onVerifyTimeExpireCallback = this.mOutOnVerifyTimeExpireCallback;
        return onVerifyTimeExpireCallback != null ? onVerifyTimeExpireCallback.onVerifyAuth(vidAuth) : AliPlayer.Status.Valid;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AliPlayer.Status onVerifySts(StsInfo stsInfo) {
        AliPlayer.OnVerifyTimeExpireCallback onVerifyTimeExpireCallback = this.mOutOnVerifyTimeExpireCallback;
        return onVerifyTimeExpireCallback != null ? onVerifyTimeExpireCallback.onVerifySts(stsInfo) : AliPlayer.Status.Valid;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onWifiTo4G() throws IllegalAccessException, IllegalArgumentException {
        TipsView tipsView;
        int i2;
        if (this.mTipsView.isErrorShow()) {
            return;
        }
        if (GlobalPlayerConfig.IS_VIDEO && ((i2 = this.mAdvVideoPlayerState) == 3 || i2 == 4)) {
            return;
        }
        if (!isLocalSource()) {
            if (this.mIsOperatorPlay) {
                ToastUtils.show(getContext(), R.string.alivc_operator_play);
            } else {
                pause();
            }
        }
        if (!this.initNetWatch) {
            reload();
        }
        if (!isLocalSource() && (tipsView = this.mTipsView) != null) {
            if (this.mIsOperatorPlay) {
                ToastUtils.show(getContext(), R.string.alivc_operator_play);
            } else {
                tipsView.hideAll();
                this.mTipsView.showNetChangeTipView();
                ControlView controlView = this.mControlView;
                if (controlView != null) {
                    ViewAction.HideType hideType = ViewAction.HideType.Normal;
                    controlView.setHideType(hideType);
                    this.mControlView.hide(hideType);
                }
                GestureView gestureView = this.mGestureView;
                if (gestureView != null) {
                    ViewAction.HideType hideType2 = ViewAction.HideType.Normal;
                    gestureView.setHideType(hideType2);
                    this.mGestureView.hide(hideType2);
                }
                AdvPictureView advPictureView = this.mAdvPictureView;
                if (advPictureView != null) {
                    advPictureView.hideAll();
                }
            }
        }
        this.initNetWatch = false;
    }

    private void playAdvVideo() {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView == null || this.mAdvVideoView == null) {
            return;
        }
        aliyunRenderView.pause();
        int advPlayerState = this.mAdvVideoView.getAdvPlayerState();
        if (advPlayerState == 4 || advPlayerState == 2 || advPlayerState == 3) {
            this.mAdvVideoView.optionStart();
        } else {
            this.mAdvVideoView.optionPrepare();
        }
    }

    private void preapreAdvVidSts(VidSts vidSts) {
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.showNetLoadingTipView();
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setIsMtsSource(false);
        }
        QualityView qualityView = this.mQualityView;
        if (qualityView != null) {
            qualityView.setIsMtsSource(false);
        }
        if (this.mAdvVideoView != null) {
            UrlSource urlSource = new UrlSource();
            urlSource.setUri(ADV_VIDEO_URL);
            this.mAdvVideoView.optionSetUrlSource(urlSource);
            this.mAdvVideoView.setAutoPlay(!this.mIsScreenCosting);
            this.mAdvVideoView.optionPrepare();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareAuth(VidAuth vidAuth) {
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.showNetLoadingTipView();
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setIsMtsSource(false);
        }
        QualityView qualityView = this.mQualityView;
        if (qualityView != null) {
            qualityView.setIsMtsSource(false);
        }
        this.mAliyunRenderView.setDataSource(vidAuth);
        this.mAliyunRenderView.prepare();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareLiveSts(LiveSts liveSts) {
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.showNetLoadingTipView();
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setIsMtsSource(false);
        }
        QualityView qualityView = this.mQualityView;
        if (qualityView != null) {
            qualityView.setIsMtsSource(false);
        }
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.setDataSource(liveSts);
            this.mAliyunRenderView.prepare();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareLocalSource(UrlSource urlSource) {
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.showNetLoadingTipView();
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setForceQuality(true);
            this.mControlView.setIsMtsSource(false);
            this.mControlView.hideMoreButton();
        }
        QualityView qualityView = this.mQualityView;
        if (qualityView != null) {
            qualityView.setIsMtsSource(false);
        }
        if (isLocalSource() && this.mIsNeedOnlyFullScreen) {
            changeScreenMode(AliyunScreenMode.Full, false);
        }
        if (urlSource != null && urlSource.getUri().startsWith("artc")) {
            Log.e(TAG, "artc setPlayerConfig");
            PlayerConfig playerConfig = this.mAliyunRenderView.getPlayerConfig();
            playerConfig.mMaxDelayTime = 1000;
            playerConfig.mHighBufferDuration = 10;
            playerConfig.mStartBufferDuration = 10;
            this.mAliyunRenderView.setPlayerConfig(playerConfig);
        }
        this.mAliyunRenderView.setAutoPlay(true);
        this.mAliyunRenderView.setDataSource(urlSource);
        this.mAliyunRenderView.prepare();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareMps(VidMps vidMps) {
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.showNetLoadingTipView();
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setIsMtsSource(false);
        }
        QualityView qualityView = this.mQualityView;
        if (qualityView != null) {
            qualityView.setIsMtsSource(false);
        }
        this.mAliyunRenderView.setDataSource(vidMps);
        this.mAliyunRenderView.prepare();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareVidsts(VidSts vidSts) {
        if (this.mTipsView != null && (!show4gTips() || this.preparePlay)) {
            this.mTipsView.showNetLoadingTipView();
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setIsMtsSource(false);
        }
        QualityView qualityView = this.mQualityView;
        if (qualityView != null) {
            qualityView.setIsMtsSource(false);
        }
        if (this.mAliyunRenderView != null) {
            if (GlobalPlayerConfig.IS_TRAILER) {
                VidPlayerConfigGen vidPlayerConfigGen = new VidPlayerConfigGen();
                vidPlayerConfigGen.addPlayerConfig("PlayDomain", PLAY_DOMAIN);
                vidPlayerConfigGen.setPreviewTime(TRAILER);
                vidSts.setPlayConfig(vidPlayerConfigGen);
            }
            this.mAliyunRenderView.setDataSource(vidSts);
            this.mAliyunRenderView.prepare();
        }
    }

    private void realySeekToFunction(int i2) {
        if (GlobalPlayerConfig.IS_VIDEO) {
            isAutoAccurate(i2 - (this.mAdvVideoCount * this.mAdvDuration));
        } else {
            isAutoAccurate(i2);
        }
        this.mAliyunRenderView.start();
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setPlayState(ControlView.PlayState.Playing);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestBitmapByPosition(int i2) {
        ThumbnailHelper thumbnailHelper = this.mThumbnailHelper;
        if (thumbnailHelper == null || !this.mThumbnailPrepareSuccess) {
            return;
        }
        thumbnailHelper.requestBitmapAtPosition(i2);
    }

    private void resumePlayerState() {
        if (this.mAliyunRenderView == null) {
            return;
        }
        AdvVideoView advVideoView = this.mAdvVideoView;
        if (advVideoView != null && GlobalPlayerConfig.IS_VIDEO) {
            if (this.mAdvVideoPlayerState == 4 || this.mCurrentPosition == 0) {
                advVideoView.optionStart();
                return;
            } else if (isLocalSource()) {
                reTry();
                return;
            } else {
                start();
                return;
            }
        }
        if (!isLocalSource() && NetWatchdog.is4GConnected(getContext()) && !this.mIsOperatorPlay && isPlaying()) {
            pause();
            return;
        }
        if (this.mSourceDuration <= 0 && this.mPlayerState == 5) {
            this.mAliyunRenderView.prepare();
        } else if (this.preparePlay || isLocalVideo()) {
            start();
        }
    }

    private void savePlayerState() {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView == null) {
            return;
        }
        if (this.mSourceDuration > 0) {
            pause();
        } else {
            this.mPlayerState = 5;
            aliyunRenderView.stop();
        }
    }

    private void setWaterMarkPosition(AliyunScreenMode aliyunScreenMode) {
        if (this.mWaterMark == null) {
            return;
        }
        int navigationBarHeight = ScreenUtils.getNavigationBarHeight(getContext());
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mWaterMark.getLayoutParams();
        int i2 = AnonymousClass27.$SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$WaterMarkRegion[WATER_MARK_REGION.ordinal()];
        if (i2 == 1) {
            Context context = getContext();
            AliyunScreenMode aliyunScreenMode2 = AliyunScreenMode.Full;
            marginLayoutParams.leftMargin = DensityUtil.dip2px(context, aliyunScreenMode == aliyunScreenMode2 ? navigationBarHeight / 2 : 20.0f);
            marginLayoutParams.topMargin = DensityUtil.dip2px(getContext(), aliyunScreenMode == aliyunScreenMode2 ? 20.0f : 10.0f);
        } else if (i2 == 2) {
            Context context2 = getContext();
            AliyunScreenMode aliyunScreenMode3 = AliyunScreenMode.Full;
            marginLayoutParams.leftMargin = DensityUtil.dip2px(context2, aliyunScreenMode == aliyunScreenMode3 ? navigationBarHeight / 2 : 20.0f);
            marginLayoutParams.bottomMargin = DensityUtil.dip2px(getContext(), aliyunScreenMode == aliyunScreenMode3 ? 20.0f : 10.0f);
        } else if (i2 == 3) {
            Context context3 = getContext();
            AliyunScreenMode aliyunScreenMode4 = AliyunScreenMode.Full;
            marginLayoutParams.rightMargin = DensityUtil.dip2px(context3, aliyunScreenMode == aliyunScreenMode4 ? navigationBarHeight / 2 : 20.0f);
            marginLayoutParams.topMargin = DensityUtil.dip2px(getContext(), aliyunScreenMode == aliyunScreenMode4 ? 20.0f : 10.0f);
        } else if (i2 != 4) {
            Context context4 = getContext();
            AliyunScreenMode aliyunScreenMode5 = AliyunScreenMode.Full;
            marginLayoutParams.rightMargin = DensityUtil.dip2px(context4, aliyunScreenMode == aliyunScreenMode5 ? navigationBarHeight / 2 : 20.0f);
            marginLayoutParams.topMargin = DensityUtil.dip2px(getContext(), aliyunScreenMode == aliyunScreenMode5 ? 20.0f : 10.0f);
        } else {
            Context context5 = getContext();
            AliyunScreenMode aliyunScreenMode6 = AliyunScreenMode.Full;
            marginLayoutParams.rightMargin = DensityUtil.dip2px(context5, aliyunScreenMode == aliyunScreenMode6 ? navigationBarHeight / 2 : 20.0f);
            marginLayoutParams.bottomMargin = DensityUtil.dip2px(getContext(), aliyunScreenMode == aliyunScreenMode6 ? 20.0f : 10.0f);
        }
        this.mWaterMark.setLayoutParams(marginLayoutParams);
    }

    private boolean show4gTips(VidSts vidSts) throws IllegalAccessException, IllegalArgumentException {
        if (isLocalSource() || !NetWatchdog.is4GConnected(getContext())) {
            return false;
        }
        if (this.mIsOperatorPlay) {
            ToastUtils.show(getContext(), R.string.alivc_operator_play);
            return false;
        }
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            this.preparePlay = false;
            tipsView.showNetChangeTipView();
            prepareVidsts(vidSts);
        }
        AdvPictureView advPictureView = this.mAdvPictureView;
        if (advPictureView == null) {
            return true;
        }
        advPictureView.hideAll();
        return true;
    }

    private void showDanmakuAndMarquee() {
        MarqueeView marqueeView;
        PlayerDanmakuView playerDanmakuView;
        if (this.mCurrentScreenMode == AliyunScreenMode.Small) {
            return;
        }
        if (GlobalPlayerConfig.IS_BARRAGE && (playerDanmakuView = this.mDanmakuView) != null) {
            playerDanmakuView.show();
        }
        if (!GlobalPlayerConfig.IS_MARQUEE || (marqueeView = this.mMarqueeView) == null) {
            return;
        }
        marqueeView.createAnimation();
        this.mMarqueeView.startFlip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showInputDanmakuClick() {
        OnSoftKeyHideListener onSoftKeyHideListener = this.mOnSoftKeyHideListener;
        if (onSoftKeyHideListener != null) {
            onSoftKeyHideListener.onClickPaint();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showThumbnailView() {
        ThumbnailView thumbnailView = this.mThumbnailView;
        if (thumbnailView != null) {
            thumbnailView.showThumbnailView();
            ImageView thumbnailImageView = this.mThumbnailView.getThumbnailImageView();
            if (thumbnailImageView != null) {
                ViewGroup.LayoutParams layoutParams = thumbnailImageView.getLayoutParams();
                int width = ScreenUtils.getWidth(getContext()) / 3;
                layoutParams.width = width;
                layoutParams.height = (width / 2) - DensityUtils.px2dip(getContext(), 10.0f);
                thumbnailImageView.setLayoutParams(layoutParams);
            }
        }
    }

    private void showVideoFunction() {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            this.mPlayerState = 5;
            aliyunRenderView.stop();
        }
        AdvVideoView advVideoView = this.mAdvVideoView;
        if (advVideoView != null) {
            advVideoView.optionStop();
            this.mAdvVideoView.isShowAdvVideoBackIamgeView(false);
            this.mAdvVideoView.isShowAdvVideoTipsTextView(false);
        }
        if (GlobalPlayerConfig.IS_WATERMARK) {
            this.mWaterMark.setVisibility(0);
        } else {
            this.mWaterMark.setVisibility(8);
        }
        if (GlobalPlayerConfig.IS_PICTRUE && !GlobalPlayerConfig.IS_VIDEO && !this.mIsScreenCosting) {
            AliyunRenderView aliyunRenderView2 = this.mAliyunRenderView;
            if (aliyunRenderView2 != null) {
                aliyunRenderView2.setAutoPlay(false);
            }
            ControlView controlView = this.mControlView;
            if (controlView != null) {
                controlView.hide(ViewAction.HideType.Normal);
            }
            AdvPictureView advPictureView = this.mAdvPictureView;
            if (advPictureView != null) {
                advPictureView.setVisibility(0);
                this.mAdvPictureView.showAll();
            }
            ControlView controlView2 = this.mControlView;
            if (controlView2 != null) {
                controlView2.showNativeSeekBar();
            }
            this.preparePlay = true;
            prepareVidsts(this.mAliyunVidSts);
            return;
        }
        AdvPictureView advPictureView2 = this.mAdvPictureView;
        if (advPictureView2 != null) {
            advPictureView2.hideAll();
            this.mAdvPictureView.cancel();
        }
        if (GlobalPlayerConfig.IS_VIDEO) {
            this.mAdvVideoCount = 0;
            VidSts vidSts = new VidSts();
            vidSts.setVid(VIDEO_ADV_VID);
            vidSts.setRegion(GlobalPlayerConfig.mRegion);
            vidSts.setAccessKeyId(GlobalPlayerConfig.mStsAccessKeyId);
            vidSts.setAccessKeySecret(GlobalPlayerConfig.mStsAccessKeySecret);
            vidSts.setSecurityToken(GlobalPlayerConfig.mStsSecurityToken);
            preapreAdvVidSts(vidSts);
            prepareVidsts(this.mAliyunVidSts);
            this.mAliyunRenderView.setAutoPlay(false);
            ControlView controlView3 = this.mControlView;
            if (controlView3 != null) {
                controlView3.hide(ViewAction.HideType.Normal);
            }
        } else {
            ControlView controlView4 = this.mControlView;
            if (controlView4 != null) {
                controlView4.showNativeSeekBar();
            }
        }
        if (GlobalPlayerConfig.IS_VIDEO) {
            return;
        }
        if (!this.isVideo) {
            this.preparePlay = true;
            prepareVidsts(this.mAliyunVidSts);
        } else {
            if (show4gTips(this.mAliyunVidSts)) {
                return;
            }
            this.preparePlay = true;
            prepareVidsts(this.mAliyunVidSts);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sourceVideoPlayerCompletion() {
        TrailersView trailersView;
        this.inSeek = false;
        if (this.mOutCompletionListener != null) {
            if (!GlobalPlayerConfig.IS_VIDEO || !advStyleIsIncludeEnd()) {
                if (!GlobalPlayerConfig.IS_TRAILER || (trailersView = this.mTrailersView) == null || this.mCurrentPosition < TRAILER * 1000) {
                    this.mOutCompletionListener.onCompletion();
                    return;
                } else {
                    trailersView.trailerPlayTipsIsShow(false);
                    return;
                }
            }
            boolean z2 = GlobalPlayerConfig.IS_TRAILER;
            if (z2 && this.mCurrentPosition < TRAILER * 1000) {
                startAdvVideo();
                return;
            }
            if (!z2) {
                this.mOutCompletionListener.onCompletion();
                return;
            }
            TrailersView trailersView2 = this.mTrailersView;
            if (trailersView2 == null || this.mCurrentPosition < TRAILER * 1000) {
                return;
            }
            trailersView2.trailerPlayTipsIsShow(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sourceVideoPlayerError(ErrorInfo errorInfo) {
        AdvPictureView advPictureView = this.mAdvPictureView;
        if (advPictureView != null) {
            advPictureView.cancel();
            this.mAdvPictureView.hideAll();
        }
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.hideAll();
        }
        lockScreen(false);
        showErrorTipView(errorInfo.getCode().getValue(), Integer.toHexString(errorInfo.getCode().getValue()), errorInfo.getMsg());
        IPlayer.OnErrorListener onErrorListener = this.mOutErrorListener;
        if (onErrorListener != null) {
            onErrorListener.onError(errorInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:41:0x009e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void sourceVideoPlayerInfo(com.aliyun.player.bean.InfoBean r14) {
        /*
            r13 = this;
            com.aliyun.player.bean.InfoCode r0 = r14.getCode()
            com.aliyun.player.bean.InfoCode r1 = com.aliyun.player.bean.InfoCode.AutoPlayStart
            if (r0 != r1) goto L1a
            com.aliyun.player.alivcplayerexpand.view.control.ControlView r0 = r13.mControlView
            if (r0 == 0) goto L11
            com.aliyun.player.alivcplayerexpand.view.control.ControlView$PlayState r1 = com.aliyun.player.alivcplayerexpand.view.control.ControlView.PlayState.Playing
            r0.setPlayState(r1)
        L11:
            com.aliyun.player.alivcplayerexpand.listener.OnAutoPlayListener r0 = r13.mOutAutoPlayListener
            if (r0 == 0) goto Lbb
            r0.onAutoPlayStarted()
            goto Lbb
        L1a:
            com.aliyun.player.bean.InfoCode r0 = r14.getCode()
            com.aliyun.player.bean.InfoCode r1 = com.aliyun.player.bean.InfoCode.BufferedPosition
            if (r0 != r1) goto L30
            long r0 = r14.getExtraValue()
            r13.mVideoBufferedPosition = r0
            com.aliyun.player.alivcplayerexpand.view.control.ControlView r2 = r13.mControlView
            int r0 = (int) r0
            r2.setVideoBufferPosition(r0)
            goto Lbb
        L30:
            com.aliyun.player.bean.InfoCode r0 = r14.getCode()
            com.aliyun.player.bean.InfoCode r1 = com.aliyun.player.bean.InfoCode.CurrentPosition
            if (r0 != r1) goto Lbb
            long r0 = r14.getExtraValue()
            r13.mCurrentPosition = r0
            com.aliyun.player.alivcplayerexpand.view.function.PlayerDanmakuView r2 = r13.mDanmakuView
            if (r2 == 0) goto L46
            int r0 = (int) r0
            r2.setCurrentPosition(r0)
        L46:
            com.aliyun.player.alivcplayerexpand.view.control.ControlView r0 = r13.mControlView
            if (r0 == 0) goto L4e
            r1 = 1
            r0.setOtherEnable(r1)
        L4e:
            boolean r0 = com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig.IS_VIDEO
            r1 = 3
            if (r0 == 0) goto La9
            com.aliyun.player.alivcplayerexpand.view.control.ControlView r0 = r13.mControlView
            if (r0 == 0) goto L74
            long r2 = r14.getExtraValue()
            int r2 = (int) r2
            int r3 = r13.mAdvVideoCount
            boolean r0 = r0.isNeedToPause(r2, r3)
            if (r0 == 0) goto L74
            long r2 = r14.getExtraValue()
            int r0 = com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.TRAILER
            int r0 = r0 * 1000
            long r4 = (long) r0
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 >= 0) goto L74
            r13.startAdvVideo()
        L74:
            com.aliyun.player.alivcplayerexpand.view.control.ControlView r0 = r13.mControlView
            if (r0 == 0) goto Lbb
            boolean r2 = r13.inSeek
            if (r2 != 0) goto Lbb
            int r2 = r13.mPlayerState
            if (r2 != r1) goto Lbb
            int r1 = r13.mAdvVideoCount
            r2 = 2
            if (r1 != r2) goto L9e
            long r1 = r13.mAdvTotalPosition
            long r3 = r13.mCurrentPosition
            long r5 = r1 + r3
            long r7 = r13.mSourceDuration
            r9 = 2
            long r11 = r7 / r9
            long r11 = r11 + r1
            int r5 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r5 >= 0) goto L9e
            long r7 = r7 / r9
            long r1 = r1 + r7
            int r1 = (int) r1
            int r2 = (int) r3
            r0.setAdvVideoPosition(r1, r2)
            goto Lbb
        L9e:
            long r1 = r13.mAdvTotalPosition
            long r3 = r13.mCurrentPosition
            long r1 = r1 + r3
            int r1 = (int) r1
            int r2 = (int) r3
            r0.setAdvVideoPosition(r1, r2)
            goto Lbb
        La9:
            com.aliyun.player.alivcplayerexpand.view.control.ControlView r0 = r13.mControlView
            if (r0 == 0) goto Lbb
            boolean r2 = r13.inSeek
            if (r2 != 0) goto Lbb
            int r2 = r13.mPlayerState
            if (r2 != r1) goto Lbb
            long r1 = r13.mCurrentPosition
            int r1 = (int) r1
            r0.setVideoPosition(r1)
        Lbb:
            com.aliyun.player.IPlayer$OnInfoListener r0 = r13.mOutInfoListener
            if (r0 == 0) goto Lc2
            r0.onInfo(r14)
        Lc2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.sourceVideoPlayerInfo(com.aliyun.player.bean.InfoBean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sourceVideoPlayerLoadingBegin() {
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            if (GlobalPlayerConfig.IS_VIDEO && this.mAdvVideoPlayerState == 3) {
                return;
            }
            tipsView.hideNetLoadingTipView();
            this.mTipsView.showBufferLoadingTipView();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sourceVideoPlayerLoadingEnd() {
        if (this.mTipsView != null) {
            if (isPlaying()) {
                this.mTipsView.hideErrorTipView();
            }
            this.mTipsView.hideBufferLoadingTipView();
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setHideType(ViewAction.HideType.Normal);
        }
        GestureView gestureView = this.mGestureView;
        if (gestureView != null) {
            gestureView.setHideType(ViewAction.HideType.Normal);
            this.mGestureView.show();
        }
        this.hasLoadEnd.put(this.mAliyunMediaInfo, Boolean.TRUE);
        this.vodPlayerLoadEndHandler.sendEmptyMessage(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sourceVideoPlayerLoadingProgress(int i2) {
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            if (!GlobalPlayerConfig.IS_VIDEO || this.mAdvVideoPlayerState != 3) {
                tipsView.updateLoadingPercent(i2);
            }
            if (i2 == 100) {
                this.mTipsView.hideBufferLoadingTipView();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sourceVideoPlayerOnVideoRenderingStart() {
        this.mCoverView.setVisibility(8);
        IPlayer.OnRenderingStartListener onRenderingStartListener = this.mOutFirstFrameStartListener;
        if (onRenderingStartListener != null) {
            onRenderingStartListener.onRenderingStart();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sourceVideoPlayerPrepared() {
        this.mThumbnailPrepareSuccess = false;
        ThumbnailView thumbnailView = this.mThumbnailView;
        if (thumbnailView != null) {
            thumbnailView.setThumbnailPicture(null);
        }
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView == null) {
            return;
        }
        MediaInfo mediaInfo = aliyunRenderView.getMediaInfo();
        this.mAliyunMediaInfo = mediaInfo;
        if (mediaInfo == null) {
            return;
        }
        List<Thumbnail> thumbnailList = mediaInfo.getThumbnailList();
        if (thumbnailList != null && thumbnailList.size() > 0) {
            ThumbnailHelper thumbnailHelper = new ThumbnailHelper(thumbnailList.get(0).mURL);
            this.mThumbnailHelper = thumbnailHelper;
            thumbnailHelper.setOnPrepareListener(new ThumbnailHelper.OnPrepareListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.24
                @Override // com.aliyun.thumbnail.ThumbnailHelper.OnPrepareListener
                public void onPrepareFail() {
                    AliyunVodPlayerView.this.mThumbnailPrepareSuccess = false;
                }

                @Override // com.aliyun.thumbnail.ThumbnailHelper.OnPrepareListener
                public void onPrepareSuccess() {
                    AliyunVodPlayerView.this.mThumbnailPrepareSuccess = true;
                }
            });
            this.mThumbnailHelper.prepare();
            this.mThumbnailHelper.setOnThumbnailGetListener(new ThumbnailHelper.OnThumbnailGetListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.25
                @Override // com.aliyun.thumbnail.ThumbnailHelper.OnThumbnailGetListener
                public void onThumbnailGetFail(long j2, String str) {
                }

                @Override // com.aliyun.thumbnail.ThumbnailHelper.OnThumbnailGetListener
                public void onThumbnailGetSuccess(long j2, ThumbnailBitmapInfo thumbnailBitmapInfo) {
                    if (thumbnailBitmapInfo == null || thumbnailBitmapInfo.getThumbnailBitmap() == null) {
                        return;
                    }
                    Bitmap thumbnailBitmap = thumbnailBitmapInfo.getThumbnailBitmap();
                    AliyunVodPlayerView.this.mThumbnailView.setTime(TimeFormater.formatMs(j2));
                    AliyunVodPlayerView.this.mThumbnailView.setThumbnailPicture(thumbnailBitmap);
                }
            });
        }
        long duration = this.mAliyunRenderView.getDuration();
        this.mSourceDuration = duration;
        this.mAliyunMediaInfo.setDuration((int) duration);
        if (this.mSourceDuration <= 0) {
            TrackInfo trackInfoCurrentTrack = this.mAliyunRenderView.currentTrack(TrackInfo.Type.TYPE_VIDEO);
            TrackInfo trackInfoCurrentTrack2 = this.mAliyunRenderView.currentTrack(TrackInfo.Type.TYPE_AUDIO);
            if (trackInfoCurrentTrack == null && trackInfoCurrentTrack2 != null) {
                Toast.makeText(getContext(), getContext().getString(R.string.alivc_player_audio_stream), 0).show();
            } else if (trackInfoCurrentTrack != null && trackInfoCurrentTrack2 == null) {
                Toast.makeText(getContext(), getContext().getString(R.string.alivc_player_video_stream), 0).show();
            }
        }
        if (!GlobalPlayerConfig.IS_VIDEO) {
            AliyunRenderView aliyunRenderView2 = this.mAliyunRenderView;
            TrackInfo.Type type = TrackInfo.Type.TYPE_VOD;
            if (aliyunRenderView2.currentTrack(type.ordinal()) != null) {
                this.mControlView.setMediaInfo(this.mAliyunMediaInfo, this.mAliyunRenderView.currentTrack(type.ordinal()).getVodDefinition());
            } else {
                this.mControlView.setMediaInfo(this.mAliyunMediaInfo, QualityValue.QUALITY_FLUENT);
            }
            this.mControlView.setScreenModeStatus(this.mCurrentScreenMode);
            if (this.mFullOrigrinClickIml == null) {
                if (this.preparePlay || isLocalVideo()) {
                    this.mControlView.show();
                    this.mGestureView.show();
                } else {
                    ControlView controlView = this.mControlView;
                    ViewAction.HideType hideType = ViewAction.HideType.Normal;
                    controlView.hide(hideType);
                    this.mGestureView.hide(hideType);
                }
            }
        }
        ControlView controlView2 = this.mControlView;
        ViewAction.HideType hideType2 = ViewAction.HideType.Normal;
        controlView2.setHideType(hideType2);
        this.mGestureView.setHideType(hideType2);
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.hideNetLoadingTipView();
            this.mTipsView.hideBufferLoadingTipView();
        }
        if (GlobalPlayerConfig.IS_VIDEO) {
            if (!this.mIsVipRetry) {
                this.mSurfaceView.setVisibility(8);
            }
            Message messageObtain = Message.obtain();
            messageObtain.what = 1;
            messageObtain.obj = this.mAliyunMediaInfo;
            this.mVodPlayerHandler.sendMessage(messageObtain);
            return;
        }
        if (GlobalPlayerConfig.IS_TRAILER) {
            TrailersView trailersView = this.mTrailersView;
            if (trailersView != null) {
                trailersView.trailerPlayTipsIsShow(true);
            }
        } else {
            SurfaceView surfaceView = this.mSurfaceView;
            if (surfaceView != null) {
                surfaceView.setVisibility(0);
            }
            AdvVideoView advVideoView = this.mAdvVideoView;
            if (advVideoView != null) {
                advVideoView.setSurfaceViewVisibility(8);
            }
            setCoverUri(this.mAliyunMediaInfo.getCoverUrl());
        }
        IPlayer.OnPreparedListener onPreparedListener = this.mOutPreparedListener;
        if (onPreparedListener != null) {
            onPreparedListener.onPrepared();
        }
        if (isLocalVideo()) {
            this.preparePlay = true;
        }
        if (!this.preparePlay && !isLocalVideo()) {
            this.mAliyunRenderView.pause();
        }
        this.mIsVipRetry = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sourceVideoPlayerSeekComplete() {
        this.inSeek = false;
        IPlayer.OnSeekCompleteListener onSeekCompleteListener = this.mOuterSeekCompleteListener;
        if (onSeekCompleteListener != null) {
            onSeekCompleteListener.onSeekComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sourceVideoPlayerStateChanged(int i2) {
        ControlView controlView;
        AdvPictureView advPictureView;
        TipsView tipsView;
        this.mPlayerState = i2;
        if (GlobalPlayerConfig.IS_PICTRUE && i2 == 4 && !this.mIsScreenCosting && this.mAdvPictureView != null && (tipsView = this.mTipsView) != null && !tipsView.isShown()) {
            this.mAdvPictureView.showCenterAdv();
        }
        if (GlobalPlayerConfig.IS_PICTRUE && i2 == 3 && (advPictureView = this.mAdvPictureView) != null && advPictureView.isShown()) {
            this.mAdvPictureView.hideAll();
        }
        if (i2 == 5) {
            OnStoppedListener onStoppedListener = this.mOnStoppedListener;
            if (onStoppedListener != null) {
                onStoppedListener.onStop();
                return;
            }
            return;
        }
        if (i2 != 3 || (controlView = this.mControlView) == null) {
            return;
        }
        controlView.setPlayState(ControlView.PlayState.Playing);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sourceVideoPlayerTrackInfoChangedFail(TrackInfo trackInfo, ErrorInfo errorInfo) {
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.hideNetLoadingTipView();
        }
        stop();
        IPlayer.OnTrackChangedListener onTrackChangedListener = this.mOutOnTrackChangedListener;
        if (onTrackChangedListener != null) {
            onTrackChangedListener.onChangedFail(trackInfo, errorInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sourceVideoPlayerTrackInfoChangedSuccess(TrackInfo trackInfo) {
        if (trackInfo.getType() == TrackInfo.Type.TYPE_VOD) {
            this.mControlView.setCurrentQuality(trackInfo.getVodDefinition());
            if (this.mIsScreenCosting) {
                ControlView controlView = this.mControlView;
                if (controlView != null) {
                    controlView.setVideoPosition((int) this.mCurrentPosition);
                }
            } else {
                start();
            }
            TipsView tipsView = this.mTipsView;
            if (tipsView != null) {
                tipsView.hideNetLoadingTipView();
            }
        }
        IPlayer.OnTrackChangedListener onTrackChangedListener = this.mOutOnTrackChangedListener;
        if (onTrackChangedListener != null) {
            onTrackChangedListener.onChangedSuccess(trackInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sourceVideoSnapShot(final Bitmap bitmap, int i2, int i3) {
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.26
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                final String strSaveBitmap = FileUtils.saveBitmap(bitmap, FileUtils.getDir(AliyunVodPlayerView.this.getContext()) + GlobalPlayerConfig.SNAP_SHOT_PATH);
                if (Build.VERSION.SDK_INT >= 29) {
                    FileUtils.saveImgToMediaStore(AliyunVodPlayerView.this.getContext().getApplicationContext(), strSaveBitmap, "image/png");
                } else {
                    MediaScannerConnection.scanFile(AliyunVodPlayerView.this.getContext().getApplicationContext(), new String[]{strSaveBitmap}, new String[]{"image/png"}, null);
                }
                if (AliyunVodPlayerView.this.mScreenShotListener != null) {
                    ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.26.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AliyunVodPlayerView.this.mScreenShotListener.snapshotSuccess(strSaveBitmap);
                        }
                    });
                }
                Log.e(AliyunVodPlayerView.TAG, "snapShot has Saved " + strSaveBitmap);
            }
        });
    }

    private void startAdvVideo() {
        if (!GlobalPlayerConfig.IS_TRAILER) {
            playAdvVideo();
        } else if (this.mSourceSeekToPosition < TRAILER * 1000) {
            playAdvVideo();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stop() {
        MediaInfo mediaInfo;
        Boolean bool;
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView == null || this.hasLoadEnd == null) {
            mediaInfo = null;
            bool = null;
        } else {
            mediaInfo = aliyunRenderView.getMediaInfo();
            bool = this.hasLoadEnd.get(mediaInfo);
        }
        AliyunRenderView aliyunRenderView2 = this.mAliyunRenderView;
        if (aliyunRenderView2 != null && bool != null) {
            this.mPlayerState = 5;
            aliyunRenderView2.stop();
        }
        AdvVideoView advVideoView = this.mAdvVideoView;
        if (advVideoView != null) {
            advVideoView.optionStop();
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setPlayState(ControlView.PlayState.NotPlaying);
        }
        Map<MediaInfo, Boolean> map = this.hasLoadEnd;
        if (map != null) {
            map.remove(mediaInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchPlayerState() {
        int i2 = this.mPlayerState;
        if (i2 == 3) {
            pause();
        } else if (i2 == 4 || i2 == 2 || i2 == 5) {
            start();
        }
        OnPlayStateBtnClickListener onPlayStateBtnClickListener = this.onPlayStateBtnClickListener;
        if (onPlayStateBtnClickListener != null) {
            onPlayStateBtnClickListener.onPlayBtnClick(this.mPlayerState);
        }
    }

    public void addSubView(View view) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        if (view instanceof ControlView) {
            layoutParams.addRule(10);
        }
        addView(view, layoutParams);
    }

    public void addSummary(String str) {
        getControlView().addSummary(str);
    }

    public void changeQuality() {
        List<TrackInfo> trackInfos = this.mAliyunMediaInfo.getTrackInfos();
        if (trackInfos != null) {
            for (TrackInfo trackInfo : trackInfos) {
                if (trackInfo.getType() == TrackInfo.Type.TYPE_VOD) {
                    if (QualityValue.QUALITY_STAND.equals(trackInfo.getVodDefinition())) {
                        this.mAliyunRenderView.selectTrack(trackInfo.getIndex());
                        return;
                    } else if (QualityValue.QUALITY_LOW.equals(trackInfo.getVodDefinition())) {
                        this.mAliyunRenderView.selectTrack(trackInfo.getIndex());
                        return;
                    }
                }
            }
        }
    }

    public void changeScreenMode(AliyunScreenMode aliyunScreenMode, boolean z2) {
        AliyunScreenMode aliyunScreenMode2 = this.mIsFullScreenLocked ? AliyunScreenMode.Full : aliyunScreenMode;
        if (aliyunScreenMode != this.mCurrentScreenMode) {
            this.mCurrentScreenMode = aliyunScreenMode2;
        }
        GestureDialogManager gestureDialogManager = this.mGestureDialogManager;
        if (gestureDialogManager != null) {
            gestureDialogManager.setCurrentScreenMode(this.mCurrentScreenMode);
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setScreenModeStatus(aliyunScreenMode2);
        }
        SpeedView speedView = this.mSpeedView;
        if (speedView != null) {
            speedView.setScreenMode(aliyunScreenMode2);
        }
        PlayerDanmakuView playerDanmakuView = this.mDanmakuView;
        if (playerDanmakuView != null) {
            playerDanmakuView.setScreenMode(aliyunScreenMode2);
        }
        MarqueeView marqueeView = this.mMarqueeView;
        if (marqueeView != null) {
            marqueeView.setScreenMode(aliyunScreenMode2);
        }
        GuideView guideView = this.mGuideView;
        if (guideView != null) {
            guideView.setScreenMode(aliyunScreenMode2);
        }
        setWaterMarkPosition(aliyunScreenMode2);
        Context context = getContext();
        if (context instanceof Activity) {
            if (aliyunScreenMode2 == AliyunScreenMode.Full) {
                if (getLockPortraitMode() != null) {
                    ViewGroup.LayoutParams layoutParams = getLayoutParams();
                    layoutParams.height = -1;
                    layoutParams.width = -1;
                } else if (z2) {
                    ((Activity) context).setRequestedOrientation(8);
                } else {
                    ((Activity) context).setRequestedOrientation(0);
                }
                showDanmakuAndMarquee();
                return;
            }
            if (aliyunScreenMode2 == AliyunScreenMode.Small) {
                if (getLockPortraitMode() == null) {
                    ((Activity) context).setRequestedOrientation(1);
                } else {
                    ViewGroup.LayoutParams layoutParams2 = getLayoutParams();
                    layoutParams2.height = (int) ((ScreenUtils.getWidth(context) * 9.0f) / 16.0f);
                    layoutParams2.width = -1;
                }
                PlayerDanmakuView playerDanmakuView2 = this.mDanmakuView;
                if (playerDanmakuView2 != null) {
                    playerDanmakuView2.hide();
                }
                MarqueeView marqueeView2 = this.mMarqueeView;
                if (marqueeView2 != null) {
                    marqueeView2.pause();
                }
            }
        }
    }

    public void changeSpeed(SpeedValue speedValue) {
        if (speedValue == SpeedValue.OneQuartern) {
            this.currentSpeed = 0.5f;
        } else if (speedValue == SpeedValue.One) {
            this.currentSpeed = 1.0f;
        } else if (speedValue == SpeedValue.OnePointTwoFive) {
            this.currentSpeed = 1.25f;
        } else if (speedValue == SpeedValue.OneHalf) {
            this.currentSpeed = 1.5f;
        } else if (speedValue == SpeedValue.Twice) {
            this.currentSpeed = 2.0f;
        }
        this.mAliyunRenderView.setSpeed(this.currentSpeed);
    }

    public void clearFrameWhenStop(boolean z2) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            PlayerConfig playerConfig = aliyunRenderView.getPlayerConfig();
            playerConfig.mClearFrameWhenStop = z2;
            this.mAliyunRenderView.setPlayerConfig(playerConfig);
        }
    }

    public TrackInfo currentTrack(TrackInfo.Type type) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView == null) {
            return null;
        }
        return aliyunRenderView.currentTrack(type);
    }

    public void disableNativeLog() {
        Logger.getInstance(getContext()).enableConsoleLog(false);
    }

    public void enableNativeLog() {
        Logger.getInstance(getContext()).enableConsoleLog(true);
        Logger.getInstance(getContext()).setLogLevel(Logger.LogLevel.AF_LOG_LEVEL_DEBUG);
    }

    public int getBufferPercentage() {
        if (this.mAliyunRenderView != null) {
            return this.mCurrentBufferPercentage;
        }
        return 0;
    }

    public ControlView getControlView() {
        return this.mControlView;
    }

    public MediaInfo getCurrentMediaInfo() {
        return this.mAliyunMediaInfo;
    }

    public float getCurrentSpeed() {
        return this.currentSpeed;
    }

    public float getCurrentVolume() {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            return aliyunRenderView.getVolume();
        }
        return 0.0f;
    }

    public long getDuration() {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            return aliyunRenderView.getDuration();
        }
        return 0L;
    }

    public boolean getIsCreenCosting() {
        return this.mIsScreenCosting;
    }

    public LockPortraitListener getLockPortraitMode() {
        return this.mLockPortraitListener;
    }

    public MediaInfo getMediaInfo() {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            return aliyunRenderView.getMediaInfo();
        }
        return null;
    }

    public PlayerConfig getPlayerConfig() {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            return aliyunRenderView.getPlayerConfig();
        }
        return null;
    }

    public int getPlayerState() {
        return this.mPlayerState;
    }

    public SurfaceView getPlayerView() {
        return this.mSurfaceView;
    }

    public String getSDKVersion() {
        return AliPlayerFactory.getSdkVersion();
    }

    public IPlayer.ScaleMode getScaleMode() {
        IPlayer.ScaleMode scaleMode = IPlayer.ScaleMode.SCALE_ASPECT_FIT;
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        return aliyunRenderView != null ? aliyunRenderView.getScaleModel() : scaleMode;
    }

    public int getScreenBrightness() {
        return this.mScreenBrightness;
    }

    public int getScreenCostingVolume() {
        return this.mScreenCostingVolume;
    }

    public AliyunScreenMode getScreenMode() {
        return this.mCurrentScreenMode;
    }

    public int getTargetPosition(long j2, long j3, long j4) {
        long j5 = (j2 / 1000) / 60;
        int i2 = (int) (j5 / 60);
        int i3 = (int) (j5 % 60);
        if (i2 >= 1) {
            j4 /= 10;
        } else if (i3 > 30) {
            j4 /= 5;
        } else if (i3 > 10) {
            j4 /= 3;
        } else if (i3 > 3) {
            j4 /= 2;
        }
        long j6 = j4 + j3;
        if (j6 < 0) {
            j6 = 0;
        }
        if (j6 <= j2) {
            j2 = j6;
        }
        return (int) j2;
    }

    public long getmCurrentPosition() {
        return this.mCurrentPosition;
    }

    public FullOrigrinClickIml getmFullOrigrinClickIml() {
        return this.mFullOrigrinClickIml;
    }

    public void hideDanmakuView() {
        PlayerDanmakuView playerDanmakuView = this.mDanmakuView;
        if (playerDanmakuView != null) {
            playerDanmakuView.hideAndPauseDrawTask();
            this.mDanmakuView.setVisibility(8);
        }
    }

    public void hideFastForwardView() {
        if (this.fastForwardView.getVisibility() != 8) {
            this.fastForwardView.setVisibility(8);
        }
    }

    public void hideProcessControl() {
        GestureView gestureView = this.mGestureView;
        ViewAction.HideType hideType = ViewAction.HideType.End;
        gestureView.hide(hideType);
        this.mControlView.hide(hideType);
    }

    public void initAdvPicture() {
        AdvPictureView advPictureView = new AdvPictureView(getContext());
        this.mAdvPictureView = advPictureView;
        advPictureView.setAdvPictureUrl("");
        this.mAdvPictureView.setVisibility(8);
        addSubView(this.mAdvPictureView);
        this.mAdvPictureView.setOnAdvPictureListener(new AdvPictureView.OnAdvPictureListener() { // from class: com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.1
            @Override // com.aliyun.player.alivcplayerexpand.view.function.AdvPictureView.OnAdvPictureListener
            public void close() {
                if (AliyunVodPlayerView.this.mAdvPictureView != null) {
                    AliyunVodPlayerView.this.mAdvPictureView.hideAll();
                    AliyunVodPlayerView.this.mAdvPictureView.cancel();
                }
                AliyunVodPlayerView aliyunVodPlayerView = AliyunVodPlayerView.this;
                if (aliyunVodPlayerView.mAliyunRenderView == null || aliyunVodPlayerView.mInBackground) {
                    return;
                }
                if (NetWatchdog.is4GConnected(AliyunVodPlayerView.this.getContext()) && AliyunVodPlayerView.this.mTipsView != null) {
                    AliyunVodPlayerView.this.mTipsView.showNetChangeTipView();
                } else {
                    AliyunVodPlayerView.this.mAliyunRenderView.setAutoPlay(true);
                    AliyunVodPlayerView.this.mAliyunRenderView.start();
                }
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.function.AdvPictureView.OnAdvPictureListener
            public void finish() {
                if (AliyunVodPlayerView.this.mOnFinishListener != null) {
                    AliyunVodPlayerView.this.mAdvPictureView.cancel();
                    AliyunVodPlayerView.this.mOnFinishListener.onFinishClick();
                }
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.function.AdvPictureView.OnAdvPictureListener
            public void onClick() throws IllegalAccessException, IllegalArgumentException {
                AliyunVodPlayerView.this.openAdvertisement();
            }
        });
    }

    public boolean isLoop() {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            return aliyunRenderView.isLoop();
        }
        return false;
    }

    public boolean isPlaying() {
        return this.mPlayerState == 3;
    }

    public void lockScreen(boolean z2) {
        this.mIsFullScreenLocked = z2;
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setScreenLockStatus(z2);
        }
        GestureView gestureView = this.mGestureView;
        if (gestureView != null) {
            gestureView.setScreenLockStatus(this.mIsFullScreenLocked);
        }
    }

    public void needOnlyFullScreenPlay(boolean z2) {
        this.mIsNeedOnlyFullScreen = z2;
    }

    public void onDestroy() {
        stop();
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.release();
            this.mAliyunRenderView = null;
        }
        this.mSurfaceView = null;
        this.mGestureView = null;
        this.mControlView = null;
        this.mCoverView = null;
        this.mGestureDialogManager = null;
        NetWatchdog netWatchdog = this.mNetWatchdog;
        if (netWatchdog != null) {
            netWatchdog.stopWatch();
        }
        this.mNetWatchdog = null;
        this.mTipsView = null;
        this.mAliyunMediaInfo = null;
        Map<MediaInfo, Boolean> map = this.hasLoadEnd;
        if (map != null) {
            map.clear();
        }
        stopNetWatch();
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (this.mCurrentScreenMode != AliyunScreenMode.Full || i2 == 3 || i2 == 24 || i2 == 25) {
            return !this.mIsFullScreenLocked || i2 == 3;
        }
        changedToPortrait(true);
        return false;
    }

    public void onResume() {
        this.mInBackground = false;
        if (this.mIsFullScreenLocked) {
            int i2 = getResources().getConfiguration().orientation;
            if (i2 == 1) {
                changeScreenMode(AliyunScreenMode.Small, false);
            } else if (i2 == 2) {
                changeScreenMode(AliyunScreenMode.Full, false);
            }
        }
        AdvPictureView advPictureView = this.mAdvPictureView;
        if (advPictureView != null && GlobalPlayerConfig.IS_PICTRUE && advPictureView.isInCountDown() && !this.mIsScreenCosting) {
            this.mAdvPictureView.reStart();
        } else {
            if (this.mIsScreenCosting) {
                return;
            }
            resumePlayerState();
        }
    }

    public void onStop() {
        this.mInBackground = true;
        savePlayerState();
    }

    public void openAdvertisement() throws IllegalAccessException, IllegalArgumentException {
        List<ResolveInfo> listCheckBrowserList = BrowserCheckUtil.checkBrowserList(getContext());
        if (listCheckBrowserList == null || listCheckBrowserList.size() <= 0) {
            ToastUtils.show(getContext(), getContext().getString(R.string.alivc_player_not_check_any_browser));
            return;
        }
        Intent intent = new Intent();
        intent.setData(Uri.parse(ADV_URL));
        intent.setAction("android.intent.action.VIEW");
        getContext().startActivity(intent);
    }

    public void pause() {
        AdvPictureView advPictureView;
        int i2;
        AdvPictureView advPictureView2;
        ControlView controlView = this.mControlView;
        if (controlView != null && !this.mIsScreenCosting) {
            controlView.setPlayState(ControlView.PlayState.NotPlaying);
        }
        if (this.mAliyunRenderView == null) {
            return;
        }
        AdvVideoView advVideoView = this.mAdvVideoView;
        if (advVideoView != null) {
            advVideoView.optionPause();
        }
        int i3 = this.mPlayerState;
        if (i3 == 3 || i3 == 2) {
            if (this.mSourceDuration <= 0) {
                this.mPlayerState = 5;
                this.mAliyunRenderView.stop();
            } else {
                this.mAliyunRenderView.pause();
                PauseClickIml pauseClickIml = this.pauseClickIml;
                if (pauseClickIml != null) {
                    pauseClickIml.mPauseClick();
                }
            }
            if (GlobalPlayerConfig.IS_PICTRUE && (advPictureView2 = this.mAdvPictureView) != null) {
                if (advPictureView2.isInCountDown()) {
                    this.mAdvPictureView.stop();
                } else if (!this.mIsScreenCosting) {
                    this.mAdvPictureView.showCenterAdv();
                }
            }
            if (GlobalPlayerConfig.IS_VIDEO && GlobalPlayerConfig.IS_TRAILER && (advPictureView = this.mAdvPictureView) != null && (i2 = this.mAdvVideoPlayerState) != 3 && i2 != 4 && !this.mIsScreenCosting) {
                advPictureView.showCenterAdv();
            }
            MarqueeView marqueeView = this.mMarqueeView;
            if (marqueeView != null) {
                marqueeView.pause();
            }
        }
    }

    public void prepareMP4UrlSource(UrlSource urlSource) {
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.showNetLoadingTipView();
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setForceQuality(true);
            this.mControlView.setIsMtsSource(false);
            this.mControlView.hideMoreButton();
        }
        QualityView qualityView = this.mQualityView;
        if (qualityView != null) {
            qualityView.setIsMtsSource(false);
        }
        this.mAliyunRenderView.setAutoPlay(false);
        this.mAliyunRenderView.setDataSource(urlSource);
        this.mAliyunRenderView.prepare();
    }

    public void rePlay() {
        this.isCompleted = false;
        this.inSeek = false;
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.hideAll();
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.reset();
        }
        GestureView gestureView = this.mGestureView;
        if (gestureView != null) {
            gestureView.reset();
        }
        AdvPictureView advPictureView = this.mAdvPictureView;
        if (advPictureView != null) {
            advPictureView.cancel();
            this.mAdvPictureView.hideAll();
        }
        if (this.mAliyunRenderView != null) {
            TipsView tipsView2 = this.mTipsView;
            if (tipsView2 != null) {
                tipsView2.showNetLoadingTipView();
            }
            this.mAliyunRenderView.prepare();
        }
    }

    public void reTry() {
        this.isCompleted = false;
        this.inSeek = false;
        int videoPosition = this.mControlView.getVideoPosition();
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.hideAll();
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.reset();
            this.mControlView.setVideoPosition(videoPosition);
        }
        GestureView gestureView = this.mGestureView;
        if (gestureView != null) {
            gestureView.reset();
        }
        if (this.mAliyunRenderView != null) {
            TipsView tipsView2 = this.mTipsView;
            if (tipsView2 != null) {
                tipsView2.showNetLoadingTipView();
            }
            if (!GlobalPlayerConfig.IS_VIDEO) {
                this.mAliyunRenderView.prepare();
                isAutoAccurate(videoPosition);
                return;
            }
            AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
            if (aliyunRenderView != null) {
                this.mIsVipRetry = true;
                aliyunRenderView.prepare();
            }
        }
    }

    public void reload() {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.reload();
        }
    }

    public void reset() {
        this.isCompleted = false;
        this.inSeek = false;
        this.mCurrentPosition = 0L;
        this.mAdvTotalPosition = 0L;
        this.mAdvCurrentPosition = 0L;
        this.mVideoBufferedPosition = 0L;
        this.needToSeek = false;
        this.mCurrentIntentPlayVideo = AdvVideoView.IntentPlayVideo.NORMAL;
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.hideAll();
        }
        TrailersView trailersView = this.mTrailersView;
        if (trailersView != null) {
            trailersView.hideAll();
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.reset();
        }
        GestureView gestureView = this.mGestureView;
        if (gestureView != null) {
            gestureView.reset();
        }
        PlayerDanmakuView playerDanmakuView = this.mDanmakuView;
        if (playerDanmakuView != null) {
            playerDanmakuView.clearDanmaList();
        }
        AdvPictureView advPictureView = this.mAdvPictureView;
        if (advPictureView != null) {
            advPictureView.cancel();
            this.mAdvPictureView.hideAll();
        }
        stop();
    }

    public void screenCostPlay() {
        this.mIsScreenCosting = true;
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.pause();
        }
        AdvPictureView advPictureView = this.mAdvPictureView;
        if (advPictureView != null) {
            advPictureView.hideAll();
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setInScreenCosting(this.mIsScreenCosting);
            this.mControlView.show();
            this.mControlView.startScreenCost();
        }
    }

    public void screenCostStop() {
        this.mIsScreenCosting = false;
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setInScreenCosting(false);
        }
    }

    public void seekTo(int i2) {
        this.mSeekToPosition = i2;
        if (this.mAliyunRenderView == null) {
            return;
        }
        this.inSeek = true;
        if (GlobalPlayerConfig.IS_VIDEO) {
            checkAdvVideoSeek(i2);
        } else {
            this.mSourceSeekToPosition = i2;
            realySeekToFunction(i2);
        }
    }

    public void selectAutoBitrateTrack() {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.selectTrack(-1);
        }
    }

    public void selectTrack(TrackInfo trackInfo) {
        if (this.mAliyunRenderView == null || trackInfo == null) {
            return;
        }
        this.mAliyunRenderView.selectTrack(trackInfo.getIndex());
    }

    public void setAuthInfo(VidAuth vidAuth) {
        if (this.mAliyunRenderView == null) {
            return;
        }
        clearAllSource();
        reset();
        this.mAliyunPlayAuth = vidAuth;
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setForceQuality(vidAuth.isForceQuality());
        }
        if (show4gTips()) {
            return;
        }
        prepareAuth(vidAuth);
    }

    public void setAutoPlay(boolean z2) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.setAutoPlay(z2);
        }
    }

    public void setCacheConfig(CacheConfig cacheConfig) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.setCacheConfig(cacheConfig);
        }
    }

    public void setCirclePlay(boolean z2) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.setLoop(z2);
        }
    }

    public void setControlBarCanShow(boolean z2) {
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setControlBarCanShow(z2);
        }
    }

    public void setCoverResource(int i2) {
        ImageView imageView = this.mCoverView;
        if (imageView != null) {
            imageView.setImageResource(i2);
            this.mCoverView.setVisibility(isPlaying() ? 8 : 0);
        }
    }

    public void setCoverUri(String str) {
        if (this.mCoverView == null || TextUtils.isEmpty(str)) {
            return;
        }
        new ImageLoaderImpl().loadImage(getContext(), str).into(this.mCoverView);
        this.mCoverView.setVisibility(isPlaying() ? 8 : 0);
    }

    public void setCurrentPosition(long j2) {
        this.mCurrentPosition = 0L;
    }

    public void setCurrentVolume(float f2) {
        if (f2 <= 0.0f) {
            f2 = 0.0f;
        }
        if (f2 >= 1.0f) {
            f2 = 1.0f;
        }
        this.currentVolume = f2;
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.setVolume(f2);
        }
    }

    public void setDanmakuAlpha(int i2) {
        PlayerDanmakuView playerDanmakuView = this.mDanmakuView;
        if (playerDanmakuView != null) {
            playerDanmakuView.setAlpha((float) (1.0d - ((i2 / 100.0d) * 1.0d)));
        }
    }

    public void setDanmakuDefault() {
        if (this.mDanmakuView != null) {
            setDanmakuAlpha(0);
            setDanmakuSpeed(30);
            setDanmakuRegion(0);
        }
    }

    public void setDanmakuRegion(int i2) {
        PlayerDanmakuView playerDanmakuView = this.mDanmakuView;
        if (playerDanmakuView != null) {
            playerDanmakuView.setDanmakuRegion(i2);
        }
    }

    public void setDanmakuSpeed(int i2) {
        PlayerDanmakuView playerDanmakuView = this.mDanmakuView;
        if (playerDanmakuView != null) {
            playerDanmakuView.setDanmakuSpeed((float) (2.5d - (((i2 + 100) / 100.0d) * 1.0d)));
        }
    }

    public void setDefaultBandWidth(int i2) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.setDefaultBandWidth(i2);
        }
    }

    public void setDotInfo(List<DotBean> list) {
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setDotInfo(list);
        }
    }

    public void setEnableHardwareDecoder(boolean z2) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.enableHardwareDecoder(z2);
        }
    }

    public void setIsVideo(boolean z2) {
        this.isVideo = z2;
    }

    public void setLandScapeActionListener(ControlView.OnLandScapeActionListener onLandScapeActionListener) {
        this.mLandScapeActionListener = onLandScapeActionListener;
    }

    public void setLiveStsDataSource(LiveSts liveSts) {
        if (this.mAliyunRenderView == null) {
            return;
        }
        clearAllSource();
        reset();
        this.mAliyunLiveSts = liveSts;
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setForceQuality(liveSts.isForceQuality());
        }
        if (show4gTips()) {
            return;
        }
        prepareLiveSts(liveSts);
    }

    public void setLocalSource(UrlSource urlSource) {
        if (this.mAliyunRenderView == null) {
            return;
        }
        clearAllSource();
        reset();
        this.mAliyunLocalSource = urlSource;
        prepareLocalSource(urlSource);
    }

    public void setLockPortraitMode(LockPortraitListener lockPortraitListener) {
        this.mLockPortraitListener = lockPortraitListener;
    }

    public void setLoop(boolean z2) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.setLoop(z2);
        }
    }

    public void setMP4UrlSource(UrlSource urlSource) {
        if (this.mAliyunRenderView == null) {
            return;
        }
        clearAllSource();
        reset();
        this.mAliyunLocalSource = urlSource;
        prepareMP4UrlSource(urlSource);
    }

    public void setMultiWindow(boolean z2) {
        this.mIsInMultiWindow = z2;
        GestureView gestureView = this.mGestureView;
        if (gestureView != null) {
            gestureView.setMultiWindow(z2);
        }
    }

    public void setMute(boolean z2) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.setMute(z2);
        }
    }

    public void setNetConnectedListener(NetConnectedListener netConnectedListener) {
        this.mNetConnectedListener = netConnectedListener;
    }

    public void setOnAutoPlayListener(OnAutoPlayListener onAutoPlayListener) {
        this.mOutAutoPlayListener = onAutoPlayListener;
    }

    public void setOnCompletionListener(IPlayer.OnCompletionListener onCompletionListener) {
        this.mOutCompletionListener = onCompletionListener;
    }

    public void setOnControlViewHideListener(ControlView.OnControlViewHideListener onControlViewHideListener) {
        this.mOnControlViewHideListener = onControlViewHideListener;
    }

    public void setOnDotViewClickListener(ControlView.OnDotViewClickListener onDotViewClickListener) {
        this.mOnDotViewClickListener = onDotViewClickListener;
    }

    public void setOnErrorListener(IPlayer.OnErrorListener onErrorListener) {
        this.mOutErrorListener = onErrorListener;
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.mOnFinishListener = onFinishListener;
    }

    public void setOnFirstFrameStartListener(IPlayer.OnRenderingStartListener onRenderingStartListener) {
        this.mOutFirstFrameStartListener = onRenderingStartListener;
    }

    public void setOnInfoListener(IPlayer.OnInfoListener onInfoListener) {
        this.mOutInfoListener = onInfoListener;
    }

    public void setOnLoadingListener(IPlayer.OnLoadingStatusListener onLoadingStatusListener) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.setOnLoadingStatusListener(onLoadingStatusListener);
        }
    }

    public void setOnPlayStateBtnClickListener(OnPlayStateBtnClickListener onPlayStateBtnClickListener) {
        this.onPlayStateBtnClickListener = onPlayStateBtnClickListener;
    }

    public void setOnPreparedListener(IPlayer.OnPreparedListener onPreparedListener) {
        this.mOutPreparedListener = onPreparedListener;
    }

    public void setOnScreenBrightness(OnScreenBrightnessListener onScreenBrightnessListener) {
        this.mOnScreenBrightnessListener = onScreenBrightnessListener;
    }

    public void setOnScreenCostingSingleTagListener(OnScreenCostingSingleTagListener onScreenCostingSingleTagListener) {
        this.mOnScreenCostingSingleTagListener = onScreenCostingSingleTagListener;
    }

    public void setOnScreenCostingVideoCompletionListener(OnScreenCostingVideoCompletionListener onScreenCostingVideoCompletionListener) {
        this.mOnScreenCostingVideoCompletionListener = onScreenCostingVideoCompletionListener;
    }

    public void setOnScreenShotListener(OnScreenShotListener onScreenShotListener) {
        this.mScreenShotListener = onScreenShotListener;
    }

    public void setOnSeekCompleteListener(IPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        this.mOuterSeekCompleteListener = onSeekCompleteListener;
    }

    public void setOnSeekStartListener(OnSeekStartListener onSeekStartListener) {
        this.onSeekStartListener = onSeekStartListener;
    }

    public void setOnShowMoreClickListener(ControlView.OnShowMoreClickListener onShowMoreClickListener) {
        this.mOutOnShowMoreClickListener = onShowMoreClickListener;
    }

    public void setOnStoppedListener(OnStoppedListener onStoppedListener) {
        this.mOnStoppedListener = onStoppedListener;
    }

    public void setOnTimeExpiredErrorListener(OnTimeExpiredErrorListener onTimeExpiredErrorListener) {
        this.mOutTimeExpiredErrorListener = onTimeExpiredErrorListener;
    }

    public void setOnTipClickListener(TipsView.OnTipClickListener onTipClickListener) {
        this.mOutOnTipClickListener = onTipClickListener;
    }

    public void setOnTipsViewBackClickListener(OnTipsViewBackClickListener onTipsViewBackClickListener) {
        this.mOutOnTipsViewBackClickListener = onTipsViewBackClickListener;
    }

    public void setOnTrackChangedListener(IPlayer.OnTrackChangedListener onTrackChangedListener) {
        this.mOutOnTrackChangedListener = onTrackChangedListener;
    }

    public void setOnTrackInfoClickListener(ControlView.OnTrackInfoClickListener onTrackInfoClickListener) {
        this.mOutOnTrackInfoClickListener = onTrackInfoClickListener;
    }

    public void setOnTrackReadyListener(IPlayer.OnTrackReadyListener onTrackReadyListener) {
        this.mOutOnTrackReadyListener = onTrackReadyListener;
    }

    public void setOnTrailerViewClickListener(TrailersView.OnTrailerViewClickListener onTrailerViewClickListener) {
        this.mOnTrailerViewClickListener = onTrailerViewClickListener;
    }

    public void setOnVideoSizeChangedListener(IPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.setOnVideoSizeChangedListener(onVideoSizeChangedListener);
        }
    }

    public void setOperatorPlay(boolean z2) {
        this.mIsOperatorPlay = z2;
    }

    public void setOrientationChangeListener(OnOrientationChangeListener onOrientationChangeListener) {
        this.orientationChangeListener = onOrientationChangeListener;
    }

    public void setOutOnSeiDataListener(IPlayer.OnSeiDataListener onSeiDataListener) {
        this.mOutOnSeiDataListener = onSeiDataListener;
    }

    public void setOutOnVerifyTimeExpireCallback(AliPlayer.OnVerifyTimeExpireCallback onVerifyTimeExpireCallback) {
        this.mOutOnVerifyTimeExpireCallback = onVerifyTimeExpireCallback;
    }

    public void setPauseClickIml(PauseClickIml pauseClickIml) {
        this.pauseClickIml = pauseClickIml;
    }

    public void setPlayDomain(String str) {
        PLAY_DOMAIN = str;
    }

    public void setPlayerConfig(PlayerConfig playerConfig) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.setPlayerConfig(playerConfig);
        }
    }

    public void setRenderMirrorMode(IPlayer.MirrorMode mirrorMode) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.setMirrorMode(mirrorMode);
        }
    }

    public void setRenderRotate(IPlayer.RotateMode rotateMode) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.setRotateModel(rotateMode);
        }
    }

    public void setScaleMode(IPlayer.ScaleMode scaleMode) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.setScaleModel(scaleMode);
        }
    }

    public void setScreenBrightness(int i2) {
        this.mScreenBrightness = i2;
    }

    public void setScreenCostingVolume(int i2) {
        if (i2 <= 0) {
            i2 = 0;
        }
        if (i2 >= 100) {
            i2 = 100;
        }
        this.mScreenCostingVolume = i2;
    }

    public void setShowVideoSummary(boolean z2) {
        getControlView().showKnowledge(z2);
    }

    public void setSoftKeyHideListener(OnSoftKeyHideListener onSoftKeyHideListener) {
        this.mOnSoftKeyHideListener = onSoftKeyHideListener;
    }

    @Override // com.aliyun.player.alivcplayerexpand.theme.ITheme
    public void setTheme(Theme theme) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            KeyEvent.Callback childAt = getChildAt(i2);
            if (childAt instanceof ITheme) {
                ((ITheme) childAt).setTheme(theme);
            }
        }
    }

    public void setTitleBarCanShow(boolean z2) {
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setTitleBarCanShow(z2);
        }
    }

    public void setTrailerTime(int i2) {
        TRAILER = i2;
    }

    public void setTryWatchComplete(boolean z2) {
        this.tryWatchComplete = z2;
    }

    public void setVidMps(VidMps vidMps) {
        if (this.mAliyunRenderView == null) {
            return;
        }
        clearAllSource();
        reset();
        this.mAliyunVidMps = vidMps;
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setForceQuality(vidMps.isForceQuality());
        }
        if (show4gTips()) {
            return;
        }
        prepareMps(vidMps);
    }

    public void setVidSts(VidSts vidSts) {
        if (this.mAliyunRenderView == null) {
            return;
        }
        clearAllSource();
        reset();
        this.mAliyunVidSts = vidSts;
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setForceQuality(vidSts.isForceQuality());
        }
        showVideoFunction();
    }

    public void setVideoTrack(int i2) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.selectTrack(i2);
        }
    }

    public void setmDanmaku(String str) {
        PlayerDanmakuView playerDanmakuView = this.mDanmakuView;
        if (playerDanmakuView != null) {
            playerDanmakuView.addDanmaku(str, this.mCurrentPosition);
        }
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.start();
        }
        hideSystemUI();
    }

    public void setmFullOrigrinClickIml(FullOrigrinClickIml fullOrigrinClickIml) {
        this.mFullOrigrinClickIml = fullOrigrinClickIml;
    }

    public void showBackAndFullView(boolean z2) {
        this.isShowBackAndFullBtn = z2;
        this.mControlView.showOrHiddenFullBtn(z2);
        this.mTipsView.hideBackView(!z2);
    }

    public void showErrorTipView(int i2, String str, String str2) {
        stop();
        AdvPictureView advPictureView = this.mAdvPictureView;
        if (advPictureView != null) {
            advPictureView.cancel();
            this.mAdvPictureView.hideAll();
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setPlayState(ControlView.PlayState.NotPlaying);
        }
        if (this.mTipsView != null) {
            GestureView gestureView = this.mGestureView;
            ViewAction.HideType hideType = ViewAction.HideType.End;
            gestureView.hide(hideType);
            this.mControlView.hide(hideType);
            this.mCoverView.setVisibility(8);
            if (i2 != 536936451) {
                this.mTipsView.showErrorTipView(i2, str, str2);
            }
            this.mTrailersView.hideAll();
        }
    }

    public void showProcessControl() {
        this.mControlView.show();
        this.mGestureView.show();
        ControlView controlView = this.mControlView;
        ViewAction.HideType hideType = ViewAction.HideType.Normal;
        controlView.setHideType(hideType);
        this.mGestureView.setHideType(hideType);
    }

    public void showReplay() {
        if (this.mTipsView != null) {
            GestureView gestureView = this.mGestureView;
            ViewAction.HideType hideType = ViewAction.HideType.End;
            gestureView.hide(hideType);
            this.mControlView.hide(hideType);
            this.mTipsView.showReplayTipView(this.isShowBackAndFullBtn);
        }
    }

    public void snapShot() {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.snapshot();
        }
    }

    public void start() {
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setPlayState(ControlView.PlayState.Playing);
        }
        if (this.mAliyunRenderView == null) {
            return;
        }
        if (this.mAdvVideoPlayerState == 3 && GlobalPlayerConfig.IS_VIDEO) {
            ControlView controlView2 = this.mControlView;
            ViewAction.HideType hideType = ViewAction.HideType.Normal;
            controlView2.setHideType(hideType);
            this.mGestureView.setHideType(hideType);
        } else {
            this.mGestureView.show();
            this.mControlView.show();
        }
        if (this.mSourceDuration > 0 || this.mPlayerState != 5) {
            this.mAliyunRenderView.start();
        } else {
            this.mAliyunRenderView.prepare();
        }
        AdvPictureView advPictureView = this.mAdvPictureView;
        if (advPictureView != null) {
            advPictureView.hideAll();
        }
        MarqueeView marqueeView = this.mMarqueeView;
        if (marqueeView != null && marqueeView.isStart() && this.mCurrentScreenMode == AliyunScreenMode.Full) {
            this.mMarqueeView.startFlip();
        }
    }

    public void startNetWatch() {
        this.initNetWatch = true;
        NetWatchdog netWatchdog = this.mNetWatchdog;
        if (netWatchdog != null) {
            netWatchdog.startWatch();
        }
    }

    public void startOrientationWatchDog() {
    }

    public void stopNetWatch() {
        NetWatchdog netWatchdog = this.mNetWatchdog;
        if (netWatchdog != null) {
            netWatchdog.stopWatch();
        }
    }

    public void stopOrientationWatchDog() {
    }

    public void updateAuthInfo(VidAuth vidAuth) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.updateAuthInfo(vidAuth);
        }
    }

    public void updateSpeedPlayShow(int i2) {
        this.mControlView.updateSpeedPlayShow(i2);
    }

    public void updateStsInfo(StsInfo stsInfo) {
        AliyunRenderView aliyunRenderView = this.mAliyunRenderView;
        if (aliyunRenderView != null) {
            aliyunRenderView.updateStsInfo(stsInfo);
        }
    }

    public AliyunVodPlayerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AliyunVodPlayerView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.hasLoadEnd = new HashMap();
        this.mLockPortraitListener = null;
        this.mIsFullScreenLocked = false;
        this.mCurrentScreenMode = AliyunScreenMode.Small;
        this.inSeek = false;
        this.isCompleted = false;
        this.mCurrentBufferPercentage = 0;
        this.mCurrentSpeed = "1X";
        this.mThumbnailPrepareSuccess = false;
        this.vodPlayerLoadEndHandler = new VodPlayerLoadEndHandler(this);
        this.mVideoBufferedPosition = 0L;
        this.mCurrentPosition = 0L;
        this.mAdvTotalPosition = 0L;
        this.mPlayerState = 0;
        this.mAdvPosition = MutiSeekBarView.AdvPosition.ALL;
        this.mIsScreenCosting = false;
        this.mOnFinishListener = null;
        this.mOutInfoListener = null;
        this.mOutErrorListener = null;
        this.mOutOnTrackReadyListener = null;
        this.mOutAutoPlayListener = null;
        this.mOutPreparedListener = null;
        this.mOutCompletionListener = null;
        this.mOuterSeekCompleteListener = null;
        this.mOutOnTrackChangedListener = null;
        this.mOutFirstFrameStartListener = null;
        this.mOnScreenCostingSingleTagListener = null;
        this.mOnScreenBrightnessListener = null;
        this.mOutTimeExpiredErrorListener = null;
        this.mOutOnTipsViewBackClickListener = null;
        this.mOnSoftKeyHideListener = null;
        this.mOnTrailerViewClickListener = null;
        this.mOutOnSeiDataListener = null;
        this.mOutOnVerifyTimeExpireCallback = null;
        this.mOutOnTipClickListener = null;
        this.mNetConnectedListener = null;
        this.mAdvVideoCount = 0;
        this.needToSeek = false;
        this.mInBackground = false;
        this.mSubtitleTypeMap = new HashMap();
        this.currentSpeed = 1.0f;
        this.mIsCanPlayVideoBy4G = false;
        this.isLongPress = false;
        this.tryWatchComplete = false;
        this.isShowBackAndFullBtn = true;
        this.isVideo = true;
        this.preparePlay = true;
        if (isInEditMode()) {
            return;
        }
        initVideoView();
    }

    private boolean show4gTips() throws IllegalAccessException, IllegalArgumentException {
        if (isLocalSource() || !NetWatchdog.is4GConnected(getContext())) {
            return false;
        }
        if (this.mIsOperatorPlay) {
            ToastUtils.show(getContext(), R.string.alivc_operator_play);
            return false;
        }
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.showNetChangeTipView();
        }
        AdvPictureView advPictureView = this.mAdvPictureView;
        if (advPictureView == null) {
            return true;
        }
        advPictureView.hideAll();
        return true;
    }

    private void prepareVidsts(VidSts vidSts, boolean z2) {
        TipsView tipsView = this.mTipsView;
        if (tipsView != null) {
            tipsView.showNetLoadingTipView();
        }
        ControlView controlView = this.mControlView;
        if (controlView != null) {
            controlView.setIsMtsSource(false);
        }
        QualityView qualityView = this.mQualityView;
        if (qualityView != null) {
            qualityView.setIsMtsSource(false);
        }
        if (this.mAliyunRenderView != null) {
            if (GlobalPlayerConfig.IS_TRAILER) {
                VidPlayerConfigGen vidPlayerConfigGen = new VidPlayerConfigGen();
                vidPlayerConfigGen.addPlayerConfig("PlayDomain", PLAY_DOMAIN);
                vidPlayerConfigGen.setPreviewTime(TRAILER);
                vidSts.setPlayConfig(vidPlayerConfigGen);
            }
            this.mAliyunRenderView.setDataSource(vidSts);
            this.mAliyunRenderView.prepare();
        }
    }
}
