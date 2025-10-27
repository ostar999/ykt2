package com.psychiatrygarden.activity.courselist;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.hjq.permissions.Permission;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.fragment.CourseDoubleChapterFragment;
import com.psychiatrygarden.activity.courselist.fragment.CourseDownLoadFragment;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;

/* loaded from: classes5.dex */
public class CourseDownLoadActivity extends BaseActivity {
    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("下载");
        Bundle bundle = new Bundle();
        bundle.putString("vidteaching_id", "" + getIntent().getExtras().getString("vidteaching_id"));
        bundle.putString("type", "all");
        bundle.putString("series", "" + getIntent().getExtras().getString("series"));
        bundle.putString("chapter_id", "0");
        bundle.putString("parent_id", "0");
        bundle.putString("flag", "0");
        if (getIntent().getExtras().getString("series").equals("2")) {
            if (findFragment(CourseDoubleChapterFragment.class) == null) {
                CourseDoubleChapterFragment courseDoubleChapterFragment = new CourseDoubleChapterFragment();
                courseDoubleChapterFragment.setArguments(bundle);
                loadRootFragment(R.id.dfragment, courseDoubleChapterFragment);
                return;
            }
            return;
        }
        if (getIntent().getExtras().getString("series").equals("1")) {
            if (findFragment(CourseDoubleChapterFragment.class) == null) {
                CourseDoubleChapterFragment courseDoubleChapterFragment2 = new CourseDoubleChapterFragment();
                courseDoubleChapterFragment2.setArguments(bundle);
                loadRootFragment(R.id.dfragment, courseDoubleChapterFragment2);
                return;
            }
            return;
        }
        this.mActionBar.hide();
        if (findFragment(CourseDownLoadFragment.class) == null) {
            CourseDownLoadFragment courseDownLoadFragment = new CourseDownLoadFragment();
            courseDownLoadFragment.setArguments(bundle);
            loadRootFragment(R.id.dfragment, courseDownLoadFragment);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post("cnm");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode && grantResults[0] == -1) {
            boolean zShouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.CAMERA);
            boolean zShouldShowRequestPermissionRationale2 = ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE);
            if (zShouldShowRequestPermissionRationale || zShouldShowRequestPermissionRationale2) {
                return;
            }
            ToastUtil.shortToast(this, "请检查app相机及存储权限是否打开！");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        CommonUtil.copyEncryptedFile(this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_down_load);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
