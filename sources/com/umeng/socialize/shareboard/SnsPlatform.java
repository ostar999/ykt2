package com.umeng.socialize.shareboard;

import com.umeng.socialize.bean.SHARE_MEDIA;

/* loaded from: classes6.dex */
public final class SnsPlatform {
    public String mGrayIcon;
    public String mIcon;
    public int mIndex;
    public String mKeyword;
    public SHARE_MEDIA mPlatform;
    public String mShowWord;

    public SnsPlatform(String str) {
        this.mKeyword = str;
        this.mPlatform = SHARE_MEDIA.convertToEmun(str);
    }

    public SnsPlatform() {
    }
}
