package androidx.appcompat.app;

import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.util.Xml;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
class AppLocalesStorageHelper {
    static final String APPLICATION_LOCALES_RECORD_FILE = "androidx.appcompat.app.AppCompatDelegate.application_locales_record_file";
    static final String APP_LOCALES_META_DATA_HOLDER_SERVICE_NAME = "androidx.appcompat.app.AppLocalesMetadataHolderService";
    static final String LOCALE_RECORD_ATTRIBUTE_TAG = "application_locales";
    static final String LOCALE_RECORD_FILE_TAG = "locales";
    static final String TAG = "AppLocalesStorageHelper";

    public static class SerialExecutor implements Executor {
        Runnable mActive;
        final Executor mExecutor;
        private final Object mLock = new Object();
        final Queue<Runnable> mTasks = new ArrayDeque();

        public SerialExecutor(Executor executor) {
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$execute$0(Runnable runnable) {
            try {
                runnable.run();
            } finally {
                scheduleNext();
            }
        }

        @Override // java.util.concurrent.Executor
        public void execute(final Runnable runnable) {
            synchronized (this.mLock) {
                this.mTasks.add(new Runnable() { // from class: androidx.appcompat.app.m
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f1222c.lambda$execute$0(runnable);
                    }
                });
                if (this.mActive == null) {
                    scheduleNext();
                }
            }
        }

        public void scheduleNext() {
            synchronized (this.mLock) {
                Runnable runnablePoll = this.mTasks.poll();
                this.mActive = runnablePoll;
                if (runnablePoll != null) {
                    this.mExecutor.execute(runnablePoll);
                }
            }
        }
    }

    public static class ThreadPerTaskExecutor implements Executor {
        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            new Thread(runnable).start();
        }
    }

    private AppLocalesStorageHelper() {
    }

    public static void persistLocales(@NonNull Context context, @NonNull String str) throws IOException {
        if (str.equals("")) {
            context.deleteFile(APPLICATION_LOCALES_RECORD_FILE);
            return;
        }
        try {
            FileOutputStream fileOutputStreamOpenFileOutput = context.openFileOutput(APPLICATION_LOCALES_RECORD_FILE, 0);
            XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
            try {
                try {
                    xmlSerializerNewSerializer.setOutput(fileOutputStreamOpenFileOutput, null);
                    xmlSerializerNewSerializer.startDocument("UTF-8", Boolean.TRUE);
                    xmlSerializerNewSerializer.startTag(null, LOCALE_RECORD_FILE_TAG);
                    xmlSerializerNewSerializer.attribute(null, LOCALE_RECORD_ATTRIBUTE_TAG, str);
                    xmlSerializerNewSerializer.endTag(null, LOCALE_RECORD_FILE_TAG);
                    xmlSerializerNewSerializer.endDocument();
                    Log.d(TAG, "Storing App Locales : app-locales: " + str + " persisted successfully.");
                } catch (Exception e2) {
                    Log.w(TAG, "Storing App Locales : Failed to persist app-locales: " + str, e2);
                    if (fileOutputStreamOpenFileOutput != null) {
                    }
                }
                if (fileOutputStreamOpenFileOutput != null) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                    } catch (IOException unused) {
                    }
                }
            } catch (Throwable th) {
                if (fileOutputStreamOpenFileOutput != null) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException unused3) {
            Log.w(TAG, String.format("Storing App Locales : FileNotFoundException: Cannot open file %s for writing ", APPLICATION_LOCALES_RECORD_FILE));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0039, code lost:
    
        r2 = r4.getAttributeValue(null, androidx.appcompat.app.AppLocalesStorageHelper.LOCALE_RECORD_ATTRIBUTE_TAG);
     */
    @androidx.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String readLocales(@androidx.annotation.NonNull android.content.Context r9) throws java.io.IOException {
        /*
            java.lang.String r0 = "androidx.appcompat.app.AppCompatDelegate.application_locales_record_file"
            java.lang.String r1 = "AppLocalesStorageHelper"
            java.lang.String r2 = ""
            java.io.FileInputStream r3 = r9.openFileInput(r0)     // Catch: java.io.FileNotFoundException -> L75
            org.xmlpull.v1.XmlPullParser r4 = android.util.Xml.newPullParser()     // Catch: java.lang.Throwable -> L46 java.lang.Throwable -> L48
            java.lang.String r5 = "UTF-8"
            r4.setInput(r3, r5)     // Catch: java.lang.Throwable -> L46 java.lang.Throwable -> L48
            int r5 = r4.getDepth()     // Catch: java.lang.Throwable -> L46 java.lang.Throwable -> L48
        L17:
            int r6 = r4.next()     // Catch: java.lang.Throwable -> L46 java.lang.Throwable -> L48
            r7 = 1
            if (r6 == r7) goto L40
            r7 = 3
            if (r6 != r7) goto L27
            int r8 = r4.getDepth()     // Catch: java.lang.Throwable -> L46 java.lang.Throwable -> L48
            if (r8 <= r5) goto L40
        L27:
            if (r6 == r7) goto L17
            r7 = 4
            if (r6 != r7) goto L2d
            goto L17
        L2d:
            java.lang.String r6 = r4.getName()     // Catch: java.lang.Throwable -> L46 java.lang.Throwable -> L48
            java.lang.String r7 = "locales"
            boolean r6 = r6.equals(r7)     // Catch: java.lang.Throwable -> L46 java.lang.Throwable -> L48
            if (r6 == 0) goto L17
            java.lang.String r5 = "application_locales"
            r6 = 0
            java.lang.String r2 = r4.getAttributeValue(r6, r5)     // Catch: java.lang.Throwable -> L46 java.lang.Throwable -> L48
        L40:
            if (r3 == 0) goto L50
        L42:
            r3.close()     // Catch: java.io.IOException -> L50
            goto L50
        L46:
            r9 = move-exception
            goto L6f
        L48:
            java.lang.String r4 = "Reading app Locales : Unable to parse through file :androidx.appcompat.app.AppCompatDelegate.application_locales_record_file"
            android.util.Log.w(r1, r4)     // Catch: java.lang.Throwable -> L46
            if (r3 == 0) goto L50
            goto L42
        L50:
            boolean r3 = r2.isEmpty()
            if (r3 != 0) goto L6b
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r0 = "Reading app Locales : Locales read from file: androidx.appcompat.app.AppCompatDelegate.application_locales_record_file , appLocales: "
            r9.append(r0)
            r9.append(r2)
            java.lang.String r9 = r9.toString()
            android.util.Log.d(r1, r9)
            goto L6e
        L6b:
            r9.deleteFile(r0)
        L6e:
            return r2
        L6f:
            if (r3 == 0) goto L74
            r3.close()     // Catch: java.io.IOException -> L74
        L74:
            throw r9
        L75:
            java.lang.String r9 = "Reading app Locales : Locales record file not found: androidx.appcompat.app.AppCompatDelegate.application_locales_record_file"
            android.util.Log.w(r1, r9)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppLocalesStorageHelper.readLocales(android.content.Context):java.lang.String");
    }

    public static void syncLocalesToFramework(Context context) {
        if (Build.VERSION.SDK_INT >= 33) {
            ComponentName componentName = new ComponentName(context, APP_LOCALES_META_DATA_HOLDER_SERVICE_NAME);
            if (context.getPackageManager().getComponentEnabledSetting(componentName) != 1) {
                if (AppCompatDelegate.getApplicationLocales().isEmpty()) {
                    String locales = readLocales(context);
                    Object systemService = context.getSystemService("locale");
                    if (systemService != null) {
                        AppCompatDelegate.Api33Impl.localeManagerSetApplicationLocales(systemService, AppCompatDelegate.Api24Impl.localeListForLanguageTags(locales));
                    }
                }
                context.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
            }
        }
    }
}
