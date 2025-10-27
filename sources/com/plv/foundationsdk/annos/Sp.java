package com.plv.foundationsdk.annos;

import androidx.annotation.Dimension;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Dimension(unit = 2)
@Documented
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes4.dex */
public @interface Sp {
}
