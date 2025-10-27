package com.alibaba.sdk.android.httpdns.net;

import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class Inet64Util {
    static final String IPV4ONLY_HOST = "ipv4only.arpa";
    public static final int IPV4_ONLY = 1;
    public static final int IPV6_ONLY = 2;
    public static final int IP_DUAL_STACK = 3;
    public static final int IP_STACK_UNKNOWN = 0;
    static final String TAG = "Inet64Util";
    private static a helper;
    private static ScheduledExecutorService threadPool;
    static final byte[][] IPV4ONLY_IP = {new byte[]{-64, 0, 0, -86}, new byte[]{-64, 0, 0, -85}};
    static volatile String networkId = null;
    static b defaultNatPrefix = null;
    static ConcurrentHashMap<String, b> nat64PrefixMap = new ConcurrentHashMap<>();
    static ConcurrentHashMap<String, Integer> ipStackMap = new ConcurrentHashMap<>();

    public static String convertToIPv6(String str) {
        try {
            return convertToIPv6((Inet4Address) InetAddress.getByName(str));
        } catch (Exception unused) {
            return null;
        }
    }

    public static String convertToIPv6(Inet4Address inet4Address) throws Exception {
        if (inet4Address == null) {
            throw new InvalidParameterException("address in null");
        }
        b nat64Prefix = getNat64Prefix();
        if (nat64Prefix == null) {
            throw new Exception("cannot get nat64 prefix");
        }
        byte[] address = inet4Address.getAddress();
        byte[] address2 = nat64Prefix.f2818a.getAddress();
        int i2 = nat64Prefix.f2819h / 8;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int i5 = i3 + i2;
            if (i5 > 15 || i4 >= 4) {
                break;
            }
            if (i5 != 8) {
                address2[i5] = (byte) (address[i4] | address2[i5]);
                i4++;
            }
            i3++;
        }
        return InetAddress.getByAddress(address2).getHostAddress();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int detectIpStack() {
        int ipStackByInterfaces;
        try {
            ipStackByInterfaces = getIpStackByInterfaces();
        } catch (Throwable unused) {
            HttpDnsLog.e("Inet64Util[detectIpStack]error.");
            ipStackByInterfaces = 0;
        }
        HttpDnsLog.d("Inet64UtilstartIpStackDetect ip stack " + ipStackByInterfaces);
        return ipStackByInterfaces;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static b detectNat64Prefix() throws UnknownHostException {
        InetAddress byName;
        boolean z2;
        try {
            byName = InetAddress.getByName(IPV4ONLY_HOST);
        } catch (Exception e2) {
            HttpDnsLog.e("Inet64Util detectNat64Prefix " + e2.getMessage(), e2);
            byName = null;
        }
        if (byName instanceof Inet6Address) {
            HttpDnsLog.d("Inet64Util Resolved AAAA: " + byName.toString());
            byte[] address = byName.getAddress();
            if (address.length != 16) {
                return null;
            }
            int i2 = 12;
            while (true) {
                if (i2 < 0) {
                    z2 = false;
                    break;
                }
                byte b3 = address[i2];
                byte[][] bArr = IPV4ONLY_IP;
                byte[] bArr2 = bArr[0];
                if ((b3 & bArr2[0]) != 0 && address[i2 + 1] == 0 && address[i2 + 2] == 0) {
                    byte b4 = address[i2 + 3];
                    z2 = true;
                    if (b4 == bArr2[3] || b4 == bArr[1][3]) {
                        break;
                    }
                }
                i2--;
            }
            if (z2) {
                address[i2 + 3] = 0;
                address[i2 + 2] = 0;
                address[i2 + 1] = 0;
                address[i2] = 0;
                return new b(Inet6Address.getByAddress(IPV4ONLY_HOST, address, 0), i2 * 8);
            }
        } else if (byName instanceof Inet4Address) {
            HttpDnsLog.d("Inet64UtilResolved A: " + byName.toString());
        }
        return null;
    }

    private static boolean filterAddress(InetAddress inetAddress) {
        return inetAddress.isLoopbackAddress() || inetAddress.isLinkLocalAddress() || inetAddress.isAnyLocalAddress();
    }

    private static int getIpStackByInterfaces() {
        int iIntValue;
        TreeMap treeMap = new TreeMap();
        Iterator it = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
        while (true) {
            iIntValue = 0;
            if (!it.hasNext()) {
                break;
            }
            NetworkInterface networkInterface = (NetworkInterface) it.next();
            if (!networkInterface.getInterfaceAddresses().isEmpty()) {
                String displayName = networkInterface.getDisplayName();
                HttpDnsLog.d("Inet64Util find NetworkInterface:" + displayName);
                Iterator<InterfaceAddress> it2 = networkInterface.getInterfaceAddresses().iterator();
                while (it2.hasNext()) {
                    InetAddress address = it2.next().getAddress();
                    if (address instanceof Inet6Address) {
                        Inet6Address inet6Address = (Inet6Address) address;
                        if (!filterAddress(inet6Address)) {
                            HttpDnsLog.d("Inet64Util Found IPv6 address:" + inet6Address.toString());
                            iIntValue |= 2;
                        }
                    } else if (address instanceof Inet4Address) {
                        Inet4Address inet4Address = (Inet4Address) address;
                        if (!filterAddress(inet4Address) && !inet4Address.getHostAddress().startsWith("192.168.43.")) {
                            HttpDnsLog.d("Inet64Util Found IPv4 address:" + inet4Address.toString());
                            iIntValue |= 1;
                        }
                    }
                }
                if (iIntValue != 0) {
                    treeMap.put(displayName.toLowerCase(), Integer.valueOf(iIntValue));
                }
            }
        }
        if (treeMap.isEmpty()) {
            return 0;
        }
        if (treeMap.size() == 1) {
            return ((Integer) treeMap.firstEntry().getValue()).intValue();
        }
        String str = helper.c() ? "wlan" : helper.d() ? "rmnet" : null;
        if (str != null) {
            Iterator it3 = treeMap.entrySet().iterator();
            while (true) {
                if (!it3.hasNext()) {
                    break;
                }
                Map.Entry entry = (Map.Entry) it3.next();
                if (((String) entry.getKey()).startsWith(str)) {
                    iIntValue = ((Integer) entry.getValue()).intValue();
                    break;
                }
            }
        }
        return (iIntValue == 2 && treeMap.containsKey("v4-wlan0")) ? iIntValue | ((Integer) treeMap.remove("v4-wlan0")).intValue() : iIntValue;
    }

    public static b getNat64Prefix() {
        b bVar = nat64PrefixMap.get(networkId);
        return bVar == null ? defaultNatPrefix : bVar;
    }

    public static int getStackType() {
        Integer num = ipStackMap.get(networkId);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public static void init(a aVar) {
        if (helper != null) {
            return;
        }
        helper = aVar;
        threadPool = Executors.newScheduledThreadPool(2);
        try {
            defaultNatPrefix = new b((Inet6Address) InetAddress.getAllByName("64:ff9b::")[0], 96);
        } catch (UnknownHostException unused) {
        }
        networkId = aVar.i();
        startIpStackDetect();
    }

    public static boolean isIPv4OnlyNetwork() {
        Integer num = ipStackMap.get(networkId);
        return num != null && num.intValue() == 1;
    }

    public static boolean isIPv6OnlyNetwork() {
        Integer num = ipStackMap.get(networkId);
        return num != null && num.intValue() == 2;
    }

    public static void startIpStackDetect() {
        networkId = helper.i();
        if (ipStackMap.putIfAbsent(networkId, 0) != null) {
            return;
        }
        final int iDetectIpStack = detectIpStack();
        ipStackMap.put(networkId, Integer.valueOf(iDetectIpStack));
        final String str = networkId;
        if (iDetectIpStack == 2 || iDetectIpStack == 3) {
            threadPool.schedule(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.net.Inet64Util.1
                @Override // java.lang.Runnable
                public void run() {
                    b bVarDetectNat64Prefix;
                    try {
                        if (str.equals(Inet64Util.helper.i())) {
                            HttpDnsLog.d("Inet64Util startIpStackDetect double check");
                            int iDetectIpStack2 = Inet64Util.detectIpStack();
                            if (iDetectIpStack != iDetectIpStack2) {
                                Inet64Util.ipStackMap.put(str, Integer.valueOf(iDetectIpStack2));
                            }
                            if ((iDetectIpStack2 == 2 || iDetectIpStack2 == 3) && (bVarDetectNat64Prefix = Inet64Util.detectNat64Prefix()) != null) {
                                Inet64Util.nat64PrefixMap.put(str, bVarDetectNat64Prefix);
                            }
                        }
                    } catch (Exception unused) {
                    }
                }
            }, 1500L, TimeUnit.MILLISECONDS);
        }
    }
}
