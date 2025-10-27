package com.umeng.socialize.net.dplus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.alipay.sdk.util.h;
import com.umeng.socialize.utils.ContextUtil;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class DBManager {

    /* renamed from: a, reason: collision with root package name */
    private static DBManager f23798a;

    /* renamed from: b, reason: collision with root package name */
    private static StandardDBHelper f23799b;

    private DBManager() {
        f23799b = new StandardDBHelper(ContextUtil.getContext());
    }

    public static synchronized DBManager get(Context context) {
        if (f23798a == null) {
            f23798a = new DBManager();
        }
        return f23798a;
    }

    public synchronized void closeDatabase() {
        f23799b.close();
    }

    public synchronized void delete(ArrayList<Integer> arrayList, String str) {
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = f23799b.getWritableDatabase();
            writableDatabase.beginTransaction();
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                writableDatabase.execSQL("delete from " + str + " where Id='" + arrayList.get(i2) + "' ");
            }
            writableDatabase.setTransactionSuccessful();
        } catch (Throwable unused) {
            if (writableDatabase != null) {
            }
        }
        try {
            writableDatabase.endTransaction();
        } catch (Throwable unused2) {
        }
    }

    public synchronized void deleteTable(String str) {
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = f23799b.getWritableDatabase();
            writableDatabase.beginTransaction();
            writableDatabase.execSQL("DELETE FROM " + str + h.f3376b);
            writableDatabase.setTransactionSuccessful();
        } catch (Throwable unused) {
            if (writableDatabase != null) {
            }
        }
        try {
            writableDatabase.endTransaction();
        } catch (Throwable unused2) {
        }
    }

    public synchronized void insertAuth(JSONObject jSONObject) {
        SQLiteDatabase writableDatabase;
        if (jSONObject == null) {
            return;
        }
        SQLiteDatabase sQLiteDatabase = null;
        try {
            try {
                writableDatabase = f23799b.getWritableDatabase();
            } catch (Throwable unused) {
            }
        } catch (Throwable unused2) {
        }
        try {
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConfig.VALUE, jSONObject.toString());
            writableDatabase.insert("auth", null, contentValues);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        } catch (Throwable unused3) {
            sQLiteDatabase = writableDatabase;
            if (sQLiteDatabase != null) {
                sQLiteDatabase.endTransaction();
            }
        }
    }

    public synchronized void insertDau(JSONObject jSONObject) {
        SQLiteDatabase writableDatabase;
        if (jSONObject == null) {
            return;
        }
        SQLiteDatabase sQLiteDatabase = null;
        try {
            try {
                writableDatabase = f23799b.getWritableDatabase();
            } catch (Throwable unused) {
            }
        } catch (Throwable unused2) {
        }
        try {
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConfig.VALUE, jSONObject.toString());
            writableDatabase.insert("dau", null, contentValues);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        } catch (Throwable unused3) {
            sQLiteDatabase = writableDatabase;
            if (sQLiteDatabase != null) {
                sQLiteDatabase.endTransaction();
            }
        }
    }

    public synchronized void insertS_E(JSONObject jSONObject) {
        SQLiteDatabase writableDatabase;
        if (jSONObject == null) {
            return;
        }
        SQLiteDatabase sQLiteDatabase = null;
        try {
            try {
                writableDatabase = f23799b.getWritableDatabase();
            } catch (Throwable unused) {
            }
            try {
                writableDatabase.beginTransaction();
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBConfig.VALUE, jSONObject.toString());
                writableDatabase.insert("s_e", null, contentValues);
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
            } catch (Throwable unused2) {
                sQLiteDatabase = writableDatabase;
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.endTransaction();
                }
            }
        } catch (Throwable unused3) {
        }
    }

    public synchronized void insertStats(JSONObject jSONObject) {
        SQLiteDatabase writableDatabase;
        if (jSONObject == null) {
            return;
        }
        SQLiteDatabase sQLiteDatabase = null;
        try {
            try {
                writableDatabase = f23799b.getWritableDatabase();
            } catch (Throwable unused) {
            }
            try {
                writableDatabase.beginTransaction();
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBConfig.VALUE, jSONObject.toString());
                writableDatabase.insert("stats", null, contentValues);
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
            } catch (Throwable unused2) {
                sQLiteDatabase = writableDatabase;
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.endTransaction();
                }
            }
        } catch (Throwable unused3) {
        }
    }

    public synchronized void insertUserInfo(JSONObject jSONObject) {
        SQLiteDatabase writableDatabase;
        if (jSONObject == null) {
            return;
        }
        SQLiteDatabase sQLiteDatabase = null;
        try {
            try {
                writableDatabase = f23799b.getWritableDatabase();
            } catch (Throwable unused) {
            }
            try {
                writableDatabase.beginTransaction();
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBConfig.VALUE, jSONObject.toString());
                writableDatabase.insert("userinfo", null, contentValues);
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
            } catch (Throwable unused2) {
                sQLiteDatabase = writableDatabase;
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.endTransaction();
                }
            }
        } catch (Throwable unused3) {
        }
    }

    public synchronized JSONArray select(String str, ArrayList<Integer> arrayList, double d3, boolean z2) throws JSONException {
        JSONArray jSONArray;
        SQLiteDatabase writableDatabase;
        jSONArray = new JSONArray();
        Cursor cursorQuery = null;
        try {
            try {
                writableDatabase = f23799b.getWritableDatabase();
                try {
                    writableDatabase.beginTransaction();
                    cursorQuery = writableDatabase.query(str, null, null, null, null, null, null);
                    while (cursorQuery.moveToNext()) {
                        int i2 = cursorQuery.getInt(0);
                        String string = cursorQuery.getString(1);
                        if (z2 && jSONArray.toString().getBytes().length + string.getBytes().length > d3) {
                            break;
                        }
                        jSONArray.put(new JSONObject(string));
                        if (!arrayList.contains(Integer.valueOf(i2))) {
                            arrayList.add(Integer.valueOf(i2));
                        }
                    }
                    writableDatabase.setTransactionSuccessful();
                    cursorQuery.close();
                } catch (JSONException e2) {
                    e = e2;
                    try {
                        throw e;
                    } catch (Throwable th) {
                        if (cursorQuery != null) {
                            try {
                                cursorQuery.close();
                            } catch (Throwable unused) {
                                throw th;
                            }
                        }
                        if (writableDatabase == null) {
                            throw th;
                        }
                        writableDatabase.endTransaction();
                        throw th;
                    }
                } catch (Throwable unused2) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (writableDatabase != null) {
                        writableDatabase.endTransaction();
                    }
                    return jSONArray;
                }
            } catch (JSONException e3) {
                e = e3;
                writableDatabase = null;
            } catch (Throwable unused3) {
                writableDatabase = null;
            }
            writableDatabase.endTransaction();
        } catch (Throwable unused4) {
        }
        return jSONArray;
    }
}
