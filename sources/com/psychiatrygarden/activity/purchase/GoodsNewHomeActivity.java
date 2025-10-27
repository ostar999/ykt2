package com.psychiatrygarden.activity.purchase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.webdemo.com.supporfragment.tablayout.FragmentContainerHelper;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.activity.PayGoodsNewActivity;
import com.psychiatrygarden.activity.purchase.beans.CommodityEvaluationBean;
import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import com.psychiatrygarden.activity.purchase.beans.GoodsCominBean;
import com.psychiatrygarden.activity.purchase.beans.SaleGoodsBean;
import com.psychiatrygarden.activity.purchase.beans.TryAndSeeBean;
import com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.GoodsSimplePagerTitleView;
import com.psychiatrygarden.widget.ShopBottom2Popwindow;
import com.psychiatrygarden.widget.ShopBottomPopwindow;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class GoodsNewHomeActivity extends BaseActivity {
    public MultiItemAdapter adapter;
    private ImageView backid;
    private String buy_permission;
    public CommodityController commodityController;
    public CommonNavigator commonNavigator;
    public GoodsCommentController goodsCommentController;
    private TextView goumai_tv;
    public boolean isDestroyed;
    private GoodsBean.DataBean.MealBean mGoodsMeanBean;
    public MagicIndicator magic_indicator;
    private GoodsBean mgBeans;
    public RecyclerView recyclerView;
    private TextView services_tv;
    public String[] TitleStr = {"商品", "评价", "详情"};
    public List<Fragment> lisview = new ArrayList();
    public String goods_id = "";
    private int inventoryCount = 0;
    private List<GoodsCominBean> listadapter = new ArrayList();
    private int oldPisition = -1;
    private float wTop = 0.0f;
    private final FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();
    private List<GoodsBean.DataBean.MealBean> mGoodsMeanBeans = new ArrayList();

    /* renamed from: com.psychiatrygarden.activity.purchase.GoodsNewHomeActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends CommonNavigatorAdapter {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) {
            GoodsNewHomeActivity.this.mFragmentContainerHelper.handlePageSelected(i2);
            ((LinearLayoutManager) GoodsNewHomeActivity.this.recyclerView.getLayoutManager()).scrollToPositionWithOffset(i2, 0);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return GoodsNewHomeActivity.this.TitleStr.length;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            linePagerIndicator.setMode(2);
            linePagerIndicator.setLineHeight(UIUtil.dip2px(context, 3.0d));
            linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 10.0d));
            linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 3.0d));
            if (SkinManager.getCurrentSkinType(context) == 1) {
                linePagerIndicator.setColors(Integer.valueOf(GoodsNewHomeActivity.this.mContext.getResources().getColor(R.color.main_theme_color_night)));
            } else {
                linePagerIndicator.setColors(Integer.valueOf(GoodsNewHomeActivity.this.mContext.getResources().getColor(R.color.main_theme_color)));
            }
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            String str;
            String str2;
            GoodsSimplePagerTitleView goodsSimplePagerTitleView = new GoodsSimplePagerTitleView(GoodsNewHomeActivity.this);
            if (SkinManager.getCurrentSkinType(context) == 1) {
                str = "#575F79";
                str2 = "#7380A9";
            } else {
                str = "#141516";
                str2 = "#141516";
            }
            goodsSimplePagerTitleView.setSelectedColor(Color.parseColor(str2));
            goodsSimplePagerTitleView.setNormalColor(Color.parseColor(str));
            goodsSimplePagerTitleView.setText(GoodsNewHomeActivity.this.TitleStr[index]);
            goodsSimplePagerTitleView.setTextSize(2, 15.0f);
            goodsSimplePagerTitleView.setMinWidth(CommonUtil.dip2px(GoodsNewHomeActivity.this, 70.0f));
            goodsSimplePagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.s
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13712c.lambda$getTitleView$0(index, view);
                }
            });
            return goodsSimplePagerTitleView;
        }
    }

    /* renamed from: com.psychiatrygarden.activity.purchase.GoodsNewHomeActivity$5, reason: invalid class name */
    public class AnonymousClass5 extends AjaxCallBack<String> {
        public AnonymousClass5() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(View view) {
            OnlineServiceBean cs;
            if (CommonUtil.isFastClick() || !GoodsNewHomeActivity.this.isLogin() || GoodsNewHomeActivity.this.mgBeans == null || GoodsNewHomeActivity.this.mgBeans.getData() == null || GoodsNewHomeActivity.this.mgBeans.getData().getCs() == null || (cs = GoodsNewHomeActivity.this.mgBeans.getData().getCs()) == null) {
                return;
            }
            CommonUtil.onlineService(GoodsNewHomeActivity.this, cs);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(GoodsBean.DataBean.MealBean mealBean) {
            GoodsNewHomeActivity.this.mJumpToPay(mealBean);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$2(List list, List list2, View view) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            if (list != null && list.size() > 0) {
                XPopup.Builder builderEnableDrag = new XPopup.Builder(GoodsNewHomeActivity.this).enableDrag(false);
                GoodsNewHomeActivity goodsNewHomeActivity = GoodsNewHomeActivity.this;
                builderEnableDrag.asCustom(new ShopBottomPopwindow(goodsNewHomeActivity, list, goodsNewHomeActivity.mgBeans.getData().getStatement(), new ShopBottomPopwindow.ShopBottomClickIml() { // from class: com.psychiatrygarden.activity.purchase.t
                    @Override // com.psychiatrygarden.widget.ShopBottomPopwindow.ShopBottomClickIml
                    public final void mShopBottomClickIml(GoodsBean.DataBean.MealBean mealBean) {
                        this.f13714a.lambda$onSuccess$1(mealBean);
                    }
                })).show();
                return;
            }
            if (list2 != null && list2.size() > 0) {
                XPopup.Builder builderEnableDrag2 = new XPopup.Builder(GoodsNewHomeActivity.this).enableDrag(false);
                GoodsNewHomeActivity goodsNewHomeActivity2 = GoodsNewHomeActivity.this;
                builderEnableDrag2.asCustom(new ShopBottom2Popwindow(goodsNewHomeActivity2, goodsNewHomeActivity2.mgBeans.getData().getAttributes(), GoodsNewHomeActivity.this.mgBeans.getData().getSkus(), GoodsNewHomeActivity.this.mgBeans.getData().getGoods_thumbnail(), new ShopBottom2Popwindow.ConfirmPurchaseClickIml() { // from class: com.psychiatrygarden.activity.purchase.GoodsNewHomeActivity.5.1
                    @Override // com.psychiatrygarden.widget.ShopBottom2Popwindow.ConfirmPurchaseClickIml
                    public void mConfirmPurchaseClickIml(GoodsBean.DataBean.SkusBean skusBean) {
                        GoodsBean.DataBean.MealBean mealBean = new GoodsBean.DataBean.MealBean();
                        mealBean.setDescription(GoodsNewHomeActivity.this.mgBeans.getData().getDescription());
                        mealBean.setGoods_id(GoodsNewHomeActivity.this.mgBeans.getData().getGoods_id());
                        mealBean.setBuy_permission(GoodsNewHomeActivity.this.mgBeans.getData().getBuy_permission());
                        mealBean.setGoods_name(GoodsNewHomeActivity.this.mgBeans.getData().getGoods_name());
                        mealBean.setPrice(skusBean.getPrice());
                        mealBean.setGoods_thumbnail(GoodsNewHomeActivity.this.mgBeans.getData().getGoods_thumbnail());
                        mealBean.setGoods_type(GoodsNewHomeActivity.this.mgBeans.getData().getGoods_type() + "");
                        mealBean.setSku_id(skusBean.getSku_id() + "");
                        mealBean.setGroup_purchase(GoodsNewHomeActivity.this.mgBeans.getData().getGroup_purchase() + "");
                        GoodsNewHomeActivity.this.mJumpToPay(mealBean);
                    }
                })).show();
                return;
            }
            if (GoodsNewHomeActivity.this.inventoryCount <= 0) {
                GoodsNewHomeActivity.this.AlertToast("暂时无货");
                return;
            }
            if (GoodsNewHomeActivity.this.buy_permission.equals("0")) {
                GoodsNewHomeActivity.this.AlertToast("已购买");
                return;
            }
            GoodsNewHomeActivity.this.mGoodsMeanBean = new GoodsBean.DataBean.MealBean();
            GoodsNewHomeActivity.this.mGoodsMeanBean.setDescription(GoodsNewHomeActivity.this.mgBeans.getData().getDescription());
            GoodsNewHomeActivity.this.mGoodsMeanBean.setGoods_id(GoodsNewHomeActivity.this.mgBeans.getData().getGoods_id());
            GoodsNewHomeActivity.this.mGoodsMeanBean.setBuy_permission(GoodsNewHomeActivity.this.mgBeans.getData().getBuy_permission());
            GoodsNewHomeActivity.this.mGoodsMeanBean.setGoods_name(GoodsNewHomeActivity.this.mgBeans.getData().getGoods_name());
            GoodsNewHomeActivity.this.mGoodsMeanBean.setPrice(GoodsNewHomeActivity.this.mgBeans.getData().getPrice());
            GoodsNewHomeActivity.this.mGoodsMeanBean.setGoods_thumbnail(GoodsNewHomeActivity.this.mgBeans.getData().getGoods_thumbnail());
            GoodsNewHomeActivity.this.mGoodsMeanBean.setGoods_type(GoodsNewHomeActivity.this.mgBeans.getData().getGoods_type() + "");
            GoodsNewHomeActivity.this.mGoodsMeanBean.setGroup_purchase(GoodsNewHomeActivity.this.mgBeans.getData().getGroup_purchase() + "");
            GoodsNewHomeActivity goodsNewHomeActivity3 = GoodsNewHomeActivity.this;
            goodsNewHomeActivity3.mJumpToPay(goodsNewHomeActivity3.mGoodsMeanBean);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String t2) {
            try {
                GoodsNewHomeActivity.this.mgBeans = (GoodsBean) new Gson().fromJson(t2, GoodsBean.class);
                GoodsNewHomeActivity goodsNewHomeActivity = GoodsNewHomeActivity.this;
                CommodityController commodityController = goodsNewHomeActivity.commodityController;
                if (commodityController != null) {
                    commodityController.showGoodsDetileView(goodsNewHomeActivity.mgBeans);
                    GoodsNewHomeActivity.this.getTryAndSee();
                }
                if (GoodsNewHomeActivity.this.mgBeans.getCode().equals("200")) {
                    GoodsNewHomeActivity goodsNewHomeActivity2 = GoodsNewHomeActivity.this;
                    goodsNewHomeActivity2.inventoryCount = Integer.parseInt(goodsNewHomeActivity2.mgBeans.getData().getInventory());
                    GoodsNewHomeActivity goodsNewHomeActivity3 = GoodsNewHomeActivity.this;
                    goodsNewHomeActivity3.buy_permission = goodsNewHomeActivity3.mgBeans.getData().getBuy_permission();
                    final List<GoodsBean.DataBean.MealBean> meal = GoodsNewHomeActivity.this.mgBeans.getData().getMeal();
                    final List<GoodsBean.DataBean.SkusBean> skus = GoodsNewHomeActivity.this.mgBeans.getData().getSkus();
                    if ((meal != null && meal.size() > 0) || (skus != null && skus.size() > 0)) {
                        GoodsNewHomeActivity.this.goumai_tv.setText("立即购买");
                        GoodsNewHomeActivity.this.goumai_tv.setBackgroundResource(R.drawable.shape_goods_action);
                    } else if (GoodsNewHomeActivity.this.inventoryCount <= 0) {
                        GoodsNewHomeActivity.this.goumai_tv.setText("暂时无货");
                        GoodsNewHomeActivity.this.goumai_tv.setBackgroundResource(R.drawable.shape_goods_gray_action);
                    } else if (GoodsNewHomeActivity.this.buy_permission.equals("0")) {
                        GoodsNewHomeActivity.this.goumai_tv.setText("已购买");
                        GoodsNewHomeActivity.this.goumai_tv.setBackgroundResource(R.drawable.shape_goods_gray_action);
                    } else {
                        GoodsNewHomeActivity.this.goumai_tv.setText("立即购买");
                        GoodsNewHomeActivity.this.goumai_tv.setBackgroundResource(R.drawable.shape_goods_action);
                    }
                    GoodsNewHomeActivity.this.services_tv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.u
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f13715c.lambda$onSuccess$0(view);
                        }
                    });
                    GoodsNewHomeActivity.this.goumai_tv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.v
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f13718c.lambda$onSuccess$2(meal, skus, view);
                        }
                    });
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class MultiItemAdapter extends BaseMultiItemQuickAdapter<GoodsCominBean, BaseViewHolder> {
        public MultiItemAdapter(@Nullable List<GoodsCominBean> data) {
            super(data);
            addItemType(0, R.layout.layout_goods_info);
            addItemType(1, R.layout.layout_goods_comment);
            addItemType(2, R.layout.fragment_goods_info);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$showGoodsDetail$0(WebView webView, View view, MotionEvent motionEvent) {
            if (GoodsNewHomeActivity.this.oldPisition == 2) {
                int action = motionEvent.getAction() & 255;
                if (action == 0) {
                    GoodsNewHomeActivity.this.wTop = motionEvent.getY();
                    GoodsNewHomeActivity.this.recyclerView.requestDisallowInterceptTouchEvent(true);
                } else if (action == 2) {
                    if (GoodsNewHomeActivity.this.wTop < motionEvent.getY() && webView.getScrollY() <= 0) {
                        GoodsNewHomeActivity.this.recyclerView.requestDisallowInterceptTouchEvent(false);
                    } else {
                        GoodsNewHomeActivity.this.recyclerView.requestDisallowInterceptTouchEvent(true);
                    }
                }
            }
            return false;
        }

        public void showCommentData(BaseViewHolder baseViewHolder, CommodityEvaluationBean.DataBean commentData) {
            GoodsNewHomeActivity goodsNewHomeActivity = GoodsNewHomeActivity.this;
            goodsNewHomeActivity.goodsCommentController = new GoodsCommentController(baseViewHolder, commentData, goodsNewHomeActivity, goodsNewHomeActivity.goods_id);
            GoodsNewHomeActivity.this.getCommentList("");
        }

        public void showGoodsDetail(BaseViewHolder baseViewHolder, String url) {
            final WebView webView = (WebView) baseViewHolder.getView(R.id.web);
            baseViewHolder.getView(R.id.yejian).setVisibility(8);
            WebSettings settings = webView.getSettings();
            webView.removeJavascriptInterface("searchBoxJavaBredge_");
            settings.setJavaScriptEnabled(true);
            settings.setAllowFileAccess(true);
            settings.setSupportZoom(true);
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(false);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setMixedContentMode(0);
            webView.loadUrl("about:blank");
            webView.setWebViewClient(new WebViewClient() { // from class: com.psychiatrygarden.activity.purchase.GoodsNewHomeActivity.MultiItemAdapter.1
                @Override // android.webkit.WebViewClient
                public void onPageFinished(WebView view, String url2) {
                    super.onPageFinished(view, url2);
                    if (webView == null || SkinManager.getCurrentSkinType(GoodsNewHomeActivity.this.mContext) != 1) {
                        return;
                    }
                    webView.evaluateJavascript("document.body.style.backgroundColor='#121622';document.body.style.color='#7380A9';", null);
                }

                @Override // android.webkit.WebViewClient
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                }

                @Override // android.webkit.WebViewClient
                public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                    super.onReceivedHttpError(view, request, errorResponse);
                }

                @Override // android.webkit.WebViewClient
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();
                    super.onReceivedSslError(view, handler, error);
                }

                @Override // android.webkit.WebViewClient
                public boolean shouldOverrideUrlLoading(WebView view, String url2) {
                    view.loadUrl(url2);
                    return true;
                }
            });
            GoodsNewHomeActivity.this.goodsDetailHtml(webView);
            webView.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.purchase.w
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f13721c.lambda$showGoodsDetail$0(webView, view, motionEvent);
                }
            });
        }

        public void showGoodsInfo(BaseViewHolder baseViewHolder, GoodsBean.DataBean data) {
            GoodsNewHomeActivity goodsNewHomeActivity = GoodsNewHomeActivity.this;
            goodsNewHomeActivity.commodityController = new CommodityController(goodsNewHomeActivity, baseViewHolder, data);
            GoodsNewHomeActivity.this.getGoodsDetile();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder baseViewHolder, GoodsCominBean goodsCominBean) {
            if (baseViewHolder.getItemViewType() == 0) {
                showGoodsInfo(baseViewHolder, goodsCominBean.getData());
            } else if (baseViewHolder.getItemViewType() == 1) {
                showCommentData(baseViewHolder, goodsCominBean.getCommentData());
            } else {
                showGoodsDetail(baseViewHolder, goodsCominBean.getDetailUrl());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getGoodsDetile() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("goods_id", this.goods_id);
        YJYHttpUtils.getgoodsmd5(this, NetworkRequestsURL.goodsDetailURL, ajaxParams, new AnonymousClass5());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getTryAndSee() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("goods_id", this.goods_id);
        YJYHttpUtils.get(this, NetworkRequestsURL.curriculumtryAndSeeUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.GoodsNewHomeActivity.6
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
                CommodityController commodityController;
                super.onSuccess((AnonymousClass6) s2);
                try {
                    TryAndSeeBean tryAndSeeBean = (TryAndSeeBean) new Gson().fromJson(s2, TryAndSeeBean.class);
                    if (!"200".equals(tryAndSeeBean.getCode()) || (commodityController = GoodsNewHomeActivity.this.commodityController) == null) {
                        return;
                    }
                    commodityController.showTryAndSee(tryAndSeeBean.getData());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
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
                if (!TextUtils.isEmpty(this.mGoodsMeanBeans.get(i2).getSku_id())) {
                    saleGoodsBean.setSku_id(this.mGoodsMeanBeans.get(i2).getSku_id());
                }
                arrayList.add(saleGoodsBean);
            }
            ajaxParams.put("goods_info", new Gson().toJson(arrayList));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.post(this, NetworkRequestsURL.morderFeeUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.GoodsNewHomeActivity.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                try {
                    GoodsNewHomeActivity.this.AlertToast("请求失败，请检查网络");
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                GoodsNewHomeActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass7) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        Intent intent = new Intent(GoodsNewHomeActivity.this, (Class<?>) PayGoodsNewActivity.class);
                        intent.putExtra("productData", (Serializable) GoodsNewHomeActivity.this.mGoodsMeanBeans);
                        intent.putExtra("total_amount", jSONObject.optJSONObject("data").optString("total_amount") + "");
                        intent.putExtra("message", "");
                        intent.putExtra("cat_id", "" + GoodsNewHomeActivity.this.mgBeans.getData().getCat_id());
                        intent.putExtra("after_purchase_goto", "" + GoodsNewHomeActivity.this.mgBeans.getData().getAfter_purchase_goto());
                        intent.putExtra("cs", new Gson().toJson(GoodsNewHomeActivity.this.mgBeans.getData().getCs()));
                        intent.putExtra("user_address_id", "");
                        intent.putExtra("flag", "" + GoodsNewHomeActivity.this.getIntent().getExtras().getString("flag"));
                        GoodsNewHomeActivity.this.startActivity(intent);
                    } else {
                        GoodsNewHomeActivity.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                GoodsNewHomeActivity.this.hideProgressDialog();
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
        YJYHttpUtils.post(this, NetworkRequestsURL.mCommentList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.GoodsNewHomeActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass3) t2);
                try {
                    CommodityEvaluationBean commodityEvaluationBean = (CommodityEvaluationBean) new Gson().fromJson(t2, CommodityEvaluationBean.class);
                    GoodsCommentController goodsCommentController = GoodsNewHomeActivity.this.goodsCommentController;
                    if (goodsCommentController != null) {
                        goodsCommentController.initData(commodityEvaluationBean.getData());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void goodsDetailHtml(final WebView mWebview) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("goods_id", this.goods_id);
        YJYHttpUtils.get(this, NetworkRequestsURL.goodsDetailHtmlUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.GoodsNewHomeActivity.4
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
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        mWebview.loadUrl(new JSONObject(s2).optJSONObject("data").optString("url"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.goods_id = getIntent().getStringExtra("goods_id");
        ImageView imageView = (ImageView) findViewById(R.id.backid);
        this.backid = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13711c.lambda$init$0(view);
            }
        });
        this.goumai_tv = (TextView) findViewById(R.id.goumai);
        this.services_tv = (TextView) findViewById(R.id.services_tv);
        this.magic_indicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        super.initStatusBar();
        if (this.mBaseTheme != 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_one_color_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_one_color), 0);
            getWindow().getDecorView().setSystemUiVisibility(R2.drawable.ddbq);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        }
    }

    public void mJumpToPay(GoodsBean.DataBean.MealBean mGoodsMeanBean) {
        GoodsBean goodsBean;
        if (!isLogin() || (goodsBean = this.mgBeans) == null || goodsBean.getData() == null) {
            return;
        }
        this.mGoodsMeanBeans.clear();
        this.mGoodsMeanBeans.add(mGoodsMeanBean);
        if (mGoodsMeanBean.getGoods_type().equals("1")) {
            return;
        }
        CombMethod();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if ("BuySuccess".equals(str)) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        this.commonNavigator = commonNavigator;
        commonNavigator.setFollowTouch(false);
        this.commonNavigator.setAdjustMode(false);
        this.commonNavigator.setAdapter(new AnonymousClass1());
        this.magic_indicator.setNavigator(this.commonNavigator);
        this.mFragmentContainerHelper.attachMagicIndicator(this.magic_indicator);
        this.mFragmentContainerHelper.setInterpolator(new LinearInterpolator());
        for (int i2 = 0; i2 < 3; i2++) {
            if (i2 == 0) {
                GoodsCominBean goodsCominBean = new GoodsCominBean();
                goodsCominBean.setViewType(0);
                goodsCominBean.setData(new GoodsBean.DataBean());
                this.listadapter.add(goodsCominBean);
            } else if (i2 == 1) {
                GoodsCominBean goodsCominBean2 = new GoodsCominBean();
                goodsCominBean2.setViewType(1);
                goodsCominBean2.setCommentData(new CommodityEvaluationBean.DataBean());
                this.listadapter.add(goodsCominBean2);
            } else {
                GoodsCominBean goodsCominBean3 = new GoodsCominBean();
                goodsCominBean3.setViewType(2);
                goodsCominBean3.setDetailUrl("");
                this.listadapter.add(goodsCominBean3);
            }
        }
        MultiItemAdapter multiItemAdapter = new MultiItemAdapter(this.listadapter);
        this.adapter = multiItemAdapter;
        this.recyclerView.setAdapter(multiItemAdapter);
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.activity.purchase.GoodsNewHomeActivity.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int iFindFirstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                GoodsNewHomeActivity.this.mFragmentContainerHelper.handlePageSelected(iFindFirstVisibleItemPosition, true);
                GoodsNewHomeActivity.this.oldPisition = iFindFirstVisibleItemPosition;
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        CustomAliPlayerView customAliPlayerView;
        super.onResume();
        CommodityController commodityController = this.commodityController;
        if (commodityController == null || (customAliPlayerView = commodityController.videoview) == null) {
            return;
        }
        customAliPlayerView.onResume();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        CustomAliPlayerView customAliPlayerView;
        super.onStop();
        CommodityController commodityController = this.commodityController;
        if (commodityController == null || (customAliPlayerView = commodityController.videoview) == null) {
            return;
        }
        customAliPlayerView.onPause();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_goods_new_home);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
