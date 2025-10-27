package master.flame.danmaku.danmaku.model.android;

/* loaded from: classes8.dex */
public class CachingPolicy {
    public static final int BMP_BPP_ARGB_4444 = 16;
    public static final int BMP_BPP_ARGB_8888 = 32;
    public static final int CACHE_PERIOD_AUTO = 0;
    public static final int CACHE_PERIOD_NOT_RECYCLE = -1;
    public static final CachingPolicy POLICY_DEFAULT;
    public static final CachingPolicy POLICY_GREEDY;
    public static final CachingPolicy POLICY_LAZY;
    public int bitsPerPixelOfCache;
    public float forceRecyleThreshold;
    public float maxCachePoolSizeFactorPercentage;
    public long periodOfRecycle;
    public int reusableOffsetPixel;
    public int maxTimesOfStrictReusableFinds = 20;
    public int maxTimesOfReusableFinds = 150;

    static {
        CachingPolicy cachingPolicy = new CachingPolicy(16, 0.3f, 0L, 50, 0.01f);
        POLICY_LAZY = cachingPolicy;
        POLICY_GREEDY = new CachingPolicy(16, 0.5f, -1L, 50, 0.005f);
        POLICY_DEFAULT = cachingPolicy;
    }

    public CachingPolicy(int i2, float f2, long j2, int i3, float f3) {
        this.maxCachePoolSizeFactorPercentage = 0.3f;
        this.periodOfRecycle = 0L;
        this.forceRecyleThreshold = 0.01f;
        this.reusableOffsetPixel = 0;
        this.bitsPerPixelOfCache = i2;
        this.bitsPerPixelOfCache = 32;
        this.maxCachePoolSizeFactorPercentage = f2;
        this.periodOfRecycle = j2;
        this.reusableOffsetPixel = i3;
        this.forceRecyleThreshold = f3;
    }
}
