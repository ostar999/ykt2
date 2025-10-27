package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableGradientColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.content.GradientStroke;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.content.ShapeStroke;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.value.Keyframe;
import com.aliyun.vod.log.struct.AliyunLogKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes2.dex */
class GradientStrokeParser {
    private static JsonReader.Options NAMES = JsonReader.Options.of("nm", "g", "o", "t", "s", AliyunLogKey.KEY_EVENT, "w", "lc", "lj", "ml", "hd", "d");
    private static final JsonReader.Options GRADIENT_NAMES = JsonReader.Options.of("p", "k");
    private static final JsonReader.Options DASH_PATTERN_NAMES = JsonReader.Options.of("n", "v");

    private GradientStrokeParser() {
    }

    public static GradientStroke parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        String str;
        AnimatableGradientColorValue animatableGradientColorValue;
        ArrayList arrayList = new ArrayList();
        float fNextDouble = 0.0f;
        String strNextString = null;
        GradientType gradientType = null;
        AnimatableGradientColorValue gradientColor = null;
        AnimatablePointValue point = null;
        AnimatablePointValue point2 = null;
        AnimatableFloatValue animatableFloatValue = null;
        ShapeStroke.LineCapType lineCapType = null;
        ShapeStroke.LineJoinType lineJoinType = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        boolean zNextBoolean = false;
        AnimatableIntegerValue animatableIntegerValue = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    strNextString = jsonReader.nextString();
                    continue;
                case 1:
                    str = strNextString;
                    jsonReader.beginObject();
                    int iNextInt = -1;
                    while (jsonReader.hasNext()) {
                        int iSelectName = jsonReader.selectName(GRADIENT_NAMES);
                        if (iSelectName != 0) {
                            animatableGradientColorValue = gradientColor;
                            if (iSelectName != 1) {
                                jsonReader.skipName();
                                jsonReader.skipValue();
                            } else {
                                gradientColor = AnimatableValueParser.parseGradientColor(jsonReader, lottieComposition, iNextInt);
                            }
                        } else {
                            animatableGradientColorValue = gradientColor;
                            iNextInt = jsonReader.nextInt();
                        }
                        gradientColor = animatableGradientColorValue;
                    }
                    jsonReader.endObject();
                    break;
                case 2:
                    animatableIntegerValue = AnimatableValueParser.parseInteger(jsonReader, lottieComposition);
                    continue;
                case 3:
                    str = strNextString;
                    gradientType = jsonReader.nextInt() == 1 ? GradientType.LINEAR : GradientType.RADIAL;
                    break;
                case 4:
                    point = AnimatableValueParser.parsePoint(jsonReader, lottieComposition);
                    continue;
                case 5:
                    point2 = AnimatableValueParser.parsePoint(jsonReader, lottieComposition);
                    continue;
                case 6:
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition);
                    continue;
                case 7:
                    str = strNextString;
                    lineCapType = ShapeStroke.LineCapType.values()[jsonReader.nextInt() - 1];
                    break;
                case 8:
                    str = strNextString;
                    lineJoinType = ShapeStroke.LineJoinType.values()[jsonReader.nextInt() - 1];
                    break;
                case 9:
                    str = strNextString;
                    fNextDouble = (float) jsonReader.nextDouble();
                    break;
                case 10:
                    zNextBoolean = jsonReader.nextBoolean();
                    continue;
                case 11:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        String strNextString2 = null;
                        AnimatableFloatValue animatableFloatValue3 = null;
                        while (jsonReader.hasNext()) {
                            int iSelectName2 = jsonReader.selectName(DASH_PATTERN_NAMES);
                            if (iSelectName2 != 0) {
                                AnimatableFloatValue animatableFloatValue4 = animatableFloatValue2;
                                if (iSelectName2 != 1) {
                                    jsonReader.skipName();
                                    jsonReader.skipValue();
                                } else {
                                    animatableFloatValue3 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition);
                                }
                                animatableFloatValue2 = animatableFloatValue4;
                            } else {
                                strNextString2 = jsonReader.nextString();
                            }
                        }
                        AnimatableFloatValue animatableFloatValue5 = animatableFloatValue2;
                        jsonReader.endObject();
                        if (strNextString2.equals("o")) {
                            animatableFloatValue2 = animatableFloatValue3;
                        } else {
                            if (strNextString2.equals("d") || strNextString2.equals("g")) {
                                lottieComposition.setHasDashPattern(true);
                                arrayList.add(animatableFloatValue3);
                            }
                            animatableFloatValue2 = animatableFloatValue5;
                        }
                    }
                    AnimatableFloatValue animatableFloatValue6 = animatableFloatValue2;
                    jsonReader.endArray();
                    if (arrayList.size() == 1) {
                        arrayList.add(arrayList.get(0));
                    }
                    animatableFloatValue2 = animatableFloatValue6;
                    continue;
                    break;
                default:
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    continue;
            }
            strNextString = str;
        }
        String str2 = strNextString;
        if (animatableIntegerValue == null) {
            animatableIntegerValue = new AnimatableIntegerValue(Collections.singletonList(new Keyframe(100)));
        }
        return new GradientStroke(str2, gradientType, gradientColor, animatableIntegerValue, point, point2, animatableFloatValue, lineCapType, lineJoinType, fNextDouble, arrayList, animatableFloatValue2, zNextBoolean);
    }
}
