package c;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: classes.dex */
public class g {
    public static Map<String, Object> a(JSONObject jSONObject) throws JSONException {
        HashMap map = new HashMap();
        if (jSONObject != null) {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                Object obj = jSONObject.get(next);
                if (obj == null) {
                    map.put(next, null);
                } else if (obj instanceof JSONObject) {
                    map.put(next, a((JSONObject) obj));
                } else if (obj instanceof JSONArray) {
                    map.put(next, a((JSONArray) obj));
                } else {
                    map.put(next, obj);
                }
            }
        }
        return map;
    }

    public static JSONObject b(Object obj) throws JSONException, IllegalAccessException, SecurityException {
        JSONObject jSONObject = new JSONObject();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.getName().equalsIgnoreCase("serialVersionUID")) {
                switch (a(field.getType())) {
                    case 0:
                        jSONObject.put(field.getName(), field.get(obj) == null ? "" : field.get(obj));
                        break;
                    case 1:
                        jSONObject.put(field.getName(), ((Integer) (field.get(obj) == null ? 0 : field.get(obj))).intValue());
                        break;
                    case 2:
                        jSONObject.put(field.getName(), ((Long) (field.get(obj) == null ? 0 : field.get(obj))).longValue());
                        break;
                    case 3:
                        jSONObject.put(field.getName(), ((Float) (field.get(obj) == null ? 0 : field.get(obj))).floatValue());
                        break;
                    case 4:
                        jSONObject.put(field.getName(), ((Double) (field.get(obj) == null ? 0 : field.get(obj))).doubleValue());
                        break;
                    case 5:
                        jSONObject.put(field.getName(), ((Boolean) (field.get(obj) == null ? Boolean.FALSE : field.get(obj))).booleanValue());
                        break;
                    case 6:
                    case 7:
                    case 8:
                        jSONObject.put(field.getName(), field.get(obj) == null ? null : field.get(obj));
                        break;
                    case 9:
                        jSONObject.put(field.getName(), new JSONArray((Collection) field.get(obj)));
                        break;
                    case 10:
                        jSONObject.put(field.getName(), new JSONObject((HashMap) field.get(obj)));
                        break;
                }
            }
        }
        return jSONObject;
    }

    public static List<Object> a(JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        if (jSONArray != null) {
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                Object obj = jSONArray.get(i2);
                if (obj != null) {
                    if (obj instanceof JSONObject) {
                        arrayList.add(a((JSONObject) obj));
                    } else if (obj instanceof JSONArray) {
                        arrayList.add(a((JSONArray) obj));
                    } else {
                        arrayList.add(obj);
                    }
                }
            }
        }
        return arrayList;
    }

    public static int a(Class<?> cls) {
        if (cls != null && (String.class.isAssignableFrom(cls) || Character.class.isAssignableFrom(cls) || Character.TYPE.isAssignableFrom(cls) || Character.TYPE.isAssignableFrom(cls))) {
            return 0;
        }
        if (cls != null) {
            if (Byte.TYPE.isAssignableFrom(cls) || Short.TYPE.isAssignableFrom(cls)) {
                return 1;
            }
            Class cls2 = Integer.TYPE;
            if (cls2.isAssignableFrom(cls) || Integer.class.isAssignableFrom(cls) || Number.class.isAssignableFrom(cls) || cls2.isAssignableFrom(cls) || Byte.TYPE.isAssignableFrom(cls) || Short.TYPE.isAssignableFrom(cls)) {
                return 1;
            }
        }
        if (cls != null) {
            Class cls3 = Long.TYPE;
            if (cls3.isAssignableFrom(cls) || cls3.isAssignableFrom(cls)) {
                return 2;
            }
        }
        if (cls != null) {
            Class cls4 = Float.TYPE;
            if (cls4.isAssignableFrom(cls) || cls4.isAssignableFrom(cls)) {
                return 3;
            }
        }
        if (cls != null && (Double.TYPE.isAssignableFrom(cls) || Double.TYPE.isAssignableFrom(cls))) {
            return 4;
        }
        if (cls != null) {
            Class cls5 = Boolean.TYPE;
            if (cls5.isAssignableFrom(cls) || Boolean.class.isAssignableFrom(cls) || cls5.isAssignableFrom(cls)) {
                return 5;
            }
        }
        if (cls != null && cls.isArray()) {
            return 6;
        }
        if (cls != null && Collections.class.isAssignableFrom(cls)) {
            return 7;
        }
        if (cls != null && JSONArray.class.isAssignableFrom(cls)) {
            return 8;
        }
        if (cls == null || !List.class.isAssignableFrom(cls)) {
            return (cls == null || !Map.class.isAssignableFrom(cls)) ? 11 : 10;
        }
        return 9;
    }

    public static Object a(JSONObject jSONObject, Field field) throws JSONException {
        switch (a(field.getType())) {
            case 0:
                return jSONObject.opt(field.getName());
            case 1:
                return Integer.valueOf(jSONObject.optInt(field.getName()));
            case 2:
                return Long.valueOf(jSONObject.optLong(field.getName()));
            case 3:
            case 4:
                return Double.valueOf(jSONObject.optDouble(field.getName()));
            case 5:
                return Boolean.valueOf(jSONObject.optBoolean(field.getName()));
            case 6:
            case 7:
            case 8:
                return jSONObject.optJSONArray(field.getName());
            case 9:
                return a(jSONObject.optJSONArray(field.getName()));
            case 10:
                return a(jSONObject.optJSONObject(field.getName()));
            default:
                return null;
        }
    }

    public static Object a(Object obj, Map<?, ?> map) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return obj;
    }

    public static String a(Object obj) throws IllegalAccessException, JSONException {
        return b(obj).toString();
    }

    public static <T> T a(String str, Class<T> cls) throws IllegalAccessException, JSONException, InstantiationException, SecurityException, IllegalArgumentException, NullPointerException {
        if (str != null && !str.equals("")) {
            T tNewInstance = cls.newInstance();
            Field[] declaredFields = cls.getDeclaredFields();
            JSONObject jSONObject = (JSONObject) new JSONTokener(str).nextValue();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                field.set(tNewInstance, a(jSONObject, field));
            }
            return tNewInstance;
        }
        throw new NullPointerException("JsonString can't be null");
    }
}
