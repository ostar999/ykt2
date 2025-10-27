package com.yddmi.doctor.utils.pay;

import com.alipay.sdk.util.j;
import java.util.Map;

/* loaded from: classes6.dex */
public class PayResult {
    private String memo;
    private String result;
    private String resultStatus;

    public PayResult(Map<String, String> map) {
        if (map == null) {
            return;
        }
        for (String str : map.keySet()) {
            if (str.equals(j.f3383a)) {
                this.resultStatus = map.get(str);
            } else if (str.equals("result")) {
                this.result = map.get(str);
            } else if (str.equals(j.f3384b)) {
                this.memo = map.get(str);
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
