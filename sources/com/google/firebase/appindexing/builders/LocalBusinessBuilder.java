package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public final class LocalBusinessBuilder extends IndexableBuilder<LocalBusinessBuilder> {
    public LocalBusinessBuilder() {
        super("LocalBusiness");
    }

    public final LocalBusinessBuilder setAddress(@NonNull PostalAddressBuilder postalAddressBuilder) {
        return put("address", postalAddressBuilder);
    }

    public final LocalBusinessBuilder setAggregateRating(@NonNull AggregateRatingBuilder aggregateRatingBuilder) {
        return put("aggregateRating", aggregateRatingBuilder);
    }

    public final LocalBusinessBuilder setGeo(@NonNull GeoShapeBuilder geoShapeBuilder) {
        return put("geo", geoShapeBuilder);
    }

    public final LocalBusinessBuilder setPriceRange(@NonNull String str) {
        return put("priceRange", str);
    }

    public final LocalBusinessBuilder setTelephone(@NonNull String str) {
        return put("telephone", str);
    }

    public LocalBusinessBuilder(String str) {
        super(str);
    }
}
