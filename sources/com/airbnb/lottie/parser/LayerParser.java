package com.airbnb.lottie.parser;

import android.graphics.Color;
import android.graphics.Rect;
import c.c;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import com.alipay.sdk.sys.a;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.umeng.analytics.pro.am;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes2.dex */
public class LayerParser {
    private static final JsonReader.Options NAMES = JsonReader.Options.of("nm", "ind", "refId", a.f3322g, "parent", "sw", c.f2218l, "sc", "ks", "tt", "masksProperties", "shapes", "t", "ef", "sr", "st", "w", "h", "ip", AliyunLogKey.KEY_OUTPUT_PATH, "tm", "cl", "hd");
    private static final JsonReader.Options TEXT_NAMES = JsonReader.Options.of("d", am.av);
    private static final JsonReader.Options EFFECTS_NAMES = JsonReader.Options.of("nm");

    /* renamed from: com.airbnb.lottie.parser.LayerParser$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType;

        static {
            int[] iArr = new int[Layer.MatteType.values().length];
            $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType = iArr;
            try {
                iArr[Layer.MatteType.LUMA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType[Layer.MatteType.LUMA_INVERTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private LayerParser() {
    }

    public static Layer parse(LottieComposition lottieComposition) {
        Rect bounds = lottieComposition.getBounds();
        return new Layer(Collections.emptyList(), lottieComposition, "__container", -1L, Layer.LayerType.PRE_COMP, -1L, null, Collections.emptyList(), new AnimatableTransform(), 0, 0, 0, 0.0f, 0.0f, bounds.width(), bounds.height(), null, null, Collections.emptyList(), Layer.MatteType.NONE, null, false);
    }

    public static Layer parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        ArrayList arrayList;
        ArrayList arrayList2;
        float f2;
        Layer.MatteType matteType = Layer.MatteType.NONE;
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        jsonReader.beginObject();
        Float fValueOf = Float.valueOf(1.0f);
        Float fValueOf2 = Float.valueOf(0.0f);
        Layer.MatteType matteType2 = matteType;
        String strNextString = null;
        AnimatableTransform animatableTransform = null;
        AnimatableTextFrame documentData = null;
        AnimatableTextProperties animatableTextProperties = null;
        AnimatableFloatValue animatableFloatValue = null;
        long jNextInt = 0;
        int iNextInt = 0;
        int iNextInt2 = 0;
        int color = 0;
        int iNextInt3 = 0;
        int iNextInt4 = 0;
        boolean zNextBoolean = false;
        float fNextDouble = 1.0f;
        long jNextInt2 = -1;
        float fNextDouble2 = 0.0f;
        float fNextDouble3 = 0.0f;
        String strNextString2 = "UNSET";
        String strNextString3 = null;
        Layer.LayerType layerType = null;
        float fNextDouble4 = 0.0f;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    strNextString2 = jsonReader.nextString();
                    break;
                case 1:
                    jNextInt = jsonReader.nextInt();
                    break;
                case 2:
                    strNextString = jsonReader.nextString();
                    break;
                case 3:
                    int iNextInt5 = jsonReader.nextInt();
                    layerType = Layer.LayerType.UNKNOWN;
                    if (iNextInt5 >= layerType.ordinal()) {
                        break;
                    } else {
                        layerType = Layer.LayerType.values()[iNextInt5];
                        break;
                    }
                case 4:
                    jNextInt2 = jsonReader.nextInt();
                    break;
                case 5:
                    iNextInt = (int) (jsonReader.nextInt() * Utils.dpScale());
                    break;
                case 6:
                    iNextInt2 = (int) (jsonReader.nextInt() * Utils.dpScale());
                    break;
                case 7:
                    color = Color.parseColor(jsonReader.nextString());
                    break;
                case 8:
                    animatableTransform = AnimatableTransformParser.parse(jsonReader, lottieComposition);
                    break;
                case 9:
                    int iNextInt6 = jsonReader.nextInt();
                    if (iNextInt6 >= Layer.MatteType.values().length) {
                        lottieComposition.addWarning("Unsupported matte type: " + iNextInt6);
                        break;
                    } else {
                        matteType2 = Layer.MatteType.values()[iNextInt6];
                        int i2 = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType[matteType2.ordinal()];
                        if (i2 == 1) {
                            lottieComposition.addWarning("Unsupported matte type: Luma");
                        } else if (i2 == 2) {
                            lottieComposition.addWarning("Unsupported matte type: Luma Inverted");
                        }
                        lottieComposition.incrementMatteOrMaskCount(1);
                        break;
                    }
                case 10:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        arrayList3.add(MaskParser.parse(jsonReader, lottieComposition));
                    }
                    lottieComposition.incrementMatteOrMaskCount(arrayList3.size());
                    jsonReader.endArray();
                    break;
                case 11:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        ContentModel contentModel = ContentModelParser.parse(jsonReader, lottieComposition);
                        if (contentModel != null) {
                            arrayList4.add(contentModel);
                        }
                    }
                    jsonReader.endArray();
                    break;
                case 12:
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        int iSelectName = jsonReader.selectName(TEXT_NAMES);
                        if (iSelectName == 0) {
                            documentData = AnimatableValueParser.parseDocumentData(jsonReader, lottieComposition);
                        } else if (iSelectName != 1) {
                            jsonReader.skipName();
                            jsonReader.skipValue();
                        } else {
                            jsonReader.beginArray();
                            if (jsonReader.hasNext()) {
                                animatableTextProperties = AnimatableTextPropertiesParser.parse(jsonReader, lottieComposition);
                            }
                            while (jsonReader.hasNext()) {
                                jsonReader.skipValue();
                            }
                            jsonReader.endArray();
                        }
                    }
                    jsonReader.endObject();
                    break;
                case 13:
                    jsonReader.beginArray();
                    ArrayList arrayList5 = new ArrayList();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            if (jsonReader.selectName(EFFECTS_NAMES) != 0) {
                                jsonReader.skipName();
                                jsonReader.skipValue();
                            } else {
                                arrayList5.add(jsonReader.nextString());
                            }
                        }
                        jsonReader.endObject();
                    }
                    jsonReader.endArray();
                    lottieComposition.addWarning("Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: " + arrayList5);
                    break;
                case 14:
                    fNextDouble = (float) jsonReader.nextDouble();
                    break;
                case 15:
                    fNextDouble3 = (float) jsonReader.nextDouble();
                    break;
                case 16:
                    iNextInt3 = (int) (jsonReader.nextInt() * Utils.dpScale());
                    break;
                case 17:
                    iNextInt4 = (int) (jsonReader.nextInt() * Utils.dpScale());
                    break;
                case 18:
                    fNextDouble2 = (float) jsonReader.nextDouble();
                    break;
                case 19:
                    fNextDouble4 = (float) jsonReader.nextDouble();
                    break;
                case 20:
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
                    break;
                case 21:
                    strNextString3 = jsonReader.nextString();
                    break;
                case 22:
                    zNextBoolean = jsonReader.nextBoolean();
                    break;
                default:
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        float f3 = fNextDouble2 / fNextDouble;
        float endFrame = fNextDouble4 / fNextDouble;
        ArrayList arrayList6 = new ArrayList();
        if (f3 > 0.0f) {
            arrayList = arrayList3;
            arrayList2 = arrayList6;
            arrayList2.add(new Keyframe(lottieComposition, fValueOf2, fValueOf2, null, 0.0f, Float.valueOf(f3)));
            f2 = 0.0f;
        } else {
            arrayList = arrayList3;
            arrayList2 = arrayList6;
            f2 = 0.0f;
        }
        if (endFrame <= f2) {
            endFrame = lottieComposition.getEndFrame();
        }
        arrayList2.add(new Keyframe(lottieComposition, fValueOf, fValueOf, null, f3, Float.valueOf(endFrame)));
        arrayList2.add(new Keyframe(lottieComposition, fValueOf2, fValueOf2, null, endFrame, Float.valueOf(Float.MAX_VALUE)));
        if (strNextString2.endsWith(".ai") || "ai".equals(strNextString3)) {
            lottieComposition.addWarning("Convert your Illustrator layers to shape layers.");
        }
        return new Layer(arrayList4, lottieComposition, strNextString2, jNextInt, layerType, jNextInt2, strNextString, arrayList, animatableTransform, iNextInt, iNextInt2, color, fNextDouble, fNextDouble3, iNextInt3, iNextInt4, documentData, animatableTextProperties, arrayList2, matteType2, animatableFloatValue, zNextBoolean);
    }
}
