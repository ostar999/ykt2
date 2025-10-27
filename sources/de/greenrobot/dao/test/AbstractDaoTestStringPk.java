package de.greenrobot.dao.test;

import de.greenrobot.dao.AbstractDao;

/* loaded from: classes8.dex */
public abstract class AbstractDaoTestStringPk<D extends AbstractDao<T, String>, T> extends AbstractDaoTestSinglePk<D, T, String> {
    public AbstractDaoTestStringPk(Class<D> cls) {
        super(cls);
    }

    @Override // de.greenrobot.dao.test.AbstractDaoTestSinglePk
    public String createRandomPk() {
        int iNextInt = this.random.nextInt(30) + 1;
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < iNextInt; i2++) {
            sb.append((char) (this.random.nextInt(25) + 97));
        }
        return sb.toString();
    }
}
