package cn.hutool.core.annotation;

import cn.hutool.core.collection.CollUtil;
import java.util.Map;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public final /* synthetic */ class r1 {
    public static void a(final SynthesizedAnnotation synthesizedAnnotation, Map map) {
        if (CollUtil.isNotEmpty((Map<?, ?>) map)) {
            map.forEach(new BiConsumer() { // from class: cn.hutool.core.annotation.q1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    synthesizedAnnotation.setAttribute((String) obj, (AnnotationAttribute) obj2);
                }
            });
        }
    }
}
