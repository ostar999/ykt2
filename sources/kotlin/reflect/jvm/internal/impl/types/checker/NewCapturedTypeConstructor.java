package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class NewCapturedTypeConstructor implements CapturedTypeConstructor {

    @NotNull
    private final Lazy _supertypes$delegate;

    @Nullable
    private final NewCapturedTypeConstructor original;

    @NotNull
    private final TypeProjection projection;

    @Nullable
    private Function0<? extends List<? extends UnwrappedType>> supertypesComputation;

    @Nullable
    private final TypeParameterDescriptor typeParameter;

    public NewCapturedTypeConstructor(@NotNull TypeProjection projection, @Nullable Function0<? extends List<? extends UnwrappedType>> function0, @Nullable NewCapturedTypeConstructor newCapturedTypeConstructor, @Nullable TypeParameterDescriptor typeParameterDescriptor) {
        Intrinsics.checkNotNullParameter(projection, "projection");
        this.projection = projection;
        this.supertypesComputation = function0;
        this.original = newCapturedTypeConstructor;
        this.typeParameter = typeParameterDescriptor;
        this._supertypes$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends UnwrappedType>>() { // from class: kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedTypeConstructor$_supertypes$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @Nullable
            public final List<? extends UnwrappedType> invoke() {
                Function0 function02 = this.this$0.supertypesComputation;
                if (function02 != null) {
                    return (List) function02.invoke();
                }
                return null;
            }
        });
    }

    private final List<UnwrappedType> get_supertypes() {
        return (List) this._supertypes$delegate.getValue();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(NewCapturedTypeConstructor.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor");
        NewCapturedTypeConstructor newCapturedTypeConstructor = (NewCapturedTypeConstructor) obj;
        NewCapturedTypeConstructor newCapturedTypeConstructor2 = this.original;
        if (newCapturedTypeConstructor2 == null) {
            newCapturedTypeConstructor2 = this;
        }
        NewCapturedTypeConstructor newCapturedTypeConstructor3 = newCapturedTypeConstructor.original;
        if (newCapturedTypeConstructor3 != null) {
            newCapturedTypeConstructor = newCapturedTypeConstructor3;
        }
        return newCapturedTypeConstructor2 == newCapturedTypeConstructor;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    @NotNull
    public KotlinBuiltIns getBuiltIns() {
        KotlinType type = getProjection().getType();
        Intrinsics.checkNotNullExpressionValue(type, "projection.type");
        return TypeUtilsKt.getBuiltIns(type);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    @Nullable
    /* renamed from: getDeclarationDescriptor */
    public ClassifierDescriptor mo2086getDeclarationDescriptor() {
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    @NotNull
    public List<TypeParameterDescriptor> getParameters() {
        return CollectionsKt__CollectionsKt.emptyList();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructor
    @NotNull
    public TypeProjection getProjection() {
        return this.projection;
    }

    public int hashCode() {
        NewCapturedTypeConstructor newCapturedTypeConstructor = this.original;
        return newCapturedTypeConstructor != null ? newCapturedTypeConstructor.hashCode() : super.hashCode();
    }

    public final void initializeSupertypes(@NotNull final List<? extends UnwrappedType> supertypes) {
        Intrinsics.checkNotNullParameter(supertypes, "supertypes");
        this.supertypesComputation = new Function0<List<? extends UnwrappedType>>() { // from class: kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedTypeConstructor.initializeSupertypes.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final List<? extends UnwrappedType> invoke() {
                return supertypes;
            }
        };
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    public boolean isDenotable() {
        return false;
    }

    @NotNull
    public String toString() {
        return "CapturedType(" + getProjection() + ')';
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    @NotNull
    /* renamed from: getSupertypes */
    public List<UnwrappedType> mo2087getSupertypes() {
        List<UnwrappedType> list = get_supertypes();
        return list == null ? CollectionsKt__CollectionsKt.emptyList() : list;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    @NotNull
    public NewCapturedTypeConstructor refine(@NotNull final KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        TypeProjection typeProjectionRefine = getProjection().refine(kotlinTypeRefiner);
        Intrinsics.checkNotNullExpressionValue(typeProjectionRefine, "projection.refine(kotlinTypeRefiner)");
        Function0<List<? extends UnwrappedType>> function0 = this.supertypesComputation != null ? new Function0<List<? extends UnwrappedType>>() { // from class: kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedTypeConstructor$refine$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final List<? extends UnwrappedType> invoke() {
                List<UnwrappedType> listMo2087getSupertypes = this.this$0.mo2087getSupertypes();
                KotlinTypeRefiner kotlinTypeRefiner2 = kotlinTypeRefiner;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listMo2087getSupertypes, 10));
                Iterator<T> it = listMo2087getSupertypes.iterator();
                while (it.hasNext()) {
                    arrayList.add(((UnwrappedType) it.next()).refine(kotlinTypeRefiner2));
                }
                return arrayList;
            }
        } : null;
        NewCapturedTypeConstructor newCapturedTypeConstructor = this.original;
        if (newCapturedTypeConstructor == null) {
            newCapturedTypeConstructor = this;
        }
        return new NewCapturedTypeConstructor(typeProjectionRefine, function0, newCapturedTypeConstructor, this.typeParameter);
    }

    public /* synthetic */ NewCapturedTypeConstructor(TypeProjection typeProjection, Function0 function0, NewCapturedTypeConstructor newCapturedTypeConstructor, TypeParameterDescriptor typeParameterDescriptor, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeProjection, (i2 & 2) != 0 ? null : function0, (i2 & 4) != 0 ? null : newCapturedTypeConstructor, (i2 & 8) != 0 ? null : typeParameterDescriptor);
    }

    public /* synthetic */ NewCapturedTypeConstructor(TypeProjection typeProjection, List list, NewCapturedTypeConstructor newCapturedTypeConstructor, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeProjection, list, (i2 & 4) != 0 ? null : newCapturedTypeConstructor);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NewCapturedTypeConstructor(@NotNull TypeProjection projection, @NotNull final List<? extends UnwrappedType> supertypes, @Nullable NewCapturedTypeConstructor newCapturedTypeConstructor) {
        this(projection, new Function0<List<? extends UnwrappedType>>() { // from class: kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedTypeConstructor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final List<? extends UnwrappedType> invoke() {
                return supertypes;
            }
        }, newCapturedTypeConstructor, null, 8, null);
        Intrinsics.checkNotNullParameter(projection, "projection");
        Intrinsics.checkNotNullParameter(supertypes, "supertypes");
    }
}
