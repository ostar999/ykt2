package com.psychiatrygarden.activity.courselist.roomDB;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.courselist.bean.CourseCalalogueBean;
import com.psychiatrygarden.activity.courselist.bean.CourseListChapterBean;
import java.util.ArrayList;
import java.util.Date;

/* loaded from: classes5.dex */
public class TypeConverters {
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        return Long.valueOf(date.getTime());
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static String fromCourseCalalogueArrayList(ArrayList<CourseCalalogueBean.DataNewBean.DataBean.CourseListBean> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static ArrayList<CourseCalalogueBean.DataNewBean.DataBean.CourseListBean> fromCourseCalalogueString(String value) {
        return (ArrayList) new Gson().fromJson(value, new TypeToken<ArrayList<CourseCalalogueBean.DataNewBean.DataBean.CourseListBean>>() { // from class: com.psychiatrygarden.activity.courselist.roomDB.TypeConverters.3
        }.getType());
    }

    @TypeConverter
    public static String fromMediaArrayList(ArrayList<CourseListChapterBean.DataBean.ChildrenBean> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static ArrayList<CourseListChapterBean.DataBean.ChildrenBean> fromMediaString(String value) {
        return (ArrayList) new Gson().fromJson(value, new TypeToken<ArrayList<CourseListChapterBean.DataBean.ChildrenBean>>() { // from class: com.psychiatrygarden.activity.courselist.roomDB.TypeConverters.2
        }.getType());
    }

    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        return (ArrayList) new Gson().fromJson(value, new TypeToken<ArrayList<String>>() { // from class: com.psychiatrygarden.activity.courselist.roomDB.TypeConverters.1
        }.getType());
    }

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        if (value == null) {
            return null;
        }
        return new Date(value.longValue());
    }
}
