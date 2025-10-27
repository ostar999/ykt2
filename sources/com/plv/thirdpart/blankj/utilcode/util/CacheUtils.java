package com.plv.thirdpart.blankj.utilcode.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import androidx.annotation.NonNull;
import androidx.collection.SimpleArrayMap;
import cn.hutool.core.text.StrPool;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public final class CacheUtils {
    private static final SimpleArrayMap<String, CacheUtils> CACHE_MAP = new SimpleArrayMap<>();
    public static final int DAY = 86400;
    private static final int DEFAULT_MAX_COUNT = Integer.MAX_VALUE;
    private static final long DEFAULT_MAX_SIZE = Long.MAX_VALUE;
    public static final int HOUR = 3600;
    public static final int MIN = 60;
    public static final int SEC = 1;
    private CacheManager mCacheManager;

    public static class CacheHelper {
        static final int timeInfoLen = 14;

        private CacheHelper() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static byte[] bitmap2Bytes(Bitmap bitmap) {
            if (bitmap == null) {
                return null;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }

        private static Drawable bitmap2Drawable(Bitmap bitmap) {
            if (bitmap == null) {
                return null;
            }
            return new BitmapDrawable(Utils.getApp().getResources(), bitmap);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Bitmap bytes2Bitmap(byte[] bArr) {
            if (bArr == null || bArr.length == 0) {
                return null;
            }
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Drawable bytes2Drawable(byte[] bArr) {
            if (bArr == null) {
                return null;
            }
            return bitmap2Drawable(bytes2Bitmap(bArr));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static JSONArray bytes2JSONArray(byte[] bArr) {
            if (bArr == null) {
                return null;
            }
            try {
                return new JSONArray(new String(bArr));
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static JSONObject bytes2JSONObject(byte[] bArr) {
            if (bArr == null) {
                return null;
            }
            try {
                return new JSONObject(new String(bArr));
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Object bytes2Object(byte[] bArr) throws Throwable {
            ObjectInputStream objectInputStream;
            ObjectInputStream objectInputStream2 = null;
            if (bArr == null) {
                return null;
            }
            try {
                objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bArr));
            } catch (Exception e2) {
                e = e2;
                objectInputStream = null;
            } catch (Throwable th) {
                th = th;
                CloseUtils.closeIO(objectInputStream2);
                throw th;
            }
            try {
                try {
                    Object object = objectInputStream.readObject();
                    CloseUtils.closeIO(objectInputStream);
                    return object;
                } catch (Throwable th2) {
                    th = th2;
                    objectInputStream2 = objectInputStream;
                    CloseUtils.closeIO(objectInputStream2);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                CloseUtils.closeIO(objectInputStream);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T> T bytes2Parcelable(byte[] bArr, Parcelable.Creator<T> creator) {
            if (bArr == null) {
                return null;
            }
            Parcel parcelObtain = Parcel.obtain();
            parcelObtain.unmarshall(bArr, 0, bArr.length);
            parcelObtain.setDataPosition(0);
            T tCreateFromParcel = creator.createFromParcel(parcelObtain);
            parcelObtain.recycle();
            return tCreateFromParcel;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String bytes2String(byte[] bArr) {
            if (bArr == null) {
                return null;
            }
            return new String(bArr);
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

        private static Bitmap drawable2Bitmap(Drawable drawable) {
            Bitmap bitmapCreateBitmap;
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if (bitmapDrawable.getBitmap() != null) {
                    return bitmapDrawable.getBitmap();
                }
            }
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmapCreateBitmap = Bitmap.createBitmap(1, 1, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            } else {
                bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmapCreateBitmap;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static byte[] drawable2Bytes(Drawable drawable) {
            if (drawable == null) {
                return null;
            }
            return bitmap2Bytes(drawable2Bitmap(drawable));
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
        public static byte[] jsonArray2Bytes(JSONArray jSONArray) {
            if (jSONArray == null) {
                return null;
            }
            return jSONArray.toString().getBytes();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static byte[] jsonObject2Bytes(JSONObject jSONObject) {
            if (jSONObject == null) {
                return null;
            }
            return jSONObject.toString().getBytes();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static byte[] newByteArrayWithTime(int i2, byte[] bArr) {
            byte[] bytes = createDueTime(i2).getBytes();
            byte[] bArr2 = new byte[bytes.length + bArr.length];
            System.arraycopy(bytes, 0, bArr2, 0, bytes.length);
            System.arraycopy(bArr, 0, bArr2, bytes.length, bArr.length);
            return bArr2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static byte[] parcelable2Bytes(Parcelable parcelable) {
            if (parcelable == null) {
                return null;
            }
            Parcel parcelObtain = Parcel.obtain();
            parcelable.writeToParcel(parcelObtain, 0);
            byte[] bArrMarshall = parcelObtain.marshall();
            parcelObtain.recycle();
            return bArrMarshall;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public static byte[] readFile2Bytes(File file) throws Throwable {
            Throwable th;
            FileChannel channel;
            try {
                try {
                    channel = new RandomAccessFile(file, "r").getChannel();
                } catch (IOException e2) {
                    e = e2;
                    channel = null;
                } catch (Throwable th2) {
                    th = th2;
                    file = null;
                    CloseUtils.closeIO(file);
                    throw th;
                }
                try {
                    int size = (int) channel.size();
                    byte[] bArr = new byte[size];
                    channel.map(FileChannel.MapMode.READ_ONLY, 0L, size).load().get(bArr, 0, size);
                    CloseUtils.closeIO(channel);
                    return bArr;
                } catch (IOException e3) {
                    e = e3;
                    e.printStackTrace();
                    CloseUtils.closeIO(channel);
                    return null;
                }
            } catch (Throwable th3) {
                th = th3;
                CloseUtils.closeIO(file);
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Not initialized variable reg: 3, insn: 0x0031: MOVE (r0 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]) (LINE:50), block:B:20:0x0031 */
        public static byte[] serializable2Bytes(Serializable serializable) throws Throwable {
            ObjectOutputStream objectOutputStream;
            Closeable closeable;
            ByteArrayOutputStream byteArrayOutputStream;
            Closeable closeable2 = null;
            if (serializable == null) {
                return null;
            }
            try {
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                } catch (Exception e2) {
                    e = e2;
                    objectOutputStream = null;
                } catch (Throwable th) {
                    th = th;
                    CloseUtils.closeIO(closeable2);
                    throw th;
                }
                try {
                    objectOutputStream.writeObject(serializable);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    CloseUtils.closeIO(objectOutputStream);
                    return byteArray;
                } catch (Exception e3) {
                    e = e3;
                    e.printStackTrace();
                    CloseUtils.closeIO(objectOutputStream);
                    return null;
                }
            } catch (Throwable th2) {
                th = th2;
                closeable2 = closeable;
                CloseUtils.closeIO(closeable2);
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static byte[] string2Bytes(String str) {
            if (str == null) {
                return null;
            }
            return str.getBytes();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void writeFileFromBytes(File file, byte[] bArr) throws IOException {
            FileChannel channel = null;
            try {
                try {
                    channel = new FileOutputStream(file, false).getChannel();
                    channel.write(ByteBuffer.wrap(bArr));
                    channel.force(true);
                    CloseUtils.closeIO(channel);
                } catch (IOException e2) {
                    e2.printStackTrace();
                    CloseUtils.closeIO(channel);
                }
            } catch (Throwable th) {
                CloseUtils.closeIO(channel);
                throw th;
            }
        }
    }

    public class CacheManager {
        private final AtomicInteger cacheCount;
        private final File cacheDir;
        private final AtomicLong cacheSize;
        private final int countLimit;
        private final Map<File, Long> lastUsageDates;
        private final Thread mThread;
        private final long sizeLimit;

        /* JADX INFO: Access modifiers changed from: private */
        public boolean clear() {
            File[] fileArrListFiles = this.cacheDir.listFiles();
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
            try {
                this.mThread.join();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            return this.cacheCount.get();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getCacheSize() throws InterruptedException {
            try {
                this.mThread.join();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            return this.cacheSize.get();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public File getFileBeforePut(String str) {
            File file = new File(this.cacheDir, String.valueOf(str.hashCode()));
            if (file.exists()) {
                this.cacheCount.addAndGet(-1);
                this.cacheSize.addAndGet(-file.length());
            }
            return file;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public File getFileIfExists(String str) {
            File file = new File(this.cacheDir, String.valueOf(str.hashCode()));
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

        private CacheManager(final File file, long j2, int i2) {
            this.lastUsageDates = Collections.synchronizedMap(new HashMap());
            this.cacheDir = file;
            this.sizeLimit = j2;
            this.countLimit = i2;
            this.cacheSize = new AtomicLong();
            this.cacheCount = new AtomicInteger();
            Thread thread = new Thread(new Runnable() { // from class: com.plv.thirdpart.blankj.utilcode.util.CacheUtils.CacheManager.1
                @Override // java.lang.Runnable
                public void run() {
                    File[] fileArrListFiles = file.listFiles();
                    if (fileArrListFiles != null) {
                        int length = 0;
                        int i3 = 0;
                        for (File file2 : fileArrListFiles) {
                            length = (int) (length + file2.length());
                            i3++;
                            CacheManager.this.lastUsageDates.put(file2, Long.valueOf(file2.lastModified()));
                        }
                        CacheManager.this.cacheSize.getAndAdd(length);
                        CacheManager.this.cacheCount.getAndAdd(i3);
                    }
                }
            });
            this.mThread = thread;
            thread.start();
        }
    }

    private CacheUtils(@NonNull File file, long j2, int i2) {
        if (!file.exists() && !file.mkdirs()) {
            PLVCommonLog.exception(new RuntimeException("can't make dirs in " + file.getAbsolutePath()));
        }
        this.mCacheManager = new CacheManager(file, j2, i2);
    }

    public static CacheUtils getInstance() {
        return getInstance("", Long.MAX_VALUE, Integer.MAX_VALUE);
    }

    private static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public boolean clear() {
        return this.mCacheManager.clear();
    }

    public Bitmap getBitmap(@NonNull String str) {
        return getBitmap(str, null);
    }

    public byte[] getBytes(@NonNull String str) {
        return getBytes(str, null);
    }

    public int getCacheCount() {
        return this.mCacheManager.getCacheCount();
    }

    public long getCacheSize() {
        return this.mCacheManager.getCacheSize();
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

    public void put(@NonNull String str, @NonNull byte[] bArr) throws IOException {
        put(str, bArr, -1);
    }

    public boolean remove(@NonNull String str) {
        return this.mCacheManager.removeByKey(str);
    }

    public static CacheUtils getInstance(String str) {
        return getInstance(str, Long.MAX_VALUE, Integer.MAX_VALUE);
    }

    public Bitmap getBitmap(@NonNull String str, Bitmap bitmap) {
        byte[] bytes = getBytes(str);
        return bytes == null ? bitmap : CacheHelper.bytes2Bitmap(bytes);
    }

    public byte[] getBytes(@NonNull String str, byte[] bArr) throws Throwable {
        File fileIfExists = this.mCacheManager.getFileIfExists(str);
        if (fileIfExists == null) {
            return bArr;
        }
        byte[] file2Bytes = CacheHelper.readFile2Bytes(fileIfExists);
        if (CacheHelper.isDue(file2Bytes)) {
            this.mCacheManager.removeByKey(str);
            return bArr;
        }
        this.mCacheManager.updateModify(fileIfExists);
        return CacheHelper.getDataWithoutDueTime(file2Bytes);
    }

    public Drawable getDrawable(@NonNull String str, Drawable drawable) {
        byte[] bytes = getBytes(str);
        return bytes == null ? drawable : CacheHelper.bytes2Drawable(bytes);
    }

    public JSONArray getJSONArray(@NonNull String str, JSONArray jSONArray) {
        byte[] bytes = getBytes(str);
        return bytes == null ? jSONArray : CacheHelper.bytes2JSONArray(bytes);
    }

    public JSONObject getJSONObject(@NonNull String str, JSONObject jSONObject) {
        byte[] bytes = getBytes(str);
        return bytes == null ? jSONObject : CacheHelper.bytes2JSONObject(bytes);
    }

    public <T> T getParcelable(@NonNull String str, @NonNull Parcelable.Creator<T> creator, T t2) {
        byte[] bytes = getBytes(str);
        return bytes == null ? t2 : (T) CacheHelper.bytes2Parcelable(bytes, creator);
    }

    public Object getSerializable(@NonNull String str, Object obj) {
        return getBytes(str) == null ? obj : CacheHelper.bytes2Object(getBytes(str));
    }

    public String getString(@NonNull String str, String str2) {
        byte[] bytes = getBytes(str);
        return bytes == null ? str2 : CacheHelper.bytes2String(bytes);
    }

    public void put(@NonNull String str, @NonNull byte[] bArr, int i2) throws IOException {
        if (bArr.length <= 0) {
            return;
        }
        if (i2 >= 0) {
            bArr = CacheHelper.newByteArrayWithTime(i2, bArr);
        }
        File fileBeforePut = this.mCacheManager.getFileBeforePut(str);
        CacheHelper.writeFileFromBytes(fileBeforePut, bArr);
        this.mCacheManager.updateModify(fileBeforePut);
        this.mCacheManager.put(fileBeforePut);
    }

    public static CacheUtils getInstance(long j2, int i2) {
        return getInstance("", j2, i2);
    }

    public static CacheUtils getInstance(String str, long j2, int i2) {
        if (isSpace(str)) {
            str = "cacheUtils";
        }
        return getInstance(new File(Utils.getApp().getCacheDir(), str), j2, i2);
    }

    public static CacheUtils getInstance(@NonNull File file) {
        return getInstance(file, Long.MAX_VALUE, Integer.MAX_VALUE);
    }

    public static CacheUtils getInstance(@NonNull File file, long j2, int i2) {
        String str = file.getAbsoluteFile() + StrPool.UNDERLINE + Process.myPid();
        SimpleArrayMap<String, CacheUtils> simpleArrayMap = CACHE_MAP;
        CacheUtils cacheUtils = simpleArrayMap.get(str);
        if (cacheUtils != null) {
            return cacheUtils;
        }
        CacheUtils cacheUtils2 = new CacheUtils(file, j2, i2);
        simpleArrayMap.put(str, cacheUtils2);
        return cacheUtils2;
    }

    public void put(@NonNull String str, @NonNull String str2) throws IOException {
        put(str, str2, -1);
    }

    public void put(@NonNull String str, @NonNull String str2, int i2) throws IOException {
        put(str, CacheHelper.string2Bytes(str2), i2);
    }

    public void put(@NonNull String str, @NonNull JSONObject jSONObject) throws IOException {
        put(str, jSONObject, -1);
    }

    public void put(@NonNull String str, @NonNull JSONObject jSONObject, int i2) throws IOException {
        put(str, CacheHelper.jsonObject2Bytes(jSONObject), i2);
    }

    public void put(@NonNull String str, @NonNull JSONArray jSONArray) throws IOException {
        put(str, jSONArray, -1);
    }

    public void put(@NonNull String str, @NonNull JSONArray jSONArray, int i2) throws IOException {
        put(str, CacheHelper.jsonArray2Bytes(jSONArray), i2);
    }

    public void put(@NonNull String str, @NonNull Bitmap bitmap) throws IOException {
        put(str, bitmap, -1);
    }

    public void put(@NonNull String str, @NonNull Bitmap bitmap, int i2) throws IOException {
        put(str, CacheHelper.bitmap2Bytes(bitmap), i2);
    }

    public void put(@NonNull String str, @NonNull Drawable drawable) throws IOException {
        put(str, CacheHelper.drawable2Bytes(drawable));
    }

    public void put(@NonNull String str, @NonNull Drawable drawable, int i2) throws IOException {
        put(str, CacheHelper.drawable2Bytes(drawable), i2);
    }

    public void put(@NonNull String str, @NonNull Parcelable parcelable) throws IOException {
        put(str, parcelable, -1);
    }

    public void put(@NonNull String str, @NonNull Parcelable parcelable, int i2) throws IOException {
        put(str, CacheHelper.parcelable2Bytes(parcelable), i2);
    }

    public void put(@NonNull String str, @NonNull Serializable serializable) throws IOException {
        put(str, serializable, -1);
    }

    public void put(@NonNull String str, @NonNull Serializable serializable, int i2) throws IOException {
        put(str, CacheHelper.serializable2Bytes(serializable), i2);
    }
}
