package com.psychiatrygarden.activity.circleactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;

/* loaded from: classes5.dex */
public class CircleSchoolSectionQuestionActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        this.mBaseTheme = currentSkinType;
        if (currentSkinType != 0) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#171D2D"));
        } else {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
            getWindow().getDecorView().setSystemUiVisibility(R2.drawable.ddbq);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        if (v2.getId() != R.id.iv_back) {
            return;
        }
        finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.activity_circle_section_question);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.iv_back.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.t2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11596c.onClick(view);
            }
        });
    }
}
