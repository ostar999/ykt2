package com.ykb.ebook.common_interface;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J\u0012\u0010\u0006\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J\u0012\u0010\u0007\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016¨\u0006\b"}, d2 = {"Lcom/ykb/ebook/common_interface/KeyboardAction;", "", "dialogHideKeyboard", "", "view", "Landroid/view/View;", "dialogShowKeyboard", "dialogToggleSoftInput", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public interface KeyboardAction {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class DefaultImpls {
        public static void dialogHideKeyboard(@NotNull KeyboardAction keyboardAction, @Nullable View view) {
            InputMethodManager inputMethodManager;
            if (view == null || (inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method")) == null) {
                return;
            }
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 2);
        }

        public static void dialogShowKeyboard(@NotNull KeyboardAction keyboardAction, @Nullable View view) {
            InputMethodManager inputMethodManager;
            if (view == null || (inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method")) == null) {
                return;
            }
            inputMethodManager.showSoftInput(view, 2);
        }

        public static void dialogToggleSoftInput(@NotNull KeyboardAction keyboardAction, @Nullable View view) {
            InputMethodManager inputMethodManager;
            if (view == null || (inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method")) == null) {
                return;
            }
            inputMethodManager.toggleSoftInput(0, 0);
        }
    }

    void dialogHideKeyboard(@Nullable View view);

    void dialogShowKeyboard(@Nullable View view);

    void dialogToggleSoftInput(@Nullable View view);
}
