package com.psychiatrygarden.activity.online.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView;
import com.aliyun.vod.common.utils.UriUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.google.android.material.timepicker.TimeModel;
import com.google.gson.Gson;
import com.huawei.hms.push.HmsMessageService;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.CommentNewActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.NoteNewActivity;
import com.psychiatrygarden.activity.QuestionCommentActivity;
import com.psychiatrygarden.activity.QuestionCorrectionActivity;
import com.psychiatrygarden.activity.answer.adapter.AnalysisAdapter;
import com.psychiatrygarden.activity.answer.bean.AnalysisBean;
import com.psychiatrygarden.activity.answer.compose.ComposeActivity;
import com.psychiatrygarden.activity.online.AnswerQuestionActivity;
import com.psychiatrygarden.activity.online.QuestionRestoreEditActivity;
import com.psychiatrygarden.activity.online.QuestionRestoreListActivity;
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
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.CommentTagEvent;
import com.psychiatrygarden.event.HideChapterTitleEvent;
import com.psychiatrygarden.event.QuestionBack2TopEvent;
import com.psychiatrygarden.event.QuestionCollectEvent;
import com.psychiatrygarden.event.RefreshQuestionCommentEvent;
import com.psychiatrygarden.event.RefreshViewStateEvent;
import com.psychiatrygarden.event.UpdateCommentPraiseEvent;
import com.psychiatrygarden.event.UpdateQuestionCutEvent;
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
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.AnalyPopWindow;
import com.psychiatrygarden.widget.BiaoPupNewWindow;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.CommentSectionListView;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.DialogNoteInput;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.QuestionAdWidegt;
import com.psychiatrygarden.widget.ShowVideoDialog;
import com.psychiatrygarden.widget.english.PopNoteList;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SubSubjectiveQuestionFragment extends BaseFragment implements QuestionDataCallBack<String>, AnalysisAdapter.AnaImClickIml, View.OnClickListener {
    private AnalysisAdapter analysisAdapter;
    private AnimationDrawable animationDrawable;
    private LinearLayout ansline;
    private String answerMode;
    private TextView biaotxt;
    private ColorStateList blackColors;
    private String break_point;
    private TextView btn_comment;
    private String category;
    private String chapterTitle;
    private String commentContent;
    private String commentId;
    private Drawable drawable;
    private TextView eitv;
    private ColorStateList grayColors;
    private boolean hasAnswer;
    private View headerContentView;
    private String identity_id;
    private WebView img_webview;
    private ImageView imgtitle;
    private boolean isLoadMore;
    private boolean isPinned;
    private boolean isSdkAd;
    private String is_show_number;
    private boolean ishavehot;
    private LinearLayout line_viewok;
    private LinearLayout lineout;
    private LinearLayout lineselect2;
    private QuestionAdWidegt llAd;
    private LinearLayout llNoComment;
    private LinearLayout llViewComment;
    private LinearLayout ll_analyze_enter;
    private LinearLayout ll_answer_analysis_layout;
    private LinearLayout ll_answer_enter;
    private LinearLayout ll_encyclopedia_analysis_layout;
    private LinearLayout ll_more_columns;
    private RelativeLayout ll_question_comment;
    private LinearLayout ll_restore_enter;
    private LinearLayout ll_restore_point;
    private LinearLayout ly_questiondetails_btn_centerMsg;
    private LinearLayout ly_questiondetails_btn_collect;
    private LinearLayout ly_questiondetails_btn_edit;
    private LinearLayout ly_questiondetails_btn_zantong;
    private ActivityResultLauncher<Intent> mActivityResultLauncher;
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
    private RoundedImageView mImgVideo;
    private View mLineEmpty;
    private View mLineExpline;
    private View mLineRestore;
    private RelativeLayout mLyAdView;
    private LinearLayout mLyAnswerPraise;
    private RelativeLayout mLyAnswerUserInfo;
    private LinearLayout mLyPraise;
    private LinearLayout mLyRestorePraise;
    private RelativeLayout mLyRestoreUserInfo;
    private RelativeLayout mLyUserInfo;
    private RelativeLayout mLyVideoView;
    private CommentSectionListView mPinnedSecListView;
    private LinearLayout mRadioGroup_content;
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
    private TextView nitv;
    private boolean notInitCommentFlag;
    private TextView pagenumtv;
    private RelativeLayout qbrel;
    private String questionId;
    private QuestionDetailBean questionInfoBean;
    private ImageView questiondetails_btn_centerMsg;
    private ImageView questiondetails_btn_collect;
    private TextView questiondetails_btn_commentNum;
    private ImageView questiondetails_btn_edit;
    private TextView questiondetails_btn_pushAnswer;
    private ImageView questiondetails_btn_zantong;
    private LinearLayout questiondetails_layout_diff;
    private TextView questiondetails_tv_content_ques;
    private TextView questiondetails_tv_content_ques1;
    private TextView questiondetails_tv_outline;
    private TextView questiondetails_tv_statistics;
    private RecyclerView recyansitem;
    private CheckedTextView remen;
    private RelativeLayout rlAnswerUser;
    private RelativeLayout rl_analyze_user;
    private RelativeLayout rl_restore_user;
    private LinearLayout schildline;
    private SmartRefreshLayout smartRefreshLayout;
    private TextView sourcetv;
    String start_timestamp;
    private TextView textView_difficulty;
    private TextView titletv;
    private String total;
    private int totalNewCommentNum;
    private TextView tvAnswerAnalysis;
    private TextView tvAnswerEdit;
    private TextView tvAnswerToEdit;
    private TextView tvEditTime;
    private TextView tv_analyze_edit;
    private TextView tv_analyze_reword;
    private TextView tv_analyze_to_edit;
    private TextView tv_answer_analyze;
    private TextView tv_correction;
    private TextView tv_question_new;
    private TextView tv_restore_edit;
    private TextView tv_restore_reword;
    private TextView tv_restore_to_edit;
    private TextView tv_statistics;
    private TextView typeStr;
    private ShowVideoDialog videoMainDialog;
    private View view_line_analyze;
    private android.webkit.WebView web;
    private CheckedTextView zuixin;
    private TextView zuocuol;
    private TextView zuoduil;
    private final List<AnalysisBean.DataBean> dataAnaList = new ArrayList();
    private String module_type = "1";
    private String show_restore_img = "1";
    private final List<Integer> positionList = new ArrayList();
    private String externalsources = "";
    private boolean isClickAudio = true;
    private boolean isInit = false;
    private List<CommentBean.DataBean.HotBean> hot = new ArrayList();
    private List<CommentBean.DataBean.HotBean> time_line = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> commlist = new ArrayList();
    private int pageNum = 0;
    private boolean isCanLoadData = true;
    private boolean isLoadCommentData = false;
    private int positionTab = 0;
    private long question_id = 0;
    private boolean hasConfigAd = false;
    private final SparseArray<ItemRecord> recordSp = new SparseArray<>(0);
    private String mTempParentId = "";
    private long startTime = 0;
    String logImg = "";
    private final View.OnClickListener collectClickListener = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.m5
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) throws JSONException {
            this.f13288c.lambda$new$23(view);
        }
    };
    private boolean currentPageIsVisible = false;
    boolean isFragmentVisible = false;
    boolean isAnswerVisible = false;

    /* renamed from: com.psychiatrygarden.activity.online.fragment.SubSubjectiveQuestionFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends SimpleMultiPurposeListener {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStateChanged$0() {
            SubSubjectiveQuestionFragment.this.headerContentView.findViewById(R.id.rl_top_title).setVisibility(0);
        }

        @Override // com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener, com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener
        public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
            super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight);
            SubSubjectiveQuestionFragment.this.headerContentView.findViewById(R.id.rl_top_title).setVisibility(4);
        }

        @Override // com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener, com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            if (SubSubjectiveQuestionFragment.this.isCanLoadData) {
                SubSubjectiveQuestionFragment.this.isLoadMore = true;
                SubSubjectiveQuestionFragment.this.isCanLoadData = false;
                SubSubjectiveQuestionFragment.access$208(SubSubjectiveQuestionFragment.this);
                SubSubjectiveQuestionFragment.this.getCommentListData();
            }
        }

        @Override // com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener, com.scwang.smartrefresh.layout.listener.OnRefreshListener
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            SubSubjectiveQuestionFragment.this.pageNum = 1;
            SubSubjectiveQuestionFragment.this.isLoadMore = false;
            SubSubjectiveQuestionFragment.this.getCommentListData();
            SubSubjectiveQuestionFragment.this.updateBottomView();
        }

        @Override // com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener, com.scwang.smartrefresh.layout.listener.OnStateChangedListener
        public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
            if (newState == RefreshState.RefreshReleased || newState == RefreshState.PullDownCanceled || newState == RefreshState.None) {
                SubSubjectiveQuestionFragment.this.smartRefreshLayout.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.u5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f13364c.lambda$onStateChanged$0();
                    }
                }, 0L);
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.online.fragment.SubSubjectiveQuestionFragment$3, reason: invalid class name */
    public class AnonymousClass3 extends AjaxCallBack<String> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(int i2) {
            SubSubjectiveQuestionFragment.this.mPinnedSecListView.setSelection(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1() {
            SubSubjectiveQuestionFragment.this.mCommListAdapter.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$2() {
            SubSubjectiveQuestionFragment.this.mCommListAdapter.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$3(String str) {
            CommentBean commentBean = (CommentBean) new Gson().fromJson(str, CommentBean.class);
            if (!commentBean.getCode().equals("200")) {
                SubSubjectiveQuestionFragment.this.AlertToast(commentBean.getMessage());
                if (SubSubjectiveQuestionFragment.this.isLoadMore) {
                    SubSubjectiveQuestionFragment.this.smartRefreshLayout.finishLoadMore(false);
                    return;
                } else {
                    SubSubjectiveQuestionFragment.this.smartRefreshLayout.finishRefresh(false);
                    return;
                }
            }
            SubSubjectiveQuestionFragment.this.llViewComment.setVisibility(8);
            SubSubjectiveQuestionFragment.this.llNoComment.setVisibility(8);
            SubSubjectiveQuestionFragment.this.hot = commentBean.getData().getHot();
            SubSubjectiveQuestionFragment.this.time_line = commentBean.getData().getTime_line();
            if (SubSubjectiveQuestionFragment.this.pageNum != 1) {
                SubSubjectiveQuestionFragment.this.smartRefreshLayout.finishLoadMore(true);
                if (SubSubjectiveQuestionFragment.this.time_line.size() == 0) {
                    SubSubjectiveQuestionFragment.this.AlertToast("已经是最后一条");
                    SubSubjectiveQuestionFragment.access$220(SubSubjectiveQuestionFragment.this, 1);
                    SubSubjectiveQuestionFragment.this.smartRefreshLayout.finishLoadMoreWithNoMoreData();
                    return;
                } else {
                    SubSubjectiveQuestionFragment.this.commlist.addAll(SubSubjectiveQuestionFragment.this.time_line);
                    SubSubjectiveQuestionFragment.this.mCommListAdapter.setRefeault(SubSubjectiveQuestionFragment.this.time_line);
                    SubSubjectiveQuestionFragment.this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.y5
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f13401c.lambda$onSuccess$2();
                        }
                    });
                    return;
                }
            }
            if (SubSubjectiveQuestionFragment.this.isLoadMore) {
                SubSubjectiveQuestionFragment.this.smartRefreshLayout.finishLoadMore(true);
            } else {
                SubSubjectiveQuestionFragment.this.smartRefreshLayout.finishRefresh(true);
            }
            SubSubjectiveQuestionFragment subSubjectiveQuestionFragment = SubSubjectiveQuestionFragment.this;
            subSubjectiveQuestionFragment.positionTab = subSubjectiveQuestionFragment.hot.size();
            SubSubjectiveQuestionFragment.this.commlist.clear();
            if (SubSubjectiveQuestionFragment.this.hot == null) {
                SubSubjectiveQuestionFragment.this.hot = new ArrayList(0);
            }
            if (SubSubjectiveQuestionFragment.this.hot.size() <= 0 || !SubSubjectiveQuestionFragment.this.hasAnswer) {
                SubSubjectiveQuestionFragment.this.lineselect2.setVisibility(8);
            } else {
                Iterator it = SubSubjectiveQuestionFragment.this.hot.iterator();
                while (it.hasNext()) {
                    ((CommentBean.DataBean.HotBean) it.next()).setHot(true);
                }
                CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                hotBean.setType(1);
                hotBean.setName("最热评论(" + commentBean.getData().getHot_total() + ")");
                SubSubjectiveQuestionFragment.this.commlist.add(hotBean);
                if (SubSubjectiveQuestionFragment.this.isSdkAd) {
                    CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
                    hotBean2.setHot(true);
                    if (SubSubjectiveQuestionFragment.this.hot.size() == 8) {
                        SubSubjectiveQuestionFragment.this.hot.add(hotBean2);
                    } else if (SubSubjectiveQuestionFragment.this.hot.size() >= 9) {
                        SubSubjectiveQuestionFragment.this.hot.add(8, hotBean2);
                    }
                }
                SubSubjectiveQuestionFragment.this.commlist.addAll(SubSubjectiveQuestionFragment.this.hot);
                SubSubjectiveQuestionFragment.this.ishavehot = true;
                if (commentBean.getData().getMore_hot() != null && "1".equals(commentBean.getData().getMore_hot())) {
                    CommentBean.DataBean.HotBean hotBean3 = new CommentBean.DataBean.HotBean();
                    hotBean3.setOtherView(3);
                    hotBean3.setName("最热评论(" + commentBean.getData().getHot_total() + ")");
                    SubSubjectiveQuestionFragment.this.commlist.add(hotBean3);
                }
            }
            if (SubSubjectiveQuestionFragment.this.time_line == null) {
                SubSubjectiveQuestionFragment.this.time_line = new ArrayList(0);
            }
            if (SubSubjectiveQuestionFragment.this.time_line.size() > 0 && SubSubjectiveQuestionFragment.this.hasAnswer) {
                CommentBean.DataBean.HotBean hotBean4 = new CommentBean.DataBean.HotBean();
                hotBean4.setType(1);
                StringBuilder sb = new StringBuilder();
                sb.append("最新评论(");
                sb.append((SubSubjectiveQuestionFragment.this.time_line == null || SubSubjectiveQuestionFragment.this.time_line.isEmpty()) ? "0" : commentBean.getData().getTime_line_total());
                sb.append(")");
                hotBean4.setName(sb.toString());
                SubSubjectiveQuestionFragment.this.commlist.add(hotBean4);
                SubSubjectiveQuestionFragment.this.commlist.addAll(SubSubjectiveQuestionFragment.this.time_line);
            }
            SubSubjectiveQuestionFragment.this.mCommListAdapter.setIsSdkAd(SubSubjectiveQuestionFragment.this.isSdkAd);
            SubSubjectiveQuestionFragment.this.mCommListAdapter.setShowAd(SubSubjectiveQuestionFragment.this.hot.size() >= 8);
            SubSubjectiveQuestionFragment.this.mCommListAdapter.setList(SubSubjectiveQuestionFragment.this.commlist);
            SubSubjectiveQuestionFragment.this.mCommListAdapter.setRefeault(SubSubjectiveQuestionFragment.this.time_line);
            SubSubjectiveQuestionFragment subSubjectiveQuestionFragment2 = SubSubjectiveQuestionFragment.this;
            subSubjectiveQuestionFragment2.totalNewCommentNum = subSubjectiveQuestionFragment2.time_line.size();
            if (SubSubjectiveQuestionFragment.this.isLoadCommentData || SubSubjectiveQuestionFragment.this.notInitCommentFlag) {
                final int i2 = (SubSubjectiveQuestionFragment.this.notInitCommentFlag && SubSubjectiveQuestionFragment.this.ishavehot) ? 2 : 1;
                if (SubSubjectiveQuestionFragment.this.notInitCommentFlag) {
                    SubSubjectiveQuestionFragment.this.notInitCommentFlag = false;
                }
                SubSubjectiveQuestionFragment.this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.w5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f13383c.lambda$onSuccess$0(i2);
                    }
                }, 310L);
                SubSubjectiveQuestionFragment.this.mPinnedSecListView.smoothScrollToPositionFromTop(i2, 0, 300);
                EventBus.getDefault().post(new HideChapterTitleEvent(SubSubjectiveQuestionFragment.this.commlist.size() > 8, SubSubjectiveQuestionFragment.this.question_id + ""));
                if (SubSubjectiveQuestionFragment.this.commlist.size() >= 8) {
                    EventBus.getDefault().post(new HideChapterTitleEvent(true, SubSubjectiveQuestionFragment.this.question_id + ""));
                }
                if (SubSubjectiveQuestionFragment.this.ishavehot) {
                    SubSubjectiveQuestionFragment.this.lineselect2.setVisibility(0);
                } else {
                    SubSubjectiveQuestionFragment.this.lineselect2.setVisibility(8);
                }
            }
            if (SubSubjectiveQuestionFragment.this.isSdkAd) {
                return;
            }
            if (SubSubjectiveQuestionFragment.this.hot.size() >= 8) {
                SubSubjectiveQuestionFragment.this.getCommentAd();
            } else {
                SubSubjectiveQuestionFragment.this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.x5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f13391c.lambda$onSuccess$1();
                    }
                });
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            SubSubjectiveQuestionFragment.this.isCanLoadData = true;
            if (SubSubjectiveQuestionFragment.this.pageNum == 1) {
                if (SubSubjectiveQuestionFragment.this.commlist.size() > 0) {
                    SubSubjectiveQuestionFragment.this.AlertToast("请求服务器失败");
                    return;
                } else if (!SubSubjectiveQuestionFragment.this.isLoadMore) {
                    SubSubjectiveQuestionFragment.this.smartRefreshLayout.finishRefresh(false);
                }
            }
            if (SubSubjectiveQuestionFragment.this.pageNum > 1) {
                SubSubjectiveQuestionFragment.this.smartRefreshLayout.finishLoadMore(false);
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(final String s2) {
            super.onSuccess((AnonymousClass3) s2);
            try {
                if (SubSubjectiveQuestionFragment.this.getActivity() != null) {
                    SubSubjectiveQuestionFragment.this.getActivity().runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.v5
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f13373c.lambda$onSuccess$3(s2);
                        }
                    });
                }
            } catch (Exception e2) {
                if (SubSubjectiveQuestionFragment.this.isLoadMore) {
                    SubSubjectiveQuestionFragment.this.smartRefreshLayout.finishLoadMore(false);
                } else {
                    SubSubjectiveQuestionFragment.this.smartRefreshLayout.finishRefresh(false);
                }
                e2.printStackTrace();
            }
            SubSubjectiveQuestionFragment.this.isCanLoadData = true;
        }
    }

    /* renamed from: com.psychiatrygarden.activity.online.fragment.SubSubjectiveQuestionFragment$4, reason: invalid class name */
    public class AnonymousClass4 extends AjaxCallBack<String> {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            SubSubjectiveQuestionFragment.this.mCommListAdapter.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(JSONObject jSONObject) {
            JSONObject jSONObjectOptJSONObject;
            if (!jSONObject.optString("code").equals("200") || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null || jSONObjectOptJSONObject.length() == 0) {
                return;
            }
            if ((SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, ((BaseFragment) SubSubjectiveQuestionFragment.this).mContext, 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, ((BaseFragment) SubSubjectiveQuestionFragment.this).mContext, 0L).longValue()) / 1000) - jSONObjectOptJSONObject.optLong("time_interval", 0L) : 0L) < 0 || TextUtils.isEmpty(jSONObjectOptJSONObject.optString("img"))) {
                return;
            }
            CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
            hotBean.setAds(jSONObject.optString("data"));
            if (SubSubjectiveQuestionFragment.this.hot.size() >= 8) {
                hotBean.setHot(true);
                SubSubjectiveQuestionFragment.this.commlist.add(9, hotBean);
                SubSubjectiveQuestionFragment.this.hasConfigAd = true;
                SubSubjectiveQuestionFragment.this.mCommListAdapter.setShowAd(SubSubjectiveQuestionFragment.this.hot.size() >= 8 && SubSubjectiveQuestionFragment.this.hasConfigAd);
                SubSubjectiveQuestionFragment.this.mCommListAdapter.setList(SubSubjectiveQuestionFragment.this.commlist);
                SubSubjectiveQuestionFragment.this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.a6
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f13184c.lambda$onSuccess$0();
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
                if (SubSubjectiveQuestionFragment.this.getActivity() != null) {
                    final JSONObject jSONObject = new JSONObject(json);
                    SubSubjectiveQuestionFragment.this.getActivity().runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.z5
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f13408c.lambda$onSuccess$1(jSONObject);
                        }
                    });
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static class ItemRecord {
        int height = 0;

        /* renamed from: top, reason: collision with root package name */
        int f13171top = 0;
    }

    public class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(android.webkit.WebView view, String url) {
            super.onPageFinished(view, url);
            SubSubjectiveQuestionFragment.this.initSubViewData();
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        public /* synthetic */ MyWebViewClient(SubSubjectiveQuestionFragment subSubjectiveQuestionFragment, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public static /* synthetic */ int access$208(SubSubjectiveQuestionFragment subSubjectiveQuestionFragment) {
        int i2 = subSubjectiveQuestionFragment.pageNum;
        subSubjectiveQuestionFragment.pageNum = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$220(SubSubjectiveQuestionFragment subSubjectiveQuestionFragment, int i2) {
        int i3 = subSubjectiveQuestionFragment.pageNum - i2;
        subSubjectiveQuestionFragment.pageNum = i3;
        return i3;
    }

    private void addAnalyzeUserView() {
        ViewGroup viewGroup;
        int i2;
        try {
            RelativeLayout relativeLayout = this.rl_analyze_user;
            if (relativeLayout != null) {
                relativeLayout.removeAllViews();
            }
            RelativeLayout relativeLayout2 = this.rlAnswerUser;
            if (relativeLayout2 != null) {
                relativeLayout2.removeAllViews();
            }
            if (TextUtils.isEmpty(this.questionInfoBean.getStatData().getExplain_correction_tips())) {
                this.mTvAnswerRecord.setVisibility(8);
            }
            if (this.questionInfoBean.getStatData().getExplain_user() == null || TextUtils.isEmpty(this.questionInfoBean.getStatData().getExplain_user().getUser_id())) {
                this.mLyAnswerUserInfo.setVisibility(8);
            } else {
                this.mLyAnswerUserInfo.setVisibility(0);
                this.mTvAnswerUserNickName.setText(this.questionInfoBean.getStatData().getExplain_user().getNickname());
                this.mTvAnswerUserUpdateTime.setText(this.questionInfoBean.getStatData().getExplain_user().getCtime());
                this.mTvAnswerPraiseCount.setText(this.questionInfoBean.getStatData().getExplain_user().getPraise_num());
                boolean z2 = SkinManager.getCurrentSkinType(this.mContext) == 1;
                if (TextUtils.isEmpty(this.questionInfoBean.getStatData().getExplain_praise()) || !this.questionInfoBean.getStatData().getExplain_praise().equals("1")) {
                    this.mImgAnswerPraise.setImageResource(z2 ? R.drawable.dianzancourse_night : R.drawable.dianzancourse);
                } else {
                    this.mImgAnswerPraise.setImageResource(z2 ? R.drawable.youdianzan_night : R.drawable.youdianzan);
                }
                Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(this.questionInfoBean.getStatData().getExplain_user().getAvatar())).placeholder(R.mipmap.ic_avatar_def).into(this.mImgAnswerUserHead);
            }
            if (!TextUtils.isEmpty(this.questionInfoBean.getExplain()) && !TextUtils.isEmpty(this.questionInfoBean.getExplain_img())) {
                this.mLyAnswerUserInfo.setVisibility(8);
            }
            int i3 = 0;
            while (true) {
                int size = this.questionInfoBean.getStatData().getExplain_correction_avatar().size();
                viewGroup = null;
                i2 = R.layout.item_restore_userhead;
                if (i3 >= size) {
                    break;
                }
                View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.item_restore_userhead, (ViewGroup) null);
                CircleImageView circleImageView = (CircleImageView) viewInflate.findViewById(R.id.civ_restore_userhead);
                ((RelativeLayout.LayoutParams) circleImageView.getLayoutParams()).setMargins(ScreenUtil.getPxByDp(this.mContext, 18) * i3, 0, 0, 0);
                Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(this.questionInfoBean.getStatData().getExplain_correction_avatar().get(i3))).placeholder(new ColorDrawable(ContextCompat.getColor(circleImageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(circleImageView);
                this.rlAnswerUser.addView(viewInflate);
                i3++;
            }
            boolean z3 = this.questionInfoBean.getStatData().getExplain_correction_avatar().size() > 0;
            this.rlAnswerUser.setVisibility(z3 ? 0 : 4);
            this.tvAnswerEdit.setVisibility(z3 ? 0 : 8);
            this.tvAnswerToEdit.setVisibility(z3 ? 8 : 0);
            if (z3) {
                this.tvAnswerEdit.setText(String.format(Locale.CHINA, "%d人编辑", Integer.valueOf(this.questionInfoBean.getStatData().getExplain_correction_number())));
            }
            this.mTvAnswerRecord.setText(this.questionInfoBean.getStatData().getExplain_correction_tips());
            if (TextUtils.isEmpty(this.questionInfoBean.getStatData().getOption_analysis_correction_tips())) {
                this.tv_analyze_reword.setVisibility(8);
            }
            if (this.questionInfoBean.getStatData().getOption_analysis_user() == null || TextUtils.isEmpty(this.questionInfoBean.getStatData().getOption_analysis_user().getUser_id())) {
                this.mLyUserInfo.setVisibility(8);
            } else {
                this.mLyUserInfo.setVisibility(0);
                this.mTvUserNickName.setText(this.questionInfoBean.getStatData().getOption_analysis_user().getNickname());
                this.mTvUserUpdateTime.setText(this.questionInfoBean.getStatData().getOption_analysis_user().getCtime());
                this.mTvPraiseCount.setText(this.questionInfoBean.getStatData().getOption_analysis_user().getPraise_num());
                boolean z4 = SkinManager.getCurrentSkinType(this.mContext) == 1;
                if (TextUtils.isEmpty(this.questionInfoBean.getStatData().getOption_analysis_praise()) || !this.questionInfoBean.getStatData().getOption_analysis_praise().equals("1")) {
                    this.mImgPraise.setImageResource(z4 ? R.drawable.dianzancourse_night : R.drawable.dianzancourse);
                } else {
                    this.mImgPraise.setImageResource(z4 ? R.drawable.youdianzan_night : R.drawable.youdianzan);
                }
                Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(this.questionInfoBean.getStatData().getOption_analysis_user().getAvatar())).placeholder(R.mipmap.ic_avatar_def).into(this.mImgUserHead);
            }
            if (!TextUtils.isEmpty(this.questionInfoBean.getOption_analysis()) && !TextUtils.isEmpty(this.questionInfoBean.getOption_analysis_img())) {
                this.mLyUserInfo.setVisibility(8);
            }
            int i4 = 0;
            while (i4 < this.questionInfoBean.getStatData().getOption_analysis_correction_avatar().size()) {
                View viewInflate2 = View.inflate(this.mContext, i2, viewGroup);
                CircleImageView circleImageView2 = (CircleImageView) viewInflate2.findViewById(R.id.civ_restore_userhead);
                ((RelativeLayout.LayoutParams) circleImageView2.getLayoutParams()).setMargins(ScreenUtil.getPxByDp(this.mContext, 18) * i4, 0, 0, 0);
                Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(this.questionInfoBean.getStatData().getOption_analysis_correction_avatar().get(i4))).placeholder(new ColorDrawable(ContextCompat.getColor(circleImageView2.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(circleImageView2);
                this.rl_analyze_user.addView(viewInflate2);
                i4++;
                viewGroup = null;
                i2 = R.layout.item_restore_userhead;
            }
            if (this.questionInfoBean.getStatData().getOption_analysis_correction_avatar().size() > 0) {
                this.tv_analyze_edit.setText(String.format(Locale.CHINA, "%d人编辑", Integer.valueOf(this.questionInfoBean.getStatData().getOption_analysis_correction_number())));
                this.rl_analyze_user.setVisibility(0);
                this.tv_analyze_edit.setVisibility(0);
                this.tv_analyze_to_edit.setVisibility(8);
            } else {
                this.rl_analyze_user.setVisibility(4);
                this.tv_analyze_edit.setVisibility(8);
                this.tv_analyze_to_edit.setVisibility(0);
            }
            this.tv_analyze_reword.setText(this.questionInfoBean.getStatData().getOption_analysis_correction_tips());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void addRestoreUserView() {
        try {
            RelativeLayout relativeLayout = this.rl_restore_user;
            if (relativeLayout != null) {
                relativeLayout.removeAllViews();
            }
            if (TextUtils.isEmpty(this.questionInfoBean.getStatData().getRestore_correction_tips())) {
                this.tv_restore_reword.setVisibility(8);
            }
            if (TextUtils.isEmpty(this.questionInfoBean.getStatData().getRestore_user().getId())) {
                this.mLyRestoreUserInfo.setVisibility(8);
            } else {
                this.mLyRestoreUserInfo.setVisibility(0);
                this.mTvRestoreUserNickName.setText(this.questionInfoBean.getStatData().getRestore_user().getNickname());
                this.mTvRestoreUserUpdateTime.setText(this.questionInfoBean.getStatData().getRestore_user().getCtime());
                this.mTvRestorePraiseCount.setText(this.questionInfoBean.getStatData().getRestore_user().getPraise_num());
                boolean z2 = SkinManager.getCurrentSkinType(this.mContext) == 1;
                if (TextUtils.isEmpty(this.questionInfoBean.getStatData().getRestore_praise()) || !this.questionInfoBean.getStatData().getRestore_praise().equals("1")) {
                    this.mImgRestorePraise.setImageResource(z2 ? R.drawable.dianzancourse_night : R.drawable.dianzancourse);
                } else {
                    this.mImgRestorePraise.setImageResource(z2 ? R.drawable.youdianzan_night : R.drawable.youdianzan);
                }
                Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(this.questionInfoBean.getStatData().getRestore_user().getAvatar())).placeholder(R.mipmap.ic_avatar_def).into(this.mImgRestoreUserHead);
            }
            if (!TextUtils.isEmpty(this.questionInfoBean.getRestore()) && !TextUtils.isEmpty(this.questionInfoBean.getRestore_img())) {
                this.mLyRestoreUserInfo.setVisibility(8);
            }
            for (int i2 = 0; i2 < this.questionInfoBean.getStatData().getRestore_correction_avatar().size(); i2++) {
                View viewInflate = View.inflate(this.mContext, R.layout.item_restore_userhead, null);
                CircleImageView circleImageView = (CircleImageView) viewInflate.findViewById(R.id.civ_restore_userhead);
                ((RelativeLayout.LayoutParams) circleImageView.getLayoutParams()).setMargins(ScreenUtil.getPxByDp(this.mContext, 18) * i2, 0, 0, 0);
                Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(this.questionInfoBean.getStatData().getRestore_correction_avatar().get(i2))).placeholder(new ColorDrawable(ContextCompat.getColor(circleImageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(circleImageView);
                this.rl_restore_user.addView(viewInflate);
            }
            if (this.questionInfoBean.getStatData().getRestore_correction_avatar().size() > 0) {
                this.tv_restore_edit.setText(String.format(Locale.CHINA, "%d人编辑", Integer.valueOf(this.questionInfoBean.getStatData().getRestore_correction_number())));
                this.rl_restore_user.setVisibility(0);
                this.tv_restore_edit.setVisibility(0);
                this.tv_restore_to_edit.setVisibility(8);
            } else {
                this.rl_restore_user.setVisibility(4);
                this.tv_restore_edit.setVisibility(8);
                this.tv_restore_to_edit.setVisibility(0);
            }
            this.tv_restore_reword.setText(this.questionInfoBean.getStatData().getRestore_correction_tips());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void analysisPraiseAction(String id, final String action, final String type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", id);
        ajaxParams.put("action", action);
        ajaxParams.put("type", type);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.analysisPraise, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.fragment.SubSubjectiveQuestionFragment.8
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
                throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.fragment.SubSubjectiveQuestionFragment.AnonymousClass8.onSuccess(java.lang.String):void");
            }
        });
    }

    private void doAnswing() {
        this.questiondetails_btn_pushAnswer.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.ver_fadout));
        this.questiondetails_btn_pushAnswer.setVisibility(8);
        this.line_viewok.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.ver_fadint));
        this.line_viewok.setVisibility(0);
        setViewVisiable(0);
        this.qbrel.setVisibility(8);
        this.schildline.setVisibility(8);
    }

    private void getAudioInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.questionInfoBean.getId());
        ajaxParams.put("resource_type", "2");
        ajaxParams.put("video_id", this.questionInfoBean.getStem_audio_list().get(0).getVideo_id());
        QuestionDataRequest.getIntance(getActivity()).getMeidaSourceById(ajaxParams, "2", this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentAd() {
        if (this.hasAnswer) {
            YJYHttpUtils.get(this.mContext, NetworkRequestsURL.adsInCommentApi, new AjaxParams(), new AnonymousClass4());
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
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mCommentList, ajaxParams, new AnonymousClass3());
    }

    public static SubSubjectiveQuestionFragment getInstance(Bundle args) {
        SubSubjectiveQuestionFragment subSubjectiveQuestionFragment = new SubSubjectiveQuestionFragment();
        subSubjectiveQuestionFragment.setArguments(args);
        return subSubjectiveQuestionFragment;
    }

    private int getNewPositions() {
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
        return i4 - itemRecord.f13171top;
    }

    private boolean hasAnswerAnalysis() {
        QuestionDetailBean questionDetailBean = this.questionInfoBean;
        return (questionDetailBean == null || TextUtils.isEmpty(questionDetailBean.getExplain()) || TextUtils.isEmpty(this.questionInfoBean.getExplain_img())) ? false : true;
    }

    private boolean hasRestore() {
        QuestionDetailBean questionDetailBean = this.questionInfoBean;
        return (questionDetailBean == null || TextUtils.isEmpty(questionDetailBean.getRestore()) || TextUtils.isEmpty(this.questionInfoBean.getRestore_img())) ? false : true;
    }

    private void initAd(final Context mContext) {
        final HomepageSmallAdBean.DataDTO.DataAd dataAd = (HomepageSmallAdBean.DataDTO.DataAd) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.QUESTION_QUESTION_AD, mContext, ""), HomepageSmallAdBean.DataDTO.DataAd.class);
        if (dataAd == null || dataAd.getAds().isEmpty()) {
            this.mLyAdView.setVisibility(8);
            return;
        }
        if ((SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_BOTTOM_AD, mContext, 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_BOTTOM_AD, mContext, 0L).longValue()) / 1000) - dataAd.getTime_interval() : 0L) < 0) {
            this.mLyAdView.setVisibility(8);
            this.mImgCloseAd.setVisibility(8);
            return;
        }
        this.mLyAdView.setVisibility(0);
        if (dataAd.getForce().equals("1")) {
            this.mImgCloseAd.setVisibility(8);
        } else {
            this.mImgCloseAd.setVisibility(0);
            this.mImgCloseAd.setImageResource(R.mipmap.ic_close_ad);
            this.mImgCloseAd.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.w4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13381c.lambda$initAd$35(mContext, view);
                }
            });
        }
        if (dataAd.getAds() == null || dataAd.getAds().size() <= 0) {
            return;
        }
        GlideApp.with(mContext).load((Object) GlideUtils.generateUrl(dataAd.getAds().get(0).getImg())).placeholder(R.mipmap.ic_order_default).into(this.mImgAd);
        this.mImgAd.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.x4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubSubjectiveQuestionFragment.lambda$initAd$36(dataAd, view);
            }
        });
    }

    private void initAudioInfo(String vid, String duration) {
        if (this.mAudioPlayerView == null) {
            this.mAudioPlayerView = new CustomAliPlayerView(this.mContext);
        }
        this.mAudioPlayerView.setIsVideo(false);
        this.mAudioPlayerView.initView(getContext(), vid, UserConfig.isCanPlayBy4g(this.mContext));
        this.mAudioPlayerView.setSeeDuration(duration);
        this.mAudioPlayerView.setExpire_str("");
        this.mAudioPlayerView.setWatch_permission("1");
        this.mAudioPlayerView.setVids(vid);
        CommonUtil.mPlayerData(vid, this.mAudioPlayerView, false);
        this.mAudioPlayerView.setOnPlayStatusLisenter(new CustomAliPlayerView.OnAudioPlayStatusListenter() { // from class: com.psychiatrygarden.activity.online.fragment.SubSubjectiveQuestionFragment.6
            @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.OnAudioPlayStatusListenter
            public void onPlayEnd() {
                if (SubSubjectiveQuestionFragment.this.animationDrawable != null) {
                    SubSubjectiveQuestionFragment.this.animationDrawable.stop();
                    if (SkinManager.getCurrentSkinType(SubSubjectiveQuestionFragment.this.getActivity()) == 0) {
                        SubSubjectiveQuestionFragment.this.mImgAudio.setImageResource(R.drawable.ic_audio_play_end);
                    } else {
                        SubSubjectiveQuestionFragment.this.mImgAudio.setImageResource(R.drawable.ic_audio_play_end_night);
                    }
                }
            }

            @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.OnAudioPlayStatusListenter
            public void onPlaying() {
                if (SubSubjectiveQuestionFragment.this.isClickAudio) {
                    if (SkinManager.getCurrentSkinType(SubSubjectiveQuestionFragment.this.getActivity()) == 0) {
                        SubSubjectiveQuestionFragment.this.mImgAudio.setImageResource(R.drawable.audio_play_anim);
                    } else {
                        SubSubjectiveQuestionFragment.this.mImgAudio.setImageResource(R.drawable.audio_play_anim_night);
                    }
                    SubSubjectiveQuestionFragment subSubjectiveQuestionFragment = SubSubjectiveQuestionFragment.this;
                    subSubjectiveQuestionFragment.animationDrawable = (AnimationDrawable) subSubjectiveQuestionFragment.mImgAudio.getDrawable();
                    SubSubjectiveQuestionFragment.this.animationDrawable.start();
                }
            }
        });
    }

    private void initListView(ViewHolder holder) {
        this.lineselect2 = (LinearLayout) holder.get(R.id.lineselect2);
        this.remen = (CheckedTextView) holder.get(R.id.remen2);
        this.zuixin = (CheckedTextView) holder.get(R.id.zuixin2);
        isSelect(true, false);
        this.mPinnedSecListView.setScrollingCacheEnabled(false);
        this.mPinnedSecListView.setTag(this.question_id + "");
        setListener();
        this.mPinnedSecListView.addHeaderView(this.headerContentView);
        QuestionCommentListAdapter questionCommentListAdapter = new QuestionCommentListAdapter(this, this.mContext, this.commlist, this.time_line, 1, "2", this.question_id + "", (AnswerQuestionActivity) getActivity(), true, true);
        this.mCommListAdapter = questionCommentListAdapter;
        this.mPinnedSecListView.setAdapter((ListAdapter) questionCommentListAdapter);
        this.mCommListAdapter.setActionListener(new QuestionCommentListAdapter.OnCommentActionListener() { // from class: com.psychiatrygarden.activity.online.fragment.e5
            @Override // com.psychiatrygarden.adapter.QuestionCommentListAdapter.OnCommentActionListener
            public final void onPraiseAction(boolean z2) {
                this.f13219a.lambda$initListView$1(z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void isSelect(boolean renmenTrue, boolean zuixinTrue) {
        this.remen.setChecked(renmenTrue);
        this.zuixin.setChecked(zuixinTrue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$24(String str) {
        Intent intent = new Intent(this.mContext, (Class<?>) NoteNewActivity.class);
        intent.putExtra("question_id", this.questionInfoBean.getId());
        intent.putExtra("notestr", str);
        intent.putExtra("module_type", "" + this.module_type);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRestoreStr$9(ArrayList arrayList, int i2) {
        CommonUtil.doPicture(getActivity(), arrayList, i2, null, R.drawable.kaodianhuanyuan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAd$35(Context context, View view) {
        SharePreferencesUtils.writeLongConfig(CommonParameter.DISMESS_TIME_QUESTION_BOTTOM_AD, Long.valueOf(System.currentTimeMillis()), context);
        this.mLyAdView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initAd$36(HomepageSmallAdBean.DataDTO.DataAd dataAd, View view) {
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
    public /* synthetic */ void lambda$initBiaoQianData$27(List list, View view) {
        showlog(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBiaoQianData$28(List list, View view) {
        showlog(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListView$1(boolean z2) {
        updateBottomView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$10(View view) {
        showlog(this.questionInfoBean.getDataBiao());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$11(View view) {
        goToAnalysis();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$12(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.remen.performClick();
        if (getActivity() instanceof AnswerQuestionActivity) {
            ((AnswerQuestionActivity) getActivity()).hideTitleView(String.valueOf(this.question_id));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$13(View view) throws JSONException {
        doClickSimeThings("1");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$14(View view) throws JSONException {
        doClickSimeThings("0");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$15(View view) {
        this.remen.performClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$16(QuestionNoteBean questionNoteBean) {
        this.questionInfoBean.getStatData().setIs_note(1);
        haveNoteimg();
        this.questionInfoBean.setNote(questionNoteBean.getContent());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$17(View view) {
        if (this.questionInfoBean.getStatData() == null) {
            AlertToast("原题加载中,请稍后再试！");
            return;
        }
        if (this.questionInfoBean.getStatData().getIs_note() != 0) {
            new XPopup.Builder(getActivity()).moveUpToKeyboard(Boolean.FALSE).enableDrag(true).asCustom(new PopNoteList(this.mContext, this.module_type, this.questionInfoBean.getId())).show();
            return;
        }
        new DialogNoteInput(getContext(), this.module_type, this.questionInfoBean.getId() + "", new onDialogNoteListener() { // from class: com.psychiatrygarden.activity.online.fragment.n4
            @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
            public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                this.f13295a.lambda$initQuestionType$16(questionNoteBean);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$18(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) QuestionCorrectionActivity.class);
        intent.putExtra("question_id", this.questionInfoBean.getId());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$19(View view) {
        if (TextUtils.isEmpty(this.questionInfoBean.getId())) {
            return;
        }
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.Comment_library_Red_Dot, false, getActivity());
        EventBus.getDefault().post(CommonParameter.Comment_library_Red_Dot);
        Intent intent = new Intent(this.mContext, (Class<?>) CommentNewActivity.class);
        intent.putExtra("obj_id", this.questionInfoBean.getId());
        intent.putExtra("question_id", Long.parseLong(this.questionInfoBean.getId()));
        intent.putExtra("module_type", 1);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isNewCom", true);
        intent.putExtra("iscomValu", true);
        intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mycomment");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$20(View view) {
        if (TextUtils.isEmpty(this.questionInfoBean.getId())) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CommentNewActivity.class);
        intent.putExtra("obj_id", this.questionInfoBean.getId());
        intent.putExtra("question_id", Long.parseLong(this.questionInfoBean.getId()));
        intent.putExtra("module_type", 1);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isNewComzantong", true);
        intent.putExtra("iscomValu", true);
        intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mypraise");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$21(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", str);
        bundle.putInt("result", 1);
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            pushComment(bundle);
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        ActivityResultLauncher<Intent> activityResultLauncher = this.mActivityResultLauncher;
        if (activityResultLauncher != null) {
            activityResultLauncher.launch(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$22(View view) {
        if (isLogin()) {
            new DialogInput(getActivity(), new onDialogClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.s4
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f13338a.lambda$initQuestionType$21(str, str2, str3);
                }
            }, true).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRestoreData$8(View view) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.questionInfoBean.getRestore_img());
        CommonUtil.doPicture(getActivity(), arrayList, 0, null, R.drawable.kaodianhuanyuan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTitleImg$30(View view) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(this.imgtitle, this.questionInfoBean.getTitle_img()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        this.questiondetails_btn_commentNum.performLongClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$23(View view) throws JSONException {
        if (this.questionInfoBean.getStatData() == null) {
            AlertToast("原题加载中,请稍后再试！");
            return;
        }
        String knowledge_id = this.questionInfoBean.getKnowledge_id();
        this.questionInfoBean.setModule_type(TextUtils.isEmpty(knowledge_id) ? this.module_type : "110");
        QuestionDetailBean questionDetailBean = this.questionInfoBean;
        questionDetailBean.setIs_redo(TextUtils.isEmpty(questionDetailBean.getIs_redo()) ? "0" : this.questionInfoBean.getIs_redo());
        this.questionInfoBean.setUnit_title(ProjectApp.unit_title);
        this.questionInfoBean.setUnit_id(getArguments().getString("unit_id", ""));
        this.questionInfoBean.setExam_title(ProjectApp.exam_title);
        this.questionInfoBean.setIdentity_title(ProjectApp.identity_title);
        this.questionInfoBean.setChapter_title(this.chapterTitle);
        this.questionInfoBean.setChapter_parent_title(getArguments().getString("subject_title", ""));
        this.questionInfoBean.setChapter_parent_id(getArguments().getString("chapter_parent_id", ""));
        if (!TextUtils.isEmpty(knowledge_id)) {
            QuestionDetailBean questionDetailBean2 = this.questionInfoBean;
            questionDetailBean2.setChapter_parent_id(questionDetailBean2.getChapter_parent_id());
        }
        String json = ProjectApp.gson.toJson(this.questionInfoBean);
        AjaxParams ajaxParams = new AjaxParams();
        if (this.questionInfoBean.getStatData().getIs_collection() == 0) {
            AlertToast("收藏成功！");
            this.questionInfoBean.getStatData().setIs_collection(1);
            this.questionInfoBean.getStatData().setCollection_count(this.questionInfoBean.getStatData().getCollection_count() + 1);
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("question_id", this.questionInfoBean.getId());
                jSONObject.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity(), "1"));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            jSONArray.put(jSONObject);
            ajaxParams.put("collection", jSONArray.toString());
            ajaxParams.put("module_type", "" + this.module_type);
            QuestionDataRequest.getIntance(getActivity()).questionDoCollectData(ajaxParams, this);
            String str = "[\"" + this.questionInfoBean.getId() + "\"]";
            String str2 = "[\"" + this.questionInfoBean.getTitle() + "\"]";
            AliyunEvent aliyunEvent = AliyunEvent.CollectionQuestion;
            CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, str2, json, "2");
        } else {
            AlertToast("取消收藏成功！");
            this.questionInfoBean.getStatData().setIs_collection(0);
            if (this.questionInfoBean.getStatData().getCollection_count() > 0) {
                this.questionInfoBean.getStatData().setCollection_count(this.questionInfoBean.getStatData().getCollection_count() - 1);
            }
            ajaxParams.put("question_id", this.questionInfoBean.getId());
            ajaxParams.put("module_type", this.module_type);
            QuestionDataRequest.getIntance(getActivity()).cleancollection(ajaxParams, this);
            String str3 = "[\"" + this.questionInfoBean.getId() + "\"]";
            String str4 = "[\"" + this.questionInfoBean.getTitle() + "\"]";
            AliyunEvent aliyunEvent2 = AliyunEvent.CancelCollectionQuestion;
            CommonUtil.addLog(aliyunEvent2.getKey(), aliyunEvent2.getValue(), System.currentTimeMillis() + "", "", str3, str4, json, "2");
        }
        EventBus.getDefault().post(new QuestionCollectEvent(this.questionInfoBean.getStatData().getIs_collection() == 1, this.questionInfoBean.getId()));
        initStaticData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$7(ActivityResult activityResult) {
        Bundle bundleExtra;
        Intent data = activityResult.getData();
        if (activityResult.getResultCode() != 1 || data == null || (bundleExtra = data.getBundleExtra("bundleIntent")) == null) {
            return;
        }
        pushComment(bundleExtra);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSuccess$33(List list, View view) {
        showlog(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSuccess$34() {
        this.mCommListAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scroll2Position$6(int i2) {
        this.mPinnedSecListView.setSelection(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListener$2(AdapterView adapterView, View view, int i2, long j2) {
        TextView textView;
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.lineremen);
        if (linearLayout == null || linearLayout.getVisibility() != 0 || (textView = (TextView) linearLayout.findViewById(R.id.group_nametv)) == null) {
            return;
        }
        String string = textView.getText().toString();
        if (TextUtils.isEmpty(string) || !string.contains("最热")) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) QuestionCommentActivity.class);
        intent.putExtra("question_id", String.valueOf(this.question_id));
        intent.putExtra("title", String.valueOf(textView.getText()));
        String str = this.module_type;
        intent.putExtra("module_type", str == null ? 0 : Integer.parseInt(str));
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListener$3() {
        if (!this.ishavehot || this.mPinnedSecListView.getFirstVisiblePosition() <= 0) {
            this.lineselect2.setVisibility(8);
        } else if (this.lineselect2.getVisibility() == 8) {
            this.lineselect2.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListener$4(View view) {
        if (this.isPinned) {
            EventBus.getDefault().post(new HideChapterTitleEvent(true, String.valueOf(this.question_id)));
        }
        if (this.commlist.size() <= 0) {
            this.isLoadCommentData = true;
            this.pageNum = 1;
            getCommentListData();
            return;
        }
        this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.y4
            @Override // java.lang.Runnable
            public final void run() {
                this.f13400c.lambda$setListener$3();
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
    public /* synthetic */ void lambda$setListener$5(View view) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= this.commlist.size()) {
                break;
            }
            CommentBean.DataBean.HotBean hotBean = this.commlist.get(i3);
            if (hotBean.getType() == 1 && !hotBean.isHot() && hotBean.getName().contains("最新")) {
                i2 = i3;
                break;
            }
            i3++;
        }
        scroll2Position(i2 + 1);
        EventBus.getDefault().post(new HideChapterTitleEvent(true, String.valueOf(this.question_id)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setViewVisiable$31() {
        if (getActivity() == null || getActivity().isDestroyed()) {
            return;
        }
        int statusBarHeight = getActivity().getResources().getDisplayMetrics().heightPixels - StatusBarUtil.getStatusBarHeight(this.mContext);
        if (this.qbrel.getVisibility() == 0) {
            statusBarHeight -= this.qbrel.getHeight();
        }
        int height = this.headerContentView.getHeight();
        LogUtils.d("headerContentView", this.questionInfoBean.getTitle() + "，fullScreenHeight = " + statusBarHeight + ",header-height = " + height);
        if (height >= statusBarHeight) {
            this.llViewComment.setVisibility(8);
            this.llNoComment.setVisibility(8);
        } else {
            int comment_count = this.questionInfoBean.getStatData().getComment_count();
            this.llViewComment.setVisibility(comment_count <= 0 ? 8 : 0);
            this.llNoComment.setVisibility(comment_count <= 0 ? 0 : 8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showToastView$32(int i2, String str) {
        initAnalyAdapterData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showlog$29(List list, int i2, boolean z2) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (list == null || list.size() <= 0) {
            return;
        }
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
        postBiaoqianData(this.questionInfoBean.getId(), ((BiaoqianBeab.DataBean) list.get(i2)).getId(), String.valueOf(((BiaoqianBeab.DataBean) list.get(i2)).getCount()), z2);
        Collections.sort(list);
        initBiaoQianData(list);
        this.questionInfoBean.setDataBiao(list);
        String str = "[\"" + b0.a("\",\"", new CharSequence[]{this.questionId}) + "\"]";
        String str2 = "[\"" + com.psychiatrygarden.activity.q2.a("\",\"", arrayList2) + "\"]";
        QuestionDetailBean questionDetailBean = this.questionInfoBean;
        questionDetailBean.setModule_type(TextUtils.isEmpty(questionDetailBean.getKnowledge_id()) ? this.module_type : "110");
        QuestionDetailBean questionDetailBean2 = this.questionInfoBean;
        questionDetailBean2.setIs_redo(TextUtils.isEmpty(questionDetailBean2.getIs_redo()) ? "0" : this.questionInfoBean.getIs_redo());
        this.questionInfoBean.setUnit_title(ProjectApp.unit_title);
        this.questionInfoBean.setExam_title(ProjectApp.exam_title);
        this.questionInfoBean.setIdentity_title(ProjectApp.identity_title);
        this.questionInfoBean.setChapter_title(this.chapterTitle);
        this.questionInfoBean.setChapter_parent_title(getArguments().getString("subject_title", ""));
        String json = ProjectApp.gson.toJson(this.questionInfoBean);
        AliyunEvent aliyunEvent = AliyunEvent.SelectTag;
        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, str2, json, "2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playAudio() {
        initAd(this.mContext);
        int iIndexOf = this.positionList.indexOf(Integer.valueOf(this.mCurrentPosition));
        if (this.positionList.contains(Integer.valueOf(this.mCurrentPosition)) && this.currentPageIsVisible && this.mCurrentPosition == this.positionList.get(iIndexOf).intValue()) {
            if (this.questionInfoBean.getStem_audio_list() == null || this.questionInfoBean.getStem_audio_list().size() <= 0) {
                this.mImgAudio.setVisibility(8);
                return;
            }
            this.mImgAudio.setVisibility(0);
            if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                this.mImgAudio.setImageResource(R.drawable.ic_audio_play_one);
            } else {
                this.mImgAudio.setImageResource(R.drawable.ic_audio_play_one_night);
            }
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

    private void pushComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("obj_id", this.questionInfoBean.getId());
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("module_type", this.module_type);
        ajaxParams.put("comment_type", "2");
        String string = b3.getString("parent_id");
        if (!TextUtils.isEmpty(string) && !"0".equals(string)) {
            this.mTempParentId = string;
            ajaxParams.put("parent_id", string);
        }
        Context context = this.mContext;
        if (context instanceof AnswerQuestionActivity) {
            String videoId = ((AnswerQuestionActivity) context).getVideoId();
            if (!TextUtils.isEmpty(videoId)) {
                b3.putString("video_id", videoId);
            }
        }
        String string2 = b3.getString("to_user_id");
        if (!TextUtils.isEmpty(string2) && !"0".equals(string2)) {
            ajaxParams.put("to_user_id", string2);
        }
        String string3 = b3.getString("reply_primary_id");
        if (!TextUtils.isEmpty(string3) && !"0".equals(string3)) {
            ajaxParams.put("reply_primary_id", string3);
        }
        String string4 = b3.getString("b_img");
        String string5 = b3.getString("s_img");
        this.logImg = "";
        if (!TextUtils.isEmpty(string4)) {
            if (string4.contains("http")) {
                ajaxParams.put("b_img", string4);
                ajaxParams.put("s_img", string5);
                this.logImg = "," + string5;
            } else {
                ajaxParams.put("video_id", b3.getString("b_img"));
                this.logImg = "," + b3.getString("b_img");
            }
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            ajaxParams.put("virtual_user_id", b3.getString("virtual_user_id"));
        }
        QuestionDataRequest.getIntance(getActivity()).pushComment(ajaxParams, this);
    }

    private void scroll2Position(final int pos) {
        this.mPinnedSecListView.smoothScrollToPosition(pos);
        this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.d5
            @Override // java.lang.Runnable
            public final void run() {
                this.f13209c.lambda$scroll2Position$6(pos);
            }
        }, 310L);
    }

    private void scrollLayout() {
        this.mPinnedSecListView.smoothScrollToPositionFromTop(0, 0);
    }

    private void setFontSize() {
        int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, getActivity(), 2);
        int pxBySp = ScreenUtil.getPxBySp(this.mContext, 2);
        TextView textView = this.typeStr;
        int i2 = intConfig - 2;
        float f2 = pxBySp * i2;
        textView.setTextSize(0, ((Float) textView.getTag()).floatValue() - f2);
        TextView textView2 = this.pagenumtv;
        textView2.setTextSize(0, ((Float) textView2.getTag()).floatValue() - f2);
        TextView textView3 = this.titletv;
        textView3.setTextSize(0, ((Float) textView3.getTag()).floatValue() - f2);
        TextView textView4 = this.questiondetails_btn_commentNum;
        textView4.setTextSize(0, ((Float) textView4.getTag()).floatValue() - f2);
        TextView textView5 = this.questiondetails_tv_statistics;
        textView5.setTextSize(0, ((Float) textView5.getTag()).floatValue() - f2);
        TextView textView6 = this.textView_difficulty;
        textView6.setTextSize(0, ((Float) textView6.getTag()).floatValue() - f2);
        TextView textView7 = this.biaotxt;
        textView7.setTextSize(0, ((Float) textView7.getTag()).floatValue() - f2);
        TextView textView8 = this.questiondetails_tv_outline;
        textView8.setTextSize(0, ((Float) textView8.getTag()).floatValue() - f2);
        TextView textView9 = this.sourcetv;
        textView9.setTextSize(0, ((Float) textView9.getTag()).floatValue() - f2);
        TextView textView10 = this.tv_correction;
        textView10.setTextSize(0, ((Float) textView10.getTag()).floatValue() - f2);
        TextView textView11 = this.nitv;
        textView11.setTextSize(0, ((Float) textView11.getTag()).floatValue() - f2);
        TextView textView12 = this.questiondetails_tv_content_ques1;
        textView12.setTextSize(0, ((Float) textView12.getTag()).floatValue() - f2);
        TextView textView13 = this.eitv;
        textView13.setTextSize(0, ((Float) textView13.getTag()).floatValue() - f2);
        TextView textView14 = this.questiondetails_tv_content_ques;
        textView14.setTextSize(0, ((Float) textView14.getTag()).floatValue() - f2);
        TextView textView15 = this.tvAnswerAnalysis;
        textView15.setTextSize(0, ((Float) textView15.getTag()).floatValue() - f2);
        TextView textView16 = this.tv_answer_analyze;
        textView16.setTextSize(0, ((Float) textView16.getTag()).floatValue() - f2);
        TextView textView17 = this.tv_statistics;
        textView17.setTextSize(0, ((Float) textView17.getTag()).floatValue() - f2);
        TextView textView18 = this.mTvEncyclopediaAnalyze;
        textView18.setTextSize(0, ((Float) textView18.getTag()).floatValue() - f2);
        this.web.getSettings().setTextZoom(100 - (i2 * 10));
        if (this.questionInfoBean.getDataBiao() != null) {
            initBiaoQianData(this.questionInfoBean.getDataBiao());
        }
        if (this.analysisAdapter != null) {
            AnalysisAdapter analysisAdapter = new AnalysisAdapter(this.dataAnaList);
            this.analysisAdapter = analysisAdapter;
            this.recyansitem.setAdapter(analysisAdapter);
            this.analysisAdapter.setmAnaImClickIml(this);
        }
    }

    private void setListener() {
        this.mPinnedSecListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.f5
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f13226c.lambda$setListener$2(adapterView, view, i2, j2);
            }
        });
        this.remen.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.g5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13234c.lambda$setListener$4(view);
            }
        });
        this.zuixin.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.h5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13245c.lambda$setListener$5(view);
            }
        });
        this.mPinnedSecListView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.psychiatrygarden.activity.online.fragment.SubSubjectiveQuestionFragment.2
            private int oldFirstVisibleItem;
            private int oldTop;

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (SubSubjectiveQuestionFragment.this.hasAnswer) {
                    if (SubSubjectiveQuestionFragment.this.positionTab > 0) {
                        if (firstVisibleItem > SubSubjectiveQuestionFragment.this.positionTab) {
                            SubSubjectiveQuestionFragment.this.isSelect(false, true);
                        } else {
                            SubSubjectiveQuestionFragment.this.isSelect(true, false);
                        }
                    } else if (firstVisibleItem > SubSubjectiveQuestionFragment.this.positionTab + 1) {
                        SubSubjectiveQuestionFragment.this.isSelect(false, true);
                    } else {
                        SubSubjectiveQuestionFragment.this.isSelect(true, false);
                    }
                    SubSubjectiveQuestionFragment.this.mCurrentFirstVisibleItem = firstVisibleItem;
                    View childAt = view.getChildAt(0);
                    if (childAt != null) {
                        ItemRecord itemRecord = (ItemRecord) SubSubjectiveQuestionFragment.this.recordSp.get(firstVisibleItem);
                        if (itemRecord == null) {
                            itemRecord = new ItemRecord();
                        }
                        itemRecord.height = childAt.getHeight();
                        itemRecord.f13171top = childAt.getTop();
                        SubSubjectiveQuestionFragment.this.recordSp.append(firstVisibleItem, itemRecord);
                        int scrollY = SubSubjectiveQuestionFragment.this.getScrollY();
                        if (SubSubjectiveQuestionFragment.this.getActivity() != null && (SubSubjectiveQuestionFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                            ((AnswerQuestionActivity) SubSubjectiveQuestionFragment.this.getActivity()).onScrollChange(scrollY, String.valueOf(SubSubjectiveQuestionFragment.this.question_id), SubSubjectiveQuestionFragment.this.headerContentView.getHeight());
                        }
                    }
                    int top2 = childAt == null ? 0 : childAt.getTop();
                    int i2 = this.oldFirstVisibleItem;
                    if (firstVisibleItem == i2) {
                        int i3 = this.oldTop;
                        if (top2 > i3) {
                            if (SubSubjectiveQuestionFragment.this.getActivity() != null && (SubSubjectiveQuestionFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                                ((AnswerQuestionActivity) SubSubjectiveQuestionFragment.this.getActivity()).showHideBack2TopIcon(true);
                            }
                        } else if (top2 < i3 && (SubSubjectiveQuestionFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                            ((AnswerQuestionActivity) SubSubjectiveQuestionFragment.this.getActivity()).showHideBack2TopIcon(false);
                        }
                    } else if (firstVisibleItem < i2) {
                        if (SubSubjectiveQuestionFragment.this.getActivity() != null && (SubSubjectiveQuestionFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                            ((AnswerQuestionActivity) SubSubjectiveQuestionFragment.this.getActivity()).showHideBack2TopIcon(true);
                        }
                    } else if (SubSubjectiveQuestionFragment.this.getActivity() != null && (SubSubjectiveQuestionFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                        ((AnswerQuestionActivity) SubSubjectiveQuestionFragment.this.getActivity()).showHideBack2TopIcon(false);
                    }
                    this.oldTop = top2;
                    this.oldFirstVisibleItem = firstVisibleItem;
                }
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (SubSubjectiveQuestionFragment.this.hasAnswer && scrollState == 0 && SubSubjectiveQuestionFragment.this.getActivity() != null && (SubSubjectiveQuestionFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                    ((AnswerQuestionActivity) SubSubjectiveQuestionFragment.this.getActivity()).showHideBack2TopIcon(this.oldTop != 0);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBottomView() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.questionInfoBean.getId());
        ajaxParams.put("module_type", this.module_type + "");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getstatApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.fragment.SubSubjectiveQuestionFragment.7
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
                super.onSuccess((AnonymousClass7) s2);
                try {
                    if (!new JSONObject(s2).optString("code", "").equals("200") || (questionStatDataBean = (QuestionStatDataBean) new Gson().fromJson(s2, QuestionStatDataBean.class)) == null) {
                        return;
                    }
                    SubSubjectiveQuestionFragment.this.tv_correction.setText(String.format(Locale.CHINA, "%d 条纠错", Integer.valueOf(questionStatDataBean.getData().getError_correction_number())));
                    SubSubjectiveQuestionFragment.this.questionInfoBean.getStatData().setComment_count(questionStatDataBean.getData().getComment_count());
                    SubSubjectiveQuestionFragment.this.questionInfoBean.getStatData().setIs_comment(questionStatDataBean.getData().getIs_comment());
                    SubSubjectiveQuestionFragment.this.questionInfoBean.getStatData().setIs_collection(questionStatDataBean.getData().getIs_collection());
                    SubSubjectiveQuestionFragment.this.questionInfoBean.getStatData().setIs_praise(questionStatDataBean.getData().getIs_praise());
                    SubSubjectiveQuestionFragment.this.onMessage(questionStatDataBean.getData());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
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

    public void bigOrAns() {
        String str;
        if (SharePreferencesUtils.readStrConfig(CommonParameter.am_pm, getActivity(), "").equals("1")) {
            str = "大纲：" + this.questionInfoBean.getOutlines_am();
            if (TextUtils.isEmpty(this.questionInfoBean.getOutlines_am())) {
                this.questiondetails_tv_outline.setVisibility(8);
            } else {
                this.questiondetails_tv_outline.setVisibility(0);
            }
        } else if (SharePreferencesUtils.readStrConfig(CommonParameter.am_pm, getActivity(), "").equals("2")) {
            str = "大纲：" + this.questionInfoBean.getOutlines_pm();
            if (TextUtils.isEmpty(this.questionInfoBean.getOutlines_pm())) {
                this.questiondetails_tv_outline.setVisibility(8);
            } else {
                this.questiondetails_tv_outline.setVisibility(0);
            }
        } else {
            str = "大纲：" + this.questionInfoBean.getOutlines();
            if (TextUtils.isEmpty(this.questionInfoBean.getOutlines())) {
                this.questiondetails_tv_outline.setVisibility(8);
            } else {
                this.questiondetails_tv_outline.setVisibility(0);
            }
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        Matcher matcher = Pattern.compile("考试大纲未要求").matcher(str);
        while (matcher.find()) {
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, this.grayColors, null), matcher.start(0), matcher.end(0), 34);
        }
        this.questiondetails_tv_outline.setText(spannableStringBuilder);
        if ("search".equals(this.externalsources)) {
            this.questiondetails_tv_outline.setVisibility(8);
            this.sourcetv.setVisibility(8);
        }
        if (ConfigUtils.isHidden(4)) {
            this.questiondetails_tv_outline.setVisibility(8);
        }
    }

    public void dialogNote(final String content) {
        new XPopup.Builder(getActivity()).asCustom(new NoteNewPopWindow(this.mContext, content, new NoteNewPopWindow.NoteClickIml() { // from class: com.psychiatrygarden.activity.online.fragment.p4
            @Override // com.psychiatrygarden.activity.online.popwindow.NoteNewPopWindow.NoteClickIml
            public final void mDoClickMethod() {
                this.f13311a.lambda$dialogNote$24(content);
            }
        })).toggle();
    }

    public void doClickSimeThings(String isRight) throws JSONException {
        if (!CommonUtil.isNetworkConnected(getActivity())) {
            AlertToast("请您检查网络是否连接");
            return;
        }
        this.line_viewok.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.ver_fadout));
        this.line_viewok.setVisibility(8);
        this.schildline.setVisibility(0);
        if ("search".equals(this.externalsources)) {
            this.qbrel.setVisibility(8);
        } else {
            this.qbrel.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.ver_fadint));
            this.qbrel.setVisibility(0);
        }
        doPushAnsData(isRight);
        initSubViewData();
        this.hasAnswer = true;
        this.smartRefreshLayout.setEnabled(true);
        this.pageNum = 1;
        getCommentListData();
    }

    public void doPushAnsData(String isRight) throws JSONException {
        this.questionInfoBean.setUser_answer(isRight);
        if (this.questionInfoBean.getStatData().getAnswer() != null) {
            if (isRight.equals("1")) {
                this.questionInfoBean.getStatData().getAnswer().setRight_count(this.questionInfoBean.getStatData().getAnswer().getRight_count() + 1);
            } else {
                this.questionInfoBean.getStatData().getAnswer().setWrong_count(this.questionInfoBean.getStatData().getAnswer().getWrong_count() + 1);
            }
        }
        submitQuestionData();
    }

    public void getAnasisData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", "" + this.questionInfoBean.getId() + "");
        QuestionDataRequest.getIntance(getActivity()).questionMyAnalysisData(ajaxParams, this);
    }

    public void getBiaoData() {
        QuestionDataRequest.getIntance(getActivity()).questionlabel(this.questionInfoBean.getId(), this);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_answer_nquestion_new;
    }

    public void getNoteData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", "" + this.questionInfoBean.getId());
        ajaxParams.put("module_type", this.module_type);
        QuestionDataRequest.getIntance(getActivity()).questionNoteData(ajaxParams, this);
    }

    public void getRestoreStr(String str, TextView mTextV, String number, int type) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        String str2 = str;
        Matcher matcher = Pattern.compile("[(（]([\\w*]+)?P\\d+(-P?(\\d+)?)?+[)）]").matcher(str2);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            colorStateList = ContextCompat.getColorStateList(this.mContext, R.color.dominant_color);
            colorStateList2 = ContextCompat.getColorStateList(this.mContext, R.color.black);
        } else {
            colorStateList = ContextCompat.getColorStateList(this.mContext, R.color.dominant_color_night);
            colorStateList2 = ContextCompat.getColorStateList(this.mContext, R.color.dominant_color_night);
        }
        ColorStateList colorStateList3 = colorStateList2;
        int i2 = 0;
        if (SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_restore_img, getActivity()).equals("1") || ("unit".equals(this.category) && this.show_restore_img.equals("0"))) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str2);
            while (matcher.find()) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), matcher.start(0), matcher.end(0), 34);
            }
            Matcher matcher2 = Pattern.compile("[(（]*.[A-E]{1,}(\\s+)?对.*?[)）]").matcher(str2);
            while (matcher2.find()) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher2.start(0), matcher2.end(0), 34);
            }
            Matcher matcher3 = Pattern.compile("[(（]*.[A-E]+(\\s+)?错.*?[)）]").matcher(str2);
            while (matcher3.find()) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher3.start(0), matcher3.end(0), 34);
            }
            mTextV.setText(spannableStringBuilder);
            return;
        }
        String str3 = type != 1 ? type != 2 ? "" : NetworkRequestsURL.CommentIameUrl2 : NetworkRequestsURL.CommentIameUrl;
        final ArrayList arrayList = new ArrayList();
        int i3 = 0;
        while (matcher.find()) {
            String strGroup = matcher.group();
            String str4 = str2.substring(i2, matcher.end(i2) + i3) + "&" + str2.substring(matcher.end(i2) + i3);
            String native_app_id = this.questionInfoBean.getNative_app_id();
            if (TextUtils.isEmpty(native_app_id) || native_app_id.equals("0")) {
                native_app_id = this.questionInfoBean.getApp_id();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append(native_app_id);
            sb.append("/");
            sb.append(number);
            sb.append(strGroup.substring(1, strGroup.length() - 1));
            sb.append("-");
            int i4 = i3 + 1;
            sb.append(i4);
            sb.append(".jpg?x-oss-process=style/water_mark");
            String string = sb.toString();
            if (type == 1 || type == 2) {
                arrayList.add(string);
            }
            i3 = i4;
            i2 = 0;
            str2 = str4;
        }
        Matcher matcher4 = Pattern.compile("[(（]([\\w*]+)?P\\d+(-P?(\\d+)?)?+[)）]").matcher(str2);
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(str2);
        Paint.FontMetrics fontMetrics = mTextV.getPaint().getFontMetrics();
        int iCeil = ((int) Math.ceil(fontMetrics.descent - fontMetrics.top)) + 2;
        int i5 = 0;
        this.drawable.setBounds(0, 0, (((this.drawable.getIntrinsicWidth() * iCeil) / this.drawable.getIntrinsicHeight()) / 5) * 4, (iCeil / 5) * 4);
        final int i6 = 0;
        while (matcher4.find()) {
            spannableStringBuilder2.setSpan(new StickerSpan(this.drawable, 1), matcher4.end(0), matcher4.end(0) + 1, 33);
            spannableStringBuilder2.setSpan(new TextClick(new DomoIml() { // from class: com.psychiatrygarden.activity.online.fragment.a5
                @Override // com.psychiatrygarden.interfaceclass.DomoIml
                public final void clickToast() {
                    this.f13181a.lambda$getRestoreStr$9(arrayList, i6);
                }
            }), matcher4.start(0), matcher4.end(0), 33);
            mTextV.setMovementMethod(LinkMovementMethod.getInstance());
            i6++;
        }
        Matcher matcher5 = Pattern.compile("[(（]*.[A-E]+(\\s+)?对.*?[)）]").matcher(str2);
        while (matcher5.find()) {
            int i7 = i5;
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher5.start(i7), matcher5.end(i7), 34);
            i5 = i7;
        }
        int i8 = i5;
        Matcher matcher6 = Pattern.compile("[(（]*.[A-E]+(\\s+)?错.*?[)）]").matcher(str2);
        while (matcher6.find()) {
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher6.start(i8), matcher6.end(i8), 34);
        }
        Matcher matcher7 = Pattern.compile("[(（]([\\w*]+)?P\\d+(-P?(\\d+)?)?+[)）]").matcher(str2);
        while (matcher7.find()) {
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), matcher7.start(i8), matcher7.end(i8), 34);
        }
        mTextV.setText(spannableStringBuilder2);
    }

    public void getStaticsData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.questionInfoBean.getId());
        ajaxParams.put("module_type", this.module_type);
        QuestionDataRequest.getIntance(getActivity()).questionStatData(ajaxParams, this);
    }

    public String getmAnsweredQuestionBean() {
        return this.questionInfoBean.getUser_answer();
    }

    public void goToAnalysis() {
        if (CommonUtil.isFastClick() && TextUtils.isEmpty(this.questionInfoBean.getId())) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) ComposeActivity.class);
        if (TextUtils.isEmpty(this.questionInfoBean.getId())) {
            intent.putExtra("question_id", 0);
        } else {
            intent.putExtra("question_id", Long.parseLong(this.questionInfoBean.getId()));
        }
        intent.putExtra("identity_id", this.identity_id);
        intent.putExtra("mAnalysisBean", this.questionInfoBean.getmAnalysisBean());
        startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.answer.adapter.AnalysisAdapter.AnaImClickIml
    public void gotoAnalysisView() {
        goToAnalysis();
    }

    public void havaCollectimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes);
        } else {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes_night);
        }
    }

    public void havaCommingimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.youpinglun);
        } else {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.youpinglun_night);
        }
    }

    public void haveNoteimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited);
        } else {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited_night);
        }
    }

    public void haveParise() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan);
        } else {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan_night);
        }
    }

    public void initAnalyAdapterData() {
        if (this.questionInfoBean.getmAnalysisBean() == null || TextUtils.isEmpty(this.questionInfoBean.getmAnalysisBean().getAnalysis())) {
            this.ansline.setVisibility(8);
            return;
        }
        this.ansline.setVisibility(0);
        this.dataAnaList.clear();
        AnalysisBean.DataBean dataBean = new AnalysisBean.DataBean();
        dataBean.setViewType(0);
        this.dataAnaList.add(dataBean);
        this.dataAnaList.add(this.questionInfoBean.getmAnalysisBean());
        this.analysisAdapter.setList(this.dataAnaList);
    }

    public void initAnsData() {
        String explain = this.questionInfoBean.getExplain();
        String explain_img = this.questionInfoBean.getExplain_img();
        boolean z2 = SkinManager.getCurrentSkinType(this.mContext) == 1;
        if (ConfigUtils.isHidden(6)) {
            this.ll_answer_analysis_layout.setVisibility(8);
        } else if (this.ll_answer_enter.getVisibility() == 8 && TextUtils.isEmpty(explain) && TextUtils.isEmpty(explain_img)) {
            this.ll_answer_analysis_layout.setVisibility(8);
        } else {
            this.ll_answer_analysis_layout.setVisibility(0);
            if (TextUtils.isEmpty(explain) && TextUtils.isEmpty(explain_img)) {
                this.mLyAnswerUserInfo.setVisibility(8);
                this.tvAnswerAnalysis.setTextColor(z2 ? Color.parseColor("#575F79") : Color.parseColor("#909499"));
                this.tvAnswerAnalysis.setTextSize(14.0f);
                this.tvAnswerAnalysis.setGravity(17);
                this.tvAnswerAnalysis.setText("暂无解析");
            } else {
                if (TextUtils.isEmpty(explain)) {
                    this.tvAnswerAnalysis.setVisibility(8);
                } else {
                    this.tvAnswerAnalysis.setVisibility(0);
                    this.tvAnswerAnalysis.setTextColor(z2 ? Color.parseColor("#7380A9") : Color.parseColor("#141516"));
                    this.tvAnswerAnalysis.setTextSize(16.0f);
                    this.tvAnswerAnalysis.setGravity(GravityCompat.START);
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
            if (this.ll_analyze_enter.getVisibility() == 8 && TextUtils.isEmpty(this.questionInfoBean.getOption_analysis()) && TextUtils.isEmpty(this.questionInfoBean.getOption_analysis_img())) {
                this.ll_encyclopedia_analysis_layout.setVisibility(8);
                this.mLineExpline.setVisibility(8);
            } else {
                this.ll_encyclopedia_analysis_layout.setVisibility(0);
                this.mLineExpline.setVisibility(0);
                if (TextUtils.isEmpty(this.questionInfoBean.getOption_analysis()) && TextUtils.isEmpty(this.questionInfoBean.getOption_analysis_img())) {
                    this.mLyUserInfo.setVisibility(8);
                    this.mTvEcyclopediaContents.setTextColor(z2 ? Color.parseColor("#575F79") : Color.parseColor("#909499"));
                    this.mTvEcyclopediaContents.setTextSize(14.0f);
                    this.mTvEcyclopediaContents.setGravity(17);
                    this.mTvEcyclopediaContents.setText("暂无解析");
                } else {
                    if (TextUtils.isEmpty(this.questionInfoBean.getOption_analysis())) {
                        this.mTvEcyclopediaContents.setVisibility(8);
                    } else {
                        this.mTvEcyclopediaContents.setVisibility(0);
                        this.mTvEcyclopediaContents.setTextColor(z2 ? Color.parseColor("#7380A9") : Color.parseColor("#141516"));
                        this.mTvEcyclopediaContents.setTextSize(16.0f);
                        this.mTvEcyclopediaContents.setGravity(GravityCompat.START);
                        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(this.questionInfoBean.getOption_analysis());
                        Matcher matcher2 = Pattern.compile("（([A-Z]+[对|错]([^）]+)?)）").matcher(this.questionInfoBean.getOption_analysis());
                        while (matcher2.find()) {
                            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, this.mTvEcyclopediaContents.getTextColors(), null), matcher2.start(0), matcher2.end(0), 34);
                        }
                        this.mTvEcyclopediaContents.setText(spannableStringBuilder2);
                    }
                    if (TextUtils.isEmpty(this.questionInfoBean.getOption_analysis_img())) {
                        this.mImgEncyclopediaExplain.setVisibility(8);
                    } else {
                        this.mImgEncyclopediaExplain.setVisibility(0);
                        Glide.with(this).load((Object) GlideUtils.generateUrl(this.questionInfoBean.getOption_analysis_img())).placeholder(new ColorDrawable(ContextCompat.getColor(this.mImgEncyclopediaExplain.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(this.mImgEncyclopediaExplain);
                    }
                }
            }
            if (this.ll_restore_point.getVisibility() == 0 && this.ll_answer_analysis_layout.getVisibility() == 8) {
                this.mLineRestore.setVisibility(0);
            }
        }
        if (this.ll_restore_point.getVisibility() == 8 && this.ll_answer_analysis_layout.getVisibility() == 8 && this.ll_encyclopedia_analysis_layout.getVisibility() == 8) {
            this.mLineEmpty.setVisibility(8);
        }
        if ("填空题".equals(this.questionInfoBean.getType_str())) {
            this.ll_answer_analysis_layout.setVisibility(8);
            this.ll_encyclopedia_analysis_layout.setVisibility(8);
            this.mLineRestore.setVisibility(8);
        }
        final ArrayList arrayList = new ArrayList();
        arrayList.add(explain_img);
        this.mImgExplain.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.q4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13320c.lambda$initAnsData$25(arrayList, view);
            }
        });
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add(this.questionInfoBean.getOption_analysis_img());
        this.mImgEncyclopediaExplain.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.r4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13328c.lambda$initAnsData$26(arrayList2, view);
            }
        });
    }

    public void initBiaoQianData(final List<BiaoqianBeab.DataBean> dataBiao) {
        if (this.mContext == null || getActivity() == null) {
            return;
        }
        if (dataBiao == null || dataBiao.size() <= 0) {
            this.mRadioGroup_content.removeAllViews();
            this.biaotxt.setText("标签：？");
        } else {
            this.biaotxt.setText("标签：");
            this.mRadioGroup_content.removeAllViews();
            if (dataBiao.get(0).getCount() < 3) {
                TextView textView = (TextView) View.inflate(this.mContext, R.layout.biaoqianview, null).findViewById(R.id.bqview);
                textView.setTextSize(12.0f);
                textView.setTextSize(0, textView.getTextSize() - ((SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, getActivity(), 2) - 2) * ScreenUtil.getPxBySp(getActivity(), 2)));
                if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                    textView.setBackgroundResource(R.drawable.gray_deek_round_bg);
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color));
                } else {
                    textView.setBackgroundResource(R.drawable.gray_deek_round_bg_night);
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
                }
                textView.setText("点击为本题添加标签");
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.i4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f13252c.lambda$initBiaoQianData$27(dataBiao, view);
                    }
                });
                this.mRadioGroup_content.addView(textView);
            } else {
                for (int i2 = 0; i2 < 3; i2++) {
                    if (dataBiao.get(i2).getCount() >= 3) {
                        TextView textView2 = (TextView) View.inflate(this.mContext, R.layout.biaoqianview, null).findViewById(R.id.bqview);
                        textView2.setTextSize(12.0f);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                        layoutParams.rightMargin = 20;
                        textView2.setTextSize(0, textView2.getTextSize() - ((SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, getActivity(), 2) - 2) * ScreenUtil.getPxBySp(getActivity(), 2)));
                        textView2.setText(String.format(Locale.CHINA, "%s %d", dataBiao.get(i2).getLabel(), Integer.valueOf(dataBiao.get(i2).getCount())));
                        try {
                            if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                                textView2.setTextColor(Color.parseColor(dataBiao.get(i2).getColor()));
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        textView2.setLayoutParams(layoutParams);
                        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.t4
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f13348c.lambda$initBiaoQianData$28(dataBiao, view);
                            }
                        });
                        this.mRadioGroup_content.addView(textView2);
                    }
                }
            }
        }
        this.headerContentView.findViewById(R.id.linbiaoqian).setVisibility(ConfigUtils.isHidden(3) ? 8 : 0);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public void initQuestionType() {
        initRefreshView();
        initStaticData();
        if ("1".equals(this.questionInfoBean.getIs_new())) {
            this.tv_question_new.setVisibility(0);
        } else {
            this.tv_question_new.setVisibility(4);
        }
        this.typeStr.setText(this.questionInfoBean.getType_str());
        if ("填空题".equals(this.questionInfoBean.getType_str())) {
            this.ll_answer_analysis_layout.setVisibility(8);
            this.ll_encyclopedia_analysis_layout.setVisibility(8);
        } else {
            this.ll_answer_analysis_layout.setVisibility(0);
            this.ll_encyclopedia_analysis_layout.setVisibility(0);
        }
        if (TextUtils.isEmpty(this.questionInfoBean.getSource())) {
            this.sourcetv.setVisibility(8);
        } else {
            this.sourcetv.setVisibility(0);
            this.sourcetv.setText(String.format(Locale.CHINA, "来源：%s", this.questionInfoBean.getSource()));
        }
        if (this.questionInfoBean.getStem_video_list() == null || this.questionInfoBean.getStem_video_list().size() <= 0) {
            this.mLyVideoView.setVisibility(8);
        } else {
            this.mLyVideoView.setVisibility(0);
            Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(this.questionInfoBean.getStem_video_list().get(0).getThumb())).override(ScreenUtil.getPxByDp(this.mContext, R2.anim.voice_from_icon), ScreenUtil.getPxByDp(this.mContext, 94)).placeholder(new ColorDrawable(ContextCompat.getColor(this.mImgVideo.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(this.mImgVideo);
        }
        if (this.positionList.size() == 0 || !this.positionList.contains(Integer.valueOf(this.mCurrentPosition))) {
            this.positionList.add(Integer.valueOf(this.mCurrentPosition));
        }
        titlenum();
        String title = this.questionInfoBean.getTitle();
        if ("3".equals(this.questionInfoBean.getType())) {
            this.web.setVisibility(0);
            this.web.getSettings().setJavaScriptEnabled(true);
            this.titletv.setVisibility(8);
            boolean z2 = (this.questionInfoBean.getmAnalysisBean() == null || TextUtils.isEmpty(this.questionInfoBean.getmAnalysisBean().getAnalysis())) ? false : true;
            boolean z3 = SkinManager.getCurrentSkinType(getActivity()) == 0;
            String str = z2 ? z3 ? "#303030" : "#7380A9" : "#00000000";
            String str2 = z3 ? "#fff" : "#121622";
            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE HTML> <HTML><head><body style=background-color:");
            sb.append(str2);
            sb.append(";color:");
            sb.append(z3 ? "#303030" : "#7380A9");
            sb.append(">");
            sb.append(title);
            sb.append("</body></HTML>");
            String strReplaceAll = sb.toString().replaceAll("#@", String.format("<span style=\"color: %s ;position: relative;vertical-align: -5px;border-bottom: 1px dashed %s;margin: 0 5px;\"><b style=\"top: -5px;position: relative;\">", str, z3 ? "#888" : "#64729F")).replaceAll("@#", "</b></span>").replaceAll("\\n", "<br>");
            this.web.setBackgroundColor(0);
            this.web.loadDataWithBaseURL(null, strReplaceAll, "text/html; charset=utf-8", "utf-8", null);
            this.web.setWebViewClient(new MyWebViewClient(this, null));
        } else {
            this.web.setVisibility(8);
            this.titletv.setVisibility(0);
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity());
            if (this.questionInfoBean.getNumber() == null || "".equals(this.questionInfoBean.getNumber()) || strConfig.equals("40") || this.category.equals("unit")) {
                if (this.questionInfoBean.getStem_audio_list() == null || this.questionInfoBean.getStem_audio_list().size() <= 0) {
                    this.titletv.setText(title);
                } else {
                    this.titletv.setText(String.format("       %s", title));
                }
            } else if (this.questionInfoBean.getStem_audio_list() == null || this.questionInfoBean.getStem_audio_list().size() <= 0) {
                this.titletv.setText(String.format("%s. %s", this.questionInfoBean.getNumber(), title));
            } else {
                this.titletv.setText(String.format("       %s %s", this.questionInfoBean.getNumber(), title));
            }
            if (TextUtils.isEmpty(this.is_show_number)) {
                if (!TextUtils.isEmpty(this.questionInfoBean.getNumber()) && (strConfig.equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ) || strConfig.equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR) || strConfig.equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_JOININ_GROUP) || strConfig.equals("20") || strConfig.equals("21"))) {
                    if (this.questionInfoBean.getStem_audio_list() == null || this.questionInfoBean.getStem_audio_list().size() <= 0) {
                        title = this.questionInfoBean.getNumber() + ". " + title;
                    } else {
                        title = "   " + this.questionInfoBean.getNumber() + " " + title;
                    }
                }
                this.titletv.setText(title);
            } else {
                if (this.is_show_number.equals("1")) {
                    if (this.questionInfoBean.getStem_audio_list() == null || this.questionInfoBean.getStem_audio_list().size() <= 0) {
                        title = this.questionInfoBean.getNumber() + ". " + title;
                    } else {
                        title = "   " + this.questionInfoBean.getNumber() + " " + title;
                    }
                }
                this.titletv.setText(title);
            }
        }
        initSubViewData();
        initTitleImg();
        if (this.questionInfoBean.getStatData().getAnswer() == null) {
            getStaticsData();
        }
        if (this.questionInfoBean.getDataBiao() != null) {
            initBiaoQianData(this.questionInfoBean.getDataBiao());
            this.ll_more_columns.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.n5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13296c.lambda$initQuestionType$10(view);
                }
            });
        } else {
            getBiaoData();
        }
        if (TextUtils.isEmpty(SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_correction_error, getActivity())) || SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_correction_error, getActivity()).equals("1")) {
            this.ll_restore_enter.setVisibility(8);
            this.tv_restore_reword.setVisibility(8);
        } else {
            this.ll_restore_enter.setVisibility(0);
            this.tv_restore_reword.setVisibility(0);
        }
        if (TextUtils.isEmpty(SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_analysis_update, this.mContext)) || SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_analysis_update, this.mContext).equals("1")) {
            this.ll_answer_enter.setVisibility(8);
            this.mTvAnswerRecord.setVisibility(8);
        } else {
            this.ll_answer_enter.setVisibility(0);
            this.mTvAnswerRecord.setVisibility(0);
        }
        if (TextUtils.isEmpty(SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_options_update, this.mContext)) || SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_options_update, this.mContext).equals("1")) {
            this.ll_analyze_enter.setVisibility(8);
            this.tv_analyze_reword.setVisibility(8);
        } else {
            this.ll_analyze_enter.setVisibility(0);
            this.tv_analyze_reword.setVisibility(0);
        }
        bigOrAns();
        initRestoreData();
        this.questiondetails_btn_pushAnswer.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.p5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13313c.lambda$initQuestionType$11(view);
            }
        });
        this.ll_question_comment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.q5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13322c.lambda$initQuestionType$12(view);
            }
        });
        AnalysisAdapter analysisAdapter = new AnalysisAdapter(this.dataAnaList);
        this.analysisAdapter = analysisAdapter;
        this.recyansitem.setAdapter(analysisAdapter);
        this.analysisAdapter.setmAnaImClickIml(this);
        if (TextUtils.isEmpty(this.questionInfoBean.getmAnalysisBean().getId())) {
            getAnasisData();
        } else {
            initSubViewData();
            initAnalyAdapterData();
        }
        initAnsData();
        this.zuoduil.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.r5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws JSONException {
                this.f13330c.lambda$initQuestionType$13(view);
            }
        });
        this.zuocuol.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.s5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws JSONException {
                this.f13339c.lambda$initQuestionType$14(view);
            }
        });
        this.ly_questiondetails_btn_collect.setOnClickListener(this.collectClickListener);
        this.questiondetails_btn_commentNum.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.t5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13350c.lambda$initQuestionType$15(view);
            }
        });
        this.ly_questiondetails_btn_edit.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.j4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13261c.lambda$initQuestionType$17(view);
            }
        });
        this.tv_correction.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.k4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13270c.lambda$initQuestionType$18(view);
            }
        });
        this.ly_questiondetails_btn_centerMsg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.l4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13278c.lambda$initQuestionType$19(view);
            }
        });
        this.ly_questiondetails_btn_zantong.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.m4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13287c.lambda$initQuestionType$20(view);
            }
        });
        this.btn_comment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.o5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13303c.lambda$initQuestionType$22(view);
            }
        });
        TextView textView = this.typeStr;
        textView.setTag(Float.valueOf(textView.getTextSize()));
        TextView textView2 = this.pagenumtv;
        textView2.setTag(Float.valueOf(textView2.getTextSize()));
        TextView textView3 = this.titletv;
        textView3.setTag(Float.valueOf(textView3.getTextSize()));
        TextView textView4 = this.questiondetails_btn_commentNum;
        textView4.setTag(Float.valueOf(textView4.getTextSize()));
        TextView textView5 = this.questiondetails_tv_statistics;
        textView5.setTag(Float.valueOf(textView5.getTextSize()));
        TextView textView6 = this.textView_difficulty;
        textView6.setTag(Float.valueOf(textView6.getTextSize()));
        TextView textView7 = this.biaotxt;
        textView7.setTag(Float.valueOf(textView7.getTextSize()));
        TextView textView8 = this.questiondetails_tv_outline;
        textView8.setTag(Float.valueOf(textView8.getTextSize()));
        TextView textView9 = this.sourcetv;
        textView9.setTag(Float.valueOf(textView9.getTextSize()));
        TextView textView10 = this.tv_correction;
        textView10.setTag(Float.valueOf(textView10.getTextSize()));
        TextView textView11 = this.nitv;
        textView11.setTag(Float.valueOf(textView11.getTextSize()));
        TextView textView12 = this.questiondetails_tv_content_ques1;
        textView12.setTag(Float.valueOf(textView12.getTextSize()));
        TextView textView13 = this.eitv;
        textView13.setTag(Float.valueOf(textView13.getTextSize()));
        TextView textView14 = this.questiondetails_tv_content_ques;
        textView14.setTag(Float.valueOf(textView14.getTextSize()));
        TextView textView15 = this.tvAnswerAnalysis;
        textView15.setTag(Float.valueOf(textView15.getTextSize()));
        TextView textView16 = this.tv_answer_analyze;
        textView16.setTag(Float.valueOf(textView16.getTextSize()));
        TextView textView17 = this.tv_statistics;
        textView17.setTag(Float.valueOf(textView17.getTextSize()));
        TextView textView18 = this.mTvEncyclopediaAnalyze;
        textView18.setTag(Float.valueOf(textView18.getTextSize()));
        setFontSize();
    }

    public void initRefreshView() {
    }

    public void initRestoreData() {
        try {
            if (ConfigUtils.isHidden(5)) {
                this.ll_restore_point.setVisibility(8);
            } else if (this.ll_restore_enter.getVisibility() == 8 && TextUtils.isEmpty(this.questionInfoBean.getRestore()) && TextUtils.isEmpty(this.questionInfoBean.getRestore_img())) {
                this.ll_restore_point.setVisibility(8);
            } else {
                this.ll_restore_point.setVisibility(0);
                boolean z2 = SkinManager.getCurrentSkinType(this.mContext) == 1;
                boolean zIsEmpty = TextUtils.isEmpty(this.questionInfoBean.getRestore());
                int i2 = R.color.fourth_line_backgroup_color;
                if (!zIsEmpty) {
                    this.questiondetails_tv_content_ques1.setTextColor(Color.parseColor(z2 ? "#7380A9" : "#141516"));
                    this.questiondetails_tv_content_ques1.setGravity(GravityCompat.START);
                    this.questiondetails_tv_content_ques1.setTextSize(16.0f);
                    this.questiondetails_tv_content_ques1.setVisibility(0);
                    if (TextUtils.isEmpty(this.questionInfoBean.getRestore_img())) {
                        this.mImgOriginal.setVisibility(8);
                    } else {
                        this.mImgOriginal.setVisibility(0);
                        RequestBuilder<Drawable> requestBuilderLoad = Glide.with(this).load((Object) GlideUtils.generateUrl(this.questionInfoBean.getRestore_img()));
                        Context context = this.mImgOriginal.getContext();
                        if (SkinManager.getCurrentSkinType(ProjectApp.instance()) != 0) {
                            i2 = R.color.bg_backgroud_night;
                        }
                        requestBuilderLoad.placeholder(new ColorDrawable(ContextCompat.getColor(context, i2))).into(this.mImgOriginal);
                    }
                    getRestoreStr(this.questionInfoBean.getRestore(), this.questiondetails_tv_content_ques1, this.questionInfoBean.getNumber(), 1);
                } else if (TextUtils.isEmpty(this.questionInfoBean.getRestore_img())) {
                    this.mImgOriginal.setVisibility(8);
                    this.questiondetails_tv_content_ques1.setVisibility(0);
                    this.questiondetails_tv_content_ques1.setTextColor(Color.parseColor(z2 ? "#575F79" : "#909499"));
                    this.questiondetails_tv_content_ques1.setGravity(17);
                    this.questiondetails_tv_content_ques1.setTextSize(14.0f);
                    this.questiondetails_tv_content_ques1.setText("暂无还原");
                    this.mLyRestoreUserInfo.setVisibility(8);
                } else {
                    this.questiondetails_tv_content_ques1.setVisibility(8);
                    this.mImgOriginal.setVisibility(0);
                    RequestBuilder<Drawable> requestBuilderLoad2 = Glide.with(this).load((Object) GlideUtils.generateUrl(this.questionInfoBean.getRestore_img()));
                    Context context2 = this.mImgOriginal.getContext();
                    if (SkinManager.getCurrentSkinType(ProjectApp.instance()) != 0) {
                        i2 = R.color.bg_backgroud_night;
                    }
                    requestBuilderLoad2.placeholder(new ColorDrawable(ContextCompat.getColor(context2, i2))).into(this.mImgOriginal);
                }
            }
            this.mImgOriginal.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.o4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13302c.lambda$initRestoreData$8(view);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void initStaticData() {
        double right_count;
        try {
            if (this.mContext != null && getActivity() != null) {
                if (this.questionInfoBean.getStatData() != null) {
                    if (this.questionInfoBean.getStatData().getError_correction_number() != 0) {
                        this.tv_correction.setText(String.format(Locale.CHINA, "%d 条纠错", Integer.valueOf(this.questionInfoBean.getStatData().getError_correction_number())));
                    } else {
                        this.tv_correction.setText("试题纠错");
                    }
                    onMessage(this.questionInfoBean.getStatData());
                    if (this.questionInfoBean.getStatData().getRight_count() + this.questionInfoBean.getStatData().getWrong_count() > 0) {
                        right_count = (this.questionInfoBean.getStatData().getRight_count() * 100) / (this.questionInfoBean.getStatData().getRight_count() + this.questionInfoBean.getStatData().getWrong_count());
                        CommonUtil.getNumber(right_count);
                    } else {
                        right_count = 0.0d;
                    }
                    if (this.questionInfoBean.getStatData().getComment_count() > 10000) {
                        this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d.%d万", Integer.valueOf(this.questionInfoBean.getStatData().getComment_count() / 10000), Integer.valueOf((this.questionInfoBean.getStatData().getComment_count() % 10000) / 1000)));
                    } else {
                        this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, TimeModel.NUMBER_FORMAT, Integer.valueOf(this.questionInfoBean.getStatData().getComment_count())));
                    }
                    if (this.questionInfoBean.getStatData().getAnswer().getRight_count() + this.questionInfoBean.getStatData().getAnswer().getWrong_count() > 0) {
                        String str = "统计：" + this.questionInfoBean.getStatData().getStat_info();
                        if (!"search".equals(this.externalsources)) {
                            str = str + "本人作答{" + (this.questionInfoBean.getStatData().getAnswer().getRight_count() + this.questionInfoBean.getStatData().getAnswer().getWrong_count()) + "}次，对{" + this.questionInfoBean.getStatData().getAnswer().getRight_count() + "}次，正确率{" + CommonUtil.getNumber((this.questionInfoBean.getStatData().getAnswer().getRight_count() * 100) / (this.questionInfoBean.getStatData().getAnswer().getRight_count() + this.questionInfoBean.getStatData().getAnswer().getWrong_count())) + "%}";
                        }
                        if (right_count > (this.questionInfoBean.getStatData().getAnswer().getRight_count() * 100) / (this.questionInfoBean.getStatData().getAnswer().getRight_count() + this.questionInfoBean.getStatData().getAnswer().getWrong_count())) {
                            this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(getActivity()) == 0 ? ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
                        } else {
                            this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(getActivity()) == 0 ? ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10572282).outerColor(-16777216).format() : ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
                        }
                    } else {
                        String str2 = "统计：" + this.questionInfoBean.getStatData().getStat_info();
                        if (!"search".equals(this.externalsources)) {
                            str2 = str2 + "本人作答{" + (this.questionInfoBean.getStatData().getAnswer().getRight_count() + this.questionInfoBean.getStatData().getAnswer().getWrong_count()) + "}次，对{" + this.questionInfoBean.getStatData().getAnswer().getRight_count() + "}次，正确率{0%}";
                        }
                        this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(getActivity()) == 0 ? ColorPhrase.from(str2).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(str2).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
                    }
                    int i2 = right_count > 95.0d ? 1 : right_count > 80.0d ? 2 : right_count > 60.0d ? 3 : right_count > 30.0d ? 4 : 5;
                    if (this.questiondetails_layout_diff.getChildCount() > 1) {
                        LinearLayout linearLayout = this.questiondetails_layout_diff;
                        linearLayout.removeViews(1, linearLayout.getChildCount() - 1);
                    }
                    for (int i3 = 0; i3 < 5; i3++) {
                        ImageView imageView = new ImageView(getActivity());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                        layoutParams.rightMargin = ScreenUtil.getPxByDp((Context) getActivity(), 5);
                        imageView.setLayoutParams(layoutParams);
                        if (i3 < i2) {
                            imageView.setImageResource(SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.drawable.icon_star_yellow_nights : R.drawable.icon_star_yellow);
                        } else {
                            imageView.setImageResource(SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.drawable.icon_star_gary_night : R.drawable.icon_star_gary);
                        }
                        this.questiondetails_layout_diff.addView(imageView);
                    }
                    if (this.questionInfoBean.getStatData().getIs_comment() == 0) {
                        noCommingimg();
                    } else {
                        havaCommingimg();
                    }
                    if (this.questionInfoBean.getStatData().getIs_praise() == 0) {
                        noParise();
                    } else {
                        haveParise();
                    }
                } else {
                    if (this.questionInfoBean.getStatData() == null) {
                        this.questiondetails_btn_commentNum.setText("?评论");
                    } else if (this.questionInfoBean.getStatData().getComment_count() > 10000) {
                        this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d.%d万", Integer.valueOf(this.questionInfoBean.getStatData().getComment_count() / 10000), Integer.valueOf((this.questionInfoBean.getStatData().getComment_count() % 10000) / 1000)));
                    } else {
                        this.questiondetails_btn_commentNum.setText(String.valueOf(this.questionInfoBean.getStatData().getComment_count()));
                    }
                    this.questiondetails_tv_statistics.setText("search".equals(this.externalsources) ? "本题0人收藏，全部考生作答0次，对0次，正确率0%" : "本题0人收藏，全部考生作答0次，对0次，正确率0%，本人作答0次，对0次，正确率0%");
                }
                addRestoreUserView();
                addAnalyzeUserView();
                if (ConfigUtils.isHidden(2)) {
                    this.headerContentView.findViewById(R.id.ll_statstics).setVisibility(8);
                }
                if (ConfigUtils.isHidden(1)) {
                    this.questiondetails_layout_diff.setVisibility(8);
                }
                if (this.questionInfoBean.getStatData().getIs_note() == 0) {
                    noNoteimg();
                } else {
                    haveNoteimg();
                }
                if (this.questionInfoBean.getStatData().getIs_collection() == 0) {
                    noCollectimg();
                } else {
                    havaCollectimg();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void initStyle() {
        String str = SkinManager.getCurrentSkinType(getActivity()) == 1 ? " spans.style.color = '#64729F';" : " spans.style.color = '#444444';";
        this.web.loadUrl("javascript:(function(){var objs = document.getElementsByTagName('span'); for(var i=0;i<objs.length;i++)  {var spans = objs[i];   " + str + "}})()");
    }

    public void initSubViewData() {
        if (!TextUtils.isEmpty(getmAnsweredQuestionBean()) || Constants.ANSWER_MODE.RECITE_MODE.equals(this.answerMode)) {
            this.hasAnswer = true;
            this.smartRefreshLayout.setEnabled(true);
            setViewVisiable(0);
            this.questiondetails_btn_pushAnswer.setVisibility(8);
            return;
        }
        if (this.questionInfoBean.getmAnalysisBean() != null && this.questionInfoBean.getmAnalysisBean().getSubjective_answering().equals("1")) {
            doAnswing();
        } else {
            setViewVisiable(8);
            this.questiondetails_btn_pushAnswer.setVisibility(0);
        }
    }

    public void initTitleImg() {
        if (TextUtils.isEmpty(this.questionInfoBean.getTitle_img())) {
            this.imgtitle.setVisibility(8);
            this.img_webview.setVisibility(8);
            return;
        }
        if (!this.questionInfoBean.getTitle_img().contains("http")) {
            this.imgtitle.setVisibility(8);
            this.img_webview.setVisibility(8);
            return;
        }
        this.imgtitle.setVisibility(0);
        this.img_webview.setVisibility(0);
        this.img_webview.loadDataWithBaseURL(null, "<html><head><title></title></head><body><img style='height:auto;max-width:100%' src=" + this.questionInfoBean.getTitle_img() + " /></body></html>", "text/html", "utf-8", null);
        this.imgtitle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.i5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13254c.lambda$initTitleImg$30(view);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.externalsources = arguments.getString("externalsources");
            this.answerMode = arguments.getString("answerMode");
            this.total = arguments.getString("total");
            this.module_type = arguments.getString("module_type");
            this.chapterTitle = arguments.getString("chapter_title");
            this.category = arguments.getString(UriUtil.QUERY_CATEGORY, "");
            this.identity_id = arguments.getString("identity_id", "");
            this.questionId = getArguments().getString("questionId");
            if (getActivity() instanceof AnswerQuestionActivity) {
                this.questionInfoBean = ((AnswerQuestionActivity) getActivity()).getQuestionDetailBean(arguments.getInt("position", 0));
            }
            if (this.questionInfoBean == null) {
                return;
            } else {
                this.mCurrentPosition = arguments.getInt("position");
            }
        }
        try {
            this.question_id = Long.parseLong(this.questionInfoBean.getId());
            this.is_show_number = this.questionInfoBean.getShow_number();
            this.show_restore_img = SharePreferencesUtils.readStrConfig(CommonParameter.show_restore_img, getActivity(), "1");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        View viewInflate = View.inflate(this.mContext, R.layout.layout_subjective_question, null);
        this.headerContentView = viewInflate;
        this.llViewComment = (LinearLayout) viewInflate.findViewById(R.id.ll_view_comment);
        this.llNoComment = (LinearLayout) this.headerContentView.findViewById(R.id.ll_no_comment);
        this.ll_restore_point = (LinearLayout) this.headerContentView.findViewById(R.id.ll_restore_point);
        this.tvEditTime = (TextView) this.headerContentView.findViewById(R.id.tv_edit_time);
        this.llAd = (QuestionAdWidegt) this.headerContentView.findViewById(R.id.ll_ad);
        this.tv_correction = (TextView) this.headerContentView.findViewById(R.id.tv_correction);
        int i2 = 8;
        if ("search".equals(this.externalsources)) {
            this.tv_correction.setVisibility(8);
        } else {
            this.tv_correction.setVisibility(0);
        }
        this.mCutQuestionFlag = (ImageView) this.headerContentView.findViewById(R.id.iv_cut_flag);
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            this.mCutQuestionFlag.setImageResource(R.drawable.ic_cut_night);
        }
        TextView textView = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_title);
        if (TextUtils.isEmpty(this.chapterTitle)) {
            this.headerContentView.findViewById(R.id.rl_top_title).setVisibility(8);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mCutQuestionFlag.getLayoutParams();
            layoutParams.topMargin = 0;
            this.mCutQuestionFlag.setLayoutParams(layoutParams);
        } else {
            this.headerContentView.findViewById(R.id.rl_top_title).setVisibility(0);
            textView.setText(this.chapterTitle);
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mCutQuestionFlag.getLayoutParams();
            layoutParams2.topMargin = CommonUtil.dip2px(requireContext(), 40.0f);
            this.mCutQuestionFlag.setLayoutParams(layoutParams2);
        }
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.srl_refresh);
        this.smartRefreshLayout = smartRefreshLayout;
        smartRefreshLayout.setOnMultiPurposeListener(new AnonymousClass1());
        this.web = (android.webkit.WebView) this.headerContentView.findViewById(R.id.web);
        this.schildline = (LinearLayout) this.headerContentView.findViewById(R.id.schildline);
        this.ansline = (LinearLayout) this.headerContentView.findViewById(R.id.ansline);
        this.mPinnedSecListView = (CommentSectionListView) holder.get(R.id.pinnedSectionListView);
        this.nitv = (TextView) this.headerContentView.findViewById(R.id.nitv);
        this.eitv = (TextView) this.headerContentView.findViewById(R.id.eitv);
        this.typeStr = (TextView) this.headerContentView.findViewById(R.id.typeStr);
        this.pagenumtv = (TextView) this.headerContentView.findViewById(R.id.pagenumtv);
        this.tv_question_new = (TextView) this.headerContentView.findViewById(R.id.tv_question_new);
        this.titletv = (TextView) this.headerContentView.findViewById(R.id.titletv);
        this.sourcetv = (TextView) this.headerContentView.findViewById(R.id.sourcetv);
        this.imgtitle = (ImageView) this.headerContentView.findViewById(R.id.imgtitle);
        this.line_viewok = (LinearLayout) holder.get(R.id.line_viewok);
        this.zuoduil = (TextView) holder.get(R.id.zuoduil);
        this.zuocuol = (TextView) holder.get(R.id.zuocuol);
        this.btn_comment = (TextView) holder.get(R.id.btn_comment);
        this.mLyAdView = (RelativeLayout) holder.get(R.id.ly_ad_view);
        this.mImgAd = (ImageView) holder.get(R.id.img_ad);
        this.mImgCloseAd = (ImageView) holder.get(R.id.btn_close);
        this.questiondetails_btn_commentNum = (TextView) holder.get(R.id.questiondetails_btn_commentNum);
        this.headerContentView.findViewById(R.id.tv_view_comment).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.b5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13195c.lambda$initViews$0(view);
            }
        });
        this.questiondetails_tv_statistics = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_statistics);
        this.tv_statistics = (TextView) this.headerContentView.findViewById(R.id.tv_statistics);
        this.questiondetails_layout_diff = (LinearLayout) this.headerContentView.findViewById(R.id.questiondetails_layout_diff);
        this.textView_difficulty = (TextView) this.headerContentView.findViewById(R.id.textView_difficulty);
        this.questiondetails_btn_edit = (ImageView) holder.get(R.id.questiondetails_btn_edit);
        this.ly_questiondetails_btn_centerMsg = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_centerMsg);
        this.questiondetails_btn_centerMsg = (ImageView) holder.get(R.id.questiondetails_btn_centerMsg);
        this.questiondetails_btn_zantong = (ImageView) holder.get(R.id.questiondetails_btn_zantong);
        this.questiondetails_btn_collect = (ImageView) holder.get(R.id.questiondetails_btn_collect);
        this.ly_questiondetails_btn_collect = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_collect);
        this.ly_questiondetails_btn_edit = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_edit);
        this.ly_questiondetails_btn_zantong = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_zantong);
        this.ll_more_columns = (LinearLayout) this.headerContentView.findViewById(R.id.ll_more_columns);
        this.mRadioGroup_content = (LinearLayout) this.headerContentView.findViewById(R.id.mRadioGroup_content);
        this.sourcetv = (TextView) this.headerContentView.findViewById(R.id.sourcetv);
        this.biaotxt = (TextView) this.headerContentView.findViewById(R.id.biaotxt);
        this.tvAnswerAnalysis = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_contents);
        this.tv_answer_analyze = (TextView) this.headerContentView.findViewById(R.id.tv_answer_analyze);
        this.questiondetails_btn_pushAnswer = (TextView) holder.get(R.id.questiondetails_btn_pushAnswer);
        this.questiondetails_tv_outline = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_outline);
        this.lineout = (LinearLayout) this.headerContentView.findViewById(R.id.lineout);
        this.qbrel = (RelativeLayout) holder.get(R.id.qbrel);
        this.questiondetails_tv_content_ques = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_content_ques);
        this.questiondetails_tv_content_ques1 = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_content_ques1);
        this.img_webview = (WebView) this.headerContentView.findViewById(R.id.img_webview);
        this.ll_question_comment = (RelativeLayout) holder.get(R.id.ll_question_comment);
        this.rl_restore_user = (RelativeLayout) this.headerContentView.findViewById(R.id.rl_restore_user);
        this.rl_analyze_user = (RelativeLayout) this.headerContentView.findViewById(R.id.rl_analyze_user);
        this.tv_restore_to_edit = (TextView) this.headerContentView.findViewById(R.id.tv_restore_to_edit);
        this.tv_analyze_to_edit = (TextView) this.headerContentView.findViewById(R.id.tv_analyze_to_edit);
        this.tv_restore_reword = (TextView) this.headerContentView.findViewById(R.id.tv_restore_reword);
        this.tv_analyze_reword = (TextView) this.headerContentView.findViewById(R.id.tv_analyze_reword);
        this.ll_restore_enter = (LinearLayout) this.headerContentView.findViewById(R.id.ll_restore_enter);
        this.ll_analyze_enter = (LinearLayout) this.headerContentView.findViewById(R.id.ll_analyze_enter);
        this.tv_restore_edit = (TextView) this.headerContentView.findViewById(R.id.tv_restore_edit);
        this.tv_analyze_edit = (TextView) this.headerContentView.findViewById(R.id.tv_analyze_edit);
        this.view_line_analyze = this.headerContentView.findViewById(R.id.view_line_analyze);
        boolean z2 = getArguments().getBoolean("fromQuestionCombine", false);
        ImageView imageView = this.mCutQuestionFlag;
        if (TextUtils.equals("1", this.questionInfoBean.getIs_cut()) && !z2) {
            i2 = 0;
        }
        imageView.setVisibility(i2);
        RecyclerView recyclerView = (RecyclerView) this.headerContentView.findViewById(R.id.recyansitem);
        this.recyansitem = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyansitem.setNestedScrollingEnabled(false);
        this.recyansitem.setHasFixedSize(true);
        this.mLyVideoView = (RelativeLayout) this.headerContentView.findViewById(R.id.ly_video_view);
        this.mImgVideo = (RoundedImageView) this.headerContentView.findViewById(R.id.img_video);
        this.mImgAudio = (ImageView) this.headerContentView.findViewById(R.id.img_audio);
        this.mImgOriginal = (RoundedImageView) this.headerContentView.findViewById(R.id.img_original);
        this.mImgExplain = (RoundedImageView) this.headerContentView.findViewById(R.id.img_explain);
        this.mTvEncyclopediaAnalyze = (TextView) this.headerContentView.findViewById(R.id.tv_encyclopedia_analyze);
        this.mTvEcyclopediaContents = (TextView) this.headerContentView.findViewById(R.id.tv_encyclopedia_contents);
        this.mImgEncyclopediaExplain = (RoundedImageView) this.headerContentView.findViewById(R.id.img_encyclopedia_explain);
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
        this.ll_answer_analysis_layout = (LinearLayout) this.headerContentView.findViewById(R.id.ll_answer_analysis_layout);
        this.mLineRestore = this.headerContentView.findViewById(R.id.line_restore);
        this.mLineExpline = this.headerContentView.findViewById(R.id.line_expline);
        this.mLineEmpty = this.headerContentView.findViewById(R.id.line_empty);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.blackColors = ContextCompat.getColorStateList(this.mContext, R.color.black);
            this.grayColors = ContextCompat.getColorStateList(this.mContext, R.color.back_font_gray);
        } else {
            this.blackColors = ContextCompat.getColorStateList(this.mContext, R.color.question_color_night);
            this.grayColors = ContextCompat.getColorStateList(this.mContext, R.color.question_color_night);
        }
        this.drawable = ContextCompat.getDrawable(this.mContext, SkinManager.getCurrentSkinType(getActivity()) == 0 ? R.drawable.huanyuan : R.drawable.huanyuan_night);
        boolean z3 = !TextUtils.isEmpty(this.questionInfoBean.getUser_answer());
        this.hasAnswer = z3;
        this.smartRefreshLayout.setEnabled(z3);
        try {
            initQuestionType();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        this.isInit = true;
        this.tv_restore_edit.setOnClickListener(this);
        this.tv_analyze_edit.setOnClickListener(this);
        this.tv_restore_to_edit.setOnClickListener(this);
        this.tv_analyze_to_edit.setOnClickListener(this);
        this.mLyVideoView.setOnClickListener(this);
        this.mImgAudio.setOnClickListener(this);
        this.mLyPraise.setOnClickListener(this);
        this.mLyRestorePraise.setOnClickListener(this);
        this.mLyAnswerPraise.setOnClickListener(this);
        this.tvAnswerToEdit.setOnClickListener(this);
        this.tvAnswerEdit.setOnClickListener(this);
        initListView(holder);
        if (this.hasAnswer && getActivity() != null && (getActivity() instanceof AnswerQuestionActivity)) {
            ((AnswerQuestionActivity) getActivity()).questionExpose("2");
        }
        if (getActivity() instanceof AnswerQuestionActivity) {
            ((AnswerQuestionActivity) getActivity()).saveQuestionPage(this.question_id + "");
        }
    }

    public void noCollectimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no);
        } else {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no_night);
        }
    }

    public void noCommingimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.question_msg);
        } else {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.question_msg_night);
        }
    }

    public void noNoteimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit);
        } else {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit_night);
        }
    }

    public void noParise() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.dianzancourse);
        } else {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.dianzancourse_night);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 1) {
            pushComment(data.getBundleExtra("bundleIntent"));
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(final View v2) {
        final int id = v2.getId();
        if (id == R.id.tv_restore_edit) {
            Intent intent = new Intent(getActivity(), (Class<?>) QuestionRestoreListActivity.class);
            intent.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_RESTORE);
            intent.putExtra("content", this.questionInfoBean.getRestore());
            intent.putExtra("question_id", this.questionInfoBean.getId());
            if (TextUtils.isEmpty(getArguments() != null ? getArguments().getString("unit_id", "") : null)) {
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
            Intent intent2 = new Intent(getActivity(), (Class<?>) QuestionRestoreListActivity.class);
            intent2.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_OPTION_ANALYZE);
            intent2.putExtra("content", this.questionInfoBean.getOption_analysis());
            if (TextUtils.isEmpty(getArguments() != null ? getArguments().getString("unit_id", "") : null)) {
                intent2.putExtra("identity_id", getArguments().getString("identity_id", ""));
            } else {
                intent2.putExtra("identity_id", getArguments().getString("unit_id", ""));
            }
            if ("unit".equals(this.category)) {
                intent2.putExtra("type", "2");
            } else {
                intent2.putExtra("type", "1");
            }
            intent2.putExtra("question_id", this.questionInfoBean.getId());
            startActivity(intent2);
            return;
        }
        if (id == R.id.tv_answer_edit) {
            Intent intent3 = new Intent(this.mContext, (Class<?>) QuestionRestoreListActivity.class);
            intent3.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_ANALYZE);
            intent3.putExtra("content", this.questionInfoBean.getExplain());
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
            new XPopup.Builder(getActivity()).asCustom(new QuestionRestoreEditPopWindow(this.mContext, new QuestionRestoreEditPopWindow.RestoreClickIml() { // from class: com.psychiatrygarden.activity.online.fragment.SubSubjectiveQuestionFragment.5
                @Override // com.psychiatrygarden.activity.online.popwindow.QuestionRestoreEditPopWindow.RestoreClickIml
                public void mEditMethod() {
                    Intent intent4 = new Intent(SubSubjectiveQuestionFragment.this.getActivity(), (Class<?>) QuestionRestoreEditActivity.class);
                    intent4.putExtra("question_id", SubSubjectiveQuestionFragment.this.questionInfoBean.getId());
                    if (v2.getId() == R.id.tv_restore_to_edit) {
                        intent4.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_RESTORE);
                        intent4.putExtra("content", SubSubjectiveQuestionFragment.this.questionInfoBean.getRestore());
                    } else if (id == R.id.tv_analyze_to_edit) {
                        intent4.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_OPTION_ANALYZE);
                        intent4.putExtra("content", SubSubjectiveQuestionFragment.this.questionInfoBean.getOption_analysis());
                    } else {
                        intent4.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_ANALYZE);
                        intent4.putExtra("content", SubSubjectiveQuestionFragment.this.questionInfoBean.getExplain());
                    }
                    if (TextUtils.isEmpty(SubSubjectiveQuestionFragment.this.getArguments() != null ? SubSubjectiveQuestionFragment.this.getArguments().getString("unit_id", "") : null)) {
                        intent4.putExtra("identity_id", SubSubjectiveQuestionFragment.this.getArguments().getString("identity_id", ""));
                    } else {
                        intent4.putExtra("identity_id", SubSubjectiveQuestionFragment.this.getArguments().getString("unit_id", ""));
                    }
                    if ("unit".equals(SubSubjectiveQuestionFragment.this.category)) {
                        intent4.putExtra("type", "2");
                    } else {
                        intent4.putExtra("type", "1");
                    }
                    SubSubjectiveQuestionFragment.this.startActivity(intent4);
                }

                @Override // com.psychiatrygarden.activity.online.popwindow.QuestionRestoreEditPopWindow.RestoreClickIml
                public void mReEditMethod() {
                    Intent intent4 = new Intent(SubSubjectiveQuestionFragment.this.getActivity(), (Class<?>) QuestionRestoreEditActivity.class);
                    if (v2.getId() == R.id.tv_restore_to_edit) {
                        intent4.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_RESTORE);
                    } else if (id == R.id.tv_analyze_to_edit) {
                        intent4.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_OPTION_ANALYZE);
                    } else {
                        intent4.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, CommonParameter.QUESTION_ANALYZE);
                    }
                    intent4.putExtra("question_id", SubSubjectiveQuestionFragment.this.questionInfoBean.getId());
                    if (TextUtils.isEmpty(SubSubjectiveQuestionFragment.this.getArguments() != null ? SubSubjectiveQuestionFragment.this.getArguments().getString("unit_id", "") : null)) {
                        intent4.putExtra("identity_id", SubSubjectiveQuestionFragment.this.getArguments().getString("identity_id", ""));
                    } else {
                        intent4.putExtra("identity_id", SubSubjectiveQuestionFragment.this.getArguments().getString("unit_id", ""));
                    }
                    if ("unit".equals(SubSubjectiveQuestionFragment.this.category)) {
                        intent4.putExtra("type", "2");
                    } else {
                        intent4.putExtra("type", "1");
                    }
                    SubSubjectiveQuestionFragment.this.startActivity(intent4);
                }
            })).toggle();
            return;
        }
        if (id == R.id.ly_video_view) {
            this.isClickAudio = false;
            CustomAliPlayerView customAliPlayerView = this.mAudioPlayerView;
            if (customAliPlayerView != null) {
                customAliPlayerView.mAliyunVodPlayerView.onStop();
                this.mAudioPlayerView.onStopped();
            }
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put("question_id", this.questionInfoBean.getId());
            ajaxParams.put("resource_type", "1");
            ajaxParams.put("video_id", this.questionInfoBean.getStem_video_list().get(0).getVideo_id());
            QuestionDataRequest.getIntance(getActivity()).getMeidaSourceById(ajaxParams, "1", this);
            return;
        }
        if (id == R.id.img_audio) {
            this.isClickAudio = true;
            CustomAliPlayerView customAliPlayerView2 = this.mAudioPlayerView;
            if (customAliPlayerView2 == null) {
                getAudioInfo();
                return;
            }
            AliyunVodPlayerView aliyunVodPlayerView = customAliPlayerView2.mAliyunVodPlayerView;
            if (aliyunVodPlayerView == null) {
                this.mAudioPlayerView = null;
                getAudioInfo();
                return;
            } else if (!aliyunVodPlayerView.isPlaying()) {
                this.mAudioPlayerView.mAliyunVodPlayerView.rePlay();
                return;
            } else {
                this.mAudioPlayerView.mAliyunVodPlayerView.onStop();
                this.mAudioPlayerView.onStopped();
                return;
            }
        }
        if (id == R.id.ly_praise_user) {
            if (TextUtils.isEmpty(this.questionInfoBean.getStatData().getOption_analysis_praise())) {
                this.questionInfoBean.getStatData().setOption_analysis_praise("0");
            }
            analysisPraiseAction(this.questionInfoBean.getStatData().getOption_analysis_user().getId(), this.questionInfoBean.getStatData().getOption_analysis_praise().equals("1") ? "2" : "1", "3");
        } else if (id == R.id.ly_restore_praise_user) {
            if (TextUtils.isEmpty(this.questionInfoBean.getStatData().getRestore_praise())) {
                this.questionInfoBean.getStatData().setRestore_praise("0");
            }
            analysisPraiseAction(this.questionInfoBean.getStatData().getRestore_user().getId(), this.questionInfoBean.getStatData().getRestore_praise().equals("1") ? "2" : "1", "1");
        } else if (id == R.id.ly_answer_praise_user) {
            if (TextUtils.isEmpty(this.questionInfoBean.getStatData().getExplain_praise())) {
                this.questionInfoBean.getStatData().setExplain_praise("0");
            }
            analysisPraiseAction(this.questionInfoBean.getStatData().getExplain_user().getId(), this.questionInfoBean.getStatData().getExplain_praise().equals("1") ? "2" : "1", "2");
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.psychiatrygarden.activity.online.fragment.j5
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f13262a.lambda$onCreate$7((ActivityResult) obj);
            }
        });
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
        SharePreferencesUtils.removeConfig(this.question_id + "", this.mContext);
        if (getActivity() instanceof AnswerQuestionActivity) {
            ((AnswerQuestionActivity) getActivity()).removeQuestionPage(this.question_id + "");
        }
    }

    public void onEventMainThread(AnalysisBean.DataBean sDaa) {
        if (this.questionInfoBean.getId().equals(sDaa.getQuestion_id())) {
            if (TextUtils.isEmpty(this.questionInfoBean.getIs_right())) {
                doAnswing();
                this.questionInfoBean.setmAnalysisBean(sDaa);
                sDaa.setSubjective_answering("1");
            } else {
                this.questionInfoBean.setmAnalysisBean(sDaa);
            }
            initAnalyAdapterData();
            EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_ANALY_REFRESH, this.questionInfoBean));
            this.hasAnswer = true;
            this.smartRefreshLayout.setEnabled(true);
            this.pageNum = 1;
            getCommentListData();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        if (requstUrl.equals(NetworkRequestsURL.questionlabelApi)) {
            this.biaotxt.setText("标签：？");
            this.mRadioGroup_content.removeAllViews();
            return;
        }
        if (requstUrl.equals(NetworkRequestsURL.questionUserLabelApi)) {
            AlertToast("提交失败！");
            return;
        }
        if (requstUrl.equals(NetworkRequestsURL.mPutComment)) {
            if (errorNo == 401) {
                new CusomNewDialog(this.mContext).setMessage(strMsg).show();
                return;
            } else {
                ToastUtil.shortToast(getActivity(), strMsg);
                return;
            }
        }
        if (requstUrl.equals(NetworkRequestsURL.getmyAnalysisApi)) {
            initAnalyAdapterData();
        } else {
            ToastUtil.shortToast(getActivity(), strMsg);
        }
    }

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
        if (this.questionInfoBean.getStatData().getComment_count() > 10000) {
            this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d.%d万", Integer.valueOf(this.questionInfoBean.getStatData().getComment_count() / 10000), Integer.valueOf((this.questionInfoBean.getStatData().getComment_count() % 10000) / 1000)));
        } else {
            this.questiondetails_btn_commentNum.setText(String.valueOf(this.questionInfoBean.getStatData().getComment_count()));
        }
        int update_time = event.getUpdate_time();
        if (update_time <= 0) {
            this.tvEditTime.setVisibility(8);
            return;
        }
        this.tvEditTime.setVisibility(0);
        this.tvEditTime.setText(String.format("内容持续优化，最近更新时间：%s", new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date(update_time * 1000))));
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
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onSupportInvisible() {
        super.onSupportInvisible();
        try {
            if (TextUtils.isEmpty(this.start_timestamp) || !this.isAnswerVisible) {
                return;
            }
            Log.e("wwwwwwwwww", "不可见 " + this.questionInfoBean.getTitle());
            Log.e("wwwwwwwwww", "不可见 时长统计" + ((System.currentTimeMillis() - Long.parseLong(this.start_timestamp)) / 1000));
            String str = "[\"" + this.question_id + "\"]";
            String str2 = "[\"" + this.questionInfoBean.getTitle() + "\"]";
            this.questionInfoBean.setModule_type(TextUtils.isEmpty(this.questionInfoBean.getKnowledge_id()) ? this.module_type : "110");
            QuestionDetailBean questionDetailBean = this.questionInfoBean;
            questionDetailBean.setIs_redo(TextUtils.isEmpty(questionDetailBean.getIs_redo()) ? "0" : this.questionInfoBean.getIs_redo());
            this.questionInfoBean.setUnit_title(ProjectApp.unit_title);
            this.questionInfoBean.setExam_title(ProjectApp.exam_title);
            this.questionInfoBean.setIdentity_title(ProjectApp.identity_title);
            this.questionInfoBean.setChapter_title(this.chapterTitle);
            this.questionInfoBean.setChapter_parent_title(getArguments().getString("subject_title", ""));
            String json = ProjectApp.gson.toJson(this.questionInfoBean);
            AliyunEvent aliyunEvent = AliyunEvent.VisitQuestion;
            CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), str, str2, this.start_timestamp, System.currentTimeMillis() + "", json);
            this.start_timestamp = "";
        } catch (Exception unused) {
            LogUtils.e("AliyunLog", "阿里日志埋点错误");
        }
    }

    public void onVisible() {
        this.isFragmentVisible = true;
        this.start_timestamp = System.currentTimeMillis() + "";
    }

    public void postBiaoqianData(String question_id, String label_id, String count, boolean isAdmin) {
        QuestionDataRequest.getIntance(getActivity()).questionSubmitLabel(question_id, label_id, count, isAdmin, this);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.currentPageIsVisible = isVisibleToUser;
        if (isVisibleToUser) {
            long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
            this.startTime = jCurrentTimeMillis;
            SharePreferencesUtils.writeLongConfig("question_startTime", Long.valueOf(jCurrentTimeMillis), this.mContext);
            LogUtils.e("question_load_time", "startTime===>" + this.startTime);
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.u4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13363c.playAudio();
                }
            }, 600L);
            return;
        }
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
        }
    }

    public void setViewVisiable(int view) {
        if (view == 0 && "3".equals(this.questionInfoBean.getType())) {
            initStyle();
        }
        if (view == 0 && this.isFragmentVisible) {
            this.start_timestamp = System.currentTimeMillis() + "";
        }
        this.isAnswerVisible = view == 0;
        this.lineout.setVisibility(view);
        if ("search".equals(this.externalsources)) {
            this.qbrel.setVisibility(8);
        } else {
            this.qbrel.setVisibility(view);
        }
        if (ConfigUtils.isHidden(1)) {
            this.questiondetails_layout_diff.setVisibility(8);
        } else {
            this.questiondetails_layout_diff.setVisibility(view);
        }
        this.questiondetails_btn_commentNum.setVisibility(view);
        if (view == 0) {
            this.headerContentView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.v4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13372c.lambda$setViewVisiable$31();
                }
            }, 500L);
        }
        if (ConfigUtils.isHidden(5)) {
            this.ll_restore_point.setVisibility(8);
        }
        if (ConfigUtils.isHidden(6)) {
            this.ll_answer_analysis_layout.setVisibility(8);
        }
        if (ConfigUtils.isHidden(7)) {
            this.ll_encyclopedia_analysis_layout.setVisibility(8);
        }
    }

    @Override // com.psychiatrygarden.activity.answer.adapter.AnalysisAdapter.AnaImClickIml
    public void showToastView(View v2) {
        new XPopup.Builder(getActivity()).hasShadowBg(Boolean.FALSE).isCenterHorizontal(true).atView(v2).asCustom(new AnalyPopWindow(this.mContext, new String[]{"默认排序", "热度排序", "时间排序", "我赞同的"}, new int[0], new OnSelectListener() { // from class: com.psychiatrygarden.activity.online.fragment.z4
            @Override // com.lxj.xpopup.interfaces.OnSelectListener
            public final void onSelect(int i2, String str) {
                this.f13407a.lambda$showToastView$32(i2, str);
            }
        })).show();
    }

    public void showlog(List<BiaoqianBeab.DataBean> dataBiao) {
        new XPopup.Builder(getActivity()).asCustom(new BiaoPupNewWindow(this.mContext, dataBiao, new BiaoqianCallbackInterface() { // from class: com.psychiatrygarden.activity.online.fragment.c5
            @Override // com.psychiatrygarden.interfaceclass.BiaoqianCallbackInterface
            public final void mBiaoianLinster(List list, int i2, boolean z2) {
                this.f13202a.lambda$showlog$29(list, i2, z2);
            }
        })).toggle();
    }

    public void submitQuestionData() throws JSONException {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        long jLongValue = SharePreferencesUtils.readLongConfig("question_startTime", this.mContext, 0L).longValue() == 0 ? this.startTime : SharePreferencesUtils.readLongConfig("question_startTime", this.mContext, 0L).longValue();
        this.startTime = jLongValue;
        long j2 = jCurrentTimeMillis - jLongValue;
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("answer", this.questionInfoBean.getUser_answer().trim());
            jSONObject.put("question_id", this.questionInfoBean.getId());
            jSONObject.put("is_right", this.questionInfoBean.getIs_right().trim());
            jSONObject.put("app_id", this.questionInfoBean.getApp_id());
            if (getArguments() != null) {
                jSONObject.put("identity_id", getArguments().getString("identity_id", ""));
                jSONObject.put("chapter_id", getArguments().getString("chapter_id", ""));
                jSONObject.put("collection_id", getArguments().getString("unit_id", ""));
                jSONObject.put("category_id", getArguments().getString("category_id", ""));
                jSONObject.put("chapter_parent_id", getArguments().getString("chapter_parent_id", ""));
                jSONObject.put("paper_id", getArguments().getBoolean("fromQuestionCombine", false) ? Integer.parseInt(getArguments().getString("paperId", "0")) : 0);
            }
            jSONObject.put("duration", j2 + "");
            jSONObject.put(HmsMessageService.SUBJECT_ID, "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext));
            jSONArray.put(0, jSONObject);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("answer", jSONArray.toString());
        ajaxParams.put("module_type", "" + this.module_type);
        ajaxParams.put("question_category_id", getArguments().getString("question_bank_id", ""));
        LogUtils.e("question_load_time", "答题时长：提交答案接口==》" + j2);
        QuestionDataRequest.getIntance(getActivity()).questionPutAnswerData(ajaxParams, this);
        String knowledge_id = this.questionInfoBean.getKnowledge_id();
        if (TextUtils.isEmpty(this.start_timestamp)) {
            return;
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis() - Long.parseLong(this.start_timestamp);
        Gson gson = new Gson();
        this.questionInfoBean.setTime_used_ms(jCurrentTimeMillis2 + "");
        this.questionInfoBean.setModule_type(TextUtils.isEmpty(knowledge_id) ? this.module_type : "110");
        QuestionDetailBean questionDetailBean = this.questionInfoBean;
        questionDetailBean.setIs_redo(TextUtils.isEmpty(questionDetailBean.getIs_redo()) ? "0" : this.questionInfoBean.getIs_redo());
        this.questionInfoBean.setUnit_title(ProjectApp.unit_title);
        this.questionInfoBean.setExam_title(ProjectApp.exam_title);
        this.questionInfoBean.setIdentity_title(ProjectApp.identity_title);
        this.questionInfoBean.setChapter_title(this.chapterTitle);
        this.questionInfoBean.setChapter_parent_title(getArguments().getString("subject_title", ""));
        this.questionInfoBean.setChapter_parent_id(getArguments().getString("chapter_parent_id", ""));
        if (!TextUtils.isEmpty(knowledge_id)) {
            QuestionDetailBean questionDetailBean2 = this.questionInfoBean;
            questionDetailBean2.setChapter_parent_id(questionDetailBean2.getChapter_parent_id());
        }
        String json = gson.toJson(this.questionInfoBean);
        String str = "[\"" + this.questionInfoBean.getId() + "\"]";
        String str2 = "[\"" + this.questionInfoBean.getTitle() + "\"]";
        AliyunEvent aliyunEvent = AliyunEvent.SubmitAnswer;
        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, str2, json, "2");
    }

    public void titlenum() {
        this.pagenumtv.setText(CharacterParser.getSpannableColorSize(this.questionInfoBean.getSort() + " /" + this.total, 0, this.questionInfoBean.getSort().length(), SkinManager.getCurrentSkinType(getActivity()) == 1 ? "#64729F" : "#000000"));
        int width = this.pagenumtv.getVisibility() == 0 ? this.pagenumtv.getWidth() + CommonUtil.dip2px(requireContext(), 15.0f) : CommonUtil.dip2px(requireContext(), 53.0f);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mCutQuestionFlag.getLayoutParams();
        layoutParams.rightMargin = width;
        this.mCutQuestionFlag.setLayoutParams(layoutParams);
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x031c A[Catch: Exception -> 0x04ca, TryCatch #0 {Exception -> 0x04ca, blocks: (B:3:0x0010, B:7:0x001d, B:9:0x0027, B:12:0x0031, B:14:0x0048, B:15:0x0060, B:18:0x0072, B:19:0x0083, B:22:0x008f, B:24:0x009e, B:25:0x00af, B:27:0x00b7, B:29:0x00c6, B:31:0x00d0, B:33:0x00da, B:34:0x00e8, B:37:0x00f7, B:39:0x0114, B:41:0x011c, B:43:0x018f, B:45:0x0194, B:50:0x01ad, B:52:0x021f, B:54:0x0227, B:69:0x027d, B:71:0x028b, B:74:0x02ca, B:76:0x02f4, B:78:0x02fa, B:80:0x0304, B:81:0x0308, B:83:0x0310, B:85:0x0316, B:87:0x031c, B:89:0x0326, B:90:0x032a, B:92:0x0330, B:93:0x0333, B:95:0x033f, B:97:0x0386, B:96:0x0373, B:75:0x02eb, B:56:0x023b, B:58:0x0248, B:60:0x025b, B:62:0x0261, B:68:0x0274, B:65:0x026e, B:70:0x0283, B:49:0x01a7, B:98:0x03a4, B:100:0x03ad, B:102:0x03b8, B:103:0x03ca, B:105:0x03d2, B:107:0x03e9, B:108:0x03f2, B:109:0x03fa, B:111:0x0402, B:113:0x0411, B:115:0x0429, B:117:0x0431, B:121:0x0442, B:123:0x044c, B:125:0x045b, B:127:0x0461, B:129:0x0473, B:131:0x0481, B:132:0x0495, B:136:0x049e, B:138:0x04a6, B:139:0x04b4, B:141:0x04bc, B:135:0x049a, B:120:0x043d), top: B:149:0x0010, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x033f A[Catch: Exception -> 0x04ca, TryCatch #0 {Exception -> 0x04ca, blocks: (B:3:0x0010, B:7:0x001d, B:9:0x0027, B:12:0x0031, B:14:0x0048, B:15:0x0060, B:18:0x0072, B:19:0x0083, B:22:0x008f, B:24:0x009e, B:25:0x00af, B:27:0x00b7, B:29:0x00c6, B:31:0x00d0, B:33:0x00da, B:34:0x00e8, B:37:0x00f7, B:39:0x0114, B:41:0x011c, B:43:0x018f, B:45:0x0194, B:50:0x01ad, B:52:0x021f, B:54:0x0227, B:69:0x027d, B:71:0x028b, B:74:0x02ca, B:76:0x02f4, B:78:0x02fa, B:80:0x0304, B:81:0x0308, B:83:0x0310, B:85:0x0316, B:87:0x031c, B:89:0x0326, B:90:0x032a, B:92:0x0330, B:93:0x0333, B:95:0x033f, B:97:0x0386, B:96:0x0373, B:75:0x02eb, B:56:0x023b, B:58:0x0248, B:60:0x025b, B:62:0x0261, B:68:0x0274, B:65:0x026e, B:70:0x0283, B:49:0x01a7, B:98:0x03a4, B:100:0x03ad, B:102:0x03b8, B:103:0x03ca, B:105:0x03d2, B:107:0x03e9, B:108:0x03f2, B:109:0x03fa, B:111:0x0402, B:113:0x0411, B:115:0x0429, B:117:0x0431, B:121:0x0442, B:123:0x044c, B:125:0x045b, B:127:0x0461, B:129:0x0473, B:131:0x0481, B:132:0x0495, B:136:0x049e, B:138:0x04a6, B:139:0x04b4, B:141:0x04bc, B:135:0x049a, B:120:0x043d), top: B:149:0x0010, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0373 A[Catch: Exception -> 0x04ca, TryCatch #0 {Exception -> 0x04ca, blocks: (B:3:0x0010, B:7:0x001d, B:9:0x0027, B:12:0x0031, B:14:0x0048, B:15:0x0060, B:18:0x0072, B:19:0x0083, B:22:0x008f, B:24:0x009e, B:25:0x00af, B:27:0x00b7, B:29:0x00c6, B:31:0x00d0, B:33:0x00da, B:34:0x00e8, B:37:0x00f7, B:39:0x0114, B:41:0x011c, B:43:0x018f, B:45:0x0194, B:50:0x01ad, B:52:0x021f, B:54:0x0227, B:69:0x027d, B:71:0x028b, B:74:0x02ca, B:76:0x02f4, B:78:0x02fa, B:80:0x0304, B:81:0x0308, B:83:0x0310, B:85:0x0316, B:87:0x031c, B:89:0x0326, B:90:0x032a, B:92:0x0330, B:93:0x0333, B:95:0x033f, B:97:0x0386, B:96:0x0373, B:75:0x02eb, B:56:0x023b, B:58:0x0248, B:60:0x025b, B:62:0x0261, B:68:0x0274, B:65:0x026e, B:70:0x0283, B:49:0x01a7, B:98:0x03a4, B:100:0x03ad, B:102:0x03b8, B:103:0x03ca, B:105:0x03d2, B:107:0x03e9, B:108:0x03f2, B:109:0x03fa, B:111:0x0402, B:113:0x0411, B:115:0x0429, B:117:0x0431, B:121:0x0442, B:123:0x044c, B:125:0x045b, B:127:0x0461, B:129:0x0473, B:131:0x0481, B:132:0x0495, B:136:0x049e, B:138:0x04a6, B:139:0x04b4, B:141:0x04bc, B:135:0x049a, B:120:0x043d), top: B:149:0x0010, inners: #1, #2 }] */
    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onSuccess(java.lang.String r25, java.lang.String r26) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 1245
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.fragment.SubSubjectiveQuestionFragment.onSuccess(java.lang.String, java.lang.String):void");
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

    @Subscribe
    public void onEventMainThread(QuestionBack2TopEvent event) {
        if (TextUtils.equals(event.getQuestionId(), String.valueOf(this.question_id))) {
            scrollLayout();
        }
    }

    @Subscribe
    public void onEventMainThread(UpdateQuestionCutEvent event) {
        QuestionDetailBean questionDetailBean;
        if (!TextUtils.equals(event.getQuestionId(), String.valueOf(this.question_id)) || (questionDetailBean = this.questionInfoBean) == null) {
            return;
        }
        questionDetailBean.setIs_cut(event.getIsCut());
        this.mCutQuestionFlag.setVisibility("1".equals(event.getIsCut()) ? 0 : 8);
        if ("1".equals(this.questionInfoBean.getIs_cut())) {
            int width = this.pagenumtv.getVisibility() == 0 ? this.pagenumtv.getWidth() + CommonUtil.dip2px(requireContext(), 15.0f) : CommonUtil.dip2px(requireContext(), 53.0f);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mCutQuestionFlag.getLayoutParams();
            layoutParams.rightMargin = width;
            this.mCutQuestionFlag.setLayoutParams(layoutParams);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NoteEventBean noteEventBean) {
        if (noteEventBean.question_id.equals(this.questionInfoBean.getId())) {
            String str = noteEventBean.content;
            if ((str != null && !"".equals(str)) || noteEventBean.img) {
                this.questionInfoBean.getStatData().setIs_note(1);
                haveNoteimg();
            } else {
                this.questionInfoBean.getStatData().setIs_note(0);
                noNoteimg();
            }
            this.questionInfoBean.setNote(noteEventBean.content);
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
            } else {
                if ("delReplyAndLoadData".equals(str)) {
                    this.pageNum = 1;
                    getCommentListData();
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
    public void onEventMainThread(RefreshQuestionCommentEvent event) {
        if (TextUtils.equals(event.getQuestionId(), this.question_id + "")) {
            this.pageNum = 1;
            getCommentListData();
        }
    }

    public void onEventMainThread(RefreshViewStateEvent e2) {
        if (TextUtils.equals(String.valueOf(this.question_id), e2.getQuestionId())) {
            scrollLayout();
        }
    }

    public void onEventMainThread(CommentTagEvent event) {
        if (TextUtils.equals("" + this.question_id, event.getQuestionId())) {
            SharePreferencesUtils.writeBooleanConfig(this.question_id + "", event.isShow(), this.mContext);
            boolean z2 = getActivity() instanceof AnswerQuestionActivity;
            this.isPinned = event.isShow();
            int visibility = this.lineselect2.getVisibility();
            if (!this.ishavehot) {
                if (visibility != 8) {
                    this.lineselect2.setVisibility(8);
                }
            } else if (event.isShow()) {
                if (visibility != 0) {
                    this.lineselect2.setVisibility(0);
                }
            } else if (visibility != 8) {
                this.lineselect2.setVisibility(8);
            }
        }
    }
}
