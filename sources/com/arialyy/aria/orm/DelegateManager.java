package com.arialyy.aria.orm;

import android.util.SparseArray;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
class DelegateManager {
    private static volatile DelegateManager INSTANCE;
    private final String TAG = "ModuleFactory";
    private SparseArray<AbsDelegate> mDelegates = new SparseArray<>();

    private DelegateManager() {
    }

    public static DelegateManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DelegateManager.class) {
                INSTANCE = new DelegateManager();
            }
        }
        return INSTANCE;
    }

    public <M extends AbsDelegate> M getDelegate(Class<M> cls) throws IllegalAccessException, NoSuchMethodException, InstantiationException, SecurityException, IllegalArgumentException, InvocationTargetException {
        M m2 = (M) this.mDelegates.get(cls.hashCode());
        if (m2 == null) {
            try {
                Constructor<M> declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
                declaredConstructor.setAccessible(true);
                M mNewInstance = declaredConstructor.newInstance(new Object[0]);
                try {
                    this.mDelegates.put(cls.hashCode(), mNewInstance);
                    return mNewInstance;
                } catch (IllegalAccessException e2) {
                    e = e2;
                    m2 = mNewInstance;
                    e.printStackTrace();
                    return m2;
                } catch (InstantiationException e3) {
                    e = e3;
                    m2 = mNewInstance;
                    e.printStackTrace();
                    return m2;
                } catch (NoSuchMethodException e4) {
                    e = e4;
                    m2 = mNewInstance;
                    e.printStackTrace();
                    return m2;
                } catch (InvocationTargetException e5) {
                    e = e5;
                    m2 = mNewInstance;
                    e.printStackTrace();
                    return m2;
                }
            } catch (IllegalAccessException e6) {
                e = e6;
            } catch (InstantiationException e7) {
                e = e7;
            } catch (NoSuchMethodException e8) {
                e = e8;
            } catch (InvocationTargetException e9) {
                e = e9;
            }
        }
        return m2;
    }
}
