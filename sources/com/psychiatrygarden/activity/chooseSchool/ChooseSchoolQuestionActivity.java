package com.psychiatrygarden.activity.chooseSchool;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.ChooseSchoolQuestionActivity;
import com.psychiatrygarden.activity.chooseSchool.fragment.ChooseSchoolNoticeFragment;
import com.psychiatrygarden.activity.chooseSchool.fragment.ChooseSchoolQuestionNewFragment;
import com.psychiatrygarden.bean.ChooseSchoolServerCustomer;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.GoodsSimplePagerTitleView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0011H\u0016J\b\u0010\u0013\u001a\u00020\u0011H\u0002J\u0012\u0010\u0014\u001a\u00020\u00112\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0011H\u0016J\b\u0010\u0018\u001a\u00020\u0011H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/ChooseSchoolQuestionActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "customerData", "Lcom/psychiatrygarden/bean/ChooseSchoolServerCustomer;", "ivActionbarBack", "Landroid/widget/ImageView;", "layoutBottom", "Landroid/widget/RelativeLayout;", "magicIndicator", "Lcn/webdemo/com/supporfragment/tablayout/MagicIndicator;", "tvCustomer", "Landroid/widget/TextView;", "txtActionbarTitle", "viewPager", "Landroidx/viewpager/widget/ViewPager;", "getCustomerData", "", "init", "initTabColumn", "onEventMainThread", "str", "", "setContentView", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolQuestionActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private ChooseSchoolServerCustomer customerData;
    private ImageView ivActionbarBack;
    private RelativeLayout layoutBottom;
    private MagicIndicator magicIndicator;
    private TextView tvCustomer;
    private TextView txtActionbarTitle;
    private ViewPager viewPager;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/ChooseSchoolQuestionActivity$Companion;", "", "()V", "navigationToChooseSchoolQuestionActivity", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void navigationToChooseSchoolQuestionActivity(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.startActivity(new Intent(context, (Class<?>) ChooseSchoolQuestionActivity.class));
        }
    }

    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/activity/chooseSchool/ChooseSchoolQuestionActivity$initTabColumn$1", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerIndicator;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "getTitleView", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerTitleView;", "index", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolQuestionActivity$initTabColumn$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05781 extends CommonNavigatorAdapter {
        final /* synthetic */ String[] $titles;
        final /* synthetic */ ChooseSchoolQuestionActivity this$0;

        public C05781(String[] strArr, ChooseSchoolQuestionActivity chooseSchoolQuestionActivity) {
            this.$titles = strArr;
            this.this$0 = chooseSchoolQuestionActivity;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void getTitleView$lambda$0(ChooseSchoolQuestionActivity this$0, int i2, View view) throws Resources.NotFoundException {
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
            ChooseSchoolQuestionActivity chooseSchoolQuestionActivity;
            int i2;
            ChooseSchoolQuestionActivity chooseSchoolQuestionActivity2;
            int i3;
            Intrinsics.checkNotNullParameter(context, "context");
            final ChooseSchoolQuestionActivity chooseSchoolQuestionActivity3 = this.this$0;
            GoodsSimplePagerTitleView goodsSimplePagerTitleView = new GoodsSimplePagerTitleView(chooseSchoolQuestionActivity3) { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolQuestionActivity$initTabColumn$1$getTitleView$titleView$1
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
                chooseSchoolQuestionActivity = this.this$0;
                i2 = R.color.first_txt_color_night;
            } else {
                chooseSchoolQuestionActivity = this.this$0;
                i2 = R.color.first_txt_color;
            }
            int color = chooseSchoolQuestionActivity.getColor(i2);
            if (SkinManager.getCurrentSkinType(this.this$0.mContext) == 1) {
                chooseSchoolQuestionActivity2 = this.this$0;
                i3 = R.color.third_txt_color_night;
            } else {
                chooseSchoolQuestionActivity2 = this.this$0;
                i3 = R.color.third_txt_color;
            }
            goodsSimplePagerTitleView.setNormalColor(chooseSchoolQuestionActivity2.getColor(i3));
            goodsSimplePagerTitleView.setSelectedColor(color);
            final ChooseSchoolQuestionActivity chooseSchoolQuestionActivity4 = this.this$0;
            goodsSimplePagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.y0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    ChooseSchoolQuestionActivity.C05781.getTitleView$lambda$0(chooseSchoolQuestionActivity4, index, view);
                }
            });
            goodsSimplePagerTitleView.setText(StringsKt__StringsKt.trim((CharSequence) this.$titles[index]).toString());
            return goodsSimplePagerTitleView;
        }
    }

    private final void getCustomerData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolFeedBackCS, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolQuestionActivity.getCustomerData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass1) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        ChooseSchoolServerCustomer chooseSchoolServerCustomer = (ChooseSchoolServerCustomer) new Gson().fromJson(jSONObject.optString("data"), ChooseSchoolServerCustomer.class);
                        ChooseSchoolQuestionActivity.this.customerData = chooseSchoolServerCustomer;
                        RelativeLayout relativeLayout = ChooseSchoolQuestionActivity.this.layoutBottom;
                        if (relativeLayout == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("layoutBottom");
                            relativeLayout = null;
                        }
                        relativeLayout.setVisibility(Intrinsics.areEqual(chooseSchoolServerCustomer.is_show(), "1") ? 0 : 8);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(ChooseSchoolQuestionActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final void initTabColumn() throws Resources.NotFoundException {
        ArrayList arrayList = new ArrayList();
        String[] strArr = {"更新公告", "常见问题"};
        for (int i2 = 0; i2 < 2; i2++) {
            Bundle bundle = new Bundle();
            if (i2 == 0) {
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(strArr[i2], ChooseSchoolNoticeFragment.class, bundle));
            } else {
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(strArr[i2], ChooseSchoolQuestionNewFragment.class, bundle));
            }
        }
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), arrayList);
        ViewPager viewPager = this.viewPager;
        ViewPager viewPager2 = null;
        if (viewPager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager = null;
        }
        viewPager.setAdapter(baseViewPagerAdapter);
        ViewPager viewPager3 = this.viewPager;
        if (viewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager3 = null;
        }
        viewPager3.setCurrentItem(0);
        ViewPager viewPager4 = this.viewPager;
        if (viewPager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager4 = null;
        }
        viewPager4.setOffscreenPageLimit(2);
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setWeights(CollectionsKt__CollectionsKt.mutableListOf(Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(1.5f)));
        commonNavigator.setLeftPadding(0);
        commonNavigator.setRightPadding(0);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new C05781(strArr, this));
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
        ViewPager viewPager5 = this.viewPager;
        if (viewPager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
        } else {
            viewPager2 = viewPager5;
        }
        ViewPagerHelper.bind(magicIndicator2, viewPager2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$2(ChooseSchoolQuestionActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ChooseSchoolServerCustomer chooseSchoolServerCustomer = this$0.customerData;
        if (chooseSchoolServerCustomer == null || chooseSchoolServerCustomer.getFeedback() == null) {
            return;
        }
        CommonUtil.onlineService(this$0, chooseSchoolServerCustomer.getFeedback());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        View viewFindViewById = findViewById(R.id.viewPage);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.viewPage)");
        this.viewPager = (ViewPager) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.magicIndicator);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.magicIndicator)");
        this.magicIndicator = (MagicIndicator) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.tvCustomer);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.tvCustomer)");
        this.tvCustomer = (TextView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.txt_actionbar_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.txt_actionbar_title)");
        this.txtActionbarTitle = (TextView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.iv_actionbar_back);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.iv_actionbar_back)");
        this.ivActionbarBack = (ImageView) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.layoutBottom);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.layoutBottom)");
        this.layoutBottom = (RelativeLayout) viewFindViewById6;
        TextView textView = this.txtActionbarTitle;
        ImageView imageView = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("txtActionbarTitle");
            textView = null;
        }
        textView.setText("医考帮择校助手");
        ImageView imageView2 = this.ivActionbarBack;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivActionbarBack");
        } else {
            imageView = imageView2;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.x0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolQuestionActivity.init$lambda$0(this.f11456c, view);
            }
        });
        initTabColumn();
        getCustomerData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.activity_choose_school_quesion);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        TextView textView = this.tvCustomer;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCustomer");
            textView = null;
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.w0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolQuestionActivity.setListenerForWidget$lambda$2(this.f11440c, view);
            }
        });
    }
}
