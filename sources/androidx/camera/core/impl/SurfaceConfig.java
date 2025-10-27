package androidx.camera.core.impl;

import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.internal.utils.SizeUtil;
import com.google.auto.value.AutoValue;

@AutoValue
@RequiresApi(21)
/* loaded from: classes.dex */
public abstract class SurfaceConfig {

    public enum ConfigSize {
        VGA(0),
        PREVIEW(1),
        RECORD(2),
        MAXIMUM(3),
        NOT_SUPPORT(4);

        final int mId;

        ConfigSize(int i2) {
            this.mId = i2;
        }

        public int getId() {
            return this.mId;
        }
    }

    public enum ConfigType {
        PRIV,
        YUV,
        JPEG,
        RAW
    }

    @NonNull
    public static SurfaceConfig create(@NonNull ConfigType configType, @NonNull ConfigSize configSize) {
        return new AutoValue_SurfaceConfig(configType, configSize);
    }

    @NonNull
    public static ConfigType getConfigType(int i2) {
        return i2 == 35 ? ConfigType.YUV : i2 == 256 ? ConfigType.JPEG : i2 == 32 ? ConfigType.RAW : ConfigType.PRIV;
    }

    @NonNull
    public static SurfaceConfig transformSurfaceConfig(int i2, @NonNull Size size, @NonNull SurfaceSizeDefinition surfaceSizeDefinition) {
        ConfigType configType = getConfigType(i2);
        ConfigSize configSize = ConfigSize.NOT_SUPPORT;
        int area = SizeUtil.getArea(size);
        return create(configType, area <= SizeUtil.getArea(surfaceSizeDefinition.getAnalysisSize()) ? ConfigSize.VGA : area <= SizeUtil.getArea(surfaceSizeDefinition.getPreviewSize()) ? ConfigSize.PREVIEW : area <= SizeUtil.getArea(surfaceSizeDefinition.getRecordSize()) ? ConfigSize.RECORD : ConfigSize.MAXIMUM);
    }

    @NonNull
    public abstract ConfigSize getConfigSize();

    @NonNull
    public abstract ConfigType getConfigType();

    public final boolean isSupported(@NonNull SurfaceConfig surfaceConfig) {
        return surfaceConfig.getConfigSize().getId() <= getConfigSize().getId() && surfaceConfig.getConfigType() == getConfigType();
    }
}
