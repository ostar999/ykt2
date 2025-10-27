package net.tsz.afinal.db.sqlite;

import android.database.Cursor;
import java.util.HashMap;
import java.util.Map;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.db.table.ManyToOne;
import net.tsz.afinal.db.table.OneToMany;
import net.tsz.afinal.db.table.Property;
import net.tsz.afinal.db.table.TableInfo;

/* loaded from: classes9.dex */
public class CursorUtils {
    public static <T> T dbModel2Entity(DbModel dbModel, Class<?> cls) {
        if (dbModel != null) {
            HashMap<String, Object> dataMap = dbModel.getDataMap();
            try {
                T t2 = (T) cls.newInstance();
                for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                    String key = entry.getKey();
                    TableInfo tableInfo = TableInfo.get(cls);
                    Property property = tableInfo.propertyMap.get(key);
                    if (property != null) {
                        property.setValue(t2, entry.getValue() == null ? null : entry.getValue().toString());
                    } else if (tableInfo.getId().getColumn().equals(key)) {
                        tableInfo.getId().setValue(t2, entry.getValue() == null ? null : entry.getValue().toString());
                    }
                }
                return t2;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static DbModel getDbModel(Cursor cursor) {
        if (cursor == null || cursor.getColumnCount() <= 0) {
            return null;
        }
        DbModel dbModel = new DbModel();
        int columnCount = cursor.getColumnCount();
        for (int i2 = 0; i2 < columnCount; i2++) {
            dbModel.set(cursor.getColumnName(i2), cursor.getString(i2));
        }
        return dbModel;
    }

    public static <T> T getEntity(Cursor cursor, Class<T> cls, FinalDb finalDb) throws IllegalAccessException, InstantiationException {
        if (cursor == null) {
            return null;
        }
        try {
            TableInfo tableInfo = TableInfo.get((Class<?>) cls);
            int columnCount = cursor.getColumnCount();
            if (columnCount <= 0) {
                return null;
            }
            T tNewInstance = cls.newInstance();
            for (int i2 = 0; i2 < columnCount; i2++) {
                String columnName = cursor.getColumnName(i2);
                Property property = tableInfo.propertyMap.get(columnName);
                if (property != null) {
                    property.setValue(tNewInstance, cursor.getString(i2));
                } else if (tableInfo.getId().getColumn().equals(columnName)) {
                    tableInfo.getId().setValue(tNewInstance, cursor.getString(i2));
                }
            }
            for (OneToMany oneToMany : tableInfo.oneToManyMap.values()) {
                if (oneToMany.getDataType() == OneToManyLazyLoader.class) {
                    oneToMany.setValue(tNewInstance, new OneToManyLazyLoader(tNewInstance, cls, oneToMany.getOneClass(), finalDb));
                }
            }
            for (ManyToOne manyToOne : tableInfo.manyToOneMap.values()) {
                if (manyToOne.getDataType() == ManyToOneLazyLoader.class) {
                    manyToOne.setValue(tNewInstance, new ManyToOneLazyLoader(tNewInstance, cls, manyToOne.getManyClass(), finalDb));
                }
            }
            return tNewInstance;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
