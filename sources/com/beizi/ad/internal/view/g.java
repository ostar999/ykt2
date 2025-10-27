package com.beizi.ad.internal.view;

import android.graphics.Canvas;
import com.beizi.ad.internal.view.f;

/* loaded from: classes2.dex */
class g extends AdWebView {

    /* renamed from: d, reason: collision with root package name */
    f f4695d;

    public g(AdViewImpl adViewImpl, f fVar) {
        super(adViewImpl);
        this.f4568b = f.f4658a[f.b.STARTING_EXPANDED.ordinal()];
        this.f4695d = fVar;
    }

    @Override // com.beizi.ad.internal.view.AdWebView
    public void e() {
        super.e();
        this.f4695d.a();
    }

    @Override // com.beizi.ad.internal.view.AdWebView, android.webkit.WebView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
