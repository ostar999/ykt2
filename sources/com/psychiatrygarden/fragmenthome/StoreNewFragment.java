package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.ContactCustomerServiceNewActivity;
import com.psychiatrygarden.activity.GoodsSearchAct;
import com.psychiatrygarden.activity.online.SelectIdentityNewActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ShopBean;
import com.psychiatrygarden.callback.HomeTabSelectedLisenter;
import com.psychiatrygarden.callback.MyCallBackControl;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.tencent.open.SocialConstants;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class StoreNewFragment extends BaseFragment {
    private boolean act;
    private CustomEmptyView empty_view;
    private ImageView iv_network_load_fail;
    private int mItemWidth = 0;
    public ShopBean mShopBean;
    private MagicIndicator shopindicator;
    private ViewPager shopviewpager;

    /* renamed from: com.psychiatrygarden.fragmenthome.StoreNewFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends CommonNavigatorAdapter {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            StoreNewFragment.this.shopviewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return StoreNewFragment.this.mShopBean.getData().size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(StoreNewFragment.this.getActivity());
            commonPagerTitleView.setContentView(R.layout.item_shop_column);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_column_name);
            final ImageView imageView = (ImageView) commonPagerTitleView.findViewById(R.id.img_choose);
            textView.setText(StoreNewFragment.this.mShopBean.getData().get(index).getCat_name());
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.od
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f15896c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.fragmenthome.StoreNewFragment.2.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setTextSize(14.0f);
                    imageView.setVisibility(8);
                    if (SkinManager.getCurrentSkinType(((BaseFragment) StoreNewFragment.this).mContext) == 1) {
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
                    imageView.setVisibility(0);
                    if (SkinManager.getCurrentSkinType(((BaseFragment) StoreNewFragment.this).mContext) == 1) {
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
    public void initTabColumn() throws Resources.NotFoundException {
        ArrayList arrayList = new ArrayList();
        int size = this.mShopBean.getData().size();
        for (int i2 = 0; i2 < size; i2++) {
            Bundle bundle = new Bundle();
            bundle.putString("text", this.mShopBean.getData().get(i2).getCat_id());
            bundle.putInt("id", Integer.parseInt(this.mShopBean.getData().get(i2).getCat_id()));
            arrayList.add(new BaseViewPagerAdapter.PagerInfo(this.mShopBean.getData().get(i2).getCat_name(), ShopFragment.class, bundle));
        }
        this.shopviewpager.setAdapter(new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), arrayList));
        this.shopviewpager.setCurrentItem(0);
        this.shopviewpager.setOffscreenPageLimit(1);
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(true);
        TypedArray typedArrayObtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.main_theme_color});
        typedArrayObtainStyledAttributes.getColor(0, ContextCompat.getColor(this.mContext, R.color.main_theme_color));
        typedArrayObtainStyledAttributes.recycle();
        commonNavigator.setAdapter(new AnonymousClass2());
        this.shopindicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(this.shopindicator, this.shopviewpager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        getActivity().finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        startActivity(new Intent(view.getContext(), (Class<?>) GoodsSearchAct.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        getDataShop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) SelectIdentityNewActivity.class);
        intent.putExtra("flag", false);
        intent.putExtra("appbeanname", "");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$4(View view) {
        if (isLogin()) {
            startActivity(new Intent(getActivity(), (Class<?>) ContactCustomerServiceNewActivity.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$5(String str) {
        LogUtils.e("choose_home_tab", "chooseShop");
        if (str.equals("chooseShop")) {
            getDataShop();
        }
    }

    public static StoreNewFragment newInstance() {
        Bundle bundle = new Bundle();
        StoreNewFragment storeNewFragment = new StoreNewFragment();
        storeNewFragment.setArguments(bundle);
        return storeNewFragment;
    }

    public void getDataShop() {
        this.iv_network_load_fail.setVisibility(8);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.goodsCategory, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.StoreNewFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                StoreNewFragment.this.empty_view.stopAnim();
                StoreNewFragment.this.empty_view.setVisibility(8);
                StoreNewFragment.this.iv_network_load_fail.setVisibility(0);
                StoreNewFragment.this.shopindicator.setVisibility(8);
                if (SkinManager.getCurrentSkinType(((BaseFragment) StoreNewFragment.this).mContext) == 0) {
                    StoreNewFragment.this.iv_network_load_fail.setImageDrawable(ContextCompat.getDrawable(((BaseFragment) StoreNewFragment.this).mContext, R.drawable.icon_net_load_error));
                } else {
                    StoreNewFragment.this.iv_network_load_fail.setImageDrawable(ContextCompat.getDrawable(((BaseFragment) StoreNewFragment.this).mContext, R.drawable.icon_net_load_error_night));
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                StoreNewFragment.this.empty_view.stopAnim();
                StoreNewFragment.this.empty_view.setVisibility(8);
                try {
                    StoreNewFragment.this.mShopBean = (ShopBean) new Gson().fromJson(s2, ShopBean.class);
                    if (!StoreNewFragment.this.mShopBean.getCode().equals("200")) {
                        StoreNewFragment storeNewFragment = StoreNewFragment.this;
                        storeNewFragment.AlertToast(storeNewFragment.mShopBean.getMessage());
                        StoreNewFragment.this.iv_network_load_fail.setVisibility(0);
                        StoreNewFragment.this.shopindicator.setVisibility(8);
                    } else if (StoreNewFragment.this.mShopBean.getData().size() > 0) {
                        StoreNewFragment.this.iv_network_load_fail.setVisibility(8);
                        StoreNewFragment.this.shopindicator.setVisibility(0);
                        StoreNewFragment.this.initTabColumn();
                    } else {
                        StoreNewFragment.this.iv_network_load_fail.setVisibility(0);
                        StoreNewFragment.this.shopindicator.setVisibility(8);
                    }
                } catch (Exception unused) {
                    StoreNewFragment.this.AlertToast("加载失败");
                    StoreNewFragment.this.shopindicator.setVisibility(8);
                    StoreNewFragment.this.iv_network_load_fail.setVisibility(0);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_store_new;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.act = arguments.getBoolean(SocialConstants.PARAM_ACT, false);
        }
        this.mItemWidth = CommonUtil.getScreenWidth(this.mContext) / 4;
        this.empty_view = (CustomEmptyView) holder.get(R.id.empty_view);
        ImageView imageView = (ImageView) holder.get(R.id.iv_switch);
        TextView textView = (TextView) holder.get(R.id.customimg);
        if (this.act) {
            imageView.setVisibility(8);
            holder.get(R.id.iv_back).setVisibility(0);
            holder.get(R.id.iv_back).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.id
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15672c.lambda$initViews$0(view);
                }
            });
        } else {
            imageView.setVisibility(0);
            holder.get(R.id.iv_back).setVisibility(8);
        }
        textView.setVisibility(8);
        this.shopindicator = (MagicIndicator) holder.get(R.id.shopindicator);
        this.shopviewpager = (ViewPager) holder.get(R.id.shopviewpager);
        this.iv_network_load_fail = (ImageView) holder.get(R.id.iv_network_load_fail);
        holder.get(R.id.iv_search_goods).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.jd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15697c.lambda$initViews$1(view);
            }
        });
        this.iv_network_load_fail.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.kd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15723c.lambda$initViews$2(view);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ld
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15814c.lambda$initViews$3(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.md
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15850c.lambda$initViews$4(view);
            }
        });
        MyCallBackControl.getIntence().setHomeTabChooseCallBack(new HomeTabSelectedLisenter() { // from class: com.psychiatrygarden.fragmenthome.nd
            @Override // com.psychiatrygarden.callback.HomeTabSelectedLisenter
            public final void homeTabSelected(String str) {
                this.f15874a.lambda$initViews$5(str);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (str.equals("isIJINGYANLogin")) {
            getDataShop();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Log.e("choose_tab", "走了onLazyInitView");
        getDataShop();
    }

    public static StoreNewFragment newInstance(boolean act) {
        Bundle bundle = new Bundle();
        StoreNewFragment storeNewFragment = new StoreNewFragment();
        bundle.putBoolean(SocialConstants.PARAM_ACT, act);
        storeNewFragment.setArguments(bundle);
        return storeNewFragment;
    }
}
