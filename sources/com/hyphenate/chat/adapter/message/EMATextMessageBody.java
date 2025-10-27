package com.hyphenate.chat.adapter.message;

import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EMATextMessageBody extends EMAMessageBody {
    private EMATextMessageBody() {
        nativeInit("");
    }

    public EMATextMessageBody(EMATextMessageBody eMATextMessageBody) {
        nativeInit(eMATextMessageBody);
    }

    public EMATextMessageBody(String str) {
        nativeInit(str);
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public List<String> getTargetLanguages() {
        return nativeGetTargetLanguages();
    }

    public Map<String, String> getTranslations() {
        return nativeGetTranslations();
    }

    public native void nativeFinalize();

    public native List<String> nativeGetTargetLanguages();

    public native Map<String, String> nativeGetTranslations();

    public native void nativeInit(EMATextMessageBody eMATextMessageBody);

    public native void nativeInit(String str);

    public native void nativeSetTargetLanguages(List<String> list);

    public native void nativeSetText(String str);

    public native String nativeText();

    public void setTargetLanguages(List<String> list) {
        nativeSetTargetLanguages(list);
    }

    public void setText(String str) {
        nativeSetText(str);
    }

    public String text() {
        return nativeText();
    }
}
