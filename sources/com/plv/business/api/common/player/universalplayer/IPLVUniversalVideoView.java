package com.plv.business.api.common.player.universalplayer;

import android.net.Uri;
import java.util.Map;

/* loaded from: classes4.dex */
public interface IPLVUniversalVideoView {
    void setVideoPath(String str);

    void setVideoURI(Uri uri);

    void setVideoURI(Uri uri, Map<String, String> map);
}
