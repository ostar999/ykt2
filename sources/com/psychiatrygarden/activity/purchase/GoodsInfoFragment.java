package com.psychiatrygarden.activity.purchase;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class GoodsInfoFragment extends BaseFragment {
    public String countsize;
    private String goods_id = "";
    TextView goumai;
    private String mGoodsUrl;
    private WebView mWebview;

    public class webViewClient extends WebViewClient {
        private webViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public static Fragment getInstance(String goods_id) {
        GoodsInfoFragment goodsInfoFragment = new GoodsInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goods_id", goods_id);
        goodsInfoFragment.setArguments(bundle);
        return goodsInfoFragment;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        this.goods_id = getArguments().getString("goods_id");
        return R.layout.fragment_goods_info;
    }

    public void goodsDetailHtml() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("goods_id", this.goods_id);
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.goodsDetailHtmlUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.GoodsInfoFragment.1
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
                super.onSuccess((AnonymousClass1) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        GoodsInfoFragment.this.mGoodsUrl = new JSONObject(s2).optJSONObject("data").optString("url");
                        WebSettings settings = GoodsInfoFragment.this.mWebview.getSettings();
                        GoodsInfoFragment.this.mWebview.removeJavascriptInterface("searchBoxJavaBredge_");
                        settings.setJavaScriptEnabled(true);
                        settings.setAllowFileAccess(true);
                        settings.setSupportZoom(true);
                        settings.setBuiltInZoomControls(true);
                        settings.setDisplayZoomControls(false);
                        settings.setUseWideViewPort(true);
                        settings.setLoadWithOverviewMode(true);
                        settings.setMixedContentMode(0);
                        GoodsInfoFragment.this.mWebview.loadUrl(GoodsInfoFragment.this.mGoodsUrl);
                        GoodsInfoFragment.this.mWebview.setWebViewClient(new webViewClient());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.goumai = (TextView) holder.get(R.id.goumai);
        View view = holder.get(R.id.yejian);
        this.mWebview = (WebView) holder.get(R.id.web);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            view.setVisibility(8);
        } else {
            view.setVisibility(0);
            this.mWebview.setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.bg_backgroud_night));
        }
        goodsDetailHtml();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        this.mWebview.loadUrl("about:blank");
        super.onDestroy();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onPause() {
        this.mWebview.reload();
        super.onPause();
    }
}
