package com.psychiatrygarden.activity.courselist;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.fragment.CourseNoNeChapterFragment;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class CourseVideoListActivity extends BaseActivity {
    TextView questionList_tv_title;
    RelativeLayout zhangjieTxt;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle(getIntent().getExtras().getString("subtitle"));
        this.zhangjieTxt = (RelativeLayout) findViewById(R.id.zhangjieTxt);
        this.questionList_tv_title = (TextView) findViewById(R.id.questionList_tv_title);
        if (getIntent().getExtras().getString("childtitle") == null || "".equals(getIntent().getExtras().getString("childtitle"))) {
            this.zhangjieTxt.setVisibility(8);
        } else {
            this.zhangjieTxt.setVisibility(0);
            this.questionList_tv_title.setText(getIntent().getExtras().getString("childtitle") + "");
        }
        Bundle bundle = new Bundle();
        bundle.putString("vidteaching_id", "" + getIntent().getExtras().getString("vidteaching_id"));
        bundle.putString("type", "" + getIntent().getExtras().getString("type"));
        bundle.putString("series", "" + getIntent().getExtras().getString("series"));
        bundle.putString("chapter_id", "" + getIntent().getExtras().getString("chapter_id"));
        bundle.putString("parent_id", "" + getIntent().getExtras().getString("parent_id"));
        if (findFragment(CourseNoNeChapterFragment.class) == null) {
            CourseNoNeChapterFragment courseNoNeChapterFragment = new CourseNoNeChapterFragment();
            courseNoNeChapterFragment.setArguments(bundle);
            loadRootFragment(R.id.course_video, courseNoNeChapterFragment);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_video_list);
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE) || ContextCompat.checkSelfPermission(this.mContext, Permission.WRITE_EXTERNAL_STORAGE) == 0) {
            return;
        }
        new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.courselist.j1
            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
            public final void onConfirm() {
                this.f12107a.lambda$setContentView$0();
            }
        })).show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
