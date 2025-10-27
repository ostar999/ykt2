package com.psychiatrygarden.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import com.hjq.permissions.Permission;
import com.psychiatrygarden.activity.mine.myfile.MyInformationFragment;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.fragmenthome.CoverCourseDownFragment;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CourseDownCacheActivity extends BaseActivity {
    private TextView courseid;
    private TextView infomation;
    private ImageView mImgBack;
    private LinearLayout mLyTab;
    private View mTabbar;
    private TextView mTvRightBar;
    private TextView tv_actionbar2_back;
    private ViewPager viewpage2;
    private boolean isshowBtn = false;
    private List<BaseViewPagerAdapter.PagerInfo> mPagerInfoList = new ArrayList();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) throws Resources.NotFoundException {
        this.viewpage2.setCurrentItem(0, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) throws Resources.NotFoundException {
        this.viewpage2.setCurrentItem(1, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        int currentItem = this.viewpage2.getCurrentItem();
        this.isshowBtn = true;
        if (currentItem != 0 && currentItem == 2) {
            EventBus.getDefault().post("openEdit");
            this.mLyTab.setVisibility(8);
            this.mTabbar.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        this.isshowBtn = false;
        this.tv_actionbar2_back.setVisibility(8);
        this.mImgBack.setVisibility(0);
        EventBus.getDefault().post("cancel");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        finish();
    }

    public void changeTextState(int type) {
        this.courseid.setSelected(type == 0);
        this.infomation.setSelected(type == 1);
        if (type == 2) {
            this.courseid.setBackgroundResource(R.drawable.shape_bg_one_round8);
            this.infomation.setBackgroundResource(R.drawable.shape_bg_one_round8);
            this.mTvRightBar.setVisibility(8);
            this.tv_actionbar2_back.setVisibility(8);
            this.mImgBack.setVisibility(0);
            return;
        }
        if (type != 0) {
            this.infomation.setBackgroundResource(R.drawable.shape_main_color_round8);
            this.courseid.setBackgroundResource(R.drawable.shape_bg_one_round8);
            this.mTvRightBar.setText("编辑");
            this.mTvRightBar.setVisibility(0);
            return;
        }
        this.courseid.setBackgroundResource(R.drawable.shape_main_color_round8);
        this.infomation.setBackgroundResource(R.drawable.shape_bg_one_round8);
        this.mTvRightBar.setText("全选");
        this.mTvRightBar.setVisibility(8);
        if (this.isshowBtn) {
            this.tv_actionbar2_back.setVisibility(0);
            this.mImgBack.setVisibility(8);
        } else {
            this.tv_actionbar2_back.setVisibility(8);
            this.mImgBack.setVisibility(0);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        TextView textView = (TextView) findViewById(R.id.txt_actionbar_title);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mTvRightBar = (TextView) findViewById(R.id.tv_actionbar_right);
        TextView textView2 = (TextView) findViewById(R.id.tv_actionbar2_back);
        this.tv_actionbar2_back = textView2;
        textView2.setText("取消");
        this.courseid = (TextView) findViewById(R.id.courseid);
        this.mLyTab = (LinearLayout) findViewById(R.id.ly_tab);
        this.mTabbar = findViewById(R.id.tabbar);
        this.infomation = (TextView) findViewById(R.id.infomation);
        this.viewpage2 = (ViewPager) findViewById(R.id.viewpage2);
        textView.setText("下载管理");
        this.mTvRightBar.setText("全选");
        changeTextState(0);
        this.courseid.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.h8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                this.f12470c.lambda$init$0(view);
            }
        });
        this.infomation.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.i8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                this.f12500c.lambda$init$1(view);
            }
        });
        this.mTvRightBar.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.j8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12550c.lambda$init$2(view);
            }
        });
        this.tv_actionbar2_back.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.k8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12582c.lambda$init$3(view);
            }
        });
        this.mPagerInfoList.add(new BaseViewPagerAdapter.PagerInfo("", CoverCourseDownFragment.class, new Bundle()));
        this.mPagerInfoList.add(new BaseViewPagerAdapter.PagerInfo("", MyInformationFragment.class, new Bundle()));
        this.viewpage2.setAdapter(new BaseViewPagerAdapter(this, getSupportFragmentManager(), this.mPagerInfoList));
        this.viewpage2.setOffscreenPageLimit(1);
        this.viewpage2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.CourseDownCacheActivity.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                CourseDownCacheActivity.this.changeTextState(position);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if ("okEvent".equals(str)) {
            this.isshowBtn = false;
            this.tv_actionbar2_back.setVisibility(8);
            this.mImgBack.setVisibility(0);
            EventBus.getDefault().post("cancel");
            return;
        }
        if (str.equals("exitEdit")) {
            this.mLyTab.setVisibility(0);
            this.mTabbar.setVisibility(0);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode && grantResults[0] == -1 && !ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE)) {
            ToastUtil.shortToast(this, "无法下载，请检查app存储权限是否打开！");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        CommonUtil.copyEncryptedFile(this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_cache);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.g8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12432c.lambda$setListenerForWidget$4(view);
            }
        });
    }
}
