package internal.com.getkeepsafe.relinker;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import internal.com.getkeepsafe.relinker.ReLinker;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* loaded from: classes8.dex */
public class ApkLibraryInstaller implements ReLinker.LibraryInstaller {
    private static final int COPY_BUFFER_SIZE = 4096;
    private static final int MAX_TRIES = 5;

    public static class ZipFileInZipEntry {
        public ZipEntry zipEntry;
        public ZipFile zipFile;

        public ZipFileInZipEntry(ZipFile zipFile, ZipEntry zipEntry) {
            this.zipFile = zipFile;
            this.zipEntry = zipEntry;
        }
    }

    private void closeSilently(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    private long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[4096];
        long j2 = 0;
        while (true) {
            int i2 = inputStream.read(bArr);
            if (i2 == -1) {
                outputStream.flush();
                return j2;
            }
            outputStream.write(bArr, 0, i2);
            j2 += i2;
        }
    }

    private ZipFileInZipEntry findAPKWithLibrary(Context context, String[] strArr, String str, ReLinkerInstance reLinkerInstance) throws IOException {
        String[] strArrSourceDirectories = sourceDirectories(context);
        int length = strArrSourceDirectories.length;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            ZipFile zipFile = null;
            if (i3 >= length) {
                return null;
            }
            String str2 = strArrSourceDirectories[i3];
            int i4 = i2;
            while (true) {
                int i5 = i4 + 1;
                if (i4 >= 5) {
                    break;
                }
                try {
                    zipFile = new ZipFile(new File(str2), 1);
                    break;
                } catch (IOException unused) {
                    i4 = i5;
                }
            }
            if (zipFile != null) {
                int i6 = i2;
                while (true) {
                    int i7 = i6 + 1;
                    if (i6 < 5) {
                        int length2 = strArr.length;
                        int i8 = i2;
                        while (i8 < length2) {
                            String str3 = "lib" + File.separatorChar + strArr[i8] + File.separatorChar + str;
                            Object[] objArr = new Object[2];
                            objArr[i2] = str3;
                            objArr[1] = str2;
                            reLinkerInstance.log("Looking for %s in APK %s...", objArr);
                            ZipEntry entry = zipFile.getEntry(str3);
                            if (entry != null) {
                                return new ZipFileInZipEntry(zipFile, entry);
                            }
                            i8++;
                            i2 = 0;
                        }
                        i6 = i7;
                        i2 = 0;
                    } else {
                        try {
                            zipFile.close();
                            break;
                        } catch (IOException unused2) {
                        }
                    }
                }
            }
            i3++;
            i2 = 0;
        }
    }

    private String[] getSupportedABIs(Context context, String str) {
        Pattern patternCompile = Pattern.compile("lib" + File.separatorChar + "([^\\" + File.separatorChar + "]*)" + File.separatorChar + str);
        HashSet hashSet = new HashSet();
        for (String str2 : sourceDirectories(context)) {
            try {
                Enumeration<? extends ZipEntry> enumerationEntries = new ZipFile(new File(str2), 1).entries();
                while (enumerationEntries.hasMoreElements()) {
                    Matcher matcher = patternCompile.matcher(enumerationEntries.nextElement().getName());
                    if (matcher.matches()) {
                        hashSet.add(matcher.group(1));
                    }
                }
            } catch (IOException unused) {
            }
        }
        return (String[]) hashSet.toArray(new String[hashSet.size()]);
    }

    private String[] sourceDirectories(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String[] strArr = applicationInfo.splitSourceDirs;
        if (strArr == null || strArr.length == 0) {
            return new String[]{applicationInfo.sourceDir};
        }
        String[] strArr2 = new String[strArr.length + 1];
        strArr2[0] = applicationInfo.sourceDir;
        System.arraycopy(strArr, 0, strArr2, 1, strArr.length);
        return strArr2;
    }

    @Override // internal.com.getkeepsafe.relinker.ReLinker.LibraryInstaller
    public void installLibrary(Context context, String[] strArr, String str, File file, ReLinkerInstance reLinkerInstance) throws Throwable {
        String[] supportedABIs;
        FileOutputStream fileOutputStream;
        InputStream inputStream;
        long jCopy;
        ZipFileInZipEntry zipFileInZipEntry = null;
        Closeable closeable = null;
        try {
            ZipFileInZipEntry zipFileInZipEntryFindAPKWithLibrary = findAPKWithLibrary(context, strArr, str, reLinkerInstance);
            try {
                if (zipFileInZipEntryFindAPKWithLibrary == null) {
                    try {
                        supportedABIs = getSupportedABIs(context, str);
                    } catch (Exception e2) {
                        supportedABIs = new String[]{e2.toString()};
                    }
                    throw new MissingLibraryException(str, strArr, supportedABIs);
                }
                int i2 = 0;
                while (true) {
                    int i3 = i2 + 1;
                    if (i2 >= 5) {
                        reLinkerInstance.log("FATAL! Couldn't extract the library from the APK!");
                        try {
                            ZipFile zipFile = zipFileInZipEntryFindAPKWithLibrary.zipFile;
                            if (zipFile != null) {
                                zipFile.close();
                                return;
                            }
                            return;
                        } catch (IOException unused) {
                            return;
                        }
                    }
                    reLinkerInstance.log("Found %s! Extracting...", str);
                    try {
                        if (file.exists() || file.createNewFile()) {
                            try {
                                inputStream = zipFileInZipEntryFindAPKWithLibrary.zipFile.getInputStream(zipFileInZipEntryFindAPKWithLibrary.zipEntry);
                                try {
                                    fileOutputStream = new FileOutputStream(file);
                                } catch (FileNotFoundException unused2) {
                                    fileOutputStream = null;
                                } catch (IOException unused3) {
                                    fileOutputStream = null;
                                } catch (Throwable th) {
                                    th = th;
                                    fileOutputStream = null;
                                }
                            } catch (FileNotFoundException unused4) {
                                inputStream = null;
                                fileOutputStream = null;
                            } catch (IOException unused5) {
                                inputStream = null;
                                fileOutputStream = null;
                            } catch (Throwable th2) {
                                th = th2;
                                fileOutputStream = null;
                            }
                            try {
                                jCopy = copy(inputStream, fileOutputStream);
                                fileOutputStream.getFD().sync();
                            } catch (FileNotFoundException unused6) {
                                closeSilently(inputStream);
                                closeSilently(fileOutputStream);
                                i2 = i3;
                            } catch (IOException unused7) {
                                closeSilently(inputStream);
                                closeSilently(fileOutputStream);
                                i2 = i3;
                            } catch (Throwable th3) {
                                th = th3;
                                closeable = inputStream;
                                closeSilently(closeable);
                                closeSilently(fileOutputStream);
                                throw th;
                            }
                            if (jCopy == file.length()) {
                                closeSilently(inputStream);
                                closeSilently(fileOutputStream);
                                file.setReadable(true, false);
                                file.setExecutable(true, false);
                                file.setWritable(true);
                                try {
                                    ZipFile zipFile2 = zipFileInZipEntryFindAPKWithLibrary.zipFile;
                                    if (zipFile2 != null) {
                                        zipFile2.close();
                                        return;
                                    }
                                    return;
                                } catch (IOException unused8) {
                                    return;
                                }
                            }
                            closeSilently(inputStream);
                            closeSilently(fileOutputStream);
                        }
                    } catch (IOException unused9) {
                    }
                    i2 = i3;
                }
            } catch (Throwable th4) {
                th = th4;
                zipFileInZipEntry = zipFileInZipEntryFindAPKWithLibrary;
                if (zipFileInZipEntry != null) {
                    try {
                        ZipFile zipFile3 = zipFileInZipEntry.zipFile;
                        if (zipFile3 != null) {
                            zipFile3.close();
                        }
                    } catch (IOException unused10) {
                    }
                }
                throw th;
            }
        } catch (Throwable th5) {
            th = th5;
        }
    }
}
