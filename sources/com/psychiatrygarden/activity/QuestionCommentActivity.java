package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators.LinePagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.comment.fragment.DiscussFragment;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CommentAnswerPopwndow;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionCommentActivity extends BaseActivity {
    public BaseViewPagerAdapter mBaseView;
    public CommonNavigator mCommonNavigator;
    public ViewPager mQuestionViewpager;
    public MagicIndicator magicIndicator;
    public List<BaseViewPagerAdapter.PagerInfo> listviewpage = new ArrayList();
    public String[] mTitleList = {"最高热度", "最多点赞", "最新发表", "最多回复"};

    /* renamed from: com.psychiatrygarden.activity.QuestionCommentActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends CommonNavigatorAdapter {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            QuestionCommentActivity.this.mQuestionViewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            String[] strArr = QuestionCommentActivity.this.mTitleList;
            if (strArr == null) {
                return 0;
            }
            return strArr.length;
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
            linePagerIndicator.setColors(Integer.valueOf(context.getTheme().obtainStyledAttributes(new int[]{R.attr.main_theme_color}).getColor(0, SupportMenu.CATEGORY_MASK)));
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
            commonPagerTitleView.setContentView(R.layout.item_question_column);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_column_name);
            textView.setText(QuestionCommentActivity.this.mTitleList[index]);
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.nf
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f13047c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.QuestionCommentActivity.1.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setTextSize(14.0f);
                    if (SkinManager.getCurrentSkinType(QuestionCommentActivity.this.mContext) == 1) {
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
                    if (SkinManager.getCurrentSkinType(QuestionCommentActivity.this.mContext) == 1) {
                        textView.setTextColor(Color.parseColor("#7380A9"));
                    } else {
                        textView.setTextColor(Color.parseColor("#141516"));
                    }
                }
            });
            return commonPagerTitleView;
        }
    }

    private void initMagicIndicator() throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        this.mCommonNavigator = commonNavigator;
        commonNavigator.setAdapter(new AnonymousClass1());
        this.magicIndicator.setNavigator(this.mCommonNavigator);
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), this.listviewpage);
        this.mBaseView = baseViewPagerAdapter;
        this.mQuestionViewpager.setAdapter(baseViewPagerAdapter);
        this.mQuestionViewpager.setOffscreenPageLimit(5);
        this.mQuestionViewpager.setCurrentItem(0);
        this.mQuestionViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.QuestionCommentActivity.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float v2, int i12) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) {
            }
        });
        ViewPagerHelper.bind(this.magicIndicator, this.mQuestionViewpager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        new XPopup.Builder(this).hasShadowBg(Boolean.FALSE).asCustom(new CommentAnswerPopwndow(this)).show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        int i2 = getIntent().getExtras().getInt("module_type");
        this.magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.mQuestionViewpager = (ViewPager) findViewById(R.id.viewpager);
        for (int i3 = 0; i3 < this.mTitleList.length; i3++) {
            Bundle bundle = new Bundle();
            if (i3 == 0) {
                bundle.putString("recommendType", "recommend");
            } else if (i3 == 1) {
                bundle.putString("recommendType", "");
            } else if (i3 == 2) {
                bundle.putString("recommendType", "newst");
            } else if (i3 == 3) {
                bundle.putString("recommendType", "replies");
            } else if (i3 == 4) {
                bundle.putString("recommendType", "own");
            }
            bundle.putString("obj_id", "" + getIntent().getExtras().getString("question_id"));
            bundle.putInt("module_type", i2);
            bundle.putString("comment_type", "2");
            bundle.putInt("count", 0);
            bundle.putSerializable("commentEnum", DiscussStatus.Recomend);
            bundle.putBoolean("isLastposition", true);
            this.listviewpage.add(new BaseViewPagerAdapter.PagerInfo(this.mTitleList[i3], DiscussFragment.class, bundle));
        }
        initMagicIndicator();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getExtras().getString("title"));
        this.mBtnActionbarRight.setVisibility(8);
        this.iv_actionbar_right.setVisibility(0);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mBtnActionbarBack.setImageResource(R.mipmap.ic_push_circle_close_day);
            this.iv_actionbar_right.setImageResource(R.drawable.caimg);
        } else {
            this.mBtnActionbarBack.setImageResource(R.mipmap.ic_push_circle_close_night);
            this.iv_actionbar_right.setImageResource(R.drawable.caimg_night);
        }
        this.iv_actionbar_right.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12764c.lambda$onCreate$0(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_question_comment);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
