package com.arialyy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes2.dex */
public @interface Upload {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onNoSupportBreakPoint {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onPre {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onTaskCancel {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onTaskComplete {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onTaskFail {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onTaskResume {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onTaskRunning {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onTaskStart {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onTaskStop {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onWait {
        String[] value() default {""};
    }
}
