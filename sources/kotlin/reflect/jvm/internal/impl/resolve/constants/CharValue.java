package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class CharValue extends IntegerValueConstant<Character> {
    public CharValue(char c3) {
        super(Character.valueOf(c3));
    }

    private final String getPrintablePart(char c3) {
        return c3 == '\b' ? "\\b" : c3 == '\t' ? "\\t" : c3 == '\n' ? "\\n" : c3 == '\f' ? "\\f" : c3 == '\r' ? "\\r" : isPrintableUnicode(c3) ? String.valueOf(c3) : "?";
    }

    private final boolean isPrintableUnicode(char c3) {
        byte type = (byte) Character.getType(c3);
        return (type == 0 || type == 13 || type == 14 || type == 15 || type == 16 || type == 18 || type == 19) ? false : true;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue
    @NotNull
    public String toString() {
        String str = String.format("\\u%04X ('%s')", Arrays.copyOf(new Object[]{Integer.valueOf(getValue().charValue()), getPrintablePart(getValue().charValue())}, 2));
        Intrinsics.checkNotNullExpressionValue(str, "format(this, *args)");
        return str;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue
    @NotNull
    public SimpleType getType(@NotNull ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        SimpleType charType = module.getBuiltIns().getCharType();
        Intrinsics.checkNotNullExpressionValue(charType, "module.builtIns.charType");
        return charType;
    }
}
