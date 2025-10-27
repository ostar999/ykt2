package com.tencent.tbs.one.impl.c;

import android.content.Context;
import com.tencent.tbs.one.TBSOneComponent;
import java.io.File;

/* loaded from: classes6.dex */
public final class a implements TBSOneComponent {

    /* renamed from: a, reason: collision with root package name */
    public Context f21803a;

    /* renamed from: b, reason: collision with root package name */
    public ClassLoader f21804b;

    /* renamed from: c, reason: collision with root package name */
    public Object f21805c;

    /* renamed from: d, reason: collision with root package name */
    public String f21806d;

    /* renamed from: e, reason: collision with root package name */
    public String f21807e;

    /* renamed from: f, reason: collision with root package name */
    public int f21808f;

    /* renamed from: g, reason: collision with root package name */
    public File f21809g;

    public a(String str, String str2, int i2, File file) {
        this.f21806d = str;
        this.f21807e = str2;
        this.f21808f = i2;
        this.f21809g = file;
    }

    @Override // com.tencent.tbs.one.TBSOneComponent
    public final ClassLoader getEntryClassLoader() {
        return this.f21804b;
    }

    @Override // com.tencent.tbs.one.TBSOneComponent
    public final Object getEntryObject() {
        return this.f21805c;
    }

    @Override // com.tencent.tbs.one.TBSOneComponent
    public final File getInstallationDirectory() {
        return this.f21809g;
    }

    @Override // com.tencent.tbs.one.TBSOneComponent
    public final String getName() {
        return this.f21806d;
    }

    @Override // com.tencent.tbs.one.TBSOneComponent
    public final Context getResourcesContext() {
        return this.f21803a;
    }

    @Override // com.tencent.tbs.one.TBSOneComponent
    public final int getVersionCode() {
        return this.f21808f;
    }

    @Override // com.tencent.tbs.one.TBSOneComponent
    public final String getVersionName() {
        return this.f21807e;
    }

    public final String toString() {
        Object[] objArr = new Object[7];
        objArr[0] = this.f21806d;
        objArr[1] = this.f21807e;
        objArr[2] = Integer.valueOf(this.f21808f);
        objArr[3] = this.f21809g;
        objArr[4] = this.f21803a;
        objArr[5] = this.f21804b;
        Object obj = this.f21805c;
        objArr[6] = obj == null ? null : obj.getClass().getName();
        return String.format("{name: '%s', versionName: '%s', versionCode: %d, installationDirectory: '%s', apkContext: '%s', entryClassLoader: '%s', entryObject: '%s'}", objArr);
    }
}
