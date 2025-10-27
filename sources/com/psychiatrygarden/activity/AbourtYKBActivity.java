package com.psychiatrygarden.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.VersionUpdateBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class AbourtYKBActivity extends BaseActivity {
    public TextView function_introduction;
    public LinearLayout goToScore;
    public ImageView headerview;
    private LinearLayout mLyUpdateVersion;
    private View mNewVersion;
    public TextView privacypolicy;
    public RelativeLayout systemPermissions;
    public TextView userAgreement;
    public TextView versionCode;

    private void checkVersion() {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("system", "2");
            ajaxParams.put("code", AndroidBaseUtils.getAPPVersionCode(getApplicationContext()) + "");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.post(getApplicationContext(), NetworkRequestsURL.mCheckVersionUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.AbourtYKBActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                VersionUpdateBean.VersionBean data;
                super.onSuccess((AnonymousClass1) s2);
                try {
                    VersionUpdateBean versionUpdateBean = (VersionUpdateBean) new Gson().fromJson(s2, VersionUpdateBean.class);
                    if (versionUpdateBean.getCode().equals("200") && (data = versionUpdateBean.getData()) != null) {
                        if (TextUtils.isEmpty(data.getIsForce()) || TextUtils.isEmpty(data.getAppUrl())) {
                            SharePreferencesUtils.writeBooleanConfig("showVersionUpdate", false, AbourtYKBActivity.this);
                        } else {
                            Intent intent = new Intent(AbourtYKBActivity.this, (Class<?>) ForceUpdateActivity.class);
                            intent.putExtra("is_force_update", data.getIsForce());
                            intent.putExtra("message", data.getMessage());
                            intent.putExtra("app_link", data.getAppUrl());
                            intent.putExtra("verCode", data.getCode());
                            intent.setFlags(268435456);
                            intent.setFlags(4194304);
                            AbourtYKBActivity.this.startActivity(intent);
                            SharePreferencesUtils.writeBooleanConfig("showVersionUpdate", true, AbourtYKBActivity.this);
                        }
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName()));
            intent.addFlags(268435456);
            startActivity(intent);
        } catch (Exception unused) {
            AlertToast("评分出错了~");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        Intent intent = new Intent(this.mContext, (Class<?>) WebViewActivity.class);
        intent.putExtra("web_url", NetworkRequestsURL.mGetGyHtmlUrl);
        intent.putExtra("title", "关于我们");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        Intent intent = new Intent(this.mContext, (Class<?>) WebViewActivity.class);
        intent.putExtra("web_url", NetworkRequestsURL.mGetGnjsHtmlUrl);
        intent.putExtra("title", "功能介绍");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        Intent intent = new Intent(this, (Class<?>) WebLongSaveActivity.class);
        intent.putExtra("web_url", NetworkRequestsURL.getPrivacyApi);
        intent.putExtra("title", "隐私政策");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(View view) {
        Intent intent = new Intent(this, (Class<?>) WebLongSaveActivity.class);
        intent.putExtra("web_url", NetworkRequestsURL.userAgreementApi);
        intent.putExtra("title", "用户协议");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(View view) {
        goActivity(SystemAuthorityActivity.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$6(View view) {
        checkVersion();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.headerview = (ImageView) findViewById(R.id.headerview);
        this.goToScore = (LinearLayout) findViewById(R.id.goToScore);
        this.versionCode = (TextView) findViewById(R.id.versionCode);
        this.function_introduction = (TextView) findViewById(R.id.function_introduction);
        this.privacypolicy = (TextView) findViewById(R.id.privacypolicy);
        this.userAgreement = (TextView) findViewById(R.id.userAgreement);
        this.systemPermissions = (RelativeLayout) findViewById(R.id.systemPermissions);
        this.mNewVersion = findViewById(R.id.view_new_version);
        this.mLyUpdateVersion = (LinearLayout) findViewById(R.id.ly_update_version);
        try {
            int aPPVersionCode = AndroidBaseUtils.getAPPVersionCode(this);
            int i2 = ProjectApp.android_open;
            if (i2 == 0 || aPPVersionCode < i2) {
                this.goToScore.setVisibility(0);
            } else {
                this.goToScore.setVisibility(8);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.mNewVersion.setVisibility(SharePreferencesUtils.readBooleanConfig("haveNewVersion", false, this) ? 0 : 8);
        this.goToScore.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f10978c.lambda$init$0(view);
            }
        });
        try {
            this.versionCode.setText("版本号\t" + AndroidBaseUtils.getAppVersion(this.mContext));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        this.headerview.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11091c.lambda$init$1(view);
            }
        });
        this.function_introduction.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11131c.lambda$init$2(view);
            }
        });
        this.privacypolicy.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12222c.lambda$init$3(view);
            }
        });
        this.userAgreement.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12263c.lambda$init$4(view);
            }
        });
        this.systemPermissions.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12332c.lambda$init$5(view);
            }
        });
        this.mLyUpdateVersion.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12420c.lambda$init$6(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_abourt_ykb);
        setTitle(getResources().getString(R.string.about_app_name));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
