package de.greenrobot.event;

/* loaded from: classes8.dex */
final class Subscription {
    volatile boolean active = true;
    final int priority;
    final Object subscriber;
    final SubscriberMethod subscriberMethod;

    public Subscription(Object obj, SubscriberMethod subscriberMethod, int i2) {
        this.subscriber = obj;
        this.subscriberMethod = subscriberMethod;
        this.priority = i2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Subscription)) {
            return false;
        }
        Subscription subscription = (Subscription) obj;
        return this.subscriber == subscription.subscriber && this.subscriberMethod.equals(subscription.subscriberMethod);
    }

    public int hashCode() {
        return this.subscriber.hashCode() + this.subscriberMethod.methodString.hashCode();
    }
}
