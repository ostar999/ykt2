package com.tencent.open.log;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public class f implements Iterable<String> {

    /* renamed from: a, reason: collision with root package name */
    private ConcurrentLinkedQueue<String> f20640a;

    /* renamed from: b, reason: collision with root package name */
    private AtomicInteger f20641b;

    public f() {
        this.f20640a = null;
        this.f20641b = null;
        this.f20640a = new ConcurrentLinkedQueue<>();
        this.f20641b = new AtomicInteger(0);
    }

    public int a(String str) {
        int length = str.length();
        this.f20640a.add(str);
        return this.f20641b.addAndGet(length);
    }

    public void b() {
        this.f20640a.clear();
        this.f20641b.set(0);
    }

    @Override // java.lang.Iterable
    public Iterator<String> iterator() {
        return this.f20640a.iterator();
    }

    public void a(Writer[] writerArr, char[] cArr) throws IOException {
        if (writerArr == null || cArr == null || cArr.length == 0 || writerArr.length < 2) {
            return;
        }
        Writer writer = writerArr[0];
        Writer writer2 = writerArr[1];
        int length = cArr.length;
        Iterator<String> it = iterator();
        int i2 = 0;
        int i3 = length;
        while (it.hasNext()) {
            String next = it.next();
            int length2 = next.length();
            int i4 = 0;
            while (length2 > 0) {
                int i5 = i3 > length2 ? length2 : i3;
                int i6 = i4 + i5;
                next.getChars(i4, i6, cArr, i2);
                i3 -= i5;
                i2 += i5;
                length2 -= i5;
                if (i3 == 0) {
                    if (writer != null) {
                        try {
                            writer.write(cArr, 0, length);
                        } catch (Exception unused) {
                        }
                    }
                    if (writer2 != null) {
                        try {
                            writer2.write(cArr, 0, length);
                        } catch (Exception unused2) {
                        }
                    }
                    i2 = 0;
                    i3 = length;
                }
                i4 = i6;
            }
        }
        if (i2 > 0) {
            if (writer != null) {
                try {
                    writer.write(cArr, 0, i2);
                } catch (Exception unused3) {
                }
            }
            if (writer2 != null) {
                try {
                    writer2.write(cArr, 0, i2);
                } catch (Exception unused4) {
                }
            }
        }
        if (writer != null) {
            try {
                writer.flush();
            } catch (Exception unused5) {
            }
        }
        if (writer2 != null) {
            try {
                writer2.flush();
            } catch (Exception unused6) {
            }
        }
    }

    public int a() {
        return this.f20641b.get();
    }
}
