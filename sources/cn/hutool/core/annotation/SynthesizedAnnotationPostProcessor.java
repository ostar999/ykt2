package cn.hutool.core.annotation;

/* loaded from: classes.dex */
public interface SynthesizedAnnotationPostProcessor extends Comparable<SynthesizedAnnotationPostProcessor> {
    public static final AliasAnnotationPostProcessor ALIAS_ANNOTATION_POST_PROCESSOR = new AliasAnnotationPostProcessor();
    public static final MirrorLinkAnnotationPostProcessor MIRROR_LINK_ANNOTATION_POST_PROCESSOR = new MirrorLinkAnnotationPostProcessor();
    public static final AliasLinkAnnotationPostProcessor ALIAS_LINK_ANNOTATION_POST_PROCESSOR = new AliasLinkAnnotationPostProcessor();

    /* JADX WARN: Can't rename method to resolve collision */
    int compareTo(SynthesizedAnnotationPostProcessor synthesizedAnnotationPostProcessor);

    @Override // java.lang.Comparable
    /* bridge */ /* synthetic */ int compareTo(SynthesizedAnnotationPostProcessor synthesizedAnnotationPostProcessor);

    int order();

    void process(SynthesizedAnnotation synthesizedAnnotation, AnnotationSynthesizer annotationSynthesizer);
}
