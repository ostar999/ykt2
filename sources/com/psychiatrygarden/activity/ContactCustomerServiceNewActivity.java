package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.ContactTypeBeam;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.fragmenthome.QuestionListTypeFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ButtomContactPopwindow;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ContactCustomerServiceNewActivity extends BaseActivity {
    public RelativeLayout costomer;
    public BaseViewPagerAdapter mBaseView;
    public CommonNavigator mCommonNavigator;
    public MagicIndicator maicview;
    public ViewPager viewpage;
    public String[] titles = new String[0];
    public List<BaseViewPagerAdapter.PagerInfo> listviewpage = new ArrayList();

    /* renamed from: com.psychiatrygarden.activity.ContactCustomerServiceNewActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends CommonNavigatorAdapter {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            ContactCustomerServiceNewActivity.this.viewpage.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return ContactCustomerServiceNewActivity.this.titles.length;
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
            textView.setText(ContactCustomerServiceNewActivity.this.titles[index]);
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.b8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f11100c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.ContactCustomerServiceNewActivity.1.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setTextSize(14.0f);
                    if (SkinManager.getCurrentSkinType(ContactCustomerServiceNewActivity.this.mContext) == 1) {
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
                    if (SkinManager.getCurrentSkinType(ContactCustomerServiceNewActivity.this.mContext) == 1) {
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
    public /* synthetic */ void lambda$init$0(View view) {
        Intent intent = new Intent(this, (Class<?>) QuestionListTypeActivity.class);
        intent.putExtra("category_id", "");
        intent.putExtra("title", "搜索");
        intent.putExtra(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, "");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        ProjectApp.instance().showDialogWindow();
        getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2() {
        if (this.titles.length > 0) {
            try {
                this.viewpage.setScrollX(30);
                if (this.viewpage.beginFakeDrag()) {
                    this.viewpage.fakeDragBy(20.0f);
                    this.viewpage.endFakeDrag();
                    this.viewpage.setScrollX(0);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void getData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.kefucsApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ContactCustomerServiceNewActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                ProjectApp.instance().hideDialogWindow();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        new XPopup.Builder(ContactCustomerServiceNewActivity.this).asCustom(new ButtomContactPopwindow(ContactCustomerServiceNewActivity.this, (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<OnlineServiceBean>>() { // from class: com.psychiatrygarden.activity.ContactCustomerServiceNewActivity.3.1
                        }.getType()))).show();
                    } else {
                        ToastUtil.shortToast(ContactCustomerServiceNewActivity.this, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getQuestionTypeList() {
        YJYHttpUtils.get(this, NetworkRequestsURL.isscategoryUrl, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ContactCustomerServiceNewActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    ContactTypeBeam contactTypeBeam = (ContactTypeBeam) new Gson().fromJson(s2, ContactTypeBeam.class);
                    if (!contactTypeBeam.getCode().equals("200") || contactTypeBeam.getData().size() <= 0) {
                        return;
                    }
                    ContactCustomerServiceNewActivity.this.titles = new String[contactTypeBeam.getData().size()];
                    ContactCustomerServiceNewActivity.this.listviewpage.clear();
                    for (int i2 = 0; i2 < contactTypeBeam.getData().size(); i2++) {
                        ContactCustomerServiceNewActivity.this.titles[i2] = contactTypeBeam.getData().get(i2).getTitle();
                        Bundle bundle = new Bundle();
                        bundle.putString("category_id", "" + contactTypeBeam.getData().get(i2).getId());
                        ContactCustomerServiceNewActivity.this.listviewpage.add(new BaseViewPagerAdapter.PagerInfo(contactTypeBeam.getData().get(i2).getTitle(), QuestionListTypeFragment.class, bundle));
                    }
                    ContactCustomerServiceNewActivity.this.mCommonNavigator.notifyDataSetChanged();
                    ContactCustomerServiceNewActivity.this.mBaseView.notifyDataSetChanged();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        setTitle(R.string.help_center_text);
        this.iv_actionbar_right.setVisibility(0);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.iv_actionbar_right.setImageResource(R.drawable.index_search_img);
        } else {
            this.iv_actionbar_right.setImageResource(R.drawable.index_search_img_night);
        }
        this.mBtnActionbarRight.setVisibility(8);
        this.costomer = (RelativeLayout) findViewById(R.id.costomer);
        this.maicview = (MagicIndicator) findViewById(R.id.maicview);
        this.viewpage = (ViewPager) findViewById(R.id.viewpage);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        this.mCommonNavigator = commonNavigator;
        commonNavigator.setAdapter(new AnonymousClass1());
        this.maicview.setNavigator(this.mCommonNavigator);
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this, getSupportFragmentManager(), this.listviewpage);
        this.mBaseView = baseViewPagerAdapter;
        this.viewpage.setAdapter(baseViewPagerAdapter);
        ViewPagerHelper.bind(this.maicview, this.viewpage);
        getQuestionTypeList();
        this.iv_actionbar_right.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.y7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14208c.lambda$init$0(view);
            }
        });
        this.costomer.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.z7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14238c.lambda$init$1(view);
            }
        });
        this.viewpage.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.a8
            @Override // java.lang.Runnable
            public final void run() {
                this.f10986c.lambda$init$2();
            }
        }, 200L);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_contact_new);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
