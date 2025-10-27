package io.reactivex.rxjava3.internal.disposables;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.DisposableContainer;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class ListCompositeDisposable implements Disposable, DisposableContainer {
    volatile boolean disposed;
    List<Disposable> resources;

    public ListCompositeDisposable() {
    }

    @Override // io.reactivex.rxjava3.disposables.DisposableContainer
    public boolean add(Disposable d3) {
        Objects.requireNonNull(d3, "d is null");
        if (!this.disposed) {
            synchronized (this) {
                if (!this.disposed) {
                    List linkedList = this.resources;
                    if (linkedList == null) {
                        linkedList = new LinkedList();
                        this.resources = linkedList;
                    }
                    linkedList.add(d3);
                    return true;
                }
            }
        }
        d3.dispose();
        return false;
    }

    public boolean addAll(Disposable... ds) {
        Objects.requireNonNull(ds, "ds is null");
        if (!this.disposed) {
            synchronized (this) {
                if (!this.disposed) {
                    List linkedList = this.resources;
                    if (linkedList == null) {
                        linkedList = new LinkedList();
                        this.resources = linkedList;
                    }
                    for (Disposable disposable : ds) {
                        Objects.requireNonNull(disposable, "d is null");
                        linkedList.add(disposable);
                    }
                    return true;
                }
            }
        }
        for (Disposable disposable2 : ds) {
            disposable2.dispose();
        }
        return false;
    }

    public void clear() {
        if (this.disposed) {
            return;
        }
        synchronized (this) {
            if (this.disposed) {
                return;
            }
            List<Disposable> list = this.resources;
            this.resources = null;
            dispose(list);
        }
    }

    @Override // io.reactivex.rxjava3.disposables.DisposableContainer
    public boolean delete(Disposable d3) {
        Objects.requireNonNull(d3, "Disposable item is null");
        if (this.disposed) {
            return false;
        }
        synchronized (this) {
            if (this.disposed) {
                return false;
            }
            List<Disposable> list = this.resources;
            if (list != null && list.remove(d3)) {
                return true;
            }
            return false;
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        if (this.disposed) {
            return;
        }
        synchronized (this) {
            if (this.disposed) {
                return;
            }
            this.disposed = true;
            List<Disposable> list = this.resources;
            this.resources = null;
            dispose(list);
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.disposed;
    }

    @Override // io.reactivex.rxjava3.disposables.DisposableContainer
    public boolean remove(Disposable d3) {
        if (!delete(d3)) {
            return false;
        }
        d3.dispose();
        return true;
    }

    public ListCompositeDisposable(Disposable... resources) {
        Objects.requireNonNull(resources, "resources is null");
        this.resources = new LinkedList();
        for (Disposable disposable : resources) {
            Objects.requireNonNull(disposable, "Disposable item is null");
            this.resources.add(disposable);
        }
    }

    public ListCompositeDisposable(Iterable<? extends Disposable> resources) {
        Objects.requireNonNull(resources, "resources is null");
        this.resources = new LinkedList();
        for (Disposable disposable : resources) {
            Objects.requireNonNull(disposable, "Disposable item is null");
            this.resources.add(disposable);
        }
    }

    public void dispose(List<Disposable> set) {
        if (set == null) {
            return;
        }
        Iterator<Disposable> it = set.iterator();
        ArrayList arrayList = null;
        while (it.hasNext()) {
            try {
                it.next().dispose();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(th);
            }
        }
        if (arrayList != null) {
            if (arrayList.size() == 1) {
                throw ExceptionHelper.wrapOrThrow((Throwable) arrayList.get(0));
            }
            throw new CompositeException(arrayList);
        }
    }
}
