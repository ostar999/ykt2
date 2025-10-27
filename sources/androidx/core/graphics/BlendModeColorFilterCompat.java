package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.BlendModeUtils;

/* loaded from: classes.dex */
public class BlendModeColorFilterCompat {

    @RequiresApi(29)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        public static ColorFilter createBlendModeColorFilter(int i2, Object obj) {
            return new BlendModeColorFilter(i2, (BlendMode) obj);
        }
    }

    private BlendModeColorFilterCompat() {
    }

    @Nullable
    public static ColorFilter createBlendModeColorFilterCompat(int i2, @NonNull BlendModeCompat blendModeCompat) {
        if (Build.VERSION.SDK_INT >= 29) {
            Object objObtainBlendModeFromCompat = BlendModeUtils.Api29Impl.obtainBlendModeFromCompat(blendModeCompat);
            if (objObtainBlendModeFromCompat != null) {
                return Api29Impl.createBlendModeColorFilter(i2, objObtainBlendModeFromCompat);
            }
            return null;
        }
        PorterDuff.Mode modeObtainPorterDuffFromCompat = BlendModeUtils.obtainPorterDuffFromCompat(blendModeCompat);
        if (modeObtainPorterDuffFromCompat != null) {
            return new PorterDuffColorFilter(i2, modeObtainPorterDuffFromCompat);
        }
        return null;
    }
}
