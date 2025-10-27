package com.tencent.liteav.network.a.a;

import com.tencent.liteav.network.a.d;
import com.tencent.liteav.network.a.e;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

/* loaded from: classes6.dex */
public final class c implements com.tencent.liteav.network.a.c {

    /* renamed from: b, reason: collision with root package name */
    private static final Random f19501b = new Random();

    /* renamed from: a, reason: collision with root package name */
    final InetAddress f19502a;

    /* renamed from: c, reason: collision with root package name */
    private final int f19503c;

    public c(InetAddress inetAddress) {
        this(inetAddress, 10);
    }

    @Override // com.tencent.liteav.network.a.c
    public e[] a(com.tencent.liteav.network.a.b bVar, d dVar) throws Throwable {
        int iNextInt;
        Random random = f19501b;
        synchronized (random) {
            iNextInt = random.nextInt() & 255;
        }
        byte[] bArrA = a(b.a(bVar.f19504a, iNextInt));
        if (bArrA != null) {
            return b.a(bArrA, iNextInt, bVar.f19504a);
        }
        throw new com.tencent.liteav.network.a.a(bVar.f19504a, "cant get answer");
    }

    public c(InetAddress inetAddress, int i2) {
        this.f19502a = inetAddress;
        this.f19503c = i2;
    }

    private byte[] a(byte[] bArr) throws Throwable {
        DatagramSocket datagramSocket = null;
        try {
            DatagramSocket datagramSocket2 = new DatagramSocket();
            try {
                DatagramPacket datagramPacket = new DatagramPacket(bArr, bArr.length, this.f19502a, 53);
                datagramSocket2.setSoTimeout(this.f19503c * 1000);
                datagramSocket2.send(datagramPacket);
                DatagramPacket datagramPacket2 = new DatagramPacket(new byte[1500], 1500);
                datagramSocket2.receive(datagramPacket2);
                byte[] data = datagramPacket2.getData();
                datagramSocket2.close();
                return data;
            } catch (Throwable th) {
                th = th;
                datagramSocket = datagramSocket2;
                if (datagramSocket != null) {
                    datagramSocket.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
