package com.beizi.ad.internal.c.a;

import com.beizi.ad.internal.c.m;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes2.dex */
public class b implements com.beizi.ad.internal.c.a {

    /* renamed from: a, reason: collision with root package name */
    public File f4052a;

    /* renamed from: b, reason: collision with root package name */
    private final a f4053b;

    /* renamed from: c, reason: collision with root package name */
    private RandomAccessFile f4054c;

    public b(File file, a aVar) throws m {
        File file2;
        try {
            if (aVar == null) {
                throw new NullPointerException();
            }
            this.f4053b = aVar;
            d.a(file.getParentFile());
            boolean zExists = file.exists();
            if (zExists) {
                file2 = file;
            } else {
                file2 = new File(file.getParentFile(), file.getName() + ".download");
            }
            this.f4052a = file2;
            this.f4054c = new RandomAccessFile(this.f4052a, zExists ? "r" : InternalZipConstants.WRITE_MODE);
        } catch (IOException e2) {
            throw new m("Error using file " + file + " as disc cache", e2);
        }
    }

    @Override // com.beizi.ad.internal.c.a
    public synchronized int a() throws m {
        try {
        } catch (IOException e2) {
            throw new m("Error reading length of file " + this.f4052a, e2);
        }
        return (int) this.f4054c.length();
    }

    @Override // com.beizi.ad.internal.c.a
    public synchronized void b() throws m {
        try {
            this.f4054c.close();
            this.f4053b.a(this.f4052a);
        } catch (IOException e2) {
            throw new m("Error closing file " + this.f4052a, e2);
        }
    }

    @Override // com.beizi.ad.internal.c.a
    public synchronized void c() throws m {
        if (d()) {
            return;
        }
        b();
        File file = new File(this.f4052a.getParentFile(), this.f4052a.getName().substring(0, this.f4052a.getName().length() - 9));
        if (!this.f4052a.renameTo(file)) {
            throw new m("Error renaming file " + this.f4052a + " to " + file + " for completion!");
        }
        this.f4052a = file;
        try {
            this.f4054c = new RandomAccessFile(this.f4052a, "r");
        } catch (IOException e2) {
            throw new m("Error opening " + this.f4052a + " as disc cache", e2);
        }
    }

    @Override // com.beizi.ad.internal.c.a
    public synchronized boolean d() {
        return !a(this.f4052a);
    }

    @Override // com.beizi.ad.internal.c.a
    public synchronized int a(byte[] bArr, long j2, int i2) throws m {
        try {
            this.f4054c.seek(j2);
        } catch (IOException e2) {
            throw new m(String.format("Error reading %d bytes with offset %d failFrom file[%d bytes] to buffer[%d bytes]", Integer.valueOf(i2), Long.valueOf(j2), Integer.valueOf(a()), Integer.valueOf(bArr.length)), e2);
        }
        return this.f4054c.read(bArr, 0, i2);
    }

    @Override // com.beizi.ad.internal.c.a
    public synchronized void a(byte[] bArr, int i2) throws m {
        try {
            if (!d()) {
                this.f4054c.seek(a());
                this.f4054c.write(bArr, 0, i2);
            } else {
                throw new m("Error append cache: cache file " + this.f4052a + " is completed!");
            }
        } catch (IOException e2) {
            throw new m(String.format("Error writing %d bytes to %s failFrom buffer with size %d", Integer.valueOf(i2), this.f4054c, Integer.valueOf(bArr.length)), e2);
        }
    }

    private boolean a(File file) {
        return file.getName().endsWith(".download");
    }
}
