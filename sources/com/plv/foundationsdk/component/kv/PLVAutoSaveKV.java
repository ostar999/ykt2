package com.plv.foundationsdk.component.kv;

import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.thirdpart.blankj.utilcode.util.SPUtils;
import io.reactivex.schedulers.Schedulers;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* loaded from: classes4.dex */
public abstract class PLVAutoSaveKV<T> {
    private static final String AUTO_SAVE_DATA_SP_NAME = "plv_auto_save_kv";
    private T recoverValue;
    private boolean recovered;
    private final String spName;
    private StorageHandler<T> storageHandler;
    private final String storageKey;
    private final Type type;

    public static class DefaultStorageHandler<T> implements StorageHandler<T> {
        private DefaultStorageHandler() {
        }

        @Override // com.plv.foundationsdk.component.kv.PLVAutoSaveKV.StorageHandler
        public T load(String str, String str2, Type type) {
            try {
                return (T) new Gson().fromJson(SPUtils.getInstance(str).getString(str2), type);
            } catch (Exception e2) {
                PLVCommonLog.exception(e2);
                return null;
            }
        }

        @Override // com.plv.foundationsdk.component.kv.PLVAutoSaveKV.StorageHandler
        public void save(final String str, final String str2, final T t2) {
            Schedulers.io().scheduleDirect(new Runnable() { // from class: com.plv.foundationsdk.component.kv.PLVAutoSaveKV.DefaultStorageHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    SPUtils.getInstance(str).put(str2, new Gson().toJson(t2));
                }
            });
        }
    }

    public interface StorageHandler<T> {
        T load(String str, String str2, Type type);

        void save(String str, String str2, T t2);
    }

    public PLVAutoSaveKV(String str) {
        this(AUTO_SAVE_DATA_SP_NAME, str);
    }

    private static Type getSuperclassTypeParameter(Class<?> cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            return ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        }
        return null;
    }

    private void saveValue(T t2) {
        this.storageHandler.save(this.spName, this.storageKey, t2);
    }

    @Nullable
    public T get() {
        if (!this.recovered) {
            this.recovered = true;
            this.recoverValue = this.storageHandler.load(this.spName, this.storageKey, this.type);
        }
        return this.recoverValue;
    }

    public void set(@Nullable T t2) {
        this.recovered = true;
        this.recoverValue = t2;
        saveValue(t2);
    }

    public void setStorageHandler(StorageHandler<T> storageHandler) {
        this.storageHandler = storageHandler;
    }

    public PLVAutoSaveKV(String str, String str2) {
        this.storageHandler = new DefaultStorageHandler();
        this.recovered = false;
        this.spName = str;
        this.storageKey = str2;
        this.type = getSuperclassTypeParameter(getClass());
    }

    public PLVAutoSaveKV(String str, String str2, Type type) {
        this.storageHandler = new DefaultStorageHandler();
        this.recovered = false;
        this.spName = str;
        this.storageKey = str2;
        this.type = type;
    }
}
