package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.HideFirstParty;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.yikaobang.yixue.R2;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import k.a;

@ShowFirstParty
@KeepForSdk
/* loaded from: classes3.dex */
public class GooglePlayServicesUtilLight {

    @KeepForSdk
    static final int GMS_AVAILABILITY_NOTIFICATION_ID = 10436;

    @KeepForSdk
    static final int GMS_GENERAL_ERROR_NOTIFICATION_ID = 39789;

    @KeepForSdk
    public static final String GOOGLE_PLAY_GAMES_PACKAGE = "com.google.android.play.games";

    @KeepForSdk
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";

    @KeepForSdk
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 12451000;

    @KeepForSdk
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    @VisibleForTesting
    private static boolean zzah = false;

    @VisibleForTesting
    private static boolean zzai = false;
    private static boolean zzaj = false;

    @VisibleForTesting
    private static boolean zzak = false;

    @VisibleForTesting
    @KeepForSdk
    static final AtomicBoolean sCanceledAvailabilityNotification = new AtomicBoolean();
    private static final AtomicBoolean zzal = new AtomicBoolean();

    @KeepForSdk
    public GooglePlayServicesUtilLight() {
    }

    @KeepForSdk
    @Deprecated
    public static void cancelAvailabilityErrorNotifications(Context context) {
        if (sCanceledAvailabilityNotification.getAndSet(true)) {
            return;
        }
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
            if (notificationManager != null) {
                notificationManager.cancel(10436);
            }
        } catch (SecurityException unused) {
        }
    }

    @ShowFirstParty
    @KeepForSdk
    public static void enableUsingApkIndependentContext() {
        zzal.set(true);
    }

    @KeepForSdk
    @Deprecated
    public static void ensurePlayServicesAvailable(Context context, int i2) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        int iIsGooglePlayServicesAvailable = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(context, i2);
        if (iIsGooglePlayServicesAvailable != 0) {
            Intent errorResolutionIntent = GoogleApiAvailabilityLight.getInstance().getErrorResolutionIntent(context, iIsGooglePlayServicesAvailable, AliyunLogKey.KEY_EVENT);
            StringBuilder sb = new StringBuilder(57);
            sb.append("GooglePlayServices not available due to error ");
            sb.append(iIsGooglePlayServicesAvailable);
            Log.e("GooglePlayServicesUtil", sb.toString());
            if (errorResolutionIntent != null) {
                throw new GooglePlayServicesRepairableException(iIsGooglePlayServicesAvailable, "Google Play Services not available", errorResolutionIntent);
            }
            throw new GooglePlayServicesNotAvailableException(iIsGooglePlayServicesAvailable);
        }
    }

    @ShowFirstParty
    @KeepForSdk
    @Deprecated
    public static int getApkVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionCode;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 0;
        }
    }

    @ShowFirstParty
    @KeepForSdk
    @Deprecated
    public static int getClientVersion(Context context) {
        Preconditions.checkState(true);
        return ClientLibraryUtils.getClientVersion(context, context.getPackageName());
    }

    @KeepForSdk
    @Deprecated
    public static PendingIntent getErrorPendingIntent(int i2, Context context, int i3) {
        return GoogleApiAvailabilityLight.getInstance().getErrorResolutionPendingIntent(context, i2, i3);
    }

    @VisibleForTesting
    @KeepForSdk
    @Deprecated
    public static String getErrorString(int i2) {
        return ConnectionResult.zza(i2);
    }

    @ShowFirstParty
    @KeepForSdk
    @Deprecated
    public static Intent getGooglePlayServicesAvailabilityRecoveryIntent(int i2) {
        return GoogleApiAvailabilityLight.getInstance().getErrorResolutionIntent(null, i2, null);
    }

    @KeepForSdk
    public static Context getRemoteContext(Context context) {
        try {
            return context.createPackageContext("com.google.android.gms", 3);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    @KeepForSdk
    public static Resources getRemoteResource(Context context) {
        try {
            return context.getPackageManager().getResourcesForApplication("com.google.android.gms");
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    @ShowFirstParty
    @KeepForSdk
    public static boolean honorsDebugCertificates(Context context) {
        try {
            if (!zzak) {
                PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo("com.google.android.gms", 64);
                GoogleSignatureVerifier.getInstance(context);
                if (packageInfo == null || GoogleSignatureVerifier.zza(packageInfo, false) || !GoogleSignatureVerifier.zza(packageInfo, true)) {
                    zzaj = false;
                } else {
                    zzaj = true;
                }
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Log.w("GooglePlayServicesUtil", "Cannot find Google Play services package name.", e2);
        } finally {
            zzak = true;
        }
        return zzaj || !DeviceProperties.isUserBuild();
    }

    @HideFirstParty
    @KeepForSdk
    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context) {
        return isGooglePlayServicesAvailable(context, GOOGLE_PLAY_SERVICES_VERSION_CODE);
    }

    @KeepForSdk
    @Deprecated
    public static boolean isGooglePlayServicesUid(Context context, int i2) {
        return UidVerifier.isGooglePlayServicesUid(context, i2);
    }

    @ShowFirstParty
    @KeepForSdk
    @Deprecated
    public static boolean isPlayServicesPossiblyUpdating(Context context, int i2) {
        if (i2 == 18) {
            return true;
        }
        if (i2 == 1) {
            return isUninstalledAppPossiblyUpdating(context, "com.google.android.gms");
        }
        return false;
    }

    @ShowFirstParty
    @KeepForSdk
    @Deprecated
    public static boolean isPlayStorePossiblyUpdating(Context context, int i2) {
        if (i2 == 9) {
            return isUninstalledAppPossiblyUpdating(context, "com.android.vending");
        }
        return false;
    }

    @KeepForSdk
    @TargetApi(18)
    public static boolean isRestrictedUserProfile(Context context) {
        Bundle applicationRestrictions;
        return PlatformVersion.isAtLeastJellyBeanMR2() && (applicationRestrictions = ((UserManager) context.getSystemService(PLVLinkMicManager.USER)).getApplicationRestrictions(context.getPackageName())) != null && a.f27523u.equals(applicationRestrictions.getString("restricted_profile"));
    }

    @VisibleForTesting
    @Deprecated
    @ShowFirstParty
    @KeepForSdk
    public static boolean isSidewinderDevice(Context context) {
        return DeviceProperties.isSidewinder(context);
    }

    @TargetApi(21)
    public static boolean isUninstalledAppPossiblyUpdating(Context context, String str) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo;
        boolean zEquals = str.equals("com.google.android.gms");
        if (PlatformVersion.isAtLeastLollipop()) {
            try {
                Iterator<PackageInstaller.SessionInfo> it = context.getPackageManager().getPackageInstaller().getAllSessions().iterator();
                while (it.hasNext()) {
                    if (str.equals(it.next().getAppPackageName())) {
                        return true;
                    }
                }
            } catch (Exception unused) {
                return false;
            }
        }
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(str, 8192);
        } catch (PackageManager.NameNotFoundException unused2) {
        }
        return zEquals ? applicationInfo.enabled : applicationInfo.enabled && !isRestrictedUserProfile(context);
    }

    @KeepForSdk
    @Deprecated
    public static boolean isUserRecoverableError(int i2) {
        return i2 == 1 || i2 == 2 || i2 == 3 || i2 == 9;
    }

    @KeepForSdk
    @TargetApi(19)
    @Deprecated
    public static boolean uidHasPackageName(Context context, int i2, String str) {
        return UidVerifier.uidHasPackageName(context, i2, str);
    }

    @VisibleForTesting
    private static int zza(Context context, boolean z2, int i2) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo;
        Preconditions.checkArgument(i2 >= 0);
        PackageManager packageManager = context.getPackageManager();
        if (z2) {
            try {
                packageInfo = packageManager.getPackageInfo("com.android.vending", R2.dimen.sp_57);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.w("GooglePlayServicesUtil", "Google Play Store is missing.");
                return 9;
            }
        } else {
            packageInfo = null;
        }
        try {
            PackageInfo packageInfo2 = packageManager.getPackageInfo("com.google.android.gms", 64);
            GoogleSignatureVerifier.getInstance(context);
            if (!GoogleSignatureVerifier.zza(packageInfo2, true)) {
                Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                return 9;
            }
            if (z2 && (!GoogleSignatureVerifier.zza(packageInfo, true) || !packageInfo.signatures[0].equals(packageInfo2.signatures[0]))) {
                Log.w("GooglePlayServicesUtil", "Google Play Store signature invalid.");
                return 9;
            }
            if (com.google.android.gms.common.util.zzb.zzc(packageInfo2.versionCode) >= com.google.android.gms.common.util.zzb.zzc(i2)) {
                ApplicationInfo applicationInfo = packageInfo2.applicationInfo;
                if (applicationInfo == null) {
                    try {
                        applicationInfo = packageManager.getApplicationInfo("com.google.android.gms", 0);
                    } catch (PackageManager.NameNotFoundException e2) {
                        Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.", e2);
                        return 1;
                    }
                }
                return !applicationInfo.enabled ? 3 : 0;
            }
            int i3 = packageInfo2.versionCode;
            StringBuilder sb = new StringBuilder(77);
            sb.append("Google Play services out of date.  Requires ");
            sb.append(i2);
            sb.append(" but found ");
            sb.append(i3);
            Log.w("GooglePlayServicesUtil", sb.toString());
            return 2;
        } catch (PackageManager.NameNotFoundException unused2) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 1;
        }
    }

    @KeepForSdk
    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context, int i2) {
        try {
            context.getResources().getString(R.string.common_google_play_services_unknown_issue);
        } catch (Throwable unused) {
            Log.e("GooglePlayServicesUtil", "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
        }
        if (!"com.google.android.gms".equals(context.getPackageName()) && !zzal.get()) {
            int iZzd = zzp.zzd(context);
            if (iZzd == 0) {
                throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
            }
            int i3 = GOOGLE_PLAY_SERVICES_VERSION_CODE;
            if (iZzd != i3) {
                StringBuilder sb = new StringBuilder(320);
                sb.append("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected ");
                sb.append(i3);
                sb.append(" but found ");
                sb.append(iZzd);
                sb.append(".  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
                throw new IllegalStateException(sb.toString());
            }
        }
        return zza(context, (DeviceProperties.isWearableWithoutPlayStore(context) || DeviceProperties.zzf(context)) ? false : true, i2);
    }
}
