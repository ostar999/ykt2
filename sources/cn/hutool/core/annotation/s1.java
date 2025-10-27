package cn.hutool.core.annotation;

import cn.hutool.core.comparator.CompareUtil;
import java.util.Comparator;

/* loaded from: classes.dex */
public final /* synthetic */ class s1 {
    public static int a(SynthesizedAnnotationPostProcessor synthesizedAnnotationPostProcessor, SynthesizedAnnotationPostProcessor synthesizedAnnotationPostProcessor2) {
        return CompareUtil.compare(synthesizedAnnotationPostProcessor, synthesizedAnnotationPostProcessor2, (Comparator<SynthesizedAnnotationPostProcessor>) Comparator.comparing(new d()));
    }

    public static int c(SynthesizedAnnotationPostProcessor synthesizedAnnotationPostProcessor) {
        return Integer.MAX_VALUE;
    }
}
