package com.easefun.polyv.livescenes.config;

import android.text.TextUtils;
import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.socket.event.PLVEventConstant;

@Deprecated
/* loaded from: classes3.dex */
public enum PolyvLiveChannelType {
    PPT("ppt"),
    ALONE("alone"),
    SEMINAR(PLVEventConstant.Seminar.SEMINAR_EVENT);

    private String value;

    /* renamed from: com.easefun.polyv.livescenes.config.PolyvLiveChannelType$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$plv$livescenes$config$PLVLiveChannelType;

        static {
            int[] iArr = new int[PLVLiveChannelType.values().length];
            $SwitchMap$com$plv$livescenes$config$PLVLiveChannelType = iArr;
            try {
                iArr[PLVLiveChannelType.PPT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$plv$livescenes$config$PLVLiveChannelType[PLVLiveChannelType.ALONE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$plv$livescenes$config$PLVLiveChannelType[PLVLiveChannelType.SEMINAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

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

    PolyvLiveChannelType(String str) {
        this.value = str;
    }

    public static PolyvLiveChannelType mapFromNewType(PLVLiveChannelType pLVLiveChannelType) {
        if (pLVLiveChannelType == null) {
            return null;
        }
        int i2 = AnonymousClass1.$SwitchMap$com$plv$livescenes$config$PLVLiveChannelType[pLVLiveChannelType.ordinal()];
        if (i2 == 1) {
            return PPT;
        }
        if (i2 == 2) {
            return ALONE;
        }
        if (i2 != 3) {
            return null;
        }
        return SEMINAR;
    }

    public static PolyvLiveChannelType mapFromServerString(String str) throws UnknownChannelTypeException {
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
}
