package com.alipay.sdk.widget;

import com.alipay.sdk.widget.a.AlertDialogC0027a;
import com.google.android.exoplayer2.C;

/* loaded from: classes2.dex */
final class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ a f3410a;

    public b(a aVar) {
        this.f3410a = aVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.f3410a.f3402f == null) {
            a aVar = this.f3410a;
            a aVar2 = this.f3410a;
            aVar.f3402f = aVar2.new AlertDialogC0027a(aVar2.f3403g);
            this.f3410a.f3402f.setCancelable(this.f3410a.f3401e);
        }
        try {
            if (this.f3410a.f3402f.isShowing()) {
                return;
            }
            this.f3410a.f3402f.show();
            this.f3410a.f3408l.sendEmptyMessageDelayed(1, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS);
        } catch (Exception unused) {
        }
    }
}
