package com.google.android.gms.common.api.internal;

import android.app.Activity;
import androidx.annotation.MainThread;
import androidx.annotation.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public final class zaa extends ActivityLifecycleObserver {
    private final WeakReference<C0144zaa> zaco;

    public zaa(Activity activity) {
        this(C0144zaa.zaa(activity));
    }

    @Override // com.google.android.gms.common.api.internal.ActivityLifecycleObserver
    public final ActivityLifecycleObserver onStopCallOnce(Runnable runnable) {
        C0144zaa c0144zaa = this.zaco.get();
        if (c0144zaa == null) {
            throw new IllegalStateException("The target activity has already been GC'd");
        }
        c0144zaa.zaa(runnable);
        return this;
    }

    @VisibleForTesting(otherwise = 2)
    private zaa(C0144zaa c0144zaa) {
        this.zaco = new WeakReference<>(c0144zaa);
    }

    @VisibleForTesting(otherwise = 2)
    /* renamed from: com.google.android.gms.common.api.internal.zaa$zaa, reason: collision with other inner class name */
    public static class C0144zaa extends LifecycleCallback {
        private List<Runnable> zacn;

        private C0144zaa(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.zacn = new ArrayList();
            this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static C0144zaa zaa(Activity activity) {
            C0144zaa c0144zaa;
            synchronized (activity) {
                LifecycleFragment fragment = LifecycleCallback.getFragment(activity);
                c0144zaa = (C0144zaa) fragment.getCallbackOrNull("LifecycleObserverOnStop", C0144zaa.class);
                if (c0144zaa == null) {
                    c0144zaa = new C0144zaa(fragment);
                }
            }
            return c0144zaa;
        }

        @Override // com.google.android.gms.common.api.internal.LifecycleCallback
        @MainThread
        public void onStop() {
            List<Runnable> list;
            synchronized (this) {
                list = this.zacn;
                this.zacn = new ArrayList();
            }
            Iterator<Runnable> it = list.iterator();
            while (it.hasNext()) {
                it.next().run();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final synchronized void zaa(Runnable runnable) {
            this.zacn.add(runnable);
        }
    }
}
