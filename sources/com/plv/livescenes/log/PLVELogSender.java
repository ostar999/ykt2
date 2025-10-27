package com.plv.livescenes.log;

import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.log.elog.PLVELogsService;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;
import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.foundationsdk.model.log.PLVStatisticsBase;
import com.plv.thirdpart.blankj.utilcode.util.NetworkUtils;
import java.io.PrintWriter;
import java.io.StringWriter;

/* loaded from: classes4.dex */
public class PLVELogSender {
    private static String getNetworkStatus() {
        return "isConnected: " + NetworkUtils.isConnected() + ", isWifiConnected: " + NetworkUtils.isWifiConnected();
    }

    private static String getStackTraceString(Throwable th) {
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
        return sendLiveLog(cls, i2, th != null ? th.getMessage() : "", th, PolyvLiveSDKClient.getInstance().getUserId());
    }

    public static <T extends PLVErrorCodeInfoBase> int sendLiveLog(Class<T> cls, int i2, Throwable th, String str) {
        return sendLiveLog(cls, i2, th != null ? th.getMessage() : "", th, str);
    }

    public static <T extends PLVErrorCodeInfoBase> int sendPlaybackLog(Class<T> cls, int i2, Throwable th) {
        return sendPlaybackLog(cls, i2, th != null ? th.getMessage() : "", th);
    }

    public static <T extends PLVErrorCodeInfoBase> int send(Class<T> cls, int i2, String str, Throwable th) {
        return sendLiveLog(cls, i2, str, th, PolyvLiveSDKClient.getInstance().getUserId());
    }

    public static <T extends PLVErrorCodeInfoBase> int sendLiveLog(Class<T> cls, int i2, String str, Throwable th, String str2) throws NoSuchMethodException, SecurityException {
        if (i2 < 0) {
            return i2;
        }
        PLVStatisticsBaseLive pLVStatisticsBaseLiveCreateLiveEntity = PLVElogEntityCreator.createLiveEntity(cls, i2, str + "\n" + getNetworkStatus(), new PLVLogFileBase("", getStackTraceString(th)), str2);
        PLVELogsService.getInstance().addStaticsLog(pLVStatisticsBaseLiveCreateLiveEntity);
        if (pLVStatisticsBaseLiveCreateLiveEntity != null) {
            return pLVStatisticsBaseLiveCreateLiveEntity.getErrorCode();
        }
        return -1;
    }

    public static <T extends PLVErrorCodeInfoBase> int sendPlaybackLog(Class<T> cls, int i2, String str, Throwable th) throws NoSuchMethodException, SecurityException {
        if (i2 < 0) {
            return i2;
        }
        PLVStatisticsPlayback pLVStatisticsPlaybackCreatePlaybackEntity = PLVElogEntityCreator.createPlaybackEntity(cls, i2, str + "\n" + getNetworkStatus(), new PLVLogFileBase("", getStackTraceString(th)));
        PLVELogsService.getInstance().addStaticsLog(pLVStatisticsPlaybackCreatePlaybackEntity);
        if (pLVStatisticsPlaybackCreatePlaybackEntity != null) {
            return pLVStatisticsPlaybackCreatePlaybackEntity.getErrorCode();
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
