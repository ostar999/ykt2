package com.plv.livescenes.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.web.PLVWebview;
import com.plv.livescenes.model.jsbridge.PLVJSBridgeSocketEventsVO;
import com.plv.livescenes.model.jsbridge.PLVJSBridgeSocketMessageVO;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.impl.PLVSocketMessageObserver;
import io.socket.client.Ack;

/* loaded from: classes5.dex */
public abstract class PLVSocketWebView extends PLVWebview {
    public String TAG;
    private PLVSocketMessageObserver.OnMessageListener onMessageListener;
    private PLVSocketMessageObserver.OnMessageListener onObserveListener;

    /* renamed from: com.plv.livescenes.webview.PLVSocketWebView$3, reason: invalid class name */
    public class AnonymousClass3 implements BridgeHandler {
        public AnonymousClass3() {
        }

        @Override // com.github.lzyzsd.jsbridge.BridgeHandler
        public void handler(String str, final CallBackFunction callBackFunction) {
            Log.d(PLVSocketWebView.this.TAG, "webViewSendSocketEvent " + str);
            PLVJSBridgeSocketMessageVO pLVJSBridgeSocketMessageVO = (PLVJSBridgeSocketMessageVO) PLVGsonUtil.fromJson(PLVJSBridgeSocketMessageVO.class, str);
            if (pLVJSBridgeSocketMessageVO == null) {
                return;
            }
            PLVSocketWrapper.getInstance().emit(pLVJSBridgeSocketMessageVO.event, pLVJSBridgeSocketMessageVO.value, new Ack() { // from class: com.plv.livescenes.webview.PLVSocketWebView.3.1
                @Override // io.socket.client.Ack
                public void call(final Object... objArr) {
                    PLVSocketWebView.this.post(new Runnable() { // from class: com.plv.livescenes.webview.PLVSocketWebView.3.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            String string;
                            try {
                                string = (String) objArr[0];
                            } catch (Exception e2) {
                                PLVCommonLog.e(PLVSocketWebView.this.TAG, "can not cast ack string " + objArr[0]);
                                e2.printStackTrace();
                                string = objArr[0].toString();
                            }
                            callBackFunction.onCallBack(string);
                        }
                    });
                }
            });
        }
    }

    public PLVSocketWebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private void initWebView() {
        PLVSocketMessageObserver socketObserver = PLVSocketWrapper.getInstance().getSocketObserver();
        PLVSocketMessageObserver.OnMessageListener onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.plv.livescenes.webview.PLVSocketWebView.1
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String str, String str2, String str3) {
                PLVCommonLog.d(PLVSocketWebView.this.TAG, "onMessageListener= " + String.format("%s, %s, %s", str, str2, str3));
                if (PLVSocketWebView.this.onInterceptMessage(str, str2, str3)) {
                    PLVCommonLog.d(PLVSocketWebView.this.TAG, "intercept message ");
                } else {
                    PLVSocketWebView.this.forwardSocketEvent(str, str3);
                }
            }
        };
        this.onMessageListener = onMessageListener;
        socketObserver.addOnMessageListener(onMessageListener);
        registerMsgReceiverFromJs(a.ap, new BridgeHandler() { // from class: com.plv.livescenes.webview.PLVSocketWebView.2
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                PLVCommonLog.d(PLVSocketWebView.this.TAG, "addObserveSocketEvent " + str);
                PLVJSBridgeSocketEventsVO pLVJSBridgeSocketEventsVO = (PLVJSBridgeSocketEventsVO) PLVGsonUtil.fromJson(PLVJSBridgeSocketEventsVO.class, str);
                if (pLVJSBridgeSocketEventsVO == null || pLVJSBridgeSocketEventsVO.getEvents() == null) {
                    return;
                }
                int i2 = 0;
                while (true) {
                    if (i2 >= pLVJSBridgeSocketEventsVO.getEvents().size()) {
                        break;
                    }
                    if (PLVEventConstant.Interact.NEWS_PUSH.equals(pLVJSBridgeSocketEventsVO.getEvents().get(i2))) {
                        pLVJSBridgeSocketEventsVO.getEvents().remove(i2);
                        PLVCommonLog.d(PLVSocketWebView.this.TAG, "remove observe needed newPush message ");
                        break;
                    }
                    i2++;
                }
                String[] strArr = (String[]) pLVJSBridgeSocketEventsVO.getEvents().toArray(new String[0]);
                PLVSocketWrapper.getInstance().addObserveSocketEvent(strArr);
                PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(PLVSocketWebView.this.onObserveListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.plv.livescenes.webview.PLVSocketWebView.2.1
                    @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
                    public void onMessage(String str2, String str3, String str4) {
                        PLVCommonLog.d(PLVSocketWebView.this.TAG, "onObserveListener= " + String.format("%s, %s, %s", str2, str3, str4));
                        if (PLVSocketWebView.this.onInterceptMessage(str2, str3, str4)) {
                            PLVCommonLog.d(PLVSocketWebView.this.TAG, "intercept message ");
                        } else {
                            PLVSocketWebView.this.forwardSocketEvent(str2, str4);
                        }
                    }
                }, strArr);
            }
        });
        registerMsgReceiverFromJs(a.aq, new AnonymousClass3());
    }

    public void forwardSocketEvent(String str, String str2) {
        sendMsgToJs(a.ao, "{\"event\":\"" + str + "\",\"value\":" + str2 + "}", new CallBackFunction() { // from class: com.plv.livescenes.webview.PLVSocketWebView.4
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String str3) {
                Log.d(PLVSocketWebView.this.TAG, "forwardSocketEvent callback: " + str3);
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onObserveListener);
    }

    public boolean onInterceptMessage(String str, String str2, String str3) {
        return false;
    }

    public void registerMsgReceiverFromJs(String str, BridgeHandler bridgeHandler) {
        registerHandler(str, bridgeHandler);
    }

    public void sendMsgToJs(String str, String str2, CallBackFunction callBackFunction) {
        callHandler(str, str2, callBackFunction);
    }

    public PLVSocketWebView(Context context) {
        this(context, null);
    }

    public PLVSocketWebView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.TAG = getClass().getSimpleName();
        initWebView();
    }
}
