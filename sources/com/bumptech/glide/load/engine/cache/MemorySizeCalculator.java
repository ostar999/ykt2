package com.bumptech.glide.load.engine.cache;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.util.Preconditions;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* loaded from: classes2.dex */
public final class MemorySizeCalculator {

    @VisibleForTesting
    static final int BYTES_PER_ARGB_8888_PIXEL = 4;
    private static final int LOW_MEMORY_BYTE_ARRAY_POOL_DIVISOR = 2;
    private static final String TAG = "MemorySizeCalculator";
    private final int arrayPoolSize;
    private final int bitmapPoolSize;
    private final Context context;
    private final int memoryCacheSize;

    public static final class Builder {
        static final int ARRAY_POOL_SIZE_BYTES = 4194304;
        static final int BITMAP_POOL_TARGET_SCREENS;
        static final float LOW_MEMORY_MAX_SIZE_MULTIPLIER = 0.33f;
        static final float MAX_SIZE_MULTIPLIER = 0.4f;

        @VisibleForTesting
        static final int MEMORY_CACHE_TARGET_SCREENS = 2;
        ActivityManager activityManager;
        float bitmapPoolScreens;
        final Context context;
        ScreenDimensions screenDimensions;
        float memoryCacheScreens = 2.0f;
        float maxSizeMultiplier = MAX_SIZE_MULTIPLIER;
        float lowMemoryMaxSizeMultiplier = LOW_MEMORY_MAX_SIZE_MULTIPLIER;
        int arrayPoolSizeBytes = 4194304;

        static {
            BITMAP_POOL_TARGET_SCREENS = Build.VERSION.SDK_INT < 26 ? 4 : 1;
        }

        public Builder(Context context) {
            this.bitmapPoolScreens = BITMAP_POOL_TARGET_SCREENS;
            this.context = context;
            this.activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            this.screenDimensions = new DisplayMetricsScreenDimensions(context.getResources().getDisplayMetrics());
            if (Build.VERSION.SDK_INT < 26 || !MemorySizeCalculator.isLowMemoryDevice(this.activityManager)) {
                return;
            }
            this.bitmapPoolScreens = 0.0f;
        }

        public MemorySizeCalculator build() {
            return new MemorySizeCalculator(this);
        }

        @VisibleForTesting
        public Builder setActivityManager(ActivityManager activityManager) {
            this.activityManager = activityManager;
            return this;
        }

        public Builder setArrayPoolSize(int i2) {
            this.arrayPoolSizeBytes = i2;
            return this;
        }

        public Builder setBitmapPoolScreens(float f2) {
            Preconditions.checkArgument(f2 >= 0.0f, "Bitmap pool screens must be greater than or equal to 0");
            this.bitmapPoolScreens = f2;
            return this;
        }

        public Builder setLowMemoryMaxSizeMultiplier(float f2) {
            Preconditions.checkArgument(f2 >= 0.0f && f2 <= 1.0f, "Low memory max size multiplier must be between 0 and 1");
            this.lowMemoryMaxSizeMultiplier = f2;
            return this;
        }

        public Builder setMaxSizeMultiplier(float f2) {
            Preconditions.checkArgument(f2 >= 0.0f && f2 <= 1.0f, "Size multiplier must be between 0 and 1");
            this.maxSizeMultiplier = f2;
            return this;
        }

        public Builder setMemoryCacheScreens(float f2) {
            Preconditions.checkArgument(f2 >= 0.0f, "Memory cache screens must be greater than or equal to 0");
            this.memoryCacheScreens = f2;
            return this;
        }

        @VisibleForTesting
        public Builder setScreenDimensions(ScreenDimensions screenDimensions) {
            this.screenDimensions = screenDimensions;
            return this;
        }
    }

    public static final class DisplayMetricsScreenDimensions implements ScreenDimensions {
        private final DisplayMetrics displayMetrics;

        public DisplayMetricsScreenDimensions(DisplayMetrics displayMetrics) {
            this.displayMetrics = displayMetrics;
        }

        @Override // com.bumptech.glide.load.engine.cache.MemorySizeCalculator.ScreenDimensions
        public int getHeightPixels() {
            return this.displayMetrics.heightPixels;
        }

        @Override // com.bumptech.glide.load.engine.cache.MemorySizeCalculator.ScreenDimensions
        public int getWidthPixels() {
            return this.displayMetrics.widthPixels;
        }
    }

    public interface ScreenDimensions {
        int getHeightPixels();

        int getWidthPixels();
    }

    public MemorySizeCalculator(Builder builder) {
        this.context = builder.context;
        int i2 = isLowMemoryDevice(builder.activityManager) ? builder.arrayPoolSizeBytes / 2 : builder.arrayPoolSizeBytes;
        this.arrayPoolSize = i2;
        int maxSize = getMaxSize(builder.activityManager, builder.maxSizeMultiplier, builder.lowMemoryMaxSizeMultiplier);
        float widthPixels = builder.screenDimensions.getWidthPixels() * builder.screenDimensions.getHeightPixels() * 4;
        int iRound = Math.round(builder.bitmapPoolScreens * widthPixels);
        int iRound2 = Math.round(widthPixels * builder.memoryCacheScreens);
        int i3 = maxSize - i2;
        int i4 = iRound2 + iRound;
        if (i4 <= i3) {
            this.memoryCacheSize = iRound2;
            this.bitmapPoolSize = iRound;
        } else {
            float f2 = i3;
            float f3 = builder.bitmapPoolScreens;
            float f4 = builder.memoryCacheScreens;
            float f5 = f2 / (f3 + f4);
            this.memoryCacheSize = Math.round(f4 * f5);
            this.bitmapPoolSize = Math.round(f5 * builder.bitmapPoolScreens);
        }
        if (Log.isLoggable(TAG, 3)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Calculation complete, Calculated memory cache size: ");
            sb.append(toMb(this.memoryCacheSize));
            sb.append(", pool size: ");
            sb.append(toMb(this.bitmapPoolSize));
            sb.append(", byte array size: ");
            sb.append(toMb(i2));
            sb.append(", memory class limited? ");
            sb.append(i4 > maxSize);
            sb.append(", max size: ");
            sb.append(toMb(maxSize));
            sb.append(", memoryClass: ");
            sb.append(builder.activityManager.getMemoryClass());
            sb.append(", isLowMemoryDevice: ");
            sb.append(isLowMemoryDevice(builder.activityManager));
            Log.d(TAG, sb.toString());
        }
    }

    private static int getMaxSize(ActivityManager activityManager, float f2, float f3) {
        float memoryClass = activityManager.getMemoryClass() * 1024 * 1024;
        if (isLowMemoryDevice(activityManager)) {
            f2 = f3;
        }
        return Math.round(memoryClass * f2);
    }

    @TargetApi(19)
    public static boolean isLowMemoryDevice(ActivityManager activityManager) {
        return activityManager.isLowRamDevice();
    }

    private String toMb(int i2) {
        return Formatter.formatFileSize(this.context, i2);
    }

    public int getArrayPoolSizeInBytes() {
        return this.arrayPoolSize;
    }

    public int getBitmapPoolSize() {
        return this.bitmapPoolSize;
    }

    public int getMemoryCacheSize() {
        return this.memoryCacheSize;
    }
}
