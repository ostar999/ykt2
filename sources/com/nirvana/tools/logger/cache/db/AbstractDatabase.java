package com.nirvana.tools.logger.cache.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.nirvana.tools.logger.model.ACMRecord;
import com.nirvana.tools.logger.utils.ConsoleLogUtils;
import com.umeng.analytics.pro.aq;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;

/* loaded from: classes4.dex */
public abstract class AbstractDatabase<T extends ACMRecord> {
    public static final int DEFAULT_LIMIT = 5242880;
    private static final String TAG = "com.nirvana.tools.logger.cache.db.AbstractDatabase";
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    protected String mTableName;
    private Semaphore semaphore = new Semaphore(1);

    public AbstractDatabase(String str, DBHelper dBHelper) {
        this.mTableName = str;
        this.mDbHelper = dBHelper;
        setMaxSizeLog(CacheDataSink.DEFAULT_FRAGMENT_SIZE);
    }

    private <G> void numberList2StringArray(List<G> list, String[] strArr) {
        if (list.size() == strArr.length) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                strArr[i2] = String.valueOf(list.get(i2));
            }
            return;
        }
        Log.e(TAG, "NumberList size(" + list.size() + ") not equals results length[" + strArr.length + StrPool.BRACKET_END);
    }

    @SuppressLint({"Range"})
    private long parseIdFromCursor(Cursor cursor) {
        if (cursor == null) {
            return -1L;
        }
        return cursor.getLong(cursor.getColumnIndex(aq.f22519d));
    }

    public void close() {
        SQLiteDatabase sQLiteDatabase = this.mDatabase;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
            this.mDatabase = null;
        }
    }

    public String contactIds(long j2) {
        if (j2 <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder("(");
        do {
            sb.append("?,");
            j2--;
        } while (j2 > 0);
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    public synchronized boolean deleteOldest(SQLiteDatabase sQLiteDatabase, int i2) throws DbException {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            Cursor cursorQuery = sQLiteDatabase.query(this.mTableName, new String[]{aq.f22519d}, null, null, null, null, "timestamp ASC", i2 > 0 ? String.valueOf(i2) : null);
            ArrayList arrayList = new ArrayList();
            while (cursorQuery.moveToNext()) {
                Long lValueOf = Long.valueOf(parseIdFromCursor(cursorQuery));
                if (lValueOf.longValue() != -1) {
                    arrayList.add(lValueOf);
                }
            }
            cursorQuery.close();
            ConsoleLogUtils.logcatV(TAG, "delete oldest: escape=" + (System.currentTimeMillis() - jCurrentTimeMillis));
            if (!arrayList.isEmpty()) {
                return deleteRecordsById(sQLiteDatabase, arrayList);
            }
        } catch (Exception e2) {
            new DbException("Delete oldest exception!", e2);
        }
        return false;
    }

    public synchronized boolean deleteRecords(List<T> list) throws DbException {
        if (list != null) {
            try {
                try {
                    if (!list.isEmpty()) {
                        ArrayList arrayList = new ArrayList();
                        Iterator<T> it = list.iterator();
                        while (it.hasNext()) {
                            arrayList.add(Long.valueOf(it.next().getId()));
                        }
                        return deleteRecordsById(getWriteDatabase(), arrayList);
                    }
                } catch (DbException e2) {
                    throw e2;
                }
            } finally {
                close();
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public synchronized boolean deleteRecordsById(SQLiteDatabase sQLiteDatabase, List<Long> list) throws DbException {
        if (list != 0) {
            try {
                if (!list.isEmpty()) {
                    String str = TAG;
                    ConsoleLogUtils.logcatV(str, "delete: size=" + list.size());
                    StringBuilder sb = new StringBuilder("_id in ");
                    sb.append(contactIds((long) list.size()));
                    ConsoleLogUtils.logcatV(str, "delete: selection=" + ((Object) sb));
                    String[] strArr = new String[list.size()];
                    numberList2StringArray(list, strArr);
                    int iDelete = sQLiteDatabase.delete(this.mTableName, sb.toString(), strArr);
                    ConsoleLogUtils.logcatV(str, "delete: count=" + iDelete);
                    return iDelete == list.size();
                }
            } catch (Exception e2) {
                throw new DbException("Delete records failed!", e2);
            }
        }
        return true;
    }

    public abstract ContentValues getContentValuesByRecord(T t2);

    public int getCount(SQLiteDatabase sQLiteDatabase) {
        return (int) DatabaseUtils.longForQuery(sQLiteDatabase, String.format("SELECT COUNT(%s) FROM %s", aq.f22519d, this.mTableName), null);
    }

    public synchronized long getCurrentSize() {
        long pageSize;
        try {
            pageSize = getReadDatabase().getPageSize() * DatabaseUtils.longForQuery(this.mDatabase, "PRAGMA page_count;", null);
            close();
        } catch (Throwable unused) {
            close();
            return -1L;
        }
        return pageSize;
    }

    public synchronized long getMaxSizeLog() {
        long maximumSize;
        try {
            maximumSize = getReadDatabase().getMaximumSize();
            close();
        } catch (Throwable unused) {
            close();
            return -1L;
        }
        return maximumSize;
    }

    public SQLiteDatabase getReadDatabase() {
        if (this.mDatabase == null) {
            this.mDatabase = this.mDbHelper.getReadableDatabase();
        }
        return this.mDatabase;
    }

    public synchronized SQLiteDatabase getWriteDatabase() {
        if (this.mDatabase == null) {
            this.mDatabase = this.mDbHelper.getWritableDatabase();
        }
        return this.mDatabase;
    }

    public synchronized boolean insert(T t2) throws DbException {
        if (t2 == null) {
            close();
            return false;
        }
        long jInsert = -1;
        try {
            try {
                if (getCurrentSize() >= CacheDataSink.DEFAULT_FRAGMENT_SIZE) {
                    ConsoleLogUtils.logcatV(TAG, "Table size is limited, clear half of data!");
                    deleteOldest(getWriteDatabase(), getCount(getWriteDatabase()) / 2);
                }
                ContentValues contentValuesByRecord = getContentValuesByRecord(t2);
                jInsert = getWriteDatabase().insert(this.mTableName, null, contentValuesByRecord);
                if (jInsert < 0 && getCount(getWriteDatabase()) > 0) {
                    deleteOldest(getWriteDatabase(), getCount(getWriteDatabase()) / 2);
                    jInsert = getWriteDatabase().insert(this.mTableName, null, contentValuesByRecord);
                }
                ConsoleLogUtils.logcatV(TAG, "insert: id=" + jInsert);
                close();
                return jInsert >= 0;
            } catch (Exception e2) {
                throw new DbException("Insert record failed!", e2);
            }
        } catch (Throwable unused) {
            close();
            return jInsert >= 0;
        }
    }

    public synchronized boolean insertList(List<T> list) throws DbException {
        boolean z2 = true;
        if (list != null) {
            long jInsert = -1;
            try {
                try {
                    if (list.size() != 0) {
                        if (getCurrentSize() >= CacheDataSink.DEFAULT_FRAGMENT_SIZE) {
                            ConsoleLogUtils.logcatV(TAG, "Table size is limited, clear half of data!");
                            deleteOldest(getWriteDatabase(), getCount(getWriteDatabase()) / 2);
                        }
                        this.semaphore.acquire();
                        String str = TAG;
                        ConsoleLogUtils.logcatV(str, "beginTransaction");
                        getWriteDatabase().beginTransaction();
                        StringBuilder sb = new StringBuilder("writedatabase success  ");
                        sb.append(this.mDatabase == null);
                        ConsoleLogUtils.logcatV(str, sb.toString());
                        Iterator<T> it = list.iterator();
                        while (it.hasNext()) {
                            ContentValues contentValuesByRecord = getContentValuesByRecord(it.next());
                            String str2 = TAG;
                            ConsoleLogUtils.logcatV(str2, "ContentValues");
                            jInsert = getWriteDatabase().insert(this.mTableName, null, contentValuesByRecord);
                            ConsoleLogUtils.logcatV(str2, "insert");
                            if (jInsert < 0 && getCount(getWriteDatabase()) > 0) {
                                deleteOldest(getWriteDatabase(), getCount(getWriteDatabase()) / 2);
                                jInsert = getWriteDatabase().insert(this.mTableName, null, contentValuesByRecord);
                            }
                            ConsoleLogUtils.logcatV(str2, "insert: id=" + jInsert);
                        }
                        getWriteDatabase().setTransactionSuccessful();
                        String str3 = TAG;
                        StringBuilder sb2 = new StringBuilder("final ");
                        sb2.append(getWriteDatabase() == null);
                        ConsoleLogUtils.logcatV(str3, sb2.toString());
                        this.mDatabase.endTransaction();
                        this.semaphore.release();
                        close();
                        return jInsert >= 0;
                    }
                } catch (Exception e2) {
                    throw new DbException("Insert record failed!", e2);
                }
            } catch (Throwable unused) {
                String str4 = TAG;
                StringBuilder sb3 = new StringBuilder("final ");
                sb3.append(getWriteDatabase() == null);
                ConsoleLogUtils.logcatV(str4, sb3.toString());
                this.mDatabase.endTransaction();
                this.semaphore.release();
                close();
                return jInsert >= 0;
            }
        }
        String str5 = TAG;
        StringBuilder sb4 = new StringBuilder("final ");
        if (getWriteDatabase() != null) {
            z2 = false;
        }
        sb4.append(z2);
        ConsoleLogUtils.logcatV(str5, sb4.toString());
        this.mDatabase.endTransaction();
        this.semaphore.release();
        close();
        return false;
    }

    public abstract T parseDataFromCursor(Cursor cursor);

    public synchronized List<T> query(int i2, int i3, String str) {
        String str2;
        String[] strArr;
        ArrayList arrayList;
        if (i3 >= 0) {
            try {
                str2 = "upload_flag = ?";
                strArr = new String[]{String.valueOf(i3)};
            } catch (Throwable unused) {
                close();
                return null;
            }
        } else {
            str2 = null;
            strArr = null;
        }
        ConsoleLogUtils.logcatV(TAG, "query: selection=" + str2);
        String strValueOf = i2 > 0 ? String.valueOf(i2) : "";
        arrayList = new ArrayList();
        Cursor cursorQuery = getReadDatabase().query(this.mTableName, null, str2, strArr, null, null, str, strValueOf);
        while (cursorQuery.moveToNext()) {
            ACMRecord dataFromCursor = parseDataFromCursor(cursorQuery);
            if (dataFromCursor != null) {
                arrayList.add(dataFromCursor);
            }
        }
        cursorQuery.close();
        ConsoleLogUtils.logcatV(TAG, "query: result=" + arrayList + ", size=" + arrayList.size());
        close();
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0078 A[Catch: all -> 0x00d5, TryCatch #0 {all -> 0x00d5, blocks: (B:4:0x0002, B:10:0x0026, B:11:0x003b, B:17:0x0063, B:19:0x0078, B:20:0x007c, B:21:0x009e, B:23:0x00a4, B:25:0x00aa, B:26:0x00ae, B:13:0x0041, B:16:0x0054), top: B:37:0x0002, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00a4 A[Catch: all -> 0x00d5, TryCatch #0 {all -> 0x00d5, blocks: (B:4:0x0002, B:10:0x0026, B:11:0x003b, B:17:0x0063, B:19:0x0078, B:20:0x007c, B:21:0x009e, B:23:0x00a4, B:25:0x00aa, B:26:0x00ae, B:13:0x0041, B:16:0x0054), top: B:37:0x0002, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized java.util.List<T> queryFailed(long r14, long r16, int r18) {
        /*
            r13 = this;
            r1 = r13
            monitor-enter(r13)
            java.lang.String r0 = ""
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld5
            r2.<init>()     // Catch: java.lang.Throwable -> Ld5
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Ld5
            r3.<init>()     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r4 = "1"
            r3.add(r4)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r4 = "upload_flag = ?"
            r2.append(r4)     // Catch: java.lang.Throwable -> Ld5
            r4 = 0
            int r6 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            if (r6 < 0) goto L3f
            int r7 = (r16 > r4 ? 1 : (r16 == r4 ? 0 : -1))
            if (r7 < 0) goto L3f
            int r7 = (r16 > r14 ? 1 : (r16 == r14 ? 0 : -1))
            if (r7 < 0) goto L3f
            java.lang.String r4 = java.lang.String.valueOf(r14)     // Catch: java.lang.Throwable -> Ld5
            r3.add(r4)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r4 = java.lang.String.valueOf(r16)     // Catch: java.lang.Throwable -> Ld5
            r3.add(r4)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r4 = " and _id"
            r2.append(r4)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r4 = " between ? and ?"
        L3b:
            r2.append(r4)     // Catch: java.lang.Throwable -> Ld5
            goto L63
        L3f:
            if (r6 < 0) goto L50
            java.lang.String r4 = java.lang.String.valueOf(r14)     // Catch: java.lang.Throwable -> Ld5
            r3.add(r4)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r4 = " and _id"
            r2.append(r4)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r4 = " >= ?"
            goto L3b
        L50:
            int r4 = (r16 > r4 ? 1 : (r16 == r4 ? 0 : -1))
            if (r4 < 0) goto L63
            java.lang.String r4 = java.lang.String.valueOf(r16)     // Catch: java.lang.Throwable -> Ld5
            r3.add(r4)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r4 = " and _id"
            r2.append(r4)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r4 = " <= ?"
            goto L3b
        L63:
            java.lang.String r4 = com.nirvana.tools.logger.cache.db.AbstractDatabase.TAG     // Catch: java.lang.Throwable -> Ld5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r6 = "query: selection="
            r5.<init>(r6)     // Catch: java.lang.Throwable -> Ld5
            r5.append(r2)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> Ld5
            com.nirvana.tools.logger.utils.ConsoleLogUtils.logcatV(r4, r5)     // Catch: java.lang.Throwable -> Ld5
            if (r18 <= 0) goto L7c
            java.lang.String r0 = java.lang.String.valueOf(r18)     // Catch: java.lang.Throwable -> Ld5
        L7c:
            r12 = r0
            int r0 = r3.size()     // Catch: java.lang.Throwable -> Ld5
            java.lang.String[] r8 = new java.lang.String[r0]     // Catch: java.lang.Throwable -> Ld5
            r3.toArray(r8)     // Catch: java.lang.Throwable -> Ld5
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Ld5
            r0.<init>()     // Catch: java.lang.Throwable -> Ld5
            android.database.sqlite.SQLiteDatabase r4 = r13.getReadDatabase()     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r5 = r1.mTableName     // Catch: java.lang.Throwable -> Ld5
            r6 = 0
            java.lang.String r7 = r2.toString()     // Catch: java.lang.Throwable -> Ld5
            r9 = 0
            r10 = 0
            java.lang.String r11 = "_id ASC"
            android.database.Cursor r2 = r4.query(r5, r6, r7, r8, r9, r10, r11, r12)     // Catch: java.lang.Throwable -> Ld5
        L9e:
            boolean r3 = r2.moveToNext()     // Catch: java.lang.Throwable -> Ld5
            if (r3 == 0) goto Lae
            com.nirvana.tools.logger.model.ACMRecord r3 = r13.parseDataFromCursor(r2)     // Catch: java.lang.Throwable -> Ld5
            if (r3 == 0) goto L9e
            r0.add(r3)     // Catch: java.lang.Throwable -> Ld5
            goto L9e
        Lae:
            r2.close()     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r2 = com.nirvana.tools.logger.cache.db.AbstractDatabase.TAG     // Catch: java.lang.Throwable -> Ld5
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r4 = "query: result="
            r3.<init>(r4)     // Catch: java.lang.Throwable -> Ld5
            r3.append(r0)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r4 = ", size="
            r3.append(r4)     // Catch: java.lang.Throwable -> Ld5
            int r4 = r0.size()     // Catch: java.lang.Throwable -> Ld5
            r3.append(r4)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> Ld5
            com.nirvana.tools.logger.utils.ConsoleLogUtils.logcatV(r2, r3)     // Catch: java.lang.Throwable -> Ld5
            r13.close()     // Catch: java.lang.Throwable -> Ldb
            monitor-exit(r13)
            return r0
        Ld5:
            r13.close()     // Catch: java.lang.Throwable -> Ldb
            monitor-exit(r13)
            r0 = 0
            return r0
        Ldb:
            r0 = move-exception
            monitor-exit(r13)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nirvana.tools.logger.cache.db.AbstractDatabase.queryFailed(long, long, int):java.util.List");
    }

    public synchronized long queryFailedMaxId() {
        long j2;
        try {
            Cursor cursorQuery = getReadDatabase().query(true, this.mTableName, new String[]{aq.f22519d}, "upload_flag=?", new String[]{"1"}, null, null, "_id desc", null);
            cursorQuery.moveToFirst();
            j2 = cursorQuery.getLong(0);
            cursorQuery.close();
            close();
        } catch (Throwable unused) {
            close();
            return -1L;
        }
        return j2;
    }

    public synchronized void setMaxSizeLog(long j2) {
        try {
            try {
                getWriteDatabase().setMaximumSize(j2);
            } finally {
                close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public synchronized void updateUploadCount(List<T> list, long j2, int i2) throws DbException {
        if (list != null) {
            try {
                if (list.size() != 0) {
                    ArrayList arrayList = new ArrayList();
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(Long.valueOf(it.next().getId()));
                    }
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(String.valueOf(j2));
                    arrayList2.add(String.valueOf(i2));
                    arrayList2.add("1");
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        arrayList2.add(String.valueOf((Long) it2.next()));
                    }
                    String[] strArr = new String[arrayList2.size()];
                    String str = String.format("Update %s SET %s=?, %s=?, %s= %s+? where %s in %s", this.mTableName, "timestamp", DBHelpTool.RecordEntry.COLUMN_UPLOAD_FLAG, DBHelpTool.RecordEntry.COLUMN_UPLOAD_COUNT, DBHelpTool.RecordEntry.COLUMN_UPLOAD_COUNT, aq.f22519d, contactIds(arrayList.size()));
                    arrayList2.toArray(strArr);
                    getWriteDatabase().execSQL(str, strArr);
                }
            } finally {
            }
        }
    }
}
