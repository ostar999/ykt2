package org.eclipse.jetty.io;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaPeriodQueue;
import com.heytap.mcssdk.constant.a;
import okhttp3.internal.connection.RealConnection;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jetty.io.BufferCache;
import org.eclipse.jetty.util.StringUtil;

/* loaded from: classes9.dex */
public class BufferUtil {
    static final byte MINUS = 45;
    static final byte SPACE = 32;
    static final byte[] DIGIT = {TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 65, 66, 67, 68, 69, 70};
    private static final int[] decDivisors = {1000000000, 100000000, 10000000, 1000000, 100000, 10000, 1000, 100, 10, 1};
    private static final int[] hexDivisors = {268435456, 16777216, 1048576, 65536, 4096, 256, 16, 1};
    private static final long[] decDivisorsL = {1000000000000000000L, 100000000000000000L, 10000000000000000L, 1000000000000000L, 100000000000000L, 10000000000000L, MediaPeriodQueue.INITIAL_RENDERER_POSITION_OFFSET_US, 100000000000L, RealConnection.IDLE_CONNECTION_HEALTHY_NS, C.NANOS_PER_SECOND, 100000000, 10000000, 1000000, 100000, a.f7153q, 1000, 100, 10, 1};

    public static boolean isPrefix(Buffer buffer, Buffer buffer2) {
        if (buffer.length() > buffer2.length()) {
            return false;
        }
        int index = buffer2.getIndex();
        int index2 = buffer.getIndex();
        while (index2 < buffer.putIndex()) {
            int i2 = index + 1;
            if (buffer.peek(index2) != buffer2.peek(index)) {
                return false;
            }
            index2++;
            index = i2;
        }
        return true;
    }

    public static void prependHexInt(Buffer buffer, int i2) {
        boolean z2;
        if (i2 == 0) {
            int index = buffer.getIndex() - 1;
            buffer.poke(index, TarConstants.LF_NORMAL);
            buffer.setGetIndex(index);
            return;
        }
        if (i2 < 0) {
            i2 = -i2;
            z2 = true;
        } else {
            z2 = false;
        }
        int index2 = buffer.getIndex();
        while (i2 > 0) {
            int i3 = i2 & 15;
            i2 >>= 4;
            index2--;
            buffer.poke(index2, DIGIT[i3]);
        }
        if (z2) {
            index2--;
            buffer.poke(index2, MINUS);
        }
        buffer.setGetIndex(index2);
    }

    public static void putCRLF(Buffer buffer) {
        buffer.put((byte) 13);
        buffer.put((byte) 10);
    }

    public static void putDecInt(Buffer buffer, int i2) {
        if (i2 < 0) {
            buffer.put(MINUS);
            if (i2 == Integer.MIN_VALUE) {
                buffer.put(TarConstants.LF_SYMLINK);
                i2 = 147483648;
            } else {
                i2 = -i2;
            }
        }
        if (i2 < 10) {
            buffer.put(DIGIT[i2]);
            return;
        }
        int i3 = 0;
        boolean z2 = false;
        while (true) {
            int[] iArr = decDivisors;
            if (i3 >= iArr.length) {
                return;
            }
            int i4 = iArr[i3];
            if (i2 >= i4) {
                int i5 = i2 / i4;
                buffer.put(DIGIT[i5]);
                i2 -= i5 * iArr[i3];
                z2 = true;
            } else if (z2) {
                buffer.put(TarConstants.LF_NORMAL);
            }
            i3++;
        }
    }

    public static void putDecLong(Buffer buffer, long j2) {
        if (j2 < 0) {
            buffer.put(MINUS);
            if (j2 == Long.MIN_VALUE) {
                buffer.put((byte) 57);
                j2 = 223372036854775808L;
            } else {
                j2 = -j2;
            }
        }
        if (j2 < 10) {
            buffer.put(DIGIT[(int) j2]);
            return;
        }
        int i2 = 0;
        boolean z2 = false;
        while (true) {
            long[] jArr = decDivisorsL;
            if (i2 >= jArr.length) {
                return;
            }
            long j3 = jArr[i2];
            if (j2 >= j3) {
                long j4 = j2 / j3;
                buffer.put(DIGIT[(int) j4]);
                j2 -= j4 * jArr[i2];
                z2 = true;
            } else if (z2) {
                buffer.put(TarConstants.LF_NORMAL);
            }
            i2++;
        }
    }

    public static void putHexInt(Buffer buffer, int i2) {
        if (i2 < 0) {
            buffer.put(MINUS);
            if (i2 == Integer.MIN_VALUE) {
                buffer.put((byte) 56);
                buffer.put(TarConstants.LF_NORMAL);
                buffer.put(TarConstants.LF_NORMAL);
                buffer.put(TarConstants.LF_NORMAL);
                buffer.put(TarConstants.LF_NORMAL);
                buffer.put(TarConstants.LF_NORMAL);
                buffer.put(TarConstants.LF_NORMAL);
                buffer.put(TarConstants.LF_NORMAL);
                return;
            }
            i2 = -i2;
        }
        if (i2 < 16) {
            buffer.put(DIGIT[i2]);
            return;
        }
        int i3 = 0;
        boolean z2 = false;
        while (true) {
            int[] iArr = hexDivisors;
            if (i3 >= iArr.length) {
                return;
            }
            int i4 = iArr[i3];
            if (i2 >= i4) {
                int i5 = i2 / i4;
                buffer.put(DIGIT[i5]);
                i2 -= i5 * iArr[i3];
                z2 = true;
            } else if (z2) {
                buffer.put(TarConstants.LF_NORMAL);
            }
            i3++;
        }
    }

    public static String to8859_1_String(Buffer buffer) {
        return buffer instanceof BufferCache.CachedBuffer ? buffer.toString() : buffer.toString(StringUtil.__ISO_8859_1_CHARSET);
    }

    public static Buffer toBuffer(long j2) {
        ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(32);
        putDecLong(byteArrayBuffer, j2);
        return byteArrayBuffer;
    }

    public static int toInt(Buffer buffer) {
        boolean z2 = false;
        int i2 = 0;
        boolean z3 = false;
        for (int index = buffer.getIndex(); index < buffer.putIndex(); index++) {
            byte bPeek = buffer.peek(index);
            if (bPeek > 32) {
                if (bPeek >= 48 && bPeek <= 57) {
                    i2 = (i2 * 10) + (bPeek - 48);
                    z2 = true;
                } else {
                    if (bPeek != 45 || z2) {
                        break;
                    }
                    z3 = true;
                }
            } else {
                if (z2) {
                    break;
                }
            }
        }
        if (z2) {
            return z3 ? -i2 : i2;
        }
        throw new NumberFormatException(buffer.toString());
    }

    public static long toLong(Buffer buffer) {
        long j2 = 0;
        boolean z2 = false;
        boolean z3 = false;
        for (int index = buffer.getIndex(); index < buffer.putIndex(); index++) {
            byte bPeek = buffer.peek(index);
            if (bPeek > 32) {
                if (bPeek >= 48 && bPeek <= 57) {
                    j2 = (j2 * 10) + (bPeek - 48);
                    z2 = true;
                } else {
                    if (bPeek != 45 || z2) {
                        break;
                    }
                    z3 = true;
                }
            } else {
                if (z2) {
                    break;
                }
            }
        }
        if (z2) {
            return z3 ? -j2 : j2;
        }
        throw new NumberFormatException(buffer.toString());
    }
}
