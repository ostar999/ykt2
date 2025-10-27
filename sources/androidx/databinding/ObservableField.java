package androidx.databinding;

import androidx.annotation.Nullable;
import java.io.Serializable;

/* loaded from: classes.dex */
public class ObservableField<T> extends BaseObservableField implements Serializable {
    static final long serialVersionUID = 1;
    private T mValue;

    public ObservableField(T t2) {
        this.mValue = t2;
    }

    @Nullable
    public T get() {
        return this.mValue;
    }

    public void set(T t2) {
        if (t2 != this.mValue) {
            this.mValue = t2;
            notifyChange();
        }
    }

    public ObservableField() {
    }

    public ObservableField(Observable... observableArr) {
        super(observableArr);
    }
}
