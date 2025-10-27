package org.apache.commons.compress.archivers.zip;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.ZipException;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

/* loaded from: classes9.dex */
public class ZipArchiveOutputStream extends ArchiveOutputStream {
    static final int BUFFER_SIZE = 512;
    private static final int CFH_COMMENT_LENGTH_OFFSET = 32;
    private static final int CFH_COMPRESSED_SIZE_OFFSET = 20;
    private static final int CFH_CRC_OFFSET = 16;
    private static final int CFH_DISK_NUMBER_OFFSET = 34;
    private static final int CFH_EXTERNAL_ATTRIBUTES_OFFSET = 38;
    private static final int CFH_EXTRA_LENGTH_OFFSET = 30;
    private static final int CFH_FILENAME_LENGTH_OFFSET = 28;
    private static final int CFH_FILENAME_OFFSET = 46;
    private static final int CFH_GPB_OFFSET = 8;
    private static final int CFH_INTERNAL_ATTRIBUTES_OFFSET = 36;
    private static final int CFH_LFH_OFFSET = 42;
    private static final int CFH_METHOD_OFFSET = 10;
    private static final int CFH_ORIGINAL_SIZE_OFFSET = 24;
    private static final int CFH_SIG_OFFSET = 0;
    private static final int CFH_TIME_OFFSET = 12;
    private static final int CFH_VERSION_MADE_BY_OFFSET = 4;
    private static final int CFH_VERSION_NEEDED_OFFSET = 6;
    public static final int DEFAULT_COMPRESSION = -1;
    static final String DEFAULT_ENCODING = "UTF8";
    public static final int DEFLATED = 8;

    @Deprecated
    public static final int EFS_FLAG = 2048;
    private static final int LFH_COMPRESSED_SIZE_OFFSET = 18;
    private static final int LFH_CRC_OFFSET = 14;
    private static final int LFH_EXTRA_LENGTH_OFFSET = 28;
    private static final int LFH_FILENAME_LENGTH_OFFSET = 26;
    private static final int LFH_FILENAME_OFFSET = 30;
    private static final int LFH_GPB_OFFSET = 6;
    private static final int LFH_METHOD_OFFSET = 8;
    private static final int LFH_ORIGINAL_SIZE_OFFSET = 22;
    private static final int LFH_SIG_OFFSET = 0;
    private static final int LFH_TIME_OFFSET = 10;
    private static final int LFH_VERSION_NEEDED_OFFSET = 4;
    public static final int STORED = 0;
    private final Calendar calendarInstance;
    private long cdLength;
    private long cdOffset;
    private String comment;
    private final byte[] copyBuffer;
    private UnicodeExtraFieldPolicy createUnicodeExtraFields;
    protected final Deflater def;
    private String encoding;
    private final List<ZipArchiveEntry> entries;
    private CurrentEntry entry;
    private boolean fallbackToUTF8;
    protected boolean finished;
    private boolean hasCompressionLevelChanged;
    private boolean hasUsedZip64;
    private int level;
    private int method;
    private final Map<ZipArchiveEntry, Long> offsets;
    private final OutputStream out;
    private final RandomAccessFile raf;
    private final StreamCompressor streamCompressor;
    private boolean useUTF8Flag;
    private Zip64Mode zip64Mode;
    private ZipEncoding zipEncoding;
    private static final byte[] EMPTY = new byte[0];
    private static final byte[] ZERO = {0, 0};
    private static final byte[] LZERO = {0, 0, 0, 0};
    private static final byte[] ONE = ZipLong.getBytes(1);
    static final byte[] LFH_SIG = ZipLong.LFH_SIG.getBytes();
    static final byte[] DD_SIG = ZipLong.DD_SIG.getBytes();
    static final byte[] CFH_SIG = ZipLong.CFH_SIG.getBytes();
    static final byte[] EOCD_SIG = ZipLong.getBytes(InternalZipConstants.ENDSIG);
    static final byte[] ZIP64_EOCD_SIG = ZipLong.getBytes(InternalZipConstants.ZIP64ENDCENDIRREC);
    static final byte[] ZIP64_EOCD_LOC_SIG = ZipLong.getBytes(InternalZipConstants.ZIP64ENDCENDIRLOC);

    public static final class CurrentEntry {
        private long bytesRead;
        private boolean causedUseOfZip64;
        private long dataStart;
        private final ZipArchiveEntry entry;
        private boolean hasWritten;
        private long localDataStart;

        private CurrentEntry(ZipArchiveEntry zipArchiveEntry) {
            this.localDataStart = 0L;
            this.dataStart = 0L;
            this.bytesRead = 0L;
            this.causedUseOfZip64 = false;
            this.entry = zipArchiveEntry;
        }
    }

    public static final class UnicodeExtraFieldPolicy {
        public static final UnicodeExtraFieldPolicy ALWAYS = new UnicodeExtraFieldPolicy("always");
        public static final UnicodeExtraFieldPolicy NEVER = new UnicodeExtraFieldPolicy("never");
        public static final UnicodeExtraFieldPolicy NOT_ENCODEABLE = new UnicodeExtraFieldPolicy("not encodeable");
        private final String name;

        private UnicodeExtraFieldPolicy(String str) {
            this.name = str;
        }

        public String toString() {
            return this.name;
        }
    }

    public ZipArchiveOutputStream(OutputStream outputStream) {
        this.finished = false;
        this.comment = "";
        this.level = -1;
        this.hasCompressionLevelChanged = false;
        this.method = 8;
        this.entries = new LinkedList();
        this.cdOffset = 0L;
        this.cdLength = 0L;
        this.offsets = new HashMap();
        this.encoding = "UTF8";
        this.zipEncoding = ZipEncodingHelper.getZipEncoding("UTF8");
        this.useUTF8Flag = true;
        this.fallbackToUTF8 = false;
        this.createUnicodeExtraFields = UnicodeExtraFieldPolicy.NEVER;
        this.hasUsedZip64 = false;
        this.zip64Mode = Zip64Mode.AsNeeded;
        this.copyBuffer = new byte[32768];
        this.calendarInstance = Calendar.getInstance();
        this.out = outputStream;
        this.raf = null;
        Deflater deflater = new Deflater(this.level, true);
        this.def = deflater;
        this.streamCompressor = StreamCompressor.create(outputStream, deflater);
    }

    private void addUnicodeExtraFields(ZipArchiveEntry zipArchiveEntry, boolean z2, ByteBuffer byteBuffer) throws IOException {
        UnicodeExtraFieldPolicy unicodeExtraFieldPolicy = this.createUnicodeExtraFields;
        UnicodeExtraFieldPolicy unicodeExtraFieldPolicy2 = UnicodeExtraFieldPolicy.ALWAYS;
        if (unicodeExtraFieldPolicy == unicodeExtraFieldPolicy2 || !z2) {
            zipArchiveEntry.addExtraField(new UnicodePathExtraField(zipArchiveEntry.getName(), byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit() - byteBuffer.position()));
        }
        String comment = zipArchiveEntry.getComment();
        if (comment == null || "".equals(comment)) {
            return;
        }
        boolean zCanEncode = this.zipEncoding.canEncode(comment);
        if (this.createUnicodeExtraFields == unicodeExtraFieldPolicy2 || !zCanEncode) {
            ByteBuffer byteBufferEncode = getEntryEncoding(zipArchiveEntry).encode(comment);
            zipArchiveEntry.addExtraField(new UnicodeCommentExtraField(comment, byteBufferEncode.array(), byteBufferEncode.arrayOffset(), byteBufferEncode.limit() - byteBufferEncode.position()));
        }
    }

    private boolean checkIfNeedsZip64(Zip64Mode zip64Mode) throws ZipException {
        boolean zIsZip64Required = isZip64Required(this.entry.entry, zip64Mode);
        if (zIsZip64Required && zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException(Zip64RequiredException.getEntryTooBigMessage(this.entry.entry));
        }
        return zIsZip64Required;
    }

    private void closeCopiedEntry(boolean z2) throws IOException {
        preClose();
        CurrentEntry currentEntry = this.entry;
        currentEntry.bytesRead = currentEntry.entry.getSize();
        closeEntry(checkIfNeedsZip64(getEffectiveZip64Mode(this.entry.entry)), z2);
    }

    private void closeEntry(boolean z2, boolean z3) throws IOException {
        if (!z3 && this.raf != null) {
            rewriteSizesAndCrc(z2);
        }
        writeDataDescriptor(this.entry.entry);
        this.entry = null;
    }

    private void copyFromZipInputStream(InputStream inputStream) throws IOException {
        CurrentEntry currentEntry = this.entry;
        if (currentEntry == null) {
            throw new IllegalStateException("No current entry");
        }
        ZipUtil.checkRequestedFeatures(currentEntry.entry);
        this.entry.hasWritten = true;
        while (true) {
            int i2 = inputStream.read(this.copyBuffer);
            if (i2 < 0) {
                return;
            }
            this.streamCompressor.writeCounted(this.copyBuffer, 0, i2);
            count(i2);
        }
    }

    private byte[] createCentralFileHeader(ZipArchiveEntry zipArchiveEntry) throws IOException {
        long jLongValue = this.offsets.get(zipArchiveEntry).longValue();
        boolean z2 = hasZip64Extra(zipArchiveEntry) || zipArchiveEntry.getCompressedSize() >= InternalZipConstants.ZIP_64_LIMIT || zipArchiveEntry.getSize() >= InternalZipConstants.ZIP_64_LIMIT || jLongValue >= InternalZipConstants.ZIP_64_LIMIT || this.zip64Mode == Zip64Mode.Always;
        if (z2 && this.zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException("archive's size exceeds the limit of 4GByte.");
        }
        handleZip64Extra(zipArchiveEntry, jLongValue, z2);
        return createCentralFileHeader(zipArchiveEntry, getName(zipArchiveEntry), jLongValue, z2);
    }

    private byte[] createLocalFileHeader(ZipArchiveEntry zipArchiveEntry, ByteBuffer byteBuffer, boolean z2, boolean z3) {
        byte[] localFileDataExtra = zipArchiveEntry.getLocalFileDataExtra();
        int iLimit = byteBuffer.limit() - byteBuffer.position();
        int i2 = iLimit + 30;
        byte[] bArr = new byte[localFileDataExtra.length + i2];
        System.arraycopy(LFH_SIG, 0, bArr, 0, 4);
        int method = zipArchiveEntry.getMethod();
        if (!z3 || isZip64Required(this.entry.entry, this.zip64Mode)) {
            ZipShort.putShort(versionNeededToExtract(method, hasZip64Extra(zipArchiveEntry)), bArr, 4);
        } else {
            ZipShort.putShort(10, bArr, 4);
        }
        getGeneralPurposeBits(method, !z2 && this.fallbackToUTF8).encode(bArr, 6);
        ZipShort.putShort(method, bArr, 8);
        ZipUtil.toDosTime(this.calendarInstance, zipArchiveEntry.getTime(), bArr, 10);
        if (z3) {
            ZipLong.putLong(zipArchiveEntry.getCrc(), bArr, 14);
        } else if (method == 8 || this.raf != null) {
            System.arraycopy(LZERO, 0, bArr, 14, 4);
        } else {
            ZipLong.putLong(zipArchiveEntry.getCrc(), bArr, 14);
        }
        if (hasZip64Extra(this.entry.entry)) {
            ZipLong zipLong = ZipLong.ZIP64_MAGIC;
            zipLong.putLong(bArr, 18);
            zipLong.putLong(bArr, 22);
        } else if (z3) {
            ZipLong.putLong(zipArchiveEntry.getCompressedSize(), bArr, 18);
            ZipLong.putLong(zipArchiveEntry.getSize(), bArr, 22);
        } else if (method == 8 || this.raf != null) {
            byte[] bArr2 = LZERO;
            System.arraycopy(bArr2, 0, bArr, 18, 4);
            System.arraycopy(bArr2, 0, bArr, 22, 4);
        } else {
            ZipLong.putLong(zipArchiveEntry.getSize(), bArr, 18);
            ZipLong.putLong(zipArchiveEntry.getSize(), bArr, 22);
        }
        ZipShort.putShort(iLimit, bArr, 26);
        ZipShort.putShort(localFileDataExtra.length, bArr, 28);
        System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset(), bArr, 30, iLimit);
        System.arraycopy(localFileDataExtra, 0, bArr, i2, localFileDataExtra.length);
        return bArr;
    }

    private void flushDeflater() throws IOException {
        if (this.entry.entry.getMethod() == 8) {
            this.streamCompressor.flushDeflater();
        }
    }

    private Zip64Mode getEffectiveZip64Mode(ZipArchiveEntry zipArchiveEntry) {
        return (this.zip64Mode == Zip64Mode.AsNeeded && this.raf == null && zipArchiveEntry.getMethod() == 8 && zipArchiveEntry.getSize() == -1) ? Zip64Mode.Never : this.zip64Mode;
    }

    private ZipEncoding getEntryEncoding(ZipArchiveEntry zipArchiveEntry) {
        return (this.zipEncoding.canEncode(zipArchiveEntry.getName()) || !this.fallbackToUTF8) ? this.zipEncoding : ZipEncodingHelper.UTF8_ZIP_ENCODING;
    }

    private GeneralPurposeBit getGeneralPurposeBits(int i2, boolean z2) {
        GeneralPurposeBit generalPurposeBit = new GeneralPurposeBit();
        generalPurposeBit.useUTF8ForNames(this.useUTF8Flag || z2);
        if (isDeflatedToOutputStream(i2)) {
            generalPurposeBit.useDataDescriptor(true);
        }
        return generalPurposeBit;
    }

    private ByteBuffer getName(ZipArchiveEntry zipArchiveEntry) throws IOException {
        return getEntryEncoding(zipArchiveEntry).encode(zipArchiveEntry.getName());
    }

    private Zip64ExtendedInformationExtraField getZip64Extra(ZipArchiveEntry zipArchiveEntry) {
        CurrentEntry currentEntry = this.entry;
        if (currentEntry != null) {
            currentEntry.causedUseOfZip64 = !this.hasUsedZip64;
        }
        this.hasUsedZip64 = true;
        Zip64ExtendedInformationExtraField zip64ExtendedInformationExtraField = (Zip64ExtendedInformationExtraField) zipArchiveEntry.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        if (zip64ExtendedInformationExtraField == null) {
            zip64ExtendedInformationExtraField = new Zip64ExtendedInformationExtraField();
        }
        zipArchiveEntry.addAsFirstExtraField(zip64ExtendedInformationExtraField);
        return zip64ExtendedInformationExtraField;
    }

    private boolean handleSizesAndCrc(long j2, long j3, Zip64Mode zip64Mode) throws ZipException {
        if (this.entry.entry.getMethod() == 8) {
            this.entry.entry.setSize(this.entry.bytesRead);
            this.entry.entry.setCompressedSize(j2);
            this.entry.entry.setCrc(j3);
        } else if (this.raf != null) {
            this.entry.entry.setSize(j2);
            this.entry.entry.setCompressedSize(j2);
            this.entry.entry.setCrc(j3);
        } else {
            if (this.entry.entry.getCrc() != j3) {
                throw new ZipException("bad CRC checksum for entry " + this.entry.entry.getName() + ": " + Long.toHexString(this.entry.entry.getCrc()) + " instead of " + Long.toHexString(j3));
            }
            if (this.entry.entry.getSize() != j2) {
                throw new ZipException("bad size for entry " + this.entry.entry.getName() + ": " + this.entry.entry.getSize() + " instead of " + j2);
            }
        }
        return checkIfNeedsZip64(zip64Mode);
    }

    private void handleZip64Extra(ZipArchiveEntry zipArchiveEntry, long j2, boolean z2) {
        if (z2) {
            Zip64ExtendedInformationExtraField zip64Extra = getZip64Extra(zipArchiveEntry);
            if (zipArchiveEntry.getCompressedSize() >= InternalZipConstants.ZIP_64_LIMIT || zipArchiveEntry.getSize() >= InternalZipConstants.ZIP_64_LIMIT || this.zip64Mode == Zip64Mode.Always) {
                zip64Extra.setCompressedSize(new ZipEightByteInteger(zipArchiveEntry.getCompressedSize()));
                zip64Extra.setSize(new ZipEightByteInteger(zipArchiveEntry.getSize()));
            } else {
                zip64Extra.setCompressedSize(null);
                zip64Extra.setSize(null);
            }
            if (j2 >= InternalZipConstants.ZIP_64_LIMIT || this.zip64Mode == Zip64Mode.Always) {
                zip64Extra.setRelativeHeaderOffset(new ZipEightByteInteger(j2));
            }
            zipArchiveEntry.setExtra();
        }
    }

    private boolean hasZip64Extra(ZipArchiveEntry zipArchiveEntry) {
        return zipArchiveEntry.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID) != null;
    }

    private boolean isDeflatedToOutputStream(int i2) {
        return i2 == 8 && this.raf == null;
    }

    private boolean isTooLageForZip32(ZipArchiveEntry zipArchiveEntry) {
        return zipArchiveEntry.getSize() >= InternalZipConstants.ZIP_64_LIMIT || zipArchiveEntry.getCompressedSize() >= InternalZipConstants.ZIP_64_LIMIT;
    }

    private boolean isZip64Required(ZipArchiveEntry zipArchiveEntry, Zip64Mode zip64Mode) {
        return zip64Mode == Zip64Mode.Always || isTooLageForZip32(zipArchiveEntry);
    }

    private void preClose() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        CurrentEntry currentEntry = this.entry;
        if (currentEntry == null) {
            throw new IOException("No current entry to close");
        }
        if (currentEntry.hasWritten) {
            return;
        }
        write(EMPTY, 0, 0);
    }

    private void rewriteSizesAndCrc(boolean z2) throws IOException {
        long filePointer = this.raf.getFilePointer();
        this.raf.seek(this.entry.localDataStart);
        writeOut(ZipLong.getBytes(this.entry.entry.getCrc()));
        if (hasZip64Extra(this.entry.entry) && z2) {
            ZipLong zipLong = ZipLong.ZIP64_MAGIC;
            writeOut(zipLong.getBytes());
            writeOut(zipLong.getBytes());
        } else {
            writeOut(ZipLong.getBytes(this.entry.entry.getCompressedSize()));
            writeOut(ZipLong.getBytes(this.entry.entry.getSize()));
        }
        if (hasZip64Extra(this.entry.entry)) {
            ByteBuffer name = getName(this.entry.entry);
            this.raf.seek(this.entry.localDataStart + 12 + 4 + (name.limit() - name.position()) + 4);
            writeOut(ZipEightByteInteger.getBytes(this.entry.entry.getSize()));
            writeOut(ZipEightByteInteger.getBytes(this.entry.entry.getCompressedSize()));
            if (!z2) {
                this.raf.seek(this.entry.localDataStart - 10);
                writeOut(ZipShort.getBytes(10));
                this.entry.entry.removeExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
                this.entry.entry.setExtra();
                if (this.entry.causedUseOfZip64) {
                    this.hasUsedZip64 = false;
                }
            }
        }
        this.raf.seek(filePointer);
    }

    private void setDefaults(ZipArchiveEntry zipArchiveEntry) {
        if (zipArchiveEntry.getMethod() == -1) {
            zipArchiveEntry.setMethod(this.method);
        }
        if (zipArchiveEntry.getTime() == -1) {
            zipArchiveEntry.setTime(System.currentTimeMillis());
        }
    }

    private boolean shouldAddZip64Extra(ZipArchiveEntry zipArchiveEntry, Zip64Mode zip64Mode) {
        return zip64Mode == Zip64Mode.Always || zipArchiveEntry.getSize() >= InternalZipConstants.ZIP_64_LIMIT || zipArchiveEntry.getCompressedSize() >= InternalZipConstants.ZIP_64_LIMIT || !(zipArchiveEntry.getSize() != -1 || this.raf == null || zip64Mode == Zip64Mode.Never);
    }

    private void validateSizeInformation(Zip64Mode zip64Mode) throws ZipException {
        if (this.entry.entry.getMethod() == 0 && this.raf == null) {
            if (this.entry.entry.getSize() == -1) {
                throw new ZipException("uncompressed size is required for STORED method when not writing to a file");
            }
            if (this.entry.entry.getCrc() == -1) {
                throw new ZipException("crc checksum is required for STORED method when not writing to a file");
            }
            this.entry.entry.setCompressedSize(this.entry.entry.getSize());
        }
        if ((this.entry.entry.getSize() >= InternalZipConstants.ZIP_64_LIMIT || this.entry.entry.getCompressedSize() >= InternalZipConstants.ZIP_64_LIMIT) && zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException(Zip64RequiredException.getEntryTooBigMessage(this.entry.entry));
        }
    }

    private int versionNeededToExtract(int i2, boolean z2) {
        if (z2) {
            return 45;
        }
        return isDeflatedToOutputStream(i2) ? 20 : 10;
    }

    private void writeCentralDirectoryInChunks() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(70000);
        Iterator<ZipArchiveEntry> it = this.entries.iterator();
        while (true) {
            int i2 = 0;
            while (it.hasNext()) {
                byteArrayOutputStream.write(createCentralFileHeader(it.next()));
                i2++;
                if (i2 > 1000) {
                    break;
                }
            }
            writeCounted(byteArrayOutputStream.toByteArray());
            return;
            writeCounted(byteArrayOutputStream.toByteArray());
            byteArrayOutputStream.reset();
        }
    }

    private void writeCounted(byte[] bArr) throws IOException {
        this.streamCompressor.writeCounted(bArr);
    }

    public void addRawArchiveEntry(ZipArchiveEntry zipArchiveEntry, InputStream inputStream) throws IOException {
        ZipArchiveEntry zipArchiveEntry2 = new ZipArchiveEntry(zipArchiveEntry);
        if (hasZip64Extra(zipArchiveEntry2)) {
            zipArchiveEntry2.removeExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        }
        boolean z2 = (zipArchiveEntry2.getCrc() == -1 || zipArchiveEntry2.getSize() == -1 || zipArchiveEntry2.getCompressedSize() == -1) ? false : true;
        putArchiveEntry(zipArchiveEntry2, z2);
        copyFromZipInputStream(inputStream);
        closeCopiedEntry(z2);
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public boolean canWriteEntryData(ArchiveEntry archiveEntry) {
        if (!(archiveEntry instanceof ZipArchiveEntry)) {
            return false;
        }
        ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) archiveEntry;
        return (zipArchiveEntry.getMethod() == ZipMethod.IMPLODING.getCode() || zipArchiveEntry.getMethod() == ZipMethod.UNSHRINKING.getCode() || !ZipUtil.canHandleEntryData(zipArchiveEntry)) ? false : true;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.finished) {
            finish();
        }
        destroy();
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void closeArchiveEntry() throws IOException {
        preClose();
        flushDeflater();
        long totalBytesWritten = this.streamCompressor.getTotalBytesWritten() - this.entry.dataStart;
        long crc32 = this.streamCompressor.getCrc32();
        this.entry.bytesRead = this.streamCompressor.getBytesRead();
        closeEntry(handleSizesAndCrc(totalBytesWritten, crc32, getEffectiveZip64Mode(this.entry.entry)), false);
        this.streamCompressor.reset();
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public ArchiveEntry createArchiveEntry(File file, String str) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        return new ZipArchiveEntry(file, str);
    }

    public final void deflate() throws IOException {
        this.streamCompressor.deflate();
    }

    public void destroy() throws IOException {
        RandomAccessFile randomAccessFile = this.raf;
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
        OutputStream outputStream = this.out;
        if (outputStream != null) {
            outputStream.close();
        }
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void finish() throws IOException {
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        if (this.entry != null) {
            throw new IOException("This archive contains unclosed entries.");
        }
        this.cdOffset = this.streamCompressor.getTotalBytesWritten();
        writeCentralDirectoryInChunks();
        this.cdLength = this.streamCompressor.getTotalBytesWritten() - this.cdOffset;
        writeZip64CentralDirectory();
        writeCentralDirectoryEnd();
        this.offsets.clear();
        this.entries.clear();
        this.streamCompressor.close();
        this.finished = true;
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        OutputStream outputStream = this.out;
        if (outputStream != null) {
            outputStream.flush();
        }
    }

    public String getEncoding() {
        return this.encoding;
    }

    public boolean isSeekable() {
        return this.raf != null;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        putArchiveEntry(archiveEntry, false);
    }

    public void setComment(String str) {
        this.comment = str;
    }

    public void setCreateUnicodeExtraFields(UnicodeExtraFieldPolicy unicodeExtraFieldPolicy) {
        this.createUnicodeExtraFields = unicodeExtraFieldPolicy;
    }

    public void setEncoding(String str) {
        this.encoding = str;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(str);
        if (!this.useUTF8Flag || ZipEncodingHelper.isUTF8(str)) {
            return;
        }
        this.useUTF8Flag = false;
    }

    public void setFallbackToUTF8(boolean z2) {
        this.fallbackToUTF8 = z2;
    }

    public void setLevel(int i2) {
        if (i2 >= -1 && i2 <= 9) {
            this.hasCompressionLevelChanged = this.level != i2;
            this.level = i2;
        } else {
            throw new IllegalArgumentException("Invalid compression level: " + i2);
        }
    }

    public void setMethod(int i2) {
        this.method = i2;
    }

    public void setUseLanguageEncodingFlag(boolean z2) {
        this.useUTF8Flag = z2 && ZipEncodingHelper.isUTF8(this.encoding);
    }

    public void setUseZip64(Zip64Mode zip64Mode) {
        this.zip64Mode = zip64Mode;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        CurrentEntry currentEntry = this.entry;
        if (currentEntry == null) {
            throw new IllegalStateException("No current entry");
        }
        ZipUtil.checkRequestedFeatures(currentEntry.entry);
        count(this.streamCompressor.write(bArr, i2, i3, this.entry.entry.getMethod()));
    }

    public void writeCentralDirectoryEnd() throws IOException {
        writeCounted(EOCD_SIG);
        byte[] bArr = ZERO;
        writeCounted(bArr);
        writeCounted(bArr);
        int size = this.entries.size();
        if (size > 65535 && this.zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException("archive contains more than 65535 entries.");
        }
        if (this.cdOffset > InternalZipConstants.ZIP_64_LIMIT && this.zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException("archive's size exceeds the limit of 4GByte.");
        }
        byte[] bytes = ZipShort.getBytes(Math.min(size, 65535));
        writeCounted(bytes);
        writeCounted(bytes);
        writeCounted(ZipLong.getBytes(Math.min(this.cdLength, InternalZipConstants.ZIP_64_LIMIT)));
        writeCounted(ZipLong.getBytes(Math.min(this.cdOffset, InternalZipConstants.ZIP_64_LIMIT)));
        ByteBuffer byteBufferEncode = this.zipEncoding.encode(this.comment);
        int iLimit = byteBufferEncode.limit() - byteBufferEncode.position();
        writeCounted(ZipShort.getBytes(iLimit));
        this.streamCompressor.writeCounted(byteBufferEncode.array(), byteBufferEncode.arrayOffset(), iLimit);
    }

    public void writeCentralFileHeader(ZipArchiveEntry zipArchiveEntry) throws IOException {
        writeCounted(createCentralFileHeader(zipArchiveEntry));
    }

    public void writeDataDescriptor(ZipArchiveEntry zipArchiveEntry) throws IOException {
        if (zipArchiveEntry.getMethod() == 8 && this.raf == null) {
            writeCounted(DD_SIG);
            writeCounted(ZipLong.getBytes(zipArchiveEntry.getCrc()));
            if (hasZip64Extra(zipArchiveEntry)) {
                writeCounted(ZipEightByteInteger.getBytes(zipArchiveEntry.getCompressedSize()));
                writeCounted(ZipEightByteInteger.getBytes(zipArchiveEntry.getSize()));
            } else {
                writeCounted(ZipLong.getBytes(zipArchiveEntry.getCompressedSize()));
                writeCounted(ZipLong.getBytes(zipArchiveEntry.getSize()));
            }
        }
    }

    public void writeLocalFileHeader(ZipArchiveEntry zipArchiveEntry) throws IOException {
        writeLocalFileHeader(zipArchiveEntry, false);
    }

    public final void writeOut(byte[] bArr) throws IOException {
        this.streamCompressor.writeOut(bArr, 0, bArr.length);
    }

    public void writeZip64CentralDirectory() throws IOException {
        if (this.zip64Mode == Zip64Mode.Never) {
            return;
        }
        if (!this.hasUsedZip64 && (this.cdOffset >= InternalZipConstants.ZIP_64_LIMIT || this.cdLength >= InternalZipConstants.ZIP_64_LIMIT || this.entries.size() >= 65535)) {
            this.hasUsedZip64 = true;
        }
        if (this.hasUsedZip64) {
            long totalBytesWritten = this.streamCompressor.getTotalBytesWritten();
            writeOut(ZIP64_EOCD_SIG);
            writeOut(ZipEightByteInteger.getBytes(44L));
            writeOut(ZipShort.getBytes(45));
            writeOut(ZipShort.getBytes(45));
            byte[] bArr = LZERO;
            writeOut(bArr);
            writeOut(bArr);
            byte[] bytes = ZipEightByteInteger.getBytes(this.entries.size());
            writeOut(bytes);
            writeOut(bytes);
            writeOut(ZipEightByteInteger.getBytes(this.cdLength));
            writeOut(ZipEightByteInteger.getBytes(this.cdOffset));
            writeOut(ZIP64_EOCD_LOC_SIG);
            writeOut(bArr);
            writeOut(ZipEightByteInteger.getBytes(totalBytesWritten));
            writeOut(ONE);
        }
    }

    private void putArchiveEntry(ArchiveEntry archiveEntry, boolean z2) throws IOException {
        ZipEightByteInteger zipEightByteInteger;
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        if (this.entry != null) {
            closeArchiveEntry();
        }
        ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) archiveEntry;
        CurrentEntry currentEntry = new CurrentEntry(zipArchiveEntry);
        this.entry = currentEntry;
        this.entries.add(currentEntry.entry);
        setDefaults(this.entry.entry);
        Zip64Mode effectiveZip64Mode = getEffectiveZip64Mode(this.entry.entry);
        validateSizeInformation(effectiveZip64Mode);
        if (shouldAddZip64Extra(this.entry.entry, effectiveZip64Mode)) {
            Zip64ExtendedInformationExtraField zip64Extra = getZip64Extra(this.entry.entry);
            ZipEightByteInteger zipEightByteInteger2 = ZipEightByteInteger.ZERO;
            if (z2) {
                zipEightByteInteger2 = new ZipEightByteInteger(this.entry.entry.getSize());
                zipEightByteInteger = new ZipEightByteInteger(this.entry.entry.getCompressedSize());
            } else {
                if (this.entry.entry.getMethod() == 0 && this.entry.entry.getSize() != -1) {
                    zipEightByteInteger2 = new ZipEightByteInteger(this.entry.entry.getSize());
                }
                zipEightByteInteger = zipEightByteInteger2;
            }
            zip64Extra.setSize(zipEightByteInteger2);
            zip64Extra.setCompressedSize(zipEightByteInteger);
            this.entry.entry.setExtra();
        }
        if (this.entry.entry.getMethod() == 8 && this.hasCompressionLevelChanged) {
            this.def.setLevel(this.level);
            this.hasCompressionLevelChanged = false;
        }
        writeLocalFileHeader(zipArchiveEntry, z2);
    }

    private void writeLocalFileHeader(ZipArchiveEntry zipArchiveEntry, boolean z2) throws IOException {
        boolean zCanEncode = this.zipEncoding.canEncode(zipArchiveEntry.getName());
        ByteBuffer name = getName(zipArchiveEntry);
        if (this.createUnicodeExtraFields != UnicodeExtraFieldPolicy.NEVER) {
            addUnicodeExtraFields(zipArchiveEntry, zCanEncode, name);
        }
        byte[] bArrCreateLocalFileHeader = createLocalFileHeader(zipArchiveEntry, name, zCanEncode, z2);
        long totalBytesWritten = this.streamCompressor.getTotalBytesWritten();
        this.offsets.put(zipArchiveEntry, Long.valueOf(totalBytesWritten));
        this.entry.localDataStart = totalBytesWritten + 14;
        writeCounted(bArrCreateLocalFileHeader);
        this.entry.dataStart = this.streamCompressor.getTotalBytesWritten();
    }

    public final void writeOut(byte[] bArr, int i2, int i3) throws IOException {
        this.streamCompressor.writeOut(bArr, i2, i3);
    }

    private byte[] createCentralFileHeader(ZipArchiveEntry zipArchiveEntry, ByteBuffer byteBuffer, long j2, boolean z2) throws IOException {
        byte[] centralDirectoryExtra = zipArchiveEntry.getCentralDirectoryExtra();
        String comment = zipArchiveEntry.getComment();
        if (comment == null) {
            comment = "";
        }
        ByteBuffer byteBufferEncode = getEntryEncoding(zipArchiveEntry).encode(comment);
        int iLimit = byteBuffer.limit() - byteBuffer.position();
        int iLimit2 = byteBufferEncode.limit() - byteBufferEncode.position();
        int i2 = iLimit + 46;
        byte[] bArr = new byte[centralDirectoryExtra.length + i2 + iLimit2];
        System.arraycopy(CFH_SIG, 0, bArr, 0, 4);
        ZipShort.putShort((zipArchiveEntry.getPlatform() << 8) | (!this.hasUsedZip64 ? 20 : 45), bArr, 4);
        int method = zipArchiveEntry.getMethod();
        boolean zCanEncode = this.zipEncoding.canEncode(zipArchiveEntry.getName());
        ZipShort.putShort(versionNeededToExtract(method, z2), bArr, 6);
        getGeneralPurposeBits(method, !zCanEncode && this.fallbackToUTF8).encode(bArr, 8);
        ZipShort.putShort(method, bArr, 10);
        ZipUtil.toDosTime(this.calendarInstance, zipArchiveEntry.getTime(), bArr, 12);
        ZipLong.putLong(zipArchiveEntry.getCrc(), bArr, 16);
        if (zipArchiveEntry.getCompressedSize() < InternalZipConstants.ZIP_64_LIMIT && zipArchiveEntry.getSize() < InternalZipConstants.ZIP_64_LIMIT && this.zip64Mode != Zip64Mode.Always) {
            ZipLong.putLong(zipArchiveEntry.getCompressedSize(), bArr, 20);
            ZipLong.putLong(zipArchiveEntry.getSize(), bArr, 24);
        } else {
            ZipLong zipLong = ZipLong.ZIP64_MAGIC;
            zipLong.putLong(bArr, 20);
            zipLong.putLong(bArr, 24);
        }
        ZipShort.putShort(iLimit, bArr, 28);
        ZipShort.putShort(centralDirectoryExtra.length, bArr, 30);
        ZipShort.putShort(iLimit2, bArr, 32);
        System.arraycopy(ZERO, 0, bArr, 34, 2);
        ZipShort.putShort(zipArchiveEntry.getInternalAttributes(), bArr, 36);
        ZipLong.putLong(zipArchiveEntry.getExternalAttributes(), bArr, 38);
        if (j2 < InternalZipConstants.ZIP_64_LIMIT && this.zip64Mode != Zip64Mode.Always) {
            ZipLong.putLong(Math.min(j2, InternalZipConstants.ZIP_64_LIMIT), bArr, 42);
        } else {
            ZipLong.putLong(InternalZipConstants.ZIP_64_LIMIT, bArr, 42);
        }
        System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset(), bArr, 46, iLimit);
        System.arraycopy(centralDirectoryExtra, 0, bArr, i2, centralDirectoryExtra.length);
        System.arraycopy(byteBufferEncode.array(), byteBufferEncode.arrayOffset(), bArr, i2 + centralDirectoryExtra.length, iLimit2);
        return bArr;
    }

    public ZipArchiveOutputStream(File file) throws IOException {
        RandomAccessFile randomAccessFile;
        FileOutputStream fileOutputStream;
        this.finished = false;
        this.comment = "";
        this.level = -1;
        this.hasCompressionLevelChanged = false;
        this.method = 8;
        this.entries = new LinkedList();
        this.cdOffset = 0L;
        this.cdLength = 0L;
        this.offsets = new HashMap();
        this.encoding = "UTF8";
        this.zipEncoding = ZipEncodingHelper.getZipEncoding("UTF8");
        this.useUTF8Flag = true;
        this.fallbackToUTF8 = false;
        this.createUnicodeExtraFields = UnicodeExtraFieldPolicy.NEVER;
        this.hasUsedZip64 = false;
        this.zip64Mode = Zip64Mode.AsNeeded;
        this.copyBuffer = new byte[32768];
        this.calendarInstance = Calendar.getInstance();
        RandomAccessFile randomAccessFile2 = null;
        try {
            randomAccessFile = new RandomAccessFile(file, InternalZipConstants.WRITE_MODE);
            try {
                randomAccessFile.setLength(0L);
                fileOutputStream = null;
                randomAccessFile2 = randomAccessFile;
            } catch (IOException unused) {
                IOUtils.closeQuietly(randomAccessFile);
                fileOutputStream = new FileOutputStream(file);
                Deflater deflater = new Deflater(this.level, true);
                this.def = deflater;
                this.streamCompressor = StreamCompressor.create(randomAccessFile2, deflater);
                this.out = fileOutputStream;
                this.raf = randomAccessFile2;
            }
        } catch (IOException unused2) {
            randomAccessFile = null;
        }
        Deflater deflater2 = new Deflater(this.level, true);
        this.def = deflater2;
        this.streamCompressor = StreamCompressor.create(randomAccessFile2, deflater2);
        this.out = fileOutputStream;
        this.raf = randomAccessFile2;
    }
}
