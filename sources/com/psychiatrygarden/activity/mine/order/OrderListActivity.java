package com.psychiatrygarden.activity.mine.order;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.psychiatrygarden.activity.mine.order.OrderListActivity;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.GoodsSimplePagerTitleView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000b\u001a\u00020\fJ\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0002J\u0012\u0010\u0010\u001a\u00020\u000e2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u000eH\u0016J\b\u0010\u0014\u001a\u00020\u000eH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/psychiatrygarden/activity/mine/order/OrderListActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "actionbarBack", "Landroid/widget/ImageView;", "actionbarTitle", "Landroid/widget/TextView;", "mMagicIndicator", "Lcn/webdemo/com/supporfragment/tablayout/MagicIndicator;", "viewpager", "Landroidx/viewpager/widget/ViewPager;", "getCurrentItemIndex", "", "init", "", "initTabColumn", "onEventMainThread", "str", "", "setContentView", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class OrderListActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private ImageView actionbarBack;
    private TextView actionbarTitle;
    private MagicIndicator mMagicIndicator;
    private ViewPager viewpager;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/activity/mine/order/OrderListActivity$Companion;", "", "()V", "goToOrderListActivity", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void goToOrderListActivity(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.startActivity(new Intent(context, (Class<?>) OrderListActivity.class));
        }
    }

    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/activity/mine/order/OrderListActivity$initTabColumn$1", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerIndicator;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "getTitleView", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerTitleView;", "index", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.mine.order.OrderListActivity$initTabColumn$1, reason: invalid class name */
    public static final class AnonymousClass1 extends CommonNavigatorAdapter {
        final /* synthetic */ String[] $titles;
        final /* synthetic */ OrderListActivity this$0;

        public AnonymousClass1(String[] strArr, OrderListActivity orderListActivity) {
            this.$titles = strArr;
            this.this$0 = orderListActivity;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void getTitleView$lambda$0(OrderListActivity this$0, int i2, View view) throws Resources.NotFoundException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            ViewPager viewPager = this$0.viewpager;
            if (viewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                viewPager = null;
            }
            viewPager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return this.$titles.length;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @NotNull
        public IPagerIndicator getIndicator(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            linePagerIndicator.setMode(2);
            linePagerIndicator.setLineHeight(UIUtil.dip2px(context, 4.0d));
            linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 13.0d));
            linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 3.0d));
            linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
            linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
            linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(this.this$0, R.color.app_theme_red)));
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @NotNull
        public IPagerTitleView getTitleView(@NotNull Context context, final int index) {
            OrderListActivity orderListActivity;
            int i2;
            OrderListActivity orderListActivity2;
            int i3;
            Intrinsics.checkNotNullParameter(context, "context");
            final OrderListActivity orderListActivity3 = this.this$0;
            GoodsSimplePagerTitleView goodsSimplePagerTitleView = new GoodsSimplePagerTitleView(orderListActivity3) { // from class: com.psychiatrygarden.activity.mine.order.OrderListActivity$initTabColumn$1$getTitleView$titleView$1
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
            if (SkinManager.getCurrentSkinType(this.this$0.mContext) == 1) {
                orderListActivity = this.this$0;
                i2 = R.color.first_txt_color_night;
            } else {
                orderListActivity = this.this$0;
                i2 = R.color.first_txt_color;
            }
            int color = orderListActivity.getColor(i2);
            if (SkinManager.getCurrentSkinType(this.this$0.mContext) == 1) {
                orderListActivity2 = this.this$0;
                i3 = R.color.third_txt_color_night;
            } else {
                orderListActivity2 = this.this$0;
                i3 = R.color.third_txt_color;
            }
            goodsSimplePagerTitleView.setNormalColor(orderListActivity2.getColor(i3));
            goodsSimplePagerTitleView.setSelectedColor(color);
            final OrderListActivity orderListActivity4 = this.this$0;
            goodsSimplePagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.order.u
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    OrderListActivity.AnonymousClass1.getTitleView$lambda$0(orderListActivity4, index, view);
                }
            });
            goodsSimplePagerTitleView.setText(StringsKt__StringsKt.trim((CharSequence) this.$titles[index]).toString());
            return goodsSimplePagerTitleView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(OrderListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final void initTabColumn() throws Resources.NotFoundException {
        ArrayList arrayList = new ArrayList();
        String[] strArr = {"全部", "待支付", "已支付", "已取消/退款"};
        for (int i2 = 0; i2 < 4; i2++) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", i2);
            arrayList.add(new BaseViewPagerAdapter.PagerInfo(strArr[i2], OrderListFragment.class, bundle));
        }
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), arrayList);
        ViewPager viewPager = this.viewpager;
        ViewPager viewPager2 = null;
        if (viewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
            viewPager = null;
        }
        viewPager.setAdapter(baseViewPagerAdapter);
        ViewPager viewPager3 = this.viewpager;
        if (viewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
            viewPager3 = null;
        }
        viewPager3.setCurrentItem(0);
        ViewPager viewPager4 = this.viewpager;
        if (viewPager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
            viewPager4 = null;
        }
        viewPager4.setOffscreenPageLimit(2);
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setWeights(CollectionsKt__CollectionsKt.mutableListOf(Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(1.5f)));
        commonNavigator.setLeftPadding(0);
        commonNavigator.setRightPadding(0);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new AnonymousClass1(strArr, this));
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
        ViewPager viewPager5 = this.viewpager;
        if (viewPager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
        } else {
            viewPager2 = viewPager5;
        }
        ViewPagerHelper.bind(magicIndicator2, viewPager2);
    }

    public final int getCurrentItemIndex() {
        ViewPager viewPager = this.viewpager;
        if (viewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
            viewPager = null;
        }
        return viewPager.getCurrentItem();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        View viewFindViewById = findViewById(R.id.orderListMagicIndicator);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.orderListMagicIndicator)");
        this.mMagicIndicator = (MagicIndicator) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.orderListViewPage);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.orderListViewPage)");
        this.viewpager = (ViewPager) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.actionbarBack);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.actionbarBack)");
        this.actionbarBack = (ImageView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.actionbarTitle);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.actionbarTitle)");
        TextView textView = (TextView) viewFindViewById4;
        this.actionbarTitle = textView;
        ImageView imageView = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("actionbarTitle");
            textView = null;
        }
        textView.setText("我的订单");
        ImageView imageView2 = this.actionbarBack;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("actionbarBack");
        } else {
            imageView = imageView2;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.order.t
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                OrderListActivity.init$lambda$0(this.f12984c, view);
            }
        });
        initTabColumn();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_order_list);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
