package net.polyv.danmaku.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import net.polyv.danmaku.danmaku.model.BaseDanmaku;
import net.polyv.danmaku.danmaku.model.DanmakuTimer;
import net.polyv.danmaku.danmaku.model.Duration;
import net.polyv.danmaku.danmaku.model.IDanmakus;
import net.polyv.danmaku.danmaku.model.android.DanmakuContext;
import net.polyv.danmaku.danmaku.model.android.Danmakus;
import net.polyv.danmaku.danmaku.util.SystemClock;

/* loaded from: classes9.dex */
public class DanmakuFilters {
    public static final int FILTER_TYPE_DUPLICATE_MERGE = 128;
    public static final int FILTER_TYPE_ELAPSED_TIME = 4;
    public static final int FILTER_TYPE_MAXIMUM_LINES = 256;
    public static final int FILTER_TYPE_OVERLAPPING = 512;
    public static final int FILTER_TYPE_TEXTCOLOR = 8;
    public static final int FILTER_TYPE_TYPE = 1;
    public static final int FILTER_TYPE_USER_GUEST = 64;
    public static final int FILTER_TYPE_USER_HASH = 32;
    public static final int FILTER_TYPE_USER_ID = 16;
    public static final int FILYER_TYPE_QUANTITY = 2;
    public static final String TAG_DUPLICATE_FILTER = "1017_Filter";
    public static final String TAG_ELAPSED_TIME_FILTER = "1012_Filter";
    public static final String TAG_GUEST_FILTER = "1016_Filter";
    public static final String TAG_MAXIMUN_LINES_FILTER = "1018_Filter";
    public static final String TAG_OVERLAPPING_FILTER = "1019_Filter";
    public static final String TAG_PRIMARY_CUSTOM_FILTER = "2000_Primary_Custom_Filter";
    public static final String TAG_QUANTITY_DANMAKU_FILTER = "1011_Filter";
    public static final String TAG_TEXT_COLOR_DANMAKU_FILTER = "1013_Filter";
    public static final String TAG_TYPE_DANMAKU_FILTER = "1010_Filter";
    public static final String TAG_USER_HASH_FILTER = "1015_Filter";
    public static final String TAG_USER_ID_FILTER = "1014_Filter";
    public final Exception filterException = new Exception("not suuport this filter tag");
    private final Map<String, IDanmakuFilter<?>> filters = Collections.synchronizedSortedMap(new TreeMap());
    private final Map<String, IDanmakuFilter<?>> filtersSecondary = Collections.synchronizedSortedMap(new TreeMap());
    IDanmakuFilter<?>[] mFilterArray = new IDanmakuFilter[0];
    IDanmakuFilter<?>[] mFilterArraySecondary = new IDanmakuFilter[0];

    public static abstract class BaseDanmakuFilter<T> implements IDanmakuFilter<T> {
        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void clear() {
        }
    }

    public static class DuplicateMergingFilter extends BaseDanmakuFilter<Void> {
        protected final IDanmakus blockedDanmakus = new Danmakus(4);
        protected final LinkedHashMap<String, BaseDanmaku> currentDanmakus = new LinkedHashMap<>();
        private final IDanmakus passedDanmakus = new Danmakus(4);

        private final void removeTimeoutDanmakus(IDanmakus iDanmakus, final long j2) {
            iDanmakus.forEachSync(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: net.polyv.danmaku.controller.DanmakuFilters.DuplicateMergingFilter.1
                long startTime = SystemClock.uptimeMillis();

                @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku) {
                    if (SystemClock.uptimeMillis() - this.startTime > j2) {
                        return 1;
                    }
                    return baseDanmaku.isTimeOut() ? 2 : 1;
                }
            });
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.BaseDanmakuFilter, net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void clear() {
            reset();
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2, DanmakuContext danmakuContext) {
            boolean zNeedFilter = needFilter(baseDanmaku, i2, i3, danmakuTimer, z2);
            if (zNeedFilter) {
                baseDanmaku.mFilterParam |= 128;
            }
            return zNeedFilter;
        }

        public synchronized boolean needFilter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2) {
            removeTimeoutDanmakus(this.blockedDanmakus, 2L);
            removeTimeoutDanmakus(this.passedDanmakus, 2L);
            removeTimeoutDanmakus(this.currentDanmakus, 3);
            if (this.blockedDanmakus.contains(baseDanmaku) && !baseDanmaku.isOutside()) {
                return true;
            }
            if (this.passedDanmakus.contains(baseDanmaku)) {
                return false;
            }
            if (!this.currentDanmakus.containsKey(baseDanmaku.text)) {
                this.currentDanmakus.put(String.valueOf(baseDanmaku.text), baseDanmaku);
                this.passedDanmakus.addItem(baseDanmaku);
                return false;
            }
            this.currentDanmakus.put(String.valueOf(baseDanmaku.text), baseDanmaku);
            this.blockedDanmakus.removeItem(baseDanmaku);
            this.blockedDanmakus.addItem(baseDanmaku);
            return true;
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public synchronized void reset() {
            this.passedDanmakus.clear();
            this.blockedDanmakus.clear();
            this.currentDanmakus.clear();
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(Void r12) {
        }

        private void removeTimeoutDanmakus(LinkedHashMap<String, BaseDanmaku> linkedHashMap, int i2) {
            Iterator<Map.Entry<String, BaseDanmaku>> it = linkedHashMap.entrySet().iterator();
            long jUptimeMillis = SystemClock.uptimeMillis();
            while (it.hasNext()) {
                try {
                    if (!it.next().getValue().isTimeOut()) {
                        return;
                    }
                    it.remove();
                    if (SystemClock.uptimeMillis() - jUptimeMillis > i2) {
                        return;
                    }
                } catch (Exception unused) {
                    return;
                }
            }
        }
    }

    public static class ElapsedTimeFilter extends BaseDanmakuFilter<Object> {
        long mMaxTime = 20;

        private synchronized boolean needFilter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2) {
            if (danmakuTimer != null) {
                if (baseDanmaku.isOutside()) {
                    return SystemClock.uptimeMillis() - danmakuTimer.currMillisecond >= this.mMaxTime;
                }
            }
            return false;
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.BaseDanmakuFilter, net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void clear() {
            reset();
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2, DanmakuContext danmakuContext) {
            boolean zNeedFilter = needFilter(baseDanmaku, i2, i3, danmakuTimer, z2);
            if (zNeedFilter) {
                baseDanmaku.mFilterParam |= 4;
            }
            return zNeedFilter;
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public synchronized void reset() {
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(Object obj) {
            reset();
        }
    }

    public static class GuestFilter extends BaseDanmakuFilter<Boolean> {
        private Boolean mBlock = Boolean.FALSE;

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2, DanmakuContext danmakuContext) {
            boolean z3 = this.mBlock.booleanValue() && baseDanmaku.isGuest;
            if (z3) {
                baseDanmaku.mFilterParam |= 64;
            }
            return z3;
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void reset() {
            this.mBlock = Boolean.FALSE;
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(Boolean bool) {
            this.mBlock = bool;
        }
    }

    public interface IDanmakuFilter<T> {
        void clear();

        boolean filter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2, DanmakuContext danmakuContext);

        void reset();

        void setData(T t2);
    }

    public static class MaximumLinesFilter extends BaseDanmakuFilter<Map<Integer, Integer>> {
        private Map<Integer, Integer> mMaximumLinesPairs;

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2, DanmakuContext danmakuContext) {
            Map<Integer, Integer> map = this.mMaximumLinesPairs;
            boolean z3 = false;
            if (map != null) {
                Integer num = map.get(Integer.valueOf(baseDanmaku.getType()));
                if (num != null && i2 >= num.intValue()) {
                    z3 = true;
                }
                if (z3) {
                    baseDanmaku.mFilterParam |= 256;
                }
            }
            return z3;
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void reset() {
            this.mMaximumLinesPairs = null;
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(Map<Integer, Integer> map) {
            this.mMaximumLinesPairs = map;
        }
    }

    public static class OverlappingFilter extends BaseDanmakuFilter<Map<Integer, Boolean>> {
        private Map<Integer, Boolean> mEnabledPairs;

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2, DanmakuContext danmakuContext) {
            Map<Integer, Boolean> map = this.mEnabledPairs;
            boolean z3 = false;
            if (map != null) {
                Boolean bool = map.get(Integer.valueOf(baseDanmaku.getType()));
                if (bool != null && bool.booleanValue() && z2) {
                    z3 = true;
                }
                if (z3) {
                    baseDanmaku.mFilterParam |= 512;
                }
            }
            return z3;
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void reset() {
            this.mEnabledPairs = null;
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(Map<Integer, Boolean> map) {
            this.mEnabledPairs = map;
        }
    }

    public static class QuantityDanmakuFilter extends BaseDanmakuFilter<Integer> {
        protected int mMaximumSize = -1;
        protected BaseDanmaku mLastSkipped = null;
        private float mFilterFactor = 1.0f;

        private boolean needFilter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2, DanmakuContext danmakuContext) {
            if (this.mMaximumSize > 0 && baseDanmaku.getType() == 1) {
                BaseDanmaku baseDanmaku2 = this.mLastSkipped;
                if (baseDanmaku2 != null && !baseDanmaku2.isTimeOut()) {
                    long actualTime = baseDanmaku.getActualTime() - this.mLastSkipped.getActualTime();
                    Duration duration = danmakuContext.mDanmakuFactory.MAX_Duration_Scroll_Danmaku;
                    if ((actualTime >= 0 && duration != null && actualTime < duration.value * this.mFilterFactor) || i2 > this.mMaximumSize) {
                        return true;
                    }
                    this.mLastSkipped = baseDanmaku;
                    return false;
                }
                this.mLastSkipped = baseDanmaku;
            }
            return false;
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.BaseDanmakuFilter, net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void clear() {
            reset();
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public synchronized boolean filter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2, DanmakuContext danmakuContext) {
            boolean zNeedFilter;
            zNeedFilter = needFilter(baseDanmaku, i2, i3, danmakuTimer, z2, danmakuContext);
            if (zNeedFilter) {
                baseDanmaku.mFilterParam |= 2;
            }
            return zNeedFilter;
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public synchronized void reset() {
            this.mLastSkipped = null;
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(Integer num) {
            reset();
            if (num == null || num.intValue() == this.mMaximumSize) {
                return;
            }
            int iIntValue = num.intValue() + (num.intValue() / 5);
            this.mMaximumSize = iIntValue;
            this.mFilterFactor = 1.0f / iIntValue;
        }
    }

    public static class TextColorFilter extends BaseDanmakuFilter<List<Integer>> {
        public List<Integer> mWhiteList = new ArrayList();

        private void addToWhiteList(Integer num) {
            if (this.mWhiteList.contains(num)) {
                return;
            }
            this.mWhiteList.add(num);
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2, DanmakuContext danmakuContext) {
            boolean z3 = (baseDanmaku == null || this.mWhiteList.contains(Integer.valueOf(baseDanmaku.textColor))) ? false : true;
            if (z3) {
                baseDanmaku.mFilterParam |= 8;
            }
            return z3;
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void reset() {
            this.mWhiteList.clear();
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(List<Integer> list) {
            reset();
            if (list != null) {
                Iterator<Integer> it = list.iterator();
                while (it.hasNext()) {
                    addToWhiteList(it.next());
                }
            }
        }
    }

    public static class TypeDanmakuFilter extends BaseDanmakuFilter<List<Integer>> {
        final List<Integer> mFilterTypes = Collections.synchronizedList(new ArrayList());

        public void disableType(Integer num) {
            if (this.mFilterTypes.contains(num)) {
                this.mFilterTypes.remove(num);
            }
        }

        public void enableType(Integer num) {
            if (this.mFilterTypes.contains(num)) {
                return;
            }
            this.mFilterTypes.add(num);
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2, DanmakuContext danmakuContext) {
            boolean z3 = baseDanmaku != null && this.mFilterTypes.contains(Integer.valueOf(baseDanmaku.getType()));
            if (z3) {
                baseDanmaku.mFilterParam = 1 | baseDanmaku.mFilterParam;
            }
            return z3;
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void reset() {
            this.mFilterTypes.clear();
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(List<Integer> list) {
            reset();
            if (list != null) {
                Iterator<Integer> it = list.iterator();
                while (it.hasNext()) {
                    enableType(it.next());
                }
            }
        }
    }

    public static abstract class UserFilter<T> extends BaseDanmakuFilter<List<T>> {
        public List<T> mBlackList = new ArrayList();

        private void addToBlackList(T t2) {
            if (this.mBlackList.contains(t2)) {
                return;
            }
            this.mBlackList.add(t2);
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public abstract boolean filter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2, DanmakuContext danmakuContext);

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void reset() {
            this.mBlackList.clear();
        }

        @Override // net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public void setData(List<T> list) {
            reset();
            if (list != null) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    addToBlackList(it.next());
                }
            }
        }
    }

    public static class UserHashFilter extends UserFilter<String> {
        @Override // net.polyv.danmaku.controller.DanmakuFilters.UserFilter, net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2, DanmakuContext danmakuContext) {
            boolean z3 = baseDanmaku != null && this.mBlackList.contains(baseDanmaku.userHash);
            if (z3) {
                baseDanmaku.mFilterParam |= 32;
            }
            return z3;
        }
    }

    public static class UserIdFilter extends UserFilter<Integer> {
        @Override // net.polyv.danmaku.controller.DanmakuFilters.UserFilter, net.polyv.danmaku.controller.DanmakuFilters.IDanmakuFilter
        public boolean filter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2, DanmakuContext danmakuContext) {
            boolean z3 = baseDanmaku != null && this.mBlackList.contains(Integer.valueOf(baseDanmaku.userId));
            if (z3) {
                baseDanmaku.mFilterParam |= 16;
            }
            return z3;
        }
    }

    private void throwFilterException() throws Exception {
        try {
            throw this.filterException;
        } catch (Exception unused) {
        }
    }

    public void clear() {
        for (IDanmakuFilter<?> iDanmakuFilter : this.mFilterArray) {
            if (iDanmakuFilter != null) {
                iDanmakuFilter.clear();
            }
        }
        for (IDanmakuFilter<?> iDanmakuFilter2 : this.mFilterArraySecondary) {
            if (iDanmakuFilter2 != null) {
                iDanmakuFilter2.clear();
            }
        }
    }

    public void filter(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2, DanmakuContext danmakuContext) {
        for (IDanmakuFilter<?> iDanmakuFilter : this.mFilterArray) {
            if (iDanmakuFilter != null) {
                boolean zFilter = iDanmakuFilter.filter(baseDanmaku, i2, i3, danmakuTimer, z2, danmakuContext);
                baseDanmaku.filterResetFlag = danmakuContext.mGlobalFlagValues.FILTER_RESET_FLAG;
                if (zFilter) {
                    return;
                }
            }
        }
    }

    public boolean filterSecondary(BaseDanmaku baseDanmaku, int i2, int i3, DanmakuTimer danmakuTimer, boolean z2, DanmakuContext danmakuContext) {
        for (IDanmakuFilter<?> iDanmakuFilter : this.mFilterArraySecondary) {
            if (iDanmakuFilter != null) {
                boolean zFilter = iDanmakuFilter.filter(baseDanmaku, i2, i3, danmakuTimer, z2, danmakuContext);
                baseDanmaku.filterResetFlag = danmakuContext.mGlobalFlagValues.FILTER_RESET_FLAG;
                if (zFilter) {
                    return true;
                }
            }
        }
        return false;
    }

    public IDanmakuFilter<?> get(String str) {
        return get(str, true);
    }

    public IDanmakuFilter<?> registerFilter(String str) {
        return registerFilter(str, true);
    }

    public void release() {
        clear();
        this.filters.clear();
        this.mFilterArray = new IDanmakuFilter[0];
        this.filtersSecondary.clear();
        this.mFilterArraySecondary = new IDanmakuFilter[0];
    }

    public void reset() {
        for (IDanmakuFilter<?> iDanmakuFilter : this.mFilterArray) {
            if (iDanmakuFilter != null) {
                iDanmakuFilter.reset();
            }
        }
        for (IDanmakuFilter<?> iDanmakuFilter2 : this.mFilterArraySecondary) {
            if (iDanmakuFilter2 != null) {
                iDanmakuFilter2.reset();
            }
        }
    }

    public void unregisterFilter(String str) {
        unregisterFilter(str, true);
    }

    public IDanmakuFilter<?> get(String str, boolean z2) {
        IDanmakuFilter<?> iDanmakuFilter = (z2 ? this.filters : this.filtersSecondary).get(str);
        return iDanmakuFilter == null ? registerFilter(str, z2) : iDanmakuFilter;
    }

    public IDanmakuFilter<?> registerFilter(String str, boolean z2) throws Exception {
        if (str == null) {
            throwFilterException();
            return null;
        }
        IDanmakuFilter<?> overlappingFilter = this.filters.get(str);
        if (overlappingFilter == null) {
            if ("1010_Filter".equals(str)) {
                overlappingFilter = new TypeDanmakuFilter();
            } else if ("1011_Filter".equals(str)) {
                overlappingFilter = new QuantityDanmakuFilter();
            } else if ("1012_Filter".equals(str)) {
                overlappingFilter = new ElapsedTimeFilter();
            } else if ("1013_Filter".equals(str)) {
                overlappingFilter = new TextColorFilter();
            } else if ("1014_Filter".equals(str)) {
                overlappingFilter = new UserIdFilter();
            } else if ("1015_Filter".equals(str)) {
                overlappingFilter = new UserHashFilter();
            } else if ("1016_Filter".equals(str)) {
                overlappingFilter = new GuestFilter();
            } else if ("1017_Filter".equals(str)) {
                overlappingFilter = new DuplicateMergingFilter();
            } else if ("1018_Filter".equals(str)) {
                overlappingFilter = new MaximumLinesFilter();
            } else if ("1019_Filter".equals(str)) {
                overlappingFilter = new OverlappingFilter();
            }
        }
        if (overlappingFilter == null) {
            throwFilterException();
            return null;
        }
        overlappingFilter.setData(null);
        if (z2) {
            this.filters.put(str, overlappingFilter);
            this.mFilterArray = (IDanmakuFilter[]) this.filters.values().toArray(this.mFilterArray);
        } else {
            this.filtersSecondary.put(str, overlappingFilter);
            this.mFilterArraySecondary = (IDanmakuFilter[]) this.filtersSecondary.values().toArray(this.mFilterArraySecondary);
        }
        return overlappingFilter;
    }

    public void unregisterFilter(String str, boolean z2) {
        IDanmakuFilter<?> iDanmakuFilterRemove = (z2 ? this.filters : this.filtersSecondary).remove(str);
        if (iDanmakuFilterRemove != null) {
            iDanmakuFilterRemove.clear();
            if (z2) {
                this.mFilterArray = (IDanmakuFilter[]) this.filters.values().toArray(this.mFilterArray);
            } else {
                this.mFilterArraySecondary = (IDanmakuFilter[]) this.filtersSecondary.values().toArray(this.mFilterArraySecondary);
            }
        }
    }

    public void unregisterFilter(BaseDanmakuFilter baseDanmakuFilter) {
        this.filters.remove("2000_Primary_Custom_Filter_" + baseDanmakuFilter.hashCode());
        this.mFilterArray = (IDanmakuFilter[]) this.filters.values().toArray(this.mFilterArray);
    }

    public void registerFilter(BaseDanmakuFilter baseDanmakuFilter) {
        this.filters.put("2000_Primary_Custom_Filter_" + baseDanmakuFilter.hashCode(), baseDanmakuFilter);
        this.mFilterArray = (IDanmakuFilter[]) this.filters.values().toArray(this.mFilterArray);
    }
}
