package com.psychiatrygarden.widget.swipemenu;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class SwipeMenuView extends LinearLayout implements View.OnClickListener {
    private SwipeMenuLayout mLayout;
    private SwipeMenuListView mListView;
    private SwipeMenu mMenu;
    private OnSwipeItemClickListener onItemClickListener;
    private int position;

    public interface OnSwipeItemClickListener {
        void onItemClick(SwipeMenuView view, SwipeMenu menu, int index);
    }

    public SwipeMenuView(SwipeMenu menu, SwipeMenuListView listView) {
        super(menu.getContext());
        this.mListView = listView;
        this.mMenu = menu;
        Iterator<SwipeMenuItem> it = menu.getMenuItems().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            addItem(it.next(), i2);
            i2++;
        }
    }

    private void addItem(SwipeMenuItem item, int id) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(item.getWidth(), -1);
        if (item.isShowSort()) {
            layoutParams = new LinearLayout.LayoutParams(item.getWidth(), item.getHeight());
            layoutParams.topMargin = dp2px(20);
        }
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setId(id);
        linearLayout.setGravity(17);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setBackground(item.getBackground());
        linearLayout.setOnClickListener(this);
        addView(linearLayout);
        if (item.getIcon() != null) {
            linearLayout.addView(createIcon(item));
        }
        if (TextUtils.isEmpty(item.getTitle())) {
            return;
        }
        linearLayout.addView(createTitle(item));
    }

    private ImageView createIcon(SwipeMenuItem item) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(item.getIcon());
        return imageView;
    }

    private TextView createTitle(SwipeMenuItem item) {
        TextView textView = new TextView(getContext());
        textView.setText(item.getTitle());
        textView.setGravity(17);
        textView.setTextSize(item.getTitleSize());
        textView.setTextColor(item.getTitleColor());
        return textView;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(1, dp, getResources().getDisplayMetrics());
    }

    public OnSwipeItemClickListener getOnSwipeItemClickListener() {
        return this.onItemClickListener;
    }

    public int getPosition() {
        return this.position;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        if (this.onItemClickListener == null || !this.mLayout.isOpen()) {
            return;
        }
        this.onItemClickListener.onItemClick(this, this.mMenu, v2.getId());
    }

    public void setLayout(SwipeMenuLayout mLayout) {
        this.mLayout = mLayout;
    }

    public void setOnSwipeItemClickListener(OnSwipeItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
