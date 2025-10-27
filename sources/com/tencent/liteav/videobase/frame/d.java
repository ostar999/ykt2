package com.tencent.liteav.videobase.frame;

import androidx.annotation.NonNull;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videobase.a.a;
import com.tencent.liteav.videobase.frame.a;
import com.tencent.liteav.videobase.utils.OpenGlUtils;
import com.yikaobang.yixue.R2;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public class d extends com.tencent.liteav.videobase.frame.a<a> {

    /* renamed from: a, reason: collision with root package name */
    private static final AtomicInteger f20086a = new AtomicInteger();

    public static class a extends a.c {

        /* renamed from: a, reason: collision with root package name */
        private int f20087a;

        /* renamed from: b, reason: collision with root package name */
        private final int f20088b;

        /* renamed from: c, reason: collision with root package name */
        private final int f20089c;

        @Override // com.tencent.liteav.videobase.frame.a.c
        public void release() {
            super.release();
        }

        private a(a.InterfaceC0341a<a> interfaceC0341a, int i2, int i3) {
            super(interfaceC0341a);
            this.f20087a = -1;
            this.f20088b = i2;
            this.f20089c = i3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b() {
            this.f20087a = OpenGlUtils.createTexture(this.f20088b, this.f20089c, R2.dimen.dm_200, R2.dimen.dm_200);
            TXCLog.d("GLTexture", "count: %d, create textureId: %d", Integer.valueOf(d.f20086a.incrementAndGet()), Integer.valueOf(this.f20087a));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c() {
            OpenGlUtils.deleteTexture(this.f20087a);
            this.f20087a = -1;
            TXCLog.d("GLTexture", "count: %d, delete textureId: %d", Integer.valueOf(d.f20086a.getAndDecrement()), Integer.valueOf(this.f20087a));
        }

        public int a() {
            return this.f20087a;
        }

        public PixelFrame a(Object obj) {
            b bVar = new b(this, obj);
            bVar.retain();
            return bVar;
        }
    }

    public static class b extends PixelFrame {

        /* renamed from: b, reason: collision with root package name */
        private static final a.InterfaceC0341a<b> f20090b = e.a();

        /* renamed from: a, reason: collision with root package name */
        private final a f20091a;

        @Override // com.tencent.liteav.videobase.frame.PixelFrame
        public void setTextureId(int i2) {
            throw new UnsupportedOperationException("Object is allocated by pool, can't change its Buffer");
        }

        private b(a aVar, Object obj) {
            super(f20090b);
            aVar.retain();
            this.mWidth = aVar.f20088b;
            this.mHeight = aVar.f20089c;
            this.f20091a = aVar;
            this.mTextureId = aVar.a();
            this.mGLContext = obj;
            this.mPixelBufferType = a.b.TEXTURE_2D;
            this.mPixelFormatType = a.c.RGBA;
        }
    }

    public static class c implements a.b {

        /* renamed from: a, reason: collision with root package name */
        private final int f20092a;

        /* renamed from: b, reason: collision with root package name */
        private final int f20093b;

        public c(int i2, int i3) {
            this.f20092a = i2;
            this.f20093b = i3;
        }

        public boolean equals(Object obj) {
            if (obj.getClass() != getClass()) {
                return false;
            }
            c cVar = (c) obj;
            return this.f20092a == cVar.f20092a && this.f20093b == cVar.f20093b;
        }

        public int hashCode() {
            return (this.f20092a * 37213) + this.f20093b;
        }
    }

    @Override // com.tencent.liteav.videobase.frame.a
    public void b() {
        super.b();
    }

    @NonNull
    public a a(int i2, int i3) {
        return (a) super.a(new c(i2, i3));
    }

    @Override // com.tencent.liteav.videobase.frame.a
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public a a(a.InterfaceC0341a<a> interfaceC0341a, a.b bVar) {
        c cVar = (c) bVar;
        a aVar = new a(interfaceC0341a, cVar.f20092a, cVar.f20093b);
        aVar.b();
        return aVar;
    }

    @Override // com.tencent.liteav.videobase.frame.a
    public void a() {
        super.a();
    }

    @Override // com.tencent.liteav.videobase.frame.a
    public void a(a aVar) {
        aVar.c();
    }

    @Override // com.tencent.liteav.videobase.frame.a
    public a.b b(a aVar) {
        return new c(aVar.f20088b, aVar.f20089c);
    }
}
