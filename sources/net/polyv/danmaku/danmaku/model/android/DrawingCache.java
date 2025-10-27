package net.polyv.danmaku.danmaku.model.android;

import net.polyv.danmaku.danmaku.model.IDrawingCache;
import net.polyv.danmaku.danmaku.model.objectpool.Poolable;

/* loaded from: classes9.dex */
public class DrawingCache implements IDrawingCache<DrawingCacheHolder>, Poolable<DrawingCache> {
    private boolean mIsPooled;
    private DrawingCache mNextElement;
    private int mSize = 0;
    private int referenceCount = 0;
    private final DrawingCacheHolder mHolder = new DrawingCacheHolder();

    @Override // net.polyv.danmaku.danmaku.model.IDrawingCache
    public void build(int i2, int i3, int i4, boolean z2, int i5) {
        this.mHolder.buildCache(i2, i3, i4, z2, i5);
        this.mSize = this.mHolder.bitmap.getRowBytes() * this.mHolder.bitmap.getHeight();
    }

    @Override // net.polyv.danmaku.danmaku.model.IDrawingCache
    public synchronized void decreaseReference() {
        this.referenceCount--;
    }

    @Override // net.polyv.danmaku.danmaku.model.IDrawingCache
    public void destroy() {
        DrawingCacheHolder drawingCacheHolder = this.mHolder;
        if (drawingCacheHolder != null) {
            drawingCacheHolder.recycle();
        }
        this.mSize = 0;
        this.referenceCount = 0;
    }

    @Override // net.polyv.danmaku.danmaku.model.IDrawingCache
    public void erase() {
        this.mHolder.erase();
    }

    @Override // net.polyv.danmaku.danmaku.model.IDrawingCache
    public synchronized boolean hasReferences() {
        return this.referenceCount > 0;
    }

    @Override // net.polyv.danmaku.danmaku.model.IDrawingCache
    public int height() {
        return this.mHolder.height;
    }

    @Override // net.polyv.danmaku.danmaku.model.IDrawingCache
    public synchronized void increaseReference() {
        this.referenceCount++;
    }

    @Override // net.polyv.danmaku.danmaku.model.objectpool.Poolable
    public boolean isPooled() {
        return this.mIsPooled;
    }

    @Override // net.polyv.danmaku.danmaku.model.objectpool.Poolable
    public void setPooled(boolean z2) {
        this.mIsPooled = z2;
    }

    @Override // net.polyv.danmaku.danmaku.model.IDrawingCache
    public int size() {
        return this.mSize;
    }

    @Override // net.polyv.danmaku.danmaku.model.IDrawingCache
    public int width() {
        return this.mHolder.width;
    }

    @Override // net.polyv.danmaku.danmaku.model.IDrawingCache
    public DrawingCacheHolder get() {
        DrawingCacheHolder drawingCacheHolder = this.mHolder;
        if (drawingCacheHolder.bitmap == null) {
            return null;
        }
        return drawingCacheHolder;
    }

    @Override // net.polyv.danmaku.danmaku.model.objectpool.Poolable
    public DrawingCache getNextPoolable() {
        return this.mNextElement;
    }

    @Override // net.polyv.danmaku.danmaku.model.objectpool.Poolable
    public void setNextPoolable(DrawingCache drawingCache) {
        this.mNextElement = drawingCache;
    }
}
