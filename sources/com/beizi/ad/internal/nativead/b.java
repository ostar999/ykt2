package com.beizi.ad.internal.nativead;

import android.content.Context;
import android.graphics.Bitmap;
import com.beizi.ad.NativeAdListener;
import com.beizi.ad.NativeAdResponse;
import com.beizi.ad.R;
import com.beizi.ad.internal.d;
import com.beizi.ad.internal.l;
import com.beizi.ad.internal.network.a;
import com.beizi.ad.internal.utilities.DeviceInfoUtil;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.ImageService;
import com.beizi.ad.internal.utilities.StringUtil;
import com.beizi.ad.internal.utilities.UserEnvInfoUtil;

/* loaded from: classes2.dex */
public class b extends com.beizi.ad.internal.network.a implements com.beizi.ad.internal.a {

    /* renamed from: a, reason: collision with root package name */
    public com.beizi.ad.internal.c f4314a;

    /* renamed from: c, reason: collision with root package name */
    private NativeAdListener f4315c;

    /* renamed from: d, reason: collision with root package name */
    private a f4316d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f4317e;

    /* renamed from: f, reason: collision with root package name */
    private boolean f4318f;

    /* renamed from: g, reason: collision with root package name */
    private d f4319g;

    /* renamed from: h, reason: collision with root package name */
    private String f4320h;

    /* renamed from: i, reason: collision with root package name */
    private String f4321i;

    /* renamed from: j, reason: collision with root package name */
    private String f4322j;

    /* renamed from: k, reason: collision with root package name */
    private boolean f4323k = false;

    public b(Context context, String str, int i2) {
        this.f4319g = null;
        DeviceInfoUtil.retrieveDeviceInfo(context.getApplicationContext());
        UserEnvInfoUtil.retrieveUserEnvInfo(context.getApplicationContext());
        d dVar = new d(context, StringUtil.createRequestId());
        this.f4319g = dVar;
        dVar.a(str);
        this.f4319g.a(i2);
        this.f4319g.a(l.NATIVE);
        com.beizi.ad.internal.c cVar = new com.beizi.ad.internal.c(this);
        this.f4314a = cVar;
        cVar.a(-1);
        this.f4316d = new a();
    }

    public String d() {
        return this.f4321i;
    }

    public NativeAdListener e() {
        return this.f4315c;
    }

    public d f() {
        return this.f4319g;
    }

    public com.beizi.ad.internal.b g() {
        return this.f4316d;
    }

    @Override // com.beizi.ad.internal.a
    public l getMediaType() {
        return this.f4319g.i();
    }

    public String h() {
        return this.f4322j;
    }

    @Override // com.beizi.ad.internal.a
    public boolean isReadyToStart() {
        return this.f4315c != null && this.f4319g.j();
    }

    public String b() {
        HaoboLog.d(HaoboLog.nativeLogTag, HaoboLog.getString(R.string.get_placement_id, this.f4319g.c()));
        return this.f4319g.c();
    }

    public String c() {
        return this.f4320h;
    }

    public void d(String str) {
        this.f4322j = str;
    }

    public boolean a() {
        HaoboLog.d(HaoboLog.nativeLogTag, HaoboLog.getString(R.string.get_opens_native_browser, this.f4319g.h()));
        return this.f4319g.h();
    }

    public void c(String str) {
        this.f4321i = str;
    }

    public void c(boolean z2) {
        this.f4318f = z2;
    }

    public void b(String str) {
        this.f4320h = str;
    }

    public void a(boolean z2) {
        HaoboLog.d(HaoboLog.nativeLogTag, HaoboLog.getString(R.string.set_opens_native_browser, z2));
        this.f4319g.b(z2);
    }

    public void b(boolean z2) {
        this.f4317e = z2;
    }

    public void a(String str) {
        HaoboLog.d(HaoboLog.nativeLogTag, HaoboLog.getString(R.string.set_placement_id, str));
        this.f4319g.a(str);
    }

    public void a(NativeAdListener nativeAdListener) {
        this.f4315c = nativeAdListener;
    }

    public boolean a(a.C0055a c0055a) {
        if (this.f4315c == null) {
            HaoboLog.e(HaoboLog.nativeLogTag, "No mNativeAdListener installed for this request, won't load a new ad");
            return false;
        }
        if (this.f4323k) {
            HaoboLog.e(HaoboLog.nativeLogTag, "Still loading last nativead ad , won't load a new ad");
            return false;
        }
        if (!this.f4319g.j()) {
            return false;
        }
        this.f4314a.a();
        this.f4314a.c();
        this.f4314a.b();
        this.f4323k = true;
        return true;
    }

    public class a implements com.beizi.ad.internal.b, ImageService.ImageServiceListener {

        /* renamed from: a, reason: collision with root package name */
        ImageService f4324a;

        /* renamed from: b, reason: collision with root package name */
        NativeAdResponse f4325b;

        private a() {
        }

        @Override // com.beizi.ad.internal.b
        public void a() {
        }

        @Override // com.beizi.ad.internal.b
        public void a(long j2) {
        }

        @Override // com.beizi.ad.internal.b
        public void a(com.beizi.ad.internal.network.b bVar) {
            if (!bVar.a().equals(l.NATIVE)) {
                a(0);
                return;
            }
            final NativeAdResponse nativeAdResponseD = bVar.d();
            if (nativeAdResponseD == null) {
                return;
            }
            b.this.b(bVar.f());
            b.this.c(bVar.g());
            b.this.d(nativeAdResponseD.getLandingPageUrl());
            if (!b.this.f4317e && !b.this.f4318f) {
                if (b.this.f4315c != null) {
                    b.this.f4315c.onAdLoaded(nativeAdResponseD);
                } else {
                    nativeAdResponseD.destroy();
                }
                b.this.f4323k = false;
                return;
            }
            this.f4324a = new ImageService();
            this.f4325b = nativeAdResponseD;
            if (b.this.f4317e) {
                this.f4324a.registerImageReceiver(new ImageService.ImageReceiver() { // from class: com.beizi.ad.internal.nativead.b.a.1
                    @Override // com.beizi.ad.internal.utilities.ImageService.ImageReceiver
                    public void onFail() {
                        HaoboLog.e(HaoboLog.httpRespLogTag, "Image downloading logFailed for url " + nativeAdResponseD.getImageUrl());
                    }

                    @Override // com.beizi.ad.internal.utilities.ImageService.ImageReceiver
                    public void onReceiveImage(Bitmap bitmap) {
                        nativeAdResponseD.setImage(bitmap);
                    }
                }, nativeAdResponseD.getImageUrl());
            }
            if (b.this.f4318f) {
                this.f4324a.registerImageReceiver(new ImageService.ImageReceiver() { // from class: com.beizi.ad.internal.nativead.b.a.2
                    @Override // com.beizi.ad.internal.utilities.ImageService.ImageReceiver
                    public void onFail() {
                        HaoboLog.e(HaoboLog.httpRespLogTag, "Image downloading logFailed for url " + nativeAdResponseD.getIconUrl());
                    }

                    @Override // com.beizi.ad.internal.utilities.ImageService.ImageReceiver
                    public void onReceiveImage(Bitmap bitmap) {
                        nativeAdResponseD.setIcon(bitmap);
                    }
                }, nativeAdResponseD.getIconUrl());
            }
            this.f4324a.registerNotification(this);
            this.f4324a.execute();
        }

        @Override // com.beizi.ad.internal.b
        public void a(String str, int i2) {
        }

        @Override // com.beizi.ad.internal.b
        public void a(String str, String str2) {
        }

        @Override // com.beizi.ad.internal.b
        public void b() {
        }

        @Override // com.beizi.ad.internal.b
        public void c() {
        }

        @Override // com.beizi.ad.internal.b
        public void d() {
        }

        @Override // com.beizi.ad.internal.b
        public void e() {
        }

        @Override // com.beizi.ad.internal.b
        public void f() {
        }

        @Override // com.beizi.ad.internal.utilities.ImageService.ImageServiceListener
        public void onAllImageDownloadsFinish() {
            if (b.this.f4315c != null) {
                b.this.f4315c.onAdLoaded(this.f4325b);
            } else {
                this.f4325b.destroy();
            }
            this.f4324a = null;
            this.f4325b = null;
            b.this.f4323k = false;
        }

        @Override // com.beizi.ad.internal.b
        public void a(int i2) {
            if (b.this.f4315c != null) {
                b.this.f4315c.onAdFailed(i2);
            }
            b.this.f4323k = false;
        }
    }
}
