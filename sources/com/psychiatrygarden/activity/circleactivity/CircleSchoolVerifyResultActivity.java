package com.psychiatrygarden.activity.circleactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.psychiatrygarden.activity.BaseActivity;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class CircleSchoolVerifyResultActivity extends BaseActivity implements View.OnClickListener {
    private String flag;
    private ImageView iv_back;
    ImageView iv_inreview;
    TextView tv_content;
    private TextView tv_next;
    TextView tv_state;

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.flag = getIntent().getExtras().getString("flag");
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.tv_next = (TextView) findViewById(R.id.tv_next);
        this.iv_inreview = (ImageView) findViewById(R.id.iv_inreview);
        this.tv_state = (TextView) findViewById(R.id.tv_state);
        this.tv_content = (TextView) findViewById(R.id.tv_content);
        if (this.flag.equals("0")) {
            return;
        }
        this.iv_inreview.setImageResource(R.mipmap.verify_rejected);
        this.tv_state.setText("认证未通过");
        this.tv_content.setText("请提交有效认证信息");
        this.tv_next.setText("重新认证");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.iv_back) {
            finish();
            return;
        }
        if (id != R.id.tv_next) {
            return;
        }
        if (this.flag.equals("0")) {
            finish();
            return;
        }
        Intent intent = new Intent(this, (Class<?>) CircleSchoolInfoActivity.class);
        intent.putExtra("group_id", "" + getIntent().getExtras().getString("group_id"));
        startActivity(intent);
        finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("身份验证");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_circle_verify_result);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.iv_back.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.d3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11508c.onClick(view);
            }
        });
        this.tv_next.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.d3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11508c.onClick(view);
            }
        });
    }
}
