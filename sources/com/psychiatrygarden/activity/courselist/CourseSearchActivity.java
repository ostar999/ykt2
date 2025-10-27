package com.psychiatrygarden.activity.courselist;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.fragment.CurriculumSearchFragment;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.widget.ClearEditText;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class CourseSearchActivity extends BaseActivity {
    private CurriculumSearchFragment curriculumFragment;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(ClearEditText clearEditText, View view) {
        String strTrim = clearEditText.getText().toString().trim();
        if (this.curriculumFragment == null || TextUtils.isEmpty(strTrim)) {
            return;
        }
        this.curriculumFragment.setSearchKeyWord(strTrim, true);
        AndroidBaseUtils.hideSoftInputFromWindow(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$2(ClearEditText clearEditText, TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        String strTrim = clearEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(strTrim)) {
            this.curriculumFragment.setSearchKeyWord(strTrim, true);
            AndroidBaseUtils.hideSoftInputFromWindow(this);
        }
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        ((ImageView) findViewById(R.id.iv_actionbar_back)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.f1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11909c.lambda$init$0(view);
            }
        });
        final ClearEditText clearEditText = (ClearEditText) findViewById(R.id.ed_search);
        clearEditText.setHint("输入关键词搜索课程");
        ((TextView) findViewById(R.id.btn_search)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.g1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12087c.lambda$init$1(clearEditText, view);
            }
        });
        clearEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.courselist.h1
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f12093c.lambda$init$2(clearEditText, textView, i2, keyEvent);
            }
        });
        this.curriculumFragment = new CurriculumSearchFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isLoadCourseSearchUI", true);
        bundle.putString("id", "1");
        this.curriculumFragment.setArguments(bundle);
        if (findFragment(CurriculumSearchFragment.class) == null) {
            loadRootFragment(R.id.fid, this.curriculumFragment);
        } else {
            replaceFragment(this.curriculumFragment, false);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        setNewStyleStatusBarColor2();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_search);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
