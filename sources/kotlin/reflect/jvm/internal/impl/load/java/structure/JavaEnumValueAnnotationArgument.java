package kotlin.reflect.jvm.internal.impl.load.java.structure;

import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public interface JavaEnumValueAnnotationArgument extends JavaAnnotationArgument {
    @Nullable
    Name getEntryName();

    @Nullable
    ClassId getEnumClassId();
}
