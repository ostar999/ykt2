package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators.LinePagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.psychiatrygarden.activity.courselist.fragment.CurriculumSearchFragment;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.GoodsSearchFrag;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.AnswerQuestionAdapter;
import com.psychiatrygarden.widget.ClearEditText;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class NewHomeSearchAct extends BaseActivity {
    private ClearEditText editText;
    private List<Fragment> fragmentList;
    private ImageView mImgBack;
    private MagicIndicator magic_indicator;
    private ViewPager viewpager;
    private int currentPositon = 0;
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.NewHomeSearchAct.2
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
            NewHomeSearchAct.this.magic_indicator.onPageScrollStateChanged(state);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            NewHomeSearchAct.this.magic_indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            NewHomeSearchAct.this.magic_indicator.onPageSelected(position);
            NewHomeSearchAct.this.currentPositon = position;
            String strTrim = NewHomeSearchAct.this.editText.getText().toString().trim();
            if (TextUtils.isEmpty(strTrim)) {
                return;
            }
            NewHomeSearchAct.this.getCurrentFragment(strTrim, false);
        }
    };

    /* renamed from: com.psychiatrygarden.activity.NewHomeSearchAct$1, reason: invalid class name */
    public class AnonymousClass1 extends CommonNavigatorAdapter {
        final /* synthetic */ List val$titleList;

        public AnonymousClass1(final List val$titleList) {
            this.val$titleList = val$titleList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            NewHomeSearchAct.this.viewpager.setCurrentItem(i2);
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
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.pe
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f13540c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.NewHomeSearchAct.1.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setTextSize(14.0f);
                    if (SkinManager.getCurrentSkinType(NewHomeSearchAct.this.mContext) == 1) {
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
                    if (SkinManager.getCurrentSkinType(NewHomeSearchAct.this.mContext) == 1) {
                        textView.setTextColor(Color.parseColor("#7380A9"));
                    } else {
                        textView.setTextColor(Color.parseColor("#141516"));
                    }
                }
            });
            return commonPagerTitleView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCurrentFragment(String keyword, boolean isReload) {
        if (this.fragmentList.get(this.currentPositon) instanceof CurriculumSearchFragment) {
            ((CurriculumSearchFragment) this.fragmentList.get(this.currentPositon)).setSearchKeyWord(keyword, isReload);
        } else if (this.fragmentList.get(this.currentPositon) instanceof GoodsSearchFrag) {
            ((GoodsSearchFrag) this.fragmentList.get(this.currentPositon)).setSearchKeyWord(keyword, isReload);
        }
        AndroidBaseUtils.hideSoftInputFromWindow(this);
    }

    private void initTabData() throws Resources.NotFoundException {
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_course, this);
        String strConfig2 = SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_shop, this);
        final ArrayList arrayList = new ArrayList();
        if (!TextUtils.equals("1", strConfig)) {
            arrayList.add("课程");
            CurriculumSearchFragment curriculumSearchFragment = new CurriculumSearchFragment();
            curriculumSearchFragment.setHaveMargin(true);
            this.fragmentList.add(curriculumSearchFragment);
        }
        if (!TextUtils.equals("1", strConfig2)) {
            arrayList.add("商品");
            this.fragmentList.add(new GoodsSearchFrag());
        }
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new AnonymousClass1(arrayList));
        this.magic_indicator.setNavigator(commonNavigator);
        if (this.mContext == null) {
            return;
        }
        this.viewpager.setAdapter(new AnswerQuestionAdapter(getSupportFragmentManager(), this.fragmentList));
        this.viewpager.setOffscreenPageLimit(1);
        this.viewpager.setCurrentItem(this.currentPositon);
        this.magic_indicator.onPageSelected(this.currentPositon);
        this.viewpager.addOnPageChangeListener(this.onPageChangeListener);
        this.viewpager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.ne
            @Override // java.lang.Runnable
            public final void run() {
                this.f13045c.lambda$initTabData$2(arrayList);
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        String strTrim = this.editText.getText().toString().trim();
        if (TextUtils.isEmpty(strTrim)) {
            return;
        }
        getCurrentFragment(strTrim, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$1(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        String strTrim = this.editText.getText().toString().trim();
        if (!TextUtils.isEmpty(strTrim)) {
            getCurrentFragment(strTrim, true);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTabData$2(List list) {
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
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        finish();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) NewHomeSearchAct.class);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        this.mActionBar.hide();
        this.fragmentList = new ArrayList();
        this.magic_indicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        initTabData();
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        ClearEditText clearEditText = (ClearEditText) findViewById(R.id.ed_search);
        this.editText = clearEditText;
        clearEditText.setHint("输入关键词搜索");
        ((TextView) findViewById(R.id.btn_search)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.le
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12664c.lambda$init$0(view);
            }
        });
        this.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.me
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f12763c.lambda$init$1(textView, i2, keyEvent);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_new_home_search);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.oe
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13078c.lambda$setListenerForWidget$3(view);
            }
        });
    }
}
