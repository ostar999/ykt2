package com.chad.library.adapter.base.dragswipe;

import android.graphics.Canvas;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.R;
import com.chad.library.adapter.base.module.BaseDraggableModule;

/* loaded from: classes2.dex */
public class DragAndSwipeCallback extends ItemTouchHelper.Callback {
    private BaseDraggableModule mDraggableModule;
    private float mMoveThreshold = 0.1f;
    private float mSwipeThreshold = 0.7f;
    private int mDragMoveFlags = 15;
    private int mSwipeMoveFlags = 32;

    public DragAndSwipeCallback(BaseDraggableModule baseDraggableModule) {
        this.mDraggableModule = baseDraggableModule;
    }

    private boolean isViewCreateByAdapter(@NonNull RecyclerView.ViewHolder viewHolder) {
        int itemViewType = viewHolder.getItemViewType();
        return itemViewType == 268435729 || itemViewType == 268436002 || itemViewType == 268436275 || itemViewType == 268436821;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (isViewCreateByAdapter(viewHolder)) {
            return;
        }
        View view = viewHolder.itemView;
        int i2 = R.id.BaseQuickAdapter_dragging_support;
        if (view.getTag(i2) != null && ((Boolean) viewHolder.itemView.getTag(i2)).booleanValue()) {
            BaseDraggableModule baseDraggableModule = this.mDraggableModule;
            if (baseDraggableModule != null) {
                baseDraggableModule.onItemDragEnd(viewHolder);
            }
            viewHolder.itemView.setTag(i2, Boolean.FALSE);
        }
        View view2 = viewHolder.itemView;
        int i3 = R.id.BaseQuickAdapter_swiping_support;
        if (view2.getTag(i3) == null || !((Boolean) viewHolder.itemView.getTag(i3)).booleanValue()) {
            return;
        }
        BaseDraggableModule baseDraggableModule2 = this.mDraggableModule;
        if (baseDraggableModule2 != null) {
            baseDraggableModule2.onItemSwipeClear(viewHolder);
        }
        viewHolder.itemView.setTag(i3, Boolean.FALSE);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public float getMoveThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return this.mMoveThreshold;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return isViewCreateByAdapter(viewHolder) ? ItemTouchHelper.Callback.makeMovementFlags(0, 0) : ItemTouchHelper.Callback.makeMovementFlags(this.mDragMoveFlags, this.mSwipeMoveFlags);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return this.mSwipeThreshold;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean isItemViewSwipeEnabled() {
        BaseDraggableModule baseDraggableModule = this.mDraggableModule;
        if (baseDraggableModule != null) {
            return baseDraggableModule.getIsSwipeEnabled();
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean isLongPressDragEnabled() {
        BaseDraggableModule baseDraggableModule = this.mDraggableModule;
        return (baseDraggableModule == null || !baseDraggableModule.getIsDragEnabled() || this.mDraggableModule.hasToggleView()) ? false : true;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onChildDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float f2, float f3, int i2, boolean z2) {
        super.onChildDrawOver(canvas, recyclerView, viewHolder, f2, f3, i2, z2);
        if (i2 != 1 || isViewCreateByAdapter(viewHolder)) {
            return;
        }
        View view = viewHolder.itemView;
        canvas.save();
        if (f2 > 0.0f) {
            canvas.clipRect(view.getLeft(), view.getTop(), view.getLeft() + f2, view.getBottom());
            canvas.translate(view.getLeft(), view.getTop());
        } else {
            canvas.clipRect(view.getRight() + f2, view.getTop(), view.getRight(), view.getBottom());
            canvas.translate(view.getRight() + f2, view.getTop());
        }
        BaseDraggableModule baseDraggableModule = this.mDraggableModule;
        if (baseDraggableModule != null) {
            baseDraggableModule.onItemSwiping(canvas, viewHolder, f2, f3, z2);
        }
        canvas.restore();
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder2) {
        return viewHolder.getItemViewType() == viewHolder2.getItemViewType();
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int i2, @NonNull RecyclerView.ViewHolder viewHolder2, int i3, int i4, int i5) {
        super.onMoved(recyclerView, viewHolder, i2, viewHolder2, i3, i4, i5);
        BaseDraggableModule baseDraggableModule = this.mDraggableModule;
        if (baseDraggableModule != null) {
            baseDraggableModule.onItemDragMoving(viewHolder, viewHolder2);
        }
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i2) {
        if (i2 == 2 && !isViewCreateByAdapter(viewHolder)) {
            BaseDraggableModule baseDraggableModule = this.mDraggableModule;
            if (baseDraggableModule != null) {
                baseDraggableModule.onItemDragStart(viewHolder);
            }
            viewHolder.itemView.setTag(R.id.BaseQuickAdapter_dragging_support, Boolean.TRUE);
        } else if (i2 == 1 && !isViewCreateByAdapter(viewHolder)) {
            BaseDraggableModule baseDraggableModule2 = this.mDraggableModule;
            if (baseDraggableModule2 != null) {
                baseDraggableModule2.onItemSwipeStart(viewHolder);
            }
            viewHolder.itemView.setTag(R.id.BaseQuickAdapter_swiping_support, Boolean.TRUE);
        }
        super.onSelectedChanged(viewHolder, i2);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i2) {
        BaseDraggableModule baseDraggableModule;
        if (isViewCreateByAdapter(viewHolder) || (baseDraggableModule = this.mDraggableModule) == null) {
            return;
        }
        baseDraggableModule.onItemSwiped(viewHolder);
    }

    public void setDragMoveFlags(int i2) {
        this.mDragMoveFlags = i2;
    }

    public void setMoveThreshold(float f2) {
        this.mMoveThreshold = f2;
    }

    public void setSwipeMoveFlags(int i2) {
        this.mSwipeMoveFlags = i2;
    }

    public void setSwipeThreshold(float f2) {
        this.mSwipeThreshold = f2;
    }
}
