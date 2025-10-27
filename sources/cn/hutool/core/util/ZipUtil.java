package cn.hutool.core.util;

import cn.hutool.core.collection.EnumerationIter;
import cn.hutool.core.compress.Deflate;
import cn.hutool.core.compress.Gzip;
import cn.hutool.core.compress.ZipCopyVisitor;
import cn.hutool.core.compress.ZipReader;
import cn.hutool.core.compress.ZipWriter;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LimitedInputStream;
import cn.hutool.core.io.file.FileSystemUtil;
import cn.hutool.core.io.file.PathUtil;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.text.CharSequenceUtil;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* loaded from: classes.dex */
public class ZipUtil {
    private static final int DEFAULT_BYTE_ARRAY_LENGTH = 32;
    private static final Charset DEFAULT_CHARSET = CharsetUtil.defaultCharset();

    public static void append(Path path, Path path2, CopyOption... copyOptionArr) throws IOException, IORuntimeException {
        try {
            FileSystem fileSystemCreateZip = FileSystemUtil.createZip(path.toString());
            try {
                if (Files.isDirectory(path2, new LinkOption[0])) {
                    Path parent = path2.getParent();
                    if (parent == null) {
                        parent = path2;
                    }
                    Files.walkFileTree(path2, new ZipCopyVisitor(parent, fileSystemCreateZip, copyOptionArr));
                } else {
                    Files.copy(path2, fileSystemCreateZip.getPath(PathUtil.getName(path2), new String[0]), copyOptionArr);
                }
                if (fileSystemCreateZip != null) {
                    fileSystemCreateZip.close();
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    if (fileSystemCreateZip != null) {
                        try {
                            fileSystemCreateZip.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                    }
                    throw th2;
                }
            }
        } catch (FileAlreadyExistsException unused) {
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static InputStream get(File file, Charset charset, String str) {
        return get(toZipFile(file, charset), str);
    }

    public static InputStream getStream(ZipFile zipFile, ZipEntry zipEntry) {
        try {
            return new LimitedInputStream(zipFile.getInputStream(zipEntry), zipEntry.getSize());
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static ZipOutputStream getZipOutputStream(OutputStream outputStream, Charset charset) {
        return outputStream instanceof ZipOutputStream ? (ZipOutputStream) outputStream : new ZipOutputStream(outputStream, charset);
    }

    public static byte[] gzip(String str, String str2) throws UtilException {
        return gzip(CharSequenceUtil.bytes(str, str2));
    }

    public static List<String> listFileNames(ZipFile zipFile, String str) {
        if (CharSequenceUtil.isNotBlank(str)) {
            str = CharSequenceUtil.addSuffixIfNot(str, "/");
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = new EnumerationIter(zipFile.entries()).iterator();
        while (it.hasNext()) {
            String name = ((ZipEntry) it.next()).getName();
            if (CharSequenceUtil.isEmpty(str) || name.startsWith(str)) {
                String strRemovePrefix = CharSequenceUtil.removePrefix(name, str);
                if (CharSequenceUtil.isNotEmpty(strRemovePrefix) && !CharSequenceUtil.contains((CharSequence) strRemovePrefix, '/')) {
                    arrayList.add(strRemovePrefix);
                }
            }
        }
        return arrayList;
    }

    public static void read(ZipFile zipFile, Consumer<ZipEntry> consumer) {
        ZipReader zipReader = new ZipReader(zipFile);
        try {
            zipReader.read(consumer);
            zipReader.close();
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    zipReader.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    public static ZipFile toZipFile(File file, Charset charset) {
        try {
            return new ZipFile(file, (Charset) ObjectUtil.defaultIfNull(charset, CharsetUtil.CHARSET_UTF_8));
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static String unGzip(byte[] bArr, String str) throws UtilException {
        return StrUtil.str(unGzip(bArr), str);
    }

    public static String unZlib(byte[] bArr, String str) {
        return StrUtil.str(unZlib(bArr), str);
    }

    public static File unzip(String str) throws UtilException {
        return unzip(str, DEFAULT_CHARSET);
    }

    public static byte[] unzipFileBytes(String str, String str2) {
        return unzipFileBytes(str, DEFAULT_CHARSET, str2);
    }

    private static void validateFiles(File file, File... fileArr) throws UtilException {
        File parentFile;
        if (file.isDirectory()) {
            throw new UtilException("Zip file [{}] must not be a directory !", file.getAbsoluteFile());
        }
        for (File file2 : fileArr) {
            if (file2 != null) {
                if (!file2.exists()) {
                    throw new UtilException(CharSequenceUtil.format("File [{}] not exist!", file2.getAbsolutePath()));
                }
                try {
                    parentFile = file.getCanonicalFile().getParentFile();
                } catch (IOException unused) {
                    parentFile = file.getParentFile();
                }
                if (file2.isDirectory() && FileUtil.isSub(file2, parentFile)) {
                    throw new UtilException("Zip file path [{}] must not be the child directory of [{}] !", file.getPath(), file2.getPath());
                }
            }
        }
    }

    public static File zip(String str) throws UtilException {
        return zip(str, DEFAULT_CHARSET);
    }

    public static byte[] zlib(String str, String str2, int i2) {
        return zlib(CharSequenceUtil.bytes(str, str2), i2);
    }

    public static InputStream get(ZipFile zipFile, String str) {
        ZipEntry entry = zipFile.getEntry(str);
        if (entry != null) {
            return getStream(zipFile, entry);
        }
        return null;
    }

    public static byte[] gzip(byte[] bArr) throws UtilException {
        return gzip(new ByteArrayInputStream(bArr), bArr.length);
    }

    public static byte[] unGzip(byte[] bArr) throws UtilException {
        return unGzip(new ByteArrayInputStream(bArr), bArr.length);
    }

    public static byte[] unZlib(byte[] bArr) {
        return unZlib(new ByteArrayInputStream(bArr), bArr.length);
    }

    public static File unzip(String str, Charset charset) throws UtilException {
        return unzip(FileUtil.file(str), charset);
    }

    public static byte[] unzipFileBytes(String str, Charset charset, String str2) {
        return unzipFileBytes(FileUtil.file(str), charset, str2);
    }

    public static File zip(String str, Charset charset) throws UtilException {
        return zip(FileUtil.file(str), charset);
    }

    public static byte[] zlib(File file, int i2) throws Throwable {
        BufferedInputStream inputStream;
        try {
            inputStream = FileUtil.getInputStream(file);
        } catch (Throwable th) {
            th = th;
            inputStream = null;
        }
        try {
            byte[] bArrZlib = zlib(inputStream, i2, (int) file.length());
            IoUtil.close((Closeable) inputStream);
            return bArrZlib;
        } catch (Throwable th2) {
            th = th2;
            IoUtil.close((Closeable) inputStream);
            throw th;
        }
    }

    public static byte[] gzip(File file) throws Throwable {
        BufferedInputStream inputStream;
        try {
            inputStream = FileUtil.getInputStream(file);
        } catch (Throwable th) {
            th = th;
            inputStream = null;
        }
        try {
            byte[] bArrGzip = gzip(inputStream, (int) file.length());
            IoUtil.close((Closeable) inputStream);
            return bArrGzip;
        } catch (Throwable th2) {
            th = th2;
            IoUtil.close((Closeable) inputStream);
            throw th;
        }
    }

    public static byte[] unGzip(InputStream inputStream) throws UtilException {
        return unGzip(inputStream, 32);
    }

    public static byte[] unZlib(InputStream inputStream) {
        return unZlib(inputStream, 32);
    }

    public static File unzip(File file) throws UtilException {
        return unzip(file, DEFAULT_CHARSET);
    }

    public static byte[] unzipFileBytes(File file, String str) {
        return unzipFileBytes(file, DEFAULT_CHARSET, str);
    }

    public static File zip(File file) throws UtilException {
        return zip(file, DEFAULT_CHARSET);
    }

    public static byte[] unGzip(InputStream inputStream, int i2) throws UtilException, IOException {
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream(i2);
        Gzip.of(inputStream, fastByteArrayOutputStream).unGzip().close();
        return fastByteArrayOutputStream.toByteArray();
    }

    public static byte[] unZlib(InputStream inputStream, int i2) throws IOException, IORuntimeException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i2);
        Deflate.of(inputStream, byteArrayOutputStream, false).inflater();
        return byteArrayOutputStream.toByteArray();
    }

    public static File unzip(File file, Charset charset) throws UtilException {
        return unzip(file, FileUtil.file(file.getParentFile(), FileUtil.mainName(file)), charset);
    }

    public static byte[] unzipFileBytes(File file, Charset charset, String str) throws IOException, IORuntimeException {
        ZipReader zipReaderOf = ZipReader.of(file, charset);
        try {
            byte[] bytes = IoUtil.readBytes(zipReaderOf.get(str));
            zipReaderOf.close();
            return bytes;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                if (zipReaderOf != null) {
                    try {
                        zipReaderOf.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }
    }

    public static File zip(File file, Charset charset) throws UtilException {
        File file2 = FileUtil.file(file.getParentFile(), FileUtil.mainName(file) + ".zip");
        zip(file2, charset, false, file);
        return file2;
    }

    public static void read(ZipInputStream zipInputStream, Consumer<ZipEntry> consumer) throws IOException, IORuntimeException {
        ZipReader zipReader = new ZipReader(zipInputStream);
        try {
            zipReader.read(consumer);
            zipReader.close();
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    zipReader.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    public static File unzip(String str, String str2) throws UtilException {
        return unzip(str, str2, DEFAULT_CHARSET);
    }

    public static File zip(String str, String str2) throws UtilException {
        return zip(str, str2, false);
    }

    public static byte[] zlib(byte[] bArr, int i2) {
        return zlib(new ByteArrayInputStream(bArr), i2, bArr.length);
    }

    public static byte[] gzip(InputStream inputStream) throws UtilException {
        return gzip(inputStream, 32);
    }

    public static File unzip(String str, String str2, Charset charset) throws UtilException {
        return unzip(FileUtil.file(str), FileUtil.mkdir(str2), charset);
    }

    public static File zip(String str, String str2, boolean z2) throws UtilException {
        return zip(str, str2, DEFAULT_CHARSET, z2);
    }

    public static byte[] zlib(InputStream inputStream, int i2) {
        return zlib(inputStream, i2, 32);
    }

    public static byte[] gzip(InputStream inputStream, int i2) throws UtilException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i2);
        Gzip.of(inputStream, byteArrayOutputStream).gzip().close();
        return byteArrayOutputStream.toByteArray();
    }

    public static File unzip(File file, File file2) throws UtilException {
        return unzip(file, file2, DEFAULT_CHARSET);
    }

    public static File zip(String str, String str2, Charset charset, boolean z2) throws UtilException {
        File file = FileUtil.file(str);
        File file2 = FileUtil.file(str2);
        zip(file2, charset, z2, file);
        return file2;
    }

    public static byte[] zlib(InputStream inputStream, int i2, int i3) throws IOException, IORuntimeException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i3);
        Deflate.of(inputStream, byteArrayOutputStream, false).deflater(i2);
        return byteArrayOutputStream.toByteArray();
    }

    public static File unzip(File file, File file2, Charset charset) {
        return unzip(toZipFile(file, charset), file2);
    }

    public static File unzip(ZipFile zipFile, File file) throws IORuntimeException {
        return unzip(zipFile, file, -1L);
    }

    public static File unzip(ZipFile zipFile, File file, long j2) throws IOException, IORuntimeException {
        if (file.exists() && file.isFile()) {
            throw new IllegalArgumentException(CharSequenceUtil.format("Target path [{}] exist!", file.getAbsolutePath()));
        }
        long size = 0;
        if (j2 > 0) {
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
            while (enumerationEntries.hasMoreElements()) {
                size += enumerationEntries.nextElement().getSize();
                if (size > j2) {
                    throw new IllegalArgumentException("The file size exceeds the limit");
                }
            }
        }
        ZipReader zipReader = new ZipReader(zipFile);
        try {
            zipReader.readTo(file);
            zipReader.close();
            return file;
        } finally {
        }
    }

    public static File zip(File file, boolean z2, File... fileArr) throws UtilException {
        return zip(file, DEFAULT_CHARSET, z2, fileArr);
    }

    public static File zip(File file, Charset charset, boolean z2, File... fileArr) throws UtilException {
        return zip(file, charset, z2, (FileFilter) null, fileArr);
    }

    public static File zip(File file, Charset charset, boolean z2, FileFilter fileFilter, File... fileArr) throws UtilException, IOException, IORuntimeException {
        validateFiles(file, fileArr);
        ZipWriter.of(file, charset).add(z2, fileFilter, fileArr).close();
        return file;
    }

    public static void zip(OutputStream outputStream, Charset charset, boolean z2, FileFilter fileFilter, File... fileArr) throws IOException, IORuntimeException {
        ZipWriter.of(outputStream, charset).add(z2, fileFilter, fileArr).close();
    }

    @Deprecated
    public static void zip(ZipOutputStream zipOutputStream, boolean z2, FileFilter fileFilter, File... fileArr) throws IOException, IORuntimeException {
        ZipWriter zipWriter = new ZipWriter(zipOutputStream);
        try {
            zipWriter.add(z2, fileFilter, fileArr);
            zipWriter.close();
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    zipWriter.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    public static File zip(File file, String str, String str2) throws UtilException {
        return zip(file, str, str2, DEFAULT_CHARSET);
    }

    public static File zip(File file, String str, String str2, Charset charset) throws UtilException {
        return zip(file, str, IoUtil.toStream(str2, charset), charset);
    }

    public static File zip(File file, String str, InputStream inputStream) throws UtilException {
        return zip(file, str, inputStream, DEFAULT_CHARSET);
    }

    public static File unzip(InputStream inputStream, File file, Charset charset) throws UtilException {
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        return unzip(new ZipInputStream(inputStream, charset), file);
    }

    public static File zip(File file, String str, InputStream inputStream, Charset charset) throws UtilException {
        return zip(file, new String[]{str}, new InputStream[]{inputStream}, charset);
    }

    public static File zip(File file, String[] strArr, InputStream[] inputStreamArr) throws UtilException {
        return zip(file, strArr, inputStreamArr, DEFAULT_CHARSET);
    }

    public static File unzip(ZipInputStream zipInputStream, File file) throws UtilException, IOException, IORuntimeException {
        ZipReader zipReader = new ZipReader(zipInputStream);
        try {
            zipReader.readTo(file);
            zipReader.close();
            return file;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    zipReader.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    public static File zip(File file, String[] strArr, InputStream[] inputStreamArr, Charset charset) throws UtilException, IOException, IORuntimeException {
        ZipWriter zipWriterOf = ZipWriter.of(file, charset);
        try {
            zipWriterOf.add(strArr, inputStreamArr);
            zipWriterOf.close();
            return file;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                if (zipWriterOf != null) {
                    try {
                        zipWriterOf.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }
    }

    public static void zip(OutputStream outputStream, String[] strArr, InputStream[] inputStreamArr) throws IOException, IORuntimeException {
        zip(getZipOutputStream(outputStream, DEFAULT_CHARSET), strArr, inputStreamArr);
    }

    public static void zip(ZipOutputStream zipOutputStream, String[] strArr, InputStream[] inputStreamArr) throws IOException, IORuntimeException {
        ZipWriter zipWriter = new ZipWriter(zipOutputStream);
        try {
            zipWriter.add(strArr, inputStreamArr);
            zipWriter.close();
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    zipWriter.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    public static File zip(File file, Charset charset, Resource... resourceArr) throws UtilException, IOException, IORuntimeException {
        ZipWriter.of(file, charset).add(resourceArr).close();
        return file;
    }
}
