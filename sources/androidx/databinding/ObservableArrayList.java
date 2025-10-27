package androidx.databinding;

import androidx.databinding.ObservableList;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes.dex */
public class ObservableArrayList<T> extends ArrayList<T> implements ObservableList<T> {
    private transient ListChangeRegistry mListeners = new ListChangeRegistry();

    private void notifyAdd(int i2, int i3) {
        ListChangeRegistry listChangeRegistry = this.mListeners;
        if (listChangeRegistry != null) {
            listChangeRegistry.notifyInserted(this, i2, i3);
        }
    }

    private void notifyRemove(int i2, int i3) {
        ListChangeRegistry listChangeRegistry = this.mListeners;
        if (listChangeRegistry != null) {
            listChangeRegistry.notifyRemoved(this, i2, i3);
        }
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(T t2) {
        super.add(t2);
        notifyAdd(size() - 1, 1);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends T> collection) {
        int size = size();
        boolean zAddAll = super.addAll(collection);
        if (zAddAll) {
            notifyAdd(size, size() - size);
        }
        return zAddAll;
    }

    @Override // androidx.databinding.ObservableList
    public void addOnListChangedCallback(ObservableList.OnListChangedCallback onListChangedCallback) {
        if (this.mListeners == null) {
            this.mListeners = new ListChangeRegistry();
        }
        this.mListeners.add(onListChangedCallback);
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        int size = size();
        super.clear();
        if (size != 0) {
            notifyRemove(0, size);
        }
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public T remove(int i2) {
        T t2 = (T) super.remove(i2);
        notifyRemove(i2, 1);
        return t2;
    }

    @Override // androidx.databinding.ObservableList
    public void removeOnListChangedCallback(ObservableList.OnListChangedCallback onListChangedCallback) {
        ListChangeRegistry listChangeRegistry = this.mListeners;
        if (listChangeRegistry != null) {
            listChangeRegistry.remove(onListChangedCallback);
        }
    }

    @Override // java.util.ArrayList, java.util.AbstractList
    public void removeRange(int i2, int i3) {
        super.removeRange(i2, i3);
        notifyRemove(i2, i3 - i2);
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public T set(int i2, T t2) {
        T t3 = (T) super.set(i2, t2);
        ListChangeRegistry listChangeRegistry = this.mListeners;
        if (listChangeRegistry != null) {
            listChangeRegistry.notifyChanged(this, i2, 1);
        }
        return t3;
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public void add(int i2, T t2) {
        super.add(i2, t2);
        notifyAdd(i2, 1);
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf < 0) {
            return false;
        }
        remove(iIndexOf);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public boolean addAll(int i2, Collection<? extends T> collection) {
        boolean zAddAll = super.addAll(i2, collection);
        if (zAddAll) {
            notifyAdd(i2, collection.size());
        }
        return zAddAll;
    }
}
