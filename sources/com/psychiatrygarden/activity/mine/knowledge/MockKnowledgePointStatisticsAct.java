package com.psychiatrygarden.activity.mine.knowledge;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators.LinePagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.RankingActivity;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.bean.MockStatisticsBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.AnswerQuestionAdapter;
import com.psychiatrygarden.widget.CoustromViewPager;
import com.psychiatrygarden.widget.DialogShare;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.yikaobang.yixue.R;
import com.ykb.common_share_lib.CommonConfig;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class MockKnowledgePointStatisticsAct extends BaseActivity {
    private AppBarLayout appbarlayout;
    private String bindSchool;
    private CollapsingToolbarLayout collapse;
    private String examId;
    private List<Fragment> fragmentList;
    private String isEstimate;
    private String isSchoolRank;
    private ImageView mBtnShare;
    private ImageView mImgBack;
    private ImageView mImgBg;
    private LinearLayout mLyPercent;
    private LinearLayout mLyRank;
    private CoordinatorLayout mLyRoot;
    private LinearLayout mLyScore;
    private LinearLayout mLyTime;
    private TextView mNavTitle;
    private TextView mTvPercent;
    private TextView mTvRank;
    private TextView mTvScore;
    private TextView mTvUseTime;
    private MagicIndicator magicIndicator;
    private RelativeLayout rellogview;
    private String shareDesc;
    private String shareImg;
    private String title;
    private Toolbar toobars1;
    private RelativeLayout toolbars;
    private CoustromViewPager viewpager;
    private int currentPositon = 0;
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.MockKnowledgePointStatisticsAct.4
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
            MockKnowledgePointStatisticsAct.this.magicIndicator.onPageScrollStateChanged(state);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            MockKnowledgePointStatisticsAct.this.magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            MockKnowledgePointStatisticsAct.this.magicIndicator.onPageSelected(position);
            MockKnowledgePointStatisticsAct.this.currentPositon = position;
        }
    };

    /* renamed from: com.psychiatrygarden.activity.mine.knowledge.MockKnowledgePointStatisticsAct$3, reason: invalid class name */
    public class AnonymousClass3 extends CommonNavigatorAdapter {
        final /* synthetic */ List val$children;

        public AnonymousClass3(final List val$children) {
            this.val$children = val$children;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            MockKnowledgePointStatisticsAct.this.viewpager.setCurrentItem(i2);
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
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            linePagerIndicator.setMode(2);
            linePagerIndicator.setLineHeight(CommonUtil.dip2px(context, 3.0f));
            linePagerIndicator.setLineWidth(CommonUtil.dip2px(context, 16.0f));
            linePagerIndicator.setRoundRadius(CommonUtil.dip2px(context, 10.0f));
            linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
            linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
            linePagerIndicator.setColors(Integer.valueOf(MockKnowledgePointStatisticsAct.this.getColor(R.color.main_theme_color)));
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
            commonPagerTitleView.setContentView(R.layout.item_mock_point_tab_view);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_column_name);
            final View viewFindViewById = commonPagerTitleView.findViewById(R.id.view_line);
            textView.setText((CharSequence) this.val$children.get(index));
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.n0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f12899c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.MockKnowledgePointStatisticsAct.3.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setTextSize(2, 14.0f);
                    textView.setTextColor(MockKnowledgePointStatisticsAct.this.getColor(R.color.third_txt_color));
                    viewFindViewById.setVisibility(8);
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
                    textView.setTextSize(2, 16.0f);
                    textView.setTextColor(MockKnowledgePointStatisticsAct.this.getColor(R.color.first_txt_color));
                    viewFindViewById.setVisibility(0);
                }
            });
            return commonPagerTitleView;
        }
    }

    private void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", this.examId);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mockStatisticsStatInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.knowledge.MockKnowledgePointStatisticsAct.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws NumberFormatException {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    MockStatisticsBean mockStatisticsBean = (MockStatisticsBean) new Gson().fromJson(s2, MockStatisticsBean.class);
                    if (!mockStatisticsBean.getCode().equals("200")) {
                        ToastUtil.shortToast(MockKnowledgePointStatisticsAct.this, mockStatisticsBean.getMessage());
                        return;
                    }
                    if (mockStatisticsBean.getData() != null) {
                        MockKnowledgePointStatisticsAct.this.shareDesc = mockStatisticsBean.getData().getDescription();
                        MockKnowledgePointStatisticsAct.this.shareImg = mockStatisticsBean.getData().getCover_img();
                        MockKnowledgePointStatisticsAct.this.initTabData(!TextUtils.isEmpty(mockStatisticsBean.getData().getKnowledge_stat()) && mockStatisticsBean.getData().getKnowledge_stat().equals("1"), mockStatisticsBean.getData().getScore());
                        MockKnowledgePointStatisticsAct.this.mTvScore.setText(mockStatisticsBean.getData().getScore());
                        String accuracy = mockStatisticsBean.getData().getAccuracy();
                        if (accuracy.indexOf("%") == -1) {
                            accuracy = mockStatisticsBean.getData().getAccuracy() + "%";
                        }
                        int iIndexOf = accuracy.indexOf("%");
                        SpannableString spannableString = new SpannableString(accuracy);
                        spannableString.setSpan(new AbsoluteSizeSpan(12, true), iIndexOf, iIndexOf + 1, 33);
                        MockKnowledgePointStatisticsAct.this.mTvPercent.setText(spannableString);
                        MockKnowledgePointStatisticsAct.this.mTvRank.setText(mockStatisticsBean.getData().getOwn_rank());
                        if (TextUtils.isEmpty(mockStatisticsBean.getData().getAnswer_time()) || mockStatisticsBean.getData().getAnswer_time().equals("0")) {
                            SpannableString spannableString2 = new SpannableString("0s");
                            spannableString2.setSpan(new AbsoluteSizeSpan(12, true), 1, 2, 33);
                            MockKnowledgePointStatisticsAct.this.mTvUseTime.setText(spannableString2);
                            return;
                        }
                        long j2 = Long.parseLong(mockStatisticsBean.getData().getAnswer_time());
                        int i2 = (int) (j2 / 3600);
                        int i3 = (int) (j2 % 3600);
                        int i4 = i3 / 60;
                        int i5 = i3 % 60;
                        String str = "";
                        if (i2 != 0) {
                            str = i2 + "h";
                        }
                        if (i4 != 0) {
                            str = str + i4 + "m";
                        }
                        if (i2 == 0 && i5 != 0) {
                            str = str + i5 + "s";
                        }
                        if (TextUtils.isEmpty(str)) {
                            str = "0m";
                        }
                        int iIndexOf2 = str.indexOf("h");
                        int iIndexOf3 = str.indexOf("m");
                        int iIndexOf4 = str.indexOf("s");
                        SpannableString spannableString3 = new SpannableString(str);
                        if (iIndexOf2 != -1) {
                            spannableString3.setSpan(new AbsoluteSizeSpan(12, true), iIndexOf2, iIndexOf2 + 1, 33);
                        }
                        if (iIndexOf3 != -1) {
                            spannableString3.setSpan(new AbsoluteSizeSpan(12, true), iIndexOf3, iIndexOf3 + 1, 33);
                        }
                        if (iIndexOf4 != -1) {
                            spannableString3.setSpan(new AbsoluteSizeSpan(12, true), iIndexOf4, iIndexOf4 + 1, 33);
                        }
                        MockKnowledgePointStatisticsAct.this.mTvUseTime.setText(spannableString3);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTabData(boolean isShowPoint, String score) throws Resources.NotFoundException {
        final ArrayList arrayList = new ArrayList();
        arrayList.add("大纲统计");
        if (isShowPoint) {
            arrayList.add(0, "考点统计");
            this.fragmentList.add(ExamPointStatisticsFrag.newInstance(this.examId, this.title, this.isEstimate, score, this.isSchoolRank));
        }
        this.fragmentList.add(OutlinesStatisticsFrag.newInstance(this.examId, this.title, this.isEstimate, score, this.isSchoolRank));
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new AnonymousClass3(arrayList));
        this.magicIndicator.setNavigator(commonNavigator);
        if (this.mContext == null) {
            return;
        }
        if (this.fragmentList.size() > 1) {
            this.magicIndicator.setVisibility(0);
        } else {
            this.magicIndicator.setVisibility(8);
        }
        this.viewpager.setSwipeEnabled(false);
        this.viewpager.setAdapter(new AnswerQuestionAdapter(getSupportFragmentManager(), this.fragmentList));
        this.viewpager.setOffscreenPageLimit(1);
        this.viewpager.setCurrentItem(this.currentPositon);
        this.magicIndicator.onPageSelected(this.currentPositon);
        this.viewpager.addOnPageChangeListener(this.onPageChangeListener);
        this.viewpager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.knowledge.m0
            @Override // java.lang.Runnable
            public final void run() {
                this.f12896c.lambda$initTabData$2(arrayList);
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(int i2, AppBarLayout appBarLayout, int i3) {
        if (Math.abs(i3) >= i2) {
            if (this.mImgBg.getVisibility() == 0) {
                this.appbarlayout.setBackgroundColor(-1);
                this.toobars1.setBackgroundColor(-1);
                this.rellogview.setBackgroundColor(-1);
                this.mImgBg.setVisibility(8);
                return;
            }
            return;
        }
        if (this.mImgBg.getVisibility() == 8) {
            this.appbarlayout.setBackgroundColor(0);
            this.rellogview.setBackgroundColor(0);
            this.toobars1.setBackgroundColor(0);
            this.mImgBg.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1() {
        SharePreferencesUtils.writeStrConfig("statisticsPermission", "1", this);
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        Intent intent = new Intent(this.mContext, (Class<?>) RankingActivity.class);
        intent.putExtra("title", this.title);
        intent.putExtra("exam_id", this.examId);
        intent.putExtra("bindSchool", this.bindSchool);
        intent.putExtra("is_estimate", this.isEstimate);
        intent.putExtra("is_school_rank", this.isSchoolRank);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(View view) {
        new DialogShare(this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.l0
            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public final void onclickIntBack(int i2) {
                this.f12894a.lambda$setListenerForWidget$5(i2);
            }
        }, false, true).show();
    }

    public static void newIntent(Context context, String examId, String bindSchool, String title, String isEstimate, String is_school_rank) {
        Intent intent = new Intent(context, (Class<?>) MockKnowledgePointStatisticsAct.class);
        intent.putExtra("examId", examId);
        intent.putExtra("bindSchool", bindSchool);
        intent.putExtra("title", title);
        intent.putExtra("isEstimate", isEstimate);
        intent.putExtra("is_school_rank", is_school_rank);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: shareToFriend, reason: merged with bridge method [inline-methods] */
    public void lambda$setListenerForWidget$5(int i2) {
        UMImage uMImage = TextUtils.isEmpty(this.shareImg) ? new UMImage(this.mContext, R.drawable.ic_launcher) : new UMImage(this.mContext, this.shareImg);
        UMMin uMMin = new UMMin(this.shareImg);
        uMMin.setThumb(uMImage);
        uMMin.setTitle(this.title);
        uMMin.setDescription(this.shareDesc);
        uMMin.setPath("pages/index/startPage");
        uMMin.setUserName("gh_14d59acf6877");
        if (CommonConfig.INSTANCE.getYI_KAO_BANG_NETWORK() == 0) {
            Config.setMiniPreView();
        }
        new ShareAction(this).withMedia(uMMin).setPlatform(BaseActivity.platforms.get(i2).mPlatform).share();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.examId = getIntent().getStringExtra("examId");
        this.bindSchool = getIntent().getStringExtra("bindSchool");
        this.title = getIntent().getStringExtra("title");
        this.isEstimate = getIntent().getStringExtra("isEstimate");
        this.isSchoolRank = getIntent().getStringExtra("is_school_rank");
        this.appbarlayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        this.toobars1 = (Toolbar) findViewById(R.id.toobars1);
        this.mNavTitle = (TextView) findViewById(R.id.nav_title);
        this.toolbars = (RelativeLayout) findViewById(R.id.toolbars);
        this.rellogview = (RelativeLayout) findViewById(R.id.rellogview);
        this.collapse = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mImgBg = (ImageView) findViewById(R.id.img_bg);
        this.magicIndicator = (MagicIndicator) findViewById(R.id.magicIndicator);
        this.viewpager = (CoustromViewPager) findViewById(R.id.viewpager);
        this.mBtnShare = (ImageView) findViewById(R.id.btn_share);
        this.mLyRoot = (CoordinatorLayout) findViewById(R.id.parent_view);
        this.mTvScore = (TextView) findViewById(R.id.tv_score);
        this.mTvUseTime = (TextView) findViewById(R.id.tv_use_time);
        this.mTvPercent = (TextView) findViewById(R.id.tv_percent);
        this.mTvRank = (TextView) findViewById(R.id.tv_rank);
        this.mLyScore = (LinearLayout) findViewById(R.id.ly_score);
        this.mLyTime = (LinearLayout) findViewById(R.id.ly_time);
        this.mLyPercent = (LinearLayout) findViewById(R.id.ly_percent);
        this.mLyRank = (LinearLayout) findViewById(R.id.ly_rank);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ly_statistics);
        this.fragmentList = new ArrayList();
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        this.appbarlayout.setSelected(false);
        int iDip2px = UIUtil.dip2px(this, 16.0d);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.setMargins(iDip2px, iDip2px, iDip2px, iDip2px);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.mine.knowledge.MockKnowledgePointStatisticsAct.1
            @Override // java.lang.Runnable
            public void run() {
                int iDip2px2 = UIUtil.dip2px(MockKnowledgePointStatisticsAct.this, 24.0d);
                int measuredWidth = linearLayout.getMeasuredWidth();
                int i2 = (measuredWidth - iDip2px2) / 4;
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) MockKnowledgePointStatisticsAct.this.mLyScore.getLayoutParams();
                layoutParams2.width = i2;
                LogUtils.e("screen_he", "measuredWidth=" + measuredWidth + ";dis=" + iDip2px2 + ";wid=" + i2);
                layoutParams2.setMarginEnd(UIUtil.dip2px(MockKnowledgePointStatisticsAct.this, 8.0d));
                MockKnowledgePointStatisticsAct.this.mLyScore.setLayoutParams(layoutParams2);
                MockKnowledgePointStatisticsAct.this.mLyTime.setLayoutParams(layoutParams2);
                MockKnowledgePointStatisticsAct.this.mLyPercent.setLayoutParams(layoutParams2);
                ((LinearLayout.LayoutParams) MockKnowledgePointStatisticsAct.this.mLyRank.getLayoutParams()).width = i2;
                MockKnowledgePointStatisticsAct.this.mLyRank.setLayoutParams(layoutParams2);
            }
        });
        CollapsingToolbarLayout.LayoutParams layoutParams2 = new CollapsingToolbarLayout.LayoutParams(this.toobars1.getLayoutParams());
        ((FrameLayout.LayoutParams) layoutParams2).height = UIUtil.dip2px(this.mContext, 44.0d) + statusBarHeight;
        layoutParams2.setCollapseMode(1);
        this.toobars1.setLayoutParams(layoutParams2);
        Toolbar.LayoutParams layoutParams3 = new Toolbar.LayoutParams(this.toolbars.getLayoutParams());
        layoutParams3.setMargins(0, statusBarHeight, 0, 0);
        this.toolbars.setLayoutParams(layoutParams3);
        CollapsingToolbarLayout.LayoutParams layoutParams4 = (CollapsingToolbarLayout.LayoutParams) this.rellogview.getLayoutParams();
        ((FrameLayout.LayoutParams) layoutParams4).topMargin = statusBarHeight + UIUtil.dip2px(this, 44.0d);
        this.rellogview.setLayoutParams(layoutParams4);
        final AppBarLayout.LayoutParams layoutParams5 = (AppBarLayout.LayoutParams) this.collapse.getLayoutParams();
        this.collapse.post(new Runnable() { // from class: com.psychiatrygarden.activity.mine.knowledge.MockKnowledgePointStatisticsAct.2
            @Override // java.lang.Runnable
            public void run() {
                int measuredHeight = MockKnowledgePointStatisticsAct.this.collapse.getMeasuredHeight();
                ((LinearLayout.LayoutParams) layoutParams5).height = measuredHeight;
                MockKnowledgePointStatisticsAct.this.collapse.setLayoutParams(layoutParams5);
                Log.e("screen_he", "Height: " + measuredHeight);
            }
        });
        final int iDip2px2 = UIUtil.dip2px(this.mContext, 44.0d);
        this.appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.j0
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                this.f12890a.lambda$init$0(iDip2px2, appBarLayout, i2);
            }
        });
        if (SharePreferencesUtils.readStrConfig("statisticsPermission", this, "0").equals("0")) {
            String strConfig = SharePreferencesUtils.readStrConfig("statisticsActiveId", this, "");
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, strConfig);
            MemInterface.getInstance().getMemData(this, ajaxParams, true, 0);
            MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.k0
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                public final void mUShareListener() {
                    this.f12892a.lambda$init$1();
                }
            });
        }
        getData();
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
        if (str.equals("closePage")) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_mock_knowledge_point_statistics);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.g0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12883c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mLyRank.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.h0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12887c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mBtnShare.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.i0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12889c.lambda$setListenerForWidget$6(view);
            }
        });
    }
}
