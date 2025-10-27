package androidx.core.graphics;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseArray;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.collection.LongSparseArray;
import androidx.core.content.res.FontResourcesParserCompat;
import java.lang.reflect.Field;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
final class WeightTypefaceApi14 {
    private static final String NATIVE_INSTANCE_FIELD = "native_instance";
    private static final String TAG = "WeightTypeface";
    private static final Field sNativeInstance;
    private static final Object sWeightCacheLock;

    @GuardedBy("sWeightCacheLock")
    private static final LongSparseArray<SparseArray<Typeface>> sWeightTypefaceCache;

    static {
        Field declaredField;
        try {
            declaredField = Typeface.class.getDeclaredField(NATIVE_INSTANCE_FIELD);
            declaredField.setAccessible(true);
        } catch (Exception e2) {
            Log.e(TAG, e2.getClass().getName(), e2);
            declaredField = null;
        }
        sNativeInstance = declaredField;
        sWeightTypefaceCache = new LongSparseArray<>(3);
        sWeightCacheLock = new Object();
    }

    private WeightTypefaceApi14() {
    }

    @Nullable
    public static Typeface createWeightStyle(@NonNull TypefaceCompatBaseImpl typefaceCompatBaseImpl, @NonNull Context context, @NonNull Typeface typeface, int i2, boolean z2) {
        if (!isPrivateApiAvailable()) {
            return null;
        }
        int i3 = (i2 << 1) | (z2 ? 1 : 0);
        synchronized (sWeightCacheLock) {
            long nativeInstance = getNativeInstance(typeface);
            LongSparseArray<SparseArray<Typeface>> longSparseArray = sWeightTypefaceCache;
            SparseArray<Typeface> sparseArray = longSparseArray.get(nativeInstance);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>(4);
                longSparseArray.put(nativeInstance, sparseArray);
            } else {
                Typeface typeface2 = sparseArray.get(i3);
                if (typeface2 != null) {
                    return typeface2;
                }
            }
            Typeface bestFontFromFamily = getBestFontFromFamily(typefaceCompatBaseImpl, context, typeface, i2, z2);
            if (bestFontFromFamily == null) {
                bestFontFromFamily = platformTypefaceCreate(typeface, i2, z2);
            }
            sparseArray.put(i3, bestFontFromFamily);
            return bestFontFromFamily;
        }
    }

    @Nullable
    private static Typeface getBestFontFromFamily(@NonNull TypefaceCompatBaseImpl typefaceCompatBaseImpl, @NonNull Context context, @NonNull Typeface typeface, int i2, boolean z2) {
        FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamily = typefaceCompatBaseImpl.getFontFamily(typeface);
        if (fontFamily == null) {
            return null;
        }
        return typefaceCompatBaseImpl.createFromFontFamilyFilesResourceEntry(context, fontFamily, context.getResources(), i2, z2);
    }

    private static long getNativeInstance(@NonNull Typeface typeface) {
        try {
            return ((Number) sNativeInstance.get(typeface)).longValue();
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }

    private static boolean isPrivateApiAvailable() {
        return sNativeInstance != null;
    }

    private static Typeface platformTypefaceCreate(Typeface typeface, int i2, boolean z2) {
        int i3 = 1;
        boolean z3 = i2 >= 600;
        if (!z3 && !z2) {
            i3 = 0;
        } else if (!z3) {
            i3 = 2;
        } else if (z2) {
            i3 = 3;
        }
        return Typeface.create(typeface, i3);
    }
}
