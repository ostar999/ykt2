package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.ruffian.library.widget.RTextView;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class ItemChapterCoverFooterBinding implements ViewBinding {

    @NonNull
    private final RTextView rootView;

    @NonNull
    public final RTextView tvFooter;

    private ItemChapterCoverFooterBinding(@NonNull RTextView rTextView, @NonNull RTextView rTextView2) {
        this.rootView = rTextView;
        this.tvFooter = rTextView2;
    }

    @NonNull
    public static ItemChapterCoverFooterBinding bind(@NonNull View view) {
        if (view == null) {
            throw new NullPointerException("rootView");
        }
        RTextView rTextView = (RTextView) view;
        return new ItemChapterCoverFooterBinding(rTextView, rTextView);
    }

    @NonNull
    public static ItemChapterCoverFooterBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ItemChapterCoverFooterBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.item_chapter_cover_footer, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public RTextView getRoot() {
        return this.rootView;
    }
}
