package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public class TypeParameterDescriptorImpl extends AbstractTypeParameterDescriptor {
    private boolean initialized;

    @Nullable
    private final Function1<KotlinType, Void> reportCycleError;
    private final List<KotlinType> upperBounds;

    private static /* synthetic */ void $$$reportNull$$$0(int i2) {
        String str = (i2 == 5 || i2 == 28) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i2 == 5 || i2 == 28) ? 2 : 3];
        switch (i2) {
            case 1:
            case 7:
            case 13:
            case 20:
                objArr[0] = "annotations";
                break;
            case 2:
            case 8:
            case 14:
            case 21:
                objArr[0] = "variance";
                break;
            case 3:
            case 9:
            case 15:
            case 22:
                objArr[0] = "name";
                break;
            case 4:
            case 11:
            case 18:
            case 25:
                objArr[0] = "storageManager";
                break;
            case 5:
            case 28:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/TypeParameterDescriptorImpl";
                break;
            case 6:
            case 12:
            case 19:
            default:
                objArr[0] = "containingDeclaration";
                break;
            case 10:
            case 16:
            case 23:
                objArr[0] = SocialConstants.PARAM_SOURCE;
                break;
            case 17:
                objArr[0] = "supertypeLoopsResolver";
                break;
            case 24:
                objArr[0] = "supertypeLoopsChecker";
                break;
            case 26:
                objArr[0] = "bound";
                break;
            case 27:
                objArr[0] = "type";
                break;
        }
        if (i2 == 5) {
            objArr[1] = "createWithDefaultBound";
        } else if (i2 != 28) {
            objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/TypeParameterDescriptorImpl";
        } else {
            objArr[1] = "resolveUpperBounds";
        }
        switch (i2) {
            case 5:
            case 28:
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[2] = "createForFurtherModification";
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                objArr[2] = "<init>";
                break;
            case 26:
                objArr[2] = "addUpperBound";
                break;
            case 27:
                objArr[2] = "reportSupertypeLoopError";
                break;
            default:
                objArr[2] = "createWithDefaultBound";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i2 != 5 && i2 != 28) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private TypeParameterDescriptorImpl(@NotNull DeclarationDescriptor declarationDescriptor, @NotNull Annotations annotations, boolean z2, @NotNull Variance variance, @NotNull Name name, int i2, @NotNull SourceElement sourceElement, @Nullable Function1<KotlinType, Void> function1, @NotNull SupertypeLoopChecker supertypeLoopChecker, @NotNull StorageManager storageManager) {
        super(storageManager, declarationDescriptor, annotations, name, variance, z2, i2, sourceElement, supertypeLoopChecker);
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(19);
        }
        if (annotations == null) {
            $$$reportNull$$$0(20);
        }
        if (variance == null) {
            $$$reportNull$$$0(21);
        }
        if (name == null) {
            $$$reportNull$$$0(22);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(23);
        }
        if (supertypeLoopChecker == null) {
            $$$reportNull$$$0(24);
        }
        if (storageManager == null) {
            $$$reportNull$$$0(25);
        }
        this.upperBounds = new ArrayList(1);
        this.initialized = false;
        this.reportCycleError = function1;
    }

    private void checkInitialized() {
        if (this.initialized) {
            return;
        }
        throw new IllegalStateException("Type parameter descriptor is not initialized: " + nameForAssertions());
    }

    private void checkUninitialized() {
        if (this.initialized) {
            throw new IllegalStateException("Type parameter descriptor is already initialized: " + nameForAssertions());
        }
    }

    public static TypeParameterDescriptorImpl createForFurtherModification(@NotNull DeclarationDescriptor declarationDescriptor, @NotNull Annotations annotations, boolean z2, @NotNull Variance variance, @NotNull Name name, int i2, @NotNull SourceElement sourceElement, @NotNull StorageManager storageManager) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        if (annotations == null) {
            $$$reportNull$$$0(7);
        }
        if (variance == null) {
            $$$reportNull$$$0(8);
        }
        if (name == null) {
            $$$reportNull$$$0(9);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(10);
        }
        if (storageManager == null) {
            $$$reportNull$$$0(11);
        }
        return createForFurtherModification(declarationDescriptor, annotations, z2, variance, name, i2, sourceElement, null, SupertypeLoopChecker.EMPTY.INSTANCE, storageManager);
    }

    @NotNull
    public static TypeParameterDescriptor createWithDefaultBound(@NotNull DeclarationDescriptor declarationDescriptor, @NotNull Annotations annotations, boolean z2, @NotNull Variance variance, @NotNull Name name, int i2, @NotNull StorageManager storageManager) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (annotations == null) {
            $$$reportNull$$$0(1);
        }
        if (variance == null) {
            $$$reportNull$$$0(2);
        }
        if (name == null) {
            $$$reportNull$$$0(3);
        }
        if (storageManager == null) {
            $$$reportNull$$$0(4);
        }
        TypeParameterDescriptorImpl typeParameterDescriptorImplCreateForFurtherModification = createForFurtherModification(declarationDescriptor, annotations, z2, variance, name, i2, SourceElement.NO_SOURCE, storageManager);
        typeParameterDescriptorImplCreateForFurtherModification.addUpperBound(DescriptorUtilsKt.getBuiltIns(declarationDescriptor).getDefaultBound());
        typeParameterDescriptorImplCreateForFurtherModification.setInitialized();
        return typeParameterDescriptorImplCreateForFurtherModification;
    }

    private void doAddUpperBound(KotlinType kotlinType) {
        if (KotlinTypeKt.isError(kotlinType)) {
            return;
        }
        this.upperBounds.add(kotlinType);
    }

    private String nameForAssertions() {
        return getName() + " declared in " + DescriptorUtils.getFqName(getContainingDeclaration());
    }

    public void addUpperBound(@NotNull KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(26);
        }
        checkUninitialized();
        doAddUpperBound(kotlinType);
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractTypeParameterDescriptor
    /* renamed from: reportSupertypeLoopError */
    public void mo2089reportSupertypeLoopError(@NotNull KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(27);
        }
        Function1<KotlinType, Void> function1 = this.reportCycleError;
        if (function1 == null) {
            return;
        }
        function1.invoke(kotlinType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractTypeParameterDescriptor
    @NotNull
    public List<KotlinType> resolveUpperBounds() {
        checkInitialized();
        List<KotlinType> list = this.upperBounds;
        if (list == null) {
            $$$reportNull$$$0(28);
        }
        return list;
    }

    public void setInitialized() {
        checkUninitialized();
        this.initialized = true;
    }

    public static TypeParameterDescriptorImpl createForFurtherModification(@NotNull DeclarationDescriptor declarationDescriptor, @NotNull Annotations annotations, boolean z2, @NotNull Variance variance, @NotNull Name name, int i2, @NotNull SourceElement sourceElement, @Nullable Function1<KotlinType, Void> function1, @NotNull SupertypeLoopChecker supertypeLoopChecker, @NotNull StorageManager storageManager) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(12);
        }
        if (annotations == null) {
            $$$reportNull$$$0(13);
        }
        if (variance == null) {
            $$$reportNull$$$0(14);
        }
        if (name == null) {
            $$$reportNull$$$0(15);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(16);
        }
        if (supertypeLoopChecker == null) {
            $$$reportNull$$$0(17);
        }
        if (storageManager == null) {
            $$$reportNull$$$0(18);
        }
        return new TypeParameterDescriptorImpl(declarationDescriptor, annotations, z2, variance, name, i2, sourceElement, function1, supertypeLoopChecker, storageManager);
    }
}
