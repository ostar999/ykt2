package com.opensource.svgaplayer.entities;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import com.alipay.sdk.sys.a;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.opensource.svgaplayer.BuildConfig;
import com.opensource.svgaplayer.proto.ShapeEntity;
import com.opensource.svgaplayer.proto.Transform;
import com.plv.thirdpart.litepal.util.Const;
import com.umeng.analytics.pro.am;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0000\u0018\u00002\u00020\u0001:\u0002./B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\"\u001a\u00020#J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0002J\u0010\u0010$\u001a\u00020%2\u0006\u0010\u0002\u001a\u00020(H\u0002J\u0010\u0010)\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0002J\u0010\u0010)\u001a\u00020%2\u0006\u0010\u0002\u001a\u00020(H\u0002J\u0010\u0010*\u001a\u00020#2\u0006\u0010\u0002\u001a\u00020\u0005H\u0002J\u0010\u0010*\u001a\u00020#2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010+\u001a\u00020#2\u0006\u0010\u0002\u001a\u00020\u0005H\u0002J\u0010\u0010+\u001a\u00020#2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010,\u001a\u00020#2\u0006\u0010\u0002\u001a\u00020\u0005H\u0002J\u0010\u0010,\u001a\u00020#2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010-\u001a\u00020#2\u0006\u0010\u0002\u001a\u00020\u0005H\u0002J\u0010\u0010-\u001a\u00020#2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002R:\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0001\u0018\u00010\b2\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0001\u0018\u00010\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\"\u0010\u0017\u001a\u0004\u0018\u00010\u00162\b\u0010\u0007\u001a\u0004\u0018\u00010\u0016@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\"\u0010\u001b\u001a\u0004\u0018\u00010\u001a2\b\u0010\u0007\u001a\u0004\u0018\u00010\u001a@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u001e\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u0007\u001a\u00020\u001e@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b \u0010!¨\u00060"}, d2 = {"Lcom/opensource/svgaplayer/entities/SVGAVideoShapeEntity;", "", "obj", "Lorg/json/JSONObject;", "(Lorg/json/JSONObject;)V", "Lcom/opensource/svgaplayer/proto/ShapeEntity;", "(Lcom/opensource/svgaplayer/proto/ShapeEntity;)V", "<set-?>", "", "", AliyunLogKey.KEY_ARGS, "getArgs", "()Ljava/util/Map;", "isKeep", "", "()Z", "shapePath", "Landroid/graphics/Path;", "getShapePath", "()Landroid/graphics/Path;", "setShapePath", "(Landroid/graphics/Path;)V", "Lcom/opensource/svgaplayer/entities/SVGAVideoShapeEntity$Styles;", "styles", "getStyles", "()Lcom/opensource/svgaplayer/entities/SVGAVideoShapeEntity$Styles;", "Landroid/graphics/Matrix;", "transform", "getTransform", "()Landroid/graphics/Matrix;", "Lcom/opensource/svgaplayer/entities/SVGAVideoShapeEntity$Type;", "type", "getType", "()Lcom/opensource/svgaplayer/entities/SVGAVideoShapeEntity$Type;", "buildPath", "", "checkAlphaValueRange", "", "color", "Lcom/opensource/svgaplayer/proto/ShapeEntity$ShapeStyle$RGBAColor;", "Lorg/json/JSONArray;", "checkValueRange", "parseArgs", "parseStyles", "parseTransform", "parseType", "Styles", "Type", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGAVideoShapeEntity {

    @Nullable
    private Map<String, ? extends Object> args;

    @Nullable
    private Path shapePath;

    @Nullable
    private Styles styles;

    @Nullable
    private Matrix transform;

    @NotNull
    private Type type;

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0014\n\u0002\b\u000e\n\u0002\u0010\u0007\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR$\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR$\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u0010@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R$\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\r\"\u0004\b\u0018\u0010\u000fR$\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0007\"\u0004\b\u001b\u0010\tR$\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0007\"\u0004\b\u001e\u0010\tR$\u0010 \u001a\u00020\u001f2\u0006\u0010\u0003\u001a\u00020\u001f@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$¨\u0006%"}, d2 = {"Lcom/opensource/svgaplayer/entities/SVGAVideoShapeEntity$Styles;", "", "()V", "<set-?>", "", "fill", "getFill", "()I", "setFill$com_opensource_svgaplayer", "(I)V", "", "lineCap", "getLineCap", "()Ljava/lang/String;", "setLineCap$com_opensource_svgaplayer", "(Ljava/lang/String;)V", "", "lineDash", "getLineDash", "()[F", "setLineDash$com_opensource_svgaplayer", "([F)V", "lineJoin", "getLineJoin", "setLineJoin$com_opensource_svgaplayer", "miterLimit", "getMiterLimit", "setMiterLimit$com_opensource_svgaplayer", "stroke", "getStroke", "setStroke$com_opensource_svgaplayer", "", "strokeWidth", "getStrokeWidth", "()F", "setStrokeWidth$com_opensource_svgaplayer", "(F)V", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
    public static final class Styles {
        private int fill;
        private int miterLimit;
        private int stroke;
        private float strokeWidth;

        @NotNull
        private String lineCap = "butt";

        @NotNull
        private String lineJoin = "miter";

        @NotNull
        private float[] lineDash = new float[0];

        public final int getFill() {
            return this.fill;
        }

        @NotNull
        public final String getLineCap() {
            return this.lineCap;
        }

        @NotNull
        public final float[] getLineDash() {
            return this.lineDash;
        }

        @NotNull
        public final String getLineJoin() {
            return this.lineJoin;
        }

        public final int getMiterLimit() {
            return this.miterLimit;
        }

        public final int getStroke() {
            return this.stroke;
        }

        public final float getStrokeWidth() {
            return this.strokeWidth;
        }

        public final void setFill$com_opensource_svgaplayer(int i2) {
            this.fill = i2;
        }

        public final void setLineCap$com_opensource_svgaplayer(@NotNull String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            this.lineCap = str;
        }

        public final void setLineDash$com_opensource_svgaplayer(@NotNull float[] fArr) {
            Intrinsics.checkParameterIsNotNull(fArr, "<set-?>");
            this.lineDash = fArr;
        }

        public final void setLineJoin$com_opensource_svgaplayer(@NotNull String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            this.lineJoin = str;
        }

        public final void setMiterLimit$com_opensource_svgaplayer(int i2) {
            this.miterLimit = i2;
        }

        public final void setStroke$com_opensource_svgaplayer(int i2) {
            this.stroke = i2;
        }

        public final void setStrokeWidth$com_opensource_svgaplayer(float f2) {
            this.strokeWidth = f2;
        }
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lcom/opensource/svgaplayer/entities/SVGAVideoShapeEntity$Type;", "", "(Ljava/lang/String;I)V", "shape", "rect", "ellipse", Const.Config.CASES_KEEP, BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
    public enum Type {
        shape,
        rect,
        ellipse,
        keep
    }

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 15})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

        static {
            int[] iArr = new int[ShapeEntity.ShapeType.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[ShapeEntity.ShapeType.SHAPE.ordinal()] = 1;
            iArr[ShapeEntity.ShapeType.RECT.ordinal()] = 2;
            iArr[ShapeEntity.ShapeType.ELLIPSE.ordinal()] = 3;
            iArr[ShapeEntity.ShapeType.KEEP.ordinal()] = 4;
            int[] iArr2 = new int[ShapeEntity.ShapeStyle.LineCap.values().length];
            $EnumSwitchMapping$1 = iArr2;
            iArr2[ShapeEntity.ShapeStyle.LineCap.LineCap_BUTT.ordinal()] = 1;
            iArr2[ShapeEntity.ShapeStyle.LineCap.LineCap_ROUND.ordinal()] = 2;
            iArr2[ShapeEntity.ShapeStyle.LineCap.LineCap_SQUARE.ordinal()] = 3;
            int[] iArr3 = new int[ShapeEntity.ShapeStyle.LineJoin.values().length];
            $EnumSwitchMapping$2 = iArr3;
            iArr3[ShapeEntity.ShapeStyle.LineJoin.LineJoin_BEVEL.ordinal()] = 1;
            iArr3[ShapeEntity.ShapeStyle.LineJoin.LineJoin_MITER.ordinal()] = 2;
            iArr3[ShapeEntity.ShapeStyle.LineJoin.LineJoin_ROUND.ordinal()] = 3;
        }
    }

    public SVGAVideoShapeEntity(@NotNull JSONObject obj) throws JSONException {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        this.type = Type.shape;
        parseType(obj);
        parseArgs(obj);
        parseStyles(obj);
        parseTransform(obj);
    }

    private final float checkAlphaValueRange(JSONArray obj) {
        return obj.optDouble(3) <= ((double) 1) ? 255.0f : 1.0f;
    }

    private final float checkValueRange(JSONArray obj) {
        double d3 = 1;
        return (obj.optDouble(0) > d3 || obj.optDouble(1) > d3 || obj.optDouble(2) > d3) ? 1.0f : 255.0f;
    }

    private final void parseArgs(JSONObject obj) throws JSONException {
        HashMap map = new HashMap();
        JSONObject jSONObjectOptJSONObject = obj.optJSONObject(AliyunLogKey.KEY_ARGS);
        if (jSONObjectOptJSONObject != null) {
            Iterator<String> itKeys = jSONObjectOptJSONObject.keys();
            Intrinsics.checkExpressionValueIsNotNull(itKeys, "values.keys()");
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                Object obj2 = jSONObjectOptJSONObject.get(next);
                if (obj2 != null) {
                    map.put(next, obj2);
                }
            }
            this.args = map;
        }
    }

    private final void parseStyles(JSONObject obj) {
        JSONObject jSONObjectOptJSONObject = obj.optJSONObject("styles");
        if (jSONObjectOptJSONObject != null) {
            Styles styles = new Styles();
            JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("fill");
            if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() == 4) {
                double dCheckValueRange = checkValueRange(jSONArrayOptJSONArray);
                styles.setFill$com_opensource_svgaplayer(Color.argb((int) (jSONArrayOptJSONArray.optDouble(3) * checkAlphaValueRange(jSONArrayOptJSONArray)), (int) (jSONArrayOptJSONArray.optDouble(0) * dCheckValueRange), (int) (jSONArrayOptJSONArray.optDouble(1) * dCheckValueRange), (int) (jSONArrayOptJSONArray.optDouble(2) * dCheckValueRange)));
            }
            JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray("stroke");
            if (jSONArrayOptJSONArray2 != null && jSONArrayOptJSONArray2.length() == 4) {
                double dCheckValueRange2 = checkValueRange(jSONArrayOptJSONArray2);
                styles.setStroke$com_opensource_svgaplayer(Color.argb((int) (jSONArrayOptJSONArray2.optDouble(3) * checkAlphaValueRange(jSONArrayOptJSONArray2)), (int) (jSONArrayOptJSONArray2.optDouble(0) * dCheckValueRange2), (int) (jSONArrayOptJSONArray2.optDouble(1) * dCheckValueRange2), (int) (jSONArrayOptJSONArray2.optDouble(2) * dCheckValueRange2)));
            }
            styles.setStrokeWidth$com_opensource_svgaplayer((float) jSONObjectOptJSONObject.optDouble("strokeWidth", 0.0d));
            String strOptString = jSONObjectOptJSONObject.optString("lineCap", "butt");
            Intrinsics.checkExpressionValueIsNotNull(strOptString, "it.optString(\"lineCap\", \"butt\")");
            styles.setLineCap$com_opensource_svgaplayer(strOptString);
            String strOptString2 = jSONObjectOptJSONObject.optString("lineJoin", "miter");
            Intrinsics.checkExpressionValueIsNotNull(strOptString2, "it.optString(\"lineJoin\", \"miter\")");
            styles.setLineJoin$com_opensource_svgaplayer(strOptString2);
            styles.setMiterLimit$com_opensource_svgaplayer(jSONObjectOptJSONObject.optInt("miterLimit", 0));
            JSONArray jSONArrayOptJSONArray3 = jSONObjectOptJSONObject.optJSONArray("lineDash");
            if (jSONArrayOptJSONArray3 != null) {
                styles.setLineDash$com_opensource_svgaplayer(new float[jSONArrayOptJSONArray3.length()]);
                int length = jSONArrayOptJSONArray3.length();
                for (int i2 = 0; i2 < length; i2++) {
                    styles.getLineDash()[i2] = (float) jSONArrayOptJSONArray3.optDouble(i2, 0.0d);
                }
            }
            this.styles = styles;
        }
    }

    private final void parseTransform(JSONObject obj) {
        JSONObject jSONObjectOptJSONObject = obj.optJSONObject("transform");
        if (jSONObjectOptJSONObject != null) {
            Matrix matrix = new Matrix();
            double dOptDouble = jSONObjectOptJSONObject.optDouble(am.av, 1.0d);
            double dOptDouble2 = jSONObjectOptJSONObject.optDouble("b", 0.0d);
            double dOptDouble3 = jSONObjectOptJSONObject.optDouble(am.aF, 0.0d);
            double dOptDouble4 = jSONObjectOptJSONObject.optDouble("d", 1.0d);
            double dOptDouble5 = jSONObjectOptJSONObject.optDouble("tx", 0.0d);
            double dOptDouble6 = jSONObjectOptJSONObject.optDouble(a.f3322g, 0.0d);
            float f2 = (float) 0.0d;
            matrix.setValues(new float[]{(float) dOptDouble, (float) dOptDouble3, (float) dOptDouble5, (float) dOptDouble2, (float) dOptDouble4, (float) dOptDouble6, f2, f2, (float) 1.0d});
            this.transform = matrix;
        }
    }

    private final void parseType(JSONObject obj) {
        String strOptString = obj.optString("type");
        if (strOptString != null) {
            if (StringsKt__StringsJVMKt.equals(strOptString, "shape", true)) {
                this.type = Type.shape;
                return;
            }
            if (StringsKt__StringsJVMKt.equals(strOptString, "rect", true)) {
                this.type = Type.rect;
            } else if (StringsKt__StringsJVMKt.equals(strOptString, "ellipse", true)) {
                this.type = Type.ellipse;
            } else if (StringsKt__StringsJVMKt.equals(strOptString, Const.Config.CASES_KEEP, true)) {
                this.type = Type.keep;
            }
        }
    }

    public final void buildPath() throws NumberFormatException {
        if (this.shapePath != null) {
            return;
        }
        SVGAVideoShapeEntityKt.getSharedPath().reset();
        Type type = this.type;
        if (type == Type.shape) {
            Map<String, ? extends Object> map = this.args;
            Object obj = map != null ? map.get("d") : null;
            String str = (String) (obj instanceof String ? obj : null);
            if (str != null) {
                new SVGAPathEntity(str).buildPath(SVGAVideoShapeEntityKt.getSharedPath());
            }
        } else if (type == Type.ellipse) {
            Map<String, ? extends Object> map2 = this.args;
            Object obj2 = map2 != null ? map2.get("x") : null;
            if (!(obj2 instanceof Number)) {
                obj2 = null;
            }
            Number number = (Number) obj2;
            if (number == null) {
                return;
            }
            Map<String, ? extends Object> map3 = this.args;
            Object obj3 = map3 != null ? map3.get("y") : null;
            if (!(obj3 instanceof Number)) {
                obj3 = null;
            }
            Number number2 = (Number) obj3;
            if (number2 == null) {
                return;
            }
            Map<String, ? extends Object> map4 = this.args;
            Object obj4 = map4 != null ? map4.get("radiusX") : null;
            if (!(obj4 instanceof Number)) {
                obj4 = null;
            }
            Number number3 = (Number) obj4;
            if (number3 == null) {
                return;
            }
            Map<String, ? extends Object> map5 = this.args;
            Object obj5 = map5 != null ? map5.get("radiusY") : null;
            Number number4 = (Number) (obj5 instanceof Number ? obj5 : null);
            if (number4 == null) {
                return;
            }
            float fFloatValue = number.floatValue();
            float fFloatValue2 = number2.floatValue();
            float fFloatValue3 = number3.floatValue();
            float fFloatValue4 = number4.floatValue();
            SVGAVideoShapeEntityKt.getSharedPath().addOval(new RectF(fFloatValue - fFloatValue3, fFloatValue2 - fFloatValue4, fFloatValue + fFloatValue3, fFloatValue2 + fFloatValue4), Path.Direction.CW);
        } else if (type == Type.rect) {
            Map<String, ? extends Object> map6 = this.args;
            Object obj6 = map6 != null ? map6.get("x") : null;
            if (!(obj6 instanceof Number)) {
                obj6 = null;
            }
            Number number5 = (Number) obj6;
            if (number5 == null) {
                return;
            }
            Map<String, ? extends Object> map7 = this.args;
            Object obj7 = map7 != null ? map7.get("y") : null;
            if (!(obj7 instanceof Number)) {
                obj7 = null;
            }
            Number number6 = (Number) obj7;
            if (number6 == null) {
                return;
            }
            Map<String, ? extends Object> map8 = this.args;
            Object obj8 = map8 != null ? map8.get("width") : null;
            if (!(obj8 instanceof Number)) {
                obj8 = null;
            }
            Number number7 = (Number) obj8;
            if (number7 == null) {
                return;
            }
            Map<String, ? extends Object> map9 = this.args;
            Object obj9 = map9 != null ? map9.get("height") : null;
            if (!(obj9 instanceof Number)) {
                obj9 = null;
            }
            Number number8 = (Number) obj9;
            if (number8 == null) {
                return;
            }
            Map<String, ? extends Object> map10 = this.args;
            Object obj10 = map10 != null ? map10.get("cornerRadius") : null;
            Number number9 = (Number) (obj10 instanceof Number ? obj10 : null);
            if (number9 == null) {
                return;
            }
            float fFloatValue5 = number5.floatValue();
            float fFloatValue6 = number6.floatValue();
            float fFloatValue7 = number7.floatValue();
            float fFloatValue8 = number8.floatValue();
            float fFloatValue9 = number9.floatValue();
            SVGAVideoShapeEntityKt.getSharedPath().addRoundRect(new RectF(fFloatValue5, fFloatValue6, fFloatValue7 + fFloatValue5, fFloatValue8 + fFloatValue6), fFloatValue9, fFloatValue9, Path.Direction.CW);
        }
        Path path = new Path();
        this.shapePath = path;
        path.set(SVGAVideoShapeEntityKt.getSharedPath());
    }

    @Nullable
    public final Map<String, Object> getArgs() {
        return this.args;
    }

    @Nullable
    public final Path getShapePath() {
        return this.shapePath;
    }

    @Nullable
    public final Styles getStyles() {
        return this.styles;
    }

    @Nullable
    public final Matrix getTransform() {
        return this.transform;
    }

    @NotNull
    public final Type getType() {
        return this.type;
    }

    public final boolean isKeep() {
        return this.type == Type.keep;
    }

    public final void setShapePath(@Nullable Path path) {
        this.shapePath = path;
    }

    private final float checkAlphaValueRange(ShapeEntity.ShapeStyle.RGBAColor color) {
        return color.f10701a.floatValue() <= 1.0f ? 255.0f : 1.0f;
    }

    private final float checkValueRange(ShapeEntity.ShapeStyle.RGBAColor color) {
        Float f2 = color.f10704r;
        float f3 = 1;
        if ((f2 != null ? f2.floatValue() : 0.0f) <= f3) {
            Float f4 = color.f10703g;
            if ((f4 != null ? f4.floatValue() : 0.0f) <= f3) {
                Float f5 = color.f10702b;
                if ((f5 != null ? f5.floatValue() : 0.0f) <= f3) {
                    return 255.0f;
                }
            }
        }
        return 1.0f;
    }

    private final void parseType(ShapeEntity obj) {
        Type type;
        ShapeEntity.ShapeType shapeType = obj.type;
        if (shapeType != null) {
            int i2 = WhenMappings.$EnumSwitchMapping$0[shapeType.ordinal()];
            if (i2 == 1) {
                type = Type.shape;
            } else if (i2 == 2) {
                type = Type.rect;
            } else if (i2 == 3) {
                type = Type.ellipse;
            } else {
                if (i2 != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                type = Type.keep;
            }
            this.type = type;
        }
    }

    public SVGAVideoShapeEntity(@NotNull ShapeEntity obj) {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        this.type = Type.shape;
        parseType(obj);
        parseArgs(obj);
        parseStyles(obj);
        parseTransform(obj);
    }

    private final void parseArgs(ShapeEntity obj) {
        String str;
        HashMap map = new HashMap();
        ShapeEntity.ShapeArgs shapeArgs = obj.shape;
        if (shapeArgs != null && (str = shapeArgs.f10699d) != null) {
            map.put("d", str);
        }
        ShapeEntity.EllipseArgs ellipseArgs = obj.ellipse;
        if (ellipseArgs != null) {
            Float fValueOf = ellipseArgs.f10691x;
            if (fValueOf == null) {
                fValueOf = Float.valueOf(0.0f);
            }
            map.put("x", fValueOf);
            Float fValueOf2 = ellipseArgs.f10692y;
            if (fValueOf2 == null) {
                fValueOf2 = Float.valueOf(0.0f);
            }
            map.put("y", fValueOf2);
            Float fValueOf3 = ellipseArgs.radiusX;
            if (fValueOf3 == null) {
                fValueOf3 = Float.valueOf(0.0f);
            }
            map.put("radiusX", fValueOf3);
            Float fValueOf4 = ellipseArgs.radiusY;
            if (fValueOf4 == null) {
                fValueOf4 = Float.valueOf(0.0f);
            }
            map.put("radiusY", fValueOf4);
        }
        ShapeEntity.RectArgs rectArgs = obj.rect;
        if (rectArgs != null) {
            Float fValueOf5 = rectArgs.f10695x;
            if (fValueOf5 == null) {
                fValueOf5 = Float.valueOf(0.0f);
            }
            map.put("x", fValueOf5);
            Float fValueOf6 = rectArgs.f10696y;
            if (fValueOf6 == null) {
                fValueOf6 = Float.valueOf(0.0f);
            }
            map.put("y", fValueOf6);
            Float fValueOf7 = rectArgs.width;
            if (fValueOf7 == null) {
                fValueOf7 = Float.valueOf(0.0f);
            }
            map.put("width", fValueOf7);
            Float fValueOf8 = rectArgs.height;
            if (fValueOf8 == null) {
                fValueOf8 = Float.valueOf(0.0f);
            }
            map.put("height", fValueOf8);
            Float fValueOf9 = rectArgs.cornerRadius;
            if (fValueOf9 == null) {
                fValueOf9 = Float.valueOf(0.0f);
            }
            map.put("cornerRadius", fValueOf9);
        }
        this.args = map;
    }

    private final void parseTransform(ShapeEntity obj) {
        Transform transform = obj.transform;
        if (transform != null) {
            Matrix matrix = new Matrix();
            float[] fArr = new float[9];
            Float f2 = transform.f10709a;
            float fFloatValue = f2 != null ? f2.floatValue() : 1.0f;
            Float f3 = transform.f10710b;
            float fFloatValue2 = f3 != null ? f3.floatValue() : 0.0f;
            Float f4 = transform.f10711c;
            float fFloatValue3 = f4 != null ? f4.floatValue() : 0.0f;
            Float f5 = transform.f10712d;
            float fFloatValue4 = f5 != null ? f5.floatValue() : 1.0f;
            Float f6 = transform.tx;
            float fFloatValue5 = f6 != null ? f6.floatValue() : 0.0f;
            Float f7 = transform.ty;
            float fFloatValue6 = f7 != null ? f7.floatValue() : 0.0f;
            fArr[0] = fFloatValue;
            fArr[1] = fFloatValue3;
            fArr[2] = fFloatValue5;
            fArr[3] = fFloatValue2;
            fArr[4] = fFloatValue4;
            fArr[5] = fFloatValue6;
            fArr[6] = 0.0f;
            fArr[7] = 0.0f;
            fArr[8] = 1.0f;
            matrix.setValues(fArr);
            this.transform = matrix;
        }
    }

    private final void parseStyles(ShapeEntity obj) {
        ShapeEntity.ShapeStyle shapeStyle = obj.styles;
        if (shapeStyle != null) {
            Styles styles = new Styles();
            ShapeEntity.ShapeStyle.RGBAColor rGBAColor = shapeStyle.fill;
            if (rGBAColor != null) {
                float fCheckValueRange = checkValueRange(rGBAColor);
                float fCheckAlphaValueRange = checkAlphaValueRange(rGBAColor);
                Float f2 = rGBAColor.f10701a;
                int iFloatValue = (int) ((f2 != null ? f2.floatValue() : 0.0f) * fCheckAlphaValueRange);
                Float f3 = rGBAColor.f10704r;
                int iFloatValue2 = (int) ((f3 != null ? f3.floatValue() : 0.0f) * fCheckValueRange);
                Float f4 = rGBAColor.f10703g;
                int iFloatValue3 = (int) ((f4 != null ? f4.floatValue() : 0.0f) * fCheckValueRange);
                Float f5 = rGBAColor.f10702b;
                styles.setFill$com_opensource_svgaplayer(Color.argb(iFloatValue, iFloatValue2, iFloatValue3, (int) ((f5 != null ? f5.floatValue() : 0.0f) * fCheckValueRange)));
            }
            ShapeEntity.ShapeStyle.RGBAColor rGBAColor2 = shapeStyle.stroke;
            if (rGBAColor2 != null) {
                float fCheckValueRange2 = checkValueRange(rGBAColor2);
                float fCheckAlphaValueRange2 = checkAlphaValueRange(rGBAColor2);
                Float f6 = rGBAColor2.f10701a;
                int iFloatValue4 = (int) ((f6 != null ? f6.floatValue() : 0.0f) * fCheckAlphaValueRange2);
                Float f7 = rGBAColor2.f10704r;
                int iFloatValue5 = (int) ((f7 != null ? f7.floatValue() : 0.0f) * fCheckValueRange2);
                Float f8 = rGBAColor2.f10703g;
                int iFloatValue6 = (int) ((f8 != null ? f8.floatValue() : 0.0f) * fCheckValueRange2);
                Float f9 = rGBAColor2.f10702b;
                styles.setStroke$com_opensource_svgaplayer(Color.argb(iFloatValue4, iFloatValue5, iFloatValue6, (int) ((f9 != null ? f9.floatValue() : 0.0f) * fCheckValueRange2)));
            }
            Float f10 = shapeStyle.strokeWidth;
            styles.setStrokeWidth$com_opensource_svgaplayer(f10 != null ? f10.floatValue() : 0.0f);
            ShapeEntity.ShapeStyle.LineCap lineCap = shapeStyle.lineCap;
            if (lineCap != null) {
                int i2 = WhenMappings.$EnumSwitchMapping$1[lineCap.ordinal()];
                if (i2 == 1) {
                    styles.setLineCap$com_opensource_svgaplayer("butt");
                } else if (i2 == 2) {
                    styles.setLineCap$com_opensource_svgaplayer("round");
                } else if (i2 == 3) {
                    styles.setLineCap$com_opensource_svgaplayer("square");
                }
            }
            ShapeEntity.ShapeStyle.LineJoin lineJoin = shapeStyle.lineJoin;
            if (lineJoin != null) {
                int i3 = WhenMappings.$EnumSwitchMapping$2[lineJoin.ordinal()];
                if (i3 == 1) {
                    styles.setLineJoin$com_opensource_svgaplayer("bevel");
                } else if (i3 == 2) {
                    styles.setLineJoin$com_opensource_svgaplayer("miter");
                } else if (i3 == 3) {
                    styles.setLineJoin$com_opensource_svgaplayer("round");
                }
            }
            Float f11 = shapeStyle.miterLimit;
            styles.setMiterLimit$com_opensource_svgaplayer((int) (f11 != null ? f11.floatValue() : 0.0f));
            styles.setLineDash$com_opensource_svgaplayer(new float[3]);
            Float f12 = shapeStyle.lineDashI;
            if (f12 != null) {
                styles.getLineDash()[0] = f12.floatValue();
            }
            Float f13 = shapeStyle.lineDashII;
            if (f13 != null) {
                styles.getLineDash()[1] = f13.floatValue();
            }
            Float f14 = shapeStyle.lineDashIII;
            if (f14 != null) {
                styles.getLineDash()[2] = f14.floatValue();
            }
            this.styles = styles;
        }
    }
}
