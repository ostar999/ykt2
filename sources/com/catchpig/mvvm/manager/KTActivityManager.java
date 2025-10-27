package com.catchpig.mvvm.manager;

import android.app.Activity;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\u0018\u0000 \u000e2\u00020\u0001:\u0002\u000e\u000fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005J\u0006\u0010\t\u001a\u00020\u0007J\u0006\u0010\n\u001a\u00020\u0007J\u0006\u0010\u000b\u001a\u00020\u0005J\u0006\u0010\f\u001a\u00020\u0005J\u0010\u0010\r\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/catchpig/mvvm/manager/KTActivityManager;", "", "()V", "activities", "Ljava/util/LinkedList;", "Landroid/app/Activity;", "addActivity", "", PushConstants.INTENT_ACTIVITY_NAME, "finishAllActivities", "finishAllActivitiesExceptTop", "getFirstActivity", "getTopActivity", "removeActivity", "Companion", "KTActivityManagerHolder", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class KTActivityManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private LinkedList<Activity> activities;

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/catchpig/mvvm/manager/KTActivityManager$Companion;", "", "()V", "getInstance", "Lcom/catchpig/mvvm/manager/KTActivityManager;", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KTActivityManager getInstance() {
            return KTActivityManagerHolder.INSTANCE.getHolder();
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/catchpig/mvvm/manager/KTActivityManager$KTActivityManagerHolder;", "", "()V", "holder", "Lcom/catchpig/mvvm/manager/KTActivityManager;", "getHolder", "()Lcom/catchpig/mvvm/manager/KTActivityManager;", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class KTActivityManagerHolder {

        @NotNull
        public static final KTActivityManagerHolder INSTANCE = new KTActivityManagerHolder();

        @NotNull
        private static final KTActivityManager holder = new KTActivityManager(null);

        private KTActivityManagerHolder() {
        }

        @NotNull
        public final KTActivityManager getHolder() {
            return holder;
        }
    }

    private KTActivityManager() {
        this.activities = new LinkedList<>();
    }

    public /* synthetic */ KTActivityManager(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public final void addActivity(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.activities.add(activity);
    }

    public final void finishAllActivities() {
        Iterator<Activity> it = this.activities.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "activities.iterator()");
        while (it.hasNext()) {
            Activity next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "iterator.next()");
            next.finish();
            it.remove();
        }
    }

    public final void finishAllActivitiesExceptTop() {
        Activity topActivity = getTopActivity();
        Iterator<Activity> it = this.activities.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "activities.iterator()");
        while (it.hasNext()) {
            Activity next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "iterator.next()");
            Activity activity = next;
            if (!Intrinsics.areEqual(activity, topActivity)) {
                activity.finish();
            }
        }
    }

    @NotNull
    public final Activity getFirstActivity() {
        Activity first = this.activities.getFirst();
        Intrinsics.checkNotNullExpressionValue(first, "activities.first");
        return first;
    }

    @NotNull
    public final Activity getTopActivity() {
        return (Activity) CollectionsKt___CollectionsKt.last((List) this.activities);
    }

    public final void removeActivity(@Nullable Activity activity) {
        TypeIntrinsics.asMutableCollection(this.activities).remove(activity);
    }
}
