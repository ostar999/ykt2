package com.github.lzyzsd.jsbridge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.WebView;
import cn.hutool.core.text.StrPool;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint({"SetJavaScriptEnabled"})
/* loaded from: classes3.dex */
public class BridgeWebView extends WebView implements WebViewJavascriptBridge {
    public static final String toLoadJs = "WebViewJavascriptBridge.js";
    private final String TAG;
    BridgeHandler defaultHandler;
    Map<String, BridgeHandler> messageHandlers;
    Map<String, CallBackFunction> responseCallbacks;
    private List<Message> startupMessage;
    private long uniqueId;

    public BridgeWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "BridgeWebView";
        this.responseCallbacks = new HashMap();
        this.messageHandlers = new HashMap();
        this.defaultHandler = new DefaultHandler();
        this.startupMessage = new ArrayList();
        this.uniqueId = 0L;
        init();
    }

    private void doSend(String str, String str2, CallBackFunction callBackFunction) {
        Message message = new Message();
        if (!TextUtils.isEmpty(str2)) {
            message.setData(str2);
        }
        if (callBackFunction != null) {
            StringBuilder sb = new StringBuilder();
            long j2 = this.uniqueId + 1;
            this.uniqueId = j2;
            sb.append(j2);
            sb.append(StrPool.UNDERLINE);
            sb.append(SystemClock.currentThreadTimeMillis());
            String str3 = String.format("JAVA_CB_%s", sb.toString());
            this.responseCallbacks.put(str3, callBackFunction);
            message.setCallbackId(str3);
        }
        if (!TextUtils.isEmpty(str)) {
            message.setHandlerName(str);
        }
        queueMessage(message);
    }

    private void init() {
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        getSettings().setJavaScriptEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);
        setWebViewClient(generateBridgeWebViewClient());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queueMessage(Message message) {
        List<Message> list = this.startupMessage;
        if (list != null) {
            list.add(message);
        } else {
            dispatchMessage(message);
        }
    }

    public void callHandler(String str, String str2, CallBackFunction callBackFunction) {
        doSend(str, str2, callBackFunction);
    }

    public void dispatchMessage(Message message) {
        String str = String.format("javascript:WebViewJavascriptBridge._handleMessageFromNative('%s');", message.toJson().replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2").replaceAll("(?<=[^\\\\])(\")", "\\\\\""));
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            loadUrl(str);
        }
    }

    public void flushMessageQueue() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            loadUrl("javascript:WebViewJavascriptBridge._fetchQueue();", new CallBackFunction() { // from class: com.github.lzyzsd.jsbridge.BridgeWebView.1
                @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                public void onCallBack(String str) {
                    try {
                        List<Message> arrayList = Message.toArrayList(str);
                        if (arrayList == null || arrayList.size() == 0) {
                            return;
                        }
                        for (int i2 = 0; i2 < arrayList.size(); i2++) {
                            Message message = arrayList.get(i2);
                            String responseId = message.getResponseId();
                            if (TextUtils.isEmpty(responseId)) {
                                final String callbackId = message.getCallbackId();
                                CallBackFunction callBackFunction = !TextUtils.isEmpty(callbackId) ? new CallBackFunction() { // from class: com.github.lzyzsd.jsbridge.BridgeWebView.1.1
                                    @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                                    public void onCallBack(String str2) {
                                        Message message2 = new Message();
                                        message2.setResponseId(callbackId);
                                        message2.setResponseData(str2);
                                        BridgeWebView.this.queueMessage(message2);
                                    }
                                } : new CallBackFunction() { // from class: com.github.lzyzsd.jsbridge.BridgeWebView.1.2
                                    @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                                    public void onCallBack(String str2) {
                                    }
                                };
                                BridgeHandler bridgeHandler = !TextUtils.isEmpty(message.getHandlerName()) ? BridgeWebView.this.messageHandlers.get(message.getHandlerName()) : BridgeWebView.this.defaultHandler;
                                if (bridgeHandler != null) {
                                    bridgeHandler.handler(message.getData(), callBackFunction);
                                }
                            } else {
                                BridgeWebView.this.responseCallbacks.get(responseId).onCallBack(message.getResponseData());
                                BridgeWebView.this.responseCallbacks.remove(responseId);
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        }
    }

    public BridgeWebViewClient generateBridgeWebViewClient() {
        return new BridgeWebViewClient(this);
    }

    public List<Message> getStartupMessage() {
        return this.startupMessage;
    }

    public void handlerReturnData(String str) {
        String functionFromReturnUrl = BridgeUtil.getFunctionFromReturnUrl(str);
        CallBackFunction callBackFunction = this.responseCallbacks.get(functionFromReturnUrl);
        String dataFromReturnUrl = BridgeUtil.getDataFromReturnUrl(str);
        if (callBackFunction != null) {
            callBackFunction.onCallBack(dataFromReturnUrl);
            this.responseCallbacks.remove(functionFromReturnUrl);
        }
    }

    public void loadUrl(String str, CallBackFunction callBackFunction) {
        loadUrl(str);
        this.responseCallbacks.put(BridgeUtil.parseFunctionName(str), callBackFunction);
    }

    public void registerHandler(String str, BridgeHandler bridgeHandler) {
        if (bridgeHandler != null) {
            this.messageHandlers.put(str, bridgeHandler);
        }
    }

    @Override // com.github.lzyzsd.jsbridge.WebViewJavascriptBridge
    public void send(String str) {
        send(str, null);
    }

    public void setDefaultHandler(BridgeHandler bridgeHandler) {
        this.defaultHandler = bridgeHandler;
    }

    public void setStartupMessage(List<Message> list) {
        this.startupMessage = list;
    }

    @Override // com.github.lzyzsd.jsbridge.WebViewJavascriptBridge
    public void send(String str, CallBackFunction callBackFunction) {
        doSend(null, str, callBackFunction);
    }

    public BridgeWebView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.TAG = "BridgeWebView";
        this.responseCallbacks = new HashMap();
        this.messageHandlers = new HashMap();
        this.defaultHandler = new DefaultHandler();
        this.startupMessage = new ArrayList();
        this.uniqueId = 0L;
        init();
    }

    public BridgeWebView(Context context) {
        super(context);
        this.TAG = "BridgeWebView";
        this.responseCallbacks = new HashMap();
        this.messageHandlers = new HashMap();
        this.defaultHandler = new DefaultHandler();
        this.startupMessage = new ArrayList();
        this.uniqueId = 0L;
        init();
    }
}
