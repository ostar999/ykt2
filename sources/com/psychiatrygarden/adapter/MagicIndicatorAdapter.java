package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators.LinePagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class MagicIndicatorAdapter extends CommonNavigatorAdapter {
    private final List<SelectIdentityBean.DataBean> children;
    private final CommonNavigator commonNavigator;
    private int currentIndex;
    private boolean initPageCallBack;
    private OnSelectListener mOnSelectListener;
    private final ViewPager viewPager;

    public interface OnSelectListener {
        void onDeselected(int index);

        void onSelected(int index);
    }

    public MagicIndicatorAdapter(List<SelectIdentityBean.DataBean> children, ViewPager viewPager, CommonNavigator commonNavigator) {
        this.children = children;
        this.commonNavigator = commonNavigator;
        this.viewPager = viewPager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
        this.viewPager.setCurrentItem(i2);
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
    public int getCount() {
        List<SelectIdentityBean.DataBean> list = this.children;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(2);
        linePagerIndicator.setLineHeight(CommonUtil.dip2px(context, 3.0f));
        linePagerIndicator.setLineWidth(CommonUtil.dip2px(context, 16.0f));
        linePagerIndicator.setRoundRadius(CommonUtil.dip2px(context, 10.0f));
        linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
        linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
        linePagerIndicator.setColors(Integer.valueOf(context.getTheme().obtainStyledAttributes(new int[]{R.attr.dominant_color}).getColor(0, SupportMenu.CATEGORY_MASK)));
        return linePagerIndicator;
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
    public IPagerTitleView getTitleView(Context context, final int index) {
        CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
        commonPagerTitleView.setContentView(R.layout.item_question_column);
        commonPagerTitleView.findViewById(R.id.tv_column_tag).setVisibility(8);
        final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_column_name);
        textView.setText(this.children.get(index).getTitle());
        if (!this.initPageCallBack) {
            this.initPageCallBack = true;
            this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.adapter.MagicIndicatorAdapter.1
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int state) {
                    if (MagicIndicatorAdapter.this.commonNavigator != null) {
                        MagicIndicatorAdapter.this.commonNavigator.onPageScrollStateChanged(state);
                    }
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (MagicIndicatorAdapter.this.commonNavigator != null) {
                        MagicIndicatorAdapter.this.commonNavigator.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int position) {
                    if (MagicIndicatorAdapter.this.commonNavigator != null) {
                        MagicIndicatorAdapter.this.commonNavigator.onPageSelected(position);
                    }
                }
            });
        }
        commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.fa
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                this.f14481c.lambda$getTitleView$0(index, view);
            }
        });
        commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.adapter.MagicIndicatorAdapter.2
            @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
            public void onDeselected(int index2, int totalCount) {
                textView.setTextSize(14.0f);
                textView.setTypeface(Typeface.DEFAULT);
                if (SkinManager.getCurrentSkinType(textView.getContext()) == 1) {
                    textView.setTextColor(Color.parseColor("#7380A9"));
                } else {
                    textView.setTextColor(Color.parseColor("#909090"));
                }
                if (MagicIndicatorAdapter.this.mOnSelectListener != null) {
                    MagicIndicatorAdapter.this.mOnSelectListener.onDeselected(index2);
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
                textView.setTextSize(18.0f);
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                if (SkinManager.getCurrentSkinType(textView.getContext()) == 1) {
                    textView.setTextColor(Color.parseColor("#B2575C"));
                } else {
                    textView.setTextColor(Color.parseColor("#303030"));
                }
                if (MagicIndicatorAdapter.this.mOnSelectListener != null) {
                    MagicIndicatorAdapter.this.mOnSelectListener.onSelected(index2);
                }
                MagicIndicatorAdapter.this.currentIndex = index2;
            }
        });
        return commonPagerTitleView;
    }
}
