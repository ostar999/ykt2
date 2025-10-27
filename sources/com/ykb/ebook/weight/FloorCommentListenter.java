package com.ykb.ebook.weight;

import android.view.View;
import com.ykb.ebook.model.BookReview;

/* loaded from: classes8.dex */
public interface FloorCommentListenter {
    void commListOppose(String str, String str2, String str3);

    void commListPraise(String str, View view, String str2, String str3);

    void commListPraiseFaile();

    void commListReply(BookReview bookReview, View view);

    void commListSupport(String str, String str2, String str3);

    void commentListenerData(BookReview bookReview, View view);
}
