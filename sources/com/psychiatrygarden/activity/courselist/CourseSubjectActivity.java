package com.psychiatrygarden.activity.courselist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.fragment.CourseSubjectFragment;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class CourseSubjectActivity extends BaseActivity {
    public static final String SPECIAL_ID = "courseSpecialId";
    public static final String SPECIAL_TITLE = "courseSpecialTitle";

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    public static Intent newIntent(Context context, String courseSpecialId, String title) {
        Intent intent = new Intent(context, (Class<?>) CourseSubjectActivity.class);
        intent.putExtra(SPECIAL_ID, courseSpecialId);
        intent.putExtra(SPECIAL_TITLE, title);
        return intent;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        String stringExtra = getIntent().getStringExtra(SPECIAL_ID);
        String stringExtra2 = getIntent().getStringExtra(SPECIAL_TITLE);
        ImageView imageView = (ImageView) findViewById(R.id.courseSubjectActionbarBack);
        TextView textView = (TextView) findViewById(R.id.courseSubjectTitle);
        imageView.setSelected(true);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.i1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12098c.lambda$init$0(view);
            }
        });
        textView.setText(stringExtra2);
        CourseSubjectFragment courseSubjectFragment = new CourseSubjectFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SPECIAL_ID, stringExtra);
        bundle.putString(SPECIAL_TITLE, stringExtra2);
        courseSubjectFragment.setArguments(bundle);
        if (findFragment(CourseSubjectFragment.class) == null) {
            loadRootFragment(R.id.fragmentCourseSubject, courseSubjectFragment);
        } else {
            replaceFragment(courseSubjectFragment, false);
        }
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
        setContentView(R.layout.activity_course_subject);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
