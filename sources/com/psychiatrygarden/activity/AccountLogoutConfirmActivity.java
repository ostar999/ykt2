package com.psychiatrygarden.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.psychiatrygarden.bean.CancelAccountBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class AccountLogoutConfirmActivity extends BaseActivity implements View.OnClickListener {
    private int gotoType = 0;

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        CancelAccountBean cancelAccountBean = (CancelAccountBean) getIntent().getSerializableExtra(AliyunLogCommon.LogLevel.INFO);
        TextView textView = (TextView) findViewById(R.id.tv_logout);
        ImageView imageView = (ImageView) findViewById(R.id.iv_top_img);
        TextView textView2 = (TextView) findViewById(R.id.tv_top_title);
        TextView textView3 = (TextView) findViewById(R.id.tv_center_content);
        TextView textView4 = (TextView) findViewById(R.id.tv_red_tps);
        textView.setOnClickListener(this);
        int intExtra = getIntent().getIntExtra("gotoType", 0);
        this.gotoType = intExtra;
        if (intExtra == 0) {
            setTitle("注销结果");
            if (SkinManager.getCurrentSkinType(this) == 0) {
                imageView.setImageResource(R.mipmap.logout_success_icon);
            } else {
                imageView.setImageResource(R.mipmap.logout_success_icon_night);
            }
            textView2.setText("注销成功");
            if (cancelAccountBean != null) {
                textView3.setText(cancelAccountBean.getTips());
                textView4.setVisibility(0);
                textView4.setText(cancelAccountBean.getTips_red());
            }
            textView.setText("下次再见");
            return;
        }
        if (intExtra == 1) {
            setTitle("解除绑定");
            if (SkinManager.getCurrentSkinType(this) == 0) {
                imageView.setImageResource(R.mipmap.email_hint_icon);
            } else {
                imageView.setImageResource(R.mipmap.email_hint_icon_night);
            }
            textView2.setText("重要提示");
            textView3.setGravity(17);
            textView3.setText("请注意，你正在进行邮箱的解除绑定操作，解除绑定将会降低您的账号安全等级，请谨慎操作，确认解绑请点击下一步。");
            textView.setText("下一步");
            return;
        }
        if (intExtra == 2) {
            setTitle("解绑成功");
            if (SkinManager.getCurrentSkinType(this) == 0) {
                imageView.setImageResource(R.mipmap.logout_success_icon);
            } else {
                imageView.setImageResource(R.mipmap.logout_success_icon_night);
            }
            textView2.setText("邮箱解绑成功");
            textView3.setGravity(17);
            textView3.setText("您的帐号存在安全风险，建议您绑定其他邮箱");
            textView.setText("确定");
            return;
        }
        if (intExtra != 3) {
            return;
        }
        setTitle("绑定邮箱提示");
        if (SkinManager.getCurrentSkinType(this) == 0) {
            imageView.setImageResource(R.mipmap.email_hint_icon);
        } else {
            imageView.setImageResource(R.mipmap.email_hint_icon_night);
        }
        textView2.setText("重要提示");
        textView3.setGravity(17);
        textView3.setText("请注意，你正在进行绑定邮箱的操作，绑定邮箱将极大地提升账号安全等级，确认绑定请点击下一步。");
        textView.setText("下一步");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        if (v2.getId() == R.id.tv_logout) {
            int i2 = this.gotoType;
            if (i2 == 0) {
                SharePreferencesUtils.clearAppointData(this);
                Intent intent = new Intent(this, (Class<?>) HomePageNewActivity.class);
                intent.addFlags(268468224);
                startActivity(intent);
                overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
                return;
            }
            if (i2 == 1) {
                Intent intent2 = new Intent(this, (Class<?>) EmailConfirmActivity.class);
                intent2.putExtra("emailType", 2);
                startActivity(intent2);
                finish();
            } else if (i2 != 2) {
                if (i2 != 3) {
                    return;
                }
                Intent intent3 = new Intent(this, (Class<?>) EmailConfirmActivity.class);
                intent3.putExtra("emailType", 1);
                startActivity(intent3);
                finish();
                return;
            }
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_account_logout_confirm);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
