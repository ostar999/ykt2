package com.easefun.polyv.livecloudclass.modules.ppt;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.businesssdk.api.common.ppt.IPolyvPPTView;
import com.easefun.polyv.businesssdk.api.common.ppt.PolyvLivePPTProcessor;
import com.easefun.polyv.businesssdk.api.common.ppt.PolyvPPTVodProcessor;
import com.easefun.polyv.businesssdk.api.common.ppt.PolyvPPTWebView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView;
import com.easefun.polyv.livecommon.module.modules.ppt.contract.IPLVPPTContract;
import com.easefun.polyv.livecommon.module.modules.ppt.presenter.PLVPPTPresenter;
import com.easefun.polyv.livecommon.ui.widget.PLVPlaceHolderView;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.easefun.polyv.livescenes.log.ppt.PolyvPPTElog;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.business.api.common.ppt.PLVLivePPTProcessor;
import com.plv.business.api.common.ppt.vo.PLVPPTLocalCacheVO;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.web.PLVWebview;
import com.plv.livescenes.document.model.PLVPPTStatus;
import com.plv.livescenes.feature.interact.vo.PLVInteractNativeAppParams;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.livescenes.log.PLVELogSender;
import com.plv.livescenes.log.ppt.PLVPPTElog;

/* loaded from: classes3.dex */
public class PLVLCPPTView extends FrameLayout implements IPLVPPTContract.IPLVPPTView, IPLVLCPPTView {
    private static final String TAG = "PLVLCPPTView";
    private int curPlaybackPosition;
    private boolean isLowLatencyWatch;
    private boolean isRtcWatch;
    private IPLVLCPPTView.OnPLVLCLivePPTViewListener onLivePPTViewListener;
    private IPLVLCPPTView.OnPLVLCPlaybackPPTViewListener onPlaybackPPTViewListener;
    private PLVPlaceHolderView pptPlaceHolderView;

    @Nullable
    private PolyvPPTWebView pptWebView;
    private IPLVPPTContract.IPLVPPTPresenter presenter;

    public PLVLCPPTView(Context context) {
        this(context, null);
    }

    private void addLiveJSEventListener() {
        PolyvPPTWebView polyvPPTWebView = this.pptWebView;
        if (polyvPPTWebView == null) {
            return;
        }
        PolyvLivePPTProcessor polyvLivePPTProcessor = new PolyvLivePPTProcessor(polyvPPTWebView);
        polyvLivePPTProcessor.registerJSHandler((PolyvLivePPTProcessor) new PLVLivePPTProcessor.LiveJSCallback() { // from class: com.easefun.polyv.livecloudclass.modules.ppt.PLVLCPPTView.5
            @Override // com.plv.business.api.common.ppt.PLVLivePPTProcessor.LiveJSCallback
            public void backTopActivity() {
                if (PLVLCPPTView.this.onLivePPTViewListener != null) {
                    PLVLCPPTView.this.onLivePPTViewListener.onLiveBackTopActivity();
                }
            }

            @Override // com.plv.business.api.common.ppt.PLVLivePPTProcessor.LiveJSCallback
            public void brushPPT(String str) {
                PLVLCPPTView.this.presenter.sendPPTBrushMsg(str);
            }

            @Override // com.plv.business.api.common.ppt.PLVLivePPTProcessor.LiveJSCallback
            public void onPPTStatusChange(String str) {
                PLVPPTStatus pLVPPTStatus = (PLVPPTStatus) PLVGsonUtil.fromJson(PLVPPTStatus.class, str);
                if (pLVPPTStatus == null || pLVPPTStatus.getMaxTeacherOp() == null || PLVLCPPTView.this.onLivePPTViewListener == null) {
                    return;
                }
                PLVLCPPTView.this.onLivePPTViewListener.onLivePPTStatusChange(pLVPPTStatus);
            }

            @Override // com.plv.business.api.common.ppt.PLVLivePPTProcessor.LiveJSCallback
            public void reloadVideo() {
                if (PLVLCPPTView.this.onLivePPTViewListener != null) {
                    PLVLCPPTView.this.onLivePPTViewListener.onLiveRestartVideoView();
                }
            }

            @Override // com.plv.business.api.common.ppt.PLVLivePPTProcessor.LiveJSCallback
            public void screenBSSwitch(boolean z2) {
            }

            @Override // com.plv.business.api.common.ppt.PLVLivePPTProcessor.LiveJSCallback
            public void screenPLSwitch(boolean z2) {
                if (PLVLCPPTView.this.onLivePPTViewListener != null) {
                    PLVLCPPTView.this.onLivePPTViewListener.onLiveChangeToLandscape(z2);
                }
            }

            @Override // com.plv.business.api.common.ppt.PLVLivePPTProcessor.LiveJSCallback
            public void startOrPause(boolean z2) {
                if (PLVLCPPTView.this.onLivePPTViewListener != null) {
                    PLVLCPPTView.this.onLivePPTViewListener.onLiveStartOrPauseVideoView(z2);
                }
            }
        });
        this.pptWebView.registerProcessor(polyvLivePPTProcessor);
    }

    private void addPlaybackJSEventListener() {
        PolyvPPTWebView polyvPPTWebView = this.pptWebView;
        if (polyvPPTWebView == null) {
            return;
        }
        PolyvPPTVodProcessor polyvPPTVodProcessor = new PolyvPPTVodProcessor(polyvPPTWebView);
        polyvPPTVodProcessor.registerJSHandler((PolyvPPTVodProcessor) new PolyvPPTVodProcessor.PolyvVideoPPTCallback() { // from class: com.easefun.polyv.livecloudclass.modules.ppt.PLVLCPPTView.4
            @Override // com.plv.business.api.common.ppt.PLVPPTVodProcessor.PLVVideoPPTCallback
            public void callVideoDuration(CallBackFunction callBackFunction) {
                if (PLVLCPPTView.this.onPlaybackPPTViewListener != null) {
                    String str = "{\"time\":" + PLVLCPPTView.this.curPlaybackPosition + "}";
                    callBackFunction.onCallBack(str);
                    PLVCommonLog.d(PLVLCPPTView.TAG, "PLVLCPPTView.callVideoDuration time=" + str);
                }
            }

            @Override // com.plv.business.api.common.ppt.PLVPPTVodProcessor.PLVVideoPPTCallback
            public void pptPositionChange(boolean z2) {
                if (PLVLCPPTView.this.onPlaybackPPTViewListener != null) {
                    PLVLCPPTView.this.onPlaybackPPTViewListener.onPlaybackSwitchPPTViewLocation(!z2);
                }
            }

            @Override // com.plv.business.api.common.ppt.PLVPPTVodProcessor.PLVVideoPPTCallback
            public void pptPrepare() {
                PLVLCPPTView.this.pptPlaceHolderView.setVisibility(4);
            }
        });
        this.pptWebView.registerProcessor(polyvPPTVodProcessor);
    }

    private void initData() {
        PLVPPTPresenter pLVPPTPresenter = new PLVPPTPresenter();
        this.presenter = pLVPPTPresenter;
        pLVPPTPresenter.init(this);
    }

    private void initialView(Context context) {
        View.inflate(context, R.layout.plvlc_ppt_view_layout, this);
        this.pptWebView = (PolyvPPTWebView) findViewById(R.id.plvlc_ppt_web_view);
        PLVPlaceHolderView pLVPlaceHolderView = (PLVPlaceHolderView) findViewById(R.id.plvlc_ppt_placeholder);
        this.pptPlaceHolderView = pLVPlaceHolderView;
        pLVPlaceHolderView.setPlaceHolderImg(R.drawable.plvlc_ppt_placeholder);
        this.pptPlaceHolderView.setPlaceHolderText(getResources().getString(R.string.plv_ppt_no_document));
        PolyvPPTWebView polyvPPTWebView = this.pptWebView;
        if (polyvPPTWebView != null) {
            polyvPPTWebView.setPageLoadCallback(new PLVWebview.WebPageLoadCallback() { // from class: com.easefun.polyv.livecloudclass.modules.ppt.PLVLCPPTView.1
                @Override // com.plv.foundationsdk.web.PLVWebview.WebPageLoadCallback
                public void onLoadFinish(WebView webView, String str) {
                    PLVELogSender.send(PolyvPPTElog.class, PLVPPTElog.PPTEvent.PPT_LOAD_FINISH, "load finish ");
                }

                @Override // com.plv.foundationsdk.web.PLVWebview.WebPageLoadCallback
                public void onLoadSslFailed(WebView webView, String str) {
                    PLVELogSender.send(PolyvPPTElog.class, PLVPPTElog.PPTEvent.PPT_LOAD_FAILED, "load failed :");
                }

                @Override // com.plv.foundationsdk.web.PLVWebview.WebPageLoadCallback
                public void onLoadStart(WebView webView, String str) {
                }
            });
        }
        PLVELogSender.send(PolyvPPTElog.class, PLVPPTElog.PPTEvent.PPT_LOAD_START, "load start :");
        registerHandler();
        PolyvPPTWebView polyvPPTWebView2 = this.pptWebView;
        if (polyvPPTWebView2 != null) {
            polyvPPTWebView2.loadWeb();
        }
    }

    private void registerHandler() {
        PolyvPPTWebView polyvPPTWebView = this.pptWebView;
        if (polyvPPTWebView != null) {
            polyvPPTWebView.registerHandler("getNativeAppParamsInfo", new BridgeHandler() { // from class: com.easefun.polyv.livecloudclass.modules.ppt.PLVLCPPTView.2
                @Override // com.github.lzyzsd.jsbridge.BridgeHandler
                public void handler(String str, CallBackFunction callBackFunction) {
                    callBackFunction.onCallBack(PLVGsonUtil.toJsonSimple(new PLVInteractNativeAppParams().setAppId(PolyvLiveSDKClient.getInstance().getAppId()).setAppSecret(PolyvLiveSDKClient.getInstance().getAppSecret())));
                }
            });
        }
    }

    private void updateMsgDelayTime() {
        this.presenter.notifyIsWatchLowLatency(this.isRtcWatch || this.isLowLatencyWatch);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView
    public void destroy() {
        PLVCommonLog.d(TAG, "destroy ppt view");
        this.presenter.destroy();
        PolyvPPTWebView polyvPPTWebView = this.pptWebView;
        if (polyvPPTWebView != null) {
            ViewParent parent = polyvPPTWebView.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.pptWebView);
            }
            this.pptWebView.removeAllViews();
            this.pptWebView.destroy();
        }
        this.pptWebView = null;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView
    public IPolyvPPTView getPlaybackPPTViewToBindInPlayer() {
        return new IPolyvPPTView() { // from class: com.easefun.polyv.livecloudclass.modules.ppt.PLVLCPPTView.3
            @Override // com.plv.business.api.common.ppt.IPLVPPTView
            public void onLoadLocalPpt(@NonNull PLVPPTLocalCacheVO pLVPPTLocalCacheVO) {
                PLVCommonLog.d(PLVLCPPTView.TAG, "PLVLCPPTView.onLoadLocalPpt=" + pLVPPTLocalCacheVO);
                PLVLCPPTView.this.hideLoading();
                if (PLVLCPPTView.this.pptWebView != null) {
                    WebSettings settings = PLVLCPPTView.this.pptWebView.getSettings();
                    settings.setAllowFileAccess(true);
                    settings.setAllowFileAccessFromFileURLs(true);
                    PLVLCPPTView.this.pptWebView.loadLocalPpt(pLVPPTLocalCacheVO);
                }
            }

            @Override // com.plv.business.api.common.ppt.IPLVPPTView
            public void pause(String str) {
                PLVCommonLog.d(PLVLCPPTView.TAG, "PLVLCPPTView.pause=" + str);
                if (PLVLCPPTView.this.pptWebView != null) {
                    PLVLCPPTView.this.pptWebView.callPause(str);
                }
            }

            @Override // com.plv.business.api.common.ppt.IPLVPPTView
            public void play(String str) {
                PLVCommonLog.d(PLVLCPPTView.TAG, "PLVLCPPTView.play=" + str);
                if (PLVLCPPTView.this.pptWebView != null) {
                    PLVLCPPTView.this.pptWebView.callStart(str);
                }
            }

            @Override // com.plv.business.api.common.ppt.IPLVPPTView
            public void pptPrepare(final String str) {
                PLVCommonLog.d(PLVLCPPTView.TAG, "PLVLCPPTView.pptPrepare=" + str);
                PLVLCPPTView.this.hideLoading();
                if (PLVLCPPTView.this.pptWebView != null) {
                    PLVLCPPTView.this.pptWebView.loadWeb();
                    PLVLCPPTView.this.pptWebView.postDelayed(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.ppt.PLVLCPPTView.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            PLVLCPPTView.this.pptWebView.callPPTParams(str);
                        }
                    }, 1500L);
                }
            }

            @Override // com.plv.business.api.common.ppt.IPLVPPTView
            public void seek(String str) {
                PLVCommonLog.d(PLVLCPPTView.TAG, "PLVLCPPTView.seek" + str);
                if (PLVLCPPTView.this.pptWebView != null) {
                    PLVLCPPTView.this.pptWebView.callSeek(str);
                }
            }
        };
    }

    @Override // com.easefun.polyv.livecommon.module.modules.ppt.contract.IPLVPPTContract.IPLVPPTView
    public void hideLoading() {
        this.pptPlaceHolderView.setVisibility(8);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView
    public void initLivePPT(IPLVLCPPTView.OnPLVLCLivePPTViewListener onPLVLCLivePPTViewListener) {
        this.onLivePPTViewListener = onPLVLCLivePPTViewListener;
        addLiveJSEventListener();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView
    public void initPlaybackPPT(IPLVLCPPTView.OnPLVLCPlaybackPPTViewListener onPLVLCPlaybackPPTViewListener) {
        this.onPlaybackPPTViewListener = onPLVLCPlaybackPPTViewListener;
        addPlaybackJSEventListener();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView
    public void notifyJoinRtcChannel() {
        this.isRtcWatch = true;
        updateMsgDelayTime();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView
    public void notifyLeaveRtcChannel() {
        this.isRtcWatch = false;
        updateMsgDelayTime();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView
    public void reLoad() {
        PolyvPPTWebView polyvPPTWebView = this.pptWebView;
        if (polyvPPTWebView != null) {
            polyvPPTWebView.loadWeb();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.ppt.contract.IPLVPPTContract.IPLVPPTView
    public void sendMsgToWebView(String str) {
        PolyvPPTWebView polyvPPTWebView = this.pptWebView;
        if (polyvPPTWebView != null) {
            polyvPPTWebView.callUpdateWebView(str);
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView
    public void sendSEIData(long j2) {
        PLVCommonLog.d(TAG, "ts=" + j2);
        sendWebMessage(PLVLivePPTProcessor.SETSEIDATA, "{\"time\":" + j2 + "}");
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView
    public void sendWebMessage(String str, String str2) {
        PolyvPPTWebView polyvPPTWebView = this.pptWebView;
        if (polyvPPTWebView != null) {
            polyvPPTWebView.callMessage(str, str2);
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView
    public void setIsLowLatencyWatch(boolean z2) {
        this.isLowLatencyWatch = z2;
        updateMsgDelayTime();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView
    public void setPlaybackCurrentPosition(int i2) {
        this.curPlaybackPosition = i2;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.ppt.contract.IPLVPPTContract.IPLVPPTView
    public void switchPPTViewLocation(boolean z2) {
        IPLVLCPPTView.OnPLVLCLivePPTViewListener onPLVLCLivePPTViewListener = this.onLivePPTViewListener;
        if (onPLVLCLivePPTViewListener != null) {
            onPLVLCLivePPTViewListener.onLiveSwitchPPTViewLocation(z2);
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView
    public void turnPagePPT(String str) {
        PLVCommonLog.d(TAG, "turnPagePPT: " + str);
        sendWebMessage("changePPTPage", "{\"type\":\"" + str + "\"}");
    }

    public PLVLCPPTView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCPPTView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.isLowLatencyWatch = PLVLinkMicConfig.getInstance().isLowLatencyWatchEnabled();
        this.isRtcWatch = PLVLinkMicConfig.getInstance().isLowLatencyPureRtcWatch();
        initialView(context);
        initData();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.ppt.contract.IPLVPPTContract.IPLVPPTView
    public void sendMsgToWebView(String str, String str2) {
        sendWebMessage(str2, str);
    }
}
