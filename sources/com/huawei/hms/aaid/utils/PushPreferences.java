package com.huawei.hms.aaid.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.huawei.hms.support.log.HMSLog;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class PushPreferences {
    public static final String TAG = "PushPreferences";

    /* renamed from: a, reason: collision with root package name */
    public SharedPreferences f7420a;

    public PushPreferences(Context context, String str) throws NoSuchMethodException, SecurityException {
        if (context == null) {
            throw new NullPointerException("context is null!");
        }
        if (Build.VERSION.SDK_INT >= 24) {
            Context contextCreateDeviceProtectedStorageContext = context.createDeviceProtectedStorageContext();
            SharedPreferences sharedPreferences = contextCreateDeviceProtectedStorageContext.getSharedPreferences("move_to_de_records", 0);
            if (sharedPreferences.getBoolean(str, false)) {
                context = contextCreateDeviceProtectedStorageContext;
            } else if (contextCreateDeviceProtectedStorageContext.moveSharedPreferencesFrom(context, str)) {
                SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                editorEdit.putBoolean(str, true);
                editorEdit.apply();
                context = contextCreateDeviceProtectedStorageContext;
            }
        }
        SharedPreferences sharedPreferencesB = b(context, str);
        this.f7420a = sharedPreferencesB;
        if (sharedPreferencesB == null) {
            HMSLog.w(TAG, "get new sharedPreferences failed,start to get from context. ");
            this.f7420a = context.getSharedPreferences(str, 0);
        }
    }

    public final File a(Context context, String str) {
        File file;
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                file = new File(context.getDataDir() + "/shared_prefs", str + ".xml");
            } else {
                file = new File(context.getFilesDir().getParent() + "/shared_prefs", str + ".xml");
            }
            if (file.exists()) {
                return file;
            }
            return null;
        } catch (Exception e2) {
            HMSLog.e(TAG, "get failed error." + e2.getMessage());
            return null;
        }
    }

    public final SharedPreferences b(Context context, String str) throws NoSuchMethodException, SecurityException {
        File fileA = a(context, str);
        if (fileA == null) {
            return null;
        }
        try {
            Constructor<?> declaredConstructor = Class.forName("android.app.SharedPreferencesImpl").getDeclaredConstructor(File.class, Integer.TYPE);
            declaredConstructor.setAccessible(true);
            return (SharedPreferences) declaredConstructor.newInstance(fileA, 0);
        } catch (Exception e2) {
            HMSLog.e(TAG, "get SharedPreferences error." + e2.getMessage());
            return null;
        }
    }

    public boolean clear() {
        SharedPreferences sharedPreferences = this.f7420a;
        if (sharedPreferences != null) {
            return sharedPreferences.edit().clear().commit();
        }
        return false;
    }

    public boolean containsKey(String str) {
        SharedPreferences sharedPreferences = this.f7420a;
        return sharedPreferences != null && sharedPreferences.contains(str);
    }

    public Map<String, ?> getAll() {
        SharedPreferences sharedPreferences = this.f7420a;
        return sharedPreferences != null ? sharedPreferences.getAll() : new HashMap();
    }

    public boolean getBoolean(String str) {
        SharedPreferences sharedPreferences = this.f7420a;
        return sharedPreferences != null && sharedPreferences.getBoolean(str, false);
    }

    public int getInt(String str) {
        SharedPreferences sharedPreferences = this.f7420a;
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(str, 0);
        }
        return 0;
    }

    public long getLong(String str) {
        SharedPreferences sharedPreferences = this.f7420a;
        if (sharedPreferences != null) {
            return sharedPreferences.getLong(str, 0L);
        }
        return 0L;
    }

    public String getString(String str) {
        SharedPreferences sharedPreferences = this.f7420a;
        return sharedPreferences != null ? sharedPreferences.getString(str, "") : "";
    }

    public ContentValues read() {
        Map<String, ?> all;
        SharedPreferences sharedPreferences = this.f7420a;
        if (sharedPreferences == null || (all = sharedPreferences.getAll()) == null) {
            return null;
        }
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<String, ?> entry : all.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                contentValues.put(key, String.valueOf(value));
            } else if ((value instanceof Integer) || (value instanceof Short) || (value instanceof Byte)) {
                contentValues.put(key, (Integer) value);
            } else if (value instanceof Long) {
                contentValues.put(key, (Long) value);
            } else if (value instanceof Float) {
                contentValues.put(key, (Float) value);
            } else if (value instanceof Double) {
                contentValues.put(key, Float.valueOf((float) ((Double) value).doubleValue()));
            } else if (value instanceof Boolean) {
                contentValues.put(key, (Boolean) value);
            }
        }
        return contentValues;
    }

    public boolean removeKey(String str) {
        SharedPreferences.Editor editorEdit;
        SharedPreferences sharedPreferences = this.f7420a;
        if (sharedPreferences == null || !sharedPreferences.contains(str) || (editorEdit = this.f7420a.edit()) == null) {
            return false;
        }
        return editorEdit.remove(str).commit();
    }

    public boolean save(String str, Object obj) {
        SharedPreferences sharedPreferences = this.f7420a;
        if (sharedPreferences == null) {
            return false;
        }
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        if (obj instanceof String) {
            editorEdit.putString(str, String.valueOf(obj));
        } else if (obj instanceof Integer) {
            editorEdit.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Short) {
            editorEdit.putInt(str, ((Short) obj).shortValue());
        } else if (obj instanceof Byte) {
            editorEdit.putInt(str, ((Byte) obj).byteValue());
        } else if (obj instanceof Long) {
            editorEdit.putLong(str, ((Long) obj).longValue());
        } else if (obj instanceof Float) {
            editorEdit.putFloat(str, ((Float) obj).floatValue());
        } else if (obj instanceof Double) {
            editorEdit.putFloat(str, (float) ((Double) obj).doubleValue());
        } else if (obj instanceof Boolean) {
            editorEdit.putBoolean(str, ((Boolean) obj).booleanValue());
        }
        return editorEdit.commit();
    }

    public void saveBoolean(String str, boolean z2) {
        SharedPreferences.Editor editorEdit;
        SharedPreferences sharedPreferences = this.f7420a;
        if (sharedPreferences == null || (editorEdit = sharedPreferences.edit()) == null) {
            return;
        }
        editorEdit.putBoolean(str, z2).commit();
    }

    public void saveInt(String str, Integer num) {
        SharedPreferences.Editor editorEdit;
        SharedPreferences sharedPreferences = this.f7420a;
        if (sharedPreferences == null || (editorEdit = sharedPreferences.edit()) == null) {
            return;
        }
        editorEdit.putInt(str, num.intValue()).commit();
    }

    public void saveLong(String str, Long l2) {
        SharedPreferences.Editor editorEdit;
        SharedPreferences sharedPreferences = this.f7420a;
        if (sharedPreferences == null || (editorEdit = sharedPreferences.edit()) == null) {
            return;
        }
        editorEdit.putLong(str, l2.longValue()).commit();
    }

    public void saveMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            save(entry.getKey(), entry.getValue());
        }
    }

    public boolean saveString(String str, String str2) {
        SharedPreferences.Editor editorEdit;
        SharedPreferences sharedPreferences = this.f7420a;
        if (sharedPreferences == null || (editorEdit = sharedPreferences.edit()) == null) {
            return false;
        }
        return editorEdit.putString(str, str2).commit();
    }

    public boolean write(ContentValues contentValues) {
        if (this.f7420a == null || contentValues == null) {
            return false;
        }
        boolean z2 = true;
        for (Map.Entry<String, Object> entry : contentValues.valueSet()) {
            if (!save(entry.getKey(), entry.getValue())) {
                z2 = false;
            }
        }
        return z2;
    }

    public boolean removeKey(String[] strArr) {
        if (this.f7420a == null) {
            return false;
        }
        for (String str : strArr) {
            if (this.f7420a.contains(str)) {
                this.f7420a.edit().remove(str);
            }
        }
        this.f7420a.edit().commit();
        return true;
    }
}
