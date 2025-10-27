package com.psychiatrygarden.activity.purchase.util;

import android.text.TextUtils;
import com.alipay.sdk.util.j;
import java.util.Map;

/* loaded from: classes5.dex */
public class PayResult {
    private String memo;
    private String result;
    private String resultStatus;

    public PayResult(Map<String, String> rawResult) {
        if (rawResult == null) {
            return;
        }
        for (String str : rawResult.keySet()) {
            if (TextUtils.equals(str, j.f3383a)) {
                this.resultStatus = rawResult.get(str);
            } else if (TextUtils.equals(str, "result")) {
                this.result = rawResult.get(str);
            } else if (TextUtils.equals(str, j.f3384b)) {
                this.memo = rawResult.get(str);
            }
        }
    }

    public String getMemo() {
        return this.memo;
    }

    public String getResult() {
        return this.result;
    }

    public String getResultStatus() {
        return this.resultStatus;
    }

    public String toString() {
        return "resultStatus={" + this.resultStatus + "};memo={" + this.memo + "};result={" + this.result + "}";
    }
}
