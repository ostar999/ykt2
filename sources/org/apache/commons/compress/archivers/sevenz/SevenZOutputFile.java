package org.apache.commons.compress.archivers.sevenz;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.utils.CountingOutputStream;

/* loaded from: classes9.dex */
public class SevenZOutputFile implements Closeable {
    private CountingOutputStream[] additionalCountingStreams;
    private CountingOutputStream currentOutputStream;
    private final RandomAccessFile file;
    private final List<SevenZArchiveEntry> files = new ArrayList();
    private int numNonEmptyStreams = 0;
    private final CRC32 crc32 = new CRC32();
    private final CRC32 compressedCrc32 = new CRC32();
    private long fileBytesWritten = 0;
    private boolean finished = false;
    private Iterable<? extends SevenZMethodConfiguration> contentMethods = Collections.singletonList(new SevenZMethodConfiguration(SevenZMethod.LZMA2));
    private final Map<SevenZArchiveEntry, long[]> additionalSizes = new HashMap();

    public SevenZOutputFile(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, InternalZipConstants.WRITE_MODE);
        this.file = randomAccessFile;
        randomAccessFile.seek(32L);
    }

    public static /* synthetic */ long access$408(SevenZOutputFile sevenZOutputFile) {
        long j2 = sevenZOutputFile.fileBytesWritten;
        sevenZOutputFile.fileBytesWritten = 1 + j2;
        return j2;
    }

    public static /* synthetic */ long access$414(SevenZOutputFile sevenZOutputFile, long j2) {
        long j3 = sevenZOutputFile.fileBytesWritten + j2;
        sevenZOutputFile.fileBytesWritten = j3;
        return j3;
    }

    private Iterable<? extends SevenZMethodConfiguration> getContentMethods(SevenZArchiveEntry sevenZArchiveEntry) {
        Iterable<? extends SevenZMethodConfiguration> contentMethods = sevenZArchiveEntry.getContentMethods();
        return contentMethods == null ? this.contentMethods : contentMethods;
    }

    private OutputStream getCurrentOutputStream() throws IOException {
        if (this.currentOutputStream == null) {
            this.currentOutputStream = setupFileOutputStream();
        }
        return this.currentOutputStream;
    }

    private static <T> Iterable<T> reverse(Iterable<T> iterable) {
        LinkedList linkedList = new LinkedList();
        Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            linkedList.addFirst(it.next());
        }
        return linkedList;
    }

    private CountingOutputStream setupFileOutputStream() throws IOException {
        if (this.files.isEmpty()) {
            throw new IllegalStateException("No current 7z entry");
        }
        OutputStream outputStreamWrapper = new OutputStreamWrapper();
        ArrayList arrayList = new ArrayList();
        List<SevenZArchiveEntry> list = this.files;
        boolean z2 = true;
        for (SevenZMethodConfiguration sevenZMethodConfiguration : getContentMethods(list.get(list.size() - 1))) {
            if (!z2) {
                CountingOutputStream countingOutputStream = new CountingOutputStream(outputStreamWrapper);
                arrayList.add(countingOutputStream);
                outputStreamWrapper = countingOutputStream;
            }
            outputStreamWrapper = Coders.addEncoder(outputStreamWrapper, sevenZMethodConfiguration.getMethod(), sevenZMethodConfiguration.getOptions());
            z2 = false;
        }
        if (!arrayList.isEmpty()) {
            this.additionalCountingStreams = (CountingOutputStream[]) arrayList.toArray(new CountingOutputStream[arrayList.size()]);
        }
        return new CountingOutputStream(outputStreamWrapper) { // from class: org.apache.commons.compress.archivers.sevenz.SevenZOutputFile.1
            @Override // org.apache.commons.compress.utils.CountingOutputStream, java.io.FilterOutputStream, java.io.OutputStream
            public void write(int i2) throws IOException {
                super.write(i2);
                SevenZOutputFile.this.crc32.update(i2);
            }

            @Override // org.apache.commons.compress.utils.CountingOutputStream, java.io.FilterOutputStream, java.io.OutputStream
            public void write(byte[] bArr) throws IOException {
                super.write(bArr);
                SevenZOutputFile.this.crc32.update(bArr);
            }

            @Override // org.apache.commons.compress.utils.CountingOutputStream, java.io.FilterOutputStream, java.io.OutputStream
            public void write(byte[] bArr, int i2, int i3) throws IOException {
                super.write(bArr, i2, i3);
                SevenZOutputFile.this.crc32.update(bArr, i2, i3);
            }
        };
    }

    private void writeBits(DataOutput dataOutput, BitSet bitSet, int i2) throws IOException {
        int i3 = 0;
        int i4 = 7;
        for (int i5 = 0; i5 < i2; i5++) {
            i3 |= (bitSet.get(i5) ? 1 : 0) << i4;
            i4--;
            if (i4 < 0) {
                dataOutput.write(i3);
                i3 = 0;
                i4 = 7;
            }
        }
        if (i4 != 7) {
            dataOutput.write(i3);
        }
    }

    private void writeFileATimes(DataOutput dataOutput) throws IOException {
        Iterator<SevenZArchiveEntry> it = this.files.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next().getHasAccessDate()) {
                i2++;
            }
        }
        if (i2 > 0) {
            dataOutput.write(19);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            if (i2 != this.files.size()) {
                dataOutputStream.write(0);
                BitSet bitSet = new BitSet(this.files.size());
                for (int i3 = 0; i3 < this.files.size(); i3++) {
                    bitSet.set(i3, this.files.get(i3).getHasAccessDate());
                }
                writeBits(dataOutputStream, bitSet, this.files.size());
            } else {
                dataOutputStream.write(1);
            }
            dataOutputStream.write(0);
            for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
                if (sevenZArchiveEntry.getHasAccessDate()) {
                    dataOutputStream.writeLong(Long.reverseBytes(SevenZArchiveEntry.javaTimeToNtfsTime(sevenZArchiveEntry.getAccessDate())));
                }
            }
            dataOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            writeUint64(dataOutput, byteArray.length);
            dataOutput.write(byteArray);
        }
    }

    private void writeFileAntiItems(DataOutput dataOutput) throws IOException {
        boolean z2 = false;
        BitSet bitSet = new BitSet(0);
        int i2 = 0;
        for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
            if (!sevenZArchiveEntry.hasStream()) {
                boolean zIsAntiItem = sevenZArchiveEntry.isAntiItem();
                bitSet.set(i2, zIsAntiItem);
                z2 |= zIsAntiItem;
                i2++;
            }
        }
        if (z2) {
            dataOutput.write(16);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            writeBits(dataOutputStream, bitSet, i2);
            dataOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            writeUint64(dataOutput, byteArray.length);
            dataOutput.write(byteArray);
        }
    }

    private void writeFileCTimes(DataOutput dataOutput) throws IOException {
        Iterator<SevenZArchiveEntry> it = this.files.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next().getHasCreationDate()) {
                i2++;
            }
        }
        if (i2 > 0) {
            dataOutput.write(18);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            if (i2 != this.files.size()) {
                dataOutputStream.write(0);
                BitSet bitSet = new BitSet(this.files.size());
                for (int i3 = 0; i3 < this.files.size(); i3++) {
                    bitSet.set(i3, this.files.get(i3).getHasCreationDate());
                }
                writeBits(dataOutputStream, bitSet, this.files.size());
            } else {
                dataOutputStream.write(1);
            }
            dataOutputStream.write(0);
            for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
                if (sevenZArchiveEntry.getHasCreationDate()) {
                    dataOutputStream.writeLong(Long.reverseBytes(SevenZArchiveEntry.javaTimeToNtfsTime(sevenZArchiveEntry.getCreationDate())));
                }
            }
            dataOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            writeUint64(dataOutput, byteArray.length);
            dataOutput.write(byteArray);
        }
    }

    private void writeFileEmptyFiles(DataOutput dataOutput) throws IOException {
        boolean z2 = false;
        BitSet bitSet = new BitSet(0);
        int i2 = 0;
        for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
            if (!sevenZArchiveEntry.hasStream()) {
                boolean zIsDirectory = sevenZArchiveEntry.isDirectory();
                bitSet.set(i2, !zIsDirectory);
                z2 |= !zIsDirectory;
                i2++;
            }
        }
        if (z2) {
            dataOutput.write(15);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            writeBits(dataOutputStream, bitSet, i2);
            dataOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            writeUint64(dataOutput, byteArray.length);
            dataOutput.write(byteArray);
        }
    }

    private void writeFileEmptyStreams(DataOutput dataOutput) throws IOException {
        int i2;
        boolean z2;
        Iterator<SevenZArchiveEntry> it = this.files.iterator();
        while (true) {
            if (it.hasNext()) {
                if (!it.next().hasStream()) {
                    z2 = true;
                    break;
                }
            } else {
                z2 = false;
                break;
            }
        }
        if (z2) {
            dataOutput.write(14);
            BitSet bitSet = new BitSet(this.files.size());
            for (i2 = 0; i2 < this.files.size(); i2++) {
                bitSet.set(i2, !this.files.get(i2).hasStream());
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            writeBits(dataOutputStream, bitSet, this.files.size());
            dataOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            writeUint64(dataOutput, byteArray.length);
            dataOutput.write(byteArray);
        }
    }

    private void writeFileMTimes(DataOutput dataOutput) throws IOException {
        Iterator<SevenZArchiveEntry> it = this.files.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next().getHasLastModifiedDate()) {
                i2++;
            }
        }
        if (i2 > 0) {
            dataOutput.write(20);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            if (i2 != this.files.size()) {
                dataOutputStream.write(0);
                BitSet bitSet = new BitSet(this.files.size());
                for (int i3 = 0; i3 < this.files.size(); i3++) {
                    bitSet.set(i3, this.files.get(i3).getHasLastModifiedDate());
                }
                writeBits(dataOutputStream, bitSet, this.files.size());
            } else {
                dataOutputStream.write(1);
            }
            dataOutputStream.write(0);
            for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
                if (sevenZArchiveEntry.getHasLastModifiedDate()) {
                    dataOutputStream.writeLong(Long.reverseBytes(SevenZArchiveEntry.javaTimeToNtfsTime(sevenZArchiveEntry.getLastModifiedDate())));
                }
            }
            dataOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            writeUint64(dataOutput, byteArray.length);
            dataOutput.write(byteArray);
        }
    }

    private void writeFileNames(DataOutput dataOutput) throws IOException {
        dataOutput.write(17);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.write(0);
        Iterator<SevenZArchiveEntry> it = this.files.iterator();
        while (it.hasNext()) {
            dataOutputStream.write(it.next().getName().getBytes("UTF-16LE"));
            dataOutputStream.writeShort(0);
        }
        dataOutputStream.flush();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        writeUint64(dataOutput, byteArray.length);
        dataOutput.write(byteArray);
    }

    private void writeFileWindowsAttributes(DataOutput dataOutput) throws IOException {
        Iterator<SevenZArchiveEntry> it = this.files.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next().getHasWindowsAttributes()) {
                i2++;
            }
        }
        if (i2 > 0) {
            dataOutput.write(21);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            if (i2 != this.files.size()) {
                dataOutputStream.write(0);
                BitSet bitSet = new BitSet(this.files.size());
                for (int i3 = 0; i3 < this.files.size(); i3++) {
                    bitSet.set(i3, this.files.get(i3).getHasWindowsAttributes());
                }
                writeBits(dataOutputStream, bitSet, this.files.size());
            } else {
                dataOutputStream.write(1);
            }
            dataOutputStream.write(0);
            for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
                if (sevenZArchiveEntry.getHasWindowsAttributes()) {
                    dataOutputStream.writeInt(Integer.reverseBytes(sevenZArchiveEntry.getWindowsAttributes()));
                }
            }
            dataOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            writeUint64(dataOutput, byteArray.length);
            dataOutput.write(byteArray);
        }
    }

    private void writeFilesInfo(DataOutput dataOutput) throws IOException {
        dataOutput.write(5);
        writeUint64(dataOutput, this.files.size());
        writeFileEmptyStreams(dataOutput);
        writeFileEmptyFiles(dataOutput);
        writeFileAntiItems(dataOutput);
        writeFileNames(dataOutput);
        writeFileCTimes(dataOutput);
        writeFileATimes(dataOutput);
        writeFileMTimes(dataOutput);
        writeFileWindowsAttributes(dataOutput);
        dataOutput.write(0);
    }

    private void writeFolder(DataOutput dataOutput, SevenZArchiveEntry sevenZArchiveEntry) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Iterator<? extends SevenZMethodConfiguration> it = getContentMethods(sevenZArchiveEntry).iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            i3++;
            writeSingleCodec(it.next(), byteArrayOutputStream);
        }
        writeUint64(dataOutput, i3);
        dataOutput.write(byteArrayOutputStream.toByteArray());
        while (i2 < i3 - 1) {
            int i4 = i2 + 1;
            writeUint64(dataOutput, i4);
            writeUint64(dataOutput, i2);
            i2 = i4;
        }
    }

    private void writeHeader(DataOutput dataOutput) throws IOException {
        dataOutput.write(1);
        dataOutput.write(4);
        writeStreamsInfo(dataOutput);
        writeFilesInfo(dataOutput);
        dataOutput.write(0);
    }

    private void writePackInfo(DataOutput dataOutput) throws IOException {
        dataOutput.write(6);
        writeUint64(dataOutput, 0L);
        writeUint64(dataOutput, this.numNonEmptyStreams & InternalZipConstants.ZIP_64_LIMIT);
        dataOutput.write(9);
        for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
            if (sevenZArchiveEntry.hasStream()) {
                writeUint64(dataOutput, sevenZArchiveEntry.getCompressedSize());
            }
        }
        dataOutput.write(10);
        dataOutput.write(1);
        for (SevenZArchiveEntry sevenZArchiveEntry2 : this.files) {
            if (sevenZArchiveEntry2.hasStream()) {
                dataOutput.writeInt(Integer.reverseBytes((int) sevenZArchiveEntry2.getCompressedCrcValue()));
            }
        }
        dataOutput.write(0);
    }

    private void writeSingleCodec(SevenZMethodConfiguration sevenZMethodConfiguration, OutputStream outputStream) throws IOException {
        byte[] id = sevenZMethodConfiguration.getMethod().getId();
        byte[] optionsAsProperties = Coders.findByMethod(sevenZMethodConfiguration.getMethod()).getOptionsAsProperties(sevenZMethodConfiguration.getOptions());
        int length = id.length;
        if (optionsAsProperties.length > 0) {
            length |= 32;
        }
        outputStream.write(length);
        outputStream.write(id);
        if (optionsAsProperties.length > 0) {
            outputStream.write(optionsAsProperties.length);
            outputStream.write(optionsAsProperties);
        }
    }

    private void writeStreamsInfo(DataOutput dataOutput) throws IOException {
        if (this.numNonEmptyStreams > 0) {
            writePackInfo(dataOutput);
            writeUnpackInfo(dataOutput);
        }
        writeSubStreamsInfo(dataOutput);
        dataOutput.write(0);
    }

    private void writeSubStreamsInfo(DataOutput dataOutput) throws IOException {
        dataOutput.write(8);
        dataOutput.write(0);
    }

    private void writeUint64(DataOutput dataOutput, long j2) throws IOException {
        int i2 = 0;
        int i3 = 128;
        int i4 = 0;
        while (true) {
            if (i2 >= 8) {
                break;
            }
            int i5 = i2 + 1;
            if (j2 < (1 << (i5 * 7))) {
                i4 = (int) (i4 | (j2 >>> (i2 * 8)));
                break;
            } else {
                i4 |= i3;
                i3 >>>= 1;
                i2 = i5;
            }
        }
        dataOutput.write(i4);
        while (i2 > 0) {
            dataOutput.write((int) (255 & j2));
            j2 >>>= 8;
            i2--;
        }
    }

    private void writeUnpackInfo(DataOutput dataOutput) throws IOException {
        dataOutput.write(7);
        dataOutput.write(11);
        writeUint64(dataOutput, this.numNonEmptyStreams);
        dataOutput.write(0);
        for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
            if (sevenZArchiveEntry.hasStream()) {
                writeFolder(dataOutput, sevenZArchiveEntry);
            }
        }
        dataOutput.write(12);
        for (SevenZArchiveEntry sevenZArchiveEntry2 : this.files) {
            if (sevenZArchiveEntry2.hasStream()) {
                long[] jArr = this.additionalSizes.get(sevenZArchiveEntry2);
                if (jArr != null) {
                    for (long j2 : jArr) {
                        writeUint64(dataOutput, j2);
                    }
                }
                writeUint64(dataOutput, sevenZArchiveEntry2.getSize());
            }
        }
        dataOutput.write(10);
        dataOutput.write(1);
        for (SevenZArchiveEntry sevenZArchiveEntry3 : this.files) {
            if (sevenZArchiveEntry3.hasStream()) {
                dataOutput.writeInt(Integer.reverseBytes((int) sevenZArchiveEntry3.getCrcValue()));
            }
        }
        dataOutput.write(0);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.finished) {
            finish();
        }
        this.file.close();
    }

    public void closeArchiveEntry() throws IOException {
        CountingOutputStream countingOutputStream = this.currentOutputStream;
        if (countingOutputStream != null) {
            countingOutputStream.flush();
            this.currentOutputStream.close();
        }
        List<SevenZArchiveEntry> list = this.files;
        SevenZArchiveEntry sevenZArchiveEntry = list.get(list.size() - 1);
        int i2 = 0;
        if (this.fileBytesWritten > 0) {
            sevenZArchiveEntry.setHasStream(true);
            this.numNonEmptyStreams++;
            sevenZArchiveEntry.setSize(this.currentOutputStream.getBytesWritten());
            sevenZArchiveEntry.setCompressedSize(this.fileBytesWritten);
            sevenZArchiveEntry.setCrcValue(this.crc32.getValue());
            sevenZArchiveEntry.setCompressedCrcValue(this.compressedCrc32.getValue());
            sevenZArchiveEntry.setHasCrc(true);
            CountingOutputStream[] countingOutputStreamArr = this.additionalCountingStreams;
            if (countingOutputStreamArr != null) {
                long[] jArr = new long[countingOutputStreamArr.length];
                while (true) {
                    CountingOutputStream[] countingOutputStreamArr2 = this.additionalCountingStreams;
                    if (i2 >= countingOutputStreamArr2.length) {
                        break;
                    }
                    jArr[i2] = countingOutputStreamArr2[i2].getBytesWritten();
                    i2++;
                }
                this.additionalSizes.put(sevenZArchiveEntry, jArr);
            }
        } else {
            sevenZArchiveEntry.setHasStream(false);
            sevenZArchiveEntry.setSize(0L);
            sevenZArchiveEntry.setCompressedSize(0L);
            sevenZArchiveEntry.setHasCrc(false);
        }
        this.currentOutputStream = null;
        this.additionalCountingStreams = null;
        this.crc32.reset();
        this.compressedCrc32.reset();
        this.fileBytesWritten = 0L;
    }

    public SevenZArchiveEntry createArchiveEntry(File file, String str) throws IOException {
        SevenZArchiveEntry sevenZArchiveEntry = new SevenZArchiveEntry();
        sevenZArchiveEntry.setDirectory(file.isDirectory());
        sevenZArchiveEntry.setName(str);
        sevenZArchiveEntry.setLastModifiedDate(new Date(file.lastModified()));
        return sevenZArchiveEntry;
    }

    public void finish() throws IOException {
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        this.finished = true;
        long filePointer = this.file.getFilePointer();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        writeHeader(dataOutputStream);
        dataOutputStream.flush();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        this.file.write(byteArray);
        CRC32 crc32 = new CRC32();
        this.file.seek(0L);
        this.file.write(SevenZFile.sevenZSignature);
        this.file.write(0);
        this.file.write(2);
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream2);
        dataOutputStream2.writeLong(Long.reverseBytes(filePointer - 32));
        dataOutputStream2.writeLong(Long.reverseBytes(byteArray.length & InternalZipConstants.ZIP_64_LIMIT));
        crc32.reset();
        crc32.update(byteArray);
        dataOutputStream2.writeInt(Integer.reverseBytes((int) crc32.getValue()));
        dataOutputStream2.flush();
        byte[] byteArray2 = byteArrayOutputStream2.toByteArray();
        crc32.reset();
        crc32.update(byteArray2);
        this.file.writeInt(Integer.reverseBytes((int) crc32.getValue()));
        this.file.write(byteArray2);
    }

    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        this.files.add((SevenZArchiveEntry) archiveEntry);
    }

    public void setContentCompression(SevenZMethod sevenZMethod) {
        setContentMethods(Collections.singletonList(new SevenZMethodConfiguration(sevenZMethod)));
    }

    public void setContentMethods(Iterable<? extends SevenZMethodConfiguration> iterable) {
        this.contentMethods = reverse(iterable);
    }

    public void write(int i2) throws IOException {
        getCurrentOutputStream().write(i2);
    }

    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    public class OutputStreamWrapper extends OutputStream {
        private OutputStreamWrapper() {
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
        }

        @Override // java.io.OutputStream
        public void write(int i2) throws IOException {
            SevenZOutputFile.this.file.write(i2);
            SevenZOutputFile.this.compressedCrc32.update(i2);
            SevenZOutputFile.access$408(SevenZOutputFile.this);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            write(bArr, 0, bArr.length);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i2, int i3) throws IOException {
            SevenZOutputFile.this.file.write(bArr, i2, i3);
            SevenZOutputFile.this.compressedCrc32.update(bArr, i2, i3);
            SevenZOutputFile.access$414(SevenZOutputFile.this, i3);
        }
    }

    public void write(byte[] bArr, int i2, int i3) throws IOException {
        if (i3 > 0) {
            getCurrentOutputStream().write(bArr, i2, i3);
        }
    }
}
