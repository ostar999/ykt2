package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes2.dex */
class ShapeGroupParser {
    private static JsonReader.Options NAMES = JsonReader.Options.of("nm", "hd", AdvanceSetting.NETWORK_TYPE);

    private ShapeGroupParser() {
    }

    public static ShapeGroup parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        ArrayList arrayList = new ArrayList();
        String strNextString = null;
        boolean zNextBoolean = false;
        while (jsonReader.hasNext()) {
            int iSelectName = jsonReader.selectName(NAMES);
            if (iSelectName == 0) {
                strNextString = jsonReader.nextString();
            } else if (iSelectName == 1) {
                zNextBoolean = jsonReader.nextBoolean();
            } else if (iSelectName != 2) {
                jsonReader.skipValue();
            } else {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    ContentModel contentModel = ContentModelParser.parse(jsonReader, lottieComposition);
                    if (contentModel != null) {
                        arrayList.add(contentModel);
                    }
                }
                jsonReader.endArray();
            }
        }
        return new ShapeGroup(strNextString, arrayList, zNextBoolean);
    }
}
