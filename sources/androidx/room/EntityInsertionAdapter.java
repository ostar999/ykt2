package androidx.room;

import androidx.annotation.RestrictTo;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public abstract class EntityInsertionAdapter<T> extends SharedSQLiteStatement {
    public EntityInsertionAdapter(RoomDatabase roomDatabase) {
        super(roomDatabase);
    }

    public abstract void bind(SupportSQLiteStatement supportSQLiteStatement, T t2);

    public final void insert(T t2) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = acquire();
        try {
            bind(supportSQLiteStatementAcquire, t2);
            supportSQLiteStatementAcquire.executeInsert();
        } finally {
            release(supportSQLiteStatementAcquire);
        }
    }

    public final long insertAndReturnId(T t2) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = acquire();
        try {
            bind(supportSQLiteStatementAcquire, t2);
            return supportSQLiteStatementAcquire.executeInsert();
        } finally {
            release(supportSQLiteStatementAcquire);
        }
    }

    public final long[] insertAndReturnIdsArray(Collection<? extends T> collection) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = acquire();
        try {
            long[] jArr = new long[collection.size()];
            Iterator<? extends T> it = collection.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                bind(supportSQLiteStatementAcquire, it.next());
                jArr[i2] = supportSQLiteStatementAcquire.executeInsert();
                i2++;
            }
            return jArr;
        } finally {
            release(supportSQLiteStatementAcquire);
        }
    }

    public final Long[] insertAndReturnIdsArrayBox(Collection<? extends T> collection) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = acquire();
        try {
            Long[] lArr = new Long[collection.size()];
            Iterator<? extends T> it = collection.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                bind(supportSQLiteStatementAcquire, it.next());
                lArr[i2] = Long.valueOf(supportSQLiteStatementAcquire.executeInsert());
                i2++;
            }
            return lArr;
        } finally {
            release(supportSQLiteStatementAcquire);
        }
    }

    public final List<Long> insertAndReturnIdsList(T[] tArr) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = acquire();
        try {
            ArrayList arrayList = new ArrayList(tArr.length);
            int i2 = 0;
            for (T t2 : tArr) {
                bind(supportSQLiteStatementAcquire, t2);
                arrayList.add(i2, Long.valueOf(supportSQLiteStatementAcquire.executeInsert()));
                i2++;
            }
            return arrayList;
        } finally {
            release(supportSQLiteStatementAcquire);
        }
    }

    public final void insert(T[] tArr) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = acquire();
        try {
            for (T t2 : tArr) {
                bind(supportSQLiteStatementAcquire, t2);
                supportSQLiteStatementAcquire.executeInsert();
            }
        } finally {
            release(supportSQLiteStatementAcquire);
        }
    }

    public final long[] insertAndReturnIdsArray(T[] tArr) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = acquire();
        try {
            long[] jArr = new long[tArr.length];
            int i2 = 0;
            for (T t2 : tArr) {
                bind(supportSQLiteStatementAcquire, t2);
                jArr[i2] = supportSQLiteStatementAcquire.executeInsert();
                i2++;
            }
            return jArr;
        } finally {
            release(supportSQLiteStatementAcquire);
        }
    }

    public final Long[] insertAndReturnIdsArrayBox(T[] tArr) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = acquire();
        try {
            Long[] lArr = new Long[tArr.length];
            int i2 = 0;
            for (T t2 : tArr) {
                bind(supportSQLiteStatementAcquire, t2);
                lArr[i2] = Long.valueOf(supportSQLiteStatementAcquire.executeInsert());
                i2++;
            }
            return lArr;
        } finally {
            release(supportSQLiteStatementAcquire);
        }
    }

    public final List<Long> insertAndReturnIdsList(Collection<? extends T> collection) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = acquire();
        try {
            ArrayList arrayList = new ArrayList(collection.size());
            Iterator<? extends T> it = collection.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                bind(supportSQLiteStatementAcquire, it.next());
                arrayList.add(i2, Long.valueOf(supportSQLiteStatementAcquire.executeInsert()));
                i2++;
            }
            return arrayList;
        } finally {
            release(supportSQLiteStatementAcquire);
        }
    }

    public final void insert(Iterable<? extends T> iterable) {
        SupportSQLiteStatement supportSQLiteStatementAcquire = acquire();
        try {
            Iterator<? extends T> it = iterable.iterator();
            while (it.hasNext()) {
                bind(supportSQLiteStatementAcquire, it.next());
                supportSQLiteStatementAcquire.executeInsert();
            }
        } finally {
            release(supportSQLiteStatementAcquire);
        }
    }
}
