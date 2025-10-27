package com.alibaba.sdk.android.httpdns.probe;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/* loaded from: classes2.dex */
public class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private IPProbeItem f2831a;

    /* renamed from: a, reason: collision with other field name */
    private a f61a;

    /* renamed from: b, reason: collision with root package name */
    private com.alibaba.sdk.android.httpdns.probe.a f2832b;
    private String host;
    private String[] ips;

    public interface a {
        Socket a();
    }

    public c(a aVar, String str, String[] strArr, IPProbeItem iPProbeItem, com.alibaba.sdk.android.httpdns.probe.a aVar2) {
        this.f61a = aVar;
        this.host = str;
        this.ips = strArr;
        this.f2831a = iPProbeItem;
        this.f2832b = aVar2;
    }

    private int a(String str, int i2) throws IOException {
        long jCurrentTimeMillis;
        Socket socketA = this.f61a.a();
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        try {
            socketA.connect(new InetSocketAddress(str, i2), 5000);
            jCurrentTimeMillis = System.currentTimeMillis();
        } catch (IOException e2) {
            e2.printStackTrace();
            jCurrentTimeMillis = Long.MAX_VALUE;
        }
        if (jCurrentTimeMillis == Long.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) (jCurrentTimeMillis - jCurrentTimeMillis2);
    }

    @Override // java.lang.Runnable
    public void run() {
        String[] strArr;
        int[] iArr = new int[this.ips.length];
        int i2 = 0;
        while (true) {
            strArr = this.ips;
            if (i2 >= strArr.length) {
                break;
            }
            iArr[i2] = a(strArr[i2], this.f2831a.getPort());
            i2++;
        }
        String[] strArrA = com.alibaba.sdk.android.httpdns.j.a.a(strArr, iArr);
        com.alibaba.sdk.android.httpdns.probe.a aVar = this.f2832b;
        if (aVar != null) {
            aVar.a(this.host, strArrA);
        }
    }
}
