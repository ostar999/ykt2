package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.psychiatrygarden.bean.QuestionCategoryBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.fragmenthome.CombineQuestionBaseFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CombineQuestionMainNewActivity extends BaseActivity {
    public static final String TYPE_TAG = "type";
    private List<QuestionCategoryBean> listTabs = new ArrayList();
    private MagicIndicator magicIndicator;
    private String type;
    private ViewPager viewPager;

    /* renamed from: com.psychiatrygarden.activity.CombineQuestionMainNewActivity$2, reason: invalid class name */
    public class AnonymousClass2 extends CommonNavigatorAdapter {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            CombineQuestionMainNewActivity.this.viewPager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return CombineQuestionMainNewActivity.this.listTabs.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(final Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(CombineQuestionMainNewActivity.this);
            commonPagerTitleView.setContentView(R.layout.item_tabs_zuti);
            commonPagerTitleView.findViewById(R.id.viewStart).setVisibility(index == 0 ? 0 : 8);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_tabs_name);
            final ImageView imageView = (ImageView) commonPagerTitleView.findViewById(R.id.img_choose);
            textView.setText(((QuestionCategoryBean) CombineQuestionMainNewActivity.this.listTabs.get(index)).getTitle());
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.k2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f12575c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.CombineQuestionMainNewActivity.2.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setTextSize(2, 14.0f);
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.third_txt_color));
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
                    textView.setTextSize(2, 16.0f);
                    textView.setTextColor(SkinManager.getThemeColor(context, R.attr.first_text_color));
                    imageView.setVisibility(0);
                }
            });
            return commonPagerTitleView;
        }
    }

    private void getQuestionCategoryZuTi() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext));
        ajaxParams.put("is_paper", "1");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.questionCategory, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CombineQuestionMainNewActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CombineQuestionMainNewActivity.this.finish();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.questionCategoryDataZuTi, jSONObject.optString("data"), CombineQuestionMainNewActivity.this.mContext);
                        CombineQuestionMainNewActivity.this.getTabList();
                    }
                } catch (Exception unused) {
                    CombineQuestionMainNewActivity.this.finish();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getTabList() throws Resources.NotFoundException {
        this.listTabs.clear();
        List<QuestionCategoryBean> questionCategoryListZuTi = CommonUtil.getQuestionCategoryListZuTi(this.mContext);
        if (questionCategoryListZuTi.isEmpty()) {
            return;
        }
        Iterator<QuestionCategoryBean> it = questionCategoryListZuTi.iterator();
        while (it.hasNext()) {
            QuestionCategoryBean next = it.next();
            if (next.getChildren() == null || next.getChildren().isEmpty()) {
                it.remove();
            }
        }
        this.listTabs.addAll(questionCategoryListZuTi);
        initTab();
    }

    public static void gotoCombineQuestionMain(Context context, String type) {
        Intent intent = new Intent(context, (Class<?>) CombineQuestionMainNewActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    private void initTab() throws Resources.NotFoundException {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.listTabs.size(); i2++) {
            Bundle bundle = new Bundle();
            bundle.putString("type", this.type);
            bundle.putString("id", this.listTabs.get(i2).getId());
            bundle.putString("tabType", this.listTabs.get(i2).getType());
            bundle.putSerializable("tabList", (Serializable) this.listTabs);
            arrayList.add(new BaseViewPagerAdapter.PagerInfo("", CombineQuestionBaseFragment.class, bundle));
        }
        this.viewPager.setAdapter(new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), arrayList));
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new AnonymousClass2());
        this.magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(this.magicIndicator, this.viewPager);
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.CombineQuestionMainNewActivity.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
            }
        });
        this.viewPager.setOffscreenPageLimit(this.listTabs.size());
        this.magicIndicator.setVisibility(this.listTabs.size() <= 1 ? 8 : 0);
        ImageView imageView = (ImageView) findViewById(R.id.iv_actionbar_record);
        findViewById(R.id.iv_actionbar_back).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.i2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12493c.lambda$initTab$0(view);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.j2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12544c.lambda$initTab$1(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTab$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTab$1(View view) {
        CombineQuestionRecordListActivity.navigationToCombineQuestionRecordList(this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.viewPager = (ViewPager) findViewById(R.id.viewPager);
        this.magicIndicator = (MagicIndicator) findViewById(R.id.magicIndicator);
        this.type = getIntent().getStringExtra("type");
        getQuestionCategoryZuTi();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BaseViewPagerAdapter baseViewPagerAdapter = (BaseViewPagerAdapter) this.viewPager.getAdapter();
        if (baseViewPagerAdapter != null) {
            baseViewPagerAdapter.getCurFragment().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.activity_combine_question_guide_new);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
