package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public final class PlaceBuilder extends IndexableBuilder<PlaceBuilder> {
    public PlaceBuilder() {
        super("Place");
    }

    public final PlaceBuilder setGeo(@NonNull GeoShapeBuilder geoShapeBuilder) {
        return put("geo", geoShapeBuilder);
    }
}
