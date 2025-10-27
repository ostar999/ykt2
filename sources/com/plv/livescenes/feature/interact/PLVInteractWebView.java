package com.plv.livescenes.feature.interact;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.foundationsdk.web.PLVWebview;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;
import com.plv.thirdpart.blankj.utilcode.util.BarUtils;
import com.plv.thirdpart.blankj.utilcode.util.LogUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Deprecated
/* loaded from: classes4.dex */
public class PLVInteractWebView extends PLVWebview implements com.easefun.polyv.livescenes.feature.interact.IPLVInteractJSBridge {
    private static final String LOAD_URL = "https://live.polyv.net/front/trivia-card/mobile?version=6";
    private static final String TAG = "PLVInteractWebView";
    private List<PLVInteractAppAbs> interactAppAbsList;

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
            AlertDialog alertDialogCreate = new AlertDialog.Builder(context).setMessage(str2).setPositiveButton("确认", new DialogInterface.OnClickListener() { // from class: com.plv.livescenes.feature.interact.PLVInteractWebView.PLVNoLeakWebChromeClient.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    jsResult.confirm();
                    dialogInterface.dismiss();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() { // from class: com.plv.livescenes.feature.interact.PLVInteractWebView.PLVNoLeakWebChromeClient.1
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

    public PLVInteractWebView(Context context) {
        this(context, null);
    }

    private void initView() {
        setWebChromeClient(new PLVNoLeakWebChromeClient(getContext()));
        PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(new PLVSocketMessageObserver.OnMessageListener() { // from class: com.plv.livescenes.feature.interact.PLVInteractWebView.1
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String str, String str2, String str3) {
                if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
                    return;
                }
                Iterator it = PLVInteractWebView.this.interactAppAbsList.iterator();
                while (it.hasNext()) {
                    ((PLVInteractAppAbs) it.next()).processSocketMsg(str3, str2);
                }
            }
        }, "message");
    }

    public void addInteractApp(PLVInteractAppAbs pLVInteractAppAbs) {
        if (pLVInteractAppAbs == null) {
            return;
        }
        pLVInteractAppAbs.setInteractJSBridge(this);
        this.interactAppAbsList.add(pLVInteractAppAbs);
    }

    @Override // com.plv.foundationsdk.web.PLVWebview, android.webkit.WebView
    public void destroy() {
        super.destroy();
        Iterator<PLVInteractAppAbs> it = this.interactAppAbsList.iterator();
        while (it.hasNext()) {
            it.next().destroy();
        }
        this.interactAppAbsList.clear();
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void loadWeb() {
        loadUrl(LOAD_URL);
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
    @SuppressLint({"ClickableViewAccessibility"})
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

    @Override // com.plv.livescenes.feature.interact.IPLVInteractJSBridge
    public void registerMsgReceiverFromJs(String str, BridgeHandler bridgeHandler) {
        registerHandler(str, bridgeHandler);
    }

    public void removeInteractApp(PLVInteractAppAbs pLVInteractAppAbs) {
        if (pLVInteractAppAbs != null) {
            this.interactAppAbsList.remove(pLVInteractAppAbs);
            pLVInteractAppAbs.destroy();
        }
    }

    @Override // com.plv.livescenes.feature.interact.IPLVInteractJSBridge
    public void sendMsgToJs(String str, String str2, CallBackFunction callBackFunction) {
        callHandler(str, str2, callBackFunction);
    }

    public PLVInteractWebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVInteractWebView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.interactAppAbsList = new ArrayList();
        initView();
    }
}
