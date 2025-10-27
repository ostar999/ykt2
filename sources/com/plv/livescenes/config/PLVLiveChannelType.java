package com.plv.livescenes.config;

import android.text.TextUtils;
import com.plv.socket.event.PLVEventConstant;

/* loaded from: classes4.dex */
public enum PLVLiveChannelType {
    PPT("ppt"),
    ALONE("alone"),
    SEMINAR(PLVEventConstant.Seminar.SEMINAR_EVENT);

    private String value;

    public static class UnknownChannelTypeException extends Exception {
        String unknownChannelType;

        public UnknownChannelTypeException(String str) {
            super("未知的频道类型 = " + str);
            this.unknownChannelType = str;
        }

        public String getUnknownChannelType() {
            return this.unknownChannelType;
        }
    }

    PLVLiveChannelType(String str) {
        this.value = str;
    }

    public static PLVLiveChannelType mapFromServerString(String str) throws UnknownChannelTypeException {
        if (TextUtils.isEmpty(str)) {
            throw new UnknownChannelTypeException(str);
        }
        str.hashCode();
        switch (str) {
            case "topclass":
            case "alone":
                return ALONE;
            case "ppt":
                return PPT;
            case "seminar":
                return SEMINAR;
            default:
                throw new UnknownChannelTypeException(str);
        }
    }

    public String getValue() {
        return this.value;
    }

    public boolean isSupportGuest() {
        return this == PPT || this == ALONE;
    }
}
