package com.hyphenate.chat.adapter.message;

import com.hyphenate.chat.adapter.EMABase;

/* loaded from: classes4.dex */
public abstract class EMAMessageBody extends EMABase {
    public static final int EMAMessaeBody_Type_CUSTOM = 7;
    public static final int EMAMessageBodyType_COMMAND = 6;
    public static final int EMAMessageBodyType_FILE = 5;
    public static final int EMAMessageBodyType_IMAGE = 1;
    public static final int EMAMessageBodyType_LOCATION = 3;
    public static final int EMAMessageBodyType_TEXT = 0;
    public static final int EMAMessageBodyType_VIDEO = 2;
    public static final int EMAMessageBodyType_VOICE = 4;
    public int type = 0;

    public native int nativeType();

    public int type() {
        return nativeType();
    }
}
