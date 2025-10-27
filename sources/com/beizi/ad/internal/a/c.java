package com.beizi.ad.internal.a;

import android.app.Activity;
import android.content.MutableContextWrapper;
import android.webkit.WebView;
import com.beizi.ad.AdActivity;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.ViewUtil;
import com.beizi.ad.internal.view.AdViewImpl;
import com.beizi.ad.internal.view.AdWebView;
import com.beizi.ad.internal.view.f;

/* loaded from: classes2.dex */
public class c implements AdActivity.a {

    /* renamed from: a, reason: collision with root package name */
    private Activity f4003a;

    /* renamed from: b, reason: collision with root package name */
    private AdWebView f4004b;

    /* renamed from: c, reason: collision with root package name */
    private f f4005c = null;

    public c(Activity activity) {
        this.f4003a = activity;
    }

    @Override // com.beizi.ad.AdActivity.a
    public void a() {
        if (AdViewImpl.getMRAIDFullscreenContainer() == null || AdViewImpl.getMRAIDFullscreenImplementation() == null) {
            HaoboLog.e(HaoboLog.baseLogTag, "Launched MRAID Fullscreen activity with invalid properties");
            this.f4003a.finish();
            return;
        }
        ViewUtil.removeChildFromParent(AdViewImpl.getMRAIDFullscreenContainer());
        this.f4003a.setContentView(AdViewImpl.getMRAIDFullscreenContainer());
        if (AdViewImpl.getMRAIDFullscreenContainer().getChildAt(0) instanceof AdWebView) {
            this.f4004b = (AdWebView) AdViewImpl.getMRAIDFullscreenContainer().getChildAt(0);
        }
        if (this.f4004b.getContext() instanceof MutableContextWrapper) {
            ((MutableContextWrapper) this.f4004b.getContext()).setBaseContext(this.f4003a);
        }
        f mRAIDFullscreenImplementation = AdViewImpl.getMRAIDFullscreenImplementation();
        this.f4005c = mRAIDFullscreenImplementation;
        mRAIDFullscreenImplementation.a(this.f4003a);
        if (AdViewImpl.getMRAIDFullscreenListener() != null) {
            AdViewImpl.getMRAIDFullscreenListener().a();
        }
    }

    @Override // com.beizi.ad.AdActivity.a
    public void b() {
        f fVar = this.f4005c;
        if (fVar != null) {
            fVar.a((Activity) null);
            this.f4005c.a();
        }
        this.f4005c = null;
    }

    @Override // com.beizi.ad.AdActivity.a
    public void c() {
    }

    @Override // com.beizi.ad.AdActivity.a
    public void d() {
    }

    @Override // com.beizi.ad.AdActivity.a
    public void e() {
    }

    @Override // com.beizi.ad.AdActivity.a
    public WebView f() {
        return this.f4004b;
    }
}
