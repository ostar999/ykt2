package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public final class GeoShapeBuilder extends IndexableBuilder<GeoShapeBuilder> {
    public GeoShapeBuilder() {
        super("GeoShape");
    }

    @Deprecated
    public final GeoShapeBuilder setBox(@NonNull String str) {
        return put("box", str);
    }

    public final GeoShapeBuilder setBox(@NonNull String... strArr) {
        return put("box", strArr);
    }
}
