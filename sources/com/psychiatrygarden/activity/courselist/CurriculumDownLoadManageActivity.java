package com.psychiatrygarden.activity.courselist;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.fragmenthome.CourseDownloadManageFragment;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class CurriculumDownLoadManageActivity extends BaseActivity {
    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        if (getIntent().getExtras() != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fl_container, CourseDownloadManageFragment.getInstance(getIntent().getExtras())).commit();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() > 0) {
            Fragment fragment = fragments.get(0);
            if (fragments instanceof CourseDownloadManageFragment) {
                ((CourseDownloadManageFragment) fragment).onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_curriculum_download_manage);
        if (this.mBaseTheme == 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.dominant_color), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.app_bg_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        }
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
