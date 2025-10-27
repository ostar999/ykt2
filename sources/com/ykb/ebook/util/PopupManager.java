package com.ykb.ebook.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArrayMap;
import androidx.annotation.NonNull;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes7.dex */
public class PopupManager {
    private static final int DAY_MAX_SHOW_COUNT = 3;
    public static final String FLAG_ENTER_BOOK_HOME = "ENTER_BOOK_HOME";
    public static final String FLAG_ENTER_BOOK_READ = "ENTER_BOOK_READ";
    public static final String FLAG_ENTER_COURSE_DETAIL = "ENTER_COURSE_DETAIL";
    public static final String FLAG_ENTER_GOODS_DETAIL = "ENTER_GOODS_DETAIL";
    public static final String FLAG_ENTER_INFO_HOME = "ENTER_INFO_HOME";
    public static final String FLAG_ENTER_INFO_NO_PERMISSION_VIEW_DETAIL = "ENTER_INFO_NO_PERMISSION_VIEW_DETAIL";
    public static final String FLAG_ENTER_MEMBER_CENTER = "ENTER_MEMBER_CENTER";
    public static final String FLAG_ENTER_PAGE_COURSE_HOME = "ENTER_PAGE_COURSE_HOME";
    public static final String FLAG_ENTER_SHOP_HOME = "ENTER_SHOP_HOME";
    private static final String PREFS_NAME = "popup_show_manager_prefs";
    private static PopupManager instance;
    private final Map<String, PagePopupRecord> pageRecords = new ArrayMap();
    private final SharedPreferences sharedPreferences;

    public static class PagePopupRecord {
        private long todayDateMillis = getTodayDateMillis();
        private int todayShowCount = 0;
        private long lastShowTime = 0;

        private long getTodayDateMillisForTime(long j2) {
            return j2 - (j2 % 86400000);
        }

        public long getLastShowTime() {
            return this.lastShowTime;
        }

        public long getTodayDateMillis() {
            return this.todayDateMillis;
        }

        public int getTodayShowCount(long j2) {
            if (getTodayDateMillis() != getTodayDateMillisForTime(j2)) {
                this.todayDateMillis = getTodayDateMillisForTime(j2);
                this.todayShowCount = 0;
            }
            return this.todayShowCount;
        }

        public void recordShow(long j2) {
            this.todayShowCount++;
            this.lastShowTime = j2;
        }

        public void setLastShowTime(long j2) {
            this.lastShowTime = j2;
        }

        public void setTodayDateMillis(long j2) {
            this.todayDateMillis = j2;
        }

        public void setTodayShowCount(int i2) {
            this.todayShowCount = i2;
        }

        @NonNull
        public String toString() {
            return this.todayDateMillis + "," + this.todayShowCount + "," + this.lastShowTime;
        }
    }

    private PopupManager(Context context) throws NumberFormatException {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        loadLocalSaveData();
    }

    private void loadLocalSaveData() throws NumberFormatException {
        Iterator<Map.Entry<String, ?>> it = this.sharedPreferences.getAll().entrySet().iterator();
        while (it.hasNext()) {
            String key = it.next().getKey();
            String[] strArrSplit = this.sharedPreferences.getString(key, "").split(",");
            if (strArrSplit != null && strArrSplit.length == 3) {
                long j2 = Long.parseLong(strArrSplit[0]);
                int i2 = Integer.parseInt(strArrSplit[1]);
                long j3 = Long.parseLong(strArrSplit[2]);
                PagePopupRecord pagePopupRecord = new PagePopupRecord();
                pagePopupRecord.setTodayDateMillis(j2);
                pagePopupRecord.setTodayShowCount(i2);
                pagePopupRecord.setLastShowTime(j3);
                this.pageRecords.put(key, pagePopupRecord);
            }
        }
    }

    private void saveToSharedPreferences(String str, PagePopupRecord pagePopupRecord) {
        SharedPreferences.Editor editorEdit = this.sharedPreferences.edit();
        editorEdit.putString(str, pagePopupRecord.toString());
        editorEdit.apply();
    }

    public boolean canShowPopup(String str, long j2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        PagePopupRecord pagePopupRecord = this.pageRecords.get(str);
        if (pagePopupRecord == null) {
            pagePopupRecord = new PagePopupRecord();
        }
        this.pageRecords.put(str, pagePopupRecord);
        if (pagePopupRecord.getTodayShowCount(jCurrentTimeMillis) >= 3) {
            saveToSharedPreferences(str, pagePopupRecord);
            return false;
        }
        if (jCurrentTimeMillis - pagePopupRecord.getLastShowTime() < j2) {
            saveToSharedPreferences(str, pagePopupRecord);
            return false;
        }
        pagePopupRecord.recordShow(jCurrentTimeMillis);
        saveToSharedPreferences(str, pagePopupRecord);
        return true;
    }

    public synchronized PopupManager getInstance(Context context) {
        if (instance == null) {
            instance = new PopupManager(context.getApplicationContext());
        }
        return instance;
    }
}
