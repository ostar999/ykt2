package com.ykb.ebook.common_interface;

import android.widget.SeekBar;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\f"}, d2 = {"Lcom/ykb/ebook/common_interface/SeekBarChangeListener;", "Landroid/widget/SeekBar$OnSeekBarChangeListener;", "onProgressChanged", "", "seekBar", "Landroid/widget/SeekBar;", "progress", "", "fromUser", "", "onStartTrackingTouch", "onStopTrackingTouch", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public interface SeekBarChangeListener extends SeekBar.OnSeekBarChangeListener {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class DefaultImpls {
        public static void onProgressChanged(@NotNull SeekBarChangeListener seekBarChangeListener, @NotNull SeekBar seekBar, int i2, boolean z2) {
            Intrinsics.checkNotNullParameter(seekBar, "seekBar");
        }

        public static void onStartTrackingTouch(@NotNull SeekBarChangeListener seekBarChangeListener, @NotNull SeekBar seekBar) {
            Intrinsics.checkNotNullParameter(seekBar, "seekBar");
        }

        public static void onStopTrackingTouch(@NotNull SeekBarChangeListener seekBarChangeListener, @NotNull SeekBar seekBar) {
            Intrinsics.checkNotNullParameter(seekBar, "seekBar");
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    void onProgressChanged(@NotNull SeekBar seekBar, int progress, boolean fromUser);

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    void onStartTrackingTouch(@NotNull SeekBar seekBar);

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    void onStopTrackingTouch(@NotNull SeekBar seekBar);
}
