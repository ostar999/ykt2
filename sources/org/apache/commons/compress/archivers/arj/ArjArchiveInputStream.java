package org.apache.commons.compress.archivers.arj;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.CRC32;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.utils.BoundedInputStream;
import org.apache.commons.compress.utils.CRC32VerifyingInputStream;
import org.apache.commons.compress.utils.IOUtils;

/* loaded from: classes9.dex */
public class ArjArchiveInputStream extends ArchiveInputStream {
    private static final int ARJ_MAGIC_1 = 96;
    private static final int ARJ_MAGIC_2 = 234;
    private final String charsetName;
    private InputStream currentInputStream;
    private LocalFileHeader currentLocalFileHeader;
    private final DataInputStream in;
    private final MainHeader mainHeader;

    public ArjArchiveInputStream(InputStream inputStream, String str) throws ArchiveException {
        this.currentLocalFileHeader = null;
        this.currentInputStream = null;
        this.in = new DataInputStream(inputStream);
        this.charsetName = str;
        try {
            MainHeader mainHeader = readMainHeader();
            this.mainHeader = mainHeader;
            int i2 = mainHeader.arjFlags;
            if ((i2 & 1) != 0) {
                throw new ArchiveException("Encrypted ARJ files are unsupported");
            }
            if ((i2 & 4) != 0) {
                throw new ArchiveException("Multi-volume ARJ files are unsupported");
            }
        } catch (IOException e2) {
            throw new ArchiveException(e2.getMessage(), e2);
        }
    }

    public static boolean matches(byte[] bArr, int i2) {
        return i2 >= 2 && (bArr[0] & 255) == 96 && (bArr[1] & 255) == 234;
    }

    private int read16(DataInputStream dataInputStream) throws IOException {
        int unsignedShort = dataInputStream.readUnsignedShort();
        count(2);
        return Integer.reverseBytes(unsignedShort) >>> 16;
    }

    private int read32(DataInputStream dataInputStream) throws IOException {
        int i2 = dataInputStream.readInt();
        count(4);
        return Integer.reverseBytes(i2);
    }

    private int read8(DataInputStream dataInputStream) throws IOException {
        int unsignedByte = dataInputStream.readUnsignedByte();
        count(1);
        return unsignedByte;
    }

    private void readExtraData(int i2, DataInputStream dataInputStream, LocalFileHeader localFileHeader) throws IOException {
        if (i2 >= 33) {
            localFileHeader.extendedFilePosition = read32(dataInputStream);
            if (i2 >= 45) {
                localFileHeader.dateTimeAccessed = read32(dataInputStream);
                localFileHeader.dateTimeCreated = read32(dataInputStream);
                localFileHeader.originalSizeEvenForVolumes = read32(dataInputStream);
                pushedBackBytes(12L);
            }
            pushedBackBytes(4L);
        }
    }

    private void readFully(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        dataInputStream.readFully(bArr);
        count(bArr.length);
    }

    private byte[] readHeader() throws IOException {
        boolean z2 = false;
        byte[] bArr = null;
        do {
            int i2 = read8(this.in);
            while (true) {
                int i3 = read8(this.in);
                if (i2 == 96 || i3 == 234) {
                    break;
                }
                i2 = i3;
            }
            int i4 = read16(this.in);
            if (i4 == 0) {
                return null;
            }
            if (i4 <= 2600) {
                bArr = new byte[i4];
                readFully(this.in, bArr);
                long j2 = read32(this.in) & InternalZipConstants.ZIP_64_LIMIT;
                CRC32 crc32 = new CRC32();
                crc32.update(bArr);
                if (j2 == crc32.getValue()) {
                    z2 = true;
                }
            }
        } while (!z2);
        return bArr;
    }

    private LocalFileHeader readLocalFileHeader() throws IOException {
        byte[] header = readHeader();
        if (header == null) {
            return null;
        }
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(header));
        int unsignedByte = dataInputStream.readUnsignedByte();
        byte[] bArr = new byte[unsignedByte - 1];
        dataInputStream.readFully(bArr);
        DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
        LocalFileHeader localFileHeader = new LocalFileHeader();
        localFileHeader.archiverVersionNumber = dataInputStream2.readUnsignedByte();
        localFileHeader.minVersionToExtract = dataInputStream2.readUnsignedByte();
        localFileHeader.hostOS = dataInputStream2.readUnsignedByte();
        localFileHeader.arjFlags = dataInputStream2.readUnsignedByte();
        localFileHeader.method = dataInputStream2.readUnsignedByte();
        localFileHeader.fileType = dataInputStream2.readUnsignedByte();
        localFileHeader.reserved = dataInputStream2.readUnsignedByte();
        localFileHeader.dateTimeModified = read32(dataInputStream2);
        localFileHeader.compressedSize = read32(dataInputStream2) & InternalZipConstants.ZIP_64_LIMIT;
        localFileHeader.originalSize = read32(dataInputStream2) & InternalZipConstants.ZIP_64_LIMIT;
        localFileHeader.originalCrc32 = read32(dataInputStream2) & InternalZipConstants.ZIP_64_LIMIT;
        localFileHeader.fileSpecPosition = read16(dataInputStream2);
        localFileHeader.fileAccessMode = read16(dataInputStream2);
        pushedBackBytes(20L);
        localFileHeader.firstChapter = dataInputStream2.readUnsignedByte();
        localFileHeader.lastChapter = dataInputStream2.readUnsignedByte();
        readExtraData(unsignedByte, dataInputStream2, localFileHeader);
        localFileHeader.name = readString(dataInputStream);
        localFileHeader.comment = readString(dataInputStream);
        ArrayList arrayList = new ArrayList();
        while (true) {
            int i2 = read16(this.in);
            if (i2 <= 0) {
                localFileHeader.extendedHeaders = (byte[][]) arrayList.toArray(new byte[arrayList.size()][]);
                return localFileHeader;
            }
            byte[] bArr2 = new byte[i2];
            readFully(this.in, bArr2);
            long j2 = read32(this.in) & InternalZipConstants.ZIP_64_LIMIT;
            CRC32 crc32 = new CRC32();
            crc32.update(bArr2);
            if (j2 != crc32.getValue()) {
                throw new IOException("Extended header CRC32 verification failure");
            }
            arrayList.add(bArr2);
        }
    }

    private MainHeader readMainHeader() throws IOException {
        byte[] header = readHeader();
        if (header == null) {
            throw new IOException("Archive ends without any headers");
        }
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(header));
        int unsignedByte = dataInputStream.readUnsignedByte();
        byte[] bArr = new byte[unsignedByte - 1];
        dataInputStream.readFully(bArr);
        DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
        MainHeader mainHeader = new MainHeader();
        mainHeader.archiverVersionNumber = dataInputStream2.readUnsignedByte();
        mainHeader.minVersionToExtract = dataInputStream2.readUnsignedByte();
        mainHeader.hostOS = dataInputStream2.readUnsignedByte();
        mainHeader.arjFlags = dataInputStream2.readUnsignedByte();
        mainHeader.securityVersion = dataInputStream2.readUnsignedByte();
        mainHeader.fileType = dataInputStream2.readUnsignedByte();
        mainHeader.reserved = dataInputStream2.readUnsignedByte();
        mainHeader.dateTimeCreated = read32(dataInputStream2);
        mainHeader.dateTimeModified = read32(dataInputStream2);
        mainHeader.archiveSize = read32(dataInputStream2) & InternalZipConstants.ZIP_64_LIMIT;
        mainHeader.securityEnvelopeFilePosition = read32(dataInputStream2);
        mainHeader.fileSpecPosition = read16(dataInputStream2);
        mainHeader.securityEnvelopeLength = read16(dataInputStream2);
        pushedBackBytes(20L);
        mainHeader.encryptionVersion = dataInputStream2.readUnsignedByte();
        mainHeader.lastChapter = dataInputStream2.readUnsignedByte();
        if (unsignedByte >= 33) {
            mainHeader.arjProtectionFactor = dataInputStream2.readUnsignedByte();
            mainHeader.arjFlags2 = dataInputStream2.readUnsignedByte();
            dataInputStream2.readUnsignedByte();
            dataInputStream2.readUnsignedByte();
        }
        mainHeader.name = readString(dataInputStream);
        mainHeader.comment = readString(dataInputStream);
        int i2 = read16(this.in);
        if (i2 > 0) {
            byte[] bArr2 = new byte[i2];
            mainHeader.extendedHeaderBytes = bArr2;
            readFully(this.in, bArr2);
            long j2 = read32(this.in) & InternalZipConstants.ZIP_64_LIMIT;
            CRC32 crc32 = new CRC32();
            crc32.update(mainHeader.extendedHeaderBytes);
            if (j2 != crc32.getValue()) {
                throw new IOException("Extended header CRC32 verification failure");
            }
        }
        return mainHeader;
    }

    private String readString(DataInputStream dataInputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int unsignedByte = dataInputStream.readUnsignedByte();
            if (unsignedByte == 0) {
                break;
            }
            byteArrayOutputStream.write(unsignedByte);
        }
        return this.charsetName != null ? new String(byteArrayOutputStream.toByteArray(), this.charsetName) : new String(byteArrayOutputStream.toByteArray());
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public boolean canReadEntryData(ArchiveEntry archiveEntry) {
        return (archiveEntry instanceof ArjArchiveEntry) && ((ArjArchiveEntry) archiveEntry).getMethod() == 0;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    public String getArchiveComment() {
        return this.mainHeader.comment;
    }

    public String getArchiveName() {
        return this.mainHeader.name;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        LocalFileHeader localFileHeader = this.currentLocalFileHeader;
        if (localFileHeader == null) {
            throw new IllegalStateException("No current arj entry");
        }
        if (localFileHeader.method == 0) {
            return this.currentInputStream.read(bArr, i2, i3);
        }
        throw new IOException("Unsupported compression method " + this.currentLocalFileHeader.method);
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public ArjArchiveEntry getNextEntry() throws IOException {
        InputStream inputStream = this.currentInputStream;
        if (inputStream != null) {
            IOUtils.skip(inputStream, Long.MAX_VALUE);
            this.currentInputStream.close();
            this.currentLocalFileHeader = null;
            this.currentInputStream = null;
        }
        LocalFileHeader localFileHeader = readLocalFileHeader();
        this.currentLocalFileHeader = localFileHeader;
        if (localFileHeader == null) {
            this.currentInputStream = null;
            return null;
        }
        BoundedInputStream boundedInputStream = new BoundedInputStream(this.in, localFileHeader.compressedSize);
        this.currentInputStream = boundedInputStream;
        LocalFileHeader localFileHeader2 = this.currentLocalFileHeader;
        if (localFileHeader2.method == 0) {
            this.currentInputStream = new CRC32VerifyingInputStream(boundedInputStream, localFileHeader2.originalSize, localFileHeader2.originalCrc32);
        }
        return new ArjArchiveEntry(this.currentLocalFileHeader);
    }

    public ArjArchiveInputStream(InputStream inputStream) throws ArchiveException {
        this(inputStream, "CP437");
    }
}
