package com.psychiatrygarden.activity.online;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Switch;
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
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CombineQuestionMainNewActivity;
import com.psychiatrygarden.activity.online.fragment.QuestionBankNewFragment;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ErrorExportPopuWindow;
import com.psychiatrygarden.widget.ExportDescriptionPop;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class QuestionWCNHomeActivity extends BaseActivity {
    private View mLine;
    private MagicIndicator magic_indicator;
    private ViewPager viewpager;
    private List<SelectIdentityBean.DataBean> children = new ArrayList();
    private int mItemWidth = 0;
    private int currentPositon = 0;
    private String mType = "";
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.online.QuestionWCNHomeActivity.2
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
            QuestionWCNHomeActivity.this.magic_indicator.onPageScrollStateChanged(state);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            QuestionWCNHomeActivity.this.magic_indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            QuestionWCNHomeActivity.this.magic_indicator.onPageSelected(position);
            QuestionWCNHomeActivity.this.currentPositon = position;
            QuestionWCNHomeActivity.this.setTitles();
        }
    };

    /* renamed from: com.psychiatrygarden.activity.online.QuestionWCNHomeActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends CommonNavigatorAdapter {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            QuestionWCNHomeActivity.this.viewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            if (QuestionWCNHomeActivity.this.children == null) {
                return 0;
            }
            return QuestionWCNHomeActivity.this.children.size();
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
            textView.setText(((SelectIdentityBean.DataBean) QuestionWCNHomeActivity.this.children.get(index)).getTitle() + "");
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.x1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f13503c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.online.QuestionWCNHomeActivity.1.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setTextSize(14.0f);
                    if (SkinManager.getCurrentSkinType(QuestionWCNHomeActivity.this.mContext) == 1) {
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
                    if (SkinManager.getCurrentSkinType(QuestionWCNHomeActivity.this.mContext) == 1) {
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
    public /* synthetic */ void lambda$getmagicData$0() {
        if (this.children.isEmpty()) {
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
    public /* synthetic */ void lambda$setListenerForWidget$1(String str, Switch r3) {
        if (!str.equals("export")) {
            if (!str.equals("compose_test")) {
                settingErrorConfig(str, r3);
                return;
            } else {
                "error".equals(this.mType);
                CombineQuestionMainNewActivity.gotoCombineQuestionMain(this, "error".equals(this.mType) ? "error" : "collection");
                return;
            }
        }
        pointCount(this.mContext, this.mType);
        if (UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
            startActivity(new Intent(this, (Class<?>) MemberCenterActivity.class));
            return;
        }
        Bundle extras = getIntent().getExtras();
        extras.putString(UriUtil.QUERY_CATEGORY, this.children.get(this.currentPositon).getCategory());
        extras.putString("module_type", this.children.get(this.currentPositon).getModule_type());
        extras.putString("identity_id", this.children.get(this.currentPositon).getIdentity_id());
        extras.putString("export_func_identity_id", this.children.get(this.currentPositon).getExport_func_identity_id());
        new XPopup.Builder(this).asCustom(new ExportDescriptionPop(this, extras)).toggle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        new XPopup.Builder(this).asCustom(new ErrorExportPopuWindow(this, this.mType, new ErrorExportPopuWindow.ClickIml() { // from class: com.psychiatrygarden.activity.online.v1
            @Override // com.psychiatrygarden.widget.ErrorExportPopuWindow.ClickIml
            public final void mClickIml(String str, Switch r3) {
                this.f13495a.lambda$setListenerForWidget$1(str, r3);
            }
        })).show();
    }

    private void settingErrorConfig(final String status, final Switch errorSetting) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("status", status);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.settingErrorConfig, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.QuestionWCNHomeActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                QuestionWCNHomeActivity.this.hideProgressDialog();
                errorSetting.setChecked(UserConfig.getInstance().getUser().getError_set().equals("1"));
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                QuestionWCNHomeActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        UserConfig.getInstance().getUser().setError_set(status);
                    } else {
                        String strOptString = jSONObject.optString("message");
                        errorSetting.setChecked(UserConfig.getInstance().getUser().getError_set().equals("1"));
                        ToastUtil.shortToast(QuestionWCNHomeActivity.this, strOptString);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                QuestionWCNHomeActivity.this.hideProgressDialog();
            }
        });
        AliyunEvent aliyunEvent = AliyunEvent.ErrorQuestionSetting;
        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", "", "", "", "2");
    }

    public void getNavigatorList() {
        List<SelectIdentityBean.DataBean> list = (List) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.catalogue_q, this.mContext, ""), new TypeToken<List<SelectIdentityBean.DataBean>>() { // from class: com.psychiatrygarden.activity.online.QuestionWCNHomeActivity.3
        }.getType());
        this.children = list;
        if (list != null) {
            ListIterator<SelectIdentityBean.DataBean> listIterator = list.listIterator();
            while (listIterator.hasNext()) {
                if ("0".equals(listIterator.next().getIdentity_id())) {
                    listIterator.remove();
                }
            }
        }
    }

    public List<BaseViewPagerAdapter.PagerInfo> getViewPageInfo() {
        ArrayList arrayList = new ArrayList();
        List<SelectIdentityBean.DataBean> list = this.children;
        if (list != null && list.size() > 0) {
            for (int i2 = 0; i2 < this.children.size(); i2++) {
                Bundle bundle = new Bundle();
                bundle.putString("identity_id", "" + this.children.get(i2).getIdentity_id());
                bundle.putString("export_func_identity_id", "" + this.children.get(i2).getExport_func_identity_id());
                bundle.putString(UriUtil.QUERY_CATEGORY, "" + this.children.get(i2).getCategory());
                if (getIntent().getExtras().getString("identity_id").equals("" + this.children.get(i2).getIdentity_id())) {
                    this.currentPositon = i2;
                }
                bundle.putString("module_type", "" + this.children.get(i2).getModule_type());
                if (this.children.get(i2).getCategory().equals("year")) {
                    SharePreferencesUtils.writeStrConfig(CommonParameter.default_identity_id, this.children.get(i2).getDefault_identity_id() + "", this.mContext);
                }
                bundle.putString("type", getIntent().getExtras().getString("type"));
                bundle.putString("is_show_number", getIntent().getExtras().getString("is_show_number"));
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(this.children.get(i2).getTitle(), QuestionBankNewFragment.class, bundle));
            }
        }
        return arrayList;
    }

    public void getmagicData() throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(false);
        commonNavigator.setAdapter(new AnonymousClass1());
        this.magic_indicator.setNavigator(commonNavigator);
        if (this.mContext == null) {
            return;
        }
        List<BaseViewPagerAdapter.PagerInfo> viewPageInfo = getViewPageInfo();
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
        this.viewpager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.w1
            @Override // java.lang.Runnable
            public final void run() {
                this.f13499c.lambda$getmagicData$0();
            }
        }, 200L);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        this.mItemWidth = CommonUtil.getScreenWidth(this.mContext) / 4;
        this.mBtnActionbarRight.setVisibility(8);
        this.iv_right_img.setVisibility(0);
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            this.iv_right_img.setImageResource(R.mipmap.ic_more_night);
        } else {
            this.iv_right_img.setImageResource(R.mipmap.ic_more);
        }
        this.magic_indicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        View viewFindViewById = findViewById(R.id.line);
        this.mLine = viewFindViewById;
        viewFindViewById.setVisibility(8);
        initTabColumn();
        setTitles();
    }

    public void initTabColumn() throws Resources.NotFoundException {
        getNavigatorList();
        getmagicData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_wcn_homepage);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.iv_right_img.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.u1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13490c.lambda$setListenerForWidget$2(view);
            }
        });
    }

    public void setTitles() {
        this.mType = getIntent().getExtras().getString("type");
        String category = this.children.get(this.viewpager.getCurrentItem()).getCategory();
        boolean z2 = "points".equals(category) || "cases".equals(category) || "unit".equals(category);
        if ("error".equals(this.mType)) {
            setTitle("我的错题");
            if (z2) {
                this.iv_right_img.setVisibility(8);
                return;
            } else {
                this.iv_right_img.setVisibility(0);
                return;
            }
        }
        if ("collection".equals(this.mType)) {
            setTitle("我的收藏");
            if (z2) {
                this.iv_right_img.setVisibility(8);
                return;
            } else {
                this.iv_right_img.setVisibility(0);
                return;
            }
        }
        if ("note".equals(this.mType)) {
            setTitle("我的笔记");
            if (z2) {
                this.iv_right_img.setVisibility(8);
                return;
            } else {
                this.iv_right_img.setVisibility(0);
                return;
            }
        }
        if ("praise".equals(this.mType)) {
            setTitle("我的点赞");
            this.iv_right_img.setVisibility(8);
        } else if (!ClientCookie.COMMENT_ATTR.equals(this.mType)) {
            this.iv_right_img.setVisibility(8);
        } else {
            setTitle("我的评论");
            this.iv_right_img.setVisibility(8);
        }
    }
}
