package net.tsz.afinal.bitmap.core;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import net.tsz.afinal.bitmap.core.BytesBufferPool;
import net.tsz.afinal.bitmap.core.DiskCache;
import net.tsz.afinal.utils.Utils;

/* loaded from: classes9.dex */
public class BitmapCache {
    private static final int DEFAULT_DISK_CACHE_COUNT = 10000;
    private static final boolean DEFAULT_DISK_CACHE_ENABLED = true;
    private static final int DEFAULT_DISK_CACHE_SIZE = 52428800;
    private static final boolean DEFAULT_MEM_CACHE_ENABLED = true;
    private static final int DEFAULT_MEM_CACHE_SIZE = 8388608;
    private ImageCacheParams mCacheParams;
    private DiskCache mDiskCache;
    private IMemoryCache mMemoryCache;

    public BitmapCache(ImageCacheParams imageCacheParams) {
        init(imageCacheParams);
    }

    private void init(ImageCacheParams imageCacheParams) {
        this.mCacheParams = imageCacheParams;
        if (imageCacheParams.memoryCacheEnabled) {
            if (imageCacheParams.recycleImmediately) {
                this.mMemoryCache = new SoftMemoryCacheImpl(imageCacheParams.memCacheSize);
            } else {
                this.mMemoryCache = new BaseMemoryCacheImpl(imageCacheParams.memCacheSize);
            }
        }
        if (imageCacheParams.diskCacheEnabled) {
            try {
                String absolutePath = this.mCacheParams.diskCacheDir.getAbsolutePath();
                ImageCacheParams imageCacheParams2 = this.mCacheParams;
                this.mDiskCache = new DiskCache(absolutePath, imageCacheParams2.diskCacheCount, imageCacheParams2.diskCacheSize, false);
            } catch (IOException unused) {
            }
        }
    }

    public void addToDiskCache(String str, byte[] bArr) {
        if (this.mDiskCache == null || str == null || bArr == null) {
            return;
        }
        byte[] bArrMakeKey = Utils.makeKey(str);
        long jCrc64Long = Utils.crc64Long(bArrMakeKey);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrMakeKey.length + bArr.length);
        byteBufferAllocate.put(bArrMakeKey);
        byteBufferAllocate.put(bArr);
        synchronized (this.mDiskCache) {
            try {
                this.mDiskCache.insert(jCrc64Long, byteBufferAllocate.array());
            } catch (IOException unused) {
            }
        }
    }

    public void addToMemoryCache(String str, Bitmap bitmap) {
        if (str == null || bitmap == null) {
            return;
        }
        this.mMemoryCache.put(str, bitmap);
    }

    public void clearCache() {
        clearMemoryCache();
        clearDiskCache();
    }

    public void clearDiskCache() {
        DiskCache diskCache = this.mDiskCache;
        if (diskCache != null) {
            diskCache.delete();
        }
    }

    public void clearMemoryCache() {
        IMemoryCache iMemoryCache = this.mMemoryCache;
        if (iMemoryCache != null) {
            iMemoryCache.evictAll();
        }
    }

    public void close() {
        DiskCache diskCache = this.mDiskCache;
        if (diskCache != null) {
            diskCache.close();
        }
    }

    public Bitmap getBitmapFromMemoryCache(String str) {
        IMemoryCache iMemoryCache = this.mMemoryCache;
        if (iMemoryCache != null) {
            return iMemoryCache.get(str);
        }
        return null;
    }

    public boolean getImageData(String str, BytesBufferPool.BytesBuffer bytesBuffer) {
        DiskCache.LookupRequest lookupRequest;
        if (this.mDiskCache == null) {
            return false;
        }
        byte[] bArrMakeKey = Utils.makeKey(str);
        long jCrc64Long = Utils.crc64Long(bArrMakeKey);
        try {
            lookupRequest = new DiskCache.LookupRequest();
            lookupRequest.key = jCrc64Long;
            lookupRequest.buffer = bytesBuffer.data;
        } catch (IOException unused) {
        }
        synchronized (this.mDiskCache) {
            if (!this.mDiskCache.lookup(lookupRequest)) {
                return false;
            }
            if (Utils.isSameKey(bArrMakeKey, lookupRequest.buffer)) {
                bytesBuffer.data = lookupRequest.buffer;
                int length = bArrMakeKey.length;
                bytesBuffer.offset = length;
                bytesBuffer.length = lookupRequest.length - length;
                return true;
            }
            return false;
        }
    }

    public void clearCache(String str) {
        clearMemoryCache(str);
        clearDiskCache(str);
    }

    public void clearDiskCache(String str) {
        addToDiskCache(str, new byte[0]);
    }

    public void clearMemoryCache(String str) {
        IMemoryCache iMemoryCache = this.mMemoryCache;
        if (iMemoryCache != null) {
            iMemoryCache.remove(str);
        }
    }

    public static class ImageCacheParams {
        public File diskCacheDir;
        public int memCacheSize = 8388608;
        public int diskCacheSize = BitmapCache.DEFAULT_DISK_CACHE_SIZE;
        public int diskCacheCount = 10000;
        public boolean memoryCacheEnabled = true;
        public boolean diskCacheEnabled = true;
        public boolean recycleImmediately = true;

        public ImageCacheParams(File file) {
            this.diskCacheDir = file;
        }

        private static int getMemoryClass(Context context) {
            return ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getMemoryClass();
        }

        public void setDiskCacheCount(int i2) {
            this.diskCacheCount = i2;
        }

        public void setDiskCacheSize(int i2) {
            this.diskCacheSize = i2;
        }

        public void setMemCacheSize(int i2) {
            this.memCacheSize = i2;
        }

        public void setMemCacheSizePercent(Context context, float f2) {
            if (f2 < 0.05f || f2 > 0.8f) {
                throw new IllegalArgumentException("setMemCacheSizePercent - percent must be between 0.05 and 0.8 (inclusive)");
            }
            this.memCacheSize = Math.round(f2 * getMemoryClass(context) * 1024.0f * 1024.0f);
        }

        public void setRecycleImmediately(boolean z2) {
            this.recycleImmediately = z2;
        }

        public ImageCacheParams(String str) {
            this.diskCacheDir = new File(str);
        }
    }
}
