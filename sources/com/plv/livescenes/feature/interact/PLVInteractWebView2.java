package com.plv.livescenes.feature.interact;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.livescenes.webview.PLVSocketWebView;
import com.plv.socket.event.PLVEventConstant;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;
import com.plv.thirdpart.blankj.utilcode.util.BarUtils;
import com.plv.thirdpart.blankj.utilcode.util.LogUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class PLVInteractWebView2 extends PLVSocketWebView {
    public static String LOAD_URL = "https://websdk.videocc.net/interactions-receive-sdk-ui-webview/latest/index.html";
    public static final String WATCH_STATUS_LIVE = "0";
    public static final String WATCH_STATUS_PLAYBACK = "1";
    private boolean cardPushEnabled;
    private String watchStatus;

    public static class PLVNoLeakWebChromeClient extends WebChromeClient {
        private WeakReference<Context> wrContext;

        public PLVNoLeakWebChromeClient(Context context) {
            this.wrContext = new WeakReference<>(context);
        }

        @Override // android.webkit.WebChromeClient
        public boolean onJsConfirm(WebView webView, String str, String str2, final JsResult jsResult) {
            Context context = this.wrContext.get();
            if (context == null) {
                return true;
            }
            AlertDialog alertDialogCreate = new AlertDialog.Builder(context).setMessage(str2).setPositiveButton("确认", new DialogInterface.OnClickListener() { // from class: com.plv.livescenes.feature.interact.PLVInteractWebView2.PLVNoLeakWebChromeClient.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    jsResult.confirm();
                    dialogInterface.dismiss();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() { // from class: com.plv.livescenes.feature.interact.PLVInteractWebView2.PLVNoLeakWebChromeClient.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    jsResult.cancel();
                    dialogInterface.dismiss();
                }
            }).create();
            alertDialogCreate.setCanceledOnTouchOutside(false);
            alertDialogCreate.setCancelable(false);
            alertDialogCreate.show();
            return true;
        }
    }

    public PLVInteractWebView2(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private void initWebView() {
        setWebChromeClient(new PLVNoLeakWebChromeClient(getContext()));
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void loadWeb() {
        loadUrl((LOAD_URL + "?livePlayBack=" + this.watchStatus) + "&security=" + PLVSignCreator.getSecurity());
    }

    @Override // com.plv.livescenes.webview.PLVSocketWebView
    public boolean onInterceptMessage(String str, String str2, String str3) {
        if (this.cardPushEnabled || !PLVEventConstant.Interact.NEWS_PUSH.equals(str)) {
            return super.onInterceptMessage(str, str2, str3);
        }
        return true;
    }

    @Override // android.webkit.WebView, android.widget.AbsoluteLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        int size = View.MeasureSpec.getSize(i2);
        int size2 = View.MeasureSpec.getSize(i3);
        if (size2 == 0) {
            size2 = ScreenUtils.getScreenHeight();
            if (BarUtils.isNavBarVisible((Activity) getContext())) {
                size2 -= BarUtils.getNavBarHeight();
            }
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (action == 1 && !hasFocus()) {
            setFocusable(true);
            setFocusableInTouchMode(true);
            requestFocus();
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public boolean overrideUrlLoading(WebView webView, String str) {
        if (str.startsWith("yy://")) {
            return super.overrideUrlLoading(webView, str);
        }
        try {
            ActivityUtils.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Exception e2) {
            LogUtils.e(e2);
        }
        return true;
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void registerHandler() {
    }

    public void setCardPushEnabled(boolean z2) {
        this.cardPushEnabled = z2;
    }

    public void setWatchStatus(String str) {
        this.watchStatus = str;
    }

    public PLVInteractWebView2(Context context) {
        this(context, null);
    }

    public PLVInteractWebView2(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.cardPushEnabled = false;
        this.watchStatus = "0";
        initWebView();
    }
}
