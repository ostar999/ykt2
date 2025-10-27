package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class NameResolverUtilKt {
    @NotNull
    public static final ClassId getClassId(@NotNull NameResolver nameResolver, int i2) {
        Intrinsics.checkNotNullParameter(nameResolver, "<this>");
        ClassId classIdFromString = ClassId.fromString(nameResolver.getQualifiedClassName(i2), nameResolver.isLocalClassName(i2));
        Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(getQualifiedC… isLocalClassName(index))");
        return classIdFromString;
    }

    @NotNull
    public static final Name getName(@NotNull NameResolver nameResolver, int i2) {
        Intrinsics.checkNotNullParameter(nameResolver, "<this>");
        Name nameGuessByFirstCharacter = Name.guessByFirstCharacter(nameResolver.getString(i2));
        Intrinsics.checkNotNullExpressionValue(nameGuessByFirstCharacter, "guessByFirstCharacter(getString(index))");
        return nameGuessByFirstCharacter;
    }
}
