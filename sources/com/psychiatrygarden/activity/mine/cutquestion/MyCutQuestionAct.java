package com.psychiatrygarden.activity.mine.cutquestion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
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
import com.aliyun.vod.common.utils.UriUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.LocalBroadcastManager;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class MyCutQuestionAct extends BaseActivity {
    private MagicIndicator magic_indicator;
    private ViewPager viewpager;
    private int mItemWidth = 0;
    private int currentPositon = 0;
    private List<SelectIdentityBean.DataBean> mChildren = new ArrayList();
    private List<Boolean> isShowEditList = new ArrayList();
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionAct.3
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
            MyCutQuestionAct.this.magic_indicator.onPageScrollStateChanged(state);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            MyCutQuestionAct.this.magic_indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            MyCutQuestionAct.this.magic_indicator.onPageSelected(position);
            MyCutQuestionAct.this.currentPositon = position;
            MyCutQuestionAct.this.isShowEdit();
        }
    };
    private BroadcastReceiver isShowEditCut = new BroadcastReceiver() { // from class: com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionAct.5
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String identity_id = ((SelectIdentityBean.DataBean) MyCutQuestionAct.this.mChildren.get(MyCutQuestionAct.this.currentPositon)).getIdentity_id();
            String stringExtra = intent.getStringExtra("identityId");
            boolean booleanExtra = intent.getBooleanExtra("isShowEdit", false);
            if (identity_id.equals(stringExtra)) {
                MyCutQuestionAct.this.isShowEditList.set(MyCutQuestionAct.this.currentPositon, Boolean.valueOf(booleanExtra));
                Log.e("isShowEdit", "isShowEdit=" + MyCutQuestionAct.this.isShowEditList);
                if (booleanExtra) {
                    ((BaseActivity) MyCutQuestionAct.this).mBtnActionbarRight.setText("编辑");
                    ((BaseActivity) MyCutQuestionAct.this).mBtnActionbarRight.setEnabled(true);
                    ((BaseActivity) MyCutQuestionAct.this).mBtnActionbarRight.setVisibility(0);
                } else {
                    ((BaseActivity) MyCutQuestionAct.this).mBtnActionbarRight.setText("");
                    ((BaseActivity) MyCutQuestionAct.this).mBtnActionbarRight.setEnabled(false);
                    ((BaseActivity) MyCutQuestionAct.this).mBtnActionbarRight.setVisibility(8);
                }
            }
        }
    };

    /* renamed from: com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionAct$2, reason: invalid class name */
    public class AnonymousClass2 extends CommonNavigatorAdapter {
        final /* synthetic */ List val$children;

        public AnonymousClass2(final List val$children) {
            this.val$children = val$children;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            MyCutQuestionAct.this.viewpager.setCurrentItem(i2);
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
            linePagerIndicator.setColors(Integer.valueOf(context.getTheme().obtainStyledAttributes(new int[]{R.attr.dominant_color}).getColor(0, SupportMenu.CATEGORY_MASK)));
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
            commonPagerTitleView.setContentView(R.layout.item_question_column);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_column_name);
            textView.setText(((SelectIdentityBean.DataBean) this.val$children.get(index)).getTitle() + "");
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.m
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f12808c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionAct.2.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setTextSize(14.0f);
                    if (SkinManager.getCurrentSkinType(MyCutQuestionAct.this.mContext) == 1) {
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
                    if (SkinManager.getCurrentSkinType(MyCutQuestionAct.this.mContext) == 1) {
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
    public void initTabData(final List<SelectIdentityBean.DataBean> children) throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(false);
        commonNavigator.setAdapter(new AnonymousClass2(children));
        this.magic_indicator.setNavigator(commonNavigator);
        if (this.mContext == null) {
            return;
        }
        for (int i2 = 0; i2 < children.size(); i2++) {
            this.isShowEditList.add(Boolean.FALSE);
        }
        List<BaseViewPagerAdapter.PagerInfo> viewPageInfo = getViewPageInfo(children);
        if (viewPageInfo == null || viewPageInfo.size() <= 1) {
            this.magic_indicator.setVisibility(8);
        } else {
            this.magic_indicator.setVisibility(0);
        }
        this.viewpager.setAdapter(new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), viewPageInfo));
        this.viewpager.setOffscreenPageLimit(3);
        this.viewpager.setCurrentItem(this.currentPositon);
        this.magic_indicator.onPageSelected(this.currentPositon);
        this.viewpager.addOnPageChangeListener(this.onPageChangeListener);
        this.viewpager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.cutquestion.k
            @Override // java.lang.Runnable
            public final void run() {
                this.f12805c.lambda$initTabData$0(children);
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void isShowEdit() {
        if (this.isShowEditList.get(this.currentPositon).booleanValue()) {
            this.mBtnActionbarRight.setText("编辑");
            this.mBtnActionbarRight.setEnabled(true);
            this.mBtnActionbarRight.setVisibility(0);
        } else {
            this.mBtnActionbarRight.setText("");
            this.mBtnActionbarRight.setEnabled(false);
            this.mBtnActionbarRight.setVisibility(8);
        }
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
        startActivity(EditMyCutQuestionAct.newIntent(this, this.mChildren.get(this.currentPositon).getIdentity_id(), this.mChildren.get(this.currentPositon).getModule_type()));
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) MyCutQuestionAct.class);
    }

    public void getTabData() throws Resources.NotFoundException {
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.catalogue_q, this.mContext, "");
        if (TextUtils.isEmpty(strConfig)) {
            getTabLabel();
            return;
        }
        List<SelectIdentityBean.DataBean> list = (List) new Gson().fromJson(strConfig, new TypeToken<List<SelectIdentityBean.DataBean>>() { // from class: com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionAct.1
        }.getType());
        ListIterator<SelectIdentityBean.DataBean> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            if ("0".equals(listIterator.next().getIdentity_id())) {
                listIterator.remove();
            }
        }
        this.mChildren = list;
        initTabData(list);
    }

    public void getTabLabel() {
        YJYHttpUtils.get(this, NetworkRequestsURL.getIdentityLabel, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.cutquestion.MyCutQuestionAct.4
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
                super.onSuccess((AnonymousClass4) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SelectIdentityBean.DataBean dataBean = (SelectIdentityBean.DataBean) new Gson().fromJson(jSONObject.optString("data"), SelectIdentityBean.DataBean.class);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.catalogue_q, new Gson().toJson(dataBean.getChildren()), MyCutQuestionAct.this.mContext);
                        MyCutQuestionAct.this.mChildren = dataBean.getChildren();
                        MyCutQuestionAct.this.initTabData(dataBean.getChildren());
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
                bundle.putString(UriUtil.QUERY_CATEGORY, "" + children.get(i2).getCategory());
                bundle.putString("module_type", "" + children.get(i2).getModule_type());
                if (children.get(i2).getCategory().equals("year")) {
                    SharePreferencesUtils.writeStrConfig(CommonParameter.default_identity_id, children.get(i2).getDefault_identity_id() + "", this.mContext);
                }
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(children.get(i2).getTitle(), MyCutQuestionFragment.class, bundle));
            }
        }
        return arrayList;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        LocalBroadcastManager.getInstance(this.mContext).registerReceiver(this.isShowEditCut, new IntentFilter("isShowEditCut"));
        this.mItemWidth = CommonUtil.getScreenWidth(this.mContext) / 4;
        this.magic_indicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        setTitle("已斩试题");
        getTabData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this.mContext).unregisterReceiver(this.isShowEditCut);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_my_cut_question);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.cutquestion.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12807c.lambda$setListenerForWidget$1(view);
            }
        });
    }
}
