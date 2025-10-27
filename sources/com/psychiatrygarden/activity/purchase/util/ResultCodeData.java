package com.psychiatrygarden.activity.purchase.util;

import android.text.TextUtils;
import com.psychiatrygarden.utils.pay.PayMethodKeyKt;

/* loaded from: classes5.dex */
public class ResultCodeData {
    private String mResultStr;

    public String mCheckResultCode(String mResultCode) throws NumberFormatException {
        try {
            int i2 = Integer.parseInt(mResultCode);
            if (i2 == 4000) {
                this.mResultStr = "订单支付失败";
            } else if (i2 == 5000) {
                this.mResultStr = "重复请求";
            } else if (i2 == 6004) {
                this.mResultStr = "支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态";
            } else if (i2 == 8000) {
                this.mResultStr = "正在处理中，支付结果未知，请查询商户订单列表中订单的支付状态";
            } else if (i2 == 9000) {
                this.mResultStr = "订单支付成功";
            } else if (i2 == 6001) {
                this.mResultStr = "用户中途取消";
            } else if (i2 != 6002) {
                this.mResultStr = "支付失败";
            } else {
                this.mResultStr = "网络连接出错";
            }
        } catch (Exception unused) {
            if (TextUtils.equals(mResultCode, PayMethodKeyKt.PAY_SUCCESS_CODE)) {
                this.mResultStr = "订单支付成功";
            } else {
                this.mResultStr = "支付失败";
            }
        }
        return this.mResultStr;
    }
}
