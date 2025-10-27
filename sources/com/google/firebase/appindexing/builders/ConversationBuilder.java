package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public final class ConversationBuilder extends IndexableBuilder<ConversationBuilder> {
    public ConversationBuilder() {
        super("Conversation");
    }

    public final ConversationBuilder setId(@NonNull String str) {
        return put("id", str);
    }
}
