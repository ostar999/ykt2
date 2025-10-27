package com.psychiatrygarden.interfaceclass;

/* loaded from: classes4.dex */
public interface QuestionDataCallBack<T> {
    void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl);

    void onStart(String requstUrl);

    void onSuccess(T t2, String requstUrl);
}
