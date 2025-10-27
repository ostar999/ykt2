package com.heytap.msp.push.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class AppConfigConstant {

    @Retention(RetentionPolicy.SOURCE)
    public @interface AppConfig {
        public static final int APP_CONFIG_NOTIFICATION_SWITCH_CLOSE = 1;
        public static final int APP_CONFIG_NOTIFICATION_SWITCH_NO_DEFINE = 0;
        public static final int APP_CONFIG_NOTIFICATION_SWITCH_OPEN = 2;
    }
}
