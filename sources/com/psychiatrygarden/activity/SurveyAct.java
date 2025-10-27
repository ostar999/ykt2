package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.online.SelectIdentityNewActivity;
import com.psychiatrygarden.adapter.SurveyAdp;
import com.psychiatrygarden.bean.SurveyBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.yikaobang.yixue.R;
import java.util.Collection;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SurveyAct extends BaseActivity {
    private SurveyAdp mAdapter;

    private void getData() {
        YJYHttpUtils.post(getApplicationContext(), NetworkRequestsURL.CHANNEL_LIST, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SurveyAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        SurveyAct.this.mAdapter.addData((Collection) ((SurveyBean) new Gson().fromJson(s2, SurveyBean.class)).getData());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goToHomePage() {
        ProjectApp.instance().initSdkPload();
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.agree, true, this);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.FIRSTSTART, false, this);
        Intent intent = new Intent(this, (Class<?>) SelectIdentityNewActivity.class);
        intent.putExtra("flag", false);
        intent.putExtra("appbeanname", "");
        startActivity(intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        submitData(this.mAdapter.getItem(i2).getId());
    }

    public static void startAct(Context context) {
        context.startActivity(new Intent(context, (Class<?>) SurveyAct.class));
    }

    private void submitData(String channelId) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("channel", channelId);
        YJYHttpUtils.post(getApplicationContext(), NetworkRequestsURL.SUBMIT_CHANNEL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SurveyAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SurveyAct.this.goToHomePage();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                SurveyAct.this.goToHomePage();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        SurveyAdp surveyAdp = new SurveyAdp();
        this.mAdapter = surveyAdp;
        recyclerView.setAdapter(surveyAdp);
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.ho
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12485c.lambda$init$0(baseQuickAdapter, view, i2);
            }
        });
        getData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_survey);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
