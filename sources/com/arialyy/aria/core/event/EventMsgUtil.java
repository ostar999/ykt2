package com.arialyy.aria.core.event;

import com.arialyy.aria.util.ALog;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class EventMsgUtil {
    private static final String TAG = "EventUtil";
    private static EventMsgUtil defaultInstance;
    private Map<Object, List<EventMethodInfo>> mEventMethods = new ConcurrentHashMap();
    private ArrayBlockingQueue<Object> mEventQueue = new ArrayBlockingQueue<>(10);
    private ExecutorService mPool = Executors.newFixedThreadPool(5);

    private EventMsgUtil() {
        Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.arialyy.aria.core.event.EventMsgUtil.1
            @Override // java.lang.Runnable
            public void run() throws InterruptedException {
                while (true) {
                    try {
                        EventMsgUtil.this.sendEvent(EventMsgUtil.this.mEventQueue.take());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });
    }

    public static EventMsgUtil getDefault() {
        if (defaultInstance == null) {
            synchronized (EventMsgUtil.class) {
                if (defaultInstance == null) {
                    defaultInstance = new EventMsgUtil();
                }
            }
        }
        return defaultInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEvent(final Object obj) {
        this.mPool.submit(new Runnable() { // from class: com.arialyy.aria.core.event.EventMsgUtil.2
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                for (Object obj2 : EventMsgUtil.this.mEventMethods.keySet()) {
                    List<EventMethodInfo> list = (List) EventMsgUtil.this.mEventMethods.get(obj2);
                    if (list != null && !list.isEmpty()) {
                        for (EventMethodInfo eventMethodInfo : list) {
                            try {
                                if (eventMethodInfo.param == obj.getClass()) {
                                    Method declaredMethod = obj2.getClass().getDeclaredMethod(eventMethodInfo.methodName, eventMethodInfo.param);
                                    declaredMethod.setAccessible(true);
                                    declaredMethod.invoke(obj2, obj);
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

    public void post(Object obj) {
        synchronized (EventMsgUtil.class) {
            try {
                this.mEventQueue.offer(obj, 2L, TimeUnit.SECONDS);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void register(Object obj) throws SecurityException {
        for (Method method : obj.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.getAnnotation(Event.class) != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 0 || parameterTypes.length > 1) {
                    ALog.e(TAG, String.format("%s.%s参数数量为0或参数数量大于1", obj.getClass().getName(), method.getName()));
                } else {
                    int modifiers = method.getModifiers();
                    if (Modifier.isStatic(modifiers) || Modifier.isAbstract(modifiers) || Modifier.isFinal(modifiers)) {
                        ALog.e(TAG, "注册的方法不能使用final、static、abstract修饰");
                    } else {
                        EventMethodInfo eventMethodInfo = new EventMethodInfo();
                        eventMethodInfo.methodName = method.getName();
                        eventMethodInfo.param = parameterTypes[0];
                        List<EventMethodInfo> arrayList = this.mEventMethods.get(obj);
                        if (arrayList == null) {
                            arrayList = new ArrayList<>();
                            this.mEventMethods.put(obj, arrayList);
                        }
                        arrayList.add(eventMethodInfo);
                    }
                }
            }
        }
    }

    public void unRegister(Object obj) {
        Iterator<Map.Entry<Object, List<EventMethodInfo>>> it = this.mEventMethods.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, List<EventMethodInfo>> next = it.next();
            if (next.getKey().equals(obj)) {
                next.getValue().clear();
                it.remove();
            }
        }
    }
}
