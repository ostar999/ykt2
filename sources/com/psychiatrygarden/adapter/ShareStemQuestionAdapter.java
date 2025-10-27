package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.psychiatrygarden.bean.QuestionIndexBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class ShareStemQuestionAdapter extends CommonNavigatorAdapter {
    private List<QuestionIndexBean> data;
    private ViewPager mViewPager;

    public ShareStemQuestionAdapter(List<QuestionIndexBean> data, ViewPager viewPager) {
        this.mViewPager = viewPager;
        if (data != null) {
            this.data = data;
        } else {
            this.data = new ArrayList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
        this.mViewPager.setCurrentItem(i2);
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
    public int getCount() {
        return this.data.size();
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
    public IPagerIndicator getIndicator(Context context) {
        return null;
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
    public IPagerTitleView getTitleView(Context context, final int index) {
        CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
        commonPagerTitleView.setContentView(R.layout.item_share_stem_question_column);
        final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_column_name);
        textView.setText(this.data.get(index).getTitle());
        commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.re
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                this.f14974c.lambda$getTitleView$0(index, view);
            }
        });
        commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.adapter.ShareStemQuestionAdapter.1
            @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
            public void onDeselected(int index2, int totalCount) {
                textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(ShareStemQuestionAdapter.this.mViewPager.getContext()) == 1 ? "#606A8A" : "#555555"));
                textView.setTextSize(14.0f);
                textView.setTypeface(Typeface.DEFAULT);
            }

            @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
            public void onEnter(int index2, int totalCount, float enterPercent, boolean leftToRight) {
            }

            @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
            public void onLeave(int index2, int totalCount, float leavePercent, boolean leftToRight) {
            }

            @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
            public void onSelected(int index2, int totalCount) {
                textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(ShareStemQuestionAdapter.this.mViewPager.getContext()) == 1 ? "#B2575C" : "#DD594A"));
                textView.setTextSize(16.0f);
                textView.setTypeface(Typeface.DEFAULT_BOLD);
            }
        });
        return commonPagerTitleView;
    }
}
