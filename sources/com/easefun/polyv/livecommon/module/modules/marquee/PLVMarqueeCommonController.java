package com.easefun.polyv.livecommon.module.modules.marquee;

import com.easefun.polyv.businesssdk.model.video.PolyvLiveMarqueeVO;
import com.easefun.polyv.livecommon.module.modules.marquee.model.PLVMarqueeModel;
import com.plv.business.model.video.PLVLiveMarqueeVO;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.livescenes.marquee.PLVMarqueeSDKController;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class PLVMarqueeCommonController {
    private static final String TAG = "PolyvMarqueeController";
    private static volatile PLVMarqueeCommonController instance;
    private String code = "";
    private String errorMsg = "跑马灯验证失败";

    public interface IPLVMarqueeControllerCallback {
        void onMarqueeModel(int controllerTip, PLVMarqueeModel marqueeModel);
    }

    private PLVMarqueeCommonController() {
    }

    public static PLVMarqueeCommonController getInstance() {
        if (instance == null) {
            synchronized (PLVMarqueeCommonController.class) {
                if (instance == null) {
                    instance = new PLVMarqueeCommonController();
                }
            }
        }
        return instance;
    }

    private void setDefaultMarqueeParams(PLVMarqueeModel marqueeModel, String content, int fontSize, int fontColor, int fontAlpha, int setting, IPLVMarqueeControllerCallback callback) {
        marqueeModel.setUserName(content).setFontSize(fontSize).setFontColor(fontColor).setFontAlpha(fontAlpha).setFilter(false).setSetting(setting).setSpeed(100);
        if (callback != null) {
            callback.onMarqueeModel(3, marqueeModel);
        }
    }

    private void setDiyUrlMarqueeParams(final PLVMarqueeModel marqueeModel, String url, String channelId, String userId, String code, final IPLVMarqueeControllerCallback callback) {
        PLVMarqueeSDKController.setMarqueeParams(url, channelId, userId, code, new PLVMarqueeSDKController.IPLVMarqueeRequestByDiyUrlCallback() { // from class: com.easefun.polyv.livecommon.module.modules.marquee.PLVMarqueeCommonController.1
            @Override // com.plv.livescenes.marquee.PLVMarqueeSDKController.IPLVMarqueeRequestByDiyUrlCallback
            public void onParams(int controllerTip, JSONObject jsonObject) {
                PLVMarqueeModel pLVMarqueeModel = marqueeModel;
                if (pLVMarqueeModel != null) {
                    pLVMarqueeModel.setParamsByJsonObject(jsonObject);
                }
                IPLVMarqueeControllerCallback iPLVMarqueeControllerCallback = callback;
                if (iPLVMarqueeControllerCallback != null) {
                    iPLVMarqueeControllerCallback.onMarqueeModel(controllerTip, marqueeModel);
                }
            }
        });
    }

    public String getErrorMessage() {
        return this.errorMsg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void updateMarqueeView(PolyvLiveMarqueeVO channelJsonMarqueeVO, String nickName, IPLVMarqueeControllerCallback callback) {
        PLVMarqueeModel pLVMarqueeModel;
        int iIntValue;
        int iMin;
        int color;
        int i2;
        pLVMarqueeModel = new PLVMarqueeModel();
        pLVMarqueeModel.setAlwaysShowWhenRun(true).setHiddenWhenPause(false);
        if (channelJsonMarqueeVO.marqueeType.equals(PLVLiveMarqueeVO.MARQUEETYPE_DIYURL)) {
            iIntValue = 1;
            iMin = 0;
            color = 0;
            i2 = 0;
        } else {
            int i3 = (int) (PLVFormatUtils.parseFloat(channelJsonMarqueeVO.marqueeOpacity.replace("%", "")) * 255.0f * 0.01f);
            iMin = Math.min(channelJsonMarqueeVO.marqueeFontSize, 66);
            color = PLVFormatUtils.parseColor(channelJsonMarqueeVO.marqueeFontColor);
            iIntValue = PLVFormatUtils.integerValueOf(channelJsonMarqueeVO.marqueeSetting, 1).intValue();
            i2 = i3;
        }
        String str = channelJsonMarqueeVO.marqueeType;
        str.hashCode();
        switch (str) {
            case "diyurl":
                setDiyUrlMarqueeParams(pLVMarqueeModel, channelJsonMarqueeVO.marquee, channelJsonMarqueeVO.getChannelId(), channelJsonMarqueeVO.getUserId(), this.code, callback);
                break;
            case "nickname":
                pLVMarqueeModel.setInterval(0);
                pLVMarqueeModel.setLifeTime(1);
                pLVMarqueeModel.setTweenTime(0);
                setDefaultMarqueeParams(pLVMarqueeModel, nickName, iMin, color, i2, iIntValue, callback);
                break;
            case "fixed":
                setDefaultMarqueeParams(pLVMarqueeModel, channelJsonMarqueeVO.marquee, iMin, color, i2, iIntValue, callback);
                break;
        }
    }
}
