package a.a.b.c;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.util.Log;
import android.util.Size;
import android.view.Surface;

/* loaded from: classes.dex */
public class b implements a.a.b.b {

    /* renamed from: j, reason: collision with root package name */
    private static final String f1195j = "b";

    /* renamed from: k, reason: collision with root package name */
    private static final String f1196k = "screen-share-display";

    /* renamed from: a, reason: collision with root package name */
    private Context f1197a;

    /* renamed from: b, reason: collision with root package name */
    private int f1198b;

    /* renamed from: c, reason: collision with root package name */
    private int f1199c;

    /* renamed from: d, reason: collision with root package name */
    private int f1200d;

    /* renamed from: e, reason: collision with root package name */
    private int f1201e;

    /* renamed from: f, reason: collision with root package name */
    private Intent f1202f;

    /* renamed from: g, reason: collision with root package name */
    private MediaProjection f1203g;

    /* renamed from: h, reason: collision with root package name */
    private VirtualDisplay f1204h;

    /* renamed from: i, reason: collision with root package name */
    private volatile boolean f1205i;

    public b(Context context, int i2, int i3, int i4, int i5, Intent intent) {
        this.f1197a = context;
        this.f1198b = i2;
        this.f1199c = i3;
        this.f1200d = i4;
        this.f1201e = 1000 / i5;
        this.f1202f = intent;
    }

    @Override // a.a.b.b
    public void a(a.a.b.a aVar, int i2, float[] fArr) {
    }

    @Override // a.a.b.b
    @TargetApi(21)
    public void a(Surface surface) {
        MediaProjection mediaProjection = ((MediaProjectionManager) this.f1197a.getSystemService("media_projection")).getMediaProjection(-1, this.f1202f);
        this.f1203g = mediaProjection;
        if (mediaProjection == null) {
            Log.e(f1195j, "media projection start failed");
        } else {
            this.f1204h = mediaProjection.createVirtualDisplay(f1196k, this.f1198b, this.f1199c, this.f1200d, 1, surface, null, null);
        }
    }

    @Override // a.a.b.b
    public boolean b() {
        return !this.f1205i;
    }

    @Override // a.a.b.b
    public int c() {
        return this.f1201e;
    }

    @Override // a.a.b.b
    @TargetApi(21)
    public void a(a.a.b.a aVar) {
        this.f1205i = true;
        VirtualDisplay virtualDisplay = this.f1204h;
        if (virtualDisplay != null) {
            virtualDisplay.release();
        }
        MediaProjection mediaProjection = this.f1203g;
        if (mediaProjection != null) {
            mediaProjection.stop();
        }
    }

    @Override // a.a.b.b
    @TargetApi(21)
    public Size a() {
        return new Size(this.f1198b, this.f1199c);
    }
}
