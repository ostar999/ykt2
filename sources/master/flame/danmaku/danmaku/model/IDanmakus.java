package master.flame.danmaku.danmaku.model;

import java.util.Collection;
import java.util.Comparator;
import master.flame.danmaku.danmaku.util.DanmakuUtils;

/* loaded from: classes8.dex */
public interface IDanmakus {
    public static final int ST_BY_LIST = 4;
    public static final int ST_BY_TIME = 0;
    public static final int ST_BY_YPOS = 1;
    public static final int ST_BY_YPOS_DESC = 2;

    public static class BaseComparator implements Comparator<BaseDanmaku> {
        protected boolean mDuplicateMergingEnable;

        public BaseComparator(boolean z2) {
            setDuplicateMergingEnabled(z2);
        }

        public void setDuplicateMergingEnabled(boolean z2) {
            this.mDuplicateMergingEnable = z2;
        }

        @Override // java.util.Comparator
        public int compare(BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2) {
            if (this.mDuplicateMergingEnable && DanmakuUtils.isDuplicate(baseDanmaku, baseDanmaku2)) {
                return 0;
            }
            return DanmakuUtils.compare(baseDanmaku, baseDanmaku2);
        }
    }

    public static abstract class Consumer<Progress, Result> {
        public static final int ACTION_BREAK = 1;
        public static final int ACTION_CONTINUE = 0;
        public static final int ACTION_REMOVE = 2;
        public static final int ACTION_REMOVE_AND_BREAK = 3;

        public abstract int accept(Progress progress);

        public void after() {
        }

        public void before() {
        }

        public Result result() {
            return null;
        }
    }

    public static abstract class DefaultConsumer<Progress> extends Consumer<Progress, Void> {
    }

    public static class TimeComparator extends BaseComparator {
        public TimeComparator(boolean z2) {
            super(z2);
        }

        @Override // master.flame.danmaku.danmaku.model.IDanmakus.BaseComparator, java.util.Comparator
        public int compare(BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2) {
            return super.compare(baseDanmaku, baseDanmaku2);
        }
    }

    public static class YPosComparator extends BaseComparator {
        public YPosComparator(boolean z2) {
            super(z2);
        }

        @Override // master.flame.danmaku.danmaku.model.IDanmakus.BaseComparator, java.util.Comparator
        public int compare(BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2) {
            if (this.mDuplicateMergingEnable && DanmakuUtils.isDuplicate(baseDanmaku, baseDanmaku2)) {
                return 0;
            }
            return Float.compare(baseDanmaku.getTop(), baseDanmaku2.getTop());
        }
    }

    public static class YPosDescComparator extends BaseComparator {
        public YPosDescComparator(boolean z2) {
            super(z2);
        }

        @Override // master.flame.danmaku.danmaku.model.IDanmakus.BaseComparator, java.util.Comparator
        public int compare(BaseDanmaku baseDanmaku, BaseDanmaku baseDanmaku2) {
            if (this.mDuplicateMergingEnable && DanmakuUtils.isDuplicate(baseDanmaku, baseDanmaku2)) {
                return 0;
            }
            return Float.compare(baseDanmaku2.getTop(), baseDanmaku.getTop());
        }
    }

    boolean addItem(BaseDanmaku baseDanmaku);

    void clear();

    boolean contains(BaseDanmaku baseDanmaku);

    BaseDanmaku first();

    void forEach(Consumer<? super BaseDanmaku, ?> consumer);

    void forEachSync(Consumer<? super BaseDanmaku, ?> consumer);

    Collection<BaseDanmaku> getCollection();

    boolean isEmpty();

    BaseDanmaku last();

    Object obtainSynchronizer();

    boolean removeItem(BaseDanmaku baseDanmaku);

    void setSubItemsDuplicateMergingEnabled(boolean z2);

    int size();

    IDanmakus sub(long j2, long j3);

    IDanmakus subnew(long j2, long j3);
}
