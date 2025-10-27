package com.plv.business.api.common.ppt;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import com.plv.business.api.common.ppt.vo.PLVPPTLocalCacheVO;
import com.plv.business.web.PLVWebview;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.manager.PLVChatDomainManager;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import java.net.MalformedURLException;
import java.net.URL;

/* loaded from: classes4.dex */
public class PLVPPTWebView extends PLVWebview {
    private static final String LOAD_URL = "https://player.polyv.net/resp/ppt-h5/latest/page/pptForMobile.html";
    private static final String TAG = "PLVPPTWebView";
    public static final String V2_GET_NATIVE_APP_PARAMS_INFO = "getNativeAppParamsInfo";
    private boolean needGestureAction;
    private String pptBackgroundColor;
    private String pptBackgroundImg;
    private String pptBackgroundImgWidth;

    public PLVPPTWebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void callPPTParams(String str) {
        callMessage(PLVPPTVodProcessor.VIDEOSTART, str);
    }

    public void callPause(String str) {
        callMessage(PLVPPTVodProcessor.PAUSE_PPT, str);
    }

    public void callSeek(String str) {
        callMessage(PLVPPTVodProcessor.SEEK_PPT, str);
    }

    public void callStart(String str) {
        callMessage(PLVPPTVodProcessor.START_PPT, str);
    }

    public void callUpdateWebView(String str) {
        super.callMessage("refreshPPT", str);
    }

    public void loadLocalPpt(@NonNull PLVPPTLocalCacheVO pLVPPTLocalCacheVO) {
        this.hasLoadFinish = false;
        loadUrl("file:///" + pLVPPTLocalCacheVO.getPptHtmlFilePath());
        callMessage(PLVPPTVodProcessor.SET_OFFLINE_PATH, "{\"path\":\"" + pLVPPTLocalCacheVO.getPptDataPath() + "\"}");
        callPPTParams(PLVGsonUtil.toJsonSimple(PLVSugarUtil.mapOf(PLVSugarUtil.pair("vid", pLVPPTLocalCacheVO.getVideoPoolId()), PLVSugarUtil.pair("videoId", pLVPPTLocalCacheVO.getVideoId()))));
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void loadWeb() {
        this.hasLoadFinish = false;
        PLVCommonLog.d(TAG, "loadWeb");
        String chatApiDomain = PLVChatDomainManager.getInstance().getChatDomain().getChatApiDomain();
        if (!TextUtils.isEmpty(chatApiDomain) && chatApiDomain.startsWith("http")) {
            try {
                chatApiDomain = new URL(chatApiDomain).getHost();
            } catch (MalformedURLException e2) {
                PLVCommonLog.e(TAG, "MalformedURLException:" + e2.getMessage());
            }
        }
        PLVCommonLog.d(TAG, "domainName:" + chatApiDomain);
        loadUrl("https://player.polyv.net/resp/ppt-h5/latest/page/pptForMobile.html?domainName=" + chatApiDomain + "&pptBackgroundImg=" + this.pptBackgroundImg + "&pptBackgroundImgWidth=" + this.pptBackgroundImgWidth + "&pptBackgroundColor=" + this.pptBackgroundColor + "&security=" + PLVSignCreator.getSecurity());
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.needGestureAction && super.onTouchEvent(motionEvent);
    }

    public void printSamples(MotionEvent motionEvent) {
        motionEvent.getHistorySize();
        int pointerCount = motionEvent.getPointerCount();
        PLVCommonLog.d(TAG, "At time %s:", motionEvent.getEventTime() + "\n");
        for (int i2 = 0; i2 < pointerCount; i2++) {
            PLVCommonLog.d(TAG, "multil  pointer %d: (%f,%f)", Integer.valueOf(motionEvent.getPointerId(i2)), Float.valueOf(motionEvent.getX(i2)), Float.valueOf(motionEvent.getY(i2)));
        }
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void registerHandler() {
    }

    public void setNeedGestureAction(boolean z2) {
        this.needGestureAction = z2;
    }

    public void setPptBackgroundColor(@NonNull String str) {
        this.pptBackgroundColor = str;
    }

    public void setPptBackgroundImg(@NonNull String str) {
        this.pptBackgroundImg = str;
    }

    public void setPptBackgroundImgWidth(@NonNull String str) {
        this.pptBackgroundImgWidth = str;
    }

    public PLVPPTWebView(Context context) {
        this(context, null);
    }

    public PLVPPTWebView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.needGestureAction = false;
        this.pptBackgroundImg = "";
        this.pptBackgroundImgWidth = "";
        this.pptBackgroundColor = "";
    }
}
