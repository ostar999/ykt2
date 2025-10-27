package com.easefun.polyv.livecommon.module.modules.player.playback.model.enums;

import androidx.room.TypeConverter;

/* loaded from: classes3.dex */
public enum PLVPlaybackCacheDownloadStatusEnum {
    NOT_IN_DOWNLOAD_LIST("未下载"),
    WAITING("等待中"),
    PAUSING("已暂停"),
    DOWNLOADING("下载中"),
    DOWNLOADED("已下载"),
    DOWNLOAD_FAIL("下载失败");

    private final String statusName;

    public static class Converter {
        @TypeConverter
        public PLVPlaybackCacheDownloadStatusEnum deserialize(String value) {
            try {
                return PLVPlaybackCacheDownloadStatusEnum.valueOf(value);
            } catch (Exception unused) {
                return PLVPlaybackCacheDownloadStatusEnum.NOT_IN_DOWNLOAD_LIST;
            }
        }

        @TypeConverter
        public String serialize(PLVPlaybackCacheDownloadStatusEnum statusEnum) {
            return statusEnum == null ? "" : statusEnum.name();
        }
    }

    PLVPlaybackCacheDownloadStatusEnum(final String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return this.statusName;
    }
}
