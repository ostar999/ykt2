package com.huawei.agconnect.core.a;

import android.content.Context;
import android.util.Log;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.core.Service;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class d {

    /* renamed from: a, reason: collision with root package name */
    private static Map<Class<?>, Service> f7347a = new HashMap();

    /* renamed from: b, reason: collision with root package name */
    private static Map<Class<?>, Object> f7348b = new HashMap();

    /* renamed from: c, reason: collision with root package name */
    private Map<Class<?>, Service> f7349c = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    private Map<Class<?>, Object> f7350d = new HashMap();

    public d(List<Service> list, Context context) {
        a(list, context);
    }

    private Object a(AGConnectInstance aGConnectInstance, Service service) throws SecurityException {
        StringBuilder sb;
        if (service.getInstance() != null) {
            return service.getInstance();
        }
        Class<?> type = service.getType();
        if (type == null) {
            return null;
        }
        try {
            Constructor constructorA = a(type, Context.class, AGConnectInstance.class);
            if (constructorA != null) {
                return constructorA.newInstance(aGConnectInstance.getContext(), aGConnectInstance);
            }
            Constructor constructorA2 = a(type, Context.class);
            return constructorA2 != null ? constructorA2.newInstance(aGConnectInstance.getContext()) : type.newInstance();
        } catch (IllegalAccessException e2) {
            e = e2;
            sb = new StringBuilder();
            sb.append("Instantiate service exception ");
            sb.append(e.getLocalizedMessage());
            Log.e("ServiceRepository", sb.toString());
            return null;
        } catch (InstantiationException e3) {
            e = e3;
            sb = new StringBuilder();
            sb.append("Instantiate service exception ");
            sb.append(e.getLocalizedMessage());
            Log.e("ServiceRepository", sb.toString());
            return null;
        } catch (InvocationTargetException e4) {
            e = e4;
            sb = new StringBuilder();
            sb.append("Instantiate service exception ");
            sb.append(e.getLocalizedMessage());
            Log.e("ServiceRepository", sb.toString());
            return null;
        }
    }

    private static Constructor a(Class cls, Class... clsArr) throws SecurityException {
        boolean z2 = false;
        for (Constructor<?> constructor : cls.getDeclaredConstructors()) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == clsArr.length) {
                for (int i2 = 0; i2 < clsArr.length; i2++) {
                    z2 = parameterTypes[i2] == clsArr[i2];
                }
                if (z2) {
                    return constructor;
                }
            }
        }
        return null;
    }

    private void a(String str, Exception exc) {
        Log.e("ServiceRepository", "Instantiate shared service " + str + exc.getLocalizedMessage());
        StringBuilder sb = new StringBuilder();
        sb.append("cause message:");
        sb.append(exc.getCause() != null ? exc.getCause().getMessage() : "");
        Log.e("ServiceRepository", sb.toString());
    }

    public <T> T a(AGConnectInstance aGConnectInstance, Class<?> cls) {
        T t2;
        Service service = this.f7349c.get(cls);
        if (service == null && (service = f7347a.get(cls)) != null) {
            return (T) f7348b.get(cls);
        }
        if (service == null) {
            return null;
        }
        if (service.isSingleton() && (t2 = (T) this.f7350d.get(cls)) != null) {
            return t2;
        }
        T t3 = (T) a(aGConnectInstance, service);
        if (t3 != null && service.isSingleton()) {
            this.f7350d.put(cls, t3);
        }
        return t3;
    }

    public void a(List<Service> list, Context context) {
        Map<Class<?>, Service> map;
        String str;
        if (list == null) {
            return;
        }
        for (Service service : list) {
            if (service.isSharedInstance()) {
                if (!f7347a.containsKey(service.getInterface())) {
                    map = f7347a;
                }
                if (!service.isAutoCreated() && service.getType() != null && !f7348b.containsKey(service.getInterface())) {
                    try {
                        Constructor constructorA = a(service.getType(), Context.class);
                        f7348b.put(service.getInterface(), constructorA != null ? constructorA.newInstance(context) : service.getType().newInstance());
                    } catch (IllegalAccessException e2) {
                        e = e2;
                        str = "AccessException";
                        a(str, e);
                    } catch (InstantiationException e3) {
                        e = e3;
                        str = "InstantiationException";
                        a(str, e);
                    } catch (InvocationTargetException e4) {
                        e = e4;
                        str = "TargetException";
                        a(str, e);
                    }
                }
            } else {
                map = this.f7349c;
            }
            map.put(service.getInterface(), service);
            if (!service.isAutoCreated()) {
            }
        }
    }
}
