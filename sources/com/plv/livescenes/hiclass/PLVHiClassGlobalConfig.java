package com.plv.livescenes.hiclass;

import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.easefun.polyv.livescenes.log.PLVELogRequestManager;
import com.plv.foundationsdk.log.elog.PLVELogsService;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.socket.socketio.PLVSocketIOClient;

/* loaded from: classes4.dex */
public class PLVHiClassGlobalConfig {
    private static String courseCode;
    private static boolean isTeacherType;
    private static long lessonId;
    private static String token;
    private static String userType;

    public static void clear() {
        setupConfig(null, null, false, null, 0L, null);
    }

    public static String getCourseCode() {
        return courseCode;
    }

    public static long getLessonId() {
        return lessonId;
    }

    public static String getToken() {
        return token;
    }

    public static String getUserType() {
        return userType;
    }

    public static boolean isTeacherType() {
        return isTeacherType;
    }

    public static void setupConfig(String str, String str2, boolean z2, String str3, long j2, String str4) {
        token = str;
        userType = str2;
        isTeacherType = z2;
        courseCode = str3;
        lessonId = j2;
        PLVLinkMicConfig.getInstance().setupHiClassConfig(str, z2, str3, j2);
        PLVSocketIOClient.getInstance().setupHiClassConfig(str, z2, str3, j2);
        PolyvLiveSDKClient.getInstance().setChannelId(str4);
        PolyvLiveSDKClient.getInstance().setAppIdSecret("", "", "");
        PLVLinkMicConfig.getInstance().setRtcType(PLVLinkMicConstant.RtcType.RTC_TYPE_U).setLiveChannelType(PLVLiveChannelType.PPT).setSessionId(j2 != 0 ? String.valueOf(j2) : "");
        PLVELogsService.getInstance().setELogSender(PLVELogRequestManager.getInstance());
    }
}
