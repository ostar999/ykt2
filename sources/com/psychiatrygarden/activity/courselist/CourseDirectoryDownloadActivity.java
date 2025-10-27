package com.psychiatrygarden.activity.courselist;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryEditListFragment;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.yikaobang.yixue.R;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Deprecated(message = "没用到，使用方老师下载管理里面的下载界面")
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016J\u0010\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0012\u0010\u0015\u001a\u00020\u00102\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\u0012\u0010\u0018\u001a\u00020\u00102\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0010H\u0002J\b\u0010\u001c\u001a\u00020\u0010H\u0016J\b\u0010\u001d\u001a\u00020\u0010H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/psychiatrygarden/activity/courselist/CourseDirectoryDownloadActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "Landroid/view/View$OnClickListener;", "()V", "downTv", "Landroid/widget/TextView;", "fragment", "Lcom/psychiatrygarden/activity/courselist/fragment/CourseDirectoryEditListFragment;", "fragmentLayout", "Landroid/widget/FrameLayout;", "imgBack", "Landroid/widget/ImageView;", "tvAllSelect", "tvSelectAll", "tvTitle", "init", "", "initwriteStatusBar", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onEventMainThread", "str", "", "selectAll", "setContentView", "setListenerForWidget", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CourseDirectoryDownloadActivity extends BaseActivity implements View.OnClickListener {
    private TextView downTv;
    private CourseDirectoryEditListFragment fragment;
    private FrameLayout fragmentLayout;
    private ImageView imgBack;
    private TextView tvAllSelect;
    private TextView tvSelectAll;
    private TextView tvTitle;

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(CourseDirectoryDownloadActivity this$0, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TextView textView = this$0.tvTitle;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
            textView = null;
        }
        textView.setText("已选择" + i2 + "个文件");
        TextView textView3 = this$0.downTv;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("downTv");
        } else {
            textView2 = textView3;
        }
        textView2.setEnabled(i2 > 0);
    }

    private final void selectAll() {
        CourseDirectoryEditListFragment courseDirectoryEditListFragment = this.fragment;
        if (courseDirectoryEditListFragment == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fragment");
            courseDirectoryEditListFragment = null;
        }
        courseDirectoryEditListFragment.selectOperaAll(Boolean.TRUE);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        View viewFindViewById = findViewById(R.id.iv_download_back);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.iv_download_back)");
        this.imgBack = (ImageView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.tv_download_directory_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.tv_download_directory_title)");
        this.tvTitle = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.tv_download_all_select);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.tv_download_all_select)");
        this.tvAllSelect = (TextView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.fragmentDownloadDirectory);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.fragmentDownloadDirectory)");
        this.fragmentLayout = (FrameLayout) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.tv_download_all_select);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.tv_download_all_select)");
        this.tvSelectAll = (TextView) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.downTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.downTv)");
        this.downTv = (TextView) viewFindViewById6;
        TextView textView = this.tvSelectAll;
        CourseDirectoryEditListFragment courseDirectoryEditListFragment = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSelectAll");
            textView = null;
        }
        textView.setOnClickListener(this);
        ImageView imageView = this.imgBack;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgBack");
            imageView = null;
        }
        imageView.setOnClickListener(this);
        this.fragment = new CourseDirectoryEditListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isLoadCourseSearchUI", true);
        bundle.putString("id", "1");
        CourseDirectoryEditListFragment courseDirectoryEditListFragment2 = this.fragment;
        if (courseDirectoryEditListFragment2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fragment");
            courseDirectoryEditListFragment2 = null;
        }
        courseDirectoryEditListFragment2.setArguments(bundle);
        if (findFragment(CourseDirectoryEditListFragment.class) == null) {
            CourseDirectoryEditListFragment courseDirectoryEditListFragment3 = this.fragment;
            if (courseDirectoryEditListFragment3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("fragment");
                courseDirectoryEditListFragment3 = null;
            }
            loadRootFragment(R.id.fragmentDownloadDirectory, courseDirectoryEditListFragment3);
        } else {
            CourseDirectoryEditListFragment courseDirectoryEditListFragment4 = this.fragment;
            if (courseDirectoryEditListFragment4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("fragment");
                courseDirectoryEditListFragment4 = null;
            }
            replaceFragment(courseDirectoryEditListFragment4, false);
        }
        CourseDirectoryEditListFragment courseDirectoryEditListFragment5 = this.fragment;
        if (courseDirectoryEditListFragment5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fragment");
        } else {
            courseDirectoryEditListFragment = courseDirectoryEditListFragment5;
        }
        courseDirectoryEditListFragment.setSelectCallBack(new CourseDirectoryEditListFragment.SelectCallBack() { // from class: com.psychiatrygarden.activity.courselist.i
            @Override // com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryEditListFragment.SelectCallBack
            public final void selectNum(int i2) {
                CourseDirectoryDownloadActivity.init$lambda$0(this.f12096a, i2);
            }
        });
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

    @Override // android.view.View.OnClickListener
    public void onClick(@NotNull View v2) {
        Intrinsics.checkNotNullParameter(v2, "v");
        int id = v2.getId();
        if (id == R.id.iv_download_back) {
            finish();
        } else {
            if (id != R.id.tv_download_all_select) {
                return;
            }
            selectAll();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        initwriteStatusBar();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_directory_download);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
