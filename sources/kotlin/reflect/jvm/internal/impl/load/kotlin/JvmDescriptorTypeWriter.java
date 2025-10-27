package kotlin.reflect.jvm.internal.impl.load.kotlin;

import cn.hutool.core.text.StrPool;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public class JvmDescriptorTypeWriter<T> {

    @Nullable
    private T jvmCurrentType;
    private int jvmCurrentTypeArrayLevel;

    @NotNull
    private final JvmTypeFactory<T> jvmTypeFactory;

    public void writeArrayEnd() {
    }

    public void writeArrayType() {
        if (this.jvmCurrentType == null) {
            this.jvmCurrentTypeArrayLevel++;
        }
    }

    public void writeClass(@NotNull T objectType) {
        Intrinsics.checkNotNullParameter(objectType, "objectType");
        writeJvmTypeAsIs(objectType);
    }

    public final void writeJvmTypeAsIs(@NotNull T type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (this.jvmCurrentType == null) {
            if (this.jvmCurrentTypeArrayLevel > 0) {
                type = this.jvmTypeFactory.createFromString(StringsKt__StringsJVMKt.repeat(StrPool.BRACKET_START, this.jvmCurrentTypeArrayLevel) + this.jvmTypeFactory.toString(type));
            }
            this.jvmCurrentType = type;
        }
    }

    public void writeTypeVariable(@NotNull Name name, @NotNull T type) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        writeJvmTypeAsIs(type);
    }
}
