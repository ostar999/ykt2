package com.aliyun.player.alivcplayerexpand.view.control;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.alivcplayerexpand.bean.DotBean;
import com.aliyun.player.alivcplayerexpand.bean.SummaryPoint;
import com.aliyun.player.alivcplayerexpand.bean.VideoSummary;
import com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig;
import com.aliyun.player.alivcplayerexpand.listener.QualityValue;
import com.aliyun.player.alivcplayerexpand.theme.ITheme;
import com.aliyun.player.alivcplayerexpand.theme.Theme;
import com.aliyun.player.alivcplayerexpand.util.TimeFormater;
import com.aliyun.player.alivcplayerexpand.view.dot.DotView;
import com.aliyun.player.alivcplayerexpand.view.function.AdvVideoView;
import com.aliyun.player.alivcplayerexpand.view.function.MutiSeekBarView;
import com.aliyun.player.alivcplayerexpand.view.interfaces.ViewAction;
import com.aliyun.player.alivcplayerexpand.view.quality.QualityItem;
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView;
import com.aliyun.player.alivcplayerexpand.widget.DotSeekBar;
import com.aliyun.player.aliyunplayerbase.util.AliyunScreenMode;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.svideo.common.utils.FastClickUtil;
import com.aliyun.svideo.common.utils.NetUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SdkConstant;
import com.yikaobang.yixue.BuildConfig;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class ControlView extends RelativeLayout implements ViewAction, ITheme {
    private static final int DELAY_TIME = 5000;
    private static final String TAG = "ControlView";
    private static final int WHAT_HIDE = 0;
    private boolean isMtsSource;
    private boolean isSeekbarTouching;
    private boolean isShowFullBtn;
    private boolean isTryWatch;
    private ImageView ivPip;
    private ImageView iv_comment;
    private ImageView iv_note;
    private View llLargeArea;
    private long mAdvDuration;
    private MutiSeekBarView.AdvPosition mAdvPosition;
    private long mAdvTotalPosition;
    private int mAdvVideoPosition;
    private MediaInfo mAliyunMediaInfo;
    private AliyunScreenMode mAliyunScreenMode;
    private TextView mAudioTextView;
    private List<TrackInfo> mAudioTrackInfoList;
    private TextView mBitrateTextView;
    private List<TrackInfo> mBitrateTrackInfoList;
    private View mControlBar;
    private boolean mControlBarCanShow;
    private String mCurrentQuality;
    private AdvVideoView.VideoState mCurrentVideoState;
    private boolean mDanmuShow;
    private TextView mDefinitionTextView;
    private List<TrackInfo> mDefinitionTrackInfoList;
    private List<DotBean> mDotBean;
    private DownloadClickListener mDownloadClickListener;
    private boolean mForceQuality;
    private HideHandler mHideHandler;
    private ViewAction.HideType mHideType;
    private SummaryIconClickListener mIconClickListener;
    private boolean mInScreenCosting;
    private ImageView mInputDanmkuImageView;
    private OnLandScapeActionListener mLandScapeActionListener;
    private Button mLargeChangeQualityBtn;
    private TextView mLargeDurationText;
    private View mLargeInfoBar;
    private MutiSeekBarView mLargeMutiSeekbar;
    private TextView mLargePositionText;
    private DotSeekBar mLargeSeekbar;
    private boolean mMarqueeShow;
    private int mMediaDuration;
    private int mMutiSeekBarCurrentProgress;
    private OnBackClickListener mOnBackClickListener;
    private OnControlViewHideListener mOnControlViewHideListener;
    private OnDLNAControlListener mOnDLNAControlListener;
    private OnDotViewClickListener mOnDotViewClickListener;
    private OnInputDanmakuClickListener mOnInputDanmakuClickListener;
    private OnPlayStateClickListener mOnPlayStateClickListener;
    private OnQualityBtnClickListener mOnQualityBtnClickListener;
    private OnScreenLockClickListener mOnScreenLockClickListener;
    private OnScreenModeClickListener mOnScreenModeClickListener;
    private OnScreenRecoderClickListener mOnScreenRecoderClickListener;
    private OnScreenShotClickListener mOnScreenShotClickListener;
    private OnSeekListener mOnSeekListener;
    private OnShowMoreClickListener mOnShowMoreClickListener;
    private OnTrackInfoClickListener mOnTrackInfoClickListener;
    private PlayState mPlayState;
    private ImageView mPlayStateBtn;
    private PlayProgressListener mProgressListener;
    private TextView mScreenCostExitTextView;
    private LinearLayout mScreenCostLinearLayout;
    private TextView mScreenCostStateTextView;
    private ImageView mScreenLockBtn;
    private boolean mScreenLocked;
    private ImageView mScreenModeBtn;
    private ImageView mScreenRecorder;
    private ImageView mScreenShot;
    private TextView mSmallDurationText;
    private View mSmallInfoBar;
    private MutiSeekBarView mSmallMutiSeekbar;
    private TextView mSmallPositionText;
    private DotSeekBar mSmallSeekbar;
    private long mSourceDuration;
    private TextView mSubtitleTextView;
    private List<TrackInfo> mSubtitleTrackInfoList;
    private View mTitleBar;
    private boolean mTitleBarCanShow;
    private ImageView mTitleMore;
    private ImageView mTitlebarBackBtn;
    private TextView mTitlebarText;
    private LinearLayout mTrackLinearLayout;
    private UnlockListener mUnlockListener;
    private int mVideoBufferPosition;
    private int mVideoPosition;
    private RelativeLayout rlSmallTimeShow;
    private boolean showChapter;
    private boolean showFullScreenBtn;
    private boolean showKnowledgePoint;
    private boolean showNewUi;
    private boolean supportPip;
    private String teacherName;
    private TextView tvChooseChapter;
    private TextView tvCommentCount;
    private TextView tvKnowledge;
    private TextView tvNoteCount;
    private TextView tvSpeedPlay;
    private TextView tvUnlock;
    private TextView tv_teacher_name;
    private TextView tv_unlock_portrait;
    private boolean unlock;

    /* renamed from: com.aliyun.player.alivcplayerexpand.view.control.ControlView$41, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass41 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition;

        static {
            int[] iArr = new int[MutiSeekBarView.AdvPosition.values().length];
            $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition = iArr;
            try {
                iArr[MutiSeekBarView.AdvPosition.ONLY_START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[MutiSeekBarView.AdvPosition.ONLY_MIDDLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[MutiSeekBarView.AdvPosition.ONLY_END.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[MutiSeekBarView.AdvPosition.START_END.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[MutiSeekBarView.AdvPosition.MIDDLE_END.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[MutiSeekBarView.AdvPosition.START_MIDDLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[MutiSeekBarView.AdvPosition.ALL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public interface DownloadClickListener {
        void downloadClick();
    }

    public static class HideHandler extends Handler {
        private WeakReference<ControlView> controlViewWeakReference;

        public HideHandler(ControlView controlView) {
            this.controlViewWeakReference = new WeakReference<>(controlView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            ControlView controlView = this.controlViewWeakReference.get();
            if (controlView != null && !controlView.isSeekbarTouching && !controlView.mInScreenCosting) {
                controlView.hide(ViewAction.HideType.Normal);
            }
            super.handleMessage(message);
        }
    }

    public interface OnBackClickListener {
        void onClick();
    }

    public interface OnControlViewHideListener {
        void onControlViewHide();
    }

    public interface OnDLNAControlListener {
        void onChangeQuality();

        void onExit();
    }

    public interface OnDotViewClickListener {
        void onDotViewClick(int i2, int i3, DotView dotView);
    }

    public interface OnInputDanmakuClickListener {
        void onInputDanmakuClick();
    }

    public interface OnLandScapeActionListener {
        void onChapterClick();

        void onCollectClick();

        void onCommentClick();

        void onKnowledgePointClick();

        void onNoteClick();
    }

    public interface OnPlayStateClickListener {
        void onPlayStateClick();
    }

    public interface OnQualityBtnClickListener {
        void onHideQualityView();

        void onQualityBtnClick(View view, List<TrackInfo> list, String str);
    }

    public interface OnScreenLockClickListener {
        void onClick();
    }

    public interface OnScreenModeClickListener {
        void onClick();
    }

    public interface OnScreenRecoderClickListener {
        void onScreenRecoderClick();
    }

    public interface OnScreenShotClickListener {
        void onScreenShotClick();
    }

    public interface OnSeekListener {
        void onProgressChanged(int i2);

        void onSeekEnd(int i2);

        void onSeekStart(int i2);
    }

    public interface OnShowMoreClickListener {
        void showMore();
    }

    public interface OnTrackInfoClickListener {
        void onAudioClick(List<TrackInfo> list);

        void onBitrateClick(List<TrackInfo> list);

        void onDefinitionClick(List<TrackInfo> list);

        void onSubtitleClick(List<TrackInfo> list);
    }

    public interface PlayProgressListener {
        void onProgress(float f2);
    }

    public enum PlayState {
        Playing,
        NotPlaying
    }

    public interface SummaryIconClickListener {
        void onIconClick(int i2);
    }

    public interface UnlockListener {
        void onLock();
    }

    public ControlView(Context context) {
        super(context);
        this.mTitleBarCanShow = true;
        this.mControlBarCanShow = true;
        this.mPlayState = PlayState.NotPlaying;
        this.mScreenLocked = false;
        this.mAliyunScreenMode = AliyunScreenMode.Small;
        this.mVideoPosition = 0;
        this.mAdvVideoPosition = 0;
        this.isSeekbarTouching = false;
        this.mForceQuality = false;
        this.mMarqueeShow = false;
        this.mDanmuShow = false;
        this.mHideType = null;
        this.isShowFullBtn = true;
        this.mHideHandler = new HideHandler(this);
        init();
    }

    private void findAllViews() {
        this.iv_comment = (ImageView) findViewById(R.id.iv_comment);
        this.iv_note = (ImageView) findViewById(R.id.iv_note);
        this.tvNoteCount = (TextView) findViewById(R.id.tv_note_count);
        this.tvCommentCount = (TextView) findViewById(R.id.tv_comment_count);
        this.tvUnlock = (TextView) findViewById(R.id.tv_unlock);
        this.tv_unlock_portrait = (TextView) findViewById(R.id.tv_unlock_portrait);
        this.mTitleBar = findViewById(R.id.titlebar);
        this.mControlBar = findViewById(R.id.controlbar);
        this.mTrackLinearLayout = (LinearLayout) findViewById(R.id.ll_track);
        this.tvKnowledge = (TextView) findViewById(R.id.tv_zsd);
        this.tvChooseChapter = (TextView) findViewById(R.id.tv_choose_chapter);
        this.mAudioTextView = (TextView) findViewById(R.id.tv_audio);
        this.mBitrateTextView = (TextView) findViewById(R.id.tv_bitrate);
        this.mSubtitleTextView = (TextView) findViewById(R.id.tv_subtitle);
        this.mDefinitionTextView = (TextView) findViewById(R.id.tv_definition);
        ImageView imageView = (ImageView) findViewById(R.id.iv_download);
        this.tv_teacher_name = (TextView) findViewById(R.id.tv_teacher_name);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mDownloadClickListener != null) {
                    ControlView.this.mDownloadClickListener.downloadClick();
                }
            }
        });
        this.tv_unlock_portrait.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mUnlockListener != null) {
                    ControlView.this.mUnlockListener.onLock();
                }
            }
        });
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 24) {
            this.supportPip = getContext().getPackageManager().hasSystemFeature("android.software.picture_in_picture") && i2 >= 26;
        }
        this.tvSpeedPlay = (TextView) findViewById(R.id.tv_speed);
        this.llLargeArea = findViewById(R.id.ll_large);
        this.tvSpeedPlay.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.isTryWatch) {
                    return;
                }
                ControlView.this.mTitleMore.performClick();
            }
        });
        if (getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android")) <= 0) {
            TypedValue.applyDimension(1, 25.0f, getResources().getDisplayMetrics());
        }
        this.rlSmallTimeShow = (RelativeLayout) findViewById(R.id.rl_small_time_show);
        findViewById(R.id.iv_collect_land).setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mAliyunScreenMode != AliyunScreenMode.Full || ControlView.this.mLandScapeActionListener == null) {
                    return;
                }
                ControlView.this.mLandScapeActionListener.onCollectClick();
            }
        });
        findViewById(R.id.ll_comment).setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mAliyunScreenMode != AliyunScreenMode.Full || ControlView.this.mLandScapeActionListener == null) {
                    return;
                }
                ControlView.this.mLandScapeActionListener.onCommentClick();
            }
        });
        findViewById(R.id.ll_note).setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mAliyunScreenMode != AliyunScreenMode.Full || ControlView.this.mLandScapeActionListener == null) {
                    return;
                }
                ControlView.this.mLandScapeActionListener.onNoteClick();
            }
        });
        this.tvKnowledge.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mLandScapeActionListener != null) {
                    ControlView.this.mLandScapeActionListener.onKnowledgePointClick();
                }
            }
        });
        this.tvChooseChapter.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mAliyunScreenMode != AliyunScreenMode.Full || ControlView.this.mLandScapeActionListener == null) {
                    return;
                }
                ControlView.this.mLandScapeActionListener.onChapterClick();
            }
        });
        this.mTitlebarBackBtn = (ImageView) findViewById(R.id.alivc_title_back);
        this.mTitlebarText = (TextView) findViewById(R.id.alivc_title_title);
        this.mTitleMore = (ImageView) findViewById(R.id.alivc_title_more);
        this.mScreenModeBtn = (ImageView) findViewById(R.id.alivc_screen_mode);
        this.mScreenLockBtn = (ImageView) findViewById(R.id.alivc_screen_lock);
        this.mPlayStateBtn = (ImageView) findViewById(R.id.alivc_player_state);
        this.mScreenShot = (ImageView) findViewById(R.id.alivc_screen_shot);
        this.mScreenRecorder = (ImageView) findViewById(R.id.alivc_screen_recoder);
        this.mLargeInfoBar = findViewById(R.id.alivc_info_large_bar);
        this.mLargePositionText = (TextView) findViewById(R.id.alivc_info_large_position);
        this.mLargeDurationText = (TextView) findViewById(R.id.alivc_info_large_duration);
        this.mLargeSeekbar = (DotSeekBar) findViewById(R.id.alivc_info_large_seekbar);
        this.mLargeChangeQualityBtn = (Button) findViewById(R.id.alivc_info_large_rate_btn);
        this.mSmallInfoBar = findViewById(R.id.alivc_info_small_bar);
        this.mSmallPositionText = (TextView) findViewById(R.id.alivc_info_small_position);
        this.mSmallDurationText = (TextView) findViewById(R.id.alivc_info_small_duration);
        this.mSmallSeekbar = (DotSeekBar) findViewById(R.id.alivc_info_small_seekbar);
        this.mSmallMutiSeekbar = (MutiSeekBarView) findViewById(R.id.alivc_info_small_mutiseekbar);
        this.mLargeMutiSeekbar = (MutiSeekBarView) findViewById(R.id.alivc_info_large_mutiseekbar);
        this.mInputDanmkuImageView = (ImageView) findViewById(R.id.iv_input_danmaku);
        this.mScreenCostLinearLayout = (LinearLayout) findViewById(R.id.screen_cost_ll);
        this.mScreenCostStateTextView = (TextView) findViewById(R.id.tv_screen_cost_state);
        this.mScreenCostExitTextView = (TextView) findViewById(R.id.tv_exit);
        updateViewShow(false);
    }

    private String getFormatCount(int i2) {
        if (i2 < 10000) {
            return String.valueOf(i2);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(i2 / 10000);
        int i3 = i2 % 10000;
        if (i3 > 1000) {
            sb.append(StrPool.DOT);
            sb.append(i3 / 1000);
        }
        sb.append("w");
        return sb.toString();
    }

    private List<TrackInfo> getTrackInfoListWithTrackInfoType(TrackInfo.Type type) {
        ArrayList arrayList = new ArrayList();
        MediaInfo mediaInfo = this.mAliyunMediaInfo;
        if (mediaInfo != null && mediaInfo.getTrackInfos() != null) {
            for (TrackInfo trackInfo : this.mAliyunMediaInfo.getTrackInfos()) {
                if (trackInfo.getType() == type) {
                    if (type == TrackInfo.Type.TYPE_SUBTITLE) {
                        if (!TextUtils.isEmpty(trackInfo.getSubtitleLang())) {
                            arrayList.add(trackInfo);
                        }
                    } else if (type == TrackInfo.Type.TYPE_AUDIO) {
                        if (!TextUtils.isEmpty(trackInfo.getAudioLang())) {
                            arrayList.add(trackInfo);
                        }
                    } else if (type == TrackInfo.Type.TYPE_VIDEO) {
                        if (trackInfo.getVideoBitrate() > 0) {
                            if (arrayList.size() == 0) {
                                arrayList.add(trackInfo);
                            }
                            arrayList.add(trackInfo);
                        }
                    } else if (type == TrackInfo.Type.TYPE_VOD && !TextUtils.isEmpty(trackInfo.getVodDefinition())) {
                        arrayList.add(trackInfo);
                    }
                }
            }
        }
        return arrayList;
    }

    private void hideDelayed() {
        this.mHideHandler.removeMessages(0);
        this.mHideHandler.sendEmptyMessageDelayed(0, 5000L);
    }

    private void hideQualityDialog() {
        OnQualityBtnClickListener onQualityBtnClickListener = this.mOnQualityBtnClickListener;
        if (onQualityBtnClickListener != null) {
            onQualityBtnClickListener.onHideQualityView();
        }
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.alivc_view_control, (ViewGroup) this, true);
        findAllViews();
        setViewListener();
        updateAllViews();
        updateDefinitionShow();
        boolean z2 = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID.equals(getContext().getPackageName()) ? SdkConstant.UMENG_ALIS : "hukaobang", 0).getBoolean(CommonParameter.SHOW_FULL_SCREEN_BTN, true);
        this.showFullScreenBtn = z2;
        this.mScreenModeBtn.setVisibility(z2 ? 0 : 8);
    }

    private boolean isVideoPositionInEnd(int i2) {
        MutiSeekBarView.AdvPosition advPosition = this.mAdvPosition;
        return (advPosition == MutiSeekBarView.AdvPosition.ALL || advPosition == MutiSeekBarView.AdvPosition.START_MIDDLE) ? ((long) i2) >= this.mSourceDuration + (this.mAdvDuration * 2) : (advPosition == MutiSeekBarView.AdvPosition.ONLY_START || advPosition == MutiSeekBarView.AdvPosition.ONLY_MIDDLE || advPosition == MutiSeekBarView.AdvPosition.START_END || advPosition == MutiSeekBarView.AdvPosition.MIDDLE_END) ? ((long) i2) >= this.mSourceDuration + this.mAdvDuration : ((long) i2) >= this.mSourceDuration;
    }

    private boolean isVideoPositionInMiddle(int i2) {
        MutiSeekBarView.AdvPosition advPosition = this.mAdvPosition;
        if (advPosition == MutiSeekBarView.AdvPosition.ALL || advPosition == MutiSeekBarView.AdvPosition.START_MIDDLE) {
            long j2 = i2;
            long j3 = this.mSourceDuration;
            long j4 = this.mAdvDuration;
            return j2 >= (j3 / 2) + j4 && j2 <= (j3 / 2) + (j4 * 2);
        }
        if (advPosition == MutiSeekBarView.AdvPosition.START_END || advPosition == MutiSeekBarView.AdvPosition.ONLY_START || advPosition == MutiSeekBarView.AdvPosition.ONLY_END) {
            return false;
        }
        long j5 = i2;
        long j6 = this.mSourceDuration;
        return j5 >= j6 / 2 && j5 <= (j6 / 2) + this.mAdvDuration;
    }

    private boolean isVideoPositionInStart(int i2) {
        return i2 >= 0 && ((long) i2) <= this.mAdvDuration;
    }

    private void judgeCurrentPlayingVideo() {
        MutiSeekBarView.AdvPosition advPosition = this.mAdvPosition;
        if (advPosition == null || !GlobalPlayerConfig.IS_VIDEO) {
            return;
        }
        switch (AnonymousClass41.$SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[advPosition.ordinal()]) {
            case 1:
                if (!isVideoPositionInStart(this.mAdvVideoPosition)) {
                    this.mCurrentVideoState = AdvVideoView.VideoState.VIDEO_SOURCE;
                    break;
                } else {
                    this.mCurrentVideoState = AdvVideoView.VideoState.VIDEO_ADV;
                    break;
                }
            case 2:
                if (!isVideoPositionInMiddle(this.mAdvVideoPosition)) {
                    this.mCurrentVideoState = AdvVideoView.VideoState.VIDEO_SOURCE;
                    break;
                } else {
                    this.mCurrentVideoState = AdvVideoView.VideoState.VIDEO_ADV;
                    break;
                }
            case 3:
                if (!isVideoPositionInEnd(this.mAdvVideoPosition)) {
                    this.mCurrentVideoState = AdvVideoView.VideoState.VIDEO_SOURCE;
                    break;
                } else {
                    this.mCurrentVideoState = AdvVideoView.VideoState.VIDEO_ADV;
                    break;
                }
            case 4:
                if (!isVideoPositionInStart(this.mAdvVideoPosition) && !isVideoPositionInEnd(this.mAdvVideoPosition)) {
                    this.mCurrentVideoState = AdvVideoView.VideoState.VIDEO_SOURCE;
                    break;
                } else {
                    this.mCurrentVideoState = AdvVideoView.VideoState.VIDEO_ADV;
                    break;
                }
                break;
            case 5:
                if (!isVideoPositionInMiddle(this.mAdvVideoPosition) && !isVideoPositionInEnd(this.mAdvVideoPosition)) {
                    this.mCurrentVideoState = AdvVideoView.VideoState.VIDEO_SOURCE;
                    break;
                } else {
                    this.mCurrentVideoState = AdvVideoView.VideoState.VIDEO_ADV;
                    break;
                }
                break;
            case 6:
                if (!isVideoPositionInStart(this.mAdvVideoPosition) && !isVideoPositionInMiddle(this.mAdvVideoPosition)) {
                    this.mCurrentVideoState = AdvVideoView.VideoState.VIDEO_SOURCE;
                    break;
                } else {
                    this.mCurrentVideoState = AdvVideoView.VideoState.VIDEO_ADV;
                    break;
                }
                break;
            case 7:
                if (!isVideoPositionInStart(this.mAdvVideoPosition) && !isVideoPositionInMiddle(this.mAdvVideoPosition) && !isVideoPositionInEnd(this.mAdvVideoPosition)) {
                    this.mCurrentVideoState = AdvVideoView.VideoState.VIDEO_SOURCE;
                    break;
                } else {
                    this.mCurrentVideoState = AdvVideoView.VideoState.VIDEO_ADV;
                    break;
                }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reLayoutDotView(long j2, final DotView dotView) {
        int measuredWidth = this.mLargeSeekbar.getMeasuredWidth();
        if (j2 <= 0 || measuredWidth <= 0) {
            return;
        }
        double dIntValue = ((measuredWidth * 1.0d) / j2) * Integer.valueOf(dotView.getDotTime()).intValue();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) dotView.getLayoutParams();
        layoutParams.leftMargin = (int) ((this.mLargeSeekbar.getPaddingLeft() + dIntValue) - (dotView.getRootWidth() / 2));
        dotView.setLayoutParams(layoutParams);
        dotView.setOnDotViewClickListener(new DotView.OnDotViewClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.40
            @Override // com.aliyun.player.alivcplayerexpand.view.dot.DotView.OnDotViewClickListener
            public void onDotViewClick() {
                int[] iArr = new int[2];
                dotView.getLocationInWindow(iArr);
                ControlView.this.mOnDotViewClickListener.onDotViewClick(iArr[0], iArr[1], dotView);
            }
        });
    }

    @SuppressLint({"SetTextI18n"})
    private void setAdvVideoTotalDuration() {
        MutiSeekBarView.AdvPosition advPosition = this.mAdvPosition;
        if (advPosition == null) {
            return;
        }
        switch (AnonymousClass41.$SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[advPosition.ordinal()]) {
            case 1:
            case 2:
            case 3:
                this.mSmallDurationText.setText(TimeFormater.formatMs(this.mAdvDuration + this.mSourceDuration));
                this.mLargeDurationText.setText(TimeFormater.formatMs(this.mAdvDuration + this.mSourceDuration));
                break;
            case 4:
            case 5:
            case 6:
                this.mSmallDurationText.setText(TimeFormater.formatMs((this.mAdvDuration * 2) + this.mSourceDuration));
                this.mLargeDurationText.setText(TimeFormater.formatMs((this.mAdvDuration * 2) + this.mSourceDuration));
                break;
            default:
                this.mSmallDurationText.setText(TimeFormater.formatMs((this.mAdvDuration * 3) + this.mSourceDuration));
                this.mLargeDurationText.setText(TimeFormater.formatMs((this.mAdvDuration * 3) + this.mSourceDuration));
                break;
        }
        updateDefinitionShow();
    }

    private void setViewListener() {
        this.tvUnlock.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mUnlockListener != null) {
                    ControlView.this.mUnlockListener.onLock();
                }
            }
        });
        this.mTitlebarBackBtn.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mOnBackClickListener != null) {
                    ControlView.this.mOnBackClickListener.onClick();
                }
                view.postDelayed(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.18.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (ControlView.this.mAliyunScreenMode == AliyunScreenMode.Small) {
                            ControlView.this.mScreenModeBtn.setVisibility(0);
                            ControlView.this.screenModeChange();
                        }
                        ControlView.this.getContext().sendBroadcast(new Intent().setAction("SCREEN_CHANGE").putExtra("full_screen", ControlView.this.mAliyunScreenMode == AliyunScreenMode.Full));
                    }
                }, 300L);
            }
        });
        this.mPlayStateBtn.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.19
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mOnPlayStateClickListener != null) {
                    ControlView.this.mOnPlayStateClickListener.onPlayStateClick();
                }
            }
        });
        this.mScreenLockBtn.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.20
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mOnScreenLockClickListener != null) {
                    ControlView.this.mOnScreenLockClickListener.onClick();
                }
            }
        });
        this.mScreenShot.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.21
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (FastClickUtil.isFastClick() || ControlView.this.mOnScreenShotClickListener == null) {
                    return;
                }
                ControlView.this.mOnScreenShotClickListener.onScreenShotClick();
            }
        });
        this.mScreenRecorder.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.22
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mOnScreenRecoderClickListener == null || GlobalPlayerConfig.IS_TRAILER || ControlView.this.mVideoPosition >= AliyunVodPlayerView.TRAILER) {
                    return;
                }
                ControlView.this.mOnScreenRecoderClickListener.onScreenRecoderClick();
            }
        });
        this.mScreenModeBtn.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.23
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mOnScreenModeClickListener != null) {
                    ControlView.this.mOnScreenModeClickListener.onClick();
                }
            }
        });
        this.mScreenCostExitTextView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.24
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mOnDLNAControlListener != null) {
                    ControlView.this.mOnDLNAControlListener.onExit();
                }
            }
        });
        this.mAudioTextView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.25
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mOnTrackInfoClickListener != null) {
                    ControlView.this.mOnTrackInfoClickListener.onAudioClick(ControlView.this.mAudioTrackInfoList);
                }
            }
        });
        this.mBitrateTextView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.26
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mOnTrackInfoClickListener != null) {
                    ControlView.this.mOnTrackInfoClickListener.onBitrateClick(ControlView.this.mBitrateTrackInfoList);
                }
            }
        });
        this.mSubtitleTextView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.27
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mOnTrackInfoClickListener != null) {
                    ControlView.this.mOnTrackInfoClickListener.onSubtitleClick(ControlView.this.mSubtitleTrackInfoList);
                }
            }
        });
        this.mDefinitionTextView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.28
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.isTryWatch) {
                    return;
                }
                if (ControlView.this.mAliyunScreenMode == AliyunScreenMode.Full) {
                    ControlView.this.mLargeChangeQualityBtn.performClick();
                } else if (ControlView.this.mOnTrackInfoClickListener != null) {
                    ControlView.this.mOnTrackInfoClickListener.onDefinitionClick(ControlView.this.mDefinitionTrackInfoList);
                }
            }
        });
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.29
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            @SuppressLint({"SetTextI18n"})
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z2) {
                if (z2) {
                    if (ControlView.this.mAliyunScreenMode == AliyunScreenMode.Full) {
                        ControlView.this.mLargePositionText.setText(TimeFormater.formatMs(i2) + " / ");
                    } else if (ControlView.this.mAliyunScreenMode == AliyunScreenMode.Small) {
                        ControlView.this.mSmallPositionText.setText(TimeFormater.formatMs(i2) + " / ");
                    }
                    if (ControlView.this.mOnSeekListener != null) {
                        ControlView.this.mOnSeekListener.onProgressChanged(i2);
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                ControlView.this.isSeekbarTouching = true;
                ControlView.this.mMutiSeekBarCurrentProgress = seekBar.getProgress();
                ControlView.this.mHideHandler.removeMessages(0);
                if (ControlView.this.mOnSeekListener != null) {
                    ControlView.this.mOnSeekListener.onSeekStart(seekBar.getProgress());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (ControlView.this.mOnSeekListener != null) {
                    ControlView.this.mOnSeekListener.onSeekEnd(seekBar.getProgress());
                }
                ControlView.this.isSeekbarTouching = false;
                ControlView.this.mHideHandler.removeMessages(0);
                ControlView.this.mHideHandler.sendEmptyMessageDelayed(0, 5000L);
            }
        };
        this.mLargeSeekbar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        this.mSmallSeekbar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        this.mLargeMutiSeekbar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        this.mSmallMutiSeekbar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        this.mLargeChangeQualityBtn.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.30
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.isTryWatch || ControlView.this.mOnQualityBtnClickListener == null || ControlView.this.mAliyunMediaInfo == null) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                for (TrackInfo trackInfo : ControlView.this.mAliyunMediaInfo.getTrackInfos()) {
                    if (trackInfo.getType() == TrackInfo.Type.TYPE_VOD) {
                        arrayList.add(trackInfo);
                    }
                }
                ControlView.this.mOnQualityBtnClickListener.onQualityBtnClick(view, arrayList, ControlView.this.mCurrentQuality);
            }
        });
        this.mTitleMore.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.31
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mOnShowMoreClickListener != null) {
                    ControlView.this.mOnShowMoreClickListener.showMore();
                }
            }
        });
        this.mInputDanmkuImageView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.32
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ControlView.this.mOnInputDanmakuClickListener != null) {
                    ControlView.this.mOnInputDanmakuClickListener.onInputDanmakuClick();
                }
            }
        });
    }

    private void updateAllControlBar() {
        boolean z2 = this.mControlBarCanShow && !this.mScreenLocked;
        View view = this.mControlBar;
        if (view != null) {
            view.setVisibility(z2 ? 0 : 4);
        }
    }

    private void updateAllTitleBar() {
        boolean z2 = this.mTitleBarCanShow && !this.mScreenLocked;
        Log.d("dsdasdasd5", this.mTitleBarCanShow + "updateAllTitleBar: " + this.mScreenLocked + "---" + z2);
        View view = this.mTitleBar;
        if (view != null) {
            view.setVisibility(z2 ? 0 : 4);
        }
    }

    private void updateAllViews() {
        updateTitleView();
        updateScreenLockBtn();
        updatePlayStateBtn();
        updateLargeInfoBar();
        updateSmallInfoBar();
        updateScreenModeBtn();
        updateAllTitleBar();
        updateAllControlBar();
        updateShowMoreBtn();
        updateScreenShotBtn();
        updateScreenRecorderBtn();
        updateInputDanmakuView();
        if (!this.supportPip || !this.showNewUi) {
            ImageView imageView = this.ivPip;
            if (imageView != null) {
                imageView.setVisibility(8);
                return;
            }
            return;
        }
        if (this.ivPip == null) {
            ImageView imageView2 = new ImageView(getContext());
            this.ivPip = imageView2;
            imageView2.setImageResource(R.drawable.ic_play_mode_pip);
            addView(this.ivPip);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.ivPip.getLayoutParams();
            layoutParams.width = (int) TypedValue.applyDimension(1, 40.0f, getContext().getResources().getDisplayMetrics());
            layoutParams.height = (int) TypedValue.applyDimension(1, 40.0f, getContext().getResources().getDisplayMetrics());
            layoutParams.rightMargin = (int) TypedValue.applyDimension(1, 16.0f, getContext().getResources().getDisplayMetrics());
            layoutParams.addRule(21);
            layoutParams.addRule(13);
            this.ivPip.setLayoutParams(layoutParams);
            this.ivPip.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.35
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    view.getContext().sendBroadcast(new Intent().setAction("OpenPipPlay"));
                    view.setVisibility(8);
                }
            });
        }
    }

    private void updateDefinitionShow() {
        List<TrackInfo> list = this.mDefinitionTrackInfoList;
        if (list == null || list.isEmpty()) {
            this.mDefinitionTextView.setVisibility(8);
        } else {
            this.mDefinitionTextView.setVisibility(0);
        }
    }

    private void updateDotView() {
        DotSeekBar dotSeekBar;
        if (this.mAliyunScreenMode != AliyunScreenMode.Full || (dotSeekBar = this.mLargeSeekbar) == null) {
            return;
        }
        dotSeekBar.post(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.34
            @Override // java.lang.Runnable
            public void run() {
                if (ControlView.this.mLargeSeekbar.getMeasuredWidth() == 0 || !ControlView.this.mLargeSeekbar.isShown()) {
                    return;
                }
                ControlView.this.initDotView();
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x00e2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateFunctionViewUi() {
        /*
            Method dump skipped, instructions count: 260
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.player.alivcplayerexpand.view.control.ControlView.updateFunctionViewUi():void");
    }

    private void updateInputDanmakuView() {
        AliyunScreenMode aliyunScreenMode = this.mAliyunScreenMode;
        if (aliyunScreenMode == AliyunScreenMode.Small || this.mScreenLocked || this.mInScreenCosting) {
            this.mInputDanmkuImageView.setVisibility(4);
        } else if (aliyunScreenMode == AliyunScreenMode.Full) {
            this.mInputDanmkuImageView.setVisibility(8);
        }
    }

    @SuppressLint({"SetTextI18n"})
    private void updateLargeInfoBar() {
        ImageView imageView;
        updateDefinitionShow();
        TextView textView = this.mTitlebarText;
        AliyunScreenMode aliyunScreenMode = this.mAliyunScreenMode;
        AliyunScreenMode aliyunScreenMode2 = AliyunScreenMode.Full;
        textView.setVisibility(aliyunScreenMode == aliyunScreenMode2 ? 0 : 8);
        AliyunScreenMode aliyunScreenMode3 = this.mAliyunScreenMode;
        if (aliyunScreenMode3 == AliyunScreenMode.Small) {
            this.mLargeInfoBar.setVisibility(8);
            findViewById(R.id.iv_download).setVisibility(8);
            if (this.supportPip && (imageView = this.ivPip) != null) {
                imageView.setVisibility(0);
            }
            this.tvChooseChapter.setVisibility(8);
            this.tvKnowledge.setVisibility(8);
            this.tvUnlock.setVisibility(8);
            this.llLargeArea.setVisibility(8);
            this.tv_unlock_portrait.setVisibility((this.unlock || !NetUtils.isNetworkConnected(getContext())) ? 8 : 0);
            this.mScreenModeBtn.setVisibility(this.showFullScreenBtn ? 0 : 8);
        } else if (aliyunScreenMode3 == aliyunScreenMode2) {
            this.tv_unlock_portrait.setVisibility(8);
            if (this.unlock) {
                this.tvUnlock.setVisibility(8);
            } else {
                this.tvUnlock.setVisibility(0);
            }
            findViewById(R.id.iv_download).setVisibility((this.showNewUi && this.unlock && NetUtils.isNetworkConnected(getContext())) ? 0 : 8);
            this.rlSmallTimeShow.setVisibility(8);
            this.tvKnowledge.setVisibility((this.showKnowledgePoint && this.showNewUi) ? 0 : 8);
            this.llLargeArea.setVisibility(this.showNewUi ? 0 : 8);
            this.tvChooseChapter.setVisibility((this.showChapter && this.showNewUi) ? 0 : 8);
            this.mScreenModeBtn.setVisibility(8);
            if (!GlobalPlayerConfig.IS_VIDEO || this.mInScreenCosting) {
                if (this.mAliyunMediaInfo != null) {
                    this.mLargeDurationText.setText(TimeFormater.formatMs(this.mMediaDuration));
                    this.mLargeSeekbar.setMax(this.mMediaDuration);
                } else {
                    this.mLargeDurationText.setText(TimeFormater.formatMs(0L));
                    this.mLargeSeekbar.setMax(0);
                }
                if (!this.isSeekbarTouching) {
                    this.mLargeSeekbar.setSecondaryProgress(this.mVideoBufferPosition);
                    this.mLargeSeekbar.setProgress(this.mVideoPosition);
                    this.mLargePositionText.setText(TimeFormater.formatMs(this.mVideoPosition) + " / ");
                }
                this.mLargeChangeQualityBtn.setText(QualityItem.getItem(getContext(), this.mCurrentQuality, this.isMtsSource).getName());
                this.mDefinitionTextView.setText(QualityItem.getItem(getContext(), this.mCurrentQuality, this.isMtsSource).getName());
            } else {
                setAdvVideoTotalDuration();
                this.mLargeMutiSeekbar.calculateWidth();
                if (!this.isSeekbarTouching) {
                    this.mLargeMutiSeekbar.setProgress(this.mAdvVideoPosition);
                    this.mLargePositionText.setText(TimeFormater.formatMs(this.mVideoPosition) + "/");
                }
            }
            this.mLargeInfoBar.setVisibility(0);
            float f2 = ((this.mVideoPosition * 1.0f) / this.mMediaDuration) * 100.0f;
            PlayProgressListener playProgressListener = this.mProgressListener;
            if (playProgressListener != null) {
                playProgressListener.onProgress(f2);
            }
        }
        updateFunctionViewUi();
    }

    private void updatePlayStateBtn() {
        PlayState playState = this.mPlayState;
        if (playState == PlayState.NotPlaying) {
            this.mPlayStateBtn.setImageResource(R.drawable.alivc_playstate_play);
        } else if (playState == PlayState.Playing) {
            this.mPlayStateBtn.setImageResource(R.drawable.alivc_playstate_pause);
        }
    }

    private void updateScreenLockBtn() {
        if (this.mScreenLocked) {
            this.mScreenLockBtn.setImageResource(R.drawable.alivc_screen_lock);
        } else {
            this.mScreenLockBtn.setImageResource(R.drawable.alivc_screen_unlock);
        }
        if (this.mAliyunScreenMode != AliyunScreenMode.Full || this.mInScreenCosting) {
            this.mScreenLockBtn.setVisibility(8);
        } else {
            this.mScreenLockBtn.setVisibility(0);
        }
        updateShowMoreBtn();
    }

    private void updateScreenModeBtn() {
    }

    private void updateScreenRecorderBtn() {
        this.mScreenRecorder.setVisibility(8);
    }

    private void updateScreenShotBtn() {
    }

    private void updateSeekBarTheme(Theme theme) {
        int i2 = R.drawable.alivc_info_seekbar_bg_blue;
        int i3 = R.drawable.alivc_info_seekbar_thumb_blue;
        if (theme == Theme.Blue) {
            i3 = R.drawable.alivc_seekbar_thumb_blue;
        } else if (theme == Theme.Green) {
            i2 = R.drawable.alivc_info_seekbar_bg_green;
            i3 = R.drawable.alivc_info_seekbar_thumb_green;
        } else if (theme == Theme.Orange) {
            i2 = R.drawable.alivc_info_seekbar_bg_orange;
            i3 = R.drawable.alivc_info_seekbar_thumb_orange;
        } else if (theme == Theme.Red) {
            i2 = R.drawable.alivc_info_seekbar_bg_red;
            i3 = R.drawable.alivc_info_seekbar_thumb_red;
        } else if (theme == Theme.WHITE) {
            i2 = R.drawable.alivc_info_seekbar_bg_white;
            i3 = R.drawable.play_progress_dot;
        }
        getResources();
        Drawable drawable = ContextCompat.getDrawable(getContext(), i2);
        Drawable drawable2 = ContextCompat.getDrawable(getContext(), i3);
        this.mSmallSeekbar.setProgressDrawable(drawable);
        this.mSmallSeekbar.setThumb(drawable2);
        this.mSmallMutiSeekbar.setThumb(ContextCompat.getDrawable(getContext(), i3));
        Drawable drawable3 = ContextCompat.getDrawable(getContext(), i2);
        Drawable drawable4 = ContextCompat.getDrawable(getContext(), i3);
        this.mLargeSeekbar.setProgressDrawable(drawable3);
        this.mLargeSeekbar.setThumb(drawable4);
        this.mLargeMutiSeekbar.setThumb(drawable4);
    }

    private void updateShowMoreBtn() {
        AliyunScreenMode aliyunScreenMode = AliyunScreenMode.Full;
    }

    @SuppressLint({"SetTextI18n"})
    private void updateSmallInfoBar() {
        ImageView imageView;
        ImageView imageView2;
        updateDefinitionShow();
        TextView textView = this.mTitlebarText;
        AliyunScreenMode aliyunScreenMode = this.mAliyunScreenMode;
        AliyunScreenMode aliyunScreenMode2 = AliyunScreenMode.Full;
        textView.setVisibility(aliyunScreenMode == aliyunScreenMode2 ? 0 : 8);
        AliyunScreenMode aliyunScreenMode3 = this.mAliyunScreenMode;
        if (aliyunScreenMode3 == aliyunScreenMode2) {
            this.mSmallInfoBar.setVisibility(4);
            this.mTitleBar.setVisibility(0);
            this.mLargeInfoBar.setVisibility(0);
            this.llLargeArea.setVisibility(this.showNewUi ? 0 : 8);
            this.tvKnowledge.setVisibility(this.showKnowledgePoint ? 0 : 8);
            this.tvChooseChapter.setVisibility(this.showChapter ? 0 : 8);
            if (this.supportPip && (imageView2 = this.ivPip) != null) {
                imageView2.setVisibility(8);
            }
            this.tv_unlock_portrait.setVisibility(8);
            if (this.unlock) {
                this.tvUnlock.setVisibility(8);
            } else {
                this.tvUnlock.setVisibility(0);
            }
            this.mScreenModeBtn.setVisibility(8);
        } else if (aliyunScreenMode3 == AliyunScreenMode.Small) {
            this.mTrackLinearLayout.setVisibility(8);
            this.mLargeInfoBar.setVisibility(8);
            this.rlSmallTimeShow.setVisibility(0);
            this.tvKnowledge.setVisibility(8);
            this.llLargeArea.setVisibility(8);
            if (this.supportPip && (imageView = this.ivPip) != null) {
                imageView.setVisibility(0);
            }
            this.tvUnlock.setVisibility(8);
            if (this.unlock || !NetUtils.isNetworkConnected(getContext())) {
                this.tv_unlock_portrait.setVisibility(8);
            } else {
                this.tv_unlock_portrait.setVisibility(0);
            }
            this.tvChooseChapter.setVisibility(8);
            this.mScreenModeBtn.setVisibility(this.showFullScreenBtn ? 0 : 8);
            if (!GlobalPlayerConfig.IS_VIDEO || this.mInScreenCosting) {
                if (this.mAliyunMediaInfo != null) {
                    this.mSmallDurationText.setText(TimeFormater.formatMs(this.mMediaDuration));
                    this.mSmallSeekbar.setMax(this.mMediaDuration);
                } else {
                    this.mSmallDurationText.setText(TimeFormater.formatMs(0L));
                    this.mSmallSeekbar.setMax(0);
                }
                if (!this.isSeekbarTouching) {
                    this.mSmallSeekbar.setSecondaryProgress(this.mVideoBufferPosition);
                    this.mSmallSeekbar.setProgress(this.mVideoPosition);
                    this.mSmallPositionText.setText(TimeFormater.formatMs(this.mVideoPosition) + "/");
                }
            } else {
                setAdvVideoTotalDuration();
                this.mSmallMutiSeekbar.calculateWidth();
                if (!this.isSeekbarTouching) {
                    this.mSmallMutiSeekbar.setProgress(this.mAdvVideoPosition);
                    this.mSmallPositionText.setText(TimeFormater.formatMs(this.mVideoPosition) + "/");
                }
            }
            this.mSmallInfoBar.setVisibility(0);
            float f2 = ((this.mVideoPosition * 1.0f) / this.mMediaDuration) * 100.0f;
            PlayProgressListener playProgressListener = this.mProgressListener;
            if (playProgressListener != null) {
                playProgressListener.onProgress(f2);
            }
        }
        updateFunctionViewUi();
    }

    private void updateTitleView() {
        String string = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID.equals(getContext().getPackageName()) ? SdkConstant.UMENG_ALIS : "hukaobang", 0).getString(CommonParameter.VIDEO_TITLE, "");
        if (!TextUtils.isEmpty(string)) {
            this.mTitlebarText.setText(string);
        }
        this.mTitlebarText.setVisibility(this.mAliyunScreenMode != AliyunScreenMode.Full ? 8 : 0);
    }

    private void updateTrackInfoView() {
        this.mAudioTrackInfoList = getTrackInfoListWithTrackInfoType(TrackInfo.Type.TYPE_AUDIO);
        this.mBitrateTrackInfoList = getTrackInfoListWithTrackInfoType(TrackInfo.Type.TYPE_VIDEO);
        this.mDefinitionTrackInfoList = getTrackInfoListWithTrackInfoType(TrackInfo.Type.TYPE_VOD);
        this.mSubtitleTrackInfoList = getTrackInfoListWithTrackInfoType(TrackInfo.Type.TYPE_SUBTITLE);
        List<TrackInfo> list = this.mAudioTrackInfoList;
        if (list == null || list.size() <= 0) {
            this.mAudioTextView.setVisibility(8);
        } else {
            this.mAudioTextView.setVisibility(0);
        }
        List<TrackInfo> list2 = this.mBitrateTrackInfoList;
        if (list2 == null || list2.size() <= 0) {
            this.mBitrateTextView.setVisibility(8);
        } else {
            this.mBitrateTextView.setVisibility(0);
        }
        List<TrackInfo> list3 = this.mSubtitleTrackInfoList;
        if (list3 == null || list3.size() <= 0) {
            this.mSubtitleTextView.setVisibility(8);
        } else {
            this.mSubtitleTextView.setVisibility(0);
        }
        List<TrackInfo> list4 = this.mDefinitionTrackInfoList;
        if (list4 == null || list4.size() == 0) {
            this.mDefinitionTextView.setVisibility(8);
        } else {
            this.mDefinitionTextView.setVisibility(8);
        }
    }

    private void updateViewShow(final boolean z2) {
        int iApplyDimension = (int) TypedValue.applyDimension(1, (TextUtils.isEmpty(this.teacherName) || this.mAliyunScreenMode == AliyunScreenMode.Small) ? 44 : 79, getContext().getResources().getDisplayMetrics());
        int iApplyDimension2 = (int) TypedValue.applyDimension(1, 100.0f, getContext().getResources().getDisplayMetrics());
        AnimatorSet animatorSet = new AnimatorSet();
        float[] fArr = new float[2];
        fArr[0] = z2 ? -iApplyDimension : 0.0f;
        fArr[1] = z2 ? 0.0f : -iApplyDimension;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(fArr);
        float[] fArr2 = new float[2];
        fArr2[0] = !z2 ? 1.0f : 0.0f;
        fArr2[1] = z2 ? 1.0f : 0.0f;
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(fArr2);
        float[] fArr3 = new float[2];
        fArr3[0] = z2 ? iApplyDimension2 : 0.0f;
        fArr3[1] = z2 ? 0.0f : iApplyDimension2;
        ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(fArr3);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ControlView.this.mTitleBar.setTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        valueAnimatorOfFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.10
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ControlView.this.mControlBar.setTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.11
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ControlView.this.mControlBar.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                ControlView.this.mTitleBar.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        valueAnimatorOfFloat.setDuration(250L);
        valueAnimatorOfFloat2.setDuration(250L);
        valueAnimatorOfFloat.setInterpolator(new AccelerateInterpolator());
        valueAnimatorOfFloat2.setInterpolator(new AccelerateInterpolator());
        valueAnimatorOfFloat3.setDuration(250L);
        valueAnimatorOfFloat3.setInterpolator(new AccelerateInterpolator());
        ArrayList arrayList = new ArrayList();
        arrayList.add(valueAnimatorOfFloat);
        arrayList.add(valueAnimatorOfFloat2);
        arrayList.add(valueAnimatorOfFloat3);
        animatorSet.playTogether(arrayList);
        postDelayed(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.12
            @Override // java.lang.Runnable
            public void run() {
                if (!z2) {
                    ControlView.this.setBackground(new ColorDrawable(0));
                } else {
                    ControlView controlView = ControlView.this;
                    controlView.setBackground(controlView.getContext().getDrawable(R.drawable.bg_control_bar));
                }
            }
        }, 100L);
        animatorSet.start();
    }

    public void addSummary(String str) {
        List list = (List) new Gson().fromJson(str, new TypeToken<List<VideoSummary>>() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.13
        }.getType());
        float fApplyDimension = TypedValue.applyDimension(1, 4.0f, getContext().getResources().getDisplayMetrics());
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            arrayList.add(new SummaryPoint(((VideoSummary) list.get(i2)).getRealPoint(), fApplyDimension, i2));
        }
        this.mSmallSeekbar.addIcons(arrayList);
        this.mLargeSeekbar.addIcons(arrayList);
        this.mSmallSeekbar.setOnIconClickListener(new DotSeekBar.OnIconClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.14
            @Override // com.aliyun.player.alivcplayerexpand.widget.DotSeekBar.OnIconClickListener
            public void onIconClick(SummaryPoint summaryPoint) {
                if (ControlView.this.mIconClickListener != null) {
                    ControlView.this.mIconClickListener.onIconClick(summaryPoint.position);
                }
            }
        });
        this.mLargeSeekbar.setOnIconClickListener(new DotSeekBar.OnIconClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.15
            @Override // com.aliyun.player.alivcplayerexpand.widget.DotSeekBar.OnIconClickListener
            public void onIconClick(SummaryPoint summaryPoint) {
                if (ControlView.this.mIconClickListener != null) {
                    ControlView.this.mIconClickListener.onIconClick(summaryPoint.position);
                }
            }
        });
    }

    public long afterSeekPlayStartPosition(long j2) {
        MutiSeekBarView mutiSeekBarView;
        MutiSeekBarView mutiSeekBarView2;
        AliyunScreenMode aliyunScreenMode = this.mAliyunScreenMode;
        return (aliyunScreenMode != AliyunScreenMode.Small || (mutiSeekBarView2 = this.mSmallMutiSeekbar) == null) ? (aliyunScreenMode != AliyunScreenMode.Full || (mutiSeekBarView = this.mLargeMutiSeekbar) == null) ? j2 : mutiSeekBarView.startPlayPosition(j2) : mutiSeekBarView2.startPlayPosition(j2);
    }

    public void closeAutoHide() {
        HideHandler hideHandler = this.mHideHandler;
        if (hideHandler != null) {
            hideHandler.removeMessages(0);
        }
        show();
    }

    public void exitScreenCost() {
        updateShowMoreBtn();
        updateScreenLockBtn();
        updateInputDanmakuView();
        ImageView imageView = this.mScreenModeBtn;
        if (imageView != null) {
            imageView.setVisibility(0);
        }
        LinearLayout linearLayout = this.mScreenCostLinearLayout;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        hideDelayed();
    }

    public AliyunScreenMode getAliyunScreenMode() {
        return this.mAliyunScreenMode;
    }

    public String getCurrentQuality() {
        return this.mCurrentQuality;
    }

    public AdvVideoView.VideoState getCurrentVideoState() {
        return this.mCurrentVideoState;
    }

    public List<TrackInfo> getDefinitionTrackInfoList() {
        return getTrackInfoListWithTrackInfoType(TrackInfo.Type.TYPE_VOD);
    }

    public AdvVideoView.IntentPlayVideo getIntentPlayVideo(int i2, int i3) {
        MutiSeekBarView mutiSeekBarView;
        MutiSeekBarView mutiSeekBarView2;
        AliyunScreenMode aliyunScreenMode = this.mAliyunScreenMode;
        return (aliyunScreenMode != AliyunScreenMode.Small || (mutiSeekBarView2 = this.mSmallMutiSeekbar) == null) ? (aliyunScreenMode != AliyunScreenMode.Full || (mutiSeekBarView = this.mLargeMutiSeekbar) == null) ? AdvVideoView.IntentPlayVideo.NORMAL : mutiSeekBarView.getIntentPlayVideo(i2, i3) : mutiSeekBarView2.getIntentPlayVideo(i2, i3);
    }

    public int getMutiSeekBarCurrentProgress() {
        return this.mMutiSeekBarCurrentProgress;
    }

    public int getVideoPosition() {
        return this.mVideoPosition;
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.interfaces.ViewAction
    public void hide(ViewAction.HideType hideType) {
        if (this.mHideType != ViewAction.HideType.End) {
            this.mHideType = hideType;
        }
        OnControlViewHideListener onControlViewHideListener = this.mOnControlViewHideListener;
        if (onControlViewHideListener != null) {
            onControlViewHideListener.onControlViewHide();
        }
        hideQualityDialog();
        updateViewShow(false);
        postDelayed(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.36
            @Override // java.lang.Runnable
            public void run() {
                ControlView.this.setVisibility(8);
            }
        }, 200L);
    }

    public void hideMoreButton() {
    }

    public void hideNativeSeekBar() {
        MutiSeekBarView mutiSeekBarView = this.mSmallMutiSeekbar;
        if (mutiSeekBarView != null) {
            mutiSeekBarView.setVisibility(0);
            this.mSmallMutiSeekbar.post(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.37
                @Override // java.lang.Runnable
                public void run() {
                    ControlView.this.mSmallMutiSeekbar.setTime(ControlView.this.mAdvDuration, ControlView.this.mSourceDuration, ControlView.this.mAdvPosition);
                }
            });
        }
        MutiSeekBarView mutiSeekBarView2 = this.mLargeMutiSeekbar;
        if (mutiSeekBarView2 != null) {
            mutiSeekBarView2.setVisibility(0);
            this.mLargeMutiSeekbar.post(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.38
                @Override // java.lang.Runnable
                public void run() {
                    ControlView.this.mLargeMutiSeekbar.setTime(ControlView.this.mAdvDuration, ControlView.this.mSourceDuration, ControlView.this.mAdvPosition);
                }
            });
        }
        if (this.mInScreenCosting) {
            MutiSeekBarView mutiSeekBarView3 = this.mSmallMutiSeekbar;
            if (mutiSeekBarView3 != null) {
                mutiSeekBarView3.setVisibility(8);
            }
            MutiSeekBarView mutiSeekBarView4 = this.mLargeMutiSeekbar;
            if (mutiSeekBarView4 != null) {
                mutiSeekBarView4.setVisibility(8);
                return;
            }
            return;
        }
        DotSeekBar dotSeekBar = this.mSmallSeekbar;
        if (dotSeekBar != null) {
            dotSeekBar.setVisibility(8);
        }
        DotSeekBar dotSeekBar2 = this.mLargeSeekbar;
        if (dotSeekBar2 != null) {
            dotSeekBar2.setVisibility(8);
        }
    }

    public void initDotView() {
        if (this.mDotBean == null) {
            return;
        }
        FrameLayout frameLayout = (FrameLayout) this.mLargeSeekbar.getParent();
        ArrayList<DotView> arrayList = new ArrayList();
        for (DotBean dotBean : this.mDotBean) {
            DotView dotView = new DotView(getContext());
            dotView.setDotTime(dotBean.getTime());
            dotView.setDotMsg(dotBean.getContent());
            arrayList.add(dotView);
            frameLayout.removeView(dotView);
            frameLayout.addView(dotView);
        }
        for (final DotView dotView2 : arrayList) {
            dotView2.post(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.39
                @Override // java.lang.Runnable
                public void run() {
                    if (ControlView.this.mAliyunMediaInfo == null) {
                        return;
                    }
                    ControlView.this.reLayoutDotView(r0.mAliyunMediaInfo.getDuration() / 1000, dotView2);
                }
            });
        }
    }

    public boolean isNeedToPause(int i2, int i3) {
        if (this.mSourceDuration <= 0) {
            return false;
        }
        switch (AnonymousClass41.$SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[this.mAdvPosition.ordinal()]) {
            case 1:
            default:
                return false;
            case 2:
                if (i2 < this.mSourceDuration / 2 || i3 != 0) {
                    return false;
                }
                break;
            case 3:
            case 4:
                if (i2 < this.mSourceDuration) {
                    return false;
                }
                break;
            case 5:
            case 7:
                long j2 = i2;
                long j3 = this.mSourceDuration;
                if ((j2 < j3 / 2 || i3 != 1) && j2 < j3) {
                    return false;
                }
                break;
            case 6:
                if (i2 < this.mSourceDuration / 2) {
                    return false;
                }
                break;
        }
        return true;
    }

    @Override // android.view.View
    public void onVisibilityChanged(@Nullable View view, int i2) {
        super.onVisibilityChanged(view, i2);
        if (i2 == 0) {
            hideDelayed();
        }
    }

    public void openAutoHide() {
        this.isSeekbarTouching = false;
        if (this.mHideHandler != null) {
            hideDelayed();
        }
    }

    public void removeBarSummaryPoint() {
        this.mSmallSeekbar.addIcons(new ArrayList());
        this.mLargeSeekbar.addIcons(new ArrayList());
    }

    public void removeHideCallBack() {
        this.mHideHandler.removeCallbacksAndMessages(null);
        this.mHideType = ViewAction.HideType.Normal;
        this.isSeekbarTouching = true;
        setVisibility(0);
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.interfaces.ViewAction
    public void reset() {
        this.mHideType = null;
        this.mAliyunMediaInfo = null;
        this.mVideoPosition = 0;
        this.mPlayState = PlayState.NotPlaying;
        this.isSeekbarTouching = false;
        showNativeSeekBar();
        updateAllViews();
    }

    public void screenModeChange() {
        TextView textView;
        updateFunctionViewUi();
        AliyunScreenMode aliyunScreenMode = this.mAliyunScreenMode;
        AliyunScreenMode aliyunScreenMode2 = AliyunScreenMode.Full;
        if (aliyunScreenMode == aliyunScreenMode2) {
            if (this.tv_teacher_name != null && !TextUtils.isEmpty(this.teacherName)) {
                this.tv_teacher_name.setVisibility(0);
                this.tv_teacher_name.setText(this.teacherName);
            }
            this.mTitleBar.setTranslationY(0.0f);
            this.mTitleBar.setAlpha(1.0f);
            this.mControlBar.setTranslationY(0.0f);
            this.mControlBar.setAlpha(1.0f);
            ImageView imageView = this.ivPip;
            if (imageView != null) {
                imageView.setVisibility(8);
            }
            this.mScreenModeBtn.setVisibility(8);
            this.tv_unlock_portrait.setVisibility(8);
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID.equals(getContext().getPackageName()) ? SdkConstant.UMENG_ALIS : "hukaobang", 0);
            int i2 = sharedPreferences.getInt(CommonParameter.NOTE_COUNT, 0);
            int i3 = sharedPreferences.getInt(CommonParameter.COMMENT_COUNT, 0);
            if (i2 > 0) {
                this.tvNoteCount.setText(getFormatCount(i2));
                this.iv_note.setSelected(true);
            } else {
                this.tvNoteCount.setText("");
                this.iv_note.setSelected(false);
            }
            if (i3 > 0) {
                this.iv_comment.setSelected(true);
                this.tvCommentCount.setText(getFormatCount(i3));
            } else {
                this.iv_comment.setSelected(false);
                this.tvCommentCount.setText("");
            }
            findViewById(R.id.ll_comment).post(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.33
                @Override // java.lang.Runnable
                public void run() {
                    int measuredWidth = ControlView.this.findViewById(R.id.ll_comment).getMeasuredWidth();
                    if (measuredWidth > 0) {
                        int measuredWidth2 = measuredWidth - ControlView.this.findViewById(R.id.iv_comment).getMeasuredWidth();
                        Log.d("comment_text_place_width", "" + measuredWidth2);
                        if (measuredWidth2 > 0) {
                            View viewFindViewById = ControlView.this.findViewById(R.id.ll_note);
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewFindViewById.getLayoutParams();
                            layoutParams.leftMargin = ((int) TypedValue.applyDimension(1, 33.0f, ControlView.this.getContext().getResources().getDisplayMetrics())) - measuredWidth2;
                            viewFindViewById.setLayoutParams(layoutParams);
                        }
                    }
                }
            });
            updateLargeInfoBar();
        } else {
            ImageView imageView2 = this.ivPip;
            if (imageView2 != null) {
                imageView2.setVisibility(0);
            }
            TextView textView2 = this.tv_teacher_name;
            if (textView2 != null) {
                textView2.setVisibility(8);
            }
            this.tv_unlock_portrait.setVisibility((this.unlock || !NetUtils.isNetworkConnected(getContext())) ? 8 : 0);
            this.mControlBar.setTranslationY(0.0f);
            this.mControlBar.setAlpha(1.0f);
            this.mTitleBar.setTranslationY(0.0f);
            this.mTitleBar.setAlpha(1.0f);
            setBackground(getContext().getDrawable(R.drawable.bg_control_bar));
            updateSmallInfoBar();
        }
        getContext().sendBroadcast(new Intent().setAction("SCREEN_CHANGE").putExtra("full_screen", this.mAliyunScreenMode == aliyunScreenMode2));
        ViewGroup.LayoutParams layoutParams = this.mTitleBar.getLayoutParams();
        layoutParams.height = (int) TypedValue.applyDimension(1, (TextUtils.isEmpty(this.teacherName) || this.mAliyunScreenMode != aliyunScreenMode2) ? 44.0f : 79.0f, getContext().getResources().getDisplayMetrics());
        this.mTitleBar.setLayoutParams(layoutParams);
        if (TextUtils.isEmpty(this.teacherName) || (textView = this.tv_teacher_name) == null || this.mAliyunScreenMode != aliyunScreenMode2) {
            TextView textView3 = this.tv_teacher_name;
            if (textView3 != null) {
                textView3.setVisibility(8);
            }
        } else {
            textView.setVisibility(0);
            this.tv_teacher_name.setText(this.teacherName);
        }
        ViewGroup.LayoutParams layoutParams2 = this.mControlBar.getLayoutParams();
        int iApplyDimension = (int) TypedValue.applyDimension(1, 100.0f, getContext().getResources().getDisplayMetrics());
        if (layoutParams2.height != iApplyDimension) {
            layoutParams2.height = iApplyDimension;
            this.mControlBar.setLayoutParams(layoutParams2);
        }
        this.tvSpeedPlay.setTextSize(2, this.mAliyunScreenMode == aliyunScreenMode2 ? 15.0f : 12.0f);
        this.mDefinitionTextView.setTextSize(2, this.mAliyunScreenMode == aliyunScreenMode2 ? 15.0f : 12.0f);
        ((TextView) findViewById(R.id.tv_zsd)).setTextSize(2, this.mAliyunScreenMode == aliyunScreenMode2 ? 15.0f : 12.0f);
        ((TextView) findViewById(R.id.tv_choose_chapter)).setTextSize(2, this.mAliyunScreenMode != aliyunScreenMode2 ? 12.0f : 15.0f);
    }

    public void setAdvVideoPosition(int i2, int i3) {
        this.mAdvVideoPosition = i2;
        this.mVideoPosition = i3;
        updateSmallInfoBar();
        updateLargeInfoBar();
    }

    public void setControlBarCanShow(boolean z2) {
        this.mControlBarCanShow = z2;
        updateAllControlBar();
    }

    public void setCurrentQuality(String str) {
        this.mCurrentQuality = str;
        updateLargeInfoBar();
    }

    public void setDotInfo(List<DotBean> list) {
        this.mDotBean = list;
    }

    public void setDownloadClickListener(DownloadClickListener downloadClickListener) {
        this.mDownloadClickListener = downloadClickListener;
    }

    public void setForceQuality(boolean z2) {
        this.mForceQuality = z2;
    }

    public void setHideType(ViewAction.HideType hideType) {
        this.mHideType = hideType;
    }

    public void setIconClickListener(SummaryIconClickListener summaryIconClickListener) {
        this.mIconClickListener = summaryIconClickListener;
    }

    public void setInScreenCosting(boolean z2) {
        this.mInScreenCosting = z2;
    }

    public void setIsMtsSource(boolean z2) {
        this.isMtsSource = z2;
    }

    public void setLandScapeActionListener(OnLandScapeActionListener onLandScapeActionListener) {
        this.mLandScapeActionListener = onLandScapeActionListener;
    }

    public void setMediaDuration(int i2) {
        this.mMediaDuration = i2;
        updateLargeInfoBar();
        updateSmallInfoBar();
    }

    public void setMediaInfo(MediaInfo mediaInfo, String str) {
        this.mAliyunMediaInfo = mediaInfo;
        this.mMediaDuration = mediaInfo.getDuration();
        this.mCurrentQuality = str;
        this.mDefinitionTrackInfoList = getTrackInfoListWithTrackInfoType(TrackInfo.Type.TYPE_VOD);
        updateVideoDefinitionShow(str);
        updateLargeInfoBar();
    }

    public void setMutiSeekBarInfo(long j2, long j3, MutiSeekBarView.AdvPosition advPosition) {
        this.mAdvDuration = j2;
        this.mSourceDuration = j3;
        this.mAdvPosition = advPosition;
    }

    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.mOnBackClickListener = onBackClickListener;
    }

    public void setOnControlViewHideListener(OnControlViewHideListener onControlViewHideListener) {
        this.mOnControlViewHideListener = onControlViewHideListener;
    }

    public void setOnDLNAControlListener(OnDLNAControlListener onDLNAControlListener) {
        this.mOnDLNAControlListener = onDLNAControlListener;
    }

    public void setOnDotViewClickListener(OnDotViewClickListener onDotViewClickListener) {
        this.mOnDotViewClickListener = onDotViewClickListener;
    }

    public void setOnInputDanmakuClickListener(OnInputDanmakuClickListener onInputDanmakuClickListener) {
        this.mOnInputDanmakuClickListener = onInputDanmakuClickListener;
    }

    public void setOnPlayStateClickListener(OnPlayStateClickListener onPlayStateClickListener) {
        this.mOnPlayStateClickListener = onPlayStateClickListener;
    }

    public void setOnQualityBtnClickListener(OnQualityBtnClickListener onQualityBtnClickListener) {
        this.mOnQualityBtnClickListener = onQualityBtnClickListener;
    }

    public void setOnScreenLockClickListener(OnScreenLockClickListener onScreenLockClickListener) {
        this.mOnScreenLockClickListener = onScreenLockClickListener;
    }

    public void setOnScreenModeClickListener(OnScreenModeClickListener onScreenModeClickListener) {
        this.mOnScreenModeClickListener = onScreenModeClickListener;
    }

    public void setOnScreenRecoderClickListener(OnScreenRecoderClickListener onScreenRecoderClickListener) {
        this.mOnScreenRecoderClickListener = onScreenRecoderClickListener;
    }

    public void setOnScreenShotClickListener(OnScreenShotClickListener onScreenShotClickListener) {
        this.mOnScreenShotClickListener = onScreenShotClickListener;
    }

    public void setOnSeekListener(OnSeekListener onSeekListener) {
        this.mOnSeekListener = onSeekListener;
    }

    public void setOnShowMoreClickListener(OnShowMoreClickListener onShowMoreClickListener) {
        this.mOnShowMoreClickListener = onShowMoreClickListener;
    }

    public void setOnTrackInfoClickListener(OnTrackInfoClickListener onTrackInfoClickListener) {
        this.mOnTrackInfoClickListener = onTrackInfoClickListener;
    }

    public void setOtherEnable(boolean z2) {
        DotSeekBar dotSeekBar = this.mSmallSeekbar;
        if (dotSeekBar != null) {
            dotSeekBar.setEnabled(z2);
        }
        DotSeekBar dotSeekBar2 = this.mLargeSeekbar;
        if (dotSeekBar2 != null) {
            dotSeekBar2.setEnabled(z2);
        }
        ImageView imageView = this.mPlayStateBtn;
        if (imageView != null) {
            imageView.setEnabled(z2);
        }
        ImageView imageView2 = this.mScreenLockBtn;
        if (imageView2 != null) {
            imageView2.setEnabled(z2);
        }
        Button button = this.mLargeChangeQualityBtn;
        if (button != null) {
            button.setEnabled(z2);
        }
        ImageView imageView3 = this.mTitleMore;
        if (imageView3 != null) {
            imageView3.setEnabled(z2);
        }
        ImageView imageView4 = this.mInputDanmkuImageView;
        if (imageView4 != null) {
            imageView4.setEnabled(z2);
        }
    }

    public void setPlayProgressListener(PlayProgressListener playProgressListener) {
        this.mProgressListener = playProgressListener;
    }

    public void setPlayState(PlayState playState) {
        this.mPlayState = playState;
        updatePlayStateBtn();
        updateDefinitionShow();
    }

    public void setScreenLockStatus(boolean z2) {
        this.mScreenLocked = z2;
        updateScreenLockBtn();
        updateAllTitleBar();
        updateAllControlBar();
        updateShowMoreBtn();
        updateScreenShotBtn();
        updateScreenRecorderBtn();
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.interfaces.ViewAction
    public void setScreenModeStatus(AliyunScreenMode aliyunScreenMode) {
        this.mAliyunScreenMode = aliyunScreenMode;
        View viewFindViewById = findViewById(R.id.ctl_pannel);
        AliyunScreenMode aliyunScreenMode2 = AliyunScreenMode.Full;
        int iApplyDimension = (int) TypedValue.applyDimension(1, aliyunScreenMode == aliyunScreenMode2 ? 18.0f : 11.0f, getResources().getDisplayMetrics());
        viewFindViewById.setPadding(0, iApplyDimension, 0, iApplyDimension);
        if (getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android")) <= 0) {
            TypedValue.applyDimension(1, 25.0f, getResources().getDisplayMetrics());
        }
        updateLargeInfoBar();
        updateSmallInfoBar();
        updateScreenLockBtn();
        updateScreenModeBtn();
        updateShowMoreBtn();
        updateScreenShotBtn();
        updateScreenRecorderBtn();
        updateInputDanmakuView();
        updateDotView();
        if (aliyunScreenMode == aliyunScreenMode2) {
            updateTrackInfoView();
        }
    }

    @Override // com.aliyun.player.alivcplayerexpand.theme.ITheme
    public void setTheme(Theme theme) {
        updateSeekBarTheme(theme);
    }

    public void setTitleBarCanShow(boolean z2) {
        this.mTitleBarCanShow = z2;
        updateAllTitleBar();
    }

    public void setTotalPosition(long j2) {
        this.mAdvTotalPosition = j2;
    }

    public void setTryWatchTime(long j2) {
        this.isTryWatch = j2 > 0;
        if (j2 <= 0) {
            this.tv_unlock_portrait.setText("点击获取完整视频");
            return;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("可试看" + j2 + "秒");
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFC100")), 0, spannableStringBuilder.length(), 18);
        int length = spannableStringBuilder.length();
        spannableStringBuilder.append((CharSequence) "，点击获取完整视频");
        spannableStringBuilder.setSpan(new ForegroundColorSpan(-1), length, spannableStringBuilder.length(), 18);
        this.tv_unlock_portrait.setText(spannableStringBuilder);
    }

    public void setUnlockListener(UnlockListener unlockListener) {
        this.mUnlockListener = unlockListener;
    }

    public void setVideoBufferPosition(int i2) {
        this.mVideoBufferPosition = i2;
        updateSmallInfoBar();
        updateLargeInfoBar();
        updateDefinitionShow();
    }

    public void setVideoPosition(int i2) {
        this.mVideoPosition = i2;
        judgeCurrentPlayingVideo();
        updateSmallInfoBar();
        updateLargeInfoBar();
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.interfaces.ViewAction
    public void show() {
        if (this.mHideType == ViewAction.HideType.End) {
            setVisibility(8);
            hideQualityDialog();
        } else {
            updateAllViews();
            setVisibility(0);
        }
        updateViewShow(true);
    }

    public void showChooseChapter(boolean z2) {
        this.tvKnowledge.setVisibility(z2 ? 0 : 8);
    }

    public void showKnowledge(boolean z2) {
        this.tvKnowledge.setVisibility(z2 ? 0 : 8);
        if (z2) {
            return;
        }
        removeBarSummaryPoint();
    }

    public void showMoreButton() {
    }

    public void showNativeSeekBar() {
        DotSeekBar dotSeekBar = this.mSmallSeekbar;
        if (dotSeekBar != null) {
            dotSeekBar.setVisibility(0);
        }
        DotSeekBar dotSeekBar2 = this.mLargeSeekbar;
        if (dotSeekBar2 != null) {
            dotSeekBar2.setVisibility(0);
        }
        MutiSeekBarView mutiSeekBarView = this.mSmallMutiSeekbar;
        if (mutiSeekBarView != null) {
            mutiSeekBarView.setVisibility(8);
        }
        MutiSeekBarView mutiSeekBarView2 = this.mLargeMutiSeekbar;
        if (mutiSeekBarView2 != null) {
            mutiSeekBarView2.setVisibility(8);
        }
    }

    public void showNewUiStyle(boolean z2) {
        this.showNewUi = z2;
        boolean zIsNetworkConnected = NetUtils.isNetworkConnected(getContext());
        findViewById(R.id.ll_large).setVisibility(z2 ? 0 : 8);
        findViewById(R.id.tv_definition).setVisibility((z2 && zIsNetworkConnected) ? 0 : 8);
        findViewById(R.id.tv_zsd).setVisibility((z2 && zIsNetworkConnected) ? 0 : 8);
        findViewById(R.id.tv_choose_chapter).setVisibility((z2 && zIsNetworkConnected) ? 0 : 8);
        findViewById(R.id.tv_unlock).setVisibility((z2 && zIsNetworkConnected) ? 0 : 8);
        findViewById(R.id.iv_download).setVisibility((z2 && zIsNetworkConnected) ? 0 : 8);
        if (getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android")) <= 0) {
            TypedValue.applyDimension(1, 25.0f, getResources().getDisplayMetrics());
        }
    }

    public void showOrHiddenFullBtn(boolean z2) {
        this.isShowFullBtn = z2;
        this.mTitlebarBackBtn.setVisibility(z2 ? 0 : 8);
        this.mScreenModeBtn.setVisibility(z2 ? 0 : 8);
    }

    public void startScreenCost() {
        ImageView imageView = this.mScreenModeBtn;
        if (imageView != null) {
            imageView.setVisibility(4);
        }
        LinearLayout linearLayout = this.mScreenCostLinearLayout;
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
        updateScreenCostPlayStateBtn(false);
        showNativeSeekBar();
        updateShowMoreBtn();
        updateScreenLockBtn();
        updateInputDanmakuView();
    }

    public void updateCollect(boolean z2) {
        findViewById(R.id.iv_collect_land).setSelected(z2);
    }

    public void updateCommentAndNoteCount(String str, String str2) {
        if (TextUtils.isEmpty(str) || !str.matches(RegexPool.NUMBERS)) {
            this.tvCommentCount.setText("");
            this.iv_comment.setSelected(false);
        } else {
            int i2 = Integer.parseInt(str);
            this.tvCommentCount.setText(i2 > 0 ? getFormatCount(i2) : "");
            this.iv_comment.setSelected(true);
        }
        findViewById(R.id.ll_comment).post(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.view.control.ControlView.16
            @Override // java.lang.Runnable
            public void run() {
                int measuredWidth = ControlView.this.findViewById(R.id.ll_comment).getMeasuredWidth();
                if (measuredWidth > 0) {
                    int measuredWidth2 = measuredWidth - ControlView.this.findViewById(R.id.iv_comment).getMeasuredWidth();
                    Log.d("comment_text_place_width", "" + measuredWidth2);
                    if (measuredWidth2 > 0) {
                        View viewFindViewById = ControlView.this.findViewById(R.id.ll_note);
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewFindViewById.getLayoutParams();
                        layoutParams.leftMargin = ((int) TypedValue.applyDimension(1, 33.0f, ControlView.this.getContext().getResources().getDisplayMetrics())) - measuredWidth2;
                        viewFindViewById.setLayoutParams(layoutParams);
                    }
                }
            }
        });
        if (TextUtils.isEmpty(str2) || !str2.matches(RegexPool.NUMBERS)) {
            this.tvNoteCount.setText("");
            this.iv_note.setSelected(false);
        } else {
            int i3 = Integer.parseInt(str2);
            this.tvNoteCount.setText(i3 > 0 ? getFormatCount(i3) : "");
            this.iv_note.setSelected(i3 > 0);
        }
    }

    public void updateScreenCostPlayStateBtn(boolean z2) {
        if (z2) {
            this.mPlayStateBtn.setImageResource(R.drawable.alivc_playstate_play);
        } else {
            this.mPlayStateBtn.setImageResource(R.drawable.alivc_playstate_pause);
        }
    }

    public void updateShow(boolean z2, boolean z3) {
        this.showChapter = z2;
        this.showKnowledgePoint = z3;
        this.tvKnowledge.setVisibility(z3 ? 0 : 8);
        this.tvChooseChapter.setVisibility(z2 ? 0 : 8);
        if (this.mAliyunScreenMode == AliyunScreenMode.Full) {
            findViewById(R.id.iv_download).setVisibility(0);
        } else {
            findViewById(R.id.iv_download).setVisibility(8);
        }
        if (z3) {
            return;
        }
        removeBarSummaryPoint();
    }

    @SuppressLint({"SetTextI18n"})
    public void updateSpeedPlayShow(int i2) {
        if (i2 == 0) {
            this.tvSpeedPlay.setText("0.5X");
        }
        if (i2 == 1) {
            this.tvSpeedPlay.setText("1.0X");
        }
        if (i2 == 2) {
            this.tvSpeedPlay.setText("1.25X");
        }
        if (i2 == 3) {
            this.tvSpeedPlay.setText("1.5X");
        }
        if (i2 == 4) {
            this.tvSpeedPlay.setText("2X");
        }
    }

    public void updateUnlockShow(boolean z2) {
        this.unlock = z2;
        this.tvUnlock.setVisibility((z2 || !this.showNewUi) ? 8 : 0);
    }

    public void updateVideoDefinitionShow(String str) {
        if (str != null) {
            str.hashCode();
            switch (str) {
                case "0D":
                    this.mDefinitionTextView.setText("原画");
                    break;
                case "FD":
                    this.mDefinitionTextView.setText("流畅");
                    break;
                case "FK":
                    this.mDefinitionTextView.setText(QualityValue.QUALITY_4K);
                    break;
                case "HD":
                    this.mDefinitionTextView.setText("超清");
                    break;
                case "LD":
                    this.mDefinitionTextView.setText("标清");
                    break;
                case "SD":
                    this.mDefinitionTextView.setText("高清");
                    break;
                case "TK":
                    this.mDefinitionTextView.setText(QualityValue.QUALITY_2K);
                    break;
            }
        }
    }

    public void updateVideoTitle(String str, String str2) {
        TextView textView;
        this.mTitlebarText.setText(str);
        this.teacherName = str2;
        ViewGroup.LayoutParams layoutParams = this.mTitleBar.getLayoutParams();
        layoutParams.height = (int) TypedValue.applyDimension(1, (TextUtils.isEmpty(str2) || this.mAliyunScreenMode != AliyunScreenMode.Full) ? 44.0f : 79.0f, getContext().getResources().getDisplayMetrics());
        this.mTitleBar.setLayoutParams(layoutParams);
        if (!TextUtils.isEmpty(str2) && (textView = this.tv_teacher_name) != null && this.mAliyunScreenMode == AliyunScreenMode.Full) {
            textView.setVisibility(0);
            this.tv_teacher_name.setText(str2);
        } else {
            TextView textView2 = this.tv_teacher_name;
            if (textView2 != null) {
                textView2.setVisibility(8);
            }
        }
    }

    public ControlView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTitleBarCanShow = true;
        this.mControlBarCanShow = true;
        this.mPlayState = PlayState.NotPlaying;
        this.mScreenLocked = false;
        this.mAliyunScreenMode = AliyunScreenMode.Small;
        this.mVideoPosition = 0;
        this.mAdvVideoPosition = 0;
        this.isSeekbarTouching = false;
        this.mForceQuality = false;
        this.mMarqueeShow = false;
        this.mDanmuShow = false;
        this.mHideType = null;
        this.isShowFullBtn = true;
        this.mHideHandler = new HideHandler(this);
        init();
    }

    public ControlView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mTitleBarCanShow = true;
        this.mControlBarCanShow = true;
        this.mPlayState = PlayState.NotPlaying;
        this.mScreenLocked = false;
        this.mAliyunScreenMode = AliyunScreenMode.Small;
        this.mVideoPosition = 0;
        this.mAdvVideoPosition = 0;
        this.isSeekbarTouching = false;
        this.mForceQuality = false;
        this.mMarqueeShow = false;
        this.mDanmuShow = false;
        this.mHideType = null;
        this.isShowFullBtn = true;
        this.mHideHandler = new HideHandler(this);
        init();
    }
}
