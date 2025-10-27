package androidx.startup;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.tracing.Trace;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class AppInitializer {
    private static final String SECTION_NAME = "Startup";
    private static volatile AppInitializer sInstance;
    private static final Object sLock = new Object();

    @NonNull
    final Context mContext;

    @NonNull
    final Set<Class<? extends Initializer<?>>> mDiscovered = new HashSet();

    @NonNull
    final Map<Class<?>, Object> mInitialized = new HashMap();

    public AppInitializer(@NonNull Context context) {
        this.mContext = context.getApplicationContext();
    }

    @NonNull
    public static AppInitializer getInstance(@NonNull Context context) {
        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new AppInitializer(context);
                }
            }
        }
        return sInstance;
    }

    public static void setDelegate(@NonNull AppInitializer appInitializer) {
        synchronized (sLock) {
            sInstance = appInitializer;
        }
    }

    public void discoverAndInitialize() {
        try {
            try {
                Trace.beginSection(SECTION_NAME);
                discoverAndInitialize(this.mContext.getPackageManager().getProviderInfo(new ComponentName(this.mContext.getPackageName(), InitializationProvider.class.getName()), 128).metaData);
            } catch (PackageManager.NameNotFoundException e2) {
                throw new StartupException(e2);
            }
        } finally {
            Trace.endSection();
        }
    }

    @NonNull
    public <T> T doInitialize(@NonNull Class<? extends Initializer<?>> cls) {
        T t2;
        synchronized (sLock) {
            t2 = (T) this.mInitialized.get(cls);
            if (t2 == null) {
                t2 = (T) doInitialize(cls, new HashSet());
            }
        }
        return t2;
    }

    @NonNull
    public <T> T initializeComponent(@NonNull Class<? extends Initializer<T>> cls) {
        return (T) doInitialize(cls);
    }

    public boolean isEagerlyInitialized(@NonNull Class<? extends Initializer<?>> cls) {
        return this.mDiscovered.contains(cls);
    }

    @NonNull
    private <T> T doInitialize(@NonNull Class<? extends Initializer<?>> cls, @NonNull Set<Class<?>> set) {
        T t2;
        if (Trace.isEnabled()) {
            try {
                Trace.beginSection(cls.getSimpleName());
            } finally {
                Trace.endSection();
            }
        }
        if (!set.contains(cls)) {
            if (!this.mInitialized.containsKey(cls)) {
                set.add(cls);
                try {
                    Initializer<?> initializerNewInstance = cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    List<Class<? extends Initializer<?>>> listDependencies = initializerNewInstance.dependencies();
                    if (!listDependencies.isEmpty()) {
                        for (Class<? extends Initializer<?>> cls2 : listDependencies) {
                            if (!this.mInitialized.containsKey(cls2)) {
                                doInitialize(cls2, set);
                            }
                        }
                    }
                    t2 = (T) initializerNewInstance.create(this.mContext);
                    set.remove(cls);
                    this.mInitialized.put(cls, t2);
                } catch (Throwable th) {
                    throw new StartupException(th);
                }
            } else {
                t2 = (T) this.mInitialized.get(cls);
            }
            return t2;
        }
        throw new IllegalStateException(String.format("Cannot initialize %s. Cycle detected.", cls.getName()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void discoverAndInitialize(@Nullable Bundle bundle) throws ClassNotFoundException {
        String string = this.mContext.getString(R.string.androidx_startup);
        if (bundle != null) {
            try {
                HashSet hashSet = new HashSet();
                for (String str : bundle.keySet()) {
                    if (string.equals(bundle.getString(str, null))) {
                        Class<?> cls = Class.forName(str);
                        if (Initializer.class.isAssignableFrom(cls)) {
                            this.mDiscovered.add(cls);
                        }
                    }
                }
                Iterator<Class<? extends Initializer<?>>> it = this.mDiscovered.iterator();
                while (it.hasNext()) {
                    doInitialize(it.next(), hashSet);
                }
            } catch (ClassNotFoundException e2) {
                throw new StartupException(e2);
            }
        }
    }
}
