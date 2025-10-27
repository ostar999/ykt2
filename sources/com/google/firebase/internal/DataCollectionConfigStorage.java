package com.google.firebase.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.events.Event;
import com.google.firebase.events.Publisher;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class DataCollectionConfigStorage {

    @VisibleForTesting
    public static final String DATA_COLLECTION_DEFAULT_ENABLED = "firebase_data_collection_default_enabled";
    private static final String FIREBASE_APP_PREFS = "com.google.firebase.common.prefs:";
    private final Context applicationContext;
    private final AtomicBoolean dataCollectionDefaultEnabled = new AtomicBoolean(readAutoDataCollectionEnabled());
    private final Publisher publisher;
    private final SharedPreferences sharedPreferences;

    public DataCollectionConfigStorage(Context context, String str, Publisher publisher) {
        this.applicationContext = directBootSafe(context);
        this.sharedPreferences = context.getSharedPreferences(FIREBASE_APP_PREFS + str, 0);
        this.publisher = publisher;
    }

    private static Context directBootSafe(Context context) {
        return (Build.VERSION.SDK_INT < 24 || ContextCompat.isDeviceProtectedStorage(context)) ? context : ContextCompat.createDeviceProtectedStorageContext(context);
    }

    private boolean readAutoDataCollectionEnabled() {
        ApplicationInfo applicationInfo;
        Bundle bundle;
        if (this.sharedPreferences.contains(DATA_COLLECTION_DEFAULT_ENABLED)) {
            return this.sharedPreferences.getBoolean(DATA_COLLECTION_DEFAULT_ENABLED, true);
        }
        try {
            PackageManager packageManager = this.applicationContext.getPackageManager();
            if (packageManager != null && (applicationInfo = packageManager.getApplicationInfo(this.applicationContext.getPackageName(), 128)) != null && (bundle = applicationInfo.metaData) != null && bundle.containsKey(DATA_COLLECTION_DEFAULT_ENABLED)) {
                return applicationInfo.metaData.getBoolean(DATA_COLLECTION_DEFAULT_ENABLED);
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return true;
    }

    public boolean isEnabled() {
        return this.dataCollectionDefaultEnabled.get();
    }

    public void setEnabled(boolean z2) {
        if (this.dataCollectionDefaultEnabled.compareAndSet(!z2, z2)) {
            this.sharedPreferences.edit().putBoolean(DATA_COLLECTION_DEFAULT_ENABLED, z2).apply();
            this.publisher.publish(new Event<>(DataCollectionDefaultChange.class, new DataCollectionDefaultChange(z2)));
        }
    }
}
