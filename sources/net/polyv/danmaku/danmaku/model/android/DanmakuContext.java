package net.polyv.danmaku.danmaku.model.android;

import android.graphics.Typeface;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.polyv.danmaku.controller.DanmakuFilters;
import net.polyv.danmaku.danmaku.model.AbsDanmakuSync;
import net.polyv.danmaku.danmaku.model.AbsDisplayer;
import net.polyv.danmaku.danmaku.model.AlphaValue;
import net.polyv.danmaku.danmaku.model.GlobalFlagValues;
import net.polyv.danmaku.danmaku.model.IDanmakus;
import net.polyv.danmaku.danmaku.model.android.BaseCacheStuffer;

/* loaded from: classes9.dex */
public class DanmakuContext implements Cloneable {
    public AbsDanmakuSync danmakuSync;
    private IDanmakus.BaseComparator mBaseComparator;
    private BaseCacheStuffer mCacheStuffer;
    private List<WeakReference<ConfigChangedCallback>> mCallbackList;
    private boolean mIsMaxLinesLimited;
    private boolean mIsPreventOverlappingEnabled;
    public Typeface mFont = null;
    public int transparency = AlphaValue.MAX;
    public float scaleTextSize = 1.0f;
    public int margin = 0;
    public boolean FTDanmakuVisibility = true;
    public boolean FBDanmakuVisibility = true;
    public boolean L2RDanmakuVisibility = true;
    public boolean R2LDanmakuVisibility = true;
    public boolean SpecialDanmakuVisibility = true;
    List<Integer> mFilterTypes = new ArrayList();
    public int maximumNumsInScreen = -1;
    public float scrollSpeedFactor = 1.0f;
    List<Integer> mColorValueWhiteList = new ArrayList();
    List<Integer> mUserIdBlackList = new ArrayList();
    List<String> mUserHashBlackList = new ArrayList();
    private boolean mBlockGuestDanmaku = false;
    private boolean mDuplicateMergingEnable = false;
    private boolean mIsAlignBottom = false;
    public AbsDisplayer mDisplayer = new AndroidDisplayer();
    public GlobalFlagValues mGlobalFlagValues = new GlobalFlagValues();
    public DanmakuFilters mDanmakuFilters = new DanmakuFilters();
    public DanmakuFactory mDanmakuFactory = DanmakuFactory.create();
    public CachingPolicy cachingPolicy = CachingPolicy.POLICY_DEFAULT;
    public byte updateMethod = 0;
    private int mUpdateRate = 16;

    public interface ConfigChangedCallback {
        boolean onDanmakuConfigChanged(DanmakuContext danmakuContext, DanmakuConfigTag danmakuConfigTag, Object... objArr);
    }

    public enum DanmakuConfigTag {
        FT_DANMAKU_VISIBILITY,
        FB_DANMAKU_VISIBILITY,
        L2R_DANMAKU_VISIBILITY,
        R2L_DANMAKU_VISIBILIY,
        SPECIAL_DANMAKU_VISIBILITY,
        TYPEFACE,
        TRANSPARENCY,
        SCALE_TEXTSIZE,
        MAXIMUM_NUMS_IN_SCREEN,
        DANMAKU_STYLE,
        DANMAKU_BOLD,
        COLOR_VALUE_WHITE_LIST,
        USER_ID_BLACK_LIST,
        USER_HASH_BLACK_LIST,
        SCROLL_SPEED_FACTOR,
        BLOCK_GUEST_DANMAKU,
        DUPLICATE_MERGING_ENABLED,
        MAXIMUN_LINES,
        OVERLAPPING_ENABLE,
        ALIGN_BOTTOM,
        DANMAKU_MARGIN,
        DANMAKU_SYNC;

        public boolean isVisibilityRelatedTag() {
            return equals(FT_DANMAKU_VISIBILITY) || equals(FB_DANMAKU_VISIBILITY) || equals(L2R_DANMAKU_VISIBILITY) || equals(R2L_DANMAKU_VISIBILIY) || equals(SPECIAL_DANMAKU_VISIBILITY) || equals(COLOR_VALUE_WHITE_LIST) || equals(USER_ID_BLACK_LIST);
        }
    }

    public static DanmakuContext create() {
        return new DanmakuContext();
    }

    private void notifyConfigureChanged(DanmakuConfigTag danmakuConfigTag, Object... objArr) {
        List<WeakReference<ConfigChangedCallback>> list = this.mCallbackList;
        if (list != null) {
            Iterator<WeakReference<ConfigChangedCallback>> it = list.iterator();
            while (it.hasNext()) {
                ConfigChangedCallback configChangedCallback = it.next().get();
                if (configChangedCallback != null) {
                    configChangedCallback.onDanmakuConfigChanged(this, danmakuConfigTag, objArr);
                }
            }
        }
    }

    private void setDanmakuVisible(boolean z2, int i2) {
        if (z2) {
            this.mFilterTypes.remove(Integer.valueOf(i2));
        } else {
            if (this.mFilterTypes.contains(Integer.valueOf(i2))) {
                return;
            }
            this.mFilterTypes.add(Integer.valueOf(i2));
        }
    }

    private <T> void setFilterData(String str, T t2) {
        setFilterData(str, t2, true);
    }

    public DanmakuContext addUserHashBlackList(String... strArr) {
        if (strArr != null && strArr.length != 0) {
            Collections.addAll(this.mUserHashBlackList, strArr);
            setFilterData("1015_Filter", this.mUserHashBlackList);
            this.mGlobalFlagValues.updateFilterFlag();
            notifyConfigureChanged(DanmakuConfigTag.USER_HASH_BLACK_LIST, this.mUserHashBlackList);
        }
        return this;
    }

    public DanmakuContext addUserIdBlackList(Integer... numArr) {
        if (numArr != null && numArr.length != 0) {
            Collections.addAll(this.mUserIdBlackList, numArr);
            setFilterData("1014_Filter", this.mUserIdBlackList);
            this.mGlobalFlagValues.updateFilterFlag();
            notifyConfigureChanged(DanmakuConfigTag.USER_ID_BLACK_LIST, this.mUserIdBlackList);
        }
        return this;
    }

    public DanmakuContext alignBottom(boolean z2) {
        if (this.mIsAlignBottom != z2) {
            this.mIsAlignBottom = z2;
            notifyConfigureChanged(DanmakuConfigTag.ALIGN_BOTTOM, Boolean.valueOf(z2));
            this.mGlobalFlagValues.updateVisibleFlag();
        }
        return this;
    }

    public DanmakuContext blockGuestDanmaku(boolean z2) {
        if (this.mBlockGuestDanmaku != z2) {
            this.mBlockGuestDanmaku = z2;
            if (z2) {
                setFilterData("1016_Filter", Boolean.valueOf(z2));
            } else {
                this.mDanmakuFilters.unregisterFilter("1016_Filter");
            }
            this.mGlobalFlagValues.updateFilterFlag();
            notifyConfigureChanged(DanmakuConfigTag.BLOCK_GUEST_DANMAKU, Boolean.valueOf(z2));
        }
        return this;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public IDanmakus.BaseComparator getBaseComparator() {
        return this.mBaseComparator;
    }

    public List<Integer> getColorValueWhiteList() {
        return this.mColorValueWhiteList;
    }

    public AbsDisplayer getDisplayer() {
        return this.mDisplayer;
    }

    public boolean getFBDanmakuVisibility() {
        return this.FBDanmakuVisibility;
    }

    public boolean getFTDanmakuVisibility() {
        return this.FTDanmakuVisibility;
    }

    public int getFrameUpdateRate() {
        return this.mUpdateRate;
    }

    public boolean getL2RDanmakuVisibility() {
        return this.L2RDanmakuVisibility;
    }

    public boolean getR2LDanmakuVisibility() {
        return this.R2LDanmakuVisibility;
    }

    public boolean getSpecialDanmakuVisibility() {
        return this.SpecialDanmakuVisibility;
    }

    public List<String> getUserHashBlackList() {
        return this.mUserHashBlackList;
    }

    public List<Integer> getUserIdBlackList() {
        return this.mUserIdBlackList;
    }

    public boolean isAlignBottom() {
        return this.mIsAlignBottom;
    }

    public boolean isDuplicateMergingEnabled() {
        return this.mDuplicateMergingEnable;
    }

    public boolean isMaxLinesLimited() {
        return this.mIsMaxLinesLimited;
    }

    public boolean isPreventOverlappingEnabled() {
        return this.mIsPreventOverlappingEnabled;
    }

    public DanmakuContext preventOverlapping(Map<Integer, Boolean> map) {
        this.mIsPreventOverlappingEnabled = map != null;
        if (map == null) {
            this.mDanmakuFilters.unregisterFilter("1019_Filter", false);
        } else {
            setFilterData("1019_Filter", map, false);
        }
        this.mGlobalFlagValues.updateFilterFlag();
        notifyConfigureChanged(DanmakuConfigTag.OVERLAPPING_ENABLE, map);
        return this;
    }

    public void registerConfigChangedCallback(ConfigChangedCallback configChangedCallback) {
        if (configChangedCallback == null || this.mCallbackList == null) {
            this.mCallbackList = Collections.synchronizedList(new ArrayList());
        }
        Iterator<WeakReference<ConfigChangedCallback>> it = this.mCallbackList.iterator();
        while (it.hasNext()) {
            if (configChangedCallback.equals(it.next().get())) {
                return;
            }
        }
        this.mCallbackList.add(new WeakReference<>(configChangedCallback));
    }

    public DanmakuContext registerFilter(DanmakuFilters.BaseDanmakuFilter baseDanmakuFilter) {
        this.mDanmakuFilters.registerFilter(baseDanmakuFilter);
        this.mGlobalFlagValues.updateFilterFlag();
        return this;
    }

    public DanmakuContext removeUserHashBlackList(String... strArr) {
        if (strArr != null && strArr.length != 0) {
            for (String str : strArr) {
                this.mUserHashBlackList.remove(str);
            }
            setFilterData("1015_Filter", this.mUserHashBlackList);
            this.mGlobalFlagValues.updateFilterFlag();
            notifyConfigureChanged(DanmakuConfigTag.USER_HASH_BLACK_LIST, this.mUserHashBlackList);
        }
        return this;
    }

    public DanmakuContext removeUserIdBlackList(Integer... numArr) {
        if (numArr != null && numArr.length != 0) {
            for (Integer num : numArr) {
                this.mUserIdBlackList.remove(num);
            }
            setFilterData("1014_Filter", this.mUserIdBlackList);
            this.mGlobalFlagValues.updateFilterFlag();
            notifyConfigureChanged(DanmakuConfigTag.USER_ID_BLACK_LIST, this.mUserIdBlackList);
        }
        return this;
    }

    public DanmakuContext resetContext() {
        this.mDisplayer = new AndroidDisplayer();
        this.mGlobalFlagValues = new GlobalFlagValues();
        this.mDanmakuFilters.clear();
        this.mDanmakuFactory = DanmakuFactory.create();
        return this;
    }

    public void setBaseComparator(IDanmakus.BaseComparator baseComparator) {
        this.mBaseComparator = baseComparator;
    }

    public DanmakuContext setCacheStuffer(BaseCacheStuffer baseCacheStuffer, BaseCacheStuffer.Proxy proxy) {
        this.mCacheStuffer = baseCacheStuffer;
        if (baseCacheStuffer != null) {
            baseCacheStuffer.setProxy(proxy);
            this.mDisplayer.setCacheStuffer(this.mCacheStuffer);
        }
        return this;
    }

    public DanmakuContext setCachingPolicy(CachingPolicy cachingPolicy) {
        this.cachingPolicy = cachingPolicy;
        return this;
    }

    public DanmakuContext setColorValueWhiteList(Integer... numArr) {
        this.mColorValueWhiteList.clear();
        if (numArr == null || numArr.length == 0) {
            this.mDanmakuFilters.unregisterFilter("1013_Filter");
        } else {
            Collections.addAll(this.mColorValueWhiteList, numArr);
            setFilterData("1013_Filter", this.mColorValueWhiteList);
        }
        this.mGlobalFlagValues.updateFilterFlag();
        notifyConfigureChanged(DanmakuConfigTag.COLOR_VALUE_WHITE_LIST, this.mColorValueWhiteList);
        return this;
    }

    public DanmakuContext setDanmakuBold(boolean z2) {
        this.mDisplayer.setFakeBoldText(z2);
        notifyConfigureChanged(DanmakuConfigTag.DANMAKU_BOLD, Boolean.valueOf(z2));
        return this;
    }

    public DanmakuContext setDanmakuMargin(int i2) {
        if (this.margin != i2) {
            this.margin = i2;
            this.mDisplayer.setMargin(i2);
            this.mGlobalFlagValues.updateFilterFlag();
            this.mGlobalFlagValues.updateVisibleFlag();
            notifyConfigureChanged(DanmakuConfigTag.DANMAKU_MARGIN, Integer.valueOf(i2));
        }
        return this;
    }

    public DanmakuContext setDanmakuStyle(int i2, float... fArr) {
        this.mDisplayer.setDanmakuStyle(i2, fArr);
        notifyConfigureChanged(DanmakuConfigTag.DANMAKU_STYLE, Integer.valueOf(i2), fArr);
        return this;
    }

    public DanmakuContext setDanmakuSync(AbsDanmakuSync absDanmakuSync) {
        this.danmakuSync = absDanmakuSync;
        return this;
    }

    public DanmakuContext setDanmakuTransparency(float f2) {
        int i2 = (int) (AlphaValue.MAX * f2);
        if (i2 != this.transparency) {
            this.transparency = i2;
            this.mDisplayer.setTransparency(i2);
            notifyConfigureChanged(DanmakuConfigTag.TRANSPARENCY, Float.valueOf(f2));
        }
        return this;
    }

    public DanmakuContext setDuplicateMergingEnabled(boolean z2) {
        if (this.mDuplicateMergingEnable != z2) {
            this.mDuplicateMergingEnable = z2;
            this.mGlobalFlagValues.updateFilterFlag();
            notifyConfigureChanged(DanmakuConfigTag.DUPLICATE_MERGING_ENABLED, Boolean.valueOf(z2));
        }
        return this;
    }

    public DanmakuContext setFBDanmakuVisibility(boolean z2) {
        setDanmakuVisible(z2, 4);
        setFilterData("1010_Filter", this.mFilterTypes);
        this.mGlobalFlagValues.updateFilterFlag();
        if (this.FBDanmakuVisibility != z2) {
            this.FBDanmakuVisibility = z2;
            notifyConfigureChanged(DanmakuConfigTag.FB_DANMAKU_VISIBILITY, Boolean.valueOf(z2));
        }
        return this;
    }

    public DanmakuContext setFTDanmakuVisibility(boolean z2) {
        setDanmakuVisible(z2, 5);
        setFilterData("1010_Filter", this.mFilterTypes);
        this.mGlobalFlagValues.updateFilterFlag();
        if (this.FTDanmakuVisibility != z2) {
            this.FTDanmakuVisibility = z2;
            notifyConfigureChanged(DanmakuConfigTag.FT_DANMAKU_VISIBILITY, Boolean.valueOf(z2));
        }
        return this;
    }

    public void setFrameUpateRate(int i2) {
        this.mUpdateRate = i2;
    }

    public DanmakuContext setL2RDanmakuVisibility(boolean z2) {
        setDanmakuVisible(z2, 6);
        setFilterData("1010_Filter", this.mFilterTypes);
        this.mGlobalFlagValues.updateFilterFlag();
        if (this.L2RDanmakuVisibility != z2) {
            this.L2RDanmakuVisibility = z2;
            notifyConfigureChanged(DanmakuConfigTag.L2R_DANMAKU_VISIBILITY, Boolean.valueOf(z2));
        }
        return this;
    }

    public DanmakuContext setMarginTop(int i2) {
        this.mDisplayer.setAllMarginTop(i2);
        return this;
    }

    public DanmakuContext setMaximumLines(Map<Integer, Integer> map) {
        this.mIsMaxLinesLimited = map != null;
        if (map == null) {
            this.mDanmakuFilters.unregisterFilter("1018_Filter", false);
        } else {
            setFilterData("1018_Filter", map, false);
        }
        this.mGlobalFlagValues.updateFilterFlag();
        notifyConfigureChanged(DanmakuConfigTag.MAXIMUN_LINES, map);
        return this;
    }

    public DanmakuContext setMaximumVisibleSizeInScreen(int i2) {
        this.maximumNumsInScreen = i2;
        if (i2 == 0) {
            this.mDanmakuFilters.unregisterFilter("1011_Filter");
            this.mDanmakuFilters.unregisterFilter("1012_Filter");
            notifyConfigureChanged(DanmakuConfigTag.MAXIMUM_NUMS_IN_SCREEN, Integer.valueOf(i2));
            return this;
        }
        if (i2 == -1) {
            this.mDanmakuFilters.unregisterFilter("1011_Filter");
            this.mDanmakuFilters.registerFilter("1012_Filter");
            notifyConfigureChanged(DanmakuConfigTag.MAXIMUM_NUMS_IN_SCREEN, Integer.valueOf(i2));
            return this;
        }
        setFilterData("1011_Filter", Integer.valueOf(i2));
        this.mGlobalFlagValues.updateFilterFlag();
        notifyConfigureChanged(DanmakuConfigTag.MAXIMUM_NUMS_IN_SCREEN, Integer.valueOf(i2));
        return this;
    }

    @Deprecated
    public DanmakuContext setOverlapping(Map<Integer, Boolean> map) {
        return preventOverlapping(map);
    }

    public DanmakuContext setR2LDanmakuVisibility(boolean z2) {
        setDanmakuVisible(z2, 1);
        setFilterData("1010_Filter", this.mFilterTypes);
        this.mGlobalFlagValues.updateFilterFlag();
        if (this.R2LDanmakuVisibility != z2) {
            this.R2LDanmakuVisibility = z2;
            notifyConfigureChanged(DanmakuConfigTag.R2L_DANMAKU_VISIBILIY, Boolean.valueOf(z2));
        }
        return this;
    }

    public DanmakuContext setScaleTextSize(float f2) {
        if (this.scaleTextSize != f2) {
            this.scaleTextSize = f2;
            this.mDisplayer.clearTextHeightCache();
            this.mDisplayer.setScaleTextSizeFactor(f2);
            this.mGlobalFlagValues.updateMeasureFlag();
            this.mGlobalFlagValues.updateVisibleFlag();
            notifyConfigureChanged(DanmakuConfigTag.SCALE_TEXTSIZE, Float.valueOf(f2));
        }
        return this;
    }

    public DanmakuContext setScrollSpeedFactor(float f2) {
        if (this.scrollSpeedFactor != f2) {
            this.scrollSpeedFactor = f2;
            this.mDanmakuFactory.updateDurationFactor(f2);
            this.mGlobalFlagValues.updateMeasureFlag();
            this.mGlobalFlagValues.updateVisibleFlag();
            notifyConfigureChanged(DanmakuConfigTag.SCROLL_SPEED_FACTOR, Float.valueOf(f2));
        }
        return this;
    }

    public DanmakuContext setSpecialDanmakuVisibility(boolean z2) {
        setDanmakuVisible(z2, 7);
        setFilterData("1010_Filter", this.mFilterTypes);
        this.mGlobalFlagValues.updateFilterFlag();
        if (this.SpecialDanmakuVisibility != z2) {
            this.SpecialDanmakuVisibility = z2;
            notifyConfigureChanged(DanmakuConfigTag.SPECIAL_DANMAKU_VISIBILITY, Boolean.valueOf(z2));
        }
        return this;
    }

    public DanmakuContext setTypeface(Typeface typeface) {
        if (this.mFont != typeface) {
            this.mFont = typeface;
            this.mDisplayer.clearTextHeightCache();
            this.mDisplayer.setTypeFace(typeface);
            notifyConfigureChanged(DanmakuConfigTag.TYPEFACE, new Object[0]);
        }
        return this;
    }

    public DanmakuContext setUserHashBlackList(String... strArr) {
        this.mUserHashBlackList.clear();
        if (strArr == null || strArr.length == 0) {
            this.mDanmakuFilters.unregisterFilter("1015_Filter");
        } else {
            Collections.addAll(this.mUserHashBlackList, strArr);
            setFilterData("1015_Filter", this.mUserHashBlackList);
        }
        this.mGlobalFlagValues.updateFilterFlag();
        notifyConfigureChanged(DanmakuConfigTag.USER_HASH_BLACK_LIST, this.mUserHashBlackList);
        return this;
    }

    public DanmakuContext setUserIdBlackList(Integer... numArr) {
        this.mUserIdBlackList.clear();
        if (numArr == null || numArr.length == 0) {
            this.mDanmakuFilters.unregisterFilter("1014_Filter");
        } else {
            Collections.addAll(this.mUserIdBlackList, numArr);
            setFilterData("1014_Filter", this.mUserIdBlackList);
        }
        this.mGlobalFlagValues.updateFilterFlag();
        notifyConfigureChanged(DanmakuConfigTag.USER_ID_BLACK_LIST, this.mUserIdBlackList);
        return this;
    }

    public void unregisterAllConfigChangedCallbacks() {
        List<WeakReference<ConfigChangedCallback>> list = this.mCallbackList;
        if (list != null) {
            list.clear();
            this.mCallbackList = null;
        }
    }

    public void unregisterConfigChangedCallback(ConfigChangedCallback configChangedCallback) {
        List<WeakReference<ConfigChangedCallback>> list;
        if (configChangedCallback == null || (list = this.mCallbackList) == null) {
            return;
        }
        Iterator<WeakReference<ConfigChangedCallback>> it = list.iterator();
        while (it.hasNext()) {
            if (configChangedCallback.equals(it.next().get())) {
                this.mCallbackList.remove(configChangedCallback);
                return;
            }
        }
    }

    public DanmakuContext unregisterFilter(DanmakuFilters.BaseDanmakuFilter baseDanmakuFilter) {
        this.mDanmakuFilters.unregisterFilter(baseDanmakuFilter);
        this.mGlobalFlagValues.updateFilterFlag();
        return this;
    }

    private <T> void setFilterData(String str, T t2, boolean z2) {
        this.mDanmakuFilters.get(str, z2).setData(t2);
    }
}
