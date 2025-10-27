package com.easefun.polyv.mediasdk.gifmaker;

import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes3.dex */
class LZWEncoder {
    static final int BITS = 12;
    private static final int EOF = -1;
    static final int HSIZE = 5003;
    int ClearCode;
    int EOFCode;
    int a_count;
    private int curPixel;
    int g_init_bits;
    private int imgH;
    private int imgW;
    private int initCodeSize;
    int maxcode;
    int n_bits;
    private byte[] pixAry;
    private int remaining;
    int maxbits = 12;
    int maxmaxcode = 4096;
    int[] htab = new int[5003];
    int[] codetab = new int[5003];
    int hsize = 5003;
    int free_ent = 0;
    boolean clear_flg = false;
    int cur_accum = 0;
    int cur_bits = 0;
    int[] masks = {0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, R2.attr.indicatorSize, 4095, R2.dimen.preference_seekbar_padding_start, R2.id.ll_private, 32767, 65535};
    byte[] accum = new byte[256];

    public LZWEncoder(int i2, int i3, byte[] bArr, int i4) {
        this.imgW = i2;
        this.imgH = i3;
        this.pixAry = bArr;
        this.initCodeSize = Math.max(2, i4);
    }

    private int nextPixel() {
        int i2 = this.remaining;
        if (i2 == 0) {
            return -1;
        }
        this.remaining = i2 - 1;
        byte[] bArr = this.pixAry;
        int i3 = this.curPixel;
        this.curPixel = i3 + 1;
        return bArr[i3] & 255;
    }

    public final int MAXCODE(int i2) {
        return (1 << i2) - 1;
    }

    public void char_out(byte b3, OutputStream outputStream) throws IOException {
        byte[] bArr = this.accum;
        int i2 = this.a_count;
        int i3 = i2 + 1;
        this.a_count = i3;
        bArr[i2] = b3;
        if (i3 >= 254) {
            flush_char(outputStream);
        }
    }

    public void cl_block(OutputStream outputStream) throws IOException {
        cl_hash(this.hsize);
        int i2 = this.ClearCode;
        this.free_ent = i2 + 2;
        this.clear_flg = true;
        output(i2, outputStream);
    }

    public void cl_hash(int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            this.htab[i3] = -1;
        }
    }

    public void compress(int i2, OutputStream outputStream) throws IOException {
        int i3;
        this.g_init_bits = i2;
        int i4 = 0;
        this.clear_flg = false;
        this.n_bits = i2;
        this.maxcode = MAXCODE(i2);
        int i5 = 1 << (i2 - 1);
        this.ClearCode = i5;
        this.EOFCode = i5 + 1;
        this.free_ent = i5 + 2;
        this.a_count = 0;
        int iNextPixel = nextPixel();
        for (int i6 = this.hsize; i6 < 65536; i6 *= 2) {
            i4++;
        }
        int i7 = 8 - i4;
        int i8 = this.hsize;
        cl_hash(i8);
        output(this.ClearCode, outputStream);
        while (true) {
            int iNextPixel2 = nextPixel();
            if (iNextPixel2 == -1) {
                output(iNextPixel, outputStream);
                output(this.EOFCode, outputStream);
                return;
            }
            int i9 = (iNextPixel2 << this.maxbits) + iNextPixel;
            int i10 = (iNextPixel2 << i7) ^ iNextPixel;
            int i11 = this.htab[i10];
            if (i11 == i9) {
                iNextPixel = this.codetab[i10];
            } else {
                if (i11 >= 0) {
                    int i12 = i8 - i10;
                    if (i10 == 0) {
                        i12 = 1;
                    }
                    do {
                        i10 -= i12;
                        if (i10 < 0) {
                            i10 += i8;
                        }
                        i3 = this.htab[i10];
                        if (i3 == i9) {
                            iNextPixel = this.codetab[i10];
                            break;
                        }
                    } while (i3 >= 0);
                }
                output(iNextPixel, outputStream);
                int i13 = this.free_ent;
                if (i13 < this.maxmaxcode) {
                    int[] iArr = this.codetab;
                    this.free_ent = i13 + 1;
                    iArr[i10] = i13;
                    this.htab[i10] = i9;
                } else {
                    cl_block(outputStream);
                }
                iNextPixel = iNextPixel2;
            }
        }
    }

    public void encode(OutputStream outputStream) throws IOException {
        outputStream.write(this.initCodeSize);
        this.remaining = this.imgW * this.imgH;
        this.curPixel = 0;
        compress(this.initCodeSize + 1, outputStream);
        outputStream.write(0);
    }

    public void flush_char(OutputStream outputStream) throws IOException {
        int i2 = this.a_count;
        if (i2 > 0) {
            outputStream.write(i2);
            outputStream.write(this.accum, 0, this.a_count);
            this.a_count = 0;
        }
    }

    public void output(int i2, OutputStream outputStream) throws IOException {
        int i3 = this.cur_accum;
        int[] iArr = this.masks;
        int i4 = this.cur_bits;
        int i5 = i3 & iArr[i4];
        this.cur_accum = i5;
        if (i4 > 0) {
            this.cur_accum = i5 | (i2 << i4);
        } else {
            this.cur_accum = i2;
        }
        this.cur_bits = i4 + this.n_bits;
        while (this.cur_bits >= 8) {
            char_out((byte) (this.cur_accum & 255), outputStream);
            this.cur_accum >>= 8;
            this.cur_bits -= 8;
        }
        if (this.free_ent > this.maxcode || this.clear_flg) {
            if (this.clear_flg) {
                int i6 = this.g_init_bits;
                this.n_bits = i6;
                this.maxcode = MAXCODE(i6);
                this.clear_flg = false;
            } else {
                int i7 = this.n_bits + 1;
                this.n_bits = i7;
                if (i7 == this.maxbits) {
                    this.maxcode = this.maxmaxcode;
                } else {
                    this.maxcode = MAXCODE(i7);
                }
            }
        }
        if (i2 == this.EOFCode) {
            while (this.cur_bits > 0) {
                char_out((byte) (this.cur_accum & 255), outputStream);
                this.cur_accum >>= 8;
                this.cur_bits -= 8;
            }
            flush_char(outputStream);
        }
    }
}
