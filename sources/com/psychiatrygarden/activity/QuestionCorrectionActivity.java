package com.psychiatrygarden.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.fragmenthome.ErrorCorrectionFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class QuestionCorrectionActivity extends BaseActivity {
    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0(View view) {
        getPopTips();
    }

    public void getPopTips() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "notice_for_rules");
        YJYHttpUtils.get(this, NetworkRequestsURL.vipnoticeApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.QuestionCorrectionActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        String strOptString = jSONObject.optJSONObject("data").optString("web_url");
                        Intent intent = new Intent(QuestionCorrectionActivity.this, (Class<?>) WebViewActivity.class);
                        intent.putExtra("title", "活动规则");
                        intent.putExtra("web_url", strOptString);
                        QuestionCorrectionActivity.this.startActivity(intent);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getPopTips2() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "notice_for_rules");
        YJYHttpUtils.get(this, NetworkRequestsURL.vipnoticeApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.QuestionCorrectionActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        if ("1".equals(jSONObject.optJSONObject("data").optString("error_correction_display"))) {
                            QuestionCorrectionActivity.this.mBtnActionbarRight.setVisibility(0);
                        } else {
                            QuestionCorrectionActivity.this.mBtnActionbarRight.setVisibility(8);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        ErrorCorrectionFragment errorCorrectionFragment = new ErrorCorrectionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("module_type", 25);
        bundle.putString(com.umeng.analytics.pro.aq.f22519d, "" + getIntent().getExtras().getString("question_id"));
        errorCorrectionFragment.setArguments(bundle);
        loadRootFragment(R.id.fragment, errorCorrectionFragment);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        super.initStatusBar();
        if (this.mBaseTheme == 0) {
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            try {
                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    if (fragment instanceof BaseFragment) {
                        ((BaseFragment) fragment).onFragmentResult(requestCode, resultCode, data.getBundleExtra("bundleIntent"));
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle("纠错");
        this.mBtnActionbarRight.setVisibility(8);
        this.mBtnActionbarRight.setText("规则");
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.of
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13079c.lambda$setContentView$0(view);
            }
        });
        setContentView(R.layout.activity_correct);
        getPopTips2();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
