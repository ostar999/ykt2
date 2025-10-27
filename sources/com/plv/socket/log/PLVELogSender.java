package com.plv.socket.log;

import com.plv.foundationsdk.log.elog.PLVELogsService;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;
import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.foundationsdk.model.log.PLVStatisticsBase;
import com.plv.thirdpart.blankj.utilcode.util.NetworkUtils;
import java.io.PrintWriter;
import java.io.StringWriter;

/* loaded from: classes5.dex */
public class PLVELogSender {
    public static String getNetworkStatus() {
        return "isConnected: " + NetworkUtils.isConnected() + ", isWifiConnected: " + NetworkUtils.isWifiConnected();
    }

    public static String getStackTraceString(Throwable th) {
        if (th == null) {
            return "";
        }
        for (Throwable cause = th; cause != null; cause = cause.getCause()) {
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    public static <T extends PLVErrorCodeInfoBase> int send(Class<T> cls, int i2, Throwable th) {
        return send(cls, i2, th != null ? th.getMessage() : "", th);
    }

    public static <T extends PLVErrorCodeInfoBase> int send(Class<T> cls, int i2, String str, Throwable th) {
        if (i2 < 0) {
            return i2;
        }
        PLVStatisticsBaseSocket pLVStatisticsBaseSocketCreateElogErrorEntity = PLVElogEntityCreator.createElogErrorEntity(cls, i2, str + "\n" + getNetworkStatus(), new PLVLogFileBase("", getStackTraceString(th)));
        PLVELogsService.getInstance().addStaticsLog(pLVStatisticsBaseSocketCreateElogErrorEntity);
        if (pLVStatisticsBaseSocketCreateElogErrorEntity != null) {
            return pLVStatisticsBaseSocketCreateElogErrorEntity.getErrorCode();
        }
        return -1;
    }

    public static <T extends PLVStatisticsBase> void send(Class<T> cls, String str, Throwable th) {
        send(cls, str, getStackTraceString(th));
    }

    public static <T extends PLVStatisticsBase> void send(Class<T> cls, String str, String str2) {
        PLVELogsService.getInstance().addStaticsLog(cls, str, str2 + "\n" + getNetworkStatus(), new String[0]);
    }
}
