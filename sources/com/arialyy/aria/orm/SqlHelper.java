package com.arialyy.aria.orm;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;
import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.ThreadRecord;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.M3U8Entity;
import com.arialyy.aria.core.upload.UploadEntity;
import com.arialyy.aria.util.ALog;
import com.tencent.smtt.sdk.TbsReaderView;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
final class SqlHelper extends SQLiteOpenHelper {
    private static volatile SqlHelper INSTANCE = null;
    private static final String TAG = "SqlHelper";
    private static boolean mainTmpDirSet = false;
    private Context mContext;

    private SqlHelper(Context context) {
        super(context, DBConfig.DB_NAME, (SQLiteDatabase.CursorFactory) null, DBConfig.VERSION);
        this.mContext = context;
    }

    private void addTaskRecordType(SQLiteDatabase sQLiteDatabase) {
        try {
            SqlUtil.checkOrCreateTable(sQLiteDatabase, ThreadRecord.class);
            SqlUtil.checkOrCreateTable(sQLiteDatabase, TaskRecord.class);
            SqlUtil.checkOrCreateTable(sQLiteDatabase, UploadEntity.class);
            SqlUtil.checkOrCreateTable(sQLiteDatabase, DownloadEntity.class);
            sQLiteDatabase.beginTransaction();
            Cursor cursorRawQuery = sQLiteDatabase.rawQuery("SELECT downloadPath, url FROM DownloadEntity", null);
            while (true) {
                int i2 = 3;
                if (!cursorRawQuery.moveToNext()) {
                    break;
                }
                String string = cursorRawQuery.getString(0);
                String string2 = cursorRawQuery.getString(1);
                if (!string2.startsWith("ftp") && !string2.startsWith("sftp")) {
                    if (SqlUtil.tableExists(sQLiteDatabase, (Class<? extends DbEntity>) M3U8Entity.class)) {
                        Cursor cursorRawQuery2 = sQLiteDatabase.rawQuery("SELECT isLive FROM M3U8Entity WHERE filePath=\"" + SqlUtil.encodeStr(string) + "\"", null);
                        if (cursorRawQuery2.moveToNext()) {
                            String string3 = cursorRawQuery2.getString(0);
                            i2 = (!TextUtils.isEmpty(string3) && Boolean.valueOf(string3).booleanValue()) ? 8 : 7;
                        } else {
                            i2 = 1;
                        }
                        cursorRawQuery2.close();
                    } else {
                        i2 = 1;
                    }
                }
                sQLiteDatabase.execSQL("UPDATE DownloadEntity SET taskType=? WHERE downloadPath=?", new Object[]{Integer.valueOf(i2), string});
                sQLiteDatabase.execSQL("UPDATE TaskRecord SET taskType=? WHERE filePath=?", new Object[]{Integer.valueOf(i2), string});
                sQLiteDatabase.execSQL("UPDATE ThreadRecord SET threadType=? WHERE taskKey=?", new Object[]{Integer.valueOf(i2), string});
            }
            cursorRawQuery.close();
            Cursor cursorRawQuery3 = sQLiteDatabase.rawQuery("SELECT filePath, url FROM UploadEntity", null);
            while (cursorRawQuery3.moveToNext()) {
                String string4 = cursorRawQuery3.getString(cursorRawQuery3.getColumnIndex(TbsReaderView.KEY_FILE_PATH));
                String string5 = cursorRawQuery3.getString(cursorRawQuery3.getColumnIndex("url"));
                int i3 = (string5.startsWith("ftp") || string5.startsWith("sftp")) ? 3 : 1;
                sQLiteDatabase.execSQL("UPDATE UploadEntity SET taskType=? WHERE filePath=?", new Object[]{Integer.valueOf(i3), string4});
                sQLiteDatabase.execSQL("UPDATE TaskRecord SET taskType=? WHERE filePath=?", new Object[]{Integer.valueOf(i3), string4});
                sQLiteDatabase.execSQL("UPDATE ThreadRecord SET threadType=? WHERE taskKey=?", new Object[]{Integer.valueOf(i3), string4});
            }
            cursorRawQuery3.close();
            sQLiteDatabase.setTransactionSuccessful();
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    private void createDbCacheDir() throws SQLException {
        String str = this.mContext.getCacheDir().getPath() + "/AriaDbCacheDir";
        File file = new File(str);
        if (!file.exists()) {
            ALog.d(TAG, file.mkdirs() + "");
        }
        super.getReadableDatabase().execSQL("PRAGMA temp_store_directory = '" + str + "'");
        mainTmpDirSet = true;
    }

    private void delRepeatThreadRecord(SQLiteDatabase sQLiteDatabase) throws SecurityException, SQLException {
        SqlUtil.checkOrCreateTable(sQLiteDatabase, ThreadRecord.class);
        ALog.d(TAG, "DELETE FROM ThreadRecord WHERE (rowid) IN (SELECT rowid FROM ThreadRecord GROUP BY taskKey, threadId, endLocation HAVING COUNT(*) > 1) AND rowid NOT IN (SELECT MIN(rowid) FROM ThreadRecord GROUP BY taskKey, threadId, endLocation HAVING COUNT(*)> 1)");
        sQLiteDatabase.execSQL("DELETE FROM ThreadRecord WHERE (rowid) IN (SELECT rowid FROM ThreadRecord GROUP BY taskKey, threadId, endLocation HAVING COUNT(*) > 1) AND rowid NOT IN (SELECT MIN(rowid) FROM ThreadRecord GROUP BY taskKey, threadId, endLocation HAVING COUNT(*)> 1)");
    }

    private List<String> getDiffColumn(List<String> list, List<String> list2) {
        ArrayList arrayList = new ArrayList(list2);
        arrayList.removeAll(list);
        return arrayList;
    }

    public static SqlHelper getInstance() {
        return INSTANCE;
    }

    private List<String> getNewColumn(List<String> list, List<String> list2) {
        ArrayList arrayList = new ArrayList(list);
        arrayList.removeAll(list2);
        return arrayList;
    }

    private void handle360AriaUpdate(SQLiteDatabase sQLiteDatabase) throws SecurityException, SQLException {
        String[] strArr = {"UploadTaskEntity", "DownloadTaskEntity", "DownloadGroupTaskEntity"};
        for (int i2 = 0; i2 < 3; i2++) {
            String str = strArr[i2];
            if (SqlUtil.tableExists(sQLiteDatabase, str)) {
                SqlUtil.dropTable(sQLiteDatabase, str);
            }
        }
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        map2.put("groupName", "groupHash");
        map.put("DownloadEntity", map2);
        map.put("DownloadGroupEntity", map2);
        HashMap map3 = new HashMap();
        map3.put("dGroupName", "dGroupHash");
        map.put("TaskRecord", map3);
        HashMap map4 = new HashMap();
        map4.put("key", "taskKey");
        map.put("ThreadRecord", map4);
        handleDbUpdate(sQLiteDatabase, map);
        delRepeatThreadRecord(sQLiteDatabase);
    }

    private void handle365Update(SQLiteDatabase sQLiteDatabase) throws SecurityException, SQLException {
        SqlUtil.checkOrCreateTable(sQLiteDatabase, ThreadRecord.class);
        sQLiteDatabase.execSQL("UPDATE ThreadRecord SET threadId=0 WHERE threadId=-1");
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        map2.put("key", "taskKey");
        map.put("ThreadRecord", map2);
        handleDbUpdate(sQLiteDatabase, map);
        delRepeatThreadRecord(sQLiteDatabase);
    }

    private void handle366Update(SQLiteDatabase sQLiteDatabase) throws SecurityException, SQLException {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        map2.put("key", "taskKey");
        map.put("ThreadRecord", map2);
        handleDbUpdate(sQLiteDatabase, map);
        delRepeatThreadRecord(sQLiteDatabase);
    }

    private void handleDbUpdate(SQLiteDatabase sQLiteDatabase, Map<String, Map<String, String>> map) {
        Map<String, String> map2;
        String strReplace;
        if (sQLiteDatabase == null) {
            ALog.e(TAG, "db 为 null");
            return;
        }
        try {
            if (!sQLiteDatabase.isOpen()) {
                ALog.e(TAG, "db已关闭");
                return;
            }
            try {
                sQLiteDatabase.beginTransaction();
                for (String str : DBConfig.mapping.keySet()) {
                    Class<? extends DbEntity> cls = DBConfig.mapping.get(str);
                    if (SqlUtil.tableExists(sQLiteDatabase, cls)) {
                        Cursor cursorRawQuery = sQLiteDatabase.rawQuery(String.format("PRAGMA table_info(%s)", str), null);
                        List<String> columns = SqlUtil.getColumns(cls);
                        ArrayList arrayList = new ArrayList();
                        while (cursorRawQuery.moveToNext()) {
                            arrayList.add(cursorRawQuery.getString(cursorRawQuery.getColumnIndex("name")));
                        }
                        cursorRawQuery.close();
                        List<String> newColumn = getNewColumn(columns, arrayList);
                        if (map != null) {
                            map2 = map.get(str);
                            if (map2 != null) {
                                Iterator<String> it = newColumn.iterator();
                                while (it.hasNext()) {
                                    if (map2.get(it.next()) != null) {
                                        it.remove();
                                    }
                                }
                            }
                        } else {
                            map2 = null;
                        }
                        int i2 = 3;
                        if (newColumn.size() > 0) {
                            for (String str2 : newColumn) {
                                Object[] objArr = new Object[i2];
                                objArr[0] = str;
                                objArr[1] = str2;
                                objArr[2] = SqlUtil.getColumnTypeByFieldName(cls, str2);
                                String str3 = String.format("ALTER TABLE %s ADD COLUMN %s %s", objArr);
                                ALog.d(TAG, "添加表字段的sql：" + str3);
                                sQLiteDatabase.execSQL(str3);
                                i2 = 3;
                            }
                        }
                        sQLiteDatabase.execSQL(String.format("ALTER TABLE %s RENAME TO %s_temp", str, str));
                        SqlUtil.createTable(sQLiteDatabase, cls);
                        Cursor cursorRawQuery2 = sQLiteDatabase.rawQuery(String.format("SELECT COUNT(*) FROM %s_temp", str), null);
                        cursorRawQuery2.moveToFirst();
                        long j2 = cursorRawQuery2.getLong(0);
                        cursorRawQuery2.close();
                        if (j2 > 0) {
                            List<String> diffColumn = getDiffColumn(columns, arrayList);
                            StringBuilder sb = new StringBuilder();
                            if (map != null) {
                                map2 = map.get(str);
                            }
                            for (String str4 : arrayList) {
                                if (diffColumn.isEmpty() || !diffColumn.contains(str4) || (map2 != null && map2.containsKey(str4))) {
                                    sb.append(str4);
                                    sb.append(",");
                                }
                            }
                            String string = sb.toString();
                            String strSubstring = string.substring(0, string.length() - 1);
                            if (map2 == null || map2.isEmpty()) {
                                strReplace = strSubstring;
                            } else {
                                strReplace = strSubstring;
                                for (String str5 : map2.keySet()) {
                                    if (strReplace.contains(str5)) {
                                        strReplace = strReplace.replace(str5, map2.get(str5));
                                    }
                                }
                            }
                            String str6 = String.format("INSERT INTO %s (%s) SELECT %s FROM %s_temp", str, strReplace, strSubstring, str);
                            ALog.d(TAG, "恢复数据的sql：" + str6);
                            sQLiteDatabase.execSQL(str6);
                        }
                        SqlUtil.dropTable(sQLiteDatabase, str + "_temp");
                    } else {
                        SqlUtil.createTable(sQLiteDatabase, cls);
                    }
                }
                sQLiteDatabase.setTransactionSuccessful();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    private void handleLowAriaUpdate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        String[] strArr = {"UploadTaskEntity", "DownloadTaskEntity", "DownloadGroupTaskEntity"};
        for (int i2 = 0; i2 < 3; i2++) {
            String str = strArr[i2];
            if (SqlUtil.tableExists(sQLiteDatabase, str)) {
                SqlUtil.dropTable(sQLiteDatabase, str);
            }
        }
        String[] strArr2 = {"DownloadEntity", "DownloadGroupEntity"};
        String[] strArr3 = {"downloadPath", "groupName"};
        int i3 = 0;
        for (int i4 = 0; i4 < 2; i4++) {
            String str2 = strArr2[i4];
            if (SqlUtil.tableExists(sQLiteDatabase, str2)) {
                String str3 = strArr3[i3];
                String str4 = String.format("DELETE FROM %s WHERE %s='' OR %s IS NULL", str2, str3, str3);
                ALog.d(TAG, str4);
                sQLiteDatabase.execSQL(str4);
                String str5 = String.format("DELETE FROM %s WHERE %s IN(SELECT %s FROM %s GROUP BY %s HAVING COUNT(%s) > 1)", str2, str3, str3, str2, str3, str3);
                ALog.d(TAG, str5);
                sQLiteDatabase.execSQL(str5);
                i3++;
            }
        }
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        map2.put("groupName", "groupHash");
        map2.put("downloadUrl", "url");
        map2.put("isDownloadComplete", "isComplete");
        map.put("DownloadEntity", map2);
        HashMap map3 = new HashMap();
        map3.put("groupName", "groupHash");
        map.put("DownloadGroupEntity", map3);
        handleDbUpdate(sQLiteDatabase, map);
    }

    public static synchronized SqlHelper init(Context context) {
        if (INSTANCE == null) {
            synchronized (SqlHelper.class) {
                INSTANCE = new SqlHelper(context.getApplicationContext());
            }
        }
        return INSTANCE;
    }

    public SQLiteDatabase getDb() throws SQLException {
        SQLiteDatabase sQLiteDatabaseOpenDatabase;
        if (Build.VERSION.SDK_INT >= 28) {
            setOpenParams(new SQLiteDatabase.OpenParams.Builder().setOpenFlags(268435472).build());
            sQLiteDatabaseOpenDatabase = getWritableDatabase();
        } else {
            File databasePath = this.mContext.getDatabasePath(DBConfig.DB_NAME);
            if (databasePath.exists()) {
                getReadableDatabase().close();
                sQLiteDatabaseOpenDatabase = SQLiteDatabase.openDatabase(databasePath.getPath(), null, 268435472);
            } else {
                sQLiteDatabaseOpenDatabase = getWritableDatabase();
            }
        }
        sQLiteDatabaseOpenDatabase.enableWriteAheadLogging();
        return sQLiteDatabaseOpenDatabase;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public SQLiteDatabase getReadableDatabase() throws SQLException {
        if (mainTmpDirSet) {
            return super.getReadableDatabase();
        }
        createDbCacheDir();
        return super.getReadableDatabase();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public SQLiteDatabase getWritableDatabase() throws SQLException {
        if (mainTmpDirSet) {
            return super.getWritableDatabase();
        }
        createDbCacheDir();
        return super.getWritableDatabase();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onConfigure(SQLiteDatabase sQLiteDatabase) {
        super.onConfigure(sQLiteDatabase);
        sQLiteDatabase.setForeignKeyConstraintsEnabled(true);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SecurityException, SQLException {
        Iterator<String> it = DBConfig.mapping.keySet().iterator();
        while (it.hasNext()) {
            Class<? extends DbEntity> cls = DBConfig.mapping.get(it.next());
            if (!SqlUtil.tableExists(sQLiteDatabase, cls)) {
                SqlUtil.createTable(sQLiteDatabase, cls);
            }
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
        if (i2 > i3) {
            handleDbUpdate(sQLiteDatabase, null);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onOpen(SQLiteDatabase sQLiteDatabase) {
        super.onOpen(sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) throws SecurityException, SQLException {
        if (i2 < i3) {
            if (i2 < 31) {
                handleLowAriaUpdate(sQLiteDatabase);
            } else if (i2 < 45) {
                handle360AriaUpdate(sQLiteDatabase);
            } else if (i2 < 51) {
                handle365Update(sQLiteDatabase);
            } else if (i2 < 53) {
                handle366Update(sQLiteDatabase);
            } else {
                handleDbUpdate(sQLiteDatabase, null);
            }
            if (i3 == 57) {
                addTaskRecordType(sQLiteDatabase);
            }
        }
    }
}
