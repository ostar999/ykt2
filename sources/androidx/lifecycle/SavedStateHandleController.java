package androidx.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.savedstate.SavedStateRegistry;

/* loaded from: classes.dex */
final class SavedStateHandleController implements LifecycleEventObserver {
    private final SavedStateHandle mHandle;
    private boolean mIsAttached = false;
    private final String mKey;

    public SavedStateHandleController(String str, SavedStateHandle savedStateHandle) {
        this.mKey = str;
        this.mHandle = savedStateHandle;
    }

    public void attachToLifecycle(SavedStateRegistry savedStateRegistry, Lifecycle lifecycle) {
        if (this.mIsAttached) {
            throw new IllegalStateException("Already attached to lifecycleOwner");
        }
        this.mIsAttached = true;
        lifecycle.addObserver(this);
        savedStateRegistry.registerSavedStateProvider(this.mKey, this.mHandle.getSavedStateProvider());
    }

    public SavedStateHandle getHandle() {
        return this.mHandle;
    }

    public boolean isAttached() {
        return this.mIsAttached;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            this.mIsAttached = false;
            lifecycleOwner.getLifecycle().removeObserver(this);
        }
    }
}
