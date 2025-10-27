package com.hjq.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.annotation.HttpRename;
import com.hjq.http.body.UpdateBody;
import com.hjq.http.model.FileContentResolver;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.Okio;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class EasyUtils {
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static HashMap<String, Object> beanToHashMap(Object obj) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        String name;
        if (obj == null) {
            return null;
        }
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        HashMap<String, Object> map = new HashMap<>(declaredFields.length);
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                Object obj2 = field.get(obj);
                if (!isEmpty(obj2) && !field.isAnnotationPresent(HttpIgnore.class)) {
                    if (field.isAnnotationPresent(HttpRename.class)) {
                        name = ((HttpRename) field.getAnnotation(HttpRename.class)).value();
                    } else {
                        name = field.getName();
                        if (!name.matches("this\\$\\d+") && !"Companion".equals(name)) {
                        }
                    }
                    if (obj2 instanceof List) {
                        map.put(name, listToJsonArray((List) obj2));
                    } else if (obj2 instanceof Map) {
                        map.put(name, mapToJsonObject((Map) obj2));
                    } else if (isBeanType(obj2)) {
                        map.put(name, beanToHashMap(obj2));
                    } else {
                        map.put(name, obj2);
                    }
                }
            } catch (IllegalAccessException e2) {
                EasyLog.print(e2);
            }
        }
        return map;
    }

    public static void closeStream(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception e2) {
            EasyLog.print(e2);
        }
    }

    public static MultipartBody.Part createPart(String str, File file) {
        String strEncodeString = encodeString(file.getName());
        if (!(file instanceof FileContentResolver)) {
            try {
                return MultipartBody.Part.createFormData(str, strEncodeString, new UpdateBody(file));
            } catch (FileNotFoundException unused) {
                EasyLog.print("文件不存在，将被忽略上传：" + str + " = " + file.getPath());
                return null;
            }
        }
        try {
            FileContentResolver fileContentResolver = (FileContentResolver) file;
            InputStream inputStreamOpenInputStream = fileContentResolver.openInputStream();
            if (inputStreamOpenInputStream == null) {
                return null;
            }
            String fileName = fileContentResolver.getFileName();
            if (TextUtils.isEmpty(fileName)) {
                fileName = fileContentResolver.getName();
            }
            return MultipartBody.Part.createFormData(str, strEncodeString, new UpdateBody(Okio.source(inputStreamOpenInputStream), fileContentResolver.getContentType(), fileName, inputStreamOpenInputStream.available()));
        } catch (IOException e2) {
            EasyLog.print(e2);
            EasyLog.print("文件流读取失败，将被忽略上传：" + str + " = " + file.getPath());
            return null;
        }
    }

    public static String encodeString(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return str;
        }
    }

    public static int getProgressProgress(long j2, long j3) {
        return (int) ((j3 / j2) * 100.0d);
    }

    public static Type getReflectType(Object obj) {
        if (obj == null) {
            return Void.class;
        }
        Type[] genericInterfaces = obj.getClass().getGenericInterfaces();
        return genericInterfaces.length > 0 ? ((ParameterizedType) genericInterfaces[0]).getActualTypeArguments()[0] : ((ParameterizedType) obj.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public static boolean isBeanType(Object obj) {
        return (obj == null || (obj instanceof Number) || (obj instanceof CharSequence) || (obj instanceof Boolean) || (obj instanceof File) || (obj instanceof InputStream) || (obj instanceof RequestBody) || (obj instanceof Character) || (obj instanceof JSONObject) || (obj instanceof JSONArray)) ? false : true;
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if ((obj instanceof List) && ((List) obj).isEmpty()) {
            return true;
        }
        return (obj instanceof Map) && ((Map) obj).isEmpty();
    }

    public static boolean isFileList(List<?> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        Iterator<?> it = list.iterator();
        while (it.hasNext()) {
            if (!(it.next() instanceof File)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMultipart(List<Field> list) throws SecurityException {
        for (Field field : list) {
            field.setAccessible(true);
            Class<?> type = field.getType();
            Class<?>[] interfaces = type.getInterfaces();
            int i2 = 0;
            while (i2 <= interfaces.length) {
                if (List.class.equals(i2 == interfaces.length ? type : interfaces[i2])) {
                    Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                    if (actualTypeArguments.length == 1 && File.class.equals(actualTypeArguments[0])) {
                        return true;
                    }
                }
                i2++;
            }
            while (!File.class.equals(type) && !InputStream.class.equals(type) && !RequestBody.class.equals(type) && !MultipartBody.Part.class.equals(type)) {
                type = type.getSuperclass();
                if (type == null || Object.class.equals(type)) {
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public static JSONArray listToJsonArray(List<?> list) {
        JSONArray jSONArray = new JSONArray();
        if (list != null && !list.isEmpty()) {
            for (Object obj : list) {
                if (!isEmpty(obj)) {
                    if (obj instanceof List) {
                        jSONArray.put(listToJsonArray((List) obj));
                    } else if (obj instanceof Map) {
                        jSONArray.put(mapToJsonObject((Map) obj));
                    } else if (isBeanType(obj)) {
                        jSONArray.put(mapToJsonObject(beanToHashMap(obj)));
                    } else {
                        jSONArray.put(obj);
                    }
                }
            }
        }
        return jSONArray;
    }

    public static JSONObject mapToJsonObject(Map<?, ?> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (map != null && !map.isEmpty()) {
            for (Object obj : map.keySet()) {
                Object obj2 = map.get(obj);
                if (!isEmpty(obj2)) {
                    try {
                        if (obj2 instanceof List) {
                            jSONObject.put(String.valueOf(obj), listToJsonArray((List) obj2));
                        } else if (obj2 instanceof Map) {
                            jSONObject.put(String.valueOf(obj), mapToJsonObject((Map) obj2));
                        } else if (isBeanType(obj2)) {
                            jSONObject.put(String.valueOf(obj), mapToJsonObject(beanToHashMap(obj2)));
                        } else {
                            jSONObject.put(String.valueOf(obj), obj2);
                        }
                    } catch (JSONException e2) {
                        EasyLog.print(e2);
                    }
                }
            }
        }
        return jSONObject;
    }

    public static void post(Runnable runnable) {
        HANDLER.post(runnable);
    }

    public static void postDelayed(Runnable runnable, long j2) {
        HANDLER.postDelayed(runnable, j2);
    }

    public static MultipartBody.Part createPart(String str, InputStream inputStream) {
        try {
            return MultipartBody.Part.createFormData(str, null, new UpdateBody(inputStream, str));
        } catch (IOException e2) {
            EasyLog.print(e2);
            return null;
        }
    }
}
