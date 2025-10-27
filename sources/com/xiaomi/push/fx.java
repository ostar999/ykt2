package com.xiaomi.push;

import android.os.Build;
import com.xiaomi.push.et;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.Adler32;

/* loaded from: classes6.dex */
public class fx {

    /* renamed from: a, reason: collision with root package name */
    private int f24910a;

    /* renamed from: a, reason: collision with other field name */
    private ga f459a;

    /* renamed from: a, reason: collision with other field name */
    private OutputStream f460a;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f463a;

    /* renamed from: b, reason: collision with root package name */
    private int f24911b;

    /* renamed from: a, reason: collision with other field name */
    ByteBuffer f461a = ByteBuffer.allocate(2048);

    /* renamed from: b, reason: collision with other field name */
    private ByteBuffer f464b = ByteBuffer.allocate(4);

    /* renamed from: a, reason: collision with other field name */
    private Adler32 f462a = new Adler32();

    public fx(OutputStream outputStream, ga gaVar) {
        this.f460a = new BufferedOutputStream(outputStream);
        this.f459a = gaVar;
        TimeZone timeZone = TimeZone.getDefault();
        this.f24910a = timeZone.getRawOffset() / 3600000;
        this.f24911b = timeZone.useDaylightTime() ? 1 : 0;
    }

    public int a(fv fvVar) {
        int iC = fvVar.c();
        if (iC > 32768) {
            com.xiaomi.channel.commonutils.logger.b.m117a("Blob size=" + iC + " should be less than 32768 Drop blob chid=" + fvVar.a() + " id=" + fvVar.e());
            return 0;
        }
        this.f461a.clear();
        int i2 = iC + 8 + 4;
        if (i2 > this.f461a.capacity() || this.f461a.capacity() > 4096) {
            this.f461a = ByteBuffer.allocate(i2);
        }
        this.f461a.putShort((short) -15618);
        this.f461a.putShort((short) 5);
        this.f461a.putInt(iC);
        int iPosition = this.f461a.position();
        this.f461a = fvVar.mo429a(this.f461a);
        if (!"CONN".equals(fvVar.m428a())) {
            if (this.f463a == null) {
                this.f463a = this.f459a.m446a();
            }
            com.xiaomi.push.service.bc.a(this.f463a, this.f461a.array(), true, iPosition, iC);
        }
        this.f462a.reset();
        this.f462a.update(this.f461a.array(), 0, this.f461a.position());
        this.f464b.putInt(0, (int) this.f462a.getValue());
        this.f460a.write(this.f461a.array(), 0, this.f461a.position());
        this.f460a.write(this.f464b.array(), 0, 4);
        this.f460a.flush();
        int iPosition2 = this.f461a.position() + 4;
        com.xiaomi.channel.commonutils.logger.b.c("[Slim] Wrote {cmd=" + fvVar.m428a() + ";chid=" + fvVar.a() + ";len=" + iPosition2 + "}");
        return iPosition2;
    }

    public void a() {
        et.e eVar = new et.e();
        eVar.a(106);
        String str = Build.MODEL;
        eVar.a(str);
        eVar.b(Build.VERSION.INCREMENTAL);
        eVar.c(com.xiaomi.push.service.bi.m731a());
        eVar.b(38);
        eVar.d(this.f459a.m451b());
        eVar.e(this.f459a.mo450a());
        eVar.f(Locale.getDefault().toString());
        int i2 = Build.VERSION.SDK_INT;
        eVar.c(i2);
        byte[] bArrMo459a = this.f459a.m449a().mo459a();
        if (bArrMo459a != null) {
            eVar.a(et.b.a(bArrMo459a));
        }
        fv fvVar = new fv();
        fvVar.a(0);
        fvVar.a("CONN", (String) null);
        fvVar.a(0L, "xiaomi.com", null);
        fvVar.a(eVar.m334a(), (String) null);
        a(fvVar);
        com.xiaomi.channel.commonutils.logger.b.m117a("[slim] open conn: andver=" + i2 + " sdk=38 hash=" + com.xiaomi.push.service.bi.m731a() + " tz=" + this.f24910a + ":" + this.f24911b + " Model=" + str + " os=" + Build.VERSION.INCREMENTAL);
    }

    public void b() throws IOException {
        fv fvVar = new fv();
        fvVar.a("CLOSE", (String) null);
        a(fvVar);
        this.f460a.close();
    }
}
