package com.tencent.rtmp.sharp.jni;

import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes6.dex */
public class TraeAudioCodecList {
    private ArrayList<CodecInfo> _sessionInfoList = new ArrayList<>();
    private ReentrantLock mLock = new ReentrantLock();

    public class CodecInfo {
        public byte[] _tempBufdec;
        public AudioDecoder audioDecoder;
        public long sessionId;

        public CodecInfo() {
        }
    }

    public CodecInfo add(long j2) {
        CodecInfo codecInfoFind = find(j2);
        if (codecInfoFind != null) {
            return codecInfoFind;
        }
        CodecInfo codecInfo = new CodecInfo();
        codecInfo.sessionId = j2;
        codecInfo.audioDecoder = new AudioDecoder();
        codecInfo._tempBufdec = new byte[R2.attr.triangleHeight];
        this.mLock.lock();
        this._sessionInfoList.add(codecInfo);
        this.mLock.unlock();
        return find(j2);
    }

    public CodecInfo find(long j2) {
        CodecInfo codecInfo;
        this.mLock.lock();
        int i2 = 0;
        while (true) {
            if (i2 >= this._sessionInfoList.size()) {
                codecInfo = null;
                break;
            }
            codecInfo = this._sessionInfoList.get(i2);
            if (codecInfo.sessionId == j2) {
                break;
            }
            i2++;
        }
        this.mLock.unlock();
        return codecInfo;
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
}
