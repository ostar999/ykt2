package androidx.databinding;

import androidx.databinding.ViewDataBinding;

/* loaded from: classes.dex */
public abstract class OnRebindCallback<T extends ViewDataBinding> {
    public void onBound(T t2) {
    }

    public void onCanceled(T t2) {
    }

    public boolean onPreBind(T t2) {
        return true;
    }
}
