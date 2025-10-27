package com.psychiatrygarden.activity.purchase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.MyCustomerServiceActivity;
import com.psychiatrygarden.activity.ViewPagerActivity;
import com.psychiatrygarden.activity.purchase.activity.PayGoodsNewActivity;
import com.psychiatrygarden.activity.purchase.activity.QuenRenDingDanNewActivity;
import com.psychiatrygarden.activity.purchase.activity.VideoRePlayActivity;
import com.psychiatrygarden.activity.purchase.beans.CommodityEvaluationBean;
import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import com.psychiatrygarden.activity.purchase.beans.SaleGoodsBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.gradview.NineGridTestLayout;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.FlowLayout;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.TagAdapter;
import com.psychiatrygarden.utils.TagFlowLayout;
import com.psychiatrygarden.utils.UILoader;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class GoodsFragment extends BaseFragment implements ViewPager.OnPageChangeListener, OnPageClickListener {
    TextView all_say_tv;
    private String buy_permission;
    TextView cat_all_tv;
    private String flag;
    TextView goumai_tv;
    TextView goumai_xuni;
    public TextView gouwuche;
    ImageView img_imgview;
    private InfiniteIndicator infinite_anim_circle;
    public LinearLayout line_content;
    public LinearLayout linear_pinglun;
    public LinearLayout list_linear;
    LinearLayout llay_shiti;
    private GoodsBean.DataBean.MealBean mGoodsMeanBean;
    GoodsBean mgBeans;
    public TextView no_content;
    private List<String> pageListImgs;
    private ArrayList<Page> pageViews;
    PopupWindow popupWindow_filtrate;
    RelativeLayout relativeLayout1;
    RelativeLayout rl_dingwei;
    ScrollView scrollView2;
    TagFlowLayout tagView;
    public TextView textView3;
    TextView tv_all_haoping;
    public TextView tv_content;
    TextView tv_count;
    TextView tv_guanbi;
    TextView tv_in;
    TextView tv_jia;
    TextView tv_jian;
    public TextView tv_joincart;
    TextView tv_m;
    public TextView tv_money;
    public TextView tv_money1;
    TextView tv_num;
    TextView tv_order;
    TextView tv_orders;
    TextView tv_selector;
    TextView tv_selector2;
    public TextView tv_title;
    TextView tv_youfei;
    TextView tv_zhekoujia;
    View view5;
    private View view_pop_bg;
    List<GoodsBean.DataBean.MealBean> meal = new ArrayList();
    private String goods_id = "";
    int tv_num_goumai = 1;
    private String is_real = "";
    private int inventoryCount = 0;
    private final List<GoodsBean.DataBean.MealBean> mGoodsMeanBeans = new ArrayList();

    private void getGoodsDetile() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("goods_id", this.goods_id);
        YJYHttpUtils.getgoodsmd5(getActivity(), NetworkRequestsURL.goodsDetailURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.GoodsFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                try {
                    GoodsFragment.this.mgBeans = (GoodsBean) new Gson().fromJson(t2, GoodsBean.class);
                    if (GoodsFragment.this.mgBeans.getCode().equals("200")) {
                        GoodsFragment goodsFragment = GoodsFragment.this;
                        goodsFragment.tv_title.setText(goodsFragment.mgBeans.getData().getGoods_name());
                        GoodsFragment goodsFragment2 = GoodsFragment.this;
                        goodsFragment2.tv_content.setText(goodsFragment2.mgBeans.getData().getDescription());
                        GoodsFragment goodsFragment3 = GoodsFragment.this;
                        goodsFragment3.tv_money1.setText(goodsFragment3.mgBeans.getData().getNow_price());
                        GoodsFragment goodsFragment4 = GoodsFragment.this;
                        goodsFragment4.tv_zhekoujia.setText(goodsFragment4.mgBeans.getData().getOriginal_price());
                        GoodsFragment.this.tv_zhekoujia.getPaint().setFlags(16);
                        GoodsFragment goodsFragment5 = GoodsFragment.this;
                        goodsFragment5.tv_youfei.setText(goodsFragment5.mgBeans.getData().getSales_volume());
                        GoodsFragment goodsFragment6 = GoodsFragment.this;
                        goodsFragment6.is_real = goodsFragment6.mgBeans.getData().getGoods_type();
                        GoodsFragment goodsFragment7 = GoodsFragment.this;
                        goodsFragment7.meal = goodsFragment7.mgBeans.getData().getMeal();
                        if (GoodsFragment.this.meal.size() > 0) {
                            GoodsFragment.this.no_content.setVisibility(0);
                            GoodsFragment goodsFragment8 = GoodsFragment.this;
                            goodsFragment8.no_content.setText(String.format("选择    %s", goodsFragment8.mgBeans.getData().getStatement()));
                            GoodsFragment.this.view5.setVisibility(0);
                        } else {
                            GoodsFragment.this.no_content.setVisibility(8);
                            GoodsFragment.this.view5.setVisibility(8);
                        }
                        GoodsFragment goodsFragment9 = GoodsFragment.this;
                        goodsFragment9.tagView.setAdapter(new TagAdapter<String>(goodsFragment9.mgBeans.getData().getLabel()) { // from class: com.psychiatrygarden.activity.purchase.GoodsFragment.2.1
                            @Override // com.psychiatrygarden.utils.TagAdapter
                            public View getView(FlowLayout parent, int position, String mealBeans) {
                                TextView textView = (TextView) LayoutInflater.from(GoodsFragment.this.getActivity()).inflate(R.layout.tag_txt, (ViewGroup) GoodsFragment.this.tagView, false);
                                textView.setText(mealBeans);
                                return textView;
                            }
                        });
                        if (GoodsFragment.this.mgBeans.getData().getGoods_img() != null) {
                            for (int i2 = 0; i2 < GoodsFragment.this.mgBeans.getData().getGoods_img().size(); i2++) {
                                try {
                                    if (TextUtils.equals(GoodsFragment.this.mgBeans.getData().getGoods_video().getImg(), GoodsFragment.this.mgBeans.getData().getGoods_img().get(i2))) {
                                        GoodsFragment.this.pageViews.add(new Page("video", GoodsFragment.this.mgBeans.getData().getGoods_img().get(i2)));
                                    } else {
                                        GoodsFragment.this.pageViews.add(new Page("img", GoodsFragment.this.mgBeans.getData().getGoods_img().get(i2)));
                                    }
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                GoodsFragment.this.pageListImgs.add(GoodsFragment.this.mgBeans.getData().getGoods_img().get(i2));
                            }
                        }
                        GoodsFragment.this.infinite_anim_circle.notifyDataChange(GoodsFragment.this.pageViews);
                        GoodsFragment goodsFragment10 = GoodsFragment.this;
                        goodsFragment10.inventoryCount = Integer.parseInt(goodsFragment10.mgBeans.getData().getInventory());
                        GoodsFragment goodsFragment11 = GoodsFragment.this;
                        goodsFragment11.buy_permission = goodsFragment11.mgBeans.getData().getBuy_permission();
                        if (GoodsFragment.this.inventoryCount <= 0) {
                            GoodsFragment.this.goumai_tv.setText("暂时无货");
                            if (SkinManager.getCurrentSkinType(GoodsFragment.this.getActivity()) == 0) {
                                GoodsFragment.this.goumai_tv.setBackgroundResource(R.color.gray_font_new);
                                return;
                            } else {
                                GoodsFragment.this.goumai_tv.setBackgroundResource(R.color.linetype6_night);
                                return;
                            }
                        }
                        if (GoodsFragment.this.buy_permission.equals("0")) {
                            GoodsFragment.this.goumai_tv.setText("已购买");
                            if (SkinManager.getCurrentSkinType(GoodsFragment.this.getActivity()) == 0) {
                                GoodsFragment.this.goumai_tv.setBackgroundResource(R.color.gray_font_new);
                                return;
                            } else {
                                GoodsFragment.this.goumai_tv.setBackgroundResource(R.color.linetype6_night);
                                return;
                            }
                        }
                        GoodsFragment.this.goumai_tv.setText("立即购买");
                        if (SkinManager.getCurrentSkinType(GoodsFragment.this.getActivity()) == 0) {
                            GoodsFragment.this.goumai_tv.setBackgroundResource(R.color.app_theme_red);
                        } else {
                            GoodsFragment.this.goumai_tv.setBackgroundResource(R.color.linetype6_night);
                        }
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    public static Fragment getInstance(String goods_id, String flag) {
        GoodsFragment goodsFragment = new GoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goods_id", goods_id);
        bundle.putString("flag", flag);
        goodsFragment.setArguments(bundle);
        return goodsFragment;
    }

    public static boolean isFloat(String input) {
        return input.matches("\\d+\\.\\d*");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogFiltrate$4(View view) {
        this.popupWindow_filtrate.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogFiltrate$5(View view) {
        this.popupWindow_filtrate.dismiss();
        this.view_pop_bg.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogFiltrate$6() {
        this.popupWindow_filtrate.dismiss();
        this.view_pop_bg.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$dialogFiltrate$7(View view, int i2, FlowLayout flowLayout) {
        this.mGoodsMeanBean = new GoodsBean.DataBean.MealBean();
        this.mGoodsMeanBean = this.meal.get(i2);
        new UILoader().load(getActivity(), this.img_imgview, this.meal.get(i2).getGoods_thumbnail());
        this.tv_m.setText(this.meal.get(i2).getNow_price());
        this.tv_in.setText(this.meal.get(i2).getDescription());
        this.tv_count.setText(this.meal.get(i2).getSales_volume());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogFiltrate$8(int i2, String str, View view) {
        if (i2 <= 0) {
            AlertToast("暂时无货");
        } else if (str.equals("0")) {
            AlertToast("已购买");
        } else {
            mJumpToPay(this.mGoodsMeanBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initViews$0(View view) {
        EventBus.getDefault().post("jumpstr");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        if (CommonUtil.isFastClick() || !isLogin() || this.mgBeans.getData().getCustomer_service() == null) {
            return;
        }
        if (this.mgBeans.getData().getCustomer_service().getLeyu().size() <= 0) {
            CommonUtil.mWChat(getActivity(), this.mgBeans.getData().getWechat_corpid(), this.mgBeans.getData().getWechat_enterprise_url());
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("attr_data", this.mgBeans.getData().getCustomer_service());
        goActivity(MyCustomerServiceActivity.class, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initViews$2(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(View view) throws NumberFormatException {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.inventoryCount <= 0) {
            AlertToast("暂时无货");
            return;
        }
        if (this.buy_permission.equals("0")) {
            AlertToast("已购买");
            return;
        }
        List<GoodsBean.DataBean.MealBean> list = this.meal;
        if (list != null && list.size() > 0) {
            dialogFiltrate(view);
            return;
        }
        GoodsBean.DataBean.MealBean mealBean = new GoodsBean.DataBean.MealBean();
        this.mGoodsMeanBean = mealBean;
        mealBean.setDescription(this.mgBeans.getData().getDescription());
        this.mGoodsMeanBean.setGoods_id(this.mgBeans.getData().getGoods_id());
        this.mGoodsMeanBean.setBuy_permission(this.mgBeans.getData().getBuy_permission());
        this.mGoodsMeanBean.setGoods_name(this.mgBeans.getData().getGoods_name());
        this.mGoodsMeanBean.setPrice(this.mgBeans.getData().getPrice());
        this.mGoodsMeanBean.setGoods_thumbnail(this.mgBeans.getData().getGoods_thumbnail());
        this.mGoodsMeanBean.setGoods_type(this.is_real);
        mJumpToPay(this.mGoodsMeanBean);
    }

    public void CombMethod() {
        showProgressDialog("请求中...");
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.mGoodsMeanBeans.size(); i2++) {
                SaleGoodsBean saleGoodsBean = new SaleGoodsBean();
                saleGoodsBean.setGoods_id(this.mGoodsMeanBeans.get(i2).getGoods_id());
                saleGoodsBean.setPrice(this.mGoodsMeanBeans.get(i2).getPrice());
                saleGoodsBean.setQuantity(this.mGoodsMeanBeans.get(i2).getQuantity());
                arrayList.add(saleGoodsBean);
            }
            ajaxParams.put("goods_info", new Gson().toJson(arrayList));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.morderFeeUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.GoodsFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                try {
                    GoodsFragment.this.AlertToast("请求失败，请检查网络");
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                GoodsFragment.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        Intent intent = new Intent(GoodsFragment.this.getActivity(), (Class<?>) PayGoodsNewActivity.class);
                        intent.putExtra("productData", (Serializable) GoodsFragment.this.mGoodsMeanBeans);
                        intent.putExtra("total_amount", jSONObject.optJSONObject("data").optString("total_amount") + "");
                        intent.putExtra("message", "");
                        intent.putExtra("user_address_id", "");
                        intent.putExtra("flag", "" + GoodsFragment.this.flag);
                        GoodsFragment.this.startActivity(intent);
                    } else {
                        GoodsFragment.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                GoodsFragment.this.hideProgressDialog();
            }
        });
    }

    @SuppressLint({"NewApi"})
    public void dialogFiltrate(View v2) throws NumberFormatException {
        View viewInflate = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.activity_goumai, (ViewGroup) null);
        View viewFindViewById = viewInflate.findViewById(R.id.rlll);
        final TagFlowLayout tagFlowLayout = (TagFlowLayout) viewInflate.findViewById(R.id.id_flowlayout);
        final LayoutInflater layoutInflaterFrom = LayoutInflater.from(getActivity());
        this.tv_m = (TextView) viewInflate.findViewById(R.id.tv_m);
        this.tv_in = (TextView) viewInflate.findViewById(R.id.tv_in);
        this.tv_count = (TextView) viewInflate.findViewById(R.id.tv_count);
        this.tv_orders = (TextView) viewInflate.findViewById(R.id.tv_orders);
        this.tv_jia = (TextView) viewInflate.findViewById(R.id.tv_jia);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_num);
        this.tv_num = textView;
        textView.setText(String.valueOf(this.tv_num_goumai));
        this.tv_jian = (TextView) viewInflate.findViewById(R.id.tv_jian);
        this.tv_order = (TextView) viewInflate.findViewById(R.id.tv_order);
        this.tv_guanbi = (TextView) viewInflate.findViewById(R.id.tv_guanbi);
        this.img_imgview = (ImageView) viewInflate.findViewById(R.id.img_imgview);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_orders2);
        this.relativeLayout1 = (RelativeLayout) viewInflate.findViewById(R.id.relativeLayout1);
        PopupWindow popupWindow = new PopupWindow(viewInflate, -1, -2);
        this.popupWindow_filtrate = popupWindow;
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        this.popupWindow_filtrate.setFocusable(true);
        this.popupWindow_filtrate.setOutsideTouchable(true);
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13704c.lambda$dialogFiltrate$4(view);
            }
        });
        this.tv_guanbi.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13705c.lambda$dialogFiltrate$5(view);
            }
        });
        this.popupWindow_filtrate.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.activity.purchase.o
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                this.f13706c.lambda$dialogFiltrate$6();
            }
        });
        this.popupWindow_filtrate.setBackgroundDrawable(new BitmapDrawable());
        this.popupWindow_filtrate.showAtLocation(v2, 80, 0, 0);
        this.view_pop_bg.setVisibility(0);
        textView2.setText(this.mgBeans.getData().getStatement());
        this.mGoodsMeanBean = new GoodsBean.DataBean.MealBean();
        this.mGoodsMeanBean = this.meal.get(0);
        new UILoader().load(getActivity(), this.img_imgview, this.meal.get(0).getGoods_thumbnail());
        this.tv_m.setText(this.meal.get(0).getNow_price());
        this.tv_in.setText(this.meal.get(0).getDescription());
        this.tv_count.setText(this.meal.get(0).getSales_volume());
        final int i2 = Integer.parseInt(this.meal.get(0).getInventory());
        final String buy_permission = this.meal.get(0).getBuy_permission();
        if (i2 <= 0) {
            this.tv_orders.setText("暂时无货");
            if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                this.tv_orders.setBackgroundResource(R.color.gray_font_new);
            } else {
                this.goumai_tv.setBackgroundResource(R.color.linetype6_night);
            }
        } else if (buy_permission.equals("0")) {
            this.tv_orders.setText("已购买");
            if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                this.tv_orders.setBackgroundResource(R.color.gray_font_new);
            } else {
                this.goumai_tv.setBackgroundResource(R.color.linetype6_night);
            }
        } else {
            this.tv_orders.setText("立即购买");
            if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                this.goumai_tv.setBackgroundResource(R.color.app_theme_red);
            } else {
                this.goumai_tv.setBackgroundResource(R.color.linetype6_night);
            }
        }
        tagFlowLayout.setMaxSelectCount(1);
        tagFlowLayout.setAdapter(new TagAdapter<GoodsBean.DataBean.MealBean>(this.meal) { // from class: com.psychiatrygarden.activity.purchase.GoodsFragment.4
            @Override // com.psychiatrygarden.utils.TagAdapter
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                ((TextView) view.findViewById(R.id.f26077tv)).setSelected(true);
            }

            @Override // com.psychiatrygarden.utils.TagAdapter
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                ((TextView) view.findViewById(R.id.f26077tv)).setSelected(false);
            }

            @Override // com.psychiatrygarden.utils.TagAdapter
            public View getView(FlowLayout parent, int position, GoodsBean.DataBean.MealBean mealBeans) {
                LinearLayout linearLayout = (LinearLayout) layoutInflaterFrom.inflate(R.layout.packagetxt, (ViewGroup) tagFlowLayout, false);
                ((TextView) linearLayout.findViewById(R.id.f26077tv)).setText(mealBeans.getGoods_name());
                ((ImageView) linearLayout.findViewById(R.id.tuijainimg)).setVisibility("1".equals(mealBeans.getRecommend()) ? 0 : 8);
                return linearLayout;
            }
        });
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() { // from class: com.psychiatrygarden.activity.purchase.p
            @Override // com.psychiatrygarden.utils.TagFlowLayout.OnTagClickListener
            public final boolean onTagClick(View view, int i3, FlowLayout flowLayout) {
                return this.f13707a.lambda$dialogFiltrate$7(view, i3, flowLayout);
            }
        });
        this.tv_orders.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13708c.lambda$dialogFiltrate$8(i2, buy_permission, view);
            }
        });
    }

    public void getCommentList(String type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("obj_id", this.goods_id);
        ajaxParams.put("module_type", Constants.VIA_SHARE_TYPE_MINI_PROGRAM);
        ajaxParams.put("comment_type", "1");
        ajaxParams.put("type", type + "");
        ajaxParams.put(DatabaseManager.SIZE, "3");
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.mCommentList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.GoodsFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) throws NumberFormatException {
                super.onSuccess((AnonymousClass3) t2);
                try {
                    CommodityEvaluationBean commodityEvaluationBean = (CommodityEvaluationBean) new Gson().fromJson(t2, CommodityEvaluationBean.class);
                    if (commodityEvaluationBean.getCode().equals("200")) {
                        int i2 = 0;
                        GoodsFragment.this.all_say_tv.setText(String.format("(%s)", commodityEvaluationBean.getData().getCount().getAll()));
                        GoodsFragment.this.cat_all_tv.setText(String.format("全部评论(%s)", commodityEvaluationBean.getData().getCount().getAll()));
                        try {
                            if (!TextUtils.equals(commodityEvaluationBean.getData().getCount().getAll(), "") && !TextUtils.equals(commodityEvaluationBean.getData().getCount().getFine(), "")) {
                                GoodsFragment.this.tv_all_haoping.setText(Math.round((Double.parseDouble(commodityEvaluationBean.getData().getCount().getFine()) / Double.parseDouble(commodityEvaluationBean.getData().getCount().getAll())) * 100.0d) + "%");
                            }
                        } catch (Exception unused) {
                            GoodsFragment.this.tv_all_haoping.setText("0%");
                        }
                        if (commodityEvaluationBean.getData().getTime_line().size() <= 0) {
                            GoodsFragment.this.linear_pinglun.setVisibility(8);
                            return;
                        }
                        GoodsFragment.this.linear_pinglun.setVisibility(0);
                        int size = commodityEvaluationBean.getData().getTime_line().size() > 3 ? 3 : commodityEvaluationBean.getData().getTime_line().size();
                        int i3 = 0;
                        while (i3 < size) {
                            View viewInflate = LayoutInflater.from(GoodsFragment.this.getActivity()).inflate(R.layout.activity_pingjialist, (ViewGroup) null);
                            TextView textView = (TextView) viewInflate.findViewById(R.id.username);
                            TextView textView2 = (TextView) viewInflate.findViewById(R.id.content_pingjia);
                            textView.setText(commodityEvaluationBean.getData().getTime_line().get(i3).getNickname());
                            textView2.setText(commodityEvaluationBean.getData().getTime_line().get(i3).getContent());
                            NineGridTestLayout nineGridTestLayout = (NineGridTestLayout) viewInflate.findViewById(R.id.ninegrid);
                            LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.linearLayout1);
                            linearLayout.removeAllViews();
                            float f2 = Float.parseFloat(commodityEvaluationBean.getData().getTime_line().get(i3).getGrade());
                            if (f2 - 1.0f <= 0.0f) {
                                for (int i4 = 0; i4 < 5; i4++) {
                                    ImageView imageView = new ImageView(GoodsFragment.this.getActivity());
                                    imageView.setLayoutParams(new LinearLayout.LayoutParams(CommonUtil.getScreenWidth(((BaseFragment) GoodsFragment.this).mContext) / 25, CommonUtil.getScreenWidth(((BaseFragment) GoodsFragment.this).mContext) / 25));
                                    if (i4 == 0) {
                                        if (SkinManager.getCurrentSkinType(GoodsFragment.this.getActivity()) == 0) {
                                            imageView.setBackgroundResource(R.drawable.xing);
                                        } else {
                                            imageView.setBackgroundResource(R.drawable.xing_night);
                                        }
                                    } else if (SkinManager.getCurrentSkinType(GoodsFragment.this.getActivity()) == 0) {
                                        imageView.setBackgroundResource(R.drawable.kongxing);
                                    } else {
                                        imageView.setBackgroundResource(R.drawable.kongxing_night);
                                        linearLayout.addView(imageView);
                                    }
                                    linearLayout.addView(imageView);
                                }
                            } else if (GoodsFragment.isFloat(commodityEvaluationBean.getData().getTime_line().get(i3).getGrade())) {
                                float f3 = (float) (f2 - 0.5d);
                                while (true) {
                                    ImageView imageView2 = new ImageView(GoodsFragment.this.getActivity());
                                    imageView2.setLayoutParams(new LinearLayout.LayoutParams(CommonUtil.getScreenWidth(((BaseFragment) GoodsFragment.this).mContext) / 25, CommonUtil.getScreenWidth(((BaseFragment) GoodsFragment.this).mContext) / 25));
                                    float f4 = i2;
                                    if (f3 > f4) {
                                        if (SkinManager.getCurrentSkinType(GoodsFragment.this.getActivity()) == 0) {
                                            imageView2.setBackgroundResource(R.drawable.xing);
                                        } else {
                                            imageView2.setBackgroundResource(R.drawable.xing_night);
                                        }
                                    } else if (f4 == f3) {
                                        if (SkinManager.getCurrentSkinType(GoodsFragment.this.getActivity()) == 0) {
                                            imageView2.setBackgroundResource(R.drawable.xing);
                                        } else {
                                            imageView2.setBackgroundResource(R.drawable.xing_night);
                                        }
                                    } else if (SkinManager.getCurrentSkinType(GoodsFragment.this.getActivity()) == 0) {
                                        imageView2.setBackgroundResource(R.drawable.kongxing);
                                    } else {
                                        imageView2.setBackgroundResource(R.drawable.kongxing_night);
                                    }
                                    linearLayout.addView(imageView2);
                                }
                            } else {
                                int i5 = i2;
                                for (int i6 = 5; i5 < i6; i6 = 5) {
                                    ImageView imageView3 = new ImageView(GoodsFragment.this.getActivity());
                                    imageView3.setLayoutParams(new LinearLayout.LayoutParams(CommonUtil.getScreenWidth(((BaseFragment) GoodsFragment.this).mContext) / 25, CommonUtil.getScreenWidth(((BaseFragment) GoodsFragment.this).mContext) / 25));
                                    if (f2 > i5) {
                                        if (SkinManager.getCurrentSkinType(GoodsFragment.this.getActivity()) == 0) {
                                            imageView3.setBackgroundResource(R.drawable.xing);
                                        } else {
                                            imageView3.setBackgroundResource(R.drawable.xing_night);
                                        }
                                    } else if (SkinManager.getCurrentSkinType(GoodsFragment.this.getActivity()) == 0) {
                                        imageView3.setBackgroundResource(R.drawable.kongxing);
                                    } else {
                                        imageView3.setBackgroundResource(R.drawable.kongxing_night);
                                    }
                                    linearLayout.addView(imageView3);
                                    i5++;
                                }
                            }
                            nineGridTestLayout.setIsShowAll(commodityEvaluationBean.getData().getTime_line().get(i3).getImgs().size() <= 3);
                            nineGridTestLayout.setUrlList(commodityEvaluationBean.getData().getTime_line().get(i3).getImgs());
                            GoodsFragment.this.list_linear.addView(viewInflate);
                            i3++;
                            i2 = 0;
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    GoodsFragment.this.linear_pinglun.setVisibility(8);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        this.goods_id = getArguments().getString("goods_id", "");
        this.flag = getArguments().getString("flag", "");
        return R.layout.fragment_goods;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) throws IllegalAccessException, NoSuchFieldException, Resources.NotFoundException, SecurityException, IllegalArgumentException {
        this.pageListImgs = new ArrayList();
        this.tv_num_goumai = 1;
        this.view_pop_bg = holder.get(R.id.view_pop_bg);
        this.view5 = holder.get(R.id.view5);
        this.linear_pinglun = (LinearLayout) holder.get(R.id.linear_pinglun);
        this.list_linear = (LinearLayout) holder.get(R.id.list_linear);
        this.cat_all_tv = (TextView) holder.get(R.id.cat_all_tv);
        this.rl_dingwei = (RelativeLayout) holder.get(R.id.rl_dingwei);
        this.scrollView2 = (ScrollView) holder.get(R.id.scrollView2);
        this.all_say_tv = (TextView) holder.get(R.id.all_say_tv);
        this.tv_all_haoping = (TextView) holder.get(R.id.tv_all_haoping);
        this.textView3 = (TextView) holder.get(R.id.textView3);
        this.tv_zhekoujia = (TextView) holder.get(R.id.tv_zhekoujia);
        this.tv_youfei = (TextView) holder.get(R.id.tv_youfei);
        this.gouwuche = (TextView) holder.get(R.id.gouwuche);
        this.tv_joincart = (TextView) holder.get(R.id.tv_joincart);
        this.tv_title = (TextView) holder.get(R.id.tv_title);
        this.tv_content = (TextView) holder.get(R.id.tv_content);
        this.tv_money = (TextView) holder.get(R.id.tv_money);
        this.tv_money1 = (TextView) holder.get(R.id.tv_money1);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.tv_content.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
            this.tv_money.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
            this.tv_all_haoping.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
            this.tv_money1.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
        } else {
            this.tv_content.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
            this.tv_money.setTextColor(ContextCompat.getColor(this.mContext, R.color.red_theme_night));
            this.tv_all_haoping.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
            this.tv_money1.setTextColor(ContextCompat.getColor(this.mContext, R.color.red_theme_night));
        }
        this.line_content = (LinearLayout) holder.get(R.id.line_content);
        this.tagView = (TagFlowLayout) holder.get(R.id.test);
        this.goumai_tv = (TextView) holder.get(R.id.goumai);
        this.goumai_xuni = (TextView) holder.get(R.id.goumai_xuni);
        this.llay_shiti = (LinearLayout) holder.get(R.id.llay_shiti);
        this.no_content = (TextView) holder.get(R.id.no_content);
        this.pageViews = new ArrayList<>();
        this.infinite_anim_circle = (InfiniteIndicator) holder.get(R.id.infinite_anim_circle);
        this.infinite_anim_circle.init(new IndicatorConfiguration.Builder().imageLoader(new UILoader()).isStopWhileTouch(true).onPageChangeListener(this).onPageClickListener(this).direction(0).internal(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS).position(IndicatorConfiguration.IndicatorPosition.Center_Bottom).mThemeSkin(SkinManager.getCurrentSkinType(getActivity()) != 0 ? 1 : 0).build());
        getGoodsDetile();
        getCommentList("");
        this.cat_all_tv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GoodsFragment.lambda$initViews$0(view);
            }
        });
        this.textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13701c.lambda$initViews$1(view);
            }
        });
        this.no_content.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws NumberFormatException {
                this.f13702c.dialogFiltrate(view);
            }
        });
        this.goumai_xuni.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GoodsFragment.lambda$initViews$2(view);
            }
        });
        this.goumai_tv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws NumberFormatException {
                this.f13703c.lambda$initViews$3(view);
            }
        });
    }

    public void mJumpToPay(GoodsBean.DataBean.MealBean mGoodsMeanBean) {
        if (isLogin()) {
            this.mGoodsMeanBeans.clear();
            this.mGoodsMeanBeans.add(mGoodsMeanBean);
            if (!mGoodsMeanBean.getGoods_type().equals("1")) {
                CombMethod();
                return;
            }
            Intent intent = new Intent(getActivity(), (Class<?>) QuenRenDingDanNewActivity.class);
            intent.putExtra("productData", (Serializable) this.mGoodsMeanBeans);
            intent.putExtra("group_purchase", "" + this.mgBeans.getData().getGroup_purchase());
            intent.putExtra("flag", "" + this.flag);
            startActivity(intent);
        }
    }

    @Override // cn.lightsky.infiniteindicator.OnPageClickListener
    public void onPageClick(int position, Page page) {
        Intent intent;
        if (page.data.equals("video")) {
            intent = new Intent(getActivity(), (Class<?>) VideoRePlayActivity.class);
            intent.putExtra("video_url", "" + this.mgBeans.getData().getGoods_video().getVideo());
        } else {
            Intent intent2 = new Intent(getActivity(), (Class<?>) ViewPagerActivity.class);
            intent2.putExtra("position", position);
            intent2.putExtra("list", (Serializable) this.pageListImgs);
            intent = intent2;
        }
        startActivity(intent);
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int state) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int position) {
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
    }
}
