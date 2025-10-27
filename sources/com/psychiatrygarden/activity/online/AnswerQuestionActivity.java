package com.psychiatrygarden.activity.online;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.aliyun.svideo.common.utils.FastClickUtil;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.aliyun.vod.common.utils.UriUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CommentSeachActivity;
import com.psychiatrygarden.activity.HomePageNewActivity;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.online.fragment.ShareStemQuestionFragment;
import com.psychiatrygarden.activity.online.fragment.SubChoiceQuestionFragment;
import com.psychiatrygarden.activity.online.fragment.SubCrazyQuestionFragemnt;
import com.psychiatrygarden.activity.online.fragment.SubEnglishWordFragment;
import com.psychiatrygarden.activity.online.fragment.SubSubjectiveQuestionFragment;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.SubmitAnswerBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.CheckLastQuestionEvent;
import com.psychiatrygarden.event.CloseAnswerSheetEvent;
import com.psychiatrygarden.event.GetNextChapterDataEvent;
import com.psychiatrygarden.event.HideChapterTitleEvent;
import com.psychiatrygarden.event.NextNodeEvent;
import com.psychiatrygarden.event.QuestionBack2TopEvent;
import com.psychiatrygarden.event.RedoOtherQuestionEvent;
import com.psychiatrygarden.event.RefreshIdNotifyEvent;
import com.psychiatrygarden.event.RefreshViewStateEvent;
import com.psychiatrygarden.event.SwitchStemQuestionEvent;
import com.psychiatrygarden.event.UpdateQuestionCutEvent;
import com.psychiatrygarden.event.UpdateQuestionIdEvent;
import com.psychiatrygarden.event.UpdateTopMargin;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.utils.weblong.StringUtils;
import com.psychiatrygarden.widget.AnswerQuestionAdapter;
import com.psychiatrygarden.widget.CustomViewPager2;
import com.psychiatrygarden.widget.CutQuestionSuccessAnimPop;
import com.psychiatrygarden.widget.CutQuestionSuccessPop;
import com.psychiatrygarden.widget.HintNewDialog;
import com.psychiatrygarden.widget.NextChapterPopWindow;
import com.psychiatrygarden.widget.NextNodePopWindow;
import com.psychiatrygarden.widget.UnlockNextChapterPop;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.Pair;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class AnswerQuestionActivity extends BaseActivity implements View.OnClickListener, QuestionDataCallBack<String>, LifecycleObserver {
    private View answerQuestionMask;
    private View cutGuideView;
    private boolean fromQuestionCombine;
    private boolean fromSearch;
    private boolean isLastQuestion;
    private ImageView ivCutQuestion;
    private ImageView ivRight;
    private boolean knowledge_point;
    private ImageView mBtnLeft;
    private String mChapterTitle;
    private boolean mIsSearch;
    private String mIsShowNumber;
    private boolean mIsTitleShow;
    private ImageView mIvBack2Top;
    private long mLastUpdate;
    private MediaPlayer mMediaPlayer;
    private String mModuleType;
    private String mQuestionType;
    private String mSubjectTitle;
    private CustomViewPager2 mViewPager;
    private boolean nextNode;
    private String node_title;
    private long periodTime;
    private String questionId;
    private String question_bank_id;
    private RelativeLayout rlTopChapterTitle;
    private ArrayMap<String, List<QuestionDetailBean>> shareStemMap;
    private TextView tvChapterTitle;
    private TextView tvTitle;
    private String videoId;
    private boolean video_summary;
    private List<QuestionDetailBean> mQuestionList = new ArrayList();
    private int mPosition = 0;
    private String mAnswerMode = "";
    private String mCategory = "";
    private String node_id = "";
    private String knowledge_id = "";
    private String mIdentityId = "";
    private String mChapterId = "";
    private String mChapterParentId = "";
    private boolean mIsYearStatistics = false;
    private boolean study_plan = false;
    private String mExternalsources = "";
    private int mCount = 0;
    private long mPreClickTime = 0;
    private final int mTotalTime = 500;
    private int mTopTitleHeight = 0;
    private final List<Fragment> mFragmentList = new ArrayList();
    private final List<QuestionDetailBean> handledQuestionList = new ArrayList();
    private final Map<String, Integer> stemIndexArr = new HashMap();
    private SparseArray<Boolean> titleShowArr = new SparseArray<>();
    private boolean fromEBook = false;
    private String currentStemQuestionId = null;
    private final List<String> questionPageList = new ArrayList();
    private boolean isToNextChapter = false;

    /* renamed from: com.psychiatrygarden.activity.online.AnswerQuestionActivity$7, reason: invalid class name */
    public class AnonymousClass7 implements HintNewDialog.OperationListener {
        public AnonymousClass7() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$confirm$0() {
            AnswerQuestionActivity.this.finish();
        }

        @Override // com.psychiatrygarden.widget.HintNewDialog.OperationListener
        public void cancel() {
        }

        @Override // com.psychiatrygarden.widget.HintNewDialog.OperationListener
        public void confirm() {
            if (!"year".equals(AnswerQuestionActivity.this.mCategory) || !AnswerQuestionActivity.this.mQuestionType.equals("all")) {
                QuestionDetailBean currentQuestion = AnswerQuestionActivity.this.getCurrentQuestion();
                if (currentQuestion != null) {
                    SharePreferencesUtils.writeBooleanConfig(CommonParameter.DETAIL_SUBMIT_STATISTICS, true, AnswerQuestionActivity.this);
                    EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_ANSWER_TEST_STATISTICS, currentQuestion.getKnowledge_id()));
                    ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.online.t
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f13482c.lambda$confirm$0();
                        }
                    }, 200L);
                    return;
                }
                return;
            }
            ArrayList arrayList = new ArrayList();
            try {
                StringBuilder sb = new StringBuilder();
                for (QuestionDetailBean questionDetailBean : AnswerQuestionActivity.this.mQuestionList) {
                    if (TextUtils.isEmpty(questionDetailBean.getUser_answer())) {
                        if (sb.length() > 0) {
                            sb.setLength(0);
                        }
                        List<QuestionDetailBean.OptionDTO> option = questionDetailBean.getOption();
                        if (option != null && !option.isEmpty()) {
                            for (QuestionDetailBean.OptionDTO optionDTO : option) {
                                if (TextUtils.equals(optionDTO.getType(), "1")) {
                                    sb.append(optionDTO.getKey());
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(sb.toString())) {
                            String str = TextUtils.equals(questionDetailBean.getAnswer().replace(",", "").trim(), sb.toString()) ? "1" : "0";
                            if (AnswerQuestionActivity.this.mAnswerMode.equals(Constants.ANSWER_MODE.TEST_MODE) && "year".equals(AnswerQuestionActivity.this.mCategory)) {
                                questionDetailBean.setUser_answer(sb.toString());
                            }
                            SubmitAnswerBean submitAnswerBean = new SubmitAnswerBean(sb.toString(), questionDetailBean.getId(), str, questionDetailBean.getApp_id(), AnswerQuestionActivity.this.mIdentityId, AnswerQuestionActivity.this.mChapterId);
                            if (AnswerQuestionActivity.this.mAnswerMode.equals(Constants.ANSWER_MODE.TEST_MODE)) {
                                if (AnswerQuestionActivity.this.fromQuestionCombine) {
                                    submitAnswerBean.setPaper_id(Integer.parseInt(AnswerQuestionActivity.this.getIntent().getBundleExtra("bundle").getString("paperId", "0")));
                                } else {
                                    submitAnswerBean.setPaper_id(0);
                                }
                            }
                            submitAnswerBean.setDuration(questionDetailBean.getDoDuration());
                            submitAnswerBean.setSubject_id(SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, AnswerQuestionActivity.this.mContext));
                            submitAnswerBean.setChapter_parent_id("");
                            arrayList.add(submitAnswerBean);
                        }
                    }
                }
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put("answer", new Gson().toJson(arrayList));
                ajaxParams.put("module_type", "" + AnswerQuestionActivity.this.mModuleType);
                ajaxParams.put("question_category_id", AnswerQuestionActivity.this.question_bank_id);
                QuestionDataRequest.getIntance(AnswerQuestionActivity.this).questionPutAnswerData(ajaxParams, AnswerQuestionActivity.this);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void cutQuestion() {
        if (this.fromQuestionCombine || FastClickUtil.isFastClick()) {
            return;
        }
        final String operate = getOperate();
        if (operate == null) {
            AlertToast("参数错误，请检查");
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", this.mIdentityId);
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("chapter_id", this.mChapterId);
        ajaxParams.put("question_id", getCurrentQuestionId());
        Bundle bundleExtra = getIntent().getBundleExtra("bundle");
        if (bundleExtra != null) {
            ajaxParams.put("unit_id", bundleExtra.getString("unit_id", ""));
        }
        ajaxParams.put("module_type", this.mModuleType);
        QuestionDetailBean currentQuestion = getCurrentQuestion();
        if (currentQuestion != null && !TextUtils.isEmpty(currentQuestion.getKnowledge_id())) {
            ajaxParams.put("identity_id", currentQuestion.getIdentity_id());
            ajaxParams.put("user_id", UserConfig.getUserId());
            ajaxParams.put("chapter_id", currentQuestion.getKnowledge_id());
            ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this));
            ajaxParams.put("module_type", "110");
        }
        ajaxParams.put("operate", operate);
        YJYHttpUtils.post(this, NetworkRequestsURL.cutQuestion, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.AnswerQuestionActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (TextUtils.isEmpty(strMsg)) {
                    return;
                }
                AnswerQuestionActivity.this.AlertToast(strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (TextUtils.equals("200", jSONObject.optString("code", ""))) {
                        AnswerQuestionActivity.this.handleCutQuestion(TextUtils.equals("add", operate), AnswerQuestionActivity.this.updateCutSet(TextUtils.equals("add", operate)));
                        AnswerQuestionActivity.this.updateCutIcon();
                    } else {
                        AnswerQuestionActivity.this.AlertToast(jSONObject.optString("message", ""));
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        });
        String str = "[\"" + getCurrentQuestionId() + "\"]";
        String str2 = "[\"" + this.handledQuestionList.get(this.mViewPager.getCurrentItem()).getTitle() + "\"]";
        QuestionDetailBean questionDetailBean = this.handledQuestionList.get(this.mViewPager.getCurrentItem());
        questionDetailBean.setModule_type(this.mModuleType);
        questionDetailBean.setIs_redo(TextUtils.isEmpty(questionDetailBean.getIs_redo()) ? "0" : questionDetailBean.getIs_redo());
        questionDetailBean.setUnit_title(ProjectApp.unit_title);
        questionDetailBean.setExam_title(ProjectApp.exam_title);
        questionDetailBean.setIdentity_title(ProjectApp.identity_title);
        questionDetailBean.setChapter_title(this.mChapterTitle);
        questionDetailBean.setChapter_parent_title(this.mSubjectTitle);
        String json = ProjectApp.gson.toJson(questionDetailBean);
        if (getOperate().equals("add")) {
            AliyunEvent aliyunEvent = AliyunEvent.AddCutQuestion;
            CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, str2, json, "2");
            return;
        }
        AliyunEvent aliyunEvent2 = AliyunEvent.DelCutQuestion;
        CommonUtil.addLog(aliyunEvent2.getKey(), aliyunEvent2.getValue(), System.currentTimeMillis() + "", "", str, str2, json, "2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void execAnimation(boolean show) {
        if (show) {
            if (this.rlTopChapterTitle.getHeight() == this.mTopTitleHeight) {
                return;
            }
        } else if (this.rlTopChapterTitle.getHeight() == 0) {
            return;
        }
        this.mIsTitleShow = show;
        int i2 = show ? 0 : this.mTopTitleHeight;
        int i3 = show ? this.mTopTitleHeight : 0;
        this.titleShowArr.put(this.mViewPager.getCurrentItem(), Boolean.valueOf(show));
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(i2, i3);
        valueAnimatorOfInt.setDuration(200L).setInterpolator(new AccelerateInterpolator());
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.online.f
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f13164c.lambda$execAnimation$16(valueAnimator);
            }
        });
        valueAnimatorOfInt.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public QuestionDetailBean getCurrentQuestion() {
        int currentPosition;
        int currentItem = this.mViewPager.getCurrentItem();
        QuestionDetailBean questionDetailBean = this.handledQuestionList.get(currentItem);
        if (TextUtils.isEmpty(questionDetailBean.getPublic_number())) {
            return questionDetailBean;
        }
        List<QuestionDetailBean> shareStemQuestionList = getShareStemQuestionList(questionDetailBean.getPublic_number());
        if (shareStemQuestionList == null || shareStemQuestionList.isEmpty()) {
            return null;
        }
        Fragment fragment = this.mFragmentList.get(currentItem);
        if (!(fragment instanceof ShareStemQuestionFragment) || (currentPosition = ((ShareStemQuestionFragment) fragment).getCurrentPosition()) >= shareStemQuestionList.size()) {
            return null;
        }
        return shareStemQuestionList.get(currentPosition);
    }

    private String getCurrentQuestionId() {
        int currentPosition;
        int currentItem = this.mViewPager.getCurrentItem();
        QuestionDetailBean questionDetailBean = this.handledQuestionList.get(currentItem);
        if (TextUtils.isEmpty(questionDetailBean.getPublic_number())) {
            return questionDetailBean.getId();
        }
        List<QuestionDetailBean> shareStemQuestionList = getShareStemQuestionList(questionDetailBean.getPublic_number());
        if (shareStemQuestionList != null && !shareStemQuestionList.isEmpty()) {
            Fragment fragment = this.mFragmentList.get(currentItem);
            if ((fragment instanceof ShareStemQuestionFragment) && (currentPosition = ((ShareStemQuestionFragment) fragment).getCurrentPosition()) < shareStemQuestionList.size()) {
                return shareStemQuestionList.get(currentPosition).getId();
            }
        }
        return this.currentStemQuestionId;
    }

    private void getEBookData() {
        this.mModuleType = "1";
        this.mPosition = 0;
        this.mSubjectTitle = this.mQuestionList.get(0).getChapter_parent_title();
        this.mChapterTitle = this.mQuestionList.get(0).getChapter_title();
        this.mQuestionType = "";
        this.mCategory = "";
        this.mIdentityId = "";
        this.mChapterId = "";
        this.mAnswerMode = Constants.ANSWER_MODE.PRACTICE_MODE;
        this.mIsYearStatistics = false;
        this.mIsShowNumber = "";
        this.mExternalsources = "";
        this.fromQuestionCombine = false;
        this.questionId = this.mQuestionList.get(0).getId();
    }

    private String getOperate() {
        if (this.handledQuestionList.isEmpty()) {
            return null;
        }
        int currentItem = this.mViewPager.getCurrentItem();
        QuestionDetailBean questionDetailBean = this.handledQuestionList.get(currentItem);
        if (TextUtils.isEmpty(questionDetailBean.getPublic_number())) {
            return TextUtils.equals("1", questionDetailBean.getIs_cut()) ? "del" : "add";
        }
        List<QuestionDetailBean> shareStemQuestionList = getShareStemQuestionList(questionDetailBean.getPublic_number());
        if (shareStemQuestionList != null) {
            if (shareStemQuestionList.size() == 1) {
                return TextUtils.equals("1", questionDetailBean.getIs_cut()) ? "del" : "add";
            }
            Fragment fragment = this.mFragmentList.get(currentItem);
            if (fragment instanceof ShareStemQuestionFragment) {
                int currentPosition = ((ShareStemQuestionFragment) fragment).getCurrentPosition();
                String is_cut = shareStemQuestionList.get(currentPosition).getIs_cut();
                this.currentStemQuestionId = shareStemQuestionList.get(currentPosition).getId();
                return TextUtils.equals("1", is_cut) ? "del" : "add";
            }
        }
        return null;
    }

    public static void gotoActivity(Context context, Bundle bundle) {
        try {
            Intent intent = new Intent(context, (Class<?>) AnswerQuestionActivity.class);
            intent.putExtra("bundle", bundle);
            context.startActivity(intent);
        } catch (Exception unused) {
            Intent intent2 = new Intent(context, (Class<?>) AnswerQuestionActivity.class);
            intent2.setFlags(268435456);
            intent2.putExtra("bundle", bundle);
            context.startActivity(intent2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCutQuestion(boolean isCut, UpdateQuestionCutEvent cutEvent) {
        if (!isCut) {
            AlertToast("取消斩题");
            EventBus.getDefault().post(cutEvent);
        } else {
            if (SharePreferencesUtils.readBooleanConfig(CommonParameter.CUT_QUESTION_SUCCESS_REMIND_NEVER, false, this)) {
                lambda$showCutQuestionRemind$12(cutEvent);
            } else {
                showCutQuestionRemind(cutEvent);
            }
            new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.online.j
                @Override // java.lang.Runnable
                public final void run() throws IllegalStateException, IOException, IllegalArgumentException {
                    this.f13429c.playCutQuestionVoice();
                }
            }).start();
        }
    }

    private void handleQuestionData() {
        this.shareStemMap.clear();
        this.handledQuestionList.clear();
        this.stemIndexArr.clear();
        this.mFragmentList.clear();
        for (int i2 = 0; i2 < this.mQuestionList.size(); i2++) {
            String public_number = this.mQuestionList.get(i2).getPublic_number();
            this.mQuestionList.get(i2).setActualStemIndex(i2);
            if (TextUtils.isEmpty(public_number)) {
                this.handledQuestionList.add(this.mQuestionList.get(i2));
            } else {
                if (!this.shareStemMap.containsKey(public_number)) {
                    this.handledQuestionList.add(this.mQuestionList.get(i2));
                    this.shareStemMap.put(public_number, new ArrayList());
                }
                List<QuestionDetailBean> list = this.shareStemMap.get(public_number);
                if (list != null) {
                    list.add(this.mQuestionList.get(i2));
                }
            }
        }
        for (int i3 = 0; i3 < this.handledQuestionList.size(); i3++) {
            String public_number2 = this.handledQuestionList.get(i3).getPublic_number();
            if (!TextUtils.isEmpty(public_number2) && !this.stemIndexArr.containsKey(public_number2)) {
                this.stemIndexArr.put(public_number2, Integer.valueOf(i3));
            }
        }
    }

    private void initViewPage() {
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add("1");
            arrayList.add("2");
            arrayList.add(com.tencent.connect.common.Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
            arrayList.add("18");
            arrayList.add(com.tencent.connect.common.Constants.VIA_ACT_TYPE_NINETEEN);
            arrayList.add(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_DATALINE);
            int i2 = 0;
            while (true) {
                boolean z2 = true;
                if (i2 >= this.handledQuestionList.size()) {
                    break;
                }
                QuestionDetailBean questionDetailBean = this.handledQuestionList.get(i2);
                Bundle bundle = new Bundle();
                if (!TextUtils.isEmpty(this.handledQuestionList.get(i2).getPublic_number())) {
                    bundle.putString("public_number", this.handledQuestionList.get(i2).getPublic_number());
                    Iterator<QuestionDetailBean> it = getShareStemQuestionList(this.handledQuestionList.get(i2).getPublic_number()).iterator();
                    while (it.hasNext()) {
                        int actualStemIndex = it.next().getActualStemIndex();
                        int i3 = this.mPosition;
                        if (actualStemIndex == i3) {
                            bundle.putInt("question_position", i3);
                        }
                    }
                }
                bundle.putBoolean("video_summary", this.video_summary);
                bundle.putBoolean("study_plan", this.study_plan);
                bundle.putString("subject_title", this.mSubjectTitle);
                if (this.video_summary && !TextUtils.isEmpty(this.node_title)) {
                    bundle.putString("node_title", this.node_title);
                }
                if (this.handledQuestionList.size() <= 1) {
                    z2 = false;
                }
                bundle.putBoolean("show_type_title", z2);
                bundle.putBoolean("ebook", this.fromEBook);
                bundle.putString("question_bank_id", this.question_bank_id);
                bundle.putString("module_type", this.mModuleType);
                bundle.putString("questionId", this.questionId);
                bundle.putString("chapter_title", this.mChapterTitle);
                bundle.putString("is_show_number", this.mIsShowNumber);
                Bundle bundleExtra = getIntent().getBundleExtra("bundle");
                if (bundleExtra != null) {
                    bundle.putString("total", bundleExtra.getString("total", ""));
                    bundle.putString("unit_id", bundleExtra.getString("unit_id", ""));
                    bundle.putString("category_id", bundleExtra.getString("category_id", ""));
                    bundle.putString("paperId", bundleExtra.getString("paperId", ""));
                }
                if (this.fromEBook) {
                    bundle.putString("total", this.mQuestionList.size() + "");
                }
                bundle.putString("answerMode", this.mAnswerMode);
                bundle.putString("type", this.mQuestionType);
                bundle.putString("identity_id", this.mIdentityId);
                bundle.putString("chapter_id", this.mChapterId);
                bundle.putString("chapter_parent_id", this.mChapterParentId);
                bundle.putString(UriUtil.QUERY_CATEGORY, this.mCategory);
                bundle.putString("externalsources", this.mExternalsources);
                bundle.putBoolean("fromQuestionCombine", this.fromQuestionCombine);
                bundle.putInt("position", this.handledQuestionList.get(i2).getActualStemIndex());
                bundle.putBoolean("ebook", this.fromEBook);
                if (arrayList.contains(questionDetailBean.getType())) {
                    QuestionDetailBean.EnglishLableBean english_label = questionDetailBean.getEnglish_label();
                    if (english_label != null && english_label.getWord() != null && !StringUtils.isEmpty(english_label.getWord().getTitle())) {
                        this.mFragmentList.add(SubEnglishWordFragment.getInstance(bundle));
                    } else if (TextUtils.isEmpty(this.handledQuestionList.get(i2).getPublic_number())) {
                        this.mFragmentList.add(SubChoiceQuestionFragment.getInstance(bundle));
                    } else {
                        this.mFragmentList.add(ShareStemQuestionFragment.getInstance(bundle));
                    }
                } else if (com.tencent.connect.common.Constants.VIA_SHARE_TYPE_MINI_PROGRAM.equals(questionDetailBean.getType())) {
                    this.mFragmentList.add(SubCrazyQuestionFragemnt.getInstance(bundle));
                    this.rlTopChapterTitle.setVisibility(8);
                } else {
                    this.mFragmentList.add(SubSubjectiveQuestionFragment.getInstance(bundle));
                }
                i2++;
            }
            if (this.mFragmentList.size() <= 0 || this.mPosition < 0) {
                return;
            }
            this.mViewPager.setOffscreenPageLimit(1);
            String public_number = this.mQuestionList.get(this.mPosition).getPublic_number();
            if (TextUtils.isEmpty(public_number)) {
                String id = this.mQuestionList.get(this.mPosition).getId();
                int i4 = 0;
                while (true) {
                    if (i4 >= this.handledQuestionList.size()) {
                        break;
                    }
                    if (TextUtils.equals(this.handledQuestionList.get(i4).getId(), id)) {
                        this.mPosition = i4;
                        break;
                    }
                    i4++;
                }
            } else {
                Integer num = this.stemIndexArr.get(public_number);
                if (num != null) {
                    this.mPosition = num.intValue();
                }
            }
            PagerAdapter adapter = this.mViewPager.getAdapter();
            if (adapter == null) {
                this.mViewPager.setSaveEnabled(false);
                this.mViewPager.setAdapter(new AnswerQuestionAdapter(getSupportFragmentManager(), this.mFragmentList));
            } else {
                adapter.notifyDataSetChanged();
            }
            this.mViewPager.setCurrentItem(this.mPosition, false);
            Fragment fragment = this.mFragmentList.get(this.mPosition);
            if (fragment instanceof SubChoiceQuestionFragment) {
                ((SubChoiceQuestionFragment) fragment).onVisible();
            } else if (fragment instanceof SubSubjectiveQuestionFragment) {
                ((SubSubjectiveQuestionFragment) fragment).onVisible();
            }
            QuestionDetailBean questionDetailBean2 = this.handledQuestionList.get(this.mPosition);
            this.currentStemQuestionId = TextUtils.isEmpty(questionDetailBean2.getPublic_number()) ? questionDetailBean2.getId() : this.questionId;
            updateCutIcon();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void jump2Statistis(String requstUrl) {
        if (NetworkRequestsURL.getanswerApi.equals(requstUrl)) {
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.DETAIL_SUBMIT_STATISTICS, true, this);
            EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_ANSWER_TEST_STATISTICS, this.mAnswerMode));
            ProjectApp.showQuestionList = new Gson().toJson(this.mQuestionList);
            Intent intent = new Intent(this, (Class<?>) YearStatisticsActivity.class);
            intent.putExtra(UriUtil.QUERY_CATEGORY, this.mCategory);
            intent.putExtra("module_type", this.mModuleType);
            intent.putExtra("unit", this.mChapterTitle);
            intent.putExtra("year", this.mSubjectTitle);
            intent.putExtra("identity_id", getIntent().getBundleExtra("bundle").getString("identity_id", ""));
            startActivity(intent);
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$execAnimation$16(ValueAnimator valueAnimator) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.rlTopChapterTitle.getLayoutParams();
        layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        this.rlTopChapterTitle.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(Pair pair) {
        if (pair != null) {
            ProjectApp.removeNode();
            EventBus.getDefault().post(new NextNodeEvent((String) pair.getSecond(), (String) pair.getFirst()));
        } else if (this.nextNode && this.study_plan) {
            EventBus.getDefault().post(new NextNodeEvent("", ""));
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3() {
        if (TextUtils.equals(this.mAnswerMode, Constants.ANSWER_MODE.TEST_MODE)) {
            if (checkCurrentQuestionIsLastQuestion()) {
                AlertToast("已是最后一题，请交卷");
                return;
            }
            return;
        }
        if (checkCurrentQuestionIsLastQuestion()) {
            Bundle bundleExtra = getIntent().getBundleExtra("bundle");
            boolean z2 = bundleExtra != null ? bundleExtra.getBoolean("hasNextChapter", false) : false;
            QuestionDetailBean currentQuestion = getCurrentQuestion();
            if (currentQuestion == null || TextUtils.isEmpty(currentQuestion.getKnowledge_id())) {
                if (z2) {
                    showNextChapterPop();
                    return;
                } else {
                    AlertToast("已是最后一题");
                    return;
                }
            }
            final Pair<String, String> nextNode = ProjectApp.getNextNode();
            if (nextNode != null || this.nextNode) {
                new XPopup.Builder(this).asCustom(new NextNodePopWindow(this, this.study_plan, new NextNodePopWindow.ClickIml() { // from class: com.psychiatrygarden.activity.online.p
                    @Override // com.psychiatrygarden.widget.NextNodePopWindow.ClickIml
                    public final void mClickIml() {
                        this.f13464a.lambda$init$2(nextNode);
                    }
                })).show();
            } else {
                AlertToast("已是最后一题");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(View view) {
        int i2 = this.mCount;
        if (i2 == 0) {
            this.mPreClickTime = System.currentTimeMillis();
            this.mCount++;
        } else if (i2 != 1) {
            this.mCount = 0;
            this.mPreClickTime = 0L;
        } else {
            if (System.currentTimeMillis() - this.mPreClickTime < 500) {
                EventBus.getDefault().post(new RefreshViewStateEvent(this.mQuestionList.get(this.mViewPager.getCurrentItem()).getId()));
            }
            this.mCount = 0;
            this.mPreClickTime = 0L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(View view) {
        if (!TextUtils.isEmpty(this.currentStemQuestionId)) {
            EventBus.getDefault().post(new QuestionBack2TopEvent(this.currentStemQuestionId));
            return;
        }
        EventBus.getDefault().post(new QuestionBack2TopEvent(this.handledQuestionList.get(this.mViewPager.getCurrentItem()).getId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$6(View view) {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.CUT_QUESTION_GUIDE, true, this);
        this.cutGuideView.setVisibility(8);
        setAppbarCorlor(Boolean.FALSE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$7() {
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.CUT_QUESTION_GUIDE, false, this)) {
            this.cutGuideView.setVisibility(8);
            setAppbarCorlor(Boolean.FALSE);
        } else {
            this.cutGuideView.setVisibility(0);
            setAppbarCorlor(Boolean.TRUE);
            findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.i
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13425c.lambda$init$6(view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEventMainThread$15() throws Resources.NotFoundException {
        this.mViewPager.setCurrentItem(this.mPosition + 1, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playCutQuestionVoice$13(MediaPlayer mediaPlayer) throws IllegalStateException {
        this.mMediaPlayer.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$playCutQuestionVoice$14(MediaPlayer mediaPlayer, int i2, int i3) {
        this.mMediaPlayer.release();
        this.mMediaPlayer = null;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0(View view) {
        cutQuestion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setContentView$1(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showCutQuestionAnim$8(UpdateQuestionCutEvent updateQuestionCutEvent) {
        EventBus.getDefault().post(updateQuestionCutEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCutQuestionAnim$9(final UpdateQuestionCutEvent updateQuestionCutEvent) {
        this.mViewPager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.r
            @Override // java.lang.Runnable
            public final void run() {
                AnswerQuestionActivity.lambda$showCutQuestionAnim$8(updateQuestionCutEvent);
            }
        }, 100L);
        if (this.isLastQuestion) {
            submitAnswer();
        } else {
            this.mViewPager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.s
                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException {
                    this.f13478c.nextQuestion();
                }
            }, 300L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNextChapterPop$10(Bundle bundle) {
        EventBus.getDefault().post(new CloseAnswerSheetEvent());
        Intent intent = new Intent(this, (Class<?>) HomePageNewActivity.class);
        intent.setFlags(67108864);
        if (bundle != null) {
            String string = bundle.getString("nextUnlockId");
            if (!TextUtils.isEmpty(string)) {
                intent.putExtra("chapter_id", string);
            }
        }
        startActivity(intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNextChapterPop$11() {
        this.isToNextChapter = true;
        final Bundle bundleExtra = getIntent().getBundleExtra("bundle");
        if (bundleExtra != null ? bundleExtra.getBoolean("nextChapterUnlock", false) : false) {
            EventBus.getDefault().post(new GetNextChapterDataEvent());
            finish();
        } else if ("all".equals(this.mQuestionType)) {
            new XPopup.Builder(this).dismissOnTouchOutside(Boolean.FALSE).asCustom(new UnlockNextChapterPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.online.n
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f13449a.lambda$showNextChapterPop$10(bundleExtra);
                }
            })).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nextQuestion() throws Resources.NotFoundException {
        if (this.mViewPager.getCurrentItem() == this.handledQuestionList.size() - 1) {
            return;
        }
        int currentItem = this.mViewPager.getCurrentItem();
        String public_number = this.handledQuestionList.get(currentItem).getPublic_number();
        if (TextUtils.isEmpty(public_number)) {
            this.mViewPager.setCurrentItem(currentItem + 1, true);
            return;
        }
        List<QuestionDetailBean> shareStemQuestionList = getShareStemQuestionList(public_number);
        if (shareStemQuestionList == null || shareStemQuestionList.size() <= 0) {
            return;
        }
        if (TextUtils.equals(shareStemQuestionList.get(shareStemQuestionList.size() - 1).getId(), this.currentStemQuestionId)) {
            this.mViewPager.setCurrentItem(currentItem + 1, true);
        } else {
            EventBus.getDefault().post(new SwitchStemQuestionEvent(true, public_number));
        }
    }

    private void performPointRequest() {
        YJYHttpUtils.get(this, NetworkRequestsURL.COMMENT_TEMP_POINT, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.AnswerQuestionActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playCutQuestionVoice() throws IllegalStateException, IOException, IllegalArgumentException {
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.CUT_QUESTION_VOICE, true, this)) {
            try {
                if (this.mMediaPlayer == null) {
                    this.mMediaPlayer = new MediaPlayer();
                    AssetFileDescriptor assetFileDescriptorOpenFd = getAssets().openFd("cut_question_success.mp3");
                    this.mMediaPlayer.setDataSource(assetFileDescriptorOpenFd.getFileDescriptor(), assetFileDescriptorOpenFd.getStartOffset(), assetFileDescriptorOpenFd.getLength());
                    this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.psychiatrygarden.activity.online.l
                        @Override // android.media.MediaPlayer.OnPreparedListener
                        public final void onPrepared(MediaPlayer mediaPlayer) throws IllegalStateException {
                            this.f13437c.lambda$playCutQuestionVoice$13(mediaPlayer);
                        }
                    });
                    this.mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.psychiatrygarden.activity.online.m
                        @Override // android.media.MediaPlayer.OnErrorListener
                        public final boolean onError(MediaPlayer mediaPlayer, int i2, int i3) {
                            return this.f13441c.lambda$playCutQuestionVoice$14(mediaPlayer, i2, i3);
                        }
                    });
                    this.mMediaPlayer.prepare();
                }
                if (this.mMediaPlayer.isPlaying()) {
                    this.mMediaPlayer.stop();
                } else {
                    this.mMediaPlayer.start();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetData() {
        ProjectApp.comment_content = "";
        ProjectApp.comment_b_img = "";
        ProjectApp.comment_s_img = "";
        ProjectApp.commentvideoPath = "";
        ProjectApp.commentvideoImage = "";
        ProjectApp.commentvideoId = "";
        ProjectApp.commentvideoImagePath = "";
    }

    private void setAppbarCorlor(Boolean showMask) {
        if (showMask.booleanValue()) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
                StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.corlor_appbar_mask_night), 0);
                getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
            } else {
                StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.black_gray), 0);
            }
            this.answerQuestionMask.setVisibility(0);
            return;
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_one_color_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_white_color), 0);
        }
        this.answerQuestionMask.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: showCutQuestionAnim, reason: merged with bridge method [inline-methods] */
    public void lambda$showCutQuestionRemind$12(final UpdateQuestionCutEvent event) {
        new XPopup.Builder(this).hasShadowBg(Boolean.FALSE).asCustom(new CutQuestionSuccessAnimPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.online.q
            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
            public final void onConfirm() {
                this.f13471a.lambda$showCutQuestionAnim$9(event);
            }
        })).show();
    }

    private void showCutQuestionRemind(final UpdateQuestionCutEvent cutEvent) {
        new XPopup.Builder(this).asCustom(new CutQuestionSuccessPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.online.o
            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
            public final void onConfirm() {
                this.f13458a.lambda$showCutQuestionRemind$12(cutEvent);
            }
        })).show();
    }

    private void showNextChapterPop() {
        new XPopup.Builder(this).asCustom(new NextChapterPopWindow(this, new NextChapterPopWindow.ClickIml() { // from class: com.psychiatrygarden.activity.online.a
            @Override // com.psychiatrygarden.widget.NextChapterPopWindow.ClickIml
            public final void mClickIml() {
                this.f13087a.lambda$showNextChapterPop$11();
            }
        })).show();
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x008e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void submitAnswer() {
        /*
            r8 = this;
            boolean r0 = com.psychiatrygarden.utils.CommonUtil.isNetworkConnected(r8)
            if (r0 != 0) goto Lc
            java.lang.String r0 = "请您检查网络是否连接"
            r8.AlertToast(r0)
            return
        Lc:
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L77
            r1.<init>()     // Catch: java.lang.Exception -> L77
            java.util.List<com.psychiatrygarden.activity.online.bean.QuestionDetailBean> r2 = r8.mQuestionList     // Catch: java.lang.Exception -> L77
            java.util.Iterator r2 = r2.iterator()     // Catch: java.lang.Exception -> L77
            r3 = r0
        L19:
            boolean r4 = r2.hasNext()     // Catch: java.lang.Exception -> L75
            if (r4 == 0) goto L7c
            java.lang.Object r4 = r2.next()     // Catch: java.lang.Exception -> L75
            com.psychiatrygarden.activity.online.bean.QuestionDetailBean r4 = (com.psychiatrygarden.activity.online.bean.QuestionDetailBean) r4     // Catch: java.lang.Exception -> L75
            java.lang.String r5 = r4.getUser_answer()     // Catch: java.lang.Exception -> L75
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch: java.lang.Exception -> L75
            if (r5 == 0) goto L19
            int r5 = r1.length()     // Catch: java.lang.Exception -> L75
            if (r5 <= 0) goto L38
            r1.setLength(r0)     // Catch: java.lang.Exception -> L75
        L38:
            java.util.List r4 = r4.getOption()     // Catch: java.lang.Exception -> L75
            if (r4 == 0) goto L68
            boolean r5 = r4.isEmpty()     // Catch: java.lang.Exception -> L75
            if (r5 != 0) goto L68
            java.util.Iterator r4 = r4.iterator()     // Catch: java.lang.Exception -> L75
        L48:
            boolean r5 = r4.hasNext()     // Catch: java.lang.Exception -> L75
            if (r5 == 0) goto L68
            java.lang.Object r5 = r4.next()     // Catch: java.lang.Exception -> L75
            com.psychiatrygarden.activity.online.bean.QuestionDetailBean$OptionDTO r5 = (com.psychiatrygarden.activity.online.bean.QuestionDetailBean.OptionDTO) r5     // Catch: java.lang.Exception -> L75
            java.lang.String r6 = "1"
            java.lang.String r7 = r5.getType()     // Catch: java.lang.Exception -> L75
            boolean r6 = android.text.TextUtils.equals(r6, r7)     // Catch: java.lang.Exception -> L75
            if (r6 == 0) goto L48
            java.lang.String r5 = r5.getKey()     // Catch: java.lang.Exception -> L75
            r1.append(r5)     // Catch: java.lang.Exception -> L75
            goto L48
        L68:
            java.lang.String r4 = r1.toString()     // Catch: java.lang.Exception -> L75
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch: java.lang.Exception -> L75
            if (r4 == 0) goto L19
            int r3 = r3 + 1
            goto L19
        L75:
            r1 = move-exception
            goto L79
        L77:
            r1 = move-exception
            r3 = r0
        L79:
            r1.printStackTrace()
        L7c:
            java.lang.String r1 = "test"
            if (r3 != 0) goto L8e
            java.lang.String r2 = r8.mAnswerMode
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L8b
            java.lang.String r1 = "确定要交卷吗?"
            goto Lc1
        L8b:
            java.lang.String r1 = "确定要统计吗?"
            goto Lc1
        L8e:
            java.lang.String r2 = r8.mAnswerMode
            boolean r1 = r2.equals(r1)
            java.lang.String r2 = "您还有"
            if (r1 == 0) goto Lad
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r2)
            r1.append(r3)
            java.lang.String r2 = "题没做，确定要交卷吗?"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            goto Lc1
        Lad:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r2)
            r1.append(r3)
            java.lang.String r2 = "题没做，确定要统计吗?"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
        Lc1:
            com.psychiatrygarden.widget.HintNewDialog r2 = new com.psychiatrygarden.widget.HintNewDialog
            android.content.Context r3 = r8.mContext
            com.psychiatrygarden.activity.online.AnswerQuestionActivity$7 r4 = new com.psychiatrygarden.activity.online.AnswerQuestionActivity$7
            r4.<init>()
            java.lang.String r5 = "取消"
            r2.<init>(r3, r1, r5, r4)
            r2.setCancelable(r0)
            r2.isOutTouchDismiss(r0)
            r2.show()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.AnswerQuestionActivity.submitAnswer():void");
    }

    private void updateCut() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.ivCutQuestion.getLayoutParams();
        if (this.ivRight.getVisibility() == 0) {
            layoutParams.endToStart = R.id.iv_right;
            layoutParams.endToEnd = -1;
            ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = CommonUtil.dip2px(this, 16.0f);
        } else {
            layoutParams.endToStart = -1;
            layoutParams.endToEnd = 0;
        }
        this.ivCutQuestion.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCutIcon() {
        if (this.fromQuestionCombine) {
            return;
        }
        QuestionDetailBean questionDetailBean = this.handledQuestionList.get(this.mViewPager.getCurrentItem());
        int i2 = SkinManager.getCurrentSkinType(this) == 0 ? R.drawable.ic_cutted_day : R.drawable.ic_cutted_night;
        int i3 = SkinManager.getCurrentSkinType(this) == 0 ? R.drawable.ic_cut_question_day : R.drawable.ic_cut_question_night;
        if (TextUtils.isEmpty(questionDetailBean.getPublic_number())) {
            if (TextUtils.equals("1", questionDetailBean.getIs_cut())) {
                this.ivCutQuestion.setImageResource(i2);
                return;
            } else {
                this.ivCutQuestion.setImageResource(i3);
                return;
            }
        }
        List<QuestionDetailBean> shareStemQuestionList = getShareStemQuestionList(questionDetailBean.getPublic_number());
        if (shareStemQuestionList != null) {
            for (QuestionDetailBean questionDetailBean2 : shareStemQuestionList) {
                if (TextUtils.equals(questionDetailBean2.getId(), this.currentStemQuestionId)) {
                    if (TextUtils.equals("1", questionDetailBean2.getIs_cut())) {
                        this.ivCutQuestion.setImageResource(i2);
                        return;
                    } else {
                        this.ivCutQuestion.setImageResource(i3);
                        return;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public UpdateQuestionCutEvent updateCutSet(boolean isCut) {
        int currentItem = this.mViewPager.getCurrentItem();
        this.isLastQuestion = false;
        UpdateQuestionCutEvent updateQuestionCutEvent = new UpdateQuestionCutEvent();
        String public_number = this.handledQuestionList.get(currentItem).getPublic_number();
        if (TextUtils.isEmpty(public_number)) {
            this.handledQuestionList.get(currentItem).setIs_cut(isCut ? "1" : "2");
            if (currentItem <= this.handledQuestionList.size() - 1) {
                updateQuestionCutEvent = new UpdateQuestionCutEvent(this.handledQuestionList.get(currentItem).getId(), isCut ? "1" : "2");
                if (currentItem == this.handledQuestionList.size() - 1 && isCut && TextUtils.equals(this.mAnswerMode, Constants.ANSWER_MODE.TEST_MODE)) {
                    this.isLastQuestion = true;
                }
            }
        } else {
            List<QuestionDetailBean> shareStemQuestionList = getShareStemQuestionList(public_number);
            if (shareStemQuestionList != null && shareStemQuestionList.size() > 0) {
                QuestionDetailBean questionDetailBean = shareStemQuestionList.get(shareStemQuestionList.size() - 1);
                if (TextUtils.equals(this.mAnswerMode, Constants.ANSWER_MODE.TEST_MODE) && currentItem == this.handledQuestionList.size() - 1 && TextUtils.equals(this.currentStemQuestionId, questionDetailBean.getId()) && isCut) {
                    this.isLastQuestion = true;
                }
                Iterator<QuestionDetailBean> it = shareStemQuestionList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    QuestionDetailBean next = it.next();
                    if (TextUtils.equals(this.currentStemQuestionId, next.getId())) {
                        next.setIs_cut(isCut ? "1" : "2");
                        updateQuestionCutEvent = new UpdateQuestionCutEvent(next.getId(), isCut ? "1" : "2");
                    }
                }
            }
        }
        return updateQuestionCutEvent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightTitle() {
        boolean zIsEmpty = TextUtils.isEmpty(this.mQuestionType);
        int i2 = R.drawable.ic_question_submit_day;
        if (!zIsEmpty && this.mQuestionType.equals("all")) {
            String str = this.mAnswerMode;
            str.hashCode();
            switch (str) {
                case "practice":
                case "quick_brush":
                    this.ivRight.setVisibility(0);
                    this.ivRight.setImageResource(SkinManager.getCurrentSkinType(this) == 0 ? R.drawable.ic_question_statistics_day : R.drawable.ic_question_statistics_night);
                    break;
                case "recite":
                    this.ivRight.setVisibility(8);
                    break;
                case "test":
                    this.ivRight.setVisibility(0);
                    ImageView imageView = this.ivRight;
                    if (SkinManager.getCurrentSkinType(this) != 0) {
                        i2 = R.drawable.ic_question_submit_night;
                    }
                    imageView.setImageResource(i2);
                    break;
            }
        } else if (this.mAnswerMode.equals(Constants.ANSWER_MODE.TEST_MODE)) {
            this.ivRight.setVisibility(0);
            ImageView imageView2 = this.ivRight;
            if (SkinManager.getCurrentSkinType(this) != 0) {
                i2 = R.drawable.ic_question_submit_night;
            }
            imageView2.setImageResource(i2);
        } else {
            this.ivRight.setVisibility(8);
        }
        updateCut();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean checkCurrentQuestionIsLastQuestion() {
        /*
            r5 = this;
            com.psychiatrygarden.widget.CustomViewPager2 r0 = r5.mViewPager
            int r0 = r0.getCurrentItem()
            java.util.List<com.psychiatrygarden.activity.online.bean.QuestionDetailBean> r1 = r5.handledQuestionList
            java.lang.Object r1 = r1.get(r0)
            com.psychiatrygarden.activity.online.bean.QuestionDetailBean r1 = (com.psychiatrygarden.activity.online.bean.QuestionDetailBean) r1
            java.util.List<com.psychiatrygarden.activity.online.bean.QuestionDetailBean> r2 = r5.handledQuestionList
            int r2 = r2.size()
            r3 = 1
            int r2 = r2 - r3
            r4 = 0
            if (r0 == r2) goto L1a
            return r4
        L1a:
            java.lang.String r2 = r1.getPublic_number()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L31
            java.util.List<com.psychiatrygarden.activity.online.bean.QuestionDetailBean> r1 = r5.handledQuestionList
            int r1 = r1.size()
            int r1 = r1 - r3
            if (r0 != r1) goto L2e
            goto L2f
        L2e:
            r3 = r4
        L2f:
            r4 = r3
            goto L53
        L31:
            java.util.List<androidx.fragment.app.Fragment> r2 = r5.mFragmentList
            java.lang.Object r0 = r2.get(r0)
            androidx.fragment.app.Fragment r0 = (androidx.fragment.app.Fragment) r0
            boolean r2 = r0 instanceof com.psychiatrygarden.activity.online.fragment.ShareStemQuestionFragment
            if (r2 == 0) goto L53
            com.psychiatrygarden.activity.online.fragment.ShareStemQuestionFragment r0 = (com.psychiatrygarden.activity.online.fragment.ShareStemQuestionFragment) r0
            int r0 = r0.getCurrentPosition()
            java.lang.String r1 = r1.getPublic_number()
            java.util.List r1 = r5.getShareStemQuestionList(r1)
            int r1 = r1.size()
            int r1 = r1 - r3
            if (r0 != r1) goto L2e
            goto L2f
        L53:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.AnswerQuestionActivity.checkCurrentQuestionIsLastQuestion():boolean");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void clearData() {
        resetData();
    }

    public QuestionDetailBean getQuestionDetailBean(int position) {
        if (position < 0 || position >= this.mQuestionList.size()) {
            return null;
        }
        return this.mQuestionList.get(position);
    }

    public List<QuestionDetailBean> getShareStemQuestionList(String public_number) {
        ArrayMap<String, List<QuestionDetailBean>> arrayMap = this.shareStemMap;
        return (arrayMap == null || arrayMap.isEmpty()) ? new ArrayList() : this.shareStemMap.get(public_number);
    }

    public String getVideoId() {
        return this.videoId;
    }

    public void hideTitleView(String questionId) {
        if (this.video_summary) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = this.rlTopChapterTitle.getLayoutParams();
        if (layoutParams.height != 0) {
            layoutParams.height = 0;
            this.rlTopChapterTitle.setLayoutParams(layoutParams);
            this.mIsTitleShow = false;
        }
        this.titleShowArr.put(this.mViewPager.getCurrentItem(), Boolean.FALSE);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    @SuppressLint({"ClickableViewAccessibility"})
    public void init() {
        this.mBtnLeft = (ImageView) findViewById(R.id.include_btn_left);
        this.tvTitle = (TextView) findViewById(R.id.include_title_center);
        this.ivRight = (ImageView) findViewById(R.id.iv_right);
        CustomViewPager2 customViewPager2 = (CustomViewPager2) findViewById(R.id.questiondetails_viewPager);
        this.mViewPager = customViewPager2;
        if (!this.video_summary) {
            customViewPager2.setListener(new CustomViewPager2.LeftSwipListener() { // from class: com.psychiatrygarden.activity.online.b
                @Override // com.psychiatrygarden.widget.CustomViewPager2.LeftSwipListener
                public final void onLeftSwipe() {
                    this.f13144a.lambda$init$3();
                }
            });
        }
        this.rlTopChapterTitle = (RelativeLayout) findViewById(R.id.rl_top_title);
        this.tvChapterTitle = (TextView) findViewById(R.id.questiondetails_tv_title);
        this.tvTitle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13148c.lambda$init$4(view);
            }
        });
        this.mIvBack2Top.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13152c.lambda$init$5(view);
            }
        });
        initTitle();
        if (this.mIsYearStatistics) {
            this.ivRight.setVisibility(8);
        }
        if (this.fromQuestionCombine) {
            this.tvChapterTitle.setVisibility(8);
            this.rlTopChapterTitle.setVisibility(8);
        }
        if (this.video_summary) {
            this.rlTopChapterTitle.setVisibility(8);
        }
        initViewPage();
        updateCut();
        if (this.fromSearch || this.fromEBook || this.video_summary) {
            this.ivCutQuestion.setVisibility(8);
            this.ivRight.setVisibility(8);
            this.cutGuideView.setVisibility(8);
            setAppbarCorlor(Boolean.FALSE);
        } else {
            this.cutGuideView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.e
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13159c.lambda$init$7();
                }
            });
        }
        if (this.video_summary) {
            findViewById(R.id.ctl).setVisibility(8);
        }
        View viewFindViewById = findViewById(R.id.iv_triangle);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewFindViewById.getLayoutParams();
        if (this.ivRight.getVisibility() == 0) {
            if (this.ivCutQuestion.getVisibility() == 0) {
                layoutParams.rightMargin = SizeUtil.dp2px(this, 45);
            }
        } else if (this.ivCutQuestion.getVisibility() == 0) {
            layoutParams.rightMargin = SizeUtil.dp2px(this, 10);
        }
        viewFindViewById.setLayoutParams(layoutParams);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        super.initStatusBar();
        getWindow().setNavigationBarColor(Color.parseColor(this.mBaseTheme == 0 ? "#FBFBFB" : "#1C2134"));
    }

    public void initTitle() {
        if (!TextUtils.isEmpty(this.mSubjectTitle)) {
            this.tvTitle.setText(this.mSubjectTitle);
        } else if ("search".equals(this.mExternalsources)) {
            this.tvTitle.setText("搜索");
        } else if (TextUtils.isEmpty(this.node_id) && TextUtils.isEmpty(this.knowledge_id)) {
            this.tvTitle.setText("题目");
        } else {
            this.tvTitle.setText("");
        }
        if (!TextUtils.isEmpty(this.node_title)) {
            this.tvTitle.setText(this.node_title);
        }
        this.tvChapterTitle.setVisibility(TextUtils.isEmpty(this.mChapterTitle) ? 8 : 0);
        if (!TextUtils.isEmpty(this.mChapterTitle)) {
            this.tvChapterTitle.setText(this.mChapterTitle);
        }
        if (!this.video_summary) {
            this.rlTopChapterTitle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.activity.online.AnswerQuestionActivity.2
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    String id;
                    AnswerQuestionActivity answerQuestionActivity = AnswerQuestionActivity.this;
                    answerQuestionActivity.mTopTitleHeight = answerQuestionActivity.rlTopChapterTitle.getHeight();
                    if (AnswerQuestionActivity.this.mQuestionList.size() == 1) {
                        AnswerQuestionActivity.this.rlTopChapterTitle.setVisibility(8);
                    }
                    if (!AnswerQuestionActivity.this.mQuestionList.isEmpty() && "year".equals(AnswerQuestionActivity.this.mCategory) && "all".equals(AnswerQuestionActivity.this.mQuestionType)) {
                        EventBus eventBus = EventBus.getDefault();
                        int i2 = AnswerQuestionActivity.this.mTopTitleHeight;
                        AnswerQuestionActivity answerQuestionActivity2 = AnswerQuestionActivity.this;
                        if (answerQuestionActivity2.getQuestionDetailBean(answerQuestionActivity2.mPosition) == null) {
                            id = "";
                        } else {
                            AnswerQuestionActivity answerQuestionActivity3 = AnswerQuestionActivity.this;
                            id = answerQuestionActivity3.getQuestionDetailBean(answerQuestionActivity3.mPosition).getId();
                        }
                        eventBus.postSticky(new UpdateTopMargin(i2, id));
                    }
                    if (AnswerQuestionActivity.this.mTopTitleHeight > 0) {
                        AnswerQuestionActivity.this.rlTopChapterTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
        boolean zIsEmpty = TextUtils.isEmpty(this.mQuestionType);
        int i2 = R.drawable.ic_question_submit_day;
        if (!zIsEmpty && this.mQuestionType.equals("all")) {
            this.ivRight.setVisibility(0);
            String str = this.mAnswerMode;
            str.hashCode();
            switch (str) {
                case "practice":
                case "quick_brush":
                    this.ivRight.setVisibility(0);
                    this.ivRight.setImageResource(SkinManager.getCurrentSkinType(this) == 0 ? R.drawable.ic_question_statistics_day : R.drawable.ic_question_statistics_night);
                    break;
                case "recite":
                    this.ivRight.setVisibility(8);
                    break;
                case "test":
                    this.ivRight.setVisibility(0);
                    ImageView imageView = this.ivRight;
                    if (SkinManager.getCurrentSkinType(this) != 0) {
                        i2 = R.drawable.ic_question_submit_night;
                    }
                    imageView.setImageResource(i2);
                    break;
            }
            if (this.mCategory.equals("cases") || this.mCategory.equals("points")) {
                this.ivRight.setVisibility(8);
            } else if (this.mCategory.equals("year") && TextUtils.isEmpty(this.mChapterTitle)) {
                this.tvChapterTitle.setVisibility(0);
                try {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.tvChapterTitle.getLayoutParams();
                    layoutParams.rightMargin = CommonUtil.dip2px(this, 10.0f);
                    layoutParams.leftMargin = CommonUtil.dip2px(this, 10.0f);
                    this.tvChapterTitle.setLayoutParams(layoutParams);
                    this.tvChapterTitle.setTextSize(12.0f);
                    this.tvChapterTitle.setSingleLine(false);
                    this.tvChapterTitle.setTextColor(getResources().getColor(R.color.question_color, getTheme()));
                    if (SkinManager.getCurrentSkinType(this) == 1) {
                        this.tvChapterTitle.setTextColor(getResources().getColor(R.color.question_color_night, getTheme()));
                    }
                    this.tvChapterTitle.setText(this.mQuestionList.get(this.mPosition).getScore_describe());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } else if (this.mAnswerMode.equals(Constants.ANSWER_MODE.TEST_MODE)) {
            this.ivRight.setVisibility(0);
            ImageView imageView2 = this.ivRight;
            if (SkinManager.getCurrentSkinType(this) != 0) {
                i2 = R.drawable.ic_question_submit_night;
            }
            imageView2.setImageResource(i2);
        } else {
            this.ivRight.setVisibility(8);
        }
        if (this.mAnswerMode.equals(Constants.ANSWER_MODE.PRACTICE_MODE)) {
            performPointRequest();
        }
    }

    public boolean isShowChapterTitle() {
        return this.rlTopChapterTitle.getVisibility() == 0 && !TextUtils.isEmpty(this.mChapterTitle);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        ProjectApp.showQuestionList = "";
        finish();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        if (v2.getId() == R.id.include_btn_left) {
            ProjectApp.showQuestionList = "";
            finish();
        } else if (v2.getId() == R.id.iv_right) {
            if (!this.mIsSearch && !this.fromEBook) {
                submitAnswer();
                return;
            }
            Intent intentPutExtra = new Intent(this.mContext, (Class<?>) CommentSeachActivity.class).putExtra("question_id", this.mQuestionList.get(this.mViewPager.getCurrentItem()).getId());
            String str = this.mModuleType;
            startActivity(intentPutExtra.putExtra("module_type", str == null ? 0 : Integer.parseInt(str)));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        String str;
        if (!this.isToNextChapter && !this.fromQuestionCombine && (str = this.mQuestionType) != null && str.equals("all")) {
            EventBus.getDefault().post(new RedoOtherQuestionEvent("all", true));
        }
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(CheckLastQuestionEvent event) {
        if (TextUtils.equals(this.mAnswerMode, Constants.ANSWER_MODE.TEST_MODE)) {
            return;
        }
        Bundle bundleExtra = getIntent().getBundleExtra("bundle");
        boolean z2 = bundleExtra != null ? bundleExtra.getBoolean("hasNextChapter", false) : false;
        if (checkCurrentQuestionIsLastQuestion() && z2) {
            showNextChapterPop();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        jump2Statistis(requstUrl);
    }

    public void onScrollChange(int scrollVerticalDistance, String questionId, int contentHeight) {
        int i2;
        if (this.mLastUpdate + 300 > System.currentTimeMillis() || contentHeight <= 0 || this.mQuestionList.size() == 1 || this.fromQuestionCombine) {
            return;
        }
        this.mLastUpdate = System.currentTimeMillis();
        int i3 = 0;
        while (true) {
            if (i3 >= this.mQuestionList.size()) {
                i3 = -1;
                break;
            } else if (TextUtils.equals(questionId, this.mQuestionList.get(i3).getId())) {
                break;
            } else {
                i3++;
            }
        }
        if ((i3 == -1 || TextUtils.equals(this.mQuestionList.get(i3).getId(), questionId)) && (i2 = this.mTopTitleHeight) != 0) {
            if (this.mIsSearch) {
                execAnimation(false);
                return;
            }
            if (scrollVerticalDistance + i2 > contentHeight) {
                if ((scrollVerticalDistance - contentHeight) - 30 <= i2) {
                    if (this.mIsTitleShow) {
                        execAnimation(false);
                        return;
                    }
                    return;
                } else {
                    if (this.mIsTitleShow) {
                        return;
                    }
                    execAnimation(true);
                    return;
                }
            }
            if (scrollVerticalDistance > i2 * 0.1d) {
                if (this.mIsTitleShow) {
                    return;
                }
                execAnimation(true);
            } else if (this.mIsTitleShow) {
                execAnimation(false);
            }
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    public void onStemQuestionPageChange(String id) {
        this.currentStemQuestionId = id;
        updateCutIcon();
    }

    public void questionExpose(String type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", type);
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"));
        YJYHttpUtils.get(getApplicationContext(), NetworkRequestsURL.QUESTION_EXPOSE, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.AnswerQuestionActivity.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    new JSONObject(s2).optString("code", "").equals("200");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void removeQuestionPage(String questionId) {
        this.questionPageList.remove(questionId);
    }

    public void saveQuestionPage(String questionId) {
        this.questionPageList.add(questionId);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        this.shareStemMap = new ArrayMap<>();
        getLifecycle().addObserver(this);
        setContentView(R.layout.activity_answer_detail_new);
        ImageView imageView = (ImageView) findViewById(R.id.iv_back_2_top);
        this.mIvBack2Top = imageView;
        int i2 = 8;
        imageView.setVisibility(8);
        this.cutGuideView = findViewById(R.id.ll_cut_guide);
        this.ivCutQuestion = (ImageView) findViewById(R.id.iv_cut_question);
        this.answerQuestionMask = findViewById(R.id.answer_question_mask);
        this.ivCutQuestion.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13410c.lambda$setContentView$0(view);
            }
        });
        this.answerQuestionMask.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AnswerQuestionActivity.lambda$setContentView$1(view);
            }
        });
        Uri data = getIntent().getData();
        if (data != null) {
            this.fromEBook = "ebook".equals(data.getScheme());
        }
        if (this.fromEBook) {
            ProjectApp.showQuestionList = data.getQueryParameter("questionList");
        }
        List<QuestionDetailBean> list = (List) new Gson().fromJson(ProjectApp.showQuestionList, new TypeToken<List<QuestionDetailBean>>() { // from class: com.psychiatrygarden.activity.online.AnswerQuestionActivity.1
        }.getType());
        this.mQuestionList = list;
        if (list == null) {
            this.mQuestionList = new ArrayList();
        }
        if (this.mQuestionList.size() == 0) {
            return;
        }
        if (this.fromEBook) {
            getEBookData();
        } else {
            Bundle bundleExtra = getIntent().getBundleExtra("bundle");
            if (bundleExtra != null) {
                this.knowledge_id = bundleExtra.getString("knowledgeId");
                this.node_id = bundleExtra.getString("nodeId");
                this.node_title = bundleExtra.getString("node_title", null);
                this.question_bank_id = bundleExtra.getString("question_bank_id", null);
                this.knowledge_point = bundleExtra.getBoolean("knowledge_point", false);
                this.video_summary = bundleExtra.getBoolean("video_summary", false);
                this.mModuleType = bundleExtra.getString("module_type", "");
                this.mPosition = bundleExtra.getInt("position", 0);
                this.mSubjectTitle = bundleExtra.getString("subject_title", "");
                this.mChapterTitle = bundleExtra.getString("chapter_title", "");
                String string = bundleExtra.getString("type", "");
                this.mQuestionType = string;
                if (TextUtils.isEmpty(string)) {
                    this.mQuestionType = "";
                }
                this.mCategory = bundleExtra.getString(UriUtil.QUERY_CATEGORY, "");
                this.mIdentityId = bundleExtra.getString("identity_id", "");
                this.mChapterId = bundleExtra.getString("chapter_id", "");
                this.mChapterParentId = bundleExtra.getString("chapter_parent_id", "");
                this.mAnswerMode = bundleExtra.getString("answerMode", Constants.ANSWER_MODE.PRACTICE_MODE);
                this.mIsYearStatistics = bundleExtra.getBoolean("isYearStatistics", false);
                this.mIsShowNumber = bundleExtra.getString("is_show_number", "");
                this.mExternalsources = bundleExtra.getString("externalsources", "");
                this.fromQuestionCombine = bundleExtra.getBoolean("fromQuestionCombine", false);
                this.fromSearch = bundleExtra.getBoolean("fromSearch", false);
                this.study_plan = bundleExtra.getBoolean("study_plan", false);
                this.nextNode = bundleExtra.getBoolean("nextNode", false);
                this.questionId = bundleExtra.getString("question_id");
            }
        }
        ImageView imageView2 = this.ivCutQuestion;
        if (!this.fromQuestionCombine && !this.fromEBook) {
            i2 = 0;
        }
        imageView2.setVisibility(i2);
        handleQuestionData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    @SuppressLint({"ClickableViewAccessibility"})
    public void setListenerForWidget() {
        this.mBtnLeft.setOnClickListener(this);
        this.ivRight.setOnClickListener(this);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.online.AnswerQuestionActivity.5
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int arg0) {
                String score_describe;
                String id;
                boolean z2;
                AnswerQuestionActivity.this.mPosition = arg0;
                QuestionDetailBean questionDetailBean = (QuestionDetailBean) AnswerQuestionActivity.this.handledQuestionList.get(arg0);
                if (TextUtils.isEmpty(questionDetailBean.getPublic_number())) {
                    AnswerQuestionActivity.this.currentStemQuestionId = questionDetailBean.getId();
                    score_describe = questionDetailBean.getScore_describe();
                    id = questionDetailBean.getId();
                    z2 = !TextUtils.isEmpty(questionDetailBean.getUser_answer());
                    AnswerQuestionActivity.this.ivCutQuestion.setImageResource((TextUtils.isEmpty(questionDetailBean.getIs_cut()) || TextUtils.equals(questionDetailBean.getIs_cut(), "2")) ? SkinManager.getCurrentSkinType(AnswerQuestionActivity.this) == 0 ? R.drawable.ic_cut_question_day : R.drawable.ic_cut_question_night : TextUtils.equals(questionDetailBean.getIs_cut(), "1") ? SkinManager.getCurrentSkinType(AnswerQuestionActivity.this) == 0 ? R.drawable.ic_cutted_day : R.drawable.ic_cutted_night : 0);
                } else {
                    EventBus.getDefault().postSticky(new RefreshIdNotifyEvent());
                    List<QuestionDetailBean> shareStemQuestionList = AnswerQuestionActivity.this.getShareStemQuestionList(questionDetailBean.getPublic_number());
                    if (shareStemQuestionList == null || shareStemQuestionList.isEmpty()) {
                        score_describe = null;
                        id = null;
                        z2 = false;
                    } else {
                        for (QuestionDetailBean questionDetailBean2 : shareStemQuestionList) {
                            if (TextUtils.equals(questionDetailBean2.getId(), AnswerQuestionActivity.this.currentStemQuestionId)) {
                                String id2 = questionDetailBean2.getId();
                                String score_describe2 = questionDetailBean2.getScore_describe();
                                boolean z3 = !TextUtils.isEmpty(questionDetailBean2.getUser_answer());
                                id = id2;
                                score_describe = score_describe2;
                                z2 = z3;
                                break;
                            }
                        }
                        score_describe = null;
                        id = null;
                        z2 = false;
                    }
                }
                if (TextUtils.equals(AnswerQuestionActivity.this.mCategory, "year") && TextUtils.isEmpty(AnswerQuestionActivity.this.mChapterTitle) && !TextUtils.isEmpty(score_describe)) {
                    AnswerQuestionActivity.this.tvChapterTitle.setText(score_describe);
                }
                if ("year".equals(AnswerQuestionActivity.this.mCategory) && "all".equals(AnswerQuestionActivity.this.mQuestionType)) {
                    EventBus.getDefault().post(new UpdateTopMargin(AnswerQuestionActivity.this.mTopTitleHeight, id));
                }
                AnswerQuestionActivity.this.updateRightTitle();
                if (id != null && !AnswerQuestionActivity.this.video_summary) {
                    boolean booleanConfig = SharePreferencesUtils.readBooleanConfig(id, false, AnswerQuestionActivity.this);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) AnswerQuestionActivity.this.rlTopChapterTitle.getLayoutParams();
                    AnswerQuestionActivity.this.mIsSearch = booleanConfig;
                    if (!AnswerQuestionActivity.this.questionPageList.contains(id)) {
                        layoutParams.height = ("year".equals(AnswerQuestionActivity.this.mCategory) && "all".equals(AnswerQuestionActivity.this.mQuestionType)) ? AnswerQuestionActivity.this.mTopTitleHeight : 0;
                    } else if (z2) {
                        layoutParams.height = booleanConfig ? 0 : AnswerQuestionActivity.this.mTopTitleHeight;
                    } else if ("year".equals(AnswerQuestionActivity.this.mCategory) && "all".equals(AnswerQuestionActivity.this.mQuestionType)) {
                        layoutParams.height = AnswerQuestionActivity.this.mTopTitleHeight;
                    } else {
                        layoutParams.height = 0;
                        AnswerQuestionActivity.this.execAnimation(false);
                    }
                    AnswerQuestionActivity.this.rlTopChapterTitle.setLayoutParams(layoutParams);
                }
                Boolean bool = (Boolean) AnswerQuestionActivity.this.titleShowArr.get(arg0);
                ViewGroup.LayoutParams layoutParams2 = AnswerQuestionActivity.this.rlTopChapterTitle.getLayoutParams();
                if (bool == Boolean.TRUE) {
                    if (layoutParams2.height != AnswerQuestionActivity.this.mTopTitleHeight) {
                        AnswerQuestionActivity.this.showTitleView();
                    }
                } else if (bool == Boolean.FALSE && layoutParams2.height != 0) {
                    AnswerQuestionActivity.this.hideTitleView(null);
                }
                AnswerQuestionActivity.this.showHideBack2TopIcon(false);
                ProjectApp.analysisContent = "";
                ProjectApp.analysisImageStr = new ArrayList();
                ProjectApp.analysisIsHidde = false;
                ProjectApp.noteContent = "";
                ProjectApp.noteBigImage = "";
                ProjectApp.noteSmellImage = "";
                LogUtils.d("question_id", AnswerQuestionActivity.this.currentStemQuestionId);
                AnswerQuestionActivity.this.updateCutIcon();
                AnswerQuestionActivity.this.resetData();
                String str = questionDetailBean.getChapter_parent_id() + "-" + questionDetailBean.getChapter_id() + "-" + questionDetailBean.getId();
                if (AnswerQuestionActivity.this.mQuestionType.equals("all") && !AnswerQuestionActivity.this.fromQuestionCombine) {
                    SharePreferencesUtils.writeStrConfig(CommonParameter.LAST_DO_QUESTION + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, AnswerQuestionActivity.this.mContext) + AnswerQuestionActivity.this.mCategory, str, AnswerQuestionActivity.this);
                }
                if (!TextUtils.isEmpty(questionDetailBean.getKnowledge_id())) {
                    SharePreferencesUtils.writeStrConfig(CommonParameter.LAST_DO_QUESTION_KNOWLEDGE_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, AnswerQuestionActivity.this.mContext), AnswerQuestionActivity.this.knowledge_id + "-" + questionDetailBean.getId(), AnswerQuestionActivity.this.mContext);
                }
                Fragment fragment = (Fragment) AnswerQuestionActivity.this.mFragmentList.get(arg0);
                if (fragment instanceof SubChoiceQuestionFragment) {
                    ((SubChoiceQuestionFragment) fragment).onVisible();
                } else if (fragment instanceof SubSubjectiveQuestionFragment) {
                    ((SubSubjectiveQuestionFragment) fragment).onVisible();
                }
            }
        });
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void showHideBack2TopIcon(boolean isShow) {
        if (!isShow) {
            if (this.mIvBack2Top.getVisibility() != 8) {
                this.mIvBack2Top.setVisibility(8);
            }
        } else if (this.mIvBack2Top.getVisibility() != 0) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(500L);
            this.mIvBack2Top.setAnimation(alphaAnimation);
            this.mIvBack2Top.setVisibility(0);
        }
    }

    public void showTitleView() {
        if (this.video_summary) {
            return;
        }
        execAnimation(true);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        if (NetworkRequestsURL.getanswerApi.equals(requstUrl)) {
            jump2Statistis(requstUrl);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (EventBusConstant.EVENT_QUESTION_ANSWER_SKIP.equals(str)) {
            String public_number = this.handledQuestionList.get(this.mPosition).getPublic_number();
            if (this.mPosition == this.handledQuestionList.size() - 1) {
                if (TextUtils.isEmpty(public_number)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("已是最后一题");
                    sb.append(TextUtils.equals(this.mAnswerMode, Constants.ANSWER_MODE.TEST_MODE) ? "，请交卷" : "");
                    AlertToast(sb.toString());
                    return;
                }
                List<QuestionDetailBean> shareStemQuestionList = getShareStemQuestionList(public_number);
                if (shareStemQuestionList == null || shareStemQuestionList.isEmpty()) {
                    return;
                }
                Fragment fragment = this.mFragmentList.get(this.mPosition);
                if ((fragment instanceof ShareStemQuestionFragment) && ((ShareStemQuestionFragment) fragment).getCurrentPosition() == shareStemQuestionList.size() - 1) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("已是最后一题");
                    sb2.append(TextUtils.equals(this.mAnswerMode, Constants.ANSWER_MODE.TEST_MODE) ? "，请交卷" : "");
                    AlertToast(sb2.toString());
                    return;
                }
                return;
            }
            this.mViewPager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.k
                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException {
                    this.f13434c.lambda$onEventMainThread$15();
                }
            }, 100L);
            return;
        }
        if ("jump2KnowledgeChapter".equals(str)) {
            finish();
        }
    }

    @Subscribe
    public void onEventMainThread(HideChapterTitleEvent event) {
        if (this.periodTime + 200 > System.currentTimeMillis()) {
            return;
        }
        this.periodTime = System.currentTimeMillis();
        QuestionDetailBean questionDetailBean = this.handledQuestionList.get(this.mViewPager.getCurrentItem());
        if (TextUtils.isEmpty(questionDetailBean.getPublic_number())) {
            if (!TextUtils.equals(event.getQuestionId(), questionDetailBean.getId())) {
                return;
            }
        } else {
            Fragment fragment = this.mFragmentList.get(this.mViewPager.getCurrentItem());
            if (fragment instanceof ShareStemQuestionFragment) {
                int currentPosition = ((ShareStemQuestionFragment) fragment).getCurrentPosition();
                List<QuestionDetailBean> shareStemQuestionList = getShareStemQuestionList(questionDetailBean.getPublic_number());
                if (shareStemQuestionList != null && !shareStemQuestionList.isEmpty()) {
                    if (!TextUtils.equals(event.getQuestionId(), shareStemQuestionList.get(currentPosition).getId())) {
                        return;
                    }
                }
            }
        }
        this.mIsSearch = event.isHide();
        if (event.isHide()) {
            this.ivRight.setImageResource(SkinManager.getCurrentSkinType(this) == 0 ? R.drawable.ic_question_search_day : R.drawable.ic_question_search_night);
            if ((Objects.equals(this.mAnswerMode, Constants.ANSWER_MODE.RECITE_MODE) || this.fromEBook) && this.ivRight.getVisibility() != 0) {
                this.ivRight.setVisibility(0);
            }
        } else {
            if (Objects.equals(this.mAnswerMode, Constants.ANSWER_MODE.TEST_MODE)) {
                this.ivRight.setImageResource(SkinManager.getCurrentSkinType(this) == 0 ? R.drawable.ic_question_submit_day : R.drawable.ic_question_submit_night);
            } else {
                this.ivRight.setImageResource(SkinManager.getCurrentSkinType(this) == 0 ? R.drawable.ic_question_statistics_day : R.drawable.ic_question_statistics_night);
            }
            if (Objects.equals(this.mAnswerMode, Constants.ANSWER_MODE.RECITE_MODE) && this.ivRight.getVisibility() != 8) {
                this.ivRight.setVisibility(8);
            }
        }
        updateCut();
        if (this.fromEBook) {
            this.ivRight.setVisibility(8);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UpdateQuestionIdEvent event) {
        if (TextUtils.isEmpty(event.getQuestionId())) {
            return;
        }
        this.currentStemQuestionId = event.getQuestionId();
        updateCutIcon();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMessage event) {
        if (EventBusConstant.EVENT_QUESTION_ANSWER_OPTION.equals(event.getKey())) {
            QuestionDetailBean questionDetailBean = (QuestionDetailBean) event.getValueObj();
            if (questionDetailBean != null) {
                for (int i2 = 0; i2 < this.mQuestionList.size(); i2++) {
                    if (TextUtils.equals(questionDetailBean.getId(), this.mQuestionList.get(i2).getId())) {
                        this.mQuestionList.get(i2).setUser_answer(questionDetailBean.getUser_answer());
                        this.mQuestionList.get(i2).setOption(questionDetailBean.getOption());
                        return;
                    }
                }
                return;
            }
            return;
        }
        if (EventBusConstant.EVENT_QUESTION_ANSWER_REFRESH.equals(event.getKey()) && this.fromSearch) {
            this.mAnswerMode = Constants.ANSWER_MODE.RECITE_MODE;
        }
    }
}
