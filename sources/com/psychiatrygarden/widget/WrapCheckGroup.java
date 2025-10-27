package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes6.dex */
public class WrapCheckGroup extends WordWrapView {
    List<CheckBox> childs;
    public View.OnClickListener clickListener;

    public WrapCheckGroup(Context context) {
        super(context);
        this.childs = new LinkedList();
        this.clickListener = new View.OnClickListener() { // from class: com.psychiatrygarden.widget.WrapCheckGroup.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                CheckBox checkBox = (CheckBox) v2;
                boolean zIsChecked = checkBox.isChecked();
                WrapCheckGroup.this.clearAllCheck();
                checkBox.setChecked(zIsChecked);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAllCheck() {
        for (CheckBox checkBox : this.childs) {
            if (checkBox.isChecked()) {
                checkBox.setChecked(false);
            }
        }
    }

    public void addCheckBox(CheckBox checkBox) {
        checkBox.setChecked(false);
        checkBox.setOnClickListener(this.clickListener);
        this.childs.add(checkBox);
        addView(checkBox);
    }

    public CheckBox getSelectedBox() {
        for (CheckBox checkBox : this.childs) {
            if (checkBox.isChecked()) {
                return checkBox;
            }
        }
        return null;
    }

    public void removeCheckBox() {
        this.childs.clear();
        removeAllViews();
    }

    public WrapCheckGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.childs = new LinkedList();
        this.clickListener = new View.OnClickListener() { // from class: com.psychiatrygarden.widget.WrapCheckGroup.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                CheckBox checkBox = (CheckBox) v2;
                boolean zIsChecked = checkBox.isChecked();
                WrapCheckGroup.this.clearAllCheck();
                checkBox.setChecked(zIsChecked);
            }
        };
    }

    public WrapCheckGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.childs = new LinkedList();
        this.clickListener = new View.OnClickListener() { // from class: com.psychiatrygarden.widget.WrapCheckGroup.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                CheckBox checkBox = (CheckBox) v2;
                boolean zIsChecked = checkBox.isChecked();
                WrapCheckGroup.this.clearAllCheck();
                checkBox.setChecked(zIsChecked);
            }
        };
    }
}
