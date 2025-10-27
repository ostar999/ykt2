package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public final class StickerBuilder extends IndexableBuilder<StickerBuilder> {
    public StickerBuilder() {
        super("Sticker");
    }

    public final StickerBuilder setIsPartOf(@NonNull StickerPackBuilder stickerPackBuilder) {
        return put("isPartOf", stickerPackBuilder);
    }
}
