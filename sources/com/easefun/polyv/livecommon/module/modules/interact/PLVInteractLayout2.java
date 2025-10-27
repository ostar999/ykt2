package com.easefun.polyv.livecommon.module.modules.interact;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.config.PLVLiveScene;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.utils.PLVWebUtils;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager;
import com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.feature.interact.PLVInteractWebView2;
import com.plv.livescenes.feature.interact.vo.PLVInteractNativeAppParams;
import com.plv.livescenes.model.interact.PLVWebviewUpdateAppStatusVO;
import com.plv.socket.event.interact.PLVCallAppEvent;
import com.plv.socket.event.interact.PLVShowPushCardEvent;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVInteractLayout2 extends FrameLayout implements IPLVInteractLayout {
    private static final List<String> JS_HANDLER = PLVSugarUtil.listOf("getNativeAppParamsInfo", PLVInteractJSBridgeEventConst.V2_CLOSE_WEB_VIEW, "linkClick", PLVInteractJSBridgeEventConst.V2_WEB_VIEW_UPDATE_APP_STATUS, PLVInteractJSBridgeEventConst.V2_SHOW_WEB_VIEW, PLVInteractJSBridgeEventConst.V2_LOCK_TO_PORTRAIT, PLVInteractJSBridgeEventConst.V2_CALL_APP_EVENT);
    private static final String TAG = "PLVInteractLayout2";
    private PLVInsideWebViewLayout insideWebViewLayout;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private PLVLiveScene liveScene;
    private OnOpenInsideWebViewListener onOpenInsideWebViewListener;
    private PLVInteractWebView2 plvlcInteractWeb;

    public interface OnOpenInsideWebViewListener {
        void onClosed();

        OpenUrlParam onOpenWithParam(boolean isLandscape);
    }

    public static class OpenUrlParam {
        private ViewGroup containerView;
        private int portraitTop;

        public OpenUrlParam(int portraitTop, ViewGroup containerView) {
            this.portraitTop = portraitTop;
            this.containerView = containerView;
        }

        public ViewGroup getContainerView() {
            return this.containerView;
        }

        public int getPortraitTop() {
            return this.portraitTop;
        }

        public void setContainerView(ViewGroup containerView) {
            this.containerView = containerView;
        }

        public void setPortraitTop(int portraitTop) {
            this.portraitTop = portraitTop;
        }
    }

    public PLVInteractLayout2(@NonNull Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getNativeAppPramsInfo() {
        return this.liveRoomDataManager != null ? PLVGsonUtil.toJsonSimple(new PLVInteractNativeAppParams().setAppId(this.liveRoomDataManager.getConfig().getAccount().getAppId()).setAppSecret(this.liveRoomDataManager.getConfig().getAccount().getAppSecret()).setSessionId(this.liveRoomDataManager.getSessionId()).setChannelInfo(new PLVInteractNativeAppParams.ChannelInfoDTO().setChannelId(this.liveRoomDataManager.getConfig().getChannelId()).setRoomId(this.liveRoomDataManager.getConfig().getChannelId())).setUserInfo(new PLVInteractNativeAppParams.UserInfoDTO().setUserId(this.liveRoomDataManager.getConfig().getUser().getViewerId()).setNick(this.liveRoomDataManager.getConfig().getUser().getViewerName()).setPic(this.liveRoomDataManager.getConfig().getUser().getViewerAvatar()))) : "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlerJsCall(String event, String param, CallBackFunction callBackFunction) {
        event.hashCode();
        switch (event) {
            case "lockToPortrait":
                lockToPortrait();
                break;
            case "linkClick":
                PLVWebUtils.openWebLink(param, getContext());
                break;
            case "webViewUpdateAppStatus":
                processWebViewUpdateAppStatus(param, callBackFunction);
                break;
            case "closeWebView":
                processWebViewVisibility(true);
                break;
            case "callAppEvent":
                processCallAppEvent(param, callBackFunction);
                break;
            case "showWebView":
                processWebViewVisibility(false);
                break;
            case "getNativeAppParamsInfo":
                processGetNativeAppParamsInfo(param, callBackFunction);
                break;
        }
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plv_interact_layout_2, (ViewGroup) this, true);
        this.plvlcInteractWeb = (PLVInteractWebView2) findViewById(R.id.plvlc_interact_web);
        setVisibility(4);
        for (final String str : JS_HANDLER) {
            this.plvlcInteractWeb.registerMsgReceiverFromJs(str, new BridgeHandler() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout2.1
                @Override // com.github.lzyzsd.jsbridge.BridgeHandler
                public void handler(String param, CallBackFunction callBackFunction) {
                    Log.d(PLVInteractLayout2.TAG, str + ", param= " + param);
                    PLVInteractLayout2.this.handlerJsCall(str, param, callBackFunction);
                }
            });
        }
        PLVInsideWebViewLayout pLVInsideWebViewLayout = new PLVInsideWebViewLayout(getContext());
        this.insideWebViewLayout = pLVInsideWebViewLayout;
        pLVInsideWebViewLayout.setOnDrawerStateChangeListener(new PLVMenuDrawer.OnDrawerStateChangeListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout2.2
            @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer.OnDrawerStateChangeListener
            public void onDrawerSlide(float openRatio, int offsetPixels) {
            }

            @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer.OnDrawerStateChangeListener
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState != 0 || PLVInteractLayout2.this.onOpenInsideWebViewListener == null) {
                    return;
                }
                PLVInteractLayout2.this.onOpenInsideWebViewListener.onClosed();
            }
        });
    }

    private void lockToPortrait() {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null) {
            return;
        }
        if (topActivity.getRequestedOrientation() != 1) {
            PLVOrientationManager.getInstance().unlockOrientation();
            PLVOrientationManager.getInstance().setPortrait(topActivity);
        }
        PLVOrientationManager.getInstance().lockOrientation();
    }

    private void observeLiveData() {
        this.liveRoomDataManager.getSessionIdLiveData().observe((LifecycleOwner) getContext(), new Observer<String>() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout2.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable String sessionId) {
                if (TextUtils.isEmpty(sessionId)) {
                    return;
                }
                PLVInteractLayout2.this.plvlcInteractWeb.sendMsgToJs(PLVInteractJSBridgeEventConst.V2_UPDATE_NATIVE_APP_PARAMS_INFO, PLVInteractLayout2.this.getNativeAppPramsInfo(), new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout2.6.1
                    @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                    public void onCallBack(String s2) {
                        PLVCommonLog.d(PLVInteractLayout2.TAG, "updateNativeAppParamsInfo " + s2);
                    }
                });
            }
        });
    }

    private void processCallAppEvent(String param, CallBackFunction callBackFunction) {
        PLVCallAppEvent pLVCallAppEvent = (PLVCallAppEvent) PLVGsonUtil.fromJson(PLVCallAppEvent.class, param);
        if (pLVCallAppEvent != null && pLVCallAppEvent.isOpenLinkEvent()) {
            if (!pLVCallAppEvent.isInsideOpen()) {
                if (pLVCallAppEvent.isOutsideOpen()) {
                    PLVOutsideWebViewActivity.start(getContext(), pLVCallAppEvent.getUrl());
                }
            } else {
                OnOpenInsideWebViewListener onOpenInsideWebViewListener = this.onOpenInsideWebViewListener;
                if (onOpenInsideWebViewListener != null) {
                    OpenUrlParam openUrlParamOnOpenWithParam = onOpenInsideWebViewListener.onOpenWithParam(PLVScreenUtils.isLandscape(getContext()));
                    this.insideWebViewLayout.open(openUrlParamOnOpenWithParam.portraitTop, pLVCallAppEvent.getUrl(), openUrlParamOnOpenWithParam.containerView);
                }
            }
        }
    }

    private void processGetNativeAppParamsInfo(String param, CallBackFunction callBackFunction) {
        String nativeAppPramsInfo = getNativeAppPramsInfo();
        PLVCommonLog.d(TAG, "processGetNativeAppParamsInfo= " + nativeAppPramsInfo);
        callBackFunction.onCallBack(nativeAppPramsInfo);
    }

    private void processWebViewUpdateAppStatus(String data, CallBackFunction callBackFunction) {
        this.liveRoomDataManager.getInteractStatusData().postValue((PLVWebviewUpdateAppStatusVO) PLVGsonUtil.fromJson(PLVWebviewUpdateAppStatusVO.class, data));
    }

    private void processWebViewVisibility(boolean close) {
        Log.d(TAG, "processWebViewVisibility close: " + close);
        setVisibility(close ? 4 : 0);
        if (close) {
            PLVOrientationManager.getInstance().unlockOrientation();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public void destroy() {
        PLVInteractWebView2 pLVInteractWebView2 = this.plvlcInteractWeb;
        if (pLVInteractWebView2 != null) {
            pLVInteractWebView2.removeAllViews();
            ViewParent parent = this.plvlcInteractWeb.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.plvlcInteractWeb);
            }
            this.plvlcInteractWeb.destroy();
            this.plvlcInteractWeb = null;
        }
        PLVInsideWebViewLayout pLVInsideWebViewLayout = this.insideWebViewLayout;
        if (pLVInsideWebViewLayout != null) {
            pLVInsideWebViewLayout.destroy();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public void init(IPLVLiveRoomDataManager liveRoomDataManager) {
        init(liveRoomDataManager, null);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public boolean onBackPress() {
        PLVInsideWebViewLayout pLVInsideWebViewLayout = this.insideWebViewLayout;
        if (pLVInsideWebViewLayout != null && pLVInsideWebViewLayout.onBackPressed()) {
            return true;
        }
        if (getVisibility() != 0) {
            return false;
        }
        setVisibility(4);
        return true;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public void onCallDynamicFunction(String event) {
        this.plvlcInteractWeb.sendMsgToJs(PLVInteractJSBridgeEventConst.V2_APP_CALL_WEB_VIEW_EVENT, event, new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout2.5
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String s2) {
                PLVCommonLog.d(PLVInteractLayout2.TAG, "appCallWebViewEvent " + s2);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public void setOnOpenInsideWebViewListener(OnOpenInsideWebViewListener onOpenInsideWebViewListener) {
        this.onOpenInsideWebViewListener = onOpenInsideWebViewListener;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public void showBulletin() {
        this.plvlcInteractWeb.sendMsgToJs(PLVInteractJSBridgeEventConst.V2_APP_CALL_WEB_VIEW_EVENT, "{\"event\" : \"SHOW_BULLETIN\"}", new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout2.3
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String s2) {
                PLVCommonLog.d(PLVInteractLayout2.TAG, "appCallWebViewEvent " + s2);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public void showCardPush(PLVShowPushCardEvent showPushCardEvent) {
        this.plvlcInteractWeb.sendMsgToJs(PLVInteractJSBridgeEventConst.V2_APP_CALL_WEB_VIEW_EVENT, PLVGsonUtil.toJsonSimple(showPushCardEvent), new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout2.4
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String s2) {
                PLVCommonLog.d(PLVInteractLayout2.TAG, "appCallWebViewEvent " + s2);
            }
        });
    }

    public PLVInteractLayout2(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public void init(IPLVLiveRoomDataManager liveRoomDataManager, @Nullable PLVLiveScene scene) {
        this.liveScene = scene;
        this.liveRoomDataManager = liveRoomDataManager;
        this.plvlcInteractWeb.setCardPushEnabled(true);
        this.plvlcInteractWeb.setWatchStatus(liveRoomDataManager.getConfig().isLive() ? "0" : "1");
        this.plvlcInteractWeb.loadWeb();
        observeLiveData();
    }

    public PLVInteractLayout2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
