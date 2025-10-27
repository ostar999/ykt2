package net.lingala.zip4j.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Raw;
import net.lingala.zip4j.util.Zip4jUtil;

/* loaded from: classes9.dex */
public class SplitOutputStream extends OutputStream {
    private long bytesWrittenForThisPart;
    private int currSplitFileCounter;
    private File outFile;
    private RandomAccessFile raf;
    private long splitLength;
    private File zipFile;

    public SplitOutputStream(String str) throws ZipException, FileNotFoundException {
        this(Zip4jUtil.isStringNotNullAndNotEmpty(str) ? new File(str) : null);
    }

    private boolean isHeaderData(byte[] bArr) {
        if (bArr != null && bArr.length >= 4) {
            int intLittleEndian = Raw.readIntLittleEndian(bArr, 0);
            long[] allHeaderSignatures = Zip4jUtil.getAllHeaderSignatures();
            if (allHeaderSignatures != null && allHeaderSignatures.length > 0) {
                for (long j2 : allHeaderSignatures) {
                    if (j2 != 134695760 && j2 == intLittleEndian) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void startNextSplitFile() throws IOException {
        String str;
        File file;
        try {
            String zipFileNameWithoutExt = Zip4jUtil.getZipFileNameWithoutExt(this.outFile.getName());
            String absolutePath = this.zipFile.getAbsolutePath();
            if (this.outFile.getParent() == null) {
                str = "";
            } else {
                str = this.outFile.getParent() + System.getProperty("file.separator");
            }
            if (this.currSplitFileCounter < 9) {
                file = new File(str + zipFileNameWithoutExt + ".z0" + (this.currSplitFileCounter + 1));
            } else {
                file = new File(str + zipFileNameWithoutExt + ".z" + (this.currSplitFileCounter + 1));
            }
            this.raf.close();
            if (file.exists()) {
                throw new IOException("split file: " + file.getName() + " already exists in the current directory, cannot rename this file");
            }
            if (!this.zipFile.renameTo(file)) {
                throw new IOException("cannot rename newly created split file");
            }
            this.zipFile = new File(absolutePath);
            this.raf = new RandomAccessFile(this.zipFile, InternalZipConstants.WRITE_MODE);
            this.currSplitFileCounter++;
        } catch (ZipException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public boolean checkBuffSizeAndStartNextSplitFile(int i2) throws ZipException {
        if (i2 < 0) {
            throw new ZipException("negative buffersize for checkBuffSizeAndStartNextSplitFile");
        }
        if (isBuffSizeFitForCurrSplitFile(i2)) {
            return false;
        }
        try {
            startNextSplitFile();
            this.bytesWrittenForThisPart = 0L;
            return true;
        } catch (IOException e2) {
            throw new ZipException(e2);
        }
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        RandomAccessFile randomAccessFile = this.raf;
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
    }

    public int getCurrSplitFileCounter() {
        return this.currSplitFileCounter;
    }

    public long getFilePointer() throws IOException {
        return this.raf.getFilePointer();
    }

    public long getSplitLength() {
        return this.splitLength;
    }

    public boolean isBuffSizeFitForCurrSplitFile(int i2) throws ZipException {
        if (i2 < 0) {
            throw new ZipException("negative buffersize for isBuffSizeFitForCurrSplitFile");
        }
        long j2 = this.splitLength;
        return j2 < 65536 || this.bytesWrittenForThisPart + ((long) i2) <= j2;
    }

    public boolean isSplitZipFile() {
        return this.splitLength != -1;
    }

    public void seek(long j2) throws IOException {
        this.raf.seek(j2);
    }

    @Override // java.io.OutputStream
    public void write(int i2) throws IOException {
        write(new byte[]{(byte) i2}, 0, 1);
    }

    public SplitOutputStream(File file) throws ZipException, FileNotFoundException {
        this(file, -1L);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    public SplitOutputStream(String str, long j2) throws ZipException, FileNotFoundException {
        this(!Zip4jUtil.isStringNotNullAndNotEmpty(str) ? new File(str) : null, j2);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        if (i3 <= 0) {
            return;
        }
        long j2 = this.splitLength;
        if (j2 == -1) {
            this.raf.write(bArr, i2, i3);
            this.bytesWrittenForThisPart += i3;
            return;
        }
        if (j2 >= 65536) {
            long j3 = this.bytesWrittenForThisPart;
            if (j3 >= j2) {
                startNextSplitFile();
                this.raf.write(bArr, i2, i3);
                this.bytesWrittenForThisPart = i3;
                return;
            }
            long j4 = i3;
            if (j3 + j4 > j2) {
                if (isHeaderData(bArr)) {
                    startNextSplitFile();
                    this.raf.write(bArr, i2, i3);
                    this.bytesWrittenForThisPart = j4;
                    return;
                }
                this.raf.write(bArr, i2, (int) (this.splitLength - this.bytesWrittenForThisPart));
                startNextSplitFile();
                RandomAccessFile randomAccessFile = this.raf;
                long j5 = this.splitLength;
                long j6 = this.bytesWrittenForThisPart;
                randomAccessFile.write(bArr, i2 + ((int) (j5 - j6)), (int) (j4 - (j5 - j6)));
                this.bytesWrittenForThisPart = j4 - (this.splitLength - this.bytesWrittenForThisPart);
                return;
            }
            this.raf.write(bArr, i2, i3);
            this.bytesWrittenForThisPart += j4;
            return;
        }
        throw new IOException("split length less than minimum allowed split length of 65536 Bytes");
    }

    public SplitOutputStream(File file, long j2) throws ZipException, FileNotFoundException {
        if (j2 >= 0 && j2 < 65536) {
            throw new ZipException("split length less than minimum allowed split length of 65536 Bytes");
        }
        this.raf = new RandomAccessFile(file, InternalZipConstants.WRITE_MODE);
        this.splitLength = j2;
        this.outFile = file;
        this.zipFile = file;
        this.currSplitFileCounter = 0;
        this.bytesWrittenForThisPart = 0L;
    }
}
