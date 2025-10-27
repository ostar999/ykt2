package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public class EMATranslateManager extends EMABase {
    public EMATranslateResult getTranslationResultByMsgId(String str) {
        return nativeGetTranslationResultByMsgId(str);
    }

    public List<EMATranslateResult> loadTranslateResults(int i2) {
        return nativeLoadTranslateResults(i2);
    }

    public native EMATranslateResult nativeGetTranslationResultByMsgId(String str);

    public native List<EMATranslateResult> nativeLoadTranslateResults(int i2);

    public native boolean nativeRemoveAllTranslations();

    public native boolean nativeRemoveTranslationsByConversationId(String str);

    public native boolean nativeRemoveTranslationsByMsgId(List<String> list);

    public native boolean nativeUpdateTranslation(EMATranslateResult eMATranslateResult);

    public boolean removeAllTranslations() {
        return nativeRemoveAllTranslations();
    }

    public boolean removeTranslationsByConversationId(String str) {
        return nativeRemoveTranslationsByConversationId(str);
    }

    public boolean removeTranslationsByMsgId(List<String> list) {
        return nativeRemoveTranslationsByMsgId(list);
    }

    public boolean updateTranslation(EMATranslateResult eMATranslateResult) {
        return nativeUpdateTranslation(eMATranslateResult);
    }
}
