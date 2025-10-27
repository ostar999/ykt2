package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;

/* loaded from: classes6.dex */
public class j {

    /* renamed from: j, reason: collision with root package name */
    private static String f18915j = "GPUGreenScreen";

    /* renamed from: a, reason: collision with root package name */
    private int f18916a;

    /* renamed from: b, reason: collision with root package name */
    private int f18917b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f18918c;

    /* renamed from: d, reason: collision with root package name */
    private w f18919d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f18920e;

    /* renamed from: f, reason: collision with root package name */
    private com.tencent.liteav.basic.opengl.k f18921f;

    /* renamed from: g, reason: collision with root package name */
    private e f18922g;

    /* renamed from: h, reason: collision with root package name */
    private p f18923h;

    /* renamed from: i, reason: collision with root package name */
    private boolean f18924i;

    /* renamed from: k, reason: collision with root package name */
    private com.tencent.liteav.basic.b.b f18925k;

    private void b() {
        TXCLog.i(f18915j, "come into destroyPlayer");
        w wVar = this.f18919d;
        if (wVar != null) {
            wVar.a();
        }
        this.f18919d = null;
        this.f18920e = false;
        this.f18924i = false;
        TXCLog.i(f18915j, "come out destroyPlayer");
    }

    private void c() {
        int i2 = this.f18917b;
        if (i2 != -1 && i2 != this.f18916a) {
            GLES20.glDeleteTextures(1, new int[]{i2}, 0);
            this.f18917b = -1;
        }
        int i3 = this.f18916a;
        if (i3 != -1) {
            GLES20.glDeleteTextures(1, new int[]{i3}, 0);
            this.f18916a = -1;
        }
    }

    public int a(int i2) {
        return i2;
    }

    public void a(com.tencent.liteav.basic.b.b bVar) {
        TXCLog.i(f18915j, "set notify");
        this.f18925k = bVar;
    }

    public void a() {
        TXCLog.i(f18915j, "come into GreenScreen destroy");
        b();
        c();
        com.tencent.liteav.basic.opengl.k kVar = this.f18921f;
        if (kVar != null) {
            kVar.d();
            this.f18921f = null;
        }
        e eVar = this.f18922g;
        if (eVar != null) {
            eVar.d();
            this.f18922g = null;
        }
        p pVar = this.f18923h;
        if (pVar != null) {
            pVar.d();
            this.f18923h = null;
        }
        this.f18918c = false;
        TXCLog.i(f18915j, "come out GreenScreen destroy");
    }
}
