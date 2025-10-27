package com.tencent.liteav.network;

import android.content.Context;
import android.os.Bundle;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import com.tencent.liteav.network.TXCStreamDownloader;
import com.tencent.rtmp.TXLiveConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/* loaded from: classes6.dex */
public abstract class TXIStreamDownloader {
    protected Context mApplicationContext;
    protected Map<String, String> mHeaders;
    protected int mPayloadType;
    protected g mListener = null;
    protected com.tencent.liteav.basic.b.b mNotifyListener = null;
    protected a mRestartListener = null;
    protected f mMessageNotifyListener = null;
    protected boolean mIsRunning = false;
    protected String mOriginUrl = "";
    public int connectRetryTimes = 0;
    public int connectRetryLimit = 3;
    public int connectRetryInterval = 3;
    protected boolean mEnableMessage = false;
    protected boolean mEnableMetaData = false;
    protected String mFlvSessionKey = "";
    protected String mUserID = "";

    public interface a {
        void onOldStreamStop();

        void onRestartDownloader();
    }

    public TXIStreamDownloader(Context context) {
        this.mApplicationContext = context;
    }

    public void PushAudioFrame(byte[] bArr, int i2, long j2, int i3) {
    }

    public void PushVideoFrame(byte[] bArr, int i2, long j2, long j3, int i3) {
    }

    public int getConnectCountQuic() {
        return 0;
    }

    public int getConnectCountTcp() {
        return 0;
    }

    public String getCurrentStreamUrl() {
        return null;
    }

    public long getCurrentTS() {
        return 0L;
    }

    public TXCStreamDownloader.DownloadStats getDownloadStats() {
        return null;
    }

    public String getFlvSessionKey() {
        return this.mFlvSessionKey;
    }

    public long getLastIFrameTS() {
        return 0L;
    }

    public String getRealStreamUrl() {
        return null;
    }

    public boolean isQuicChannel() {
        return false;
    }

    public void onRecvAudioData(byte[] bArr, int i2, int i3, int i4) {
        if (this.mListener != null) {
            com.tencent.liteav.basic.structs.a aVar = new com.tencent.liteav.basic.structs.a();
            aVar.f18649f = bArr;
            aVar.f18648e = i2;
            if (i3 == 10) {
                if (i4 == 1) {
                    aVar.f18647d = 2;
                } else {
                    aVar.f18647d = 3;
                }
                if (aVar.f18647d == 2) {
                    aVar.f18646c = 16;
                }
            }
            if (i3 == 2) {
                aVar.f18647d = 5;
            }
            aVar.f18651h = i3;
            aVar.f18650g = i4;
            this.mListener.onPullAudio(aVar);
        }
    }

    public void onRecvMetaData(HashMap<String, String> map) {
        f fVar;
        if (map == null || map.size() <= 0 || (fVar = this.mMessageNotifyListener) == null) {
            return;
        }
        fVar.onMetaDataMessage(map);
    }

    public void onRecvSEIData(byte[] bArr) {
        f fVar;
        if (bArr == null || bArr.length <= 0 || (fVar = this.mMessageNotifyListener) == null) {
            return;
        }
        fVar.onSEIMessage(bArr);
    }

    public void onRecvVideoData(byte[] bArr, int i2, long j2, long j3, int i3) {
        if (this.mListener != null) {
            TXSNALPacket tXSNALPacket = new TXSNALPacket();
            tXSNALPacket.nalData = bArr;
            tXSNALPacket.nalType = i2;
            tXSNALPacket.dts = j2;
            tXSNALPacket.pts = j3;
            tXSNALPacket.codecId = i3;
            this.mListener.onPullNAL(tXSNALPacket);
        }
    }

    public void requestKeyFrame(String str) {
    }

    public void sendNotifyEvent(int i2) {
        com.tencent.liteav.basic.b.b bVar = this.mNotifyListener;
        if (bVar != null) {
            bVar.onNotifyEvent(i2, null);
        }
    }

    public void setFlvSessionKey(String str) {
        this.mFlvSessionKey = str;
    }

    public void setHeaders(Map<String, String> map) {
        this.mHeaders = map;
    }

    public void setListener(g gVar) {
        this.mListener = gVar;
    }

    public void setMessageNotifyListener(f fVar) {
        this.mMessageNotifyListener = fVar;
    }

    public void setNotifyListener(com.tencent.liteav.basic.b.b bVar) {
        this.mNotifyListener = bVar;
    }

    public void setOriginUrl(String str) {
        this.mOriginUrl = str;
    }

    public void setPayloadType(int i2) {
        this.mPayloadType = i2;
    }

    public void setRestartListener(a aVar) {
        this.mRestartListener = aVar;
    }

    public void setUserID(String str) {
        this.mUserID = str;
    }

    public abstract void startDownload(Vector<e> vector, boolean z2, boolean z3, boolean z4, boolean z5);

    public abstract void stopDownload();

    public void sendNotifyEvent(int i2, String str) {
        if (this.mNotifyListener != null) {
            Bundle bundle = new Bundle();
            bundle.putString(TXLiveConstants.EVT_DESCRIPTION, str);
            this.mNotifyListener.onNotifyEvent(i2, bundle);
        }
    }
}
