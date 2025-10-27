package com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.converter;

import androidx.room.TypeConverter;
import com.plv.livescenes.config.PLVLiveChannelType;

/* loaded from: classes3.dex */
public class PLVLiveChannelTypeConverter {
    @TypeConverter
    public PLVLiveChannelType deserialize(String value) {
        try {
            return PLVLiveChannelType.valueOf(value);
        } catch (Exception unused) {
            return PLVLiveChannelType.ALONE;
        }
    }

    @TypeConverter
    public String serialize(PLVLiveChannelType liveChannelType) {
        return liveChannelType == null ? "" : liveChannelType.name();
    }
}
