package com.tencent.rtmp;

import android.os.Bundle;

/* loaded from: classes6.dex */
public interface ITXLivePlayListener {
    void onNetStatus(Bundle bundle);

    void onPlayEvent(int i2, Bundle bundle);
}
