package net.polyv.danmaku.danmaku.loader.android;

import net.polyv.danmaku.danmaku.loader.ILoader;

/* loaded from: classes9.dex */
public class DanmakuLoaderFactory {
    public static String TAG_ACFUN = "acfun";
    public static String TAG_BILI = "bili";

    public static ILoader create(String str) {
        if (TAG_BILI.equalsIgnoreCase(str)) {
            return BiliDanmakuLoader.instance();
        }
        if (TAG_ACFUN.equalsIgnoreCase(str)) {
            return AcFunDanmakuLoader.instance();
        }
        return null;
    }
}
