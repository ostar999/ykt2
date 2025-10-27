package com.psychiatrygarden.activity.chat;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ChatReportActivity extends BaseActivity {
    ImageView jmui_return_btn;
    LinearLayout linep;
    TextView submitBtn;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1() {
        ToastUtil.shortToast(this, "提交成功");
        this.linep.setVisibility(8);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        try {
            if (((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo().isAvailable()) {
                this.linep.setVisibility(0);
                new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.chat.n
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f11168c.lambda$setListenerForWidget$1();
                    }
                }, 500L);
            } else {
                ToastUtil.shortToast(this, "请先连接网络！");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        this.jmui_return_btn = (ImageView) findViewById(R.id.jmui_return_btn);
        this.linep = (LinearLayout) findViewById(R.id.linep);
        this.submitBtn = (TextView) findViewById(R.id.submitBtn);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_reportim);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.jmui_return_btn.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11166c.lambda$setListenerForWidget$0(view);
            }
        });
        this.submitBtn.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11167c.lambda$setListenerForWidget$2(view);
            }
        });
    }
}
