package com.psychiatrygarden.activity.online.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.TextAppearanceSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView;
import com.aliyun.vod.common.utils.UriUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.CommentNewActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.NoteNewActivity;
import com.psychiatrygarden.activity.QuestionChoiceVideoAdapter;
import com.psychiatrygarden.activity.QuestionCommentActivity;
import com.psychiatrygarden.activity.online.AnswerQuestionActivity;
import com.psychiatrygarden.activity.online.adapter.EnglishExampleAdapter;
import com.psychiatrygarden.activity.online.adapter.EnglishTranAdapter;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.online.bean.QuestionStatDataBean;
import com.psychiatrygarden.activity.online.bean.event.NoteEventBean;
import com.psychiatrygarden.activity.online.popwindow.NoteNewPopWindow;
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
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.interfaceclass.onDialogNoteListener;
import com.psychiatrygarden.utils.ColorPhrase;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ConfigUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.MyNightUtil;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.utils.StringUtil;
import com.psychiatrygarden.widget.BiaoPupNewWindow;
import com.psychiatrygarden.widget.CirclePoint;
import com.psychiatrygarden.widget.CommentSectionListView;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.DialogNoteInput;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.QuestionAdWidegt;
import com.psychiatrygarden.widget.ShowVideoDialog;
import com.psychiatrygarden.widget.english.EnglishTextView;
import com.psychiatrygarden.widget.english.PopNoteList;
import com.psychiatrygarden.widget.english.PopWord;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.tencent.smtt.sdk.WebView;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
public class SubEnglishWordFragment extends BaseFragment implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, QuestionDataCallBack<String> {
    private View addFooterView;
    private AnimationDrawable animationDrawable;
    private String answerMode;
    private AnimationDrawable audioAnimDrawable;
    private TextView biaotxt;
    private ColorStateList blackColors;
    private String break_point;
    private TextView btn_comment;
    private String category;
    private CirclePoint circlePoint;
    private Drawable drawable;
    private EnglishExampleAdapter englishExampleAdapter;
    private EnglishTranAdapter englishSplitAdapter;
    private EnglishTranAdapter englishTranAdapter;
    private ColorStateList grayColors;
    private boolean hasAnswer;
    private View headerContentView;
    private ImageView imgtitle;
    private boolean isPinned;
    private boolean isRefreshEvent;
    private boolean isSdkAd;
    private boolean ishavehot;
    private ImageView iv_play;
    private View line_example_sentence;
    private View line_restore;
    private View line_split;
    private LinearLayout lineout;
    private LinearLayout lineselect2;
    private RelativeLayout lineviewtype;
    private QuestionAdWidegt llAd;
    private LinearLayout llNoComment;
    private LinearLayout llViewComment;
    private LinearLayout ll_more_columns;
    private LinearLayout ll_restore;
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
    private ImageView mImgAudio;
    private ImageView mImgCloseAd;
    private RoundedImageView mImgOriginal;
    private RoundedImageView mImgVideo;
    private RelativeLayout mLyAdView;
    private RelativeLayout mLyVideoView;
    private CommentSectionListView mPinnedSecListView;
    private PopWord mPopWordCard;
    private List<QuestionDetailBean.OptionDTO> mQuestionOptionBean;
    private LinearLayout mRadioGroup_content;
    private MediaPlayer mediaPlayer;
    private String module_type;
    private TextView pagenumtv;
    private BaseQuickAdapter<QuestionDetailBean.OptionDTO, BaseViewHolder> qAdapter;
    private RelativeLayout qbrel;
    private RecyclerView qlistview;
    private QuestionDetailBean questionDetailBean;
    private ImageView questiondetails_btn_centerMsg;
    private ImageView questiondetails_btn_collect;
    private TextView questiondetails_btn_commentNum;
    private ImageView questiondetails_btn_edit;
    private TextView questiondetails_btn_pushAnswer;
    private ImageView questiondetails_btn_zantong;
    private LinearLayout questiondetails_layout_answer;
    private LinearLayout questiondetails_layout_diff;
    private TextView questiondetails_tv_Answer;
    private TextView questiondetails_tv_content_ques;
    private TextView questiondetails_tv_outline;
    private TextView questiondetails_tv_statistics;
    private CheckedTextView remen;
    private RelativeLayout rl_question_video;
    private RecyclerView rv_example_sentence;
    private RecyclerView rv_phonetic;
    private RecyclerView rv_split;
    private SwipeRefreshLayout smartRefreshLayout;
    private TextView sourcetv;
    private long startTime;
    private TextView textView_difficulty;
    private TextView titletv;
    private int totalNewCommentNum;
    private TextView tvEditTime;
    private TextView tv_example_sentence;
    private TextView tv_phonetic;
    private TextView tv_question_new;
    private TextView tv_restore;
    private TextView tv_split;
    private TextView tv_statistics;
    private EnglishTextView tv_word;
    private TextView typeStr;
    private ShowVideoDialog videoMainDialog;
    private WebView webview;
    private CheckedTextView zuixin;
    private long question_id = 0;
    private String show_restore_img = "1";
    private final List<Integer> positionList = new ArrayList();
    private String externalsources = "";
    private boolean isClickAudio = true;
    private boolean isInit = false;
    private List<CommentBean.DataBean.HotBean> hot = new ArrayList();
    private List<CommentBean.DataBean.HotBean> time_line = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> commlist = new ArrayList();
    private int pageNum = 0;
    private boolean isLoadCommentData = false;
    private boolean isCanLoadData = true;
    private int positionTab = 0;
    private boolean hasConfigAd = false;
    private final SparseArray<ItemRecord> recordSp = new SparseArray<>(0);
    private boolean currentPageIsVisible = false;

    /* renamed from: com.psychiatrygarden.activity.online.fragment.SubEnglishWordFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends AjaxCallBack<String> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            SubEnglishWordFragment.this.mPinnedSecListView.setSelection(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1() {
            SubEnglishWordFragment.this.mCommListAdapter.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$2() {
            SubEnglishWordFragment.this.mCommListAdapter.notifyDataSetChanged();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            SubEnglishWordFragment.this.isCanLoadData = true;
            SubEnglishWordFragment.this.addFooterView.setVisibility(8);
            if (SubEnglishWordFragment.this.pageNum == 1) {
                if (SubEnglishWordFragment.this.commlist.size() > 0) {
                    SubEnglishWordFragment.this.AlertToast("请求服务器失败");
                } else if (SubEnglishWordFragment.this.isRefreshEvent) {
                    SubEnglishWordFragment.this.isRefreshEvent = false;
                    SubEnglishWordFragment.this.smartRefreshLayout.setRefreshing(false);
                }
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass2) s2);
            try {
                CommentBean commentBean = (CommentBean) new Gson().fromJson(s2, CommentBean.class);
                if (commentBean.getCode().equals("200")) {
                    SubEnglishWordFragment.this.llViewComment.setVisibility(8);
                    SubEnglishWordFragment.this.llNoComment.setVisibility(8);
                    if (SubEnglishWordFragment.this.isRefreshEvent) {
                        SubEnglishWordFragment.this.isRefreshEvent = false;
                        SubEnglishWordFragment.this.smartRefreshLayout.setRefreshing(false);
                    }
                    SubEnglishWordFragment.this.hot = commentBean.getData().getHot();
                    SubEnglishWordFragment.this.time_line = commentBean.getData().getTime_line();
                    if (SubEnglishWordFragment.this.pageNum == 1) {
                        SubEnglishWordFragment.this.commlist.clear();
                        String time_line_total = "0";
                        if (SubEnglishWordFragment.this.hot.size() <= 0 || !SubEnglishWordFragment.this.hasAnswer) {
                            SubEnglishWordFragment.this.lineselect2.setVisibility(8);
                        } else {
                            SubEnglishWordFragment subEnglishWordFragment = SubEnglishWordFragment.this;
                            subEnglishWordFragment.positionTab = subEnglishWordFragment.hot.size();
                            CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                            hotBean.setType(1);
                            StringBuilder sb = new StringBuilder();
                            sb.append("最热评论(");
                            sb.append((SubEnglishWordFragment.this.hot == null || SubEnglishWordFragment.this.hot.isEmpty()) ? "0" : commentBean.getData().getHot_total());
                            sb.append(")");
                            hotBean.setName(sb.toString());
                            SubEnglishWordFragment.this.commlist.add(hotBean);
                            if (SubEnglishWordFragment.this.isSdkAd) {
                                CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
                                if (SubEnglishWordFragment.this.hot.size() == 8) {
                                    hotBean2.setHot(true);
                                    SubEnglishWordFragment.this.hot.add(hotBean2);
                                } else if (SubEnglishWordFragment.this.hot.size() >= 9) {
                                    hotBean2.setHot(true);
                                    SubEnglishWordFragment.this.hot.add(8, hotBean2);
                                }
                            }
                            SubEnglishWordFragment.this.commlist.addAll(SubEnglishWordFragment.this.hot);
                            SubEnglishWordFragment.this.ishavehot = true;
                            if (commentBean.getData().getMore_hot() != null && "1".equals(commentBean.getData().getMore_hot())) {
                                CommentBean.DataBean.HotBean hotBean3 = new CommentBean.DataBean.HotBean();
                                hotBean3.setOtherView(3);
                                hotBean3.setName("最热评论(" + commentBean.getData().getHot_total() + ")");
                                SubEnglishWordFragment.this.commlist.add(hotBean3);
                            }
                        }
                        if (SubEnglishWordFragment.this.time_line.size() > 0 && SubEnglishWordFragment.this.hasAnswer) {
                            CommentBean.DataBean.HotBean hotBean4 = new CommentBean.DataBean.HotBean();
                            hotBean4.setType(1);
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("最新评论(");
                            if (SubEnglishWordFragment.this.time_line != null && !SubEnglishWordFragment.this.time_line.isEmpty()) {
                                time_line_total = commentBean.getData().getTime_line_total();
                            }
                            sb2.append(time_line_total);
                            sb2.append(")");
                            hotBean4.setName(sb2.toString());
                            SubEnglishWordFragment.this.commlist.add(hotBean4);
                            SubEnglishWordFragment.this.commlist.addAll(SubEnglishWordFragment.this.time_line);
                        }
                        SubEnglishWordFragment.this.mCommListAdapter.setIsSdkAd(SubEnglishWordFragment.this.isSdkAd);
                        SubEnglishWordFragment.this.mCommListAdapter.setShowAd(SubEnglishWordFragment.this.hot.size() >= 8 && SubEnglishWordFragment.this.hasConfigAd);
                        SubEnglishWordFragment.this.mCommListAdapter.setList(SubEnglishWordFragment.this.commlist);
                        SubEnglishWordFragment.this.mCommListAdapter.setRefeault(SubEnglishWordFragment.this.time_line);
                        SubEnglishWordFragment subEnglishWordFragment2 = SubEnglishWordFragment.this;
                        subEnglishWordFragment2.totalNewCommentNum = (subEnglishWordFragment2.time_line == null || SubEnglishWordFragment.this.time_line.isEmpty()) ? 0 : Integer.parseInt(commentBean.getData().getTime_line_total());
                        if (SubEnglishWordFragment.this.isLoadCommentData) {
                            SubEnglishWordFragment.this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.c4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f13201c.lambda$onSuccess$0();
                                }
                            }, 310L);
                            SubEnglishWordFragment.this.mPinnedSecListView.smoothScrollToPositionFromTop(1, 0, 300);
                            if (SubEnglishWordFragment.this.commlist.size() >= 8) {
                                EventBus.getDefault().post(new HideChapterTitleEvent(true, String.valueOf(SubEnglishWordFragment.this.question_id)));
                            }
                            if (SubEnglishWordFragment.this.ishavehot) {
                                SubEnglishWordFragment.this.lineselect2.setVisibility(0);
                            } else {
                                SubEnglishWordFragment.this.lineselect2.setVisibility(8);
                            }
                        }
                        if (!SubEnglishWordFragment.this.isSdkAd) {
                            if (SubEnglishWordFragment.this.hot.size() >= 8) {
                                SubEnglishWordFragment.this.getCommentAd();
                            } else {
                                SubEnglishWordFragment.this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.d4
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        this.f13208c.lambda$onSuccess$1();
                                    }
                                });
                            }
                        }
                        if (SubEnglishWordFragment.this.commlist.size() == 0) {
                            SubEnglishWordFragment.this.addFooterView.setVisibility(8);
                        }
                    } else {
                        SubEnglishWordFragment.this.addFooterView.setVisibility(8);
                        if (SubEnglishWordFragment.this.time_line.size() == 0) {
                            SubEnglishWordFragment.this.AlertToast("已经是最后一条");
                            SubEnglishWordFragment.access$320(SubEnglishWordFragment.this, 1);
                        } else {
                            SubEnglishWordFragment.this.commlist.addAll(SubEnglishWordFragment.this.time_line);
                            SubEnglishWordFragment.this.mCommListAdapter.setRefeault(SubEnglishWordFragment.this.time_line);
                            SubEnglishWordFragment.this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.e4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f13218c.lambda$onSuccess$2();
                                }
                            });
                        }
                    }
                } else {
                    SubEnglishWordFragment.this.addFooterView.setVisibility(8);
                    SubEnglishWordFragment.this.AlertToast(commentBean.getMessage());
                    if (SubEnglishWordFragment.this.isRefreshEvent) {
                        SubEnglishWordFragment.this.isRefreshEvent = false;
                        SubEnglishWordFragment.this.smartRefreshLayout.setRefreshing(false);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                SubEnglishWordFragment.this.addFooterView.setVisibility(8);
                if (SubEnglishWordFragment.this.isRefreshEvent) {
                    SubEnglishWordFragment.this.isRefreshEvent = false;
                    SubEnglishWordFragment.this.smartRefreshLayout.setRefreshing(false);
                }
            }
            SubEnglishWordFragment.this.isCanLoadData = true;
        }
    }

    /* renamed from: com.psychiatrygarden.activity.online.fragment.SubEnglishWordFragment$3, reason: invalid class name */
    public class AnonymousClass3 extends AjaxCallBack<String> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            SubEnglishWordFragment.this.mCommListAdapter.notifyDataSetChanged();
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
                JSONObject jSONObject = new JSONObject(json);
                if (jSONObject.optString("code").equals("200")) {
                    if ((SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, ((BaseFragment) SubEnglishWordFragment.this).mContext, 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, ((BaseFragment) SubEnglishWordFragment.this).mContext, 0L).longValue()) / 1000) - jSONObject.optJSONObject("data").optLong("time_interval", 0L) : 0L) < 0 || TextUtils.isEmpty(jSONObject.optJSONObject("data").optString("img"))) {
                        return;
                    }
                    CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                    boolean z2 = true;
                    hotBean.setHot(true);
                    hotBean.setAds(jSONObject.optString("data"));
                    if (SubEnglishWordFragment.this.hot.size() >= 8) {
                        SubEnglishWordFragment.this.commlist.add(9, hotBean);
                        SubEnglishWordFragment.this.hasConfigAd = true;
                        QuestionCommentListAdapter questionCommentListAdapter = SubEnglishWordFragment.this.mCommListAdapter;
                        if (SubEnglishWordFragment.this.hot.size() < 8 || !SubEnglishWordFragment.this.hasConfigAd) {
                            z2 = false;
                        }
                        questionCommentListAdapter.setShowAd(z2);
                        SubEnglishWordFragment.this.mCommListAdapter.setList(SubEnglishWordFragment.this.commlist);
                        SubEnglishWordFragment.this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.f4
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f13225c.lambda$onSuccess$0();
                            }
                        });
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.online.fragment.SubEnglishWordFragment$7, reason: invalid class name */
    public class AnonymousClass7 implements EnglishTextView.OnWordClickListener {
        public AnonymousClass7() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onWordClickListener$0() throws IllegalStateException {
            SubEnglishWordFragment.this.mPopWordCard.releasePlayer();
            SubEnglishWordFragment.this.tv_word.setClickedRectList(new HashMap());
        }

        @Override // com.psychiatrygarden.widget.english.EnglishTextView.OnWordClickListener
        public void onDataInitListener(Map<String, Map<RectF, String>> sentenceLines) {
        }

        @Override // com.psychiatrygarden.widget.english.EnglishTextView.OnWordClickListener
        public void onWordClickListener(Map<RectF, String> rectFStringMap, String word) {
            if (SubEnglishWordFragment.this.mPopWordCard != null && SubEnglishWordFragment.this.mPopWordCard.isShow()) {
                SubEnglishWordFragment.this.mPopWordCard.dismiss();
                return;
            }
            SubEnglishWordFragment subEnglishWordFragment = SubEnglishWordFragment.this;
            subEnglishWordFragment.mPopWordCard = new PopWord(((BaseFragment) subEnglishWordFragment).mContext, SubEnglishWordFragment.this.tv_word.getRawY(), ScreenUtil.getPxByDp(((BaseFragment) SubEnglishWordFragment.this).mContext, 88), ScreenUtil.getScreenHeight((Activity) ((BaseFragment) SubEnglishWordFragment.this).mContext) - ScreenUtil.getPxByDp(((BaseFragment) SubEnglishWordFragment.this).mContext, 103), new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.activity.online.fragment.g4
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() throws IllegalStateException {
                    this.f13233c.lambda$onWordClickListener$0();
                }
            }, word, "");
            SubEnglishWordFragment.this.mPopWordCard.show(SubEnglishWordFragment.this.tv_word.getRootView());
        }
    }

    /* renamed from: com.psychiatrygarden.activity.online.fragment.SubEnglishWordFragment$8, reason: invalid class name */
    public class AnonymousClass8 extends BaseQuickAdapter<QuestionDetailBean.OptionDTO, BaseViewHolder> {
        public AnonymousClass8(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(ImageView imageView, QuestionDetailBean.OptionDTO optionDTO, View view) {
            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(((BaseFragment) SubEnglishWordFragment.this).mContext).setSingleSrcView(imageView, optionDTO.getImg()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader.popupInfo = new PopupInfo();
            xPopupImageLoader.show();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder helper, final QuestionDetailBean.OptionDTO item) {
            ImageView imageView = (ImageView) helper.getView(R.id.QuestionOptions_item_img_select);
            TextView textView = (TextView) helper.getView(R.id.QuestionOptions_item_tv_content);
            final ImageView imageView2 = (ImageView) helper.getView(R.id.optionimg);
            WebView webView = (WebView) helper.getView(R.id.webview);
            textView.setText(String.format(Locale.CHINA, "%s.%s", item.getKey(), item.getTitle()));
            textView.setTextSize(0, ScreenUtil.getPxBySp(((BaseFragment) SubEnglishWordFragment.this).mContext, 17) - ((SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, ((BaseFragment) SubEnglishWordFragment.this).mContext, 2) - 2) * ScreenUtil.getPxBySp(((BaseFragment) SubEnglishWordFragment.this).mContext, 2)));
            String type = item.getType();
            if (TextUtils.isEmpty(item.getImg())) {
                imageView2.setVisibility(8);
                webView.setVisibility(8);
            } else {
                imageView2.setVisibility(0);
                webView.setVisibility(0);
                webView.loadDataWithBaseURL(null, "<html><head><title></title></head><body><img style='height:auto;max-width:100%' src=" + item.getImg() + " /></body></html>", "text/html", "utf-8", null);
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.h4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f13242c.lambda$convert$0(imageView2, item, view);
                    }
                });
            }
            if (type == null || type.equals("0")) {
                if (SubEnglishWordFragment.this.answerMode.equals(Constants.ANSWER_MODE.RECITE_MODE) && SubEnglishWordFragment.this.questionDetailBean.getAnswer().contains(item.getKey())) {
                    if (SkinManager.getCurrentSkinType(((BaseFragment) SubEnglishWordFragment.this).mContext) == 0) {
                        textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubEnglishWordFragment.this).mContext, R.color.black));
                        imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubEnglishWordFragment.this).mContext, R.drawable.wx));
                        return;
                    } else {
                        textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubEnglishWordFragment.this).mContext, R.color.question_color_night));
                        imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubEnglishWordFragment.this).mContext, R.drawable.wx));
                        return;
                    }
                }
                if (SkinManager.getCurrentSkinType(((BaseFragment) SubEnglishWordFragment.this).mContext) == 0) {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubEnglishWordFragment.this).mContext, R.color.black));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubEnglishWordFragment.this).mContext, R.drawable.icon_options_select_no));
                    return;
                } else {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubEnglishWordFragment.this).mContext, R.color.question_color_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubEnglishWordFragment.this).mContext, R.drawable.icon_options_select_no_night));
                    return;
                }
            }
            if (type.equals("1")) {
                if (SkinManager.getCurrentSkinType(((BaseFragment) SubEnglishWordFragment.this).mContext) == 0) {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubEnglishWordFragment.this).mContext, R.color.black));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubEnglishWordFragment.this).mContext, R.drawable.icon_options_select_yes));
                    return;
                } else {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubEnglishWordFragment.this).mContext, R.color.question_color_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubEnglishWordFragment.this).mContext, R.drawable.icon_options_select_yes_night));
                    return;
                }
            }
            if (type.equals("2")) {
                if (SkinManager.getCurrentSkinType(((BaseFragment) SubEnglishWordFragment.this).mContext) == 0) {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubEnglishWordFragment.this).mContext, R.color.green_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubEnglishWordFragment.this).mContext, R.drawable.icon_options_select_ok));
                    return;
                } else {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubEnglishWordFragment.this).mContext, R.color.green_theme_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubEnglishWordFragment.this).mContext, R.drawable.ic_option_right));
                    return;
                }
            }
            if (type.equals("3")) {
                if (SkinManager.getCurrentSkinType(((BaseFragment) SubEnglishWordFragment.this).mContext) == 0) {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubEnglishWordFragment.this).mContext, R.color.red_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubEnglishWordFragment.this).mContext, R.drawable.icon_options_select_error));
                    return;
                } else {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubEnglishWordFragment.this).mContext, R.color.red_theme_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubEnglishWordFragment.this).mContext, R.drawable.ic_option_wrong));
                    return;
                }
            }
            if (type.equals("4")) {
                if (SkinManager.getCurrentSkinType(((BaseFragment) SubEnglishWordFragment.this).mContext) == 0) {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubEnglishWordFragment.this).mContext, R.color.red_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubEnglishWordFragment.this).mContext, R.drawable.icon_options_select_ok_lack));
                } else {
                    textView.setTextColor(MyNightUtil.getColor(((BaseFragment) SubEnglishWordFragment.this).mContext, R.color.red_theme_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(((BaseFragment) SubEnglishWordFragment.this).mContext, R.drawable.icon_options_select_ok_lack_night));
                }
            }
        }
    }

    public static class ItemRecord {
        int height;

        /* renamed from: top, reason: collision with root package name */
        int f13170top;

        private ItemRecord() {
            this.height = 0;
            this.f13170top = 0;
        }
    }

    public static /* synthetic */ int access$308(SubEnglishWordFragment subEnglishWordFragment) {
        int i2 = subEnglishWordFragment.pageNum;
        subEnglishWordFragment.pageNum = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$320(SubEnglishWordFragment subEnglishWordFragment, int i2) {
        int i3 = subEnglishWordFragment.pageNum - i2;
        subEnglishWordFragment.pageNum = i3;
        return i3;
    }

    private void getAudioInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.questionDetailBean.getId());
        ajaxParams.put("resource_type", "2");
        ajaxParams.put("video_id", this.questionDetailBean.getStem_audio_list().get(0).getVideo_id());
        QuestionDataRequest.getIntance(this.mContext).getMeidaSourceById(ajaxParams, "2", this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentAd() {
        if (this.hasAnswer) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put("identity_id", "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext));
            YJYHttpUtils.get(this.mContext, NetworkRequestsURL.adsInCommentApi, ajaxParams, new AnonymousClass3());
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
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mCommentList, ajaxParams, new AnonymousClass2());
    }

    public static SubEnglishWordFragment getInstance(Bundle args) {
        SubEnglishWordFragment subEnglishWordFragment = new SubEnglishWordFragment();
        subEnglishWordFragment.setArguments(args);
        return subEnglishWordFragment;
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
        return i4 - itemRecord.f13170top;
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
            this.mImgCloseAd.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.y3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13398c.lambda$initAd$31(mContext, view);
                }
            });
        }
        if (dataAd.getAds() == null || dataAd.getAds().size() <= 0) {
            return;
        }
        GlideApp.with(mContext).load((Object) GlideUtils.generateUrl(dataAd.getAds().get(0).getImg())).placeholder(R.mipmap.ic_order_default).into(this.mImgAd);
        this.mImgAd.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.z3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubEnglishWordFragment.lambda$initAd$32(dataAd, view);
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
        this.mAudioPlayerView.setOnPlayStatusLisenter(new CustomAliPlayerView.OnAudioPlayStatusListenter() { // from class: com.psychiatrygarden.activity.online.fragment.SubEnglishWordFragment.10
            @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.OnAudioPlayStatusListenter
            public void onPlayEnd() {
                if (SubEnglishWordFragment.this.audioAnimDrawable != null) {
                    SubEnglishWordFragment.this.audioAnimDrawable.stop();
                    SubEnglishWordFragment.this.mImgAudio.setImageResource(R.drawable.audio_play_end_anim);
                }
            }

            @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.OnAudioPlayStatusListenter
            public void onPlaying() {
                if (SubEnglishWordFragment.this.isClickAudio) {
                    SubEnglishWordFragment.this.mImgAudio.setImageResource(R.drawable.audio_play_anim);
                    SubEnglishWordFragment subEnglishWordFragment = SubEnglishWordFragment.this;
                    subEnglishWordFragment.audioAnimDrawable = (AnimationDrawable) subEnglishWordFragment.mImgAudio.getDrawable();
                    SubEnglishWordFragment.this.audioAnimDrawable.start();
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
        this.mPinnedSecListView.setTag(String.valueOf(this.question_id));
        setListener();
        this.addFooterView = View.inflate(this.mContext, R.layout.activity_hideview, null);
        this.mPinnedSecListView.addHeaderView(this.headerContentView);
        QuestionCommentListAdapter questionCommentListAdapter = new QuestionCommentListAdapter(this, this.mContext, this.commlist, this.time_line, 1, "2", this.question_id + "", (AnswerQuestionActivity) getActivity(), true, true);
        this.mCommListAdapter = questionCommentListAdapter;
        this.mPinnedSecListView.setAdapter((ListAdapter) questionCommentListAdapter);
        this.mPinnedSecListView.addFooterView(this.addFooterView);
        this.mCommListAdapter.setActionListener(new QuestionCommentListAdapter.OnCommentActionListener() { // from class: com.psychiatrygarden.activity.online.fragment.k3
            @Override // com.psychiatrygarden.adapter.QuestionCommentListAdapter.OnCommentActionListener
            public final void onPraiseAction(boolean z2) {
                this.f13269a.lambda$initListView$2(z2);
            }
        });
        this.addFooterView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void isSelect(boolean renmenTrue, boolean zuixinTrue) {
        this.remen.setChecked(renmenTrue);
        this.zuixin.setChecked(zuixinTrue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$26(String str) {
        Intent intent = new Intent(this.mContext, (Class<?>) NoteNewActivity.class);
        intent.putExtra("question_id", this.questionDetailBean.getId());
        intent.putExtra("notestr", str);
        intent.putExtra("module_type", this.module_type);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$engWordAns$20(QuestionDetailBean.EnglishLableBean englishLableBean, BaseQuickAdapter baseQuickAdapter, View view, int i2) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if (TextUtils.isEmpty(englishLableBean.getExample().get(i2).getExample_pronunciation())) {
            return;
        }
        playWord(view, englishLableBean.getExample().get(i2).getExample_pronunciation());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAd$31(Context context, View view) {
        SharePreferencesUtils.writeLongConfig(CommonParameter.DISMESS_TIME_QUESTION_BOTTOM_AD, Long.valueOf(System.currentTimeMillis()), context);
        this.mLyAdView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initAd$32(HomepageSmallAdBean.DataDTO.DataAd dataAd, View view) {
        PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(dataAd.getAds().get(0).getExtra()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBiaoQianData$15(List list, View view) {
        showlog(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBiaoQianData$16(List list, View view) {
        showlog(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListView$2(boolean z2) {
        updateBottomView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionList$21() {
        this.qAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionList$22(BaseQuickAdapter baseQuickAdapter, View view, int i2) throws JSONException {
        if (TextUtils.isEmpty(this.questionDetailBean.getUser_answer())) {
            doSelectOption(i2);
            this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.b3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13193c.lambda$initQuestionList$21();
                }
            });
            if (!this.answerMode.equals(Constants.ANSWER_MODE.QUICK_BRUSH_MODE) || this.questionDetailBean.getAnswer().replaceAll(",", "").length() > 1) {
                return;
            }
            pushAnswer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$8(View view) {
        showlog(this.questionDetailBean.getDataBiao());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRestoreData$18(View view) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.questionDetailBean.getRestore_img());
        CommonUtil.doPicture(this.mContext, arrayList, 0, null, R.drawable.kaodianhuanyuan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTitleImg$17(View view) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(this.imgtitle, this.questionDetailBean.getTitle_img()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0() {
        this.smartRefreshLayout.setRefreshing(true);
        this.pageNum = 1;
        this.isRefreshEvent = true;
        getCommentListData();
        updateBottomView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        this.questiondetails_btn_commentNum.performClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$23(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", str);
        bundle.putInt("result", 1);
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            pushComment(bundle);
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$24(QuestionNoteBean questionNoteBean) {
        this.questionDetailBean.getStatData().setIs_note(1);
        haveNoteimg();
        this.questionDetailBean.setNote(questionNoteBean.getContent());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSuccess$29(List list, View view) {
        showlog(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSuccess$30() {
        this.mCommListAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pushAnswer$27() {
        this.qAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pushAnswer$28() {
        this.qAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scroll2Position$7(int i2) {
        this.mPinnedSecListView.setSelection(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setFontSize$10() {
        this.englishTranAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setFontSize$11() {
        this.englishSplitAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setFontSize$12() {
        this.englishExampleAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setFontSize$9() {
        this.qAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListener$3(AdapterView adapterView, View view, int i2, long j2) {
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
        intent.putExtra("question_id", this.question_id + "");
        intent.putExtra("title", ((Object) textView.getText()) + "");
        String str = this.module_type;
        intent.putExtra("module_type", str == null ? 0 : Integer.parseInt(str));
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListener$4() {
        if (!this.ishavehot || this.mPinnedSecListView.getFirstVisiblePosition() <= 0) {
            this.lineselect2.setVisibility(8);
        } else if (this.lineselect2.getVisibility() == 8) {
            this.lineselect2.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListener$5(View view) {
        if (this.isPinned) {
            EventBus.getDefault().post(new HideChapterTitleEvent(true, String.valueOf(this.question_id)));
        }
        if (this.commlist.size() <= 0) {
            this.isLoadCommentData = true;
            this.pageNum = 1;
            getCommentListData();
            return;
        }
        this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.b4
            @Override // java.lang.Runnable
            public final void run() {
                this.f13194c.lambda$setListener$4();
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
    public /* synthetic */ void lambda$setListener$6(View view) {
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
    public /* synthetic */ void lambda$setUserVisibleHint$25() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        QuestionDetailBean questionDetailBean = this.questionDetailBean;
        if (questionDetailBean != null) {
            playWord(this.iv_play, questionDetailBean.getEnglish_label().getWord().getPronunciation());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setViewVisiable$19() {
        if (getActivity() == null || getActivity().isDestroyed()) {
            return;
        }
        int statusBarHeight = getActivity().getResources().getDisplayMetrics().heightPixels - StatusBarUtil.getStatusBarHeight(this.mContext);
        if (this.qbrel.getVisibility() == 0) {
            statusBarHeight -= this.qbrel.getHeight();
        }
        int height = this.headerContentView.getHeight();
        LogUtils.d("headerContentView", this.questionDetailBean.getTitle() + "，fullScreenHeight = " + statusBarHeight + ",header-height = " + height);
        if (height >= statusBarHeight) {
            this.llViewComment.setVisibility(8);
            this.llNoComment.setVisibility(8);
        } else {
            int comment_count = this.questionDetailBean.getStatData().getComment_count();
            this.llViewComment.setVisibility(comment_count <= 0 ? 8 : 0);
            this.llNoComment.setVisibility(comment_count <= 0 ? 0 : 8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showlog$14(List list, int i2, boolean z2) {
        ArrayList arrayList = new ArrayList();
        if (list != null && list.size() > 0) {
            for (int i3 = 0; i3 < list.size(); i3++) {
                if (((BiaoqianBeab.DataBean) list.get(i3)).getUser_label().equals("1")) {
                    arrayList.add(((BiaoqianBeab.DataBean) list.get(i3)).getId());
                }
            }
            if (!arrayList.isEmpty()) {
                for (int i4 = 0; i4 < arrayList.size(); i4++) {
                    arrayList.size();
                }
            }
            QuestionDataRequest.getIntance(this.mContext).questionSubmitLabel(this.question_id + "", ((BiaoqianBeab.DataBean) list.get(i2)).getId(), String.valueOf(((BiaoqianBeab.DataBean) list.get(i2)).getCount()), z2, this);
        }
        if (list != null) {
            Collections.sort(list);
            initBiaoQianData(list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateCutIconLayout$13() {
        int width = this.lineviewtype.getVisibility() == 0 ? this.pagenumtv.getWidth() + CommonUtil.dip2px(requireContext(), 15.0f) : CommonUtil.dip2px(requireContext(), 53.0f);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mCutQuestionFlag.getLayoutParams();
        layoutParams.rightMargin = width;
        this.mCutQuestionFlag.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playAudio() {
        initAd(this.mContext);
        int iIndexOf = this.positionList.indexOf(Integer.valueOf(this.mCurrentPosition));
        if (this.positionList.contains(Integer.valueOf(this.mCurrentPosition)) && this.currentPageIsVisible && this.mCurrentPosition == this.positionList.get(iIndexOf).intValue()) {
            if (this.questionDetailBean.getStem_audio_list() == null || this.questionDetailBean.getStem_audio_list().size() <= 0) {
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

    private void playWord(View view, String playUrl) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        AnimationDrawable animationDrawable;
        try {
            MediaPlayer mediaPlayer = this.mediaPlayer;
            if (mediaPlayer != null && mediaPlayer.isPlaying() && (animationDrawable = this.animationDrawable) != null) {
                animationDrawable.stop();
                this.animationDrawable.setVisible(true, true);
                releasePlayer();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.animationDrawable = (AnimationDrawable) ((ImageView) view).getDrawable();
        try {
            MediaPlayer mediaPlayer2 = new MediaPlayer();
            this.mediaPlayer = mediaPlayer2;
            mediaPlayer2.setDataSource(playUrl);
            this.mediaPlayer.setAudioStreamType(3);
            this.mediaPlayer.prepareAsync();
            this.mediaPlayer.setOnPreparedListener(this);
            this.mediaPlayer.setOnCompletionListener(this);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0073  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void pushAnswer() throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 340
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.fragment.SubEnglishWordFragment.pushAnswer():void");
    }

    private void pushComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("obj_id", this.question_id + "");
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("module_type", "1");
        ajaxParams.put("comment_type", "2");
        final String string = b3.getString("parent_id");
        if (!TextUtils.isEmpty(string) && !"0".equals(string)) {
            ajaxParams.put("parent_id", string);
        }
        String string2 = b3.getString("to_user_id");
        if (!TextUtils.isEmpty(string2) && !"0".equals(string2)) {
            ajaxParams.put("to_user_id", string2);
        }
        String string3 = b3.getString("reply_primary_id");
        if (!TextUtils.isEmpty(string3) && !"0".equals(string3)) {
            ajaxParams.put("reply_primary_id", string3);
        }
        if (!TextUtils.isEmpty(string) && !"0".equals(string) && !TextUtils.isEmpty(string3) && !"0".equals(string3) && !TextUtils.isEmpty(string2)) {
            "0".equals(string2);
        }
        String string4 = b3.getString("b_img");
        String string5 = b3.getString("s_img");
        if (!TextUtils.isEmpty(string4)) {
            if (string4.contains("http")) {
                ajaxParams.put("b_img", string4);
                ajaxParams.put("s_img", string5);
            } else {
                ajaxParams.put("video_id", b3.getString("b_img"));
            }
        }
        Context context = this.mContext;
        if (context instanceof AnswerQuestionActivity) {
            String videoId = ((AnswerQuestionActivity) context).getVideoId();
            if (!TextUtils.isEmpty(videoId)) {
                b3.putString("video_id", videoId);
            }
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            ajaxParams.put("virtual_user_id", b3.getString("virtual_user_id"));
        }
        showProgressDialog("发布中");
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.fragment.SubEnglishWordFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SubEnglishWordFragment.this.hideProgressDialog();
                SubEnglishWordFragment.this.AlertToast("请求服务器失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws JSONException {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        CommentBean.DataBean.HotBean hotBean = (CommentBean.DataBean.HotBean) new Gson().fromJson(jSONObject.getJSONObject("data").toString(), CommentBean.DataBean.HotBean.class);
                        SubEnglishWordFragment.this.questionDetailBean.getStatData().setIs_comment(1);
                        SubEnglishWordFragment.this.havaCommingimg();
                        if (SubEnglishWordFragment.this.mCommListAdapter.getList().size() > 0) {
                            if (TextUtils.isEmpty(string)) {
                                SubEnglishWordFragment.this.mCommListAdapter.getList().add(SubEnglishWordFragment.this.getNewPositions(), hotBean);
                                SubEnglishWordFragment.this.zuixin.performClick();
                            } else {
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= SubEnglishWordFragment.this.mCommListAdapter.getList().size()) {
                                        i2 = -1;
                                        break;
                                    }
                                    CommentBean.DataBean.HotBean hotBean2 = SubEnglishWordFragment.this.mCommListAdapter.getList().get(i2);
                                    if (hotBean2.getType() != 1 && !hotBean2.isHot() && TextUtils.equals(hotBean2.getId(), string)) {
                                        break;
                                    } else {
                                        i2++;
                                    }
                                }
                                if (i2 > -1) {
                                    SubEnglishWordFragment.this.mCommListAdapter.getList().set(i2, hotBean);
                                }
                            }
                            SubEnglishWordFragment.this.mCommListAdapter.notifyDataSetChanged();
                        } else {
                            SubEnglishWordFragment.this.pageNum = 1;
                            SubEnglishWordFragment.this.getCommentListData();
                            SubEnglishWordFragment.this.zuixin.performClick();
                        }
                        ProjectApp.comment_content = "";
                        ProjectApp.comment_b_img = "";
                        ProjectApp.comment_s_img = "";
                        ProjectApp.commentvideoPath = "";
                        ProjectApp.commentvideoImage = "";
                        ProjectApp.commentvideoId = "";
                        ProjectApp.commentvideoImagePath = "";
                        SubEnglishWordFragment.this.AlertToast(jSONObject.optString("message"));
                        if (SubEnglishWordFragment.this.questionDetailBean.getStatData() != null) {
                            SubEnglishWordFragment.this.questionDetailBean.getStatData().setComment_count(SubEnglishWordFragment.this.questionDetailBean.getStatData().getComment_count() + 1);
                            int comment_count = SubEnglishWordFragment.this.questionDetailBean.getStatData().getComment_count();
                            if (comment_count > 10000) {
                                SubEnglishWordFragment.this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d.%d万", Integer.valueOf(comment_count / 10000), Integer.valueOf((comment_count % 10000) / 1000)));
                            } else {
                                SubEnglishWordFragment.this.questiondetails_btn_commentNum.setText(comment_count + "");
                            }
                        }
                        if (jSONObject.has("is_collection")) {
                            String string6 = jSONObject.getString("is_collection");
                            if (TextUtils.isEmpty(string6)) {
                                SubEnglishWordFragment.this.noCollectimg();
                            } else if ("1".equals(string6)) {
                                SubEnglishWordFragment.this.havaCollectimg();
                            }
                        }
                        if (jSONObject.has("is_praise")) {
                            String string7 = jSONObject.getString("is_praise");
                            if (TextUtils.isEmpty(string7)) {
                                SubEnglishWordFragment.this.noParise();
                            } else if ("1".equals(string7)) {
                                SubEnglishWordFragment.this.haveParise();
                            }
                        }
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        CommonUtil.showFristDialog(jSONObject);
                    } else if (jSONObject.optString("code").equals("401")) {
                        new CusomNewDialog(((BaseFragment) SubEnglishWordFragment.this).mContext).setMessage(jSONObject.optString("message")).show();
                    } else {
                        SubEnglishWordFragment.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception unused) {
                    SubEnglishWordFragment.this.AlertToast("数据异常！");
                }
                SubEnglishWordFragment.this.hideProgressDialog();
            }
        });
    }

    private void scroll2Position(final int pos) {
        this.mPinnedSecListView.smoothScrollToPosition(pos);
        this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.a4
            @Override // java.lang.Runnable
            public final void run() {
                this.f13179c.lambda$scroll2Position$7(pos);
            }
        }, 310L);
    }

    private void scrollLayout() {
        this.mPinnedSecListView.smoothScrollToPositionFromTop(0, 0);
    }

    @SuppressLint({"NotifyDataSetChanged"})
    private void setFontSize() {
        int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, this.mContext, 2);
        int pxBySp = ScreenUtil.getPxBySp(this.mContext, 2);
        TextView textView = this.typeStr;
        float f2 = (intConfig - 2) * pxBySp;
        textView.setTextSize(0, ((Float) textView.getTag()).floatValue() - f2);
        TextView textView2 = this.pagenumtv;
        textView2.setTextSize(0, ((Float) textView2.getTag()).floatValue() - f2);
        TextView textView3 = this.titletv;
        textView3.setTextSize(0, ((Float) textView3.getTag()).floatValue() - f2);
        TextView textView4 = this.questiondetails_btn_commentNum;
        textView4.setTextSize(0, ((Float) textView4.getTag()).floatValue() - f2);
        TextView textView5 = this.textView_difficulty;
        textView5.setTextSize(0, ((Float) textView5.getTag()).floatValue() - f2);
        TextView textView6 = this.questiondetails_tv_Answer;
        textView6.setTextSize(0, ((Float) textView6.getTag()).floatValue() - f2);
        TextView textView7 = this.questiondetails_tv_statistics;
        textView7.setTextSize(0, ((Float) textView7.getTag()).floatValue() - f2);
        TextView textView8 = this.tv_statistics;
        textView8.setTextSize(0, ((Float) textView8.getTag()).floatValue() - f2);
        TextView textView9 = this.biaotxt;
        textView9.setTextSize(0, ((Float) textView9.getTag()).floatValue() - f2);
        TextView textView10 = this.questiondetails_tv_outline;
        textView10.setTextSize(0, ((Float) textView10.getTag()).floatValue() - f2);
        TextView textView11 = this.sourcetv;
        textView11.setTextSize(0, ((Float) textView11.getTag()).floatValue() - f2);
        TextView textView12 = this.questiondetails_tv_content_ques;
        textView12.setTextSize(0, ((Float) textView12.getTag()).floatValue() - f2);
        EnglishTextView englishTextView = this.tv_word;
        englishTextView.setTextSize(ScreenUtil.px2sp(this.mContext, ((Float) englishTextView.getTag()).floatValue() - f2));
        TextView textView13 = this.tv_phonetic;
        textView13.setTextSize(0, ((Float) textView13.getTag()).floatValue() - f2);
        TextView textView14 = this.tv_split;
        textView14.setTextSize(0, ((Float) textView14.getTag()).floatValue() - f2);
        TextView textView15 = this.tv_example_sentence;
        textView15.setTextSize(0, ((Float) textView15.getTag()).floatValue() - f2);
        TextView textView16 = this.tv_restore;
        textView16.setTextSize(0, ((Float) textView16.getTag()).floatValue() - f2);
        BaseQuickAdapter<QuestionDetailBean.OptionDTO, BaseViewHolder> baseQuickAdapter = this.qAdapter;
        if (baseQuickAdapter != null) {
            baseQuickAdapter.notifyDataSetChanged();
            this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.w2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13379c.lambda$setFontSize$9();
                }
            });
        }
        if (this.englishTranAdapter != null) {
            this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.x2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13388c.lambda$setFontSize$10();
                }
            });
        }
        if (this.englishSplitAdapter != null) {
            this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.y2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13397c.lambda$setFontSize$11();
                }
            });
        }
        if (this.englishExampleAdapter != null) {
            this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.z2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13405c.lambda$setFontSize$12();
                }
            });
        }
        if (this.questionDetailBean.getDataBiao() != null) {
            initBiaoQianData(this.questionDetailBean.getDataBiao());
        }
    }

    private void setListener() {
        this.mPinnedSecListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.v3
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f13371c.lambda$setListener$3(adapterView, view, i2, j2);
            }
        });
        this.remen.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.w3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13380c.lambda$setListener$5(view);
            }
        });
        this.zuixin.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.x3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13389c.lambda$setListener$6(view);
            }
        });
        this.mPinnedSecListView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.psychiatrygarden.activity.online.fragment.SubEnglishWordFragment.1
            private int oldFirstVisibleItem;
            private int oldTop;

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (SubEnglishWordFragment.this.hasAnswer) {
                    if (SubEnglishWordFragment.this.positionTab > 0) {
                        if (firstVisibleItem > SubEnglishWordFragment.this.positionTab) {
                            SubEnglishWordFragment.this.isSelect(false, true);
                        } else {
                            SubEnglishWordFragment.this.isSelect(true, false);
                        }
                    } else if (firstVisibleItem > SubEnglishWordFragment.this.positionTab + 1) {
                        SubEnglishWordFragment.this.isSelect(false, true);
                    } else {
                        SubEnglishWordFragment.this.isSelect(true, false);
                    }
                    SubEnglishWordFragment.this.mCurrentFirstVisibleItem = firstVisibleItem;
                    View childAt = view.getChildAt(0);
                    if (childAt != null) {
                        ItemRecord itemRecord = (ItemRecord) SubEnglishWordFragment.this.recordSp.get(firstVisibleItem);
                        if (itemRecord == null) {
                            itemRecord = new ItemRecord();
                        }
                        itemRecord.height = childAt.getHeight();
                        itemRecord.f13170top = childAt.getTop();
                        SubEnglishWordFragment.this.recordSp.append(firstVisibleItem, itemRecord);
                        int scrollY = SubEnglishWordFragment.this.getScrollY();
                        if (SubEnglishWordFragment.this.getActivity() != null && (SubEnglishWordFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                            ((AnswerQuestionActivity) SubEnglishWordFragment.this.getActivity()).onScrollChange(scrollY, String.valueOf(SubEnglishWordFragment.this.question_id), SubEnglishWordFragment.this.headerContentView.getHeight());
                        }
                    }
                    int top2 = childAt == null ? 0 : childAt.getTop();
                    int i2 = this.oldFirstVisibleItem;
                    if (firstVisibleItem == i2) {
                        int i3 = this.oldTop;
                        if (top2 > i3) {
                            if (SubEnglishWordFragment.this.getActivity() != null && (SubEnglishWordFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                                ((AnswerQuestionActivity) SubEnglishWordFragment.this.getActivity()).showHideBack2TopIcon(true);
                            }
                        } else if (top2 < i3 && (SubEnglishWordFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                            ((AnswerQuestionActivity) SubEnglishWordFragment.this.getActivity()).showHideBack2TopIcon(false);
                        }
                    } else if (firstVisibleItem < i2) {
                        if (SubEnglishWordFragment.this.getActivity() != null && (SubEnglishWordFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                            ((AnswerQuestionActivity) SubEnglishWordFragment.this.getActivity()).showHideBack2TopIcon(true);
                        }
                    } else if (SubEnglishWordFragment.this.getActivity() != null && (SubEnglishWordFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                        ((AnswerQuestionActivity) SubEnglishWordFragment.this.getActivity()).showHideBack2TopIcon(false);
                    }
                    this.oldTop = top2;
                    this.oldFirstVisibleItem = firstVisibleItem;
                }
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (SubEnglishWordFragment.this.hasAnswer && scrollState == 0) {
                    if (SubEnglishWordFragment.this.getActivity() != null && (SubEnglishWordFragment.this.getActivity() instanceof AnswerQuestionActivity)) {
                        ((AnswerQuestionActivity) SubEnglishWordFragment.this.getActivity()).showHideBack2TopIcon(this.oldTop != 0);
                    }
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        if (SubEnglishWordFragment.this.addFooterView.getVisibility() == 8) {
                            SubEnglishWordFragment.this.addFooterView.setVisibility(0);
                        }
                        if (SubEnglishWordFragment.this.isCanLoadData) {
                            SubEnglishWordFragment.this.isCanLoadData = false;
                            SubEnglishWordFragment.access$308(SubEnglishWordFragment.this);
                            SubEnglishWordFragment.this.getCommentListData();
                        }
                    }
                }
            }
        });
    }

    private void updateBottomView() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.questionDetailBean.getId());
        ajaxParams.put("module_type", this.module_type);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getstatApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.fragment.SubEnglishWordFragment.9
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
                super.onSuccess((AnonymousClass9) s2);
                try {
                    if (!new JSONObject(s2).optString("code", "").equals("200") || (questionStatDataBean = (QuestionStatDataBean) new Gson().fromJson(s2, QuestionStatDataBean.class)) == null) {
                        return;
                    }
                    SubEnglishWordFragment.this.questionDetailBean.getStatData().setComment_count(questionStatDataBean.getData().getComment_count());
                    SubEnglishWordFragment.this.questionDetailBean.getStatData().setIs_comment(questionStatDataBean.getData().getIs_comment());
                    SubEnglishWordFragment.this.questionDetailBean.getStatData().setIs_collection(questionStatDataBean.getData().getIs_collection());
                    SubEnglishWordFragment.this.questionDetailBean.getStatData().setIs_praise(questionStatDataBean.getData().getIs_praise());
                    SubEnglishWordFragment.this.onMessage(questionStatDataBean.getData());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void updateCutIcon() {
        if (TextUtils.equals("1", this.questionDetailBean.getIs_cut())) {
            this.mCutQuestionFlag.setImageResource(SkinManager.getCurrentSkinType(this.mContext) == 0 ? R.drawable.ic_cut_day : R.drawable.ic_cut_night);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mCutQuestionFlag.getLayoutParams();
            if (this.headerContentView.findViewById(R.id.rl_top_title).getVisibility() != 0) {
                layoutParams.topMargin = CommonUtil.dip2px(this.mContext, !"1".equals(this.questionDetailBean.getIs_new()) ? 10.0f : 30.0f);
            } else if ("1".equals(this.questionDetailBean.getIs_new())) {
                layoutParams.topMargin = CommonUtil.dip2px(requireContext(), 65.0f);
            } else {
                layoutParams.topMargin = CommonUtil.dip2px(requireContext(), 45.0f);
            }
            this.mCutQuestionFlag.setLayoutParams(layoutParams);
        }
    }

    private void updateCutIconLayout() {
        this.pagenumtv.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.f3
            @Override // java.lang.Runnable
            public final void run() {
                this.f13224c.lambda$updateCutIconLayout$13();
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
        if (ConfigUtils.isHidden(4)) {
            this.questiondetails_tv_outline.setVisibility(8);
        }
    }

    public void dialogNote(final String content) {
        new XPopup.Builder(this.mContext).asCustom(new NoteNewPopWindow(this.mContext, content, new NoteNewPopWindow.NoteClickIml() { // from class: com.psychiatrygarden.activity.online.fragment.a3
            @Override // com.psychiatrygarden.activity.online.popwindow.NoteNewPopWindow.NoteClickIml
            public final void mDoClickMethod() {
                this.f13177a.lambda$dialogNote$26(content);
            }
        })).toggle();
    }

    public void doChangeColor() {
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
                this.questiondetails_btn_pushAnswer.setTextColor(Color.parseColor("#64729F"));
                return;
            } else {
                this.questiondetails_btn_pushAnswer.setTextColor(Color.parseColor("#000000"));
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            this.questiondetails_btn_pushAnswer.setTextColor(Color.parseColor("#38456D"));
        } else {
            this.questiondetails_btn_pushAnswer.setTextColor(Color.parseColor("#B7B7B7"));
        }
    }

    public void doPushAnsData() throws JSONException {
        String str;
        JSONArray jSONArray;
        JSONArray jSONArray2;
        StringBuilder sb = new StringBuilder();
        List<QuestionDetailBean.OptionDTO> list = this.mQuestionOptionBean;
        if (list == null || list.size() <= 0) {
            return;
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            str = "1";
            if (i2 >= this.mQuestionOptionBean.size()) {
                break;
            }
            if (!TextUtils.isEmpty(this.mQuestionOptionBean.get(i2).getType()) && this.mQuestionOptionBean.get(i2).getType().equals("1")) {
                sb.append(this.mQuestionOptionBean.get(i2).getKey());
                sb.append(",");
                i3++;
            }
            i2++;
        }
        if (i3 == 0) {
            NewToast.showShort(this.mContext, "请选择答案", 0).show();
            return;
        }
        String strTrim = this.questionDetailBean.getAnswer().replace(",", "").trim();
        String strTrim2 = sb.toString().replace(",", "").trim();
        if (!strTrim.equals(strTrim2)) {
            if (this.questionDetailBean.getStatData() != null && this.questionDetailBean.getStatData().getAnswer() != null) {
                this.questionDetailBean.getStatData().getAnswer().setWrong_count(this.questionDetailBean.getStatData().getAnswer().getWrong_count() + 1);
            }
            str = "0";
        } else if (this.questionDetailBean.getStatData() != null && this.questionDetailBean.getStatData().getAnswer() != null) {
            this.questionDetailBean.getStatData().getAnswer().setRight_count(this.questionDetailBean.getStatData().getAnswer().getRight_count() + 1);
        }
        this.questionDetailBean.setUser_answer(strTrim2);
        initStaticData();
        JSONArray jSONArray3 = new JSONArray();
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        long jLongValue = SharePreferencesUtils.readLongConfig("question_startTime", this.mContext, 0L).longValue() == 0 ? this.startTime : SharePreferencesUtils.readLongConfig("question_startTime", this.mContext, 0L).longValue();
        this.startTime = jLongValue;
        long j2 = jCurrentTimeMillis - jLongValue;
        try {
            JSONObject jSONObject = new JSONObject();
            if (getArguments() != null) {
                jSONArray2 = jSONArray3;
                try {
                    jSONObject.put("paper_id", getArguments().getBoolean("fromQuestionCombine", false) ? Integer.parseInt(getArguments().getString("paperId", "0")) : 0);
                    jSONObject.put("identity_id", getArguments().getString("identity_id", ""));
                    jSONObject.put("chapter_id", getArguments().getString("chapter_id", ""));
                    jSONObject.put("collection_id", getArguments().getString("unit_id", ""));
                    jSONObject.put("category_id", getArguments().getString("category_id", ""));
                    jSONObject.put("chapter_parent_id", getArguments().getString("chapter_parent_id", ""));
                } catch (Exception e2) {
                    e = e2;
                    jSONArray = jSONArray2;
                    e.printStackTrace();
                    LogUtils.e("question_load_time", "答题时长：提交答案接口==》" + j2);
                    AjaxParams ajaxParams = new AjaxParams();
                    ajaxParams.put("answer", jSONArray.toString());
                    ajaxParams.put("module_type", this.module_type);
                    ajaxParams.put("question_category_id", getArguments().getString("question_bank_id", ""));
                    QuestionDataRequest.getIntance(this.mContext).questionPutAnswerData(ajaxParams, this);
                }
            } else {
                jSONArray2 = jSONArray3;
            }
            jSONObject.put("answer", strTrim2);
            jSONObject.put("question_id", this.question_id);
            jSONObject.put("is_right", str);
            jSONObject.put("app_id", this.questionDetailBean.getApp_id());
            jSONObject.put("duration", j2 + "");
            jSONObject.put(HmsMessageService.SUBJECT_ID, "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext));
            jSONArray = jSONArray2;
            try {
                jSONArray.put(jSONObject);
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                LogUtils.e("question_load_time", "答题时长：提交答案接口==》" + j2);
                AjaxParams ajaxParams2 = new AjaxParams();
                ajaxParams2.put("answer", jSONArray.toString());
                ajaxParams2.put("module_type", this.module_type);
                ajaxParams2.put("question_category_id", getArguments().getString("question_bank_id", ""));
                QuestionDataRequest.getIntance(this.mContext).questionPutAnswerData(ajaxParams2, this);
            }
        } catch (Exception e4) {
            e = e4;
            jSONArray = jSONArray3;
        }
        LogUtils.e("question_load_time", "答题时长：提交答案接口==》" + j2);
        AjaxParams ajaxParams22 = new AjaxParams();
        ajaxParams22.put("answer", jSONArray.toString());
        ajaxParams22.put("module_type", this.module_type);
        ajaxParams22.put("question_category_id", getArguments().getString("question_bank_id", ""));
        QuestionDataRequest.getIntance(this.mContext).questionPutAnswerData(ajaxParams22, this);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0035  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doSelectOption(int r9) {
        /*
            Method dump skipped, instructions count: 348
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.fragment.SubEnglishWordFragment.doSelectOption(int):void");
    }

    public void engWordAns() {
        final QuestionDetailBean.EnglishLableBean english_label = this.questionDetailBean.getEnglish_label();
        this.tv_word.setTextBold(true);
        this.tv_word.setText(StringUtil.replaceCharacter(english_label.getWord().getTitle()));
        this.tv_word.setOnWordClickListener(new AnonymousClass7());
        if (TextUtils.isEmpty(english_label.getWord().getPronunciation())) {
            this.iv_play.setVisibility(8);
        } else {
            this.iv_play.setVisibility(0);
            this.iv_play.setOnClickListener(this);
        }
        if (TextUtils.isEmpty(english_label.getWord().getSymbols())) {
            this.tv_phonetic.setVisibility(8);
        } else {
            this.tv_phonetic.setVisibility(0);
            this.tv_phonetic.setText("/ " + english_label.getWord().getSymbols() + " /");
        }
        if (english_label.getWord().getTranslate() == null || english_label.getWord().getTranslate().size() <= 0) {
            this.rv_phonetic.setVisibility(8);
        } else {
            this.rv_phonetic.setVisibility(0);
            this.rv_phonetic.setLayoutManager(new LinearLayoutManager(this.mContext));
            EnglishTranAdapter englishTranAdapter = new EnglishTranAdapter(english_label.getWord().getTranslate(), false);
            this.englishTranAdapter = englishTranAdapter;
            this.rv_phonetic.setAdapter(englishTranAdapter);
        }
        if (english_label.getMemory() == null || english_label.getMemory().size() <= 0) {
            this.tv_split.setVisibility(8);
            this.line_split.setVisibility(8);
            this.rv_split.setVisibility(8);
        } else {
            this.tv_split.setVisibility(0);
            this.line_split.setVisibility(0);
            this.rv_split.setVisibility(0);
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < english_label.getMemory().size(); i2++) {
                if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
                    arrayList.add("<font color='#5f5f79'>" + english_label.getMemory().get(i2).getMemory_label() + "</font>  " + english_label.getMemory().get(i2).getMemory());
                } else {
                    arrayList.add("<font color='#8C8C8C'>" + english_label.getMemory().get(i2).getMemory_label() + "</font>  " + english_label.getMemory().get(i2).getMemory());
                }
            }
            this.rv_split.setLayoutManager(new LinearLayoutManager(this.mContext));
            EnglishTranAdapter englishTranAdapter2 = new EnglishTranAdapter(arrayList, true);
            this.englishSplitAdapter = englishTranAdapter2;
            this.rv_split.setAdapter(englishTranAdapter2);
        }
        if (english_label.getExample() == null || english_label.getExample().size() <= 0) {
            this.tv_example_sentence.setVisibility(8);
            this.line_example_sentence.setVisibility(8);
            this.rv_example_sentence.setVisibility(8);
            return;
        }
        this.tv_example_sentence.setVisibility(0);
        this.line_example_sentence.setVisibility(0);
        this.rv_example_sentence.setVisibility(0);
        this.rv_example_sentence.setLayoutManager(new LinearLayoutManager(this.mContext));
        EnglishExampleAdapter englishExampleAdapter = new EnglishExampleAdapter(english_label.getExample());
        this.englishExampleAdapter = englishExampleAdapter;
        this.rv_example_sentence.setAdapter(englishExampleAdapter);
        this.englishExampleAdapter.addChildClickViewIds(R.id.iv_english_example_play);
        this.englishExampleAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.e3
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i3) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                this.f13216c.lambda$engWordAns$20(english_label, baseQuickAdapter, view, i3);
            }
        });
    }

    public void getBiaoData() {
        QuestionDataRequest.getIntance(this.mContext).questionlabel(String.valueOf(this.question_id), this);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_sub_english_word;
    }

    public void getNoteData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.questionDetailBean.getId());
        ajaxParams.put("module_type", this.module_type);
        QuestionDataRequest.getIntance(this.mContext).questionNoteData(ajaxParams, this);
    }

    public void getRestoreStr(String str, TextView mTextV, String number) {
        SpannableStringBuilder spannableStringBuilder;
        String strReplaceFirst = str.replaceFirst("（", "(").replaceFirst("）", ")");
        if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_JOININ_GROUP) || ("unit".equals(this.category) && this.show_restore_img.equals("0"))) {
            spannableStringBuilder = new SpannableStringBuilder(strReplaceFirst);
        } else {
            Matcher matcher = Pattern.compile("[(（]([\\u4E00-\\u9FA5]+)?P[0-9]+(-P?([0-9]+)?)?+[)）]").matcher(strReplaceFirst);
            String str2 = NetworkRequestsURL.CommentIameUrl2;
            final ArrayList arrayList = new ArrayList();
            int i2 = 0;
            while (matcher.find()) {
                String strGroup = matcher.group();
                strReplaceFirst = strReplaceFirst.substring(0, matcher.end(0) + i2) + "&" + strReplaceFirst.substring(matcher.end(0) + i2);
                String native_app_id = this.questionDetailBean.getNative_app_id();
                if (TextUtils.isEmpty(native_app_id) || native_app_id.equals("0")) {
                    native_app_id = this.questionDetailBean.getApp_id();
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(native_app_id);
                sb.append("/");
                sb.append(number);
                sb.append(strGroup.substring(1, strGroup.length() - 1));
                sb.append("-");
                i2++;
                sb.append(i2);
                sb.append(".jpg?x-oss-process=style/water_mark");
                arrayList.add(sb.toString());
            }
            Matcher matcher2 = Pattern.compile("[(（]([\\u4E00-\\u9FA5]+)?P[0-9]+(-P?([0-9]+)?)?+[)）]").matcher(strReplaceFirst);
            spannableStringBuilder = new SpannableStringBuilder(strReplaceFirst);
            Paint.FontMetrics fontMetrics = mTextV.getPaint().getFontMetrics();
            int iCeil = ((int) Math.ceil(fontMetrics.descent - fontMetrics.top)) + 2;
            this.drawable.setBounds(0, 0, (((this.drawable.getIntrinsicWidth() * iCeil) / this.drawable.getIntrinsicHeight()) / 10) * 7, (iCeil / 10) * 7);
            final int i3 = 0;
            while (matcher2.find()) {
                spannableStringBuilder.setSpan(new StickerSpan(this.drawable, 1), matcher2.end(0), matcher2.end(0) + 1, 33);
                spannableStringBuilder.setSpan(new ClickableSpan() { // from class: com.psychiatrygarden.activity.online.fragment.SubEnglishWordFragment.6
                    @Override // android.text.style.ClickableSpan
                    public void onClick(@NonNull View widget) {
                        CommonUtil.doPicture(((BaseFragment) SubEnglishWordFragment.this).mContext, arrayList, i3, null, R.drawable.kaodianhuanyuan);
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(@NonNull TextPaint ds) {
                        if (SkinManager.getCurrentSkinType(((BaseFragment) SubEnglishWordFragment.this).mContext) == 0) {
                            ds.setColor(Color.parseColor("#FF1C1A15"));
                        } else {
                            ds.setColor(MyNightUtil.getColor(((BaseFragment) SubEnglishWordFragment.this).mContext, R.color.question_color_night));
                        }
                    }
                }, matcher2.start(0), matcher2.end(0), 33);
                mTextV.setMovementMethod(LinkMovementMethod.getInstance());
                i3++;
            }
        }
        Matcher matcher3 = Pattern.compile("[(（]*.[A-E]+(\\s+)?对.*?[)）]").matcher(strReplaceFirst);
        while (matcher3.find()) {
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, this.blackColors, null), matcher3.start(0), matcher3.end(0), 34);
        }
        Matcher matcher4 = Pattern.compile("[(（]*.[A-E]+(\\s+)?错.*?[)）]").matcher(strReplaceFirst);
        while (matcher4.find()) {
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, this.blackColors, null), matcher4.start(0), matcher4.end(0), 34);
        }
        Matcher matcher5 = Pattern.compile("[(（]([\\u4E00-\\u9FA5]+)?P[0-9]+(-P?([0-9]+)?)?+[)）]").matcher(strReplaceFirst);
        int i4 = SkinManager.getCurrentSkinType(getActivity()) == 1 ? R.color.dominant_color_night : R.color.dominant_color;
        while (matcher5.find()) {
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(ContextCompat.getColor(this.mContext, i4)), null), matcher5.start(0), matcher5.end(0), 34);
        }
        mTextV.setText(spannableStringBuilder);
    }

    public void getStaticsData() {
        if (this.questionDetailBean.getStatData().getAnswer() != null) {
            initStaticData();
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.questionDetailBean.getId());
        ajaxParams.put("module_type", this.module_type);
        QuestionDataRequest.getIntance(this.mContext).questionStatData(ajaxParams, this);
    }

    public void havaCollectimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes);
        } else {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes_night);
        }
    }

    public void havaCommingimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.youpinglun);
        } else {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.youpinglun_night);
        }
    }

    public void haveNoteimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited);
        } else {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited_night);
        }
    }

    public void haveParise() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan);
        } else {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan_night);
        }
    }

    public void initBiaoQianData(final List<BiaoqianBeab.DataBean> dataBiao) {
        if (this.mContext == null) {
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
                textView.setTextSize(0, textView.getTextSize() - ((SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, this.mContext, 2) - 2) * ScreenUtil.getPxBySp(this.mContext, 2)));
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView.setBackgroundResource(R.drawable.gray_deek_round_bg);
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color));
                } else {
                    textView.setBackgroundResource(R.drawable.gray_deek_round_bg_night);
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
                }
                textView.setText("点击为本题添加标签");
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.m3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f13285c.lambda$initBiaoQianData$15(dataBiao, view);
                    }
                });
                this.mRadioGroup_content.addView(textView);
            } else {
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
                        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.n3
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f13293c.lambda$initBiaoQianData$16(dataBiao, view);
                            }
                        });
                        this.mRadioGroup_content.addView(textView2);
                    }
                }
            }
        }
        if (ConfigUtils.isHidden(3)) {
            this.headerContentView.findViewById(R.id.linbiaoqian).setVisibility(8);
        }
    }

    public void initQuestionList() {
        AnonymousClass8 anonymousClass8 = new AnonymousClass8(R.layout.item_questionoptions_listview, this.mQuestionOptionBean);
        this.qAdapter = anonymousClass8;
        this.qlistview.setAdapter(anonymousClass8);
        this.qAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.g3
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) throws JSONException {
                this.f13232c.lambda$initQuestionList$22(baseQuickAdapter, view, i2);
            }
        });
    }

    public void initQuestionType() {
        initSubViewData();
        this.isInit = true;
        if (this.questionDetailBean.getIs_new().equals("1")) {
            this.tv_question_new.setVisibility(0);
        } else {
            this.tv_question_new.setVisibility(4);
        }
        this.typeStr.setText(this.questionDetailBean.getType_str());
        if (TextUtils.isEmpty(this.questionDetailBean.getSource())) {
            this.sourcetv.setVisibility(8);
        } else {
            this.sourcetv.setVisibility(0);
            this.sourcetv.setText(String.format(Locale.CHINA, "来源：%s", this.questionDetailBean.getSource()));
        }
        if (this.questionDetailBean.getStem_video_list() == null || this.questionDetailBean.getStem_video_list().size() <= 0) {
            this.mLyVideoView.setVisibility(8);
        } else {
            this.mLyVideoView.setVisibility(0);
            Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(this.questionDetailBean.getStem_video_list().get(0).getThumb())).placeholder(new ColorDrawable(ContextCompat.getColor(this.mImgVideo.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).override(ScreenUtil.getPxByDp(this.mContext, R2.anim.voice_from_icon), ScreenUtil.getPxByDp(this.mContext, 94)).into(this.mImgVideo);
        }
        if (this.positionList.size() == 0 || !this.positionList.contains(Integer.valueOf(this.mCurrentPosition))) {
            this.positionList.add(Integer.valueOf(this.mCurrentPosition));
        }
        titlenum();
        updateCutIcon();
        initQuestionList();
        bigOrAns();
        engWordAns();
        initRestoreData();
        initVideoData();
        initTitleImg();
        getStaticsData();
        if (this.questionDetailBean.getDataBiao() != null) {
            initBiaoQianData(this.questionDetailBean.getDataBiao());
            this.ll_more_columns.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.l3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13277c.lambda$initQuestionType$8(view);
                }
            });
        } else {
            getBiaoData();
        }
        this.questiondetails_btn_pushAnswer.setOnClickListener(this);
        this.questiondetails_btn_commentNum.setOnClickListener(this);
        this.ly_questiondetails_btn_collect.setOnClickListener(this);
        this.ly_questiondetails_btn_edit.setOnClickListener(this);
        this.ly_questiondetails_btn_centerMsg.setOnClickListener(this);
        this.ly_questiondetails_btn_zantong.setOnClickListener(this);
        this.btn_comment.setOnClickListener(this);
        this.mLyVideoView.setOnClickListener(this);
        this.mImgAudio.setOnClickListener(this);
        TextView textView = this.typeStr;
        textView.setTag(Float.valueOf(textView.getTextSize()));
        TextView textView2 = this.pagenumtv;
        textView2.setTag(Float.valueOf(textView2.getTextSize()));
        TextView textView3 = this.titletv;
        textView3.setTag(Float.valueOf(textView3.getTextSize()));
        TextView textView4 = this.questiondetails_btn_commentNum;
        textView4.setTag(Float.valueOf(textView4.getTextSize()));
        TextView textView5 = this.questiondetails_tv_Answer;
        textView5.setTag(Float.valueOf(textView5.getTextSize()));
        TextView textView6 = this.questiondetails_tv_statistics;
        textView6.setTag(Float.valueOf(textView6.getTextSize()));
        TextView textView7 = this.tv_statistics;
        textView7.setTag(Float.valueOf(textView7.getTextSize()));
        TextView textView8 = this.biaotxt;
        textView8.setTag(Float.valueOf(textView8.getTextSize()));
        TextView textView9 = this.questiondetails_tv_outline;
        textView9.setTag(Float.valueOf(textView9.getTextSize()));
        TextView textView10 = this.sourcetv;
        textView10.setTag(Float.valueOf(textView10.getTextSize()));
        EnglishTextView englishTextView = this.tv_word;
        englishTextView.setTag(Float.valueOf(englishTextView.getTextSize()));
        TextView textView11 = this.tv_phonetic;
        textView11.setTag(Float.valueOf(textView11.getTextSize()));
        TextView textView12 = this.tv_split;
        textView12.setTag(Float.valueOf(textView12.getTextSize()));
        TextView textView13 = this.tv_example_sentence;
        textView13.setTag(Float.valueOf(textView13.getTextSize()));
        TextView textView14 = this.questiondetails_tv_content_ques;
        textView14.setTag(Float.valueOf(textView14.getTextSize()));
        TextView textView15 = this.tv_restore;
        textView15.setTag(Float.valueOf(textView15.getTextSize()));
        TextView textView16 = this.textView_difficulty;
        textView16.setTag(Float.valueOf(textView16.getTextSize()));
        setFontSize();
    }

    public void initRestoreData() {
        try {
            boolean zIsEmpty = TextUtils.isEmpty(this.questionDetailBean.getRestore());
            int i2 = R.color.fourth_line_backgroup_color;
            if (zIsEmpty) {
                this.questiondetails_tv_content_ques.setVisibility(8);
                this.tv_restore.setVisibility(8);
                this.line_restore.setVisibility(8);
                if (TextUtils.isEmpty(this.questionDetailBean.getRestore_img())) {
                    this.mImgOriginal.setVisibility(8);
                    this.ll_restore.setVisibility(8);
                } else {
                    this.mImgOriginal.setVisibility(0);
                    RequestBuilder<Drawable> requestBuilderLoad = Glide.with(this).load((Object) GlideUtils.generateUrl(this.questionDetailBean.getRestore_img()));
                    Context context = this.mImgOriginal.getContext();
                    if (SkinManager.getCurrentSkinType(ProjectApp.instance()) != 0) {
                        i2 = R.color.bg_backgroud_night;
                    }
                    requestBuilderLoad.placeholder(new ColorDrawable(ContextCompat.getColor(context, i2))).into(this.mImgOriginal);
                }
            } else {
                this.questiondetails_tv_content_ques.setVisibility(0);
                this.tv_restore.setVisibility(0);
                this.line_restore.setVisibility(0);
                if (TextUtils.isEmpty(this.questionDetailBean.getRestore_img())) {
                    this.mImgOriginal.setVisibility(8);
                } else {
                    this.mImgOriginal.setVisibility(0);
                    RequestBuilder<Drawable> requestBuilderLoad2 = Glide.with(this).load((Object) GlideUtils.generateUrl(this.questionDetailBean.getRestore_img()));
                    Context context2 = this.mImgOriginal.getContext();
                    if (SkinManager.getCurrentSkinType(ProjectApp.instance()) != 0) {
                        i2 = R.color.bg_backgroud_night;
                    }
                    requestBuilderLoad2.placeholder(new ColorDrawable(ContextCompat.getColor(context2, i2))).into(this.mImgOriginal);
                }
                getRestoreStr(this.questionDetailBean.getRestore(), this.questiondetails_tv_content_ques, this.questionDetailBean.getNumber());
            }
            this.mImgOriginal.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.u2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13361c.lambda$initRestoreData$18(view);
                }
            });
            if (ConfigUtils.isHidden(5)) {
                this.ll_restore.setVisibility(8);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void initSelectedAnsed() {
        String strTrim = this.questionDetailBean.getAnswer().replace(",", "").trim();
        String strTrim2 = this.questionDetailBean.getUser_answer().replace(",", "").trim();
        if (TextUtils.equals(strTrim2, "0")) {
            return;
        }
        if (strTrim.length() <= 1) {
            char[] charArray = strTrim.toCharArray();
            if (charArray[0] - 'A' >= this.mQuestionOptionBean.size() || charArray[0] - 'A' < 0) {
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
        char[] charArray3 = strTrim.toCharArray();
        if (TextUtils.isEmpty(strTrim2)) {
            for (char c4 : charArray3) {
                int i2 = c4 - 'A';
                if (i2 >= this.mQuestionOptionBean.size() || i2 < 0) {
                    return;
                }
                this.mQuestionOptionBean.get(i2).setType("4");
            }
            return;
        }
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

    public void initStaticData() {
        double right_count;
        try {
            if (this.mContext == null) {
                return;
            }
            int i2 = 0;
            if (this.questionDetailBean.getStatData().getAnswer() != null) {
                if (this.questionDetailBean.getStatData().getRight_count() + this.questionDetailBean.getStatData().getWrong_count() > 0) {
                    right_count = (this.questionDetailBean.getStatData().getRight_count() * 100) / (this.questionDetailBean.getStatData().getRight_count() + this.questionDetailBean.getStatData().getWrong_count());
                    CommonUtil.getNumber(right_count);
                } else {
                    right_count = 0.0d;
                }
                onMessage(this.questionDetailBean.getStatData());
                if (this.questionDetailBean.getStatData().getAnswer().getRight_count() + this.questionDetailBean.getStatData().getAnswer().getWrong_count() > 0) {
                    String stat_info = this.questionDetailBean.getStatData().getStat_info();
                    if (!"search".equals(this.externalsources)) {
                        stat_info = stat_info + "本人作答{" + (this.questionDetailBean.getStatData().getAnswer().getRight_count() + this.questionDetailBean.getStatData().getAnswer().getWrong_count()) + "}次，对{" + this.questionDetailBean.getStatData().getAnswer().getRight_count() + "}次，正确率{" + CommonUtil.getNumber((this.questionDetailBean.getStatData().getAnswer().getRight_count() * 100) / (this.questionDetailBean.getStatData().getAnswer().getRight_count() + this.questionDetailBean.getStatData().getAnswer().getWrong_count())) + "%}";
                    }
                    if (right_count > (this.questionDetailBean.getStatData().getAnswer().getRight_count() * 100) / (this.questionDetailBean.getStatData().getAnswer().getRight_count() + this.questionDetailBean.getStatData().getAnswer().getWrong_count())) {
                        this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(this.mContext) == 0 ? ColorPhrase.from(stat_info).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(stat_info).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-10194273).format());
                    } else {
                        this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(this.mContext) == 0 ? ColorPhrase.from(stat_info).withSeparator(StrPool.EMPTY_JSON).innerColor(-10572282).outerColor(-16777216).format() : ColorPhrase.from(stat_info).withSeparator(StrPool.EMPTY_JSON).innerColor(-10572282).outerColor(-10194273).format());
                    }
                } else {
                    String stat_info2 = this.questionDetailBean.getStatData().getStat_info();
                    if (!"search".equals(this.externalsources)) {
                        stat_info2 = stat_info2 + "本人作答{0}次，对{0}次，正确率{0%}";
                    }
                    this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(this.mContext) == 0 ? ColorPhrase.from(stat_info2).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(stat_info2).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-10194273).format());
                }
                if (this.questiondetails_layout_diff.getChildCount() > 1) {
                    LinearLayout linearLayout = this.questiondetails_layout_diff;
                    linearLayout.removeViews(1, linearLayout.getChildCount() - 1);
                }
                int i3 = right_count > 95.0d ? 1 : right_count > 80.0d ? 2 : right_count > 60.0d ? 3 : right_count > 30.0d ? 4 : 5;
                while (i2 < 5) {
                    ImageView imageView = new ImageView(this.mContext);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams.rightMargin = ScreenUtil.getPxByDp(this.mContext, 5);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setImageResource(i2 < i3 ? SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.drawable.icon_star_yellow_nights : R.drawable.icon_star_yellow : SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.drawable.icon_star_gary_night : R.drawable.icon_star_gary);
                    this.questiondetails_layout_diff.addView(imageView);
                    i2++;
                }
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
            } else {
                if (this.questionDetailBean.getStatData() != null) {
                    this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d评论", Integer.valueOf(this.questionDetailBean.getStatData().getComment_count())));
                } else {
                    this.questiondetails_btn_commentNum.setText("0");
                }
                this.questiondetails_tv_statistics.setText("search".equals(this.externalsources) ? "本题0人收藏，全部考生作答0次，对0次，正确率0%" : "本题0人收藏，全部考生作答0次，对0次，正确率0%，本人作答0次，对0次，正确率0%");
            }
            if (ConfigUtils.isHidden(1)) {
                this.questiondetails_layout_diff.setVisibility(8);
            }
            if (ConfigUtils.isHidden(2)) {
                this.headerContentView.findViewById(R.id.ll_statistics).setVisibility(8);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void initSubViewData() {
        if (!TextUtils.isEmpty(this.questionDetailBean.getUser_answer())) {
            setViewVisiable(0);
            this.questiondetails_btn_pushAnswer.setVisibility(8);
            initSelectedAnsed();
        }
        String str = this.answerMode;
        str.hashCode();
        switch (str) {
            case "recite":
                this.hasAnswer = true;
                this.smartRefreshLayout.setEnabled(true);
                setViewVisiable(0);
                this.questiondetails_btn_pushAnswer.setVisibility(8);
                break;
            case "test":
            case "quick_brush":
                setViewVisiable(8);
                if (this.questionDetailBean.getAnswer().replace(",", "").length() <= 1) {
                    this.questiondetails_btn_pushAnswer.setVisibility(8);
                    break;
                } else {
                    this.questiondetails_btn_pushAnswer.setVisibility(0);
                    break;
                }
            default:
                setViewVisiable(8);
                this.questiondetails_btn_pushAnswer.setVisibility(0);
                break;
        }
    }

    public void initTitleImg() {
        if (TextUtils.isEmpty(this.questionDetailBean.getTitle_img())) {
            this.imgtitle.setVisibility(8);
            this.webview.setVisibility(8);
            return;
        }
        this.imgtitle.setVisibility(0);
        this.webview.setVisibility(0);
        this.webview.loadDataWithBaseURL(null, "<html><head><title></title></head><body><img style='height:auto;max-width:100%' src=" + this.questionDetailBean.getTitle_img() + " /></body></html>", "text/html", "utf-8", null);
        this.imgtitle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.j3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13260c.lambda$initTitleImg$17(view);
            }
        });
    }

    public void initVideoData() {
        if (this.questionDetailBean.getVideo_list() == null || this.questionDetailBean.getVideo_list().size() < 1) {
            this.rl_question_video.setVisibility(8);
            return;
        }
        this.rl_question_video.setVisibility(0);
        try {
            int size = this.questionDetailBean.getVideo_list().size();
            this.circlePoint.removeAllViews();
            if (size > 1) {
                this.circlePoint.setCount(size);
                this.circlePoint.initViewData();
                this.circlePoint.invalidate();
                ((ViewPager2) this.headerContentView.findViewById(R.id.vp_video)).registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.psychiatrygarden.activity.online.fragment.SubEnglishWordFragment.5
                    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        SubEnglishWordFragment.this.circlePoint.setonPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                });
            }
            ((ViewPager2) this.headerContentView.findViewById(R.id.vp_video)).setAdapter(new QuestionChoiceVideoAdapter(R.layout.adapter_question_video_item, this.questionDetailBean.getVideo_list(), String.valueOf(this.question_id)));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        String string;
        if (getArguments() != null) {
            this.answerMode = getArguments().getString("answerMode", Constants.ANSWER_MODE.PRACTICE_MODE);
            this.module_type = getArguments().getString("module_type");
            this.externalsources = getArguments().getString("externalsources");
            this.category = getArguments().getString(UriUtil.QUERY_CATEGORY, "");
            this.mCurrentPosition = getArguments().getInt("position");
            string = getArguments().getString("chapter_title");
            if (getActivity() instanceof AnswerQuestionActivity) {
                this.questionDetailBean = ((AnswerQuestionActivity) getActivity()).getQuestionDetailBean(this.mCurrentPosition);
            }
            if (this.questionDetailBean == null) {
                return;
            }
        } else {
            string = null;
        }
        this.show_restore_img = SharePreferencesUtils.readStrConfig(CommonParameter.show_restore_img, this.mContext, "1");
        this.question_id = Long.parseLong(this.questionDetailBean.getId());
        View viewInflate = View.inflate(this.mContext, R.layout.layout_sub_english_word, null);
        this.headerContentView = viewInflate;
        this.llViewComment = (LinearLayout) viewInflate.findViewById(R.id.ll_view_comment);
        this.llNoComment = (LinearLayout) this.headerContentView.findViewById(R.id.ll_no_comment);
        this.mCutQuestionFlag = (ImageView) this.headerContentView.findViewById(R.id.iv_cut_flag);
        this.mCutQuestionFlag.setVisibility((!TextUtils.equals("1", this.questionDetailBean.getIs_cut()) || getArguments().getBoolean("fromQuestionCombine", false)) ? 8 : 0);
        this.ll_restore = (LinearLayout) this.headerContentView.findViewById(R.id.ll_restore);
        this.tvEditTime = (TextView) this.headerContentView.findViewById(R.id.tv_edit_time);
        this.llAd = (QuestionAdWidegt) this.headerContentView.findViewById(R.id.ll_ad);
        this.mQuestionOptionBean = this.questionDetailBean.getOption();
        for (int i2 = 0; i2 < this.mQuestionOptionBean.size(); i2++) {
            if (TextUtils.isEmpty(this.mQuestionOptionBean.get(i2).getType())) {
                this.mQuestionOptionBean.get(i2).setType("0");
            }
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.drawable = ContextCompat.getDrawable(this.mContext, R.mipmap.english_huanyuan);
        } else {
            this.drawable = ContextCompat.getDrawable(this.mContext, R.mipmap.english_huanyuan_night);
            this.mCutQuestionFlag.setImageResource(R.drawable.ic_cut_night);
        }
        TextView textView = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_title);
        if (TextUtils.isEmpty(string)) {
            this.headerContentView.findViewById(R.id.rl_top_title).setVisibility(8);
        } else {
            textView.setText(string);
        }
        this.lineviewtype = (RelativeLayout) this.headerContentView.findViewById(R.id.lineviewtype);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) holder.get(R.id.srl_refresh);
        this.smartRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.psychiatrygarden.activity.online.fragment.o3
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                this.f13301c.lambda$initViews$0();
            }
        });
        this.mPinnedSecListView = (CommentSectionListView) holder.get(R.id.pinnedSectionListView);
        this.btn_comment = (TextView) holder.get(R.id.btn_comment);
        this.questiondetails_layout_answer = (LinearLayout) this.headerContentView.findViewById(R.id.questiondetails_layout_answer);
        this.questiondetails_btn_edit = (ImageView) holder.get(R.id.questiondetails_btn_edit);
        this.questiondetails_btn_centerMsg = (ImageView) holder.get(R.id.questiondetails_btn_centerMsg);
        this.questiondetails_btn_zantong = (ImageView) holder.get(R.id.questiondetails_btn_zantong);
        this.questiondetails_btn_collect = (ImageView) holder.get(R.id.questiondetails_btn_collect);
        this.ly_questiondetails_btn_zantong = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_zantong);
        this.ly_questiondetails_btn_centerMsg = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_centerMsg);
        this.ly_questiondetails_btn_collect = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_collect);
        this.ly_questiondetails_btn_edit = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_edit);
        this.imgtitle = (ImageView) this.headerContentView.findViewById(R.id.imgtitle);
        this.rl_question_video = (RelativeLayout) this.headerContentView.findViewById(R.id.rl_question_video);
        this.circlePoint = (CirclePoint) this.headerContentView.findViewById(R.id.circlepoint);
        this.qbrel = (RelativeLayout) holder.get(R.id.qbrel);
        this.typeStr = (TextView) this.headerContentView.findViewById(R.id.typeStr);
        this.pagenumtv = (TextView) this.headerContentView.findViewById(R.id.pagenumtv);
        this.titletv = (TextView) this.headerContentView.findViewById(R.id.titletv);
        this.tv_question_new = (TextView) this.headerContentView.findViewById(R.id.tv_question_new);
        this.qlistview = (RecyclerView) this.headerContentView.findViewById(R.id.qlistview);
        this.questiondetails_btn_commentNum = (TextView) holder.get(R.id.questiondetails_btn_commentNum);
        this.headerContentView.findViewById(R.id.tv_view_comment).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.p3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13310c.lambda$initViews$1(view);
            }
        });
        this.questiondetails_tv_outline = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_outline);
        this.questiondetails_tv_Answer = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_Answer);
        this.questiondetails_layout_diff = (LinearLayout) this.headerContentView.findViewById(R.id.questiondetails_layout_diff);
        this.questiondetails_btn_pushAnswer = (TextView) holder.get(R.id.questiondetails_btn_pushAnswer);
        this.textView_difficulty = (TextView) this.headerContentView.findViewById(R.id.textView_difficulty);
        this.questiondetails_tv_content_ques = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_content_ques);
        this.tv_word = (EnglishTextView) this.headerContentView.findViewById(R.id.tv_word);
        this.tv_phonetic = (TextView) this.headerContentView.findViewById(R.id.tv_phonetic);
        this.iv_play = (ImageView) this.headerContentView.findViewById(R.id.iv_play);
        this.rv_phonetic = (RecyclerView) this.headerContentView.findViewById(R.id.rv_phonetic);
        this.line_split = this.headerContentView.findViewById(R.id.line_split);
        this.tv_split = (TextView) this.headerContentView.findViewById(R.id.tv_split);
        this.rv_split = (RecyclerView) this.headerContentView.findViewById(R.id.rv_split);
        this.line_example_sentence = this.headerContentView.findViewById(R.id.line_example_sentence);
        this.tv_example_sentence = (TextView) this.headerContentView.findViewById(R.id.tv_example_sentence);
        this.rv_example_sentence = (RecyclerView) this.headerContentView.findViewById(R.id.rv_example_sentence);
        this.line_restore = this.headerContentView.findViewById(R.id.line_restore);
        this.tv_restore = (TextView) this.headerContentView.findViewById(R.id.tv_restore);
        this.webview = (WebView) this.headerContentView.findViewById(R.id.webview);
        this.questiondetails_tv_statistics = (TextView) this.headerContentView.findViewById(R.id.questiondetails_tv_statistics);
        this.tv_statistics = (TextView) this.headerContentView.findViewById(R.id.tv_statistics);
        this.ll_more_columns = (LinearLayout) this.headerContentView.findViewById(R.id.ll_more_columns);
        this.mRadioGroup_content = (LinearLayout) this.headerContentView.findViewById(R.id.mRadioGroup_content);
        this.sourcetv = (TextView) this.headerContentView.findViewById(R.id.sourcetv);
        this.biaotxt = (TextView) this.headerContentView.findViewById(R.id.biaotxt);
        this.lineout = (LinearLayout) this.headerContentView.findViewById(R.id.lineout);
        this.qlistview.setHasFixedSize(true);
        this.qlistview.setNestedScrollingEnabled(false);
        this.qlistview.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mLyVideoView = (RelativeLayout) this.headerContentView.findViewById(R.id.ly_video_view);
        this.mImgVideo = (RoundedImageView) this.headerContentView.findViewById(R.id.img_video);
        this.mImgAudio = (ImageView) this.headerContentView.findViewById(R.id.img_audio);
        this.mImgOriginal = (RoundedImageView) this.headerContentView.findViewById(R.id.img_original);
        this.mLyAdView = (RelativeLayout) holder.get(R.id.ly_ad_view);
        this.mImgAd = (ImageView) holder.get(R.id.img_ad);
        this.mImgCloseAd = (ImageView) holder.get(R.id.btn_close);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.blackColors = ContextCompat.getColorStateList(this.mContext, R.color.black);
            this.grayColors = ContextCompat.getColorStateList(this.mContext, R.color.back_font_gray);
        } else {
            this.blackColors = ContextCompat.getColorStateList(this.mContext, R.color.question_color_night);
            this.grayColors = ContextCompat.getColorStateList(this.mContext, R.color.question_color_night);
        }
        boolean z2 = !TextUtils.isEmpty(this.questionDetailBean.getUser_answer());
        this.hasAnswer = z2;
        this.smartRefreshLayout.setEnabled(z2);
        initQuestionType();
        initListView(holder);
        if (getActivity() == null || !(getActivity() instanceof AnswerQuestionActivity)) {
            return;
        }
        ((AnswerQuestionActivity) getActivity()).questionExpose("2");
        ((AnswerQuestionActivity) getActivity()).saveQuestionPage(String.valueOf(this.question_id));
    }

    public void noCollectimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no);
        } else {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no_night);
        }
    }

    public void noCommingimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.question_msg);
        } else {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.question_msg_night);
        }
    }

    public void noNoteimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit);
        } else {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit_night);
        }
    }

    public void noParise() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
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
    @SuppressLint({"NonConstantResourceId"})
    public void onClick(View v2) throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
        switch (v2.getId()) {
            case R.id.btn_comment /* 2131362326 */:
                if (isLogin()) {
                    new DialogInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.h3
                        @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                        public final void onclickStringBack(String str, String str2, String str3) {
                            this.f13241a.lambda$onClick$23(str, str2, str3);
                        }
                    }, true).show();
                    break;
                }
                break;
            case R.id.img_audio /* 2131363687 */:
                this.isClickAudio = true;
                CustomAliPlayerView customAliPlayerView = this.mAudioPlayerView;
                if (customAliPlayerView != null) {
                    AliyunVodPlayerView aliyunVodPlayerView = customAliPlayerView.mAliyunVodPlayerView;
                    if (aliyunVodPlayerView == null) {
                        this.mAudioPlayerView = null;
                        getAudioInfo();
                        break;
                    } else if (!aliyunVodPlayerView.isPlaying()) {
                        this.mAudioPlayerView.mAliyunVodPlayerView.rePlay();
                        break;
                    } else {
                        this.mAudioPlayerView.mAliyunVodPlayerView.onStop();
                        this.mAudioPlayerView.onStopped();
                        break;
                    }
                } else {
                    getAudioInfo();
                    break;
                }
            case R.id.iv_play /* 2131364179 */:
                playWord(this.iv_play, this.questionDetailBean.getEnglish_label().getWord().getPronunciation());
                break;
            case R.id.ly_questiondetails_btn_centerMsg /* 2131365233 */:
                SharePreferencesUtils.writeBooleanConfig(CommonParameter.Comment_library_Red_Dot, false, this.mContext);
                EventBus.getDefault().post(CommonParameter.Comment_library_Red_Dot);
                Intent intent = new Intent(this.mContext, (Class<?>) CommentNewActivity.class);
                intent.putExtra("obj_id", String.valueOf(this.question_id));
                intent.putExtra("question_id", this.question_id);
                intent.putExtra("module_type", 1);
                intent.putExtra("comment_type", "2");
                intent.putExtra("isNewCom", true);
                intent.putExtra("iscomValu", true);
                intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mycomment");
                startActivity(intent);
                break;
            case R.id.ly_questiondetails_btn_collect /* 2131365234 */:
                if (this.questionDetailBean.getStatData().getAnswer() != null) {
                    AjaxParams ajaxParams = new AjaxParams();
                    if (this.questionDetailBean.getStatData().getIs_collection() == 0) {
                        AlertToast("收藏成功！");
                        this.questionDetailBean.getStatData().setIs_collection(1);
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
                        ajaxParams.put("module_type", this.module_type);
                        QuestionDataRequest.getIntance(this.mContext).questionDoCollectData(ajaxParams, this);
                    } else {
                        AlertToast("取消收藏成功！");
                        this.questionDetailBean.getStatData().setIs_collection(0);
                        if (this.questionDetailBean.getStatData().getCollection_count() > 0) {
                            this.questionDetailBean.getStatData().setCollection_count(this.questionDetailBean.getStatData().getCollection_count() - 1);
                        }
                        ajaxParams.put("question_id", this.questionDetailBean.getId());
                        ajaxParams.put("module_type", this.module_type);
                        QuestionDataRequest.getIntance(this.mContext).cleancollection(ajaxParams, this);
                    }
                    EventBus.getDefault().post(new QuestionCollectEvent(this.questionDetailBean.getStatData().getIs_collection() == 1, this.questionDetailBean.getId()));
                    initStaticData();
                    break;
                } else {
                    AlertToast("原题加载中,请稍后再试！");
                    break;
                }
            case R.id.ly_questiondetails_btn_edit /* 2131365235 */:
                if (this.questionDetailBean.getStatData().getAnswer() != null) {
                    if (this.questionDetailBean.getStatData().getIs_note() != 0) {
                        new XPopup.Builder(this.mContext).moveUpToKeyboard(Boolean.FALSE).enableDrag(true).asCustom(new PopNoteList(this.mContext, this.module_type, this.questionDetailBean.getId())).show();
                        break;
                    } else {
                        new DialogNoteInput(getContext(), this.module_type, String.valueOf(this.question_id), new onDialogNoteListener() { // from class: com.psychiatrygarden.activity.online.fragment.i3
                            @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                            public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                                this.f13251a.lambda$onClick$24(questionNoteBean);
                            }
                        }).show();
                        break;
                    }
                } else {
                    AlertToast("原题加载中,请稍后再试！");
                    break;
                }
            case R.id.ly_questiondetails_btn_zantong /* 2131365237 */:
                Intent intent2 = new Intent(this.mContext, (Class<?>) CommentNewActivity.class);
                intent2.putExtra("obj_id", String.valueOf(this.question_id));
                intent2.putExtra("question_id", this.question_id);
                intent2.putExtra("module_type", 1);
                intent2.putExtra("comment_type", "2");
                intent2.putExtra("isNewComzantong", true);
                intent2.putExtra("iscomValu", true);
                intent2.putExtra(com.alipay.sdk.authjs.a.f3174g, "mypraise");
                startActivity(intent2);
                break;
            case R.id.ly_video_view /* 2131365306 */:
                this.isClickAudio = false;
                CustomAliPlayerView customAliPlayerView2 = this.mAudioPlayerView;
                if (customAliPlayerView2 != null) {
                    customAliPlayerView2.mAliyunVodPlayerView.onStop();
                    this.mAudioPlayerView.onStopped();
                }
                AjaxParams ajaxParams2 = new AjaxParams();
                ajaxParams2.put("question_id", this.questionDetailBean.getId());
                ajaxParams2.put("resource_type", "1");
                ajaxParams2.put("video_id", this.questionDetailBean.getStem_video_list().get(0).getVideo_id());
                QuestionDataRequest.getIntance(this.mContext).getMeidaSourceById(ajaxParams2, "1", this);
                break;
            case R.id.questiondetails_btn_commentNum /* 2131366219 */:
                if (!CommonUtil.isFastClick()) {
                    this.remen.performClick();
                    if (getActivity() instanceof AnswerQuestionActivity) {
                        ((AnswerQuestionActivity) getActivity()).hideTitleView(String.valueOf(this.question_id));
                        break;
                    }
                }
                break;
            case R.id.questiondetails_btn_pushAnswer /* 2131366222 */:
                pushAnswer();
                break;
        }
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mp) throws IllegalStateException {
        AnimationDrawable animationDrawable = this.animationDrawable;
        if (animationDrawable == null || !animationDrawable.isRunning()) {
            return;
        }
        this.animationDrawable.stop();
        this.animationDrawable.setVisible(true, true);
        releasePlayer();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() throws IllegalStateException {
        releasePlayer();
        QuestionAdWidegt questionAdWidegt = this.llAd;
        if (questionAdWidegt != null && !this.isSdkAd) {
            questionAdWidegt.onDestory();
        }
        SharePreferencesUtils.removeConfig(String.valueOf(this.question_id), this.mContext);
        super.onDestroy();
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
        if (getActivity() instanceof AnswerQuestionActivity) {
            ((AnswerQuestionActivity) getActivity()).removeQuestionPage(String.valueOf(this.question_id));
        }
    }

    @Subscribe
    public void onEventMainThread(RefreshQuestionCommentEvent event) {
        if (TextUtils.equals(event.getQuestionId(), this.question_id + "")) {
            this.pageNum = 1;
            this.isRefreshEvent = true;
            getCommentListData();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        if (requstUrl.equals(NetworkRequestsURL.questionlabelApi)) {
            try {
                this.biaotxt.setText("标签：？");
                this.mRadioGroup_content.removeAllViews();
                if (ConfigUtils.isHidden(3)) {
                    this.headerContentView.findViewById(R.id.linbiaoqian).setVisibility(8);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
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
        int comment_count = event.getComment_count();
        if (comment_count > 10000) {
            this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d.%d万", Integer.valueOf(comment_count / 10000), Integer.valueOf((comment_count % 10000) / 1000)));
        } else {
            this.questiondetails_btn_commentNum.setText(comment_count + "");
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
    public void onPause() throws IllegalStateException {
        super.onPause();
        this.currentPageIsVisible = false;
        if (this.isInit) {
            AnimationDrawable animationDrawable = this.animationDrawable;
            if (animationDrawable != null) {
                animationDrawable.stop();
                this.animationDrawable.setVisible(true, true);
                releasePlayer();
            }
            CustomAliPlayerView customAliPlayerView = this.mAudioPlayerView;
            if (customAliPlayerView != null) {
                AliyunVodPlayerView aliyunVodPlayerView = customAliPlayerView.mAliyunVodPlayerView;
                if (aliyunVodPlayerView != null) {
                    aliyunVodPlayerView.onStop();
                }
                this.mAudioPlayerView.onStopped();
            }
        }
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mp) throws IllegalStateException {
        AnimationDrawable animationDrawable = this.animationDrawable;
        if (animationDrawable != null) {
            animationDrawable.start();
        }
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        QuestionAdWidegt questionAdWidegt = this.llAd;
        if (questionAdWidegt == null || !this.isSdkAd) {
            return;
        }
        questionAdWidegt.onResume();
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

    public void releasePlayer() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        this.currentPageIsVisible = isVisibleToUser;
        if (isVisibleToUser) {
            long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
            this.startTime = jCurrentTimeMillis;
            SharePreferencesUtils.writeLongConfig("question_startTime", Long.valueOf(jCurrentTimeMillis), this.mContext);
            LogUtils.e("question_load_time", "startTime===>" + this.startTime);
            QuestionDetailBean questionDetailBean = this.questionDetailBean;
            if (questionDetailBean != null) {
                playWord(this.iv_play, questionDetailBean.getEnglish_label().getWord().getPronunciation());
            } else {
                new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.r3
                    @Override // java.lang.Runnable
                    public final void run() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                        this.f13327c.lambda$setUserVisibleHint$25();
                    }
                }, 1500L);
            }
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.s3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13337c.playAudio();
                }
            }, 600L);
            return;
        }
        AnimationDrawable animationDrawable = this.animationDrawable;
        if (animationDrawable != null) {
            animationDrawable.stop();
            this.animationDrawable.setVisible(true, true);
            releasePlayer();
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
        this.questiondetails_layout_answer.setVisibility(view);
        if (view == 0) {
            this.headerContentView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.q3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13319c.lambda$setViewVisiable$19();
                }
            }, 500L);
        }
    }

    public void showlog(List<BiaoqianBeab.DataBean> dataBiao) {
        new XPopup.Builder(this.mContext).asCustom(new BiaoPupNewWindow(this.mContext, dataBiao, new BiaoqianCallbackInterface() { // from class: com.psychiatrygarden.activity.online.fragment.v2
            @Override // com.psychiatrygarden.interfaceclass.BiaoqianCallbackInterface
            public final void mBiaoianLinster(List list, int i2, boolean z2) {
                this.f13370a.lambda$showlog$14(list, i2, z2);
            }
        })).toggle();
    }

    public void titlenum() {
        String str;
        String sort = this.questionDetailBean.getSort();
        String title = this.questionDetailBean.getTitle();
        if (this.questionDetailBean.getStem_audio_list() == null || this.questionDetailBean.getStem_audio_list().size() <= 0) {
            str = sort + ". " + title;
        } else {
            str = "   " + sort + ". " + title;
        }
        this.lineviewtype.setVisibility(8);
        this.titletv.setText(str);
        updateCutIconLayout();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
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
            if (questionStatDataBean.getCode().equals("200") && questionStatDataBean.getData().getAnswer() != null) {
                this.questionDetailBean.setStatData(questionStatDataBean.getData());
                initStaticData();
            }
        } else if (requstUrl.equals(NetworkRequestsURL.getcollectionApi)) {
            havaCollectimg();
        } else if (requstUrl.equals(NetworkRequestsURL.cleancollectionApi)) {
            noCollectimg();
        } else if (requstUrl.equals(NetworkRequestsURL.questionlabelApi)) {
            try {
                BiaoqianBeab biaoqianBeab = (BiaoqianBeab) new Gson().fromJson(s2, BiaoqianBeab.class);
                if (biaoqianBeab.getCode().equals("200")) {
                    final List<BiaoqianBeab.DataBean> data = biaoqianBeab.getData();
                    this.questionDetailBean.setDataBiao(data);
                    initBiaoQianData(data);
                    this.ll_more_columns.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.c3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f13199c.lambda$onSuccess$29(data, view);
                        }
                    });
                } else {
                    this.biaotxt.setText("标签：？");
                    this.mRadioGroup_content.removeAllViews();
                    if (ConfigUtils.isHidden(3)) {
                        this.headerContentView.findViewById(R.id.linbiaoqian).setVisibility(8);
                    }
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
                    EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_ANSWER_REFRESH, this.questionDetailBean, strSubstring));
                }
                this.hasAnswer = true;
                this.smartRefreshLayout.setEnabled(true);
                if (getActivity() != null && (getActivity() instanceof AnswerQuestionActivity)) {
                    ((AnswerQuestionActivity) getActivity()).questionExpose("1");
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
                        initAudioInfo(strOptString, strOptString2);
                    }
                }
            } catch (JSONException e6) {
                e6.printStackTrace();
            }
        }
        if (this.mCommListAdapter != null) {
            this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.d3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13207c.lambda$onSuccess$30();
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
    public void onEventMainThread(QuestionBack2TopEvent event) {
        if (TextUtils.equals(event.getQuestionId(), String.valueOf(this.question_id))) {
            scrollLayout();
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

    public void onEventMainThread(RefreshViewStateEvent e2) {
        if (TextUtils.equals(e2.getQuestionId(), String.valueOf(this.question_id))) {
            scrollLayout();
        }
    }

    public void onEventMainThread(CommentTagEvent event) {
        if (TextUtils.equals(String.valueOf(this.question_id), event.getQuestionId())) {
            SharePreferencesUtils.writeBooleanConfig(String.valueOf(this.question_id), event.isShow(), this.mContext);
            this.isPinned = event.isShow();
            int visibility = this.lineselect2.getVisibility();
            if (!this.ishavehot) {
                if (this.lineselect2.getVisibility() != 8) {
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
