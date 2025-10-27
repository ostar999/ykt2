package com.arialyy.aria.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class BufferedRandomAccessFile extends RandomAccessFile {
    static final long BuffMask_ = -65536;
    public static final int BuffSz_ = 65536;
    static final int LogBuffSz_ = 16;
    private byte[] buff_;
    private boolean closed_;
    private long curr_;
    private boolean dirty_;
    private long diskPos_;
    private long hi_;
    private boolean hitEOF_;
    private long lo_;
    private long maxHi_;

    public BufferedRandomAccessFile(File file, String str) throws IOException {
        super(file, str);
        init(0);
    }

    private int fillBuffer() throws IOException {
        int length = this.buff_.length;
        int i2 = 0;
        while (length > 0) {
            int i3 = super.read(this.buff_, i2, length);
            if (i3 < 0) {
                break;
            }
            i2 += i3;
            length -= i3;
        }
        if (i2 < 0) {
            byte[] bArr = this.buff_;
            boolean z2 = i2 < bArr.length;
            this.hitEOF_ = z2;
            if (z2) {
                Arrays.fill(bArr, i2, bArr.length, (byte) -1);
            }
        }
        this.diskPos_ += i2;
        return i2;
    }

    private void flushBuffer() throws IOException {
        if (this.dirty_) {
            long j2 = this.diskPos_;
            long j3 = this.lo_;
            if (j2 != j3) {
                super.seek(j3);
            }
            super.write(this.buff_, 0, (int) (this.curr_ - this.lo_));
            this.diskPos_ = this.curr_;
            this.dirty_ = false;
        }
    }

    private void init(int i2) {
        this.closed_ = false;
        this.dirty_ = false;
        this.hi_ = 0L;
        this.curr_ = 0L;
        this.lo_ = 0L;
        this.buff_ = i2 > 65536 ? new byte[i2] : new byte[65536];
        this.maxHi_ = 65536L;
        this.hitEOF_ = false;
        this.diskPos_ = 0L;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int writeAtMost(byte[] r7, int r8, int r9) throws java.io.IOException {
        /*
            r6 = this;
            long r0 = r6.curr_
            long r2 = r6.hi_
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 < 0) goto L24
            boolean r4 = r6.hitEOF_
            if (r4 == 0) goto L15
            long r4 = r6.maxHi_
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 >= 0) goto L15
            r6.hi_ = r4
            goto L24
        L15:
            r6.seek(r0)
            long r0 = r6.curr_
            long r2 = r6.hi_
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto L24
            long r0 = r6.maxHi_
            r6.hi_ = r0
        L24:
            long r0 = r6.hi_
            long r2 = r6.curr_
            long r0 = r0 - r2
            int r0 = (int) r0
            int r9 = java.lang.Math.min(r9, r0)
            long r0 = r6.curr_
            long r2 = r6.lo_
            long r0 = r0 - r2
            int r0 = (int) r0
            byte[] r1 = r6.buff_
            java.lang.System.arraycopy(r7, r8, r1, r0, r9)
            long r7 = r6.curr_
            long r0 = (long) r9
            long r7 = r7 + r0
            r6.curr_ = r7
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.arialyy.aria.util.BufferedRandomAccessFile.writeAtMost(byte[], int, int):int");
    }

    @Override // java.io.RandomAccessFile, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        flush();
        this.closed_ = true;
        super.close();
    }

    public void flush() throws IOException {
        flushBuffer();
    }

    @Override // java.io.RandomAccessFile
    public long getFilePointer() {
        return this.curr_;
    }

    @Override // java.io.RandomAccessFile
    public long length() throws IOException {
        return Math.max(this.curr_, super.length());
    }

    @Override // java.io.RandomAccessFile
    public int read() throws IOException {
        long j2 = this.curr_;
        if (j2 >= this.hi_) {
            if (this.hitEOF_) {
                return -1;
            }
            seek(j2);
            if (this.curr_ == this.hi_) {
                return -1;
            }
        }
        byte[] bArr = this.buff_;
        long j3 = this.curr_;
        byte b3 = bArr[(int) (j3 - this.lo_)];
        this.curr_ = j3 + 1;
        return b3 & 255;
    }

    @Override // java.io.RandomAccessFile
    public void seek(long j2) throws IOException {
        if (j2 >= this.hi_ || j2 < this.lo_) {
            flushBuffer();
            long j3 = BuffMask_ & j2;
            this.lo_ = j3;
            this.maxHi_ = this.buff_.length + j3;
            if (this.diskPos_ != j3) {
                super.seek(j3);
                this.diskPos_ = this.lo_;
            }
            this.hi_ = this.lo_ + fillBuffer();
        } else if (j2 < this.curr_) {
            flushBuffer();
        }
        this.curr_ = j2;
    }

    @Override // java.io.RandomAccessFile, java.io.DataOutput
    public void write(int i2) throws IOException {
        long j2 = this.curr_;
        long j3 = this.hi_;
        if (j2 >= j3) {
            if (!this.hitEOF_ || j3 >= this.maxHi_) {
                seek(j2);
                long j4 = this.curr_;
                long j5 = this.hi_;
                if (j4 == j5) {
                    this.hi_ = j5 + 1;
                }
            } else {
                this.hi_ = j3 + 1;
            }
        }
        byte[] bArr = this.buff_;
        long j6 = this.curr_;
        bArr[(int) (j6 - this.lo_)] = (byte) i2;
        this.curr_ = j6 + 1;
        this.dirty_ = true;
    }

    public BufferedRandomAccessFile(File file, String str, int i2) throws IOException {
        super(file, str);
        init(i2);
    }

    public BufferedRandomAccessFile(String str, String str2) throws IOException {
        super(str, str2);
        init(0);
    }

    public BufferedRandomAccessFile(String str, String str2, int i2) throws FileNotFoundException {
        super(str, str2);
        init(i2);
    }

    @Override // java.io.RandomAccessFile
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.RandomAccessFile
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        long j2 = this.curr_;
        if (j2 >= this.hi_) {
            if (this.hitEOF_) {
                return -1;
            }
            seek(j2);
            if (this.curr_ == this.hi_) {
                return -1;
            }
        }
        int iMin = Math.min(i3, (int) (this.hi_ - this.curr_));
        System.arraycopy(this.buff_, (int) (this.curr_ - this.lo_), bArr, i2, iMin);
        this.curr_ += iMin;
        return iMin;
    }

    @Override // java.io.RandomAccessFile, java.io.DataOutput
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.RandomAccessFile, java.io.DataOutput
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        while (i3 > 0) {
            int iWriteAtMost = writeAtMost(bArr, i2, i3);
            i2 += iWriteAtMost;
            i3 -= iWriteAtMost;
            this.dirty_ = true;
        }
    }
}
