package a.a.a.a.f;

import android.graphics.SurfaceTexture;
import android.view.Surface;

/* loaded from: classes.dex */
public class h extends c {

    /* renamed from: f, reason: collision with root package name */
    private Surface f1168f;

    /* renamed from: g, reason: collision with root package name */
    private boolean f1169g;

    public h(b bVar, Surface surface, boolean z2) {
        super(bVar);
        a(surface);
        this.f1168f = surface;
        this.f1169g = z2;
    }

    public void a(b bVar) {
        Surface surface = this.f1168f;
        if (surface == null) {
            throw new RuntimeException("not yet implemented for SurfaceTexture");
        }
        this.f1158a = bVar;
        a(surface);
    }

    public void f() {
        d();
        Surface surface = this.f1168f;
        if (surface != null) {
            if (this.f1169g) {
                surface.release();
            }
            this.f1168f = null;
        }
    }

    public h(b bVar, SurfaceTexture surfaceTexture) {
        super(bVar);
        a(surfaceTexture);
    }

    public h(b bVar, int i2, int i3) {
        super(bVar);
        a(i2, i3);
    }
}
