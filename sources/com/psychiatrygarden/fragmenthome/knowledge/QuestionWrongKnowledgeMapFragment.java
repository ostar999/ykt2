package com.psychiatrygarden.fragmenthome.knowledge;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import com.psychiatrygarden.bean.KnowledgeListType;
import com.psychiatrygarden.bean.QuestionCategoryBean;
import com.psychiatrygarden.bean.SelectErrorWrongFragmentEvent;
import com.psychiatrygarden.bean.SelectFragmentEvent;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionWrongKnowledgeMapFragment extends BaseFragment {
    private String domain_type;
    private String identity_id;
    private String is_show_number;
    private MagicIndicator magic_indicator;
    private ViewPager viewpager;
    private List<SelectIdentityBean.DataBean> children = new ArrayList();
    private int mItemWidth = 0;
    public int currentPosition = 0;
    private String level1Id = "";
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.QuestionWrongKnowledgeMapFragment.2
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
            QuestionWrongKnowledgeMapFragment.this.magic_indicator.onPageScrollStateChanged(state);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            QuestionWrongKnowledgeMapFragment.this.magic_indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            QuestionWrongKnowledgeMapFragment.this.magic_indicator.onPageSelected(position);
            QuestionWrongKnowledgeMapFragment.this.currentPosition = position;
            EventBus.getDefault().post(new SelectErrorWrongFragmentEvent(QuestionWrongKnowledgeMapFragment.this.currentPosition));
        }
    };

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.QuestionWrongKnowledgeMapFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends CommonNavigatorAdapter {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            QuestionWrongKnowledgeMapFragment.this.viewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            if (QuestionWrongKnowledgeMapFragment.this.children == null) {
                return 0;
            }
            return QuestionWrongKnowledgeMapFragment.this.children.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(final Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(QuestionWrongKnowledgeMapFragment.this.getActivity());
            commonPagerTitleView.setContentView(R.layout.item_knowledge_tab_customer);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.item_question_column);
            textView.setText(((SelectIdentityBean.DataBean) QuestionWrongKnowledgeMapFragment.this.children.get(index)).getName());
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.k0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f15762c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.QuestionWrongKnowledgeMapFragment.1.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(SkinManager.getThemeColor(context, R.attr.new_bg_two_color));
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(QuestionWrongKnowledgeMapFragment.this.requireContext(), 8.0f));
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
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(QuestionWrongKnowledgeMapFragment.this.requireContext(), 8.0f));
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.main_theme_color));
                    textView.setBackground(gradientDrawable);
                }
            });
            return commonPagerTitleView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTab$0() {
        if (this.children.isEmpty()) {
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

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_question_wrong_knowledge_map;
    }

    public List<BaseViewPagerAdapter.PagerInfo> getViewPageInfo() {
        ArrayList arrayList = new ArrayList();
        List<SelectIdentityBean.DataBean> list = this.children;
        if (list != null && !list.isEmpty()) {
            for (int i2 = 0; i2 < this.children.size(); i2++) {
                Bundle bundle = new Bundle();
                bundle.putString(KnowledgeQuestionListFragment.EXTRA_TREE_ID, this.children.get(i2).getId());
                bundle.putString(KnowledgeQuestionListFragment.EXTRA_DOMAIN_TYPE, this.domain_type);
                bundle.putString(KnowledgeQuestionListFragment.EXTRA_LEVEL1_ID, this.level1Id);
                if (!this.domain_type.equals(KnowledgeListType.CUT.getType())) {
                    bundle.putString("show.dialog", "1");
                }
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(this.children.get(i2).getTitle(), KnowledgeQuestionListFragment.class, bundle));
            }
        }
        return arrayList;
    }

    public void initTab() throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(false);
        commonNavigator.setAdapter(new AnonymousClass1());
        this.magic_indicator.setNavigator(commonNavigator);
        if (this.mContext == null) {
            return;
        }
        List<BaseViewPagerAdapter.PagerInfo> viewPageInfo = getViewPageInfo();
        this.magic_indicator.setVisibility(0);
        this.viewpager.setAdapter(new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), viewPageInfo));
        this.viewpager.setOffscreenPageLimit(3);
        this.viewpager.setCurrentItem(this.currentPosition);
        this.magic_indicator.onPageSelected(this.currentPosition);
        this.viewpager.addOnPageChangeListener(this.onPageChangeListener);
        this.viewpager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.knowledge.j0
            @Override // java.lang.Runnable
            public final void run() {
                this.f15760c.lambda$initTab$0();
            }
        }, 200L);
        EventBus.getDefault().post(new SelectFragmentEvent(this.currentPosition));
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.identity_id = arguments.getString("identity_id", "");
            this.is_show_number = arguments.getString("is_show_number", "");
            this.domain_type = arguments.getString(KnowledgeQuestionListFragment.EXTRA_DOMAIN_TYPE, "");
            List list = (List) arguments.getSerializable("tabList");
            this.level1Id = arguments.getString("id", "");
            if (list == null || list.isEmpty()) {
                return;
            }
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (this.level1Id.equals(((QuestionCategoryBean) list.get(i2)).getId())) {
                    this.children.clear();
                    if (((QuestionCategoryBean) list.get(i2)).getChildren() == null || ((QuestionCategoryBean) list.get(i2)).getChildren().isEmpty()) {
                        return;
                    } else {
                        this.children.addAll(((QuestionCategoryBean) list.get(i2)).getChildren());
                    }
                }
            }
        }
        this.mItemWidth = CommonUtil.getScreenWidth(this.mContext) / 4;
        this.magic_indicator = (MagicIndicator) holder.get(R.id.magic_indicator);
        this.viewpager = (ViewPager) holder.get(R.id.viewpager);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) throws Resources.NotFoundException {
        super.onLazyInitView(savedInstanceState);
        initTab();
        EventBus.getDefault().post(new SelectErrorWrongFragmentEvent(this.currentPosition));
    }

    public void setListenerForWidget() {
    }
}
