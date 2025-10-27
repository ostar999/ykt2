package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.yikaobang.yixue.R2;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class g extends com.tencent.liteav.basic.opengl.j {

    /* renamed from: r, reason: collision with root package name */
    protected List<com.tencent.liteav.basic.opengl.j> f18907r;

    /* renamed from: s, reason: collision with root package name */
    protected List<com.tencent.liteav.basic.opengl.j> f18908s;

    /* renamed from: t, reason: collision with root package name */
    private int[] f18909t;

    /* renamed from: u, reason: collision with root package name */
    private int[] f18910u;

    /* renamed from: v, reason: collision with root package name */
    private com.tencent.liteav.basic.opengl.j f18911v;

    @Override // com.tencent.liteav.basic.opengl.j
    public void a(int i2, int i3) {
        if (this.f18596e == i2 && this.f18597f == i3) {
            return;
        }
        if (this.f18909t != null) {
            f();
        }
        super.a(i2, i3);
        int size = this.f18908s.size();
        for (int i4 = 0; i4 < size; i4++) {
            this.f18908s.get(i4).a(i2, i3);
        }
        this.f18911v.a(i2, i3);
        List<com.tencent.liteav.basic.opengl.j> list = this.f18908s;
        if (list == null || list.size() <= 0) {
            return;
        }
        this.f18908s.size();
        this.f18909t = new int[2];
        this.f18910u = new int[2];
        for (int i5 = 0; i5 < 2; i5++) {
            GLES20.glGenFramebuffers(1, this.f18909t, i5);
            GLES20.glGenTextures(1, this.f18910u, i5);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, this.f18910u[i5]);
            GLES20.glTexImage2D(R2.attr.tab_indicator_height, 0, R2.dimen.dm_200, i2, i3, 0, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, null);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, 10240, 9729.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_day, 9729.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_night, 33071.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_select_day, 33071.0f);
            GLES20.glBindFramebuffer(36160, this.f18909t[i5]);
            GLES20.glFramebufferTexture2D(36160, 36064, R2.attr.tab_indicator_height, this.f18910u[i5], 0);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
            GLES20.glBindFramebuffer(36160, 0);
        }
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public boolean b() {
        boolean zB = super.b();
        if (zB) {
            for (com.tencent.liteav.basic.opengl.j jVar : this.f18907r) {
                jVar.a();
                if (!jVar.n()) {
                    break;
                }
            }
            zB = this.f18911v.a();
        }
        return zB && GLES20.glGetError() == 0;
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void e() {
        super.e();
        Iterator<com.tencent.liteav.basic.opengl.j> it = this.f18907r.iterator();
        while (it.hasNext()) {
            it.next().d();
        }
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public void f() {
        super.f();
        int[] iArr = this.f18910u;
        if (iArr != null) {
            GLES20.glDeleteTextures(2, iArr, 0);
            this.f18910u = null;
        }
        int[] iArr2 = this.f18909t;
        if (iArr2 != null) {
            GLES20.glDeleteFramebuffers(2, iArr2, 0);
            this.f18909t = null;
        }
    }

    @Override // com.tencent.liteav.basic.opengl.j
    public int a(int i2, int i3, int i4) {
        int size = this.f18908s.size();
        k();
        boolean z2 = false;
        for (int i5 = 0; i5 < size; i5++) {
            com.tencent.liteav.basic.opengl.j jVar = this.f18908s.get(i5);
            if (z2) {
                i2 = jVar.a(i2, i3, i4);
            } else {
                i2 = jVar.a(i2, this.f18909t[0], this.f18910u[0]);
            }
            z2 = !z2;
        }
        if (z2) {
            this.f18911v.a(i2, i3, i4);
        }
        return i4;
    }
}
