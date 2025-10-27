package com.tencent.rtmp.sharp.jni;

import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes6.dex */
public class TraeAudioSessionHost {
    private ArrayList<SessionInfo> _sessionInfoList = new ArrayList<>();
    private ReentrantLock mLock = new ReentrantLock();

    public class SessionInfo {
        public TraeAudioSession _traeAs;
        public long sessionId;

        public SessionInfo() {
        }
    }

    public void add(TraeAudioSession traeAudioSession, long j2, Context context) {
        if (find(j2) != null) {
            return;
        }
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.sessionId = j2;
        sessionInfo._traeAs = traeAudioSession;
        this.mLock.lock();
        this._sessionInfoList.add(sessionInfo);
        this.mLock.unlock();
    }

    public SessionInfo find(long j2) {
        SessionInfo sessionInfo;
        this.mLock.lock();
        int i2 = 0;
        while (true) {
            if (i2 >= this._sessionInfoList.size()) {
                sessionInfo = null;
                break;
            }
            sessionInfo = this._sessionInfoList.get(i2);
            if (sessionInfo.sessionId == j2) {
                break;
            }
            i2++;
        }
        this.mLock.unlock();
        return sessionInfo;
    }

    public void remove(long j2) {
        this.mLock.lock();
        int i2 = 0;
        while (true) {
            if (i2 >= this._sessionInfoList.size()) {
                break;
            }
            if (this._sessionInfoList.get(i2).sessionId == j2) {
                this._sessionInfoList.remove(i2);
                break;
            }
            i2++;
        }
        this.mLock.unlock();
    }

    public void sendToAudioSessionMessage(Intent intent) {
        this.mLock.lock();
        for (int i2 = 0; i2 < this._sessionInfoList.size(); i2++) {
            this._sessionInfoList.get(i2)._traeAs.onReceiveCallback(intent);
        }
        this.mLock.unlock();
    }
}
