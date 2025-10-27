package cn.hutool.core.compress;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ZipUtil;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/* loaded from: classes.dex */
public class ZipReader implements Closeable {
    private static final int DEFAULT_MAX_SIZE_DIFF = 100;
    private ZipInputStream in;
    private int maxSizeDiff = 100;
    private ZipFile zipFile;

    public ZipReader(File file, Charset charset) {
        this.zipFile = ZipUtil.toZipFile(file, charset);
    }

    private ZipEntry checkZipBomb(ZipEntry zipEntry) {
        if (this.maxSizeDiff < 0) {
            return zipEntry;
        }
        if (zipEntry == null) {
            return null;
        }
        long compressedSize = zipEntry.getCompressedSize();
        long size = zipEntry.getSize();
        if (compressedSize < 0 || size < 0 || this.maxSizeDiff * compressedSize < size) {
            throw new UtilException("Zip bomb attack detected, invalid sizes: compressed {}, uncompressed {}, name {}", Long.valueOf(compressedSize), Long.valueOf(size), zipEntry.getName());
        }
        return zipEntry;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readTo$0(Filter filter, File file, ZipEntry zipEntry) throws IORuntimeException {
        if (filter == null || filter.accept(zipEntry)) {
            String name = zipEntry.getName();
            if (FileUtil.isWindows()) {
                name = CharSequenceUtil.replace(name, "*", StrPool.UNDERLINE);
            }
            File file2 = FileUtil.file(file, name);
            if (zipEntry.isDirectory()) {
                file2.mkdirs();
            } else {
                ZipFile zipFile = this.zipFile;
                FileUtil.writeFromStream(zipFile != null ? ZipUtil.getStream(zipFile, zipEntry) : this.in, file2, false);
            }
        }
    }

    public static ZipReader of(File file, Charset charset) {
        return new ZipReader(file, charset);
    }

    private void readFromStream(Consumer<ZipEntry> consumer) throws IOException, IORuntimeException {
        while (true) {
            try {
                ZipEntry nextEntry = this.in.getNextEntry();
                if (nextEntry == null) {
                    return;
                }
                consumer.accept(nextEntry);
                checkZipBomb(nextEntry);
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        }
    }

    private void readFromZipFile(Consumer<ZipEntry> consumer) {
        Enumeration<? extends ZipEntry> enumerationEntries = this.zipFile.entries();
        while (enumerationEntries.hasMoreElements()) {
            consumer.accept(checkZipBomb(enumerationEntries.nextElement()));
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException, IORuntimeException {
        ZipFile zipFile = this.zipFile;
        if (zipFile != null) {
            IoUtil.close((Closeable) zipFile);
        } else {
            IoUtil.close((Closeable) this.in);
        }
    }

    public InputStream get(String str) throws IOException {
        ZipEntry nextEntry;
        ZipFile zipFile = this.zipFile;
        if (zipFile != null) {
            ZipEntry entry = zipFile.getEntry(str);
            if (entry != null) {
                return ZipUtil.getStream(zipFile, entry);
            }
            return null;
        }
        do {
            try {
                nextEntry = this.in.getNextEntry();
                if (nextEntry == null) {
                    return null;
                }
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } while (!nextEntry.getName().equals(str));
        return this.in;
    }

    public ZipReader read(Consumer<ZipEntry> consumer) throws IOException, IORuntimeException {
        if (this.zipFile != null) {
            readFromZipFile(consumer);
        } else {
            readFromStream(consumer);
        }
        return this;
    }

    public File readTo(File file) throws IORuntimeException {
        return readTo(file, null);
    }

    public ZipReader setMaxSizeDiff(int i2) {
        this.maxSizeDiff = i2;
        return this;
    }

    public static ZipReader of(InputStream inputStream, Charset charset) {
        return new ZipReader(inputStream, charset);
    }

    public File readTo(final File file, final Filter<ZipEntry> filter) throws IOException, IORuntimeException {
        read(new Consumer() { // from class: u.g
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws IORuntimeException {
                this.f28241c.lambda$readTo$0(filter, file, (ZipEntry) obj);
            }
        });
        return file;
    }

    public ZipReader(ZipFile zipFile) {
        this.zipFile = zipFile;
    }

    public ZipReader(InputStream inputStream, Charset charset) {
        this.in = new ZipInputStream(inputStream, charset);
    }

    public ZipReader(ZipInputStream zipInputStream) {
        this.in = zipInputStream;
    }
}
