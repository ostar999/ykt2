package com.psychiatrygarden.ranking;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.DataStatisticsInfoDialog;
import com.yikaobang.yixue.R;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.cookie.ClientCookie;

/* loaded from: classes6.dex */
public class CommentActionRankingAct extends BaseActivity {
    public AppBarLayout appbarlayout;
    private CollapsingToolbarLayout collapse;
    private ImageView mImgBack;
    private ImageView mImgNameLogo;
    private ImageView mImgNameLogo2;
    private ImageView mImgQuestion;
    private RelativeLayout mLyViewOne;
    private RelativeLayout mLyViewTwo;
    private TextView mTvDesc;
    private TextView mTvDesc2;
    private TextView mTvTitle;
    private String mType;
    private MagicIndicator magic_indicator;
    public RelativeLayout rellogview;
    public Toolbar toobars1;
    public RelativeLayout toolbars;
    private ViewPager viewpager;
    private int currentPositon = 0;
    private List<SelectIdentityBean.DataBean> mChildren = new ArrayList();
    private int mPosition = 0;
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.ranking.CommentActionRankingAct.2
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
            CommentActionRankingAct.this.magic_indicator.onPageScrollStateChanged(state);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            CommentActionRankingAct.this.magic_indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            CommentActionRankingAct.this.magic_indicator.onPageSelected(position);
            CommentActionRankingAct.this.currentPositon = position;
        }
    };

    /* renamed from: com.psychiatrygarden.ranking.CommentActionRankingAct$1, reason: invalid class name */
    public class AnonymousClass1 extends CommonNavigatorAdapter {
        final /* synthetic */ List val$children;

        public AnonymousClass1(final List val$children) {
            this.val$children = val$children;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            CommentActionRankingAct.this.viewpager.setCurrentItem(i2);
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
        public IPagerTitleView getTitleView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(CommentActionRankingAct.this);
            commonPagerTitleView.setContentView(R.layout.item_ranking_day_tab);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tab_name);
            textView.setText((CharSequence) this.val$children.get(index));
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.g
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f16202c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.ranking.CommentActionRankingAct.1.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setBackground(null);
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
                    textView.setBackgroundResource(R.drawable.shape_rank_day_tab_bg);
                }
            });
            return commonPagerTitleView;
        }
    }

    private void initTabData(List<String> children) throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(false);
        commonNavigator.setAdapter(new AnonymousClass1(children));
        this.magic_indicator.setNavigator(commonNavigator);
        if (this.mContext == null) {
            return;
        }
        List<BaseViewPagerAdapter.PagerInfo> viewPageInfo = getViewPageInfo(children);
        if (viewPageInfo == null || viewPageInfo.size() <= 1) {
            this.magic_indicator.setVisibility(8);
        } else {
            this.magic_indicator.setVisibility(0);
        }
        this.viewpager.setAdapter(new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), viewPageInfo));
        this.viewpager.setOffscreenPageLimit(1);
        this.viewpager.setCurrentItem(this.currentPositon);
        this.magic_indicator.onPageSelected(this.currentPositon);
        this.viewpager.addOnPageChangeListener(this.onPageChangeListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(AppBarLayout appBarLayout, int i2) {
        float f2 = i2 * 1.0f;
        this.rellogview.setAlpha(1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()));
        if (1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()) == 0.0f) {
            this.rellogview.setVisibility(8);
            this.mTvTitle.setVisibility(0);
        } else {
            this.rellogview.setVisibility(0);
            this.mTvTitle.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        new XPopup.Builder(this).popupAnimation(null).asCustom(new DataStatisticsInfoDialog(this, true, this.mType)).show();
    }

    public static void newIntent(Context context, String type, int position) {
        Intent intent = new Intent(context, (Class<?>) CommentActionRankingAct.class);
        intent.putExtra("type", type);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    public void getTabData() throws Resources.NotFoundException {
        ArrayList arrayList = new ArrayList();
        arrayList.add("日榜");
        arrayList.add("周榜");
        arrayList.add("月榜");
        arrayList.add("年榜");
        initTabData(arrayList);
    }

    public List<BaseViewPagerAdapter.PagerInfo> getViewPageInfo(List<String> children) {
        ArrayList arrayList = new ArrayList();
        if (children != null && children.size() > 0) {
            int i2 = 0;
            while (i2 < children.size()) {
                Bundle bundle = new Bundle();
                bundle.putString("type", this.mType);
                StringBuilder sb = new StringBuilder();
                int i3 = i2 + 1;
                sb.append(i3);
                sb.append("");
                bundle.putString("position", sb.toString());
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(children.get(i2), SingleRankFragment.class, bundle));
                i2 = i3;
            }
        }
        return arrayList;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        this.mType = getIntent().getStringExtra("type");
        this.mPosition = getIntent().getIntExtra("position", 0);
        this.magic_indicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mTvDesc = (TextView) findViewById(R.id.tv_desc);
        this.mTvDesc2 = (TextView) findViewById(R.id.tv_desc2);
        this.appbarlayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        this.toobars1 = (Toolbar) findViewById(R.id.toobars1);
        this.toolbars = (RelativeLayout) findViewById(R.id.toolbars);
        this.rellogview = (RelativeLayout) findViewById(R.id.rellogview);
        this.mTvTitle = (TextView) findViewById(R.id.title);
        this.collapse = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        this.mImgNameLogo = (ImageView) findViewById(R.id.img_name_logo);
        this.mImgNameLogo2 = (ImageView) findViewById(R.id.img_name_logo2);
        this.mLyViewOne = (RelativeLayout) findViewById(R.id.ly_view_one);
        this.mLyViewTwo = (RelativeLayout) findViewById(R.id.ly_view_two);
        this.mImgQuestion = (ImageView) findViewById(R.id.question);
        this.currentPositon = this.mPosition;
        if (this.mType.equals(ClientCookie.COMMENT_ATTR)) {
            this.mLyViewOne.setVisibility(0);
            this.mLyViewTwo.setVisibility(8);
            this.mTvTitle.setText("评论榜");
            this.mImgNameLogo.setImageResource(R.mipmap.ic_rank_comment_name_logo);
            this.mTvDesc.setText("莫愁前路无知己  帮里谁人不识君");
        } else if (this.mType.equals("praise")) {
            this.mLyViewOne.setVisibility(0);
            this.mLyViewTwo.setVisibility(8);
            this.mTvTitle.setText("获赞榜");
            this.mImgNameLogo.setImageResource(R.mipmap.ic_rank_praise_name_logo);
            this.mTvDesc.setText("表述清晰 | 逻辑完美 | 见解独到 | 字字珠玑");
        } else if (this.mType.equals("question")) {
            this.mTvTitle.setText("刷题榜");
            this.mLyViewOne.setVisibility(8);
            this.mLyViewTwo.setVisibility(0);
            this.mImgNameLogo2.setImageResource(R.mipmap.ic_rank_question_name_logo);
            this.mTvDesc2.setText("医考帮百万医学生之中脱颖而出");
        } else if (this.mType.equals("post")) {
            this.mTvTitle.setText("发帖榜");
            this.mLyViewOne.setVisibility(8);
            this.mLyViewTwo.setVisibility(0);
            this.mImgNameLogo2.setImageResource(R.mipmap.ic_rank_post_name_logo);
            this.mTvDesc2.setText("医考帮江湖百晓生  医学圈里真正的KOL");
        }
        this.appbarlayout.setSelected(false);
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(this.toobars1.getLayoutParams());
        layoutParams.setMargins(0, statusBarHeight, 0, 0);
        layoutParams.setCollapseMode(1);
        this.toobars1.setLayoutParams(layoutParams);
        CollapsingToolbarLayout.LayoutParams layoutParams2 = (CollapsingToolbarLayout.LayoutParams) this.rellogview.getLayoutParams();
        ((FrameLayout.LayoutParams) layoutParams2).topMargin = UIUtil.dip2px(this, 60.0d) + statusBarHeight;
        this.rellogview.setLayoutParams(layoutParams2);
        AppBarLayout.LayoutParams layoutParams3 = (AppBarLayout.LayoutParams) this.collapse.getLayoutParams();
        if (this.mType.equals("question")) {
            ((LinearLayout.LayoutParams) layoutParams3).height = UIUtil.dip2px(this, 133.0d) + statusBarHeight;
        } else if (this.mType.equals("post")) {
            ((LinearLayout.LayoutParams) layoutParams3).height = UIUtil.dip2px(this, 131.0d) + statusBarHeight;
        } else {
            ((LinearLayout.LayoutParams) layoutParams3).height = UIUtil.dip2px(this, 125.0d) + statusBarHeight;
        }
        Log.e("nav_bar_he", "hhh==>" + (ScreenUtil.getDpByPx(this, statusBarHeight) + 89));
        this.collapse.setLayoutParams(layoutParams3);
        this.appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.ranking.d
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                this.f16199a.lambda$init$0(appBarLayout, i2);
            }
        });
        getTabData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, true);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_comment_action_ranking);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16200c.lambda$setListenerForWidget$1(view);
            }
        });
        this.mImgQuestion.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16201c.lambda$setListenerForWidget$2(view);
            }
        });
    }
}
