package com.xiaomi.push.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.xiaomi.push.Cif;
import com.xiaomi.push.ii;
import com.xiaomi.push.ij;
import com.xiaomi.push.ik;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    private static volatile g f25682a = null;

    /* renamed from: a, reason: collision with other field name */
    private static String f1055a = "GeoFenceDao.";

    /* renamed from: a, reason: collision with other field name */
    private Context f1056a;

    private g(Context context) {
        this.f1056a = context;
    }

    private synchronized Cursor a(SQLiteDatabase sQLiteDatabase) {
        com.xiaomi.push.ao.a(false);
        try {
        } catch (Exception unused) {
            return null;
        }
        return sQLiteDatabase.query("geofence", null, null, null, null, null, null);
    }

    private synchronized Cif a(Cursor cursor) {
        try {
        } catch (IllegalArgumentException e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return null;
        }
        return Cif.valueOf(cursor.getString(cursor.getColumnIndex("coordinate_provider")));
    }

    /* renamed from: a, reason: collision with other method in class */
    private synchronized ij m737a(Cursor cursor) {
        try {
            for (ij ijVar : ij.values()) {
                if (TextUtils.equals(cursor.getString(cursor.getColumnIndex("type")), ijVar.name())) {
                    return ijVar;
                }
            }
            return null;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return null;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private synchronized ik m738a(Cursor cursor) {
        ik ikVar;
        ikVar = new ik();
        try {
            ikVar.b(Double.parseDouble(cursor.getString(cursor.getColumnIndex("center_lantitude"))));
            ikVar.a(Double.parseDouble(cursor.getString(cursor.getColumnIndex("center_longtitude"))));
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return null;
        }
        return ikVar;
    }

    public static g a(Context context) {
        if (f25682a == null) {
            synchronized (g.class) {
                if (f25682a == null) {
                    f25682a = new g(context);
                }
            }
        }
        return f25682a;
    }

    private synchronized String a(List<ik> list) {
        if (list != null) {
            if (list.size() >= 3) {
                JSONArray jSONArray = new JSONArray();
                try {
                    for (ik ikVar : list) {
                        if (ikVar != null) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("point_lantitude", ikVar.b());
                            jSONObject.put("point_longtitude", ikVar.a());
                            jSONArray.put(jSONObject);
                        }
                    }
                    return jSONArray.toString();
                } catch (JSONException e2) {
                    com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
                    return null;
                }
            }
        }
        com.xiaomi.channel.commonutils.logger.b.m117a(f1055a + " points unvalidated");
        return null;
    }

    /* renamed from: a, reason: collision with other method in class */
    private synchronized ArrayList<ik> m739a(Cursor cursor) {
        ArrayList<ik> arrayList;
        arrayList = new ArrayList<>();
        try {
            JSONArray jSONArray = new JSONArray(cursor.getString(cursor.getColumnIndex("polygon_points")));
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                ik ikVar = new ik();
                JSONObject jSONObject = (JSONObject) jSONArray.get(i2);
                ikVar.b(jSONObject.getDouble("point_lantitude"));
                ikVar.a(jSONObject.getDouble("point_longtitude"));
                arrayList.add(ikVar);
            }
        } catch (JSONException e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return null;
        }
        return arrayList;
    }

    public synchronized int a(String str) {
        com.xiaomi.push.ao.a(false);
        try {
            if (m740a(str) == null) {
                return 0;
            }
            int iDelete = h.a(this.f1056a).a().delete("geofence", "id = ?", new String[]{str});
            h.a(this.f1056a).m743a();
            return iDelete;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return 0;
        }
    }

    public synchronized int a(String str, String str2) {
        com.xiaomi.push.ao.a(false);
        try {
            if (!"Enter".equals(str2) && !"Leave".equals(str2) && !"Unknown".equals(str2)) {
                return 0;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("current_status", str2);
            int iUpdate = h.a(this.f1056a).a().update("geofence", contentValues, "id=?", new String[]{str});
            h.a(this.f1056a).m743a();
            return iUpdate;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return 0;
        }
    }

    public synchronized long a(ii iiVar) {
        long jInsert;
        com.xiaomi.push.ao.a(false);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", iiVar.m520a());
            contentValues.put("appId", Long.valueOf(iiVar.m516a()));
            contentValues.put("name", iiVar.m525b());
            contentValues.put("package_name", iiVar.c());
            contentValues.put("create_time", Long.valueOf(iiVar.b()));
            contentValues.put("type", iiVar.m518a().name());
            contentValues.put("center_longtitude", String.valueOf(iiVar.m519a().a()));
            contentValues.put("center_lantitude", String.valueOf(iiVar.m519a().b()));
            contentValues.put("circle_radius", Double.valueOf(iiVar.a()));
            contentValues.put("polygon_point", a(iiVar.m521a()));
            contentValues.put("coordinate_provider", iiVar.m517a().name());
            contentValues.put("current_status", "Unknown");
            jInsert = h.a(this.f1056a).a().insert("geofence", null, contentValues);
            h.a(this.f1056a).m743a();
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return -1L;
        }
        return jInsert;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized ii m740a(String str) {
        com.xiaomi.push.ao.a(false);
        try {
            Iterator<ii> it = a().iterator();
            while (it.hasNext()) {
                ii next = it.next();
                if (TextUtils.equals(next.m520a(), str)) {
                    return next;
                }
            }
            return null;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return null;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized String m741a(String str) {
        com.xiaomi.push.ao.a(false);
        try {
            Cursor cursorA = a(h.a(this.f1056a).a());
            if (cursorA != null) {
                while (cursorA.moveToNext()) {
                    if (TextUtils.equals(cursorA.getString(cursorA.getColumnIndex("id")), str)) {
                        String string = cursorA.getString(cursorA.getColumnIndex("current_status"));
                        com.xiaomi.channel.commonutils.logger.b.c(f1055a + "findGeoStatueByGeoId: geo current statue is " + string + " geoId:" + str);
                        cursorA.close();
                        return string;
                    }
                }
                cursorA.close();
            }
            h.a(this.f1056a).m743a();
            return "Unknown";
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return "Unknown";
        }
    }

    public synchronized ArrayList<ii> a() {
        ArrayList<ii> arrayList;
        ii iiVar;
        ij ijVarM737a;
        String str;
        com.xiaomi.push.ao.a(false);
        try {
            Cursor cursorA = a(h.a(this.f1056a).a());
            arrayList = new ArrayList<>();
            if (cursorA != null) {
                while (cursorA.moveToNext()) {
                    try {
                        iiVar = new ii();
                        iiVar.a(cursorA.getString(cursorA.getColumnIndex("id")));
                        iiVar.b(cursorA.getString(cursorA.getColumnIndex("name")));
                        iiVar.a(cursorA.getInt(cursorA.getColumnIndex("appId")));
                        iiVar.c(cursorA.getString(cursorA.getColumnIndex("package_name")));
                        iiVar.b(cursorA.getInt(cursorA.getColumnIndex("create_time")));
                        ijVarM737a = m737a(cursorA);
                    } catch (Exception e2) {
                        com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
                    }
                    if (ijVarM737a == null) {
                        str = f1055a + "findAllGeoFencing: geo type null";
                    } else {
                        iiVar.a(ijVarM737a);
                        if (TextUtils.equals("Circle", ijVarM737a.name())) {
                            iiVar.a(m738a(cursorA));
                            iiVar.a(cursorA.getDouble(cursorA.getColumnIndex("circle_radius")));
                        } else if (TextUtils.equals("Polygon", ijVarM737a.name())) {
                            ArrayList<ik> arrayListM739a = m739a(cursorA);
                            if (arrayListM739a != null && arrayListM739a.size() >= 3) {
                                iiVar.a(arrayListM739a);
                            }
                            str = f1055a + "findAllGeoFencing: geo points null or size<3";
                        }
                        Cif cifA = a(cursorA);
                        if (cifA == null) {
                            com.xiaomi.channel.commonutils.logger.b.c(f1055a + "findAllGeoFencing: geo Coordinate Provider null ");
                        } else {
                            iiVar.a(cifA);
                            arrayList.add(iiVar);
                        }
                    }
                    com.xiaomi.channel.commonutils.logger.b.c(str);
                }
                cursorA.close();
            }
            h.a(this.f1056a).m743a();
        } catch (Exception e3) {
            com.xiaomi.channel.commonutils.logger.b.d(e3.toString());
            return null;
        }
        return arrayList;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized ArrayList<ii> m742a(String str) {
        ArrayList<ii> arrayList;
        com.xiaomi.push.ao.a(false);
        try {
            ArrayList<ii> arrayListA = a();
            arrayList = new ArrayList<>();
            Iterator<ii> it = arrayListA.iterator();
            while (it.hasNext()) {
                ii next = it.next();
                if (TextUtils.equals(next.c(), str)) {
                    arrayList.add(next);
                }
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return null;
        }
        return arrayList;
    }

    public synchronized int b(String str) {
        com.xiaomi.push.ao.a(false);
        try {
            if (TextUtils.isEmpty(str)) {
                return 0;
            }
            int iDelete = h.a(this.f1056a).a().delete("geofence", "package_name = ?", new String[]{str});
            h.a(this.f1056a).m743a();
            return iDelete;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return 0;
        }
    }
}
