package com.plv.foundationsdk.download.utils;

import android.text.TextUtils;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/* loaded from: classes4.dex */
public class PLVDownloadErrorMessageUtils {
    private static final String TAG = "PLVDownloadErrorMessageUtils";

    public static String getExceptionFullMessage(Throwable th) {
        return getExceptionFullMessage(th, 500);
    }

    public static String getExceptionFullMessage(Throwable th, int i2) throws Throwable {
        StringWriter stringWriter;
        PrintWriter printWriter;
        if (th == null) {
            return "";
        }
        PrintWriter printWriter2 = null;
        try {
            stringWriter = new StringWriter();
            try {
                printWriter = new PrintWriter(stringWriter);
            } catch (Exception unused) {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused2) {
            stringWriter = null;
        } catch (Throwable th3) {
            th = th3;
            stringWriter = null;
        }
        try {
            th.printStackTrace(printWriter);
            String string = stringWriter.toString();
            if (TextUtils.isEmpty(string)) {
                printWriter.close();
                try {
                    stringWriter.close();
                } catch (IOException e2) {
                    PLVCommonLog.e(TAG, getExceptionFullMessage(e2, -1));
                }
                return "";
            }
            if (i2 == -1 || string.length() < i2) {
                i2 = string.length();
            }
            String strSubstring = string.substring(0, i2);
            printWriter.close();
            try {
                stringWriter.close();
            } catch (IOException e3) {
                PLVCommonLog.e(TAG, getExceptionFullMessage(e3, -1));
            }
            return strSubstring;
        } catch (Exception unused3) {
            printWriter2 = printWriter;
            if (printWriter2 != null) {
                printWriter2.close();
            }
            if (stringWriter != null) {
                try {
                    stringWriter.close();
                } catch (IOException e4) {
                    PLVCommonLog.e(TAG, getExceptionFullMessage(e4, -1));
                }
            }
            return "";
        } catch (Throwable th4) {
            th = th4;
            printWriter2 = printWriter;
            if (printWriter2 != null) {
                printWriter2.close();
            }
            if (stringWriter != null) {
                try {
                    stringWriter.close();
                } catch (IOException e5) {
                    PLVCommonLog.e(TAG, getExceptionFullMessage(e5, -1));
                }
            }
            throw th;
        }
    }
}
