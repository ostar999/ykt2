package com.arialyy.aria.orm;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import com.arialyy.aria.util.CommonUtil;
import com.plv.foundationsdk.log.PLVLogType;
import java.io.File;
import java.io.IOException;

/* loaded from: classes2.dex */
class DatabaseContext extends ContextWrapper {
    public DatabaseContext(Context context) {
        super(context);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public File getDatabasePath(String str) throws IOException {
        boolean zCreateNewFile;
        String str2 = CommonUtil.getAppPath(getBaseContext()) + PLVLogType.DB;
        String str3 = str2 + "/" + str;
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(str3);
        if (file2.exists()) {
            zCreateNewFile = true;
        } else {
            try {
                zCreateNewFile = file2.createNewFile();
            } catch (IOException e2) {
                e2.printStackTrace();
                zCreateNewFile = false;
            }
        }
        if (zCreateNewFile) {
            return file2;
        }
        return null;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public SQLiteDatabase openOrCreateDatabase(String str, int i2, SQLiteDatabase.CursorFactory cursorFactory) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(str), (SQLiteDatabase.CursorFactory) null);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public SQLiteDatabase openOrCreateDatabase(String str, int i2, SQLiteDatabase.CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(str), (SQLiteDatabase.CursorFactory) null);
    }
}
