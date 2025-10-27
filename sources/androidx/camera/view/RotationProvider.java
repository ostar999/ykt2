package androidx.camera.view;

import android.content.Context;
import android.view.OrientationEventListener;
import androidx.annotation.CheckResult;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class RotationProvider {

    @NonNull
    @GuardedBy("mLock")
    @VisibleForTesting
    final OrientationEventListener mOrientationListener;
    final Object mLock = new Object();

    @NonNull
    @GuardedBy("mLock")
    final Map<Listener, ListenerWrapper> mListeners = new HashMap();

    @VisibleForTesting
    boolean mIgnoreCanDetectForTest = false;

    public interface Listener {
        void onRotationChanged(int i2);
    }

    public static class ListenerWrapper {
        private final AtomicBoolean mEnabled = new AtomicBoolean(true);
        private final Executor mExecutor;
        private final Listener mListener;

        public ListenerWrapper(Listener listener, Executor executor) {
            this.mListener = listener;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRotationChanged$0(int i2) {
            if (this.mEnabled.get()) {
                this.mListener.onRotationChanged(i2);
            }
        }

        public void disable() {
            this.mEnabled.set(false);
        }

        public void onRotationChanged(final int i2) {
            this.mExecutor.execute(new Runnable() { // from class: androidx.camera.view.n
                @Override // java.lang.Runnable
                public final void run() {
                    this.f1749c.lambda$onRotationChanged$0(i2);
                }
            });
        }
    }

    public RotationProvider(@NonNull Context context) {
        this.mOrientationListener = new OrientationEventListener(context) { // from class: androidx.camera.view.RotationProvider.1
            private static final int INVALID_SURFACE_ROTATION = -1;
            private int mRotation = -1;

            @Override // android.view.OrientationEventListener
            public void onOrientationChanged(int i2) {
                int iOrientationToSurfaceRotation;
                ArrayList arrayList;
                if (i2 == -1 || this.mRotation == (iOrientationToSurfaceRotation = RotationProvider.orientationToSurfaceRotation(i2))) {
                    return;
                }
                this.mRotation = iOrientationToSurfaceRotation;
                synchronized (RotationProvider.this.mLock) {
                    arrayList = new ArrayList(RotationProvider.this.mListeners.values());
                }
                if (arrayList.isEmpty()) {
                    return;
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((ListenerWrapper) it.next()).onRotationChanged(iOrientationToSurfaceRotation);
                }
            }
        };
    }

    @VisibleForTesting
    public static int orientationToSurfaceRotation(int i2) {
        if (i2 >= 315 || i2 < 45) {
            return 0;
        }
        if (i2 >= 225) {
            return 1;
        }
        return i2 >= 135 ? 2 : 3;
    }

    @CheckResult
    public boolean addListener(@NonNull Executor executor, @NonNull Listener listener) {
        synchronized (this.mLock) {
            if (!this.mOrientationListener.canDetectOrientation() && !this.mIgnoreCanDetectForTest) {
                return false;
            }
            this.mListeners.put(listener, new ListenerWrapper(listener, executor));
            this.mOrientationListener.enable();
            return true;
        }
    }

    public void removeListener(@NonNull Listener listener) {
        synchronized (this.mLock) {
            ListenerWrapper listenerWrapper = this.mListeners.get(listener);
            if (listenerWrapper != null) {
                listenerWrapper.disable();
                this.mListeners.remove(listener);
            }
            if (this.mListeners.isEmpty()) {
                this.mOrientationListener.disable();
            }
        }
    }
}
