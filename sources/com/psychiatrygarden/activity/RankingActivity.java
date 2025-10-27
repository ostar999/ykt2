package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators.LinePagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.psychiatrygarden.bean.RankingContentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.fragmenthome.MockRankingFrag;
import com.psychiatrygarden.fragmenthome.MockTypeRankingFragment;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.CircleImageView;
import com.yikaobang.yixue.R;
import com.ykb.ebook.dialog.CommonOneDialog;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;

/* loaded from: classes5.dex */
public class RankingActivity extends BaseActivity {
    private RankingContentBean.DataBean allInfo;
    private ImageView iv_ranking_back;
    public BaseViewPagerAdapter mBaseView;
    private TextView mBtnShare;
    public CommonNavigator mCommonNavigator;
    private CircleImageView mImgUserHead;
    private LinearLayout mLyScoreInfo;
    public ViewPager mQuestionViewpager;
    private TextView mTvNameInfo;
    private TextView mTvNickName;
    private TextView mTvRank;
    private TextView mTvScore;
    public MagicIndicator magicIndicator;
    private RankingContentBean.DataBean schoolInfo;
    private SpannableStringBuilder zhengqueTxtSpanBuilder;
    public List<BaseViewPagerAdapter.PagerInfo> listviewpage = new ArrayList();
    public String[] mTitleList = new String[2];
    private String bindSchool = "";

    /* renamed from: com.psychiatrygarden.activity.RankingActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends CommonNavigatorAdapter {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            RankingActivity.this.mQuestionViewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            String[] strArr = RankingActivity.this.mTitleList;
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
            linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(RankingActivity.this.mContext, SkinManager.getCurrentSkinType(RankingActivity.this.mContext) == 1 ? R.color.main_theme_color_night : R.color.main_theme_color)));
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
            commonPagerTitleView.setContentView(R.layout.item_question_column);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_column_name);
            textView.getLayoutParams().height = ScreenUtil.getPxByDp(context, 44);
            textView.setText(RankingActivity.this.mTitleList[index]);
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.zf
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f14248c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.RankingActivity.1.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    if (SkinManager.getCurrentSkinType(RankingActivity.this.mContext) == 1) {
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
                    if (SkinManager.getCurrentSkinType(RankingActivity.this.mContext) == 1) {
                        textView.setTextColor(Color.parseColor("#7380A9"));
                    } else {
                        textView.setTextColor(Color.parseColor("#141516"));
                    }
                }
            });
            return commonPagerTitleView;
        }
    }

    private String getExamTimeStr(String remain) {
        String str;
        String str2;
        int i2 = !TextUtils.isEmpty(remain) ? Integer.parseInt(remain) : 0;
        if (i2 == 0) {
            return "0秒";
        }
        int i3 = i2 / 3600;
        int i4 = i2 % 3600;
        int i5 = i4 / 60;
        int i6 = i4 % 60;
        StringBuilder sb = new StringBuilder();
        String str3 = "";
        if (i3 <= 0) {
            str = "";
        } else {
            str = i3 + "时";
        }
        sb.append(str);
        if (i5 <= 0) {
            str2 = "";
        } else {
            str2 = i5 + "分";
        }
        sb.append(str2);
        if (i6 > 0) {
            str3 = i6 + "秒";
        }
        sb.append(str3);
        return sb.toString();
    }

    private void initMagicIndicator() throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        this.mCommonNavigator = commonNavigator;
        commonNavigator.setScrollPivotX(0.65f);
        this.mCommonNavigator.setAdapter(new AnonymousClass1());
        this.magicIndicator.setNavigator(this.mCommonNavigator);
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), this.listviewpage);
        this.mBaseView = baseViewPagerAdapter;
        this.mQuestionViewpager.setAdapter(baseViewPagerAdapter);
        this.mQuestionViewpager.setOffscreenPageLimit(1);
        ViewPagerHelper.bind(this.magicIndicator, this.mQuestionViewpager);
        this.mQuestionViewpager.setCurrentItem(getIntent().getExtras().getInt("position"));
        this.mQuestionViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.RankingActivity.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float v2, int i12) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) {
                if (i2 == 0) {
                    if (RankingActivity.this.allInfo == null || TextUtils.isEmpty(RankingActivity.this.allInfo.getScore())) {
                        return;
                    }
                    RankingActivity.this.mLyScoreInfo.setVisibility(0);
                    RankingActivity.this.mTvNameInfo.setText("全部院校排名");
                    RankingActivity rankingActivity = RankingActivity.this;
                    rankingActivity.updateUi(rankingActivity.allInfo, true, 0);
                    return;
                }
                if (TextUtils.isEmpty(RankingActivity.this.bindSchool) || !RankingActivity.this.bindSchool.equals("1")) {
                    RankingActivity.this.mLyScoreInfo.setVisibility(8);
                } else {
                    RankingActivity.this.mLyScoreInfo.setVisibility(0);
                }
                if (RankingActivity.this.schoolInfo != null && !TextUtils.isEmpty(RankingActivity.this.schoolInfo.getScore())) {
                    RankingActivity.this.mTvNameInfo.setText("考研院校排名");
                    RankingActivity rankingActivity2 = RankingActivity.this;
                    rankingActivity2.updateUi(rankingActivity2.schoolInfo, false, 0);
                }
                boolean booleanConfig = SharePreferencesUtils.readBooleanConfig(CommonParameter.IS_RANK_SHOW_DIALOG, false, RankingActivity.this);
                if ((TextUtils.isEmpty(RankingActivity.this.bindSchool) || RankingActivity.this.bindSchool.equals("0")) && !booleanConfig) {
                    new CommonOneDialog.Builder(RankingActivity.this).setTitle("温馨提示").setSubTitle("由于大量用户反馈，选择院校排名信息时选择错误。遂安排大家重新选择相关信息，注意：只能重选一次，请谨慎选择。").setLeftText("取消").setIsNight(SkinManager.getCurrentSkinType(RankingActivity.this) == 1).setRightText("确定").show();
                    SharePreferencesUtils.writeBooleanConfig(CommonParameter.IS_RANK_SHOW_DIALOG, true, RankingActivity.this);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setListenerForWidget$0(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUi(RankingContentBean.DataBean info, boolean isAll, int position) {
        String str;
        this.mTvScore.setText(info.getScore() + "分  " + info.getExam_time());
        ColorStateList colorStateList = ContextCompat.getColorStateList(this.mContext, SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.color.person_orange_color_night : R.color.person_orange_color);
        if (position == 0) {
            this.zhengqueTxtSpanBuilder = new SpannableStringBuilder(info.getOwn_rank() + "/" + info.getNumber_of_test());
        } else if (position == 1) {
            this.zhengqueTxtSpanBuilder = new SpannableStringBuilder(info.getOwn_rank_2() + "/" + info.getNumber_of_test_2());
        } else if (position == 2) {
            this.zhengqueTxtSpanBuilder = new SpannableStringBuilder(info.getOwn_rank_3() + "/" + info.getNumber_of_test_3());
        }
        this.zhengqueTxtSpanBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), 0, info.getOwn_rank().length(), 34);
        this.mTvRank.setText(this.zhengqueTxtSpanBuilder);
        if (isAll) {
            this.mTvNickName.setText(UserConfig.getInstance().getUser().getNickname());
            return;
        }
        String str2 = (!TextUtils.isEmpty(info.getSchool_department_title()) ? info.getSchool_department_title() : info.getSchool_title()) + "   " + info.getZhuan_xue();
        if (TextUtils.isEmpty(info.getMajor_direction_title())) {
            str = str2 + "   " + info.getMajor_title();
        } else {
            str = str2 + "   " + info.getMajor_direction_title();
        }
        this.mTvNickName.setText(str);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        String stringExtra = getIntent().getStringExtra("is_school_rank");
        this.bindSchool = getIntent().getStringExtra("bindSchool");
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.ly_tabbar);
        this.mImgUserHead = (CircleImageView) findViewById(R.id.img_user_head);
        this.mTvNickName = (TextView) findViewById(R.id.tv_nickname);
        this.magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.mQuestionViewpager = (ViewPager) findViewById(R.id.viewpager);
        this.mBtnShare = (TextView) findViewById(R.id.btn_share);
        this.mTvScore = (TextView) findViewById(R.id.tv_score);
        this.mTvRank = (TextView) findViewById(R.id.tv_rank);
        this.mLyScoreInfo = (LinearLayout) findViewById(R.id.ly_score);
        this.iv_ranking_back = (ImageView) findViewById(R.id.iv_ranking_back);
        this.mTvNameInfo = (TextView) findViewById(R.id.tv_name_info);
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.topMargin = statusBarHeight;
        relativeLayout.setLayoutParams(layoutParams);
        if (TextUtils.isEmpty(stringExtra) || !stringExtra.equals("1")) {
            this.mTitleList = new String[]{"全部院校"};
        } else {
            this.mTitleList = new String[]{"全部院校", "考研院校"};
        }
        Bundle bundle = new Bundle();
        bundle.putInt("position", 0);
        bundle.putString("title", getIntent().getStringExtra("title"));
        bundle.putString("exam_id", getIntent().getExtras().getString("exam_id"));
        bundle.putString("bindSchool", this.bindSchool);
        this.listviewpage.add(new BaseViewPagerAdapter.PagerInfo(this.mTitleList[0], MockRankingFrag.class, bundle));
        if (this.mTitleList.length > 1) {
            Bundle bundle2 = new Bundle();
            bundle2.putInt("position", 1);
            bundle2.putString("title", getIntent().getStringExtra("title"));
            bundle2.putString("exam_id", getIntent().getExtras().getString("exam_id"));
            bundle2.putString("bindSchool", this.bindSchool);
            this.listviewpage.add(new BaseViewPagerAdapter.PagerInfo(this.mTitleList[0], MockTypeRankingFragment.class, bundle2));
        }
        initMagicIndicator();
        GlideUtils.loadImage(this, UserConfig.getInstance().getUser().getAvatar(), this.mImgUserHead);
        this.mTvNickName.setText(UserConfig.getInstance().getUser().getNickname());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("bindSuccess")) {
            this.bindSchool = "1";
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, true);
        this.mActionBar.hide();
        setContentView(R.layout.layout_mock_ranking);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnShare.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.xf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RankingActivity.lambda$setListenerForWidget$0(view);
            }
        });
        this.iv_ranking_back.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.yf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14216c.lambda$setListenerForWidget$1(view);
            }
        });
    }

    public void setmTvNameInfo(String tvNameInfo, int position) {
        this.mTvNameInfo.setText(tvNameInfo);
        updateUi(this.schoolInfo, false, position);
    }

    @Subscribe
    public void onEventMainThread(RankingContentBean.DataBean event) {
        if (event.isAll()) {
            this.allInfo = event;
            updateUi(event, true, 0);
            return;
        }
        this.schoolInfo = event;
        if (this.mLyScoreInfo.getVisibility() == 8) {
            updateUi(this.schoolInfo, false, 0);
            this.mLyScoreInfo.setVisibility(0);
        }
    }
}
