package com.tencent.liteav.muxer;

import android.content.Context;
import android.media.MediaFormat;
import com.tencent.liteav.basic.log.TXCLog;

/* loaded from: classes6.dex */
public class c implements a {

    /* renamed from: a, reason: collision with root package name */
    private int f19427a;

    /* renamed from: b, reason: collision with root package name */
    private a f19428b;

    public c(Context context, int i2) {
        this.f19427a = 0;
        if (i2 == 0) {
            this.f19427a = 0;
            this.f19428b = new d();
            TXCLog.i("TXCMP4Muxer", "TXCMP4Muxer: use sw model ");
            return;
        }
        if (i2 == 1) {
            this.f19427a = 1;
            this.f19428b = new b();
            TXCLog.i("TXCMP4Muxer", "TXCMP4Muxer: use hw model ");
            return;
        }
        boolean zA = a(context);
        TXCLog.i("TXCMP4Muxer", getClass().getName() + "  useSW:" + zA);
        if (zA) {
            this.f19427a = 0;
            this.f19428b = new d();
            TXCLog.i("TXCMP4Muxer", "TXCMP4Muxer: use sw model ");
        } else {
            this.f19427a = 1;
            this.f19428b = new b();
            TXCLog.i("TXCMP4Muxer", "TXCMP4Muxer: use hw model ");
        }
    }

    public static boolean a(Context context) {
        com.tencent.liteav.basic.c.c.a().a(context);
        return com.tencent.liteav.basic.c.c.a().d() == 1;
    }

    @Override // com.tencent.liteav.muxer.a
    public void b(MediaFormat mediaFormat) {
        this.f19428b.b(mediaFormat);
    }

    @Override // com.tencent.liteav.muxer.a
    public boolean c() {
        return this.f19428b.c();
    }

    @Override // com.tencent.liteav.muxer.a
    public void b(byte[] bArr, int i2, int i3, long j2, int i4) {
        this.f19428b.b(bArr, i2, i3, j2, i4);
    }

    @Override // com.tencent.liteav.muxer.a
    public void a(MediaFormat mediaFormat) {
        this.f19428b.a(mediaFormat);
    }

    @Override // com.tencent.liteav.muxer.a
    public int b() {
        return this.f19428b.b();
    }

    @Override // com.tencent.liteav.muxer.a
    public void a(String str) {
        TXCLog.i("TXCMP4Muxer", "TXCMP4Muxer : setTargetPath ():" + str);
        this.f19428b.a(str);
    }

    @Override // com.tencent.liteav.muxer.a
    public void a(byte[] bArr, int i2, int i3, long j2, int i4) {
        this.f19428b.a(bArr, i2, i3, j2, i4);
    }

    @Override // com.tencent.liteav.muxer.a
    public int a() {
        return this.f19428b.a();
    }
}
