package com.psychiatrygarden.fragmenthome;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.aliyun.vod.common.utils.UriUtil;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.exoplayer2.C;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.huawei.hms.push.HmsMessageService;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.mine.knowledge.KnowledgePointStatisticsAct;
import com.psychiatrygarden.activity.online.AnswerQuestionActivity;
import com.psychiatrygarden.activity.online.ChartAnswerSheetActivity;
import com.psychiatrygarden.activity.online.adapter.AnswerSheetAdapter;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.online.bean.QuestionListBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.NodeRefreshEvent;
import com.psychiatrygarden.bean.StatisticsData;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.CloseAnswerSheetEvent;
import com.psychiatrygarden.event.DailyTaskShowStatisticsEvent;
import com.psychiatrygarden.event.NextNodeEvent;
import com.psychiatrygarden.event.QuestionCollectEvent;
import com.psychiatrygarden.event.RedoMultiKnowledgeEvent;
import com.psychiatrygarden.event.RedoOtherQuestionEvent;
import com.psychiatrygarden.event.RefreshHomePaperListEvent;
import com.psychiatrygarden.event.RefreshLastDoEvent;
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
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.KnowledgeQuestionRedoDialog;
import com.psychiatrygarden.widget.QuestionStatisticsPop;
import com.psychiatrygarden.widget.english.PopQuestionYearFilter;
import com.psychiatrygarden.widget.glideUtil.util.Utils;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class AnswerSheetFragment extends BaseFragment implements QuestionDataCallBack<String>, OnItemClickListener, View.OnClickListener {
    private String answerMode;
    private AnswerSheetAdapter answerSheetAdapter;
    private AppBarLayout appBarLayout;
    private boolean bottom_statistics;
    private String category;
    private String category_id;
    private String chapter_title;
    private String currentId;
    private String day;
    private CustomEmptyView empty_view;
    private String identity_id;
    private boolean isDailyTaskPage;
    private boolean isKnowledge;
    private boolean isNextNode;
    private boolean isNode;
    private boolean isVisible;
    private String is_show_number;
    private String knowledgeId;
    private LoadingPopupView loadingView;
    private StatisticsData mStatisticsData;
    private String module_type;
    private String nodeId;
    private String nodeTitle;
    private String node_parent_id;
    private RelativeLayout openrel;
    private TextView opentxt;
    private String paperId;
    private String plan_id;
    private String primary_id;
    private RecyclerView questionList_GridView;
    private String question_bank_id;
    private RecyclerView recycleview;
    private boolean refresh;
    private List<QuestionListBean.DataDTO.SearchDTO> search;
    private boolean showStatistics;
    private String statisticsId;
    private String subject_title;
    private boolean testSubmitAnswer;
    private TextView tvStatistics;
    private String type;
    private String unit;
    private String unit_id;
    private List<QuestionDetailBean> questionList = new ArrayList();
    private final List<QuestionDetailBean> showList = new ArrayList();
    private ArrayMap<String, List<String>> defaultFilterMap = new ArrayMap<>();
    private double errorCount = 0.0d;
    private double rightCount = 0.0d;
    private int orignalSize = 0;
    private boolean switchModeSubmit = false;
    private JSONArray mArraySubAnswer = new JSONArray();
    private List<String> nodeIdList = new ArrayList();
    private List<String> nodeIdTitleList = new ArrayList();
    private Map<String, String> filterMap = new ArrayMap();
    private boolean redoMultiKnowledge = false;
    private String isRedoType = "";

    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler = new Handler(new Handler.Callback() { // from class: com.psychiatrygarden.fragmenthome.p
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            return this.f15901c.lambda$new$8(message);
        }
    });

    private String calculateRightPercent(List<QuestionDetailBean> list) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String str = "0.0";
        if (!this.showList.isEmpty()) {
            int countByType = getCountByType(1);
            Iterator<QuestionDetailBean> it = list.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                if (!TextUtils.isEmpty(it.next().getUser_answer())) {
                    i2++;
                }
            }
            if (i2 > 0) {
                String str2 = i2 == countByType ? "100" : decimalFormat.format(((countByType * 1.0f) / i2) * 100.0d);
                if (str2.contains(StrPool.DOT) && Integer.parseInt(str2.split("\\.")[1]) == 0) {
                    str2 = str2.split("\\.")[0];
                }
                str = str2;
            }
        }
        return str + "%";
    }

    private void getAdConfig() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.QUESTION_AD_CONFIG, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.AnswerSheetFragment.4
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
                super.onSuccess((AnonymousClass4) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (TextUtils.equals("200", jSONObject.optString("code", "0"))) {
                        JSONArray jSONArray = jSONObject.getJSONArray("data");
                        if (jSONArray.length() > 0) {
                            int length = jSONArray.length();
                            for (int i2 = 0; i2 < length; i2++) {
                                JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                                if (CommonParameter.AD_CONFIG.equals(jSONObject2.optString("key", ""))) {
                                    SharePreferencesUtils.writeStrConfig(CommonParameter.AD_CONFIG, jSONObject2.optString("value", ""), ((BaseFragment) AnswerSheetFragment.this).mContext);
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
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.paperDetailNew, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.AnswerSheetFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    AnswerSheetFragment.this.onSuccess(s2, NetworkRequestsURL.paperDetailNew);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getPingyuData(String zhengquelvValue, final boolean detail) {
        AjaxParams ajaxParams = new AjaxParams();
        this.mStatisticsData.setShowNextChapter(false);
        ajaxParams.put("scoring_rate", zhengquelvValue);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mremarkUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.AnswerSheetFragment.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                AnswerSheetFragment.this.showStatisticsPop();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    JSONObject jSONObjectOptJSONObject = new JSONObject(s2).optJSONObject("data");
                    if (jSONObjectOptJSONObject != null) {
                        AnswerSheetFragment.this.mStatisticsData.setDescrible(jSONObjectOptJSONObject.optString("remark"));
                    }
                    if ((!Objects.equals(AnswerSheetFragment.this.answerMode, Constants.ANSWER_MODE.TEST_MODE) || detail) && !AnswerSheetFragment.this.testSubmitAnswer) {
                        AnswerSheetFragment.this.showStatisticsPop();
                        return;
                    }
                    AnswerSheetFragment.this.mStatisticsData.setShowNextChapter(false);
                    EventBus.getDefault().post(!AnswerSheetFragment.this.isDailyTaskPage ? new ShowStatisticsEvent(AnswerSheetFragment.this.mStatisticsData) : new DailyTaskShowStatisticsEvent(AnswerSheetFragment.this.mStatisticsData));
                    AnswerSheetFragment.this.getActivity().finish();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (Objects.equals(AnswerSheetFragment.this.answerMode, Constants.ANSWER_MODE.TEST_MODE)) {
                        return;
                    }
                    AnswerSheetFragment.this.showStatisticsPop();
                }
            }
        });
    }

    private void getScoreNewData(String totalValue, String zuoguo, String rightValue, String wrongValue, String zhengquelvValue, boolean detail) {
        this.mStatisticsData = new StatisticsData(this.subject_title, this.chapter_title, Integer.parseInt(totalValue), Integer.parseInt(zuoguo), Integer.parseInt(rightValue), Integer.parseInt(wrongValue), "", false, false, zhengquelvValue);
        getPingyuData(zhengquelvValue, detail);
    }

    public static void gotoActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, (Class<?>) AnswerSheetFragment.class);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    private void hideLoading() {
        LoadingPopupView loadingPopupView = this.loadingView;
        if (loadingPopupView == null || !loadingPopupView.isShow()) {
            return;
        }
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.r
            @Override // java.lang.Runnable
            public final void run() {
                this.f15952c.lambda$hideLoading$7();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideLoading$7() {
        this.loadingView.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initViews$0(String str) {
        return TextUtils.equals(str, this.nodeId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        this.appBarLayout.setExpanded(true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(AppBarLayout appBarLayout, int i2) {
        float f2 = i2 * 1.0f;
        this.recycleview.setAlpha(1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()));
        if (1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()) != 0.0f) {
            this.openrel.setVisibility(8);
        } else if (this.openrel.getVisibility() == 8) {
            shrink(300);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$8(Message message) {
        hideLoading();
        Object obj = message.obj;
        if (obj != null && this.isVisible && (obj instanceof String)) {
            String str = (String) obj;
            if (str.isEmpty()) {
                this.opentxt.setText("全部");
            } else {
                this.opentxt.setText(str.substring(0, str.length() - 1));
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshData$10() throws NumberFormatException {
        if (this.mContext == null) {
            return;
        }
        mChangeData();
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.j
            @Override // java.lang.Runnable
            public final void run() {
                this.f15677c.lambda$refreshData$9();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshData$9() {
        hideLoading();
        if (this.questionList_GridView.getAdapter() == null) {
            this.questionList_GridView.setAdapter(this.answerSheetAdapter);
        } else {
            this.answerSheetAdapter.notifyDataSetChanged();
        }
        updateStatisticsShow();
        if (this.answerSheetAdapter.hasEmptyView() || this.mContext == null) {
            return;
        }
        CustomEmptyView customEmptyView = new CustomEmptyView(this.mContext, 0, (String) null);
        customEmptyView.stopAnim();
        this.answerSheetAdapter.setEmptyView(customEmptyView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRedoDialog$6(KnowledgeQuestionRedoDialog knowledgeQuestionRedoDialog, View view) {
        this.isRedoType = "all";
        if (!TextUtils.isEmpty(this.paperId)) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put("paper_id", this.paperId);
            ajaxParams.put("is_wrong", "0");
            QuestionDataRequest.getIntance(this.mContext).redoAnswer(ajaxParams, NetworkRequestsURL.combineRedo, this);
        } else if (!this.isDailyTaskPage) {
            String str = this.knowledgeId;
            int i2 = str != null ? 1 : 2;
            if (str == null) {
                str = this.nodeId;
            }
            postRedoValue(i2, str);
        } else if (!TextUtils.isEmpty(this.nodeId) && !TextUtils.isEmpty(this.day)) {
            AjaxParams ajaxParams2 = new AjaxParams();
            ajaxParams2.put("day", this.day);
            ajaxParams2.put("node_id", this.nodeId);
            YJYHttpUtils.post(this.mContext, NetworkRequestsURL.redoStudyPlanQuestion, ajaxParams2, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.AnswerSheetFragment.7
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    AnswerSheetFragment.this.onFailure(t2, errorNo, strMsg, NetworkRequestsURL.redoStudyPlanQuestion);
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) throws JSONException {
                    super.onSuccess((AnonymousClass7) s2);
                    AnswerSheetFragment.this.onSuccess(s2, NetworkRequestsURL.redoStudyPlanQuestion);
                }
            });
        }
        knowledgeQuestionRedoDialog.dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$submitAnswer$3() {
        this.showStatistics = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$submitAnswer$4(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.refresh = true;
        this.testSubmitAnswer = true;
        postAnswer(this.mArraySubAnswer.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$submitAnswer$5(View view) {
        getActivity().finish();
    }

    private void loadStudyPlanQuestionList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("day", this.day);
        ajaxParams.put("node_id", this.nodeId);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.studyPlanQuestionList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.AnswerSheetFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws JSONException {
                super.onSuccess((AnonymousClass1) s2);
                AnswerSheetFragment.this.onSuccess(s2, NetworkRequestsURL.studyPlanQuestionList);
            }
        });
    }

    private void mChangeData() throws NumberFormatException {
        try {
            ArrayList arrayList = new ArrayList();
            StringBuilder sb = new StringBuilder();
            Iterator<QuestionListBean.DataDTO.SearchDTO> it = this.search.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                if (!it.next().getSelectId().equals("-1")) {
                    i2++;
                }
            }
            for (QuestionDetailBean questionDetailBean : this.questionList) {
                int i3 = 0;
                for (QuestionListBean.DataDTO.SearchDTO searchDTO : this.search) {
                    String selectId = searchDTO.getSelectId();
                    String field = searchDTO.getField();
                    if (field.equals("pattern")) {
                        i3++;
                    }
                    if (!"-1".equals(searchDTO.getSelectId())) {
                        if (field.equals("year")) {
                            String[] strArrSplit = "free_year".equals(selectId) ? searchDTO.getSeleteTitle().split("-") : selectId.split("-");
                            int i4 = Integer.parseInt(questionDetailBean.getYear());
                            if (i4 >= Integer.parseInt(strArrSplit[0]) && i4 <= Integer.parseInt(strArrSplit[1])) {
                                i3++;
                            }
                        } else {
                            String strOptString = new JSONObject(new Gson().toJson(questionDetailBean)).optString(field, "");
                            if (strOptString.contains(",")) {
                                if (Arrays.asList(strOptString.split(",")).contains(selectId)) {
                                    i3++;
                                }
                            } else if (strOptString.equals(selectId)) {
                                i3++;
                            }
                        }
                    }
                }
                if (i3 == i2) {
                    arrayList.add(questionDetailBean);
                }
            }
            for (int i5 = 0; i5 < this.search.size(); i5++) {
                if (!TextUtils.equals(this.search.get(i5).getSeleteTitle(), "全部")) {
                    sb.append(this.search.get(i5).getSeleteTitle());
                    sb.append("-");
                }
            }
            this.showList.clear();
            this.showList.addAll(arrayList);
            if (sb.length() > 0) {
                Message message = new Message();
                message.what = 1;
                message.obj = sb.toString();
                this.mHandler.sendMessage(message);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            hideLoading();
        }
    }

    private void postAnswer(String answer) {
        if (TextUtils.isEmpty(answer)) {
            return;
        }
        this.answerSheetAdapter.notifyDataSetChanged();
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("answer", answer);
        ajaxParams.put("question_category_id", this.question_bank_id);
        ajaxParams.put("module_type", "110");
        QuestionDataRequest.getIntance(this.mContext).questionPutAnswerData(ajaxParams, this);
    }

    private void postRedoValue(int type, String id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("is_wrong", "0");
        ajaxParams.put("module_type", "110");
        if (type == 2) {
            ajaxParams.put("node_id", this.nodeId);
        }
        if (type == 1) {
            if (!TextUtils.isEmpty(id)) {
                ajaxParams.put("obj_id", id);
            } else if (!TextUtils.isEmpty(this.knowledgeId)) {
                ajaxParams.put("obj_id", this.knowledgeId);
            }
        }
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
            ajaxParams.put("type", AliyunLogCommon.SubModule.CUT.equals(this.type) ? "chapter" : this.type);
        }
        QuestionDataRequest.getIntance(this.mContext).redoAnswer(ajaxParams, NetworkRequestsURL.questionRedo, this);
        QuestionDetailBean questionDetailBean = new QuestionDetailBean();
        questionDetailBean.setModule_type(this.module_type);
        questionDetailBean.setUnit_title(ProjectApp.unit_title);
        questionDetailBean.setExam_title(ProjectApp.exam_title);
        questionDetailBean.setIdentity_title(ProjectApp.identity_title);
        questionDetailBean.setIdentity_id(this.identity_id);
        questionDetailBean.setChapter_title(this.chapter_title);
        questionDetailBean.setChapter_id(this.primary_id);
        questionDetailBean.setChapter_parent_title(this.subject_title);
        questionDetailBean.setChapter_parent_id(SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext));
        questionDetailBean.setKnowledge_id(this.knowledgeId);
        String json = ProjectApp.gson.toJson(questionDetailBean);
        String str = "[\"" + ((TextUtils.isEmpty(this.knowledgeId) || type != 1) ? "" : this.knowledgeId) + "\"]";
        String str2 = "[\"" + this.subject_title + "\"]";
        AliyunEvent aliyunEvent = AliyunEvent.RedoAnswer_Chapter;
        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, str2, json, "2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshData() {
        new Thread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.q
            @Override // java.lang.Runnable
            public final void run() throws NumberFormatException {
                this.f15926c.lambda$refreshData$10();
            }
        }).start();
    }

    private void refreshDataByRedoQuestion(String requstUrl, boolean isLastDo) {
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.LAST_DO_QUESTION_KNOWLEDGE_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext), this.mContext, "");
        String str = (strConfig.contains("-") && strConfig.split("-").length == 2) ? strConfig.split("-")[1] : "";
        for (int i2 = 0; i2 < this.showList.size(); i2++) {
            if (isLastDo && this.type.equals("all")) {
                this.showList.get(i2).setShowConunite(this.showList.get(i2).getId().equals(str));
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
            } else if (this.questionList.get(i4).getId().equals(str)) {
                this.questionList.get(i4).setShowConunite(true);
            } else {
                this.questionList.get(i4).setShowConunite(false);
            }
        }
        if (requstUrl.equals(NetworkRequestsURL.combineRedo)) {
            EventBus.getDefault().post(new RefreshHomePaperListEvent());
        } else {
            EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_HOME_PAGE_REFRESH, this.identity_id));
            EventBus.getDefault().post(new NodeRefreshEvent());
        }
    }

    private String replacevalue(String s2) {
        return !TextUtils.isEmpty(s2) ? s2.indexOf(StrPool.DOT) > 0 ? s2.replaceAll("0+?$", "").replaceAll("[.]$", "") : s2 : "0";
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
        ((QuestionStatisticsPop) new XPopup.Builder(this.mContext).asCustom(new QuestionStatisticsPop(this.mContext, this.mStatisticsData, new OnConfirmListener() { // from class: com.psychiatrygarden.fragmenthome.AnswerSheetFragment.6
            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
            public void onConfirm() {
            }
        }))).show();
    }

    private void updateStatisticsShow() {
        if (this.mContext == null) {
            return;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int countByType = getCountByType(3);
        if (countByType == this.showList.size()) {
            this.tvStatistics.setText("当前共" + this.showList.size() + "题，未做" + countByType + "题");
            return;
        }
        Context context = this.mContext;
        int color = ContextCompat.getColor(context, SkinManager.getCurrentSkinType(context) == 0 ? R.color.dominant_color : R.color.dominant_color_night);
        spannableStringBuilder.append((CharSequence) "当前共").append((CharSequence) String.valueOf(this.showList.size())).append((CharSequence) "题").append((CharSequence) "，");
        spannableStringBuilder.append((CharSequence) "对").append((CharSequence) String.valueOf(getCountByType(1))).append((CharSequence) "题").append((CharSequence) "，").append((CharSequence) "错");
        int length = spannableStringBuilder.length();
        spannableStringBuilder.append((CharSequence) String.valueOf(getCountByType(2)));
        spannableStringBuilder.append((CharSequence) "题").append((CharSequence) "，").append((CharSequence) "未做").append((CharSequence) String.valueOf(getCountByType(3))).append((CharSequence) "题").append((CharSequence) "，").append((CharSequence) "正确率");
        TypedArray typedArrayObtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.first_txt_color});
        spannableStringBuilder.setSpan(new ForegroundColorSpan(typedArrayObtainStyledAttributes.getColor(0, ContextCompat.getColor(this.mContext, R.color.first_txt_color))), 0, spannableStringBuilder.length(), 18);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(color), length, String.valueOf(getCountByType(2)).length() + length, 34);
        spannableStringBuilder.append((CharSequence) calculateRightPercent(this.showList)).setSpan(new ForegroundColorSpan(color), spannableStringBuilder.length(), spannableStringBuilder.length(), 34);
        this.tvStatistics.setText(spannableStringBuilder);
        typedArrayObtainStyledAttributes.recycle();
    }

    public String getAnswerMode() {
        return this.answerMode;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fmt_answer_sheet;
    }

    public void gotoStatistics() {
        String str = this.statisticsId;
        if (str != null) {
            KnowledgePointStatisticsAct.newIntent(this.mContext, str, getArguments().getString("desc"), this.question_bank_id);
        }
    }

    public void initQuestionType() {
        List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO> data;
        List<QuestionListBean.DataDTO.SearchDTO> list = this.search;
        if (list == null || list.isEmpty()) {
            hideLoading();
            getViewHolder().get(R.id.rl_content).setVisibility(8);
            this.questionList_GridView.setAdapter(this.answerSheetAdapter);
            this.answerSheetAdapter.setNewInstance(this.showList);
            if (this.answerSheetAdapter.hasEmptyView()) {
                return;
            }
            CustomEmptyView customEmptyView = new CustomEmptyView(this.mContext, 0, (String) null);
            customEmptyView.stopAnim();
            this.answerSheetAdapter.setEmptyView(customEmptyView);
            return;
        }
        this.appBarLayout.setVisibility(0);
        if (!this.filterMap.isEmpty()) {
            for (QuestionListBean.DataDTO.SearchDTO searchDTO : this.search) {
                for (Map.Entry<String, String> entry : this.filterMap.entrySet()) {
                    if (TextUtils.equals(entry.getKey(), searchDTO.getField()) && (data = searchDTO.getData()) != null) {
                        for (QuestionListBean.DataDTO.SearchDTO.SearchDataDTO searchDataDTO : data) {
                            searchDataDTO.setIsSelected(TextUtils.equals(searchDataDTO.getId(), entry.getKey()) ? 1 : 0);
                        }
                    }
                }
            }
            this.filterMap.clear();
        }
        refreshData();
        this.recycleview.setAdapter(new BaseQuickAdapter<QuestionListBean.DataDTO.SearchDTO, BaseViewHolder>(R.layout.activity_list_type_item, this.search) { // from class: com.psychiatrygarden.fragmenthome.AnswerSheetFragment.9

            /* renamed from: com.psychiatrygarden.fragmenthome.AnswerSheetFragment$9$1, reason: invalid class name */
            public class AnonymousClass1 extends BaseQuickAdapter<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO, BaseViewHolder> {
                final /* synthetic */ BaseViewHolder val$helpers;
                final /* synthetic */ QuestionListBean.DataDTO.SearchDTO val$items;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(int layoutResId, List data, final QuestionListBean.DataDTO.SearchDTO val$items, final BaseViewHolder val$helpers) {
                    super(layoutResId, data);
                    this.val$items = val$items;
                    this.val$helpers = val$helpers;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$0(QuestionListBean.DataDTO.SearchDTO searchDTO, BaseViewHolder baseViewHolder, BaseViewHolder baseViewHolder2, QuestionListBean.DataDTO.SearchDTO.SearchDataDTO searchDataDTO) {
                    for (int i2 = 0; i2 < searchDTO.getData().size(); i2++) {
                        searchDTO.getData().get(i2).setIsSelected(0);
                    }
                    int bindingAdapterPosition = baseViewHolder.getBindingAdapterPosition();
                    if (bindingAdapterPosition >= 0) {
                        searchDTO.getData().get(bindingAdapterPosition).setIsSelected(1);
                        searchDTO.getData().get(bindingAdapterPosition).setYearTitle(searchDataDTO.getYearTitle());
                    }
                    int bindingAdapterPosition2 = baseViewHolder2.getBindingAdapterPosition();
                    if (bindingAdapterPosition2 >= 0) {
                        ((QuestionListBean.DataDTO.SearchDTO) AnswerSheetFragment.this.search.get(bindingAdapterPosition2)).setSelectId(searchDataDTO.getId());
                        ((QuestionListBean.DataDTO.SearchDTO) AnswerSheetFragment.this.search.get(bindingAdapterPosition2)).setSeleteTitle(searchDataDTO.getYearTitle());
                    }
                    notifyDataSetChanged();
                    AnswerSheetFragment.this.showLoading();
                    AnswerSheetFragment.this.refreshData();
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$1(final BaseViewHolder baseViewHolder, final QuestionListBean.DataDTO.SearchDTO searchDTO, QuestionListBean.DataDTO.SearchDTO.SearchDataDTO searchDataDTO, final BaseViewHolder baseViewHolder2, View view) throws JSONException {
                    try {
                        if (baseViewHolder.getBindingAdapterPosition() != -1 && searchDTO.getData().size() > baseViewHolder.getBindingAdapterPosition()) {
                            if (searchDataDTO.getId().equals("free_year")) {
                                new XPopup.Builder(((BaseFragment) AnswerSheetFragment.this).mContext).autoDismiss(Boolean.FALSE).asCustom(new PopQuestionYearFilter(((BaseFragment) AnswerSheetFragment.this).mContext, searchDataDTO, new PopQuestionYearFilter.OnClickBtnListener() { // from class: com.psychiatrygarden.fragmenthome.t
                                    @Override // com.psychiatrygarden.widget.english.PopQuestionYearFilter.OnClickBtnListener
                                    public final void onChooseYear(QuestionListBean.DataDTO.SearchDTO.SearchDataDTO searchDataDTO2) {
                                        this.f15998a.lambda$convert$0(searchDTO, baseViewHolder, baseViewHolder2, searchDataDTO2);
                                    }
                                })).show();
                                return;
                            }
                            if (searchDTO.getData().get(baseViewHolder.getBindingAdapterPosition()).getIsSelected() == 1) {
                                ToastUtil.shortToast(((BaseFragment) AnswerSheetFragment.this).mContext, "已经选中");
                                return;
                            }
                            for (int i2 = 0; i2 < searchDTO.getData().size(); i2++) {
                                searchDTO.getData().get(i2).setIsSelected(0);
                                searchDTO.getData().get(i2).setYearTitle("");
                            }
                            int bindingAdapterPosition = baseViewHolder.getBindingAdapterPosition();
                            if (bindingAdapterPosition >= 0) {
                                searchDTO.getData().get(bindingAdapterPosition).setIsSelected(1);
                                ((QuestionListBean.DataDTO.SearchDTO) AnswerSheetFragment.this.search.get(baseViewHolder2.getBindingAdapterPosition())).setSelectId(searchDataDTO.getId());
                                ((QuestionListBean.DataDTO.SearchDTO) AnswerSheetFragment.this.search.get(baseViewHolder2.getBindingAdapterPosition())).setSeleteTitle(searchDataDTO.getTitle());
                            }
                            notifyDataSetChanged();
                            if (!"pattern".equals(searchDTO.getField())) {
                                AnswerSheetFragment.this.refreshData();
                                return;
                            }
                            if (AnswerSheetFragment.this.questionList != null && !AnswerSheetFragment.this.questionList.isEmpty()) {
                                if ("111".equals(searchDataDTO.getId()) || "1".equals(searchDataDTO.getId())) {
                                    AnswerSheetFragment.this.answerSheetAdapter.setTestMode(false);
                                    AnswerSheetFragment.this.switchModeSubmit = true;
                                    AnswerSheetFragment.this.submitAnswer("2");
                                    AnswerSheetFragment.this.answerMode = Constants.ANSWER_MODE.PRACTICE_MODE;
                                } else if ("112".equals(searchDataDTO.getId()) || "2".equals(searchDataDTO.getId())) {
                                    AnswerSheetFragment.this.answerMode = Constants.ANSWER_MODE.TEST_MODE;
                                } else if ("113".equals(searchDataDTO.getId())) {
                                    AnswerSheetFragment.this.switchModeSubmit = true;
                                    AnswerSheetFragment.this.submitAnswer("2");
                                    AnswerSheetFragment.this.answerMode = Constants.ANSWER_MODE.RECITE_MODE;
                                } else if ("114".equals(searchDataDTO.getId())) {
                                    AnswerSheetFragment.this.switchModeSubmit = true;
                                    AnswerSheetFragment.this.submitAnswer("2");
                                    AnswerSheetFragment.this.answerMode = Constants.ANSWER_MODE.QUICK_BRUSH_MODE;
                                }
                                StringBuilder sb = new StringBuilder();
                                JSONArray jSONArray = new JSONArray();
                                for (int i3 = 0; i3 < AnswerSheetFragment.this.search.size(); i3++) {
                                    JSONObject jSONObject = new JSONObject();
                                    try {
                                        if (((QuestionListBean.DataDTO.SearchDTO) AnswerSheetFragment.this.search.get(i3)).getField().equals("year")) {
                                            jSONObject.put("field", ((QuestionListBean.DataDTO.SearchDTO) AnswerSheetFragment.this.search.get(i3)).getField());
                                            jSONObject.put("id", ((QuestionListBean.DataDTO.SearchDTO) AnswerSheetFragment.this.search.get(i3)).getSelectId());
                                            if (((QuestionListBean.DataDTO.SearchDTO) AnswerSheetFragment.this.search.get(i3)).getSelectId().equals("free_year")) {
                                                jSONObject.put("free_year", ((QuestionListBean.DataDTO.SearchDTO) AnswerSheetFragment.this.search.get(i3)).getSeleteTitle());
                                            }
                                            jSONArray.put(jSONObject);
                                        } else if (((QuestionListBean.DataDTO.SearchDTO) AnswerSheetFragment.this.search.get(i3)).getField().equals("pattern")) {
                                            jSONObject.put("field", ((QuestionListBean.DataDTO.SearchDTO) AnswerSheetFragment.this.search.get(i3)).getField());
                                            jSONObject.put("id", ((QuestionListBean.DataDTO.SearchDTO) AnswerSheetFragment.this.search.get(i3)).getSelectId());
                                            jSONArray.put(jSONObject);
                                        }
                                    } catch (JSONException e2) {
                                        e2.printStackTrace();
                                    }
                                    if (!TextUtils.equals(((QuestionListBean.DataDTO.SearchDTO) AnswerSheetFragment.this.search.get(i3)).getSeleteTitle(), "全部")) {
                                        sb.append(((QuestionListBean.DataDTO.SearchDTO) AnswerSheetFragment.this.search.get(i3)).getSeleteTitle());
                                        sb.append("-");
                                    }
                                }
                                if (AnswerSheetFragment.this.category.equals("unit")) {
                                    SharePreferencesUtils.writeStrConfig(CommonParameter.SEARCH_QUESTION_UNIT_ID, jSONArray.toString(), ((BaseFragment) AnswerSheetFragment.this).mContext);
                                } else {
                                    SharePreferencesUtils.writeStrConfig(CommonParameter.SEARCH_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ((BaseFragment) AnswerSheetFragment.this).mContext) + AnswerSheetFragment.this.category, jSONArray.toString(), ((BaseFragment) AnswerSheetFragment.this).mContext);
                                }
                                Message message = new Message();
                                message.what = 2;
                                if (sb.length() > 0) {
                                    message.obj = sb.toString();
                                    AnswerSheetFragment.this.mHandler.sendMessage(message);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        ToastUtil.shortToast(((BaseFragment) AnswerSheetFragment.this).mContext, "下标异常");
                    } catch (Exception unused) {
                        ToastUtil.shortToast(((BaseFragment) AnswerSheetFragment.this).mContext, "操作异常，请重新选择！");
                    }
                }

                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(@NonNull final BaseViewHolder helper, final QuestionListBean.DataDTO.SearchDTO.SearchDataDTO item) {
                    TextView textView = (TextView) helper.getView(R.id.labeltext);
                    if (!item.getId().equals("free_year") || TextUtils.isEmpty(item.getYearTitle())) {
                        textView.setText(item.getTitle());
                    } else {
                        textView.setText(item.getYearTitle());
                    }
                    textView.setSelected(item.getIsSelected() == 1);
                    final QuestionListBean.DataDTO.SearchDTO searchDTO = this.val$items;
                    final BaseViewHolder baseViewHolder = this.val$helpers;
                    textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.u
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) throws JSONException {
                            this.f16023c.lambda$convert$1(helper, searchDTO, item, baseViewHolder, view);
                        }
                    });
                }
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helpers, QuestionListBean.DataDTO.SearchDTO items) {
                helpers.setText(R.id.labeid, items.getType_str());
                RecyclerView recyclerView = (RecyclerView) helpers.getView(R.id.recychildview);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(((BaseFragment) AnswerSheetFragment.this).mContext);
                int i2 = 0;
                linearLayoutManager.setOrientation(0);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(new AnonymousClass1(R.layout.layout_question_type_item, items.getData(), items, helpers));
                int i3 = 0;
                while (true) {
                    if (i3 >= items.getData().size()) {
                        break;
                    }
                    if (items.getData().get(i3).getIsSelected() == 1) {
                        i2 = i3;
                        break;
                    }
                    i3++;
                }
                recyclerView.smoothScrollToPosition(i2);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) throws JSONException {
        this.answerMode = Constants.ANSWER_MODE.PRACTICE_MODE;
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.node_parent_id = arguments.getString("node_parent_id");
            this.question_bank_id = arguments.getString("question_bank_id");
            this.bottom_statistics = arguments.getBoolean("bottom_statistics", false);
            this.nodeTitle = arguments.getString("node_title");
            this.day = arguments.getString("day");
            this.primary_id = arguments.getString("primary_id", "");
            boolean z2 = arguments.getBoolean("isDailyTaskPage", false);
            this.isDailyTaskPage = z2;
            if (z2) {
                this.bottom_statistics = true;
            }
            this.unit = arguments.getString("unit", "");
            this.unit_id = arguments.getString("unit_id", "");
            this.category_id = arguments.getString("category_id", "");
            this.category = arguments.getString(UriUtil.QUERY_CATEGORY, "");
            this.module_type = arguments.getString("module_type", "");
            this.type = arguments.getString("type", "");
            this.subject_title = arguments.getString("subject_title", "");
            this.chapter_title = arguments.getString("chapter_title", "");
            this.is_show_number = arguments.getString("is_show_number", "");
            this.identity_id = arguments.getString("identity_id");
            this.nodeId = arguments.getString("node_id");
            this.paperId = arguments.getString("paperId");
            this.knowledgeId = arguments.getString("knowledge_id");
            this.isNode = arguments.getBoolean("isNode", false);
            this.isKnowledge = arguments.getBoolean("isKnowledge", this.isKnowledge);
            ArrayList<String> stringArrayList = arguments.getStringArrayList("nodeIdList");
            ArrayList<String> stringArrayList2 = arguments.getStringArrayList("nodeIdTitleList");
            if (stringArrayList != null && !stringArrayList.isEmpty()) {
                this.nodeIdList.addAll(stringArrayList);
                if (Build.VERSION.SDK_INT >= 24) {
                    this.nodeIdList.removeIf(new Predicate() { // from class: com.psychiatrygarden.fragmenthome.m
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return this.f15818a.lambda$initViews$0((String) obj);
                        }
                    });
                }
            }
            if (stringArrayList2 != null && !stringArrayList2.isEmpty()) {
                this.nodeIdTitleList.addAll(stringArrayList2);
            }
        }
        this.loadingView = new XPopup.Builder(this.mContext).isViewMode(true).asLoading("加载中", R.layout.layout_loading);
        this.openrel = (RelativeLayout) holder.get(R.id.openrel);
        this.empty_view = (CustomEmptyView) holder.get(R.id.empty_view);
        this.tvStatistics = (TextView) holder.get(R.id.tv_statistics);
        this.appBarLayout = (AppBarLayout) holder.get(R.id.appBarLayout);
        this.opentxt = (TextView) holder.get(R.id.opentxt);
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.recycleview);
        this.recycleview = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.recycleview.setNestedScrollingEnabled(false);
        this.recycleview.setHasFixedSize(true);
        this.recycleview.setItemAnimator(null);
        this.openrel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15856c.lambda$initViews$1(view);
            }
        });
        this.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.fragmenthome.o
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                this.f15882a.lambda$initViews$2(appBarLayout, i2);
            }
        });
        getAdConfig();
        this.questionList_GridView = (RecyclerView) holder.get(R.id.questionList_GridView);
        this.questionList_GridView.setLayoutManager(new GridLayoutManager(this.mContext, (UIUtil.getScreenWidth(this.mContext) - UIUtil.dip2px(this.mContext, 28.0d)) / UIUtil.dip2px(this.mContext, 58.0d)));
        AnswerSheetAdapter answerSheetAdapter = new AnswerSheetAdapter(this.showList, R.layout.item_knowledge_answer_sheet);
        this.answerSheetAdapter = answerSheetAdapter;
        answerSheetAdapter.setKnowledgeChart(true);
        this.answerSheetAdapter.setOnItemClickListener(this);
        this.appBarLayout.setVisibility(8);
        this.tvStatistics.setVisibility(this.bottom_statistics ? 0 : 8);
        if (this.isNode) {
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.KNOWLEDGE_FILTER_VALUE, this.mContext);
            if (!TextUtils.isEmpty(strConfig) && !this.isDailyTaskPage) {
                try {
                    JSONObject jSONObject = new JSONObject(strConfig);
                    Iterator<String> itKeys = jSONObject.keys();
                    while (itKeys.hasNext()) {
                        String next = itKeys.next();
                        String string = jSONObject.getString(next);
                        if (string == null) {
                            this.defaultFilterMap.put(next, new ArrayList());
                        } else {
                            String[] strArrSplit = string.split(",");
                            ArrayList arrayList = new ArrayList(strArrSplit.length);
                            for (String str : strArrSplit) {
                                if (!str.isEmpty()) {
                                    arrayList.add(str.trim());
                                }
                            }
                            this.defaultFilterMap.put(next, arrayList);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (!this.isDailyTaskPage || TextUtils.isEmpty(this.day)) {
                loadQuestionData(this.nodeId, null);
            } else {
                loadStudyPlanQuestionList();
            }
        } else if (this.isKnowledge) {
            loadQuestionData(null, this.knowledgeId);
        } else if (!TextUtils.isEmpty(this.paperId)) {
            this.currentId = this.knowledgeId;
            getPaperData(this.paperId);
        }
        this.statisticsId = this.knowledgeId;
    }

    public void loadQuestionData(String nodeId, String knowledgeId) {
        JSONObject jSONObject;
        AjaxParams ajaxParams = new AjaxParams();
        FragmentActivity activity = getActivity();
        if ((activity instanceof ChartAnswerSheetActivity) && !this.answerSheetAdapter.getData().isEmpty() && ((ChartAnswerSheetActivity) activity).getPageSize() == 2) {
            return;
        }
        if (nodeId != null) {
            ajaxParams.put("node_id", nodeId);
            ajaxParams.put("knowledge_id", "0");
            this.currentId = nodeId;
            if (!this.defaultFilterMap.isEmpty()) {
                try {
                    jSONObject = new JSONObject();
                    for (Map.Entry<String, List<String>> entry : this.defaultFilterMap.entrySet()) {
                        List<String> value = entry.getValue();
                        if (value != null && !value.isEmpty()) {
                            StringBuilder sb = new StringBuilder();
                            int i2 = 0;
                            while (i2 < value.size()) {
                                sb.append(value.get(i2));
                                sb.append(i2 == value.size() + (-1) ? "" : ",");
                                i2++;
                            }
                            jSONObject.put(entry.getKey(), sb.toString());
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    jSONObject = new JSONObject();
                }
                if (jSONObject.length() > 0) {
                    ajaxParams.put("search", jSONObject.toString());
                }
            }
        }
        if (knowledgeId != null) {
            ajaxParams.put("knowledge_id", knowledgeId);
            ajaxParams.put("node_id", "0");
            this.currentId = knowledgeId;
        }
        String str = this.type;
        if (str != null) {
            ajaxParams.put("type", str);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.knowledgeQuestionList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.AnswerSheetFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws JSONException {
                super.onSuccess((AnonymousClass3) s2);
                AnswerSheetFragment.this.onSuccess(s2, NetworkRequestsURL.knowledgeQuestionList);
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        if (this.refresh && (getActivity() instanceof ChartAnswerSheetActivity)) {
            if (((ChartAnswerSheetActivity) getActivity()).getPageSize() == 2) {
                EventBus.getDefault().post(new EventBusMessage("EVENT_QUESTION_ANSWER_REFRESH_KNOWLEDGE", this.nodeId + "-" + this.knowledgeId));
            } else {
                EventBus.getDefault().post(new EventBusMessage("EVENT_QUESTION_ANSWER_REFRESH_KNOWLEDGE", this.knowledgeId));
            }
        }
        this.mHandler.removeCallbacksAndMessages(null);
        hideLoading();
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(NextNodeEvent event) {
        if (getActivity() instanceof ChartAnswerSheetActivity) {
            ChartAnswerSheetActivity chartAnswerSheetActivity = (ChartAnswerSheetActivity) getActivity();
            if (chartAnswerSheetActivity.getPageSize() == 1) {
                this.isNextNode = true;
                this.switchModeSubmit = false;
                if (!this.isDailyTaskPage) {
                    EventBus.getDefault().post(new EventBusMessage("EVENT_QUESTION_ANSWER_REFRESH_KNOWLEDGE", this.knowledgeId));
                    String id = event.getId();
                    this.knowledgeId = id;
                    loadQuestionData(null, id);
                    return;
                }
                if (this.nodeIdList.isEmpty()) {
                    return;
                }
                this.nodeId = this.nodeIdList.get(0);
                this.subject_title = this.nodeIdTitleList.get(0);
                this.nodeIdList.remove(0);
                this.nodeIdTitleList.remove(0);
                chartAnswerSheetActivity.updateTitle(this.subject_title);
                this.empty_view.setVisibility(0);
                this.empty_view.restartAnim();
                this.questionList.clear();
                this.showList.clear();
                this.answerSheetAdapter.notifyDataSetChanged();
                loadStudyPlanQuestionList();
            }
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        hideLoading();
        if (requstUrl.equals(NetworkRequestsURL.questionUserAnswerApi)) {
            initQuestionType();
        }
        this.empty_view.stopAnim();
        this.empty_view.setVisibility(8);
        AlertToast("请求失败");
        if (requstUrl.equals(NetworkRequestsURL.questionRedo)) {
            this.redoMultiKnowledge = false;
        }
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        ProjectApp.showQuestionList = new Gson().toJson(this.showList);
        Bundle bundle = new Bundle();
        if (this.showList.size() <= 0 || position >= this.showList.size()) {
            return;
        }
        String id = this.showList.get(position).getId();
        if (!TextUtils.isEmpty(this.node_parent_id) && !TextUtils.isEmpty(this.nodeId)) {
            SharePreferencesUtils.writeStrConfig("LAST_DO_NODE_KNOWLEDGE_ID_" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext), this.node_parent_id + StrPool.UNDERLINE + this.nodeId, this.mContext);
            EventBus.getDefault().post(new RefreshLastDoEvent(this.node_parent_id, this.nodeId));
        }
        FragmentActivity activity = getActivity();
        if ((activity instanceof ChartAnswerSheetActivity) && ((ChartAnswerSheetActivity) activity).getPageSize() == 1 && !TextUtils.isEmpty(this.knowledgeId) && !TextUtils.isEmpty(this.nodeId)) {
            SharePreferencesUtils.writeStrConfig(CommonParameter.LAST_DO_QUESTION_KNOWLEDGE_NODE_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + StrPool.UNDERLINE + this.nodeId, this.knowledgeId, this.mContext);
            this.refresh = true;
        }
        SharePreferencesUtils.writeStrConfig(CommonParameter.LAST_DO_QUESTION_KNOWLEDGE_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext), this.currentId + "-" + id, this.mContext);
        bundle.putBoolean("study_plan", this.isDailyTaskPage);
        bundle.putInt("position", position);
        bundle.putBoolean("knowledge_point", true);
        bundle.putString("question_id", id);
        bundle.putString("module_type", "1");
        bundle.putString("currentId", this.currentId);
        bundle.putString("knowledgeId", this.knowledgeId);
        bundle.putString("paperId", this.paperId);
        bundle.putString("nodeId", this.nodeId);
        bundle.putString("subject_title", this.subject_title);
        if (TextUtils.isEmpty(this.subject_title) && !TextUtils.isEmpty(this.nodeTitle) && this.isNode) {
            bundle.putString("subject_title", this.nodeTitle);
        }
        bundle.putString("chapter_title", this.chapter_title);
        bundle.putBoolean("fromQuestionCombine", getArguments().getBoolean("fromQuestionCombine", false));
        bundle.putString("identity_id", this.identity_id);
        bundle.putString("chapter_id", this.primary_id);
        bundle.putString("is_show_number", this.is_show_number);
        bundle.putString("type", this.type);
        bundle.putString(UriUtil.QUERY_CATEGORY, this.category);
        bundle.putString("identity_id", this.identity_id);
        bundle.putString("category_id", this.category_id);
        bundle.putString("total", String.valueOf(this.showList.size()));
        bundle.putString("answerMode", this.answerMode);
        if (!TextUtils.isEmpty(this.question_bank_id)) {
            bundle.putString("question_bank_id", this.question_bank_id);
        }
        if (this.isDailyTaskPage && !this.nodeIdList.isEmpty()) {
            bundle.putBoolean("nextNode", true);
        }
        AnswerQuestionActivity.gotoActivity(this.mContext, bundle);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
        if (requstUrl.equals(NetworkRequestsURL.questionListApi) || requstUrl.equals(NetworkRequestsURL.questionSetsList) || requstUrl.equals(NetworkRequestsURL.paperDetail) || requstUrl.equals(NetworkRequestsURL.studyPlanQuestionList) || requstUrl.equals(NetworkRequestsURL.sheetCutList)) {
            showLoading();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onSupportInvisible() {
        super.onSupportInvisible();
        this.isVisible = false;
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onSupportVisible() {
        super.onSupportVisible();
        this.isVisible = true;
    }

    public void refreshFilterParams(ArrayMap<String, List<String>> params) {
        this.defaultFilterMap.clear();
        this.defaultFilterMap.putAll((ArrayMap<? extends String, ? extends List<String>>) params);
        this.showList.clear();
        AnswerSheetAdapter answerSheetAdapter = this.answerSheetAdapter;
        if (answerSheetAdapter != null) {
            answerSheetAdapter.getData().clear();
        }
        loadQuestionData(this.nodeId, null);
    }

    public void setKnowledgeId(String knowledgeId) {
        if (TextUtils.isEmpty(knowledgeId)) {
            return;
        }
        this.statisticsId = knowledgeId;
    }

    public void setTitle(String title) {
        this.chapter_title = title;
    }

    public void showRedoDialog() {
        String str;
        String str2;
        if (this.questionList.isEmpty()) {
            return;
        }
        if (this.paperId != null) {
            str = "重做仅清空答案，不影响斩题记录。\n是否重做当前组题下的试题？";
            str2 = "重做当前组题下的试题";
        } else if (this.knowledgeId != null) {
            str = "重做仅清空答案，不影响斩题记录。\n是否重做当前考点的试题？";
            str2 = "重做当前考点下的试题";
        } else if (this.nodeId != null) {
            str = "重做仅清空答案，不影响斩题记录。\n是否重做当前节点下的所有试题？";
            str2 = "重做当前节点下的所有试题";
        } else {
            str = "";
            str2 = "";
        }
        final KnowledgeQuestionRedoDialog knowledgeQuestionRedoDialog = new KnowledgeQuestionRedoDialog(this.mContext, false, str, str2);
        if (!TextUtils.isEmpty(this.nodeId) && "all".equals(this.type) && !TextUtils.isEmpty(this.identity_id) && !this.isDailyTaskPage) {
            knowledgeQuestionRedoDialog.setShowDoMore(this.identity_id, this.type);
        }
        knowledgeQuestionRedoDialog.setButtonLisenter(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.s
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15976c.lambda$showRedoDialog$6(knowledgeQuestionRedoDialog, view);
            }
        });
        knowledgeQuestionRedoDialog.show();
    }

    public void shrink(int duration) {
        this.openrel.setVisibility(0);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.openrel, "translationY", Utils.dp2px(this.mContext, 40.0f), 0.0f);
        objectAnimatorOfFloat.setDuration(duration);
        objectAnimatorOfFloat.setInterpolator(new OvershootInterpolator());
        objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.fragmenthome.AnswerSheetFragment.8
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

    public void submitAnswer(String todo) {
        if (!CommonUtil.isNetworkConnected(this.mContext)) {
            AlertToast("请您检查网络是否连接");
            return;
        }
        if (Constants.ANSWER_MODE.RECITE_MODE.equals(this.answerMode) && "1".equals(todo)) {
            getActivity().finish();
            return;
        }
        this.errorCount = 0.0d;
        this.rightCount = 0.0d;
        int i2 = 0;
        for (int i3 = 0; i3 < this.showList.size(); i3++) {
            try {
                String str = "0";
                if (TextUtils.isEmpty(this.showList.get(i3).getUser_answer())) {
                    String str2 = "";
                    for (int i4 = 0; i4 < this.showList.get(i3).getOption().size(); i4++) {
                        if (this.showList.get(i3).getOption().get(i4).getType().equals("1")) {
                            str2 = str2 + this.showList.get(i3).getOption().get(i4).getKey();
                        }
                    }
                    this.showList.get(i3).setUser_answer(str2);
                    if (TextUtils.isEmpty(str2)) {
                        i2++;
                    } else {
                        JSONObject jSONObject = new JSONObject();
                        String strTrim = this.showList.get(i3).getAnswer().replace(",", "").trim();
                        jSONObject.put("answer", str2);
                        jSONObject.put("knowledge_id", this.showList.get(i3).getKnowledge_id());
                        jSONObject.put("question_id", this.showList.get(i3).getId());
                        if (strTrim.equals(str2)) {
                            this.rightCount += 1.0d;
                            str = "1";
                        } else {
                            this.errorCount += 1.0d;
                        }
                        jSONObject.put("module_type", (this.knowledgeId == null && this.nodeId == null) ? "1" : "110");
                        jSONObject.put("is_right", str);
                        jSONObject.put("app_id", this.showList.get(i3).getApp_id());
                        jSONObject.put("identity_id", this.identity_id);
                        jSONObject.put("chapter_id", this.primary_id);
                        jSONObject.put("paper_id", 0);
                        jSONObject.put("duration", this.showList.get(i3).getDoDuration());
                        jSONObject.put(HmsMessageService.SUBJECT_ID, "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext));
                        this.mArraySubAnswer.put(jSONObject);
                    }
                } else if (this.showList.get(i3).getIs_right().equals("0")) {
                    this.errorCount += 1.0d;
                } else if (this.showList.get(i3).getIs_right().equals("1")) {
                    this.rightCount += 1.0d;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        for (int i5 = 0; i5 < this.questionList.size(); i5++) {
            if (TextUtils.isEmpty(this.questionList.get(i5).getUser_answer())) {
                String str3 = "";
                for (int i6 = 0; i6 < this.questionList.get(i5).getOption().size(); i6++) {
                    if (this.questionList.get(i5).getOption().get(i6).getType().equals("1")) {
                        str3 = str3 + this.questionList.get(i5).getOption().get(i6).getKey();
                    }
                }
                this.questionList.get(i5).setUser_answer(str3);
            }
        }
        todo.hashCode();
        switch (todo) {
            case "1":
                if (this.mArraySubAnswer.length() == 0) {
                    getActivity().finish();
                    return;
                }
                break;
            case "2":
                if (this.mArraySubAnswer.length() > 0) {
                    postAnswer(this.mArraySubAnswer.toString());
                    return;
                }
                return;
            case "3":
                if (this.showStatistics) {
                    return;
                }
                if (this.mArraySubAnswer.length() > 0) {
                    postAnswer(this.mArraySubAnswer.toString());
                } else if (!this.isDailyTaskPage && !this.isNode) {
                    KnowledgePointStatisticsAct.newIntent(this.mContext, this.statisticsId, getArguments().getString("desc"), this.question_bank_id);
                } else if (this.isNode) {
                    double d3 = this.errorCount;
                    double d4 = this.rightCount;
                    double d5 = d3 + d4 == 0.0d ? 0.0d : (d4 / (d3 + d4)) * 100.0d;
                    DecimalFormat decimalFormat = new DecimalFormat("#0.0");
                    getScoreNewData(this.showList.size() + "", replacevalue((this.errorCount + this.rightCount) + ""), replacevalue(this.rightCount + ""), replacevalue(this.errorCount + ""), replacevalue(decimalFormat.format(d5)), SharePreferencesUtils.readBooleanConfig(CommonParameter.DETAIL_SUBMIT_STATISTICS, false, this.mContext));
                }
                this.showStatistics = true;
                ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.i
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f15654c.lambda$submitAnswer$3();
                    }
                }, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
                return;
        }
        if (Objects.equals(this.answerMode, Constants.ANSWER_MODE.TEST_MODE) && "1".equals(todo)) {
            CustomDialog customDialog = new CustomDialog(this.mContext, 2, R.layout.view_dialog_content_answer_sheet_exit);
            customDialog.setTitle(i2 == 0 ? "确定要交卷吗?" : "您还有" + i2 + "题没做，确定要交卷吗?");
            customDialog.setMessage("保留答题记录，请点击交卷，退出后未提交答题记录将被清空");
            customDialog.setPositiveBtn("确定", new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.k
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15701c.lambda$submitAnswer$4(view);
                }
            });
            customDialog.setNegativeBtn("退出", new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.l
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15794c.lambda$submitAnswer$5(view);
                }
            });
            customDialog.setCancelable(false);
            customDialog.isOutTouchDismiss(false);
            customDialog.show();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:118:0x0463  */
    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onSuccess(java.lang.String r19, java.lang.String r20) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 1577
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.AnswerSheetFragment.onSuccess(java.lang.String, java.lang.String):void");
    }

    @Subscribe
    public void onEventMainThread(RedoMultiKnowledgeEvent event) {
        if (TextUtils.isEmpty(event.getId())) {
            return;
        }
        this.redoMultiKnowledge = true;
        postRedoValue(1, event.getId());
    }

    @Subscribe
    public void onEventMainThread(CloseAnswerSheetEvent event) {
        getActivity().finish();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RedoOtherQuestionEvent event) {
        this.isRedoType = event.getIsError();
        refreshDataByRedoQuestion(NetworkRequestsURL.questionRedo, event.getIsLastDo());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMessage event) {
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
                    updateStatisticsShow();
                    break;
                }
                break;
            case "EVENT_QUESTION_ANALY_REFRESH":
            case "EVENT_QUESTION_ANSWER_REFRESH":
                QuestionDetailBean questionDetailBean2 = (QuestionDetailBean) event.getValueObj();
                if (questionDetailBean2 != null) {
                    String id = questionDetailBean2.getId();
                    int i4 = 0;
                    while (true) {
                        if (i4 < this.showList.size()) {
                            QuestionDetailBean questionDetailBean3 = this.showList.get(i4);
                            if (id.equals(questionDetailBean3.getId())) {
                                questionDetailBean3.setUser_answer(questionDetailBean2.getUser_answer());
                                questionDetailBean3.setStatData(questionDetailBean2.getStatData());
                                questionDetailBean3.setNote(questionDetailBean2.getNote());
                                questionDetailBean3.setmAnalysisBean(questionDetailBean2.getmAnalysisBean());
                                List<QuestionDetailBean.OptionDTO> option = questionDetailBean3.getOption();
                                if (option != null && !option.isEmpty()) {
                                    for (int i5 = 0; i5 < option.size(); i5++) {
                                        option.get(i5).setType("");
                                    }
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
                    if (!TextUtils.isEmpty(this.paperId)) {
                        EventBus.getDefault().post(new RefreshPaperListEvent(this.paperId, getCountByType(2), getCountByType(1)));
                    }
                }
                this.refresh = true;
                break;
            case "EVENT_QUESTION_ANSWER_TEST_STATISTICS":
                Activity currentLastActivity = ProjectApp.instance().getCurrentLastActivity();
                if (currentLastActivity instanceof ChartAnswerSheetActivity) {
                    Object valueObj = event.getValueObj();
                    if (event.getValueObj() != null && (valueObj instanceof String)) {
                        ((ChartAnswerSheetActivity) currentLastActivity).gotoStatistics(valueObj.toString());
                        break;
                    } else {
                        ((ChartAnswerSheetActivity) currentLastActivity).gotoStatistics();
                        break;
                    }
                }
                break;
        }
    }
}
