package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.HorizontalAttachPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class PopNoteListMenu extends HorizontalAttachPopupView implements View.OnClickListener {
    private boolean landScape;
    private OnClickBtnListener onClickBtnListener;
    private int position;
    private TextView tv_note_copy;
    private TextView tv_note_delete;
    private TextView tv_note_edit;

    public interface OnClickBtnListener {
        void onPopNoteCopy(int position);

        void onPopNoteDelete(int position);

        void onPopNoteEdit(int position);
    }

    public PopNoteListMenu(@NonNull Context context, int position, OnClickBtnListener onClickBtnListener) {
        super(context);
        this.onClickBtnListener = onClickBtnListener;
        this.position = position;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return !this.landScape ? R.layout.pop_question_note_list_menu : R.layout.pop_question_note_list_menu_landscape;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        switch (v2.getId()) {
            case R.id.tv_note_copy /* 2131368295 */:
                dismiss();
                this.onClickBtnListener.onPopNoteCopy(this.position);
                break;
            case R.id.tv_note_delete /* 2131368297 */:
                dismiss();
                this.onClickBtnListener.onPopNoteDelete(this.position);
                break;
            case R.id.tv_note_edit /* 2131368298 */:
                dismiss();
                this.onClickBtnListener.onPopNoteEdit(this.position);
                break;
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.tv_note_copy = (TextView) findViewById(R.id.tv_note_copy);
        this.tv_note_edit = (TextView) findViewById(R.id.tv_note_edit);
        this.tv_note_delete = (TextView) findViewById(R.id.tv_note_delete);
        this.tv_note_edit.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.sc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16900c.onClick(view);
            }
        });
        this.tv_note_delete.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.sc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16900c.onClick(view);
            }
        });
        this.tv_note_copy.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.sc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16900c.onClick(view);
            }
        });
    }

    public void setLandScape(boolean landScape) {
        this.landScape = landScape;
    }
}
