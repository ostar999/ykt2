package de.greenrobot.event;

import android.util.Log;
import cn.hutool.core.text.StrPool;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.text.Typography;

/* loaded from: classes8.dex */
class SubscriberMethodFinder {
    private static final int BRIDGE = 64;
    private static final int MODIFIERS_IGNORE = 5192;
    private static final String ON_EVENT_METHOD_NAME = "onEvent";
    private static final int SYNTHETIC = 4096;
    private static final Map<Class<?>, List<SubscriberMethod>> methodCache = new HashMap();
    private final Map<Class<?>, Class<?>> skipMethodVerificationForClasses = new ConcurrentHashMap();

    public SubscriberMethodFinder(List<Class<?>> list) {
        if (list != null) {
            for (Class<?> cls : list) {
                this.skipMethodVerificationForClasses.put(cls, cls);
            }
        }
    }

    public static void clearCaches() {
        Map<Class<?>, List<SubscriberMethod>> map = methodCache;
        synchronized (map) {
            map.clear();
        }
    }

    private void filterSubscriberMethods(List<SubscriberMethod> list, HashMap<String, Class> map, StringBuilder sb, Method[] methodArr) {
        ThreadMode threadMode;
        for (Method method : methodArr) {
            String name = method.getName();
            if (name.startsWith(ON_EVENT_METHOD_NAME)) {
                int modifiers = method.getModifiers();
                Class<?> declaringClass = method.getDeclaringClass();
                if ((modifiers & 1) != 0 && (modifiers & 5192) == 0) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length == 1 && (threadMode = getThreadMode(declaringClass, method, name)) != null) {
                        Class<?> cls = parameterTypes[0];
                        sb.setLength(0);
                        sb.append(name);
                        sb.append(Typography.greater);
                        sb.append(cls.getName());
                        String string = sb.toString();
                        Class clsPut = map.put(string, declaringClass);
                        if (clsPut == null || clsPut.isAssignableFrom(declaringClass)) {
                            list.add(new SubscriberMethod(method, threadMode, cls));
                        } else {
                            map.put(string, clsPut);
                        }
                    }
                } else if (!this.skipMethodVerificationForClasses.containsKey(declaringClass)) {
                    Log.d(EventBus.TAG, "Skipping method (not public, static or abstract): " + declaringClass + StrPool.DOT + name);
                }
            }
        }
    }

    private ThreadMode getThreadMode(Class<?> cls, Method method, String str) {
        String strSubstring = str.substring(7);
        if (strSubstring.length() == 0) {
            return ThreadMode.PostThread;
        }
        if (strSubstring.equals("MainThread")) {
            return ThreadMode.MainThread;
        }
        if (strSubstring.equals("BackgroundThread")) {
            return ThreadMode.BackgroundThread;
        }
        if (strSubstring.equals("Async")) {
            return ThreadMode.Async;
        }
        if (this.skipMethodVerificationForClasses.containsKey(cls)) {
            return null;
        }
        throw new EventBusException("Illegal onEvent method, check for typos: " + method);
    }

    public List<SubscriberMethod> findSubscriberMethods(Class<?> cls) throws SecurityException {
        List<SubscriberMethod> list;
        Map<Class<?>, List<SubscriberMethod>> map = methodCache;
        synchronized (map) {
            list = map.get(cls);
        }
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        HashMap<String, Class> map2 = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (Class<?> superclass = cls; superclass != null; superclass = superclass.getSuperclass()) {
            String name = superclass.getName();
            if (name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.")) {
                break;
            }
            try {
                filterSubscriberMethods(arrayList, map2, sb, superclass.getDeclaredMethods());
            } catch (Throwable unused) {
                Method[] methods = cls.getMethods();
                arrayList.clear();
                map2.clear();
                filterSubscriberMethods(arrayList, map2, sb, methods);
            }
        }
        if (!arrayList.isEmpty()) {
            Map<Class<?>, List<SubscriberMethod>> map3 = methodCache;
            synchronized (map3) {
                map3.put(cls, arrayList);
            }
            return arrayList;
        }
        throw new EventBusException("Subscriber " + cls + " has no public methods called " + ON_EVENT_METHOD_NAME);
    }
}
