package com.tencent.smtt.export.external;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import dalvik.system.DexClassLoader;
import dalvik.system.VMStack;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/* loaded from: classes6.dex */
public class DexLoader {
    private static final String ANDROIDX_PACKAGE_PREFIX = "androidx";
    private static final String CHROMIUM_J_N = "J.N";
    private static final String JAVACORE_PACKAGE_PREFIX = "org.chromium";
    private static final String TAF_PACKAGE_PREFIX = "com.taf";
    private static final String TAG = "DexLoader";
    private static final String TBS_FUSION_DEX = "tbs_jars_fusion_dex";
    private static final String TBS_WEBVIEW_DEX = "webview_dex";
    private static final String TENCENT_PACKAGE_PREFIX = "com.tencent";
    static boolean mCanUseDexLoaderProviderService = true;
    private static boolean mMttClassUseCorePrivate = false;
    private static boolean mUseSpeedyClassLoader = false;
    private static boolean mUseTbsCorePrivateClassLoader = false;
    private DexClassLoader mClassLoader;

    public static class TbsCorePrivateClassLoader extends DexClassLoader {
        public TbsCorePrivateClassLoader(String str, String str2, String str3, ClassLoader classLoader) {
            super(str, str2, str3, classLoader);
        }

        @Override // java.lang.ClassLoader
        public Class<?> loadClass(String str, boolean z2) throws ClassNotFoundException {
            ClassLoader parent;
            if (str == null) {
                return super.loadClass(str, z2);
            }
            boolean z3 = str.startsWith(DexLoader.JAVACORE_PACKAGE_PREFIX) || str.startsWith(DexLoader.TENCENT_PACKAGE_PREFIX) || str.startsWith(DexLoader.ANDROIDX_PACKAGE_PREFIX) || str.startsWith(DexLoader.TAF_PACKAGE_PREFIX) || str.equals(DexLoader.CHROMIUM_J_N);
            if (DexLoader.mMttClassUseCorePrivate) {
                z3 = z3 || str.startsWith(DexLoader.TENCENT_PACKAGE_PREFIX) || str.startsWith(DexLoader.TAF_PACKAGE_PREFIX);
            }
            if (!z3) {
                return super.loadClass(str, z2);
            }
            Class<?> clsFindLoadedClass = findLoadedClass(str);
            if (clsFindLoadedClass != null) {
                return clsFindLoadedClass;
            }
            try {
                Log.d(DexLoader.TAG, "WebCoreClassLoader - loadClass(" + str + "," + z2 + ")...");
                clsFindLoadedClass = findClass(str);
            } catch (ClassNotFoundException unused) {
            }
            return (clsFindLoadedClass != null || (parent = getParent()) == null) ? clsFindLoadedClass : parent.loadClass(str);
        }
    }

    public DexLoader(Context context, String str, String str2) {
        this(context, new String[]{str}, str2);
    }

    public DexLoader(Context context, String[] strArr, String str) {
        this((String) null, context, strArr, str);
    }

    public DexLoader(Context context, String[] strArr, String str, DexLoader dexLoader) throws Throwable {
        DexClassLoader classLoader = dexLoader.getClassLoader();
        for (String str2 : strArr) {
            classLoader = createDexClassLoader(str2, str, context.getApplicationInfo().nativeLibraryDir, classLoader, context);
            this.mClassLoader = classLoader;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.ClassLoader] */
    public DexLoader(Context context, String[] strArr, String str, String str2) throws Throwable {
        ?? classLoader = context.getClassLoader();
        String str3 = context.getApplicationInfo().nativeLibraryDir;
        str3 = TextUtils.isEmpty(str2) ? str3 : str3 + File.pathSeparator + str2;
        DexClassLoader dexClassLoaderCreateDexClassLoader = classLoader;
        for (String str4 : strArr) {
            dexClassLoaderCreateDexClassLoader = createDexClassLoader(str4, str, str3, dexClassLoaderCreateDexClassLoader, context);
            this.mClassLoader = dexClassLoaderCreateDexClassLoader;
        }
    }

    public DexLoader(String str, Context context, String[] strArr, String str2) {
        this(str, context, strArr, str2, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v1, types: [java.lang.ClassLoader] */
    /* JADX WARN: Type inference failed for: r12v4, types: [java.lang.ClassLoader] */
    public DexLoader(String str, Context context, String[] strArr, String str2, Map<String, Object> map) throws Throwable {
        initTbsSettings(map);
        DexClassLoader callingClassLoader = VMStack.getCallingClassLoader();
        DexClassLoader classLoader = callingClassLoader == 0 ? context.getClassLoader() : callingClassLoader;
        Log.d("dexloader", "Set base classLoader for DexClassLoader: " + classLoader);
        DexClassLoader dexClassLoaderCreateDexClassLoader = classLoader;
        for (String str3 : strArr) {
            dexClassLoaderCreateDexClassLoader = createDexClassLoader(str3, str2, str, dexClassLoaderCreateDexClassLoader, context);
            this.mClassLoader = dexClassLoaderCreateDexClassLoader;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x01d5 A[PHI: r15
      0x01d5: PHI (r15v5 com.tencent.smtt.utils.m) = (r15v4 com.tencent.smtt.utils.m), (r15v12 com.tencent.smtt.utils.m) binds: [B:33:0x01d3, B:22:0x01c2] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x020a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private dalvik.system.DexClassLoader createDexClassLoader(java.lang.String r17, java.lang.String r18, java.lang.String r19, java.lang.ClassLoader r20, android.content.Context r21) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 614
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.export.external.DexLoader.createDexClassLoader(java.lang.String, java.lang.String, java.lang.String, java.lang.ClassLoader, android.content.Context):dalvik.system.DexClassLoader");
    }

    public static void delete(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        for (File file2 : fileArrListFiles) {
            delete(file2);
        }
        file.delete();
    }

    public static String getFileNameNoEx(String str) {
        int iLastIndexOf;
        return (str == null || str.length() <= 0 || (iLastIndexOf = str.lastIndexOf(46)) <= -1 || iLastIndexOf >= str.length()) ? str : str.substring(0, iLastIndexOf);
    }

    public static void initTbsSettings(Map<String, Object> map) {
        Log.d(TAG, "initTbsSettings - " + map);
        if (map != null) {
            try {
                Object obj = map.get(TbsCoreSettings.TBS_SETTINGS_USE_PRIVATE_CLASSLOADER);
                if (obj instanceof Boolean) {
                    mUseTbsCorePrivateClassLoader = ((Boolean) obj).booleanValue();
                }
                Object obj2 = map.get(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER);
                if (obj2 instanceof Boolean) {
                    mUseSpeedyClassLoader = ((Boolean) obj2).booleanValue();
                }
                Object obj3 = map.get(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE);
                if (obj3 instanceof Boolean) {
                    mCanUseDexLoaderProviderService = ((Boolean) obj3).booleanValue();
                }
                Object obj4 = map.get(TbsCoreSettings.TBS_SETTINGS_PRAVITE_MTT_CLASSES);
                if (obj4 instanceof Boolean) {
                    mMttClassUseCorePrivate = ((Boolean) obj4).booleanValue();
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private boolean shouldUseTbsCorePrivateClassLoader(String str) {
        if (mUseTbsCorePrivateClassLoader) {
            return str.contains(TBS_FUSION_DEX) || str.contains(TBS_WEBVIEW_DEX);
        }
        return false;
    }

    public DexClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    public Object getStaticField(String str, String str2) {
        try {
            Field field = this.mClassLoader.loadClass(str).getField(str2);
            field.setAccessible(true);
            return field.get(null);
        } catch (Throwable th) {
            Log.e(getClass().getSimpleName(), "'" + str + "' get field '" + str2 + "' failed", th);
            return null;
        }
    }

    public Object invokeMethod(Object obj, String str, String str2, Class<?>[] clsArr, Object... objArr) {
        try {
            Method method = this.mClassLoader.loadClass(str).getMethod(str2, clsArr);
            method.setAccessible(true);
            return method.invoke(obj, objArr);
        } catch (Throwable th) {
            Log.e(getClass().getSimpleName(), "Not a crash, just message for help." + str + " invoke method '" + str2 + "' failed", th);
            return null;
        }
    }

    public Object invokeStaticMethod(String str, String str2, Class<?>[] clsArr, Object... objArr) {
        try {
            Method method = this.mClassLoader.loadClass(str).getMethod(str2, clsArr);
            method.setAccessible(true);
            return method.invoke(null, objArr);
        } catch (Throwable th) {
            if (str2 == null || !str2.equalsIgnoreCase("initTesRuntimeEnvironment")) {
                Log.i(getClass().getSimpleName(), "Not a crash, just message for help." + str + " invoke static method '" + str2 + "' failed", th);
                return null;
            }
            Log.e(getClass().getSimpleName(), "Not a crash, just message for help. " + str + " invoke static method '" + str2 + "' failed", th);
            return th;
        }
    }

    public Class<?> loadClass(String str) {
        try {
            return this.mClassLoader.loadClass(str);
        } catch (Throwable th) {
            Log.e(getClass().getSimpleName(), "loadClass '" + str + "' failed", th);
            return null;
        }
    }

    public Object newInstance(String str) {
        try {
            return this.mClassLoader.loadClass(str).newInstance();
        } catch (Throwable th) {
            Log.e(getClass().getSimpleName(), "create " + str + " instance failed", th);
            return null;
        }
    }

    public Object newInstance(String str, Class<?>[] clsArr, Object... objArr) {
        try {
            return this.mClassLoader.loadClass(str).getConstructor(clsArr).newInstance(objArr);
        } catch (Throwable th) {
            if ("com.tencent.smtt.webkit.adapter.X5WebViewAdapter".equalsIgnoreCase(str)) {
                Log.e(getClass().getSimpleName(), "'newInstance " + str + " failed", th);
                return th;
            }
            Log.e(getClass().getSimpleName(), "create '" + str + "' instance failed", th);
            return null;
        }
    }

    public void setStaticField(String str, String str2, Object obj) {
        try {
            Field field = this.mClassLoader.loadClass(str).getField(str2);
            field.setAccessible(true);
            field.set(null, obj);
        } catch (Throwable th) {
            Log.e(getClass().getSimpleName(), "'" + str + "' set field '" + str2 + "' failed", th);
        }
    }
}
