package com.tencent.tbs.logger.file.backup;

import java.io.File;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public long f21678a;

    public a(long j2) {
        this.f21678a = j2;
    }

    public boolean a(File file) {
        return file != null && file.length() > this.f21678a;
    }
}
