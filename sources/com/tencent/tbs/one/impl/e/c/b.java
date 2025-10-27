package com.tencent.tbs.one.impl.e.c;

import android.content.Context;
import com.tencent.tbs.one.TBSOneException;
import com.tencent.tbs.one.impl.a.o;
import com.tencent.tbs.one.impl.common.d;
import com.tencent.tbs.one.impl.common.f;
import com.tencent.tbs.one.impl.e.e;
import java.io.File;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class b extends com.tencent.tbs.one.impl.a.b<e<d>> {

    /* renamed from: b, reason: collision with root package name */
    public Context f22143b;

    /* renamed from: c, reason: collision with root package name */
    public String f22144c;

    /* renamed from: d, reason: collision with root package name */
    public File f22145d;

    public b(Context context, String str, File file) {
        this.f22143b = context;
        this.f22144c = str;
        this.f22145d = file;
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void a() {
        o.d(new Runnable() { // from class: com.tencent.tbs.one.impl.e.c.b.1
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                b bVar = b.this;
                Context context = bVar.f22143b;
                String str = bVar.f22144c;
                File file = bVar.f22145d;
                File fileA = f.a(context);
                try {
                    com.tencent.tbs.one.impl.a.d.a(f.c(fileA, str), file);
                    try {
                        bVar.a((b) e.a(e.a.BUILTIN, d.a(file)));
                    } catch (TBSOneException e2) {
                        bVar.a(e2.getErrorCode(), e2.getMessage(), e2.getCause());
                    }
                } catch (IOException e3) {
                    bVar.a(302, "Failed to copy builtin DEPS from " + fileA.getAbsolutePath() + " to " + file.getAbsolutePath(), e3);
                }
            }
        });
    }
}
