package com.tencent.liteav.videobase.frame;

import android.opengl.GLES20;
import android.os.SystemClock;
import android.util.SparseArray;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videobase.frame.d;
import com.tencent.liteav.videobase.utils.OpenGlUtils;
import com.yikaobang.yixue.R2;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static final long f20076a = TimeUnit.SECONDS.toMillis(3);

    /* renamed from: b, reason: collision with root package name */
    private final SparseArray<LinkedList<a>> f20077b = new SparseArray<>();

    /* renamed from: c, reason: collision with root package name */
    private final d f20078c = new d();

    public class a {

        /* renamed from: b, reason: collision with root package name */
        private final int f20080b;

        /* renamed from: c, reason: collision with root package name */
        private final int f20081c;

        /* renamed from: d, reason: collision with root package name */
        private int f20082d;

        /* renamed from: e, reason: collision with root package name */
        private long f20083e;

        /* renamed from: f, reason: collision with root package name */
        private Object f20084f;

        /* renamed from: g, reason: collision with root package name */
        private d.a f20085g;

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.f20082d = OpenGlUtils.generateFrameBufferId();
            d();
            TXCLog.i("GLFrameBuffer", "create framebufferId: %d, textureId: %d", Integer.valueOf(this.f20082d), Integer.valueOf(this.f20085g.a()));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void i() {
            TXCLog.i("GLFrameBuffer", "uninitialize framebufferId: %d, textureId: %d", Integer.valueOf(this.f20082d), Integer.valueOf(this.f20085g.a()));
            d.a aVar = this.f20085g;
            if (aVar != null) {
                aVar.release();
                this.f20085g = null;
            }
            OpenGlUtils.deleteFrameBuffer(this.f20082d);
            this.f20082d = -1;
            this.f20084f = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long j() {
            return this.f20083e;
        }

        public void d() {
            int i2;
            if (this.f20085g != null && (i2 = this.f20082d) != -1) {
                GLES20.glBindFramebuffer(36160, i2);
                GLES20.glFramebufferTexture2D(36160, 36064, R2.attr.tab_indicator_height, 0, 0);
                GLES20.glBindFramebuffer(36160, 0);
                this.f20085g.release();
            }
            d.a aVarA = c.this.f20078c.a(this.f20080b, this.f20081c);
            this.f20085g = aVarA;
            OpenGlUtils.attachTextureToFrameBuffer(aVarA.a(), this.f20082d);
        }

        public int e() {
            d.a aVar = this.f20085g;
            if (aVar == null) {
                return -1;
            }
            return aVar.a();
        }

        public int f() {
            return this.f20080b;
        }

        public int g() {
            return this.f20081c;
        }

        private a(int i2, int i3) {
            this.f20082d = -1;
            this.f20083e = -1L;
            this.f20080b = i2;
            this.f20081c = i3;
        }

        public void b() {
            OpenGlUtils.bindFramebuffer(36160, 0);
        }

        public d.a c() {
            return this.f20085g;
        }

        public void a() {
            OpenGlUtils.bindFramebuffer(36160, this.f20082d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(long j2) {
            this.f20083e = j2;
        }
    }

    private void c() {
        a aVarPeekLast;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        for (int i2 = 0; i2 < this.f20077b.size(); i2++) {
            LinkedList<a> linkedListValueAt = this.f20077b.valueAt(i2);
            while (!linkedListValueAt.isEmpty() && ((aVarPeekLast = linkedListValueAt.peekLast()) == null || jElapsedRealtime - aVarPeekLast.j() >= f20076a)) {
                linkedListValueAt.pollLast();
                if (aVarPeekLast != null) {
                    aVarPeekLast.i();
                }
            }
        }
    }

    public void b() {
        a();
        this.f20078c.b();
    }

    public a a(int i2, int i3) {
        LinkedList<a> linkedListB = b(i2, i3);
        if (!linkedListB.isEmpty()) {
            return linkedListB.removeFirst();
        }
        a aVar = new a(i2, i3);
        aVar.h();
        return aVar;
    }

    private LinkedList<a> b(int i2, int i3) {
        int i4 = (i2 * 32713) + i3;
        LinkedList<a> linkedList = this.f20077b.get(i4);
        if (linkedList != null) {
            return linkedList;
        }
        LinkedList<a> linkedList2 = new LinkedList<>();
        this.f20077b.put(i4, linkedList2);
        return linkedList2;
    }

    public void a(a aVar) {
        if (aVar == null) {
            return;
        }
        aVar.d();
        aVar.a(SystemClock.elapsedRealtime());
        b(aVar.f(), aVar.g()).addFirst(aVar);
        c();
    }

    public void a() {
        for (int i2 = 0; i2 < this.f20077b.size(); i2++) {
            LinkedList<a> linkedListValueAt = this.f20077b.valueAt(i2);
            Iterator<a> it = linkedListValueAt.iterator();
            while (it.hasNext()) {
                it.next().i();
            }
            linkedListValueAt.clear();
        }
        this.f20077b.clear();
        this.f20078c.a();
    }
}
