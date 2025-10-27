package com.tencent.liteav.trtc.impl;

import com.tencent.trtc.TRTCCloudDef;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class TRTCTranscodingConfigInner extends TRTCCloudDef.TRTCTranscodingConfig {
    public String backgroundURL;
    public String mixExtraInfo;

    public TRTCTranscodingConfigInner() {
        this.backgroundURL = "";
        this.mixExtraInfo = "";
    }

    @Override // com.tencent.trtc.TRTCCloudDef.TRTCTranscodingConfig
    public String toString() {
        return "backgroundURL=" + this.backgroundURL + ", mixExtraInfo=" + this.mixExtraInfo + ", " + super.toString();
    }

    public static class TRTCMixUserInner extends TRTCCloudDef.TRTCMixUser {
        public String streamId;

        public TRTCMixUserInner() {
            this.streamId = "";
        }

        @Override // com.tencent.trtc.TRTCCloudDef.TRTCMixUser
        public String toString() {
            return super.toString() + ", streamId=" + this.streamId;
        }

        public TRTCMixUserInner(String str, int i2, int i3, int i4, int i5, int i6) {
            super(str, i2, i3, i4, i5, i6);
            this.streamId = "";
        }

        public TRTCMixUserInner(TRTCCloudDef.TRTCMixUser tRTCMixUser) {
            super(tRTCMixUser);
            this.streamId = "";
        }
    }

    public TRTCTranscodingConfigInner(TRTCCloudDef.TRTCTranscodingConfig tRTCTranscodingConfig) {
        this.appId = tRTCTranscodingConfig.appId;
        this.bizId = tRTCTranscodingConfig.bizId;
        this.mode = tRTCTranscodingConfig.mode;
        this.videoWidth = tRTCTranscodingConfig.videoWidth;
        this.videoHeight = tRTCTranscodingConfig.videoHeight;
        this.videoBitrate = tRTCTranscodingConfig.videoBitrate;
        this.videoFramerate = tRTCTranscodingConfig.videoFramerate;
        this.videoGOP = tRTCTranscodingConfig.videoGOP;
        this.backgroundColor = tRTCTranscodingConfig.backgroundColor;
        this.backgroundImage = tRTCTranscodingConfig.backgroundImage;
        this.audioSampleRate = tRTCTranscodingConfig.audioSampleRate;
        this.audioBitrate = tRTCTranscodingConfig.audioBitrate;
        this.audioChannels = tRTCTranscodingConfig.audioChannels;
        this.streamId = tRTCTranscodingConfig.streamId;
        this.backgroundURL = "";
        this.mixExtraInfo = "";
        this.mixUsers = new ArrayList<>();
        ArrayList<TRTCCloudDef.TRTCMixUser> arrayList = tRTCTranscodingConfig.mixUsers;
        if (arrayList != null) {
            Iterator<TRTCCloudDef.TRTCMixUser> it = arrayList.iterator();
            while (it.hasNext()) {
                TRTCCloudDef.TRTCMixUser next = it.next();
                if (next != null) {
                    this.mixUsers.add(new TRTCMixUserInner(next));
                }
            }
        }
    }
}
