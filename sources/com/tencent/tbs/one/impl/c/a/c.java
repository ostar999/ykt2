package com.tencent.tbs.one.impl.c.a;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import com.tencent.tbs.one.impl.a.g;
import com.tencent.tbs.one.impl.a.o;
import com.tencent.tbs.one.optional.TBSOneStandaloneService;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class c {

    /* renamed from: a, reason: collision with root package name */
    public static Boolean f21814a;

    public static File a(String str, String str2) {
        return new File(str, str2 + ".opt");
    }

    public static ClassLoader a(final Context context, File[] fileArr, final String str, final String str2, ClassLoader classLoader, boolean z2, String[] strArr, boolean z3) {
        boolean z4;
        boolean z5 = true;
        if (fileArr[0].getAbsolutePath().contains("fusion")) {
            File file = fileArr[0];
            if (Build.VERSION.SDK_INT >= 29) {
                try {
                    String name = file.getName();
                    String strReplace = name.replace(".jar", ".odex");
                    String strReplace2 = name.replace(".jar", ".vdex");
                    String strReplace3 = name.replace(".jar", ".art");
                    String parent = file.getParent();
                    StringBuilder sb = new StringBuilder();
                    sb.append(parent);
                    String str3 = File.separator;
                    sb.append(str3);
                    sb.append("oat");
                    sb.append(str3);
                    sb.append("arm64");
                    sb.append(str3);
                    sb.append(strReplace);
                    File file2 = new File(sb.toString());
                    if (file2.exists()) {
                        com.tencent.tbs.one.impl.a.d.c(file2);
                    }
                    File file3 = new File(parent + str3 + "oat" + str3 + "arm64" + str3 + strReplace2);
                    if (file3.exists()) {
                        com.tencent.tbs.one.impl.a.d.c(file3);
                    }
                    File file4 = new File(parent + str3 + "oat" + str3 + "arm64" + str3 + strReplace3);
                    if (file4.exists()) {
                        com.tencent.tbs.one.impl.a.d.c(file4);
                    }
                    g.a("deleteOdexFileIfNeeded finished odexFile=" + file2.getAbsolutePath() + " vdexFile=" + file3.getAbsolutePath() + " artFile=" + file4.getAbsolutePath(), new Object[0]);
                } catch (Exception e2) {
                    g.a("Exception in deleteOdexFileIfNeeded", e2.getMessage());
                }
            }
        }
        g.a("DexUtils createClassLoader isSealedClassLoaderDisabled = " + z2 + ", isAsyncDexOptimizationEnabled = " + z3, new Object[0]);
        g.a("Creating class loader from %s, optimizedDirectory: %s, librarySearchPath: %s, parent: %s, unsealedPackages: %s, isAsyncDexOptimizationEnabled: %b", fileArr[0].getAbsolutePath(), str, str2, classLoader, strArr, Boolean.valueOf(z3));
        if (z3) {
            if (f21814a == null) {
                int i2 = Build.VERSION.SDK_INT;
                if (i2 > 25) {
                    g.a("API level %d does not support dex optimization", Integer.valueOf(i2));
                } else {
                    String property = System.getProperty("java.vm.version");
                    if (property == null || !property.startsWith("2")) {
                        g.a("VM version %s does not support dex optimization", property);
                    } else {
                        g.a("API level %d and VM version %s supports dex optimization", Integer.valueOf(i2), property);
                        z4 = true;
                        f21814a = Boolean.valueOf(z4);
                    }
                }
                z4 = false;
                f21814a = Boolean.valueOf(z4);
            }
            if (f21814a.booleanValue()) {
                String name2 = fileArr[0].getName();
                boolean zExists = a(str, name2).exists();
                Object[] objArr = new Object[2];
                objArr[0] = name2;
                objArr[1] = zExists ? "has" : "has not";
                g.a("The dex %s %s optimized", objArr);
                if (zExists && b(str, name2)) {
                    z5 = false;
                }
                if (z5) {
                    ClassLoader pathClassLoader = z2 ? new PathClassLoader(a(fileArr), str2, classLoader) : new e(fileArr[0].getAbsolutePath(), null, str2, classLoader, strArr);
                    final File file5 = fileArr[0];
                    o.c(new Runnable() { // from class: com.tencent.tbs.one.impl.c.a.c.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            g.a("Starting standalone service to optimize dex %s", file5.getAbsolutePath());
                            Intent intent = new Intent(context, (Class<?>) TBSOneStandaloneService.class);
                            intent.putExtra(TBSOneStandaloneService.IMPL_CLASS_NAME_KEY, b.class.getName());
                            intent.putExtra("dexPath", file5.getAbsolutePath());
                            intent.putExtra("dexName", file5.getName());
                            intent.putExtra("optimizedDirectory", str);
                            intent.putExtra("librarySearchPath", str2);
                            context.startService(intent);
                        }
                    });
                    return pathClassLoader;
                }
            }
        }
        return z2 ? new f(a(fileArr), str, str2, classLoader) : new e(fileArr[0].getAbsolutePath(), str, str2, classLoader, strArr);
    }

    public static String a(File[] fileArr) {
        String absolutePath = fileArr[0].getAbsolutePath();
        if (fileArr.length >= 2) {
            for (int i2 = 1; i2 < fileArr.length; i2++) {
                File file = fileArr[i2];
                if (file != null && file.exists()) {
                    absolutePath = absolutePath + ":" + fileArr[i2].getAbsolutePath();
                }
            }
            g.a("Creating class loader from: " + absolutePath, new Object[0]);
        }
        return absolutePath;
    }

    public static boolean b(String str, String str2) {
        int iLastIndexOf;
        String name = new File(str2).getName();
        File file = new File(str, ((TextUtils.isEmpty(name) || (iLastIndexOf = name.lastIndexOf(46)) <= 0) ? new String[]{name, null} : new String[]{name.substring(0, iLastIndexOf), name.substring(iLastIndexOf + 1)})[0] + ".dex");
        if (!file.exists()) {
            g.a("The odex file %s does not exist", file.getAbsolutePath());
            return true;
        }
        if (!d.a(file)) {
            g.a("The odex file %s is not a elf file", file.getAbsolutePath());
            return true;
        }
        try {
            new d(file);
            g.a("The odex file %s is well-kept", file.getAbsolutePath());
            return true;
        } catch (IOException unused) {
            g.a("The odex file %s has broken", file.getAbsolutePath());
            return false;
        }
    }
}
