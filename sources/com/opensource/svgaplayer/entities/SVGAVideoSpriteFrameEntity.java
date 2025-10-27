package com.opensource.svgaplayer.entities;

import android.graphics.Matrix;
import com.alipay.sdk.sys.a;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.opensource.svgaplayer.BuildConfig;
import com.opensource.svgaplayer.proto.FrameEntity;
import com.opensource.svgaplayer.proto.Layout;
import com.opensource.svgaplayer.proto.ShapeEntity;
import com.opensource.svgaplayer.proto.Transform;
import com.opensource.svgaplayer.utils.SVGARect;
import com.umeng.analytics.pro.am;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R \u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%¨\u0006&"}, d2 = {"Lcom/opensource/svgaplayer/entities/SVGAVideoSpriteFrameEntity;", "", "obj", "Lorg/json/JSONObject;", "(Lorg/json/JSONObject;)V", "Lcom/opensource/svgaplayer/proto/FrameEntity;", "(Lcom/opensource/svgaplayer/proto/FrameEntity;)V", "alpha", "", "getAlpha", "()D", "setAlpha", "(D)V", TtmlNode.TAG_LAYOUT, "Lcom/opensource/svgaplayer/utils/SVGARect;", "getLayout", "()Lcom/opensource/svgaplayer/utils/SVGARect;", "setLayout", "(Lcom/opensource/svgaplayer/utils/SVGARect;)V", "maskPath", "Lcom/opensource/svgaplayer/entities/SVGAPathEntity;", "getMaskPath", "()Lcom/opensource/svgaplayer/entities/SVGAPathEntity;", "setMaskPath", "(Lcom/opensource/svgaplayer/entities/SVGAPathEntity;)V", "shapes", "", "Lcom/opensource/svgaplayer/entities/SVGAVideoShapeEntity;", "getShapes", "()Ljava/util/List;", "setShapes", "(Ljava/util/List;)V", "transform", "Landroid/graphics/Matrix;", "getTransform", "()Landroid/graphics/Matrix;", "setTransform", "(Landroid/graphics/Matrix;)V", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGAVideoSpriteFrameEntity {
    private double alpha;

    @NotNull
    private SVGARect layout;

    @Nullable
    private SVGAPathEntity maskPath;

    @NotNull
    private List<SVGAVideoShapeEntity> shapes;

    @NotNull
    private Matrix transform;

    public SVGAVideoSpriteFrameEntity(@NotNull JSONObject obj) {
        int i2;
        int i3;
        SVGAVideoSpriteFrameEntity sVGAVideoSpriteFrameEntity = this;
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        sVGAVideoSpriteFrameEntity.layout = new SVGARect(0.0d, 0.0d, 0.0d, 0.0d);
        sVGAVideoSpriteFrameEntity.transform = new Matrix();
        sVGAVideoSpriteFrameEntity.shapes = CollectionsKt__CollectionsKt.emptyList();
        sVGAVideoSpriteFrameEntity.alpha = obj.optDouble("alpha", 0.0d);
        JSONObject jSONObjectOptJSONObject = obj.optJSONObject(TtmlNode.TAG_LAYOUT);
        if (jSONObjectOptJSONObject != null) {
            sVGAVideoSpriteFrameEntity.layout = new SVGARect(jSONObjectOptJSONObject.optDouble("x", 0.0d), jSONObjectOptJSONObject.optDouble("y", 0.0d), jSONObjectOptJSONObject.optDouble("width", 0.0d), jSONObjectOptJSONObject.optDouble("height", 0.0d));
        }
        JSONObject jSONObjectOptJSONObject2 = obj.optJSONObject("transform");
        if (jSONObjectOptJSONObject2 != null) {
            double dOptDouble = jSONObjectOptJSONObject2.optDouble(am.av, 1.0d);
            double dOptDouble2 = jSONObjectOptJSONObject2.optDouble("b", 0.0d);
            double dOptDouble3 = jSONObjectOptJSONObject2.optDouble(am.aF, 0.0d);
            double dOptDouble4 = jSONObjectOptJSONObject2.optDouble("d", 1.0d);
            double dOptDouble5 = jSONObjectOptJSONObject2.optDouble("tx", 0.0d);
            double dOptDouble6 = jSONObjectOptJSONObject2.optDouble(a.f3322g, 0.0d);
            i3 = 0;
            float f2 = (float) dOptDouble3;
            i2 = 1;
            float f3 = (float) 0.0d;
            float[] fArr = {(float) dOptDouble, f2, (float) dOptDouble5, (float) dOptDouble2, (float) dOptDouble4, (float) dOptDouble6, f3, f3, (float) 1.0d};
            sVGAVideoSpriteFrameEntity = this;
            sVGAVideoSpriteFrameEntity.transform.setValues(fArr);
        } else {
            i2 = 1;
            i3 = 0;
        }
        String strOptString = obj.optString("clipPath");
        if (strOptString != null) {
            if ((strOptString.length() <= 0 ? i3 : i2) != 0) {
                sVGAVideoSpriteFrameEntity.maskPath = new SVGAPathEntity(strOptString);
            }
        }
        JSONArray jSONArrayOptJSONArray = obj.optJSONArray("shapes");
        if (jSONArrayOptJSONArray != null) {
            ArrayList arrayList = new ArrayList();
            int length = jSONArrayOptJSONArray.length();
            for (int i4 = i3; i4 < length; i4++) {
                JSONObject jSONObjectOptJSONObject3 = jSONArrayOptJSONArray.optJSONObject(i4);
                if (jSONObjectOptJSONObject3 != null) {
                    arrayList.add(new SVGAVideoShapeEntity(jSONObjectOptJSONObject3));
                }
            }
            sVGAVideoSpriteFrameEntity.shapes = CollectionsKt___CollectionsKt.toList(arrayList);
        }
    }

    public final double getAlpha() {
        return this.alpha;
    }

    @NotNull
    public final SVGARect getLayout() {
        return this.layout;
    }

    @Nullable
    public final SVGAPathEntity getMaskPath() {
        return this.maskPath;
    }

    @NotNull
    public final List<SVGAVideoShapeEntity> getShapes() {
        return this.shapes;
    }

    @NotNull
    public final Matrix getTransform() {
        return this.transform;
    }

    public final void setAlpha(double d3) {
        this.alpha = d3;
    }

    public final void setLayout(@NotNull SVGARect sVGARect) {
        Intrinsics.checkParameterIsNotNull(sVGARect, "<set-?>");
        this.layout = sVGARect;
    }

    public final void setMaskPath(@Nullable SVGAPathEntity sVGAPathEntity) {
        this.maskPath = sVGAPathEntity;
    }

    public final void setShapes(@NotNull List<SVGAVideoShapeEntity> list) {
        Intrinsics.checkParameterIsNotNull(list, "<set-?>");
        this.shapes = list;
    }

    public final void setTransform(@NotNull Matrix matrix) {
        Intrinsics.checkParameterIsNotNull(matrix, "<set-?>");
        this.transform = matrix;
    }

    public SVGAVideoSpriteFrameEntity(@NotNull FrameEntity obj) {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        this.layout = new SVGARect(0.0d, 0.0d, 0.0d, 0.0d);
        this.transform = new Matrix();
        this.shapes = CollectionsKt__CollectionsKt.emptyList();
        this.alpha = obj.alpha != null ? r0.floatValue() : 0.0f;
        Layout layout = obj.layout;
        if (layout != null) {
            Float f2 = layout.f10687x;
            double dFloatValue = f2 != null ? f2.floatValue() : 0.0f;
            Float f3 = layout.f10688y;
            double dFloatValue2 = f3 != null ? f3.floatValue() : 0.0f;
            Float f4 = layout.width;
            this.layout = new SVGARect(dFloatValue, dFloatValue2, f4 != null ? f4.floatValue() : 0.0f, layout.height != null ? r0.floatValue() : 0.0f);
        }
        Transform transform = obj.transform;
        if (transform != null) {
            float[] fArr = new float[9];
            Float f5 = transform.f10709a;
            float fFloatValue = f5 != null ? f5.floatValue() : 1.0f;
            Float f6 = transform.f10710b;
            float fFloatValue2 = f6 != null ? f6.floatValue() : 0.0f;
            Float f7 = transform.f10711c;
            float fFloatValue3 = f7 != null ? f7.floatValue() : 0.0f;
            Float f8 = transform.f10712d;
            float fFloatValue4 = f8 != null ? f8.floatValue() : 1.0f;
            Float f9 = transform.tx;
            float fFloatValue5 = f9 != null ? f9.floatValue() : 0.0f;
            Float f10 = transform.ty;
            float fFloatValue6 = f10 != null ? f10.floatValue() : 0.0f;
            fArr[0] = fFloatValue;
            fArr[1] = fFloatValue3;
            fArr[2] = fFloatValue5;
            fArr[3] = fFloatValue2;
            fArr[4] = fFloatValue4;
            fArr[5] = fFloatValue6;
            fArr[6] = 0.0f;
            fArr[7] = 0.0f;
            fArr[8] = 1.0f;
            this.transform.setValues(fArr);
        }
        String str = obj.clipPath;
        if (str != null) {
            str = str.length() > 0 ? str : null;
            if (str != null) {
                this.maskPath = new SVGAPathEntity(str);
            }
        }
        List<ShapeEntity> list = obj.shapes;
        Intrinsics.checkExpressionValueIsNotNull(list, "obj.shapes");
        List<ShapeEntity> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (ShapeEntity it : list2) {
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            arrayList.add(new SVGAVideoShapeEntity(it));
        }
        this.shapes = arrayList;
    }
}
