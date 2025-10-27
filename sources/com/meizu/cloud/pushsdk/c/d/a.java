package com.meizu.cloud.pushsdk.c.d;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class a implements d {

    /* renamed from: b, reason: collision with root package name */
    private SQLiteDatabase f9367b;

    /* renamed from: c, reason: collision with root package name */
    private b f9368c;

    /* renamed from: f, reason: collision with root package name */
    private int f9371f;

    /* renamed from: a, reason: collision with root package name */
    private String f9366a = a.class.getSimpleName();

    /* renamed from: d, reason: collision with root package name */
    private String[] f9369d = {"id", "eventData", "dateCreated"};

    /* renamed from: e, reason: collision with root package name */
    private long f9370e = -1;

    public a(Context context, int i2) {
        this.f9368c = b.a(context, a(context));
        b();
        this.f9371f = i2;
    }

    private String a(Context context) {
        String processName = MzSystemUtils.getProcessName(context);
        if (TextUtils.isEmpty(processName)) {
            return "PushEvents.db";
        }
        return processName + StrPool.UNDERLINE + "PushEvents.db";
    }

    public static Map<String, String> a(byte[] bArr) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            HashMap map = (HashMap) objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
            return map;
        } catch (IOException | ClassNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static byte[] a(Map<String, String> map) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(map);
            objectOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> a(int i2) {
        return a(null, "id ASC LIMIT " + i2);
    }

    public List<Map<String, Object>> a(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        if (e()) {
            Cursor cursorQuery = this.f9367b.query(com.umeng.analytics.pro.d.ar, this.f9369d, str, null, null, null, str2);
            cursorQuery.moveToFirst();
            while (!cursorQuery.isAfterLast()) {
                HashMap map = new HashMap();
                map.put("id", Long.valueOf(cursorQuery.getLong(0)));
                map.put("eventData", a(cursorQuery.getBlob(1)));
                map.put("dateCreated", cursorQuery.getString(2));
                cursorQuery.moveToNext();
                arrayList.add(map);
            }
            cursorQuery.close();
        }
        return arrayList;
    }

    @Override // com.meizu.cloud.pushsdk.c.d.d
    public void a(com.meizu.cloud.pushsdk.c.a.a aVar) throws IOException {
        b(aVar);
    }

    @Override // com.meizu.cloud.pushsdk.c.d.d
    public boolean a() {
        return e();
    }

    @Override // com.meizu.cloud.pushsdk.c.d.d
    public boolean a(long j2) {
        int iDelete;
        if (e()) {
            iDelete = this.f9367b.delete(com.umeng.analytics.pro.d.ar, "id=" + j2, null);
        } else {
            iDelete = -1;
        }
        com.meizu.cloud.pushsdk.c.f.c.b(this.f9366a, "Removed event from database: " + j2, new Object[0]);
        return iDelete == 1;
    }

    public long b(com.meizu.cloud.pushsdk.c.a.a aVar) throws IOException {
        if (e()) {
            byte[] bArrA = a((Map<String, String>) aVar.a());
            ContentValues contentValues = new ContentValues(2);
            contentValues.put("eventData", bArrA);
            this.f9370e = this.f9367b.insert(com.umeng.analytics.pro.d.ar, null, contentValues);
        }
        com.meizu.cloud.pushsdk.c.f.c.b(this.f9366a, "Added event to database: " + this.f9370e, new Object[0]);
        return this.f9370e;
    }

    public void b() {
        if (e()) {
            return;
        }
        try {
            SQLiteDatabase writableDatabase = this.f9368c.getWritableDatabase();
            this.f9367b = writableDatabase;
            writableDatabase.enableWriteAheadLogging();
        } catch (Exception e2) {
            com.meizu.cloud.pushsdk.c.f.c.a(this.f9366a, " open database error " + e2.getMessage(), new Object[0]);
        }
    }

    @Override // com.meizu.cloud.pushsdk.c.d.d
    public long c() {
        if (e()) {
            return DatabaseUtils.queryNumEntries(this.f9367b, com.umeng.analytics.pro.d.ar);
        }
        return 0L;
    }

    @Override // com.meizu.cloud.pushsdk.c.d.d
    public com.meizu.cloud.pushsdk.c.b.b d() {
        LinkedList linkedList = new LinkedList();
        ArrayList arrayList = new ArrayList();
        for (Map<String, Object> map : a(this.f9371f)) {
            com.meizu.cloud.pushsdk.c.a.c cVar = new com.meizu.cloud.pushsdk.c.a.c();
            cVar.a((Map) map.get("eventData"));
            linkedList.add((Long) map.get("id"));
            arrayList.add(cVar);
        }
        return new com.meizu.cloud.pushsdk.c.b.b(arrayList, linkedList);
    }

    public boolean e() {
        SQLiteDatabase sQLiteDatabase = this.f9367b;
        return sQLiteDatabase != null && sQLiteDatabase.isOpen();
    }
}
