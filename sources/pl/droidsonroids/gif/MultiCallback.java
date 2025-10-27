package pl.droidsonroids.gif;

import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes9.dex */
public class MultiCallback implements Drawable.Callback {
    private final CopyOnWriteArrayList<CallbackWeakReference> mCallbacks;
    private final boolean mUseViewInvalidate;

    public static final class CallbackWeakReference extends WeakReference<Drawable.Callback> {
        public CallbackWeakReference(Drawable.Callback callback) {
            super(callback);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && CallbackWeakReference.class == obj.getClass() && get() == ((CallbackWeakReference) obj).get();
        }

        public int hashCode() {
            Drawable.Callback callback = get();
            if (callback != null) {
                return callback.hashCode();
            }
            return 0;
        }
    }

    public MultiCallback() {
        this(false);
    }

    public void addView(Drawable.Callback callback) {
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            CallbackWeakReference callbackWeakReference = this.mCallbacks.get(i2);
            if (callbackWeakReference.get() == null) {
                this.mCallbacks.remove(callbackWeakReference);
            }
        }
        this.mCallbacks.addIfAbsent(new CallbackWeakReference(callback));
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(@NonNull Drawable drawable) {
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            CallbackWeakReference callbackWeakReference = this.mCallbacks.get(i2);
            Drawable.Callback callback = callbackWeakReference.get();
            if (callback == null) {
                this.mCallbacks.remove(callbackWeakReference);
            } else if (this.mUseViewInvalidate && (callback instanceof View)) {
                ((View) callback).invalidate();
            } else {
                callback.invalidateDrawable(drawable);
            }
        }
    }

    public void removeView(Drawable.Callback callback) {
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            CallbackWeakReference callbackWeakReference = this.mCallbacks.get(i2);
            Drawable.Callback callback2 = callbackWeakReference.get();
            if (callback2 == null || callback2 == callback) {
                this.mCallbacks.remove(callbackWeakReference);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long j2) {
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            CallbackWeakReference callbackWeakReference = this.mCallbacks.get(i2);
            Drawable.Callback callback = callbackWeakReference.get();
            if (callback != null) {
                callback.scheduleDrawable(drawable, runnable, j2);
            } else {
                this.mCallbacks.remove(callbackWeakReference);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            CallbackWeakReference callbackWeakReference = this.mCallbacks.get(i2);
            Drawable.Callback callback = callbackWeakReference.get();
            if (callback != null) {
                callback.unscheduleDrawable(drawable, runnable);
            } else {
                this.mCallbacks.remove(callbackWeakReference);
            }
        }
    }

    public MultiCallback(boolean z2) {
        this.mCallbacks = new CopyOnWriteArrayList<>();
        this.mUseViewInvalidate = z2;
    }
}
