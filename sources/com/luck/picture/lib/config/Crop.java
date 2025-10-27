package com.luck.picture.lib.config;

import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
public class Crop {
    public static final String EXTRA_ERROR = "com.yalantis.ucrop.Error";
    public static final String EXTRA_OUTPUT_CROP_ASPECT_RATIO = "com.yalantis.ucrop.CropAspectRatio";
    public static final String EXTRA_OUTPUT_IMAGE_HEIGHT = "com.yalantis.ucrop.ImageHeight";
    public static final String EXTRA_OUTPUT_IMAGE_WIDTH = "com.yalantis.ucrop.ImageWidth";
    public static final String EXTRA_OUTPUT_OFFSET_X = "com.yalantis.ucrop.OffsetX";
    public static final String EXTRA_OUTPUT_OFFSET_Y = "com.yalantis.ucrop.OffsetY";
    private static final String EXTRA_PREFIX = "com.yalantis.ucrop";
    public static final int REQUEST_CROP = 69;
    public static final int REQUEST_EDIT_CROP = 696;
    public static final int RESULT_CROP_ERROR = 96;

    @Nullable
    public static Throwable getError(@NonNull Intent intent) {
        return (Throwable) intent.getSerializableExtra(EXTRA_ERROR);
    }

    @Nullable
    public static Uri getOutput(@NonNull Intent intent) {
        return (Uri) intent.getParcelableExtra("output");
    }

    public static float getOutputCropAspectRatio(@NonNull Intent intent) {
        return intent.getFloatExtra(EXTRA_OUTPUT_CROP_ASPECT_RATIO, 0.0f);
    }

    public static String getOutputCustomExtraData(@NonNull Intent intent) {
        return intent.getStringExtra(CustomIntentKey.EXTRA_CUSTOM_EXTRA_DATA);
    }

    public static int getOutputImageHeight(@NonNull Intent intent) {
        return intent.getIntExtra(EXTRA_OUTPUT_IMAGE_HEIGHT, -1);
    }

    public static int getOutputImageOffsetX(@NonNull Intent intent) {
        return intent.getIntExtra(EXTRA_OUTPUT_OFFSET_X, 0);
    }

    public static int getOutputImageOffsetY(@NonNull Intent intent) {
        return intent.getIntExtra(EXTRA_OUTPUT_OFFSET_Y, 0);
    }

    public static int getOutputImageWidth(@NonNull Intent intent) {
        return intent.getIntExtra(EXTRA_OUTPUT_IMAGE_WIDTH, -1);
    }
}
