package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public final class PersonBuilder extends IndexableBuilder<PersonBuilder> {
    public PersonBuilder() {
        super("Person");
    }

    public final PersonBuilder setEmail(@NonNull String str) {
        return put("email", str);
    }

    public final PersonBuilder setIsSelf(@NonNull boolean z2) {
        return put("isSelf", z2);
    }

    public final PersonBuilder setTelephone(@NonNull String str) {
        return put("telephone", str);
    }
}
