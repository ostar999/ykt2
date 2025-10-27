package com.psychiatrygarden.widget;

import android.app.Activity;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class AuthDialogView extends CenterPopupView {
    public Activity mContext;
    public OnAuthDataListener mListener;
    public WebView webview;

    /* renamed from: com.psychiatrygarden.widget.AuthDialogView$2, reason: invalid class name */
    public class AnonymousClass2 extends WebViewClient {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPageFinished$0() {
            AuthDialogView.this.webview.setVisibility(0);
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.j
                @Override // java.lang.Runnable
                public final void run() {
                    this.f16600c.lambda$onPageFinished$0();
                }
            }, 500L);
        }
    }

    public interface OnAuthDataListener {
        void onConfirm(String dataJson);
    }

    public class testJsInterface {
        Activity mActivity;

        public testJsInterface(Activity activity) {
            this.mActivity = activity;
        }

        @JavascriptInterface
        public void closeWebView() {
        }

        @JavascriptInterface
        public void getVerifyResult(String jsonString) throws JSONException {
            System.out.println(jsonString);
            try {
                AuthDialogView.this.mListener.onConfirm(new JSONObject(jsonString).getString("data"));
                AuthDialogView.this.dialog.dismiss();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public AuthDialogView(@NonNull Activity context, OnAuthDataListener listener) {
        super(context);
        this.mContext = context;
        this.mListener = listener;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.auth_dialog_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        WebView webView = (WebView) findViewById(R.id.webview);
        this.webview = webView;
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        this.webview.setOverScrollMode(2);
        settings.setCacheMode(2);
        this.webview.addJavascriptInterface(new testJsInterface(this.mContext), "testInterface");
        this.webview.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.widget.AuthDialogView.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v2, MotionEvent event) {
                return false;
            }
        });
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.webview.getLayoutParams();
        this.webview.setLayoutParams(layoutParams);
        this.mContext.getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        layoutParams.width = (int) (r2.widthPixels * 0.9d);
        layoutParams.height = -2;
        WebView.setWebContentsDebuggingEnabled(true);
        this.webview.loadUrl("file:///android_asset/index.html");
        this.webview.setWebViewClient(new AnonymousClass2());
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDestroy() {
        super.onDestroy();
        WebView webView = this.webview;
        if (webView != null) {
            webView.removeJavascriptInterface("testInterface");
            this.webview.destroy();
        }
        super.onDestroy();
    }
}
