package com.psychiatrygarden.ranking;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.QuestionCategoryBean;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.ranking.StatisticsMainActivity;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.DataStatisticsInfoDialog;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000  2\u00020\u0001:\u0001 B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0016J\b\u0010\u0015\u001a\u00020\u0013H\u0002J\b\u0010\u0016\u001a\u00020\u0013H\u0016J\u0012\u0010\u0017\u001a\u00020\u00132\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0014J\u0012\u0010\u001a\u001a\u00020\u00132\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0013H\u0014J\b\u0010\u001e\u001a\u00020\u0013H\u0016J\b\u0010\u001f\u001a\u00020\u0013H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/psychiatrygarden/ranking/StatisticsMainActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "isInitTab", "", "ivActionbarBack", "Landroid/widget/ImageView;", "listTabs", "", "Lcom/psychiatrygarden/bean/QuestionCategoryBean;", "mImgQuestion", "magicIndicator", "Lcn/webdemo/com/supporfragment/tablayout/MagicIndicator;", "onlyItemTab", "title", "Landroid/widget/TextView;", "viewPager", "Landroidx/viewpager/widget/ViewPager;", "getTabList", "", "init", "initTab", "initwriteStatusBar", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onEventMainThread", "str", "", "onResume", "setContentView", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nStatisticsMainActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StatisticsMainActivity.kt\ncom/psychiatrygarden/ranking/StatisticsMainActivity\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,291:1\n1855#2,2:292\n1855#2,2:294\n*S KotlinDebug\n*F\n+ 1 StatisticsMainActivity.kt\ncom/psychiatrygarden/ranking/StatisticsMainActivity\n*L\n133#1:292,2\n180#1:294,2\n*E\n"})
/* loaded from: classes6.dex */
public final class StatisticsMainActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    public static final String allTabId = "0";

    @NotNull
    public static final String childId = "0";

    @NotNull
    public static final String childIdOther = "0";
    private boolean isInitTab;
    private ImageView ivActionbarBack;

    @NotNull
    private final List<QuestionCategoryBean> listTabs = new ArrayList();
    private ImageView mImgQuestion;
    private MagicIndicator magicIndicator;
    private boolean onlyItemTab;
    private TextView title;
    private ViewPager viewPager;

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/psychiatrygarden/ranking/StatisticsMainActivity$Companion;", "", "()V", "allTabId", "", "childId", "childIdOther", "navigationToStatisticsMain", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void navigationToStatisticsMain(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.startActivity(new Intent(context, (Class<?>) StatisticsMainActivity.class));
        }
    }

    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/ranking/StatisticsMainActivity$initTab$2", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerIndicator;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "getTitleView", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerTitleView;", "index", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.ranking.StatisticsMainActivity$initTab$2, reason: invalid class name */
    public static final class AnonymousClass2 extends CommonNavigatorAdapter {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void getTitleView$lambda$0(StatisticsMainActivity this$0, int i2, View view) throws Resources.NotFoundException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            ViewPager viewPager = this$0.viewPager;
            if (viewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                viewPager = null;
            }
            viewPager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return StatisticsMainActivity.this.listTabs.size();
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
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(StatisticsMainActivity.this);
            commonPagerTitleView.setContentView(R.layout.item_tabs_zuti);
            commonPagerTitleView.findViewById(R.id.viewStart).setVisibility(index == 0 ? 0 : 8);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_tabs_name);
            final ImageView imageView = (ImageView) commonPagerTitleView.findViewById(R.id.img_choose);
            imageView.setImageResource(R.mipmap.ic_comment_group_tag);
            textView.setText(((QuestionCategoryBean) StatisticsMainActivity.this.listTabs.get(index)).getTitle());
            final StatisticsMainActivity statisticsMainActivity = StatisticsMainActivity.this;
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.a0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    StatisticsMainActivity.AnonymousClass2.getTitleView$lambda$0(statisticsMainActivity, index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.ranking.StatisticsMainActivity$initTab$2$getTitleView$2
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setTextColor(context.getColor(R.color.third_txt_color));
                    textView.setTextSize(14.0f);
                    imageView.setVisibility(4);
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
                    textView.setTextColor(context.getColor(R.color.first_text_color));
                    textView.setTextSize(16.0f);
                    imageView.setVisibility(0);
                }
            });
            return commonPagerTitleView;
        }
    }

    private final void getTabList() throws Resources.NotFoundException {
        int i2;
        this.listTabs.clear();
        List<QuestionCategoryBean> categoryList = CommonUtil.getQuestionCategoryList(this.mContext);
        Intrinsics.checkNotNullExpressionValue(categoryList, "categoryList");
        List<QuestionCategoryBean> list = categoryList;
        if (!list.isEmpty()) {
            i2 = 0;
            for (QuestionCategoryBean questionCategoryBean : categoryList) {
                if (questionCategoryBean.getChildren() != null) {
                    Intrinsics.checkNotNullExpressionValue(questionCategoryBean.getChildren(), "it.children");
                    if (!r6.isEmpty()) {
                        i2++;
                    }
                }
            }
        } else {
            i2 = 0;
        }
        boolean z2 = i2 == 1;
        this.onlyItemTab = z2;
        if (!z2) {
            SelectIdentityBean.DataBean dataBean = new SelectIdentityBean.DataBean();
            dataBean.setTitle("全部");
            dataBean.setType("001");
            dataBean.setId("0");
            List<SelectIdentityBean.DataBean> listMutableListOf = CollectionsKt__CollectionsKt.mutableListOf(dataBean);
            QuestionCategoryBean questionCategoryBean2 = new QuestionCategoryBean();
            questionCategoryBean2.setType("000");
            questionCategoryBean2.setTitle("全部");
            questionCategoryBean2.setId("0");
            questionCategoryBean2.setChildren(listMutableListOf);
            categoryList.add(0, questionCategoryBean2);
        }
        if (!list.isEmpty()) {
            ListIterator<QuestionCategoryBean> listIterator = categoryList.listIterator();
            while (listIterator.hasNext()) {
                QuestionCategoryBean next = listIterator.next();
                if (next.getChildren() == null || next.getChildren().isEmpty()) {
                    listIterator.remove();
                }
            }
            this.listTabs.addAll(categoryList);
            initTab();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(StatisticsMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$1(StatisticsMainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        new XPopup.Builder(this$0).popupAnimation(null).asCustom(new DataStatisticsInfoDialog(this$0, false, "")).show();
    }

    private final void initTab() throws Resources.NotFoundException {
        this.isInitTab = true;
        if (this.listTabs.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (QuestionCategoryBean questionCategoryBean : this.listTabs) {
            Bundle bundle = new Bundle();
            List<QuestionCategoryBean> list = this.listTabs;
            Intrinsics.checkNotNull(list, "null cannot be cast to non-null type java.io.Serializable");
            bundle.putSerializable("tabList", (Serializable) list);
            bundle.putString("id", questionCategoryBean.getId());
            arrayList.add(new BaseViewPagerAdapter.PagerInfo(questionCategoryBean.getTitle(), StatisticsMainFragment.class, bundle));
        }
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), arrayList);
        ViewPager viewPager = this.viewPager;
        ViewPager viewPager2 = null;
        if (viewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager = null;
        }
        viewPager.setAdapter(baseViewPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new AnonymousClass2());
        MagicIndicator magicIndicator = this.magicIndicator;
        if (magicIndicator == null) {
            Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
            magicIndicator = null;
        }
        magicIndicator.setNavigator(commonNavigator);
        MagicIndicator magicIndicator2 = this.magicIndicator;
        if (magicIndicator2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
            magicIndicator2 = null;
        }
        magicIndicator2.setVisibility(this.listTabs.size() == 1 ? 8 : 0);
        MagicIndicator magicIndicator3 = this.magicIndicator;
        if (magicIndicator3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
            magicIndicator3 = null;
        }
        ViewPager viewPager3 = this.viewPager;
        if (viewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager3 = null;
        }
        ViewPagerHelper.bind(magicIndicator3, viewPager3);
        ViewPager viewPager4 = this.viewPager;
        if (viewPager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager4 = null;
        }
        viewPager4.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.ranking.StatisticsMainActivity.initTab.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
            }
        });
        ViewPager viewPager5 = this.viewPager;
        if (viewPager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager5 = null;
        }
        viewPager5.setOffscreenPageLimit(1);
        ViewPager viewPager6 = this.viewPager;
        if (viewPager6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
        } else {
            viewPager2 = viewPager6;
        }
        viewPager2.setCurrentItem(0);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        View viewFindViewById = findViewById(R.id.viewPager);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.viewPager)");
        this.viewPager = (ViewPager) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.magicIndicator);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.magicIndicator)");
        this.magicIndicator = (MagicIndicator) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.iv_actionbar_back);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.iv_actionbar_back)");
        this.ivActionbarBack = (ImageView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.question);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.question)");
        this.mImgQuestion = (ImageView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.title)");
        this.title = (TextView) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.toolbars);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.toolbars)");
        RelativeLayout relativeLayout = (RelativeLayout) viewFindViewById6;
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
        layoutParams2.topMargin = statusBarHeight;
        relativeLayout.setLayoutParams(layoutParams2);
        TextView textView = this.title;
        ImageView imageView = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("title");
            textView = null;
        }
        textView.setText("刷题统计");
        ImageView imageView2 = this.ivActionbarBack;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivActionbarBack");
            imageView2 = null;
        }
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StatisticsMainActivity.init$lambda$0(this.f16227c, view);
            }
        });
        ImageView imageView3 = this.mImgQuestion;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mImgQuestion");
        } else {
            imageView = imageView3;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.z
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StatisticsMainActivity.init$lambda$1(this.f16228c, view);
            }
        });
        getTabList();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initwriteStatusBar() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white_color), 0);
        getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white_color), 0);
        getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        StatusBarCompat.setLightStatusBar(this, true);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() throws Resources.NotFoundException {
        super.onResume();
        if (this.isInitTab) {
            return;
        }
        initTab();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_statistice_main);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
