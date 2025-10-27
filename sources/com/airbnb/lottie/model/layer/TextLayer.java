package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import cn.hutool.core.text.StrPool;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.content.ContentGroup;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TextKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.DocumentData;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class TextLayer extends BaseLayer {
    private final LongSparseArray<String> codePointCache;

    @Nullable
    private BaseKeyframeAnimation<Integer, Integer> colorAnimation;

    @Nullable
    private BaseKeyframeAnimation<Integer, Integer> colorCallbackAnimation;
    private final LottieComposition composition;
    private final Map<FontCharacter, List<ContentGroup>> contentsForCharacter;
    private final Paint fillPaint;
    private final LottieDrawable lottieDrawable;
    private final Matrix matrix;
    private final RectF rectF;
    private final StringBuilder stringBuilder;

    @Nullable
    private BaseKeyframeAnimation<Integer, Integer> strokeColorAnimation;

    @Nullable
    private BaseKeyframeAnimation<Integer, Integer> strokeColorCallbackAnimation;
    private final Paint strokePaint;

    @Nullable
    private BaseKeyframeAnimation<Float, Float> strokeWidthAnimation;

    @Nullable
    private BaseKeyframeAnimation<Float, Float> strokeWidthCallbackAnimation;
    private final TextKeyframeAnimation textAnimation;

    @Nullable
    private BaseKeyframeAnimation<Float, Float> textSizeAnimation;

    @Nullable
    private BaseKeyframeAnimation<Float, Float> textSizeCallbackAnimation;

    @Nullable
    private BaseKeyframeAnimation<Float, Float> trackingAnimation;

    @Nullable
    private BaseKeyframeAnimation<Float, Float> trackingCallbackAnimation;

    /* renamed from: com.airbnb.lottie.model.layer.TextLayer$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification;

        static {
            int[] iArr = new int[DocumentData.Justification.values().length];
            $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification = iArr;
            try {
                iArr[DocumentData.Justification.LEFT_ALIGN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[DocumentData.Justification.RIGHT_ALIGN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[DocumentData.Justification.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public TextLayer(LottieDrawable lottieDrawable, Layer layer) {
        AnimatableFloatValue animatableFloatValue;
        AnimatableFloatValue animatableFloatValue2;
        AnimatableColorValue animatableColorValue;
        AnimatableColorValue animatableColorValue2;
        super(lottieDrawable, layer);
        this.stringBuilder = new StringBuilder(2);
        this.rectF = new RectF();
        this.matrix = new Matrix();
        int i2 = 1;
        this.fillPaint = new Paint(i2) { // from class: com.airbnb.lottie.model.layer.TextLayer.1
            {
                setStyle(Paint.Style.FILL);
            }
        };
        this.strokePaint = new Paint(i2) { // from class: com.airbnb.lottie.model.layer.TextLayer.2
            {
                setStyle(Paint.Style.STROKE);
            }
        };
        this.contentsForCharacter = new HashMap();
        this.codePointCache = new LongSparseArray<>();
        this.lottieDrawable = lottieDrawable;
        this.composition = layer.getComposition();
        TextKeyframeAnimation textKeyframeAnimationCreateAnimation = layer.getText().createAnimation();
        this.textAnimation = textKeyframeAnimationCreateAnimation;
        textKeyframeAnimationCreateAnimation.addUpdateListener(this);
        addAnimation(textKeyframeAnimationCreateAnimation);
        AnimatableTextProperties textProperties = layer.getTextProperties();
        if (textProperties != null && (animatableColorValue2 = textProperties.color) != null) {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimationCreateAnimation = animatableColorValue2.createAnimation();
            this.colorAnimation = baseKeyframeAnimationCreateAnimation;
            baseKeyframeAnimationCreateAnimation.addUpdateListener(this);
            addAnimation(this.colorAnimation);
        }
        if (textProperties != null && (animatableColorValue = textProperties.stroke) != null) {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimationCreateAnimation2 = animatableColorValue.createAnimation();
            this.strokeColorAnimation = baseKeyframeAnimationCreateAnimation2;
            baseKeyframeAnimationCreateAnimation2.addUpdateListener(this);
            addAnimation(this.strokeColorAnimation);
        }
        if (textProperties != null && (animatableFloatValue2 = textProperties.strokeWidth) != null) {
            BaseKeyframeAnimation<Float, Float> baseKeyframeAnimationCreateAnimation3 = animatableFloatValue2.createAnimation();
            this.strokeWidthAnimation = baseKeyframeAnimationCreateAnimation3;
            baseKeyframeAnimationCreateAnimation3.addUpdateListener(this);
            addAnimation(this.strokeWidthAnimation);
        }
        if (textProperties == null || (animatableFloatValue = textProperties.tracking) == null) {
            return;
        }
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimationCreateAnimation4 = animatableFloatValue.createAnimation();
        this.trackingAnimation = baseKeyframeAnimationCreateAnimation4;
        baseKeyframeAnimationCreateAnimation4.addUpdateListener(this);
        addAnimation(this.trackingAnimation);
    }

    private void applyJustification(DocumentData.Justification justification, Canvas canvas, float f2) {
        int i2 = AnonymousClass3.$SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[justification.ordinal()];
        if (i2 == 2) {
            canvas.translate(-f2, 0.0f);
        } else {
            if (i2 != 3) {
                return;
            }
            canvas.translate((-f2) / 2.0f, 0.0f);
        }
    }

    private String codePointToString(String str, int i2) {
        int iCodePointAt = str.codePointAt(i2);
        int iCharCount = Character.charCount(iCodePointAt) + i2;
        while (iCharCount < str.length()) {
            int iCodePointAt2 = str.codePointAt(iCharCount);
            if (!isModifier(iCodePointAt2)) {
                break;
            }
            iCharCount += Character.charCount(iCodePointAt2);
            iCodePointAt = (iCodePointAt * 31) + iCodePointAt2;
        }
        long j2 = iCodePointAt;
        if (this.codePointCache.containsKey(j2)) {
            return this.codePointCache.get(j2);
        }
        this.stringBuilder.setLength(0);
        while (i2 < iCharCount) {
            int iCodePointAt3 = str.codePointAt(i2);
            this.stringBuilder.appendCodePoint(iCodePointAt3);
            i2 += Character.charCount(iCodePointAt3);
        }
        String string = this.stringBuilder.toString();
        this.codePointCache.put(j2, string);
        return string;
    }

    private void drawCharacter(String str, Paint paint, Canvas canvas) {
        if (paint.getColor() == 0) {
            return;
        }
        if (paint.getStyle() == Paint.Style.STROKE && paint.getStrokeWidth() == 0.0f) {
            return;
        }
        canvas.drawText(str, 0, str.length(), 0.0f, 0.0f, paint);
    }

    private void drawCharacterAsGlyph(FontCharacter fontCharacter, Matrix matrix, float f2, DocumentData documentData, Canvas canvas) {
        List<ContentGroup> contentsForCharacter = getContentsForCharacter(fontCharacter);
        for (int i2 = 0; i2 < contentsForCharacter.size(); i2++) {
            Path path = contentsForCharacter.get(i2).getPath();
            path.computeBounds(this.rectF, false);
            this.matrix.set(matrix);
            this.matrix.preTranslate(0.0f, (-documentData.baselineShift) * Utils.dpScale());
            this.matrix.preScale(f2, f2);
            path.transform(this.matrix);
            if (documentData.strokeOverFill) {
                drawGlyph(path, this.fillPaint, canvas);
                drawGlyph(path, this.strokePaint, canvas);
            } else {
                drawGlyph(path, this.strokePaint, canvas);
                drawGlyph(path, this.fillPaint, canvas);
            }
        }
    }

    private void drawCharacterFromFont(String str, DocumentData documentData, Canvas canvas) {
        if (documentData.strokeOverFill) {
            drawCharacter(str, this.fillPaint, canvas);
            drawCharacter(str, this.strokePaint, canvas);
        } else {
            drawCharacter(str, this.strokePaint, canvas);
            drawCharacter(str, this.fillPaint, canvas);
        }
    }

    private void drawFontTextLine(String str, DocumentData documentData, Canvas canvas, float f2) {
        int length = 0;
        while (length < str.length()) {
            String strCodePointToString = codePointToString(str, length);
            length += strCodePointToString.length();
            drawCharacterFromFont(strCodePointToString, documentData, canvas);
            canvas.translate(this.fillPaint.measureText(strCodePointToString) + f2, 0.0f);
        }
    }

    private void drawGlyph(Path path, Paint paint, Canvas canvas) {
        if (paint.getColor() == 0) {
            return;
        }
        if (paint.getStyle() == Paint.Style.STROKE && paint.getStrokeWidth() == 0.0f) {
            return;
        }
        canvas.drawPath(path, paint);
    }

    private void drawGlyphTextLine(String str, DocumentData documentData, Matrix matrix, Font font, Canvas canvas, float f2, float f3) {
        float fFloatValue;
        for (int i2 = 0; i2 < str.length(); i2++) {
            FontCharacter fontCharacter = this.composition.getCharacters().get(FontCharacter.hashFor(str.charAt(i2), font.getFamily(), font.getStyle()));
            if (fontCharacter != null) {
                drawCharacterAsGlyph(fontCharacter, matrix, f3, documentData, canvas);
                float width = ((float) fontCharacter.getWidth()) * f3 * Utils.dpScale() * f2;
                float f4 = documentData.tracking / 10.0f;
                BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation = this.trackingCallbackAnimation;
                if (baseKeyframeAnimation != null) {
                    fFloatValue = baseKeyframeAnimation.getValue().floatValue();
                } else {
                    BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation2 = this.trackingAnimation;
                    if (baseKeyframeAnimation2 != null) {
                        fFloatValue = baseKeyframeAnimation2.getValue().floatValue();
                    }
                    canvas.translate(width + (f4 * f2), 0.0f);
                }
                f4 += fFloatValue;
                canvas.translate(width + (f4 * f2), 0.0f);
            }
        }
    }

    private void drawTextGlyphs(DocumentData documentData, Matrix matrix, Font font, Canvas canvas) {
        float fFloatValue;
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation = this.textSizeCallbackAnimation;
        if (baseKeyframeAnimation != null) {
            fFloatValue = baseKeyframeAnimation.getValue().floatValue();
        } else {
            BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation2 = this.textSizeAnimation;
            fFloatValue = baseKeyframeAnimation2 != null ? baseKeyframeAnimation2.getValue().floatValue() : documentData.size;
        }
        float f2 = fFloatValue / 100.0f;
        float scale = Utils.getScale(matrix);
        String str = documentData.text;
        float fDpScale = documentData.lineHeight * Utils.dpScale();
        List<String> textLines = getTextLines(str);
        int size = textLines.size();
        for (int i2 = 0; i2 < size; i2++) {
            String str2 = textLines.get(i2);
            float textLineWidthForGlyphs = getTextLineWidthForGlyphs(str2, font, f2, scale);
            canvas.save();
            applyJustification(documentData.justification, canvas, textLineWidthForGlyphs);
            canvas.translate(0.0f, (i2 * fDpScale) - (((size - 1) * fDpScale) / 2.0f));
            drawGlyphTextLine(str2, documentData, matrix, font, canvas, scale, f2);
            canvas.restore();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00a7 A[LOOP:0: B:23:0x00a5->B:24:0x00a7, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void drawTextWithFont(com.airbnb.lottie.model.DocumentData r7, com.airbnb.lottie.model.Font r8, android.graphics.Matrix r9, android.graphics.Canvas r10) {
        /*
            r6 = this;
            com.airbnb.lottie.utils.Utils.getScale(r9)
            com.airbnb.lottie.LottieDrawable r9 = r6.lottieDrawable
            java.lang.String r0 = r8.getFamily()
            java.lang.String r8 = r8.getStyle()
            android.graphics.Typeface r8 = r9.getTypeface(r0, r8)
            if (r8 != 0) goto L14
            return
        L14:
            java.lang.String r9 = r7.text
            com.airbnb.lottie.LottieDrawable r0 = r6.lottieDrawable
            com.airbnb.lottie.TextDelegate r0 = r0.getTextDelegate()
            if (r0 == 0) goto L22
            java.lang.String r9 = r0.getTextInternal(r9)
        L22:
            android.graphics.Paint r0 = r6.fillPaint
            r0.setTypeface(r8)
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation<java.lang.Float, java.lang.Float> r8 = r6.textSizeCallbackAnimation
            if (r8 == 0) goto L36
            java.lang.Object r8 = r8.getValue()
            java.lang.Float r8 = (java.lang.Float) r8
            float r8 = r8.floatValue()
            goto L47
        L36:
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation<java.lang.Float, java.lang.Float> r8 = r6.textSizeAnimation
            if (r8 == 0) goto L45
            java.lang.Object r8 = r8.getValue()
            java.lang.Float r8 = (java.lang.Float) r8
            float r8 = r8.floatValue()
            goto L47
        L45:
            float r8 = r7.size
        L47:
            android.graphics.Paint r0 = r6.fillPaint
            float r1 = com.airbnb.lottie.utils.Utils.dpScale()
            float r1 = r1 * r8
            r0.setTextSize(r1)
            android.graphics.Paint r0 = r6.strokePaint
            android.graphics.Paint r1 = r6.fillPaint
            android.graphics.Typeface r1 = r1.getTypeface()
            r0.setTypeface(r1)
            android.graphics.Paint r0 = r6.strokePaint
            android.graphics.Paint r1 = r6.fillPaint
            float r1 = r1.getTextSize()
            r0.setTextSize(r1)
            float r0 = r7.lineHeight
            float r1 = com.airbnb.lottie.utils.Utils.dpScale()
            float r0 = r0 * r1
            int r1 = r7.tracking
            float r1 = (float) r1
            r2 = 1092616192(0x41200000, float:10.0)
            float r1 = r1 / r2
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation<java.lang.Float, java.lang.Float> r2 = r6.trackingCallbackAnimation
            if (r2 == 0) goto L84
            java.lang.Object r2 = r2.getValue()
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
        L82:
            float r1 = r1 + r2
            goto L93
        L84:
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation<java.lang.Float, java.lang.Float> r2 = r6.trackingAnimation
            if (r2 == 0) goto L93
            java.lang.Object r2 = r2.getValue()
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            goto L82
        L93:
            float r2 = com.airbnb.lottie.utils.Utils.dpScale()
            float r1 = r1 * r2
            float r1 = r1 * r8
            r8 = 1120403456(0x42c80000, float:100.0)
            float r1 = r1 / r8
            java.util.List r8 = r6.getTextLines(r9)
            int r9 = r8.size()
            r2 = 0
        La5:
            if (r2 >= r9) goto Ldb
            java.lang.Object r3 = r8.get(r2)
            java.lang.String r3 = (java.lang.String) r3
            android.graphics.Paint r4 = r6.strokePaint
            float r4 = r4.measureText(r3)
            int r5 = r3.length()
            int r5 = r5 + (-1)
            float r5 = (float) r5
            float r5 = r5 * r1
            float r4 = r4 + r5
            r10.save()
            com.airbnb.lottie.model.DocumentData$Justification r5 = r7.justification
            r6.applyJustification(r5, r10, r4)
            int r4 = r9 + (-1)
            float r4 = (float) r4
            float r4 = r4 * r0
            r5 = 1073741824(0x40000000, float:2.0)
            float r4 = r4 / r5
            float r5 = (float) r2
            float r5 = r5 * r0
            float r5 = r5 - r4
            r4 = 0
            r10.translate(r4, r5)
            r6.drawFontTextLine(r3, r7, r10, r1)
            r10.restore()
            int r2 = r2 + 1
            goto La5
        Ldb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.model.layer.TextLayer.drawTextWithFont(com.airbnb.lottie.model.DocumentData, com.airbnb.lottie.model.Font, android.graphics.Matrix, android.graphics.Canvas):void");
    }

    private List<ContentGroup> getContentsForCharacter(FontCharacter fontCharacter) {
        if (this.contentsForCharacter.containsKey(fontCharacter)) {
            return this.contentsForCharacter.get(fontCharacter);
        }
        List<ShapeGroup> shapes = fontCharacter.getShapes();
        int size = shapes.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            arrayList.add(new ContentGroup(this.lottieDrawable, this, shapes.get(i2)));
        }
        this.contentsForCharacter.put(fontCharacter, arrayList);
        return arrayList;
    }

    private float getTextLineWidthForGlyphs(String str, Font font, float f2, float f3) {
        float width = 0.0f;
        for (int i2 = 0; i2 < str.length(); i2++) {
            FontCharacter fontCharacter = this.composition.getCharacters().get(FontCharacter.hashFor(str.charAt(i2), font.getFamily(), font.getStyle()));
            if (fontCharacter != null) {
                width = (float) (width + (fontCharacter.getWidth() * f2 * Utils.dpScale() * f3));
            }
        }
        return width;
    }

    private List<String> getTextLines(String str) {
        return Arrays.asList(str.replaceAll("\r\n", StrPool.CR).replaceAll("\n", StrPool.CR).split(StrPool.CR));
    }

    private boolean isModifier(int i2) {
        return Character.getType(i2) == 16 || Character.getType(i2) == 27 || Character.getType(i2) == 6 || Character.getType(i2) == 28 || Character.getType(i2) == 19;
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.KeyPathElement
    public <T> void addValueCallback(T t2, @Nullable LottieValueCallback<T> lottieValueCallback) {
        super.addValueCallback(t2, lottieValueCallback);
        if (t2 == LottieProperty.COLOR) {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation = this.colorCallbackAnimation;
            if (baseKeyframeAnimation != null) {
                removeAnimation(baseKeyframeAnimation);
            }
            if (lottieValueCallback == null) {
                this.colorCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.colorCallbackAnimation = valueCallbackKeyframeAnimation;
            valueCallbackKeyframeAnimation.addUpdateListener(this);
            addAnimation(this.colorCallbackAnimation);
            return;
        }
        if (t2 == LottieProperty.STROKE_COLOR) {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation2 = this.strokeColorCallbackAnimation;
            if (baseKeyframeAnimation2 != null) {
                removeAnimation(baseKeyframeAnimation2);
            }
            if (lottieValueCallback == null) {
                this.strokeColorCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.strokeColorCallbackAnimation = valueCallbackKeyframeAnimation2;
            valueCallbackKeyframeAnimation2.addUpdateListener(this);
            addAnimation(this.strokeColorCallbackAnimation);
            return;
        }
        if (t2 == LottieProperty.STROKE_WIDTH) {
            BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation3 = this.strokeWidthCallbackAnimation;
            if (baseKeyframeAnimation3 != null) {
                removeAnimation(baseKeyframeAnimation3);
            }
            if (lottieValueCallback == null) {
                this.strokeWidthCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation3 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.strokeWidthCallbackAnimation = valueCallbackKeyframeAnimation3;
            valueCallbackKeyframeAnimation3.addUpdateListener(this);
            addAnimation(this.strokeWidthCallbackAnimation);
            return;
        }
        if (t2 == LottieProperty.TEXT_TRACKING) {
            BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation4 = this.trackingCallbackAnimation;
            if (baseKeyframeAnimation4 != null) {
                removeAnimation(baseKeyframeAnimation4);
            }
            if (lottieValueCallback == null) {
                this.trackingCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation4 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.trackingCallbackAnimation = valueCallbackKeyframeAnimation4;
            valueCallbackKeyframeAnimation4.addUpdateListener(this);
            addAnimation(this.trackingCallbackAnimation);
            return;
        }
        if (t2 == LottieProperty.TEXT_SIZE) {
            BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation5 = this.textSizeCallbackAnimation;
            if (baseKeyframeAnimation5 != null) {
                removeAnimation(baseKeyframeAnimation5);
            }
            if (lottieValueCallback == null) {
                this.textSizeCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation5 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.textSizeCallbackAnimation = valueCallbackKeyframeAnimation5;
            valueCallbackKeyframeAnimation5.addUpdateListener(this);
            addAnimation(this.textSizeCallbackAnimation);
        }
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    public void drawLayer(Canvas canvas, Matrix matrix, int i2) {
        canvas.save();
        if (!this.lottieDrawable.useTextGlyphs()) {
            canvas.concat(matrix);
        }
        DocumentData value = this.textAnimation.getValue();
        Font font = this.composition.getFonts().get(value.fontName);
        if (font == null) {
            canvas.restore();
            return;
        }
        BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation = this.colorCallbackAnimation;
        if (baseKeyframeAnimation != null) {
            this.fillPaint.setColor(baseKeyframeAnimation.getValue().intValue());
        } else {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation2 = this.colorAnimation;
            if (baseKeyframeAnimation2 != null) {
                this.fillPaint.setColor(baseKeyframeAnimation2.getValue().intValue());
            } else {
                this.fillPaint.setColor(value.color);
            }
        }
        BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation3 = this.strokeColorCallbackAnimation;
        if (baseKeyframeAnimation3 != null) {
            this.strokePaint.setColor(baseKeyframeAnimation3.getValue().intValue());
        } else {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation4 = this.strokeColorAnimation;
            if (baseKeyframeAnimation4 != null) {
                this.strokePaint.setColor(baseKeyframeAnimation4.getValue().intValue());
            } else {
                this.strokePaint.setColor(value.strokeColor);
            }
        }
        int iIntValue = ((this.transform.getOpacity() == null ? 100 : this.transform.getOpacity().getValue().intValue()) * 255) / 100;
        this.fillPaint.setAlpha(iIntValue);
        this.strokePaint.setAlpha(iIntValue);
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation5 = this.strokeWidthCallbackAnimation;
        if (baseKeyframeAnimation5 != null) {
            this.strokePaint.setStrokeWidth(baseKeyframeAnimation5.getValue().floatValue());
        } else {
            BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation6 = this.strokeWidthAnimation;
            if (baseKeyframeAnimation6 != null) {
                this.strokePaint.setStrokeWidth(baseKeyframeAnimation6.getValue().floatValue());
            } else {
                this.strokePaint.setStrokeWidth(value.strokeWidth * Utils.dpScale() * Utils.getScale(matrix));
            }
        }
        if (this.lottieDrawable.useTextGlyphs()) {
            drawTextGlyphs(value, matrix, font, canvas);
        } else {
            drawTextWithFont(value, font, matrix, canvas);
        }
        canvas.restore();
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.animation.content.DrawingContent
    public void getBounds(RectF rectF, Matrix matrix, boolean z2) {
        super.getBounds(rectF, matrix, z2);
        rectF.set(0.0f, 0.0f, this.composition.getBounds().width(), this.composition.getBounds().height());
    }
}
