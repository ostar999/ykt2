package com.psychiatrygarden;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.CommonProblem;
import com.psychiatrygarden.fragmenthome.CommonProblemFragment;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class CommonProblemActivity extends BaseActivity {
    private List<CommonProblem> mProblemList;

    public static Intent getIntent(Context context) {
        return new Intent(context, (Class<?>) CommonProblemActivity.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        String stringExtra = getIntent().getStringExtra("data");
        ((ImageView) findViewById(R.id.imgCommonProblemBack)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16183c.lambda$init$0(view);
            }
        });
        ((TextView) findViewById(R.id.titleCommonProblem)).setText("常见问题");
        CommonProblemFragment commonProblemFragment = new CommonProblemFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isLoadCourseSearchUI", true);
        bundle.putString("id", "1");
        bundle.putString("data", stringExtra);
        commonProblemFragment.setArguments(bundle);
        if (findFragment(CommonProblemFragment.class) == null) {
            loadRootFragment(R.id.fragmentCommonProblem, commonProblemFragment);
        } else {
            replaceFragment(commonProblemFragment, false);
        }
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
        setContentView(R.layout.activity_common_problem);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
