package com.psychiatrygarden.activity.chooseSchool;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
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
import com.aliyun.svideo.common.utils.FastClickUtil;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.ChooseSchoolListActivity;
import com.psychiatrygarden.activity.chooseSchool.fragment.ChooseSchoolListFragment;
import com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow;
import com.psychiatrygarden.bean.ChooseSchoolFilterBean;
import com.psychiatrygarden.bean.ChooseSchoolFilterBeanList;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\r\u0018\u0000 A2\u00020\u0001:\u0001AB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010-\u001a\u00020.H\u0002J\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u000400J\b\u00101\u001a\u00020.H\u0016J\b\u00102\u001a\u00020.H\u0016J\b\u00103\u001a\u00020.H\u0002J\b\u00104\u001a\u000205H\u0002J\u0010\u00106\u001a\u00020.2\u0006\u0010\f\u001a\u00020\u0007H\u0002J\u0012\u00107\u001a\u00020.2\b\u00108\u001a\u0004\u0018\u00010\u0004H\u0016J\b\u00109\u001a\u00020.H\u0016J\b\u0010:\u001a\u00020.H\u0016J\b\u0010;\u001a\u00020.H\u0002J\u0018\u0010<\u001a\u00020.2\u0006\u0010=\u001a\u0002052\u0006\u0010>\u001a\u00020\tH\u0002J\b\u0010?\u001a\u00020.H\u0002J\b\u0010@\u001a\u00020.H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00040\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020&X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020&X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020&X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010,\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006B"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/ChooseSchoolListActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "baoCount", "", "chongCount", "height", "", "imgArea", "Landroid/widget/ImageView;", "imgAttribute", "imgType", "index", "ivActionbarBack", "layoutArea", "Landroid/widget/LinearLayout;", "layoutContent", "layoutFilter", "layoutHint", "Landroid/widget/RelativeLayout;", "layoutSchoolAttribute", "layoutSchoolMajor", "layoutSchoolType", "listTabs", "", "mListArea", "Lcom/psychiatrygarden/bean/ChooseSchoolFilterBean;", "mListAttribute", "mListSelectArea", "mListSelectAttribute", "mListSelectType", "mListType", "magicIndicator", "Lcn/webdemo/com/supporfragment/tablayout/MagicIndicator;", "major_id", "nanCount", "score", "tvArea", "Landroid/widget/TextView;", "tvAttribute", "tvType", "txtActionbarTitle", "viewPager", "Landroidx/viewpager/widget/ViewPager;", "wenCount", "getFilterData", "", "getSelectFilterData", "", "init", "initStatusBar", "initTab", "isNightTheme", "", "notifyListDataChanged", "onEventMainThread", "str", "setContentView", "setListenerForWidget", "showAreaChooseDialog", "showOrHiddenArrow", "isShow", "arrowImg", "showSchoolAttributeChooseDialog", "showSchoolTypeChooseDialog", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolListActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private String baoCount;

    @Nullable
    private String chongCount;
    private int height;
    private ImageView imgArea;
    private ImageView imgAttribute;
    private ImageView imgType;
    private int index;
    private ImageView ivActionbarBack;
    private LinearLayout layoutArea;
    private LinearLayout layoutContent;
    private LinearLayout layoutFilter;
    private RelativeLayout layoutHint;
    private LinearLayout layoutSchoolAttribute;
    private LinearLayout layoutSchoolMajor;
    private LinearLayout layoutSchoolType;
    private MagicIndicator magicIndicator;

    @Nullable
    private String major_id;

    @Nullable
    private String nanCount;

    @Nullable
    private String score;
    private TextView tvArea;
    private TextView tvAttribute;
    private TextView tvType;
    private TextView txtActionbarTitle;
    private ViewPager viewPager;

    @Nullable
    private String wenCount;

    @NotNull
    private final List<String> listTabs = CollectionsKt__CollectionsKt.mutableListOf("保 55", "稳 22", "冲 15", "难 54");

    @NotNull
    private final List<ChooseSchoolFilterBean> mListArea = new ArrayList();

    @NotNull
    private final List<String> mListSelectArea = new ArrayList();

    @NotNull
    private final List<ChooseSchoolFilterBean> mListAttribute = new ArrayList();

    @NotNull
    private final List<String> mListSelectAttribute = new ArrayList();

    @NotNull
    private final List<ChooseSchoolFilterBean> mListType = new ArrayList();

    @NotNull
    private final List<String> mListSelectType = new ArrayList();

    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JT\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\b2\b\u0010\n\u001a\u0004\u0018\u00010\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\b2\b\u0010\f\u001a\u0004\u0018\u00010\b2\b\u0010\r\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\u000e\u001a\u00020\u000f¨\u0006\u0010"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/ChooseSchoolListActivity$Companion;", "", "()V", "navigationToChooseSchoolActivity", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "baoCount", "", "wenCount", "chongCount", "nanCount", "major_id", "score", "index", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void navigationToChooseSchoolActivity(@NotNull Context context, @Nullable String baoCount, @Nullable String wenCount, @Nullable String chongCount, @Nullable String nanCount, @Nullable String major_id, @Nullable String score, int index) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intent intent = new Intent(context, (Class<?>) ChooseSchoolListActivity.class);
            intent.putExtra("baoCount", baoCount);
            intent.putExtra("wenCount", wenCount);
            intent.putExtra("chongCount", chongCount);
            intent.putExtra("nanCount", nanCount);
            intent.putExtra("major_id", major_id);
            intent.putExtra("score", score);
            intent.putExtra("index", index);
            context.startActivity(intent);
        }
    }

    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/activity/chooseSchool/ChooseSchoolListActivity$initTab$1", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerIndicator;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "getTitleView", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerTitleView;", "index", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolListActivity$initTab$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05651 extends CommonNavigatorAdapter {
        public C05651() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void getTitleView$lambda$0(ChooseSchoolListActivity this$0, int i2, View view) throws Resources.NotFoundException {
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
            return ChooseSchoolListActivity.this.listTabs.size();
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
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(ChooseSchoolListActivity.this);
            commonPagerTitleView.setContentView(R.layout.item_tab_choose_school);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_tab_name);
            final RelativeLayout relativeLayout = (RelativeLayout) commonPagerTitleView.findViewById(R.id.tabItemRoot);
            ViewPager viewPager = ChooseSchoolListActivity.this.viewPager;
            if (viewPager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                viewPager = null;
            }
            if (viewPager.getCurrentItem() == index) {
                relativeLayout.setBackgroundResource(R.drawable.shape_app_bg_top_corners8);
            } else {
                relativeLayout.setBackground(null);
            }
            textView.setText((CharSequence) ChooseSchoolListActivity.this.listTabs.get(index));
            final ChooseSchoolListActivity chooseSchoolListActivity = ChooseSchoolListActivity.this;
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.b0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    ChooseSchoolListActivity.C05651.getTitleView$lambda$0(chooseSchoolListActivity, index, view);
                }
            });
            final ChooseSchoolListActivity chooseSchoolListActivity2 = ChooseSchoolListActivity.this;
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolListActivity$initTab$1$getTitleView$2
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT_BOLD);
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.first_text_color));
                    textView.setTextSize(2, 15.0f);
                    ViewPager viewPager2 = chooseSchoolListActivity2.viewPager;
                    if (viewPager2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                        viewPager2 = null;
                    }
                    if (viewPager2.getCurrentItem() == index2) {
                        relativeLayout.setBackgroundResource(R.drawable.shape_app_bg_top_corners8);
                    } else {
                        relativeLayout.setBackground(null);
                    }
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
                    if (index2 == 0) {
                        textView.setTextColor(chooseSchoolListActivity2.getResources().getColor(R.color.zx_color_green));
                    } else if (index2 == 1) {
                        textView.setTextColor(chooseSchoolListActivity2.getResources().getColor(R.color.zx_color_blue));
                    } else if (index2 == 2) {
                        textView.setTextColor(chooseSchoolListActivity2.getResources().getColor(R.color.main_theme_color));
                    } else if (index2 == 3) {
                        textView.setTextColor(SkinManager.getThemeColor(context, R.attr.first_text_color));
                    }
                    textView.setTextSize(2, 15.0f);
                    ViewPager viewPager2 = chooseSchoolListActivity2.viewPager;
                    if (viewPager2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                        viewPager2 = null;
                    }
                    if (viewPager2.getCurrentItem() == index2) {
                        relativeLayout.setBackgroundResource(R.drawable.shape_app_bg_top_corners8);
                    } else {
                        relativeLayout.setBackground(null);
                    }
                }
            });
            return commonPagerTitleView;
        }
    }

    public ChooseSchoolListActivity() {
        this.TAG = "ChooseSchoolListActivity";
    }

    private final void getFilterData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolFilterData, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolListActivity.getFilterData.1
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
                    if (Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                        ChooseSchoolFilterBeanList chooseSchoolFilterBeanList = (ChooseSchoolFilterBeanList) new Gson().fromJson(jSONObject.optString("data"), ChooseSchoolFilterBeanList.class);
                        List<ChooseSchoolFilterBean> area = chooseSchoolFilterBeanList.getArea();
                        if (!(area == null || area.isEmpty())) {
                            ChooseSchoolListActivity.this.mListArea.addAll(chooseSchoolFilterBeanList.getArea());
                        }
                        List<ChooseSchoolFilterBean> attr = chooseSchoolFilterBeanList.getAttr();
                        if (!(attr == null || attr.isEmpty())) {
                            ChooseSchoolListActivity.this.mListAttribute.addAll(chooseSchoolFilterBeanList.getAttr());
                        }
                        List<ChooseSchoolFilterBean> category = chooseSchoolFilterBeanList.getCategory();
                        if (category == null || category.isEmpty()) {
                            return;
                        }
                        ChooseSchoolListActivity.this.mListType.addAll(chooseSchoolFilterBeanList.getCategory());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void initTab() throws Resources.NotFoundException {
        this.listTabs.clear();
        this.listTabs.add("保 " + this.baoCount);
        this.listTabs.add("稳 " + this.wenCount);
        this.listTabs.add("冲 " + this.chongCount);
        this.listTabs.add("难 " + this.nanCount);
        if (this.listTabs.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        int size = this.listTabs.size();
        for (int i2 = 0; i2 < size; i2++) {
            Bundle bundle = new Bundle();
            bundle.putString("type", String.valueOf(4 - i2));
            bundle.putString("major_id", this.major_id);
            bundle.putString("score", this.score);
            arrayList.add(new BaseViewPagerAdapter.PagerInfo(this.listTabs.get(i2), ChooseSchoolListFragment.class, bundle));
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
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new C05651());
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
        ViewPager viewPager3 = this.viewPager;
        if (viewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager3 = null;
        }
        ViewPagerHelper.bind(magicIndicator2, viewPager3);
        ViewPager viewPager4 = this.viewPager;
        if (viewPager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager4 = null;
        }
        viewPager4.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolListActivity.initTab.2
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
        viewPager5.setOffscreenPageLimit(this.listTabs.size());
        ViewPager viewPager6 = this.viewPager;
        if (viewPager6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager6 = null;
        }
        viewPager6.setCurrentItem(0);
        MagicIndicator magicIndicator3 = this.magicIndicator;
        if (magicIndicator3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
            magicIndicator3 = null;
        }
        magicIndicator3.setVisibility(this.listTabs.size() <= 1 ? 8 : 0);
        ViewPager viewPager7 = this.viewPager;
        if (viewPager7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
        } else {
            viewPager2 = viewPager7;
        }
        viewPager2.setCurrentItem(this.index);
        getFilterData();
    }

    private final boolean isNightTheme() {
        return SkinManager.getCurrentSkinType(this) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifyListDataChanged(int index) {
        EventBus.getDefault().post(EventBusConstant.EVENT_CHOOSE_SCHOOL_FILTER_CHANGE);
        int i2 = R.drawable.icon_arrow_first_txt_color_bottom;
        ImageView imageView = null;
        if (index == 0) {
            TextView textView = this.tvArea;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvArea");
                textView = null;
            }
            textView.setTextColor(this.mListSelectArea.isEmpty() ^ true ? SkinManager.getThemeColor(this, R.attr.first_text_color) : SkinManager.getThemeColor(this, R.attr.second_txt_color));
            TextView textView2 = this.tvArea;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvArea");
                textView2 = null;
            }
            textView2.setTypeface(this.mListSelectArea.isEmpty() ? Typeface.DEFAULT : Typeface.DEFAULT_BOLD);
            ImageView imageView2 = this.imgArea;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgArea");
                imageView2 = null;
            }
            imageView2.setBackgroundResource(this.mListSelectArea.isEmpty() ^ true ? R.drawable.icon_arrow_first_txt_color_bottom : R.drawable.icon_arrow_second_txt_color_bottom);
        }
        if (index == 1) {
            TextView textView3 = this.tvAttribute;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvAttribute");
                textView3 = null;
            }
            textView3.setTextColor(this.mListSelectAttribute.isEmpty() ^ true ? SkinManager.getThemeColor(this, R.attr.first_text_color) : SkinManager.getThemeColor(this, R.attr.second_txt_color));
            TextView textView4 = this.tvAttribute;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvAttribute");
                textView4 = null;
            }
            textView4.setTypeface(this.mListSelectAttribute.isEmpty() ? Typeface.DEFAULT : Typeface.DEFAULT_BOLD);
            ImageView imageView3 = this.imgAttribute;
            if (imageView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgAttribute");
                imageView3 = null;
            }
            imageView3.setBackgroundResource(this.mListSelectAttribute.isEmpty() ^ true ? R.drawable.icon_arrow_first_txt_color_bottom : R.drawable.icon_arrow_second_txt_color_bottom);
        }
        if (index == 2) {
            TextView textView5 = this.tvType;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvType");
                textView5 = null;
            }
            textView5.setTextColor(this.mListSelectType.isEmpty() ^ true ? SkinManager.getThemeColor(this, R.attr.first_text_color) : SkinManager.getThemeColor(this, R.attr.second_txt_color));
            TextView textView6 = this.tvType;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvType");
                textView6 = null;
            }
            textView6.setTypeface(this.mListSelectType.isEmpty() ? Typeface.DEFAULT : Typeface.DEFAULT_BOLD);
            ImageView imageView4 = this.imgType;
            if (imageView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgType");
            } else {
                imageView = imageView4;
            }
            if (!(!this.mListSelectType.isEmpty())) {
                i2 = R.drawable.icon_arrow_second_txt_color_bottom;
            }
            imageView.setBackgroundResource(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$0(ChooseSchoolListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$1(ChooseSchoolListActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        LinearLayout linearLayout = this$0.layoutContent;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutContent");
            linearLayout = null;
        }
        this$0.height = linearLayout.getMeasuredHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$2(ChooseSchoolListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (FastClickUtil.isFastClick()) {
            return;
        }
        this$0.showAreaChooseDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$3(ChooseSchoolListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (FastClickUtil.isFastClick()) {
            return;
        }
        this$0.showSchoolAttributeChooseDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$4(ChooseSchoolListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (FastClickUtil.isFastClick()) {
            return;
        }
        this$0.showSchoolTypeChooseDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$5(ChooseSchoolListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        RelativeLayout relativeLayout = this$0.layoutHint;
        if (relativeLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutHint");
            relativeLayout = null;
        }
        ViewExtensionsKt.gone(relativeLayout);
    }

    private final void showAreaChooseDialog() {
        LinearLayout linearLayout;
        for (ChooseSchoolFilterBean chooseSchoolFilterBean : this.mListArea) {
            chooseSchoolFilterBean.setSelected(this.mListSelectArea.contains(chooseSchoolFilterBean.getId()));
        }
        ImageView imageView = this.imgArea;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgArea");
            imageView = null;
        }
        showOrHiddenArrow(true, imageView);
        LinearLayout linearLayout2 = this.layoutFilter;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutFilter");
            linearLayout = null;
        } else {
            linearLayout = linearLayout2;
        }
        new ChooseAreaFilterPopUpWindow(this, linearLayout, this.height, this.mListArea, new ChooseAreaFilterPopUpWindow.ProjectChooseInterface() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolListActivity.showAreaChooseDialog.1
            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemDismissListener() {
                ChooseSchoolListActivity chooseSchoolListActivity = ChooseSchoolListActivity.this;
                ImageView imageView2 = chooseSchoolListActivity.imgArea;
                if (imageView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("imgArea");
                    imageView2 = null;
                }
                chooseSchoolListActivity.showOrHiddenArrow(false, imageView2);
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemListener(int choosePos, @Nullable ChooseSchoolFilterBean type) {
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mSubmitListener(@NotNull List<ChooseSchoolFilterBean> mList) {
                Intrinsics.checkNotNullParameter(mList, "mList");
                ChooseSchoolListActivity.this.mListSelectArea.clear();
                for (ChooseSchoolFilterBean chooseSchoolFilterBean2 : mList) {
                    if (chooseSchoolFilterBean2.getSelected()) {
                        ChooseSchoolListActivity.this.mListSelectArea.add(chooseSchoolFilterBean2.getId());
                    }
                }
                ChooseSchoolListActivity.this.notifyListDataChanged(0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showOrHiddenArrow(boolean isShow, final ImageView arrowImg) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300L);
        if (isShow) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 180);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.t
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ChooseSchoolListActivity.showOrHiddenArrow$lambda$6(arrowImg, valueAnimator);
                }
            });
            animatorSet.playTogether(valueAnimatorOfInt);
            animatorSet.start();
            return;
        }
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(180, 0);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.u
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ChooseSchoolListActivity.showOrHiddenArrow$lambda$7(arrowImg, valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt2);
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showOrHiddenArrow$lambda$6(ImageView arrowImg, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(arrowImg, "$arrowImg");
        Intrinsics.checkNotNullParameter(animation, "animation");
        Intrinsics.checkNotNull(animation.getAnimatedValue(), "null cannot be cast to non-null type kotlin.Int");
        arrowImg.setRotation(((Integer) r2).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showOrHiddenArrow$lambda$7(ImageView arrowImg, ValueAnimator animation2) {
        Intrinsics.checkNotNullParameter(arrowImg, "$arrowImg");
        Intrinsics.checkNotNullParameter(animation2, "animation2");
        Intrinsics.checkNotNull(animation2.getAnimatedValue(), "null cannot be cast to non-null type kotlin.Int");
        arrowImg.setRotation(((Integer) r2).intValue());
    }

    private final void showSchoolAttributeChooseDialog() {
        LinearLayout linearLayout;
        for (ChooseSchoolFilterBean chooseSchoolFilterBean : this.mListAttribute) {
            chooseSchoolFilterBean.setSelected(this.mListSelectAttribute.contains(chooseSchoolFilterBean.getId()));
        }
        ImageView imageView = this.imgAttribute;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgAttribute");
            imageView = null;
        }
        showOrHiddenArrow(true, imageView);
        LinearLayout linearLayout2 = this.layoutFilter;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutFilter");
            linearLayout = null;
        } else {
            linearLayout = linearLayout2;
        }
        new ChooseAreaFilterPopUpWindow(this, linearLayout, this.height, this.mListAttribute, new ChooseAreaFilterPopUpWindow.ProjectChooseInterface() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolListActivity$showSchoolAttributeChooseDialog$window$1
            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemDismissListener() {
                ChooseSchoolListActivity chooseSchoolListActivity = this.this$0;
                ImageView imageView2 = chooseSchoolListActivity.imgAttribute;
                if (imageView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("imgAttribute");
                    imageView2 = null;
                }
                chooseSchoolListActivity.showOrHiddenArrow(false, imageView2);
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemListener(int choosePos, @Nullable ChooseSchoolFilterBean type) {
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mSubmitListener(@NotNull List<ChooseSchoolFilterBean> mList) {
                Intrinsics.checkNotNullParameter(mList, "mList");
                this.this$0.mListSelectAttribute.clear();
                for (ChooseSchoolFilterBean chooseSchoolFilterBean2 : mList) {
                    if (chooseSchoolFilterBean2.getSelected()) {
                        this.this$0.mListSelectAttribute.add(chooseSchoolFilterBean2.getId());
                    }
                }
                this.this$0.notifyListDataChanged(1);
            }
        });
    }

    private final void showSchoolTypeChooseDialog() {
        LinearLayout linearLayout;
        for (ChooseSchoolFilterBean chooseSchoolFilterBean : this.mListType) {
            chooseSchoolFilterBean.setSelected(this.mListSelectType.contains(chooseSchoolFilterBean.getId()));
        }
        ImageView imageView = this.imgType;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgType");
            imageView = null;
        }
        showOrHiddenArrow(true, imageView);
        LinearLayout linearLayout2 = this.layoutFilter;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutFilter");
            linearLayout = null;
        } else {
            linearLayout = linearLayout2;
        }
        new ChooseAreaFilterPopUpWindow(this, linearLayout, this.height, this.mListType, new ChooseAreaFilterPopUpWindow.ProjectChooseInterface() { // from class: com.psychiatrygarden.activity.chooseSchool.ChooseSchoolListActivity.showSchoolTypeChooseDialog.1
            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemDismissListener() {
                ChooseSchoolListActivity chooseSchoolListActivity = ChooseSchoolListActivity.this;
                ImageView imageView2 = chooseSchoolListActivity.imgType;
                if (imageView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("imgType");
                    imageView2 = null;
                }
                chooseSchoolListActivity.showOrHiddenArrow(false, imageView2);
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemListener(int choosePos, @Nullable ChooseSchoolFilterBean type) {
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mSubmitListener(@NotNull List<ChooseSchoolFilterBean> mList) {
                Intrinsics.checkNotNullParameter(mList, "mList");
                ChooseSchoolListActivity.this.mListSelectType.clear();
                for (ChooseSchoolFilterBean chooseSchoolFilterBean2 : mList) {
                    if (chooseSchoolFilterBean2.getSelected()) {
                        ChooseSchoolListActivity.this.mListSelectType.add(chooseSchoolFilterBean2.getId());
                    }
                }
                ChooseSchoolListActivity.this.notifyListDataChanged(2);
            }
        });
    }

    @NotNull
    public final List<String> getSelectFilterData() {
        ArrayList arrayList = new ArrayList();
        if (!this.mListSelectArea.isEmpty()) {
            arrayList.add(CollectionsKt___CollectionsKt.joinToString$default(this.mListSelectArea, ",", null, null, 0, null, null, 62, null));
        } else {
            arrayList.add("");
        }
        if (!this.mListSelectAttribute.isEmpty()) {
            arrayList.add(CollectionsKt___CollectionsKt.joinToString$default(this.mListSelectAttribute, ",", null, null, 0, null, null, 62, null));
        } else {
            arrayList.add("");
        }
        if (!this.mListSelectType.isEmpty()) {
            arrayList.add(CollectionsKt___CollectionsKt.joinToString$default(this.mListSelectType, ",", null, null, 0, null, null, 62, null));
        } else {
            arrayList.add("");
        }
        return arrayList;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        initStatusBar();
        this.baoCount = getIntent().getStringExtra("baoCount");
        this.wenCount = getIntent().getStringExtra("wenCount");
        this.chongCount = getIntent().getStringExtra("chongCount");
        this.nanCount = getIntent().getStringExtra("nanCount");
        this.major_id = getIntent().getStringExtra("major_id");
        this.score = getIntent().getStringExtra("score");
        this.index = getIntent().getIntExtra("index", 0);
        View viewFindViewById = findViewById(R.id.layoutHint);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.layoutHint)");
        this.layoutHint = (RelativeLayout) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.layoutContent);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.layoutContent)");
        this.layoutContent = (LinearLayout) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.layoutFilter);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.layoutFilter)");
        this.layoutFilter = (LinearLayout) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.layoutArea);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.layoutArea)");
        this.layoutArea = (LinearLayout) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.layoutSchoolAttribute);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.layoutSchoolAttribute)");
        this.layoutSchoolAttribute = (LinearLayout) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.layoutSchoolType);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.layoutSchoolType)");
        this.layoutSchoolType = (LinearLayout) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.layoutSchoolMajor);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.layoutSchoolMajor)");
        this.layoutSchoolMajor = (LinearLayout) viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.txt_actionbar_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.txt_actionbar_title)");
        this.txtActionbarTitle = (TextView) viewFindViewById8;
        View viewFindViewById9 = findViewById(R.id.iv_actionbar_back);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(R.id.iv_actionbar_back)");
        this.ivActionbarBack = (ImageView) viewFindViewById9;
        View viewFindViewById10 = findViewById(R.id.magicIndicator);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(R.id.magicIndicator)");
        this.magicIndicator = (MagicIndicator) viewFindViewById10;
        View viewFindViewById11 = findViewById(R.id.viewPager);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById(R.id.viewPager)");
        this.viewPager = (ViewPager) viewFindViewById11;
        TextView textView = this.txtActionbarTitle;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("txtActionbarTitle");
            textView = null;
        }
        textView.setText("院校");
        View viewFindViewById12 = findViewById(R.id.tvArea);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById12, "findViewById(R.id.tvArea)");
        this.tvArea = (TextView) viewFindViewById12;
        View viewFindViewById13 = findViewById(R.id.imgArea);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById13, "findViewById(R.id.imgArea)");
        this.imgArea = (ImageView) viewFindViewById13;
        View viewFindViewById14 = findViewById(R.id.tvAttribute);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById14, "findViewById(R.id.tvAttribute)");
        this.tvAttribute = (TextView) viewFindViewById14;
        View viewFindViewById15 = findViewById(R.id.imgAttribute);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById15, "findViewById(R.id.imgAttribute)");
        this.imgAttribute = (ImageView) viewFindViewById15;
        View viewFindViewById16 = findViewById(R.id.tvType);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById16, "findViewById(R.id.tvType)");
        this.tvType = (TextView) viewFindViewById16;
        View viewFindViewById17 = findViewById(R.id.imgType);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById17, "findViewById(R.id.imgType)");
        this.imgType = (ImageView) viewFindViewById17;
        initTab();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        super.initStatusBar();
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, isNightTheme() ? R.color.zx_color_school_list_top_start_night : R.color.zx_color_school_list_top_start_day), 0);
        getWindow().getDecorView().setSystemUiVisibility(8192);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.activity_choose_school_list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        ImageView imageView = this.ivActionbarBack;
        LinearLayout linearLayout = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivActionbarBack");
            imageView = null;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.v
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolListActivity.setListenerForWidget$lambda$0(this.f11431c, view);
            }
        });
        LinearLayout linearLayout2 = this.layoutContent;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutContent");
            linearLayout2 = null;
        }
        linearLayout2.post(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.w
            @Override // java.lang.Runnable
            public final void run() {
                ChooseSchoolListActivity.setListenerForWidget$lambda$1(this.f11439c);
            }
        });
        LinearLayout linearLayout3 = this.layoutArea;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutArea");
            linearLayout3 = null;
        }
        linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.x
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolListActivity.setListenerForWidget$lambda$2(this.f11455c, view);
            }
        });
        LinearLayout linearLayout4 = this.layoutSchoolAttribute;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutSchoolAttribute");
            linearLayout4 = null;
        }
        linearLayout4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolListActivity.setListenerForWidget$lambda$3(this.f11466c, view);
            }
        });
        LinearLayout linearLayout5 = this.layoutSchoolType;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutSchoolType");
        } else {
            linearLayout = linearLayout5;
        }
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.z
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolListActivity.setListenerForWidget$lambda$4(this.f11475c, view);
            }
        });
        ((ImageView) findViewById(R.id.imgDel)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.a0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseSchoolListActivity.setListenerForWidget$lambda$5(this.f11182c, view);
            }
        });
    }
}
