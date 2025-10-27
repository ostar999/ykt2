package com.nostra13.universalimageloader.cache.disc.impl;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;
import com.nostra13.universalimageloader.utils.IoUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class LimitedAgeDiskCache extends BaseDiskCache {
    private final Map<File, Long> loadingDates;
    private final long maxFileAge;

    public LimitedAgeDiskCache(File file, long j2) {
        this(file, null, DefaultConfigurationFactory.createFileNameGenerator(), j2);
    }

    private void rememberUsage(String str) {
        File file = getFile(str);
        long jCurrentTimeMillis = System.currentTimeMillis();
        file.setLastModified(jCurrentTimeMillis);
        this.loadingDates.put(file, Long.valueOf(jCurrentTimeMillis));
    }

    @Override // com.nostra13.universalimageloader.cache.disc.impl.BaseDiskCache, com.nostra13.universalimageloader.cache.disc.DiskCache
    public void clear() {
        super.clear();
        this.loadingDates.clear();
    }

    @Override // com.nostra13.universalimageloader.cache.disc.impl.BaseDiskCache, com.nostra13.universalimageloader.cache.disc.DiskCache
    public File get(String str) {
        boolean z2;
        File file = super.get(str);
        if (file != null && file.exists()) {
            Long lValueOf = this.loadingDates.get(file);
            if (lValueOf == null) {
                lValueOf = Long.valueOf(file.lastModified());
                z2 = false;
            } else {
                z2 = true;
            }
            if (System.currentTimeMillis() - lValueOf.longValue() > this.maxFileAge) {
                file.delete();
                this.loadingDates.remove(file);
            } else if (!z2) {
                this.loadingDates.put(file, lValueOf);
            }
        }
        return file;
    }

    @Override // com.nostra13.universalimageloader.cache.disc.impl.BaseDiskCache, com.nostra13.universalimageloader.cache.disc.DiskCache
    public boolean remove(String str) {
        this.loadingDates.remove(getFile(str));
        return super.remove(str);
    }

    @Override // com.nostra13.universalimageloader.cache.disc.impl.BaseDiskCache, com.nostra13.universalimageloader.cache.disc.DiskCache
    public boolean save(String str, InputStream inputStream, IoUtils.CopyListener copyListener) throws Throwable {
        boolean zSave = super.save(str, inputStream, copyListener);
        rememberUsage(str);
        return zSave;
    }

    public LimitedAgeDiskCache(File file, File file2, long j2) {
        this(file, file2, DefaultConfigurationFactory.createFileNameGenerator(), j2);
    }

    public LimitedAgeDiskCache(File file, File file2, FileNameGenerator fileNameGenerator, long j2) {
        super(file, file2, fileNameGenerator);
        this.loadingDates = Collections.synchronizedMap(new HashMap());
        this.maxFileAge = j2 * 1000;
    }

    @Override // com.nostra13.universalimageloader.cache.disc.impl.BaseDiskCache, com.nostra13.universalimageloader.cache.disc.DiskCache
    public boolean save(String str, Bitmap bitmap) throws IOException {
        boolean zSave = super.save(str, bitmap);
        rememberUsage(str);
        return zSave;
    }
}
