package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.Cache;
import com.android.volley.VolleyLog;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class DiskBasedCache implements Cache {
    private static final int CACHE_MAGIC = 538051844;
    private static final int DEFAULT_DISK_USAGE_BYTES = 5242880;
    private static final float HYSTERESIS_FACTOR = 0.9f;
    private final Map<String, CacheHeader> mEntries;
    private final int mMaxCacheSizeInBytes;
    private final File mRootDirectory;
    private long mTotalSize;

    public static class CacheHeader {
        public String etag;
        public String key;
        public Map<String, String> responseHeaders;
        public long serverDate;
        public long size;
        public long softTtl;
        public long ttl;

        private CacheHeader() {
        }

        public static CacheHeader readHeader(InputStream inputStream) throws IOException {
            CacheHeader cacheHeader = new CacheHeader();
            if (DiskBasedCache.readInt(inputStream) != DiskBasedCache.CACHE_MAGIC) {
                throw new IOException();
            }
            cacheHeader.key = DiskBasedCache.readString(inputStream);
            String string = DiskBasedCache.readString(inputStream);
            cacheHeader.etag = string;
            if (string.equals("")) {
                cacheHeader.etag = null;
            }
            cacheHeader.serverDate = DiskBasedCache.readLong(inputStream);
            cacheHeader.ttl = DiskBasedCache.readLong(inputStream);
            cacheHeader.softTtl = DiskBasedCache.readLong(inputStream);
            cacheHeader.responseHeaders = DiskBasedCache.readStringStringMap(inputStream);
            return cacheHeader;
        }

        public Cache.Entry toCacheEntry(byte[] bArr) {
            Cache.Entry entry = new Cache.Entry();
            entry.data = bArr;
            entry.etag = this.etag;
            entry.serverDate = this.serverDate;
            entry.ttl = this.ttl;
            entry.softTtl = this.softTtl;
            entry.responseHeaders = this.responseHeaders;
            return entry;
        }

        public boolean writeHeader(OutputStream outputStream) throws IOException {
            try {
                DiskBasedCache.writeInt(outputStream, DiskBasedCache.CACHE_MAGIC);
                DiskBasedCache.writeString(outputStream, this.key);
                String str = this.etag;
                if (str == null) {
                    str = "";
                }
                DiskBasedCache.writeString(outputStream, str);
                DiskBasedCache.writeLong(outputStream, this.serverDate);
                DiskBasedCache.writeLong(outputStream, this.ttl);
                DiskBasedCache.writeLong(outputStream, this.softTtl);
                DiskBasedCache.writeStringStringMap(this.responseHeaders, outputStream);
                outputStream.flush();
                return true;
            } catch (IOException e2) {
                VolleyLog.d("%s", e2.toString());
                return false;
            }
        }

        public CacheHeader(String str, Cache.Entry entry) {
            this.key = str;
            this.size = entry.data.length;
            this.etag = entry.etag;
            this.serverDate = entry.serverDate;
            this.ttl = entry.ttl;
            this.softTtl = entry.softTtl;
            this.responseHeaders = entry.responseHeaders;
        }
    }

    public static class CountingInputStream extends FilterInputStream {
        private int bytesRead;

        public /* synthetic */ CountingInputStream(InputStream inputStream, CountingInputStream countingInputStream) {
            this(inputStream);
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read() throws IOException {
            int i2 = super.read();
            if (i2 != -1) {
                this.bytesRead++;
            }
            return i2;
        }

        private CountingInputStream(InputStream inputStream) {
            super(inputStream);
            this.bytesRead = 0;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i2, int i3) throws IOException {
            int i4 = super.read(bArr, i2, i3);
            if (i4 != -1) {
                this.bytesRead += i4;
            }
            return i4;
        }
    }

    public DiskBasedCache(File file, int i2) {
        this.mEntries = new LinkedHashMap(16, 0.75f, true);
        this.mTotalSize = 0L;
        this.mRootDirectory = file;
        this.mMaxCacheSizeInBytes = i2;
    }

    private String getFilenameForKey(String str) {
        int length = str.length() / 2;
        return String.valueOf(String.valueOf(str.substring(0, length).hashCode())) + String.valueOf(str.substring(length).hashCode());
    }

    private void pruneIfNeeded(int i2) {
        long j2;
        long j3 = i2;
        if (this.mTotalSize + j3 < this.mMaxCacheSizeInBytes) {
            return;
        }
        if (VolleyLog.DEBUG) {
            VolleyLog.v("Pruning old cache entries.", new Object[0]);
        }
        long j4 = this.mTotalSize;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        Iterator<Map.Entry<String, CacheHeader>> it = this.mEntries.entrySet().iterator();
        int i3 = 0;
        while (it.hasNext()) {
            CacheHeader value = it.next().getValue();
            if (getFileForKey(value.key).delete()) {
                j2 = j3;
                this.mTotalSize -= value.size;
            } else {
                j2 = j3;
                String str = value.key;
                VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", str, getFilenameForKey(str));
            }
            it.remove();
            i3++;
            if (this.mTotalSize + j2 < this.mMaxCacheSizeInBytes * HYSTERESIS_FACTOR) {
                break;
            } else {
                j3 = j2;
            }
        }
        if (VolleyLog.DEBUG) {
            VolleyLog.v("pruned %d files, %d bytes, %d ms", Integer.valueOf(i3), Long.valueOf(this.mTotalSize - j4), Long.valueOf(SystemClock.elapsedRealtime() - jElapsedRealtime));
        }
    }

    private void putEntry(String str, CacheHeader cacheHeader) {
        if (this.mEntries.containsKey(str)) {
            this.mTotalSize += cacheHeader.size - this.mEntries.get(str).size;
        } else {
            this.mTotalSize += cacheHeader.size;
        }
        this.mEntries.put(str, cacheHeader);
    }

    private static int read(InputStream inputStream) throws IOException {
        int i2 = inputStream.read();
        if (i2 != -1) {
            return i2;
        }
        throw new EOFException();
    }

    public static int readInt(InputStream inputStream) throws IOException {
        return (read(inputStream) << 24) | (read(inputStream) << 0) | 0 | (read(inputStream) << 8) | (read(inputStream) << 16);
    }

    public static long readLong(InputStream inputStream) throws IOException {
        return ((read(inputStream) & 255) << 0) | 0 | ((read(inputStream) & 255) << 8) | ((read(inputStream) & 255) << 16) | ((read(inputStream) & 255) << 24) | ((read(inputStream) & 255) << 32) | ((read(inputStream) & 255) << 40) | ((read(inputStream) & 255) << 48) | ((255 & read(inputStream)) << 56);
    }

    public static String readString(InputStream inputStream) throws IOException {
        return new String(streamToBytes(inputStream, (int) readLong(inputStream)), "UTF-8");
    }

    public static Map<String, String> readStringStringMap(InputStream inputStream) throws IOException {
        int i2 = readInt(inputStream);
        Map<String, String> mapEmptyMap = i2 == 0 ? Collections.emptyMap() : new HashMap<>(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            mapEmptyMap.put(readString(inputStream).intern(), readString(inputStream).intern());
        }
        return mapEmptyMap;
    }

    private void removeEntry(String str) {
        CacheHeader cacheHeader = this.mEntries.get(str);
        if (cacheHeader != null) {
            this.mTotalSize -= cacheHeader.size;
            this.mEntries.remove(str);
        }
    }

    private static byte[] streamToBytes(InputStream inputStream, int i2) throws IOException {
        byte[] bArr = new byte[i2];
        int i3 = 0;
        while (i3 < i2) {
            int i4 = inputStream.read(bArr, i3, i2 - i3);
            if (i4 == -1) {
                break;
            }
            i3 += i4;
        }
        if (i3 == i2) {
            return bArr;
        }
        throw new IOException("Expected " + i2 + " bytes, read " + i3 + " bytes");
    }

    public static void writeInt(OutputStream outputStream, int i2) throws IOException {
        outputStream.write((i2 >> 0) & 255);
        outputStream.write((i2 >> 8) & 255);
        outputStream.write((i2 >> 16) & 255);
        outputStream.write((i2 >> 24) & 255);
    }

    public static void writeLong(OutputStream outputStream, long j2) throws IOException {
        outputStream.write((byte) (j2 >>> 0));
        outputStream.write((byte) (j2 >>> 8));
        outputStream.write((byte) (j2 >>> 16));
        outputStream.write((byte) (j2 >>> 24));
        outputStream.write((byte) (j2 >>> 32));
        outputStream.write((byte) (j2 >>> 40));
        outputStream.write((byte) (j2 >>> 48));
        outputStream.write((byte) (j2 >>> 56));
    }

    public static void writeString(OutputStream outputStream, String str) throws IOException {
        byte[] bytes = str.getBytes("UTF-8");
        writeLong(outputStream, bytes.length);
        outputStream.write(bytes, 0, bytes.length);
    }

    public static void writeStringStringMap(Map<String, String> map, OutputStream outputStream) throws IOException {
        if (map == null) {
            writeInt(outputStream, 0);
            return;
        }
        writeInt(outputStream, map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            writeString(outputStream, entry.getKey());
            writeString(outputStream, entry.getValue());
        }
    }

    @Override // com.android.volley.Cache
    public synchronized void clear() {
        File[] fileArrListFiles = this.mRootDirectory.listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                file.delete();
            }
        }
        this.mEntries.clear();
        this.mTotalSize = 0L;
        VolleyLog.d("Cache cleared.", new Object[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0066 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.android.volley.Cache
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized com.android.volley.Cache.Entry get(java.lang.String r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            java.util.Map<java.lang.String, com.android.volley.toolbox.DiskBasedCache$CacheHeader> r0 = r8.mEntries     // Catch: java.lang.Throwable -> L6d
            java.lang.Object r0 = r0.get(r9)     // Catch: java.lang.Throwable -> L6d
            com.android.volley.toolbox.DiskBasedCache$CacheHeader r0 = (com.android.volley.toolbox.DiskBasedCache.CacheHeader) r0     // Catch: java.lang.Throwable -> L6d
            r1 = 0
            if (r0 != 0) goto Le
            monitor-exit(r8)
            return r1
        Le:
            java.io.File r2 = r8.getFileForKey(r9)     // Catch: java.lang.Throwable -> L6d
            com.android.volley.toolbox.DiskBasedCache$CountingInputStream r3 = new com.android.volley.toolbox.DiskBasedCache$CountingInputStream     // Catch: java.lang.Throwable -> L3b java.io.IOException -> L3e
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L3b java.io.IOException -> L3e
            r4.<init>(r2)     // Catch: java.lang.Throwable -> L3b java.io.IOException -> L3e
            r3.<init>(r4, r1)     // Catch: java.lang.Throwable -> L3b java.io.IOException -> L3e
            com.android.volley.toolbox.DiskBasedCache.CacheHeader.readHeader(r3)     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L63
            long r4 = r2.length()     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L63
            int r6 = com.android.volley.toolbox.DiskBasedCache.CountingInputStream.access$1(r3)     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L63
            long r6 = (long) r6     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L63
            long r4 = r4 - r6
            int r4 = (int) r4     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L63
            byte[] r4 = streamToBytes(r3, r4)     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L63
            com.android.volley.Cache$Entry r9 = r0.toCacheEntry(r4)     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L63
            r3.close()     // Catch: java.io.IOException -> L37 java.lang.Throwable -> L6d
            monitor-exit(r8)
            return r9
        L37:
            monitor-exit(r8)
            return r1
        L39:
            r0 = move-exception
            goto L40
        L3b:
            r9 = move-exception
            r3 = r1
            goto L64
        L3e:
            r0 = move-exception
            r3 = r1
        L40:
            java.lang.String r4 = "%s: %s"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch: java.lang.Throwable -> L63
            java.lang.String r2 = r2.getAbsolutePath()     // Catch: java.lang.Throwable -> L63
            r6 = 0
            r5[r6] = r2     // Catch: java.lang.Throwable -> L63
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L63
            r2 = 1
            r5[r2] = r0     // Catch: java.lang.Throwable -> L63
            com.android.volley.VolleyLog.d(r4, r5)     // Catch: java.lang.Throwable -> L63
            r8.remove(r9)     // Catch: java.lang.Throwable -> L63
            if (r3 == 0) goto L61
            r3.close()     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L6d
            goto L61
        L5f:
            monitor-exit(r8)
            return r1
        L61:
            monitor-exit(r8)
            return r1
        L63:
            r9 = move-exception
        L64:
            if (r3 == 0) goto L6c
            r3.close()     // Catch: java.io.IOException -> L6a java.lang.Throwable -> L6d
            goto L6c
        L6a:
            monitor-exit(r8)
            return r1
        L6c:
            throw r9     // Catch: java.lang.Throwable -> L6d
        L6d:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.DiskBasedCache.get(java.lang.String):com.android.volley.Cache$Entry");
    }

    public File getFileForKey(String str) {
        return new File(this.mRootDirectory, getFilenameForKey(str));
    }

    @Override // com.android.volley.Cache
    public synchronized void initialize() {
        if (!this.mRootDirectory.exists()) {
            if (!this.mRootDirectory.mkdirs()) {
                VolleyLog.e("Unable to create cache dir %s", this.mRootDirectory.getAbsolutePath());
            }
            return;
        }
        File[] fileArrListFiles = this.mRootDirectory.listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        for (File file : fileArrListFiles) {
            FileInputStream fileInputStream = null;
            try {
                try {
                    FileInputStream fileInputStream2 = new FileInputStream(file);
                    try {
                        CacheHeader header = CacheHeader.readHeader(fileInputStream2);
                        header.size = file.length();
                        putEntry(header.key, header);
                        try {
                            fileInputStream2.close();
                        } catch (IOException unused) {
                        }
                    } catch (IOException unused2) {
                        fileInputStream = fileInputStream2;
                        if (file != null) {
                            file.delete();
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException unused3) {
                            }
                        }
                        throw th;
                    }
                } catch (IOException unused4) {
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    @Override // com.android.volley.Cache
    public synchronized void invalidate(String str, boolean z2) {
        Cache.Entry entry = get(str);
        if (entry != null) {
            entry.softTtl = 0L;
            if (z2) {
                entry.ttl = 0L;
            }
            put(str, entry);
        }
    }

    @Override // com.android.volley.Cache
    public synchronized void put(String str, Cache.Entry entry) {
        pruneIfNeeded(entry.data.length);
        File fileForKey = getFileForKey(str);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileForKey);
            CacheHeader cacheHeader = new CacheHeader(str, entry);
            cacheHeader.writeHeader(fileOutputStream);
            fileOutputStream.write(entry.data);
            fileOutputStream.close();
            putEntry(str, cacheHeader);
        } catch (IOException unused) {
            if (fileForKey.delete()) {
                return;
            }
            VolleyLog.d("Could not clean up file %s", fileForKey.getAbsolutePath());
        }
    }

    @Override // com.android.volley.Cache
    public synchronized void remove(String str) {
        boolean zDelete = getFileForKey(str).delete();
        removeEntry(str);
        if (!zDelete) {
            VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", str, getFilenameForKey(str));
        }
    }

    public DiskBasedCache(File file) {
        this(file, 5242880);
    }
}
