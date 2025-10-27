package com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.converter;

import androidx.room.TypeConverter;
import com.plv.livescenes.playback.video.PLVPlaybackListType;

/* loaded from: classes3.dex */
public class PLVPlaybackListTypeConverter {
    @TypeConverter
    public PLVPlaybackListType deserialize(String value) {
        try {
            return PLVPlaybackListType.valueOf(value);
        } catch (Exception unused) {
            return PLVPlaybackListType.TEMP_STORE;
        }
    }

    @TypeConverter
    public String serialize(PLVPlaybackListType playbackListType) {
        return playbackListType == null ? "" : playbackListType.name();
    }
}
