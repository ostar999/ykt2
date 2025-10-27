package net.tsz.afinal.db.table;

import android.annotation.SuppressLint;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes9.dex */
public class Property {

    @SuppressLint({"SimpleDateFormat"})
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String column;
    private Class<?> dataType;
    private String defaultValue;
    private Field field;
    private String fieldName;
    private Method get;
    private Method set;

    private static Date stringToDateTime(String str) {
        if (str == null) {
            return null;
        }
        try {
            return sdf.parse(str);
        } catch (ParseException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public String getColumn() {
        return this.column;
    }

    public Class<?> getDataType() {
        return this.dataType;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public Field getField() {
        return this.field;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public Method getGet() {
        return this.get;
    }

    public Method getSet() {
        return this.set;
    }

    public <T> T getValue(Object obj) {
        Method method;
        if (obj == null || (method = this.get) == null) {
            return null;
        }
        try {
            return (T) method.invoke(obj, new Object[0]);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public void setColumn(String str) {
        this.column = str;
    }

    public void setDataType(Class<?> cls) {
        this.dataType = cls;
    }

    public void setDefaultValue(String str) {
        this.defaultValue = str;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setFieldName(String str) {
        this.fieldName = str;
    }

    public void setGet(Method method) {
        this.get = method;
    }

    public void setSet(Method method) {
        this.set = method;
    }

    public void setValue(Object obj, Object obj2) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Method method = this.set;
        if (method == null || obj2 == null) {
            try {
                this.field.setAccessible(true);
                this.field.set(obj, obj2);
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        try {
            Class<?> cls = this.dataType;
            if (cls == String.class) {
                method.invoke(obj, obj2.toString());
            } else if (cls == Integer.TYPE || cls == Integer.class) {
                method.invoke(obj, Integer.valueOf(Integer.parseInt(obj2.toString())));
            } else if (cls == Float.TYPE || cls == Float.class) {
                method.invoke(obj, Float.valueOf(Float.parseFloat(obj2.toString())));
            } else if (cls == Double.TYPE || cls == Double.class) {
                method.invoke(obj, Double.valueOf(Double.parseDouble(obj2.toString())));
            } else if (cls == Long.TYPE || cls == Long.class) {
                method.invoke(obj, Long.valueOf(Long.parseLong(obj2.toString())));
            } else if (cls == Date.class || cls == java.sql.Date.class) {
                method.invoke(obj, stringToDateTime(obj2.toString()));
            } else if (cls == Boolean.TYPE || cls == Boolean.class) {
                method.invoke(obj, Boolean.valueOf("1".equals(obj2.toString())));
            } else {
                method.invoke(obj, obj2);
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }
}
