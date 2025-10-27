package com.blankj.utilcode.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class CacheDoubleStaticUtils {
    private static CacheDoubleUtils sDefaultCacheDoubleUtils;

    public static void clear() {
        clear(getDefaultCacheDoubleUtils());
    }

    public static Bitmap getBitmap(@NonNull String str) {
        return getBitmap(str, getDefaultCacheDoubleUtils());
    }

    public static byte[] getBytes(@NonNull String str) {
        return getBytes(str, getDefaultCacheDoubleUtils());
    }

    public static int getCacheDiskCount() {
        return getCacheDiskCount(getDefaultCacheDoubleUtils());
    }

    public static long getCacheDiskSize() {
        return getCacheDiskSize(getDefaultCacheDoubleUtils());
    }

    public static int getCacheMemoryCount() {
        return getCacheMemoryCount(getDefaultCacheDoubleUtils());
    }

    private static CacheDoubleUtils getDefaultCacheDoubleUtils() {
        CacheDoubleUtils cacheDoubleUtils = sDefaultCacheDoubleUtils;
        return cacheDoubleUtils != null ? cacheDoubleUtils : CacheDoubleUtils.getInstance();
    }

    public static Drawable getDrawable(@NonNull String str) {
        return getDrawable(str, getDefaultCacheDoubleUtils());
    }

    public static JSONArray getJSONArray(@NonNull String str) {
        return getJSONArray(str, getDefaultCacheDoubleUtils());
    }

    public static JSONObject getJSONObject(@NonNull String str) {
        return getJSONObject(str, getDefaultCacheDoubleUtils());
    }

    public static <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator) {
        return (T) getParcelable(str, (Parcelable.Creator) creator, getDefaultCacheDoubleUtils());
    }

    public static Object getSerializable(@NonNull String str) {
        return getSerializable(str, getDefaultCacheDoubleUtils());
    }

    public static String getString(@NonNull String str) {
        return getString(str, getDefaultCacheDoubleUtils());
    }

    public static void put(@NonNull String str, byte[] bArr) throws InterruptedException {
        put(str, bArr, getDefaultCacheDoubleUtils());
    }

    public static void remove(@NonNull String str) {
        remove(str, getDefaultCacheDoubleUtils());
    }

    public static void setDefaultCacheDoubleUtils(CacheDoubleUtils cacheDoubleUtils) {
        sDefaultCacheDoubleUtils = cacheDoubleUtils;
    }

    public static void clear(@NonNull CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.clear();
    }

    public static Bitmap getBitmap(@NonNull String str, Bitmap bitmap) {
        return getBitmap(str, bitmap, getDefaultCacheDoubleUtils());
    }

    public static byte[] getBytes(@NonNull String str, byte[] bArr) {
        return getBytes(str, bArr, getDefaultCacheDoubleUtils());
    }

    public static int getCacheDiskCount(@NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getCacheDiskCount();
    }

    public static long getCacheDiskSize(@NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getCacheDiskSize();
    }

    public static int getCacheMemoryCount(@NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getCacheMemoryCount();
    }

    public static Drawable getDrawable(@NonNull String str, Drawable drawable) {
        return getDrawable(str, drawable, getDefaultCacheDoubleUtils());
    }

    public static JSONArray getJSONArray(@NonNull String str, JSONArray jSONArray) {
        return getJSONArray(str, jSONArray, getDefaultCacheDoubleUtils());
    }

    public static JSONObject getJSONObject(@NonNull String str, JSONObject jSONObject) {
        return getJSONObject(str, jSONObject, getDefaultCacheDoubleUtils());
    }

    public static <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator, T t2) {
        return (T) getParcelable(str, creator, t2, getDefaultCacheDoubleUtils());
    }

    public static Object getSerializable(@NonNull String str, Object obj) {
        return getSerializable(str, obj, getDefaultCacheDoubleUtils());
    }

    public static String getString(@NonNull String str, String str2) {
        return getString(str, str2, getDefaultCacheDoubleUtils());
    }

    public static void put(@NonNull String str, byte[] bArr, int i2) throws InterruptedException {
        put(str, bArr, i2, getDefaultCacheDoubleUtils());
    }

    public static void remove(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.remove(str);
    }

    public static Bitmap getBitmap(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getBitmap(str);
    }

    public static byte[] getBytes(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getBytes(str);
    }

    public static Drawable getDrawable(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getDrawable(str);
    }

    public static JSONArray getJSONArray(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getJSONArray(str);
    }

    public static JSONObject getJSONObject(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getJSONObject(str);
    }

    public static <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return (T) cacheDoubleUtils.getParcelable(str, creator);
    }

    public static Object getSerializable(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getSerializable(str);
    }

    public static String getString(@NonNull String str, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getString(str);
    }

    public static void put(@NonNull String str, String str2) throws InterruptedException {
        put(str, str2, getDefaultCacheDoubleUtils());
    }

    public static Bitmap getBitmap(@NonNull String str, Bitmap bitmap, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getBitmap(str, bitmap);
    }

    public static byte[] getBytes(@NonNull String str, byte[] bArr, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getBytes(str, bArr);
    }

    public static Drawable getDrawable(@NonNull String str, Drawable drawable, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getDrawable(str, drawable);
    }

    public static JSONArray getJSONArray(@NonNull String str, JSONArray jSONArray, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getJSONArray(str, jSONArray);
    }

    public static JSONObject getJSONObject(@NonNull String str, JSONObject jSONObject, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getJSONObject(str, jSONObject);
    }

    public static <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator, T t2, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return (T) cacheDoubleUtils.getParcelable(str, creator, t2);
    }

    public static Object getSerializable(@NonNull String str, Object obj, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getSerializable(str, obj);
    }

    public static String getString(@NonNull String str, String str2, @NonNull CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getString(str, str2);
    }

    public static void put(@NonNull String str, String str2, int i2) throws InterruptedException {
        put(str, str2, i2, getDefaultCacheDoubleUtils());
    }

    public static void put(@NonNull String str, JSONObject jSONObject) throws InterruptedException {
        put(str, jSONObject, getDefaultCacheDoubleUtils());
    }

    public static void put(@NonNull String str, JSONObject jSONObject, int i2) throws InterruptedException {
        put(str, jSONObject, i2, getDefaultCacheDoubleUtils());
    }

    public static void put(@NonNull String str, JSONArray jSONArray) throws InterruptedException {
        put(str, jSONArray, getDefaultCacheDoubleUtils());
    }

    public static void put(@NonNull String str, JSONArray jSONArray, int i2) throws InterruptedException {
        put(str, jSONArray, i2, getDefaultCacheDoubleUtils());
    }

    public static void put(@NonNull String str, Bitmap bitmap) throws InterruptedException {
        put(str, bitmap, getDefaultCacheDoubleUtils());
    }

    public static void put(@NonNull String str, Bitmap bitmap, int i2) throws InterruptedException {
        put(str, bitmap, i2, getDefaultCacheDoubleUtils());
    }

    public static void put(@NonNull String str, Drawable drawable) throws InterruptedException {
        put(str, drawable, getDefaultCacheDoubleUtils());
    }

    public static void put(@NonNull String str, Drawable drawable, int i2) throws InterruptedException {
        put(str, drawable, i2, getDefaultCacheDoubleUtils());
    }

    public static void put(@NonNull String str, Parcelable parcelable) throws InterruptedException {
        put(str, parcelable, getDefaultCacheDoubleUtils());
    }

    public static void put(@NonNull String str, Parcelable parcelable, int i2) throws InterruptedException {
        put(str, parcelable, i2, getDefaultCacheDoubleUtils());
    }

    public static void put(@NonNull String str, Serializable serializable) throws InterruptedException {
        put(str, serializable, getDefaultCacheDoubleUtils());
    }

    public static void put(@NonNull String str, Serializable serializable, int i2) throws InterruptedException {
        put(str, serializable, i2, getDefaultCacheDoubleUtils());
    }

    public static void put(@NonNull String str, byte[] bArr, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, bArr);
    }

    public static void put(@NonNull String str, byte[] bArr, int i2, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, bArr, i2);
    }

    public static void put(@NonNull String str, String str2, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, str2);
    }

    public static void put(@NonNull String str, String str2, int i2, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, str2, i2);
    }

    public static void put(@NonNull String str, JSONObject jSONObject, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, jSONObject);
    }

    public static void put(@NonNull String str, JSONObject jSONObject, int i2, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, jSONObject, i2);
    }

    public static void put(@NonNull String str, JSONArray jSONArray, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, jSONArray);
    }

    public static void put(@NonNull String str, JSONArray jSONArray, int i2, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, jSONArray, i2);
    }

    public static void put(@NonNull String str, Bitmap bitmap, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, bitmap);
    }

    public static void put(@NonNull String str, Bitmap bitmap, int i2, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, bitmap, i2);
    }

    public static void put(@NonNull String str, Drawable drawable, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, drawable);
    }

    public static void put(@NonNull String str, Drawable drawable, int i2, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, drawable, i2);
    }

    public static void put(@NonNull String str, Parcelable parcelable, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, parcelable);
    }

    public static void put(@NonNull String str, Parcelable parcelable, int i2, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, parcelable, i2);
    }

    public static void put(@NonNull String str, Serializable serializable, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, serializable);
    }

    public static void put(@NonNull String str, Serializable serializable, int i2, @NonNull CacheDoubleUtils cacheDoubleUtils) throws InterruptedException {
        cacheDoubleUtils.put(str, serializable, i2);
    }
}
