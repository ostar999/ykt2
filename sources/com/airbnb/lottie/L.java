package com.airbnb.lottie;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.core.os.TraceCompat;
import cn.hutool.core.text.StrPool;
import com.airbnb.lottie.network.DefaultLottieNetworkFetcher;
import com.airbnb.lottie.network.LottieNetworkCacheProvider;
import com.airbnb.lottie.network.LottieNetworkFetcher;
import com.airbnb.lottie.network.NetworkCache;
import com.airbnb.lottie.network.NetworkFetcher;
import java.io.File;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class L {
    public static boolean DBG = false;
    private static final int MAX_DEPTH = 20;
    public static final String TAG = "LOTTIE";
    private static LottieNetworkCacheProvider cacheProvider = null;
    private static int depthPastMaxDepth = 0;
    private static LottieNetworkFetcher fetcher = null;
    private static volatile NetworkCache networkCache = null;
    private static volatile NetworkFetcher networkFetcher = null;
    private static String[] sections = null;
    private static long[] startTimeNs = null;
    private static int traceDepth = 0;
    private static boolean traceEnabled = false;

    private L() {
    }

    public static void beginSection(String str) {
        if (traceEnabled) {
            int i2 = traceDepth;
            if (i2 == 20) {
                depthPastMaxDepth++;
                return;
            }
            sections[i2] = str;
            startTimeNs[i2] = System.nanoTime();
            TraceCompat.beginSection(str);
            traceDepth++;
        }
    }

    public static float endSection(String str) {
        int i2 = depthPastMaxDepth;
        if (i2 > 0) {
            depthPastMaxDepth = i2 - 1;
            return 0.0f;
        }
        if (!traceEnabled) {
            return 0.0f;
        }
        int i3 = traceDepth - 1;
        traceDepth = i3;
        if (i3 == -1) {
            throw new IllegalStateException("Can't end trace section. There are none.");
        }
        if (str.equals(sections[i3])) {
            TraceCompat.endSection();
            return (System.nanoTime() - startTimeNs[traceDepth]) / 1000000.0f;
        }
        throw new IllegalStateException("Unbalanced trace call " + str + ". Expected " + sections[traceDepth] + StrPool.DOT);
    }

    @NonNull
    public static NetworkCache networkCache(@NonNull final Context context) {
        NetworkCache networkCache2 = networkCache;
        if (networkCache2 == null) {
            synchronized (NetworkCache.class) {
                networkCache2 = networkCache;
                if (networkCache2 == null) {
                    LottieNetworkCacheProvider lottieNetworkCacheProvider = cacheProvider;
                    if (lottieNetworkCacheProvider == null) {
                        lottieNetworkCacheProvider = new LottieNetworkCacheProvider() { // from class: com.airbnb.lottie.L.1
                            @Override // com.airbnb.lottie.network.LottieNetworkCacheProvider
                            @NonNull
                            public File getCacheDir() {
                                return new File(context.getCacheDir(), "lottie_network_cache");
                            }
                        };
                    }
                    networkCache2 = new NetworkCache(lottieNetworkCacheProvider);
                    networkCache = networkCache2;
                }
            }
        }
        return networkCache2;
    }

    @NonNull
    public static NetworkFetcher networkFetcher(@NonNull Context context) {
        NetworkFetcher networkFetcher2 = networkFetcher;
        if (networkFetcher2 == null) {
            synchronized (NetworkFetcher.class) {
                networkFetcher2 = networkFetcher;
                if (networkFetcher2 == null) {
                    NetworkCache networkCache2 = networkCache(context);
                    LottieNetworkFetcher defaultLottieNetworkFetcher = fetcher;
                    if (defaultLottieNetworkFetcher == null) {
                        defaultLottieNetworkFetcher = new DefaultLottieNetworkFetcher();
                    }
                    networkFetcher2 = new NetworkFetcher(networkCache2, defaultLottieNetworkFetcher);
                    networkFetcher = networkFetcher2;
                }
            }
        }
        return networkFetcher2;
    }

    public static void setCacheProvider(LottieNetworkCacheProvider lottieNetworkCacheProvider) {
        cacheProvider = lottieNetworkCacheProvider;
    }

    public static void setFetcher(LottieNetworkFetcher lottieNetworkFetcher) {
        fetcher = lottieNetworkFetcher;
    }

    public static void setTraceEnabled(boolean z2) {
        if (traceEnabled == z2) {
            return;
        }
        traceEnabled = z2;
        if (z2) {
            sections = new String[20];
            startTimeNs = new long[20];
        }
    }
}
