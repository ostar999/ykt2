package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.adapter.DragAdapter;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class DragGrid extends GridView {
    private String LastAnimationID;
    private int Remainder;
    public int downX;
    public int downY;
    private View dragImageView;
    private ViewGroup dragItemView;
    int dragOffsetX;
    int dragOffsetY;
    public int dragPosition;
    private double dragScale;
    private int dropPosition;
    private int holdPosition;
    boolean isCircleTrue;
    private boolean isMoving;
    private int itemHeight;
    private int itemTotalCount;
    private int itemWidth;
    private int mHorizontalSpacing;
    private int mVerticalSpacing;
    private Vibrator mVibrator;
    private int nColumns;
    private int nRows;
    private int startPosition;
    private int win_view_x;
    private int win_view_y;
    private WindowManager windowManager;
    private WindowManager.LayoutParams windowParams;
    public int windowX;
    public int windowY;

    public DragGrid(Context context) {
        super(context);
        this.dragImageView = null;
        this.dragItemView = null;
        this.windowManager = null;
        this.windowParams = null;
        this.nColumns = 1;
        this.isMoving = false;
        this.dragScale = 1.2d;
        this.mHorizontalSpacing = 15;
        this.mVerticalSpacing = 15;
        init(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideDropItem() {
        ((DragAdapter) getAdapter()).setShowDropItem(false);
    }

    private void onDrag(int x2, int y2, int rawx, int rawy) {
        View view = this.dragImageView;
        if (view != null) {
            WindowManager.LayoutParams layoutParams = this.windowParams;
            layoutParams.alpha = 0.6f;
            layoutParams.x = rawx - this.win_view_x;
            layoutParams.y = rawy - this.win_view_y;
            this.windowManager.updateViewLayout(view, layoutParams);
        }
    }

    private void onDrop(int x2, int y2) {
        this.dropPosition = pointToPosition(x2, y2);
        DragAdapter dragAdapter = (DragAdapter) getAdapter();
        dragAdapter.setShowDropItem(true);
        dragAdapter.notifyDataSetChanged();
    }

    private void stopDrag() {
        View view = this.dragImageView;
        if (view != null) {
            this.windowManager.removeView(view);
            this.dragImageView = null;
        }
    }

    public void OnMove(int x2, int y2) {
        int i2;
        float f2;
        int iPointToPosition = pointToPosition(x2, y2);
        if (iPointToPosition <= (this.isCircleTrue ? SharePreferencesUtils.readLongConfig(CommonParameter.circlrListnum, ProjectApp.instance(), 1L).longValue() : SharePreferencesUtils.readLongConfig(CommonParameter.Lock_NUM_TYPE, ProjectApp.instance(), 1L).longValue()) - 1 || iPointToPosition == -1 || iPointToPosition == (i2 = this.dragPosition)) {
            return;
        }
        this.dropPosition = iPointToPosition;
        int i3 = this.startPosition;
        if (i2 != i3) {
            this.dragPosition = i3;
        }
        int i4 = this.dragPosition;
        int i5 = (i4 == i3 || i4 != iPointToPosition) ? iPointToPosition - i4 : 0;
        if (i5 == 0) {
            return;
        }
        int iAbs = Math.abs(i5);
        int i6 = this.dragPosition;
        if (iPointToPosition != i6) {
            ((ViewGroup) getChildAt(i6)).setVisibility(4);
            float f3 = (this.mHorizontalSpacing / this.itemWidth) + 1.0f;
            float f4 = (this.mVerticalSpacing / this.itemHeight) + 1.0f;
            Log.d("x_vlaue", "x_vlaue = " + f3);
            for (int i7 = 0; i7 < iAbs; i7++) {
                float f5 = 0.0f;
                if (i5 > 0) {
                    int i8 = this.dragPosition;
                    int i9 = i8 + i7 + 1;
                    this.holdPosition = i9;
                    int i10 = this.nColumns;
                    if (i8 / i10 != i9 / i10 && i9 % 4 == 0) {
                        f2 = f3 * 3.0f;
                        f5 = -f4;
                    } else {
                        f2 = -f3;
                    }
                } else {
                    int i11 = this.dragPosition;
                    int i12 = (i11 - i7) - 1;
                    this.holdPosition = i12;
                    int i13 = this.nColumns;
                    if (i11 / i13 != i12 / i13 && (i12 + 1) % 4 == 0) {
                        f2 = f3 * (-3.0f);
                        f5 = f4;
                    } else {
                        f2 = f3;
                    }
                }
                ViewGroup viewGroup = (ViewGroup) getChildAt(this.holdPosition);
                Animation moveAnimation = getMoveAnimation(f2, f5);
                viewGroup.startAnimation(moveAnimation);
                if (this.holdPosition == this.dropPosition) {
                    this.LastAnimationID = moveAnimation.toString();
                }
                moveAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.psychiatrygarden.widget.DragGrid.2
                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation) {
                        if (animation.toString().equalsIgnoreCase(DragGrid.this.LastAnimationID)) {
                            ((DragAdapter) DragGrid.this.getAdapter()).exchange(DragGrid.this.startPosition, DragGrid.this.dropPosition);
                            DragGrid dragGrid = DragGrid.this;
                            dragGrid.startPosition = dragGrid.dropPosition;
                            DragGrid dragGrid2 = DragGrid.this;
                            dragGrid2.dragPosition = dragGrid2.dropPosition;
                            DragGrid.this.isMoving = false;
                        }
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation) {
                        DragGrid.this.isMoving = true;
                    }
                });
            }
        }
    }

    public Animation getMoveAnimation(float toXValue, float toYValue) {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, toXValue, 1, 0.0f, 1, toYValue);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(300L);
        return translateAnimation;
    }

    public void init(Context context) {
        this.mVibrator = (Vibrator) context.getSystemService("vibrator");
        this.mHorizontalSpacing = CommonUtil.dip2px(context, this.mHorizontalSpacing);
    }

    public boolean isCircleTrue() {
        return this.isCircleTrue;
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            this.downX = (int) ev.getX();
            this.downY = (int) ev.getY();
            this.windowX = (int) ev.getX();
            this.windowY = (int) ev.getY();
            setOnItemClickListener(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
    }

    @Override // android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.dragImageView != null && this.dragPosition != -1) {
            super.onTouchEvent(ev);
            int x2 = (int) ev.getX();
            int y2 = (int) ev.getY();
            int action = ev.getAction();
            if (action == 0) {
                this.downX = (int) ev.getX();
                this.windowX = (int) ev.getX();
                this.downY = (int) ev.getY();
                this.windowY = (int) ev.getY();
            } else if (action == 1) {
                stopDrag();
                onDrop(x2, y2);
                requestDisallowInterceptTouchEvent(false);
            } else if (action == 2) {
                onDrag(x2, y2, (int) ev.getRawX(), (int) ev.getRawY());
                if (!this.isMoving) {
                    OnMove(x2, y2);
                }
                pointToPosition(x2, y2);
            }
        }
        return super.onTouchEvent(ev);
    }

    public void setCircleTrue(boolean circleTrue) {
        this.isCircleTrue = circleTrue;
    }

    public void setOnItemClickListener(final MotionEvent ev) {
        setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: com.psychiatrygarden.widget.DragGrid.1
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int x2 = (int) ev.getX();
                int y2 = (int) ev.getY();
                DragGrid.this.startPosition = position;
                DragGrid dragGrid = DragGrid.this;
                dragGrid.dragPosition = position;
                if (dragGrid.isCircleTrue) {
                    if (dragGrid.startPosition <= SharePreferencesUtils.readLongConfig(CommonParameter.circlrListnum, ProjectApp.instance(), 1L).longValue() - 1) {
                        return false;
                    }
                } else if (dragGrid.startPosition <= SharePreferencesUtils.readLongConfig(CommonParameter.Lock_NUM_TYPE, ProjectApp.instance(), 1L).longValue() - 1) {
                    return false;
                }
                DragGrid dragGrid2 = DragGrid.this;
                ViewGroup viewGroup = (ViewGroup) dragGrid2.getChildAt(dragGrid2.dragPosition - dragGrid2.getFirstVisiblePosition());
                TextView textView = (TextView) viewGroup.findViewById(R.id.text_item);
                textView.setSelected(true);
                textView.setEnabled(false);
                DragGrid.this.itemHeight = viewGroup.getHeight();
                DragGrid.this.itemWidth = viewGroup.getWidth();
                DragGrid dragGrid3 = DragGrid.this;
                dragGrid3.itemTotalCount = dragGrid3.getCount();
                int i2 = DragGrid.this.itemTotalCount / DragGrid.this.nColumns;
                DragGrid dragGrid4 = DragGrid.this;
                dragGrid4.Remainder = dragGrid4.itemTotalCount % DragGrid.this.nColumns;
                if (DragGrid.this.Remainder != 0) {
                    DragGrid.this.nRows = i2 + 1;
                } else {
                    DragGrid.this.nRows = i2;
                }
                DragGrid dragGrid5 = DragGrid.this;
                if (dragGrid5.dragPosition == -1) {
                    return false;
                }
                dragGrid5.win_view_x = dragGrid5.windowX - viewGroup.getLeft();
                DragGrid dragGrid6 = DragGrid.this;
                dragGrid6.win_view_y = dragGrid6.windowY - viewGroup.getTop();
                DragGrid.this.dragOffsetX = (int) (ev.getRawX() - x2);
                DragGrid.this.dragOffsetY = (int) (ev.getRawY() - y2);
                DragGrid.this.dragItemView = viewGroup;
                viewGroup.destroyDrawingCache();
                viewGroup.setDrawingCacheEnabled(true);
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(viewGroup.getDrawingCache());
                DragGrid.this.mVibrator.vibrate(50L);
                DragGrid.this.startDrag(bitmapCreateBitmap, (int) ev.getRawX(), (int) ev.getRawY());
                DragGrid.this.hideDropItem();
                viewGroup.setVisibility(4);
                DragGrid.this.isMoving = false;
                DragGrid.this.requestDisallowInterceptTouchEvent(true);
                return true;
            }
        });
    }

    public void startDrag(Bitmap dragBitmap, int x2, int y2) {
        stopDrag();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.windowParams = layoutParams;
        layoutParams.gravity = 51;
        layoutParams.x = x2 - this.win_view_x;
        layoutParams.y = y2 - this.win_view_y;
        layoutParams.width = (int) (this.dragScale * dragBitmap.getWidth());
        this.windowParams.height = (int) (this.dragScale * dragBitmap.getHeight());
        WindowManager.LayoutParams layoutParams2 = this.windowParams;
        layoutParams2.flags = 408;
        layoutParams2.format = -3;
        layoutParams2.windowAnimations = 0;
        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(dragBitmap);
        WindowManager windowManager = (WindowManager) getContext().getSystemService("window");
        this.windowManager = windowManager;
        windowManager.addView(imageView, this.windowParams);
        this.dragImageView = imageView;
    }

    public DragGrid(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.dragImageView = null;
        this.dragItemView = null;
        this.windowManager = null;
        this.windowParams = null;
        this.nColumns = 1;
        this.isMoving = false;
        this.dragScale = 1.2d;
        this.mHorizontalSpacing = 15;
        this.mVerticalSpacing = 15;
        init(context);
    }

    public DragGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.dragImageView = null;
        this.dragItemView = null;
        this.windowManager = null;
        this.windowParams = null;
        this.nColumns = 1;
        this.isMoving = false;
        this.dragScale = 1.2d;
        this.mHorizontalSpacing = 15;
        this.mVerticalSpacing = 15;
        init(context);
    }
}
