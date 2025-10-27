package com.yddmi.doctor.utils.pay;

import android.app.Activity;
import android.text.TextUtils;
import com.yddmi.doctor.utils.pay.wx.WeiXinPay;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class JPay {
    private static JPay mJPay;
    private Activity mContext;

    public interface PayListener {
        void onPayCancel();

        void onPayError(int i2, String str);

        void onPaySuccess();
    }

    private JPay(Activity activity) {
        this.mContext = activity;
    }

    public static JPay getIntance(Activity activity) {
        if (mJPay == null) {
            synchronized (JPay.class) {
                if (mJPay == null) {
                    mJPay = new JPay(activity);
                }
            }
        }
        return mJPay;
    }

    public void toAliPay(String str, PayListener payListener) {
        if (str != null) {
            if (payListener != null) {
                Alipay.getInstance(this.mContext).startAliPay(str, payListener);
            }
        } else if (payListener != null) {
            payListener.onPayError(7, "参数异常");
        }
    }

    public void toWxPay(String str, PayListener payListener) {
        if (str == null) {
            if (payListener != null) {
                payListener.onPayError(2, "参数异常");
                return;
            }
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!TextUtils.isEmpty(jSONObject.optString("appId")) && !TextUtils.isEmpty(jSONObject.optString("partnerId")) && !TextUtils.isEmpty(jSONObject.optString("prepayId")) && !TextUtils.isEmpty(jSONObject.optString("nonceStr")) && !TextUtils.isEmpty(jSONObject.optString("timeStamp")) && !TextUtils.isEmpty(jSONObject.optString("sign"))) {
                toWxPay(jSONObject.optString("appId"), jSONObject.optString("partnerId"), jSONObject.optString("prepayId"), jSONObject.optString("nonceStr"), jSONObject.optString("timeStamp"), jSONObject.optString("sign"), payListener);
            } else if (payListener != null) {
                payListener.onPayError(2, "参数异常");
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            if (payListener != null) {
                payListener.onPayError(2, "参数异常");
            }
        }
    }

    public void toWxPay(String str, String str2, String str3, String str4, String str5, String str6, PayListener payListener) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str4) && !TextUtils.isEmpty(str5) && !TextUtils.isEmpty(str6)) {
            WeiXinPay.getInstance(this.mContext).startWXPay(str, str2, str3, str4, str5, str6, payListener);
        } else if (payListener != null) {
            payListener.onPayError(2, "参数异常");
        }
    }
}
