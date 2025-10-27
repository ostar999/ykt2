package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension({"SMAP\nInlineClassRepresentation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InlineClassRepresentation.kt\norg/jetbrains/kotlin/descriptors/InlineClassRepresentation\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,25:1\n1#2:26\n*E\n"})
/* loaded from: classes8.dex */
public final class InlineClassRepresentation<Type extends SimpleTypeMarker> extends ValueClassRepresentation<Type> {

    @NotNull
    private final Name underlyingPropertyName;

    @NotNull
    private final Type underlyingType;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InlineClassRepresentation(@NotNull Name underlyingPropertyName, @NotNull Type underlyingType) {
        super(null);
        Intrinsics.checkNotNullParameter(underlyingPropertyName, "underlyingPropertyName");
        Intrinsics.checkNotNullParameter(underlyingType, "underlyingType");
        this.underlyingPropertyName = underlyingPropertyName;
        this.underlyingType = underlyingType;
    }

    @NotNull
    public final Name getUnderlyingPropertyName() {
        return this.underlyingPropertyName;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ValueClassRepresentation
    @NotNull
    public List<Pair<Name, Type>> getUnderlyingPropertyNamesToTypes() {
        return CollectionsKt__CollectionsJVMKt.listOf(TuplesKt.to(this.underlyingPropertyName, this.underlyingType));
    }

    @NotNull
    public final Type getUnderlyingType() {
        return this.underlyingType;
    }

    @NotNull
    public String toString() {
        return "InlineClassRepresentation(underlyingPropertyName=" + this.underlyingPropertyName + ", underlyingType=" + this.underlyingType + ')';
    }
}
