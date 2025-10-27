package com.nirvana.tools.logger.cache;

import android.content.Context;
import com.nirvana.tools.logger.cache.db.ACMMonitorDatabase;
import com.nirvana.tools.logger.executor.ReentrantSingleThreadExecutor;
import com.nirvana.tools.logger.model.ACMMonitorRecord;
import java.util.List;

/* loaded from: classes4.dex */
public class ACMMonitorCacheManager extends ACMCacheManager<ACMMonitorRecord, ACMMonitorDatabase> {
    public ACMMonitorCacheManager(Context context, ReentrantSingleThreadExecutor reentrantSingleThreadExecutor, String str, String str2) {
        super(new ACMMonitorDatabase(context, str, str2), reentrantSingleThreadExecutor);
    }

    public List<ACMMonitorRecord> getNormalMonitorRecords(int i2) {
        return ((ACMMonitorDatabase) this.mDatabase).query(i2, 0, "urgency DESC");
    }
}
