package com.aliyun.auth.common;

import java.io.UnsupportedEncodingException;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes2.dex */
public class Base64Coder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CRLF = 4;
    public static final int DEFAULT = 0;
    public static final int NO_CLOSE = 16;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int URL_SAFE = 8;

    public static abstract class Coder {
        public int op;
        public byte[] output;

        public abstract int maxOutputSize(int i2);

        public abstract boolean process(byte[] bArr, int i2, int i3, boolean z2);
    }

    public static class Decoder extends Coder {
        private static final int[] DECODE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] DECODE_WEBSAFE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int EQUALS = -2;
        private static final int SKIP = -1;
        private final int[] alphabet;
        private int state;
        private int value;

        public Decoder(int i2, byte[] bArr) {
            this.output = bArr;
            this.alphabet = (i2 & 8) == 0 ? DECODE : DECODE_WEBSAFE;
            this.state = 0;
            this.value = 0;
        }

        @Override // com.aliyun.auth.common.Base64Coder.Coder
        public int maxOutputSize(int i2) {
            return ((i2 * 3) / 4) + 10;
        }

        @Override // com.aliyun.auth.common.Base64Coder.Coder
        public boolean process(byte[] bArr, int i2, int i3, boolean z2) {
            int i4 = this.state;
            if (i4 == 6) {
                return false;
            }
            int i5 = i3 + i2;
            int i6 = this.value;
            byte[] bArr2 = this.output;
            int[] iArr = this.alphabet;
            int i7 = 0;
            int i8 = i6;
            int i9 = i4;
            int i10 = i2;
            while (i10 < i5) {
                if (i9 == 0) {
                    while (true) {
                        int i11 = i10 + 4;
                        if (i11 > i5 || (i8 = (iArr[bArr[i10] & 255] << 18) | (iArr[bArr[i10 + 1] & 255] << 12) | (iArr[bArr[i10 + 2] & 255] << 6) | iArr[bArr[i10 + 3] & 255]) < 0) {
                            break;
                        }
                        bArr2[i7 + 2] = (byte) i8;
                        bArr2[i7 + 1] = (byte) (i8 >> 8);
                        bArr2[i7] = (byte) (i8 >> 16);
                        i7 += 3;
                        i10 = i11;
                    }
                    if (i10 >= i5) {
                        break;
                    }
                }
                int i12 = i10 + 1;
                int i13 = iArr[bArr[i10] & 255];
                if (i9 != 0) {
                    if (i9 == 1) {
                        if (i13 < 0) {
                            if (i13 != -1) {
                                this.state = 6;
                                return false;
                            }
                        }
                        i13 |= i8 << 6;
                    } else if (i9 == 2) {
                        if (i13 < 0) {
                            if (i13 == -2) {
                                bArr2[i7] = (byte) (i8 >> 4);
                                i7++;
                                i9 = 4;
                            } else if (i13 != -1) {
                                this.state = 6;
                                return false;
                            }
                        }
                        i13 |= i8 << 6;
                    } else if (i9 != 3) {
                        if (i9 != 4) {
                            if (i9 == 5 && i13 != -1) {
                                this.state = 6;
                                return false;
                            }
                        } else if (i13 == -2) {
                            i9++;
                        } else if (i13 != -1) {
                            this.state = 6;
                            return false;
                        }
                    } else if (i13 >= 0) {
                        int i14 = i13 | (i8 << 6);
                        bArr2[i7 + 2] = (byte) i14;
                        bArr2[i7 + 1] = (byte) (i14 >> 8);
                        bArr2[i7] = (byte) (i14 >> 16);
                        i7 += 3;
                        i8 = i14;
                        i9 = 0;
                    } else if (i13 == -2) {
                        bArr2[i7 + 1] = (byte) (i8 >> 2);
                        bArr2[i7] = (byte) (i8 >> 10);
                        i7 += 2;
                        i9 = 5;
                    } else if (i13 != -1) {
                        this.state = 6;
                        return false;
                    }
                    i9++;
                    i8 = i13;
                } else if (i13 >= 0) {
                    i9++;
                    i8 = i13;
                } else if (i13 != -1) {
                    this.state = 6;
                    return false;
                }
                i10 = i12;
            }
            if (!z2) {
                this.state = i9;
                this.value = i8;
                this.op = i7;
                return true;
            }
            if (i9 == 1) {
                this.state = 6;
                return false;
            }
            if (i9 == 2) {
                bArr2[i7] = (byte) (i8 >> 4);
                i7++;
            } else if (i9 == 3) {
                int i15 = i7 + 1;
                bArr2[i7] = (byte) (i8 >> 10);
                i7 = i15 + 1;
                bArr2[i15] = (byte) (i8 >> 2);
            } else if (i9 == 4) {
                this.state = 6;
                return false;
            }
            this.state = i9;
            this.op = i7;
            return true;
        }
    }

    public static class Encoder extends Coder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final byte[] ENCODE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 43, 47};
        private static final byte[] ENCODE_WEBSAFE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 45, 95};
        public static final int LINE_GROUPS = 19;
        private final byte[] alphabet;
        private int count;
        public final boolean do_cr;
        public final boolean do_newline;
        public final boolean do_padding;
        private final byte[] tail;
        int tailLen;

        public Encoder(int i2, byte[] bArr) {
            this.output = bArr;
            this.do_padding = (i2 & 1) == 0;
            boolean z2 = (i2 & 2) == 0;
            this.do_newline = z2;
            this.do_cr = (i2 & 4) != 0;
            this.alphabet = (i2 & 8) == 0 ? ENCODE : ENCODE_WEBSAFE;
            this.tail = new byte[2];
            this.tailLen = 0;
            this.count = z2 ? 19 : -1;
        }

        @Override // com.aliyun.auth.common.Base64Coder.Coder
        public int maxOutputSize(int i2) {
            return ((i2 * 8) / 5) + 10;
        }

        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        /* JADX WARN: Removed duplicated region for block: B:12:0x0050  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0094  */
        /* JADX WARN: Removed duplicated region for block: B:87:0x00e6 A[SYNTHETIC] */
        @Override // com.aliyun.auth.common.Base64Coder.Coder
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean process(byte[] r18, int r19, int r20, boolean r21) {
            /*
                Method dump skipped, instructions count: 478
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.aliyun.auth.common.Base64Coder.Encoder.process(byte[], int, int, boolean):boolean");
        }
    }

    private Base64Coder() {
    }

    public static byte[] decode(String str, int i2) {
        return decode(str.getBytes(), i2);
    }

    public static byte[] encode(byte[] bArr, int i2) {
        return encode(bArr, 0, bArr.length, i2);
    }

    public static String encodeToString(byte[] bArr, int i2) {
        try {
            return new String(encode(bArr, i2), "US-ASCII");
        } catch (UnsupportedEncodingException e2) {
            throw new AssertionError(e2);
        }
    }

    public static byte[] decode(byte[] bArr, int i2) {
        return decode(bArr, 0, bArr.length, i2);
    }

    public static byte[] encode(byte[] bArr, int i2, int i3, int i4) {
        Encoder encoder = new Encoder(i4, null);
        int i5 = (i3 / 3) * 4;
        if (!encoder.do_padding) {
            int i6 = i3 % 3;
            if (i6 == 1) {
                i5 += 2;
            } else if (i6 == 2) {
                i5 += 3;
            }
        } else if (i3 % 3 > 0) {
            i5 += 4;
        }
        if (encoder.do_newline && i3 > 0) {
            i5 += (((i3 - 1) / 57) + 1) * (encoder.do_cr ? 2 : 1);
        }
        encoder.output = new byte[i5];
        encoder.process(bArr, i2, i3, true);
        return encoder.output;
    }

    public static byte[] decode(byte[] bArr, int i2, int i3, int i4) {
        Decoder decoder = new Decoder(i4, new byte[(i3 * 3) / 4]);
        if (decoder.process(bArr, i2, i3, true)) {
            int i5 = decoder.op;
            byte[] bArr2 = decoder.output;
            if (i5 == bArr2.length) {
                return bArr2;
            }
            byte[] bArr3 = new byte[i5];
            System.arraycopy(bArr2, 0, bArr3, 0, i5);
            return bArr3;
        }
        throw new IllegalArgumentException("bad base-64");
    }

    public static String encodeToString(byte[] bArr, int i2, int i3, int i4) {
        try {
            return new String(encode(bArr, i2, i3, i4), "US-ASCII");
        } catch (UnsupportedEncodingException e2) {
            throw new AssertionError(e2);
        }
    }
}
