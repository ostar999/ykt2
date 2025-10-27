package net.lingala.zip4j.util;

import cn.hutool.core.text.StrPool;
import com.umeng.analytics.pro.am;
import com.yikaobang.yixue.R2;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipModel;

/* loaded from: classes9.dex */
public class Zip4jUtil {
    public static boolean checkArrayListTypes(ArrayList arrayList, int i2) throws ZipException {
        if (arrayList == null) {
            throw new ZipException("input arraylist is null, cannot check types");
        }
        if (arrayList.size() <= 0) {
            return true;
        }
        boolean z2 = false;
        if (i2 == 1) {
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                if (!(arrayList.get(i3) instanceof File)) {
                    z2 = true;
                    break;
                }
            }
        } else if (i2 == 2) {
            for (int i4 = 0; i4 < arrayList.size(); i4++) {
                if (!(arrayList.get(i4) instanceof String)) {
                    z2 = true;
                    break;
                }
            }
        }
        return !z2;
    }

    public static boolean checkFileExists(String str) throws ZipException {
        if (isStringNotNullAndNotEmpty(str)) {
            return checkFileExists(new File(str));
        }
        throw new ZipException("path is null");
    }

    public static boolean checkFileReadAccess(String str) throws ZipException {
        if (!isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("path is null");
        }
        if (checkFileExists(str)) {
            try {
                return new File(str).canRead();
            } catch (Exception unused) {
                throw new ZipException("cannot read zip file");
            }
        }
        throw new ZipException("file does not exist: " + str);
    }

    public static boolean checkFileWriteAccess(String str) throws ZipException {
        if (!isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("path is null");
        }
        if (checkFileExists(str)) {
            try {
                return new File(str).canWrite();
            } catch (Exception unused) {
                throw new ZipException("cannot read zip file");
            }
        }
        throw new ZipException("file does not exist: " + str);
    }

    public static boolean checkOutputFolder(String str) throws ZipException {
        if (!isStringNotNullAndNotEmpty(str)) {
            throw new ZipException(new NullPointerException("output path is null"));
        }
        File file = new File(str);
        if (file.exists()) {
            if (!file.isDirectory()) {
                throw new ZipException("output folder is not valid");
            }
            if (file.canWrite()) {
                return true;
            }
            throw new ZipException("no write access to output folder");
        }
        try {
            file.mkdirs();
            if (!file.isDirectory()) {
                throw new ZipException("output folder is not valid");
            }
            if (file.canWrite()) {
                return true;
            }
            throw new ZipException("no write access to destination folder");
        } catch (Exception unused) {
            throw new ZipException("Cannot create destination folder");
        }
    }

    public static byte[] convertCharset(String str) throws ZipException {
        try {
            String strDetectCharSet = detectCharSet(str);
            return strDetectCharSet.equals(InternalZipConstants.CHARSET_CP850) ? str.getBytes(InternalZipConstants.CHARSET_CP850) : strDetectCharSet.equals("UTF8") ? str.getBytes("UTF8") : str.getBytes();
        } catch (UnsupportedEncodingException unused) {
            return str.getBytes();
        } catch (Exception e2) {
            throw new ZipException(e2);
        }
    }

    public static String decodeFileName(byte[] bArr, boolean z2) {
        if (!z2) {
            return getCp850EncodedString(bArr);
        }
        try {
            return new String(bArr, "UTF8");
        } catch (UnsupportedEncodingException unused) {
            return new String(bArr);
        }
    }

    public static String detectCharSet(String str) throws ZipException {
        if (str == null) {
            throw new ZipException("input string is null, cannot detect charset");
        }
        try {
            return str.equals(new String(str.getBytes(InternalZipConstants.CHARSET_CP850), InternalZipConstants.CHARSET_CP850)) ? InternalZipConstants.CHARSET_CP850 : str.equals(new String(str.getBytes("UTF8"), "UTF8")) ? "UTF8" : InternalZipConstants.CHARSET_DEFAULT;
        } catch (UnsupportedEncodingException unused) {
            return InternalZipConstants.CHARSET_DEFAULT;
        } catch (Exception unused2) {
            return InternalZipConstants.CHARSET_DEFAULT;
        }
    }

    public static long dosToJavaTme(int i2) {
        int i3 = (i2 & 31) * 2;
        int i4 = (i2 >> 5) & 63;
        int i5 = (i2 >> 11) & 31;
        int i6 = (i2 >> 16) & 31;
        int i7 = ((i2 >> 21) & 15) - 1;
        int i8 = ((i2 >> 25) & 127) + R2.attr.icon_pressed_bottom;
        Calendar calendar = Calendar.getInstance();
        calendar.set(i8, i7, i6, i5, i4, i3);
        calendar.set(14, 0);
        return calendar.getTime().getTime();
    }

    public static String getAbsoluteFilePath(String str) throws ZipException {
        if (isStringNotNullAndNotEmpty(str)) {
            return new File(str).getAbsolutePath();
        }
        throw new ZipException("filePath is null or empty, cannot get absolute file path");
    }

    public static long[] getAllHeaderSignatures() {
        return new long[]{InternalZipConstants.LOCSIG, 134695760, InternalZipConstants.CENSIG, InternalZipConstants.ENDSIG, InternalZipConstants.DIGSIG, InternalZipConstants.ARCEXTDATREC, 134695760, InternalZipConstants.ZIP64ENDCENDIRLOC, InternalZipConstants.ZIP64ENDCENDIRREC, 1, 39169};
    }

    public static String getCp850EncodedString(byte[] bArr) {
        try {
            return new String(bArr, InternalZipConstants.CHARSET_CP850);
        } catch (UnsupportedEncodingException unused) {
            return new String(bArr);
        }
    }

    public static int getEncodedStringLength(String str) throws ZipException {
        if (isStringNotNullAndNotEmpty(str)) {
            return getEncodedStringLength(str, detectCharSet(str));
        }
        throw new ZipException("input string is null, cannot calculate encoded String length");
    }

    public static FileHeader getFileHeader(ZipModel zipModel, String str) throws ZipException {
        if (zipModel == null) {
            throw new ZipException("zip model is null, cannot determine file header for fileName: " + str);
        }
        if (!isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("file name is null, cannot determine file header for fileName: " + str);
        }
        FileHeader fileHeaderWithExactMatch = getFileHeaderWithExactMatch(zipModel, str);
        if (fileHeaderWithExactMatch != null) {
            return fileHeaderWithExactMatch;
        }
        String strReplaceAll = str.replaceAll("\\\\", "/");
        FileHeader fileHeaderWithExactMatch2 = getFileHeaderWithExactMatch(zipModel, strReplaceAll);
        return fileHeaderWithExactMatch2 == null ? getFileHeaderWithExactMatch(zipModel, strReplaceAll.replaceAll("/", "\\\\")) : fileHeaderWithExactMatch2;
    }

    public static FileHeader getFileHeaderWithExactMatch(ZipModel zipModel, String str) throws ZipException {
        if (zipModel == null) {
            throw new ZipException("zip model is null, cannot determine file header with exact match for fileName: " + str);
        }
        if (!isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("file name is null, cannot determine file header with exact match for fileName: " + str);
        }
        if (zipModel.getCentralDirectory() == null) {
            throw new ZipException("central directory is null, cannot determine file header with exact match for fileName: " + str);
        }
        if (zipModel.getCentralDirectory().getFileHeaders() == null) {
            throw new ZipException("file Headers are null, cannot determine file header with exact match for fileName: " + str);
        }
        if (zipModel.getCentralDirectory().getFileHeaders().size() <= 0) {
            return null;
        }
        ArrayList fileHeaders = zipModel.getCentralDirectory().getFileHeaders();
        for (int i2 = 0; i2 < fileHeaders.size(); i2++) {
            FileHeader fileHeader = (FileHeader) fileHeaders.get(i2);
            String fileName = fileHeader.getFileName();
            if (isStringNotNullAndNotEmpty(fileName) && str.equalsIgnoreCase(fileName)) {
                return fileHeader;
            }
        }
        return null;
    }

    public static long getFileLengh(String str) throws ZipException {
        if (isStringNotNullAndNotEmpty(str)) {
            return getFileLengh(new File(str));
        }
        throw new ZipException("invalid file name");
    }

    public static String getFileNameFromFilePath(File file) throws ZipException {
        if (file == null) {
            throw new ZipException("input file is null, cannot get file name");
        }
        if (file.isDirectory()) {
            return null;
        }
        return file.getName();
    }

    public static ArrayList getFilesInDirectoryRec(File file, boolean z2) throws ZipException {
        if (file == null) {
            throw new ZipException("input path is null, cannot read files in the directory");
        }
        ArrayList arrayList = new ArrayList();
        List listAsList = Arrays.asList(file.listFiles());
        if (!file.canRead()) {
            return arrayList;
        }
        for (int i2 = 0; i2 < listAsList.size(); i2++) {
            File file2 = (File) listAsList.get(i2);
            if (file2.isHidden() && !z2) {
                return arrayList;
            }
            arrayList.add(file2);
            if (file2.isDirectory()) {
                arrayList.addAll(getFilesInDirectoryRec(file2, z2));
            }
        }
        return arrayList;
    }

    public static int getIndexOfFileHeader(ZipModel zipModel, FileHeader fileHeader) throws ZipException {
        if (zipModel == null || fileHeader == null) {
            throw new ZipException("input parameters is null, cannot determine index of file header");
        }
        if (zipModel.getCentralDirectory() == null) {
            throw new ZipException("central directory is null, ccannot determine index of file header");
        }
        if (zipModel.getCentralDirectory().getFileHeaders() == null) {
            throw new ZipException("file Headers are null, cannot determine index of file header");
        }
        if (zipModel.getCentralDirectory().getFileHeaders().size() <= 0) {
            return -1;
        }
        String fileName = fileHeader.getFileName();
        if (!isStringNotNullAndNotEmpty(fileName)) {
            throw new ZipException("file name in file header is empty or null, cannot determine index of file header");
        }
        ArrayList fileHeaders = zipModel.getCentralDirectory().getFileHeaders();
        for (int i2 = 0; i2 < fileHeaders.size(); i2++) {
            String fileName2 = ((FileHeader) fileHeaders.get(i2)).getFileName();
            if (isStringNotNullAndNotEmpty(fileName2) && fileName.equalsIgnoreCase(fileName2)) {
                return i2;
            }
        }
        return -1;
    }

    public static long getLastModifiedFileTime(File file, TimeZone timeZone) throws ZipException {
        if (file == null) {
            throw new ZipException("input file is null, cannot read last modified file time");
        }
        if (file.exists()) {
            return file.lastModified();
        }
        throw new ZipException("input file does not exist, cannot read last modified file time");
    }

    public static String getRelativeFileName(String str, String str2, String str3) throws ZipException {
        String fileNameFromFilePath;
        if (!isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("input file path/name is empty, cannot calculate relative file name");
        }
        if (isStringNotNullAndNotEmpty(str3)) {
            String path = new File(str3).getPath();
            String str4 = InternalZipConstants.FILE_SEPARATOR;
            if (!path.endsWith(str4)) {
                path = path + str4;
            }
            String strSubstring = str.substring(path.length());
            if (strSubstring.startsWith(System.getProperty("file.separator"))) {
                strSubstring = strSubstring.substring(1);
            }
            File file = new File(str);
            if (file.isDirectory()) {
                fileNameFromFilePath = strSubstring.replaceAll("\\\\", "/") + "/";
            } else {
                fileNameFromFilePath = strSubstring.substring(0, strSubstring.lastIndexOf(file.getName())).replaceAll("\\\\", "/") + file.getName();
            }
        } else {
            File file2 = new File(str);
            if (file2.isDirectory()) {
                fileNameFromFilePath = file2.getName() + "/";
            } else {
                fileNameFromFilePath = getFileNameFromFilePath(new File(str));
            }
        }
        if (isStringNotNullAndNotEmpty(str2)) {
            fileNameFromFilePath = str2 + fileNameFromFilePath;
        }
        if (isStringNotNullAndNotEmpty(fileNameFromFilePath)) {
            return fileNameFromFilePath;
        }
        throw new ZipException("Error determining file name");
    }

    public static ArrayList getSplitZipFiles(ZipModel zipModel) throws ZipException {
        if (zipModel == null) {
            throw new ZipException("cannot get split zip files: zipmodel is null");
        }
        if (zipModel.getEndCentralDirRecord() == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        String zipFile = zipModel.getZipFile();
        String name = new File(zipFile).getName();
        if (!isStringNotNullAndNotEmpty(zipFile)) {
            throw new ZipException("cannot get split zip files: zipfile is null");
        }
        if (!zipModel.isSplitArchive()) {
            arrayList.add(zipFile);
            return arrayList;
        }
        int noOfThisDisk = zipModel.getEndCentralDirRecord().getNoOfThisDisk();
        if (noOfThisDisk == 0) {
            arrayList.add(zipFile);
            return arrayList;
        }
        int i2 = 0;
        while (i2 <= noOfThisDisk) {
            if (i2 == noOfThisDisk) {
                arrayList.add(zipModel.getZipFile());
            } else {
                String str = i2 > 9 ? ".z" : ".z0";
                arrayList.add((name.indexOf(StrPool.DOT) >= 0 ? zipFile.substring(0, zipFile.lastIndexOf(StrPool.DOT)) : zipFile) + str + (i2 + 1));
            }
            i2++;
        }
        return arrayList;
    }

    public static String getZipFileNameWithoutExt(String str) throws ZipException {
        if (!isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("zip file name is empty or null, cannot determine zip file name");
        }
        if (str.indexOf(System.getProperty("file.separator")) >= 0) {
            str = str.substring(str.lastIndexOf(System.getProperty("file.separator")));
        }
        return str.indexOf(StrPool.DOT) > 0 ? str.substring(0, str.lastIndexOf(StrPool.DOT)) : str;
    }

    public static boolean isStringNotNullAndNotEmpty(String str) {
        return str != null && str.trim().length() > 0;
    }

    public static boolean isSupportedCharset(String str) throws ZipException {
        if (!isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("charset is null or empty, cannot check if it is supported");
        }
        try {
            new String(am.av.getBytes(), str);
            return true;
        } catch (UnsupportedEncodingException unused) {
            return false;
        } catch (Exception e2) {
            throw new ZipException(e2);
        }
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
    }

    public static long javaToDosTime(long j2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j2);
        if (calendar.get(1) < 1980) {
            return 2162688L;
        }
        return (calendar.get(13) >> 1) | ((r5 - R2.attr.icon_pressed_bottom) << 25) | ((calendar.get(2) + 1) << 21) | (calendar.get(5) << 16) | (calendar.get(11) << 11) | (calendar.get(12) << 5);
    }

    public static void setFileArchive(File file) throws ZipException {
    }

    public static void setFileHidden(File file) throws ZipException {
    }

    public static void setFileReadOnly(File file) throws ZipException {
        if (file == null) {
            throw new ZipException("input file is null. cannot set read only file attribute");
        }
        if (file.exists()) {
            file.setReadOnly();
        }
    }

    public static void setFileSystemMode(File file) throws ZipException {
    }

    public static long getFileLengh(File file) throws ZipException {
        if (file != null) {
            if (file.isDirectory()) {
                return -1L;
            }
            return file.length();
        }
        throw new ZipException("input file is null, cannot calculate file length");
    }

    public static boolean checkFileExists(File file) throws ZipException {
        if (file != null) {
            return file.exists();
        }
        throw new ZipException("cannot check if file exists: input file is null");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.nio.ByteBuffer] */
    /* JADX WARN: Type inference failed for: r3v6, types: [java.nio.Buffer] */
    public static int getEncodedStringLength(String str, String str2) throws ZipException {
        if (isStringNotNullAndNotEmpty(str)) {
            if (isStringNotNullAndNotEmpty(str2)) {
                try {
                    if (str2.equals(InternalZipConstants.CHARSET_CP850)) {
                        str = ByteBuffer.wrap(str.getBytes(InternalZipConstants.CHARSET_CP850));
                    } else if (str2.equals("UTF8")) {
                        str = ByteBuffer.wrap(str.getBytes("UTF8"));
                    } else {
                        str = ByteBuffer.wrap(str.getBytes(str2));
                    }
                } catch (UnsupportedEncodingException unused) {
                    str = ByteBuffer.wrap(str.getBytes());
                } catch (Exception e2) {
                    throw new ZipException(e2);
                }
                return str.limit();
            }
            throw new ZipException("encoding is not defined, cannot calculate string length");
        }
        throw new ZipException("input string is null, cannot calculate encoded String length");
    }
}
