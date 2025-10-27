package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public interface JavaArrayAnnotationArgument extends JavaAnnotationArgument {
    @NotNull
    List<JavaAnnotationArgument> getElements();
}
