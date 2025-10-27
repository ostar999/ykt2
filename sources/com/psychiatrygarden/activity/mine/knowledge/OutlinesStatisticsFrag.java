package com.psychiatrygarden.activity.mine.knowledge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import androidx.core.widget.NestedScrollView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.SubQuestionCeshiDaActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.bean.MockLoulineStatisticsBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.MockPointStatisticeChildItemView;
import com.yikaobang.yixue.R;
import java.util.Collections;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class OutlinesStatisticsFrag extends BaseFragment {
    private String examId;
    private String isEstimate;
    private String isSchoolRank;
    private LinearLayout mLyAddView;
    private String score;
    private NestedScrollView scrollView;
    private String title;

    private void getData(String answer) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", this.examId);
        ajaxParams.put("answer", answer);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mockStatisticsOutlines, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.knowledge.OutlinesStatisticsFrag.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    MockLoulineStatisticsBean mockLoulineStatisticsBean = (MockLoulineStatisticsBean) new Gson().fromJson(s2, MockLoulineStatisticsBean.class);
                    if (!mockLoulineStatisticsBean.getCode().equals("200")) {
                        ToastUtil.shortToast(((BaseFragment) OutlinesStatisticsFrag.this).mContext, mockLoulineStatisticsBean.getMessage());
                        return;
                    }
                    if (mockLoulineStatisticsBean.getData() == null || mockLoulineStatisticsBean.getData().getList() == null || mockLoulineStatisticsBean.getData().getList().size() <= 0) {
                        return;
                    }
                    OutlinesStatisticsFrag.this.mLyAddView.removeAllViews();
                    for (int i2 = 0; i2 < mockLoulineStatisticsBean.getData().getList().size(); i2++) {
                        MockPointStatisticeChildItemView mockPointStatisticeChildItemView = new MockPointStatisticeChildItemView(((BaseFragment) OutlinesStatisticsFrag.this).mContext);
                        mockPointStatisticeChildItemView.setOnItemActionLisenter(new MockPointStatisticeChildItemView.OnItemActionLisenter() { // from class: com.psychiatrygarden.activity.mine.knowledge.OutlinesStatisticsFrag.2.1
                            @Override // com.psychiatrygarden.widget.MockPointStatisticeChildItemView.OnItemActionLisenter
                            public void jumpToQuestionDetail(String knowId) {
                                OutlinesStatisticsFrag.this.getQuestionList(knowId);
                            }
                        });
                        mockPointStatisticeChildItemView.setData(mockLoulineStatisticsBean.getData().getList().get(i2), 1);
                        OutlinesStatisticsFrag.this.mLyAddView.addView(mockPointStatisticeChildItemView);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getQuestionList(String knowledgeId) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", this.examId);
        ajaxParams.put("chapter_id", knowledgeId);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getKnowledgeOutlinesQuestion, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.knowledge.OutlinesStatisticsFrag.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        List list = (List) new Gson().fromJson(DesUtil.decode(CommonParameter.DES_KEY_VERIFY, new JSONObject(jSONObject.optString("data")).optString("data")), new TypeToken<List<ExesQuestionBean>>() { // from class: com.psychiatrygarden.activity.mine.knowledge.OutlinesStatisticsFrag.3.1
                        }.getType());
                        Collections.sort(list);
                        ProjectApp.questExamDataList.clear();
                        ProjectApp.questExamDataList.addAll(list);
                        OutlinesStatisticsFrag.this.jumpToDetails();
                    } else {
                        ToastUtil.shortToast(((BaseFragment) OutlinesStatisticsFrag.this).mContext, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void jumpToDetails() {
        Intent intent = new Intent(getContext(), (Class<?>) SubQuestionCeshiDaActivity.class);
        intent.putExtra("exam_id", this.examId);
        intent.putExtra("question_file", "");
        intent.putExtra("title", this.title);
        intent.putExtra("user_exam_time", "");
        intent.putExtra("score", this.score);
        intent.putExtra("istongji", true);
        intent.putExtra("is_esaydta", this.isEstimate);
        intent.putExtra("is_school_rank", this.isSchoolRank);
        startActivity(intent);
    }

    public static OutlinesStatisticsFrag newInstance(String examId, String title, String isEstimate, String score, String isSchoolRank) {
        Bundle bundle = new Bundle();
        bundle.putString("examId", examId);
        bundle.putString("title", title);
        bundle.putString("isEstimate", isEstimate);
        bundle.putString("score", score);
        bundle.putString("isSchoolRank", isSchoolRank);
        OutlinesStatisticsFrag outlinesStatisticsFrag = new OutlinesStatisticsFrag();
        outlinesStatisticsFrag.setArguments(bundle);
        return outlinesStatisticsFrag;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.frag_outlines_sttistics;
    }

    public Bitmap getRootView() {
        int height = this.scrollView.getHeight();
        for (int i2 = 0; i2 < this.scrollView.getChildCount(); i2++) {
            height += this.scrollView.getChildAt(i2).getHeight();
        }
        LogUtils.e("point_view_height", "totalHeight=" + height);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.scrollView.getWidth(), height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        int height2 = 0;
        for (int i3 = 0; i3 < this.scrollView.getChildCount(); i3++) {
            View childAt = this.scrollView.getChildAt(i3);
            childAt.layout(0, height2, childAt.getWidth(), childAt.getHeight() + height2);
            childAt.draw(canvas);
            height2 += childAt.getHeight();
        }
        return bitmapCreateBitmap;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) throws JSONException {
        this.examId = getArguments().getString("examId");
        this.title = getArguments().getString("title");
        this.isEstimate = getArguments().getString("isEstimate");
        this.isSchoolRank = getArguments().getString("isSchoolRank");
        this.score = getArguments().getString("score");
        this.mLyAddView = (LinearLayout) holder.get(R.id.ly_add_view);
        this.scrollView = (NestedScrollView) holder.get(R.id.scroll_view);
        List list = (List) new Gson().fromJson(new Gson().toJson(ProjectApp.questExamList), new TypeToken<List<ExesQuestionBean>>() { // from class: com.psychiatrygarden.activity.mine.knowledge.OutlinesStatisticsFrag.1
        }.getType());
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < list.size(); i2++) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("question_id", ((ExesQuestionBean) list.get(i2)).getQuestion_id());
                jSONObject.put("score", ((ExesQuestionBean) list.get(i2)).getScore());
                jSONObject.put("is_right", ((ExesQuestionBean) list.get(i2)).getIsRight());
                jSONObject.put("chapter_id", ((ExesQuestionBean) list.get(i2)).getChapter_id());
                jSONObject.put("knowledge_id", TextUtils.isEmpty(((ExesQuestionBean) list.get(i2)).getKnowledge_id()) ? "" : ((ExesQuestionBean) list.get(i2)).getKnowledge_id());
                jSONArray.put(jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        getData(jSONArray.toString());
    }
}
