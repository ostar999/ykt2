package kotlin.reflect.jvm.internal.impl.load.java.structure;

import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public interface JavaWildcardType extends JavaType {
    @Nullable
    JavaType getBound();

    boolean isExtends();
}
