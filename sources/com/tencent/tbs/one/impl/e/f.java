package com.tencent.tbs.one.impl.e;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.tencent.tbs.one.TBSOneException;
import com.tencent.tbs.one.impl.a.k;
import com.tencent.tbs.one.impl.a.l;
import com.tencent.tbs.one.impl.common.d;
import com.tencent.tbs.one.impl.common.e;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public final class f {
    public static int a(int i2, int i3, int i4) {
        return (int) (((Math.min(Math.max(0, i2), 100) / 100.0f) * (i4 - i3)) + i3);
    }

    public static int a(com.tencent.tbs.one.impl.common.d dVar, String str) {
        d.a aVarB;
        if (dVar == null || (aVarB = dVar.b(str)) == null) {
            return -1;
        }
        return aVarB.f21994c;
    }

    public static String a(String str, String str2) {
        return str.substring(str2.length(), str.length() - 3);
    }

    public static MessageDigest a() throws TBSOneException {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e2) {
            throw new TBSOneException(106, "Failed to find md5 algorithm, error: " + e2.getMessage(), e2);
        }
    }

    public static void a(Context context) {
        com.tencent.tbs.one.impl.a.g.a("Cleaning proc directory", new Object[0]);
        File[] fileArrListFiles = com.tencent.tbs.one.impl.common.f.b(context).listFiles();
        if (fileArrListFiles == null) {
            com.tencent.tbs.one.impl.a.g.a("Empty proc directory", new Object[0]);
            return;
        }
        int iMyPid = Process.myPid();
        if (fileArrListFiles.length == 1 && fileArrListFiles[0].getName().equals(String.valueOf(iMyPid))) {
            com.tencent.tbs.one.impl.a.g.a("No need to clean", new Object[0]);
            return;
        }
        List<ActivityManager.RunningAppProcessInfo> listE = com.tencent.tbs.one.impl.a.e.e(context);
        if (listE == null) {
            com.tencent.tbs.one.impl.a.g.a("Cannot get running process infos, disabled is %s", Boolean.valueOf(com.tencent.tbs.one.impl.a.e.e()));
            return;
        }
        for (File file : fileArrListFiles) {
            int iIntValue = Integer.valueOf(file.getName()).intValue();
            if (iIntValue != iMyPid) {
                Iterator<ActivityManager.RunningAppProcessInfo> it = listE.iterator();
                boolean z2 = false;
                while (it.hasNext()) {
                    if (it.next().pid == iIntValue) {
                        z2 = true;
                    }
                }
                if (!z2) {
                    com.tencent.tbs.one.impl.a.d.a(file);
                }
            }
        }
    }

    public static void a(File file) {
        try {
            if (file == null) {
                com.tencent.tbs.one.impl.a.g.a("setDirectoryExcutableIfNeeded,dir=null,return", new Object[0]);
                return;
            }
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                com.tencent.tbs.one.impl.a.g.a("setDirectoryExcutableIfNeeded,subdir=null,return", new Object[0]);
                return;
            }
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    file2.setExecutable(true, false);
                    a(file2);
                }
            }
        } catch (Exception e2) {
            com.tencent.tbs.one.impl.a.g.a("setDirectoryExcutableIfNeeded,exception=%s", Log.getStackTraceString(e2));
        }
    }

    public static void a(File file, int i2) throws Throwable {
        a(file, i2, false);
    }

    public static void a(File file, int i2, boolean z2) throws Throwable {
        com.tencent.tbs.one.impl.common.e eVarA = com.tencent.tbs.one.impl.common.e.a(new File(file, "MANIFEST"));
        if (z2) {
            String str = eVarA.f22004g;
            if (str.equals("64") && !"arm64-v8a".equals(com.tencent.tbs.one.impl.a.e.d())) {
                throw new TBSOneException(322, "Failed to verify cpuType,expected " + com.tencent.tbs.one.impl.a.e.d() + " but was " + str);
            }
            if (str.equals("32") && "arm64-v8a".equals(com.tencent.tbs.one.impl.a.e.d())) {
                throw new TBSOneException(322, "Failed to verify cpuType,expected " + com.tencent.tbs.one.impl.a.e.d() + " but was " + str);
            }
        }
        int i3 = eVarA.f21998a;
        if (i2 != i3) {
            throw new TBSOneException(417, "Failed to verify version code, expected " + i2 + " but was " + i3);
        }
        e.a[] aVarArr = eVarA.f22002e;
        if (aVarArr != null) {
            MessageDigest messageDigestA = null;
            for (e.a aVar : aVarArr) {
                File file2 = new File(file, aVar.f22006a);
                if (!file2.exists()) {
                    throw new TBSOneException(105, "Failed to find component file " + file2.getAbsolutePath());
                }
                String str2 = aVar.f22007b;
                if (!TextUtils.isEmpty(str2)) {
                    if (messageDigestA == null) {
                        messageDigestA = a();
                    }
                    a(messageDigestA, file2, str2);
                }
            }
        }
    }

    public static void a(File file, long j2) {
        File fileE = com.tencent.tbs.one.impl.common.f.e(file, ".failed");
        try {
            if (!fileE.exists() && !fileE.createNewFile()) {
                com.tencent.tbs.one.impl.a.g.c("Failed to touch file %s, cannot create new file", fileE.getAbsolutePath());
            } else {
                if (fileE.setLastModified(j2)) {
                    return;
                }
                com.tencent.tbs.one.impl.a.g.c("Failed to touch file %s, cannot set last modified with %d", fileE.getAbsolutePath(), Long.valueOf(j2));
            }
        } catch (IOException e2) {
            com.tencent.tbs.one.impl.a.g.c("Failed to touch file %s", fileE.getAbsolutePath(), e2);
        }
    }

    public static void a(File file, File file2) throws Throwable {
        File[] fileArrListFiles;
        String strA;
        File file3;
        int i2;
        int i3;
        int i4;
        int i5 = 2;
        int i6 = 0;
        int i7 = 1;
        com.tencent.tbs.one.impl.a.g.a("Extracting from installation package %s to %s", file.getAbsolutePath(), file2.getAbsolutePath());
        File file4 = new File(file, "jni");
        File file5 = new File(file, "lib");
        String[] strArr = Build.SUPPORTED_ABIS;
        int length = strArr.length;
        int i8 = 0;
        while (i8 < length) {
            String str = strArr[i8];
            File file6 = new File(file4, str);
            if (file6.exists() && (fileArrListFiles = file6.listFiles()) != null && fileArrListFiles.length > 0) {
                Object[] objArr = new Object[i7];
                objArr[i6] = str;
                com.tencent.tbs.one.impl.a.g.a("  ABI: %s", objArr);
                int length2 = fileArrListFiles.length;
                int i9 = i6;
                while (i9 < length2) {
                    File file7 = fileArrListFiles[i9];
                    String name = file7.getName();
                    if (name.startsWith("libtbs=")) {
                        strA = new String(Base64.decode(a(name, "libtbs="), i5), com.tencent.tbs.one.impl.common.b.f21979b);
                        if (strA.contains(File.separator)) {
                            strA = strA.substring(strA.indexOf(File.separatorChar) + 1);
                        }
                    } else {
                        strA = name.startsWith("libtbs") ? a(name, "libtbs") : name;
                    }
                    File file8 = new File(file2, strA);
                    if (file8.exists()) {
                        i2 = 2;
                        i3 = 0;
                        i4 = 1;
                        com.tencent.tbs.one.impl.a.g.a("    %s -> %s, already exist", name, strA);
                        file3 = file4;
                    } else {
                        file3 = file4;
                        i2 = 2;
                        i3 = 0;
                        i4 = 1;
                        com.tencent.tbs.one.impl.a.g.a("    %s -> %s", name, strA);
                        try {
                            com.tencent.tbs.one.impl.a.d.b(file7, file8);
                        } catch (IOException e2) {
                            throw new TBSOneException(316, "Failed to extract component files in " + file2.getAbsolutePath() + ", error: " + e2.getMessage(), e2);
                        }
                    }
                    i9++;
                    i5 = i2;
                    i6 = i3;
                    i7 = i4;
                    file4 = file3;
                }
            }
            File file9 = file4;
            int i10 = i6;
            int i11 = i7;
            int i12 = i5;
            File file10 = new File(file5, str);
            if (file10.exists()) {
                b(file10, file2);
            }
            i8++;
            i5 = i12;
            i6 = i10;
            i7 = i11;
            file4 = file9;
        }
        b(new File(file, "assets/webkit"), file2);
    }

    public static void a(InputStream inputStream, String str, long j2, File file, File file2, l.a aVar) throws Throwable {
        com.tencent.tbs.one.impl.a.g.a("Unzipping from download stream to %s, sdcard backup file is %s", file.getAbsolutePath(), file2);
        MessageDigest messageDigestA = !TextUtils.isEmpty(str) ? a() : null;
        l lVar = messageDigestA != null ? new l(new DigestInputStream(inputStream, messageDigestA), j2) : new l(inputStream, j2);
        lVar.f21759b = aVar;
        com.tencent.tbs.one.impl.a.g.a("installationUtils unzipFromDownloadStream try to unzipStream ", new Object[0]);
        try {
            com.tencent.tbs.one.impl.a.d.a(lVar, file, file2);
            com.tencent.tbs.one.impl.a.g.a("installationUtils unzipFromDownloadStream contentLength: " + j2, new Object[0]);
            if (j2 > 0) {
                long j3 = lVar.f21758a;
                if (j3 != j2) {
                    throw new TBSOneException(221, "Failed to verify download stream length, expected " + j2 + " but was " + j3);
                }
            }
            if (messageDigestA != null) {
                String strA = com.tencent.tbs.one.impl.a.e.a(messageDigestA.digest());
                if (strA.equals(str)) {
                    return;
                }
                throw new TBSOneException(108, "Failed to verify download stream md5, expected " + str + " but was " + strA);
            }
        } catch (IOException e2) {
            throw new TBSOneException(315, "Failed to unzip online component to " + file.getAbsolutePath() + ", error: " + e2.getMessage(), e2);
        }
    }

    public static void a(MessageDigest messageDigest, File file, String str) throws TBSOneException {
        try {
            com.tencent.tbs.one.impl.a.d.a(new DigestInputStream(new FileInputStream(file), messageDigest), com.tencent.tbs.one.impl.a.j.f21752a);
            String strA = com.tencent.tbs.one.impl.a.e.a(messageDigest.digest());
            if (strA.equals(str)) {
                return;
            }
            throw new TBSOneException(108, "Failed to verify md5 for component file " + file.getAbsolutePath() + ", expected " + str + " but was " + strA);
        } catch (IOException e2) {
            throw new TBSOneException(107, "Failed to compute md5 for component file " + file.getAbsolutePath() + ", error: " + e2.getMessage(), e2);
        }
    }

    public static void b(File file) {
        com.tencent.tbs.one.impl.a.g.a("shareFileIfNeeded,directory=%s", file);
        if (file != null) {
            try {
                if (file.exists()) {
                    file.setReadable(true, false);
                    if (file.isDirectory()) {
                        for (File file2 : file.listFiles()) {
                            b(file2);
                        }
                    }
                }
            } catch (Exception e2) {
                com.tencent.tbs.one.impl.a.g.a("shareFileIfNeeded,exception=%s", Log.getStackTraceString(e2));
            }
        }
    }

    public static void b(File file, File file2) throws Throwable {
        File[] fileArrListFiles;
        try {
            if (!file.exists() || (fileArrListFiles = file.listFiles()) == null || fileArrListFiles.length <= 0) {
                return;
            }
            for (File file3 : fileArrListFiles) {
                com.tencent.tbs.one.impl.a.d.b(file3, new File(file2, file3.getName()));
            }
        } catch (IOException e2) {
            throw new TBSOneException(316, "extractFromUnzipDirToTarget failed,unzip Dir=" + file + ";target=" + file2 + ", error: " + e2.getMessage(), e2);
        }
    }

    public static int c(File file) throws NumberFormatException {
        int i2;
        File[] fileArrListFiles = file.listFiles(new FileFilter() { // from class: com.tencent.tbs.one.impl.e.f.1
            @Override // java.io.FileFilter
            public final boolean accept(File file2) {
                return file2.isDirectory();
            }
        });
        if (fileArrListFiles == null) {
            return -1;
        }
        int i3 = -1;
        for (File file2 : fileArrListFiles) {
            if (g(file2)) {
                try {
                    i2 = Integer.parseInt(file2.getName());
                } catch (Exception e2) {
                    com.tencent.tbs.one.impl.a.g.c("Failed to parse component version from path %s", file2.getAbsolutePath(), e2);
                    i2 = -1;
                }
                if (i2 != -1 && i2 > i3) {
                    i3 = i2;
                }
            }
        }
        return i3;
    }

    public static void d(File file) throws Throwable {
        com.tencent.tbs.one.impl.common.d dVarA;
        com.tencent.tbs.one.impl.common.d dVar;
        com.tencent.tbs.one.impl.common.d dVar2;
        String name = file.getName();
        int i2 = 0;
        com.tencent.tbs.one.impl.a.g.a("[%s] Cleaning useless components", name);
        File fileB = com.tencent.tbs.one.impl.common.f.b(file);
        int i3 = 2;
        if (!fileB.exists()) {
            com.tencent.tbs.one.impl.a.g.a("[%s] %s no DEPS file exists", name, "Early out for cleanup useless components,");
            return;
        }
        final k kVarA = k.a(com.tencent.tbs.one.impl.common.f.e(fileB, ".lock"));
        if (kVarA == null) {
            com.tencent.tbs.one.impl.a.g.a("[%s] %s the DEPS installation lock is busy", name, "Early out for cleanup useless components,");
            return;
        }
        File fileC = com.tencent.tbs.one.impl.common.f.c(file);
        final k kVarA2 = k.a(com.tencent.tbs.one.impl.common.f.e(fileC, ".lock"));
        Runnable runnable = new Runnable() { // from class: com.tencent.tbs.one.impl.e.f.2
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                kVarA.a();
                k kVar = kVarA2;
                if (kVar != null) {
                    kVar.a();
                }
            }
        };
        if (kVarA2 == null) {
            com.tencent.tbs.one.impl.a.g.a("[%s] %s the update lock is busy", name, "Early out for cleanup useless components,");
            runnable.run();
            return;
        }
        try {
            com.tencent.tbs.one.impl.common.d dVarA2 = com.tencent.tbs.one.impl.common.d.a(fileB);
            if (fileC.exists()) {
                try {
                    dVarA = com.tencent.tbs.one.impl.common.d.a(fileC);
                } catch (TBSOneException e2) {
                    com.tencent.tbs.one.impl.a.g.c("[%s] Failed to parse latest DEPS %s", name, " to clean useless components", e2);
                    runnable.run();
                    return;
                }
            } else {
                dVarA = null;
            }
            File[] fileArrListFiles = com.tencent.tbs.one.impl.common.f.d(file).listFiles();
            if (fileArrListFiles == null) {
                runnable.run();
                return;
            }
            int length = fileArrListFiles.length;
            int i4 = 0;
            while (i4 < length) {
                File file2 = fileArrListFiles[i4];
                String name2 = file2.getName();
                int iA = a(dVarA2, name2);
                int iA2 = a(dVarA, name2);
                if (iA == -1 && iA2 == -1) {
                    com.tencent.tbs.one.impl.a.d.a(file2);
                } else {
                    File[] fileArrListFiles2 = file2.listFiles();
                    if (fileArrListFiles2 != null) {
                        int length2 = fileArrListFiles2.length;
                        int i5 = i2;
                        while (i5 < length2) {
                            File file3 = fileArrListFiles2[i5];
                            boolean zIsDirectory = file3.isDirectory();
                            String name3 = file3.getName();
                            if (zIsDirectory) {
                                if (!name3.equals(String.valueOf(iA)) && !name3.equals(String.valueOf(iA2))) {
                                    Object[] objArr = new Object[i3];
                                    objArr[0] = name;
                                    objArr[1] = file3.getAbsolutePath();
                                    com.tencent.tbs.one.impl.a.g.a("[%s] Deleting unreferenced component version directory %s", objArr);
                                    com.tencent.tbs.one.impl.a.d.a(file3);
                                }
                                dVar = dVarA2;
                                dVar2 = dVarA;
                            } else if (name3.endsWith(".incomplete")) {
                                dVar = dVarA2;
                                dVar2 = dVarA;
                                if (!new File(file3.getParent(), name3.substring(0, name3.length() - 11)).exists()) {
                                    com.tencent.tbs.one.impl.a.g.a("[%s] Deleting unused incomplete flag file %s", name, file3.getAbsolutePath());
                                    com.tencent.tbs.one.impl.a.d.a(file3);
                                }
                            } else {
                                dVar = dVarA2;
                                dVar2 = dVarA;
                                if (!name3.endsWith(".failed")) {
                                    com.tencent.tbs.one.impl.a.g.a("[%s] Deleting unknown file %s", name, file3.getAbsolutePath());
                                } else if (System.currentTimeMillis() - file3.lastModified() >= 86400000) {
                                    com.tencent.tbs.one.impl.a.g.a("[%s] Deleting expired failed flag file %s", name, file3.getAbsolutePath());
                                }
                                com.tencent.tbs.one.impl.a.d.a(file3);
                            }
                            i5++;
                            dVarA = dVar2;
                            dVarA2 = dVar;
                            i3 = 2;
                        }
                    }
                }
                i4++;
                dVarA = dVarA;
                dVarA2 = dVarA2;
                i2 = 0;
                i3 = 2;
            }
            runnable.run();
        } catch (TBSOneException e3) {
            com.tencent.tbs.one.impl.a.g.c("[%s] Failed to parse current DEPS %s", name, " to clean useless components", e3);
            runnable.run();
        }
    }

    public static void e(File file) {
        File fileE = com.tencent.tbs.one.impl.common.f.e(file, ".incomplete");
        if (fileE.exists()) {
            return;
        }
        com.tencent.tbs.one.impl.a.d.b(fileE);
    }

    public static void f(File file) {
        File fileE = com.tencent.tbs.one.impl.common.f.e(file, ".incomplete");
        if (fileE.exists()) {
            com.tencent.tbs.one.impl.a.d.c(fileE);
        }
    }

    public static boolean g(File file) {
        return !com.tencent.tbs.one.impl.common.f.e(file, ".incomplete").exists();
    }
}
