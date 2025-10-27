package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class AnimatedExpandableListView extends ExpandableListView {
    private static final int ANIMATION_DURATION = 300;
    private static final String TAG = AnimatedExpandableListAdapter.class.getSimpleName();
    private AnimatedExpandableListAdapter adapter;

    public static abstract class AnimatedExpandableListAdapter extends BaseExpandableListAdapter {
        private static final int STATE_COLLAPSING = 2;
        private static final int STATE_EXPANDING = 1;
        private static final int STATE_IDLE = 0;
        private final SparseArray<GroupInfo> groupInfo = new SparseArray<>();
        private AnimatedExpandableListView parent;

        private GroupInfo getGroupInfo(int groupPosition) {
            GroupInfo groupInfo = this.groupInfo.get(groupPosition);
            if (groupInfo != null) {
                return groupInfo;
            }
            GroupInfo groupInfo2 = new GroupInfo();
            this.groupInfo.put(groupPosition, groupInfo2);
            return groupInfo2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setParent(AnimatedExpandableListView parent) {
            this.parent = parent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void startCollapseAnimation(int groupPosition, int firstChildPosition) {
            GroupInfo groupInfo = getGroupInfo(groupPosition);
            groupInfo.animating = true;
            groupInfo.firstChildPosition = firstChildPosition;
            groupInfo.expanding = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void startExpandAnimation(int groupPosition, int firstChildPosition) {
            GroupInfo groupInfo = getGroupInfo(groupPosition);
            groupInfo.animating = true;
            groupInfo.firstChildPosition = firstChildPosition;
            groupInfo.expanding = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void stopAnimation(int groupPosition) {
            getGroupInfo(groupPosition).animating = false;
        }

        public ViewGroup.LayoutParams generateDefaultLayoutParams() {
            return new AbsListView.LayoutParams(-1, -2, 0);
        }

        @Override // android.widget.BaseExpandableListAdapter, android.widget.HeterogeneousExpandableList
        public final int getChildType(int groupPosition, int childPosition) {
            if (getGroupInfo(groupPosition).animating) {
                return 0;
            }
            return getRealChildType(groupPosition, childPosition) + 1;
        }

        @Override // android.widget.BaseExpandableListAdapter, android.widget.HeterogeneousExpandableList
        public final int getChildTypeCount() {
            return getRealChildTypeCount() + 1;
        }

        @Override // android.widget.ExpandableListAdapter
        public final View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
            int i2;
            int i3;
            final GroupInfo groupInfo = getGroupInfo(groupPosition);
            if (!groupInfo.animating) {
                return getRealChildView(groupPosition, childPosition, isLastChild, convertView, parent);
            }
            View dummyView = convertView;
            boolean z2 = false;
            if (!(dummyView instanceof DummyView)) {
                dummyView = new DummyView(parent.getContext());
                dummyView.setLayoutParams(new AbsListView.LayoutParams(-1, 0));
            }
            View view = dummyView;
            if (childPosition < groupInfo.firstChildPosition) {
                view.getLayoutParams().height = 0;
                return view;
            }
            final ExpandableListView expandableListView = (ExpandableListView) parent;
            final DummyView dummyView2 = (DummyView) view;
            dummyView2.clearViews();
            dummyView2.setDivider(expandableListView.getDivider(), parent.getMeasuredWidth(), expandableListView.getDividerHeight());
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), 1073741824);
            int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
            int height = parent.getHeight();
            int realChildrenCount = getRealChildrenCount(groupPosition);
            int i4 = groupInfo.firstChildPosition;
            int i5 = 0;
            while (true) {
                if (i4 >= realChildrenCount) {
                    i2 = 1;
                    i3 = i5;
                    break;
                }
                boolean z3 = i4 == realChildrenCount + (-1) ? true : z2;
                i2 = 1;
                int i6 = i4;
                int i7 = i4;
                boolean z4 = z3;
                int i8 = realChildrenCount;
                int i9 = height;
                View realChildView = getRealChildView(groupPosition, i6, z4, null, parent);
                AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) realChildView.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = (AbsListView.LayoutParams) generateDefaultLayoutParams();
                    realChildView.setLayoutParams(layoutParams);
                }
                int i10 = layoutParams.height;
                realChildView.measure(iMakeMeasureSpec, i10 > 0 ? View.MeasureSpec.makeMeasureSpec(i10, 1073741824) : iMakeMeasureSpec2);
                int measuredHeight = i5 + realChildView.getMeasuredHeight();
                if (measuredHeight >= i9) {
                    dummyView2.addFakeView(realChildView);
                    i3 = measuredHeight + (((i8 - i7) - 1) * (measuredHeight / (i7 + 1)));
                    break;
                }
                dummyView2.addFakeView(realChildView);
                i4 = i7 + 1;
                i5 = measuredHeight;
                height = i9;
                realChildrenCount = i8;
                z2 = false;
            }
            Object tag = dummyView2.getTag();
            int iIntValue = tag == null ? 0 : ((Integer) tag).intValue();
            boolean z5 = groupInfo.expanding;
            if (z5 && iIntValue != i2) {
                ExpandAnimation expandAnimation = new ExpandAnimation(dummyView2, 0, i3, groupInfo);
                expandAnimation.setDuration(this.parent.getAnimationDuration());
                expandAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.psychiatrygarden.widget.AnimatedExpandableListView.AnimatedExpandableListAdapter.1
                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation) {
                        AnimatedExpandableListAdapter.this.stopAnimation(groupPosition);
                        AnimatedExpandableListAdapter.this.notifyDataSetChanged();
                        dummyView2.setTag(0);
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation) {
                    }
                });
                dummyView2.startAnimation(expandAnimation);
                dummyView2.setTag(Integer.valueOf(i2));
            } else if (!z5 && iIntValue != 2) {
                if (groupInfo.dummyHeight == -1) {
                    groupInfo.dummyHeight = i3;
                }
                ExpandAnimation expandAnimation2 = new ExpandAnimation(dummyView2, groupInfo.dummyHeight, 0, groupInfo);
                expandAnimation2.setDuration(this.parent.getAnimationDuration());
                expandAnimation2.setAnimationListener(new Animation.AnimationListener() { // from class: com.psychiatrygarden.widget.AnimatedExpandableListView.AnimatedExpandableListAdapter.2
                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation) {
                        AnimatedExpandableListAdapter.this.stopAnimation(groupPosition);
                        expandableListView.collapseGroup(groupPosition);
                        AnimatedExpandableListAdapter.this.notifyDataSetChanged();
                        groupInfo.dummyHeight = -1;
                        dummyView2.setTag(0);
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation) {
                    }
                });
                dummyView2.startAnimation(expandAnimation2);
                dummyView2.setTag(2);
            }
            return view;
        }

        @Override // android.widget.ExpandableListAdapter
        public final int getChildrenCount(int groupPosition) {
            GroupInfo groupInfo = getGroupInfo(groupPosition);
            return groupInfo.animating ? groupInfo.firstChildPosition + 1 : getRealChildrenCount(groupPosition);
        }

        public int getRealChildType(int groupPosition, int childPosition) {
            return 0;
        }

        public int getRealChildTypeCount() {
            return 1;
        }

        public abstract View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent);

        public abstract int getRealChildrenCount(int groupPosition);

        public void notifyGroupExpanded(int groupPosition) {
            getGroupInfo(groupPosition).dummyHeight = -1;
        }
    }

    public static class DummyView extends View {
        private Drawable divider;
        private int dividerHeight;
        private int dividerWidth;
        private List<View> views;

        public DummyView(Context context) {
            super(context);
            this.views = new ArrayList();
        }

        public void addFakeView(View childView) {
            childView.layout(0, 0, getWidth(), childView.getMeasuredHeight());
            this.views.add(childView);
        }

        public void clearViews() {
            this.views.clear();
        }

        @Override // android.view.View
        public void dispatchDraw(Canvas canvas) {
            canvas.save();
            Drawable drawable = this.divider;
            if (drawable != null) {
                drawable.setBounds(0, 0, this.dividerWidth, this.dividerHeight);
            }
            int size = this.views.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view = this.views.get(i2);
                canvas.save();
                canvas.clipRect(0, 0, getWidth(), view.getMeasuredHeight());
                view.draw(canvas);
                canvas.restore();
                Drawable drawable2 = this.divider;
                if (drawable2 != null) {
                    drawable2.draw(canvas);
                    canvas.translate(0.0f, this.dividerHeight);
                }
                canvas.translate(0.0f, view.getMeasuredHeight());
            }
            canvas.restore();
        }

        @Override // android.view.View
        public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
            super.onLayout(changed, left, top2, right, bottom);
            int size = this.views.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view = this.views.get(i2);
                view.layout(left, top2, view.getMeasuredWidth() + left, view.getMeasuredHeight() + top2);
            }
        }

        public void setDivider(Drawable divider, int dividerWidth, int dividerHeight) {
            if (divider != null) {
                this.divider = divider;
                this.dividerWidth = dividerWidth;
                this.dividerHeight = dividerHeight;
                divider.setBounds(0, 0, dividerWidth, dividerHeight);
            }
        }
    }

    public static class ExpandAnimation extends Animation {
        private int baseHeight;
        private int delta;
        private GroupInfo groupInfo;
        private View view;

        @Override // android.view.animation.Animation
        public void applyTransformation(float interpolatedTime, Transformation t2) {
            int i2;
            super.applyTransformation(interpolatedTime, t2);
            if (interpolatedTime < 1.0f) {
                i2 = this.baseHeight + ((int) (this.delta * interpolatedTime));
            } else {
                i2 = this.delta + this.baseHeight;
            }
            this.view.getLayoutParams().height = i2;
            this.groupInfo.dummyHeight = i2;
            this.view.requestLayout();
        }

        private ExpandAnimation(View v2, int startHeight, int endHeight, GroupInfo info) {
            this.baseHeight = startHeight;
            this.delta = endHeight - startHeight;
            this.view = v2;
            this.groupInfo = info;
            v2.getLayoutParams().height = startHeight;
            this.view.requestLayout();
        }
    }

    public static class GroupInfo {
        boolean animating;
        int dummyHeight;
        boolean expanding;
        int firstChildPosition;

        private GroupInfo() {
            this.animating = false;
            this.expanding = false;
            this.dummyHeight = -1;
        }
    }

    public AnimatedExpandableListView(Context context) {
        super(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getAnimationDuration() {
        return 300;
    }

    public boolean collapseGroupWithAnimation(int groupPos) {
        int flatListPosition = getFlatListPosition(ExpandableListView.getPackedPositionForGroup(groupPos));
        if (flatListPosition != -1) {
            int firstVisiblePosition = flatListPosition - getFirstVisiblePosition();
            if (firstVisiblePosition < 0 || firstVisiblePosition >= getChildCount()) {
                return collapseGroup(groupPos);
            }
            if (getChildAt(firstVisiblePosition).getBottom() >= getBottom()) {
                return collapseGroup(groupPos);
            }
        }
        long expandableListPosition = getExpandableListPosition(getFirstVisiblePosition());
        int packedPositionChild = ExpandableListView.getPackedPositionChild(expandableListPosition);
        int packedPositionGroup = ExpandableListView.getPackedPositionGroup(expandableListPosition);
        if (packedPositionChild == -1 || packedPositionGroup != groupPos) {
            packedPositionChild = 0;
        }
        this.adapter.startCollapseAnimation(groupPos, packedPositionChild);
        this.adapter.notifyDataSetChanged();
        return isGroupExpanded(groupPos);
    }

    @SuppressLint({"NewApi"})
    public boolean expandGroupWithAnimation(int groupPos) {
        int firstVisiblePosition;
        if (groupPos == this.adapter.getGroupCount() - 1) {
            return expandGroup(groupPos, true);
        }
        int flatListPosition = getFlatListPosition(ExpandableListView.getPackedPositionForGroup(groupPos));
        if (flatListPosition == -1 || (firstVisiblePosition = flatListPosition - getFirstVisiblePosition()) >= getChildCount() || getChildAt(firstVisiblePosition).getBottom() < getBottom()) {
            this.adapter.startExpandAnimation(groupPos, 0);
            return expandGroup(groupPos);
        }
        this.adapter.notifyGroupExpanded(groupPos);
        return expandGroup(groupPos);
    }

    @Override // android.widget.ExpandableListView
    public void setAdapter(ExpandableListAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof AnimatedExpandableListAdapter) {
            AnimatedExpandableListAdapter animatedExpandableListAdapter = (AnimatedExpandableListAdapter) adapter;
            this.adapter = animatedExpandableListAdapter;
            animatedExpandableListAdapter.setParent(this);
        } else {
            throw new ClassCastException(adapter.toString() + " must implement AnimatedExpandableListAdapter");
        }
    }

    public AnimatedExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatedExpandableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
