package com.plv.livescenes.document;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.easefun.polyv.livescenes.document.model.PLVSPPTJsModel;
import com.easefun.polyv.livescenes.document.model.PLVSPPTPaintStatus;
import com.easefun.polyv.livescenes.document.model.PLVSPPTStatus;
import com.easefun.polyv.livescenes.log.ppt.PolyvPPTElog;
import com.easefun.polyv.livescenes.streamer.transfer.PLVSStreamerInnerDataTransfer;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.PLVELogsService;
import com.plv.foundationsdk.log.elog.logcode.ppt.PLVErrorCodePPTWeb;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.web.PLVWebMessageProcessor;
import com.plv.foundationsdk.web.PLVWebview;
import com.plv.livescenes.log.PLVElogEntityCreator;
import com.plv.livescenes.log.ppt.PLVPPTElog;
import com.plv.livescenes.streamer.transfer.PLVStreamerInnerDataTransfer;

/* loaded from: classes4.dex */
public class PLVDocumentWebProcessor extends PLVWebMessageProcessor<CloudClassJSCallback> {
    public static final String AUTHORIZATION_PPT_PAINT = "setPaintPermission";
    public static final String CHANGEPPT = "changePPT";
    public static final String CHANGEPPTPAGE = "changePPTPage";
    public static final String CHANGE_COLOR = "changeColor";
    public static final String DELETEALLPAINT = "deleteAllPaint";
    public static final String EDIT_TEXT_CONTENT = "toEditText";
    public static final String ERASE_STATUS = "toDelete";
    public static final String FILLEDITTEXT = "changeTextContent";
    public static final String GETUSER = "pptLoaded";
    public static final String GET_PPT_IMAGES = "pptMiniImages";
    public static final String ONSLICESTART = "sliceStart";
    public static final String PPTSTATUSCHANGE = "pptStatusChange";
    public static final String PPTTHUMBNAIL = "pptThumbnail";
    public static final String SEND_SOCKET_EVENT = "sendSocketEvent";
    public static final String SETDRAWTYPE = "setDrawType";
    public static final String SETPAINTSTATUS = "setPaintStatus";
    public static final String SETUSER = "setUser";
    private static final String TAG = "PLVSDocumentWebProcesso";
    public static final String TO_ZOOM_RESET = "toZoomReset";
    public static final String UPDATE_PPT = "refreshPPT";
    public static final String ZOOM_CHANGE = "zoomChange";
    private boolean recoverFlag;

    public static abstract class CloudClassJSCallback {
        public void getEditContent(PLVSPPTPaintStatus pLVSPPTPaintStatus) {
        }

        public void getPPTChangeStatus(PLVSPPTStatus pLVSPPTStatus) {
        }

        public void getPPTImagesList(PLVSPPTJsModel pLVSPPTJsModel) {
        }

        public void getUser(CallBackFunction callBackFunction) {
        }

        public void onZoomChange(String str) {
        }

        public void refreshPPT(String str) {
        }
    }

    public PLVDocumentWebProcessor(@NonNull PLVWebview pLVWebview) {
        super(pLVWebview);
        this.recoverFlag = false;
        this.protocols.add("refreshPPT");
        this.protocols.add("changeColor");
        this.protocols.add("toDelete");
        this.protocols.add(GET_PPT_IMAGES);
        this.protocols.add(DELETEALLPAINT);
        this.protocols.add(GETUSER);
        this.protocols.add("setPaintStatus");
        this.protocols.add("setPaintPermission");
        this.protocols.add(SETDRAWTYPE);
        this.protocols.add("changePPTPage");
        this.protocols.add("setUser");
        this.protocols.add(CHANGEPPT);
        this.protocols.add(ONSLICESTART);
        this.protocols.add(FILLEDITTEXT);
        this.protocols.add(TO_ZOOM_RESET);
    }

    @Override // com.plv.foundationsdk.web.PLVWebMessageProcessor
    public void callMessage(final String str, String str2) {
        if (this.protocols.contains(str)) {
            PLVCommonLog.d(TAG, "callMessage->type=" + str + " message=" + str2);
            this.webview.callHandler(str, str2, new CallBackFunction() { // from class: com.plv.livescenes.document.PLVDocumentWebProcessor.1
                @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                public void onCallBack(String str3) {
                    PLVCommonLog.d(PLVDocumentWebProcessor.TAG, "call handler data ：" + str3);
                    if (!PLVDocumentWebProcessor.CHANGEPPT.equals(str) || TextUtils.isEmpty(str3)) {
                        return;
                    }
                    PLVSPPTJsModel pLVSPPTJsModel = (PLVSPPTJsModel) PLVGsonUtil.fromJson(PLVSPPTJsModel.class, str3);
                    if (pLVSPPTJsModel != null) {
                        if (pLVSPPTJsModel.getSmallImages() == null || ((PLVWebMessageProcessor) PLVDocumentWebProcessor.this).callback == null) {
                            return;
                        }
                        ((CloudClassJSCallback) ((PLVWebMessageProcessor) PLVDocumentWebProcessor.this).callback).getPPTImagesList(pLVSPPTJsModel);
                        return;
                    }
                    PLVELogsService.getInstance().addStaticsLog(PLVElogEntityCreator.createLiveEntity(PLVErrorCodePPTWeb.class, 3, "changePPT data:" + str3));
                }
            });
        }
    }

    @Override // com.plv.foundationsdk.web.PLVWebMessageProcessor
    public void destroy() {
        PLVWebview pLVWebview = this.webview;
        if (pLVWebview != null) {
            pLVWebview.removeAllViews();
            this.webview.destroy();
            this.webview = null;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof PLVDocumentWebProcessor) && getWebview().equals(((PLVDocumentWebProcessor) obj).getWebview());
    }

    public int hashCode() {
        return getWebview().hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.plv.foundationsdk.web.PLVWebMessageProcessor
    public void registerJSHandler(final CloudClassJSCallback cloudClassJSCallback) {
        this.callback = cloudClassJSCallback;
        this.webview.registerHandler("sendSocketEvent", new BridgeHandler() { // from class: com.plv.livescenes.document.PLVDocumentWebProcessor.2
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                CloudClassJSCallback cloudClassJSCallback2 = cloudClassJSCallback;
                if (cloudClassJSCallback2 != null) {
                    cloudClassJSCallback2.refreshPPT(str);
                }
                PLVELogsService.getInstance().addStaticsLog(PolyvPPTElog.class, PLVPPTElog.PPTEvent.PPT_RECEIVE_WEB_MESSAGE, "receive message :" + str, new String[0]);
            }
        });
        this.webview.registerHandler(GETUSER, new BridgeHandler() { // from class: com.plv.livescenes.document.PLVDocumentWebProcessor.3
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                CloudClassJSCallback cloudClassJSCallback2 = cloudClassJSCallback;
                if (cloudClassJSCallback2 != null) {
                    cloudClassJSCallback2.getUser(callBackFunction);
                }
            }
        });
        this.webview.registerHandler("pptStatusChange", new BridgeHandler() { // from class: com.plv.livescenes.document.PLVDocumentWebProcessor.4
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                PLVCommonLog.d(PLVDocumentWebProcessor.TAG, "PPTSTATUSCHANGE data:" + str);
                PLVSPPTStatus pLVSPPTStatus = (PLVSPPTStatus) PLVGsonUtil.fromJson(PLVSPPTStatus.class, str);
                if (pLVSPPTStatus == null) {
                    PLVELogsService.getInstance().addStaticsLog(PLVElogEntityCreator.createLiveEntity(PLVErrorCodePPTWeb.class, 2, "pptStatusChange data:" + str));
                    return;
                }
                if (PLVStreamerInnerDataTransfer.getInstance().isLiveStreamingWhenLogin() && !PLVDocumentWebProcessor.this.recoverFlag) {
                    PLVDocumentWebProcessor.this.recoverFlag = true;
                    return;
                }
                PLVSStreamerInnerDataTransfer.getInstance().setPPTStatusForOnSliceStartEvent(pLVSPPTStatus);
                CloudClassJSCallback cloudClassJSCallback2 = cloudClassJSCallback;
                if (cloudClassJSCallback2 != null) {
                    cloudClassJSCallback2.getPPTChangeStatus(pLVSPPTStatus);
                }
                PLVELogsService.getInstance().addStaticsLog(PolyvPPTElog.class, PLVPPTElog.PPTEvent.PPT_RECEIVE_WEB_MESSAGE, "receive message :" + str, new String[0]);
            }
        });
        this.webview.registerHandler(EDIT_TEXT_CONTENT, new BridgeHandler() { // from class: com.plv.livescenes.document.PLVDocumentWebProcessor.5
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                PLVCommonLog.d(PLVDocumentWebProcessor.TAG, "EDIT_TEXT_CONTENT");
                PLVSPPTPaintStatus pLVSPPTPaintStatus = (PLVSPPTPaintStatus) PLVGsonUtil.fromJson(PLVSPPTPaintStatus.class, str);
                if (pLVSPPTPaintStatus != null) {
                    CloudClassJSCallback cloudClassJSCallback2 = cloudClassJSCallback;
                    if (cloudClassJSCallback2 != null) {
                        cloudClassJSCallback2.getEditContent(pLVSPPTPaintStatus);
                        return;
                    }
                    return;
                }
                PLVELogsService.getInstance().addStaticsLog(PLVElogEntityCreator.createLiveEntity(PLVErrorCodePPTWeb.class, 3, "changeTextContent data:" + str));
            }
        });
        this.webview.registerHandler(PPTTHUMBNAIL, new BridgeHandler() { // from class: com.plv.livescenes.document.PLVDocumentWebProcessor.6
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                CloudClassJSCallback cloudClassJSCallback2;
                PLVCommonLog.d(PLVDocumentWebProcessor.TAG, "PPTTHUMBNAIL：" + str);
                PLVSPPTJsModel pLVSPPTJsModel = (PLVSPPTJsModel) PLVGsonUtil.fromJson(PLVSPPTJsModel.class, str);
                if (pLVSPPTJsModel == null || pLVSPPTJsModel.getSmallImages() == null || (cloudClassJSCallback2 = cloudClassJSCallback) == null) {
                    return;
                }
                cloudClassJSCallback2.getPPTImagesList(pLVSPPTJsModel);
            }
        });
        this.webview.registerHandler(ZOOM_CHANGE, new BridgeHandler() { // from class: com.plv.livescenes.document.PLVDocumentWebProcessor.7
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                CloudClassJSCallback cloudClassJSCallback2 = cloudClassJSCallback;
                if (cloudClassJSCallback2 != null) {
                    cloudClassJSCallback2.onZoomChange(str);
                }
            }
        });
    }
}
