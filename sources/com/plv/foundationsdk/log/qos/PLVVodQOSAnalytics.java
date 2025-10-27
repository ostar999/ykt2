package com.plv.foundationsdk.log.qos;

import android.text.TextUtils;
import com.plv.foundationsdk.log.PLVAnalyticsBase;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.api.PLVFoundationApiManager;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes4.dex */
public abstract class PLVVodQOSAnalytics extends PLVAnalyticsBase {
    private static final String ADERROR = "aderror";
    private static final String ADPLAY = "adplay";
    private static final String A_DOMAIN_EQ = "&domain=";
    private static final String A_ERROR_EQ = "&error=";
    private static final String A_FLASH_VERSION_EQ = "&flash_version=";
    private static final String A_PARAM_1_EQ = "&param1=";
    private static final String A_PARAM_2_EQ = "&param2=";
    private static final String A_PARAM_3_EQ = "&param3=";
    private static final String A_PARAM_4_EQ = "&param4=";
    private static final String A_PARAM_5_EQ = "&param5=";
    private static final String A_REQUEST_URI_EQ = "&request_uri=";
    private static final String A_RESPONSE_CODE_EQ = "&response_code=";
    private static final String A_SESSION_ID_EQ = "&session_id=";
    private static final String A_TIME_EQ = "&time=";
    private static final String A_TYPE_EQ = "&type=";
    private static final String A_UID_EQ = "&uid=";
    private static final String A_VID_EQ = "&vid=";
    private static final String BUFFER = "buffer";
    private static final String ERROR = "error";
    public static final String ERROR_DOWNLOAD_TYPE_PREFIX = "download_type_";
    public static final String ERROR_VIDEO_TYPE_PREFIX = "video_type_";
    public static final String HTTPS_PRTAS_VIDEOCC_NET_LOGQOS = "https://prtas.videocc.net/logqos";
    public static final String HTTPS_PRTAS_VIDEOCC_NET_QOS = "https://prtas.videocc.net/qos";
    private static final String LOADING = "loading";
    private static final String Q_PID_EQ = "?pid=";
    private static final String TAG = "PLVVodQOSAnalytics";
    private static final String UTF_8 = "UTF-8";
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private String qOSAnalyticsParam = "";

    private void sendRequest2Service(String str) {
        PLVResponseExcutor.excuteResponseBodyData(PLVFoundationApiManager.getPlvUrlApi().requestQosUrl(str), null);
    }

    private String urlEncoder(String str) {
        if (TextUtils.isEmpty(str)) {
            PLVCommonLog.e(TAG, "str is null");
            return "";
        }
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return str;
        }
    }

    public void buffer(String str, String str2, int i2) {
        buffer(str, str2, i2, "");
    }

    public void error(String str, String str2, String str3) {
        error(str, str2, str3, "");
    }

    public String getQOSAnalyticsParam() {
        return this.qOSAnalyticsParam;
    }

    public void loading(String str, String str2, int i2) {
        loading(str, str2, i2, "");
    }

    public void logError(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11) {
        sendRequest2Service(HTTPS_PRTAS_VIDEOCC_NET_LOGQOS + Q_PID_EQ + str + A_UID_EQ + createProjectInfo().getSDKUserId() + A_VID_EQ + str2 + A_ERROR_EQ + str3 + "&type=error" + A_DOMAIN_EQ + str4 + A_SESSION_ID_EQ + str5 + A_PARAM_1_EQ + str8 + A_PARAM_2_EQ + str9 + A_PARAM_3_EQ + str10 + A_PARAM_4_EQ + createProjectInfo().getSDKViewerId() + A_PARAM_5_EQ + createProjectInfo().getSDKName() + A_REQUEST_URI_EQ + str6 + A_RESPONSE_CODE_EQ + str7 + A_FLASH_VERSION_EQ);
    }

    public void setQOSAnalyticsParam(String str) {
        this.qOSAnalyticsParam = str;
    }

    public void buffer(String str, String str2, int i2, String str3) {
        buffer(str, str2, i2, str3, "");
    }

    public void error(String str, String str2, String str3, String str4) {
        error(str, str2, str3, str4, "");
    }

    public void loading(String str, String str2, int i2, String str3) {
        loading(str, str2, i2, str3, "");
    }

    public void buffer(String str, String str2, int i2, String str3, String str4) {
        buffer(str, str2, i2, str3, str4, "");
    }

    public void error(String str, String str2, String str3, String str4, String str5) {
        error(str, str2, str3, str4, str5, "");
    }

    public void loading(String str, String str2, int i2, String str3, String str4) {
        loading(str, str2, i2, str3, str4, "");
    }

    public void buffer(String str, String str2, int i2, String str3, String str4, String str5) {
        buffer(str, str2, i2, str3, str4, str5, "");
    }

    public void error(String str, String str2, String str3, String str4, String str5, String str6) {
        error(str, str2, str3, str4, str5, str6, "");
    }

    public void loading(String str, String str2, int i2, String str3, String str4, String str5) {
        loading(str, str2, i2, str3, str4, str5, "");
    }

    public void buffer(String str, String str2, int i2, String str3, String str4, String str5, String str6) {
        buffer(str, str2, i2, str3, str4, str5, str6, "");
    }

    public void error(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        error(str, str2, str3, str4, str5, str6, str7, "");
    }

    public void loading(String str, String str2, int i2, String str3, String str4, String str5, String str6) {
        loading(str, str2, i2, str3, str4, str5, str6, "");
    }

    public void buffer(String str, String str2, int i2, String str3, String str4, String str5, String str6, String str7) {
        buffer(str, str2, i2, str3, str4, str5, str6, str7, "");
    }

    public void error(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        error(str, str2, str3, str4, str5, str6, str7, str8, "");
    }

    public void loading(String str, String str2, int i2, String str3, String str4, String str5, String str6, String str7) {
        loading(str, str2, i2, str3, str4, str5, str6, str7, "");
    }

    public void buffer(String str, String str2, int i2, String str3, String str4, String str5, String str6, String str7, String str8) {
        buffer(str, str2, i2, str3, str4, str5, str6, str7, str8, createProjectInfo().getSDKName());
    }

    public void error(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        error(str, str2, str3, str4, str5, str6, str7, str8, str9, "");
    }

    public void loading(String str, String str2, int i2, String str3, String str4, String str5, String str6, String str7, String str8) {
        loading(str, str2, i2, str3, str4, str5, str6, str7, str8, createProjectInfo().getSDKName());
    }

    private void buffer(String str, String str2, int i2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        sendRequest2Service(HTTPS_PRTAS_VIDEOCC_NET_QOS + Q_PID_EQ + str + A_UID_EQ + createProjectInfo().getSDKUserId() + A_VID_EQ + str2 + A_TIME_EQ + i2 + "&type=buffer" + A_DOMAIN_EQ + str3 + A_SESSION_ID_EQ + urlEncoder(str4) + A_PARAM_1_EQ + urlEncoder(str5) + A_PARAM_2_EQ + urlEncoder(str6) + A_PARAM_3_EQ + urlEncoder(str7) + A_PARAM_4_EQ + urlEncoder(createProjectInfo().getSDKViewerId()) + A_PARAM_5_EQ + urlEncoder(str9));
    }

    private void loading(String str, String str2, int i2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        sendRequest2Service(HTTPS_PRTAS_VIDEOCC_NET_QOS + Q_PID_EQ + str + A_UID_EQ + createProjectInfo().getSDKUserId() + A_VID_EQ + str2 + A_TIME_EQ + i2 + "&type=loading" + A_DOMAIN_EQ + str3 + A_SESSION_ID_EQ + urlEncoder(str4) + A_PARAM_1_EQ + urlEncoder(str5) + A_PARAM_2_EQ + urlEncoder(str6) + A_PARAM_3_EQ + urlEncoder(str7) + A_PARAM_4_EQ + urlEncoder(createProjectInfo().getSDKViewerId()) + A_PARAM_5_EQ + urlEncoder(str9));
    }

    public void error(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        error(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, "");
    }

    public void error(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11) {
        error(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, createProjectInfo().getSDKName());
    }

    private void error(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12) {
        sendRequest2Service(HTTPS_PRTAS_VIDEOCC_NET_QOS + Q_PID_EQ + str + A_UID_EQ + createProjectInfo().getSDKUserId() + A_VID_EQ + str2 + A_ERROR_EQ + str3 + "&type=error" + A_DOMAIN_EQ + str4 + A_SESSION_ID_EQ + urlEncoder(str5) + A_PARAM_1_EQ + urlEncoder(str8) + A_PARAM_2_EQ + urlEncoder(str9) + A_PARAM_3_EQ + urlEncoder(str10) + A_PARAM_4_EQ + urlEncoder(createProjectInfo().getSDKViewerId()) + A_PARAM_5_EQ + urlEncoder(str12) + A_REQUEST_URI_EQ + urlEncoder(str6) + A_RESPONSE_CODE_EQ + str7 + A_FLASH_VERSION_EQ);
    }
}
