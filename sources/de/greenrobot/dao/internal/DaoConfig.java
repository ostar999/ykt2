package de.greenrobot.dao.internal;

import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoException;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.identityscope.IdentityScope;
import de.greenrobot.dao.identityscope.IdentityScopeLong;
import de.greenrobot.dao.identityscope.IdentityScopeObject;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes8.dex */
public final class DaoConfig implements Cloneable {
    public final String[] allColumns;
    public final SQLiteDatabase db;
    private IdentityScope<?, ?> identityScope;
    public final boolean keyIsNumeric;
    public final String[] nonPkColumns;
    public final String[] pkColumns;
    public final Property pkProperty;
    public final Property[] properties;
    public final TableStatements statements;
    public final String tablename;

    public DaoConfig(SQLiteDatabase sQLiteDatabase, Class<? extends AbstractDao<?, ?>> cls) {
        this.db = sQLiteDatabase;
        try {
            this.tablename = (String) cls.getField("TABLENAME").get(null);
            Property[] propertyArrReflectProperties = reflectProperties(cls);
            this.properties = propertyArrReflectProperties;
            this.allColumns = new String[propertyArrReflectProperties.length];
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Property property = null;
            for (int i2 = 0; i2 < propertyArrReflectProperties.length; i2++) {
                Property property2 = propertyArrReflectProperties[i2];
                String str = property2.columnName;
                this.allColumns[i2] = str;
                if (property2.primaryKey) {
                    arrayList.add(str);
                    property = property2;
                } else {
                    arrayList2.add(str);
                }
            }
            this.nonPkColumns = (String[]) arrayList2.toArray(new String[arrayList2.size()]);
            String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
            this.pkColumns = strArr;
            Property property3 = strArr.length == 1 ? property : null;
            this.pkProperty = property3;
            this.statements = new TableStatements(sQLiteDatabase, this.tablename, this.allColumns, strArr);
            if (property3 == null) {
                this.keyIsNumeric = false;
            } else {
                Class<?> cls2 = property3.type;
                this.keyIsNumeric = cls2.equals(Long.TYPE) || cls2.equals(Long.class) || cls2.equals(Integer.TYPE) || cls2.equals(Integer.class) || cls2.equals(Short.TYPE) || cls2.equals(Short.class) || cls2.equals(Byte.TYPE) || cls2.equals(Byte.class);
            }
        } catch (Exception e2) {
            throw new DaoException("Could not init DAOConfig", e2);
        }
    }

    private static Property[] reflectProperties(Class<? extends AbstractDao<?, ?>> cls) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException {
        Field[] declaredFields = Class.forName(cls.getName() + "$Properties").getDeclaredFields();
        ArrayList arrayList = new ArrayList();
        for (Field field : declaredFields) {
            if ((field.getModifiers() & 9) == 9) {
                Object obj = field.get(null);
                if (obj instanceof Property) {
                    arrayList.add((Property) obj);
                }
            }
        }
        Property[] propertyArr = new Property[arrayList.size()];
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Property property = (Property) it.next();
            int i2 = property.ordinal;
            if (propertyArr[i2] != null) {
                throw new DaoException("Duplicate property ordinals");
            }
            propertyArr[i2] = property;
        }
        return propertyArr;
    }

    public IdentityScope<?, ?> getIdentityScope() {
        return this.identityScope;
    }

    public void initIdentityScope(IdentityScopeType identityScopeType) {
        if (identityScopeType == IdentityScopeType.None) {
            this.identityScope = null;
            return;
        }
        if (identityScopeType != IdentityScopeType.Session) {
            throw new IllegalArgumentException("Unsupported type: " + identityScopeType);
        }
        if (this.keyIsNumeric) {
            this.identityScope = new IdentityScopeLong();
        } else {
            this.identityScope = new IdentityScopeObject();
        }
    }

    public void setIdentityScope(IdentityScope<?, ?> identityScope) {
        this.identityScope = identityScope;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public DaoConfig m778clone() {
        return new DaoConfig(this);
    }

    public DaoConfig(DaoConfig daoConfig) {
        this.db = daoConfig.db;
        this.tablename = daoConfig.tablename;
        this.properties = daoConfig.properties;
        this.allColumns = daoConfig.allColumns;
        this.pkColumns = daoConfig.pkColumns;
        this.nonPkColumns = daoConfig.nonPkColumns;
        this.pkProperty = daoConfig.pkProperty;
        this.statements = daoConfig.statements;
        this.keyIsNumeric = daoConfig.keyIsNumeric;
    }
}
