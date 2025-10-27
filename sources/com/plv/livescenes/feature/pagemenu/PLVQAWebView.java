package com.plv.livescenes.feature.pagemenu;

import android.content.Context;
import android.util.AttributeSet;
import com.easefun.polyv.livecommon.module.modules.interact.PLVInteractJSBridgeEventConst;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.business.web.PLVWebview;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVEventConstant;
import com.plv.thirdpart.blankj.utilcode.util.LogUtils;
import io.socket.client.Ack;
import javax.xml.transform.TransformerException;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class PLVQAWebView extends PLVWebview {
    private static final String CALLBACK_DATA = "成功收到web的回传消息";
    private static final String LOAD_URL = "https://s3.videocc.net/app-web-receive/index.html";
    private static final String TAG = "PLVQAWebView";

    /* renamed from: com.plv.livescenes.feature.pagemenu.PLVQAWebView$1, reason: invalid class name */
    public class AnonymousClass1 implements BridgeHandler {
        public AnonymousClass1() {
        }

        @Override // com.github.lzyzsd.jsbridge.BridgeHandler
        public void handler(String str, CallBackFunction callBackFunction) {
            PLVSocketWrapper.getInstance().emit("message", str, new Ack() { // from class: com.plv.livescenes.feature.pagemenu.PLVQAWebView.1.1
                @Override // io.socket.client.Ack
                public void call(Object... objArr) {
                    final String string = objArr[0].toString();
                    PLVQAWebView.this.post(new Runnable() { // from class: com.plv.livescenes.feature.pagemenu.PLVQAWebView.1.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            PLVQAWebView.this.callLaunchQ(string);
                        }
                    });
                }
            });
        }
    }

    public static class JSConst {
        private static final String DELETE_QA_ANSWER = "DELETE_QA_ANSWER";
        private static final String INIT_QA = "messageInit";
        private static final String LAUNCH_A = "LAUNCH_A";
        private static final String LAUNCH_Q = "LAUNCH_Q";
        private static final String LaunchQ = "launchQ";

        private JSConst() {
        }
    }

    public PLVQAWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void callDeleteQAAnswer(String str) {
        callHandler(PLVEventConstant.QuestionAndAnswer.EVENT_DELETE_QA_ANSWER, str, new CallBackFunction() { // from class: com.plv.livescenes.feature.pagemenu.PLVQAWebView.4
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String str2) throws JSONException, TransformerException, IllegalArgumentException {
                LogUtils.dTag(PLVQAWebView.TAG, PLVQAWebView.CALLBACK_DATA + str2);
            }
        });
    }

    public void callInit(String str) {
        callHandler(PLVInteractJSBridgeEventConst.MESSAGE_INIT, str, new CallBackFunction() { // from class: com.plv.livescenes.feature.pagemenu.PLVQAWebView.2
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String str2) throws JSONException, TransformerException, IllegalArgumentException {
                LogUtils.dTag(PLVQAWebView.TAG, PLVQAWebView.CALLBACK_DATA + str2);
            }
        });
    }

    public void callLaunchA(String str) {
        callHandler(PLVEventConstant.QuestionAndAnswer.EVENT_LAUNCH_A, str, new CallBackFunction() { // from class: com.plv.livescenes.feature.pagemenu.PLVQAWebView.3
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String str2) throws JSONException, TransformerException, IllegalArgumentException {
                LogUtils.dTag(PLVQAWebView.TAG, PLVQAWebView.CALLBACK_DATA + str2);
            }
        });
    }

    public void callLaunchQ(String str) {
        callHandler(PLVEventConstant.QuestionAndAnswer.EVENT_LAUNCH_Q, str, new CallBackFunction() { // from class: com.plv.livescenes.feature.pagemenu.PLVQAWebView.5
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String str2) throws JSONException, TransformerException, IllegalArgumentException {
                LogUtils.dTag(PLVQAWebView.TAG, PLVQAWebView.CALLBACK_DATA + str2);
            }
        });
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void loadWeb() {
        loadUrl(LOAD_URL);
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void registerHandler() {
        registerHandler("launchQ", new AnonymousClass1());
    }

    public PLVQAWebView(Context context) {
        super(context, null);
    }

    public PLVQAWebView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
