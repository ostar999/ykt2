package com.psychiatrygarden.ranking;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.QuestionCategoryBean;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class StatisticsMainFragment extends BaseFragment {
    private MagicIndicator magic_indicator;
    private String questionCategoryId;
    private ViewPager viewpager;
    private boolean isKnowledge = false;
    private boolean initTab = false;
    private int currentPositon = 0;
    private List<SelectIdentityBean.DataBean> children = new ArrayList();
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.ranking.StatisticsMainFragment.2
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
            StatisticsMainFragment.this.magic_indicator.onPageScrollStateChanged(state);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            StatisticsMainFragment.this.magic_indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            StatisticsMainFragment.this.magic_indicator.onPageSelected(position);
            StatisticsMainFragment.this.currentPositon = position;
        }
    };

    /* renamed from: com.psychiatrygarden.ranking.StatisticsMainFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends CommonNavigatorAdapter {
        final /* synthetic */ List val$children;

        public AnonymousClass1(final List val$children) {
            this.val$children = val$children;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            StatisticsMainFragment.this.viewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            List list = this.val$children;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(StatisticsMainFragment.this.getActivity());
            commonPagerTitleView.setContentView(R.layout.item_forum_tab);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.item_question_column);
            if (StatisticsMainFragment.this.isKnowledge) {
                textView.setText(((SelectIdentityBean.DataBean) this.val$children.get(index)).getName());
            } else {
                textView.setText(((SelectIdentityBean.DataBean) this.val$children.get(index)).getTitle());
            }
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.c0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f16197c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.ranking.StatisticsMainFragment.1.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(StatisticsMainFragment.this.getContext().getColor(R.color.new_bg_two_color));
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(StatisticsMainFragment.this.requireContext(), 8.0f));
                    textView.setTextColor(StatisticsMainFragment.this.getContext().getColor(R.color.first_txt_color));
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
                    gradientDrawable.setColor(StatisticsMainFragment.this.getContext().getColor(R.color.main_theme_five_deep_color));
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(StatisticsMainFragment.this.requireContext(), 8.0f));
                    textView.setTextColor(StatisticsMainFragment.this.getContext().getColor(R.color.main_theme_color));
                    textView.setBackground(gradientDrawable);
                }
            });
            return commonPagerTitleView;
        }
    }

    private void initTabData(final List<SelectIdentityBean.DataBean> children) throws Resources.NotFoundException {
        this.initTab = true;
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(false);
        commonNavigator.setAdapter(new AnonymousClass1(children));
        this.magic_indicator.setNavigator(commonNavigator);
        if (this.mContext == null) {
            return;
        }
        List<BaseViewPagerAdapter.PagerInfo> viewPageInfo = getViewPageInfo(children);
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), viewPageInfo);
        if (viewPageInfo.size() == 1) {
            this.magic_indicator.setVisibility(8);
        }
        this.viewpager.setAdapter(baseViewPagerAdapter);
        this.viewpager.setOffscreenPageLimit(1);
        this.viewpager.setCurrentItem(this.currentPositon);
        this.magic_indicator.onPageSelected(this.currentPositon);
        this.viewpager.addOnPageChangeListener(this.onPageChangeListener);
        this.viewpager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.ranking.b0
            @Override // java.lang.Runnable
            public final void run() {
                this.f16194c.lambda$initTabData$0(children);
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTabData$0(List list) {
        if (list.isEmpty()) {
            return;
        }
        try {
            this.viewpager.setScrollX(30);
            if (this.viewpager.beginFakeDrag()) {
                this.viewpager.fakeDragBy(20.0f);
                this.viewpager.endFakeDrag();
                this.viewpager.setScrollX(0);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void newIntent(Context context) {
        context.startActivity(new Intent(context, (Class<?>) QuestionStatisticsAct.class));
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_statistics_main;
    }

    public List<BaseViewPagerAdapter.PagerInfo> getViewPageInfo(List<SelectIdentityBean.DataBean> children) {
        ArrayList arrayList = new ArrayList();
        if (children != null && !children.isEmpty()) {
            for (int i2 = 0; i2 < children.size(); i2++) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryId", this.questionCategoryId);
                bundle.putBoolean("isKnowledge", this.isKnowledge);
                bundle.putString("identity_id", this.isKnowledge ? children.get(i2).getId() : children.get(i2).getIdentity_id());
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(children.get(i2).getTitle(), StatisticsFragment.class, bundle));
            }
        }
        return arrayList;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        setNewStyleStatusBarColor();
        this.magic_indicator = (MagicIndicator) holder.get(R.id.magic_indicator);
        this.viewpager = (ViewPager) holder.get(R.id.viewpager);
        Bundle arguments = getArguments();
        if (arguments != null) {
            List list = (List) arguments.getSerializable("tabList");
            String string = arguments.getString("id", "");
            this.questionCategoryId = string;
            if (list == null || list.isEmpty()) {
                return;
            }
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (string.equals(((QuestionCategoryBean) list.get(i2)).getId())) {
                    this.children.clear();
                    this.isKnowledge = "1".equals(((QuestionCategoryBean) list.get(i2)).getType());
                    if (((QuestionCategoryBean) list.get(i2)).getChildren() == null || ((QuestionCategoryBean) list.get(i2)).getChildren().isEmpty()) {
                        return;
                    }
                    if (!"0".equals(string)) {
                        SelectIdentityBean.DataBean dataBean = new SelectIdentityBean.DataBean();
                        dataBean.setTitle("全部统计");
                        dataBean.setName("全部统计");
                        dataBean.setIdentity_id("0");
                        dataBean.setType(((QuestionCategoryBean) list.get(i2)).getType());
                        this.children.add(dataBean);
                    }
                    this.children.addAll(((QuestionCategoryBean) list.get(i2)).getChildren());
                }
            }
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) throws Resources.NotFoundException {
        super.onLazyInitView(savedInstanceState);
        initTabData(this.children);
    }

    public void setNewStyleStatusBarColor() {
    }
}
