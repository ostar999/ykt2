package com.tencent.liteav.network;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.caverock.androidsvg.SVGParser;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.network.TXCStreamDownloader;
import com.tencent.liteav.network.TXIStreamDownloader;
import com.tencent.rtmp.TXLiveConstants;
import java.util.Vector;

/* loaded from: classes6.dex */
public class TXCRTMPDownloader extends TXIStreamDownloader {
    private final int MSG_EVENT;
    private final int MSG_RECONNECT;
    private final String TAG;
    private int mConnectCountQuic;
    private int mConnectCountTcp;
    private a mCurrentThread;
    private boolean mEnableNearestIP;
    private Handler mHandler;
    private boolean mHasTcpPlayUrl;
    private boolean mIsPlayRtmpAccStream;
    private int mLastNetworkType;
    private String mPlayUrl;
    private boolean mQuicChannel;
    private Object mRTMPThreadLock;
    private String mServerIp;
    private HandlerThread mThread;
    private Vector<e> mVecPlayUrls;

    public TXCRTMPDownloader(Context context) {
        super(context);
        this.TAG = "network.TXCRTMPDownloader";
        this.MSG_RECONNECT = 101;
        this.MSG_EVENT = 102;
        this.mPlayUrl = "";
        this.mQuicChannel = false;
        this.mServerIp = "";
        this.mCurrentThread = null;
        this.mThread = null;
        this.mHandler = null;
        this.mIsPlayRtmpAccStream = false;
        this.mEnableNearestIP = false;
        this.mConnectCountQuic = 0;
        this.mConnectCountTcp = 0;
        this.mLastNetworkType = 0;
        this.mRTMPThreadLock = new Object();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void internalReconnect(boolean z2) {
        Vector<e> vector;
        if (this.mIsRunning) {
            if (this.mIsPlayRtmpAccStream && this.mLastNetworkType != com.tencent.liteav.basic.util.h.e(this.mApplicationContext)) {
                this.mLastNetworkType = com.tencent.liteav.basic.util.h.e(this.mApplicationContext);
                TXIStreamDownloader.a aVar = this.mRestartListener;
                if (aVar != null) {
                    aVar.onRestartDownloader();
                    return;
                }
                return;
            }
            boolean z3 = this.mQuicChannel;
            if (this.mIsPlayRtmpAccStream) {
                if (!this.mEnableNearestIP) {
                    z2 = false;
                }
                if (z3) {
                    z2 = true;
                }
                if (z2 && (vector = this.mVecPlayUrls) != null && !vector.isEmpty()) {
                    e eVar = this.mVecPlayUrls.get(0);
                    this.mVecPlayUrls.remove(0);
                    this.mPlayUrl = eVar.f19559a;
                    this.mQuicChannel = eVar.f19560b;
                }
            }
            if (z3 && this.mHasTcpPlayUrl) {
                sendNotifyEvent(2103);
                startInternal();
                return;
            }
            int i2 = this.connectRetryTimes;
            if (i2 >= this.connectRetryLimit) {
                TXCLog.e("network.TXCRTMPDownloader", "reconnect all times retried, send failed event ");
                sendNotifyEvent(-2301);
                return;
            }
            this.connectRetryTimes = i2 + 1;
            TXCLog.i("network.TXCRTMPDownloader", "reconnect retry count:" + this.connectRetryTimes + " limit:" + this.connectRetryLimit);
            sendNotifyEvent(2103);
            startInternal();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native TXCStreamDownloader.DownloadStats nativeGetStats(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native long nativeInitRtmpHandler(String str, String str2, String str3, boolean z2, boolean z3, boolean z4);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeRequestKeyFrame(long j2, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeSetPayloadType(long j2, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeStart(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeStop(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeUninitRtmpHandler(long j2);

    private void postReconnectMsg() {
        Message message = new Message();
        message.what = 101;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendMessageDelayed(message, this.connectRetryInterval * 1000);
        }
    }

    private void reconnect(final boolean z2) {
        synchronized (this.mRTMPThreadLock) {
            a aVar = this.mCurrentThread;
            if (aVar != null) {
                aVar.a();
                this.mCurrentThread = null;
            }
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.postDelayed(new Runnable() { // from class: com.tencent.liteav.network.TXCRTMPDownloader.1
                @Override // java.lang.Runnable
                public void run() {
                    TXCRTMPDownloader.this.internalReconnect(z2);
                }
            }, this.connectRetryInterval * 1000);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startInternal() {
        if (this.mQuicChannel) {
            this.mConnectCountQuic++;
        } else {
            this.mConnectCountTcp++;
        }
        synchronized (this.mRTMPThreadLock) {
            a aVar = new a(this.mPlayUrl, this.mQuicChannel);
            this.mCurrentThread = aVar;
            aVar.start();
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public int getConnectCountQuic() {
        return this.mConnectCountQuic;
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public int getConnectCountTcp() {
        return this.mConnectCountTcp;
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public String getCurrentStreamUrl() {
        return this.mPlayUrl;
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public TXCStreamDownloader.DownloadStats getDownloadStats() {
        synchronized (this.mRTMPThreadLock) {
            a aVar = this.mCurrentThread;
            if (aVar == null) {
                return null;
            }
            return aVar.b();
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public boolean isQuicChannel() {
        return this.mQuicChannel;
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void requestKeyFrame(String str) {
        synchronized (this.mRTMPThreadLock) {
            a aVar = this.mCurrentThread;
            if (aVar != null) {
                aVar.a(str);
            }
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void sendNotifyEvent(int i2, String str) {
        if (str.isEmpty()) {
            sendNotifyEvent(i2);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(TXLiveConstants.EVT_DESCRIPTION, str);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
        com.tencent.liteav.basic.b.b bVar = this.mNotifyListener;
        if (bVar != null) {
            bVar.onNotifyEvent(i2, bundle);
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void setPayloadType(int i2) {
        this.mPayloadType = i2;
        synchronized (this.mRTMPThreadLock) {
            a aVar = this.mCurrentThread;
            if (aVar != null) {
                aVar.a(this.mPayloadType);
            }
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void startDownload(Vector<e> vector, boolean z2, boolean z3, boolean z4, boolean z5) {
        if (this.mIsRunning || vector == null || vector.isEmpty()) {
            return;
        }
        this.mEnableMessage = z4;
        this.mEnableMetaData = z5;
        this.mIsPlayRtmpAccStream = z2;
        this.mEnableNearestIP = z3;
        this.mVecPlayUrls = vector;
        this.mHasTcpPlayUrl = false;
        int i2 = 0;
        while (true) {
            if (i2 >= this.mVecPlayUrls.size()) {
                break;
            }
            if (!this.mVecPlayUrls.elementAt(i2).f19560b) {
                this.mHasTcpPlayUrl = true;
                break;
            }
            i2++;
        }
        e eVar = this.mVecPlayUrls.get(0);
        this.mVecPlayUrls.remove(0);
        this.mPlayUrl = eVar.f19559a;
        this.mQuicChannel = eVar.f19560b;
        this.mIsRunning = true;
        StringBuilder sb = new StringBuilder();
        sb.append("start pull with url:");
        sb.append(this.mPlayUrl);
        sb.append(" quic:");
        sb.append(this.mQuicChannel ? "yes" : SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO);
        TXCLog.i("network.TXCRTMPDownloader", sb.toString());
        this.mConnectCountQuic = 0;
        this.mConnectCountTcp = 0;
        this.connectRetryTimes = 0;
        if (this.mThread == null) {
            HandlerThread handlerThread = new HandlerThread("RTMP_PULL");
            this.mThread = handlerThread;
            handlerThread.start();
        }
        this.mHandler = new Handler(this.mThread.getLooper()) { // from class: com.tencent.liteav.network.TXCRTMPDownloader.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 101) {
                    TXCRTMPDownloader.this.startInternal();
                }
            }
        };
        startInternal();
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void stopDownload() {
        if (this.mIsRunning) {
            this.mIsRunning = false;
            this.mVecPlayUrls.removeAllElements();
            this.mVecPlayUrls = null;
            this.mIsPlayRtmpAccStream = false;
            this.mEnableNearestIP = false;
            TXCLog.i("network.TXCRTMPDownloader", "stop pull");
            synchronized (this.mRTMPThreadLock) {
                a aVar = this.mCurrentThread;
                if (aVar != null) {
                    aVar.a();
                    this.mCurrentThread = null;
                }
            }
            HandlerThread handlerThread = this.mThread;
            if (handlerThread != null) {
                handlerThread.quit();
                this.mThread = null;
            }
            if (this.mHandler != null) {
                this.mHandler = null;
            }
        }
    }

    public class a extends Thread {

        /* renamed from: b, reason: collision with root package name */
        private long f19461b;

        /* renamed from: c, reason: collision with root package name */
        private String f19462c;

        /* renamed from: d, reason: collision with root package name */
        private boolean f19463d;

        public a(String str, boolean z2) {
            super("RTMPDownLoad");
            this.f19461b = 0L;
            this.f19462c = str;
            this.f19463d = z2;
        }

        public void a() {
            synchronized (this) {
                long j2 = this.f19461b;
                if (j2 != 0) {
                    TXCRTMPDownloader.this.nativeStop(j2);
                }
            }
        }

        public TXCStreamDownloader.DownloadStats b() {
            TXCStreamDownloader.DownloadStats downloadStatsNativeGetStats;
            synchronized (this) {
                long j2 = this.f19461b;
                downloadStatsNativeGetStats = j2 != 0 ? TXCRTMPDownloader.this.nativeGetStats(j2) : null;
            }
            return downloadStatsNativeGetStats;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            synchronized (this) {
                TXCRTMPDownloader tXCRTMPDownloader = TXCRTMPDownloader.this;
                this.f19461b = tXCRTMPDownloader.nativeInitRtmpHandler(tXCRTMPDownloader.mUserID, tXCRTMPDownloader.mOriginUrl, this.f19462c, this.f19463d, tXCRTMPDownloader.mEnableMessage, tXCRTMPDownloader.mEnableMetaData);
            }
            a(TXCRTMPDownloader.this.mPayloadType);
            TXCRTMPDownloader.this.nativeStart(this.f19461b);
            synchronized (this) {
                TXCRTMPDownloader.this.nativeUninitRtmpHandler(this.f19461b);
                this.f19461b = 0L;
            }
        }

        public void a(String str) {
            synchronized (this) {
                long j2 = this.f19461b;
                if (j2 != 0) {
                    TXCRTMPDownloader.this.nativeRequestKeyFrame(j2, str);
                }
            }
        }

        public void a(int i2) {
            synchronized (this) {
                long j2 = this.f19461b;
                if (j2 != 0) {
                    TXCRTMPDownloader.this.nativeSetPayloadType(j2, i2);
                }
            }
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void sendNotifyEvent(int i2) {
        if (i2 != 0 && i2 != 1) {
            super.sendNotifyEvent(i2);
        } else {
            reconnect(i2 == 1);
        }
    }
}
