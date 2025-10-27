package com.ykb.ebook.common;

import kotlin.Metadata;
import org.apache.commons.codec.language.bm.Languages;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001H&¨\u0006\u0007"}, d2 = {"Lcom/ykb/ebook/common/EventCallback;", "", "onMessage", "", "what", "", Languages.ANY, "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public interface EventCallback {
    void onMessage(int what, @Nullable Object any);
}
