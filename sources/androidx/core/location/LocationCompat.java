package androidx.core.location;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.DoNotInline;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class LocationCompat {
    public static final String EXTRA_BEARING_ACCURACY = "bearingAccuracy";
    public static final String EXTRA_IS_MOCK = "mockLocation";
    public static final String EXTRA_MSL_ALTITUDE = "androidx.core.location.extra.MSL_ALTITUDE";
    public static final String EXTRA_MSL_ALTITUDE_ACCURACY = "androidx.core.location.extra.MSL_ALTITUDE_ACCURACY";
    public static final String EXTRA_SPEED_ACCURACY = "speedAccuracy";
    public static final String EXTRA_VERTICAL_ACCURACY = "verticalAccuracy";

    @Nullable
    private static Method sSetIsFromMockProviderMethod;

    @RequiresApi(17)
    public static class Api17Impl {
        private Api17Impl() {
        }

        @DoNotInline
        public static long getElapsedRealtimeNanos(Location location) {
            return location.getElapsedRealtimeNanos();
        }
    }

    @RequiresApi(18)
    public static class Api18Impl {
        private Api18Impl() {
        }

        @DoNotInline
        public static boolean isMock(Location location) {
            return location.isFromMockProvider();
        }
    }

    @RequiresApi(26)
    public static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        public static float getBearingAccuracyDegrees(Location location) {
            return location.getBearingAccuracyDegrees();
        }

        @DoNotInline
        public static float getSpeedAccuracyMetersPerSecond(Location location) {
            return location.getSpeedAccuracyMetersPerSecond();
        }

        @DoNotInline
        public static float getVerticalAccuracyMeters(Location location) {
            return location.getVerticalAccuracyMeters();
        }

        @DoNotInline
        public static boolean hasBearingAccuracy(Location location) {
            return location.hasBearingAccuracy();
        }

        @DoNotInline
        public static boolean hasSpeedAccuracy(Location location) {
            return location.hasSpeedAccuracy();
        }

        @DoNotInline
        public static boolean hasVerticalAccuracy(Location location) {
            return location.hasVerticalAccuracy();
        }

        @DoNotInline
        public static void setBearingAccuracyDegrees(Location location, float f2) {
            location.setBearingAccuracyDegrees(f2);
        }

        @DoNotInline
        public static void setSpeedAccuracyMetersPerSecond(Location location, float f2) {
            location.setSpeedAccuracyMetersPerSecond(f2);
        }

        @DoNotInline
        public static void setVerticalAccuracyMeters(Location location, float f2) {
            location.setVerticalAccuracyMeters(f2);
        }
    }

    private LocationCompat() {
    }

    private static boolean containsExtra(@NonNull Location location, String str) {
        Bundle extras = location.getExtras();
        return extras != null && extras.containsKey(str);
    }

    public static float getBearingAccuracyDegrees(@NonNull Location location) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.getBearingAccuracyDegrees(location);
        }
        Bundle extras = location.getExtras();
        if (extras == null) {
            return 0.0f;
        }
        return extras.getFloat(EXTRA_BEARING_ACCURACY, 0.0f);
    }

    public static long getElapsedRealtimeMillis(@NonNull Location location) {
        return TimeUnit.NANOSECONDS.toMillis(Api17Impl.getElapsedRealtimeNanos(location));
    }

    public static long getElapsedRealtimeNanos(@NonNull Location location) {
        return Api17Impl.getElapsedRealtimeNanos(location);
    }

    @FloatRange(from = 0.0d)
    public static float getMslAltitudeAccuracyMeters(@NonNull Location location) {
        Preconditions.checkState(hasMslAltitudeAccuracy(location), "The Mean Sea Level altitude accuracy of the location is not set.");
        return getOrCreateExtras(location).getFloat(EXTRA_MSL_ALTITUDE_ACCURACY);
    }

    public static double getMslAltitudeMeters(@NonNull Location location) {
        Preconditions.checkState(hasMslAltitude(location), "The Mean Sea Level altitude of the location is not set.");
        return getOrCreateExtras(location).getDouble(EXTRA_MSL_ALTITUDE);
    }

    private static Bundle getOrCreateExtras(@NonNull Location location) {
        Bundle extras = location.getExtras();
        if (extras != null) {
            return extras;
        }
        location.setExtras(new Bundle());
        return location.getExtras();
    }

    private static Method getSetIsFromMockProviderMethod() throws NoSuchMethodException, SecurityException {
        if (sSetIsFromMockProviderMethod == null) {
            Method declaredMethod = Location.class.getDeclaredMethod("setIsFromMockProvider", Boolean.TYPE);
            sSetIsFromMockProviderMethod = declaredMethod;
            declaredMethod.setAccessible(true);
        }
        return sSetIsFromMockProviderMethod;
    }

    public static float getSpeedAccuracyMetersPerSecond(@NonNull Location location) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.getSpeedAccuracyMetersPerSecond(location);
        }
        Bundle extras = location.getExtras();
        if (extras == null) {
            return 0.0f;
        }
        return extras.getFloat(EXTRA_SPEED_ACCURACY, 0.0f);
    }

    public static float getVerticalAccuracyMeters(@NonNull Location location) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.getVerticalAccuracyMeters(location);
        }
        Bundle extras = location.getExtras();
        if (extras == null) {
            return 0.0f;
        }
        return extras.getFloat(EXTRA_VERTICAL_ACCURACY, 0.0f);
    }

    public static boolean hasBearingAccuracy(@NonNull Location location) {
        return Build.VERSION.SDK_INT >= 26 ? Api26Impl.hasBearingAccuracy(location) : containsExtra(location, EXTRA_BEARING_ACCURACY);
    }

    public static boolean hasMslAltitude(@NonNull Location location) {
        return containsExtra(location, EXTRA_MSL_ALTITUDE);
    }

    public static boolean hasMslAltitudeAccuracy(@NonNull Location location) {
        return containsExtra(location, EXTRA_MSL_ALTITUDE_ACCURACY);
    }

    public static boolean hasSpeedAccuracy(@NonNull Location location) {
        return Build.VERSION.SDK_INT >= 26 ? Api26Impl.hasSpeedAccuracy(location) : containsExtra(location, EXTRA_SPEED_ACCURACY);
    }

    public static boolean hasVerticalAccuracy(@NonNull Location location) {
        return Build.VERSION.SDK_INT >= 26 ? Api26Impl.hasVerticalAccuracy(location) : containsExtra(location, EXTRA_VERTICAL_ACCURACY);
    }

    public static boolean isMock(@NonNull Location location) {
        return Api18Impl.isMock(location);
    }

    private static void removeExtra(@NonNull Location location, String str) {
        Bundle extras = location.getExtras();
        if (extras != null) {
            extras.remove(str);
            if (extras.isEmpty()) {
                location.setExtras(null);
            }
        }
    }

    public static void removeMslAltitude(@NonNull Location location) {
        removeExtra(location, EXTRA_MSL_ALTITUDE);
    }

    public static void removeMslAltitudeAccuracy(@NonNull Location location) {
        removeExtra(location, EXTRA_MSL_ALTITUDE_ACCURACY);
    }

    public static void setBearingAccuracyDegrees(@NonNull Location location, float f2) {
        if (Build.VERSION.SDK_INT >= 26) {
            Api26Impl.setBearingAccuracyDegrees(location, f2);
        } else {
            getOrCreateExtras(location).putFloat(EXTRA_BEARING_ACCURACY, f2);
        }
    }

    public static void setMock(@NonNull Location location, boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            getSetIsFromMockProviderMethod().invoke(location, Boolean.valueOf(z2));
        } catch (IllegalAccessException e2) {
            IllegalAccessError illegalAccessError = new IllegalAccessError();
            illegalAccessError.initCause(e2);
            throw illegalAccessError;
        } catch (NoSuchMethodException e3) {
            NoSuchMethodError noSuchMethodError = new NoSuchMethodError();
            noSuchMethodError.initCause(e3);
            throw noSuchMethodError;
        } catch (InvocationTargetException e4) {
            throw new RuntimeException(e4);
        }
    }

    public static void setMslAltitudeAccuracyMeters(@NonNull Location location, @FloatRange(from = 0.0d) float f2) {
        getOrCreateExtras(location).putFloat(EXTRA_MSL_ALTITUDE_ACCURACY, f2);
    }

    public static void setMslAltitudeMeters(@NonNull Location location, double d3) {
        getOrCreateExtras(location).putDouble(EXTRA_MSL_ALTITUDE, d3);
    }

    public static void setSpeedAccuracyMetersPerSecond(@NonNull Location location, float f2) {
        if (Build.VERSION.SDK_INT >= 26) {
            Api26Impl.setSpeedAccuracyMetersPerSecond(location, f2);
        } else {
            getOrCreateExtras(location).putFloat(EXTRA_SPEED_ACCURACY, f2);
        }
    }

    public static void setVerticalAccuracyMeters(@NonNull Location location, float f2) {
        if (Build.VERSION.SDK_INT >= 26) {
            Api26Impl.setVerticalAccuracyMeters(location, f2);
        } else {
            getOrCreateExtras(location).putFloat(EXTRA_VERTICAL_ACCURACY, f2);
        }
    }
}
