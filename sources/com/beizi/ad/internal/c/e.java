package com.beizi.ad.internal.c;

import android.text.TextUtils;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/* loaded from: classes2.dex */
class e extends l {

    /* renamed from: a, reason: collision with root package name */
    private final h f4074a;

    /* renamed from: b, reason: collision with root package name */
    private final com.beizi.ad.internal.c.a.b f4075b;

    /* renamed from: c, reason: collision with root package name */
    private b f4076c;

    public e(h hVar, com.beizi.ad.internal.c.a.b bVar) {
        super(hVar, bVar);
        this.f4075b = bVar;
        this.f4074a = hVar;
    }

    private String b(d dVar) throws IOException, m {
        String strC = this.f4074a.c();
        boolean z2 = !TextUtils.isEmpty(strC);
        int iA = this.f4075b.d() ? this.f4075b.a() : this.f4074a.a();
        boolean z3 = iA >= 0;
        boolean z4 = dVar.f4073c;
        long j2 = iA;
        if (z4) {
            j2 -= dVar.f4072b;
        }
        boolean z5 = z3 && z4;
        StringBuilder sb = new StringBuilder();
        sb.append(dVar.f4073c ? "HTTP/1.1 206 PARTIAL CONTENT\n" : "HTTP/1.1 200 OK\n");
        sb.append("Accept-Ranges: bytes\n");
        sb.append(z3 ? String.format("Content-Length: %d\n", Long.valueOf(j2)) : "");
        sb.append(z5 ? String.format("Content-Range: bytes %d-%d/%d\n", Long.valueOf(dVar.f4072b), Integer.valueOf(iA - 1), Integer.valueOf(iA)) : "");
        sb.append(z2 ? String.format("Content-Type: %s\n", strC) : "");
        sb.append("\n");
        return sb.toString();
    }

    public void a(b bVar) {
        this.f4076c = bVar;
    }

    public void a(d dVar, Socket socket) throws IOException, m {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        bufferedOutputStream.write(b(dVar).getBytes("UTF-8"));
        long j2 = dVar.f4072b;
        if (a(dVar)) {
            a(bufferedOutputStream, j2);
        } else {
            b(bufferedOutputStream, j2);
        }
    }

    private boolean a(d dVar) throws m {
        int iA = this.f4074a.a();
        return ((iA > 0) && dVar.f4073c && ((float) dVar.f4072b) > ((float) this.f4075b.a()) + (((float) iA) * 0.2f)) ? false : true;
    }

    private void a(OutputStream outputStream, long j2) throws IOException, m {
        byte[] bArr = new byte[8192];
        while (true) {
            int iA = a(bArr, j2, 8192);
            if (iA != -1) {
                outputStream.write(bArr, 0, iA);
                j2 += iA;
            } else {
                outputStream.flush();
                return;
            }
        }
    }

    private void b(OutputStream outputStream, long j2) throws IOException, m {
        h hVar = new h(this.f4074a);
        try {
            hVar.a((int) j2);
            byte[] bArr = new byte[8192];
            while (true) {
                int iA = hVar.a(bArr);
                if (iA != -1) {
                    outputStream.write(bArr, 0, iA);
                } else {
                    outputStream.flush();
                    return;
                }
            }
        } finally {
            hVar.b();
        }
    }

    @Override // com.beizi.ad.internal.c.l
    public void a(int i2) {
        b bVar = this.f4076c;
        if (bVar != null) {
            bVar.a(this.f4075b.f4052a, this.f4074a.d(), i2);
        }
    }
}
