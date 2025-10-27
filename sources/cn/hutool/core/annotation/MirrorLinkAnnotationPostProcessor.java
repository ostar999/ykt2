package cn.hutool.core.annotation;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;

/* loaded from: classes.dex */
public class MirrorLinkAnnotationPostProcessor extends AbstractLinkAnnotationPostProcessor {
    private static final RelationType[] PROCESSED_RELATION_TYPES = {RelationType.MIRROR_FOR};

    private void checkMirrorRelation(Link link, AnnotationAttribute annotationAttribute, AnnotationAttribute annotationAttribute2) throws Throwable {
        checkLinkedAttributeNotNull(annotationAttribute, annotationAttribute2, link);
        checkAttributeType(annotationAttribute, annotationAttribute2);
        RelationType relationType = RelationType.MIRROR_FOR;
        Link linkAnnotation = getLinkAnnotation(annotationAttribute2, relationType);
        Assert.isTrue(ObjectUtil.isNotNull(linkAnnotation) && relationType.equals(linkAnnotation.type()), "mirror attribute [{}] of original attribute [{}] must marked by @Link, and also @LinkType.type() must is [{}]", annotationAttribute2.getAttribute(), annotationAttribute.getAttribute(), relationType);
        checkLinkedSelf(annotationAttribute, annotationAttribute2);
    }

    private void checkMirrored(AnnotationAttribute annotationAttribute, AnnotationAttribute annotationAttribute2) {
        boolean z2 = annotationAttribute instanceof MirroredAnnotationAttribute;
        boolean z3 = annotationAttribute2 instanceof MirroredAnnotationAttribute;
        if (z2 && z3 && ObjectUtil.equals(((MirroredAnnotationAttribute) annotationAttribute).getLinked(), ((MirroredAnnotationAttribute) annotationAttribute2).getOriginal())) {
        } else {
            throw new IllegalArgumentException((!z2 || z3) ? (z2 || !z3) ? CharSequenceUtil.format("attribute [{}] cannot mirror for [{}], because [{}] already mirrored for [{}] and  [{}] already mirrored for [{}]", annotationAttribute2.getAttribute(), annotationAttribute.getAttribute(), annotationAttribute2.getAttribute(), ((MirroredAnnotationAttribute) annotationAttribute2).getLinked(), annotationAttribute.getAttribute(), ((MirroredAnnotationAttribute) annotationAttribute).getLinked()) : CharSequenceUtil.format("attribute [{}] cannot mirror for [{}], because it's already mirrored for [{}]", annotationAttribute2.getAttribute(), annotationAttribute.getAttribute(), ((MirroredAnnotationAttribute) annotationAttribute2).getLinked()) : CharSequenceUtil.format("attribute [{}] cannot mirror for [{}], because it's already mirrored for [{}]", annotationAttribute.getAttribute(), annotationAttribute2.getAttribute(), ((MirroredAnnotationAttribute) annotationAttribute).getLinked()));
        }
    }

    @Override // cn.hutool.core.annotation.AbstractLinkAnnotationPostProcessor, cn.hutool.core.annotation.SynthesizedAnnotationPostProcessor
    public int order() {
        return -2147483647;
    }

    @Override // cn.hutool.core.annotation.AbstractLinkAnnotationPostProcessor
    public void processLinkedAttribute(AnnotationSynthesizer annotationSynthesizer, Link link, SynthesizedAnnotation synthesizedAnnotation, AnnotationAttribute annotationAttribute, SynthesizedAnnotation synthesizedAnnotation2, AnnotationAttribute annotationAttribute2) throws Throwable {
        if ((annotationAttribute instanceof MirroredAnnotationAttribute) || (annotationAttribute2 instanceof MirroredAnnotationAttribute)) {
            checkMirrored(annotationAttribute, annotationAttribute2);
            return;
        }
        checkMirrorRelation(link, annotationAttribute, annotationAttribute2);
        synthesizedAnnotation.setAttribute(annotationAttribute.getAttributeName(), new MirroredAnnotationAttribute(annotationAttribute, annotationAttribute2));
        synthesizedAnnotation2.setAttribute(link.attribute(), new MirroredAnnotationAttribute(annotationAttribute2, annotationAttribute));
    }

    @Override // cn.hutool.core.annotation.AbstractLinkAnnotationPostProcessor
    public RelationType[] processTypes() {
        return PROCESSED_RELATION_TYPES;
    }
}
