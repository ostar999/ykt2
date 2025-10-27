package com.psychiatrygarden.adapter;

import android.graphics.Canvas;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.dragswipe.DragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.module.BaseDraggableModule;
import com.chad.library.adapter.base.module.DraggableModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.TopicListBean;
import com.yikaobang.yixue.R;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes5.dex */
public class TopicDragAdapter extends BaseQuickAdapter<TopicListBean.DataDTO, BaseViewHolder> implements DraggableModule {
    public String value;

    public TopicDragAdapter(int layoutResId, @Nullable final List<TopicListBean.DataDTO> data, String value) {
        super(layoutResId, data);
        this.value = value;
        OnItemDragListener onItemDragListener = new OnItemDragListener() { // from class: com.psychiatrygarden.adapter.TopicDragAdapter.1
            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            }
        };
        getDraggableModule().setItemTouchHelper(new ItemTouchHelper(new DragAndSwipeCallback(getDraggableModule()) { // from class: com.psychiatrygarden.adapter.TopicDragAdapter.2
            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean canDropOver(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder current, @NonNull @NotNull RecyclerView.ViewHolder target) {
                if ("全部".equals(((TextView) target.itemView.findViewById(R.id.name)).getText())) {
                    return target.getBindingAdapterPosition() != 0 && super.canDropOver(recyclerView, current, target);
                }
                if (((TopicListBean.DataDTO) data.get(target.getBindingAdapterPosition())).getIs_default() == 1) {
                    return false;
                }
                return super.canDropOver(recyclerView, current, target);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onChildDraw(@NonNull @NotNull Canvas c3, @NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                int childAdapterPosition = recyclerView.getChildAdapterPosition(viewHolder.itemView);
                int spanCount = ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
                int itemCount = recyclerView.getLayoutManager().getItemCount();
                float f2 = (childAdapterPosition % spanCount != 0 || dX >= 0.0f) ? dX : 0.0f;
                float f3 = (childAdapterPosition >= spanCount || dY >= 0.0f) ? dY : 0.0f;
                int i2 = childAdapterPosition + 1;
                if ((i2 % spanCount == 0 || i2 == itemCount) && f2 > 0.0f) {
                    f2 = 0.0f;
                }
                float f4 = (childAdapterPosition + spanCount < itemCount || f3 <= 0.0f) ? f3 : 0.0f;
                if (isCurrentlyActive) {
                    viewHolder.itemView.animate().scaleX(1.1f).scaleY(1.1f).setDuration(300L);
                } else {
                    viewHolder.itemView.animate().scaleX(1.0f).scaleX(1.0f).setDuration(300L);
                }
                super.onChildDraw(c3, recyclerView, viewHolder, f2, f4, actionState, isCurrentlyActive);
            }
        }));
        getDraggableModule().setSwipeEnabled(true);
        getDraggableModule().setDragEnabled(true);
        getDraggableModule().setOnItemDragListener(onItemDragListener);
        getDraggableModule().getItemTouchHelperCallback().setSwipeMoveFlags(48);
    }

    @Override // com.chad.library.adapter.base.module.DraggableModule
    public /* synthetic */ BaseDraggableModule addDraggableModule(BaseQuickAdapter baseQuickAdapter) {
        return t0.g.a(this, baseQuickAdapter);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder baseViewHolder, TopicListBean.DataDTO dataDTO) {
        TextView textView = (TextView) baseViewHolder.getView(R.id.name);
        textView.setText(dataDTO.getName());
        textView.setSelected(this.value.equals(dataDTO.getId()));
    }
}
