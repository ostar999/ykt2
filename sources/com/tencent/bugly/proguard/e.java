package com.tencent.bugly.proguard;

import cn.hutool.core.text.StrPool;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class e extends d {

    /* renamed from: h, reason: collision with root package name */
    static HashMap<String, byte[]> f17820h;

    /* renamed from: i, reason: collision with root package name */
    static HashMap<String, HashMap<String, byte[]>> f17821i;

    /* renamed from: g, reason: collision with root package name */
    protected g f17822g;

    /* renamed from: j, reason: collision with root package name */
    private int f17823j;

    public e() {
        g gVar = new g();
        this.f17822g = gVar;
        this.f17823j = 0;
        gVar.f17829a = (short) 2;
    }

    @Override // com.tencent.bugly.proguard.d, com.tencent.bugly.proguard.c
    public final <T> void a(String str, T t2) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, UnsupportedEncodingException {
        if (str.startsWith(StrPool.DOT)) {
            throw new IllegalArgumentException("put name can not startwith . , now is ".concat(str));
        }
        super.a(str, (String) t2);
    }

    @Override // com.tencent.bugly.proguard.d
    public final void b() {
        super.b();
        this.f17822g.f17829a = (short) 3;
    }

    public final void c(String str) {
        this.f17822g.f17834f = str;
    }

    public final void c() {
        this.f17822g.f17832d = 1;
    }

    public final void b(String str) {
        this.f17822g.f17833e = str;
    }

    @Override // com.tencent.bugly.proguard.d, com.tencent.bugly.proguard.c
    public final byte[] a() throws UnsupportedEncodingException {
        g gVar = this.f17822g;
        if (gVar.f17829a == 2) {
            if (!gVar.f17833e.equals("")) {
                if (this.f17822g.f17834f.equals("")) {
                    throw new IllegalArgumentException("funcName can not is null");
                }
            } else {
                throw new IllegalArgumentException("servantName can not is null");
            }
        } else {
            if (gVar.f17833e == null) {
                gVar.f17833e = "";
            }
            if (gVar.f17834f == null) {
                gVar.f17834f = "";
            }
        }
        l lVar = new l(0);
        lVar.a(this.f17814c);
        if (this.f17822g.f17829a == 2) {
            lVar.a((Map) this.f17812a, 0);
        } else {
            lVar.a((Map) ((d) this).f17817e, 0);
        }
        this.f17822g.f17835g = n.a(lVar.f17845a);
        l lVar2 = new l(0);
        lVar2.a(this.f17814c);
        this.f17822g.a(lVar2);
        byte[] bArrA = n.a(lVar2.f17845a);
        int length = bArrA.length + 4;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length);
        byteBufferAllocate.putInt(length).put(bArrA).flip();
        return byteBufferAllocate.array();
    }

    @Override // com.tencent.bugly.proguard.d, com.tencent.bugly.proguard.c
    public final void a(byte[] bArr) {
        if (bArr.length >= 4) {
            try {
                k kVar = new k(bArr, (byte) 0);
                kVar.a(this.f17814c);
                this.f17822g.a(kVar);
                g gVar = this.f17822g;
                if (gVar.f17829a == 3) {
                    k kVar2 = new k(gVar.f17835g);
                    kVar2.a(this.f17814c);
                    if (f17820h == null) {
                        HashMap<String, byte[]> map = new HashMap<>();
                        f17820h = map;
                        map.put("", new byte[0]);
                    }
                    ((d) this).f17817e = kVar2.a((Map) f17820h, 0, false);
                    return;
                }
                k kVar3 = new k(gVar.f17835g);
                kVar3.a(this.f17814c);
                if (f17821i == null) {
                    f17821i = new HashMap<>();
                    HashMap<String, byte[]> map2 = new HashMap<>();
                    map2.put("", new byte[0]);
                    f17821i.put("", map2);
                }
                this.f17812a = kVar3.a((Map) f17821i, 0, false);
                this.f17813b = new HashMap<>();
                return;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
        throw new IllegalArgumentException("decode package must include size head");
    }
}
