package com.tencent.tbs.one.impl.e.d;

import android.content.Context;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.TBSOneException;
import com.tencent.tbs.one.impl.a.g;
import com.tencent.tbs.one.impl.a.m;
import com.tencent.tbs.one.impl.common.d;
import com.tencent.tbs.one.impl.d.a;
import com.tencent.tbs.one.impl.e.e;
import com.tencent.tbs.one.optional.TBSOneRuntimeExtension;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public final class b extends com.tencent.tbs.one.impl.a.b<e<d>> implements a.InterfaceC0368a {

    /* renamed from: b, reason: collision with root package name */
    public com.tencent.tbs.one.impl.d.a f22166b;

    /* renamed from: c, reason: collision with root package name */
    public int f22167c;

    /* renamed from: d, reason: collision with root package name */
    public Context f22168d;

    /* renamed from: e, reason: collision with root package name */
    public String f22169e;

    /* renamed from: f, reason: collision with root package name */
    public String f22170f;

    /* renamed from: g, reason: collision with root package name */
    public File f22171g;

    public b(Context context, String str, String str2, File file) {
        this.f22168d = context;
        this.f22169e = str;
        this.f22170f = str2;
        this.f22171g = file;
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void a() {
        com.tencent.tbs.one.impl.d.a aVar = new com.tencent.tbs.one.impl.d.a(this.f22168d, this.f22170f);
        this.f22166b = aVar;
        aVar.f22045f = this;
        aVar.a((m) new m<Integer>() { // from class: com.tencent.tbs.one.impl.e.d.b.1
            @Override // com.tencent.tbs.one.impl.a.m
            public final void a(int i2, int i3) {
                b.this.a(i3);
            }

            @Override // com.tencent.tbs.one.impl.a.m
            public final void a(int i2, String str, Throwable th) {
                b bVar = b.this;
                bVar.f22166b = null;
                bVar.a(i2, str, th);
            }
        });
    }

    @Override // com.tencent.tbs.one.impl.d.a.InterfaceC0368a
    public final void a(int i2, Map<String, List<String>> map, InputStream inputStream) {
        int iShouldInterceptDEPSResponse;
        Context context = this.f22168d;
        String str = this.f22170f;
        final String str2 = this.f22169e;
        final File file = this.f22171g;
        g.a("[%s] Receiving DEPS response: [%d] %s", str2, Integer.valueOf(i2), map);
        if (i2 != 200 || inputStream == null) {
            a(210, "Invalid DEPS response stream, url: " + str + ", statusCode: " + i2, (Throwable) null);
            return;
        }
        TBSOneRuntimeExtension tBSOneRuntimeExtensionA = com.tencent.tbs.one.impl.common.a.a(context, str2);
        if (tBSOneRuntimeExtensionA != null && (iShouldInterceptDEPSResponse = tBSOneRuntimeExtensionA.shouldInterceptDEPSResponse(str2, null, inputStream, file, new TBSOneCallback<Void>() { // from class: com.tencent.tbs.one.impl.e.d.b.2
            @Override // com.tencent.tbs.one.TBSOneCallback
            public final /* synthetic */ void onCompleted(Void r4) {
                g.a("[%s] Finished intercepting DEPS download stream by runtime extension", str2);
                b.this.f22167c = 0;
                try {
                    b.this.a((b) e.a(e.a.EXTENSION, d.a(file)));
                } catch (TBSOneException e2) {
                    b.this.a(e2.getErrorCode(), e2.getMessage(), e2.getCause());
                }
            }

            @Override // com.tencent.tbs.one.TBSOneCallback
            public final void onError(int i3, String str3) {
                b bVar = b.this;
                bVar.f22167c = 0;
                bVar.a(i3, str3, (Throwable) null);
            }

            @Override // com.tencent.tbs.one.TBSOneCallback
            public final void onProgressChanged(int i3, int i4) {
                b.this.a(i4);
            }
        })) != 0) {
            g.a("[%s] Intercepted DEPS download stream by runtime extension", str2);
            this.f22167c = iShouldInterceptDEPSResponse;
            return;
        }
        try {
            a((b) e.a(e.a.ONLINE, d.a(com.tencent.tbs.one.impl.a.d.a(inputStream, "utf-8", file))));
        } catch (TBSOneException e2) {
            a(e2.getErrorCode(), e2.getMessage(), e2.getCause());
        } catch (IOException e3) {
            a(305, "Failed to download online DEPS from " + str + " to " + file.getAbsolutePath(), e3);
        }
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void b() {
        TBSOneRuntimeExtension tBSOneRuntimeExtensionA;
        super.b();
        com.tencent.tbs.one.impl.d.a aVar = this.f22166b;
        if (aVar != null) {
            aVar.b();
        }
        if (this.f22167c == 0 || (tBSOneRuntimeExtensionA = com.tencent.tbs.one.impl.common.a.a(this.f22168d, this.f22169e)) == null) {
            return;
        }
        tBSOneRuntimeExtensionA.cancel(this.f22167c);
    }
}
