package com.blankj.utilcode.util;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes2.dex */
public final class BusUtils {
    private static final Object NULL = "nULl";
    private static final String TAG = "BusUtils";
    private final Map<String, Set<Object>> mClassName_BusesMap;
    private final Map<String, Map<String, Object>> mClassName_Tag_Arg4StickyMap;
    private final Map<String, List<String>> mClassName_TagsMap;
    private final Map<String, List<BusInfo>> mTag_BusInfoListMap;

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface Bus {
        int priority() default 0;

        boolean sticky() default false;

        String tag();

        ThreadMode threadMode() default ThreadMode.POSTING;
    }

    public static final class BusInfo {
        String className;
        String funName;
        Method method;
        String paramName;
        String paramType;
        int priority;
        boolean sticky;
        List<String> subClassNames = new CopyOnWriteArrayList();
        String tag;
        String threadMode;

        public BusInfo(String str, String str2, String str3, String str4, String str5, boolean z2, String str6, int i2) {
            this.tag = str;
            this.className = str2;
            this.funName = str3;
            this.paramType = str4;
            this.paramName = str5;
            this.sticky = z2;
            this.threadMode = str6;
            this.priority = i2;
        }

        private String getDesc() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append(this.className);
            sb.append(DictionaryFactory.SHARP);
            sb.append(this.funName);
            if ("".equals(this.paramType)) {
                str = "()";
            } else {
                str = "(" + this.paramType + " " + this.paramName + ")";
            }
            sb.append(str);
            return sb.toString();
        }

        public String toString() {
            return "BusInfo { tag : " + this.tag + ", desc: " + getDesc() + ", sticky: " + this.sticky + ", threadMode: " + this.threadMode + ", method: " + this.method + ", priority: " + this.priority + " }";
        }
    }

    public static class LazyHolder {
        private static final BusUtils INSTANCE = new BusUtils();

        private LazyHolder() {
        }
    }

    public enum ThreadMode {
        MAIN,
        IO,
        CPU,
        CACHED,
        SINGLE,
        POSTING
    }

    private void consumeSticky(Object obj, String str, Object obj2) {
        List<BusInfo> list = this.mTag_BusInfoListMap.get(str);
        if (list == null) {
            Log.e(TAG, "The bus of tag <" + str + "> is not exists.");
            return;
        }
        for (BusInfo busInfo : list) {
            if (busInfo.subClassNames.contains(obj.getClass().getName()) && busInfo.sticky) {
                synchronized (this.mClassName_Tag_Arg4StickyMap) {
                    Map<String, Object> map = this.mClassName_Tag_Arg4StickyMap.get(busInfo.className);
                    if (map != null && map.containsKey(str)) {
                        invokeBus(obj, obj2, busInfo, true);
                    }
                }
            }
        }
    }

    private void consumeStickyIfExist(Object obj) {
        Map<String, Object> map = this.mClassName_Tag_Arg4StickyMap.get(obj.getClass().getName());
        if (map == null) {
            return;
        }
        synchronized (this.mClassName_Tag_Arg4StickyMap) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                consumeSticky(obj, entry.getKey(), entry.getValue());
            }
        }
    }

    private Class getClassName(String str) throws ClassNotFoundException {
        str.hashCode();
        switch (str) {
            case "double":
                return Double.TYPE;
            case "int":
                return Integer.TYPE;
            case "byte":
                return Byte.TYPE;
            case "char":
                return Character.TYPE;
            case "long":
                return Long.TYPE;
            case "boolean":
                return Boolean.TYPE;
            case "float":
                return Float.TYPE;
            case "short":
                return Short.TYPE;
            default:
                return Class.forName(str);
        }
    }

    private static BusUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    private Method getMethodByBusInfo(BusInfo busInfo) {
        try {
            return "".equals(busInfo.paramType) ? Class.forName(busInfo.className).getDeclaredMethod(busInfo.funName, new Class[0]) : Class.forName(busInfo.className).getDeclaredMethod(busInfo.funName, getClassName(busInfo.paramType));
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    private void init() {
    }

    private void invokeBus(Object obj, BusInfo busInfo, boolean z2) {
        invokeBus(null, obj, busInfo, z2);
    }

    private void invokeBuses(Object obj, BusInfo busInfo, Set<Object> set) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            if (obj == NULL) {
                Iterator<Object> it = set.iterator();
                while (it.hasNext()) {
                    busInfo.method.invoke(it.next(), new Object[0]);
                }
            } else {
                Iterator<Object> it2 = set.iterator();
                while (it2.hasNext()) {
                    busInfo.method.invoke(it2.next(), obj);
                }
            }
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
    }

    private void invokeMethod(Object obj, BusInfo busInfo, boolean z2) {
        invokeMethod(null, obj, busInfo, z2);
    }

    public static void post(@NonNull String str) {
        post(str, NULL);
    }

    private void postInner(String str, Object obj) {
        postInner(str, obj, false);
    }

    public static void postSticky(@NonNull String str) {
        postSticky(str, NULL);
    }

    private void postStickyInner(String str, Object obj) {
        List<BusInfo> list = this.mTag_BusInfoListMap.get(str);
        if (list == null) {
            Log.e(TAG, "The bus of tag <" + str + "> is not exists.");
            return;
        }
        for (BusInfo busInfo : list) {
            if (busInfo.sticky) {
                synchronized (this.mClassName_Tag_Arg4StickyMap) {
                    Map<String, Object> concurrentHashMap = this.mClassName_Tag_Arg4StickyMap.get(busInfo.className);
                    if (concurrentHashMap == null) {
                        concurrentHashMap = new ConcurrentHashMap<>();
                        this.mClassName_Tag_Arg4StickyMap.put(busInfo.className, concurrentHashMap);
                    }
                    concurrentHashMap.put(str, obj);
                }
                invokeBus(obj, busInfo, true);
            } else {
                invokeBus(obj, busInfo, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void realInvokeMethod(Object obj, Object obj2, BusInfo busInfo, boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Set<Object> hashSet = new HashSet<>();
        if (obj == null) {
            Iterator<String> it = busInfo.subClassNames.iterator();
            while (it.hasNext()) {
                Set<Object> set = this.mClassName_BusesMap.get(it.next());
                if (set != null && !set.isEmpty()) {
                    hashSet.addAll(set);
                }
            }
            if (hashSet.size() == 0) {
                if (z2) {
                    return;
                }
                Log.e(TAG, "The " + busInfo + " was not registered before.");
                return;
            }
        } else {
            hashSet.add(obj);
        }
        invokeBuses(obj2, busInfo, hashSet);
    }

    private void recordTags(Class<?> cls, String str) {
        if (this.mClassName_TagsMap.get(str) == null) {
            synchronized (this.mClassName_TagsMap) {
                if (this.mClassName_TagsMap.get(str) == null) {
                    CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
                    for (Map.Entry<String, List<BusInfo>> entry : this.mTag_BusInfoListMap.entrySet()) {
                        for (BusInfo busInfo : entry.getValue()) {
                            try {
                                if (Class.forName(busInfo.className).isAssignableFrom(cls)) {
                                    copyOnWriteArrayList.add(entry.getKey());
                                    busInfo.subClassNames.add(str);
                                }
                            } catch (ClassNotFoundException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                    this.mClassName_TagsMap.put(str, copyOnWriteArrayList);
                }
            }
        }
    }

    public static void register(@Nullable Object obj) {
        getInstance().registerInner(obj);
    }

    private void registerBus(String str, String str2, String str3, String str4, String str5, boolean z2, String str6) {
        registerBus(str, str2, str3, str4, str5, z2, str6, 0);
    }

    public static void registerBus4Test(String str, String str2, String str3, String str4, String str5, boolean z2, String str6, int i2) {
        getInstance().registerBus(str, str2, str3, str4, str5, z2, str6, i2);
    }

    private void registerInner(@Nullable Object obj) {
        boolean z2;
        if (obj == null) {
            return;
        }
        Class<?> cls = obj.getClass();
        String name = cls.getName();
        synchronized (this.mClassName_BusesMap) {
            Set<Object> copyOnWriteArraySet = this.mClassName_BusesMap.get(name);
            if (copyOnWriteArraySet == null) {
                copyOnWriteArraySet = new CopyOnWriteArraySet<>();
                this.mClassName_BusesMap.put(name, copyOnWriteArraySet);
                z2 = true;
            } else {
                z2 = false;
            }
            if (!copyOnWriteArraySet.contains(obj)) {
                copyOnWriteArraySet.add(obj);
                if (z2) {
                    recordTags(cls, name);
                }
                consumeStickyIfExist(obj);
                return;
            }
            Log.w(TAG, "The bus of <" + obj + "> already registered.");
        }
    }

    public static void removeSticky(String str) {
        getInstance().removeStickyInner(str);
    }

    private void removeStickyInner(String str) {
        List<BusInfo> list = this.mTag_BusInfoListMap.get(str);
        if (list == null) {
            Log.e(TAG, "The bus of tag <" + str + "> is not exists.");
            return;
        }
        for (BusInfo busInfo : list) {
            if (busInfo.sticky) {
                synchronized (this.mClassName_Tag_Arg4StickyMap) {
                    Map<String, Object> map = this.mClassName_Tag_Arg4StickyMap.get(busInfo.className);
                    if (map != null && map.containsKey(str)) {
                        map.remove(str);
                    }
                    return;
                }
            }
        }
    }

    public static String toString_() {
        return getInstance().toString();
    }

    public static void unregister(@Nullable Object obj) {
        getInstance().unregisterInner(obj);
    }

    private void unregisterInner(Object obj) {
        if (obj == null) {
            return;
        }
        String name = obj.getClass().getName();
        synchronized (this.mClassName_BusesMap) {
            Set<Object> set = this.mClassName_BusesMap.get(name);
            if (set != null && set.contains(obj)) {
                set.remove(obj);
                return;
            }
            Log.e(TAG, "The bus of <" + obj + "> was not registered before.");
        }
    }

    public String toString() {
        return "BusUtils: " + this.mTag_BusInfoListMap;
    }

    private BusUtils() {
        this.mTag_BusInfoListMap = new ConcurrentHashMap();
        this.mClassName_BusesMap = new ConcurrentHashMap();
        this.mClassName_TagsMap = new ConcurrentHashMap();
        this.mClassName_Tag_Arg4StickyMap = new ConcurrentHashMap();
        init();
    }

    private void invokeBus(Object obj, Object obj2, BusInfo busInfo, boolean z2) {
        if (busInfo.method == null) {
            Method methodByBusInfo = getMethodByBusInfo(busInfo);
            if (methodByBusInfo == null) {
                return;
            } else {
                busInfo.method = methodByBusInfo;
            }
        }
        invokeMethod(obj, obj2, busInfo, z2);
    }

    private void invokeMethod(final Object obj, final Object obj2, final BusInfo busInfo, final boolean z2) {
        Runnable runnable;
        runnable = new Runnable() { // from class: com.blankj.utilcode.util.BusUtils.1
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                BusUtils.this.realInvokeMethod(obj, obj2, busInfo, z2);
            }
        };
        String str = busInfo.threadMode;
        str.hashCode();
        switch (str) {
            case "SINGLE":
                ThreadUtils.getSinglePool().execute(runnable);
                break;
            case "IO":
                ThreadUtils.getIoPool().execute(runnable);
                break;
            case "CPU":
                ThreadUtils.getCpuPool().execute(runnable);
                break;
            case "MAIN":
                ThreadUtils.runOnUiThread(runnable);
                break;
            case "CACHED":
                ThreadUtils.getCachedPool().execute(runnable);
                break;
            default:
                runnable.run();
                break;
        }
    }

    public static void post(@NonNull String str, @NonNull Object obj) {
        getInstance().postInner(str, obj);
    }

    private void postInner(String str, Object obj, boolean z2) {
        List<BusInfo> list = this.mTag_BusInfoListMap.get(str);
        if (list != null) {
            Iterator<BusInfo> it = list.iterator();
            while (it.hasNext()) {
                invokeBus(obj, it.next(), z2);
            }
            return;
        }
        Log.e(TAG, "The bus of tag <" + str + "> is not exists.");
        if (this.mTag_BusInfoListMap.isEmpty()) {
            Log.e(TAG, "Please check whether the bus plugin is applied.");
        }
    }

    public static void postSticky(@NonNull String str, Object obj) {
        getInstance().postStickyInner(str, obj);
    }

    private void registerBus(String str, String str2, String str3, String str4, String str5, boolean z2, String str6, int i2) {
        List<BusInfo> copyOnWriteArrayList = this.mTag_BusInfoListMap.get(str);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            this.mTag_BusInfoListMap.put(str, copyOnWriteArrayList);
        }
        copyOnWriteArrayList.add(new BusInfo(str, str2, str3, str4, str5, z2, str6, i2));
    }
}
