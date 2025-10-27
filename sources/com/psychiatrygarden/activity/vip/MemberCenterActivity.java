package com.psychiatrygarden.activity.vip;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.lxj.xpopup.XPopup;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.WebLongSaveActivity;
import com.psychiatrygarden.activity.forum.GiveLogActivity;
import com.psychiatrygarden.activity.mine.order.OrderConfirmActivity;
import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import com.psychiatrygarden.activity.vip.bean.VipIntroductionBean;
import com.psychiatrygarden.activity.vip.fragment.SvipCreateFragment;
import com.psychiatrygarden.activity.vip.fragment.VipCreateFragment;
import com.psychiatrygarden.activity.vip.pop.GoBuyVipPopwindow;
import com.psychiatrygarden.activity.vip.pop.RulePopwindow;
import com.psychiatrygarden.activity.vip.pop.ToSvipPopWindow;
import com.psychiatrygarden.activity.vip.pop.ValidPopwondow;
import com.psychiatrygarden.bean.HomepageSmallAdBean;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.psychiatrygarden.bean.OrderConfirmParams;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.event.BuyVipSuccessEvent;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.PopupShowManager;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.AdMainHomeDialog;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class MemberCenterActivity extends BaseActivity {
    public BaseViewPagerAdapter adapter;
    public AppBarLayout appbarlayout;
    public ImageView backimg;
    public LinearLayout btnlineview;
    public LinearLayout btview;
    public TextView buttomviewtv;
    public ViewPager cyidview;
    public TextView datatime;
    public TextView dsvipsaletv;
    public TextView dvipsaletv;
    public ImageView guize;
    public CircleImageView headers;
    public ImageView hyguize;
    public TextView kefu3;
    public LinearLayout line;
    private TextView mBtnMemberRule;
    public RelativeLayout rellogview;
    public TextView sjantv;
    public RelativeLayout sviprel;
    public TextView titleid;
    public Toolbar toobars1;
    public RelativeLayout toolbars;
    public RelativeLayout viprel;
    public TextView xfantv;
    public List<BaseViewPagerAdapter.PagerInfo> listview = new ArrayList();
    public int vipGoodsPosition = 0;
    public int svipGoodsposition = 0;
    public int is_Svip_valid = 0;
    int psotision = 0;
    private String fromDailyTask = "0";
    public String upgrade_type = "";
    public String deduction_id = "";
    public String goods_id = "";
    public String price = "";
    public String quantity = "1";
    public String tag = "vip";
    public String upVipTxt = "";
    private String is_promotion = "";
    public List<GoodsBean.DataBean.MealBean> rMealBeans = new ArrayList();

    /* renamed from: com.psychiatrygarden.activity.vip.MemberCenterActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends AjaxCallBack<String> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(AdMainHomeDialog adMainHomeDialog) {
            adMainHomeDialog.show(MemberCenterActivity.this.getSupportFragmentManager(), "MemberCenterActivity");
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass1) s2);
            try {
                JSONObject jSONObject = new JSONObject(s2);
                HomepageSmallAdBean.DataDTO.DataAd dataAd = (HomepageSmallAdBean.DataDTO.DataAd) new Gson().fromJson(jSONObject.optString("data"), HomepageSmallAdBean.DataDTO.DataAd.class);
                if (dataAd == null || dataAd.getAds().size() <= 0) {
                    return;
                }
                final AdMainHomeDialog adMainHomeDialogNewInstance = AdMainHomeDialog.newInstance("" + jSONObject.optString("data"));
                MemberCenterActivity.this.getSupportFragmentManager().executePendingTransactions();
                if (adMainHomeDialogNewInstance.isAdded() || adMainHomeDialogNewInstance.isRemoving() || adMainHomeDialogNewInstance.isVisible()) {
                    return;
                }
                new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.vip.k
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f14098c.lambda$onSuccess$0(adMainHomeDialogNewInstance);
                    }
                }, 500L);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.vip.MemberCenterActivity$2, reason: invalid class name */
    public class AnonymousClass2 extends AjaxCallBack<String> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(VipIntroductionBean vipIntroductionBean, View view) {
            OnlineServiceBean onlineServiceBean = new OnlineServiceBean();
            onlineServiceBean.setCs_type("wechat_enterprise_personal");
            onlineServiceBean.setWechat_corpid(vipIntroductionBean.getData().getWechat_corpid());
            onlineServiceBean.setContact(vipIntroductionBean.getData().getWechat_enterprise_url());
            CommonUtil.onlineService(MemberCenterActivity.this, onlineServiceBean);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(VipIntroductionBean vipIntroductionBean, View view) {
            new XPopup.Builder(MemberCenterActivity.this).asCustom(new RulePopwindow(MemberCenterActivity.this, vipIntroductionBean.getData().getRule() + "")).show();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        /* JADX WARN: Type inference failed for: r0v18, types: [boolean, int] */
        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String str) {
            super.onSuccess((AnonymousClass2) str);
            try {
                final VipIntroductionBean vipIntroductionBean = (VipIntroductionBean) new Gson().fromJson(str, VipIntroductionBean.class);
                MemberCenterActivity.this.listview.clear();
                if (vipIntroductionBean.getCode() == 200) {
                    if (vipIntroductionBean.getData().getVip() != null && vipIntroductionBean.getData().getVip().getIs_vip() == 1) {
                        UserConfig.getInstance().getUser().setIs_vip("1");
                    }
                    if (vipIntroductionBean.getData().getSvip() != null && vipIntroductionBean.getData().getSvip().getIs_vip() == 1) {
                        UserConfig.getInstance().getUser().setIs_vip("1");
                    }
                    MemberCenterActivity.this.kefu3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.l
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f14100c.lambda$onSuccess$0(vipIntroductionBean, view);
                        }
                    });
                    MemberCenterActivity.this.hyguize.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.m
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f14102c.lambda$onSuccess$1(vipIntroductionBean, view);
                        }
                    });
                    GlideApp.with((FragmentActivity) MemberCenterActivity.this).load((Object) GlideUtils.generateUrl(UserConfig.getInstance().getUser().getAvatar())).into(MemberCenterActivity.this.headers);
                    ?? Equals = "1".equals(vipIntroductionBean.getData().getVip().getAvailable());
                    int i2 = Equals;
                    if ("1".equals(vipIntroductionBean.getData().getSvip().getAvailable())) {
                        i2 = Equals + 1;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("vipdata", vipIntroductionBean);
                    bundle.putInt("typew", i2);
                    if ("1".equals(vipIntroductionBean.getData().getVip().getAvailable())) {
                        MemberCenterActivity.this.listview.add(new BaseViewPagerAdapter.PagerInfo("vip", VipCreateFragment.class, bundle));
                    }
                    if ("1".equals(vipIntroductionBean.getData().getSvip().getAvailable())) {
                        MemberCenterActivity.this.listview.add(new BaseViewPagerAdapter.PagerInfo("svip", SvipCreateFragment.class, bundle));
                    }
                    MemberCenterActivity.this.upVipTxt = vipIntroductionBean.getData().getDiscount_description();
                    if (MemberCenterActivity.this.listview.size() > 1) {
                        MemberCenterActivity.this.line.setVisibility(0);
                        MemberCenterActivity.this.titleid.setText(UserConfig.getInstance().getUser().getNickname());
                        if (TextUtils.isEmpty(vipIntroductionBean.getData().getVip().getSimple_declaration())) {
                            MemberCenterActivity.this.datatime.setVisibility(8);
                        } else {
                            MemberCenterActivity.this.datatime.setText(vipIntroductionBean.getData().getVip().getSimple_declaration());
                            MemberCenterActivity.this.datatime.setVisibility(0);
                        }
                        if (1 == vipIntroductionBean.getData().getVip().getIs_vip()) {
                            MemberCenterActivity memberCenterActivity = MemberCenterActivity.this;
                            CommonUtil.mDoDrawable(memberCenterActivity, memberCenterActivity.titleid, R.drawable.vip_icon, 2);
                        }
                        if (1 == vipIntroductionBean.getData().getSvip().getIs_vip()) {
                            MemberCenterActivity memberCenterActivity2 = MemberCenterActivity.this;
                            CommonUtil.mDoDrawable(memberCenterActivity2, memberCenterActivity2.titleid, R.drawable.svip100_icon, 2);
                        }
                        MemberCenterActivity.this.is_Svip_valid = vipIntroductionBean.getData().getSvip().getIs_vip();
                        if (TextUtils.isEmpty(vipIntroductionBean.getData().getVip().getLabel())) {
                            MemberCenterActivity.this.dvipsaletv.setVisibility(8);
                        } else {
                            MemberCenterActivity.this.dvipsaletv.setText(vipIntroductionBean.getData().getVip().getLabel());
                            MemberCenterActivity.this.dvipsaletv.setVisibility(0);
                        }
                        if (TextUtils.isEmpty(vipIntroductionBean.getData().getSvip().getLabel())) {
                            MemberCenterActivity.this.dsvipsaletv.setVisibility(8);
                        } else {
                            MemberCenterActivity.this.dsvipsaletv.setText(vipIntroductionBean.getData().getSvip().getLabel());
                            MemberCenterActivity.this.dsvipsaletv.setVisibility(0);
                        }
                        MemberCenterActivity.this.tag = "vip";
                        List<VipIntroductionBean.DataDTO.VipDTO.GoodsDTO> goods = vipIntroductionBean.getData().getVip().getGoods();
                        if (goods != null && !goods.isEmpty()) {
                            MemberCenterActivity.this.showBtnData(vipIntroductionBean.getData().getVip().getGoods().get(0));
                        }
                        MemberCenterActivity.this.appbarlayout.setSelected(false);
                    } else {
                        MemberCenterActivity.this.line.setVisibility(8);
                        if ("1".equals(vipIntroductionBean.getData().getVip().getAvailable())) {
                            MemberCenterActivity.this.titleid.setText(UserConfig.getInstance().getUser().getNickname());
                            if (TextUtils.isEmpty(vipIntroductionBean.getData().getVip().getSimple_declaration())) {
                                MemberCenterActivity.this.datatime.setVisibility(8);
                            } else {
                                MemberCenterActivity.this.datatime.setText(vipIntroductionBean.getData().getVip().getSimple_declaration());
                                MemberCenterActivity.this.datatime.setVisibility(0);
                            }
                            if (1 == vipIntroductionBean.getData().getVip().getIs_vip()) {
                                MemberCenterActivity memberCenterActivity3 = MemberCenterActivity.this;
                                CommonUtil.mDoDrawable(memberCenterActivity3, memberCenterActivity3.titleid, R.drawable.vip_icon, 2);
                            }
                            MemberCenterActivity.this.tag = "vip";
                            List<VipIntroductionBean.DataDTO.VipDTO.GoodsDTO> goods2 = vipIntroductionBean.getData().getVip().getGoods();
                            if (goods2 != null && !goods2.isEmpty()) {
                                MemberCenterActivity.this.showBtnData(vipIntroductionBean.getData().getVip().getGoods().get(0));
                            }
                            MemberCenterActivity.this.appbarlayout.setSelected(false);
                        }
                        if ("1".equals(vipIntroductionBean.getData().getSvip().getAvailable())) {
                            MemberCenterActivity.this.titleid.setText(UserConfig.getInstance().getUser().getNickname());
                            if (TextUtils.isEmpty(vipIntroductionBean.getData().getSvip().getSimple_declaration())) {
                                MemberCenterActivity.this.datatime.setVisibility(8);
                            } else {
                                MemberCenterActivity.this.datatime.setText(vipIntroductionBean.getData().getSvip().getSimple_declaration());
                                MemberCenterActivity.this.datatime.setVisibility(0);
                            }
                            if (1 == vipIntroductionBean.getData().getSvip().getIs_vip()) {
                                MemberCenterActivity memberCenterActivity4 = MemberCenterActivity.this;
                                CommonUtil.mDoDrawable(memberCenterActivity4, memberCenterActivity4.titleid, R.drawable.svip100_icon, 2);
                            }
                            MemberCenterActivity.this.is_Svip_valid = vipIntroductionBean.getData().getSvip().getIs_vip();
                            MemberCenterActivity.this.tag = "svip";
                            List<VipIntroductionBean.DataDTO.VipDTO.GoodsDTO> goods3 = vipIntroductionBean.getData().getSvip().getGoods();
                            if (goods3 != null && !goods3.isEmpty()) {
                                MemberCenterActivity.this.showBtnData(vipIntroductionBean.getData().getSvip().getGoods().get(0));
                            }
                            MemberCenterActivity.this.appbarlayout.setSelected(true);
                        }
                    }
                    MemberCenterActivity memberCenterActivity5 = MemberCenterActivity.this;
                    memberCenterActivity5.adapter = new BaseViewPagerAdapter(memberCenterActivity5, memberCenterActivity5.getSupportFragmentManager(), MemberCenterActivity.this.listview);
                    MemberCenterActivity memberCenterActivity6 = MemberCenterActivity.this;
                    memberCenterActivity6.cyidview.setAdapter(memberCenterActivity6.adapter);
                    MemberCenterActivity.this.cyidview.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.vip.MemberCenterActivity.2.1
                        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                        public void onPageScrollStateChanged(int state) {
                        }

                        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        }

                        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                        public void onPageSelected(int position) throws IllegalAccessException, NoSuchFieldException, Resources.NotFoundException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
                            if (MemberCenterActivity.this.listview.size() > 1) {
                                if (1 == vipIntroductionBean.getData().getVip().getIs_vip()) {
                                    MemberCenterActivity memberCenterActivity7 = MemberCenterActivity.this;
                                    CommonUtil.mDoDrawable(memberCenterActivity7, memberCenterActivity7.titleid, R.drawable.vip_icon, 2);
                                }
                                if (1 == vipIntroductionBean.getData().getSvip().getIs_vip()) {
                                    MemberCenterActivity memberCenterActivity8 = MemberCenterActivity.this;
                                    CommonUtil.mDoDrawable(memberCenterActivity8, memberCenterActivity8.titleid, R.drawable.svip100_icon, 2);
                                }
                                if (position == 0) {
                                    StatusBarCompat.setLightStatusBar(MemberCenterActivity.this, true);
                                    if (TextUtils.isEmpty(vipIntroductionBean.getData().getVip().getSimple_declaration())) {
                                        MemberCenterActivity.this.datatime.setVisibility(8);
                                    } else {
                                        MemberCenterActivity.this.datatime.setText(vipIntroductionBean.getData().getVip().getSimple_declaration());
                                        MemberCenterActivity.this.datatime.setVisibility(0);
                                    }
                                    MemberCenterActivity.this.tag = "vip";
                                    if (vipIntroductionBean.getData().getVip().getGoods().size() > 0) {
                                        MemberCenterActivity.this.showBtnData(vipIntroductionBean.getData().getVip().getGoods().get(MemberCenterActivity.this.getVipGoodsPosition()));
                                    }
                                } else {
                                    StatusBarCompat.setLightStatusBar(MemberCenterActivity.this, false);
                                    if (TextUtils.isEmpty(vipIntroductionBean.getData().getSvip().getSimple_declaration())) {
                                        MemberCenterActivity.this.datatime.setVisibility(8);
                                    } else {
                                        MemberCenterActivity.this.datatime.setText(vipIntroductionBean.getData().getSvip().getSimple_declaration());
                                        MemberCenterActivity.this.datatime.setVisibility(0);
                                    }
                                    MemberCenterActivity.this.tag = "svip";
                                    if (vipIntroductionBean.getData().getSvip().getGoods().size() > 0) {
                                        MemberCenterActivity.this.showBtnData(vipIntroductionBean.getData().getSvip().getGoods().get(MemberCenterActivity.this.getSvipGoodsposition()));
                                    }
                                }
                                MemberCenterActivity.this.appbarlayout.setSelected(position != 0);
                                return;
                            }
                            if ("1".equals(vipIntroductionBean.getData().getVip().getAvailable())) {
                                if (TextUtils.isEmpty(vipIntroductionBean.getData().getVip().getSimple_declaration())) {
                                    MemberCenterActivity.this.datatime.setVisibility(8);
                                } else {
                                    MemberCenterActivity.this.datatime.setText(vipIntroductionBean.getData().getVip().getSimple_declaration());
                                    MemberCenterActivity.this.datatime.setVisibility(0);
                                }
                                if (1 == vipIntroductionBean.getData().getVip().getIs_vip()) {
                                    MemberCenterActivity memberCenterActivity9 = MemberCenterActivity.this;
                                    CommonUtil.mDoDrawable(memberCenterActivity9, memberCenterActivity9.titleid, R.drawable.vip_icon, 2);
                                }
                                MemberCenterActivity.this.tag = "vip";
                                if (vipIntroductionBean.getData().getVip().getGoods().size() > 0) {
                                    MemberCenterActivity.this.showBtnData(vipIntroductionBean.getData().getVip().getGoods().get(MemberCenterActivity.this.getVipGoodsPosition()));
                                }
                                MemberCenterActivity.this.appbarlayout.setSelected(false);
                            }
                            if ("1".equals(vipIntroductionBean.getData().getSvip().getAvailable())) {
                                if (TextUtils.isEmpty(vipIntroductionBean.getData().getSvip().getSimple_declaration())) {
                                    MemberCenterActivity.this.datatime.setVisibility(8);
                                } else {
                                    MemberCenterActivity.this.datatime.setText(vipIntroductionBean.getData().getSvip().getSimple_declaration());
                                    MemberCenterActivity.this.datatime.setVisibility(0);
                                }
                                if (1 == vipIntroductionBean.getData().getSvip().getIs_vip()) {
                                    MemberCenterActivity memberCenterActivity10 = MemberCenterActivity.this;
                                    CommonUtil.mDoDrawable(memberCenterActivity10, memberCenterActivity10.titleid, R.drawable.svip100_icon, 2);
                                }
                                MemberCenterActivity.this.tag = "svip";
                                if (vipIntroductionBean.getData().getSvip().getGoods().size() > 0) {
                                    MemberCenterActivity.this.showBtnData(vipIntroductionBean.getData().getSvip().getGoods().get(MemberCenterActivity.this.getSvipGoodsposition()));
                                }
                                MemberCenterActivity.this.appbarlayout.setSelected(true);
                            }
                        }
                    });
                    if (MemberCenterActivity.this.listview.size() > 1) {
                        MemberCenterActivity memberCenterActivity7 = MemberCenterActivity.this;
                        memberCenterActivity7.cyidview.setCurrentItem(memberCenterActivity7.psotision);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        if ("1".equals(this.fromDailyTask)) {
            NavigationUtilKt.gotoStudyPlanListActivity(this);
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) throws Resources.NotFoundException {
        this.appbarlayout.setSelected(false);
        this.cyidview.setCurrentItem(0);
        ((TextView) findViewById(R.id.dvipimgtv)).setTextColor(Color.parseColor("#DBAB55"));
        ((TextView) findViewById(R.id.dsvipimgtv)).setTextColor(Color.parseColor("#DBAB55"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) throws Resources.NotFoundException {
        this.appbarlayout.setSelected(true);
        this.cyidview.setCurrentItem(1);
        ((TextView) findViewById(R.id.dvipimgtv)).setTextColor(Color.parseColor("#868DA6"));
        ((TextView) findViewById(R.id.dsvipimgtv)).setTextColor(Color.parseColor("#3C4154"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(AppBarLayout appBarLayout, int i2) {
        float f2 = i2 * 1.0f;
        this.rellogview.setAlpha(1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()));
        if (1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()) == 0.0f) {
            this.rellogview.setVisibility(8);
        } else {
            this.rellogview.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        startActivity(new Intent(this, (Class<?>) GiveLogActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(View view) {
        Intent intent = new Intent(ProjectApp.instance(), (Class<?>) WebLongSaveActivity.class);
        intent.putExtra("title", "会员服务协议");
        intent.putExtra("web_url", NetworkRequestsURL.memberServiceRule);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBtnData$6(VipIntroductionBean.DataDTO.VipDTO.GoodsDTO goodsDTO, int i2) {
        if (i2 == 0) {
            startActivity(new Intent(this.mContext, (Class<?>) ActCourseOrGoodsDetail.class).putExtra("detailType", 2).putExtra("goods_id", goodsDTO.getRecommend().getGoods_id()));
        } else if (i2 == 1) {
            if (goodsDTO.getButton().get(0).getValid() == 1) {
                CombMethod();
            } else {
                ToastUtil.shortToast(this, "此商品无法购买！");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBtnData$7(final VipIntroductionBean.DataDTO.VipDTO.GoodsDTO goodsDTO, View view) {
        try {
            if (goodsDTO.getButton().get(0).getValid() != 1) {
                ToastUtil.shortToast(this, "此商品无法购买！");
                return;
            }
            this.upgrade_type = goodsDTO.getButton().get(0).getUpgrade_type() + "";
            this.deduction_id = goodsDTO.getButton().get(0).getDeduction_id() + "";
            if (goodsDTO.getRecommend().getGoods_id() != null && !"0".equals(goodsDTO.getRecommend().getGoods_id())) {
                new XPopup.Builder(this).asCustom(new GoBuyVipPopwindow(this, new GoBuyVipPopwindow.GoBuyClickIml() { // from class: com.psychiatrygarden.activity.vip.a
                    @Override // com.psychiatrygarden.activity.vip.pop.GoBuyVipPopwindow.GoBuyClickIml
                    public final void mGoBuyClick(int i2) {
                        this.f14039a.lambda$showBtnData$6(goodsDTO, i2);
                    }
                }, goodsDTO.getRecommend().getGoods_name(), goodsDTO.getRecommend().getGoods_description(), goodsDTO.getRecommend().getLabel(), this.tag)).show();
            } else if (goodsDTO.getButton().get(0).getValid() == 1) {
                CombMethod();
            } else {
                ToastUtil.shortToast(this, "此商品无法购买！");
            }
        } catch (Exception e2) {
            ToastUtil.shortToast(this, "数据格式存在问题！");
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBtnData$8(VipIntroductionBean.DataDTO.VipDTO.GoodsDTO goodsDTO, View view) {
        try {
            if (goodsDTO.getButton().get(1).getValid() == 1) {
                this.upgrade_type = goodsDTO.getButton().get(1).getUpgrade_type() + "";
                this.deduction_id = goodsDTO.getButton().get(1).getDeduction_id() + "";
                new XPopup.Builder(this).asCustom(new ToSvipPopWindow(this, this.upVipTxt, new ToSvipPopWindow.ToSvipClickIml() { // from class: com.psychiatrygarden.activity.vip.MemberCenterActivity.3
                    @Override // com.psychiatrygarden.activity.vip.pop.ToSvipPopWindow.ToSvipClickIml
                    public void mToSvipClick() {
                        MemberCenterActivity.this.CombMethod();
                    }
                })).show();
            } else {
                new XPopup.Builder(this).asCustom(new ValidPopwondow(this)).show();
            }
        } catch (Exception e2) {
            ToastUtil.shortToast(this, "数据格式存在问题！");
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBtnData$9(final VipIntroductionBean.DataDTO.VipDTO.GoodsDTO goodsDTO, View view) {
        try {
            this.upgrade_type = goodsDTO.getButton().get(0).getUpgrade_type() + "";
            this.deduction_id = goodsDTO.getButton().get(0).getDeduction_id() + "";
            if (goodsDTO.getRecommend().getGoods_id() != null && !"0".equals(goodsDTO.getRecommend().getGoods_id())) {
                new XPopup.Builder(this).asCustom(new GoBuyVipPopwindow(this, new GoBuyVipPopwindow.GoBuyClickIml() { // from class: com.psychiatrygarden.activity.vip.MemberCenterActivity.4
                    @Override // com.psychiatrygarden.activity.vip.pop.GoBuyVipPopwindow.GoBuyClickIml
                    public void mGoBuyClick(int type) {
                        if (type == 0) {
                            MemberCenterActivity.this.startActivity(new Intent(MemberCenterActivity.this.mContext, (Class<?>) ActCourseOrGoodsDetail.class).putExtra("detailType", 2).putExtra("goods_id", goodsDTO.getRecommend().getGoods_id()));
                        } else if (type == 1) {
                            if (goodsDTO.getButton().get(0).getValid() == 1) {
                                MemberCenterActivity.this.CombMethod();
                            } else {
                                ToastUtil.shortToast(MemberCenterActivity.this, "此商品无法购买！");
                            }
                        }
                    }
                }, goodsDTO.getRecommend().getGoods_name(), goodsDTO.getRecommend().getGoods_description(), goodsDTO.getRecommend().getLabel(), this.tag)).show();
            } else if (goodsDTO.getButton().get(0).getValid() == 1) {
                CombMethod();
            } else {
                ToastUtil.shortToast(this, "此商品无法购买！");
            }
        } catch (Exception e2) {
            ToastUtil.shortToast(this, "数据格式存在问题！");
            e2.printStackTrace();
        }
    }

    public void CombMethod() {
        OrderConfirmParams orderConfirmParams = new OrderConfirmParams();
        orderConfirmParams.setGoods_id(this.goods_id).setPrice(this.price).setUpgrade_type(this.upgrade_type).setDeduction_id(this.deduction_id).setGoodType("1").setIs_promotion("1".equals(this.is_promotion));
        OrderConfirmActivity.goToOrderConfirmEntrance(this, orderConfirmParams);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void dismissCouponPop() {
        super.dismissCouponPop();
        getWindowShow();
    }

    public void getPopTips() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "notice_for_rules");
        YJYHttpUtils.get(this, NetworkRequestsURL.vipnoticeApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.vip.MemberCenterActivity.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        if ("1".equals(jSONObject.optJSONObject("data").optString("display"))) {
                            MemberCenterActivity.this.guize.setVisibility(0);
                        } else {
                            MemberCenterActivity.this.guize.setVisibility(8);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public int getSvipGoodsposition() {
        return this.svipGoodsposition;
    }

    public void getVipData() {
        YJYHttpUtils.get(this, NetworkRequestsURL.buyInfoV2Api, new AjaxParams(), new AnonymousClass2());
    }

    public int getVipGoodsPosition() {
        return this.vipGoodsPosition;
    }

    public void getWindowShow() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.vipNoticeApi, new AjaxParams(), new AnonymousClass1());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.psotision = getIntent().getIntExtra("psotision", 0);
        this.fromDailyTask = getIntent().getStringExtra("fromDailyTask");
        this.guize = (ImageView) findViewById(R.id.guize);
        this.hyguize = (ImageView) findViewById(R.id.hyguize);
        this.kefu3 = (TextView) findViewById(R.id.kefu3);
        this.btnlineview = (LinearLayout) findViewById(R.id.btnlineview);
        this.xfantv = (TextView) findViewById(R.id.xfantv);
        this.sjantv = (TextView) findViewById(R.id.sjantv);
        this.btview = (LinearLayout) findViewById(R.id.btview);
        this.buttomviewtv = (TextView) findViewById(R.id.buttomviewtv);
        ImageView imageView = (ImageView) findViewById(R.id.backimg);
        this.backimg = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14041c.lambda$init$0(view);
            }
        });
        this.dvipsaletv = (TextView) findViewById(R.id.dvipsaletv);
        this.dsvipsaletv = (TextView) findViewById(R.id.dsvipsaletv);
        this.headers = (CircleImageView) findViewById(R.id.headers);
        this.titleid = (TextView) findViewById(R.id.titleid);
        this.datatime = (TextView) findViewById(R.id.datatime);
        this.viprel = (RelativeLayout) findViewById(R.id.viprel);
        this.sviprel = (RelativeLayout) findViewById(R.id.sviprel);
        this.rellogview = (RelativeLayout) findViewById(R.id.rellogview);
        this.appbarlayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        this.line = (LinearLayout) findViewById(R.id.line);
        this.toobars1 = (Toolbar) findViewById(R.id.toobars1);
        this.toolbars = (RelativeLayout) findViewById(R.id.toolbars);
        this.appbarlayout.setSelected(false);
        this.cyidview = (ViewPager) findViewById(R.id.cyidview);
        this.mBtnMemberRule = (TextView) findViewById(R.id.btn_member_rule);
        this.viprel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                this.f14042c.lambda$init$1(view);
            }
        });
        this.sviprel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                this.f14043c.lambda$init$2(view);
            }
        });
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(this.toobars1.getLayoutParams());
        layoutParams.setMargins(0, statusBarHeight, 0, 0);
        layoutParams.setCollapseMode(1);
        this.toobars1.setLayoutParams(layoutParams);
        this.appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.vip.e
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                this.f14044a.lambda$init$3(appBarLayout, i2);
            }
        });
        this.guize.setVisibility(8);
        this.guize.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14045c.lambda$init$4(view);
            }
        });
        this.mBtnMemberRule.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14091c.lambda$init$5(view);
            }
        });
        getPopTips();
        getVipData();
        String[] timeFromInt = CommonUtil.getTimeFromInt(1746670519L);
        LogUtils.e("current_time", timeFromInt[0] + ":" + timeFromInt[1] + ":" + timeFromInt[2] + ":" + timeFromInt[3] + ";转换的结果=" + CommonUtil.getDateByTimes("1746670519"));
        PopupShowManager.getInstance(this).checkShowCoupon(this, "ENTER_MEMBER_CENTER", "2", "3", null);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        this.mBaseTheme = SkinManager.getCurrentSkinType(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
        getWindow().getDecorView().setSystemUiVisibility(R2.drawable.ddbq);
        getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        if ("1".equals(this.fromDailyTask)) {
            NavigationUtilKt.gotoStudyPlanListActivity(this);
        }
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
        if ("BuySuccess".equals(str)) {
            EventBus.getDefault().post(new BuyVipSuccessEvent(true));
            getVipData();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_video);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void setSvipGoodsposition(int svipGoodsposition) {
        this.svipGoodsposition = svipGoodsposition;
    }

    public void setVipGoodsPosition(int vipGoodsPosition) {
        this.vipGoodsPosition = vipGoodsPosition;
    }

    public void showBtnData(final VipIntroductionBean.DataDTO.VipDTO.GoodsDTO item) {
        if (this.tag.equals("vip")) {
            this.buttomviewtv.setBackgroundResource(R.drawable.shape_line_rote_bg);
            this.buttomviewtv.setTextColor(Color.parseColor("#000000"));
        } else {
            this.buttomviewtv.setBackgroundResource(R.drawable.dot_shape_round_red_vip);
            this.buttomviewtv.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
        }
        this.goods_id = "" + item.getGoods_id();
        this.price = "" + item.getCent_price();
        this.quantity = "1";
        this.is_promotion = item.getIs_promotion();
        this.btnlineview.setVisibility(item.getButton().size() > 1 ? 0 : 8);
        if (item.getButton().size() <= 1) {
            if (item.getButton().size() == 1) {
                this.btnlineview.setVisibility(8);
                this.buttomviewtv.setVisibility(0);
                this.buttomviewtv.setText(item.getButton().get(0).getLabel());
                this.buttomviewtv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.j
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f14096c.lambda$showBtnData$9(item, view);
                    }
                });
                return;
            }
            return;
        }
        this.buttomviewtv.setVisibility(8);
        this.xfantv.setText(item.getButton().get(0).getLabel());
        this.sjantv.setText(item.getButton().get(1).getLabel());
        if (item.getButton().get(1).getValid() == 1) {
            this.sjantv.setBackgroundResource(R.drawable.subtract2);
        } else {
            this.sjantv.setBackgroundResource(R.drawable.subtract32);
        }
        this.xfantv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14092c.lambda$showBtnData$7(item, view);
            }
        });
        this.sjantv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14094c.lambda$showBtnData$8(item, view);
            }
        });
    }
}
