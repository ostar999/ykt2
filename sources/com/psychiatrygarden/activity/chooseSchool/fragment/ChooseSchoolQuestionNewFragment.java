package com.psychiatrygarden.activity.chooseSchool.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.chooseSchool.fragment.ChooseSchoolQuestionNewFragment;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ChooseSchoolQuestionBean;
import com.psychiatrygarden.bean.ChooseSchoolQuestionData;
import com.psychiatrygarden.bean.ChooseSchoolQuestionItem;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0014J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u000e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0002J\b\u0010\u0014\u001a\u00020\u0010H\u0002J\u001a\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0014J\u0012\u0010\u001a\u001a\u00020\u00102\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/fragment/ChooseSchoolQuestionNewFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "()V", "data", "Ljava/util/ArrayList;", "Lcom/psychiatrygarden/bean/ChooseSchoolQuestionData;", "Lkotlin/collections/ArrayList;", "magicIndicator", "Lcn/webdemo/com/supporfragment/tablayout/MagicIndicator;", "onPageChangeListener", "Landroidx/viewpager/widget/ViewPager$OnPageChangeListener;", "viewpager", "Landroidx/viewpager/widget/ViewPager;", "getLayoutId", "", "getListData", "", "getViewPageInfo", "", "Lcom/psychiatrygarden/fragmenthome/BaseViewPagerAdapter$PagerInfo;", "initTab", "initViews", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "Landroid/view/View;", "onLazyInitView", "savedInstanceState", "Landroid/os/Bundle;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolQuestionNewFragment extends BaseFragment {
    private MagicIndicator magicIndicator;
    private ViewPager viewpager;

    @NotNull
    private final ArrayList<ChooseSchoolQuestionData> data = new ArrayList<>();

    @NotNull
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.ChooseSchoolQuestionNewFragment$onPageChangeListener$1
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
            MagicIndicator magicIndicator = this.this$0.magicIndicator;
            if (magicIndicator == null) {
                Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
                magicIndicator = null;
            }
            magicIndicator.onPageScrollStateChanged(state);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            MagicIndicator magicIndicator = this.this$0.magicIndicator;
            if (magicIndicator == null) {
                Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
                magicIndicator = null;
            }
            magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            MagicIndicator magicIndicator = this.this$0.magicIndicator;
            if (magicIndicator == null) {
                Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
                magicIndicator = null;
            }
            magicIndicator.onPageSelected(position);
        }
    };

    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/activity/chooseSchool/fragment/ChooseSchoolQuestionNewFragment$initTab$1", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerIndicator;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "getTitleView", "Lcn/webdemo/com/supporfragment/tablayout/buildins/commonnavigator/abs/IPagerTitleView;", "index", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.chooseSchool.fragment.ChooseSchoolQuestionNewFragment$initTab$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05811 extends CommonNavigatorAdapter {
        public C05811() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void getTitleView$lambda$0(ChooseSchoolQuestionNewFragment this$0, int i2, View view) throws Resources.NotFoundException {
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
            return ChooseSchoolQuestionNewFragment.this.data.size();
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
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(ChooseSchoolQuestionNewFragment.this.getActivity());
            commonPagerTitleView.setContentView(R.layout.item_forum_tab);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.item_question_column);
            textView.setText(((ChooseSchoolQuestionData) ChooseSchoolQuestionNewFragment.this.data.get(index)).getTitle());
            final ChooseSchoolQuestionNewFragment chooseSchoolQuestionNewFragment = ChooseSchoolQuestionNewFragment.this;
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.l
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    ChooseSchoolQuestionNewFragment.C05811.getTitleView$lambda$0(chooseSchoolQuestionNewFragment, index, view);
                }
            });
            final ChooseSchoolQuestionNewFragment chooseSchoolQuestionNewFragment2 = ChooseSchoolQuestionNewFragment.this;
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.ChooseSchoolQuestionNewFragment$initTab$1$getTitleView$2
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(SkinManager.getThemeColor(context, R.attr.app_bg));
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(chooseSchoolQuestionNewFragment2.requireContext(), 8.0f));
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
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(chooseSchoolQuestionNewFragment2.requireContext(), 8.0f));
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.main_theme_color));
                    textView.setBackground(gradientDrawable);
                }
            });
            return commonPagerTitleView;
        }
    }

    private final void getListData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.chooseSchoolQuestionList, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.ChooseSchoolQuestionNewFragment.getListData.1
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
                    ChooseSchoolQuestionBean chooseSchoolQuestionBean = (ChooseSchoolQuestionBean) new Gson().fromJson(t2, ChooseSchoolQuestionBean.class);
                    if (!Intrinsics.areEqual(chooseSchoolQuestionBean.getCode(), "200") || chooseSchoolQuestionBean.getData() == null) {
                        return;
                    }
                    ArrayList arrayList = ChooseSchoolQuestionNewFragment.this.data;
                    List<ChooseSchoolQuestionData> data = chooseSchoolQuestionBean.getData();
                    Intrinsics.checkNotNull(data);
                    arrayList.addAll(data);
                    ChooseSchoolQuestionNewFragment.this.initTab();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final List<BaseViewPagerAdapter.PagerInfo> getViewPageInfo() {
        ArrayList arrayList = new ArrayList();
        if (this.data.size() > 0) {
            int size = this.data.size();
            for (int i2 = 0; i2 < size; i2++) {
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + this.data.get(i2).getId());
                List<ChooseSchoolQuestionItem> question_list = this.data.get(i2).getQuestion_list();
                Intrinsics.checkNotNull(question_list, "null cannot be cast to non-null type java.io.Serializable");
                bundle.putSerializable("data", (Serializable) question_list);
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(this.data.get(i2).getTitle(), ChooseSchoolQuestionSubFragment.class, bundle));
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initTab() throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(false);
        commonNavigator.setAdapter(new C05811());
        MagicIndicator magicIndicator = this.magicIndicator;
        ViewPager viewPager = null;
        if (magicIndicator == null) {
            Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
            magicIndicator = null;
        }
        magicIndicator.setNavigator(commonNavigator);
        if (this.mContext == null) {
            return;
        }
        List<BaseViewPagerAdapter.PagerInfo> viewPageInfo = getViewPageInfo();
        MagicIndicator magicIndicator2 = this.magicIndicator;
        if (magicIndicator2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("magicIndicator");
            magicIndicator2 = null;
        }
        magicIndicator2.setVisibility(0);
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), viewPageInfo);
        ViewPager viewPager2 = this.viewpager;
        if (viewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
            viewPager2 = null;
        }
        viewPager2.setAdapter(baseViewPagerAdapter);
        ViewPager viewPager3 = this.viewpager;
        if (viewPager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
            viewPager3 = null;
        }
        viewPager3.setOffscreenPageLimit(3);
        ViewPager viewPager4 = this.viewpager;
        if (viewPager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
            viewPager4 = null;
        }
        viewPager4.addOnPageChangeListener(this.onPageChangeListener);
        ViewPager viewPager5 = this.viewpager;
        if (viewPager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewpager");
        } else {
            viewPager = viewPager5;
        }
        viewPager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.fragment.k
            @Override // java.lang.Runnable
            public final void run() {
                ChooseSchoolQuestionNewFragment.initTab$lambda$0(this.f11274c);
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initTab$lambda$0(ChooseSchoolQuestionNewFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!this$0.data.isEmpty()) {
            try {
                ViewPager viewPager = this$0.viewpager;
                ViewPager viewPager2 = null;
                if (viewPager == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                    viewPager = null;
                }
                viewPager.setScrollX(30);
                ViewPager viewPager3 = this$0.viewpager;
                if (viewPager3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                    viewPager3 = null;
                }
                if (viewPager3.beginFakeDrag()) {
                    ViewPager viewPager4 = this$0.viewpager;
                    if (viewPager4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                        viewPager4 = null;
                    }
                    viewPager4.fakeDragBy(20.0f);
                    ViewPager viewPager5 = this$0.viewpager;
                    if (viewPager5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                        viewPager5 = null;
                    }
                    viewPager5.endFakeDrag();
                    ViewPager viewPager6 = this$0.viewpager;
                    if (viewPager6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("viewpager");
                    } else {
                        viewPager2 = viewPager6;
                    }
                    viewPager2.setScrollX(0);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_choose_school_question_new;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @Nullable View root) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        View view = holder.get(R.id.viewpager);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.viewpager)");
        this.viewpager = (ViewPager) view;
        View view2 = holder.get(R.id.magic_indicator);
        Intrinsics.checkNotNullExpressionValue(view2, "holder.get(R.id.magic_indicator)");
        this.magicIndicator = (MagicIndicator) view2;
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getListData();
    }
}
