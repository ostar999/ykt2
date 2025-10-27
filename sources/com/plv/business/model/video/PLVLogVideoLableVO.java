package com.plv.business.model.video;

import com.plv.business.model.PLVBaseVO;

/* loaded from: classes4.dex */
public class PLVLogVideoLableVO implements PLVBaseVO {
    private String playId;
    private String videoId;

    public PLVLogVideoLableVO(String str, String str2) {
        this.videoId = str;
        this.playId = str2;
    }

    public String getPlayId() {
        return this.playId;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public void setPlayId(String str) {
        this.playId = str;
    }

    public void setVideoId(String str) {
        this.videoId = str;
    }
}
