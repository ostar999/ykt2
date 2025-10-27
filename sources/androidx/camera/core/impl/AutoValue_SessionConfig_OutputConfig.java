package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.SessionConfig;
import java.util.List;

/* loaded from: classes.dex */
final class AutoValue_SessionConfig_OutputConfig extends SessionConfig.OutputConfig {
    private final String physicalCameraId;
    private final List<DeferrableSurface> sharedSurfaces;
    private final DeferrableSurface surface;
    private final int surfaceGroupId;

    public static final class Builder extends SessionConfig.OutputConfig.Builder {
        private String physicalCameraId;
        private List<DeferrableSurface> sharedSurfaces;
        private DeferrableSurface surface;
        private Integer surfaceGroupId;

        @Override // androidx.camera.core.impl.SessionConfig.OutputConfig.Builder
        public SessionConfig.OutputConfig build() {
            String str = "";
            if (this.surface == null) {
                str = " surface";
            }
            if (this.sharedSurfaces == null) {
                str = str + " sharedSurfaces";
            }
            if (this.surfaceGroupId == null) {
                str = str + " surfaceGroupId";
            }
            if (str.isEmpty()) {
                return new AutoValue_SessionConfig_OutputConfig(this.surface, this.sharedSurfaces, this.physicalCameraId, this.surfaceGroupId.intValue());
            }
            throw new IllegalStateException("Missing required properties:" + str);
        }

        @Override // androidx.camera.core.impl.SessionConfig.OutputConfig.Builder
        public SessionConfig.OutputConfig.Builder setPhysicalCameraId(@Nullable String str) {
            this.physicalCameraId = str;
            return this;
        }

        @Override // androidx.camera.core.impl.SessionConfig.OutputConfig.Builder
        public SessionConfig.OutputConfig.Builder setSharedSurfaces(List<DeferrableSurface> list) {
            if (list == null) {
                throw new NullPointerException("Null sharedSurfaces");
            }
            this.sharedSurfaces = list;
            return this;
        }

        @Override // androidx.camera.core.impl.SessionConfig.OutputConfig.Builder
        public SessionConfig.OutputConfig.Builder setSurface(DeferrableSurface deferrableSurface) {
            if (deferrableSurface == null) {
                throw new NullPointerException("Null surface");
            }
            this.surface = deferrableSurface;
            return this;
        }

        @Override // androidx.camera.core.impl.SessionConfig.OutputConfig.Builder
        public SessionConfig.OutputConfig.Builder setSurfaceGroupId(int i2) {
            this.surfaceGroupId = Integer.valueOf(i2);
            return this;
        }
    }

    public boolean equals(Object obj) {
        String str;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SessionConfig.OutputConfig)) {
            return false;
        }
        SessionConfig.OutputConfig outputConfig = (SessionConfig.OutputConfig) obj;
        return this.surface.equals(outputConfig.getSurface()) && this.sharedSurfaces.equals(outputConfig.getSharedSurfaces()) && ((str = this.physicalCameraId) != null ? str.equals(outputConfig.getPhysicalCameraId()) : outputConfig.getPhysicalCameraId() == null) && this.surfaceGroupId == outputConfig.getSurfaceGroupId();
    }

    @Override // androidx.camera.core.impl.SessionConfig.OutputConfig
    @Nullable
    public String getPhysicalCameraId() {
        return this.physicalCameraId;
    }

    @Override // androidx.camera.core.impl.SessionConfig.OutputConfig
    @NonNull
    public List<DeferrableSurface> getSharedSurfaces() {
        return this.sharedSurfaces;
    }

    @Override // androidx.camera.core.impl.SessionConfig.OutputConfig
    @NonNull
    public DeferrableSurface getSurface() {
        return this.surface;
    }

    @Override // androidx.camera.core.impl.SessionConfig.OutputConfig
    public int getSurfaceGroupId() {
        return this.surfaceGroupId;
    }

    public int hashCode() {
        int iHashCode = (((this.surface.hashCode() ^ 1000003) * 1000003) ^ this.sharedSurfaces.hashCode()) * 1000003;
        String str = this.physicalCameraId;
        return ((iHashCode ^ (str == null ? 0 : str.hashCode())) * 1000003) ^ this.surfaceGroupId;
    }

    public String toString() {
        return "OutputConfig{surface=" + this.surface + ", sharedSurfaces=" + this.sharedSurfaces + ", physicalCameraId=" + this.physicalCameraId + ", surfaceGroupId=" + this.surfaceGroupId + "}";
    }

    private AutoValue_SessionConfig_OutputConfig(DeferrableSurface deferrableSurface, List<DeferrableSurface> list, @Nullable String str, int i2) {
        this.surface = deferrableSurface;
        this.sharedSurfaces = list;
        this.physicalCameraId = str;
        this.surfaceGroupId = i2;
    }
}
