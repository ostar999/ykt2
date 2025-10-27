package com.bumptech.glide.annotation.compiler;

import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.bumptech.glide.repackaged.com.squareup.javapoet.ClassName;
import com.bumptech.glide.repackaged.com.squareup.javapoet.MethodSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.ParameterSpec;
import com.bumptech.glide.repackaged.com.squareup.javapoet.TypeSpec;
import com.umeng.analytics.pro.d;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/* loaded from: classes2.dex */
final class RequestManagerFactoryGenerator {
    private static final ClassName CONTEXT_CLASS_NAME = ClassName.get("android.content", "Context", new String[0]);
    private final TypeElement glideType;
    private final TypeElement lifecycleType;
    private final ClassName requestManagerClassName;
    private final TypeElement requestManagerFactoryInterface;
    private final TypeElement requestManagerTreeNodeType;

    public RequestManagerFactoryGenerator(ProcessingEnvironment processingEnvironment) {
        Elements elementUtils = processingEnvironment.getElementUtils();
        this.glideType = elementUtils.getTypeElement("com.bumptech.glide.Glide");
        this.lifecycleType = elementUtils.getTypeElement("com.bumptech.glide.manager.Lifecycle");
        this.requestManagerTreeNodeType = elementUtils.getTypeElement("com.bumptech.glide.manager.RequestManagerTreeNode");
        this.requestManagerFactoryInterface = elementUtils.getTypeElement("com.bumptech.glide.manager.RequestManagerRetriever.RequestManagerFactory");
        this.requestManagerClassName = ClassName.get(elementUtils.getTypeElement("com.bumptech.glide.RequestManager"));
    }

    public TypeSpec generate(String str, TypeSpec typeSpec) {
        return TypeSpec.classBuilder("GeneratedRequestManagerFactory").addModifiers(Modifier.FINAL).addSuperinterface(ClassName.get(this.requestManagerFactoryInterface)).addJavadoc("Generated code, do not modify\n", new Object[0]).addMethod(MethodSpec.methodBuilder("build").addModifiers(Modifier.PUBLIC).addAnnotation(Override.class).addAnnotation(ProcessorUtil.nonNull()).returns(this.requestManagerClassName).addParameter(ParameterSpec.builder(ClassName.get(this.glideType), "glide", new Modifier[0]).addAnnotation(ProcessorUtil.nonNull()).build()).addParameter(ParameterSpec.builder(ClassName.get(this.lifecycleType), RequestParameters.SUBRESOURCE_LIFECYCLE, new Modifier[0]).addAnnotation(ProcessorUtil.nonNull()).build()).addParameter(ParameterSpec.builder(ClassName.get(this.requestManagerTreeNodeType), "treeNode", new Modifier[0]).addAnnotation(ProcessorUtil.nonNull()).build()).addParameter(ParameterSpec.builder(CONTEXT_CLASS_NAME, d.R, new Modifier[0]).addAnnotation(ProcessorUtil.nonNull()).build()).addStatement("return new $T(glide, lifecycle, treeNode, context)", ClassName.get(str, typeSpec.name, new String[0])).build()).build();
    }
}
