package kotlin.reflect.jvm.internal.impl.builtins.functions;

import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'KFunction' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:372)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:337)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:322)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:293)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:266)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes8.dex */
public final class FunctionClassKind {
    private static final /* synthetic */ FunctionClassKind[] $VALUES;

    @NotNull
    public static final Companion Companion;
    public static final FunctionClassKind KFunction;
    public static final FunctionClassKind KSuspendFunction;

    @NotNull
    private final String classNamePrefix;
    private final boolean isReflectType;
    private final boolean isSuspendType;

    @NotNull
    private final FqName packageFqName;
    public static final FunctionClassKind Function = new FunctionClassKind("Function", 0, StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Function", false, false);
    public static final FunctionClassKind SuspendFunction = new FunctionClassKind("SuspendFunction", 1, StandardNames.COROUTINES_PACKAGE_FQ_NAME, "SuspendFunction", true, false);

    @SourceDebugExtension({"SMAP\nFunctionClassKind.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FunctionClassKind.kt\norg/jetbrains/kotlin/builtins/functions/FunctionClassKind$Companion\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,59:1\n1282#2,2:60\n*S KotlinDebug\n*F\n+ 1 FunctionClassKind.kt\norg/jetbrains/kotlin/builtins/functions/FunctionClassKind$Companion\n*L\n27#1:60,2\n*E\n"})
    public static final class Companion {

        public static final class KindWithArity {
            private final int arity;

            @NotNull
            private final FunctionClassKind kind;

            public KindWithArity(@NotNull FunctionClassKind kind, int i2) {
                Intrinsics.checkNotNullParameter(kind, "kind");
                this.kind = kind;
                this.arity = i2;
            }

            @NotNull
            public final FunctionClassKind component1() {
                return this.kind;
            }

            public final int component2() {
                return this.arity;
            }

            public boolean equals(@Nullable Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof KindWithArity)) {
                    return false;
                }
                KindWithArity kindWithArity = (KindWithArity) obj;
                return this.kind == kindWithArity.kind && this.arity == kindWithArity.arity;
            }

            @NotNull
            public final FunctionClassKind getKind() {
                return this.kind;
            }

            public int hashCode() {
                return (this.kind.hashCode() * 31) + this.arity;
            }

            @NotNull
            public String toString() {
                return "KindWithArity(kind=" + this.kind + ", arity=" + this.arity + ')';
            }
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final Integer toInt(String str) {
            if (str.length() == 0) {
                return null;
            }
            int length = str.length();
            int i2 = 0;
            for (int i3 = 0; i3 < length; i3++) {
                int iCharAt = str.charAt(i3) - '0';
                if (!(iCharAt >= 0 && iCharAt < 10)) {
                    return null;
                }
                i2 = (i2 * 10) + iCharAt;
            }
            return Integer.valueOf(i2);
        }

        @Nullable
        public final FunctionClassKind byClassNamePrefix(@NotNull FqName packageFqName, @NotNull String className) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(className, "className");
            for (FunctionClassKind functionClassKind : FunctionClassKind.values()) {
                if (Intrinsics.areEqual(functionClassKind.getPackageFqName(), packageFqName) && StringsKt__StringsJVMKt.startsWith$default(className, functionClassKind.getClassNamePrefix(), false, 2, null)) {
                    return functionClassKind;
                }
            }
            return null;
        }

        @JvmStatic
        @Nullable
        public final FunctionClassKind getFunctionalClassKind(@NotNull String className, @NotNull FqName packageFqName) {
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            KindWithArity className2 = parseClassName(className, packageFqName);
            if (className2 != null) {
                return className2.getKind();
            }
            return null;
        }

        @Nullable
        public final KindWithArity parseClassName(@NotNull String className, @NotNull FqName packageFqName) {
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            FunctionClassKind functionClassKindByClassNamePrefix = byClassNamePrefix(packageFqName, className);
            if (functionClassKindByClassNamePrefix == null) {
                return null;
            }
            String strSubstring = className.substring(functionClassKindByClassNamePrefix.getClassNamePrefix().length());
            Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
            Integer num = toInt(strSubstring);
            if (num != null) {
                return new KindWithArity(functionClassKindByClassNamePrefix, num.intValue());
            }
            return null;
        }
    }

    private static final /* synthetic */ FunctionClassKind[] $values() {
        return new FunctionClassKind[]{Function, SuspendFunction, KFunction, KSuspendFunction};
    }

    static {
        FqName fqName = StandardNames.KOTLIN_REFLECT_FQ_NAME;
        KFunction = new FunctionClassKind("KFunction", 2, fqName, "KFunction", false, true);
        KSuspendFunction = new FunctionClassKind("KSuspendFunction", 3, fqName, "KSuspendFunction", true, true);
        $VALUES = $values();
        Companion = new Companion(null);
    }

    private FunctionClassKind(String str, int i2, FqName fqName, String str2, boolean z2, boolean z3) {
        this.packageFqName = fqName;
        this.classNamePrefix = str2;
        this.isSuspendType = z2;
        this.isReflectType = z3;
    }

    public static FunctionClassKind valueOf(String str) {
        return (FunctionClassKind) Enum.valueOf(FunctionClassKind.class, str);
    }

    public static FunctionClassKind[] values() {
        return (FunctionClassKind[]) $VALUES.clone();
    }

    @NotNull
    public final String getClassNamePrefix() {
        return this.classNamePrefix;
    }

    @NotNull
    public final FqName getPackageFqName() {
        return this.packageFqName;
    }

    @NotNull
    public final Name numberedClassName(int i2) {
        Name nameIdentifier = Name.identifier(this.classNamePrefix + i2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(\"$classNamePrefix$arity\")");
        return nameIdentifier;
    }
}
