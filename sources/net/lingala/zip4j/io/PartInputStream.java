package net.lingala.zip4j.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import net.lingala.zip4j.crypto.AESDecrypter;
import net.lingala.zip4j.crypto.IDecrypter;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.unzip.UnzipEngine;

/* loaded from: classes9.dex */
public class PartInputStream extends BaseInputStream {
    private IDecrypter decrypter;
    private boolean isAESEncryptedFile;
    private long length;
    private RandomAccessFile raf;
    private UnzipEngine unzipEngine;
    private byte[] oneByteBuff = new byte[1];
    private byte[] aesBlockByte = new byte[16];
    private int aesBytesReturned = 0;
    private int count = -1;
    private long bytesRead = 0;

    public PartInputStream(RandomAccessFile randomAccessFile, long j2, long j3, UnzipEngine unzipEngine) {
        this.isAESEncryptedFile = false;
        this.raf = randomAccessFile;
        this.unzipEngine = unzipEngine;
        this.decrypter = unzipEngine.getDecrypter();
        this.length = j3;
        this.isAESEncryptedFile = unzipEngine.getFileHeader().isEncrypted() && unzipEngine.getFileHeader().getEncryptionMethod() == 99;
    }

    @Override // net.lingala.zip4j.io.BaseInputStream, java.io.InputStream
    public int available() {
        long j2 = this.length - this.bytesRead;
        if (j2 > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) j2;
    }

    public void checkAndReadAESMacBytes() throws IOException {
        IDecrypter iDecrypter;
        if (this.isAESEncryptedFile && (iDecrypter = this.decrypter) != null && (iDecrypter instanceof AESDecrypter) && ((AESDecrypter) iDecrypter).getStoredMac() == null) {
            byte[] bArr = new byte[10];
            int i2 = this.raf.read(bArr);
            if (i2 != 10) {
                if (!this.unzipEngine.getZipModel().isSplitArchive()) {
                    throw new IOException("Error occured while reading stored AES authentication bytes");
                }
                this.raf.close();
                RandomAccessFile randomAccessFileStartNextSplitFile = this.unzipEngine.startNextSplitFile();
                this.raf = randomAccessFileStartNextSplitFile;
                randomAccessFileStartNextSplitFile.read(bArr, i2, 10 - i2);
            }
            ((AESDecrypter) this.unzipEngine.getDecrypter()).setStoredMac(bArr);
        }
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.raf.close();
    }

    @Override // net.lingala.zip4j.io.BaseInputStream
    public UnzipEngine getUnzipEngine() {
        return this.unzipEngine;
    }

    @Override // net.lingala.zip4j.io.BaseInputStream, java.io.InputStream
    public int read() throws IOException {
        if (this.bytesRead >= this.length) {
            return -1;
        }
        if (!this.isAESEncryptedFile) {
            if (read(this.oneByteBuff, 0, 1) == -1) {
                return -1;
            }
            return this.oneByteBuff[0] & 255;
        }
        int i2 = this.aesBytesReturned;
        if (i2 == 0 || i2 == 16) {
            if (read(this.aesBlockByte) == -1) {
                return -1;
            }
            this.aesBytesReturned = 0;
        }
        byte[] bArr = this.aesBlockByte;
        int i3 = this.aesBytesReturned;
        this.aesBytesReturned = i3 + 1;
        return bArr[i3] & 255;
    }

    @Override // net.lingala.zip4j.io.BaseInputStream
    public void seek(long j2) throws IOException {
        this.raf.seek(j2);
    }

    @Override // java.io.InputStream
    public long skip(long j2) throws IOException {
        if (j2 < 0) {
            throw new IllegalArgumentException();
        }
        long j3 = this.length;
        long j4 = this.bytesRead;
        if (j2 > j3 - j4) {
            j2 = j3 - j4;
        }
        this.bytesRead = j4 + j2;
        return j2;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4;
        long j2 = i3;
        long j3 = this.length;
        long j4 = this.bytesRead;
        if (j2 > j3 - j4 && (i3 = (int) (j3 - j4)) == 0) {
            checkAndReadAESMacBytes();
            return -1;
        }
        if ((this.unzipEngine.getDecrypter() instanceof AESDecrypter) && this.bytesRead + i3 < this.length && (i4 = i3 % 16) != 0) {
            i3 -= i4;
        }
        synchronized (this.raf) {
            int i5 = this.raf.read(bArr, i2, i3);
            this.count = i5;
            if (i5 < i3 && this.unzipEngine.getZipModel().isSplitArchive()) {
                this.raf.close();
                RandomAccessFile randomAccessFileStartNextSplitFile = this.unzipEngine.startNextSplitFile();
                this.raf = randomAccessFileStartNextSplitFile;
                if (this.count < 0) {
                    this.count = 0;
                }
                int i6 = this.count;
                int i7 = randomAccessFileStartNextSplitFile.read(bArr, i6, i3 - i6);
                if (i7 > 0) {
                    this.count += i7;
                }
            }
        }
        int i8 = this.count;
        if (i8 > 0) {
            IDecrypter iDecrypter = this.decrypter;
            if (iDecrypter != null) {
                try {
                    iDecrypter.decryptData(bArr, i2, i8);
                } catch (ZipException e2) {
                    throw new IOException(e2.getMessage());
                }
            }
            this.bytesRead += this.count;
        }
        if (this.bytesRead >= this.length) {
            checkAndReadAESMacBytes();
        }
        return this.count;
    }
}
