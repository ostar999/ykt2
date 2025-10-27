package com.blankj.utilcode.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import com.blankj.utilcode.constant.CacheConstants;
import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class CacheDiskUtils implements CacheConstants {
    private static final Map<String, CacheDiskUtils> CACHE_MAP = new HashMap();
    private static final String CACHE_PREFIX = "cdu_";
    private static final int DEFAULT_MAX_COUNT = Integer.MAX_VALUE;
    private static final long DEFAULT_MAX_SIZE = Long.MAX_VALUE;
    private static final String TYPE_BITMAP = "bi_";
    private static final String TYPE_BYTE = "by_";
    private static final String TYPE_DRAWABLE = "dr_";
    private static final String TYPE_JSON_ARRAY = "ja_";
    private static final String TYPE_JSON_OBJECT = "jo_";
    private static final String TYPE_PARCELABLE = "pa_";
    private static final String TYPE_SERIALIZABLE = "se_";
    private static final String TYPE_STRING = "st_";
    private final File mCacheDir;
    private final String mCacheKey;
    private DiskCacheManager mDiskCacheManager;
    private final int mMaxCount;
    private final long mMaxSize;

    public static final class DiskCacheHelper {
        static final int TIME_INFO_LEN = 14;

        private DiskCacheHelper() {
        }

        private static byte[] copyOfRange(byte[] bArr, int i2, int i3) {
            int i4 = i3 - i2;
            if (i4 >= 0) {
                byte[] bArr2 = new byte[i4];
                System.arraycopy(bArr, i2, bArr2, 0, Math.min(bArr.length - i2, i4));
                return bArr2;
            }
            throw new IllegalArgumentException(i2 + " > " + i3);
        }

        private static String createDueTime(int i2) {
            return String.format(Locale.getDefault(), "_$%010d$_", Long.valueOf((System.currentTimeMillis() / 1000) + i2));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static byte[] getDataWithoutDueTime(byte[] bArr) {
            return hasTimeInfo(bArr) ? copyOfRange(bArr, 14, bArr.length) : bArr;
        }

        private static long getDueTime(byte[] bArr) {
            if (hasTimeInfo(bArr)) {
                try {
                    return Long.parseLong(new String(copyOfRange(bArr, 2, 12))) * 1000;
                } catch (NumberFormatException unused) {
                }
            }
            return -1L;
        }

        private static boolean hasTimeInfo(byte[] bArr) {
            return bArr != null && bArr.length >= 14 && bArr[0] == 95 && bArr[1] == 36 && bArr[12] == 36 && bArr[13] == 95;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isDue(byte[] bArr) {
            long dueTime = getDueTime(bArr);
            return dueTime != -1 && System.currentTimeMillis() > dueTime;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static byte[] newByteArrayWithTime(int i2, byte[] bArr) {
            byte[] bytes = createDueTime(i2).getBytes();
            byte[] bArr2 = new byte[bytes.length + bArr.length];
            System.arraycopy(bytes, 0, bArr2, 0, bytes.length);
            System.arraycopy(bArr, 0, bArr2, bytes.length, bArr.length);
            return bArr2;
        }
    }

    public static final class DiskCacheManager {
        private final AtomicInteger cacheCount;
        private final File cacheDir;
        private final AtomicLong cacheSize;
        private final int countLimit;
        private final Map<File, Long> lastUsageDates;
        private final Thread mThread;
        private final long sizeLimit;

        /* JADX INFO: Access modifiers changed from: private */
        public boolean clear() {
            File[] fileArrListFiles = this.cacheDir.listFiles(new FilenameFilter() { // from class: com.blankj.utilcode.util.CacheDiskUtils.DiskCacheManager.2
                @Override // java.io.FilenameFilter
                public boolean accept(File file, String str) {
                    return str.startsWith(CacheDiskUtils.CACHE_PREFIX);
                }
            });
            boolean z2 = true;
            if (fileArrListFiles != null && fileArrListFiles.length > 0) {
                for (File file : fileArrListFiles) {
                    if (file.delete()) {
                        this.cacheSize.addAndGet(-file.length());
                        this.cacheCount.addAndGet(-1);
                        this.lastUsageDates.remove(file);
                    } else {
                        z2 = false;
                    }
                }
                if (z2) {
                    this.lastUsageDates.clear();
                    this.cacheSize.set(0L);
                    this.cacheCount.set(0);
                }
            }
            return z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getCacheCount() throws InterruptedException {
            wait2InitOk();
            return this.cacheCount.get();
        }

        private String getCacheNameByKey(String str) {
            return CacheDiskUtils.CACHE_PREFIX + str.substring(0, 3) + str.substring(3).hashCode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getCacheSize() throws InterruptedException {
            wait2InitOk();
            return this.cacheSize.get();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public File getFileBeforePut(String str) throws InterruptedException {
            wait2InitOk();
            File file = new File(this.cacheDir, getCacheNameByKey(str));
            if (file.exists()) {
                this.cacheCount.addAndGet(-1);
                this.cacheSize.addAndGet(-file.length());
            }
            return file;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public File getFileIfExists(String str) {
            File file = new File(this.cacheDir, getCacheNameByKey(str));
            if (file.exists()) {
                return file;
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void put(File file) {
            this.cacheCount.addAndGet(1);
            this.cacheSize.addAndGet(file.length());
            while (true) {
                if (this.cacheCount.get() <= this.countLimit && this.cacheSize.get() <= this.sizeLimit) {
                    return;
                }
                this.cacheSize.addAndGet(-removeOldest());
                this.cacheCount.addAndGet(-1);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean removeByKey(String str) {
            File fileIfExists = getFileIfExists(str);
            if (fileIfExists == null) {
                return true;
            }
            if (!fileIfExists.delete()) {
                return false;
            }
            this.cacheSize.addAndGet(-fileIfExists.length());
            this.cacheCount.addAndGet(-1);
            this.lastUsageDates.remove(fileIfExists);
            return true;
        }

        private long removeOldest() {
            File key;
            if (this.lastUsageDates.isEmpty()) {
                return 0L;
            }
            Long l2 = Long.MAX_VALUE;
            Set<Map.Entry<File, Long>> setEntrySet = this.lastUsageDates.entrySet();
            synchronized (this.lastUsageDates) {
                key = null;
                for (Map.Entry<File, Long> entry : setEntrySet) {
                    Long value = entry.getValue();
                    if (value.longValue() < l2.longValue()) {
                        key = entry.getKey();
                        l2 = value;
                    }
                }
            }
            if (key == null) {
                return 0L;
            }
            long length = key.length();
            if (!key.delete()) {
                return 0L;
            }
            this.lastUsageDates.remove(key);
            return length;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateModify(File file) {
            Long lValueOf = Long.valueOf(System.currentTimeMillis());
            file.setLastModified(lValueOf.longValue());
            this.lastUsageDates.put(file, lValueOf);
        }

        private void wait2InitOk() throws InterruptedException {
            try {
                this.mThread.join();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }

        private DiskCacheManager(final File file, long j2, int i2) {
            this.lastUsageDates = Collections.synchronizedMap(new HashMap());
            this.cacheDir = file;
            this.sizeLimit = j2;
            this.countLimit = i2;
            this.cacheSize = new AtomicLong();
            this.cacheCount = new AtomicInteger();
            Thread thread = new Thread(new Runnable() { // from class: com.blankj.utilcode.util.CacheDiskUtils.DiskCacheManager.1
                @Override // java.lang.Runnable
                public void run() {
                    File[] fileArrListFiles = file.listFiles(new FilenameFilter() { // from class: com.blankj.utilcode.util.CacheDiskUtils.DiskCacheManager.1.1
                        @Override // java.io.FilenameFilter
                        public boolean accept(File file2, String str) {
                            return str.startsWith(CacheDiskUtils.CACHE_PREFIX);
                        }
                    });
                    if (fileArrListFiles != null) {
                        int length = 0;
                        int i3 = 0;
                        for (File file2 : fileArrListFiles) {
                            length = (int) (length + file2.length());
                            i3++;
                            DiskCacheManager.this.lastUsageDates.put(file2, Long.valueOf(file2.lastModified()));
                        }
                        DiskCacheManager.this.cacheSize.getAndAdd(length);
                        DiskCacheManager.this.cacheCount.getAndAdd(i3);
                    }
                }
            });
            this.mThread = thread;
            thread.start();
        }
    }

    private CacheDiskUtils(String str, File file, long j2, int i2) {
        this.mCacheKey = str;
        this.mCacheDir = file;
        this.mMaxSize = j2;
        this.mMaxCount = i2;
    }

    private DiskCacheManager getDiskCacheManager() {
        if (this.mCacheDir.exists()) {
            if (this.mDiskCacheManager == null) {
                this.mDiskCacheManager = new DiskCacheManager(this.mCacheDir, this.mMaxSize, this.mMaxCount);
            }
        } else if (this.mCacheDir.mkdirs()) {
            this.mDiskCacheManager = new DiskCacheManager(this.mCacheDir, this.mMaxSize, this.mMaxCount);
        } else {
            Log.e("CacheDiskUtils", "can't make dirs in " + this.mCacheDir.getAbsolutePath());
        }
        return this.mDiskCacheManager;
    }

    public static CacheDiskUtils getInstance() {
        return getInstance("", Long.MAX_VALUE, Integer.MAX_VALUE);
    }

    private byte[] realGetBytes(@NonNull String str) {
        return realGetBytes(str, null);
    }

    private void realPutBytes(String str, byte[] bArr, int i2) throws InterruptedException {
        DiskCacheManager diskCacheManager;
        if (bArr == null || (diskCacheManager = getDiskCacheManager()) == null) {
            return;
        }
        if (i2 >= 0) {
            bArr = DiskCacheHelper.newByteArrayWithTime(i2, bArr);
        }
        File fileBeforePut = diskCacheManager.getFileBeforePut(str);
        UtilsBridge.writeFileFromBytes(fileBeforePut, bArr);
        diskCacheManager.updateModify(fileBeforePut);
        diskCacheManager.put(fileBeforePut);
    }

    public boolean clear() {
        DiskCacheManager diskCacheManager = getDiskCacheManager();
        if (diskCacheManager == null) {
            return true;
        }
        return diskCacheManager.clear();
    }

    public Bitmap getBitmap(@NonNull String str) {
        return getBitmap(str, null);
    }

    public byte[] getBytes(@NonNull String str) {
        return getBytes(str, null);
    }

    public int getCacheCount() {
        DiskCacheManager diskCacheManager = getDiskCacheManager();
        if (diskCacheManager == null) {
            return 0;
        }
        return diskCacheManager.getCacheCount();
    }

    public long getCacheSize() {
        DiskCacheManager diskCacheManager = getDiskCacheManager();
        if (diskCacheManager == null) {
            return 0L;
        }
        return diskCacheManager.getCacheSize();
    }

    public Drawable getDrawable(@NonNull String str) {
        return getDrawable(str, null);
    }

    public JSONArray getJSONArray(@NonNull String str) {
        return getJSONArray(str, null);
    }

    public JSONObject getJSONObject(@NonNull String str) {
        return getJSONObject(str, null);
    }

    public <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator) {
        return (T) getParcelable(str, creator, null);
    }

    public Object getSerializable(@NonNull String str) {
        return getSerializable(str, null);
    }

    public String getString(@NonNull String str) {
        return getString(str, null);
    }

    public void put(@NonNull String str, byte[] bArr) throws InterruptedException {
        put(str, bArr, -1);
    }

    public boolean remove(@NonNull String str) {
        DiskCacheManager diskCacheManager = getDiskCacheManager();
        if (diskCacheManager == null) {
            return true;
        }
        if (diskCacheManager.removeByKey(TYPE_BYTE + str)) {
            if (diskCacheManager.removeByKey(TYPE_STRING + str)) {
                if (diskCacheManager.removeByKey(TYPE_JSON_OBJECT + str)) {
                    if (diskCacheManager.removeByKey(TYPE_JSON_ARRAY + str)) {
                        if (diskCacheManager.removeByKey(TYPE_BITMAP + str)) {
                            if (diskCacheManager.removeByKey(TYPE_DRAWABLE + str)) {
                                if (diskCacheManager.removeByKey(TYPE_PARCELABLE + str)) {
                                    if (diskCacheManager.removeByKey(TYPE_SERIALIZABLE + str)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public String toString() {
        return this.mCacheKey + "@" + Integer.toHexString(hashCode());
    }

    public static CacheDiskUtils getInstance(String str) {
        return getInstance(str, Long.MAX_VALUE, Integer.MAX_VALUE);
    }

    private byte[] realGetBytes(@NonNull String str, byte[] bArr) {
        File fileIfExists;
        DiskCacheManager diskCacheManager = getDiskCacheManager();
        if (diskCacheManager == null || (fileIfExists = diskCacheManager.getFileIfExists(str)) == null) {
            return bArr;
        }
        byte[] file2Bytes = UtilsBridge.readFile2Bytes(fileIfExists);
        if (DiskCacheHelper.isDue(file2Bytes)) {
            diskCacheManager.removeByKey(str);
            return bArr;
        }
        diskCacheManager.updateModify(fileIfExists);
        return DiskCacheHelper.getDataWithoutDueTime(file2Bytes);
    }

    public Bitmap getBitmap(@NonNull String str, Bitmap bitmap) {
        byte[] bArrRealGetBytes = realGetBytes(TYPE_BITMAP + str);
        return bArrRealGetBytes == null ? bitmap : UtilsBridge.bytes2Bitmap(bArrRealGetBytes);
    }

    public byte[] getBytes(@NonNull String str, byte[] bArr) {
        return realGetBytes(TYPE_BYTE + str, bArr);
    }

    public Drawable getDrawable(@NonNull String str, Drawable drawable) {
        byte[] bArrRealGetBytes = realGetBytes(TYPE_DRAWABLE + str);
        return bArrRealGetBytes == null ? drawable : UtilsBridge.bytes2Drawable(bArrRealGetBytes);
    }

    public JSONArray getJSONArray(@NonNull String str, JSONArray jSONArray) {
        byte[] bArrRealGetBytes = realGetBytes(TYPE_JSON_ARRAY + str);
        return bArrRealGetBytes == null ? jSONArray : UtilsBridge.bytes2JSONArray(bArrRealGetBytes);
    }

    public JSONObject getJSONObject(@NonNull String str, JSONObject jSONObject) {
        byte[] bArrRealGetBytes = realGetBytes(TYPE_JSON_OBJECT + str);
        return bArrRealGetBytes == null ? jSONObject : UtilsBridge.bytes2JSONObject(bArrRealGetBytes);
    }

    public <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator, T t2) {
        byte[] bArrRealGetBytes = realGetBytes(TYPE_PARCELABLE + str);
        return bArrRealGetBytes == null ? t2 : (T) UtilsBridge.bytes2Parcelable(bArrRealGetBytes, creator);
    }

    public Object getSerializable(@NonNull String str, Object obj) {
        byte[] bArrRealGetBytes = realGetBytes(TYPE_SERIALIZABLE + str);
        return bArrRealGetBytes == null ? obj : UtilsBridge.bytes2Object(bArrRealGetBytes);
    }

    public String getString(@NonNull String str, String str2) {
        byte[] bArrRealGetBytes = realGetBytes(TYPE_STRING + str);
        return bArrRealGetBytes == null ? str2 : UtilsBridge.bytes2String(bArrRealGetBytes);
    }

    public void put(@NonNull String str, byte[] bArr, int i2) throws InterruptedException {
        realPutBytes(TYPE_BYTE + str, bArr, i2);
    }

    public static CacheDiskUtils getInstance(long j2, int i2) {
        return getInstance("", j2, i2);
    }

    public void put(@NonNull String str, String str2) throws InterruptedException {
        put(str, str2, -1);
    }

    public static CacheDiskUtils getInstance(String str, long j2, int i2) {
        if (UtilsBridge.isSpace(str)) {
            str = "cacheUtils";
        }
        return getInstance(new File(Utils.getApp().getCacheDir(), str), j2, i2);
    }

    public void put(@NonNull String str, String str2, int i2) throws InterruptedException {
        realPutBytes(TYPE_STRING + str, UtilsBridge.string2Bytes(str2), i2);
    }

    public void put(@NonNull String str, JSONObject jSONObject) throws InterruptedException {
        put(str, jSONObject, -1);
    }

    public void put(@NonNull String str, JSONObject jSONObject, int i2) throws InterruptedException {
        realPutBytes(TYPE_JSON_OBJECT + str, UtilsBridge.jsonObject2Bytes(jSONObject), i2);
    }

    public static CacheDiskUtils getInstance(@NonNull File file) {
        return getInstance(file, Long.MAX_VALUE, Integer.MAX_VALUE);
    }

    public void put(@NonNull String str, JSONArray jSONArray) throws InterruptedException {
        put(str, jSONArray, -1);
    }

    public static CacheDiskUtils getInstance(@NonNull File file, long j2, int i2) {
        String str = file.getAbsoluteFile() + StrPool.UNDERLINE + j2 + StrPool.UNDERLINE + i2;
        Map<String, CacheDiskUtils> map = CACHE_MAP;
        CacheDiskUtils cacheDiskUtils = map.get(str);
        if (cacheDiskUtils == null) {
            synchronized (CacheDiskUtils.class) {
                cacheDiskUtils = map.get(str);
                if (cacheDiskUtils == null) {
                    CacheDiskUtils cacheDiskUtils2 = new CacheDiskUtils(str, file, j2, i2);
                    map.put(str, cacheDiskUtils2);
                    cacheDiskUtils = cacheDiskUtils2;
                }
            }
        }
        return cacheDiskUtils;
    }

    public void put(@NonNull String str, JSONArray jSONArray, int i2) throws InterruptedException {
        realPutBytes(TYPE_JSON_ARRAY + str, UtilsBridge.jsonArray2Bytes(jSONArray), i2);
    }

    public void put(@NonNull String str, Bitmap bitmap) throws InterruptedException {
        put(str, bitmap, -1);
    }

    public void put(@NonNull String str, Bitmap bitmap, int i2) throws InterruptedException {
        realPutBytes(TYPE_BITMAP + str, UtilsBridge.bitmap2Bytes(bitmap), i2);
    }

    public void put(@NonNull String str, Drawable drawable) throws InterruptedException {
        put(str, drawable, -1);
    }

    public void put(@NonNull String str, Drawable drawable, int i2) throws InterruptedException {
        realPutBytes(TYPE_DRAWABLE + str, UtilsBridge.drawable2Bytes(drawable), i2);
    }

    public void put(@NonNull String str, Parcelable parcelable) throws InterruptedException {
        put(str, parcelable, -1);
    }

    public void put(@NonNull String str, Parcelable parcelable, int i2) throws InterruptedException {
        realPutBytes(TYPE_PARCELABLE + str, UtilsBridge.parcelable2Bytes(parcelable), i2);
    }

    public void put(@NonNull String str, Serializable serializable) throws InterruptedException {
        put(str, serializable, -1);
    }

    public void put(@NonNull String str, Serializable serializable, int i2) throws InterruptedException {
        realPutBytes(TYPE_SERIALIZABLE + str, UtilsBridge.serializable2Bytes(serializable), i2);
    }
}
