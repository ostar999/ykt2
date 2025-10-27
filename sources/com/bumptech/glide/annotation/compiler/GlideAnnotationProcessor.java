package com.bumptech.glide.annotation.compiler;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/* loaded from: classes2.dex */
public final class GlideAnnotationProcessor extends AbstractProcessor {
    private AppModuleProcessor appModuleProcessor;
    private ExtensionProcessor extensionProcessor;
    private boolean isGeneratedAppGlideModuleWritten;
    private LibraryModuleProcessor libraryModuleProcessor;
    private ProcessorUtil processorUtil;

    public Set<String> getSupportedAnnotationTypes() {
        HashSet hashSet = new HashSet();
        hashSet.addAll(this.libraryModuleProcessor.getSupportedAnnotationTypes());
        hashSet.addAll(this.extensionProcessor.getSupportedAnnotationTypes());
        return hashSet;
    }

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        ProcessorUtil processorUtil = new ProcessorUtil(processingEnvironment);
        this.processorUtil = processorUtil;
        IndexerGenerator indexerGenerator = new IndexerGenerator(processorUtil);
        this.libraryModuleProcessor = new LibraryModuleProcessor(this.processorUtil, indexerGenerator);
        this.appModuleProcessor = new AppModuleProcessor(processingEnvironment, this.processorUtil);
        this.extensionProcessor = new ExtensionProcessor(processingEnvironment, this.processorUtil, indexerGenerator);
    }

    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        this.processorUtil.process();
        boolean zProcessModules = this.libraryModuleProcessor.processModules(roundEnvironment);
        boolean zProcessExtensions = this.extensionProcessor.processExtensions(roundEnvironment);
        this.appModuleProcessor.processModules(set, roundEnvironment);
        if (zProcessExtensions || zProcessModules) {
            if (this.isGeneratedAppGlideModuleWritten) {
                throw new IllegalStateException("Cannot process annotations after writing AppGlideModule");
            }
            return true;
        }
        if (!this.isGeneratedAppGlideModuleWritten) {
            this.isGeneratedAppGlideModuleWritten = this.appModuleProcessor.maybeWriteAppModule();
        }
        return true;
    }
}
