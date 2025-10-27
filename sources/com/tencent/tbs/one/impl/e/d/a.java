package com.tencent.tbs.one.impl.e.d;

import android.content.Context;
import android.os.Bundle;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.TBSOneException;
import com.tencent.tbs.one.impl.a.g;
import com.tencent.tbs.one.impl.a.l;
import com.tencent.tbs.one.impl.a.m;
import com.tencent.tbs.one.impl.common.d;
import com.tencent.tbs.one.impl.d.a;
import com.tencent.tbs.one.impl.e.e;
import com.tencent.tbs.one.impl.e.f;
import com.tencent.tbs.one.optional.TBSOneRuntimeExtension;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public final class a extends com.tencent.tbs.one.impl.a.b<e<File>> implements a.InterfaceC0368a {

    /* renamed from: b, reason: collision with root package name */
    public int f22151b;

    /* renamed from: c, reason: collision with root package name */
    public Context f22152c;

    /* renamed from: d, reason: collision with root package name */
    public String f22153d;

    /* renamed from: e, reason: collision with root package name */
    public d.a f22154e;

    /* renamed from: f, reason: collision with root package name */
    public File f22155f;

    /* renamed from: g, reason: collision with root package name */
    public File f22156g;

    /* renamed from: h, reason: collision with root package name */
    public com.tencent.tbs.one.impl.d.a f22157h;

    /* renamed from: i, reason: collision with root package name */
    public long f22158i = -1;

    /* renamed from: j, reason: collision with root package name */
    public Bundle f22159j;

    public a(Context context, String str, d.a aVar, File file, Bundle bundle) {
        this.f22152c = context;
        this.f22153d = str;
        this.f22154e = aVar;
        this.f22155f = file;
        this.f22159j = bundle;
        String str2 = aVar.f21992a;
        int i2 = aVar.f21994c;
        File externalFilesDir = context.getExternalFilesDir("tbs");
        File file2 = externalFilesDir != null ? new File(new File(externalFilesDir, str), str2) : null;
        this.f22156g = file2 != null ? new File(file2, String.valueOf(i2)) : null;
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void a() {
        com.tencent.tbs.one.impl.d.a aVar = new com.tencent.tbs.one.impl.d.a(this.f22152c, this.f22154e.f21995d);
        this.f22157h = aVar;
        aVar.f22045f = this;
        aVar.a((m) new m<Integer>() { // from class: com.tencent.tbs.one.impl.e.d.a.1
            @Override // com.tencent.tbs.one.impl.a.m
            public final void a(int i2, int i3) {
                a.this.a(i3);
            }

            @Override // com.tencent.tbs.one.impl.a.m
            public final void a(int i2, String str, Throwable th) {
                a.this.a(i2, str, th);
            }
        });
    }

    @Override // com.tencent.tbs.one.impl.d.a.InterfaceC0368a
    public final void a(int i2, Map<String, List<String>> map, InputStream inputStream) throws Throwable {
        File file;
        int iShouldInterceptComponentResponse;
        final String str = this.f22153d;
        d.a aVar = this.f22154e;
        final String str2 = aVar.f21992a;
        int i3 = aVar.f21994c;
        String str3 = aVar.f21995d;
        final File file2 = this.f22155f;
        File file3 = this.f22156g;
        if (file3 != null && !file3.exists()) {
            this.f22156g.mkdirs();
        }
        File file4 = this.f22156g;
        if (file4 == null) {
            file = null;
        } else {
            file = new File(file4, this.f22154e.f21992a + ".tbs");
        }
        g.a("[%s] {%s} Receiving component response: [%d] %s", str, str2, Integer.valueOf(i2), map);
        if (i2 != 200 || inputStream == null) {
            a(215, "Invalid component response stream, url: " + str3 + ", statusCode: " + i2, (Throwable) null);
            return;
        }
        List<String> list = map.get("Content-Length");
        if (list == null || list.size() <= 0) {
            g.a("No Content-Length header exists, url: %s", str3);
        } else {
            try {
                this.f22158i = Long.parseLong(list.get(0));
            } catch (Exception e2) {
                g.c("Failed to parse Content-Length header %s, url: %s", list, str3, e2);
            }
        }
        TBSOneRuntimeExtension tBSOneRuntimeExtensionA = com.tencent.tbs.one.impl.common.a.a(this.f22152c, str);
        if (tBSOneRuntimeExtensionA != null && (iShouldInterceptComponentResponse = tBSOneRuntimeExtensionA.shouldInterceptComponentResponse(str, str2, i3, null, inputStream, file2, new TBSOneCallback<Void>() { // from class: com.tencent.tbs.one.impl.e.d.a.2
            @Override // com.tencent.tbs.one.TBSOneCallback
            public final /* synthetic */ void onCompleted(Void r4) {
                g.a("[%s] {%s} Finished intercepting component download stream by runtime extension", str, str2);
                a aVar2 = a.this;
                aVar2.f22151b = 0;
                aVar2.a((a) e.a(e.a.EXTENSION, file2));
            }

            @Override // com.tencent.tbs.one.TBSOneCallback
            public final void onError(int i4, String str4) {
                a aVar2 = a.this;
                aVar2.f22151b = 0;
                aVar2.a(i4, str4, (Throwable) null);
            }

            @Override // com.tencent.tbs.one.TBSOneCallback
            public final void onProgressChanged(int i4, int i5) {
                a.this.a(i5);
            }
        })) != 0) {
            g.a("[%s] {%s} Intercepted component download stream by runtime extension", str, str2);
            this.f22151b = iShouldInterceptComponentResponse;
            return;
        }
        try {
            f.a(inputStream, this.f22154e.f21996e, this.f22158i, file2, file, new l.a() { // from class: com.tencent.tbs.one.impl.e.d.a.3
                @Override // com.tencent.tbs.one.impl.a.l.a
                public final void a(int i4) {
                    a.this.a(i4);
                }

                @Override // com.tencent.tbs.one.impl.a.l.a
                public final boolean a() {
                    return !a.this.f21723a;
                }
            });
            f.a(file2, file2);
            f.a(file2, i3);
            f.a(this.f22152c.getDir("tbs", 0));
            f.b(file2);
            a((a) e.a(e.a.ONLINE, file2));
        } catch (TBSOneException e3) {
            a(e3.getErrorCode(), e3.getMessage(), e3.getCause());
        }
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void b() {
        TBSOneRuntimeExtension tBSOneRuntimeExtensionA;
        super.b();
        com.tencent.tbs.one.impl.d.a aVar = this.f22157h;
        if (aVar != null) {
            aVar.b();
        }
        if (this.f22151b == 0 || (tBSOneRuntimeExtensionA = com.tencent.tbs.one.impl.common.a.a(this.f22152c, this.f22153d)) == null) {
            return;
        }
        tBSOneRuntimeExtensionA.cancel(this.f22151b);
    }
}
