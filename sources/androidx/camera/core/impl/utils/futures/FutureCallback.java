package androidx.camera.core.impl.utils.futures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@RequiresApi(21)
/* loaded from: classes.dex */
public interface FutureCallback<V> {
    void onFailure(@NonNull Throwable th);

    void onSuccess(@Nullable V v2);
}
