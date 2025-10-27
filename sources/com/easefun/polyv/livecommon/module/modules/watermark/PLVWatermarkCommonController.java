package com.easefun.polyv.livecommon.module.modules.watermark;

import com.plv.business.model.video.PLVWatermarkVO;
import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes3.dex */
public class PLVWatermarkCommonController {
    private static final String TAG = "PLVWatermarkCommonController: ";
    private static volatile PLVWatermarkCommonController instance;

    private PLVWatermarkCommonController() {
    }

    public static PLVWatermarkCommonController getInstance() {
        if (instance == null) {
            synchronized (PLVWatermarkCommonController.class) {
                if (instance == null) {
                    instance = new PLVWatermarkCommonController();
                }
            }
        }
        return instance;
    }

    private int stringToInt(String fontSize) {
        fontSize.hashCode();
        switch (fontSize) {
            case "middle":
                return 45;
            case "large":
                return 60;
            case "small":
                return 30;
            default:
                return 0;
        }
    }

    public void setDefaultWatermarkParam(PLVWatermarkTextVO plvWatermarkTextVO, String content, String fontSize, String fontAlpha) {
        plvWatermarkTextVO.setContent(content).setFontSize(fontSize).setFontAlpha(fontAlpha);
    }

    public void updateWatermarkView(PLVWatermarkVO channelJsonWaterMarkVO, String nickname) {
        PLVWatermarkTextVO pLVWatermarkTextVO = new PLVWatermarkTextVO();
        String str = channelJsonWaterMarkVO.watermarkType;
        str.hashCode();
        if (str.equals("nickname")) {
            setDefaultWatermarkParam(pLVWatermarkTextVO, nickname, channelJsonWaterMarkVO.watermarkFontSize, channelJsonWaterMarkVO.watermarkOpacity);
        } else if (str.equals("fixed")) {
            setDefaultWatermarkParam(pLVWatermarkTextVO, channelJsonWaterMarkVO.watermarkContent, channelJsonWaterMarkVO.watermarkFontSize, channelJsonWaterMarkVO.watermarkOpacity);
        } else {
            PLVCommonLog.d(TAG, "channelJsonWaterMarkVO.watermarkType 类别出错");
        }
    }
}
