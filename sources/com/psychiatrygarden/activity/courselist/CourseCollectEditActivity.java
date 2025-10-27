package com.psychiatrygarden.activity.courselist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity;
import com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryCollectEditFragment;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class CourseCollectEditActivity extends BaseActivity {
    public static void gotoCollectEditActivity(Context context, String courseId, String courseType) {
        Intent intent = new Intent(context, (Class<?>) CourseCollectEditActivity.class);
        intent.putExtra("course_id", courseId);
        intent.putExtra(CourseDirectoryActivity.EXTRA_DATA_COURSE_TYPE, courseType);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        String stringExtra;
        String stringExtra2;
        Intent intent = getIntent();
        if (intent != null) {
            stringExtra = intent.getStringExtra("course_id");
            stringExtra2 = intent.getStringExtra(CourseDirectoryActivity.EXTRA_DATA_COURSE_TYPE);
        } else {
            stringExtra = "";
            stringExtra2 = "";
        }
        CourseDirectoryCollectEditFragment courseDirectoryCollectEditFragment = new CourseDirectoryCollectEditFragment();
        Bundle bundle = new Bundle();
        bundle.putString("course_id", stringExtra);
        bundle.putString(CourseDirectoryActivity.EXTRA_DATA_COURSE_TYPE, stringExtra2);
        courseDirectoryCollectEditFragment.setArguments(bundle);
        if (findFragment(CourseDirectoryCollectEditFragment.class) == null) {
            loadRootFragment(R.id.fragmentCollect, courseDirectoryCollectEditFragment);
        } else {
            replaceFragment(courseDirectoryCollectEditFragment, false);
        }
        courseDirectoryCollectEditFragment.setClickBack(new CourseDirectoryCollectEditFragment.OnClickBack() { // from class: com.psychiatrygarden.activity.courselist.f
            @Override // com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryCollectEditFragment.OnClickBack
            public final void clickBack() {
                this.f11907a.finish();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_collect_edit);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
