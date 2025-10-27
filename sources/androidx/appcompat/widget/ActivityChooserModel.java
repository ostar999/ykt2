package androidx.appcompat.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import cn.hutool.core.text.StrPool;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
class ActivityChooserModel extends DataSetObservable {
    static final String ATTRIBUTE_ACTIVITY = "activity";
    static final String ATTRIBUTE_TIME = "time";
    static final String ATTRIBUTE_WEIGHT = "weight";
    static final boolean DEBUG = false;
    private static final int DEFAULT_ACTIVITY_INFLATION = 5;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final String HISTORY_FILE_EXTENSION = ".xml";
    private static final int INVALID_INDEX = -1;
    static final String LOG_TAG = "ActivityChooserModel";
    static final String TAG_HISTORICAL_RECORD = "historical-record";
    static final String TAG_HISTORICAL_RECORDS = "historical-records";
    private OnChooseActivityListener mActivityChoserModelPolicy;
    final Context mContext;
    final String mHistoryFileName;
    private Intent mIntent;
    private static final Object sRegistryLock = new Object();
    private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();
    private final Object mInstanceLock = new Object();
    private final List<ActivityResolveInfo> mActivities = new ArrayList();
    private final List<HistoricalRecord> mHistoricalRecords = new ArrayList();
    private ActivitySorter mActivitySorter = new DefaultSorter();
    private int mHistoryMaxSize = 50;
    boolean mCanReadHistoricalData = true;
    private boolean mReadShareHistoryCalled = false;
    private boolean mHistoricalRecordsChanged = true;
    private boolean mReloadActivities = false;

    public interface ActivityChooserModelClient {
        void setActivityChooserModel(ActivityChooserModel activityChooserModel);
    }

    public static final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(ResolveInfo resolveInfo) {
            this.resolveInfo = resolveInfo;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && ActivityResolveInfo.class == obj.getClass() && Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo) obj).weight);
        }

        public int hashCode() {
            return Float.floatToIntBits(this.weight) + 31;
        }

        public String toString() {
            return StrPool.BRACKET_START + "resolveInfo:" + this.resolveInfo.toString() + "; weight:" + new BigDecimal(this.weight) + StrPool.BRACKET_END;
        }

        @Override // java.lang.Comparable
        public int compareTo(ActivityResolveInfo activityResolveInfo) {
            return Float.floatToIntBits(activityResolveInfo.weight) - Float.floatToIntBits(this.weight);
        }
    }

    public interface ActivitySorter {
        void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2);
    }

    public static final class DefaultSorter implements ActivitySorter {
        private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
        private final Map<ComponentName, ActivityResolveInfo> mPackageNameToActivityMap = new HashMap();

        @Override // androidx.appcompat.widget.ActivityChooserModel.ActivitySorter
        public void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2) {
            Map<ComponentName, ActivityResolveInfo> map = this.mPackageNameToActivityMap;
            map.clear();
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                ActivityResolveInfo activityResolveInfo = list.get(i2);
                activityResolveInfo.weight = 0.0f;
                ActivityInfo activityInfo = activityResolveInfo.resolveInfo.activityInfo;
                map.put(new ComponentName(activityInfo.packageName, activityInfo.name), activityResolveInfo);
            }
            float f2 = 1.0f;
            for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
                HistoricalRecord historicalRecord = list2.get(size2);
                ActivityResolveInfo activityResolveInfo2 = map.get(historicalRecord.activity);
                if (activityResolveInfo2 != null) {
                    activityResolveInfo2.weight += historicalRecord.weight * f2;
                    f2 *= WEIGHT_DECAY_COEFFICIENT;
                }
            }
            Collections.sort(list);
        }
    }

    public static final class HistoricalRecord {
        public final ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(String str, long j2, float f2) {
            this(ComponentName.unflattenFromString(str), j2, f2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || HistoricalRecord.class != obj.getClass()) {
                return false;
            }
            HistoricalRecord historicalRecord = (HistoricalRecord) obj;
            ComponentName componentName = this.activity;
            if (componentName == null) {
                if (historicalRecord.activity != null) {
                    return false;
                }
            } else if (!componentName.equals(historicalRecord.activity)) {
                return false;
            }
            return this.time == historicalRecord.time && Float.floatToIntBits(this.weight) == Float.floatToIntBits(historicalRecord.weight);
        }

        public int hashCode() {
            ComponentName componentName = this.activity;
            int iHashCode = componentName == null ? 0 : componentName.hashCode();
            long j2 = this.time;
            return ((((iHashCode + 31) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + Float.floatToIntBits(this.weight);
        }

        public String toString() {
            return StrPool.BRACKET_START + "; activity:" + this.activity + "; time:" + this.time + "; weight:" + new BigDecimal(this.weight) + StrPool.BRACKET_END;
        }

        public HistoricalRecord(ComponentName componentName, long j2, float f2) {
            this.activity = componentName;
            this.time = j2;
            this.weight = f2;
        }
    }

    public interface OnChooseActivityListener {
        boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent);
    }

    public final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {
        public PersistHistoryAsyncTask() {
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Object... objArr) throws IOException {
            List list = (List) objArr[0];
            String str = (String) objArr[1];
            try {
                FileOutputStream fileOutputStreamOpenFileOutput = ActivityChooserModel.this.mContext.openFileOutput(str, 0);
                XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
                try {
                    try {
                        try {
                            xmlSerializerNewSerializer.setOutput(fileOutputStreamOpenFileOutput, null);
                            xmlSerializerNewSerializer.startDocument("UTF-8", Boolean.TRUE);
                            xmlSerializerNewSerializer.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                            int size = list.size();
                            for (int i2 = 0; i2 < size; i2++) {
                                HistoricalRecord historicalRecord = (HistoricalRecord) list.remove(0);
                                xmlSerializerNewSerializer.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
                                xmlSerializerNewSerializer.attribute(null, "activity", historicalRecord.activity.flattenToString());
                                xmlSerializerNewSerializer.attribute(null, "time", String.valueOf(historicalRecord.time));
                                xmlSerializerNewSerializer.attribute(null, ActivityChooserModel.ATTRIBUTE_WEIGHT, String.valueOf(historicalRecord.weight));
                                xmlSerializerNewSerializer.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
                            }
                            xmlSerializerNewSerializer.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                            xmlSerializerNewSerializer.endDocument();
                            ActivityChooserModel.this.mCanReadHistoricalData = true;
                        } catch (IllegalArgumentException e2) {
                            Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + ActivityChooserModel.this.mHistoryFileName, e2);
                            ActivityChooserModel.this.mCanReadHistoricalData = true;
                            if (fileOutputStreamOpenFileOutput != null) {
                            }
                        } catch (IllegalStateException e3) {
                            Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + ActivityChooserModel.this.mHistoryFileName, e3);
                            ActivityChooserModel.this.mCanReadHistoricalData = true;
                            if (fileOutputStreamOpenFileOutput != null) {
                            }
                        }
                    } catch (IOException e4) {
                        Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + ActivityChooserModel.this.mHistoryFileName, e4);
                        ActivityChooserModel.this.mCanReadHistoricalData = true;
                        if (fileOutputStreamOpenFileOutput != null) {
                        }
                    }
                    if (fileOutputStreamOpenFileOutput != null) {
                        try {
                            fileOutputStreamOpenFileOutput.close();
                        } catch (IOException unused) {
                        }
                    }
                    return null;
                } catch (Throwable th) {
                    ActivityChooserModel.this.mCanReadHistoricalData = true;
                    if (fileOutputStreamOpenFileOutput != null) {
                        try {
                            fileOutputStreamOpenFileOutput.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + str, e5);
                return null;
            }
        }
    }

    private ActivityChooserModel(Context context, String str) {
        this.mContext = context.getApplicationContext();
        if (TextUtils.isEmpty(str) || str.endsWith(HISTORY_FILE_EXTENSION)) {
            this.mHistoryFileName = str;
            return;
        }
        this.mHistoryFileName = str + HISTORY_FILE_EXTENSION;
    }

    private boolean addHistoricalRecord(HistoricalRecord historicalRecord) {
        boolean zAdd = this.mHistoricalRecords.add(historicalRecord);
        if (zAdd) {
            this.mHistoricalRecordsChanged = true;
            pruneExcessiveHistoricalRecordsIfNeeded();
            persistHistoricalDataIfNeeded();
            sortActivitiesIfNeeded();
            notifyChanged();
        }
        return zAdd;
    }

    private void ensureConsistentState() {
        boolean zLoadActivitiesIfNeeded = loadActivitiesIfNeeded() | readHistoricalDataIfNeeded();
        pruneExcessiveHistoricalRecordsIfNeeded();
        if (zLoadActivitiesIfNeeded) {
            sortActivitiesIfNeeded();
            notifyChanged();
        }
    }

    public static ActivityChooserModel get(Context context, String str) {
        ActivityChooserModel activityChooserModel;
        synchronized (sRegistryLock) {
            Map<String, ActivityChooserModel> map = sDataModelRegistry;
            activityChooserModel = map.get(str);
            if (activityChooserModel == null) {
                activityChooserModel = new ActivityChooserModel(context, str);
                map.put(str, activityChooserModel);
            }
        }
        return activityChooserModel;
    }

    private boolean loadActivitiesIfNeeded() {
        if (!this.mReloadActivities || this.mIntent == null) {
            return false;
        }
        this.mReloadActivities = false;
        this.mActivities.clear();
        List<ResolveInfo> listQueryIntentActivities = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        int size = listQueryIntentActivities.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mActivities.add(new ActivityResolveInfo(listQueryIntentActivities.get(i2)));
        }
        return true;
    }

    private void persistHistoricalDataIfNeeded() {
        if (!this.mReadShareHistoryCalled) {
            throw new IllegalStateException("No preceding call to #readHistoricalData");
        }
        if (this.mHistoricalRecordsChanged) {
            this.mHistoricalRecordsChanged = false;
            if (TextUtils.isEmpty(this.mHistoryFileName)) {
                return;
            }
            new PersistHistoryAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new ArrayList(this.mHistoricalRecords), this.mHistoryFileName);
        }
    }

    private void pruneExcessiveHistoricalRecordsIfNeeded() {
        int size = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
        if (size <= 0) {
            return;
        }
        this.mHistoricalRecordsChanged = true;
        for (int i2 = 0; i2 < size; i2++) {
            this.mHistoricalRecords.remove(0);
        }
    }

    private boolean readHistoricalDataIfNeeded() throws IOException {
        if (!this.mCanReadHistoricalData || !this.mHistoricalRecordsChanged || TextUtils.isEmpty(this.mHistoryFileName)) {
            return false;
        }
        this.mCanReadHistoricalData = false;
        this.mReadShareHistoryCalled = true;
        readHistoricalDataImpl();
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0038, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void readHistoricalDataImpl() throws java.io.IOException {
        /*
            r10 = this;
            java.lang.String r0 = "Error reading historical recrod file: "
            android.content.Context r1 = r10.mContext     // Catch: java.io.FileNotFoundException -> Lc2
            java.lang.String r2 = r10.mHistoryFileName     // Catch: java.io.FileNotFoundException -> Lc2
            java.io.FileInputStream r1 = r1.openFileInput(r2)     // Catch: java.io.FileNotFoundException -> Lc2
            org.xmlpull.v1.XmlPullParser r2 = android.util.Xml.newPullParser()     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            java.lang.String r3 = "UTF-8"
            r2.setInput(r1, r3)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            r3 = 0
        L14:
            r4 = 1
            if (r3 == r4) goto L1f
            r5 = 2
            if (r3 == r5) goto L1f
            int r3 = r2.next()     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            goto L14
        L1f:
            java.lang.String r3 = "historical-records"
            java.lang.String r5 = r2.getName()     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            boolean r3 = r3.equals(r5)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            if (r3 == 0) goto L7c
            java.util.List<androidx.appcompat.widget.ActivityChooserModel$HistoricalRecord> r3 = r10.mHistoricalRecords     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            r3.clear()     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
        L30:
            int r5 = r2.next()     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            if (r5 != r4) goto L3d
            if (r1 == 0) goto Lbb
        L38:
            r1.close()     // Catch: java.io.IOException -> Lbb
            goto Lbb
        L3d:
            r6 = 3
            if (r5 == r6) goto L30
            r6 = 4
            if (r5 != r6) goto L44
            goto L30
        L44:
            java.lang.String r5 = r2.getName()     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            java.lang.String r6 = "historical-record"
            boolean r5 = r6.equals(r5)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            if (r5 == 0) goto L74
            java.lang.String r5 = "activity"
            r6 = 0
            java.lang.String r5 = r2.getAttributeValue(r6, r5)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            java.lang.String r7 = "time"
            java.lang.String r7 = r2.getAttributeValue(r6, r7)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            long r7 = java.lang.Long.parseLong(r7)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            java.lang.String r9 = "weight"
            java.lang.String r6 = r2.getAttributeValue(r6, r9)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            float r6 = java.lang.Float.parseFloat(r6)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            androidx.appcompat.widget.ActivityChooserModel$HistoricalRecord r9 = new androidx.appcompat.widget.ActivityChooserModel$HistoricalRecord     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            r9.<init>(r5, r7, r6)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            r3.add(r9)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            goto L30
        L74:
            org.xmlpull.v1.XmlPullParserException r2 = new org.xmlpull.v1.XmlPullParserException     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            java.lang.String r3 = "Share records file not well-formed."
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            throw r2     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
        L7c:
            org.xmlpull.v1.XmlPullParserException r2 = new org.xmlpull.v1.XmlPullParserException     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            java.lang.String r3 = "Share records file does not start with historical-records tag."
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
            throw r2     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L86 org.xmlpull.v1.XmlPullParserException -> La0
        L84:
            r0 = move-exception
            goto Lbc
        L86:
            r2 = move-exception
            java.lang.String r3 = androidx.appcompat.widget.ActivityChooserModel.LOG_TAG     // Catch: java.lang.Throwable -> L84
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L84
            r4.<init>()     // Catch: java.lang.Throwable -> L84
            r4.append(r0)     // Catch: java.lang.Throwable -> L84
            java.lang.String r0 = r10.mHistoryFileName     // Catch: java.lang.Throwable -> L84
            r4.append(r0)     // Catch: java.lang.Throwable -> L84
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L84
            android.util.Log.e(r3, r0, r2)     // Catch: java.lang.Throwable -> L84
            if (r1 == 0) goto Lbb
            goto L38
        La0:
            r2 = move-exception
            java.lang.String r3 = androidx.appcompat.widget.ActivityChooserModel.LOG_TAG     // Catch: java.lang.Throwable -> L84
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L84
            r4.<init>()     // Catch: java.lang.Throwable -> L84
            r4.append(r0)     // Catch: java.lang.Throwable -> L84
            java.lang.String r0 = r10.mHistoryFileName     // Catch: java.lang.Throwable -> L84
            r4.append(r0)     // Catch: java.lang.Throwable -> L84
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L84
            android.util.Log.e(r3, r0, r2)     // Catch: java.lang.Throwable -> L84
            if (r1 == 0) goto Lbb
            goto L38
        Lbb:
            return
        Lbc:
            if (r1 == 0) goto Lc1
            r1.close()     // Catch: java.io.IOException -> Lc1
        Lc1:
            throw r0
        Lc2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserModel.readHistoricalDataImpl():void");
    }

    private boolean sortActivitiesIfNeeded() {
        if (this.mActivitySorter == null || this.mIntent == null || this.mActivities.isEmpty() || this.mHistoricalRecords.isEmpty()) {
            return false;
        }
        this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
        return true;
    }

    public Intent chooseActivity(int i2) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == null) {
                return null;
            }
            ensureConsistentState();
            ActivityInfo activityInfo = this.mActivities.get(i2).resolveInfo.activityInfo;
            ComponentName componentName = new ComponentName(activityInfo.packageName, activityInfo.name);
            Intent intent = new Intent(this.mIntent);
            intent.setComponent(componentName);
            if (this.mActivityChoserModelPolicy != null) {
                if (this.mActivityChoserModelPolicy.onChooseActivity(this, new Intent(intent))) {
                    return null;
                }
            }
            addHistoricalRecord(new HistoricalRecord(componentName, System.currentTimeMillis(), 1.0f));
            return intent;
        }
    }

    public ResolveInfo getActivity(int i2) {
        ResolveInfo resolveInfo;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            resolveInfo = this.mActivities.get(i2).resolveInfo;
        }
        return resolveInfo;
    }

    public int getActivityCount() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mActivities.size();
        }
        return size;
    }

    public int getActivityIndex(ResolveInfo resolveInfo) {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            List<ActivityResolveInfo> list = this.mActivities;
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (list.get(i2).resolveInfo == resolveInfo) {
                    return i2;
                }
            }
            return -1;
        }
    }

    public ResolveInfo getDefaultActivity() {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            if (this.mActivities.isEmpty()) {
                return null;
            }
            return this.mActivities.get(0).resolveInfo;
        }
    }

    public int getHistoryMaxSize() {
        int i2;
        synchronized (this.mInstanceLock) {
            i2 = this.mHistoryMaxSize;
        }
        return i2;
    }

    public int getHistorySize() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mHistoricalRecords.size();
        }
        return size;
    }

    public Intent getIntent() {
        Intent intent;
        synchronized (this.mInstanceLock) {
            intent = this.mIntent;
        }
        return intent;
    }

    public void setActivitySorter(ActivitySorter activitySorter) {
        synchronized (this.mInstanceLock) {
            if (this.mActivitySorter == activitySorter) {
                return;
            }
            this.mActivitySorter = activitySorter;
            if (sortActivitiesIfNeeded()) {
                notifyChanged();
            }
        }
    }

    public void setDefaultActivity(int i2) {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            ActivityResolveInfo activityResolveInfo = this.mActivities.get(i2);
            ActivityResolveInfo activityResolveInfo2 = this.mActivities.get(0);
            float f2 = activityResolveInfo2 != null ? (activityResolveInfo2.weight - activityResolveInfo.weight) + 5.0f : 1.0f;
            ActivityInfo activityInfo = activityResolveInfo.resolveInfo.activityInfo;
            addHistoricalRecord(new HistoricalRecord(new ComponentName(activityInfo.packageName, activityInfo.name), System.currentTimeMillis(), f2));
        }
    }

    public void setHistoryMaxSize(int i2) {
        synchronized (this.mInstanceLock) {
            if (this.mHistoryMaxSize == i2) {
                return;
            }
            this.mHistoryMaxSize = i2;
            pruneExcessiveHistoricalRecordsIfNeeded();
            if (sortActivitiesIfNeeded()) {
                notifyChanged();
            }
        }
    }

    public void setIntent(Intent intent) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == intent) {
                return;
            }
            this.mIntent = intent;
            this.mReloadActivities = true;
            ensureConsistentState();
        }
    }

    public void setOnChooseActivityListener(OnChooseActivityListener onChooseActivityListener) {
        synchronized (this.mInstanceLock) {
            this.mActivityChoserModelPolicy = onChooseActivityListener;
        }
    }
}
