package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
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
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.SearchByCircleFragment;
import com.psychiatrygarden.fragmenthome.SearchByReplyFragment;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.MyAdapter;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class SearchCircleAct extends BaseActivity {
    public ImageView back_view;
    public CommonNavigator mCommonNavigator;
    public ViewPager mQuestionViewpager;
    public MagicIndicator magicIndicator;
    private final List<Fragment> mFragmentList = new ArrayList();
    public String[] mTitleList = new String[2];
    private String searchValue = "";

    /* renamed from: com.psychiatrygarden.activity.SearchCircleAct$1, reason: invalid class name */
    public class AnonymousClass1 extends CommonNavigatorAdapter {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            SearchCircleAct.this.mQuestionViewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            String[] strArr = SearchCircleAct.this.mTitleList;
            if (strArr == null) {
                return 0;
            }
            return strArr.length;
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
            linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(SearchCircleAct.this.mContext, SkinManager.getCurrentSkinType(SearchCircleAct.this.mContext) == 1 ? R.color.white_color_night : R.color.white_color)));
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);
            ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
            clipPagerTitleView.setText(SearchCircleAct.this.mTitleList[index]);
            boolean z2 = SkinManager.getCurrentSkinType(SearchCircleAct.this.mContext) == 1;
            int color = !z2 ? Color.parseColor("#BFFFFFFF") : ContextCompat.getColor(SearchCircleAct.this.mContext, R.color.fourth_text_color_night);
            int color2 = ContextCompat.getColor(SearchCircleAct.this.mContext, z2 ? R.color.white_color_night : R.color.white_color);
            clipPagerTitleView.setTextColor(color);
            clipPagerTitleView.setClipColor(color2);
            clipPagerTitleView.setmItemWidth(UIUtil.getScreenWidth(SearchCircleAct.this.mContext) / 6);
            clipPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.si
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f13922c.lambda$getTitleView$0(index, view);
                }
            });
            badgePagerTitleView.setInnerPagerTitleView(clipPagerTitleView);
            badgePagerTitleView.setXBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_RIGHT, -UIUtil.dip2px(context, 6.0d)));
            badgePagerTitleView.setYBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_TOP, 0));
            badgePagerTitleView.setAutoCancelBadge(false);
            return badgePagerTitleView;
        }
    }

    private void initMagicIndicator() throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        this.mCommonNavigator = commonNavigator;
        commonNavigator.setScrollPivotX(0.65f);
        this.mCommonNavigator.setAdapter(new AnonymousClass1());
        this.magicIndicator.setNavigator(this.mCommonNavigator);
        this.mQuestionViewpager.setAdapter(new MyAdapter(getSupportFragmentManager(), this.mFragmentList));
        this.mQuestionViewpager.setOffscreenPageLimit(1);
        ViewPagerHelper.bind(this.magicIndicator, this.mQuestionViewpager);
        this.mQuestionViewpager.setCurrentItem(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    public static void newIntent(Context context, String editTextData, boolean flagEdit) {
        Intent intent = new Intent(context, (Class<?>) SearchCircleAct.class);
        intent.putExtra("flagEdit", flagEdit);
        intent.putExtra("editTextData", editTextData);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        String stringExtra = getIntent().getStringExtra("editTextData");
        SharePreferencesUtils.writeStrConfig("searchCircleKeyWords", stringExtra, this.mContext);
        boolean booleanExtra = getIntent().getBooleanExtra("flagEdit", false);
        this.magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.mQuestionViewpager = (ViewPager) findViewById(R.id.viewpager);
        ImageView imageView = (ImageView) findViewById(R.id.back_view);
        this.back_view = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ri
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13800c.lambda$init$0(view);
            }
        });
        Bundle bundle = new Bundle();
        bundle.putBoolean("flagEdit", booleanExtra);
        bundle.putString("editTextData", stringExtra);
        String[] strArr = this.mTitleList;
        strArr[0] = "按帖子";
        strArr[1] = "按评论";
        this.mFragmentList.add(SearchByCircleFragment.getInstance(bundle));
        this.mFragmentList.add(SearchByReplyFragment.getInstance(bundle));
        initMagicIndicator();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_search_circle);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
