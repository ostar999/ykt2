package com.psychiatrygarden.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.adapter.CourseSurveyAdapter;
import com.psychiatrygarden.bean.CourseSurveyBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.yikaobang.yixue.R;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseSurveyActivity extends BaseActivity implements View.OnClickListener {
    private String chapterId;
    private String courseId;
    private final CourseSurveyAdapter mAdapter = new CourseSurveyAdapter();
    private CustomEmptyView mEmptyView;
    private String progress;

    private void getSurveyList() {
        YJYHttpUtils.get(getApplicationContext(), NetworkRequestsURL.courseSurveyList, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CourseSurveyActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CourseSurveyActivity.this.mEmptyView.showEmptyView();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        TextView textView = (TextView) CourseSurveyActivity.this.findViewById(R.id.title);
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        textView.setText(jSONObjectOptJSONObject.optString("title"));
                        List list = (List) new Gson().fromJson(jSONObjectOptJSONObject.optString("problem"), new TypeToken<List<CourseSurveyBean>>() { // from class: com.psychiatrygarden.activity.CourseSurveyActivity.1.1
                        }.getType());
                        if (list == null || list.size() <= 0) {
                            CourseSurveyActivity.this.mEmptyView.showEmptyView();
                        } else {
                            CourseSurveyActivity.this.mEmptyView.stopAnim();
                            CourseSurveyActivity.this.mAdapter.setList(list);
                        }
                    } else {
                        CourseSurveyActivity.this.mEmptyView.showEmptyView();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    CourseSurveyActivity.this.mEmptyView.showEmptyView();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_submit).setOnClickListener(this);
        this.courseId = getIntent().getStringExtra("courseId");
        this.chapterId = getIntent().getStringExtra("chapterId");
        this.progress = getIntent().getStringExtra("progress");
        ((RecyclerView) findViewById(R.id.rvSurvey)).setAdapter(this.mAdapter);
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.mEmptyView = customEmptyView;
        this.mAdapter.setEmptyView(customEmptyView);
        getSurveyList();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) throws JSONException {
        if (v2.getId() == R.id.iv_back) {
            finish();
            return;
        }
        if (v2.getId() == R.id.tv_submit) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put("course_id", this.courseId);
            ajaxParams.put("progress", this.progress);
            Map<String, String> answerMap = this.mAdapter.getAnswerMap();
            List<CourseSurveyBean> data = this.mAdapter.getData();
            JSONArray jSONArray = new JSONArray();
            try {
                Iterator<CourseSurveyBean> it = data.iterator();
                while (it.hasNext()) {
                    String id = it.next().getId();
                    String str = answerMap.get(id);
                    if (TextUtils.isEmpty(str)) {
                        NewToast.showShort(this, "还有问题未作答");
                        return;
                    }
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("id", id);
                    jSONObject.put(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION, str);
                    jSONArray.put(jSONObject);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            ajaxParams.put("answer", jSONArray.toString());
            YJYHttpUtils.post(getApplicationContext(), NetworkRequestsURL.submitCourseSurvey, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CourseSurveyActivity.2
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) {
                    super.onSuccess((AnonymousClass2) s2);
                    try {
                        JSONObject jSONObject2 = new JSONObject(s2);
                        if (TextUtils.equals("200", jSONObject2.optString("code"))) {
                            CourseSurveyActivity.this.AlertToast("提交成功");
                            CourseSurveyActivity.this.finish();
                        } else {
                            CourseSurveyActivity.this.AlertToast(jSONObject2.optString("message"));
                        }
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                }
            });
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_survey);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
