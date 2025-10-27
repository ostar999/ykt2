package com.ykb.ebook.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/ykb/ebook/event/ImageUploadSuccessEvent;", "", "imgUrl", "", "(Ljava/lang/String;)V", "getImgUrl", "()Ljava/lang/String;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ImageUploadSuccessEvent {

    @NotNull
    private final String imgUrl;

    public ImageUploadSuccessEvent(@NotNull String imgUrl) {
        Intrinsics.checkNotNullParameter(imgUrl, "imgUrl");
        this.imgUrl = imgUrl;
    }

    @NotNull
    public final String getImgUrl() {
        return this.imgUrl;
    }
}
