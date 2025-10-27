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
public abstract class PLVLiveQOSAnalytics extends PLVAnalyticsBase {
    private static final String ADERROR = "aderror";
    private static final String ADPLAY = "adplay";
    private static final String A_CID_EQ = "&cid=";
    private static final String A_CLIENT_EQ = "&client=";
    private static final String A_ERRORCODE_EQ = "&errorcode=";
    private static final String A_ERRORMSG_EQ = "&errormsg=";
    private static final String A_LOAD_TIME_EQ = "&load_time=";
    private static final String A_PARAM_1_EQ = "&param1=";
    private static final String A_PARAM_2_EQ = "&param2=";
    private static final String A_PARAM_3_EQ = "&param3=";
    private static final String A_PARAM_4_EQ = "&param4=";
    private static final String A_PARAM_5_EQ = "&param5=";
    private static final String A_PLAY_TIME_EQ = "&play_time=";
    private static final String A_SESSION_ID_EQ = "&session_id=";
    private static final String A_STATUS_EQ = "&status=";
    private static final String A_TIME_EQ = "&time=";
    private static final String A_TYPE_EQ = "&type=";
    private static final String A_UID_EQ = "&uid=";
    private static final String A_URI = "&uri";
    private static final String A_URL = "&url";
    private static final String BUFFER = "buffer";
    private static final String ERROR = "error";
    public static final String ERROR_DOWNLOAD_TYPE_PREFIX = "download_type_";
    public static final String ERROR_VIDEO_TYPE_PREFIX = "video_type_";
    private static final String HTTPS_RTAS_VIDEOCC_NET_ADSTATS = "https://rtas.videocc.net/adstats";
    private static final String HTTPS_RTAS_VIDEOCC_NET_QOS = "https://rtas.videocc.net/qos";
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
        PLVCommonLog.e(TAG, "content :" + str);
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            PLVCommonLog.e(TAG, e2.getMessage());
            return str;
        }
    }

    public String getQOSAnalyticsParam() {
        return this.qOSAnalyticsParam;
    }

    public void liveAdError(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        liveAdError(str, str2, str3, str4, str4, str6, str7, str8, "");
    }

    public void liveAdPlay(String str, String str2, String str3, String str4, int i2, int i3, String str5) {
        liveAdPlay(str, str2, str3, str4, i2, i3, str5, "");
    }

    public void liveBuffer(String str, String str2, String str3, int i2) {
        liveBuffer(str, str2, str3, i2, "");
    }

    public void liveError(String str, String str2, String str3, String str4, String str5) {
        liveError(str, str2, str3, str4, str5, "");
    }

    public void liveLoading(String str, String str2, String str3, int i2) {
        liveLoading(str, str2, str3, i2, "");
    }

    public void setQOSAnalyticsParam(String str) {
        this.qOSAnalyticsParam = str;
    }

    public void liveAdError(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        liveAdError(str, str2, str3, str4, str4, str6, str7, str8, str9, "");
    }

    public void liveAdPlay(String str, String str2, String str3, String str4, int i2, int i3, String str5, String str6) {
        liveAdPlay(str, str2, str3, str4, i2, i3, str5, str6, "");
    }

    public void liveBuffer(String str, String str2, String str3, int i2, String str4) {
        liveBuffer(str, str2, str3, i2, str4, "");
    }

    public void liveError(String str, String str2, String str3, String str4, String str5, String str6) {
        liveError(str, str2, str3, str4, str5, str6, "");
    }

    public void liveLoading(String str, String str2, String str3, int i2, String str4) {
        liveLoading(str, str2, str3, i2, str4, "");
    }

    public void liveAdError(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        liveAdError(str, str2, str3, str4, str4, str6, str7, str8, str9, str10, "");
    }

    public void liveAdPlay(String str, String str2, String str3, String str4, int i2, int i3, String str5, String str6, String str7) {
        liveAdPlay(str, str2, str3, str4, i2, i3, str5, str6, str7, "");
    }

    public void liveBuffer(String str, String str2, String str3, int i2, String str4, String str5) {
        liveBuffer(str, str2, str3, i2, str4, str5, "");
    }

    public void liveError(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        liveError(str, str2, str3, str4, str5, str6, str7, "");
    }

    public void liveLoading(String str, String str2, String str3, int i2, String str4, String str5) {
        liveLoading(str, str2, str3, i2, str4, str5, "");
    }

    public void liveAdError(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11) {
        liveAdError(str, str2, str3, str4, str4, str6, str7, str8, str9, str10, str11, "");
    }

    public void liveAdPlay(String str, String str2, String str3, String str4, int i2, int i3, String str5, String str6, String str7, String str8) {
        liveAdPlay(str, str2, str3, str4, i2, i3, str5, str6, str7, str8, "");
    }

    public void liveBuffer(String str, String str2, String str3, int i2, String str4, String str5, String str6) {
        liveBuffer(str, str2, str3, i2, str4, str5, str6, "");
    }

    public void liveError(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        liveError(str, str2, str3, str4, str5, str6, str7, str8, "");
    }

    public void liveLoading(String str, String str2, String str3, int i2, String str4, String str5, String str6) {
        liveLoading(str, str2, str3, i2, str4, str5, str6, "");
    }

    public void liveAdError(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12) {
        liveAdError(str, str2, str3, str4, str4, str6, str7, str8, str9, str10, str11, str12, "");
    }

    public void liveAdPlay(String str, String str2, String str3, String str4, int i2, int i3, String str5, String str6, String str7, String str8, String str9) {
        liveAdPlay(str, str2, str3, str4, i2, i3, str5, str6, str7, str8, str9, "");
    }

    public void liveBuffer(String str, String str2, String str3, int i2, String str4, String str5, String str6, String str7) {
        liveBuffer(str, str2, str3, i2, str4, str5, str6, str7, "");
    }

    public void liveError(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        liveError(str, str2, str3, str4, str5, str6, str7, str8, str9, "");
    }

    public void liveLoading(String str, String str2, String str3, int i2, String str4, String str5, String str6, String str7) {
        liveLoading(str, str2, str3, i2, str4, str5, str6, str7, "");
    }

    public void liveAdError(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13) {
        sendRequest2Service(HTTPS_RTAS_VIDEOCC_NET_ADSTATS + Q_PID_EQ + str + A_UID_EQ + str2 + A_CID_EQ + str3 + "&type=aderror" + A_SESSION_ID_EQ + urlEncoder(str6) + A_PARAM_1_EQ + urlEncoder(str9) + A_PARAM_2_EQ + urlEncoder(str10) + A_PARAM_3_EQ + urlEncoder(str11) + A_PARAM_4_EQ + urlEncoder(str12) + A_PARAM_5_EQ + urlEncoder(str13) + A_URI + urlEncoder(str7) + A_STATUS_EQ + urlEncoder(str8) + A_ERRORCODE_EQ + urlEncoder(str4) + A_ERRORMSG_EQ + urlEncoder(str5) + A_CLIENT_EQ + createProjectInfo().getSDKName());
    }

    public void liveAdPlay(String str, String str2, String str3, String str4, int i2, int i3, String str5, String str6, String str7, String str8, String str9, String str10) {
        sendRequest2Service(HTTPS_RTAS_VIDEOCC_NET_ADSTATS + Q_PID_EQ + str + A_UID_EQ + str2 + A_CID_EQ + str3 + "&type=adplay" + A_SESSION_ID_EQ + urlEncoder(str5) + A_PARAM_1_EQ + urlEncoder(str6) + A_PARAM_2_EQ + urlEncoder(str7) + A_PARAM_3_EQ + urlEncoder(str8) + A_PARAM_4_EQ + urlEncoder(str9) + A_PARAM_5_EQ + urlEncoder(str10) + A_URL + urlEncoder(str4) + A_LOAD_TIME_EQ + i2 + A_PLAY_TIME_EQ + i3 + A_CLIENT_EQ + createProjectInfo().getSDKName());
    }

    public void liveBuffer(String str, String str2, String str3, int i2, String str4, String str5, String str6, String str7, String str8) {
        liveBuffer(str, str2, str3, i2, str4, str5, str6, str7, str8, "");
    }

    public void liveError(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        liveError(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, "");
    }

    public void liveLoading(String str, String str2, String str3, int i2, String str4, String str5, String str6, String str7, String str8) {
        liveLoading(str, str2, str3, i2, str4, str5, str6, str7, str8, "");
    }

    public void liveBuffer(String str, String str2, String str3, int i2, String str4, String str5, String str6, String str7, String str8, String str9) {
        sendRequest2Service(HTTPS_RTAS_VIDEOCC_NET_QOS + Q_PID_EQ + str + A_UID_EQ + str2 + A_CID_EQ + str3 + A_TIME_EQ + i2 + "&type=buffer" + A_SESSION_ID_EQ + urlEncoder(str4) + A_PARAM_1_EQ + urlEncoder(str5) + A_PARAM_2_EQ + urlEncoder(str6) + A_PARAM_3_EQ + urlEncoder(str7) + A_PARAM_4_EQ + urlEncoder(str8) + A_PARAM_5_EQ + urlEncoder(str9) + A_CLIENT_EQ + createProjectInfo().getSDKName());
    }

    public void liveError(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11) {
        liveError(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, "");
    }

    public void liveLoading(String str, String str2, String str3, int i2, String str4, String str5, String str6, String str7, String str8, String str9) {
        sendRequest2Service(HTTPS_RTAS_VIDEOCC_NET_QOS + Q_PID_EQ + str + A_UID_EQ + str2 + A_CID_EQ + str3 + A_TIME_EQ + i2 + "&type=loading" + A_SESSION_ID_EQ + urlEncoder(str4) + A_PARAM_1_EQ + urlEncoder(str5) + A_PARAM_2_EQ + urlEncoder(str6) + A_PARAM_3_EQ + urlEncoder(str7) + A_PARAM_4_EQ + urlEncoder(str8) + A_PARAM_5_EQ + urlEncoder(str9) + A_CLIENT_EQ + createProjectInfo().getSDKName());
    }

    public void liveError(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12) {
        liveError(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, "");
    }

    public void liveError(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13) {
        sendRequest2Service(HTTPS_RTAS_VIDEOCC_NET_QOS + Q_PID_EQ + str + A_UID_EQ + str2 + A_CID_EQ + str3 + "&type=error" + A_SESSION_ID_EQ + urlEncoder(str6) + A_PARAM_1_EQ + urlEncoder(str9) + A_PARAM_2_EQ + urlEncoder(str10) + A_PARAM_3_EQ + urlEncoder(str11) + A_PARAM_4_EQ + urlEncoder(str12) + A_PARAM_5_EQ + urlEncoder(str13) + A_URI + urlEncoder(str7) + A_STATUS_EQ + urlEncoder(str8) + A_ERRORCODE_EQ + urlEncoder(str4) + A_ERRORMSG_EQ + urlEncoder(str5) + A_CLIENT_EQ + createProjectInfo().getSDKName());
    }
}
