package com.google.common.net;

import cn.hutool.core.text.StrPool;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Ints;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.eclipse.jetty.util.StringUtil;

@Beta
@GwtIncompatible
/* loaded from: classes4.dex */
public final class InetAddresses {
    private static final int IPV4_PART_COUNT = 4;
    private static final int IPV6_PART_COUNT = 8;
    private static final Splitter IPV4_SPLITTER = Splitter.on('.').limit(4);
    private static final Splitter IPV6_SPLITTER = Splitter.on(':').limit(10);
    private static final Inet4Address LOOPBACK4 = (Inet4Address) forString("127.0.0.1");
    private static final Inet4Address ANY4 = (Inet4Address) forString(StringUtil.ALL_INTERFACES);

    @Beta
    public static final class TeredoInfo {
        private final Inet4Address client;
        private final int flags;
        private final int port;
        private final Inet4Address server;

        public TeredoInfo(@NullableDecl Inet4Address inet4Address, @NullableDecl Inet4Address inet4Address2, int i2, int i3) {
            Preconditions.checkArgument(i2 >= 0 && i2 <= 65535, "port '%s' is out of range (0 <= port <= 0xffff)", i2);
            Preconditions.checkArgument(i3 >= 0 && i3 <= 65535, "flags '%s' is out of range (0 <= flags <= 0xffff)", i3);
            this.server = (Inet4Address) MoreObjects.firstNonNull(inet4Address, InetAddresses.ANY4);
            this.client = (Inet4Address) MoreObjects.firstNonNull(inet4Address2, InetAddresses.ANY4);
            this.port = i2;
            this.flags = i3;
        }

        public Inet4Address getClient() {
            return this.client;
        }

        public int getFlags() {
            return this.flags;
        }

        public int getPort() {
            return this.port;
        }

        public Inet4Address getServer() {
            return this.server;
        }
    }

    private InetAddresses() {
    }

    private static InetAddress bytesToInetAddress(byte[] bArr) {
        try {
            return InetAddress.getByAddress(bArr);
        } catch (UnknownHostException e2) {
            throw new AssertionError(e2);
        }
    }

    public static int coerceToInteger(InetAddress inetAddress) {
        return ByteStreams.newDataInput(getCoercedIPv4Address(inetAddress).getAddress()).readInt();
    }

    private static void compressLongestRunOfZeroes(int[] iArr) {
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;
        for (int i5 = 0; i5 < iArr.length + 1; i5++) {
            if (i5 >= iArr.length || iArr[i5] != 0) {
                if (i4 >= 0) {
                    int i6 = i5 - i4;
                    if (i6 > i2) {
                        i3 = i4;
                        i2 = i6;
                    }
                    i4 = -1;
                }
            } else if (i4 < 0) {
                i4 = i5;
            }
        }
        if (i2 >= 2) {
            Arrays.fill(iArr, i3, i2 + i3, -1);
        }
    }

    @NullableDecl
    private static String convertDottedQuadToHex(String str) {
        int iLastIndexOf = str.lastIndexOf(58) + 1;
        String strSubstring = str.substring(0, iLastIndexOf);
        byte[] bArrTextToNumericFormatV4 = textToNumericFormatV4(str.substring(iLastIndexOf));
        if (bArrTextToNumericFormatV4 == null) {
            return null;
        }
        return strSubstring + Integer.toHexString(((bArrTextToNumericFormatV4[0] & 255) << 8) | (bArrTextToNumericFormatV4[1] & 255)) + ":" + Integer.toHexString((bArrTextToNumericFormatV4[3] & 255) | ((bArrTextToNumericFormatV4[2] & 255) << 8));
    }

    public static InetAddress decrement(InetAddress inetAddress) {
        byte[] address = inetAddress.getAddress();
        int length = address.length - 1;
        while (length >= 0 && address[length] == 0) {
            address[length] = -1;
            length--;
        }
        Preconditions.checkArgument(length >= 0, "Decrementing %s would wrap.", inetAddress);
        address[length] = (byte) (address[length] - 1);
        return bytesToInetAddress(address);
    }

    public static InetAddress forString(String str) {
        byte[] bArrIpStringToBytes = ipStringToBytes(str);
        if (bArrIpStringToBytes != null) {
            return bytesToInetAddress(bArrIpStringToBytes);
        }
        throw formatIllegalArgumentException("'%s' is not an IP string literal.", str);
    }

    public static InetAddress forUriString(String str) {
        InetAddress inetAddressForUriStringNoThrow = forUriStringNoThrow(str);
        if (inetAddressForUriStringNoThrow != null) {
            return inetAddressForUriStringNoThrow;
        }
        throw formatIllegalArgumentException("Not a valid URI IP literal: '%s'", str);
    }

    @NullableDecl
    private static InetAddress forUriStringNoThrow(String str) {
        int i2;
        Preconditions.checkNotNull(str);
        if (str.startsWith(StrPool.BRACKET_START) && str.endsWith(StrPool.BRACKET_END)) {
            str = str.substring(1, str.length() - 1);
            i2 = 16;
        } else {
            i2 = 4;
        }
        byte[] bArrIpStringToBytes = ipStringToBytes(str);
        if (bArrIpStringToBytes == null || bArrIpStringToBytes.length != i2) {
            return null;
        }
        return bytesToInetAddress(bArrIpStringToBytes);
    }

    private static IllegalArgumentException formatIllegalArgumentException(String str, Object... objArr) {
        return new IllegalArgumentException(String.format(Locale.ROOT, str, objArr));
    }

    public static Inet4Address fromInteger(int i2) {
        return getInet4Address(Ints.toByteArray(i2));
    }

    public static InetAddress fromLittleEndianByteArray(byte[] bArr) throws UnknownHostException {
        byte[] bArr2 = new byte[bArr.length];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr2[i2] = bArr[(bArr.length - i2) - 1];
        }
        return InetAddress.getByAddress(bArr2);
    }

    public static Inet4Address get6to4IPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(is6to4Address(inet6Address), "Address '%s' is not a 6to4 address.", toAddrString(inet6Address));
        return getInet4Address(Arrays.copyOfRange(inet6Address.getAddress(), 2, 6));
    }

    public static Inet4Address getCoercedIPv4Address(InetAddress inetAddress) {
        boolean z2;
        if (inetAddress instanceof Inet4Address) {
            return (Inet4Address) inetAddress;
        }
        byte[] address = inetAddress.getAddress();
        int i2 = 0;
        while (true) {
            if (i2 >= 15) {
                z2 = true;
                break;
            }
            if (address[i2] != 0) {
                z2 = false;
                break;
            }
            i2++;
        }
        if (z2 && address[15] == 1) {
            return LOOPBACK4;
        }
        if (z2 && address[15] == 0) {
            return ANY4;
        }
        Inet6Address inet6Address = (Inet6Address) inetAddress;
        int iAsInt = Hashing.murmur3_32().hashLong(hasEmbeddedIPv4ClientAddress(inet6Address) ? getEmbeddedIPv4ClientAddress(inet6Address).hashCode() : ByteBuffer.wrap(inet6Address.getAddress(), 0, 8).getLong()).asInt() | (-536870912);
        if (iAsInt == -1) {
            iAsInt = -2;
        }
        return getInet4Address(Ints.toByteArray(iAsInt));
    }

    public static Inet4Address getCompatIPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(isCompatIPv4Address(inet6Address), "Address '%s' is not IPv4-compatible.", toAddrString(inet6Address));
        return getInet4Address(Arrays.copyOfRange(inet6Address.getAddress(), 12, 16));
    }

    public static Inet4Address getEmbeddedIPv4ClientAddress(Inet6Address inet6Address) {
        if (isCompatIPv4Address(inet6Address)) {
            return getCompatIPv4Address(inet6Address);
        }
        if (is6to4Address(inet6Address)) {
            return get6to4IPv4Address(inet6Address);
        }
        if (isTeredoAddress(inet6Address)) {
            return getTeredoInfo(inet6Address).getClient();
        }
        throw formatIllegalArgumentException("'%s' has no embedded IPv4 address.", toAddrString(inet6Address));
    }

    private static Inet4Address getInet4Address(byte[] bArr) {
        Preconditions.checkArgument(bArr.length == 4, "Byte array has invalid length for an IPv4 address: %s != 4.", bArr.length);
        return (Inet4Address) bytesToInetAddress(bArr);
    }

    public static Inet4Address getIsatapIPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(isIsatapAddress(inet6Address), "Address '%s' is not an ISATAP address.", toAddrString(inet6Address));
        return getInet4Address(Arrays.copyOfRange(inet6Address.getAddress(), 12, 16));
    }

    public static TeredoInfo getTeredoInfo(Inet6Address inet6Address) {
        Preconditions.checkArgument(isTeredoAddress(inet6Address), "Address '%s' is not a Teredo address.", toAddrString(inet6Address));
        byte[] address = inet6Address.getAddress();
        Inet4Address inet4Address = getInet4Address(Arrays.copyOfRange(address, 4, 8));
        int i2 = ByteStreams.newDataInput(address, 8).readShort() & 65535;
        int i3 = 65535 & (~ByteStreams.newDataInput(address, 10).readShort());
        byte[] bArrCopyOfRange = Arrays.copyOfRange(address, 12, 16);
        for (int i4 = 0; i4 < bArrCopyOfRange.length; i4++) {
            bArrCopyOfRange[i4] = (byte) (~bArrCopyOfRange[i4]);
        }
        return new TeredoInfo(inet4Address, getInet4Address(bArrCopyOfRange), i3, i2);
    }

    public static boolean hasEmbeddedIPv4ClientAddress(Inet6Address inet6Address) {
        return isCompatIPv4Address(inet6Address) || is6to4Address(inet6Address) || isTeredoAddress(inet6Address);
    }

    private static String hextetsToIPv6String(int[] iArr) {
        StringBuilder sb = new StringBuilder(39);
        int i2 = 0;
        boolean z2 = false;
        while (i2 < iArr.length) {
            boolean z3 = iArr[i2] >= 0;
            if (z3) {
                if (z2) {
                    sb.append(':');
                }
                sb.append(Integer.toHexString(iArr[i2]));
            } else if (i2 == 0 || z2) {
                sb.append("::");
            }
            i2++;
            z2 = z3;
        }
        return sb.toString();
    }

    public static InetAddress increment(InetAddress inetAddress) {
        byte[] address = inetAddress.getAddress();
        int length = address.length - 1;
        while (true) {
            if (length < 0 || address[length] != -1) {
                break;
            }
            address[length] = 0;
            length--;
        }
        Preconditions.checkArgument(length >= 0, "Incrementing %s would wrap.", inetAddress);
        address[length] = (byte) (address[length] + 1);
        return bytesToInetAddress(address);
    }

    @NullableDecl
    private static byte[] ipStringToBytes(String str) {
        boolean z2 = false;
        boolean z3 = false;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '.') {
                z3 = true;
            } else if (cCharAt == ':') {
                if (z3) {
                    return null;
                }
                z2 = true;
            } else if (Character.digit(cCharAt, 16) == -1) {
                return null;
            }
        }
        if (!z2) {
            if (z3) {
                return textToNumericFormatV4(str);
            }
            return null;
        }
        if (z3 && (str = convertDottedQuadToHex(str)) == null) {
            return null;
        }
        return textToNumericFormatV6(str);
    }

    public static boolean is6to4Address(Inet6Address inet6Address) {
        byte[] address = inet6Address.getAddress();
        return address[0] == 32 && address[1] == 2;
    }

    public static boolean isCompatIPv4Address(Inet6Address inet6Address) {
        byte b3;
        if (!inet6Address.isIPv4CompatibleAddress()) {
            return false;
        }
        byte[] address = inet6Address.getAddress();
        return (address[12] == 0 && address[13] == 0 && address[14] == 0 && ((b3 = address[15]) == 0 || b3 == 1)) ? false : true;
    }

    public static boolean isInetAddress(String str) {
        return ipStringToBytes(str) != null;
    }

    public static boolean isIsatapAddress(Inet6Address inet6Address) {
        if (isTeredoAddress(inet6Address)) {
            return false;
        }
        byte[] address = inet6Address.getAddress();
        return (address[8] | 3) == 3 && address[9] == 0 && address[10] == 94 && address[11] == -2;
    }

    public static boolean isMappedIPv4Address(String str) {
        byte[] bArrIpStringToBytes = ipStringToBytes(str);
        if (bArrIpStringToBytes == null || bArrIpStringToBytes.length != 16) {
            return false;
        }
        int i2 = 0;
        while (true) {
            if (i2 >= 10) {
                for (int i3 = 10; i3 < 12; i3++) {
                    if (bArrIpStringToBytes[i3] != -1) {
                        return false;
                    }
                }
                return true;
            }
            if (bArrIpStringToBytes[i2] != 0) {
                return false;
            }
            i2++;
        }
    }

    public static boolean isMaximum(InetAddress inetAddress) {
        for (byte b3 : inetAddress.getAddress()) {
            if (b3 != -1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isTeredoAddress(Inet6Address inet6Address) {
        byte[] address = inet6Address.getAddress();
        return address[0] == 32 && address[1] == 1 && address[2] == 0 && address[3] == 0;
    }

    public static boolean isUriInetAddress(String str) {
        return forUriStringNoThrow(str) != null;
    }

    private static short parseHextet(String str) throws NumberFormatException {
        int i2 = Integer.parseInt(str, 16);
        if (i2 <= 65535) {
            return (short) i2;
        }
        throw new NumberFormatException();
    }

    private static byte parseOctet(String str) throws NumberFormatException {
        int i2 = Integer.parseInt(str);
        if (i2 > 255 || (str.startsWith("0") && str.length() > 1)) {
            throw new NumberFormatException();
        }
        return (byte) i2;
    }

    @NullableDecl
    private static byte[] textToNumericFormatV4(String str) {
        byte[] bArr = new byte[4];
        try {
            Iterator<String> it = IPV4_SPLITTER.split(str).iterator();
            int i2 = 0;
            while (it.hasNext()) {
                int i3 = i2 + 1;
                bArr[i2] = parseOctet(it.next());
                i2 = i3;
            }
            if (i2 == 4) {
                return bArr;
            }
            return null;
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    @NullableDecl
    private static byte[] textToNumericFormatV6(String str) {
        int size;
        int size2;
        List<String> listSplitToList = IPV6_SPLITTER.splitToList(str);
        if (listSplitToList.size() < 3 || listSplitToList.size() > 9) {
            return null;
        }
        int i2 = -1;
        for (int i3 = 1; i3 < listSplitToList.size() - 1; i3++) {
            if (listSplitToList.get(i3).length() == 0) {
                if (i2 >= 0) {
                    return null;
                }
                i2 = i3;
            }
        }
        if (i2 >= 0) {
            size2 = (listSplitToList.size() - i2) - 1;
            if (listSplitToList.get(0).length() == 0) {
                size = i2 - 1;
                if (size != 0) {
                    return null;
                }
            } else {
                size = i2;
            }
            if (((String) Iterables.getLast(listSplitToList)).length() == 0 && size2 - 1 != 0) {
                return null;
            }
        } else {
            size = listSplitToList.size();
            size2 = 0;
        }
        int i4 = 8 - (size + size2);
        if (i2 < 0 ? i4 != 0 : i4 < 1) {
            return null;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(16);
        for (int i5 = 0; i5 < size; i5++) {
            try {
                byteBufferAllocate.putShort(parseHextet(listSplitToList.get(i5)));
            } catch (NumberFormatException unused) {
                return null;
            }
        }
        for (int i6 = 0; i6 < i4; i6++) {
            byteBufferAllocate.putShort((short) 0);
        }
        while (size2 > 0) {
            byteBufferAllocate.putShort(parseHextet(listSplitToList.get(listSplitToList.size() - size2)));
            size2--;
        }
        return byteBufferAllocate.array();
    }

    public static String toAddrString(InetAddress inetAddress) {
        Preconditions.checkNotNull(inetAddress);
        if (inetAddress instanceof Inet4Address) {
            return inetAddress.getHostAddress();
        }
        Preconditions.checkArgument(inetAddress instanceof Inet6Address);
        byte[] address = inetAddress.getAddress();
        int[] iArr = new int[8];
        for (int i2 = 0; i2 < 8; i2++) {
            int i3 = i2 * 2;
            iArr[i2] = Ints.fromBytes((byte) 0, (byte) 0, address[i3], address[i3 + 1]);
        }
        compressLongestRunOfZeroes(iArr);
        return hextetsToIPv6String(iArr);
    }

    public static String toUriString(InetAddress inetAddress) {
        if (!(inetAddress instanceof Inet6Address)) {
            return toAddrString(inetAddress);
        }
        return StrPool.BRACKET_START + toAddrString(inetAddress) + StrPool.BRACKET_END;
    }
}
