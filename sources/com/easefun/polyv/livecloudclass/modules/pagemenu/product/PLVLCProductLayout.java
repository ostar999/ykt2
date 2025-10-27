package com.easefun.polyv.livecloudclass.modules.pagemenu.product;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.pagemenu.commodity.PLVLCCommodityDetailActivity;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.utils.PLVDebounceClicker;
import com.easefun.polyv.livecommon.module.utils.PLVToast;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.utils.PLVTimeUnit;
import com.plv.livescenes.feature.interact.vo.PLVInteractNativeAppParams;
import com.plv.livescenes.feature.pagemenu.product.PLVProductWebView;
import com.plv.livescenes.feature.pagemenu.product.vo.PLVInteractProductOnClickDataVO;

/* loaded from: classes3.dex */
public class PLVLCProductLayout extends FrameLayout {

    @Nullable
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private PLVProductWebView productWebView;

    @Nullable
    private Observer<String> sessionIdObserver;

    public PLVLCProductLayout(@NonNull Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public PLVInteractNativeAppParams generateAppParams() {
        if (this.liveRoomDataManager == null) {
            return null;
        }
        return new PLVInteractNativeAppParams().setAppId(this.liveRoomDataManager.getConfig().getAccount().getAppId()).setAppSecret(this.liveRoomDataManager.getConfig().getAccount().getAppSecret()).setSessionId(this.liveRoomDataManager.getSessionId()).setChannelInfo(new PLVInteractNativeAppParams.ChannelInfoDTO().setChannelId(this.liveRoomDataManager.getConfig().getChannelId()).setRoomId(this.liveRoomDataManager.getConfig().getChannelId())).setUserInfo(new PLVInteractNativeAppParams.UserInfoDTO().setUserId(this.liveRoomDataManager.getConfig().getUser().getViewerId()).setNick(this.liveRoomDataManager.getConfig().getUser().getViewerName()).setPic(this.liveRoomDataManager.getConfig().getUser().getViewerAvatar()));
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_product_layout, this);
        this.productWebView = (PLVProductWebView) findViewById(R.id.plvlc_product_web_view);
        initWebView();
    }

    private void initWebView() {
        this.productWebView.setOnNeedUpdateNativeAppParamsInfoHandler(new BridgeHandler() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.product.PLVLCProductLayout.2
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                callBackFunction.onCallBack(PLVGsonUtil.toJsonSimple(PLVLCProductLayout.this.generateAppParams()));
            }
        }).setOnReceiveEventClickProductButtonHandler(new BridgeHandler() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.product.PLVLCProductLayout.1
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                PLVInteractProductOnClickDataVO pLVInteractProductOnClickDataVO;
                if (!PLVDebounceClicker.tryClick(getClass().getName(), PLVTimeUnit.seconds(1L).toMillis()) || (pLVInteractProductOnClickDataVO = (PLVInteractProductOnClickDataVO) PLVGsonUtil.fromJson(PLVInteractProductOnClickDataVO.class, str)) == null || pLVInteractProductOnClickDataVO.getData() == null || PLVLCProductLayout.this.getContext() == null) {
                    return;
                }
                String linkByType = pLVInteractProductOnClickDataVO.getData().getLinkByType();
                if (TextUtils.isEmpty(linkByType)) {
                    PLVToast.Builder.context(PLVLCProductLayout.this.getContext()).setText(R.string.plv_commodity_toast_empty_link).show();
                } else {
                    PLVLCCommodityDetailActivity.start(PLVLCProductLayout.this.getContext(), linkByType);
                }
            }
        }).loadWeb();
    }

    private void observeLiveRoomDataManager() {
        IPLVLiveRoomDataManager iPLVLiveRoomDataManager = this.liveRoomDataManager;
        if (iPLVLiveRoomDataManager == null) {
            return;
        }
        if (this.sessionIdObserver != null) {
            iPLVLiveRoomDataManager.getSessionIdLiveData().removeObserver(this.sessionIdObserver);
        }
        LiveData<String> sessionIdLiveData = this.liveRoomDataManager.getSessionIdLiveData();
        Observer<String> observer = new Observer<String>() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.product.PLVLCProductLayout.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable String str) {
                PLVLCProductLayout.this.updateNativeAppParamToWebView();
            }
        };
        this.sessionIdObserver = observer;
        sessionIdLiveData.observeForever(observer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNativeAppParamToWebView() {
        PLVProductWebView pLVProductWebView;
        IPLVLiveRoomDataManager iPLVLiveRoomDataManager = this.liveRoomDataManager;
        if (iPLVLiveRoomDataManager == null || TextUtils.isEmpty(iPLVLiveRoomDataManager.getSessionId()) || (pLVProductWebView = this.productWebView) == null) {
            return;
        }
        pLVProductWebView.updateNativeAppParamsInfo(generateAppParams());
    }

    public void init(IPLVLiveRoomDataManager iPLVLiveRoomDataManager) {
        this.liveRoomDataManager = iPLVLiveRoomDataManager;
        observeLiveRoomDataManager();
    }

    public PLVLCProductLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCProductLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        initView();
    }
}
