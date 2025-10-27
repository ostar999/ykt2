package net.polyv.danmaku.danmaku.model.android;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import net.polyv.danmaku.danmaku.model.BaseDanmaku;
import net.polyv.danmaku.danmaku.model.Danmaku;
import net.polyv.danmaku.danmaku.model.IDanmakus;

/* loaded from: classes9.dex */
public class Danmakus implements IDanmakus {
    private BaseDanmaku endItem;
    private BaseDanmaku endSubItem;
    public Collection<BaseDanmaku> items;
    private IDanmakus.BaseComparator mComparator;
    private boolean mDuplicateMergingEnabled;
    private Object mLockObject;
    private volatile AtomicInteger mSize;
    private int mSortType;
    private BaseDanmaku startItem;
    private BaseDanmaku startSubItem;
    private Danmakus subItems;

    public Danmakus() {
        this(0, false);
    }

    private BaseDanmaku createItem(String str) {
        return new Danmaku(str);
    }

    private void setDuplicateMergingEnabled(boolean z2) {
        this.mComparator.setDuplicateMergingEnabled(z2);
        this.mDuplicateMergingEnabled = z2;
    }

    private Collection<BaseDanmaku> subset(long j2, long j3) {
        Collection<BaseDanmaku> collection;
        if (this.mSortType == 4 || (collection = this.items) == null || collection.size() == 0) {
            return null;
        }
        if (this.subItems == null) {
            Danmakus danmakus = new Danmakus(this.mDuplicateMergingEnabled);
            this.subItems = danmakus;
            danmakus.mLockObject = this.mLockObject;
        }
        if (this.startSubItem == null) {
            this.startSubItem = createItem("start");
        }
        if (this.endSubItem == null) {
            this.endSubItem = createItem("end");
        }
        this.startSubItem.setTime(j2);
        this.endSubItem.setTime(j3);
        return ((SortedSet) this.items).subSet(this.startSubItem, this.endSubItem);
    }

    @Override // net.polyv.danmaku.danmaku.model.IDanmakus
    public boolean addItem(BaseDanmaku baseDanmaku) {
        synchronized (this.mLockObject) {
            Collection<BaseDanmaku> collection = this.items;
            if (collection != null) {
                try {
                    if (collection.add(baseDanmaku)) {
                        this.mSize.incrementAndGet();
                        return true;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            return false;
        }
    }

    @Override // net.polyv.danmaku.danmaku.model.IDanmakus
    public void clear() {
        synchronized (this.mLockObject) {
            Collection<BaseDanmaku> collection = this.items;
            if (collection != null) {
                collection.clear();
                this.mSize.set(0);
            }
        }
        if (this.subItems != null) {
            this.subItems = null;
            this.startItem = createItem("start");
            this.endItem = createItem("end");
        }
    }

    @Override // net.polyv.danmaku.danmaku.model.IDanmakus
    public boolean contains(BaseDanmaku baseDanmaku) {
        Collection<BaseDanmaku> collection = this.items;
        return collection != null && collection.contains(baseDanmaku);
    }

    @Override // net.polyv.danmaku.danmaku.model.IDanmakus
    public BaseDanmaku first() {
        Collection<BaseDanmaku> collection = this.items;
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        return this.mSortType == 4 ? (BaseDanmaku) ((LinkedList) this.items).peek() : (BaseDanmaku) ((SortedSet) this.items).first();
    }

    @Override // net.polyv.danmaku.danmaku.model.IDanmakus
    public void forEach(IDanmakus.Consumer<? super BaseDanmaku, ?> consumer) {
        consumer.before();
        Iterator<BaseDanmaku> it = this.items.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            BaseDanmaku next = it.next();
            if (next != null) {
                int iAccept = consumer.accept(next);
                if (iAccept == 1) {
                    break;
                }
                if (iAccept == 2) {
                    it.remove();
                    this.mSize.decrementAndGet();
                } else if (iAccept == 3) {
                    it.remove();
                    this.mSize.decrementAndGet();
                    break;
                }
            }
        }
        consumer.after();
    }

    @Override // net.polyv.danmaku.danmaku.model.IDanmakus
    public void forEachSync(IDanmakus.Consumer<? super BaseDanmaku, ?> consumer) {
        synchronized (this.mLockObject) {
            forEach(consumer);
        }
    }

    @Override // net.polyv.danmaku.danmaku.model.IDanmakus
    public Collection<BaseDanmaku> getCollection() {
        return this.items;
    }

    @Override // net.polyv.danmaku.danmaku.model.IDanmakus
    public boolean isEmpty() {
        Collection<BaseDanmaku> collection = this.items;
        return collection == null || collection.isEmpty();
    }

    @Override // net.polyv.danmaku.danmaku.model.IDanmakus
    public BaseDanmaku last() {
        Collection<BaseDanmaku> collection = this.items;
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        return this.mSortType == 4 ? (BaseDanmaku) ((LinkedList) this.items).peekLast() : (BaseDanmaku) ((SortedSet) this.items).last();
    }

    @Override // net.polyv.danmaku.danmaku.model.IDanmakus
    public Object obtainSynchronizer() {
        return this.mLockObject;
    }

    @Override // net.polyv.danmaku.danmaku.model.IDanmakus
    public boolean removeItem(BaseDanmaku baseDanmaku) {
        if (baseDanmaku == null) {
            return false;
        }
        if (baseDanmaku.isOutside()) {
            baseDanmaku.setVisibility(false);
        }
        synchronized (this.mLockObject) {
            if (!this.items.remove(baseDanmaku)) {
                return false;
            }
            this.mSize.decrementAndGet();
            return true;
        }
    }

    public void setItems(Collection<BaseDanmaku> collection) {
        if (!this.mDuplicateMergingEnabled || this.mSortType == 4) {
            this.items = collection;
        } else {
            synchronized (this.mLockObject) {
                this.items.clear();
                this.items.addAll(collection);
                collection = this.items;
            }
        }
        if (collection instanceof List) {
            this.mSortType = 4;
        }
        this.mSize.set(collection == null ? 0 : collection.size());
    }

    @Override // net.polyv.danmaku.danmaku.model.IDanmakus
    public void setSubItemsDuplicateMergingEnabled(boolean z2) {
        this.mDuplicateMergingEnabled = z2;
        this.endItem = null;
        this.startItem = null;
        if (this.subItems == null) {
            Danmakus danmakus = new Danmakus(z2);
            this.subItems = danmakus;
            danmakus.mLockObject = this.mLockObject;
        }
        this.subItems.setDuplicateMergingEnabled(z2);
    }

    @Override // net.polyv.danmaku.danmaku.model.IDanmakus
    public int size() {
        return this.mSize.get();
    }

    @Override // net.polyv.danmaku.danmaku.model.IDanmakus
    public IDanmakus sub(long j2, long j3) {
        Collection<BaseDanmaku> collection = this.items;
        if (collection == null || collection.size() == 0) {
            return null;
        }
        if (this.subItems == null) {
            if (this.mSortType == 4) {
                Danmakus danmakus = new Danmakus(4);
                this.subItems = danmakus;
                danmakus.mLockObject = this.mLockObject;
                synchronized (this.mLockObject) {
                    this.subItems.setItems(this.items);
                }
            } else {
                Danmakus danmakus2 = new Danmakus(this.mDuplicateMergingEnabled);
                this.subItems = danmakus2;
                danmakus2.mLockObject = this.mLockObject;
            }
        }
        if (this.mSortType == 4) {
            return this.subItems;
        }
        if (this.startItem == null) {
            this.startItem = createItem("start");
        }
        if (this.endItem == null) {
            this.endItem = createItem("end");
        }
        if (this.subItems != null && j2 - this.startItem.getActualTime() >= 0 && j3 <= this.endItem.getActualTime()) {
            return this.subItems;
        }
        this.startItem.setTime(j2);
        this.endItem.setTime(j3);
        synchronized (this.mLockObject) {
            this.subItems.setItems(((SortedSet) this.items).subSet(this.startItem, this.endItem));
        }
        return this.subItems;
    }

    @Override // net.polyv.danmaku.danmaku.model.IDanmakus
    public IDanmakus subnew(long j2, long j3) {
        Collection<BaseDanmaku> collectionSubset = subset(j2, j3);
        if (collectionSubset == null || collectionSubset.isEmpty()) {
            return null;
        }
        return new Danmakus(new LinkedList(collectionSubset));
    }

    public Danmakus(int i2) {
        this(i2, false);
    }

    public Danmakus(int i2, boolean z2) {
        this(i2, z2, null);
    }

    public Danmakus(int i2, boolean z2, IDanmakus.BaseComparator baseComparator) {
        this.mSize = new AtomicInteger(0);
        this.mSortType = 0;
        this.mLockObject = new Object();
        if (i2 == 0) {
            if (baseComparator == null) {
                baseComparator = new IDanmakus.TimeComparator(z2);
            }
        } else if (i2 == 1) {
            baseComparator = new IDanmakus.YPosComparator(z2);
        } else {
            baseComparator = i2 == 2 ? new IDanmakus.YPosDescComparator(z2) : null;
        }
        if (i2 == 4) {
            this.items = new LinkedList();
        } else {
            this.mDuplicateMergingEnabled = z2;
            baseComparator.setDuplicateMergingEnabled(z2);
            this.items = new TreeSet(baseComparator);
            this.mComparator = baseComparator;
        }
        this.mSortType = i2;
        this.mSize.set(0);
    }

    public Danmakus(Collection<BaseDanmaku> collection) {
        this.mSize = new AtomicInteger(0);
        this.mSortType = 0;
        this.mLockObject = new Object();
        setItems(collection);
    }

    public Danmakus(boolean z2) {
        this(0, z2);
    }
}
