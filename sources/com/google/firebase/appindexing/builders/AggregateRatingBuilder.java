package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public final class AggregateRatingBuilder extends IndexableBuilder<AggregateRatingBuilder> {
    public AggregateRatingBuilder() {
        super("AggregateRating");
    }

    public final AggregateRatingBuilder setRatingCount(@NonNull long j2) {
        return put("ratingCount", j2);
    }

    public final AggregateRatingBuilder setRatingValue(@NonNull String str) {
        return put("ratingValue", str);
    }
}
