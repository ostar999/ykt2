package com.tencent.tbs.logger.file.clean;

import java.io.File;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public long f21679a;

    public a(long j2) {
        this.f21679a = j2;
    }

    public boolean a(File file) {
        return file != null && System.currentTimeMillis() - file.lastModified() > this.f21679a;
    }
}
