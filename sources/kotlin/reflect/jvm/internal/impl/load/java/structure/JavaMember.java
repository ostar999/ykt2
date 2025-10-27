package kotlin.reflect.jvm.internal.impl.load.java.structure;

import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public interface JavaMember extends JavaAnnotationOwner, JavaModifierListOwner, JavaNamedElement {
    @NotNull
    JavaClass getContainingClass();
}
