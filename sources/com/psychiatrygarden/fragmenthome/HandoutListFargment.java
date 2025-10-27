package com.psychiatrygarden.fragmenthome;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.ChannelNewActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.EveHandoutBean;
import com.psychiatrygarden.bean.HandoutChannelBean;
import com.psychiatrygarden.bean.HandoutNewCateBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.LocalBroadcastManager;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.sortlist.PinyinCompatorHandout;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class HandoutListFargment extends BaseFragment {
    private ImageView iv_network_load_fail;
    private LinearLayout line_ids;
    LinearLayout ll_more_columns;
    private ImageView load_anim;
    private BaseViewPagerAdapter mBaseView;
    private CommonNavigator mCommonNavigator;
    private MagicIndicator mMagicIndicator;
    private PinyinCompatorHandout mPinyin;
    RelativeLayout rl_column;
    public ImageView shade_right;
    private ViewPager viewpager;
    private int columnSelectIndex = 0;
    private List<HandoutChannelBean.DataBean.DefaultBean> _default = new ArrayList();
    List<HandoutChannelBean.DataBean.ListBean> _list = new ArrayList();
    private BroadcastReceiver loadDataReceiver = new BroadcastReceiver() { // from class: com.psychiatrygarden.fragmenthome.HandoutListFargment.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            LogUtils.e("收到", "收到了广播");
            HandoutListFargment.this.handler.sendEmptyMessage(0);
        }
    };

    @SuppressLint({"HandlerLeak"})
    private final Handler handler = new Handler() { // from class: com.psychiatrygarden.fragmenthome.HandoutListFargment.2
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                HandoutListFargment.this.line_ids.setVisibility(8);
                HandoutListFargment.this.load_anim.setVisibility(0);
                ((Animatable) HandoutListFargment.this.load_anim.getDrawable()).start();
                HandoutListFargment.this.iv_network_load_fail.setVisibility(8);
                HandoutListFargment.this.getColumnData();
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public void initTabColumn() throws Resources.NotFoundException {
        if (isAdded() && this.mContext != null) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this._default.size(); i2++) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", Integer.parseInt(this._default.get(i2).getId()));
                bundle.putString("text", this._default.get(i2).getTitle());
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(this._default.get(i2).getTitle(), NewsFragment.class, bundle));
            }
            BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), arrayList);
            this.mBaseView = baseViewPagerAdapter;
            this.viewpager.setAdapter(baseViewPagerAdapter);
            this.viewpager.setCurrentItem(0);
            this.viewpager.setOffscreenPageLimit(2);
            CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
            this.mCommonNavigator = commonNavigator;
            commonNavigator.setSkimOver(true);
            this.mCommonNavigator.setAdapter(new CommonNavigatorAdapter() { // from class: com.psychiatrygarden.fragmenthome.HandoutListFargment.4
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
                public int getCount() {
                    return HandoutListFargment.this._default.size();
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
                public IPagerIndicator getIndicator(Context context) {
                    return null;
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
                public IPagerTitleView getTitleView(Context context, final int index) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams.leftMargin = CommonUtil.px2dip(((BaseFragment) HandoutListFargment.this).mContext, 10.0f);
                    layoutParams.rightMargin = CommonUtil.dip2px(((BaseFragment) HandoutListFargment.this).mContext, 20.0f);
                    ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                    colorTransitionPagerTitleView.setText(String.format("%s%s", ((HandoutChannelBean.DataBean.DefaultBean) HandoutListFargment.this._default.get(index)).getTitle(), ((HandoutChannelBean.DataBean.DefaultBean) HandoutListFargment.this._default.get(index)).getPname()));
                    colorTransitionPagerTitleView.setLayoutParams(layoutParams);
                    if (SkinManager.getCurrentSkinType(((BaseFragment) HandoutListFargment.this).mContext) == 1) {
                        colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#7380A9"));
                        colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#B2575C"));
                    } else {
                        colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#555555"));
                        colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#dd594a"));
                    }
                    colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.HandoutListFargment.4.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v2) throws Resources.NotFoundException {
                            HandoutListFargment.this.viewpager.setCurrentItem(index);
                        }
                    });
                    return colorTransitionPagerTitleView;
                }
            });
            this.mMagicIndicator.setNavigator(this.mCommonNavigator);
            ViewPagerHelper.bind(this.mMagicIndicator, this.viewpager);
            mStopAnim(this.load_anim);
            this.load_anim.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.handler.sendEmptyMessage(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        if (isLogin()) {
            Intent intent = new Intent(this.mContext, (Class<?>) ChannelNewActivity.class);
            intent.putExtra("defaultList", (Serializable) this._default);
            intent.putExtra("userList", (Serializable) this._list);
            startActivity(intent);
        }
    }

    public static HandoutListFargment newInstance(boolean isNotice) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isNotice", isNotice);
        HandoutListFargment handoutListFargment = new HandoutListFargment();
        handoutListFargment.setArguments(bundle);
        return handoutListFargment;
    }

    private void showGrade_jingyan() {
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mContext, R.style.MyDialog).create();
        alertDialogCreate.show();
        alertDialogCreate.setCanceledOnTouchOutside(false);
        Window window = alertDialogCreate.getWindow();
        window.setGravity(17);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            window.setContentView(R.layout.jingyan_guide);
            ((ImageView) alertDialogCreate.findViewById(R.id.ikwon)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.HandoutListFargment.5
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    alertDialogCreate.dismiss();
                    SharePreferencesUtils.writeBooleanConfig(CommonParameter.JINGYAN_LIST_HELP, false, ((BaseFragment) HandoutListFargment.this).mContext);
                }
            });
        } else {
            window.setContentView(R.layout.jingyan_guide1);
            ((ImageView) alertDialogCreate.findViewById(R.id.iv_jingyan_top)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.HandoutListFargment.6
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    alertDialogCreate.dismiss();
                    SharePreferencesUtils.writeBooleanConfig(CommonParameter.JINGYAN_LIST_HELP, false, ((BaseFragment) HandoutListFargment.this).mContext);
                }
            });
        }
        WindowManager.LayoutParams attributes = alertDialogCreate.getWindow().getAttributes();
        attributes.height = CommonUtil.getScreenHeight(this.mContext);
        attributes.width = CommonUtil.getScreenWidth(this.mContext);
        alertDialogCreate.getWindow().setAttributes(attributes);
    }

    public void getColumnData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mCategoryListNewUrl, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.HandoutListFargment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                HandoutListFargment.this.iv_network_load_fail.setVisibility(0);
                HandoutListFargment.this.line_ids.setVisibility(8);
                ((Animatable) HandoutListFargment.this.load_anim.getDrawable()).stop();
                HandoutListFargment.this.load_anim.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws Resources.NotFoundException {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    HandoutNewCateBean handoutNewCateBean = (HandoutNewCateBean) new Gson().fromJson(s2, HandoutNewCateBean.class);
                    if (handoutNewCateBean.getCode().equals("200")) {
                        HandoutListFargment.this.line_ids.setVisibility(0);
                        HandoutListFargment.this._default.clear();
                        if (handoutNewCateBean.getData().get_default().size() > 0) {
                            for (int i2 = 0; i2 < handoutNewCateBean.getData().get_default().size(); i2++) {
                                HandoutNewCateBean.DataBean.DefaultBean defaultBean = handoutNewCateBean.getData().get_default().get(i2);
                                HandoutChannelBean.DataBean.DefaultBean defaultBean2 = new HandoutChannelBean.DataBean.DefaultBean();
                                defaultBean2.setSort(defaultBean.getSort());
                                defaultBean2.setTitle(defaultBean.getTitle());
                                defaultBean2.setSelected(defaultBean.getSelected());
                                defaultBean2.setPname(defaultBean.getPname());
                                defaultBean2.setPid(defaultBean.getPid());
                                defaultBean2.setId(defaultBean.getId());
                                HandoutListFargment.this._default.add(defaultBean2);
                            }
                        }
                    }
                    HandoutListFargment.this._list.clear();
                    if (handoutNewCateBean.getData().get_list().size() > 0) {
                        for (int i3 = 0; i3 < handoutNewCateBean.getData().get_list().size(); i3++) {
                            HandoutChannelBean.DataBean.ListBean listBean = new HandoutChannelBean.DataBean.ListBean();
                            listBean.setP_id(handoutNewCateBean.getData().get_list().get(i3).getP_id());
                            listBean.setP_name(handoutNewCateBean.getData().get_list().get(i3).getP_name());
                            ArrayList arrayList = new ArrayList();
                            for (int i4 = 0; i4 < handoutNewCateBean.getData().get_list().get(i3).getSub().size(); i4++) {
                                for (int i5 = 0; i5 < handoutNewCateBean.getData().get_list().get(i3).getSub().get(i4).size(); i5++) {
                                    HandoutNewCateBean.DataBean.ListBean.SubBean subBean = handoutNewCateBean.getData().get_list().get(i3).getSub().get(i4).get(i5);
                                    HandoutChannelBean.DataBean.ListBean.SubBean subBean2 = new HandoutChannelBean.DataBean.ListBean.SubBean();
                                    subBean2.setSelected(subBean.getSelected());
                                    subBean2.setId(subBean.getId());
                                    subBean2.setPid(subBean.getPid());
                                    subBean2.setPname(subBean.getPname());
                                    subBean2.setSort(subBean.getSort());
                                    subBean2.setSortLetters(subBean.getInitials());
                                    subBean2.setTitle(subBean.getTitle());
                                    arrayList.add(subBean2);
                                }
                            }
                            if (Build.VERSION.SDK_INT >= 24) {
                                arrayList.sort(HandoutListFargment.this.mPinyin);
                            } else {
                                Collections.sort(arrayList, HandoutListFargment.this.mPinyin);
                            }
                            listBean.setSub(arrayList);
                            HandoutListFargment.this._list.add(listBean);
                        }
                    }
                    SharePreferencesUtils.writeLongConfig(CommonParameter.Lock_NUM_TYPE, Long.valueOf(handoutNewCateBean.getFixedCount() == null ? 0L : Long.parseLong(handoutNewCateBean.getFixedCount())), ((BaseFragment) HandoutListFargment.this).mContext);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                HandoutListFargment.this.initTabColumn();
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.activity_handoutlist;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        LocalBroadcastManager.getInstance(this.mContext).registerReceiver(this.loadDataReceiver, new IntentFilter("jumpToJingyans"));
        Log.e("choose_home_tab", "选中经验initview");
        boolean z2 = getArguments().getBoolean("isNotice");
        this.mPinyin = new PinyinCompatorHandout();
        this.ll_more_columns = (LinearLayout) holder.get(R.id.ll_more_columns);
        this.rl_column = (RelativeLayout) holder.get(R.id.rl_column);
        this.viewpager = (ViewPager) holder.get(R.id.mViewPager);
        this.iv_network_load_fail = (ImageView) holder.get(R.id.iv_network_load_fail);
        this.shade_right = (ImageView) holder.get(R.id.shade_right);
        this.line_ids = (LinearLayout) holder.get(R.id.line_ids);
        this.load_anim = (ImageView) holder.get(R.id.load_anim);
        this.mMagicIndicator = (MagicIndicator) holder.get(R.id.circlehs);
        if (z2) {
            if (SharePreferencesUtils.readBooleanConfig(CommonParameter.JINGYAN_LIST_HELP, true, this.mContext)) {
                showGrade_jingyan();
            }
            this.handler.sendEmptyMessage(0);
        }
        this.iv_network_load_fail.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.u5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16035c.lambda$initViews$0(view);
            }
        });
        this.ll_more_columns.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.v5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16063c.lambda$initViews$1(view);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this.mContext).unregisterReceiver(this.loadDataReceiver);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(final String str) {
        if (str.equals("isIJINGYANLogin")) {
            this.handler.sendEmptyMessage(0);
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.JINGYAN_LIST_HELP, true, this.mContext)) {
            showGrade_jingyan();
        }
        this.handler.sendEmptyMessage(0);
    }

    public void onEventMainThread(EveHandoutBean mChannelBean) {
        if (!mChannelBean.getmEveStr().equals("handout") || mChannelBean.getUserChannelList().size() <= 0) {
            return;
        }
        this._default.clear();
        this._list.clear();
        this._default = mChannelBean.getUserChannelList();
        this._list = mChannelBean.getOtherChannelList();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this._default.size(); i2++) {
            Bundle bundle = new Bundle();
            bundle.putInt("id", Integer.parseInt(this._default.get(i2).getId()));
            bundle.putString("text", this._default.get(i2).getTitle());
            arrayList.add(new BaseViewPagerAdapter.PagerInfo(this._default.get(i2).getTitle(), NewsFragment.class, bundle));
        }
        this.mBaseView.setPageInfo(arrayList);
        this.mCommonNavigator.notifyDataSetChanged();
        this.mBaseView.notifyDataSetChanged();
    }
}
