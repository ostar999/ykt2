package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.EventBusConstant;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ModifyMailboxActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvModifyMail;
    private TextView mTvUnbindEmail;

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        TextView textView = (TextView) findViewById(R.id.tv_email_conent);
        this.mTvModifyMail = (TextView) findViewById(R.id.tv_modify_mail);
        this.mTvUnbindEmail = (TextView) findViewById(R.id.tv_unbind_email);
        String email = UserConfig.getInstance().getUser().getEmail();
        try {
            if (TextUtils.isEmpty(email)) {
                textView.setText("待完善");
            } else {
                String[] strArrSplit = email.split("@");
                textView.setText(String.format("%s*********%s", strArrSplit[0].substring(0, 2), strArrSplit[1]));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            textView.setText(email);
        }
    }

    @Override // android.view.View.OnClickListener
    @SuppressLint({"NonConstantResourceId"})
    public void onClick(View v2) {
        int id = v2.getId();
        if (id != R.id.tv_modify_mail) {
            if (id != R.id.tv_unbind_email) {
                return;
            }
            Intent intent = new Intent(this, (Class<?>) AccountLogoutConfirmActivity.class);
            intent.putExtra("gotoType", 1);
            startActivity(intent);
            return;
        }
        Intent intent2 = new Intent();
        intent2.setClass(this.mContext, PhoneChangeActivity.class);
        intent2.putExtra("gotoType", 1);
        intent2.putExtra("identityType", 3);
        startActivity(intent2);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals(EventBusConstant.EVENT_EMAIL_CHANGE_SUCCESS)) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_modify_mailbox);
        setTitle("修改邮箱");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mTvUnbindEmail.setOnClickListener(this);
        this.mTvModifyMail.setOnClickListener(this);
    }
}
