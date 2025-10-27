package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import cn.hutool.core.text.StrPool;
import com.google.android.gms.common.R;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.util.VisibleForTesting;
import javax.annotation.concurrent.GuardedBy;

@KeepForSdk
@Deprecated
/* loaded from: classes3.dex */
public final class GoogleServices {
    private static final Object sLock = new Object();

    @GuardedBy("sLock")
    private static GoogleServices zzay;
    private final String zzaz;
    private final Status zzba;
    private final boolean zzbb;
    private final boolean zzbc;

    @VisibleForTesting
    @KeepForSdk
    public GoogleServices(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("google_app_measurement_enable", TypedValues.Custom.S_INT, resources.getResourcePackageName(R.string.common_google_play_services_unknown_issue));
        boolean z2 = true;
        if (identifier != 0) {
            boolean z3 = resources.getInteger(identifier) != 0;
            this.zzbc = !z3;
            z2 = z3;
        } else {
            this.zzbc = false;
        }
        this.zzbb = z2;
        String strZzc = zzp.zzc(context);
        strZzc = strZzc == null ? new StringResourceValueReader(context).getString("google_app_id") : strZzc;
        if (TextUtils.isEmpty(strZzc)) {
            this.zzba = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
            this.zzaz = null;
        } else {
            this.zzaz = strZzc;
            this.zzba = Status.RESULT_SUCCESS;
        }
    }

    @KeepForSdk
    private static GoogleServices checkInitialized(String str) {
        GoogleServices googleServices;
        synchronized (sLock) {
            googleServices = zzay;
            if (googleServices == null) {
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 34);
                sb.append("Initialize must be called before ");
                sb.append(str);
                sb.append(StrPool.DOT);
                throw new IllegalStateException(sb.toString());
            }
        }
        return googleServices;
    }

    @VisibleForTesting
    @KeepForSdk
    public static void clearInstanceForTest() {
        synchronized (sLock) {
            zzay = null;
        }
    }

    @KeepForSdk
    public static String getGoogleAppId() {
        return checkInitialized("getGoogleAppId").zzaz;
    }

    @KeepForSdk
    public static Status initialize(Context context, String str, boolean z2) {
        Preconditions.checkNotNull(context, "Context must not be null.");
        Preconditions.checkNotEmpty(str, "App ID must be nonempty.");
        synchronized (sLock) {
            GoogleServices googleServices = zzay;
            if (googleServices != null) {
                return googleServices.checkGoogleAppId(str);
            }
            GoogleServices googleServices2 = new GoogleServices(str, z2);
            zzay = googleServices2;
            return googleServices2.zzba;
        }
    }

    @KeepForSdk
    public static boolean isMeasurementEnabled() {
        GoogleServices googleServicesCheckInitialized = checkInitialized("isMeasurementEnabled");
        return googleServicesCheckInitialized.zzba.isSuccess() && googleServicesCheckInitialized.zzbb;
    }

    @KeepForSdk
    public static boolean isMeasurementExplicitlyDisabled() {
        return checkInitialized("isMeasurementExplicitlyDisabled").zzbc;
    }

    @VisibleForTesting
    @KeepForSdk
    public final Status checkGoogleAppId(String str) {
        String str2 = this.zzaz;
        if (str2 == null || str2.equals(str)) {
            return Status.RESULT_SUCCESS;
        }
        String str3 = this.zzaz;
        StringBuilder sb = new StringBuilder(String.valueOf(str3).length() + 97);
        sb.append("Initialize was called with two different Google App IDs.  Only the first app ID will be used: '");
        sb.append(str3);
        sb.append("'.");
        return new Status(10, sb.toString());
    }

    @KeepForSdk
    public static Status initialize(Context context) {
        Status status;
        Preconditions.checkNotNull(context, "Context must not be null.");
        synchronized (sLock) {
            if (zzay == null) {
                zzay = new GoogleServices(context);
            }
            status = zzay.zzba;
        }
        return status;
    }

    @VisibleForTesting
    @KeepForSdk
    public GoogleServices(String str, boolean z2) {
        this.zzaz = str;
        this.zzba = Status.RESULT_SUCCESS;
        this.zzbb = z2;
        this.zzbc = !z2;
    }
}
