package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.exoplayer2.ExoPlayer;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.QuestionCategoryBean;
import com.psychiatrygarden.bean.QuestionCombineTabItem;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CombineQuestionBaseFragment extends BaseFragment {
    private BaseQuickAdapter<QuestionCombineTabItem, BaseViewHolder> mAdapter;
    private AnimationDrawable mAnimationDrawable;
    private LinearLayout mLyContentView;
    private LinearLayout mLyLoadingView;
    private String mType;
    private ViewPager mViewPager;
    private MagicIndicator magicIndicator;
    private RecyclerView rvRab;
    private String tabType;
    private List<SelectIdentityBean.DataBean> children = new ArrayList();
    private boolean isKnowledge = false;
    private int curSelectIndex = 0;
    private final CountDownTimer mCountDownTimer = new CountDownTimer(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS, 1000) { // from class: com.psychiatrygarden.fragmenthome.CombineQuestionBaseFragment.1
        @Override // android.os.CountDownTimer
        public void onFinish() {
            CombineQuestionBaseFragment.this.mLyContentView.setVisibility(0);
            CombineQuestionBaseFragment.this.mLyLoadingView.setVisibility(8);
            if (CombineQuestionBaseFragment.this.mAnimationDrawable != null && CombineQuestionBaseFragment.this.mAnimationDrawable.isRunning()) {
                CombineQuestionBaseFragment.this.mAnimationDrawable.stop();
            }
            CombineQuestionBaseFragment.this.mCountDownTimer.cancel();
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
        }
    };
    private String idLevel1 = "";

    /* renamed from: com.psychiatrygarden.fragmenthome.CombineQuestionBaseFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends CommonNavigatorAdapter {
        final /* synthetic */ List val$questionCombineTabItems;

        public AnonymousClass2(final List val$questionCombineTabItems) {
            this.val$questionCombineTabItems = val$questionCombineTabItems;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            CombineQuestionBaseFragment.this.mViewPager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return this.val$questionCombineTabItems.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(final Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(CombineQuestionBaseFragment.this.getActivity());
            commonPagerTitleView.setContentView(R.layout.item_knowledge_tab_customer2);
            final RelativeLayout relativeLayout = (RelativeLayout) commonPagerTitleView.findViewById(R.id.layout);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.title);
            final ImageView imageView = (ImageView) commonPagerTitleView.findViewById(R.id.img);
            final boolean z2 = ("1".equals(CombineQuestionBaseFragment.this.tabType) && "0".equals(((SelectIdentityBean.DataBean) CombineQuestionBaseFragment.this.children.get(index)).getHas_permission())) ? false : true;
            imageView.setVisibility(z2 ? 8 : 0);
            textView.setText(((QuestionCombineTabItem) this.val$questionCombineTabItems.get(index)).getTitle());
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.i0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f15655c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.fragmenthome.CombineQuestionBaseFragment.2.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.first_txt_color));
                    CombineQuestionBaseFragment combineQuestionBaseFragment = CombineQuestionBaseFragment.this;
                    combineQuestionBaseFragment.setTabBackground(((BaseFragment) combineQuestionBaseFragment).mContext, relativeLayout, false);
                    if (z2) {
                        return;
                    }
                    imageView.setImageResource(CombineQuestionBaseFragment.this.getDrawableResId(false));
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
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.main_theme_color));
                    CombineQuestionBaseFragment combineQuestionBaseFragment = CombineQuestionBaseFragment.this;
                    combineQuestionBaseFragment.setTabBackground(((BaseFragment) combineQuestionBaseFragment).mContext, relativeLayout, true);
                    if (z2) {
                        return;
                    }
                    imageView.setImageResource(CombineQuestionBaseFragment.this.getDrawableResId(true));
                }
            });
            return commonPagerTitleView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDrawableResId(boolean isSelect) {
        return isSelect ? SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.ic_knowledge_chart_lock_select_night : R.drawable.ic_knowledge_chart_lock_select_day : SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.ic_knowledge_chart_lock_night : R.drawable.ic_knowledge_chart_lock_day;
    }

    private void getTabList() {
        Bundle arguments = getArguments();
        List list = (List) arguments.getSerializable("tabList");
        this.idLevel1 = arguments.getString("id", "");
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (this.idLevel1.equals(((QuestionCategoryBean) list.get(i2)).getId())) {
                this.isKnowledge = "1".equals(((QuestionCategoryBean) list.get(i2)).getType());
                this.children.clear();
                if (((QuestionCategoryBean) list.get(i2)).getChildren() == null || ((QuestionCategoryBean) list.get(i2)).getChildren().isEmpty()) {
                    return;
                } else {
                    this.children.addAll(((QuestionCategoryBean) list.get(i2)).getChildren());
                }
            }
        }
    }

    private void initTab(List<SelectIdentityBean.DataBean> columnItems, String type) throws Resources.NotFoundException {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i2 = 0;
        while (true) {
            if (i2 >= columnItems.size()) {
                this.mViewPager.setAdapter(new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), arrayList2));
                CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
                commonNavigator.setSkimOver(true);
                commonNavigator.setAdapter(new AnonymousClass2(arrayList));
                this.magicIndicator.setNavigator(commonNavigator);
                ViewPagerHelper.bind(this.magicIndicator, this.mViewPager);
                this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.fragmenthome.CombineQuestionBaseFragment.3
                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public void onPageScrollStateChanged(int state) {
                    }

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        CombineQuestionBaseFragment.this.curSelectIndex = position;
                    }

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public void onPageSelected(int position) {
                    }
                });
                this.mViewPager.setCurrentItem(0);
                this.mViewPager.setOffscreenPageLimit(columnItems.size() - 1);
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("type", type);
            bundle.putString(CombineQuestionFragment.EXTRA_DATA_LEVEL1_ID, this.idLevel1);
            if ("1".equals(this.tabType)) {
                bundle.putString("identity_id", columnItems.get(i2).getId());
                bundle.putBoolean("havePermission", !"0".equals(columnItems.get(i2).getHas_permission()));
            } else {
                bundle.putString("identity_id", columnItems.get(i2).getIdentity_id());
                bundle.putBoolean("havePermission", true);
            }
            bundle.putString("tabType", this.tabType);
            arrayList2.add(new BaseViewPagerAdapter.PagerInfo("", CombineQuestionFragment.class, bundle));
            if (this.isKnowledge) {
                arrayList.add(new QuestionCombineTabItem(i2 == 0, columnItems.get(i2).getName()));
            } else {
                arrayList.add(new QuestionCombineTabItem(i2 == 0, columnItems.get(i2).getTitle()));
            }
            i2++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTabBackground(Context context, RelativeLayout layout, boolean isSelect) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        if (isSelect) {
            gradientDrawable.setColor(SkinManager.getThemeColor(context, R.attr.main_theme_five_deep_color));
        } else {
            gradientDrawable.setColor(SkinManager.getThemeColor(context, R.attr.app_bg));
        }
        gradientDrawable.setCornerRadius(CommonUtil.dip2px(requireContext(), 8.0f));
        layout.setBackground(gradientDrawable);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_combine_question_base;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mType = arguments.getString("type", "");
            this.tabType = arguments.getString("tabType", "");
        }
        this.rvRab = (RecyclerView) holder.get(R.id.rvTab);
        this.mViewPager = (ViewPager) holder.get(R.id.viewPager);
        this.mLyContentView = (LinearLayout) holder.get(R.id.ly_content_view);
        LinearLayout linearLayout = (LinearLayout) holder.get(R.id.ly_loading_view);
        this.mLyLoadingView = linearLayout;
        linearLayout.setVisibility(8);
        this.mLyContentView.setVisibility(0);
        this.magicIndicator = (MagicIndicator) holder.get(R.id.magicIndicator);
        getTabList();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) throws Resources.NotFoundException {
        super.onLazyInitView(savedInstanceState);
        initTab(this.children, this.mType);
    }
}
