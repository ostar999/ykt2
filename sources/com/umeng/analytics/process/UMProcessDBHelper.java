package com.umeng.analytics.process;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.umeng.analytics.process.DBFileTraversalUtil;
import com.umeng.analytics.process.a;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.utils.FileLockCallback;
import com.umeng.commonsdk.utils.FileLockUtil;
import com.umeng.commonsdk.utils.UMUtils;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class UMProcessDBHelper {
    private static UMProcessDBHelper mInstance;
    private Context mContext;
    private FileLockUtil mFileLock = new FileLockUtil();
    private InsertEventCallback ekvCallBack = new InsertEventCallback();

    public class InsertEventCallback implements FileLockCallback {
        private InsertEventCallback() {
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(File file, int i2) {
            return false;
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(String str) {
            return false;
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(String str, Object obj) throws Throwable {
            if (TextUtils.isEmpty(str)) {
                return true;
            }
            if (str.startsWith(com.umeng.analytics.process.a.f22981c)) {
                str = str.replaceFirst(com.umeng.analytics.process.a.f22981c, "");
            }
            UMProcessDBHelper.this.insertEvents(str.replace(".db", ""), (JSONArray) obj);
            return true;
        }
    }

    public class ProcessToMainCallback implements FileLockCallback {
        private ProcessToMainCallback() {
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(File file, int i2) {
            return false;
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(String str) throws Throwable {
            if (TextUtils.isEmpty(str)) {
                return true;
            }
            if (str.startsWith(com.umeng.analytics.process.a.f22981c)) {
                str = str.replaceFirst(com.umeng.analytics.process.a.f22981c, "");
            }
            UMProcessDBHelper.this.processToMain(str.replace(".db", ""));
            return true;
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(String str, Object obj) {
            return false;
        }
    }

    public class a implements Serializable {

        /* renamed from: a, reason: collision with root package name */
        int f22970a;

        /* renamed from: b, reason: collision with root package name */
        String f22971b;

        /* renamed from: c, reason: collision with root package name */
        String f22972c;

        /* renamed from: d, reason: collision with root package name */
        String f22973d;

        /* renamed from: e, reason: collision with root package name */
        int f22974e;

        /* renamed from: f, reason: collision with root package name */
        String f22975f;

        /* renamed from: g, reason: collision with root package name */
        String f22976g;

        /* renamed from: h, reason: collision with root package name */
        String f22977h;

        private a() {
        }
    }

    private UMProcessDBHelper() {
    }

    private List<a> datasAdapter(String str, JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(str)) {
            return arrayList;
        }
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                a aVar = new a();
                aVar.f22972c = jSONObject.optString("id");
                aVar.f22976g = UMUtils.getAppVersionName(this.mContext);
                aVar.f22977h = UMUtils.getAppVersionCode(this.mContext);
                aVar.f22971b = jSONObject.optString("__i");
                aVar.f22974e = jSONObject.optInt("__t");
                aVar.f22975f = str;
                if (jSONObject.has("ds")) {
                    jSONObject.remove("ds");
                }
                jSONObject.put("ds", getDataSource());
                jSONObject.remove("__i");
                jSONObject.remove("__t");
                aVar.f22973d = com.umeng.common.a.a().a(jSONObject.toString());
                jSONObject.remove("ds");
                arrayList.add(aVar);
            } catch (Exception unused) {
            }
        }
        return arrayList;
    }

    private boolean dbIsExists(String str) {
        try {
            return new File(b.b(this.mContext, str)).exists();
        } catch (Throwable unused) {
            return false;
        }
    }

    private int getDataSource() {
        return 0;
    }

    public static UMProcessDBHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (UMProcessDBHelper.class) {
                if (mInstance == null) {
                    mInstance = new UMProcessDBHelper(context);
                }
            }
        }
        UMProcessDBHelper uMProcessDBHelper = mInstance;
        uMProcessDBHelper.mContext = context;
        return uMProcessDBHelper;
    }

    private boolean insertEvents_(String str, List<a> list) throws Throwable {
        SQLiteDatabase sQLiteDatabaseA;
        if (TextUtils.isEmpty(str) || list == null || list.isEmpty()) {
            return true;
        }
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabaseA = c.a(this.mContext).a(str);
        } catch (Exception unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            try {
                sQLiteDatabaseA.beginTransaction();
                for (a aVar : list) {
                    try {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("__i", aVar.f22971b);
                        contentValues.put("__e", aVar.f22972c);
                        contentValues.put("__t", Integer.valueOf(aVar.f22974e));
                        contentValues.put(a.InterfaceC0380a.f22992f, aVar.f22975f);
                        contentValues.put("__av", aVar.f22976g);
                        contentValues.put("__vc", aVar.f22977h);
                        contentValues.put("__s", aVar.f22973d);
                        sQLiteDatabaseA.insert(a.InterfaceC0380a.f22987a, null, contentValues);
                    } catch (Exception unused2) {
                    }
                }
                sQLiteDatabaseA.setTransactionSuccessful();
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused3) {
                }
                c.a(this.mContext).b(str);
                return true;
            } catch (Throwable th2) {
                th = th2;
                sQLiteDatabase = sQLiteDatabaseA;
                if (sQLiteDatabase != null) {
                    try {
                        sQLiteDatabase.endTransaction();
                    } catch (Throwable unused4) {
                    }
                }
                c.a(this.mContext).b(str);
                throw th;
            }
        } catch (Exception unused5) {
            sQLiteDatabase = sQLiteDatabaseA;
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.endTransaction();
                } catch (Throwable unused6) {
                }
            }
            c.a(this.mContext).b(str);
            return false;
        }
    }

    private boolean processIsService(Context context) {
        return context.getPackageManager().getServiceInfo(new ComponentName(context, this.mContext.getClass()), 0) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processToMain(String str) throws Throwable {
        if (dbIsExists(str)) {
            List<a> eventByProcess = readEventByProcess(str);
            if (!eventByProcess.isEmpty() && insertEvents_(com.umeng.analytics.process.a.f22986h, eventByProcess)) {
                deleteEventDatas(str, null, eventByProcess);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00c2 A[Catch: Exception -> 0x00c5, TRY_LEAVE, TryCatch #6 {Exception -> 0x00c5, blocks: (B:33:0x00bd, B:35:0x00c2), top: B:45:0x00bd }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00bd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.umeng.analytics.process.UMProcessDBHelper$1] */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.List<com.umeng.analytics.process.UMProcessDBHelper.a> readEventByProcess(java.lang.String r13) throws java.lang.Throwable {
        /*
            r12 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            android.content.Context r2 = r12.mContext     // Catch: java.lang.Throwable -> L9b java.lang.Exception -> L9e
            com.umeng.analytics.process.c r2 = com.umeng.analytics.process.c.a(r2)     // Catch: java.lang.Throwable -> L9b java.lang.Exception -> L9e
            android.database.sqlite.SQLiteDatabase r2 = r2.a(r13)     // Catch: java.lang.Throwable -> L9b java.lang.Exception -> L9e
            r2.beginTransaction()     // Catch: java.lang.Throwable -> L94 java.lang.Exception -> L96
            java.lang.String r4 = "__et_p"
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r3 = r2
            android.database.Cursor r3 = r3.query(r4, r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L94 java.lang.Exception -> L96
            if (r3 == 0) goto L8e
        L22:
            boolean r4 = r3.moveToNext()     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            if (r4 == 0) goto L8e
            com.umeng.analytics.process.UMProcessDBHelper$a r4 = new com.umeng.analytics.process.UMProcessDBHelper$a     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            r4.<init>()     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            r5 = 0
            int r5 = r3.getInt(r5)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            r4.f22970a = r5     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            java.lang.String r5 = "__i"
            int r5 = r3.getColumnIndex(r5)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            r4.f22971b = r5     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            java.lang.String r5 = "__e"
            int r5 = r3.getColumnIndex(r5)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            r4.f22972c = r5     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            java.lang.String r5 = "__s"
            int r5 = r3.getColumnIndex(r5)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            r4.f22973d = r5     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            java.lang.String r5 = "__t"
            int r5 = r3.getColumnIndex(r5)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            int r5 = r3.getInt(r5)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            r4.f22974e = r5     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            java.lang.String r5 = "__pn"
            int r5 = r3.getColumnIndex(r5)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            r4.f22975f = r5     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            java.lang.String r5 = "__av"
            int r5 = r3.getColumnIndex(r5)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            r4.f22976g = r5     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            java.lang.String r5 = "__vc"
            int r5 = r3.getColumnIndex(r5)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            r4.f22977h = r5     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            r0.add(r4)     // Catch: java.lang.Exception -> L8c java.lang.Throwable -> Lb9
            goto L22
        L8c:
            r1 = move-exception
            goto La2
        L8e:
            if (r3 == 0) goto Lac
            r3.close()     // Catch: java.lang.Exception -> Laf
            goto Lac
        L94:
            r0 = move-exception
            goto Lbb
        L96:
            r3 = move-exception
            r11 = r3
            r3 = r1
            r1 = r11
            goto La2
        L9b:
            r0 = move-exception
            r2 = r1
            goto Lbb
        L9e:
            r2 = move-exception
            r3 = r1
            r1 = r2
            r2 = r3
        La2:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> Lb9
            if (r3 == 0) goto Laa
            r3.close()     // Catch: java.lang.Exception -> Laf
        Laa:
            if (r2 == 0) goto Laf
        Lac:
            r2.endTransaction()     // Catch: java.lang.Exception -> Laf
        Laf:
            android.content.Context r1 = r12.mContext
            com.umeng.analytics.process.c r1 = com.umeng.analytics.process.c.a(r1)
            r1.b(r13)
            return r0
        Lb9:
            r0 = move-exception
            r1 = r3
        Lbb:
            if (r1 == 0) goto Lc0
            r1.close()     // Catch: java.lang.Exception -> Lc5
        Lc0:
            if (r2 == 0) goto Lc5
            r2.endTransaction()     // Catch: java.lang.Exception -> Lc5
        Lc5:
            android.content.Context r1 = r12.mContext
            com.umeng.analytics.process.c r1 = com.umeng.analytics.process.c.a(r1)
            r1.b(r13)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.process.UMProcessDBHelper.readEventByProcess(java.lang.String):java.util.List");
    }

    public void createDBByProcess(String str) {
        try {
            c.a(this.mContext).a(str);
            c.a(this.mContext).b(str);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void deleteEventDatas(String str, String str2, List<a> list) throws Throwable {
        SQLiteDatabase sQLiteDatabaseA;
        Throwable th;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabaseA = c.a(this.mContext).a(str);
            try {
                sQLiteDatabaseA.beginTransaction();
                int size = list.size();
                if (size > 0) {
                    for (int i2 = 0; i2 < size; i2++) {
                        sQLiteDatabaseA.execSQL("delete from __et_p where rowid=" + list.get(i2).f22970a);
                    }
                } else {
                    sQLiteDatabaseA.delete(a.InterfaceC0380a.f22987a, null, null);
                }
                sQLiteDatabaseA.setTransactionSuccessful();
                sQLiteDatabaseA.endTransaction();
            } catch (Exception unused) {
                sQLiteDatabase = sQLiteDatabaseA;
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.endTransaction();
                }
                c.a(this.mContext).b(str);
            } catch (Throwable th2) {
                th = th2;
                if (sQLiteDatabaseA != null) {
                    sQLiteDatabaseA.endTransaction();
                }
                c.a(this.mContext).b(str);
                throw th;
            }
        } catch (Exception unused2) {
        } catch (Throwable th3) {
            sQLiteDatabaseA = null;
            th = th3;
        }
        c.a(this.mContext).b(str);
    }

    public void deleteMainProcessEventDatasByIds(List<Integer> list) {
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            sQLiteDatabaseA = c.a(this.mContext).a(com.umeng.analytics.process.a.f22986h);
            sQLiteDatabaseA.beginTransaction();
            Iterator<Integer> it = list.iterator();
            while (it.hasNext()) {
                sQLiteDatabaseA.delete(a.InterfaceC0380a.f22987a, "id=?", new String[]{String.valueOf(it.next())});
            }
            sQLiteDatabaseA.setTransactionSuccessful();
        } catch (Exception unused) {
            if (sQLiteDatabaseA != null) {
            }
        } catch (Throwable th) {
            if (sQLiteDatabaseA != null) {
                sQLiteDatabaseA.endTransaction();
            }
            c.a(this.mContext).b(com.umeng.analytics.process.a.f22986h);
            throw th;
        }
        sQLiteDatabaseA.endTransaction();
        c.a(this.mContext).b(com.umeng.analytics.process.a.f22986h);
    }

    public void insertEvents(String str, JSONArray jSONArray) throws Throwable {
        if (AnalyticsConstants.SUB_PROCESS_EVENT && !TextUtils.isEmpty(str)) {
            insertEvents_(str, datasAdapter(str, jSONArray));
        }
    }

    public void insertEventsInSubProcess(String str, JSONArray jSONArray) throws Throwable {
        if (AnalyticsConstants.SUB_PROCESS_EVENT && !TextUtils.isEmpty(str)) {
            File file = new File(b.b(this.mContext, str));
            if (file.exists()) {
                this.mFileLock.doFileOperateion(file, this.ekvCallBack, jSONArray);
            } else {
                insertEvents(str, jSONArray);
            }
        }
    }

    public void processDBToMain() {
        try {
            DBFileTraversalUtil.traverseDBFiles(b.a(this.mContext), new ProcessToMainCallback(), new DBFileTraversalUtil.a() { // from class: com.umeng.analytics.process.UMProcessDBHelper.1
                @Override // com.umeng.analytics.process.DBFileTraversalUtil.a
                public void a() {
                    if (AnalyticsConstants.SUB_PROCESS_EVENT) {
                        UMWorkDispatch.sendEvent(UMProcessDBHelper.this.mContext, UMProcessDBDatasSender.UM_PROCESS_CONSTRUCTMESSAGE, UMProcessDBDatasSender.getInstance(UMProcessDBHelper.this.mContext), null);
                    }
                }
            });
        } catch (Exception unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:68:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0188 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.json.JSONObject readMainEvents(long r20, java.util.List<java.lang.Integer> r22) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 405
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.process.UMProcessDBHelper.readMainEvents(long, java.util.List):org.json.JSONObject");
    }

    public JSONObject readVersionInfoFromColumId(Integer num) throws Throwable {
        SQLiteDatabase sQLiteDatabaseA;
        JSONObject jSONObject;
        Cursor cursor = null;
        jSONObject = null;
        JSONObject jSONObject2 = null;
        cursor = null;
        cursor = null;
        cursor = null;
        try {
            sQLiteDatabaseA = c.a(this.mContext).a(com.umeng.analytics.process.a.f22986h);
            try {
                try {
                    sQLiteDatabaseA.beginTransaction();
                    Cursor cursorQuery = sQLiteDatabaseA.query(a.InterfaceC0380a.f22987a, null, "rowid=?", new String[]{String.valueOf(num)}, null, null, null);
                    if (cursorQuery != null) {
                        try {
                            try {
                                if (cursorQuery.moveToNext()) {
                                    jSONObject = new JSONObject();
                                    try {
                                        String string = cursorQuery.getString(cursorQuery.getColumnIndex("__av"));
                                        String string2 = cursorQuery.getString(cursorQuery.getColumnIndex("__vc"));
                                        if (!TextUtils.isEmpty(string)) {
                                            jSONObject.put("__av", string);
                                        }
                                        if (!TextUtils.isEmpty(string2)) {
                                            jSONObject.put("__vc", string2);
                                        }
                                        jSONObject2 = jSONObject;
                                    } catch (Exception e2) {
                                        e = e2;
                                        cursor = cursorQuery;
                                        e.printStackTrace();
                                        if (cursor != null) {
                                            try {
                                                cursor.close();
                                            } catch (Exception unused) {
                                                c.a(this.mContext).b(com.umeng.analytics.process.a.f22986h);
                                                return jSONObject;
                                            }
                                        }
                                        if (sQLiteDatabaseA != null) {
                                            sQLiteDatabaseA.endTransaction();
                                        }
                                        c.a(this.mContext).b(com.umeng.analytics.process.a.f22986h);
                                        return jSONObject;
                                    }
                                }
                            } catch (Throwable th) {
                                th = th;
                                cursor = cursorQuery;
                                if (cursor != null) {
                                    try {
                                        cursor.close();
                                    } catch (Exception unused2) {
                                        c.a(this.mContext).b(com.umeng.analytics.process.a.f22986h);
                                        throw th;
                                    }
                                }
                                if (sQLiteDatabaseA != null) {
                                    sQLiteDatabaseA.endTransaction();
                                }
                                c.a(this.mContext).b(com.umeng.analytics.process.a.f22986h);
                                throw th;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            jSONObject = null;
                        }
                    }
                    if (cursorQuery != null) {
                        try {
                            cursorQuery.close();
                        } catch (Exception unused3) {
                        }
                    }
                    sQLiteDatabaseA.endTransaction();
                    c.a(this.mContext).b(com.umeng.analytics.process.a.f22986h);
                    return jSONObject2;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e4) {
                e = e4;
                jSONObject = null;
            }
        } catch (Exception e5) {
            e = e5;
            sQLiteDatabaseA = null;
            jSONObject = null;
        } catch (Throwable th3) {
            th = th3;
            sQLiteDatabaseA = null;
        }
    }

    private UMProcessDBHelper(Context context) {
        com.umeng.common.a.a().a(context);
    }
}
