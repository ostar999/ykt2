package com.mobile.auth.c;

import android.util.Log;
import com.google.common.base.Ascii;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import kotlin.io.encoding.Base64;
import okio.Utf8;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jetty.http.HttpTokens;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static String f9637a = "c";

    /* renamed from: b, reason: collision with root package name */
    private static char[] f9638b = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    /* renamed from: c, reason: collision with root package name */
    private static byte[] f9639c = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, Utf8.REPLACEMENT_BYTE, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, HttpTokens.COLON, HttpTokens.SEMI_COLON, 60, Base64.padSymbol, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, Ascii.DC2, 19, Ascii.DC4, 21, 22, 23, Ascii.CAN, Ascii.EM, -1, -1, -1, -1, -1, -1, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, -1, -1, -1, -1, -1};

    public static String a(byte[] bArr) {
        String str;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            int length = bArr.length;
            int i2 = 0;
            while (i2 < length) {
                int i3 = i2 + 1;
                int i4 = bArr[i2] & 255;
                if (i3 == length) {
                    stringBuffer.append(f9638b[i4 >>> 2]);
                    stringBuffer.append(f9638b[(i4 & 3) << 4]);
                    str = "==";
                } else {
                    int i5 = i3 + 1;
                    int i6 = bArr[i3] & 255;
                    if (i5 == length) {
                        stringBuffer.append(f9638b[i4 >>> 2]);
                        stringBuffer.append(f9638b[((i4 & 3) << 4) | ((i6 & 240) >>> 4)]);
                        stringBuffer.append(f9638b[(i6 & 15) << 2]);
                        str = "=";
                    } else {
                        int i7 = i5 + 1;
                        int i8 = bArr[i5] & 255;
                        stringBuffer.append(f9638b[i4 >>> 2]);
                        stringBuffer.append(f9638b[((i4 & 3) << 4) | ((i6 & 240) >>> 4)]);
                        stringBuffer.append(f9638b[((i6 & 15) << 2) | ((i8 & 192) >>> 6)]);
                        stringBuffer.append(f9638b[i8 & 63]);
                        i2 = i7;
                    }
                }
                stringBuffer.append(str);
                break;
            }
            return stringBuffer.toString();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static byte[] a(String str) {
        try {
            return b(str);
        } catch (Throwable th) {
            try {
                Log.w(f9637a, "When decode() ,throws exception", th);
                return new byte[0];
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return null;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return null;
                }
            }
        }
    }

    private static byte[] b(String str) throws Throwable {
        int i2;
        byte b3;
        int i3;
        byte b4;
        int i4;
        byte b5;
        int i5;
        byte b6;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            byte[] bytes = str.getBytes("US-ASCII");
            int length = bytes.length;
            int i6 = 0;
            while (i6 < length) {
                while (true) {
                    i2 = i6 + 1;
                    b3 = f9639c[bytes[i6]];
                    if (i2 >= length || b3 != -1) {
                        break;
                    }
                    i6 = i2;
                }
                if (b3 == -1) {
                    break;
                }
                while (true) {
                    i3 = i2 + 1;
                    b4 = f9639c[bytes[i2]];
                    if (i3 >= length || b4 != -1) {
                        break;
                    }
                    i2 = i3;
                }
                if (b4 == -1) {
                    break;
                }
                stringBuffer.append((char) ((b3 << 2) | ((b4 & TarConstants.LF_NORMAL) >>> 4)));
                while (true) {
                    i4 = i3 + 1;
                    byte b7 = bytes[i3];
                    if (b7 != 61) {
                        b5 = f9639c[b7];
                        if (i4 >= length || b5 != -1) {
                            break;
                        }
                        i3 = i4;
                    } else {
                        return stringBuffer.toString().getBytes("iso8859-1");
                    }
                }
                if (b5 == -1) {
                    break;
                }
                stringBuffer.append((char) (((b4 & 15) << 4) | ((b5 & 60) >>> 2)));
                while (true) {
                    i5 = i4 + 1;
                    byte b8 = bytes[i4];
                    if (b8 != 61) {
                        b6 = f9639c[b8];
                        if (i5 >= length || b6 != -1) {
                            break;
                        }
                        i4 = i5;
                    } else {
                        return stringBuffer.toString().getBytes("iso8859-1");
                    }
                }
                if (b6 == -1) {
                    break;
                }
                stringBuffer.append((char) (b6 | ((b5 & 3) << 6)));
                i6 = i5;
            }
            return stringBuffer.toString().getBytes("iso8859-1");
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }
}
