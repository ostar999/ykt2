package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public final class StickerPackBuilder extends IndexableBuilder<StickerPackBuilder> {
    public StickerPackBuilder() {
        super("StickerPack");
    }

    public final StickerPackBuilder setHasSticker(@NonNull StickerBuilder... stickerBuilderArr) {
        return put("hasSticker", stickerBuilderArr);
    }
}
