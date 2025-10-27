package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;

/* loaded from: classes4.dex */
public class AudiobookBuilder extends IndexableBuilder<AudiobookBuilder> {
    public AudiobookBuilder() {
        super("Audiobook");
    }

    public AudiobookBuilder setAuthor(@NonNull PersonBuilder... personBuilderArr) {
        return put(SocializeProtocolConstants.AUTHOR, personBuilderArr);
    }

    public AudiobookBuilder setReadBy(@NonNull PersonBuilder... personBuilderArr) {
        return put("readBy", personBuilderArr);
    }
}
