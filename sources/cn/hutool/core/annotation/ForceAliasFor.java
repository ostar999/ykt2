package cn.hutool.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Link(type = RelationType.FORCE_ALIAS_FOR)
@Documented
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes.dex */
public @interface ForceAliasFor {
    @Link(annotation = Link.class, attribute = "annotation", type = RelationType.FORCE_ALIAS_FOR)
    Class<? extends Annotation> annotation() default Annotation.class;

    @Link(annotation = Link.class, attribute = "attribute", type = RelationType.FORCE_ALIAS_FOR)
    String attribute() default "";
}
