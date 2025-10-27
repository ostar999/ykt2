package cn.hutool.core.compress;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ZipUtil;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* loaded from: classes.dex */
public class ZipWriter implements Closeable {
    private final ZipOutputStream out;

    public ZipWriter(File file, Charset charset) {
        this.out = getZipOutputStream(file, charset);
    }

    private ZipWriter _add(File file, String str, FileFilter fileFilter) throws IOException, IORuntimeException {
        if (file != null && (fileFilter == null || fileFilter.accept(file))) {
            String strSubPath = FileUtil.subPath(str, file);
            if (file.isDirectory()) {
                File[] fileArrListFiles = file.listFiles();
                if (ArrayUtil.isEmpty((Object[]) fileArrListFiles)) {
                    add(strSubPath, (InputStream) null);
                } else {
                    for (File file2 : fileArrListFiles) {
                        _add(file2, str, fileFilter);
                    }
                }
            } else {
                putEntry(strSubPath, FileUtil.getInputStream(file));
            }
        }
        return this;
    }

    private static ZipOutputStream getZipOutputStream(File file, Charset charset) {
        return ZipUtil.getZipOutputStream(FileUtil.getOutputStream(file), charset);
    }

    public static ZipWriter of(File file, Charset charset) {
        return new ZipWriter(file, charset);
    }

    private ZipWriter putEntry(String str, InputStream inputStream) throws IOException, IORuntimeException {
        try {
            try {
                this.out.putNextEntry(new ZipEntry(str));
                if (inputStream != null) {
                    IoUtil.copy(inputStream, this.out);
                }
                this.out.closeEntry();
                IoUtil.close((Closeable) inputStream);
                IoUtil.flush(this.out);
                return this;
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } catch (Throwable th) {
            IoUtil.close((Closeable) inputStream);
            throw th;
        }
    }

    public ZipWriter add(boolean z2, FileFilter fileFilter, File... fileArr) throws IOException, IORuntimeException {
        for (File file : fileArr) {
            try {
                String canonicalPath = file.getCanonicalPath();
                if (!file.isDirectory() || z2) {
                    canonicalPath = file.getCanonicalFile().getParentFile().getCanonicalPath();
                }
                _add(file, canonicalPath, fileFilter);
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        }
        return this;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException, IORuntimeException {
        try {
            try {
                this.out.finish();
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } finally {
            IoUtil.close((Closeable) this.out);
        }
    }

    public ZipOutputStream getOut() {
        return this.out;
    }

    public ZipWriter setComment(String str) {
        this.out.setComment(str);
        return this;
    }

    public ZipWriter setLevel(int i2) {
        this.out.setLevel(i2);
        return this;
    }

    public static ZipWriter of(OutputStream outputStream, Charset charset) {
        return new ZipWriter(outputStream, charset);
    }

    public ZipWriter(OutputStream outputStream, Charset charset) {
        this.out = ZipUtil.getZipOutputStream(outputStream, charset);
    }

    public ZipWriter(ZipOutputStream zipOutputStream) {
        this.out = zipOutputStream;
    }

    public ZipWriter add(Resource... resourceArr) throws IORuntimeException {
        for (Resource resource : resourceArr) {
            if (resource != null) {
                add(resource.getName(), resource.getStream());
            }
        }
        return this;
    }

    public ZipWriter add(String str, InputStream inputStream) throws IORuntimeException {
        String strNullToEmpty = CharSequenceUtil.nullToEmpty(str);
        if (inputStream == null) {
            strNullToEmpty = CharSequenceUtil.addSuffixIfNot(strNullToEmpty, "/");
            if (CharSequenceUtil.isBlank(strNullToEmpty)) {
                return this;
            }
        }
        return putEntry(strNullToEmpty, inputStream);
    }

    public ZipWriter add(String[] strArr, InputStream[] inputStreamArr) throws IORuntimeException {
        if (!ArrayUtil.isEmpty((Object[]) strArr) && !ArrayUtil.isEmpty((Object[]) inputStreamArr)) {
            if (strArr.length == inputStreamArr.length) {
                for (int i2 = 0; i2 < strArr.length; i2++) {
                    add(strArr[i2], inputStreamArr[i2]);
                }
                return this;
            }
            throw new IllegalArgumentException("Paths length is not equals to ins length !");
        }
        throw new IllegalArgumentException("Paths or ins is empty !");
    }
}
