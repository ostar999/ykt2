package com.psychiatrygarden.activity.online.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TextAppearanceSpan;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager2.widget.ViewPager2;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.IPlayer;
import com.aliyun.player.alivcplayerexpand.listener.OnStoppedListener;
import com.aliyun.player.alivcplayerexpand.util.TimeFormater;
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView;
import com.aliyun.vod.common.utils.UriUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.CommentNewActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.NoteNewActivity;
import com.psychiatrygarden.activity.QuestionChoiceVideoAdapter;
import com.psychiatrygarden.activity.QuestionCommentActivity;
import com.psychiatrygarden.activity.QuestionCorrectionActivity;
import com.psychiatrygarden.activity.online.AnswerQuestionActivity;
import com.psychiatrygarden.activity.online.ChartAnswerSheetActivity;
import com.psychiatrygarden.activity.online.QuestionRestoreEditActivity;
import com.psychiatrygarden.activity.online.QuestionRestoreListActivity;
import com.psychiatrygarden.activity.online.RecommendQuestionVideoAct;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.online.bean.QuestionStatDataBean;
import com.psychiatrygarden.activity.online.bean.event.NoteEventBean;
import com.psychiatrygarden.activity.online.popwindow.NoteNewPopWindow;
import com.psychiatrygarden.activity.online.popwindow.QuestionRestoreEditPopWindow;
import com.psychiatrygarden.adapter.QuestionCommentListAdapter;
import com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.BiaoqianBeab;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.HomepageSmallAdBean;
import com.psychiatrygarden.bean.QuestionNoteBean;
import com.psychiatrygarden.bean.QuestionTopLabel;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.CommentTagEvent;
import com.psychiatrygarden.event.HideChapterTitleEvent;
import com.psychiatrygarden.event.PlayQuestionMediaEvent;
import com.psychiatrygarden.event.QuestionBack2TopEvent;
import com.psychiatrygarden.event.QuestionCollectEvent;
import com.psychiatrygarden.event.RefreshQuestionCommentEvent;
import com.psychiatrygarden.event.RefreshViewStateEvent;
import com.psychiatrygarden.event.ScrollChangeEvent;
import com.psychiatrygarden.event.ScrollDirectionEvent;
import com.psychiatrygarden.event.UpdateCommentPraiseEvent;
import com.psychiatrygarden.event.UpdateQuestionCutEvent;
import com.psychiatrygarden.event.UpdateQuestionIdEvent;
import com.psychiatrygarden.event.UpdateScrollEvent;
import com.psychiatrygarden.event.UpdateTopMargin;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.BiaoqianCallbackInterface;
import com.psychiatrygarden.interfaceclass.DomoIml;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.interfaceclass.TextClick;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.interfaceclass.onDialogNoteListener;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CharacterParser;
import com.psychiatrygarden.utils.ColorPhrase;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ConfigUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.BiaoPupNewWindow;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.CirclePoint;
import com.psychiatrygarden.widget.CommentSectionListView;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.DialogNoteInput;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.KnowledgePointNoQuestionTipsPop;
import com.psychiatrygarden.widget.MaxRecyclerView;
import com.psychiatrygarden.widget.PopQuestionDetailFilterShow;
import com.psychiatrygarden.widget.QuestionAdWidegt;
import com.psychiatrygarden.widget.ShowVideoDialog;
import com.psychiatrygarden.widget.english.PopNoteList;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.tencent.open.SocialConstants;
import com.tencent.smtt.sdk.WebView;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Pair;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"NotifyDataSetChanged"})
/* loaded from: classes5.dex */
public class SubChoiceQuestionFragment extends BaseFragment implements View.OnClickListener, QuestionDataCallBack<String> {
    private String answerMode;
    private String audioId;
    private boolean back2Top;
    private TextView biaotxt;
    private String break_point;
    private TextView btn_comment;
    private String category;
    private String chapterTitle;
    private CirclePoint circlePoint;
    private String commentContent;
    private String commentId;
    private boolean currentPageIsVisible;
    private Drawable drawable;
    private boolean ebook;
    private boolean firstInitScroll;
    private FrameLayout flTitleImg;
    private ColorStateList grayColors;
    private boolean hasAnswer;
    private boolean hasGetStatData;
    private boolean hasTitleShow;
    private int headerContentHeight;
    private View headerContentView;
    private RoundedImageView img_wrong;
    private AppCompatImageView imgtitle;
    private boolean isPinned;
    private boolean isRefreshFlag;
    private boolean isSdkAd;
    private String is_show_number;
    private boolean ishavehot;
    private ImageView ivPlayPause;
    private View lineKnowledge;
    private LinearLayout lineout;
    private LinearLayout lineselect2;
    private View lineviewtype;
    private QuestionAdWidegt llAd;
    private LinearLayout llAnalyzeEnter;
    private LinearLayout llAudio;
    private LinearLayout llEasyWrongOption;
    private LinearLayout llMoreColumns;
    private LinearLayout llNoComment;
    private RelativeLayout llQuestionComment;
    private LinearLayout llRestoreEnter;
    private View llStatisticsContent;
    private LinearLayout llViewComment;
    private View llWrongOptionAnalysis;
    private LinearLayout ll_answer_analysis_layout;
    private LinearLayout ll_answer_enter;
    private LinearLayout ll_encyclopedia_analysis_layout;
    private View ll_knowledge_point;
    private LinearLayout ll_restore_point;
    private LinearLayout ll_statistics;
    private View ll_use_time;
    private LinearLayout lvKnowledgePointTree;
    private LinearLayout ly_questiondetails_btn_centerMsg;
    private LinearLayout ly_questiondetails_btn_collect;
    private LinearLayout ly_questiondetails_btn_edit;
    private LinearLayout ly_questiondetails_btn_zantong;
    private CustomAliPlayerView mAudioPlayerView;
    private QuestionCommentListAdapter mCommListAdapter;
    private int mCurrentFirstVisibleItem;
    private int mCurrentPosition;
    private ImageView mCutQuestionFlag;
    private ImageView mImgAd;
    private ImageView mImgAnswerPraise;
    private CircleImageView mImgAnswerUserHead;
    private ImageView mImgAudio;
    private ImageView mImgCloseAd;
    private RoundedImageView mImgEncyclopediaExplain;
    private RoundedImageView mImgExplain;
    private RoundedImageView mImgOriginal;
    private ImageView mImgPraise;
    private ImageView mImgRestorePraise;
    private CircleImageView mImgRestoreUserHead;
    private CircleImageView mImgUserHead;
    private long mLastTime;
    private View mLineEmpty;
    private View mLineExpline;
    private View mLineRestore;
    private View mLineVideo;
    private RelativeLayout mLyAdView;
    private LinearLayout mLyAnswerPraise;
    private RelativeLayout mLyAnswerUserInfo;
    private LinearLayout mLyPraise;
    private LinearLayout mLyRestorePraise;
    private RelativeLayout mLyRestoreUserInfo;
    private RelativeLayout mLyUserInfo;
    private RelativeLayout mLyVideoView;
    private CommentSectionListView mPinnedSecListView;
    private List<QuestionDetailBean.OptionDTO> mQuestionOptionBean;
    private LinearLayout mQuestiondetailsBottomLayout;
    private LinearLayout mRadioGroupContent;
    private SeekBar mSeekBar;
    private TextView mTvAnswerPraiseCount;
    private TextView mTvAnswerRecord;
    private TextView mTvAnswerUserNickName;
    private TextView mTvAnswerUserUpdateTime;
    private TextView mTvEcyclopediaContents;
    private TextView mTvEncyclopediaAnalyze;
    private TextView mTvPraiseCount;
    private TextView mTvRestorePraiseCount;
    private TextView mTvRestoreUserNickName;
    private TextView mTvRestoreUserUpdateTime;
    private TextView mTvUserNickName;
    private TextView mTvUserUpdateTime;
    private ViewPager2 mVpVideo;
    private String module_type;
    private View nodeShowView;
    private boolean notInitCommentFlag;
    private boolean playEnd;
    private BaseQuickAdapter<QuestionDetailBean.OptionDTO, BaseViewHolder> qAdapter;
    private RelativeLayout qbrel;
    private MaxRecyclerView qlistview;
    private QuestionDetailBean questionDetailBean;
    private String questionId;
    private String question_bank_id;
    private LinearLayout questiondetailsLayoutDiff;
    private ImageView questiondetails_btn_centerMsg;
    private ImageView questiondetails_btn_collect;
    private ImageView questiondetails_btn_edit;
    private ImageView questiondetails_btn_zantong;
    private TextView questiondetails_tv_Answer;
    private TextView questiondetails_tv_content_ques1;
    private TextView questiondetails_tv_outline;
    private TextView questiondetails_tv_statistics;
    private CheckedTextView remen;
    private RelativeLayout rlAnalyzeUser;
    private RelativeLayout rlAnswerUser;
    private RelativeLayout rlQuestionVideo;
    private RelativeLayout rlRestoreUser;
    private boolean shareStem;
    private boolean showTypeTitle;
    private SmartRefreshLayout smartRefreshLayout;
    private TextView sourcetv;
    private long startTime;
    String start_timestamp;
    private String subjectTitle;
    private TextView textViewDifficulty;
    private String total;
    private int totalNewCommentNum;
    private TextView tvAllCorrectPercent;
    private TextView tvAllUseTime;
    private TextView tvAnalyzeEdit;
    private TextView tvAnalyzeReword;
    private TextView tvAnalyzeToEdit;
    private TextView tvAnswerAnalysis;
    private TextView tvAnswerAnalyze;
    private TextView tvAnswerEdit;
    private TextView tvAnswerToEdit;
    private TextView tvCommentNum;
    private TextView tvCurrent;
    private TextView tvEasyWrongOption;
    private TextView tvEasyWrongOptions;
    private TextView tvEditTime;
    private TextView tvMyCorrectPercent;
    private TextView tvMyDoTimes;
    private TextView tvPageNum;
    private TextView tvPushAnswer;
    private TextView tvQuestionNew;
    private TextView tvRestoreEdit;
    private TextView tvRestoreReword;
    private TextView tvRestoreToEdit;
    private TextView tvStatistics;
    private TextView tvTitle;
    private TextView tvTotal;
    private TextView tvTotalDoTimes;
    private TextView tvUseTime;
    private TextView tvWrongOptionAnalysis;
    private TextView tv_correction;
    private TextView typeStr;
    private ShowVideoDialog videoMainDialog;
    private boolean videoSummary;
    private boolean viewCommentClickFlag;
    private WebView webview;
    private CheckedTextView zuixin;
    private long question_id = 0;
    private int answerUseTime = 0;
    private String show_restore_img = "1";
    private String externalsources = "";
    private String node_title = "";
    private boolean isInit = false;
    private boolean isClickAudio = true;
    private List<CommentBean.DataBean.HotBean> hot = new ArrayList();
    private List<CommentBean.DataBean.HotBean> time_line = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> commlist = new ArrayList();
    private final List<Integer> positionList = new ArrayList();
    private int pageNum = 0;
    private int positionTab = 0;
    private boolean hasConfigAd = false;
    private boolean isLoadMore = false;
    private final SparseArray<ItemRecord> recordSp = new SparseArray<>(0);
    private boolean isLoadCommentData = false;
    private final ArrayMap<Integer, Boolean> optionImgMap = new ArrayMap<>();
    private int collapseExpPos = 0;
    private boolean firstShowNode = true;
    private Map<Integer, View> knowledgePointTreeViewMap = new ArrayMap();
    private boolean isCanLoadData = true;
    private boolean hasMockClick = false;
    private Map<Integer, View> KnowledgeViewMap = new ArrayMap();
    String logImg = "";
    boolean isFragmentVisible = false;
    boolean isAnswerVisible = false;

    /* renamed from: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends SimpleMultiPurposeListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStateChanged$0() {
            SubChoiceQuestionFragment.this.headerContentView.findViewById(R.id.rl_top_title).setVisibility(0);
        }

        @Override // com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener, com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener
        public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
            super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight);
            if (!TextUtils.equals("year", SubChoiceQuestionFragment.this.category) && !TextUtils.isEmpty(SubChoiceQuestionFragment.this.chapterTitle) && TextUtils.isEmpty(SubChoiceQuestionFragment.this.questionDetailBean.getPublic_number())) {
                SubChoiceQuestionFragment.this.headerContentView.findViewById(R.id.rl_top_title).setVisibility(4);
            }
            if (TextUtils.equals("year", SubChoiceQuestionFragment.this.category) && "all".equals(SubChoiceQuestionFragment.this.getArguments().getString("type", ""))) {
                if (SubChoiceQuestionFragment.this.headerContentView.findViewById(R.id.rl_top_title).getVisibility() == 0) {
                    SubChoiceQuestionFragment.this.headerContentView.findViewById(R.id.rl_top_title).setVisibility(4);
                    SubChoiceQuestionFragment.this.hasTitleShow = true;
                }
            }
        }

        @Override // com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener, com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            if (SubChoiceQuestionFragment.this.isCanLoadData) {
                SubChoiceQuestionFragment.this.isCanLoadData = false;
                SubChoiceQuestionFragment.this.isLoadMore = true;
                SubChoiceQuestionFragment.access$608(SubChoiceQuestionFragment.this);
                SubChoiceQuestionFragment.this.isRefreshFlag = false;
                SubChoiceQuestionFragment.this.getCommentListData();
            }
        }

        @Override // com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener, com.scwang.smartrefresh.layout.listener.OnRefreshListener
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            SubChoiceQuestionFragment.this.pageNum = 1;
            SubChoiceQuestionFragment.this.isRefreshFlag = true;
            SubChoiceQuestionFragment.this.isLoadMore = false;
            SubChoiceQuestionFragment.this.getCommentListData();
            SubChoiceQuestionFragment.this.updateBottomView();
        }

        @Override // com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener, com.scwang.smartrefresh.layout.listener.OnStateChangedListener
        public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
            if (newState == RefreshState.RefreshReleased || newState == RefreshState.PullDownCanceled || newState == RefreshState.None) {
                if (SubChoiceQuestionFragment.this.hasTitleShow || (!TextUtils.equals("year", SubChoiceQuestionFragment.this.category) && !TextUtils.isEmpty(SubChoiceQuestionFragment.this.chapterTitle) && TextUtils.isEmpty(SubChoiceQuestionFragment.this.questionDetailBean.getPublic_number()))) {
                    SubChoiceQuestionFragment.this.smartRefreshLayout.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.t1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f13345c.lambda$onStateChanged$0();
                        }
                    }, 0L);
                }
                if (SubChoiceQuestionFragment.this.hasTitleShow) {
                    SubChoiceQuestionFragment.this.hasTitleShow = false;
                }
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment$4, reason: invalid class name */
    public class AnonymousClass4 extends AjaxCallBack<String> {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            SubChoiceQuestionFragment.this.mPinnedSecListView.smoothScrollToPosition(1);
            SubChoiceQuestionFragment.this.mPinnedSecListView.setSelection(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1() {
            SubChoiceQuestionFragment.this.mCommListAdapter.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$2() {
            SubChoiceQuestionFragment.this.mCommListAdapter.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$3() {
            SubChoiceQuestionFragment.this.mCommListAdapter.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$4(String str) {
            CommentBean commentBean = (CommentBean) new Gson().fromJson(str, CommentBean.class);
            if (commentBean.getCode().equals("200")) {
                SubChoiceQuestionFragment.this.hot = commentBean.getData().getHot();
                SubChoiceQuestionFragment.this.time_line = commentBean.getData().getTime_line();
                if (SubChoiceQuestionFragment.this.pageNum == 1) {
                    if (SubChoiceQuestionFragment.this.isLoadMore) {
                        SubChoiceQuestionFragment.this.smartRefreshLayout.finishLoadMore(true);
                    } else {
                        SubChoiceQuestionFragment.this.smartRefreshLayout.finishRefresh(true);
                    }
                    SubChoiceQuestionFragment.this.commlist.clear();
                    if (SubChoiceQuestionFragment.this.hot == null) {
                        SubChoiceQuestionFragment.this.hot = new ArrayList(0);
                    }
                    String time_line_total = "0";
                    if (SubChoiceQuestionFragment.this.hot.size() <= 0 || !SubChoiceQuestionFragment.this.hasAnswer) {
                        SubChoiceQuestionFragment.this.lineselect2.setVisibility(8);
                    } else {
                        SubChoiceQuestionFragment subChoiceQuestionFragment = SubChoiceQuestionFragment.this;
                        subChoiceQuestionFragment.positionTab = subChoiceQuestionFragment.hot.size();
                        CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                        hotBean.setHot(true);
                        hotBean.setType(1);
                        StringBuilder sb = new StringBuilder();
                        sb.append("最热评论(");
                        sb.append((SubChoiceQuestionFragment.this.hot == null || SubChoiceQuestionFragment.this.hot.isEmpty()) ? "0" : commentBean.getData().getHot_total());
                        sb.append(")");
                        hotBean.setName(sb.toString());
                        SubChoiceQuestionFragment.this.commlist.add(hotBean);
                        Iterator it = SubChoiceQuestionFragment.this.hot.iterator();
                        while (it.hasNext()) {
                            ((CommentBean.DataBean.HotBean) it.next()).setHot(true);
                        }
                        if (SubChoiceQuestionFragment.this.isSdkAd) {
                            CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
                            hotBean2.setHot(true);
                            if (SubChoiceQuestionFragment.this.hot.size() == 8) {
                                SubChoiceQuestionFragment.this.hot.add(hotBean2);
                            } else if (SubChoiceQuestionFragment.this.hot.size() >= 9) {
                                SubChoiceQuestionFragment.this.hot.add(8, hotBean2);
                            }
                        }
                        SubChoiceQuestionFragment.this.commlist.addAll(SubChoiceQuestionFragment.this.hot);
                        SubChoiceQuestionFragment.this.ishavehot = true;
                        if (commentBean.getData().getMore_hot() != null && "1".equals(commentBean.getData().getMore_hot())) {
                            CommentBean.DataBean.HotBean hotBean3 = new CommentBean.DataBean.HotBean();
                            hotBean3.setOtherView(3);
                            hotBean3.setName("最热评论（" + commentBean.getData().getHot_total() + "）");
                            SubChoiceQuestionFragment.this.commlist.add(hotBean3);
                        }
                    }
                    SubChoiceQuestionFragment subChoiceQuestionFragment2 = SubChoiceQuestionFragment.this;
                    subChoiceQuestionFragment2.totalNewCommentNum = (subChoiceQuestionFragment2.time_line == null || SubChoiceQuestionFragment.this.time_line.isEmpty()) ? 0 : Integer.parseInt(commentBean.getData().getTime_line_total());
                    if (SubChoiceQuestionFragment.this.time_line == null) {
                        SubChoiceQuestionFragment.this.time_line = new ArrayList(0);
                    }
                    if (SubChoiceQuestionFragment.this.time_line.size() > 0 && SubChoiceQuestionFragment.this.hasAnswer) {
                        CommentBean.DataBean.HotBean hotBean4 = new CommentBean.DataBean.HotBean();
                        hotBean4.setHot(false);
                        hotBean4.setType(1);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("最新评论（");
                        if (SubChoiceQuestionFragment.this.time_line != null && !SubChoiceQuestionFragment.this.time_line.isEmpty()) {
                            time_line_total = commentBean.getData().getTime_line_total();
                        }
                        sb2.append(time_line_total);
                        sb2.append("）");
                        hotBean4.setName(sb2.toString());
                        SubChoiceQuestionFragment.this.commlist.add(hotBean4);
                        Iterator it2 = SubChoiceQuestionFragment.this.time_line.iterator();
                        while (it2.hasNext()) {
                            ((CommentBean.DataBean.HotBean) it2.next()).setHot(false);
                        }
                        SubChoiceQuestionFragment.this.commlist.addAll(SubChoiceQuestionFragment.this.time_line);
                    }
                    SubChoiceQuestionFragment.this.mCommListAdapter.setIsSdkAd(SubChoiceQuestionFragment.this.isSdkAd);
                    SubChoiceQuestionFragment.this.mCommListAdapter.setShowAd(SubChoiceQuestionFragment.this.hot.size() >= 8 && SubChoiceQuestionFragment.this.hasConfigAd);
                    SubChoiceQuestionFragment.this.mCommListAdapter.setList(SubChoiceQuestionFragment.this.commlist);
                    SubChoiceQuestionFragment.this.mCommListAdapter.setRefeault(SubChoiceQuestionFragment.this.time_line);
                    SubChoiceQuestionFragment.this.mCommListAdapter.notifyDataSetChanged();
                    if (SubChoiceQuestionFragment.this.isLoadCommentData) {
                        SubChoiceQuestionFragment.this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.v1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f13369c.lambda$onSuccess$0();
                            }
                        }, 310L);
                        if (SubChoiceQuestionFragment.this.commlist.size() >= 8) {
                            EventBus.getDefault().post(new HideChapterTitleEvent(true, String.valueOf(SubChoiceQuestionFragment.this.question_id)));
                            ((AnswerQuestionActivity) SubChoiceQuestionFragment.this.getActivity()).hideTitleView(SubChoiceQuestionFragment.this.questionId);
                        }
                        if (SubChoiceQuestionFragment.this.ishavehot) {
                            SubChoiceQuestionFragment.this.lineselect2.setVisibility(0);
                        } else {
                            SubChoiceQuestionFragment.this.lineselect2.setVisibility(8);
                        }
                    } else if (SubChoiceQuestionFragment.this.notInitCommentFlag) {
                        if (SubChoiceQuestionFragment.this.ishavehot) {
                            SubChoiceQuestionFragment.this.hasMockClick = false;
                            SubChoiceQuestionFragment.this.zuixin.performClick();
                        } else {
                            SubChoiceQuestionFragment.this.remen.performClick();
                        }
                        SubChoiceQuestionFragment.this.notInitCommentFlag = false;
                        EventBus.getDefault().post(new HideChapterTitleEvent(SubChoiceQuestionFragment.this.commlist.size() > 8, String.valueOf(SubChoiceQuestionFragment.this.question_id)));
                    }
                    if (SubChoiceQuestionFragment.this.isSdkAd) {
                        SubChoiceQuestionFragment.this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.x1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f13387c.lambda$onSuccess$2();
                            }
                        });
                    } else if (SubChoiceQuestionFragment.this.hot.size() >= 8) {
                        SubChoiceQuestionFragment.this.getCommentAd();
                    } else {
                        SubChoiceQuestionFragment.this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.w1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f13378c.lambda$onSuccess$1();
                            }
                        });
                    }
                } else {
                    SubChoiceQuestionFragment.this.smartRefreshLayout.finishLoadMore(true);
                    if (SubChoiceQuestionFragment.this.time_line.size() == 0) {
                        SubChoiceQuestionFragment.this.AlertToast("已经是最后一条");
                        SubChoiceQuestionFragment.access$620(SubChoiceQuestionFragment.this, 1);
                        SubChoiceQuestionFragment.this.smartRefreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                        SubChoiceQuestionFragment.this.commlist.addAll(SubChoiceQuestionFragment.this.time_line);
                        SubChoiceQuestionFragment.this.mCommListAdapter.setRefeault(SubChoiceQuestionFragment.this.time_line);
                        SubChoiceQuestionFragment.this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.y1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f13396c.lambda$onSuccess$3();
                            }
                        });
                    }
                }
            } else {
                if (SubChoiceQuestionFragment.this.isLoadMore) {
                    SubChoiceQuestionFragment.this.smartRefreshLayout.finishLoadMore(false);
                } else {
                    SubChoiceQuestionFragment.this.smartRefreshLayout.finishRefresh(false);
                }
                SubChoiceQuestionFragment.this.AlertToast(commentBean.getMessage());
            }
            SubChoiceQuestionFragment.this.isLoadCommentData = false;
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            if (SubChoiceQuestionFragment.this.viewCommentClickFlag) {
                SubChoiceQuestionFragment.this.showProgressDialog();
            }
            SubChoiceQuestionFragment.this.isCanLoadData = true;
            if (SubChoiceQuestionFragment.this.pageNum == 1 && SubChoiceQuestionFragment.this.commlist.size() > 0) {
                SubChoiceQuestionFragment.this.AlertToast("请求服务器失败");
            } else if (SubChoiceQuestionFragment.this.isLoadMore) {
                SubChoiceQuestionFragment.this.smartRefreshLayout.finishLoadMore(false);
            } else {
                SubChoiceQuestionFragment.this.smartRefreshLayout.finishRefresh(false);
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
            if (SubChoiceQuestionFragment.this.viewCommentClickFlag) {
                SubChoiceQuestionFragment.this.showProgressDialog();
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(final String s2) {
            if (SubChoiceQuestionFragment.this.viewCommentClickFlag) {
                SubChoiceQuestionFragment.this.hideProgressDialog();
                SubChoiceQuestionFragment.this.viewCommentClickFlag = false;
            }
            super.onSuccess((AnonymousClass4) s2);
            try {
                if (SubChoiceQuestionFragment.this.getActivity() != null) {
                    SubChoiceQuestionFragment.this.getActivity().runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.u1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f13359c.lambda$onSuccess$4(s2);
                        }
                    });
                }
                SubChoiceQuestionFragment.this.llNoComment.setVisibility(SubChoiceQuestionFragment.this.commlist.isEmpty() ? 0 : 8);
            } catch (Exception e2) {
                e2.printStackTrace();
                if (SubChoiceQuestionFragment.this.isLoadMore) {
                    SubChoiceQuestionFragment.this.smartRefreshLayout.finishLoadMore(false);
                } else {
                    SubChoiceQuestionFragment.this.smartRefreshLayout.finishRefresh(false);
                }
            }
            SubChoiceQuestionFragment.this.isCanLoadData = true;
            SubChoiceQuestionFragment.this.llViewComment.setVisibility(8);
        }
    }

    /* renamed from: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment$5, reason: invalid class name */
    public class AnonymousClass5 extends AjaxCallBack<String> {
        public AnonymousClass5() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            SubChoiceQuestionFragment.this.mCommListAdapter.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(JSONObject jSONObject) {
            JSONObject jSONObjectOptJSONObject;
            if (!jSONObject.optString("code").equals("200") || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null || jSONObjectOptJSONObject.length() <= 0) {
                return;
            }
            if ((SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, ((BaseFragment) SubChoiceQuestionFragment.this).mContext, 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, ((BaseFragment) SubChoiceQuestionFragment.this).mContext, 0L).longValue()) / 1000) - jSONObjectOptJSONObject.optLong("time_interval", 0L) : 0L) < 0 || SubChoiceQuestionFragment.this.commlist.size() <= 8 || TextUtils.isEmpty(jSONObjectOptJSONObject.optString("img"))) {
                return;
            }
            CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
            hotBean.setAds(jSONObject.optString("data"));
            if (SubChoiceQuestionFragment.this.hot.size() >= 8) {
                hotBean.setHot(true);
                SubChoiceQuestionFragment.this.commlist.add(9, hotBean);
                SubChoiceQuestionFragment.this.hasConfigAd = true;
                SubChoiceQuestionFragment.this.mCommListAdapter.setShowAd(SubChoiceQuestionFragment.this.hot.size() >= 8 && SubChoiceQuestionFragment.this.hasConfigAd);
                SubChoiceQuestionFragment.this.mCommListAdapter.setList(SubChoiceQuestionFragment.this.commlist);
                SubChoiceQuestionFragment.this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.z1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f13404c.lambda$onSuccess$0();
                    }
                });
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            if (t2 != null) {
                t2.printStackTrace();
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String json) {
            try {
                final JSONObject jSONObject = new JSONObject(json);
                if (SubChoiceQuestionFragment.this.getActivity() != null) {
                    SubChoiceQuestionFragment.this.getActivity().runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.a2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f13175c.lambda$onSuccess$1(jSONObject);
                        }
                    });
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment$8, reason: invalid class name */
    public class AnonymousClass8 extends BaseQuickAdapter<QuestionDetailBean.OptionDTO, BaseViewHolder> {
        public AnonymousClass8(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(ImageView imageView, QuestionDetailBean.OptionDTO optionDTO, View view) {
            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(((BaseFragment) SubChoiceQuestionFragment.this).mContext).setSingleSrcView(imageView, optionDTO.getImg()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader.popupInfo = new PopupInfo();
            xPopupImageLoader.show();
        }

        /* JADX WARN: Removed duplicated region for block: B:80:0x0415  */
        /* JADX WARN: Removed duplicated region for block: B:81:0x0433  */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void convert(@androidx.annotation.NonNull com.chad.library.adapter.base.viewholder.BaseViewHolder r17, final com.psychiatrygarden.activity.online.bean.QuestionDetailBean.OptionDTO r18) {
            /*
                Method dump skipped, instructions count: 1105
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment.AnonymousClass8.convert(com.chad.library.adapter.base.viewholder.BaseViewHolder, com.psychiatrygarden.activity.online.bean.QuestionDetailBean$OptionDTO):void");
        }
    }

    public static class ItemRecord {
        int height = 0;

        /* renamed from: top, reason: collision with root package name */
        int f13169top = 0;
    }

    public static /* synthetic */ int access$608(SubChoiceQuestionFragment subChoiceQuestionFragment) {
        int i2 = subChoiceQuestionFragment.pageNum;
        subChoiceQuestionFragment.pageNum = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$620(SubChoiceQuestionFragment subChoiceQuestionFragment, int i2) {
        int i3 = subChoiceQuestionFragment.pageNum - i2;
        subChoiceQuestionFragment.pageNum = i3;
        return i3;
    }

    private void addAnalyzeUserView() {
        ViewGroup viewGroup;
        try {
            RelativeLayout relativeLayout = this.rlAnalyzeUser;
            if (relativeLayout != null) {
                relativeLayout.removeAllViews();
            }
            RelativeLayout relativeLayout2 = this.rlAnswerUser;
            if (relativeLayout2 != null) {
                relativeLayout2.removeAllViews();
            }
            if (TextUtils.isEmpty(this.questionDetailBean.getStatData().getExplain_correction_tips())) {
                this.mTvAnswerRecord.setVisibility(8);
            }
            if (this.questionDetailBean.getStatData().getExplain_user() == null || TextUtils.isEmpty(this.questionDetailBean.getStatData().getExplain_user().getUser_id())) {
                this.mLyAnswerUserInfo.setVisibility(8);
            } else {
                this.mLyAnswerUserInfo.setVisibility(0);
                this.mTvAnswerUserNickName.setText(this.questionDetailBean.getStatData().getExplain_user().getNickname());
                this.mTvAnswerUserUpdateTime.setText(this.questionDetailBean.getStatData().getExplain_user().getCtime());
                this.mTvAnswerPraiseCount.setText(this.questionDetailBean.getStatData().getExplain_user().getPraise_num());
                boolean z2 = SkinManager.getCurrentSkinType(this.mContext) == 1;
                if (TextUtils.isEmpty(this.questionDetailBean.getStatData().getExplain_praise()) || !this.questionDetailBean.getStatData().getExplain_praise().equals("1")) {
                    this.mImgAnswerPraise.setImageResource(z2 ? R.drawable.dianzancourse_night : R.drawable.dianzancourse);
                } else {
                    this.mImgAnswerPraise.setImageResource(z2 ? R.drawable.youdianzan_night : R.drawable.youdianzan);
                }
                Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(this.questionDetailBean.getStatData().getExplain_user().getAvatar())).placeholder(R.mipmap.ic_avatar_def).into(this.mImgAnswerUserHead);
            }
            if (!TextUtils.isEmpty(this.questionDetailBean.getExplain()) && !TextUtils.isEmpty(this.questionDetailBean.getExplain_img())) {
                this.mLyAnswerUserInfo.setVisibility(8);
            }
            int i2 = 0;
            while (true) {
                int size = this.questionDetailBean.getStatData().getExplain_correction_avatar().size();
                int i3 = R.color.fourth_line_backgroup_color;
                viewGroup = null;
                if (i2 >= size) {
                    break;
                }
                View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.item_restore_userhead, (ViewGroup) null);
                CircleImageView circleImageView = (CircleImageView) viewInflate.findViewById(R.id.civ_restore_userhead);
                ((RelativeLayout.LayoutParams) circleImageView.getLayoutParams()).setMargins(ScreenUtil.getPxByDp(this.mContext, 18) * i2, 0, 0, 0);
                RequestBuilder<Drawable> requestBuilderLoad = Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(this.questionDetailBean.getStatData().getExplain_correction_avatar().get(i2)));
                Context context = circleImageView.getContext();
                if (SkinManager.getCurrentSkinType(ProjectApp.instance()) != 0) {
                    i3 = R.color.bg_backgroud_night;
                }
                requestBuilderLoad.placeholder(new ColorDrawable(ContextCompat.getColor(context, i3))).into(circleImageView);
                this.rlAnswerUser.addView(viewInflate);
                i2++;
            }
            boolean z3 = this.questionDetailBean.getStatData().getExplain_correction_avatar().size() > 0;
            this.rlAnswerUser.setVisibility(z3 ? 0 : 4);
            this.tvAnswerEdit.setVisibility(z3 ? 0 : 8);
            this.tvAnswerToEdit.setVisibility(z3 ? 8 : 0);
            if (z3) {
                this.tvAnswerEdit.setText(String.format(Locale.CHINA, "%d人编辑", Integer.valueOf(this.questionDetailBean.getStatData().getExplain_correction_number())));
            }
            this.mTvAnswerRecord.setText(this.questionDetailBean.getStatData().getExplain_correction_tips());
            if (TextUtils.isEmpty(this.questionDetailBean.getStatData().getOption_analysis_correction_tips())) {
                this.tvAnalyzeReword.setVisibility(8);
            }
            if (this.questionDetailBean.getStatData().getOption_analysis_user() == null || TextUtils.isEmpty(this.questionDetailBean.getStatData().getOption_analysis_user().getUser_id())) {
                this.mLyUserInfo.setVisibility(8);
            } else {
                this.mLyUserInfo.setVisibility(0);
                this.mTvUserNickName.setText(this.questionDetailBean.getStatData().getOption_analysis_user().getNickname());
                this.mTvUserUpdateTime.setText(this.questionDetailBean.getStatData().getOption_analysis_user().getCtime());
                this.mTvPraiseCount.setText(this.questionDetailBean.getStatData().getOption_analysis_user().getPraise_num());
                boolean z4 = SkinManager.getCurrentSkinType(this.mContext) == 1;
                if (TextUtils.isEmpty(this.questionDetailBean.getStatData().getOption_analysis_praise()) || !this.questionDetailBean.getStatData().getOption_analysis_praise().equals("1")) {
                    this.mImgPraise.setImageResource(z4 ? R.drawable.dianzancourse_night : R.drawable.dianzancourse);
                } else {
                    this.mImgPraise.setImageResource(z4 ? R.drawable.youdianzan_night : R.drawable.youdianzan);
                }
                Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(this.questionDetailBean.getStatData().getOption_analysis_user().getAvatar())).placeholder(R.mipmap.ic_avatar_def).into(this.mImgUserHead);
            }
            if (!TextUtils.isEmpty(this.questionDetailBean.getOption_analysis()) && !TextUtils.isEmpty(this.questionDetailBean.getOption_analysis_img())) {
                this.mLyUserInfo.setVisibility(8);
            }
            int i4 = 0;
            while (i4 < this.questionDetailBean.getStatData().getOption_analysis_correction_avatar().size()) {
                View viewInflate2 = LayoutInflater.from(this.mContext).inflate(R.layout.item_restore_userhead, viewGroup);
                CircleImageView circleImageView2 = (CircleImageView) viewInflate2.findViewById(R.id.civ_restore_userhead);
                ((RelativeLayout.LayoutParams) circleImageView2.getLayoutParams()).setMargins(ScreenUtil.getPxByDp(this.mContext, 18) * i4, 0, 0, 0);
                Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(this.questionDetailBean.getStatData().getOption_analysis_correction_avatar().get(i4))).placeholder(new ColorDrawable(ContextCompat.getColor(circleImageView2.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(circleImageView2);
                this.rlAnalyzeUser.addView(viewInflate2);
                i4++;
                viewGroup = null;
            }
            boolean z5 = this.questionDetailBean.getStatData().getOption_analysis_correction_avatar().size() > 0;
            this.rlAnalyzeUser.setVisibility(z5 ? 0 : 4);
            this.tvAnalyzeEdit.setVisibility(z5 ? 0 : 8);
            this.tvAnalyzeToEdit.setVisibility(z5 ? 8 : 0);
            if (z5) {
                this.tvAnalyzeEdit.setText(String.format(Locale.CHINA, "%d人编辑", Integer.valueOf(this.questionDetailBean.getStatData().getOption_analysis_correction_number())));
            }
            this.tvAnalyzeReword.setText(this.questionDetailBean.getStatData().getOption_analysis_correction_tips());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void addKnowledgePointTree(List<QuestionDetailBean.KnowledgePointTree> treeList, QuestionDetailBean.KnowledgePointTree tree) {
        if (tree == null || TextUtils.isEmpty(tree.getName())) {
            return;
        }
        treeList.add(tree);
        if (tree.getChildren() != null) {
            addKnowledgePointTree(treeList, tree.getChildren());
        }
    }

    private void addOrUpdateView(View view, final QuestionDetailBean.KnowledgePointTree tree, final int index, final int size, final List<QuestionDetailBean.KnowledgePointTree> treeList) {
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{R.attr.ic_question_knowledge_point_expand, R.attr.ic_question_knowledge_point_collapse, R.attr.first_txt_color, R.attr.main_theme_color});
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(1);
        int color = typedArrayObtainStyledAttributes.getColor(2, this.mContext.getColor(R.color.first_txt_color));
        int color2 = typedArrayObtainStyledAttributes.getColor(3, this.mContext.getColor(R.color.main_theme_color));
        TextView textView = (TextView) view.findViewById(R.id.tv_title);
        View viewFindViewById = view.findViewById(R.id.view_last);
        View viewFindViewById2 = view.findViewById(R.id.leftView);
        View viewFindViewById3 = view.findViewById(R.id.lLine);
        textView.setText(tree.getName());
        int i2 = size - 1;
        viewFindViewById.setVisibility(index != i2 ? 8 : 0);
        if (index == i2) {
            color = color2;
        }
        textView.setTextColor(color);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_exp_col);
        imageView.setVisibility(index == i2 ? 8 : 0);
        if (tree.isExpand()) {
            drawable = drawable2;
        }
        imageView.setImageDrawable(drawable);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.s0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f13332c.lambda$addOrUpdateView$11(index, treeList, view2);
            }
        });
        viewFindViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.t0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f13342c.lambda$addOrUpdateView$12(index, treeList, view2);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.u0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f13354c.lambda$addOrUpdateView$14(index, tree, treeList, size, view2);
            }
        });
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.leftMargin = SizeUtil.dp2px(this.mContext, index * 16);
        layoutParams.topMargin = index == 0 ? 0 : -SizeUtil.dp2px(this.mContext, 25);
        this.knowledgePointTreeViewMap.put(Integer.valueOf(index), view);
        if (index == i2) {
            viewFindViewById3.setVisibility(8);
        } else {
            viewFindViewById3.setVisibility(tree.isShowLine() ? 0 : 8);
        }
        if (!tree.isVisible()) {
            layoutParams.topMargin = -SizeUtil.dp2px(this.mContext, 56);
        }
        view.setLayoutParams(layoutParams);
        LogUtils.d("convert", "title = " + this.questionDetailBean.getTitle() + ",top =" + layoutParams.topMargin + ",position = " + index + ",expand = " + tree.isExpand() + ",visible = " + tree.isVisible());
        view.setVisibility(tree.isVisible() ? 0 : 8);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void addRestoreUserView() {
        try {
            RelativeLayout relativeLayout = this.rlRestoreUser;
            if (relativeLayout != null) {
                relativeLayout.removeAllViews();
            }
            if (TextUtils.isEmpty(this.questionDetailBean.getStatData().getRestore_correction_tips())) {
                this.tvRestoreReword.setVisibility(8);
            }
            if (this.questionDetailBean.getStatData().getRestore_user() == null || TextUtils.isEmpty(this.questionDetailBean.getStatData().getRestore_user().getId())) {
                this.mLyRestoreUserInfo.setVisibility(8);
            } else {
                this.mLyRestoreUserInfo.setVisibility(0);
                this.mTvRestoreUserNickName.setText(this.questionDetailBean.getStatData().getRestore_user().getNickname());
                this.mTvRestoreUserUpdateTime.setText(this.questionDetailBean.getStatData().getRestore_user().getCtime());
                this.mTvRestorePraiseCount.setText(this.questionDetailBean.getStatData().getRestore_user().getPraise_num());
                boolean z2 = SkinManager.getCurrentSkinType(this.mContext) == 1;
                if (TextUtils.isEmpty(this.questionDetailBean.getStatData().getRestore_praise()) || !this.questionDetailBean.getStatData().getRestore_praise().equals("1")) {
                    this.mImgRestorePraise.setImageResource(z2 ? R.drawable.dianzancourse_night : R.drawable.dianzancourse);
                } else {
                    this.mImgRestorePraise.setImageResource(z2 ? R.drawable.youdianzan_night : R.drawable.youdianzan);
                }
                Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(this.questionDetailBean.getStatData().getRestore_user().getAvatar())).placeholder(R.mipmap.ic_avatar_def).into(this.mImgRestoreUserHead);
            }
            if (!TextUtils.isEmpty(this.questionDetailBean.getRestore()) && !TextUtils.isEmpty(this.questionDetailBean.getRestore_img())) {
                this.mLyRestoreUserInfo.setVisibility(8);
            }
            for (int i2 = 0; i2 < this.questionDetailBean.getStatData().getRestore_correction_avatar().size(); i2++) {
                View viewInflate = View.inflate(this.mContext, R.layout.item_restore_userhead, null);
                CircleImageView circleImageView = (CircleImageView) viewInflate.findViewById(R.id.civ_restore_userhead);
                ((RelativeLayout.LayoutParams) circleImageView.getLayoutParams()).setMargins(ScreenUtil.getPxByDp(this.mContext, 18) * i2, 0, 0, 0);
                Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(this.questionDetailBean.getStatData().getRestore_correction_avatar().get(i2))).placeholder(new ColorDrawable(ContextCompat.getColor(circleImageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(circleImageView);
                this.rlRestoreUser.addView(viewInflate);
            }
            if (this.questionDetailBean.getStatData().getRestore_correction_avatar().size() > 0) {
                this.tvRestoreEdit.setText(String.format(Locale.CHINA, "%d人编辑", Integer.valueOf(this.questionDetailBean.getStatData().getRestore_correction_number())));
                this.rlRestoreUser.setVisibility(0);
                this.tvRestoreEdit.setVisibility(0);
                this.tvRestoreToEdit.setVisibility(8);
            } else {
                this.rlRestoreUser.setVisibility(4);
                this.tvRestoreEdit.setVisibility(8);
                this.tvRestoreToEdit.setVisibility(0);
            }
            this.tvRestoreReword.setText(this.questionDetailBean.getStatData().getRestore_correction_tips());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void analysisPraiseAction(String id, final String action, final String type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", id);
        ajaxParams.put("action", action);
        ajaxParams.put("type", type);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.analysisPraise, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment.12
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            /* JADX WARN: Removed duplicated region for block: B:41:0x00d3  */
            /* JADX WARN: Removed duplicated region for block: B:65:0x0204  */
            @Override // net.tsz.afinal.http.AjaxCallBack
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onSuccess(java.lang.String r13) {
                /*
                    Method dump skipped, instructions count: 798
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment.AnonymousClass12.onSuccess(java.lang.String):void");
            }
        });
    }

    private void bigOrAns() {
        String str;
        String str2;
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            if (TextUtils.isEmpty(this.questionDetailBean.getUser_answer())) {
                str = "答案：" + this.questionDetailBean.getAnswer().replaceAll(",", "");
            } else if (this.questionDetailBean.getIs_right().equals("1")) {
                str = "答案：正确答案 " + this.questionDetailBean.getAnswer().replaceAll(",", "") + "，你的答案 <font color='#5EAE06'>" + this.questionDetailBean.getUser_answer() + "</font>";
            } else {
                str = "答案：正确答案 " + this.questionDetailBean.getAnswer().replaceAll(",", "") + "，你的答案 <font color='#dd594a'>" + this.questionDetailBean.getUser_answer() + "</font>";
            }
        } else if (TextUtils.isEmpty(this.questionDetailBean.getUser_answer())) {
            str = "答案：" + this.questionDetailBean.getAnswer().replaceAll(",", "");
        } else if (this.questionDetailBean.getIs_right().equals("1")) {
            str = "答案：正确答案 " + this.questionDetailBean.getAnswer().replaceAll(",", "") + "，你的答案 <font color='#69A063'>" + this.questionDetailBean.getUser_answer() + "</font>";
        } else {
            str = "答案：正确答案 " + this.questionDetailBean.getAnswer().replaceAll(",", "") + "，你的答案 <font color='#B2575C'>" + this.questionDetailBean.getUser_answer() + "</font>";
        }
        this.questiondetails_tv_Answer.setText(Html.fromHtml(str));
        if (ConfigUtils.isHidden(4)) {
            this.questiondetails_tv_outline.setVisibility(8);
            return;
        }
        if (SharePreferencesUtils.readStrConfig(CommonParameter.am_pm, this.mContext, "").equals("1")) {
            str2 = "大纲：" + this.questionDetailBean.getOutlines_am();
            if (TextUtils.isEmpty(this.questionDetailBean.getOutlines_am())) {
                this.questiondetails_tv_outline.setVisibility(8);
            } else {
                this.questiondetails_tv_outline.setVisibility(0);
            }
        } else if (SharePreferencesUtils.readStrConfig(CommonParameter.am_pm, this.mContext, "").equals("2")) {
            str2 = "大纲：" + this.questionDetailBean.getOutlines_pm();
            if (TextUtils.isEmpty(this.questionDetailBean.getOutlines_pm())) {
                this.questiondetails_tv_outline.setVisibility(8);
            } else {
                this.questiondetails_tv_outline.setVisibility(0);
            }
        } else {
            str2 = "大纲：" + this.questionDetailBean.getOutlines();
            if (TextUtils.isEmpty(this.questionDetailBean.getOutlines())) {
                this.questiondetails_tv_outline.setVisibility(8);
            } else {
                this.questiondetails_tv_outline.setVisibility(0);
            }
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str2);
        Matcher matcher = Pattern.compile("考试大纲未要求").matcher(str2);
        while (matcher.find()) {
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, this.grayColors, null), matcher.start(0), matcher.end(0), 34);
        }
        this.questiondetails_tv_outline.setText(spannableStringBuilder);
        if ("search".equals(this.externalsources)) {
            this.questiondetails_tv_outline.setVisibility(8);
            this.sourcetv.setVisibility(8);
        }
    }

    private void click(int position, List<QuestionDetailBean.KnowledgePointTree> treeList) {
        this.collapseExpPos = position;
        boolean zIsExpand = treeList.get(position).isExpand();
        for (int i2 = position; i2 < treeList.size(); i2++) {
            QuestionDetailBean.KnowledgePointTree knowledgePointTree = treeList.get(i2);
            if (zIsExpand) {
                knowledgePointTree.setExpand(false);
                if (i2 > position) {
                    knowledgePointTree.setVisible(false);
                }
            } else {
                knowledgePointTree.setVisible(true);
                knowledgePointTree.setExpand(true);
            }
            updateLineStates(position, treeList);
        }
        for (int i3 = 0; i3 < treeList.size(); i3++) {
            if (this.knowledgePointTreeViewMap.get(Integer.valueOf(i3)) != null) {
                addOrUpdateView(this.knowledgePointTreeViewMap.get(Integer.valueOf(i3)), treeList.get(i3), i3, treeList.size(), treeList);
            }
        }
    }

    private void doChangeColor() {
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i2 >= this.mQuestionOptionBean.size()) {
                break;
            }
            if ("1".equals(this.mQuestionOptionBean.get(i2).getType())) {
                z2 = true;
                break;
            }
            i2++;
        }
        if (z2) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
                this.tvPushAnswer.setTextColor(Color.parseColor("#64729F"));
                return;
            } else {
                this.tvPushAnswer.setTextColor(Color.parseColor("#000000"));
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            this.tvPushAnswer.setTextColor(Color.parseColor("#38456D"));
        } else {
            this.tvPushAnswer.setTextColor(Color.parseColor("#B7B7B7"));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0035  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void doSelectOption(int r12) {
        /*
            Method dump skipped, instructions count: 750
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment.doSelectOption(int):void");
    }

    private String formatUseTime(String prefix, int time) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        int i2 = time / 3600;
        if (i2 > 0) {
            sb.append(i2);
            sb.append("h");
        }
        int i3 = time % 3600;
        int i4 = i3 / 60;
        if (i4 > 0) {
            sb.append(i4);
            sb.append("m");
        }
        int i5 = i3 % 60;
        if (i5 > 0) {
            sb.append(i5);
            sb.append("s");
        }
        return sb.toString();
    }

    private void getAudioInfo() {
        Log.e("question_Id", "question_id=" + this.questionDetailBean.getId() + ";title=" + this.questionDetailBean.getTitle());
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.questionDetailBean.getId());
        ajaxParams.put("resource_type", "2");
        ajaxParams.put("video_id", this.questionDetailBean.getStem_audio_list().get(0).getVideo_id());
        QuestionDataRequest.getIntance(this.mContext).getMeidaSourceById(ajaxParams, "2", this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentAd() {
        if (this.hasAnswer) {
            YJYHttpUtils.get(this.mContext, NetworkRequestsURL.adsInCommentApi, new AjaxParams(), new AnonymousClass5());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentListData() {
        this.isSdkAd = false;
        this.llAd.setVisibility(8);
        if (this.pageNum == 1) {
            this.break_point = String.valueOf(System.currentTimeMillis() / 1000);
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("module_type", "1");
        ajaxParams.put("obj_id", String.valueOf(this.question_id));
        ajaxParams.put("comment_type", "2");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.pageNum));
        if (getArguments() != null) {
            if (getArguments().getBoolean("isNewCom", false)) {
                ajaxParams.put(com.alipay.sdk.authjs.a.f3174g, "mycomment");
            }
            if (getArguments().getBoolean("isNewComzantong", false)) {
                ajaxParams.put(com.alipay.sdk.authjs.a.f3174g, "mypraise");
            }
        }
        ajaxParams.put("break_point", this.break_point);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mCommentList, ajaxParams, new AnonymousClass4());
    }

    public static SubChoiceQuestionFragment getInstance(Bundle args) {
        SubChoiceQuestionFragment subChoiceQuestionFragment = new SubChoiceQuestionFragment();
        subChoiceQuestionFragment.setArguments(args);
        return subChoiceQuestionFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNewPositions() {
        for (int i2 = 0; i2 < this.commlist.size(); i2++) {
            CommentBean.DataBean.HotBean hotBean = this.commlist.get(i2);
            int type = hotBean.getType();
            boolean zIsHot = hotBean.isHot();
            if (type == 1 && !zIsHot && hotBean.getName() != null && hotBean.getName().contains("最新")) {
                Locale locale = Locale.CHINA;
                int i3 = this.totalNewCommentNum + 1;
                this.totalNewCommentNum = i3;
                hotBean.setName(String.format(locale, "最新评论（%d）", Integer.valueOf(i3)));
                return i2 + 1;
            }
        }
        return -1;
    }

    private void getNoteData(final View v2) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.questionDetailBean.getId());
        ajaxParams.put("module_type", this.module_type);
        QuestionDataRequest.getIntance(this.mContext).questionNoteData(ajaxParams, this);
    }

    private void getQuestionStatistics() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.questionDetailBean.getId());
        ajaxParams.put("module_type", TextUtils.isEmpty(this.questionDetailBean.getKnowledge_id()) ? this.module_type : "110");
        QuestionDataRequest.getIntance(this.mContext).questionStatData(ajaxParams, this);
    }

    private void getRestoreStr(String str, TextView mTextV, String number, int type) {
        String str2 = str;
        Matcher matcher = Pattern.compile("[(（]([\\w*]+)?P\\d+(-P?(\\d+)?)?+[)）]").matcher(str2);
        ColorStateList colorStateList = ContextCompat.getColorStateList(this.mContext, SkinManager.getCurrentSkinType(getActivity()) == 1 ? R.color.dominant_color_night : R.color.dominant_color);
        ColorStateList colorStateList2 = SkinManager.getCurrentSkinType(this.mContext) == 0 ? ContextCompat.getColorStateList(this.mContext, R.color.black) : ContextCompat.getColorStateList(this.mContext, R.color.question_color_night);
        int i2 = 0;
        if (SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_restore_img, this.mContext).equals("1") || ("unit".equals(this.category) && this.show_restore_img.equals("0"))) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str2);
            while (matcher.find()) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), matcher.start(0), matcher.end(0), 34);
            }
            Matcher matcher2 = Pattern.compile("（([A-Z]+[对|错]([^）]+)?)）").matcher(str2);
            while (matcher2.find()) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList2, null), matcher2.start(0), matcher2.end(0), 34);
            }
            mTextV.setText(spannableStringBuilder);
            return;
        }
        String str3 = type != 1 ? type != 2 ? "" : NetworkRequestsURL.CommentIameUrl2 : NetworkRequestsURL.CommentIameUrl;
        final ArrayList arrayList = new ArrayList();
        int i3 = 0;
        while (matcher.find()) {
            String strGroup = matcher.group();
            str2 = str2.substring(i2, matcher.end(i2) + i3) + "&" + str2.substring(matcher.end(i2) + i3);
            String native_app_id = this.questionDetailBean.getNative_app_id();
            if (TextUtils.isEmpty(native_app_id) || native_app_id.equals("0")) {
                native_app_id = this.questionDetailBean.getApp_id();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append(native_app_id);
            sb.append("/");
            sb.append(number);
            sb.append(strGroup.substring(1, strGroup.length() - 1));
            sb.append("-");
            i3++;
            sb.append(i3);
            sb.append(".jpg?x-oss-process=style/water_mark");
            arrayList.add(sb.toString());
            i2 = 0;
        }
        Matcher matcher3 = Pattern.compile("[(（]([\\w*]+)?P\\d+(-P?(\\d+)?)?+[)）]").matcher(str2);
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(str2);
        Paint.FontMetrics fontMetrics = mTextV.getPaint().getFontMetrics();
        int iCeil = ((int) Math.ceil(fontMetrics.descent - fontMetrics.top)) + 2;
        int i4 = 0;
        this.drawable.setBounds(0, 0, (((this.drawable.getIntrinsicWidth() * iCeil) / this.drawable.getIntrinsicHeight()) / 5) * 4, (iCeil / 5) * 4);
        final int i5 = 0;
        while (matcher3.find()) {
            spannableStringBuilder2.setSpan(new StickerSpan(this.drawable, 1), matcher3.end(i4), matcher3.end(i4) + 1, 33);
            spannableStringBuilder2.setSpan(new TextClick(new DomoIml() { // from class: com.psychiatrygarden.activity.online.fragment.v0
                @Override // com.psychiatrygarden.interfaceclass.DomoIml
                public final void clickToast() {
                    this.f13366a.lambda$getRestoreStr$23(arrayList, i5);
                }
            }), matcher3.start(i4), matcher3.end(i4), 33);
            mTextV.setMovementMethod(LinkMovementMethod.getInstance());
            i5++;
            i4 = 0;
        }
        Matcher matcher4 = Pattern.compile("（([A-Z]+[对|错]([^）]+)?)）").matcher(str2);
        while (matcher4.find()) {
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, this.tvAnswerAnalysis.getTextColors(), null), matcher4.start(0), matcher4.end(0), 34);
        }
        int i6 = 0;
        int i7 = 34;
        Matcher matcher5 = Pattern.compile("[(（]([\\w*]+)?P\\d+(-P?(\\d+)?)?+[)）]").matcher(str2);
        while (matcher5.find()) {
            int i8 = i6;
            int i9 = i7;
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), matcher5.start(i8), matcher5.end(i8), i9);
            i7 = i9;
            i6 = i8;
        }
        mTextV.setText(spannableStringBuilder2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getScrollY() {
        int i2;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            i2 = this.mCurrentFirstVisibleItem;
            if (i3 >= i2) {
                break;
            }
            if (this.recordSp.get(i3) != null) {
                i4 += this.recordSp.get(i3).height;
            }
            i3++;
        }
        ItemRecord itemRecord = this.recordSp.get(i2);
        if (itemRecord == null) {
            itemRecord = new ItemRecord();
        }
        return i4 - itemRecord.f13169top;
    }

    private void getStaticsData() throws NumberFormatException {
        if (this.questionDetailBean.getStatData().getAnswer() != null) {
            initStaticData();
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.questionDetailBean.getId());
        ajaxParams.put("module_type", TextUtils.isEmpty(this.questionDetailBean.getKnowledge_id()) ? this.module_type : "110");
        QuestionDataRequest.getIntance(this.mContext).questionStatData(ajaxParams, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void havaCollectimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes);
        } else {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes_night);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void havaCommingimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.youpinglun);
        } else {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.youpinglun_night);
        }
    }

    private void haveNoteimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited);
        } else {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited_night);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void haveParise() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan);
        } else {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan_night);
        }
    }

    private void initAd(final Context mContext) {
        final HomepageSmallAdBean.DataDTO.DataAd dataAd = (HomepageSmallAdBean.DataDTO.DataAd) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.QUESTION_QUESTION_AD, mContext, ""), HomepageSmallAdBean.DataDTO.DataAd.class);
        if (dataAd == null || dataAd.getAds().isEmpty()) {
            this.mLyAdView.setVisibility(8);
            return;
        }
        if ((SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_BOTTOM_AD, mContext, 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_BOTTOM_AD, mContext, 0L).longValue()) / 1000) - dataAd.getTime_interval() : 0L) < 0) {
            this.mLyAdView.setVisibility(8);
            return;
        }
        this.mLyAdView.setVisibility(0);
        if (dataAd.getForce().equals("1")) {
            this.mImgCloseAd.setVisibility(8);
        } else {
            this.mImgCloseAd.setVisibility(0);
            this.mImgCloseAd.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.y0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13394c.lambda$initAd$40(mContext, view);
                }
            });
        }
        if (dataAd.getAds() == null || dataAd.getAds().size() <= 0) {
            return;
        }
        GlideApp.with(mContext).load((Object) GlideUtils.generateUrl(dataAd.getAds().get(0).getImg())).placeholder(R.mipmap.ic_order_default).into(this.mImgAd);
        this.mImgAd.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.z0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubChoiceQuestionFragment.lambda$initAd$41(dataAd, view);
            }
        });
    }

    private void initAnsData() {
        String explain = this.questionDetailBean.getExplain();
        String explain_img = this.questionDetailBean.getExplain_img();
        boolean z2 = SkinManager.getCurrentSkinType(this.mContext) == 1;
        if (ConfigUtils.isHidden(6)) {
            this.ll_answer_analysis_layout.setVisibility(8);
            this.mLineRestore.setVisibility(8);
        } else if (this.ll_answer_enter.getVisibility() == 8 && TextUtils.isEmpty(explain) && TextUtils.isEmpty(explain_img)) {
            this.ll_answer_analysis_layout.setVisibility(8);
            this.mLineRestore.setVisibility(8);
        } else {
            this.ll_answer_analysis_layout.setVisibility(0);
            this.mLineRestore.setVisibility(0);
            if (TextUtils.isEmpty(explain) && TextUtils.isEmpty(explain_img)) {
                this.mLyAnswerUserInfo.setVisibility(8);
                this.tvAnswerAnalysis.setText("暂无解析");
                this.tvAnswerAnalysis.setTextSize(14.0f);
                this.tvAnswerAnalysis.setTextColor(z2 ? Color.parseColor("#575F79") : Color.parseColor("#909499"));
                this.tvAnswerAnalysis.setGravity(17);
            } else {
                if (TextUtils.isEmpty(explain)) {
                    this.tvAnswerAnalysis.setVisibility(8);
                } else {
                    this.tvAnswerAnalysis.setVisibility(0);
                    this.tvAnswerAnalysis.setTextColor(z2 ? Color.parseColor("#7380A9") : Color.parseColor("#141516"));
                    this.tvAnswerAnalysis.setGravity(GravityCompat.START);
                    this.tvAnswerAnalysis.setTextSize(16.0f);
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(explain);
                    Matcher matcher = Pattern.compile("（([A-Z]+[对|错]([^）]+)?)）").matcher(explain);
                    while (matcher.find()) {
                        spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, this.tvAnswerAnalysis.getTextColors(), null), matcher.start(0), matcher.end(0), 34);
                    }
                    this.tvAnswerAnalysis.setText(spannableStringBuilder);
                }
                if (TextUtils.isEmpty(explain_img)) {
                    this.mImgExplain.setVisibility(8);
                } else {
                    this.mImgExplain.setVisibility(0);
                    Glide.with(this).load((Object) GlideUtils.generateUrl(explain_img)).placeholder(new ColorDrawable(ContextCompat.getColor(this.mImgExplain.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(this.mImgExplain);
                }
            }
        }
        if (ConfigUtils.isHidden(7)) {
            this.ll_encyclopedia_analysis_layout.setVisibility(8);
            this.mLineExpline.setVisibility(8);
        } else {
            if (this.llAnalyzeEnter.getVisibility() == 8 && TextUtils.isEmpty(this.questionDetailBean.getOption_analysis()) && TextUtils.isEmpty(this.questionDetailBean.getOption_analysis_img())) {
                this.ll_encyclopedia_analysis_layout.setVisibility(8);
                this.mLineExpline.setVisibility(8);
            } else {
                this.ll_encyclopedia_analysis_layout.setVisibility(0);
                if (TextUtils.isEmpty(this.questionDetailBean.getOption_analysis()) && TextUtils.isEmpty(this.questionDetailBean.getOption_analysis_img())) {
                    this.mLyUserInfo.setVisibility(8);
                    this.mTvEcyclopediaContents.setTextColor(z2 ? Color.parseColor("#575F79") : Color.parseColor("#909499"));
                    this.mTvEcyclopediaContents.setGravity(17);
                    this.mTvEcyclopediaContents.setTextSize(14.0f);
                    this.mTvEcyclopediaContents.setText("暂无解析");
                } else {
                    if (TextUtils.isEmpty(this.questionDetailBean.getOption_analysis())) {
                        this.mTvEcyclopediaContents.setVisibility(8);
                    } else {
                        this.mTvEcyclopediaContents.setVisibility(0);
                        this.mTvEcyclopediaContents.setTextColor(z2 ? Color.parseColor("#7380A9") : Color.parseColor("#141516"));
                        this.mTvEcyclopediaContents.setGravity(GravityCompat.START);
                        this.mTvEcyclopediaContents.setTextSize(16.0f);
                        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(this.questionDetailBean.getOption_analysis());
                        Matcher matcher2 = Pattern.compile("（([A-Z]+[对|错]([^）]+)?)）").matcher(this.questionDetailBean.getOption_analysis());
                        while (matcher2.find()) {
                            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, this.mTvEcyclopediaContents.getTextColors(), null), matcher2.start(0), matcher2.end(0), 34);
                        }
                        this.mTvEcyclopediaContents.setText(spannableStringBuilder2);
                    }
                    if (TextUtils.isEmpty(this.questionDetailBean.getOption_analysis_img())) {
                        this.mImgEncyclopediaExplain.setVisibility(8);
                    } else {
                        this.mImgEncyclopediaExplain.setVisibility(0);
                        Glide.with(this).load((Object) GlideUtils.generateUrl(this.questionDetailBean.getOption_analysis_img())).placeholder(new ColorDrawable(ContextCompat.getColor(this.mImgEncyclopediaExplain.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(this.mImgEncyclopediaExplain);
                    }
                }
            }
            if (this.ll_restore_point.getVisibility() == 0 && this.ll_answer_analysis_layout.getVisibility() == 8) {
                this.mLineRestore.setVisibility(0);
            }
        }
        if (this.ll_restore_point.getVisibility() == 8 && this.ll_answer_analysis_layout.getVisibility() == 8 && this.ll_encyclopedia_analysis_layout.getVisibility() == 8) {
            this.mLineVideo.setVisibility(8);
        }
        final ArrayList arrayList = new ArrayList();
        arrayList.add(explain_img);
        this.mImgExplain.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.g0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13228c.lambda$initAnsData$25(arrayList, view);
            }
        });
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add(this.questionDetailBean.getOption_analysis_img());
        this.mImgEncyclopediaExplain.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.h0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13236c.lambda$initAnsData$26(arrayList2, view);
            }
        });
        initErrorAnalysis();
    }

    private void initAudioInfo(String vid, String duration) {
        if (this.mAudioPlayerView == null) {
            this.mAudioPlayerView = new CustomAliPlayerView(this.mContext);
        }
        this.mAudioPlayerView.setIsVideo(false);
        CustomAliPlayerView customAliPlayerView = this.mAudioPlayerView;
        Context context = this.mContext;
        customAliPlayerView.initView(context, vid, UserConfig.isCanPlayBy4g(context));
        this.mAudioPlayerView.setSeeDuration(duration);
        this.mAudioPlayerView.setExpire_str("");
        this.mAudioPlayerView.setWatch_permission("1");
        this.mAudioPlayerView.setVids(vid);
        this.mSeekBar.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.online.fragment.c0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return SubChoiceQuestionFragment.lambda$initAudioInfo$36(view, motionEvent);
            }
        });
        this.mAudioPlayerView.setProgressListener(new CustomAliPlayerView.PlayProgressListener() { // from class: com.psychiatrygarden.activity.online.fragment.d0
            @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.PlayProgressListener
            public final void onProgress(float f2) {
                this.f13204a.lambda$initAudioInfo$37(f2);
            }
        });
        this.mAudioPlayerView.setOnPlayStatusLisenter(new CustomAliPlayerView.OnAudioPlayStatusListenter() { // from class: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment.10
            @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.OnAudioPlayStatusListenter
            public void onPlayEnd() {
                if (SkinManager.getCurrentSkinType(((BaseFragment) SubChoiceQuestionFragment.this).mContext) == 1) {
                    SubChoiceQuestionFragment.this.ivPlayPause.setImageResource(R.drawable.ic_question_video_play_night);
                } else {
                    SubChoiceQuestionFragment.this.ivPlayPause.setImageResource(R.drawable.ic_question_video_play_day);
                }
                SubChoiceQuestionFragment.this.mSeekBar.setProgress(0);
                SubChoiceQuestionFragment.this.tvCurrent.setText("00:00");
                SubChoiceQuestionFragment.this.playEnd = true;
            }

            @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.OnAudioPlayStatusListenter
            public void onPlaying() {
            }
        });
        AliyunVodPlayerView aliyunVodPlayerView = this.mAudioPlayerView.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.setOnPreparedListener(new IPlayer.OnPreparedListener() { // from class: com.psychiatrygarden.activity.online.fragment.e0
                @Override // com.aliyun.player.IPlayer.OnPreparedListener
                public final void onPrepared() {
                    this.f13212a.lambda$initAudioInfo$38();
                }
            });
            this.mAudioPlayerView.mAliyunVodPlayerView.setOnStoppedListener(new OnStoppedListener() { // from class: com.psychiatrygarden.activity.online.fragment.f0
                @Override // com.aliyun.player.alivcplayerexpand.listener.OnStoppedListener
                public final void onStop() {
                    this.f13221a.lambda$initAudioInfo$39();
                }
            });
        }
        CommonUtil.mPlayerData(vid, this.mAudioPlayerView, false);
    }

    private void initErrorAnalysis() {
        String error_analysis = this.questionDetailBean.getError_analysis();
        if (TextUtils.isEmpty(error_analysis) && TextUtils.isEmpty(this.questionDetailBean.getError_analysis_img())) {
            this.llWrongOptionAnalysis.setVisibility(8);
            return;
        }
        this.llWrongOptionAnalysis.setVisibility(0);
        getRestoreStr(error_analysis, this.tvWrongOptionAnalysis, this.questionDetailBean.getNumber(), 1);
        if (TextUtils.isEmpty(this.questionDetailBean.getError_analysis_img())) {
            return;
        }
        this.img_wrong.setVisibility(0);
        GlideUtils.loadImage(this.mContext, this.questionDetailBean.getError_analysis_img(), this.img_wrong);
        this.img_wrong.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.r0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13324c.lambda$initErrorAnalysis$27(view);
            }
        });
    }

    private void initJudgeQuestionOption() {
        QuestionDetailBean.OptionDTO optionDTO = new QuestionDetailBean.OptionDTO();
        QuestionDetailBean.OptionDTO optionDTO2 = new QuestionDetailBean.OptionDTO();
        optionDTO.setKey("正确");
        optionDTO.setTitle("");
        optionDTO2.setKey("错误");
        optionDTO2.setTitle("");
        String answer = this.questionDetailBean.getAnswer();
        if (TextUtils.isEmpty(this.questionDetailBean.getUser_answer())) {
            optionDTO.setType("0");
            optionDTO2.setType("0");
        } else {
            optionDTO.setType("3");
            optionDTO2.setType("3");
            if (TextUtils.equals(answer, this.questionDetailBean.getUser_answer())) {
                if (TextUtils.equals("正确", answer)) {
                    optionDTO.setType("2");
                    optionDTO2.setType("0");
                } else {
                    optionDTO2.setType("2");
                    optionDTO.setType("0");
                }
            } else if (TextUtils.equals("正确", answer)) {
                optionDTO.setType("2");
            } else if (this.questionDetailBean.getUser_answer().equals("正确")) {
                optionDTO2.setType("2");
            }
        }
        this.mQuestionOptionBean.clear();
        this.mQuestionOptionBean.add(optionDTO);
        this.mQuestionOptionBean.add(optionDTO2);
        this.questionDetailBean.setOption(this.mQuestionOptionBean);
    }

    private void initListView(ViewHolder holder) {
        this.lineselect2 = (LinearLayout) holder.get(R.id.lineselect2);
        this.remen = (CheckedTextView) holder.get(R.id.remen2);
        this.zuixin = (CheckedTextView) holder.get(R.id.zuixin2);
        isSelect(true, false);
        this.mPinnedSecListView.setScrollingCacheEnabled(false);
        this.mPinnedSecListView.setAnimationCacheEnabled(false);
        this.mPinnedSecListView.setTag(String.valueOf(this.question_id));
        setListener();
        this.mPinnedSecListView.addHeaderView(this.headerContentView);
        QuestionCommentListAdapter questionCommentListAdapter = new QuestionCommentListAdapter(this, this.mContext, this.commlist, this.time_line, 1, "2", this.question_id + "", (AnswerQuestionActivity) getActivity(), true, true);
        this.mCommListAdapter = questionCommentListAdapter;
        this.mPinnedSecListView.setAdapter((ListAdapter) questionCommentListAdapter);
        this.mCommListAdapter.setActionListener(new QuestionCommentListAdapter.OnCommentActionListener() { // from class: com.psychiatrygarden.activity.online.fragment.w0
            @Override // com.psychiatrygarden.adapter.QuestionCommentListAdapter.OnCommentActionListener
            public final void onPraiseAction(boolean z2) {
                this.f13377a.lambda$initListView$3(z2);
            }
        });
    }

    private void initQuestionList() {
        AnonymousClass8 anonymousClass8 = new AnonymousClass8(R.layout.item_question_option, this.mQuestionOptionBean);
        this.qAdapter = anonymousClass8;
        this.qlistview.setAdapter(anonymousClass8);
        RecyclerView.ItemAnimator itemAnimator = this.qlistview.getItemAnimator();
        if (itemAnimator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        }
        this.qAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.q0
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) throws JSONException {
                this.f13316c.lambda$initQuestionList$28(baseQuickAdapter, view, i2);
            }
        });
    }

    private void initQuestionType() {
        initSubViewData();
        if (!TextUtils.isEmpty(this.questionDetailBean.getUser_answer()) || TextUtils.equals(this.answerMode, Constants.ANSWER_MODE.RECITE_MODE)) {
            getQuestionStatistics();
        }
        if ("1".equals(this.questionDetailBean.getIs_new())) {
            this.tvQuestionNew.setVisibility(0);
        } else {
            this.tvQuestionNew.setVisibility(8);
        }
        this.typeStr.setText(this.questionDetailBean.getType_str());
        this.sourcetv.setVisibility(TextUtils.isEmpty(this.questionDetailBean.getSource()) ? 8 : 0);
        if (TextUtils.isEmpty(this.questionDetailBean.getSource())) {
            this.sourcetv.setVisibility(8);
        } else {
            this.sourcetv.setText(String.format(Locale.CHINA, "来源：%s", this.questionDetailBean.getSource()));
        }
        this.llEasyWrongOption.setVisibility(("1".equals(SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_easy_option, this.mContext)) || TextUtils.isEmpty(this.questionDetailBean.getKnowledge_id())) ? 8 : 0);
        this.tvEasyWrongOption.setText("易错答案 " + this.questionDetailBean.getEasyOption());
        if (TextUtils.isEmpty(this.questionDetailBean.getKnowledge_id())) {
            this.lineKnowledge.setVisibility(8);
        } else {
            this.lineKnowledge.setVisibility(0);
        }
        if (TextUtils.isEmpty(SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_correction_error, this.mContext)) || SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_correction_error, this.mContext).equals("1")) {
            this.llRestoreEnter.setVisibility(8);
            this.tvRestoreReword.setVisibility(8);
        } else {
            this.llRestoreEnter.setVisibility(0);
            this.tvRestoreReword.setVisibility(0);
        }
        if (TextUtils.isEmpty(SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_analysis_update, this.mContext)) || SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_analysis_update, this.mContext).equals("1")) {
            this.ll_answer_enter.setVisibility(8);
            this.mTvAnswerRecord.setVisibility(8);
        } else {
            this.ll_answer_enter.setVisibility(0);
            this.mTvAnswerRecord.setVisibility(0);
        }
        if (TextUtils.isEmpty(SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_options_update, this.mContext)) || SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_options_update, this.mContext).equals("1")) {
            this.llAnalyzeEnter.setVisibility(8);
            this.tvAnalyzeReword.setVisibility(8);
        } else {
            this.llAnalyzeEnter.setVisibility(0);
            this.tvAnalyzeReword.setVisibility(0);
        }
        titlenum();
        updateCutIcon();
        initTitleImg();
        initQuestionList();
        bigOrAns();
        initRestoreData();
        initAnsData();
        initVideoData();
        if (!this.answerMode.equals(Constants.ANSWER_MODE.TEST_MODE) || !TextUtils.isEmpty(this.questionDetailBean.getUser_answer())) {
            if (this.questionDetailBean.getDataBiao() != null) {
                initBiaoQianData(this.questionDetailBean.getDataBiao());
                this.llMoreColumns.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.i1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f13248c.lambda$initQuestionType$10(view);
                    }
                });
            } else {
                getBiaoData();
            }
        }
        this.tvPushAnswer.setOnClickListener(this);
        this.tvCommentNum.setOnClickListener(this);
        this.llQuestionComment.setOnClickListener(this);
        this.ly_questiondetails_btn_collect.setOnClickListener(this);
        this.ly_questiondetails_btn_edit.setOnClickListener(this);
        this.tv_correction.setOnClickListener(this);
        this.ly_questiondetails_btn_centerMsg.setOnClickListener(this);
        this.ly_questiondetails_btn_zantong.setOnClickListener(this);
        this.btn_comment.setOnClickListener(this);
        this.tvRestoreEdit.setOnClickListener(this);
        this.tvAnalyzeEdit.setOnClickListener(this);
        this.tvRestoreToEdit.setOnClickListener(this);
        this.tvAnalyzeToEdit.setOnClickListener(this);
        this.tvAnswerToEdit.setOnClickListener(this);
        this.mLyVideoView.setOnClickListener(this);
        this.mImgAudio.setOnClickListener(this);
        this.llAudio.setOnClickListener(this);
        TextView textView = this.typeStr;
        textView.setTag(Float.valueOf(textView.getTextSize()));
        TextView textView2 = this.tvPageNum;
        textView2.setTag(Float.valueOf(textView2.getTextSize()));
        TextView textView3 = this.tvTitle;
        textView3.setTag(Float.valueOf(textView3.getTextSize()));
        TextView textView4 = this.tvCommentNum;
        textView4.setTag(Float.valueOf(textView4.getTextSize()));
        TextView textView5 = this.questiondetails_tv_Answer;
        textView5.setTag(Float.valueOf(textView5.getTextSize()));
        TextView textView6 = this.questiondetails_tv_statistics;
        textView6.setTag(Float.valueOf(textView6.getTextSize()));
        TextView textView7 = this.tvStatistics;
        textView7.setTag(Float.valueOf(textView7.getTextSize()));
        TextView textView8 = this.biaotxt;
        textView8.setTag(Float.valueOf(textView8.getTextSize()));
        TextView textView9 = this.questiondetails_tv_outline;
        textView9.setTag(Float.valueOf(textView9.getTextSize()));
        TextView textView10 = this.sourcetv;
        textView10.setTag(Float.valueOf(textView10.getTextSize()));
        TextView textView11 = this.tv_correction;
        textView11.setTag(Float.valueOf(textView11.getTextSize()));
        TextView textView12 = this.questiondetails_tv_content_ques1;
        textView12.setTag(Float.valueOf(textView12.getTextSize()));
        TextView textView13 = this.tvAnswerAnalyze;
        textView13.setTag(Float.valueOf(textView13.getTextSize()));
        TextView textView14 = this.tvAnswerAnalysis;
        textView14.setTag(Float.valueOf(textView14.getTextSize()));
        TextView textView15 = this.textViewDifficulty;
        textView15.setTag(Float.valueOf(textView15.getTextSize()));
        TextView textView16 = this.mTvEncyclopediaAnalyze;
        textView16.setTag(Float.valueOf(textView16.getTextSize()));
        setFontSize();
        if (this.questionDetailBean.getKnowledge_path() == null || TextUtils.isEmpty(this.questionDetailBean.getKnowledge_id())) {
            this.ll_knowledge_point.setVisibility(8);
        } else {
            loadKnowledgeTree();
            this.ll_knowledge_point.setVisibility(0);
        }
    }

    private void initRestoreData() {
        try {
            if (ConfigUtils.isHidden(5)) {
                this.ll_restore_point.setVisibility(8);
            } else {
                boolean z2 = SkinManager.getCurrentSkinType(this.mContext) == 1;
                if (this.llRestoreEnter.getVisibility() == 8 && TextUtils.isEmpty(this.questionDetailBean.getRestore()) && TextUtils.isEmpty(this.questionDetailBean.getRestore_img())) {
                    this.ll_restore_point.setVisibility(8);
                } else {
                    this.ll_restore_point.setVisibility(0);
                    if (!TextUtils.isEmpty(this.questionDetailBean.getRestore())) {
                        this.questiondetails_tv_content_ques1.setTextColor(Color.parseColor(z2 ? "#7380A9" : "#141516"));
                        this.questiondetails_tv_content_ques1.setGravity(GravityCompat.START);
                        this.questiondetails_tv_content_ques1.setTextSize(16.0f);
                        this.questiondetails_tv_content_ques1.setVisibility(0);
                        getRestoreStr(this.questionDetailBean.getRestore(), this.questiondetails_tv_content_ques1, this.questionDetailBean.getNumber(), 1);
                    }
                    if (TextUtils.isEmpty(this.questionDetailBean.getRestore_img())) {
                        this.mImgOriginal.setVisibility(8);
                        if (TextUtils.isEmpty(this.questionDetailBean.getRestore())) {
                            this.questiondetails_tv_content_ques1.setVisibility(8);
                            this.mLyRestoreUserInfo.setVisibility(8);
                        }
                    } else {
                        if (TextUtils.isEmpty(this.questionDetailBean.getRestore())) {
                            this.questiondetails_tv_content_ques1.setVisibility(8);
                        }
                        this.mImgOriginal.setVisibility(0);
                        Glide.with(this).load((Object) GlideUtils.generateUrl(this.questionDetailBean.getRestore_img())).placeholder(this.mContext.getDrawable(R.mipmap.ic_order_default)).into(this.mImgOriginal);
                    }
                }
            }
            this.mImgOriginal.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.n1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13291c.lambda$initRestoreData$22(view);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void initSelectedAnsed() {
        String strTrim = this.questionDetailBean.getAnswer().replace(",", "").trim();
        String strTrim2 = this.questionDetailBean.getUser_answer().replace(",", "").trim();
        if (TextUtils.equals(strTrim2, "0")) {
            return;
        }
        if (!this.questionDetailBean.getType().equals(com.tencent.connect.common.Constants.VIA_ACT_TYPE_NINETEEN) && strTrim.length() <= 1) {
            char[] charArray = strTrim.toCharArray();
            if (TextUtils.isEmpty(strTrim) || charArray[0] - 'A' >= this.mQuestionOptionBean.size() || charArray[0] - 'A' < 0) {
                return;
            }
            if (TextUtils.isEmpty(strTrim2)) {
                this.mQuestionOptionBean.get(charArray[0] - 'A').setType("4");
                return;
            }
            this.mQuestionOptionBean.get(charArray[0] - 'A').setType("2");
            if (TextUtils.equals(strTrim, strTrim2)) {
                return;
            }
            char[] charArray2 = strTrim2.toCharArray();
            if (charArray2[0] - 'A' < this.mQuestionOptionBean.size()) {
                char c3 = charArray2[0];
                if (c3 - 'A' < 0) {
                    return;
                }
                this.mQuestionOptionBean.get(c3 - 'A').setType("3");
                return;
            }
            return;
        }
        if (TextUtils.isEmpty(strTrim2)) {
            for (char c4 : strTrim.toCharArray()) {
                int i2 = c4 - 'A';
                if (i2 >= this.mQuestionOptionBean.size() || i2 < 0) {
                    return;
                }
                this.mQuestionOptionBean.get(i2).setType("4");
            }
            return;
        }
        char[] charArray3 = strTrim.toCharArray();
        for (char c5 : strTrim2.toCharArray()) {
            int i3 = c5 - 'A';
            if (i3 >= this.mQuestionOptionBean.size() || i3 < 0) {
                return;
            }
            this.mQuestionOptionBean.get(i3).setType("3");
            for (char c6 : charArray3) {
                int i4 = c6 - 'A';
                if (i4 >= this.mQuestionOptionBean.size() || i4 < 0) {
                    return;
                }
                if (this.mQuestionOptionBean.get(i4).getKey().trim().equals(this.mQuestionOptionBean.get(i3).getKey().trim())) {
                    this.mQuestionOptionBean.get(i4).setType("2");
                } else if (!this.mQuestionOptionBean.get(i4).getType().equals("2")) {
                    this.mQuestionOptionBean.get(i4).setType("4");
                }
            }
        }
    }

    private void initStaticData() throws NumberFormatException {
        String stat_info;
        double right_count;
        String question_level;
        try {
            if (this.mContext == null) {
                return;
            }
            if (!TextUtils.isEmpty(this.questionDetailBean.getKnowledge_id())) {
                new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.o0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f13298c.lambda$initStaticData$20();
                    }
                }, 1000L);
            }
            this.tvEasyWrongOptions.setText(this.questionDetailBean.getEasyOption());
            String str = this.questionDetailBean.getStatData().getAccuracy() + "%";
            SpannableString spannableString = new SpannableString(str);
            spannableString.setSpan(new AbsoluteSizeSpan(14, true), 0, str.indexOf("%"), 33);
            this.tvAllCorrectPercent.setText(spannableString);
            String all_count = this.questionDetailBean.getStatData().getAll_count();
            if (TextUtils.isEmpty(all_count)) {
                all_count = "0";
            }
            if (all_count.matches(RegexPool.NUMBERS)) {
                int i2 = Integer.parseInt(all_count);
                if (i2 > 10000) {
                    String str2 = String.format(Locale.CHINA, "%d.%d万次", Integer.valueOf(i2 / 10000), Integer.valueOf((i2 % 10000) / 1000));
                    int iIndexOf = str2.indexOf("万次");
                    SpannableString spannableString2 = new SpannableString(str2);
                    spannableString2.setSpan(new AbsoluteSizeSpan(14, true), 0, iIndexOf, 33);
                    this.tvTotalDoTimes.setText(spannableString2);
                } else {
                    String str3 = all_count + "次";
                    int iIndexOf2 = str3.indexOf("次");
                    SpannableString spannableString3 = new SpannableString(str3);
                    spannableString3.setSpan(new AbsoluteSizeSpan(14, true), 0, iIndexOf2, 33);
                    this.tvTotalDoTimes.setText(spannableString3);
                }
            } else {
                SpannableString spannableString4 = new SpannableString("0次");
                spannableString4.setSpan(new AbsoluteSizeSpan(14, true), 0, 1, 33);
                this.tvTotalDoTimes.setText(spannableString4);
            }
            if (this.questionDetailBean.getStatData().getAnswer() != null) {
                this.questiondetailsLayoutDiff.setVisibility(0);
                onMessage(this.questionDetailBean.getStatData());
                if (this.questionDetailBean.getStatData().getError_correction_number() != 0) {
                    this.tv_correction.setText(String.format(Locale.CHINA, "%d 条纠错", Integer.valueOf(this.questionDetailBean.getStatData().getError_correction_number())));
                } else {
                    this.tv_correction.setText("试题纠错");
                }
                if (this.questionDetailBean.getStatData().getRight_count() + this.questionDetailBean.getStatData().getWrong_count() > 0) {
                    right_count = (this.questionDetailBean.getStatData().getRight_count() * 100) / (this.questionDetailBean.getStatData().getRight_count() + this.questionDetailBean.getStatData().getWrong_count());
                    CommonUtil.getNumber(right_count);
                } else {
                    right_count = 0.0d;
                }
                setStatisticsText();
                if (this.questiondetailsLayoutDiff.getChildCount() > 1) {
                    LinearLayout linearLayout = this.questiondetailsLayoutDiff;
                    linearLayout.removeViews(1, linearLayout.getChildCount() - 1);
                }
                int i3 = right_count > 95.0d ? 1 : right_count > 80.0d ? 2 : right_count > 60.0d ? 3 : right_count > 30.0d ? 4 : 5;
                if (!TextUtils.isEmpty(this.questionDetailBean.getKnowledge_id()) && (question_level = this.questionDetailBean.getQuestion_level()) != null && question_level.matches(RegexPool.NUMBERS)) {
                    i3 = Integer.parseInt(question_level);
                }
                TypedArray typedArrayObtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.ic_rating_star_select, R.attr.ic_rating_star_not_select});
                Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
                Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(1);
                int iDp2px = SizeUtil.dp2px(this.mContext, 16);
                for (int i4 = 0; i4 < 5; i4++) {
                    ImageView imageView = new ImageView(this.mContext);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(iDp2px, iDp2px);
                    layoutParams.rightMargin = ScreenUtil.getPxByDp(this.mContext, 8);
                    imageView.setLayoutParams(layoutParams);
                    if (i4 < i3) {
                        imageView.setImageDrawable(drawable);
                    } else {
                        imageView.setImageDrawable(drawable2);
                    }
                    this.questiondetailsLayoutDiff.addView(imageView);
                }
                typedArrayObtainStyledAttributes.recycle();
                if (this.questionDetailBean.getStatData().getIs_comment() == 0) {
                    noCommingimg();
                } else {
                    havaCommingimg();
                }
                if (this.questionDetailBean.getStatData().getIs_praise() == 0) {
                    noParise();
                } else {
                    haveParise();
                }
                if (this.questionDetailBean.getStatData().getIs_collection() == 0) {
                    noCollectimg();
                } else {
                    havaCollectimg();
                }
                if (this.questionDetailBean.getStatData().getIs_note() == 0) {
                    noNoteimg();
                } else {
                    haveNoteimg();
                }
                addRestoreUserView();
                addAnalyzeUserView();
            } else {
                if (this.questionDetailBean.getStatData().getComment_count() > 10000) {
                    this.tvCommentNum.setText(String.format(Locale.CHINA, "%d.%d万", Integer.valueOf(this.questionDetailBean.getStatData().getComment_count() / 10000), Integer.valueOf((this.questionDetailBean.getStatData().getComment_count() % 10000) / 1000)));
                } else {
                    this.tvCommentNum.setText(String.valueOf(this.questionDetailBean.getStatData().getComment_count()));
                }
                if ("search".equals(this.externalsources)) {
                    stat_info = this.questionDetailBean.getStatData().getStat_info();
                } else {
                    stat_info = this.questionDetailBean.getStatData().getStat_info() + "</p>本人作答<span style='color:#71AC34'>0</span>次，对<span style='color:#71AC34'>0<span>次，正确率<span style='color:#71AC34'>0</span>%";
                    SpannableString spannableString5 = new SpannableString("0次");
                    spannableString5.setSpan(new AbsoluteSizeSpan(14, true), 0, 1, 33);
                    this.tvMyDoTimes.setText(spannableString5);
                    SpannableString spannableString6 = new SpannableString("0%");
                    spannableString6.setSpan(new AbsoluteSizeSpan(14, true), 0, 1, 33);
                    this.tvMyCorrectPercent.setText(spannableString6);
                }
                this.questiondetails_tv_statistics.setText(Html.fromHtml(stat_info));
            }
            if (ConfigUtils.isHidden(2)) {
                this.ll_statistics.setVisibility(8);
            }
            if (TextUtils.isEmpty(this.questionDetailBean.getKnowledge_id()) || "1".equals(SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_statistics_card, this.mContext))) {
                this.llStatisticsContent.setVisibility(8);
            } else {
                this.llStatisticsContent.setVisibility(0);
            }
            if (ConfigUtils.isHidden(1)) {
                this.questiondetailsLayoutDiff.setVisibility(8);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void initSubViewData() {
        if (!TextUtils.isEmpty(this.questionDetailBean.getUser_answer())) {
            this.smartRefreshLayout.setEnableLoadMore(true);
            this.smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(true);
            if (this.videoSummary) {
                this.smartRefreshLayout.setEnableLoadMore(false);
                this.smartRefreshLayout.setEnableRefresh(false);
                this.smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);
            }
            setViewVisiable(0);
            this.tvPushAnswer.setVisibility(8);
            initSelectedAnsed();
        }
        String str = this.answerMode;
        str.hashCode();
        switch (str) {
            case "recite":
                this.hasAnswer = true;
                this.smartRefreshLayout.setEnabled(true);
                setViewVisiable(0);
                this.tvPushAnswer.setVisibility(8);
                break;
            case "test":
            case "quick_brush":
                setViewVisiable(8);
                if (!this.questionDetailBean.getType().equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_DATALINE) && (this.questionDetailBean.getType().equals(com.tencent.connect.common.Constants.VIA_ACT_TYPE_NINETEEN) || this.questionDetailBean.getAnswer().replace(",", "").length() > 1)) {
                    this.tvPushAnswer.setVisibility(0);
                    break;
                } else {
                    this.tvPushAnswer.setVisibility(8);
                    break;
                }
                break;
            default:
                setViewVisiable(8);
                this.tvPushAnswer.setVisibility(0);
                break;
        }
    }

    private void initTitleImg() {
        if (TextUtils.isEmpty(this.questionDetailBean.getTitle_img())) {
            this.flTitleImg.setVisibility(8);
            return;
        }
        if (!this.questionDetailBean.getTitle_img().contains("http")) {
            this.flTitleImg.setVisibility(8);
            return;
        }
        this.flTitleImg.setVisibility(0);
        String str = "<html><head><title></title></head><body><img style='height:auto;max-width:100%' src=" + this.questionDetailBean.getTitle_img() + " /></body></html>";
        this.imgtitle.setBackground(new ColorDrawable(0));
        this.webview.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);
        this.imgtitle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.m0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13281c.lambda$initTitleImg$21(view);
            }
        });
    }

    private void initVideoData() {
        if (this.questionDetailBean.getVideo_list() == null || this.questionDetailBean.getVideo_list().isEmpty()) {
            this.rlQuestionVideo.setVisibility(8);
            this.mLineVideo.setVisibility(8);
            return;
        }
        this.rlQuestionVideo.setVisibility(0);
        this.mLineVideo.setVisibility(8);
        if (!TextUtils.isEmpty(this.questionDetailBean.getKnowledge_id())) {
            this.lineKnowledge.setVisibility(0);
        }
        try {
            int size = this.questionDetailBean.getVideo_list().size();
            this.circlePoint.removeAllViews();
            if (size > 1) {
                this.circlePoint.setCount(size);
                this.circlePoint.initViewData();
                this.circlePoint.invalidate();
            }
            this.mVpVideo.setOrientation(0);
            this.mVpVideo.setAdapter(new QuestionChoiceVideoAdapter(R.layout.adapter_question_video_item, this.questionDetailBean.getVideo_list(), String.valueOf(this.question_id)));
            if (size > 1) {
                this.mVpVideo.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment.7
                    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        SubChoiceQuestionFragment.this.circlePoint.setonPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                });
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void isSelect(boolean renmenTrue, boolean zuixinTrue) {
        if (this.remen.isChecked() != renmenTrue) {
            this.remen.setChecked(renmenTrue);
        }
        if (this.zuixin.isChecked() != zuixinTrue) {
            this.zuixin.setChecked(zuixinTrue);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOrUpdateView$11(int i2, List list, View view) {
        click(i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOrUpdateView$12(int i2, List list, View view) {
        click(i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOrUpdateView$13(int i2, int i3, QuestionDetailBean.KnowledgePointTree knowledgePointTree) {
        if (i2 == i3 - 1) {
            getActivity().finish();
        } else {
            startActivity(new Intent(this.mContext, (Class<?>) ChartAnswerSheetActivity.class).putExtra("knowledge_id", knowledgePointTree.getId()).putExtra("isKnowledge", true).putExtra("type", getArguments().getString("type", "all")).putExtra("title", knowledgePointTree.getName()));
            getActivity().finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOrUpdateView$14(final int i2, final QuestionDetailBean.KnowledgePointTree knowledgePointTree, List list, final int i3, View view) {
        if (i2 == 0) {
            SharePreferencesUtils.writeStrConfig("KNOWLEDGE_CHAPTER_ID", knowledgePointTree.getId(), this.mContext);
            EventBus.getDefault().post("jump2KnowledgeChapter");
            ProjectApp.instance().return2Home();
            return;
        }
        if (i2 == 1) {
            SharePreferencesUtils.writeStrConfig("KNOWLEDGE_CHAPTER_ID", ((QuestionDetailBean.KnowledgePointTree) list.get(0)).getId(), this.mContext);
            SharePreferencesUtils.writeStrConfig("KNOWLEDGE_CHAPTER_ID", knowledgePointTree.getId(), this.mContext);
            EventBus.getDefault().post("jump2KnowledgeChapter");
            ProjectApp.instance().return2Home();
            return;
        }
        if (i2 == 2) {
            Activity currentLastActivity = ProjectApp.instance().getCurrentLastActivity();
            if ((currentLastActivity instanceof RecommendQuestionVideoAct) || (currentLastActivity instanceof ChartAnswerSheetActivity)) {
                EventBus.getDefault().post(CommonParameter.GOTO_KNOWLEDGE_NODE_LIST);
                getActivity().finish();
                return;
            } else {
                startActivity(new Intent(this.mContext, (Class<?>) ChartAnswerSheetActivity.class).putExtra("node_id", knowledgePointTree.getId()).putExtra("isNode", true).putExtra("type", getArguments().getString("type", "all")).putExtra("title", getArguments().getString("subject_title", "")));
                getActivity().finish();
                return;
            }
        }
        if (i2 >= 3) {
            if (TextUtils.isEmpty(knowledgePointTree.getNumber())) {
                new XPopup.Builder(this.mContext).asCustom(new KnowledgePointNoQuestionTipsPop(this.mContext)).show();
                return;
            }
            List<Map<String, String>> top_label = knowledgePointTree.getTop_label();
            ArrayList arrayList = new ArrayList();
            if (top_label == null || top_label.isEmpty()) {
                if (i2 == i3 - 1) {
                    getActivity().finish();
                    return;
                } else {
                    startActivity(new Intent(this.mContext, (Class<?>) ChartAnswerSheetActivity.class).putExtra("knowledge_id", knowledgePointTree.getId()).putExtra("isKnowledge", true).putExtra("type", getArguments().getString("type", "all")).putExtra("title", knowledgePointTree.getName()));
                    getActivity().finish();
                    return;
                }
            }
            for (Map<String, String> map : top_label) {
                ArrayList arrayList2 = new ArrayList();
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    arrayList2.add(it.next().getValue());
                }
                if (arrayList2.size() == 2) {
                    arrayList.add(new Pair((String) arrayList2.get(0), (String) arrayList2.get(1)));
                }
            }
            new XPopup.Builder(this.mContext).asCustom(new PopQuestionDetailFilterShow(this.mContext, arrayList, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.online.fragment.b1
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f13186a.lambda$addOrUpdateView$13(i2, i3, knowledgePointTree);
                }
            })).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$31(String str) {
        Intent intent = new Intent(this.mContext, (Class<?>) NoteNewActivity.class);
        intent.putExtra("question_id", this.questionDetailBean.getId());
        intent.putExtra("notestr", str);
        intent.putExtra("module_type", this.module_type);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRestoreStr$23(ArrayList arrayList, int i2) {
        CommonUtil.doPicture(this.mContext, arrayList, i2, null, R.drawable.kaodianhuanyuan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAd$40(Context context, View view) {
        SharePreferencesUtils.writeLongConfig(CommonParameter.DISMESS_TIME_QUESTION_BOTTOM_AD, Long.valueOf(System.currentTimeMillis()), context);
        this.mLyAdView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initAd$41(HomepageSmallAdBean.DataDTO.DataAd dataAd, View view) {
        PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(dataAd.getAds().get(0).getExtra()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAnsData$25(List list, View view) {
        CommonUtil.doPicture(this.mContext, list, 0, null, R.drawable.kaodianhuanyuan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAnsData$26(List list, View view) {
        CommonUtil.doPicture(this.mContext, list, 0, null, R.drawable.kaodianhuanyuan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$initAudioInfo$36(View view, MotionEvent motionEvent) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAudioInfo$37(float f2) {
        this.mSeekBar.setProgress((int) f2);
        this.tvCurrent.setText(TimeFormater.formatMs(this.mAudioPlayerView.mAliyunVodPlayerView.getmCurrentPosition(), false));
        this.playEnd = f2 >= 100.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAudioInfo$38() {
        this.tvTotal.setText(TimeFormater.formatMs(this.mAudioPlayerView.mAliyunVodPlayerView.getDuration(), false));
        TypedArray typedArrayObtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.ic_question_video_pause});
        this.ivPlayPause.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
        typedArrayObtainStyledAttributes.recycle();
        this.playEnd = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAudioInfo$39() {
        TypedArray typedArrayObtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.ic_question_video_play});
        this.ivPlayPause.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initErrorAnalysis$27(View view) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.questionDetailBean.getError_analysis_img());
        CommonUtil.doPicture(this.mContext, arrayList, 0, null, R.drawable.kaodianhuanyuan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListView$3(boolean z2) {
        updateBottomView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionList$28(BaseQuickAdapter baseQuickAdapter, View view, int i2) throws JSONException {
        if (!CommonUtil.isFastClickNew() && TextUtils.isEmpty(this.questionDetailBean.getUser_answer())) {
            doSelectOption(i2);
            this.qAdapter.notifyDataSetChanged();
            if (this.questionDetailBean.getType().equals(com.tencent.connect.common.Constants.VIA_ACT_TYPE_NINETEEN) || !this.answerMode.equals(Constants.ANSWER_MODE.QUICK_BRUSH_MODE)) {
                return;
            }
            if (this.questionDetailBean.getAnswer().replaceAll(",", "").length() <= 1 || com.tencent.connect.common.Constants.VIA_REPORT_TYPE_DATALINE.equals(this.questionDetailBean.getType())) {
                pushAnswer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$10(View view) {
        lambda$onSuccess$33(this.questionDetailBean.getDataBiao(), view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRestoreData$22(View view) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.questionDetailBean.getRestore_img());
        CommonUtil.doPicture(this.mContext, arrayList, 0, null, R.drawable.kaodianhuanyuan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initStaticData$20() {
        try {
            questionTimeStatistics();
        } catch (Exception e2) {
            Log.d("SubChoiceQuestionFragment", "initStaticData: " + e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTitleImg$21(View view) {
        try {
            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(this.imgtitle, this.questionDetailBean.getTitle_img()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader.popupInfo = new PopupInfo();
            xPopupImageLoader.show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0() {
        this.headerContentHeight = this.headerContentView.getHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        if (this.commlist.size() <= 0) {
            this.viewCommentClickFlag = true;
            this.pageNum = 1;
            this.isLoadCommentData = true;
            getCommentListData();
            return;
        }
        if (this.commlist.size() >= 8) {
            ((AnswerQuestionActivity) getActivity()).hideTitleView("");
        }
        if (this.ishavehot) {
            this.remen.performClick();
        } else {
            this.zuixin.performClick();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initViews$2(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (this.answerMode.equals(Constants.ANSWER_MODE.RECITE_MODE)) {
            if (action == 2) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
            }
            return false;
        }
        if (!TextUtils.isEmpty(this.questionDetailBean.getUser_answer()) && action == 2) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$29(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", str);
        bundle.putInt("result", 1);
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        Context context = this.mContext;
        if (context instanceof AnswerQuestionActivity) {
            String videoId = ((AnswerQuestionActivity) context).getVideoId();
            if (!TextUtils.isEmpty(videoId)) {
                bundle.putString("video_id", videoId);
            }
        }
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            pushComment(bundle);
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$30(QuestionNoteBean questionNoteBean) {
        this.questionDetailBean.getStatData().setIs_note(1);
        haveNoteimg();
        this.questionDetailBean.setNote(questionNoteBean.getContent());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSuccess$34() {
        this.qAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSuccess$35() {
        this.mCommListAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pushAnswer$32() {
        this.qAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scroll2Position$8(int i2) {
        this.mPinnedSecListView.setSelection(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scroll2Position$9() {
        this.zuixin.performClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setFontSize$15() {
        this.qAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListener$4(AdapterView adapterView, View view, int i2, long j2) {
        TextView textView;
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.lineremen);
        if (linearLayout == null || linearLayout.getVisibility() != 0 || (textView = (TextView) linearLayout.findViewById(R.id.group_nametv)) == null) {
            return;
        }
        String string = textView.getText().toString();
        if (TextUtils.isEmpty(string)) {
            return;
        }
        if (string.contains("最热") || string.contains("最新")) {
            Intent intent = new Intent(this.mContext, (Class<?>) QuestionCommentActivity.class);
            intent.putExtra("question_id", String.valueOf(this.question_id));
            intent.putExtra("title", String.valueOf(textView.getText()));
            String str = this.module_type;
            intent.putExtra("module_type", str == null ? 0 : Integer.parseInt(str));
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListener$5() {
        if (!this.ishavehot || this.mPinnedSecListView.getFirstVisiblePosition() <= 0) {
            this.lineselect2.setVisibility(8);
        } else if (this.lineselect2.getVisibility() == 8) {
            this.lineselect2.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListener$6(View view) {
        if (this.isPinned) {
            EventBus.getDefault().post(new HideChapterTitleEvent(true, this.question_id + ""));
        }
        if (this.commlist.size() <= 0) {
            this.isLoadCommentData = true;
            this.pageNum = 1;
            getCommentListData();
            return;
        }
        this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.g1
            @Override // java.lang.Runnable
            public final void run() {
                this.f13230c.lambda$setListener$5();
            }
        }, 100L);
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= this.commlist.size()) {
                break;
            }
            CommentBean.DataBean.HotBean hotBean = this.commlist.get(i3);
            if (hotBean.getType() == 1 && hotBean.isHot() && hotBean.getName().contains("最热")) {
                i2 = i3;
                break;
            }
            i3++;
        }
        scroll2Position(i2 + 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListener$7(View view) {
        if (this.remen.getVisibility() == 8) {
            return;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.commlist.size(); i3++) {
            CommentBean.DataBean.HotBean hotBean = this.commlist.get(i3);
            if (TextUtils.isEmpty(this.commentId)) {
                if (hotBean.getType() == 1 && !hotBean.isHot() && hotBean.getName().contains("最新")) {
                    i2 = i3;
                    break;
                }
            } else {
                if (TextUtils.equals(this.commentId, hotBean.getId())) {
                    this.commentId = null;
                    i2 = i3;
                    break;
                }
            }
        }
        scroll2Position(i2 + 1);
        EventBus.getDefault().post(new HideChapterTitleEvent(true, String.valueOf(this.question_id)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setViewVisiable$24() {
        if (getActivity() == null || getActivity().isDestroyed()) {
            return;
        }
        int statusBarHeight = getActivity().getResources().getDisplayMetrics().heightPixels - StatusBarUtil.getStatusBarHeight(this.mContext);
        if (this.mQuestiondetailsBottomLayout.getVisibility() == 0) {
            statusBarHeight -= this.mQuestiondetailsBottomLayout.getHeight();
        }
        if (this.headerContentView.getHeight() >= statusBarHeight) {
            this.llViewComment.setVisibility(8);
            this.llNoComment.setVisibility(8);
        } else {
            int comment_count = this.questionDetailBean.getStatData().getComment_count();
            this.llViewComment.setVisibility(comment_count <= 0 ? 8 : 0);
            this.llNoComment.setVisibility(comment_count <= 0 ? 0 : 8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showlog$17(List list, int i2, boolean z2) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (list != null && list.size() > 0) {
            for (int i3 = 0; i3 < list.size(); i3++) {
                if (((BiaoqianBeab.DataBean) list.get(i3)).getUser_label().equals("1")) {
                    arrayList.add(((BiaoqianBeab.DataBean) list.get(i3)).getId());
                    arrayList2.add(((BiaoqianBeab.DataBean) list.get(i3)).getLabel());
                }
            }
            if (arrayList.size() > 0) {
                for (int i4 = 0; i4 < arrayList.size(); i4++) {
                    arrayList.size();
                }
            }
            QuestionDataRequest.getIntance(this.mContext).questionSubmitLabel(String.valueOf(this.question_id), ((BiaoqianBeab.DataBean) list.get(i2)).getId(), ((BiaoqianBeab.DataBean) list.get(i2)).getCount() + "", z2, this);
            String str = "[\"" + b0.a("\",\"", new CharSequence[]{this.questionId}) + "\"]";
            String str2 = "[\"" + com.psychiatrygarden.activity.q2.a("\",\"", arrayList2) + "\"]";
            QuestionDetailBean questionDetailBean = this.questionDetailBean;
            questionDetailBean.setModule_type(TextUtils.isEmpty(questionDetailBean.getKnowledge_id()) ? this.module_type : "110");
            QuestionDetailBean questionDetailBean2 = this.questionDetailBean;
            questionDetailBean2.setIs_redo(TextUtils.isEmpty(questionDetailBean2.getIs_redo()) ? "0" : this.questionDetailBean.getIs_redo());
            this.questionDetailBean.setUnit_title(ProjectApp.unit_title);
            this.questionDetailBean.setExam_title(ProjectApp.exam_title);
            this.questionDetailBean.setIdentity_title(ProjectApp.identity_title);
            this.questionDetailBean.setChapter_title(this.chapterTitle);
            this.questionDetailBean.setChapter_parent_title(this.subjectTitle);
            String json = ProjectApp.gson.toJson(this.questionDetailBean);
            AliyunEvent aliyunEvent = AliyunEvent.SelectTag;
            CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, str2, json, "2");
        }
        if (list != null) {
            Collections.sort(list);
            initBiaoQianData(list);
            this.questionDetailBean.setDataBiao(list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateCutIconLayout$16() {
        int width = this.lineviewtype.getVisibility() == 0 ? this.tvPageNum.getWidth() + CommonUtil.dip2px(requireContext(), 15.0f) : CommonUtil.dip2px(requireContext(), 53.0f);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mCutQuestionFlag.getLayoutParams();
        layoutParams.rightMargin = width;
        this.mCutQuestionFlag.setLayoutParams(layoutParams);
    }

    private void loadKnowledgeTree() {
        ArrayList arrayList = new ArrayList();
        QuestionDetailBean.KnowledgePointTree knowledge_path = this.questionDetailBean.getKnowledge_path();
        arrayList.add(knowledge_path);
        if (knowledge_path.getChildren() != null) {
            addKnowledgePointTree(arrayList, knowledge_path.getChildren());
        }
        int i2 = 0;
        while (i2 < arrayList.size()) {
            QuestionDetailBean.KnowledgePointTree knowledgePointTree = arrayList.get(i2);
            if (i2 < 3) {
                knowledgePointTree.setExpand(i2 < 2);
                knowledgePointTree.setVisible(true);
                knowledgePointTree.setShowLine(i2 < 2);
            } else {
                knowledgePointTree.setExpand(false);
                knowledgePointTree.setVisible(false);
                knowledgePointTree.setShowLine(false);
            }
            i2++;
        }
        this.lvKnowledgePointTree.removeAllViews();
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            QuestionDetailBean.KnowledgePointTree knowledgePointTree2 = arrayList.get(i3);
            View viewInflate = View.inflate(this.mContext, R.layout.item_question_knowledge_node, null);
            this.KnowledgeViewMap.put(Integer.valueOf(i3), viewInflate);
            this.lvKnowledgePointTree.addView(viewInflate, new LinearLayout.LayoutParams(-1, -2));
            addOrUpdateView(viewInflate, knowledgePointTree2, i3, arrayList.size(), arrayList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void noCollectimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no);
        } else {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no_night);
        }
    }

    private void noCommingimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.question_msg);
        } else {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.question_msg_night);
        }
    }

    private void noNoteimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit);
        } else {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit_night);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void noParise() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.dianzancourse);
        } else {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.dianzancourse_night);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMessage(QuestionStatDataBean.DataBean event) {
        if (event == null) {
            return;
        }
        if (TextUtils.equals("1", String.valueOf(event.getIs_collection()))) {
            havaCollectimg();
        } else {
            noCollectimg();
        }
        if (TextUtils.equals("1", String.valueOf(event.getIs_comment()))) {
            havaCommingimg();
        } else {
            noCommingimg();
        }
        int comment_count = event.getComment_count();
        this.tvCommentNum.setVisibility(0);
        if (comment_count > 10000) {
            this.tvCommentNum.setText(String.format(Locale.CHINA, "%d.%d万", Integer.valueOf(comment_count / 10000), Integer.valueOf((comment_count % 10000) / 1000)));
        } else {
            this.tvCommentNum.setText(String.valueOf(comment_count));
        }
        String strValueOf = String.valueOf(event.getIs_collection());
        if (TextUtils.isEmpty(strValueOf) || "0".equals(strValueOf)) {
            noCollectimg();
        } else if ("1".equals(strValueOf)) {
            havaCollectimg();
        }
        String strValueOf2 = String.valueOf(event.getIs_praise());
        if (TextUtils.isEmpty(strValueOf2) || "0".equals(strValueOf2)) {
            noParise();
        } else if ("1".equals(strValueOf2)) {
            haveParise();
        }
        int update_time = event.getUpdate_time();
        if (update_time <= 0) {
            this.tvEditTime.setVisibility(8);
            return;
        }
        this.tvEditTime.setVisibility(0);
        this.tvEditTime.setText(String.format("内容持续优化，最近更新时间：%s", new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date(update_time * 1000))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playAudio() {
        if (this.positionList.contains(Integer.valueOf(this.mCurrentPosition)) && this.currentPageIsVisible) {
            if (this.questionDetailBean.getStem_audio_list() == null || this.questionDetailBean.getStem_audio_list().size() <= 0) {
                this.llAudio.setVisibility(8);
                return;
            }
            this.llAudio.setVisibility(0);
            CustomAliPlayerView customAliPlayerView = this.mAudioPlayerView;
            if (customAliPlayerView == null) {
                getAudioInfo();
                return;
            }
            AliyunVodPlayerView aliyunVodPlayerView = customAliPlayerView.mAliyunVodPlayerView;
            if (aliyunVodPlayerView == null) {
                this.mAudioPlayerView = null;
                getAudioInfo();
            } else if (!aliyunVodPlayerView.isPlaying()) {
                this.mAudioPlayerView.mAliyunVodPlayerView.rePlay();
            } else {
                this.mAudioPlayerView.mAliyunVodPlayerView.onStop();
                this.mAudioPlayerView.onStopped();
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:32:0x009b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void pushAnswer() throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 586
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment.pushAnswer():void");
    }

    private void pushComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("obj_id", String.valueOf(this.question_id));
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("module_type", "1");
        ajaxParams.put("comment_type", "2");
        String string = b3.getString("video_id");
        if (!TextUtils.isEmpty(string) && !"0".equals(string)) {
            ajaxParams.put("video_id", string);
        }
        final String string2 = b3.getString("parent_id");
        if (!TextUtils.isEmpty(string2) && !"0".equals(string2)) {
            ajaxParams.put("parent_id", string2);
        }
        String string3 = b3.getString("to_user_id");
        if (!TextUtils.isEmpty(string3) && !"0".equals(string3)) {
            ajaxParams.put("to_user_id", string3);
        }
        String string4 = b3.getString("reply_primary_id");
        if (!TextUtils.isEmpty(string4) && !"0".equals(string4)) {
            ajaxParams.put("reply_primary_id", string4);
        }
        if (!TextUtils.isEmpty(string2) && !"0".equals(string2) && !TextUtils.isEmpty(string4) && !"0".equals(string4) && !TextUtils.isEmpty(string3)) {
            "0".equals(string3);
        }
        String string5 = b3.getString("b_img");
        String string6 = b3.getString("s_img");
        this.logImg = "";
        if (!TextUtils.isEmpty(string5)) {
            if (string5.contains("http")) {
                ajaxParams.put("b_img", string5);
                ajaxParams.put("s_img", string6);
                this.logImg = "," + string6;
            } else {
                ajaxParams.put("video_id", b3.getString("b_img"));
                this.logImg = "," + b3.getString("b_img");
            }
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            ajaxParams.put("virtual_user_id", b3.getString("virtual_user_id"));
        }
        showProgressDialog("发布中");
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SubChoiceQuestionFragment.this.hideProgressDialog();
                SubChoiceQuestionFragment.this.AlertToast("请求服务器失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws JSONException {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SubChoiceQuestionFragment.this.llNoComment.setVisibility(8);
                        SubChoiceQuestionFragment.this.llViewComment.setVisibility(8);
                        ToastUtil.shortToast(SubChoiceQuestionFragment.this.getActivity(), jSONObject.optString("message"));
                        CommentBean.DataBean.HotBean hotBean = (CommentBean.DataBean.HotBean) new Gson().fromJson(jSONObject.getJSONObject("data").toString(), CommentBean.DataBean.HotBean.class);
                        SubChoiceQuestionFragment.this.questionDetailBean.getStatData().setIs_comment(1);
                        SubChoiceQuestionFragment.this.havaCommingimg();
                        SubChoiceQuestionFragment.this.commentId = hotBean.getId();
                        SubChoiceQuestionFragment.this.commentContent = hotBean.getContent();
                        String str = "[\"" + SubChoiceQuestionFragment.this.commentId + "\"]";
                        String str2 = "[\"" + SubChoiceQuestionFragment.this.commentContent + SubChoiceQuestionFragment.this.logImg + "\"]";
                        SubChoiceQuestionFragment.this.questionDetailBean.setModule_type(TextUtils.isEmpty(SubChoiceQuestionFragment.this.questionDetailBean.getKnowledge_id()) ? SubChoiceQuestionFragment.this.module_type : "110");
                        SubChoiceQuestionFragment.this.questionDetailBean.setIs_redo(TextUtils.isEmpty(SubChoiceQuestionFragment.this.questionDetailBean.getIs_redo()) ? "0" : SubChoiceQuestionFragment.this.questionDetailBean.getIs_redo());
                        SubChoiceQuestionFragment.this.questionDetailBean.setUnit_title(ProjectApp.unit_title);
                        SubChoiceQuestionFragment.this.questionDetailBean.setUnit_id(ProjectApp.unit_id);
                        SubChoiceQuestionFragment.this.questionDetailBean.setExam_title(ProjectApp.exam_title);
                        SubChoiceQuestionFragment.this.questionDetailBean.setIdentity_title(ProjectApp.identity_title);
                        SubChoiceQuestionFragment.this.questionDetailBean.setChapter_title(SubChoiceQuestionFragment.this.chapterTitle);
                        SubChoiceQuestionFragment.this.questionDetailBean.setChapter_parent_title(SubChoiceQuestionFragment.this.subjectTitle);
                        String json = new Gson().toJson(SubChoiceQuestionFragment.this.questionDetailBean);
                        AliyunEvent aliyunEvent = AliyunEvent.PublishComment;
                        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, str2, json, "2");
                        if (SubChoiceQuestionFragment.this.mCommListAdapter.getList().size() > 0) {
                            if (TextUtils.isEmpty(string2)) {
                                SubChoiceQuestionFragment.this.mCommListAdapter.getList().add(SubChoiceQuestionFragment.this.getNewPositions(), hotBean);
                            } else {
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= SubChoiceQuestionFragment.this.mCommListAdapter.getList().size()) {
                                        i2 = -1;
                                        break;
                                    }
                                    CommentBean.DataBean.HotBean hotBean2 = SubChoiceQuestionFragment.this.mCommListAdapter.getList().get(i2);
                                    if (hotBean2.getType() != 1 && !hotBean2.isHot() && TextUtils.equals(string2, hotBean2.getId())) {
                                        break;
                                    } else {
                                        i2++;
                                    }
                                }
                                if (i2 > -1) {
                                    SubChoiceQuestionFragment.this.mCommListAdapter.getList().set(i2, hotBean);
                                }
                            }
                            SubChoiceQuestionFragment.this.mCommListAdapter.notifyDataSetChanged();
                            SubChoiceQuestionFragment.this.hasMockClick = false;
                            if (TextUtils.isEmpty(string2)) {
                                SubChoiceQuestionFragment.this.zuixin.performClick();
                            }
                        } else {
                            SubChoiceQuestionFragment.this.notInitCommentFlag = true;
                            SubChoiceQuestionFragment.this.pageNum = 1;
                            SubChoiceQuestionFragment.this.getCommentListData();
                        }
                        ProjectApp.comment_content = "";
                        ProjectApp.comment_b_img = "";
                        ProjectApp.comment_s_img = "";
                        ProjectApp.commentvideoPath = "";
                        ProjectApp.commentvideoImage = "";
                        ProjectApp.commentvideoId = "";
                        ProjectApp.commentvideoImagePath = "";
                        if (SubChoiceQuestionFragment.this.questionDetailBean.getStatData() != null) {
                            SubChoiceQuestionFragment.this.questionDetailBean.getStatData().setComment_count(SubChoiceQuestionFragment.this.questionDetailBean.getStatData().getComment_count() + 1);
                            int comment_count = SubChoiceQuestionFragment.this.questionDetailBean.getStatData().getComment_count();
                            SubChoiceQuestionFragment.this.tvCommentNum.setVisibility(0);
                            if (comment_count > 10000) {
                                SubChoiceQuestionFragment.this.tvCommentNum.setText(String.format(Locale.CHINA, "%d.%d万", Integer.valueOf(comment_count / 10000), Integer.valueOf((comment_count % 10000) / 1000)));
                            } else {
                                SubChoiceQuestionFragment.this.tvCommentNum.setText(String.valueOf(comment_count));
                            }
                        }
                        if (jSONObject.has("is_collection")) {
                            String string7 = jSONObject.getString("is_collection");
                            if (TextUtils.isEmpty(string7)) {
                                SubChoiceQuestionFragment.this.noCollectimg();
                            } else if ("1".equals(string7)) {
                                SubChoiceQuestionFragment.this.havaCollectimg();
                            }
                        }
                        if (jSONObject.has("is_praise")) {
                            String string8 = jSONObject.getString("is_praise");
                            if (TextUtils.isEmpty(string8)) {
                                SubChoiceQuestionFragment.this.noParise();
                            } else if ("1".equals(string8)) {
                                SubChoiceQuestionFragment.this.haveParise();
                            }
                        }
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        CommonUtil.showFristDialog(jSONObject);
                    } else if (jSONObject.optString("code").equals("401")) {
                        new CusomNewDialog(((BaseFragment) SubChoiceQuestionFragment.this).mContext).setMessage(jSONObject.optString("message")).show();
                    } else {
                        SubChoiceQuestionFragment.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    SubChoiceQuestionFragment.this.AlertToast("数据异常！");
                    e2.printStackTrace();
                }
                SubChoiceQuestionFragment.this.hideProgressDialog();
            }
        });
    }

    private void questionTimeStatistics() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.questionDetailBean.getId());
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.questionTimeStatistics, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment.13
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws NumberFormatException {
                super.onSuccess((AnonymousClass13) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (TextUtils.equals("200", jSONObject.optString("code"))) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        String strOptString = jSONObjectOptJSONObject.optString("time_used");
                        String str = "0";
                        if (TextUtils.isEmpty(strOptString)) {
                            strOptString = "0";
                        }
                        String strOptString2 = jSONObjectOptJSONObject.optString("ave_time");
                        if (!TextUtils.isEmpty(strOptString2)) {
                            str = strOptString2;
                        }
                        if (Integer.parseInt(strOptString) > SubChoiceQuestionFragment.this.answerUseTime) {
                            SubChoiceQuestionFragment.this.tvUseTime.setText(strOptString + "s");
                        } else {
                            SubChoiceQuestionFragment.this.tvUseTime.setText(SubChoiceQuestionFragment.this.answerUseTime + "s");
                        }
                        String string = "0s";
                        if (str.matches(RegexPool.NUMBERS)) {
                            int i2 = Integer.parseInt(str);
                            int i3 = i2 / 3600;
                            int i4 = (i2 % 3600) / 60;
                            int i5 = (i2 % 3600) % 60;
                            StringBuilder sb = new StringBuilder();
                            if (i3 > 0) {
                                sb.append(i3);
                                sb.append("h");
                            }
                            if (i4 > 0) {
                                sb.append(i4);
                                sb.append("m");
                            }
                            if (i5 > 0) {
                                sb.append(i5);
                                sb.append("s");
                            }
                            TextView textView = SubChoiceQuestionFragment.this.tvAllUseTime;
                            if (i2 != 0) {
                                string = sb.toString();
                            }
                            textView.setText(string);
                        } else {
                            SubChoiceQuestionFragment.this.tvAllUseTime.setText("0s");
                        }
                        if (SubChoiceQuestionFragment.this.questionDetailBean.getStatData() != null) {
                            SubChoiceQuestionFragment.this.questionDetailBean.getStatData().setAllDoUseTime(str);
                            SubChoiceQuestionFragment.this.questionDetailBean.getStatData().setDoUseTime(strOptString);
                            SubChoiceQuestionFragment.this.setStatisticsText();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void scroll2Position(final int pos) {
        if (TextUtils.isEmpty(this.questionDetailBean.getPublic_number())) {
            this.mPinnedSecListView.smoothScrollToPositionFromTop(pos, 0);
        }
        this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.h1
            @Override // java.lang.Runnable
            public final void run() {
                this.f13238c.lambda$scroll2Position$8(pos);
            }
        }, 310L);
        if (!this.ishavehot || this.hasMockClick) {
            return;
        }
        this.hasMockClick = true;
        if (TextUtils.isEmpty(this.questionDetailBean.getPublic_number())) {
            this.zuixin.performClick();
        } else if (this.shareStem) {
            EventBus.getDefault().post(new ScrollDirectionEvent(false, false, String.valueOf(this.question_id)));
            EventBus.getDefault().post(new UpdateScrollEvent(200));
            this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.j1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13258c.lambda$scroll2Position$9();
                }
            }, 100L);
        }
    }

    private void scrollLayout() {
        this.mPinnedSecListView.smoothScrollToPositionFromTop(0, 0);
    }

    private void setFontSize() {
        int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, this.mContext, 2);
        int pxBySp = ScreenUtil.getPxBySp(this.mContext, 2);
        TextView textView = this.typeStr;
        float f2 = (intConfig - 2) * pxBySp;
        textView.setTextSize(0, ((Float) textView.getTag()).floatValue() - f2);
        TextView textView2 = this.tvPageNum;
        textView2.setTextSize(0, ((Float) textView2.getTag()).floatValue() - f2);
        TextView textView3 = this.tvTitle;
        textView3.setTextSize(0, ((Float) textView3.getTag()).floatValue() - f2);
        TextView textView4 = this.tvCommentNum;
        textView4.setTextSize(0, ((Float) textView4.getTag()).floatValue() - f2);
        TextView textView5 = this.textViewDifficulty;
        textView5.setTextSize(0, ((Float) textView5.getTag()).floatValue() - f2);
        TextView textView6 = this.questiondetails_tv_Answer;
        textView6.setTextSize(0, ((Float) textView6.getTag()).floatValue() - f2);
        TextView textView7 = this.questiondetails_tv_statistics;
        textView7.setTextSize(0, ((Float) textView7.getTag()).floatValue() - f2);
        TextView textView8 = this.tvStatistics;
        textView8.setTextSize(0, ((Float) textView8.getTag()).floatValue() - f2);
        TextView textView9 = this.biaotxt;
        textView9.setTextSize(0, ((Float) textView9.getTag()).floatValue() - f2);
        TextView textView10 = this.questiondetails_tv_outline;
        textView10.setTextSize(0, ((Float) textView10.getTag()).floatValue() - f2);
        TextView textView11 = this.sourcetv;
        textView11.setTextSize(0, ((Float) textView11.getTag()).floatValue() - f2);
        TextView textView12 = this.tv_correction;
        textView12.setTextSize(0, ((Float) textView12.getTag()).floatValue() - f2);
        TextView textView13 = this.questiondetails_tv_content_ques1;
        textView13.setTextSize(0, ((Float) textView13.getTag()).floatValue() - f2);
        TextView textView14 = this.tvAnswerAnalyze;
        textView14.setTextSize(0, ((Float) textView14.getTag()).floatValue() - f2);
        TextView textView15 = this.tvAnswerAnalysis;
        textView15.setTextSize(0, ((Float) textView15.getTag()).floatValue() - f2);
        TextView textView16 = this.mTvEncyclopediaAnalyze;
        textView16.setTextSize(0, ((Float) textView16.getTag()).floatValue() - f2);
        if (this.questionDetailBean.getDataBiao() != null) {
            initBiaoQianData(this.questionDetailBean.getDataBiao());
        }
        this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.o1
            @Override // java.lang.Runnable
            public final void run() {
                this.f13299c.lambda$setFontSize$15();
            }
        });
    }

    private void setListener() {
        this.mPinnedSecListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.i0
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f13247c.lambda$setListener$4(adapterView, view, i2, j2);
            }
        });
        this.remen.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.j0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13257c.lambda$setListener$6(view);
            }
        });
        this.zuixin.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.k0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13265c.lambda$setListener$7(view);
            }
        });
        if (this.videoSummary) {
            return;
        }
        this.mPinnedSecListView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment.3
            private int oldFirstVisibleItem;
            private int oldTop;

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!SubChoiceQuestionFragment.this.firstInitScroll) {
                    SubChoiceQuestionFragment.this.firstInitScroll = true;
                    return;
                }
                if (SubChoiceQuestionFragment.this.hasAnswer) {
                    if (SubChoiceQuestionFragment.this.positionTab > 0) {
                        if (firstVisibleItem > SubChoiceQuestionFragment.this.positionTab) {
                            SubChoiceQuestionFragment.this.isSelect(false, true);
                        } else {
                            SubChoiceQuestionFragment.this.isSelect(true, false);
                        }
                    } else if (firstVisibleItem > SubChoiceQuestionFragment.this.positionTab + 1) {
                        SubChoiceQuestionFragment.this.isSelect(false, true);
                    } else {
                        SubChoiceQuestionFragment.this.isSelect(true, false);
                    }
                    SubChoiceQuestionFragment.this.mCurrentFirstVisibleItem = firstVisibleItem;
                    View childAt = view.getChildAt(0);
                    if (childAt != null) {
                        if ((childAt instanceof ScrollView) && SubChoiceQuestionFragment.this.ishavehot && SubChoiceQuestionFragment.this.lineselect2.getVisibility() != 8) {
                            SubChoiceQuestionFragment.this.lineselect2.setVisibility(8);
                        }
                        ItemRecord itemRecord = (ItemRecord) SubChoiceQuestionFragment.this.recordSp.get(firstVisibleItem);
                        if (itemRecord == null) {
                            itemRecord = new ItemRecord();
                        }
                        itemRecord.height = childAt.getHeight();
                        itemRecord.f13169top = childAt.getTop();
                        SubChoiceQuestionFragment.this.recordSp.append(firstVisibleItem, itemRecord);
                        int scrollY = SubChoiceQuestionFragment.this.getScrollY();
                        if (SubChoiceQuestionFragment.this.getActivity() != null && (SubChoiceQuestionFragment.this.getActivity() instanceof AnswerQuestionActivity) && TextUtils.isEmpty(SubChoiceQuestionFragment.this.questionDetailBean.getPublic_number())) {
                            ((AnswerQuestionActivity) SubChoiceQuestionFragment.this.getActivity()).onScrollChange(scrollY, String.valueOf(SubChoiceQuestionFragment.this.question_id), SubChoiceQuestionFragment.this.headerContentHeight);
                        }
                    }
                    int top2 = childAt == null ? 0 : childAt.getTop();
                    int i2 = this.oldFirstVisibleItem;
                    if (firstVisibleItem == i2) {
                        int i3 = this.oldTop;
                        if (top2 > i3) {
                            if (SubChoiceQuestionFragment.this.getActivity() != null && (SubChoiceQuestionFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                                ((AnswerQuestionActivity) SubChoiceQuestionFragment.this.getActivity()).showHideBack2TopIcon(true);
                                if (!TextUtils.isEmpty(SubChoiceQuestionFragment.this.questionDetailBean.getPublic_number()) && ((!TextUtils.isEmpty(SubChoiceQuestionFragment.this.questionDetailBean.getUser_answer()) || TextUtils.equals(Constants.ANSWER_MODE.RECITE_MODE, SubChoiceQuestionFragment.this.answerMode)) && SubChoiceQuestionFragment.this.shareStem)) {
                                    EventBus.getDefault().post(new ScrollChangeEvent(String.valueOf(SubChoiceQuestionFragment.this.question_id), true));
                                }
                            }
                        } else if (top2 < i3 && (SubChoiceQuestionFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                            ((AnswerQuestionActivity) SubChoiceQuestionFragment.this.getActivity()).showHideBack2TopIcon(false);
                            if (!TextUtils.isEmpty(SubChoiceQuestionFragment.this.questionDetailBean.getPublic_number()) && ((!TextUtils.isEmpty(SubChoiceQuestionFragment.this.questionDetailBean.getUser_answer()) || TextUtils.equals(Constants.ANSWER_MODE.RECITE_MODE, SubChoiceQuestionFragment.this.answerMode)) && SubChoiceQuestionFragment.this.shareStem)) {
                                EventBus.getDefault().post(new ScrollChangeEvent(String.valueOf(SubChoiceQuestionFragment.this.question_id), false));
                            }
                        }
                    } else if (firstVisibleItem < i2) {
                        if (SubChoiceQuestionFragment.this.getActivity() != null && (SubChoiceQuestionFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                            ((AnswerQuestionActivity) SubChoiceQuestionFragment.this.getActivity()).showHideBack2TopIcon(true);
                        }
                        if (!TextUtils.isEmpty(SubChoiceQuestionFragment.this.questionDetailBean.getPublic_number()) && ((!TextUtils.isEmpty(SubChoiceQuestionFragment.this.questionDetailBean.getUser_answer()) || TextUtils.equals(Constants.ANSWER_MODE.RECITE_MODE, SubChoiceQuestionFragment.this.answerMode)) && SubChoiceQuestionFragment.this.shareStem)) {
                            EventBus.getDefault().post(new ScrollChangeEvent(String.valueOf(SubChoiceQuestionFragment.this.question_id), true));
                        }
                    } else {
                        if (SubChoiceQuestionFragment.this.getActivity() != null && (SubChoiceQuestionFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                            ((AnswerQuestionActivity) SubChoiceQuestionFragment.this.getActivity()).showHideBack2TopIcon(false);
                        }
                        if (!TextUtils.isEmpty(SubChoiceQuestionFragment.this.questionDetailBean.getPublic_number()) && ((!TextUtils.isEmpty(SubChoiceQuestionFragment.this.questionDetailBean.getUser_answer()) || TextUtils.equals(Constants.ANSWER_MODE.RECITE_MODE, SubChoiceQuestionFragment.this.answerMode)) && SubChoiceQuestionFragment.this.shareStem)) {
                            EventBus.getDefault().post(new ScrollChangeEvent(String.valueOf(SubChoiceQuestionFragment.this.question_id), false));
                        }
                    }
                    this.oldTop = top2;
                    this.oldFirstVisibleItem = firstVisibleItem;
                }
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (SubChoiceQuestionFragment.this.hasAnswer && scrollState == 0) {
                    if (SubChoiceQuestionFragment.this.getActivity() != null && (SubChoiceQuestionFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                        ((AnswerQuestionActivity) SubChoiceQuestionFragment.this.getActivity()).showHideBack2TopIcon(this.oldTop != 0);
                    }
                    if (TextUtils.isEmpty(SubChoiceQuestionFragment.this.questionDetailBean.getPublic_number())) {
                        return;
                    }
                    if ((!TextUtils.isEmpty(SubChoiceQuestionFragment.this.questionDetailBean.getUser_answer()) || TextUtils.equals(Constants.ANSWER_MODE.RECITE_MODE, SubChoiceQuestionFragment.this.answerMode)) && SubChoiceQuestionFragment.this.shareStem) {
                        if (SubChoiceQuestionFragment.this.back2Top) {
                            SubChoiceQuestionFragment.this.back2Top = false;
                            return;
                        }
                        if (!SubChoiceQuestionFragment.this.isRefreshFlag) {
                            EventBus.getDefault().post(new UpdateScrollEvent(SubChoiceQuestionFragment.this.getScrollY(), String.valueOf(SubChoiceQuestionFragment.this.question_id)));
                        }
                        if (SubChoiceQuestionFragment.this.isRefreshFlag) {
                            SubChoiceQuestionFragment.this.isRefreshFlag = false;
                        }
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStatisticsText() throws NumberFormatException {
        if (this.questionDetailBean.getStatData().getAnswer().getRight_count() + this.questionDetailBean.getStatData().getAnswer().getWrong_count() <= 0) {
            String stat_info = this.questionDetailBean.getStatData().getStat_info();
            String doUseTime = this.questionDetailBean.getStatData().getDoUseTime();
            String allDoUseTime = this.questionDetailBean.getStatData().getAllDoUseTime();
            if (!"search".equals(this.externalsources)) {
                if (!TextUtils.isEmpty(allDoUseTime) && allDoUseTime.matches(RegexPool.NUMBERS)) {
                    int i2 = Integer.parseInt(allDoUseTime);
                    if (i2 < 60) {
                        stat_info = stat_info + "平均作答用时" + i2 + "s。\n";
                    } else {
                        stat_info = stat_info + formatUseTime("平均作答用时", i2) + "。\n";
                    }
                }
                stat_info = stat_info + "本人作答{0}次，对{0}次，正确率{0}%";
                SpannableString spannableString = new SpannableString("0次");
                spannableString.setSpan(new AbsoluteSizeSpan(14, true), 0, 1, 33);
                this.tvMyDoTimes.setText(spannableString);
                SpannableString spannableString2 = new SpannableString("0%");
                spannableString2.setSpan(new AbsoluteSizeSpan(14, true), 0, 1, 33);
                this.tvMyCorrectPercent.setText(spannableString2);
            }
            if (!TextUtils.isEmpty(doUseTime) && doUseTime.matches(RegexPool.NUMBERS)) {
                int i3 = Integer.parseInt(doUseTime);
                if (i3 < 60) {
                    stat_info = stat_info + "，本题作答用时" + i3 + "s。";
                } else {
                    stat_info = stat_info + formatUseTime("，本题作答用时", i3) + "。";
                }
            }
            this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(this.mContext) == 0 ? ColorPhrase.from(stat_info).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(stat_info).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
            return;
        }
        String stat_info2 = this.questionDetailBean.getStatData().getStat_info();
        if (!"search".equals(this.externalsources)) {
            String doUseTime2 = this.questionDetailBean.getStatData().getDoUseTime();
            String allDoUseTime2 = this.questionDetailBean.getStatData().getAllDoUseTime();
            int right_count = this.questionDetailBean.getStatData().getAnswer().getRight_count() + this.questionDetailBean.getStatData().getAnswer().getWrong_count();
            if (!TextUtils.isEmpty(allDoUseTime2) && allDoUseTime2.matches(RegexPool.NUMBERS)) {
                int i4 = Integer.parseInt(allDoUseTime2);
                if (i4 < 60) {
                    stat_info2 = stat_info2 + "平均作答用时" + i4 + "s。\n";
                } else {
                    stat_info2 = stat_info2 + formatUseTime("平均作答用时", i4) + "。\n";
                }
            }
            String number = CommonUtil.getNumber((this.questionDetailBean.getStatData().getAnswer().getRight_count() * 100) / (this.questionDetailBean.getStatData().getAnswer().getRight_count() + this.questionDetailBean.getStatData().getAnswer().getWrong_count()));
            stat_info2 = stat_info2 + "本人作答{" + right_count + "}次，对{" + this.questionDetailBean.getStatData().getAnswer().getRight_count() + "}次，正确率{" + number + "%}";
            if (!TextUtils.isEmpty(doUseTime2) && doUseTime2.matches(RegexPool.NUMBERS) && allDoUseTime2.matches(RegexPool.NUMBERS)) {
                int i5 = Integer.parseInt(doUseTime2);
                if (i5 < 60) {
                    stat_info2 = stat_info2 + "，本题作答用时" + i5 + "s。";
                } else {
                    stat_info2 = stat_info2 + formatUseTime("，本题作答用时", i5) + "。";
                }
            }
            String str = right_count + "次";
            int iIndexOf = str.indexOf("次");
            SpannableString spannableString3 = new SpannableString(str);
            spannableString3.setSpan(new AbsoluteSizeSpan(14, true), 0, iIndexOf, 33);
            this.tvMyDoTimes.setText(spannableString3);
            String str2 = number + "%";
            int iIndexOf2 = str2.indexOf("%");
            SpannableString spannableString4 = new SpannableString(str2);
            spannableString4.setSpan(new AbsoluteSizeSpan(14, true), 0, iIndexOf2, 33);
            this.tvMyCorrectPercent.setText(spannableString4);
        }
        if ((this.questionDetailBean.getStatData().getRight_count() * 100) / (this.questionDetailBean.getStatData().getRight_count() + this.questionDetailBean.getStatData().getWrong_count()) > (this.questionDetailBean.getStatData().getAnswer().getRight_count() * 100) / (this.questionDetailBean.getStatData().getAnswer().getRight_count() + this.questionDetailBean.getStatData().getAnswer().getWrong_count())) {
            this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(this.mContext) == 0 ? ColorPhrase.from(stat_info2).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(stat_info2).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
        } else {
            this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(this.mContext) == 0 ? ColorPhrase.from(stat_info2).withSeparator(StrPool.EMPTY_JSON).innerColor(-10572282).outerColor(-16777216).format() : ColorPhrase.from(stat_info2).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
        }
    }

    private void setViewVisiable(int view) {
        if (view == 0 && this.isFragmentVisible) {
            this.start_timestamp = System.currentTimeMillis() + "";
        }
        this.isAnswerVisible = view == 0;
        this.lineout.setVisibility(view);
        this.qbrel.setVisibility(view);
        this.questiondetailsLayoutDiff.setVisibility(view);
        if (ConfigUtils.isHidden(1)) {
            this.questiondetailsLayoutDiff.setVisibility(8);
        }
        if (view == 0) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.qlistview.getLayoutParams();
            layoutParams.bottomMargin = 0;
            this.qlistview.setLayoutParams(layoutParams);
            this.headerContentView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.a1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13174c.lambda$setViewVisiable$24();
                }
            }, 500L);
        }
        if (ConfigUtils.isHidden(5)) {
            this.ll_restore_point.setVisibility(8);
        }
        if (ConfigUtils.isHidden(7)) {
            this.ll_encyclopedia_analysis_layout.setVisibility(8);
        }
        if (ConfigUtils.isHidden(6)) {
            this.ll_answer_analysis_layout.setVisibility(8);
        }
        if (this.videoSummary) {
            int childCount = this.mQuestiondetailsBottomLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = this.mQuestiondetailsBottomLayout.getChildAt(i2);
                childAt.setVisibility(childAt.getId() == R.id.questiondetails_btn_pushAnswer ? 0 : 8);
            }
        }
    }

    private void testMatch(String content) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        Matcher matcher = Pattern.compile("（([A-Z]+[对|错]([^）]+)?)）").matcher(content);
        while (matcher.find()) {
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, this.tvAnswerAnalysis.getTextColors(), null), matcher.start(0), matcher.end(0), 34);
        }
        this.tvAnswerAnalysis.setText(spannableStringBuilder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBottomView() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.questionDetailBean.getId());
        ajaxParams.put("module_type", this.module_type + "");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getstatApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment.11
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                QuestionStatDataBean questionStatDataBean;
                super.onSuccess((AnonymousClass11) s2);
                try {
                    if (!new JSONObject(s2).optString("code", "").equals("200") || (questionStatDataBean = (QuestionStatDataBean) new Gson().fromJson(s2, QuestionStatDataBean.class)) == null) {
                        return;
                    }
                    SubChoiceQuestionFragment.this.tv_correction.setText(String.format(Locale.CHINA, "%d 条纠错", Integer.valueOf(questionStatDataBean.getData().getError_correction_number())));
                    SubChoiceQuestionFragment.this.questionDetailBean.getStatData().setComment_count(questionStatDataBean.getData().getComment_count());
                    SubChoiceQuestionFragment.this.questionDetailBean.getStatData().setIs_comment(questionStatDataBean.getData().getIs_comment());
                    SubChoiceQuestionFragment.this.questionDetailBean.getStatData().setIs_collection(questionStatDataBean.getData().getIs_collection());
                    SubChoiceQuestionFragment.this.questionDetailBean.getStatData().setIs_praise(questionStatDataBean.getData().getIs_praise());
                    SubChoiceQuestionFragment.this.onMessage(questionStatDataBean.getData());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void updateCutIcon() {
        if (!this.videoSummary && TextUtils.equals("1", this.questionDetailBean.getIs_cut()) && this.mCutQuestionFlag.getVisibility() == 0) {
            this.mCutQuestionFlag.setImageResource(SkinManager.getCurrentSkinType(this.mContext) == 0 ? R.drawable.ic_cut_day : R.drawable.ic_cut_night);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mCutQuestionFlag.getLayoutParams();
            if (this.headerContentView.findViewById(R.id.rl_top_title).getVisibility() == 0) {
                if ("1".equals(this.questionDetailBean.getIs_new())) {
                    layoutParams.topMargin = CommonUtil.dip2px(requireContext(), 55.0f);
                } else if (this.lineviewtype.getVisibility() == 0) {
                    layoutParams.topMargin = CommonUtil.dip2px(requireContext(), 40.0f);
                } else {
                    layoutParams.topMargin = CommonUtil.dip2px(requireContext(), 35.0f);
                }
            } else if (this.lineviewtype.getVisibility() == 0) {
                layoutParams.topMargin = CommonUtil.dip2px(requireContext(), 30.0f);
            } else {
                layoutParams.topMargin = (TextUtils.isEmpty(this.questionDetailBean.getPublic_number()) || !"1".equals(this.questionDetailBean.getIs_new())) ? 0 : CommonUtil.dip2px(this.mContext, 20.0f);
            }
            this.mCutQuestionFlag.setLayoutParams(layoutParams);
        }
    }

    private void updateCutIconLayout() {
        this.tvPageNum.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.p0
            @Override // java.lang.Runnable
            public final void run() {
                this.f13306c.lambda$updateCutIconLayout$16();
            }
        });
    }

    private void updateLineStates(int startPos, List<QuestionDetailBean.KnowledgePointTree> items) {
        boolean z2;
        int i2 = startPos;
        while (true) {
            z2 = false;
            if (i2 >= items.size()) {
                break;
            }
            QuestionDetailBean.KnowledgePointTree knowledgePointTree = items.get(i2);
            if (i2 == items.size() - 1) {
                knowledgePointTree.setShowLine(false);
            } else {
                QuestionDetailBean.KnowledgePointTree knowledgePointTree2 = items.get(i2 + 1);
                if (knowledgePointTree.isExpand() && knowledgePointTree2.isVisible()) {
                    z2 = true;
                }
                knowledgePointTree.setShowLine(z2);
            }
            i2++;
        }
        if (startPos > 0) {
            QuestionDetailBean.KnowledgePointTree knowledgePointTree3 = items.get(startPos - 1);
            QuestionDetailBean.KnowledgePointTree knowledgePointTree4 = items.get(startPos);
            if (knowledgePointTree3.isExpand() && knowledgePointTree4.isVisible()) {
                z2 = true;
            }
            knowledgePointTree3.setShowLine(z2);
        }
    }

    private void updatePraise(CommentBean.DataBean.HotBean hotBean, boolean praise) {
        if (praise != "1".equals(hotBean.getIs_praise())) {
            String praise_num = hotBean.getPraise_num();
            if (TextUtils.isEmpty(praise_num)) {
                praise_num = "0";
            }
            if (praise_num.matches(RegexPool.NUMBERS)) {
                hotBean.setPraise_num(String.valueOf(Integer.parseInt(praise_num) + (praise ? 1 : -1)));
                this.mCommListAdapter.notifyDataSetChanged();
            }
        }
    }

    public void dialogNote(final String content) {
        new XPopup.Builder(this.mContext).asCustom(new NoteNewPopWindow(this.mContext, content, new NoteNewPopWindow.NoteClickIml() { // from class: com.psychiatrygarden.activity.online.fragment.m1
            @Override // com.psychiatrygarden.activity.online.popwindow.NoteNewPopWindow.NoteClickIml
            public final void mDoClickMethod() {
                this.f13282a.lambda$dialogNote$31(content);
            }
        })).toggle();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(32:21|(3:23|(4:26|(3:143|28|146)(1:145)|144|24)|142)|29|(1:32)|33|(2:35|(1:39))(3:40|(1:44)|45)|46|(1:48)|49|(1:51)|52|(1:54)(1:55)|56|(1:58)|59|(1:61)(1:62)|63|(3:126|64|65)|(12:67|68|128|69|70|(4:134|72|(1:74)(1:75)|76)(0)|105|(1:107)(1:108)|109|(1:111)|112|(8:114|(1:116)|117|(1:120)|121|(1:123)|124|125)(1:149))(1:80)|81|(1:83)|84|(5:130|86|87|132|88)(1:94)|95|136|96|105|(0)(0)|109|(0)|112|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x02f1, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x032a  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x033b  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0353  */
    /* JADX WARN: Removed duplicated region for block: B:149:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0228 A[Catch: Exception -> 0x02f3, TryCatch #4 {Exception -> 0x02f3, blocks: (B:72:0x0218, B:74:0x0220, B:76:0x022b, B:81:0x0271, B:83:0x0280, B:84:0x028b, B:75:0x0228), top: B:134:0x0218 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doPushAnsData() throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 1080
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment.doPushAnsData():void");
    }

    public void getBiaoData() {
        QuestionDataRequest.getIntance(this.mContext).questionlabel(this.question_id + "", this);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_answer_question;
    }

    public void initBiaoQianData(final List<BiaoqianBeab.DataBean> dataBiao) {
        if (this.mContext == null) {
            return;
        }
        if (dataBiao == null || dataBiao.size() <= 0 || ConfigUtils.isHidden(3)) {
            this.mRadioGroupContent.removeAllViews();
            this.biaotxt.setText("标签：？");
            this.headerContentView.findViewById(R.id.linbiaoqian).setVisibility(8);
            return;
        }
        this.headerContentView.findViewById(R.id.linbiaoqian).setVisibility(0);
        this.biaotxt.setText("标签：");
        this.mRadioGroupContent.removeAllViews();
        if (dataBiao.get(0).getCount() < 3) {
            TextView textView = (TextView) LayoutInflater.from(this.mContext).inflate(R.layout.biaoqianview, (ViewGroup) null).findViewById(R.id.bqview);
            int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, this.mContext, 2);
            int pxBySp = ScreenUtil.getPxBySp(this.mContext, 2);
            textView.setTextSize(12.0f);
            textView.setTextSize(0, textView.getTextSize() - ((intConfig - 2) * pxBySp));
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                textView.setBackgroundResource(R.drawable.gray_deek_round_bg);
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color));
            } else {
                textView.setBackgroundResource(R.drawable.gray_deek_round_bg_night);
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
            }
            textView.setText("点击为本题添加标签");
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.k1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13266c.lambda$initBiaoQianData$18(dataBiao, view);
                }
            });
            this.mRadioGroupContent.addView(textView);
            return;
        }
        for (int i2 = 0; i2 < 3; i2++) {
            if (dataBiao.get(i2).getCount() >= 3) {
                TextView textView2 = (TextView) LayoutInflater.from(this.mContext).inflate(R.layout.biaoqianview, (ViewGroup) null).findViewById(R.id.bqview);
                textView2.setTextSize(12.0f);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                layoutParams.rightMargin = 20;
                textView2.setTextSize(0, textView2.getTextSize() - ((SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, this.mContext, 2) - 2) * ScreenUtil.getPxBySp(this.mContext, 2)));
                textView2.setText(String.format(Locale.CHINA, "%s %d", dataBiao.get(i2).getLabel(), Integer.valueOf(dataBiao.get(i2).getCount())));
                try {
                    if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                        textView2.setTextColor(Color.parseColor(dataBiao.get(i2).getColor()));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                textView2.setLayoutParams(layoutParams);
                textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.l1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f13275c.lambda$initBiaoQianData$19(dataBiao, view);
                    }
                });
                this.mRadioGroupContent.addView(textView2);
            }
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        if (getArguments() != null) {
            this.node_title = getArguments().getString("node_title", null);
            this.question_bank_id = getArguments().getString("question_bank_id", null);
            this.externalsources = getArguments().getString("externalsources");
            this.total = getArguments().getString("total");
            this.mCurrentPosition = getArguments().getInt("position");
            this.answerMode = getArguments().getString("answerMode", Constants.ANSWER_MODE.PRACTICE_MODE);
            this.module_type = getArguments().getString("module_type");
            this.category = getArguments().getString(UriUtil.QUERY_CATEGORY, "");
            this.chapterTitle = getArguments().getString("chapter_title");
            this.subjectTitle = getArguments().getString("subject_title", "");
            this.questionId = getArguments().getString("questionId");
            this.showTypeTitle = getArguments().getBoolean("show_type_title", true);
            this.ebook = getArguments().getBoolean("ebook", false);
            this.shareStem = getArguments().getBoolean("shareStem", false);
            this.videoSummary = getArguments().getBoolean("video_summary", false);
        }
        Context context = this.mContext;
        if (context instanceof AnswerQuestionActivity) {
            ((AnswerQuestionActivity) context).setVideoId("");
        }
        try {
            this.show_restore_img = SharePreferencesUtils.readStrConfig(CommonParameter.show_restore_img, this.mContext, "1");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        View viewInflate = View.inflate(this.mContext, R.layout.layout_choice_question_common, null);
        this.headerContentView = viewInflate;
        this.lineKnowledge = viewInflate.findViewById(R.id.line_knowledge_point);
        this.llEasyWrongOption = (LinearLayout) this.headerContentView.findViewById(R.id.ll_easy_wrong_option);
        this.tvEasyWrongOption = (TextView) this.headerContentView.findViewById(R.id.tv_easy_wrong_option);
        this.llStatisticsContent = this.headerContentView.findViewById(R.id.ll_statistics_content);
        this.img_wrong = (RoundedImageView) this.headerContentView.findViewById(R.id.img_wrong);
        this.ll_use_time = this.headerContentView.findViewById(R.id.ll_use_time);
        this.tvCurrent = (TextView) this.headerContentView.findViewById(R.id.tv_current);
        this.tvTotal = (TextView) this.headerContentView.findViewById(R.id.tv_total);
        this.mSeekBar = (SeekBar) this.headerContentView.findViewById(R.id.seekbar);
        this.ivPlayPause = (ImageView) this.headerContentView.findViewById(R.id.iv_video_play_pause);
        this.llAudio = (LinearLayout) this.headerContentView.findViewById(R.id.ll_audio);
        RecyclerView recyclerView = (RecyclerView) this.headerContentView.findViewById(R.id.rvTags);
        this.tvUseTime = (TextView) this.headerContentView.findViewById(R.id.tv_use_time);
        this.tvAllUseTime = (TextView) this.headerContentView.findViewById(R.id.tv_all_use_time);
        this.tvEasyWrongOptions = (TextView) this.headerContentView.findViewById(R.id.tv_easy_wrong_options);
        this.tvTotalDoTimes = (TextView) this.headerContentView.findViewById(R.id.total_do_times);
        this.tvAllCorrectPercent = (TextView) this.headerContentView.findViewById(R.id.tv_all_correct_percent);
        this.tvMyDoTimes = (TextView) this.headerContentView.findViewById(R.id.tv_my_do_times);
        this.tvMyCorrectPercent = (TextView) this.headerContentView.findViewById(R.id.tv_my_correct_percent);
        this.tvWrongOptionAnalysis = (TextView) this.headerContentView.findViewById(R.id.tv_wrong_option_analysis);
        this.llWrongOptionAnalysis = this.headerContentView.findViewById(R.id.ll_wrong_option_analysis);
        this.lvKnowledgePointTree = (LinearLayout) this.headerContentView.findViewById(R.id.lv_knowledge_point_tree);
        this.ll_knowledge_point = this.headerContentView.findViewById(R.id.ll_knowledge_point);
        this.nodeShowView = this.headerContentView.findViewById(R.id.ll_node_summary);
        this.mVpVideo = (ViewPager2) this.headerContentView.findViewById(R.id.vp_video);
        this.ll_statistics = (LinearLayout) this.headerContentView.findViewById(R.id.ll_statistics);
        this.ll_restore_point = (LinearLayout) this.headerContentView.findViewById(R.id.ll_restore_point);
        this.ll_answer_analysis_layout = (LinearLayout) this.headerContentView.findViewById(R.id.ll_answer_analysis_layout);
        TextView textView = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_title);
        this.tvEditTime = (TextView) this.headerContentView.findViewById(R.id.tv_edit_time);
        this.flTitleImg = (FrameLayout) this.headerContentView.findViewById(R.id.fl_title_img);
        this.llViewComment = (LinearLayout) this.headerContentView.findViewById(R.id.ll_view_comment);
        this.llNoComment = (LinearLayout) this.headerContentView.findViewById(R.id.ll_no_comment);
        this.nodeShowView.setVisibility(!this.videoSummary ? 0 : 8);
        if (getActivity() instanceof AnswerQuestionActivity) {
            QuestionDetailBean questionDetailBean = ((AnswerQuestionActivity) getActivity()).getQuestionDetailBean(this.mCurrentPosition);
            this.questionDetailBean = questionDetailBean;
            if (questionDetailBean == null) {
                return;
            }
            this.is_show_number = questionDetailBean.getShow_number();
            List<QuestionTopLabel> top_label = this.questionDetailBean.getTop_label();
            if (top_label == null || top_label.isEmpty()) {
                recyclerView.setVisibility(8);
            } else {
                ArrayList arrayList = new ArrayList();
                Iterator<QuestionTopLabel> it = top_label.iterator();
                while (it.hasNext()) {
                    String value = it.next().getValue();
                    if (!TextUtils.isEmpty(value)) {
                        arrayList.add(value);
                    }
                }
                BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_kc_filter) { // from class: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment.1
                    @Override // com.chad.library.adapter.base.BaseQuickAdapter
                    public void convert(@NonNull BaseViewHolder holder2, String s2) {
                        ((TextView) holder2.itemView).setText(s2);
                    }
                };
                baseQuickAdapter.setList(arrayList);
                recyclerView.setAdapter(baseQuickAdapter);
                recyclerView.setVisibility(0);
            }
            if (TextUtils.isEmpty(this.chapterTitle)) {
                this.headerContentView.findViewById(R.id.rl_top_title).setVisibility(8);
            } else {
                this.headerContentView.findViewById(R.id.rl_top_title).setVisibility(0);
                textView.setText(this.chapterTitle);
            }
            if (!TextUtils.isEmpty(this.questionDetailBean.getPublic_number()) || this.videoSummary) {
                this.headerContentView.findViewById(R.id.rl_top_title).setVisibility(8);
            }
            this.llAd = (QuestionAdWidegt) this.headerContentView.findViewById(R.id.ll_ad);
            this.question_id = Long.parseLong(this.questionDetailBean.getId());
            if (getActivity() instanceof AnswerQuestionActivity) {
                ((AnswerQuestionActivity) getActivity()).saveQuestionPage(this.question_id + "");
            }
            if (!TextUtils.equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_DATALINE, this.questionDetailBean.getType())) {
                this.mQuestionOptionBean = this.questionDetailBean.getOption();
                for (int i2 = 0; i2 < this.mQuestionOptionBean.size(); i2++) {
                    if (TextUtils.isEmpty(this.mQuestionOptionBean.get(i2).getType())) {
                        this.mQuestionOptionBean.get(i2).setType("0");
                    }
                }
            } else if (this.questionDetailBean.getOption() == null || this.questionDetailBean.getOption().isEmpty()) {
                this.mQuestionOptionBean = new ArrayList();
                initJudgeQuestionOption();
            } else {
                this.mQuestionOptionBean = this.questionDetailBean.getOption();
                String answer = this.questionDetailBean.getAnswer();
                if (!TextUtils.isEmpty(this.questionDetailBean.getUser_answer())) {
                    for (QuestionDetailBean.OptionDTO optionDTO : this.mQuestionOptionBean) {
                        optionDTO.setType("3");
                        if (TextUtils.equals(answer, this.questionDetailBean.getUser_answer())) {
                            if (TextUtils.equals("正确", answer)) {
                                if (TextUtils.equals("正确", optionDTO.getKey())) {
                                    optionDTO.setType("2");
                                } else {
                                    optionDTO.setType("0");
                                }
                            } else if (TextUtils.equals("错误", optionDTO.getKey())) {
                                optionDTO.setType("2");
                            } else {
                                optionDTO.setType("0");
                            }
                        } else if (TextUtils.equals("正确", answer)) {
                            if (TextUtils.equals("正确", optionDTO.getKey())) {
                                optionDTO.setType("2");
                            }
                        } else if (TextUtils.equals("错误", optionDTO.getKey())) {
                            optionDTO.setType("2");
                        }
                    }
                }
            }
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.drawable = ContextCompat.getDrawable(this.mContext, R.drawable.huanyuan);
            } else {
                this.drawable = ContextCompat.getDrawable(this.mContext, R.drawable.huanyuan_night);
            }
            View viewFindViewById = this.headerContentView.findViewById(R.id.lineviewtype);
            this.lineviewtype = viewFindViewById;
            if (!this.showTypeTitle && this.ebook) {
                viewFindViewById.setVisibility(8);
            }
            this.tv_correction = (TextView) this.headerContentView.findViewById(R.id.tv_correction);
            this.mCutQuestionFlag = (ImageView) this.headerContentView.findViewById(R.id.iv_cut_flag);
            this.mCutQuestionFlag.setVisibility((!TextUtils.equals("1", this.questionDetailBean.getIs_cut()) || getArguments().getBoolean("fromQuestionCombine", false)) ? 8 : 0);
            if (this.videoSummary) {
                this.mCutQuestionFlag.setVisibility(8);
            }
            if ("search".equals(this.externalsources) || this.videoSummary) {
                this.tv_correction.setVisibility(8);
            } else {
                this.tv_correction.setVisibility(0);
            }
            this.mLyAdView = (RelativeLayout) holder.get(R.id.ly_ad_view);
            this.mImgAd = (ImageView) holder.get(R.id.img_ad);
            this.mImgCloseAd = (ImageView) holder.get(R.id.btn_close);
            SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.srl_refresh);
            this.smartRefreshLayout = smartRefreshLayout;
            smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(TextUtils.equals(this.answerMode, Constants.ANSWER_MODE.RECITE_MODE) || !TextUtils.isEmpty(this.questionDetailBean.getUser_answer()));
            this.smartRefreshLayout.setEnableRefresh(TextUtils.equals(this.answerMode, Constants.ANSWER_MODE.RECITE_MODE) || !TextUtils.isEmpty(this.questionDetailBean.getUser_answer()));
            this.smartRefreshLayout.setEnableLoadMore(!TextUtils.isEmpty(this.questionDetailBean.getUser_answer()) || TextUtils.equals(this.answerMode, Constants.ANSWER_MODE.RECITE_MODE));
            if (this.videoSummary) {
                this.smartRefreshLayout.setEnableRefresh(false);
                this.smartRefreshLayout.setEnableLoadMore(false);
                this.smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);
            } else {
                this.smartRefreshLayout.setOnMultiPurposeListener(new AnonymousClass2());
            }
            this.mPinnedSecListView = (CommentSectionListView) holder.get(R.id.pinnedSectionListView);
            this.btn_comment = (TextView) holder.get(R.id.btn_comment);
            this.questiondetails_btn_edit = (ImageView) holder.get(R.id.questiondetails_btn_edit);
            this.questiondetails_btn_centerMsg = (ImageView) holder.get(R.id.questiondetails_btn_centerMsg);
            this.questiondetails_btn_zantong = (ImageView) holder.get(R.id.questiondetails_btn_zantong);
            this.questiondetails_btn_collect = (ImageView) holder.get(R.id.questiondetails_btn_collect);
            this.ly_questiondetails_btn_zantong = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_zantong);
            this.ly_questiondetails_btn_centerMsg = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_centerMsg);
            this.ly_questiondetails_btn_collect = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_collect);
            this.ly_questiondetails_btn_edit = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_edit);
            this.imgtitle = (AppCompatImageView) this.headerContentView.findViewById(R.id.imgtitle);
            this.rlQuestionVideo = (RelativeLayout) this.headerContentView.findViewById(R.id.rl_question_video);
            this.circlePoint = (CirclePoint) this.headerContentView.findViewById(R.id.circlepoint);
            this.qbrel = (RelativeLayout) holder.get(R.id.qbrel);
            this.typeStr = (TextView) this.headerContentView.findViewById(R.id.typeStr);
            this.tvPageNum = (TextView) this.headerContentView.findViewById(R.id.pagenumtv);
            this.tvTitle = (TextView) this.headerContentView.findViewById(R.id.titletv);
            this.tvQuestionNew = (TextView) this.headerContentView.findViewById(R.id.tv_question_new);
            this.qlistview = (MaxRecyclerView) this.headerContentView.findViewById(R.id.qlistview);
            this.textViewDifficulty = (TextView) this.headerContentView.findViewById(R.id.textView_difficulty);
            this.tvCommentNum = (TextView) holder.get(R.id.questiondetails_btn_commentNum);
            this.rlRestoreUser = (RelativeLayout) this.headerContentView.findViewById(R.id.rl_restore_user);
            this.rlAnalyzeUser = (RelativeLayout) this.headerContentView.findViewById(R.id.rl_analyze_user);
            this.llRestoreEnter = (LinearLayout) this.headerContentView.findViewById(R.id.ll_restore_enter);
            this.llAnalyzeEnter = (LinearLayout) this.headerContentView.findViewById(R.id.ll_analyze_enter);
            this.tvRestoreEdit = (TextView) this.headerContentView.findViewById(R.id.tv_restore_edit);
            this.tvAnalyzeEdit = (TextView) this.headerContentView.findViewById(R.id.tv_analyze_edit);
            this.tvRestoreToEdit = (TextView) this.headerContentView.findViewById(R.id.tv_restore_to_edit);
            this.tvAnalyzeToEdit = (TextView) this.headerContentView.findViewById(R.id.tv_analyze_to_edit);
            this.tvRestoreReword = (TextView) this.headerContentView.findViewById(R.id.tv_restore_reword);
            this.tvAnalyzeReword = (TextView) this.headerContentView.findViewById(R.id.tv_analyze_reword);
            this.llQuestionComment = (RelativeLayout) holder.get(R.id.ll_question_comment);
            this.mQuestiondetailsBottomLayout = (LinearLayout) holder.get(R.id.questiondetails_bottom_layout);
            this.tvAnswerAnalysis = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_contents);
            this.questiondetails_tv_outline = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_outline);
            this.questiondetails_tv_Answer = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_Answer);
            this.questiondetailsLayoutDiff = (LinearLayout) this.headerContentView.findViewById(R.id.questiondetails_layout_diff);
            this.tvPushAnswer = (TextView) holder.get(R.id.questiondetails_btn_pushAnswer);
            this.questiondetails_tv_content_ques1 = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_content_ques1);
            this.questiondetails_tv_statistics = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_statistics);
            this.mImgOriginal = (RoundedImageView) this.headerContentView.findViewById(R.id.img_original);
            this.mImgExplain = (RoundedImageView) this.headerContentView.findViewById(R.id.img_explain);
            this.tvStatistics = (TextView) this.headerContentView.findViewById(R.id.tv_statistics);
            this.tvAnswerAnalyze = (TextView) this.headerContentView.findViewById(R.id.tv_answer_analyze);
            this.llMoreColumns = (LinearLayout) this.headerContentView.findViewById(R.id.ll_more_columns);
            this.mRadioGroupContent = (LinearLayout) this.headerContentView.findViewById(R.id.mRadioGroup_content);
            this.mLyVideoView = (RelativeLayout) this.headerContentView.findViewById(R.id.ly_video_view);
            RoundedImageView roundedImageView = (RoundedImageView) this.headerContentView.findViewById(R.id.img_video);
            this.mImgAudio = (ImageView) this.headerContentView.findViewById(R.id.img_audio);
            this.webview = (WebView) this.headerContentView.findViewById(R.id.webview);
            this.sourcetv = (TextView) this.headerContentView.findViewById(R.id.sourcetv);
            this.biaotxt = (TextView) this.headerContentView.findViewById(R.id.biaotxt);
            this.lineout = (LinearLayout) this.headerContentView.findViewById(R.id.lineout);
            this.mTvEcyclopediaContents = (TextView) this.headerContentView.findViewById(R.id.tv_encyclopedia_contents);
            this.mImgEncyclopediaExplain = (RoundedImageView) this.headerContentView.findViewById(R.id.img_encyclopedia_explain);
            this.mTvEncyclopediaAnalyze = (TextView) this.headerContentView.findViewById(R.id.tv_encyclopedia_analyze);
            this.mLyUserInfo = (RelativeLayout) this.headerContentView.findViewById(R.id.ly_user_info);
            this.mImgUserHead = (CircleImageView) this.headerContentView.findViewById(R.id.img_head);
            this.mTvUserNickName = (TextView) this.headerContentView.findViewById(R.id.tv_nickname);
            this.mTvUserUpdateTime = (TextView) this.headerContentView.findViewById(R.id.tv_time);
            this.mLyPraise = (LinearLayout) this.headerContentView.findViewById(R.id.ly_praise_user);
            this.mImgPraise = (ImageView) this.headerContentView.findViewById(R.id.img_praise);
            this.mTvPraiseCount = (TextView) this.headerContentView.findViewById(R.id.tv_praise_count);
            this.mLyRestoreUserInfo = (RelativeLayout) this.headerContentView.findViewById(R.id.ly_restore_user_info);
            this.mImgRestoreUserHead = (CircleImageView) this.headerContentView.findViewById(R.id.img_restore_head);
            this.mTvRestoreUserNickName = (TextView) this.headerContentView.findViewById(R.id.tv_restore_nickname);
            this.mTvRestoreUserUpdateTime = (TextView) this.headerContentView.findViewById(R.id.tv_restore_time);
            this.mLyRestorePraise = (LinearLayout) this.headerContentView.findViewById(R.id.ly_restore_praise_user);
            this.mImgRestorePraise = (ImageView) this.headerContentView.findViewById(R.id.img_restore_praise);
            this.mTvRestorePraiseCount = (TextView) this.headerContentView.findViewById(R.id.tv_restore_praise_count);
            this.mLyAnswerUserInfo = (RelativeLayout) this.headerContentView.findViewById(R.id.ly_answer_user_info);
            this.mImgAnswerUserHead = (CircleImageView) this.headerContentView.findViewById(R.id.img_answer_head);
            this.mTvAnswerUserNickName = (TextView) this.headerContentView.findViewById(R.id.tv_answer_nickname);
            this.mTvAnswerUserUpdateTime = (TextView) this.headerContentView.findViewById(R.id.tv_answer_time);
            this.mLyAnswerPraise = (LinearLayout) this.headerContentView.findViewById(R.id.ly_answer_praise_user);
            this.mImgAnswerPraise = (ImageView) this.headerContentView.findViewById(R.id.img_answer_praise);
            this.mTvAnswerPraiseCount = (TextView) this.headerContentView.findViewById(R.id.tv_answer_praise_count);
            this.mTvAnswerRecord = (TextView) this.headerContentView.findViewById(R.id.tv_answer_reword);
            this.ll_answer_enter = (LinearLayout) this.headerContentView.findViewById(R.id.ll_answer_enter);
            this.rlAnswerUser = (RelativeLayout) this.headerContentView.findViewById(R.id.rl_answer_user);
            this.tvAnswerEdit = (TextView) this.headerContentView.findViewById(R.id.tv_answer_edit);
            this.tvAnswerToEdit = (TextView) this.headerContentView.findViewById(R.id.tv_answer_to_edit);
            this.ll_encyclopedia_analysis_layout = (LinearLayout) this.headerContentView.findViewById(R.id.ll_encyclopedia_analysis_layout);
            this.mLineRestore = this.headerContentView.findViewById(R.id.line_restore);
            this.mLineExpline = this.headerContentView.findViewById(R.id.line_expline);
            this.mLineEmpty = this.headerContentView.findViewById(R.id.line_empty);
            this.mLineVideo = this.headerContentView.findViewById(R.id.line_video);
            this.qlistview.setHasFixedSize(true);
            this.qlistview.setNestedScrollingEnabled(false);
            RecyclerView.ItemAnimator itemAnimator = this.qlistview.getItemAnimator();
            if (itemAnimator instanceof SimpleItemAnimator) {
                ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
            }
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.grayColors = ContextCompat.getColorStateList(this.mContext, R.color.back_font_gray);
            } else {
                this.grayColors = ContextCompat.getColorStateList(this.mContext, R.color.question_color_night);
            }
            if ("search".equals(this.externalsources)) {
                this.mQuestiondetailsBottomLayout.setVisibility(8);
            } else {
                this.mQuestiondetailsBottomLayout.setVisibility(0);
            }
            if (this.videoSummary) {
                int childCount = this.mQuestiondetailsBottomLayout.getChildCount();
                for (int i3 = 0; i3 < childCount; i3++) {
                    View childAt = this.mQuestiondetailsBottomLayout.getChildAt(i3);
                    childAt.setVisibility(childAt.getId() == R.id.questiondetails_btn_pushAnswer ? 0 : 8);
                }
            }
            if (this.questionDetailBean.getStem_video_list() == null || this.questionDetailBean.getStem_video_list().size() <= 0) {
                this.mLyVideoView.setVisibility(8);
            } else {
                this.mLyVideoView.setVisibility(0);
                Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(this.questionDetailBean.getStem_video_list().get(0).getThumb())).override(ScreenUtil.getPxByDp(this.mContext, R2.anim.voice_from_icon), ScreenUtil.getPxByDp(this.mContext, 94)).placeholder(new ColorDrawable(ContextCompat.getColor(roundedImageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(roundedImageView);
            }
            if (this.positionList.size() == 0 || !this.positionList.contains(Integer.valueOf(this.mCurrentPosition))) {
                this.positionList.add(Integer.valueOf(this.mCurrentPosition));
            }
            boolean z2 = !TextUtils.isEmpty(this.questionDetailBean.getUser_answer());
            this.hasAnswer = z2;
            if (!z2 && !TextUtils.equals(this.answerMode, Constants.ANSWER_MODE.RECITE_MODE) && !TextUtils.isEmpty(this.questionDetailBean.getPublic_number())) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.qlistview.getLayoutParams();
                layoutParams.bottomMargin = CommonUtil.dip2px(requireContext(), 20.0f);
                this.qlistview.setLayoutParams(layoutParams);
            }
            initQuestionType();
            initListView(holder);
            this.isInit = true;
            if (this.hasAnswer && getActivity() != null && (getActivity() instanceof AnswerQuestionActivity)) {
                ((AnswerQuestionActivity) getActivity()).questionExpose("2");
            }
            this.mLyPraise.setOnClickListener(this);
            this.mLyRestorePraise.setOnClickListener(this);
            this.mLyAnswerPraise.setOnClickListener(this);
            this.tvAnswerEdit.setOnClickListener(this);
            this.ivPlayPause.setOnClickListener(this);
            this.headerContentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.activity.online.fragment.c1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    this.f13197c.lambda$initViews$0();
                }
            });
            this.headerContentView.findViewById(R.id.tv_view_comment).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.d1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13205c.lambda$initViews$1(view);
                }
            });
            this.mPinnedSecListView.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.online.fragment.e1
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f13213c.lambda$initViews$2(view, motionEvent);
                }
            });
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundleExtra;
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || requestCode != 1 || (bundleExtra = data.getBundleExtra("bundleIntent")) == null) {
            return;
        }
        pushComment(bundleExtra);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(final View v2) throws JSONException, NumberFormatException {
        AliyunVodPlayerView aliyunVodPlayerView;
        AliyunVodPlayerView aliyunVodPlayerView2;
        final int id = v2.getId();
        if (id == R.id.iv_video_play_pause) {
            CustomAliPlayerView customAliPlayerView = this.mAudioPlayerView;
            if (customAliPlayerView == null || (aliyunVodPlayerView2 = customAliPlayerView.mAliyunVodPlayerView) == null) {
                return;
            }
            int playerState = aliyunVodPlayerView2.getPlayerState();
            TypedArray typedArrayObtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.ic_question_video_pause, R.attr.ic_question_video_play});
            if (playerState == 3) {
                this.mAudioPlayerView.mAliyunVodPlayerView.onStop();
                this.ivPlayPause.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(1));
            } else {
                if (this.playEnd) {
                    this.mAudioPlayerView.mAliyunVodPlayerView.rePlay();
                } else {
                    this.mAudioPlayerView.mAliyunVodPlayerView.start();
                }
                this.ivPlayPause.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
            }
            typedArrayObtainStyledAttributes.recycle();
            return;
        }
        if (id == R.id.tv_restore_edit) {
            Intent intent = new Intent(this.mContext, (Class<?>) QuestionRestoreListActivity.class);
            intent.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_RESTORE);
            intent.putExtra("content", this.questionDetailBean.getRestore());
            intent.putExtra("question_id", String.valueOf(this.question_id));
            if (TextUtils.isEmpty(getArguments() != null ? getArguments().getString("unit_id", "") : "")) {
                intent.putExtra("identity_id", getArguments().getString("identity_id", ""));
            } else {
                intent.putExtra("identity_id", getArguments().getString("unit_id", ""));
            }
            if ("unit".equals(this.category)) {
                intent.putExtra("type", "2");
            } else {
                intent.putExtra("type", "1");
            }
            startActivity(intent);
            return;
        }
        if (id == R.id.tv_analyze_edit) {
            Intent intent2 = new Intent(this.mContext, (Class<?>) QuestionRestoreListActivity.class);
            intent2.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_OPTION_ANALYZE);
            intent2.putExtra("content", this.questionDetailBean.getOption_analysis());
            if (TextUtils.isEmpty(getArguments() != null ? getArguments().getString("unit_id", "") : "")) {
                intent2.putExtra("identity_id", getArguments().getString("identity_id", ""));
            } else {
                intent2.putExtra("identity_id", getArguments().getString("unit_id", ""));
            }
            if ("unit".equals(this.category)) {
                intent2.putExtra("type", "2");
            } else {
                intent2.putExtra("type", "1");
            }
            intent2.putExtra("question_id", String.valueOf(this.question_id));
            startActivity(intent2);
            return;
        }
        if (id == R.id.tv_answer_edit) {
            Intent intent3 = new Intent(this.mContext, (Class<?>) QuestionRestoreListActivity.class);
            intent3.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_ANALYZE);
            intent3.putExtra("content", this.questionDetailBean.getExplain());
            if (TextUtils.isEmpty(getArguments() != null ? getArguments().getString("unit_id", "") : "")) {
                intent3.putExtra("identity_id", getArguments().getString("identity_id", ""));
            } else {
                intent3.putExtra("identity_id", getArguments().getString("unit_id", ""));
            }
            if ("unit".equals(this.category)) {
                intent3.putExtra("type", "2");
            } else {
                intent3.putExtra("type", "1");
            }
            intent3.putExtra("question_id", String.valueOf(this.question_id));
            startActivity(intent3);
            return;
        }
        if (id == R.id.tv_restore_to_edit || id == R.id.tv_analyze_to_edit || id == R.id.tv_answer_to_edit) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            new XPopup.Builder(this.mContext).asCustom(new QuestionRestoreEditPopWindow(this.mContext, new QuestionRestoreEditPopWindow.RestoreClickIml() { // from class: com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment.9
                @Override // com.psychiatrygarden.activity.online.popwindow.QuestionRestoreEditPopWindow.RestoreClickIml
                public void mEditMethod() {
                    Intent intent4 = new Intent(((BaseFragment) SubChoiceQuestionFragment.this).mContext, (Class<?>) QuestionRestoreEditActivity.class);
                    intent4.putExtra("question_id", SubChoiceQuestionFragment.this.question_id + "");
                    if (v2.getId() == R.id.tv_restore_to_edit) {
                        intent4.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_RESTORE);
                        intent4.putExtra("content", SubChoiceQuestionFragment.this.questionDetailBean.getRestore());
                    } else if (id == R.id.tv_analyze_to_edit) {
                        intent4.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_OPTION_ANALYZE);
                        intent4.putExtra("content", SubChoiceQuestionFragment.this.questionDetailBean.getOption_analysis());
                    } else {
                        intent4.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_ANALYZE);
                        intent4.putExtra("content", SubChoiceQuestionFragment.this.questionDetailBean.getExplain());
                    }
                    if (TextUtils.isEmpty(SubChoiceQuestionFragment.this.getArguments() != null ? SubChoiceQuestionFragment.this.getArguments().getString("unit_id", "") : "")) {
                        intent4.putExtra("identity_id", SubChoiceQuestionFragment.this.getArguments().getString("identity_id", ""));
                    } else {
                        intent4.putExtra("identity_id", SubChoiceQuestionFragment.this.getArguments().getString("unit_id", ""));
                    }
                    if ("unit".equals(SubChoiceQuestionFragment.this.category)) {
                        intent4.putExtra("type", "2");
                    } else {
                        intent4.putExtra("type", "1");
                    }
                    SubChoiceQuestionFragment.this.startActivity(intent4);
                }

                @Override // com.psychiatrygarden.activity.online.popwindow.QuestionRestoreEditPopWindow.RestoreClickIml
                public void mReEditMethod() {
                    Intent intent4 = new Intent(((BaseFragment) SubChoiceQuestionFragment.this).mContext, (Class<?>) QuestionRestoreEditActivity.class);
                    if (v2.getId() == R.id.tv_restore_to_edit) {
                        intent4.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_RESTORE);
                    } else if (id == R.id.tv_analyze_to_edit) {
                        intent4.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_OPTION_ANALYZE);
                        intent4.putExtra("content", SubChoiceQuestionFragment.this.questionDetailBean.getOption_analysis());
                    } else {
                        intent4.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_ANALYZE);
                        intent4.putExtra("content", SubChoiceQuestionFragment.this.questionDetailBean.getExplain());
                    }
                    intent4.putExtra("question_id", String.valueOf(SubChoiceQuestionFragment.this.question_id));
                    if (TextUtils.isEmpty(SubChoiceQuestionFragment.this.getArguments() != null ? SubChoiceQuestionFragment.this.getArguments().getString("unit_id", "") : "")) {
                        intent4.putExtra("identity_id", SubChoiceQuestionFragment.this.getArguments().getString("identity_id", ""));
                    } else {
                        intent4.putExtra("identity_id", SubChoiceQuestionFragment.this.getArguments().getString("unit_id", ""));
                    }
                    if ("unit".equals(SubChoiceQuestionFragment.this.category)) {
                        intent4.putExtra("type", "2");
                    } else {
                        intent4.putExtra("type", "1");
                    }
                    SubChoiceQuestionFragment.this.startActivity(intent4);
                }
            })).toggle();
            return;
        }
        if (id == R.id.ly_questiondetails_btn_collect) {
            if (this.questionDetailBean.getStatData().getAnswer() == null) {
                AlertToast("原题加载中,请稍后再试！");
                return;
            }
            String knowledge_id = this.questionDetailBean.getKnowledge_id();
            this.questionDetailBean.setModule_type(TextUtils.isEmpty(knowledge_id) ? this.module_type : "110");
            QuestionDetailBean questionDetailBean = this.questionDetailBean;
            questionDetailBean.setIs_redo(TextUtils.isEmpty(questionDetailBean.getIs_redo()) ? "0" : this.questionDetailBean.getIs_redo());
            this.questionDetailBean.setUnit_title(ProjectApp.unit_title);
            this.questionDetailBean.setUnit_id(getArguments().getString("unit_id", ""));
            this.questionDetailBean.setExam_title(ProjectApp.exam_title);
            this.questionDetailBean.setIdentity_title(ProjectApp.identity_title);
            this.questionDetailBean.setChapter_title(this.chapterTitle);
            this.questionDetailBean.setChapter_parent_title(this.subjectTitle);
            this.questionDetailBean.setChapter_parent_id(getArguments().getString("chapter_parent_id", ""));
            if (!TextUtils.isEmpty(knowledge_id)) {
                QuestionDetailBean questionDetailBean2 = this.questionDetailBean;
                questionDetailBean2.setChapter_parent_id(questionDetailBean2.getChapter_parent_id());
            }
            String json = ProjectApp.gson.toJson(this.questionDetailBean);
            AjaxParams ajaxParams = new AjaxParams();
            if (this.questionDetailBean.getStatData().getIs_collection() == 0) {
                this.questionDetailBean.getStatData().setIs_collection(1);
                havaCollectimg();
                AlertToast("收藏成功！");
                this.questionDetailBean.getStatData().setCollection_count(this.questionDetailBean.getStatData().getCollection_count() + 1);
                JSONArray jSONArray = new JSONArray();
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("question_id", this.questionDetailBean.getId());
                    jSONObject.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext, "1"));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                jSONArray.put(jSONObject);
                ajaxParams.put("collection", jSONArray.toString());
                ajaxParams.put("module_type", "" + this.module_type);
                QuestionDataRequest.getIntance(this.mContext).questionDoCollectData(ajaxParams, this);
                String str = "[\"" + this.questionDetailBean.getId() + "\"]";
                String str2 = "[\"" + this.questionDetailBean.getTitle() + "\"]";
                AliyunEvent aliyunEvent = AliyunEvent.CollectionQuestion;
                CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, str2, json, "2");
            } else {
                this.questionDetailBean.getStatData().setIs_collection(0);
                noCollectimg();
                AlertToast("取消收藏成功！");
                if (this.questionDetailBean.getStatData().getCollection_count() > 0) {
                    this.questionDetailBean.getStatData().setCollection_count(this.questionDetailBean.getStatData().getCollection_count() - 1);
                }
                ajaxParams.put("question_id", "" + this.questionDetailBean.getId());
                ajaxParams.put("module_type", "" + this.module_type);
                QuestionDataRequest.getIntance(this.mContext).cleancollection(ajaxParams, this);
                String str3 = "[\"" + this.questionDetailBean.getId() + "\"]";
                String str4 = "[\"" + this.questionDetailBean.getTitle() + "\"]";
                AliyunEvent aliyunEvent2 = AliyunEvent.CancelCollectionQuestion;
                CommonUtil.addLog(aliyunEvent2.getKey(), aliyunEvent2.getValue(), System.currentTimeMillis() + "", "", str3, str4, json, "2");
            }
            EventBus.getDefault().post(new QuestionCollectEvent(this.questionDetailBean.getStatData().getIs_collection() == 1, this.questionDetailBean.getId()));
            initStaticData();
            return;
        }
        if (id == R.id.ly_questiondetails_btn_zantong) {
            Intent intent4 = new Intent(this.mContext, (Class<?>) CommentNewActivity.class);
            intent4.putExtra("obj_id", String.valueOf(this.question_id));
            intent4.putExtra("question_id", this.question_id);
            intent4.putExtra("module_type", 1);
            intent4.putExtra("comment_type", "2");
            intent4.putExtra("isNewComzantong", true);
            intent4.putExtra("iscomValu", true);
            intent4.putExtra(com.alipay.sdk.authjs.a.f3174g, "mypraise");
            intent4.putExtra(SocialConstants.PARAM_SOURCE, "answer_question");
            startActivity(intent4);
            return;
        }
        if (id == R.id.btn_comment) {
            if (isLogin()) {
                new DialogInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.l0
                    @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                    public final void onclickStringBack(String str5, String str6, String str7) {
                        this.f13274a.lambda$onClick$29(str5, str6, str7);
                    }
                }, true).show();
                return;
            }
            return;
        }
        if (id == R.id.ly_questiondetails_btn_edit) {
            if (this.questionDetailBean.getStatData().getAnswer() == null) {
                AlertToast("原题加载中,请稍后再试！");
                return;
            } else if (this.questionDetailBean.getStatData().getIs_note() == 0) {
                new DialogNoteInput(this.mContext, this.module_type, String.valueOf(this.question_id), new onDialogNoteListener() { // from class: com.psychiatrygarden.activity.online.fragment.n0
                    @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                    public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                        this.f13290a.lambda$onClick$30(questionNoteBean);
                    }
                }).show();
                return;
            } else {
                new XPopup.Builder(this.mContext).moveUpToKeyboard(Boolean.FALSE).enableDrag(true).asCustom(new PopNoteList(this.mContext, this.module_type, this.questionDetailBean.getId())).show();
                return;
            }
        }
        if (id == R.id.tv_correction) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            Intent intent5 = new Intent(this.mContext, (Class<?>) QuestionCorrectionActivity.class);
            intent5.putExtra("question_id", String.valueOf(this.question_id));
            startActivity(intent5);
            return;
        }
        if (id == R.id.questiondetails_btn_commentNum || id == R.id.ll_question_comment) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            this.remen.performClick();
            if (getActivity() instanceof AnswerQuestionActivity) {
                ((AnswerQuestionActivity) getActivity()).hideTitleView(String.valueOf(this.question_id));
                return;
            }
            return;
        }
        if (id == R.id.questiondetails_btn_pushAnswer) {
            pushAnswer();
            return;
        }
        if (id == R.id.ly_questiondetails_btn_centerMsg) {
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.Comment_library_Red_Dot, false, this.mContext);
            EventBus.getDefault().post(CommonParameter.Comment_library_Red_Dot);
            Intent intent6 = new Intent(this.mContext, (Class<?>) CommentNewActivity.class);
            intent6.putExtra("obj_id", String.valueOf(this.question_id));
            intent6.putExtra("question_id", this.question_id);
            intent6.putExtra("module_type", 1);
            intent6.putExtra("comment_type", "2");
            intent6.putExtra("isNewCom", true);
            intent6.putExtra("iscomValu", true);
            intent6.putExtra(com.alipay.sdk.authjs.a.f3174g, "mycomment");
            startActivity(intent6);
            return;
        }
        if (id == R.id.ly_video_view) {
            this.isClickAudio = false;
            CustomAliPlayerView customAliPlayerView2 = this.mAudioPlayerView;
            if (customAliPlayerView2 != null && (aliyunVodPlayerView = customAliPlayerView2.mAliyunVodPlayerView) != null) {
                aliyunVodPlayerView.onStop();
                this.mAudioPlayerView.onStopped();
            }
            AjaxParams ajaxParams2 = new AjaxParams();
            ajaxParams2.put("question_id", "" + this.questionDetailBean.getId());
            ajaxParams2.put("resource_type", "1");
            ajaxParams2.put("video_id", this.questionDetailBean.getStem_video_list().get(0).getVideo_id());
            if (this.videoSummary) {
                QuestionDataRequest.getIntance(this.mContext).getMeidaSourceById(ajaxParams2, "1", this, this.questionDetailBean.getApp_id());
                return;
            } else {
                QuestionDataRequest.getIntance(this.mContext).getMeidaSourceById(ajaxParams2, "1", this);
                return;
            }
        }
        if (id == R.id.img_audio) {
            this.isClickAudio = true;
            CustomAliPlayerView customAliPlayerView3 = this.mAudioPlayerView;
            if (customAliPlayerView3 == null) {
                getAudioInfo();
                return;
            }
            AliyunVodPlayerView aliyunVodPlayerView3 = customAliPlayerView3.mAliyunVodPlayerView;
            if (aliyunVodPlayerView3 == null) {
                this.mAudioPlayerView = null;
                getAudioInfo();
                return;
            } else if (!aliyunVodPlayerView3.isPlaying()) {
                this.mAudioPlayerView.mAliyunVodPlayerView.rePlay();
                return;
            } else {
                this.mAudioPlayerView.mAliyunVodPlayerView.onStop();
                this.mAudioPlayerView.onStopped();
                return;
            }
        }
        if (id == R.id.ly_praise_user) {
            if (TextUtils.isEmpty(this.questionDetailBean.getStatData().getOption_analysis_praise())) {
                this.questionDetailBean.getStatData().setOption_analysis_praise("0");
            }
            analysisPraiseAction(this.questionDetailBean.getStatData().getOption_analysis_user().getId(), this.questionDetailBean.getStatData().getOption_analysis_praise().equals("1") ? "2" : "1", "3");
        } else if (id == R.id.ly_restore_praise_user) {
            if (TextUtils.isEmpty(this.questionDetailBean.getStatData().getRestore_praise())) {
                this.questionDetailBean.getStatData().setRestore_praise("0");
            }
            analysisPraiseAction(this.questionDetailBean.getStatData().getRestore_user().getId(), this.questionDetailBean.getStatData().getRestore_praise().equals("1") ? "2" : "1", "1");
        } else if (id == R.id.ly_answer_praise_user) {
            if (TextUtils.isEmpty(this.questionDetailBean.getStatData().getExplain_praise())) {
                this.questionDetailBean.getStatData().setExplain_praise("0");
            }
            analysisPraiseAction(this.questionDetailBean.getStatData().getExplain_user().getId(), this.questionDetailBean.getStatData().getExplain_praise().equals("1") ? "2" : "1", "2");
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.isInit = false;
        CustomAliPlayerView customAliPlayerView = this.mAudioPlayerView;
        if (customAliPlayerView != null) {
            customAliPlayerView.onDestory();
        }
        if (this.videoMainDialog != null) {
            this.videoMainDialog = null;
            ProjectApp.mPlayerVideo.clear();
        }
        QuestionAdWidegt questionAdWidegt = this.llAd;
        if (questionAdWidegt != null) {
            questionAdWidegt.onDestory();
        }
        if (getActivity() instanceof AnswerQuestionActivity) {
            ((AnswerQuestionActivity) getActivity()).removeQuestionPage(String.valueOf(this.question_id));
        }
        SharePreferencesUtils.removeConfig(String.valueOf(this.question_id), this.mContext);
    }

    @Subscribe
    public void onEventMainThread(RefreshQuestionCommentEvent event) {
        if (TextUtils.equals(event.getQuestionId(), String.valueOf(this.question_id)) && this.currentPageIsVisible) {
            this.pageNum = 1;
            getCommentListData();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        if (!requstUrl.equals(NetworkRequestsURL.questionlabelApi)) {
            if (strMsg != null) {
                AlertToast(strMsg);
                return;
            }
            return;
        }
        try {
            this.biaotxt.setText("标签：？");
            this.mRadioGroupContent.removeAllViews();
            if (ConfigUtils.isHidden(3)) {
                this.headerContentView.findViewById(R.id.linbiaoqian).setVisibility(8);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("image_audio", "hidden：" + hidden);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        CustomAliPlayerView customAliPlayerView = this.mAudioPlayerView;
        if (customAliPlayerView != null) {
            AliyunVodPlayerView aliyunVodPlayerView = customAliPlayerView.mAliyunVodPlayerView;
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.onStop();
            }
            this.mAudioPlayerView.onStopped();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        QuestionAdWidegt questionAdWidegt = this.llAd;
        if (questionAdWidegt != null) {
            questionAdWidegt.onResume();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        CustomAliPlayerView customAliPlayerView = this.mAudioPlayerView;
        if (customAliPlayerView != null) {
            AliyunVodPlayerView aliyunVodPlayerView = customAliPlayerView.mAliyunVodPlayerView;
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.onStop();
            }
            this.mAudioPlayerView.onStopped();
            TypedArray typedArrayObtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.ic_question_video_play});
            this.ivPlayPause.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onSupportInvisible() {
        super.onSupportInvisible();
        if (TextUtils.isEmpty(this.start_timestamp) || !this.isAnswerVisible) {
            return;
        }
        Log.e("wwwwwwwwww", "不可见 " + this.questionDetailBean.getTitle());
        Log.e("wwwwwwwwww", "不可见 时长统计" + ((System.currentTimeMillis() - Long.parseLong(this.start_timestamp)) / 1000));
        String str = "[\"" + this.question_id + "\"]";
        String str2 = "[\"" + this.questionDetailBean.getTitle() + "\"]";
        this.questionDetailBean.setModule_type(TextUtils.isEmpty(this.questionDetailBean.getKnowledge_id()) ? this.module_type : "110");
        QuestionDetailBean questionDetailBean = this.questionDetailBean;
        questionDetailBean.setIs_redo(TextUtils.isEmpty(questionDetailBean.getIs_redo()) ? "0" : this.questionDetailBean.getIs_redo());
        this.questionDetailBean.setUnit_title(ProjectApp.unit_title);
        this.questionDetailBean.setExam_title(ProjectApp.exam_title);
        this.questionDetailBean.setIdentity_title(ProjectApp.identity_title);
        this.questionDetailBean.setChapter_title(this.chapterTitle);
        this.questionDetailBean.setChapter_parent_title(this.subjectTitle);
        String json = ProjectApp.gson.toJson(this.questionDetailBean);
        AliyunEvent aliyunEvent = AliyunEvent.VisitQuestion;
        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), str, str2, this.start_timestamp, System.currentTimeMillis() + "", json);
        this.start_timestamp = "";
    }

    public void onVisible() {
        this.isFragmentVisible = true;
        this.start_timestamp = System.currentTimeMillis() + "";
    }

    public void pausePlayVideoAudio() {
        if (this.videoMainDialog != null) {
            this.videoMainDialog = null;
        }
        CustomAliPlayerView customAliPlayerView = this.mAudioPlayerView;
        if (customAliPlayerView != null) {
            this.isInit = false;
            AliyunVodPlayerView aliyunVodPlayerView = customAliPlayerView.mAliyunVodPlayerView;
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.onStop();
            }
            this.mAudioPlayerView.onStopped();
            TypedArray typedArrayObtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.ic_question_video_play});
            this.ivPlayPause.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public void playMediaAudio() {
        new Handler().postDelayed(new x0(this), 600L);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        QuestionDetailBean questionDetailBean;
        super.setUserVisibleHint(isVisibleToUser);
        this.currentPageIsVisible = isVisibleToUser;
        if (isVisibleToUser) {
            long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
            this.startTime = jCurrentTimeMillis;
            SharePreferencesUtils.writeLongConfig("question_startTime", Long.valueOf(jCurrentTimeMillis), this.mContext);
            LogUtils.e("question_load_time", "startTime页面可时间时间戳===>" + this.startTime);
        }
        if (this.isInit) {
            if (isVisibleToUser) {
                new Handler().postDelayed(new x0(this), 200L);
                initAd(this.mContext);
            } else {
                pausePlayVideoAudio();
            }
            if (isVisibleToUser && (questionDetailBean = this.questionDetailBean) != null && TextUtils.isEmpty(questionDetailBean.getPublic_number())) {
                EventBus.getDefault().post(new UpdateQuestionIdEvent(this.questionDetailBean.getId()));
            }
        }
    }

    /* renamed from: showlog, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$onSuccess$33(List<BiaoqianBeab.DataBean> dataBiao, View v2) {
        new XPopup.Builder(this.mContext).asCustom(new BiaoPupNewWindow(this.mContext, dataBiao, new BiaoqianCallbackInterface() { // from class: com.psychiatrygarden.activity.online.fragment.f1
            @Override // com.psychiatrygarden.interfaceclass.BiaoqianCallbackInterface
            public final void mBiaoianLinster(List list, int i2, boolean z2) {
                this.f13222a.lambda$showlog$17(list, i2, z2);
            }
        })).toggle();
    }

    public void titlenum() {
        String strReplace;
        String sort = this.questionDetailBean.getSort();
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext);
        if (TextUtils.isEmpty(this.is_show_number)) {
            if (TextUtils.isEmpty(this.questionDetailBean.getNumber()) || (!(strConfig.equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ) || strConfig.equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR) || strConfig.equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_JOININ_GROUP) || strConfig.equals("20") || strConfig.equals("21")) || "search".equals(this.externalsources))) {
                strReplace = (!TextUtils.isEmpty(this.questionDetailBean.getPublic_number()) || TextUtils.isEmpty(this.questionDetailBean.getPublic_title())) ? this.questionDetailBean.getTitle().replace(" ", "") : this.questionDetailBean.getNumber() + " " + this.questionDetailBean.getPublic_title() + this.questionDetailBean.getTitle().replace(" ", "");
            } else if (!TextUtils.isEmpty(this.questionDetailBean.getPublic_number()) || TextUtils.isEmpty(this.questionDetailBean.getPublic_title())) {
                strReplace = this.questionDetailBean.getNumber() + " " + this.questionDetailBean.getTitle().replace(" ", "");
            } else {
                strReplace = this.questionDetailBean.getNumber() + " " + this.questionDetailBean.getPublic_title() + this.questionDetailBean.getTitle().replace(" ", "");
            }
        } else if (!this.is_show_number.equals("1") || "search".equals(this.externalsources)) {
            strReplace = (!TextUtils.isEmpty(this.questionDetailBean.getPublic_number()) || TextUtils.isEmpty(this.questionDetailBean.getPublic_title())) ? this.questionDetailBean.getTitle().replace(" ", "") : this.questionDetailBean.getNumber() + " " + this.questionDetailBean.getPublic_title() + this.questionDetailBean.getTitle().replace(" ", "");
        } else if (!TextUtils.isEmpty(this.questionDetailBean.getPublic_number()) || TextUtils.isEmpty(this.questionDetailBean.getPublic_title())) {
            strReplace = this.questionDetailBean.getNumber() + " " + this.questionDetailBean.getTitle().replace(" ", "");
        } else {
            strReplace = this.questionDetailBean.getNumber() + " " + this.questionDetailBean.getPublic_title() + this.questionDetailBean.getTitle().replace(" ", "");
        }
        if (TextUtils.isEmpty(SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_question_type, this.mContext)) || SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_question_type, this.mContext).equals("1")) {
            strReplace = sort + ". " + strReplace;
            this.lineviewtype.setVisibility(8);
        } else {
            if (!this.ebook && this.showTypeTitle) {
                this.lineviewtype.setVisibility(0);
            }
            this.tvPageNum.setText(CharacterParser.getSpannableColorSize(sort + " /" + this.total, 0, sort.length(), SkinManager.getCurrentSkinType(this.mContext) == 1 ? "#64729F" : "#000000"));
        }
        this.tvTitle.setText(strReplace.trim());
        if (!TextUtils.isEmpty(this.questionDetailBean.getPublic_number())) {
            this.tvPageNum.setVisibility(8);
            this.lineviewtype.setVisibility(8);
            this.typeStr.setText("");
            if (!TextUtils.isEmpty(this.questionDetailBean.getKnowledge_id())) {
                this.typeStr.setVisibility(8);
                this.headerContentView.findViewById(R.id.verticaltv).setVisibility(8);
                this.lineviewtype.setVisibility(0);
                if ("1".equals(this.questionDetailBean.getIs_new())) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.tvQuestionNew.getLayoutParams();
                    layoutParams.leftMargin = 0;
                    this.tvQuestionNew.setLayoutParams(layoutParams);
                } else {
                    View viewFindViewById = this.headerContentView.findViewById(R.id.rl_tag);
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) viewFindViewById.getLayoutParams();
                    layoutParams2.leftMargin = 0;
                    viewFindViewById.setLayoutParams(layoutParams2);
                }
            }
        }
        if (TextUtils.isEmpty(this.questionDetailBean.getPublic_number()) && !TextUtils.isEmpty(this.questionDetailBean.getPublic_title())) {
            this.lineviewtype.setVisibility(0);
            this.typeStr.setText("共用题干题");
            this.tvPageNum.setVisibility(8);
        }
        if (this.questionDetailBean.getStem_audio_list() != null && this.questionDetailBean.getStem_audio_list().size() > 0) {
            int i2 = 0;
            while (i2 * this.tvTitle.getPaint().measureText(" ") < CommonUtil.dip2px(requireContext(), 28.0f)) {
                i2++;
            }
            if (i2 > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i3 = 0; i3 < i2; i3++) {
                    sb.append("");
                }
                sb.append(strReplace);
                this.tvTitle.setText(sb);
            }
            if (TextUtils.equals(this.questionId, this.questionDetailBean.getId())) {
                playAudio();
            }
        }
        if ("1".equals(this.questionDetailBean.getIs_cut())) {
            updateCutIconLayout();
        }
        if (this.videoSummary || TextUtils.isEmpty(this.total)) {
            this.tvPageNum.setVisibility(8);
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) throws NumberFormatException {
        String strSubstring;
        int iIndexOf = requstUrl.indexOf(HiAnalyticsConstant.REPORT_VAL_SEPARATOR);
        if (iIndexOf > 0) {
            strSubstring = requstUrl.substring(iIndexOf + 1);
            requstUrl = requstUrl.substring(0, iIndexOf);
        } else {
            strSubstring = "";
        }
        if (requstUrl.equals(NetworkRequestsURL.getstatApi)) {
            QuestionStatDataBean questionStatDataBean = (QuestionStatDataBean) new Gson().fromJson(s2, QuestionStatDataBean.class);
            if (questionStatDataBean != null && questionStatDataBean.getCode().equals("200")) {
                if (questionStatDataBean.getData().getAnswer() != null) {
                    this.questionDetailBean.setStatData(questionStatDataBean.getData());
                    initStaticData();
                }
                this.hasGetStatData = true;
            }
        } else if (requstUrl.equals(NetworkRequestsURL.getcollectionApi)) {
            havaCollectimg();
            this.questionDetailBean.getStatData().setIs_collection(1);
        } else if (requstUrl.equals(NetworkRequestsURL.cleancollectionApi)) {
            noCollectimg();
            this.questionDetailBean.getStatData().setIs_collection(0);
        } else if (requstUrl.equals(NetworkRequestsURL.questionlabelApi)) {
            try {
                BiaoqianBeab biaoqianBeab = (BiaoqianBeab) new Gson().fromJson(s2, BiaoqianBeab.class);
                if (biaoqianBeab.getCode().equals("200")) {
                    final List<BiaoqianBeab.DataBean> data = biaoqianBeab.getData();
                    this.questionDetailBean.setDataBiao(data);
                    initBiaoQianData(data);
                    this.llMoreColumns.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.p1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f13307c.lambda$onSuccess$33(data, view);
                        }
                    });
                } else {
                    this.biaotxt.setText("标签：？");
                    this.mRadioGroupContent.removeAllViews();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if (requstUrl.equals(NetworkRequestsURL.questionUserLabelApi)) {
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (!jSONObject.optString("code").equals("200")) {
                    NewToast.showShort(this.mContext, jSONObject.optString("message"), 0).show();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        } else if (requstUrl.equals(NetworkRequestsURL.getnoteApi)) {
            try {
                this.questionDetailBean.setNote(new JSONObject(s2).optJSONObject("data").optString("content"));
                dialogNote(new JSONObject(s2).optJSONObject("data").optString("content"));
            } catch (JSONException e4) {
                e4.printStackTrace();
            }
        } else if (requstUrl.equals(NetworkRequestsURL.getanswerApi)) {
            try {
                if (new JSONObject(s2).optString("code").equals("200")) {
                    this.hasAnswer = true;
                    this.smartRefreshLayout.setEnabled(true);
                    initSubViewData();
                    bigOrAns();
                    if (this.qAdapter != null) {
                        this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.q1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f13317c.lambda$onSuccess$34();
                            }
                        });
                    }
                    if (getActivity() != null && (getActivity() instanceof AnswerQuestionActivity)) {
                        ((AnswerQuestionActivity) getActivity()).questionExpose("1");
                    }
                    EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_ANSWER_REFRESH, this.questionDetailBean, strSubstring));
                    getQuestionStatistics();
                }
            } catch (JSONException e5) {
                e5.printStackTrace();
            }
        } else if (requstUrl.equals(NetworkRequestsURL.getSourceInfoById)) {
            try {
                if (new JSONObject(s2).optString("code").equals("200")) {
                    String strOptString = new JSONObject(s2).optJSONObject("data").optString("vid");
                    String strOptString2 = new JSONObject(s2).optJSONObject("data").optString("duration");
                    if (strSubstring.equals("1")) {
                        this.videoMainDialog = ShowVideoDialog.newInstance(this.mContext, strOptString, strOptString2);
                        if (getActivity() != null) {
                            this.videoMainDialog.showDialog(this.mContext, getActivity().getWindow().getDecorView());
                        }
                    } else {
                        this.audioId = strOptString;
                        initAudioInfo(strOptString, strOptString2);
                    }
                }
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        }
        if (this.mCommListAdapter != null) {
            this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.r1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13325c.lambda$onSuccess$35();
                }
            });
        }
    }

    @Subscribe
    public void onEventMainThread(UpdateQuestionCutEvent event) {
        if (TextUtils.equals(event.getQuestionId(), String.valueOf(this.question_id))) {
            QuestionDetailBean questionDetailBean = this.questionDetailBean;
            if (questionDetailBean != null) {
                questionDetailBean.setIs_cut(event.getIsCut());
            }
            this.mCutQuestionFlag.setVisibility("1".equals(event.getIsCut()) ? 0 : 8);
            updateCutIconLayout();
            updateCutIcon();
        }
    }

    @Subscribe
    public void onEventMainThread(QuestionBack2TopEvent event) {
        if (TextUtils.equals(event.getQuestionId(), String.valueOf(this.question_id))) {
            if (!TextUtils.isEmpty(this.questionDetailBean.getPublic_number())) {
                this.back2Top = true;
            }
            scrollLayout();
        }
    }

    @Subscribe(sticky = true)
    public void onEventMainThread(UpdateTopMargin event) {
        if (TextUtils.equals(event.getQuestionId(), String.valueOf(this.question_id))) {
            if (this.lineviewtype.getVisibility() == 0) {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.lineviewtype.getLayoutParams();
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = event.getTopMargin();
                this.lineviewtype.setLayoutParams(layoutParams);
            } else {
                ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.headerContentView.findViewById(R.id.rl_title).getLayoutParams();
                ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = event.getTopMargin();
                if ("year".equals(this.category) && "all".equals(getArguments().getString("type", ""))) {
                    ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = 0;
                }
                this.headerContentView.findViewById(R.id.rl_title).setLayoutParams(layoutParams2);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NoteEventBean noteEventBean) {
        if (noteEventBean.question_id.equals(this.questionDetailBean.getId())) {
            String str = noteEventBean.content;
            if ((str != null && !"".equals(str)) || noteEventBean.img) {
                this.questionDetailBean.getStatData().setIs_note(1);
                haveNoteimg();
            } else {
                this.questionDetailBean.getStatData().setIs_note(0);
                noNoteimg();
            }
            this.questionDetailBean.setNote(noteEventBean.content);
        }
    }

    @Subscribe
    public void onEventMainThread(UpdateCommentPraiseEvent event) {
        if (this.commlist.isEmpty()) {
            return;
        }
        String objId = event.getObjId();
        for (CommentBean.DataBean.HotBean hotBean : this.commlist) {
            if (TextUtils.equals(hotBean.getId(), objId)) {
                updatePraise(hotBean, event.isPriase());
                hotBean.setIs_praise(event.isPriase() ? "1" : "0");
                return;
            }
            List<CommentBean.DataBean.HotBean.ReplyBean> reply = hotBean.getReply();
            if (reply != null && reply.size() > 0) {
                Iterator<CommentBean.DataBean.HotBean.ReplyBean> it = reply.iterator();
                while (true) {
                    if (it.hasNext()) {
                        CommentBean.DataBean.HotBean.ReplyBean next = it.next();
                        if (TextUtils.equals(objId, next.getId())) {
                            updatePraise(next, event.isPriase());
                            next.setIs_praise(event.isPriase() ? "1" : "0");
                        }
                    }
                }
            }
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (!"mCommentResult".equals(str) && !"refresh_praise".equals(str)) {
            if (EventBusConstant.EVENT_QUESTION_FONT_SIZE.equals(str)) {
                setFontSize();
                return;
            }
            if (TextUtils.equals(EventBusConstant.EVENT_REFRESH_COMMENT_NUM, str)) {
                updateBottomView();
                return;
            }
            if (TextUtils.equals("deleteComment", str)) {
                updateBottomView();
                return;
            }
            if ("delReplyAndLoadData".equals(str)) {
                this.pageNum = 1;
                getCommentListData();
                return;
            } else {
                if ("showStemPage".equals(str)) {
                    long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                    this.startTime = jCurrentTimeMillis;
                    SharePreferencesUtils.writeLongConfig("question_startTime", Long.valueOf(jCurrentTimeMillis), this.mContext);
                    LogUtils.e("question_load_time", "共用题干显示====startTime===>" + this.startTime);
                    initAd(this.mContext);
                    return;
                }
                return;
            }
        }
        updateBottomView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMessage event) {
        EventBusConstant.EVENT_QUESTION_ERROR_CORRECTION_OK.equals(event.getKey());
    }

    @Subscribe
    public void onEventMainThread(PlayQuestionMediaEvent event) {
        if (this.questionDetailBean == null || !TextUtils.equals(event.getId(), this.questionDetailBean.getId())) {
            return;
        }
        this.currentPageIsVisible = event.isPlay();
        if (event.isPlay()) {
            playAudio();
        } else {
            pausePlayVideoAudio();
        }
    }

    public void onEventMainThread(RefreshViewStateEvent e2) {
        if (TextUtils.equals("" + this.question_id, e2.getQuestionId())) {
            scrollLayout();
        }
    }

    @Subscribe
    public void onEventMainThread(CommentTagEvent event) {
        if (this.mLastTime + 300 > System.currentTimeMillis()) {
            return;
        }
        this.mLastTime = System.currentTimeMillis();
        if (TextUtils.equals("" + this.question_id, event.getQuestionId())) {
            SharePreferencesUtils.writeBooleanConfig(this.question_id + "", event.isShow(), this.mContext);
            if (!this.ishavehot) {
                if (this.lineselect2.getVisibility() != 8) {
                    this.lineselect2.setVisibility(8);
                    return;
                }
                return;
            }
            int visibility = this.lineselect2.getVisibility();
            if (event.isShow()) {
                this.isPinned = true;
                if (visibility != 0) {
                    this.lineselect2.setVisibility(0);
                    return;
                }
                return;
            }
            this.isPinned = false;
            if (visibility != 8) {
                this.lineselect2.setVisibility(8);
            }
        }
    }
}
