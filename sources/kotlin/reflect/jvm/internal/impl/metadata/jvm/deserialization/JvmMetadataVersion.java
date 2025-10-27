package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.Arrays;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class JvmMetadataVersion extends BinaryVersion {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @JvmField
    @NotNull
    public static final JvmMetadataVersion INSTANCE;

    @JvmField
    @NotNull
    public static final JvmMetadataVersion INSTANCE_NEXT;

    @JvmField
    @NotNull
    public static final JvmMetadataVersion INVALID_VERSION;
    private final boolean isStrictSemantics;

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        JvmMetadataVersion jvmMetadataVersion = new JvmMetadataVersion(1, 8, 0);
        INSTANCE = jvmMetadataVersion;
        INSTANCE_NEXT = jvmMetadataVersion.next();
        INVALID_VERSION = new JvmMetadataVersion(new int[0]);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmMetadataVersion(@NotNull int[] versionArray, boolean z2) {
        super(Arrays.copyOf(versionArray, versionArray.length));
        Intrinsics.checkNotNullParameter(versionArray, "versionArray");
        this.isStrictSemantics = z2;
    }

    private final boolean isCompatibleInternal(JvmMetadataVersion jvmMetadataVersion) {
        if ((getMajor() == 1 && getMinor() == 0) || getMajor() == 0) {
            return false;
        }
        return !newerThan(jvmMetadataVersion);
    }

    private final boolean newerThan(JvmMetadataVersion jvmMetadataVersion) {
        if (getMajor() > jvmMetadataVersion.getMajor()) {
            return true;
        }
        return getMajor() >= jvmMetadataVersion.getMajor() && getMinor() > jvmMetadataVersion.getMinor();
    }

    public final boolean isCompatible(@NotNull JvmMetadataVersion metadataVersionFromLanguageVersion) {
        Intrinsics.checkNotNullParameter(metadataVersionFromLanguageVersion, "metadataVersionFromLanguageVersion");
        if (getMajor() == 2 && getMinor() == 0) {
            JvmMetadataVersion jvmMetadataVersion = INSTANCE;
            if (jvmMetadataVersion.getMajor() == 1 && jvmMetadataVersion.getMinor() == 8) {
                return true;
            }
        }
        return isCompatibleInternal(metadataVersionFromLanguageVersion.lastSupportedVersionWithThisLanguageVersion(this.isStrictSemantics));
    }

    public final boolean isStrictSemantics() {
        return this.isStrictSemantics;
    }

    @NotNull
    public final JvmMetadataVersion lastSupportedVersionWithThisLanguageVersion(boolean z2) {
        JvmMetadataVersion jvmMetadataVersion = z2 ? INSTANCE : INSTANCE_NEXT;
        return jvmMetadataVersion.newerThan(this) ? jvmMetadataVersion : this;
    }

    @NotNull
    public final JvmMetadataVersion next() {
        return (getMajor() == 1 && getMinor() == 9) ? new JvmMetadataVersion(2, 0, 0) : new JvmMetadataVersion(getMajor(), getMinor() + 1, 0);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JvmMetadataVersion(@NotNull int... numbers) {
        this(numbers, false);
        Intrinsics.checkNotNullParameter(numbers, "numbers");
    }
}
