package com.psychiatrygarden.activity.coupon;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.coupon.CouponRecordActivity;
import com.psychiatrygarden.activity.coupon.fragment.CouponRecordFragment;
import com.psychiatrygarden.activity.coupon.fragment.RedPacketRecordFragment;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.GoodsSimplePagerTitleView;
import com.umeng.analytics.pro.d;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 \u00132\u00020\u00012\u00020\u0002:\u0001\u0013B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\tH\u0002J\u0010\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0012\u0010\u000e\u001a\u00020\t2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\b\u0010\u0011\u001a\u00020\tH\u0016J\b\u0010\u0012\u001a\u00020\tH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/psychiatrygarden/activity/coupon/CouponRecordActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "Landroid/view/View$OnClickListener;", "()V", "couponMagicIndicator", "Lcn/webdemo/com/supporfragment/tablayout/MagicIndicator;", "couponViewPage", "Landroidx/viewpager/widget/ViewPager;", "init", "", "initViewPage", "onClick", "v", "Landroid/view/View;", "onEventMainThread", "str", "", "setContentView", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CouponRecordActivity extends BaseActivity implements View.OnClickListener {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private MagicIndicator couponMagicIndicator;
    private ViewPager couponViewPage;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/activity/coupon/CouponRecordActivity$Companion;", "", "()V", "gotoCouponRecordActivity", "", d.R, "Landroid/content/Context;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void gotoCouponRecordActivity(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.startActivity(new Intent(context, (Class<?>) CouponRecordActivity.class));
        }
    }

    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/activity/coupon/CouponRecordActivity$initViewPage$2", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerIndicator;", d.R, "Landroid/content/Context;", "getTitleView", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerTitleView;", "index", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.coupon.CouponRecordActivity$initViewPage$2, reason: invalid class name */
    public static final class AnonymousClass2 extends CommonNavigatorAdapter {
        final /* synthetic */ List<String> $tabTitles;
        final /* synthetic */ CouponRecordActivity this$0;

        public AnonymousClass2(List<String> list, CouponRecordActivity couponRecordActivity) {
            this.$tabTitles = list;
            this.this$0 = couponRecordActivity;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void getTitleView$lambda$0(CouponRecordActivity this$0, int i2, View view) throws Resources.NotFoundException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            ViewPager viewPager = this$0.couponViewPage;
            if (viewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("couponViewPage");
                viewPager = null;
            }
            viewPager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return this.$tabTitles.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @NotNull
        public IPagerIndicator getIndicator(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            linePagerIndicator.setMode(2);
            linePagerIndicator.setLineHeight(UIUtil.dip2px(context, 3.0d));
            linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 16.0d));
            linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 3.0d));
            linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
            linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
            linePagerIndicator.setYOffset(0.0f);
            if (SkinManager.getCurrentSkinType(this.this$0.mContext) == 1) {
                linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(this.this$0, R.color.main_theme_color_night)));
            } else {
                linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(this.this$0, R.color.main_theme_color)));
            }
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @NotNull
        public IPagerTitleView getTitleView(@NotNull Context context, final int index) {
            Intrinsics.checkNotNullParameter(context, "context");
            final CouponRecordActivity couponRecordActivity = this.this$0;
            GoodsSimplePagerTitleView goodsSimplePagerTitleView = new GoodsSimplePagerTitleView(couponRecordActivity) { // from class: com.psychiatrygarden.activity.coupon.CouponRecordActivity$initViewPage$2$getTitleView$titleView$1
                @Override // com.psychiatrygarden.widget.GoodsSimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
                public void onDeselected(int index2, int totalCount) {
                    super.onDeselected(index2, totalCount);
                    setTextSize(2, 14.0f);
                }

                @Override // com.psychiatrygarden.widget.GoodsSimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
                public void onSelected(int index2, int totalCount) {
                    super.onSelected(index2, totalCount);
                    setTextSize(2, 16.0f);
                }
            };
            int themeColor = SkinManager.getThemeColor(this.this$0, R.attr.first_txt_color);
            goodsSimplePagerTitleView.setNormalColor(SkinManager.getThemeColor(this.this$0, R.attr.third_txt_color));
            goodsSimplePagerTitleView.setSelectedColor(themeColor);
            final CouponRecordActivity couponRecordActivity2 = this.this$0;
            goodsSimplePagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.coupon.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    CouponRecordActivity.AnonymousClass2.getTitleView$lambda$0(couponRecordActivity2, index, view);
                }
            });
            goodsSimplePagerTitleView.setText(this.$tabTitles.get(index));
            return goodsSimplePagerTitleView;
        }
    }

    private final void initViewPage() throws Resources.NotFoundException {
        List listMutableListOf = CollectionsKt__CollectionsKt.mutableListOf("优惠券", "红包");
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BaseViewPagerAdapter.PagerInfo((String) listMutableListOf.get(0), CouponRecordFragment.class, new Bundle()));
        arrayList.add(new BaseViewPagerAdapter.PagerInfo((String) listMutableListOf.get(1), RedPacketRecordFragment.class, new Bundle()));
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), arrayList);
        ViewPager viewPager = this.couponViewPage;
        ViewPager viewPager2 = null;
        if (viewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("couponViewPage");
            viewPager = null;
        }
        viewPager.setAdapter(baseViewPagerAdapter);
        ViewPager viewPager3 = this.couponViewPage;
        if (viewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("couponViewPage");
            viewPager3 = null;
        }
        viewPager3.setCurrentItem(0);
        ViewPager viewPager4 = this.couponViewPage;
        if (viewPager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("couponViewPage");
            viewPager4 = null;
        }
        viewPager4.setOffscreenPageLimit(1);
        ViewPager viewPager5 = this.couponViewPage;
        if (viewPager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("couponViewPage");
            viewPager5 = null;
        }
        viewPager5.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.coupon.CouponRecordActivity.initViewPage.1
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
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new AnonymousClass2(listMutableListOf, this));
        commonNavigator.setAdjustMode(true);
        MagicIndicator magicIndicator = this.couponMagicIndicator;
        if (magicIndicator == null) {
            Intrinsics.throwUninitializedPropertyAccessException("couponMagicIndicator");
            magicIndicator = null;
        }
        magicIndicator.setNavigator(commonNavigator);
        MagicIndicator magicIndicator2 = this.couponMagicIndicator;
        if (magicIndicator2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("couponMagicIndicator");
            magicIndicator2 = null;
        }
        ViewPager viewPager6 = this.couponViewPage;
        if (viewPager6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("couponViewPage");
        } else {
            viewPager2 = viewPager6;
        }
        ViewPagerHelper.bind(magicIndicator2, viewPager2);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        View viewFindViewById = findViewById(R.id.couponViewPage);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.couponViewPage)");
        this.couponViewPage = (ViewPager) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.couponMagicIndicator);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.couponMagicIndicator)");
        this.couponMagicIndicator = (MagicIndicator) viewFindViewById2;
        initViewPage();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(@NotNull View v2) {
        Intrinsics.checkNotNullParameter(v2, "v");
        if (v2.getId() == R.id.imgCouponBack) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_coupon_record);
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        ((ImageView) findViewById(R.id.imgCouponBack)).setOnClickListener(this);
    }
}
