package cn.hutool.core.lang;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.RuntimeUtil;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class ObjectId {
    private static final AtomicInteger NEXT_INC = new AtomicInteger(RandomUtil.randomInt());
    private static final int MACHINE = getMachinePiece() | getProcessPiece();

    private static int getMachinePiece() {
        int iRandomInt;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                sb.append(networkInterfaces.nextElement().toString());
            }
            iRandomInt = sb.toString().hashCode();
        } catch (Throwable unused) {
            iRandomInt = RandomUtil.randomInt();
        }
        return iRandomInt << 16;
    }

    private static int getProcessPiece() {
        int iRandomInt;
        try {
            iRandomInt = RuntimeUtil.getPid();
        } catch (Throwable unused) {
            iRandomInt = RandomUtil.randomInt();
        }
        ClassLoader classLoader = ClassLoaderUtil.getClassLoader();
        return (Integer.toHexString(iRandomInt) + Integer.toHexString(classLoader != null ? System.identityHashCode(classLoader) : 0)).hashCode() & 65535;
    }

    public static boolean isValid(String str) {
        String strRemoveAll;
        int length;
        if (str == null || (length = (strRemoveAll = CharSequenceUtil.removeAll(str, "-")).length()) != 24) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = strRemoveAll.charAt(i2);
            if ((cCharAt < '0' || cCharAt > '9') && ((cCharAt < 'a' || cCharAt > 'f') && (cCharAt < 'A' || cCharAt > 'F'))) {
                return false;
            }
        }
        return true;
    }

    public static String next() {
        return next(false);
    }

    public static byte[] nextBytes() {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[12]);
        byteBufferWrap.putInt((int) DateUtil.currentSeconds());
        byteBufferWrap.putInt(MACHINE);
        byteBufferWrap.putInt(NEXT_INC.getAndIncrement());
        return byteBufferWrap.array();
    }

    public static String next(boolean z2) {
        byte[] bArrNextBytes = nextBytes();
        StringBuilder sb = new StringBuilder(z2 ? 26 : 24);
        for (int i2 = 0; i2 < bArrNextBytes.length; i2++) {
            if (z2 && i2 % 4 == 0 && i2 != 0) {
                sb.append("-");
            }
            int i3 = bArrNextBytes[i2] & 255;
            if (i3 < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(i3));
        }
        return sb.toString();
    }
}
