package com.alipay.sdk.packet;

import com.alipay.sdk.util.l;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Locale;

/* loaded from: classes2.dex */
public final class e {

    /* renamed from: a, reason: collision with root package name */
    private boolean f3305a;

    /* renamed from: b, reason: collision with root package name */
    private String f3306b = l.e();

    public e(boolean z2) {
        this.f3305a = z2;
    }

    private static byte[] b(String str, byte[] bArr) {
        return com.alipay.sdk.encrypt.e.b(str, bArr);
    }

    public final b a(c cVar) throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        String str;
        String str2;
        ByteArrayInputStream byteArrayInputStream2 = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(cVar.f3284b);
        } catch (Exception unused) {
            byteArrayInputStream = null;
            str = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            try {
                byte[] bArr = new byte[5];
                byteArrayInputStream.read(bArr);
                byte[] bArr2 = new byte[Integer.parseInt(new String(bArr))];
                byteArrayInputStream.read(bArr2);
                str = new String(bArr2);
                try {
                    byte[] bArr3 = new byte[5];
                    byteArrayInputStream.read(bArr3);
                    int i2 = Integer.parseInt(new String(bArr3));
                    if (i2 > 0) {
                        byte[] bArrB = new byte[i2];
                        byteArrayInputStream.read(bArrB);
                        if (this.f3305a) {
                            bArrB = com.alipay.sdk.encrypt.e.b(this.f3306b, bArrB);
                        }
                        if (cVar.f3283a) {
                            bArrB = com.alipay.sdk.encrypt.c.b(bArrB);
                        }
                        str2 = new String(bArrB);
                    } else {
                        str2 = null;
                    }
                    try {
                        byteArrayInputStream.close();
                    } catch (Exception unused2) {
                    }
                } catch (Exception unused3) {
                    if (byteArrayInputStream != null) {
                        try {
                            byteArrayInputStream.close();
                        } catch (Exception unused4) {
                        }
                    }
                    str2 = null;
                    if (str == null) {
                    }
                    return new b(str, str2);
                }
            } catch (Exception unused5) {
                str = null;
            }
            if (str == null || str2 != null) {
                return new b(str, str2);
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            byteArrayInputStream2 = byteArrayInputStream;
            if (byteArrayInputStream2 != null) {
                try {
                    byteArrayInputStream2.close();
                } catch (Exception unused6) {
                }
            }
            throw th;
        }
    }

    private static byte[] a(String str, String str2) {
        return com.alipay.sdk.encrypt.d.a(str, str2);
    }

    private static byte[] a(String str, byte[] bArr) {
        return com.alipay.sdk.encrypt.e.a(str, bArr);
    }

    private static byte[] a(byte[]... bArr) throws Throwable {
        DataOutputStream dataOutputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        byteArray = null;
        byte[] byteArray = null;
        if (bArr.length == 0) {
            return null;
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                for (int i2 = 0; i2 < bArr.length; i2++) {
                    try {
                        dataOutputStream.write(String.format(Locale.getDefault(), "%05d", Integer.valueOf(bArr[i2].length)).getBytes());
                        dataOutputStream.write(bArr[i2]);
                    } catch (Exception unused) {
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception unused2) {
                            }
                        }
                        if (dataOutputStream != null) {
                            dataOutputStream.close();
                        }
                        return byteArray;
                    } catch (Throwable th) {
                        th = th;
                        byteArrayOutputStream2 = byteArrayOutputStream;
                        if (byteArrayOutputStream2 != null) {
                            try {
                                byteArrayOutputStream2.close();
                            } catch (Exception unused3) {
                            }
                        }
                        if (dataOutputStream != null) {
                            try {
                                dataOutputStream.close();
                                throw th;
                            } catch (Exception unused4) {
                                throw th;
                            }
                        }
                        throw th;
                    }
                }
                dataOutputStream.flush();
                byteArray = byteArrayOutputStream.toByteArray();
                try {
                    byteArrayOutputStream.close();
                } catch (Exception unused5) {
                }
            } catch (Exception unused6) {
                dataOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                dataOutputStream = null;
            }
        } catch (Exception unused7) {
            byteArrayOutputStream = null;
            dataOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            dataOutputStream = null;
        }
        try {
            dataOutputStream.close();
        } catch (Exception unused8) {
        }
        return byteArray;
    }

    private static String a(int i2) {
        return String.format(Locale.getDefault(), "%05d", Integer.valueOf(i2));
    }

    private static int a(String str) {
        return Integer.parseInt(str);
    }

    public final c a(b bVar, boolean z2) throws Throwable {
        byte[] bArrA;
        byte[] bytes = bVar.f3281a.getBytes();
        byte[] bytes2 = bVar.f3282b.getBytes();
        if (z2) {
            try {
                bytes2 = com.alipay.sdk.encrypt.c.a(bytes2);
            } catch (Exception unused) {
                z2 = false;
            }
        }
        if (this.f3305a) {
            bArrA = a(bytes, com.alipay.sdk.encrypt.d.a(this.f3306b, com.alipay.sdk.cons.a.f3197c), com.alipay.sdk.encrypt.e.a(this.f3306b, bytes2));
        } else {
            bArrA = a(bytes, bytes2);
        }
        return new c(z2, bArrA);
    }
}
