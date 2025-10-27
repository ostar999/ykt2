package de.greenrobot.dao.query;

import android.database.Cursor;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoException;

/* loaded from: classes8.dex */
public class CountQuery<T> extends AbstractQuery<T> {
    private final QueryData<T> queryData;

    public static final class QueryData<T2> extends AbstractQueryData<T2, CountQuery<T2>> {
        private QueryData(AbstractDao<T2, ?> abstractDao, String str, String[] strArr) {
            super(abstractDao, str, strArr);
        }

        @Override // de.greenrobot.dao.query.AbstractQueryData
        public CountQuery<T2> createQuery() {
            return new CountQuery<>(this, this.dao, this.sql, (String[]) this.initialValues.clone());
        }
    }

    public static <T2> CountQuery<T2> create(AbstractDao<T2, ?> abstractDao, String str, Object[] objArr) {
        return new QueryData(abstractDao, str, AbstractQuery.toStringArray(objArr)).forCurrentThread();
    }

    public long count() {
        checkThread();
        Cursor cursorRawQuery = this.dao.getDatabase().rawQuery(this.sql, this.parameters);
        try {
            if (!cursorRawQuery.moveToNext()) {
                throw new DaoException("No result for count");
            }
            if (!cursorRawQuery.isLast()) {
                throw new DaoException("Unexpected row count: " + cursorRawQuery.getCount());
            }
            if (cursorRawQuery.getColumnCount() == 1) {
                return cursorRawQuery.getLong(0);
            }
            throw new DaoException("Unexpected column count: " + cursorRawQuery.getColumnCount());
        } finally {
            cursorRawQuery.close();
        }
    }

    public CountQuery<T> forCurrentThread() {
        return (CountQuery) this.queryData.forCurrentThread(this);
    }

    @Override // de.greenrobot.dao.query.AbstractQuery
    public /* bridge */ /* synthetic */ void setParameter(int i2, Object obj) {
        super.setParameter(i2, obj);
    }

    private CountQuery(QueryData<T> queryData, AbstractDao<T, ?> abstractDao, String str, String[] strArr) {
        super(abstractDao, str, strArr);
        this.queryData = queryData;
    }
}
