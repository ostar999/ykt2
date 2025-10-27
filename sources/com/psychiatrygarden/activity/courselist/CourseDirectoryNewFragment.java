package com.psychiatrygarden.activity.courselist;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.courselist.CourseDirectoryNewFragment;
import com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity;
import com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryListFragment;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CourseDirectoryItemData;
import com.psychiatrygarden.event.CollectEvent;
import com.psychiatrygarden.event.RefreshCourseDownloadedEvent;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.fragmenthome.CourseDownloadManageFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.glideUtil.util.Utils;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\fH\u0002J\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\fH\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0014J\b\u0010 \u001a\u00020\u0019H\u0002J\b\u0010!\u001a\u00020\u0019H\u0002J\u001a\u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&H\u0014J\u0010\u0010'\u001a\u00020\u00192\u0006\u0010(\u001a\u00020)H\u0007J\u0012\u0010'\u001a\u00020\u00192\b\u0010*\u001a\u0004\u0018\u00010+H\u0007J\b\u0010,\u001a\u00020\u0019H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lcom/psychiatrygarden/activity/courselist/CourseDirectoryNewFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "()V", "appBarLayout", "Lcom/google/android/material/appbar/AppBarLayout;", "collectData", "", "Lcom/psychiatrygarden/bean/CourseDirectoryItemData;", "courseId", "", "courseType", "haveCollectTab", "", "haveDownloadData", "haveDownloadTab", "mMagicIndicator", "Lcn/webdemo/com/supporfragment/tablayout/MagicIndicator;", "openTxt", "Landroid/widget/TextView;", "openrel", "Landroid/widget/RelativeLayout;", "updateChapterId", "viewpager", "Landroidx/viewpager/widget/ViewPager;", "changeAppbarState", "", "visible", "getCollectData", "getCollectListData", "notify", "getLayoutId", "", "haveDownload", "initTabColumn", "initViews", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "Landroid/view/View;", "onEventMainThread", AliyunLogKey.KEY_EVENT, "Lcom/psychiatrygarden/event/CollectEvent;", NotificationCompat.CATEGORY_EVENT, "Lcom/psychiatrygarden/event/RefreshCourseDownloadedEvent;", "shrink", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CourseDirectoryNewFragment extends BaseFragment {
    private AppBarLayout appBarLayout;
    private boolean haveCollectTab;
    private boolean haveDownloadData;
    private boolean haveDownloadTab;
    private MagicIndicator mMagicIndicator;
    private TextView openTxt;
    private RelativeLayout openrel;
    private ViewPager viewpager;

    @Nullable
    private String courseType = "";

    @Nullable
    private String courseId = "";

    @Nullable
    private String updateChapterId = "";

    @NotNull
    private List<? extends CourseDirectoryItemData> collectData = new ArrayList();

    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/activity/courselist/CourseDirectoryNewFragment$initTabColumn$1", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerIndicator;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "getTitleView", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerTitleView;", "index", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.courselist.CourseDirectoryNewFragment$initTabColumn$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05821 extends CommonNavigatorAdapter {
        final /* synthetic */ List<String> $dataNewList;
        final /* synthetic */ CourseDirectoryNewFragment this$0;

        public C05821(List<String> list, CourseDirectoryNewFragment courseDirectoryNewFragment) {
            this.$dataNewList = list;
            this.this$0 = courseDirectoryNewFragment;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void getTitleView$lambda$0(CourseDirectoryNewFragment this$0, int i2, List dataNewList, View view) throws Resources.NotFoundException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(dataNewList, "$dataNewList");
            ViewPager viewPager = this$0.viewpager;
            if (viewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                viewPager = null;
            }
            viewPager.setCurrentItem(i2);
            boolean z2 = i2 == dataNewList.size() - 1 && this$0.haveDownloadData;
            if (this$0.getActivity() instanceof CourseDirectoryActivity) {
                FragmentActivity activity = this$0.getActivity();
                Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity");
                ((CourseDirectoryActivity) activity).updateEditShow(z2);
            }
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return this.$dataNewList.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @Nullable
        public IPagerIndicator getIndicator(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @NotNull
        public IPagerTitleView getTitleView(@NotNull final Context context, final int index) {
            Intrinsics.checkNotNullParameter(context, "context");
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(this.this$0.getActivity());
            commonPagerTitleView.setContentView(R.layout.item_forum_tab);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.item_question_column);
            textView.setText(this.$dataNewList.get(index));
            final CourseDirectoryNewFragment courseDirectoryNewFragment = this.this$0;
            final List<String> list = this.$dataNewList;
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.o
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    CourseDirectoryNewFragment.C05821.getTitleView$lambda$0(courseDirectoryNewFragment, index, list, view);
                }
            });
            final CourseDirectoryNewFragment courseDirectoryNewFragment2 = this.this$0;
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.courselist.CourseDirectoryNewFragment$initTabColumn$1$getTitleView$2
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(SkinManager.getThemeColor(context, R.attr.new_bg_two_color));
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(courseDirectoryNewFragment2.requireContext(), 8.0f));
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.first_txt_color));
                    textView.setBackground(gradientDrawable);
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onEnter(int index2, int totalCount, float enterPercent, boolean leftToRight) {
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onLeave(int index2, int totalCount, float leavePercent, boolean leftToRight) {
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onSelected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT_BOLD);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(SkinManager.getThemeColor(context, R.attr.main_theme_five_deep_color));
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(courseDirectoryNewFragment2.requireContext(), 8.0f));
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.main_theme_color));
                    textView.setBackground(gradientDrawable);
                }
            });
            return commonPagerTitleView;
        }
    }

    private final void changeAppbarState(boolean visible) {
        AppBarLayout appBarLayout = this.appBarLayout;
        AppBarLayout appBarLayout2 = null;
        if (appBarLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appBarLayout");
            appBarLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = appBarLayout.getLayoutParams();
        if (this.viewpager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
        }
        if (visible) {
            layoutParams.height = -2;
            ViewPager viewPager = this.viewpager;
            if (viewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                viewPager = null;
            }
            ViewGroup.LayoutParams layoutParams2 = viewPager.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams2, "null cannot be cast to non-null type androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams");
            CoordinatorLayout.LayoutParams layoutParams3 = (CoordinatorLayout.LayoutParams) layoutParams2;
            layoutParams3.setBehavior(new AppBarLayout.ScrollingViewBehavior());
            ViewPager viewPager2 = this.viewpager;
            if (viewPager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                viewPager2 = null;
            }
            viewPager2.setLayoutParams(layoutParams3);
        } else {
            layoutParams.height = 0;
            ViewPager viewPager3 = this.viewpager;
            if (viewPager3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                viewPager3 = null;
            }
            ViewGroup.LayoutParams layoutParams4 = viewPager3.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams4, "null cannot be cast to non-null type androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams");
            CoordinatorLayout.LayoutParams layoutParams5 = (CoordinatorLayout.LayoutParams) layoutParams4;
            layoutParams5.setBehavior(null);
            ViewPager viewPager4 = this.viewpager;
            if (viewPager4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                viewPager4 = null;
            }
            viewPager4.setLayoutParams(layoutParams5);
        }
        AppBarLayout appBarLayout3 = this.appBarLayout;
        if (appBarLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appBarLayout");
            appBarLayout3 = null;
        }
        appBarLayout3.setLayoutParams(layoutParams);
        AppBarLayout appBarLayout4 = this.appBarLayout;
        if (appBarLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appBarLayout");
        } else {
            appBarLayout2 = appBarLayout4;
        }
        appBarLayout2.setVisibility(visible ? 0 : 8);
    }

    private final void getCollectListData(final boolean notify) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("type", this.courseType);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseDirectoryCollect, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.CourseDirectoryNewFragment.getCollectListData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) throws Resources.NotFoundException {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                CourseDirectoryNewFragment.this.collectData = new ArrayList();
                CourseDirectoryNewFragment.this.initTabColumn();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) throws Resources.NotFoundException {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass1) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                        List arrayList = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends CourseDirectoryItemData>>() { // from class: com.psychiatrygarden.activity.courselist.CourseDirectoryNewFragment$getCollectListData$1$onSuccess$dateBeanList$1
                        }.getType());
                        CourseDirectoryNewFragment courseDirectoryNewFragment = CourseDirectoryNewFragment.this;
                        if (arrayList == null || arrayList.isEmpty()) {
                            arrayList = new ArrayList();
                        }
                        courseDirectoryNewFragment.collectData = arrayList;
                    } else {
                        CourseDirectoryNewFragment.this.collectData = new ArrayList();
                    }
                } catch (Exception e2) {
                    System.out.println((Object) ("ErrorTag:" + e2.getMessage()));
                    CourseDirectoryNewFragment.this.collectData = new ArrayList();
                }
                if (notify && CourseDirectoryNewFragment.this.haveCollectTab && (!CourseDirectoryNewFragment.this.collectData.isEmpty())) {
                    EventBus.getDefault().post(EventBusConstant.DIRECTORY_TAB_UPDATE_COLLECT);
                } else {
                    CourseDirectoryNewFragment.this.initTabColumn();
                }
            }
        });
    }

    private final void haveDownload() {
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.j
            @Override // java.lang.Runnable
            public final void run() {
                CourseDirectoryNewFragment.haveDownload$lambda$5(this.f12102c);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void haveDownload$lambda$5(final CourseDirectoryNewFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            List<VideoDownBean> videoDownLoadInfo = ProjectApp.database.getVideoDownDao().getVideoDownLoadInfo("course_" + this$0.courseId);
            this$0.haveDownloadData = videoDownLoadInfo == null || videoDownLoadInfo.isEmpty() ? false : true;
            Log.d("downloadData", "haveDownload: -- " + this$0.haveDownloadData);
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.n
                @Override // java.lang.Runnable
                public final void run() {
                    CourseDirectoryNewFragment.haveDownload$lambda$5$lambda$4(this.f12124c);
                }
            });
        } catch (Exception e2) {
            System.out.println((Object) ("ErrorTag: " + e2.getMessage()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void haveDownload$lambda$5$lambda$4(CourseDirectoryNewFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.getActivity() instanceof CourseDirectoryActivity) {
            FragmentActivity activity = this$0.getActivity();
            Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity");
            ((CourseDirectoryActivity) activity).updateEditShow(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initTabColumn() throws Resources.NotFoundException {
        final boolean zIsEmpty = this.collectData.isEmpty();
        ArrayList arrayList = new ArrayList();
        arrayList.add("全部");
        if (!zIsEmpty) {
            arrayList.add("我的收藏");
        }
        if (this.haveDownloadData) {
            arrayList.add("我下载的");
        }
        ArrayList arrayList2 = new ArrayList();
        Bundle bundle = new Bundle();
        bundle.putString(CourseDirectoryActivity.EXTRA_DATA_COURSE_TYPE, this.courseType);
        bundle.putString("course_id", this.courseId);
        bundle.putString(CourseDirectoryActivity.EXTRA_DATA_COURSE_UPDATE_CHAPTER_ID, this.updateChapterId);
        bundle.putString(CourseDirectoryListFragment.EXTRA_DATA_PAGE_TYPE, "0");
        arrayList2.add(new BaseViewPagerAdapter.PagerInfo((String) arrayList.get(0), CourseDirectoryListFragment.class, bundle));
        if (zIsEmpty) {
            this.haveCollectTab = false;
        } else {
            Bundle bundle2 = new Bundle();
            bundle2.putString(CourseDirectoryActivity.EXTRA_DATA_COURSE_TYPE, this.courseType);
            bundle2.putString("course_id", this.courseId);
            bundle2.putString(CourseDirectoryActivity.EXTRA_DATA_COURSE_UPDATE_CHAPTER_ID, this.updateChapterId);
            bundle2.putString(CourseDirectoryListFragment.EXTRA_DATA_COLLECT, new Gson().toJson(this.collectData));
            bundle2.putString(CourseDirectoryListFragment.EXTRA_DATA_PAGE_TYPE, "1");
            arrayList2.add(new BaseViewPagerAdapter.PagerInfo((String) arrayList.get(1), CourseDirectoryListFragment.class, bundle2));
            this.haveCollectTab = true;
        }
        if (this.haveDownloadData) {
            Bundle bundle3 = new Bundle();
            bundle3.putString("course_id", this.courseId);
            bundle3.putString("courseTitle", "");
            bundle3.putString(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "");
            bundle3.putBoolean("fromCourseDetail", false);
            bundle3.putBoolean("manage", true);
            bundle3.putBoolean("fromCourseDirectory", true);
            arrayList2.add(new BaseViewPagerAdapter.PagerInfo((String) arrayList.get(arrayList.size() - 1), CourseDownloadManageFragment.class, bundle3));
            this.haveDownloadTab = true;
        } else {
            this.haveDownloadTab = false;
        }
        if (this.haveCollectTab || this.haveDownloadTab) {
            changeAppbarState(true);
        } else {
            changeAppbarState(false);
        }
        if (isAdded()) {
            BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), arrayList2);
            ViewPager viewPager = this.viewpager;
            TextView textView = null;
            if (viewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                viewPager = null;
            }
            viewPager.setAdapter(baseViewPagerAdapter);
            CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
            commonNavigator.setSkimOver(true);
            commonNavigator.setAdapter(new C05821(arrayList, this));
            MagicIndicator magicIndicator = this.mMagicIndicator;
            if (magicIndicator == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMagicIndicator");
                magicIndicator = null;
            }
            magicIndicator.setNavigator(commonNavigator);
            MagicIndicator magicIndicator2 = this.mMagicIndicator;
            if (magicIndicator2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMagicIndicator");
                magicIndicator2 = null;
            }
            ViewPager viewPager2 = this.viewpager;
            if (viewPager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                viewPager2 = null;
            }
            ViewPagerHelper.bind(magicIndicator2, viewPager2);
            ViewPager viewPager3 = this.viewpager;
            if (viewPager3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                viewPager3 = null;
            }
            viewPager3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.courselist.CourseDirectoryNewFragment.initTabColumn.2
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int state) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int position) {
                    TextView textView2 = null;
                    if (position == 0) {
                        EventBus.getDefault().post(EventBusConstant.DIRECTORY_TAB_SELECT_ALL);
                        TextView textView3 = CourseDirectoryNewFragment.this.openTxt;
                        if (textView3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("openTxt");
                        } else {
                            textView2 = textView3;
                        }
                        textView2.setText("全部");
                        return;
                    }
                    if (position != 1) {
                        if (position != 2) {
                            return;
                        }
                        EventBus.getDefault().post(EventBusConstant.DIRECTORY_TAB_SELECT_DOWNLOAD);
                        TextView textView4 = CourseDirectoryNewFragment.this.openTxt;
                        if (textView4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("openTxt");
                        } else {
                            textView2 = textView4;
                        }
                        textView2.setText("我下载的");
                        if (CourseDirectoryNewFragment.this.getActivity() instanceof CourseDirectoryActivity) {
                            FragmentActivity activity = CourseDirectoryNewFragment.this.getActivity();
                            Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity");
                            ((CourseDirectoryActivity) activity).updateEditShow(CourseDirectoryNewFragment.this.haveDownloadData);
                            return;
                        }
                        return;
                    }
                    if (!zIsEmpty) {
                        EventBus.getDefault().post(EventBusConstant.DIRECTORY_TAB_SELECT_COLLECT);
                        TextView textView5 = CourseDirectoryNewFragment.this.openTxt;
                        if (textView5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("openTxt");
                        } else {
                            textView2 = textView5;
                        }
                        textView2.setText("我的收藏");
                        return;
                    }
                    EventBus.getDefault().post(EventBusConstant.DIRECTORY_TAB_SELECT_DOWNLOAD);
                    TextView textView6 = CourseDirectoryNewFragment.this.openTxt;
                    if (textView6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("openTxt");
                    } else {
                        textView2 = textView6;
                    }
                    textView2.setText("我下载的");
                    if (CourseDirectoryNewFragment.this.getActivity() instanceof CourseDirectoryActivity) {
                        FragmentActivity activity2 = CourseDirectoryNewFragment.this.getActivity();
                        Intrinsics.checkNotNull(activity2, "null cannot be cast to non-null type com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity");
                        ((CourseDirectoryActivity) activity2).updateEditShow(CourseDirectoryNewFragment.this.haveDownloadData);
                    }
                }
            });
            ViewPager viewPager4 = this.viewpager;
            if (viewPager4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                viewPager4 = null;
            }
            viewPager4.setOffscreenPageLimit(2);
            EventBus.getDefault().post(EventBusConstant.DIRECTORY_TAB_SELECT_ALL);
            TextView textView2 = this.openTxt;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("openTxt");
            } else {
                textView = textView2;
            }
            textView.setText("全部");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$1(CourseDirectoryNewFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AppBarLayout appBarLayout = this$0.appBarLayout;
        if (appBarLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appBarLayout");
            appBarLayout = null;
        }
        appBarLayout.setExpanded(true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$2(CourseDirectoryNewFragment this$0, AppBarLayout appBarLayout, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(appBarLayout, "appBarLayout");
        MagicIndicator magicIndicator = this$0.mMagicIndicator;
        RelativeLayout relativeLayout = null;
        if (magicIndicator == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMagicIndicator");
            magicIndicator = null;
        }
        double d3 = 1;
        float f2 = i2 * 1.0f;
        magicIndicator.setAlpha((float) (d3 - Math.abs(f2 / appBarLayout.getTotalScrollRange())));
        if (!(((float) (d3 - Math.abs((double) (f2 / ((float) appBarLayout.getTotalScrollRange()))))) == 0.0f)) {
            RelativeLayout relativeLayout2 = this$0.openrel;
            if (relativeLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("openrel");
            } else {
                relativeLayout = relativeLayout2;
            }
            relativeLayout.setVisibility(8);
            return;
        }
        RelativeLayout relativeLayout3 = this$0.openrel;
        if (relativeLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("openrel");
        } else {
            relativeLayout = relativeLayout3;
        }
        if (relativeLayout.getVisibility() == 8) {
            this$0.shrink();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onEventMainThread$lambda$3(CourseDirectoryNewFragment this$0) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        boolean z2 = this$0.haveDownloadTab;
        if (!z2 && !this$0.haveDownloadData) {
            EventBus.getDefault().post(EventBusConstant.DIRECTORY_UPDATE_FRAGMENT_DATA);
        } else if (this$0.haveDownloadData && z2) {
            EventBus.getDefault().post(EventBusConstant.DIRECTORY_UPDATE_FRAGMENT_DATA);
        } else {
            this$0.initTabColumn();
        }
    }

    private final void shrink() {
        RelativeLayout relativeLayout = this.openrel;
        RelativeLayout relativeLayout2 = null;
        if (relativeLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("openrel");
            relativeLayout = null;
        }
        relativeLayout.setVisibility(0);
        RelativeLayout relativeLayout3 = this.openrel;
        if (relativeLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("openrel");
        } else {
            relativeLayout2 = relativeLayout3;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(relativeLayout2, "translationY", Utils.dp2px(getContext(), 40.0f), 0.0f);
        objectAnimatorOfFloat.setDuration(300L);
        objectAnimatorOfFloat.setInterpolator(new OvershootInterpolator());
        objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.activity.courselist.CourseDirectoryNewFragment.shrink.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(@NotNull Animator animator) {
                Intrinsics.checkNotNullParameter(animator, "animator");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(@NotNull Animator animator) {
                Intrinsics.checkNotNullParameter(animator, "animator");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(@NotNull Animator animator) {
                Intrinsics.checkNotNullParameter(animator, "animator");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(@NotNull Animator animator) {
                Intrinsics.checkNotNullParameter(animator, "animator");
            }
        });
        objectAnimatorOfFloat.start();
    }

    @NotNull
    public final List<CourseDirectoryItemData> getCollectData() {
        return this.collectData;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_course_directory_new;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @Nullable View root) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.courseType = arguments.getString(CourseDirectoryActivity.EXTRA_DATA_COURSE_TYPE);
            this.courseId = arguments.getString("course_id");
            this.updateChapterId = arguments.getString(CourseDirectoryActivity.EXTRA_DATA_COURSE_UPDATE_CHAPTER_ID);
        }
        View view = holder.get(R.id.magicIndicator);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.magicIndicator)");
        this.mMagicIndicator = (MagicIndicator) view;
        View view2 = holder.get(R.id.newViewPage);
        Intrinsics.checkNotNullExpressionValue(view2, "holder.get(R.id.newViewPage)");
        this.viewpager = (ViewPager) view2;
        View view3 = holder.get(R.id.appBarLayout);
        Intrinsics.checkNotNullExpressionValue(view3, "holder.get(R.id.appBarLayout)");
        this.appBarLayout = (AppBarLayout) view3;
        View view4 = holder.get(R.id.opentxt);
        Intrinsics.checkNotNullExpressionValue(view4, "holder.get(R.id.opentxt)");
        this.openTxt = (TextView) view4;
        View view5 = holder.get(R.id.openrel);
        Intrinsics.checkNotNullExpressionValue(view5, "holder.get(R.id.openrel)");
        RelativeLayout relativeLayout = (RelativeLayout) view5;
        this.openrel = relativeLayout;
        AppBarLayout appBarLayout = null;
        if (relativeLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("openrel");
            relativeLayout = null;
        }
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view6) {
                CourseDirectoryNewFragment.initViews$lambda$1(this.f12113c, view6);
            }
        });
        AppBarLayout appBarLayout2 = this.appBarLayout;
        if (appBarLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appBarLayout");
        } else {
            appBarLayout = appBarLayout2;
        }
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.courselist.m
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout3, int i2) {
                CourseDirectoryNewFragment.initViews$lambda$2(this.f12117a, appBarLayout3, i2);
            }
        });
        haveDownload();
        getCollectListData(false);
    }

    @Subscribe
    public final void onEventMainThread(@NotNull CollectEvent e2) {
        Intrinsics.checkNotNullParameter(e2, "e");
        if (Intrinsics.areEqual(e2.getCourseId(), this.courseId)) {
            getCollectListData(true);
        }
    }

    @Subscribe
    public final void onEventMainThread(@Nullable RefreshCourseDownloadedEvent event) {
        try {
            haveDownload();
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.k
                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException {
                    CourseDirectoryNewFragment.onEventMainThread$lambda$3(this.f12109c);
                }
            }, 300L);
        } catch (Exception e2) {
            System.out.println((Object) ("ErrorTag:" + e2.getMessage()));
        }
    }
}
