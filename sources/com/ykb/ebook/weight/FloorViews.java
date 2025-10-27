package com.ykb.ebook.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.model.SubFloorComments;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes8.dex */
public class FloorViews<T> extends LinearLayout {
    public T adapter;
    private SubFloorComments datas;
    private int density;
    private Drawable drawer;
    private SubFloorFactorys factory;
    private boolean isNewComzantong;
    public String islouzhu;
    public FloorCommentListenter mCommentListenter;
    public List<Integer> mlist;

    public FloorViews(Context context) {
        super(context);
        this.isNewComzantong = false;
        this.mlist = new ArrayList();
        this.islouzhu = "0";
        init(context);
    }

    private void init(Context context) {
        setOrientation(1);
        this.density = (int) (context.getResources().getDisplayMetrics().density * 3.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        ((TextView) view.findViewById(R.id.hide_text)).setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        view.findViewById(R.id.hide_pb).setVisibility(0);
        removeAllViews();
        for (int i2 = 0; i2 < this.datas.size(); i2++) {
            this.datas.get(i2).set_zhankai(true);
            addView(this.factory.buildSubFloor(this.datas.get(i2), this, this.mCommentListenter, this.isNewComzantong, this.adapter, this.islouzhu));
        }
        reLayoutChildren();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        int childCount = getChildCount();
        if (this.drawer != null && childCount > 0) {
            for (int i2 = childCount - 1; i2 >= 0; i2--) {
                if (i2 >= this.mlist.size() - 1) {
                    View childAt = getChildAt(i2);
                    this.drawer.setBounds(childAt.getLeft(), childAt.getLeft(), childAt.getRight(), childAt.getBottom());
                    this.drawer.draw(canvas);
                } else {
                    View childAt2 = getChildAt(i2);
                    Paint paint = new Paint(1);
                    paint.setStrokeWidth(1.0f);
                    if (ReadConfig.INSTANCE.getColorMode() != 2) {
                        paint.setColor(getResources().getColor(R.color.login_font_comm_color));
                    } else {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.tertiary_backgroup_color_night));
                    }
                    canvas.drawLine(childAt2.getLeft(), childAt2.getBottom(), childAt2.getRight(), childAt2.getBottom(), paint);
                }
            }
        }
        super.dispatchDraw(canvas);
    }

    public int getFloorNum() {
        return getChildCount();
    }

    public String getIslouzhu() {
        return this.islouzhu;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        if (getChildCount() <= 0) {
            setMeasuredDimension(0, 0);
        } else {
            super.onMeasure(i2, i3);
        }
    }

    public void reLayoutChildren() {
        int childCount = getChildCount();
        if (this.mlist.size() > 0) {
            this.mlist.clear();
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
            layoutParams.gravity = 49;
            int i3 = childCount - i2;
            int iMin = Math.min(i3 - 1, 4);
            int i4 = this.density;
            int i5 = iMin * i4;
            if (i5 == i4 * 4) {
                this.mlist.add(Integer.valueOf(i5));
            }
            layoutParams.leftMargin = i5;
            layoutParams.rightMargin = i5;
            if (i2 == childCount - 1) {
                layoutParams.topMargin = 0;
            } else {
                layoutParams.topMargin = Math.min(i3, 4) * this.density;
            }
            childAt.setLayoutParams(layoutParams);
        }
    }

    public void setBoundDrawer(Drawable drawable) {
        this.drawer = drawable;
    }

    public void setComments(SubFloorComments subFloorComments, boolean z2, T t2) {
        this.datas = subFloorComments;
        this.isNewComzantong = z2;
        this.adapter = t2;
    }

    public void setFactory(SubFloorFactorys subFloorFactorys) {
        this.factory = subFloorFactorys;
    }

    public void setIslouzhu(String str) {
        this.islouzhu = str;
    }

    public void setmCommentListenter(FloorCommentListenter floorCommentListenter) {
        this.mCommentListenter = floorCommentListenter;
    }

    public void init() {
        removeAllViews();
        if (this.datas.iterator() == null) {
            return;
        }
        if (this.datas.size() < 5) {
            for (int i2 = 0; i2 < this.datas.size(); i2++) {
                addView(this.factory.buildSubFloor(this.datas.get(i2), this, this.mCommentListenter, this.isNewComzantong, this.adapter, this.islouzhu));
            }
        } else if (this.datas.get(0).is_zhankai()) {
            for (int i3 = 0; i3 < this.datas.size(); i3++) {
                addView(this.factory.buildSubFloor(this.datas.get(i3), this, this.mCommentListenter, this.isNewComzantong, this.adapter, this.islouzhu));
            }
        } else {
            addView(this.factory.buildSubFloor(this.datas.get(0), this, this.mCommentListenter, this.isNewComzantong, this.adapter, this.islouzhu));
            addView(this.factory.buildSubFloor(this.datas.get(1), this, this.mCommentListenter, this.isNewComzantong, this.adapter, this.islouzhu));
            View viewBuildSubHideFloor = this.factory.buildSubHideFloor(this.datas.get(2), this);
            viewBuildSubHideFloor.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.f
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f26479c.lambda$init$0(view);
                }
            });
            addView(viewBuildSubHideFloor);
            SubFloorFactorys subFloorFactorys = this.factory;
            SubFloorComments subFloorComments = this.datas;
            addView(subFloorFactorys.buildSubFloor(subFloorComments.get(subFloorComments.size() - 1), this, this.mCommentListenter, this.isNewComzantong, this.adapter, this.islouzhu));
        }
        reLayoutChildren();
    }

    public FloorViews(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isNewComzantong = false;
        this.mlist = new ArrayList();
        this.islouzhu = "0";
        init(context);
    }

    public FloorViews(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.isNewComzantong = false;
        this.mlist = new ArrayList();
        this.islouzhu = "0";
        init(context);
    }
}
