package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.ErasureProjectionComputer;
import kotlin.reflect.jvm.internal.impl.types.ErasureTypeAttributes;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeParameterUpperBoundEraser;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class RawProjectionComputer extends ErasureProjectionComputer {

    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JavaTypeFlexibility.values().length];
            try {
                iArr[JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JavaTypeFlexibility.FLEXIBLE_UPPER_BOUND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JavaTypeFlexibility.INFLEXIBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.ErasureProjectionComputer
    @NotNull
    public TypeProjection computeProjection(@NotNull TypeParameterDescriptor parameter, @NotNull ErasureTypeAttributes typeAttr, @NotNull TypeParameterUpperBoundEraser typeParameterUpperBoundEraser, @NotNull KotlinType erasedUpperBound) {
        TypeProjection typeProjectionImpl;
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(typeAttr, "typeAttr");
        Intrinsics.checkNotNullParameter(typeParameterUpperBoundEraser, "typeParameterUpperBoundEraser");
        Intrinsics.checkNotNullParameter(erasedUpperBound, "erasedUpperBound");
        if (!(typeAttr instanceof JavaTypeAttributes)) {
            return super.computeProjection(parameter, typeAttr, typeParameterUpperBoundEraser, erasedUpperBound);
        }
        JavaTypeAttributes javaTypeAttributesWithFlexibility = (JavaTypeAttributes) typeAttr;
        if (!javaTypeAttributesWithFlexibility.isRaw()) {
            javaTypeAttributesWithFlexibility = javaTypeAttributesWithFlexibility.withFlexibility(JavaTypeFlexibility.INFLEXIBLE);
        }
        int i2 = WhenMappings.$EnumSwitchMapping$0[javaTypeAttributesWithFlexibility.getFlexibility().ordinal()];
        if (i2 == 1) {
            return new TypeProjectionImpl(Variance.INVARIANT, erasedUpperBound);
        }
        if (i2 != 2 && i2 != 3) {
            throw new NoWhenBranchMatchedException();
        }
        if (parameter.getVariance().getAllowsOutPosition()) {
            List<TypeParameterDescriptor> parameters = erasedUpperBound.getConstructor().getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "erasedUpperBound.constructor.parameters");
            typeProjectionImpl = parameters.isEmpty() ^ true ? new TypeProjectionImpl(Variance.OUT_VARIANCE, erasedUpperBound) : TypeUtils.makeStarProjection(parameter, javaTypeAttributesWithFlexibility);
        } else {
            typeProjectionImpl = new TypeProjectionImpl(Variance.INVARIANT, DescriptorUtilsKt.getBuiltIns(parameter).getNothingType());
        }
        Intrinsics.checkNotNullExpressionValue(typeProjectionImpl, "{\n                if (!p…          }\n            }");
        return typeProjectionImpl;
    }
}
