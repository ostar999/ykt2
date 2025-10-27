package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public interface ImplicitContextReceiver extends ImplicitReceiver {
    @Nullable
    Name getCustomLabelName();
}
