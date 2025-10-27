package de.greenrobot.dao.query;

import android.os.Process;
import android.util.SparseArray;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.AbstractQuery;
import java.lang.ref.WeakReference;

/* loaded from: classes8.dex */
abstract class AbstractQueryData<T, Q extends AbstractQuery<T>> {
    final AbstractDao<T, ?> dao;
    final String[] initialValues;
    final SparseArray<WeakReference<Q>> queriesForThreads = new SparseArray<>();
    final String sql;

    public AbstractQueryData(AbstractDao<T, ?> abstractDao, String str, String[] strArr) {
        this.dao = abstractDao;
        this.sql = str;
        this.initialValues = strArr;
    }

    public abstract Q createQuery();

    public Q forCurrentThread(Q q2) {
        if (Thread.currentThread() != q2.ownerThread) {
            return (Q) forCurrentThread();
        }
        String[] strArr = this.initialValues;
        System.arraycopy(strArr, 0, q2.parameters, 0, strArr.length);
        return q2;
    }

    public void gc() {
        synchronized (this.queriesForThreads) {
            for (int size = this.queriesForThreads.size() - 1; size >= 0; size--) {
                if (this.queriesForThreads.valueAt(size).get() == null) {
                    SparseArray<WeakReference<Q>> sparseArray = this.queriesForThreads;
                    sparseArray.remove(sparseArray.keyAt(size));
                }
            }
        }
    }

    public Q forCurrentThread() {
        Q q2;
        int iMyTid = Process.myTid();
        synchronized (this.queriesForThreads) {
            WeakReference<Q> weakReference = this.queriesForThreads.get(iMyTid);
            q2 = weakReference != null ? weakReference.get() : null;
            if (q2 == null) {
                gc();
                q2 = (Q) createQuery();
                this.queriesForThreads.put(iMyTid, new WeakReference<>(q2));
            } else {
                String[] strArr = this.initialValues;
                System.arraycopy(strArr, 0, q2.parameters, 0, strArr.length);
            }
        }
        return q2;
    }
}
