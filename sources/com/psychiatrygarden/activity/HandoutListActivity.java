package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators.LinePagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.ClipPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.badge.BadgeAnchor;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.badge.BadgePagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.badge.BadgeRule;
import com.psychiatrygarden.fragmenthome.HandoutSearchFragment;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.MyAdapter;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class HandoutListActivity extends BaseActivity {
    public CommonNavigator mCommonNavigator;
    private final List<Fragment> mFragmentList = new ArrayList();
    private MagicIndicator mMagicIndicator;
    private ViewPager viewpager;

    /* renamed from: com.psychiatrygarden.activity.HandoutListActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends CommonNavigatorAdapter {
        final /* synthetic */ String[] val$mTitleList;

        public AnonymousClass1(final String[] val$mTitleList) {
            this.val$mTitleList = val$mTitleList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            HandoutListActivity.this.viewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return this.val$mTitleList.length;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            linePagerIndicator.setMode(2);
            linePagerIndicator.setLineHeight(UIUtil.dip2px(context, 3.0d));
            linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 20.0d));
            linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 3.0d));
            linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
            linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
            linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(HandoutListActivity.this.mContext, SkinManager.getCurrentSkinType(HandoutListActivity.this.mContext) == 1 ? R.color.white_color_night : R.color.white_color)));
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);
            ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
            clipPagerTitleView.setText(this.val$mTitleList[index]);
            boolean z2 = SkinManager.getCurrentSkinType(HandoutListActivity.this.mContext) == 1;
            int color = !z2 ? Color.parseColor("#BFFFFFFF") : ContextCompat.getColor(HandoutListActivity.this.mContext, R.color.fourth_text_color_night);
            int color2 = ContextCompat.getColor(HandoutListActivity.this.mContext, z2 ? R.color.white_color_night : R.color.white_color);
            clipPagerTitleView.setTextColor(color);
            clipPagerTitleView.setClipColor(color2);
            clipPagerTitleView.setmItemWidth(UIUtil.getScreenWidth(HandoutListActivity.this.mContext) / 6);
            clipPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.pb
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f13537c.lambda$getTitleView$0(index, view);
                }
            });
            badgePagerTitleView.setInnerPagerTitleView(clipPagerTitleView);
            badgePagerTitleView.setXBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_RIGHT, -UIUtil.dip2px(context, 6.0d)));
            badgePagerTitleView.setYBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_TOP, 0));
            badgePagerTitleView.setAutoCancelBadge(false);
            return badgePagerTitleView;
        }
    }

    private void initIndicator() throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        this.mCommonNavigator = commonNavigator;
        commonNavigator.setScrollPivotX(0.65f);
        CommonNavigator commonNavigator2 = new CommonNavigator(this);
        this.mCommonNavigator = commonNavigator2;
        commonNavigator2.setScrollPivotX(0.65f);
        this.mCommonNavigator.setAdapter(new AnonymousClass1(new String[]{"按经验", "按评论"}));
        this.mMagicIndicator.setNavigator(this.mCommonNavigator);
        this.viewpager.setAdapter(new MyAdapter(getSupportFragmentManager(), this.mFragmentList));
        this.viewpager.setOffscreenPageLimit(1);
        ViewPagerHelper.bind(this.mMagicIndicator, this.viewpager);
        this.viewpager.setCurrentItem(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        this.mFragmentList.add(HandoutSearchFragment.getInstance(0));
        this.mFragmentList.add(HandoutSearchFragment.getInstance(1));
        this.mMagicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        findViewById(R.id.back_view).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ob
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13076c.lambda$init$0(view);
            }
        });
        initIndicator();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(131072);
        getWindow().setSoftInputMode(5);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_handouts_search);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
