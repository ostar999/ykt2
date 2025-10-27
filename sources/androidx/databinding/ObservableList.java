package androidx.databinding;

import java.util.List;

/* loaded from: classes.dex */
public interface ObservableList<T> extends List<T> {

    public static abstract class OnListChangedCallback<T extends ObservableList> {
        public abstract void onChanged(T t2);

        public abstract void onItemRangeChanged(T t2, int i2, int i3);

        public abstract void onItemRangeInserted(T t2, int i2, int i3);

        public abstract void onItemRangeMoved(T t2, int i2, int i3, int i4);

        public abstract void onItemRangeRemoved(T t2, int i2, int i3);
    }

    void addOnListChangedCallback(OnListChangedCallback<? extends ObservableList<T>> onListChangedCallback);

    void removeOnListChangedCallback(OnListChangedCallback<? extends ObservableList<T>> onListChangedCallback);
}
