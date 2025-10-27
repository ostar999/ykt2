package com.nirvana.tools.logger.cache;

import com.nirvana.tools.logger.cache.db.AbstractDatabase;
import com.nirvana.tools.logger.cache.db.DbException;
import com.nirvana.tools.logger.executor.ReentrantSingleThreadExecutor;
import com.nirvana.tools.logger.model.ACMRecord;
import java.util.List;

/* loaded from: classes4.dex */
public class ACMCacheManager<T extends ACMRecord, G extends AbstractDatabase<T>> {
    protected G mDatabase;
    protected ReentrantSingleThreadExecutor mExecutor;

    public ACMCacheManager(G g2, ReentrantSingleThreadExecutor reentrantSingleThreadExecutor) {
        this.mDatabase = g2;
        this.mExecutor = reentrantSingleThreadExecutor;
    }

    public void addUploadCount(List<T> list) throws DbException {
        this.mDatabase.updateUploadCount(list, System.currentTimeMillis(), 1);
    }

    public boolean cacheRecord(T t2) throws DbException {
        return this.mDatabase.insert(t2);
    }

    public boolean cacheRecords(List<T> list) throws DbException {
        return this.mDatabase.insertList(list);
    }

    public boolean deleteRecords(List<T> list) throws DbException {
        return this.mDatabase.deleteRecords(list);
    }

    public List<T> getAllFailedRecords() {
        return this.mDatabase.query(-1, 1, null);
    }

    public List<T> getAllRecords() {
        return this.mDatabase.query(-1, -1, null);
    }

    public long getFailedMaxID() {
        return this.mDatabase.queryFailedMaxId();
    }

    public List<T> getFailedRecords(long j2, long j3, int i2) {
        return this.mDatabase.queryFailed(j2, j3, i2);
    }

    public List<T> getUnUploadRecords() {
        return this.mDatabase.query(-1, 0, null);
    }

    public boolean hasFailedRecords() {
        List<T> listQuery = this.mDatabase.query(1, 1, null);
        return listQuery != null && listQuery.size() > 0;
    }

    public boolean hasNormalRecords() {
        List<T> listQuery = this.mDatabase.query(1, 0, null);
        return listQuery != null && listQuery.size() > 0;
    }
}
