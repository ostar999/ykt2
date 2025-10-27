package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public final class PostalAddressBuilder extends IndexableBuilder<PostalAddressBuilder> {
    public PostalAddressBuilder() {
        super("PostalAddress");
    }

    public final PostalAddressBuilder setAddressCountry(@NonNull String str) {
        return put("addressCountry", str);
    }

    public final PostalAddressBuilder setAddressLocality(@NonNull String str) {
        return put("addressLocality", str);
    }

    public final PostalAddressBuilder setPostalCode(@NonNull String str) {
        return put("postalCode", str);
    }

    public final PostalAddressBuilder setStreetAddress(@NonNull String str) {
        return put("streetAddress", str);
    }
}
