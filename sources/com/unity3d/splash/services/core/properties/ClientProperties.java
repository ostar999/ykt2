package com.unity3d.splash.services.core.properties;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import cn.hutool.crypto.KeyUtil;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.io.ByteArrayInputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes6.dex */
public class ClientProperties {
    private static final X500Principal DEBUG_CERT = new X500Principal("CN=Android Debug,O=Android,C=US");
    private static WeakReference _activity;
    private static Application _application;
    private static Context _applicationContext;
    private static String _gameId;

    public static Activity getActivity() {
        return (Activity) _activity.get();
    }

    public static String getAppName() {
        return _applicationContext.getPackageName();
    }

    public static String getAppVersion() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            return getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            DeviceLog.exception("Error getting package info", e2);
            return null;
        }
    }

    public static Application getApplication() {
        return _application;
    }

    public static Context getApplicationContext() {
        return _applicationContext;
    }

    public static String getGameId() {
        return _gameId;
    }

    public static boolean isAppDebuggable() throws IllegalAccessException, NoSuchMethodException, PackageManager.NameNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        boolean zEquals;
        if (getApplicationContext() == null) {
            return false;
        }
        PackageManager packageManager = getApplicationContext().getPackageManager();
        String packageName = getApplicationContext().getPackageName();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            int i2 = applicationInfo.flags & 2;
            applicationInfo.flags = i2;
            zEquals = i2 != 0;
            z = false;
        } catch (PackageManager.NameNotFoundException e2) {
            DeviceLog.exception("Could not find name", e2);
            zEquals = false;
        }
        if (z) {
            try {
                for (Signature signature : packageManager.getPackageInfo(packageName, 64).signatures) {
                    zEquals = ((X509Certificate) CertificateFactory.getInstance(KeyUtil.CERT_TYPE_X509).generateCertificate(new ByteArrayInputStream(signature.toByteArray()))).getSubjectX500Principal().equals(DEBUG_CERT);
                    if (zEquals) {
                        break;
                    }
                }
            } catch (PackageManager.NameNotFoundException e3) {
                DeviceLog.exception("Could not find name", e3);
            } catch (CertificateException e4) {
                DeviceLog.exception("Certificate exception", e4);
            }
        }
        return zEquals;
    }

    public static void setActivity(Activity activity) {
        _activity = new WeakReference(activity);
    }

    public static void setApplication(Application application) {
        _application = application;
    }

    public static void setApplicationContext(Context context) {
        _applicationContext = context;
    }

    public static void setGameId(String str) {
        _gameId = str;
    }
}
