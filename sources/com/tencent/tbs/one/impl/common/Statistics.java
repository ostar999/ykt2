package com.tencent.tbs.one.impl.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class Statistics {
    public static final String EVENT_ACTION = "TBSOneAction";
    public static final String EVENT_ERROR = "TBSOneError";
    public static final String KEY_COMPONENT_LOCAL_VERSION_CODE = "COMPONENT_LOCAL_VERSION_CODE";
    public static final String KEY_COMPONENT_NAME = "COMPONENT_NAME";
    public static final String KEY_COMPONENT_VERSION_CODE = "COMPONENT_VERSION_CODE";
    public static final String KEY_DEPS_COMPONENT_LOCV = "KEY_DEPS_COMPONENT_LOCV";
    public static final String KEY_DEPS_LOCAL_VERSION_CODE = "DEPS_LOCAL_VERSION_CODE";
    public static final String KEY_DEPS_VERSION_CODE = "DEPS_VERSION_CODE";

    /* renamed from: a, reason: collision with root package name */
    public static StatisticsProvider f21945a;

    public static class Builder {

        /* renamed from: a, reason: collision with root package name */
        public String f21946a = "";

        /* renamed from: b, reason: collision with root package name */
        public int f21947b = -1;

        /* renamed from: c, reason: collision with root package name */
        public String f21948c = "";

        /* renamed from: d, reason: collision with root package name */
        public int f21949d = -1;

        /* renamed from: e, reason: collision with root package name */
        public int f21950e = -1;

        /* renamed from: f, reason: collision with root package name */
        public int f21951f = -1;

        /* renamed from: g, reason: collision with root package name */
        public int f21952g = -1;

        /* renamed from: h, reason: collision with root package name */
        public String f21953h = "";

        public void report() {
            if (Statistics.f21945a != null) {
                HashMap map = new HashMap();
                map.put(Statistics.KEY_DEPS_VERSION_CODE, Integer.valueOf(this.f21951f));
                map.put(Statistics.KEY_DEPS_LOCAL_VERSION_CODE, Integer.valueOf(this.f21952g));
                map.put(Statistics.KEY_DEPS_COMPONENT_LOCV, this.f21953h);
                map.put(Statistics.KEY_COMPONENT_NAME, this.f21948c);
                map.put(Statistics.KEY_COMPONENT_VERSION_CODE, Integer.valueOf(this.f21949d));
                map.put(Statistics.KEY_COMPONENT_LOCAL_VERSION_CODE, Integer.valueOf(this.f21950e));
                Statistics.f21945a.reportEvent(this.f21946a, this.f21947b, map);
            }
        }

        public Builder setComponentLocalVersion(int i2) {
            this.f21950e = i2;
            return this;
        }

        public Builder setComponentName(String str) {
            this.f21948c = str;
            return this;
        }

        public Builder setComponentVersion(int i2) {
            this.f21949d = i2;
            return this;
        }

        public Builder setDEPSComponentLocalVersions(String str) {
            this.f21953h = str;
            return this;
        }

        public Builder setDEPSLocalVersion(int i2) {
            this.f21952g = i2;
            return this;
        }

        public Builder setDEPSVersion(int i2) {
            this.f21951f = i2;
            return this;
        }

        public Builder setEventCode(int i2) {
            this.f21947b = i2;
            return this;
        }

        public Builder setEventName(String str) {
            this.f21946a = str;
            return this;
        }
    }

    public interface StatisticsProvider {
        void reportEvent(String str, int i2, Map<?, ?> map);

        void reportLog();
    }

    public static Builder create(String str, int i2) {
        return new Builder().setEventName(str).setEventCode(i2);
    }

    public static void initialize(StatisticsProvider statisticsProvider) {
        f21945a = statisticsProvider;
    }

    public static void reportLog() {
        StatisticsProvider statisticsProvider = f21945a;
        if (statisticsProvider != null) {
            statisticsProvider.reportLog();
        }
    }
}
