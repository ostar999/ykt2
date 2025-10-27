package com.tencent.tbs.one.impl.e;

import android.os.Process;
import java.io.File;

/* loaded from: classes6.dex */
public final class a extends Thread {

    /* renamed from: a, reason: collision with root package name */
    public final File f22055a;

    public a(File file) {
        this.f22055a = file;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() throws Throwable {
        super.run();
        com.tencent.tbs.one.impl.a.g.a("Running cleanup thread", new Object[0]);
        com.tencent.tbs.one.impl.a.d.a(com.tencent.tbs.one.impl.common.f.a(this.f22055a, Process.myPid()));
        File[] fileArrListFiles = com.tencent.tbs.one.impl.common.f.a(this.f22055a).listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                f.d(file);
            }
        }
    }
}
