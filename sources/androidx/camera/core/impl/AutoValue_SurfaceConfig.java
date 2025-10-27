package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.camera.core.impl.SurfaceConfig;

/* loaded from: classes.dex */
final class AutoValue_SurfaceConfig extends SurfaceConfig {
    private final SurfaceConfig.ConfigSize configSize;
    private final SurfaceConfig.ConfigType configType;

    public AutoValue_SurfaceConfig(SurfaceConfig.ConfigType configType, SurfaceConfig.ConfigSize configSize) {
        if (configType == null) {
            throw new NullPointerException("Null configType");
        }
        this.configType = configType;
        if (configSize == null) {
            throw new NullPointerException("Null configSize");
        }
        this.configSize = configSize;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SurfaceConfig)) {
            return false;
        }
        SurfaceConfig surfaceConfig = (SurfaceConfig) obj;
        return this.configType.equals(surfaceConfig.getConfigType()) && this.configSize.equals(surfaceConfig.getConfigSize());
    }

    @Override // androidx.camera.core.impl.SurfaceConfig
    @NonNull
    public SurfaceConfig.ConfigSize getConfigSize() {
        return this.configSize;
    }

    @Override // androidx.camera.core.impl.SurfaceConfig
    @NonNull
    public SurfaceConfig.ConfigType getConfigType() {
        return this.configType;
    }

    public int hashCode() {
        return ((this.configType.hashCode() ^ 1000003) * 1000003) ^ this.configSize.hashCode();
    }

    public String toString() {
        return "SurfaceConfig{configType=" + this.configType + ", configSize=" + this.configSize + "}";
    }
}
