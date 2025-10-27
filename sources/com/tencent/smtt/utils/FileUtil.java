package com.tencent.smtt.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import com.hjq.permissions.Permission;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsLogReport;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.lingala.zip4j.util.InternalZipConstants;

@SuppressLint({"NewApi"})
/* loaded from: classes6.dex */
public class FileUtil {

    /* renamed from: a, reason: collision with root package name */
    public static String f21422a = null;

    /* renamed from: b, reason: collision with root package name */
    public static final a f21423b = new a() { // from class: com.tencent.smtt.utils.FileUtil.2
        @Override // com.tencent.smtt.utils.FileUtil.a
        public boolean a(File file, File file2) {
            return file.length() == file2.length() && file.lastModified() == file2.lastModified();
        }
    };

    /* renamed from: c, reason: collision with root package name */
    private static final int f21424c = 4;

    /* renamed from: d, reason: collision with root package name */
    private static RandomAccessFile f21425d;

    public interface a {
        boolean a(File file, File file2);
    }

    public interface b {
        boolean a(InputStream inputStream, ZipEntry zipEntry, String str) throws Exception;
    }

    public static long a(InputStream inputStream, OutputStream outputStream) throws IOException, OutOfMemoryError {
        if (inputStream == null) {
            return -1L;
        }
        byte[] bArr = new byte[4096];
        long j2 = 0;
        while (true) {
            int i2 = inputStream.read(bArr);
            if (-1 == i2) {
                return j2;
            }
            outputStream.write(bArr, 0, i2);
            j2 += i2;
        }
    }

    public static File a(Context context, String str) throws IOException {
        String str2;
        File file = new File(context.getFilesDir(), "tbs");
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.canWrite()) {
            File file2 = new File(file, str);
            if (!file2.exists()) {
                try {
                    file2.createNewFile();
                } catch (IOException e2) {
                    str2 = "getPermanentTbsFile -- exception: " + e2;
                }
            }
            return file2;
        }
        str2 = "getPermanentTbsFile -- no permission!";
        TbsLog.e("FileHelper", str2);
        return null;
    }

    public static File a(Context context, boolean z2, String str) throws IOException {
        String strC = c(context);
        if (strC == null) {
            return null;
        }
        File file = new File(strC);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!file.canWrite()) {
            return null;
        }
        File file2 = new File(file, str);
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            }
        }
        return file2;
    }

    public static String a(Context context, int i2) {
        return a(context, context.getApplicationInfo().packageName, i2, true);
    }

    public static String a(Context context, String str, int i2, boolean z2) {
        String str2;
        if (context == null || !a(context)) {
            return "";
        }
        try {
            str2 = Environment.getExternalStorageDirectory() + File.separator;
        } catch (Exception e2) {
            TbsLog.i(e2);
            str2 = "";
        }
        switch (i2) {
            case 1:
                if (!str2.equals("")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(SocializeProtocolConstants.PROTOCOL_KEY_TENCENT);
                    String str3 = File.separator;
                    sb.append(str3);
                    sb.append("tbs");
                    sb.append(str3);
                    sb.append(str);
                    break;
                }
                break;
            case 2:
                if (!str2.equals("")) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str2);
                    sb2.append("tbs");
                    String str4 = File.separator;
                    sb2.append(str4);
                    sb2.append("backup");
                    sb2.append(str4);
                    sb2.append(str);
                    break;
                }
                break;
            case 3:
                if (!str2.equals("")) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str2);
                    sb3.append(SocializeProtocolConstants.PROTOCOL_KEY_TENCENT);
                    String str5 = File.separator;
                    sb3.append(str5);
                    sb3.append("tbs");
                    sb3.append(str5);
                    sb3.append("backup");
                    sb3.append(str5);
                    sb3.append(str);
                    break;
                }
                break;
            case 4:
                if (!str2.equals("")) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str2);
                    sb4.append(SocializeProtocolConstants.PROTOCOL_KEY_TENCENT);
                    String str6 = File.separator;
                    sb4.append(str6);
                    sb4.append("tbs");
                    sb4.append(str6);
                    sb4.append("stable");
                    sb4.append(str6);
                    sb4.append(str);
                    String string = sb4.toString();
                    if (z2) {
                        File file = new File(string);
                        if (!file.exists() || !file.canWrite()) {
                            if (!file.exists()) {
                                try {
                                    file.mkdirs();
                                } catch (SecurityException e3) {
                                    TbsLog.i(e3);
                                }
                                if (!file.canWrite()) {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                } else {
                    break;
                }
                break;
            case 5:
                if (!str2.equals("")) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(str2);
                    sb5.append(SocializeProtocolConstants.PROTOCOL_KEY_TENCENT);
                    String str7 = File.separator;
                    sb5.append(str7);
                    sb5.append("tbs");
                    sb5.append(str7);
                    sb5.append(str);
                    break;
                }
                break;
            case 6:
                String str8 = f21422a;
                if (str8 == null) {
                    String strB = b(context, "tbslog");
                    f21422a = strB;
                    break;
                }
                break;
            case 7:
                if (!str2.equals("")) {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(str2);
                    sb6.append(SocializeProtocolConstants.PROTOCOL_KEY_TENCENT);
                    String str9 = File.separator;
                    sb6.append(str9);
                    sb6.append("tbs");
                    sb6.append(str9);
                    sb6.append("backup");
                    sb6.append(str9);
                    sb6.append("core");
                    break;
                }
                break;
        }
        return "";
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v2, types: [java.nio.channels.spi.AbstractInterruptibleChannel] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v9, types: [java.nio.channels.spi.AbstractInterruptibleChannel] */
    public static FileLock a(Context context, FileOutputStream fileOutputStream) throws Throwable {
        FileChannel channel;
        try {
            if (fileOutputStream == 0) {
                return null;
            }
            try {
                channel = fileOutputStream.getChannel();
            } catch (OverlappingFileLockException e2) {
                e = e2;
                channel = null;
            } catch (Exception e3) {
                e = e3;
                channel = null;
            } catch (Throwable th) {
                fileOutputStream = 0;
                th = th;
                if (fileOutputStream != 0) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable unused) {
                    }
                }
                throw th;
            }
            try {
                FileLock fileLockTryLock = channel.tryLock();
                fileOutputStream = channel;
                if (fileLockTryLock.isValid()) {
                    try {
                        channel.close();
                    } catch (Throwable unused2) {
                    }
                    return fileLockTryLock;
                }
            } catch (OverlappingFileLockException e4) {
                e = e4;
                e.printStackTrace();
                fileOutputStream = channel;
                if (channel != null) {
                    fileOutputStream.close();
                }
                return null;
            } catch (Exception e5) {
                e = e5;
                e.printStackTrace();
                if (channel != null) {
                    fileOutputStream = channel;
                    fileOutputStream.close();
                }
                return null;
            }
            try {
                fileOutputStream.close();
            } catch (Throwable unused3) {
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static synchronized void a(Context context, FileLock fileLock) {
        TbsLog.i("FileHelper", "releaseTbsCoreRenameFileLock -- lock: " + fileLock);
        FileChannel fileChannelChannel = fileLock.channel();
        if (fileChannelChannel != null && fileChannelChannel.isOpen()) {
            try {
                fileLock.release();
                fileChannelChannel.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void a(File file, boolean z2) {
        a(file, z2, false);
    }

    public static void a(File file, boolean z2, String str) {
        TbsLog.i("FileUtils", "delete file,ignore=" + z2 + "except" + str + file + Log.getStackTraceString(new Throwable()));
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
            if (!file2.getName().equals(str)) {
                a(file2, z2);
            }
        }
        if (z2) {
            return;
        }
        file.delete();
    }

    public static void a(File file, boolean z2, boolean z3) {
        TbsLog.i("FileUtils", "delete file,ignore=" + z2 + "isSoftLink=" + z3);
        if (file == null) {
            return;
        }
        if (z3 || file.exists()) {
            if ((z3 && !file.isDirectory()) || file.isFile()) {
                file.delete();
                return;
            }
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                return;
            }
            for (File file2 : fileArrListFiles) {
                a(file2, z2, z3);
            }
            if (z2) {
                return;
            }
            file.delete();
        }
    }

    public static void a(FileLock fileLock, FileOutputStream fileOutputStream) throws IOException {
        if (fileLock != null) {
            try {
                FileChannel fileChannelChannel = fileLock.channel();
                if (fileChannelChannel != null && fileChannelChannel.isOpen()) {
                    fileLock.release();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    public static boolean a(Context context) {
        return context != null && context.getApplicationContext().checkSelfPermission(Permission.WRITE_EXTERNAL_STORAGE) == 0 && context.getApplicationContext().checkSelfPermission(Permission.READ_EXTERNAL_STORAGE) == 0;
    }

    public static boolean a(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists() && file.isDirectory()) {
            return true;
        }
        b(file);
        return file.mkdirs();
    }

    public static boolean a(File file, File file2) throws Exception {
        return a(file.getPath(), file2.getPath());
    }

    public static boolean a(File file, File file2, FileFilter fileFilter) throws Exception {
        return a(file, file2, fileFilter, f21423b);
    }

    public static boolean a(File file, File file2, FileFilter fileFilter, a aVar) throws Exception {
        if (file == null || file2 == null) {
            return false;
        }
        TbsLog.e("FileHelper", "copyFiles src is " + file.getAbsolutePath() + " dst is " + file2.getAbsolutePath());
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return b(file, file2, fileFilter, aVar);
        }
        File[] fileArrListFiles = file.listFiles(fileFilter);
        if (fileArrListFiles == null) {
            return false;
        }
        boolean z2 = true;
        for (File file3 : fileArrListFiles) {
            if (!a(file3, new File(file2, file3.getName()), fileFilter)) {
                z2 = false;
            }
        }
        return z2;
    }

    private static boolean a(String str, long j2, long j3, long j4) throws Exception {
        FileInputStream fileInputStream;
        Throwable th;
        File file = new File(str);
        if (file.length() != j2) {
            TbsLog.e("FileHelper", "file size doesn't match: " + file.length() + " vs " + j2);
            return true;
        }
        try {
            fileInputStream = new FileInputStream(file);
        } catch (Throwable th2) {
            fileInputStream = null;
            th = th2;
        }
        try {
            CRC32 crc32 = new CRC32();
            byte[] bArr = new byte[8192];
            while (true) {
                int i2 = fileInputStream.read(bArr);
                if (i2 <= 0) {
                    break;
                }
                crc32.update(bArr, 0, i2);
            }
            long value = crc32.getValue();
            TbsLog.i("FileHelper", "" + file.getName() + ": crc = " + value + ", zipCrc = " + j4);
            fileInputStream.close();
            return value != j4;
        } catch (Throwable th3) {
            th = th3;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            throw th;
        }
    }

    @SuppressLint({"InlinedApi"})
    public static boolean a(String str, String str2) throws Exception {
        return a(str, str2, Build.CPU_ABI, Build.CPU_ABI2, PropertyUtils.getQuickly("ro.product.cpu.upgradeabi", "armeabi"));
    }

    private static boolean a(String str, String str2, String str3, String str4, b bVar) throws Exception {
        ZipFile zipFile;
        ZipFile zipFile2 = null;
        try {
            zipFile = new ZipFile(str);
        } catch (Throwable th) {
            th = th;
        }
        try {
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
            boolean z2 = false;
            boolean z3 = false;
            while (enumerationEntries.hasMoreElements()) {
                ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                String name = zipEntryNextElement.getName();
                if (name != null && !name.contains("../") && (name.startsWith("lib/") || name.startsWith("assets/"))) {
                    String strSubstring = name.substring(name.lastIndexOf(47));
                    if (strSubstring.endsWith(".so")) {
                        int i2 = f21424c;
                        if (name.regionMatches(i2, str2, 0, str2.length()) && name.charAt(str2.length() + i2) == '/') {
                            z2 = true;
                        } else if (str3 != null && name.regionMatches(i2, str3, 0, str3.length()) && name.charAt(str3.length() + i2) == '/') {
                            z3 = true;
                            if (z2) {
                            }
                        } else if (str4 != null && name.regionMatches(i2, str4, 0, str4.length()) && name.charAt(i2 + str4.length()) == '/' && !z2 && !z3) {
                        }
                    }
                    InputStream inputStream = zipFile.getInputStream(zipEntryNextElement);
                    try {
                        if (!bVar.a(inputStream, zipEntryNextElement, strSubstring.substring(1))) {
                            zipFile.close();
                            return false;
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                }
            }
            zipFile.close();
            return true;
        } catch (Throwable th2) {
            th = th2;
            zipFile2 = zipFile;
            if (zipFile2 != null) {
                zipFile2.close();
            }
            throw th;
        }
    }

    private static boolean a(String str, final String str2, String str3, String str4, String str5) throws Exception {
        return a(str, str3, str4, str5, new b() { // from class: com.tencent.smtt.utils.FileUtil.1
            @Override // com.tencent.smtt.utils.FileUtil.b
            public boolean a(InputStream inputStream, ZipEntry zipEntry, String str6) throws Exception {
                try {
                    return FileUtil.b(inputStream, zipEntry, str2, str6);
                } catch (Exception e2) {
                    throw new Exception("copyFileIfChanged Exception", e2);
                }
            }
        });
    }

    public static FileOutputStream b(Context context, boolean z2, String str) throws IOException {
        File fileA = a(context, z2, str);
        if (fileA == null) {
            return null;
        }
        try {
            return new FileOutputStream(fileA);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static String b(Context context, String str) {
        if (context == null || !a(context)) {
            return "";
        }
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            context = applicationContext;
        }
        try {
            try {
                return context.getExternalFilesDir(str).getAbsolutePath();
            } catch (Throwable unused) {
                StringBuilder sb = new StringBuilder();
                sb.append(Environment.getExternalStorageDirectory());
                String str2 = File.separator;
                sb.append(str2);
                sb.append("Android");
                sb.append(str2);
                sb.append("data");
                sb.append(str2);
                sb.append(context.getApplicationInfo().packageName);
                sb.append(str2);
                sb.append("files");
                sb.append(str2);
                sb.append(str);
                return sb.toString();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static void b(File file) {
        a(file, false);
    }

    public static boolean b(Context context) {
        long jA = s.a();
        boolean z2 = jA >= TbsDownloadConfig.getInstance(context).getDownloadMinFreeSpace();
        if (!z2) {
            TbsLog.e(TbsDownloader.LOGTAG, "[TbsApkDwonloader.hasEnoughFreeSpace] freeSpace too small,  freeSpace = " + jA);
        }
        return z2;
    }

    public static boolean b(File file, File file2) throws Exception {
        return a(file, file2, (FileFilter) null);
    }

    private static boolean b(File file, File file2, FileFilter fileFilter, a aVar) throws Exception {
        FileChannel fileChannel;
        if (file == null || file2 == null) {
            return false;
        }
        if (fileFilter != null && !fileFilter.accept(file)) {
            return false;
        }
        FileChannel channel = null;
        try {
            if (file.exists() && file.isFile()) {
                if (file2.exists()) {
                    if (aVar != null && aVar.a(file, file2)) {
                        return true;
                    }
                    b(file2);
                }
                File parentFile = file2.getParentFile();
                if (parentFile.isFile()) {
                    b(parentFile);
                }
                if (!parentFile.exists() && !parentFile.mkdirs()) {
                    return false;
                }
                FileChannel channel2 = new FileInputStream(file).getChannel();
                try {
                    channel = new FileOutputStream(file2).getChannel();
                    long size = channel2.size();
                    if (channel.transferFrom(channel2, 0L, size) == size) {
                        channel2.close();
                        channel.close();
                        return true;
                    }
                    b(file2);
                    channel2.close();
                    channel.close();
                    return false;
                } catch (Throwable th) {
                    FileChannel fileChannel2 = channel;
                    channel = channel2;
                    th = th;
                    fileChannel = fileChannel2;
                    if (channel != null) {
                        channel.close();
                    }
                    if (fileChannel != null) {
                        fileChannel.close();
                    }
                    throw th;
                }
            }
            return false;
        } catch (Throwable th2) {
            th = th2;
            fileChannel = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public static boolean b(InputStream inputStream, ZipEntry zipEntry, String str, String str2) throws Exception {
        a(new File(str));
        String str3 = str + File.separator + str2;
        File file = new File(str3);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    byte[] bArr = new byte[8192];
                    while (true) {
                        int i2 = inputStream.read(bArr);
                        if (i2 <= 0) {
                            break;
                        }
                        fileOutputStream2.write(bArr, 0, i2);
                    }
                    fileOutputStream2.close();
                    if (a(str3, zipEntry.getSize(), zipEntry.getTime(), zipEntry.getCrc())) {
                        TbsLog.e("FileHelper", "file is different: " + str3);
                        return false;
                    }
                    if (file.setLastModified(zipEntry.getTime())) {
                        return true;
                    }
                    TbsLog.e("FileHelper", "Couldn't set time for dst file " + file);
                    return true;
                } catch (IOException e2) {
                    e = e2;
                    b(file);
                    throw new IOException("Couldn't write dst file " + file, e);
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String c(Context context) {
        File file = new File(QbSdk.getTbsFolderDir(context), "core_private");
        if (file.isDirectory() || file.mkdir()) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public static boolean c(File file) {
        return file != null && file.exists() && file.isFile() && file.length() > 0;
    }

    public static int copy(InputStream inputStream, OutputStream outputStream) throws IOException, OutOfMemoryError {
        long jA = a(inputStream, outputStream);
        if (jA > 2147483647L) {
            return -1;
        }
        return (int) jA;
    }

    public static FileOutputStream d(File file) throws IOException {
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
                throw new IOException("File '" + file + "' could not be created");
            }
        } else {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        }
        return new FileOutputStream(file);
    }

    public static FileLock d(Context context) throws IOException {
        FileLock fileLockF;
        StringBuilder sb;
        String str;
        TbsLog.i("FileHelper", "getTbsCoreLoadFileLock #1");
        File fileA = a(context, "tbs_rename_lock");
        TbsLog.i("FileHelper", "getTbsCoreLoadFileLock #4 " + fileA);
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileA.getAbsolutePath(), "r");
            f21425d = randomAccessFile;
            fileLockF = randomAccessFile.getChannel().tryLock(0L, Long.MAX_VALUE, true);
        } catch (Throwable th) {
            TbsLog.e("FileHelper", "getTbsCoreLoadFileLock -- exception: " + th);
            fileLockF = null;
        }
        if (fileLockF == null) {
            fileLockF = f(context);
        }
        if (fileLockF == null) {
            sb = new StringBuilder();
            str = "getTbsCoreLoadFileLock -- failed: ";
        } else {
            sb = new StringBuilder();
            str = "getTbsCoreLoadFileLock -- success: ";
        }
        sb.append(str);
        sb.append("tbs_rename_lock");
        TbsLog.i("FileHelper", sb.toString());
        return fileLockF;
    }

    public static FileLock e(Context context) throws IOException {
        FileLock fileLockTryLock;
        StringBuilder sb;
        String str;
        File fileA = a(context, "tbs_rename_lock");
        TbsLog.i("FileHelper", "getTbsCoreRenameFileLock #1 " + fileA);
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileA.getAbsolutePath(), InternalZipConstants.WRITE_MODE);
            f21425d = randomAccessFile;
            fileLockTryLock = randomAccessFile.getChannel().tryLock(0L, Long.MAX_VALUE, false);
        } catch (Throwable unused) {
            TbsLog.e("FileHelper", "getTbsCoreRenameFileLock -- excpetion: tbs_rename_lock");
            fileLockTryLock = null;
        }
        if (fileLockTryLock == null) {
            sb = new StringBuilder();
            str = "getTbsCoreRenameFileLock -- failed: ";
        } else {
            sb = new StringBuilder();
            str = "getTbsCoreRenameFileLock -- success: ";
        }
        sb.append(str);
        sb.append("tbs_rename_lock");
        TbsLog.i("FileHelper", sb.toString());
        return fileLockTryLock;
    }

    private static FileLock f(Context context) {
        FileLock fileLockTryLock = null;
        try {
            TbsLogReport.TbsLogInfo tbsLogInfo = TbsLogReport.getInstance(context).tbsLogInfo();
            tbsLogInfo.setErrorCode(803);
            File fileA = a(context, "tbs_rename_lock");
            if (TbsDownloadConfig.getInstance(context).getTbsCoreLoadRenameFileLockWaitEnable()) {
                int i2 = 0;
                while (i2 < 20 && fileLockTryLock == null) {
                    try {
                        try {
                            Thread.sleep(100L);
                        } catch (Throwable unused) {
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    RandomAccessFile randomAccessFile = new RandomAccessFile(fileA.getAbsolutePath(), "r");
                    f21425d = randomAccessFile;
                    fileLockTryLock = randomAccessFile.getChannel().tryLock(0L, Long.MAX_VALUE, true);
                    i2++;
                }
                if (fileLockTryLock != null) {
                    tbsLogInfo.setErrorCode(802);
                } else {
                    tbsLogInfo.setErrorCode(801);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("getTbsCoreLoadFileLock,retry num=");
                sb.append(i2);
                sb.append("success=");
                sb.append(fileLockTryLock == null);
                TbsLog.i("FileHelper", sb.toString());
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return fileLockTryLock;
    }
}
