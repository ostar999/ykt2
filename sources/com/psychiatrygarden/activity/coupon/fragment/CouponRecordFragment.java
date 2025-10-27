package com.psychiatrygarden.activity.coupon.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.psychiatrygarden.activity.coupon.fragment.CouponRecordFragment;
import com.psychiatrygarden.activity.mine.coupons.FragRedEnvelopeCoupons;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0014J\b\u0010\t\u001a\u00020\nH\u0002J\u001a\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014J\u0012\u0010\u0010\u001a\u00020\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/psychiatrygarden/activity/coupon/fragment/CouponRecordFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "()V", "mMagicIndicator", "Lcn/webdemo/com/supporfragment/tablayout/MagicIndicator;", "viewpager", "Landroidx/viewpager/widget/ViewPager;", "getLayoutId", "", "initTabColumn", "", "initViews", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "Landroid/view/View;", "onLazyInitView", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nCouponRecordFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CouponRecordFragment.kt\ncom/psychiatrygarden/activity/coupon/fragment/CouponRecordFragment\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,173:1\n1864#2,3:174\n*S KotlinDebug\n*F\n+ 1 CouponRecordFragment.kt\ncom/psychiatrygarden/activity/coupon/fragment/CouponRecordFragment\n*L\n54#1:174,3\n*E\n"})
/* loaded from: classes5.dex */
public final class CouponRecordFragment extends BaseFragment {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String extraType = RedPacketRecordFragment.extraType;
    private MagicIndicator mMagicIndicator;
    private ViewPager viewpager;

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/activity/coupon/fragment/CouponRecordFragment$Companion;", "", "()V", "extraType", "", "getExtraType", "()Ljava/lang/String;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final String getExtraType() {
            return CouponRecordFragment.extraType;
        }
    }

    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/activity/coupon/fragment/CouponRecordFragment$initTabColumn$2", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerIndicator;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "getTitleView", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerTitleView;", "index", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.coupon.fragment.CouponRecordFragment$initTabColumn$2, reason: invalid class name */
    public static final class AnonymousClass2 extends CommonNavigatorAdapter {
        final /* synthetic */ List<String> $dataNewList;
        final /* synthetic */ CouponRecordFragment this$0;

        public AnonymousClass2(List<String> list, CouponRecordFragment couponRecordFragment) {
            this.$dataNewList = list;
            this.this$0 = couponRecordFragment;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void getTitleView$lambda$0(CouponRecordFragment this$0, int i2, View view) throws Resources.NotFoundException {
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
            final CouponRecordFragment couponRecordFragment = this.this$0;
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.coupon.fragment.g
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    CouponRecordFragment.AnonymousClass2.getTitleView$lambda$0(couponRecordFragment, index, view);
                }
            });
            final CouponRecordFragment couponRecordFragment2 = this.this$0;
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.coupon.fragment.CouponRecordFragment$initTabColumn$2$getTitleView$2
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(SkinManager.getThemeColor(context, R.attr.new_bg_two_color));
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(couponRecordFragment2.requireContext(), 8.0f));
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
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(couponRecordFragment2.requireContext(), 8.0f));
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.main_theme_color));
                    textView.setBackground(gradientDrawable);
                }
            });
            return commonPagerTitleView;
        }
    }

    private final void initTabColumn() throws Resources.NotFoundException {
        List listMutableListOf = CollectionsKt__CollectionsKt.mutableListOf("全部", "已使用", "已过期", "已失效");
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        for (Object obj : listMutableListOf) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            Bundle bundle = new Bundle();
            bundle.putInt("position", 0);
            bundle.putBoolean("isCenter", false);
            bundle.putBoolean("available", false);
            bundle.putInt("useType", i2 > 0 ? i3 : i2);
            bundle.putBoolean("needTopMargin", true);
            arrayList.add(new BaseViewPagerAdapter.PagerInfo((String) listMutableListOf.get(i2), FragRedEnvelopeCoupons.class, bundle));
            i2 = i3;
        }
        if (isAdded()) {
            BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), arrayList);
            ViewPager viewPager = this.viewpager;
            ViewPager viewPager2 = null;
            if (viewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                viewPager = null;
            }
            viewPager.setAdapter(baseViewPagerAdapter);
            CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
            commonNavigator.setSkimOver(false);
            commonNavigator.setAdapter(new AnonymousClass2(listMutableListOf, this));
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
            ViewPager viewPager3 = this.viewpager;
            if (viewPager3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                viewPager3 = null;
            }
            ViewPagerHelper.bind(magicIndicator2, viewPager3);
            ViewPager viewPager4 = this.viewpager;
            if (viewPager4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                viewPager4 = null;
            }
            viewPager4.setOffscreenPageLimit(listMutableListOf.size() - 1);
            ViewPager viewPager5 = this.viewpager;
            if (viewPager5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewpager");
            } else {
                viewPager2 = viewPager5;
            }
            viewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.coupon.fragment.CouponRecordFragment.initTabColumn.3
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
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLazyInitView$lambda$1() {
        EventBus.getDefault().post(RedPacketRecordFragment.REFRESH_TAB);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_coupon_record;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @Nullable View root) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        View view = holder.get(R.id.couponRecordMagicIndicator);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.couponRecordMagicIndicator)");
        this.mMagicIndicator = (MagicIndicator) view;
        View view2 = holder.get(R.id.couponRecordViewpage);
        Intrinsics.checkNotNullExpressionValue(view2, "holder.get(R.id.couponRecordViewpage)");
        this.viewpager = (ViewPager) view2;
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) throws Resources.NotFoundException {
        super.onLazyInitView(savedInstanceState);
        initTabColumn();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.coupon.fragment.f
            @Override // java.lang.Runnable
            public final void run() {
                CouponRecordFragment.onLazyInitView$lambda$1();
            }
        }, 300L);
    }
}
