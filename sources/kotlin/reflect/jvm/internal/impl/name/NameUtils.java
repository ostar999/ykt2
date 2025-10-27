package kotlin.reflect.jvm.internal.impl.name;

import cn.hutool.core.text.StrPool;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class NameUtils {

    @NotNull
    public static final NameUtils INSTANCE = new NameUtils();

    @NotNull
    private static final Regex SANITIZE_AS_JAVA_INVALID_CHARACTERS = new Regex("[^\\p{L}\\p{Digit}]");

    private NameUtils() {
    }

    @JvmStatic
    @NotNull
    public static final Name contextReceiverName(int i2) {
        Name nameIdentifier = Name.identifier("_context_receiver_" + i2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(\"_context_receiver_$index\")");
        return nameIdentifier;
    }

    @JvmStatic
    @NotNull
    public static final String sanitizeAsJavaIdentifier(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return SANITIZE_AS_JAVA_INVALID_CHARACTERS.replace(name, StrPool.UNDERLINE);
    }
}
