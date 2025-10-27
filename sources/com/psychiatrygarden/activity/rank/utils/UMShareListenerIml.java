package com.psychiatrygarden.activity.rank.utils;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/* loaded from: classes5.dex */
public interface UMShareListenerIml extends UMShareListener {
    @Override // com.umeng.socialize.UMShareListener
    void onCancel(SHARE_MEDIA share_media);

    @Override // com.umeng.socialize.UMShareListener
    void onError(SHARE_MEDIA share_media, Throwable throwable);

    @Override // com.umeng.socialize.UMShareListener
    void onResult(SHARE_MEDIA share_media);

    @Override // com.umeng.socialize.UMShareListener
    void onStart(SHARE_MEDIA share_media);
}
