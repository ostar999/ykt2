package com.plv.linkmic.processor;

import android.os.Handler;
import android.os.Looper;
import com.plv.linkmic.PLVLinkMicEventHandler;
import io.reactivex.disposables.Disposable;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public abstract class d implements a {
    protected static final int QUALITY_BAD = 4;
    protected static final int QUALITY_DOWN = 6;
    protected static final int QUALITY_EXCELLENT = 1;
    protected static final int QUALITY_GOOD = 2;
    protected static final int QUALITY_POOR = 3;
    protected static final int QUALITY_UNKNOWN = 0;
    protected static final int QUALITY_VBAD = 5;

    /* renamed from: a, reason: collision with root package name */
    protected final ConcurrentHashMap<PLVLinkMicEventHandler, Integer> f10818a = new ConcurrentHashMap<>();

    /* renamed from: b, reason: collision with root package name */
    protected Handler f10819b = new Handler(Looper.getMainLooper());

    @Override // com.plv.linkmic.processor.a
    public Collection<PLVLinkMicEventHandler> a() {
        return this.f10818a.keySet();
    }

    @Override // com.plv.linkmic.processor.a
    public void addEventHandler(PLVLinkMicEventHandler pLVLinkMicEventHandler) {
        if (pLVLinkMicEventHandler == null) {
            return;
        }
        this.f10818a.put(pLVLinkMicEventHandler, 0);
    }

    @Override // com.plv.linkmic.processor.a
    public void destroy() {
    }

    public void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override // com.plv.linkmic.processor.a
    public void removeAllEventHandler() {
        this.f10818a.clear();
    }

    @Override // com.plv.linkmic.processor.a
    public void removeEventHandler(PLVLinkMicEventHandler pLVLinkMicEventHandler) {
        if (pLVLinkMicEventHandler == null) {
            return;
        }
        this.f10818a.remove(pLVLinkMicEventHandler);
    }

    public void a(final int i2, int i3) {
        if (i2 == 0) {
            i2 = i3;
        }
        this.f10819b.post(new Runnable() { // from class: com.plv.linkmic.processor.d.1
            @Override // java.lang.Runnable
            public void run() {
                for (PLVLinkMicEventHandler pLVLinkMicEventHandler : d.this.f10818a.keySet()) {
                    switch (i2) {
                        case 1:
                        case 2:
                            pLVLinkMicEventHandler.onNetworkQuality(11);
                            break;
                        case 3:
                        case 4:
                            pLVLinkMicEventHandler.onNetworkQuality(12);
                            break;
                        case 5:
                            pLVLinkMicEventHandler.onNetworkQuality(13);
                            break;
                        case 6:
                            pLVLinkMicEventHandler.onNetworkQuality(14);
                            break;
                    }
                }
            }
        });
    }
}
