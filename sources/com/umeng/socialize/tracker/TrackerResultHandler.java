package com.umeng.socialize.tracker;

/* loaded from: classes6.dex */
public interface TrackerResultHandler {
    void codeGenerateFailed(Throwable th);

    void codeGenerateSuccess(String str);
}
