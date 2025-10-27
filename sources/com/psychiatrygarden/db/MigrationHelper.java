package com.psychiatrygarden.db;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.sdk.util.h;
import com.psychiatrygarden.bean.DaoMaster;
import com.psychiatrygarden.bean.DaoMasterTiKu;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes5.dex */
public class MigrationHelper {
    private static final String CONVERSION_CLASS_NOT_FOUND_EXCEPTION = "MIGRATION HELPER - CLASS DOESN'T MATCH WITH THE CURRENT PARAMETERS";
    private static MigrationHelper instance;

    private void generateTempTables(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) throws SQLException {
        String typeByClass;
        for (Class<? extends AbstractDao<?, ?>> cls : daoClasses) {
            DaoConfig daoConfig = new DaoConfig(db, cls);
            String str = daoConfig.tablename;
            String strConcat = str.concat("_TEMP");
            ArrayList arrayList = new ArrayList();
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE ");
            sb.append(strConcat);
            sb.append(" (");
            String str2 = "";
            int i2 = 0;
            while (true) {
                Property[] propertyArr = daoConfig.properties;
                if (i2 < propertyArr.length) {
                    String str3 = propertyArr[i2].columnName;
                    if (getColumns(db, str).contains(str3)) {
                        arrayList.add(str3);
                        try {
                            typeByClass = getTypeByClass(daoConfig.properties[i2].type);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            typeByClass = null;
                        }
                        sb.append(str2);
                        sb.append(str3);
                        sb.append(" ");
                        sb.append(typeByClass);
                        if (daoConfig.properties[i2].primaryKey) {
                            sb.append(" PRIMARY KEY");
                        }
                        str2 = ",";
                    }
                    i2++;
                }
            }
            sb.append(");");
            db.execSQL(sb.toString());
            db.execSQL("INSERT INTO " + strConcat + " (" + TextUtils.join(",", arrayList) + ") SELECT " + TextUtils.join(",", arrayList) + " FROM " + str + h.f3376b);
        }
    }

    private static List<String> getColumns(SQLiteDatabase db, String tableName) {
        ArrayList arrayList = new ArrayList();
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = db.rawQuery("SELECT * FROM " + tableName + " limit 1", null);
                if (cursorRawQuery != null) {
                    arrayList = new ArrayList(Arrays.asList(cursorRawQuery.getColumnNames()));
                }
            } catch (Exception e2) {
                Log.v(tableName, e2.getMessage(), e2);
                e2.printStackTrace();
                if (cursorRawQuery != null) {
                }
            }
            return arrayList;
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }

    public static MigrationHelper getInstance() {
        if (instance == null) {
            instance = new MigrationHelper();
        }
        return instance;
    }

    private String getTypeByClass(Class<?> type) throws Exception {
        if (type.equals(String.class)) {
            return "TEXT";
        }
        if (type.equals(Long.class) || type.equals(Integer.class) || type.equals(Long.TYPE)) {
            return "INTEGER";
        }
        if (type.equals(Boolean.class)) {
            return "BOOLEAN";
        }
        Exception exc = new Exception(CONVERSION_CLASS_NOT_FOUND_EXCEPTION.concat(" - Class: ").concat(type.toString()));
        exc.printStackTrace();
        throw exc;
    }

    private void restoreData(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) throws SQLException {
        for (Class<? extends AbstractDao<?, ?>> cls : daoClasses) {
            DaoConfig daoConfig = new DaoConfig(db, cls);
            String str = daoConfig.tablename;
            String strConcat = str.concat("_TEMP");
            ArrayList arrayList = new ArrayList();
            int i2 = 0;
            while (true) {
                Property[] propertyArr = daoConfig.properties;
                if (i2 < propertyArr.length) {
                    String str2 = propertyArr[i2].columnName;
                    if (getColumns(db, strConcat).contains(str2)) {
                        arrayList.add(str2);
                    }
                    i2++;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("DROP TABLE ");
            sb.append(strConcat);
            db.execSQL("INSERT INTO " + str + " (" + TextUtils.join(",", arrayList) + ") SELECT " + TextUtils.join(",", arrayList) + " FROM " + strConcat + h.f3376b);
            db.execSQL(sb.toString());
        }
    }

    public void migrate(int type, SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) throws SQLException {
        generateTempTables(db, daoClasses);
        if (type == 1) {
            DaoMaster.dropAllTables(db, true);
            DaoMaster.createAllTables(db, false);
        } else {
            DaoMasterTiKu.dropAllTables(db, true);
            DaoMasterTiKu.createAllTables(db, false);
        }
        restoreData(db, daoClasses);
    }
}
