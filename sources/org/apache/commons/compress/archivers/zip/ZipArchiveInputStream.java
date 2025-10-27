package org.apache.commons.compress.archivers.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.UnsupportedZipFeatureException;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.ArchiveUtils;
import org.apache.commons.compress.utils.IOUtils;

/* loaded from: classes9.dex */
public class ZipArchiveInputStream extends ArchiveInputStream {
    private static final int CFH_LEN = 46;
    private static final int LFH_LEN = 30;
    private static final long TWO_EXP_32 = 4294967296L;
    private final byte[] LFH_BUF;
    private final byte[] SHORT_BUF;
    private final byte[] SKIP_BUF;
    private final byte[] TWO_DWORD_BUF;
    private final byte[] WORD_BUF;
    private boolean allowStoredEntriesWithDataDescriptor;
    private final ByteBuffer buf;
    private boolean closed;
    private CurrentEntry current;
    final String encoding;
    private int entriesRead;
    private boolean hitCentralDirectory;
    private final InputStream in;
    private final Inflater inf;
    private ByteArrayInputStream lastStoredEntry;
    private final boolean useUnicodeExtraFields;
    private final ZipEncoding zipEncoding;
    private static final byte[] LFH = ZipLong.LFH_SIG.getBytes();
    private static final byte[] CFH = ZipLong.CFH_SIG.getBytes();
    private static final byte[] DD = ZipLong.DD_SIG.getBytes();

    public static final class CurrentEntry {
        private long bytesRead;
        private long bytesReadFromStream;
        private final CRC32 crc;
        private final ZipArchiveEntry entry;
        private boolean hasDataDescriptor;
        private InputStream in;
        private boolean usesZip64;

        private CurrentEntry() {
            this.entry = new ZipArchiveEntry();
            this.crc = new CRC32();
        }

        public static /* synthetic */ long access$614(CurrentEntry currentEntry, long j2) {
            long j3 = currentEntry.bytesRead + j2;
            currentEntry.bytesRead = j3;
            return j3;
        }

        public static /* synthetic */ long access$708(CurrentEntry currentEntry) {
            long j2 = currentEntry.bytesReadFromStream;
            currentEntry.bytesReadFromStream = 1 + j2;
            return j2;
        }

        public static /* synthetic */ long access$714(CurrentEntry currentEntry, long j2) {
            long j3 = currentEntry.bytesReadFromStream + j2;
            currentEntry.bytesReadFromStream = j3;
            return j3;
        }
    }

    public ZipArchiveInputStream(InputStream inputStream) {
        this(inputStream, "UTF8");
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a2 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean bufferContainsSignature(java.io.ByteArrayOutputStream r12, int r13, int r14, int r15) throws java.io.IOException {
        /*
            r11 = this;
            r0 = 0
            r1 = r0
            r2 = r1
            r3 = r2
        L4:
            if (r1 != 0) goto La6
            int r4 = r14 + (-4)
            if (r2 >= r4) goto La6
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            r4 = r4[r2]
            byte[] r5 = org.apache.commons.compress.archivers.zip.ZipArchiveInputStream.LFH
            r6 = r5[r0]
            if (r4 != r6) goto La2
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            int r6 = r2 + 1
            r4 = r4[r6]
            r6 = 1
            r7 = r5[r6]
            if (r4 != r7) goto La2
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            int r7 = r2 + 2
            r4 = r4[r7]
            r8 = 2
            r9 = r5[r8]
            r10 = 3
            if (r4 != r9) goto L45
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            int r9 = r2 + 3
            r4 = r4[r9]
            r5 = r5[r10]
            if (r4 == r5) goto L61
        L45:
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            r4 = r4[r2]
            byte[] r5 = org.apache.commons.compress.archivers.zip.ZipArchiveInputStream.CFH
            r9 = r5[r8]
            if (r4 != r9) goto L68
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            int r9 = r2 + 3
            r4 = r4[r9]
            r5 = r5[r10]
            if (r4 != r5) goto L68
        L61:
            int r1 = r13 + r14
            int r1 = r1 - r2
            int r1 = r1 - r15
        L65:
            r3 = r1
            r1 = r6
            goto L88
        L68:
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            r4 = r4[r7]
            byte[] r5 = org.apache.commons.compress.archivers.zip.ZipArchiveInputStream.DD
            r7 = r5[r8]
            if (r4 != r7) goto L88
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            int r7 = r2 + 3
            r4 = r4[r7]
            r5 = r5[r10]
            if (r4 != r5) goto L88
            int r1 = r13 + r14
            int r1 = r1 - r2
            goto L65
        L88:
            if (r1 == 0) goto La2
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            int r5 = r13 + r14
            int r5 = r5 - r3
            r11.pushback(r4, r5, r3)
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            r12.write(r4, r0, r2)
            r11.readDataDescriptor()
        La2:
            int r2 = r2 + 1
            goto L4
        La6:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.archivers.zip.ZipArchiveInputStream.bufferContainsSignature(java.io.ByteArrayOutputStream, int, int, int):boolean");
    }

    private int cacheBytesRead(ByteArrayOutputStream byteArrayOutputStream, int i2, int i3, int i4) {
        int i5 = i2 + i3;
        int i6 = (i5 - i4) - 3;
        if (i6 <= 0) {
            return i5;
        }
        byteArrayOutputStream.write(this.buf.array(), 0, i6);
        int i7 = i4 + 3;
        System.arraycopy(this.buf.array(), i6, this.buf.array(), 0, i7);
        return i7;
    }

    private static boolean checksig(byte[] bArr, byte[] bArr2) {
        for (int i2 = 0; i2 < bArr2.length; i2++) {
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    private void closeEntry() throws DataFormatException, IOException {
        if (this.closed) {
            throw new IOException("The stream is closed");
        }
        CurrentEntry currentEntry = this.current;
        if (currentEntry == null) {
            return;
        }
        if (currentEntry.bytesReadFromStream > this.current.entry.getCompressedSize() || this.current.hasDataDescriptor) {
            skip(Long.MAX_VALUE);
            int bytesInflated = (int) (this.current.bytesReadFromStream - (this.current.entry.getMethod() == 8 ? getBytesInflated() : this.current.bytesRead));
            if (bytesInflated > 0) {
                pushback(this.buf.array(), this.buf.limit() - bytesInflated, bytesInflated);
            }
        } else {
            drainCurrentEntryData();
        }
        if (this.lastStoredEntry == null && this.current.hasDataDescriptor) {
            readDataDescriptor();
        }
        this.inf.reset();
        this.buf.clear().flip();
        this.current = null;
        this.lastStoredEntry = null;
    }

    private void drainCurrentEntryData() throws IOException {
        long compressedSize = this.current.entry.getCompressedSize() - this.current.bytesReadFromStream;
        while (compressedSize > 0) {
            long j2 = this.in.read(this.buf.array(), 0, (int) Math.min(this.buf.capacity(), compressedSize));
            if (j2 < 0) {
                throw new EOFException("Truncated ZIP entry: " + ArchiveUtils.sanitize(this.current.entry.getName()));
            }
            count(j2);
            compressedSize -= j2;
        }
    }

    private int fill() throws IOException {
        if (this.closed) {
            throw new IOException("The stream is closed");
        }
        int i2 = this.in.read(this.buf.array());
        if (i2 > 0) {
            this.buf.limit(i2);
            count(this.buf.limit());
            this.inf.setInput(this.buf.array(), 0, this.buf.limit());
        }
        return i2;
    }

    private void findEocdRecord() throws IOException {
        int oneByte = -1;
        while (true) {
            boolean zIsFirstByteOfEocdSig = false;
            while (true) {
                if (!zIsFirstByteOfEocdSig) {
                    oneByte = readOneByte();
                    if (oneByte <= -1) {
                        return;
                    }
                }
                if (!isFirstByteOfEocdSig(oneByte)) {
                    break;
                }
                oneByte = readOneByte();
                byte[] bArr = ZipArchiveOutputStream.EOCD_SIG;
                if (oneByte == bArr[1]) {
                    oneByte = readOneByte();
                    if (oneByte == bArr[2]) {
                        oneByte = readOneByte();
                        if (oneByte == -1 || oneByte == bArr[3]) {
                            return;
                        } else {
                            zIsFirstByteOfEocdSig = isFirstByteOfEocdSig(oneByte);
                        }
                    } else if (oneByte == -1) {
                        return;
                    } else {
                        zIsFirstByteOfEocdSig = isFirstByteOfEocdSig(oneByte);
                    }
                } else if (oneByte == -1) {
                    return;
                } else {
                    zIsFirstByteOfEocdSig = isFirstByteOfEocdSig(oneByte);
                }
            }
        }
    }

    private long getBytesInflated() {
        long bytesRead = this.inf.getBytesRead();
        if (this.current.bytesReadFromStream >= 4294967296L) {
            while (true) {
                long j2 = bytesRead + 4294967296L;
                if (j2 > this.current.bytesReadFromStream) {
                    break;
                }
                bytesRead = j2;
            }
        }
        return bytesRead;
    }

    private boolean isFirstByteOfEocdSig(int i2) {
        return i2 == ZipArchiveOutputStream.EOCD_SIG[0];
    }

    public static boolean matches(byte[] bArr, int i2) {
        byte[] bArr2 = ZipArchiveOutputStream.LFH_SIG;
        if (i2 < bArr2.length) {
            return false;
        }
        return checksig(bArr, bArr2) || checksig(bArr, ZipArchiveOutputStream.EOCD_SIG) || checksig(bArr, ZipArchiveOutputStream.DD_SIG) || checksig(bArr, ZipLong.SINGLE_SEGMENT_SPLIT_MARKER.getBytes());
    }

    private void processZip64Extra(ZipLong zipLong, ZipLong zipLong2) {
        Zip64ExtendedInformationExtraField zip64ExtendedInformationExtraField = (Zip64ExtendedInformationExtraField) this.current.entry.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        this.current.usesZip64 = zip64ExtendedInformationExtraField != null;
        if (this.current.hasDataDescriptor) {
            return;
        }
        if (zip64ExtendedInformationExtraField != null) {
            ZipLong zipLong3 = ZipLong.ZIP64_MAGIC;
            if (zipLong2.equals(zipLong3) || zipLong.equals(zipLong3)) {
                this.current.entry.setCompressedSize(zip64ExtendedInformationExtraField.getCompressedSize().getLongValue());
                this.current.entry.setSize(zip64ExtendedInformationExtraField.getSize().getLongValue());
                return;
            }
        }
        this.current.entry.setCompressedSize(zipLong2.getValue());
        this.current.entry.setSize(zipLong.getValue());
    }

    private void pushback(byte[] bArr, int i2, int i3) throws IOException {
        ((PushbackInputStream) this.in).unread(bArr, i2, i3);
        pushedBackBytes(i3);
    }

    private void readDataDescriptor() throws IOException {
        readFully(this.WORD_BUF);
        ZipLong zipLong = new ZipLong(this.WORD_BUF);
        if (ZipLong.DD_SIG.equals(zipLong)) {
            readFully(this.WORD_BUF);
            zipLong = new ZipLong(this.WORD_BUF);
        }
        this.current.entry.setCrc(zipLong.getValue());
        readFully(this.TWO_DWORD_BUF);
        ZipLong zipLong2 = new ZipLong(this.TWO_DWORD_BUF, 8);
        if (!zipLong2.equals(ZipLong.CFH_SIG) && !zipLong2.equals(ZipLong.LFH_SIG)) {
            this.current.entry.setCompressedSize(ZipEightByteInteger.getLongValue(this.TWO_DWORD_BUF));
            this.current.entry.setSize(ZipEightByteInteger.getLongValue(this.TWO_DWORD_BUF, 8));
        } else {
            pushback(this.TWO_DWORD_BUF, 8, 8);
            this.current.entry.setCompressedSize(ZipLong.getValue(this.TWO_DWORD_BUF));
            this.current.entry.setSize(ZipLong.getValue(this.TWO_DWORD_BUF, 4));
        }
    }

    private int readDeflated(byte[] bArr, int i2, int i3) throws DataFormatException, IOException {
        int fromInflater = readFromInflater(bArr, i2, i3);
        if (fromInflater <= 0) {
            if (this.inf.finished()) {
                return -1;
            }
            if (this.inf.needsDictionary()) {
                throw new ZipException("This archive needs a preset dictionary which is not supported by Commons Compress.");
            }
            if (fromInflater == -1) {
                throw new IOException("Truncated ZIP file");
            }
        }
        return fromInflater;
    }

    private void readFirstLocalFileHeader(byte[] bArr) throws IOException {
        readFully(bArr);
        ZipLong zipLong = new ZipLong(bArr);
        if (zipLong.equals(ZipLong.DD_SIG)) {
            throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.SPLITTING);
        }
        if (zipLong.equals(ZipLong.SINGLE_SEGMENT_SPLIT_MARKER)) {
            byte[] bArr2 = new byte[4];
            readFully(bArr2);
            System.arraycopy(bArr, 4, bArr, 0, 26);
            System.arraycopy(bArr2, 0, bArr, 26, 4);
        }
    }

    private int readFromInflater(byte[] bArr, int i2, int i3) throws DataFormatException, IOException {
        int iInflate = 0;
        while (true) {
            if (this.inf.needsInput()) {
                int iFill = fill();
                if (iFill > 0) {
                    CurrentEntry.access$714(this.current, this.buf.limit());
                    iInflate = this.inf.inflate(bArr, i2, i3);
                    if (iInflate != 0) {
                        break;
                    }
                    break;
                    break;
                }
                if (iFill == -1) {
                    return -1;
                }
            } else {
                try {
                    iInflate = this.inf.inflate(bArr, i2, i3);
                    if (iInflate != 0 || !this.inf.needsInput()) {
                        break;
                    }
                } catch (DataFormatException e2) {
                    throw ((IOException) new ZipException(e2.getMessage()).initCause(e2));
                }
            }
        }
        return iInflate;
    }

    private void readFully(byte[] bArr) throws IOException {
        int fully = IOUtils.readFully(this.in, bArr);
        count(fully);
        if (fully < bArr.length) {
            throw new EOFException();
        }
    }

    private int readOneByte() throws IOException {
        int i2 = this.in.read();
        if (i2 != -1) {
            count(1);
        }
        return i2;
    }

    private int readStored(byte[] bArr, int i2, int i3) throws IOException {
        if (this.current.hasDataDescriptor) {
            if (this.lastStoredEntry == null) {
                readStoredEntry();
            }
            return this.lastStoredEntry.read(bArr, i2, i3);
        }
        long size = this.current.entry.getSize();
        if (this.current.bytesRead >= size) {
            return -1;
        }
        if (this.buf.position() >= this.buf.limit()) {
            this.buf.position(0);
            int i4 = this.in.read(this.buf.array());
            if (i4 == -1) {
                return -1;
            }
            this.buf.limit(i4);
            count(i4);
            CurrentEntry.access$714(this.current, i4);
        }
        int iMin = Math.min(this.buf.remaining(), i3);
        if (size - this.current.bytesRead < iMin) {
            iMin = (int) (size - this.current.bytesRead);
        }
        this.buf.get(bArr, i2, iMin);
        CurrentEntry.access$614(this.current, iMin);
        return iMin;
    }

    private void readStoredEntry() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = this.current.usesZip64 ? 20 : 12;
        boolean zBufferContainsSignature = false;
        int iCacheBytesRead = 0;
        while (!zBufferContainsSignature) {
            int i3 = this.in.read(this.buf.array(), iCacheBytesRead, 512 - iCacheBytesRead);
            if (i3 <= 0) {
                throw new IOException("Truncated ZIP file");
            }
            int i4 = i3 + iCacheBytesRead;
            if (i4 < 4) {
                iCacheBytesRead = i4;
            } else {
                zBufferContainsSignature = bufferContainsSignature(byteArrayOutputStream, iCacheBytesRead, i3, i2);
                if (!zBufferContainsSignature) {
                    iCacheBytesRead = cacheBytesRead(byteArrayOutputStream, iCacheBytesRead, i3, i2);
                }
            }
        }
        this.lastStoredEntry = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    private void realSkip(long j2) throws IOException {
        long j3 = 0;
        if (j2 < 0) {
            throw new IllegalArgumentException();
        }
        while (j3 < j2) {
            long length = j2 - j3;
            InputStream inputStream = this.in;
            byte[] bArr = this.SKIP_BUF;
            if (bArr.length <= length) {
                length = bArr.length;
            }
            int i2 = inputStream.read(bArr, 0, (int) length);
            if (i2 == -1) {
                return;
            }
            count(i2);
            j3 += i2;
        }
    }

    private void skipRemainderOfArchive() throws IOException {
        realSkip((this.entriesRead * 46) - 30);
        findEocdRecord();
        realSkip(16L);
        readFully(this.SHORT_BUF);
        realSkip(ZipShort.getValue(this.SHORT_BUF));
    }

    private boolean supportsDataDescriptorFor(ZipArchiveEntry zipArchiveEntry) {
        return !zipArchiveEntry.getGeneralPurposeBit().usesDataDescriptor() || (this.allowStoredEntriesWithDataDescriptor && zipArchiveEntry.getMethod() == 0) || zipArchiveEntry.getMethod() == 8;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public boolean canReadEntryData(ArchiveEntry archiveEntry) {
        if (!(archiveEntry instanceof ZipArchiveEntry)) {
            return false;
        }
        ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) archiveEntry;
        return ZipUtil.canHandleEntryData(zipArchiveEntry) && supportsDataDescriptorFor(zipArchiveEntry);
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        try {
            this.in.close();
        } finally {
            this.inf.end();
        }
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public ArchiveEntry getNextEntry() throws IOException {
        return getNextZipEntry();
    }

    public ZipArchiveEntry getNextZipEntry() throws IOException {
        boolean z2;
        ZipLong zipLong;
        ZipLong zipLong2;
        if (!this.closed && !this.hitCentralDirectory) {
            if (this.current != null) {
                closeEntry();
                z2 = false;
            } else {
                z2 = true;
            }
            try {
                if (z2) {
                    readFirstLocalFileHeader(this.LFH_BUF);
                } else {
                    readFully(this.LFH_BUF);
                }
                ZipLong zipLong3 = new ZipLong(this.LFH_BUF);
                if (zipLong3.equals(ZipLong.CFH_SIG) || zipLong3.equals(ZipLong.AED_SIG)) {
                    this.hitCentralDirectory = true;
                    skipRemainderOfArchive();
                }
                if (!zipLong3.equals(ZipLong.LFH_SIG)) {
                    return null;
                }
                this.current = new CurrentEntry();
                this.current.entry.setPlatform((ZipShort.getValue(this.LFH_BUF, 4) >> 8) & 15);
                GeneralPurposeBit generalPurposeBit = GeneralPurposeBit.parse(this.LFH_BUF, 6);
                boolean zUsesUTF8ForNames = generalPurposeBit.usesUTF8ForNames();
                ZipEncoding zipEncoding = zUsesUTF8ForNames ? ZipEncodingHelper.UTF8_ZIP_ENCODING : this.zipEncoding;
                this.current.hasDataDescriptor = generalPurposeBit.usesDataDescriptor();
                this.current.entry.setGeneralPurposeBit(generalPurposeBit);
                this.current.entry.setMethod(ZipShort.getValue(this.LFH_BUF, 8));
                this.current.entry.setTime(ZipUtil.dosToJavaTime(ZipLong.getValue(this.LFH_BUF, 10)));
                if (this.current.hasDataDescriptor) {
                    zipLong = null;
                    zipLong2 = null;
                } else {
                    this.current.entry.setCrc(ZipLong.getValue(this.LFH_BUF, 14));
                    zipLong = new ZipLong(this.LFH_BUF, 18);
                    zipLong2 = new ZipLong(this.LFH_BUF, 22);
                }
                int value = ZipShort.getValue(this.LFH_BUF, 26);
                int value2 = ZipShort.getValue(this.LFH_BUF, 28);
                byte[] bArr = new byte[value];
                readFully(bArr);
                this.current.entry.setName(zipEncoding.decode(bArr), bArr);
                byte[] bArr2 = new byte[value2];
                readFully(bArr2);
                this.current.entry.setExtra(bArr2);
                if (!zUsesUTF8ForNames && this.useUnicodeExtraFields) {
                    ZipUtil.setNameAndCommentFromExtraFields(this.current.entry, bArr, null);
                }
                processZip64Extra(zipLong2, zipLong);
                if (this.current.entry.getCompressedSize() != -1) {
                    if (this.current.entry.getMethod() == ZipMethod.UNSHRINKING.getCode()) {
                        CurrentEntry currentEntry = this.current;
                        currentEntry.in = new UnshrinkingInputStream(new BoundedInputStream(this.in, currentEntry.entry.getCompressedSize()));
                    } else if (this.current.entry.getMethod() == ZipMethod.IMPLODING.getCode()) {
                        CurrentEntry currentEntry2 = this.current;
                        currentEntry2.in = new ExplodingInputStream(currentEntry2.entry.getGeneralPurposeBit().getSlidingDictionarySize(), this.current.entry.getGeneralPurposeBit().getNumberOfShannonFanoTrees(), new BoundedInputStream(this.in, this.current.entry.getCompressedSize()));
                    } else if (this.current.entry.getMethod() == ZipMethod.BZIP2.getCode()) {
                        CurrentEntry currentEntry3 = this.current;
                        currentEntry3.in = new BZip2CompressorInputStream(new BoundedInputStream(this.in, currentEntry3.entry.getCompressedSize()));
                    }
                }
                this.entriesRead++;
                return this.current.entry;
            } catch (EOFException unused) {
            }
        }
        return null;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws DataFormatException, IOException {
        int deflated;
        if (this.closed) {
            throw new IOException("The stream is closed");
        }
        CurrentEntry currentEntry = this.current;
        if (currentEntry == null) {
            return -1;
        }
        if (i2 > bArr.length || i3 < 0 || i2 < 0 || bArr.length - i2 < i3) {
            throw new ArrayIndexOutOfBoundsException();
        }
        ZipUtil.checkRequestedFeatures(currentEntry.entry);
        if (!supportsDataDescriptorFor(this.current.entry)) {
            throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.DATA_DESCRIPTOR, this.current.entry);
        }
        if (this.current.entry.getMethod() == 0) {
            deflated = readStored(bArr, i2, i3);
        } else if (this.current.entry.getMethod() == 8) {
            deflated = readDeflated(bArr, i2, i3);
        } else {
            if (this.current.entry.getMethod() != ZipMethod.UNSHRINKING.getCode() && this.current.entry.getMethod() != ZipMethod.IMPLODING.getCode() && this.current.entry.getMethod() != ZipMethod.BZIP2.getCode()) {
                throw new UnsupportedZipFeatureException(ZipMethod.getMethodByCode(this.current.entry.getMethod()), this.current.entry);
            }
            deflated = this.current.in.read(bArr, i2, i3);
        }
        if (deflated >= 0) {
            this.current.crc.update(bArr, i2, deflated);
        }
        return deflated;
    }

    @Override // java.io.InputStream
    public long skip(long j2) throws DataFormatException, IOException {
        long j3 = 0;
        if (j2 < 0) {
            throw new IllegalArgumentException();
        }
        while (j3 < j2) {
            long length = j2 - j3;
            byte[] bArr = this.SKIP_BUF;
            if (bArr.length <= length) {
                length = bArr.length;
            }
            int i2 = read(bArr, 0, (int) length);
            if (i2 == -1) {
                return j3;
            }
            j3 += i2;
        }
        return j3;
    }

    public ZipArchiveInputStream(InputStream inputStream, String str) {
        this(inputStream, str, true);
    }

    public ZipArchiveInputStream(InputStream inputStream, String str, boolean z2) {
        this(inputStream, str, z2, false);
    }

    public ZipArchiveInputStream(InputStream inputStream, String str, boolean z2, boolean z3) {
        this.inf = new Inflater(true);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(512);
        this.buf = byteBufferAllocate;
        this.current = null;
        this.closed = false;
        this.hitCentralDirectory = false;
        this.lastStoredEntry = null;
        this.allowStoredEntriesWithDataDescriptor = false;
        this.LFH_BUF = new byte[30];
        this.SKIP_BUF = new byte[1024];
        this.SHORT_BUF = new byte[2];
        this.WORD_BUF = new byte[4];
        this.TWO_DWORD_BUF = new byte[16];
        this.entriesRead = 0;
        this.encoding = str;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(str);
        this.useUnicodeExtraFields = z2;
        this.in = new PushbackInputStream(inputStream, byteBufferAllocate.capacity());
        this.allowStoredEntriesWithDataDescriptor = z3;
        byteBufferAllocate.limit(0);
    }

    public class BoundedInputStream extends InputStream {
        private final InputStream in;
        private final long max;
        private long pos = 0;

        public BoundedInputStream(InputStream inputStream, long j2) {
            this.max = j2;
            this.in = inputStream;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            long j2 = this.max;
            if (j2 < 0 || this.pos < j2) {
                return this.in.available();
            }
            return 0;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            long j2 = this.max;
            if (j2 >= 0 && this.pos >= j2) {
                return -1;
            }
            int i2 = this.in.read();
            this.pos++;
            ZipArchiveInputStream.this.count(1);
            CurrentEntry.access$708(ZipArchiveInputStream.this.current);
            return i2;
        }

        @Override // java.io.InputStream
        public long skip(long j2) throws IOException {
            long j3 = this.max;
            if (j3 >= 0) {
                j2 = Math.min(j2, j3 - this.pos);
            }
            long jSkip = this.in.skip(j2);
            this.pos += jSkip;
            return jSkip;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr) throws IOException {
            return read(bArr, 0, bArr.length);
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i2, int i3) throws IOException {
            long j2 = this.max;
            if (j2 >= 0 && this.pos >= j2) {
                return -1;
            }
            int i4 = this.in.read(bArr, i2, (int) (j2 >= 0 ? Math.min(i3, j2 - this.pos) : i3));
            if (i4 == -1) {
                return -1;
            }
            long j3 = i4;
            this.pos += j3;
            ZipArchiveInputStream.this.count(i4);
            CurrentEntry.access$714(ZipArchiveInputStream.this.current, j3);
            return i4;
        }
    }
}
