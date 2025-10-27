package core.monitor;

import android.text.TextUtils;
import c.e;
import c.g;
import c.h;
import c.i;
import com.easefun.polyv.mediasdk.player.misc.IMediaFormat;
import e.b;
import h.a;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes8.dex */
public class LogReportManager {
    public static final int AUDIO_STATUS = 1;
    public static final int EXCEPTION_CAMERA_ERROR = 3;
    public static final int EXCEPTION_HIGH_CPU_USAGE = 6;
    public static final int EXCEPTION_HIGH_LATENCY = 8;
    public static final int EXCEPTION_HIGH_PACKET_LOST = 7;
    public static final int EXCEPTION_IM_SERVICE_UNAVAILABLE = 11;
    public static final int EXCEPTION_MIC_ERROR = 4;
    public static final int EXCEPTION_OTHER_ERROR = 13;
    public static final int EXCEPTION_PULL_ERROR = 2;
    public static final int EXCEPTION_PUSH_ERROR = 1;
    public static final int EXCEPTION_RECORD_STORAGE_FAILED = 10;
    public static final int EXCEPTION_RECORD_TRANSCODE_FAILED = 9;
    public static final int EXCEPTION_SPEAKER_ERROR = 5;
    public static final int EXCEPTION_WHITE_SERVICE_UNAVAILABLE = 12;
    public static final String INVALID_STRING_VALUE = "";
    public static final int INVALID_VALUE = -1;
    public static final int LOG_EXCEPTION = 4;
    public static final int LOG_JOIN_ROOM = 1;
    public static final int LOG_LEAVE_ROOM = 3;
    public static final int LOG_OPERATION = 5;
    public static final int LOG_STATUS = 2;
    public static final int OPERATION_CLOSE_CAMERA = 8;
    public static final int OPERATION_CLOSE_MIC = 10;
    public static final int OPERATION_JOIN_ROOM = 1;
    public static final int OPERATION_LEAVE_ROOM = 2;
    public static final int OPERATION_OPEN_CAMERA = 7;
    public static final int OPERATION_OPEN_MIC = 9;
    public static final int OPERATION_PULL_FINISH = 6;
    public static final int OPERATION_PULL_START = 5;
    public static final int OPERATION_PUSH_FINISH = 4;
    public static final int OPERATION_PUSH_START = 3;
    public static final int OPERATION_RECORD_FINISH = 12;
    public static final int OPERATION_RECORD_START = 11;
    private static final String TAG = "LogReportManager";
    public static final int VIDEO_STATUS = 0;
    public static String exceptionUrl = null;
    private static LogReportManager instance = null;
    public static String joinLeaveReportUrl = null;
    public static boolean logPre = false;
    public static String operationUrl;
    public static String statsReportUrl = e.d() + "/api/rtcClinetLog";

    public class LogRunnable implements Runnable {
        public int mLogType;
        public String mMessage;
        public String mUrl;

        public LogRunnable(int i2, String str, String str2) {
            this.mLogType = i2;
            this.mUrl = str;
            this.mMessage = str2;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            try {
                h.a(LogReportManager.TAG, "type: " + this.mLogType + " request url: " + this.mUrl);
                StringBuilder sb = new StringBuilder();
                sb.append(" origin message ");
                sb.append(this.mMessage);
                h.a(LogReportManager.TAG, sb.toString());
                h.a(LogReportManager.TAG, " log response " + a.b().a(this.mUrl, this.mMessage));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class PublicLogContainer {
        private String aid;
        private String method;
        private int mtype;
        private String rid;
        private String rpc_id;
        private String sid;
        private String streamid;
        private int stype;
        private long ts;
        private int type;
        private String uid;
        private String version;

        public PublicLogContainer(String str, String str2, String str3, int i2, long j2, String str4, String str5, String str6, String str7, String str8, int i3, int i4) {
            this.version = str;
            this.method = str2;
            this.rpc_id = str3;
            this.type = i2;
            this.ts = j2;
            this.aid = str4;
            this.rid = str5;
            this.sid = str6;
            this.uid = str7;
            this.streamid = str8;
            this.stype = i3;
            this.mtype = i4;
        }

        public JSONObject convertToJsonObject() {
            try {
                return g.b(this);
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
                return null;
            } catch (JSONException e3) {
                e3.printStackTrace();
                return null;
            }
        }
    }

    public class PublicLogContainerBuilder {
        private String mVersion = "";
        private String mMethod = "";
        private String mRpcId = "";
        private int mType = -1;
        private long mTimemills = 0;
        private long mTimeSeconds = 0;
        private String mAppId = "";
        private String mRoomId = "";
        private String mSessionId = "";
        private String mUserId = "";
        private String mStreamId = "";
        private int mStreamType = -1;
        private int mMediaType = -1;

        public PublicLogContainerBuilder() {
        }

        public PublicLogContainerBuilder appId(String str) {
            this.mAppId = str;
            return this;
        }

        public PublicLogContainer build() {
            return LogReportManager.this.new PublicLogContainer(this.mVersion, this.mMethod, this.mRpcId, this.mType, this.mTimeSeconds, this.mAppId, this.mRoomId, this.mSessionId, this.mUserId, this.mStreamId, this.mStreamType, this.mMediaType);
        }

        public PublicLogContainerBuilder mediaType(int i2) {
            this.mMediaType = i2;
            return this;
        }

        public PublicLogContainerBuilder method(String str) {
            this.mMethod = str;
            return this;
        }

        public PublicLogContainerBuilder roomId(String str) {
            this.mRoomId = str;
            return this;
        }

        public PublicLogContainerBuilder rpcId(String str) {
            this.mRpcId = str;
            return this;
        }

        public PublicLogContainerBuilder sessionId(String str) {
            this.mSessionId = str;
            return this;
        }

        public PublicLogContainerBuilder streamId(String str) {
            this.mStreamId = str;
            return this;
        }

        public PublicLogContainerBuilder streamType(int i2) {
            this.mStreamType = i2;
            return this;
        }

        public PublicLogContainerBuilder timemills(long j2) {
            this.mTimemills = j2;
            this.mTimeSeconds = j2 / 1000;
            return this;
        }

        public PublicLogContainerBuilder type(int i2) {
            this.mType = i2;
            return this;
        }

        public PublicLogContainerBuilder userId(String str) {
            this.mUserId = str;
            return this;
        }

        public PublicLogContainerBuilder version(String str) {
            this.mVersion = str;
            return this;
        }
    }

    static {
        String str;
        String str2;
        String str3;
        if (logPre) {
            str = "https://logpre.urtc.com.cn:443/api/rtcJoinLeaveClientLog";
        } else {
            str = e.d() + "/api/rtcJoinLeaveClientLog";
        }
        joinLeaveReportUrl = str;
        if (logPre) {
            str2 = "https://logpre.urtc.com.cn:443/api/opertionLog";
        } else {
            str2 = e.d() + "/api/opertionLog";
        }
        operationUrl = str2;
        if (logPre) {
            str3 = "https://logpre.urtc.com.cn:443/api/exceptionLog";
        } else {
            str3 = e.d() + "/api/exceptionLog";
        }
        exceptionUrl = str3;
    }

    public static LogReportManager getInstance() {
        if (instance == null) {
            synchronized (LogReportManager.class) {
                if (instance == null) {
                    instance = new LogReportManager();
                }
            }
        }
        return instance;
    }

    public static void refreshUrl() {
        boolean z2 = logPre;
        statsReportUrl = z2 ? "https://logpre.urtc.com.cn:443/api/rtcClinetLog" : "https://log.urtc.com.cn:443/api/rtcClinetLog";
        joinLeaveReportUrl = z2 ? "https://logpre.urtc.com.cn:443/api/rtcJoinLeaveClientLog" : "https://log.urtc.com.cn:443/api/rtcJoinLeaveClientLog";
        operationUrl = z2 ? "https://logpre.urtc.com.cn:443/api/opertionLog" : "https://log.urtc.com.cn:443/api/opertionLog";
        exceptionUrl = z2 ? "https://logpre.urtc.com.cn:443/api/exceptionLog" : "https://log.urtc.com.cn:443/api/exceptionLog";
    }

    public PublicLogContainerBuilder assemblePublicHeader() {
        return new PublicLogContainerBuilder();
    }

    public JSONObject createStatusTrackDataDefault(int i2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            if (i2 == 0) {
                jSONObject.put("br", -1);
                jSONObject.put("lostpre", -1);
                jSONObject.put("frt", -1);
                jSONObject.put("w", -1);
                jSONObject.put("h", -1);
                jSONObject.put(IMediaFormat.KEY_MIME, "");
            } else {
                jSONObject.put("br", -1);
                jSONObject.put("lostpre", -1);
                jSONObject.put("vol", -1);
                jSONObject.put(IMediaFormat.KEY_MIME, "");
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    public void sendCommonMsg(b bVar, int i2, JSONObject jSONObject) throws JSONException {
        try {
            JSONObject jSONObjectConvertToJsonObject = getInstance().assemblePublicHeader().version("1.0").method(i.f2281l).rpcId(e.a()).type(i2).timemills(System.currentTimeMillis()).appId(bVar.u().getAppId()).roomId(bVar.u().getRoomId()).sessionId(bVar.y().b()).userId(bVar.u().getUId()).build().convertToJsonObject();
            jSONObjectConvertToJsonObject.put("data", jSONObject);
            sendLog(i2, jSONObjectConvertToJsonObject.toString(), bVar.l());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void sendErrorMsg(b bVar, int i2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("errorType", i2);
            jSONObject.put("userid", "");
            jSONObject.put("streamid", "");
            jSONObject.put("msg", "");
            sendCommonMsg(bVar, 4, jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void sendErrorStreamMsg(b bVar, int i2, String str, int i3, int i4) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("errorType", i2);
            jSONObject.put("userid", "");
            jSONObject.put("streamid", "");
            jSONObject.put("msg", "");
            sendStreamInfoMsg(bVar, 4, jSONObject, str, i3, i4);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void sendErrorSubStreamMsg(b bVar, int i2, String str, String str2, String str3, int i3, int i4) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("errorType", i2);
            jSONObject.put("userid", str3);
            jSONObject.put("streamid", str2);
            jSONObject.put("msg", "");
            sendStreamInfoMsg(bVar, 4, jSONObject, str, i3, i4);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void sendLog(int i2, String str, ExecutorService executorService) {
        if (d.b.u()) {
            String str2 = i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? "" : operationUrl : exceptionUrl : joinLeaveReportUrl : statsReportUrl : joinLeaveReportUrl;
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            if (executorService == null || executorService.isShutdown()) {
                new LogRunnable(i2, str2, str).run();
            } else {
                executorService.execute(new LogRunnable(i2, str2, str));
            }
        }
    }

    public void sendOpMsg(b bVar, int i2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("opertionType", i2);
            jSONObject.put("userid", "");
            jSONObject.put("streamid", "");
            jSONObject.put("recordid", "");
            sendCommonMsg(bVar, 5, jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void sendOpRecordMsg(b bVar, int i2, String str) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("opertionType", i2);
            jSONObject.put("userid", "");
            jSONObject.put("streamid", "");
            jSONObject.put("recordid", str);
            sendCommonMsg(bVar, 5, jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void sendOpStreamMsg(b bVar, int i2, String str, int i3, int i4) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("opertionType", i2);
            jSONObject.put("userid", "");
            jSONObject.put("streamid", "");
            jSONObject.put("recordid", "");
            sendStreamInfoMsg(bVar, 5, jSONObject, str, i3, i4);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void sendOpSubStreamMsg(b bVar, int i2, String str, String str2, String str3, int i3, int i4) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("opertionType", i2);
            jSONObject.put("userid", str3);
            jSONObject.put("streamid", str2);
            jSONObject.put("recordid", "");
            sendStreamInfoMsg(bVar, 5, jSONObject, str, i3, i4);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void sendOtherErrorMsg(b bVar, String str) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("errorType", 13);
            jSONObject.put("userid", "");
            jSONObject.put("streamid", "");
            jSONObject.put("msg", str);
            sendCommonMsg(bVar, 4, jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void sendOtherErrorStreamMsg(b bVar, String str, String str2, int i2, int i3) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("errorType", 13);
            jSONObject.put("userid", "");
            jSONObject.put("streamid", "");
            jSONObject.put("msg", str);
            sendStreamInfoMsg(bVar, 4, jSONObject, str2, i2, i3);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void sendOtherErrorSubStreamMsg(b bVar, String str, String str2, String str3, String str4, int i2, int i3) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("errorType", 13);
            jSONObject.put("userid", str4);
            jSONObject.put("streamid", str3);
            jSONObject.put("msg", str);
            sendStreamInfoMsg(bVar, 4, jSONObject, str2, i2, i3);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void sendStreamInfoMsg(b bVar, int i2, JSONObject jSONObject, String str, int i3, int i4) throws JSONException {
        try {
            JSONObject jSONObjectConvertToJsonObject = getInstance().assemblePublicHeader().version("1.0").method(i.f2281l).rpcId(e.a()).type(i2).timemills(System.currentTimeMillis()).appId(bVar.u().getAppId()).roomId(bVar.u().getRoomId()).sessionId(bVar.y().b()).userId(bVar.u().getUId()).streamId(str).streamType(i3).mediaType(i4).build().convertToJsonObject();
            jSONObjectConvertToJsonObject.put("data", jSONObject);
            sendLog(i2, jSONObjectConvertToJsonObject.toString(), bVar.l());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }
}
