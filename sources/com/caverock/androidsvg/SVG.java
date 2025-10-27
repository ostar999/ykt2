package com.caverock.androidsvg;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.RectF;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.caverock.androidsvg.CSSParser;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class SVG {
    private static final int DEFAULT_PICTURE_HEIGHT = 512;
    private static final int DEFAULT_PICTURE_WIDTH = 512;
    private static final long SPECIFIED_ALL = -1;
    static final long SPECIFIED_CLIP = 1048576;
    static final long SPECIFIED_CLIP_PATH = 268435456;
    static final long SPECIFIED_CLIP_RULE = 536870912;
    static final long SPECIFIED_COLOR = 4096;
    static final long SPECIFIED_DIRECTION = 68719476736L;
    static final long SPECIFIED_DISPLAY = 16777216;
    static final long SPECIFIED_FILL = 1;
    static final long SPECIFIED_FILL_OPACITY = 4;
    static final long SPECIFIED_FILL_RULE = 2;
    static final long SPECIFIED_FONT_FAMILY = 8192;
    static final long SPECIFIED_FONT_SIZE = 16384;
    static final long SPECIFIED_FONT_STYLE = 65536;
    static final long SPECIFIED_FONT_WEIGHT = 32768;
    static final long SPECIFIED_IMAGE_RENDERING = 137438953472L;
    static final long SPECIFIED_MARKER_END = 8388608;
    static final long SPECIFIED_MARKER_MID = 4194304;
    static final long SPECIFIED_MARKER_START = 2097152;
    static final long SPECIFIED_MASK = 1073741824;
    static final long SPECIFIED_OPACITY = 2048;
    static final long SPECIFIED_OVERFLOW = 524288;
    static final long SPECIFIED_SOLID_COLOR = 2147483648L;
    static final long SPECIFIED_SOLID_OPACITY = 4294967296L;
    static final long SPECIFIED_STOP_COLOR = 67108864;
    static final long SPECIFIED_STOP_OPACITY = 134217728;
    static final long SPECIFIED_STROKE = 8;
    static final long SPECIFIED_STROKE_DASHARRAY = 512;
    static final long SPECIFIED_STROKE_DASHOFFSET = 1024;
    static final long SPECIFIED_STROKE_LINECAP = 64;
    static final long SPECIFIED_STROKE_LINEJOIN = 128;
    static final long SPECIFIED_STROKE_MITERLIMIT = 256;
    static final long SPECIFIED_STROKE_OPACITY = 16;
    static final long SPECIFIED_STROKE_WIDTH = 32;
    static final long SPECIFIED_TEXT_ANCHOR = 262144;
    static final long SPECIFIED_TEXT_DECORATION = 131072;
    static final long SPECIFIED_VECTOR_EFFECT = 34359738368L;
    static final long SPECIFIED_VIEWPORT_FILL = 8589934592L;
    static final long SPECIFIED_VIEWPORT_FILL_OPACITY = 17179869184L;
    static final long SPECIFIED_VISIBILITY = 33554432;
    private static final double SQRT2 = 1.414213562373095d;
    private static final String VERSION = "1.4";
    private static boolean enableInternalEntities = true;
    private static SVGExternalFileResolver externalFileResolver;
    private Svg rootElement = null;
    private String title = "";
    private String desc = "";
    private float renderDPI = 96.0f;
    private CSSParser.Ruleset cssRules = new CSSParser.Ruleset();
    private Map<String, SvgElementBase> idToElementMap = new HashMap();

    /* renamed from: com.caverock.androidsvg.SVG$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$caverock$androidsvg$SVG$Unit;

        static {
            int[] iArr = new int[Unit.values().length];
            $SwitchMap$com$caverock$androidsvg$SVG$Unit = iArr;
            try {
                iArr[Unit.px.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.em.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.ex.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.in.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.cm.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.mm.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.pt.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.pc.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.percent.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public static class CSSClipRect {
        Length bottom;
        Length left;
        Length right;

        /* renamed from: top, reason: collision with root package name */
        Length f6304top;

        public CSSClipRect(Length length, Length length2, Length length3, Length length4) {
            this.f6304top = length;
            this.right = length2;
            this.bottom = length3;
            this.left = length4;
        }
    }

    public static class Circle extends GraphicsElement {
        Length cx;
        Length cy;

        /* renamed from: r, reason: collision with root package name */
        Length f6305r;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return TtmlNode.TEXT_EMPHASIS_MARK_CIRCLE;
        }
    }

    public static class ClipPath extends Group implements NotDirectlyRendered {
        Boolean clipPathUnitsAreUser;

        @Override // com.caverock.androidsvg.SVG.Group, com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "clipPath";
        }
    }

    public static class Colour extends SvgPaint {
        static final Colour BLACK = new Colour(-16777216);
        static final Colour TRANSPARENT = new Colour(0);
        int colour;

        public Colour(int i2) {
            this.colour = i2;
        }

        public String toString() {
            return String.format("#%08x", Integer.valueOf(this.colour));
        }
    }

    public static class CurrentColor extends SvgPaint {
        private static CurrentColor instance = new CurrentColor();

        private CurrentColor() {
        }

        public static CurrentColor getInstance() {
            return instance;
        }
    }

    public static class Defs extends Group implements NotDirectlyRendered {
        @Override // com.caverock.androidsvg.SVG.Group, com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "defs";
        }
    }

    public static class Ellipse extends GraphicsElement {
        Length cx;
        Length cy;
        Length rx;
        Length ry;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "ellipse";
        }
    }

    public static abstract class GradientElement extends SvgElementBase implements SvgContainer {
        List<SvgObject> children = new ArrayList();
        Matrix gradientTransform;
        Boolean gradientUnitsAreUser;
        String href;
        GradientSpread spreadMethod;

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public void addChild(SvgObject svgObject) throws SVGParseException {
            if (svgObject instanceof Stop) {
                this.children.add(svgObject);
                return;
            }
            throw new SVGParseException("Gradient elements cannot contain " + svgObject + " elements.");
        }

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public List<SvgObject> getChildren() {
            return this.children;
        }
    }

    public enum GradientSpread {
        pad,
        reflect,
        repeat
    }

    public static abstract class GraphicsElement extends SvgConditionalElement implements HasTransform {
        Matrix transform;

        @Override // com.caverock.androidsvg.SVG.HasTransform
        public void setTransform(Matrix matrix) {
            this.transform = matrix;
        }
    }

    public static class Group extends SvgConditionalContainer implements HasTransform {
        Matrix transform;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "group";
        }

        @Override // com.caverock.androidsvg.SVG.HasTransform
        public void setTransform(Matrix matrix) {
            this.transform = matrix;
        }
    }

    public interface HasTransform {
        void setTransform(Matrix matrix);
    }

    public static class Image extends SvgPreserveAspectRatioContainer implements HasTransform {
        Length height;
        String href;
        Matrix transform;
        Length width;

        /* renamed from: x, reason: collision with root package name */
        Length f6306x;

        /* renamed from: y, reason: collision with root package name */
        Length f6307y;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "image";
        }

        @Override // com.caverock.androidsvg.SVG.HasTransform
        public void setTransform(Matrix matrix) {
            this.transform = matrix;
        }
    }

    public static class Length implements Cloneable {
        Unit unit;
        float value;

        public Length(float f2, Unit unit) {
            this.value = f2;
            this.unit = unit;
        }

        public float floatValue() {
            return this.value;
        }

        public float floatValueX(SVGAndroidRenderer sVGAndroidRenderer) {
            switch (AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVG$Unit[this.unit.ordinal()]) {
                case 9:
                    Box currentViewPortInUserUnits = sVGAndroidRenderer.getCurrentViewPortInUserUnits();
                    if (currentViewPortInUserUnits != null) {
                        break;
                    } else {
                        break;
                    }
            }
            return this.value;
        }

        public float floatValueY(SVGAndroidRenderer sVGAndroidRenderer) {
            if (this.unit != Unit.percent) {
                return floatValueX(sVGAndroidRenderer);
            }
            Box currentViewPortInUserUnits = sVGAndroidRenderer.getCurrentViewPortInUserUnits();
            return currentViewPortInUserUnits == null ? this.value : (this.value * currentViewPortInUserUnits.height) / 100.0f;
        }

        public boolean isNegative() {
            return this.value < 0.0f;
        }

        public boolean isZero() {
            return this.value == 0.0f;
        }

        public String toString() {
            return String.valueOf(this.value) + this.unit;
        }

        public float floatValue(SVGAndroidRenderer sVGAndroidRenderer) {
            if (this.unit != Unit.percent) {
                return floatValueX(sVGAndroidRenderer);
            }
            Box currentViewPortInUserUnits = sVGAndroidRenderer.getCurrentViewPortInUserUnits();
            if (currentViewPortInUserUnits == null) {
                return this.value;
            }
            float f2 = currentViewPortInUserUnits.width;
            if (f2 == currentViewPortInUserUnits.height) {
                return (this.value * f2) / 100.0f;
            }
            return (this.value * ((float) (Math.sqrt((f2 * f2) + (r6 * r6)) / SVG.SQRT2))) / 100.0f;
        }

        public Length(float f2) {
            this.value = f2;
            this.unit = Unit.px;
        }

        public float floatValue(SVGAndroidRenderer sVGAndroidRenderer, float f2) {
            if (this.unit == Unit.percent) {
                return (this.value * f2) / 100.0f;
            }
            return floatValueX(sVGAndroidRenderer);
        }

        public float floatValue(float f2) {
            int i2 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVG$Unit[this.unit.ordinal()];
            if (i2 != 1) {
                switch (i2) {
                    case 4:
                        return this.value * f2;
                    case 5:
                        return (this.value * f2) / 2.54f;
                    case 6:
                        return (this.value * f2) / 25.4f;
                    case 7:
                        return (this.value * f2) / 72.0f;
                    case 8:
                        return (this.value * f2) / 6.0f;
                    default:
                        return this.value;
                }
            }
            return this.value;
        }
    }

    public static class Line extends GraphicsElement {

        /* renamed from: x1, reason: collision with root package name */
        Length f6308x1;
        Length x2;

        /* renamed from: y1, reason: collision with root package name */
        Length f6309y1;
        Length y2;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return PLVDocumentMarkToolType.BRUSH;
        }
    }

    public static class Marker extends SvgViewBoxContainer implements NotDirectlyRendered {
        Length markerHeight;
        boolean markerUnitsAreUser;
        Length markerWidth;
        Float orient;
        Length refX;
        Length refY;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return RequestParameters.MARKER;
        }
    }

    public static class Mask extends SvgConditionalContainer implements NotDirectlyRendered {
        Length height;
        Boolean maskContentUnitsAreUser;
        Boolean maskUnitsAreUser;
        Length width;

        /* renamed from: x, reason: collision with root package name */
        Length f6310x;

        /* renamed from: y, reason: collision with root package name */
        Length f6311y;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "mask";
        }
    }

    public interface NotDirectlyRendered {
    }

    public static class PaintReference extends SvgPaint {
        SvgPaint fallback;
        String href;

        public PaintReference(String str, SvgPaint svgPaint) {
            this.href = str;
            this.fallback = svgPaint;
        }

        public String toString() {
            return this.href + " " + this.fallback;
        }
    }

    public static class Path extends GraphicsElement {

        /* renamed from: d, reason: collision with root package name */
        PathDefinition f6312d;
        Float pathLength;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "path";
        }
    }

    public static class PathDefinition implements PathInterface {
        private static final byte ARCTO = 4;
        private static final byte CLOSE = 8;
        private static final byte CUBICTO = 2;
        private static final byte LINETO = 1;
        private static final byte MOVETO = 0;
        private static final byte QUADTO = 3;
        private int commandsLength = 0;
        private int coordsLength = 0;
        private byte[] commands = new byte[8];
        private float[] coords = new float[16];

        private void addCommand(byte b3) {
            int i2 = this.commandsLength;
            byte[] bArr = this.commands;
            if (i2 == bArr.length) {
                byte[] bArr2 = new byte[bArr.length * 2];
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                this.commands = bArr2;
            }
            byte[] bArr3 = this.commands;
            int i3 = this.commandsLength;
            this.commandsLength = i3 + 1;
            bArr3[i3] = b3;
        }

        private void coordsEnsure(int i2) {
            float[] fArr = this.coords;
            if (fArr.length < this.coordsLength + i2) {
                float[] fArr2 = new float[fArr.length * 2];
                System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
                this.coords = fArr2;
            }
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void arcTo(float f2, float f3, float f4, boolean z2, boolean z3, float f5, float f6) {
            addCommand((byte) ((z2 ? 2 : 0) | 4 | (z3 ? 1 : 0)));
            coordsEnsure(5);
            float[] fArr = this.coords;
            int i2 = this.coordsLength;
            int i3 = i2 + 1;
            fArr[i2] = f2;
            int i4 = i3 + 1;
            fArr[i3] = f3;
            int i5 = i4 + 1;
            fArr[i4] = f4;
            int i6 = i5 + 1;
            fArr[i5] = f5;
            this.coordsLength = i6 + 1;
            fArr[i6] = f6;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void close() {
            addCommand((byte) 8);
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void cubicTo(float f2, float f3, float f4, float f5, float f6, float f7) {
            addCommand((byte) 2);
            coordsEnsure(6);
            float[] fArr = this.coords;
            int i2 = this.coordsLength;
            int i3 = i2 + 1;
            fArr[i2] = f2;
            int i4 = i3 + 1;
            fArr[i3] = f3;
            int i5 = i4 + 1;
            fArr[i4] = f4;
            int i6 = i5 + 1;
            fArr[i5] = f5;
            int i7 = i6 + 1;
            fArr[i6] = f6;
            this.coordsLength = i7 + 1;
            fArr[i7] = f7;
        }

        public void enumeratePath(PathInterface pathInterface) {
            int i2;
            int i3 = 0;
            for (int i4 = 0; i4 < this.commandsLength; i4++) {
                byte b3 = this.commands[i4];
                if (b3 == 0) {
                    float[] fArr = this.coords;
                    int i5 = i3 + 1;
                    i2 = i5 + 1;
                    pathInterface.moveTo(fArr[i3], fArr[i5]);
                } else if (b3 != 1) {
                    if (b3 == 2) {
                        float[] fArr2 = this.coords;
                        int i6 = i3 + 1;
                        float f2 = fArr2[i3];
                        int i7 = i6 + 1;
                        float f3 = fArr2[i6];
                        int i8 = i7 + 1;
                        float f4 = fArr2[i7];
                        int i9 = i8 + 1;
                        float f5 = fArr2[i8];
                        int i10 = i9 + 1;
                        float f6 = fArr2[i9];
                        i3 = i10 + 1;
                        pathInterface.cubicTo(f2, f3, f4, f5, f6, fArr2[i10]);
                    } else if (b3 == 3) {
                        float[] fArr3 = this.coords;
                        int i11 = i3 + 1;
                        int i12 = i11 + 1;
                        int i13 = i12 + 1;
                        pathInterface.quadTo(fArr3[i3], fArr3[i11], fArr3[i12], fArr3[i13]);
                        i3 = i13 + 1;
                    } else if (b3 != 8) {
                        boolean z2 = (b3 & 2) != 0;
                        boolean z3 = (b3 & 1) != 0;
                        float[] fArr4 = this.coords;
                        int i14 = i3 + 1;
                        float f7 = fArr4[i3];
                        int i15 = i14 + 1;
                        float f8 = fArr4[i14];
                        int i16 = i15 + 1;
                        float f9 = fArr4[i15];
                        int i17 = i16 + 1;
                        pathInterface.arcTo(f7, f8, f9, z2, z3, fArr4[i16], fArr4[i17]);
                        i3 = i17 + 1;
                    } else {
                        pathInterface.close();
                    }
                } else {
                    float[] fArr5 = this.coords;
                    int i18 = i3 + 1;
                    i2 = i18 + 1;
                    pathInterface.lineTo(fArr5[i3], fArr5[i18]);
                }
                i3 = i2;
            }
        }

        public boolean isEmpty() {
            return this.commandsLength == 0;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void lineTo(float f2, float f3) {
            addCommand((byte) 1);
            coordsEnsure(2);
            float[] fArr = this.coords;
            int i2 = this.coordsLength;
            int i3 = i2 + 1;
            fArr[i2] = f2;
            this.coordsLength = i3 + 1;
            fArr[i3] = f3;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void moveTo(float f2, float f3) {
            addCommand((byte) 0);
            coordsEnsure(2);
            float[] fArr = this.coords;
            int i2 = this.coordsLength;
            int i3 = i2 + 1;
            fArr[i2] = f2;
            this.coordsLength = i3 + 1;
            fArr[i3] = f3;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void quadTo(float f2, float f3, float f4, float f5) {
            addCommand((byte) 3);
            coordsEnsure(4);
            float[] fArr = this.coords;
            int i2 = this.coordsLength;
            int i3 = i2 + 1;
            fArr[i2] = f2;
            int i4 = i3 + 1;
            fArr[i3] = f3;
            int i5 = i4 + 1;
            fArr[i4] = f4;
            this.coordsLength = i5 + 1;
            fArr[i5] = f5;
        }
    }

    public interface PathInterface {
        void arcTo(float f2, float f3, float f4, boolean z2, boolean z3, float f5, float f6);

        void close();

        void cubicTo(float f2, float f3, float f4, float f5, float f6, float f7);

        void lineTo(float f2, float f3);

        void moveTo(float f2, float f3);

        void quadTo(float f2, float f3, float f4, float f5);
    }

    public static class Pattern extends SvgViewBoxContainer implements NotDirectlyRendered {
        Length height;
        String href;
        Boolean patternContentUnitsAreUser;
        Matrix patternTransform;
        Boolean patternUnitsAreUser;
        Length width;

        /* renamed from: x, reason: collision with root package name */
        Length f6313x;

        /* renamed from: y, reason: collision with root package name */
        Length f6314y;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "pattern";
        }
    }

    public static class PolyLine extends GraphicsElement {
        float[] points;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "polyline";
        }
    }

    public static class Polygon extends PolyLine {
        @Override // com.caverock.androidsvg.SVG.PolyLine, com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "polygon";
        }
    }

    public static class Rect extends GraphicsElement {
        Length height;
        Length rx;
        Length ry;
        Length width;

        /* renamed from: x, reason: collision with root package name */
        Length f6315x;

        /* renamed from: y, reason: collision with root package name */
        Length f6316y;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "rect";
        }
    }

    public static class SolidColor extends SvgElementBase implements SvgContainer {
        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public void addChild(SvgObject svgObject) {
        }

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public List<SvgObject> getChildren() {
            return Collections.emptyList();
        }

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "solidColor";
        }
    }

    public static class Stop extends SvgElementBase implements SvgContainer {
        Float offset;

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public void addChild(SvgObject svgObject) {
        }

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public List<SvgObject> getChildren() {
            return Collections.emptyList();
        }

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "stop";
        }
    }

    public static class Style implements Cloneable {
        static final int FONT_WEIGHT_BOLD = 700;
        static final int FONT_WEIGHT_BOLDER = 1;
        static final int FONT_WEIGHT_LIGHTER = -1;
        static final int FONT_WEIGHT_NORMAL = 400;
        CSSClipRect clip;
        String clipPath;
        FillRule clipRule;
        Colour color;
        TextDirection direction;
        Boolean display;
        SvgPaint fill;
        Float fillOpacity;
        FillRule fillRule;
        List<String> fontFamily;
        Length fontSize;
        FontStyle fontStyle;
        Integer fontWeight;
        RenderQuality imageRendering;
        String markerEnd;
        String markerMid;
        String markerStart;
        String mask;
        Float opacity;
        Boolean overflow;
        SvgPaint solidColor;
        Float solidOpacity;
        long specifiedFlags = 0;
        SvgPaint stopColor;
        Float stopOpacity;
        SvgPaint stroke;
        Length[] strokeDashArray;
        Length strokeDashOffset;
        LineCap strokeLineCap;
        LineJoin strokeLineJoin;
        Float strokeMiterLimit;
        Float strokeOpacity;
        Length strokeWidth;
        TextAnchor textAnchor;
        TextDecoration textDecoration;
        VectorEffect vectorEffect;
        SvgPaint viewportFill;
        Float viewportFillOpacity;
        Boolean visibility;

        public enum FillRule {
            NonZero,
            EvenOdd
        }

        public enum FontStyle {
            Normal,
            Italic,
            Oblique
        }

        public enum LineCap {
            Butt,
            Round,
            Square
        }

        public enum LineJoin {
            Miter,
            Round,
            Bevel
        }

        public enum RenderQuality {
            auto,
            optimizeQuality,
            optimizeSpeed
        }

        public enum TextAnchor {
            Start,
            Middle,
            End
        }

        public enum TextDecoration {
            None,
            Underline,
            Overline,
            LineThrough,
            Blink
        }

        public enum TextDirection {
            LTR,
            RTL
        }

        public enum VectorEffect {
            None,
            NonScalingStroke
        }

        public static Style getDefaultStyle() {
            Style style = new Style();
            style.specifiedFlags = -1L;
            Colour colour = Colour.BLACK;
            style.fill = colour;
            FillRule fillRule = FillRule.NonZero;
            style.fillRule = fillRule;
            Float fValueOf = Float.valueOf(1.0f);
            style.fillOpacity = fValueOf;
            style.stroke = null;
            style.strokeOpacity = fValueOf;
            style.strokeWidth = new Length(1.0f);
            style.strokeLineCap = LineCap.Butt;
            style.strokeLineJoin = LineJoin.Miter;
            style.strokeMiterLimit = Float.valueOf(4.0f);
            style.strokeDashArray = null;
            style.strokeDashOffset = new Length(0.0f);
            style.opacity = fValueOf;
            style.color = colour;
            style.fontFamily = null;
            style.fontSize = new Length(12.0f, Unit.pt);
            style.fontWeight = 400;
            style.fontStyle = FontStyle.Normal;
            style.textDecoration = TextDecoration.None;
            style.direction = TextDirection.LTR;
            style.textAnchor = TextAnchor.Start;
            Boolean bool = Boolean.TRUE;
            style.overflow = bool;
            style.clip = null;
            style.markerStart = null;
            style.markerMid = null;
            style.markerEnd = null;
            style.display = bool;
            style.visibility = bool;
            style.stopColor = colour;
            style.stopOpacity = fValueOf;
            style.clipPath = null;
            style.clipRule = fillRule;
            style.mask = null;
            style.solidColor = null;
            style.solidOpacity = fValueOf;
            style.viewportFill = null;
            style.viewportFillOpacity = fValueOf;
            style.vectorEffect = VectorEffect.None;
            style.imageRendering = RenderQuality.auto;
            return style;
        }

        public Object clone() throws CloneNotSupportedException {
            Style style = (Style) super.clone();
            Length[] lengthArr = this.strokeDashArray;
            if (lengthArr != null) {
                style.strokeDashArray = (Length[]) lengthArr.clone();
            }
            return style;
        }

        public void resetNonInheritingProperties(boolean z2) {
            Boolean bool = Boolean.TRUE;
            this.display = bool;
            if (!z2) {
                bool = Boolean.FALSE;
            }
            this.overflow = bool;
            this.clip = null;
            this.clipPath = null;
            this.opacity = Float.valueOf(1.0f);
            this.stopColor = Colour.BLACK;
            this.stopOpacity = Float.valueOf(1.0f);
            this.mask = null;
            this.solidColor = null;
            this.solidOpacity = Float.valueOf(1.0f);
            this.viewportFill = null;
            this.viewportFillOpacity = Float.valueOf(1.0f);
            this.vectorEffect = VectorEffect.None;
        }
    }

    public static class Svg extends SvgViewBoxContainer {
        Length height;
        public String version;
        Length width;

        /* renamed from: x, reason: collision with root package name */
        Length f6317x;

        /* renamed from: y, reason: collision with root package name */
        Length f6318y;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "svg";
        }
    }

    public interface SvgConditional {
        String getRequiredExtensions();

        Set<String> getRequiredFeatures();

        Set<String> getRequiredFonts();

        Set<String> getRequiredFormats();

        Set<String> getSystemLanguage();

        void setRequiredExtensions(String str);

        void setRequiredFeatures(Set<String> set);

        void setRequiredFonts(Set<String> set);

        void setRequiredFormats(Set<String> set);

        void setSystemLanguage(Set<String> set);
    }

    public static abstract class SvgConditionalContainer extends SvgElement implements SvgContainer, SvgConditional {
        List<SvgObject> children = new ArrayList();
        Set<String> requiredFeatures = null;
        String requiredExtensions = null;
        Set<String> systemLanguage = null;
        Set<String> requiredFormats = null;
        Set<String> requiredFonts = null;

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public void addChild(SvgObject svgObject) throws SVGParseException {
            this.children.add(svgObject);
        }

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public List<SvgObject> getChildren() {
            return this.children;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public String getRequiredExtensions() {
            return this.requiredExtensions;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public Set<String> getRequiredFeatures() {
            return this.requiredFeatures;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public Set<String> getRequiredFonts() {
            return this.requiredFonts;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public Set<String> getRequiredFormats() {
            return this.requiredFormats;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public Set<String> getSystemLanguage() {
            return null;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public void setRequiredExtensions(String str) {
            this.requiredExtensions = str;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public void setRequiredFeatures(Set<String> set) {
            this.requiredFeatures = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public void setRequiredFonts(Set<String> set) {
            this.requiredFonts = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public void setRequiredFormats(Set<String> set) {
            this.requiredFormats = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public void setSystemLanguage(Set<String> set) {
            this.systemLanguage = set;
        }
    }

    public static abstract class SvgConditionalElement extends SvgElement implements SvgConditional {
        Set<String> requiredFeatures = null;
        String requiredExtensions = null;
        Set<String> systemLanguage = null;
        Set<String> requiredFormats = null;
        Set<String> requiredFonts = null;

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public String getRequiredExtensions() {
            return this.requiredExtensions;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public Set<String> getRequiredFeatures() {
            return this.requiredFeatures;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public Set<String> getRequiredFonts() {
            return this.requiredFonts;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public Set<String> getRequiredFormats() {
            return this.requiredFormats;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public Set<String> getSystemLanguage() {
            return this.systemLanguage;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public void setRequiredExtensions(String str) {
            this.requiredExtensions = str;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public void setRequiredFeatures(Set<String> set) {
            this.requiredFeatures = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public void setRequiredFonts(Set<String> set) {
            this.requiredFonts = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public void setRequiredFormats(Set<String> set) {
            this.requiredFormats = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public void setSystemLanguage(Set<String> set) {
            this.systemLanguage = set;
        }
    }

    public interface SvgContainer {
        void addChild(SvgObject svgObject) throws SVGParseException;

        List<SvgObject> getChildren();
    }

    public static abstract class SvgElement extends SvgElementBase {
        Box boundingBox = null;
    }

    public static abstract class SvgElementBase extends SvgObject {
        String id = null;
        Boolean spacePreserve = null;
        Style baseStyle = null;
        Style style = null;
        List<String> classNames = null;

        public String toString() {
            return getNodeName();
        }
    }

    public static class SvgLinearGradient extends GradientElement {

        /* renamed from: x1, reason: collision with root package name */
        Length f6319x1;
        Length x2;

        /* renamed from: y1, reason: collision with root package name */
        Length f6320y1;
        Length y2;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "linearGradient";
        }
    }

    public static class SvgObject {
        SVG document;
        SvgContainer parent;

        public String getNodeName() {
            return "";
        }
    }

    public static abstract class SvgPaint implements Cloneable {
    }

    public static abstract class SvgPreserveAspectRatioContainer extends SvgConditionalContainer {
        PreserveAspectRatio preserveAspectRatio = null;
    }

    public static class SvgRadialGradient extends GradientElement {
        Length cx;
        Length cy;
        Length fx;
        Length fy;

        /* renamed from: r, reason: collision with root package name */
        Length f6321r;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "radialGradient";
        }
    }

    public static abstract class SvgViewBoxContainer extends SvgPreserveAspectRatioContainer {
        Box viewBox;
    }

    public static class Switch extends Group {
        @Override // com.caverock.androidsvg.SVG.Group, com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "switch";
        }
    }

    public static class Symbol extends SvgViewBoxContainer implements NotDirectlyRendered {
        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "symbol";
        }
    }

    public static class TRef extends TextContainer implements TextChild {
        String href;
        private TextRoot textRoot;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "tref";
        }

        @Override // com.caverock.androidsvg.SVG.TextChild
        public TextRoot getTextRoot() {
            return this.textRoot;
        }

        @Override // com.caverock.androidsvg.SVG.TextChild
        public void setTextRoot(TextRoot textRoot) {
            this.textRoot = textRoot;
        }
    }

    public static class TSpan extends TextPositionedContainer implements TextChild {
        private TextRoot textRoot;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "tspan";
        }

        @Override // com.caverock.androidsvg.SVG.TextChild
        public TextRoot getTextRoot() {
            return this.textRoot;
        }

        @Override // com.caverock.androidsvg.SVG.TextChild
        public void setTextRoot(TextRoot textRoot) {
            this.textRoot = textRoot;
        }
    }

    public static class Text extends TextPositionedContainer implements TextRoot, HasTransform {
        Matrix transform;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "text";
        }

        @Override // com.caverock.androidsvg.SVG.HasTransform
        public void setTransform(Matrix matrix) {
            this.transform = matrix;
        }
    }

    public interface TextChild {
        TextRoot getTextRoot();

        void setTextRoot(TextRoot textRoot);
    }

    public static abstract class TextContainer extends SvgConditionalContainer {
        @Override // com.caverock.androidsvg.SVG.SvgConditionalContainer, com.caverock.androidsvg.SVG.SvgContainer
        public void addChild(SvgObject svgObject) throws SVGParseException {
            if (svgObject instanceof TextChild) {
                this.children.add(svgObject);
                return;
            }
            throw new SVGParseException("Text content elements cannot contain " + svgObject + " elements.");
        }
    }

    public static class TextPath extends TextContainer implements TextChild {
        String href;
        Length startOffset;
        private TextRoot textRoot;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "textPath";
        }

        @Override // com.caverock.androidsvg.SVG.TextChild
        public TextRoot getTextRoot() {
            return this.textRoot;
        }

        @Override // com.caverock.androidsvg.SVG.TextChild
        public void setTextRoot(TextRoot textRoot) {
            this.textRoot = textRoot;
        }
    }

    public static abstract class TextPositionedContainer extends TextContainer {
        List<Length> dx;
        List<Length> dy;

        /* renamed from: x, reason: collision with root package name */
        List<Length> f6322x;

        /* renamed from: y, reason: collision with root package name */
        List<Length> f6323y;
    }

    public interface TextRoot {
    }

    public static class TextSequence extends SvgObject implements TextChild {
        String text;
        private TextRoot textRoot;

        public TextSequence(String str) {
            this.text = str;
        }

        @Override // com.caverock.androidsvg.SVG.TextChild
        public TextRoot getTextRoot() {
            return this.textRoot;
        }

        @Override // com.caverock.androidsvg.SVG.TextChild
        public void setTextRoot(TextRoot textRoot) {
            this.textRoot = textRoot;
        }

        public String toString() {
            return "TextChild: '" + this.text + "'";
        }
    }

    public enum Unit {
        px,
        em,
        ex,
        in,
        cm,
        mm,
        pt,
        pc,
        percent
    }

    public static class Use extends Group {
        Length height;
        String href;
        Length width;

        /* renamed from: x, reason: collision with root package name */
        Length f6324x;

        /* renamed from: y, reason: collision with root package name */
        Length f6325y;

        @Override // com.caverock.androidsvg.SVG.Group, com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "use";
        }
    }

    public static class View extends SvgViewBoxContainer implements NotDirectlyRendered {
        static final String NODE_NAME = "view";

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return NODE_NAME;
        }
    }

    private String cssQuotedString(String str) {
        if (str.startsWith("\"") && str.endsWith("\"")) {
            str = str.substring(1, str.length() - 1).replace("\\\"", "\"");
        } else if (str.startsWith("'") && str.endsWith("'")) {
            str = str.substring(1, str.length() - 1).replace("\\'", "'");
        }
        return str.replace("\\\n", "").replace("\\A", "\n");
    }

    public static void deregisterExternalFileResolver() {
        externalFileResolver = null;
    }

    private Box getDocumentDimensions(float f2) {
        Unit unit;
        Unit unit2;
        Unit unit3;
        Unit unit4;
        float fFloatValue;
        Unit unit5;
        Svg svg = this.rootElement;
        Length length = svg.width;
        Length length2 = svg.height;
        if (length == null || length.isZero() || (unit = length.unit) == (unit2 = Unit.percent) || unit == (unit3 = Unit.em) || unit == (unit4 = Unit.ex)) {
            return new Box(-1.0f, -1.0f, -1.0f, -1.0f);
        }
        float fFloatValue2 = length.floatValue(f2);
        if (length2 == null) {
            Box box = this.rootElement.viewBox;
            fFloatValue = box != null ? (box.height * fFloatValue2) / box.width : fFloatValue2;
        } else {
            if (length2.isZero() || (unit5 = length2.unit) == unit2 || unit5 == unit3 || unit5 == unit4) {
                return new Box(-1.0f, -1.0f, -1.0f, -1.0f);
            }
            fFloatValue = length2.floatValue(f2);
        }
        return new Box(0.0f, 0.0f, fFloatValue2, fFloatValue);
    }

    private List<SvgObject> getElementsByTagName(String str) {
        ArrayList arrayList = new ArrayList();
        getElementsByTagName(arrayList, this.rootElement, str);
        return arrayList;
    }

    public static SVGExternalFileResolver getFileResolver() {
        return externalFileResolver;
    }

    public static SVG getFromAsset(AssetManager assetManager, String str) throws IOException, SVGParseException {
        SVGParser sVGParser = new SVGParser();
        InputStream inputStreamOpen = assetManager.open(str);
        try {
            return sVGParser.parse(inputStreamOpen, enableInternalEntities);
        } finally {
            try {
                inputStreamOpen.close();
            } catch (IOException unused) {
            }
        }
    }

    public static SVG getFromInputStream(InputStream inputStream) throws SVGParseException {
        return new SVGParser().parse(inputStream, enableInternalEntities);
    }

    public static SVG getFromResource(Context context, int i2) throws SVGParseException {
        return getFromResource(context.getResources(), i2);
    }

    public static SVG getFromString(String str) throws SVGParseException {
        return new SVGParser().parse(new ByteArrayInputStream(str.getBytes()), enableInternalEntities);
    }

    public static String getVersion() {
        return VERSION;
    }

    public static boolean isInternalEntitiesEnabled() {
        return enableInternalEntities;
    }

    public static void registerExternalFileResolver(SVGExternalFileResolver sVGExternalFileResolver) {
        externalFileResolver = sVGExternalFileResolver;
    }

    public static void setInternalEntitiesEnabled(boolean z2) {
        enableInternalEntities = z2;
    }

    public void addCSSRules(CSSParser.Ruleset ruleset) {
        this.cssRules.addAll(ruleset);
    }

    public void clearRenderCSSRules() {
        this.cssRules.removeFromSource(CSSParser.Source.RenderOptions);
    }

    public List<CSSParser.Rule> getCSSRules() {
        return this.cssRules.getRules();
    }

    public float getDocumentAspectRatio() {
        Svg svg = this.rootElement;
        if (svg == null) {
            throw new IllegalArgumentException("SVG document is empty");
        }
        Length length = svg.width;
        Length length2 = svg.height;
        if (length != null && length2 != null) {
            Unit unit = length.unit;
            Unit unit2 = Unit.percent;
            if (unit != unit2 && length2.unit != unit2) {
                if (length.isZero() || length2.isZero()) {
                    return -1.0f;
                }
                return length.floatValue(this.renderDPI) / length2.floatValue(this.renderDPI);
            }
        }
        Box box = svg.viewBox;
        if (box != null) {
            float f2 = box.width;
            if (f2 != 0.0f) {
                float f3 = box.height;
                if (f3 != 0.0f) {
                    return f2 / f3;
                }
            }
        }
        return -1.0f;
    }

    public String getDocumentDescription() {
        if (this.rootElement != null) {
            return this.desc;
        }
        throw new IllegalArgumentException("SVG document is empty");
    }

    public float getDocumentHeight() {
        if (this.rootElement != null) {
            return getDocumentDimensions(this.renderDPI).height;
        }
        throw new IllegalArgumentException("SVG document is empty");
    }

    public PreserveAspectRatio getDocumentPreserveAspectRatio() {
        Svg svg = this.rootElement;
        if (svg == null) {
            throw new IllegalArgumentException("SVG document is empty");
        }
        PreserveAspectRatio preserveAspectRatio = svg.preserveAspectRatio;
        if (preserveAspectRatio == null) {
            return null;
        }
        return preserveAspectRatio;
    }

    public String getDocumentSVGVersion() {
        Svg svg = this.rootElement;
        if (svg != null) {
            return svg.version;
        }
        throw new IllegalArgumentException("SVG document is empty");
    }

    public String getDocumentTitle() {
        if (this.rootElement != null) {
            return this.title;
        }
        throw new IllegalArgumentException("SVG document is empty");
    }

    public RectF getDocumentViewBox() {
        Svg svg = this.rootElement;
        if (svg == null) {
            throw new IllegalArgumentException("SVG document is empty");
        }
        Box box = svg.viewBox;
        if (box == null) {
            return null;
        }
        return box.toRectF();
    }

    public float getDocumentWidth() {
        if (this.rootElement != null) {
            return getDocumentDimensions(this.renderDPI).width;
        }
        throw new IllegalArgumentException("SVG document is empty");
    }

    public SvgElementBase getElementById(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (str.equals(this.rootElement.id)) {
            return this.rootElement;
        }
        if (this.idToElementMap.containsKey(str)) {
            return this.idToElementMap.get(str);
        }
        SvgElementBase elementById = getElementById(this.rootElement, str);
        this.idToElementMap.put(str, elementById);
        return elementById;
    }

    public float getRenderDPI() {
        return this.renderDPI;
    }

    public Svg getRootElement() {
        return this.rootElement;
    }

    public Set<String> getViewList() {
        if (this.rootElement == null) {
            throw new IllegalArgumentException("SVG document is empty");
        }
        List<SvgObject> elementsByTagName = getElementsByTagName("view");
        HashSet hashSet = new HashSet(elementsByTagName.size());
        Iterator<SvgObject> it = elementsByTagName.iterator();
        while (it.hasNext()) {
            String str = ((View) it.next()).id;
            if (str != null) {
                hashSet.add(str);
            } else {
                Log.w("AndroidSVG", "getViewList(): found a <view> without an id attribute");
            }
        }
        return hashSet;
    }

    public boolean hasCSSRules() {
        return !this.cssRules.isEmpty();
    }

    public void renderToCanvas(Canvas canvas) {
        renderToCanvas(canvas, (RenderOptions) null);
    }

    public Picture renderToPicture() {
        return renderToPicture(null);
    }

    public void renderViewToCanvas(String str, Canvas canvas) {
        renderToCanvas(canvas, RenderOptions.create().view(str));
    }

    public Picture renderViewToPicture(String str, int i2, int i3) {
        RenderOptions renderOptions = new RenderOptions();
        renderOptions.view(str).viewPort(0.0f, 0.0f, i2, i3);
        Picture picture = new Picture();
        new SVGAndroidRenderer(picture.beginRecording(i2, i3), this.renderDPI).renderDocument(this, renderOptions);
        picture.endRecording();
        return picture;
    }

    public SvgObject resolveIRI(String str) {
        if (str == null) {
            return null;
        }
        String strCssQuotedString = cssQuotedString(str);
        if (strCssQuotedString.length() <= 1 || !strCssQuotedString.startsWith(DictionaryFactory.SHARP)) {
            return null;
        }
        return getElementById(strCssQuotedString.substring(1));
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public void setDocumentHeight(float f2) {
        Svg svg = this.rootElement;
        if (svg == null) {
            throw new IllegalArgumentException("SVG document is empty");
        }
        svg.height = new Length(f2);
    }

    public void setDocumentPreserveAspectRatio(PreserveAspectRatio preserveAspectRatio) {
        Svg svg = this.rootElement;
        if (svg == null) {
            throw new IllegalArgumentException("SVG document is empty");
        }
        svg.preserveAspectRatio = preserveAspectRatio;
    }

    public void setDocumentViewBox(float f2, float f3, float f4, float f5) {
        Svg svg = this.rootElement;
        if (svg == null) {
            throw new IllegalArgumentException("SVG document is empty");
        }
        svg.viewBox = new Box(f2, f3, f4, f5);
    }

    public void setDocumentWidth(float f2) {
        Svg svg = this.rootElement;
        if (svg == null) {
            throw new IllegalArgumentException("SVG document is empty");
        }
        svg.width = new Length(f2);
    }

    public void setRenderDPI(float f2) {
        this.renderDPI = f2;
    }

    public void setRootElement(Svg svg) {
        this.rootElement = svg;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public static SVG getFromResource(Resources resources, int i2) throws Resources.NotFoundException, IOException, SVGParseException {
        SVGParser sVGParser = new SVGParser();
        InputStream inputStreamOpenRawResource = resources.openRawResource(i2);
        try {
            return sVGParser.parse(inputStreamOpenRawResource, enableInternalEntities);
        } finally {
            try {
                inputStreamOpenRawResource.close();
            } catch (IOException unused) {
            }
        }
    }

    public void renderToCanvas(Canvas canvas, RectF rectF) {
        RenderOptions renderOptions = new RenderOptions();
        if (rectF != null) {
            renderOptions.viewPort(rectF.left, rectF.top, rectF.width(), rectF.height());
        } else {
            renderOptions.viewPort(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight());
        }
        new SVGAndroidRenderer(canvas, this.renderDPI).renderDocument(this, renderOptions);
    }

    public Picture renderToPicture(int i2, int i3) {
        return renderToPicture(i2, i3, null);
    }

    public void renderViewToCanvas(String str, Canvas canvas, RectF rectF) {
        RenderOptions renderOptionsView = RenderOptions.create().view(str);
        if (rectF != null) {
            renderOptionsView.viewPort(rectF.left, rectF.top, rectF.width(), rectF.height());
        }
        renderToCanvas(canvas, renderOptionsView);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void getElementsByTagName(List<SvgObject> list, SvgObject svgObject, String str) {
        if (svgObject.getNodeName().equals(str)) {
            list.add(svgObject);
        }
        if (svgObject instanceof SvgContainer) {
            Iterator<SvgObject> it = ((SvgContainer) svgObject).getChildren().iterator();
            while (it.hasNext()) {
                getElementsByTagName(list, it.next(), str);
            }
        }
    }

    public Picture renderToPicture(RenderOptions renderOptions) {
        Length length;
        Box box = (renderOptions == null || !renderOptions.hasViewBox()) ? this.rootElement.viewBox : renderOptions.viewBox;
        if (renderOptions != null && renderOptions.hasViewPort()) {
            return renderToPicture((int) Math.ceil(renderOptions.viewPort.maxX()), (int) Math.ceil(renderOptions.viewPort.maxY()), renderOptions);
        }
        Svg svg = this.rootElement;
        Length length2 = svg.width;
        if (length2 != null) {
            Unit unit = length2.unit;
            Unit unit2 = Unit.percent;
            if (unit != unit2 && (length = svg.height) != null && length.unit != unit2) {
                return renderToPicture((int) Math.ceil(length2.floatValue(this.renderDPI)), (int) Math.ceil(this.rootElement.height.floatValue(this.renderDPI)), renderOptions);
            }
        }
        if (length2 != null && box != null) {
            return renderToPicture((int) Math.ceil(length2.floatValue(this.renderDPI)), (int) Math.ceil((box.height * r1) / box.width), renderOptions);
        }
        Length length3 = svg.height;
        if (length3 != null && box != null) {
            return renderToPicture((int) Math.ceil((box.width * r1) / box.height), (int) Math.ceil(length3.floatValue(this.renderDPI)), renderOptions);
        }
        return renderToPicture(512, 512, renderOptions);
    }

    public void setDocumentHeight(String str) throws SVGParseException {
        Svg svg = this.rootElement;
        if (svg != null) {
            svg.height = SVGParser.parseLength(str);
            return;
        }
        throw new IllegalArgumentException("SVG document is empty");
    }

    public void setDocumentWidth(String str) throws SVGParseException {
        Svg svg = this.rootElement;
        if (svg != null) {
            svg.width = SVGParser.parseLength(str);
            return;
        }
        throw new IllegalArgumentException("SVG document is empty");
    }

    public static class Box {
        float height;
        float minX;
        float minY;
        float width;

        public Box(float f2, float f3, float f4, float f5) {
            this.minX = f2;
            this.minY = f3;
            this.width = f4;
            this.height = f5;
        }

        public static Box fromLimits(float f2, float f3, float f4, float f5) {
            return new Box(f2, f3, f4 - f2, f5 - f3);
        }

        public float maxX() {
            return this.minX + this.width;
        }

        public float maxY() {
            return this.minY + this.height;
        }

        public RectF toRectF() {
            return new RectF(this.minX, this.minY, maxX(), maxY());
        }

        public String toString() {
            return StrPool.BRACKET_START + this.minX + " " + this.minY + " " + this.width + " " + this.height + StrPool.BRACKET_END;
        }

        public void union(Box box) {
            float f2 = box.minX;
            if (f2 < this.minX) {
                this.minX = f2;
            }
            float f3 = box.minY;
            if (f3 < this.minY) {
                this.minY = f3;
            }
            if (box.maxX() > maxX()) {
                this.width = box.maxX() - this.minX;
            }
            if (box.maxY() > maxY()) {
                this.height = box.maxY() - this.minY;
            }
        }

        public Box(Box box) {
            this.minX = box.minX;
            this.minY = box.minY;
            this.width = box.width;
            this.height = box.height;
        }
    }

    public void renderToCanvas(Canvas canvas, RenderOptions renderOptions) {
        if (renderOptions == null) {
            renderOptions = new RenderOptions();
        }
        if (!renderOptions.hasViewPort()) {
            renderOptions.viewPort(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight());
        }
        new SVGAndroidRenderer(canvas, this.renderDPI).renderDocument(this, renderOptions);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private SvgElementBase getElementById(SvgContainer svgContainer, String str) {
        SvgElementBase elementById;
        SvgElementBase svgElementBase = (SvgElementBase) svgContainer;
        if (str.equals(svgElementBase.id)) {
            return svgElementBase;
        }
        for (Object obj : svgContainer.getChildren()) {
            if (obj instanceof SvgElementBase) {
                SvgElementBase svgElementBase2 = (SvgElementBase) obj;
                if (str.equals(svgElementBase2.id)) {
                    return svgElementBase2;
                }
                if ((obj instanceof SvgContainer) && (elementById = getElementById((SvgContainer) obj, str)) != null) {
                    return elementById;
                }
            }
        }
        return null;
    }

    public Picture renderToPicture(int i2, int i3, RenderOptions renderOptions) {
        Picture picture = new Picture();
        Canvas canvasBeginRecording = picture.beginRecording(i2, i3);
        if (renderOptions == null || renderOptions.viewPort == null) {
            renderOptions = renderOptions == null ? new RenderOptions() : new RenderOptions(renderOptions);
            renderOptions.viewPort(0.0f, 0.0f, i2, i3);
        }
        new SVGAndroidRenderer(canvasBeginRecording, this.renderDPI).renderDocument(this, renderOptions);
        picture.endRecording();
        return picture;
    }
}
