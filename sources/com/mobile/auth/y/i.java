package com.mobile.auth.y;

import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes4.dex */
public final class i {
    public static String a() {
        try {
            try {
                String strReplace = UUID.randomUUID().toString().replace("-", "");
                return (TextUtils.isEmpty(strReplace) || strReplace.length() < 32) ? u.c().substring(0, 32) : strReplace;
            } catch (Exception unused) {
                t.b();
                return "";
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static String a(String str, String str2, String str3) {
        try {
            try {
                return d(str, str2, str3);
            } catch (Exception unused) {
                t.b();
                return null;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static String b(String str, String str2, String str3) {
        try {
            try {
                return c(str, str2, str3);
            } catch (Exception unused) {
                t.b();
                return null;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private static String c(String str, String str2, String str3) throws Exception {
        if (str != null) {
            try {
                if (str.length() != 0 && str.trim().length() != 0) {
                    if (str2 == null) {
                        throw new Exception("encrypt key is null");
                    }
                    if (str2.length() != 16) {
                        throw new Exception("encrypt key length error");
                    }
                    if (str3.length() != 16) {
                        throw new Exception(" iv encrypt key length error");
                    }
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    cipher.init(1, new SecretKeySpec(str2.getBytes("utf-8"), "AES"), new IvParameterSpec(str3.getBytes("utf-8")));
                    return j.a(cipher.doFinal(str.getBytes("utf-8")));
                }
            } catch (Exception e2) {
                throw new Exception("Encrypt error", e2);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }
        return null;
    }

    private static String d(String str, String str2, String str3) throws Exception {
        if (str != null) {
            try {
                if (str.length() != 0 && str.trim().length() != 0) {
                    if (str2 == null) {
                        throw new Exception("decrypt key is null");
                    }
                    if (str2.length() != 16) {
                        throw new Exception("decrypt key length error");
                    }
                    if (str3.length() != 16) {
                        throw new Exception(" iv decrypt key length error");
                    }
                    byte[] bArrB = j.b(str);
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    cipher.init(2, new SecretKeySpec(str2.getBytes("utf-8"), "AES"), new IvParameterSpec(str3.getBytes("utf-8")));
                    return new String(cipher.doFinal(bArrB), "utf-8");
                }
            } catch (Exception e2) {
                throw new Exception("decrypt error", e2);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }
        return null;
    }
}
