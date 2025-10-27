package com.psychiatrygarden.activity.courselist;

import android.os.Bundle;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.fragment.CourseDoubleChapterFragment;
import com.psychiatrygarden.activity.courselist.fragment.CourseNoNeChapterFragment;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class CourseToConfigureActivity extends BaseActivity {
    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle(getIntent().getExtras().getString("title"));
        Bundle bundle = new Bundle();
        bundle.putString("vidteaching_id", getIntent().getExtras().getString("vidteaching_id"));
        bundle.putString("type", getIntent().getExtras().getString("type"));
        bundle.putString("series", "" + getIntent().getExtras().getString("series"));
        bundle.putString("chapter_id", "0");
        bundle.putString("parent_id", "0");
        if (getIntent().getExtras().getString("series").equals("2")) {
            if (findFragment(CourseDoubleChapterFragment.class) == null) {
                CourseDoubleChapterFragment courseDoubleChapterFragment = new CourseDoubleChapterFragment();
                courseDoubleChapterFragment.setArguments(bundle);
                loadRootFragment(R.id.course_ctc, courseDoubleChapterFragment);
                return;
            }
            return;
        }
        if (getIntent().getExtras().getString("series").equals("1")) {
            if (findFragment(CourseDoubleChapterFragment.class) == null) {
                CourseDoubleChapterFragment courseDoubleChapterFragment2 = new CourseDoubleChapterFragment();
                courseDoubleChapterFragment2.setArguments(bundle);
                loadRootFragment(R.id.course_ctc, courseDoubleChapterFragment2);
                return;
            }
            return;
        }
        if (findFragment(CourseNoNeChapterFragment.class) == null) {
            CourseNoNeChapterFragment courseNoNeChapterFragment = new CourseNoNeChapterFragment();
            courseNoNeChapterFragment.setArguments(bundle);
            loadRootFragment(R.id.course_ctc, courseNoNeChapterFragment);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_ctc);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
