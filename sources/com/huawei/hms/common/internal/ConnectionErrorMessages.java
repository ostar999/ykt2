package com.huawei.hms.common.internal;

import android.app.Activity;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.update.ui.NotInstalledHmsDialogHelper;
import com.huawei.hms.utils.ResourceLoaderUtil;

/* loaded from: classes4.dex */
public class ConnectionErrorMessages {
    public static String getErrorDialogButtonMessage(Activity activity, int i2) {
        if (ResourceLoaderUtil.getmContext() == null) {
            ResourceLoaderUtil.setmContext(activity.getApplicationContext());
        }
        return ResourceLoaderUtil.getString("hms_confirm");
    }

    public static String getErrorMessage(Activity activity, int i2) {
        if (ResourceLoaderUtil.getmContext() == null) {
            ResourceLoaderUtil.setmContext(activity.getApplicationContext());
        }
        if (i2 == 1 || i2 == 2) {
            return activity.getString(ResourceLoaderUtil.getStringId("hms_apk_not_installed_hints"), NotInstalledHmsDialogHelper.getAppName(activity));
        }
        return null;
    }

    public static String getErrorTitle(Activity activity, int i2) {
        if (ResourceLoaderUtil.getmContext() == null) {
            ResourceLoaderUtil.setmContext(activity.getApplicationContext());
        }
        if (i2 == 1 || i2 == 2) {
            return null;
        }
        if (i2 == 3) {
            return ResourceLoaderUtil.getString("hms_bindfaildlg_message");
        }
        if (i2 == 9) {
            HMSLog.e("HuaweiApiAvailability", "Huawei Mobile Services is invalid. Cannot recover.");
            return null;
        }
        HMSLog.e("HuaweiApiAvailability", "Unexpected error code " + i2);
        return null;
    }
}
