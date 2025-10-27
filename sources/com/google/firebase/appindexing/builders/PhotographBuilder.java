package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;
import java.util.Date;

/* loaded from: classes4.dex */
public final class PhotographBuilder extends IndexableBuilder<PhotographBuilder> {
    public PhotographBuilder() {
        super("Photograph");
    }

    public final PhotographBuilder setDateCreated(@NonNull Date date) {
        return put("dateCreated", date.getTime());
    }

    public final PhotographBuilder setLocationCreated(@NonNull PlaceBuilder placeBuilder) {
        return put("locationCreated", placeBuilder);
    }
}
