package com.psychiatrygarden.activity.courselist.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.just.agentweb.DefaultWebClient;
import com.lxj.xpopup.core.PopupInfo;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class CourseIntroductionFragment extends BaseFragment {
    String WEBVIEW_CONTENT;
    String body;
    String discription_img;
    private WebView webview;

    public class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    }

    public class PrictData {
        Context context;

        public PrictData(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void getCoument(String url) {
            try {
                if (TextUtils.isEmpty(url)) {
                    return;
                }
                if (TextUtils.isEmpty(Uri.parse(url).getScheme())) {
                    url = DefaultWebClient.HTTP_SCHEME + url;
                }
                Intent intent = new Intent();
                intent.setData(Uri.parse(url));
                intent.setAction("android.intent.action.VIEW");
                ((BaseFragment) CourseIntroductionFragment.this).mContext.startActivity(intent);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @JavascriptInterface
        public void getPrictData(int postion) {
            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(((BaseFragment) CourseIntroductionFragment.this).mContext).setSingleSrcView(null, CourseIntroductionFragment.this.discription_img).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader.popupInfo = new PopupInfo();
            xPopupImageLoader.show();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_course_intro;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.WEBVIEW_CONTENT = CommonUtil.getWebHtml(getActivity());
        WebView webView = (WebView) holder.get(R.id.webview);
        this.webview = webView;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.webview.setHorizontalScrollBarEnabled(false);
        this.webview.setVerticalScrollBarEnabled(false);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setDefaultTextEncodingName("utf-8");
        this.webview.setBackgroundColor(0);
        this.webview.getSettings().setDefaultFontSize(17);
        this.webview.setWebViewClient(new MyWebViewClient());
        this.webview.addJavascriptInterface(new PrictData(this.mContext), "javaScxript");
        String string = getArguments().getString("discription_img");
        this.discription_img = string;
        if (string == null || "".equals(string)) {
            return;
        }
        String str = this.discription_img;
        if (str != null && str.endsWith("html")) {
            this.webview.loadUrl(this.discription_img);
            return;
        }
        String str2 = "<p><img id='showimg' onclick='prictaction(this)' style='width: 100%; height: auto;' src='" + this.discription_img + "' /></p>";
        this.body = str2;
        this.webview.loadDataWithBaseURL(null, String.format(this.WEBVIEW_CONTENT, str2), "text/html", "utf-8", null);
    }
}
