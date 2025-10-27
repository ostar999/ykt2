package com.hyphenate.chat.adapter.message;

import android.net.Uri;

/* loaded from: classes4.dex */
public class EMAVoiceMessageBody extends EMAFileMessageBody {
    private EMAVoiceMessageBody() {
        super("", 4);
        nativeInit("", 0);
    }

    public EMAVoiceMessageBody(Uri uri, int i2) {
        super(uri, 4);
        nativeInit(uri != null ? uri.toString() : "", i2);
    }

    public EMAVoiceMessageBody(EMAVoiceMessageBody eMAVoiceMessageBody) {
        super("", 4);
        nativeInit(eMAVoiceMessageBody);
    }

    public EMAVoiceMessageBody(String str, int i2) {
        super(str, 4);
        nativeInit(str, i2);
    }

    public int duration() {
        return nativeDuration();
    }

    @Override // com.hyphenate.chat.adapter.message.EMAFileMessageBody
    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native int nativeDuration();

    @Override // com.hyphenate.chat.adapter.message.EMAFileMessageBody
    public native void nativeFinalize();

    public native void nativeInit(EMAVoiceMessageBody eMAVoiceMessageBody);

    @Override // com.hyphenate.chat.adapter.message.EMAFileMessageBody
    public native void nativeInit(String str, int i2);

    public native void nativeSetDuration(int i2);

    public void setDuration(int i2) {
        nativeSetDuration(i2);
    }
}
