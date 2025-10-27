package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.CrashUtils;
import com.google.android.gms.dynamite.DynamiteModule;
import java.lang.reflect.Method;

/* loaded from: classes3.dex */
public class ProviderInstaller {
    public static final String PROVIDER_NAME = "GmsCore_OpenSSL";
    private static final GoogleApiAvailabilityLight zziv = GoogleApiAvailabilityLight.getInstance();
    private static final Object lock = new Object();
    private static Method zziw = null;

    public interface ProviderInstallListener {
        void onProviderInstallFailed(int i2, Intent intent);

        void onProviderInstalled();
    }

    public static void installIfNeeded(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        Preconditions.checkNotNull(context, "Context must not be null");
        zziv.verifyGooglePlayServicesIsAvailable(context, 11925000);
        Context contextZzk = zzk(context);
        if (contextZzk == null) {
            contextZzk = zzl(context);
        }
        if (contextZzk == null) {
            Log.e("ProviderInstaller", "Failed to get remote context");
            throw new GooglePlayServicesNotAvailableException(8);
        }
        synchronized (lock) {
            try {
                if (zziw == null) {
                    zziw = contextZzk.getClassLoader().loadClass("com.google.android.gms.common.security.ProviderInstallerImpl").getMethod("insertProvider", Context.class);
                }
                zziw.invoke(null, contextZzk);
            } catch (Exception e2) {
                e = e2;
                Throwable cause = e.getCause();
                if (Log.isLoggable("ProviderInstaller", 6)) {
                    String strValueOf = String.valueOf(cause == null ? e.getMessage() : cause.getMessage());
                    Log.e("ProviderInstaller", strValueOf.length() != 0 ? "Failed to install provider: ".concat(strValueOf) : new String("Failed to install provider: "));
                }
                if (cause != null) {
                    e = cause;
                }
                CrashUtils.addDynamiteErrorToDropBox(context, e);
                throw new GooglePlayServicesNotAvailableException(8);
            } finally {
            }
        }
    }

    public static void installIfNeededAsync(Context context, ProviderInstallListener providerInstallListener) {
        Preconditions.checkNotNull(context, "Context must not be null");
        Preconditions.checkNotNull(providerInstallListener, "Listener must not be null");
        Preconditions.checkMainThread("Must be called on the UI thread");
        new zza(context, providerInstallListener).execute(new Void[0]);
    }

    @Nullable
    private static Context zzk(Context context) {
        try {
            return DynamiteModule.load(context, DynamiteModule.PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING, "providerinstaller").getModuleContext();
        } catch (DynamiteModule.LoadingException e2) {
            String strValueOf = String.valueOf(e2.getMessage());
            Log.w("ProviderInstaller", strValueOf.length() != 0 ? "Failed to load providerinstaller module: ".concat(strValueOf) : new String("Failed to load providerinstaller module: "));
            return null;
        }
    }

    @Nullable
    private static Context zzl(Context context) {
        try {
            return GooglePlayServicesUtilLight.getRemoteContext(context);
        } catch (Resources.NotFoundException e2) {
            String strValueOf = String.valueOf(e2.getMessage());
            Log.w("ProviderInstaller", strValueOf.length() != 0 ? "Failed to load GMS Core context for providerinstaller: ".concat(strValueOf) : new String("Failed to load GMS Core context for providerinstaller: "));
            CrashUtils.addDynamiteErrorToDropBox(context, e2);
            return null;
        }
    }
}
