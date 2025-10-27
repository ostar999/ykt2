package com.plv.foundationsdk.config;

import cn.hutool.core.text.CharPool;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class PLVPlayOption {
    public static final int DECODEMODE_AVCODEC = 0;
    public static final int DECODEMODE_MEDIACODEC = 1;
    public static final String KEY_DECODEMODE = "KEY_DECODEMODE";
    public static final String KEY_FRAMEDROP = "KEY_FRAMEDROP";
    public static final String KEY_HEADAD = "KEY_HEADAD";
    public static final String KEY_HEADERS = "KEY_HEADERS";
    public static final String KEY_HOST = "KEY_HOST";
    public static final String KEY_LOADINGVIEW_DELAY = "KEY_LOADINGVIEW_DELAY";
    public static final String KEY_PLAYMODE = "KEY_PLAYMODE";
    public static final String KEY_PRELOADTIME = "KEY_PRELOADTIME";
    public static final String KEY_RECONNECTION_COUNT = "KEY_RECONNECTION_COUNT";
    public static final String KEY_TAILAD = "KEY_TAILAD";
    public static final String KEY_TEASER = "KEY_TEASER";
    public static final String KEY_TIMEOUT = "KEY_TIMEOUT";
    public static final int PLAYMODE_LIVE = 1002;
    public static final int PLAYMODE_VOD = 1001;
    private Map<String, Object> options = new HashMap();

    @Retention(RetentionPolicy.SOURCE)
    public @interface DecodeMode {
    }

    public static class HeadAdOption {
        private int headAdDuration;
        private String headAdPath;

        public HeadAdOption(String str, int i2) {
            this.headAdPath = str;
            this.headAdDuration = i2;
        }

        public int getHeadAdDuration() {
            return this.headAdDuration;
        }

        public String getHeadAdPath() {
            return this.headAdPath;
        }

        public void setHeadAdDuration(int i2) {
            this.headAdDuration = i2;
        }

        public void setHeadAdPath(String str) {
            this.headAdPath = str;
        }

        public String toString() {
            return "HeadAdOption{headAdPath='" + this.headAdPath + CharPool.SINGLE_QUOTE + ", headAdDuration=" + this.headAdDuration + '}';
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayMode {
    }

    public static class TailAdOption {
        public final int tailAdDuration;
        public final String tailAdPath;

        public TailAdOption(String str, int i2) {
            this.tailAdPath = str;
            this.tailAdDuration = i2;
        }

        public String toString() {
            return "TailAdOption{tailAdPath='" + this.tailAdPath + CharPool.SINGLE_QUOTE + ", tailAdDuration=" + this.tailAdDuration + '}';
        }
    }

    private void addDefaultOption() {
        put(KEY_DECODEMODE, 0);
        put(KEY_PLAYMODE, 1001);
        put(KEY_FRAMEDROP, 5);
        put(KEY_TIMEOUT, 15);
        put(KEY_RECONNECTION_COUNT, 0);
        put(KEY_LOADINGVIEW_DELAY, 0);
    }

    public static PLVPlayOption getDefault() {
        PLVPlayOption pLVPlayOption = new PLVPlayOption();
        pLVPlayOption.addDefaultOption();
        return pLVPlayOption;
    }

    public Object get(String str) {
        return this.options.get(str);
    }

    public Map<String, Object> getOptions() {
        return this.options;
    }

    public PLVPlayOption put(String str, Object obj) {
        this.options.put(str, obj);
        return this;
    }

    public PLVPlayOption reset() {
        this.options.clear();
        addDefaultOption();
        return this;
    }

    public String toString() {
        return "PLVPlayOption{options=" + this.options + '}';
    }
}
