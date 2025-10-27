package com.alipay.a.a;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public final class g implements i, j {
    @Override // com.alipay.a.a.j
    public final Object a(Object obj) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        TreeMap treeMap = new TreeMap();
        Class<?> superclass = obj.getClass();
        while (true) {
            Field[] declaredFields = superclass.getDeclaredFields();
            if (superclass.equals(Object.class)) {
                return treeMap;
            }
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    Object objB = null;
                    if (field != null && !"this$0".equals(field.getName())) {
                        boolean zIsAccessible = field.isAccessible();
                        field.setAccessible(true);
                        Object obj2 = field.get(obj);
                        if (obj2 != null) {
                            field.setAccessible(zIsAccessible);
                            objB = f.b(obj2);
                        }
                    }
                    if (objB != null) {
                        treeMap.put(field.getName(), objB);
                    }
                }
            }
            superclass = superclass.getSuperclass();
        }
    }

    @Override // com.alipay.a.a.i
    public final Object a(Object obj, Type type) throws IllegalAccessException, InstantiationException, SecurityException, IllegalArgumentException {
        if (!obj.getClass().equals(org.json.alipay.b.class)) {
            return null;
        }
        org.json.alipay.b bVar = (org.json.alipay.b) obj;
        Class superclass = (Class) type;
        Object objNewInstance = superclass.newInstance();
        while (!superclass.equals(Object.class)) {
            Field[] declaredFields = superclass.getDeclaredFields();
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    String name = field.getName();
                    Type genericType = field.getGenericType();
                    if (bVar.b(name)) {
                        field.setAccessible(true);
                        field.set(objNewInstance, e.a(bVar.a(name), genericType));
                    }
                }
            }
            superclass = superclass.getSuperclass();
        }
        return objNewInstance;
    }

    @Override // com.alipay.a.a.i, com.alipay.a.a.j
    public final boolean a(Class<?> cls) {
        return true;
    }
}
