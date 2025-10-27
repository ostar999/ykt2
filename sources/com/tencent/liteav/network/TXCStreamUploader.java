package com.tencent.liteav.network;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import cn.hutool.core.text.StrPool;
import com.caverock.androidsvg.SVGParser;
import com.google.android.exoplayer2.ExoPlayer;
import com.tencent.liteav.TXLiteAVCode;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.Monitor;
import com.tencent.liteav.basic.module.TXCEventRecorderProxy;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.open.SocialConstants;
import com.tencent.rtmp.TXLiveConstants;
import com.yikaobang.yixue.R2;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class TXCStreamUploader extends com.tencent.liteav.basic.module.a implements com.tencent.liteav.network.b {
    public static final int NAL_CODEC_ID_AVC = 0;
    public static final int NAL_CODEC_ID_HEVC = 1;
    public static final int RTMPSENDSTRATEGY_LIVE = 1;
    public static final int RTMPSENDSTRATEGY_REALTIME_QUIC = 3;
    public static final int RTMPSENDSTRATEGY_REALTIME_TCP = 2;
    static final String TAG = "TXCStreamUploader";
    public static final int TXE_UPLOAD_MODE_AUDIO_ONLY = 1;
    public static final int TXE_UPLOAD_MODE_LINK_MIC = 2;
    public static final int TXE_UPLOAD_MODE_REAL_TIME = 0;
    public static final int TXE_UPLOAD_PROTOCOL_AV = 1;
    public static final int TXE_UPLOAD_PROTOCOL_RTMP = 0;
    private Context mContext;
    private int mCurrentRecordIdx;
    private HandlerThread mHandlerThread;
    private c mIntelligentRoute;
    private ArrayList<com.tencent.liteav.network.a> mIpList;
    private boolean mIsPushing;
    private int mLastNetworkType;
    HashMap<String, String> mMetaData;
    private i mParam;
    private int mRetryCount;
    private String mRtmpUrl;
    private Thread mThread;
    private Object mThreadLock;
    private k mUploadQualityReport;
    private long mUploaderInstance;
    private boolean mQuicChannel = false;
    private int mChannelType = 0;
    private boolean mEnableNearestIP = true;
    private WeakReference<com.tencent.liteav.basic.b.b> mNotifyListener = null;
    private long mConnectSuccessTimeStamps = 0;
    private long mGoodPushTime = 30000;
    private Handler mHandler = null;
    private final int MSG_RECONNECT = 101;
    private final int MSG_EVENT = 102;
    private final int MSG_REPORT_STATUS = 103;
    private final int MSG_RTMPPROXY_HEARTBEAT = 104;
    private long mLastTimeStamp = 0;
    private UploadStats mLastUploadStats = null;
    private Vector<TXSNALPacket> mVecPendingNAL = new Vector<>();
    private int mConnectCountQuic = 0;
    private int mConnectCountTcp = 0;
    private boolean mRtmpProxyEnable = false;
    private boolean mAudioMuted = false;
    private a mRtmpProxyParam = new a();
    private Vector<String> mRtmpProxyIPList = new Vector<>();
    private int mRtmpProxyIPIndex = 0;
    private long mRtmpProxyInstance = 0;
    private long mRtmpMsgRecvThreadInstance = 0;
    private Object mRtmpProxyLock = new Object();
    private Object mRtmpMsgRecvThreadLock = new Object();

    public class RtmpProxyUserInfo {
        public String account = "";
        public String playUrl = "";
        public int stmType = 0;

        public RtmpProxyUserInfo() {
        }
    }

    public class UploadStats {
        public long audioCacheLen;
        public long audioDropCount;
        public long bandwidthEst;
        public long channelType;
        public long connTS;
        public long connectTimeCost;
        public String connectionID;
        public String connectionStats;
        public long dnsTS;
        public long dnsparseTimeCost;
        public long handshakeTimeCost;
        public long inAudioBytes;
        public long inVideoBytes;
        public long outAudioBytes;
        public long outVideoBytes;
        public String serverIP;
        public long startTS;
        public long videoCacheLen;
        public long videoDropCount;

        public UploadStats() {
        }
    }

    public class a {

        /* renamed from: a, reason: collision with root package name */
        public long f19482a;

        /* renamed from: b, reason: collision with root package name */
        public long f19483b;

        /* renamed from: c, reason: collision with root package name */
        public String f19484c;

        /* renamed from: d, reason: collision with root package name */
        public long f19485d;

        /* renamed from: e, reason: collision with root package name */
        public String f19486e;

        /* renamed from: f, reason: collision with root package name */
        public long f19487f;

        /* renamed from: g, reason: collision with root package name */
        public long f19488g;

        /* renamed from: h, reason: collision with root package name */
        public String f19489h;

        /* renamed from: i, reason: collision with root package name */
        public boolean f19490i;

        /* renamed from: j, reason: collision with root package name */
        public String f19491j;

        public a() {
        }

        public void a() {
            this.f19482a = 0L;
            this.f19483b = 0L;
            this.f19484c = "";
            this.f19485d = 0L;
            this.f19486e = "";
            this.f19487f = 0L;
            this.f19488g = 0L;
            this.f19490i = false;
            this.f19491j = "";
        }
    }

    public class b {

        /* renamed from: a, reason: collision with root package name */
        public String f19493a;

        /* renamed from: b, reason: collision with root package name */
        public boolean f19494b;

        public b(String str, boolean z2) {
            this.f19493a = str;
            this.f19494b = z2;
        }
    }

    static {
        com.tencent.liteav.basic.util.h.d();
    }

    public TXCStreamUploader(Context context, i iVar) {
        this.mUploaderInstance = 0L;
        this.mThread = null;
        this.mThreadLock = null;
        this.mIsPushing = false;
        this.mRtmpUrl = "";
        this.mIntelligentRoute = null;
        this.mLastNetworkType = 0;
        this.mContext = null;
        this.mIpList = null;
        this.mCurrentRecordIdx = 0;
        this.mRetryCount = 0;
        this.mHandlerThread = null;
        this.mParam = null;
        this.mUploadQualityReport = null;
        this.mContext = context;
        if (iVar == null) {
            iVar = new i();
            iVar.f19590a = 0;
            iVar.f19595f = 3;
            iVar.f19594e = 3;
            iVar.f19596g = 40;
            iVar.f19597h = 1000;
            iVar.f19598i = true;
        }
        this.mParam = iVar;
        this.mThreadLock = new Object();
        c cVar = new c();
        this.mIntelligentRoute = cVar;
        cVar.f19521a = this;
        this.mUploaderInstance = 0L;
        this.mRetryCount = 0;
        this.mCurrentRecordIdx = 0;
        this.mIpList = null;
        this.mIsPushing = false;
        this.mThread = null;
        this.mRtmpUrl = null;
        this.mLastNetworkType = 0;
        this.mHandlerThread = null;
        this.mUploadQualityReport = new k(context);
        j.a().a(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getAddressFromUrl(String str) {
        int iIndexOf;
        String strSubstring;
        int iIndexOf2;
        return (str == null || (iIndexOf = str.indexOf("://")) == -1 || (iIndexOf2 = (strSubstring = str.substring(iIndexOf + 3)).indexOf("/")) == -1) ? "" : strSubstring.substring(0, iIndexOf2);
    }

    private boolean getNextRtmpProxyIP() {
        a aVar = this.mRtmpProxyParam;
        aVar.f19487f = 234L;
        aVar.f19488g = 80L;
        Vector<String> vector = this.mRtmpProxyIPList;
        if (vector == null || vector.size() <= 0) {
            return false;
        }
        if (this.mRtmpProxyIPIndex >= this.mRtmpProxyIPList.size()) {
            this.mRtmpProxyIPIndex = 0;
            return false;
        }
        String[] strArrSplit = this.mRtmpUrl.split("://");
        if (strArrSplit.length < 2) {
            return false;
        }
        String str = strArrSplit[1];
        String strSubstring = str.substring(str.indexOf("/"));
        String str2 = this.mRtmpProxyIPList.get(this.mRtmpProxyIPIndex);
        this.mRtmpProxyParam.f19489h = str2;
        this.mRtmpUrl = "room://" + str2 + strSubstring;
        this.mQuicChannel = true;
        this.mRtmpProxyIPIndex = this.mRtmpProxyIPIndex + 1;
        return true;
    }

    private HashMap getParamsFromUrl(String str) {
        String str2;
        HashMap map = new HashMap();
        String[] strArrSplit = str.split("[?]");
        if (strArrSplit != null && strArrSplit.length >= 2 && (str2 = strArrSplit[1]) != null && str2.length() != 0) {
            for (String str3 : strArrSplit[1].split("[&]")) {
                if (str3.indexOf("=") != -1) {
                    String[] strArrSplit2 = str3.split("[=]");
                    if (strArrSplit2.length == 2) {
                        map.put(strArrSplit2[0], strArrSplit2[1]);
                    }
                }
            }
        }
        return map;
    }

    private b getRtmpRealConnectInfo() {
        int i2;
        if (!this.mEnableNearestIP) {
            return new b(this.mRtmpUrl, false);
        }
        ArrayList<com.tencent.liteav.network.a> arrayList = this.mIpList;
        if (arrayList == null) {
            return new b(this.mRtmpUrl, false);
        }
        if (this.mCurrentRecordIdx >= arrayList.size() || (i2 = this.mCurrentRecordIdx) < 0) {
            return new b(this.mRtmpUrl, false);
        }
        com.tencent.liteav.network.a aVar = this.mIpList.get(i2);
        String[] strArrSplit = this.mRtmpUrl.split("://");
        if (strArrSplit.length < 2) {
            return new b(this.mRtmpUrl, false);
        }
        String[] strArrSplit2 = strArrSplit[1].split("/");
        if (aVar.f19496a.split(":").length <= 1 || aVar.f19496a.startsWith(StrPool.BRACKET_START)) {
            strArrSplit2[0] = aVar.f19496a + ":" + aVar.f19497b;
        } else {
            strArrSplit2[0] = StrPool.BRACKET_START + aVar.f19496a + "]:" + aVar.f19497b;
        }
        StringBuilder sb = new StringBuilder(strArrSplit2[0]);
        for (int i3 = 1; i3 < strArrSplit2.length; i3++) {
            sb.append("/");
            sb.append(strArrSplit2[i3]);
        }
        return new b(strArrSplit[0] + "://" + sb.toString(), aVar.f19498c);
    }

    private Long getSpeed(long j2, long j3, long j4) {
        if (j2 <= j3) {
            j3 -= j2;
        }
        return Long.valueOf(j4 > 0 ? ((j3 * 8) * 1000) / (j4 * 1024) : 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void internalReconnect(boolean z2) throws JSONException {
        if (this.mIsPushing) {
            if (this.mRtmpProxyEnable) {
                if (this.mLastNetworkType != com.tencent.liteav.basic.util.h.e(this.mContext)) {
                    TXCLog.e(TAG, "reconnect network switch from " + this.mLastNetworkType + " to " + com.tencent.liteav.basic.util.h.e(this.mContext));
                    this.mLastNetworkType = com.tencent.liteav.basic.util.h.e(this.mContext);
                    this.mRetryCount = 0;
                    Monitor.a(2, "WebrtcRoom: need enter again by user", "", 0);
                    sendNotifyEvent(1021, String.format("Network type has changed. Need to re-enter the room", new Object[0]));
                    return;
                }
                int i2 = this.mRetryCount;
                if (i2 >= this.mParam.f19594e) {
                    if (!getNextRtmpProxyIP()) {
                        TXCEventRecorderProxy.a(this.mRtmpUrl, 91002, -1L, -1L, "connect rtmp-proxy server failed(try all addresses)", 0);
                        sendNotifyEvent(TXLiteAVCode.ERR_RTMP_PUSH_NET_ALLADDRESS_FAIL);
                        return;
                    } else {
                        this.mRetryCount = 0;
                        Monitor.a(2, String.format("Network: reconnecting to upload server with quic.[addr:%s][retryCount:%d][retryLimit:%d]", this.mRtmpProxyParam.f19489h, 0, Integer.valueOf(this.mParam.f19594e)), "", 0);
                        sendNotifyEvent(1102);
                        startPushTask(this.mRtmpUrl, this.mQuicChannel, 0);
                        return;
                    }
                }
                int i3 = i2 + 1;
                this.mRetryCount = i3;
                Monitor.a(2, String.format("Network: reconnecting to upload server with quic.[addr:%s][retryCount:%d][retryLimit:%d]", this.mRtmpProxyParam.f19489h, Integer.valueOf(i3), Integer.valueOf(this.mParam.f19594e)), "", 0);
                TXCEventRecorderProxy.a(this.mRtmpUrl, 91003, -1L, -1L, "reconnect rtmp-proxy server(econnect retry count:" + this.mRetryCount + " retry limit:" + this.mParam.f19594e + ")", 0);
                sendNotifyEvent(1102);
                startPushTask(this.mRtmpUrl, this.mQuicChannel, 0);
                return;
            }
            this.mUploadQualityReport.c();
            if (this.mEnableNearestIP && this.mLastNetworkType != com.tencent.liteav.basic.util.h.e(this.mContext)) {
                TXCLog.e(TAG, "reconnect network switch from " + this.mLastNetworkType + " to " + com.tencent.liteav.basic.util.h.e(this.mContext));
                this.mLastNetworkType = com.tencent.liteav.basic.util.h.e(this.mContext);
                this.mIntelligentRoute.a(this.mRtmpUrl, this.mChannelType);
                this.mRetryCount = 0;
                return;
            }
            boolean z3 = !this.mEnableNearestIP ? false : z2;
            if (this.mQuicChannel) {
                z3 = true;
            }
            if (z3 && !nextRecordIdx(true)) {
                TXCLog.e(TAG, "reconnect: try all addresses failed");
                TXCEventRecorderProxy.a(this.mRtmpUrl, 91002, -1L, -1L, "connect upload server failed(try all addresses failed)", 0);
            }
            b rtmpRealConnectInfo = getRtmpRealConnectInfo();
            String addressFromUrl = getAddressFromUrl(rtmpRealConnectInfo.f19493a);
            StringBuilder sb = new StringBuilder();
            sb.append("reconnect change ip: ");
            sb.append(addressFromUrl);
            sb.append(" enableNearestIP: ");
            sb.append(this.mEnableNearestIP);
            sb.append(" last channel type: ");
            sb.append(this.mQuicChannel ? "Q Channel" : "TCP");
            TXCLog.e(TAG, sb.toString());
            if (this.mQuicChannel) {
                TXCLog.e(TAG, "reconnect last channel type is Q Channel，ignore retry limit");
                Monitor.a(2, String.format("Network: reconnecting to upload server with quic.[addr:%s]", addressFromUrl), "", 0);
                startPushTask(rtmpRealConnectInfo.f19493a, rtmpRealConnectInfo.f19494b, 0);
                sendNotifyEvent(1102);
                return;
            }
            TXCLog.e(TAG, "reconnect retry count:" + this.mRetryCount + " retry limit:" + this.mParam.f19594e);
            int i4 = this.mRetryCount;
            if (i4 >= this.mParam.f19594e) {
                TXCLog.e(TAG, "reconnect: try all times failed");
                TXCEventRecorderProxy.a(this.mRtmpUrl, 91002, -1L, -1L, "connect upload server failed(try all times failed)", 0);
                sendNotifyEvent(TXLiteAVCode.ERR_RTMP_PUSH_NET_ALLADDRESS_FAIL);
                return;
            }
            int i5 = i4 + 1;
            this.mRetryCount = i5;
            Monitor.a(2, String.format("Network: reconnecting to upload server with tcp.[addr:%s][retryCount:%d][retryLimit:%d]", addressFromUrl, Integer.valueOf(i5), Integer.valueOf(this.mParam.f19594e)), "", 0);
            TXCEventRecorderProxy.a(this.mRtmpUrl, 91003, -1L, -1L, "reconnect upload server:(retry count:" + this.mRetryCount + " retry limit:" + this.mParam.f19594e + ")", 0);
            startPushTask(rtmpRealConnectInfo.f19493a, rtmpRealConnectInfo.f19494b, 0);
            sendNotifyEvent(1102);
        }
    }

    private boolean isQCloudStreamUrl(String str) {
        int iIndexOf;
        String strSubstring;
        return (str == null || str.length() == 0 || (iIndexOf = str.indexOf("://")) == -1 || (strSubstring = str.substring(iIndexOf + 3)) == null || !strSubstring.startsWith("cloud.tencent.com")) ? false : true;
    }

    private native void nativeCacheJNIParams();

    private native void nativeEnableDrop(long j2, boolean z2);

    private native UploadStats nativeGetStats(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native long nativeInitRtmpMsgRecvThreadInstance(long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public native long nativeInitRtmpProxyInstance(long j2, long j3, String str, long j4, String str2, long j5, long j6, String str3, boolean z2, String str4);

    /* JADX INFO: Access modifiers changed from: private */
    public native long nativeInitUploader(String str, String str2, boolean z2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z3, int i9, HashMap<String, String> map);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnThreadRun(long j2);

    private native void nativePushAAC(long j2, byte[] bArr, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativePushNAL(long j2, byte[] bArr, int i2, long j3, long j4, long j5, boolean z2);

    private native void nativeReleaseJNIParams();

    private native void nativeRtmpMsgRecvThreadStart(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeRtmpMsgRecvThreadStop(long j2);

    private native void nativeRtmpProxyEnterRoom(long j2);

    private native void nativeRtmpProxyLeaveRoom(long j2);

    private native void nativeRtmpProxySendHeartBeat(long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12);

    private native void nativeSendRtmpProxyMsg(long j2, byte[] bArr);

    private native void nativeSendSeiMessage(long j2, int i2, byte[] bArr);

    private native void nativeSetSendStrategy(long j2, int i2, boolean z2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeSetVideoDropParams(long j2, boolean z2, int i2, int i3);

    private native void nativeStopPush(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeUninitRtmpMsgRecvThreadInstance(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeUninitRtmpProxyInstance(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeUninitUploader(long j2);

    private boolean nextRecordIdx(boolean z2) {
        ArrayList<com.tencent.liteav.network.a> arrayList = this.mIpList;
        if (arrayList != null && arrayList.size() != 0) {
            if (z2) {
                this.mIpList.get(this.mCurrentRecordIdx).f19500e++;
            }
            if (this.mCurrentRecordIdx + 1 < this.mIpList.size()) {
                this.mCurrentRecordIdx++;
                return true;
            }
        }
        return false;
    }

    private void onRtmpProxyRoomEvent(int i2, int i3) {
        if (i2 == 1) {
            sendNotifyEvent(1018, String.format("Already in room，[%d]", Integer.valueOf(i3)));
        } else if (i2 == 2) {
            sendNotifyEvent(1019, String.format("Not in the room，[%d]", Integer.valueOf(i3)));
        }
    }

    private void onRtmpProxyUserListPushed(RtmpProxyUserInfo[] rtmpProxyUserInfoArr) throws JSONException {
        if (rtmpProxyUserInfoArr != null && this.mIsPushing && this.mRtmpProxyEnable && this.mRtmpProxyParam != null) {
            try {
                JSONArray jSONArray = new JSONArray();
                JSONArray jSONArray2 = new JSONArray();
                for (int i2 = 0; i2 < rtmpProxyUserInfoArr.length; i2++) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("userid", rtmpProxyUserInfoArr[i2].account);
                    jSONObject.put(SocialConstants.PARAM_PLAY_URL, rtmpProxyUserInfoArr[i2].playUrl);
                    if (rtmpProxyUserInfoArr[i2].stmType == 0) {
                        jSONArray.put(jSONObject);
                    } else {
                        jSONArray2.put(jSONObject);
                    }
                }
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("userlist", jSONArray);
                jSONObject2.put("userlist_aux", jSONArray2);
                sendNotifyEvent(1020, jSONObject2.toString());
            } catch (Exception e2) {
                TXCLog.e(TAG, "build json object failed.", e2);
            }
        }
    }

    private void onSendRtmpProxyMsg(byte[] bArr) {
        synchronized (this.mThreadLock) {
            long j2 = this.mUploaderInstance;
            if (j2 != 0) {
                nativeSendRtmpProxyMsg(j2, bArr);
            }
        }
    }

    private void parseProxyInfo(String str) throws JSONException {
        if (str == null || str.length() == 0 || !str.startsWith("room")) {
            return;
        }
        this.mRtmpProxyParam.f19490i = isQCloudStreamUrl(str);
        HashMap paramsFromUrl = getParamsFromUrl(str);
        if (paramsFromUrl == null) {
            return;
        }
        if (paramsFromUrl.containsKey("sdkappid")) {
            this.mRtmpProxyParam.f19482a = Long.valueOf((String) paramsFromUrl.get("sdkappid")).longValue();
        }
        if (paramsFromUrl.containsKey("roomid") && paramsFromUrl.containsKey("userid") && paramsFromUrl.containsKey("roomsig")) {
            this.mRtmpProxyParam.f19485d = Long.valueOf((String) paramsFromUrl.get("roomid")).longValue();
            this.mRtmpProxyParam.f19484c = (String) paramsFromUrl.get("userid");
            if (paramsFromUrl.containsKey("bizbuf")) {
                try {
                    this.mRtmpProxyParam.f19491j = URLDecoder.decode((String) paramsFromUrl.get("bizbuf"), "UTF-8");
                } catch (Exception e2) {
                    TXCLog.e(TAG, "decode bizbuf failed.", e2);
                }
            }
            try {
                JSONObject jSONObject = new JSONObject(URLDecoder.decode((String) paramsFromUrl.get("roomsig"), "UTF-8"));
                this.mRtmpProxyParam.f19483b = 0L;
                if (jSONObject.has("Key")) {
                    this.mRtmpProxyParam.f19486e = jSONObject.optString("Key");
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("RtmpProxy");
                    if (jSONObjectOptJSONObject == null || (jSONObjectOptJSONObject.has("Ip") && jSONObjectOptJSONObject.has("Port") && jSONObjectOptJSONObject.has("Type"))) {
                        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("AccessList");
                        if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 0) {
                            for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                                JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i2);
                                if (jSONObject2 != null && jSONObject2.has("Ip") && jSONObject2.has("Port") && jSONObject2.has("Type")) {
                                    String strOptString = jSONObject2.optString("Ip");
                                    long jOptLong = jSONObject2.optLong("Port");
                                    if (jSONObject2.optLong("Type") == 2) {
                                        this.mRtmpProxyIPList.add(strOptString + ":" + jOptLong);
                                    }
                                }
                            }
                        }
                        if (!this.mRtmpProxyParam.f19490i) {
                            this.mRtmpUrl = str;
                            this.mQuicChannel = false;
                        } else {
                            if (jSONObjectOptJSONObject == null) {
                                return;
                            }
                            this.mRtmpUrl = str.substring(0, str.indexOf("?")) + "/webrtc/" + (this.mRtmpProxyParam.f19482a + StrPool.UNDERLINE + this.mRtmpProxyParam.f19485d + StrPool.UNDERLINE + this.mRtmpProxyParam.f19484c) + "?real_rtmp_ip=" + jSONObjectOptJSONObject.optString("Ip") + "&real_rtmp_port=" + jSONObjectOptJSONObject.optLong("Port") + "&tinyid=" + this.mRtmpProxyParam.f19483b + "&srctinyid=0";
                            getNextRtmpProxyIP();
                        }
                        this.mRtmpProxyEnable = true;
                    }
                }
            } catch (Exception e3) {
                TXCLog.e(TAG, "parse proxy info failed.", e3);
            }
        }
    }

    private void postReconnectMsg(String str, boolean z2, int i2) {
        Message message = new Message();
        message.what = 101;
        message.obj = str;
        message.arg1 = z2 ? 2 : 1;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendMessageDelayed(message, i2);
        }
    }

    private void reconnect(final boolean z2) {
        stopPushTask();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.postDelayed(new Runnable() { // from class: com.tencent.liteav.network.TXCStreamUploader.3
                @Override // java.lang.Runnable
                public void run() throws JSONException {
                    TXCStreamUploader.this.internalReconnect(z2);
                }
            }, this.mParam.f19595f * 1000);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportNetStatus() {
        long jLongValue;
        long jLongValue2;
        long jLongValue3;
        int i2;
        long timeTick = TXCTimeUtil.getTimeTick();
        long j2 = timeTick - this.mLastTimeStamp;
        UploadStats uploadStats = getUploadStats();
        long j3 = 0;
        if (uploadStats != null) {
            UploadStats uploadStats2 = this.mLastUploadStats;
            if (uploadStats2 != null) {
                i2 = 7005;
                long jLongValue4 = getSpeed(uploadStats2.inVideoBytes, uploadStats.inVideoBytes, j2).longValue();
                jLongValue2 = getSpeed(this.mLastUploadStats.inAudioBytes, uploadStats.inAudioBytes, j2).longValue();
                jLongValue3 = getSpeed(this.mLastUploadStats.outVideoBytes, uploadStats.outVideoBytes, j2).longValue();
                jLongValue = getSpeed(this.mLastUploadStats.outAudioBytes, uploadStats.outAudioBytes, j2).longValue();
                j3 = jLongValue4;
            } else {
                i2 = 7005;
                jLongValue = 0;
                jLongValue2 = 0;
                jLongValue3 = 0;
            }
            setStatusValue(i2, Long.valueOf(uploadStats.videoCacheLen));
            setStatusValue(R2.dimen.dp_411, Long.valueOf(uploadStats.audioCacheLen));
            setStatusValue(R2.dimen.dp_412, Long.valueOf(uploadStats.videoDropCount));
            setStatusValue(R2.dimen.dp_413, Long.valueOf(uploadStats.audioDropCount));
            setStatusValue(R2.dimen.dp_425, Long.valueOf(uploadStats.bandwidthEst));
            setStatusValue(R2.dimen.dp_414, Long.valueOf(uploadStats.startTS));
            setStatusValue(R2.dimen.dp_415, Long.valueOf(uploadStats.dnsTS));
            setStatusValue(R2.dimen.dp_416, Long.valueOf(uploadStats.connTS));
            setStatusValue(R2.dimen.dp_417, String.valueOf(uploadStats.serverIP));
            setStatusValue(R2.dimen.dp_418, Long.valueOf(this.mQuicChannel ? 2L : 1L));
            setStatusValue(R2.dimen.dp_419, uploadStats.connectionID);
            setStatusValue(R2.dimen.dp_42, uploadStats.connectionStats);
            this.mUploadQualityReport.a(uploadStats.videoDropCount, uploadStats.audioDropCount);
            this.mUploadQualityReport.b(uploadStats.videoCacheLen, uploadStats.audioCacheLen);
        } else {
            setStatusValue(R2.dimen.dp_410, 0L);
            setStatusValue(R2.dimen.dp_411, 0L);
            setStatusValue(R2.dimen.dp_412, 0L);
            setStatusValue(R2.dimen.dp_413, 0L);
            jLongValue = 0;
            jLongValue2 = 0;
            jLongValue3 = 0;
        }
        setStatusValue(7001, Long.valueOf(j3));
        setStatusValue(7002, Long.valueOf(jLongValue2));
        setStatusValue(7003, Long.valueOf(jLongValue3));
        setStatusValue(R2.dimen.dp_41, Long.valueOf(jLongValue));
        this.mLastTimeStamp = timeTick;
        this.mLastUploadStats = uploadStats;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendEmptyMessageDelayed(103, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rtmpProxySendHeartBeat() throws Throwable {
        int[] iArrA = com.tencent.liteav.basic.util.h.a();
        long j2 = iArrA[0] / 10;
        long j3 = iArrA[1] / 10;
        long jC = TXCStatus.c(getID(), R2.dimen.dp_41);
        long jC2 = TXCStatus.c(getID(), 7003);
        long jC3 = TXCStatus.c(getID(), 1001);
        long jC4 = TXCStatus.c(getID(), 4001);
        long jC5 = TXCStatus.c(getID(), R2.dimen.dp_411);
        long jC6 = TXCStatus.c(getID(), R2.dimen.dp_410);
        long jC7 = TXCStatus.c(getID(), R2.dimen.dp_413);
        long jC8 = TXCStatus.c(getID(), R2.dimen.dp_412);
        synchronized (this.mRtmpProxyLock) {
            try {
                try {
                    nativeRtmpProxySendHeartBeat(this.mRtmpProxyInstance, j2, j3, jC, jC2, jC3, jC4, jC5, jC6, jC7, jC8);
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    private void sendNotifyEvent(int i2, String str) {
        if (str == null || str.isEmpty()) {
            sendNotifyEvent(i2);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(TXLiveConstants.EVT_DESCRIPTION, str);
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            bundle.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
            com.tencent.liteav.basic.util.h.a(this.mNotifyListener, i2, bundle);
        }
        if (i2 != 1002) {
            if (i2 == 1101) {
                this.mUploadQualityReport.d();
            }
        } else {
            UploadStats uploadStats = getUploadStats();
            if (uploadStats != null) {
                this.mUploadQualityReport.a(uploadStats.dnsparseTimeCost, uploadStats.connectTimeCost, uploadStats.handshakeTimeCost);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPushTask(final String str, final boolean z2, int i2) {
        TXCLog.i(TAG, "start push task");
        boolean z3 = this.mQuicChannel;
        if (z3 != z2 && z3) {
            Monitor.a(2, String.format("Network: switch push channel from quic to tcp.[retryCount:%d][retryLimit:%d]", Integer.valueOf(this.mRetryCount), Integer.valueOf(this.mParam.f19594e)), "", 0);
        }
        if (z2) {
            int i3 = this.mConnectCountQuic + 1;
            this.mConnectCountQuic = i3;
            setStatusValue(R2.dimen.dp_421, Long.valueOf(i3));
        } else {
            int i4 = this.mConnectCountTcp + 1;
            this.mConnectCountTcp = i4;
            setStatusValue(R2.dimen.dp_422, Long.valueOf(i4));
        }
        Thread thread = new Thread("RTMPUpload") { // from class: com.tencent.liteav.network.TXCStreamUploader.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() throws InterruptedException {
                Iterator it;
                while (TXCStreamUploader.this.mUploaderInstance != 0) {
                    try {
                        Thread.sleep(100L, 0);
                    } catch (InterruptedException unused) {
                    }
                }
                TXCStreamUploader.this.mUploadQualityReport.b();
                TXCStreamUploader.this.mUploadQualityReport.a(TXCStreamUploader.this.mParam.f19599j);
                TXCStreamUploader.this.mUploadQualityReport.a(TXCStreamUploader.this.mRtmpUrl);
                TXCStreamUploader.this.mUploadQualityReport.a(z2, TXCStreamUploader.this.getAddressFromUrl(str));
                synchronized (TXCStreamUploader.this.mThreadLock) {
                    TXCStreamUploader.this.mQuicChannel = z2;
                    int i5 = TXCStreamUploader.this.mParam.f19599j ? TXCStreamUploader.this.mQuicChannel ? 3 : 2 : 1;
                    if (TXCStreamUploader.this.mRtmpProxyEnable) {
                        if (TXCStreamUploader.this.mAudioMuted) {
                            TXCStreamUploader.this.mParam.f19600k = false;
                        }
                    } else if (TXCStreamUploader.this.mIpList == null || TXCStreamUploader.this.mIpList.size() == 0) {
                        i5 = 1;
                    }
                    TXCStreamUploader.this.setStatusValue(R2.dimen.dp_424, Long.valueOf(i5));
                    TXCStreamUploader tXCStreamUploader = TXCStreamUploader.this;
                    tXCStreamUploader.mUploaderInstance = tXCStreamUploader.nativeInitUploader(tXCStreamUploader.mRtmpUrl, str, z2, TXCStreamUploader.this.mParam.f19593d, TXCStreamUploader.this.mParam.f19592c, TXCStreamUploader.this.mParam.f19590a, TXCStreamUploader.this.mParam.f19591b, TXCStreamUploader.this.mParam.f19596g, 16, i5, TXCStreamUploader.this.mParam.f19600k, TXCStreamUploader.this.mParam.f19601l, TXCStreamUploader.this.mMetaData);
                    if (TXCStreamUploader.this.mUploaderInstance != 0) {
                        TXCStreamUploader tXCStreamUploader2 = TXCStreamUploader.this;
                        tXCStreamUploader2.nativeSetVideoDropParams(tXCStreamUploader2.mUploaderInstance, TXCStreamUploader.this.mParam.f19598i, TXCStreamUploader.this.mParam.f19596g, TXCStreamUploader.this.mParam.f19597h);
                        Iterator it2 = TXCStreamUploader.this.mVecPendingNAL.iterator();
                        boolean z4 = false;
                        while (it2.hasNext()) {
                            TXSNALPacket tXSNALPacket = (TXSNALPacket) it2.next();
                            if (!z4 && tXSNALPacket.nalType == 0) {
                                z4 = true;
                            }
                            if (z4) {
                                TXCStreamUploader tXCStreamUploader3 = TXCStreamUploader.this;
                                it = it2;
                                tXCStreamUploader3.nativePushNAL(tXCStreamUploader3.mUploaderInstance, tXSNALPacket.nalData, tXSNALPacket.nalType, tXSNALPacket.frameIndex, tXSNALPacket.pts, tXSNALPacket.dts, tXSNALPacket.codecId == 1);
                            } else {
                                it = it2;
                            }
                            it2 = it;
                        }
                        TXCStreamUploader.this.mVecPendingNAL.removeAllElements();
                    }
                }
                if (TXCStreamUploader.this.mRtmpProxyEnable) {
                    synchronized (TXCStreamUploader.this.mRtmpProxyLock) {
                        TXCStreamUploader tXCStreamUploader4 = TXCStreamUploader.this;
                        tXCStreamUploader4.mRtmpProxyInstance = tXCStreamUploader4.nativeInitRtmpProxyInstance(tXCStreamUploader4.mRtmpProxyParam.f19482a, TXCStreamUploader.this.mRtmpProxyParam.f19483b, TXCStreamUploader.this.mRtmpProxyParam.f19484c, TXCStreamUploader.this.mRtmpProxyParam.f19485d, TXCStreamUploader.this.mRtmpProxyParam.f19486e, TXCStreamUploader.this.mRtmpProxyParam.f19487f, TXCStreamUploader.this.mRtmpProxyParam.f19488g, TXCStreamUploader.this.mRtmpProxyParam.f19489h, TXCStreamUploader.this.mRtmpProxyParam.f19490i, TXCStreamUploader.this.mRtmpProxyParam.f19491j);
                    }
                    synchronized (TXCStreamUploader.this.mRtmpMsgRecvThreadLock) {
                        TXCStreamUploader tXCStreamUploader5 = TXCStreamUploader.this;
                        tXCStreamUploader5.mRtmpMsgRecvThreadInstance = tXCStreamUploader5.nativeInitRtmpMsgRecvThreadInstance(tXCStreamUploader5.mRtmpProxyInstance, TXCStreamUploader.this.mUploaderInstance);
                    }
                }
                TXCStreamUploader tXCStreamUploader6 = TXCStreamUploader.this;
                tXCStreamUploader6.nativeOnThreadRun(tXCStreamUploader6.mUploaderInstance);
                if (TXCStreamUploader.this.mRtmpProxyEnable) {
                    synchronized (TXCStreamUploader.this.mRtmpMsgRecvThreadLock) {
                        TXCStreamUploader tXCStreamUploader7 = TXCStreamUploader.this;
                        tXCStreamUploader7.nativeRtmpMsgRecvThreadStop(tXCStreamUploader7.mRtmpMsgRecvThreadInstance);
                        TXCStreamUploader tXCStreamUploader8 = TXCStreamUploader.this;
                        tXCStreamUploader8.nativeUninitRtmpMsgRecvThreadInstance(tXCStreamUploader8.mRtmpMsgRecvThreadInstance);
                        TXCStreamUploader.this.mRtmpMsgRecvThreadInstance = 0L;
                    }
                    synchronized (TXCStreamUploader.this.mRtmpProxyLock) {
                        TXCStreamUploader tXCStreamUploader9 = TXCStreamUploader.this;
                        tXCStreamUploader9.nativeUninitRtmpProxyInstance(tXCStreamUploader9.mRtmpProxyInstance);
                        TXCStreamUploader.this.mRtmpProxyInstance = 0L;
                    }
                }
                synchronized (TXCStreamUploader.this.mThreadLock) {
                    TXCStreamUploader tXCStreamUploader10 = TXCStreamUploader.this;
                    tXCStreamUploader10.nativeUninitUploader(tXCStreamUploader10.mUploaderInstance);
                    TXCStreamUploader.this.mUploaderInstance = 0L;
                }
            }
        };
        this.mThread = thread;
        thread.start();
    }

    private void stopPushTask() {
        TXCLog.i(TAG, "stop push task");
        synchronized (this.mThreadLock) {
            this.mVecPendingNAL.removeAllElements();
            nativeStopPush(this.mUploaderInstance);
        }
    }

    private void tryResetRetryCount() {
        if (this.mConnectSuccessTimeStamps != 0) {
            long timeTick = TXCTimeUtil.getTimeTick() - this.mConnectSuccessTimeStamps;
            i iVar = this.mParam;
            if (timeTick > iVar.f19594e * (iVar.f19595f + 13) * 1000) {
                this.mRetryCount = 0;
                this.mConnectSuccessTimeStamps = 0L;
                TXCLog.i(TAG, "reset retry count");
            }
        }
    }

    public String getConfusionIP(String str) {
        int iIndexOf;
        String strSubstring;
        int iIndexOf2;
        if (str == null || (iIndexOf = str.indexOf(StrPool.DOT)) == -1 || (iIndexOf2 = (strSubstring = str.substring(iIndexOf + 1)).indexOf(StrPool.DOT)) == -1) {
            return str;
        }
        return "A.B." + strSubstring.substring(iIndexOf2 + 1);
    }

    public UploadStats getUploadStats() {
        UploadStats uploadStatsNativeGetStats;
        synchronized (this.mThreadLock) {
            uploadStatsNativeGetStats = nativeGetStats(this.mUploaderInstance);
            if (uploadStatsNativeGetStats != null) {
                uploadStatsNativeGetStats.channelType = this.mQuicChannel ? 2L : 1L;
            }
        }
        return uploadStatsNativeGetStats;
    }

    public int init() {
        return 0;
    }

    @Override // com.tencent.liteav.network.b
    public void onFetchDone(int i2, ArrayList<com.tencent.liteav.network.a> arrayList) {
        String str;
        if (this.mIsPushing) {
            if (i2 == 1) {
                TXCLog.w(TAG, "onFetchDone: Network connection failed. Invalid push url!");
                sendNotifyEvent(-1313, "Network connection failed. Invalid push url!");
                return;
            }
            if (i2 != 0 || arrayList == null || arrayList.size() <= 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("onFetchDone: code = ");
                sb.append(i2);
                sb.append(" ip count = ");
                sb.append(arrayList != null ? arrayList.size() : 0);
                TXCLog.i(TAG, sb.toString());
            } else {
                TXCLog.i(TAG, "onFetchDone: connect success, ip count = " + arrayList.size());
                this.mIpList = arrayList;
                this.mCurrentRecordIdx = 0;
                Iterator<com.tencent.liteav.network.a> it = arrayList.iterator();
                String str2 = "";
                int i3 = 0;
                while (it.hasNext()) {
                    com.tencent.liteav.network.a next = it.next();
                    if (next != null && next.f19498c && (str = next.f19496a) != null && str.length() > 0) {
                        i3++;
                    }
                    if (next != null) {
                        str2 = str2 + " " + getConfusionIP(next.f19496a) + ":" + next.f19497b;
                    }
                }
                setStatusValue(R2.dimen.dp_420, Long.valueOf(i3));
                setStatusValue(R2.dimen.dp_423, StrPool.DELIM_START + str2 + " }");
            }
            b rtmpRealConnectInfo = getRtmpRealConnectInfo();
            postReconnectMsg(rtmpRealConnectInfo.f19493a, rtmpRealConnectInfo.f19494b, 0);
        }
    }

    public void pushAAC(byte[] bArr, long j2) {
        tryResetRetryCount();
        synchronized (this.mThreadLock) {
            if (!this.mAudioMuted || !this.mRtmpProxyEnable) {
                nativePushAAC(this.mUploaderInstance, bArr, j2);
            }
        }
    }

    public void pushNAL(TXSNALPacket tXSNALPacket) {
        byte[] bArr;
        tryResetRetryCount();
        synchronized (this.mThreadLock) {
            long j2 = this.mUploaderInstance;
            if (j2 == 0) {
                if (tXSNALPacket.nalType == 0) {
                    this.mVecPendingNAL.removeAllElements();
                }
                this.mVecPendingNAL.add(tXSNALPacket);
            } else if (tXSNALPacket != null && (bArr = tXSNALPacket.nalData) != null && bArr.length > 0) {
                nativePushNAL(j2, bArr, tXSNALPacket.nalType, tXSNALPacket.frameIndex, tXSNALPacket.pts, tXSNALPacket.dts, tXSNALPacket.codecId == 1);
            }
        }
    }

    public void sendSeiMessage(int i2, byte[] bArr) {
        nativeSendSeiMessage(this.mUploaderInstance, i2, bArr);
    }

    public void setAudioInfo(int i2, int i3) {
        i iVar = this.mParam;
        if (iVar != null) {
            iVar.f19592c = i3;
            iVar.f19593d = i2;
        }
    }

    public void setAudioMute(boolean z2) {
        synchronized (this.mThreadLock) {
            this.mAudioMuted = z2;
            if (this.mRtmpProxyEnable) {
                long j2 = this.mUploaderInstance;
                if (j2 != 0) {
                    nativeSetSendStrategy(j2, this.mParam.f19599j ? this.mQuicChannel ? 3 : 2 : 1, false);
                }
            }
        }
    }

    public void setDropEanble(boolean z2) {
        StringBuilder sb = new StringBuilder();
        sb.append("drop enable ");
        sb.append(z2 ? "yes" : SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO);
        TXCLog.i(TAG, sb.toString());
        synchronized (this.mThreadLock) {
            nativeEnableDrop(this.mUploaderInstance, z2);
        }
    }

    public void setMetaData(HashMap<String, String> map) {
        this.mMetaData = map;
    }

    public void setMode(int i2) {
        i iVar = this.mParam;
        if (iVar != null) {
            iVar.f19590a = i2;
        }
    }

    public void setNotifyListener(com.tencent.liteav.basic.b.b bVar) {
        this.mNotifyListener = new WeakReference<>(bVar);
    }

    public void setRetryInterval(int i2) {
        i iVar = this.mParam;
        if (iVar != null) {
            iVar.f19595f = i2;
        }
    }

    public void setRetryTimes(int i2) {
        i iVar = this.mParam;
        if (iVar != null) {
            iVar.f19594e = i2;
        }
    }

    public void setSendStrategy(boolean z2, boolean z3) {
        ArrayList<com.tencent.liteav.network.a> arrayList;
        i iVar = this.mParam;
        iVar.f19599j = z2;
        iVar.f19600k = z3;
        this.mUploadQualityReport.a(z2);
        int i2 = 1;
        int i3 = z2 ? this.mQuicChannel ? 3 : 2 : 1;
        if (this.mRtmpProxyEnable || ((arrayList = this.mIpList) != null && arrayList.size() != 0)) {
            i2 = i3;
        }
        synchronized (this.mThreadLock) {
            long j2 = this.mUploaderInstance;
            if (j2 != 0) {
                nativeSetSendStrategy(j2, i2, z3);
            }
        }
        setStatusValue(R2.dimen.dp_424, Long.valueOf(i2));
    }

    public void setVideoDropParams(boolean z2, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append("drop params wait i frame:");
        sb.append(z2 ? "yes" : SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO);
        sb.append(" max video count:");
        sb.append(i2);
        sb.append(" max video cache time: ");
        sb.append(i3);
        sb.append(" ms");
        TXCLog.i(TAG, sb.toString());
        synchronized (this.mThreadLock) {
            i iVar = this.mParam;
            iVar.f19598i = z2;
            iVar.f19596g = i2;
            iVar.f19597h = i3;
            long j2 = this.mUploaderInstance;
            if (j2 != 0) {
                nativeSetVideoDropParams(j2, z2, i2, i3);
            }
        }
    }

    public String start(String str, boolean z2, int i2) throws JSONException {
        if (this.mIsPushing) {
            return this.mRtmpUrl;
        }
        this.mIsPushing = true;
        this.mConnectSuccessTimeStamps = 0L;
        this.mRetryCount = 0;
        this.mRtmpUrl = str;
        this.mChannelType = i2;
        this.mConnectCountQuic = 0;
        this.mConnectCountTcp = 0;
        this.mRtmpProxyEnable = false;
        this.mRtmpProxyParam.a();
        this.mRtmpProxyIPList.clear();
        this.mRtmpProxyIPIndex = 0;
        this.mRtmpProxyInstance = 0L;
        this.mRtmpMsgRecvThreadInstance = 0L;
        setStatusValue(R2.dimen.dp_420, 0L);
        setStatusValue(R2.dimen.dp_421, 0L);
        setStatusValue(R2.dimen.dp_422, 0L);
        this.mUploadQualityReport.a();
        StringBuilder sb = new StringBuilder();
        sb.append("start push with url:");
        sb.append(this.mRtmpUrl);
        sb.append(" enable nearest ip:");
        sb.append(z2 ? "yes" : SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO);
        sb.append("channel type:");
        sb.append(i2);
        TXCLog.i(TAG, sb.toString());
        if (com.tencent.liteav.basic.util.h.e(this.mContext) == 0) {
            sendNotifyEvent(TXLiteAVCode.ERR_RTMP_PUSH_NO_NETWORK);
            return this.mRtmpUrl;
        }
        this.mEnableNearestIP = z2;
        if (this.mHandlerThread == null) {
            HandlerThread handlerThread = new HandlerThread("RTMP_PUSH");
            this.mHandlerThread = handlerThread;
            handlerThread.start();
        }
        this.mHandler = new Handler(this.mHandlerThread.getLooper()) { // from class: com.tencent.liteav.network.TXCStreamUploader.1
            @Override // android.os.Handler
            public void handleMessage(Message message) throws Throwable {
                int i3 = message.what;
                if (i3 == 101) {
                    TXCStreamUploader.this.startPushTask((String) message.obj, message.arg1 == 2, 0);
                    return;
                }
                if (i3 == 103) {
                    TXCStreamUploader.this.reportNetStatus();
                } else {
                    if (i3 != 104) {
                        return;
                    }
                    TXCStreamUploader.this.rtmpProxySendHeartBeat();
                    if (TXCStreamUploader.this.mHandler != null) {
                        TXCStreamUploader.this.mHandler.sendEmptyMessageDelayed(104, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                    }
                }
            }
        };
        parseProxyInfo(str);
        if (this.mRtmpProxyEnable) {
            this.mLastNetworkType = com.tencent.liteav.basic.util.h.e(this.mContext);
            nativeCacheJNIParams();
            startPushTask(this.mRtmpUrl, this.mQuicChannel, 0);
        } else if (!this.mEnableNearestIP || this.mLastNetworkType == com.tencent.liteav.basic.util.h.e(this.mContext)) {
            startPushTask(this.mRtmpUrl, this.mQuicChannel, 0);
        } else {
            TXCLog.i(TAG, "fetching nearest ip list");
            this.mLastNetworkType = com.tencent.liteav.basic.util.h.e(this.mContext);
            this.mIntelligentRoute.a(str, i2);
        }
        this.mHandler.sendEmptyMessageDelayed(103, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        return this.mRtmpUrl;
    }

    public void stop() throws JSONException {
        if (this.mIsPushing) {
            this.mIsPushing = false;
            TXCLog.i(TAG, "stop push");
            if (this.mRtmpProxyEnable) {
                synchronized (this.mRtmpProxyLock) {
                    nativeRtmpProxyLeaveRoom(this.mRtmpProxyInstance);
                }
            }
            synchronized (this.mThreadLock) {
                nativeStopPush(this.mUploaderInstance);
            }
            HandlerThread handlerThread = this.mHandlerThread;
            if (handlerThread != null) {
                handlerThread.getLooper().quit();
                this.mHandlerThread = null;
            }
            if (this.mHandler != null) {
                this.mHandler = null;
            }
            if (this.mRtmpProxyEnable) {
                nativeReleaseJNIParams();
            }
            this.mUploadQualityReport.c();
            this.mUploadQualityReport.a();
        }
    }

    private void sendNotifyEvent(int i2) {
        if (i2 == 0) {
            reconnect(false);
            return;
        }
        if (i2 == 1) {
            reconnect(true);
            return;
        }
        if (i2 == 1001) {
            this.mConnectSuccessTimeStamps = TXCTimeUtil.getTimeTick();
        }
        if (i2 == 1026) {
            if (this.mRtmpProxyEnable) {
                synchronized (this.mRtmpMsgRecvThreadLock) {
                    nativeRtmpMsgRecvThreadStart(this.mRtmpMsgRecvThreadInstance);
                }
                synchronized (this.mRtmpProxyLock) {
                    nativeRtmpProxyEnterRoom(this.mRtmpProxyInstance);
                }
                Handler handler = this.mHandler;
                if (handler != null) {
                    handler.sendEmptyMessageDelayed(104, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                    return;
                }
                return;
            }
            return;
        }
        if (this.mNotifyListener != null) {
            Bundle bundle = new Bundle();
            if (i2 == -2308) {
                bundle.putString(TXLiveConstants.EVT_DESCRIPTION, "The server rejects the connection request. It may be that the push URL has been occupied or expired, or the anti-leech link is wrong.");
            } else if (i2 == -1307) {
                bundle.putString(TXLiveConstants.EVT_DESCRIPTION, "failed to connect server for several times, abort connection");
            } else {
                if (i2 == 1026) {
                    if (this.mRtmpProxyEnable) {
                        synchronized (this.mRtmpMsgRecvThreadLock) {
                            nativeRtmpMsgRecvThreadStart(this.mRtmpMsgRecvThreadInstance);
                        }
                        synchronized (this.mRtmpProxyLock) {
                            nativeRtmpProxyEnterRoom(this.mRtmpProxyInstance);
                        }
                        Handler handler2 = this.mHandler;
                        if (handler2 != null) {
                            handler2.sendEmptyMessageDelayed(104, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (i2 == 3003) {
                    bundle.putString(TXLiveConstants.EVT_DESCRIPTION, "RTMP servers handshake failed");
                } else if (i2 == -1325) {
                    bundle.putString(TXLiveConstants.EVT_DESCRIPTION, "No internet. Please check if WiFi or mobile data is turned on");
                } else if (i2 == -1324) {
                    bundle.putString(TXLiveConstants.EVT_DESCRIPTION, "Failed to connect all IPs, abort connection.");
                } else if (i2 == 1001) {
                    bundle.putString(TXLiveConstants.EVT_DESCRIPTION, "Already connected to rtmp server");
                } else if (i2 == 1002) {
                    bundle.putString(TXLiveConstants.EVT_DESCRIPTION, "rtmp start push stream");
                } else if (i2 == 1101) {
                    bundle.putString(TXLiveConstants.EVT_DESCRIPTION, "Insufficient upstream bandwidth. Data transmission is not timely");
                } else if (i2 == 1102) {
                    bundle.putString(TXLiveConstants.EVT_DESCRIPTION, "Enables network reconnection");
                } else if (i2 == 3008) {
                    bundle.putString(TXLiveConstants.EVT_DESCRIPTION, "No data is sent for more than 30s. Actively disconnect");
                } else if (i2 != 3009) {
                    bundle.putString(TXLiveConstants.EVT_DESCRIPTION, "UNKNOWN");
                } else {
                    bundle.putString(TXLiveConstants.EVT_DESCRIPTION, "Failed to connect server");
                }
            }
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            bundle.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
            com.tencent.liteav.basic.util.h.a(this.mNotifyListener, i2, bundle);
        }
    }
}
