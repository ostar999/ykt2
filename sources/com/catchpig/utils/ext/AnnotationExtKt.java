package com.catchpig.utils.ext;

import androidx.exifinterface.media.ExifInterface;
import java.lang.annotation.Annotation;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\u0010\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0002\u0010\u0000\n\u0002\b\u0002\u001a \u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\u0086\b¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"annotation", ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "", "", "(Ljava/lang/Object;)Ljava/lang/annotation/Annotation;", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AnnotationExtKt {
    public static final /* synthetic */ <A extends Annotation> A annotation(Object obj) {
        Intrinsics.checkNotNullParameter(obj, "<this>");
        Class<?> cls = obj.getClass();
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_MEASUREMENT_IN_PROGRESS);
        if (cls.isAnnotationPresent(Annotation.class)) {
            return (A) cls.getAnnotation(Annotation.class);
        }
        return null;
    }
}
