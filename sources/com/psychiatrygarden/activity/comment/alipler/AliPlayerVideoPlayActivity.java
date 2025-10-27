package com.psychiatrygarden.activity.comment.alipler;

import android.app.Activity;
import android.app.Application;
import android.app.PictureInPictureParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Rational;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.IPlayer;
import com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadManager;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.alivcplayerexpand.view.control.ControlView;
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView;
import com.aliyun.svideo.common.utils.NetUtils;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.ActivityLifecycleCallbacks;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CourseDownCacheActivity;
import com.psychiatrygarden.activity.SetNewAvtivity;
import com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.comment.widget.BottomInputView;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.bean.CurriculumScheduleCardBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.activity.online.AnswerQuestionActivity;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.adapter.VideoSummaryAdapter;
import com.psychiatrygarden.aliPlayer.utils.AliPlayUtils;
import com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView;
import com.psychiatrygarden.bean.CourseDirectoryContentItem;
import com.psychiatrygarden.bean.EnterHandoutPreviewEvent;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.ExitFromPipEvent;
import com.psychiatrygarden.bean.QuestionNoteBean;
import com.psychiatrygarden.bean.UserInfoBean;
import com.psychiatrygarden.bean.VideoFeedBackBean;
import com.psychiatrygarden.bean.VideoHandout;
import com.psychiatrygarden.bean.VideoSummary;
import com.psychiatrygarden.bean.VideoTopBasicInfo;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.CollectEvent;
import com.psychiatrygarden.event.RefreshCourseDownloadedEvent;
import com.psychiatrygarden.event.RefreshVideoCommentEvent;
import com.psychiatrygarden.event.RefreshVideoProgressEvent;
import com.psychiatrygarden.event.RefreshVideoSummaryQuestionDoEvent;
import com.psychiatrygarden.event.UpdateVideoCommentNote;
import com.psychiatrygarden.event.VideoBuryPointEvent;
import com.psychiatrygarden.event.VideoStateEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogNoteListener;
import com.psychiatrygarden.utils.AnimUtil;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.CourseDataSpUtilKt;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.DownloadIconUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.AddCoursePopWindow;
import com.psychiatrygarden.widget.CancelConfrimPop;
import com.psychiatrygarden.widget.ChooseChapterDialog;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.CommonTwoBtnDialog;
import com.psychiatrygarden.widget.DialogVideoNoteInput;
import com.psychiatrygarden.widget.DialogVideoNoteInputLandScape;
import com.psychiatrygarden.widget.DownloadTipPop;
import com.psychiatrygarden.widget.NiceRatingBar;
import com.psychiatrygarden.widget.PopVideoCommentLandScape;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.psychiatrygarden.widget.SummaryPointLandScapePop;
import com.psychiatrygarden.widget.VideoCommentLayout;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Triple;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class AliPlayerVideoPlayActivity extends BaseActivity implements CustomAliPlayerView.ShareDataBuyCourse, View.OnClickListener {
    private static WeakReference<AliPlayerVideoPlayActivity> instanceRef;
    private boolean buryPointLoading;
    private String chapterId;
    private String chapter_title;
    private int commentCount;
    private DiscussStatus commentEnum;
    private String courseCover;
    private String courseId;
    private String courseTitle;
    private boolean dataSuccess;
    private TextView etContent;
    private List<VideoFeedBackBean> feedBackBeans;
    private boolean fromCourseDetail;
    private boolean fromDownload;
    private boolean fromHandout;
    private int handoutPosition;
    private boolean hasChapter;
    private boolean hasFeedBack;
    private boolean hasHandout;
    private boolean hasNote;
    private boolean hasSummary;
    private boolean isCollect;
    private boolean isCurrentPageDown;
    private boolean isDestroyed;
    private boolean isPipMode;
    private LinearLayout llInputContent;
    private LinearLayout llShowContent;
    private VideoCommentLayout lyCommentLayout;
    private CustomAliPlayerView mAliPlayerView;
    private BottomInputView mBottomInputView;
    private BasePopupView mCommentDialog;
    private Handler mHandler;
    private LinearLayout mLyAddFeedView;
    private VideoSummaryAdapter mSummaryAdapter;
    BaseQuickAdapter<VideoSummary, BaseViewHolder> mVideoSummaryAdapter;
    private int noteCount;
    private boolean playComplete;
    private String rootChapterId;
    private String rootChapterTitle;
    private RecyclerView rvExcerptList;
    private boolean showCompleteHint;
    private boolean showSummaryPop;
    private boolean startPlayBuryPoint;
    private boolean statePause;
    private TextView tvSubmit;
    private TextView tvTitle;
    private TextView tv_collection;
    private TextView tv_download;
    private TextView tv_feedback;
    private TextView tv_show_content;
    private boolean userTouchScrollSummary;
    private String videoActivityId;
    private String videoTitle;
    private final List<Triple<Integer, Integer, Integer>> nodes = new ArrayList();
    private final List<VideoHandout> mVideoHandouts = new ArrayList();
    private List<VideoHandout> handoutList = new ArrayList();
    private String mVid = "";
    private String mExpireStr = "";
    private String video_id = "";
    private String mWatchPermission = "0";
    private long mFreeWatchTime = 0;
    private String isFreeWatch = "0";
    private int mModuleType = 8;
    public String seeDuration = "0";
    private String type = "1";
    private String mObjId = "";
    private boolean isFirstPlay = true;
    private boolean newVideo = false;
    private int currentSummaryPosition = -1;
    private int popHeight = 0;
    private final HandlerThread mHandlerThread = new HandlerThread("update_db");
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(EventBusConstant.CLOSE_PIP, action)) {
                if (AliPlayerVideoPlayActivity.this.isPipMode) {
                    AliPlayerVideoPlayActivity.this.finish();
                }
            } else if ("OpenPipPlay".equals(action) && AliPlayerVideoPlayActivity.this.getPackageManager().hasSystemFeature("android.software.picture_in_picture") && Build.VERSION.SDK_INT >= 26) {
                try {
                    AliPlayerVideoPlayActivity.this.enterPictureInPictureMode(new PictureInPictureParams.Builder().setAspectRatio(new Rational(16, 9)).build());
                } catch (Exception unused) {
                }
            }
        }
    };
    private final BaseQuickAdapter<VideoFeedBackBean, BaseViewHolder> mAdapter = new AnonymousClass18(R.layout.item_video_feedback);

    /* renamed from: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity$18, reason: invalid class name */
    public class AnonymousClass18 extends BaseQuickAdapter<VideoFeedBackBean, BaseViewHolder> {
        public AnonymousClass18(int layoutResId) {
            super(layoutResId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$convert$0(VideoFeedBackBean videoFeedBackBean, BaseViewHolder baseViewHolder, float f2) {
            int i2 = (int) f2;
            videoFeedBackBean.setStar_level(String.valueOf(i2));
            baseViewHolder.setText(R.id.tv_score, i2 + "分");
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull final BaseViewHolder holder, final VideoFeedBackBean videoFeedBackBean) {
            holder.setText(R.id.tv_title, videoFeedBackBean.getTitle()).setText(R.id.tv_score, videoFeedBackBean.getStar_level() + "分");
            NiceRatingBar niceRatingBar = (NiceRatingBar) holder.getView(R.id.rating_bar);
            if (!AliPlayerVideoPlayActivity.this.hasFeedBack) {
                niceRatingBar.setOnRatingChangedListener(new NiceRatingBar.OnRatingChangedListener() { // from class: com.psychiatrygarden.activity.comment.alipler.l0
                    @Override // com.psychiatrygarden.widget.NiceRatingBar.OnRatingChangedListener
                    public final void onRatingChanged(float f2) {
                        AliPlayerVideoPlayActivity.AnonymousClass18.lambda$convert$0(videoFeedBackBean, holder, f2);
                    }
                });
                return;
            }
            String star_level = videoFeedBackBean.getStar_level();
            if (!TextUtils.isEmpty(star_level) && star_level.matches(RegexPool.NUMBERS)) {
                niceRatingBar.setRating(Integer.parseInt(star_level));
            }
            niceRatingBar.setRatingStatus(NiceRatingBar.RatingStatus.Disable);
        }
    }

    /* renamed from: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity$2, reason: invalid class name */
    public class AnonymousClass2 extends RecyclerView.OnScrollListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            AliPlayerVideoPlayActivity.this.userTouchScrollSummary = false;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 1) {
                AliPlayerVideoPlayActivity.this.userTouchScrollSummary = true;
            } else if (newState == 0) {
                AliPlayerVideoPlayActivity.this.rvExcerptList.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.comment.alipler.m0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f11668c.lambda$onScrollStateChanged$0();
                    }
                }, 5000L);
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity$22, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass22 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status;

        static {
            int[] iArr = new int[AliyunDownloadMediaInfo.Status.values().length];
            $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status = iArr;
            try {
                iArr[AliyunDownloadMediaInfo.Status.Idle.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Prepare.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Wait.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Start.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Stop.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Complete.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Error.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity$3, reason: invalid class name */
    public class AnonymousClass3 extends AjaxCallBack<String> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(VideoTopBasicInfo videoTopBasicInfo, float f2) {
            if (!AliPlayerVideoPlayActivity.this.startPlayBuryPoint && f2 > 0.0f && !AliPlayerVideoPlayActivity.this.buryPointLoading) {
                AliPlayerVideoPlayActivity.this.buryPointLoading = true;
                AliPlayerVideoPlayActivity.this.buryPoint(videoTopBasicInfo.getIs_permission(), videoTopBasicInfo.getFree_watch());
            }
            if (!AliPlayerVideoPlayActivity.this.hasSummary || AliPlayerVideoPlayActivity.this.userTouchScrollSummary) {
                return;
            }
            AliPlayerVideoPlayActivity.this.updateSummary();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(VideoDownBean videoDownBean) {
            AliPlayerVideoPlayActivity.this.tv_download.setText((!AliPlayerVideoPlayActivity.this.checkVideoHasDownload() || videoDownBean == null) ? "下载" : "已下载");
            CommonUtil.mPlayerData(AliPlayerVideoPlayActivity.this.mVid, AliPlayerVideoPlayActivity.this.mAliPlayerView, true, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$2() {
            final VideoDownBean videoDownBean = ProjectApp.database.getVideoDownDao().getVideoDownBean(AliPlayerVideoPlayActivity.this.mVid);
            if (videoDownBean == null && AliPlayerVideoPlayActivity.this.checkVideoHasDownload()) {
                AliyunDownloadManager aliyunDownloadManager = ProjectApp.downloadManager;
                AliPlayerVideoPlayActivity aliPlayerVideoPlayActivity = AliPlayerVideoPlayActivity.this;
                aliyunDownloadManager.deleteFile(aliPlayerVideoPlayActivity.isInAliDownloadList(aliPlayerVideoPlayActivity.mVid));
                Iterator<AliyunDownloadMediaInfo> it = YkBManager.getInstance().mDownloadMediaLists.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    AliyunDownloadMediaInfo next = it.next();
                    if (TextUtils.equals(next.getVid(), AliPlayerVideoPlayActivity.this.mVid)) {
                        YkBManager.getInstance().mDownloadMediaLists.remove(next);
                        break;
                    }
                }
            }
            AliPlayerVideoPlayActivity.this.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.comment.alipler.n0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11672c.lambda$onSuccess$1(videoDownBean);
                }
            });
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            AliPlayerVideoPlayActivity.this.dataSuccess = false;
            if (errorNo == 500 || errorNo == 502) {
                AliPlayerVideoPlayActivity.this.finish();
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            int i2;
            super.onSuccess((AnonymousClass3) s2);
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (!TextUtils.equals("200", jSONObject.optString("code"))) {
                    String strOptString = jSONObject.optString("message");
                    if (!TextUtils.isEmpty(strOptString)) {
                        NewToast.showShort(AliPlayerVideoPlayActivity.this.mContext, strOptString);
                    }
                    AliPlayerVideoPlayActivity.this.dataSuccess = false;
                    return;
                }
                final VideoTopBasicInfo videoTopBasicInfo = (VideoTopBasicInfo) new Gson().fromJson(DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString(AliyunLogCommon.LogLevel.INFO)), VideoTopBasicInfo.class);
                AliPlayerVideoPlayActivity.this.isCollect = "1".equals(videoTopBasicInfo.getIs_collection());
                if (!TextUtils.isEmpty(videoTopBasicInfo.getVideo_id())) {
                    AliPlayerVideoPlayActivity.this.video_id = videoTopBasicInfo.getVideo_id();
                }
                SharePreferencesUtils.writeBooleanConfig("SHOW_VIDEO_COLLECTION_" + AliPlayerVideoPlayActivity.this.courseId + StrPool.UNDERLINE + AliPlayerVideoPlayActivity.this.mObjId, AliPlayerVideoPlayActivity.this.isCollect, AliPlayerVideoPlayActivity.this.mContext);
                TextView textView = (TextView) AliPlayerVideoPlayActivity.this.findViewById(R.id.tv_handout);
                String handout_num = videoTopBasicInfo.getHandout_num();
                if (TextUtils.isEmpty(handout_num) || !handout_num.matches(RegexPool.NUMBERS) || (i2 = Integer.parseInt(handout_num)) <= 0) {
                    textView.setText("讲义");
                } else {
                    textView.setText(String.format("讲义(%s)", Integer.valueOf(i2)));
                }
                AliPlayerVideoPlayActivity.this.hasHandout = !TextUtils.isEmpty(videoTopBasicInfo.getHandout_num()) && videoTopBasicInfo.getHandout_num().matches(RegexPool.NUMBERS) && Integer.parseInt(videoTopBasicInfo.getHandout_num()) > 0;
                AliPlayerVideoPlayActivity.this.commentCount = videoTopBasicInfo.getCommentCount();
                AliPlayerVideoPlayActivity.this.noteCount = videoTopBasicInfo.getNoteCount();
                TextView textView2 = (TextView) AliPlayerVideoPlayActivity.this.findViewById(R.id.tv_note);
                if (AliPlayerVideoPlayActivity.this.noteCount > 0) {
                    textView2.setText(String.format("笔记(%d)", Integer.valueOf(AliPlayerVideoPlayActivity.this.noteCount)));
                } else {
                    textView2.setText("笔记");
                }
                AliPlayerVideoPlayActivity.this.hasNote = "1".equals(videoTopBasicInfo.getIs_note());
                AliPlayerVideoPlayActivity.this.hasFeedBack = "1".equals(videoTopBasicInfo.getIs_feedback());
                if (AliPlayerVideoPlayActivity.this.hasFeedBack) {
                    AliPlayerVideoPlayActivity.this.tv_feedback.setText("已评价");
                }
                if (TextUtils.isEmpty(AliPlayerVideoPlayActivity.this.mVid)) {
                    AliPlayerVideoPlayActivity.this.mVid = videoTopBasicInfo.getVid();
                }
                if (!TextUtils.isEmpty(AliPlayerVideoPlayActivity.this.mVid)) {
                    AliPlayerVideoPlayActivity.this.findViewById(R.id.ll_basic_panel).setVisibility(0);
                }
                SharePreferencesUtils.writeStrConfig(CommonParameter.curriculum_vid, AliPlayerVideoPlayActivity.this.mVid, AliPlayerVideoPlayActivity.this);
                AliPlayerVideoPlayActivity.this.courseTitle = videoTopBasicInfo.getCourseTitle();
                if (!TextUtils.isEmpty(videoTopBasicInfo.getTitle())) {
                    TextView textView3 = (TextView) AliPlayerVideoPlayActivity.this.findViewById(R.id.tv_video_title);
                    AliPlayerVideoPlayActivity.this.videoTitle = videoTopBasicInfo.getTitle();
                    textView3.setText(AliPlayerVideoPlayActivity.this.videoTitle);
                    textView3.setVisibility(0);
                }
                SharePreferencesUtils.writeStrConfig(CommonParameter.VIDEO_TITLE, AliPlayerVideoPlayActivity.this.videoTitle, AliPlayerVideoPlayActivity.this);
                AliPlayerVideoPlayActivity.this.courseCover = videoTopBasicInfo.getCover();
                AliPlayerVideoPlayActivity.this.videoActivityId = videoTopBasicInfo.getActivity_id();
                if (!TextUtils.isEmpty(videoTopBasicInfo.getCourse_id())) {
                    AliPlayerVideoPlayActivity.this.courseId = videoTopBasicInfo.getCourse_id();
                }
                SharePreferencesUtils.writeStrConfig(CommonParameter.LIVE_COURSE_ID, AliPlayerVideoPlayActivity.this.courseId, AliPlayerVideoPlayActivity.this.mContext);
                AliPlayerVideoPlayActivity.this.seeDuration = videoTopBasicInfo.getCurrent_duration();
                if (!TextUtils.isEmpty(videoTopBasicInfo.getChapter_pid())) {
                    AliPlayerVideoPlayActivity.this.rootChapterId = videoTopBasicInfo.getChapter_pid();
                }
                if (!TextUtils.isEmpty(videoTopBasicInfo.getChapter_ptitle())) {
                    AliPlayerVideoPlayActivity.this.rootChapterTitle = videoTopBasicInfo.getChapter_ptitle();
                }
                if (!TextUtils.isEmpty(videoTopBasicInfo.getChapter_title())) {
                    AliPlayerVideoPlayActivity.this.chapter_title = videoTopBasicInfo.getChapter_title();
                }
                if (!TextUtils.isEmpty(videoTopBasicInfo.getChapter_id())) {
                    AliPlayerVideoPlayActivity.this.chapterId = videoTopBasicInfo.getChapter_id();
                }
                AliPlayerVideoPlayActivity.this.isFreeWatch = videoTopBasicInfo.getFree_watch();
                AliPlayerVideoPlayActivity aliPlayerVideoPlayActivity = AliPlayerVideoPlayActivity.this;
                aliPlayerVideoPlayActivity.hasChapter = !TextUtils.isEmpty(aliPlayerVideoPlayActivity.chapterId);
                AliPlayerVideoPlayActivity.this.mAliPlayerView.setFreeWatch("1".equals(AliPlayerVideoPlayActivity.this.isFreeWatch));
                if (AliPlayerVideoPlayActivity.this.isFirstPlay) {
                    CustomAliPlayerView customAliPlayerView = AliPlayerVideoPlayActivity.this.mAliPlayerView;
                    AliPlayerVideoPlayActivity aliPlayerVideoPlayActivity2 = AliPlayerVideoPlayActivity.this;
                    customAliPlayerView.initView(aliPlayerVideoPlayActivity2, aliPlayerVideoPlayActivity2.mVid, UserConfig.isCanPlayBy4g(AliPlayerVideoPlayActivity.this));
                    AliPlayerVideoPlayActivity.this.mAliPlayerView.setProgressListener(new CustomAliPlayerView.PlayProgressListener() { // from class: com.psychiatrygarden.activity.comment.alipler.o0
                        @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.PlayProgressListener
                        public final void onProgress(float f2) {
                            this.f11675a.lambda$onSuccess$0(videoTopBasicInfo, f2);
                        }
                    });
                }
                CourseDataSpUtilKt.writePlayingChapterId(AliPlayerVideoPlayActivity.this.courseId, !TextUtils.isEmpty(AliPlayerVideoPlayActivity.this.chapterId) ? AliPlayerVideoPlayActivity.this.chapterId : AliPlayerVideoPlayActivity.this.rootChapterId, ProjectApp.instance());
                if ("1".equals(AliPlayerVideoPlayActivity.this.isFreeWatch)) {
                    String duration = videoTopBasicInfo.getDuration();
                    boolean z2 = !TextUtils.isEmpty(duration) && duration.matches(RegexPool.NUMBERS);
                    boolean z3 = !TextUtils.isEmpty(duration) && duration.matches("^\\d*\\.\\d+$|^\\d+\\.?\\d*$");
                    if (!z2 && !z3) {
                        AliPlayerVideoPlayActivity.this.mAliPlayerView.setFree_watch_time(0L);
                    } else if (z2) {
                        AliPlayerVideoPlayActivity.this.mAliPlayerView.setFree_watch_time(Long.parseLong(duration));
                    } else {
                        AliPlayerVideoPlayActivity.this.mAliPlayerView.setFree_watch_time((int) Float.parseFloat(duration));
                    }
                } else {
                    AliPlayerVideoPlayActivity.this.mAliPlayerView.setFree_watch_time(0L);
                }
                if ("0".equals(AliPlayerVideoPlayActivity.this.mWatchPermission) && "1".equals(AliPlayerVideoPlayActivity.this.isFreeWatch)) {
                    AliPlayerVideoPlayActivity.this.mAliPlayerView.setWatch_permission("1");
                } else {
                    AliPlayerVideoPlayActivity.this.mAliPlayerView.setWatch_permission(AliPlayerVideoPlayActivity.this.mWatchPermission);
                }
                AliPlayerVideoPlayActivity.this.mAliPlayerView.setVids(AliPlayerVideoPlayActivity.this.mVid);
                AliPlayerVideoPlayActivity.this.mWatchPermission = videoTopBasicInfo.getIs_permission();
                AliPlayerVideoPlayActivity.this.mAliPlayerView.mAliyunVodPlayerView.getControlView().updateUnlockShow("1".equals(AliPlayerVideoPlayActivity.this.mWatchPermission));
                AliPlayerVideoPlayActivity.this.mAliPlayerView.mAliyunVodPlayerView.getControlView().updateVideoTitle(videoTopBasicInfo.getTitle(), videoTopBasicInfo.getTeacher_name());
                AliPlayerVideoPlayActivity.this.mAliPlayerView.setSeeDuration(videoTopBasicInfo.getCurrent_duration());
                ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.activity.comment.alipler.p0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f11678c.lambda$onSuccess$2();
                    }
                });
                AliPlayerVideoPlayActivity.this.mAliPlayerView.updateCommentAndNoteCount(videoTopBasicInfo.getNote_count(), videoTopBasicInfo.getComment_count());
                AliPlayerVideoPlayActivity.this.mBottomInputView.setObj_id((!"2".equals(AliPlayerVideoPlayActivity.this.type) || TextUtils.isEmpty(AliPlayerVideoPlayActivity.this.video_id)) ? AliPlayerVideoPlayActivity.this.mObjId : AliPlayerVideoPlayActivity.this.video_id);
                AliPlayerVideoPlayActivity.this.mBottomInputView.setVideoType(AliPlayerVideoPlayActivity.this.type);
                AliPlayerVideoPlayActivity.this.mBottomInputView.setCourseId(AliPlayerVideoPlayActivity.this.courseId);
                AliPlayerVideoPlayActivity.this.mBottomInputView.setNoteStr(videoTopBasicInfo.getIs_note());
                if (AliPlayerVideoPlayActivity.this.commentEnum.getCode() == 8) {
                    AliPlayerVideoPlayActivity.this.mBottomInputView.setVidteaching_id(AliPlayerVideoPlayActivity.this.courseId);
                }
                AliPlayerVideoPlayActivity.this.mBottomInputView.setContext(AliPlayerVideoPlayActivity.this);
                AliPlayerVideoPlayActivity.this.lyCommentLayout.initParams((!"2".equals(AliPlayerVideoPlayActivity.this.type) || TextUtils.isEmpty(AliPlayerVideoPlayActivity.this.video_id)) ? AliPlayerVideoPlayActivity.this.mObjId : AliPlayerVideoPlayActivity.this.video_id, String.valueOf(AliPlayerVideoPlayActivity.this.mModuleType), AliPlayerVideoPlayActivity.this.commentEnum, AliPlayerVideoPlayActivity.this.newVideo);
                AliPlayerVideoPlayActivity.this.lyCommentLayout.setCourseId(AliPlayerVideoPlayActivity.this.courseId);
                AliPlayerVideoPlayActivity.this.lyCommentLayout.initView();
                AliPlayerVideoPlayActivity.this.updateCollectIcon();
                AliPlayerVideoPlayActivity.this.getVideoSummary();
                SharePreferencesUtils.writeIntConfig(CommonParameter.NOTE_COUNT, AliPlayerVideoPlayActivity.this.noteCount, AliPlayerVideoPlayActivity.this.mContext);
                SharePreferencesUtils.writeIntConfig(CommonParameter.COMMENT_COUNT, AliPlayerVideoPlayActivity.this.commentCount, AliPlayerVideoPlayActivity.this.mContext);
                AliPlayerVideoPlayActivity.this.dataSuccess = true;
            } catch (Exception e2) {
                e2.printStackTrace();
                AliPlayerVideoPlayActivity.this.dataSuccess = false;
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity$4, reason: invalid class name */
    public class AnonymousClass4 extends AjaxCallBack<String> {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(int i2) {
            AliPlayerVideoPlayActivity.this.jump2SummaryPosition(i2);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            AliPlayerVideoPlayActivity.this.mAliPlayerView.mAliyunVodPlayerView.setShowVideoSummary(false);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass4) s2);
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (!TextUtils.equals(jSONObject.optString("code"), "200")) {
                    AliPlayerVideoPlayActivity.this.mAliPlayerView.mAliyunVodPlayerView.setShowVideoSummary(false);
                    return;
                }
                AliPlayerVideoPlayActivity.this.rvExcerptList.setAdapter(AliPlayerVideoPlayActivity.this.mSummaryAdapter);
                List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<VideoSummary>>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.4.1
                }.getType());
                AliPlayerVideoPlayActivity.this.mAliPlayerView.mAliyunVodPlayerView.setShowVideoSummary(list != null && list.size() > 0);
                AliPlayerVideoPlayActivity.this.nodes.clear();
                if (list != null && list.size() > 0) {
                    int size = list.size();
                    if (size > 1) {
                        for (int i2 = 0; i2 < size; i2++) {
                            VideoSummary videoSummary = (VideoSummary) list.get(i2);
                            int i3 = size - 1;
                            if (i2 == i3) {
                                AliPlayerVideoPlayActivity.this.nodes.add(new Triple(Integer.valueOf(i2), Integer.valueOf(videoSummary.getRealPoint()), 2147483));
                            } else {
                                int i4 = i2 + 1;
                                if (i4 <= i3) {
                                    AliPlayerVideoPlayActivity.this.nodes.add(new Triple(Integer.valueOf(i2), Integer.valueOf(videoSummary.getRealPoint()), Integer.valueOf(((VideoSummary) list.get(i4)).getRealPoint())));
                                }
                            }
                        }
                    } else {
                        AliPlayerVideoPlayActivity.this.nodes.add(new Triple(0, Integer.valueOf(((VideoSummary) list.get(0)).getRealPoint()), 2147483));
                    }
                    AliPlayerVideoPlayActivity.this.rvExcerptList.setVisibility(0);
                    AliPlayerVideoPlayActivity.this.mSummaryAdapter.setList(list);
                    AliPlayerVideoPlayActivity.this.mAliPlayerView.mAliyunVodPlayerView.getControlView().addSummary(new Gson().toJson(list));
                    AliPlayerVideoPlayActivity.this.hasSummary = true;
                } else if (AliPlayerVideoPlayActivity.this.mSummaryAdapter != null && AliPlayerVideoPlayActivity.this.mSummaryAdapter.getData().size() > 0) {
                    AliPlayerVideoPlayActivity.this.mSummaryAdapter.setList(new ArrayList());
                    AliPlayerVideoPlayActivity.this.rvExcerptList.setVisibility(8);
                }
                AliPlayerVideoPlayActivity.this.mAliPlayerView.mAliyunVodPlayerView.getControlView().updateShow(ProjectApp.mPlayerVideo.size() > 1, list != null && list.size() > 0);
                AliPlayerVideoPlayActivity.this.mAliPlayerView.mAliyunVodPlayerView.getControlView().setIconClickListener(new ControlView.SummaryIconClickListener() { // from class: com.psychiatrygarden.activity.comment.alipler.q0
                    @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.SummaryIconClickListener
                    public final void onIconClick(int i5) {
                        this.f11680a.lambda$onSuccess$0(i5);
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
                if (AliPlayerVideoPlayActivity.this.mAliPlayerView == null || AliPlayerVideoPlayActivity.this.mAliPlayerView.mAliyunVodPlayerView == null || AliPlayerVideoPlayActivity.this.mAliPlayerView.mAliyunVodPlayerView.getControlView() == null) {
                    return;
                }
                AliPlayerVideoPlayActivity.this.mAliPlayerView.mAliyunVodPlayerView.getControlView().updateShow(ProjectApp.mPlayerVideo.size() > 1, false);
            }
        }
    }

    public interface RedoQuestionSuccessListener {
        void onSuccess();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void buryPoint(String isPermission, String isFreeWatch) {
        VideoBuryPointEvent videoBuryPointEvent = new VideoBuryPointEvent(this.courseId, "1", "1", (!"2".equals(this.type) || TextUtils.isEmpty(this.video_id)) ? this.mObjId : this.video_id);
        videoBuryPointEvent.setFree_watch(isFreeWatch);
        videoBuryPointEvent.setIs_permission(isPermission);
        EventBus.getDefault().post(videoBuryPointEvent);
        this.startPlayBuryPoint = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkVideoHasDownload() {
        if (YkBManager.getInstance().mDownloadMediaLists == null || YkBManager.getInstance().mDownloadMediaLists.size() <= 0) {
            return false;
        }
        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : YkBManager.getInstance().mDownloadMediaLists) {
            if (aliyunDownloadMediaInfo != null && TextUtils.equals(this.mVid, aliyunDownloadMediaInfo.getVid()) && !TextUtils.isEmpty(aliyunDownloadMediaInfo.getSavePath())) {
                File file = new File(aliyunDownloadMediaInfo.getSavePath());
                if (aliyunDownloadMediaInfo.getProgress() == 100 && file.exists() && file.length() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVideoInDownloadList() {
        if (YkBManager.getInstance().mDownloadMediaLists == null || YkBManager.getInstance().mDownloadMediaLists.size() <= 0) {
            return false;
        }
        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : YkBManager.getInstance().mDownloadMediaLists) {
            if (TextUtils.equals(this.mVid, aliyunDownloadMediaInfo.getVid()) && aliyunDownloadMediaInfo.getProgress() < 100) {
                LogUtils.d("MediaInfo", aliyunDownloadMediaInfo.getVid() + " 在下载列表中");
                return true;
            }
        }
        return false;
    }

    private void clickDownload() {
        if (!CommonUtil.isWifi(this.mContext) && !UserConfig.isCanDownloadBy4g(this.mContext)) {
            new XPopup.Builder(this.mContext).hasShadowBg(Boolean.TRUE).asCustom(new CancelConfrimPop(this.mContext, new CancelConfrimPop.ClickIml() { // from class: com.psychiatrygarden.activity.comment.alipler.a0
                @Override // com.psychiatrygarden.widget.CancelConfrimPop.ClickIml
                public final void mClickIml() {
                    this.f11634a.lambda$clickDownload$24();
                }
            }, "当前设置不允许流量下载，如仍需下载可以到【设置】里开启", "温馨提示", "暂不开启", "去开启")).show();
            return;
        }
        if (!"1".equals(this.mWatchPermission)) {
            new XPopup.Builder(this).hasShadowBg(Boolean.FALSE).asCustom(new DownloadTipPop(this, "无权限，无法下载课程", true, " 解锁课程", new p(this))).show();
            return;
        }
        if (checkVideoHasDownload()) {
            new XPopup.Builder(this).hasShadowBg(Boolean.FALSE).asCustom(new DownloadTipPop(this, "已下载过此视频", !this.fromDownload, new DownloadTipPop.ViewDownloadListener() { // from class: com.psychiatrygarden.activity.comment.alipler.z
                @Override // com.psychiatrygarden.widget.DownloadTipPop.ViewDownloadListener
                public final void onClick() {
                    this.f11689a.lambda$clickDownload$23();
                }
            })).show();
        } else {
            if (!checkVideoInDownloadList()) {
                getCourseAk();
                return;
            }
            if (!checkVideoHasDownload()) {
                getCourseAk();
            }
            NewToast.showShort(this.mContext, "已在下载列表，请耐心等待");
        }
    }

    private void collectVideo() {
        if (!CommonUtil.isNetworkConnected(this)) {
            AlertToast("请检查网络连接");
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("video_id", (!"2".equals(this.type) || TextUtils.isEmpty(this.video_id)) ? this.mObjId : this.video_id);
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this, "0"));
        ajaxParams.put("type", this.isCollect ? "2" : "1");
        YJYHttpUtils.post(this, NetworkRequestsURL.courseVideoCollect, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.20
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass20) s2);
                try {
                    if (TextUtils.equals("200", new JSONObject(s2).optString("code"))) {
                        AliPlayerVideoPlayActivity aliPlayerVideoPlayActivity = AliPlayerVideoPlayActivity.this;
                        aliPlayerVideoPlayActivity.isCollect = !aliPlayerVideoPlayActivity.isCollect;
                        AliPlayerVideoPlayActivity.this.updateCollectIcon();
                        AliPlayerVideoPlayActivity aliPlayerVideoPlayActivity2 = AliPlayerVideoPlayActivity.this;
                        NewToast.showShort(aliPlayerVideoPlayActivity2.mContext, aliPlayerVideoPlayActivity2.isCollect ? "收藏成功" : "取消收藏");
                        EventBus.getDefault().post(new CollectEvent(AliPlayerVideoPlayActivity.this.courseId, AliPlayerVideoPlayActivity.this.isCollect));
                        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_VIDEO_COLLECTION, AliPlayerVideoPlayActivity.this.isCollect, AliPlayerVideoPlayActivity.this.mContext);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void doQuestionBuryPoint(String question_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("question_id", question_id);
        YJYHttpUtils.post(this, NetworkRequestsURL.doQuestionBuryPoint, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadData(final String acId, final String akSecret, final String securityToken) {
        AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsInAliDownloadList = isInAliDownloadList(this.mVid);
        if (aliyunDownloadMediaInfoIsInAliDownloadList == null) {
            this.isCurrentPageDown = true;
            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.activity.comment.alipler.e
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11642c.lambda$downloadData$21(acId, akSecret, securityToken);
                }
            });
            return;
        }
        if (aliyunDownloadMediaInfoIsInAliDownloadList.getStatus() == AliyunDownloadMediaInfo.Status.Error) {
            aliyunDownloadMediaInfoIsInAliDownloadList.setStatus(AliyunDownloadMediaInfo.Status.Stop);
        }
        if (aliyunDownloadMediaInfoIsInAliDownloadList.getProgress() == 100 && aliyunDownloadMediaInfoIsInAliDownloadList.getStatus() == AliyunDownloadMediaInfo.Status.Complete) {
            new XPopup.Builder(this).hasShadowBg(Boolean.FALSE).asCustom(new DownloadTipPop(this, "已下载过此视频", true, new DownloadTipPop.ViewDownloadListener() { // from class: com.psychiatrygarden.activity.comment.alipler.d
                @Override // com.psychiatrygarden.widget.DownloadTipPop.ViewDownloadListener
                public final void onClick() {
                    this.f11639a.lambda$downloadData$19();
                }
            })).show();
        } else {
            ProjectApp.downloadManager.startDownload(aliyunDownloadMediaInfoIsInAliDownloadList);
            this.isCurrentPageDown = true;
        }
    }

    private void downloadVideoBuryPoint() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("video_id", this.video_id);
        YJYHttpUtils.post(this, NetworkRequestsURL.downloadVideoBuryPoint, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.21
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass21) s2);
                try {
                    if ("200".equals(new JSONObject(s2).optString("code"))) {
                        LogUtils.d("onSuccess", "download point success");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private PopupWindow getBasicPopupWindow(int contentResId) {
        PopupWindow popupWindow = new PopupWindow(getLayoutInflater().inflate(contentResId, (ViewGroup) null), -1, (this.popHeight + StatusBarUtil.getStatusBarHeight(this)) - SizeUtil.dp2px(this, 10));
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.bg_round_corner_top_radius_16));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.popupwindow_anim_style);
        return popupWindow;
    }

    private void getFeedBackList() {
        YJYHttpUtils.get(this, NetworkRequestsURL.videoEvaluateList, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.13
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass13) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (TextUtils.equals(jSONObject.optString("code"), "200")) {
                        AliPlayerVideoPlayActivity.this.feedBackBeans = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<VideoFeedBackBean>>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.13.1
                        }.getType());
                        if (AliPlayerVideoPlayActivity.this.hasFeedBack) {
                            AliPlayerVideoPlayActivity.this.getUserFeedback();
                        } else {
                            AliPlayerVideoPlayActivity.this.showFeedBack(null);
                        }
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getHandoutList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("video_id", (!"2".equals(this.type) || TextUtils.isEmpty(this.video_id)) ? this.mObjId : this.video_id);
        ajaxParams.put("user_id", UserConfig.getUserId());
        showProgressDialog();
        YJYHttpUtils.get(this, NetworkRequestsURL.videoHandoutList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.14
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                AliPlayerVideoPlayActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                AliPlayerVideoPlayActivity.this.hideProgressDialog();
                super.onSuccess((AnonymousClass14) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (TextUtils.equals("200", jSONObject.optString("code"))) {
                        List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<VideoHandout>>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.14.1
                        }.getType());
                        if (list == null || list.size() <= 0) {
                            return;
                        }
                        AliPlayerVideoPlayActivity.this.handoutList = list;
                        if (AliPlayerVideoPlayActivity.this.mVideoHandouts.isEmpty()) {
                            AliPlayerVideoPlayActivity.this.mVideoHandouts.addAll(list);
                        }
                        if (list.size() != 1) {
                            AliPlayerVideoPlayActivity.this.showVideoHandout(list);
                            return;
                        }
                        if (!AliPlayerVideoPlayActivity.this.getPackageManager().hasSystemFeature("android.software.picture_in_picture") || Build.VERSION.SDK_INT < 26) {
                            return;
                        }
                        PictureInPictureParams.Builder aspectRatio = new PictureInPictureParams.Builder().setAspectRatio(new Rational(16, 9));
                        AliPlayerVideoPlayActivity.this.fromHandout = true;
                        AliPlayerVideoPlayActivity.this.handoutPosition = 0;
                        try {
                            AliPlayerVideoPlayActivity.this.enterPictureInPictureMode(aspectRatio.build());
                        } catch (Exception unused) {
                            EventBus.getDefault().post(new EnterHandoutPreviewEvent((VideoHandout) AliPlayerVideoPlayActivity.this.mVideoHandouts.get(AliPlayerVideoPlayActivity.this.handoutPosition)));
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public static AliPlayerVideoPlayActivity getInstance() {
        WeakReference<AliPlayerVideoPlayActivity> weakReference = instanceRef;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getUserFeedback() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("video_id", (!"2".equals(this.type) || TextUtils.isEmpty(this.video_id)) ? this.mObjId : this.video_id);
        showProgressDialog();
        YJYHttpUtils.get(this, NetworkRequestsURL.userFeedback, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.12
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                AliPlayerVideoPlayActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                JSONObject jSONObjectOptJSONObject;
                super.onSuccess((AnonymousClass12) s2);
                AliPlayerVideoPlayActivity.this.hideProgressDialog();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!TextUtils.equals(jSONObject.optString("code"), "200") || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null) {
                        return;
                    }
                    String strOptString = jSONObjectOptJSONObject.optString("content");
                    String strOptString2 = jSONObjectOptJSONObject.optString("evaluate");
                    if (TextUtils.isEmpty(strOptString2)) {
                        if (AliPlayerVideoPlayActivity.this.feedBackBeans == null || AliPlayerVideoPlayActivity.this.feedBackBeans.size() <= 0) {
                            return;
                        }
                        AliPlayerVideoPlayActivity.this.showFeedBack(null);
                        return;
                    }
                    List<VideoFeedBackBean> list = (List) new Gson().fromJson(strOptString2, new TypeToken<List<VideoFeedBackBean>>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.12.1
                    }.getType());
                    if (list != null && list.size() > 0) {
                        for (VideoFeedBackBean videoFeedBackBean : list) {
                            Iterator it = AliPlayerVideoPlayActivity.this.feedBackBeans.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    VideoFeedBackBean videoFeedBackBean2 = (VideoFeedBackBean) it.next();
                                    if (TextUtils.equals(videoFeedBackBean2.getId(), videoFeedBackBean.getId())) {
                                        videoFeedBackBean.setTitle(videoFeedBackBean2.getTitle());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    AliPlayerVideoPlayActivity.this.showFeedBack(strOptString);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getVideoBasicInfo() throws PackageManager.NameNotFoundException {
        if (!CommonUtil.isNetworkConnected(this)) {
            AlertToast("请检查网络连接");
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("video_type", this.type);
        ajaxParams.put("video_id", this.mObjId);
        ajaxParams.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.postMethod(this, NetworkRequestsURL.videoBasicInfo, ajaxParams, new AnonymousClass3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getVideoSummary() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("video_id", (!"2".equals(this.type) || TextUtils.isEmpty(this.video_id)) ? this.mObjId : this.video_id);
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this));
        YJYHttpUtils.post(getApplicationContext(), NetworkRequestsURL.videoSummary, ajaxParams, new AnonymousClass4());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void jump2Question(final VideoSummary item) {
        showProgressDialog();
        if (ProjectApp.instance().getTopActivity() instanceof AnswerQuestionActivity) {
            return;
        }
        if (!"1".equals(item.getIsDo())) {
            doQuestionBuryPoint(item.getQuestionId());
        }
        AjaxParams ajaxParams = new AjaxParams();
        item.setHasJump(true);
        ajaxParams.put("question_id", item.getQuestionId());
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("summary_id", item.getSummaryId());
        YJYHttpUtils.post(getApplicationContext(), NetworkRequestsURL.videoQuestionInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                item.setHasJump(false);
                AliPlayerVideoPlayActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String str) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.optInt("code") == 200) {
                        AliPlayerVideoPlayActivity.this.queryQuestionAnswer((QuestionDetailBean) new Gson().fromJson(DesUtil.decode(CommonParameter.DES_KEY_VERIFY, jSONObject.optString("data")), QuestionDetailBean.class), item.getQuestionId(), item.getModuleType(), item);
                    } else {
                        AliPlayerVideoPlayActivity.this.hideProgressDialog();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    AliPlayerVideoPlayActivity.this.hideProgressDialog();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void jump2SummaryPosition(int position) {
        VideoSummary item = this.mSummaryAdapter.getItem(position);
        if (this.mAliPlayerView.mAliyunVodPlayerView.getPlayerState() != 3) {
            this.mAliPlayerView.mAliyunVodPlayerView.onResume();
        }
        this.mAliPlayerView.mAliyunVodPlayerView.mAliyunRenderView.seekTo(item.getRealPoint(), IPlayer.SeekMode.Accurate);
        if (this.currentSummaryPosition != position) {
            this.currentSummaryPosition = position;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clickDownload$23() {
        startActivity(new Intent(this, (Class<?>) CourseDownCacheActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clickDownload$24() {
        if (isLogin()) {
            goActivity(SetNewAvtivity.class);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$downloadData$19() {
        startActivity(new Intent(this, (Class<?>) CourseDownCacheActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$downloadData$20(String str, String str2, String str3) {
        AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsInAliDownloadList = isInAliDownloadList(this.mVid);
        if (aliyunDownloadMediaInfoIsInAliDownloadList == null || ProjectApp.downloadManager.getmVidSts() == null) {
            CommonUtil.initDownAliyunVideo(this.mVid, str, str2, str3);
            downloadVideoBuryPoint();
            return;
        }
        if (aliyunDownloadMediaInfoIsInAliDownloadList.getStatus() == AliyunDownloadMediaInfo.Status.Error) {
            aliyunDownloadMediaInfoIsInAliDownloadList.setStatus(AliyunDownloadMediaInfo.Status.Stop);
        }
        if (aliyunDownloadMediaInfoIsInAliDownloadList.getStatus() != AliyunDownloadMediaInfo.Status.Start) {
            aliyunDownloadMediaInfoIsInAliDownloadList.setQuality(AliPlayUtils.getDownloadVideoDefinition(this));
            ProjectApp.downloadManager.startDownload(aliyunDownloadMediaInfoIsInAliDownloadList);
            this.isCurrentPageDown = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01ec  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$downloadData$21(final java.lang.String r8, final java.lang.String r9, final java.lang.String r10) {
        /*
            Method dump skipped, instructions count: 601
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.lambda$downloadData$21(java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$10(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (TextUtils.equals(aliyunDownloadMediaInfo.getVid(), this.mVid)) {
            this.mHandler.post(new Runnable() { // from class: com.psychiatrygarden.activity.comment.alipler.k0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11662c.lambda$init$8(aliyunDownloadMediaInfo);
                }
            });
            if (aliyunDownloadMediaInfo.getProgress() == 100) {
                if (!this.showCompleteHint && this.isCurrentPageDown) {
                    this.showCompleteHint = true;
                    if (this.fromCourseDetail) {
                        EventBus.getDefault().post(new RefreshCourseDownloadedEvent(Collections.singletonList(aliyunDownloadMediaInfo.getVid())));
                    }
                    for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo2 : YkBManager.getInstance().mDownloadMediaLists) {
                        if (TextUtils.equals(aliyunDownloadMediaInfo2.getVid(), this.mVid)) {
                            AliyunDownloadMediaInfo.Status status = aliyunDownloadMediaInfo2.getStatus();
                            AliyunDownloadMediaInfo.Status status2 = AliyunDownloadMediaInfo.Status.Complete;
                            if (status != status2) {
                                aliyunDownloadMediaInfo2.setStatus(status2);
                            }
                        }
                    }
                    new XPopup.Builder(this).hasShadowBg(Boolean.FALSE).asCustom(new DownloadTipPop(this, "下载完成", true, new DownloadTipPop.ViewDownloadListener() { // from class: com.psychiatrygarden.activity.comment.alipler.c
                        @Override // com.psychiatrygarden.widget.DownloadTipPop.ViewDownloadListener
                        public final void onClick() {
                            this.f11636a.lambda$init$9();
                        }
                    })).show();
                }
                this.tv_download.setText(checkVideoHasDownload() ? "已下载" : "下载");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$init$11(View view, MotionEvent motionEvent) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$init$3(AppBarLayout appBarLayout, int i2) {
        int childCount = appBarLayout.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = appBarLayout.getChildAt(i3);
            AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) childAt.getLayoutParams();
            if (i2 == 0) {
                layoutParams.setScrollFlags(0);
            } else {
                layoutParams.setScrollFlags(9);
            }
            childAt.setLayoutParams(layoutParams);
        }
        appBarLayout.requestLayout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(View view) {
        AnimUtil.fromTopToBottomAnims(this.mLyAddFeedView);
        this.mLyAddFeedView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(String str) {
        this.etContent.setText(str);
        this.tvSubmit.setEnabled(!TextUtils.isEmpty(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$6(View view) {
        new XPopup.Builder(this.mContext).asCustom(new AddCoursePopWindow(this.mContext, this.etContent.getText().toString().trim(), new AddCoursePopWindow.NoteClickIml() { // from class: com.psychiatrygarden.activity.comment.alipler.b0
            @Override // com.psychiatrygarden.widget.AddCoursePopWindow.NoteClickIml
            public final void mDoClickMethod(String str) {
                this.f11635a.lambda$init$5(str);
            }
        })).toggle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$7(CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO) throws PackageManager.NameNotFoundException {
        if (childrenDTO != null) {
            nextVideo(childrenDTO);
        } else {
            this.playComplete = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$8(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        ProjectApp.database.getVideoDownDao().updateSizeAndProgress(this.mVid, aliyunDownloadMediaInfo.getSize(), aliyunDownloadMediaInfo.getProgress(), mapVideoStatus(aliyunDownloadMediaInfo.getStatus()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$9() {
        startActivity(new Intent(this, (Class<?>) CourseDownCacheActivity.class).putExtra("fromCourseDetail", this.fromCourseDetail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$22() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE}, 123);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        jump2SummaryPosition(i2);
        showKnowledgePoint(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onPictureInPictureModeChanged$1() {
        EventBus.getDefault().post(new ExitFromPipEvent());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPictureInPictureModeChanged$2() {
        Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = ProjectApp.instance().activityLifecycleCallbacks;
        if (activityLifecycleCallbacks instanceof ActivityLifecycleCallbacks) {
            List<Activity> list = ((ActivityLifecycleCallbacks) activityLifecycleCallbacks).resumeActivity;
            if (list.size() <= 0 || (list.get(list.size() - 1) instanceof AliPlayerVideoPlayActivity)) {
                return;
            }
            VideoBuryPointEvent videoBuryPointEvent = new VideoBuryPointEvent(this.courseId, "2", "1", (!"2".equals(this.type) || TextUtils.isEmpty(this.video_id)) ? this.mObjId : this.video_id);
            videoBuryPointEvent.setQuitPlay(true);
            EventBus.getDefault().post(videoBuryPointEvent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$12(VideoDownBean videoDownBean) {
        this.tv_download.setText((!checkVideoHasDownload() || videoDownBean == null) ? "下载" : "已下载");
        EventBus.getDefault().post(new RefreshCourseDownloadedEvent(new ArrayList()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$13() {
        final VideoDownBean videoDownBean = ProjectApp.database.getVideoDownDao().getVideoDownBean(this.mVid);
        if (videoDownBean == null && checkVideoHasDownload()) {
            ProjectApp.downloadManager.deleteFile(isInAliDownloadList(this.mVid));
            Iterator<AliyunDownloadMediaInfo> it = YkBManager.getInstance().mDownloadMediaLists.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                AliyunDownloadMediaInfo next = it.next();
                if (TextUtils.equals(next.getVid(), this.mVid)) {
                    YkBManager.getInstance().mDownloadMediaLists.remove(next);
                    break;
                }
            }
        }
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.comment.alipler.c0
            @Override // java.lang.Runnable
            public final void run() {
                this.f11637c.lambda$onResume$12(videoDownBean);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChapter$18(CourseDirectoryContentItem courseDirectoryContentItem, String str, String str2, String str3, String str4) throws PackageManager.NameNotFoundException {
        CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO = new CurriculumScheduleCardBean.DataDTO.ChildrenDTO();
        childrenDTO.setVideo_type(courseDirectoryContentItem.getType());
        childrenDTO.setIsFreeWatch(courseDirectoryContentItem.getFree_watch());
        childrenDTO.setId(courseDirectoryContentItem.getObj_id());
        childrenDTO.setVid(courseDirectoryContentItem.getVid());
        childrenDTO.setSee(courseDirectoryContentItem.getCurrent_see());
        childrenDTO.setTitle(courseDirectoryContentItem.getTitle());
        childrenDTO.setVideo_type(courseDirectoryContentItem.getType());
        nextVideo(childrenDTO);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFeedBack$33(View view) {
        try {
            submitContent(this.etContent.getText().toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNote$14(QuestionNoteBean questionNoteBean) {
        this.mBottomInputView.setNoteStr("1");
        this.hasNote = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNote$15(DialogInterface dialogInterface) {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_NOTE_INPUT, true, this);
        if ("1".equals(this.isFreeWatch) || "1".equals(this.mWatchPermission)) {
            this.mAliPlayerView.mAliyunVodPlayerView.onResume();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNote$16(DialogInterface dialogInterface) {
        if ("1".equals(this.isFreeWatch) || "1".equals(this.mWatchPermission)) {
            this.mAliPlayerView.mAliyunVodPlayerView.onResume();
        }
        getWindow().clearFlags(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNote$17(QuestionNoteBean questionNoteBean) {
        this.mBottomInputView.setNoteStr("1");
        this.hasNote = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showVideoHandout$26(PopupWindow popupWindow, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (!getPackageManager().hasSystemFeature("android.software.picture_in_picture") || Build.VERSION.SDK_INT < 26) {
            return;
        }
        this.fromHandout = true;
        this.handoutPosition = i2;
        try {
            enterPictureInPictureMode(new PictureInPictureParams.Builder().setAspectRatio(new Rational(16, 9)).build());
            popupWindow.dismiss();
        } catch (Exception unused) {
            NewToast.showShort(this.mContext, "您的设备不支持小窗播放");
            EventBus.getDefault().post(new EnterHandoutPreviewEvent(this.mVideoHandouts.get(this.handoutPosition)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showVideoSummary$27() {
        this.showSummaryPop = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showVideoSummary$28(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (!CommonUtil.isFastClick() && R.id.tv_operate == view.getId()) {
            jump2Question(this.mVideoSummaryAdapter.getItem(i2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showVideoSummary$29(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        jump2SummaryPosition(i2);
        List<VideoSummary> data = this.mVideoSummaryAdapter.getData();
        int i3 = 0;
        while (i3 < data.size()) {
            data.get(i3).setCurrentPlay(i3 == i2);
            i3++;
        }
        baseQuickAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showVideoSummary$31(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((VideoSummary) it.next()).setIsDo("0");
        }
        this.mVideoSummaryAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showVideoSummary$32(List list, final List list2, View view) {
        redoQuestion(list, new RedoQuestionSuccessListener() { // from class: com.psychiatrygarden.activity.comment.alipler.d0
            @Override // com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.RedoQuestionSuccessListener
            public final void onSuccess() {
                this.f11640a.lambda$showVideoSummary$31(list2);
            }
        });
    }

    private int mapVideoStatus(AliyunDownloadMediaInfo.Status status) {
        int i2 = AnonymousClass22.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[status.ordinal()];
        if (i2 == 3) {
            return 3;
        }
        if (i2 == 4) {
            return 1;
        }
        if (i2 == 5) {
            return 4;
        }
        if (i2 != 6) {
            return i2 != 7 ? 0 : 2;
        }
        return 5;
    }

    private void nextVideo(CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO) throws PackageManager.NameNotFoundException {
        if ("0".equals(this.mWatchPermission) && "0".equals(childrenDTO.getIsFreeWatch())) {
            finish();
            return;
        }
        postDuration(this.mAliPlayerView.mAliyunVodPlayerView.getmCurrentPosition());
        EventBus.getDefault().post(new VideoBuryPointEvent(this.courseId, "2", "1", (!"2".equals(this.type) || TextUtils.isEmpty(this.video_id)) ? this.mObjId : this.video_id));
        this.showCompleteHint = false;
        this.isCurrentPageDown = false;
        this.mVid = childrenDTO.getVid();
        this.videoTitle = childrenDTO.getTitle();
        SharePreferencesUtils.writeStrConfig(CommonParameter.curriculum_vid, this.mVid, this);
        this.mModuleType = 8;
        this.mExpireStr = "";
        this.mFreeWatchTime = 0L;
        this.mAliPlayerView.setExpire_str("");
        this.mBottomInputView.setVideoType(childrenDTO.getVideo_type());
        this.type = childrenDTO.getVideo_type();
        this.playComplete = false;
        this.newVideo = true;
        this.isFirstPlay = false;
        this.mObjId = childrenDTO.getId();
        this.buryPointLoading = false;
        this.startPlayBuryPoint = false;
        getVideoBasicInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryQuestionAnswer(final QuestionDetailBean detailBean, String questionId, final String moduleType, final VideoSummary item) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", questionId);
        ajaxParams.put("module_type", moduleType);
        YJYHttpUtils.get(this, NetworkRequestsURL.questionUserAnswerApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                AliPlayerVideoPlayActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    AliPlayerVideoPlayActivity.this.hideProgressDialog();
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code", "").equals("200")) {
                        if (jSONObject.getJSONArray("data").length() > 0) {
                            detailBean.setUser_answer(jSONObject.getJSONArray("data").getJSONObject(0).optString("answer"));
                        }
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(detailBean);
                        ProjectApp.showQuestionList = new Gson().toJson(arrayList);
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", 0);
                        bundle.putString("module_type", moduleType);
                        bundle.putString("identity_id", item.getIdentityId());
                        bundle.putBoolean("video_summary", true);
                        bundle.putString("node_title", item.getTitle());
                        bundle.putString("subject_title", detailBean.getChapter_parent_title());
                        bundle.putString("chapter_title", detailBean.getChapter_title());
                        AnswerQuestionActivity.gotoActivity(ProjectApp.instance(), bundle);
                        item.setHasJump(true);
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    AliPlayerVideoPlayActivity.this.hideProgressDialog();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void redoQuestion(final List<String> questionIds, final RedoQuestionSuccessListener listener) {
        if (questionIds == null || questionIds.isEmpty()) {
            return;
        }
        new XPopup.Builder(this).asCustom(new CommonTwoBtnDialog(this, new CommonTwoBtnDialog.ClickIml() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.17
            @Override // com.psychiatrygarden.widget.CommonTwoBtnDialog.ClickIml
            public void mClickIml() {
                StringBuilder sb = new StringBuilder();
                int i2 = 0;
                while (i2 < questionIds.size()) {
                    sb.append((String) questionIds.get(i2));
                    sb.append(i2 < questionIds.size() + (-1) ? "," : "");
                    i2++;
                }
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put("question_id", sb.toString());
                ajaxParams.put("course_id", AliPlayerVideoPlayActivity.this.courseId);
                YJYHttpUtils.post(ProjectApp.instance(), NetworkRequestsURL.videoSummaryQuestionRedo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.17.1
                    @Override // net.tsz.afinal.http.AjaxCallBack
                    public void onSuccess(String s2) {
                        super.onSuccess((AnonymousClass1) s2);
                        try {
                            JSONObject jSONObject = new JSONObject(s2);
                            if (jSONObject.optString("code", "").equals("200")) {
                                RedoQuestionSuccessListener redoQuestionSuccessListener = listener;
                                if (redoQuestionSuccessListener != null) {
                                    redoQuestionSuccessListener.onSuccess();
                                }
                            } else {
                                NewToast.showShort(AliPlayerVideoPlayActivity.this.mContext, jSONObject.optString("message"));
                            }
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                });
            }
        }, new SpannableStringBuilder("是否重做视频所有节点试题？"), "温馨提示", "取消", "确定")).show();
    }

    private void refreshSummary(int position) {
        List<VideoSummary> data = this.mSummaryAdapter.getData();
        int i2 = 0;
        while (i2 < data.size()) {
            data.get(i2).setCurrentPlay(i2 == position);
            i2++;
        }
        this.rvExcerptList.smoothScrollToPosition(position);
        this.mSummaryAdapter.notifyDataSetChanged();
        this.currentSummaryPosition = position;
        VideoSummary item = this.mSummaryAdapter.getItem(position);
        BaseQuickAdapter<VideoSummary, BaseViewHolder> baseQuickAdapter = this.mVideoSummaryAdapter;
        if (baseQuickAdapter != null && this.showSummaryPop) {
            baseQuickAdapter.notifyDataSetChanged();
        }
        if (item == null || TextUtils.isEmpty(item.getQuestionId()) || "1".equals(item.getIsDo()) || item.isHasJump() || CommonUtil.isFastClick()) {
            return;
        }
        jump2Question(item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFeedBack(String content) {
        this.mLyAddFeedView.setVisibility(0);
        AnimUtil.fromBottomToTopAnim(this.mLyAddFeedView);
        this.llShowContent.setVisibility(this.hasFeedBack ? 0 : 8);
        this.llInputContent.setVisibility(this.hasFeedBack ? 8 : 0);
        if (this.hasFeedBack && !TextUtils.isEmpty(content)) {
            this.tv_show_content.setText(content);
        }
        ((RecyclerView) findViewById(R.id.rvFeedBackList)).setAdapter(this.mAdapter);
        Iterator<VideoFeedBackBean> it = this.feedBackBeans.iterator();
        while (it.hasNext()) {
            it.next().setStar_level("5");
        }
        this.mAdapter.setList(this.feedBackBeans);
        if (this.hasFeedBack) {
            this.tvTitle.setText("已反馈");
            this.tv_feedback.setText("已评价");
            this.etContent.setEnabled(false);
            this.etContent.setFocusable(false);
            this.etContent.setFocusableInTouchMode(false);
            this.llInputContent.removeAllViews();
        }
        if (this.hasFeedBack) {
            this.tvSubmit.setVisibility(8);
        } else {
            this.tvSubmit.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.alipler.j0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11660c.lambda$showFeedBack$33(view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showVideoHandout(List<VideoHandout> handoutList) {
        final PopupWindow basicPopupWindow = getBasicPopupWindow(R.layout.pop_video_handout);
        View contentView = basicPopupWindow.getContentView();
        basicPopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.bg_round_corner_top_radius_16));
        basicPopupWindow.setOutsideTouchable(true);
        basicPopupWindow.setAnimationStyle(R.style.popupwindow_anim_style);
        basicPopupWindow.showAtLocation(getWindow().getDecorView(), 80, 0, 0);
        contentView.findViewById(R.id.iv_close_handout).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.alipler.h0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                basicPopupWindow.dismiss();
            }
        });
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.rvList);
        BaseQuickAdapter<VideoHandout, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<VideoHandout, BaseViewHolder>(R.layout.item_video_handout) { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.15
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder holder, VideoHandout item) {
                holder.setText(R.id.tv_title, item.getTitle()).setText(R.id.tv_file_size, item.getSize_info()).setGone(R.id.tv_author, TextUtils.isEmpty(item.getAuthor())).setText(R.id.tv_author, item.getAuthor()).setImageResource(R.id.iv_type, DownloadIconUtil.getIcon(item.getSuffix()));
            }
        };
        recyclerView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.comment.alipler.i0
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f11657c.lambda$showVideoHandout$26(basicPopupWindow, baseQuickAdapter2, view, i2);
            }
        });
        baseQuickAdapter.setList(handoutList);
        basicPopupWindow.showAtLocation(getWindow().getDecorView(), 80, 0, 0);
    }

    private void showVideoSummary(final List<VideoSummary> mSummaryList) {
        final ArrayList arrayList = new ArrayList();
        for (VideoSummary videoSummary : mSummaryList) {
            if (!TextUtils.isEmpty(videoSummary.getQuestionId())) {
                arrayList.add(videoSummary.getQuestionId());
            }
        }
        final PopupWindow basicPopupWindow = getBasicPopupWindow(R.layout.pop_video_knowledge_point);
        basicPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.activity.comment.alipler.j
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                this.f11659c.lambda$showVideoSummary$27();
            }
        });
        View contentView = basicPopupWindow.getContentView();
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.rvNodes);
        BaseQuickAdapter<VideoSummary, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<VideoSummary, BaseViewHolder>(R.layout.item_knowledge_point) { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.16
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder holder, VideoSummary item) {
                CheckedTextView checkedTextView = (CheckedTextView) holder.getView(R.id.tv_operate);
                checkedTextView.setText("1".equals(item.getIsDo()) ? "回顾" : "练习");
                checkedTextView.setVisibility(!TextUtils.isEmpty(item.getQuestionId()) ? 0 : 8);
                checkedTextView.setChecked((TextUtils.isEmpty(item.getQuestionId()) || "1".equals(item.getIsDo())) ? false : true);
                if (SkinManager.getCurrentSkinType(AliPlayerVideoPlayActivity.this.mContext) == 1) {
                    checkedTextView.setTextColor(AliPlayerVideoPlayActivity.this.getColor(R.color.color_121622));
                }
                TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{R.attr.main_theme_color, R.attr.first_txt_color});
                int realPoint = item.getRealPoint() / 1000;
                int i2 = realPoint / 3600;
                holder.setText(R.id.tv_time, String.format(i2 <= 99 ? "%02d:%02d:%02d" : "%d:%02d:%02d", Integer.valueOf(i2), Integer.valueOf((realPoint % 3600) / 60), Integer.valueOf(realPoint % 60))).setText(R.id.tv_point_title, item.getTitle()).setTextColor(R.id.tv_time, item.isCurrentPlay() ? typedArrayObtainStyledAttributes.getColor(0, Color.parseColor("#F95843")) : typedArrayObtainStyledAttributes.getColor(1, Color.parseColor("#333333"))).setTextColor(R.id.tv_point_title, item.isCurrentPlay() ? typedArrayObtainStyledAttributes.getColor(0, Color.parseColor("#F95843")) : typedArrayObtainStyledAttributes.getColor(1, Color.parseColor("#333333")));
                ImageView imageView = (ImageView) holder.getView(R.id.iv_status);
                Drawable background = imageView.getBackground();
                if (item.isCurrentPlay()) {
                    imageView.setBackground(getContext().getDrawable(R.drawable.anim_excerpt_play_day));
                    Drawable background2 = imageView.getBackground();
                    if (background2 instanceof AnimationDrawable) {
                        AnimationDrawable animationDrawable = (AnimationDrawable) background2;
                        if (!animationDrawable.isRunning()) {
                            animationDrawable.start();
                        }
                    }
                } else {
                    if (background instanceof AnimationDrawable) {
                        AnimationDrawable animationDrawable2 = (AnimationDrawable) background;
                        if (animationDrawable2.isRunning()) {
                            animationDrawable2.stop();
                        }
                    }
                    AliPlayerVideoPlayActivity aliPlayerVideoPlayActivity = AliPlayerVideoPlayActivity.this;
                    imageView.setBackground(aliPlayerVideoPlayActivity.getDrawable(SkinManager.getCurrentSkinType(aliPlayerVideoPlayActivity.mContext) == 1 ? R.drawable.ic_point_play_portrait : R.drawable.ic_point_play_landscape));
                }
                typedArrayObtainStyledAttributes.recycle();
            }
        };
        this.mVideoSummaryAdapter = baseQuickAdapter;
        baseQuickAdapter.addChildClickViewIds(R.id.iv_status, R.id.tv_operate);
        this.mVideoSummaryAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.comment.alipler.k
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f11661c.lambda$showVideoSummary$28(baseQuickAdapter2, view, i2);
            }
        });
        this.mVideoSummaryAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.comment.alipler.l
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f11664c.lambda$showVideoSummary$29(baseQuickAdapter2, view, i2);
            }
        });
        recyclerView.setAdapter(this.mVideoSummaryAdapter);
        this.mVideoSummaryAdapter.setList(mSummaryList);
        contentView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.alipler.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                basicPopupWindow.dismiss();
            }
        });
        if (arrayList.size() > 0) {
            View viewFindViewById = contentView.findViewById(R.id.ll_redo);
            viewFindViewById.setVisibility(0);
            viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.alipler.n
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11669c.lambda$showVideoSummary$32(arrayList, mSummaryList, view);
                }
            });
        }
        basicPopupWindow.showAtLocation(getWindow().getDecorView(), 80, 0, 0);
        this.showSummaryPop = true;
    }

    private void submitContent(String content) throws Exception {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("video_id", (!"2".equals(this.type) || TextUtils.isEmpty(this.video_id)) ? this.mObjId : this.video_id);
        if (TextUtils.isEmpty(content)) {
            NewToast.showShort(this, "评价点什么吧");
            return;
        }
        ajaxParams.put("content", content);
        JSONArray jSONArray = new JSONArray();
        for (VideoFeedBackBean videoFeedBackBean : this.mAdapter.getData()) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("evaluate_id", videoFeedBackBean.getId());
            jSONObject.put("star_level", videoFeedBackBean.getStar_level());
            jSONObject.put("title", videoFeedBackBean.getTitle());
            jSONArray.put(jSONObject);
        }
        ajaxParams.put("evaluate", jSONArray.toString());
        YJYHttpUtils.post(this, NetworkRequestsURL.submitVideoFeedBack, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.19
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (TextUtils.isEmpty(strMsg)) {
                    return;
                }
                NewToast.showShort(AliPlayerVideoPlayActivity.this.mContext, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass19) s2);
                try {
                    JSONObject jSONObject2 = new JSONObject(s2);
                    String strOptString = jSONObject2.optString("code");
                    String strOptString2 = jSONObject2.optString("message");
                    if (TextUtils.equals("200", strOptString)) {
                        NewToast.showShort(AliPlayerVideoPlayActivity.this.mContext, "提交成功");
                        AliPlayerVideoPlayActivity.this.hasFeedBack = true;
                        AliPlayerVideoPlayActivity.this.tv_feedback.setText("已评价");
                        AnimUtil.fromTopToBottomAnims(AliPlayerVideoPlayActivity.this.mLyAddFeedView);
                        AliPlayerVideoPlayActivity.this.mLyAddFeedView.setVisibility(8);
                    } else if (!TextUtils.isEmpty(strOptString2)) {
                        NewToast.showShort(AliPlayerVideoPlayActivity.this.mContext, strOptString2);
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCollectIcon() {
        ImageView imageView = (ImageView) findViewById(R.id.iv_collect);
        boolean z2 = SkinManager.getCurrentSkinType(this) == 1;
        this.tv_collection.setText(this.isCollect ? "已收藏" : "收藏");
        if (z2) {
            if (this.isCollect) {
                imageView.setImageResource(R.drawable.ic_video_play_collect_ok_night);
            } else {
                imageView.setImageResource(R.drawable.ic_video_play_collect_normal_night);
            }
        } else if (this.isCollect) {
            imageView.setImageResource(R.drawable.ic_video_play_collect_ok_day);
        } else {
            imageView.setImageResource(R.drawable.ic_video_play_collect_normal_day);
        }
        this.mAliPlayerView.mAliyunVodPlayerView.getControlView().updateCollect(this.isCollect);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSummary() {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliPlayerView.mAliyunVodPlayerView;
        if (aliyunVodPlayerView == null) {
            return;
        }
        long j2 = aliyunVodPlayerView.getmCurrentPosition();
        for (int i2 = 0; i2 < this.nodes.size(); i2++) {
            Triple<Integer, Integer, Integer> triple = this.nodes.get(i2);
            if (j2 >= triple.getSecond().intValue() && j2 <= triple.getThird().intValue()) {
                if (this.mSummaryAdapter.getItem(triple.getFirst().intValue()).isCurrentPlay()) {
                    return;
                }
                refreshSummary(triple.getFirst().intValue());
                return;
            }
            List<VideoSummary> data = this.mSummaryAdapter.getData();
            for (int i3 = 0; i3 < data.size(); i3++) {
                VideoSummary videoSummary = data.get(i3);
                if (videoSummary.isCurrentPlay()) {
                    videoSummary.setCurrentPlay(false);
                    this.mSummaryAdapter.notifyItemChanged(i3);
                }
            }
        }
    }

    public void changeDesTroy() {
        if (this.isDestroyed) {
            return;
        }
        try {
            CustomAliPlayerView customAliPlayerView = this.mAliPlayerView;
            if (customAliPlayerView != null) {
                customAliPlayerView.onDestory();
                this.isDestroyed = true;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void execCollect() {
        collectVideo();
    }

    public void getCourseAk() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(AliPlayerVideoPlayActivity.this.mContext, "获取视频信息失败");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject == null) {
                            return;
                        }
                        AliPlayerVideoPlayActivity.this.downloadData(DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("akId")), DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("akSecret")), DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("st")));
                        NewToast.showShort(AliPlayerVideoPlayActivity.this.mContext, "开始下载");
                    } else {
                        NewToast.showShort(AliPlayerVideoPlayActivity.this.mContext, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.commentEnum = (DiscussStatus) extras.getSerializable("commentEnum");
            this.mObjId = getIntent().getExtras().getString("obj_id");
            String string = getIntent().getExtras().getString("chapter_id", "0");
            this.courseId = string;
            if (TextUtils.isEmpty(string) || "0".equals(this.courseId)) {
                this.courseId = getIntent().getExtras().getString("course_id", "0");
            }
            this.mVid = getIntent().getExtras().getString("vid");
            this.fromDownload = getIntent().getExtras().getBoolean("fromDownload", false);
            this.mExpireStr = getIntent().getExtras().getString("expire_str");
            this.mWatchPermission = getIntent().getExtras().getString("watch_permission");
            this.type = getIntent().getExtras().getString("type", "1");
            this.video_id = getIntent().getExtras().getString("video_id", "");
        }
        this.fromCourseDetail = getIntent().getBooleanExtra("fromCourseDetail", false);
        this.courseTitle = getIntent().getStringExtra("title");
        this.videoTitle = getIntent().getStringExtra("video_title");
        this.mFreeWatchTime = 0L;
        TextView textView = (TextView) findViewById(R.id.tv_video_title);
        this.tv_download = (TextView) findViewById(R.id.tv_download);
        this.tv_collection = (TextView) findViewById(R.id.tv_collection);
        this.tv_feedback = (TextView) findViewById(R.id.tv_feedback);
        this.chapter_title = getIntent().getStringExtra("chapterTitle");
        this.hasChapter = getIntent().getBooleanExtra("hasChapter", false);
        this.rootChapterTitle = getIntent().getStringExtra("rootChapterTitle");
        this.chapterId = getIntent().getStringExtra("chapterId");
        this.rootChapterId = getIntent().getStringExtra("rootChapterId");
        if (TextUtils.isEmpty(this.videoTitle)) {
            textView.setVisibility(8);
        } else {
            SharePreferencesUtils.writeStrConfig(CommonParameter.VIDEO_TITLE, this.videoTitle, this);
            textView.setText(this.videoTitle);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvExcerptList);
        this.rvExcerptList = recyclerView;
        recyclerView.addOnScrollListener(new AnonymousClass2());
        this.mAliPlayerView = (CustomAliPlayerView) findViewById(R.id.aliplerView);
        this.mBottomInputView = (BottomInputView) findViewById(R.id.bottom_input_view);
        this.lyCommentLayout = (VideoCommentLayout) findViewById(R.id.lyComment);
        final AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        this.lyCommentLayout.setCommentInfoListener(new VideoCommentLayout.CommentInfoListener() { // from class: com.psychiatrygarden.activity.comment.alipler.q
            @Override // com.psychiatrygarden.widget.VideoCommentLayout.CommentInfoListener
            public final void commentChange(int i2) {
                AliPlayerVideoPlayActivity.lambda$init$3(appBarLayout, i2);
            }
        });
        this.mLyAddFeedView = (LinearLayout) findViewById(R.id.ly_feedback);
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.alipler.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11681c.lambda$init$4(view);
            }
        });
        this.llShowContent = (LinearLayout) findViewById(R.id.ll_show_content);
        this.llInputContent = (LinearLayout) findViewById(R.id.ll_input_content);
        this.etContent = (TextView) findViewById(R.id.input_et_content);
        this.tvTitle = (TextView) findViewById(R.id.tv_title);
        this.tv_show_content = (TextView) findViewById(R.id.tv_show_content);
        this.tvSubmit = (TextView) findViewById(R.id.tv_submit);
        CircleImageView circleImageView = (CircleImageView) findViewById(R.id.iv_user_avatar);
        UserInfoBean.DataBean user = UserConfig.getInstance().getUser();
        if (user != null) {
            String avatar = user.getAvatar();
            if (!TextUtils.isEmpty(avatar)) {
                GlideUtils.loadImage(this, avatar, circleImageView);
            }
        }
        this.mBottomInputView.setFromVideo(true);
        findViewById(R.id.ll_feedback).setOnClickListener(this);
        findViewById(R.id.ll_collect).setOnClickListener(this);
        findViewById(R.id.tv_add_comment).setOnClickListener(this);
        findViewById(R.id.ll_note).setOnClickListener(this);
        findViewById(R.id.ll_handout).setOnClickListener(this);
        findViewById(R.id.ll_download).setOnClickListener(this);
        this.etContent.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.alipler.s
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11683c.lambda$init$6(view);
            }
        });
        this.mAliPlayerView.setOnCompleteToNext(new CustomAliPlayerView.OnCompleteToNext() { // from class: com.psychiatrygarden.activity.comment.alipler.t
            @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.OnCompleteToNext
            public final void onCompleteToNext(CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO) throws PackageManager.NameNotFoundException {
                this.f11685a.lambda$init$7(childrenDTO);
            }
        });
        if (!TextUtils.isEmpty(this.courseId)) {
            YkBManager.getInstance().getVideoDownBean().observe(this, new Observer() { // from class: com.psychiatrygarden.activity.comment.alipler.u
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    this.f11686a.lambda$init$10((AliyunDownloadMediaInfo) obj);
                }
            });
        }
        this.mBottomInputView.setCommentEnum(this.commentEnum);
        String stringExtra = getIntent().getStringExtra("isProhibit");
        this.mBottomInputView.setIsProhibit(TextUtils.isEmpty(stringExtra) ? "0" : stringExtra);
        this.mBottomInputView.setModule_type(this.mModuleType);
        this.mBottomInputView.setContext(this);
        this.mBottomInputView.setVideoType(this.type);
        this.mBottomInputView.initView();
        if (TextUtils.isEmpty(this.mVid)) {
            findViewById(R.id.ll_basic_panel).setVisibility(8);
        }
        this.mLyAddFeedView.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.comment.alipler.v
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AliPlayerVideoPlayActivity.lambda$init$11(view, motionEvent);
            }
        });
    }

    public AliyunDownloadMediaInfo isInAliDownloadList(String vid) {
        if (!TextUtils.isEmpty(vid) && YkBManager.getInstance().mDownloadMediaLists != null && YkBManager.getInstance().mDownloadMediaLists.size() > 0) {
            for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : YkBManager.getInstance().mDownloadMediaLists) {
                if (vid.equals(aliyunDownloadMediaInfo.getVid())) {
                    return aliyunDownloadMediaInfo;
                }
            }
        }
        return null;
    }

    @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.ShareDataBuyCourse
    public void mShareDataBuyCourse() {
        if ("1".equals(this.isFreeWatch) || "0".equals(this.mWatchPermission)) {
            if (this.fromCourseDetail) {
                finish();
                return;
            }
            Intent intent = new Intent(this, (Class<?>) ActCourseOrGoodsDetail.class);
            intent.putExtra("goods_id", this.courseId);
            startActivity(intent);
            finish();
        }
    }

    public void noteNote() {
        this.hasNote = false;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        Bundle bundleExtra = data.getBundleExtra("bundleIntent");
        if (bundleExtra != null) {
            bundleExtra.putInt("module_type", this.mModuleType);
            bundleExtra.putString("course_id", this.courseId);
            bundleExtra.putString("video_type", this.type);
        }
        if (requestCode == 0) {
            this.lyCommentLayout.putVideoComment(bundleExtra);
            return;
        }
        if (requestCode != 1) {
            return;
        }
        BasePopupView basePopupView = this.mCommentDialog;
        if (basePopupView == null || !basePopupView.isShow()) {
            if (bundleExtra != null) {
                this.lyCommentLayout.putComment(bundleExtra);
            }
        } else {
            PopVideoCommentLandScape popVideoCommentLandScape = (PopVideoCommentLandScape) this.mCommentDialog;
            if (bundleExtra != null) {
                popVideoCommentLandScape.putComment(bundleExtra);
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        if (TextUtils.isEmpty(this.mVid)) {
        }
        if (!CommonUtil.isNetworkConnected(this)) {
            AlertToast("请检查网络连接");
            return;
        }
        if (this.dataSuccess) {
            switch (v2.getId()) {
                case R.id.ll_collect /* 2131364755 */:
                    collectVideo();
                    break;
                case R.id.ll_download /* 2131364777 */:
                    if (!CommonUtil.isNetworkConnected(this.mContext)) {
                        NewToast.showShort(this.mContext, "当前无网络连接");
                        break;
                    } else if (!CommonUtil.hasRequiredPermissionsWriteStorage(this)) {
                        new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.comment.alipler.o
                            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                            public final void onConfirm() {
                                this.f11674a.lambda$onClick$22();
                            }
                        })).show();
                        break;
                    } else {
                        clickDownload();
                        break;
                    }
                case R.id.ll_feedback /* 2131364795 */:
                    List<VideoFeedBackBean> list = this.feedBackBeans;
                    if (list != null && !list.isEmpty()) {
                        getUserFeedback();
                        break;
                    } else {
                        getFeedBackList();
                        break;
                    }
                case R.id.ll_handout /* 2131364803 */:
                    if (!this.hasHandout) {
                        NewToast.showShort(this.mContext, "暂无课程讲义");
                        break;
                    } else if (!"1".equals(this.mWatchPermission) && (!"0".equals(this.mWatchPermission) || !"1".equals(this.isFreeWatch))) {
                        new XPopup.Builder(this).hasShadowBg(Boolean.FALSE).asCustom(new DownloadTipPop(this, "无权限，无法查看讲义", true, "解锁课程", new p(this))).show();
                        break;
                    } else if (!this.handoutList.isEmpty()) {
                        if (this.handoutList.size() != 1) {
                            showVideoHandout(this.handoutList);
                            break;
                        } else if (getPackageManager().hasSystemFeature("android.software.picture_in_picture") && Build.VERSION.SDK_INT >= 26) {
                            PictureInPictureParams.Builder aspectRatio = new PictureInPictureParams.Builder().setAspectRatio(new Rational(16, 9));
                            this.fromHandout = true;
                            this.handoutPosition = 0;
                            try {
                                enterPictureInPictureMode(aspectRatio.build());
                                break;
                            } catch (Exception unused) {
                                EventBus.getDefault().post(new EnterHandoutPreviewEvent(this.mVideoHandouts.get(this.handoutPosition)));
                                return;
                            }
                        }
                    } else {
                        getHandoutList();
                        break;
                    }
                    break;
                case R.id.ll_note /* 2131364839 */:
                    this.mAliPlayerView.mAliyunVodPlayerView.snapShot();
                    break;
                case R.id.tv_add_comment /* 2131367665 */:
                    this.mBottomInputView.addComment();
                    break;
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int i2 = newConfig.orientation;
        if (i2 == 2) {
            findViewById(R.id.ll_bottom).setVisibility(4);
            findViewById(R.id.cdl).setVisibility(4);
        } else if (i2 == 1) {
            findViewById(R.id.ll_bottom).setVisibility(0);
            findViewById(R.id.cdl).setVisibility(0);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VideoSummaryAdapter videoSummaryAdapter = new VideoSummaryAdapter();
        this.mSummaryAdapter = videoSummaryAdapter;
        videoSummaryAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.comment.alipler.g0
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11653c.lambda$onCreate$0(baseQuickAdapter, view, i2);
            }
        });
        GlobalPlayerConfig.PlayConfig.mEnableAccurateSeekModule = true;
        int dimensionPixelSize = getResources().getDisplayMetrics().heightPixels - getResources().getDimensionPixelSize(R.dimen.dp_236);
        this.popHeight = dimensionPixelSize;
        SharePreferencesUtils.writeIntConfig(CommonParameter.VIDEO_POP_HEIGHT, dimensionPixelSize, this);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_FULL_SCREEN_BTN, true, this);
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
        this.mActionBar.hide();
        getWindow().setStatusBarColor(-16777216);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & (-8193));
        if (SkinManager.getCurrentSkinType(this) == 0) {
            getWindow().setNavigationBarColor(Color.parseColor("#FFFFFF"));
        } else {
            getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EventBusConstant.CLOSE_PIP);
        intentFilter.addAction("OpenPipPlay");
        registerReceiver(this.mReceiver, intentFilter);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.COURSE_VIDEO, true, this);
        instanceRef = new WeakReference<>(this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        changeDesTroy();
        VideoBuryPointEvent videoBuryPointEvent = new VideoBuryPointEvent(this.courseId, "2", "1", (!"2".equals(this.type) || TextUtils.isEmpty(this.video_id)) ? this.mObjId : this.video_id);
        videoBuryPointEvent.setQuitPlay(true);
        EventBus.getDefault().post(videoBuryPointEvent);
        unregisterReceiver(this.mReceiver);
        ProjectApp.noteContent = "";
        ProjectApp.noteBigImage = "";
        ProjectApp.noteSmellImage = "";
        EventBus.getDefault().post(new EventBusMessage("refreshDuration", ""));
        ProjectApp.mPlayerVideo.clear();
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_VIDEO_COLLECTION, false, getApplicationContext());
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.HAS_VIDEO_SUMMARY, false, getApplicationContext());
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.COURSE_VIDEO, false, this);
        EventBus.getDefault().post(new RefreshCourseDownloadedEvent(Collections.singletonList("")));
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(VideoStateEvent event) {
        if (!event.getPlay()) {
            this.statePause = true;
            this.mAliPlayerView.onPause();
            return;
        }
        this.statePause = false;
        if ("1".equals(this.mWatchPermission) || "1".equals(this.isFreeWatch)) {
            this.mAliPlayerView.onResume();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if ("DOWNLOAD_COURSE_VIDEO".equals(str)) {
            findViewById(R.id.ll_download).performClick();
        } else if ("delReplyAndLoadData".equals(str)) {
            this.lyCommentLayout.refresh();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && event.getRepeatCount() == 0) {
            if (this.mLyAddFeedView.getVisibility() == 0) {
                this.mLyAddFeedView.setVisibility(8);
                return true;
            }
            finish();
        }
        return false;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            changeDesTroy();
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        this.isPipMode = isInPictureInPictureMode;
        if (!isInPictureInPictureMode) {
            if (this.fromHandout) {
                ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.comment.alipler.x
                    @Override // java.lang.Runnable
                    public final void run() {
                        AliPlayerVideoPlayActivity.lambda$onPictureInPictureModeChanged$1();
                    }
                }, 1000L);
                this.fromHandout = false;
            }
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.comment.alipler.y
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11688c.lambda$onPictureInPictureModeChanged$2();
                }
            }, 500L);
        }
        if (this.fromHandout) {
            EventBus.getDefault().post(new EnterHandoutPreviewEvent(this.mVideoHandouts.get(this.handoutPosition)));
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(@Nullable Bundle savedInstanceState) throws PackageManager.NameNotFoundException {
        super.onPostCreate(savedInstanceState);
        this.mAliPlayerView.setExpire_str(this.mExpireStr);
        this.mAliPlayerView.setFree_watch_time(this.mFreeWatchTime);
        if ("0".equals(this.mWatchPermission) && "1".equals(this.isFreeWatch)) {
            this.mAliPlayerView.setWatch_permission("1");
        } else {
            this.mAliPlayerView.setWatch_permission(this.mWatchPermission);
        }
        this.mAliPlayerView.setmShareDataBuyCourse(this);
        SharePreferencesUtils.writeIntConfig(CommonParameter.NOTE_COUNT, 0, this);
        SharePreferencesUtils.writeIntConfig(CommonParameter.COMMENT_COUNT, 0, this);
        if (CommonUtil.isNetworkConnected(this)) {
            getVideoBasicInfo();
            return;
        }
        NewToast.showShort(this, "请检查网络连接");
        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : YkBManager.getInstance().mDownloadMediaLists) {
            if (TextUtils.equals(aliyunDownloadMediaInfo.getVid(), this.mVid) && (aliyunDownloadMediaInfo.getProgress() == 100 || aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Complete)) {
                this.mAliPlayerView.initView(this, this.mVid, UserConfig.isCanPlayBy4g(this));
                this.mAliPlayerView.setWatch_permission("1");
                if (!TextUtils.isEmpty(this.videoTitle)) {
                    this.mAliPlayerView.mAliyunVodPlayerView.getControlView().updateVideoTitle(this.videoTitle, null);
                }
                this.mAliPlayerView.mAliyunVodPlayerView.getControlView().updateShow(false, false);
                CommonUtil.mPlayerData(this.mVid, this.mAliPlayerView, true, false);
                return;
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length != grantResults.length || grantResults.length <= 0) {
            return;
        }
        if (grantResults[0] == 0 && requestCode == 123) {
            clickDownload();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE) || requestCode != 123) {
                return;
            }
            NewToast.showShort(this, "无法下载，请检查app存储权限是否打开！");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.mAliPlayerView != null && !this.statePause && ("1".equals(this.mWatchPermission) || "1".equals(this.isFreeWatch))) {
            this.mAliPlayerView.onResume();
        }
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.activity.comment.alipler.w
            @Override // java.lang.Runnable
            public final void run() {
                this.f11687c.lambda$onResume$13();
            }
        });
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        AliyunVodPlayerView aliyunVodPlayerView;
        super.onStop();
        CustomAliPlayerView customAliPlayerView = this.mAliPlayerView;
        if (customAliPlayerView == null || (aliyunVodPlayerView = customAliPlayerView.mAliyunVodPlayerView) == null) {
            return;
        }
        if (!aliyunVodPlayerView.isPlaying()) {
            this.statePause = true;
        }
        this.mAliPlayerView.onPause();
    }

    @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.ShareDataBuyCourse
    public void peekVideo() {
    }

    public void postDuration(final long time) {
        if (this.commentEnum.getCode() != 8) {
            return;
        }
        final String str = this.mVid;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("video_id", TextUtils.isEmpty(this.video_id) ? this.mObjId : this.video_id);
        ajaxParams.put("duration", String.valueOf(time / 1000.0d));
        YJYHttpUtils.post(this, NetworkRequestsURL.curriculumseeUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.11
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass11) s2);
                try {
                    if (!"200".equals(new JSONObject(s2).optString("code"))) {
                        NewToast.showShort(AliPlayerVideoPlayActivity.this.mContext, "播放记录保存失败");
                    } else {
                        CourseDataSpUtilKt.writeLastLearnVid(AliPlayerVideoPlayActivity.this.courseId, str, !TextUtils.isEmpty(AliPlayerVideoPlayActivity.this.chapterId) ? AliPlayerVideoPlayActivity.this.chapterId : AliPlayerVideoPlayActivity.this.rootChapterId, ProjectApp.instance());
                        EventBus.getDefault().post(new RefreshVideoProgressEvent(str, String.valueOf(time / 1000.0d)));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.ShareDataBuyCourse
    public void postSeekDuration(long time) {
        postDuration(time);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_aliyun_video_play_new);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void showChapter(boolean fullScreen) {
        if ("1".equals(this.mWatchPermission) && fullScreen) {
            ChooseChapterDialog chooseChapterDialog = new ChooseChapterDialog(this, this.mVid, this.courseId, this.courseTitle, new ChooseChapterDialog.OnCourseSelectListener() { // from class: com.psychiatrygarden.activity.comment.alipler.e0
                @Override // com.psychiatrygarden.widget.ChooseChapterDialog.OnCourseSelectListener
                public final void onCourseSelect(CourseDirectoryContentItem courseDirectoryContentItem, String str, String str2, String str3, String str4) throws PackageManager.NameNotFoundException {
                    this.f11646a.lambda$showChapter$18(courseDirectoryContentItem, str, str2, str3, str4);
                }
            });
            chooseChapterDialog.setCurrentSee(String.valueOf(this.mAliPlayerView.mAliyunVodPlayerView.getmCurrentPosition() / 1000));
            new XPopup.Builder(this).popupPosition(PopupPosition.Right).asCustom(chooseChapterDialog).show();
        }
    }

    public void showComment() {
        if (!NetUtils.isNetworkConnected(this)) {
            AlertToast("请检查网络连接");
            return;
        }
        BasePopupView basePopupViewAsCustom = new XPopup.Builder(this).popupPosition(PopupPosition.Right).isRequestFocus(false).asCustom(new PopVideoCommentLandScape(this, (!"2".equals(this.type) || TextUtils.isEmpty(this.video_id)) ? this.mObjId : this.video_id, this.courseId, String.valueOf(this.mModuleType), this.commentEnum, new SimpleCallback() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.5
        }));
        this.mCommentDialog = basePopupViewAsCustom;
        basePopupViewAsCustom.show();
    }

    public void showKnowledgePoint(boolean fullScreen) {
        if (!NetUtils.isNetworkConnected(this)) {
            AlertToast("请检查网络连接");
        } else if (fullScreen) {
            new XPopup.Builder(this).popupPosition(PopupPosition.Right).asCustom(new SummaryPointLandScapePop(this, this.mSummaryAdapter.getData(), this.videoTitle, new SummaryPointLandScapePop.OnNodeSelectListener() { // from class: com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity.6
                @Override // com.psychiatrygarden.widget.SummaryPointLandScapePop.OnNodeSelectListener
                public void doQuestion(VideoSummary item) {
                    if (CommonUtil.isFastClick()) {
                        return;
                    }
                    AliPlayerVideoPlayActivity.this.jump2Question(item);
                }

                @Override // com.psychiatrygarden.widget.SummaryPointLandScapePop.OnNodeSelectListener
                public void onNodeSelect(int position) {
                    AliPlayerVideoPlayActivity.this.jump2SummaryPosition(position);
                }

                @Override // com.psychiatrygarden.widget.SummaryPointLandScapePop.OnNodeSelectListener
                public void redoAllQuestion(List<String> questionIds, RedoQuestionSuccessListener l2) {
                    AliPlayerVideoPlayActivity.this.redoQuestion(questionIds, l2);
                }
            })).show();
        } else {
            showVideoSummary(this.mSummaryAdapter.getData());
        }
    }

    public void showNote(String captureScreenFilePath, boolean fullScreen) {
        if (!NetUtils.isNetworkConnected(this)) {
            AlertToast("请检查网络连接");
            return;
        }
        boolean z2 = SharePreferencesUtils.readIntConfig(CommonParameter.NOTE_COUNT, this, 0) > 0;
        this.hasNote = z2;
        if (fullScreen) {
            if (z2) {
                this.mAliPlayerView.showDrawerNoteList((!"2".equals(this.type) || TextUtils.isEmpty(this.video_id)) ? this.mObjId : this.video_id, this.courseId, captureScreenFilePath, String.valueOf(this.mModuleType));
                return;
            }
            this.mAliPlayerView.mAliyunVodPlayerView.pause();
            DialogVideoNoteInputLandScape dialogVideoNoteInputLandScape = new DialogVideoNoteInputLandScape(this, String.valueOf(this.mModuleType), !this.playComplete ? captureScreenFilePath : null, null, new onDialogNoteListener() { // from class: com.psychiatrygarden.activity.comment.alipler.f
                @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                    this.f11647a.lambda$showNote$14(questionNoteBean);
                }
            }, (!"2".equals(this.type) || TextUtils.isEmpty(this.video_id)) ? this.mObjId : this.video_id);
            dialogVideoNoteInputLandScape.setCourseId(this.courseId).setModuleType(String.valueOf(this.mModuleType));
            dialogVideoNoteInputLandScape.setDismissListener(new DialogInterface.OnDismissListener() { // from class: com.psychiatrygarden.activity.comment.alipler.g
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    this.f11652c.lambda$showNote$15(dialogInterface);
                }
            });
            dialogVideoNoteInputLandScape.show();
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_NOTE_INPUT, true, this);
            return;
        }
        if (z2) {
            BottomInputView bottomInputView = this.mBottomInputView;
            if (this.playComplete) {
                captureScreenFilePath = null;
            }
            bottomInputView.showNoteDialog(captureScreenFilePath);
            return;
        }
        if (TextUtils.isEmpty(captureScreenFilePath)) {
            return;
        }
        this.mAliPlayerView.mAliyunVodPlayerView.pause();
        DialogVideoNoteInput.Builder moduleType = new DialogVideoNoteInput.Builder(this).setModuleType(String.valueOf(this.mModuleType));
        if (this.playComplete) {
            captureScreenFilePath = null;
        }
        DialogVideoNoteInput dialogVideoNoteInputBuild = moduleType.setScreenShotFilePath(captureScreenFilePath).setDismissListener(new DialogInterface.OnDismissListener() { // from class: com.psychiatrygarden.activity.comment.alipler.h
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f11654c.lambda$showNote$16(dialogInterface);
            }
        }).setObjId((!"2".equals(this.type) || TextUtils.isEmpty(this.video_id)) ? this.mObjId : this.video_id).setCourseId(this.courseId).setClickNoteListener(new onDialogNoteListener() { // from class: com.psychiatrygarden.activity.comment.alipler.i
            @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
            public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                this.f11656a.lambda$showNote$17(questionNoteBean);
            }
        }).build();
        this.statePause = true;
        dialogVideoNoteInputBuild.show();
    }

    @Subscribe
    public void onEventMainThread(RefreshVideoCommentEvent event) {
        this.lyCommentLayout.refresh();
    }

    @Subscribe
    public void onEventMainThread(UpdateVideoCommentNote event) {
        this.mAliPlayerView.mAliyunVodPlayerView.getControlView().updateCommentAndNoteCount(String.valueOf(event.getCommentCount()), String.valueOf(event.getNoteCount()));
        ((TextView) findViewById(R.id.tv_note)).setText(event.getNoteCount() == 0 ? "笔记" : String.format("笔记(%d)", Integer.valueOf(event.getNoteCount())));
    }

    @Subscribe
    public void onEventMainThread(RefreshVideoSummaryQuestionDoEvent event) {
        String questionId = event.getQuestionId();
        for (VideoSummary videoSummary : this.mSummaryAdapter.getData()) {
            if (TextUtils.equals(videoSummary.getQuestionId(), questionId)) {
                videoSummary.setIsDo(event.is_do() ? "1" : "0");
            }
        }
        this.mSummaryAdapter.notifyDataSetChanged();
        BaseQuickAdapter<VideoSummary, BaseViewHolder> baseQuickAdapter = this.mVideoSummaryAdapter;
        if (baseQuickAdapter != null) {
            baseQuickAdapter.notifyDataSetChanged();
        }
    }
}
