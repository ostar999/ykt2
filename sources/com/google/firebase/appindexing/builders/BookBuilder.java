package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;

/* loaded from: classes4.dex */
public class BookBuilder extends IndexableBuilder<BookBuilder> {
    public BookBuilder() {
        super("Book");
    }

    public BookBuilder setAuthor(@NonNull PersonBuilder... personBuilderArr) {
        return put(SocializeProtocolConstants.AUTHOR, personBuilderArr);
    }
}
