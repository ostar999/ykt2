package kotlin.reflect.jvm.internal.impl.name;

import cn.hutool.core.text.StrPool;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class CallableId {

    @NotNull
    private static final Companion Companion = new Companion(null);

    @NotNull
    private static final Name LOCAL_NAME;

    @NotNull
    private static final FqName PACKAGE_FQ_NAME_FOR_LOCAL;

    @NotNull
    private final Name callableName;

    @Nullable
    private final FqName className;

    @NotNull
    private final FqName packageName;

    @Nullable
    private final FqName pathToLocal;

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        Name name = SpecialNames.LOCAL;
        LOCAL_NAME = name;
        FqName fqName = FqName.topLevel(name);
        Intrinsics.checkNotNullExpressionValue(fqName, "topLevel(LOCAL_NAME)");
        PACKAGE_FQ_NAME_FOR_LOCAL = fqName;
    }

    public CallableId(@NotNull FqName packageName, @Nullable FqName fqName, @NotNull Name callableName, @Nullable FqName fqName2) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(callableName, "callableName");
        this.packageName = packageName;
        this.className = fqName;
        this.callableName = callableName;
        this.pathToLocal = fqName2;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CallableId)) {
            return false;
        }
        CallableId callableId = (CallableId) obj;
        return Intrinsics.areEqual(this.packageName, callableId.packageName) && Intrinsics.areEqual(this.className, callableId.className) && Intrinsics.areEqual(this.callableName, callableId.callableName) && Intrinsics.areEqual(this.pathToLocal, callableId.pathToLocal);
    }

    public int hashCode() {
        int iHashCode = this.packageName.hashCode() * 31;
        FqName fqName = this.className;
        int iHashCode2 = (((iHashCode + (fqName == null ? 0 : fqName.hashCode())) * 31) + this.callableName.hashCode()) * 31;
        FqName fqName2 = this.pathToLocal;
        return iHashCode2 + (fqName2 != null ? fqName2.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String strAsString = this.packageName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "packageName.asString()");
        sb.append(StringsKt__StringsJVMKt.replace$default(strAsString, '.', '/', false, 4, (Object) null));
        sb.append("/");
        FqName fqName = this.className;
        if (fqName != null) {
            sb.append(fqName);
            sb.append(StrPool.DOT);
        }
        sb.append(this.callableName);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    public /* synthetic */ CallableId(FqName fqName, FqName fqName2, Name name, FqName fqName3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(fqName, fqName2, name, (i2 & 8) != 0 ? null : fqName3);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CallableId(@NotNull FqName packageName, @NotNull Name callableName) {
        this(packageName, null, callableName, null, 8, null);
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(callableName, "callableName");
    }
}
