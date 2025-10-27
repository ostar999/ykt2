package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Date;

/* loaded from: classes4.dex */
public final class MessageBuilder extends IndexableBuilder<MessageBuilder> {
    public MessageBuilder() {
        super("Message");
    }

    public final MessageBuilder setDateRead(@NonNull Date date) {
        Preconditions.checkNotNull(date);
        return put("dateRead", date.getTime());
    }

    public final MessageBuilder setDateReceived(@NonNull Date date) {
        Preconditions.checkNotNull(date);
        return put("dateReceived", date.getTime());
    }

    public final MessageBuilder setDateSent(@NonNull Date date) {
        Preconditions.checkNotNull(date);
        return put("dateSent", date.getTime());
    }

    public final MessageBuilder setIsPartOf(@NonNull ConversationBuilder... conversationBuilderArr) {
        return put("isPartOf", conversationBuilderArr);
    }

    public final MessageBuilder setMessageAttachment(@NonNull IndexableBuilder<?>... indexableBuilderArr) {
        return put("messageAttachment", indexableBuilderArr);
    }

    public final MessageBuilder setRecipient(@NonNull PersonBuilder... personBuilderArr) {
        return put("recipient", personBuilderArr);
    }

    public final MessageBuilder setSender(@NonNull PersonBuilder personBuilder) {
        return put("sender", personBuilder);
    }

    public final MessageBuilder setText(@NonNull String str) {
        return put("text", str);
    }

    public MessageBuilder(String str) {
        super(str);
    }
}
