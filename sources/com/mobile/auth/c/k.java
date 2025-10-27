package com.mobile.auth.c;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.tencent.connect.common.Constants;
import com.tencent.liteav.audio.TXEAudioDef;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class k {
    private static int a(int i2) {
        int i3 = TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
        if (i2 != -101) {
            i3 = -1;
            if (i2 != -1) {
                switch (i2) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                    case 16:
                        return 1;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                    case 17:
                        return 2;
                    case 13:
                    case 18:
                    case 19:
                        return 3;
                    default:
                        return i2;
                }
            }
        }
        return i3;
    }

    public static NetworkInfo a(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static void a(Context context, long j2) {
        try {
            try {
                d.a(context, "key_s_p_dm_time", j2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void a(Context context, String str) {
        try {
            try {
                d.a(context, "key_s_p_dm", str);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static boolean b(Context context) {
        try {
            NetworkInfo networkInfoA = a(context);
            if (networkInfoA != null) {
                return networkInfoA.isAvailable();
            }
            return false;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static boolean c(Context context) {
        try {
            NetworkInfo networkInfoA = a(context);
            if (networkInfoA != null) {
                if (networkInfoA.getType() == 0) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static boolean d(Context context) {
        if (context == null) {
            return true;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            Method declaredMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(connectivityManager, new Object[0])).booleanValue();
        } catch (Throwable th) {
            try {
                com.mobile.auth.a.a.a("NetUtil", "isMobileEnable error ", th);
                return true;
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return false;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return false;
                }
            }
        }
    }

    public static String e(Context context) {
        try {
            int iK = k(context);
            return iK != -101 ? (iK == -1 || iK == 0) ? "null" : iK != 1 ? iK != 2 ? iK != 3 ? Integer.toString(iK) : "4G" : "3G" : "2G" : "WIFI";
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static String f(Context context) {
        try {
            String strE = e(context);
            if (strE != null && strE.equals("WIFI")) {
                if (d(context)) {
                    return "BOTH";
                }
            }
            return strE;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static String g(Context context) {
        try {
            String strF = f(context);
            if (!TextUtils.isEmpty(strF) && !strF.equals("null")) {
                if (strF.equals("2G")) {
                    return Constants.VIA_REPORT_TYPE_SHARE_TO_QQ;
                }
                if (strF.equals("3G")) {
                    return Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE;
                }
                if (strF.equals("4G")) {
                    return Constants.VIA_REPORT_TYPE_SET_AVATAR;
                }
                if (strF.equals("WIFI")) {
                    return Constants.VIA_REPORT_TYPE_JOININ_GROUP;
                }
                if (strF.equals("BOTH")) {
                    return Constants.VIA_REPORT_TYPE_MAKE_FRIEND;
                }
            }
            return Constants.VIA_REPORT_TYPE_WPA_STATE;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static String h(Context context) {
        try {
            if (System.currentTimeMillis() - i(context) > 172800000) {
                return "1";
            }
            try {
                return d.b(context, "key_s_p_dm", "1");
            } catch (Exception e2) {
                e2.printStackTrace();
                return "1";
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static long i(Context context) {
        Long lValueOf;
        try {
            try {
                lValueOf = Long.valueOf(d.b(context, "key_s_p_dm_time", 0L));
            } catch (Exception e2) {
                e2.printStackTrace();
                lValueOf = null;
            }
            return lValueOf.longValue();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1L;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1L;
            }
        }
    }

    public static String j(Context context) {
        try {
            String strH = h(context);
            return (TextUtils.isEmpty(strH) || strH.equals("1")) ? "https://id6.me/auth/preauth.do" : strH.equals("2") ? "https://card.e.189.cn/auth/preauth.do" : "https://id6.me/auth/preauth.do";
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private static int k(Context context) {
        int subtype = 0;
        try {
            try {
                NetworkInfo networkInfoA = a(context);
                if (networkInfoA != null && networkInfoA.isAvailable() && networkInfoA.isConnected()) {
                    int type = networkInfoA.getType();
                    if (type == 1) {
                        subtype = TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
                    } else if (type == 0) {
                        subtype = networkInfoA.getSubtype();
                    }
                } else {
                    subtype = -1;
                }
            } catch (NullPointerException e2) {
                e = e2;
                e.printStackTrace();
                return a(subtype);
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                return a(subtype);
            }
            return a(subtype);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
    }
}
