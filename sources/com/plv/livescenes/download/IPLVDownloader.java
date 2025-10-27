package com.plv.livescenes.download;

import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import com.plv.livescenes.playback.video.PLVPlaybackListType;
import java.io.File;

/* loaded from: classes4.dex */
public interface IPLVDownloader extends com.plv.foundationsdk.download.IPLVDownloader {

    public static class Builder {
        String channelId;
        File downloadDir;
        PLVPlaybackListType playbackListType;
        String videoPoolId;
        String viewerId;

        public Builder(@NonNull String str, @NonNull String str2) {
            this.videoPoolId = str;
            this.channelId = str2;
        }

        @NonNull
        public String createDownloadKey() {
            return this.viewerId + StrPool.UNDERLINE + this.channelId + StrPool.UNDERLINE + this.videoPoolId + StrPool.UNDERLINE;
        }

        public Builder downloadDir(@NonNull File file) {
            this.downloadDir = file;
            return this;
        }

        public Builder setPlaybackListType(PLVPlaybackListType pLVPlaybackListType) {
            this.playbackListType = pLVPlaybackListType;
            return this;
        }

        public Builder viewerId(String str) {
            this.viewerId = str;
            return this;
        }
    }

    String getKey();

    int getSpeedCallbackInterval();

    void isCallbackProgressWhereExists(boolean z2);

    boolean isCallbackProgressWhereExists();

    @Override // com.plv.foundationsdk.download.IPLVDownloader
    boolean isDownloading();

    void setSpeedCallbackInterval(int i2);
}
