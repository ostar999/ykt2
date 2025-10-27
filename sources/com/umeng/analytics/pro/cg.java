package com.umeng.analytics.pro;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes6.dex */
public class cg extends ci {

    /* renamed from: a, reason: collision with root package name */
    protected InputStream f22667a;

    /* renamed from: b, reason: collision with root package name */
    protected OutputStream f22668b;

    public cg() {
        this.f22667a = null;
        this.f22668b = null;
    }

    @Override // com.umeng.analytics.pro.ci
    public int a(byte[] bArr, int i2, int i3) throws cj, IOException {
        InputStream inputStream = this.f22667a;
        if (inputStream == null) {
            throw new cj(1, "Cannot read from null inputStream");
        }
        try {
            int i4 = inputStream.read(bArr, i2, i3);
            if (i4 >= 0) {
                return i4;
            }
            throw new cj(4);
        } catch (IOException e2) {
            throw new cj(0, e2);
        }
    }

    @Override // com.umeng.analytics.pro.ci
    public boolean a() {
        return true;
    }

    @Override // com.umeng.analytics.pro.ci
    public void b() throws cj {
    }

    @Override // com.umeng.analytics.pro.ci
    public void b(byte[] bArr, int i2, int i3) throws cj, IOException {
        OutputStream outputStream = this.f22668b;
        if (outputStream == null) {
            throw new cj(1, "Cannot write to null outputStream");
        }
        try {
            outputStream.write(bArr, i2, i3);
        } catch (IOException e2) {
            throw new cj(0, e2);
        }
    }

    @Override // com.umeng.analytics.pro.ci
    public void c() throws IOException {
        InputStream inputStream = this.f22667a;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            this.f22667a = null;
        }
        OutputStream outputStream = this.f22668b;
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            this.f22668b = null;
        }
    }

    @Override // com.umeng.analytics.pro.ci
    public void d() throws cj, IOException {
        OutputStream outputStream = this.f22668b;
        if (outputStream == null) {
            throw new cj(1, "Cannot flush null outputStream");
        }
        try {
            outputStream.flush();
        } catch (IOException e2) {
            throw new cj(0, e2);
        }
    }

    public cg(InputStream inputStream) {
        this.f22668b = null;
        this.f22667a = inputStream;
    }

    public cg(OutputStream outputStream) {
        this.f22667a = null;
        this.f22668b = outputStream;
    }

    public cg(InputStream inputStream, OutputStream outputStream) {
        this.f22667a = inputStream;
        this.f22668b = outputStream;
    }
}
