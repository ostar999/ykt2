package com.aliyun.player.videoview.a;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import com.aliyun.player.IPlayer;
import com.aliyun.player.videoview.AliDisplayView;
import com.cicada.player.utils.Logger;

/* loaded from: classes2.dex */
public abstract class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f3545a = "AliDisplayView_" + a.class.getSimpleName();

    /* renamed from: b, reason: collision with root package name */
    private final ViewGroup f3546b;

    /* renamed from: c, reason: collision with root package name */
    protected h f3547c = null;

    /* renamed from: d, reason: collision with root package name */
    private int f3548d = 0;

    /* renamed from: e, reason: collision with root package name */
    private int f3549e = 0;

    /* renamed from: f, reason: collision with root package name */
    private int f3550f = 0;

    /* renamed from: g, reason: collision with root package name */
    private IPlayer.ScaleMode f3551g = IPlayer.ScaleMode.SCALE_ASPECT_FIT;

    /* renamed from: h, reason: collision with root package name */
    private IPlayer.MirrorMode f3552h = IPlayer.MirrorMode.MIRROR_MODE_NONE;

    /* renamed from: i, reason: collision with root package name */
    private IPlayer.RotateMode f3553i = IPlayer.RotateMode.ROTATE_0;

    /* renamed from: j, reason: collision with root package name */
    private boolean f3554j = false;

    /* renamed from: k, reason: collision with root package name */
    private View f3555k = null;

    /* renamed from: com.aliyun.player.videoview.a.a$a, reason: collision with other inner class name */
    public class RunnableC0036a implements Runnable {
        public RunnableC0036a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a.this.g();
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a.this.g();
        }
    }

    public class c implements Runnable {
        public c() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a.this.g();
        }
    }

    public class d implements Runnable {
        public d() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a.this.g();
        }
    }

    public class e implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ IPlayer.MirrorMode f3560a;

        public e(IPlayer.MirrorMode mirrorMode) {
            this.f3560a = mirrorMode;
        }

        @Override // java.lang.Runnable
        public void run() {
            a.this.a(this.f3560a);
        }
    }

    public class f implements Runnable {
        public f() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (a.this.f3555k.getParent() == a.this.f3546b) {
                a.this.f3546b.removeView(a.this.f3555k);
            }
        }
    }

    public class g implements Runnable {
        public g() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a.this.f3546b.addView(a.this.f3555k, 0);
            a aVar = a.this;
            aVar.c(aVar.f3552h);
            a aVar2 = a.this;
            aVar2.b(aVar2.f3551g);
            a aVar3 = a.this;
            aVar3.c(aVar3.f3553i);
            a.this.g();
        }
    }

    public interface h {
        void onSurfaceCreated(Surface surface);

        void onSurfaceDestroy();

        void onSurfaceSizeChanged();

        void onViewCreated(AliDisplayView.DisplayViewType displayViewType);
    }

    public a(ViewGroup viewGroup) {
        this.f3546b = viewGroup;
        Logger.v(f3545a, this + " new IDisplayView()");
    }

    private void a(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            this.f3546b.post(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(IPlayer.ScaleMode scaleMode) {
        if (scaleMode != null) {
            this.f3551g = scaleMode;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(IPlayer.MirrorMode mirrorMode) {
        if (mirrorMode != null) {
            this.f3552h = mirrorMode;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(IPlayer.RotateMode rotateMode) {
        if (rotateMode != null) {
            this.f3553i = rotateMode;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008a A[PHI: r0 r6
      0x008a: PHI (r0v6 int) = (r0v5 int), (r0v11 int) binds: [B:35:0x0088, B:30:0x007a] A[DONT_GENERATE, DONT_INLINE]
      0x008a: PHI (r6v1 int) = (r6v0 int), (r6v3 int) binds: [B:35:0x0088, B:30:0x007a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0094 A[PHI: r0 r6
      0x0094: PHI (r0v9 int) = (r0v5 int), (r0v11 int) binds: [B:37:0x0092, B:32:0x007d] A[DONT_GENERATE, DONT_INLINE]
      0x0094: PHI (r6v2 int) = (r6v0 int), (r6v3 int) binds: [B:37:0x0092, B:32:0x007d] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void g() {
        /*
            r8 = this;
            java.lang.String r0 = com.aliyun.player.videoview.a.a.f3545a
            java.lang.String r1 = "updateViewParams  "
            com.cicada.player.utils.Logger.v(r0, r1)
            boolean r1 = r8.f3554j
            r2 = -1
            if (r1 != 0) goto L21
            android.widget.FrameLayout$LayoutParams r0 = new android.widget.FrameLayout$LayoutParams
            r0.<init>(r2, r2)
            android.view.View r1 = r8.f3555k
            android.view.ViewParent r1 = r1.getParent()
            android.view.ViewGroup r2 = r8.f3546b
            if (r1 != r2) goto L20
            android.view.View r1 = r8.f3555k
            r1.setLayoutParams(r0)
        L20:
            return
        L21:
            int r1 = r8.f3549e
            if (r1 == 0) goto Lbe
            int r1 = r8.f3548d
            if (r1 != 0) goto L2b
            goto Lbe
        L2b:
            android.view.ViewGroup r1 = r8.f3546b
            int r1 = r1.getMeasuredWidth()
            android.view.ViewGroup r3 = r8.f3546b
            int r3 = r3.getMeasuredHeight()
            if (r1 == 0) goto Lb8
            if (r3 != 0) goto L3d
            goto Lb8
        L3d:
            com.aliyun.player.IPlayer$RotateMode r0 = r8.f3553i
            boolean r0 = r8.a(r0)
            if (r0 == 0) goto L65
            com.aliyun.player.IPlayer$RotateMode r0 = r8.f3553i
            int r0 = r0.getValue()
            float r0 = (float) r0
            int r4 = r8.f3550f
            float r4 = (float) r4
            float r4 = r4 + r0
            int r0 = (int) r4
            r4 = 90
            if (r0 == r4) goto L59
            r4 = 270(0x10e, float:3.78E-43)
            if (r0 != r4) goto L65
        L59:
            android.view.ViewGroup r0 = r8.f3546b
            int r1 = r0.getMeasuredHeight()
            android.view.ViewGroup r0 = r8.f3546b
            int r3 = r0.getMeasuredWidth()
        L65:
            com.aliyun.player.IPlayer$ScaleMode r0 = r8.f3551g
            com.aliyun.player.IPlayer$ScaleMode r4 = com.aliyun.player.IPlayer.ScaleMode.SCALE_TO_FILL
            if (r0 != r4) goto L6c
            goto L9b
        L6c:
            com.aliyun.player.IPlayer$ScaleMode r4 = com.aliyun.player.IPlayer.ScaleMode.SCALE_ASPECT_FILL
            r5 = 1065353216(0x3f800000, float:1.0)
            if (r0 != r4) goto L80
            int r0 = r8.f3548d
            int r4 = r0 * r3
            int r6 = r8.f3549e
            int r7 = r1 * r6
            if (r4 <= r7) goto L7d
            goto L8a
        L7d:
            if (r4 >= r7) goto L9b
            goto L94
        L80:
            int r0 = r8.f3548d
            int r4 = r0 * r3
            int r6 = r8.f3549e
            int r7 = r1 * r6
            if (r4 >= r7) goto L92
        L8a:
            float r1 = (float) r3
            float r1 = r1 * r5
            float r0 = (float) r0
            float r1 = r1 * r0
            float r0 = (float) r6
            float r1 = r1 / r0
            int r1 = (int) r1
            goto L9b
        L92:
            if (r4 <= r7) goto L9b
        L94:
            float r3 = (float) r1
            float r3 = r3 * r5
            float r4 = (float) r6
            float r3 = r3 * r4
            float r0 = (float) r0
            float r3 = r3 / r0
            int r3 = (int) r3
        L9b:
            android.widget.FrameLayout$LayoutParams r0 = new android.widget.FrameLayout$LayoutParams
            r0.<init>(r2, r2)
            r0.width = r1
            r0.height = r3
            r1 = 17
            r0.gravity = r1
            android.view.View r1 = r8.f3555k
            android.view.ViewParent r1 = r1.getParent()
            android.view.ViewGroup r2 = r8.f3546b
            if (r1 != r2) goto Lb7
            android.view.View r1 = r8.f3555k
            r1.setLayoutParams(r0)
        Lb7:
            return
        Lb8:
            java.lang.String r1 = "updateViewParams ，unknow parent height and width "
        Lba:
            com.cicada.player.utils.Logger.w(r0, r1)
            return
        Lbe:
            java.lang.String r1 = "updateViewParams ，unknow videoheight and width "
            goto Lba
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.player.videoview.a.a.g():void");
    }

    public abstract View a(Context context);

    public void a() {
        Logger.v(f3545a, " attachView");
        a(new g());
    }

    public void a(int i2, int i3, int i4) {
        Logger.v(f3545a, "setVideoSize  " + i2 + " ， " + i3 + " ， " + i4);
        this.f3548d = i2;
        this.f3549e = i3;
        this.f3550f = i4;
        a(new c());
    }

    public void a(IPlayer.ScaleMode scaleMode) {
        Logger.v(f3545a, "setScaleMode  " + scaleMode);
        b(scaleMode);
        a(new d());
    }

    public void a(h hVar) {
        Logger.v(f3545a, this + " setOnViewStatusListener " + hVar);
        this.f3547c = hVar;
    }

    public void a(boolean z2) {
        Logger.v(f3545a, "setRenderFlag  " + z2);
        this.f3554j = z2;
        a(new b());
    }

    public abstract boolean a(IPlayer.MirrorMode mirrorMode);

    public abstract boolean a(IPlayer.RotateMode rotateMode);

    public void b() {
        Logger.v(f3545a, " detachView");
        a(new f());
    }

    public void b(IPlayer.MirrorMode mirrorMode) {
        Logger.v(f3545a, "setMirrorMode  " + mirrorMode);
        c(mirrorMode);
        this.f3555k.post(new e(mirrorMode));
    }

    public void b(IPlayer.RotateMode rotateMode) {
        Logger.v(f3545a, "setRotateMode  " + rotateMode);
        c(rotateMode);
        g();
    }

    public abstract void b(boolean z2);

    public void c() {
        this.f3555k = a(this.f3546b.getContext());
    }

    public void d() {
        Logger.v(f3545a, "parentSizeChanged  ");
        a(new RunnableC0036a());
    }

    public abstract Bitmap e();

    public Bitmap f() {
        return e();
    }
}
