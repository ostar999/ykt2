package de.greenrobot.dao.query;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoException;
import java.util.List;

/* loaded from: classes8.dex */
public class Query<T> extends AbstractQuery<T> {
    private final int limitPosition;
    private final int offsetPosition;
    private final QueryData<T> queryData;

    public static final class QueryData<T2> extends AbstractQueryData<T2, Query<T2>> {
        private final int limitPosition;
        private final int offsetPosition;

        public QueryData(AbstractDao<T2, ?> abstractDao, String str, String[] strArr, int i2, int i3) {
            super(abstractDao, str, strArr);
            this.limitPosition = i2;
            this.offsetPosition = i3;
        }

        @Override // de.greenrobot.dao.query.AbstractQueryData
        public Query<T2> createQuery() {
            return new Query<>(this, this.dao, this.sql, (String[]) this.initialValues.clone(), this.limitPosition, this.offsetPosition);
        }
    }

    public static <T2> Query<T2> create(AbstractDao<T2, ?> abstractDao, String str, Object[] objArr, int i2, int i3) {
        return new QueryData(abstractDao, str, AbstractQuery.toStringArray(objArr), i2, i3).forCurrentThread();
    }

    public static <T2> Query<T2> internalCreate(AbstractDao<T2, ?> abstractDao, String str, Object[] objArr) {
        return create(abstractDao, str, objArr, -1, -1);
    }

    public Query<T> forCurrentThread() {
        return (Query) this.queryData.forCurrentThread(this);
    }

    public List<T> list() {
        checkThread();
        return this.daoAccess.loadAllAndCloseCursor(this.dao.getDatabase().rawQuery(this.sql, this.parameters));
    }

    public CloseableListIterator<T> listIterator() {
        return listLazyUncached().listIteratorAutoClose();
    }

    public LazyList<T> listLazy() {
        checkThread();
        return new LazyList<>(this.daoAccess, this.dao.getDatabase().rawQuery(this.sql, this.parameters), true);
    }

    public LazyList<T> listLazyUncached() {
        checkThread();
        return new LazyList<>(this.daoAccess, this.dao.getDatabase().rawQuery(this.sql, this.parameters), false);
    }

    public void setLimit(int i2) {
        checkThread();
        int i3 = this.limitPosition;
        if (i3 == -1) {
            throw new IllegalStateException("Limit must be set with QueryBuilder before it can be used here");
        }
        this.parameters[i3] = Integer.toString(i2);
    }

    public void setOffset(int i2) {
        checkThread();
        int i3 = this.offsetPosition;
        if (i3 == -1) {
            throw new IllegalStateException("Offset must be set with QueryBuilder before it can be used here");
        }
        this.parameters[i3] = Integer.toString(i2);
    }

    @Override // de.greenrobot.dao.query.AbstractQuery
    public void setParameter(int i2, Object obj) {
        if (i2 < 0 || !(i2 == this.limitPosition || i2 == this.offsetPosition)) {
            super.setParameter(i2, obj);
            return;
        }
        throw new IllegalArgumentException("Illegal parameter index: " + i2);
    }

    public T unique() {
        checkThread();
        return this.daoAccess.loadUniqueAndCloseCursor(this.dao.getDatabase().rawQuery(this.sql, this.parameters));
    }

    public T uniqueOrThrow() {
        T tUnique = unique();
        if (tUnique != null) {
            return tUnique;
        }
        throw new DaoException("No entity found for query");
    }

    private Query(QueryData<T> queryData, AbstractDao<T, ?> abstractDao, String str, String[] strArr, int i2, int i3) {
        super(abstractDao, str, strArr);
        this.queryData = queryData;
        this.limitPosition = i2;
        this.offsetPosition = i3;
    }
}
