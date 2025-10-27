package de.greenrobot.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes8.dex */
public class DbUtils {
    public static int copyAllBytes(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[4096];
        int i2 = 0;
        while (true) {
            int i3 = inputStream.read(bArr);
            if (i3 == -1) {
                return i2;
            }
            outputStream.write(bArr, 0, i3);
            i2 += i3;
        }
    }

    public static int executeSqlScript(Context context, SQLiteDatabase sQLiteDatabase, String str) throws IOException {
        return executeSqlScript(context, sQLiteDatabase, str, true);
    }

    public static int executeSqlStatements(SQLiteDatabase sQLiteDatabase, String[] strArr) throws SQLException {
        int i2 = 0;
        for (String str : strArr) {
            String strTrim = str.trim();
            if (strTrim.length() > 0) {
                sQLiteDatabase.execSQL(strTrim);
                i2++;
            }
        }
        return i2;
    }

    public static int executeSqlStatementsInTx(SQLiteDatabase sQLiteDatabase, String[] strArr) {
        sQLiteDatabase.beginTransaction();
        try {
            int iExecuteSqlStatements = executeSqlStatements(sQLiteDatabase, strArr);
            sQLiteDatabase.setTransactionSuccessful();
            return iExecuteSqlStatements;
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    public static void logTableDump(SQLiteDatabase sQLiteDatabase, String str) {
        Cursor cursorQuery = sQLiteDatabase.query(str, null, null, null, null, null, null);
        try {
            DaoLog.d(DatabaseUtils.dumpCursorToString(cursorQuery));
        } finally {
            cursorQuery.close();
        }
    }

    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copyAllBytes(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] readAsset(Context context, String str) throws IOException {
        InputStream inputStreamOpen = context.getResources().getAssets().open(str);
        try {
            return readAllBytes(inputStreamOpen);
        } finally {
            inputStreamOpen.close();
        }
    }

    public static void vacuum(SQLiteDatabase sQLiteDatabase) throws SQLException {
        sQLiteDatabase.execSQL("VACUUM");
    }

    public static int executeSqlScript(Context context, SQLiteDatabase sQLiteDatabase, String str, boolean z2) throws IOException {
        String[] strArrSplit = new String(readAsset(context, str), "UTF-8").split(";(\\s)*[\n\r]");
        int iExecuteSqlStatementsInTx = z2 ? executeSqlStatementsInTx(sQLiteDatabase, strArrSplit) : executeSqlStatements(sQLiteDatabase, strArrSplit);
        DaoLog.i("Executed " + iExecuteSqlStatementsInTx + " statements from SQL script '" + str + "'");
        return iExecuteSqlStatementsInTx;
    }
}
