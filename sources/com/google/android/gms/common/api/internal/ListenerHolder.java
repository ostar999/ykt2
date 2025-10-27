package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;

@KeepForSdk
/* loaded from: classes3.dex */
public final class ListenerHolder<L> {
    private final zaa zajm;
    private volatile L zajn;
    private final ListenerKey<L> zajo;

    @KeepForSdk
    public static final class ListenerKey<L> {
        private final L zajn;
        private final String zajp;

        @KeepForSdk
        public ListenerKey(L l2, String str) {
            this.zajn = l2;
            this.zajp = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ListenerKey)) {
                return false;
            }
            ListenerKey listenerKey = (ListenerKey) obj;
            return this.zajn == listenerKey.zajn && this.zajp.equals(listenerKey.zajp);
        }

        public final int hashCode() {
            return (System.identityHashCode(this.zajn) * 31) + this.zajp.hashCode();
        }
    }

    @KeepForSdk
    public interface Notifier<L> {
        @KeepForSdk
        void notifyListener(L l2);

        @KeepForSdk
        void onNotifyListenerFailed();
    }

    public final class zaa extends com.google.android.gms.internal.base.zar {
        public zaa(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Preconditions.checkArgument(message.what == 1);
            ListenerHolder.this.notifyListenerInternal((Notifier) message.obj);
        }
    }

    @KeepForSdk
    public ListenerHolder(@NonNull Looper looper, @NonNull L l2, @NonNull String str) {
        this.zajm = new zaa(looper);
        this.zajn = (L) Preconditions.checkNotNull(l2, "Listener must not be null");
        this.zajo = new ListenerKey<>(l2, Preconditions.checkNotEmpty(str));
    }

    @KeepForSdk
    public final void clear() {
        this.zajn = null;
    }

    @NonNull
    @KeepForSdk
    public final ListenerKey<L> getListenerKey() {
        return this.zajo;
    }

    @KeepForSdk
    public final boolean hasListener() {
        return this.zajn != null;
    }

    @KeepForSdk
    public final void notifyListener(Notifier<? super L> notifier) {
        Preconditions.checkNotNull(notifier, "Notifier must not be null");
        this.zajm.sendMessage(this.zajm.obtainMessage(1, notifier));
    }

    @KeepForSdk
    public final void notifyListenerInternal(Notifier<? super L> notifier) {
        L l2 = this.zajn;
        if (l2 == null) {
            notifier.onNotifyListenerFailed();
            return;
        }
        try {
            notifier.notifyListener(l2);
        } catch (RuntimeException e2) {
            notifier.onNotifyListenerFailed();
            throw e2;
        }
    }
}
