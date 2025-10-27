package com.google.android.gms.common.api;

import androidx.annotation.NonNull;
import com.alipay.security.mobile.module.http.model.c;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.plv.foundationsdk.web.PLVWebview;

@KeepForSdk
/* loaded from: classes3.dex */
public class CommonStatusCodes {
    public static final int API_NOT_CONNECTED = 17;
    public static final int CANCELED = 16;
    public static final int DEVELOPER_ERROR = 10;
    public static final int ERROR = 13;
    public static final int INTERNAL_ERROR = 8;
    public static final int INTERRUPTED = 14;
    public static final int INVALID_ACCOUNT = 5;
    public static final int NETWORK_ERROR = 7;
    public static final int RESOLUTION_REQUIRED = 6;

    @Deprecated
    public static final int SERVICE_DISABLED = 3;

    @Deprecated
    public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
    public static final int SIGN_IN_REQUIRED = 4;
    public static final int SUCCESS = 0;
    public static final int SUCCESS_CACHE = -1;
    public static final int TIMEOUT = 15;

    @KeepForSdk
    public CommonStatusCodes() {
    }

    @NonNull
    public static String getStatusCodeString(int i2) {
        switch (i2) {
            case -1:
                return "SUCCESS_CACHE";
            case 0:
                return c.f3449g;
            case 1:
            case 9:
            case 11:
            case 12:
            default:
                StringBuilder sb = new StringBuilder(32);
                sb.append("unknown status code: ");
                sb.append(i2);
                return sb.toString();
            case 2:
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            case 3:
                return "SERVICE_DISABLED";
            case 4:
                return "SIGN_IN_REQUIRED";
            case 5:
                return "INVALID_ACCOUNT";
            case 6:
                return "RESOLUTION_REQUIRED";
            case 7:
                return "NETWORK_ERROR";
            case 8:
                return "INTERNAL_ERROR";
            case 10:
                return "DEVELOPER_ERROR";
            case 13:
                return PLVWebview.MESSAGE_ERROR;
            case 14:
                return "INTERRUPTED";
            case 15:
                return "TIMEOUT";
            case 16:
                return "CANCELED";
            case 17:
                return "API_NOT_CONNECTED";
            case 18:
                return "DEAD_CLIENT";
        }
    }
}
