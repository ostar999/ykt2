package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import cn.hutool.core.text.StrPool;
import com.bumptech.glide.util.Util;
import java.util.NavigableMap;

@RequiresApi(19)
/* loaded from: classes2.dex */
final class SizeStrategy implements LruPoolStrategy {
    private static final int MAX_SIZE_MULTIPLE = 8;
    private final KeyPool keyPool = new KeyPool();
    private final GroupedLinkedMap<Key, Bitmap> groupedMap = new GroupedLinkedMap<>();
    private final NavigableMap<Integer, Integer> sortedSizes = new PrettyPrintTreeMap();

    @VisibleForTesting
    public static final class Key implements Poolable {
        private final KeyPool pool;
        int size;

        public Key(KeyPool keyPool) {
            this.pool = keyPool;
        }

        public boolean equals(Object obj) {
            return (obj instanceof Key) && this.size == ((Key) obj).size;
        }

        public int hashCode() {
            return this.size;
        }

        public void init(int i2) {
            this.size = i2;
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.Poolable
        public void offer() {
            this.pool.offer(this);
        }

        public String toString() {
            return SizeStrategy.getBitmapString(this.size);
        }
    }

    @VisibleForTesting
    public static class KeyPool extends BaseKeyPool<Key> {
        public Key get(int i2) {
            Key key = (Key) super.get();
            key.init(i2);
            return key;
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.BaseKeyPool
        public Key create() {
            return new Key(this);
        }
    }

    private void decrementBitmapOfSize(Integer num) {
        Integer num2 = this.sortedSizes.get(num);
        if (num2.intValue() == 1) {
            this.sortedSizes.remove(num);
        } else {
            this.sortedSizes.put(num, Integer.valueOf(num2.intValue() - 1));
        }
    }

    private static String getBitmapString(Bitmap bitmap) {
        return getBitmapString(Util.getBitmapByteSize(bitmap));
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    @Nullable
    public Bitmap get(int i2, int i3, Bitmap.Config config) {
        int bitmapByteSize = Util.getBitmapByteSize(i2, i3, config);
        Key key = this.keyPool.get(bitmapByteSize);
        Integer numCeilingKey = this.sortedSizes.ceilingKey(Integer.valueOf(bitmapByteSize));
        if (numCeilingKey != null && numCeilingKey.intValue() != bitmapByteSize && numCeilingKey.intValue() <= bitmapByteSize * 8) {
            this.keyPool.offer(key);
            key = this.keyPool.get(numCeilingKey.intValue());
        }
        Bitmap bitmap = this.groupedMap.get(key);
        if (bitmap != null) {
            bitmap.reconfigure(i2, i3, config);
            decrementBitmapOfSize(numCeilingKey);
        }
        return bitmap;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public int getSize(Bitmap bitmap) {
        return Util.getBitmapByteSize(bitmap);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public String logBitmap(Bitmap bitmap) {
        return getBitmapString(bitmap);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public void put(Bitmap bitmap) {
        Key key = this.keyPool.get(Util.getBitmapByteSize(bitmap));
        this.groupedMap.put(key, bitmap);
        Integer num = this.sortedSizes.get(Integer.valueOf(key.size));
        this.sortedSizes.put(Integer.valueOf(key.size), Integer.valueOf(num != null ? 1 + num.intValue() : 1));
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    @Nullable
    public Bitmap removeLast() {
        Bitmap bitmapRemoveLast = this.groupedMap.removeLast();
        if (bitmapRemoveLast != null) {
            decrementBitmapOfSize(Integer.valueOf(Util.getBitmapByteSize(bitmapRemoveLast)));
        }
        return bitmapRemoveLast;
    }

    public String toString() {
        return "SizeStrategy:\n  " + this.groupedMap + "\n  SortedSizes" + this.sortedSizes;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public String logBitmap(int i2, int i3, Bitmap.Config config) {
        return getBitmapString(Util.getBitmapByteSize(i2, i3, config));
    }

    public static String getBitmapString(int i2) {
        return StrPool.BRACKET_START + i2 + StrPool.BRACKET_END;
    }
}
