package com.google.android.gms.common.api.internal;

import android.os.Looper;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

@KeepForSdk
/* loaded from: classes3.dex */
public class ListenerHolders {
    private final Set<ListenerHolder<?>> zajr = Collections.newSetFromMap(new WeakHashMap());

    @KeepForSdk
    public static <L> ListenerHolder<L> createListenerHolder(@NonNull L l2, @NonNull Looper looper, @NonNull String str) {
        Preconditions.checkNotNull(l2, "Listener must not be null");
        Preconditions.checkNotNull(looper, "Looper must not be null");
        Preconditions.checkNotNull(str, "Listener type must not be null");
        return new ListenerHolder<>(looper, l2, str);
    }

    @KeepForSdk
    public static <L> ListenerHolder.ListenerKey<L> createListenerKey(@NonNull L l2, @NonNull String str) {
        Preconditions.checkNotNull(l2, "Listener must not be null");
        Preconditions.checkNotNull(str, "Listener type must not be null");
        Preconditions.checkNotEmpty(str, "Listener type must not be empty");
        return new ListenerHolder.ListenerKey<>(l2, str);
    }

    public final void release() {
        Iterator<ListenerHolder<?>> it = this.zajr.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
        this.zajr.clear();
    }

    public final <L> ListenerHolder<L> zaa(@NonNull L l2, @NonNull Looper looper, @NonNull String str) {
        ListenerHolder<L> listenerHolderCreateListenerHolder = createListenerHolder(l2, looper, str);
        this.zajr.add(listenerHolderCreateListenerHolder);
        return listenerHolderCreateListenerHolder;
    }
}
