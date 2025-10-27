package com.psychiatrygarden.ranking;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.google.gson.Gson;
import com.huawei.hms.push.HmsMessageService;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.DataStatisticsInfoDialog;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class QuestionStatisticsAct extends BaseActivity {
    private ImageView mImgBack;
    private ImageView mImgQuestion;
    private MagicIndicator magic_indicator;
    private ViewPager viewpager;
    private int currentPositon = 0;
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.ranking.QuestionStatisticsAct.2
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
            QuestionStatisticsAct.this.magic_indicator.onPageScrollStateChanged(state);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            QuestionStatisticsAct.this.magic_indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            QuestionStatisticsAct.this.magic_indicator.onPageSelected(position);
            QuestionStatisticsAct.this.currentPositon = position;
        }
    };

    /* renamed from: com.psychiatrygarden.ranking.QuestionStatisticsAct$1, reason: invalid class name */
    public class AnonymousClass1 extends CommonNavigatorAdapter {
        final /* synthetic */ List val$children;

        public AnonymousClass1(final List val$children) {
            this.val$children = val$children;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            QuestionStatisticsAct.this.viewpager.setCurrentItem(i2);
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
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(QuestionStatisticsAct.this);
            commonPagerTitleView.setContentView(R.layout.item_ranking_tab);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tab_name);
            final ImageView imageView = (ImageView) commonPagerTitleView.findViewById(R.id.img_line);
            textView.setText(((SelectIdentityBean.DataBean) this.val$children.get(index)).getTitle());
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.k
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f16208c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.ranking.QuestionStatisticsAct.1.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setTextSize(14.0f);
                    textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(QuestionStatisticsAct.this) == 1 ? "#575F79" : "#909499"));
                    imageView.setVisibility(4);
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
                    textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(QuestionStatisticsAct.this) == 1 ? "#7380A9" : "#141516"));
                    imageView.setVisibility(0);
                }
            });
            return commonPagerTitleView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTabData(final List<SelectIdentityBean.DataBean> children) throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(false);
        commonNavigator.setAdapter(new AnonymousClass1(children));
        this.magic_indicator.setNavigator(commonNavigator);
        if (this.mContext == null) {
            return;
        }
        this.viewpager.setAdapter(new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), getViewPageInfo(children)));
        this.viewpager.setOffscreenPageLimit(1);
        this.viewpager.setCurrentItem(this.currentPositon);
        this.magic_indicator.onPageSelected(this.currentPositon);
        this.viewpager.addOnPageChangeListener(this.onPageChangeListener);
        this.viewpager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.ranking.h
            @Override // java.lang.Runnable
            public final void run() {
                this.f16204c.lambda$initTabData$0(children);
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTabData$0(List list) {
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
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        new XPopup.Builder(this).popupAnimation(null).asCustom(new DataStatisticsInfoDialog(this, false, "")).show();
    }

    public static void newIntent(Context context) {
        context.startActivity(new Intent(context, (Class<?>) QuestionStatisticsAct.class));
    }

    public void getTabLabel() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(HmsMessageService.SUBJECT_ID, "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this));
        YJYHttpUtils.post(this, NetworkRequestsURL.getStatisticalTab, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.ranking.QuestionStatisticsAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        QuestionStatisticsAct.this.initTabData(((SelectIdentityBean) new Gson().fromJson(s2, SelectIdentityBean.class)).getData());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public List<BaseViewPagerAdapter.PagerInfo> getViewPageInfo(List<SelectIdentityBean.DataBean> children) {
        ArrayList arrayList = new ArrayList();
        if (children != null && children.size() > 0) {
            for (int i2 = 0; i2 < children.size(); i2++) {
                Bundle bundle = new Bundle();
                bundle.putString("identity_id", "" + children.get(i2).getIdentity_id());
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(children.get(i2).getTitle(), StatisticsFragment.class, bundle));
            }
        }
        return arrayList;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
        this.magic_indicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mImgQuestion = (ImageView) findViewById(R.id.question);
        getTabLabel();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_question_statistics);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16206c.lambda$setListenerForWidget$1(view);
            }
        });
        this.mImgQuestion.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16207c.lambda$setListenerForWidget$2(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setNewStyleStatusBarColor() {
        this.mBaseTheme = SkinManager.getCurrentSkinType(this);
        StatusBarUtil.setColor(this, getTheme().obtainStyledAttributes(new int[]{R.attr.app_bg}).getColor(0, -1), 0);
        if (this.mBaseTheme != 0) {
            getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        } else {
            getWindow().getDecorView().setSystemUiVisibility(8192);
            getWindow().setNavigationBarColor(Color.parseColor("#FFFFFF"));
        }
    }
}
