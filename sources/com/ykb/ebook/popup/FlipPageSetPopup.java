package com.ykb.ebook.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import com.umeng.analytics.pro.d;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.databinding.PopFlipPageSetBinding;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002:\u0001\u0014B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u001a\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/ykb/ebook/popup/FlipPageSetPopup;", "Landroid/widget/PopupWindow;", "Landroid/widget/CompoundButton$OnCheckedChangeListener;", d.R, "Landroid/content/Context;", "cb", "Lcom/ykb/ebook/popup/FlipPageSetPopup$CallBack;", "(Landroid/content/Context;Lcom/ykb/ebook/popup/FlipPageSetPopup$CallBack;)V", "binding", "Lcom/ykb/ebook/databinding/PopFlipPageSetBinding;", "buttons", "Ljava/util/ArrayList;", "Landroid/widget/RadioButton;", "Lkotlin/collections/ArrayList;", "onCheckedChanged", "", "buttonView", "Landroid/widget/CompoundButton;", "isChecked", "", "CallBack", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nFlipPageSetPopup.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FlipPageSetPopup.kt\ncom/ykb/ebook/popup/FlipPageSetPopup\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,74:1\n1855#2,2:75\n*S KotlinDebug\n*F\n+ 1 FlipPageSetPopup.kt\ncom/ykb/ebook/popup/FlipPageSetPopup\n*L\n66#1:75,2\n*E\n"})
/* loaded from: classes7.dex */
public final class FlipPageSetPopup extends PopupWindow implements CompoundButton.OnCheckedChangeListener {

    @NotNull
    private final PopFlipPageSetBinding binding;

    @NotNull
    private final ArrayList<RadioButton> buttons;

    @NotNull
    private final CallBack cb;

    @NotNull
    private final Context context;

    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/popup/FlipPageSetPopup$CallBack;", "", "scrollAnimChange", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface CallBack {
        void scrollAnimChange();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlipPageSetPopup(@NotNull Context context, @NotNull CallBack cb) {
        super(-1, -2);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(cb, "cb");
        this.context = context;
        this.cb = cb;
        ArrayList<RadioButton> arrayList = new ArrayList<>();
        this.buttons = arrayList;
        PopFlipPageSetBinding popFlipPageSetBindingInflate = PopFlipPageSetBinding.inflate(LayoutInflater.from(context));
        Intrinsics.checkNotNullExpressionValue(popFlipPageSetBindingInflate, "inflate(LayoutInflater.from(context))");
        this.binding = popFlipPageSetBindingInflate;
        popFlipPageSetBindingInflate.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.popup.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FlipPageSetPopup._init_$lambda$0(this.f26449c, view);
            }
        });
        setContentView(popFlipPageSetBindingInflate.getRoot());
        setTouchable(true);
        setOutsideTouchable(true);
        setFocusable(false);
        int pageAnim = ReadConfig.INSTANCE.getPageAnim();
        if (pageAnim == 1) {
            popFlipPageSetBindingInflate.cbPageSx.setChecked(true);
        } else if (pageAnim == 2) {
            popFlipPageSetBindingInflate.cbPageFz.setChecked(true);
        } else if (pageAnim == 4) {
            popFlipPageSetBindingInflate.cbPageZy.setChecked(true);
        }
        arrayList.add(popFlipPageSetBindingInflate.cbPageFz);
        arrayList.add(popFlipPageSetBindingInflate.cbPageZy);
        arrayList.add(popFlipPageSetBindingInflate.cbPageSx);
        popFlipPageSetBindingInflate.cbPageFz.setOnCheckedChangeListener(this);
        popFlipPageSetBindingInflate.cbPageZy.setOnCheckedChangeListener(this);
        popFlipPageSetBindingInflate.cbPageSx.setOnCheckedChangeListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$0(FlipPageSetPopup this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(@Nullable CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (Intrinsics.areEqual(buttonView, this.binding.cbPageFz)) {
                ReadConfig.INSTANCE.setPageAnim(2);
            } else if (Intrinsics.areEqual(buttonView, this.binding.cbPageZy)) {
                ReadConfig.INSTANCE.setPageAnim(4);
            } else if (Intrinsics.areEqual(buttonView, this.binding.cbPageSx)) {
                ReadConfig.INSTANCE.setPageAnim(1);
            }
        }
        this.cb.scrollAnimChange();
        for (RadioButton radioButton : this.buttons) {
            if (!Intrinsics.areEqual(radioButton, buttonView)) {
                radioButton.setChecked(false);
            }
        }
        dismiss();
    }
}
