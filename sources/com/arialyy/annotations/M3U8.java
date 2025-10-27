package com.arialyy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes2.dex */
public @interface M3U8 {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onPeerComplete {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onPeerFail {
        String[] value() default {""};
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface onPeerStart {
        String[] value() default {""};
    }
}
