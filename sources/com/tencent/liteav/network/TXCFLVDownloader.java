package com.tencent.liteav.network;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.network.TXCStreamDownloader;
import com.tencent.liteav.network.TXIStreamDownloader;
import com.tencent.open.apireq.BaseResp;
import com.yikaobang.yixue.R2;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;
import java.util.Vector;
import javax.net.ssl.SSLException;

/* loaded from: classes6.dex */
public class TXCFLVDownloader extends TXIStreamDownloader {
    private final int CONNECT_TIMEOUT;
    private final int ERROR_CONNECT_COMMON;
    private final int ERROR_CONNECT_FILE_NOT_FOUND;
    private final int ERROR_CONNECT_SOCKET_TIME_OUT;
    private final int ERROR_CONNECT_THROWABLE;
    private final int ERROR_READ_STREAM_COMMON;
    private final int ERROR_READ_STREAM_EOF;
    private final int ERROR_READ_STREAM_SOCKET;
    private final int ERROR_READ_STREAM_SOCKET_TIME_OUT;
    private final int ERROR_READ_STREAM_SSL;
    private final int ERROR_READ_STREAM_THROWABLE;
    private final int FLV_HEAD_SIZE;
    private final int MAX_FRAME_SIZE;
    private final int MSG_CONNECT;
    private final int MSG_DISCONNECT;
    private final int MSG_QUIT;
    private final int MSG_RECONNECT;
    private final int MSG_RECV_DATA;
    private final int MSG_RESUME;
    private final int MSG_SEEK;
    private final int READ_STREAM_SIZE;
    public final String TAG;
    private HttpURLConnection mConnection;
    private long mContentLength;
    private long mCurrentNalTs;
    private long mDownloadedSize;
    private long mFLVParser;
    private Handler mFlvHandler;
    private HandlerThread mFlvThread;
    private boolean mHandleDataInJava;
    private boolean mHasReceivedFirstAudio;
    private boolean mHasReceivedFirstVideo;
    private InputStream mInputStream;
    private long mLastIFramelTs;
    private byte[] mPacketBytes;
    private String mPlayUrl;
    private boolean mRecvData;
    private WeakReference<TXCFLVDownloader> mRefFLVDownloader;
    private TXCStreamDownloader.DownloadStats mStats;
    private boolean mStopJitter;

    public TXCFLVDownloader(Context context) {
        super(context);
        this.TAG = "network.TXCFLVDownloader";
        this.FLV_HEAD_SIZE = 9;
        this.MAX_FRAME_SIZE = 1048576;
        this.MSG_CONNECT = 100;
        this.MSG_RECV_DATA = 101;
        this.MSG_DISCONNECT = 102;
        this.MSG_RECONNECT = 103;
        this.MSG_SEEK = 104;
        this.MSG_RESUME = 105;
        this.MSG_QUIT = 106;
        this.CONNECT_TIMEOUT = 8000;
        this.READ_STREAM_SIZE = R2.attr.ease_border_color;
        this.ERROR_CONNECT_SOCKET_TIME_OUT = BaseResp.CODE_QQ_LOW_VERSION;
        this.ERROR_CONNECT_FILE_NOT_FOUND = -1008;
        this.ERROR_CONNECT_COMMON = -1010;
        this.ERROR_CONNECT_THROWABLE = -1011;
        this.ERROR_READ_STREAM_SOCKET_TIME_OUT = -1012;
        this.ERROR_READ_STREAM_SOCKET = -1013;
        this.ERROR_READ_STREAM_SSL = -1014;
        this.ERROR_READ_STREAM_EOF = -1015;
        this.ERROR_READ_STREAM_COMMON = -1016;
        this.ERROR_READ_STREAM_THROWABLE = -1017;
        this.mFlvThread = null;
        this.mFlvHandler = null;
        this.mInputStream = null;
        this.mConnection = null;
        this.mPacketBytes = null;
        this.mRecvData = false;
        this.mContentLength = 0L;
        this.mDownloadedSize = 0L;
        this.mHandleDataInJava = false;
        this.mFLVParser = 0L;
        this.mCurrentNalTs = 0L;
        this.mLastIFramelTs = 0L;
        this.mStopJitter = true;
        this.mPlayUrl = "";
        this.mHasReceivedFirstVideo = false;
        this.mHasReceivedFirstAudio = false;
        this.mStats = null;
        this.mRefFLVDownloader = null;
        TXCStreamDownloader.DownloadStats downloadStats = new TXCStreamDownloader.DownloadStats();
        this.mStats = downloadStats;
        downloadStats.afterParseAudioBytes = 0L;
        downloadStats.dnsTS = 0L;
        downloadStats.startTS = TXCTimeUtil.getTimeTick();
        TXCLog.i("network.TXCFLVDownloader", "new flv download " + this);
    }

    private void connect() throws Exception {
        String headerField;
        HttpURLConnection httpURLConnection = this.mConnection;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
            this.mConnection = null;
        }
        TXCLog.i("network.TXCFLVDownloader", "[FirstFramePath][Network] TXCFLVDownloader: start network connect. instance:" + hashCode() + " url:" + this.mPlayUrl);
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.mConnection = (HttpURLConnection) new URL(this.mPlayUrl).openConnection();
        this.mStats.dnsTS = TXCTimeUtil.getTimeTick();
        this.mConnection.setConnectTimeout(8000);
        this.mConnection.setReadTimeout(8000);
        this.mConnection.setRequestProperty("Accept-Encoding", "identity");
        this.mConnection.setInstanceFollowRedirects(true);
        Map<String, String> map = this.mHeaders;
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                this.mConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        this.mConnection.connect();
        if (200 == this.mConnection.getResponseCode()) {
            this.mStats.connTS = TXCTimeUtil.getTimeTick();
        } else {
            this.mStats.errorCode = this.mConnection.getResponseCode();
        }
        this.mInputStream = this.mConnection.getInputStream();
        this.mPacketBytes = new byte[R2.attr.ease_border_color];
        this.mRecvData = false;
        this.mContentLength = this.mConnection.getContentLength();
        this.mDownloadedSize = 0L;
        this.mStats.serverIP = InetAddress.getByName(this.mConnection.getURL().getHost()).getHostAddress();
        String str = "connect server success,ServerIp:" + this.mStats.serverIP;
        TXCLog.i("network.TXCFLVDownloader", str);
        TXCLog.i("network.TXCFLVDownloader", "[FirstFramePath][Network] TXCFLVDownloader: connect server success. instance:" + hashCode() + " ip:" + this.mStats.serverIP + " cost:" + (System.currentTimeMillis() - jCurrentTimeMillis) + " rspCode:" + this.mStats.errorCode);
        sendNotifyEvent(2001, str);
        this.mStats.flvSessionKey = this.mConnection.getHeaderField("X-Tlive-SpanId");
        if (TextUtils.isEmpty(this.mFlvSessionKey) || (headerField = this.mConnection.getHeaderField(this.mFlvSessionKey)) == null) {
            return;
        }
        TXCLog.i("network.TXCFLVDownloader", "receive flvSessionKey " + headerField);
        sendNotifyEvent(2031, headerField);
    }

    private void disconnect() throws Exception {
        TXCLog.i("network.TXCFLVDownloader", "[Network]FLVDownloader disconnect.");
        HttpURLConnection httpURLConnection = this.mConnection;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
            this.mConnection = null;
        }
        InputStream inputStream = this.mInputStream;
        if (inputStream != null) {
            inputStream.close();
            this.mInputStream = null;
        }
    }

    private native void nativeCleanData(long j2);

    private native int nativeGetAudioBytes(long j2);

    private native int nativeGetVideoBytes(long j2);

    private native int nativeGetVideoGop(long j2);

    private native long nativeInitFlvHander(String str, int i2, boolean z2, boolean z3);

    private native long nativeInitFlvHanderByRef(long j2);

    private native int nativeParseData(long j2, byte[] bArr, int i2);

    private native void nativeUninitFlvhander(long j2, boolean z2);

    private void onRecvFirstAudioData() {
        if (this.mHasReceivedFirstAudio) {
            return;
        }
        this.mHasReceivedFirstAudio = true;
        this.mStats.firstAudioTS = TXCTimeUtil.getTimeTick();
        TXCLog.i("network.TXCFLVDownloader", "[FirstFramePath][Network][Audio] TXCFlvDownloader: recv first audio frame. instance:" + hashCode());
    }

    private void onRecvFirstVideoData() {
        if (this.mHasReceivedFirstVideo) {
            return;
        }
        this.mHasReceivedFirstVideo = true;
        this.mStats.firstVideoTS = TXCTimeUtil.getTimeTick();
        TXCLog.i("network.TXCFLVDownloader", "[FirstFramePath][Network][Video] TXCFlvDownloader: recv first video frame. instance:" + hashCode());
    }

    private void postConnectMsg() {
        this.mInputStream = null;
        HttpURLConnection httpURLConnection = this.mConnection;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
            this.mConnection = null;
        }
        Message message = new Message();
        message.what = 100;
        message.arg1 = 0;
        Handler handler = this.mFlvHandler;
        if (handler != null) {
            handler.sendMessage(message);
        }
    }

    private void postDisconnectMsg() {
        Handler handler = this.mFlvHandler;
        if (handler != null) {
            handler.sendEmptyMessage(102);
        }
    }

    private void postReconnectMsg() {
        Handler handler = this.mFlvHandler;
        if (handler != null) {
            handler.sendEmptyMessageDelayed(103, this.connectRetryInterval * 1000);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processMsgConnect() {
        long j2;
        try {
            connect();
            if (this.mFLVParser == 0) {
                WeakReference<TXCFLVDownloader> weakReference = this.mRefFLVDownloader;
                if (weakReference != null) {
                    TXCFLVDownloader tXCFLVDownloader = weakReference.get();
                    if (tXCFLVDownloader == null) {
                        j2 = 0;
                        this.mRefFLVDownloader = null;
                    } else if (tXCFLVDownloader.mIsRunning) {
                        TXCLog.i("network.TXCFLVDownloader", "[Network]init flv parser with old downloader:" + tXCFLVDownloader.hashCode());
                        tXCFLVDownloader.mStopJitter = false;
                        j2 = tXCFLVDownloader.mFLVParser;
                        this.mRefFLVDownloader = null;
                    } else {
                        TXCLog.e("network.TXCFLVDownloader", "[Network]old downloader:" + tXCFLVDownloader.hashCode() + " isn't running now. just create new parser.");
                        j2 = 0;
                        this.mRefFLVDownloader = null;
                    }
                } else {
                    j2 = 0;
                }
                if (j2 != 0) {
                    TXCLog.i("network.TXCFLVDownloader", "[Network]init flv parser with reference parse:" + j2);
                    this.mFLVParser = nativeInitFlvHanderByRef(j2);
                } else {
                    TXCLog.i("network.TXCFLVDownloader", "[Network]init flv parser.");
                    this.mFLVParser = nativeInitFlvHander(this.mUserID, 0, this.mEnableMessage, this.mEnableMetaData);
                }
                int i2 = this.mPayloadType;
                if (i2 != 0) {
                    nativeSetPayloadType(this.mFLVParser, i2);
                }
            }
            Handler handler = this.mFlvHandler;
            if (handler != null) {
                handler.sendEmptyMessage(101);
            }
        } catch (FileNotFoundException e2) {
            TXCLog.e("network.TXCFLVDownloader", "[Network]file not found, reconnect");
            this.mStats.errorCode = -1008;
            this.mStats.errorInfo = e2.toString();
            e2.printStackTrace();
            postReconnectMsg();
        } catch (Error e3) {
            TXCLog.e("network.TXCFLVDownloader", "[Network]error, reconnect");
            this.mStats.errorCode = -1011;
            this.mStats.errorInfo = e3.toString();
            e3.printStackTrace();
            postReconnectMsg();
        } catch (SocketTimeoutException e4) {
            TXCLog.e("network.TXCFLVDownloader", "[Network]socket timeout, reconnect");
            this.mStats.errorCode = BaseResp.CODE_QQ_LOW_VERSION;
            this.mStats.errorInfo = e4.toString();
            postReconnectMsg();
        } catch (Exception e5) {
            TXCLog.e("network.TXCFLVDownloader", "[Network]exception, reconnect");
            this.mStats.errorCode = -1010;
            this.mStats.errorInfo = e5.toString();
            e5.printStackTrace();
            postReconnectMsg();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processMsgDisConnect() {
        try {
            disconnect();
        } catch (Exception e2) {
            TXCLog.e("network.TXCFLVDownloader", "disconnect failed.", e2);
        }
        long j2 = this.mFLVParser;
        if (j2 != 0) {
            nativeUninitFlvhander(j2, this.mStopJitter);
            this.mFLVParser = 0L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processMsgReconnect() {
        if (this.mStopJitter) {
            reconnect();
            return;
        }
        TXCLog.i("network.TXCFLVDownloader", "ignore processMsgReconnect when start multi stream switch" + this);
        TXIStreamDownloader.a aVar = this.mRestartListener;
        if (aVar != null) {
            aVar.onOldStreamStop();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processMsgRecvData() throws IOException {
        InputStream inputStream = this.mInputStream;
        if (inputStream != null) {
            try {
                int iNativeParseData = 0;
                int i2 = inputStream.read(this.mPacketBytes, 0, R2.attr.ease_border_color);
                if (i2 > 0) {
                    long j2 = i2;
                    this.mDownloadedSize += j2;
                    if (!this.mRecvData) {
                        this.mRecvData = true;
                        TXCLog.i("network.TXCFLVDownloader", "[FirstFramePath][Network] TXCFLVDownloader: recv first data packet. instance:" + hashCode());
                    }
                    long j3 = this.mFLVParser;
                    if (j3 != 0) {
                        this.mStats.beforeParseVideoBytes += j2;
                        iNativeParseData = nativeParseData(j3, this.mPacketBytes, i2);
                        this.mStats.afterParseVideoBytes = nativeGetVideoBytes(this.mFLVParser);
                        this.mStats.afterParseAudioBytes = nativeGetAudioBytes(this.mFLVParser);
                        this.mStats.videoGop = nativeGetVideoGop(this.mFLVParser);
                    }
                    if (iNativeParseData > 1048576) {
                        TXCLog.e("network.TXCFLVDownloader", "[Network]flv play parse frame: " + iNativeParseData + " > 1048576,start reconnect");
                        postReconnectMsg();
                        return;
                    }
                } else if (i2 < 0) {
                    TXCLog.e("network.TXCFLVDownloader", "[Network]http read: " + i2 + " < 0, start reconnect");
                    postReconnectMsg();
                    return;
                }
                Handler handler = this.mFlvHandler;
                if (handler != null) {
                    handler.sendEmptyMessage(101);
                }
            } catch (EOFException e2) {
                TXCLog.e("network.TXCFLVDownloader", "[Network]eof exception start reconnect");
                TXCStreamDownloader.DownloadStats downloadStats = this.mStats;
                downloadStats.errorCode = -1015;
                downloadStats.errorInfo = e2.toString();
                postReconnectMsg();
            } catch (Error e3) {
                TXCLog.e("network.TXCFLVDownloader", "[Network]error");
                TXCStreamDownloader.DownloadStats downloadStats2 = this.mStats;
                downloadStats2.errorCode = -1017;
                downloadStats2.errorInfo = e3.toString();
                e3.printStackTrace();
                this.mInputStream = null;
                this.mConnection = null;
            } catch (SocketException e4) {
                TXCLog.e("network.TXCFLVDownloader", "[Network]socket exception start reconnect");
                TXCStreamDownloader.DownloadStats downloadStats3 = this.mStats;
                downloadStats3.errorCode = -1013;
                downloadStats3.errorInfo = e4.toString();
                postReconnectMsg();
            } catch (SocketTimeoutException e5) {
                TXCLog.e("network.TXCFLVDownloader", "[Network]socket timeout start reconnect");
                TXCStreamDownloader.DownloadStats downloadStats4 = this.mStats;
                downloadStats4.errorCode = -1012;
                downloadStats4.errorInfo = e5.toString();
                postReconnectMsg();
            } catch (SSLException e6) {
                TXCLog.e("network.TXCFLVDownloader", "[Network]ssl exception start reconnect");
                TXCStreamDownloader.DownloadStats downloadStats5 = this.mStats;
                downloadStats5.errorCode = -1014;
                downloadStats5.errorInfo = e6.toString();
                postReconnectMsg();
            } catch (Exception e7) {
                TXCLog.e("network.TXCFLVDownloader", "[Network]exception");
                TXCStreamDownloader.DownloadStats downloadStats6 = this.mStats;
                downloadStats6.errorCode = -1016;
                downloadStats6.errorInfo = e7.toString();
                e7.printStackTrace();
                this.mInputStream = null;
                this.mConnection = null;
            }
        }
    }

    private void reconnect() {
        processMsgDisConnect();
        String str = "ServerIp:" + this.mStats.serverIP + ",errCode:" + this.mStats.errorCode + ",errInfo:" + this.mStats.errorInfo;
        TXCLog.i("network.TXCFLVDownloader", "reconnect:" + str);
        int i2 = this.connectRetryTimes;
        if (i2 >= this.connectRetryLimit) {
            TXCLog.i("network.TXCFLVDownloader", "[Network] reconnect fail. all times retried. limit:" + this.connectRetryLimit);
            sendNotifyEvent(-2301, str);
            return;
        }
        this.connectRetryTimes = i2 + 1;
        TXCLog.i("network.TXCFLVDownloader", "[Network] start reconnect, times:" + this.connectRetryTimes + " limit:" + this.connectRetryLimit);
        processMsgConnect();
        sendNotifyEvent(2103, str);
    }

    private void startInternal() {
        if (this.mFlvThread == null) {
            HandlerThread handlerThread = new HandlerThread("FlvThread");
            this.mFlvThread = handlerThread;
            handlerThread.start();
            TXCLog.i("network.TXCFLVDownloader", "[Network] flv downloader thread id:" + this.mFlvThread.getId() + " instance:" + hashCode());
        }
        if (this.mFlvHandler == null) {
            this.mFlvHandler = new Handler(this.mFlvThread.getLooper()) { // from class: com.tencent.liteav.network.TXCFLVDownloader.1
                @Override // android.os.Handler
                public void handleMessage(Message message) throws IOException {
                    int i2 = message.what;
                    if (i2 == 106) {
                        try {
                            Looper.myLooper().quit();
                        } catch (Exception unused) {
                            return;
                        }
                    }
                    switch (i2) {
                        case 100:
                            TXCFLVDownloader.this.processMsgConnect();
                            break;
                        case 101:
                            TXCFLVDownloader.this.processMsgRecvData();
                            break;
                        case 102:
                            TXCFLVDownloader.this.processMsgDisConnect();
                            break;
                        case 103:
                            TXCFLVDownloader.this.processMsgReconnect();
                            break;
                    }
                }
            };
        }
        postConnectMsg();
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void PushAudioFrame(byte[] bArr, int i2, long j2, int i3) {
        nativePushAudioFrame(this.mFLVParser, bArr, i2, j2, i3);
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void PushVideoFrame(byte[] bArr, int i2, long j2, long j3, int i3) {
        nativePushVideoFrame(this.mFLVParser, bArr, i2, j2, j3, i3);
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public long getCurrentTS() {
        return this.mCurrentNalTs;
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public TXCStreamDownloader.DownloadStats getDownloadStats() {
        TXCStreamDownloader.DownloadStats downloadStats = new TXCStreamDownloader.DownloadStats();
        TXCStreamDownloader.DownloadStats downloadStats2 = this.mStats;
        downloadStats.afterParseAudioBytes = downloadStats2.afterParseAudioBytes;
        downloadStats.afterParseVideoBytes = downloadStats2.afterParseVideoBytes;
        downloadStats.beforeParseVideoBytes = downloadStats2.beforeParseVideoBytes;
        downloadStats.beforeParseAudioBytes = downloadStats2.beforeParseAudioBytes;
        downloadStats.videoGop = downloadStats2.videoGop;
        downloadStats.startTS = downloadStats2.startTS;
        downloadStats.dnsTS = downloadStats2.dnsTS;
        downloadStats.connTS = downloadStats2.connTS;
        downloadStats.firstAudioTS = downloadStats2.firstAudioTS;
        downloadStats.firstVideoTS = downloadStats2.firstVideoTS;
        downloadStats.serverIP = downloadStats2.serverIP;
        downloadStats.flvSessionKey = downloadStats2.flvSessionKey;
        downloadStats.errorCode = downloadStats2.errorCode;
        downloadStats.errorInfo = downloadStats2.errorInfo;
        return downloadStats;
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public long getLastIFrameTS() {
        return this.mLastIFramelTs;
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public String getRealStreamUrl() {
        HttpURLConnection httpURLConnection = this.mConnection;
        if (httpURLConnection != null) {
            return httpURLConnection.getURL().toString();
        }
        return null;
    }

    public native void nativePushAudioFrame(long j2, byte[] bArr, int i2, long j3, int i3);

    public native void nativePushVideoFrame(long j2, byte[] bArr, int i2, long j3, long j4, int i3);

    public native void nativeSetPayloadType(long j2, int i2);

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void onRecvAudioData(byte[] bArr, int i2, int i3, int i4) {
        if (!this.mHasReceivedFirstAudio) {
            this.mHasReceivedFirstAudio = true;
            this.mStats.firstAudioTS = TXCTimeUtil.getTimeTick();
            TXCLog.i("network.TXCFLVDownloader", "[FirstFramePath][Network][Audio] TXCFlvDownloader: recv first audio frame. instance:" + hashCode());
        }
        this.mStats.afterParseAudioBytes += bArr.length;
        super.onRecvAudioData(bArr, i2, i3, i4);
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void onRecvVideoData(byte[] bArr, int i2, long j2, long j3, int i3) {
        if (!this.mHasReceivedFirstVideo) {
            this.mHasReceivedFirstVideo = true;
            this.mStats.firstVideoTS = TXCTimeUtil.getTimeTick();
            TXCLog.i("network.TXCFLVDownloader", "[FirstFramePath][Network][Video] TXCFlvDownloader: recv first video frame. instance:" + hashCode());
        }
        this.mStats.afterParseVideoBytes += bArr.length;
        super.onRecvVideoData(bArr, i2, j2, j3, i3);
    }

    public void recvData(boolean z2) {
        this.mHandleDataInJava = z2;
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void setPayloadType(int i2) {
        this.mPayloadType = i2;
        long j2 = this.mFLVParser;
        if (j2 != 0) {
            nativeSetPayloadType(j2, i2);
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void startDownload(Vector<e> vector, boolean z2, boolean z3, boolean z4, boolean z5) {
        if (this.mIsRunning || vector == null || vector.isEmpty()) {
            return;
        }
        this.mEnableMessage = z4;
        this.mEnableMetaData = z5;
        this.mIsRunning = true;
        this.mPlayUrl = vector.get(0).f19559a;
        TXCLog.i("network.TXCFLVDownloader", "start pull with url " + this.mPlayUrl);
        startInternal();
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void stopDownload() {
        if (this.mIsRunning) {
            this.mIsRunning = false;
            TXCLog.i("network.TXCFLVDownloader", "stop pull");
            try {
                Handler handler = this.mFlvHandler;
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                    this.mFlvHandler.sendEmptyMessage(102);
                    this.mFlvHandler.sendEmptyMessage(106);
                    this.mFlvHandler = null;
                }
            } catch (Exception e2) {
                TXCLog.e("network.TXCFLVDownloader", "stop download failed.", e2);
            }
        }
    }

    public TXCFLVDownloader(Context context, TXCFLVDownloader tXCFLVDownloader) {
        super(context);
        this.TAG = "network.TXCFLVDownloader";
        this.FLV_HEAD_SIZE = 9;
        this.MAX_FRAME_SIZE = 1048576;
        this.MSG_CONNECT = 100;
        this.MSG_RECV_DATA = 101;
        this.MSG_DISCONNECT = 102;
        this.MSG_RECONNECT = 103;
        this.MSG_SEEK = 104;
        this.MSG_RESUME = 105;
        this.MSG_QUIT = 106;
        this.CONNECT_TIMEOUT = 8000;
        this.READ_STREAM_SIZE = R2.attr.ease_border_color;
        this.ERROR_CONNECT_SOCKET_TIME_OUT = BaseResp.CODE_QQ_LOW_VERSION;
        this.ERROR_CONNECT_FILE_NOT_FOUND = -1008;
        this.ERROR_CONNECT_COMMON = -1010;
        this.ERROR_CONNECT_THROWABLE = -1011;
        this.ERROR_READ_STREAM_SOCKET_TIME_OUT = -1012;
        this.ERROR_READ_STREAM_SOCKET = -1013;
        this.ERROR_READ_STREAM_SSL = -1014;
        this.ERROR_READ_STREAM_EOF = -1015;
        this.ERROR_READ_STREAM_COMMON = -1016;
        this.ERROR_READ_STREAM_THROWABLE = -1017;
        this.mFlvThread = null;
        this.mFlvHandler = null;
        this.mInputStream = null;
        this.mConnection = null;
        this.mPacketBytes = null;
        this.mRecvData = false;
        this.mContentLength = 0L;
        this.mDownloadedSize = 0L;
        this.mHandleDataInJava = false;
        this.mFLVParser = 0L;
        this.mCurrentNalTs = 0L;
        this.mLastIFramelTs = 0L;
        this.mStopJitter = true;
        this.mPlayUrl = "";
        this.mHasReceivedFirstVideo = false;
        this.mHasReceivedFirstAudio = false;
        this.mStats = null;
        this.mRefFLVDownloader = null;
        TXCStreamDownloader.DownloadStats downloadStats = new TXCStreamDownloader.DownloadStats();
        this.mStats = downloadStats;
        downloadStats.afterParseAudioBytes = 0L;
        downloadStats.dnsTS = 0L;
        downloadStats.startTS = TXCTimeUtil.getTimeTick();
        this.mRefFLVDownloader = new WeakReference<>(tXCFLVDownloader);
        TXCLog.i("network.TXCFLVDownloader", "new multi flv download " + this);
    }
}
