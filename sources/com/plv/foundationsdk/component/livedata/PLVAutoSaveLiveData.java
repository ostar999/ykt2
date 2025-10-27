package com.plv.foundationsdk.component.livedata;

import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import com.plv.foundationsdk.component.kv.PLVAutoSaveKV;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* loaded from: classes4.dex */
public abstract class PLVAutoSaveLiveData<T> extends MutableLiveData<T> {
    private static final String AUTO_SAVE_DATA_SP_NAME = "plv_auto_save_live_data";
    private final PLVAutoSaveKV<T> autoSaveKV;
    private final Type type;

    public PLVAutoSaveLiveData(String str) {
        Type superclassTypeParameter = getSuperclassTypeParameter(getClass());
        this.type = superclassTypeParameter;
        this.autoSaveKV = new PLVAutoSaveKV<T>(AUTO_SAVE_DATA_SP_NAME, str, superclassTypeParameter) { // from class: com.plv.foundationsdk.component.livedata.PLVAutoSaveLiveData.1
        };
    }

    private static Type getSuperclassTypeParameter(Class<?> cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            return ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        }
        return null;
    }

    public final PLVAutoSaveKV<T> getAutoSaveKV() {
        return this.autoSaveKV;
    }

    @Override // androidx.lifecycle.LiveData
    @Nullable
    public T getValue() {
        T t2 = (T) super.getValue();
        return t2 != null ? t2 : this.autoSaveKV.get();
    }

    @Override // androidx.lifecycle.MutableLiveData, androidx.lifecycle.LiveData
    public void setValue(T t2) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            postValue(t2);
        } else {
            this.autoSaveKV.set(t2);
            super.setValue(t2);
        }
    }
}
