package com.caverock.androidsvg;

import com.caverock.androidsvg.CSSParser;
import com.caverock.androidsvg.SVG;

/* loaded from: classes2.dex */
public class RenderOptions {
    CSSParser.Ruleset css;
    PreserveAspectRatio preserveAspectRatio;
    String targetId;
    SVG.Box viewBox;
    String viewId;
    SVG.Box viewPort;

    public RenderOptions() {
        this.css = null;
        this.preserveAspectRatio = null;
        this.targetId = null;
        this.viewBox = null;
        this.viewId = null;
        this.viewPort = null;
    }

    public static RenderOptions create() {
        return new RenderOptions();
    }

    public RenderOptions css(String str) {
        this.css = new CSSParser(CSSParser.Source.RenderOptions).parse(str);
        return this;
    }

    public boolean hasCss() {
        CSSParser.Ruleset ruleset = this.css;
        return ruleset != null && ruleset.ruleCount() > 0;
    }

    public boolean hasPreserveAspectRatio() {
        return this.preserveAspectRatio != null;
    }

    public boolean hasTarget() {
        return this.targetId != null;
    }

    public boolean hasView() {
        return this.viewId != null;
    }

    public boolean hasViewBox() {
        return this.viewBox != null;
    }

    public boolean hasViewPort() {
        return this.viewPort != null;
    }

    public RenderOptions preserveAspectRatio(PreserveAspectRatio preserveAspectRatio) {
        this.preserveAspectRatio = preserveAspectRatio;
        return this;
    }

    public RenderOptions target(String str) {
        this.targetId = str;
        return this;
    }

    public RenderOptions view(String str) {
        this.viewId = str;
        return this;
    }

    public RenderOptions viewBox(float f2, float f3, float f4, float f5) {
        this.viewBox = new SVG.Box(f2, f3, f4, f5);
        return this;
    }

    public RenderOptions viewPort(float f2, float f3, float f4, float f5) {
        this.viewPort = new SVG.Box(f2, f3, f4, f5);
        return this;
    }

    public RenderOptions(RenderOptions renderOptions) {
        this.css = null;
        this.preserveAspectRatio = null;
        this.targetId = null;
        this.viewBox = null;
        this.viewId = null;
        this.viewPort = null;
        if (renderOptions == null) {
            return;
        }
        this.css = renderOptions.css;
        this.preserveAspectRatio = renderOptions.preserveAspectRatio;
        this.viewBox = renderOptions.viewBox;
        this.viewId = renderOptions.viewId;
        this.viewPort = renderOptions.viewPort;
    }
}
