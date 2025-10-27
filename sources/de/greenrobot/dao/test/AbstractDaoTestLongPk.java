package de.greenrobot.dao.test;

import android.test.AndroidTestCase;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoLog;

/* loaded from: classes8.dex */
public abstract class AbstractDaoTestLongPk<D extends AbstractDao<T, Long>, T> extends AbstractDaoTestSinglePk<D, T, Long> {
    public AbstractDaoTestLongPk(Class<D> cls) {
        super(cls);
    }

    public void testAssignPk() {
        if (!this.daoAccess.isEntityUpdateable()) {
            DaoLog.d("Skipping testAssignPk for not updateable " + this.daoClass);
            return;
        }
        T tCreateEntity = createEntity(null);
        if (tCreateEntity == null) {
            DaoLog.d("Skipping testAssignPk for " + this.daoClass + " (createEntity returned null for null key)");
            return;
        }
        T tCreateEntity2 = createEntity(null);
        this.dao.insert(tCreateEntity);
        this.dao.insert(tCreateEntity2);
        Long l2 = (Long) this.daoAccess.getKey(tCreateEntity);
        AndroidTestCase.assertNotNull(l2);
        Long l3 = (Long) this.daoAccess.getKey(tCreateEntity2);
        AndroidTestCase.assertNotNull(l3);
        AndroidTestCase.assertFalse(l2.equals(l3));
        AndroidTestCase.assertNotNull(this.dao.load(l2));
        AndroidTestCase.assertNotNull(this.dao.load(l3));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.test.AbstractDaoTestSinglePk
    public Long createRandomPk() {
        return Long.valueOf(this.random.nextLong());
    }
}
