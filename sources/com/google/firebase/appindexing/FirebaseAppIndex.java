package com.google.firebase.appindexing;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appindexing.internal.zzi;
import java.lang.ref.WeakReference;
import javax.annotation.concurrent.GuardedBy;

/* loaded from: classes4.dex */
public abstract class FirebaseAppIndex {
    public static final String ACTION_UPDATE_INDEX = "com.google.firebase.appindexing.UPDATE_INDEX";
    public static final String APP_INDEXING_API_TAG = "FirebaseAppIndex";
    public static final String EXTRA_UPDATE_INDEX_REASON = "com.google.firebase.appindexing.extra.REASON";
    public static final int EXTRA_UPDATE_INDEX_REASON_REBUILD = 1;
    public static final int EXTRA_UPDATE_INDEX_REASON_REFRESH = 2;

    @GuardedBy("FirebaseAppIndex.class")
    private static WeakReference<FirebaseAppIndex> zzen;

    public static synchronized FirebaseAppIndex getInstance() {
        FirebaseAppIndex firebaseAppIndex;
        WeakReference<FirebaseAppIndex> weakReference = zzen;
        firebaseAppIndex = weakReference == null ? null : weakReference.get();
        if (firebaseAppIndex == null) {
            zzi zziVar = new zzi(FirebaseApp.getInstance().getApplicationContext());
            zzen = new WeakReference<>(zziVar);
            firebaseAppIndex = zziVar;
        }
        return firebaseAppIndex;
    }

    public abstract Task<Void> remove(String... strArr);

    public abstract Task<Void> removeAll();

    public abstract Task<Void> update(Indexable... indexableArr);
}
