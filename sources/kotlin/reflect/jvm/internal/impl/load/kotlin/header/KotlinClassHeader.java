package kotlin.reflect.jvm.internal.impl.load.kotlin.header;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nKotlinClassHeader.kt\nKotlin\n*S Kotlin\n*F\n+ 1 KotlinClassHeader.kt\norg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,78:1\n1#2:79\n*E\n"})
/* loaded from: classes8.dex */
public final class KotlinClassHeader {

    @Nullable
    private final String[] data;
    private final int extraInt;

    @Nullable
    private final String extraString;

    @Nullable
    private final String[] incompatibleData;

    @NotNull
    private final Kind kind;

    @NotNull
    private final JvmMetadataVersion metadataVersion;

    @Nullable
    private final String packageName;

    @Nullable
    private final byte[] serializedIr;

    @Nullable
    private final String[] strings;

    @SourceDebugExtension({"SMAP\nKotlinClassHeader.kt\nKotlin\n*S Kotlin\n*F\n+ 1 KotlinClassHeader.kt\norg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,78:1\n8811#2,2:79\n9071#2,4:81\n*S KotlinDebug\n*F\n+ 1 KotlinClassHeader.kt\norg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind\n*L\n34#1:79,2\n34#1:81,4\n*E\n"})
    public enum Kind {
        UNKNOWN(0),
        CLASS(1),
        FILE_FACADE(2),
        SYNTHETIC_CLASS(3),
        MULTIFILE_CLASS(4),
        MULTIFILE_CLASS_PART(5);


        @NotNull
        public static final Companion Companion = new Companion(null);

        @NotNull
        private static final Map<Integer, Kind> entryById;
        private final int id;

        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            @JvmStatic
            @NotNull
            public final Kind getById(int i2) {
                Kind kind = (Kind) Kind.entryById.get(Integer.valueOf(i2));
                return kind == null ? Kind.UNKNOWN : kind;
            }
        }

        static {
            Kind[] kindArrValues = values();
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(kindArrValues.length), 16));
            for (Kind kind : kindArrValues) {
                linkedHashMap.put(Integer.valueOf(kind.id), kind);
            }
            entryById = linkedHashMap;
        }

        Kind(int i2) {
            this.id = i2;
        }

        @JvmStatic
        @NotNull
        public static final Kind getById(int i2) {
            return Companion.getById(i2);
        }
    }

    public KotlinClassHeader(@NotNull Kind kind, @NotNull JvmMetadataVersion metadataVersion, @Nullable String[] strArr, @Nullable String[] strArr2, @Nullable String[] strArr3, @Nullable String str, int i2, @Nullable String str2, @Nullable byte[] bArr) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        this.kind = kind;
        this.metadataVersion = metadataVersion;
        this.data = strArr;
        this.incompatibleData = strArr2;
        this.strings = strArr3;
        this.extraString = str;
        this.extraInt = i2;
        this.packageName = str2;
        this.serializedIr = bArr;
    }

    private final boolean has(int i2, int i3) {
        return (i2 & i3) != 0;
    }

    @Nullable
    public final String[] getData() {
        return this.data;
    }

    @Nullable
    public final String[] getIncompatibleData() {
        return this.incompatibleData;
    }

    @NotNull
    public final Kind getKind() {
        return this.kind;
    }

    @NotNull
    public final JvmMetadataVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    @Nullable
    public final String getMultifileClassName() {
        String str = this.extraString;
        if (this.kind == Kind.MULTIFILE_CLASS_PART) {
            return str;
        }
        return null;
    }

    @NotNull
    public final List<String> getMultifilePartNames() {
        String[] strArr = this.data;
        if (!(this.kind == Kind.MULTIFILE_CLASS)) {
            strArr = null;
        }
        List<String> listAsList = strArr != null ? ArraysKt___ArraysJvmKt.asList(strArr) : null;
        return listAsList == null ? CollectionsKt__CollectionsKt.emptyList() : listAsList;
    }

    @Nullable
    public final String[] getStrings() {
        return this.strings;
    }

    public final boolean isPreRelease() {
        return has(this.extraInt, 2);
    }

    public final boolean isUnstableFirBinary() {
        return has(this.extraInt, 64) && !has(this.extraInt, 32);
    }

    public final boolean isUnstableJvmIrBinary() {
        return has(this.extraInt, 16) && !has(this.extraInt, 32);
    }

    @NotNull
    public String toString() {
        return this.kind + " version=" + this.metadataVersion;
    }
}
