package com.arialyy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes2.dex */
public @interface DownloadGroup {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onPre {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onSubTaskCancel {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onSubTaskComplete {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onSubTaskFail {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onSubTaskPre {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onSubTaskRunning {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onSubTaskStart {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onSubTaskStop {
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
    public @interface onTaskPre {
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
