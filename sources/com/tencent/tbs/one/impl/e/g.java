package com.tencent.tbs.one.impl.e;

import android.os.Bundle;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.TBSOneOnlineService;
import com.tencent.tbs.one.impl.a.k;
import com.tencent.tbs.one.impl.a.m;
import com.tencent.tbs.one.impl.a.o;
import com.tencent.tbs.one.impl.common.Statistics;
import java.io.File;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class g implements TBSOneOnlineService {

    /* renamed from: a, reason: collision with root package name */
    public j f22192a;

    /* renamed from: b, reason: collision with root package name */
    public i f22193b;

    public g(i iVar) {
        this.f22193b = iVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(k kVar) throws IOException {
        this.f22192a = null;
        kVar.a();
    }

    public static /* synthetic */ void a(g gVar) {
        j jVar = gVar.f22192a;
        if (jVar != null) {
            jVar.b();
        }
    }

    public static /* synthetic */ void a(g gVar, Bundle bundle, final TBSOneCallback tBSOneCallback) throws IOException {
        j jVar = gVar.f22192a;
        if (jVar != null) {
            jVar.a((m) new com.tencent.tbs.one.impl.common.c<Void>() { // from class: com.tencent.tbs.one.impl.e.g.4
                @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                public final void a(int i2, int i3) {
                    tBSOneCallback.onProgressChanged(i2, i3);
                }

                @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                public final void a(int i2, String str, Throwable th) {
                    tBSOneCallback.onError(i2, str);
                }

                @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                public final /* synthetic */ void a(Object obj) {
                    tBSOneCallback.onCompleted((Void) obj);
                }
            });
            return;
        }
        final i iVar = gVar.f22193b;
        String str = iVar.f22205b;
        final k kVarI = iVar.i();
        if (kVarI == null) {
            if (tBSOneCallback != null) {
                tBSOneCallback.onError(502, "Failed to acquire update lock");
            }
        } else {
            if (iVar.h()) {
                com.tencent.tbs.one.impl.a.g.a("[%s] No need to update because another process has updated", str);
                if (tBSOneCallback != null) {
                    tBSOneCallback.onCompleted(null);
                }
                gVar.a(kVarI);
                return;
            }
            File fileG = iVar.g();
            if (!fileG.exists()) {
                com.tencent.tbs.one.impl.a.d.b(fileG);
            }
            j jVar2 = new j(iVar, bundle);
            gVar.f22192a = jVar2;
            jVar2.a((m) new com.tencent.tbs.one.impl.common.c<Void>() { // from class: com.tencent.tbs.one.impl.e.g.3
                @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                public final void a(int i2, int i3) {
                    tBSOneCallback.onProgressChanged(i2, i3);
                }

                @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                public final void a(int i2, String str2, Throwable th) throws IOException {
                    com.tencent.tbs.one.impl.common.d dVar = iVar.f22218o;
                    Statistics.create(Statistics.EVENT_ERROR, i2).setDEPSVersion(dVar != null ? dVar.f21989a : -1).report();
                    g.this.a(kVarI);
                    tBSOneCallback.onError(i2, str2);
                }

                @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                public final /* synthetic */ void a(Object obj) throws IOException {
                    Void r3 = (Void) obj;
                    File fileG2 = iVar.g();
                    if (fileG2.exists()) {
                        com.tencent.tbs.one.impl.a.d.c(fileG2);
                    }
                    g.this.a(kVarI);
                    tBSOneCallback.onCompleted(r3);
                }
            });
        }
    }

    @Override // com.tencent.tbs.one.TBSOneOnlineService
    public final void cancelUpdate() {
        com.tencent.tbs.one.impl.a.g.a("[%s] Canceling update", this.f22193b.f22205b);
        o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.e.g.2
            @Override // java.lang.Runnable
            public final void run() {
                g.a(g.this);
            }
        });
    }

    @Override // com.tencent.tbs.one.TBSOneOnlineService
    public final void update(final Bundle bundle, final TBSOneCallback<Void> tBSOneCallback) {
        String str = this.f22193b.f22205b;
        com.tencent.tbs.one.impl.a.g.a("[%s] Updating", str);
        if (!this.f22193b.h()) {
            o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.e.g.1
                @Override // java.lang.Runnable
                public final void run() throws IOException {
                    g.a(g.this, bundle, tBSOneCallback);
                }
            });
            return;
        }
        com.tencent.tbs.one.impl.a.g.a("[%s] No need to update because the last update has not applied", str);
        if (tBSOneCallback != null) {
            tBSOneCallback.onCompleted(null);
        }
    }
}
