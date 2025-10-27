package com.psychiatrygarden.activity.mine.coupons;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators.LinePagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.ExchangeCodeListActivity;
import com.psychiatrygarden.activity.coupon.CouponRecordActivity;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.CardActiveGuidePop;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class RedEnvelopeCouponsAct extends BaseActivity {
    private boolean isCenter;
    private TextView mBtnCardActive;
    private TextView mBtnUseRecord;
    LinearLayout mLyBottom;
    private MagicIndicator magic_indicator;
    private ViewPager viewpager;
    private int currentPositon = 0;
    private int allHeight = 0;
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.mine.coupons.RedEnvelopeCouponsAct.4
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
            RedEnvelopeCouponsAct.this.magic_indicator.onPageScrollStateChanged(state);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            RedEnvelopeCouponsAct.this.magic_indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            RedEnvelopeCouponsAct.this.magic_indicator.onPageSelected(position);
            RedEnvelopeCouponsAct.this.currentPositon = position;
        }
    };

    /* renamed from: com.psychiatrygarden.activity.mine.coupons.RedEnvelopeCouponsAct$3, reason: invalid class name */
    public class AnonymousClass3 extends CommonNavigatorAdapter {
        final /* synthetic */ List val$titleList;

        public AnonymousClass3(final List val$titleList) {
            this.val$titleList = val$titleList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            RedEnvelopeCouponsAct.this.viewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return this.val$titleList.size();
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
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_column_name);
            textView.setText((CharSequence) this.val$titleList.get(index));
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.coupons.o
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f12791c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.mine.coupons.RedEnvelopeCouponsAct.3.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setTextSize(14.0f);
                    if (SkinManager.getCurrentSkinType(RedEnvelopeCouponsAct.this.mContext) == 1) {
                        textView.setTextColor(Color.parseColor("#575F79"));
                    } else {
                        textView.setTextColor(Color.parseColor("#909499"));
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
                    textView.setTypeface(Typeface.DEFAULT_BOLD);
                    textView.setTextSize(16.0f);
                    if (SkinManager.getCurrentSkinType(RedEnvelopeCouponsAct.this.mContext) == 1) {
                        textView.setTextColor(Color.parseColor("#7380A9"));
                    } else {
                        textView.setTextColor(Color.parseColor("#141516"));
                    }
                }
            });
            return commonPagerTitleView;
        }
    }

    private void initTabData() throws Resources.NotFoundException {
        final ArrayList arrayList = new ArrayList();
        arrayList.add("优惠券");
        arrayList.add("红包");
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new AnonymousClass3(arrayList));
        this.magic_indicator.setNavigator(commonNavigator);
        if (this.mContext == null) {
            return;
        }
        this.viewpager.setAdapter(new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), getViewPageInfo(arrayList)));
        this.viewpager.setOffscreenPageLimit(1);
        this.viewpager.setCurrentItem(this.currentPositon);
        this.magic_indicator.onPageSelected(this.currentPositon);
        this.viewpager.addOnPageChangeListener(this.onPageChangeListener);
        this.viewpager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.coupons.l
            @Override // java.lang.Runnable
            public final void run() {
                this.f12787c.lambda$initTabData$1(arrayList);
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        startActivity(newIntent(this.mContext, true));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTabData$1(List list) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        CouponRecordActivity.INSTANCE.gotoCouponRecordActivity(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        startActivity(new Intent(this, (Class<?>) ExchangeCodeListActivity.class));
    }

    public static Intent newIntent(Context context, boolean isCenter) {
        Intent intent = new Intent(context, (Class<?>) RedEnvelopeCouponsAct.class);
        intent.putExtra("isCenter", isCenter);
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showGuide(int height) {
        if (!SharePreferencesUtils.readBooleanConfig(CommonParameter.showCardActiveGuid, true, this.mContext) || this.isCenter) {
            return;
        }
        int screenWidth = (ScreenUtil.getScreenWidth(this) / 4) - ScreenUtil.getPxByDp(this.mContext, 16);
        int statusBarHeight = (StatusBarUtil.getStatusBarHeight(this.mContext) + height) - ScreenUtil.getPxByDp(this.mContext, 46);
        LogUtils.e("top_right", "allHeight===>" + height + ";offsetY===>" + statusBarHeight);
        XPopup.Builder builderPopupAnimation = new XPopup.Builder(this).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.activity.mine.coupons.RedEnvelopeCouponsAct.2
            @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
            public void onDismiss(BasePopupView popupView) {
                SharePreferencesUtils.writeBooleanConfig(CommonParameter.showCardActiveGuid, false, RedEnvelopeCouponsAct.this.mContext);
            }
        }).atView(this.mBtnCardActive).popupPosition(PopupPosition.Bottom).offsetY(statusBarHeight).offsetX(screenWidth).popupAnimation(PopupAnimation.ScaleAlphaFromCenter);
        Boolean bool = Boolean.FALSE;
        ((CardActiveGuidePop) builderPopupAnimation.hasShadowBg(bool).dismissOnTouchOutside(bool).asCustom(new CardActiveGuidePop(this))).show();
    }

    public List<BaseViewPagerAdapter.PagerInfo> getViewPageInfo(List<String> children) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < children.size(); i2++) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", i2);
            bundle.putBoolean("isCenter", this.isCenter);
            bundle.putBoolean("available", true);
            bundle.putInt("useType", 1);
            bundle.putBoolean("needTopMargin", true);
            arrayList.add(new BaseViewPagerAdapter.PagerInfo(children.get(i2), FragRedEnvelopeCoupons.class, bundle));
        }
        return arrayList;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        boolean booleanExtra = getIntent().getBooleanExtra("isCenter", false);
        this.isCenter = booleanExtra;
        setTitle(booleanExtra ? "领券中心" : "红包卡券");
        this.magic_indicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.mBtnCardActive = (TextView) findViewById(R.id.btn_card_active);
        this.mBtnUseRecord = (TextView) findViewById(R.id.btn_use_record);
        View viewFindViewById = findViewById(R.id.h_line);
        this.mLyBottom = (LinearLayout) findViewById(R.id.ly_bottom);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ly_view);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.ly_more_active);
        findViewById(R.id.ly_center).setVisibility(this.isCenter ? 8 : 0);
        linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.coupons.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12786c.lambda$init$0(view);
            }
        });
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.activity.mine.coupons.RedEnvelopeCouponsAct.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                RedEnvelopeCouponsAct.this.allHeight = linearLayout.getHeight();
                RedEnvelopeCouponsAct redEnvelopeCouponsAct = RedEnvelopeCouponsAct.this;
                redEnvelopeCouponsAct.showGuide(redEnvelopeCouponsAct.allHeight);
                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        viewFindViewById.setVisibility(this.isCenter ? 8 : 0);
        this.mBtnUseRecord.setVisibility(this.isCenter ? 8 : 0);
        initTabData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_red_envelope_coupons);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnUseRecord.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.coupons.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12789c.lambda$setListenerForWidget$2(view);
            }
        });
        this.mBtnCardActive.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.coupons.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12790c.lambda$setListenerForWidget$3(view);
            }
        });
    }
}
