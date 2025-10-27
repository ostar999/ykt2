package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.io.InputStream;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public interface KotlinMetadataFinder {
    @Nullable
    InputStream findBuiltInsData(@NotNull FqName fqName);
}
