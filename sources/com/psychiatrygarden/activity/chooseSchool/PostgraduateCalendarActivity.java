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
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.PostgraduateCalendarActivity;
import com.psychiatrygarden.activity.chooseSchool.fragment.ChooseSchoolCalendarFragment;
import com.psychiatrygarden.activity.chooseSchool.util.AliYunLogUtil;
import com.psychiatrygarden.bean.ChooseSchoolCalendarTabBean;
import com.psychiatrygarden.bean.NewHomeKingKongItem;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.GoodsSimplePagerTitleView;
import com.psychiatrygarden.widget.HeaderScrollView;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 )2\u00020\u0001:\u0001)B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\b\u0010 \u001a\u00020\u001cH\u0002J\b\u0010!\u001a\u00020\u001cH\u0002J\b\u0010\"\u001a\u00020\u001cH\u0002J\u0012\u0010#\u001a\u00020\u001c2\b\u0010$\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u0010%\u001a\u00020\u001cH\u0016J\u0010\u0010&\u001a\u00020\u001c2\u0006\u0010'\u001a\u00020\u0012H\u0002J\b\u0010(\u001a\u00020\u001cH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/PostgraduateCalendarActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "imageViewBg", "Landroid/widget/ImageView;", "layoutBottom1V1", "Landroid/widget/RelativeLayout;", "magicIndicator", "Lcn/webdemo/com/supporfragment/tablayout/MagicIndicator;", "scrollBegin", "", "scrollEnd", "scrollView", "Lcom/psychiatrygarden/widget/HeaderScrollView;", "str1v1QrCode", "", "str1v1QrId", "str1v1Success", "", "tabList", "", "tv1V1", "Landroid/widget/TextView;", "tvActionBarBg", "tvTitle", "viewpager", "Landroidx/viewpager/widget/ViewPager;", "init", "", "initTabColumn", "tabBean", "Lcom/psychiatrygarden/bean/ChooseSchoolCalendarTabBean;", "load1v1QrCode", "loadKingKongArea", "loadKyCalendarTabInfo", "onEventMainThread", "str", "setContentView", "setHave1V1Btn", "have1v1", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class PostgraduateCalendarActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private ImageView imageViewBg;
    private RelativeLayout layoutBottom1V1;
    private MagicIndicator magicIndicator;
    private HeaderScrollView scrollView;
    private boolean str1v1Success;
    private TextView tv1V1;
    private TextView tvActionBarBg;
    private TextView tvTitle;
    private ViewPager viewpager;

    @NotNull
    private final List<String> tabList = CollectionsKt__CollectionsKt.mutableListOf("时间线", "考试科目", "调剂时间线");

    @NotNull
    private String str1v1QrCode = "";

    @NotNull
    private String str1v1QrId = "";
    private final int scrollBegin = 100;
    private int scrollEnd = 500;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/PostgraduateCalendarActivity$Companion;", "", "()V", "goToPostgraduateCalendarActivity", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void goToPostgraduateCalendarActivity(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.startActivity(new Intent(context, (Class<?>) PostgraduateCalendarActivity.class));
        }
    }

    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/activity/chooseSchool/PostgraduateCalendarActivity$initTabColumn$1", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerIndicator;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "getTitleView", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerTitleView;", "index", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.chooseSchool.PostgraduateCalendarActivity$initTabColumn$1, reason: invalid class name */
    public static final class AnonymousClass1 extends CommonNavigatorAdapter {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void getTitleView$lambda$0(PostgraduateCalendarActivity this$0, int i2, View view) throws Resources.NotFoundException {
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
            return PostgraduateCalendarActivity.this.tabList.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @NotNull
        public IPagerIndicator getIndicator(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            linePagerIndicator.setMode(2);
            linePagerIndicator.setLineHeight(UIUtil.dip2px(context, 4.0d));
            linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 16.0d));
            linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 3.0d));
            linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
            linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
            Integer[] numArr = new Integer[1];
            numArr[0] = Integer.valueOf(SkinManager.getCurrentSkinType(PostgraduateCalendarActivity.this.mContext) == 1 ? ContextCompat.getColor(PostgraduateCalendarActivity.this, R.color.main_theme_color_night) : ContextCompat.getColor(PostgraduateCalendarActivity.this, R.color.main_theme_color));
            linePagerIndicator.setColors(numArr);
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @NotNull
        public IPagerTitleView getTitleView(@NotNull Context context, final int index) {
            PostgraduateCalendarActivity postgraduateCalendarActivity;
            int i2;
            PostgraduateCalendarActivity postgraduateCalendarActivity2;
            int i3;
            Intrinsics.checkNotNullParameter(context, "context");
            final PostgraduateCalendarActivity postgraduateCalendarActivity3 = PostgraduateCalendarActivity.this;
            GoodsSimplePagerTitleView goodsSimplePagerTitleView = new GoodsSimplePagerTitleView(postgraduateCalendarActivity3) { // from class: com.psychiatrygarden.activity.chooseSchool.PostgraduateCalendarActivity$initTabColumn$1$getTitleView$titleView$1
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
            if (SkinManager.getCurrentSkinType(PostgraduateCalendarActivity.this.mContext) == 1) {
                postgraduateCalendarActivity = PostgraduateCalendarActivity.this;
                i2 = R.color.first_txt_color_night;
            } else {
                postgraduateCalendarActivity = PostgraduateCalendarActivity.this;
                i2 = R.color.first_txt_color;
            }
            int color = postgraduateCalendarActivity.getColor(i2);
            if (SkinManager.getCurrentSkinType(PostgraduateCalendarActivity.this.mContext) == 1) {
                postgraduateCalendarActivity2 = PostgraduateCalendarActivity.this;
                i3 = R.color.third_txt_color_night;
            } else {
                postgraduateCalendarActivity2 = PostgraduateCalendarActivity.this;
                i3 = R.color.third_txt_color;
            }
            goodsSimplePagerTitleView.setNormalColor(postgraduateCalendarActivity2.getColor(i3));
            goodsSimplePagerTitleView.setSelectedColor(color);
            final PostgraduateCalendarActivity postgraduateCalendarActivity4 = PostgraduateCalendarActivity.this;
            goodsSimplePagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.h3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    PostgraduateCalendarActivity.AnonymousClass1.getTitleView$lambda$0(postgraduateCalendarActivity4, index, view);
                }
            });
            goodsSimplePagerTitleView.setText(StringsKt__StringsKt.trim((CharSequence) PostgraduateCalendarActivity.this.tabList.get(index)).toString());
            return goodsSimplePagerTitleView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(PostgraduateCalendarActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ImageView imageView = this$0.imageViewBg;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageViewBg");
            imageView = null;
        }
        this$0.scrollEnd = imageView.getHeight() - UIUtil.dip2px(this$0.mContext, 90.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initTabColumn(ChooseSchoolCalendarTabBean tabBean) throws Resources.NotFoundException {
        boolean zAreEqual = Intrinsics.areEqual(tabBean.getType_1(), "1");
        boolean zAreEqual2 = Intrinsics.areEqual(tabBean.getType_2(), "1");
        boolean zAreEqual3 = Intrinsics.areEqual(tabBean.getType_3(), "1");
        this.tabList.clear();
        if (zAreEqual) {
            this.tabList.add("时间线");
        }
        if (zAreEqual2) {
            this.tabList.add("考试科目");
        }
        if (zAreEqual3) {
            this.tabList.add("调剂时间线");
        }
        ArrayList arrayList = new ArrayList();
        int size = this.tabList.size();
        for (int i2 = 0; i2 < size; i2++) {
            Bundle bundle = new Bundle();
            if (Intrinsics.areEqual(this.tabList.get(i2), "时间线")) {
                bundle.putString("type", "1");
            } else if (Intrinsics.areEqual(this.tabList.get(i2), "考试科目")) {
                bundle.putString("type", "2");
            } else {
                bundle.putString("type", "3");
            }
            arrayList.add(new BaseViewPagerAdapter.PagerInfo(this.tabList.get(i2), ChooseSchoolCalendarFragment.class, bundle));
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
        commonNavigator.setLeftPadding(0);
        commonNavigator.setRightPadding(0);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new AnonymousClass1());
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
        ViewPager viewPager5 = this.viewpager;
        if (viewPager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
        } else {
            viewPager2 = viewPager5;
        }
        ViewPagerHelper.bind(magicIndicator2, viewPager2);
    }

    private final void load1v1QrCode() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.chooseSchool1V1, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.PostgraduateCalendarActivity.load1v1QrCode.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                JSONObject jSONObjectOptJSONObject;
                Intrinsics.checkNotNullParameter(json, "json");
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (!Intrinsics.areEqual(jSONObject.optString("code"), "200") || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null) {
                        return;
                    }
                    PostgraduateCalendarActivity postgraduateCalendarActivity = PostgraduateCalendarActivity.this;
                    String strOptString = jSONObjectOptJSONObject.optString("qr_code");
                    Intrinsics.checkNotNullExpressionValue(strOptString, "it.optString(\"qr_code\")");
                    postgraduateCalendarActivity.str1v1QrCode = strOptString;
                    String strOptString2 = jSONObjectOptJSONObject.optString("we_chat_id");
                    Intrinsics.checkNotNullExpressionValue(strOptString2, "it.optString(\"we_chat_id\")");
                    postgraduateCalendarActivity.str1v1QrId = strOptString2;
                    postgraduateCalendarActivity.str1v1Success = true;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void loadKingKongArea() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolKingKongArea, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.PostgraduateCalendarActivity.loadKingKongArea.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                PostgraduateCalendarActivity.this.setHave1V1Btn(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                Object next;
                Intrinsics.checkNotNullParameter(json, "json");
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                        List dataList = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends NewHomeKingKongItem>>() { // from class: com.psychiatrygarden.activity.chooseSchool.PostgraduateCalendarActivity$loadKingKongArea$1$onSuccess$dataList$1
                        }.getType());
                        List list = dataList;
                        boolean z2 = true;
                        if (list == null || list.isEmpty()) {
                            PostgraduateCalendarActivity.this.setHave1V1Btn(false);
                            return;
                        }
                        Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
                        Iterator it = dataList.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            } else {
                                next = it.next();
                                if (Intrinsics.areEqual(((NewHomeKingKongItem) next).getPush_type(), "50")) {
                                    break;
                                }
                            }
                        }
                        NewHomeKingKongItem newHomeKingKongItem = (NewHomeKingKongItem) next;
                        PostgraduateCalendarActivity postgraduateCalendarActivity = PostgraduateCalendarActivity.this;
                        if (newHomeKingKongItem == null) {
                            z2 = false;
                        }
                        postgraduateCalendarActivity.setHave1V1Btn(z2);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    PostgraduateCalendarActivity.this.setHave1V1Btn(false);
                }
            }
        });
    }

    private final void loadKyCalendarTabInfo() {
        Context context = this.mContext;
        String str = NetworkRequestsURL.chooseSchoolCalendar;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "0");
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.get(context, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.PostgraduateCalendarActivity.loadKyCalendarTabInfo.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                Intrinsics.checkNotNullParameter(json, "json");
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                        ChooseSchoolCalendarTabBean data = (ChooseSchoolCalendarTabBean) new Gson().fromJson(jSONObject.optString("data"), ChooseSchoolCalendarTabBean.class);
                        PostgraduateCalendarActivity postgraduateCalendarActivity = PostgraduateCalendarActivity.this;
                        Intrinsics.checkNotNullExpressionValue(data, "data");
                        postgraduateCalendarActivity.initTabColumn(data);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setHave1V1Btn(boolean have1v1) {
        RelativeLayout relativeLayout = null;
        if (have1v1) {
            RelativeLayout relativeLayout2 = this.layoutBottom1V1;
            if (relativeLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutBottom1V1");
            } else {
                relativeLayout = relativeLayout2;
            }
            ViewExtensionsKt.visible(relativeLayout);
            SharePreferencesUtils.writeStrConfig(CommonParameter.CHOOSE_SCHOOL_1v1, "1", this);
            return;
        }
        RelativeLayout relativeLayout3 = this.layoutBottom1V1;
        if (relativeLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutBottom1V1");
        } else {
            relativeLayout = relativeLayout3;
        }
        ViewExtensionsKt.gone(relativeLayout);
        SharePreferencesUtils.writeStrConfig(CommonParameter.CHOOSE_SCHOOL_1v1, "0", this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$1(PostgraduateCalendarActivity this$0, View view, int i2, int i3, int i4, int i5) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TextView textView = null;
        if (i3 < this$0.scrollBegin) {
            TextView textView2 = this$0.tvActionBarBg;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvActionBarBg");
                textView2 = null;
            }
            textView2.setAlpha(0.0f);
            TextView textView3 = this$0.tvTitle;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
            } else {
                textView = textView3;
            }
            ViewExtensionsKt.gone(textView);
            return;
        }
        if (i3 <= this$0.scrollEnd) {
            float f2 = (i3 - r2) / (r0 - r2);
            TextView textView4 = this$0.tvActionBarBg;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvActionBarBg");
            } else {
                textView = textView4;
            }
            textView.setAlpha(f2);
            return;
        }
        TextView textView5 = this$0.tvActionBarBg;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvActionBarBg");
            textView5 = null;
        }
        textView5.setAlpha(1.0f);
        TextView textView6 = this$0.tvTitle;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
        } else {
            textView = textView6;
        }
        ViewExtensionsKt.visible(textView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$2(PostgraduateCalendarActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$3(PostgraduateCalendarActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.str1v1Success) {
            CommonUtil.jump1v1(this$0, this$0.str1v1QrCode, this$0.str1v1QrId);
        } else {
            this$0.load1v1QrCode();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        View viewFindViewById = findViewById(R.id.tv1V1);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.tv1V1)");
        this.tv1V1 = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.layoutBottom1V1);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.layoutBottom1V1)");
        this.layoutBottom1V1 = (RelativeLayout) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.scrollView);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.scrollView)");
        this.scrollView = (HeaderScrollView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.tvTitle);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.tvTitle)");
        this.tvTitle = (TextView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.tvActionBarBg);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.tvActionBarBg)");
        this.tvActionBarBg = (TextView) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.magicIndicator);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.magicIndicator)");
        this.magicIndicator = (MagicIndicator) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.viewpager);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.viewpager)");
        this.viewpager = (ViewPager) viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.imageViewBg);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.imageViewBg)");
        ImageView imageView = (ImageView) viewFindViewById8;
        this.imageViewBg = imageView;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageViewBg");
            imageView = null;
        }
        imageView.post(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.g3
            @Override // java.lang.Runnable
            public final void run() {
                PostgraduateCalendarActivity.init$lambda$0(this.f11295c);
            }
        });
        loadKyCalendarTabInfo();
        AliYunLogUtil.getInstance().addLog(AliyunEvent.PostgraduateCalendar);
        loadKingKongArea();
        load1v1QrCode();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentFillFromStatusBar(false);
        this.mActionBar.hide();
        setContentView(R.layout.activity_postgraduate_calendar);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        HeaderScrollView headerScrollView = this.scrollView;
        TextView textView = null;
        if (headerScrollView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scrollView");
            headerScrollView = null;
        }
        headerScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.d3
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i2, int i3, int i4, int i5) {
                PostgraduateCalendarActivity.setListenerForWidget$lambda$1(this.f11239c, view, i2, i3, i4, i5);
            }
        });
        ((ImageView) findViewById(R.id.imgBack)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.e3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PostgraduateCalendarActivity.setListenerForWidget$lambda$2(this.f11248c, view);
            }
        });
        TextView textView2 = this.tv1V1;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tv1V1");
        } else {
            textView = textView2;
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.f3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PostgraduateCalendarActivity.setListenerForWidget$lambda$3(this.f11258c, view);
            }
        });
    }
}
