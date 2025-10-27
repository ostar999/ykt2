package com.tencent.tbs.one.impl.e.b.a;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.tbs.one.TBSOneException;
import com.tencent.tbs.one.impl.a.o;
import com.tencent.tbs.one.impl.common.d;
import com.tencent.tbs.one.impl.common.e;
import com.tencent.tbs.one.impl.common.f;
import com.tencent.tbs.one.impl.e.e;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/* loaded from: classes6.dex */
public final class a extends com.tencent.tbs.one.impl.a.b<e<File>> {

    /* renamed from: b, reason: collision with root package name */
    public Context f22120b;

    /* renamed from: c, reason: collision with root package name */
    public String f22121c;

    /* renamed from: d, reason: collision with root package name */
    public d.a f22122d;

    /* renamed from: e, reason: collision with root package name */
    public File f22123e;

    /* renamed from: f, reason: collision with root package name */
    public Bundle f22124f;

    /* renamed from: g, reason: collision with root package name */
    public String f22125g;

    public a(Context context, String str, d.a aVar, File file, Bundle bundle, String str2) {
        this.f22120b = context;
        this.f22121c = str;
        this.f22122d = aVar;
        this.f22123e = file;
        this.f22124f = bundle;
        this.f22125g = str2;
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void a() {
        o.d(new Runnable() { // from class: com.tencent.tbs.one.impl.e.b.a.a.1
            @Override // java.lang.Runnable
            public final void run() {
                a aVar = a.this;
                Context context = aVar.f22120b;
                String str = aVar.f22121c;
                d.a aVar2 = aVar.f22122d;
                File file = aVar.f22123e;
                File fileA = f.a(context);
                com.tencent.tbs.one.impl.a.d.d(file);
                File file2 = new File(file, "MANIFEST");
                String str2 = "webkit/repo/" + str + "/components/" + aVar2.f21992a + "/" + aVar2.f21994c;
                try {
                    InputStream inputStreamOpen = aVar.f22120b.getAssets().open(str2 + "/MANIFEST");
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    com.tencent.tbs.one.impl.a.d.a(inputStreamOpen, fileOutputStream);
                    com.tencent.tbs.one.impl.a.d.a(fileOutputStream);
                    com.tencent.tbs.one.impl.common.e eVarA = com.tencent.tbs.one.impl.common.e.a(file2);
                    int i2 = aVar.f22122d.f21994c;
                    int i3 = eVarA.f21998a;
                    MessageDigest messageDigestA = null;
                    if (i2 != i3) {
                        aVar.a(417, "Failed to verify version code, expected " + i2 + " but was " + i3, null);
                        return;
                    }
                    for (e.a aVar3 : eVarA.f22002e) {
                        String str3 = aVar3.f22006a;
                        File file3 = new File(file, str3);
                        String str4 = str2 + "/" + str3;
                        try {
                            FileOutputStream fileOutputStream2 = new FileOutputStream(file3);
                            com.tencent.tbs.one.impl.a.d.a(aVar.f22120b.getAssets().open(str4), fileOutputStream2);
                            com.tencent.tbs.one.impl.a.d.a(fileOutputStream2);
                            String str5 = aVar3.f22007b;
                            if (!TextUtils.isEmpty(str5)) {
                                if (messageDigestA == null) {
                                    try {
                                        messageDigestA = com.tencent.tbs.one.impl.e.f.a();
                                    } catch (TBSOneException e2) {
                                        aVar.a(e2.getErrorCode(), e2.getMessage(), e2.getCause());
                                        return;
                                    }
                                }
                                com.tencent.tbs.one.impl.e.f.a(messageDigestA, file3, str5);
                            }
                        } catch (IOException e3) {
                            aVar.a(326, "Failed to copy builtin component file from " + fileA.getAbsolutePath() + " to " + file3.getAbsolutePath(), e3);
                            return;
                        }
                    }
                    aVar.a((a) com.tencent.tbs.one.impl.e.e.a(e.a.BUILTIN_ASSETS, file));
                } catch (Throwable th) {
                    aVar.a(327, th.getMessage(), th.getCause());
                }
            }
        });
    }
}
