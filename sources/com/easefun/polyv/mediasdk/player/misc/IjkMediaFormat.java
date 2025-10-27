package com.easefun.polyv.mediasdk.player.misc;

import android.annotation.TargetApi;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes3.dex */
public class IjkMediaFormat implements IMediaFormat {
    public static final String CODEC_NAME_H264 = "h264";
    public static final String KEY_IJK_BIT_RATE_UI = "polyv-bit-rate-ui";
    public static final String KEY_IJK_CHANNEL_UI = "polyv-channel-ui";
    public static final String KEY_IJK_CODEC_LONG_NAME_UI = "polyv-codec-long-name-ui";
    public static final String KEY_IJK_CODEC_NAME_UI = "polyv-codec-name-ui";
    public static final String KEY_IJK_CODEC_PIXEL_FORMAT_UI = "polyv-pixel-format-ui";
    public static final String KEY_IJK_CODEC_PROFILE_LEVEL_UI = "polyv-profile-level-ui";
    public static final String KEY_IJK_FRAME_RATE_UI = "polyv-frame-rate-ui";
    public static final String KEY_IJK_RESOLUTION_UI = "polyv-resolution-ui";
    public static final String KEY_IJK_SAMPLE_RATE_UI = "polyv-sample-rate-ui";
    private static final Map<String, Formatter> sFormatterMap = new HashMap();
    public final IjkMediaMeta.IjkStreamMeta mMediaFormat;

    public static abstract class Formatter {
        private Formatter() {
        }

        public abstract String doFormat(IjkMediaFormat ijkMediaFormat);

        public String format(IjkMediaFormat ijkMediaFormat) {
            String strDoFormat = doFormat(ijkMediaFormat);
            return TextUtils.isEmpty(strDoFormat) ? getDefaultString() : strDoFormat;
        }

        public String getDefaultString() {
            return "N/A";
        }
    }

    public IjkMediaFormat(IjkMediaMeta.IjkStreamMeta ijkStreamMeta) {
        Map<String, Formatter> map = sFormatterMap;
        map.put(KEY_IJK_CODEC_LONG_NAME_UI, new Formatter() { // from class: com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.1
            @Override // com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.Formatter
            public String doFormat(IjkMediaFormat ijkMediaFormat) {
                return IjkMediaFormat.this.mMediaFormat.getString(IjkMediaMeta.IJKM_KEY_CODEC_LONG_NAME);
            }
        });
        map.put(KEY_IJK_CODEC_NAME_UI, new Formatter() { // from class: com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.2
            @Override // com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.Formatter
            public String doFormat(IjkMediaFormat ijkMediaFormat) {
                return IjkMediaFormat.this.mMediaFormat.getString(IjkMediaMeta.IJKM_KEY_CODEC_NAME);
            }
        });
        map.put(KEY_IJK_BIT_RATE_UI, new Formatter() { // from class: com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.3
            @Override // com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.Formatter
            public String doFormat(IjkMediaFormat ijkMediaFormat) {
                int integer = ijkMediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_BITRATE);
                if (integer <= 0) {
                    return null;
                }
                return integer < 1000 ? String.format(Locale.US, "%d bit/s", Integer.valueOf(integer)) : String.format(Locale.US, "%d kb/s", Integer.valueOf(integer / 1000));
            }
        });
        map.put(KEY_IJK_CODEC_PROFILE_LEVEL_UI, new Formatter() { // from class: com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.4
            @Override // com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.Formatter
            public String doFormat(IjkMediaFormat ijkMediaFormat) {
                String str;
                switch (ijkMediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_CODEC_PROFILE_ID)) {
                    case 44:
                        str = "CAVLC 4:4:4";
                        break;
                    case 66:
                        str = "Baseline";
                        break;
                    case 77:
                        str = "Main";
                        break;
                    case 88:
                        str = "Extended";
                        break;
                    case 100:
                        str = "High";
                        break;
                    case 110:
                        str = "High 10";
                        break;
                    case 122:
                        str = "High 4:2:2";
                        break;
                    case 144:
                        str = "High 4:4:4";
                        break;
                    case 244:
                        str = "High 4:4:4 Predictive";
                        break;
                    case 578:
                        str = "Constrained Baseline";
                        break;
                    case 2158:
                        str = "High 10 Intra";
                        break;
                    case 2170:
                        str = "High 4:2:2 Intra";
                        break;
                    case 2292:
                        str = "High 4:4:4 Intra";
                        break;
                    default:
                        return null;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                String string = ijkMediaFormat.getString(IjkMediaMeta.IJKM_KEY_CODEC_NAME);
                if (!TextUtils.isEmpty(string) && string.equalsIgnoreCase("h264")) {
                    int integer = ijkMediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_CODEC_LEVEL);
                    if (integer < 10) {
                        return sb.toString();
                    }
                    sb.append(" Profile Level ");
                    sb.append((integer / 10) % 10);
                    int i2 = integer % 10;
                    if (i2 != 0) {
                        sb.append(StrPool.DOT);
                        sb.append(i2);
                    }
                }
                return sb.toString();
            }
        });
        map.put(KEY_IJK_CODEC_PIXEL_FORMAT_UI, new Formatter() { // from class: com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.5
            @Override // com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.Formatter
            public String doFormat(IjkMediaFormat ijkMediaFormat) {
                return ijkMediaFormat.getString(IjkMediaMeta.IJKM_KEY_CODEC_PIXEL_FORMAT);
            }
        });
        map.put(KEY_IJK_RESOLUTION_UI, new Formatter() { // from class: com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.6
            @Override // com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.Formatter
            public String doFormat(IjkMediaFormat ijkMediaFormat) {
                int integer = ijkMediaFormat.getInteger("width");
                int integer2 = ijkMediaFormat.getInteger("height");
                int integer3 = ijkMediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_SAR_NUM);
                int integer4 = ijkMediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_SAR_DEN);
                if (integer <= 0 || integer2 <= 0) {
                    return null;
                }
                return (integer3 <= 0 || integer4 <= 0) ? String.format(Locale.US, "%d x %d", Integer.valueOf(integer), Integer.valueOf(integer2)) : String.format(Locale.US, "%d x %d [SAR %d:%d]", Integer.valueOf(integer), Integer.valueOf(integer2), Integer.valueOf(integer3), Integer.valueOf(integer4));
            }
        });
        map.put(KEY_IJK_FRAME_RATE_UI, new Formatter() { // from class: com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.7
            @Override // com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.Formatter
            public String doFormat(IjkMediaFormat ijkMediaFormat) {
                int integer = ijkMediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_FPS_NUM);
                int integer2 = ijkMediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_FPS_DEN);
                if (integer <= 0 || integer2 <= 0) {
                    return null;
                }
                return String.valueOf(integer / integer2);
            }
        });
        map.put(KEY_IJK_SAMPLE_RATE_UI, new Formatter() { // from class: com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.8
            @Override // com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.Formatter
            public String doFormat(IjkMediaFormat ijkMediaFormat) {
                int integer = ijkMediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_SAMPLE_RATE);
                if (integer <= 0) {
                    return null;
                }
                return String.format(Locale.US, "%d Hz", Integer.valueOf(integer));
            }
        });
        map.put(KEY_IJK_CHANNEL_UI, new Formatter() { // from class: com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.9
            @Override // com.easefun.polyv.mediasdk.player.misc.IjkMediaFormat.Formatter
            public String doFormat(IjkMediaFormat ijkMediaFormat) {
                int integer = ijkMediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_CHANNEL_LAYOUT);
                if (integer <= 0) {
                    return null;
                }
                long j2 = integer;
                return j2 == 4 ? "mono" : j2 == 3 ? "stereo" : String.format(Locale.US, "%x", Integer.valueOf(integer));
            }
        });
        this.mMediaFormat = ijkStreamMeta;
    }

    @Override // com.easefun.polyv.mediasdk.player.misc.IMediaFormat
    @TargetApi(16)
    public int getInteger(String str) {
        IjkMediaMeta.IjkStreamMeta ijkStreamMeta = this.mMediaFormat;
        if (ijkStreamMeta == null) {
            return 0;
        }
        return ijkStreamMeta.getInt(str);
    }

    @Override // com.easefun.polyv.mediasdk.player.misc.IMediaFormat
    public String getString(String str) {
        if (this.mMediaFormat == null) {
            return null;
        }
        Map<String, Formatter> map = sFormatterMap;
        return map.containsKey(str) ? map.get(str).format(this) : this.mMediaFormat.getString(str);
    }
}
