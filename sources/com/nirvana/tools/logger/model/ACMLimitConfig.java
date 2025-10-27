package com.nirvana.tools.logger.model;

import android.text.TextUtils;
import com.nirvana.tools.jsoner.JSONUtils;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ACMLimitConfig implements Serializable {
    private boolean isLimited;
    private int limitCount;
    private int limitHours;

    public static final class Builder {
        private boolean isLimited;
        private int limitCount;
        private int limitHours;

        private Builder() {
        }

        public final ACMLimitConfig build() {
            return new ACMLimitConfig(this);
        }

        public final Builder isLimited(boolean z2) {
            this.isLimited = z2;
            return this;
        }

        public final Builder limitCount(int i2) {
            this.limitCount = i2;
            return this;
        }

        public final Builder limitHours(int i2) {
            this.limitHours = i2;
            return this;
        }
    }

    public ACMLimitConfig() {
    }

    private ACMLimitConfig(Builder builder) {
        this.isLimited = builder.isLimited;
        this.limitCount = builder.limitCount;
        this.limitHours = builder.limitHours;
    }

    public static ACMLimitConfig fromJson(String str) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        ACMLimitConfig aCMLimitConfig = new ACMLimitConfig();
        try {
            if (!TextUtils.isEmpty(str)) {
                JSONUtils.fromJson(new JSONObject(str), aCMLimitConfig, (List<Field>) null);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return aCMLimitConfig;
    }

    public static Builder newACMLimitConfig() {
        return new Builder();
    }

    public int getLimitCount() {
        return this.limitCount;
    }

    public int getLimitHours() {
        return this.limitHours;
    }

    public boolean isLimited() {
        return this.isLimited;
    }

    public void setLimitCount(int i2) {
        this.limitCount = i2;
    }

    public void setLimitHours(int i2) {
        this.limitHours = i2;
    }

    public void setLimited(boolean z2) {
        this.isLimited = z2;
    }

    public String toJsonString() {
        return JSONUtils.toJson(this, null).toString();
    }
}
