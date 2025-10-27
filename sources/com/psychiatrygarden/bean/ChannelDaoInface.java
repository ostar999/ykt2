package com.psychiatrygarden.bean;

import android.content.ContentValues;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public interface ChannelDaoInface {
    boolean addCache(ChannelItem item);

    void clearFeedTable();

    boolean deleteCache(String whereClause, String[] whereArgs);

    List<Map<String, String>> listCache(String selection, String[] selectionArgs, String orderby);

    boolean updateCache(ContentValues values, String whereClause, String[] whereArgs);

    Map<String, String> viewCache(String selection, String[] selectionArgs);
}
