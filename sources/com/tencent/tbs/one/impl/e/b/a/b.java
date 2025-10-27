package com.tencent.tbs.one.impl.e.b.a;

import android.content.Context;
import com.tencent.tbs.one.TBSOneException;
import com.tencent.tbs.one.impl.a.o;
import com.tencent.tbs.one.impl.common.d;
import com.tencent.tbs.one.impl.e.e;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/* loaded from: classes6.dex */
public final class b extends com.tencent.tbs.one.impl.a.b<e<d>> {

    /* renamed from: b, reason: collision with root package name */
    public Context f22127b;

    /* renamed from: c, reason: collision with root package name */
    public String f22128c;

    /* renamed from: d, reason: collision with root package name */
    public File f22129d;

    public b(Context context, String str, File file) {
        this.f22127b = context;
        this.f22128c = str;
        this.f22129d = file;
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void a() {
        o.d(new Runnable() { // from class: com.tencent.tbs.one.impl.e.b.a.b.1
            @Override // java.lang.Runnable
            public final void run() {
                b bVar = b.this;
                Context context = bVar.f22127b;
                String str = bVar.f22128c;
                File file = bVar.f22129d;
                try {
                    InputStream inputStreamOpen = context.getAssets().open("webkit/repo/" + str + "/DEPS");
                    if (inputStreamOpen == null) {
                        bVar.a(324, "Failed to install deps from assets: webkit/repo/DEPS", new Throwable());
                        return;
                    }
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStreamOpen);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                    com.tencent.tbs.one.impl.a.d.a(bufferedInputStream, bufferedOutputStream);
                    com.tencent.tbs.one.impl.a.d.a(bufferedOutputStream);
                    com.tencent.tbs.one.impl.a.d.a(bufferedInputStream);
                    try {
                        bVar.a((b) e.a(e.a.BUILTIN_ASSETS, d.a(file)));
                    } catch (TBSOneException e2) {
                        bVar.a(e2.getErrorCode(), e2.getMessage(), e2.getCause());
                    }
                } catch (Throwable th) {
                    bVar.a(325, th.getMessage(), th.getCause());
                }
            }
        });
    }
}
