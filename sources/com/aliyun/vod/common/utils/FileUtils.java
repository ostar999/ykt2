package com.aliyun.vod.common.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import cn.hutool.core.text.StrPool;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.slf4j.Marker;

/* loaded from: classes2.dex */
public class FileUtils {
    public static final File[] EMPTY_FILE_ARRAY;
    private static final long FILE_COPY_BUFFER_SIZE = 31457280;
    public static final double GB = 1.073741824E9d;
    public static final double KB = 1024.0d;
    public static final double MB = 1048576.0d;
    public static final long ONE_EB = 1152921504606846976L;
    public static final BigInteger ONE_EB_BI;
    public static final long ONE_GB = 1073741824;
    public static final BigInteger ONE_GB_BI;
    public static final long ONE_KB = 1024;
    public static final BigInteger ONE_KB_BI;
    public static final long ONE_MB = 1048576;
    public static final BigInteger ONE_MB_BI;
    public static final long ONE_PB = 1125899906842624L;
    public static final BigInteger ONE_PB_BI;
    public static final long ONE_TB = 1099511627776L;
    public static final BigInteger ONE_TB_BI;
    public static final BigInteger ONE_YB;
    public static final BigInteger ONE_ZB;
    private static final Charset UTF8;

    public static class FileExistsException extends IOException {
        private static final long serialVersionUID = 1;

        public FileExistsException() {
        }

        public FileExistsException(String str) {
            super(str);
        }

        public FileExistsException(File file) {
            super("File " + file + " exists");
        }
    }

    static {
        BigInteger bigIntegerValueOf = BigInteger.valueOf(1024L);
        ONE_KB_BI = bigIntegerValueOf;
        BigInteger bigIntegerMultiply = bigIntegerValueOf.multiply(bigIntegerValueOf);
        ONE_MB_BI = bigIntegerMultiply;
        BigInteger bigIntegerMultiply2 = bigIntegerValueOf.multiply(bigIntegerMultiply);
        ONE_GB_BI = bigIntegerMultiply2;
        BigInteger bigIntegerMultiply3 = bigIntegerValueOf.multiply(bigIntegerMultiply2);
        ONE_TB_BI = bigIntegerMultiply3;
        BigInteger bigIntegerMultiply4 = bigIntegerValueOf.multiply(bigIntegerMultiply3);
        ONE_PB_BI = bigIntegerMultiply4;
        ONE_EB_BI = bigIntegerValueOf.multiply(bigIntegerMultiply4);
        BigInteger bigIntegerMultiply5 = BigInteger.valueOf(1024L).multiply(BigInteger.valueOf(1152921504606846976L));
        ONE_ZB = bigIntegerMultiply5;
        ONE_YB = bigIntegerValueOf.multiply(bigIntegerMultiply5);
        EMPTY_FILE_ARRAY = new File[0];
        UTF8 = Charset.forName("UTF-8");
    }

    public static String byteCountToDisplaySize(BigInteger bigInteger) {
        BigInteger bigInteger2 = ONE_EB_BI;
        BigInteger bigIntegerDivide = bigInteger.divide(bigInteger2);
        BigInteger bigInteger3 = BigInteger.ZERO;
        if (bigIntegerDivide.compareTo(bigInteger3) > 0) {
            return String.valueOf(bigInteger.divide(bigInteger2)) + " EB";
        }
        BigInteger bigInteger4 = ONE_PB_BI;
        if (bigInteger.divide(bigInteger4).compareTo(bigInteger3) > 0) {
            return String.valueOf(bigInteger.divide(bigInteger4)) + " PB";
        }
        BigInteger bigInteger5 = ONE_TB_BI;
        if (bigInteger.divide(bigInteger5).compareTo(bigInteger3) > 0) {
            return String.valueOf(bigInteger.divide(bigInteger5)) + " TB";
        }
        BigInteger bigInteger6 = ONE_GB_BI;
        if (bigInteger.divide(bigInteger6).compareTo(bigInteger3) > 0) {
            return String.valueOf(bigInteger.divide(bigInteger6)) + " GB";
        }
        BigInteger bigInteger7 = ONE_MB_BI;
        if (bigInteger.divide(bigInteger7).compareTo(bigInteger3) > 0) {
            return String.valueOf(bigInteger.divide(bigInteger7)) + " MB";
        }
        BigInteger bigInteger8 = ONE_KB_BI;
        if (bigInteger.divide(bigInteger8).compareTo(bigInteger3) > 0) {
            return String.valueOf(bigInteger.divide(bigInteger8)) + " KB";
        }
        return String.valueOf(bigInteger) + " bytes";
    }

    private static void checkDirectory(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }
        if (file.isDirectory()) {
            return;
        }
        throw new IllegalArgumentException(file + " is not a directory");
    }

    public static void cleanDirectory(File file) throws IOException {
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(file + " is not a directory");
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            throw new IOException("Failed to list contents of " + file);
        }
        IOException e2 = null;
        for (File file2 : fileArrListFiles) {
            try {
                forceDelete(file2);
            } catch (IOException e3) {
                e2 = e3;
            }
        }
        if (e2 != null) {
            throw e2;
        }
    }

    private static void cleanDirectoryOnExit(File file) throws IOException {
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(file + " is not a directory");
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            throw new IOException("Failed to list contents of " + file);
        }
        IOException e2 = null;
        for (File file2 : fileArrListFiles) {
            try {
                forceDeleteOnExit(file2);
            } catch (IOException e3) {
                e2 = e3;
            }
        }
        if (e2 != null) {
            throw e2;
        }
    }

    public static void clearDirectory(File file) {
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        for (File file2 : fileArrListFiles) {
            if (file2.isDirectory()) {
                deleteDirectory(file2);
            } else {
                file2.delete();
            }
        }
    }

    public static boolean contentEquals(File file, File file2) throws Throwable {
        FileInputStream fileInputStream;
        boolean zExists = file.exists();
        if (zExists != file2.exists()) {
            return false;
        }
        if (!zExists) {
            return true;
        }
        if (file.isDirectory() || file2.isDirectory()) {
            throw new IOException("Can't compare directories, only files");
        }
        if (file.length() != file2.length()) {
            return false;
        }
        if (file.getCanonicalFile().equals(file2.getCanonicalFile())) {
            return true;
        }
        FileInputStream fileInputStream2 = null;
        try {
            FileInputStream fileInputStream3 = new FileInputStream(file);
            try {
                fileInputStream = new FileInputStream(file2);
                try {
                    boolean zContentEquals = IOUtils.contentEquals(fileInputStream3, fileInputStream);
                    IOUtils.closeQuietly((InputStream) fileInputStream3);
                    IOUtils.closeQuietly((InputStream) fileInputStream);
                    return zContentEquals;
                } catch (Throwable th) {
                    th = th;
                    fileInputStream2 = fileInputStream3;
                    IOUtils.closeQuietly((InputStream) fileInputStream2);
                    IOUtils.closeQuietly((InputStream) fileInputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileInputStream = null;
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
        }
    }

    public static boolean contentEqualsIgnoreEOL(File file, File file2, String str) throws Throwable {
        InputStreamReader inputStreamReader;
        InputStreamReader inputStreamReader2;
        boolean zExists = file.exists();
        if (zExists != file2.exists()) {
            return false;
        }
        if (!zExists) {
            return true;
        }
        if (file.isDirectory() || file2.isDirectory()) {
            throw new IOException("Can't compare directories, only files");
        }
        if (file.getCanonicalFile().equals(file2.getCanonicalFile())) {
            return true;
        }
        InputStreamReader inputStreamReader3 = null;
        try {
            if (str != null) {
                InputStreamReader inputStreamReader4 = new InputStreamReader(new FileInputStream(file), str);
                try {
                    inputStreamReader2 = new InputStreamReader(new FileInputStream(file2), str);
                    inputStreamReader3 = inputStreamReader4;
                    boolean zContentEqualsIgnoreEOL = IOUtils.contentEqualsIgnoreEOL(inputStreamReader3, inputStreamReader2);
                    IOUtils.closeQuietly((Reader) inputStreamReader3);
                    IOUtils.closeQuietly((Reader) inputStreamReader2);
                    return zContentEqualsIgnoreEOL;
                } catch (Throwable th) {
                    th = th;
                    inputStreamReader = null;
                    inputStreamReader3 = inputStreamReader4;
                    IOUtils.closeQuietly((Reader) inputStreamReader3);
                    IOUtils.closeQuietly((Reader) inputStreamReader);
                    throw th;
                }
            }
            InputStreamReader inputStreamReader5 = new InputStreamReader(new FileInputStream(file));
            try {
                inputStreamReader2 = new InputStreamReader(new FileInputStream(file2));
                inputStreamReader3 = inputStreamReader5;
                try {
                    boolean zContentEqualsIgnoreEOL2 = IOUtils.contentEqualsIgnoreEOL(inputStreamReader3, inputStreamReader2);
                    IOUtils.closeQuietly((Reader) inputStreamReader3);
                    IOUtils.closeQuietly((Reader) inputStreamReader2);
                    return zContentEqualsIgnoreEOL2;
                } catch (Throwable th2) {
                    inputStreamReader = inputStreamReader2;
                    th = th2;
                    IOUtils.closeQuietly((Reader) inputStreamReader3);
                    IOUtils.closeQuietly((Reader) inputStreamReader);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                inputStreamReader = null;
                inputStreamReader3 = inputStreamReader5;
                IOUtils.closeQuietly((Reader) inputStreamReader3);
                IOUtils.closeQuietly((Reader) inputStreamReader);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            inputStreamReader = inputStreamReader3;
        }
        IOUtils.closeQuietly((Reader) inputStreamReader3);
        IOUtils.closeQuietly((Reader) inputStreamReader);
        throw th;
    }

    public static File[] convertFileCollectionToFileArray(Collection<File> collection) {
        return (File[]) collection.toArray(new File[collection.size()]);
    }

    public static void copyDirectory(File file, File file2) throws Throwable {
        copyDirectory(file, file2, true);
    }

    public static void copyDirectoryToDirectory(File file, File file2) throws Throwable {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file.exists() && !file.isDirectory()) {
            throw new IllegalArgumentException("Source '" + file2 + "' is not a directory");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!file2.exists() || file2.isDirectory()) {
            copyDirectory(file, new File(file2, file.getName()), true);
            return;
        }
        throw new IllegalArgumentException("Destination '" + file2 + "' is not a directory");
    }

    public static void copyFile(File file, File file2) throws Throwable {
        copyFile(file, file2, true);
    }

    public static void copyFileToDirectory(File file, File file2) throws Throwable {
        copyFileToDirectory(file, file2, true);
    }

    public static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        try {
            FileOutputStream fileOutputStreamOpenOutputStream = openOutputStream(file);
            try {
                IOUtils.copy(inputStream, fileOutputStreamOpenOutputStream);
                fileOutputStreamOpenOutputStream.close();
            } finally {
                IOUtils.closeQuietly((OutputStream) fileOutputStreamOpenOutputStream);
            }
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public static void copyURLToFile(URL url, File file) throws IOException {
        copyInputStreamToFile(url.openStream(), file);
    }

    public static String decodeUrl(String str) {
        int i2;
        if (str == null || str.indexOf(37) < 0) {
            return str;
        }
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length);
        int i3 = 0;
        while (i3 < length) {
            if (str.charAt(i3) == '%') {
                while (true) {
                    i2 = i3 + 3;
                    try {
                        try {
                            byteBufferAllocate.put((byte) Integer.parseInt(str.substring(i3 + 1, i2), 16));
                            if (i2 >= length) {
                                break;
                            }
                            try {
                                if (str.charAt(i2) != '%') {
                                    break;
                                }
                                i3 = i2;
                            } catch (RuntimeException unused) {
                                i3 = i2;
                                if (byteBufferAllocate.position() > 0) {
                                    byteBufferAllocate.flip();
                                    stringBuffer.append(UTF8.decode(byteBufferAllocate).toString());
                                    byteBufferAllocate.clear();
                                }
                                stringBuffer.append(str.charAt(i3));
                                i3++;
                            }
                        } finally {
                            if (byteBufferAllocate.position() > 0) {
                                byteBufferAllocate.flip();
                                stringBuffer.append(UTF8.decode(byteBufferAllocate).toString());
                                byteBufferAllocate.clear();
                            }
                        }
                    } catch (RuntimeException unused2) {
                    }
                }
                i3 = i2;
            }
            stringBuffer.append(str.charAt(i3));
            i3++;
        }
        return stringBuffer.toString();
    }

    public static boolean deleteDirectory(File file) {
        clearDirectory(file);
        return file.delete();
    }

    private static void deleteDirectoryOnExit(File file) throws IOException {
        if (file.exists()) {
            file.deleteOnExit();
            if (isSymlink(file)) {
                return;
            }
            cleanDirectoryOnExit(file);
        }
    }

    public static boolean deleteFD(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        File file2 = new File(file.getAbsolutePath() + System.currentTimeMillis());
        file.renameTo(file2);
        return deleteFD(file2);
    }

    public static boolean deleteFile(String str) {
        File file = new File(str);
        if (!file.exists()) {
            return false;
        }
        file.delete();
        return true;
    }

    public static boolean deleteQuietly(File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        } catch (Exception unused) {
        }
        try {
            return file.delete();
        } catch (Exception unused2) {
            return false;
        }
    }

    private static void doCopyDirectory(File file, File file2, FileFilter fileFilter, boolean z2, List<String> list) throws Throwable {
        File[] fileArrListFiles = fileFilter == null ? file.listFiles() : file.listFiles(fileFilter);
        if (fileArrListFiles == null) {
            throw new IOException("Failed to list contents of " + file);
        }
        if (file2.exists()) {
            if (!file2.isDirectory()) {
                throw new IOException("Destination '" + file2 + "' exists but is not a directory");
            }
        } else if (!file2.mkdirs() && !file2.isDirectory()) {
            throw new IOException("Destination '" + file2 + "' directory cannot be created");
        }
        if (!file2.canWrite()) {
            throw new IOException("Destination '" + file2 + "' cannot be written to");
        }
        for (File file3 : fileArrListFiles) {
            File file4 = new File(file2, file3.getName());
            if (list == null || !list.contains(file3.getCanonicalPath())) {
                if (file3.isDirectory()) {
                    doCopyDirectory(file3, file4, fileFilter, z2, list);
                } else {
                    doCopyFile(file3, file4, z2);
                }
            }
        }
        if (z2) {
            file2.setLastModified(file.lastModified());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.io.FileOutputStream, java.io.OutputStream] */
    private static void doCopyFile(File file, File file2, boolean z2) throws Throwable {
        FileInputStream fileInputStream;
        ?? fileOutputStream;
        FileChannel channel;
        if (file2.exists() && file2.isDirectory()) {
            throw new IOException("Destination '" + file2 + "' exists but is a directory");
        }
        FileChannel channel2 = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (Throwable th) {
            th = th;
            fileInputStream = null;
            fileOutputStream = 0;
        }
        try {
            fileOutputStream = new FileOutputStream(file2);
            try {
                channel = fileInputStream.getChannel();
            } catch (Throwable th2) {
                th = th2;
                channel = null;
            }
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = 0;
            channel = fileOutputStream;
            IOUtils.closeQuietly(channel2);
            IOUtils.closeQuietly((OutputStream) fileOutputStream);
            IOUtils.closeQuietly(channel);
            IOUtils.closeQuietly((InputStream) fileInputStream);
            throw th;
        }
        try {
            channel2 = fileOutputStream.getChannel();
            long size = channel.size();
            long jTransferFrom = 0;
            while (jTransferFrom < size) {
                long j2 = size - jTransferFrom;
                jTransferFrom += channel2.transferFrom(channel, jTransferFrom, j2 > FILE_COPY_BUFFER_SIZE ? 31457280L : j2);
            }
            IOUtils.closeQuietly(channel2);
            IOUtils.closeQuietly((OutputStream) fileOutputStream);
            IOUtils.closeQuietly(channel);
            IOUtils.closeQuietly((InputStream) fileInputStream);
            if (file.length() == file2.length()) {
                if (z2) {
                    file2.setLastModified(file.lastModified());
                }
            } else {
                throw new IOException("Failed to copy full contents from '" + file + "' to '" + file2 + "'");
            }
        } catch (Throwable th4) {
            th = th4;
            IOUtils.closeQuietly(channel2);
            IOUtils.closeQuietly((OutputStream) fileOutputStream);
            IOUtils.closeQuietly(channel);
            IOUtils.closeQuietly((InputStream) fileInputStream);
            throw th;
        }
    }

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
            return;
        }
        boolean zExists = file.exists();
        if (file.delete()) {
            return;
        }
        if (zExists) {
            throw new IOException("Unable to delete file: " + file);
        }
        throw new FileNotFoundException("File does not exist: " + file);
    }

    public static void forceDeleteOnExit(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectoryOnExit(file);
        } else {
            file.deleteOnExit();
        }
    }

    public static void forceMkdir(File file) throws IOException {
        if (!file.exists()) {
            if (file.mkdirs() || file.isDirectory()) {
                return;
            }
            throw new IOException("Unable to create directory " + file);
        }
        if (file.isDirectory()) {
            return;
        }
        throw new IOException("File " + file + " exists and is not a directory. Unable to create directory.");
    }

    public static File getApplicationSdcardPath(Context context) {
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        return externalFilesDir == null ? context.getFilesDir() : externalFilesDir;
    }

    public static BitmapFactory.Options getDropboxIMGSize(Uri uri) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
        return options;
    }

    public static File getFile(File file, String... strArr) {
        if (file == null) {
            throw new NullPointerException("directorydirectory must not be null");
        }
        if (strArr == null) {
            throw new NullPointerException("names must not be null");
        }
        int length = strArr.length;
        int i2 = 0;
        while (i2 < length) {
            File file2 = new File(file, strArr[i2]);
            i2++;
            file = file2;
        }
        return file;
    }

    public static InputStream getFileInputStream(Context context, String str) {
        try {
            return StringUtils.isUriPath(str) ? context.getContentResolver().openInputStream(Uri.parse(str)) : new FileInputStream(new File(str));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static long getFileLength(Context context, String str) {
        return StringUtils.isUriPath(str) ? getFileLengthByUri(context, Uri.parse(str)) : new File(str).length();
    }

    public static long getFileLengthByUri(Context context, Uri uri) {
        if (context == null || uri == null) {
            return 0L;
        }
        Cursor cursorQuery = context.getContentResolver().query(uri, null, null, null, null);
        cursorQuery.moveToFirst();
        return cursorQuery.getLong(cursorQuery.getColumnIndex("_size"));
    }

    public static String getFileName(Context context, String str) {
        return StringUtils.isUriPath(str) ? getFileNameByUri(context, Uri.parse(str)) : new File(str).getName();
    }

    public static String getFileNameByUri(Context context, Uri uri) {
        if (context == null || uri == null) {
            return "";
        }
        Cursor cursorQuery = context.getContentResolver().query(uri, null, null, null, null);
        cursorQuery.moveToFirst();
        return cursorQuery.getString(cursorQuery.getColumnIndex("_display_name"));
    }

    public static String getMd5OfFile(String str) {
        String str2 = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            byte[] bArr = new byte[1024];
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            int i2 = 0;
            while (i2 != -1) {
                i2 = fileInputStream.read(bArr);
                if (i2 > 0) {
                    messageDigest.update(bArr, 0, i2);
                }
            }
            fileInputStream.close();
            for (byte b3 : messageDigest.digest()) {
                str2 = str2 + Integer.toString((b3 & 255) + 256, 16).substring(1);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return str2.toUpperCase();
    }

    public static String getMimeType(String str) {
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(str);
        if (fileExtensionFromUrl != null) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl);
        }
        return null;
    }

    public static File getTempDirectory() {
        return new File(getTempDirectoryPath());
    }

    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }

    public static File getUserDirectory() {
        return new File(getUserDirectoryPath());
    }

    public static String getUserDirectoryPath() {
        return System.getProperty("user.home");
    }

    public static Bitmap getVideoSize(String str) throws IOException {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                mediaMetadataRetriever.setDataSource(str);
                return mediaMetadataRetriever.getFrameAtTime();
            } catch (Exception e2) {
                e2.printStackTrace();
                mediaMetadataRetriever.release();
                return null;
            }
        } finally {
            mediaMetadataRetriever.release();
        }
    }

    public static boolean isFileExists(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return new File(str).exists();
    }

    public static boolean isFileNewer(File file, File file2) {
        if (file2 == null) {
            throw new IllegalArgumentException("No specified reference file");
        }
        if (file2.exists()) {
            return isFileNewer(file, file2.lastModified());
        }
        throw new IllegalArgumentException("The reference file '" + file2 + "' doesn't exist");
    }

    public static boolean isFileOlder(File file, File file2) {
        if (file2 == null) {
            throw new IllegalArgumentException("No specified reference file");
        }
        if (file2.exists()) {
            return isFileOlder(file, file2.lastModified());
        }
        throw new IllegalArgumentException("The reference file '" + file2 + "' doesn't exist");
    }

    public static boolean isSymlink(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }
        if (FilenameUtils.isSystemWindows()) {
            return false;
        }
        if (file.getParent() != null) {
            file = new File(file.getParentFile().getCanonicalFile(), file.getName());
        }
        return !file.getCanonicalFile().equals(file.getAbsoluteFile());
    }

    public static boolean mkdirs(File file) {
        try {
            forceMkdir(file);
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    public static void moveDirectory(File file, File file2) throws Throwable {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!file.exists()) {
            throw new FileNotFoundException("Source '" + file + "' does not exist");
        }
        if (!file.isDirectory()) {
            throw new IOException("Source '" + file + "' is not a directory");
        }
        if (file2.exists()) {
            throw new FileExistsException("Destination '" + file2 + "' already exists");
        }
        if (file.renameTo(file2)) {
            return;
        }
        if (file2.getCanonicalPath().startsWith(file.getCanonicalPath())) {
            throw new IOException("Cannot move directory: " + file + " to a subdirectory of itself: " + file2);
        }
        copyDirectory(file, file2);
        deleteDirectory(file);
        if (file.exists()) {
            throw new IOException("Failed to delete original directory '" + file + "' after copy to '" + file2 + "'");
        }
    }

    public static void moveDirectoryToDirectory(File file, File file2, boolean z2) throws Throwable {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination directory must not be null");
        }
        if (!file2.exists() && z2) {
            file2.mkdirs();
        }
        if (!file2.exists()) {
            throw new FileNotFoundException("Destination directory '" + file2 + "' does not exist [createDestDir=" + z2 + StrPool.BRACKET_END);
        }
        if (file2.isDirectory()) {
            moveDirectory(file, new File(file2, file.getName()));
            return;
        }
        throw new IOException("Destination '" + file2 + "' is not a directory");
    }

    public static void moveFile(File file, File file2) throws Throwable {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!file.exists()) {
            throw new FileNotFoundException("Source '" + file + "' does not exist");
        }
        if (file.isDirectory()) {
            throw new IOException("Source '" + file + "' is a directory");
        }
        if (file2.exists()) {
            throw new FileExistsException("Destination '" + file2 + "' already exists");
        }
        if (file2.isDirectory()) {
            throw new IOException("Destination '" + file2 + "' is a directory");
        }
        if (file.renameTo(file2)) {
            return;
        }
        copyFile(file, file2);
        if (file.delete()) {
            return;
        }
        deleteQuietly(file2);
        throw new IOException("Failed to delete original file '" + file + "' after copy to '" + file2 + "'");
    }

    public static void moveFileToDirectory(File file, File file2, boolean z2) throws Throwable {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination directory must not be null");
        }
        if (!file2.exists() && z2) {
            file2.mkdirs();
        }
        if (!file2.exists()) {
            throw new FileNotFoundException("Destination directory '" + file2 + "' does not exist [createDestDir=" + z2 + StrPool.BRACKET_END);
        }
        if (file2.isDirectory()) {
            moveFile(file, new File(file2, file.getName()));
            return;
        }
        throw new IOException("Destination '" + file2 + "' is not a directory");
    }

    public static void moveToDirectory(File file, File file2, boolean z2) throws Throwable {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (file.exists()) {
            if (file.isDirectory()) {
                moveDirectoryToDirectory(file, file2, z2);
                return;
            } else {
                moveFileToDirectory(file, file2, z2);
                return;
            }
        }
        throw new FileNotFoundException("Source '" + file + "' does not exist");
    }

    public static FileInputStream openInputStream(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        if (file.isDirectory()) {
            throw new IOException("File '" + file + "' exists but is a directory");
        }
        if (file.canRead()) {
            return new FileInputStream(file);
        }
        throw new IOException("File '" + file + "' cannot be read");
    }

    public static FileOutputStream openOutputStream(File file) throws IOException {
        return openOutputStream(file, false);
    }

    public static String percentEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8").replace(Marker.ANY_NON_NULL_MARKER, "%20").replace("*", "%2A").replace("%7E", Constants.WAVE_SEPARATOR);
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return str;
        }
    }

    public static byte[] readFileToByteArray(File file) throws Throwable {
        FileInputStream fileInputStreamOpenInputStream;
        try {
            fileInputStreamOpenInputStream = openInputStream(file);
        } catch (Throwable th) {
            th = th;
            fileInputStreamOpenInputStream = null;
        }
        try {
            byte[] byteArray = IOUtils.toByteArray(fileInputStreamOpenInputStream, file.length());
            IOUtils.closeQuietly((InputStream) fileInputStreamOpenInputStream);
            return byteArray;
        } catch (Throwable th2) {
            th = th2;
            IOUtils.closeQuietly((InputStream) fileInputStreamOpenInputStream);
            throw th;
        }
    }

    public static String readFileToString(File file, Charset charset) throws Throwable {
        FileInputStream fileInputStreamOpenInputStream;
        try {
            fileInputStreamOpenInputStream = openInputStream(file);
        } catch (Throwable th) {
            th = th;
            fileInputStreamOpenInputStream = null;
        }
        try {
            String string = IOUtils.toString(fileInputStreamOpenInputStream, Charsets.toCharset(charset));
            IOUtils.closeQuietly((InputStream) fileInputStreamOpenInputStream);
            return string;
        } catch (Throwable th2) {
            th = th2;
            IOUtils.closeQuietly((InputStream) fileInputStreamOpenInputStream);
            throw th;
        }
    }

    public static List<String> readLines(File file, Charset charset) throws Throwable {
        FileInputStream fileInputStreamOpenInputStream;
        try {
            fileInputStreamOpenInputStream = openInputStream(file);
        } catch (Throwable th) {
            th = th;
            fileInputStreamOpenInputStream = null;
        }
        try {
            List<String> lines = IOUtils.readLines(fileInputStreamOpenInputStream, Charsets.toCharset(charset));
            IOUtils.closeQuietly((InputStream) fileInputStreamOpenInputStream);
            return lines;
        } catch (Throwable th2) {
            th = th2;
            IOUtils.closeQuietly((InputStream) fileInputStreamOpenInputStream);
            throw th;
        }
    }

    public static long sizeOf(File file) {
        if (file.exists()) {
            return file.isDirectory() ? sizeOfDirectory(file) : file.length();
        }
        throw new IllegalArgumentException(file + " does not exist");
    }

    public static BigInteger sizeOfAsBigInteger(File file) {
        if (file.exists()) {
            return file.isDirectory() ? sizeOfDirectoryAsBigInteger(file) : BigInteger.valueOf(file.length());
        }
        throw new IllegalArgumentException(file + " does not exist");
    }

    public static long sizeOfDirectory(File file) {
        checkDirectory(file);
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return 0L;
        }
        long jSizeOf = 0;
        for (File file2 : fileArrListFiles) {
            try {
                if (!isSymlink(file2)) {
                    jSizeOf += sizeOf(file2);
                    if (jSizeOf < 0) {
                        break;
                    }
                } else {
                    continue;
                }
            } catch (IOException unused) {
            }
        }
        return jSizeOf;
    }

    public static BigInteger sizeOfDirectoryAsBigInteger(File file) {
        checkDirectory(file);
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return BigInteger.ZERO;
        }
        BigInteger bigIntegerAdd = BigInteger.ZERO;
        for (File file2 : fileArrListFiles) {
            try {
                if (!isSymlink(file2)) {
                    bigIntegerAdd = bigIntegerAdd.add(BigInteger.valueOf(sizeOf(file2)));
                }
            } catch (IOException unused) {
            }
        }
        return bigIntegerAdd;
    }

    public static File toFile(URL url) {
        if (url == null || !"file".equalsIgnoreCase(url.getProtocol())) {
            return null;
        }
        return new File(decodeUrl(url.getFile().replace('/', File.separatorChar)));
    }

    public static File[] toFiles(URL[] urlArr) {
        if (urlArr == null || urlArr.length == 0) {
            return EMPTY_FILE_ARRAY;
        }
        File[] fileArr = new File[urlArr.length];
        for (int i2 = 0; i2 < urlArr.length; i2++) {
            URL url = urlArr[i2];
            if (url != null) {
                if (!url.getProtocol().equals("file")) {
                    throw new IllegalArgumentException("URL could not be converted to a File: " + url);
                }
                fileArr[i2] = toFile(url);
            }
        }
        return fileArr;
    }

    private static String[] toSuffixes(String[] strArr) {
        String[] strArr2 = new String[strArr.length];
        for (int i2 = 0; i2 < strArr.length; i2++) {
            strArr2[i2] = StrPool.DOT + strArr[i2];
        }
        return strArr2;
    }

    public static URL[] toURLs(File[] fileArr) throws IOException {
        int length = fileArr.length;
        URL[] urlArr = new URL[length];
        for (int i2 = 0; i2 < length; i2++) {
            urlArr[i2] = fileArr[i2].toURI().toURL();
        }
        return urlArr;
    }

    public static void touch(File file) throws IOException {
        if (!file.exists()) {
            IOUtils.closeQuietly((OutputStream) openOutputStream(file));
        }
        if (file.setLastModified(System.currentTimeMillis())) {
            return;
        }
        throw new IOException("Unable to set the last modification time for " + file);
    }

    public static boolean waitFor(File file, int i2) throws InterruptedException {
        int i3 = 0;
        int i4 = 0;
        while (!file.exists()) {
            int i5 = i3 + 1;
            if (i3 >= 10) {
                int i6 = i4 + 1;
                if (i4 > i2) {
                    return false;
                }
                i4 = i6;
                i3 = 0;
            } else {
                i3 = i5;
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException unused) {
            } catch (Exception unused2) {
                return true;
            }
        }
        return true;
    }

    public static void write(File file, CharSequence charSequence) throws Throwable {
        write(file, charSequence, Charset.defaultCharset(), false);
    }

    public static void writeByteArrayToFile(File file, byte[] bArr) throws Throwable {
        writeByteArrayToFile(file, bArr, false);
    }

    public static void writeLines(File file, String str, Collection<?> collection) throws Throwable {
        writeLines(file, str, collection, null, false);
    }

    public static void writeStringToFile(File file, String str, Charset charset) throws Throwable {
        writeStringToFile(file, str, charset, false);
    }

    public static void copyDirectory(File file, File file2, boolean z2) throws Throwable {
        copyDirectory(file, file2, null, z2);
    }

    public static void copyFile(File file, File file2, boolean z2) throws Throwable {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!file.exists()) {
            throw new FileNotFoundException("Source '" + file + "' does not exist");
        }
        if (file.isDirectory()) {
            throw new IOException("Source '" + file + "' exists but is a directory");
        }
        if (file.getCanonicalPath().equals(file2.getCanonicalPath())) {
            throw new IOException("Source '" + file + "' and destination '" + file2 + "' are the same");
        }
        File parentFile = file2.getParentFile();
        if (parentFile != null && !parentFile.mkdirs() && !parentFile.isDirectory()) {
            throw new IOException("Destination '" + parentFile + "' directory cannot be created");
        }
        if (!file2.exists() || file2.canWrite()) {
            doCopyFile(file, file2, z2);
            return;
        }
        throw new IOException("Destination '" + file2 + "' exists but is read-only");
    }

    public static void copyFileToDirectory(File file, File file2, boolean z2) throws Throwable {
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!file2.exists() || file2.isDirectory()) {
            copyFile(file, new File(file2, file.getName()), z2);
            return;
        }
        throw new IllegalArgumentException("Destination '" + file2 + "' is not a directory");
    }

    public static FileOutputStream openOutputStream(File file, boolean z2) throws IOException {
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.mkdirs() && !parentFile.isDirectory()) {
                throw new IOException("Directory '" + parentFile + "' could not be created");
            }
        } else {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        }
        return new FileOutputStream(file, z2);
    }

    public static void write(File file, CharSequence charSequence, boolean z2) throws Throwable {
        write(file, charSequence, Charset.defaultCharset(), z2);
    }

    public static void writeByteArrayToFile(File file, byte[] bArr, boolean z2) throws Throwable {
        FileOutputStream fileOutputStreamOpenOutputStream;
        try {
            fileOutputStreamOpenOutputStream = openOutputStream(file, z2);
            try {
                fileOutputStreamOpenOutputStream.write(bArr);
                fileOutputStreamOpenOutputStream.close();
                IOUtils.closeQuietly((OutputStream) fileOutputStreamOpenOutputStream);
            } catch (Throwable th) {
                th = th;
                IOUtils.closeQuietly((OutputStream) fileOutputStreamOpenOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStreamOpenOutputStream = null;
        }
    }

    public static void writeLines(File file, String str, Collection<?> collection, boolean z2) throws Throwable {
        writeLines(file, str, collection, null, z2);
    }

    public static void writeStringToFile(File file, String str, String str2) throws Throwable {
        writeStringToFile(file, str, str2, false);
    }

    public static void copyDirectory(File file, File file2, FileFilter fileFilter) throws Throwable {
        copyDirectory(file, file2, fileFilter, true);
    }

    public static void copyURLToFile(URL url, File file, int i2, int i3) throws IOException {
        URLConnection uRLConnectionOpenConnection = url.openConnection();
        uRLConnectionOpenConnection.setConnectTimeout(i2);
        uRLConnectionOpenConnection.setReadTimeout(i3);
        copyInputStreamToFile(uRLConnectionOpenConnection.getInputStream(), file);
    }

    public static void write(File file, CharSequence charSequence, Charset charset) throws Throwable {
        write(file, charSequence, charset, false);
    }

    public static void writeLines(File file, Collection<?> collection) throws Throwable {
        writeLines(file, null, collection, null, false);
    }

    public static void writeStringToFile(File file, String str, Charset charset, boolean z2) throws Throwable {
        FileOutputStream fileOutputStreamOpenOutputStream;
        try {
            fileOutputStreamOpenOutputStream = openOutputStream(file, z2);
            try {
                IOUtils.write(str, (OutputStream) fileOutputStreamOpenOutputStream, charset);
                fileOutputStreamOpenOutputStream.close();
                IOUtils.closeQuietly((OutputStream) fileOutputStreamOpenOutputStream);
            } catch (Throwable th) {
                th = th;
                IOUtils.closeQuietly((OutputStream) fileOutputStreamOpenOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStreamOpenOutputStream = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void copyDirectory(java.io.File r6, java.io.File r7, java.io.FileFilter r8, boolean r9) throws java.lang.Throwable {
        /*
            if (r6 == 0) goto Lc0
            if (r7 == 0) goto Lb8
            boolean r0 = r6.exists()
            java.lang.String r1 = "Source '"
            if (r0 == 0) goto L9e
            boolean r0 = r6.isDirectory()
            if (r0 == 0) goto L84
            java.lang.String r0 = r6.getCanonicalPath()
            java.lang.String r2 = r7.getCanonicalPath()
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L62
            java.lang.String r0 = r7.getCanonicalPath()
            java.lang.String r1 = r6.getCanonicalPath()
            boolean r0 = r0.startsWith(r1)
            if (r0 == 0) goto L5d
            if (r8 != 0) goto L35
            java.io.File[] r0 = r6.listFiles()
            goto L39
        L35:
            java.io.File[] r0 = r6.listFiles(r8)
        L39:
            if (r0 == 0) goto L5d
            int r1 = r0.length
            if (r1 <= 0) goto L5d
            java.util.ArrayList r1 = new java.util.ArrayList
            int r2 = r0.length
            r1.<init>(r2)
            int r2 = r0.length
            r3 = 0
        L46:
            if (r3 >= r2) goto L5e
            r4 = r0[r3]
            java.io.File r5 = new java.io.File
            java.lang.String r4 = r4.getName()
            r5.<init>(r7, r4)
            java.lang.String r4 = r5.getCanonicalPath()
            r1.add(r4)
            int r3 = r3 + 1
            goto L46
        L5d:
            r1 = 0
        L5e:
            doCopyDirectory(r6, r7, r8, r9, r1)
            return
        L62:
            java.io.IOException r8 = new java.io.IOException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r1)
            r9.append(r6)
            java.lang.String r6 = "' and destination '"
            r9.append(r6)
            r9.append(r7)
            java.lang.String r6 = "' are the same"
            r9.append(r6)
            java.lang.String r6 = r9.toString()
            r8.<init>(r6)
            throw r8
        L84:
            java.io.IOException r7 = new java.io.IOException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r1)
            r8.append(r6)
            java.lang.String r6 = "' exists but is not a directory"
            r8.append(r6)
            java.lang.String r6 = r8.toString()
            r7.<init>(r6)
            throw r7
        L9e:
            java.io.FileNotFoundException r7 = new java.io.FileNotFoundException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r1)
            r8.append(r6)
            java.lang.String r6 = "' does not exist"
            r8.append(r6)
            java.lang.String r6 = r8.toString()
            r7.<init>(r6)
            throw r7
        Lb8:
            java.lang.NullPointerException r6 = new java.lang.NullPointerException
            java.lang.String r7 = "Destination must not be null"
            r6.<init>(r7)
            throw r6
        Lc0:
            java.lang.NullPointerException r6 = new java.lang.NullPointerException
            java.lang.String r7 = "Source must not be null"
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.vod.common.utils.FileUtils.copyDirectory(java.io.File, java.io.File, java.io.FileFilter, boolean):void");
    }

    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return false;
        }
        file.delete();
        return true;
    }

    public static String readFileToString(File file, String str) throws IOException {
        return readFileToString(file, Charsets.toCharset(str));
    }

    public static List<String> readLines(File file, String str) throws IOException {
        return readLines(file, Charsets.toCharset(str));
    }

    public static void write(File file, CharSequence charSequence, String str) throws Throwable {
        write(file, charSequence, str, false);
    }

    public static void writeLines(File file, Collection<?> collection, boolean z2) throws Throwable {
        writeLines(file, null, collection, null, z2);
    }

    public static File getFile(String... strArr) {
        if (strArr != null) {
            File file = null;
            for (String str : strArr) {
                if (file == null) {
                    file = new File(str);
                } else {
                    file = new File(file, str);
                }
            }
            return file;
        }
        throw new NullPointerException("names must not be null");
    }

    public static boolean isFileNewer(File file, Date date) {
        if (date != null) {
            return isFileNewer(file, date.getTime());
        }
        throw new IllegalArgumentException("No specified date");
    }

    public static boolean isFileOlder(File file, Date date) {
        if (date != null) {
            return isFileOlder(file, date.getTime());
        }
        throw new IllegalArgumentException("No specified date");
    }

    public static String readFileToString(File file) throws IOException {
        return readFileToString(file, Charset.defaultCharset());
    }

    public static List<String> readLines(File file) throws IOException {
        return readLines(file, Charset.defaultCharset());
    }

    public static void write(File file, CharSequence charSequence, Charset charset, boolean z2) throws Throwable {
        writeStringToFile(file, charSequence == null ? null : charSequence.toString(), charset, z2);
    }

    public static void writeLines(File file, String str, Collection<?> collection, String str2) throws Throwable {
        writeLines(file, str, collection, str2, false);
    }

    public static boolean deleteFD(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            return deleteDirectory(file);
        }
        return file.delete();
    }

    public static void writeLines(File file, String str, Collection<?> collection, String str2, boolean z2) throws Throwable {
        FileOutputStream fileOutputStreamOpenOutputStream;
        try {
            fileOutputStreamOpenOutputStream = openOutputStream(file, z2);
        } catch (Throwable th) {
            th = th;
            fileOutputStreamOpenOutputStream = null;
        }
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStreamOpenOutputStream);
            IOUtils.writeLines(collection, str2, bufferedOutputStream, str);
            bufferedOutputStream.flush();
            fileOutputStreamOpenOutputStream.close();
            IOUtils.closeQuietly((OutputStream) fileOutputStreamOpenOutputStream);
        } catch (Throwable th2) {
            th = th2;
            IOUtils.closeQuietly((OutputStream) fileOutputStreamOpenOutputStream);
            throw th;
        }
    }

    public static boolean isFileNewer(File file, long j2) {
        if (file != null) {
            return file.exists() && file.lastModified() > j2;
        }
        throw new IllegalArgumentException("No specified file");
    }

    public static boolean isFileOlder(File file, long j2) {
        if (file != null) {
            return file.exists() && file.lastModified() < j2;
        }
        throw new IllegalArgumentException("No specified file");
    }

    public static void write(File file, CharSequence charSequence, String str, boolean z2) throws Throwable {
        write(file, charSequence, Charsets.toCharset(str), z2);
    }

    public static void writeStringToFile(File file, String str, String str2, boolean z2) throws Throwable {
        writeStringToFile(file, str, Charsets.toCharset(str2), z2);
    }

    public static void writeStringToFile(File file, String str) throws Throwable {
        writeStringToFile(file, str, Charset.defaultCharset(), false);
    }

    public static void writeStringToFile(File file, String str, boolean z2) throws Throwable {
        writeStringToFile(file, str, Charset.defaultCharset(), z2);
    }

    public static void writeLines(File file, Collection<?> collection, String str) throws Throwable {
        writeLines(file, null, collection, str, false);
    }

    public static void writeLines(File file, Collection<?> collection, String str, boolean z2) throws Throwable {
        writeLines(file, null, collection, str, z2);
    }

    public static String byteCountToDisplaySize(long j2) {
        return byteCountToDisplaySize(BigInteger.valueOf(j2));
    }

    public static long copyFile(File file, OutputStream outputStream) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            return IOUtils.copyLarge(fileInputStream, outputStream);
        } finally {
            fileInputStream.close();
        }
    }
}
