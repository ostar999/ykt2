package com.luck.lib.camerax.listener;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public interface CameraListener {
    void onError(int i2, String str, Throwable th);

    void onPictureSuccess(@NonNull String str);

    void onRecordSuccess(@NonNull String str);
}
