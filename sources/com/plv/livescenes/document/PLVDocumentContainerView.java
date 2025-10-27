package com.plv.livescenes.document;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.document.IPLVDocumentContainerView;
import com.plv.livescenes.document.event.PLVAbsDocumentEvent;
import com.plv.livescenes.document.event.PLVChangeApplianceEvent;
import com.plv.livescenes.document.event.PLVChangeFontSizeEvent;
import com.plv.livescenes.document.event.PLVChangeLineWidthEvent;
import com.plv.livescenes.document.event.PLVChangeStrokeStyleEvent;
import com.plv.livescenes.document.event.PLVJoinSocketDataEvent;
import com.plv.livescenes.document.event.PLVRefreshMinimizeContainerDataEvent;
import com.plv.livescenes.document.event.PLVRefreshPptContainerTotalEvent;
import com.plv.livescenes.document.event.PLVSendSocketDataEvent;
import com.plv.livescenes.document.event.PLVStartEditTextEvent;
import com.plv.livescenes.document.event.PLVToggleOperationStatusEvent;
import com.plv.livescenes.document.event.PLVZoomPercentChangeEvent;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.livescenes.webview.PLVSocketWebView;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.user.PLVSocketUserConstant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class PLVDocumentContainerView extends PLVSocketWebView implements IPLVDocumentContainerView {
    private static final String DOCUMENT_URL = "https://player.polyv.net/resp/white-board-sdk/latest/web-view/small-class-mobile.html";
    private PLVSocketMessageObserver.OnMessageListener onMessageListener;
    private IPLVDocumentContainerView.OnReceiveEventListener onReceiveEventListener;
    private final Map<String, String> paramsMap;
    private static final List<String> RECEIVE_EVENT_TYPE_LIST = PLVSugarUtil.listOf(PLVRefreshMinimizeContainerDataEvent.TYPE, PLVRefreshPptContainerTotalEvent.TYPE, PLVStartEditTextEvent.TYPE, PLVToggleOperationStatusEvent.TYPE, PLVSendSocketDataEvent.TYPE, PLVChangeApplianceEvent.TYPE, PLVChangeStrokeStyleEvent.TYPE, PLVChangeLineWidthEvent.TYPE, PLVChangeFontSizeEvent.TYPE, PLVZoomPercentChangeEvent.TYPE);
    private static final List<String> OBSERVE_SOCKET_EVENT_TYPE_LIST = PLVSugarUtil.listOf("onSliceID", "onSliceOpen", "onSliceControl", "onSliceDraw", PLVEventConstant.Ppt.ON_SLICE_CLOSE_EVENT);
    private static final Handler MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper());

    public PLVDocumentContainerView(Context context) {
        super(context);
        this.paramsMap = new HashMap(8);
        initView();
    }

    private void initView() {
        observeSocketEvent();
    }

    private void observeSocketEvent() {
        PLVSocketMessageObserver socketObserver = PLVSocketWrapper.getInstance().getSocketObserver();
        PLVSocketMessageObserver.OnMessageListener onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.plv.livescenes.document.PLVDocumentContainerView.1
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String str, String str2, String str3) {
                if (PLVDocumentContainerView.OBSERVE_SOCKET_EVENT_TYPE_LIST.contains(str2)) {
                    PLVDocumentContainerView.this.sendEvent(new PLVJoinSocketDataEvent(str3));
                }
            }
        };
        this.onMessageListener = onMessageListener;
        socketObserver.addOnMessageListener(onMessageListener);
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void loadWeb() {
        if (this.paramsMap.isEmpty()) {
            loadUrl(DOCUMENT_URL);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : this.paramsMap.entrySet()) {
            sb.append("&");
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        loadUrl(DOCUMENT_URL + ("?" + sb.substring(1)));
    }

    @Override // com.plv.livescenes.webview.PLVSocketWebView, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void registerHandler() {
        PLVSugarUtil.foreach(RECEIVE_EVENT_TYPE_LIST, new PLVSugarUtil.Consumer<String>() { // from class: com.plv.livescenes.document.PLVDocumentContainerView.2
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
            public void accept(final String str) {
                PLVDocumentContainerView.this.registerHandler(str, new BridgeHandler() { // from class: com.plv.livescenes.document.PLVDocumentContainerView.2.1
                    @Override // com.github.lzyzsd.jsbridge.BridgeHandler
                    public void handler(String str2, CallBackFunction callBackFunction) {
                        if (PLVDocumentContainerView.this.onReceiveEventListener != null) {
                            PLVDocumentContainerView.this.onReceiveEventListener.onReceive(str, str2);
                        }
                    }
                });
            }
        });
    }

    @Override // com.plv.livescenes.document.IPLVDocumentContainerView
    public void sendEvent(final PLVAbsDocumentEvent pLVAbsDocumentEvent) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            callHandler(pLVAbsDocumentEvent.getEventType(), pLVAbsDocumentEvent.toJson(), null);
        } else {
            MAIN_THREAD_HANDLER.post(new Runnable() { // from class: com.plv.livescenes.document.PLVDocumentContainerView.3
                @Override // java.lang.Runnable
                public void run() {
                    PLVDocumentContainerView.this.callHandler(pLVAbsDocumentEvent.getEventType(), pLVAbsDocumentEvent.toJson(), null);
                }
            });
        }
    }

    @Override // com.plv.livescenes.document.IPLVDocumentContainerView
    public void setOnReceiveEventListener(IPLVDocumentContainerView.OnReceiveEventListener onReceiveEventListener) {
        this.onReceiveEventListener = onReceiveEventListener;
    }

    @Override // com.plv.livescenes.document.IPLVDocumentContainerView
    public IPLVDocumentContainerView setSessionId(String str) {
        this.paramsMap.put(PLVLinkMicManager.SESSION_ID, str);
        return this;
    }

    @Override // com.plv.livescenes.document.IPLVDocumentContainerView
    public IPLVDocumentContainerView setUserType(String str) {
        if (PLVSocketUserConstant.USERTYPE_SCSTUDENT.equals(str) || "teacher".equals(str)) {
            this.paramsMap.put(PLVLinkMicManager.USER_TYPE, str);
        }
        return this;
    }

    @Override // com.plv.livescenes.document.IPLVDocumentContainerView
    public IPLVDocumentContainerView setViewerId(String str) {
        this.paramsMap.put("userId", str);
        return this;
    }

    @Override // com.plv.livescenes.document.IPLVDocumentContainerView
    public IPLVDocumentContainerView setViewerName(String str) {
        this.paramsMap.put("userName", str);
        return this;
    }

    public PLVDocumentContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paramsMap = new HashMap(8);
        initView();
    }

    public PLVDocumentContainerView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.paramsMap = new HashMap(8);
        initView();
    }
}
