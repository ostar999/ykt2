package com.caverock.androidsvg;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Base64;
import android.util.Log;
import com.caverock.androidsvg.CSSParser;
import com.caverock.androidsvg.PreserveAspectRatio;
import com.caverock.androidsvg.SVG;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Stack;

/* loaded from: classes2.dex */
class SVGAndroidRenderer {
    private static final float BEZIER_ARC_FACTOR = 0.5522848f;
    private static final String DEFAULT_FONT_FAMILY = "serif";
    public static final float LUMINANCE_TO_ALPHA_BLUE = 0.0722f;
    public static final float LUMINANCE_TO_ALPHA_GREEN = 0.7151f;
    public static final float LUMINANCE_TO_ALPHA_RED = 0.2127f;
    private static final String TAG = "SVGAndroidRenderer";
    private static HashSet<String> supportedFeatures;
    private Canvas canvas;
    private SVG document;
    private float dpi;
    private Stack<Matrix> matrixStack;
    private Stack<SVG.SvgContainer> parentStack;
    private CSSParser.RuleMatchContext ruleMatchContext = null;
    private RendererState state;
    private Stack<RendererState> stateStack;

    /* renamed from: com.caverock.androidsvg.SVGAndroidRenderer$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment;
        static final /* synthetic */ int[] $SwitchMap$com$caverock$androidsvg$SVG$Style$LineCap;
        static final /* synthetic */ int[] $SwitchMap$com$caverock$androidsvg$SVG$Style$LineJoin;

        static {
            int[] iArr = new int[SVG.Style.LineJoin.values().length];
            $SwitchMap$com$caverock$androidsvg$SVG$Style$LineJoin = iArr;
            try {
                iArr[SVG.Style.LineJoin.Miter.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Style$LineJoin[SVG.Style.LineJoin.Round.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Style$LineJoin[SVG.Style.LineJoin.Bevel.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[SVG.Style.LineCap.values().length];
            $SwitchMap$com$caverock$androidsvg$SVG$Style$LineCap = iArr2;
            try {
                iArr2[SVG.Style.LineCap.Butt.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Style$LineCap[SVG.Style.LineCap.Round.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Style$LineCap[SVG.Style.LineCap.Square.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr3 = new int[PreserveAspectRatio.Alignment.values().length];
            $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment = iArr3;
            try {
                iArr3[PreserveAspectRatio.Alignment.xMidYMin.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[PreserveAspectRatio.Alignment.xMidYMid.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[PreserveAspectRatio.Alignment.xMidYMax.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[PreserveAspectRatio.Alignment.xMaxYMin.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[PreserveAspectRatio.Alignment.xMaxYMid.ordinal()] = 5;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[PreserveAspectRatio.Alignment.xMaxYMax.ordinal()] = 6;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[PreserveAspectRatio.Alignment.xMinYMid.ordinal()] = 7;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[PreserveAspectRatio.Alignment.xMinYMax.ordinal()] = 8;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    public class MarkerPositionCalculator implements SVG.PathInterface {
        private boolean closepathReAdjustPending;
        private float startX;
        private float startY;
        private List<MarkerVector> markers = new ArrayList();
        private MarkerVector lastPos = null;
        private boolean startArc = false;
        private boolean normalCubic = true;
        private int subpathStartIndex = -1;

        public MarkerPositionCalculator(SVG.PathDefinition pathDefinition) {
            if (pathDefinition == null) {
                return;
            }
            pathDefinition.enumeratePath(this);
            if (this.closepathReAdjustPending) {
                this.lastPos.add(this.markers.get(this.subpathStartIndex));
                this.markers.set(this.subpathStartIndex, this.lastPos);
                this.closepathReAdjustPending = false;
            }
            MarkerVector markerVector = this.lastPos;
            if (markerVector != null) {
                this.markers.add(markerVector);
            }
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void arcTo(float f2, float f3, float f4, boolean z2, boolean z3, float f5, float f6) {
            this.startArc = true;
            this.normalCubic = false;
            MarkerVector markerVector = this.lastPos;
            SVGAndroidRenderer.arcTo(markerVector.f6326x, markerVector.f6327y, f2, f3, f4, z2, z3, f5, f6, this);
            this.normalCubic = true;
            this.closepathReAdjustPending = false;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void close() {
            this.markers.add(this.lastPos);
            lineTo(this.startX, this.startY);
            this.closepathReAdjustPending = true;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void cubicTo(float f2, float f3, float f4, float f5, float f6, float f7) {
            if (this.normalCubic || this.startArc) {
                this.lastPos.add(f2, f3);
                this.markers.add(this.lastPos);
                this.startArc = false;
            }
            this.lastPos = SVGAndroidRenderer.this.new MarkerVector(f6, f7, f6 - f4, f7 - f5);
            this.closepathReAdjustPending = false;
        }

        public List<MarkerVector> getMarkers() {
            return this.markers;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void lineTo(float f2, float f3) {
            this.lastPos.add(f2, f3);
            this.markers.add(this.lastPos);
            SVGAndroidRenderer sVGAndroidRenderer = SVGAndroidRenderer.this;
            MarkerVector markerVector = this.lastPos;
            this.lastPos = sVGAndroidRenderer.new MarkerVector(f2, f3, f2 - markerVector.f6326x, f3 - markerVector.f6327y);
            this.closepathReAdjustPending = false;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void moveTo(float f2, float f3) {
            if (this.closepathReAdjustPending) {
                this.lastPos.add(this.markers.get(this.subpathStartIndex));
                this.markers.set(this.subpathStartIndex, this.lastPos);
                this.closepathReAdjustPending = false;
            }
            MarkerVector markerVector = this.lastPos;
            if (markerVector != null) {
                this.markers.add(markerVector);
            }
            this.startX = f2;
            this.startY = f3;
            this.lastPos = SVGAndroidRenderer.this.new MarkerVector(f2, f3, 0.0f, 0.0f);
            this.subpathStartIndex = this.markers.size();
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void quadTo(float f2, float f3, float f4, float f5) {
            this.lastPos.add(f2, f3);
            this.markers.add(this.lastPos);
            this.lastPos = SVGAndroidRenderer.this.new MarkerVector(f4, f5, f4 - f2, f5 - f3);
            this.closepathReAdjustPending = false;
        }
    }

    public class PathConverter implements SVG.PathInterface {
        float lastX;
        float lastY;
        Path path = new Path();

        public PathConverter(SVG.PathDefinition pathDefinition) {
            if (pathDefinition == null) {
                return;
            }
            pathDefinition.enumeratePath(this);
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void arcTo(float f2, float f3, float f4, boolean z2, boolean z3, float f5, float f6) {
            SVGAndroidRenderer.arcTo(this.lastX, this.lastY, f2, f3, f4, z2, z3, f5, f6, this);
            this.lastX = f5;
            this.lastY = f6;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void close() {
            this.path.close();
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void cubicTo(float f2, float f3, float f4, float f5, float f6, float f7) {
            this.path.cubicTo(f2, f3, f4, f5, f6, f7);
            this.lastX = f6;
            this.lastY = f7;
        }

        public Path getPath() {
            return this.path;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void lineTo(float f2, float f3) {
            this.path.lineTo(f2, f3);
            this.lastX = f2;
            this.lastY = f3;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void moveTo(float f2, float f3) {
            this.path.moveTo(f2, f3);
            this.lastX = f2;
            this.lastY = f3;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public void quadTo(float f2, float f3, float f4, float f5) {
            this.path.quadTo(f2, f3, f4, f5);
            this.lastX = f4;
            this.lastY = f5;
        }
    }

    public class PathTextDrawer extends PlainTextDrawer {
        private Path path;

        public PathTextDrawer(Path path, float f2, float f3) {
            super(f2, f3);
            this.path = path;
        }

        @Override // com.caverock.androidsvg.SVGAndroidRenderer.PlainTextDrawer, com.caverock.androidsvg.SVGAndroidRenderer.TextProcessor
        public void processText(String str) {
            if (SVGAndroidRenderer.this.visible()) {
                if (SVGAndroidRenderer.this.state.hasFill) {
                    SVGAndroidRenderer.this.canvas.drawTextOnPath(str, this.path, this.f6328x, this.f6329y, SVGAndroidRenderer.this.state.fillPaint);
                }
                if (SVGAndroidRenderer.this.state.hasStroke) {
                    SVGAndroidRenderer.this.canvas.drawTextOnPath(str, this.path, this.f6328x, this.f6329y, SVGAndroidRenderer.this.state.strokePaint);
                }
            }
            this.f6328x += SVGAndroidRenderer.this.state.fillPaint.measureText(str);
        }
    }

    public class PlainTextDrawer extends TextProcessor {

        /* renamed from: x, reason: collision with root package name */
        float f6328x;

        /* renamed from: y, reason: collision with root package name */
        float f6329y;

        public PlainTextDrawer(float f2, float f3) {
            super(SVGAndroidRenderer.this, null);
            this.f6328x = f2;
            this.f6329y = f3;
        }

        @Override // com.caverock.androidsvg.SVGAndroidRenderer.TextProcessor
        public void processText(String str) {
            SVGAndroidRenderer.debug("TextSequence render", new Object[0]);
            if (SVGAndroidRenderer.this.visible()) {
                if (SVGAndroidRenderer.this.state.hasFill) {
                    SVGAndroidRenderer.this.canvas.drawText(str, this.f6328x, this.f6329y, SVGAndroidRenderer.this.state.fillPaint);
                }
                if (SVGAndroidRenderer.this.state.hasStroke) {
                    SVGAndroidRenderer.this.canvas.drawText(str, this.f6328x, this.f6329y, SVGAndroidRenderer.this.state.strokePaint);
                }
            }
            this.f6328x += SVGAndroidRenderer.this.state.fillPaint.measureText(str);
        }
    }

    public class PlainTextToPath extends TextProcessor {
        Path textAsPath;

        /* renamed from: x, reason: collision with root package name */
        float f6330x;

        /* renamed from: y, reason: collision with root package name */
        float f6331y;

        public PlainTextToPath(float f2, float f3, Path path) {
            super(SVGAndroidRenderer.this, null);
            this.f6330x = f2;
            this.f6331y = f3;
            this.textAsPath = path;
        }

        @Override // com.caverock.androidsvg.SVGAndroidRenderer.TextProcessor
        public boolean doTextContainer(SVG.TextContainer textContainer) {
            if (!(textContainer instanceof SVG.TextPath)) {
                return true;
            }
            SVGAndroidRenderer.warn("Using <textPath> elements in a clip path is not supported.", new Object[0]);
            return false;
        }

        @Override // com.caverock.androidsvg.SVGAndroidRenderer.TextProcessor
        public void processText(String str) {
            if (SVGAndroidRenderer.this.visible()) {
                Path path = new Path();
                SVGAndroidRenderer.this.state.fillPaint.getTextPath(str, 0, str.length(), this.f6330x, this.f6331y, path);
                this.textAsPath.addPath(path);
            }
            this.f6330x += SVGAndroidRenderer.this.state.fillPaint.measureText(str);
        }
    }

    public class TextBoundsCalculator extends TextProcessor {
        RectF bbox;

        /* renamed from: x, reason: collision with root package name */
        float f6332x;

        /* renamed from: y, reason: collision with root package name */
        float f6333y;

        public TextBoundsCalculator(float f2, float f3) {
            super(SVGAndroidRenderer.this, null);
            this.bbox = new RectF();
            this.f6332x = f2;
            this.f6333y = f3;
        }

        @Override // com.caverock.androidsvg.SVGAndroidRenderer.TextProcessor
        public boolean doTextContainer(SVG.TextContainer textContainer) {
            if (!(textContainer instanceof SVG.TextPath)) {
                return true;
            }
            SVG.TextPath textPath = (SVG.TextPath) textContainer;
            SVG.SvgObject svgObjectResolveIRI = textContainer.document.resolveIRI(textPath.href);
            if (svgObjectResolveIRI == null) {
                SVGAndroidRenderer.error("TextPath path reference '%s' not found", textPath.href);
                return false;
            }
            SVG.Path path = (SVG.Path) svgObjectResolveIRI;
            Path path2 = SVGAndroidRenderer.this.new PathConverter(path.f6312d).getPath();
            Matrix matrix = path.transform;
            if (matrix != null) {
                path2.transform(matrix);
            }
            RectF rectF = new RectF();
            path2.computeBounds(rectF, true);
            this.bbox.union(rectF);
            return false;
        }

        @Override // com.caverock.androidsvg.SVGAndroidRenderer.TextProcessor
        public void processText(String str) {
            if (SVGAndroidRenderer.this.visible()) {
                Rect rect = new Rect();
                SVGAndroidRenderer.this.state.fillPaint.getTextBounds(str, 0, str.length(), rect);
                RectF rectF = new RectF(rect);
                rectF.offset(this.f6332x, this.f6333y);
                this.bbox.union(rectF);
            }
            this.f6332x += SVGAndroidRenderer.this.state.fillPaint.measureText(str);
        }
    }

    public abstract class TextProcessor {
        private TextProcessor() {
        }

        public boolean doTextContainer(SVG.TextContainer textContainer) {
            return true;
        }

        public abstract void processText(String str);

        public /* synthetic */ TextProcessor(SVGAndroidRenderer sVGAndroidRenderer, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public SVGAndroidRenderer(Canvas canvas, float f2) {
        this.canvas = canvas;
        this.dpi = f2;
    }

    private void addObjectToClip(SVG.SvgObject svgObject, boolean z2, Path path, Matrix matrix) {
        if (display()) {
            clipStatePush();
            if (svgObject instanceof SVG.Use) {
                if (z2) {
                    addObjectToClip((SVG.Use) svgObject, path, matrix);
                } else {
                    error("<use> elements inside a <clipPath> cannot reference another <use>", new Object[0]);
                }
            } else if (svgObject instanceof SVG.Path) {
                addObjectToClip((SVG.Path) svgObject, path, matrix);
            } else if (svgObject instanceof SVG.Text) {
                addObjectToClip((SVG.Text) svgObject, path, matrix);
            } else if (svgObject instanceof SVG.GraphicsElement) {
                addObjectToClip((SVG.GraphicsElement) svgObject, path, matrix);
            } else {
                error("Invalid %s element found in clipPath definition", svgObject.toString());
            }
            clipStatePop();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void arcTo(float f2, float f3, float f4, float f5, float f6, boolean z2, boolean z3, float f7, float f8, SVG.PathInterface pathInterface) {
        float f9;
        SVG.PathInterface pathInterface2;
        if (f2 == f7 && f3 == f8) {
            return;
        }
        if (f4 == 0.0f) {
            f9 = f7;
            pathInterface2 = pathInterface;
        } else {
            if (f5 != 0.0f) {
                float fAbs = Math.abs(f4);
                float fAbs2 = Math.abs(f5);
                double radians = Math.toRadians(f6 % 360.0d);
                double dCos = Math.cos(radians);
                double dSin = Math.sin(radians);
                double d3 = (f2 - f7) / 2.0d;
                double d4 = (f3 - f8) / 2.0d;
                double d5 = (dCos * d3) + (dSin * d4);
                double d6 = ((-dSin) * d3) + (d4 * dCos);
                double d7 = fAbs * fAbs;
                double d8 = fAbs2 * fAbs2;
                double d9 = d5 * d5;
                double d10 = d6 * d6;
                double d11 = (d9 / d7) + (d10 / d8);
                if (d11 > 0.99999d) {
                    double dSqrt = Math.sqrt(d11) * 1.00001d;
                    fAbs = (float) (fAbs * dSqrt);
                    fAbs2 = (float) (dSqrt * fAbs2);
                    d7 = fAbs * fAbs;
                    d8 = fAbs2 * fAbs2;
                }
                double d12 = z2 == z3 ? -1.0d : 1.0d;
                double d13 = d7 * d8;
                double d14 = d7 * d10;
                double d15 = d8 * d9;
                double d16 = ((d13 - d14) - d15) / (d14 + d15);
                if (d16 < 0.0d) {
                    d16 = 0.0d;
                }
                double dSqrt2 = d12 * Math.sqrt(d16);
                double d17 = fAbs;
                double d18 = fAbs2;
                double d19 = ((d17 * d6) / d18) * dSqrt2;
                float f10 = fAbs;
                float f11 = fAbs2;
                double d20 = dSqrt2 * (-((d18 * d5) / d17));
                double d21 = ((f2 + f7) / 2.0d) + ((dCos * d19) - (dSin * d20));
                double d22 = ((f3 + f8) / 2.0d) + (dSin * d19) + (dCos * d20);
                double d23 = (d5 - d19) / d17;
                double d24 = (d6 - d20) / d18;
                double d25 = ((-d5) - d19) / d17;
                double d26 = ((-d6) - d20) / d18;
                double d27 = (d23 * d23) + (d24 * d24);
                double dAcos = (d24 < 0.0d ? -1.0d : 1.0d) * Math.acos(d23 / Math.sqrt(d27));
                double dCheckedArcCos = ((d23 * d26) - (d24 * d25) >= 0.0d ? 1.0d : -1.0d) * checkedArcCos(((d23 * d25) + (d24 * d26)) / Math.sqrt(d27 * ((d25 * d25) + (d26 * d26))));
                if (!z3 && dCheckedArcCos > 0.0d) {
                    dCheckedArcCos -= 6.283185307179586d;
                } else if (z3 && dCheckedArcCos < 0.0d) {
                    dCheckedArcCos += 6.283185307179586d;
                }
                float[] fArrArcToBeziers = arcToBeziers(dAcos % 6.283185307179586d, dCheckedArcCos % 6.283185307179586d);
                Matrix matrix = new Matrix();
                matrix.postScale(f10, f11);
                matrix.postRotate(f6);
                matrix.postTranslate((float) d21, (float) d22);
                matrix.mapPoints(fArrArcToBeziers);
                fArrArcToBeziers[fArrArcToBeziers.length - 2] = f7;
                fArrArcToBeziers[fArrArcToBeziers.length - 1] = f8;
                for (int i2 = 0; i2 < fArrArcToBeziers.length; i2 += 6) {
                    pathInterface.cubicTo(fArrArcToBeziers[i2], fArrArcToBeziers[i2 + 1], fArrArcToBeziers[i2 + 2], fArrArcToBeziers[i2 + 3], fArrArcToBeziers[i2 + 4], fArrArcToBeziers[i2 + 5]);
                }
                return;
            }
            pathInterface2 = pathInterface;
            f9 = f7;
        }
        pathInterface2.lineTo(f9, f8);
    }

    private static float[] arcToBeziers(double d3, double d4) {
        int iCeil = (int) Math.ceil((Math.abs(d4) * 2.0d) / 3.141592653589793d);
        double d5 = d4 / iCeil;
        double d6 = d5 / 2.0d;
        double dSin = (Math.sin(d6) * 1.3333333333333333d) / (Math.cos(d6) + 1.0d);
        float[] fArr = new float[iCeil * 6];
        int i2 = 0;
        for (int i3 = 0; i3 < iCeil; i3++) {
            double d7 = d3 + (i3 * d5);
            double dCos = Math.cos(d7);
            double dSin2 = Math.sin(d7);
            int i4 = i2 + 1;
            fArr[i2] = (float) (dCos - (dSin * dSin2));
            int i5 = i4 + 1;
            fArr[i4] = (float) (dSin2 + (dCos * dSin));
            d5 = d5;
            double d8 = d7 + d5;
            double dCos2 = Math.cos(d8);
            double dSin3 = Math.sin(d8);
            int i6 = i5 + 1;
            fArr[i5] = (float) ((dSin * dSin3) + dCos2);
            int i7 = i6 + 1;
            fArr[i6] = (float) (dSin3 - (dSin * dCos2));
            int i8 = i7 + 1;
            fArr[i7] = (float) dCos2;
            i2 = i8 + 1;
            fArr[i8] = (float) dSin3;
        }
        return fArr;
    }

    @TargetApi(19)
    private Path calculateClipPath(SVG.SvgElement svgElement, SVG.Box box) {
        Path pathObjectToPath;
        SVG.SvgObject svgObjectResolveIRI = svgElement.document.resolveIRI(this.state.style.clipPath);
        if (svgObjectResolveIRI == null) {
            error("ClipPath reference '%s' not found", this.state.style.clipPath);
            return null;
        }
        SVG.ClipPath clipPath = (SVG.ClipPath) svgObjectResolveIRI;
        this.stateStack.push(this.state);
        this.state = findInheritFromAncestorState(clipPath);
        Boolean bool = clipPath.clipPathUnitsAreUser;
        boolean z2 = bool == null || bool.booleanValue();
        Matrix matrix = new Matrix();
        if (!z2) {
            matrix.preTranslate(box.minX, box.minY);
            matrix.preScale(box.width, box.height);
        }
        Matrix matrix2 = clipPath.transform;
        if (matrix2 != null) {
            matrix.preConcat(matrix2);
        }
        Path path = new Path();
        for (SVG.SvgObject svgObject : clipPath.children) {
            if ((svgObject instanceof SVG.SvgElement) && (pathObjectToPath = objectToPath((SVG.SvgElement) svgObject, true)) != null) {
                path.op(pathObjectToPath, Path.Op.UNION);
            }
        }
        if (this.state.style.clipPath != null) {
            if (clipPath.boundingBox == null) {
                clipPath.boundingBox = calculatePathBounds(path);
            }
            Path pathCalculateClipPath = calculateClipPath(clipPath, clipPath.boundingBox);
            if (pathCalculateClipPath != null) {
                path.op(pathCalculateClipPath, Path.Op.INTERSECT);
            }
        }
        path.transform(matrix);
        this.state = this.stateStack.pop();
        return path;
    }

    private List<MarkerVector> calculateMarkerPositions(SVG.Line line) {
        SVG.Length length = line.f6308x1;
        float fFloatValueX = length != null ? length.floatValueX(this) : 0.0f;
        SVG.Length length2 = line.f6309y1;
        float fFloatValueY = length2 != null ? length2.floatValueY(this) : 0.0f;
        SVG.Length length3 = line.x2;
        float fFloatValueX2 = length3 != null ? length3.floatValueX(this) : 0.0f;
        SVG.Length length4 = line.y2;
        float fFloatValueY2 = length4 != null ? length4.floatValueY(this) : 0.0f;
        ArrayList arrayList = new ArrayList(2);
        float f2 = fFloatValueX2 - fFloatValueX;
        float f3 = fFloatValueY2 - fFloatValueY;
        arrayList.add(new MarkerVector(fFloatValueX, fFloatValueY, f2, f3));
        arrayList.add(new MarkerVector(fFloatValueX2, fFloatValueY2, f2, f3));
        return arrayList;
    }

    private SVG.Box calculatePathBounds(Path path) {
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        return new SVG.Box(rectF.left, rectF.top, rectF.width(), rectF.height());
    }

    private float calculateTextWidth(SVG.TextContainer textContainer) {
        TextWidthCalculator textWidthCalculator = new TextWidthCalculator(this, null);
        enumerateTextSpans(textContainer, textWidthCalculator);
        return textWidthCalculator.f6334x;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0082, code lost:
    
        if (r12 != 8) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0089  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.graphics.Matrix calculateViewBoxTransform(com.caverock.androidsvg.SVG.Box r10, com.caverock.androidsvg.SVG.Box r11, com.caverock.androidsvg.PreserveAspectRatio r12) {
        /*
            r9 = this;
            android.graphics.Matrix r0 = new android.graphics.Matrix
            r0.<init>()
            if (r12 == 0) goto L9b
            com.caverock.androidsvg.PreserveAspectRatio$Alignment r1 = r12.getAlignment()
            if (r1 != 0) goto Lf
            goto L9b
        Lf:
            float r1 = r10.width
            float r2 = r11.width
            float r1 = r1 / r2
            float r2 = r10.height
            float r3 = r11.height
            float r2 = r2 / r3
            float r3 = r11.minX
            float r3 = -r3
            float r4 = r11.minY
            float r4 = -r4
            com.caverock.androidsvg.PreserveAspectRatio r5 = com.caverock.androidsvg.PreserveAspectRatio.STRETCH
            boolean r5 = r12.equals(r5)
            if (r5 == 0) goto L35
            float r11 = r10.minX
            float r10 = r10.minY
            r0.preTranslate(r11, r10)
            r0.preScale(r1, r2)
            r0.preTranslate(r3, r4)
            return r0
        L35:
            com.caverock.androidsvg.PreserveAspectRatio$Scale r5 = r12.getScale()
            com.caverock.androidsvg.PreserveAspectRatio$Scale r6 = com.caverock.androidsvg.PreserveAspectRatio.Scale.slice
            if (r5 != r6) goto L42
            float r1 = java.lang.Math.max(r1, r2)
            goto L46
        L42:
            float r1 = java.lang.Math.min(r1, r2)
        L46:
            float r2 = r10.width
            float r2 = r2 / r1
            float r5 = r10.height
            float r5 = r5 / r1
            int[] r6 = com.caverock.androidsvg.SVGAndroidRenderer.AnonymousClass1.$SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment
            com.caverock.androidsvg.PreserveAspectRatio$Alignment r7 = r12.getAlignment()
            int r7 = r7.ordinal()
            r7 = r6[r7]
            r8 = 1073741824(0x40000000, float:2.0)
            switch(r7) {
                case 1: goto L62;
                case 2: goto L62;
                case 3: goto L62;
                case 4: goto L5e;
                case 5: goto L5e;
                case 6: goto L5e;
                default: goto L5d;
            }
        L5d:
            goto L67
        L5e:
            float r7 = r11.width
            float r7 = r7 - r2
            goto L66
        L62:
            float r7 = r11.width
            float r7 = r7 - r2
            float r7 = r7 / r8
        L66:
            float r3 = r3 - r7
        L67:
            com.caverock.androidsvg.PreserveAspectRatio$Alignment r12 = r12.getAlignment()
            int r12 = r12.ordinal()
            r12 = r6[r12]
            r2 = 2
            if (r12 == r2) goto L89
            r2 = 3
            if (r12 == r2) goto L85
            r2 = 5
            if (r12 == r2) goto L89
            r2 = 6
            if (r12 == r2) goto L85
            r2 = 7
            if (r12 == r2) goto L89
            r2 = 8
            if (r12 == r2) goto L85
            goto L8e
        L85:
            float r11 = r11.height
            float r11 = r11 - r5
            goto L8d
        L89:
            float r11 = r11.height
            float r11 = r11 - r5
            float r11 = r11 / r8
        L8d:
            float r4 = r4 - r11
        L8e:
            float r11 = r10.minX
            float r10 = r10.minY
            r0.preTranslate(r11, r10)
            r0.preScale(r1, r1)
            r0.preTranslate(r3, r4)
        L9b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGAndroidRenderer.calculateViewBoxTransform(com.caverock.androidsvg.SVG$Box, com.caverock.androidsvg.SVG$Box, com.caverock.androidsvg.PreserveAspectRatio):android.graphics.Matrix");
    }

    private void checkForClipPath(SVG.SvgElement svgElement) {
        checkForClipPath(svgElement, svgElement.boundingBox);
    }

    private void checkForClipPath_OldStyle(SVG.SvgElement svgElement, SVG.Box box) {
        SVG.SvgObject svgObjectResolveIRI = svgElement.document.resolveIRI(this.state.style.clipPath);
        if (svgObjectResolveIRI == null) {
            error("ClipPath reference '%s' not found", this.state.style.clipPath);
            return;
        }
        SVG.ClipPath clipPath = (SVG.ClipPath) svgObjectResolveIRI;
        if (clipPath.children.isEmpty()) {
            this.canvas.clipRect(0, 0, 0, 0);
            return;
        }
        Boolean bool = clipPath.clipPathUnitsAreUser;
        boolean z2 = bool == null || bool.booleanValue();
        if ((svgElement instanceof SVG.Group) && !z2) {
            warn("<clipPath clipPathUnits=\"objectBoundingBox\"> is not supported when referenced from container elements (like %s)", svgElement.getNodeName());
            return;
        }
        clipStatePush();
        if (!z2) {
            Matrix matrix = new Matrix();
            matrix.preTranslate(box.minX, box.minY);
            matrix.preScale(box.width, box.height);
            this.canvas.concat(matrix);
        }
        Matrix matrix2 = clipPath.transform;
        if (matrix2 != null) {
            this.canvas.concat(matrix2);
        }
        this.state = findInheritFromAncestorState(clipPath);
        checkForClipPath(clipPath);
        Path path = new Path();
        Iterator<SVG.SvgObject> it = clipPath.children.iterator();
        while (it.hasNext()) {
            addObjectToClip(it.next(), true, path, new Matrix());
        }
        this.canvas.clipPath(path);
        clipStatePop();
    }

    private void checkForGradientsAndPatterns(SVG.SvgElement svgElement) {
        SVG.SvgPaint svgPaint = this.state.style.fill;
        if (svgPaint instanceof SVG.PaintReference) {
            decodePaintReference(true, svgElement.boundingBox, (SVG.PaintReference) svgPaint);
        }
        SVG.SvgPaint svgPaint2 = this.state.style.stroke;
        if (svgPaint2 instanceof SVG.PaintReference) {
            decodePaintReference(false, svgElement.boundingBox, (SVG.PaintReference) svgPaint2);
        }
    }

    private Bitmap checkForImageDataURL(String str) {
        int iIndexOf;
        if (!str.startsWith("data:") || str.length() < 14 || (iIndexOf = str.indexOf(44)) < 12 || !";base64".equals(str.substring(iIndexOf - 7, iIndexOf))) {
            return null;
        }
        try {
            byte[] bArrDecode = Base64.decode(str.substring(iIndexOf + 1), 0);
            return BitmapFactory.decodeByteArray(bArrDecode, 0, bArrDecode.length);
        } catch (Exception e2) {
            Log.e(TAG, "Could not decode bad Data URL", e2);
            return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.graphics.Typeface checkGenericFont(java.lang.String r6, java.lang.Integer r7, com.caverock.androidsvg.SVG.Style.FontStyle r8) {
        /*
            r5 = this;
            com.caverock.androidsvg.SVG$Style$FontStyle r0 = com.caverock.androidsvg.SVG.Style.FontStyle.Italic
            r1 = 1
            r2 = 0
            if (r8 != r0) goto L8
            r8 = r1
            goto L9
        L8:
            r8 = r2
        L9:
            int r7 = r7.intValue()
            r0 = 500(0x1f4, float:7.0E-43)
            r3 = 3
            r4 = 2
            if (r7 <= r0) goto L19
            if (r8 == 0) goto L17
            r7 = r3
            goto L1e
        L17:
            r7 = r1
            goto L1e
        L19:
            if (r8 == 0) goto L1d
            r7 = r4
            goto L1e
        L1d:
            r7 = r2
        L1e:
            r6.hashCode()
            int r8 = r6.hashCode()
            r0 = -1
            switch(r8) {
                case -1536685117: goto L55;
                case -1431958525: goto L4c;
                case -1081737434: goto L41;
                case 109326717: goto L36;
                case 1126973893: goto L2b;
                default: goto L29;
            }
        L29:
            r1 = r0
            goto L5f
        L2b:
            java.lang.String r8 = "cursive"
            boolean r6 = r6.equals(r8)
            if (r6 != 0) goto L34
            goto L29
        L34:
            r1 = 4
            goto L5f
        L36:
            java.lang.String r8 = "serif"
            boolean r6 = r6.equals(r8)
            if (r6 != 0) goto L3f
            goto L29
        L3f:
            r1 = r3
            goto L5f
        L41:
            java.lang.String r8 = "fantasy"
            boolean r6 = r6.equals(r8)
            if (r6 != 0) goto L4a
            goto L29
        L4a:
            r1 = r4
            goto L5f
        L4c:
            java.lang.String r8 = "monospace"
            boolean r6 = r6.equals(r8)
            if (r6 != 0) goto L5f
            goto L29
        L55:
            java.lang.String r8 = "sans-serif"
            boolean r6 = r6.equals(r8)
            if (r6 != 0) goto L5e
            goto L29
        L5e:
            r1 = r2
        L5f:
            switch(r1) {
                case 0: goto L80;
                case 1: goto L79;
                case 2: goto L72;
                case 3: goto L6b;
                case 4: goto L64;
                default: goto L62;
            }
        L62:
            r6 = 0
            goto L86
        L64:
            android.graphics.Typeface r6 = android.graphics.Typeface.SANS_SERIF
            android.graphics.Typeface r6 = android.graphics.Typeface.create(r6, r7)
            goto L86
        L6b:
            android.graphics.Typeface r6 = android.graphics.Typeface.SERIF
            android.graphics.Typeface r6 = android.graphics.Typeface.create(r6, r7)
            goto L86
        L72:
            android.graphics.Typeface r6 = android.graphics.Typeface.SANS_SERIF
            android.graphics.Typeface r6 = android.graphics.Typeface.create(r6, r7)
            goto L86
        L79:
            android.graphics.Typeface r6 = android.graphics.Typeface.MONOSPACE
            android.graphics.Typeface r6 = android.graphics.Typeface.create(r6, r7)
            goto L86
        L80:
            android.graphics.Typeface r6 = android.graphics.Typeface.SANS_SERIF
            android.graphics.Typeface r6 = android.graphics.Typeface.create(r6, r7)
        L86:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGAndroidRenderer.checkGenericFont(java.lang.String, java.lang.Integer, com.caverock.androidsvg.SVG$Style$FontStyle):android.graphics.Typeface");
    }

    private void checkXMLSpaceAttribute(SVG.SvgObject svgObject) {
        Boolean bool;
        if ((svgObject instanceof SVG.SvgElementBase) && (bool = ((SVG.SvgElementBase) svgObject).spacePreserve) != null) {
            this.state.spacePreserve = bool.booleanValue();
        }
    }

    private static double checkedArcCos(double d3) {
        if (d3 < -1.0d) {
            return 3.141592653589793d;
        }
        if (d3 > 1.0d) {
            return 0.0d;
        }
        return Math.acos(d3);
    }

    private static int clamp255(float f2) {
        int i2 = (int) (f2 * 256.0f);
        if (i2 < 0) {
            return 0;
        }
        if (i2 > 255) {
            return 255;
        }
        return i2;
    }

    private void clipStatePop() {
        this.canvas.restore();
        this.state = this.stateStack.pop();
    }

    private void clipStatePush() {
        CanvasLegacy.save(this.canvas, CanvasLegacy.MATRIX_SAVE_FLAG);
        this.stateStack.push(this.state);
        this.state = new RendererState(this.state);
    }

    private static int colourWithOpacity(int i2, float f2) {
        int i3 = 255;
        int iRound = Math.round(((i2 >> 24) & 255) * f2);
        if (iRound < 0) {
            i3 = 0;
        } else if (iRound <= 255) {
            i3 = iRound;
        }
        return (i2 & 16777215) | (i3 << 24);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void debug(String str, Object... objArr) {
    }

    private void decodePaintReference(boolean z2, SVG.Box box, SVG.PaintReference paintReference) {
        SVG.SvgObject svgObjectResolveIRI = this.document.resolveIRI(paintReference.href);
        if (svgObjectResolveIRI != null) {
            if (svgObjectResolveIRI instanceof SVG.SvgLinearGradient) {
                makeLinearGradient(z2, box, (SVG.SvgLinearGradient) svgObjectResolveIRI);
                return;
            } else if (svgObjectResolveIRI instanceof SVG.SvgRadialGradient) {
                makeRadialGradient(z2, box, (SVG.SvgRadialGradient) svgObjectResolveIRI);
                return;
            } else {
                if (svgObjectResolveIRI instanceof SVG.SolidColor) {
                    setSolidColor(z2, (SVG.SolidColor) svgObjectResolveIRI);
                    return;
                }
                return;
            }
        }
        Object[] objArr = new Object[2];
        objArr[0] = z2 ? "Fill" : "Stroke";
        objArr[1] = paintReference.href;
        error("%s reference '%s' not found", objArr);
        SVG.SvgPaint svgPaint = paintReference.fallback;
        if (svgPaint != null) {
            setPaintColour(this.state, z2, svgPaint);
        } else if (z2) {
            this.state.hasFill = false;
        } else {
            this.state.hasStroke = false;
        }
    }

    private boolean display() {
        Boolean bool = this.state.style.display;
        if (bool != null) {
            return bool.booleanValue();
        }
        return true;
    }

    private void doFilledPath(SVG.SvgElement svgElement, Path path) {
        SVG.SvgPaint svgPaint = this.state.style.fill;
        if (svgPaint instanceof SVG.PaintReference) {
            SVG.SvgObject svgObjectResolveIRI = this.document.resolveIRI(((SVG.PaintReference) svgPaint).href);
            if (svgObjectResolveIRI instanceof SVG.Pattern) {
                fillWithPattern(svgElement, path, (SVG.Pattern) svgObjectResolveIRI);
                return;
            }
        }
        this.canvas.drawPath(path, this.state.fillPaint);
    }

    private void doStroke(Path path) {
        RendererState rendererState = this.state;
        if (rendererState.style.vectorEffect != SVG.Style.VectorEffect.NonScalingStroke) {
            this.canvas.drawPath(path, rendererState.strokePaint);
            return;
        }
        Matrix matrix = this.canvas.getMatrix();
        Path path2 = new Path();
        path.transform(matrix, path2);
        this.canvas.setMatrix(new Matrix());
        Shader shader = this.state.strokePaint.getShader();
        Matrix matrix2 = new Matrix();
        if (shader != null) {
            shader.getLocalMatrix(matrix2);
            Matrix matrix3 = new Matrix(matrix2);
            matrix3.postConcat(matrix);
            shader.setLocalMatrix(matrix3);
        }
        this.canvas.drawPath(path2, this.state.strokePaint);
        this.canvas.setMatrix(matrix);
        if (shader != null) {
            shader.setLocalMatrix(matrix2);
        }
    }

    private float dotProduct(float f2, float f3, float f4, float f5) {
        return (f2 * f4) + (f3 * f5);
    }

    private void enumerateTextSpans(SVG.TextContainer textContainer, TextProcessor textProcessor) {
        if (display()) {
            Iterator<SVG.SvgObject> it = textContainer.children.iterator();
            boolean z2 = true;
            while (it.hasNext()) {
                SVG.SvgObject next = it.next();
                if (next instanceof SVG.TextSequence) {
                    textProcessor.processText(textXMLSpaceTransform(((SVG.TextSequence) next).text, z2, !it.hasNext()));
                } else {
                    processTextChild(next, textProcessor);
                }
                z2 = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void error(String str, Object... objArr) {
        Log.e(TAG, String.format(str, objArr));
    }

    private void extractRawText(SVG.TextContainer textContainer, StringBuilder sb) {
        Iterator<SVG.SvgObject> it = textContainer.children.iterator();
        boolean z2 = true;
        while (it.hasNext()) {
            SVG.SvgObject next = it.next();
            if (next instanceof SVG.TextContainer) {
                extractRawText((SVG.TextContainer) next, sb);
            } else if (next instanceof SVG.TextSequence) {
                sb.append(textXMLSpaceTransform(((SVG.TextSequence) next).text, z2, !it.hasNext()));
            }
            z2 = false;
        }
    }

    private void fillInChainedGradientFields(SVG.GradientElement gradientElement, String str) {
        SVG.SvgObject svgObjectResolveIRI = gradientElement.document.resolveIRI(str);
        if (svgObjectResolveIRI == null) {
            warn("Gradient reference '%s' not found", str);
            return;
        }
        if (!(svgObjectResolveIRI instanceof SVG.GradientElement)) {
            error("Gradient href attributes must point to other gradient elements", new Object[0]);
            return;
        }
        if (svgObjectResolveIRI == gradientElement) {
            error("Circular reference in gradient href attribute '%s'", str);
            return;
        }
        SVG.GradientElement gradientElement2 = (SVG.GradientElement) svgObjectResolveIRI;
        if (gradientElement.gradientUnitsAreUser == null) {
            gradientElement.gradientUnitsAreUser = gradientElement2.gradientUnitsAreUser;
        }
        if (gradientElement.gradientTransform == null) {
            gradientElement.gradientTransform = gradientElement2.gradientTransform;
        }
        if (gradientElement.spreadMethod == null) {
            gradientElement.spreadMethod = gradientElement2.spreadMethod;
        }
        if (gradientElement.children.isEmpty()) {
            gradientElement.children = gradientElement2.children;
        }
        try {
            if (gradientElement instanceof SVG.SvgLinearGradient) {
                fillInChainedGradientFields((SVG.SvgLinearGradient) gradientElement, (SVG.SvgLinearGradient) svgObjectResolveIRI);
            } else {
                fillInChainedGradientFields((SVG.SvgRadialGradient) gradientElement, (SVG.SvgRadialGradient) svgObjectResolveIRI);
            }
        } catch (ClassCastException unused) {
        }
        String str2 = gradientElement2.href;
        if (str2 != null) {
            fillInChainedGradientFields(gradientElement, str2);
        }
    }

    private void fillInChainedPatternFields(SVG.Pattern pattern, String str) {
        SVG.SvgObject svgObjectResolveIRI = pattern.document.resolveIRI(str);
        if (svgObjectResolveIRI == null) {
            warn("Pattern reference '%s' not found", str);
            return;
        }
        if (!(svgObjectResolveIRI instanceof SVG.Pattern)) {
            error("Pattern href attributes must point to other pattern elements", new Object[0]);
            return;
        }
        if (svgObjectResolveIRI == pattern) {
            error("Circular reference in pattern href attribute '%s'", str);
            return;
        }
        SVG.Pattern pattern2 = (SVG.Pattern) svgObjectResolveIRI;
        if (pattern.patternUnitsAreUser == null) {
            pattern.patternUnitsAreUser = pattern2.patternUnitsAreUser;
        }
        if (pattern.patternContentUnitsAreUser == null) {
            pattern.patternContentUnitsAreUser = pattern2.patternContentUnitsAreUser;
        }
        if (pattern.patternTransform == null) {
            pattern.patternTransform = pattern2.patternTransform;
        }
        if (pattern.f6313x == null) {
            pattern.f6313x = pattern2.f6313x;
        }
        if (pattern.f6314y == null) {
            pattern.f6314y = pattern2.f6314y;
        }
        if (pattern.width == null) {
            pattern.width = pattern2.width;
        }
        if (pattern.height == null) {
            pattern.height = pattern2.height;
        }
        if (pattern.children.isEmpty()) {
            pattern.children = pattern2.children;
        }
        if (pattern.viewBox == null) {
            pattern.viewBox = pattern2.viewBox;
        }
        if (pattern.preserveAspectRatio == null) {
            pattern.preserveAspectRatio = pattern2.preserveAspectRatio;
        }
        String str2 = pattern2.href;
        if (str2 != null) {
            fillInChainedPatternFields(pattern, str2);
        }
    }

    private void fillWithPattern(SVG.SvgElement svgElement, Path path, SVG.Pattern pattern) {
        float fFloatValueX;
        float fFloatValueY;
        float fFloatValueY2;
        float fFloatValueX2;
        float f2;
        Boolean bool = pattern.patternUnitsAreUser;
        boolean z2 = bool != null && bool.booleanValue();
        String str = pattern.href;
        if (str != null) {
            fillInChainedPatternFields(pattern, str);
        }
        if (z2) {
            SVG.Length length = pattern.f6313x;
            fFloatValueX = length != null ? length.floatValueX(this) : 0.0f;
            SVG.Length length2 = pattern.f6314y;
            fFloatValueY2 = length2 != null ? length2.floatValueY(this) : 0.0f;
            SVG.Length length3 = pattern.width;
            fFloatValueX2 = length3 != null ? length3.floatValueX(this) : 0.0f;
            SVG.Length length4 = pattern.height;
            fFloatValueY = length4 != null ? length4.floatValueY(this) : 0.0f;
        } else {
            SVG.Length length5 = pattern.f6313x;
            float fFloatValue = length5 != null ? length5.floatValue(this, 1.0f) : 0.0f;
            SVG.Length length6 = pattern.f6314y;
            float fFloatValue2 = length6 != null ? length6.floatValue(this, 1.0f) : 0.0f;
            SVG.Length length7 = pattern.width;
            float fFloatValue3 = length7 != null ? length7.floatValue(this, 1.0f) : 0.0f;
            SVG.Length length8 = pattern.height;
            float fFloatValue4 = length8 != null ? length8.floatValue(this, 1.0f) : 0.0f;
            SVG.Box box = svgElement.boundingBox;
            float f3 = box.minX;
            float f4 = box.width;
            fFloatValueX = (fFloatValue * f4) + f3;
            float f5 = box.minY;
            float f6 = box.height;
            float f7 = fFloatValue3 * f4;
            fFloatValueY = fFloatValue4 * f6;
            fFloatValueY2 = (fFloatValue2 * f6) + f5;
            fFloatValueX2 = f7;
        }
        if (fFloatValueX2 == 0.0f || fFloatValueY == 0.0f) {
            return;
        }
        PreserveAspectRatio preserveAspectRatio = pattern.preserveAspectRatio;
        if (preserveAspectRatio == null) {
            preserveAspectRatio = PreserveAspectRatio.LETTERBOX;
        }
        statePush();
        this.canvas.clipPath(path);
        RendererState rendererState = new RendererState();
        updateStyle(rendererState, SVG.Style.getDefaultStyle());
        rendererState.style.overflow = Boolean.FALSE;
        this.state = findInheritFromAncestorState(pattern, rendererState);
        SVG.Box box2 = svgElement.boundingBox;
        Matrix matrix = pattern.patternTransform;
        if (matrix != null) {
            this.canvas.concat(matrix);
            Matrix matrix2 = new Matrix();
            if (pattern.patternTransform.invert(matrix2)) {
                SVG.Box box3 = svgElement.boundingBox;
                SVG.Box box4 = svgElement.boundingBox;
                SVG.Box box5 = svgElement.boundingBox;
                float[] fArr = {box3.minX, box3.minY, box3.maxX(), box4.minY, box4.maxX(), svgElement.boundingBox.maxY(), box5.minX, box5.maxY()};
                matrix2.mapPoints(fArr);
                float f8 = fArr[0];
                float f9 = fArr[1];
                RectF rectF = new RectF(f8, f9, f8, f9);
                for (int i2 = 2; i2 <= 6; i2 += 2) {
                    float f10 = fArr[i2];
                    if (f10 < rectF.left) {
                        rectF.left = f10;
                    }
                    if (f10 > rectF.right) {
                        rectF.right = f10;
                    }
                    float f11 = fArr[i2 + 1];
                    if (f11 < rectF.top) {
                        rectF.top = f11;
                    }
                    if (f11 > rectF.bottom) {
                        rectF.bottom = f11;
                    }
                }
                float f12 = rectF.left;
                float f13 = rectF.top;
                box2 = new SVG.Box(f12, f13, rectF.right - f12, rectF.bottom - f13);
            }
        }
        float fFloor = fFloatValueX + (((float) Math.floor((box2.minX - fFloatValueX) / fFloatValueX2)) * fFloatValueX2);
        float fMaxX = box2.maxX();
        float fMaxY = box2.maxY();
        SVG.Box box6 = new SVG.Box(0.0f, 0.0f, fFloatValueX2, fFloatValueY);
        boolean zPushLayer = pushLayer();
        for (float fFloor2 = fFloatValueY2 + (((float) Math.floor((box2.minY - fFloatValueY2) / fFloatValueY)) * fFloatValueY); fFloor2 < fMaxY; fFloor2 += fFloatValueY) {
            float f14 = fFloor;
            while (f14 < fMaxX) {
                box6.minX = f14;
                box6.minY = fFloor2;
                statePush();
                if (this.state.style.overflow.booleanValue()) {
                    f2 = fFloor;
                } else {
                    f2 = fFloor;
                    setClipRect(box6.minX, box6.minY, box6.width, box6.height);
                }
                SVG.Box box7 = pattern.viewBox;
                if (box7 != null) {
                    this.canvas.concat(calculateViewBoxTransform(box6, box7, preserveAspectRatio));
                } else {
                    Boolean bool2 = pattern.patternContentUnitsAreUser;
                    boolean z3 = bool2 == null || bool2.booleanValue();
                    this.canvas.translate(f14, fFloor2);
                    if (!z3) {
                        Canvas canvas = this.canvas;
                        SVG.Box box8 = svgElement.boundingBox;
                        canvas.scale(box8.width, box8.height);
                    }
                }
                Iterator<SVG.SvgObject> it = pattern.children.iterator();
                while (it.hasNext()) {
                    render(it.next());
                }
                statePop();
                f14 += fFloatValueX2;
                fFloor = f2;
            }
        }
        if (zPushLayer) {
            popLayer(pattern);
        }
        statePop();
    }

    private RendererState findInheritFromAncestorState(SVG.SvgObject svgObject) {
        RendererState rendererState = new RendererState();
        updateStyle(rendererState, SVG.Style.getDefaultStyle());
        return findInheritFromAncestorState(svgObject, rendererState);
    }

    private SVG.Style.TextAnchor getAnchorPosition() {
        SVG.Style.TextAnchor textAnchor;
        SVG.Style style = this.state.style;
        if (style.direction == SVG.Style.TextDirection.LTR || (textAnchor = style.textAnchor) == SVG.Style.TextAnchor.Middle) {
            return style.textAnchor;
        }
        SVG.Style.TextAnchor textAnchor2 = SVG.Style.TextAnchor.Start;
        return textAnchor == textAnchor2 ? SVG.Style.TextAnchor.End : textAnchor2;
    }

    private Path.FillType getClipRuleFromState() {
        SVG.Style.FillRule fillRule = this.state.style.clipRule;
        return (fillRule == null || fillRule != SVG.Style.FillRule.EvenOdd) ? Path.FillType.WINDING : Path.FillType.EVEN_ODD;
    }

    private Path.FillType getFillTypeFromState() {
        SVG.Style.FillRule fillRule = this.state.style.fillRule;
        return (fillRule == null || fillRule != SVG.Style.FillRule.EvenOdd) ? Path.FillType.WINDING : Path.FillType.EVEN_ODD;
    }

    private static synchronized void initialiseSupportedFeaturesMap() {
        HashSet<String> hashSet = new HashSet<>();
        supportedFeatures = hashSet;
        hashSet.add("Structure");
        supportedFeatures.add("BasicStructure");
        supportedFeatures.add("ConditionalProcessing");
        supportedFeatures.add("Image");
        supportedFeatures.add("Style");
        supportedFeatures.add("ViewportAttribute");
        supportedFeatures.add("Shape");
        supportedFeatures.add("BasicText");
        supportedFeatures.add("PaintAttribute");
        supportedFeatures.add("BasicPaintAttribute");
        supportedFeatures.add("OpacityAttribute");
        supportedFeatures.add("BasicGraphicsAttribute");
        supportedFeatures.add("Marker");
        supportedFeatures.add("Gradient");
        supportedFeatures.add("Pattern");
        supportedFeatures.add("Clip");
        supportedFeatures.add("BasicClip");
        supportedFeatures.add("Mask");
        supportedFeatures.add("View");
    }

    private boolean isSpecified(SVG.Style style, long j2) {
        return (style.specifiedFlags & j2) != 0;
    }

    private void makeLinearGradient(boolean z2, SVG.Box box, SVG.SvgLinearGradient svgLinearGradient) {
        float f2;
        float fFloatValue;
        float f3;
        float f4;
        String str = svgLinearGradient.href;
        if (str != null) {
            fillInChainedGradientFields(svgLinearGradient, str);
        }
        Boolean bool = svgLinearGradient.gradientUnitsAreUser;
        int i2 = 0;
        boolean z3 = bool != null && bool.booleanValue();
        RendererState rendererState = this.state;
        Paint paint = z2 ? rendererState.fillPaint : rendererState.strokePaint;
        if (z3) {
            SVG.Box currentViewPortInUserUnits = getCurrentViewPortInUserUnits();
            SVG.Length length = svgLinearGradient.f6319x1;
            float fFloatValueX = length != null ? length.floatValueX(this) : 0.0f;
            SVG.Length length2 = svgLinearGradient.f6320y1;
            float fFloatValueY = length2 != null ? length2.floatValueY(this) : 0.0f;
            SVG.Length length3 = svgLinearGradient.x2;
            float fFloatValueX2 = length3 != null ? length3.floatValueX(this) : currentViewPortInUserUnits.width;
            SVG.Length length4 = svgLinearGradient.y2;
            f4 = fFloatValueX2;
            f2 = fFloatValueX;
            f3 = fFloatValueY;
            fFloatValue = length4 != null ? length4.floatValueY(this) : 0.0f;
        } else {
            SVG.Length length5 = svgLinearGradient.f6319x1;
            float fFloatValue2 = length5 != null ? length5.floatValue(this, 1.0f) : 0.0f;
            SVG.Length length6 = svgLinearGradient.f6320y1;
            float fFloatValue3 = length6 != null ? length6.floatValue(this, 1.0f) : 0.0f;
            SVG.Length length7 = svgLinearGradient.x2;
            float fFloatValue4 = length7 != null ? length7.floatValue(this, 1.0f) : 1.0f;
            SVG.Length length8 = svgLinearGradient.y2;
            f2 = fFloatValue2;
            fFloatValue = length8 != null ? length8.floatValue(this, 1.0f) : 0.0f;
            f3 = fFloatValue3;
            f4 = fFloatValue4;
        }
        statePush();
        this.state = findInheritFromAncestorState(svgLinearGradient);
        Matrix matrix = new Matrix();
        if (!z3) {
            matrix.preTranslate(box.minX, box.minY);
            matrix.preScale(box.width, box.height);
        }
        Matrix matrix2 = svgLinearGradient.gradientTransform;
        if (matrix2 != null) {
            matrix.preConcat(matrix2);
        }
        int size = svgLinearGradient.children.size();
        if (size == 0) {
            statePop();
            if (z2) {
                this.state.hasFill = false;
                return;
            } else {
                this.state.hasStroke = false;
                return;
            }
        }
        int[] iArr = new int[size];
        float[] fArr = new float[size];
        Iterator<SVG.SvgObject> it = svgLinearGradient.children.iterator();
        float f5 = -1.0f;
        while (it.hasNext()) {
            SVG.Stop stop = (SVG.Stop) it.next();
            Float f6 = stop.offset;
            float fFloatValue5 = f6 != null ? f6.floatValue() : 0.0f;
            if (i2 == 0 || fFloatValue5 >= f5) {
                fArr[i2] = fFloatValue5;
                f5 = fFloatValue5;
            } else {
                fArr[i2] = f5;
            }
            statePush();
            updateStyleForElement(this.state, stop);
            SVG.Style style = this.state.style;
            SVG.Colour colour = (SVG.Colour) style.stopColor;
            if (colour == null) {
                colour = SVG.Colour.BLACK;
            }
            iArr[i2] = colourWithOpacity(colour.colour, style.stopOpacity.floatValue());
            i2++;
            statePop();
        }
        if ((f2 == f4 && f3 == fFloatValue) || size == 1) {
            statePop();
            paint.setColor(iArr[size - 1]);
            return;
        }
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        SVG.GradientSpread gradientSpread = svgLinearGradient.spreadMethod;
        if (gradientSpread != null) {
            if (gradientSpread == SVG.GradientSpread.reflect) {
                tileMode = Shader.TileMode.MIRROR;
            } else if (gradientSpread == SVG.GradientSpread.repeat) {
                tileMode = Shader.TileMode.REPEAT;
            }
        }
        statePop();
        LinearGradient linearGradient = new LinearGradient(f2, f3, f4, fFloatValue, iArr, fArr, tileMode);
        linearGradient.setLocalMatrix(matrix);
        paint.setShader(linearGradient);
        paint.setAlpha(clamp255(this.state.style.fillOpacity.floatValue()));
    }

    private Path makePathAndBoundingBox(SVG.Line line) {
        SVG.Length length = line.f6308x1;
        float fFloatValueX = length == null ? 0.0f : length.floatValueX(this);
        SVG.Length length2 = line.f6309y1;
        float fFloatValueY = length2 == null ? 0.0f : length2.floatValueY(this);
        SVG.Length length3 = line.x2;
        float fFloatValueX2 = length3 == null ? 0.0f : length3.floatValueX(this);
        SVG.Length length4 = line.y2;
        float fFloatValueY2 = length4 != null ? length4.floatValueY(this) : 0.0f;
        if (line.boundingBox == null) {
            line.boundingBox = new SVG.Box(Math.min(fFloatValueX, fFloatValueX2), Math.min(fFloatValueY, fFloatValueY2), Math.abs(fFloatValueX2 - fFloatValueX), Math.abs(fFloatValueY2 - fFloatValueY));
        }
        Path path = new Path();
        path.moveTo(fFloatValueX, fFloatValueY);
        path.lineTo(fFloatValueX2, fFloatValueY2);
        return path;
    }

    private void makeRadialGradient(boolean z2, SVG.Box box, SVG.SvgRadialGradient svgRadialGradient) {
        float f2;
        float fFloatValue;
        float f3;
        String str = svgRadialGradient.href;
        if (str != null) {
            fillInChainedGradientFields(svgRadialGradient, str);
        }
        Boolean bool = svgRadialGradient.gradientUnitsAreUser;
        int i2 = 0;
        boolean z3 = bool != null && bool.booleanValue();
        RendererState rendererState = this.state;
        Paint paint = z2 ? rendererState.fillPaint : rendererState.strokePaint;
        if (z3) {
            SVG.Length length = new SVG.Length(50.0f, SVG.Unit.percent);
            SVG.Length length2 = svgRadialGradient.cx;
            float fFloatValueX = length2 != null ? length2.floatValueX(this) : length.floatValueX(this);
            SVG.Length length3 = svgRadialGradient.cy;
            float fFloatValueY = length3 != null ? length3.floatValueY(this) : length.floatValueY(this);
            SVG.Length length4 = svgRadialGradient.f6321r;
            fFloatValue = length4 != null ? length4.floatValue(this) : length.floatValue(this);
            f2 = fFloatValueX;
            f3 = fFloatValueY;
        } else {
            SVG.Length length5 = svgRadialGradient.cx;
            float fFloatValue2 = length5 != null ? length5.floatValue(this, 1.0f) : 0.5f;
            SVG.Length length6 = svgRadialGradient.cy;
            float fFloatValue3 = length6 != null ? length6.floatValue(this, 1.0f) : 0.5f;
            SVG.Length length7 = svgRadialGradient.f6321r;
            f2 = fFloatValue2;
            fFloatValue = length7 != null ? length7.floatValue(this, 1.0f) : 0.5f;
            f3 = fFloatValue3;
        }
        statePush();
        this.state = findInheritFromAncestorState(svgRadialGradient);
        Matrix matrix = new Matrix();
        if (!z3) {
            matrix.preTranslate(box.minX, box.minY);
            matrix.preScale(box.width, box.height);
        }
        Matrix matrix2 = svgRadialGradient.gradientTransform;
        if (matrix2 != null) {
            matrix.preConcat(matrix2);
        }
        int size = svgRadialGradient.children.size();
        if (size == 0) {
            statePop();
            if (z2) {
                this.state.hasFill = false;
                return;
            } else {
                this.state.hasStroke = false;
                return;
            }
        }
        int[] iArr = new int[size];
        float[] fArr = new float[size];
        Iterator<SVG.SvgObject> it = svgRadialGradient.children.iterator();
        float f4 = -1.0f;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            SVG.Stop stop = (SVG.Stop) it.next();
            Float f5 = stop.offset;
            float fFloatValue4 = f5 != null ? f5.floatValue() : 0.0f;
            if (i2 == 0 || fFloatValue4 >= f4) {
                fArr[i2] = fFloatValue4;
                f4 = fFloatValue4;
            } else {
                fArr[i2] = f4;
            }
            statePush();
            updateStyleForElement(this.state, stop);
            SVG.Style style = this.state.style;
            SVG.Colour colour = (SVG.Colour) style.stopColor;
            if (colour == null) {
                colour = SVG.Colour.BLACK;
            }
            iArr[i2] = colourWithOpacity(colour.colour, style.stopOpacity.floatValue());
            i2++;
            statePop();
        }
        if (fFloatValue == 0.0f || size == 1) {
            statePop();
            paint.setColor(iArr[size - 1]);
            return;
        }
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        SVG.GradientSpread gradientSpread = svgRadialGradient.spreadMethod;
        if (gradientSpread != null) {
            if (gradientSpread == SVG.GradientSpread.reflect) {
                tileMode = Shader.TileMode.MIRROR;
            } else if (gradientSpread == SVG.GradientSpread.repeat) {
                tileMode = Shader.TileMode.REPEAT;
            }
        }
        statePop();
        RadialGradient radialGradient = new RadialGradient(f2, f3, fFloatValue, iArr, fArr, tileMode);
        radialGradient.setLocalMatrix(matrix);
        paint.setShader(radialGradient);
        paint.setAlpha(clamp255(this.state.style.fillOpacity.floatValue()));
    }

    private SVG.Box makeViewPort(SVG.Length length, SVG.Length length2, SVG.Length length3, SVG.Length length4) {
        float fFloatValueX = length != null ? length.floatValueX(this) : 0.0f;
        float fFloatValueY = length2 != null ? length2.floatValueY(this) : 0.0f;
        SVG.Box currentViewPortInUserUnits = getCurrentViewPortInUserUnits();
        return new SVG.Box(fFloatValueX, fFloatValueY, length3 != null ? length3.floatValueX(this) : currentViewPortInUserUnits.width, length4 != null ? length4.floatValueY(this) : currentViewPortInUserUnits.height);
    }

    @TargetApi(19)
    private Path objectToPath(SVG.SvgElement svgElement, boolean z2) {
        Path pathMakePathAndBoundingBox;
        Path pathCalculateClipPath;
        this.stateStack.push(this.state);
        RendererState rendererState = new RendererState(this.state);
        this.state = rendererState;
        updateStyleForElement(rendererState, svgElement);
        if (!display() || !visible()) {
            this.state = this.stateStack.pop();
            return null;
        }
        if (svgElement instanceof SVG.Use) {
            if (!z2) {
                error("<use> elements inside a <clipPath> cannot reference another <use>", new Object[0]);
            }
            SVG.Use use = (SVG.Use) svgElement;
            SVG.SvgObject svgObjectResolveIRI = svgElement.document.resolveIRI(use.href);
            if (svgObjectResolveIRI == null) {
                error("Use reference '%s' not found", use.href);
                this.state = this.stateStack.pop();
                return null;
            }
            if (!(svgObjectResolveIRI instanceof SVG.SvgElement)) {
                this.state = this.stateStack.pop();
                return null;
            }
            pathMakePathAndBoundingBox = objectToPath((SVG.SvgElement) svgObjectResolveIRI, false);
            if (pathMakePathAndBoundingBox == null) {
                return null;
            }
            if (use.boundingBox == null) {
                use.boundingBox = calculatePathBounds(pathMakePathAndBoundingBox);
            }
            Matrix matrix = use.transform;
            if (matrix != null) {
                pathMakePathAndBoundingBox.transform(matrix);
            }
        } else if (svgElement instanceof SVG.GraphicsElement) {
            SVG.GraphicsElement graphicsElement = (SVG.GraphicsElement) svgElement;
            if (svgElement instanceof SVG.Path) {
                pathMakePathAndBoundingBox = new PathConverter(((SVG.Path) svgElement).f6312d).getPath();
                if (svgElement.boundingBox == null) {
                    svgElement.boundingBox = calculatePathBounds(pathMakePathAndBoundingBox);
                }
            } else {
                pathMakePathAndBoundingBox = svgElement instanceof SVG.Rect ? makePathAndBoundingBox((SVG.Rect) svgElement) : svgElement instanceof SVG.Circle ? makePathAndBoundingBox((SVG.Circle) svgElement) : svgElement instanceof SVG.Ellipse ? makePathAndBoundingBox((SVG.Ellipse) svgElement) : svgElement instanceof SVG.PolyLine ? makePathAndBoundingBox((SVG.PolyLine) svgElement) : null;
            }
            if (pathMakePathAndBoundingBox == null) {
                return null;
            }
            if (graphicsElement.boundingBox == null) {
                graphicsElement.boundingBox = calculatePathBounds(pathMakePathAndBoundingBox);
            }
            Matrix matrix2 = graphicsElement.transform;
            if (matrix2 != null) {
                pathMakePathAndBoundingBox.transform(matrix2);
            }
            pathMakePathAndBoundingBox.setFillType(getClipRuleFromState());
        } else {
            if (!(svgElement instanceof SVG.Text)) {
                error("Invalid %s element found in clipPath definition", svgElement.getNodeName());
                return null;
            }
            SVG.Text text = (SVG.Text) svgElement;
            pathMakePathAndBoundingBox = makePathAndBoundingBox(text);
            if (pathMakePathAndBoundingBox == null) {
                return null;
            }
            Matrix matrix3 = text.transform;
            if (matrix3 != null) {
                pathMakePathAndBoundingBox.transform(matrix3);
            }
            pathMakePathAndBoundingBox.setFillType(getClipRuleFromState());
        }
        if (this.state.style.clipPath != null && (pathCalculateClipPath = calculateClipPath(svgElement, svgElement.boundingBox)) != null) {
            pathMakePathAndBoundingBox.op(pathCalculateClipPath, Path.Op.INTERSECT);
        }
        this.state = this.stateStack.pop();
        return pathMakePathAndBoundingBox;
    }

    private void parentPop() {
        this.parentStack.pop();
        this.matrixStack.pop();
    }

    private void parentPush(SVG.SvgContainer svgContainer) {
        this.parentStack.push(svgContainer);
        this.matrixStack.push(this.canvas.getMatrix());
    }

    private void popLayer(SVG.SvgElement svgElement) {
        popLayer(svgElement, svgElement.boundingBox);
    }

    private void processTextChild(SVG.SvgObject svgObject, TextProcessor textProcessor) {
        float f2;
        float fFloatValueY;
        float fFloatValueX;
        SVG.Style.TextAnchor anchorPosition;
        if (textProcessor.doTextContainer((SVG.TextContainer) svgObject)) {
            if (svgObject instanceof SVG.TextPath) {
                statePush();
                renderTextPath((SVG.TextPath) svgObject);
                statePop();
                return;
            }
            if (!(svgObject instanceof SVG.TSpan)) {
                if (svgObject instanceof SVG.TRef) {
                    statePush();
                    SVG.TRef tRef = (SVG.TRef) svgObject;
                    updateStyleForElement(this.state, tRef);
                    if (display()) {
                        checkForGradientsAndPatterns((SVG.SvgElement) tRef.getTextRoot());
                        SVG.SvgObject svgObjectResolveIRI = svgObject.document.resolveIRI(tRef.href);
                        if (svgObjectResolveIRI == null || !(svgObjectResolveIRI instanceof SVG.TextContainer)) {
                            error("Tref reference '%s' not found", tRef.href);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            extractRawText((SVG.TextContainer) svgObjectResolveIRI, sb);
                            if (sb.length() > 0) {
                                textProcessor.processText(sb.toString());
                            }
                        }
                    }
                    statePop();
                    return;
                }
                return;
            }
            debug("TSpan render", new Object[0]);
            statePush();
            SVG.TSpan tSpan = (SVG.TSpan) svgObject;
            updateStyleForElement(this.state, tSpan);
            if (display()) {
                List<SVG.Length> list = tSpan.f6322x;
                boolean z2 = list != null && list.size() > 0;
                boolean z3 = textProcessor instanceof PlainTextDrawer;
                float fFloatValueY2 = 0.0f;
                if (z3) {
                    float fFloatValueX2 = !z2 ? ((PlainTextDrawer) textProcessor).f6328x : tSpan.f6322x.get(0).floatValueX(this);
                    List<SVG.Length> list2 = tSpan.f6323y;
                    fFloatValueY = (list2 == null || list2.size() == 0) ? ((PlainTextDrawer) textProcessor).f6329y : tSpan.f6323y.get(0).floatValueY(this);
                    List<SVG.Length> list3 = tSpan.dx;
                    fFloatValueX = (list3 == null || list3.size() == 0) ? 0.0f : tSpan.dx.get(0).floatValueX(this);
                    List<SVG.Length> list4 = tSpan.dy;
                    if (list4 != null && list4.size() != 0) {
                        fFloatValueY2 = tSpan.dy.get(0).floatValueY(this);
                    }
                    f2 = fFloatValueY2;
                    fFloatValueY2 = fFloatValueX2;
                } else {
                    f2 = 0.0f;
                    fFloatValueY = 0.0f;
                    fFloatValueX = 0.0f;
                }
                if (z2 && (anchorPosition = getAnchorPosition()) != SVG.Style.TextAnchor.Start) {
                    float fCalculateTextWidth = calculateTextWidth(tSpan);
                    if (anchorPosition == SVG.Style.TextAnchor.Middle) {
                        fCalculateTextWidth /= 2.0f;
                    }
                    fFloatValueY2 -= fCalculateTextWidth;
                }
                checkForGradientsAndPatterns((SVG.SvgElement) tSpan.getTextRoot());
                if (z3) {
                    PlainTextDrawer plainTextDrawer = (PlainTextDrawer) textProcessor;
                    plainTextDrawer.f6328x = fFloatValueY2 + fFloatValueX;
                    plainTextDrawer.f6329y = fFloatValueY + f2;
                }
                boolean zPushLayer = pushLayer();
                enumerateTextSpans(tSpan, textProcessor);
                if (zPushLayer) {
                    popLayer(tSpan);
                }
            }
            statePop();
        }
    }

    private boolean pushLayer() {
        SVG.SvgObject svgObjectResolveIRI;
        if (!requiresCompositing()) {
            return false;
        }
        this.canvas.saveLayerAlpha(null, clamp255(this.state.style.opacity.floatValue()), 31);
        this.stateStack.push(this.state);
        RendererState rendererState = new RendererState(this.state);
        this.state = rendererState;
        String str = rendererState.style.mask;
        if (str != null && ((svgObjectResolveIRI = this.document.resolveIRI(str)) == null || !(svgObjectResolveIRI instanceof SVG.Mask))) {
            error("Mask reference '%s' not found", this.state.style.mask);
            this.state.style.mask = null;
        }
        return true;
    }

    private MarkerVector realignMarkerMid(MarkerVector markerVector, MarkerVector markerVector2, MarkerVector markerVector3) {
        float fDotProduct = dotProduct(markerVector2.dx, markerVector2.dy, markerVector2.f6326x - markerVector.f6326x, markerVector2.f6327y - markerVector.f6327y);
        if (fDotProduct == 0.0f) {
            fDotProduct = dotProduct(markerVector2.dx, markerVector2.dy, markerVector3.f6326x - markerVector2.f6326x, markerVector3.f6327y - markerVector2.f6327y);
        }
        if (fDotProduct > 0.0f) {
            return markerVector2;
        }
        if (fDotProduct == 0.0f && (markerVector2.dx > 0.0f || markerVector2.dy >= 0.0f)) {
            return markerVector2;
        }
        markerVector2.dx = -markerVector2.dx;
        markerVector2.dy = -markerVector2.dy;
        return markerVector2;
    }

    private void render(SVG.SvgObject svgObject) {
        if (svgObject instanceof SVG.NotDirectlyRendered) {
            return;
        }
        statePush();
        checkXMLSpaceAttribute(svgObject);
        if (svgObject instanceof SVG.Svg) {
            render((SVG.Svg) svgObject);
        } else if (svgObject instanceof SVG.Use) {
            render((SVG.Use) svgObject);
        } else if (svgObject instanceof SVG.Switch) {
            render((SVG.Switch) svgObject);
        } else if (svgObject instanceof SVG.Group) {
            render((SVG.Group) svgObject);
        } else if (svgObject instanceof SVG.Image) {
            render((SVG.Image) svgObject);
        } else if (svgObject instanceof SVG.Path) {
            render((SVG.Path) svgObject);
        } else if (svgObject instanceof SVG.Rect) {
            render((SVG.Rect) svgObject);
        } else if (svgObject instanceof SVG.Circle) {
            render((SVG.Circle) svgObject);
        } else if (svgObject instanceof SVG.Ellipse) {
            render((SVG.Ellipse) svgObject);
        } else if (svgObject instanceof SVG.Line) {
            render((SVG.Line) svgObject);
        } else if (svgObject instanceof SVG.Polygon) {
            render((SVG.Polygon) svgObject);
        } else if (svgObject instanceof SVG.PolyLine) {
            render((SVG.PolyLine) svgObject);
        } else if (svgObject instanceof SVG.Text) {
            render((SVG.Text) svgObject);
        }
        statePop();
    }

    private void renderChildren(SVG.SvgContainer svgContainer, boolean z2) {
        if (z2) {
            parentPush(svgContainer);
        }
        Iterator<SVG.SvgObject> it = svgContainer.getChildren().iterator();
        while (it.hasNext()) {
            render(it.next());
        }
        if (z2) {
            parentPop();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x00ff, code lost:
    
        if (r7 != 8) goto L67;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0115  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void renderMarker(com.caverock.androidsvg.SVG.Marker r12, com.caverock.androidsvg.SVGAndroidRenderer.MarkerVector r13) {
        /*
            Method dump skipped, instructions count: 350
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGAndroidRenderer.renderMarker(com.caverock.androidsvg.SVG$Marker, com.caverock.androidsvg.SVGAndroidRenderer$MarkerVector):void");
    }

    private void renderMarkers(SVG.GraphicsElement graphicsElement) {
        SVG.Marker marker;
        SVG.Marker marker2;
        SVG.Marker marker3;
        int size;
        SVG.Style style = this.state.style;
        String str = style.markerStart;
        if (str == null && style.markerMid == null && style.markerEnd == null) {
            return;
        }
        if (str == null) {
            marker = null;
        } else {
            SVG.SvgObject svgObjectResolveIRI = graphicsElement.document.resolveIRI(str);
            if (svgObjectResolveIRI != null) {
                marker = (SVG.Marker) svgObjectResolveIRI;
            } else {
                error("Marker reference '%s' not found", this.state.style.markerStart);
                marker = null;
            }
        }
        String str2 = this.state.style.markerMid;
        if (str2 == null) {
            marker2 = null;
        } else {
            SVG.SvgObject svgObjectResolveIRI2 = graphicsElement.document.resolveIRI(str2);
            if (svgObjectResolveIRI2 != null) {
                marker2 = (SVG.Marker) svgObjectResolveIRI2;
            } else {
                error("Marker reference '%s' not found", this.state.style.markerMid);
                marker2 = null;
            }
        }
        String str3 = this.state.style.markerEnd;
        if (str3 == null) {
            marker3 = null;
        } else {
            SVG.SvgObject svgObjectResolveIRI3 = graphicsElement.document.resolveIRI(str3);
            if (svgObjectResolveIRI3 != null) {
                marker3 = (SVG.Marker) svgObjectResolveIRI3;
            } else {
                error("Marker reference '%s' not found", this.state.style.markerEnd);
                marker3 = null;
            }
        }
        List<MarkerVector> markers = graphicsElement instanceof SVG.Path ? new MarkerPositionCalculator(((SVG.Path) graphicsElement).f6312d).getMarkers() : graphicsElement instanceof SVG.Line ? calculateMarkerPositions((SVG.Line) graphicsElement) : calculateMarkerPositions((SVG.PolyLine) graphicsElement);
        if (markers == null || (size = markers.size()) == 0) {
            return;
        }
        SVG.Style style2 = this.state.style;
        style2.markerEnd = null;
        style2.markerMid = null;
        style2.markerStart = null;
        if (marker != null) {
            renderMarker(marker, markers.get(0));
        }
        if (marker2 != null && markers.size() > 2) {
            MarkerVector markerVectorRealignMarkerMid = markers.get(0);
            MarkerVector markerVector = markers.get(1);
            int i2 = 1;
            while (i2 < size - 1) {
                i2++;
                MarkerVector markerVector2 = markers.get(i2);
                markerVectorRealignMarkerMid = markerVector.isAmbiguous ? realignMarkerMid(markerVectorRealignMarkerMid, markerVector, markerVector2) : markerVector;
                renderMarker(marker2, markerVectorRealignMarkerMid);
                markerVector = markerVector2;
            }
        }
        if (marker3 != null) {
            renderMarker(marker3, markers.get(size - 1));
        }
    }

    private void renderMask(SVG.Mask mask, SVG.SvgElement svgElement, SVG.Box box) {
        float fFloatValueX;
        float fFloatValueY;
        debug("Mask render", new Object[0]);
        Boolean bool = mask.maskUnitsAreUser;
        boolean z2 = true;
        if (bool != null && bool.booleanValue()) {
            SVG.Length length = mask.width;
            fFloatValueX = length != null ? length.floatValueX(this) : box.width;
            SVG.Length length2 = mask.height;
            fFloatValueY = length2 != null ? length2.floatValueY(this) : box.height;
        } else {
            SVG.Length length3 = mask.width;
            float fFloatValue = length3 != null ? length3.floatValue(this, 1.0f) : 1.2f;
            SVG.Length length4 = mask.height;
            float fFloatValue2 = length4 != null ? length4.floatValue(this, 1.0f) : 1.2f;
            fFloatValueX = fFloatValue * box.width;
            fFloatValueY = fFloatValue2 * box.height;
        }
        if (fFloatValueX == 0.0f || fFloatValueY == 0.0f) {
            return;
        }
        statePush();
        RendererState rendererStateFindInheritFromAncestorState = findInheritFromAncestorState(mask);
        this.state = rendererStateFindInheritFromAncestorState;
        rendererStateFindInheritFromAncestorState.style.opacity = Float.valueOf(1.0f);
        boolean zPushLayer = pushLayer();
        this.canvas.save();
        Boolean bool2 = mask.maskContentUnitsAreUser;
        if (bool2 != null && !bool2.booleanValue()) {
            z2 = false;
        }
        if (!z2) {
            this.canvas.translate(box.minX, box.minY);
            this.canvas.scale(box.width, box.height);
        }
        renderChildren(mask, false);
        this.canvas.restore();
        if (zPushLayer) {
            popLayer(svgElement, box);
        }
        statePop();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void renderSwitchChild(SVG.Switch r8) {
        Set<String> systemLanguage;
        String language = Locale.getDefault().getLanguage();
        SVGExternalFileResolver fileResolver = SVG.getFileResolver();
        for (SVG.SvgObject svgObject : r8.getChildren()) {
            if (svgObject instanceof SVG.SvgConditional) {
                SVG.SvgConditional svgConditional = (SVG.SvgConditional) svgObject;
                if (svgConditional.getRequiredExtensions() == null && ((systemLanguage = svgConditional.getSystemLanguage()) == null || (!systemLanguage.isEmpty() && systemLanguage.contains(language)))) {
                    Set<String> requiredFeatures = svgConditional.getRequiredFeatures();
                    if (requiredFeatures != null) {
                        if (supportedFeatures == null) {
                            initialiseSupportedFeaturesMap();
                        }
                        if (requiredFeatures.isEmpty() || !supportedFeatures.containsAll(requiredFeatures)) {
                        }
                    }
                    Set<String> requiredFormats = svgConditional.getRequiredFormats();
                    if (requiredFormats != null) {
                        if (!requiredFormats.isEmpty() && fileResolver != null) {
                            Iterator<String> it = requiredFormats.iterator();
                            while (it.hasNext()) {
                                if (!fileResolver.isFormatSupported(it.next())) {
                                    break;
                                }
                            }
                        }
                    }
                    Set<String> requiredFonts = svgConditional.getRequiredFonts();
                    if (requiredFonts != null) {
                        if (!requiredFonts.isEmpty() && fileResolver != null) {
                            Iterator<String> it2 = requiredFonts.iterator();
                            while (it2.hasNext()) {
                                if (fileResolver.resolveFont(it2.next(), this.state.style.fontWeight.intValue(), String.valueOf(this.state.style.fontStyle)) == null) {
                                    break;
                                }
                            }
                        }
                    }
                    render(svgObject);
                    return;
                }
            }
        }
    }

    private void renderTextPath(SVG.TextPath textPath) {
        debug("TextPath render", new Object[0]);
        updateStyleForElement(this.state, textPath);
        if (display() && visible()) {
            SVG.SvgObject svgObjectResolveIRI = textPath.document.resolveIRI(textPath.href);
            if (svgObjectResolveIRI == null) {
                error("TextPath reference '%s' not found", textPath.href);
                return;
            }
            SVG.Path path = (SVG.Path) svgObjectResolveIRI;
            Path path2 = new PathConverter(path.f6312d).getPath();
            Matrix matrix = path.transform;
            if (matrix != null) {
                path2.transform(matrix);
            }
            PathMeasure pathMeasure = new PathMeasure(path2, false);
            SVG.Length length = textPath.startOffset;
            float fFloatValue = length != null ? length.floatValue(this, pathMeasure.getLength()) : 0.0f;
            SVG.Style.TextAnchor anchorPosition = getAnchorPosition();
            if (anchorPosition != SVG.Style.TextAnchor.Start) {
                float fCalculateTextWidth = calculateTextWidth(textPath);
                if (anchorPosition == SVG.Style.TextAnchor.Middle) {
                    fCalculateTextWidth /= 2.0f;
                }
                fFloatValue -= fCalculateTextWidth;
            }
            checkForGradientsAndPatterns((SVG.SvgElement) textPath.getTextRoot());
            boolean zPushLayer = pushLayer();
            enumerateTextSpans(textPath, new PathTextDrawer(path2, fFloatValue, 0.0f));
            if (zPushLayer) {
                popLayer(textPath);
            }
        }
    }

    private boolean requiresCompositing() {
        return this.state.style.opacity.floatValue() < 1.0f || this.state.style.mask != null;
    }

    private void resetState() {
        this.state = new RendererState();
        this.stateStack = new Stack<>();
        updateStyle(this.state, SVG.Style.getDefaultStyle());
        RendererState rendererState = this.state;
        rendererState.viewPort = null;
        rendererState.spacePreserve = false;
        this.stateStack.push(new RendererState(rendererState));
        this.matrixStack = new Stack<>();
        this.parentStack = new Stack<>();
    }

    private void setClipRect(float f2, float f3, float f4, float f5) {
        float fFloatValueX = f4 + f2;
        float fFloatValueY = f5 + f3;
        SVG.CSSClipRect cSSClipRect = this.state.style.clip;
        if (cSSClipRect != null) {
            f2 += cSSClipRect.left.floatValueX(this);
            f3 += this.state.style.clip.f6304top.floatValueY(this);
            fFloatValueX -= this.state.style.clip.right.floatValueX(this);
            fFloatValueY -= this.state.style.clip.bottom.floatValueY(this);
        }
        this.canvas.clipRect(f2, f3, fFloatValueX, fFloatValueY);
    }

    private void setPaintColour(RendererState rendererState, boolean z2, SVG.SvgPaint svgPaint) {
        int i2;
        SVG.Style style = rendererState.style;
        float fFloatValue = (z2 ? style.fillOpacity : style.strokeOpacity).floatValue();
        if (svgPaint instanceof SVG.Colour) {
            i2 = ((SVG.Colour) svgPaint).colour;
        } else if (!(svgPaint instanceof SVG.CurrentColor)) {
            return;
        } else {
            i2 = rendererState.style.color.colour;
        }
        int iColourWithOpacity = colourWithOpacity(i2, fFloatValue);
        if (z2) {
            rendererState.fillPaint.setColor(iColourWithOpacity);
        } else {
            rendererState.strokePaint.setColor(iColourWithOpacity);
        }
    }

    private void setSolidColor(boolean z2, SVG.SolidColor solidColor) {
        if (z2) {
            if (isSpecified(solidColor.baseStyle, IjkMediaMeta.AV_CH_WIDE_LEFT)) {
                RendererState rendererState = this.state;
                SVG.Style style = rendererState.style;
                SVG.SvgPaint svgPaint = solidColor.baseStyle.solidColor;
                style.fill = svgPaint;
                rendererState.hasFill = svgPaint != null;
            }
            if (isSpecified(solidColor.baseStyle, IjkMediaMeta.AV_CH_WIDE_RIGHT)) {
                this.state.style.fillOpacity = solidColor.baseStyle.solidOpacity;
            }
            if (isSpecified(solidColor.baseStyle, 6442450944L)) {
                RendererState rendererState2 = this.state;
                setPaintColour(rendererState2, z2, rendererState2.style.fill);
                return;
            }
            return;
        }
        if (isSpecified(solidColor.baseStyle, IjkMediaMeta.AV_CH_WIDE_LEFT)) {
            RendererState rendererState3 = this.state;
            SVG.Style style2 = rendererState3.style;
            SVG.SvgPaint svgPaint2 = solidColor.baseStyle.solidColor;
            style2.stroke = svgPaint2;
            rendererState3.hasStroke = svgPaint2 != null;
        }
        if (isSpecified(solidColor.baseStyle, IjkMediaMeta.AV_CH_WIDE_RIGHT)) {
            this.state.style.strokeOpacity = solidColor.baseStyle.solidOpacity;
        }
        if (isSpecified(solidColor.baseStyle, 6442450944L)) {
            RendererState rendererState4 = this.state;
            setPaintColour(rendererState4, z2, rendererState4.style.stroke);
        }
    }

    private void statePop() {
        this.canvas.restore();
        this.state = this.stateStack.pop();
    }

    private void statePush() {
        this.canvas.save();
        this.stateStack.push(this.state);
        this.state = new RendererState(this.state);
    }

    private String textXMLSpaceTransform(String str, boolean z2, boolean z3) {
        if (this.state.spacePreserve) {
            return str.replaceAll("[\\n\\t]", " ");
        }
        String strReplaceAll = str.replaceAll("\\n", "").replaceAll("\\t", " ");
        if (z2) {
            strReplaceAll = strReplaceAll.replaceAll("^\\s+", "");
        }
        if (z3) {
            strReplaceAll = strReplaceAll.replaceAll("\\s+$", "");
        }
        return strReplaceAll.replaceAll("\\s{2,}", " ");
    }

    private void updateParentBoundingBox(SVG.SvgElement svgElement) {
        if (svgElement.parent == null || svgElement.boundingBox == null) {
            return;
        }
        Matrix matrix = new Matrix();
        if (this.matrixStack.peek().invert(matrix)) {
            SVG.Box box = svgElement.boundingBox;
            SVG.Box box2 = svgElement.boundingBox;
            SVG.Box box3 = svgElement.boundingBox;
            float[] fArr = {box.minX, box.minY, box.maxX(), box2.minY, box2.maxX(), svgElement.boundingBox.maxY(), box3.minX, box3.maxY()};
            matrix.preConcat(this.canvas.getMatrix());
            matrix.mapPoints(fArr);
            float f2 = fArr[0];
            float f3 = fArr[1];
            RectF rectF = new RectF(f2, f3, f2, f3);
            for (int i2 = 2; i2 <= 6; i2 += 2) {
                float f4 = fArr[i2];
                if (f4 < rectF.left) {
                    rectF.left = f4;
                }
                if (f4 > rectF.right) {
                    rectF.right = f4;
                }
                float f5 = fArr[i2 + 1];
                if (f5 < rectF.top) {
                    rectF.top = f5;
                }
                if (f5 > rectF.bottom) {
                    rectF.bottom = f5;
                }
            }
            SVG.SvgElement svgElement2 = (SVG.SvgElement) this.parentStack.peek();
            SVG.Box box4 = svgElement2.boundingBox;
            if (box4 == null) {
                svgElement2.boundingBox = SVG.Box.fromLimits(rectF.left, rectF.top, rectF.right, rectF.bottom);
            } else {
                box4.union(SVG.Box.fromLimits(rectF.left, rectF.top, rectF.right, rectF.bottom));
            }
        }
    }

    private void updateStyle(RendererState rendererState, SVG.Style style) {
        if (isSpecified(style, 4096L)) {
            rendererState.style.color = style.color;
        }
        if (isSpecified(style, 2048L)) {
            rendererState.style.opacity = style.opacity;
        }
        if (isSpecified(style, 1L)) {
            rendererState.style.fill = style.fill;
            SVG.SvgPaint svgPaint = style.fill;
            rendererState.hasFill = (svgPaint == null || svgPaint == SVG.Colour.TRANSPARENT) ? false : true;
        }
        if (isSpecified(style, 4L)) {
            rendererState.style.fillOpacity = style.fillOpacity;
        }
        if (isSpecified(style, 6149L)) {
            setPaintColour(rendererState, true, rendererState.style.fill);
        }
        if (isSpecified(style, 2L)) {
            rendererState.style.fillRule = style.fillRule;
        }
        if (isSpecified(style, 8L)) {
            rendererState.style.stroke = style.stroke;
            SVG.SvgPaint svgPaint2 = style.stroke;
            rendererState.hasStroke = (svgPaint2 == null || svgPaint2 == SVG.Colour.TRANSPARENT) ? false : true;
        }
        if (isSpecified(style, 16L)) {
            rendererState.style.strokeOpacity = style.strokeOpacity;
        }
        if (isSpecified(style, 6168L)) {
            setPaintColour(rendererState, false, rendererState.style.stroke);
        }
        if (isSpecified(style, IjkMediaMeta.AV_CH_LOW_FREQUENCY_2)) {
            rendererState.style.vectorEffect = style.vectorEffect;
        }
        if (isSpecified(style, 32L)) {
            SVG.Style style2 = rendererState.style;
            SVG.Length length = style.strokeWidth;
            style2.strokeWidth = length;
            rendererState.strokePaint.setStrokeWidth(length.floatValue(this));
        }
        if (isSpecified(style, 64L)) {
            rendererState.style.strokeLineCap = style.strokeLineCap;
            int i2 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVG$Style$LineCap[style.strokeLineCap.ordinal()];
            if (i2 == 1) {
                rendererState.strokePaint.setStrokeCap(Paint.Cap.BUTT);
            } else if (i2 == 2) {
                rendererState.strokePaint.setStrokeCap(Paint.Cap.ROUND);
            } else if (i2 == 3) {
                rendererState.strokePaint.setStrokeCap(Paint.Cap.SQUARE);
            }
        }
        if (isSpecified(style, 128L)) {
            rendererState.style.strokeLineJoin = style.strokeLineJoin;
            int i3 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVG$Style$LineJoin[style.strokeLineJoin.ordinal()];
            if (i3 == 1) {
                rendererState.strokePaint.setStrokeJoin(Paint.Join.MITER);
            } else if (i3 == 2) {
                rendererState.strokePaint.setStrokeJoin(Paint.Join.ROUND);
            } else if (i3 == 3) {
                rendererState.strokePaint.setStrokeJoin(Paint.Join.BEVEL);
            }
        }
        if (isSpecified(style, 256L)) {
            rendererState.style.strokeMiterLimit = style.strokeMiterLimit;
            rendererState.strokePaint.setStrokeMiter(style.strokeMiterLimit.floatValue());
        }
        if (isSpecified(style, 512L)) {
            rendererState.style.strokeDashArray = style.strokeDashArray;
        }
        if (isSpecified(style, 1024L)) {
            rendererState.style.strokeDashOffset = style.strokeDashOffset;
        }
        Typeface typefaceCheckGenericFont = null;
        if (isSpecified(style, 1536L)) {
            SVG.Length[] lengthArr = rendererState.style.strokeDashArray;
            if (lengthArr == null) {
                rendererState.strokePaint.setPathEffect(null);
            } else {
                int length2 = lengthArr.length;
                int i4 = length2 % 2 == 0 ? length2 : length2 * 2;
                float[] fArr = new float[i4];
                float f2 = 0.0f;
                for (int i5 = 0; i5 < i4; i5++) {
                    float fFloatValue = rendererState.style.strokeDashArray[i5 % length2].floatValue(this);
                    fArr[i5] = fFloatValue;
                    f2 += fFloatValue;
                }
                if (f2 == 0.0f) {
                    rendererState.strokePaint.setPathEffect(null);
                } else {
                    float fFloatValue2 = rendererState.style.strokeDashOffset.floatValue(this);
                    if (fFloatValue2 < 0.0f) {
                        fFloatValue2 = (fFloatValue2 % f2) + f2;
                    }
                    rendererState.strokePaint.setPathEffect(new DashPathEffect(fArr, fFloatValue2));
                }
            }
        }
        if (isSpecified(style, 16384L)) {
            float currentFontSize = getCurrentFontSize();
            rendererState.style.fontSize = style.fontSize;
            rendererState.fillPaint.setTextSize(style.fontSize.floatValue(this, currentFontSize));
            rendererState.strokePaint.setTextSize(style.fontSize.floatValue(this, currentFontSize));
        }
        if (isSpecified(style, 8192L)) {
            rendererState.style.fontFamily = style.fontFamily;
        }
        if (isSpecified(style, 32768L)) {
            if (style.fontWeight.intValue() == -1 && rendererState.style.fontWeight.intValue() > 100) {
                SVG.Style style3 = rendererState.style;
                style3.fontWeight = Integer.valueOf(style3.fontWeight.intValue() - 100);
            } else if (style.fontWeight.intValue() != 1 || rendererState.style.fontWeight.intValue() >= 900) {
                rendererState.style.fontWeight = style.fontWeight;
            } else {
                SVG.Style style4 = rendererState.style;
                style4.fontWeight = Integer.valueOf(style4.fontWeight.intValue() + 100);
            }
        }
        if (isSpecified(style, 65536L)) {
            rendererState.style.fontStyle = style.fontStyle;
        }
        if (isSpecified(style, 106496L)) {
            if (rendererState.style.fontFamily != null && this.document != null) {
                SVGExternalFileResolver fileResolver = SVG.getFileResolver();
                for (String str : rendererState.style.fontFamily) {
                    SVG.Style style5 = rendererState.style;
                    Typeface typefaceCheckGenericFont2 = checkGenericFont(str, style5.fontWeight, style5.fontStyle);
                    typefaceCheckGenericFont = (typefaceCheckGenericFont2 != null || fileResolver == null) ? typefaceCheckGenericFont2 : fileResolver.resolveFont(str, rendererState.style.fontWeight.intValue(), String.valueOf(rendererState.style.fontStyle));
                    if (typefaceCheckGenericFont != null) {
                        break;
                    }
                }
            }
            if (typefaceCheckGenericFont == null) {
                SVG.Style style6 = rendererState.style;
                typefaceCheckGenericFont = checkGenericFont("serif", style6.fontWeight, style6.fontStyle);
            }
            rendererState.fillPaint.setTypeface(typefaceCheckGenericFont);
            rendererState.strokePaint.setTypeface(typefaceCheckGenericFont);
        }
        if (isSpecified(style, 131072L)) {
            rendererState.style.textDecoration = style.textDecoration;
            Paint paint = rendererState.fillPaint;
            SVG.Style.TextDecoration textDecoration = style.textDecoration;
            SVG.Style.TextDecoration textDecoration2 = SVG.Style.TextDecoration.LineThrough;
            paint.setStrikeThruText(textDecoration == textDecoration2);
            Paint paint2 = rendererState.fillPaint;
            SVG.Style.TextDecoration textDecoration3 = style.textDecoration;
            SVG.Style.TextDecoration textDecoration4 = SVG.Style.TextDecoration.Underline;
            paint2.setUnderlineText(textDecoration3 == textDecoration4);
            rendererState.strokePaint.setStrikeThruText(style.textDecoration == textDecoration2);
            rendererState.strokePaint.setUnderlineText(style.textDecoration == textDecoration4);
        }
        if (isSpecified(style, 68719476736L)) {
            rendererState.style.direction = style.direction;
        }
        if (isSpecified(style, PlaybackStateCompat.ACTION_SET_REPEAT_MODE)) {
            rendererState.style.textAnchor = style.textAnchor;
        }
        if (isSpecified(style, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED)) {
            rendererState.style.overflow = style.overflow;
        }
        if (isSpecified(style, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE)) {
            rendererState.style.markerStart = style.markerStart;
        }
        if (isSpecified(style, PlaybackStateCompat.ACTION_SET_PLAYBACK_SPEED)) {
            rendererState.style.markerMid = style.markerMid;
        }
        if (isSpecified(style, 8388608L)) {
            rendererState.style.markerEnd = style.markerEnd;
        }
        if (isSpecified(style, 16777216L)) {
            rendererState.style.display = style.display;
        }
        if (isSpecified(style, 33554432L)) {
            rendererState.style.visibility = style.visibility;
        }
        if (isSpecified(style, 1048576L)) {
            rendererState.style.clip = style.clip;
        }
        if (isSpecified(style, 268435456L)) {
            rendererState.style.clipPath = style.clipPath;
        }
        if (isSpecified(style, IjkMediaMeta.AV_CH_STEREO_LEFT)) {
            rendererState.style.clipRule = style.clipRule;
        }
        if (isSpecified(style, 1073741824L)) {
            rendererState.style.mask = style.mask;
        }
        if (isSpecified(style, 67108864L)) {
            rendererState.style.stopColor = style.stopColor;
        }
        if (isSpecified(style, 134217728L)) {
            rendererState.style.stopOpacity = style.stopOpacity;
        }
        if (isSpecified(style, IjkMediaMeta.AV_CH_SURROUND_DIRECT_LEFT)) {
            rendererState.style.viewportFill = style.viewportFill;
        }
        if (isSpecified(style, IjkMediaMeta.AV_CH_SURROUND_DIRECT_RIGHT)) {
            rendererState.style.viewportFillOpacity = style.viewportFillOpacity;
        }
        if (isSpecified(style, 137438953472L)) {
            rendererState.style.imageRendering = style.imageRendering;
        }
    }

    private void updateStyleForElement(RendererState rendererState, SVG.SvgElementBase svgElementBase) {
        rendererState.style.resetNonInheritingProperties(svgElementBase.parent == null);
        SVG.Style style = svgElementBase.baseStyle;
        if (style != null) {
            updateStyle(rendererState, style);
        }
        if (this.document.hasCSSRules()) {
            for (CSSParser.Rule rule : this.document.getCSSRules()) {
                if (CSSParser.ruleMatch(this.ruleMatchContext, rule.selector, svgElementBase)) {
                    updateStyle(rendererState, rule.style);
                }
            }
        }
        SVG.Style style2 = svgElementBase.style;
        if (style2 != null) {
            updateStyle(rendererState, style2);
        }
    }

    private void viewportFill() {
        int iColourWithOpacity;
        SVG.Style style = this.state.style;
        SVG.SvgPaint svgPaint = style.viewportFill;
        if (svgPaint instanceof SVG.Colour) {
            iColourWithOpacity = ((SVG.Colour) svgPaint).colour;
        } else if (!(svgPaint instanceof SVG.CurrentColor)) {
            return;
        } else {
            iColourWithOpacity = style.color.colour;
        }
        Float f2 = style.viewportFillOpacity;
        if (f2 != null) {
            iColourWithOpacity = colourWithOpacity(iColourWithOpacity, f2.floatValue());
        }
        this.canvas.drawColor(iColourWithOpacity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean visible() {
        Boolean bool = this.state.style.visibility;
        if (bool != null) {
            return bool.booleanValue();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void warn(String str, Object... objArr) {
        Log.w(TAG, String.format(str, objArr));
    }

    public float getCurrentFontSize() {
        return this.state.fillPaint.getTextSize();
    }

    public float getCurrentFontXHeight() {
        return this.state.fillPaint.getTextSize() / 2.0f;
    }

    public SVG.Box getCurrentViewPortInUserUnits() {
        RendererState rendererState = this.state;
        SVG.Box box = rendererState.viewBox;
        return box != null ? box : rendererState.viewPort;
    }

    public float getDPI() {
        return this.dpi;
    }

    public void renderDocument(SVG svg, RenderOptions renderOptions) {
        SVG.Box box;
        PreserveAspectRatio preserveAspectRatio;
        if (renderOptions == null) {
            throw new NullPointerException("renderOptions shouldn't be null");
        }
        this.document = svg;
        SVG.Svg rootElement = svg.getRootElement();
        if (rootElement == null) {
            warn("Nothing to render. Document is empty.", new Object[0]);
            return;
        }
        if (renderOptions.hasView()) {
            SVG.SvgElementBase elementById = this.document.getElementById(renderOptions.viewId);
            if (elementById == null || !(elementById instanceof SVG.View)) {
                Log.w(TAG, String.format("View element with id \"%s\" not found.", renderOptions.viewId));
                return;
            }
            SVG.View view = (SVG.View) elementById;
            box = view.viewBox;
            if (box == null) {
                Log.w(TAG, String.format("View element with id \"%s\" is missing a viewBox attribute.", renderOptions.viewId));
                return;
            }
            preserveAspectRatio = view.preserveAspectRatio;
        } else {
            box = renderOptions.hasViewBox() ? renderOptions.viewBox : rootElement.viewBox;
            preserveAspectRatio = renderOptions.hasPreserveAspectRatio() ? renderOptions.preserveAspectRatio : rootElement.preserveAspectRatio;
        }
        if (renderOptions.hasCss()) {
            svg.addCSSRules(renderOptions.css);
        }
        if (renderOptions.hasTarget()) {
            CSSParser.RuleMatchContext ruleMatchContext = new CSSParser.RuleMatchContext();
            this.ruleMatchContext = ruleMatchContext;
            ruleMatchContext.targetElement = svg.getElementById(renderOptions.targetId);
        }
        resetState();
        checkXMLSpaceAttribute(rootElement);
        statePush();
        SVG.Box box2 = new SVG.Box(renderOptions.viewPort);
        SVG.Length length = rootElement.width;
        if (length != null) {
            box2.width = length.floatValue(this, box2.width);
        }
        SVG.Length length2 = rootElement.height;
        if (length2 != null) {
            box2.height = length2.floatValue(this, box2.height);
        }
        render(rootElement, box2, box, preserveAspectRatio);
        statePop();
        if (renderOptions.hasCss()) {
            svg.clearRenderCSSRules();
        }
    }

    public class TextWidthCalculator extends TextProcessor {

        /* renamed from: x, reason: collision with root package name */
        float f6334x;

        private TextWidthCalculator() {
            super(SVGAndroidRenderer.this, null);
            this.f6334x = 0.0f;
        }

        @Override // com.caverock.androidsvg.SVGAndroidRenderer.TextProcessor
        public void processText(String str) {
            this.f6334x += SVGAndroidRenderer.this.state.fillPaint.measureText(str);
        }

        public /* synthetic */ TextWidthCalculator(SVGAndroidRenderer sVGAndroidRenderer, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    private void checkForClipPath(SVG.SvgElement svgElement, SVG.Box box) {
        Path pathCalculateClipPath;
        if (this.state.style.clipPath == null || (pathCalculateClipPath = calculateClipPath(svgElement, box)) == null) {
            return;
        }
        this.canvas.clipPath(pathCalculateClipPath);
    }

    private void popLayer(SVG.SvgElement svgElement, SVG.Box box) {
        if (this.state.style.mask != null) {
            Paint paint = new Paint();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            this.canvas.saveLayer(null, paint, 31);
            Paint paint2 = new Paint();
            paint2.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2127f, 0.7151f, 0.0722f, 0.0f, 0.0f})));
            this.canvas.saveLayer(null, paint2, 31);
            SVG.Mask mask = (SVG.Mask) this.document.resolveIRI(this.state.style.mask);
            renderMask(mask, svgElement, box);
            this.canvas.restore();
            Paint paint3 = new Paint();
            paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            this.canvas.saveLayer(null, paint3, 31);
            renderMask(mask, svgElement, box);
            this.canvas.restore();
            this.canvas.restore();
        }
        statePop();
    }

    private RendererState findInheritFromAncestorState(SVG.SvgObject svgObject, RendererState rendererState) {
        ArrayList arrayList = new ArrayList();
        while (true) {
            if (svgObject instanceof SVG.SvgElementBase) {
                arrayList.add(0, (SVG.SvgElementBase) svgObject);
            }
            Object obj = svgObject.parent;
            if (obj == null) {
                break;
            }
            svgObject = (SVG.SvgObject) obj;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            updateStyleForElement(rendererState, (SVG.SvgElementBase) it.next());
        }
        RendererState rendererState2 = this.state;
        rendererState.viewBox = rendererState2.viewBox;
        rendererState.viewPort = rendererState2.viewPort;
        return rendererState;
    }

    private List<MarkerVector> calculateMarkerPositions(SVG.PolyLine polyLine) {
        int length = polyLine.points.length;
        int i2 = 2;
        if (length < 2) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        float[] fArr = polyLine.points;
        MarkerVector markerVector = new MarkerVector(fArr[0], fArr[1], 0.0f, 0.0f);
        float f2 = 0.0f;
        float f3 = 0.0f;
        while (i2 < length) {
            float[] fArr2 = polyLine.points;
            float f4 = fArr2[i2];
            float f5 = fArr2[i2 + 1];
            markerVector.add(f4, f5);
            arrayList.add(markerVector);
            i2 += 2;
            markerVector = new MarkerVector(f4, f5, f4 - markerVector.f6326x, f5 - markerVector.f6327y);
            f3 = f5;
            f2 = f4;
        }
        if (polyLine instanceof SVG.Polygon) {
            float[] fArr3 = polyLine.points;
            float f6 = fArr3[0];
            if (f2 != f6) {
                float f7 = fArr3[1];
                if (f3 != f7) {
                    markerVector.add(f6, f7);
                    arrayList.add(markerVector);
                    MarkerVector markerVector2 = new MarkerVector(f6, f7, f6 - markerVector.f6326x, f7 - markerVector.f6327y);
                    markerVector2.add((MarkerVector) arrayList.get(0));
                    arrayList.add(markerVector2);
                    arrayList.set(0, markerVector2);
                }
            }
        } else {
            arrayList.add(markerVector);
        }
        return arrayList;
    }

    public class MarkerVector {
        float dx;
        float dy;
        boolean isAmbiguous = false;

        /* renamed from: x, reason: collision with root package name */
        float f6326x;

        /* renamed from: y, reason: collision with root package name */
        float f6327y;

        public MarkerVector(float f2, float f3, float f4, float f5) {
            this.dx = 0.0f;
            this.dy = 0.0f;
            this.f6326x = f2;
            this.f6327y = f3;
            double dSqrt = Math.sqrt((f4 * f4) + (f5 * f5));
            if (dSqrt != 0.0d) {
                this.dx = (float) (f4 / dSqrt);
                this.dy = (float) (f5 / dSqrt);
            }
        }

        public void add(float f2, float f3) {
            float f4 = f2 - this.f6326x;
            float f5 = f3 - this.f6327y;
            double dSqrt = Math.sqrt((f4 * f4) + (f5 * f5));
            if (dSqrt != 0.0d) {
                f4 = (float) (f4 / dSqrt);
                f5 = (float) (f5 / dSqrt);
            }
            float f6 = this.dx;
            if (f4 != (-f6) || f5 != (-this.dy)) {
                this.dx = f6 + f4;
                this.dy += f5;
            } else {
                this.isAmbiguous = true;
                this.dx = -f5;
                this.dy = f4;
            }
        }

        public String toString() {
            return "(" + this.f6326x + "," + this.f6327y + " " + this.dx + "," + this.dy + ")";
        }

        public void add(MarkerVector markerVector) {
            float f2 = markerVector.dx;
            float f3 = this.dx;
            if (f2 == (-f3)) {
                float f4 = markerVector.dy;
                if (f4 == (-this.dy)) {
                    this.isAmbiguous = true;
                    this.dx = -f4;
                    this.dy = markerVector.dx;
                    return;
                }
            }
            this.dx = f3 + f2;
            this.dy += markerVector.dy;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00dd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.graphics.Path makePathAndBoundingBox(com.caverock.androidsvg.SVG.Rect r24) {
        /*
            Method dump skipped, instructions count: 241
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGAndroidRenderer.makePathAndBoundingBox(com.caverock.androidsvg.SVG$Rect):android.graphics.Path");
    }

    public class RendererState {
        Paint fillPaint;
        boolean hasFill;
        boolean hasStroke;
        boolean spacePreserve;
        Paint strokePaint;
        SVG.Style style;
        SVG.Box viewBox;
        SVG.Box viewPort;

        public RendererState() {
            Paint paint = new Paint();
            this.fillPaint = paint;
            paint.setFlags(193);
            this.fillPaint.setHinting(0);
            this.fillPaint.setStyle(Paint.Style.FILL);
            this.fillPaint.setTypeface(Typeface.DEFAULT);
            Paint paint2 = new Paint();
            this.strokePaint = paint2;
            paint2.setFlags(193);
            this.strokePaint.setHinting(0);
            this.strokePaint.setStyle(Paint.Style.STROKE);
            this.strokePaint.setTypeface(Typeface.DEFAULT);
            this.style = SVG.Style.getDefaultStyle();
        }

        public RendererState(RendererState rendererState) {
            this.hasFill = rendererState.hasFill;
            this.hasStroke = rendererState.hasStroke;
            this.fillPaint = new Paint(rendererState.fillPaint);
            this.strokePaint = new Paint(rendererState.strokePaint);
            SVG.Box box = rendererState.viewPort;
            if (box != null) {
                this.viewPort = new SVG.Box(box);
            }
            SVG.Box box2 = rendererState.viewBox;
            if (box2 != null) {
                this.viewBox = new SVG.Box(box2);
            }
            this.spacePreserve = rendererState.spacePreserve;
            try {
                this.style = (SVG.Style) rendererState.style.clone();
            } catch (CloneNotSupportedException e2) {
                Log.e(SVGAndroidRenderer.TAG, "Unexpected clone error", e2);
                this.style = SVG.Style.getDefaultStyle();
            }
        }
    }

    private void addObjectToClip(SVG.Path path, Path path2, Matrix matrix) {
        updateStyleForElement(this.state, path);
        if (display() && visible()) {
            Matrix matrix2 = path.transform;
            if (matrix2 != null) {
                matrix.preConcat(matrix2);
            }
            Path path3 = new PathConverter(path.f6312d).getPath();
            if (path.boundingBox == null) {
                path.boundingBox = calculatePathBounds(path3);
            }
            checkForClipPath(path);
            path2.setFillType(getClipRuleFromState());
            path2.addPath(path3, matrix);
        }
    }

    private void fillInChainedGradientFields(SVG.SvgLinearGradient svgLinearGradient, SVG.SvgLinearGradient svgLinearGradient2) {
        if (svgLinearGradient.f6319x1 == null) {
            svgLinearGradient.f6319x1 = svgLinearGradient2.f6319x1;
        }
        if (svgLinearGradient.f6320y1 == null) {
            svgLinearGradient.f6320y1 = svgLinearGradient2.f6320y1;
        }
        if (svgLinearGradient.x2 == null) {
            svgLinearGradient.x2 = svgLinearGradient2.x2;
        }
        if (svgLinearGradient.y2 == null) {
            svgLinearGradient.y2 = svgLinearGradient2.y2;
        }
    }

    private void addObjectToClip(SVG.GraphicsElement graphicsElement, Path path, Matrix matrix) {
        Path pathMakePathAndBoundingBox;
        updateStyleForElement(this.state, graphicsElement);
        if (display() && visible()) {
            Matrix matrix2 = graphicsElement.transform;
            if (matrix2 != null) {
                matrix.preConcat(matrix2);
            }
            if (graphicsElement instanceof SVG.Rect) {
                pathMakePathAndBoundingBox = makePathAndBoundingBox((SVG.Rect) graphicsElement);
            } else if (graphicsElement instanceof SVG.Circle) {
                pathMakePathAndBoundingBox = makePathAndBoundingBox((SVG.Circle) graphicsElement);
            } else if (graphicsElement instanceof SVG.Ellipse) {
                pathMakePathAndBoundingBox = makePathAndBoundingBox((SVG.Ellipse) graphicsElement);
            } else if (!(graphicsElement instanceof SVG.PolyLine)) {
                return;
            } else {
                pathMakePathAndBoundingBox = makePathAndBoundingBox((SVG.PolyLine) graphicsElement);
            }
            checkForClipPath(graphicsElement);
            path.setFillType(getClipRuleFromState());
            path.addPath(pathMakePathAndBoundingBox, matrix);
        }
    }

    private void fillInChainedGradientFields(SVG.SvgRadialGradient svgRadialGradient, SVG.SvgRadialGradient svgRadialGradient2) {
        if (svgRadialGradient.cx == null) {
            svgRadialGradient.cx = svgRadialGradient2.cx;
        }
        if (svgRadialGradient.cy == null) {
            svgRadialGradient.cy = svgRadialGradient2.cy;
        }
        if (svgRadialGradient.f6321r == null) {
            svgRadialGradient.f6321r = svgRadialGradient2.f6321r;
        }
        if (svgRadialGradient.fx == null) {
            svgRadialGradient.fx = svgRadialGradient2.fx;
        }
        if (svgRadialGradient.fy == null) {
            svgRadialGradient.fy = svgRadialGradient2.fy;
        }
    }

    private void render(SVG.Svg svg) {
        render(svg, makeViewPort(svg.f6317x, svg.f6318y, svg.width, svg.height), svg.viewBox, svg.preserveAspectRatio);
    }

    private void render(SVG.Svg svg, SVG.Box box) {
        render(svg, box, svg.viewBox, svg.preserveAspectRatio);
    }

    private void render(SVG.Svg svg, SVG.Box box, SVG.Box box2, PreserveAspectRatio preserveAspectRatio) {
        debug("Svg render", new Object[0]);
        if (box.width == 0.0f || box.height == 0.0f) {
            return;
        }
        if (preserveAspectRatio == null && (preserveAspectRatio = svg.preserveAspectRatio) == null) {
            preserveAspectRatio = PreserveAspectRatio.LETTERBOX;
        }
        updateStyleForElement(this.state, svg);
        if (display()) {
            RendererState rendererState = this.state;
            rendererState.viewPort = box;
            if (!rendererState.style.overflow.booleanValue()) {
                SVG.Box box3 = this.state.viewPort;
                setClipRect(box3.minX, box3.minY, box3.width, box3.height);
            }
            checkForClipPath(svg, this.state.viewPort);
            if (box2 != null) {
                this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, box2, preserveAspectRatio));
                this.state.viewBox = svg.viewBox;
            } else {
                Canvas canvas = this.canvas;
                SVG.Box box4 = this.state.viewPort;
                canvas.translate(box4.minX, box4.minY);
            }
            boolean zPushLayer = pushLayer();
            viewportFill();
            renderChildren(svg, true);
            if (zPushLayer) {
                popLayer(svg);
            }
            updateParentBoundingBox(svg);
        }
    }

    private Path makePathAndBoundingBox(SVG.Circle circle) {
        SVG.Length length = circle.cx;
        float fFloatValueX = length != null ? length.floatValueX(this) : 0.0f;
        SVG.Length length2 = circle.cy;
        float fFloatValueY = length2 != null ? length2.floatValueY(this) : 0.0f;
        float fFloatValue = circle.f6305r.floatValue(this);
        float f2 = fFloatValueX - fFloatValue;
        float f3 = fFloatValueY - fFloatValue;
        float f4 = fFloatValueX + fFloatValue;
        float f5 = fFloatValueY + fFloatValue;
        if (circle.boundingBox == null) {
            float f6 = 2.0f * fFloatValue;
            circle.boundingBox = new SVG.Box(f2, f3, f6, f6);
        }
        float f7 = BEZIER_ARC_FACTOR * fFloatValue;
        Path path = new Path();
        path.moveTo(fFloatValueX, f3);
        float f8 = fFloatValueX + f7;
        float f9 = fFloatValueY - f7;
        path.cubicTo(f8, f3, f4, f9, f4, fFloatValueY);
        float f10 = fFloatValueY + f7;
        path.cubicTo(f4, f10, f8, f5, fFloatValueX, f5);
        float f11 = fFloatValueX - f7;
        path.cubicTo(f11, f5, f2, f10, f2, fFloatValueY);
        path.cubicTo(f2, f9, f11, f3, fFloatValueX, f3);
        path.close();
        return path;
    }

    private void addObjectToClip(SVG.Use use, Path path, Matrix matrix) {
        updateStyleForElement(this.state, use);
        if (display() && visible()) {
            Matrix matrix2 = use.transform;
            if (matrix2 != null) {
                matrix.preConcat(matrix2);
            }
            SVG.SvgObject svgObjectResolveIRI = use.document.resolveIRI(use.href);
            if (svgObjectResolveIRI == null) {
                error("Use reference '%s' not found", use.href);
            } else {
                checkForClipPath(use);
                addObjectToClip(svgObjectResolveIRI, false, path, matrix);
            }
        }
    }

    private void addObjectToClip(SVG.Text text, Path path, Matrix matrix) {
        updateStyleForElement(this.state, text);
        if (display()) {
            Matrix matrix2 = text.transform;
            if (matrix2 != null) {
                matrix.preConcat(matrix2);
            }
            List<SVG.Length> list = text.f6322x;
            float fFloatValueY = 0.0f;
            float fFloatValueX = (list == null || list.size() == 0) ? 0.0f : text.f6322x.get(0).floatValueX(this);
            List<SVG.Length> list2 = text.f6323y;
            float fFloatValueY2 = (list2 == null || list2.size() == 0) ? 0.0f : text.f6323y.get(0).floatValueY(this);
            List<SVG.Length> list3 = text.dx;
            float fFloatValueX2 = (list3 == null || list3.size() == 0) ? 0.0f : text.dx.get(0).floatValueX(this);
            List<SVG.Length> list4 = text.dy;
            if (list4 != null && list4.size() != 0) {
                fFloatValueY = text.dy.get(0).floatValueY(this);
            }
            if (this.state.style.textAnchor != SVG.Style.TextAnchor.Start) {
                float fCalculateTextWidth = calculateTextWidth(text);
                if (this.state.style.textAnchor == SVG.Style.TextAnchor.Middle) {
                    fCalculateTextWidth /= 2.0f;
                }
                fFloatValueX -= fCalculateTextWidth;
            }
            if (text.boundingBox == null) {
                TextBoundsCalculator textBoundsCalculator = new TextBoundsCalculator(fFloatValueX, fFloatValueY2);
                enumerateTextSpans(text, textBoundsCalculator);
                RectF rectF = textBoundsCalculator.bbox;
                text.boundingBox = new SVG.Box(rectF.left, rectF.top, rectF.width(), textBoundsCalculator.bbox.height());
            }
            checkForClipPath(text);
            Path path2 = new Path();
            enumerateTextSpans(text, new PlainTextToPath(fFloatValueX + fFloatValueX2, fFloatValueY2 + fFloatValueY, path2));
            path.setFillType(getClipRuleFromState());
            path.addPath(path2, matrix);
        }
    }

    private void render(SVG.Group group) {
        debug("Group render", new Object[0]);
        updateStyleForElement(this.state, group);
        if (display()) {
            Matrix matrix = group.transform;
            if (matrix != null) {
                this.canvas.concat(matrix);
            }
            checkForClipPath(group);
            boolean zPushLayer = pushLayer();
            renderChildren(group, true);
            if (zPushLayer) {
                popLayer(group);
            }
            updateParentBoundingBox(group);
        }
    }

    private Path makePathAndBoundingBox(SVG.Ellipse ellipse) {
        SVG.Length length = ellipse.cx;
        float fFloatValueX = length != null ? length.floatValueX(this) : 0.0f;
        SVG.Length length2 = ellipse.cy;
        float fFloatValueY = length2 != null ? length2.floatValueY(this) : 0.0f;
        float fFloatValueX2 = ellipse.rx.floatValueX(this);
        float fFloatValueY2 = ellipse.ry.floatValueY(this);
        float f2 = fFloatValueX - fFloatValueX2;
        float f3 = fFloatValueY - fFloatValueY2;
        float f4 = fFloatValueX + fFloatValueX2;
        float f5 = fFloatValueY + fFloatValueY2;
        if (ellipse.boundingBox == null) {
            ellipse.boundingBox = new SVG.Box(f2, f3, fFloatValueX2 * 2.0f, 2.0f * fFloatValueY2);
        }
        float f6 = fFloatValueX2 * BEZIER_ARC_FACTOR;
        float f7 = BEZIER_ARC_FACTOR * fFloatValueY2;
        Path path = new Path();
        path.moveTo(fFloatValueX, f3);
        float f8 = fFloatValueX + f6;
        float f9 = fFloatValueY - f7;
        path.cubicTo(f8, f3, f4, f9, f4, fFloatValueY);
        float f10 = f7 + fFloatValueY;
        path.cubicTo(f4, f10, f8, f5, fFloatValueX, f5);
        float f11 = fFloatValueX - f6;
        path.cubicTo(f11, f5, f2, f10, f2, fFloatValueY);
        path.cubicTo(f2, f9, f11, f3, fFloatValueX, f3);
        path.close();
        return path;
    }

    private void render(SVG.Switch r3) {
        debug("Switch render", new Object[0]);
        updateStyleForElement(this.state, r3);
        if (display()) {
            Matrix matrix = r3.transform;
            if (matrix != null) {
                this.canvas.concat(matrix);
            }
            checkForClipPath(r3);
            boolean zPushLayer = pushLayer();
            renderSwitchChild(r3);
            if (zPushLayer) {
                popLayer(r3);
            }
            updateParentBoundingBox(r3);
        }
    }

    private Path makePathAndBoundingBox(SVG.PolyLine polyLine) {
        Path path = new Path();
        float[] fArr = polyLine.points;
        path.moveTo(fArr[0], fArr[1]);
        int i2 = 2;
        while (true) {
            float[] fArr2 = polyLine.points;
            if (i2 >= fArr2.length) {
                break;
            }
            path.lineTo(fArr2[i2], fArr2[i2 + 1]);
            i2 += 2;
        }
        if (polyLine instanceof SVG.Polygon) {
            path.close();
        }
        if (polyLine.boundingBox == null) {
            polyLine.boundingBox = calculatePathBounds(path);
        }
        return path;
    }

    private void render(SVG.Use use) {
        debug("Use render", new Object[0]);
        SVG.Length length = use.width;
        if (length == null || !length.isZero()) {
            SVG.Length length2 = use.height;
            if (length2 == null || !length2.isZero()) {
                updateStyleForElement(this.state, use);
                if (display()) {
                    SVG.SvgObject svgObjectResolveIRI = use.document.resolveIRI(use.href);
                    if (svgObjectResolveIRI == null) {
                        error("Use reference '%s' not found", use.href);
                        return;
                    }
                    Matrix matrix = use.transform;
                    if (matrix != null) {
                        this.canvas.concat(matrix);
                    }
                    SVG.Length length3 = use.f6324x;
                    float fFloatValueX = length3 != null ? length3.floatValueX(this) : 0.0f;
                    SVG.Length length4 = use.f6325y;
                    this.canvas.translate(fFloatValueX, length4 != null ? length4.floatValueY(this) : 0.0f);
                    checkForClipPath(use);
                    boolean zPushLayer = pushLayer();
                    parentPush(use);
                    if (svgObjectResolveIRI instanceof SVG.Svg) {
                        SVG.Box boxMakeViewPort = makeViewPort(null, null, use.width, use.height);
                        statePush();
                        render((SVG.Svg) svgObjectResolveIRI, boxMakeViewPort);
                        statePop();
                    } else if (svgObjectResolveIRI instanceof SVG.Symbol) {
                        SVG.Length length5 = use.width;
                        if (length5 == null) {
                            length5 = new SVG.Length(100.0f, SVG.Unit.percent);
                        }
                        SVG.Length length6 = use.height;
                        if (length6 == null) {
                            length6 = new SVG.Length(100.0f, SVG.Unit.percent);
                        }
                        SVG.Box boxMakeViewPort2 = makeViewPort(null, null, length5, length6);
                        statePush();
                        render((SVG.Symbol) svgObjectResolveIRI, boxMakeViewPort2);
                        statePop();
                    } else {
                        render(svgObjectResolveIRI);
                    }
                    parentPop();
                    if (zPushLayer) {
                        popLayer(use);
                    }
                    updateParentBoundingBox(use);
                }
            }
        }
    }

    private Path makePathAndBoundingBox(SVG.Text text) {
        List<SVG.Length> list = text.f6322x;
        float fFloatValueY = 0.0f;
        float fFloatValueX = (list == null || list.size() == 0) ? 0.0f : text.f6322x.get(0).floatValueX(this);
        List<SVG.Length> list2 = text.f6323y;
        float fFloatValueY2 = (list2 == null || list2.size() == 0) ? 0.0f : text.f6323y.get(0).floatValueY(this);
        List<SVG.Length> list3 = text.dx;
        float fFloatValueX2 = (list3 == null || list3.size() == 0) ? 0.0f : text.dx.get(0).floatValueX(this);
        List<SVG.Length> list4 = text.dy;
        if (list4 != null && list4.size() != 0) {
            fFloatValueY = text.dy.get(0).floatValueY(this);
        }
        if (this.state.style.textAnchor != SVG.Style.TextAnchor.Start) {
            float fCalculateTextWidth = calculateTextWidth(text);
            if (this.state.style.textAnchor == SVG.Style.TextAnchor.Middle) {
                fCalculateTextWidth /= 2.0f;
            }
            fFloatValueX -= fCalculateTextWidth;
        }
        if (text.boundingBox == null) {
            TextBoundsCalculator textBoundsCalculator = new TextBoundsCalculator(fFloatValueX, fFloatValueY2);
            enumerateTextSpans(text, textBoundsCalculator);
            RectF rectF = textBoundsCalculator.bbox;
            text.boundingBox = new SVG.Box(rectF.left, rectF.top, rectF.width(), textBoundsCalculator.bbox.height());
        }
        Path path = new Path();
        enumerateTextSpans(text, new PlainTextToPath(fFloatValueX + fFloatValueX2, fFloatValueY2 + fFloatValueY, path));
        return path;
    }

    private void render(SVG.Path path) {
        debug("Path render", new Object[0]);
        if (path.f6312d == null) {
            return;
        }
        updateStyleForElement(this.state, path);
        if (display() && visible()) {
            RendererState rendererState = this.state;
            if (rendererState.hasStroke || rendererState.hasFill) {
                Matrix matrix = path.transform;
                if (matrix != null) {
                    this.canvas.concat(matrix);
                }
                Path path2 = new PathConverter(path.f6312d).getPath();
                if (path.boundingBox == null) {
                    path.boundingBox = calculatePathBounds(path2);
                }
                updateParentBoundingBox(path);
                checkForGradientsAndPatterns(path);
                checkForClipPath(path);
                boolean zPushLayer = pushLayer();
                if (this.state.hasFill) {
                    path2.setFillType(getFillTypeFromState());
                    doFilledPath(path, path2);
                }
                if (this.state.hasStroke) {
                    doStroke(path2);
                }
                renderMarkers(path);
                if (zPushLayer) {
                    popLayer(path);
                }
            }
        }
    }

    private void render(SVG.Rect rect) {
        debug("Rect render", new Object[0]);
        SVG.Length length = rect.width;
        if (length == null || rect.height == null || length.isZero() || rect.height.isZero()) {
            return;
        }
        updateStyleForElement(this.state, rect);
        if (display() && visible()) {
            Matrix matrix = rect.transform;
            if (matrix != null) {
                this.canvas.concat(matrix);
            }
            Path pathMakePathAndBoundingBox = makePathAndBoundingBox(rect);
            updateParentBoundingBox(rect);
            checkForGradientsAndPatterns(rect);
            checkForClipPath(rect);
            boolean zPushLayer = pushLayer();
            if (this.state.hasFill) {
                doFilledPath(rect, pathMakePathAndBoundingBox);
            }
            if (this.state.hasStroke) {
                doStroke(pathMakePathAndBoundingBox);
            }
            if (zPushLayer) {
                popLayer(rect);
            }
        }
    }

    private void render(SVG.Circle circle) {
        debug("Circle render", new Object[0]);
        SVG.Length length = circle.f6305r;
        if (length == null || length.isZero()) {
            return;
        }
        updateStyleForElement(this.state, circle);
        if (display() && visible()) {
            Matrix matrix = circle.transform;
            if (matrix != null) {
                this.canvas.concat(matrix);
            }
            Path pathMakePathAndBoundingBox = makePathAndBoundingBox(circle);
            updateParentBoundingBox(circle);
            checkForGradientsAndPatterns(circle);
            checkForClipPath(circle);
            boolean zPushLayer = pushLayer();
            if (this.state.hasFill) {
                doFilledPath(circle, pathMakePathAndBoundingBox);
            }
            if (this.state.hasStroke) {
                doStroke(pathMakePathAndBoundingBox);
            }
            if (zPushLayer) {
                popLayer(circle);
            }
        }
    }

    private void render(SVG.Ellipse ellipse) {
        debug("Ellipse render", new Object[0]);
        SVG.Length length = ellipse.rx;
        if (length == null || ellipse.ry == null || length.isZero() || ellipse.ry.isZero()) {
            return;
        }
        updateStyleForElement(this.state, ellipse);
        if (display() && visible()) {
            Matrix matrix = ellipse.transform;
            if (matrix != null) {
                this.canvas.concat(matrix);
            }
            Path pathMakePathAndBoundingBox = makePathAndBoundingBox(ellipse);
            updateParentBoundingBox(ellipse);
            checkForGradientsAndPatterns(ellipse);
            checkForClipPath(ellipse);
            boolean zPushLayer = pushLayer();
            if (this.state.hasFill) {
                doFilledPath(ellipse, pathMakePathAndBoundingBox);
            }
            if (this.state.hasStroke) {
                doStroke(pathMakePathAndBoundingBox);
            }
            if (zPushLayer) {
                popLayer(ellipse);
            }
        }
    }

    private void render(SVG.Line line) {
        debug("Line render", new Object[0]);
        updateStyleForElement(this.state, line);
        if (display() && visible() && this.state.hasStroke) {
            Matrix matrix = line.transform;
            if (matrix != null) {
                this.canvas.concat(matrix);
            }
            Path pathMakePathAndBoundingBox = makePathAndBoundingBox(line);
            updateParentBoundingBox(line);
            checkForGradientsAndPatterns(line);
            checkForClipPath(line);
            boolean zPushLayer = pushLayer();
            doStroke(pathMakePathAndBoundingBox);
            renderMarkers(line);
            if (zPushLayer) {
                popLayer(line);
            }
        }
    }

    private void render(SVG.PolyLine polyLine) {
        debug("PolyLine render", new Object[0]);
        updateStyleForElement(this.state, polyLine);
        if (display() && visible()) {
            RendererState rendererState = this.state;
            if (rendererState.hasStroke || rendererState.hasFill) {
                Matrix matrix = polyLine.transform;
                if (matrix != null) {
                    this.canvas.concat(matrix);
                }
                if (polyLine.points.length < 2) {
                    return;
                }
                Path pathMakePathAndBoundingBox = makePathAndBoundingBox(polyLine);
                updateParentBoundingBox(polyLine);
                pathMakePathAndBoundingBox.setFillType(getFillTypeFromState());
                checkForGradientsAndPatterns(polyLine);
                checkForClipPath(polyLine);
                boolean zPushLayer = pushLayer();
                if (this.state.hasFill) {
                    doFilledPath(polyLine, pathMakePathAndBoundingBox);
                }
                if (this.state.hasStroke) {
                    doStroke(pathMakePathAndBoundingBox);
                }
                renderMarkers(polyLine);
                if (zPushLayer) {
                    popLayer(polyLine);
                }
            }
        }
    }

    private void render(SVG.Polygon polygon) {
        debug("Polygon render", new Object[0]);
        updateStyleForElement(this.state, polygon);
        if (display() && visible()) {
            RendererState rendererState = this.state;
            if (rendererState.hasStroke || rendererState.hasFill) {
                Matrix matrix = polygon.transform;
                if (matrix != null) {
                    this.canvas.concat(matrix);
                }
                if (polygon.points.length < 2) {
                    return;
                }
                Path pathMakePathAndBoundingBox = makePathAndBoundingBox(polygon);
                updateParentBoundingBox(polygon);
                checkForGradientsAndPatterns(polygon);
                checkForClipPath(polygon);
                boolean zPushLayer = pushLayer();
                if (this.state.hasFill) {
                    doFilledPath(polygon, pathMakePathAndBoundingBox);
                }
                if (this.state.hasStroke) {
                    doStroke(pathMakePathAndBoundingBox);
                }
                renderMarkers(polygon);
                if (zPushLayer) {
                    popLayer(polygon);
                }
            }
        }
    }

    private void render(SVG.Text text) {
        debug("Text render", new Object[0]);
        updateStyleForElement(this.state, text);
        if (display()) {
            Matrix matrix = text.transform;
            if (matrix != null) {
                this.canvas.concat(matrix);
            }
            List<SVG.Length> list = text.f6322x;
            float fFloatValueY = 0.0f;
            float fFloatValueX = (list == null || list.size() == 0) ? 0.0f : text.f6322x.get(0).floatValueX(this);
            List<SVG.Length> list2 = text.f6323y;
            float fFloatValueY2 = (list2 == null || list2.size() == 0) ? 0.0f : text.f6323y.get(0).floatValueY(this);
            List<SVG.Length> list3 = text.dx;
            float fFloatValueX2 = (list3 == null || list3.size() == 0) ? 0.0f : text.dx.get(0).floatValueX(this);
            List<SVG.Length> list4 = text.dy;
            if (list4 != null && list4.size() != 0) {
                fFloatValueY = text.dy.get(0).floatValueY(this);
            }
            SVG.Style.TextAnchor anchorPosition = getAnchorPosition();
            if (anchorPosition != SVG.Style.TextAnchor.Start) {
                float fCalculateTextWidth = calculateTextWidth(text);
                if (anchorPosition == SVG.Style.TextAnchor.Middle) {
                    fCalculateTextWidth /= 2.0f;
                }
                fFloatValueX -= fCalculateTextWidth;
            }
            if (text.boundingBox == null) {
                TextBoundsCalculator textBoundsCalculator = new TextBoundsCalculator(fFloatValueX, fFloatValueY2);
                enumerateTextSpans(text, textBoundsCalculator);
                RectF rectF = textBoundsCalculator.bbox;
                text.boundingBox = new SVG.Box(rectF.left, rectF.top, rectF.width(), textBoundsCalculator.bbox.height());
            }
            updateParentBoundingBox(text);
            checkForGradientsAndPatterns(text);
            checkForClipPath(text);
            boolean zPushLayer = pushLayer();
            enumerateTextSpans(text, new PlainTextDrawer(fFloatValueX + fFloatValueX2, fFloatValueY2 + fFloatValueY));
            if (zPushLayer) {
                popLayer(text);
            }
        }
    }

    private void render(SVG.Symbol symbol, SVG.Box box) {
        debug("Symbol render", new Object[0]);
        if (box.width == 0.0f || box.height == 0.0f) {
            return;
        }
        PreserveAspectRatio preserveAspectRatio = symbol.preserveAspectRatio;
        if (preserveAspectRatio == null) {
            preserveAspectRatio = PreserveAspectRatio.LETTERBOX;
        }
        updateStyleForElement(this.state, symbol);
        RendererState rendererState = this.state;
        rendererState.viewPort = box;
        if (!rendererState.style.overflow.booleanValue()) {
            SVG.Box box2 = this.state.viewPort;
            setClipRect(box2.minX, box2.minY, box2.width, box2.height);
        }
        SVG.Box box3 = symbol.viewBox;
        if (box3 != null) {
            this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, box3, preserveAspectRatio));
            this.state.viewBox = symbol.viewBox;
        } else {
            Canvas canvas = this.canvas;
            SVG.Box box4 = this.state.viewPort;
            canvas.translate(box4.minX, box4.minY);
        }
        boolean zPushLayer = pushLayer();
        renderChildren(symbol, true);
        if (zPushLayer) {
            popLayer(symbol);
        }
        updateParentBoundingBox(symbol);
    }

    private void render(SVG.Image image) {
        SVG.Length length;
        String str;
        debug("Image render", new Object[0]);
        SVG.Length length2 = image.width;
        if (length2 == null || length2.isZero() || (length = image.height) == null || length.isZero() || (str = image.href) == null) {
            return;
        }
        PreserveAspectRatio preserveAspectRatio = image.preserveAspectRatio;
        if (preserveAspectRatio == null) {
            preserveAspectRatio = PreserveAspectRatio.LETTERBOX;
        }
        Bitmap bitmapCheckForImageDataURL = checkForImageDataURL(str);
        if (bitmapCheckForImageDataURL == null) {
            SVGExternalFileResolver fileResolver = SVG.getFileResolver();
            if (fileResolver == null) {
                return;
            } else {
                bitmapCheckForImageDataURL = fileResolver.resolveImage(image.href);
            }
        }
        if (bitmapCheckForImageDataURL == null) {
            error("Could not locate image '%s'", image.href);
            return;
        }
        SVG.Box box = new SVG.Box(0.0f, 0.0f, bitmapCheckForImageDataURL.getWidth(), bitmapCheckForImageDataURL.getHeight());
        updateStyleForElement(this.state, image);
        if (display() && visible()) {
            Matrix matrix = image.transform;
            if (matrix != null) {
                this.canvas.concat(matrix);
            }
            SVG.Length length3 = image.f6306x;
            float fFloatValueX = length3 != null ? length3.floatValueX(this) : 0.0f;
            SVG.Length length4 = image.f6307y;
            this.state.viewPort = new SVG.Box(fFloatValueX, length4 != null ? length4.floatValueY(this) : 0.0f, image.width.floatValueX(this), image.height.floatValueX(this));
            if (!this.state.style.overflow.booleanValue()) {
                SVG.Box box2 = this.state.viewPort;
                setClipRect(box2.minX, box2.minY, box2.width, box2.height);
            }
            image.boundingBox = this.state.viewPort;
            updateParentBoundingBox(image);
            checkForClipPath(image);
            boolean zPushLayer = pushLayer();
            viewportFill();
            this.canvas.save();
            this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, box, preserveAspectRatio));
            this.canvas.drawBitmap(bitmapCheckForImageDataURL, 0.0f, 0.0f, new Paint(this.state.style.imageRendering != SVG.Style.RenderQuality.optimizeSpeed ? 2 : 0));
            this.canvas.restore();
            if (zPushLayer) {
                popLayer(image);
            }
        }
    }
}
