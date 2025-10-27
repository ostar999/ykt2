package com.huawei.hms.security;

import android.content.Context;
import android.content.Intent;
import com.huawei.hms.api.HuaweiServicesNotAvailableException;
import com.huawei.hms.api.HuaweiServicesRepairableException;

/* loaded from: classes4.dex */
public class SecComponentInstallWizard {
    public static final String PROVIDER_NAME = "HmsCore_OpenSSL";

    public interface SecComponentInstallWizardListener {
        void onFailed(int i2, Intent intent);

        void onSuccess();
    }

    public static void install(Context context) throws HuaweiServicesRepairableException, HuaweiServicesNotAvailableException {
    }
}
