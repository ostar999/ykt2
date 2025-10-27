package com.beizi.ad.internal.c.a;

import com.beizi.ad.internal.utilities.HaoboLog;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
abstract class e implements com.beizi.ad.internal.c.a.a {

    /* renamed from: a, reason: collision with root package name */
    private final ExecutorService f4055a = Executors.newSingleThreadExecutor();

    public class a implements Callable<Void> {

        /* renamed from: b, reason: collision with root package name */
        private final File f4057b;

        public a(File file) {
            this.f4057b = file;
        }

        @Override // java.util.concurrent.Callable
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Void call() throws Exception {
            e.this.b(this.f4057b);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(File file) throws IOException {
        d.c(file);
        a(d.b(file.getParentFile()));
    }

    public abstract boolean a(File file, long j2, int i2);

    @Override // com.beizi.ad.internal.c.a.a
    public void a(File file) throws IOException {
        this.f4055a.submit(new a(file));
    }

    private void a(List<File> list) {
        long jB = b(list);
        int size = list.size();
        for (File file : list) {
            if (!a(file, jB, size)) {
                long length = file.length();
                if (file.delete()) {
                    size--;
                    jB -= length;
                    HaoboLog.i(HaoboLog.lruDiskUsageLogTag, "Cache file " + file + " is deleted because it exceeds cache limit");
                } else {
                    HaoboLog.e(HaoboLog.lruDiskUsageLogTag, "Error deleting file " + file + " for trimming cache");
                }
            }
        }
    }

    private long b(List<File> list) {
        Iterator<File> it = list.iterator();
        long length = 0;
        while (it.hasNext()) {
            length += it.next().length();
        }
        return length;
    }
}
