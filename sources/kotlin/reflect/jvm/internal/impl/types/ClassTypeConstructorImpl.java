package kotlin.reflect.jvm.internal.impl.types;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public class ClassTypeConstructorImpl extends AbstractClassTypeConstructor implements TypeConstructor {
    private final ClassDescriptor classDescriptor;
    private final List<TypeParameterDescriptor> parameters;
    private final Collection<KotlinType> supertypes;

    private static /* synthetic */ void $$$reportNull$$$0(int i2) {
        String str = (i2 == 4 || i2 == 5 || i2 == 6 || i2 == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i2 == 4 || i2 == 5 || i2 == 6 || i2 == 7) ? 2 : 3];
        switch (i2) {
            case 1:
                objArr[0] = PushConstants.PARAMS;
                break;
            case 2:
                objArr[0] = "supertypes";
                break;
            case 3:
                objArr[0] = "storageManager";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/types/ClassTypeConstructorImpl";
                break;
            default:
                objArr[0] = "classDescriptor";
                break;
        }
        if (i2 == 4) {
            objArr[1] = "getParameters";
        } else if (i2 == 5) {
            objArr[1] = "getDeclarationDescriptor";
        } else if (i2 == 6) {
            objArr[1] = "computeSupertypes";
        } else if (i2 != 7) {
            objArr[1] = "kotlin/reflect/jvm/internal/impl/types/ClassTypeConstructorImpl";
        } else {
            objArr[1] = "getSupertypeLoopChecker";
        }
        if (i2 != 4 && i2 != 5 && i2 != 6 && i2 != 7) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i2 != 4 && i2 != 5 && i2 != 6 && i2 != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassTypeConstructorImpl(@NotNull ClassDescriptor classDescriptor, @NotNull List<? extends TypeParameterDescriptor> list, @NotNull Collection<KotlinType> collection, @NotNull StorageManager storageManager) {
        super(storageManager);
        if (classDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        if (collection == null) {
            $$$reportNull$$$0(2);
        }
        if (storageManager == null) {
            $$$reportNull$$$0(3);
        }
        this.classDescriptor = classDescriptor;
        this.parameters = Collections.unmodifiableList(new ArrayList(list));
        this.supertypes = Collections.unmodifiableCollection(collection);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor
    @NotNull
    public Collection<KotlinType> computeSupertypes() {
        Collection<KotlinType> collection = this.supertypes;
        if (collection == null) {
            $$$reportNull$$$0(6);
        }
        return collection;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    @NotNull
    public List<TypeParameterDescriptor> getParameters() {
        List<TypeParameterDescriptor> list = this.parameters;
        if (list == null) {
            $$$reportNull$$$0(4);
        }
        return list;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor
    @NotNull
    public SupertypeLoopChecker getSupertypeLoopChecker() {
        SupertypeLoopChecker.EMPTY empty = SupertypeLoopChecker.EMPTY.INSTANCE;
        if (empty == null) {
            $$$reportNull$$$0(7);
        }
        return empty;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    public boolean isDenotable() {
        return true;
    }

    public String toString() {
        return DescriptorUtils.getFqName(this.classDescriptor).asString();
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractClassTypeConstructor, kotlin.reflect.jvm.internal.impl.types.ClassifierBasedTypeConstructor, kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    @NotNull
    /* renamed from: getDeclarationDescriptor */
    public ClassDescriptor mo2086getDeclarationDescriptor() {
        ClassDescriptor classDescriptor = this.classDescriptor;
        if (classDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        return classDescriptor;
    }
}
