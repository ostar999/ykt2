package com.easefun.polyv.livecommon.module.modules.beauty.model.config;

import com.plv.beauty.api.options.PLVBeautyOption;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class PLVBeautyOptionDefaultConfig {
    public static final Map<PLVBeautyOption, Float> DEFAULT_BEAUTY_OPTION_VALUE;
    public static final List<String> DEFAULT_FILTER_KEY_ORDER;
    public static final float DEFAULT_FILTER_VALUE = 0.5f;

    static {
        PLVBeautyOption pLVBeautyOption = PLVBeautyOption.BEAUTY_SHARP;
        Float fValueOf = Float.valueOf(0.25f);
        PLVBeautyOption pLVBeautyOption2 = PLVBeautyOption.RESHAPE_DEFORM_OVERALL;
        Float fValueOf2 = Float.valueOf(0.35f);
        PLVBeautyOption pLVBeautyOption3 = PLVBeautyOption.RESHAPE_DEFORM_ZOOM_MOUTH;
        Float fValueOf3 = Float.valueOf(0.2f);
        DEFAULT_BEAUTY_OPTION_VALUE = PLVSugarUtil.mapOf(PLVSugarUtil.pair(PLVBeautyOption.BEAUTY_SMOOTH, Float.valueOf(0.85f)), PLVSugarUtil.pair(PLVBeautyOption.BEAUTY_WHITEN, Float.valueOf(0.7f)), PLVSugarUtil.pair(pLVBeautyOption, fValueOf), PLVSugarUtil.pair(pLVBeautyOption2, fValueOf2), PLVSugarUtil.pair(PLVBeautyOption.RESHAPE_DEFORM_EYE, fValueOf), PLVSugarUtil.pair(PLVBeautyOption.RESHAPE_DEFORM_NOSE, Float.valueOf(0.3f)), PLVSugarUtil.pair(pLVBeautyOption3, fValueOf3), PLVSugarUtil.pair(PLVBeautyOption.RESHAPE_DEFORM_FOREHEAD, Float.valueOf(0.4f)), PLVSugarUtil.pair(PLVBeautyOption.RESHAPE_DEFORM_ZOOM_JAWBONE, fValueOf3), PLVSugarUtil.pair(PLVBeautyOption.RESHAPE_BEAUTY_WHITEN_TEETH, fValueOf2), PLVSugarUtil.pair(PLVBeautyOption.RESHAPE_BEAUTY_BRIGHTEN_EYE, Float.valueOf(0.65f)));
        DEFAULT_FILTER_KEY_ORDER = PLVSugarUtil.listOf("原图", "氧气", "初见", "冷氧", "温柔", "慕斯", "蜜桃", "物语", "樱花", "胶片", "夜色");
    }

    private PLVBeautyOptionDefaultConfig() {
    }
}
