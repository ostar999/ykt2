package com.tencent.liteav.network;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.google.android.exoplayer2.ExoPlayer;
import com.just.agentweb.DefaultWebClient;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.network.TXIStreamDownloader;
import com.tencent.liteav.network.d;
import com.tencent.liteav.network.h;
import com.tencent.rtmp.TXLiveConstants;
import com.yikaobang.yixue.R2;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/* loaded from: classes6.dex */
public class TXCStreamDownloader extends com.tencent.liteav.basic.module.a implements com.tencent.liteav.basic.b.b, TXIStreamDownloader.a, d.a, f, g {
    public static final String TAG = "TXCStreamDownloader";
    private h mAccUrlFetcher;
    private Context mApplicationContext;
    private int mDownloadFormat;
    private TXIStreamDownloader mDownloader;
    private Handler mHandler;
    protected Map<String, String> mHeaders;
    private g mListener = null;
    private byte[] mListenerLock = new byte[0];
    private com.tencent.liteav.basic.b.b mNotifyListener = null;
    private boolean mDownloaderRunning = false;
    private String mOriginPlayUrl = "";
    private boolean mEnableNearestIP = false;
    private int mChannelType = 0;
    private boolean mEnableMessage = false;
    private boolean mEnableMetaData = false;
    private String mFlvSessionKey = "";
    private long mLastTimeStamp = 0;
    private DownloadStats mLastDownloadStats = null;
    private boolean mRecvFirstNal = false;
    private long mSwitchStartTime = 0;
    private long mCurrentNalTs = 0;
    private long mLastIFramelTs = 0;
    private d mStreamSwitcher = null;
    private int mPayLoadType = 0;
    private Runnable mReportNetStatusRunnalbe = new Runnable() { // from class: com.tencent.liteav.network.TXCStreamDownloader.3
        @Override // java.lang.Runnable
        public void run() {
            TXCStreamDownloader.this.reportNetStatus();
        }
    };

    public static class DownloadStats {
        public long afterParseAudioBytes;
        public long afterParseVideoBytes;
        public long beforeParseAudioBytes;
        public long beforeParseVideoBytes;
        public long connTS;
        public long dnsTS;
        public int errorCode;
        public String errorInfo;
        public long firstAudioTS;
        public long firstVideoTS;
        public String flvSessionKey;
        public String serverIP;
        public long startTS;
        public long videoGop;
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public String f19470a;

        /* renamed from: b, reason: collision with root package name */
        public String f19471b;

        /* renamed from: c, reason: collision with root package name */
        public String f19472c;

        /* renamed from: d, reason: collision with root package name */
        public int f19473d;

        /* renamed from: e, reason: collision with root package name */
        public String f19474e;

        /* renamed from: f, reason: collision with root package name */
        public boolean f19475f;
    }

    static {
        com.tencent.liteav.basic.util.h.d();
    }

    public TXCStreamDownloader(Context context, int i2) {
        this.mDownloader = null;
        this.mDownloadFormat = 1;
        this.mHandler = null;
        if (i2 == 0) {
            TXCFLVDownloader tXCFLVDownloader = new TXCFLVDownloader(context);
            this.mDownloader = tXCFLVDownloader;
            tXCFLVDownloader.setFlvSessionKey(this.mFlvSessionKey);
        } else if (i2 == 1 || i2 == 4) {
            this.mDownloader = new TXCRTMPDownloader(context);
        }
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            tXIStreamDownloader.setListener(this);
            this.mDownloader.setNotifyListener(this);
            this.mDownloader.setRestartListener(this);
            this.mDownloader.setMessageNotifyListener(this);
        }
        this.mDownloadFormat = i2;
        this.mAccUrlFetcher = new h(context);
        this.mApplicationContext = context;
        if (context != null) {
            this.mHandler = new Handler(this.mApplicationContext.getMainLooper());
        }
    }

    private DownloadStats getDownloadStats() {
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            return tXIStreamDownloader.getDownloadStats();
        }
        return null;
    }

    private a getRealTimeStreamInfo() {
        a aVar;
        h hVar = this.mAccUrlFetcher;
        if (hVar == null || TextUtils.isEmpty(hVar.a())) {
            aVar = null;
        } else {
            aVar = new a();
            aVar.f19471b = this.mAccUrlFetcher.a();
            aVar.f19472c = this.mAccUrlFetcher.b();
            aVar.f19473d = this.mAccUrlFetcher.c();
            aVar.f19474e = this.mAccUrlFetcher.d();
        }
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null && aVar != null) {
            aVar.f19470a = tXIStreamDownloader.getCurrentStreamUrl();
            aVar.f19475f = this.mDownloader.isQuicChannel();
        }
        return aVar;
    }

    private Long getSpeed(long j2, long j3, long j4) {
        if (j2 <= j3) {
            j3 -= j2;
        }
        return Long.valueOf(j4 > 0 ? ((j3 * 8) * 1000) / (j4 * 1024) : 0L);
    }

    private native String nativeGetRTMPProxyUserId();

    private void playStreamWithRawUrl(String str, boolean z2) {
        if (this.mDownloader != null) {
            if (str != null && ((str.startsWith(DefaultWebClient.HTTP_SCHEME) || str.startsWith(DefaultWebClient.HTTPS_SCHEME)) && str.contains(".flv"))) {
                TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
                int i2 = tXIStreamDownloader.connectRetryLimit;
                int i3 = tXIStreamDownloader.connectRetryInterval;
                this.mDownloader = null;
                TXCFLVDownloader tXCFLVDownloader = new TXCFLVDownloader(this.mApplicationContext);
                this.mDownloader = tXCFLVDownloader;
                tXCFLVDownloader.setFlvSessionKey(this.mFlvSessionKey);
                this.mDownloader.setListener(this);
                this.mDownloader.setNotifyListener(this);
                this.mDownloader.setRestartListener(this);
                TXIStreamDownloader tXIStreamDownloader2 = this.mDownloader;
                tXIStreamDownloader2.connectRetryLimit = i2;
                tXIStreamDownloader2.connectRetryInterval = i3;
                tXIStreamDownloader2.setHeaders(this.mHeaders);
                this.mDownloader.setUserID(getID());
                this.mDownloader.setMessageNotifyListener(this);
                this.mDownloader.setPayloadType(this.mPayLoadType);
            }
            setStatusValue(R2.dimen.dp_505, 1L);
            Vector<e> vector = new Vector<>();
            vector.add(new e(str, false));
            this.mDownloader.setOriginUrl(str);
            this.mDownloader.startDownload(vector, false, false, z2, z2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportNetStatus() {
        reportNetStatusInternal();
        this.mHandler.postDelayed(this.mReportNetStatusRunnalbe, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    private void reportNetStatusInternal() {
        long jLongValue;
        long jLongValue2;
        long timeTick = TXCTimeUtil.getTimeTick();
        long j2 = timeTick - this.mLastTimeStamp;
        DownloadStats downloadStats = getDownloadStats();
        a realTimeStreamInfo = getRealTimeStreamInfo();
        if (downloadStats != null) {
            DownloadStats downloadStats2 = this.mLastDownloadStats;
            if (downloadStats2 != null) {
                jLongValue2 = getSpeed(downloadStats2.afterParseVideoBytes, downloadStats.afterParseVideoBytes, j2).longValue();
                jLongValue = getSpeed(this.mLastDownloadStats.afterParseAudioBytes, downloadStats.afterParseAudioBytes, j2).longValue();
            } else {
                jLongValue = 0;
                jLongValue2 = 0;
            }
            if (jLongValue2 > 0 || jLongValue > 0) {
                this.mDownloader.connectRetryTimes = 0;
            }
            setStatusValue(R2.dimen.dp_498, Long.valueOf(jLongValue2));
            setStatusValue(R2.dimen.dp_499, Long.valueOf(jLongValue));
            setStatusValue(R2.dimen.dp_4_, Long.valueOf(downloadStats.firstVideoTS));
            setStatusValue(R2.dimen.dp_4_5, Long.valueOf(downloadStats.firstAudioTS));
            setStatusValue(R2.dimen.dp_511, Long.valueOf(downloadStats.videoGop));
            if (realTimeStreamInfo != null) {
                setStatusValue(R2.dimen.dp_5, Long.valueOf(realTimeStreamInfo.f19473d));
                setStatusValue(R2.dimen.dp_50, realTimeStreamInfo.f19474e);
                setStatusValue(R2.dimen.dp_504, Long.valueOf(realTimeStreamInfo.f19475f ? 2L : 1L));
                setStatusValue(R2.dimen.dp_509, realTimeStreamInfo.f19470a);
                setStatusValue(R2.dimen.dp_50_, realTimeStreamInfo.f19471b);
                setStatusValue(R2.dimen.dp_51, realTimeStreamInfo.f19472c);
            } else {
                setStatusValue(R2.dimen.dp_5, Long.valueOf(downloadStats.errorCode));
                setStatusValue(R2.dimen.dp_50, downloadStats.errorInfo);
                setStatusValue(R2.dimen.dp_504, 1L);
            }
            setStatusValue(R2.dimen.dp_500, Long.valueOf(downloadStats.startTS));
            setStatusValue(R2.dimen.dp_501, Long.valueOf(downloadStats.dnsTS));
            setStatusValue(R2.dimen.dp_502, Long.valueOf(downloadStats.connTS));
            setStatusValue(R2.dimen.dp_503, String.valueOf(downloadStats.serverIP));
        }
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            int connectCountQuic = tXIStreamDownloader.getConnectCountQuic();
            int connectCountTcp = this.mDownloader.getConnectCountTcp();
            setStatusValue(R2.dimen.dp_507, Long.valueOf(connectCountQuic + 1));
            setStatusValue(R2.dimen.dp_508, Long.valueOf(connectCountTcp + 1));
            setStatusValue(R2.dimen.dp_510, this.mDownloader.getRealStreamUrl());
            setStatusValue(R2.dimen.dp_512, String.valueOf(this.mDownloader.getFlvSessionKey()));
        }
        this.mLastTimeStamp = timeTick;
        this.mLastDownloadStats = downloadStats;
    }

    private void tryResetRetryCount() {
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            tXIStreamDownloader.connectRetryTimes = 0;
        }
    }

    public String getRTMPProxyUserId() {
        return nativeGetRTMPProxyUserId();
    }

    @Override // com.tencent.liteav.network.f
    public void onMetaDataMessage(HashMap<String, String> map) {
        com.tencent.liteav.basic.b.b bVar = this.mNotifyListener;
        if (bVar != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("EVT_GET_METADATA", map);
            bVar.onNotifyEvent(2028, bundle);
        }
    }

    @Override // com.tencent.liteav.basic.b.b
    public void onNotifyEvent(int i2, Bundle bundle) {
        int i3;
        com.tencent.liteav.basic.b.b bVar = this.mNotifyListener;
        if (bVar != null) {
            Bundle bundle2 = new Bundle();
            if (i2 == -2308) {
                bundle2.putString(TXLiveConstants.EVT_DESCRIPTION, "The server rejected the connection request");
            } else if (i2 == 2012) {
                byte[] byteArray = bundle.getByteArray(TXLiveConstants.EVT_GET_MSG);
                if (byteArray != null && byteArray.length > 0) {
                    bundle2.putByteArray(TXLiveConstants.EVT_GET_MSG, byteArray);
                }
            } else if (i2 == 2028 || i2 == 2031) {
                bundle2 = bundle;
            } else if (i2 == 2103) {
                bundle2.putString(TXLiveConstants.EVT_DESCRIPTION, "retry connecting stream server");
            } else if (i2 == 3010) {
                bundle2.putString(TXLiveConstants.EVT_DESCRIPTION, "No video at this stream address");
            } else if (i2 == -2302) {
                bundle2.putString(TXLiveConstants.EVT_DESCRIPTION, "Failed to get accelerated pull address");
            } else if (i2 == -2301) {
                bundle2.putString(TXLiveConstants.EVT_DESCRIPTION, "failed to connect server for several times, abort connection");
            } else if (i2 == 2001) {
                bundle2.putString(TXLiveConstants.EVT_DESCRIPTION, "connection SUCCESS");
            } else if (i2 == 2002) {
                bundle2.putString(TXLiveConstants.EVT_DESCRIPTION, "begin receiving stream");
            } else if (i2 == 3002) {
                bundle2.putString(TXLiveConstants.EVT_DESCRIPTION, "Failed to connect server");
            } else if (i2 == 3003) {
                bundle2.putString(TXLiveConstants.EVT_DESCRIPTION, "RTMP handshake failed");
            } else if (i2 == 3006) {
                bundle2.putString(TXLiveConstants.EVT_DESCRIPTION, "Write data error");
            } else if (i2 != 3007) {
                bundle2.putString(TXLiveConstants.EVT_DESCRIPTION, "UNKNOWN event = " + i2);
            } else {
                bundle2.putString(TXLiveConstants.EVT_DESCRIPTION, "Read data error");
            }
            String string = bundle != null ? bundle.getString(TXLiveConstants.EVT_DESCRIPTION, "") : "";
            if (string != null && !string.isEmpty()) {
                bundle2.putString(TXLiveConstants.EVT_DESCRIPTION, string);
            }
            bundle2.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            bundle2.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
            bVar.onNotifyEvent(i2, bundle2);
            if (i2 == 3001 || i2 == 3002 || i2 == 3003 || i2 == 3004 || i2 == 3005 || i2 == 3006 || i2 == 3007 || i2 == 3008 || i2 == 3009 || i2 == 3010 || i2 == 2101 || i2 == 2102 || i2 == 2109 || i2 == 2110 || i2 == -2301 || i2 == -2304 || i2 == -2308 || i2 == -2309) {
                setStatusValue(R2.dimen.dp_5, Integer.valueOf(i2));
                setStatusValue(R2.dimen.dp_50, bundle2.getString(TXLiveConstants.EVT_DESCRIPTION));
            }
            i3 = 2001;
        } else {
            i3 = 2001;
        }
        if (i2 == i3) {
            reportNetStatusInternal();
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader.a
    public void onOldStreamStop() {
        synchronized (this.mListenerLock) {
            d dVar = this.mStreamSwitcher;
            if (dVar != null) {
                dVar.b();
            }
        }
    }

    @Override // com.tencent.liteav.network.g
    public void onPullAudio(com.tencent.liteav.basic.structs.a aVar) {
        tryResetRetryCount();
        synchronized (this.mListenerLock) {
            g gVar = this.mListener;
            if (gVar != null) {
                gVar.onPullAudio(aVar);
            }
            TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
            if (tXIStreamDownloader != null) {
                tXIStreamDownloader.PushAudioFrame(aVar.f18649f, aVar.f18651h, aVar.f18648e, aVar.f18650g);
            }
        }
    }

    @Override // com.tencent.liteav.network.g
    public void onPullNAL(TXSNALPacket tXSNALPacket) {
        tryResetRetryCount();
        if (!this.mRecvFirstNal) {
            reportNetStatusInternal();
            this.mRecvFirstNal = true;
        }
        synchronized (this.mListenerLock) {
            long j2 = tXSNALPacket.pts;
            this.mCurrentNalTs = j2;
            if (tXSNALPacket.nalType == 0) {
                this.mLastIFramelTs = j2;
            }
            g gVar = this.mListener;
            if (gVar != null) {
                gVar.onPullNAL(tXSNALPacket);
            }
            TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
            if (tXIStreamDownloader != null) {
                tXIStreamDownloader.PushVideoFrame(tXSNALPacket.nalData, tXSNALPacket.nalType, tXSNALPacket.dts, tXSNALPacket.pts, tXSNALPacket.codecId);
            }
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader.a
    public void onRestartDownloader() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.network.TXCStreamDownloader.1
                @Override // java.lang.Runnable
                public void run() {
                    TXCStreamDownloader.this.stop();
                    TXCStreamDownloader tXCStreamDownloader = TXCStreamDownloader.this;
                    tXCStreamDownloader.start(tXCStreamDownloader.mOriginPlayUrl, TXCStreamDownloader.this.mEnableNearestIP, TXCStreamDownloader.this.mChannelType, TXCStreamDownloader.this.mEnableMessage, TXCStreamDownloader.this.mEnableMetaData);
                }
            });
        }
    }

    @Override // com.tencent.liteav.network.f
    public void onSEIMessage(byte[] bArr) {
        com.tencent.liteav.basic.b.b bVar = this.mNotifyListener;
        if (bVar != null) {
            Bundle bundle = new Bundle();
            bundle.putByteArray(TXLiveConstants.EVT_GET_MSG, bArr);
            bVar.onNotifyEvent(2012, bundle);
        }
    }

    @Override // com.tencent.liteav.network.d.a
    public void onSwitchFinish(TXIStreamDownloader tXIStreamDownloader, boolean z2) {
        synchronized (this.mListenerLock) {
            int iCurrentTimeMillis = (int) (System.currentTimeMillis() - this.mSwitchStartTime);
            this.mSwitchStartTime = 0L;
            Bundle bundle = new Bundle();
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            bundle.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
            if (z2) {
                this.mDownloader = tXIStreamDownloader;
                tXIStreamDownloader.setListener(this);
                this.mDownloader.setNotifyListener(this);
                this.mDownloader.setRestartListener(this);
                bundle.putInt("EVT_ID", 2015);
                bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, "Switched resolution successfully");
                com.tencent.liteav.basic.b.b bVar = this.mNotifyListener;
                if (bVar != null) {
                    bVar.onNotifyEvent(2015, bundle);
                }
                TXCDRApi.txReportDAU(this.mApplicationContext, com.tencent.liteav.basic.datareport.a.bx, iCurrentTimeMillis, "");
            } else {
                bundle.putInt("EVT_ID", -2307);
                bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, "Failed to switch resolution");
                com.tencent.liteav.basic.b.b bVar2 = this.mNotifyListener;
                if (bVar2 != null) {
                    bVar2.onNotifyEvent(-2307, bundle);
                }
                TXCDRApi.txReportDAU(this.mApplicationContext, com.tencent.liteav.basic.datareport.a.by);
            }
            this.mStreamSwitcher = null;
        }
    }

    public void requestKeyFrame(String str) {
        TXIStreamDownloader tXIStreamDownloader;
        if (!this.mDownloaderRunning || str == null || !str.startsWith("room") || (tXIStreamDownloader = this.mDownloader) == null) {
            return;
        }
        tXIStreamDownloader.requestKeyFrame(str);
    }

    public void setFlvSessionKey(String str) {
        this.mFlvSessionKey = str;
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            tXIStreamDownloader.setFlvSessionKey(str);
        }
    }

    public void setHeaders(Map<String, String> map) {
        this.mHeaders = map;
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            tXIStreamDownloader.setHeaders(map);
        }
    }

    @Override // com.tencent.liteav.basic.module.a
    public void setID(String str) {
        super.setID(str);
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            tXIStreamDownloader.setUserID(str);
        }
    }

    public void setListener(g gVar) {
        synchronized (this.mListenerLock) {
            this.mListener = gVar;
        }
    }

    public void setNotifyListener(com.tencent.liteav.basic.b.b bVar) {
        synchronized (this.mListenerLock) {
            this.mNotifyListener = bVar;
        }
    }

    public void setPayloadType(int i2) {
        this.mPayLoadType = i2;
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            tXIStreamDownloader.setPayloadType(i2);
        }
    }

    public void setRetryInterval(int i2) {
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            tXIStreamDownloader.connectRetryInterval = i2;
        }
    }

    public void setRetryTimes(int i2) {
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            tXIStreamDownloader.connectRetryLimit = i2;
        }
    }

    public int start(final String str, boolean z2, int i2, final boolean z3, final boolean z4) {
        TXCLog.i(TAG, "start url:" + str + ",enableNearestIP:" + z2 + ",channeyType:" + i2 + ",enableMessage:" + z3 + ",enableMetaData:" + z4);
        this.mDownloaderRunning = true;
        this.mRecvFirstNal = false;
        this.mOriginPlayUrl = str;
        this.mEnableNearestIP = z2;
        this.mChannelType = i2;
        this.mEnableMessage = z3;
        this.mEnableMetaData = z4;
        setStatusValue(R2.dimen.dp_506, 0L);
        setStatusValue(R2.dimen.dp_507, 0L);
        setStatusValue(R2.dimen.dp_508, 0L);
        if (str.startsWith("room")) {
            setStatusValue(R2.dimen.dp_506, 1L);
            setStatusValue(R2.dimen.dp_505, 2L);
            setStatusValue(R2.dimen.dp_509, str);
            if (this.mDownloader != null) {
                Vector<e> vector = new Vector<>();
                vector.add(new e(str, true));
                this.mDownloader.setOriginUrl(str);
                this.mDownloader.setUserID(getID());
                this.mDownloader.startDownload(vector, false, false, z3, z4);
            }
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.postDelayed(this.mReportNetStatusRunnalbe, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            }
            return 0;
        }
        if (!z2 || this.mDownloadFormat != 4) {
            if (this.mDownloader != null) {
                setStatusValue(R2.dimen.dp_505, 1L);
                Vector<e> vector2 = new Vector<>();
                vector2.add(new e(str, false));
                this.mDownloader.setOriginUrl(str);
                this.mDownloader.startDownload(vector2, this.mDownloadFormat == 4, z2, z3, z4);
                Handler handler2 = this.mHandler;
                if (handler2 != null) {
                    handler2.postDelayed(this.mReportNetStatusRunnalbe, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            }
            return 0;
        }
        int iA = this.mAccUrlFetcher.a(str, i2, new h.a() { // from class: com.tencent.liteav.network.TXCStreamDownloader.2
            @Override // com.tencent.liteav.network.h.a
            public void a(int i3, String str2, Vector<e> vector3) {
                String str3;
                if (i3 != 0 || vector3 == null || vector3.isEmpty()) {
                    TXCStreamDownloader.this.onNotifyEvent(-2302, null);
                    TXCDRApi.txReportDAU(TXCStreamDownloader.this.mApplicationContext, com.tencent.liteav.basic.datareport.a.au, i3, str2);
                    TXCLog.e(TXCStreamDownloader.TAG, "getAccelerateStreamPlayUrl failed, play stream with raw url");
                    if (TXCStreamDownloader.this.mDownloaderRunning) {
                        TXCStreamDownloader.this.onNotifyEvent(-2301, null);
                        return;
                    }
                    return;
                }
                if (!TXCStreamDownloader.this.mDownloaderRunning) {
                    TXCDRApi.txReportDAU(TXCStreamDownloader.this.mApplicationContext, com.tencent.liteav.basic.datareport.a.au, -4, "livePlayer have been stopped");
                    return;
                }
                if (TXCStreamDownloader.this.mDownloader != null) {
                    Iterator<e> it = vector3.iterator();
                    int i4 = 0;
                    while (it.hasNext()) {
                        e next = it.next();
                        if (next != null && next.f19560b && (str3 = next.f19559a) != null && str3.length() > 0) {
                            i4++;
                        }
                    }
                    TXCStreamDownloader.this.setStatusValue(R2.dimen.dp_506, Long.valueOf(i4));
                    TXCStreamDownloader.this.setStatusValue(R2.dimen.dp_505, 2L);
                    TXCStreamDownloader.this.mDownloader.setOriginUrl(str);
                    TXCStreamDownloader.this.mDownloader.startDownload(vector3, true, true, z3, z4);
                }
                if (TXCStreamDownloader.this.mHandler != null) {
                    TXCStreamDownloader.this.mHandler.postDelayed(TXCStreamDownloader.this.mReportNetStatusRunnalbe, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
                TXCDRApi.txReportDAU(TXCStreamDownloader.this.mApplicationContext, com.tencent.liteav.basic.datareport.a.au, i3, TXCStreamDownloader.this.mAccUrlFetcher.b());
            }
        });
        if (iA != 0) {
            if (iA == -1) {
                TXCDRApi.txReportDAU(this.mApplicationContext, com.tencent.liteav.basic.datareport.a.au, iA, "invalid playUrl");
            } else if (iA == -2) {
                TXCDRApi.txReportDAU(this.mApplicationContext, com.tencent.liteav.basic.datareport.a.au, iA, "invalid streamID");
            } else if (iA == -3) {
                TXCDRApi.txReportDAU(this.mApplicationContext, com.tencent.liteav.basic.datareport.a.au, iA, "invalid signature");
            }
            TXCLog.e(TAG, "getAccelerateStreamPlayUrl failed, result = " + iA + ", play stream with raw url");
            onNotifyEvent(-2302, null);
            onNotifyEvent(-2301, null);
        }
        return 0;
    }

    public void stop() {
        this.mDownloaderRunning = false;
        this.mRecvFirstNal = false;
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            tXIStreamDownloader.stopDownload();
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mReportNetStatusRunnalbe);
        }
        synchronized (this.mListenerLock) {
            d dVar = this.mStreamSwitcher;
            if (dVar != null) {
                dVar.a((g) null);
                this.mStreamSwitcher.a();
                this.mStreamSwitcher = null;
            }
        }
    }

    public boolean switchStream(String str) {
        TXIStreamDownloader tXIStreamDownloader;
        synchronized (this.mListenerLock) {
            if (this.mStreamSwitcher == null && (tXIStreamDownloader = this.mDownloader) != null && (tXIStreamDownloader instanceof TXCFLVDownloader)) {
                TXCFLVDownloader tXCFLVDownloader = new TXCFLVDownloader(this.mApplicationContext, (TXCFLVDownloader) tXIStreamDownloader);
                TXIStreamDownloader tXIStreamDownloader2 = this.mDownloader;
                tXCFLVDownloader.connectRetryLimit = tXIStreamDownloader2.connectRetryLimit;
                tXCFLVDownloader.connectRetryInterval = tXIStreamDownloader2.connectRetryInterval;
                tXCFLVDownloader.setHeaders(this.mHeaders);
                tXCFLVDownloader.setUserID(getID());
                tXCFLVDownloader.setFlvSessionKey(this.mFlvSessionKey);
                tXCFLVDownloader.setMessageNotifyListener(this);
                tXCFLVDownloader.setPayloadType(this.mPayLoadType);
                d dVar = new d(this);
                this.mStreamSwitcher = dVar;
                dVar.a(this);
                this.mStreamSwitcher.a(this.mDownloader, tXCFLVDownloader, this.mCurrentNalTs, this.mLastIFramelTs, str);
                this.mSwitchStartTime = System.currentTimeMillis();
                return true;
            }
            TXCLog.w(TAG, "stream_switch stream is changing ignore this change");
            return false;
        }
    }
}
