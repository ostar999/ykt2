package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes6.dex */
public class di {
    public static int a(Context context, int i2) {
        int iA = hh.a(context);
        if (-1 == iA) {
            return -1;
        }
        return (i2 * (iA == 0 ? 13 : 11)) / 10;
    }

    public static int a(hw hwVar) {
        return fk.a(hwVar.a());
    }

    public static int a(jq jqVar, hw hwVar) {
        int iA;
        switch (dj.f24726a[hwVar.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                return fk.a(hwVar.a());
            case 11:
                iA = fk.a(hwVar.a());
                if (jqVar != null) {
                    try {
                        if (jqVar instanceof iw) {
                            String str = ((iw) jqVar).f714d;
                            if (!TextUtils.isEmpty(str) && fk.a(fk.m417a(str)) != -1) {
                                iA = fk.a(fk.m417a(str));
                                break;
                            }
                        } else if (jqVar instanceof je) {
                            String str2 = ((je) jqVar).f774d;
                            if (!TextUtils.isEmpty(str2)) {
                                if (fk.a(fk.m417a(str2)) != -1) {
                                    iA = fk.a(fk.m417a(str2));
                                }
                                if (in.UploadTinyData.equals(fk.m417a(str2))) {
                                    return -1;
                                }
                            }
                        }
                    } catch (Exception unused) {
                        com.xiaomi.channel.commonutils.logger.b.d("PERF_ERROR : parse Notification type error");
                        return iA;
                    }
                }
                break;
            case 12:
                iA = fk.a(hwVar.a());
                if (jqVar != null) {
                    try {
                        if (jqVar instanceof ja) {
                            String strA = ((ja) jqVar).a();
                            if (!TextUtils.isEmpty(strA) && fq.a(strA) != -1) {
                                iA = fq.a(strA);
                                break;
                            }
                        } else if (jqVar instanceof iz) {
                            String strA2 = ((iz) jqVar).a();
                            if (!TextUtils.isEmpty(strA2) && fq.a(strA2) != -1) {
                                return fq.a(strA2);
                            }
                        }
                    } catch (Exception unused2) {
                        com.xiaomi.channel.commonutils.logger.b.d("PERF_ERROR : parse Command type error");
                        break;
                    }
                }
                break;
            default:
                return -1;
        }
        return iA;
    }

    public static void a(String str, Context context, int i2, int i3) {
        if (i2 <= 0 || i3 <= 0) {
            return;
        }
        int iA = a(context, i3);
        if (i2 != fk.a(in.UploadTinyData)) {
            com.xiaomi.channel.commonutils.logger.b.m117a("Perf_code  packetType  " + i2 + "  length " + iA);
            fl.a(context.getApplicationContext()).a(str, i2, 1L, (long) iA);
        }
    }

    public static void a(String str, Context context, jb jbVar, int i2) {
        hw hwVarA;
        if (context == null || jbVar == null || (hwVarA = jbVar.a()) == null) {
            return;
        }
        int iA = a(hwVarA);
        if (i2 <= 0) {
            byte[] bArrA = jp.a(jbVar);
            i2 = bArrA != null ? bArrA.length : 0;
        }
        a(str, context, iA, i2);
    }

    public static void a(String str, Context context, jq jqVar, hw hwVar, int i2) {
        a(str, context, a(jqVar, hwVar), i2);
    }

    public static void a(String str, Context context, byte[] bArr) {
        if (context == null || bArr == null || bArr.length <= 0) {
            return;
        }
        jb jbVar = new jb();
        try {
            jp.a(jbVar, bArr);
            a(str, context, jbVar, bArr.length);
        } catch (jv unused) {
            com.xiaomi.channel.commonutils.logger.b.m117a("fail to convert bytes to container");
        }
    }
}
