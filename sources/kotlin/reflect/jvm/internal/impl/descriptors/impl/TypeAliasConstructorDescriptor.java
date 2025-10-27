package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public interface TypeAliasConstructorDescriptor extends ConstructorDescriptor {
    @NotNull
    ClassConstructorDescriptor getUnderlyingConstructorDescriptor();
}
