package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.ArrayMap;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class MagicNoIndicatorAdapter extends CommonNavigatorAdapter {
    private final List<SelectIdentityBean.DataBean> children;
    private final CommonNavigator commonNavigator;
    private int currentIndex;
    private boolean initPageCallBack;
    private OnSelectListener mOnSelectListener;
    private ArrayMap<Integer, ImageView> viewMap = new ArrayMap<>();
    private final ViewPager viewPager;

    public interface OnSelectListener {
        void onDeselected(int index);

        void onSelected(int index);
    }

    public MagicNoIndicatorAdapter(List<SelectIdentityBean.DataBean> children, ViewPager viewPager, CommonNavigator commonNavigator) {
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
        return null;
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
    public IPagerTitleView getTitleView(Context context, final int index) {
        CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
        View viewInflate = View.inflate(context, R.layout.item_tab_combine_question, null);
        commonPagerTitleView.setContentView(viewInflate);
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.ic_triangle_down);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 81;
        commonPagerTitleView.addView(imageView, layoutParams);
        final CheckedTextView checkedTextView = (CheckedTextView) viewInflate.findViewById(R.id.tv_column_name);
        checkedTextView.setText(this.children.get(index).getTitle());
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) viewInflate.getLayoutParams();
        layoutParams2.leftMargin = index == 0 ? 0 : CommonUtil.dip2px(context, 12.0f);
        viewInflate.setLayoutParams(layoutParams2);
        if (!this.initPageCallBack) {
            this.initPageCallBack = true;
            this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.adapter.MagicNoIndicatorAdapter.1
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int state) {
                    if (MagicNoIndicatorAdapter.this.commonNavigator != null) {
                        MagicNoIndicatorAdapter.this.commonNavigator.onPageScrollStateChanged(state);
                    }
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (MagicNoIndicatorAdapter.this.commonNavigator != null) {
                        MagicNoIndicatorAdapter.this.commonNavigator.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int position) {
                    if (MagicNoIndicatorAdapter.this.commonNavigator != null) {
                        MagicNoIndicatorAdapter.this.commonNavigator.onPageSelected(position);
                    }
                }
            });
        }
        commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.ga
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                this.f14522c.lambda$getTitleView$0(index, view);
            }
        });
        commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.adapter.MagicNoIndicatorAdapter.2
            @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
            public void onDeselected(int index2, int totalCount) {
                checkedTextView.setChecked(false);
                if (SkinManager.getCurrentSkinType(checkedTextView.getContext()) == 1) {
                    checkedTextView.setTextColor(Color.parseColor("#7380A9"));
                } else {
                    checkedTextView.setTextColor(Color.parseColor("#303030"));
                }
                if (MagicNoIndicatorAdapter.this.mOnSelectListener != null) {
                    MagicNoIndicatorAdapter.this.mOnSelectListener.onDeselected(index2);
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
                checkedTextView.setChecked(true);
                checkedTextView.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
                if (MagicNoIndicatorAdapter.this.mOnSelectListener != null) {
                    MagicNoIndicatorAdapter.this.mOnSelectListener.onSelected(index2);
                }
                MagicNoIndicatorAdapter.this.currentIndex = index2;
            }
        });
        return commonPagerTitleView;
    }
}
