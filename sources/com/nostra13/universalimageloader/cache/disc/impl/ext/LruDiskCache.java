package com.nostra13.universalimageloader.cache.disc.impl.ext;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.ext.DiskLruCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.utils.IoUtils;
import com.nostra13.universalimageloader.utils.L;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class LruDiskCache implements DiskCache {
    public static final int DEFAULT_BUFFER_SIZE = 32768;
    public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
    public static final int DEFAULT_COMPRESS_QUALITY = 100;
    private static final String ERROR_ARG_NEGATIVE = " argument must be positive number";
    private static final String ERROR_ARG_NULL = " argument must be not null";
    protected int bufferSize;
    protected DiskLruCache cache;
    protected Bitmap.CompressFormat compressFormat;
    protected int compressQuality;
    protected final FileNameGenerator fileNameGenerator;
    private File reserveCacheDir;

    public LruDiskCache(File file, FileNameGenerator fileNameGenerator, long j2) throws IOException {
        this(file, null, fileNameGenerator, j2, 0);
    }

    private String getKey(String str) {
        return this.fileNameGenerator.generate(str);
    }

    private void initCache(File file, File file2, long j2, int i2) throws IOException {
        try {
            this.cache = DiskLruCache.open(file, 1, 1, j2, i2);
        } catch (IOException e2) {
            L.e(e2);
            if (file2 != null) {
                initCache(file2, null, j2, i2);
            }
            if (this.cache == null) {
                throw e2;
            }
        }
    }

    @Override // com.nostra13.universalimageloader.cache.disc.DiskCache
    public void clear() {
        try {
            this.cache.delete();
        } catch (IOException e2) {
            L.e(e2);
        }
        try {
            initCache(this.cache.getDirectory(), this.reserveCacheDir, this.cache.getMaxSize(), this.cache.getMaxFileCount());
        } catch (IOException e3) {
            L.e(e3);
        }
    }

    @Override // com.nostra13.universalimageloader.cache.disc.DiskCache
    public void close() {
        try {
            this.cache.close();
        } catch (IOException e2) {
            L.e(e2);
        }
        this.cache = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x002e  */
    @Override // com.nostra13.universalimageloader.cache.disc.DiskCache
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.io.File get(java.lang.String r4) throws java.lang.Throwable {
        /*
            r3 = this;
            r0 = 0
            com.nostra13.universalimageloader.cache.disc.impl.ext.DiskLruCache r1 = r3.cache     // Catch: java.lang.Throwable -> L1b java.io.IOException -> L20
            java.lang.String r4 = r3.getKey(r4)     // Catch: java.lang.Throwable -> L1b java.io.IOException -> L20
            com.nostra13.universalimageloader.cache.disc.impl.ext.DiskLruCache$Snapshot r4 = r1.get(r4)     // Catch: java.lang.Throwable -> L1b java.io.IOException -> L20
            if (r4 != 0) goto Le
            goto L13
        Le:
            r1 = 0
            java.io.File r0 = r4.getFile(r1)     // Catch: java.io.IOException -> L19 java.lang.Throwable -> L2b
        L13:
            if (r4 == 0) goto L18
            r4.close()
        L18:
            return r0
        L19:
            r1 = move-exception
            goto L22
        L1b:
            r4 = move-exception
            r2 = r0
            r0 = r4
            r4 = r2
            goto L2c
        L20:
            r1 = move-exception
            r4 = r0
        L22:
            com.nostra13.universalimageloader.utils.L.e(r1)     // Catch: java.lang.Throwable -> L2b
            if (r4 == 0) goto L2a
            r4.close()
        L2a:
            return r0
        L2b:
            r0 = move-exception
        L2c:
            if (r4 == 0) goto L31
            r4.close()
        L31:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache.get(java.lang.String):java.io.File");
    }

    @Override // com.nostra13.universalimageloader.cache.disc.DiskCache
    public File getDirectory() {
        return this.cache.getDirectory();
    }

    @Override // com.nostra13.universalimageloader.cache.disc.DiskCache
    public boolean remove(String str) {
        try {
            return this.cache.remove(getKey(str));
        } catch (IOException e2) {
            L.e(e2);
            return false;
        }
    }

    @Override // com.nostra13.universalimageloader.cache.disc.DiskCache
    public boolean save(String str, InputStream inputStream, IoUtils.CopyListener copyListener) throws IOException {
        DiskLruCache.Editor editorEdit = this.cache.edit(getKey(str));
        if (editorEdit == null) {
            return false;
        }
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(editorEdit.newOutputStream(0), this.bufferSize);
        try {
            boolean zCopyStream = IoUtils.copyStream(inputStream, bufferedOutputStream, copyListener, this.bufferSize);
            IoUtils.closeSilently(bufferedOutputStream);
            if (zCopyStream) {
                editorEdit.commit();
            } else {
                editorEdit.abort();
            }
            return zCopyStream;
        } catch (Throwable th) {
            IoUtils.closeSilently(bufferedOutputStream);
            editorEdit.abort();
            throw th;
        }
    }

    public void setBufferSize(int i2) {
        this.bufferSize = i2;
    }

    public void setCompressFormat(Bitmap.CompressFormat compressFormat) {
        this.compressFormat = compressFormat;
    }

    public void setCompressQuality(int i2) {
        this.compressQuality = i2;
    }

    public LruDiskCache(File file, File file2, FileNameGenerator fileNameGenerator, long j2, int i2) throws IOException {
        this.bufferSize = 32768;
        this.compressFormat = DEFAULT_COMPRESS_FORMAT;
        this.compressQuality = 100;
        if (file == null) {
            throw new IllegalArgumentException("cacheDir argument must be not null");
        }
        if (j2 < 0) {
            throw new IllegalArgumentException("cacheMaxSize argument must be positive number");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("cacheMaxFileCount argument must be positive number");
        }
        if (fileNameGenerator == null) {
            throw new IllegalArgumentException("fileNameGenerator argument must be not null");
        }
        long j3 = j2 == 0 ? Long.MAX_VALUE : j2;
        i2 = i2 == 0 ? Integer.MAX_VALUE : i2;
        this.reserveCacheDir = file2;
        this.fileNameGenerator = fileNameGenerator;
        initCache(file, file2, j3, i2);
    }

    @Override // com.nostra13.universalimageloader.cache.disc.DiskCache
    public boolean save(String str, Bitmap bitmap) throws IOException {
        DiskLruCache.Editor editorEdit = this.cache.edit(getKey(str));
        if (editorEdit == null) {
            return false;
        }
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(editorEdit.newOutputStream(0), this.bufferSize);
        try {
            boolean zCompress = bitmap.compress(this.compressFormat, this.compressQuality, bufferedOutputStream);
            if (zCompress) {
                editorEdit.commit();
            } else {
                editorEdit.abort();
            }
            return zCompress;
        } finally {
            IoUtils.closeSilently(bufferedOutputStream);
        }
    }
}
