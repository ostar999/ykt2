package com.xiaomi.push;

import android.text.TextUtils;
import com.xiaomi.push.et;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.Adler32;

/* loaded from: classes6.dex */
class fw {

    /* renamed from: a, reason: collision with other field name */
    private ga f453a;

    /* renamed from: a, reason: collision with other field name */
    private InputStream f454a;

    /* renamed from: a, reason: collision with other field name */
    private volatile boolean f457a;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f458a;

    /* renamed from: a, reason: collision with other field name */
    private ByteBuffer f455a = ByteBuffer.allocate(2048);

    /* renamed from: b, reason: collision with root package name */
    private ByteBuffer f24909b = ByteBuffer.allocate(4);

    /* renamed from: a, reason: collision with other field name */
    private Adler32 f456a = new Adler32();

    /* renamed from: a, reason: collision with root package name */
    private fy f24908a = new fy();

    public fw(InputStream inputStream, ga gaVar) {
        this.f454a = new BufferedInputStream(inputStream);
        this.f453a = gaVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00cf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.nio.ByteBuffer a() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.fw.a():java.nio.ByteBuffer");
    }

    private void a(ByteBuffer byteBuffer, int i2) throws IOException {
        int iPosition = byteBuffer.position();
        do {
            int i3 = this.f454a.read(byteBuffer.array(), iPosition, i2);
            if (i3 == -1) {
                throw new EOFException();
            }
            i2 -= i3;
            iPosition += i3;
        } while (i2 > 0);
        byteBuffer.position(iPosition);
    }

    private void c() throws IOException {
        String string;
        StringBuilder sb;
        boolean z2 = false;
        this.f457a = false;
        fv fvVarM436a = m436a();
        if ("CONN".equals(fvVarM436a.m428a())) {
            et.f fVarA = et.f.a(fvVarM436a.m432a());
            if (fVarA.m391a()) {
                this.f453a.a(fVarA.m390a());
                z2 = true;
            }
            if (fVarA.c()) {
                et.b bVarM389a = fVarA.m389a();
                fv fvVar = new fv();
                fvVar.a("SYNC", "CONF");
                fvVar.a(bVarM389a.m334a(), (String) null);
                this.f453a.a(fvVar);
            }
            com.xiaomi.channel.commonutils.logger.b.m117a("[Slim] CONN: host = " + fVarA.m392b());
        }
        if (!z2) {
            com.xiaomi.channel.commonutils.logger.b.m117a("[Slim] Invalid CONN");
            throw new IOException("Invalid Connection");
        }
        this.f458a = this.f453a.m446a();
        while (!this.f457a) {
            fv fvVarM436a2 = m436a();
            this.f453a.d();
            short sM430a = fvVarM436a2.m430a();
            if (sM430a != 1) {
                if (sM430a != 2) {
                    if (sM430a != 3) {
                        string = "[Slim] unknow blob type " + ((int) fvVarM436a2.m430a());
                    } else {
                        try {
                            this.f453a.b(this.f24908a.a(fvVarM436a2.m432a(), this.f453a));
                        } catch (Exception e2) {
                            e = e2;
                            sb = new StringBuilder();
                            sb.append("[Slim] Parse packet from Blob chid=");
                            sb.append(fvVarM436a2.a());
                            sb.append("; Id=");
                            sb.append(fvVarM436a2.e());
                            sb.append(" failure:");
                            sb.append(e.getMessage());
                            string = sb.toString();
                            com.xiaomi.channel.commonutils.logger.b.m117a(string);
                        }
                    }
                } else if ("SECMSG".equals(fvVarM436a2.m428a()) && ((fvVarM436a2.a() == 2 || fvVarM436a2.a() == 3) && TextUtils.isEmpty(fvVarM436a2.m434b()))) {
                    try {
                        this.f453a.b(this.f24908a.a(fvVarM436a2.m433a(com.xiaomi.push.service.at.a().a(Integer.valueOf(fvVarM436a2.a()).toString(), fvVarM436a2.g()).f25598h), this.f453a));
                    } catch (Exception e3) {
                        e = e3;
                        sb = new StringBuilder();
                        sb.append("[Slim] Parse packet from Blob chid=");
                        sb.append(fvVarM436a2.a());
                        sb.append("; Id=");
                        sb.append(fvVarM436a2.e());
                        sb.append(" failure:");
                        sb.append(e.getMessage());
                        string = sb.toString();
                        com.xiaomi.channel.commonutils.logger.b.m117a(string);
                    }
                }
                com.xiaomi.channel.commonutils.logger.b.m117a(string);
            }
            this.f453a.a(fvVarM436a2);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public fv m436a() throws IOException {
        int iPosition;
        try {
            ByteBuffer byteBufferA = a();
            iPosition = byteBufferA.position();
            try {
                byteBufferA.flip();
                byteBufferA.position(8);
                fv fzVar = iPosition == 8 ? new fz() : fv.a(byteBufferA.slice());
                com.xiaomi.channel.commonutils.logger.b.c("[Slim] Read {cmd=" + fzVar.m428a() + ";chid=" + fzVar.a() + ";len=" + iPosition + "}");
                return fzVar;
            } catch (IOException e2) {
                e = e2;
                if (iPosition == 0) {
                    iPosition = this.f455a.position();
                }
                StringBuilder sb = new StringBuilder();
                sb.append("[Slim] read Blob [");
                byte[] bArrArray = this.f455a.array();
                if (iPosition > 128) {
                    iPosition = 128;
                }
                sb.append(af.a(bArrArray, 0, iPosition));
                sb.append("] Err:");
                sb.append(e.getMessage());
                com.xiaomi.channel.commonutils.logger.b.m117a(sb.toString());
                throw e;
            }
        } catch (IOException e3) {
            e = e3;
            iPosition = 0;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m437a() throws IOException {
        try {
            c();
        } catch (IOException e2) {
            if (!this.f457a) {
                throw e2;
            }
        }
    }

    public void b() {
        this.f457a = true;
    }
}
