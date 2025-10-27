package com.umeng.socialize;

import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.Map;

/* loaded from: classes6.dex */
public interface UMAuthListener {
    public static final int ACTION_AUTHORIZE = 0;
    public static final int ACTION_DELETE = 1;
    public static final int ACTION_GET_PROFILE = 2;

    void onCancel(SHARE_MEDIA share_media, int i2);

    void onComplete(SHARE_MEDIA share_media, int i2, Map<String, String> map);

    void onError(SHARE_MEDIA share_media, int i2, Throwable th);

    void onStart(SHARE_MEDIA share_media);
}
