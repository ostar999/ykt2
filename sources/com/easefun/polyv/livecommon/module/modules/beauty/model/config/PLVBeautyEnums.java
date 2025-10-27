package com.easefun.polyv.livecommon.module.modules.beauty.model.config;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.R;
import com.plv.beauty.api.options.PLVBeautyOption;
import com.plv.foundationsdk.utils.PLVAppUtils;

/* loaded from: classes3.dex */
public class PLVBeautyEnums {

    public enum BeautyOption {
        BEAUTY_SMOOTH(PLVBeautyOption.BEAUTY_SMOOTH, PLVAppUtils.getString(R.string.plv_beauty_smooth), R.drawable.plv_beauty_smooth_icon),
        BEAUTY_WHITEN(PLVBeautyOption.BEAUTY_WHITEN, PLVAppUtils.getString(R.string.plv_beauty_whiten), R.drawable.plv_beauty_whiten_icon),
        BEAUTY_SHARP(PLVBeautyOption.BEAUTY_SHARP, PLVAppUtils.getString(R.string.plv_beauty_sharp), R.drawable.plv_beauty_sharp_icon);

        public final PLVBeautyOption beautyOption;

        @DrawableRes
        public final int iconResId;
        public final String name;

        BeautyOption(final PLVBeautyOption beautyOption, final String name, final int iconResId) {
            this.beautyOption = beautyOption;
            this.name = name;
            this.iconResId = iconResId;
        }

        public static boolean contains(PLVBeautyOption beautyOption) {
            return getByBeautyOption(beautyOption) != null;
        }

        @Nullable
        public static BeautyOption getByBeautyOption(PLVBeautyOption beautyOption) {
            for (BeautyOption beautyOption2 : values()) {
                if (beautyOption2.beautyOption == beautyOption) {
                    return beautyOption2;
                }
            }
            return null;
        }
    }

    public enum DetailOption {
        RESHAPE_DEFORM_EYE(PLVBeautyOption.RESHAPE_DEFORM_EYE, PLVAppUtils.getString(R.string.plv_beauty_deform_eye), R.drawable.plv_beauty_deform_eye_icon),
        RESHAPE_DEFORM_OVERALL(PLVBeautyOption.RESHAPE_DEFORM_OVERALL, PLVAppUtils.getString(R.string.plv_beauty_deform_overall), R.drawable.plv_beauty_deform_overall_icon),
        RESHAPE_DEFORM_ZOOM_JAWBONE(PLVBeautyOption.RESHAPE_DEFORM_ZOOM_JAWBONE, PLVAppUtils.getString(R.string.plv_beauty_deform_zoom_jawbone), R.drawable.plv_beauty_deform_zoom_jawbone_icon),
        RESHAPE_DEFORM_FOREHEAD(PLVBeautyOption.RESHAPE_DEFORM_FOREHEAD, PLVAppUtils.getString(R.string.plv_beauty_deform_forehead), R.drawable.plv_beauty_deform_forehead_icon),
        RESHAPE_BEAUTY_BRIGHTEN_EYE(PLVBeautyOption.RESHAPE_BEAUTY_BRIGHTEN_EYE, PLVAppUtils.getString(R.string.plv_beauty_brighten_eye), R.drawable.plv_beauty_brighten_eye_icon),
        RESHAPE_DEFORM_NOSE(PLVBeautyOption.RESHAPE_DEFORM_NOSE, PLVAppUtils.getString(R.string.plv_beauty_deform_nose), R.drawable.plv_beauty_deform_nose_icon),
        RESHAPE_DEFORM_ZOOM_MOUTH(PLVBeautyOption.RESHAPE_DEFORM_ZOOM_MOUTH, PLVAppUtils.getString(R.string.plv_beauty_deform_zoom_mouth), R.drawable.plv_beauty_deform_zoom_mouth_icon),
        RESHAPE_BEAUTY_WHITEN_TEETH(PLVBeautyOption.RESHAPE_BEAUTY_WHITEN_TEETH, PLVAppUtils.getString(R.string.plv_beauty_whiten_teeth), R.drawable.plv_beauty_whiten_teeth_icon);

        public final PLVBeautyOption beautyOption;

        @DrawableRes
        public final int iconResId;
        public final String name;

        DetailOption(final PLVBeautyOption beautyOption, final String name, final int iconResId) {
            this.beautyOption = beautyOption;
            this.name = name;
            this.iconResId = iconResId;
        }

        public static boolean contains(PLVBeautyOption beautyOption) {
            return getByBeautyOption(beautyOption) != null;
        }

        @Nullable
        public static DetailOption getByBeautyOption(PLVBeautyOption beautyOption) {
            for (DetailOption detailOption : values()) {
                if (detailOption.beautyOption == beautyOption) {
                    return detailOption;
                }
            }
            return null;
        }
    }
}
