package com.beizi.ad.internal.b;

import android.view.View;
import com.beizi.ad.internal.utilities.ViewUtil;

/* loaded from: classes2.dex */
public class e implements com.beizi.ad.internal.view.c {

    /* renamed from: a, reason: collision with root package name */
    private View f4039a;

    /* renamed from: b, reason: collision with root package name */
    private b f4040b;

    public e(b bVar) {
        this.f4040b = bVar;
    }

    public void a(View view) {
        this.f4039a = view;
    }

    @Override // com.beizi.ad.internal.view.c
    public void destroy() {
        this.f4040b.b();
        ViewUtil.removeChildFromParent(this.f4039a);
    }

    @Override // com.beizi.ad.internal.view.c
    public boolean failed() {
        return this.f4040b.f4021g;
    }

    @Override // com.beizi.ad.internal.view.c
    public int getCreativeHeight() {
        View view = this.f4039a;
        if (view != null) {
            return view.getHeight();
        }
        return -1;
    }

    @Override // com.beizi.ad.internal.view.c
    public int getCreativeWidth() {
        View view = this.f4039a;
        if (view != null) {
            return view.getWidth();
        }
        return -1;
    }

    @Override // com.beizi.ad.internal.view.c
    public int getRefreshInterval() {
        return 0;
    }

    @Override // com.beizi.ad.internal.view.c
    public View getView() {
        return this.f4039a;
    }

    @Override // com.beizi.ad.internal.view.c
    public void onDestroy() {
        this.f4040b.j();
        destroy();
    }

    @Override // com.beizi.ad.internal.view.c
    public void onPause() {
        this.f4040b.k();
    }

    @Override // com.beizi.ad.internal.view.c
    public void onResume() {
        this.f4040b.l();
    }

    @Override // com.beizi.ad.internal.view.c
    public void visible() {
    }

    public b a() {
        return this.f4040b;
    }
}
