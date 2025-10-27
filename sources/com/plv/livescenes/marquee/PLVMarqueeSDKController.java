package com.plv.livescenes.marquee;

import android.graphics.Color;
import com.alipay.sdk.sys.a;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.plv.foundationsdk.net.PLVResponseBean;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.PLVrResponseCallback;
import com.plv.foundationsdk.net.api.PLVFoundationApiManager;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.foundationsdk.utils.PLVUtils;
import com.umeng.analytics.pro.am;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlinx.coroutines.DebugKt;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class PLVMarqueeSDKController {
    public static final int ALLOW_PLAY_MARQUEE = 3;
    public static final int MARQUEE_SIGN_ERROR = 0;
    public static final int NOT_ALLOW_PLAY_MARQUEE = 2;
    public static final int NOT_ALLOW_PLAY_VIDEO = 1;
    private static final String TAG = "PLVMarqueeRequestDiyUrl";

    public interface IPLVMarqueeRequestByDiyUrlCallback {
        void onParams(int i2, JSONObject jSONObject);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MARQUEE_CONTROLLER_TIP {
    }

    private static void requestMarqueeParams(String str, final String str2, final String str3, final String str4, final long j2, final IPLVMarqueeRequestByDiyUrlCallback iPLVMarqueeRequestByDiyUrlCallback) {
        PLVResponseExcutor.excuteUndefinData(PLVFoundationApiManager.getPlvUrlApi().requestMarQueeUrl(str), new PLVrResponseCallback<ResponseBody>() { // from class: com.plv.livescenes.marquee.PLVMarqueeSDKController.1
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable th) {
                super.onError(th);
                IPLVMarqueeRequestByDiyUrlCallback iPLVMarqueeRequestByDiyUrlCallback2 = iPLVMarqueeRequestByDiyUrlCallback;
                if (iPLVMarqueeRequestByDiyUrlCallback2 != null) {
                    iPLVMarqueeRequestByDiyUrlCallback2.onParams(3, null);
                }
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFailure(PLVResponseBean<ResponseBody> pLVResponseBean) {
                super.onFailure(pLVResponseBean);
                IPLVMarqueeRequestByDiyUrlCallback iPLVMarqueeRequestByDiyUrlCallback2 = iPLVMarqueeRequestByDiyUrlCallback;
                if (iPLVMarqueeRequestByDiyUrlCallback2 != null) {
                    iPLVMarqueeRequestByDiyUrlCallback2.onParams(3, null);
                }
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(ResponseBody responseBody) throws JSONException {
                IPLVMarqueeRequestByDiyUrlCallback iPLVMarqueeRequestByDiyUrlCallback2;
                IPLVMarqueeRequestByDiyUrlCallback iPLVMarqueeRequestByDiyUrlCallback3;
                IPLVMarqueeRequestByDiyUrlCallback iPLVMarqueeRequestByDiyUrlCallback4;
                try {
                    try {
                        JSONObject jSONObject = new JSONObject(responseBody.string());
                        String strOptString = jSONObject.optString("show");
                        String strOptString2 = jSONObject.optString("status");
                        String strOptString3 = jSONObject.optString("sign");
                        String strOptString4 = jSONObject.optString("username");
                        String strOptString5 = jSONObject.optString("msg");
                        String strOptString6 = jSONObject.optString(TtmlNode.ATTR_TTS_FONT_SIZE);
                        String strOptString7 = jSONObject.optString("fontColor");
                        String strOptString8 = jSONObject.optString("speed");
                        String strOptString9 = jSONObject.optString("filter");
                        String strOptString10 = jSONObject.optString(a.f3325j);
                        String strOptString11 = jSONObject.optString("alpha");
                        String strOptString12 = jSONObject.optString("filterAlpha");
                        String strOptString13 = jSONObject.optString("filterColor");
                        String strOptString14 = jSONObject.optString("blurX");
                        String strOptString15 = jSONObject.optString("blurY");
                        String strOptString16 = jSONObject.optString(am.aT);
                        String strOptString17 = jSONObject.optString("lifeTime");
                        String strOptString18 = jSONObject.optString("tweenTime");
                        String strOptString19 = jSONObject.optString("strength");
                        StringBuilder sb = new StringBuilder();
                        sb.append("vid=");
                        sb.append(str2);
                        sb.append("&uid=");
                        sb.append(str3);
                        sb.append("&username=");
                        sb.append(strOptString4);
                        sb.append("&code=");
                        sb.append(str4);
                        sb.append("&t=");
                        sb.append(j2);
                        sb.append("&msg=");
                        sb.append(strOptString5);
                        sb.append("&fontSize=");
                        sb.append(strOptString6);
                        sb.append("&fontColor=");
                        sb.append(strOptString7);
                        sb.append("&speed=");
                        sb.append(strOptString8);
                        sb.append("&filter=");
                        sb.append(strOptString9);
                        sb.append("&setting=");
                        sb.append(strOptString10);
                        sb.append("&alpha=");
                        sb.append(strOptString11);
                        sb.append("&filterAlpha=");
                        sb.append(strOptString12);
                        sb.append("&filterColor=");
                        sb.append(strOptString13);
                        sb.append("&blurX=");
                        sb.append(strOptString14);
                        sb.append("&blurY=");
                        sb.append(strOptString15);
                        sb.append("&interval=");
                        sb.append(strOptString16);
                        sb.append("&lifeTime=");
                        sb.append(strOptString17);
                        sb.append("&tweenTime=");
                        sb.append(strOptString18);
                        sb.append("&strength=");
                        sb.append(strOptString19);
                        sb.append("&show=");
                        String str5 = strOptString;
                        sb.append(str5);
                        if (!strOptString3.equals(PLVUtils.MD5(sb.toString()).toLowerCase()) && (iPLVMarqueeRequestByDiyUrlCallback4 = iPLVMarqueeRequestByDiyUrlCallback) != null) {
                            iPLVMarqueeRequestByDiyUrlCallback4.onParams(0, null);
                            return;
                        }
                        if ("".equals(str5)) {
                            str5 = DebugKt.DEBUG_PROPERTY_VALUE_ON;
                        }
                        if (!DebugKt.DEBUG_PROPERTY_VALUE_ON.equals(str5) && (iPLVMarqueeRequestByDiyUrlCallback3 = iPLVMarqueeRequestByDiyUrlCallback) != null) {
                            iPLVMarqueeRequestByDiyUrlCallback3.onParams(2, null);
                            return;
                        }
                        if ("".equals(strOptString7)) {
                            strOptString7 = "0x000000";
                        }
                        if ("".equals(strOptString13)) {
                            strOptString13 = "0x000000";
                        }
                        if ("".equals(strOptString9)) {
                            strOptString9 = DebugKt.DEBUG_PROPERTY_VALUE_OFF;
                        }
                        int iIntValue = PLVFormatUtils.integerValueOf(strOptString6, 30).intValue();
                        int iIntValue2 = PLVFormatUtils.integerValueOf(strOptString8, 200).intValue();
                        int iIntValue3 = PLVFormatUtils.integerValueOf(strOptString10, 1).intValue();
                        float fFloatValue = PLVFormatUtils.floatValueOf(strOptString11, 1.0f).floatValue();
                        float fFloatValue2 = PLVFormatUtils.floatValueOf(strOptString12, 1.0f).floatValue();
                        int iIntValue4 = PLVFormatUtils.integerValueOf(strOptString14, 2).intValue();
                        int iIntValue5 = PLVFormatUtils.integerValueOf(strOptString15, 2).intValue();
                        int iIntValue6 = PLVFormatUtils.integerValueOf(strOptString16, 5).intValue();
                        int iIntValue7 = PLVFormatUtils.integerValueOf(strOptString17, 3).intValue();
                        int iIntValue8 = PLVFormatUtils.integerValueOf(strOptString18, 1).intValue();
                        int iIntValue9 = PLVFormatUtils.integerValueOf(strOptString19, 4).intValue();
                        if (PLVFormatUtils.integerValueOf(strOptString2, 1).intValue() != 1 && (iPLVMarqueeRequestByDiyUrlCallback2 = iPLVMarqueeRequestByDiyUrlCallback) != null) {
                            iPLVMarqueeRequestByDiyUrlCallback2.onParams(1, null);
                            return;
                        }
                        int i2 = 1;
                        if (iIntValue3 >= 1 && iIntValue3 <= 5) {
                            i2 = iIntValue3;
                        }
                        JSONObject jSONObject2 = new JSONObject();
                        try {
                            jSONObject2.put("userName", strOptString4);
                            jSONObject2.put(TtmlNode.ATTR_TTS_FONT_SIZE, Math.min(iIntValue, 66));
                            jSONObject2.put("fontColor", Color.parseColor(strOptString7.replace("0x", DictionaryFactory.SHARP)));
                            jSONObject2.put("fontAlpha", (int) (fFloatValue * 255.0f));
                            jSONObject2.put("filter", strOptString9.equals(DebugKt.DEBUG_PROPERTY_VALUE_ON));
                            jSONObject2.put("filterColor", Color.parseColor(strOptString13.replace("0x", DictionaryFactory.SHARP)));
                            jSONObject2.put("filterBlurX", iIntValue4);
                            jSONObject2.put("filterBlurY", iIntValue5);
                            jSONObject2.put("filterAlpha", (int) (fFloatValue2 * 255.0f));
                            jSONObject2.put("filterStrength", iIntValue9);
                            jSONObject2.put(a.f3325j, i2);
                            jSONObject2.put("speed", iIntValue2);
                            jSONObject2.put(am.aT, iIntValue6);
                            jSONObject2.put("lifeTime", iIntValue7);
                            jSONObject2.put("tweenTime", iIntValue8);
                            IPLVMarqueeRequestByDiyUrlCallback iPLVMarqueeRequestByDiyUrlCallback5 = iPLVMarqueeRequestByDiyUrlCallback;
                            if (iPLVMarqueeRequestByDiyUrlCallback5 != null) {
                                iPLVMarqueeRequestByDiyUrlCallback5.onParams(3, jSONObject2);
                            }
                        } catch (IOException e2) {
                            e = e2;
                            e.printStackTrace();
                        } catch (JSONException e3) {
                            e = e3;
                            e.printStackTrace();
                        }
                    } catch (JSONException e4) {
                        e = e4;
                    }
                } catch (IOException e5) {
                    e = e5;
                }
            }
        });
    }

    public static void setMarqueeParams(String str, String str2, String str3, String str4, IPLVMarqueeRequestByDiyUrlCallback iPLVMarqueeRequestByDiyUrlCallback) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        if (str.contains("?")) {
            sb.append("&");
        } else {
            sb.append("?");
        }
        sb.append("vid=");
        sb.append(str2);
        sb.append("&uid=");
        sb.append(str3);
        sb.append("&code=");
        sb.append(str4);
        sb.append("&t=");
        sb.append(jCurrentTimeMillis);
        requestMarqueeParams(str + sb.toString(), str2, str3, str4, jCurrentTimeMillis, iPLVMarqueeRequestByDiyUrlCallback);
    }
}
