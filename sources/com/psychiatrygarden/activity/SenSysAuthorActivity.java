package com.psychiatrygarden.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import com.psychiatrygarden.activity.SystemAuthorityActivity;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class SenSysAuthorActivity extends BaseActivity {
    boolean isOpen;
    private TextView opcontent;
    private TextView oppentext;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
            startActivity(intent);
        } catch (Exception unused) {
            window();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        if (this.isOpen) {
            this.isOpen = false;
            this.opcontent.setVisibility(8);
            this.oppentext.setText("展开");
        } else {
            this.isOpen = true;
            this.opcontent.setVisibility(0);
            this.oppentext.setText("收起");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        Intent intent = new Intent(this, (Class<?>) WebLongSaveActivity.class);
        intent.putExtra("web_url", NetworkRequestsURL.getPrivacyApi);
        intent.putExtra("title", "隐私政策");
        startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        SystemAuthorityActivity.SysAuthBean.SecendBean secendBean = (SystemAuthorityActivity.SysAuthBean.SecendBean) getIntent().getExtras().getSerializable(NotificationCompat.CATEGORY_SYSTEM);
        TextView textView = (TextView) findViewById(R.id.title);
        TextView textView2 = (TextView) findViewById(R.id.shezhitxt);
        TextView textView3 = (TextView) findViewById(R.id.shezhicontent);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.shezhiclik);
        TextView textView4 = (TextView) findViewById(R.id.titlecontent);
        this.oppentext = (TextView) findViewById(R.id.oppentext);
        this.opcontent = (TextView) findViewById(R.id.opcontent);
        TextView textView5 = (TextView) findViewById(R.id.weburl);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.relweburl);
        TextView textView6 = (TextView) findViewById(R.id.whys);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.mclickLine);
        textView.setText(secendBean.getTitle());
        textView4.setText(secendBean.getTitleContent());
        textView2.setText(secendBean.getDoThings());
        textView3.setText(secendBean.getDoThingsContent());
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.zi
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14252c.lambda$init$0(view);
            }
        });
        textView6.setText(secendBean.getWhyThings());
        this.opcontent.setText(secendBean.getWhyThingsContent());
        linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.aj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f10996c.lambda$init$1(view);
            }
        });
        textView5.setText(secendBean.getHintweb());
        linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.bj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11115c.lambda$init$2(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_sec_sen_auth);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void window() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("请在手机系统‘设置’中进行授予，尝试查找‘应用管理’、‘权限设置’等关键词");
        builder.setNegativeButton("确定", (DialogInterface.OnClickListener) null);
        builder.show();
    }
}
