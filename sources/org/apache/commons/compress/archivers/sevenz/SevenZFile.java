package org.apache.commons.compress.archivers.sevenz;

import com.google.common.base.Ascii;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.zip.CRC32;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.commons.compress.utils.BoundedInputStream;
import org.apache.commons.compress.utils.CRC32VerifyingInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.bouncycastle.crypto.signers.PSSSigner;

/* loaded from: classes9.dex */
public class SevenZFile implements Closeable {
    static final int SIGNATURE_HEADER_SIZE = 32;
    static final byte[] sevenZSignature = {TarConstants.LF_CONTIG, 122, PSSSigner.TRAILER_IMPLICIT, -81, 39, Ascii.FS};
    private final Archive archive;
    private int currentEntryIndex;
    private int currentFolderIndex;
    private InputStream currentFolderInputStream;
    private final ArrayList<InputStream> deferredBlockStreams;
    private RandomAccessFile file;
    private final String fileName;
    private byte[] password;

    public SevenZFile(File file, byte[] bArr) throws IOException {
        this.currentEntryIndex = -1;
        this.currentFolderIndex = -1;
        this.currentFolderInputStream = null;
        this.deferredBlockStreams = new ArrayList<>();
        this.file = new RandomAccessFile(file, "r");
        this.fileName = file.getAbsolutePath();
        try {
            this.archive = readHeaders(bArr);
            if (bArr == null) {
                this.password = null;
                return;
            }
            byte[] bArr2 = new byte[bArr.length];
            this.password = bArr2;
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        } catch (Throwable th) {
            this.file.close();
            throw th;
        }
    }

    private InputStream buildDecoderStack(Folder folder, long j2, int i2, SevenZArchiveEntry sevenZArchiveEntry) throws IOException {
        this.file.seek(j2);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new BoundedRandomAccessFileInputStream(this.file, this.archive.packSizes[i2]));
        LinkedList linkedList = new LinkedList();
        InputStream inputStreamAddDecoder = bufferedInputStream;
        for (Coder coder : folder.getOrderedCoders()) {
            if (coder.numInStreams != 1 || coder.numOutStreams != 1) {
                throw new IOException("Multi input/output stream coders are not yet supported");
            }
            SevenZMethod sevenZMethodById = SevenZMethod.byId(coder.decompressionMethodId);
            inputStreamAddDecoder = Coders.addDecoder(this.fileName, inputStreamAddDecoder, folder.getUnpackSizeForCoder(coder), coder, this.password);
            linkedList.addFirst(new SevenZMethodConfiguration(sevenZMethodById, Coders.findByMethod(sevenZMethodById).getOptionsFromCoder(coder, inputStreamAddDecoder)));
        }
        sevenZArchiveEntry.setContentMethods(linkedList);
        return folder.hasCrc ? new CRC32VerifyingInputStream(inputStreamAddDecoder, folder.getUnpackSize(), folder.crc) : inputStreamAddDecoder;
    }

    private void buildDecodingStream() throws IOException {
        Archive archive = this.archive;
        int[] iArr = archive.streamMap.fileFolderIndex;
        int i2 = this.currentEntryIndex;
        int i3 = iArr[i2];
        if (i3 < 0) {
            this.deferredBlockStreams.clear();
            return;
        }
        SevenZArchiveEntry[] sevenZArchiveEntryArr = archive.files;
        SevenZArchiveEntry sevenZArchiveEntry = sevenZArchiveEntryArr[i2];
        if (this.currentFolderIndex == i3) {
            sevenZArchiveEntry.setContentMethods(sevenZArchiveEntryArr[i2 - 1].getContentMethods());
        } else {
            this.currentFolderIndex = i3;
            this.deferredBlockStreams.clear();
            InputStream inputStream = this.currentFolderInputStream;
            if (inputStream != null) {
                inputStream.close();
                this.currentFolderInputStream = null;
            }
            Archive archive2 = this.archive;
            Folder folder = archive2.folders[i3];
            StreamMap streamMap = archive2.streamMap;
            int i4 = streamMap.folderFirstPackStreamIndex[i3];
            this.currentFolderInputStream = buildDecoderStack(folder, streamMap.packStreamOffsets[i4] + archive2.packPos + 32, i4, sevenZArchiveEntry);
        }
        InputStream boundedInputStream = new BoundedInputStream(this.currentFolderInputStream, sevenZArchiveEntry.getSize());
        if (sevenZArchiveEntry.getHasCrc()) {
            boundedInputStream = new CRC32VerifyingInputStream(boundedInputStream, sevenZArchiveEntry.getSize(), sevenZArchiveEntry.getCrcValue());
        }
        this.deferredBlockStreams.add(boundedInputStream);
    }

    private void calculateStreamMap(Archive archive) throws IOException {
        Folder[] folderArr;
        StreamMap streamMap = new StreamMap();
        Folder[] folderArr2 = archive.folders;
        int length = folderArr2 != null ? folderArr2.length : 0;
        streamMap.folderFirstPackStreamIndex = new int[length];
        int length2 = 0;
        for (int i2 = 0; i2 < length; i2++) {
            streamMap.folderFirstPackStreamIndex[i2] = length2;
            length2 += archive.folders[i2].packedStreams.length;
        }
        long[] jArr = archive.packSizes;
        int length3 = jArr != null ? jArr.length : 0;
        streamMap.packStreamOffsets = new long[length3];
        long j2 = 0;
        for (int i3 = 0; i3 < length3; i3++) {
            streamMap.packStreamOffsets[i3] = j2;
            j2 += archive.packSizes[i3];
        }
        streamMap.folderFirstFileIndex = new int[length];
        streamMap.fileFolderIndex = new int[archive.files.length];
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            SevenZArchiveEntry[] sevenZArchiveEntryArr = archive.files;
            if (i4 >= sevenZArchiveEntryArr.length) {
                archive.streamMap = streamMap;
                return;
            }
            if (sevenZArchiveEntryArr[i4].hasStream() || i5 != 0) {
                if (i5 == 0) {
                    while (true) {
                        folderArr = archive.folders;
                        if (i6 >= folderArr.length) {
                            break;
                        }
                        streamMap.folderFirstFileIndex[i6] = i4;
                        if (folderArr[i6].numUnpackSubStreams > 0) {
                            break;
                        } else {
                            i6++;
                        }
                    }
                    if (i6 >= folderArr.length) {
                        throw new IOException("Too few folders in archive");
                    }
                }
                streamMap.fileFolderIndex[i4] = i6;
                if (archive.files[i4].hasStream() && (i5 = i5 + 1) >= archive.folders[i6].numUnpackSubStreams) {
                    i6++;
                    i5 = 0;
                }
            } else {
                streamMap.fileFolderIndex[i4] = -1;
            }
            i4++;
        }
    }

    private InputStream getCurrentStream() throws IOException {
        if (this.archive.files[this.currentEntryIndex].getSize() == 0) {
            return new ByteArrayInputStream(new byte[0]);
        }
        if (this.deferredBlockStreams.isEmpty()) {
            throw new IllegalStateException("No current 7z entry (call getNextEntry() first).");
        }
        while (this.deferredBlockStreams.size() > 1) {
            InputStream inputStreamRemove = this.deferredBlockStreams.remove(0);
            IOUtils.skip(inputStreamRemove, Long.MAX_VALUE);
            inputStreamRemove.close();
        }
        return this.deferredBlockStreams.get(0);
    }

    public static boolean matches(byte[] bArr, int i2) {
        if (i2 < sevenZSignature.length) {
            return false;
        }
        int i3 = 0;
        while (true) {
            byte[] bArr2 = sevenZSignature;
            if (i3 >= bArr2.length) {
                return true;
            }
            if (bArr[i3] != bArr2[i3]) {
                return false;
            }
            i3++;
        }
    }

    private BitSet readAllOrBits(DataInput dataInput, int i2) throws IOException {
        if (dataInput.readUnsignedByte() == 0) {
            return readBits(dataInput, i2);
        }
        BitSet bitSet = new BitSet(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            bitSet.set(i3, true);
        }
        return bitSet;
    }

    private void readArchiveProperties(DataInput dataInput) throws IOException {
        int unsignedByte = dataInput.readUnsignedByte();
        while (unsignedByte != 0) {
            dataInput.readFully(new byte[(int) readUint64(dataInput)]);
            unsignedByte = dataInput.readUnsignedByte();
        }
    }

    private BitSet readBits(DataInput dataInput, int i2) throws IOException {
        BitSet bitSet = new BitSet(i2);
        int i3 = 0;
        int unsignedByte = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            if (i3 == 0) {
                unsignedByte = dataInput.readUnsignedByte();
                i3 = 128;
            }
            bitSet.set(i4, (unsignedByte & i3) != 0);
            i3 >>>= 1;
        }
        return bitSet;
    }

    private DataInputStream readEncodedHeader(DataInputStream dataInputStream, Archive archive, byte[] bArr) throws IOException {
        readStreamsInfo(dataInputStream, archive);
        Folder folder = archive.folders[0];
        this.file.seek(archive.packPos + 32 + 0);
        BoundedRandomAccessFileInputStream boundedRandomAccessFileInputStream = new BoundedRandomAccessFileInputStream(this.file, archive.packSizes[0]);
        InputStream cRC32VerifyingInputStream = boundedRandomAccessFileInputStream;
        for (Coder coder : folder.getOrderedCoders()) {
            if (coder.numInStreams != 1 || coder.numOutStreams != 1) {
                throw new IOException("Multi input/output stream coders are not yet supported");
            }
            cRC32VerifyingInputStream = Coders.addDecoder(this.fileName, cRC32VerifyingInputStream, folder.getUnpackSizeForCoder(coder), coder, bArr);
        }
        if (folder.hasCrc) {
            cRC32VerifyingInputStream = new CRC32VerifyingInputStream(cRC32VerifyingInputStream, folder.getUnpackSize(), folder.crc);
        }
        byte[] bArr2 = new byte[(int) folder.getUnpackSize()];
        DataInputStream dataInputStream2 = new DataInputStream(cRC32VerifyingInputStream);
        try {
            dataInputStream2.readFully(bArr2);
            dataInputStream2.close();
            return new DataInputStream(new ByteArrayInputStream(bArr2));
        } catch (Throwable th) {
            dataInputStream2.close();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x01ee, code lost:
    
        throw new java.io.IOException("Error parsing file names");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void readFilesInfo(java.io.DataInput r17, org.apache.commons.compress.archivers.sevenz.Archive r18) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 612
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.archivers.sevenz.SevenZFile.readFilesInfo(java.io.DataInput, org.apache.commons.compress.archivers.sevenz.Archive):void");
    }

    private Folder readFolder(DataInput dataInput) throws IOException {
        int i2;
        Folder folder = new Folder();
        int uint64 = (int) readUint64(dataInput);
        Coder[] coderArr = new Coder[uint64];
        long j2 = 0;
        long j3 = 0;
        for (int i3 = 0; i3 < uint64; i3++) {
            coderArr[i3] = new Coder();
            int unsignedByte = dataInput.readUnsignedByte();
            int i4 = unsignedByte & 15;
            boolean z2 = (unsignedByte & 16) == 0;
            boolean z3 = (unsignedByte & 32) != 0;
            boolean z4 = (unsignedByte & 128) != 0;
            byte[] bArr = new byte[i4];
            coderArr[i3].decompressionMethodId = bArr;
            dataInput.readFully(bArr);
            if (z2) {
                Coder coder = coderArr[i3];
                coder.numInStreams = 1L;
                coder.numOutStreams = 1L;
            } else {
                coderArr[i3].numInStreams = readUint64(dataInput);
                coderArr[i3].numOutStreams = readUint64(dataInput);
            }
            Coder coder2 = coderArr[i3];
            j2 += coder2.numInStreams;
            j3 += coder2.numOutStreams;
            if (z3) {
                byte[] bArr2 = new byte[(int) readUint64(dataInput)];
                coderArr[i3].properties = bArr2;
                dataInput.readFully(bArr2);
            }
            if (z4) {
                throw new IOException("Alternative methods are unsupported, please report. The reference implementation doesn't support them either.");
            }
        }
        folder.coders = coderArr;
        folder.totalInputStreams = j2;
        folder.totalOutputStreams = j3;
        if (j3 == 0) {
            throw new IOException("Total output streams can't be 0");
        }
        long j4 = j3 - 1;
        int i5 = (int) j4;
        BindPair[] bindPairArr = new BindPair[i5];
        for (int i6 = 0; i6 < i5; i6++) {
            BindPair bindPair = new BindPair();
            bindPairArr[i6] = bindPair;
            bindPair.inIndex = readUint64(dataInput);
            bindPairArr[i6].outIndex = readUint64(dataInput);
        }
        folder.bindPairs = bindPairArr;
        if (j2 < j4) {
            throw new IOException("Total input streams can't be less than the number of bind pairs");
        }
        long j5 = j2 - j4;
        int i7 = (int) j5;
        long[] jArr = new long[i7];
        if (j5 == 1) {
            int i8 = 0;
            while (true) {
                i2 = (int) j2;
                if (i8 >= i2 || folder.findBindPairForInStream(i8) < 0) {
                    break;
                }
                i8++;
            }
            if (i8 == i2) {
                throw new IOException("Couldn't find stream's bind pair index");
            }
            jArr[0] = i8;
        } else {
            for (int i9 = 0; i9 < i7; i9++) {
                jArr[i9] = readUint64(dataInput);
            }
        }
        folder.packedStreams = jArr;
        return folder;
    }

    private void readHeader(DataInput dataInput, Archive archive) throws IOException {
        int unsignedByte = dataInput.readUnsignedByte();
        if (unsignedByte == 2) {
            readArchiveProperties(dataInput);
            unsignedByte = dataInput.readUnsignedByte();
        }
        if (unsignedByte == 3) {
            throw new IOException("Additional streams unsupported");
        }
        if (unsignedByte == 4) {
            readStreamsInfo(dataInput, archive);
            unsignedByte = dataInput.readUnsignedByte();
        }
        if (unsignedByte == 5) {
            readFilesInfo(dataInput, archive);
            unsignedByte = dataInput.readUnsignedByte();
        }
        if (unsignedByte == 0) {
            return;
        }
        throw new IOException("Badly terminated header, found " + unsignedByte);
    }

    private Archive readHeaders(byte[] bArr) throws Throwable {
        byte[] bArr2 = new byte[6];
        this.file.readFully(bArr2);
        if (!Arrays.equals(bArr2, sevenZSignature)) {
            throw new IOException("Bad 7z signature");
        }
        byte b3 = this.file.readByte();
        byte b4 = this.file.readByte();
        if (b3 != 0) {
            throw new IOException(String.format("Unsupported 7z version (%d,%d)", Byte.valueOf(b3), Byte.valueOf(b4)));
        }
        StartHeader startHeader = readStartHeader(Integer.reverseBytes(this.file.readInt()) & InternalZipConstants.ZIP_64_LIMIT);
        long j2 = startHeader.nextHeaderSize;
        int i2 = (int) j2;
        if (i2 != j2) {
            throw new IOException("cannot handle nextHeaderSize " + startHeader.nextHeaderSize);
        }
        this.file.seek(startHeader.nextHeaderOffset + 32);
        byte[] bArr3 = new byte[i2];
        this.file.readFully(bArr3);
        CRC32 crc32 = new CRC32();
        crc32.update(bArr3);
        if (startHeader.nextHeaderCrc != crc32.getValue()) {
            throw new IOException("NextHeader CRC mismatch");
        }
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr3));
        Archive archive = new Archive();
        int unsignedByte = dataInputStream.readUnsignedByte();
        if (unsignedByte == 23) {
            dataInputStream = readEncodedHeader(dataInputStream, archive, bArr);
            archive = new Archive();
            unsignedByte = dataInputStream.readUnsignedByte();
        }
        if (unsignedByte != 1) {
            throw new IOException("Broken or unsupported archive: no Header");
        }
        readHeader(dataInputStream, archive);
        dataInputStream.close();
        return archive;
    }

    private void readPackInfo(DataInput dataInput, Archive archive) throws IOException {
        archive.packPos = readUint64(dataInput);
        long uint64 = readUint64(dataInput);
        int unsignedByte = dataInput.readUnsignedByte();
        if (unsignedByte == 9) {
            archive.packSizes = new long[(int) uint64];
            int i2 = 0;
            while (true) {
                long[] jArr = archive.packSizes;
                if (i2 >= jArr.length) {
                    break;
                }
                jArr[i2] = readUint64(dataInput);
                i2++;
            }
            unsignedByte = dataInput.readUnsignedByte();
        }
        if (unsignedByte == 10) {
            int i3 = (int) uint64;
            archive.packCrcsDefined = readAllOrBits(dataInput, i3);
            archive.packCrcs = new long[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                if (archive.packCrcsDefined.get(i4)) {
                    archive.packCrcs[i4] = Integer.reverseBytes(dataInput.readInt()) & InternalZipConstants.ZIP_64_LIMIT;
                }
            }
            unsignedByte = dataInput.readUnsignedByte();
        }
        if (unsignedByte == 0) {
            return;
        }
        throw new IOException("Badly terminated PackInfo (" + unsignedByte + ")");
    }

    private StartHeader readStartHeader(long j2) throws Throwable {
        DataInputStream dataInputStream;
        StartHeader startHeader = new StartHeader();
        DataInputStream dataInputStream2 = null;
        try {
            dataInputStream = new DataInputStream(new CRC32VerifyingInputStream(new BoundedRandomAccessFileInputStream(this.file, 20L), 20L, j2));
        } catch (Throwable th) {
            th = th;
        }
        try {
            startHeader.nextHeaderOffset = Long.reverseBytes(dataInputStream.readLong());
            startHeader.nextHeaderSize = Long.reverseBytes(dataInputStream.readLong());
            startHeader.nextHeaderCrc = Integer.reverseBytes(dataInputStream.readInt()) & InternalZipConstants.ZIP_64_LIMIT;
            dataInputStream.close();
            return startHeader;
        } catch (Throwable th2) {
            th = th2;
            dataInputStream2 = dataInputStream;
            if (dataInputStream2 != null) {
                dataInputStream2.close();
            }
            throw th;
        }
    }

    private void readStreamsInfo(DataInput dataInput, Archive archive) throws IOException {
        int unsignedByte = dataInput.readUnsignedByte();
        if (unsignedByte == 6) {
            readPackInfo(dataInput, archive);
            unsignedByte = dataInput.readUnsignedByte();
        }
        if (unsignedByte == 7) {
            readUnpackInfo(dataInput, archive);
            unsignedByte = dataInput.readUnsignedByte();
        } else {
            archive.folders = new Folder[0];
        }
        if (unsignedByte == 8) {
            readSubStreamsInfo(dataInput, archive);
            unsignedByte = dataInput.readUnsignedByte();
        }
        if (unsignedByte != 0) {
            throw new IOException("Badly terminated StreamsInfo");
        }
    }

    private void readSubStreamsInfo(DataInput dataInput, Archive archive) throws IOException {
        boolean z2;
        Folder[] folderArr = archive.folders;
        int length = folderArr.length;
        int i2 = 0;
        while (true) {
            z2 = true;
            if (i2 >= length) {
                break;
            }
            folderArr[i2].numUnpackSubStreams = 1;
            i2++;
        }
        int length2 = archive.folders.length;
        int unsignedByte = dataInput.readUnsignedByte();
        if (unsignedByte == 13) {
            int i3 = 0;
            for (Folder folder : archive.folders) {
                long uint64 = readUint64(dataInput);
                folder.numUnpackSubStreams = (int) uint64;
                i3 = (int) (i3 + uint64);
            }
            unsignedByte = dataInput.readUnsignedByte();
            length2 = i3;
        }
        SubStreamsInfo subStreamsInfo = new SubStreamsInfo();
        subStreamsInfo.unpackSizes = new long[length2];
        subStreamsInfo.hasCrc = new BitSet(length2);
        subStreamsInfo.crcs = new long[length2];
        int i4 = 0;
        for (Folder folder2 : archive.folders) {
            if (folder2.numUnpackSubStreams != 0) {
                long j2 = 0;
                if (unsignedByte == 9) {
                    int i5 = 0;
                    while (i5 < folder2.numUnpackSubStreams - 1) {
                        long uint642 = readUint64(dataInput);
                        subStreamsInfo.unpackSizes[i4] = uint642;
                        j2 += uint642;
                        i5++;
                        i4++;
                    }
                }
                subStreamsInfo.unpackSizes[i4] = folder2.getUnpackSize() - j2;
                i4++;
            }
        }
        if (unsignedByte == 9) {
            unsignedByte = dataInput.readUnsignedByte();
        }
        int i6 = 0;
        for (Folder folder3 : archive.folders) {
            int i7 = folder3.numUnpackSubStreams;
            if (i7 != 1 || !folder3.hasCrc) {
                i6 += i7;
            }
        }
        if (unsignedByte == 10) {
            BitSet allOrBits = readAllOrBits(dataInput, i6);
            long[] jArr = new long[i6];
            for (int i8 = 0; i8 < i6; i8++) {
                if (allOrBits.get(i8)) {
                    jArr[i8] = Integer.reverseBytes(dataInput.readInt()) & InternalZipConstants.ZIP_64_LIMIT;
                }
            }
            Folder[] folderArr2 = archive.folders;
            int length3 = folderArr2.length;
            int i9 = 0;
            int i10 = 0;
            int i11 = 0;
            while (i9 < length3) {
                Folder folder4 = folderArr2[i9];
                if (folder4.numUnpackSubStreams == z2 && folder4.hasCrc) {
                    subStreamsInfo.hasCrc.set(i10, z2);
                    subStreamsInfo.crcs[i10] = folder4.crc;
                    i10++;
                } else {
                    for (int i12 = 0; i12 < folder4.numUnpackSubStreams; i12++) {
                        subStreamsInfo.hasCrc.set(i10, allOrBits.get(i11));
                        subStreamsInfo.crcs[i10] = jArr[i11];
                        i10++;
                        i11++;
                    }
                }
                i9++;
                z2 = true;
            }
            unsignedByte = dataInput.readUnsignedByte();
        }
        if (unsignedByte != 0) {
            throw new IOException("Badly terminated SubStreamsInfo");
        }
        archive.subStreamsInfo = subStreamsInfo;
    }

    private static long readUint64(DataInput dataInput) throws IOException {
        long unsignedByte = dataInput.readUnsignedByte();
        int i2 = 128;
        long unsignedByte2 = 0;
        for (int i3 = 0; i3 < 8; i3++) {
            if ((i2 & unsignedByte) == 0) {
                return ((unsignedByte & (i2 - 1)) << (i3 * 8)) | unsignedByte2;
            }
            unsignedByte2 |= dataInput.readUnsignedByte() << (i3 * 8);
            i2 >>>= 1;
        }
        return unsignedByte2;
    }

    private void readUnpackInfo(DataInput dataInput, Archive archive) throws IOException {
        int unsignedByte = dataInput.readUnsignedByte();
        if (unsignedByte != 11) {
            throw new IOException("Expected kFolder, got " + unsignedByte);
        }
        int uint64 = (int) readUint64(dataInput);
        Folder[] folderArr = new Folder[uint64];
        archive.folders = folderArr;
        if (dataInput.readUnsignedByte() != 0) {
            throw new IOException("External unsupported");
        }
        for (int i2 = 0; i2 < uint64; i2++) {
            folderArr[i2] = readFolder(dataInput);
        }
        int unsignedByte2 = dataInput.readUnsignedByte();
        if (unsignedByte2 != 12) {
            throw new IOException("Expected kCodersUnpackSize, got " + unsignedByte2);
        }
        for (int i3 = 0; i3 < uint64; i3++) {
            Folder folder = folderArr[i3];
            folder.unpackSizes = new long[(int) folder.totalOutputStreams];
            for (int i4 = 0; i4 < folder.totalOutputStreams; i4++) {
                folder.unpackSizes[i4] = readUint64(dataInput);
            }
        }
        int unsignedByte3 = dataInput.readUnsignedByte();
        if (unsignedByte3 == 10) {
            BitSet allOrBits = readAllOrBits(dataInput, uint64);
            for (int i5 = 0; i5 < uint64; i5++) {
                if (allOrBits.get(i5)) {
                    Folder folder2 = folderArr[i5];
                    folder2.hasCrc = true;
                    folder2.crc = Integer.reverseBytes(dataInput.readInt()) & InternalZipConstants.ZIP_64_LIMIT;
                } else {
                    folderArr[i5].hasCrc = false;
                }
            }
            unsignedByte3 = dataInput.readUnsignedByte();
        }
        if (unsignedByte3 != 0) {
            throw new IOException("Badly terminated UnpackInfo");
        }
    }

    private static long skipBytesFully(DataInput dataInput, long j2) throws IOException {
        if (j2 < 1) {
            return 0L;
        }
        long j3 = 0;
        while (j2 > 2147483647L) {
            long jSkipBytesFully = skipBytesFully(dataInput, 2147483647L);
            if (jSkipBytesFully == 0) {
                return j3;
            }
            j3 += jSkipBytesFully;
            j2 -= jSkipBytesFully;
        }
        while (j2 > 0) {
            int iSkipBytes = dataInput.skipBytes((int) j2);
            if (iSkipBytes == 0) {
                return j3;
            }
            long j4 = iSkipBytes;
            j3 += j4;
            j2 -= j4;
        }
        return j3;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        RandomAccessFile randomAccessFile = this.file;
        if (randomAccessFile != null) {
            try {
                randomAccessFile.close();
            } finally {
                this.file = null;
                byte[] bArr = this.password;
                if (bArr != null) {
                    Arrays.fill(bArr, (byte) 0);
                }
                this.password = null;
            }
        }
    }

    public Iterable<SevenZArchiveEntry> getEntries() {
        return Arrays.asList(this.archive.files);
    }

    public SevenZArchiveEntry getNextEntry() throws IOException {
        int i2 = this.currentEntryIndex;
        SevenZArchiveEntry[] sevenZArchiveEntryArr = this.archive.files;
        if (i2 >= sevenZArchiveEntryArr.length - 1) {
            return null;
        }
        int i3 = i2 + 1;
        this.currentEntryIndex = i3;
        SevenZArchiveEntry sevenZArchiveEntry = sevenZArchiveEntryArr[i3];
        buildDecodingStream();
        return sevenZArchiveEntry;
    }

    public int read() throws IOException {
        return getCurrentStream().read();
    }

    public String toString() {
        return this.archive.toString();
    }

    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    public int read(byte[] bArr, int i2, int i3) throws IOException {
        return getCurrentStream().read(bArr, i2, i3);
    }

    public SevenZFile(File file) throws IOException {
        this(file, null);
    }
}
