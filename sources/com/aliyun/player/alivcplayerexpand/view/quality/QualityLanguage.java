package com.aliyun.player.alivcplayerexpand.view.quality;

import android.content.Context;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.alivcplayerexpand.listener.QualityValue;

/* loaded from: classes2.dex */
public class QualityLanguage {
    public static String getMtsLanguage(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.toUpperCase().contains("XLD")) {
            String string = context.getString(R.string.alivc_mts_xld_definition);
            if (!str.contains(StrPool.UNDERLINE)) {
                return string;
            }
            return string + StrPool.UNDERLINE + str.split(StrPool.UNDERLINE)[1];
        }
        if (str.toUpperCase().contains(QualityValue.QUALITY_LOW)) {
            String string2 = context.getString(R.string.alivc_mts_ld_definition);
            if (!str.contains(StrPool.UNDERLINE)) {
                return string2;
            }
            return string2 + StrPool.UNDERLINE + str.split(StrPool.UNDERLINE)[1];
        }
        if (str.toUpperCase().contains(QualityValue.QUALITY_STAND)) {
            String string3 = context.getString(R.string.alivc_mts_sd_definition);
            if (!str.contains(StrPool.UNDERLINE)) {
                return string3;
            }
            return string3 + StrPool.UNDERLINE + str.split(StrPool.UNDERLINE)[1];
        }
        if (str.toUpperCase().contains("FHD")) {
            String string4 = context.getString(R.string.alivc_mts_fhd_definition);
            if (!str.contains(StrPool.UNDERLINE)) {
                return string4;
            }
            return string4 + StrPool.UNDERLINE + str.split(StrPool.UNDERLINE)[1];
        }
        if (!str.toUpperCase().contains(QualityValue.QUALITY_HIGH)) {
            return null;
        }
        String string5 = context.getString(R.string.alivc_mts_hd_definition);
        if (!str.contains(StrPool.UNDERLINE)) {
            return string5;
        }
        return string5 + StrPool.UNDERLINE + str.split(StrPool.UNDERLINE)[1];
    }

    public static String getSaasLanguage(Context context, String str) {
        if (QualityValue.QUALITY_FLUENT.equals(str)) {
            return context.getString(R.string.alivc_fd_definition);
        }
        if (QualityValue.QUALITY_LOW.equals(str)) {
            return context.getString(R.string.alivc_ld_definition);
        }
        if (QualityValue.QUALITY_STAND.equals(str)) {
            return context.getString(R.string.alivc_sd_definition);
        }
        if (QualityValue.QUALITY_HIGH.equals(str)) {
            return context.getString(R.string.alivc_hd_definition);
        }
        if (QualityValue.QUALITY_2K.equals(str)) {
            return context.getString(R.string.alivc_k2_definition);
        }
        if (QualityValue.QUALITY_4K.equals(str)) {
            return context.getString(R.string.alivc_k4_definition);
        }
        if (QualityValue.QUALITY_SQ.equals(str)) {
            return context.getString(R.string.alivc_sq_definition);
        }
        if (QualityValue.QUALITY_HQ.equals(str)) {
            return context.getString(R.string.alivc_hq_definition);
        }
        QualityValue.QUALITY_ORIGINAL.equals(str);
        return context.getString(R.string.alivc_od_definition);
    }
}
