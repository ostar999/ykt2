package com.psychiatrygarden.forum.database;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.tencent.smtt.sdk.WebView;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class DataPreviewAct extends BaseActivity {
    private View mBottomView;
    private TextView mTvTitle;
    private WebView mWebview;

    public static void newIntent(Context context, String id, String name, String url) {
        Intent intent = new Intent(context, (Class<?>) DataPreviewAct.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        String stringExtra = getIntent().getStringExtra("name");
        this.mWebview = (WebView) findViewById(R.id.webview);
        this.mBottomView = findViewById(R.id.bottom_view);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        this.mTvTitle = textView;
        textView.setText(stringExtra);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initwriteStatusBar() {
        super.initwriteStatusBar();
        if (this.mBaseTheme == 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white_color), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.app_bg_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        initwriteStatusBar();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layotu_data_preview);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
