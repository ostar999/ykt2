package com.actionbarsherlock.widget.wheelview.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/* loaded from: classes.dex */
public abstract class AbstractWheelTextAdapter extends AbstractWheelAdapter {
    public static final int DEFAULT_TEXT_COLOR = -11184811;
    public static final int DEFAULT_TEXT_SIZE = 20;
    public static final int LABEL_COLOR = -16777216;
    protected static final int NO_RESOURCE = 0;
    public static final int TEXT_VIEW_ITEM_RESOURCE = -1;
    protected Context context;
    protected int emptyItemResourceId;
    protected LayoutInflater inflater;
    protected int itemResourceId;
    protected int itemTextResourceId;
    private int textColor;
    private int textSize;

    public AbstractWheelTextAdapter(Context context) {
        this(context, -1);
    }

    public void configureTextView(TextView textView, int i2, int i3) {
        textView.setTextColor(i2);
        textView.setGravity(17);
        textView.setTextSize(i3);
        textView.setPadding(0, 25, 0, 15);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setLines(1);
    }

    @Override // com.actionbarsherlock.widget.wheelview.adapter.AbstractWheelAdapter, com.actionbarsherlock.widget.wheelview.adapter.WheelViewAdapter
    public View getEmptyItem(View view, ViewGroup viewGroup) {
        if (view == null) {
            view = getView(this.emptyItemResourceId, viewGroup);
        }
        if (this.emptyItemResourceId == -1 && (view instanceof TextView)) {
            configureTextView((TextView) view, this.textColor, this.textSize);
        }
        return view;
    }

    public int getEmptyItemResource() {
        return this.emptyItemResourceId;
    }

    @Override // com.actionbarsherlock.widget.wheelview.adapter.WheelViewAdapter
    public View getItem(int i2, View view, ViewGroup viewGroup) {
        if (i2 < 0 || i2 >= getItemsCount()) {
            return null;
        }
        if (view == null) {
            view = getView(this.itemResourceId, viewGroup);
        }
        TextView textView = getTextView(view, this.itemTextResourceId);
        if (textView != null) {
            CharSequence itemText = getItemText(i2);
            if (itemText == null) {
                itemText = "";
            }
            textView.setText(itemText);
            if (this.itemResourceId == -1) {
                configureTextView(textView, this.textColor, this.textSize);
            }
        }
        return view;
    }

    public int getItemResource() {
        return this.itemResourceId;
    }

    public abstract CharSequence getItemText(int i2);

    public int getItemTextResource() {
        return this.itemTextResourceId;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public int getTextSize() {
        return this.textSize;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x000b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.widget.TextView getTextView(android.view.View r2, int r3) {
        /*
            r1 = this;
            if (r3 != 0) goto Lb
            boolean r0 = r2 instanceof android.widget.TextView     // Catch: java.lang.ClassCastException -> L9
            if (r0 == 0) goto Lb
            android.widget.TextView r2 = (android.widget.TextView) r2     // Catch: java.lang.ClassCastException -> L9
            goto L24
        L9:
            r2 = move-exception
            goto L14
        Lb:
            if (r3 == 0) goto L23
            android.view.View r2 = r2.findViewById(r3)     // Catch: java.lang.ClassCastException -> L9
            android.widget.TextView r2 = (android.widget.TextView) r2     // Catch: java.lang.ClassCastException -> L9
            goto L24
        L14:
            java.lang.String r3 = "AbstractWheelAdapter"
            java.lang.String r0 = "You must supply a resource ID for a TextView"
            android.util.Log.e(r3, r0)
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r0 = "AbstractWheelAdapter requires the resource ID to be a TextView"
            r3.<init>(r0, r2)
            throw r3
        L23:
            r2 = 0
        L24:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.actionbarsherlock.widget.wheelview.adapter.AbstractWheelTextAdapter.getTextView(android.view.View, int):android.widget.TextView");
    }

    public View getView(int i2, ViewGroup viewGroup) {
        if (i2 == -1) {
            return new TextView(this.context);
        }
        if (i2 != 0) {
            return this.inflater.inflate(i2, viewGroup, false);
        }
        return null;
    }

    public void setEmptyItemResource(int i2) {
        this.emptyItemResourceId = i2;
    }

    public void setItemResource(int i2) {
        this.itemResourceId = i2;
    }

    public void setItemTextResource(int i2) {
        this.itemTextResourceId = i2;
    }

    public void setTextColor(int i2) {
        this.textColor = i2;
    }

    public void setTextSize(int i2) {
        this.textSize = i2;
    }

    public AbstractWheelTextAdapter(Context context, int i2) {
        this(context, i2, 0);
    }

    public AbstractWheelTextAdapter(Context context, int i2, int i3) {
        this.textColor = DEFAULT_TEXT_COLOR;
        this.textSize = 20;
        this.context = context;
        this.itemResourceId = i2;
        this.itemTextResourceId = i3;
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }
}
