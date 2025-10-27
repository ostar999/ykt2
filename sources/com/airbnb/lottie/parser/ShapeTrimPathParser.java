package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.aliyun.vod.log.struct.AliyunLogKey;
import java.io.IOException;

/* loaded from: classes2.dex */
class ShapeTrimPathParser {
    private static JsonReader.Options NAMES = JsonReader.Options.of("s", AliyunLogKey.KEY_EVENT, "o", "nm", "m", "hd");

    private ShapeTrimPathParser() {
    }

    public static ShapeTrimPath parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        String strNextString = null;
        ShapeTrimPath.Type typeForId = null;
        AnimatableFloatValue animatableFloatValue = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        AnimatableFloatValue animatableFloatValue3 = null;
        boolean zNextBoolean = false;
        while (jsonReader.hasNext()) {
            int iSelectName = jsonReader.selectName(NAMES);
            if (iSelectName == 0) {
                animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
            } else if (iSelectName == 1) {
                animatableFloatValue2 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
            } else if (iSelectName == 2) {
                animatableFloatValue3 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
            } else if (iSelectName == 3) {
                strNextString = jsonReader.nextString();
            } else if (iSelectName == 4) {
                typeForId = ShapeTrimPath.Type.forId(jsonReader.nextInt());
            } else if (iSelectName != 5) {
                jsonReader.skipValue();
            } else {
                zNextBoolean = jsonReader.nextBoolean();
            }
        }
        return new ShapeTrimPath(strNextString, typeForId, animatableFloatValue, animatableFloatValue2, animatableFloatValue3, zNextBoolean);
    }
}
