package de.greenrobot.dao.query;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoException;
import de.greenrobot.dao.InternalQueryDaoAccess;

/* loaded from: classes8.dex */
abstract class AbstractQuery<T> {
    protected final AbstractDao<T, ?> dao;
    protected final InternalQueryDaoAccess<T> daoAccess;
    protected final Thread ownerThread = Thread.currentThread();
    protected final String[] parameters;
    protected final String sql;

    public AbstractQuery(AbstractDao<T, ?> abstractDao, String str, String[] strArr) {
        this.dao = abstractDao;
        this.daoAccess = new InternalQueryDaoAccess<>(abstractDao);
        this.sql = str;
        this.parameters = strArr;
    }

    public static String[] toStringArray(Object[] objArr) {
        int length = objArr.length;
        String[] strArr = new String[length];
        for (int i2 = 0; i2 < length; i2++) {
            Object obj = objArr[i2];
            if (obj != null) {
                strArr[i2] = obj.toString();
            } else {
                strArr[i2] = null;
            }
        }
        return strArr;
    }

    public void checkThread() {
        if (Thread.currentThread() != this.ownerThread) {
            throw new DaoException("Method may be called only in owner thread, use forCurrentThread to get an instance for this thread");
        }
    }

    public void setParameter(int i2, Object obj) {
        checkThread();
        if (obj != null) {
            this.parameters[i2] = obj.toString();
        } else {
            this.parameters[i2] = null;
        }
    }
}
