package androidx.core.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Map;

@RequiresApi(26)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {
    private static final String ABORT_CREATION_METHOD = "abortCreation";
    private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
    private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String FREEZE_METHOD = "freeze";
    private static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String TAG = "TypefaceCompatApi26Impl";
    protected final Method mAbortCreation;
    protected final Method mAddFontFromAssetManager;
    protected final Method mAddFontFromBuffer;
    protected final Method mCreateFromFamiliesWithDefault;
    protected final Class<?> mFontFamily;
    protected final Constructor<?> mFontFamilyCtor;
    protected final Method mFreeze;

    public TypefaceCompatApi26Impl() {
        Class<?> clsObtainFontFamily;
        Constructor<?> constructorObtainFontFamilyCtor;
        Method methodObtainAddFontFromAssetManagerMethod;
        Method methodObtainAddFontFromBufferMethod;
        Method methodObtainFreezeMethod;
        Method methodObtainAbortCreationMethod;
        Method methodObtainCreateFromFamiliesWithDefaultMethod;
        try {
            clsObtainFontFamily = obtainFontFamily();
            constructorObtainFontFamilyCtor = obtainFontFamilyCtor(clsObtainFontFamily);
            methodObtainAddFontFromAssetManagerMethod = obtainAddFontFromAssetManagerMethod(clsObtainFontFamily);
            methodObtainAddFontFromBufferMethod = obtainAddFontFromBufferMethod(clsObtainFontFamily);
            methodObtainFreezeMethod = obtainFreezeMethod(clsObtainFontFamily);
            methodObtainAbortCreationMethod = obtainAbortCreationMethod(clsObtainFontFamily);
            methodObtainCreateFromFamiliesWithDefaultMethod = obtainCreateFromFamiliesWithDefaultMethod(clsObtainFontFamily);
        } catch (ClassNotFoundException | NoSuchMethodException e2) {
            Log.e(TAG, "Unable to collect necessary methods for class " + e2.getClass().getName(), e2);
            clsObtainFontFamily = null;
            constructorObtainFontFamilyCtor = null;
            methodObtainAddFontFromAssetManagerMethod = null;
            methodObtainAddFontFromBufferMethod = null;
            methodObtainFreezeMethod = null;
            methodObtainAbortCreationMethod = null;
            methodObtainCreateFromFamiliesWithDefaultMethod = null;
        }
        this.mFontFamily = clsObtainFontFamily;
        this.mFontFamilyCtor = constructorObtainFontFamilyCtor;
        this.mAddFontFromAssetManager = methodObtainAddFontFromAssetManagerMethod;
        this.mAddFontFromBuffer = methodObtainAddFontFromBufferMethod;
        this.mFreeze = methodObtainFreezeMethod;
        this.mAbortCreation = methodObtainAbortCreationMethod;
        this.mCreateFromFamiliesWithDefault = methodObtainCreateFromFamiliesWithDefaultMethod;
    }

    private void abortCreation(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            this.mAbortCreation.invoke(obj, new Object[0]);
        } catch (IllegalAccessException | InvocationTargetException unused) {
        }
    }

    private boolean addFontFromAssetManager(Context context, Object obj, String str, int i2, int i3, int i4, @Nullable FontVariationAxis[] fontVariationAxisArr) {
        try {
            return ((Boolean) this.mAddFontFromAssetManager.invoke(obj, context.getAssets(), str, 0, Boolean.FALSE, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), fontVariationAxisArr)).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    private boolean addFontFromBuffer(Object obj, ByteBuffer byteBuffer, int i2, int i3, int i4) {
        try {
            return ((Boolean) this.mAddFontFromBuffer.invoke(obj, byteBuffer, Integer.valueOf(i2), null, Integer.valueOf(i3), Integer.valueOf(i4))).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    private boolean freeze(Object obj) {
        try {
            return ((Boolean) this.mFreeze.invoke(obj, new Object[0])).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    private boolean isFontFamilyPrivateAPIAvailable() {
        if (this.mAddFontFromAssetManager == null) {
            Log.w(TAG, "Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        return this.mAddFontFromAssetManager != null;
    }

    @Nullable
    private Object newFamily() {
        try {
            return this.mFontFamilyCtor.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException unused) {
            return null;
        }
    }

    @Nullable
    public Typeface createFromFamiliesWithDefault(Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        try {
            Object objNewInstance = Array.newInstance(this.mFontFamily, 1);
            Array.set(objNewInstance, 0, obj);
            return (Typeface) this.mCreateFromFamiliesWithDefault.invoke(null, objNewInstance, -1, -1);
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return null;
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatApi21Impl, androidx.core.graphics.TypefaceCompatBaseImpl
    @Nullable
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromFontFamilyFilesResourceEntry(context, fontFamilyFilesResourceEntry, resources, i2);
        }
        Object objNewFamily = newFamily();
        if (objNewFamily == null) {
            return null;
        }
        for (FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry : fontFamilyFilesResourceEntry.getEntries()) {
            if (!addFontFromAssetManager(context, objNewFamily, fontFileResourceEntry.getFileName(), fontFileResourceEntry.getTtcIndex(), fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic() ? 1 : 0, FontVariationAxis.fromFontVariationSettings(fontFileResourceEntry.getVariationSettings()))) {
                abortCreation(objNewFamily);
                return null;
            }
        }
        if (freeze(objNewFamily)) {
            return createFromFamiliesWithDefault(objNewFamily);
        }
        return null;
    }

    @Override // androidx.core.graphics.TypefaceCompatApi21Impl, androidx.core.graphics.TypefaceCompatBaseImpl
    @Nullable
    public Typeface createFromFontInfo(Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] fontInfoArr, int i2) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        Typeface typefaceCreateFromFamiliesWithDefault;
        if (fontInfoArr.length < 1) {
            return null;
        }
        if (!isFontFamilyPrivateAPIAvailable()) {
            FontsContractCompat.FontInfo fontInfoFindBestInfo = findBestInfo(fontInfoArr, i2);
            try {
                ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(fontInfoFindBestInfo.getUri(), "r", cancellationSignal);
                if (parcelFileDescriptorOpenFileDescriptor == null) {
                    if (parcelFileDescriptorOpenFileDescriptor != null) {
                        parcelFileDescriptorOpenFileDescriptor.close();
                    }
                    return null;
                }
                try {
                    Typeface typefaceBuild = new Typeface.Builder(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor()).setWeight(fontInfoFindBestInfo.getWeight()).setItalic(fontInfoFindBestInfo.isItalic()).build();
                    parcelFileDescriptorOpenFileDescriptor.close();
                    return typefaceBuild;
                } finally {
                }
            } catch (IOException unused) {
                return null;
            }
        }
        Map<Uri, ByteBuffer> fontInfoIntoByteBuffer = TypefaceCompatUtil.readFontInfoIntoByteBuffer(context, fontInfoArr, cancellationSignal);
        Object objNewFamily = newFamily();
        if (objNewFamily == null) {
            return null;
        }
        boolean z2 = false;
        for (FontsContractCompat.FontInfo fontInfo : fontInfoArr) {
            ByteBuffer byteBuffer = fontInfoIntoByteBuffer.get(fontInfo.getUri());
            if (byteBuffer != null) {
                if (!addFontFromBuffer(objNewFamily, byteBuffer, fontInfo.getTtcIndex(), fontInfo.getWeight(), fontInfo.isItalic() ? 1 : 0)) {
                    abortCreation(objNewFamily);
                    return null;
                }
                z2 = true;
            }
        }
        if (!z2) {
            abortCreation(objNewFamily);
            return null;
        }
        if (freeze(objNewFamily) && (typefaceCreateFromFamiliesWithDefault = createFromFamiliesWithDefault(objNewFamily)) != null) {
            return Typeface.create(typefaceCreateFromFamiliesWithDefault, i2);
        }
        return null;
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    @Nullable
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int i2, String str, int i3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, resources, i2, str, i3);
        }
        Object objNewFamily = newFamily();
        if (objNewFamily == null) {
            return null;
        }
        if (!addFontFromAssetManager(context, objNewFamily, str, 0, -1, -1, null)) {
            abortCreation(objNewFamily);
            return null;
        }
        if (freeze(objNewFamily)) {
            return createFromFamiliesWithDefault(objNewFamily);
        }
        return null;
    }

    @Override // androidx.core.graphics.TypefaceCompatApi21Impl, androidx.core.graphics.TypefaceCompatBaseImpl
    @NonNull
    public Typeface createWeightStyle(@NonNull Context context, @NonNull Typeface typeface, int i2, boolean z2) {
        Typeface typefaceCreateWeightStyle;
        try {
            typefaceCreateWeightStyle = WeightTypefaceApi26.createWeightStyle(typeface, i2, z2);
        } catch (RuntimeException unused) {
            typefaceCreateWeightStyle = null;
        }
        return typefaceCreateWeightStyle == null ? super.createWeightStyle(context, typeface, i2, z2) : typefaceCreateWeightStyle;
    }

    public Method obtainAbortCreationMethod(Class<?> cls) throws NoSuchMethodException {
        return cls.getMethod(ABORT_CREATION_METHOD, new Class[0]);
    }

    public Method obtainAddFontFromAssetManagerMethod(Class<?> cls) throws NoSuchMethodException {
        Class<?> cls2 = Integer.TYPE;
        return cls.getMethod(ADD_FONT_FROM_ASSET_MANAGER_METHOD, AssetManager.class, String.class, cls2, Boolean.TYPE, cls2, cls2, cls2, FontVariationAxis[].class);
    }

    public Method obtainAddFontFromBufferMethod(Class<?> cls) throws NoSuchMethodException {
        Class<?> cls2 = Integer.TYPE;
        return cls.getMethod(ADD_FONT_FROM_BUFFER_METHOD, ByteBuffer.class, cls2, FontVariationAxis[].class, cls2, cls2);
    }

    public Method obtainCreateFromFamiliesWithDefaultMethod(Class<?> cls) throws NoSuchMethodException, SecurityException {
        Class cls2 = Integer.TYPE;
        Method declaredMethod = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, Array.newInstance(cls, 1).getClass(), cls2, cls2);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }

    public Class<?> obtainFontFamily() throws ClassNotFoundException {
        return Class.forName(FONT_FAMILY_CLASS);
    }

    public Constructor<?> obtainFontFamilyCtor(Class<?> cls) throws NoSuchMethodException {
        return cls.getConstructor(new Class[0]);
    }

    public Method obtainFreezeMethod(Class<?> cls) throws NoSuchMethodException {
        return cls.getMethod(FREEZE_METHOD, new Class[0]);
    }
}
