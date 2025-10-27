package com.beizi.fusion.work.f;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.beizi.fusion.NativeUnifiedAdResponse;
import com.beizi.fusion.d.h;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import java.util.List;

/* loaded from: classes2.dex */
public class a extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    protected Context f5684n;

    /* renamed from: p, reason: collision with root package name */
    protected long f5686p;

    /* renamed from: q, reason: collision with root package name */
    protected float f5687q;

    /* renamed from: r, reason: collision with root package name */
    protected float f5688r;

    /* renamed from: s, reason: collision with root package name */
    protected NativeUnifiedAdResponse f5689s;

    /* renamed from: t, reason: collision with root package name */
    protected FrameLayout f5690t;

    /* renamed from: o, reason: collision with root package name */
    protected com.beizi.fusion.f.a f5685o = com.beizi.fusion.f.a.ADDEFAULT;

    /* renamed from: u, reason: collision with root package name */
    protected boolean f5691u = false;

    /* renamed from: v, reason: collision with root package name */
    protected boolean f5692v = false;

    public a(Context context, long j2, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, int i2) {
        this.f5684n = context;
        this.f5686p = j2;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5547k = i2;
        this.f5542f = forwardBean;
        this.f5687q = as.m(context);
        this.f5688r = as.n(context);
        x();
    }

    private void aZ() {
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " NativeUnifiedWorker:" + eVar.p().toString());
        ad();
        h hVar = this.f5543g;
        if (hVar == h.SUCCESS) {
            ba();
            this.f5540d.a(g(), (View) null);
        } else if (hVar == h.FAIL) {
            Log.d("BeiZis", "other worker shown," + g() + " remove");
        }
    }

    private void ba() {
        this.f5689s = new NativeUnifiedAdResponse() { // from class: com.beizi.fusion.work.f.a.1
            @Override // com.beizi.fusion.NativeUnifiedAdResponse
            public String getActionText() {
                return a.this.aT();
            }

            @Override // com.beizi.fusion.NativeUnifiedAdResponse
            public String getDescription() {
                return a.this.aO();
            }

            @Override // com.beizi.fusion.NativeUnifiedAdResponse
            public int getECPM() {
                String strL = a.this.l();
                if (strL == null) {
                    return -1;
                }
                try {
                    return Integer.parseInt(strL);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return -1;
                }
            }

            @Override // com.beizi.fusion.NativeUnifiedAdResponse
            public String getIconUrl() {
                return a.this.aP();
            }

            @Override // com.beizi.fusion.NativeUnifiedAdResponse
            public String getImageUrl() {
                return a.this.aQ();
            }

            @Override // com.beizi.fusion.NativeUnifiedAdResponse
            public List<String> getImgList() {
                return a.this.aR();
            }

            @Override // com.beizi.fusion.NativeUnifiedAdResponse
            public int getMaterialType() {
                return a.this.aS();
            }

            @Override // com.beizi.fusion.NativeUnifiedAdResponse
            public String getTitle() {
                return a.this.aN();
            }

            @Override // com.beizi.fusion.NativeUnifiedAdResponse
            public View getVideoView() {
                return a.this.aW();
            }

            @Override // com.beizi.fusion.NativeUnifiedAdResponse
            public ViewGroup getViewContainer() {
                return a.this.aV();
            }

            @Override // com.beizi.fusion.NativeUnifiedAdResponse
            public boolean isVideo() {
                return a.this.aU();
            }

            @Override // com.beizi.fusion.NativeUnifiedAdResponse
            public void registerViewForInteraction(List<View> list) {
                a.this.a(list);
            }
        };
    }

    public void a(List<View> list) {
    }

    @Override // com.beizi.fusion.work.a
    public NativeUnifiedAdResponse aK() {
        return this.f5689s;
    }

    public void aL() {
    }

    public void aM() {
    }

    public String aN() {
        return null;
    }

    public String aO() {
        return null;
    }

    public String aP() {
        return null;
    }

    public String aQ() {
        return null;
    }

    public List<String> aR() {
        return null;
    }

    public int aS() {
        return 0;
    }

    public String aT() {
        return null;
    }

    public boolean aU() {
        return false;
    }

    public ViewGroup aV() {
        return null;
    }

    public View aW() {
        return null;
    }

    public void aX() {
    }

    public void aY() {
        try {
            if (ac()) {
                aZ();
            } else {
                S();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override // com.beizi.fusion.work.a
    public void ag() {
        Log.d("BeiZis", "showNativeUnifiedAd Callback --> onAdShow()");
        this.f5685o = com.beizi.fusion.f.a.ADSHOW;
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar != null && eVar.q() != 2) {
            this.f5540d.b(g());
        }
        if (this.f5691u) {
            return;
        }
        this.f5691u = true;
        aG();
        I();
        J();
        am();
    }

    public void b() {
        Log.d("BeiZis", "showNativeUnifiedAd Callback --> onADClicked()");
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar != null && eVar.q() != 2) {
            this.f5540d.d(g());
        }
        if (this.f5692v) {
            return;
        }
        this.f5692v = true;
        K();
        an();
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        if (this.f5540d == null) {
            return;
        }
        this.f5544h = this.f5541e.getAppId();
        this.f5545i = this.f5541e.getSpaceId();
        this.f5539c = com.beizi.fusion.f.b.a(this.f5541e.getId());
        com.beizi.fusion.b.d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                aL();
            }
        }
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return null;
    }

    @Override // com.beizi.fusion.work.a
    public com.beizi.fusion.f.a k() {
        return this.f5685o;
    }

    @Override // com.beizi.fusion.work.a
    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        aM();
    }
}
