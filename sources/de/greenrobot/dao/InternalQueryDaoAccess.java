package de.greenrobot.dao;

import android.database.Cursor;
import de.greenrobot.dao.internal.TableStatements;
import java.util.List;

/* loaded from: classes8.dex */
public final class InternalQueryDaoAccess<T> {
    private final AbstractDao<T, ?> dao;

    public InternalQueryDaoAccess(AbstractDao<T, ?> abstractDao) {
        this.dao = abstractDao;
    }

    public TableStatements getStatements() {
        return this.dao.getStatements();
    }

    public List<T> loadAllAndCloseCursor(Cursor cursor) {
        return this.dao.loadAllAndCloseCursor(cursor);
    }

    public T loadCurrent(Cursor cursor, int i2, boolean z2) {
        return this.dao.loadCurrent(cursor, i2, z2);
    }

    public T loadUniqueAndCloseCursor(Cursor cursor) {
        return this.dao.loadUniqueAndCloseCursor(cursor);
    }

    public static <T2> TableStatements getStatements(AbstractDao<T2, ?> abstractDao) {
        return abstractDao.getStatements();
    }
}
