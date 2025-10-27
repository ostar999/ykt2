package androidx.core.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import androidx.annotation.AnyRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DoNotInline;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public final class ResourcesCompat {

    @AnyRes
    public static final int ID_NULL = 0;
    private static final String TAG = "ResourcesCompat";
    private static final ThreadLocal<TypedValue> sTempTypedValue = new ThreadLocal<>();

    @GuardedBy("sColorStateCacheLock")
    private static final WeakHashMap<ColorStateListCacheKey, SparseArray<ColorStateListCacheEntry>> sColorStateCaches = new WeakHashMap<>(0);
    private static final Object sColorStateCacheLock = new Object();

    @RequiresApi(15)
    public static class Api15Impl {
        private Api15Impl() {
        }

        @DoNotInline
        public static Drawable getDrawableForDensity(Resources resources, int i2, int i3) {
            return resources.getDrawableForDensity(i2, i3);
        }
    }

    @RequiresApi(21)
    public static class Api21Impl {
        private Api21Impl() {
        }

        @DoNotInline
        public static Drawable getDrawable(Resources resources, int i2, Resources.Theme theme) {
            return resources.getDrawable(i2, theme);
        }

        @DoNotInline
        public static Drawable getDrawableForDensity(Resources resources, int i2, int i3, Resources.Theme theme) {
            return resources.getDrawableForDensity(i2, i3, theme);
        }
    }

    @RequiresApi(23)
    public static class Api23Impl {
        private Api23Impl() {
        }

        @DoNotInline
        public static int getColor(Resources resources, int i2, Resources.Theme theme) {
            return resources.getColor(i2, theme);
        }

        @NonNull
        @DoNotInline
        public static ColorStateList getColorStateList(@NonNull Resources resources, @ColorRes int i2, @Nullable Resources.Theme theme) {
            return resources.getColorStateList(i2, theme);
        }
    }

    @RequiresApi(29)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        public static float getFloat(@NonNull Resources resources, @DimenRes int i2) {
            return resources.getFloat(i2);
        }
    }

    public static class ColorStateListCacheEntry {
        final Configuration mConfiguration;
        final int mThemeHash;
        final ColorStateList mValue;

        public ColorStateListCacheEntry(@NonNull ColorStateList colorStateList, @NonNull Configuration configuration, @Nullable Resources.Theme theme) {
            this.mValue = colorStateList;
            this.mConfiguration = configuration;
            this.mThemeHash = theme == null ? 0 : theme.hashCode();
        }
    }

    public static final class ColorStateListCacheKey {
        final Resources mResources;
        final Resources.Theme mTheme;

        public ColorStateListCacheKey(@NonNull Resources resources, @Nullable Resources.Theme theme) {
            this.mResources = resources;
            this.mTheme = theme;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || ColorStateListCacheKey.class != obj.getClass()) {
                return false;
            }
            ColorStateListCacheKey colorStateListCacheKey = (ColorStateListCacheKey) obj;
            return this.mResources.equals(colorStateListCacheKey.mResources) && ObjectsCompat.equals(this.mTheme, colorStateListCacheKey.mTheme);
        }

        public int hashCode() {
            return ObjectsCompat.hash(this.mResources, this.mTheme);
        }
    }

    public static abstract class FontCallback {
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public static Handler getHandler(@Nullable Handler handler) {
            return handler == null ? new Handler(Looper.getMainLooper()) : handler;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public final void callbackFailAsync(final int i2, @Nullable Handler handler) {
            getHandler(handler).post(new Runnable() { // from class: androidx.core.content.res.c
                @Override // java.lang.Runnable
                public final void run() {
                    this.f1807c.lambda$callbackFailAsync$1(i2);
                }
            });
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public final void callbackSuccessAsync(@NonNull final Typeface typeface, @Nullable Handler handler) {
            getHandler(handler).post(new Runnable() { // from class: androidx.core.content.res.b
                @Override // java.lang.Runnable
                public final void run() {
                    this.f1805c.lambda$callbackSuccessAsync$0(typeface);
                }
            });
        }

        /* renamed from: onFontRetrievalFailed, reason: merged with bridge method [inline-methods] */
        public abstract void lambda$callbackFailAsync$1(int i2);

        /* renamed from: onFontRetrieved, reason: merged with bridge method [inline-methods] */
        public abstract void lambda$callbackSuccessAsync$0(@NonNull Typeface typeface);
    }

    public static final class ThemeCompat {

        @RequiresApi(23)
        public static class Api23Impl {
            private static Method sRebaseMethod;
            private static boolean sRebaseMethodFetched;
            private static final Object sRebaseMethodLock = new Object();

            private Api23Impl() {
            }

            /* JADX WARN: Removed duplicated region for block: B:30:0x0027 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @android.annotation.SuppressLint({"BanUncheckedReflection"})
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public static void rebase(@androidx.annotation.NonNull android.content.res.Resources.Theme r6) {
                /*
                    java.lang.Object r0 = androidx.core.content.res.ResourcesCompat.ThemeCompat.Api23Impl.sRebaseMethodLock
                    monitor-enter(r0)
                    boolean r1 = androidx.core.content.res.ResourcesCompat.ThemeCompat.Api23Impl.sRebaseMethodFetched     // Catch: java.lang.Throwable -> L3c
                    r2 = 0
                    if (r1 != 0) goto L23
                    r1 = 1
                    java.lang.Class<android.content.res.Resources$Theme> r3 = android.content.res.Resources.Theme.class
                    java.lang.String r4 = "rebase"
                    java.lang.Class[] r5 = new java.lang.Class[r2]     // Catch: java.lang.NoSuchMethodException -> L19 java.lang.Throwable -> L3c
                    java.lang.reflect.Method r3 = r3.getDeclaredMethod(r4, r5)     // Catch: java.lang.NoSuchMethodException -> L19 java.lang.Throwable -> L3c
                    androidx.core.content.res.ResourcesCompat.ThemeCompat.Api23Impl.sRebaseMethod = r3     // Catch: java.lang.NoSuchMethodException -> L19 java.lang.Throwable -> L3c
                    r3.setAccessible(r1)     // Catch: java.lang.NoSuchMethodException -> L19 java.lang.Throwable -> L3c
                    goto L21
                L19:
                    r3 = move-exception
                    java.lang.String r4 = "ResourcesCompat"
                    java.lang.String r5 = "Failed to retrieve rebase() method"
                    android.util.Log.i(r4, r5, r3)     // Catch: java.lang.Throwable -> L3c
                L21:
                    androidx.core.content.res.ResourcesCompat.ThemeCompat.Api23Impl.sRebaseMethodFetched = r1     // Catch: java.lang.Throwable -> L3c
                L23:
                    java.lang.reflect.Method r1 = androidx.core.content.res.ResourcesCompat.ThemeCompat.Api23Impl.sRebaseMethod     // Catch: java.lang.Throwable -> L3c
                    if (r1 == 0) goto L3a
                    java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch: java.lang.reflect.InvocationTargetException -> L2d java.lang.IllegalAccessException -> L2f java.lang.Throwable -> L3c
                    r1.invoke(r6, r2)     // Catch: java.lang.reflect.InvocationTargetException -> L2d java.lang.IllegalAccessException -> L2f java.lang.Throwable -> L3c
                    goto L3a
                L2d:
                    r6 = move-exception
                    goto L30
                L2f:
                    r6 = move-exception
                L30:
                    java.lang.String r1 = "ResourcesCompat"
                    java.lang.String r2 = "Failed to invoke rebase() method via reflection"
                    android.util.Log.i(r1, r2, r6)     // Catch: java.lang.Throwable -> L3c
                    r6 = 0
                    androidx.core.content.res.ResourcesCompat.ThemeCompat.Api23Impl.sRebaseMethod = r6     // Catch: java.lang.Throwable -> L3c
                L3a:
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> L3c
                    return
                L3c:
                    r6 = move-exception
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> L3c
                    throw r6
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ResourcesCompat.ThemeCompat.Api23Impl.rebase(android.content.res.Resources$Theme):void");
            }
        }

        @RequiresApi(29)
        public static class Api29Impl {
            private Api29Impl() {
            }

            @DoNotInline
            public static void rebase(@NonNull Resources.Theme theme) {
                theme.rebase();
            }
        }

        private ThemeCompat() {
        }

        public static void rebase(@NonNull Resources.Theme theme) {
            if (Build.VERSION.SDK_INT >= 29) {
                Api29Impl.rebase(theme);
            } else {
                Api23Impl.rebase(theme);
            }
        }
    }

    private ResourcesCompat() {
    }

    private static void addColorStateListToCache(@NonNull ColorStateListCacheKey colorStateListCacheKey, @ColorRes int i2, @NonNull ColorStateList colorStateList, @Nullable Resources.Theme theme) {
        synchronized (sColorStateCacheLock) {
            WeakHashMap<ColorStateListCacheKey, SparseArray<ColorStateListCacheEntry>> weakHashMap = sColorStateCaches;
            SparseArray<ColorStateListCacheEntry> sparseArray = weakHashMap.get(colorStateListCacheKey);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>();
                weakHashMap.put(colorStateListCacheKey, sparseArray);
            }
            sparseArray.append(i2, new ColorStateListCacheEntry(colorStateList, colorStateListCacheKey.mResources.getConfiguration(), theme));
        }
    }

    public static void clearCachesForTheme(@NonNull Resources.Theme theme) {
        synchronized (sColorStateCacheLock) {
            Iterator<ColorStateListCacheKey> it = sColorStateCaches.keySet().iterator();
            while (it.hasNext()) {
                ColorStateListCacheKey next = it.next();
                if (next != null && theme.equals(next.mTheme)) {
                    it.remove();
                }
            }
        }
    }

    @Nullable
    private static ColorStateList getCachedColorStateList(@NonNull ColorStateListCacheKey colorStateListCacheKey, @ColorRes int i2) {
        ColorStateListCacheEntry colorStateListCacheEntry;
        Resources.Theme theme;
        synchronized (sColorStateCacheLock) {
            SparseArray<ColorStateListCacheEntry> sparseArray = sColorStateCaches.get(colorStateListCacheKey);
            if (sparseArray != null && sparseArray.size() > 0 && (colorStateListCacheEntry = sparseArray.get(i2)) != null) {
                if (colorStateListCacheEntry.mConfiguration.equals(colorStateListCacheKey.mResources.getConfiguration()) && (((theme = colorStateListCacheKey.mTheme) == null && colorStateListCacheEntry.mThemeHash == 0) || (theme != null && colorStateListCacheEntry.mThemeHash == theme.hashCode()))) {
                    return colorStateListCacheEntry.mValue;
                }
                sparseArray.remove(i2);
            }
            return null;
        }
    }

    @Nullable
    public static Typeface getCachedFont(@NonNull Context context, @FontRes int i2) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, i2, new TypedValue(), 0, null, null, false, true);
    }

    @ColorInt
    public static int getColor(@NonNull Resources resources, @ColorRes int i2, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        return Api23Impl.getColor(resources, i2, theme);
    }

    @Nullable
    public static ColorStateList getColorStateList(@NonNull Resources resources, @ColorRes int i2, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        ColorStateListCacheKey colorStateListCacheKey = new ColorStateListCacheKey(resources, theme);
        ColorStateList cachedColorStateList = getCachedColorStateList(colorStateListCacheKey, i2);
        if (cachedColorStateList != null) {
            return cachedColorStateList;
        }
        ColorStateList colorStateListInflateColorStateList = inflateColorStateList(resources, i2, theme);
        if (colorStateListInflateColorStateList == null) {
            return Api23Impl.getColorStateList(resources, i2, theme);
        }
        addColorStateListToCache(colorStateListCacheKey, i2, colorStateListInflateColorStateList, theme);
        return colorStateListInflateColorStateList;
    }

    @Nullable
    public static Drawable getDrawable(@NonNull Resources resources, @DrawableRes int i2, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        return Api21Impl.getDrawable(resources, i2, theme);
    }

    @Nullable
    public static Drawable getDrawableForDensity(@NonNull Resources resources, @DrawableRes int i2, int i3, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        return Api21Impl.getDrawableForDensity(resources, i2, i3, theme);
    }

    public static float getFloat(@NonNull Resources resources, @DimenRes int i2) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 29) {
            return Api29Impl.getFloat(resources, i2);
        }
        TypedValue typedValue = getTypedValue();
        resources.getValue(i2, typedValue, true);
        if (typedValue.type == 4) {
            return typedValue.getFloat();
        }
        throw new Resources.NotFoundException("Resource ID #0x" + Integer.toHexString(i2) + " type #0x" + Integer.toHexString(typedValue.type) + " is not valid");
    }

    @Nullable
    public static Typeface getFont(@NonNull Context context, @FontRes int i2) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, i2, new TypedValue(), 0, null, null, false, false);
    }

    @NonNull
    private static TypedValue getTypedValue() {
        ThreadLocal<TypedValue> threadLocal = sTempTypedValue;
        TypedValue typedValue = threadLocal.get();
        if (typedValue != null) {
            return typedValue;
        }
        TypedValue typedValue2 = new TypedValue();
        threadLocal.set(typedValue2);
        return typedValue2;
    }

    @Nullable
    private static ColorStateList inflateColorStateList(Resources resources, int i2, @Nullable Resources.Theme theme) {
        if (isColorInt(resources, i2)) {
            return null;
        }
        try {
            return ColorStateListInflaterCompat.createFromXml(resources, resources.getXml(i2), theme);
        } catch (Exception e2) {
            Log.w(TAG, "Failed to inflate ColorStateList, leaving it to the framework", e2);
            return null;
        }
    }

    private static boolean isColorInt(@NonNull Resources resources, @ColorRes int i2) throws Resources.NotFoundException {
        TypedValue typedValue = getTypedValue();
        resources.getValue(i2, typedValue, true);
        int i3 = typedValue.type;
        return i3 >= 28 && i3 <= 31;
    }

    private static Typeface loadFont(@NonNull Context context, int i2, @NonNull TypedValue typedValue, int i3, @Nullable FontCallback fontCallback, @Nullable Handler handler, boolean z2, boolean z3) throws Resources.NotFoundException {
        Resources resources = context.getResources();
        resources.getValue(i2, typedValue, true);
        Typeface typefaceLoadFont = loadFont(context, resources, typedValue, i2, i3, fontCallback, handler, z2, z3);
        if (typefaceLoadFont != null || fontCallback != null || z3) {
            return typefaceLoadFont;
        }
        throw new Resources.NotFoundException("Font resource ID #0x" + Integer.toHexString(i2) + " could not be retrieved.");
    }

    public static void getFont(@NonNull Context context, @FontRes int i2, @NonNull FontCallback fontCallback, @Nullable Handler handler) throws Resources.NotFoundException {
        Preconditions.checkNotNull(fontCallback);
        if (context.isRestricted()) {
            fontCallback.callbackFailAsync(-4, handler);
        } else {
            loadFont(context, i2, new TypedValue(), 0, fontCallback, handler, false, false);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.graphics.Typeface loadFont(@androidx.annotation.NonNull android.content.Context r17, android.content.res.Resources r18, @androidx.annotation.NonNull android.util.TypedValue r19, int r20, int r21, @androidx.annotation.Nullable androidx.core.content.res.ResourcesCompat.FontCallback r22, @androidx.annotation.Nullable android.os.Handler r23, boolean r24, boolean r25) {
        /*
            r0 = r18
            r1 = r19
            r4 = r20
            r11 = r22
            r12 = r23
            java.lang.String r13 = "ResourcesCompat"
            java.lang.CharSequence r2 = r1.string
            if (r2 == 0) goto Lbb
            java.lang.String r14 = r2.toString()
            java.lang.String r2 = "res/"
            boolean r2 = r14.startsWith(r2)
            r15 = -3
            r16 = 0
            if (r2 != 0) goto L25
            if (r11 == 0) goto L24
            r11.callbackFailAsync(r15, r12)
        L24:
            return r16
        L25:
            int r2 = r1.assetCookie
            r7 = r21
            android.graphics.Typeface r2 = androidx.core.graphics.TypefaceCompat.findFromCache(r0, r4, r14, r2, r7)
            if (r2 == 0) goto L35
            if (r11 == 0) goto L34
            r11.callbackSuccessAsync(r2, r12)
        L34:
            return r2
        L35:
            if (r25 == 0) goto L38
            return r16
        L38:
            java.lang.String r2 = r14.toLowerCase()     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> La0
            java.lang.String r3 = ".xml"
            boolean r2 = r2.endsWith(r3)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> La0
            if (r2 == 0) goto L6f
            android.content.res.XmlResourceParser r2 = r0.getXml(r4)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> La0
            androidx.core.content.res.FontResourcesParserCompat$FamilyResourceEntry r2 = androidx.core.content.res.FontResourcesParserCompat.parse(r2, r0)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> La0
            if (r2 != 0) goto L59
            java.lang.String r0 = "Failed to find font-family tag"
            android.util.Log.e(r13, r0)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> La0
            if (r11 == 0) goto L58
            r11.callbackFailAsync(r15, r12)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> La0
        L58:
            return r16
        L59:
            int r6 = r1.assetCookie     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> La0
            r1 = r17
            r3 = r18
            r4 = r20
            r5 = r14
            r7 = r21
            r8 = r22
            r9 = r23
            r10 = r24
            android.graphics.Typeface r0 = androidx.core.graphics.TypefaceCompat.createFromResourcesFamilyXml(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> La0
            return r0
        L6f:
            int r5 = r1.assetCookie     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> La0
            r1 = r17
            r2 = r18
            r3 = r20
            r4 = r14
            r6 = r21
            android.graphics.Typeface r0 = androidx.core.graphics.TypefaceCompat.createFromResourcesFontFile(r1, r2, r3, r4, r5, r6)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> La0
            if (r11 == 0) goto L89
            if (r0 == 0) goto L86
            r11.callbackSuccessAsync(r0, r12)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> La0
            goto L89
        L86:
            r11.callbackFailAsync(r15, r12)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> La0
        L89:
            return r0
        L8a:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Failed to read xml resource "
            r1.append(r2)
            r1.append(r14)
            java.lang.String r1 = r1.toString()
            android.util.Log.e(r13, r1, r0)
            goto Lb5
        La0:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Failed to parse xml resource "
            r1.append(r2)
            r1.append(r14)
            java.lang.String r1 = r1.toString()
            android.util.Log.e(r13, r1, r0)
        Lb5:
            if (r11 == 0) goto Lba
            r11.callbackFailAsync(r15, r12)
        Lba:
            return r16
        Lbb:
            android.content.res.Resources$NotFoundException r2 = new android.content.res.Resources$NotFoundException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "Resource \""
            r3.append(r5)
            java.lang.String r0 = r0.getResourceName(r4)
            r3.append(r0)
            java.lang.String r0 = "\" ("
            r3.append(r0)
            java.lang.String r0 = java.lang.Integer.toHexString(r20)
            r3.append(r0)
            java.lang.String r0 = ") is not a Font: "
            r3.append(r0)
            r3.append(r1)
            java.lang.String r0 = r3.toString()
            r2.<init>(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ResourcesCompat.loadFont(android.content.Context, android.content.res.Resources, android.util.TypedValue, int, int, androidx.core.content.res.ResourcesCompat$FontCallback, android.os.Handler, boolean, boolean):android.graphics.Typeface");
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Typeface getFont(@NonNull Context context, @FontRes int i2, @NonNull TypedValue typedValue, int i3, @Nullable FontCallback fontCallback) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, i2, typedValue, i3, fontCallback, null, true, false);
    }
}
