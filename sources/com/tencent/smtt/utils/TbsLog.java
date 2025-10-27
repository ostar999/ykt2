package com.tencent.smtt.utils;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import android.widget.TextView;
import com.tencent.connect.common.Constants;
import com.tencent.smtt.sdk.TbsConfig;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes6.dex */
public class TbsLog {
    public static final int TBSLOG_CODE_SDK_BASE = 1000;
    public static final int TBSLOG_CODE_SDK_CONFLICT_X5CORE = 993;
    public static final int TBSLOG_CODE_SDK_INIT = 999;
    public static final int TBSLOG_CODE_SDK_INVOKE_ERROR = 997;
    public static final int TBSLOG_CODE_SDK_LOAD_ERROR = 998;
    public static final int TBSLOG_CODE_SDK_NO_SHARE_X5CORE = 994;
    public static final int TBSLOG_CODE_SDK_SELF_MODE = 996;
    public static final int TBSLOG_CODE_SDK_THIRD_MODE = 995;
    public static final int TBSLOG_CODE_SDK_UNAVAIL_X5CORE = 992;
    public static final String X5LOGTAG = "x5logtag";

    /* renamed from: a, reason: collision with root package name */
    private static boolean f21430a = false;

    /* renamed from: b, reason: collision with root package name */
    private static boolean f21431b = true;

    /* renamed from: c, reason: collision with root package name */
    private static TbsLogClient f21432c;
    public static List<String> sTbsLogList = new LinkedList();
    public static int sLogMaxCount = 10;

    public static void addLog(int i2, String str, Object... objArr) {
        synchronized (sTbsLogList) {
            try {
                if (sTbsLogList.size() > sLogMaxCount) {
                    int size = sTbsLogList.size() - sLogMaxCount;
                    while (true) {
                        int i3 = size - 1;
                        if (size <= 0 || sTbsLogList.size() <= 0) {
                            break;
                        }
                        sTbsLogList.remove(0);
                        size = i3;
                    }
                }
                String str2 = null;
                if (str != null) {
                    try {
                        str2 = String.format(str, objArr);
                    } catch (Exception unused) {
                    }
                }
                if (str2 == null) {
                    str2 = "";
                }
                sTbsLogList.add(String.format("[%d][%d][%c][%d]%s", Long.valueOf(System.currentTimeMillis()), 1, '0', Integer.valueOf(i2), str2));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void app_extra(String str, Context context) {
        try {
            Context applicationContext = context.getApplicationContext();
            String[] strArr = {TbsConfig.APP_DEMO, TbsConfig.APP_QB, "com.tencent.mm", "com.tencent.mobileqq", TbsConfig.APP_DEMO_TEST, "com.qzone"};
            String[] strArr2 = {"DEMO", "QB", "WX", Constants.SOURCE_QQ, "TEST", "QZ"};
            int i2 = 0;
            while (true) {
                if (i2 >= 6) {
                    break;
                }
                if (applicationContext.getPackageName().contains(strArr[i2])) {
                    i(str, "app_extra pid:" + Process.myPid() + "; APP_TAG:" + strArr2[i2] + "!");
                    break;
                }
                i2++;
            }
            if (i2 == 6) {
                i(str, "app_extra pid:" + Process.myPid() + "; APP_TAG:OTHER!");
            }
        } catch (Throwable th) {
            w(str, "app_extra exception:" + Log.getStackTraceString(th));
        }
    }

    public static void d(String str, String str2) {
        d(str, "", str2);
    }

    public static void d(String str, String str2, String str3) {
        TbsLogClient tbsLogClient = f21432c;
        if (tbsLogClient == null) {
            return;
        }
        tbsLogClient.d(str, "TBS:" + str2 + " " + str3);
    }

    public static void d(String str, String str2, boolean z2) {
        d(str, str2);
        TbsLogClient tbsLogClient = f21432c;
        if (tbsLogClient != null && f21430a && z2) {
            tbsLogClient.showLog(str + ": " + str2);
        }
    }

    public static void e(String str, String str2) {
        e(str, "", str2);
    }

    public static void e(String str, String str2, String str3) {
        TbsLogClient tbsLogClient = f21432c;
        if (tbsLogClient == null) {
            return;
        }
        tbsLogClient.e(str, "TBS:" + str2 + " " + str3);
        f21432c.writeLog("(E)-" + str + "-TBS:" + str2 + " " + str3);
    }

    public static void e(String str, String str2, boolean z2) {
        e(str, str2);
        TbsLogClient tbsLogClient = f21432c;
        if (tbsLogClient != null && f21430a && z2) {
            tbsLogClient.showLog(str + ": " + str2);
        }
    }

    public static String getTbsLogFilePath() {
        File file = TbsLogClient.f21434c;
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public static void i(String str, String str2) {
        i(str, "", str2);
    }

    public static void i(String str, String str2, String str3) {
        TbsLogClient tbsLogClient = f21432c;
        if (tbsLogClient == null) {
            return;
        }
        tbsLogClient.i(str, "TBS:" + str2 + " " + str3);
        f21432c.writeLog("(I)-" + str + "-TBS:" + str2 + " " + str3);
    }

    public static void i(String str, String str2, boolean z2) {
        i(str, str2);
        TbsLogClient tbsLogClient = f21432c;
        if (tbsLogClient != null && f21430a && z2) {
            tbsLogClient.showLog(str + ": " + str2);
        }
    }

    public static void i(Throwable th) {
        i("handle_throwable", Log.getStackTraceString(th));
    }

    public static synchronized void initIfNeed(Context context) {
        if (f21432c == null) {
            setTbsLogClient(new TbsLogClient(context));
        }
    }

    public static void setLogView(TextView textView) {
        TbsLogClient tbsLogClient;
        if (textView == null || (tbsLogClient = f21432c) == null) {
            return;
        }
        tbsLogClient.setLogView(textView);
    }

    public static boolean setTbsLogClient(TbsLogClient tbsLogClient) {
        if (tbsLogClient == null) {
            return false;
        }
        f21432c = tbsLogClient;
        TbsLogClient.setWriteLogJIT(f21431b);
        return true;
    }

    public static void setWriteLogJIT(boolean z2) {
        f21431b = z2;
        if (f21432c == null) {
            return;
        }
        TbsLogClient.setWriteLogJIT(z2);
    }

    public static void v(String str, String str2) {
        v(str, "", str2);
    }

    public static void v(String str, String str2, String str3) {
        TbsLogClient tbsLogClient = f21432c;
        if (tbsLogClient == null) {
            return;
        }
        tbsLogClient.v(str, "TBS:" + str2 + " " + str3);
    }

    public static void v(String str, String str2, boolean z2) {
        v(str, str2);
        TbsLogClient tbsLogClient = f21432c;
        if (tbsLogClient != null && f21430a && z2) {
            tbsLogClient.showLog(str + ": " + str2);
        }
    }

    public static void w(String str, String str2) {
        w(str, "", str2);
    }

    public static void w(String str, String str2, String str3) {
        TbsLogClient tbsLogClient = f21432c;
        if (tbsLogClient == null) {
            return;
        }
        tbsLogClient.w(str, "TBS:" + str2 + " " + str3);
        f21432c.writeLog("(W)-" + str + "-TBS:" + str2 + " " + str3);
    }

    public static void w(String str, String str2, boolean z2) {
        w(str, str2);
        TbsLogClient tbsLogClient = f21432c;
        if (tbsLogClient != null && f21430a && z2) {
            tbsLogClient.showLog(str + ": " + str2);
        }
    }

    public static synchronized void writeLogToDisk() {
        TbsLogClient tbsLogClient = f21432c;
        if (tbsLogClient != null) {
            tbsLogClient.writeLogToDisk();
        }
    }
}
