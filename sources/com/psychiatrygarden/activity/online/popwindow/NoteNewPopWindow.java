package com.psychiatrygarden.activity.online.popwindow;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class NoteNewPopWindow extends BottomPopupView {
    private String content;
    private NoteClickIml mNoteClickIml;

    public interface NoteClickIml {
        void mDoClickMethod();
    }

    public NoteNewPopWindow(@NonNull Context context, String content, NoteClickIml mNoteClickIml) {
        super(context);
        this.content = content;
        this.mNoteClickIml = mNoteClickIml;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        dismiss();
        this.mNoteClickIml.mDoClickMethod();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_note_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.tv_note_content);
        TextView textView2 = (TextView) findViewById(R.id.popu_cancel);
        TextView textView3 = (TextView) findViewById(R.id.popu_edit);
        textView.setText(this.content + "");
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.popwindow.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13469c.lambda$onCreate$0(view);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.popwindow.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13470c.lambda$onCreate$1(view);
            }
        });
    }
}
