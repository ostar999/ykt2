package com.psychiatrygarden.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.psychiatrygarden.db.SQLHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class ChannelDao implements ChannelDaoInface {
    private SQLHelper helper;

    public ChannelDao(Context context) {
        this.helper = null;
        this.helper = new SQLHelper(context);
    }

    private void revertSeq() {
        try {
            this.helper.getWritableDatabase().execSQL("update sqlite_sequence set seq=0 where name='channel'");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.bean.ChannelDaoInface
    public boolean addCache(ChannelItem item) throws Throwable {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            try {
                SQLiteDatabase writableDatabase = this.helper.getWritableDatabase();
                try {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", item.getName());
                    contentValues.put("id", Integer.valueOf(item.getId()));
                    contentValues.put(SQLHelper.ORDERID, Integer.valueOf(item.getOrderId()));
                    contentValues.put("sort", item.getSort());
                    contentValues.put(SQLHelper.SELECTED, item.getSelected());
                    z = writableDatabase.insert("channel", null, contentValues) != -1;
                    writableDatabase.close();
                } catch (Exception e2) {
                    e = e2;
                    sQLiteDatabase = writableDatabase;
                    e.printStackTrace();
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.close();
                    }
                    return z;
                } catch (Throwable th) {
                    th = th;
                    sQLiteDatabase = writableDatabase;
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
        }
        return z;
    }

    public long allCaseNum() {
        long j2 = 0;
        try {
            Cursor cursorRawQuery = this.helper.getWritableDatabase().rawQuery("select count(*) from channel", null);
            cursorRawQuery.moveToFirst();
            j2 = cursorRawQuery.getLong(0);
            cursorRawQuery.close();
            return j2;
        } catch (Exception e2) {
            e2.printStackTrace();
            return j2;
        }
    }

    @Override // com.psychiatrygarden.bean.ChannelDaoInface
    public void clearFeedTable() {
        try {
            this.helper.getWritableDatabase().execSQL("DELETE FROM channel");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        revertSeq();
    }

    @Override // com.psychiatrygarden.bean.ChannelDaoInface
    public boolean deleteCache(String whereClause, String[] whereArgs) {
        boolean z2 = false;
        SQLiteDatabase writableDatabase = null;
        try {
            try {
                writableDatabase = this.helper.getWritableDatabase();
                if (writableDatabase.delete("channel", whereClause, whereArgs) > 0) {
                    z2 = true;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (writableDatabase != null) {
                }
            }
            writableDatabase.close();
            return z2;
        } catch (Throwable th) {
            if (writableDatabase != null) {
                writableDatabase.close();
            }
            throw th;
        }
    }

    public ChannelItem getChannelData(int id) {
        ChannelItem channelItem = null;
        try {
            Cursor cursorQuery = this.helper.getReadableDatabase().query("channel", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursorQuery != null && cursorQuery.moveToFirst()) {
                ChannelItem channelItem2 = new ChannelItem();
                try {
                    int i2 = cursorQuery.getInt(cursorQuery.getColumnIndex("id"));
                    String string = cursorQuery.getString(cursorQuery.getColumnIndex("name"));
                    int i3 = cursorQuery.getInt(cursorQuery.getColumnIndex(SQLHelper.ORDERID));
                    int i4 = cursorQuery.getInt(cursorQuery.getColumnIndex("sort"));
                    int i5 = cursorQuery.getInt(cursorQuery.getColumnIndex(SQLHelper.SELECTED));
                    channelItem2.setId(i2);
                    channelItem2.setName(string);
                    channelItem2.setOrderId(i3);
                    channelItem2.setSort(Integer.valueOf(i4));
                    channelItem2.setSelected(Integer.valueOf(i5));
                    channelItem = channelItem2;
                } catch (Exception e2) {
                    e = e2;
                    channelItem = channelItem2;
                    e.printStackTrace();
                    return channelItem;
                }
            }
        } catch (Exception e3) {
            e = e3;
        }
        return channelItem;
    }

    @Override // com.psychiatrygarden.bean.ChannelDaoInface
    public List<Map<String, String>> listCache(String selection, String[] selectionArgs, String orderby) {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase readableDatabase = null;
        try {
            try {
                readableDatabase = this.helper.getReadableDatabase();
                Cursor cursorQuery = readableDatabase.query(false, "channel", null, selection, selectionArgs, null, null, orderby, "10000");
                int columnCount = cursorQuery.getColumnCount();
                while (cursorQuery.moveToNext()) {
                    HashMap map = new HashMap();
                    for (int i2 = 0; i2 < columnCount; i2++) {
                        String columnName = cursorQuery.getColumnName(i2);
                        String string = cursorQuery.getString(cursorQuery.getColumnIndex(columnName));
                        if (string == null) {
                            string = "";
                        }
                        map.put(columnName, string);
                    }
                    arrayList.add(map);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (readableDatabase != null) {
                }
            }
            readableDatabase.close();
            return arrayList;
        } catch (Throwable th) {
            if (readableDatabase != null) {
                readableDatabase.close();
            }
            throw th;
        }
    }

    @Override // com.psychiatrygarden.bean.ChannelDaoInface
    public boolean updateCache(ContentValues values, String whereClause, String[] whereArgs) {
        boolean z2 = false;
        SQLiteDatabase writableDatabase = null;
        try {
            try {
                writableDatabase = this.helper.getWritableDatabase();
                if (writableDatabase.update("channel", values, whereClause, whereArgs) > 0) {
                    z2 = true;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (writableDatabase != null) {
                }
            }
            writableDatabase.close();
            return z2;
        } catch (Throwable th) {
            if (writableDatabase != null) {
                writableDatabase.close();
            }
            throw th;
        }
    }

    @Override // com.psychiatrygarden.bean.ChannelDaoInface
    public Map<String, String> viewCache(String selection, String[] selectionArgs) {
        HashMap map = new HashMap();
        SQLiteDatabase readableDatabase = null;
        try {
            try {
                readableDatabase = this.helper.getReadableDatabase();
                Cursor cursorQuery = readableDatabase.query(true, "channel", null, selection, selectionArgs, null, null, null, null);
                int columnCount = cursorQuery.getColumnCount();
                while (cursorQuery.moveToNext()) {
                    for (int i2 = 0; i2 < columnCount; i2++) {
                        String columnName = cursorQuery.getColumnName(i2);
                        String string = cursorQuery.getString(cursorQuery.getColumnIndex(columnName));
                        if (string == null) {
                            string = "";
                        }
                        map.put(columnName, string);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (readableDatabase != null) {
                }
            }
            readableDatabase.close();
            return map;
        } catch (Throwable th) {
            if (readableDatabase != null) {
                readableDatabase.close();
            }
            throw th;
        }
    }
}
