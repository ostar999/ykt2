package de.greenrobot.event;

import android.os.Looper;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

/* loaded from: classes8.dex */
public class EventBus {
    public static String TAG = "Event";
    static volatile EventBus defaultInstance;
    private final AsyncPoster asyncPoster;
    private final BackgroundPoster backgroundPoster;
    private final ThreadLocal<PostingThreadState> currentPostingThreadState;
    private final boolean eventInheritance;
    private final ExecutorService executorService;
    private final boolean logNoSubscriberMessages;
    private final boolean logSubscriberExceptions;
    private final HandlerPoster mainThreadPoster;
    private final boolean sendNoSubscriberEvent;
    private final boolean sendSubscriberExceptionEvent;
    private final Map<Class<?>, Object> stickyEvents;
    private final SubscriberMethodFinder subscriberMethodFinder;
    private final Map<Class<?>, CopyOnWriteArrayList<Subscription>> subscriptionsByEventType;
    private final boolean throwSubscriberException;
    private final Map<Object, List<Class<?>>> typesBySubscriber;
    private static final EventBusBuilder DEFAULT_BUILDER = new EventBusBuilder();
    private static final Map<Class<?>, List<Class<?>>> eventTypesCache = new HashMap();

    /* renamed from: de.greenrobot.event.EventBus$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$de$greenrobot$event$ThreadMode;

        static {
            int[] iArr = new int[ThreadMode.values().length];
            $SwitchMap$de$greenrobot$event$ThreadMode = iArr;
            try {
                iArr[ThreadMode.PostThread.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$de$greenrobot$event$ThreadMode[ThreadMode.MainThread.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$de$greenrobot$event$ThreadMode[ThreadMode.BackgroundThread.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$de$greenrobot$event$ThreadMode[ThreadMode.Async.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public interface PostCallback {
        void onPostCompleted(List<SubscriberExceptionEvent> list);
    }

    public static final class PostingThreadState {
        boolean canceled;
        Object event;
        final List<Object> eventQueue = new ArrayList();
        boolean isMainThread;
        boolean isPosting;
        Subscription subscription;
    }

    public EventBus() {
        this(DEFAULT_BUILDER);
    }

    public static void addInterfaces(List<Class<?>> list, Class<?>[] clsArr) {
        for (Class<?> cls : clsArr) {
            if (!list.contains(cls)) {
                list.add(cls);
                addInterfaces(list, cls.getInterfaces());
            }
        }
    }

    public static EventBusBuilder builder() {
        return new EventBusBuilder();
    }

    private void checkPostStickyEventToSubscription(Subscription subscription, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (obj != null) {
            postToSubscription(subscription, obj, Looper.getMainLooper() == Looper.myLooper());
        }
    }

    public static void clearCaches() {
        SubscriberMethodFinder.clearCaches();
        eventTypesCache.clear();
    }

    public static EventBus getDefault() {
        if (defaultInstance == null) {
            synchronized (EventBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new EventBus();
                }
            }
        }
        return defaultInstance;
    }

    private void handleSubscriberException(Subscription subscription, Object obj, Throwable th) {
        if (!(obj instanceof SubscriberExceptionEvent)) {
            if (this.throwSubscriberException) {
                throw new EventBusException("Invoking subscriber failed", th);
            }
            if (this.logSubscriberExceptions) {
                Log.e(TAG, "Could not dispatch event: " + obj.getClass() + " to subscribing class " + subscription.subscriber.getClass(), th);
            }
            if (this.sendSubscriberExceptionEvent) {
                post(new SubscriberExceptionEvent(this, th, obj, subscription.subscriber));
                return;
            }
            return;
        }
        if (this.logSubscriberExceptions) {
            Log.e(TAG, "SubscriberExceptionEvent subscriber " + subscription.subscriber.getClass() + " threw an exception", th);
            SubscriberExceptionEvent subscriberExceptionEvent = (SubscriberExceptionEvent) obj;
            Log.e(TAG, "Initial event " + subscriberExceptionEvent.causingEvent + " caused exception in " + subscriberExceptionEvent.causingSubscriber, subscriberExceptionEvent.throwable);
        }
    }

    private List<Class<?>> lookupAllEventTypes(Class<?> cls) {
        List<Class<?>> arrayList;
        Map<Class<?>, List<Class<?>>> map = eventTypesCache;
        synchronized (map) {
            arrayList = map.get(cls);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                for (Class<?> superclass = cls; superclass != null; superclass = superclass.getSuperclass()) {
                    arrayList.add(superclass);
                    addInterfaces(arrayList, superclass.getInterfaces());
                }
                eventTypesCache.put(cls, arrayList);
            }
        }
        return arrayList;
    }

    private void postSingleEvent(Object obj, PostingThreadState postingThreadState) throws Error {
        boolean zPostSingleEventForEventType;
        Class<?> cls = obj.getClass();
        if (this.eventInheritance) {
            List<Class<?>> listLookupAllEventTypes = lookupAllEventTypes(cls);
            int size = listLookupAllEventTypes.size();
            zPostSingleEventForEventType = false;
            for (int i2 = 0; i2 < size; i2++) {
                zPostSingleEventForEventType |= postSingleEventForEventType(obj, postingThreadState, listLookupAllEventTypes.get(i2));
            }
        } else {
            zPostSingleEventForEventType = postSingleEventForEventType(obj, postingThreadState, cls);
        }
        if (zPostSingleEventForEventType) {
            return;
        }
        if (this.logNoSubscriberMessages) {
            Log.d(TAG, "No subscribers registered for event " + cls);
        }
        if (!this.sendNoSubscriberEvent || cls == NoSubscriberEvent.class || cls == SubscriberExceptionEvent.class) {
            return;
        }
        post(new NoSubscriberEvent(this, obj));
    }

    private boolean postSingleEventForEventType(Object obj, PostingThreadState postingThreadState, Class<?> cls) {
        CopyOnWriteArrayList<Subscription> copyOnWriteArrayList;
        synchronized (this) {
            copyOnWriteArrayList = this.subscriptionsByEventType.get(cls);
        }
        if (copyOnWriteArrayList == null || copyOnWriteArrayList.isEmpty()) {
            return false;
        }
        Iterator<Subscription> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            Subscription next = it.next();
            postingThreadState.event = obj;
            postingThreadState.subscription = next;
            try {
                postToSubscription(next, obj, postingThreadState.isMainThread);
                if (postingThreadState.canceled) {
                    return true;
                }
            } finally {
                postingThreadState.event = null;
                postingThreadState.subscription = null;
                postingThreadState.canceled = false;
            }
        }
        return true;
    }

    private void postToSubscription(Subscription subscription, Object obj, boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int i2 = AnonymousClass2.$SwitchMap$de$greenrobot$event$ThreadMode[subscription.subscriberMethod.threadMode.ordinal()];
        if (i2 == 1) {
            invokeSubscriber(subscription, obj);
            return;
        }
        if (i2 == 2) {
            if (z2) {
                invokeSubscriber(subscription, obj);
                return;
            } else {
                this.mainThreadPoster.enqueue(subscription, obj);
                return;
            }
        }
        if (i2 == 3) {
            if (z2) {
                this.backgroundPoster.enqueue(subscription, obj);
                return;
            } else {
                invokeSubscriber(subscription, obj);
                return;
            }
        }
        if (i2 == 4) {
            this.asyncPoster.enqueue(subscription, obj);
            return;
        }
        throw new IllegalStateException("Unknown thread mode: " + subscription.subscriberMethod.threadMode);
    }

    private void subscribe(Object obj, SubscriberMethod subscriberMethod, boolean z2, int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls = subscriberMethod.eventType;
        CopyOnWriteArrayList<Subscription> copyOnWriteArrayList = this.subscriptionsByEventType.get(cls);
        Subscription subscription = new Subscription(obj, subscriberMethod, i2);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            this.subscriptionsByEventType.put(cls, copyOnWriteArrayList);
        } else if (copyOnWriteArrayList.contains(subscription)) {
            throw new EventBusException("Subscriber " + obj.getClass() + " already registered to event " + cls);
        }
        int size = copyOnWriteArrayList.size();
        for (int i3 = 0; i3 <= size; i3++) {
            if (i3 == size || subscription.priority > copyOnWriteArrayList.get(i3).priority) {
                copyOnWriteArrayList.add(i3, subscription);
                break;
            }
        }
        List<Class<?>> arrayList = this.typesBySubscriber.get(obj);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.typesBySubscriber.put(obj, arrayList);
        }
        arrayList.add(cls);
        if (z2) {
            if (!this.eventInheritance) {
                checkPostStickyEventToSubscription(subscription, this.stickyEvents.get(cls));
                return;
            }
            for (Map.Entry<Class<?>, Object> entry : this.stickyEvents.entrySet()) {
                if (cls.isAssignableFrom(entry.getKey())) {
                    checkPostStickyEventToSubscription(subscription, entry.getValue());
                }
            }
        }
    }

    private void unsubscribeByEventType(Object obj, Class<?> cls) {
        CopyOnWriteArrayList<Subscription> copyOnWriteArrayList = this.subscriptionsByEventType.get(cls);
        if (copyOnWriteArrayList != null) {
            int size = copyOnWriteArrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Subscription subscription = copyOnWriteArrayList.get(i2);
                if (subscription.subscriber == obj) {
                    subscription.active = false;
                    copyOnWriteArrayList.remove(i2);
                    i2--;
                    size--;
                }
                i2++;
            }
        }
    }

    public void cancelEventDelivery(Object obj) {
        PostingThreadState postingThreadState = this.currentPostingThreadState.get();
        if (!postingThreadState.isPosting) {
            throw new EventBusException("This method may only be called from inside event handling methods on the posting thread");
        }
        if (obj == null) {
            throw new EventBusException("Event may not be null");
        }
        if (postingThreadState.event != obj) {
            throw new EventBusException("Only the currently handled event may be aborted");
        }
        if (postingThreadState.subscription.subscriberMethod.threadMode != ThreadMode.PostThread) {
            throw new EventBusException(" event handlers may only abort the incoming event");
        }
        postingThreadState.canceled = true;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public <T> T getStickyEvent(Class<T> cls) {
        T tCast;
        synchronized (this.stickyEvents) {
            tCast = cls.cast(this.stickyEvents.get(cls));
        }
        return tCast;
    }

    public boolean hasSubscriberForEvent(Class<?> cls) {
        CopyOnWriteArrayList<Subscription> copyOnWriteArrayList;
        List<Class<?>> listLookupAllEventTypes = lookupAllEventTypes(cls);
        if (listLookupAllEventTypes != null) {
            int size = listLookupAllEventTypes.size();
            for (int i2 = 0; i2 < size; i2++) {
                Class<?> cls2 = listLookupAllEventTypes.get(i2);
                synchronized (this) {
                    copyOnWriteArrayList = this.subscriptionsByEventType.get(cls2);
                }
                if (copyOnWriteArrayList != null && !copyOnWriteArrayList.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void invokeSubscriber(PendingPost pendingPost) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object obj = pendingPost.event;
        Subscription subscription = pendingPost.subscription;
        PendingPost.releasePendingPost(pendingPost);
        if (subscription.active) {
            invokeSubscriber(subscription, obj);
        }
    }

    public synchronized boolean isRegistered(Object obj) {
        return this.typesBySubscriber.containsKey(obj);
    }

    public void post(Object obj) {
        PostingThreadState postingThreadState = this.currentPostingThreadState.get();
        List<Object> list = postingThreadState.eventQueue;
        list.add(obj);
        if (postingThreadState.isPosting) {
            return;
        }
        postingThreadState.isMainThread = Looper.getMainLooper() == Looper.myLooper();
        postingThreadState.isPosting = true;
        if (postingThreadState.canceled) {
            throw new EventBusException("Internal error. Abort state was not reset");
        }
        while (!list.isEmpty()) {
            try {
                postSingleEvent(list.remove(0), postingThreadState);
            } finally {
                postingThreadState.isPosting = false;
                postingThreadState.isMainThread = false;
            }
        }
    }

    public void postSticky(Object obj) {
        synchronized (this.stickyEvents) {
            this.stickyEvents.put(obj.getClass(), obj);
        }
        post(obj);
    }

    public void register(Object obj) {
        register(obj, false, 0);
    }

    public void registerSticky(Object obj) {
        register(obj, true, 0);
    }

    public void removeAllStickyEvents() {
        synchronized (this.stickyEvents) {
            this.stickyEvents.clear();
        }
    }

    public <T> T removeStickyEvent(Class<T> cls) {
        T tCast;
        synchronized (this.stickyEvents) {
            tCast = cls.cast(this.stickyEvents.remove(cls));
        }
        return tCast;
    }

    public synchronized void unregister(Object obj) {
        List<Class<?>> list = this.typesBySubscriber.get(obj);
        if (list != null) {
            Iterator<Class<?>> it = list.iterator();
            while (it.hasNext()) {
                unsubscribeByEventType(obj, it.next());
            }
            this.typesBySubscriber.remove(obj);
        } else {
            Log.w(TAG, "Subscriber to unregister was not registered before: " + obj.getClass());
        }
    }

    public EventBus(EventBusBuilder eventBusBuilder) {
        this.currentPostingThreadState = new ThreadLocal<PostingThreadState>() { // from class: de.greenrobot.event.EventBus.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.lang.ThreadLocal
            public PostingThreadState initialValue() {
                return new PostingThreadState();
            }
        };
        this.subscriptionsByEventType = new HashMap();
        this.typesBySubscriber = new HashMap();
        this.stickyEvents = new ConcurrentHashMap();
        this.mainThreadPoster = new HandlerPoster(this, Looper.getMainLooper(), 10);
        this.backgroundPoster = new BackgroundPoster(this);
        this.asyncPoster = new AsyncPoster(this);
        this.subscriberMethodFinder = new SubscriberMethodFinder(eventBusBuilder.skipMethodVerificationForClasses);
        this.logSubscriberExceptions = eventBusBuilder.logSubscriberExceptions;
        this.logNoSubscriberMessages = eventBusBuilder.logNoSubscriberMessages;
        this.sendSubscriberExceptionEvent = eventBusBuilder.sendSubscriberExceptionEvent;
        this.sendNoSubscriberEvent = eventBusBuilder.sendNoSubscriberEvent;
        this.throwSubscriberException = eventBusBuilder.throwSubscriberException;
        this.eventInheritance = eventBusBuilder.eventInheritance;
        this.executorService = eventBusBuilder.executorService;
    }

    public void register(Object obj, int i2) {
        register(obj, false, i2);
    }

    public void registerSticky(Object obj, int i2) {
        register(obj, true, i2);
    }

    private synchronized void register(Object obj, boolean z2, int i2) {
        Iterator<SubscriberMethod> it = this.subscriberMethodFinder.findSubscriberMethods(obj.getClass()).iterator();
        while (it.hasNext()) {
            subscribe(obj, it.next(), z2, i2);
        }
    }

    public boolean removeStickyEvent(Object obj) {
        synchronized (this.stickyEvents) {
            Class<?> cls = obj.getClass();
            if (!obj.equals(this.stickyEvents.get(cls))) {
                return false;
            }
            this.stickyEvents.remove(cls);
            return true;
        }
    }

    public void invokeSubscriber(Subscription subscription, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            subscription.subscriberMethod.method.invoke(subscription.subscriber, obj);
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException("Unexpected exception", e2);
        } catch (InvocationTargetException e3) {
            handleSubscriberException(subscription, obj, e3.getCause());
        }
    }
}
