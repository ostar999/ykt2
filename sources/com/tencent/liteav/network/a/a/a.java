package com.tencent.liteav.network.a.a;

import com.tencent.liteav.network.a.d;
import com.tencent.liteav.network.a.e;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes6.dex */
public final class a {
    public static InetAddress[] a() throws IOException {
        String hostAddress;
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(Runtime.getRuntime().exec("getprop").getInputStream()));
            ArrayList arrayList = new ArrayList(5);
            while (true) {
                String line = lineNumberReader.readLine();
                if (line == null) {
                    break;
                }
                int iIndexOf = line.indexOf("]: [");
                if (iIndexOf != -1) {
                    String strSubstring = line.substring(1, iIndexOf);
                    String strSubstring2 = line.substring(iIndexOf + 4, line.length() - 1);
                    if (strSubstring.endsWith(".dns") || strSubstring.endsWith(".dns1") || strSubstring.endsWith(".dns2") || strSubstring.endsWith(".dns3") || strSubstring.endsWith(".dns4")) {
                        InetAddress byName = InetAddress.getByName(strSubstring2);
                        if (byName != null && (hostAddress = byName.getHostAddress()) != null && hostAddress.length() != 0) {
                            arrayList.add(byName);
                        }
                    }
                }
            }
            if (arrayList.size() > 0) {
                return (InetAddress[]) arrayList.toArray(new InetAddress[arrayList.size()]);
            }
            return null;
        } catch (IOException e2) {
            Logger.getLogger("AndroidDnsServer").log(Level.WARNING, "Exception in findDNSByExec", (Throwable) e2);
            return null;
        }
    }

    public static InetAddress[] b() throws NoSuchMethodException, SecurityException {
        InetAddress byName;
        String hostAddress;
        try {
            Method method = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
            ArrayList arrayList = new ArrayList(5);
            String[] strArr = {"net.dns1", "net.dns2", "net.dns3", "net.dns4"};
            for (int i2 = 0; i2 < 4; i2++) {
                String str = (String) method.invoke(null, strArr[i2]);
                if (str != null && str.length() != 0 && (byName = InetAddress.getByName(str)) != null && (hostAddress = byName.getHostAddress()) != null && hostAddress.length() != 0 && !arrayList.contains(byName)) {
                    arrayList.add(byName);
                }
            }
            if (arrayList.size() > 0) {
                return (InetAddress[]) arrayList.toArray(new InetAddress[arrayList.size()]);
            }
        } catch (Exception e2) {
            Logger.getLogger("AndroidDnsServer").log(Level.WARNING, "Exception in findDNSByReflection", (Throwable) e2);
        }
        return null;
    }

    public static com.tencent.liteav.network.a.c c() {
        return new com.tencent.liteav.network.a.c() { // from class: com.tencent.liteav.network.a.a.a.1
            @Override // com.tencent.liteav.network.a.c
            public e[] a(com.tencent.liteav.network.a.b bVar, d dVar) throws NoSuchMethodException, SecurityException, IOException {
                InetAddress[] inetAddressArrB = a.b();
                if (inetAddressArrB == null) {
                    inetAddressArrB = a.a();
                }
                if (inetAddressArrB != null) {
                    return new c(inetAddressArrB[0]).a(bVar, dVar);
                }
                throw new IOException("cant get local dns server");
            }
        };
    }
}
