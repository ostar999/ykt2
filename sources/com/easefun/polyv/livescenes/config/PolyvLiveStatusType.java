package com.easefun.polyv.livescenes.config;

import android.text.TextUtils;

@Deprecated
/* loaded from: classes3.dex */
public enum PolyvLiveStatusType {
    LIVE,
    STOP,
    END;

    public static class UnknownLiveStatusTypeException extends Exception {
        private String unknownLiveStatusType;

        public UnknownLiveStatusTypeException(String str) {
            super(str);
            this.unknownLiveStatusType = str;
        }

        public String getUnknownLiveStatusType() {
            return this.unknownLiveStatusType;
        }
    }

    public static PolyvLiveStatusType mapFromServerString(String str) throws UnknownLiveStatusTypeException {
        if (TextUtils.isEmpty(str)) {
            throw new UnknownLiveStatusTypeException(str);
        }
        str.hashCode();
        switch (str) {
            case "end":
                return END;
            case "live":
                return LIVE;
            case "stop":
                return STOP;
            default:
                throw new UnknownLiveStatusTypeException(str);
        }
    }
}
