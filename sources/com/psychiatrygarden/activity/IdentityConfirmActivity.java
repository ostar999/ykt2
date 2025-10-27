package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class IdentityConfirmActivity extends BaseActivity implements View.OnClickListener {
    private int identityType = 0;
    private LinearLayout mLlEmail;
    private LinearLayout mLlPhone;

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        TextView textView = (TextView) findViewById(R.id.tv_top_hint);
        TextView textView2 = (TextView) findViewById(R.id.tv_email_identity);
        TextView textView3 = (TextView) findViewById(R.id.tv_phone_identity);
        this.mLlEmail = (LinearLayout) findViewById(R.id.ll_email);
        this.mLlPhone = (LinearLayout) findViewById(R.id.ll_phone);
        int intExtra = getIntent().getIntExtra("identityType", 0);
        this.identityType = intExtra;
        if (intExtra == 0) {
            textView.setText("为了你的账户安全，需要对你的身份进行验证，验证后即可更换手机号。");
        } else if (intExtra == 1) {
            textView.setText("为了你的账户安全，需要对你的身份进行验证，验证后即可修改密码。");
        } else if (intExtra == 2) {
            textView.setText("为了你的账户安全，需要对你的身份进行验证，验证后即可注销账号。");
        }
        String str = SharePreferencesUtils.readStrConfig(CommonParameter.TEL, this) + "";
        try {
            if (str.length() > 7) {
                textView3.setText(String.format("通过%s****%s接收短信验证码", str.substring(0, 3), str.substring(7)));
            } else {
                textView3.setText(String.format("通过%s接收短信验证码", str));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            textView3.setText(str);
        }
        String email = UserConfig.getInstance().getUser().getEmail();
        if (TextUtils.isEmpty(email)) {
            this.mLlEmail.setVisibility(8);
            return;
        }
        this.mLlEmail.setVisibility(0);
        try {
            String[] strArrSplit = email.split("@");
            textView2.setText(String.format("通过%s*********%s接收邮箱验证码", strArrSplit[0].substring(0, 2), strArrSplit[1]));
        } catch (Exception e3) {
            e3.printStackTrace();
            textView2.setText(String.format("通过%s接收邮箱验证码", email));
        }
    }

    @Override // android.view.View.OnClickListener
    @SuppressLint({"NonConstantResourceId"})
    public void onClick(View v2) {
        Intent intent = new Intent();
        int id = v2.getId();
        if (id == R.id.ll_email) {
            intent.setClass(this.mContext, PhoneChangeActivity.class);
            intent.putExtra("gotoType", 1);
            intent.putExtra("identityType", this.identityType);
            startActivity(intent);
            return;
        }
        if (id != R.id.ll_phone) {
            return;
        }
        intent.setClass(this.mContext, PhoneChangeActivity.class);
        intent.putExtra("gotoType", 0);
        intent.putExtra("identityType", this.identityType);
        startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_identity_confirm);
        setTitle("身份确认");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mLlEmail.setOnClickListener(this);
        this.mLlPhone.setOnClickListener(this);
    }
}
