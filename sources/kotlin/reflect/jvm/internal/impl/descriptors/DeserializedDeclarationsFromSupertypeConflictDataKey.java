package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class DeserializedDeclarationsFromSupertypeConflictDataKey implements CallableDescriptor.UserDataKey<CallableMemberDescriptor> {

    @NotNull
    public static final DeserializedDeclarationsFromSupertypeConflictDataKey INSTANCE = new DeserializedDeclarationsFromSupertypeConflictDataKey();

    private DeserializedDeclarationsFromSupertypeConflictDataKey() {
    }
}
