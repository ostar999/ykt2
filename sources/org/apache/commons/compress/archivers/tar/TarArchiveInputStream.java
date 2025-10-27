package org.apache.commons.compress.archivers.tar;

import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.utils.ArchiveUtils;
import org.apache.commons.compress.utils.IOUtils;

/* loaded from: classes9.dex */
public class TarArchiveInputStream extends ArchiveInputStream {
    private static final int SMALL_BUFFER_SIZE = 256;
    private final byte[] SMALL_BUF;
    private final int blockSize;
    private TarArchiveEntry currEntry;
    final String encoding;
    private long entryOffset;
    private long entrySize;
    private Map<String, String> globalPaxHeaders;
    private boolean hasHitEOF;
    private final InputStream is;
    private final int recordSize;
    private final ZipEncoding zipEncoding;

    public TarArchiveInputStream(InputStream inputStream) {
        this(inputStream, 10240, 512);
    }

    private void applyPaxHeadersToCurrentEntry(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if ("path".equals(key)) {
                this.currEntry.setName(value);
            } else if ("linkpath".equals(key)) {
                this.currEntry.setLinkName(value);
            } else if ("gid".equals(key)) {
                this.currEntry.setGroupId(Long.parseLong(value));
            } else if ("gname".equals(key)) {
                this.currEntry.setGroupName(value);
            } else if ("uid".equals(key)) {
                this.currEntry.setUserId(Long.parseLong(value));
            } else if ("uname".equals(key)) {
                this.currEntry.setUserName(value);
            } else if (DatabaseManager.SIZE.equals(key)) {
                this.currEntry.setSize(Long.parseLong(value));
            } else if ("mtime".equals(key)) {
                this.currEntry.setModTime((long) (Double.parseDouble(value) * 1000.0d));
            } else if ("SCHILY.devminor".equals(key)) {
                this.currEntry.setDevMinor(Integer.parseInt(value));
            } else if ("SCHILY.devmajor".equals(key)) {
                this.currEntry.setDevMajor(Integer.parseInt(value));
            } else if ("GNU.sparse.size".equals(key)) {
                this.currEntry.fillGNUSparse0xData(map);
            } else if ("GNU.sparse.realsize".equals(key)) {
                this.currEntry.fillGNUSparse1xData(map);
            } else if ("SCHILY.filetype".equals(key) && "sparse".equals(value)) {
                this.currEntry.fillStarSparseData(map);
            }
        }
    }

    private void consumeRemainderOfLastBlock() throws IOException {
        long bytesRead = getBytesRead();
        int i2 = this.blockSize;
        long j2 = bytesRead % i2;
        if (j2 > 0) {
            count(IOUtils.skip(this.is, i2 - j2));
        }
    }

    private byte[] getRecord() throws IOException {
        byte[] record = readRecord();
        boolean zIsEOFRecord = isEOFRecord(record);
        this.hasHitEOF = zIsEOFRecord;
        if (!zIsEOFRecord || record == null) {
            return record;
        }
        tryToConsumeSecondEOFRecord();
        consumeRemainderOfLastBlock();
        return null;
    }

    private boolean isDirectory() {
        TarArchiveEntry tarArchiveEntry = this.currEntry;
        return tarArchiveEntry != null && tarArchiveEntry.isDirectory();
    }

    public static boolean matches(byte[] bArr, int i2) {
        if (i2 < 265) {
            return false;
        }
        if (ArchiveUtils.matchAsciiBuffer("ustar\u0000", bArr, 257, 6) && ArchiveUtils.matchAsciiBuffer(TarConstants.VERSION_POSIX, bArr, 263, 2)) {
            return true;
        }
        if (ArchiveUtils.matchAsciiBuffer(TarConstants.MAGIC_GNU, bArr, 257, 6) && (ArchiveUtils.matchAsciiBuffer(TarConstants.VERSION_GNU_SPACE, bArr, 263, 2) || ArchiveUtils.matchAsciiBuffer(TarConstants.VERSION_GNU_ZERO, bArr, 263, 2))) {
            return true;
        }
        return ArchiveUtils.matchAsciiBuffer("ustar\u0000", bArr, 257, 6) && ArchiveUtils.matchAsciiBuffer(TarConstants.VERSION_ANT, bArr, 263, 2);
    }

    private void paxHeaders() throws IOException {
        Map<String, String> paxHeaders = parsePaxHeaders(this);
        getNextEntry();
        applyPaxHeadersToCurrentEntry(paxHeaders);
    }

    private void readGlobalPaxHeaders() throws IOException {
        this.globalPaxHeaders = parsePaxHeaders(this);
        getNextEntry();
    }

    private void readOldGNUSparse() throws IOException {
        byte[] record;
        if (this.currEntry.isExtended()) {
            do {
                record = getRecord();
                if (record == null) {
                    this.currEntry = null;
                    return;
                }
            } while (new TarArchiveSparseEntry(record).isExtended());
        }
    }

    private void skipRecordPadding() throws IOException {
        if (isDirectory()) {
            return;
        }
        long j2 = this.entrySize;
        if (j2 > 0) {
            int i2 = this.recordSize;
            if (j2 % i2 != 0) {
                count(IOUtils.skip(this.is, (((j2 / i2) + 1) * i2) - j2));
            }
        }
    }

    private void tryToConsumeSecondEOFRecord() throws IOException {
        boolean zMarkSupported = this.is.markSupported();
        if (zMarkSupported) {
            this.is.mark(this.recordSize);
        }
        try {
            if ((!isEOFRecord(readRecord())) && zMarkSupported) {
            }
        } finally {
            if (zMarkSupported) {
                pushedBackBytes(this.recordSize);
                this.is.reset();
            }
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (isDirectory()) {
            return 0;
        }
        long j2 = this.entrySize;
        long j3 = this.entryOffset;
        if (j2 - j3 > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) (j2 - j3);
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public boolean canReadEntryData(ArchiveEntry archiveEntry) {
        if (archiveEntry instanceof TarArchiveEntry) {
            return !((TarArchiveEntry) archiveEntry).isSparse();
        }
        return false;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.is.close();
    }

    public TarArchiveEntry getCurrentEntry() {
        return this.currEntry;
    }

    public byte[] getLongNameData() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int i2 = read(this.SMALL_BUF);
            if (i2 < 0) {
                break;
            }
            byteArrayOutputStream.write(this.SMALL_BUF, 0, i2);
        }
        getNextEntry();
        if (this.currEntry == null) {
            return null;
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        int length = byteArray.length;
        while (length > 0 && byteArray[length - 1] == 0) {
            length--;
        }
        if (length == byteArray.length) {
            return byteArray;
        }
        byte[] bArr = new byte[length];
        System.arraycopy(byteArray, 0, bArr, 0, length);
        return bArr;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public ArchiveEntry getNextEntry() throws IOException {
        return getNextTarEntry();
    }

    public TarArchiveEntry getNextTarEntry() throws IOException {
        if (this.hasHitEOF) {
            return null;
        }
        if (this.currEntry != null) {
            IOUtils.skip(this, Long.MAX_VALUE);
            skipRecordPadding();
        }
        byte[] record = getRecord();
        if (record == null) {
            this.currEntry = null;
            return null;
        }
        try {
            TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(record, this.zipEncoding);
            this.currEntry = tarArchiveEntry;
            this.entryOffset = 0L;
            this.entrySize = tarArchiveEntry.getSize();
            if (this.currEntry.isGNULongLinkEntry()) {
                byte[] longNameData = getLongNameData();
                if (longNameData == null) {
                    return null;
                }
                this.currEntry.setLinkName(this.zipEncoding.decode(longNameData));
            }
            if (this.currEntry.isGNULongNameEntry()) {
                byte[] longNameData2 = getLongNameData();
                if (longNameData2 == null) {
                    return null;
                }
                this.currEntry.setName(this.zipEncoding.decode(longNameData2));
            }
            if (this.currEntry.isGlobalPaxHeader()) {
                readGlobalPaxHeaders();
            }
            if (this.currEntry.isPaxHeader()) {
                paxHeaders();
            } else if (!this.globalPaxHeaders.isEmpty()) {
                applyPaxHeadersToCurrentEntry(this.globalPaxHeaders);
            }
            if (this.currEntry.isOldGNUSparse()) {
                readOldGNUSparse();
            }
            this.entrySize = this.currEntry.getSize();
            return this.currEntry;
        } catch (IllegalArgumentException e2) {
            throw new IOException("Error detected parsing the header", e2);
        }
    }

    public int getRecordSize() {
        return this.recordSize;
    }

    public final boolean isAtEOF() {
        return this.hasHitEOF;
    }

    public boolean isEOFRecord(byte[] bArr) {
        return bArr == null || ArchiveUtils.isArrayZero(bArr, this.recordSize);
    }

    @Override // java.io.InputStream
    public void mark(int i2) {
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0070, code lost:
    
        r4 = r7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Map<java.lang.String, java.lang.String> parsePaxHeaders(java.io.InputStream r10) throws java.io.IOException {
        /*
            r9 = this;
            java.util.HashMap r0 = new java.util.HashMap
            java.util.Map<java.lang.String, java.lang.String> r1 = r9.globalPaxHeaders
            r0.<init>(r1)
        L7:
            r1 = 0
            r2 = r1
            r3 = r2
        La:
            int r4 = r10.read()
            r5 = -1
            if (r4 == r5) goto L78
            r6 = 1
            int r2 = r2 + r6
            r7 = 10
            if (r4 != r7) goto L18
            goto L78
        L18:
            r7 = 32
            if (r4 != r7) goto L72
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream
            r4.<init>()
        L21:
            int r7 = r10.read()
            if (r7 == r5) goto L70
            int r2 = r2 + r6
            r8 = 61
            if (r7 != r8) goto L6b
            java.lang.String r8 = "UTF-8"
            java.lang.String r4 = r4.toString(r8)
            int r3 = r3 - r2
            if (r3 != r6) goto L39
            r0.remove(r4)
            goto L70
        L39:
            byte[] r2 = new byte[r3]
            int r6 = org.apache.commons.compress.utils.IOUtils.readFully(r10, r2)
            if (r6 != r3) goto L4c
            java.lang.String r6 = new java.lang.String
            int r3 = r3 + (-1)
            r6.<init>(r2, r1, r3, r8)
            r0.put(r4, r6)
            goto L70
        L4c:
            java.io.IOException r10 = new java.io.IOException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Failed to read Paxheader. Expected "
            r0.append(r1)
            r0.append(r3)
            java.lang.String r1 = " bytes, read "
            r0.append(r1)
            r0.append(r6)
            java.lang.String r0 = r0.toString()
            r10.<init>(r0)
            throw r10
        L6b:
            byte r7 = (byte) r7
            r4.write(r7)
            goto L21
        L70:
            r4 = r7
            goto L78
        L72:
            int r3 = r3 * 10
            int r4 = r4 + (-48)
            int r3 = r3 + r4
            goto La
        L78:
            if (r4 != r5) goto L7
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.archivers.tar.TarArchiveInputStream.parsePaxHeaders(java.io.InputStream):java.util.Map");
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (this.hasHitEOF || isDirectory() || this.entryOffset >= this.entrySize) {
            return -1;
        }
        if (this.currEntry == null) {
            throw new IllegalStateException("No current tar entry");
        }
        int iMin = Math.min(i3, available());
        int i4 = this.is.read(bArr, i2, iMin);
        if (i4 != -1) {
            count(i4);
            this.entryOffset += i4;
        } else {
            if (iMin > 0) {
                throw new IOException("Truncated TAR archive");
            }
            this.hasHitEOF = true;
        }
        return i4;
    }

    public byte[] readRecord() throws IOException {
        byte[] bArr = new byte[this.recordSize];
        int fully = IOUtils.readFully(this.is, bArr);
        count(fully);
        if (fully != this.recordSize) {
            return null;
        }
        return bArr;
    }

    @Override // java.io.InputStream
    public synchronized void reset() {
    }

    public final void setAtEOF(boolean z2) {
        this.hasHitEOF = z2;
    }

    public final void setCurrentEntry(TarArchiveEntry tarArchiveEntry) {
        this.currEntry = tarArchiveEntry;
    }

    @Override // java.io.InputStream
    public long skip(long j2) throws IOException {
        if (j2 <= 0 || isDirectory()) {
            return 0L;
        }
        long jSkip = this.is.skip(Math.min(j2, this.entrySize - this.entryOffset));
        count(jSkip);
        this.entryOffset += jSkip;
        return jSkip;
    }

    public TarArchiveInputStream(InputStream inputStream, String str) {
        this(inputStream, 10240, 512, str);
    }

    public TarArchiveInputStream(InputStream inputStream, int i2) {
        this(inputStream, i2, 512);
    }

    public TarArchiveInputStream(InputStream inputStream, int i2, String str) {
        this(inputStream, i2, 512, str);
    }

    public TarArchiveInputStream(InputStream inputStream, int i2, int i3) {
        this(inputStream, i2, i3, null);
    }

    public TarArchiveInputStream(InputStream inputStream, int i2, int i3, String str) {
        this.SMALL_BUF = new byte[256];
        this.globalPaxHeaders = new HashMap();
        this.is = inputStream;
        this.hasHitEOF = false;
        this.encoding = str;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(str);
        this.recordSize = i3;
        this.blockSize = i2;
    }
}
