package com.psychiatrygarden.activity.courselist;

import android.os.Bundle;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.fragmenthome.CourseListFragment;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class CourseMoreActivity extends BaseActivity {
    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getExtras().getString("title"));
        if (findFragment(CourseListFragment.class) == null) {
            Bundle bundle = new Bundle();
            bundle.putString("c_id", "" + getIntent().getExtras().getString("c_id"));
            CourseListFragment courseListFragment = new CourseListFragment();
            courseListFragment.setArguments(bundle);
            loadRootFragment(R.id.fragemnt, courseListFragment, true, true);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_more);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
