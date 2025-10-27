package com.tencent.liteav.videobase.e;

import android.media.MediaCodec;
import com.google.common.base.Ascii;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public byte[] f19989a;

    /* renamed from: b, reason: collision with root package name */
    public c f19990b;

    /* renamed from: c, reason: collision with root package name */
    public d f19991c;

    /* renamed from: d, reason: collision with root package name */
    public int f19992d;

    /* renamed from: e, reason: collision with root package name */
    public long f19993e;

    /* renamed from: f, reason: collision with root package name */
    public long f19994f;

    /* renamed from: g, reason: collision with root package name */
    public long f19995g = 0;

    /* renamed from: h, reason: collision with root package name */
    public long f19996h = 0;

    /* renamed from: i, reason: collision with root package name */
    public long f19997i = 0;

    /* renamed from: j, reason: collision with root package name */
    public long f19998j = 0;

    /* renamed from: k, reason: collision with root package name */
    public MediaCodec.BufferInfo f19999k = null;

    public boolean a() {
        c cVar = this.f19990b;
        return cVar != null && cVar.b();
    }

    public boolean b() {
        d dVar = this.f19991c;
        return dVar == d.H264_BASELINE_RPS || dVar == d.H264_MAIN_RPS || dVar == d.H264_HIGH_RPS;
    }

    public boolean c() {
        return this.f19991c == d.H265;
    }

    public void d() {
        if (this.f19989a == null) {
            return;
        }
        c cVar = this.f19990b;
        if (cVar != null && cVar != c.UNKNOWN) {
            return;
        }
        int iA = 0;
        while (true) {
            iA = a(iA, this.f19989a);
            if (iA == 0 || iA >= this.f19989a.length) {
                return;
            }
            c cVar2 = c.UNKNOWN;
            c cVarB = c() ? b(this.f19989a, iA) : !c() ? a(this.f19989a, iA) : cVar2;
            c cVar3 = this.f19990b;
            if (cVar3 == null || cVar3 == cVar2 || cVarB == c.IDR) {
                this.f19990b = cVarB;
            }
            c cVar4 = this.f19990b;
            if (cVar4 != c.SPS && cVar4 != c.PPS && cVar4 != c.VPS) {
                return;
            }
        }
    }

    private c a(byte[] bArr, int i2) {
        int i3 = bArr[i2] & Ascii.US;
        return i3 != 5 ? i3 != 6 ? i3 != 7 ? i3 != 8 ? c.UNKNOWN : c.PPS : c.SPS : c.SEI : c.IDR;
    }

    private c b(byte[] bArr, int i2) {
        int i3 = (bArr[i2] & 126) >> 1;
        if (i3 == 39) {
            return c.SEI;
        }
        switch (i3) {
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                return c.IDR;
            default:
                switch (i3) {
                    case 32:
                        return c.VPS;
                    case 33:
                        return c.SPS;
                    case 34:
                        return c.PPS;
                    default:
                        return c.UNKNOWN;
                }
        }
    }

    private int a(int i2, byte[] bArr) {
        while (true) {
            int i3 = i2 + 3;
            if (i3 >= bArr.length) {
                return 0;
            }
            byte b3 = bArr[i2];
            if (b3 == 0 && bArr[i2 + 1] == 0 && bArr[i2 + 2] == 0 && bArr[i3] == 1) {
                return i2 + 4;
            }
            if (b3 == 0 && bArr[i2 + 1] == 0 && bArr[i2 + 2] == 1) {
                return i3;
            }
            i2++;
        }
    }
}
