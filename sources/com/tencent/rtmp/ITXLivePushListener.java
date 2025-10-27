package com.tencent.rtmp;

import android.os.Bundle;

/* loaded from: classes6.dex */
public interface ITXLivePushListener {
    void onNetStatus(Bundle bundle);

    void onPushEvent(int i2, Bundle bundle);
}
