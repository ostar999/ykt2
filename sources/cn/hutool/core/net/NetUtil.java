package cn.hutool.core.net;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.EnumerationIter;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.JNDIUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Authenticator;
import java.net.DatagramSocket;
import java.net.HttpCookie;
import java.net.IDN;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

/* loaded from: classes.dex */
public class NetUtil {
    public static final String LOCAL_IP = "127.0.0.1";
    public static final int PORT_RANGE_MAX = 65535;
    public static final int PORT_RANGE_MIN = 1024;
    public static String localhostName;

    public static String bigIntegerToIPv6(BigInteger bigInteger) {
        try {
            return InetAddress.getByAddress(bigInteger.toByteArray()).toString().substring(1);
        } catch (UnknownHostException unused) {
            return null;
        }
    }

    public static InetSocketAddress buildInetSocketAddress(String str, int i2) throws NumberFormatException {
        if (CharSequenceUtil.isBlank(str)) {
            str = "127.0.0.1";
        }
        int iIndexOf = str.indexOf(":");
        if (iIndexOf != -1) {
            String strSubstring = str.substring(0, iIndexOf);
            i2 = Integer.parseInt(str.substring(iIndexOf + 1));
            str = strSubstring;
        }
        return new InetSocketAddress(str, i2);
    }

    public static InetSocketAddress createAddress(String str, int i2) {
        return CharSequenceUtil.isBlank(str) ? new InetSocketAddress(i2) : new InetSocketAddress(str, i2);
    }

    public static List<String> getDnsInfo(String str, String... strArr) {
        Attributes attributes = JNDIUtil.getAttributes(CharSequenceUtil.addPrefixIfNot(str, "dns:"), strArr);
        ArrayList arrayList = new ArrayList();
        Iterator it = new EnumerationIter(attributes.getAll()).iterator();
        while (it.hasNext()) {
            try {
                arrayList.add((String) ((Attribute) it.next()).get());
            } catch (NamingException unused) {
            }
        }
        return arrayList;
    }

    public static byte[] getHardwareAddress(InetAddress inetAddress) throws SocketException {
        if (inetAddress == null) {
            return null;
        }
        try {
            NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(inetAddress);
            if (byInetAddress != null) {
                return byInetAddress.getHardwareAddress();
            }
            return null;
        } catch (SocketException e2) {
            throw new UtilException(e2);
        }
    }

    public static String getIpByHost(String str) {
        try {
            return InetAddress.getByName(str).getHostAddress();
        } catch (UnknownHostException unused) {
            return str;
        }
    }

    public static byte[] getLocalHardwareAddress() {
        return getHardwareAddress(getLocalhost());
    }

    public static String getLocalHostName() {
        if (CharSequenceUtil.isNotBlank(localhostName)) {
            return localhostName;
        }
        InetAddress localhost = getLocalhost();
        if (localhost != null) {
            String hostName = localhost.getHostName();
            if (CharSequenceUtil.isEmpty(hostName)) {
                hostName = localhost.getHostAddress();
            }
            localhostName = hostName;
        }
        return localhostName;
    }

    public static String getLocalMacAddress() {
        return getMacAddress(getLocalhost());
    }

    public static InetAddress getLocalhost() {
        LinkedHashSet<InetAddress> linkedHashSetLocalAddressList = localAddressList(new Filter() { // from class: i0.e
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return NetUtil.lambda$getLocalhost$2((InetAddress) obj);
            }
        });
        if (CollUtil.isNotEmpty((Collection<?>) linkedHashSetLocalAddressList)) {
            Iterator<InetAddress> it = linkedHashSetLocalAddressList.iterator();
            InetAddress inetAddress = null;
            while (it.hasNext()) {
                InetAddress next = it.next();
                if (!next.isSiteLocalAddress()) {
                    return next;
                }
                if (inetAddress == null) {
                    inetAddress = next;
                }
            }
            if (inetAddress != null) {
                return inetAddress;
            }
        }
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException unused) {
            return null;
        }
    }

    public static String getLocalhostStr() {
        InetAddress localhost = getLocalhost();
        if (localhost != null) {
            return localhost.getHostAddress();
        }
        return null;
    }

    public static String getMacAddress(InetAddress inetAddress) {
        return getMacAddress(inetAddress, "-");
    }

    public static String getMultistageReverseProxyIp(String str) {
        if (str == null || CharSequenceUtil.indexOf(str, ',') <= 0) {
            return str;
        }
        for (String str2 : CharSequenceUtil.splitTrim((CharSequence) str, ',')) {
            if (!isUnknown(str2)) {
                return str2;
            }
        }
        return str;
    }

    public static NetworkInterface getNetworkInterface(String str) throws SocketException {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (networkInterfaceNextElement != null && str.equals(networkInterfaceNextElement.getName())) {
                    return networkInterfaceNextElement;
                }
            }
        } catch (SocketException unused) {
        }
        return null;
    }

    public static Collection<NetworkInterface> getNetworkInterfaces() throws SocketException {
        try {
            return CollUtil.addAll((Collection) new ArrayList(), (Enumeration) NetworkInterface.getNetworkInterfaces());
        } catch (SocketException unused) {
            return null;
        }
    }

    public static int getUsableLocalPort() {
        return getUsableLocalPort(1024);
    }

    public static TreeSet<Integer> getUsableLocalPorts(int i2, int i3, int i4) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        int i5 = 0;
        while (true) {
            i5++;
            if (i5 > i2 + 100 || treeSet.size() >= i2) {
                break;
            }
            treeSet.add(Integer.valueOf(getUsableLocalPort(i3, i4)));
        }
        if (treeSet.size() == i2) {
            return treeSet;
        }
        throw new UtilException("Could not find {} available  ports in the range [{}, {}]", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
    }

    public static String hideIpPart(String str) {
        StringBuilder sbBuilder = StrUtil.builder(str.length());
        sbBuilder.append((CharSequence) str, 0, str.lastIndexOf(StrPool.DOT) + 1);
        sbBuilder.append("*");
        return sbBuilder.toString();
    }

    public static String idnToASCII(String str) {
        return IDN.toASCII(str);
    }

    public static long ipv4ToLong(String str) {
        return Ipv4Util.ipv4ToLong(str);
    }

    public static BigInteger ipv6ToBigInteger(String str) throws UnknownHostException {
        try {
            InetAddress byName = InetAddress.getByName(str);
            if (byName instanceof Inet6Address) {
                return new BigInteger(1, byName.getAddress());
            }
            return null;
        } catch (UnknownHostException unused) {
            return null;
        }
    }

    @Deprecated
    public static BigInteger ipv6ToBitInteger(String str) {
        return ipv6ToBigInteger(str);
    }

    public static boolean isInRange(String str, String str2) {
        int iLastIndexOf = str2.lastIndexOf("/");
        if (iLastIndexOf >= 0) {
            long j2 = (-1) << (32 - Integer.parseInt(str2.substring(iLastIndexOf + 1)));
            return (ipv4ToLong(str) & j2) == (ipv4ToLong(str2.substring(0, iLastIndexOf)) & j2);
        }
        throw new IllegalArgumentException("Invalid cidr: " + str2);
    }

    public static boolean isInnerIP(String str) {
        return Ipv4Util.isInnerIP(str);
    }

    public static boolean isOpen(InetSocketAddress inetSocketAddress, int i2) throws IOException {
        try {
            Socket socket = new Socket();
            try {
                socket.connect(inetSocketAddress, i2);
                socket.close();
                return true;
            } finally {
            }
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isUnknown(String str) {
        return CharSequenceUtil.isBlank(str) || "unknown".equalsIgnoreCase(str);
    }

    public static boolean isUsableLocalPort(int i2) throws IOException {
        if (!isValidPort(i2)) {
            return false;
        }
        try {
            ServerSocket serverSocket = new ServerSocket(i2);
            try {
                serverSocket.setReuseAddress(true);
                serverSocket.close();
                try {
                    DatagramSocket datagramSocket = new DatagramSocket(i2);
                    try {
                        datagramSocket.setReuseAddress(true);
                        datagramSocket.close();
                        return true;
                    } finally {
                    }
                } catch (IOException unused) {
                    return false;
                }
            } finally {
            }
        } catch (IOException unused2) {
            return false;
        }
    }

    public static boolean isValidPort(int i2) {
        return i2 >= 0 && i2 <= 65535;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getLocalhost$2(InetAddress inetAddress) {
        return !inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$localIpv4s$0(InetAddress inetAddress) {
        return inetAddress instanceof Inet4Address;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$localIpv6s$1(InetAddress inetAddress) {
        return inetAddress instanceof Inet6Address;
    }

    public static LinkedHashSet<InetAddress> localAddressList(Filter<InetAddress> filter) {
        return localAddressList(null, filter);
    }

    public static LinkedHashSet<String> localIps() {
        return toIpList(localAddressList(null));
    }

    public static LinkedHashSet<String> localIpv4s() {
        return toIpList(localAddressList(new Filter() { // from class: i0.d
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return NetUtil.lambda$localIpv4s$0((InetAddress) obj);
            }
        }));
    }

    public static LinkedHashSet<String> localIpv6s() {
        return toIpList(localAddressList(new Filter() { // from class: i0.c
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return NetUtil.lambda$localIpv6s$1((InetAddress) obj);
            }
        }));
    }

    public static String longToIpv4(long j2) {
        return Ipv4Util.longToIpv4(j2);
    }

    public static void netCat(String str, int i2, boolean z2, ByteBuffer byteBuffer) throws IOException, IORuntimeException {
        try {
            SocketChannel socketChannelOpen = SocketChannel.open(createAddress(str, i2));
            try {
                socketChannelOpen.configureBlocking(z2);
                socketChannelOpen.write(byteBuffer);
                socketChannelOpen.close();
            } finally {
            }
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static List<HttpCookie> parseCookies(String str) {
        return CharSequenceUtil.isBlank(str) ? Collections.emptyList() : HttpCookie.parse(str);
    }

    public static boolean ping(String str) {
        return ping(str, 200);
    }

    public static void setGlobalAuthenticator(String str, char[] cArr) {
        setGlobalAuthenticator(new UserPassAuthenticator(str, cArr));
    }

    public static String toAbsoluteUrl(String str, String str2) {
        try {
            return new URL(new URL(str), str2).toString();
        } catch (Exception e2) {
            throw new UtilException(e2, "To absolute url [{}] base [{}] error!", str2, str);
        }
    }

    public static LinkedHashSet<String> toIpList(Set<InetAddress> set) {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        Iterator<InetAddress> it = set.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(it.next().getHostAddress());
        }
        return linkedHashSet;
    }

    public static String getMacAddress(InetAddress inetAddress, String str) {
        byte[] hardwareAddress;
        if (inetAddress == null || (hardwareAddress = getHardwareAddress(inetAddress)) == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < hardwareAddress.length; i2++) {
            if (i2 != 0) {
                sb.append(str);
            }
            String hexString = Integer.toHexString(hardwareAddress[i2] & 255);
            if (hexString.length() == 1) {
                hexString = 0 + hexString;
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static int getUsableLocalPort(int i2) {
        return getUsableLocalPort(i2, 65535);
    }

    public static String hideIpPart(long j2) {
        return hideIpPart(longToIpv4(j2));
    }

    public static LinkedHashSet<InetAddress> localAddressList(Filter<NetworkInterface> filter, Filter<InetAddress> filter2) throws SocketException {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces == null) {
                throw new UtilException("Get network interface error!");
            }
            LinkedHashSet<InetAddress> linkedHashSet = new LinkedHashSet<>();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (filter == null || filter.accept(networkInterfaceNextElement)) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddressNextElement = inetAddresses.nextElement();
                        if (inetAddressNextElement != null && (filter2 == null || filter2.accept(inetAddressNextElement))) {
                            linkedHashSet.add(inetAddressNextElement);
                        }
                    }
                }
            }
            return linkedHashSet;
        } catch (SocketException e2) {
            throw new UtilException(e2);
        }
    }

    public static boolean ping(String str, int i2) {
        try {
            return InetAddress.getByName(str).isReachable(i2);
        } catch (Exception unused) {
            return false;
        }
    }

    public static void setGlobalAuthenticator(Authenticator authenticator) {
        Authenticator.setDefault(authenticator);
    }

    public static int getUsableLocalPort(int i2, int i3) {
        int i4 = i3 + 1;
        for (int i5 = i2; i5 < i4; i5++) {
            int iRandomInt = RandomUtil.randomInt(i2, i4);
            if (isUsableLocalPort(iRandomInt)) {
                return iRandomInt;
            }
        }
        throw new UtilException("Could not find an available port in the range [{}, {}] after {} attempts", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i3 - i2));
    }

    public static void netCat(String str, int i2, byte[] bArr) throws IOException, IORuntimeException {
        try {
            try {
                Socket socket = new Socket(str, i2);
                try {
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(bArr);
                    outputStream.flush();
                    socket.close();
                    IoUtil.close((Closeable) outputStream);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        try {
                            socket.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                        throw th2;
                    }
                }
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } catch (Throwable th4) {
            IoUtil.close((Closeable) null);
            throw th4;
        }
    }
}
