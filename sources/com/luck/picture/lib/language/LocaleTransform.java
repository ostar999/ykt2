package com.luck.picture.lib.language;

import com.aliyun.vod.log.struct.AliyunLogKey;
import java.util.Locale;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;

/* loaded from: classes4.dex */
public class LocaleTransform {
    public static Locale getLanguage(int i2) {
        switch (i2) {
            case 1:
                return Locale.TRADITIONAL_CHINESE;
            case 2:
                return Locale.ENGLISH;
            case 3:
                return Locale.KOREA;
            case 4:
                return Locale.GERMANY;
            case 5:
                return Locale.FRANCE;
            case 6:
                return Locale.JAPAN;
            case 7:
                return new Locale(AliyunLogKey.KEY_VIDEOID);
            case 8:
                return new Locale("es", "ES");
            case 9:
                return new Locale("pt", "PT");
            case 10:
                return new Locale(ArchiveStreamFactory.AR, "AE");
            default:
                return Locale.CHINESE;
        }
    }
}
