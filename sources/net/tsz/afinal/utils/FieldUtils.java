package net.tsz.afinal.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.ManyToOne;
import net.tsz.afinal.annotation.sqlite.OneToMany;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Transient;

/* loaded from: classes9.dex */
public class FieldUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Method getBooleanFieldGetMethod(Class<?> cls, String str) {
        String str2 = "is" + str.substring(0, 1).toUpperCase() + str.substring(1);
        if (!isISStart(str)) {
            str = str2;
        }
        try {
            return cls.getDeclaredMethod(str, new Class[0]);
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Method getBooleanFieldSetMethod(Class<?> cls, Field field) {
        String name = field.getName();
        String str = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        if (isISStart(field.getName())) {
            str = "set" + name.substring(2, 3).toUpperCase() + name.substring(3);
        }
        try {
            return cls.getDeclaredMethod(str, field.getType());
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getColumnByField(Field field) {
        Property property = (Property) field.getAnnotation(Property.class);
        if (property != null && property.column().trim().length() != 0) {
            return property.column();
        }
        ManyToOne manyToOne = (ManyToOne) field.getAnnotation(ManyToOne.class);
        if (manyToOne != null && manyToOne.column().trim().length() != 0) {
            return manyToOne.column();
        }
        OneToMany oneToMany = (OneToMany) field.getAnnotation(OneToMany.class);
        if (oneToMany != null && oneToMany.manyColumn() != null && oneToMany.manyColumn().trim().length() != 0) {
            return oneToMany.manyColumn();
        }
        Id id = (Id) field.getAnnotation(Id.class);
        return (id == null || id.column().trim().length() == 0) ? field.getName() : id.column();
    }

    public static Field getFieldByColumnName(Class<?> cls, String str) {
        Field[] declaredFields;
        ManyToOne manyToOne;
        if (str == null || (declaredFields = cls.getDeclaredFields()) == null || declaredFields.length <= 0) {
            return null;
        }
        Field primaryKeyField = str.equals(ClassUtils.getPrimaryKeyColumn(cls)) ? ClassUtils.getPrimaryKeyField(cls) : null;
        if (primaryKeyField == null) {
            for (Field field : declaredFields) {
                Property property = (Property) field.getAnnotation(Property.class);
                if ((property != null && str.equals(property.column())) || ((manyToOne = (ManyToOne) field.getAnnotation(ManyToOne.class)) != null && manyToOne.column().trim().length() != 0)) {
                    primaryKeyField = field;
                    break;
                }
            }
        }
        return primaryKeyField == null ? getFieldByName(cls, str) : primaryKeyField;
    }

    public static Field getFieldByName(Class<?> cls, String str) {
        if (str != null) {
            try {
                return cls.getDeclaredField(str);
            } catch (NoSuchFieldException e2) {
                e2.printStackTrace();
            } catch (SecurityException e3) {
                e3.printStackTrace();
            }
        }
        return null;
    }

    public static Method getFieldGetMethod(Class<?> cls, Field field) {
        String name = field.getName();
        Method booleanFieldGetMethod = field.getType() == Boolean.TYPE ? getBooleanFieldGetMethod(cls, name) : null;
        return booleanFieldGetMethod == null ? getFieldGetMethod(cls, name) : booleanFieldGetMethod;
    }

    public static Method getFieldSetMethod(Class<?> cls, Field field) {
        String name = field.getName();
        try {
            return cls.getDeclaredMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), field.getType());
        } catch (NoSuchMethodException unused) {
            if (field.getType() == Boolean.TYPE) {
                return getBooleanFieldSetMethod(cls, field);
            }
            return null;
        }
    }

    public static Object getFieldValue(Object obj, Field field) {
        return invoke(obj, getFieldGetMethod(obj.getClass(), field));
    }

    public static String getPropertyDefaultValue(Field field) {
        Property property = (Property) field.getAnnotation(Property.class);
        if (property == null || property.defaultValue().trim().length() == 0) {
            return null;
        }
        return property.defaultValue();
    }

    private static Object invoke(Object obj, Method method) {
        if (obj != null && method != null) {
            try {
                return method.invoke(obj, new Object[0]);
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
            }
        }
        return null;
    }

    public static boolean isBaseDateType(Field field) {
        Class<?> type = field.getType();
        return type.equals(String.class) || type.equals(Integer.class) || type.equals(Byte.class) || type.equals(Long.class) || type.equals(Double.class) || type.equals(Float.class) || type.equals(Character.class) || type.equals(Short.class) || type.equals(Boolean.class) || type.equals(Date.class) || type.equals(Date.class) || type.equals(java.sql.Date.class) || type.isPrimitive();
    }

    private static boolean isISStart(String str) {
        return (str == null || str.trim().length() == 0 || !str.startsWith("is") || Character.isLowerCase(str.charAt(2))) ? false : true;
    }

    public static boolean isManyToOne(Field field) {
        return field.getAnnotation(ManyToOne.class) != null;
    }

    public static boolean isManyToOneOrOneToMany(Field field) {
        return isManyToOne(field) || isOneToMany(field);
    }

    public static boolean isOneToMany(Field field) {
        return field.getAnnotation(OneToMany.class) != null;
    }

    public static boolean isTransient(Field field) {
        return field.getAnnotation(Transient.class) != null;
    }

    public static void setFieldValue(Object obj, Field field, Object obj2) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            Method fieldSetMethod = getFieldSetMethod(obj.getClass(), field);
            if (fieldSetMethod != null) {
                fieldSetMethod.setAccessible(true);
                Class<?> type = field.getType();
                if (type == String.class) {
                    fieldSetMethod.invoke(obj, obj2.toString());
                    return;
                }
                Date dateStringToDateTime = null;
                if (type != Integer.TYPE && type != Integer.class) {
                    if (type != Float.TYPE && type != Float.class) {
                        if (type != Long.TYPE && type != Long.class) {
                            if (type != Date.class) {
                                fieldSetMethod.invoke(obj, obj2);
                                return;
                            }
                            Object[] objArr = new Object[1];
                            if (obj2 != null) {
                                dateStringToDateTime = stringToDateTime(obj2.toString());
                            }
                            objArr[0] = dateStringToDateTime;
                            fieldSetMethod.invoke(obj, objArr);
                            return;
                        }
                        Object[] objArr2 = new Object[1];
                        if (obj2 == null) {
                            throw null;
                        }
                        objArr2[0] = Long.valueOf(Long.parseLong(obj2.toString()));
                        fieldSetMethod.invoke(obj, objArr2);
                        return;
                    }
                    Object[] objArr3 = new Object[1];
                    if (obj2 == null) {
                        throw null;
                    }
                    objArr3[0] = Float.valueOf(Float.parseFloat(obj2.toString()));
                    fieldSetMethod.invoke(obj, objArr3);
                    return;
                }
                Object[] objArr4 = new Object[1];
                if (obj2 == null) {
                    throw null;
                }
                objArr4[0] = Integer.valueOf(Integer.parseInt(obj2.toString()));
                fieldSetMethod.invoke(obj, objArr4);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

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

    public static Object getFieldValue(Object obj, String str) {
        return invoke(obj, getFieldGetMethod(obj.getClass(), str));
    }

    public static Method getFieldGetMethod(Class<?> cls, String str) {
        try {
            return cls.getDeclaredMethod("get" + str.substring(0, 1).toUpperCase() + str.substring(1), new Class[0]);
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Method getFieldSetMethod(Class<?> cls, String str) {
        try {
            return getFieldSetMethod(cls, cls.getDeclaredField(str));
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
            return null;
        } catch (SecurityException e3) {
            e3.printStackTrace();
            return null;
        }
    }
}
