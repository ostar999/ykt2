package com.psychiatrygarden.activity.online;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.ArrayMap;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.text.StrPool;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.aliyun.vod.common.utils.UriUtil;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.HomePageNewActivity;
import com.psychiatrygarden.activity.answer.BaseQuestionRemakeActivity;
import com.psychiatrygarden.activity.online.adapter.AnswerSheetAdapter;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.online.bean.QuestionListBean;
import com.psychiatrygarden.bean.AnswerBean;
import com.psychiatrygarden.bean.ChapterTitleInfo;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.NextChapterInfo;
import com.psychiatrygarden.bean.StatisticsData;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.CloseAnswerSheetEvent;
import com.psychiatrygarden.event.GetNextChapterDataEvent;
import com.psychiatrygarden.event.QuestionCollectEvent;
import com.psychiatrygarden.event.RedoOtherQuestionEvent;
import com.psychiatrygarden.event.RefreshCutQuestionEvent;
import com.psychiatrygarden.event.RefreshHomePaperListEvent;
import com.psychiatrygarden.event.RefreshPaperListEvent;
import com.psychiatrygarden.event.ShowStatisticsEvent;
import com.psychiatrygarden.event.UpdateQuestionCutEvent;
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
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.ErrorQuestionRedoDialog;
import com.psychiatrygarden.widget.QuestionStatisticsPop;
import com.psychiatrygarden.widget.UnlockNextChapterPop;
import com.psychiatrygarden.widget.glideUtil.util.Utils;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class AnswerSheetActivity extends BaseActivity implements QuestionDataCallBack<String>, OnItemClickListener, View.OnClickListener {
    private String answerMode;
    private AnswerSheetAdapter answerSheetAdapter;
    private AppBarLayout appBarLayout;
    private String category;
    private String category_id;
    private ArrayList<String> chapterIds;
    private String chapterParentId;
    private String chapter_title;
    private boolean fromQuestionCombine;
    private boolean hasNextChapter;
    private String identity_id;
    private boolean isClickNextChapter;
    private boolean isCutQuestion;
    private boolean isNextChapter;
    private String is_show_number;
    private LoadingPopupView loadingView;
    private StatisticsData mStatisticsData;
    private QuestionStatisticsPop mStatisticsPop;
    private String module_type;
    private boolean nextChapterUnlock;
    private String nextUnlockId;
    private RelativeLayout openrel;
    private TextView opentxt;
    private String paperId;
    private String primary_id;
    private RecyclerView questionList_GridView;
    private String question_bank_id;
    private RecyclerView recycleview;
    private List<QuestionListBean.DataDTO.SearchDTO> search;
    private String subject_title;
    private TextView tvStatistics;
    private String type;
    private String unit;
    private String unit_id;
    private boolean[] unlockStateArr;
    private List<QuestionDetailBean> questionList = new ArrayList();
    private final List<QuestionDetailBean> showList = new ArrayList();
    private boolean isYearStatistics = false;
    private boolean isFiltrate = false;
    private int currentChapterPos = 0;
    double errorCount = 0.0d;
    double rightCount = 0.0d;
    int noDo = 0;
    JSONArray mArraySubAnswer = new JSONArray();
    String target_alias = "";
    private String isRedoType = "";

    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler = new Handler(new Handler.Callback() { // from class: com.psychiatrygarden.activity.online.l0
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            return this.f13438c.lambda$new$13(message);
        }
    });

    private void dealNextChapter() {
        boolean z2 = false;
        if (this.isClickNextChapter) {
            this.appBarLayout.setExpanded(true, true);
            this.questionList_GridView.smoothScrollToPosition(0);
            this.isClickNextChapter = false;
            updateTitle();
            int i2 = this.currentChapterPos + 1;
            this.currentChapterPos = i2;
            if (i2 < this.chapterIds.size()) {
                this.primary_id = this.chapterIds.get(this.currentChapterPos);
                this.hasNextChapter = true;
            } else {
                ArrayList<String> arrayList = this.chapterIds;
                this.primary_id = arrayList.get(arrayList.size() - 1);
                this.hasNextChapter = false;
            }
        } else {
            if (this.chapterIds.size() > 0 && this.unlockStateArr.length > 0) {
                z2 = true;
            }
            this.hasNextChapter = z2;
        }
        if (this.hasNextChapter) {
            if ("all".equals(this.type)) {
                int i3 = this.currentChapterPos;
                boolean[] zArr = this.unlockStateArr;
                if (i3 < zArr.length) {
                    this.nextChapterUnlock = zArr[i3];
                }
            } else {
                this.nextChapterUnlock = true;
            }
            if (this.currentChapterPos < this.chapterIds.size()) {
                this.nextUnlockId = this.chapterIds.get(this.currentChapterPos);
            }
        }
    }

    private void getAdConfig() {
        YJYHttpUtils.post(getApplicationContext(), NetworkRequestsURL.QUESTION_AD_CONFIG, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.AnswerSheetActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws JSONException {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (TextUtils.equals("200", jSONObject.optString("code", "0"))) {
                        JSONArray jSONArray = jSONObject.getJSONArray("data");
                        if (jSONArray.length() > 0) {
                            int length = jSONArray.length();
                            for (int i2 = 0; i2 < length; i2++) {
                                JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                                if (CommonParameter.AD_CONFIG.equals(jSONObject2.optString("key", ""))) {
                                    SharePreferencesUtils.writeStrConfig(CommonParameter.AD_CONFIG, jSONObject2.optString("value", ""), AnswerSheetActivity.this.getApplicationContext());
                                    return;
                                }
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    private int getCountByType(int i2) {
        int iEquals;
        int i3 = 0;
        for (int i4 = 0; i4 < this.showList.size(); i4++) {
            QuestionDetailBean questionDetailBean = this.showList.get(i4);
            if (questionDetailBean != null) {
                if (i2 == 3) {
                    iEquals = TextUtils.isEmpty(questionDetailBean.getUser_answer());
                } else {
                    if (TextUtils.isEmpty(questionDetailBean.getIs_right())) {
                        questionDetailBean.setIs_right("0");
                    }
                    iEquals = TextUtils.equals(i2 == 1 ? "1" : "0", questionDetailBean.getIs_right());
                }
                i3 += iEquals;
            }
        }
        return i3;
    }

    private void getPaperData(String id) {
        if (TextUtils.isEmpty(id)) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("paper_id", id);
        YJYHttpUtils.get(this, NetworkRequestsURL.paperDetail, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.AnswerSheetActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    AnswerSheetActivity.this.onSuccess(s2, NetworkRequestsURL.paperDetail);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getPingyuData(String zhengquelvValue, final boolean detail) {
        AjaxParams ajaxParams = new AjaxParams();
        this.mStatisticsData.setShowNextChapter(this.hasNextChapter);
        ajaxParams.put("scoring_rate", zhengquelvValue);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mremarkUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.AnswerSheetActivity.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                AnswerSheetActivity.this.showStatisticsPop();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    JSONObject jSONObjectOptJSONObject = new JSONObject(s2).optJSONObject("data");
                    if (jSONObjectOptJSONObject != null) {
                        AnswerSheetActivity.this.mStatisticsData.setDescrible(jSONObjectOptJSONObject.optString("remark"));
                    }
                    if (Objects.equals(AnswerSheetActivity.this.answerMode, Constants.ANSWER_MODE.TEST_MODE) && !detail) {
                        AnswerSheetActivity.this.mStatisticsData.setShowNextChapter(false);
                        EventBus.getDefault().post(new ShowStatisticsEvent(AnswerSheetActivity.this.mStatisticsData));
                        AnswerSheetActivity.this.finish();
                        return;
                    }
                    AnswerSheetActivity.this.showStatisticsPop();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (Objects.equals(AnswerSheetActivity.this.answerMode, Constants.ANSWER_MODE.TEST_MODE)) {
                        return;
                    }
                    AnswerSheetActivity.this.showStatisticsPop();
                }
            }
        });
    }

    private void getQuestionList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", this.identity_id);
        if (this.isClickNextChapter) {
            ajaxParams.put("primary_id", this.chapterIds.get(this.currentChapterPos));
        } else {
            ajaxParams.put("primary_id", this.primary_id);
        }
        if (!TextUtils.isEmpty(this.module_type)) {
            ajaxParams.put("module_type", this.module_type);
        }
        if (this.isCutQuestion) {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, "chapter");
            ajaxParams.put("type", AliyunLogCommon.SubModule.CUT);
            QuestionDataRequest.getIntance(this).questionList(ajaxParams, this.category, true, this);
        } else {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, this.category);
            ajaxParams.put("type", this.type);
            ajaxParams.put("am_pm", SharePreferencesUtils.readStrConfig(CommonParameter.am_pm, this, ""));
            if (!TextUtils.isEmpty(this.unit_id)) {
                ajaxParams.put("unit_id", this.unit_id);
            }
            QuestionDataRequest.getIntance(this).questionList(ajaxParams, this.category, false, this);
        }
    }

    private void getScoreNewData(String totalValue, String zuoguo, String rightValue, String wrongValue, String zhengquelvValue, boolean detail) {
        this.mStatisticsData = new StatisticsData(this.subject_title, this.chapter_title, Integer.parseInt(totalValue), Integer.parseInt(zuoguo), Integer.parseInt(rightValue), Integer.parseInt(wrongValue), "", false, this.fromQuestionCombine, zhengquelvValue);
        getPingyuData(zhengquelvValue, detail);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goto2NextChapter() throws JSONException {
        ArrayList<String> arrayList = this.chapterIds;
        if (arrayList == null || this.unlockStateArr == null || arrayList.isEmpty() || this.unlockStateArr.length == 0 || !this.hasNextChapter) {
            return;
        }
        final String str = this.chapterIds.get(this.currentChapterPos);
        if (!this.unlockStateArr[this.currentChapterPos]) {
            new XPopup.Builder(this).dismissOnTouchOutside(Boolean.FALSE).asCustom(new UnlockNextChapterPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.online.j0
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f13430a.lambda$goto2NextChapter$7(str);
                }
            })).show();
            return;
        }
        this.isClickNextChapter = true;
        if (TextUtils.equals(this.answerMode, Constants.ANSWER_MODE.TEST_MODE)) {
            submitAnswer("2");
        }
        getQuestionList();
    }

    public static void gotoActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, (Class<?>) AnswerSheetActivity.class);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    private void handleData() throws JSONException {
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < this.showList.size(); i4++) {
            try {
                if (this.showList.get(i4).getIs_right().equals("0")) {
                    i2++;
                } else if (this.showList.get(i4).getIs_right().equals("1")) {
                    i3++;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("errorCount", i2);
        jSONObject.put("rightCount", i3);
        jSONObject.put("primary_id", this.primary_id);
        jSONObject.put("identity_id", this.identity_id);
        jSONObject.put("type", this.type);
        EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_HOME_PAGE_DATA, jSONObject));
    }

    private void hideLoading() {
        LoadingPopupView loadingPopupView = this.loadingView;
        if (loadingPopupView == null || !loadingPopupView.isShow()) {
            return;
        }
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.online.i0
            @Override // java.lang.Runnable
            public final void run() {
                this.f13426c.lambda$hideLoading$10();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearnDialog$8(ErrorQuestionRedoDialog errorQuestionRedoDialog, View view) {
        this.isRedoType = "all";
        if (this.fromQuestionCombine) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put("paper_id", this.paperId);
            ajaxParams.put("is_wrong", "0");
            QuestionDataRequest.getIntance(this).redoAnswer(ajaxParams, NetworkRequestsURL.combineRedo, this);
        } else {
            postRedoValue("0");
        }
        errorQuestionRedoDialog.dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearnDialog$9(ErrorQuestionRedoDialog errorQuestionRedoDialog, View view) {
        BaseQuestionRemakeActivity.INSTANCE.gotToBaseQuestionRemakeActivity(this, this.identity_id, this.module_type, this.category, this.type, this.unit_id, this.primary_id);
        errorQuestionRedoDialog.dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goto2NextChapter$7(String str) {
        Intent intent = new Intent(this, (Class<?>) HomePageNewActivity.class);
        intent.setFlags(67108864);
        intent.putExtra("chapter_id", str);
        startActivity(intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideLoading$10() {
        this.loadingView.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        this.appBarLayout.setExpanded(true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(AppBarLayout appBarLayout, int i2) {
        float f2 = i2 * 1.0f;
        this.recycleview.setAlpha(1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()));
        if (1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()) != 0.0f) {
            this.openrel.setVisibility(8);
        } else if (this.openrel.getVisibility() == 8) {
            shrink(300);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$12(Message message) {
        hideLoading();
        String str = (String) message.obj;
        if (str.equals("")) {
            this.opentxt.setText("全部");
        } else {
            this.opentxt.setText(str.substring(0, str.length() - 1));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$13(final Message message) {
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.online.e0
            @Override // java.lang.Runnable
            public final void run() {
                this.f13160c.lambda$new$12(message);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$onSuccess$11(QuestionListBean.DataDTO.SearchDTO.SearchDataDTO searchDataDTO, QuestionListBean.DataDTO.SearchDTO.SearchDataDTO searchDataDTO2) {
        if (TextUtils.equals("-1", searchDataDTO.getId()) || TextUtils.equals("-1", searchDataDTO2.getId())) {
            return 1;
        }
        String id = searchDataDTO.getId();
        String id2 = searchDataDTO2.getId();
        if (id.matches(RegexPool.NUMBERS) && id2.matches(RegexPool.NUMBERS)) {
            return Integer.parseInt(id) < Integer.parseInt(id2) ? 1 : -1;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postAnswerToAliyun$6(List list, int i2, QuestionDetailBean questionDetailBean) {
        if (questionDetailBean.getId().equals(((AnswerBean) list.get(i2)).getQuestion_id())) {
            questionDetailBean.setTime_used_ms((Integer.parseInt(((AnswerBean) list.get(i2)).getDuration()) * 1000) + "");
            questionDetailBean.setIs_redo(TextUtils.isEmpty(questionDetailBean.getIs_redo()) ? "0" : questionDetailBean.getIs_redo());
            questionDetailBean.setExam_title(getIntent().getExtras().getString("title"));
            questionDetailBean.setExam_id(getIntent().getExtras().getString("exam_id"));
            questionDetailBean.setUser_answer(((AnswerBean) list.get(i2)).getAnswer());
            questionDetailBean.setIs_right(((AnswerBean) list.get(i2)).getIs_right());
            questionDetailBean.setModule_type(this.module_type);
            questionDetailBean.setUnit_title(ProjectApp.unit_title);
            questionDetailBean.setUnit_id(ProjectApp.unit_id);
            questionDetailBean.setExam_title(ProjectApp.exam_title);
            questionDetailBean.setIdentity_title(ProjectApp.identity_title);
            questionDetailBean.setIdentity_id(this.identity_id);
            questionDetailBean.setChapter_title(this.chapter_title);
            questionDetailBean.setChapter_id(this.primary_id);
            questionDetailBean.setChapter_parent_title(this.subject_title);
            questionDetailBean.setChapter_parent_id(ProjectApp.chapter_parent_id);
            String json = ProjectApp.gson.toJson(questionDetailBean);
            this.target_alias = "[\"" + questionDetailBean.getTitle() + "\"]";
            String str = "[\"" + ((AnswerBean) list.get(i2)).getQuestion_id() + "\"]";
            AliyunEvent aliyunEvent = AliyunEvent.SubmitAnswer;
            CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, this.target_alias, json, "2");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshData$14() {
        hideLoading();
        if (this.questionList_GridView.getAdapter() == null) {
            this.questionList_GridView.setAdapter(this.answerSheetAdapter);
        } else {
            this.answerSheetAdapter.notifyDataSetChanged();
        }
        updateStatisticsShow();
        this.answerSheetAdapter.setEmptyView(R.layout.adapter_empty_txt_view);
        dealNextChapter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshData$15() throws JSONException, NumberFormatException {
        mChangeData();
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.online.c0
            @Override // java.lang.Runnable
            public final void run() {
                this.f13149c.lambda$refreshData$14();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) throws JSONException {
        if (this.isFiltrate) {
            EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_HOME_PAGE_REFRESH, this.identity_id));
            if (this.isCutQuestion) {
                EventBus.getDefault().post(new RefreshCutQuestionEvent(this.identity_id));
            }
        } else {
            handleData();
        }
        if (this.answerMode.equals(Constants.ANSWER_MODE.TEST_MODE)) {
            submitAnswer("1");
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        clearnDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$submitAnswer$4(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$submitAnswer$5(CustomDialog customDialog, String str, DecimalFormat decimalFormat, double d3, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismissNoAnimaltion();
        if (str.equals("1") && this.category.equals("year") && this.type.equals("all")) {
            ProjectApp.showQuestionList = new Gson().toJson(this.showList);
            Intent intent = new Intent(this, (Class<?>) YearStatisticsActivity.class);
            intent.putExtra(UriUtil.QUERY_CATEGORY, this.category);
            intent.putExtra("module_type", this.module_type);
            intent.putExtra("unit", this.chapter_title);
            intent.putExtra("year", this.subject_title);
            intent.putExtra("identity_id", this.identity_id);
            startActivity(intent);
        } else {
            getScoreNewData(this.showList.size() + "", replacevalue((this.errorCount + this.rightCount) + ""), replacevalue(this.rightCount + ""), replacevalue(this.errorCount + ""), replacevalue(decimalFormat.format(d3)), "3".equals(str));
        }
        postAnswer(this.mArraySubAnswer.toString());
    }

    private void mChangeData() throws JSONException, NumberFormatException {
        String str = "pattern";
        try {
            ArrayList arrayList = new ArrayList();
            StringBuilder sb = new StringBuilder();
            JSONArray jSONArray = new JSONArray();
            Iterator<QuestionListBean.DataDTO.SearchDTO> it = this.search.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                if (!it.next().getSelectId().equals("-1")) {
                    i2++;
                }
            }
            for (int i3 = 0; i3 < this.search.size(); i3++) {
                try {
                    if (!TextUtils.equals(this.search.get(i3).getSeleteTitle(), "全部")) {
                        sb.append(this.search.get(i3).getSeleteTitle());
                        sb.append("-");
                    }
                    JSONObject jSONObject = new JSONObject();
                    if (this.search.get(i3).getField().equals("year")) {
                        jSONObject.put("field", this.search.get(i3).getField());
                        jSONObject.put("id", this.search.get(i3).getSelectId());
                        if (this.search.get(i3).getSelectId().equals("free_year")) {
                            jSONObject.put("free_year", this.search.get(i3).getSeleteTitle());
                        }
                        jSONArray.put(jSONObject);
                        if (!this.fromQuestionCombine) {
                            SharePreferencesUtils.writeStrConfig(CommonParameter.FILTER_YEAR_TO_QUETION_DATA + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + this.category, new Gson().toJson(this.search.get(i3).getData()), this);
                            SharePreferencesUtils.writeStrConfig(CommonParameter.FILTER_YEAR_DATA + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + this.category, this.search.get(i3).getSeleteTitle(), this);
                        }
                    } else if (this.search.get(i3).getField().equals("pattern")) {
                        jSONObject.put("field", this.search.get(i3).getField());
                        jSONObject.put("id", this.search.get(i3).getSelectId());
                        jSONArray.put(jSONObject);
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            if (!this.fromQuestionCombine) {
                if (this.isCutQuestion) {
                    SharePreferencesUtils.writeStrConfig(CommonParameter.SEARCH_CUT_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + this.category, jSONArray.toString(), this);
                } else if (this.category.equals("unit")) {
                    SharePreferencesUtils.writeStrConfig(CommonParameter.SEARCH_QUESTION_UNIT_ID, jSONArray.toString(), this);
                } else {
                    SharePreferencesUtils.writeStrConfig(CommonParameter.SEARCH_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + this.category, jSONArray.toString(), this);
                }
            }
            Iterator<QuestionDetailBean> it2 = this.questionList.iterator();
            while (it2.hasNext()) {
                QuestionDetailBean next = it2.next();
                int i4 = 0;
                for (QuestionListBean.DataDTO.SearchDTO searchDTO : this.search) {
                    String selectId = searchDTO.getSelectId();
                    Iterator<QuestionDetailBean> it3 = it2;
                    String field = searchDTO.getField();
                    if (field.equals(str)) {
                        i4++;
                    }
                    String str2 = str;
                    if (!"-1".equals(searchDTO.getSelectId())) {
                        if (field.equals("year")) {
                            String[] strArrSplit = "free_year".equals(selectId) ? searchDTO.getSeleteTitle().split("-") : selectId.split("-");
                            int i5 = Integer.parseInt(next.getYear());
                            if (i5 >= Integer.parseInt(strArrSplit[0]) && i5 <= Integer.parseInt(strArrSplit[1])) {
                                i4++;
                            }
                        } else {
                            String strOptString = new JSONObject(new Gson().toJson(next)).optString(field, "");
                            if (strOptString.contains(",")) {
                                if (Arrays.asList(strOptString.split(",")).contains(selectId)) {
                                    i4++;
                                }
                            } else if (strOptString.equals(selectId)) {
                                i4++;
                            }
                        }
                    }
                    it2 = it3;
                    str = str2;
                }
                Iterator<QuestionDetailBean> it4 = it2;
                String str3 = str;
                if (i4 == i2) {
                    arrayList.add(next);
                }
                it2 = it4;
                str = str3;
            }
            this.showList.clear();
            this.showList.addAll(arrayList);
            Message message = new Message();
            message.what = 0;
            message.obj = sb.toString();
            this.mHandler.sendMessage(message);
        } catch (Exception e3) {
            e3.printStackTrace();
            hideLoading();
        }
    }

    private void postAnswer(String answer) {
        this.answerSheetAdapter.notifyDataSetChanged();
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("answer", answer);
        ajaxParams.put("module_type", "" + this.module_type);
        ajaxParams.put("question_category_id", this.question_bank_id);
        QuestionDataRequest.getIntance(this).questionPutAnswerData(ajaxParams, this);
        postAnswerToAliyun(this.mArraySubAnswer.toString());
    }

    private void postAnswerToAliyun(String mArraySubAnswer) {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                final List list = (List) ProjectApp.gson.fromJson(mArraySubAnswer.toString(), new TypeToken<List<AnswerBean>>() { // from class: com.psychiatrygarden.activity.online.AnswerSheetActivity.4
                }.getType());
                for (final int i2 = 0; i2 < list.size(); i2++) {
                    if (!TextUtils.isEmpty(((AnswerBean) list.get(i2)).getAnswer()) && !TextUtils.isEmpty(((AnswerBean) list.get(i2)).getDuration())) {
                        this.showList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.online.h0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                this.f13418c.lambda$postAnswerToAliyun$6(list, i2, (QuestionDetailBean) obj);
                            }
                        });
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    private void postRedoValue(String isWrong) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("is_wrong", isWrong);
        ajaxParams.put("module_type", this.module_type);
        ajaxParams.put("obj_id", this.primary_id);
        ajaxParams.put("identity_id", this.identity_id);
        if (this.category.equals("unit")) {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, "unit");
        } else {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, "chapter");
        }
        if (!TextUtils.isEmpty(this.unit_id)) {
            ajaxParams.put("unit_id", this.unit_id);
        }
        if (this.type.equals("all")) {
            ajaxParams.put("type", "chapter");
        } else {
            ajaxParams.put("type", this.type);
        }
        QuestionDataRequest.getIntance(this).redoAnswer(ajaxParams, NetworkRequestsURL.questionRedo, this);
        QuestionDetailBean questionDetailBean = new QuestionDetailBean();
        questionDetailBean.setModule_type(this.module_type);
        questionDetailBean.setUnit_title(ProjectApp.unit_title);
        questionDetailBean.setUnit_id(ProjectApp.unit_id);
        questionDetailBean.setExam_title(ProjectApp.exam_title);
        questionDetailBean.setIdentity_title(ProjectApp.identity_title);
        questionDetailBean.setIdentity_id(this.identity_id);
        questionDetailBean.setChapter_title(this.chapter_title);
        questionDetailBean.setChapter_id(this.primary_id);
        questionDetailBean.setChapter_parent_title(this.subject_title);
        questionDetailBean.setChapter_parent_id(ProjectApp.chapter_parent_id);
        String json = ProjectApp.gson.toJson(questionDetailBean);
        String str = "[\"" + this.primary_id + "\"]";
        String str2 = "[\"" + this.chapter_title + "\"]";
        AliyunEvent aliyunEvent = AliyunEvent.RedoAnswer_Chapter;
        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, str2, json, "2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshData() {
        if (this.isClickNextChapter) {
            resetFilter();
        }
        new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.online.x
            @Override // java.lang.Runnable
            public final void run() throws JSONException, NumberFormatException {
                this.f13500c.lambda$refreshData$15();
            }
        }).start();
    }

    private void refreshDataByRedoQuestion(String requstUrl, boolean isLastDo) {
        String str;
        String str2;
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.LAST_DO_QUESTION + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this) + this.category, this, "");
        if (strConfig.contains("-")) {
            String[] strArrSplit = strConfig.split("-");
            str2 = strArrSplit[1];
            str = strArrSplit[2];
        } else {
            str = "";
            str2 = str;
        }
        for (int i2 = 0; i2 < this.showList.size(); i2++) {
            if (isLastDo && this.type.equals("all") && !this.fromQuestionCombine) {
                if (this.showList.get(i2).getChapter_id().equals(str2) && this.showList.get(i2).getId().equals(str)) {
                    this.showList.get(i2).setShowConunite(true);
                } else {
                    this.showList.get(i2).setShowConunite(false);
                }
                this.answerSheetAdapter.notifyItemChanged(i2, "CONTINUE_DO");
            } else {
                this.showList.get(i2).setUser_answer("");
                for (int i3 = 0; i3 < this.showList.get(i2).getOption().size(); i3++) {
                    this.showList.get(i2).getOption().get(i3).setType("0");
                }
                this.answerSheetAdapter.notifyItemChanged(i2);
            }
        }
        for (int i4 = 0; i4 < this.questionList.size(); i4++) {
            if (!isLastDo) {
                this.questionList.get(i4).setUser_answer("");
                for (int i5 = 0; i5 < this.questionList.get(i4).getOption().size(); i5++) {
                    this.questionList.get(i4).getOption().get(i5).setType("0");
                }
            } else if (this.questionList.get(i4).getChapter_id().equals(str2) && this.questionList.get(i4).getId().equals(str)) {
                this.questionList.get(i4).setShowConunite(true);
            } else {
                this.questionList.get(i4).setShowConunite(false);
            }
        }
        if (requstUrl.equals(NetworkRequestsURL.combineRedo)) {
            EventBus.getDefault().post(new RefreshHomePaperListEvent());
        } else {
            EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_HOME_PAGE_REFRESH, this.identity_id));
        }
    }

    private void resetFilter() {
        List<QuestionListBean.DataDTO.SearchDTO> list = this.search;
        if (list == null || list.isEmpty()) {
            return;
        }
        for (QuestionListBean.DataDTO.SearchDTO searchDTO : this.search) {
            String field = searchDTO.getField();
            if (!TextUtils.equals("year", field) && !TextUtils.equals("pattern", field) && !TextUtils.equals("cut_question", field)) {
                searchDTO.setSelectId("-1");
                List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO> data = searchDTO.getData();
                if (data != null && !data.isEmpty()) {
                    searchDTO.setSeleteTitle(data.get(0).getTitle());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoading() {
        LoadingPopupView loadingPopupView = this.loadingView;
        if (loadingPopupView == null || loadingPopupView.isShow()) {
            return;
        }
        this.loadingView.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showStatisticsPop() {
        QuestionStatisticsPop questionStatisticsPop = (QuestionStatisticsPop) new XPopup.Builder(this).asCustom(new QuestionStatisticsPop(this, this.mStatisticsData, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.online.y
            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
            public final void onConfirm() throws JSONException {
                this.f13505a.goto2NextChapter();
            }
        }));
        this.mStatisticsPop = questionStatisticsPop;
        questionStatisticsPop.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0266  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void submitAnswer(final java.lang.String r17) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 916
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.AnswerSheetActivity.submitAnswer(java.lang.String):void");
    }

    private void updateStatisticsShow() {
        int countByType = getCountByType(3);
        if (countByType == this.showList.size()) {
            this.tvStatistics.setText("当前共" + this.showList.size() + "题，未做" + countByType + "题");
            return;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int color = ContextCompat.getColor(this, SkinManager.getCurrentSkinType(this) == 0 ? R.color.dominant_color : R.color.dominant_color_night);
        spannableStringBuilder.append((CharSequence) "当前共").append((CharSequence) String.valueOf(this.showList.size())).append((CharSequence) "题").append((CharSequence) "，").append((CharSequence) "对").append((CharSequence) String.valueOf(getCountByType(1))).append((CharSequence) "题").append((CharSequence) "，").append((CharSequence) "错");
        int length = spannableStringBuilder.length();
        int countByType2 = getCountByType(1);
        spannableStringBuilder.append((CharSequence) String.valueOf(getCountByType(2)));
        spannableStringBuilder.append((CharSequence) "题").append((CharSequence) "，").append((CharSequence) "未做").append((CharSequence) String.valueOf(getCountByType(3))).append((CharSequence) "题").append((CharSequence) "，").append((CharSequence) "正确率");
        TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.question_color});
        spannableStringBuilder.setSpan(new ForegroundColorSpan(typedArrayObtainStyledAttributes.getColor(0, ContextCompat.getColor(this, R.color.question_color))), 0, spannableStringBuilder.length(), 18);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(color), length, String.valueOf(getCountByType(2)).length() + length, 34);
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String str = "0.0";
        if (!this.showList.isEmpty()) {
            Iterator<QuestionDetailBean> it = this.showList.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                if (!TextUtils.isEmpty(it.next().getUser_answer())) {
                    i2++;
                }
            }
            if (i2 > 0) {
                String str2 = i2 == countByType2 ? "100" : decimalFormat.format(((countByType2 * 1.0f) / i2) * 100.0d);
                if (str2.contains(StrPool.DOT) && Integer.parseInt(str2.split("\\.")[1]) == 0) {
                    str2 = str2.split("\\.")[0];
                }
                str = str2;
            }
        }
        spannableStringBuilder.append((CharSequence) str).append((CharSequence) "%").setSpan(new ForegroundColorSpan(color), spannableStringBuilder.length(), spannableStringBuilder.length(), 34);
        this.tvStatistics.setText(spannableStringBuilder);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void updateTitle() {
        ChapterTitleInfo chapterTitleInfo;
        NextChapterInfo chapterInfo = ProjectApp.getChapterInfo();
        if (chapterInfo != null) {
            ArrayMap<String, ChapterTitleInfo> titleMap = chapterInfo.getTitleMap();
            if (this.currentChapterPos >= this.chapterIds.size() || (chapterTitleInfo = titleMap.get(this.chapterIds.get(this.currentChapterPos))) == null) {
                return;
            }
            String subjectTitle = chapterTitleInfo.getSubjectTitle();
            String chapterTitle = chapterTitleInfo.getChapterTitle();
            if (!TextUtils.isEmpty(subjectTitle)) {
                setTitle(subjectTitle);
                this.subject_title = subjectTitle;
            }
            if (TextUtils.isEmpty(chapterTitle)) {
                return;
            }
            if (TextUtils.isEmpty(subjectTitle)) {
                setTitle(chapterTitle);
            }
            this.chapter_title = chapterTitle;
        }
    }

    public void clearnDialog() {
        if (this.questionList.isEmpty()) {
            return;
        }
        final ErrorQuestionRedoDialog errorQuestionRedoDialog = new ErrorQuestionRedoDialog(this.mContext, this.fromQuestionCombine);
        errorQuestionRedoDialog.setButtonLisenter(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.f0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13165c.lambda$clearnDialog$8(errorQuestionRedoDialog, view);
            }
        }, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.g0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13411c.lambda$clearnDialog$9(errorQuestionRedoDialog, view);
            }
        });
        errorQuestionRedoDialog.show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle(this.subject_title);
        this.iv_right_img.setImageResource(R.drawable.ic_sheet_redo_day);
        if (SkinManager.getCurrentSkinType(this) == 1) {
            this.iv_right_img.setImageResource(R.drawable.ic_sheet_redo_night);
        }
        this.iv_right_img.setVisibility(0);
        this.mBtnActionbarRight.setVisibility(8);
        if (TextUtils.isEmpty(this.type)) {
            this.iv_right_img.setVisibility(0);
        } else if (this.type.equals(ClientCookie.COMMENT_ATTR) || this.type.equals("praise")) {
            this.iv_right_img.setVisibility(8);
        } else {
            this.iv_right_img.setVisibility(0);
        }
        this.loadingView = new XPopup.Builder(this).isViewMode(true).asLoading("加载中", R.layout.layout_loading);
        this.openrel = (RelativeLayout) findViewById(R.id.openrel);
        this.tvStatistics = (TextView) findViewById(R.id.tv_statistics);
        this.appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        this.opentxt = (TextView) findViewById(R.id.opentxt);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        this.recycleview = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recycleview.setNestedScrollingEnabled(false);
        this.recycleview.setHasFixedSize(true);
        this.openrel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.a0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13088c.lambda$init$0(view);
            }
        });
        this.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.online.b0
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                this.f13145a.lambda$init$1(appBarLayout, i2);
            }
        });
        getAdConfig();
        this.questionList_GridView = (RecyclerView) findViewById(R.id.questionList_GridView);
        this.questionList_GridView.setLayoutManager(new GridLayoutManager(this, (UIUtil.getScreenWidth(this) - UIUtil.dip2px(this, 28.0d)) / UIUtil.dip2px(this, 58.0d)));
        AnswerSheetAdapter answerSheetAdapter = new AnswerSheetAdapter(this.showList);
        this.answerSheetAdapter = answerSheetAdapter;
        answerSheetAdapter.setOnItemClickListener(this);
        this.appBarLayout.setVisibility(8);
        if (!this.isYearStatistics) {
            if (this.fromQuestionCombine) {
                getPaperData(this.paperId);
                return;
            } else {
                getQuestionList();
                return;
            }
        }
        this.iv_right_img.setVisibility(8);
        if (!TextUtils.isEmpty(ProjectApp.showQuestionList)) {
            this.questionList = (List) new Gson().fromJson(ProjectApp.showQuestionList, new TypeToken<List<QuestionDetailBean>>() { // from class: com.psychiatrygarden.activity.online.AnswerSheetActivity.1
            }.getType());
        }
        this.showList.addAll(this.questionList);
        this.questionList_GridView.setAdapter(this.answerSheetAdapter);
        this.answerSheetAdapter.setNewInstance(this.showList);
        this.answerSheetAdapter.setEmptyView(R.layout.adapter_empty_txt_view);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void initQuestionType() {
        /*
            Method dump skipped, instructions count: 580
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.AnswerSheetActivity.initQuestionType():void");
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() throws JSONException {
        if (this.isFiltrate) {
            if (!this.fromQuestionCombine) {
                EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_HOME_PAGE_REFRESH, this.identity_id));
            }
            if (this.isCutQuestion) {
                EventBus.getDefault().post(new RefreshCutQuestionEvent(this.identity_id));
            }
        } else if (!this.fromQuestionCombine) {
            handleData();
        }
        if (this.answerMode.equals(Constants.ANSWER_MODE.TEST_MODE)) {
            submitAnswer("1");
        } else {
            finish();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        QuestionStatisticsPop questionStatisticsPop;
        super.onConfigurationChanged(newConfig);
        try {
            AnswerSheetAdapter answerSheetAdapter = this.answerSheetAdapter;
            if (answerSheetAdapter != null) {
                answerSheetAdapter.notifyDataSetChanged();
            }
            if (ScreenUtil.isTablet(this) && (questionStatisticsPop = this.mStatisticsPop) != null && questionStatisticsPop.isShow()) {
                this.mStatisticsPop.dismiss();
                this.mStatisticsPop = null;
                this.questionList_GridView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.z
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f13508c.showStatisticsPop();
                    }
                }, 500L);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacksAndMessages(null);
        ProjectApp.saveNChapterInfo(null);
        hideLoading();
    }

    @Subscribe
    public void onEventMainThread(CloseAnswerSheetEvent event) {
        finish();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        hideLoading();
        if (requstUrl.equals(NetworkRequestsURL.questionUserAnswerApi)) {
            initQuestionType();
        }
        if (requstUrl.equals(NetworkRequestsURL.questionListApi) || requstUrl.equals(NetworkRequestsURL.questionSetsList)) {
            this.hasNextChapter = false;
        }
        AlertToast("请求失败");
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        ProjectApp.showQuestionList = new Gson().toJson(this.showList);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putBoolean("hasNextChapter", this.hasNextChapter);
        bundle.putBoolean("nextChapterUnlock", this.nextChapterUnlock);
        bundle.putString("nextUnlockId", this.nextUnlockId);
        List<QuestionDetailBean> list = this.showList;
        if (list != null && list.size() > 0 && position < this.showList.size()) {
            String id = this.showList.get(position).getId();
            bundle.putString("question_id", id);
            String str = this.showList.get(position).getChapter_parent_id() + "-" + this.showList.get(position).getChapter_id() + "-" + id;
            if (this.type.equals("all") && !this.fromQuestionCombine) {
                SharePreferencesUtils.writeStrConfig(CommonParameter.LAST_DO_QUESTION + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + this.category, str, this);
            }
        }
        this.chapterParentId = this.answerSheetAdapter.getItem(position).getChapter_parent_id();
        LogUtils.e("chapter_parent_id", "parentId===>" + this.chapterParentId);
        bundle.putBoolean("fromQuestionCombine", this.fromQuestionCombine);
        bundle.putString("paperId", this.paperId);
        bundle.putBoolean("isYearStatistics", this.isYearStatistics);
        bundle.putString("module_type", this.module_type);
        bundle.putString("subject_title", this.subject_title);
        bundle.putString("chapter_title", this.chapter_title);
        bundle.putString("identity_id", this.identity_id);
        bundle.putString("chapter_id", this.primary_id);
        bundle.putString("chapter_parent_id", this.chapterParentId);
        bundle.putString("is_show_number", this.is_show_number);
        bundle.putString("type", this.type);
        bundle.putString(UriUtil.QUERY_CATEGORY, this.category);
        bundle.putString("identity_id", this.identity_id);
        bundle.putString("question_bank_id", this.question_bank_id);
        bundle.putString("unit_id", this.unit_id);
        bundle.putString("category_id", this.category_id);
        List<QuestionDetailBean> list2 = this.showList;
        bundle.putString("total", list2 == null ? "0" : String.valueOf(list2.size()));
        if (!this.isYearStatistics) {
            bundle.putString("answerMode", this.answerMode);
        }
        AnswerQuestionActivity.gotoActivity(this, bundle);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
        if (requstUrl.equals(NetworkRequestsURL.questionListApi) || requstUrl.equals(NetworkRequestsURL.questionSetsList) || requstUrl.equals(NetworkRequestsURL.paperDetail) || requstUrl.equals(NetworkRequestsURL.sheetCutList)) {
            showLoading();
        }
    }

    public String replacevalue(String s2) {
        return !TextUtils.isEmpty(s2) ? s2.indexOf(StrPool.DOT) > 0 ? s2.replaceAll("0+?$", "").replaceAll("[.]$", "") : s2 : "0";
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.DETAIL_SUBMIT_STATISTICS, false, this);
        setContentView(R.layout.activity_questionlist);
        this.answerMode = Constants.ANSWER_MODE.PRACTICE_MODE;
        Bundle bundleExtra = getIntent().getBundleExtra("bundle");
        if (bundleExtra != null) {
            NextChapterInfo chapterInfo = ProjectApp.getChapterInfo();
            if (chapterInfo != null) {
                this.chapterIds = chapterInfo.getRemainChapterIds();
                this.unlockStateArr = chapterInfo.getUnlockArr();
            } else {
                this.chapterIds = new ArrayList<>(0);
                this.unlockStateArr = new boolean[0];
            }
            this.isYearStatistics = bundleExtra.getBoolean("isYearStatistics", false);
            this.fromQuestionCombine = bundleExtra.getBoolean("fromQuestionCombine", false);
            this.primary_id = bundleExtra.getString("primary_id", "");
            this.paperId = bundleExtra.getString("paperId", "");
            this.question_bank_id = bundleExtra.getString("question_bank_id");
            this.unit = bundleExtra.getString("unit", "");
            this.unit_id = bundleExtra.getString("unit_id", "");
            this.category_id = bundleExtra.getString("category_id", "");
            this.category = bundleExtra.getString(UriUtil.QUERY_CATEGORY, "");
            this.module_type = bundleExtra.getString("module_type", "");
            this.type = bundleExtra.getString("type", "");
            this.subject_title = bundleExtra.getString("subject_title", "");
            this.chapter_title = bundleExtra.getString("chapter_title", "");
            this.is_show_number = bundleExtra.getString("is_show_number", "");
            this.identity_id = bundleExtra.getString("identity_id");
            this.isCutQuestion = bundleExtra.getBoolean("isCutQuestion", false);
            if (this.fromQuestionCombine) {
                this.type = "all";
                this.subject_title = bundleExtra.getString("title", "");
            }
            ProjectApp.chapter_title = this.chapter_title;
            ProjectApp.chapter_id = this.primary_id;
            ProjectApp.chapter_parent_title = this.subject_title;
            ProjectApp.identity_id = this.identity_id;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.v
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws JSONException {
                this.f13491c.lambda$setListenerForWidget$2(view);
            }
        });
        this.iv_right_img.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13496c.lambda$setListenerForWidget$3(view);
            }
        });
    }

    public void shrink(int duration) {
        this.openrel.setVisibility(0);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.openrel, "translationY", Utils.dp2px(this, 40.0f), 0.0f);
        objectAnimatorOfFloat.setDuration(duration);
        objectAnimatorOfFloat.setInterpolator(new OvershootInterpolator());
        objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.activity.online.AnswerSheetActivity.8
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }
        });
        objectAnimatorOfFloat.start();
    }

    @Subscribe
    public void onEventMainThread(QuestionCollectEvent event) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= this.showList.size()) {
                break;
            }
            QuestionDetailBean questionDetailBean = this.showList.get(i3);
            if (!TextUtils.equals(questionDetailBean.getId(), event.getId())) {
                i3++;
            } else if (event.isCollect()) {
                String filter_type = questionDetailBean.getFilter_type();
                if (filter_type.indexOf("2") == -1) {
                    questionDetailBean.setFilter_type(filter_type + ",2");
                }
            }
        }
        while (true) {
            if (i2 >= this.questionList.size()) {
                break;
            }
            QuestionDetailBean questionDetailBean2 = this.questionList.get(i2);
            if (!TextUtils.equals(questionDetailBean2.getId(), event.getId())) {
                i2++;
            } else if (event.isCollect()) {
                String filter_type2 = questionDetailBean2.getFilter_type();
                if (filter_type2.indexOf("2") == -1) {
                    questionDetailBean2.setFilter_type(filter_type2 + ",2");
                }
            }
        }
        refreshData();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:250:0x0794  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0797 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:262:0x07b0 A[Catch: Exception -> 0x0895, TryCatch #1 {Exception -> 0x0895, blocks: (B:76:0x0248, B:78:0x029e, B:80:0x02b6, B:82:0x02da, B:83:0x02ec, B:85:0x02f2, B:87:0x0304, B:88:0x0311, B:90:0x0339, B:92:0x0369, B:93:0x036f, B:95:0x0375, B:97:0x0387, B:99:0x038d, B:101:0x0393, B:102:0x0396, B:104:0x039a, B:106:0x03a2, B:108:0x03ac, B:110:0x03b6, B:111:0x03ba, B:113:0x03bf, B:115:0x03c7, B:117:0x03eb, B:120:0x0450, B:122:0x0458, B:127:0x0480, B:128:0x048b, B:130:0x0491, B:132:0x04a1, B:133:0x04a9, B:134:0x04b3, B:135:0x04c0, B:137:0x04c6, B:139:0x04d9, B:141:0x04e1, B:118:0x0419, B:142:0x04f3, B:144:0x04fe, B:145:0x0509, B:147:0x0511, B:149:0x0523, B:152:0x0529, B:154:0x053b, B:156:0x0541, B:157:0x0549, B:159:0x0580, B:161:0x0592, B:162:0x05c8, B:164:0x05dc, B:166:0x05e0, B:168:0x05ee, B:169:0x05f2, B:171:0x05f8, B:173:0x0608, B:174:0x060c, B:175:0x0610, B:177:0x0624, B:179:0x0628, B:181:0x0636, B:182:0x063a, B:184:0x0640, B:186:0x0650, B:187:0x0654, B:188:0x0658, B:190:0x066c, B:192:0x0670, B:194:0x067e, B:195:0x0682, B:197:0x0688, B:199:0x0698, B:200:0x069c, B:201:0x06a0, B:203:0x06b4, B:204:0x06de, B:206:0x06e4, B:210:0x06f4, B:211:0x06fa, B:213:0x070e, B:216:0x0714, B:219:0x0724, B:220:0x0728, B:222:0x072e, B:224:0x073e, B:225:0x0742, B:226:0x0746, B:230:0x0754, B:259:0x07a3, B:260:0x07a8, B:261:0x07ad, B:262:0x07b0, B:232:0x0758, B:235:0x0762, B:238:0x076c, B:241:0x0776, B:244:0x0780, B:247:0x078a, B:263:0x07b3, B:267:0x07bd, B:268:0x07c0, B:269:0x07c2, B:270:0x07c6, B:272:0x07ce, B:275:0x07ee, B:277:0x07f6, B:279:0x0800, B:281:0x0813, B:283:0x0822, B:284:0x0829, B:286:0x0831, B:287:0x0838, B:289:0x083c, B:290:0x0844, B:292:0x0851, B:280:0x080e, B:293:0x0860, B:294:0x088d), top: B:301:0x0248 }] */
    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onSuccess(java.lang.String r14, java.lang.String r15) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 2220
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.AnswerSheetActivity.onSuccess(java.lang.String, java.lang.String):void");
    }

    @Subscribe
    public void onEventMainThread(UpdateQuestionCutEvent event) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= this.showList.size()) {
                break;
            }
            QuestionDetailBean questionDetailBean = this.showList.get(i3);
            if (TextUtils.equals(questionDetailBean.getId(), event.getQuestionId())) {
                questionDetailBean.setIs_cut(event.getIsCut());
                break;
            }
            i3++;
        }
        while (true) {
            if (i2 >= this.questionList.size()) {
                break;
            }
            QuestionDetailBean questionDetailBean2 = this.questionList.get(i2);
            if (TextUtils.equals(questionDetailBean2.getId(), event.getQuestionId())) {
                questionDetailBean2.setIs_cut(event.getIsCut());
                break;
            }
            i2++;
        }
        refreshData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if ("jump2KnowledgeChapter".equals(str)) {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RedoOtherQuestionEvent event) {
        this.isRedoType = event.getIsError();
        refreshDataByRedoQuestion(NetworkRequestsURL.questionRedo, event.getIsLastDo());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMessage event) throws JSONException {
        int i2;
        String key = event.getKey();
        key.hashCode();
        i2 = 0;
        switch (key) {
            case "EVENT_QUESTION_ANSWER_OPTION":
                QuestionDetailBean questionDetailBean = (QuestionDetailBean) event.getValueObj();
                if (questionDetailBean != null) {
                    int i3 = 0;
                    while (true) {
                        if (i3 < this.showList.size()) {
                            if (questionDetailBean.getId().equals(this.showList.get(i3).getId())) {
                                this.showList.get(i3).setUser_answer(questionDetailBean.getUser_answer());
                                this.showList.get(i3).setStatData(questionDetailBean.getStatData());
                                this.showList.get(i3).setNote(questionDetailBean.getNote());
                                this.showList.get(i3).setOption(questionDetailBean.getOption());
                                this.showList.get(i3).setDoDuration(questionDetailBean.getDoDuration());
                            } else {
                                i3++;
                            }
                        }
                    }
                    while (true) {
                        if (i2 < this.questionList.size()) {
                            if (questionDetailBean.getId().equals(this.questionList.get(i2).getId())) {
                                this.questionList.get(i2).setUser_answer(questionDetailBean.getUser_answer());
                                this.questionList.get(i2).setStatData(questionDetailBean.getStatData());
                                this.questionList.get(i2).setNote(questionDetailBean.getNote());
                                this.questionList.get(i2).setOption(questionDetailBean.getOption());
                                this.questionList.get(i2).setDoDuration(questionDetailBean.getDoDuration());
                            } else {
                                i2++;
                            }
                        }
                    }
                    refreshData();
                    break;
                }
                break;
            case "EVENT_QUESTION_ANALY_REFRESH":
            case "EVENT_QUESTION_ANSWER_REFRESH":
                QuestionDetailBean questionDetailBean2 = (QuestionDetailBean) event.getValueObj();
                if (questionDetailBean2 != null) {
                    int i4 = 0;
                    while (true) {
                        if (i4 < this.showList.size()) {
                            if (questionDetailBean2.getId().equals(this.showList.get(i4).getId())) {
                                this.showList.get(i4).setUser_answer(questionDetailBean2.getUser_answer());
                                this.showList.get(i4).setStatData(questionDetailBean2.getStatData());
                                this.showList.get(i4).setNote(questionDetailBean2.getNote());
                                this.showList.get(i4).setmAnalysisBean(questionDetailBean2.getmAnalysisBean());
                                for (int i5 = 0; i5 < questionDetailBean2.getOption().size(); i5++) {
                                    this.showList.get(i4).getOption().get(i5).setType("");
                                }
                            } else {
                                i4++;
                            }
                        }
                    }
                    while (true) {
                        if (i2 < this.questionList.size()) {
                            if (questionDetailBean2.getId().equals(this.questionList.get(i2).getId())) {
                                this.questionList.get(i2).setUser_answer(questionDetailBean2.getUser_answer());
                                this.questionList.get(i2).setStatData(questionDetailBean2.getStatData());
                                this.questionList.get(i2).setNote(questionDetailBean2.getNote());
                                this.questionList.get(i2).setmAnalysisBean(questionDetailBean2.getmAnalysisBean());
                            } else {
                                i2++;
                            }
                        }
                    }
                    refreshData();
                    if (this.fromQuestionCombine) {
                        EventBus.getDefault().post(new RefreshPaperListEvent(this.paperId, getCountByType(2), getCountByType(1)));
                        break;
                    }
                }
                break;
            case "EVENT_QUESTION_ANSWER_TEST_STATISTICS":
                if ("year".equals(this.category) && this.type.equals("all")) {
                    for (int i6 = 0; i6 < this.showList.size(); i6++) {
                        try {
                            if (TextUtils.isEmpty(this.showList.get(i6).getUser_answer())) {
                                String str = "";
                                for (int i7 = 0; i7 < this.showList.get(i6).getOption().size(); i7++) {
                                    if (this.showList.get(i6).getOption().get(i7).getType().equals("1") || this.showList.get(i6).getOption().get(i7).getType().equals("2") || this.showList.get(i6).getOption().get(i7).getType().equals("3")) {
                                        str = str + this.showList.get(i6).getOption().get(i7).getKey();
                                    }
                                }
                                if (!TextUtils.isEmpty(str)) {
                                    this.showList.get(i6).setUser_answer(str);
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    for (int i8 = 0; i8 < this.questionList.size(); i8++) {
                        if (TextUtils.isEmpty(this.questionList.get(i8).getUser_answer())) {
                            String str2 = "";
                            for (int i9 = 0; i9 < this.questionList.get(i8).getOption().size(); i9++) {
                                if (this.questionList.get(i8).getOption().get(i9).getType().equals("1") || this.questionList.get(i8).getOption().get(i9).getType().equals("2") || this.questionList.get(i8).getOption().get(i9).getType().equals("3")) {
                                    str2 = str2 + this.questionList.get(i8).getOption().get(i9).getKey();
                                }
                            }
                            if (!TextUtils.isEmpty(str2)) {
                                this.questionList.get(i8).setUser_answer(str2);
                            }
                        }
                    }
                    this.answerSheetAdapter.notifyDataSetChanged();
                    break;
                } else {
                    submitAnswer("3");
                    break;
                }
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(GetNextChapterDataEvent event) throws JSONException {
        this.isNextChapter = true;
        goto2NextChapter();
    }
}
