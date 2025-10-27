package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public interface JavaConstructor extends JavaMember, JavaTypeParameterListOwner {
    @NotNull
    List<JavaValueParameter> getValueParameters();
}
