package com.plv.beauty.api.anno;

import androidx.annotation.WorkerThread;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
@WorkerThread
/* loaded from: classes4.dex */
public @interface OpenGLThread {
}
