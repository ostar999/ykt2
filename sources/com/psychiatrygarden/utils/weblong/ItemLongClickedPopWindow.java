package com.psychiatrygarden.utils.weblong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ItemLongClickedPopWindow extends PopupWindow {
    public static final int ACHOR_VIEW_POPUPWINDOW = 6;
    public static final int FAVORITES_ITEM_POPUPWINDOW = 0;
    public static final int FAVORITES_VIEW_POPUPWINDOW = 1;
    public static final int HISTORY_ITEM_POPUPWINDOW = 3;
    public static final int HISTORY_VIEW_POPUPWINDOW = 4;
    public static final int IMAGE_VIEW_POPUPWINDOW = 5;
    private Context context;
    private LayoutInflater itemLongClickedPopWindowInflater;
    private View itemLongClickedPopWindowView;
    private int type;

    public ItemLongClickedPopWindow(Context context, int type, int width, int height) {
        super(context);
        this.context = context;
        this.type = type;
        initTab();
        setWidth(width);
        setHeight(height);
        setContentView(this.itemLongClickedPopWindowView);
        setOutsideTouchable(true);
        setFocusable(true);
    }

    private void initTab() {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.context);
        this.itemLongClickedPopWindowInflater = layoutInflaterFrom;
        if (this.type != 5) {
            return;
        }
        this.itemLongClickedPopWindowView = layoutInflaterFrom.inflate(R.layout.list_item_longclicked_img, (ViewGroup) null);
    }

    public View getView(int id) {
        return this.itemLongClickedPopWindowView.findViewById(id);
    }
}
