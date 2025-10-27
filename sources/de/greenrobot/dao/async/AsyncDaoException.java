package de.greenrobot.dao.async;

import de.greenrobot.dao.DaoException;

/* loaded from: classes8.dex */
public class AsyncDaoException extends DaoException {
    private static final long serialVersionUID = 5872157552005102382L;
    private final AsyncOperation failedOperation;

    public AsyncDaoException(AsyncOperation asyncOperation, Throwable th) {
        super(th);
        this.failedOperation = asyncOperation;
    }

    public AsyncOperation getFailedOperation() {
        return this.failedOperation;
    }
}
