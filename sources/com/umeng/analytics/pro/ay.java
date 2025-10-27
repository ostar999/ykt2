package com.umeng.analytics.pro;

import com.umeng.analytics.pro.bo;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class ay {

    /* renamed from: a, reason: collision with root package name */
    private final bu f22545a;

    /* renamed from: b, reason: collision with root package name */
    private final ch f22546b;

    public ay() {
        this(new bo.a());
    }

    private bp j(byte[] bArr, bc bcVar, bc... bcVarArr) throws bb {
        this.f22546b.a(bArr);
        int length = bcVarArr.length + 1;
        bc[] bcVarArr2 = new bc[length];
        int i2 = 0;
        bcVarArr2[0] = bcVar;
        int i3 = 0;
        while (i3 < bcVarArr.length) {
            int i4 = i3 + 1;
            bcVarArr2[i4] = bcVarArr[i3];
            i3 = i4;
        }
        this.f22545a.j();
        bp bpVarL = null;
        while (i2 < length) {
            bpVarL = this.f22545a.l();
            if (bpVarL.f22625b == 0 || bpVarL.f22626c > bcVarArr2[i2].a()) {
                return null;
            }
            if (bpVarL.f22626c != bcVarArr2[i2].a()) {
                bx.a(this.f22545a, bpVarL.f22625b);
                this.f22545a.m();
            } else {
                i2++;
                if (i2 < length) {
                    this.f22545a.j();
                }
            }
        }
        return bpVarL;
    }

    public void a(av avVar, byte[] bArr) throws bb {
        try {
            this.f22546b.a(bArr);
            avVar.read(this.f22545a);
        } finally {
            this.f22546b.e();
            this.f22545a.B();
        }
    }

    public Byte b(byte[] bArr, bc bcVar, bc... bcVarArr) throws bb {
        return (Byte) a((byte) 3, bArr, bcVar, bcVarArr);
    }

    public Double c(byte[] bArr, bc bcVar, bc... bcVarArr) throws bb {
        return (Double) a((byte) 4, bArr, bcVar, bcVarArr);
    }

    public Short d(byte[] bArr, bc bcVar, bc... bcVarArr) throws bb {
        return (Short) a((byte) 6, bArr, bcVar, bcVarArr);
    }

    public Integer e(byte[] bArr, bc bcVar, bc... bcVarArr) throws bb {
        return (Integer) a((byte) 8, bArr, bcVar, bcVarArr);
    }

    public Long f(byte[] bArr, bc bcVar, bc... bcVarArr) throws bb {
        return (Long) a((byte) 10, bArr, bcVar, bcVarArr);
    }

    public String g(byte[] bArr, bc bcVar, bc... bcVarArr) throws bb {
        return (String) a((byte) 11, bArr, bcVar, bcVarArr);
    }

    public ByteBuffer h(byte[] bArr, bc bcVar, bc... bcVarArr) throws bb {
        return (ByteBuffer) a((byte) 100, bArr, bcVar, bcVarArr);
    }

    public Short i(byte[] bArr, bc bcVar, bc... bcVarArr) throws bb {
        try {
            try {
                if (j(bArr, bcVar, bcVarArr) != null) {
                    this.f22545a.j();
                    return Short.valueOf(this.f22545a.l().f22626c);
                }
                this.f22546b.e();
                this.f22545a.B();
                return null;
            } catch (Exception e2) {
                throw new bb(e2);
            }
        } finally {
            this.f22546b.e();
            this.f22545a.B();
        }
    }

    public ay(bw bwVar) {
        ch chVar = new ch();
        this.f22546b = chVar;
        this.f22545a = bwVar.a(chVar);
    }

    public void a(av avVar, String str, String str2) throws bb {
        try {
            try {
                a(avVar, str.getBytes(str2));
            } catch (UnsupportedEncodingException unused) {
                throw new bb("JVM DOES NOT SUPPORT ENCODING: " + str2);
            }
        } finally {
            this.f22545a.B();
        }
    }

    public void a(av avVar, byte[] bArr, bc bcVar, bc... bcVarArr) throws bb {
        try {
            try {
                if (j(bArr, bcVar, bcVarArr) != null) {
                    avVar.read(this.f22545a);
                }
            } catch (Exception e2) {
                throw new bb(e2);
            }
        } finally {
            this.f22546b.e();
            this.f22545a.B();
        }
    }

    public Boolean a(byte[] bArr, bc bcVar, bc... bcVarArr) throws bb {
        return (Boolean) a((byte) 2, bArr, bcVar, bcVarArr);
    }

    private Object a(byte b3, byte[] bArr, bc bcVar, bc... bcVarArr) throws bb {
        try {
            try {
                bp bpVarJ = j(bArr, bcVar, bcVarArr);
                if (bpVarJ != null) {
                    if (b3 != 2) {
                        if (b3 != 3) {
                            if (b3 != 4) {
                                if (b3 != 6) {
                                    if (b3 != 8) {
                                        if (b3 != 100) {
                                            if (b3 != 10) {
                                                if (b3 == 11 && bpVarJ.f22625b == 11) {
                                                    return this.f22545a.z();
                                                }
                                            } else if (bpVarJ.f22625b == 10) {
                                                return Long.valueOf(this.f22545a.x());
                                            }
                                        } else if (bpVarJ.f22625b == 11) {
                                            return this.f22545a.A();
                                        }
                                    } else if (bpVarJ.f22625b == 8) {
                                        return Integer.valueOf(this.f22545a.w());
                                    }
                                } else if (bpVarJ.f22625b == 6) {
                                    return Short.valueOf(this.f22545a.v());
                                }
                            } else if (bpVarJ.f22625b == 4) {
                                return Double.valueOf(this.f22545a.y());
                            }
                        } else if (bpVarJ.f22625b == 3) {
                            return Byte.valueOf(this.f22545a.u());
                        }
                    } else if (bpVarJ.f22625b == 2) {
                        return Boolean.valueOf(this.f22545a.t());
                    }
                }
                this.f22546b.e();
                this.f22545a.B();
                return null;
            } catch (Exception e2) {
                throw new bb(e2);
            }
        } finally {
            this.f22546b.e();
            this.f22545a.B();
        }
    }

    public void a(av avVar, String str) throws bb {
        a(avVar, str.getBytes());
    }
}
