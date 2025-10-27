package com.psychiatrygarden.activity.chooseSchool;

import android.content.Context;
import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.psychiatrygarden.activity.BaseActivity;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class SchoolIntroductionAct extends BaseActivity {
    public static void newIntent(Context context, boolean isSchool, String content, String name) {
        Intent intent = new Intent(context, (Class<?>) SchoolIntroductionAct.class);
        intent.putExtra("content", content);
        intent.putExtra("name", name);
        intent.putExtra("isSchool", isSchool);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        boolean booleanExtra = getIntent().getBooleanExtra("isSchool", false);
        String stringExtra = getIntent().getStringExtra("name");
        String stringExtra2 = getIntent().getStringExtra("content");
        setTitle(stringExtra);
        TextView textView = (TextView) findViewById(R.id.tv_content);
        ((RelativeLayout) findViewById(R.id.ly_desc)).setVisibility(booleanExtra ? 0 : 8);
        textView.setText(stringExtra2);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_school_introduction);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
