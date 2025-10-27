package com.psychiatrygarden.activity.mine.cutquestion;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
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
import com.aliyun.vod.common.utils.UriUtil;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.QuestionCategoryBean;
import com.psychiatrygarden.bean.SelectFragmentEvent;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class MyCutQuestionBaseFragment extends BaseFragment {
    private MagicIndicator magic_indicator;
    private ViewPager viewpager;
    public int currentPosition = 0;
    private final List<Boolean> isShowEditList = new ArrayList();
    private List<SelectIdentityBean.DataBean> tabChildList = new ArrayList();
    private String level1Id = "";
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionBaseFragment.2
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
            MyCutQuestionBaseFragment.this.magic_indicator.onPageScrollStateChanged(state);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            MyCutQuestionBaseFragment.this.magic_indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            MyCutQuestionBaseFragment.this.magic_indicator.onPageSelected(position);
            MyCutQuestionBaseFragment.this.currentPosition = position;
            try {
                EventBus.getDefault().post(new SelectFragmentEvent(position));
            } catch (Exception e2) {
                Log.d("error", "onPageSelected: " + e2.getMessage());
            }
        }
    };

    /* renamed from: com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionBaseFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends CommonNavigatorAdapter {
        final /* synthetic */ List val$children;

        public AnonymousClass1(final List val$children) {
            this.val$children = val$children;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            MyCutQuestionBaseFragment.this.viewpager.setCurrentItem(i2);
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
        public IPagerTitleView getTitleView(final Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(MyCutQuestionBaseFragment.this.getActivity());
            commonPagerTitleView.setContentView(R.layout.item_forum_tab);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.item_question_column);
            textView.setText(((SelectIdentityBean.DataBean) this.val$children.get(index)).getTitle());
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.o
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f12812c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionBaseFragment.1.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(SkinManager.getThemeColor(context, R.attr.new_bg_two_color));
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(MyCutQuestionBaseFragment.this.requireContext(), 8.0f));
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
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(MyCutQuestionBaseFragment.this.requireContext(), 8.0f));
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.main_theme_color));
                    textView.setBackground(gradientDrawable);
                }
            });
            return commonPagerTitleView;
        }
    }

    private void initTabData(final List<SelectIdentityBean.DataBean> children) throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(false);
        commonNavigator.setAdapter(new AnonymousClass1(children));
        this.magic_indicator.setNavigator(commonNavigator);
        if (this.mContext == null) {
            return;
        }
        for (int i2 = 0; i2 < children.size(); i2++) {
            this.isShowEditList.add(Boolean.FALSE);
        }
        List<BaseViewPagerAdapter.PagerInfo> viewPageInfo = getViewPageInfo(children);
        if (viewPageInfo == null || viewPageInfo.size() <= 1) {
            this.magic_indicator.setVisibility(8);
        } else {
            this.magic_indicator.setVisibility(0);
        }
        this.viewpager.setAdapter(new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), viewPageInfo));
        this.viewpager.setOffscreenPageLimit(3);
        this.viewpager.setCurrentItem(this.currentPosition);
        this.magic_indicator.onPageSelected(this.currentPosition);
        this.viewpager.addOnPageChangeListener(this.onPageChangeListener);
        this.viewpager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.cutquestion.n
            @Override // java.lang.Runnable
            public final void run() {
                this.f12810c.lambda$initTabData$0(children);
            }
        }, 200L);
        EventBus.getDefault().post(new SelectFragmentEvent(0));
    }

    private void isShowEdit() {
        this.isShowEditList.get(this.currentPosition).booleanValue();
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

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_my_cut_question_base;
    }

    public List<BaseViewPagerAdapter.PagerInfo> getViewPageInfo(List<SelectIdentityBean.DataBean> children) {
        ArrayList arrayList = new ArrayList();
        if (children != null && children.size() > 0) {
            for (int i2 = 0; i2 < children.size(); i2++) {
                Bundle bundle = new Bundle();
                bundle.putString("identity_id", "" + children.get(i2).getIdentity_id());
                bundle.putString(UriUtil.QUERY_CATEGORY, "" + children.get(i2).getCategory());
                bundle.putString("module_type", "" + children.get(i2).getModule_type());
                bundle.putString(KnowledgeQuestionListFragment.EXTRA_LEVEL1_ID, "" + this.level1Id);
                if (children.get(i2).getCategory().equals("year")) {
                    SharePreferencesUtils.writeStrConfig(CommonParameter.default_identity_id, children.get(i2).getDefault_identity_id() + "", this.mContext);
                }
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(children.get(i2).getTitle(), MyCutQuestionFragment.class, bundle));
            }
        }
        return arrayList;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.magic_indicator = (MagicIndicator) holder.get(R.id.magic_indicator);
        this.viewpager = (ViewPager) holder.get(R.id.viewpager);
        Bundle arguments = getArguments();
        if (arguments != null) {
            List list = (List) arguments.getSerializable("tabList");
            this.level1Id = arguments.getString("id", "");
            if (list == null || list.isEmpty()) {
                return;
            }
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (this.level1Id.equals(((QuestionCategoryBean) list.get(i2)).getId())) {
                    this.tabChildList.clear();
                    if (((QuestionCategoryBean) list.get(i2)).getChildren() == null || ((QuestionCategoryBean) list.get(i2)).getChildren().isEmpty()) {
                        return;
                    } else {
                        this.tabChildList.addAll(((QuestionCategoryBean) list.get(i2)).getChildren());
                    }
                }
            }
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) throws Resources.NotFoundException {
        super.onLazyInitView(savedInstanceState);
        initTabData(this.tabChildList);
        EventBus.getDefault().post(new SelectFragmentEvent(this.currentPosition));
    }

    public void setListenerForWidget() {
    }
}
