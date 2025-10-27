package com.huawei.hms.common.api;

import com.huawei.hms.api.Api;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.common.HuaweiApi;
import com.huawei.hms.support.log.HMSLog;

/* loaded from: classes4.dex */
public class AvailabilityException extends Exception {
    private String TAG = "AvailabilityException";
    private String message = null;

    private ConnectionResult generateConnectionResult(int i2) {
        HMSLog.i(this.TAG, "The availability check result is: " + i2);
        setMessage(i2);
        return new ConnectionResult(i2);
    }

    private void setMessage(int i2) {
        if (i2 == 21) {
            this.message = "ANDROID_VERSION_UNSUPPORT";
            return;
        }
        if (i2 == 0) {
            this.message = "success";
            return;
        }
        if (i2 == 1) {
            this.message = "SERVICE_MISSING";
            return;
        }
        if (i2 == 2) {
            this.message = "SERVICE_VERSION_UPDATE_REQUIRED";
        } else if (i2 != 3) {
            this.message = "INTERNAL_ERROR";
        } else {
            this.message = "SERVICE_DISABLED";
        }
    }

    public ConnectionResult getConnectionResult(HuaweiApiCallable huaweiApiCallable) {
        if (huaweiApiCallable == null || huaweiApiCallable.getHuaweiApi() == null) {
            HMSLog.e(this.TAG, "The huaweiApi is null.");
            return generateConnectionResult(8);
        }
        return generateConnectionResult(HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(huaweiApiCallable.getHuaweiApi().getContext(), 30000000));
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.message;
    }

    public ConnectionResult getConnectionResult(HuaweiApi<? extends Api.ApiOptions> huaweiApi) {
        if (huaweiApi == null) {
            HMSLog.e(this.TAG, "The huaweiApi is null.");
            return generateConnectionResult(8);
        }
        return generateConnectionResult(HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(huaweiApi.getContext(), 30000000));
    }
}
