package androidx.core.os;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.ext.SdkExtensions;
import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresOptIn;
import androidx.annotation.RestrictTo;
import androidx.exifinterface.media.ExifInterface;
import java.util.Locale;

/* loaded from: classes.dex */
public class BuildCompat {

    @ChecksSdkIntAtLeast(extension = 1000000)
    @SuppressLint({"CompileTimeConstant"})
    public static final int AD_SERVICES_EXTENSION_INT;

    @ChecksSdkIntAtLeast(extension = 30)
    @SuppressLint({"CompileTimeConstant"})
    public static final int R_EXTENSION_INT;

    @ChecksSdkIntAtLeast(extension = 31)
    @SuppressLint({"CompileTimeConstant"})
    public static final int S_EXTENSION_INT;

    @ChecksSdkIntAtLeast(extension = 33)
    @SuppressLint({"CompileTimeConstant"})
    public static final int T_EXTENSION_INT;

    @RequiresApi(30)
    public static final class Extensions30Impl {
        static final int R = SdkExtensions.getExtensionVersion(30);
        static final int S = SdkExtensions.getExtensionVersion(31);
        static final int TIRAMISU = SdkExtensions.getExtensionVersion(33);
        static final int AD_SERVICES = SdkExtensions.getExtensionVersion(1000000);

        private Extensions30Impl() {
        }
    }

    @RequiresOptIn
    public @interface PrereleaseSdkCheck {
    }

    static {
        int i2 = Build.VERSION.SDK_INT;
        R_EXTENSION_INT = i2 >= 30 ? Extensions30Impl.R : 0;
        S_EXTENSION_INT = i2 >= 30 ? Extensions30Impl.S : 0;
        T_EXTENSION_INT = i2 >= 30 ? Extensions30Impl.TIRAMISU : 0;
        AD_SERVICES_EXTENSION_INT = i2 >= 30 ? Extensions30Impl.AD_SERVICES : 0;
    }

    private BuildCompat() {
    }

    @ChecksSdkIntAtLeast(api = 24)
    @Deprecated
    public static boolean isAtLeastN() {
        return Build.VERSION.SDK_INT >= 24;
    }

    @ChecksSdkIntAtLeast(api = 25)
    @Deprecated
    public static boolean isAtLeastNMR1() {
        return Build.VERSION.SDK_INT >= 25;
    }

    @ChecksSdkIntAtLeast(api = 26)
    @Deprecated
    public static boolean isAtLeastO() {
        return Build.VERSION.SDK_INT >= 26;
    }

    @ChecksSdkIntAtLeast(api = 27)
    @Deprecated
    public static boolean isAtLeastOMR1() {
        return Build.VERSION.SDK_INT >= 27;
    }

    @ChecksSdkIntAtLeast(api = 28)
    @Deprecated
    public static boolean isAtLeastP() {
        return Build.VERSION.SDK_INT >= 28;
    }

    @RestrictTo({RestrictTo.Scope.TESTS})
    public static boolean isAtLeastPreReleaseCodename(@NonNull String str, @NonNull String str2) {
        if ("REL".equals(str2)) {
            return false;
        }
        Locale locale = Locale.ROOT;
        return str2.toUpperCase(locale).compareTo(str.toUpperCase(locale)) >= 0;
    }

    @ChecksSdkIntAtLeast(api = 29)
    @Deprecated
    public static boolean isAtLeastQ() {
        return Build.VERSION.SDK_INT >= 29;
    }

    @ChecksSdkIntAtLeast(api = 30)
    @Deprecated
    public static boolean isAtLeastR() {
        return Build.VERSION.SDK_INT >= 30;
    }

    @ChecksSdkIntAtLeast(api = 31, codename = ExifInterface.LATITUDE_SOUTH)
    @SuppressLint({"RestrictedApi"})
    @Deprecated
    public static boolean isAtLeastS() {
        int i2 = Build.VERSION.SDK_INT;
        return i2 >= 31 || (i2 >= 30 && isAtLeastPreReleaseCodename(ExifInterface.LATITUDE_SOUTH, Build.VERSION.CODENAME));
    }

    @ChecksSdkIntAtLeast(api = 32, codename = "Sv2")
    @PrereleaseSdkCheck
    @Deprecated
    public static boolean isAtLeastSv2() {
        int i2 = Build.VERSION.SDK_INT;
        return i2 >= 32 || (i2 >= 31 && isAtLeastPreReleaseCodename("Sv2", Build.VERSION.CODENAME));
    }

    @ChecksSdkIntAtLeast(api = 33, codename = "Tiramisu")
    @PrereleaseSdkCheck
    public static boolean isAtLeastT() {
        int i2 = Build.VERSION.SDK_INT;
        return i2 >= 33 || (i2 >= 32 && isAtLeastPreReleaseCodename("Tiramisu", Build.VERSION.CODENAME));
    }

    @ChecksSdkIntAtLeast(codename = "UpsideDownCake")
    @PrereleaseSdkCheck
    public static boolean isAtLeastU() {
        return Build.VERSION.SDK_INT >= 33 && isAtLeastPreReleaseCodename("UpsideDownCake", Build.VERSION.CODENAME);
    }
}
