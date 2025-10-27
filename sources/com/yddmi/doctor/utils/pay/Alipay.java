package com.yddmi.doctor.utils.pay;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.mobile.auth.gatewayauth.Constant;
import com.psychiatrygarden.utils.pay.PayMethodKeyKt;
import com.yddmi.doctor.utils.pay.JPay;
import java.util.Map;

/* loaded from: classes6.dex */
public class Alipay {
    public static final int PAY_DEALING = 4;
    public static final int PAY_ERROR = 1;
    public static final int PAY_NETWORK_ERROR = 2;
    public static final int PAY_OTHER_ERROR = 6;
    public static final int PAY_PARAMETERS_ERROE = 7;
    public static final int RESULT_ERROR = 3;
    private static Alipay mAliPay;
    private Activity mContext;
    private Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.yddmi.doctor.utils.pay.Alipay.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            PayResult payResult = new PayResult((Map) message.obj);
            Log.e("aliPay call ", payResult.toString());
            String resultStatus = payResult.getResultStatus();
            if (Alipay.this.mJPayListener == null) {
                return;
            }
            if (TextUtils.equals(resultStatus, PayMethodKeyKt.PAY_SUCCESS_CODE)) {
                Alipay.this.mJPayListener.onPaySuccess();
                return;
            }
            if (TextUtils.equals(resultStatus, Constant.CODE_GET_TOKEN_SUCCESS)) {
                Alipay.this.mJPayListener.onPayError(4, "正在处理结果中");
                return;
            }
            if (TextUtils.equals(resultStatus, "6001")) {
                Alipay.this.mJPayListener.onPayCancel();
                return;
            }
            if (TextUtils.equals(resultStatus, "6002")) {
                Alipay.this.mJPayListener.onPayError(2, "网络连接出错");
            } else if (TextUtils.equals(resultStatus, "4000")) {
                Alipay.this.mJPayListener.onPayError(1, "订单支付失败");
            } else {
                Alipay.this.mJPayListener.onPayError(6, resultStatus);
            }
        }
    };
    private JPay.PayListener mJPayListener;

    private Alipay(Activity activity) {
        this.mContext = activity;
    }

    public static Alipay getInstance(Activity activity) {
        if (mAliPay == null) {
            synchronized (Alipay.class) {
                if (mAliPay == null) {
                    mAliPay = new Alipay(activity);
                }
            }
        }
        return mAliPay;
    }

    public void startAliPay(String str, JPay.PayListener payListener) {
        this.mJPayListener = payListener;
        new Thread(new Runnable() { // from class: com.yddmi.doctor.utils.pay.Alipay.1
            @Override // java.lang.Runnable
            public void run() {
            }
        }).start();
    }
}
