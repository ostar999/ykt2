package com.tencent.tbs.one;

import android.os.Bundle;

/* loaded from: classes6.dex */
public interface TBSOneOnlineService {
    void cancelUpdate();

    void update(Bundle bundle, TBSOneCallback<Void> tBSOneCallback);
}
