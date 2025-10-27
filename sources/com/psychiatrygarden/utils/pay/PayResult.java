package com.psychiatrygarden.utils.pay;

import android.text.TextUtils;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.j;

/* loaded from: classes6.dex */
public class PayResult {
    private String memo;
    private String result;
    private String resultStatus;

    public PayResult(String rawResult) {
        if (TextUtils.isEmpty(rawResult)) {
            return;
        }
        for (String str : rawResult.split(h.f3376b)) {
            if (str.startsWith(j.f3383a)) {
                this.resultStatus = gatValue(str, j.f3383a);
            }
            if (str.startsWith("result")) {
                this.result = gatValue(str, "result");
            }
            if (str.startsWith(j.f3384b)) {
                this.memo = gatValue(str, j.f3384b);
            }
        }
    }

    private String gatValue(String content, String key) {
        String str = key + "={";
        return content.substring(content.indexOf(str) + str.length(), content.lastIndexOf("}"));
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
