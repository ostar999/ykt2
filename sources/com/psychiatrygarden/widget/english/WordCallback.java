package com.psychiatrygarden.widget.english;

import org.json.JSONObject;

/* loaded from: classes6.dex */
public interface WordCallback {
    void dismissLoading();

    void onError(String errMessage);

    void onSuccess(JSONObject jsonObject);

    void showLoading();
}
