package kotlin.reflect.jvm.internal.impl.load.kotlin;

import com.yikaobang.yixue.R2;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class TypeMappingMode {

    @JvmField
    @NotNull
    public static final TypeMappingMode CLASS_DECLARATION;

    @NotNull
    public static final Companion Companion = new Companion(null);

    @JvmField
    @NotNull
    public static final TypeMappingMode DEFAULT;

    @JvmField
    @NotNull
    public static final TypeMappingMode DEFAULT_UAST;

    @JvmField
    @NotNull
    public static final TypeMappingMode GENERIC_ARGUMENT;

    @JvmField
    @NotNull
    public static final TypeMappingMode GENERIC_ARGUMENT_UAST;

    @JvmField
    @NotNull
    public static final TypeMappingMode RETURN_TYPE_BOXED;

    @JvmField
    @NotNull
    public static final TypeMappingMode SUPER_TYPE;

    @JvmField
    @NotNull
    public static final TypeMappingMode SUPER_TYPE_KOTLIN_COLLECTIONS_AS_IS;

    @JvmField
    @NotNull
    public static final TypeMappingMode VALUE_FOR_ANNOTATION;

    @Nullable
    private final TypeMappingMode genericArgumentMode;

    @Nullable
    private final TypeMappingMode genericContravariantArgumentMode;

    @Nullable
    private final TypeMappingMode genericInvariantArgumentMode;
    private final boolean isForAnnotationParameter;
    private final boolean kotlinCollectionsToJavaCollections;
    private final boolean mapTypeAliases;
    private final boolean needInlineClassWrapping;
    private final boolean needPrimitiveBoxing;
    private final boolean skipDeclarationSiteWildcards;
    private final boolean skipDeclarationSiteWildcardsIfPossible;

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Variance.values().length];
            try {
                iArr[Variance.IN_VARIANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Variance.INVARIANT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        TypeMappingMode typeMappingMode = null;
        boolean z6 = false;
        TypeMappingMode typeMappingMode2 = null;
        TypeMappingMode typeMappingMode3 = null;
        boolean z7 = false;
        DefaultConstructorMarker defaultConstructorMarker = null;
        TypeMappingMode typeMappingMode4 = new TypeMappingMode(z2, false, z3, z4, z5, typeMappingMode, z6, typeMappingMode2, typeMappingMode3, z7, 1023, defaultConstructorMarker);
        GENERIC_ARGUMENT = typeMappingMode4;
        boolean z8 = false;
        boolean z9 = false;
        boolean z10 = false;
        boolean z11 = false;
        boolean z12 = false;
        boolean z13 = false;
        TypeMappingMode typeMappingMode5 = null;
        TypeMappingMode typeMappingMode6 = null;
        boolean z14 = true;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        TypeMappingMode typeMappingMode7 = new TypeMappingMode(z8, z9, z10, z11, z12, null, z13, typeMappingMode5, typeMappingMode6, z14, 511, defaultConstructorMarker2);
        GENERIC_ARGUMENT_UAST = typeMappingMode7;
        RETURN_TYPE_BOXED = new TypeMappingMode(z2, true, z3, z4, z5, typeMappingMode, z6, typeMappingMode2, typeMappingMode3, z7, 1021, defaultConstructorMarker);
        int i2 = R2.attr.clear_input;
        DEFAULT = new TypeMappingMode(z2, false, z3, z4, z5, typeMappingMode4, z6, typeMappingMode2, typeMappingMode3, z7, i2, defaultConstructorMarker);
        DEFAULT_UAST = new TypeMappingMode(z8, z9, z10, z11, z12, typeMappingMode7, z13, typeMappingMode5, typeMappingMode6, z14, R2.attr.behavior_hideable, defaultConstructorMarker2);
        CLASS_DECLARATION = new TypeMappingMode(z2, true, z3, z4, z5, typeMappingMode4, z6, typeMappingMode2, typeMappingMode3, z7, i2, defaultConstructorMarker);
        boolean z15 = false;
        boolean z16 = true;
        SUPER_TYPE = new TypeMappingMode(z2, z15, z3, z16, z5, typeMappingMode4, z6, typeMappingMode2, typeMappingMode3, z7, R2.attr.civ_border_color, defaultConstructorMarker);
        SUPER_TYPE_KOTLIN_COLLECTIONS_AS_IS = new TypeMappingMode(z2, z15, z3, z16, z5, typeMappingMode4, z6, typeMappingMode2, typeMappingMode3, z7, R2.attr.checkbox_style_single, defaultConstructorMarker);
        VALUE_FOR_ANNOTATION = new TypeMappingMode(z2, z15, true, false, z5, typeMappingMode4, z6, typeMappingMode2, typeMappingMode3, z7, R2.attr.civ_border_overlay, defaultConstructorMarker);
    }

    public TypeMappingMode() {
        this(false, false, false, false, false, null, false, null, null, false, 1023, null);
    }

    public TypeMappingMode(boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, @Nullable TypeMappingMode typeMappingMode, boolean z7, @Nullable TypeMappingMode typeMappingMode2, @Nullable TypeMappingMode typeMappingMode3, boolean z8) {
        this.needPrimitiveBoxing = z2;
        this.needInlineClassWrapping = z3;
        this.isForAnnotationParameter = z4;
        this.skipDeclarationSiteWildcards = z5;
        this.skipDeclarationSiteWildcardsIfPossible = z6;
        this.genericArgumentMode = typeMappingMode;
        this.kotlinCollectionsToJavaCollections = z7;
        this.genericContravariantArgumentMode = typeMappingMode2;
        this.genericInvariantArgumentMode = typeMappingMode3;
        this.mapTypeAliases = z8;
    }

    public final boolean getKotlinCollectionsToJavaCollections() {
        return this.kotlinCollectionsToJavaCollections;
    }

    public final boolean getMapTypeAliases() {
        return this.mapTypeAliases;
    }

    public final boolean getNeedInlineClassWrapping() {
        return this.needInlineClassWrapping;
    }

    public final boolean getNeedPrimitiveBoxing() {
        return this.needPrimitiveBoxing;
    }

    public final boolean isForAnnotationParameter() {
        return this.isForAnnotationParameter;
    }

    @NotNull
    public final TypeMappingMode toGenericArgumentMode(@NotNull Variance effectiveVariance, boolean z2) {
        Intrinsics.checkNotNullParameter(effectiveVariance, "effectiveVariance");
        if (!z2 || !this.isForAnnotationParameter) {
            int i2 = WhenMappings.$EnumSwitchMapping$0[effectiveVariance.ordinal()];
            if (i2 == 1) {
                TypeMappingMode typeMappingMode = this.genericContravariantArgumentMode;
                if (typeMappingMode != null) {
                    return typeMappingMode;
                }
            } else if (i2 != 2) {
                TypeMappingMode typeMappingMode2 = this.genericArgumentMode;
                if (typeMappingMode2 != null) {
                    return typeMappingMode2;
                }
            } else {
                TypeMappingMode typeMappingMode3 = this.genericInvariantArgumentMode;
                if (typeMappingMode3 != null) {
                    return typeMappingMode3;
                }
            }
        }
        return this;
    }

    @NotNull
    public final TypeMappingMode wrapInlineClassesMode() {
        return new TypeMappingMode(this.needPrimitiveBoxing, true, this.isForAnnotationParameter, this.skipDeclarationSiteWildcards, this.skipDeclarationSiteWildcardsIfPossible, this.genericArgumentMode, this.kotlinCollectionsToJavaCollections, this.genericContravariantArgumentMode, this.genericInvariantArgumentMode, false, 512, null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ TypeMappingMode(boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, TypeMappingMode typeMappingMode, boolean z7, TypeMappingMode typeMappingMode2, TypeMappingMode typeMappingMode3, boolean z8, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        boolean z9 = (i2 & 1) != 0 ? true : z2;
        boolean z10 = (i2 & 2) != 0 ? true : z3;
        boolean z11 = (i2 & 4) != 0 ? false : z4;
        boolean z12 = (i2 & 8) != 0 ? false : z5;
        boolean z13 = (i2 & 16) != 0 ? false : z6;
        TypeMappingMode typeMappingMode4 = (i2 & 32) != 0 ? null : typeMappingMode;
        this(z9, z10, z11, z12, z13, typeMappingMode4, (i2 & 64) == 0 ? z7 : true, (i2 & 128) != 0 ? typeMappingMode4 : typeMappingMode2, (i2 & 256) != 0 ? typeMappingMode4 : typeMappingMode3, (i2 & 512) == 0 ? z8 : false);
    }
}
