package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.umeng.analytics.pro.am;
import java.io.IOException;

/* loaded from: classes2.dex */
public class AnimatableTextPropertiesParser {
    private static JsonReader.Options PROPERTIES_NAMES = JsonReader.Options.of(am.av);
    private static JsonReader.Options ANIMATABLE_PROPERTIES_NAMES = JsonReader.Options.of(AliyunLogKey.KEY_FILL_COLOR, "sc", "sw", "t");

    private AnimatableTextPropertiesParser() {
    }

    public static AnimatableTextProperties parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        jsonReader.beginObject();
        AnimatableTextProperties animatableTextProperties = null;
        while (jsonReader.hasNext()) {
            if (jsonReader.selectName(PROPERTIES_NAMES) != 0) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                animatableTextProperties = parseAnimatableTextProperties(jsonReader, lottieComposition);
            }
        }
        jsonReader.endObject();
        return animatableTextProperties == null ? new AnimatableTextProperties(null, null, null, null) : animatableTextProperties;
    }

    private static AnimatableTextProperties parseAnimatableTextProperties(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        jsonReader.beginObject();
        AnimatableColorValue color = null;
        AnimatableColorValue color2 = null;
        AnimatableFloatValue animatableFloatValue = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        while (jsonReader.hasNext()) {
            int iSelectName = jsonReader.selectName(ANIMATABLE_PROPERTIES_NAMES);
            if (iSelectName == 0) {
                color = AnimatableValueParser.parseColor(jsonReader, lottieComposition);
            } else if (iSelectName == 1) {
                color2 = AnimatableValueParser.parseColor(jsonReader, lottieComposition);
            } else if (iSelectName == 2) {
                animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition);
            } else if (iSelectName != 3) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                animatableFloatValue2 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition);
            }
        }
        jsonReader.endObject();
        return new AnimatableTextProperties(color, color2, animatableFloatValue, animatableFloatValue2);
    }
}
