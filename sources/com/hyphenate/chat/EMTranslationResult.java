package com.hyphenate.chat;

import com.hyphenate.chat.adapter.EMATranslateResult;

@Deprecated
/* loaded from: classes4.dex */
public class EMTranslationResult extends EMBase<EMATranslateResult> {
    /* JADX WARN: Multi-variable type inference failed */
    public EMTranslationResult(EMATranslateResult eMATranslateResult) {
        this.emaObject = eMATranslateResult;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.hyphenate.chat.adapter.EMATranslateResult] */
    public EMTranslationResult(String str) {
        this.emaObject = new EMATranslateResult(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String conversationId() {
        return ((EMATranslateResult) this.emaObject).conversationId();
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String msgId() {
        return ((EMATranslateResult) this.emaObject).msgId();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setConversationId(String str) {
        ((EMATranslateResult) this.emaObject).setConversationId(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setShowTranslation(boolean z2) {
        ((EMATranslateResult) this.emaObject).setShowTranslation(z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setTranslateCount(int i2) {
        ((EMATranslateResult) this.emaObject).setTranslateTime(i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setTranslatedText(String str) {
        ((EMATranslateResult) this.emaObject).setTranslations(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean showTranslation() {
        return ((EMATranslateResult) this.emaObject).showTranslation();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int translateCount() {
        return ((EMATranslateResult) this.emaObject).translateTime();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String translatedText() {
        return ((EMATranslateResult) this.emaObject).translations();
    }
}
