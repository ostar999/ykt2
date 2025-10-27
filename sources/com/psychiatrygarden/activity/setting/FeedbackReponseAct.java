package com.psychiatrygarden.activity.setting;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.FeedbackResponseBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class FeedbackReponseAct extends BaseActivity {
    private FeedbackResponseAdp mAdapter;
    private RecyclerView mRecyclerView;

    private void getData(String id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", id);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.userFeedbackInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.FeedbackReponseAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                try {
                    FeedbackResponseBean feedbackResponseBean = (FeedbackResponseBean) new Gson().fromJson(t2, FeedbackResponseBean.class);
                    if (!feedbackResponseBean.getCode().equals("200")) {
                        FeedbackReponseAct.this.AlertToast(feedbackResponseBean.getMessage());
                    } else if (feedbackResponseBean.getData() != null && feedbackResponseBean.getData().size() > 0) {
                        FeedbackReponseAct.this.mAdapter.setNewData(feedbackResponseBean.getData());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public static Intent newIntent(Context context, String id) {
        Intent intent = new Intent(context, (Class<?>) FeedbackReponseAct.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        String stringExtra = getIntent().getStringExtra("id");
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        FeedbackResponseAdp feedbackResponseAdp = new FeedbackResponseAdp();
        this.mAdapter = feedbackResponseAdp;
        this.mRecyclerView.setAdapter(feedbackResponseAdp);
        getData(stringExtra);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_feedback_response);
        setTitle("反馈回复");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
